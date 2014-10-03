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
package com.nextbreakpoint.nextfractal.core.math;

import java.io.IOException;
import java.io.Serializable;

/**
 * Library for matrix operations.
 * 
 * @author Andrea Medeghini
 */
public final class Matrix implements Serializable {
	private static final long serialVersionUID = 1L;
	private int nr = 0;
	private int nc = 0;
	private double[][] M;

	/**
	 * @param nr
	 * @param nc
	 */
	public Matrix(final int nr, final int nc) {
		this.nr = nr;
		this.nc = nc;
		M = new double[nr][nc];
	}

	/**
	 * @param ni
	 */
	public Matrix(final int ni) {
		nr = ni;
		nc = ni;
		M = new double[ni][ni];
	}

	/**
	 * @param ni
	 * @param s
	 */
	public Matrix(final int ni, final double s) {
		nr = ni;
		nc = ni;
		M = new double[ni][ni];
		Matrix.diag(M, s, ni);
	}

	/**
	 * @param M
	 * @param nr
	 * @param nc
	 */
	public Matrix(final double[][] M, final int nr, final int nc) {
		this.nr = nr;
		this.nc = nc;
		this.M = M;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		M = null;
		super.finalize();
	}

	/**
	 * @return
	 */
	public double[][] toArray() {
		return M;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean compare(final Matrix x, final Matrix y) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				if (x.M[r][c] != y.M[r][c]) {
					return (false);
				}
			}
		}
		return (true);
	}

	/**
	 * @param x
	 * @param y
	 * @param t
	 * @return
	 */
	public static boolean compare(final Matrix x, final Matrix y, double t) {
		t = Math.abs(t);
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				if (Math.abs(x.M[r][c] - y.M[r][c]) > t) {
					return (false);
				}
			}
		}
		return (true);
	}

	/**
	 * @param x
	 * @param t
	 * @return
	 */
	public static Matrix normalize(final Matrix x, double t) {
		double m = 0.0;
		t = Math.abs(t);
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				x.M[r][c] = (Math.abs(m = x.M[r][c]) < t) ? 0.0 : m;
			}
		}
		return (x);
	}

	/**
	 * @param x
	 * @return
	 */
	public static Matrix clear(final Matrix x) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				x.M[r][c] = 0.0;
			}
		}
		return (x);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @return
	 */
	public static Matrix add(final Matrix z, final Matrix x, final Matrix y) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[r][c] = x.M[r][c] + y.M[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @return
	 */
	public static Matrix sub(final Matrix z, final Matrix x, final Matrix y) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[r][c] = x.M[r][c] - y.M[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @return
	 */
	public static Matrix mul(final Matrix z, final Matrix x, final Matrix y) {
		Matrix.clear(z);
		if (x.nc == y.nr) {
			for (int r = 0; r < x.nr; r++) {
				for (int c = 0; c < y.nc; c++) {
					for (int i = 0; i < x.nc; i++) {
						z.M[r][c] += (x.M[r][i] * y.M[i][c]);
					}
				}
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param s
	 * @return
	 */
	public static Matrix mul(final Matrix z, final Matrix x, final double s) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[r][c] = x.M[r][c] * s;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param s
	 * @return
	 */
	public static Matrix div(final Matrix z, final Matrix x, final double s) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[r][c] = x.M[r][c] / s;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @return
	 */
	public static Matrix copy(final Matrix z, final Matrix x) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[r][c] = x.M[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param dr
	 * @param dc
	 * @param sr
	 * @param sc
	 * @param br
	 * @param bc
	 * @return
	 */
	public static Matrix copy(final Matrix z, final Matrix x, final int dr, final int dc, final int sr, final int sc, final int br, final int bc) {
		for (int r = 0; r < br; r++) {
			for (int c = 0; c < bc; c++) {
				z.M[r + dr][c + dc] = x.M[r + sr][c + sc];
			}
		}
		return (z);
	}

	/**
	 * @param x
	 * @param s
	 * @return
	 */
	public static Matrix diag(final Matrix x, final double s) {
		if (x.nr == x.nc) {
			for (int r = 0; r < x.nr; r++) {
				for (int c = 0; c < x.nc; c++) {
					x.M[r][c] = (r == c) ? s : 0.0;
				}
			}
		}
		else {
			Matrix.clear(x);
		}
		return (x);
	}

	/**
	 * @param x
	 * @return
	 */
	public static double den(final Matrix x) {
		if (x.nr == x.nc) {
			return (Matrix.den(x.M, x.nr));
		}
		return (0);
	}

	/**
	 * @param x
	 * @param r
	 * @param c
	 * @return
	 */
	public static double comp(final Matrix x, final int r, final int c) {
		if (x.nr == x.nc) {
			return (Matrix.comp(x.M, r, c, x.nr));
		}
		return (0);
	}

	/**
	 * @param z
	 * @param x
	 * @param dr
	 * @param dc
	 * @return
	 */
	public static Matrix minore(final Matrix z, final Matrix x, final int dr, final int dc) {
		int mr = 0;
		for (int r = 0; r < x.nr; r++) {
			if (r != dr) {
				int mc = 0;
				for (int c = 0; c < x.nc; c++) {
					if (c != dc) {
						z.M[mr][mc] = x.M[r][c];
						mc++;
					}
				}
				mr++;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @return
	 */
	public static Matrix trans(final Matrix z, final Matrix x) {
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				z.M[c][r] = x.M[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @return
	 */
	public static Matrix inv(final Matrix z, final Matrix x) {
		if (x.nr == x.nc) {
			Matrix.inv(z.M, x.M, x.nr);
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @return
	 */
	public static Matrix pinv(final Matrix z, final Matrix x) {
		Matrix.pinv(z.M, x.M, x.nr, x.nc);
		return (z);
	}

	/**
	 * @param x
	 * @return
	 */
	public static double norm_sqrt(final Matrix x) {
		double n = 0.0;
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				n += Math.pow(x.M[r][c], 2);
			}
		}
		return (Math.sqrt(n));
	}

	/**
	 * @param x
	 * @return
	 */
	public static double norm_max(final Matrix x) {
		double a = 0.0;
		double m = 0.0;
		for (int r = 0; r < x.nr; r++) {
			for (int c = 0; c < x.nc; c++) {
				m = (m > (a = Math.abs(x.M[r][c]))) ? m : a;
			}
		}
		return (m);
	}

	/**
	 * @param j
	 * @param p
	 * @param q
	 * @param s
	 * @return
	 */
	public static Matrix solve(final Matrix j, final Matrix p, final Matrix q, final Matrix s) {
		Matrix.inv(p, j);// p = inversa(j)
		Matrix.mul(q, p, s);// q = p * s
		return (p);
	}

	/**
	 * @param j
	 * @param p
	 * @param q
	 * @param s
	 * @return
	 */
	public static Matrix psolve(final Matrix j, final Matrix p, final Matrix q, final Matrix s) {
		Matrix.pinv(p, j);// p = pseudoinversa(j)
		Matrix.mul(q, p, s);// q = p * s
		return (p);
	}

	/**
	 * @param x
	 * @param y
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static boolean compare(final double[][] x, final double[][] y, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				if (x[r][c] != y[r][c]) {
					return (false);
				}
			}
		}
		return (true);
	}

	/**
	 * @param x
	 * @param y
	 * @param t
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static boolean compare(final double[][] x, final double[][] y, double t, final int nr, final int nc) {
		t = Math.abs(t);
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				if (Math.abs(x[r][c] - y[r][c]) > t) {
					return (false);
				}
			}
		}
		return (true);
	}

	/**
	 * @param x
	 * @param t
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] normalize(final double[][] x, double t, final int nr, final int nc) {
		double m = 0.0;
		t = Math.abs(t);
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				x[r][c] = (Math.abs(m = x[r][c]) < t) ? 0.0 : m;
			}
		}
		return (x);
	}

	/**
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] clear(final double[][] x, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				x[r][c] = 0.0;
			}
		}
		return (x);
	}

	/**
	 * @param x
	 * @param ni
	 * @return
	 */
	public static double[] clear(final double[] x, final int ni) {
		for (int i = 0; i < ni; i++) {
			x[i] = 0.0;
		}
		return (x);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] add(final double[][] z, final double[][] x, final double[][] y, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r][c] = x[r][c] + y[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] sub(final double[][] z, final double[][] x, final double[][] y, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r][c] = x[r][c] - y[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @param nr
	 * @param nc
	 * @param ni
	 * @return
	 */
	public static double[][] mul(final double[][] z, final double[][] x, final double[][] y, final int nr, final int nc, final int ni) {
		Matrix.clear(z, nr, nc);
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				for (int i = 0; i < ni; i++) {
					z[r][c] += (x[r][i] * y[i][c]);
				}
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param y
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[] mul(final double[] z, final double[][] x, final double[] y, final int nr, final int nc) {
		Matrix.clear(z, nr);
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r] += (x[r][c] * y[c]);
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param s
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] mul(final double[][] z, final double[][] x, final double s, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r][c] = x[r][c] * s;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param s
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] div(final double[][] z, final double[][] x, final double s, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r][c] = x[r][c] / s;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] copy(final double[][] z, final double[][] x, final int nr, final int nc) {
		if (z == x) {
			return (z);
		}
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[r][c] = x[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param nr
	 * @param nc
	 * @param mr
	 * @param mc
	 * @param dr
	 * @param dc
	 * @param sr
	 * @param sc
	 * @param br
	 * @param bc
	 * @return
	 */
	public static double[][] copy(final double[][] z, final double[][] x, final int nr, final int nc, final int mr, final int mc, final int dr, final int dc, final int sr, final int sc, final int br, final int bc) {
		for (int r = 0; r < br; r++) {
			for (int c = 0; c < bc; c++) {
				z[r + dr][c + dc] = x[r + sr][c + sc];
			}
		}
		return (z);
	}

	/**
	 * @param x
	 * @param s
	 * @param ni
	 * @return
	 */
	public static double[][] diag(final double[][] x, final double s, final int ni) {
		for (int r = 0; r < ni; r++) {
			for (int c = 0; c < ni; c++) {
				x[r][c] = (r == c) ? s : 0.0;
			}
		}
		return (x);
	}

	/**
	 * @param x
	 * @param ni
	 * @return
	 */
	public static double den(final double[][] x, final int ni) {
		double d = 0.0;
		if (ni == 1) {
			return (x[0][0]);
		}
		if (ni == 2) {
			return ((x[0][0] * x[1][1]) - (x[0][1] * x[1][0]));
		}
		for (int i = 0; i < ni; i++) {
			d += ((((-2) * (i % 2)) + 1) * x[i][0] * Matrix.comp(x, i, 0, ni));
		}
		return (d);
	}

	/**
	 * @param x
	 * @param r
	 * @param c
	 * @param ni
	 * @return
	 */
	public static double comp(final double[][] x, final int r, final int c, final int ni) {
		if (ni == 1) {
			return (1.0);
		}
		if (ni == 2) {
			return (x[1 - r][1 - c]);
		}
		final double[][] z = new double[ni - 1][ni - 1];
		final double d = Matrix.den(Matrix.minore(z, x, r, c, ni, ni), ni - 1);
		return (d);
	}

	/**
	 * @param z
	 * @param x
	 * @param dr
	 * @param dc
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] minore(final double[][] z, final double[][] x, final int dr, final int dc, final int nr, final int nc) {
		int mr = 0;
		for (int r = 0; r < nr; r++) {
			if (r != dr) {
				int mc = 0;
				for (int c = 0; c < nc; c++) {
					if (c != dc) {
						z[mr][mc] = x[r][c];
						mc++;
					}
				}
				mr++;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] trans(final double[][] z, final double[][] x, final int nr, final int nc) {
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				z[c][r] = x[r][c];
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param ni
	 * @return
	 */
	public static double[][] inv(final double[][] z, final double[][] x, final int ni) {
		Matrix.clear(z, ni, ni);
		final double d = Matrix.den(x, ni);
		for (int r = 0; r < ni; r++) {
			for (int c = 0; c < ni; c++) {
				z[c][r] = ((((-2 * (r % 2)) + 1) * ((-2 * (c % 2)) + 1)) * Matrix.comp(x, r, c, ni)) / d;
			}
		}
		return (z);
	}

	/**
	 * @param z
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] pinv(final double[][] z, final double[][] x, final int nr, final int nc) {
		int i = 0;
		int t = 0;
		final double T = 0.0005d;
		final double[][] a = new double[nr][nc];
		final double[][] c = new double[nr][1];
		final double[][] b = new double[nr][1];
		final double[][] d = new double[1][nc];
		final double[][] e = new double[1][1];
		Matrix.clear(z, nc, nr);
		for (i = 0, t = 0; (i < nr) && (t == 0); i++) {
			if (Math.abs(x[i][0]) > T) {
				t = 1;
			}
		}
		if (t == 1) {
			// a1 != 0 : z = a1(t) / (a1(t) * a1)
			Matrix.copy(a, x, nr, 1, nr, nc, 0, 0, 0, 0, nr, 1);
			Matrix.mul(e, a, a, 1, 1, nr);
			Matrix.mul(a, a, 1 / e[0][0], nr, 1);
			Matrix.copy(z, a, nc, nr, 1, nr, 0, 0, 0, 0, 1, nr);
		}
		for (int k = 2; k <= nc; k++) {
			Matrix.clear(d, k - 1, 1);
			Matrix.clear(c, nr, 1);
			Matrix.clear(b, nr, 1);
			// dk = z(k - 1) * ak
			Matrix.copy(a, x, nr, 1, nr, nc, 0, 0, 0, k - 1, nr, 1);
			Matrix.mul(d, z, a, k - 1, 1, nr);
			// ck = ak - x(k - 1) * dk
			Matrix.clear(a, nr, k - 1);
			Matrix.copy(a, x, nr, k - 1, nr, nc, 0, 0, 0, 0, nr, k - 1);
			Matrix.mul(c, a, d, nr, 1, k - 1);
			Matrix.copy(a, x, nr, 1, nr, nc, 0, 0, 0, k - 1, nr, 1);
			Matrix.sub(c, a, c, nr, 1);
			for (i = 0, t = 0; (i < nr) && (t == 0); i++) {
				if (Math.abs(c[i][0]) > T) {
					t = 1;
				}
			}
			if (t == 1) {
				// ck != 0 : bk(t) = ck(t) / (ck(t) * ck)
				Matrix.mul(e, c, c, 1, 1, nr);
				Matrix.mul(b, c, 1 / e[0][0], nr, 1);
			}
			else {
				// ck == 0 : bk(t) = (dk(t) * z(k - 1)) / (1 + dk(t) * dk)
				Matrix.mul(b, d, z, 1, nr, k - 1);
				Matrix.mul(e, d, d, 1, 1, k - 1);
				Matrix.mul(b, d, 1 / (e[0][0] + 1), nr, 1);
			}
			Matrix.clear(a, k - 1, nr);
			Matrix.mul(a, d, b, k - 1, nr, 1);
			Matrix.sub(z, z, a, k - 1, nr);
			Matrix.copy(z, b, nc, nr, 1, nr, k - 1, 0, 0, 0, 1, nr);
		}
		return (z);
	}

	/**
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double norm_sqrt(final double[][] x, final int nr, final int nc) {
		double n = 0.0;
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				n += Math.pow(x[r][c], 2);
			}
		}
		return (Math.sqrt(n));
	}

	/**
	 * @param x
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double norm_max(final double[][] x, final int nr, final int nc) {
		double a = 0.0;
		double m = 0.0;
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				m = (m > (a = Math.abs(x[r][c]))) ? m : a;
			}
		}
		return (m);
	}

	/**
	 * @param j
	 * @param p
	 * @param q
	 * @param s
	 * @param ni
	 * @return
	 */
	public static double[][] solve(final double[][] j, final double[][] p, final double[][] q, final double[][] s, final int ni) {
		Matrix.inv(p, j, ni);// p = inversa(j)
		Matrix.mul(q, p, s, ni, 1, ni);// q = p * s
		return (p);
	}

	/**
	 * @param j
	 * @param p
	 * @param q
	 * @param s
	 * @param nr
	 * @param nc
	 * @return
	 */
	public static double[][] psolve(final double[][] j, final double[][] p, final double[][] q, final double[][] s, final int nr, final int nc) {
		Matrix.pinv(p, j, nr, nc);// p = pseudoinversa(j)
		Matrix.mul(q, p, s, nc, 1, nr);// q = p * s
		return (p);
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeInt(nr);
		out.writeInt(nc);
		out.writeObject(M);
	}

	/**
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		nr = in.readInt();
		nc = in.readInt();
		M = (double[][]) in.readObject();
	}
}
