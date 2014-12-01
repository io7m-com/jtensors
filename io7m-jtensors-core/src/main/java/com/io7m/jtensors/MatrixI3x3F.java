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

import java.util.Arrays;

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;

/**
 * An immutable 3x3 matrix type.
 */

@EqualityStructural public final class MatrixI3x3F implements
  MatrixReadable3x3FType
{
  private static final float[][] IDENTITY  = MatrixI3x3F.makeIdentity();
  private static MatrixI3x3F     IDENTITYM = MatrixI3x3F.makeIdentityM();

  /**
   * @return The identity matrix
   */

  public static MatrixI3x3F identity()
  {
    return MatrixI3x3F.IDENTITYM;
  }

  private static float[][] makeIdentity()
  {
    final float[][] m = new float[3][3];
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        if (row == col) {
          m[row][col] = 1.0f;
        } else {
          m[row][col] = 0.0f;
        }
      }
    }
    return m;
  }

  private static MatrixI3x3F makeIdentityM()
  {
    return new MatrixI3x3F(MatrixI3x3F.IDENTITY);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given columns.
   *
   * @param column_0
   *          The first column
   * @param column_1
   *          The second column
   * @param column_2
   *          The third column
   *
   * @return A new 3x3 matrix
   */

  public static MatrixI3x3F newFromColumns(
    final VectorReadable3FType column_0,
    final VectorReadable3FType column_1,
    final VectorReadable3FType column_2)
  {
    final float[][] e = new float[3][3];

    e[0][0] = column_0.getXF();
    e[1][0] = column_0.getYF();
    e[2][0] = column_0.getZF();

    e[0][1] = column_1.getXF();
    e[1][1] = column_1.getYF();
    e[2][1] = column_1.getZF();

    e[0][2] = column_2.getXF();
    e[1][2] = column_2.getYF();
    e[2][2] = column_2.getZF();

    return new MatrixI3x3F(e);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given readable 3x3 matrix.
   *
   * @param m
   *          The original matrix
   *
   * @return A new 3x3 matrix
   */

  public static MatrixI3x3F newFromReadable(
    final MatrixReadable3x3FType m)
  {
    final float[][] e = new float[3][3];

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        e[row][col] = m.getRowColumnF(row, col);
      }
    }

    return new MatrixI3x3F(e);
  }

  private final float[][] elements;

  private MatrixI3x3F(
    final float[][] e)
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
    final MatrixI3x3F other = (MatrixI3x3F) obj;
    if (!Arrays.deepEquals(this.elements, other.elements)) {
      return false;
    }
    return true;
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    out.set3F(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2]);
  }

  /**
   * @param row
   *          The row
   * @param col
   *          The column
   * @return The value at the given row and column
   */

  @Override public float getRowColumnF(
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

  public void makeMatrixM3x3F(
    final MatrixM3x3F m)
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m.set(row, col, this.elements[row][col]);
      }
    }
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 3; ++row) {
      final String text =
        String.format(
          "[%+.6f %+.6f %+.6f]\n",
          Float.valueOf(this.elements[row][0]),
          Float.valueOf(this.elements[row][1]),
          Float.valueOf(this.elements[row][2]));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
