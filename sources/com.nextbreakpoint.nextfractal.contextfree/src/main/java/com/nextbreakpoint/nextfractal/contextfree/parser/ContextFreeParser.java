package com.nextbreakpoint.nextfractal.contextfree.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.nextbreakpoint.nextfractal.cfdg.analysis.DepthFirstAdapter;
import com.nextbreakpoint.nextfractal.cfdg.node.*;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfig;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathAdjustment.PathAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathAdjustment.PathAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeReplacement.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeReplacement.ShapeReplacementExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.extensions.figure.PathFigureConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.figure.RuleFigureConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.CurrentAlphaPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.CurrentBrightnessPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.CurrentHuePathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.CurrentSaturationPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.FlipPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.RotatePathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.Size2PathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.SizePathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.SkewPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.TargetAlphaPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.TargetBrightnessPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.TargetHuePathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.TargetSaturationPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.XPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment.YPathAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.ArcToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.ClosePolyPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.CurveRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.CurveToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.FillPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.LineRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.LineToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.MoveRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.MoveToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.MultiPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.QuadRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.QuadToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.SmoothCurveRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.SmoothCurveToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.SmoothQuadRelPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.SmoothQuadToPathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement.StrokePathReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.CurrentAlphaShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.CurrentBrightnessShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.CurrentHueShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.CurrentSaturationShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.FlipShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.RotateShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.Size2ShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.Size3ShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.SizeShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.SkewShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.TargetAlphaShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.TargetBrightnessShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.TargetHueShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.TargetSaturationShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.XShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.YShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment.ZShapeAdjustmentConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement.MultiShapeReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement.SingleShapeReplacementConfig;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFColor;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;

public class ContextFreeParser {
	public ContextFreeConfig parseConfig(File file) throws ContextFreeParserException {
		try {
			return parseConfig(new File(file.getParent()), new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new ContextFreeParserException(e);
		}
	}
	
	public ContextFreeConfig parseConfig(File baseDir, String text) throws ContextFreeParserException {
		return parseConfig(baseDir, new StringReader(text));
	}

	public ContextFreeConfig parseConfig(File baseDir, Reader reader) throws ContextFreeParserException {
		try {
			CharStream stream = new ANTLRInputStream(reader);
			CFDGLexer lexer = new CFDGLexer(stream);
	        TokenStream tokenStream = new CommonTokenStream(lexer);
	        CFDGParser parser = new CFDGParser(tokenStream);
			ContextFreeConfig config = new ContextFreeConfig();
			CFInterpreter interpreter = new CFInterpreter(baseDir, config);
//			interpreter.inACfdg(parser.cfdg());
//			parser.cfdg()parse().apply(interpreter);
			return config;
//		} catch (ParserException e) {
//			String line = null;
//			try {
//				BufferedReader reader2 = new BufferedReader(reader);
//				reader.reset();
//				int i = 0;
//				while ((line = reader2.readLine()) != null) {
//					if (i == e.getToken().getLine()) {
//						break;
//					}
//					i += 1;
//				}
//				System.out.println("[" + e.getToken().getLine() + "," +  e.getToken().getPos() + "] unexpected token " + e.getToken().getText() + " at line " + line);
//			} catch (IOException x) {
//				x.printStackTrace();
//			}
//			throw new ContextFreeParserException(e);
//		} catch (LexerException e) {
//			throw new ContextFreeParserException(e);
		} catch (IOException e) {
			throw new ContextFreeParserException(e);
		}
	}

	public class CFInterpreter extends DepthFirstAdapter {
		private ContextFreeConfig config;
		private File baseDir;
		
		public CFInterpreter(File baseDir, ContextFreeConfig config) {
			this.baseDir = baseDir;
			this.config = config;
		}

		/**
		 * 
		 */
		@Override
		public void inACfdg(ACfdg node) {
			super.inACfdg(node);
			CFDGConfigElement cfdgElement = new CFDGConfigElement();
			try {
				FigureConfigElement triangleFigureElement = new FigureConfigElement();
				cfdgElement.appendFigureConfigElement(triangleFigureElement);
				ConfigurableExtensionReference<FigureExtensionConfig> triangleReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.triangle").createConfigurableExtensionReference();
				triangleFigureElement.setExtensionReference(triangleReference);
				FigureConfigElement squareFigureElement = new FigureConfigElement();
				cfdgElement.appendFigureConfigElement(squareFigureElement);
				ConfigurableExtensionReference<FigureExtensionConfig> squareReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.square").createConfigurableExtensionReference();
				squareFigureElement.setExtensionReference(squareReference);
				FigureConfigElement circleFigureElement = new FigureConfigElement();
				cfdgElement.appendFigureConfigElement(circleFigureElement);
				ConfigurableExtensionReference<FigureExtensionConfig> circleReference = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.circle").createConfigurableExtensionReference();
				circleFigureElement.setExtensionReference(circleReference);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			config.setCFDG(cfdgElement);
		}

		/**
		 * 
		 */
		@Override
		public void inARuleDeclaration(ARuleDeclaration node) {
			super.inARuleDeclaration(node);
			try {
				FigureConfigElement figureElement = createRuleFigureElement(node);
				config.getCFDG().appendFigureConfigElement(figureElement);
			} catch (ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 
		 */
		@Override
		public void inAPathDeclaration(APathDeclaration node) {
			super.inAPathDeclaration(node);
			try {
				FigureConfigElement figureElement = createPathFigureElement(node);
				config.getCFDG().appendFigureConfigElement(figureElement);
			} catch (ExtensionNotFoundException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 
		 */
		@Override
		public void inAStartshapeDeclaration(AStartshapeDeclaration node) {
			super.inAStartshapeDeclaration(node);
			config.getCFDG().setStartshape(node.getString().getText());
		}

		/**
		 * 
		 */
		@Override
		public void inAIncludeDeclaration(AIncludeDeclaration node) {
			super.inAIncludeDeclaration(node);
			try {
				String path = node.getFilename().getText();
				if (path.startsWith("\"") && path.endsWith("\"")) {
					path = path.substring(1, path.length() - 1);
				}
				ContextFreeConfig tmpConfig = parseConfig(path.startsWith("/") ? new File(path) : new File(baseDir, path));
				for (int i = 0; i < tmpConfig.getCFDG().getFigureConfigElementCount(); i++) {
					config.getCFDG().appendFigureConfigElement(tmpConfig.getCFDG().getFigureConfigElement(i));
				}
//				if (!startshapeFound) {
//					if (tmpConfig.getCFDG().getStartshape() != null) {
//						config.getCFDG().setStartshape(tmpConfig.getCFDG().getStartshape());
//						startshapeFound = true;
//					}
//				}
				tmpConfig.dispose();
			} catch (ContextFreeParserException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 
		 */
		@Override
		public void inABackgroundDeclaration(ABackgroundDeclaration node) {
			super.inABackgroundDeclaration(node);
			CFColor color = new CFColor(0, 0, 1, 1);
			CFColor target = new CFColor();
			for (PBackgroundAdjustment adjustment : node.getBackgroundAdjustment()) {
				if (adjustment instanceof AHueBackgroundAdjustment) {
					float value = evaluateExpression(((AHueBackgroundAdjustment) adjustment).getExpression());
					color.adjustWith(new CFColor(value, 0, 0, 0), target);
				} else if (adjustment instanceof ASaturationBackgroundAdjustment) {
					float value = evaluateExpression(((ASaturationBackgroundAdjustment) adjustment).getExpression());
					color.adjustWith(new CFColor(0, value, 0, 0), target);
				} else if (adjustment instanceof ABrightnessBackgroundAdjustment) {
					float value = evaluateExpression(((ABrightnessBackgroundAdjustment) adjustment).getExpression());
					color.adjustWith(new CFColor(0, 0, value, 0), target);
				} else if (adjustment instanceof AAlphaBackgroundAdjustment) {
					float value = evaluateExpression(((AAlphaBackgroundAdjustment) adjustment).getExpression());
					color.adjustWith(new CFColor(0, 0, 0, value), target);
				}
			}
			config.getCFDG().setBackground(new Color32bit(color.getARGB()));
		}

		/**
		 * 
		 */
		@Override
		public void inATileDeclaration(ATileDeclaration node) {
			super.inATileDeclaration(node);
			float tx = 1;
			float ty = 1;
			float x = 0;
			float y = 0;
			for (PTileAdjustment adjustment : node.getTileAdjustment()) {
				if (adjustment instanceof ATileAdjustment) {
					PFirstExpression firstExpression = ((ATileAdjustment) adjustment).getFirstExpression();
					if (firstExpression instanceof AFirstExpression) {
						float value = evaluateExpression(((AFirstExpression) firstExpression).getExtendedExpression());
						tx = value;
						ty = value;
					}
					PSecondExpression secondExpression = ((ATileAdjustment) adjustment).getSecondExpression();
					if (secondExpression != null && secondExpression instanceof ASecondExpression) {
						float value = evaluateExpression(((ASecondExpression) secondExpression).getExtendedExpression());
						ty = value;
					}
				} else if (adjustment instanceof AXTileAdjustment) {
					float value = evaluateExpression(((AXTileAdjustment) adjustment).getExpression());
					x = value;
				} else if (adjustment instanceof AYTileAdjustment) {
					float value = evaluateExpression(((AYTileAdjustment) adjustment).getExpression());
					y = value;
				}
			}
			config.getCFDG().setUseTile(true);
			config.getCFDG().setX(x);
			config.getCFDG().setY(y);
			config.getCFDG().setWidth(tx);
			config.getCFDG().setHeight(ty);
			config.getCFDG().setTileWidth(tx);
			config.getCFDG().setTileHeight(ty);
		}

		/**
		 * 
		 */
		@Override
		public void inASizeDeclaration(ASizeDeclaration node) {
			super.inASizeDeclaration(node);
			float tx = 1;
			float ty = 1;
			float x = 0;
			float y = 0;
			for (PSizeAdjustment adjustment : node.getSizeAdjustment()) {
				if (adjustment instanceof ASizeSizeAdjustment) {
					PFirstExpression firstExpression = ((ASizeSizeAdjustment) adjustment).getFirstExpression();
					if (firstExpression instanceof AFirstExpression) {
						float value = evaluateExpression(((AFirstExpression) firstExpression).getExtendedExpression());
						tx = value;
						ty = value;
					}
					PSecondExpression secondExpression = ((ASizeSizeAdjustment) adjustment).getSecondExpression();
					if (secondExpression != null && secondExpression instanceof ASecondExpression) {
						float value = evaluateExpression(((ASecondExpression) secondExpression).getExtendedExpression());
						ty = value;
					}
				} else if (adjustment instanceof AXSizeAdjustment) {
					float value = evaluateExpression(((AXSizeAdjustment) adjustment).getExpression());
					x = value;
				} else if (adjustment instanceof AYSizeAdjustment) {
					float value = evaluateExpression(((AYSizeAdjustment) adjustment).getExpression());
					y = value;
				}
			}
			config.getCFDG().setUseSize(true);
			config.getCFDG().setX(x);
			config.getCFDG().setY(y);
			config.getCFDG().setWidth(tx);
			config.getCFDG().setHeight(ty);
			config.getCFDG().setTileWidth(tx);
			config.getCFDG().setTileHeight(ty);
		}

		public float evaluateExpression(PExpression expression) {
			if (expression instanceof AFunctionExpression) {
				return evaluateExpression((AFunctionExpression) expression);
			} else if (expression instanceof ANestedExpression) {
				return evaluateExpression((ANestedExpression) expression);
			} else if (expression instanceof ANumberExpression) {
				return evaluateExpression((ANumberExpression) expression);
			}
			return 0;
		}
		
		public float evaluateExpression(PFirstExpression expression) {
			if (expression instanceof AFirstExpression) {
				return evaluateExpression(((AFirstExpression) expression).getExtendedExpression());
			}
			return 0;
		}
		
		public float evaluateExpression(PSecondExpression expression) {
			if (expression instanceof ASecondExpression) {
				return evaluateExpression(((ASecondExpression) expression).getExtendedExpression());
			}
			return 0;
		}
		
		public float evaluateExpression(PThirdExpression expression) {
			if (expression instanceof AThirdExpression) {
				return evaluateExpression(((AThirdExpression) expression).getExtendedExpression());
			}
			return 0;
		}

		public float evaluateExpression(AFunctionExpression expression) {
			return evaluateFunction(expression.getFunction());
		}
		
		public float evaluateExpression(ANestedExpression expression) {
			return evaluateExpression(expression.getExtendedExpression());
		}

		public float evaluateExpression(ANumberExpression expression) {
			return Float.valueOf(expression.getNumber().getText());
		}
		
		public float evaluateExpression(PExtendedExpression expression) {
			if (expression instanceof AFunctionExtendedExpression) {
				return evaluateExpression((AFunctionExtendedExpression) expression);
			} else if (expression instanceof ANestedExtendedExpression) {
				return evaluateExpression((ANestedExtendedExpression) expression);
			} else if (expression instanceof ANumberExtendedExpression) {
				return evaluateExpression((ANumberExtendedExpression) expression);
			} else if (expression instanceof AComposedExtendedExpression) {
				return evaluateExpression((AComposedExtendedExpression) expression);
			}
			return 0;
		}

		public float evaluateExpression(AFunctionExtendedExpression expression) {
			return evaluateFunction(expression.getFunction());
		}
		
		public float evaluateExpression(ANestedExtendedExpression expression) {
			return evaluateExpression(expression.getExtendedExpression());
		}

		public float evaluateExpression(ANumberExtendedExpression expression) {
			return Float.valueOf(expression.getNumber().getText());
		}

		public float evaluateExpression(AComposedExtendedExpression expression) {
			float value2 = evaluateExpression(expression.getExpression());
			float value1 = evaluateExpression(expression.getExtendedExpression());
			POperator operator = expression.getOperator();
			if (operator instanceof APlusOperator) {
				return value1 + value2;
			} else if (operator instanceof AMinusOperator) {
				return value1 - value2;
			} else if (operator instanceof AStarOperator) {
				return value1 * value2;
			} else if (operator instanceof ASlashOperator) {
				return value1 / value2;
			} else if (operator instanceof AArrowOperator) {
				return (float) Math.pow(value1, value2);
			}
			return 0;
		}

		public float evaluateFunction(PFunction function) {
			if (function instanceof AArg0Function) {
				return evaluateFunction((AArg0Function) function); 
			} else if (function instanceof AArg1Function) {
				return evaluateFunction((AArg1Function) function); 
			} else if (function instanceof AArg2Function) {
				return evaluateFunction((AArg2Function) function); 
			}
			return 0;
		}

		public float evaluateFunction(AArg0Function function) {
			String functionName = function.getFunctionArg0().getText();
			if ("rnd".equals(functionName)) {
				return (float) Math.random();
			}
			return 0;
		}

		public float evaluateFunction(AArg1Function function) {
			PFirstExpression firstExpression = function.getFirstExpression();
			if (firstExpression instanceof AFirstExpression) {
				float value = evaluateExpression(((AFirstExpression) firstExpression).getExtendedExpression());
				String functionName = function.getFunctionArg1().getText();
				if ("sin".equals(functionName)) {
					return (float) Math.sin(value);
				} else if ("cos".equals(functionName)) {
					return (float) Math.cos(value);
				} else if ("tan".equals(functionName)) {
					return (float) Math.tan(value);
				} else if ("sinh".equals(functionName)) {
					return (float) Math.sinh(value);
				} else if ("cosh".equals(functionName)) {
					return (float) Math.cosh(value);
				} else if ("tanh".equals(functionName)) {
					return (float) Math.tanh(value);
				} else if ("asin".equals(functionName)) {
					return (float) Math.asin(value);
				} else if ("acos".equals(functionName)) {
					return (float) Math.acos(value);
				} else if ("atan".equals(functionName)) {
					return (float) Math.atan(value);
				} else if ("abs".equals(functionName)) {
					return Math.abs(value);
				} else if ("exp".equals(functionName)) {
					return (float) Math.exp(value);
				} else if ("log".equals(functionName)) {
					return (float) Math.log(value);
				} else if ("sqrt".equals(functionName)) {
					return (float) Math.sqrt(value);
				}
			}
			return 0;
		}

		public float evaluateFunction(AArg2Function function) {
			PFirstExpression firstExpression = function.getFirstExpression();
			PSecondExpression secondExpression = function.getSecondExpression();
			if (firstExpression instanceof AFirstExpression && secondExpression instanceof ASecondExpression) {
				float value1 = evaluateExpression(((AFirstExpression) firstExpression).getExtendedExpression());
				float value2 = evaluateExpression(((ASecondExpression) secondExpression).getExtendedExpression());
				String functionName = function.getFunctionArg2().getText();
				if ("min".equals(functionName)) {
					return Math.min(value1, value2);
				} else if ("max".equals(functionName)) {
					return Math.max(value1, value2);
				} else if ("pow".equals(functionName)) {
					return (float) Math.pow(value1, value2);
				} else if ("hypot".equals(functionName)) {
					return (float) Math.hypot(value1, value2);
				} else if ("atan2".equals(functionName)) {
					return (float) Math.atan2(value1, value2);
				}
			}
			return 0;
		}

		private FigureConfigElement createPathFigureElement(APathDeclaration pathDeclaration) throws ExtensionNotFoundException {
			PathFigureConfig config = new PathFigureConfig();
			config.setName(pathDeclaration.getString().getText());
			for (PPathReplacementDeclaration pathReplacementDeclaration : pathDeclaration.getPathReplacementDeclaration()) {
				PathReplacementConfigElement pathReplacementElement = createPathReplacementElement(pathReplacementDeclaration);
				if (pathReplacementElement != null) {
					config.appendPathReplacementConfigElement(pathReplacementElement);
				}
			}
//			PathReplacementConfigElement pathReplacementElement = createFlushPathReplacementElement();
//			config.appendPathReplacementConfigElement(pathReplacementElement);
			ConfigurableExtension<FigureExtensionRuntime<?>, FigureExtensionConfig> extension = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.path");
			ConfigurableExtensionReference<FigureExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			FigureConfigElement figureElement = new FigureConfigElement();
			figureElement.setExtensionReference(reference);
			return figureElement;
		}

//		private PathReplacementConfigElement createFlushPathReplacementElement() throws ExtensionNotFoundException {
//			FlushPathReplacementConfig config = new FlushPathReplacementConfig();
//			PathReplacementConfigElement pathReplacementElement = new PathReplacementConfigElement();
//			ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.command.flush");
//			ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
//			pathReplacementElement.setExtensionReference(reference);
//			return pathReplacementElement;
//		}

		private PathReplacementConfigElement createPathReplacementElement(PPathReplacementDeclaration pathReplacementDeclaration) throws ExtensionNotFoundException {
			if (pathReplacementDeclaration instanceof ASinglePathReplacementDeclaration) {
				return createPathReplacementElement((ASinglePathReplacementDeclaration) pathReplacementDeclaration);
			} else if (pathReplacementDeclaration instanceof AUnorderedPathReplacementDeclaration) {
				return createPathReplacementElement((AUnorderedPathReplacementDeclaration) pathReplacementDeclaration);
			} else if (pathReplacementDeclaration instanceof AOrderedPathReplacementDeclaration) {
				return createPathReplacementElement((AOrderedPathReplacementDeclaration) pathReplacementDeclaration);
			}
			return null;
		}

		private PathReplacementConfigElement createPathReplacementElement(ASinglePathReplacementDeclaration pathReplacementDeclaration) throws ExtensionNotFoundException {
			return createPathReplacementElement(pathReplacementDeclaration.getPathReplacement());
		}
		
		private PathReplacementConfigElement createPathReplacementElement(AUnorderedPathReplacementDeclaration pathReplacementDeclaration) throws ExtensionNotFoundException {
			MultiPathReplacementConfig config = new MultiPathReplacementConfig();
			dropSamePathAdjustments(pathReplacementDeclaration.getPathAdjustment());
			config.setTimes(Integer.valueOf(pathReplacementDeclaration.getNumber().getText()));
			for (PPathAdjustment pathAdjustment : pathReplacementDeclaration.getPathAdjustment()) {
				PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(pathAdjustment);
				if (pathAdjustmentElement != null) {
					config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
				}
			}
			PPathReplacementBlock pathReplacementBlock = pathReplacementDeclaration.getPathReplacementBlock();
			if (pathReplacementBlock instanceof ABasicPathReplacementBlock) {
				PPathReplacement pathReplacement = ((ABasicPathReplacementBlock) pathReplacementBlock).getPathReplacement();
				PathReplacementConfigElement pathReplacementElement = createPathReplacementElement(pathReplacement);
				if (pathReplacementElement != null) {
					config.appendPathReplacementConfigElement(pathReplacementElement);
				}
			} else if (pathReplacementBlock instanceof AListPathReplacementBlock) {
				for (PPathReplacementDeclaration pathReplacement : ((AListPathReplacementBlock) pathReplacementBlock).getPathReplacementDeclaration()) {
					PathReplacementConfigElement pathReplacementElement = createPathReplacementElement(pathReplacement);
					if (pathReplacementElement != null) {
						config.appendPathReplacementConfigElement(pathReplacementElement);
					}
				}
			}
			return null;
		}
		
		private PathReplacementConfigElement createPathReplacementElement(AOrderedPathReplacementDeclaration pathReplacementDeclaration) throws ExtensionNotFoundException {
			MultiPathReplacementConfig config = new MultiPathReplacementConfig();
			dropSamePathAdjustments(pathReplacementDeclaration.getPathAdjustment());
			Collections.sort(pathReplacementDeclaration.getPathAdjustment(), new PPathAdjustmentComparator());
			config.setTimes(Integer.valueOf(pathReplacementDeclaration.getNumber().getText()));
			for (PPathAdjustment pathAdjustment : pathReplacementDeclaration.getPathAdjustment()) {
				PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(pathAdjustment);
				if (pathAdjustmentElement != null) {
					config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
				}
			}
			PPathReplacementBlock pathReplacementBlock = pathReplacementDeclaration.getPathReplacementBlock();
			if (pathReplacementBlock instanceof ABasicPathReplacementBlock) {
				PPathReplacement pathReplacement = ((ABasicPathReplacementBlock) pathReplacementBlock).getPathReplacement();
				PathReplacementConfigElement pathReplacementElement = createPathReplacementElement(pathReplacement);
				if (pathReplacementElement != null) {
					config.appendPathReplacementConfigElement(pathReplacementElement);
				}
			} else if (pathReplacementBlock instanceof AListPathReplacementBlock) {
				for (PPathReplacementDeclaration pathReplacement : ((AListPathReplacementBlock) pathReplacementBlock).getPathReplacementDeclaration()) {
					PathReplacementConfigElement pathReplacementElement = createPathReplacementElement(pathReplacement);
					if (pathReplacementElement != null) {
						config.appendPathReplacementConfigElement(pathReplacementElement);
					}
				}
			}
			return null;
		}

		private PathReplacementConfigElement createPathReplacementElement(PPathReplacement pathReplacement) throws ExtensionNotFoundException {
			ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = null;
			if (pathReplacement instanceof AOperationPathReplacement) {
				reference = createPathReplacementExtensionReference((AOperationPathReplacement) pathReplacement);
			} else if (pathReplacement instanceof ACommandPathReplacement) {
				reference = createPathReplacementExtensionReference((ACommandPathReplacement) pathReplacement);
			}
			PathReplacementConfigElement pathReplacementElement = new PathReplacementConfigElement();
			pathReplacementElement.setExtensionReference(reference);
			return pathReplacementElement;
		}

		private PathAdjustmentConfigElement createPathAdjustmentElement(PColorAdjustment pathAdjustment) throws ExtensionNotFoundException {
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = null;
			if (pathAdjustment instanceof ACurrentColorAdjustment) {
				reference = getPathAdjustmentExtensionReference((ACurrentColorAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof ATargetColorAdjustment) {
				reference = getPathAdjustmentExtensionReference((ATargetColorAdjustment) pathAdjustment);
			}
			PathAdjustmentConfigElement pathAdjustmentElement = new PathAdjustmentConfigElement();
			pathAdjustmentElement.setExtensionReference(reference);
			return pathAdjustmentElement;
		}

		private PathAdjustmentConfigElement createPathAdjustmentElement(PPathAdjustment pathAdjustment) throws ExtensionNotFoundException {
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = null;
			if (pathAdjustment instanceof AFlipPathAdjustment) {
				reference = getPathAdjustmentExtensionReference((AFlipPathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof ARotatePathAdjustment) {
				reference = getPathAdjustmentExtensionReference((ARotatePathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof ASize2PathAdjustment) {
				reference = getPathAdjustmentExtensionReference((ASize2PathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof ASizePathAdjustment) {
				reference = getPathAdjustmentExtensionReference((ASizePathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof ASkewPathAdjustment) {
				reference = getPathAdjustmentExtensionReference((ASkewPathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof AXPathAdjustment) {
				reference = getPathAdjustmentExtensionReference((AXPathAdjustment) pathAdjustment);
			} else if (pathAdjustment instanceof AYPathAdjustment) {
				reference = getPathAdjustmentExtensionReference((AYPathAdjustment) pathAdjustment);
			}
			PathAdjustmentConfigElement pathAdjustmentElement = new PathAdjustmentConfigElement();
			pathAdjustmentElement.setExtensionReference(reference);
			return pathAdjustmentElement;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ACurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			PCurrentColorAdjustment currentColorAdjustment = colorAdjustment.getCurrentColorAdjustment();
			if (currentColorAdjustment instanceof AAlphaCurrentColorAdjustment) {
				return getPathAdjustmentExtensionReference((AAlphaCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof ABrightnessCurrentColorAdjustment) {
				return getPathAdjustmentExtensionReference((ABrightnessCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof ASaturationCurrentColorAdjustment) {
				return getPathAdjustmentExtensionReference((ASaturationCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof AHueCurrentColorAdjustment) {
				return getPathAdjustmentExtensionReference((AHueCurrentColorAdjustment) currentColorAdjustment);
			} else {
				return null;
			}
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ATargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			PTargetColorAdjustment targetColorAdjustment = colorAdjustment.getTargetColorAdjustment();
			if (targetColorAdjustment instanceof AAlphaTargetColorAdjustment) {
				return getPathAdjustmentExtensionReference((AAlphaTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof ABrightnessTargetColorAdjustment) {
				return getPathAdjustmentExtensionReference((ABrightnessTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof ASaturationTargetColorAdjustment) {
				return getPathAdjustmentExtensionReference((ASaturationTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof AHueTargetColorAdjustment) {
				return getPathAdjustmentExtensionReference((AHueTargetColorAdjustment) targetColorAdjustment);
			} else {
				return null;
			}
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AAlphaCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentAlphaPathAdjustmentConfig config = new CurrentAlphaPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.currentAlpha");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AAlphaTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetAlphaPathAdjustmentConfig config = new TargetAlphaPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.targetAlpha");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ABrightnessCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentBrightnessPathAdjustmentConfig config = new CurrentBrightnessPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.currentBrightness");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ABrightnessTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetBrightnessPathAdjustmentConfig config = new TargetBrightnessPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.targetBrightness");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ASaturationCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentSaturationPathAdjustmentConfig config = new CurrentSaturationPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.currentSaturation");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ASaturationTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetSaturationPathAdjustmentConfig config = new TargetSaturationPathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.targetSaturation");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AHueCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentHuePathAdjustmentConfig config = new CurrentHuePathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.currentHue");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AHueTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetHuePathAdjustmentConfig config = new TargetHuePathAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.color.targetHue");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AXPathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			XPathAdjustmentConfig config = new XPathAdjustmentConfig();
			config.setValue(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.x");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AYPathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			YPathAdjustmentConfig config = new YPathAdjustmentConfig();
			config.setValue(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.y");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ASizePathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			SizePathAdjustmentConfig config = new SizePathAdjustmentConfig();
			config.setScale(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.size");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ASize2PathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			Size2PathAdjustmentConfig config = new Size2PathAdjustmentConfig();
			config.setScaleX(evaluateExpression(geometryAdjustment.getFirstExpression()));
			config.setScaleY(evaluateExpression(geometryAdjustment.getSecondExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.size2");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ASkewPathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			SkewPathAdjustmentConfig config = new SkewPathAdjustmentConfig();
			config.setShearX(evaluateExpression(geometryAdjustment.getFirstExpression()));
			config.setShearY(evaluateExpression(geometryAdjustment.getSecondExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.skew");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(AFlipPathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			FlipPathAdjustmentConfig config = new FlipPathAdjustmentConfig();
			config.setAngle(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.flip");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathAdjustmentExtensionConfig> getPathAdjustmentExtensionReference(ARotatePathAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			RotatePathAdjustmentConfig config = new RotatePathAdjustmentConfig();
			config.setAngle(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<PathAdjustmentExtensionRuntime<?>, PathAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathAdjustmentExtension("contextfree.path.adjustment.geometry.rotate");
			ConfigurableExtensionReference<PathAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<PathReplacementExtensionConfig> createPathReplacementExtensionReference(AOperationPathReplacement pathReplacement) throws ExtensionNotFoundException {
			PPathOperation pathOperation = pathReplacement.getPathOperation();
			if (pathOperation instanceof APathOperation) {
				return createPathReplacementExtensionReference((APathOperation) pathOperation);
			}
			return null;
		}

		private ConfigurableExtensionReference<PathReplacementExtensionConfig> createPathReplacementExtensionReference(APathOperation pathOperation) throws ExtensionNotFoundException {
			String operation = pathOperation.getOperation().getText();
			if ("LINETO".equals(operation)) {
				LineToPathReplacementConfig config = new LineToPathReplacementConfig();
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.lineto");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("ARCTO".equals(operation)) {
				boolean elliptical = false;
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof ARxOperationParameter) {
						elliptical = true;
					} else if (parameter instanceof ARyOperationParameter) {
						elliptical = true;
					}
				}
				ArcToPathReplacementConfig config = new ArcToPathReplacementConfig();
				if (!elliptical) {
					config.setR(1f);
				}
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARotateOperationParameter) {
						config.setR(evaluateExpression(((ARotateOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARxOperationParameter) {
						config.setRx(evaluateExpression(((ARxOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARyOperationParameter) {
						config.setRy(evaluateExpression(((ARyOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AParametersOperationParameter) {
						String param = ((AParametersOperationParameter) parameter).getString().getText();
						if ("cw".equals(param)) {
							config.setSweep(true);
						} else if ("large".equals(param)) {
							config.setLarge(true);
						}
					}
				}
				if (!elliptical) {
					config.setRx(config.getR());
					config.setRy(config.getR());
					config.setR(0f);
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.arcto");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("CURVETO".equals(operation)) {
				boolean cubic = false;
				boolean smooth = true;
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AX1OperationParameter) {
						smooth = false;
					} else if (parameter instanceof AY1OperationParameter) {
						smooth = false;
					} else if (parameter instanceof AX2OperationParameter) {
						cubic = true;
					} else if (parameter instanceof AY2OperationParameter) {
						cubic = true;
					}
				}
				if (cubic) {
					if (smooth) {
						SmoothCurveToPathReplacementConfig config = new SmoothCurveToPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX2OperationParameter) {
								config.setX2(evaluateExpression(((AX2OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY2OperationParameter) {
								config.setY2(evaluateExpression(((AY2OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.smoothcurveto");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					} else {
						CurveToPathReplacementConfig config = new CurveToPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX1OperationParameter) {
								config.setX1(evaluateExpression(((AX1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY1OperationParameter) {
								config.setY1(evaluateExpression(((AY1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX2OperationParameter) {
								config.setX2(evaluateExpression(((AX2OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY2OperationParameter) {
								config.setY2(evaluateExpression(((AY2OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.curveto");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					}
				} else {
					if (smooth) {
						SmoothQuadToPathReplacementConfig config = new SmoothQuadToPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.smoothquadto");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					} else {
						QuadToPathReplacementConfig config = new QuadToPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX1OperationParameter) {
								config.setX1(evaluateExpression(((AX1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY1OperationParameter) {
								config.setY1(evaluateExpression(((AY1OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.quadto");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					}
				}
			} else if ("MOVETO".equals(operation)) {
				MoveToPathReplacementConfig config = new MoveToPathReplacementConfig();
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.moveto");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("LINEREL".equals(operation)) {
				LineRelPathReplacementConfig config = new LineRelPathReplacementConfig();
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.linerel");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("ARCREL".equals(operation)) {
				boolean elliptical = false;
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof ARxOperationParameter) {
						elliptical = true;
					} else if (parameter instanceof ARyOperationParameter) {
						elliptical = true;
					}
				}
				ArcToPathReplacementConfig config = new ArcToPathReplacementConfig();
				if (!elliptical) {
					config.setR(1f);
				}
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARotateOperationParameter) {
						config.setR(evaluateExpression(((ARotateOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARxOperationParameter) {
						config.setRx(evaluateExpression(((ARxOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof ARyOperationParameter) {
						config.setRy(evaluateExpression(((ARyOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AParametersOperationParameter) {
						String param = ((AParametersOperationParameter) parameter).getString().getText();
						if ("cw".equals(param)) {
							config.setSweep(true);
						} else if ("large".equals(param)) {
							config.setLarge(true);
						}
					}
				}
				if (!elliptical) {
					config.setRx(config.getR());
					config.setRy(config.getR());
					config.setR(0f);
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.arcrel");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("CURVEREL".equals(operation)) {
				boolean cubic = false;
				boolean smooth = true;
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AX1OperationParameter) {
						smooth = false;
					} else if (parameter instanceof AY1OperationParameter) {
						smooth = false;
					} else if (parameter instanceof AX2OperationParameter) {
						cubic = true;
					} else if (parameter instanceof AY2OperationParameter) {
						cubic = true;
					}
				}
				if (cubic) {
					if (smooth) {
						SmoothCurveRelPathReplacementConfig config = new SmoothCurveRelPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX2OperationParameter) {
								config.setX2(evaluateExpression(((AX2OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY2OperationParameter) {
								config.setY2(evaluateExpression(((AY2OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.smoothcurverel");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					} else {
						CurveRelPathReplacementConfig config = new CurveRelPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX1OperationParameter) {
								config.setX1(evaluateExpression(((AX1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY1OperationParameter) {
								config.setY1(evaluateExpression(((AY1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX2OperationParameter) {
								config.setX2(evaluateExpression(((AX2OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY2OperationParameter) {
								config.setY2(evaluateExpression(((AY2OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.curverel");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					}
				} else {
					if (smooth) {
						SmoothQuadRelPathReplacementConfig config = new SmoothQuadRelPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.smoothquadrel");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					} else {
						QuadRelPathReplacementConfig config = new QuadRelPathReplacementConfig();
						for (POperationParameter parameter : pathOperation.getOperationParameter()) {
							if (parameter instanceof AXOperationParameter) {
								config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AYOperationParameter) {
								config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AX1OperationParameter) {
								config.setX1(evaluateExpression(((AX1OperationParameter) parameter).getExpression()));
							} else if (parameter instanceof AY1OperationParameter) {
								config.setY1(evaluateExpression(((AY1OperationParameter) parameter).getExpression()));
							}
						}
						ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.quadrel");
						ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
						return reference;
					}
				}
			} else if ("MOVEREL".equals(operation)) {
				MoveRelPathReplacementConfig config = new MoveRelPathReplacementConfig();
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AXOperationParameter) {
						config.setX(evaluateExpression(((AXOperationParameter) parameter).getExpression()));
					} else if (parameter instanceof AYOperationParameter) {
						config.setY(evaluateExpression(((AYOperationParameter) parameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.moverel");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("CLOSEPOLY".equals(operation)) {
				ClosePolyPathReplacementConfig config = new ClosePolyPathReplacementConfig();
				for (POperationParameter parameter : pathOperation.getOperationParameter()) {
					if (parameter instanceof AParametersOperationParameter) {
						String param = ((AParametersOperationParameter) parameter).getString().getText();
						if ("align".equals(param)) {
							config.setAlign(true);
						}
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.operation.closepoly");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			}
			return null;
		}

		private ConfigurableExtensionReference<PathReplacementExtensionConfig> createPathReplacementExtensionReference(ACommandPathReplacement pathReplacement) throws ExtensionNotFoundException {
			PPathCommand pathCommand = pathReplacement.getPathCommand();
			if (pathCommand instanceof AUnorderedPathCommand) {
				return createPathReplacementExtensionReference((AUnorderedPathCommand) pathCommand);
			} else if (pathCommand instanceof AOrderedPathCommand) {
				return createPathReplacementExtensionReference((AOrderedPathCommand) pathCommand);
			}
			return null;
		}
		
		private ConfigurableExtensionReference<PathReplacementExtensionConfig> createPathReplacementExtensionReference(AUnorderedPathCommand pathCommand) throws ExtensionNotFoundException {
			String command = pathCommand.getCommand().getText();
			if ("FILL".equals(command)) {
				FillPathReplacementConfig config = new FillPathReplacementConfig();
				for (PCommandParameter commadParameter : pathCommand.getCommandParameter()) {
					if (commadParameter instanceof AColorCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AColorCommandParameter) commadParameter).getColorAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AGeometryCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AGeometryCommandParameter) commadParameter).getPathAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AParametersCommandParameter) {
						config.setRule(((AParametersCommandParameter) commadParameter).getString().getText());
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.command.fill");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("STROKE".equals(command)) {
				StrokePathReplacementConfig config = new StrokePathReplacementConfig();
				for (PCommandParameter commadParameter : pathCommand.getCommandParameter()) {
					if (commadParameter instanceof AColorCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AColorCommandParameter) commadParameter).getColorAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AGeometryCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AGeometryCommandParameter) commadParameter).getPathAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AParametersCommandParameter) {
						config.setCap(((AParametersCommandParameter) commadParameter).getString().getText());
					} else if (commadParameter instanceof AStrokeCommandParameter) {
						config.setWidth(evaluateExpression(((AStrokeCommandParameter) commadParameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.command.stroke");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			}
			return null;
		}

		private void dropSameParameters(List<PCommandParameter> commandParameters) {
			AFlipPathAdjustment lastAFlipPathAdjustment = null;
			ARotatePathAdjustment lastARotatePathAdjustment = null;
			ASize2PathAdjustment lastASize2PathAdjustment = null;
			ASizePathAdjustment lastASizePathAdjustment = null;
			ASkewPathAdjustment lastASkewPathAdjustment = null;
			AXPathAdjustment lastAXPathAdjustment = null;
			AYPathAdjustment lastAYPathAdjustment = null;
			for (int i = commandParameters.size() - 1; i >= 0; i--) {
				PCommandParameter commandParameter = commandParameters.get(i);
				if (commandParameter instanceof AGeometryCommandParameter) {
					PPathAdjustment pathAdjustment = ((AGeometryCommandParameter) commandParameter).getPathAdjustment();
					if (pathAdjustment instanceof AFlipPathAdjustment) {
						if (lastAFlipPathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastAFlipPathAdjustment = (AFlipPathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof ARotatePathAdjustment) {
						if (lastARotatePathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastARotatePathAdjustment = (ARotatePathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof ASize2PathAdjustment) {
						if (lastASize2PathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastASize2PathAdjustment = (ASize2PathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof ASizePathAdjustment) {
						if (lastASizePathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastASizePathAdjustment = (ASizePathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof ASkewPathAdjustment) {
						if (lastASkewPathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastASkewPathAdjustment = (ASkewPathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof AXPathAdjustment) {
						if (lastAXPathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastAXPathAdjustment = (AXPathAdjustment) pathAdjustment;
						}
					} else if (pathAdjustment instanceof AYPathAdjustment) {
						if (lastAYPathAdjustment != null) {
							commandParameters.remove(commandParameter);
						} else {
							lastAYPathAdjustment = (AYPathAdjustment) pathAdjustment;
						}
					}
				}
			}
		}

		private ConfigurableExtensionReference<PathReplacementExtensionConfig> createPathReplacementExtensionReference(AOrderedPathCommand pathCommand) throws ExtensionNotFoundException {
			String command = pathCommand.getCommand().getText();
			dropSameParameters(pathCommand.getCommandParameter());
			Collections.sort(pathCommand.getCommandParameter(), new PCommandParameterComparator());
			if ("FILL".equals(command)) {
				FillPathReplacementConfig config = new FillPathReplacementConfig();
				for (PCommandParameter commadParameter : pathCommand.getCommandParameter()) {
					if (commadParameter instanceof AColorCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AColorCommandParameter) commadParameter).getColorAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AGeometryCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AGeometryCommandParameter) commadParameter).getPathAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AParametersCommandParameter) {
						config.setRule(((AParametersCommandParameter) commadParameter).getString().getText());
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.command.fill");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			} else if ("STROKE".equals(command)) {
				StrokePathReplacementConfig config = new StrokePathReplacementConfig();
				for (PCommandParameter commadParameter : pathCommand.getCommandParameter()) {
					if (commadParameter instanceof AColorCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AColorCommandParameter) commadParameter).getColorAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AGeometryCommandParameter) {
						PathAdjustmentConfigElement pathAdjustmentElement = createPathAdjustmentElement(((AGeometryCommandParameter) commadParameter).getPathAdjustment());
						if (pathAdjustmentElement != null) {
							config.appendPathAdjustmentConfigElement(pathAdjustmentElement);
						}
					} else if (commadParameter instanceof AParametersCommandParameter) {
						config.setCap(((AParametersCommandParameter) commadParameter).getString().getText());
					} else if (commadParameter instanceof AStrokeCommandParameter) {
						config.setWidth(evaluateExpression(((AStrokeCommandParameter) commadParameter).getExpression()));
					}
				}
				ConfigurableExtension<PathReplacementExtensionRuntime<?>, PathReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getPathReplacementExtension("contextfree.path.replacement.command.stroke");
				ConfigurableExtensionReference<PathReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
				return reference; 
			}
			return null;
		}

		private FigureConfigElement createRuleFigureElement(ARuleDeclaration ruleDeclaration) throws ExtensionNotFoundException {
			RuleFigureConfig config = new RuleFigureConfig();
			config.setName(ruleDeclaration.getString().getText());
			if (ruleDeclaration.getNumber() != null) {
				config.setProbability(Float.valueOf(ruleDeclaration.getNumber().getText()));
			}
			for (PShapeReplacementDeclaration shapeReplacementDeclaration : ruleDeclaration.getShapeReplacementDeclaration()) {
				ShapeReplacementConfigElement shapeReplacementElement = createShapeReplacementElement(shapeReplacementDeclaration);
				if (shapeReplacementElement != null) {
					config.appendShapeReplacementConfigElement(shapeReplacementElement);
				}
			}
			ConfigurableExtension<FigureExtensionRuntime<?>, FigureExtensionConfig> extension = ContextFreeRegistry.getInstance().getFigureExtension("contextfree.figure.rule");
			ConfigurableExtensionReference<FigureExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			FigureConfigElement figureElement = new FigureConfigElement();
			figureElement.setExtensionReference(reference);
			return figureElement;
		}

		private ShapeReplacementConfigElement createShapeReplacementElement(PShapeReplacementDeclaration shapeReplacementDeclaration) throws ExtensionNotFoundException {
			if (shapeReplacementDeclaration instanceof ASingleShapeReplacementDeclaration) {
				return createShapeReplacementElement((ASingleShapeReplacementDeclaration) shapeReplacementDeclaration);
			} else if (shapeReplacementDeclaration instanceof AUnorderedShapeReplacementDeclaration) {
				return createShapeReplacementElement((AUnorderedShapeReplacementDeclaration) shapeReplacementDeclaration);
			} else if (shapeReplacementDeclaration instanceof AOrderedShapeReplacementDeclaration) {
				return createShapeReplacementElement((AOrderedShapeReplacementDeclaration) shapeReplacementDeclaration);
			}
			return null;
		}

		private ShapeReplacementConfigElement createShapeReplacementElement(ASingleShapeReplacementDeclaration shapeReplacementDeclaration) throws ExtensionNotFoundException {
			return createShapeReplacementElement(shapeReplacementDeclaration.getShapeReplacement());
		}

		private ShapeReplacementConfigElement createShapeReplacementElement(AUnorderedShapeReplacementDeclaration shapeReplacementDeclaration) throws ExtensionNotFoundException {
			MultiShapeReplacementConfig config = new MultiShapeReplacementConfig();
			dropSameShapeAdjustments(shapeReplacementDeclaration.getShapeAdjustment());
			config.setTimes(Integer.valueOf(shapeReplacementDeclaration.getNumber().getText()));
			for (PShapeAdjustment shapeAdjustment : shapeReplacementDeclaration.getShapeAdjustment()) {
				ShapeAdjustmentConfigElement shapeAdjustmentElement = createShapeAdjustmentElement(shapeAdjustment);
				if (shapeAdjustmentElement != null) {
					config.appendShapeAdjustmentConfigElement(shapeAdjustmentElement);
				}
			}
			PShapeReplacementBlock shapeReplacementBlock = shapeReplacementDeclaration.getShapeReplacementBlock();
			if (shapeReplacementBlock instanceof ABasicShapeReplacementBlock) {
				PShapeReplacement shapeReplacement = ((ABasicShapeReplacementBlock) shapeReplacementBlock).getShapeReplacement();
				ShapeReplacementConfigElement shapeReplacementElement = createShapeReplacementElement(shapeReplacement);
				if (shapeReplacementElement != null) {
					config.appendShapeReplacementConfigElement(shapeReplacementElement);
				}
			} else if (shapeReplacementBlock instanceof AListShapeReplacementBlock) {
				for (PShapeReplacementDeclaration shapeReplacement : ((AListShapeReplacementBlock) shapeReplacementBlock).getShapeReplacementDeclaration()) {
					ShapeReplacementConfigElement shapeReplacementElement = createShapeReplacementElement(shapeReplacement);
					if (shapeReplacementElement != null) {
						config.appendShapeReplacementConfigElement(shapeReplacementElement);
					}
				}
			}
			ConfigurableExtension<ShapeReplacementExtensionRuntime<?>, ShapeReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeReplacementExtension("contextfree.shape.replacement.multi");
			ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			ShapeReplacementConfigElement shapeReplacementElement = new ShapeReplacementConfigElement();
			shapeReplacementElement.setExtensionReference(reference);
			return shapeReplacementElement;
		}

		private ShapeReplacementConfigElement createShapeReplacementElement(AOrderedShapeReplacementDeclaration shapeReplacementDeclaration) throws ExtensionNotFoundException {
			MultiShapeReplacementConfig config = new MultiShapeReplacementConfig();
			dropSameShapeAdjustments(shapeReplacementDeclaration.getShapeAdjustment());
			Collections.sort(shapeReplacementDeclaration.getShapeAdjustment(), new PShapeAdjustmentComparator());
			config.setTimes(Integer.valueOf(shapeReplacementDeclaration.getNumber().getText()));
			for (PShapeAdjustment shapeAdjustment : shapeReplacementDeclaration.getShapeAdjustment()) {
				ShapeAdjustmentConfigElement shapeAdjustmentElement = createShapeAdjustmentElement(shapeAdjustment);
				if (shapeAdjustmentElement != null) {
					config.appendShapeAdjustmentConfigElement(shapeAdjustmentElement);
				}
			}
			PShapeReplacementBlock shapeReplacementBlock = shapeReplacementDeclaration.getShapeReplacementBlock();
			if (shapeReplacementBlock instanceof ABasicShapeReplacementBlock) {
				PShapeReplacement shapeReplacement = ((ABasicShapeReplacementBlock) shapeReplacementBlock).getShapeReplacement();
				ShapeReplacementConfigElement shapeReplacementElement = createShapeReplacementElement(shapeReplacement);
				if (shapeReplacementElement != null) {
					config.appendShapeReplacementConfigElement(shapeReplacementElement);
				}
			} else if (shapeReplacementBlock instanceof AListShapeReplacementBlock) {
				for (PShapeReplacementDeclaration shapeReplacement : ((AListShapeReplacementBlock) shapeReplacementBlock).getShapeReplacementDeclaration()) {
					ShapeReplacementConfigElement shapeReplacementElement = createShapeReplacementElement(shapeReplacement);
					if (shapeReplacementElement != null) {
						config.appendShapeReplacementConfigElement(shapeReplacementElement);
					}
				}
			}
			ConfigurableExtension<ShapeReplacementExtensionRuntime<?>, ShapeReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeReplacementExtension("contextfree.shape.replacement.multi");
			ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			ShapeReplacementConfigElement shapeReplacementElement = new ShapeReplacementConfigElement();
			shapeReplacementElement.setExtensionReference(reference);
			return shapeReplacementElement;
		}

		private ShapeReplacementConfigElement createShapeReplacementElement(PShapeReplacement shapeReplacement)	throws ExtensionNotFoundException {
			ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference = null;
			if (shapeReplacement instanceof AUnorderedShapeReplacement) {
				reference = createShapeReplacementExtensionReference((AUnorderedShapeReplacement) shapeReplacement);
			} else if (shapeReplacement instanceof AOrderedShapeReplacement) {
				reference = createShapeReplacementExtensionReference((AOrderedShapeReplacement) shapeReplacement);
			}
			ShapeReplacementConfigElement shapeReplacementElement = new ShapeReplacementConfigElement();
			shapeReplacementElement.setExtensionReference(reference);
			return shapeReplacementElement;
		}

		private ConfigurableExtensionReference<ShapeReplacementExtensionConfig> createShapeReplacementExtensionReference(AUnorderedShapeReplacement shapeReplacement)	throws ExtensionNotFoundException {
			SingleShapeReplacementConfig config = new SingleShapeReplacementConfig();
			config.setShape(shapeReplacement.getString().getText());
			for (PShapeAdjustment shapeAdjustment : shapeReplacement.getShapeAdjustment()) {
				ShapeAdjustmentConfigElement shapeAdjustmentElement = createShapeAdjustmentElement(shapeAdjustment);
				config.appendShapeAdjustmentConfigElement(shapeAdjustmentElement);
			}
			ConfigurableExtension<ShapeReplacementExtensionRuntime<?>, ShapeReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeReplacementExtension("contextfree.shape.replacement.single");
			ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private void dropSameShapeAdjustments(List<PShapeAdjustment> shapeAdjustments) {
			AFlipGeometryAdjustment lastAFlipGeometryAdjustment = null;
			ARotateGeometryAdjustment lastARotateGeometryAdjustment = null;
			ASize2GeometryAdjustment lastASize2GeometryAdjustment = null;
			ASizeGeometryAdjustment lastASizeGeometryAdjustment = null;
			ASkewGeometryAdjustment lastASkewGeometryAdjustment = null;
			AXGeometryAdjustment lastAXGeometryAdjustment = null;
			AYGeometryAdjustment lastAYGeometryAdjustment = null;
			for (int i = shapeAdjustments.size() - 1; i >= 0; i--) {
				PShapeAdjustment shapeAdjustment = shapeAdjustments.get(i);
				if (shapeAdjustment instanceof AGeometryShapeAdjustment) {
					PGeometryAdjustment geometryAdjustment = ((AGeometryShapeAdjustment) shapeAdjustment).getGeometryAdjustment();
					if (geometryAdjustment instanceof AFlipGeometryAdjustment) {
						if (lastAFlipGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastAFlipGeometryAdjustment = (AFlipGeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof ARotateGeometryAdjustment) {
						if (lastARotateGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastARotateGeometryAdjustment = (ARotateGeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof ASize2GeometryAdjustment) {
						if (lastASize2GeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastASize2GeometryAdjustment = (ASize2GeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof ASizeGeometryAdjustment) {
						if (lastASizeGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastASizeGeometryAdjustment = (ASizeGeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof ASkewGeometryAdjustment) {
						if (lastASkewGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastASkewGeometryAdjustment = (ASkewGeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof AXGeometryAdjustment) {
						if (lastAXGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastAXGeometryAdjustment = (AXGeometryAdjustment) geometryAdjustment;
						}
					} else if (geometryAdjustment instanceof AYGeometryAdjustment) {
						if (lastAYGeometryAdjustment != null) {
							shapeAdjustments.remove(shapeAdjustment);
						} else {
							lastAYGeometryAdjustment = (AYGeometryAdjustment) geometryAdjustment;
						}
					}
				}
			}
		}
		
		private void dropSamePathAdjustments(List<PPathAdjustment> pathAdjustments) {
			AFlipPathAdjustment lastAFlipPathAdjustment = null;
			ARotatePathAdjustment lastARotatePathAdjustment = null;
			ASize2PathAdjustment lastASize2PathAdjustment = null;
			ASizePathAdjustment lastASizePathAdjustment = null;
			ASkewPathAdjustment lastASkewPathAdjustment = null;
			AXPathAdjustment lastAXPathAdjustment = null;
			AYPathAdjustment lastAYPathAdjustment = null;
			for (int i = pathAdjustments.size() - 1; i >= 0; i--) {
				PPathAdjustment pathAdjustment = pathAdjustments.get(i);
				if (pathAdjustment instanceof AFlipPathAdjustment) {
					if (lastAFlipPathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastAFlipPathAdjustment = (AFlipPathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof ARotatePathAdjustment) {
					if (lastARotatePathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastARotatePathAdjustment = (ARotatePathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof ASize2PathAdjustment) {
					if (lastASize2PathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastASize2PathAdjustment = (ASize2PathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof ASizePathAdjustment) {
					if (lastASizePathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastASizePathAdjustment = (ASizePathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof ASkewPathAdjustment) {
					if (lastASkewPathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastASkewPathAdjustment = (ASkewPathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof AXPathAdjustment) {
					if (lastAXPathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastAXPathAdjustment = (AXPathAdjustment) pathAdjustment;
					}
				} else if (pathAdjustment instanceof AYPathAdjustment) {
					if (lastAYPathAdjustment != null) {
						pathAdjustments.remove(pathAdjustment);
					} else {
						lastAYPathAdjustment = (AYPathAdjustment) pathAdjustment;
					}
				}
			}
		}

		private ConfigurableExtensionReference<ShapeReplacementExtensionConfig> createShapeReplacementExtensionReference(AOrderedShapeReplacement shapeReplacement)	throws ExtensionNotFoundException {
			SingleShapeReplacementConfig config = new SingleShapeReplacementConfig();
			dropSameShapeAdjustments(shapeReplacement.getShapeAdjustment());
			Collections.sort(shapeReplacement.getShapeAdjustment(), new PShapeAdjustmentComparator());
			config.setShape(shapeReplacement.getString().getText());
			for (PShapeAdjustment shapeAdjustment : shapeReplacement.getShapeAdjustment()) {
				ShapeAdjustmentConfigElement shapeAdjustmentElement = createShapeAdjustmentElement(shapeAdjustment);
				config.appendShapeAdjustmentConfigElement(shapeAdjustmentElement);
			}
			ConfigurableExtension<ShapeReplacementExtensionRuntime<?>, ShapeReplacementExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeReplacementExtension("contextfree.shape.replacement.single");
			ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ShapeAdjustmentConfigElement createShapeAdjustmentElement(PShapeAdjustment shapeAdjustment) throws ExtensionNotFoundException {
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = null;
			if (shapeAdjustment instanceof AColorShapeAdjustment) {
				reference = getShapeAdjustmentExtensionReference((AColorShapeAdjustment) shapeAdjustment);
			} else if (shapeAdjustment instanceof AGeometryShapeAdjustment) {
				reference = getShapeAdjustmentExtensionReference((AGeometryShapeAdjustment) shapeAdjustment);
			}
			ShapeAdjustmentConfigElement shapeAdjustmentElement = new ShapeAdjustmentConfigElement();
			shapeAdjustmentElement.setExtensionReference(reference);
			return shapeAdjustmentElement;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AColorShapeAdjustment shapeAdjustment) throws ExtensionNotFoundException {
			PColorAdjustment colorAdjustment = shapeAdjustment.getColorAdjustment();
			if (colorAdjustment instanceof ACurrentColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ACurrentColorAdjustment) colorAdjustment);
			} else if (colorAdjustment instanceof ATargetColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ATargetColorAdjustment) colorAdjustment);
			} else {
				return null;
			}
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ACurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			PCurrentColorAdjustment currentColorAdjustment = colorAdjustment.getCurrentColorAdjustment();
			if (currentColorAdjustment instanceof AAlphaCurrentColorAdjustment) {
				return getShapeAdjustmentExtensionReference((AAlphaCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof ABrightnessCurrentColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ABrightnessCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof ASaturationCurrentColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ASaturationCurrentColorAdjustment) currentColorAdjustment);
			} else if (currentColorAdjustment instanceof AHueCurrentColorAdjustment) {
				return getShapeAdjustmentExtensionReference((AHueCurrentColorAdjustment) currentColorAdjustment);
			} else {
				return null;
			}
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ATargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			PTargetColorAdjustment targetColorAdjustment = colorAdjustment.getTargetColorAdjustment();
			if (targetColorAdjustment instanceof AAlphaTargetColorAdjustment) {
				return getShapeAdjustmentExtensionReference((AAlphaTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof ABrightnessTargetColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ABrightnessTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof ASaturationTargetColorAdjustment) {
				return getShapeAdjustmentExtensionReference((ASaturationTargetColorAdjustment) targetColorAdjustment);
			} else if (targetColorAdjustment instanceof AHueTargetColorAdjustment) {
				return getShapeAdjustmentExtensionReference((AHueTargetColorAdjustment) targetColorAdjustment);
			} else {
				return null;
			}
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AGeometryShapeAdjustment shapeAdjustment) throws ExtensionNotFoundException {
			PGeometryAdjustment geometryAdjustment = shapeAdjustment.getGeometryAdjustment();
			if (geometryAdjustment instanceof AFlipGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((AFlipGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof ARotateGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((ARotateGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof ASize3GeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((ASize3GeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof ASize2GeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((ASize2GeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof ASizeGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((ASizeGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof ASkewGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((ASkewGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof AXGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((AXGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof AYGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((AYGeometryAdjustment) geometryAdjustment);
			} else if (geometryAdjustment instanceof AZGeometryAdjustment) {
				return getShapeAdjustmentExtensionReference((AZGeometryAdjustment) geometryAdjustment);
			} else {
				return null;
			}
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AAlphaCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentAlphaShapeAdjustmentConfig config = new CurrentAlphaShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.currentAlpha");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AAlphaTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetAlphaShapeAdjustmentConfig config = new TargetAlphaShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.targetAlpha");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ABrightnessCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentBrightnessShapeAdjustmentConfig config = new CurrentBrightnessShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.currentBrightness");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ABrightnessTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetBrightnessShapeAdjustmentConfig config = new TargetBrightnessShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.targetBrightness");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASaturationCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentSaturationShapeAdjustmentConfig config = new CurrentSaturationShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.currentSaturation");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASaturationTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetSaturationShapeAdjustmentConfig config = new TargetSaturationShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.targetSaturation");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AHueCurrentColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			CurrentHueShapeAdjustmentConfig config = new CurrentHueShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			config.setTarget(colorAdjustment.getBar() != null);
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.currentHue");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AHueTargetColorAdjustment colorAdjustment) throws ExtensionNotFoundException {
			TargetHueShapeAdjustmentConfig config = new TargetHueShapeAdjustmentConfig();
			config.setValue(evaluateExpression(colorAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.color.targetHue");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AXGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			XShapeAdjustmentConfig config = new XShapeAdjustmentConfig();
			config.setValue(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.x");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AYGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			YShapeAdjustmentConfig config = new YShapeAdjustmentConfig();
			config.setValue(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.y");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AZGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			ZShapeAdjustmentConfig config = new ZShapeAdjustmentConfig();
			config.setValue(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.z");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}

		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASizeGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			SizeShapeAdjustmentConfig config = new SizeShapeAdjustmentConfig();
			config.setScale(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.size");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASize2GeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			Size2ShapeAdjustmentConfig config = new Size2ShapeAdjustmentConfig();
			config.setScaleX(evaluateExpression(geometryAdjustment.getFirstExpression()));
			config.setScaleY(evaluateExpression(geometryAdjustment.getSecondExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.size2");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASize3GeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			Size3ShapeAdjustmentConfig config = new Size3ShapeAdjustmentConfig();
			config.setScaleX(evaluateExpression(geometryAdjustment.getFirstExpression()));
			config.setScaleY(evaluateExpression(geometryAdjustment.getSecondExpression()));
			config.setScaleZ(evaluateExpression(geometryAdjustment.getThirdExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.size3");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ASkewGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			SkewShapeAdjustmentConfig config = new SkewShapeAdjustmentConfig();
			config.setShearX(evaluateExpression(geometryAdjustment.getFirstExpression()));
			config.setShearY(evaluateExpression(geometryAdjustment.getSecondExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.skew");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(AFlipGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			FlipShapeAdjustmentConfig config = new FlipShapeAdjustmentConfig();
			config.setAngle(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.flip");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> getShapeAdjustmentExtensionReference(ARotateGeometryAdjustment geometryAdjustment) throws ExtensionNotFoundException {
			RotateShapeAdjustmentConfig config = new RotateShapeAdjustmentConfig();
			config.setAngle(evaluateExpression(geometryAdjustment.getExpression()));
			ConfigurableExtension<ShapeAdjustmentExtensionRuntime<?>, ShapeAdjustmentExtensionConfig> extension = ContextFreeRegistry.getInstance().getShapeAdjustmentExtension("contextfree.shape.adjustment.geometry.rotate");
			ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> reference = extension.createConfigurableExtensionReference(config);
			return reference;
		}
		
		private class PCommandParameterComparator implements Comparator<PCommandParameter> {
			@Override
			public int compare(PCommandParameter o1, PCommandParameter o2) {
				int op1 = 0;
				int op2 = 0;
				if (o1 instanceof AColorCommandParameter) {
					op1 = 1;
				} else if (o1 instanceof AGeometryCommandParameter) {
					op1 = 2;
				}
				if (o2 instanceof AColorCommandParameter) {
					op2 = 1;
				} else if (o2 instanceof AGeometryCommandParameter) {
					op2 = 2;
				}
				if (op1 == op2) {
					if (o1 instanceof AColorCommandParameter) {
						PColorAdjustment ca1 = ((AColorCommandParameter) o1).getColorAdjustment();
						PColorAdjustment ca2 = ((AColorCommandParameter) o2).getColorAdjustment();
						int cap1 = 0;
						int cap2 = 0;
						if (ca1 instanceof ACurrentColorAdjustment) {
							cap1 = 1; 
						} else if (ca1 instanceof ATargetColorAdjustment) {
							cap1 = 2; 
						} 
						if (ca2 instanceof ACurrentColorAdjustment) {
							cap2 = 1; 
						} else if (ca2 instanceof ATargetColorAdjustment) {
							cap2 = 2; 
						} 
						if (cap1 == cap2) {
							if (ca1 instanceof ACurrentColorAdjustment) {
								PCurrentColorAdjustment cca1 = ((ACurrentColorAdjustment) ca1).getCurrentColorAdjustment();
								PCurrentColorAdjustment cca2 = ((ACurrentColorAdjustment) ca2).getCurrentColorAdjustment();
								int ccap1 = 0;
								int ccap2 = 0;
								if (cca1 instanceof AHueCurrentColorAdjustment) {
									ccap1 = 1;
								} else if (cca1 instanceof ABrightnessCurrentColorAdjustment) {
									ccap1 = 2;
								} else if (cca1 instanceof ASaturationCurrentColorAdjustment) {
									ccap1 = 3;
								} else if (cca1 instanceof AAlphaCurrentColorAdjustment) {
									ccap1 = 4;
								}
								if (cca2 instanceof AHueCurrentColorAdjustment) {
									ccap2 = 1;
								} else if (cca1 instanceof ABrightnessCurrentColorAdjustment) {
									ccap2 = 2;
								} else if (cca1 instanceof ASaturationCurrentColorAdjustment) {
									ccap2 = 3;
								} else if (cca1 instanceof AAlphaCurrentColorAdjustment) {
									ccap2 = 4;
								}
								return ccap1 - ccap2; 
							} else if (ca1 instanceof ATargetColorAdjustment) {
								PTargetColorAdjustment tca1 = ((ATargetColorAdjustment) ca1).getTargetColorAdjustment();
								PTargetColorAdjustment tca2 = ((ATargetColorAdjustment) ca2).getTargetColorAdjustment();
								int tcap1 = 0;
								int tcap2 = 0;
								if (tca1 instanceof AHueTargetColorAdjustment) {
									tcap1 = 1;
								} else if (tca1 instanceof ABrightnessTargetColorAdjustment) {
									tcap1 = 2;
								} else if (tca1 instanceof ASaturationTargetColorAdjustment) {
									tcap1 = 3;
								} else if (tca1 instanceof AAlphaTargetColorAdjustment) {
									tcap1 = 4;
								}
								if (tca2 instanceof AHueTargetColorAdjustment) {
									tcap2 = 1;
								} else if (tca1 instanceof ABrightnessTargetColorAdjustment) {
									tcap2 = 2;
								} else if (tca1 instanceof ASaturationTargetColorAdjustment) {
									tcap2 = 3;
								} else if (tca1 instanceof AAlphaTargetColorAdjustment) {
									tcap2 = 4;
								}
								return tcap1 - tcap2; 
							}
							return 0;
						}
						return cap1 - cap2; 
					} else if (o1 instanceof AGeometryCommandParameter) {
						PPathAdjustment ga1 = ((AGeometryCommandParameter) o1).getPathAdjustment();
						PPathAdjustment ga2 = ((AGeometryCommandParameter) o2).getPathAdjustment();
						int gap1 = 0;
						int gap2 = 0;
						if (ga1 instanceof AXPathAdjustment) {
							gap1 = 1; 
						} else if (ga1 instanceof AYPathAdjustment) {
							gap1 = 2; 
						} else if (ga1 instanceof ARotatePathAdjustment) {
							gap1 = 3; 
						} else if (ga1 instanceof ASizePathAdjustment) {
							gap1 = 4; 
						} else if (ga1 instanceof ASize2PathAdjustment) {
							gap1 = 5; 
						} else if (ga1 instanceof ASkewPathAdjustment) {
							gap1 = 6; 
						} else if (ga1 instanceof AFlipPathAdjustment) {
							gap1 = 7; 
						} 
						if (ga2 instanceof AXPathAdjustment) {
							gap2 = 1; 
						} else if (ga2 instanceof AYPathAdjustment) {
							gap2 = 2; 
						} else if (ga2 instanceof ARotatePathAdjustment) {
							gap2 = 3; 
						} else if (ga2 instanceof ASizePathAdjustment) {
							gap2 = 4; 
						} else if (ga2 instanceof ASize2PathAdjustment) {
							gap2 = 5; 
						} else if (ga2 instanceof ASkewPathAdjustment) {
							gap2 = 6; 
						} else if (ga2 instanceof AFlipPathAdjustment) {
							gap2 = 7; 
						} 
						return gap1 - gap2;
					}
					return 0;
				}
				return op1 - op2;
			}
		}
	
		private class PShapeAdjustmentComparator implements Comparator<PShapeAdjustment> {
			@Override
			public int compare(PShapeAdjustment o1, PShapeAdjustment o2) {
				int op1 = 0;
				int op2 = 0;
				if (o1 instanceof AColorShapeAdjustment) {
					op1 = 1;
				} else if (o1 instanceof AGeometryShapeAdjustment) {
					op1 = 2;
				}
				if (o2 instanceof AColorShapeAdjustment) {
					op2 = 1;
				} else if (o2 instanceof AGeometryShapeAdjustment) {
					op2 = 2;
				}
				if (op1 == op2) {
					if (o1 instanceof AColorShapeAdjustment) {
						PColorAdjustment ca1 = ((AColorShapeAdjustment) o1).getColorAdjustment();
						PColorAdjustment ca2 = ((AColorShapeAdjustment) o2).getColorAdjustment();
						int cap1 = 0;
						int cap2 = 0;
						if (ca1 instanceof ACurrentColorAdjustment) {
							cap1 = 1; 
						} else if (ca1 instanceof ATargetColorAdjustment) {
							cap1 = 2; 
						} 
						if (ca2 instanceof ACurrentColorAdjustment) {
							cap2 = 1; 
						} else if (ca2 instanceof ATargetColorAdjustment) {
							cap2 = 2; 
						} 
						if (cap1 == cap2) {
							if (ca1 instanceof ACurrentColorAdjustment) {
								PCurrentColorAdjustment cca1 = ((ACurrentColorAdjustment) ca1).getCurrentColorAdjustment();
								PCurrentColorAdjustment cca2 = ((ACurrentColorAdjustment) ca2).getCurrentColorAdjustment();
								int ccap1 = 0;
								int ccap2 = 0;
								if (cca1 instanceof AHueCurrentColorAdjustment) {
									ccap1 = 1;
								} else if (cca1 instanceof ABrightnessCurrentColorAdjustment) {
									ccap1 = 2;
								} else if (cca1 instanceof ASaturationCurrentColorAdjustment) {
									ccap1 = 3;
								} else if (cca1 instanceof AAlphaCurrentColorAdjustment) {
									ccap1 = 4;
								}
								if (cca2 instanceof AHueCurrentColorAdjustment) {
									ccap2 = 1;
								} else if (cca1 instanceof ABrightnessCurrentColorAdjustment) {
									ccap2 = 2;
								} else if (cca1 instanceof ASaturationCurrentColorAdjustment) {
									ccap2 = 3;
								} else if (cca1 instanceof AAlphaCurrentColorAdjustment) {
									ccap2 = 4;
								}
								return ccap1 - ccap2; 
							} else if (ca1 instanceof ATargetColorAdjustment) {
								PTargetColorAdjustment tca1 = ((ATargetColorAdjustment) ca1).getTargetColorAdjustment();
								PTargetColorAdjustment tca2 = ((ATargetColorAdjustment) ca2).getTargetColorAdjustment();
								int tcap1 = 0;
								int tcap2 = 0;
								if (tca1 instanceof AHueTargetColorAdjustment) {
									tcap1 = 1;
								} else if (tca1 instanceof ABrightnessTargetColorAdjustment) {
									tcap1 = 2;
								} else if (tca1 instanceof ASaturationTargetColorAdjustment) {
									tcap1 = 3;
								} else if (tca1 instanceof AAlphaTargetColorAdjustment) {
									tcap1 = 4;
								}
								if (tca2 instanceof AHueTargetColorAdjustment) {
									tcap2 = 1;
								} else if (tca1 instanceof ABrightnessTargetColorAdjustment) {
									tcap2 = 2;
								} else if (tca1 instanceof ASaturationTargetColorAdjustment) {
									tcap2 = 3;
								} else if (tca1 instanceof AAlphaTargetColorAdjustment) {
									tcap2 = 4;
								}
								return tcap1 - tcap2; 
							}
							return 0;
						}
						return cap2 - cap1; 
					} else if (o1 instanceof AGeometryShapeAdjustment) {
						PGeometryAdjustment ga1 = ((AGeometryShapeAdjustment) o1).getGeometryAdjustment();
						PGeometryAdjustment ga2 = ((AGeometryShapeAdjustment) o2).getGeometryAdjustment();
						int gap1 = 0;
						int gap2 = 0;
						if (ga1 instanceof AXGeometryAdjustment) {
							gap1 = 1; 
						} else if (ga1 instanceof AYGeometryAdjustment) {
							gap1 = 2; 
						} else if (ga1 instanceof ARotateGeometryAdjustment) {
							gap1 = 3; 
						} else if (ga1 instanceof ASizeGeometryAdjustment) {
							gap1 = 4; 
						} else if (ga1 instanceof ASize2GeometryAdjustment) {
							gap1 = 5; 
						} else if (ga1 instanceof ASkewGeometryAdjustment) {
							gap1 = 6; 
						} else if (ga1 instanceof AFlipGeometryAdjustment) {
							gap1 = 7; 
						} 
						if (ga2 instanceof AXGeometryAdjustment) {
							gap2 = 1; 
						} else if (ga2 instanceof AYGeometryAdjustment) {
							gap2 = 2; 
						} else if (ga2 instanceof ARotateGeometryAdjustment) {
							gap2 = 3; 
						} else if (ga2 instanceof ASizeGeometryAdjustment) {
							gap2 = 4; 
						} else if (ga2 instanceof ASize2GeometryAdjustment) {
							gap2 = 5; 
						} else if (ga2 instanceof ASkewGeometryAdjustment) {
							gap2 = 6; 
						} else if (ga2 instanceof AFlipGeometryAdjustment) {
							gap2 = 7; 
						} 
						return gap1 - gap2;
					}
					return 0;
				}
				return op1 - op2;
			}
		}
	}
	
	private class PPathAdjustmentComparator implements Comparator<PPathAdjustment> {
		@Override
		public int compare(PPathAdjustment ga1, PPathAdjustment ga2) {
			int gap1 = 0;
			int gap2 = 0;
			if (ga1 instanceof AXPathAdjustment) {
				gap1 = 1; 
			} else if (ga1 instanceof AYPathAdjustment) {
				gap1 = 2; 
			} else if (ga1 instanceof ARotatePathAdjustment) {
				gap1 = 3; 
			} else if (ga1 instanceof ASizePathAdjustment) {
				gap1 = 4; 
			} else if (ga1 instanceof ASize2PathAdjustment) {
				gap1 = 5; 
			} else if (ga1 instanceof ASkewPathAdjustment) {
				gap1 = 6; 
			} else if (ga1 instanceof AFlipPathAdjustment) {
				gap1 = 7; 
			} 
			if (ga2 instanceof AXPathAdjustment) {
				gap2 = 1; 
			} else if (ga2 instanceof AYPathAdjustment) {
				gap2 = 2; 
			} else if (ga2 instanceof ARotatePathAdjustment) {
				gap2 = 3; 
			} else if (ga2 instanceof ASizePathAdjustment) {
				gap2 = 4; 
			} else if (ga2 instanceof ASize2PathAdjustment) {
				gap2 = 5; 
			} else if (ga2 instanceof ASkewPathAdjustment) {
				gap2 = 6; 
			} else if (ga2 instanceof AFlipPathAdjustment) {
				gap2 = 7; 
			} 
			return gap1 - gap2;
		}
	}
}
