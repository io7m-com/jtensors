/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

import com.io7m.jnull.Nullable;
import com.io7m.jtensors.HashUtility;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.VectorWritable4DType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p> A 4x4 mutable matrix type with double precision elements. </p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM4x4D<T0, T1>
  implements PMatrixDirect4x4DType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = PMatrixM4x4D.VIEW_ROWS * PMatrixM4x4D.VIEW_COLS;
    VIEW_BYTES = PMatrixM4x4D.VIEW_ELEMENTS * PMatrixM4x4D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM4x4D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();
    MatrixM4x4D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public PMatrixM4x4D(
    final PMatrixReadable4x4DType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM4x4D.copy(source, this);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + PMatrixM4x4D.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM4x4D.indexUnsafe(
      PMatrixM4x4D.rowCheck(row), PMatrixM4x4D.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * PMatrixM4x4D.VIEW_COLS) + row;
  }

  private static <T0, T1, M extends PMatrixWritable4x4DType<T1, T0>>
  boolean invertActual(
    final PMatrixReadable4x4DType<T0, T1> m,
    final MatrixM3x3D m3,
    final PMatrixM4x4D<T1, T0> temp,
    final M out)
  {
    final double d = MatrixM4x4D.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    /**
     * This code is based on the Laplace Expansion theorem. Essentially, the
     * inverse of the matrix is calculated by taking the determinants of 3x3
     * sub-matrices of the original matrix. The sub-matrices are created by
     * removing a specific row and column to leave 9 (possibly non-adjacent)
     * cells, which are then placed in a 3x3 matrix.
     *
     * This implementation was derived from the paper "The Laplace Expansion
     * Theorem: Computing the Determinants and Inverses of Matrices" by David
     * Eberly.
     */

    final double r0c0;
    final double r0c1;
    final double r0c2;
    final double r0c3;

    final double r1c0;
    final double r1c1;
    final double r1c2;
    final double r1c3;

    final double r2c0;
    final double r2c1;
    final double r2c2;
    final double r2c3;

    final double r3c0;
    final double r3c1;
    final double r3c2;
    final double r3c3;

    {
      // Sub-matrix obtained by removing m[0, 0]
      // 1 = (-1) ^ (0 + 0)

      m3.setR0C0D(m.getR1C1D());
      m3.setR0C1D(m.getR1C2D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C1D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r0c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C2D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r0c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C1D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r0c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C1D());
      m3.setR0C2D(m.getR1C2D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r0c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C1D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r1c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r1c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r1c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r1c3 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C1D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r2c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r2c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r2c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r2c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C1D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C1D());
      m3.setR2C1D(m.getR2C2D());
      m3.setR2C2D(m.getR2C3D());

      r3c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C2D());
      m3.setR2C2D(m.getR2C3D());

      r3c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C1D());
      m3.setR2C2D(m.getR2C3D());

      r3c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C2D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C1D());
      m3.setR2C2D(m.getR2C2D());

      r3c3 = MatrixM3x3D.determinant(m3);
    }

    /**
     * Divide sub-matrix determinants by the determinant of the original
     * matrix and transpose.
     */

    temp.setR0C0D(r0c0 * d_inv);
    temp.setR0C1D(r0c1 * d_inv);
    temp.setR0C2D(r0c2 * d_inv);
    temp.setR0C3D(r0c3 * d_inv);

    temp.setR1C0D(r1c0 * d_inv);
    temp.setR1C1D(r1c1 * d_inv);
    temp.setR1C2D(r1c2 * d_inv);
    temp.setR1C3D(r1c3 * d_inv);

    temp.setR2C0D(r2c0 * d_inv);
    temp.setR2C1D(r2c1 * d_inv);
    temp.setR2C2D(r2c2 * d_inv);
    temp.setR2C3D(r2c3 * d_inv);

    temp.setR3C0D(r3c0 * d_inv);
    temp.setR3C1D(r3c1 * d_inv);
    temp.setR3C2D(r3c2 * d_inv);
    temp.setR3C3D(r3c3 * d_inv);

    MatrixM4x4D.transpose(temp, out);
    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory. If the
   * function returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param out     The output matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code true} iff the matrix was invertible
   */

  public static <T0, T1, MOUT extends PMatrixWritable4x4DType<T1, T0>>
  boolean invert(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final MOUT out)
  {
    final PMatrixM4x4D<T1, T0> m4a = (PMatrixM4x4D<T1, T0>) context.m4a;
    return PMatrixM4x4D.invertActual(m, context.m3a, m4a, out);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0     The left input vector
   * @param m1     The right input vector
   * @param out    The output vector
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   * @param <T2>   A phantom type parameter
   * @param <MOUT> The precise type of output matrix
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, MOUT extends PMatrixWritable4x4DType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable4x4DType<T1, T2> m0,
    final PMatrixReadable4x4DType<T0, T1> m1,
    final MOUT out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getR0C0D() * m1.getR0C0D();
    r0c0 += m0.getR0C1D() * m1.getR1C0D();
    r0c0 += m0.getR0C2D() * m1.getR2C0D();
    r0c0 += m0.getR0C3D() * m1.getR3C0D();

    double r1c0 = 0.0;
    r1c0 += m0.getR1C0D() * m1.getR0C0D();
    r1c0 += m0.getR1C1D() * m1.getR1C0D();
    r1c0 += m0.getR1C2D() * m1.getR2C0D();
    r1c0 += m0.getR1C3D() * m1.getR3C0D();

    double r2c0 = 0.0;
    r2c0 += m0.getR2C0D() * m1.getR0C0D();
    r2c0 += m0.getR2C1D() * m1.getR1C0D();
    r2c0 += m0.getR2C2D() * m1.getR2C0D();
    r2c0 += m0.getR2C3D() * m1.getR3C0D();

    double r3c0 = 0.0;
    r3c0 += m0.getR3C0D() * m1.getR0C0D();
    r3c0 += m0.getR3C1D() * m1.getR1C0D();
    r3c0 += m0.getR3C2D() * m1.getR2C0D();
    r3c0 += m0.getR3C3D() * m1.getR3C0D();

    double r0c1 = 0.0;
    r0c1 += m0.getR0C0D() * m1.getR0C1D();
    r0c1 += m0.getR0C1D() * m1.getR1C1D();
    r0c1 += m0.getR0C2D() * m1.getR2C1D();
    r0c1 += m0.getR0C3D() * m1.getR3C1D();

    double r1c1 = 0.0;
    r1c1 += m0.getR1C0D() * m1.getR0C1D();
    r1c1 += m0.getR1C1D() * m1.getR1C1D();
    r1c1 += m0.getR1C2D() * m1.getR2C1D();
    r1c1 += m0.getR1C3D() * m1.getR3C1D();

    double r2c1 = 0.0;
    r2c1 += m0.getR2C0D() * m1.getR0C1D();
    r2c1 += m0.getR2C1D() * m1.getR1C1D();
    r2c1 += m0.getR2C2D() * m1.getR2C1D();
    r2c1 += m0.getR2C3D() * m1.getR3C1D();

    double r3c1 = 0.0;
    r3c1 += m0.getR3C0D() * m1.getR0C1D();
    r3c1 += m0.getR3C1D() * m1.getR1C1D();
    r3c1 += m0.getR3C2D() * m1.getR2C1D();
    r3c1 += m0.getR3C3D() * m1.getR3C1D();

    double r0c2 = 0.0;
    r0c2 += m0.getR0C0D() * m1.getR0C2D();
    r0c2 += m0.getR0C1D() * m1.getR1C2D();
    r0c2 += m0.getR0C2D() * m1.getR2C2D();
    r0c2 += m0.getR0C3D() * m1.getR3C2D();

    double r1c2 = 0.0;
    r1c2 += m0.getR1C0D() * m1.getR0C2D();
    r1c2 += m0.getR1C1D() * m1.getR1C2D();
    r1c2 += m0.getR1C2D() * m1.getR2C2D();
    r1c2 += m0.getR1C3D() * m1.getR3C2D();

    double r2c2 = 0.0;
    r2c2 += m0.getR2C0D() * m1.getR0C2D();
    r2c2 += m0.getR2C1D() * m1.getR1C2D();
    r2c2 += m0.getR2C2D() * m1.getR2C2D();
    r2c2 += m0.getR2C3D() * m1.getR3C2D();

    double r3c2 = 0.0;
    r3c2 += m0.getR3C0D() * m1.getR0C2D();
    r3c2 += m0.getR3C1D() * m1.getR1C2D();
    r3c2 += m0.getR3C2D() * m1.getR2C2D();
    r3c2 += m0.getR3C3D() * m1.getR3C2D();

    double r0c3 = 0.0;
    r0c3 += m0.getR0C0D() * m1.getR0C3D();
    r0c3 += m0.getR0C1D() * m1.getR1C3D();
    r0c3 += m0.getR0C2D() * m1.getR2C3D();
    r0c3 += m0.getR0C3D() * m1.getR3C3D();

    double r1c3 = 0.0;
    r1c3 += m0.getR1C0D() * m1.getR0C3D();
    r1c3 += m0.getR1C1D() * m1.getR1C3D();
    r1c3 += m0.getR1C2D() * m1.getR2C3D();
    r1c3 += m0.getR1C3D() * m1.getR3C3D();

    double r2c3 = 0.0;
    r2c3 += m0.getR2C0D() * m1.getR0C3D();
    r2c3 += m0.getR2C1D() * m1.getR1C3D();
    r2c3 += m0.getR2C2D() * m1.getR2C3D();
    r2c3 += m0.getR2C3D() * m1.getR3C3D();

    double r3c3 = 0.0;
    r3c3 += m0.getR3C0D() * m1.getR0C3D();
    r3c3 += m0.getR3C1D() * m1.getR1C3D();
    r3c3 += m0.getR3C2D() * m1.getR2C3D();
    r3c3 += m0.getR3C3D() * m1.getR3C3D();

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR0C2D(r0c2);
    out.setR0C3D(r0c3);

    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    out.setR1C2D(r1c2);
    out.setR1C3D(r1c3);

    out.setR2C0D(r2c0);
    out.setR2C1D(r2c1);
    out.setR2C2D(r2c2);
    out.setR2C3D(r2c3);

    out.setR3C0D(r3c0);
    out.setR3C1D(r3c1);
    out.setR3C2D(r3c2);
    out.setR3C3D(r3c3);
    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM4x4D.VIEW_COLS);
    }
    return row;
  }

  private static <V extends VectorWritable4DType> V multiplyVector4DActual(
    final MatrixReadable4x4DType m,
    final VectorReadable4DType v,
    final VectorM4D va,
    final VectorM4D vb,
    final V out)
  {
    vb.copyFrom4D(v);

    m.getRow4DUnsafe(0, va);
    out.setXD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(1, va);
    out.setYD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(2, va);
    out.setZD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(3, va);
    out.setWD(VectorM4D.dotProduct(va, vb));

    return out;
  }

  /**
   * <p> Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}. </p> <p> The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p> <p> Formally,
   * this can be considered to be premultiplication of the column vector {@code
   * v} with the matrix {@code m}. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param v       The input vector
   * @param out     The output vector
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <V>     The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <T0, T1, V extends PVectorWritable4DType<T1>> V
  multiplyVector4D(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final PVectorReadable4DType<T0> v,
    final V out)
  {
    return PMatrixM4x4D.multiplyVector4DActual(
      m, v, context.v4a, context.v4b, out);
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
    final PMatrixM4x4D<?, ?> other = (PMatrixM4x4D<?, ?>) obj;
    for (int index = 0; index < PMatrixM4x4D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public DoubleBuffer getDirectDoubleBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    PMatrixM4x4D.rowCheck(row);
    this.getRow4DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable4DType> void getRow4DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixM4x4D.indexUnsafe(row, 1));
    final double z = this.view.get(PMatrixM4x4D.indexUnsafe(row, 2));
    final double w = this.view.get(PMatrixM4x4D.indexUnsafe(row, 3));
    out.set4D(x, y, z, w);
  }

  @Override public double getR0C3D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(0, 3));
  }

  @Override public void setR0C3D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 3), x);
  }

  @Override public void setRowWith4D(
    final int row,
    final VectorReadable4DType v)
  {
    PMatrixM4x4D.rowCheck(row);
    this.setRowWith4DUnsafe(row, v);
  }

  @Override public void setRowWith4DUnsafe(
    final int row,
    final VectorReadable4DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 2), v.getZD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 3), v.getWD());
  }

  @Override public void setRow0With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 2), v.getZD());
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 3), v.getWD());
  }

  @Override public void setRow1With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 2), v.getZD());
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 3), v.getWD());
  }

  @Override public void setRow2With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 2), v.getZD());
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 3), v.getWD());
  }

  @Override public void setRow3With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 2), v.getZD());
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 3), v.getWD());
  }

  @Override public double getR1C3D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(1, 3));
  }

  @Override public void setR1C3D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 3), x);
  }

  @Override public double getR2C3D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(2, 3));
  }

  @Override public void setR2C3D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 3), x);
  }

  @Override public double getR3C0D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(3, 0));
  }

  @Override public void setR3C0D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 0), x);
  }

  @Override public double getR3C1D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(3, 1));
  }

  @Override public void setR3C1D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 1), x);
  }

  @Override public double getR3C2D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(3, 2));
  }

  @Override public void setR3C2D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 2), x);
  }

  @Override public double getR3C3D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(3, 3));
  }

  @Override public void setR3C3D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(3, 3), x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM4x4D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C1D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C2D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C3D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM4x4D.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < PMatrixM4x4D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 0));
      final double c1 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 1));
      final double c2 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 2));
      final double c3 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.15f %+.15f %+.15f %+.15f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    PMatrixM4x4D.rowCheck(row);
    this.getRow3DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixM4x4D.indexUnsafe(row, 1));
    final double z = this.view.get(PMatrixM4x4D.indexUnsafe(row, 2));
    out.set3D(x, y, z);
  }

  @Override public double getR0C2D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(0, 2));
  }

  @Override public void setR0C2D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    PMatrixM4x4D.rowCheck(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 1), v.getYD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 2), v.getZD());
  }

  @Override public double getR1C2D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(1, 2));
  }

  @Override public void setR1C2D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 2), x);
  }

  @Override public double getR2C0D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(2, 0));
  }

  @Override public void setR2C0D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 0), x);
  }

  @Override public double getR2C1D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(2, 1));
  }

  @Override public void setR2C1D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 1), x);
  }

  @Override public double getR2C2D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(2, 2));
  }

  @Override public void setR2C2D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    PMatrixM4x4D.rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixM4x4D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    PMatrixM4x4D.rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixM4x4D.indexUnsafe(row, 1), v.getYD());
  }

  @Override public double getR1C0D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(PMatrixM4x4D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(1, 1), x);
  }

  private interface Phantom2Type
  {
    // Type-level only.
  }

  /**
   * <p>The {@code ContextPM4D} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM4x4D} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM4D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM4D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM4D
  {
    private final MatrixM3x3D        m3a = new MatrixM3x3D();
    private final PMatrixM4x4D<?, ?> m4a = new PMatrixM4x4D<Object, Object>();
    private final PMatrixM4x4D<?, ?> m4b = new PMatrixM4x4D<Object, Object>();
    private final VectorM3D          v3a = new VectorM3D();
    private final VectorM3D          v3b = new VectorM3D();
    private final VectorM3D          v3c = new VectorM3D();
    private final VectorM3D          v3d = new VectorM3D();
    private final VectorM4D          v4a = new VectorM4D();
    private final VectorM4D          v4b = new VectorM4D();

    /**
     * Construct a new context.
     */

    public ContextPM4D()
    {

    }
  }
}
