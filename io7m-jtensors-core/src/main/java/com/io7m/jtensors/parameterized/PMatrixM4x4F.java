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

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.HashUtility;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;
import com.io7m.jtensors.VectorWritable4FType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <p> A 4x4 mutable matrix type with single precision elements. </p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM4x4F<T0, T1>
  implements PMatrixDirect4x4FType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = PMatrixM4x4F.VIEW_ROWS * PMatrixM4x4F.VIEW_COLS;
    VIEW_BYTES = PMatrixM4x4F.VIEW_ELEMENTS * PMatrixM4x4F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM4x4F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();
    MatrixM4x4F.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public PMatrixM4x4F(
    final PMatrixReadable4x4FType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM4x4F.copy(source, this);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + PMatrixM4x4F.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM4x4F.indexUnsafe(
      PMatrixM4x4F.rowCheck(row), PMatrixM4x4F.columnCheck(column));
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
    return (column * PMatrixM4x4F.VIEW_COLS) + row;
  }

  private static <T0, T1, M extends PMatrixWritable4x4FType<T1, T0>>
  OptionType<M> invertActual(
    final PMatrixReadable4x4FType<T0, T1> m,
    final MatrixM3x3F m3,
    final PMatrixM4x4F<T1, T0> temp,
    final M out)
  {
    final double d = MatrixM4x4F.determinant(m);

    if (d == 0.0) {
      return Option.none();
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

      m3.setR0C0F(m.getR1C1F());
      m3.setR0C1F(m.getR1C2F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C1F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r0c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C2F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r0c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C1F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r0c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C1F());
      m3.setR0C2F(m.getR1C2F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r0c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C1F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r1c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r1c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r1c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r1c3 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C1F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r2c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r2c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r2c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r2c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C1F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C1F());
      m3.setR2C1F(m.getR2C2F());
      m3.setR2C2F(m.getR2C3F());

      r3c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C2F());
      m3.setR2C2F(m.getR2C3F());

      r3c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C1F());
      m3.setR2C2F(m.getR2C3F());

      r3c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C2F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C1F());
      m3.setR2C2F(m.getR2C2F());

      r3c3 = MatrixM3x3F.determinant(m3);
    }

    /**
     * Divide sub-matrix determinants by the determinant of the original
     * matrix and transpose.
     */

    temp.setR0C0F((float) (r0c0 * d_inv));
    temp.setR0C1F((float) (r0c1 * d_inv));
    temp.setR0C2F((float) (r0c2 * d_inv));
    temp.setR0C3F((float) (r0c3 * d_inv));

    temp.setR1C0F((float) (r1c0 * d_inv));
    temp.setR1C1F((float) (r1c1 * d_inv));
    temp.setR1C2F((float) (r1c2 * d_inv));
    temp.setR1C3F((float) (r1c3 * d_inv));

    temp.setR2C0F((float) (r2c0 * d_inv));
    temp.setR2C1F((float) (r2c1 * d_inv));
    temp.setR2C2F((float) (r2c2 * d_inv));
    temp.setR2C3F((float) (r2c3 * d_inv));

    temp.setR3C0F((float) (r3c0 * d_inv));
    temp.setR3C1F((float) (r3c1 * d_inv));
    temp.setR3C2F((float) (r3c2 * d_inv));
    temp.setR3C3F((float) (r3c3 * d_inv));

    MatrixM4x4F.transpose(temp, out);
    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(out)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory. If the
   * function returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MIN>   The precise type of input matrix
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code m}
   */

  public static <T0, T1, MIN extends PMatrixWritable4x4FType<T0, T1> &
    PMatrixReadable4x4FType<T0, T1>, MOUT extends PMatrixWritable4x4FType<T1,
    T0> & PMatrixReadable4x4FType<T1, T0>> OptionType<MOUT> invertInPlace(
    final ContextPM4F context,
    final MIN m)
  {
    final PMatrixM4x4F<T1, T0> mt = (PMatrixM4x4F<T1, T0>) m;
    return (OptionType<MOUT>) PMatrixM4x4F.invert(context, m, mt);
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
   * @return {@code out}
   */

  public static <T0, T1, MOUT extends PMatrixWritable4x4FType<T1, T0>>
  OptionType<MOUT> invert(
    final ContextPM4F context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final MOUT out)
  {
    final PMatrixM4x4F<T1, T0> m4a = (PMatrixM4x4F<T1, T0>) context.m4a;
    return PMatrixM4x4F.invertActual(m, context.m3a, m4a, out);
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

  public static <T0, T1, T2, MOUT extends PMatrixWritable4x4FType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable4x4FType<T1, T2> m0,
    final PMatrixReadable4x4FType<T0, T1> m1,
    final MOUT out)
  {
    double r0c0 = 0.0;
    r0c0 += (double) (m0.getR0C0F() * m1.getR0C0F());
    r0c0 += (double) (m0.getR0C1F() * m1.getR1C0F());
    r0c0 += (double) (m0.getR0C2F() * m1.getR2C0F());
    r0c0 += (double) (m0.getR0C3F() * m1.getR3C0F());

    double r1c0 = 0.0;
    r1c0 += (double) (m0.getR1C0F() * m1.getR0C0F());
    r1c0 += (double) (m0.getR1C1F() * m1.getR1C0F());
    r1c0 += (double) (m0.getR1C2F() * m1.getR2C0F());
    r1c0 += (double) (m0.getR1C3F() * m1.getR3C0F());

    double r2c0 = 0.0;
    r2c0 += (double) (m0.getR2C0F() * m1.getR0C0F());
    r2c0 += (double) (m0.getR2C1F() * m1.getR1C0F());
    r2c0 += (double) (m0.getR2C2F() * m1.getR2C0F());
    r2c0 += (double) (m0.getR2C3F() * m1.getR3C0F());

    double r3c0 = 0.0;
    r3c0 += (double) (m0.getR3C0F() * m1.getR0C0F());
    r3c0 += (double) (m0.getR3C1F() * m1.getR1C0F());
    r3c0 += (double) (m0.getR3C2F() * m1.getR2C0F());
    r3c0 += (double) (m0.getR3C3F() * m1.getR3C0F());

    double r0c1 = 0.0;
    r0c1 += (double) (m0.getR0C0F() * m1.getR0C1F());
    r0c1 += (double) (m0.getR0C1F() * m1.getR1C1F());
    r0c1 += (double) (m0.getR0C2F() * m1.getR2C1F());
    r0c1 += (double) (m0.getR0C3F() * m1.getR3C1F());

    double r1c1 = 0.0;
    r1c1 += (double) (m0.getR1C0F() * m1.getR0C1F());
    r1c1 += (double) (m0.getR1C1F() * m1.getR1C1F());
    r1c1 += (double) (m0.getR1C2F() * m1.getR2C1F());
    r1c1 += (double) (m0.getR1C3F() * m1.getR3C1F());

    double r2c1 = 0.0;
    r2c1 += (double) (m0.getR2C0F() * m1.getR0C1F());
    r2c1 += (double) (m0.getR2C1F() * m1.getR1C1F());
    r2c1 += (double) (m0.getR2C2F() * m1.getR2C1F());
    r2c1 += (double) (m0.getR2C3F() * m1.getR3C1F());

    double r3c1 = 0.0;
    r3c1 += (double) (m0.getR3C0F() * m1.getR0C1F());
    r3c1 += (double) (m0.getR3C1F() * m1.getR1C1F());
    r3c1 += (double) (m0.getR3C2F() * m1.getR2C1F());
    r3c1 += (double) (m0.getR3C3F() * m1.getR3C1F());

    double r0c2 = 0.0;
    r0c2 += (double) (m0.getR0C0F() * m1.getR0C2F());
    r0c2 += (double) (m0.getR0C1F() * m1.getR1C2F());
    r0c2 += (double) (m0.getR0C2F() * m1.getR2C2F());
    r0c2 += (double) (m0.getR0C3F() * m1.getR3C2F());

    double r1c2 = 0.0;
    r1c2 += (double) (m0.getR1C0F() * m1.getR0C2F());
    r1c2 += (double) (m0.getR1C1F() * m1.getR1C2F());
    r1c2 += (double) (m0.getR1C2F() * m1.getR2C2F());
    r1c2 += (double) (m0.getR1C3F() * m1.getR3C2F());

    double r2c2 = 0.0;
    r2c2 += (double) (m0.getR2C0F() * m1.getR0C2F());
    r2c2 += (double) (m0.getR2C1F() * m1.getR1C2F());
    r2c2 += (double) (m0.getR2C2F() * m1.getR2C2F());
    r2c2 += (double) (m0.getR2C3F() * m1.getR3C2F());

    double r3c2 = 0.0;
    r3c2 += (double) (m0.getR3C0F() * m1.getR0C2F());
    r3c2 += (double) (m0.getR3C1F() * m1.getR1C2F());
    r3c2 += (double) (m0.getR3C2F() * m1.getR2C2F());
    r3c2 += (double) (m0.getR3C3F() * m1.getR3C2F());

    double r0c3 = 0.0;
    r0c3 += (double) (m0.getR0C0F() * m1.getR0C3F());
    r0c3 += (double) (m0.getR0C1F() * m1.getR1C3F());
    r0c3 += (double) (m0.getR0C2F() * m1.getR2C3F());
    r0c3 += (double) (m0.getR0C3F() * m1.getR3C3F());

    double r1c3 = 0.0;
    r1c3 += (double) (m0.getR1C0F() * m1.getR0C3F());
    r1c3 += (double) (m0.getR1C1F() * m1.getR1C3F());
    r1c3 += (double) (m0.getR1C2F() * m1.getR2C3F());
    r1c3 += (double) (m0.getR1C3F() * m1.getR3C3F());

    double r2c3 = 0.0;
    r2c3 += (double) (m0.getR2C0F() * m1.getR0C3F());
    r2c3 += (double) (m0.getR2C1F() * m1.getR1C3F());
    r2c3 += (double) (m0.getR2C2F() * m1.getR2C3F());
    r2c3 += (double) (m0.getR2C3F() * m1.getR3C3F());

    double r3c3 = 0.0;
    r3c3 += (double) (m0.getR3C0F() * m1.getR0C3F());
    r3c3 += (double) (m0.getR3C1F() * m1.getR1C3F());
    r3c3 += (double) (m0.getR3C2F() * m1.getR2C3F());
    r3c3 += (double) (m0.getR3C3F() * m1.getR3C3F());

    out.setR0C0F((float) r0c0);
    out.setR0C1F((float) r0c1);
    out.setR0C2F((float) r0c2);
    out.setR0C3F((float) r0c3);

    out.setR1C0F((float) r1c0);
    out.setR1C1F((float) r1c1);
    out.setR1C2F((float) r1c2);
    out.setR1C3F((float) r1c3);

    out.setR2C0F((float) r2c0);
    out.setR2C1F((float) r2c1);
    out.setR2C2F((float) r2c2);
    out.setR2C3F((float) r2c3);

    out.setR3C0F((float) r3c0);
    out.setR3C1F((float) r3c1);
    out.setR3C2F((float) r3c2);
    out.setR3C3F((float) r3c3);
    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM4x4F.VIEW_COLS);
    }
    return row;
  }

  private static <V extends VectorWritable4FType> V multiplyVector4FActual(
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final VectorM4F va,
    final VectorM4F vb,
    final V out)
  {
    vb.copyFrom4F(v);

    m.getRow4FUnsafe(0, va);
    out.setXF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(1, va);
    out.setYF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(2, va);
    out.setZF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(3, va);
    out.setWF((float) VectorM4F.dotProduct(va, vb));

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

  public static <T0, T1, V extends PVectorWritable4FType<T1>> V
  multiplyVector4F(
    final ContextPM4F context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final PVectorReadable4FType<T0> v,
    final V out)
  {
    return PMatrixM4x4F.multiplyVector4FActual(
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
    final PMatrixM4x4F<?, ?> other = (PMatrixM4x4F<?, ?>) obj;
    for (int index = 0; index < PMatrixM4x4F.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public FloatBuffer getDirectFloatBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    PMatrixM4x4F.rowCheck(row);
    this.getRow4FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable4FType> void getRow4FUnsafe(
    final int row,
    final V out)
  {
    final double x = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 0));
    final double y = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 1));
    final double z = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 2));
    final double w = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 3));
    out.set4F((float) x, (float) y, (float) z, (float) w);
  }

  @Override public float getR0C3F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(0, 3));
  }

  @Override public void setR0C3F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 3), x);
  }

  @Override public void setRowWith4F(
    final int row,
    final VectorReadable4FType v)
  {
    PMatrixM4x4F.rowCheck(row);
    this.setRowWith4FUnsafe(row, v);
  }

  @Override public void setRowWith4FUnsafe(
    final int row,
    final VectorReadable4FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 2), v.getZF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 3), v.getWF());
  }

  @Override public void setRow0With4F(final VectorReadable4FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 2), v.getZF());
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 3), v.getWF());
  }

  @Override public void setRow1With4F(final VectorReadable4FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 2), v.getZF());
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 3), v.getWF());
  }

  @Override public void setRow2With4F(final VectorReadable4FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 2), v.getZF());
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 3), v.getWF());
  }

  @Override public void setRow3With4F(final VectorReadable4FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 2), v.getZF());
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 3), v.getWF());
  }

  @Override public float getR1C3F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(1, 3));
  }

  @Override public void setR1C3F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 3), x);
  }

  @Override public float getR2C3F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(2, 3));
  }

  @Override public void setR2C3F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 3), x);
  }

  @Override public float getR3C0F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(3, 0));
  }

  @Override public void setR3C0F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 0), x);
  }

  @Override public float getR3C1F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(3, 1));
  }

  @Override public void setR3C1F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 1), x);
  }

  @Override public float getR3C2F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(3, 2));
  }

  @Override public void setR3C2F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 2), x);
  }

  @Override public float getR3C3F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(3, 3));
  }

  @Override public void setR3C3F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(3, 3), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM4x4F.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C1F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C2F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C3F(), prime, r);

    return r;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixM4x4F.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < PMatrixM4x4F.VIEW_ROWS; ++row) {
      final double c0 =
        (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 0));
      final double c1 =
        (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 1));
      final double c2 =
        (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 2));
      final double c3 =
        (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.15f %+.15f %+.15f %+.15f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    PMatrixM4x4F.rowCheck(row);
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final double x = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 0));
    final double y = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 1));
    final double z = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 2));
    out.set3F((float) x, (float) y, (float) z);
  }

  @Override public float getR0C2F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(0, 2));
  }

  @Override public void setR0C2F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    PMatrixM4x4F.rowCheck(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 2), v.getZF());
  }

  @Override public float getR1C2F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(1, 2));
  }

  @Override public void setR1C2F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 2), x);
  }

  @Override public float getR2C0F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(2, 0));
  }

  @Override public void setR2C0F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 0), x);
  }

  @Override public float getR2C1F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(2, 1));
  }

  @Override public void setR2C1F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 1), x);
  }

  @Override public float getR2C2F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(2, 2));
  }

  @Override public void setR2C2F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    PMatrixM4x4F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final double x = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 0));
    final double y = (double) this.view.get(PMatrixM4x4F.indexUnsafe(row, 1));
    out.set2F((float) x, (float) y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    PMatrixM4x4F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixM4x4F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public float getR1C0F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(1, 1), x);
  }

  private interface Phantom2Type
  {
    // Type-level only.
  }

  /**
   * <p>The {@code ContextPM4F} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM4x4F} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM4F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM4F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM4F
  {
    private final MatrixM3x3F        m3a = new MatrixM3x3F();
    private final PMatrixM4x4F<?, ?> m4a = new PMatrixM4x4F<Object, Object>();
    private final PMatrixM4x4F<?, ?> m4b = new PMatrixM4x4F<Object, Object>();
    private final VectorM3F          v3a = new VectorM3F();
    private final VectorM3F          v3b = new VectorM3F();
    private final VectorM3F          v3c = new VectorM3F();
    private final VectorM3F          v3d = new VectorM3F();
    private final VectorM4F          v4a = new VectorM4F();
    private final VectorM4F          v4b = new VectorM4F();

    /**
     * Construct a new context.
     */

    public ContextPM4F()
    {

    }
  }
}
