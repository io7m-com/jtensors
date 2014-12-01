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
 * An immutable 2x2 matrix type.
 *
 * @since 7.0.0
 */

@EqualityStructural public final class MatrixI2x2F implements
  MatrixReadable2x2FType
{
  private static final float[][] IDENTITY  = MatrixI2x2F.makeIdentity();
  private static MatrixI2x2F     IDENTITYM = MatrixI2x2F.makeIdentityM();

  /**
   * @return The identity matrix
   */

  public static MatrixI2x2F identity()
  {
    return MatrixI2x2F.IDENTITYM;
  }

  private static float[][] makeIdentity()
  {
    final float[][] m = new float[2][2];
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        if (row == col) {
          m[row][col] = 1.0f;
        } else {
          m[row][col] = 0.0f;
        }
      }
    }
    return m;
  }

  private static MatrixI2x2F makeIdentityM()
  {
    return new MatrixI2x2F(MatrixI2x2F.IDENTITY);
  }

  /**
   * Construct a new immutable 2x2 matrix from the given columns.
   *
   * @param column_0
   *          The first column
   * @param column_1
   *          The second column
   *
   * @return A new 2x2 matrix
   */

  public static MatrixI2x2F newFromColumns(
    final VectorReadable2FType column_0,
    final VectorReadable2FType column_1)
  {
    final float[][] e = new float[2][2];

    e[0][0] = column_0.getXF();
    e[1][0] = column_0.getYF();

    e[0][1] = column_1.getXF();
    e[1][1] = column_1.getYF();

    return new MatrixI2x2F(e);
  }

  /**
   * Construct a new immutable 2x2 matrix from the given readable 2x2 matrix.
   *
   * @param m
   *          The original matrix
   *
   * @return A new 2x2 matrix
   */

  public static MatrixI2x2F newFromReadable(
    final MatrixReadable2x2FType m)
  {
    final float[][] e = new float[2][2];

    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        e[row][col] = m.getRowColumnF(row, col);
      }
    }

    return new MatrixI2x2F(e);
  }

  private final float[][] elements;

  private MatrixI2x2F(
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
    final MatrixI2x2F other = (MatrixI2x2F) obj;
    if (!Arrays.deepEquals(this.elements, other.elements)) {
      return false;
    }
    return true;
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    out.set2F(this.elements[row][0], this.elements[row][1]);
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

  public void makeMatrixM2x2F(
    final MatrixM2x2F m)
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m.set(row, col, this.elements[row][col]);
      }
    }
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 2; ++row) {
      final String text =
        String.format(
          "[%+.6f %+.6f]\n",
          Float.valueOf(this.elements[row][0]),
          Float.valueOf(this.elements[row][1]));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
