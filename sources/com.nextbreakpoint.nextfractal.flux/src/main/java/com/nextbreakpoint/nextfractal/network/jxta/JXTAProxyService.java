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

import java.io.File;
import java.net.URI;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;

/**
 * @author Andrea Medeghini
 */
public class JXTAProxyService {
	private static final Logger logger = Logger.getLogger(JXTAProxyService.class.getName());
	private static final ThreadFactory factory = new DefaultThreadFactory("JXTAProxy", true, Thread.MIN_PRIORITY);
	private NetworkManager manager;
	private final File workdir;
	private Thread thread;
	private boolean running;

	/**
	 * @param workdir
	 */
	public JXTAProxyService(final File workdir) {
		this.workdir = workdir;
	}

	/**
	 * 
	 */
	public void start() {
		if (thread == null) {
			thread = factory.newThread(new NetworkHandler());
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

	private class NetworkHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			boolean interrupted = false;
			try {
				final File cache = new File(workdir, "jxta");
				manager = new NetworkManager(NetworkManager.ConfigMode.PROXY, "JXTAProxy", cache.toURI());
				configureNetwork(manager);
				manager.startNetwork();
				if (!manager.waitForRendezvousConnection(20000)) {
					logger.warning("Failed to connect to proxy service");
				}
				while (running) {
					Thread.sleep(60000);
				}
			}
			catch (final InterruptedException e) {
				interrupted = true;
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
			finally {
				if (manager != null) {
					manager.stopNetwork();
				}
			}
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private static void configureNetwork(final NetworkManager manager) throws Exception {
		NetworkConfigurator config = manager.getConfigurator();
		if (config.exists()) {
			config.load();
		}
		else {
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
			config.setTcpPort(9703);
			config.setHttpIncoming(true);
			config.setHttpEnabled(true);
			config.setHttpPort(9903);
			config.save();
		}
		if (System.getProperty(JXTANetworkService.JXTA_RELAY_SEEDS) != null) {
			URI seedingURI = new File(System.getProperty(JXTANetworkService.JXTA_RELAY_SEEDS)).toURI();
			logger.info("Add relay seeds: " + seedingURI);
			config.clearRelaySeedingURIs();
			config.addRelaySeedingURI(seedingURI);
		}
		if (System.getProperty(JXTANetworkService.JXTA_RENDEZVOUS_SEEDS) != null) {
			URI seedingURI = new File(System.getProperty(JXTANetworkService.JXTA_RENDEZVOUS_SEEDS)).toURI();
			logger.info("Add rendezvous seeds: " + seedingURI);
			config.clearRendezvousSeedingURIs();
			config.addRdvSeedingURI(seedingURI);
		}
		if (Boolean.getBoolean(JXTANetworkService.JXTA_MULTICAST)) {
			logger.info("Enable multicast");
			config.setUseMulticast(true);
		}
	}

	private static final PeerGroupID createInfrastructurePeerGroupID() {
		return IDFactory.newPeerGroupID("NextFractal-PeerGroup".getBytes());
	}

	private static final PeerID createPeerID(final PeerGroupID peerGroupId) {
		return IDFactory.newPeerID(peerGroupId, String.valueOf(System.currentTimeMillis()).getBytes());
	}
}
