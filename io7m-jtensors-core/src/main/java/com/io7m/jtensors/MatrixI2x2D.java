/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

/**
 * An immutable 2x2 matrix type.
 *
 * @since 7.0.0
 */

@EqualityStructural @Immutable public final class MatrixI2x2D
  implements MatrixReadable2x2DType
{
  private static final double[][]  IDENTITY  = MatrixI2x2D.makeIdentity();
  private static final MatrixI2x2D IDENTITYM = MatrixI2x2D.makeIdentityM();
  private final double[][] elements;

  private MatrixI2x2D(
    final double[][] e)
  {
    this.elements = e;
  }

  /**
   * @return The identity matrix
   */

  public static MatrixI2x2D identity()
  {
    return MatrixI2x2D.IDENTITYM;
  }

  private static double[][] makeIdentity()
  {
    final double[][] m = new double[2][2];
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        if (row == col) {
          m[row][col] = 1.0;
        } else {
          m[row][col] = 0.0;
        }
      }
    }
    return m;
  }

  private static MatrixI2x2D makeIdentityM()
  {
    return new MatrixI2x2D(MatrixI2x2D.IDENTITY);
  }

  /**
   * Construct a new immutable 2x2 matrix from the given columns.
   *
   * @param column_0 The first column
   * @param column_1 The second column
   *
   * @return A new 2x2 matrix
   */

  public static MatrixI2x2D newFromColumns(
    final VectorReadable2DType column_0,
    final VectorReadable2DType column_1)
  {
    final double[][] e = new double[2][2];

    e[0][0] = column_0.getXD();
    e[1][0] = column_0.getYD();

    e[0][1] = column_1.getXD();
    e[1][1] = column_1.getYD();

    return new MatrixI2x2D(e);
  }

  /**
   * Construct a new immutable 2x2 matrix from the given readable 2x2 matrix.
   *
   * @param m The original matrix
   *
   * @return A new 2x2 matrix
   */

  public static MatrixI2x2D newFromReadable(
    final MatrixReadable2x2DType m)
  {
    final double[][] e = new double[2][2];

    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        e[row][col] = m.getRowColumnD(row, col);
      }
    }

    return new MatrixI2x2D(e);
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
    final MatrixI2x2D other = (MatrixI2x2D) obj;
    return MatrixM2x2D.compareElements(this, other);
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    out.set2D(this.elements[row][0], this.elements[row][1]);
  }

  @Override public double getR0C0D()
  {
    return this.elements[0][0];
  }

  @Override public double getR1C0D()
  {
    return this.elements[1][0];
  }

  @Override public double getR0C1D()
  {
    return this.elements[0][1];
  }

  @Override public double getR1C1D()
  {
    return this.elements[1][1];
  }

  /**
   * @param row The row
   * @param col The column
   *
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
    return MatrixM2x2D.hashElements(this);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param <M> The precise type of mutable matrix
   * @param m   The mutable matrix
   */

  public <M extends MatrixWritable2x2DType> void makeMatrix2x2D(
    final M m)
  {
    m.setR0C0D(this.getR0C0D());
    m.setR1C0D(this.getR1C0D());
    m.setR0C1D(this.getR0C1D());
    m.setR1C1D(this.getR1C1D());
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM2x2D.showElements(this, builder);
    return builder.toString();
  }
}
