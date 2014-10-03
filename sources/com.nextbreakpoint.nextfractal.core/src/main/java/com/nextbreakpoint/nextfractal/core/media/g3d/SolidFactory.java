/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is based on code from idx3dIII
 * Copyright 1999, 2000 Peter Walser
 * http://www.idx3d.ch/idx3d/idx3d.html
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
package com.nextbreakpoint.nextfractal.core.media.g3d;

public final class SolidFactory {
	private SolidFactory() {
	}

	public static Part FIELD(final String name, final int xresolution, final int yresolution, final float xsize, final float ysize, final boolean single) {
		final float[][] data = new float[xresolution][yresolution];
		for (int i = 0; i < xresolution; i++) {
			for (int j = 0; j < yresolution; j++) {
				data[i][j] = 0;
			}
		}
		return SolidFactory.HEIGHTFIELD(name, data, xsize, ysize, single);
	}

	public static Part HEIGHTFIELD(final String name, final Texture heightmap, final float height, final int xresolution, final int yresolution, final float xsize, final float ysize, final boolean single) {
		final float[][] data = new float[xresolution][yresolution];
		final float vx = (float) heightmap.width / (float) xresolution;
		final float vy = (float) heightmap.height / (float) yresolution;
		for (int j = 0; j < yresolution; j++) {
			for (int i = 0; i < xresolution; i++) {
				data[i][j] = ((Color.getAverage(heightmap.pixels[((int) (vy * j) * heightmap.width) + (int) (vx * i)]) - 127) / 127f) * height;
			}
		}
		return SolidFactory.HEIGHTFIELD(name, data, xsize, ysize, single);
	}

	public static Part HEIGHTFIELD(final String name, final float[][] data, final float xsize, final float ysize, final boolean single) {
		final Part part = new Part(name);
		Vertex vertex = null;
		int q1;
		int q2;
		int q3;
		int q4;
		float x;
		float y;
		float u;
		float v;
		final int xresolution = data.length;
		final int yresolution = data[0].length;
		final float xscale = 1f / (xresolution - 1);
		final float yscale = 1f / (yresolution - 1);
		for (int i = 0; i < xresolution; i++) {
			u = (float) i / (float) (xresolution - 1);
			x = ((xscale * i) - 0.5f) * xsize;
			for (int j = 0; j < yresolution; j++) {
				v = (float) j / (float) (yresolution - 1);
				y = (0.5f - (yscale * j)) * ysize;
				vertex = new Vertex(x, y, data[i][j]);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (xresolution - 1); i++) {
			for (int j = 0; j < (yresolution - 1); j++) {
				q1 = j + (yresolution * i);
				q2 = q1 + 1;
				q3 = q1 + yresolution;
				q4 = q2 + yresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q2, q4, q3);
			}
		}
		return part;
	}

	public static Part CUBE(final String name, final int resolution, final float size, final boolean single) {
		return SolidFactory.BOX(name, resolution, resolution, resolution, size, size, size, single);
	}

	/*
	 * public static Part BOX(String name,float xsize,float ysize,float zsize,boolean single) { Part part = new Part(name); float x = (float)Math.abs(xsize / 2f); float y = (float)Math.abs(ysize / 2f); float z = (float)Math.abs(zsize / 2f); int[] xflag = new int[6]; int[] yflag = new int[6]; int[] zflag = new int[6]; xflag[0] = 10; yflag[0] = 3; zflag[0] = 0; xflag[1] = 10; yflag[1] = 15; zflag[1] = 3; xflag[2] = 15; yflag[2] = 3; zflag[2] = 10; xflag[3] = 10; yflag[3] = 0; zflag[3] = 12; xflag[4] = 0; yflag[4] = 3; zflag[4] = 5; xflag[5] = 5; yflag[5] = 3; zflag[5] = 15; float xx; float yy; float zz; for (int side = 0; side < 6; side++) { for (int i = 0; i < 4; i++) { xx = ((xflag[side] & (1 << i)) > 0) ? +x : -x; yy = ((yflag[side] & (1 << i)) > 0) ? +y : -y; zz = ((zflag[side] & (1 << i)) > 0) ? +z : -z; part.addVertex(xx,yy,zz,i & 1,(i & 2) >> 1); } int j = side << 2; part.addTriangle(single,j,j + 2,j + 3); part.addTriangle(single,j,j + 3,j + 1); } return part; }
	 */
	public static Part BOX(final String name, final int xresolution, final int yresolution, final int zresolution, final float xsize, final float ysize, final float zsize, final boolean single) {
		final Part part = new Part(name);
		Vertex vertex = null;
		int q1;
		int q2;
		int q3;
		int q4;
		float x;
		float y;
		float z;
		float u;
		float v;
		final float xscale = 1f / (xresolution - 1);
		final float yscale = 1f / (yresolution - 1);
		final float zscale = 1f / (zresolution - 1);
		int offset = 0;
		z = -0.5f * zsize;
		for (int i = 0; i < xresolution; i++) {
			u = (float) i / (float) (xresolution - 1);
			x = ((xscale * i) - 0.5f) * xsize;
			for (int j = 0; j < yresolution; j++) {
				v = (float) j / (float) (yresolution - 1);
				y = (0.5f - (yscale * j)) * ysize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (xresolution - 1); i++) {
			for (int j = 0; j < (yresolution - 1); j++) {
				q1 = offset + j + (yresolution * i);
				q2 = q1 + 1;
				q3 = q1 + yresolution;
				q4 = q2 + yresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		offset += (xresolution * yresolution);
		z = -z;
		for (int i = 0; i < xresolution; i++) {
			u = (float) i / (float) (xresolution - 1);
			x = (0.5f - (xscale * i)) * xsize;
			for (int j = 0; j < yresolution; j++) {
				v = (float) j / (float) (yresolution - 1);
				y = (0.5f - (yscale * j)) * ysize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (xresolution - 1); i++) {
			for (int j = 0; j < (yresolution - 1); j++) {
				q1 = offset + j + (yresolution * i);
				q2 = q1 + 1;
				q3 = q1 + yresolution;
				q4 = q2 + yresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		offset += (xresolution * yresolution);
		x = -0.5f * xsize;
		for (int i = 0; i < zresolution; i++) {
			u = (float) (zresolution - 1 - i) / (float) (zresolution - 1);
			z = ((zscale * i) - 0.5f) * zsize;
			for (int j = 0; j < yresolution; j++) {
				v = (float) (yresolution - 1 - j) / (float) (yresolution - 1);
				y = ((yscale * j) - 0.5f) * ysize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (zresolution - 1); i++) {
			for (int j = 0; j < (yresolution - 1); j++) {
				q1 = offset + j + (yresolution * i);
				q2 = q1 + 1;
				q3 = q1 + yresolution;
				q4 = q2 + yresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		offset += (zresolution * yresolution);
		x = -x;
		for (int i = 0; i < zresolution; i++) {
			u = (float) i / (float) (zresolution - 1);
			z = ((zscale * i) - 0.5f) * zsize;
			for (int j = 0; j < yresolution; j++) {
				v = (float) j / (float) (yresolution - 1);
				y = (0.5f - (yscale * j)) * ysize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (zresolution - 1); i++) {
			for (int j = 0; j < (yresolution - 1); j++) {
				q1 = offset + j + (yresolution * i);
				q2 = q1 + 1;
				q3 = q1 + yresolution;
				q4 = q2 + yresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		offset += (zresolution * yresolution);
		y = -0.5f * ysize;
		for (int i = 0; i < zresolution; i++) {
			u = (float) i / (float) (zresolution - 1);
			z = ((zscale * i) - 0.5f) * zsize;
			for (int j = 0; j < xresolution; j++) {
				v = (float) j / (float) (xresolution - 1);
				x = (0.5f - (xscale * j)) * xsize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (zresolution - 1); i++) {
			for (int j = 0; j < (xresolution - 1); j++) {
				q1 = offset + j + (xresolution * i);
				q2 = q1 + 1;
				q3 = q1 + xresolution;
				q4 = q2 + xresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		offset += (zresolution * xresolution);
		y = -y;
		for (int i = 0; i < zresolution; i++) {
			u = (float) i / (float) (zresolution - 1);
			z = (0.5f - (zscale * i)) * zsize;
			for (int j = 0; j < xresolution; j++) {
				v = (float) j / (float) (xresolution - 1);
				x = (0.5f - (xscale * j)) * xsize;
				vertex = new Vertex(x, y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (zresolution - 1); i++) {
			for (int j = 0; j < (xresolution - 1); j++) {
				q1 = offset + j + (+xresolution * i);
				q2 = q1 + 1;
				q3 = q1 + xresolution;
				q4 = q2 + xresolution;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		return part;
	}

	public static Part CONE(final String name, final float height, final float radius, final int sides, final boolean single) {
		final Vector[] path = new Vector[3];
		final float h = height / 2f;
		path[0] = new Vector(0, +h, 0);
		path[1] = new Vector(radius, -h, 0);
		path[2] = new Vector(0, -h, 0);
		return SolidFactory.ROTATIONOBJECT(name, path, sides, single);
	}

	public static Part CYLINDER(final String name, final float height, final float radius, final int sides, final boolean single) {
		final Vector[] path = new Vector[4];
		final float h = height / 2f;
		path[0] = new Vector(0, +h, 0);
		path[1] = new Vector(radius, +h, 0);
		path[2] = new Vector(radius, -h, 0);
		path[3] = new Vector(0, -h, 0);
		return SolidFactory.ROTATIONOBJECT(name, path, sides, single);
	}

	public static Part SPHERE(final String name, final float radius, final int sides, final boolean single) {
		final Vector[] path = new Vector[sides + 1];
		float x;
		float y;
		final float step = -3.14159265f / sides;
		float angle = (0.5f * 3.14159265f) + step;
		path[0] = new Vector(0, +radius, 0);
		for (int i = 1; i < sides; i++) {
			x = Math.cos(angle) * radius;
			y = Math.sin(angle) * radius;
			path[i] = new Vector(x, y, 0);
			angle += step;
		}
		path[sides] = new Vector(0, -radius, 0);
		return SolidFactory.ROTATIONOBJECT(name, path, sides, single);
	}

	public static Part ROTATIONOBJECT(final String name, final Vector[] path, final int sides, final boolean single) {
		final Part part = new Part(name);
		Vertex vertex = null;
		int q1;
		int q2;
		int q3;
		int q4;
		float x;
		float z;
		float u;
		float v;
		final int steps = sides + 1;
		final int nodes = path.length;
		final float step = -(2f * 3.14159265f) / sides;
		float alpha = 0;
		for (int j = 0; j < steps; j++) {
			u = (float) j / (float) (steps - 1);
			for (int i = 0; i < nodes; i++) {
				v = (float) i / (float) (nodes - 1);
				x = ((path[i].x * Math.cos(alpha)) + (path[i].z * Math.sin(alpha)));
				z = ((path[i].z * Math.cos(alpha)) - (path[i].x * Math.sin(alpha)));
				vertex = new Vertex(x, path[i].y, z);
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
			alpha += step;
		}
		for (int j = 0; j < (steps - 1); j++) {
			for (int i = 0; i < (nodes - 1); i++) {
				q1 = i + (nodes * j);
				q2 = q1 + 1;
				q3 = q1 + nodes;
				q4 = q2 + nodes;
				part.addTriangle(single, q1, q2, q3);
				part.addTriangle(single, q3, q2, q4);
			}
		}
		for (int i = 0; i < (nodes - 1); i++) {
			q1 = i + (nodes * (steps - 1));
			q2 = q1 + 1;
			q3 = i;
			q4 = q3 + 1;
			part.addTriangle(single, q1, q2, q3);
			part.addTriangle(single, q3, q2, q4);
		}
		return part;
	}

	public static Part TORUS(final String name, final float radius, final float size, final int segments, final int sides, final boolean single) {
		final Vector[] circle = new Vector[segments + 1];
		final float step = (2f * 3.14159265f) / segments;
		float angle = 0;
		for (int i = 0; i < (segments + 1); i++) {
			circle[i] = new Vector(radius * Math.cos(angle), radius * Math.sin(angle), 0f);
			angle += step;
		}
		return SolidFactory.TUBE(name, circle, size, sides, true, single);
	}

	public static Part TUBE(final String name, final Vector[] path, final float radius, final int sides, final boolean closed, final boolean single) {
		final Vector[] circle = new Vector[sides + 1];
		final float step = (2f * 3.14159265f) / sides;
		float angle = 0;
		for (int i = 0; i < (sides + 1); i++) {
			circle[i] = new Vector(radius * Math.cos(angle), radius * Math.sin(angle), 0f);
			angle += step;
		}
		return SolidFactory.EXTRUSIONOBJECT(name, path, circle, closed, single);
	}

	public static Part EXTRUSIONOBJECT(final String name, final Vector[] path, final Vector[] shape, final boolean closed, final boolean single) {
		final Part part = new Part(name);
		Vector forward;
		Vector up;
		Vector right;
		Matrix frenetmatrix;
		Vertex vertex;
		int q1;
		int q2;
		int q3;
		int q4;
		float u;
		float v;
		final int segments = path.length;
		final int sides = shape.length;
		for (int i = 0; i < segments; i++) {
			if (i != (segments - 1)) {
				forward = Vector.sub(path[i + 1], path[i]);
			}
			else {
				if (!closed) {
					forward = Vector.sub(path[i], path[i - 1]);
				}
				else {
					forward = Vector.sub(path[1], path[0]);
				}
			}
			forward.normalize();
			up = new Vector(0f, 0f, 1f);
			right = Vector.normal(forward, up);
			up = Vector.normal(forward, right);
			frenetmatrix = new Matrix(right, up, forward);
			frenetmatrix.shift(path[i].x, path[i].y, path[i].z);
			v = (float) i / (float) (segments - 1);
			for (int k = 0; k < sides; k++) {
				u = (float) k / (float) (sides - 1);
				vertex = new Vertex(shape[k].transform(frenetmatrix));
				vertex.u = u;
				vertex.v = v;
				part.addVertex(vertex);
			}
		}
		for (int i = 0; i < (segments - 1); i++) {
			for (int k = 0; k < (sides - 1); k++) {
				q1 = (i * sides) + k;
				q2 = q1 + 1;
				q3 = q1 + sides;
				q4 = q2 + sides;
				part.addTriangle(single, q1, q3, q2);
				part.addTriangle(single, q2, q3, q4);
			}
			q1 = ((i + 1) * sides) - 1;
			q2 = (q1 + 1) - sides;
			q3 = q1 + sides;
			q4 = q2 + sides;
			part.addTriangle(single, q1, q3, q2);
			part.addTriangle(single, q2, q3, q4);
		}
		return part;
	}
}
