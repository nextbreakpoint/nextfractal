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

    public static ExpType decodeType(CFDGDriver driver, String typeName, int[] tupleSize, boolean[] isNatural, Token location) {
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
                    driver.error("Illegal vector size (<=1 or >99)", location);
                }
            } else {
                driver.error("Illegal vector type specification", location);
            }
        } else {
            type = ExpType.NoType;
            driver.error("Unrecognized type name", location);
        }
        return type;
    }

    public static List<ASTModification> getTransforms(CFDGDriver driver, ASTExpression e, List<AffineTransform> syms, CFDGRenderer renderer, boolean tiled, AffineTransform transform) {
        List<ASTModification> result = new ArrayList<>();

        syms.clear();

        if (e == null) {
            return result;
        }

        //TODO rivedere

        List<Double> symmSpec = new ArrayList<>();

        for (int i = 0; i < e.size(); i++) {
            ASTExpression cit = e.getChild(i);
            switch (cit.getType()) {
                case FlagType:
                    processSymmSpec(driver, syms, transform, tiled, symmSpec, cit.getLocation());
                case NumericType:
                    if (symmSpec.isEmpty() && cit.getType() != ExpType.FlagType) {
                        driver.fail("Symmetry flag expected here", cit.getLocation());
                    }
                    int sz = cit.evaluate(null, 0);
                    if (sz < 1) {
                        driver.fail("Could not evaluate this", cit.getLocation());
                    } else {
                        double[] values = new double[sz];
                        if (cit.evaluate(values, values.length) != sz) {
                            driver.fail("Could not evaluate this", cit.getLocation());
                        } else {
                            for (double value : values) {
                                symmSpec.add(value);
                            }
                        }
                    }
                    break;
                case ModType:
                    processSymmSpec(driver, syms, transform, tiled, symmSpec, cit.getLocation());
                    if (cit instanceof ASTModification) {
                        ASTModification m = (ASTModification)cit;
                        if (m.getModClass() != null && m.getModClass().getType() == (ModClass.GeomClass.getType() | ModClass.PathOpClass.getType()) && (renderer != null || m.isConstant())) {
                            Modification mod = new Modification();
                            cit.evaluate(mod, false, renderer);
                            addUnique(syms, mod.getTransform());
                        } else {
                            result.add(m);
                        }
                    } else {
                        driver.fail("Wrong type", cit.getLocation());
                    }
                    break;
                default:
                    driver.fail("Wrong type", cit.getLocation());
                    break;
            }
        }

        processSymmSpec(driver, syms, transform, tiled, symmSpec, e.getLocation());

        return result;
    }

    public static void addUnique(List<AffineTransform> syms, AffineTransform transform) {
        if (syms.contains(transform)) {
            syms.add(transform);
        }
    }

    public static void processDihedral(CFDGDriver driver, List<AffineTransform> syms, double order, double x, double y, boolean dihedral, double angle, Token location) {
        if (order < 1.0) {
            driver.fail("Rotational symmetry order must be one or larger", location);
        }
        AffineTransform reg = new AffineTransform();
        reg.translate(-x, -y);
        AffineTransform mirror = getMirrorTransform(angle);
        int num = (int)order;
        order = 2.0 * Math.PI / order;
        for (int i = 0; i < num; ++i) {
            AffineTransform t = new AffineTransform(reg);
            if (i != 0) t.rotate(i * order);
            AffineTransform t2 = new AffineTransform(t);
            t2.concatenate(mirror);
            t.translate(x, y);
            t2.translate(x, y);
            addUnique(syms, t);
            if (dihedral) addUnique(syms, t2);
        }
    }

    private static AffineTransform getMirrorTransform(double angle) {
        AffineTransform mirror = new AffineTransform();
        double ca = Math.cos(angle);
        double sa = Math.sin(angle);
        double d = Math.hypot(ca, sa);
        mirror.scale(ca / d, sa / d);
        return mirror;
    }

    public static void processSymmSpec(CFDGDriver driver, List<AffineTransform> syms, AffineTransform tile, boolean tiled, List<Double> data, Token location) {
        if (data == null || data.size() == 0) {
            return;
        }

        int type = data.get(0).intValue();
        FlagType flag = FlagType.fromMask(type);

        boolean frieze = (tile.getScaleX() != 0.0 || tile.getScaleY() != 0.0) && tile.getScaleX() * tile.getScaleY() == 0.0;
        boolean rhombic = tiled && ((Math.abs(tile.getShearY()) <= 0.0000001 && Math.abs(tile.getShearX() / tile.getScaleX() - 0.5) < 0.0000001) || (Math.abs(tile.getShearX()) <= 0.0000001 && Math.abs(tile.getShearY() / tile.getScaleY() - 0.5) < 0.0000001));
        boolean rectangular = tiled && tile.getShearX() == 0.0 && tile.getShearY() == 0.0;
        boolean square = rectangular && tile.getScaleX() == tile.getScaleY();
        boolean hexagonal = false;
        boolean square45 = false;
        double size45 = tile.getScaleX();

        if (rhombic) {
            double x1 = 1;
            double y1 = 0;
            Point2D.Double p1 = new Point2D.Double(x1, y1);
            tile.transform(p1, p1);
            x1 = p1.getX();
            y1 = p1.getY();
            double dist10 = Math.hypot(x1, y1);
            double x2 = 0;
            double y2 = 1;
            Point2D.Double p2 = new Point2D.Double(x1, y1);
            tile.transform(p2, p2);
            x2 = p2.getX();
            y2 = p2.getY();
            double dist01 = Math.hypot(x2, y2);
            hexagonal = Math.abs(dist10 / dist01 - 1.0) < 0.0000001;
            square45 = Math.abs(dist10 / dist01 - AST.SQRT2) < 0.0000001 || Math.abs(dist01 / dist10 - AST.SQRT2) < 0.0000001;
            size45 = Math.min(dist01, dist10);
        }

        if (type >= FlagType.CF_P11G.getMask() && type <= FlagType.CF_P2MM.getMask() && !frieze) {
            driver.fail("Frieze symmetry only works in frieze designs", location);
        }

        if (type >= FlagType.CF_PM.getMask() && type <= FlagType.CF_P6M.getMask() && !tiled) {
            driver.fail("Wallpaper symmetry only works in tiled designs", location);
        }

        if (type >= FlagType.CF_P2.getMask() && !frieze && !tiled) {
            driver.fail("p2 symmetry only works in frieze or tiled designs", location);
        }

        AffineTransform ref45 = getMirrorTransform(Math.PI / 4);
        AffineTransform ref135 = getMirrorTransform(-Math.PI / 4);

        //TODO completare symmetry

        double M_PI = Math.PI;
        double M_PI_2 = Math.PI / 2;
        double M_PI_4 = Math.PI / 4;
        double M_SQRT1_2 = 1 / Math.sqrt(2);
        double M_SQRT2 = Math.sqrt(2);

        switch (flag) {
            case CF_CYCLIC: {
                double order, x = 0.0, y = 0.0;
                switch (data.size()) {
                    case 4:
                        x = data.get(2);
                        y = data.get(3);
                    case 2:
                        order = data.get(1);
                        break;
                    default:
                        driver.fail("Cyclic symmetry requires an order argument and an optional center of rotation", location);
                        order = 1.0;    // suppress warning, never executed
                        break;  // never gets here
                }
                processDihedral(driver, syms, order, x, y, false, 0.0, location);
                break;
            }
            case CF_DIHEDRAL: {
                double order, angle = 0.0, x = 0.0, y = 0.0;
                switch (data.size()) {
                    case 5:
                        x = data.get(3);
                        y = data.get(4);
                    case 3:
                        order = data.get(1);
                        angle = data.get(2) * M_PI / 180.0;
                        break;
                    case 4:
                        x = data.get(2);
                        y = data.get(3);
                    case 2:
                        order = data.get(1);
                        break;
                    default:
                        driver.fail("Dihedral symmetry requires an order argument, an optional mirror angle, and an optional center of rotation", location);
                        order = 1.0;    // suppress warning, never executed
                        break;  // never gets here
                }
                processDihedral(driver, syms, order, x, y, true, angle, location);
                break;
            }
//            case CF_P11G: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 2) {
//                    if (tile.sx != 0.0)
//                        mirrory = data.get(1);
//                    else
//                        mirrorx = data.get(1);
//                } else if (data.size() > 2) {
//                    driver.fail("p11g symmetry takes no arguments or an optional glide axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                tr.translate(-mirrorx, -mirrory);
//                if (tile.sx != 0.0)
//                    tr.flip_y();
//                else
//                    tr.flip_x();
//                tr.translate(tile.sx * 0.5 + mirrorx, tile.sy * 0.5 + mirrory);
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_P11M: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 2) {
//                    if (tile.sx != 0.0)
//                        mirrory = data.get(1);
//                    else
//                        mirrorx = data.get(1);
//                } else if (data.size() > 2) {
//                    driver.fail("p11m symmetry takes no arguments or an optional mirror axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                tr.translate(-mirrorx, -mirrory);
//                if (tile.sx != 0.0)
//                    tr.flip_y();
//                else
//                    tr.flip_x();
//                tr.translate(mirrorx, mirrory);
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_P1M1: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 2) {
//                    if (tile.sx != 0.0)
//                        mirrorx = data.get(1);
//                    else
//                        mirrory = data.get(1);
//                } else if (data.size() > 2) {
//                    driver.fail("p1m1 symmetry takes no arguments or an optional mirror axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                tr.translate(-mirrorx, -mirrory);
//                if (tile.sx != 0.0)
//                    tr.flip_x();
//                else
//                    tr.flip_y();
//                tr.translate(mirrorx, mirrory);
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_P2: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 3) {
//                    mirrorx = data.get(1);
//                    mirrory = data.get(2);
//                } else if (data.size() != 1) {
//                    driver.fail("p2 symmetry takes no arguments or a center of rotation", location);
//                }
//                processDihedral(driver, syms, 2.0, mirrorx, mirrory, false, 0.0, location);
//                break;
//            }
//            case CF_P2MG: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 3) {
//                    mirrorx = data.get(1);
//                    mirrory = data.get(2);
//                } else if (data.size() != 1) {
//                    driver.fail("p2mg symmetry takes no arguments or a center of rotation", location);
//                }
//                agg::trans_affine tr1;
//                agg::trans_affine_translation tr2(-mirrorx, -mirrory);
//                agg::trans_affine_translation tr3(-mirrorx, -mirrory);
//                agg::trans_affine_translation tr4(-mirrorx, -mirrory);
//                tr2.flip_x();
//                tr3.flip_x();
//                tr3.flip_y();
//                tr4.flip_y();
//                tr2.translate(tile.sx * 0.5 + mirrorx, tile.sy * 0.5 + mirrory);
//                tr3.translate(mirrorx, mirrory);
//                tr4.translate(tile.sx * 0.5 + mirrorx, tile.sy * 0.5 + mirrory);
//                addUnique(syms, tr1);
//                addUnique(syms, tr2);
//                addUnique(syms, tr3);
//                addUnique(syms, tr4);
//                break;
//            }
//            case CF_P2MM: {
//                double mirrorx = 0.0, mirrory = 0.0;
//                if (data.size() == 3) {
//                    mirrorx = data.get(1);
//                    mirrory = data.get(2);
//                } else if (data.size() != 1) {
//                    driver.fail("p2mm symmetry takes no arguments or a center of relection", location);
//                }
//                processDihedral(driver, syms, 2.0, mirrorx, mirrory, true, 0.0, location);
//                break;
//            }
//            case CF_PM: {
//                if (!rectangular && !square45) {
//                    driver.fail("pm symmetry requires rectangular tiling", location);
//                }
//                double offset = 0.0;
//                switch (data.size()) {
//                    case 2:
//                        break;
//                    case 3:
//                        offset = data.get(2);
//                        break;
//                    default:
//                        driver.fail("pm symmetry takes a mirror axis argument and an optional axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                int axis = data.get(1).intValue();
//                if (rectangular && (axis < 0 || axis > 1))
//                    driver.fail("pm symmetry mirror axis argument must be 0 or 1", location);
//                else if (square45 && (axis < 2 || axis > 3))
//                    driver.fail("pm symmetry mirror axis argument must be 2 or 3", location);
//                switch (axis) {
//                    case 0:         // mirror on x axis
//                        tr.translate(0, -offset);
//                        tr.flip_y();
//                        tr.translate(0, offset);
//                        break;
//                    case 1:         // mirror on y axis
//                        tr.translate(-offset, 0);
//                        tr.flip_x();
//                        tr.translate(offset, 0);
//                        break;
//                    case 2:         // mirror on x=y axis
//                        tr.translate(-offset * M_SQRT1_2,  offset * M_SQRT1_2);
//                        tr *= ref45;
//                        tr.translate( offset * M_SQRT1_2, -offset * M_SQRT1_2);
//                        break;
//                    case 3:         // mirror on x=-y axis
//                        tr.translate(-offset * M_SQRT1_2, -offset * M_SQRT1_2);
//                        tr *= ref135;
//                        tr.translate( offset * M_SQRT1_2,  offset * M_SQRT1_2);
//                        break;
//                    default:
//                        driver.fail("pm symmetry mirror axis argument must be 0, 1, 2, or 3", location);
//                        break;
//                }
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_PG: {
//                if (!rectangular && !square45) {
//                    driver.fail("pg symmetry requires rectangular tiling", location);
//                }
//                double offset = 0.0;
//                switch (data.size()) {
//                    case 2:
//                        break;
//                    case 3:
//                        offset = data.get(2);
//                        break;
//                    default:
//                        driver.fail("pg symmetry takes a glide axis argument and an optional axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                int axis = data.get(1).intValue();
//                if (rectangular && (axis < 0 || axis > 1))
//                    driver.fail("pg symmetry mirror axis argument must be 0 or 1", location);
//                else if (square45 && (axis < 2 || axis > 3))
//                    driver.fail("pg symmetry mirror axis argument must be 2 or 3", location);
//                switch (axis) {
//                    case 0:         // mirror on x axis
//                        tr.translate(0, -offset);
//                        tr.flip_y();
//                        tr.translate(tile.sx * 0.5, offset);
//                        break;
//                    case 1:         // mirror on y axis
//                        tr.translate(-offset, 0);
//                        tr.flip_x();
//                        tr.translate(offset, tile.sy * 0.5);
//                        break;
//                    case 2:         // mirror on x=y axis
//                        tr.translate(-offset * M_SQRT1_2,  offset * M_SQRT1_2);
//                        tr *= ref45;
//                        tr.translate(( offset + size45 * 0.5) * M_SQRT1_2, (-offset + size45 * 0.5) * M_SQRT1_2);
//                        break;
//                    case 3:         // mirror on x=-y axis
//                        tr.translate(-offset * M_SQRT1_2, -offset * M_SQRT1_2);
//                        tr *= ref135;
//                        tr.translate(( offset - size45 * 0.5) * M_SQRT1_2, ( offset + size45 * 0.5) * M_SQRT1_2);
//                        break;
//                    default:
//                        driver.fail("pg symmetry glide axis argument must be 0, 1, 2, or 3", location);
//                        break;
//                }
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_CM: {
//                if (!rhombic && !square) {
//                    driver.fail("cm symmetry requires diamond tiling", location);
//                }
//                double offset = 0.0;
//                switch (data.size()) {
//                    case 2:
//                        break;
//                    case 3:
//                        offset = data.get(2);
//                        break;
//                    default:
//                        driver.fail("cm symmetry takes a mirror axis argument and an optional axis position argument", location);
//                }
//                agg::trans_affine tr;
//                addUnique(syms, tr);
//                int axis = data.get(1).intValue();
//                if (rhombic && (axis < 0 || axis > 1))
//                    driver.fail("cm symmetry mirror axis argument must be 0 or 1", location);
//                else if (square && (axis < 2 || axis > 3))
//                    driver.fail("cm symmetry mirror axis argument must be 2 or 3", location);
//                switch (axis) {
//                    case 0:         // mirror on x axis
//                        tr.translate(0, -offset);
//                        tr.flip_y();
//                        tr.translate(0, offset);
//                        break;
//                    case 1:         // mirror on y axis
//                        tr.translate(-offset, 0);
//                        tr.flip_x();
//                        tr.translate(offset, 0);
//                        break;
//                    case 2:         // mirror on x=y axis
//                        tr.translate( offset * M_SQRT1_2, -offset * M_SQRT1_2);
//                        tr *= ref45;
//                        tr.translate(-offset * M_SQRT1_2,  offset * M_SQRT1_2);
//                        break;
//                    case 3:         // mirror on x=-y axis
//                        tr.translate(-offset * M_SQRT1_2, -offset * M_SQRT1_2);
//                        tr *= ref135;
//                        tr.translate( offset * M_SQRT1_2,  offset * M_SQRT1_2);
//                        break;
//                    default:
//                        driver.fail("cm symmetry mirror axis argument must be 0, 1, 2, or 3", location);
//                        break;
//                }
//                addUnique(syms, tr);
//                break;
//            }
//            case CF_PMM: {
//                if (!rectangular && !square45) {
//                    driver.fail("pmm symmetry requires rectangular tiling", location);
//                }
//                double centerx = 0.0, centery = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        centerx = data.get(1);
//                        centery = data.get(2);
//                        break;
//                    default:
//                        driver.fail("pmm symmetry takes no arguments or a center of reflection", location);
//                }
//                processDihedral(driver, syms, 2.0, centerx, centery, true, square45 ? M_PI_4 : 0.0, location);
//                break;
//            }
//            case CF_PMG: {
//                if (!rectangular && !square45) {
//                    driver.fail("pmg symmetry requires rectangular tiling", location);
//                }
//                double centerx = 0.0, centery = 0.0;
//                switch (data.size()) {
//                    case 2:
//                        break;
//                    case 4:
//                        centerx = data.get(2);
//                        centery = data.get(3);
//                        break;
//                    default:
//                        driver.fail("pmg symmetry takes a mirror axis argument and an optional center of reflection", location);
//                }
//                agg::trans_affine tr, tr2;
//                int axis = data.get(1).intValue();
//                if (rectangular && (axis < 0 || axis > 1))
//                    driver.fail("pmg symmetry mirror axis argument must be 0 or 1", location);
//                else if (square45 && (axis < 2 || axis > 3))
//                    driver.fail("pmg symmetry mirror axis argument must be 2 or 3", location);
//                switch (axis) {
//                    case 0: {       // mirror on x axis
//                        double cy = Math.abs(centery + 0.25 * tile.sy) < Math.abs(centery - 0.25 * tile.sy) ?
//                                centery + 0.25 * tile.sy : centery - 0.25 * tile.sy;
//                        processDihedral(driver, syms, 2.0, centerx, cy, false, 0.0, location);
//                        tr.translate(-centerx, 0.0);
//                        tr.flip_x();
//                        tr.translate(centerx, 0.5 * tile.sy);
//                        addUnique(syms, tr);
//                        tr2.translate(0.0, -centery);
//                        tr2.flip_y();
//                        tr2.translate(0.0, centery);
//                        addUnique(syms, tr2);
//                        break;
//                    }
//                    case 1: {       // mirror on y axis
//                        double cx = Math.abs(centerx + 0.25 * tile.sx) < Math.abs(centerx - 0.25 * tile.sx) ?
//                                centerx + 0.25 * tile.sx : centerx - 0.25 * tile.sx;
//                        processDihedral(driver, syms, 2.0, cx, centery, false, 0.0, location);
//                        tr.translate(-centerx, 0.0);
//                        tr.flip_x();
//                        tr.translate(centerx, 0.0);
//                        addUnique(syms, tr);
//                        tr2.translate(0.0, -centery);
//                        tr2.flip_y();
//                        tr2.translate(0.5 * tile.sx, centery);
//                        addUnique(syms, tr2);
//                        break;
//                    }
//                    case 2: {       // mirror on x=y axis
//                        double cx  = centerx - 0.25 * M_SQRT1_2 * size45;
//                        double cy  = centery + 0.25 * M_SQRT1_2 * size45;
//                        double cx2 = centerx + 0.25 * M_SQRT1_2 * size45;
//                        double cy2 = centery - 0.25 * M_SQRT1_2 * size45;
//                        if (cx2 * cx2 + cy2 * cy2 < cx * cx + cy * cy) {
//                            cx = cx2;
//                            cy = cy2;
//                        }
//                        processDihedral(driver, syms, 2.0, cx, cy, false, 0.0, location);
//                        tr.translate(-centerx, -centery);   // mirror on x=y
//                        tr *= ref45;
//                        tr.translate( centerx,  centery);
//                        addUnique(syms, tr);
//                        tr2.translate(-centerx, -centery);   // glide on x=-y
//                        tr2 *= ref135;
//                        tr2.translate(centerx - size45 * M_SQRT1_2 * 0.5, centery + size45 * M_SQRT1_2 * 0.5);
//                        addUnique(syms, tr2);
//                        break;
//                    }
//                    case 3: {       // mirror on x=-y axis
//                        double cx  = centerx + 0.25 * M_SQRT1_2 * size45;
//                        double cy  = centery + 0.25 * M_SQRT1_2 * size45;
//                        double cx2 = centerx - 0.25 * M_SQRT1_2 * size45;
//                        double cy2 = centery - 0.25 * M_SQRT1_2 * size45;
//                        if (cx2 * cx2 + cy2 * cy2 < cx * cx + cy * cy) {
//                            cx = cx2;
//                            cy = cy2;
//                        }
//                        processDihedral(driver, syms, 2.0, cx, cy, false, 0.0, location);
//                        tr.translate(-centerx, -centery);   // mirror on x=-y
//                        tr *= ref135;
//                        tr.translate( centerx,  centery);
//                        addUnique(syms, tr);
//                        tr2.translate(-centerx, -centery);   // glide on x=y
//                        tr2 *= ref45;
//                        tr2.translate(centerx + size45 * M_SQRT1_2 * 0.5, centery + size45 * M_SQRT1_2 * 0.5);
//                        addUnique(syms, tr2);
//                        break;
//                    }
//                    default:
//                        driver.fail("pmg symmetry mirror axis argument must be 0, 1, 2, or 3", location);
//                        break;
//                }
//                break;
//            }
//            case CF_PGG: {
//                if (!rectangular && !square45) {
//                    driver.fail("pgg symmetry requires rectangular tiling", location);
//                }
//                double centerx = 0.0, centery = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        centerx = data.get(1);
//                        centery = data.get(2);
//                        break;
//                    default:
//                        driver.fail("pgg symmetry takes no arguments or a center of glide axis intersection", location);
//                }
//                if (square45) {
//                    double cx = centerx + 0.25 * M_SQRT2 * size45;
//                    double cy = centery;
//                    double cx2 = centerx - 0.25 * M_SQRT2 * size45;
//                    double cy2 = centery;
//                    if (cx2*cx2 + cy2*cy2 < cx*cx + cy*cy) {
//                        cx = cx2;
//                        cy = cy2;
//                    }
//                    cx2 = centerx;
//                    cy2 = centery + 0.25 * M_SQRT2 * size45;
//                    if (cx2*cx2 + cy2*cy2 < cx*cx + cy*cy) {
//                        cx = cx2;
//                        cy = cy2;
//                    }
//                    cx2 = centerx;
//                    cy2 = centery - 0.25 * M_SQRT2 * size45;
//                    if (cx2*cx2 + cy2*cy2 < cx*cx + cy*cy) {
//                        cx = cx2;
//                        cy = cy2;
//                    }
//                    processDihedral(driver, syms, 2.0, cx, cy, false, 0.0, location);
//                    agg::trans_affine tr, tr2;
//                    tr.translate(-centerx, -centery);   // glide on x=y
//                    tr *= ref45;
//                    tr.translate(centerx + size45 * M_SQRT1_2 * 0.5, centery + size45 * M_SQRT1_2 * 0.5);
//                    addUnique(syms, tr);
//                    tr2.translate(-centerx, -centery);   // glide on x=-y
//                    tr2 *= ref135;
//                    tr2.translate(centerx - size45 * M_SQRT1_2 * 0.5, centery + size45 * M_SQRT1_2 * 0.5);
//                    addUnique(syms, tr2);
//                    break;
//                }
//                double cx = Math.abs(centerx + 0.25 * tile.sx) < Math.abs(centerx - 0.25 * tile.sx) ?
//                        centerx + 0.25 * tile.sx : centerx - 0.25 * tile.sx;
//                double cy = Math.abs(centery + 0.25 * tile.sy) < Math.abs(centery - 0.25 * tile.sy) ?
//                        centery + 0.25 * tile.sy : centery - 0.25 * tile.sy;
//                processDihedral(driver, syms, 2.0, cx, cy, false, 0.0, location);
//                agg::trans_affine tr, tr2;
//                tr.translate(-centerx, 0.0);
//                tr.flip_x();
//                tr.translate(centerx, 0.5 * tile.sy);
//                addUnique(syms, tr);
//                tr2.translate(0.0, -centery);
//                tr2.flip_y();
//                tr2.translate(0.5 * tile.sx, centery);
//                addUnique(syms, tr2);
//                break;
//            }
//            case CF_CMM: {
//                if (!rhombic && !square) {
//                    driver.fail("cmm symmetry requires diamond tiling", location);
//                }
//                double centerx = 0.0, centery = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        centerx = data.get(1);
//                        centery = data.get(2);
//                        break;
//                    default:
//                        driver.fail("cmm symmetry takes no arguments or a center of reflection", location);
//                }
//                processDihedral(driver, syms, 2.0, centerx, centery, true, square45 ? M_PI_4 : 0.0, location);
//                break;
//            }
//            case CF_P4:
//            case CF_P4M: {
//                if (!square && !square45) {
//                    driver.fail("p4 & p4m symmetry requires square tiling", location);
//                }
//                double x = 0.0, y = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        x = data.get(1);
//                        y = data.get(2);
//                        break;
//                    default:
//                        driver.fail("p4 & p4m symmetry takes no arguments or a center of rotation", location);
//                }
//                processDihedral(driver, syms, 4.0, x, y, type == FlagType.CF_P4M.getMask(), square ? M_PI_4 : 0.0, location);
//                break;
//            }
//            case CF_P4G: {
//                if (!square && !square45) {
//                    driver.fail("p4g symmetry requires square tiling", location);
//                }
//                double centerx = 0.0, centery = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        centerx = data.get(1);
//                        centery = data.get(2);
//                        break;
//                    default:
//                        driver.fail("p4g symmetry takes no arguments or a center of rotation", location);
//                }
//                agg::trans_affine reg;
//                reg.translate(-centerx, -centery);
//                agg::trans_affine glide(reg);
//                if (square45) {
//                    glide.translate(-size45 * 0.25 * M_SQRT1_2, -size45 * 0.25 * M_SQRT1_2);
//                    glide *= ref135;
//                    glide.translate(-size45 * 0.25 * M_SQRT1_2,  size45 * 0.75 * M_SQRT1_2);
//                } else {
//                    glide.translate(tile.sx * 0.25, 0.0);
//                    glide.flip_x();
//                    glide.translate(-tile.sx * 0.25, tile.sy * 0.5);
//                }
//                for (int i = 0; i < 4; ++i) {
//                    agg::trans_affine tr(reg), tr2(glide);
//                    if (i) {
//                        tr.rotate(i * M_PI_2);
//                        tr2.rotate(i * M_PI_2);
//                    }
//                    tr.translate(centerx, centery);
//                    tr2.translate(centerx, centery);
//                    addUnique(syms, tr);
//                    addUnique(syms, tr2);
//                }
//                break;
//            }
//            case CF_P3: {
//                if (!hexagonal) {
//                    driver.fail("p3 symmetry requires hexagonal tiling", location);
//                }
//                double x = 0.0, y = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        x = data.get(1);
//                        y = data.get(2);
//                        break;
//                    default:
//                        driver.fail("p3 symmetry takes no arguments or a center of rotation", location);
//                }
//                processDihedral(driver, syms, 3.0, x, y, false, 0.0, location);
//                break;
//            }
//            case CF_P3M1:
//            case CF_P31M: {
//                if (!hexagonal) {
//                    driver.fail("p3m1 & p31m symmetry requires hexagonal tiling", location);
//                }
//                double x = 0.0, y = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        x = data.get(1);
//                        y = data.get(2);
//                        break;
//                    default:
//                        driver.fail("p3m1 & p31m symmetry takes no arguments or a center of rotation", location);
//                }
//                boolean deg30 = (Math.abs(tile.shx) <= 0.000001) != (type == FlagType.CF_P3M1.getMask());
//                double angle = M_PI / (deg30 ? 6.0 : 3.0);
//                processDihedral(driver, syms, 3.0, x, y, true, angle, location);
//                break;
//            }
//            case CF_P6:
//            case CF_P6M: {
//                if (!hexagonal) {
//                    driver.fail("p6 & p6m symmetry requires hexagonal tiling", location);
//                }
//                double x = 0.0, y = 0.0;
//                switch (data.size()) {
//                    case 1:
//                        break;
//                    case 3:
//                        x = data.get(1);
//                        y = data.get(2);
//                        break;
//                    default:
//                        driver.fail("p6 & p6m symmetry takes no arguments or a center of rotation", location);
//                }
//                processDihedral(driver, syms, 6.0, x, y, type == FlagType.CF_P6M.getMask(), 0.0, location);
//                break;
//            }
            default:
                driver.fail("Unknown symmetry type", location);
                break;  // never gets here
        }

        data.clear();
    }

    public static Locality combineLocality(Locality locality1, Locality locality2) {
        return Locality.fromType(locality1.getType() | locality2.getType());
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

    public static ASTExpression makeResult(CFDGDriver driver, double result, int lenght, ASTExpression from) {
        ASTReal r = new ASTReal(driver, result, from.getLocation());
        r.setType(from.getType());
        r.setIsNatural(from.isNatural());
        if (lenght > 1) {
            ASTCons l = new ASTCons(driver, from.getLocation(), r);
            for (int i = 1; i< lenght; i++) {
                r = new ASTReal(driver, result, from.getLocation());
                r.setType(from.getType());
                r.setIsNatural(from.isNatural());
                l.append(r);
            }
            return l;
        }
        return r;
    }

    public static ASTExpression getFlagsAndStroke(CFDGDriver driver, List<ASTModTerm> terms, int[] flags) {
        List<ASTModTerm> temp = new ArrayList<>(terms);
        terms.clear();
        ASTExpression ret = null;
        for (ASTModTerm term : temp) {
            switch (term.getModType()) {
                case param:
                    flags[0] |= term.getArgumentsCount();
                    break;
                case stroke:
                    if (ret != null) {
                        driver.error("Only one stroke width term is allowed", term.getLocation());
                    }
                    ret = term.getArguments();
                    term.setArguments(null);
                    break;
                default:
                    terms.add(term);
                    break;
            }
        }
        return ret;
    }

    public static void evalArgs(CFDGRenderer renderer, CFStackRule parent, CFStackIterator dest, ASTExpression arguments, boolean onStack) {
        //TODO rivedere evalArgs
        for (int i = 0; i < arguments.size(); i++, dest.next()) {
            if (onStack) {
                //TODO completare evalArgs
                renderer.setLogicalStack(dest.getStack());
                renderer.setLogicalStackTop(0);
            }
            ASTExpression arg = arguments.getChild(i);
            switch (arg.getType()) {
                case NumericType: {
                    double[] value = new double[dest.getType().getTupleSize()];
                    int num = arg.evaluate(value, dest.getType().getTupleSize(), renderer);
                    if (!ASTParameter.Impure && dest.getType().isNatural() && !renderer.isNatual(value[0])) {
                        renderer.getDriver().error("Expression does not evaluate to a legal natural number", arg.getLocation());
                    }
                    if (num != dest.getType().getTupleSize()) {
                        renderer.getDriver().error("Expression does not evaluate to the correct size", arg.getLocation());
                    }
                    for (int j = 0; j < dest.getType().getTupleSize(); j++) {
                        dest.setItem(j, new CFStackNumber(renderer.getStack(), value[j]));
                    }
                    break;
                }
                case ModType: {
                    Modification modification = new Modification();
                    arg.evaluate(modification, false, renderer);
                    dest.setItem(0, new CFStackModification(renderer.getStack(), modification));
                    break;
                }
                case RuleType: {
                    dest.setItem(0, arg.evalArgs(renderer, parent));
                    break;
                }
                default:
                    break;
            }
        }
    }
}
