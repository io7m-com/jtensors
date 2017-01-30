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
 * An immutable 4x4 matrix type.
 *
 * @since 7.0.0
 */

@EqualityStructural
@Immutable
public final class MatrixI4x4D
  implements MatrixReadable4x4DType
{
  private static final double[][] IDENTITY = makeIdentity();
  private static final MatrixI4x4D IDENTITYM = makeIdentityM();
  private final double[][] elements;

  private MatrixI4x4D(
    final double[][] e)
  {
    this.elements = e;
  }

  /**
   * @return The identity matrix
   */

  public static MatrixI4x4D identity()
  {
    return IDENTITYM;
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
    return new MatrixI4x4D(IDENTITY);
  }

  /**
   * Construct a new immutable 4x4 matrix from the given columns.
   *
   * @param column_0 The first column
   * @param column_1 The second column
   * @param column_2 The third column
   * @param column_3 The fourth column
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
   * @param m The original matrix
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

  @Override
  public boolean equals(
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
    return MatrixM4x4D.compareElements(this, other);
  }

  @Override
  public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    out.set4D(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  @Override
  public <V extends VectorWritable4DType> void getRow4DUnsafe(
    final int row,
    final V out)
  {
    out.set4D(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  @Override
  public double getR0C3D()
  {
    return this.elements[0][3];
  }

  @Override
  public double getR1C3D()
  {
    return this.elements[1][3];
  }

  @Override
  public double getR2C3D()
  {
    return this.elements[2][3];
  }

  @Override
  public double getR3C0D()
  {
    return this.elements[3][0];
  }

  @Override
  public double getR3C1D()
  {
    return this.elements[3][1];
  }

  @Override
  public double getR3C2D()
  {
    return this.elements[3][2];
  }

  @Override
  public double getR3C3D()
  {
    return this.elements[3][3];
  }

  /**
   * @param row The row
   * @param col The column
   *
   * @return The value at the given row and column
   */

  @Override
  public double getRowColumnD(
    final int row,
    final int col)
  {
    return this.elements[row][col];
  }

  @Override
  public int hashCode()
  {
    return MatrixM4x4D.hashElements(this);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The mutable matrix
   */

  public <M extends MatrixWritable4x4DType> void makeMatrixM4x4D(
    final M m)
  {
    m.setR0C0D(this.getR0C0D());
    m.setR0C1D(this.getR0C1D());
    m.setR0C2D(this.getR0C2D());
    m.setR0C3D(this.getR0C3D());

    m.setR1C0D(this.getR1C0D());
    m.setR1C1D(this.getR1C1D());
    m.setR1C2D(this.getR1C2D());
    m.setR1C3D(this.getR1C3D());

    m.setR2C0D(this.getR2C0D());
    m.setR2C1D(this.getR2C1D());
    m.setR2C2D(this.getR2C2D());
    m.setR2C3D(this.getR2C3D());

    m.setR3C0D(this.getR3C0D());
    m.setR3C1D(this.getR3C1D());
    m.setR3C2D(this.getR3C2D());
    m.setR3C3D(this.getR3C3D());
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM4x4D.showElements(this, builder);
    return builder.toString();
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    this.getRow3DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    out.set3D(
      this.elements[row][0], this.elements[row][1], this.elements[row][2]);
  }

  @Override
  public double getR0C2D()
  {
    return this.elements[0][2];
  }

  @Override
  public double getR1C2D()
  {
    return this.elements[1][2];
  }

  @Override
  public double getR2C0D()
  {
    return this.elements[2][0];
  }

  @Override
  public double getR2C1D()
  {
    return this.elements[2][1];
  }

  @Override
  public double getR2C2D()
  {
    return this.elements[2][2];
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    out.set2D(this.elements[row][0], this.elements[row][1]);
  }

  @Override
  public double getR0C0D()
  {
    return this.elements[0][0];
  }

  @Override
  public double getR1C0D()
  {
    return this.elements[1][0];
  }

  @Override
  public double getR0C1D()
  {
    return this.elements[0][1];
  }

  @Override
  public double getR1C1D()
  {
    return this.elements[1][1];
  }
}
