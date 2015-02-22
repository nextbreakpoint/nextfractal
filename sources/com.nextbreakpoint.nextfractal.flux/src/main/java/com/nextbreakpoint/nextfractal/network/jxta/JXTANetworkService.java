/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.network.jxta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredTextDocument;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.ModuleClassAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;
import net.jxta.util.PipeEventListener;
import net.jxta.util.PipeStateListener;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.network.ServiceConsumer;
import com.nextbreakpoint.nextfractal.network.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.network.ServiceException;
import com.nextbreakpoint.nextfractal.network.ServiceMessage;
import com.nextbreakpoint.nextfractal.network.ServiceProcessor;
import com.nextbreakpoint.nextfractal.network.ServiceProducer;
import com.nextbreakpoint.nextfractal.network.ServiceSession;
import com.nextbreakpoint.nextfractal.network.SessionDelegate;
import com.nextbreakpoint.nextfractal.network.SessionHandler;

/**
 * @author Andrea Medeghini
 */
public class JXTANetworkService {
	public static final String JXTA_MULTICAST = "nextfractal.jxta.multicast";
	public static final String JXTA_RELAY_SEEDS = "nextfractal.jxta.relay.seeds";
	public static final String JXTA_RENDEZVOUS_SEEDS = "nextfractal.jxta.rendezvous.seeds";
	private static final MimeMediaType GZIP_MEDIA_TYPE = new MimeMediaType("application/gzip");
	private static final Logger logger = Logger.getLogger(JXTANetworkService.class.getName());
	private static final int CONNECTION_RETRY = 2;
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int CONNECTION_PAUSE = 5000;
	private static final int SERVERPIPE_TIMEOUT = 10 * 60 * 1000;
	private static final int RENDEZVOUS_TIMEOUT = 20000;
	private static final long ADVERTISEMENT_LIFETIME = 15 * 60 * 1000L;
	private static final long ADVERTISEMENT_EXPIRATION = 15 * 60 * 1000L;
	private static final long PUBLISH_POLLINGTIME = 18 * 60 * 1000L;
	private static final long DISCOVER_POLLINGTIME = 30 * 1000L;
	private static final long CLEANUP_POLLINGTIME = 30 * 1000L;
	private static final long CONTROL_POLLINGTIME = 30 * 1000L;
	private static final long SESSION_TIMEOUT = 120 * 1000L;
	private static final int MAX_LENGTH = 32 * 1024;
	private static final ThreadFactory factory = new DefaultThreadFactory("JXTANetwork", true, Thread.MIN_PRIORITY);
	private final Map<String, JXTAServiceSession> controlSessions = new HashMap<String, JXTAServiceSession>();
	private final Map<String, JXTAServiceSession> serverSessions = new HashMap<String, JXTAServiceSession>();
	private final Map<String, JXTAServiceSession> clientSessions = new HashMap<String, JXTAServiceSession>();
	private final Map<String, PipeAdvertisement> advertisements = new HashMap<String, PipeAdvertisement>();
	private final Map<String, ServiceEndpoint> endpoints = new HashMap<String, ServiceEndpoint>();
	private final Set<String> invalidAdvertisements = new HashSet<String>();
	private final Object monitor = new Object();
	private final ServiceProcessor processor;
	private final String serviceURI;
	private final String serviceName;
	private final String serviceAuthor;
	private final String serviceVersion;
	private final File workdir;
	private DiscoveryService discovery;
	private NetworkManager manager;
	private JxtaServerPipe serverPipe;
	private PipeAdvertisement pipeadv;
	private Thread thread;
	private boolean running;
	private boolean dirty;

	/**
	 * @param workdir
	 * @param serviceURI
	 * @param serviceName
	 * @param serviceAuthor
	 * @param serviceVersion
	 * @param processor
	 */
	public JXTANetworkService(final File workdir, final String serviceURI, final String serviceName, final String serviceAuthor, final String serviceVersion, final ServiceProcessor processor) {
		this.workdir = workdir;
		this.serviceURI = serviceURI;
		this.serviceName = serviceName;
		this.serviceAuthor = serviceAuthor;
		this.serviceVersion = serviceVersion;
		this.processor = processor;
	}

	/**
	 * 
	 */
	public void start() {
		if (thread == null) {
			thread = factory.newThread(new NetworkHandler());
			thread.setName("JXTANetwork");
			running = true;
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		if (thread != null) {
			running = false;
			thread.interrupt();
			try {
				thread.join();
			}
			catch (final InterruptedException e) {
			}
			thread = null;
		}
	}

	/**
	 * @return
	 */
	public Collection<ServiceEndpoint> getEndpoints() {
		synchronized (endpoints) {
			return endpoints.values();
		}
	}

	/**
	 * @param pipeadv
	 * @param delegate
	 * @return
	 * @throws ServiceException
	 */
	public ServiceSession createSession(final PipeAdvertisement pipeadv, final SessionDelegate delegate) throws ServiceException {
		try {
			for (int i = 0; i < CONNECTION_RETRY; i++) {
				try {
					return createClientSession(pipeadv, delegate);
				}
				catch (final Exception e) {
				}
				Thread.sleep(CONNECTION_PAUSE);
			}
			try {
				return createClientSession(pipeadv, delegate);
			}
			catch (final Exception e) {
				synchronized (invalidAdvertisements) {
					invalidAdvertisements.add(pipeadv.getID().toString());
				}
				logger.warning("Can't connect to pipe " + pipeadv.getID().toString());
				throw new ServiceException(e);
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.warning("Can't connect to pipe " + pipeadv.getID().toString());
			throw new ServiceException(e);
		}
	}

	private JXTAServiceSession createClientSession(final PipeAdvertisement pipeadv, final SessionDelegate delegate) throws IOException, Exception {
		final JXTAConnectionHandler handler = new JXTAConnectionHandler();
		final JxtaBiDiPipe pipe = new JxtaBiDiPipe(manager.getNetPeerGroup(), pipeadv, CONNECTION_TIMEOUT, handler, true);
		handler.setPipe(pipe);
		final JXTAServiceSession session = new JXTAServiceSession(new JXTAClientServiceConsumer(handler, delegate), new JXTAClientServiceProducer(handler));
		handler.setSessionId(session.getSessionId());
		logger.info("Client session created " + session.getSessionId());
		synchronized (clientSessions) {
			clientSessions.put(session.getSessionId(), session);
		}
		return session;
	}

	/**
	 * @param pipeadv
	 */
	public void invalidate(final PipeAdvertisement pipeadv) {
		synchronized (controlSessions) {
			synchronized (invalidAdvertisements) {
				final Iterator<JXTAServiceSession> sessionIterator = controlSessions.values().iterator();
				while (sessionIterator.hasNext()) {
					final ServiceSession session = sessionIterator.next();
					final PipeAdvertisement tmpPipeadv = advertisements.get(session.getSessionId());
					if ((tmpPipeadv != null) && tmpPipeadv.getID().equals(pipeadv.getID())) {
						invalidAdvertisements.add(pipeadv.getID().toString());
						advertisements.remove(session.getSessionId());
						endpoints.remove(session.getSessionId());
						sessionIterator.remove();
						session.dispose();
					}
				}
			}
		}
	}

	/**
	 * @param pipeadv
	 * @return
	 */
	public boolean isInvalidated(final PipeAdvertisement pipeadv) {
		synchronized (invalidAdvertisements) {
			return invalidAdvertisements.contains(pipeadv.getPipeID().toString());
		}
	}

	private class NetworkHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			boolean interrupted = false;
			final Thread discoveryThread = factory.newThread(new DiscoveryHandler());
			discoveryThread.setName(serviceName + " Discovery Thread");
			final Thread publishThread = factory.newThread(new PublishHandler());
			publishThread.setName(serviceName + " Publish Thread");
			final Thread cleanupThread = factory.newThread(new CleanupHandler());
			cleanupThread.setName(serviceName + " Cleanup Thread");
			final Thread controlThread = factory.newThread(new ControlHandler());
			controlThread.setName(serviceName + " Control Thread");
			try {
				final File cache = new File(workdir, "jxta");
				manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, serviceName, cache.toURI());
				configureNetwork(manager);
				manager.startNetwork();
				if (!manager.waitForRendezvousConnection(RENDEZVOUS_TIMEOUT)) {
					logger.warning("Failed to connect to Rendezvous service");
				}
				final PeerGroup netPeerGroup = manager.getNetPeerGroup();
				discovery = netPeerGroup.getDiscoveryService();
				pipeadv = createPipeAdvertisement(manager, serviceName);
				discoveryThread.start();
				publishThread.start();
				cleanupThread.start();
				controlThread.start();
				serverPipe = new JxtaServerPipe(netPeerGroup, pipeadv, 100, SERVERPIPE_TIMEOUT);
				while (running) {
					try {
						final JxtaBiDiPipe pipe = serverPipe.accept();
						if (pipe != null) {
							createServerSession(pipe);
						}
					}
					catch (final Exception e) {
						logger.info(e.getMessage());
					}
				}
			}
			catch (final InterruptedException e) {
				interrupted = true;
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			finally {
				discoveryThread.interrupt();
				publishThread.interrupt();
				cleanupThread.interrupt();
				controlThread.interrupt();
				try {
					discoveryThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					publishThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					cleanupThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					controlThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				synchronized (controlSessions) {
					controlSessions.clear();
				}
				synchronized (serverSessions) {
					controlSessions.clear();
				}
				synchronized (clientSessions) {
					controlSessions.clear();
				}
				synchronized (advertisements) {
					advertisements.clear();
				}
				synchronized (endpoints) {
					endpoints.clear();
				}
				if (manager != null) {
					manager.stopNetwork();
				}
			}
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}

		private void createServerSession(final JxtaBiDiPipe pipe) throws Exception {
			final JXTAConnectionHandler handler = new JXTAConnectionHandler();
			handler.setPipe(pipe);
			final SessionHandler sessionHandler = processor.createSessionHandler();
			final JXTAServiceSession session = new JXTAServiceSession(new JXTAServerServiceConsumer(handler, sessionHandler), new JXTAServerServiceProducer(handler));
			handler.setSessionId(session.getSessionId());
			sessionHandler.setSession(session);
			logger.info("Server session created " + session.getSessionId());
			synchronized (serverSessions) {
				serverSessions.put(session.getSessionId(), session);
			}
		}
	}

	private static void configureNetwork(final NetworkManager manager) throws Exception {
		NetworkConfigurator config = manager.getConfigurator();
		if (config.exists()) {
			config.load();
		}
		else {
			createNewConfig(manager, config);
		}
		if (System.getProperty(JXTA_RELAY_SEEDS) != null) {
			URI seedingURI = new File(System.getProperty(JXTA_RELAY_SEEDS)).toURI();
			logger.info("Add relay seeds: " + seedingURI);
			config.clearRelaySeedingURIs();
			config.addRelaySeedingURI(seedingURI);
		}
		if (System.getProperty(JXTA_RENDEZVOUS_SEEDS) != null) {
			URI seedingURI = new File(System.getProperty(JXTA_RENDEZVOUS_SEEDS)).toURI();
			logger.info("Add rendezvous seeds: " + seedingURI);
			config.clearRendezvousSeedingURIs();
			config.addRdvSeedingURI(seedingURI);
		}
		if (Boolean.getBoolean(JXTA_MULTICAST)) {
			logger.info("Enable multicast");
			config.setUseMulticast(true);
		}
	}

	private static void createNewConfig(final NetworkManager manager, final NetworkConfigurator config) throws IOException {
		config.setName("NextFractal");
		config.setInfrastructureID(createInfrastructurePeerGroupID());
		config.setInfrastructureDescriptionStr("NextFractal");
		config.setInfrastructureName("NextFractal-JXTA");
		config.setPeerID(createPeerID(manager.getInfrastructureID()));
		config.setUseMulticast(false);
		config.setTcpIncoming(true);
		config.setTcpOutgoing(true);
		config.setTcpEnabled(true);
		config.setTcpStartPort(9701);
		config.setTcpEndPort(9704);
		config.setTcpPort(9701);
		config.setHttpEnabled(false);
		config.save();
	}

	private static final PeerGroupID createInfrastructurePeerGroupID() {
		return IDFactory.newPeerGroupID("NextFractal-PeerGroup".getBytes());
	}

	private static final PeerID createPeerID(final PeerGroupID peerGroupId) {
		return IDFactory.newPeerID(peerGroupId, String.valueOf(System.currentTimeMillis()).getBytes());
	}

	private static PipeID createPipeId(final PeerGroupID peerGroupId) {
		return IDFactory.newPipeID(peerGroupId, String.valueOf(System.currentTimeMillis()).getBytes());
	}

	private static PipeAdvertisement createPipeAdvertisement(final NetworkManager manager, final String name) {
		final PipeAdvertisement advertisement = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
		advertisement.setPipeID(createPipeId(manager.getInfrastructureID()));
		advertisement.setType("NextFractal-JXTA");
		advertisement.setType(PipeService.UnicastType);
		advertisement.setName(name);
		return advertisement;
	}

	private static void printAdvertisement(final Advertisement adv) {
		try {
			final StructuredTextDocument<?> doc = (StructuredTextDocument<?>) adv.getDocument(MimeMediaType.XMLUTF8);
			final StringWriter out = new StringWriter();
			doc.sendToWriter(out);
			logger.fine(out.toString());
			out.close();
		}
		catch (final Exception e) {
		}
	}

	private class CleanupHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					try {
						synchronized (controlSessions) {
							final Iterator<JXTAServiceSession> sessionIterator = controlSessions.values().iterator();
							while (sessionIterator.hasNext()) {
								final JXTAServiceSession session = sessionIterator.next();
								if (session.isExpired()) {
									logger.info("Dispose expired session " + session.getSessionId());
									advertisements.remove(session.getSessionId());
									endpoints.remove(session.getSessionId());
									sessionIterator.remove();
									session.dispose();
								}
								else {
									try {
										session.consumeMessages();
									}
									catch (final Exception e) {
										e.printStackTrace();
										session.invalidate();
									}
								}
							}
						}
						synchronized (serverSessions) {
							final Iterator<JXTAServiceSession> sessionIterator = serverSessions.values().iterator();
							while (sessionIterator.hasNext()) {
								final JXTAServiceSession session = sessionIterator.next();
								if (session.isExpired()) {
									logger.info("Dispose expired session " + session.getSessionId());
									sessionIterator.remove();
									session.dispose();
								}
								else {
									try {
										session.consumeMessages();
									}
									catch (final Exception e) {
										e.printStackTrace();
										session.invalidate();
									}
								}
							}
						}
						synchronized (clientSessions) {
							final Iterator<JXTAServiceSession> sessionIterator = clientSessions.values().iterator();
							while (sessionIterator.hasNext()) {
								final JXTAServiceSession session = sessionIterator.next();
								if (session.isExpired()) {
									logger.info("Dispose expired session " + session.getSessionId());
									sessionIterator.remove();
									session.dispose();
								}
								else {
									try {
										session.consumeMessages();
									}
									catch (final Exception e) {
										e.printStackTrace();
										session.invalidate();
									}
								}
							}
						}
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (monitor) {
						if (!dirty) {
							monitor.wait(CLEANUP_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						dirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			synchronized (controlSessions) {
				for (final ServiceSession session : controlSessions.values()) {
					logger.info("Dispose session " + session.getSessionId());
					session.dispose();
				}
				controlSessions.clear();
			}
			synchronized (serverSessions) {
				for (final ServiceSession session : serverSessions.values()) {
					logger.info("Dispose session " + session.getSessionId());
					session.dispose();
				}
				serverSessions.clear();
			}
			synchronized (clientSessions) {
				for (final ServiceSession session : clientSessions.values()) {
					logger.info("Dispose session " + session.getSessionId());
					session.dispose();
				}
				clientSessions.clear();
			}
		}
	}

	private class PublishHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final ModuleClassAdvertisement mcadv = (ModuleClassAdvertisement) AdvertisementFactory.newAdvertisement(ModuleClassAdvertisement.getAdvertisementType());
			mcadv.setName("JXTAMOD:" + serviceName);
			mcadv.setDescription(serviceName);
			mcadv.setModuleClassID(IDFactory.newModuleClassID());
			final ModuleSpecAdvertisement msadv = (ModuleSpecAdvertisement) AdvertisementFactory.newAdvertisement(ModuleSpecAdvertisement.getAdvertisementType());
			msadv.setName("JXTASPEC:" + serviceName);
			mcadv.setDescription(serviceName);
			msadv.setCreator(serviceAuthor);
			msadv.setVersion(serviceVersion);
			msadv.setModuleSpecID(IDFactory.newModuleSpecID(mcadv.getModuleClassID()));
			msadv.setSpecURI(serviceURI);
			msadv.setPipeAdvertisement(pipeadv);
			try {
				while (running) {
					try {
						if (logger.isLoggable(Level.FINE)) {
							logger.fine("Publish module class advertisement");
							printAdvertisement(mcadv);
						}
						discovery.publish(mcadv, ADVERTISEMENT_LIFETIME, ADVERTISEMENT_EXPIRATION);
						discovery.remotePublish(mcadv, ADVERTISEMENT_EXPIRATION);
						if (logger.isLoggable(Level.FINE)) {
							logger.fine("Publish module spec advertisement");
							printAdvertisement(msadv);
						}
						discovery.publish(msadv, ADVERTISEMENT_LIFETIME, ADVERTISEMENT_EXPIRATION);
						discovery.remotePublish(msadv, ADVERTISEMENT_EXPIRATION);
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(PUBLISH_POLLINGTIME);
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class ControlHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					synchronized (controlSessions) {
						final Iterator<JXTAServiceSession> sessionIterator = controlSessions.values().iterator();
						while (sessionIterator.hasNext()) {
							final JXTAServiceSession session = sessionIterator.next();
							try {
								session.sendKeepAliveMessage();
							}
							catch (final Exception e) {
								session.invalidate();
								e.printStackTrace();
							}
						}
					}
					Thread.sleep(CONTROL_POLLINGTIME);
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class DiscoveryHandler implements Runnable, DiscoveryListener, SessionDelegate {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			discovery.addDiscoveryListener(this);
			try {
				while (running) {
					try {
						discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, "Name", "JXTASPEC:" + serviceName, 100);
					}
					catch (final Exception e) {
						logger.warning(e.getMessage());
					}
					Thread.sleep(DISCOVER_POLLINGTIME);
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			discovery.removeDiscoveryListener(this);
		}

		/**
		 * @see net.jxta.discovery.DiscoveryListener#discoveryEvent(net.jxta.discovery.DiscoveryEvent)
		 */
		@Override
		public void discoveryEvent(final DiscoveryEvent e) {
			final DiscoveryResponseMsg response = e.getResponse();
			logger.fine("Got a discovery response [" + response.getResponseCount() + " elements] from peer " + e.getSource());
			final Enumeration<Advertisement> advEnum = response.getAdvertisements();
			if (advEnum != null) {
				while (advEnum.hasMoreElements()) {
					final Advertisement adv = advEnum.nextElement();
					if (adv.getAdvType().equals(ModuleSpecAdvertisement.getAdvertisementType())) {
						final ModuleSpecAdvertisement msadv = (ModuleSpecAdvertisement) adv;
						logger.fine("Got a module spec advertisement");
						printAdvertisement(msadv);
						synchronized (controlSessions) {
							synchronized (invalidAdvertisements) {
								if (!pipeadv.getID().toString().equals(msadv.getPipeAdvertisement().getID().toString()) && !controlSessions.containsKey(msadv.getPipeAdvertisement().getID().toString()) && !invalidAdvertisements.contains(msadv.getPipeAdvertisement().getID().toString())) {
									Thread thread = factory.newThread(new DiscoveryRunnable(msadv, this));
									thread.setName("Discovery task for pipe " + msadv.getPipeAdvertisement().getPipeID().toString());
									thread.start();
								}
							}
						}
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.SessionDelegate.network.ServiceListener#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
		}
	}

	private class DiscoveryRunnable implements Runnable, SessionDelegate {
		private final ModuleSpecAdvertisement msadv;
		private final SessionDelegate delegate;

		/**
		 * @param msadv
		 */
		public DiscoveryRunnable(final ModuleSpecAdvertisement msadv, final SessionDelegate delegate) {
			this.msadv = msadv;
			this.delegate = delegate;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				JXTAServiceSession session = (JXTAServiceSession) createSession(msadv.getPipeAdvertisement(), delegate);
				synchronized (controlSessions) {
					final JXTAServiceEndpoint endpoint = new JXTAServiceEndpoint(JXTANetworkService.this, msadv.getPipeAdvertisement());
					controlSessions.put(msadv.getPipeAdvertisement().getID().toString(), session);
					advertisements.put(session.getSessionId(), msadv.getPipeAdvertisement());
					endpoints.put(session.getSessionId(), endpoint);
					logger.info("Session " + session.getSessionId() + " bound to Endpoint " + endpoint);
				}
			}
			catch (ServiceException x) {
				logger.warning("Can't create control session for pipe " + msadv.getPipeAdvertisement().getID().toString());
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.SessionDelegate.network.ServiceListener#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
		}
	}

	private class JXTAConnectionHandler implements PipeMsgListener, PipeStateListener, PipeEventListener {
		private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		private final List<Message> newMessages = new ArrayList<Message>();
		private final List<Message> messages = new ArrayList<Message>();
		private final byte[] buffer = new byte[MAX_LENGTH];
		private SessionDelegate delegate;
		private String lastCorrelationId;
		private long lastReceivedTime;
		private int lastPartReceived;
		private JxtaBiDiPipe pipe;
		private String sessionId;

		/**
		 * 
		 */
		public JXTAConnectionHandler() {
			lastReceivedTime = System.currentTimeMillis();
		}

		/**
		 * @param pipe
		 */
		public void setPipe(final JxtaBiDiPipe pipe) {
			this.pipe = pipe;
			pipe.setMessageListener(this);
			pipe.setPipeEventListener(this);
			pipe.setPipeStateListener(this);
		}

		/**
		 * @param sessionId
		 */
		public void setSessionId(final String sessionId) {
			this.sessionId = sessionId;
		}

		/**
		 * @param session
		 */
		public void setSessionDelegate(final SessionDelegate delegate) {
			this.delegate = delegate;
		}

		/**
		 * 
		 */
		public void dispose() {
			synchronized (this) {
				if (pipe != null) {
					pipe.setMessageListener(null);
					pipe.setPipeEventListener(null);
					pipe.setPipeStateListener(null);
					try {
						if (pipe.isBound()) {
							pipe.close();
						}
					}
					catch (final IOException e) {
					}
					pipe = null;
				}
			}
			synchronized (messages) {
				messages.clear();
			}
		}

//		/**
//		 * @return
//		 */
//		public synchronized boolean isDisposed() {
//			return pipe == null;
//		}

		/**
		 * @return
		 */
		public synchronized boolean isConnected() {
			return (pipe != null) && pipe.isBound();
		}

		/**
		 * @return
		 */
		public synchronized boolean isTimeout() {
			return (System.currentTimeMillis() - lastReceivedTime) > SESSION_TIMEOUT;
		}

		/**
		 * @param message
		 * @throws Exception
		 */
		public synchronized void sendMessage(final ServiceMessage message) throws ServiceException {
			if (!isConnected()) {
				return;
			}
			ByteArrayOutputStream baos = null;
			GZIPOutputStream zos = null;
			ObjectOutputStream oos = null;
			try {
				baos = new ByteArrayOutputStream();
				zos = new GZIPOutputStream(baos);
				oos = new ObjectOutputStream(zos);
				oos.writeObject(message);
				oos.close();
				zos.finish();
				zos.close();
				baos.close();
				byte[] data = baos.toByteArray();
				int maxLength = MAX_LENGTH;
				int offset = 0;
				int parts = 1;
				int part = 1;
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("[sessionId = " + sessionId + "] Sending message " + message + " (dataLength = " + data.length + " bytes)");
				}
				if (data.length > maxLength) {
					parts += data.length / maxLength;
					for (int i = 1; i < parts; i++) {
						sendDataMessage(message.getMessageId(), parts, part, data, offset, maxLength);
						offset += maxLength;
						part += 1;
					}
					sendDataMessage(message.getMessageId(), parts, part, data, offset, data.length % maxLength);
				}
				else {
					sendDataMessage(message.getMessageId(), parts, part, data, 0, data.length);
				}
			}
			catch (final Exception e) {
				throw new ServiceException(e);
			}
			finally {
				if (oos != null) {
					try {
						oos.close();
					}
					catch (Exception e) {
					}
				}
				if (zos != null) {
					try {
						zos.close();
					}
					catch (Exception e) {
					}
				}
				if (baos != null) {
					try {
						baos.close();
					}
					catch (Exception e) {
					}
				}
			}
		}

		private void sendDataMessage(final String correlationId, final int parts, final int part, final byte[] data, final int offset, final int length) throws ServiceException {
			try {
				final Message msg = new Message();
				msg.addMessageElement(new StringMessageElement("correlationId", correlationId, null));
				msg.addMessageElement(new StringMessageElement("messageParts", String.valueOf(parts), null));
				msg.addMessageElement(new StringMessageElement("messagePart", String.valueOf(part), null));
				msg.addMessageElement(new StringMessageElement("messageType", "data", null));
				msg.addMessageElement(new ByteArrayMessageElement("messageBody", GZIP_MEDIA_TYPE, data, offset, length, null));
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("[sessionId = " + sessionId + "] Sending data message (correlationId = " + correlationId.toString() + ", part = " + part + " of " + parts + ", size = " + msg.getByteLength() + " bytes)");
				}
				pipe.sendMessage(msg);
			}
			catch (final Exception e) {
				logger.warning("[sessionId = " + sessionId + "] Failed to send message " + correlationId.toString() + " (part = " + part + " of " + parts + ")");
				throw new ServiceException(e);
			}
		}

		/**
		 * @throws ServiceException
		 */
		public synchronized void sendKeepAliveMessage() throws ServiceException {
			if (!isConnected()) {
				return;
			}
			try {
				final Message msg = new Message();
				msg.addMessageElement(new StringMessageElement("messageType", "keepalive", null));
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("[sessionId = " + sessionId + "] Sending keepalive message (size = " + msg.getByteLength() + " bytes)");
				}
				pipe.sendMessage(msg);
			}
			catch (final Exception e) {
				throw new ServiceException(e);
			}
		}

		/**
		 * @throws ServiceException
		 */
		public synchronized void sendAckMessage() throws ServiceException {
			if (!isConnected()) {
				return;
			}
			try {
				final Message msg = new Message();
				msg.addMessageElement(new StringMessageElement("messageType", "ack", null));
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("[sessionId = " + sessionId + "] Sending ack message (size = " + msg.getByteLength() + " bytes)");
				}
				pipe.sendMessage(msg);
			}
			catch (final Exception e) {
				throw new ServiceException(e);
			}
		}

		/**
		 * @throws Exception
		 */
		public void consumeMessages() throws ServiceException {
			try {
				synchronized (messages) {
					newMessages.clear();
					newMessages.addAll(messages);
					messages.clear();
				}
				for (final Message message : newMessages) {
					final MessageElement messageType = message.getMessageElement("messageType");
					if ("keepalive".equals(messageType.toString())) {
						logger.fine("[sessionId = " + sessionId + "] Received keepalive message (size = " + message.getByteLength() + " bytes)");
						sendAckMessage();
					}
					else if ("ack".equals(messageType.toString())) {
						logger.fine("[sessionId = " + sessionId + "] Received ack message (size = " + message.getByteLength() + " bytes)");
					}
					else if ("data".equals(messageType.toString())) {
						final MessageElement correlationId = message.getMessageElement("correlationId");
						final MessageElement messageParts = message.getMessageElement("messageParts");
						final MessageElement messagePart = message.getMessageElement("messagePart");
						final MessageElement messageBody = message.getMessageElement("messageBody");
						int parts = Integer.parseInt(messageParts.toString());
						int part = Integer.parseInt(messagePart.toString());
						if ((lastCorrelationId != null) && !lastCorrelationId.equals(correlationId.toString())) {
							lastCorrelationId = null;
							lastPartReceived = 0;
							baos.reset();
							throw new Exception("[sessionId = " + sessionId + "] Invalid correlationId " + correlationId.toString());
						}
						if ((lastCorrelationId == null) || lastCorrelationId.equals(correlationId.toString())) {
							if (logger.isLoggable(Level.FINE)) {
								logger.fine("[sessionId = " + sessionId + "] Received data message (correlationId = " + correlationId.toString() + ", part = " + part + " of " + parts + ", size = " + message.getByteLength() + " bytes)");
							}
							lastCorrelationId = correlationId.toString();
							if (part > lastPartReceived) {
								lastPartReceived = part;
								int length = 0;
								InputStream is = messageBody.getStream();
								while ((length = is.read(buffer)) > 0) {
									baos.write(buffer, 0, length);
								}
								if (lastPartReceived == parts) {
									byte[] data = baos.toByteArray();
									lastCorrelationId = null;
									lastPartReceived = 0;
									baos.reset();
									if (delegate != null) {
										ByteArrayInputStream bais = null;
										ObjectInputStream ois = null;
										GZIPInputStream zis = null;
										try {
											bais = new ByteArrayInputStream(data);
											zis = new GZIPInputStream(bais);
											ois = new ObjectInputStream(zis);
											final ServiceMessage msg = (ServiceMessage) ois.readObject();
											if (logger.isLoggable(Level.FINE)) {
												logger.fine("[sessionId = " + sessionId + "] Received message " + msg + " (dataLength = " + data.length + " bytes)");
											}
											consumeMessage(msg);
										}
										catch (Exception e) {
											throw new Exception("[sessionId = " + sessionId + "] Failed to decode message correlationId = " + correlationId.toString() + " (part = " + part + " of " + parts + ")");
										}
										finally {
											if (ois != null) {
												try {
													ois.close();
												}
												catch (Exception e) {
												}
											}
											if (zis != null) {
												try {
													zis.close();
												}
												catch (Exception e) {
												}
											}
											if (bais != null) {
												try {
													bais.close();
												}
												catch (Exception e) {
												}
											}
										}
									}
								}
							}
						}
					}
				}
				newMessages.clear();
			}
			catch (final Exception e) {
				throw new ServiceException(e);
			}
		}

		/**
		 * @param message
		 * @return
		 */
		private boolean isMessageValid(final Message message) {
			final MessageElement correlationId = message.getMessageElement("correlationId");
			final MessageElement messageParts = message.getMessageElement("messageParts");
			final MessageElement messagePart = message.getMessageElement("messagePart");
			final MessageElement messageType = message.getMessageElement("messageType");
			final MessageElement messageBody = message.getMessageElement("messageBody");
			if ("data".equals(messageType.toString())) {
				return (correlationId != null) && (messageParts != null) && (messagePart != null) && (messageType != null) && (messageBody != null);
			}
			else if ("keepalive".equals(messageType.toString())) {
				return true;
			}
			else if ("ack".equals(messageType.toString())) {
				return true;
			}
			return false;
		}

		/**
		 * @see net.jxta.pipe.PipeMsgListener#pipeMsgEvent(net.jxta.pipe.PipeMsgEvent)
		 */
		@Override
		public synchronized void pipeMsgEvent(final PipeMsgEvent e) {
			try {
				synchronized (messages) {
					final Message message = e.getMessage();
					if (message != null) {
						if (isMessageValid(message)) {
							lastReceivedTime = System.currentTimeMillis();
							messages.add(message);
						}
					}
				}
				synchronized (monitor) {
					dirty = true;
					monitor.notify();
				}
			}
			catch (Exception x) {
				x.printStackTrace();
			}
		}

		/**
		 * @see net.jxta.util.PipeStateListener#stateEvent(java.lang.Object, int)
		 */
		@Override
		public void stateEvent(final Object source, final int event) {
		}

		/**
		 * @see net.jxta.util.PipeEventListener#pipeEvent(int)
		 */
		@Override
		public void pipeEvent(final int event) {
		}

		/**
		 * @param message
		 * @throws ServiceException
		 */
		public void consumeMessage(final ServiceMessage message) throws ServiceException {
			if (delegate != null) {
				delegate.onMessage(message);
			}
		}
	}

	private class JXTAClientServiceProducer implements ServiceProducer {
		private JXTAConnectionHandler handler;

		/**
		 * @param handler
		 */
		public JXTAClientServiceProducer(final JXTAConnectionHandler handler) {
			this.handler = handler;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void sendMessage(final ServiceMessage message) throws ServiceException {
			if (handler != null) {
				handler.sendMessage(message);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#dispose()
		 */
		@Override
		public void dispose() {
			if (handler != null) {
				handler.dispose();
				handler = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendKeepAliveMessage()
		 */
		@Override
		public void sendKeepAliveMessage() throws ServiceException {
			if (handler != null) {
				handler.sendKeepAliveMessage();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendAckMessage()
		 */
		@Override
		public void sendAckMessage() throws ServiceException {
			if (handler != null) {
				handler.sendAckMessage();
			}
		}
	}

	private class JXTAServerServiceProducer implements ServiceProducer {
		private JXTAConnectionHandler handler;

		/**
		 * @param handler
		 */
		public JXTAServerServiceProducer(final JXTAConnectionHandler handler) {
			this.handler = handler;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void sendMessage(final ServiceMessage message) throws ServiceException {
			if (handler != null) {
				handler.sendMessage(message);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#dispose()
		 */
		@Override
		public void dispose() {
			if (handler != null) {
				handler.dispose();
				handler = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendKeepAliveMessage()
		 */
		@Override
		public void sendKeepAliveMessage() throws ServiceException {
			if (handler != null) {
				handler.sendKeepAliveMessage();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendAckMessage()
		 */
		@Override
		public void sendAckMessage() throws ServiceException {
			if (handler != null) {
				handler.sendAckMessage();
			}
		}
	}

	private class JXTAClientServiceConsumer implements ServiceConsumer, SessionDelegate {
		private JXTAConnectionHandler handler;
		private SessionDelegate delegate;

		/**
		 * @param handler
		 * @param delegate
		 */
		public JXTAClientServiceConsumer(final JXTAConnectionHandler handler, final SessionDelegate delegate) {
			this.handler = handler;
			this.delegate = delegate;
			handler.setSessionDelegate(this);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			if (delegate != null) {
				delegate.onMessage(message);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessages()
		 */
		@Override
		public void consumeMessages() throws ServiceException {
			if (handler != null) {
				handler.consumeMessages();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#isTimeout()
		 */
		@Override
		public boolean isTimeout() {
			if (handler != null) {
				return handler.isTimeout();
			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#dispose()
		 */
		@Override
		public void dispose() {
			if (handler != null) {
				handler.dispose();
				handler = null;
			}
			delegate = null;
		}

//		/**
//		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
//		 */
//		@Override
//		public void consumeMessage(final ServiceMessage message) throws ServiceException {
//			if (handler != null) {
//				handler.consumeMessage(message);
//			}
//		}
	}

	private class JXTAServerServiceConsumer implements ServiceConsumer, SessionDelegate {
		private JXTAConnectionHandler handler;
		private SessionDelegate delegate;

		/**
		 * @param handler
		 * @param delegate
		 */
		public JXTAServerServiceConsumer(final JXTAConnectionHandler handler, final SessionDelegate delegate) {
			this.handler = handler;
			this.delegate = delegate;
			handler.setSessionDelegate(this);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.SessionDelegate.network.ServiceListener#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			if (delegate != null) {
				delegate.onMessage(message);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessages()
		 */
		@Override
		public void consumeMessages() throws ServiceException {
			if (handler != null) {
				handler.consumeMessages();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#isTimeout()
		 */
		@Override
		public boolean isTimeout() {
			if (handler != null) {
				return handler.isTimeout();
			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#dispose()
		 */
		@Override
		public void dispose() {
			if (handler != null) {
				handler.dispose();
				handler = null;
			}
			delegate = null;
		}

//		/**
//		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
//		 */
//		@Override
//		public void consumeMessage(final ServiceMessage message) throws ServiceException {
//			if (handler != null) {
//				handler.consumeMessage(message);
//			}
//		}
	}

	private class JXTAServiceSession extends ServiceSession {
		/**
		 * @param consumer
		 * @param producer
		 * @throws Exception
		 */
		public JXTAServiceSession(final JXTAClientServiceConsumer consumer, final JXTAClientServiceProducer producer) throws Exception {
			super(consumer, producer);
		}

		/**
		 * @param consumer
		 * @param producer
		 * @throws Exception
		 */
		public JXTAServiceSession(final JXTAServerServiceConsumer consumer, final JXTAServerServiceProducer producer) throws Exception {
			super(consumer, producer);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceSession#dispose()
		 */
		@Override
		public void dispose() {
			logger.fine("Disposed session " + getSessionId());
			super.dispose();
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceSession#isLocalSession()
		 */
		@Override
		public boolean isLocalSession() {
			return false;
		}

//		/**
//		 * @param message
//		 * @throws Exception
//		 */
//		public void onMessage(final ServiceMessage message) throws ServiceException {
//			if (isExpired()) {
//				throw new ServiceException("Expired session " + sessionId);
//			}
//			consumer.onMessage(message);
//		}
//
//		/**
//		 * @param message
//		 * @throws Exception
//		 */
//		public void consumeMessage(final ServiceMessage message) throws ServiceException {
//			if (isExpired()) {
//				throw new ServiceException("Expired session " + sessionId);
//			}
//			consumer.consumeMessage(message);
//		}

		/**
		 * @throws Exception
		 */
		public void consumeMessages() throws ServiceException {
			if (isExpired()) {
				throw new ServiceException("Expired session " + sessionId);
			}
			consumer.consumeMessages();
		}
	}
}
