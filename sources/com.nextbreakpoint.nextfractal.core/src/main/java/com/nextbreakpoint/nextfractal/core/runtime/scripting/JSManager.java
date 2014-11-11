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
package com.nextbreakpoint.nextfractal.core.runtime.scripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;

/**
 * @author Andrea Medeghini
 */
public class JSManager {
	/**
	 * 
	 */
	private JSManager() {
	}

	/**
	 * @param node
	 * @param basedir
	 * @param file
	 * @throws JSException
	 */
	public static void execute(final RenderContext renderContext, final JSContext jsContext, final NodeObject node, final File basedir, final File file) throws JSException {
		try {
			execute(renderContext, jsContext, node, basedir, new FileInputStream(file), file.getName());
		}
		catch (FileNotFoundException x) {
			throw new JSException(x.getMessage(), x);
		}
	}

	/**
	 * @param node
	 * @param basedir
	 * @param is
	 * @throws JSException
	 */
	private static void execute(final RenderContext renderContext, final JSContext jsContext, final NodeObject node, final File basedir, final InputStream is, final String name) throws JSException {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
			DefaultJSTree jsTree = new DefaultJSTree(renderContext, node);
			Bindings bindings = engine.createBindings();
			bindings.put("nextfractalContext", jsContext);
			bindings.put("nextfractalTree", jsTree);
			String script = loadScript(basedir, is);
//			context.setClassShutter(new ClassShutterImpl());
			engine.eval(script, bindings);
		}
		catch (Exception x) {
			x.printStackTrace();
			throw new JSException(x.getMessage(), x);
		}
	}

	private static String loadScript(final File basedir, final InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#include ")) {
					String fileName = line.substring(9);
					if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
						fileName = fileName.substring(1, fileName.length() - 1);
						String script = loadScript(basedir, new FileInputStream(new File(basedir, fileName)));
						builder.append(script);
					}
				}
				else {
					builder.append(line);
				}
				builder.append("\n");
			}
			return builder.toString();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {
				}
			}
		}
	}

//	private static class ClassShutterImpl implements ClassShutter {
//		private List<String> whiteList = new LinkedList<String>();
//		private List<String> blackList = new LinkedList<String>();
//
//		/**
//		 * 
//		 */
//		public ClassShutterImpl() {
//			whiteList.add("org[.]mozilla[.]javascript[.].*");
//			whiteList.add("net[.]sf[.]nextfractal[.].*");
//			whiteList.add("java[.]math[.].*");
//			whiteList.add("java[.]lang[.]Math");
//			whiteList.add("java[.]lang[.]Class");
//			whiteList.add("java[.]lang[.]Object");
//			whiteList.add("java[.]lang[.]Integer");
//			whiteList.add("java[.]lang[.]Long");
//			whiteList.add("java[.]lang[.]Short");
//			whiteList.add("java[.]lang[.]Float");
//			whiteList.add("java[.]lang[.]Double");
//			whiteList.add("java[.]lang[.]String");
//			whiteList.add("java[.]lang[.]Boolean");
//			whiteList.add("java[.]util[.]ArrayList");
//			whiteList.add("java[.]util[.]LinkedList");
//			// whiteList.add("java[.]lang[.]reflect[.].*");
//			// whiteList.add("java[.]lang[.].*");
//			// whiteList.add("java[.]util[.].*");
//			// whiteList.add("java[.]text[.].*");
//			// blackList.add("java[.]lang[.]Thread.*");
//			// blackList.add("java[.]lang[.]System.*");
//			// blackList.add("java[.]lang[.]Runtime.*");
//			// blackList.add("java[.]lang[.]Process.*");
//			// blackList.add("java[.]lang[.]Compiler.*");
//			// blackList.add("java[.]lang[.]ClassLoader.*");
//			// blackList.add("java[.]util[.]concurrent.*");
//			// blackList.add("java[.]util[.]zip.*");
//			// blackList.add("java[.]util[.]jar.*");
//			// blackList.add("java[.]util[.]prefs.*");
//			// blackList.add("java[.]util[.]logging.*");
//		}
//
//		/**
//		 * @see org.mozilla.javascript.ClassShutter#visibleToScripts(java.lang.String)
//		 */
//		public boolean visibleToScripts(String fullClassName) {
//			for (String regexp : blackList) {
//				if (Pattern.matches(regexp, fullClassName)) {
//					return false;
//				}
//			}
//			for (String regexp : whiteList) {
//				if (Pattern.matches(regexp, fullClassName)) {
//					return true;
//				}
//			}
//			return false;
//		}
//	}
}
