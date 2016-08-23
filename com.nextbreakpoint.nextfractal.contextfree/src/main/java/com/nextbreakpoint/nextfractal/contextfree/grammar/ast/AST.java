package com.nextbreakpoint.nextfractal.contextfree.grammar.ast;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.FlagType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ModClass;
import org.antlr.v4.runtime.Token;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AST {
    public static final double SQRT2 = Math.sqrt(2.0);
    public static final int MAX_VECTOR_SIZE = 99;

    public static ExpType decodeType(String typeName, int[] tupleSize, boolean[] isNatural, Token location) {
        ExpType type;
        tupleSize[0] = 1;
        isNatural[0] = false;

        if (typeName.equals("number")) {
            type = ExpType.NumericType;
        } else if (typeName.equals("natural")) {
            type = ExpType.NumericType;
            isNatural[0] = true;
        } else if (typeName == "adjustment") {
            type = ExpType.ModType;
            tupleSize[0] = 6;
        } else if (typeName == "shape") {
            type = ExpType.RuleType;
        } else if (typeName.startsWith("vector")) {
            type = ExpType.NumericType;
            if (typeName.matches("vector[0-9]+")) {
                tupleSize[0] = Integer.parseInt(typeName.substring(6));
                if (tupleSize[0] <= 1 || tupleSize[0] > 99) {
                    Logger.error("Illegal vector size (<=1 or >99)", location);
                }
            } else {
                Logger.error("Illegal vector type specification", location);
            }
        } else {
            type = ExpType.NoType;
            Logger.error("Unrecognized type name", location);
        }
        return type;
    }

    public static List<ASTModification> getTransforms(ASTExpression e, SymmList syms, RTI rti, boolean tiled, AffineTransform transform) {
        List<ASTModification> result = new ArrayList<>();

        syms.clear();

        if (e == null) {
            return result;
        }

        // TODO da rivedere

        List<Double> symmSpec = new ArrayList<>();

        for (int i = 0; i < e.size(); i++) {
            ASTExpression cit = e.getChild(i);
            switch (cit.getType()) {
                case FlagType:
                    processSymmSpec(syms, transform, tiled, symmSpec, cit.getLocation());
                    break;
                case NumericType:
                    if (symmSpec.isEmpty() && cit.getType() != ExpType.FlagType) {
                        Logger.fail("Symmetry flag expected here", cit.getLocation());
                    }
                    int sz = cit.evaluate(null, 0);
                    if (sz < 1) {
                        Logger.fail("Could not evaluate this", cit.getLocation());
                    } else {
                        double[] values = new double[sz];
                        if (cit.evaluate(values, values.length) != sz) {
                            Logger.fail("Could not evaluate this", cit.getLocation());
                        } else {
                            for (double value : values) {
                                symmSpec.add(value);
                            }
                        }
                    }
                    break;
                case ModType:
                    processSymmSpec(syms, transform, tiled, symmSpec, cit.getLocation());
                    if (cit instanceof ASTModification) {
                        ASTModification m = (ASTModification)cit;
                        if (m.getModClass() != null && m.getModClass().getType() == (ModClass.GeomClass.getType() | ModClass.PathOpClass.getType()) && (rti != null || m.isConstant())) {
                            Modification mod = new Modification();
                            cit.evaluate(mod, false, rti);
                            addUnique(syms, mod.getTransform());
                        } else {
                            result.add(m);
                        }
                    } else {
                        Logger.fail("Wrong type", cit.getLocation());
                    }
                    break;
                default:
                    Logger.fail("Wrong type", cit.getLocation());
                    break;
            }
        }

        processSymmSpec(syms, transform, tiled, symmSpec, e.getLocation());

        return result;
    }

    public static void addUnique(SymmList syms, AffineTransform transform) {
        if (syms.contains(transform)) {
            syms.add(transform);
        }
    }

    public static void processDihedral(SymmList syms, double order, double x, double y, boolean dihedral, double angle, Token location) {
        //TODO completare
    }

    public static void processSymmSpec(SymmList syms, AffineTransform transform, boolean tiled, List<Double> data, Token location) {
        if (data == null) {
            return;
        }

        int type = data.get(0).intValue();
        FlagType flag = FlagType.fromMask(type);

        boolean frieze = (transform.getScaleX() != 0.0 || transform.getScaleY() != 0.0) && transform.getScaleX() * transform.getScaleY() == 0.0;
        boolean rhombic = tiled && ((Math.abs(transform.getShearY()) <= 0.0000001 && Math.abs(transform.getShearX() / transform.getScaleX() - 0.5) < 0.0000001) || (Math.abs(transform.getShearX()) <= 0.0000001 && Math.abs(transform.getShearY() / transform.getScaleY() - 0.5) < 0.0000001));
        boolean rectangular = tiled && transform.getShearX() == 0.0 && transform.getShearY() == 0.0;
        boolean square = rectangular && transform.getScaleX() == transform.getScaleY();
        boolean hexagonal = false;
        boolean square45 = false;
        double size45 = transform.getScaleX();

        if (rhombic) {
            double x1 = 1;
            double y1 = 0;
            Point2D.Double p1 = new Point2D.Double(x1, y1);
            transform.transform(p1, p1);
            x1 = p1.getX();
            y1 = p1.getY();
            double dist10 = Math.hypot(x1, y1);
            double x2 = 0;
            double y2 = 1;
            Point2D.Double p2 = new Point2D.Double(x1, y1);
            transform.transform(p2, p2);
            x2 = p2.getX();
            y2 = p2.getY();
            double dist01 = Math.hypot(x2, y2);
            hexagonal = Math.abs(dist10 / dist01 - 1.0) < 0.0000001;
            square45 = Math.abs(dist10 / dist01 - AST.SQRT2) < 0.0000001 || Math.abs(dist01 / dist10 - AST.SQRT2) < 0.0000001;
            size45 = Math.min(dist01, dist10);
        }

        if (type >= FlagType.CF_P11G.getMask() && type <= FlagType.CF_P2MM.getMask() && !frieze) {
            Logger.fail("Frieze symmetry only works in frieze designs", location);
        }

        if (type >= FlagType.CF_PM.getMask() && type <= FlagType.CF_P6M.getMask() && !tiled) {
            Logger.fail("Wallpaper symmetry only works in tiled designs", location);
        }

        if (type >= FlagType.CF_P2.getMask() && !frieze && !tiled) {
            Logger.fail("p2 symmetry only works in frieze or tiled designs", location);
        }

        //TODO completare

        switch (flag) {
            case CF_CYCLIC: {
                break;
            }
            default: {
                break;
            }
        }
    }

    public static Locality combineLocality(Locality locality1, Locality locality2) {
        return Locality.fromType(locality1.ordinal() | locality2.ordinal());
    }

    public static List<ASTExpression> extract(ASTExpression exp) {
        if (exp instanceof ASTCons) {
            return ((ASTCons)exp).getChildren();
        } else {
            List<ASTExpression> ret = new ArrayList<ASTExpression>();
            ret.add(exp);
            return ret;
        }
    }

    public static ASTExpression makeResult(double result, int lenght, ASTExpression from) {
        //TODO completare
        return null;
    }
}