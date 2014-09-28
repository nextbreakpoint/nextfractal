/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfigBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.converter.ConfigConverter;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElement;

/**
 * @author Andrea Medeghini
 */
public class DefaultConfigConverter implements ConfigConverter {
	private static final Logger logger = Logger.getLogger(DefaultConfigConverter.class.getName());

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.converter.extension.ConverterExtensionRuntime#convert(java.io.File)
	 */
	public TwisterConfig convert(final File file) {
		TwisterConfig config = null;
		MandelbrotConverter5 mconverter5 = new MandelbrotConverter5();
		config = mconverter5.convert(file);
		if (config != null) {
			return config;
		}
		JuliaConverter5 jconverter5 = new JuliaConverter5();
		config = jconverter5.convert(file);
		if (config != null) {
			return config;
		}
		Converter4 converter4 = new Converter4();
		config = converter4.convert(file);
		if (config != null) {
			return config;
		}
		return null;
	}

	private static void buildXML(final StringBuffer buffer, final String plane, final String fractal, final String formula, final String wx, final String wy, final String cx, final String cy, final String scale, final String iterations) {
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buffer.append("<photo class=\"it.topix.explorer.");
		buffer.append(fractal);
		buffer.append("ExplorerPhoto\" version=\"1.0\">");
		buffer.append("<plane class=\"it.topix.explorer.Plane$");
		buffer.append(plane);
		buffer.append("\">");
		buffer.append("<configuration class=\"it.topix.explorer.Plane$Lambda$PlaneConfiguration\" version=\"1.0\" />");
		buffer.append("</plane>");
		buffer.append("<formula class=\"it.topix.explorer.");
		buffer.append(fractal);
		buffer.append("Formula$");
		buffer.append(formula);
		buffer.append("\">");
		buffer.append("<configuration class=\"it.topix.explorer.Formula$Abstract");
		buffer.append(formula);
		buffer.append("$FormulaConfiguration\" version=\"1.0\">");
		buffer.append("<iterations>");
		buffer.append(iterations);
		buffer.append("</iterations>");
		buffer.append("<threshold>40.0</threshold>");
		buffer.append("<scale>");
		buffer.append(scale);
		buffer.append("</scale>");
		buffer.append("<x-center>");
		buffer.append(cx);
		buffer.append("</x-center>");
		buffer.append("<y-center>");
		buffer.append(cy);
		buffer.append("</y-center>");
		buffer.append("<x-constant>");
		buffer.append(wx);
		buffer.append("</x-constant>");
		buffer.append("<y-constant>");
		buffer.append(wy);
		buffer.append("</y-constant>");
		buffer.append("</configuration>");
		buffer.append("</formula>");
		buffer.append("<external-filter class=\"it.topix.explorer.ExternalFilter$Potenziale\">");
		buffer.append("<configuration class=\"it.topix.explorer.ExternalFilter$Potenziale$FilterConfiguration\" version=\"1.0\">");
		buffer.append("<palette class=\"it.topix.nextfractal.plaf.Palette\" version=\"1.0\">");
		buffer.append("<sequence class=\"it.topix.nextfractal.plaf.Sequence\" version=\"1.0\">");
		buffer.append("<function class=\"it.topix.nextfractal.ColorTableFunction$LIN\"><configuration class=\"it.topix.nextfractal.ColorTableFunction$LIN$FunctionConfiguration\" version=\"1.0\"/></function>");
		buffer.append("<color-steps>256</color-steps>");
		buffer.append("<first-color>ffffff</first-color>");
		buffer.append("<last-color>0</last-color>");
		buffer.append("</sequence>");
		buffer.append("</palette>");
		buffer.append("</configuration>");
		buffer.append("</external-filter>");
		buffer.append("<internal-filter class=\"it.topix.explorer.InternalFilter$Colore\">");
		buffer.append("<configuration class=\"it.topix.explorer.InternalFilter$Colore$FilterConfiguration\" version=\"1.0\">");
		buffer.append("<color>0</color>");
		buffer.append("</configuration>");
		buffer.append("</internal-filter>");
		buffer.append("<image-filter class=\"it.topix.explorer.ImageFilter$Copy\">");
		buffer.append("<configuration class=\"it.topix.explorer.ImageFilter$Copy$FilterConfiguration\" version=\"1.0\" />");
		buffer.append("</image-filter>");
		buffer.append("<rotation-parameters>");
		buffer.append("<direction>0</direction>");
		buffer.append("<enable>false</enable>");
		buffer.append("<mode>0</mode>");
		buffer.append("<speed>20</speed>");
		buffer.append("<angle>0.0</angle>");
		buffer.append("</rotation-parameters>");
		buffer.append("<color-parameters>");
		buffer.append("<direction>0</direction>");
		buffer.append("<enable>false</enable>");
		buffer.append("<mode>0</mode>");
		buffer.append("<speed>0</speed>");
		buffer.append("<shift>0</shift>");
		buffer.append("</color-parameters>");
		buffer.append("</photo>");
	}

	private static final class FractalData {
		private String type = "undefined";
		private String insidepalette = "undefined";
		private String outsidepalette = "undefined";
		private String formulamodule = "undefined";
		private String insidemethodmodule = "undefined";
		private String outsidemethodmodule = "undefined";
		private String formulaoptions = "undefined";
		private String insidemethodoptions = "undefined";
		private String outsidemethodoptions = "undefined";
		private int iterations = 100;
		private double[] parameters;

		/**
		 * Allocates a new fractalData object.
		 */
		public FractalData() {
			parameters = new double[6];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = 0.0d;
			}
		}

		/**
		 * Allocates a new fractalData object from the specified URL.
		 */
		public FractalData(final URL resource) throws FormatException {
			parameters = new double[6];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = 0.0d;
			}
			if (resource == null) {
				throw new NullPointerException("illegal argument : resource is null !");
			}
			try {
				load(new InputStreamReader(resource.openStream()));
			}
			catch (Exception e) {
				throw new FormatException("wrong resource format ! [" + resource.getFile() + "]");
			}
		}

		/**
		 * Allocates a new fractalData object from the specified file.
		 */
		public FractalData(final File file) throws FileNotFoundException, FormatException {
			parameters = new double[6];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = 0.0d;
			}
			if (file == null) {
				throw new NullPointerException("illegal argument : file is null !");
			}
			try {
				load(new FileReader(file));
			}
			catch (FileNotFoundException e) {
				throw (e);
			}
			catch (Exception e) {
				throw new FormatException("wrong file format ! [" + file.getName() + "]");
			}
		}

		/**
		 * Allocates a new fractalData object from the specified string.
		 */
		public FractalData(final String string) throws FormatException {
			parameters = new double[6];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = 0.0d;
			}
			if (string == null) {
				throw new NullPointerException("illegal argument : string is null !");
			}
			try {
				load(new StringReader(string));
			}
			catch (Exception e) {
				throw new FormatException("wrong string format !");
			}
		}

		private void load(final Reader r) throws IOException, FormatException {
			try {
				BufferedReader reader = new BufferedReader(r);
				String comment = reader.readLine();
				StringTokenizer t = new StringTokenizer(reader.readLine(), ":");
				String id = t.nextToken();
				if (!(id.equals("nextfractal fractal 3.1"))) {
					if (!(id.equals("nextfractal fractal 3.0"))) {
						if (!(id.equals("nextfractal fractal 2.0"))) {
							if (!(id.equals("nextfractal fractal 1.2"))) {
								throw new FormatException("wrong file format !");
							}
							else {
								insidepalette = comment;
								outsidepalette = comment;
								type = t.nextToken();
								if ((!type.equals("julia/fatou")) && (!type.equals("mandelbrot"))) {
									throw new FormatException("wrong file type !");
								}
								formulamodule = t.nextToken();
								insidemethodmodule = t.nextToken();
								outsidemethodmodule = insidemethodmodule;
								formulaoptions = "undefined";
								insidemethodoptions = "undefined";
								outsidemethodoptions = "undefined";
								for (int j = 0; j < 6; j++) {
									parameters[j] = Double.valueOf(t.nextToken()).doubleValue();
								}
								iterations = 400;
								if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ2")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ2";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ3")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ3";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ4")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ4";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ5")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ5";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalcosZ")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulacosZ";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalsinZ")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulasinZ";
								}
								else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalexpZ")) {
									formulamodule = "org.nextfractal.engine.basic.module.formula.formulaexpZ";
								}
							}
						}
						else {
							insidepalette = comment;
							outsidepalette = comment;
							type = t.nextToken();
							if ((!type.equals("julia/fatou")) && (!type.equals("mandelbrot"))) {
								throw new FormatException("wrong file type !");
							}
							formulamodule = t.nextToken();
							insidemethodmodule = t.nextToken();
							outsidemethodmodule = t.nextToken();
							for (int j = 0; j < 6; j++) {
								parameters[j] = Double.valueOf(t.nextToken()).doubleValue();
							}
							iterations = Integer.valueOf(t.nextToken()).intValue();
							formulaoptions = reader.readLine();
							insidemethodoptions = reader.readLine();
							outsidemethodoptions = reader.readLine();
							if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ2")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ2";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ3")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ3";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ4")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ4";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ5")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ5";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalcosZ")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulacosZ";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalsinZ")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulasinZ";
							}
							else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalexpZ")) {
								formulamodule = "org.nextfractal.engine.basic.module.formula.formulaexpZ";
							}
						}
					}
					else {
						type = t.nextToken();
						if ((!type.equals("julia/fatou")) && (!type.equals("mandelbrot"))) {
							throw new FormatException("wrong file type !");
						}
						formulamodule = t.nextToken();
						insidemethodmodule = t.nextToken();
						outsidemethodmodule = t.nextToken();
						for (int j = 0; j < 6; j++) {
							parameters[j] = Double.valueOf(t.nextToken()).doubleValue();
						}
						iterations = Integer.valueOf(t.nextToken()).intValue();
						formulaoptions = reader.readLine();
						insidemethodoptions = reader.readLine();
						outsidemethodoptions = reader.readLine();
						insidepalette = reader.readLine();
						outsidepalette = reader.readLine();
						if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ2")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ2";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ3")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ3";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ4")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ4";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalZ5")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulaZ5";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalcosZ")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulacosZ";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalsinZ")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulasinZ";
						}
						else if (formulamodule.equals("org.nextfractal.engine.basic.module.fractal.fractalexpZ")) {
							formulamodule = "org.nextfractal.engine.basic.module.formula.formulaexpZ";
						}
					}
				}
				else {
					type = t.nextToken();
					if ((!type.equals("julia/fatou")) && (!type.equals("mandelbrot"))) {
						throw new FormatException("wrong file type !");
					}
					formulamodule = t.nextToken();
					insidemethodmodule = t.nextToken();
					outsidemethodmodule = t.nextToken();
					for (int j = 0; j < 6; j++) {
						parameters[j] = Double.valueOf(t.nextToken()).doubleValue();
					}
					iterations = Integer.valueOf(t.nextToken()).intValue();
					formulaoptions = reader.readLine();
					insidemethodoptions = reader.readLine();
					outsidemethodoptions = reader.readLine();
					insidepalette = reader.readLine();
					outsidepalette = reader.readLine();
				}
				reader.close();
			}
			catch (NumberFormatException e) {
				throw new FormatException("wrong format !");
			}
			catch (NoSuchElementException e) {
				throw new FormatException("wrong format !");
			}
		}

		private void save(final Writer w) throws IOException {
			BufferedWriter writer = new BufferedWriter(w);
			writer.write("#don't remove this line!\n");
			writer.write("nextfractal fractal 3.1:" + getType());
			writer.write(":" + formulamodule);
			writer.write(":" + insidemethodmodule);
			writer.write(":" + outsidemethodmodule);
			double[] parameters = getParameters();
			for (int i = 0; i < 6; i++) {
				writer.write(":" + Double.toString(parameters[i]));
			}
			writer.write(":" + iterations + "\n");
			writer.write(formulaoptions + "\n");
			writer.write(insidemethodoptions + "\n");
			writer.write(outsidemethodoptions + "\n");
			writer.write(insidepalette + "\n");
			writer.write(outsidepalette + "\n");
			writer.close();
		}

		/**
		 * Saves the fractalData object in the specified file.
		 */
		public void save(final File file) throws IOException {
			save(new FileWriter(file));
		}

		/**
		 * Returns the fractalData object loaded from the specified file.
		 */
		public static DefaultConfigConverter.FractalData load(final File file) throws IOException, FormatException {
			return (new FractalData(file));
		}

		/**
		 * Returns the fractalData object loaded from the specified URL.
		 */
		public static DefaultConfigConverter.FractalData load(final URL resource) throws IOException, FormatException {
			return (new FractalData(resource));
		}

		/**
		 * Returns a String object representing this fractalData object.
		 */
		public String toString() {
			String string = "fractal type " + getType();
			string += " " + formulamodule;
			string += " " + insidemethodmodule;
			string += " " + outsidemethodmodule;
			// string += " " + formulaoptions;
			// string += " " + insidemethodoptions;
			// string += " " + outsidemethodoptions;
			if (parameters != null) {
				for (int i = 0; i < 6; i++) {
					string += " " + Double.toString(parameters[i]);
				}
			}
			else {
				string += " undefined region";
			}
			// string += " " + insidepalette;
			// string += " " + outsidepalette;
			return (string);
		}

		/**
		 * Sets the fractal's type.
		 */
		public void setType(final String type) {
			if (type == null) {
				throw new NullPointerException("illegal argument : type is null !");
			}
			this.type = type;
		}

		/**
		 * Sets the inside palette.
		 */
		public void setInsidePalette(final String palette) {
			if (palette == null) {
				throw new NullPointerException("illegal argument : palette is null !");
			}
			insidepalette = palette;
		}

		/**
		 * Sets the outside palette.
		 */
		public void setOutsidePalette(final String palette) {
			if (palette == null) {
				throw new NullPointerException("illegal argument : palette is null !");
			}
			outsidepalette = palette;
		}

		/**
		 * Sets the fractal formula module's name.
		 */
		public void setFormulaModule(final String name) {
			if (name == null) {
				throw new NullPointerException();
			}
			formulamodule = name;
		}

		/**
		 * Sets the inside method module's name.
		 */
		public void setInsideMethodModule(final String name) {
			if (name == null) {
				throw new NullPointerException("illegal argument : name is null !");
			}
			insidemethodmodule = name;
		}

		/**
		 * Sets the outside method module's name.
		 */
		public void setOutsideMethodModule(final String name) {
			if (name == null) {
				throw new NullPointerException("illegal argument : name is null !");
			}
			outsidemethodmodule = name;
		}

		/**
		 * Sets the fractal formula module's options.
		 */
		public void setFormulaOptions(final String options) {
			if (options == null) {
				throw new NullPointerException("illegal argument : options is null !");
			}
			formulaoptions = options;
		}

		/**
		 * Sets the inside method module's options.
		 */
		public void setInsideMethodOptions(final String options) {
			if (options == null) {
				throw new NullPointerException("illegal argument : options is null !");
			}
			insidemethodoptions = options;
		}

		/**
		 * Sets the outside method module's options.
		 */
		public void setOutsideMethodOptions(final String options) {
			if (options == null) {
				throw new NullPointerException("illegal argument : options is null !");
			}
			outsidemethodoptions = options;
		}

		/**
		 * Sets the region's parameters.
		 */
		public void setParameters(final double[] parameters) {
			if (parameters == null) {
				throw new NullPointerException("illegal argument : parameters is null !");
			}
			this.parameters = parameters;
		}

		/**
		 * Sets the number of iterations.
		 */
		public void setIterations(final int i) {
			iterations = i;
		}

		/**
		 * Returns the fractal's type.
		 */
		public String getType() {
			return (type);
		}

		/**
		 * Returns the inside palette.
		 */
		public String getInsidePalette() {
			return (insidepalette);
		}

		/**
		 * Returns the outside palette.
		 */
		public String getOutsidePalette() {
			return (outsidepalette);
		}

		/**
		 * Returns the fractal formula module's name.
		 */
		public String getFormulaModule() {
			return (formulamodule);
		}

		/**
		 * Returns the inside method module's name.
		 */
		public String getInsideMethodModule() {
			return (insidemethodmodule);
		}

		/**
		 * Returns the outside method module's name.
		 */
		public String getOutsideMethodModule() {
			return (outsidemethodmodule);
		}

		/**
		 * Returns the fractal formula module's options.
		 */
		public String getFormulaOptions() {
			return (formulaoptions);
		}

		/**
		 * Returns the inside method module's options.
		 */
		public String getInsideMethodOptions() {
			return (insidemethodoptions);
		}

		/**
		 * Returns the outside method module's options.
		 */
		public String getOutsideMethodOptions() {
			return (outsidemethodoptions);
		}

		/**
		 * Returns the region's parameters.
		 */
		public double[] getParameters() {
			return (parameters);
		}

		/**
		 * Returns the number of iterations.
		 */
		public int getIterations() {
			return (iterations);
		}
	}

	/**
	 * The FormatException class signals that a format exception has occurred.
	 */
	public static class FormatException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Allocates a new FormatException object.
		 */
		public FormatException() {
			super("format exception !");
		}

		/**
		 * Allocates a new FormatException object.
		 * 
		 * @param message the exception's description.
		 */
		public FormatException(final String message) {
			super(message);
		}
	}

	private class Converter4 {
		public TwisterConfig convert(final File file) {
			try {
				DefaultConfigConverter.FractalData data = new FractalData(file);
				StringBuffer buffer = new StringBuffer();
				String plane = "Lambda";
				String formula = null;
				if (data.getFormulaModule().indexOf("org.nextfractal.engine.basic.module.formula.formula") > -1) {
					formula = data.getFormulaModule().substring("org.nextfractal.engine.basic.module.formula.formula".length()).toUpperCase();
				}
				else {
					if (data.getFormulaModule().equals("module_formulaMI.basicModule")) {
						formula = "MAGNETISM1";
					}
					else if (data.getFormulaModule().equals("module_formulaMII.basicModule")) {
						formula = "MAGNETISM2";
					}
					else if (data.getFormulaModule().equals("module_formulaZ2R.basicModule")) {
						formula = "Z2";
						plane = "Inverso1";
					}
					else if (data.getFormulaModule().equals("module_formulaZ3R.basicModule")) {
						formula = "Z3";
						plane = "Inverso1";
					}
					else if (data.getFormulaModule().equals("module_formulaZ4R.basicModule")) {
						formula = "Z4";
						plane = "Inverso1";
					}
					else if (data.getFormulaModule().equals("module_formulaZ5R.basicModule")) {
						formula = "Z5";
						plane = "Inverso1";
					}
					else if (data.getFormulaModule().equals("module_formulaZ8R.basicModule")) {
						formula = "ZN";
						plane = "Inverso1";
					}
					else if (data.getFormulaModule().equals("module_formulaZ8C.basicModule")) {
						formula = "ZN";
					}
				}
				if (formula != null) {
					String iterations = String.valueOf(data.iterations);
					String cx = String.valueOf(data.parameters[0] + data.parameters[2] / 2d);
					String cy = String.valueOf(data.parameters[1] + data.parameters[3] / 2d);
					String scale = String.valueOf(data.parameters[2]);
					String wx = String.valueOf(data.parameters[4]);
					String wy = String.valueOf(data.parameters[5]);
					buffer.setLength(0);
					if (data.type.equals("mandelbrot")) {
						buildXML(buffer, plane, "Mandelbrot", formula, wx, wy, cx, cy, scale, iterations);
						logger.info(buffer.toString());
						MandelbrotConverter5 converter5 = new MandelbrotConverter5();
						Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(buffer.toString().getBytes()));
						TwisterConfig config = converter5.convert(doc.getDocumentElement());
						return config;
					}
					else {
						buildXML(buffer, plane, "Julia", formula, wx, wy, cx, cy, scale, iterations);
						logger.info(buffer.toString());
						JuliaConverter5 converter5 = new JuliaConverter5();
						Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(buffer.toString().getBytes()));
						TwisterConfig config = converter5.convert(doc.getDocumentElement());
						return config;
					}
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (DefaultConfigConverter.FormatException e) {
				e.printStackTrace();
			}
			catch (SAXException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private class MandelbrotConverter5 {
		public TwisterConfig convert(final File file) {
			try {
				ZipFile zip = new ZipFile(file);
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(zip.getInputStream(zip.getEntry("photo.xml")));
				zip.close();
				TwisterConfig config = convert(doc.getDocumentElement());
				return config;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			catch (Error e) {
				e.printStackTrace();
			}
			return null;
		}

		public TwisterConfig convert(final Element element) {
			if (isValidPhoto(element)) {
				try {
					String plane = ((Element) XML.getElementsByName(element, "plane").get(0)).getAttribute("class");
					String formula = ((Element) XML.getElementsByName(element, "formula").get(0)).getAttribute("class");
					// String externalFilter = ((Element) XML.getElementsByName(element, "external-filter").get(0)).getAttribute("class");
					// String internalFilter = ((Element) XML.getElementsByName(element, "internal-filter").get(0)).getAttribute("class");
					// String imageFilter = ((Element) XML.getElementsByName(element, "image-filter").get(0)).getAttribute("class");
					if (plane.equals("it.topix.explorer.Plane$Lambda")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.z";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso1")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.u";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso2")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.y";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso3")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.h";
					}
					else {
						throw new Exception("Invalid plane");
					}
					if (formula.equals("it.topix.explorer.MandelbrotFormula$Z2")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z2";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$Z3")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z3";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$Z4")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z4";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$Z5")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z5";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$ZN")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.zn";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$COSZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.cosz";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$SINZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.sinz";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$EXPZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.expz";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$EXPZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.expz";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$MAGNETISM1")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.magnetism1";
					}
					else if (formula.equals("it.topix.explorer.MandelbrotFormula$MAGNETISM2")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.magnetism2";
					}
					else {
						throw new Exception("Invalid formula");
					}
					Element formulaElement = (Element) XML.getElementsByName(element, "formula").get(0);
					int iterations = XML.getIntegerElementValue(formulaElement, "iterations");
					double threshold = XML.getDoubleElementValue(formulaElement, "threshold");
					double s = XML.getDoubleElementValue(formulaElement, "scale");
					DoubleVector2D scale = new DoubleVector2D(s, s);
					double x = XML.getDoubleElementValue(formulaElement, "x-center");
					double y = XML.getDoubleElementValue(formulaElement, "y-center");
					DoubleVector2D center = new DoubleVector2D(x, y);
					x = XML.getDoubleElementValue(formulaElement, "x-constant");
					y = XML.getDoubleElementValue(formulaElement, "y-constant");
					DoubleVector2D constant = new DoubleVector2D(x, y);
					// Element rotation = (Element) XML.getElementsByName(element, "rotation-parameters").get(0);
					// Element color = (Element) XML.getElementsByName(element, "color-parameters").get(0);
					// int rotationDirection = XML.getIntegerElementValue(rotation, "direction");
					// boolean rotationEnable = XML.getBooleanElementValue(rotation, "enable");
					// int rotationMode = XML.getIntegerElementValue(rotation, "mode");
					// int rotationSpeed = XML.getIntegerElementValue(rotation, "speed");
					// double rotationAngle = XML.getDoubleElementValue(rotation, "angle");
					// int colorDirection = XML.getIntegerElementValue(color, "direction");
					// boolean colorEnable = XML.getBooleanElementValue(color, "enable");
					// int colorMode = XML.getIntegerElementValue(color, "mode");
					// int colorSpeed = XML.getIntegerElementValue(color, "speed");
					// int colorShift = XML.getIntegerElementValue(color, "shift");
					TwisterConfig config = new TwisterConfig();
					EffectConfigElement effectElement = new EffectConfigElement();
					FrameConfigElement frameElement = new FrameConfigElement();
					GroupLayerConfigElement groupLayerElement = new GroupLayerConfigElement();
					ImageLayerConfigElement imageLayerElement = new ImageLayerConfigElement();
					ImageConfigElement imageElement = new ImageConfigElement();
					MandelbrotImageConfig imageConfig = new MandelbrotImageConfig();
					imageConfig.getMandelbrotConfig().setConstant(constant);
					imageConfig.getMandelbrotConfig().setShowPreview(Boolean.FALSE);
					final MandelbrotFractalConfigElement fractalElement = new MandelbrotFractalConfigElement();
					final RenderingFormulaConfigElement renderingFormulaElement = new RenderingFormulaConfigElement();
					final TransformingFormulaConfigElement transformingFormulaElement = new TransformingFormulaConfigElement();
					final ProcessingFormulaConfigElement processingFormulaElement = new ProcessingFormulaConfigElement();
					final IncolouringFormulaConfigElement incolouringFormulaElement = new IncolouringFormulaConfigElement();
					final OutcolouringFormulaConfigElement outcolouringFormulaElement = new OutcolouringFormulaConfigElement();
					final OrbitTrapConfigElement orbitTrapElement = new OrbitTrapConfigElement();
					fractalElement.setRenderingFormulaConfigElement(renderingFormulaElement);
					renderingFormulaElement.setReference(MandelbrotRegistry.getInstance().getRenderingFormulaExtension(formula).createConfigurableExtensionReference());
					fractalElement.setTransformingFormulaConfigElement(transformingFormulaElement);
					transformingFormulaElement.setReference(MandelbrotRegistry.getInstance().getTransformingFormulaExtension(plane).createConfigurableExtensionReference());
					fractalElement.setProcessingFormulaConfigElement(processingFormulaElement);
					fractalElement.setOrbitTrapConfigElement(orbitTrapElement);
					fractalElement.appendIncolouringFormulaConfigElement(incolouringFormulaElement);
					incolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getIncolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_INCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
					fractalElement.appendOutcolouringFormulaConfigElement(outcolouringFormulaElement);
					outcolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getOutcolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_OUTCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
					imageConfig.getMandelbrotConfig().setMandelbrotFractal(fractalElement);
					ConfigurableExtensionReference<ImageExtensionConfig> reference = TwisterRegistry.getInstance().getImageExtension("twister.frame.layer.image.mandelbrot").createConfigurableExtensionReference(imageConfig);
					imageElement.setReference(reference);
					imageLayerElement.setImageConfigElement(imageElement);
					groupLayerElement.appendLayerConfigElement(imageLayerElement);
					frameElement.appendLayerConfigElement(groupLayerElement);
					config.setFrameConfigElement(frameElement);
					config.setEffectConfigElement(effectElement);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setCenter(center);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setScale(scale);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setIterations(iterations);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setThreshold(threshold);
					return config;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				catch (Error e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		private boolean isValidPhoto(final Element element) {
			return ("photo".equals(element.getNodeName()) && "it.topix.explorer.MandelbrotExplorerPhoto".equals(element.getAttribute("class")) && "1.0".equals(element.getAttribute("version")));
		}
	}

	private class JuliaConverter5 {
		public TwisterConfig convert(final File file) {
			try {
				ZipFile zip = new ZipFile(file);
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(zip.getInputStream(zip.getEntry("photo.xml")));
				zip.close();
				TwisterConfig config = convert(doc.getDocumentElement());
				return config;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			catch (Error e) {
				e.printStackTrace();
			}
			return null;
		}

		public TwisterConfig convert(final Element element) {
			if (isValidPhoto(element)) {
				try {
					String plane = ((Element) XML.getElementsByName(element, "plane").get(0)).getAttribute("class");
					String formula = ((Element) XML.getElementsByName(element, "formula").get(0)).getAttribute("class");
					// String externalFilter = ((Element) XML.getElementsByName(element, "external-filter").get(0)).getAttribute("class");
					// String internalFilter = ((Element) XML.getElementsByName(element, "internal-filter").get(0)).getAttribute("class");
					// String imageFilter = ((Element) XML.getElementsByName(element, "image-filter").get(0)).getAttribute("class");
					if (plane.equals("it.topix.explorer.Plane$Lambda")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.z";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso1")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.u";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso2")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.y";
					}
					else if (plane.equals("it.topix.explorer.Plane$Inverso3")) {
						plane = "twister.mandelbrot.fractal.transforming.formula.h";
					}
					else {
						throw new Exception("Invalid plane");
					}
					if (formula.equals("it.topix.explorer.JuliaFormula$Z2")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z2";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$Z3")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z3";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$Z4")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z4";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$Z5")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.z5";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$ZN")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.zn";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$COSZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.cosz";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$SINZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.sinz";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$EXPZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.expz";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$EXPZ")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.expz";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$MAGNETISM1")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.magnetism1";
					}
					else if (formula.equals("it.topix.explorer.JuliaFormula$MAGNETISM2")) {
						formula = "twister.mandelbrot.fractal.rendering.formula.magnetism2";
					}
					else {
						throw new Exception("Invalid formula");
					}
					Element formulaElement = (Element) XML.getElementsByName(element, "formula").get(0);
					int iterations = XML.getIntegerElementValue(formulaElement, "iterations");
					double threshold = XML.getDoubleElementValue(formulaElement, "threshold");
					double s = XML.getDoubleElementValue(formulaElement, "scale");
					DoubleVector2D scale = new DoubleVector2D(s, s);
					double x = XML.getDoubleElementValue(formulaElement, "x-center");
					double y = XML.getDoubleElementValue(formulaElement, "y-center");
					DoubleVector2D center = new DoubleVector2D(x, y);
					x = XML.getDoubleElementValue(formulaElement, "x-constant");
					y = XML.getDoubleElementValue(formulaElement, "y-constant");
					DoubleVector2D constant = new DoubleVector2D(x, y);
					// Element rotation = (Element) XML.getElementsByName(element, "rotation-parameters").get(0);
					// Element color = (Element) XML.getElementsByName(element, "color-parameters").get(0);
					// int rotationDirection = XML.getIntegerElementValue(rotation, "direction");
					// boolean rotationEnable = XML.getBooleanElementValue(rotation, "enable");
					// int rotationMode = XML.getIntegerElementValue(rotation, "mode");
					// int rotationSpeed = XML.getIntegerElementValue(rotation, "speed");
					// double rotationAngle = XML.getDoubleElementValue(rotation, "angle");
					// int colorDirection = XML.getIntegerElementValue(color, "direction");
					// boolean colorEnable = XML.getBooleanElementValue(color, "enable");
					// int colorMode = XML.getIntegerElementValue(color, "mode");
					// int colorSpeed = XML.getIntegerElementValue(color, "speed");
					// int colorShift = XML.getIntegerElementValue(color, "shift");
					TwisterConfig config = new TwisterConfig();
					EffectConfigElement effectElement = new EffectConfigElement();
					FrameConfigElement frameElement = new FrameConfigElement();
					GroupLayerConfigElement groupLayerElement = new GroupLayerConfigElement();
					ImageLayerConfigElement imageLayerElement = new ImageLayerConfigElement();
					ImageConfigElement imageElement = new ImageConfigElement();
					MandelbrotImageConfig imageConfig = new MandelbrotImageConfig();
					imageConfig.getMandelbrotConfig().setImageMode(MandelbrotImageConfig.IMAGE_MODE_JULIA);
					imageConfig.getMandelbrotConfig().setConstant(constant);
					imageConfig.getMandelbrotConfig().setShowPreview(Boolean.FALSE);
					final MandelbrotFractalConfigElement fractalElement = new MandelbrotFractalConfigElement();
					final RenderingFormulaConfigElement renderingFormulaElement = new RenderingFormulaConfigElement();
					final TransformingFormulaConfigElement transformingFormulaElement = new TransformingFormulaConfigElement();
					final ProcessingFormulaConfigElement processingFormulaElement = new ProcessingFormulaConfigElement();
					final IncolouringFormulaConfigElement incolouringFormulaElement = new IncolouringFormulaConfigElement();
					final OutcolouringFormulaConfigElement outcolouringFormulaElement = new OutcolouringFormulaConfigElement();
					final OrbitTrapConfigElement orbitTrapElement = new OrbitTrapConfigElement();
					fractalElement.setRenderingFormulaConfigElement(renderingFormulaElement);
					renderingFormulaElement.setReference(MandelbrotRegistry.getInstance().getRenderingFormulaExtension(formula).createConfigurableExtensionReference());
					fractalElement.setTransformingFormulaConfigElement(transformingFormulaElement);
					transformingFormulaElement.setReference(MandelbrotRegistry.getInstance().getTransformingFormulaExtension(plane).createConfigurableExtensionReference());
					fractalElement.setProcessingFormulaConfigElement(processingFormulaElement);
					fractalElement.setOrbitTrapConfigElement(orbitTrapElement);
					transformingFormulaElement.setReference(MandelbrotRegistry.getInstance().getTransformingFormulaExtension(plane).createConfigurableExtensionReference());
					fractalElement.appendIncolouringFormulaConfigElement(incolouringFormulaElement);
					incolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getIncolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_INCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
					fractalElement.appendOutcolouringFormulaConfigElement(outcolouringFormulaElement);
					outcolouringFormulaElement.setReference(MandelbrotRegistry.getInstance().getOutcolouringFormulaExtension(MandelbrotConfigBuilder.DEFAULT_OUTCOLOURING_FORMULA_EXTENSION_ID).createConfigurableExtensionReference());
					imageConfig.getMandelbrotConfig().setMandelbrotFractal(fractalElement);
					ConfigurableExtensionReference<ImageExtensionConfig> reference = TwisterRegistry.getInstance().getImageExtension("twister.frame.layer.image.mandelbrot").createConfigurableExtensionReference(imageConfig);
					imageElement.setReference(reference);
					imageLayerElement.setImageConfigElement(imageElement);
					groupLayerElement.appendLayerConfigElement(imageLayerElement);
					frameElement.appendLayerConfigElement(groupLayerElement);
					config.setFrameConfigElement(frameElement);
					config.setEffectConfigElement(effectElement);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setCenter(center);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setScale(scale);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setIterations(iterations);
					((RenderingFormulaExtensionConfig) renderingFormulaElement.getReference().getExtensionConfig()).setThreshold(threshold);
					return config;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				catch (Error e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		private boolean isValidPhoto(final Element element) {
			return ("photo".equals(element.getNodeName()) && "it.topix.explorer.JuliaExplorerPhoto".equals(element.getAttribute("class")) && "1.0".equals(element.getAttribute("version")));
		}
	}
}
