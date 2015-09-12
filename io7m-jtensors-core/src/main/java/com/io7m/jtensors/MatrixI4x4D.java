/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jtensors;

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

import java.util.Arrays;

/**
 * An immutable 4x4 matrix type.
 *
 * @since 7.0.0
 */

@EqualityStructural @Immutable public final class MatrixI4x4D implements
  MatrixReadable4x4DType
{
  private static final double[][] IDENTITY  = MatrixI4x4D.makeIdentity();
  private static final MatrixI4x4D IDENTITYM = MatrixI4x4D.makeIdentityM();

  /**
   * @return The identity matrix
   */

  public static MatrixI4x4D identity()
  {
    return MatrixI4x4D.IDENTITYM;
  }

  private static double[][] makeIdentity()
  {
    final double[][] m = new double[4][4];
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        if (row == col) {
          m[row][col] = 1.0;
        } else {
          m[row][col] = 0.0;
        }
      }
    }
    return m;
  }

  private static MatrixI4x4D makeIdentityM()
  {
    return new MatrixI4x4D(MatrixI4x4D.IDENTITY);
  }

  /**
   * Construct a new immutable 4x4 matrix from the given columns.
   *
   * @param column_0
   *          The first column
   * @param column_1
   *          The second column
   * @param column_2
   *          The third column
   * @param column_3
   *          The fourth column
   *
   * @return A new 4x4 matrix
   */

  public static MatrixI4x4D newFromColumns(
    final VectorReadable4DType column_0,
    final VectorReadable4DType column_1,
    final VectorReadable4DType column_2,
    final VectorReadable4DType column_3)
  {
    final double[][] e = new double[4][4];

    e[0][0] = column_0.getXD();
    e[1][0] = column_0.getYD();
    e[2][0] = column_0.getZD();
    e[3][0] = column_0.getWD();

    e[0][1] = column_1.getXD();
    e[1][1] = column_1.getYD();
    e[2][1] = column_1.getZD();
    e[3][1] = column_1.getWD();

    e[0][2] = column_2.getXD();
    e[1][2] = column_2.getYD();
    e[2][2] = column_2.getZD();
    e[3][2] = column_2.getWD();

    e[0][3] = column_3.getXD();
    e[1][3] = column_3.getYD();
    e[2][3] = column_3.getZD();
    e[3][3] = column_3.getWD();

    return new MatrixI4x4D(e);
  }

  /**
   * Construct a new immutable 4x4 matrix from the given readable 4x4 matrix.
   *
   * @param m
   *          The original matrix
   *
   * @return A new 4x4 matrix
   */

  public static MatrixI4x4D newFromReadable(
    final MatrixReadable4x4DType m)
  {
    final double[][] e = new double[4][4];

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        e[row][col] = m.getRowColumnD(row, col);
      }
    }

    return new MatrixI4x4D(e);
  }

  private final double[][] elements;

  private MatrixI4x4D(
    final double[][] e)
  {
    this.elements = e;
  }

  @Override public boolean equals(
    final @Nullable Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final MatrixI4x4D other = (MatrixI4x4D) obj;
    return Arrays.deepEquals(this.elements, other.elements);
  }

  @Override public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    out.set4D(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  /**
   * @param row
   *          The row
   * @param col
   *          The column
   * @return The value at the given row and column
   */

  @Override public double getRowColumnD(
    final int row,
    final int col)
  {
    return this.elements[row][col];
  }

  @Override public int hashCode()
  {
    return Arrays.hashCode(this.elements);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param m
   *          The mutable matrix
   */

  public void makeMatrixM4x4D(
    final MatrixM4x4D m)
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m.set(row, col, this.elements[row][col]);
      }
    }
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 4; ++row) {
      final String text =
        String.format(
          "[%+.15f %+.15f %+.15f %+.15f]\n",
          Double.valueOf(this.elements[row][0]),
          Double.valueOf(this.elements[row][1]),
          Double.valueOf(this.elements[row][2]),
          Double.valueOf(this.elements[row][3]));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
