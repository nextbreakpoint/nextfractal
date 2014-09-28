package com.nextbreakpoint.nextfractal.contextfree.parser;

import java.awt.geom.PathIterator;

import org.antlr.v4.runtime.Token;

class ASTRule extends ASTReplacement implements Comparable<ASTRule> {
	private ASTRepContainer ruleBody = new ASTRepContainer();
	private ASTCompiledPath cachedPath;
	private float weight;
	private boolean isPath;
	private int nameIndex;
	private EWeightType weightType;
	
	public ASTRule(int nameIndex, float weight, boolean percent, Token location) {
		super(null, ERepElemType.rule, location);
		this.nameIndex = nameIndex;
		this.isPath = false;
		this.weight = weight <= 0.0 ? 1.0f : weight;
		this.weightType = percent ? EWeightType.PercentWeight : EWeightType.ExplicitWeight;
		this.cachedPath = null;
	}

	public ASTRule(int nameIndex, Token location) {
		super(null, ERepElemType.rule, location);
		this.nameIndex = nameIndex;
		this.isPath = false;
		this.weight = 1.0f;
		this.weightType = EWeightType.NoWeight;
		this.cachedPath = null;
	}

	protected ASTRule(int nameIndex, Token location, boolean dummy) {
		super(null, ERepElemType.rule, location);
		this.nameIndex = nameIndex;
		this.isPath = true;
		this.weight = 1.0f;
		this.weightType = EWeightType.NoWeight;
		this.cachedPath = null;
		if (nameIndex != EPrimShape.circleType.getType()) {
			PrimShape shape = PrimShape.getShapeMap().get(nameIndex);
			double[] coords = new double[6];
			int cmd;
			PathIterator iterator = shape.getPathIterator();
			while (!isStop(cmd = iterator.currentSegment(coords))) {
				if (isVertex(cmd)) {
					ASTExpression a = new ASTCons(location, new ASTReal(coords[0], location), new ASTReal(coords[1], location));
					ASTPathOp op = new ASTPathOp(isMoveTo(cmd) ? EPathOp.MOVETO.name() : EPathOp.LINETO.name(), a, location);
					getRuleBody().getBody().add(op);
				}
			}
		} else {
			ASTExpression a = new ASTCons(location, new ASTReal(0.5, location), new ASTReal(0.0, location));
			ASTPathOp op = new ASTPathOp(EPathOp.MOVETO.name(), a, location);
			getRuleBody().getBody().add(op);
			a = new ASTCons(location, new ASTReal(-0.5, location), new ASTReal(0.0, location), new ASTReal(0.5, location));
			op = new ASTPathOp(EPathOp.ARCTO.name(), a, location);
			getRuleBody().getBody().add(op);
			a = new ASTCons(location, new ASTReal(0.5, location), new ASTReal(0.0, location), new ASTReal(0.5, location));
			op = new ASTPathOp(EPathOp.ARCTO.name(), a, location);
			getRuleBody().getBody().add(op);
		}
		getRuleBody().getBody().add(new ASTPathOp(EPathOp.CLOSEPOLY.name(), null, location));
		getRuleBody().setRepType(ERepElemType.op.getType());
		getRuleBody().setPathOp(EPathOp.MOVETO);
	}
	
	private boolean isMoveTo(int cmd) {
		return cmd == PathIterator.SEG_MOVETO;
	}

	private boolean isVertex(int cmd) {
		return cmd >= PathIterator.SEG_MOVETO && cmd < PathIterator.SEG_CLOSE;
	}

	private boolean isStop(int cmd) {
		return cmd == PathIterator.SEG_CLOSE;
	}

	public ASTRepContainer getRuleBody() {
		return ruleBody;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public EWeightType getWeightType() {
		return weightType;
	}
	
	public boolean isPath() {
		return isPath;
	}
	
	public void setPath(boolean isPath) {
		this.isPath = isPath;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Override
	public void compile(ECompilePhase ph) {
		Builder.currentBuilder().setInPathContainer(isPath);
		super.compile(ph);
		ruleBody.compile(ph, null, null);
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		rti.setCurrentSeed(parent.getWorldState().getRand64Seed());
		if (isPath) {
			rti.processPrimShape(parent, this);
		} else {
			ruleBody.traverse(parent, tr, rti, true);
			parent.releaseParams();
		}
	}
	
	public void traversePath(Shape parent, RTI rti) {
		rti.init();
		rti.setCurrentSeed(parent.getWorldState().getRand64Seed());
		rti.setRandUsed(false);
		
		ASTCompiledPath savedPath = null;
		
		if (cachedPath != null && cachedPath.getParameters().equals(parent.getParameters())) {
			savedPath = rti.getCurrentPath();
			rti.setCurrentPath(cachedPath);
			rti.setCurrentCommand(cachedPath.getCommandInfo().iterator());
		}
		
		ruleBody.traverse(parent, false, rti, true);
		if (!rti.getCurrentPath().isComplete()) {
			rti.getCurrentPath().finish(true, rti);
		}
		if (rti.getCurrentPath().useTerminal()) {
			rti.getCurrentPath().getTerminalCommand().traverse(parent, false, rti);
		}
		
		if (savedPath != null) {
			rti.setCurrentPath(savedPath);
		} else {
			if (rti.isRandUsed() && cachedPath == null) {
				cachedPath = rti.getCurrentPath();
				cachedPath.setIsComplete(true);
				cachedPath.setParameters(new StackRule(parent.getParameters()));
				rti.setCurrentPath(new ASTCompiledPath(getLocation()));
			} else {
				rti.getCurrentPath().getPath().clear();
				rti.getCurrentPath().getCommandInfo().clear();
				rti.getCurrentPath().setUseTerminal(false);
				rti.getCurrentPath().setPathUID(ASTCompiledPath.nextPathUID());
				if (rti.getCurrentPath().getParameters() != null) {
					rti.getCurrentPath().setParameters(null);
				}
			}
		}
	}

	@Override
	public int compareTo(ASTRule o) {
		return nameIndex == o.nameIndex ? (weight < o.weight ? -1 : weight == o.weight ? 0 : 1) : nameIndex - o.nameIndex;
	}
}