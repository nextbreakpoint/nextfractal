/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.*;

import com.nextbreakpoint.nextfractal.contextfree.core.Rand64;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ShapeType;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

public class CFDGDriver {
	private CFDG cfdg = new CFDG(this);
	private Stack<ASTRepContainer> containerStack = new Stack<>();
	private ASTRepContainer paramDecls = new ASTRepContainer(this);
	private Map<String, Integer> flagNames = new HashMap<String, Integer>();
	private List<CFStackRule> longLivedParams = new ArrayList<>();
	private Stack<String> fileNames = new Stack<>();
	private Stack<String> filesToLoad = new Stack<>();
	private Stack<CharStream> streamsToLoad = new Stack<>();
	private Stack<Boolean> includeNamespace = new Stack<>();
	private Stack<ASTSwitch> switchStack = new Stack<>();
	private String currentNameSpace = "";
	private String currentPath;
	private String maybeVersion;
	private int currentShape;
	private int includeDepth;
	private int localStackDepth;
	private boolean allowOverlap;
	private boolean inPathContainer;
	private Rand64 seed = new Rand64();
	private boolean errorOccured;
	private double maxNatual = 1000.0;
	private int pathCount = -1;

	public CFDGDriver() {
		containerStack.add(new ASTRepContainer(this));
		currentShape = -1;
		pathCount = 1;
	}
	
	protected void warning(String message, Token location) {
		Logger.warning(message, location);
	}
	
	protected void error(String message, Token location) {
		Logger.error(message, location);
	}
	
	protected boolean isPrimeShape(int nameIndex) {
		return nameIndex < 4;
	}

	public int stringToShape(String name, boolean colonsAllowed, Token location) {
		checkName(name, colonsAllowed, location);
		if (currentNameSpace.length() == 0) {
			return cfdg.encodeShapeName(name);
		}
		int index = Collections.binarySearch(PrimShape.getShapeNames(), name);
		String n = currentNameSpace + name;
		if (index != -1 && cfdg.tryEncodeShapeName(n) == -1) {
			return cfdg.encodeShapeName(name);
		} else {
			return cfdg.encodeShapeName(n);
		}
	}

	public String shapeToString(int shape) {
		return cfdg.decodeShapeName(shape);
	}

	public void includeFile(String fileName, Token location) {
		try {
			String path = relativeFilePath(currentPath, fileName);
			ANTLRFileStream is = new ANTLRFileStream(path);
			fileNames.push(path);
			currentPath = path;
			filesToLoad.push(currentPath);
			streamsToLoad.push(is);
			includeNamespace.push(Boolean.FALSE);
			pathCount++;
			includeDepth++;
			currentShape = -1;
			setShape(null, false, location);
			warning("Reading rules file " + path, location);
		} catch (Exception e) {
			error(e.getMessage(), location);
		}
	}
	
	public boolean endInclude(Token location) {
		boolean endOfInput = includeDepth == 0;
		try {
			setShape(null, false, location);
			includeDepth--;
			if (filesToLoad.isEmpty()) {
				return endOfInput;
			}
			if (includeNamespace.peek()) {
				popNameSpace();
			}
			streamsToLoad.pop();
			filesToLoad.pop();
			includeNamespace.pop();
			currentPath = filesToLoad.isEmpty() ? null : filesToLoad.peek();
		} catch (Exception e) {
			error(e.getMessage(), location);
		}
		return endOfInput;
	}
	
	public void setShape(String name, Token location) {
		setShape(name, false, location);
	}
	
	public void setShape(String name, boolean isPath, Token location) {
		if (name == null) {
			currentShape = -1;
			return;
		}
		currentShape = stringToShape(name, false, location);
		ASTDefine def = cfdg.findFunction(currentShape);
		if (def != null) {
			error("There is a function with the same name as this shape: " + def.getLocation(), location);
			return;
		}
		String err = cfdg.setShapeParams(currentShape, paramDecls, paramDecls.getStackCount(), isPath);
		if (err != null) {
			errorOccured = true;
			error("cannot set shape params: " + err, location);
		}
		localStackDepth -= paramDecls.getStackCount();
		paramDecls.getParameters().clear();
		paramDecls.setStackCount(0);
	}
	
	public void addRule(ASTRule rule) {
		boolean isShapeItem = rule.getNameIndex() == -1;
		if (isShapeItem) {
			rule.setNameIndex(currentShape);
		} else {
			currentShape = -1;
		}
		if (rule.getNameIndex() == -1) {
			error("Shape rules/paths must follow a shape declaration", rule.getLocation());
		}
		ShapeType type = cfdg.getShapeType(rule.getNameIndex());
		if ((rule.isPath() && type == ShapeType.RuleType) || (!rule.isPath() && type == ShapeType.PathType)) {
			error("Cannot mix rules and shapes with the same name", rule.getLocation());
		}
		boolean matchesShape = cfdg.addRule(rule);
		if (!isShapeItem && matchesShape) {
			error("Rule/path name matches existing shape name", rule.getLocation());
		}
	}

	public void nextParameterDecl(String type, String name, Token location) {
		int nameIndex = stringToShape(name, false, location);
		checkVariableName(nameIndex, true, location);
		paramDecls.addParameter(type, nameIndex, location);
		ASTParameter param = paramDecls.getParameters().get(paramDecls.getParameters().size() - 1);
		param.setStackIndex(localStackDepth);
		paramDecls.setStackCount(paramDecls.getStackCount() + param.getTupleSize());
		localStackDepth += param.getTupleSize();
	}

	public ASTDefine makeDefinition(String name, boolean isFunction, Token location) {
		if (name.startsWith("CF::")) {
			if (isFunction) {
				error("Configuration parameters cannot be functions", location);
				return null;
			}
			if (containerStack.lastElement().isGlobal()) {
				error("Configuration parameters must be at global scope", location);
				return null;
			}
			ASTDefine def = new ASTDefine(this, name, location);
			def.setConfigDepth(includeDepth);
			def.setDefineType(DefineType.ConfigDefine);
			return def;
		}
		if (FuncType.byName(name) != FuncType.NotAFunction) {
			error("Internal function names are reserved", location);
			return null;
		}
		int nameIndex = stringToShape(name, false, location);
		ASTDefine def = cfdg.findFunction(nameIndex);
		if (def != null) {
			error("Definition with same name as user function: " + def.getLocation(), location);
			return null;
		}
		checkVariableName(nameIndex, false, location);
		def = new ASTDefine(this, name, location);
		def.getShapeSpecifier().setShapeType(nameIndex);
		if (isFunction) {
			for (ASTParameter param : paramDecls.getParameters()) {
				param.setLocality(Locality.PureNonlocal);
			}
			def.getParameters().clear();
			def.getParameters().addAll(paramDecls.getParameters());
			def.setStackCount(paramDecls.getStackCount());
			def.setDefineType(DefineType.FunctionDefine);
			localStackDepth -= paramDecls.getStackCount();
			paramDecls.setStackCount(0);
			cfdg.declareFunction(nameIndex, def);
		} else {
			containerStack.lastElement().addDefParameter(nameIndex, def, location);
		}
		return def;
	}
	
	public void makeConfig(ASTDefine cfg) {
		if (cfg.getName().equals(CFG.Impure.getName())) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::Impure requires a constant numeric expression", cfg.getLocation());
			} else {
				ASTParameter.Impure = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals(CFG.AllowOverlap.getName())) {
			double[] v = new double[] { 0.0 };
			if (cfg.getExp() != null || cfg.getExp().isConstant() || cfg.getExp().evaluate(v, 1, null) != 1) {
				error("CF::AllowOverlap requires a constant numeric expression", cfg.getLocation());
			} else {
				allowOverlap = v[0] != 0.0;
			}
		}
		if (cfg.getName().equals(CFG.StartShape.getName()) && cfg.getExp() != null && !(cfg.getExp() instanceof ASTStartSpecifier)) {
			ASTRuleSpecifier rule = null;
			ASTModification mod = null;
			List<ASTExpression> specAndMod = extract(cfg.getExp());
			switch (specAndMod.size()) {
				case 2:
					if (!(specAndMod.get(1) instanceof ASTModification)) {
						error("CF::StartShape second term must be a modification", cfg.getLocation());
					} else {
						mod = (ASTModification) specAndMod.get(1);
					}
					break;
	
				case 1:
					if (!(specAndMod.get(0) instanceof ASTRuleSpecifier)) {
						error("CF::StartShape must start with a shape specification", cfg.getLocation());
					} else {
						rule = (ASTRuleSpecifier) specAndMod.get(0);
					}
					break;
					
				default:
					error("CF::StartShape expression must have the form shape_spec or shape_spec, modification", cfg.getLocation());
					break;
			}
			if (mod == null) {
				mod = new ASTModification(this, cfg.getLocation());
			}
			cfg.setExp(new ASTStartSpecifier(this, rule, mod, cfg.getLocation()));
		}
		ASTExpression current = cfg.getExp();
		if (!cfdg.addParameter(cfg.getName(), cfg.getExp(), cfg.getConfigDepth())) {
			error("Unknown configuration parameter", cfg.getLocation());
		}
		if (cfg.getName().equals(CFG.MaxNatural.getName())) {
			ASTExpression max = cfdg.hasParameter(CFG.MaxNatural);
			if (max != current) {
				return;
			}
			double[] v = new double[] { -1.0 };
			if (max == null || !max.isConstant() || max.getType() != ExpType.NumericType || max.evaluate(v, 1, null) != 1) {
				error("CF::MaxNatural requires a constant numeric expression", cfg.getLocation());
			} else if (v[0] < 1.0 || v[0] > 9007199254740992.0) {
				error(v[0] < 1.0 ? "CF::MaxNatural must be >= 1" : "CF::MaxNatural must be < 9007199254740992", cfg.getLocation());
			} else {
				maxNatual = v[0];
			}
		}
	}
	
	private List<ASTExpression> extract(ASTExpression exp) {
		if (exp instanceof ASTCons) {
			return ((ASTCons)exp).getChildren();
		} else {
			List<ASTExpression> ret = new ArrayList<ASTExpression>();
			ret.add(exp);
			return ret;
		}
	}

	public ASTExpression makeVariable(String name, Token location) {
		Integer flagItem = flagNames.get(name);
		if (flagItem != null) {
			ASTReal flag = new ASTReal(flagItem, location);
			flag.setType(ExpType.FlagType);
			return flag;
		}
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved", location);
			return new ASTExpression(location);
		}
		if (FuncType.byName(name) != FuncType.NotAFunction) {
			error("Internal function names are reserved", location);
			return new ASTExpression(location);
		}
		int varNum = stringToShape(name, true, location);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return new ASTRuleSpecifier(this, varNum, name, null, cfdg.getShapeParams(currentShape), location);
		}
		return new ASTVariable(this, varNum, name, location);
	}

	public ASTExpression makeArray(String name, ASTExpression args, Token location) {
		if (name.startsWith("CF::")) {
			error("Configuration parameter names are reserved", location);
			return args;
		}
		int varNum = stringToShape(name, true, location);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(varNum, isGlobal);
		if (bound == null) {
			return args;
		}
		return new ASTArray(this, varNum, args, "", location);
	}

	public ASTExpression makeLet(ASTRepContainer vars, ASTExpression exp, Token location) {
		int nameIndex = stringToShape("let", false, location);
		ASTDefine def = new ASTDefine(this, "let", location);
		def.getShapeSpecifier().setShapeType(nameIndex);
		def.setExp(exp);
		def.setDefineType(DefineType.LetDefine);
		return new ASTLet(this, vars, def, location);
	}

	public ASTRuleSpecifier makeRuleSpec(String name, ASTExpression args, Token location) {
			return makeRuleSpec(name, args, null, false, location);
	}
	
	public ASTRuleSpecifier makeRuleSpec(String name, ASTExpression args, ASTModification mod, boolean makeStart, Token location) {
		if (name.equals("if") || name.equals("let") || name.equals("select")) {
			if (name.equals("select")) {
				args = new ASTSelect(args, false, location);
			}
			if (makeStart) {
				return new ASTStartSpecifier(this, args, mod, location);
			} else {
				return new ASTRuleSpecifier(this, args, location);
			}
		}
		int nameIndex = stringToShape(name, true, location);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null && args != null && args.getType() == ExpType.ReuseType && !makeStart && isGlobal && nameIndex == currentShape) {
			error("Shape name binds to global variable and current shape, using current shape", location);
		}
		if (bound != null && bound.isParameter() && bound.getType() == ExpType.RuleType) {
			return new ASTRuleSpecifier(this, nameIndex, name, location);
		}
		ASTRuleSpecifier ret = null;
		cfdg.setShapeHasNoParam(nameIndex, args);
		if (makeStart) {
			ret = new ASTStartSpecifier(this, nameIndex, name, args, mod, location);
		} else {
			ret = new ASTRuleSpecifier(this, nameIndex, name, args, cfdg.getShapeParams(currentShape), location);
		}
		if (ret.getArguments() != null && ret.getArguments().getType() == ExpType.ReuseType) {
			if (makeStart) {
				error("Startshape cannot reuse parameters", location);
			} else if (nameIndex == currentShape)  {
				ret.setArgSouce(ArgSource.SimpleArgs);
				ret.setTypeSignature(ret.getTypeSignature());
			}
		}
		return ret;
	}

	public void makeModTerm(ASTModification dest, ASTModTerm t, Token location) {
		if (t == null) {
			return;
		}
		if (t.getModType() == ModType.time) {
			timeWise();
		}
		if (t.getModType() == ModType.sat || t.getModType() == ModType.satTarg) {
			inColor();
		}
		if ("CFDG3".equals(getMaybeVersion()) && t.getModType().getType() >= ModType.hueTarg.getType() && t.getModType().getType() <= ModType.alphaTarg.getType()) {
			error("Color target feature unavailable in v3 syntax", dest.getLocation());
		}
		dest.getModExp().add(t);
	}
	
	public ASTReplacement makeElement(String command, ASTModification mods, ASTExpression params, boolean subPath, Token location) {
		if (inPathContainer && !subPath && (command.equals("FILL") || command.equals("STROKE"))) {
			return new ASTPathCommand(this, command, mods, params, location);
		}
		ASTRuleSpecifier ruleSpecifier = makeRuleSpec(command, params, null, false, location);
		RepElemType elemType = RepElemType.replacement;
		if (inPathContainer) {
			boolean isGlobal = false;
			ASTParameter bound = findExpression(ruleSpecifier.getShapeType(), isGlobal);
			if (!subPath) {
				error("Replacements are not allowed in paths", location);
			} else if (ruleSpecifier.getArgSource() == ArgSource.StackArgs || ruleSpecifier.getArgSource() == ArgSource.ShapeArgs) {
	            // Parameter subpaths must be all ops, but we must checkParam at runtime
				elemType = RepElemType.op;
			} else if (cfdg.getShapeType(ruleSpecifier.getShapeType()) == ShapeType.PathType) {
				ASTRule rule = cfdg.findRule(ruleSpecifier.getShapeType());
				if (rule != null) {
					elemType = RepElemType.fromType(rule.getRuleBody().getRepType());
				} else {
					// Recursive calls must be all ops, checkParam at runtime
					elemType = RepElemType.op;
				}
			} else if (bound != null) {
	            // Variable subpaths must be all ops, but we must checkParam at runtime
				elemType = RepElemType.op;
			} else if (isPrimeShape(ruleSpecifier.getShapeType())) {
				elemType = RepElemType.op;
			} else {
				// Forward calls must be all ops, checkParam at runtime
				elemType = RepElemType.op;
			}
		}
		return new ASTReplacement(this, ruleSpecifier, mods, elemType, location);
	}

	public ASTExpression makeFunction(String name, ASTExpression args, Token location) {
		return makeFunction(name, args, false, location);
	}
	
	public ASTExpression makeFunction(String name, ASTExpression args, boolean consAllowed, Token location) {
		int nameIndex = stringToShape(name, true, location);
		boolean isGlobal = false;
		ASTParameter bound = findExpression(nameIndex, isGlobal);
		if (bound != null) {
			if (!consAllowed) {
				error("Cannot bind expression to variable/parameter", location);
			}
			return makeVariable(name, location).append(args); 
		}
		if (name.equals("select") || name.equals("if")) {
			return new ASTSelect(args, name.equals("if"), location);
		}
		FuncType t = FuncType.byName(name);
		if (t == FuncType.Ftime || t == FuncType.Frame) {
			cfdg.addParameter(Param.FrameTime);
		}
		if (t != FuncType.NotAFunction) {
			return new ASTFunction(name, args, seed, location);
		}
		if (args != null && args.getType() == ExpType.ReuseType) {
			return makeRuleSpec(name, args, null, false, location);
		}
		return new ASTUserFunction(this, nameIndex, args, null, location);
	}
	
	public ASTModification makeModification(ASTModification mod, boolean canonial, Token location) {
		mod.setIsConstant(mod.getModExp().isEmpty());
		mod.setCanonical(canonial);
		mod.setLocation(location);
		return mod;
	}
	
	public String getTypeInfo(int nameIndex, ASTDefine[] func, List<ASTParameter>[] p) {
		func[0] = cfdg.findFunction(nameIndex);
		p[0] = cfdg.getShapeParams(nameIndex);
		return cfdg.decodeShapeName(nameIndex);
	}

	public ASTRule getRule(int nameIndex) {
		return cfdg.findRule(nameIndex);
	}

	public void pushRepContainer(ASTRepContainer c) {
		containerStack.push(c);
		processRepContainer(c);
	}
	
	private void processRepContainer(ASTRepContainer c) {
		c.setStackCount(0);
		for (ASTParameter param : c.getParameters()) {
			if (param.isParameter() || param.isLoopIndex()) {
				param.setStackIndex(localStackDepth);
				c.setStackCount(c.getStackCount() + param.getTupleSize());
				localStackDepth += param.getTupleSize();
			} else {
				break;  // the parameters are all in front
			}
		}
	}
	
	public void popRepContainer(ASTReplacement r) {
		ASTRepContainer lastContainer = containerStack.lastElement();
		localStackDepth -= lastContainer.getStackCount();
		if (r != null) {
			r.setRepType(RepElemType.fromType(r.getRepType().getType() | lastContainer.getRepType()));
			if (r.getPathOp() == PathOp.UNKNOWN) {
				r.setPathOp(lastContainer.getPathOp());
			}
		}
		containerStack.pop();
	}

	private boolean badContainer(int containerType) {
		return (containerType & (RepElemType.op.getType() | RepElemType.replacement.getType())) == (RepElemType.op.getType() | RepElemType.replacement.getType());
	}
	
	public void pushRep(ASTReplacement r, boolean global) {
		if (r == null) {
			return;
		}
		ASTRepContainer container = containerStack.lastElement();
		container.getBody().add(r);
		if (container.getPathOp() == PathOp.UNKNOWN) {
			container.setPathOp(r.getPathOp());
		}
		int oldType = container.getRepType();
		container.setRepType(oldType | r.getRepType().getType());
		if (badContainer(container.getRepType()) && !badContainer(oldType) && !global) {
			error("Cannot mix path elements and replacements in the same container", r.getLocation());
		}
	}
	
	public ASTParameter findExpression(int nameIndex, boolean isGlobal) {
		if (containerStack.size() > 0) {
			for (ListIterator<ASTRepContainer> i = containerStack.listIterator(containerStack.size()); i.hasPrevious();) {
				ASTRepContainer repCont = i.previous();
				if (repCont.getParameters().size() > 0) {
					for (ListIterator<ASTParameter> p = repCont.getParameters().listIterator(repCont.getParameters().size()); p.hasPrevious();) {
						ASTParameter param = p.previous();
						if (param.getNameIndex() == nameIndex) {
							isGlobal = repCont.isGlobal();
							return param;
						}
					}
				}
			}
		}
		return null;
	}
	
	protected void checkVariableName(int nameIndex, boolean param, Token location) {
		if (allowOverlap && !param) {
			return;
		}
		if (containerStack.size() > 0) {
			ASTRepContainer repCont = param ? paramDecls : containerStack.lastElement();
			if (repCont.getParameters().size() > 0) {
				for (ListIterator<ASTParameter> i = repCont.getParameters().listIterator(repCont.getParameters().size()); i.hasPrevious(); ) {
					ASTParameter p = i.previous();
					if (p.getNameIndex() == nameIndex) {
						warning("Scope of name overlaps variable/parameter with same name", location);
						warning("Previous variable/parameter declared here", p.getLocation());
					}
				}
			}
		}
	}

	public void checkName(String name, boolean colonsAllowed, Token location) {
		int pos = name.indexOf(":");
		if (pos == -1) {
			return;
		}
		if (!colonsAllowed) {
			error("namespace specification not allowed in this context", location);
			return;
		}
		if (pos == 0) {
			error("improper namespace specification", location);
			return;
		}
		for (;;) {
			if (pos == name.length() - 1 || name.charAt(pos + 1) != ':') break;
			int next = name.indexOf(":", pos + 2);
			if (next == -1) return;
			if (next == pos + 2) break;
			pos = next;
		}
		error("improper namespace specification", location);
	}

	protected String relativeFilePath(String base, String rel) {
		int i = base.lastIndexOf("/");
		if (i == -1) {
			return rel;
		}
		return base.substring(0, i) + rel;
	}
	
	protected void popNameSpace() {
		currentNameSpace = currentNameSpace.substring(0, currentNameSpace.length() - 2);
		int end = currentNameSpace.lastIndexOf(":");
		if (end == -1) {
			currentNameSpace = "";
		} else {
			currentNameSpace.substring(0, end + 1);
		}
	}

	protected void pushNameSpace(String n, Token location) {
		if (n.equals("CF")) {
			error("CF namespace is reserved", location);
			return;
		}
		if (n.length() == 0) {
			error("zero-length namespace", location);
			return;
		}
		checkName(n, false, location);
		includeNamespace.pop();
		includeNamespace.push(Boolean.TRUE);
		currentNameSpace = currentNameSpace + n + "::";
	}
	
	public void inColor() {
		cfdg.addParameter(Param.Color);
	}

	public void timeWise() {
		cfdg.addParameter(Param.Time);
	}

	public void storeParams(CFStackRule p) {
		longLivedParams.add(p);
	}

	public String getMaybeVersion() {
		return maybeVersion;
	}

	public void setMaybeVersion(String maybeVersion) {
		this.maybeVersion = maybeVersion;
	}

	public CFDG getCFDG() {
		return cfdg;
	}

	public boolean errorOccured() {
		return errorOccured;
	}

	public boolean isInPathContainer() {
		return this.inPathContainer ;
	}

	public void setInPathContainer(boolean inPathContainer) {
		this.inPathContainer = inPathContainer;
	}

	public Rand64 getSeed() {
		return seed;
	}

	public ASTRepContainer getParamDecls() {
		return paramDecls;
	}

	public Stack<ASTSwitch> getSwitchStack() {
		return switchStack;
	}

	public Stack<ASTRepContainer> getContainerStack() {
		return containerStack;
	}

	public void incSwitchStack() {
		localStackDepth--;
	}

	public void decSwitchStack() {
		localStackDepth++;
	}

	public int getLocalStackDepth() {
		return localStackDepth;
	}

	public void setLocalStackDepth(int localStackDepth) {
		this.localStackDepth = localStackDepth;
	}

	public ASTRepContainer getCFDGContent() {
		return cfdg.getContents();
	}
}
