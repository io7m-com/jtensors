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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixReadable3x3DType;
import com.io7m.jtensors.MatrixWritable3x3DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import net.jcip.annotations.Immutable;

/**
 * An immutable 3x3 matrix type.
 *
 * @param <T0> A phantom type parameter.
 * @param <T1> A phantom type parameter.
 */

@EqualityStructural
@Immutable
public final class PMatrixI3x3D<T0, T1>
  implements PMatrixReadable3x3DType<T0, T1>
{
  private static final double[][] IDENTITY =
    makeIdentity();
  private static final PMatrixI3x3D<?, ?> IDENTITYM =
    makeIdentityM();
  private final double[][] elements;

  private PMatrixI3x3D(
    final double[][] e)
  {
    this.elements = e;
  }

  /**
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return The identity matrix
   */

  @SuppressWarnings("unchecked")
  public static <T0, T1> PMatrixI3x3D<T0, T1> identity()
  {
    return (PMatrixI3x3D<T0, T1>) IDENTITYM;
  }

  private static double[][] makeIdentity()
  {
    final double[][] m = new double[3][3];
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        if (row == col) {
          m[row][col] = 1.0;
        } else {
          m[row][col] = 0.0;
        }
      }
    }
    return m;
  }

  private static PMatrixI3x3D<?, ?> makeIdentityM()
  {
    return new PMatrixI3x3D<Object, Object>(IDENTITY);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given columns.
   *
   * @param column_0 The first column
   * @param column_1 The second column
   * @param column_2 The third column
   * @param <T0>     A phantom type parameter.
   * @param <T1>     A phantom type parameter.
   *
   * @return A new 3x3 matrix
   */

  public static <T0, T1> PMatrixI3x3D<T0, T1> newFromColumns(
    final VectorReadable3DType column_0,
    final VectorReadable3DType column_1,
    final VectorReadable3DType column_2)
  {
    final double[][] e = new double[3][3];

    e[0][0] = column_0.getXD();
    e[1][0] = column_0.getYD();
    e[2][0] = column_0.getZD();

    e[0][1] = column_1.getXD();
    e[1][1] = column_1.getYD();
    e[2][1] = column_1.getZD();

    e[0][2] = column_2.getXD();
    e[1][2] = column_2.getYD();
    e[2][2] = column_2.getZD();

    return new PMatrixI3x3D<T0, T1>(e);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given readable 3x3 matrix.
   *
   * @param m    The original matrix
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return A new 3x3 matrix
   */

  public static <T0, T1> PMatrixI3x3D<T0, T1> newFromReadable(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    final double[][] e = new double[3][3];

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        e[row][col] = m.getRowColumnD(row, col);
      }
    }

    return new PMatrixI3x3D<T0, T1>(e);
  }

  /**
   * Construct a new immutable 3x3 matrix from the given readable 3x3 matrix.
   *
   * @param m    The original matrix
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return A new 3x3 matrix
   */

  public static <T0, T1> PMatrixI3x3D<T0, T1> newFromReadableUntyped(
    final MatrixReadable3x3DType m)
  {
    final double[][] e = new double[3][3];

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        e[row][col] = m.getRowColumnD(row, col);
      }
    }

    return new PMatrixI3x3D<T0, T1>(e);
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
    final PMatrixI3x3D<?, ?> other = (PMatrixI3x3D<?, ?>) obj;
    return MatrixM3x3D.compareElements(this, other);
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
    return MatrixM3x3D.hashElements(this);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param m The mutable matrix
   */

  public void makeMatrix3x3D(
    final PMatrixWritable3x3DType<T0, T1> m)
  {
    this.makeMatrix3x3DUntyped(m);
  }

  /**
   * Write the current matrix into the given mutable matrix.
   *
   * @param m The mutable matrix
   */

  public void makeMatrix3x3DUntyped(
    final MatrixWritable3x3DType m)
  {
    m.setR0C0D(this.getR0C0D());
    m.setR0C1D(this.getR0C1D());
    m.setR0C2D(this.getR0C2D());

    m.setR1C0D(this.getR1C0D());
    m.setR1C1D(this.getR1C1D());
    m.setR1C2D(this.getR1C2D());

    m.setR2C0D(this.getR2C0D());
    m.setR2C1D(this.getR2C1D());
    m.setR2C2D(this.getR2C2D());
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM3x3D.showElements(this, builder);
    return builder.toString();
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
