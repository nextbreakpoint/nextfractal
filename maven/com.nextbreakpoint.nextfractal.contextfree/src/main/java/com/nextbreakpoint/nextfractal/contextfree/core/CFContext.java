/*
 * NextFractal 1.0.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CFContext {
	private ArrayList<ShapeType> shapeTypes = new ArrayList<ShapeType>();
	private ArrayList<CFRule> rules = new ArrayList<CFRule>();
	private CFColor background = new CFColor(0, 0, 1, 1);
	private float offsetX;
	private float offsetY;
	private float tileX;
	private float tileY;
	private float sizeX;
	private float sizeY;
	private boolean sized;
	private boolean tiled;
	private int loopStartShapeType;
	private int loopEndShapeType;

	public CFContext() {
		loopStartShapeType = encodeShapeName("~~LOOPSTART~~");
		loopEndShapeType = encodeShapeName("~~LOOPEND~~");
	}
	
	public CFColor getBackground() {
		return background;
	}

	public void setBackground(CFColor background) {
		this.background = background;
	}

	public void addRule(CFRule rule) {
		if (rule.getInitialShapeType() < shapeTypes.size()) {
			rules.add(rule);
			int type = rule.hasPath() ? ShapeType.TYPE_PATH : ShapeType.TYPE_RULE;
			shapeTypes.get(rule.getInitialShapeType()).setHasRules(true);
			shapeTypes.get(rule.getInitialShapeType()).setType(type);
		}
	}
	
	public CFRule findRule(int shapeType, double random) {
		if (random >= 0 && random <= 1) {
			CFRuleSpecifier rs = new CFRuleSpecifier(shapeType, random);
			CFRule rule = lowerBound(rules, 0, rules.size() - 1, rs);
			return rule;
		} else {
			return null;
		}
	}
	
	private CFRule lowerBound(List<CFRule> list, int begin, int end, CFRuleSpecifier element) {
		if (begin == end) {
			CFRule rule = rules.get(begin);
			if (element.getInitialShapeType() == rule.getInitialShapeType()) {
				return rule;
			} else {
				return null;
			}
		}
		int mid = (begin + end) / 2;
		CFRule rule = rules.get(mid);
		if (rule.compareTo(element) > 0) {
			return lowerBound(rules, begin, mid, element);
		} else {
			return lowerBound(rules, mid + 1, end, element);
		}
	}
	
	public void reloadRules() {
		double[] unitweightsums = new double[shapeTypes.size()];
		double[] weightsums = new double[shapeTypes.size()];
		int[] rulescount = new int[shapeTypes.size()];
		for (int i = 0; i < rules.size(); i++) {
			CFRule rule = rules.get(i);
			weightsums[rule.getInitialShapeType()] += rule.getWeight();
			rulescount[rule.getInitialShapeType()] += 1;
		}
		for (int i = 0; i < rules.size(); i++) {
			CFRule rule = rules.get(i);
			double weight = rule.getWeight() / weightsums[rule.getInitialShapeType()];
			unitweightsums[rule.getInitialShapeType()] += weight;
			rulescount[rule.getInitialShapeType()] -= 1;
			if (rulescount[rule.getInitialShapeType()] != 0) {
				rule.setWeight(unitweightsums[rule.getInitialShapeType()]);
			} else {
				rule.setWeight(1);
			}
		}
		Collections.sort(rules);
	}
	
	public int getRuleCount() {
		return rules.size();
	}

	public boolean isSized() {
		return sized;
	}

	public boolean isTiled() {
		return tiled;
	}
	
	public void setTiled(AffineTransform tileTransform, float tileX, float tileY, boolean useTile, boolean useSize) {
		this.offsetX = (float) tileTransform.getTranslateX();
		this.offsetY = (float) tileTransform.getTranslateY();
		this.sizeX = (float) tileTransform.getScaleX();
		this.sizeY = (float) tileTransform.getScaleY();
		this.tileX = tileX;
		this.tileY = tileY;
		tiled = useTile;
		sized = useSize;
	}

	public float getTileX() {
		return tileX;
	}

	public float getTileY() {
		return tileY;
	}

	public float getSizeX() {
		return sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public String decodeShapeName(int shapeType) {
		if (shapeType < shapeTypes.size()) {
			return shapeTypes.get(shapeType).getName();
		}
		return "Unnamed shape";
	}
	
	public int encodeShapeName(String name) {
		for (int i = 0; i < shapeTypes.size(); i++) {
			if (name.equals(shapeTypes.get(i).getName())) {
				return i;
			}
		}
		shapeTypes.add(new ShapeType(name));
		return shapeTypes.size() - 1;
	}
	
	public int getShapeType(String name) {
		for (int i = 0; i < shapeTypes.size(); i++) {
			if (name.equals(shapeTypes.get(i).getName())) {
				return shapeTypes.get(i).getType();
			}
		}
		return ShapeType.TYPE_NEW;
	}
	
	public int getShapeType(int shapeType) {
		if (shapeType < shapeTypes.size()) {
			return shapeTypes.get(shapeType).getType();
		}
		return ShapeType.TYPE_NEW;
	}
	
	public boolean shapeHasRule(int shapeType) {
		if (shapeType < shapeTypes.size()) {
			return shapeTypes.get(shapeType).hasRules();
		}
		return false;
	}
	
	public ShapeType shapeType(int shapeType) {
		if (shapeType < shapeTypes.size()) {
			return shapeTypes.get(shapeType);
		}
		return null;
	}

	public int getLoopStartShapeType() {
		return loopStartShapeType;
	}

	public int getLoopEndShapeType() {
		return loopEndShapeType;
	}
}
