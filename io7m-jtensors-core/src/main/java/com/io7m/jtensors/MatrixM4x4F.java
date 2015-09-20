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

package com.io7m.jtensors;

import com.io7m.jnull.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//@formatter:off

/**
 * <p>
 * A 4x4 mutable matrix type with single precision elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM4x4F} are backed by direct memory, with the
 * rows and columns of the matrices being stored in column-major format. This
 * allows the matrices to be passed to OpenGL directly, without requiring
 * transposition.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization.
 * </p>
 * <p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * </p>
 * <p>
 * See http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations
 * for the three <i>elementary</i> operations defined on matrices.
 * </p>
 */

//@formatter:on

public final class MatrixM4x4F implements MatrixDirect4x4FType
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
    VIEW_ELEMENTS = MatrixM4x4F.VIEW_ROWS * MatrixM4x4F.VIEW_COLS;
    VIEW_BYTES = MatrixM4x4F.VIEW_ELEMENTS * MatrixM4x4F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM4x4F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    MatrixM4x4F.setIdentity(this);
    this.view.rewind();
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public MatrixM4x4F(
    final MatrixReadable4x4FType source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.rewind();

    MatrixM4x4F.copy(source, this);
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M add(
    final MatrixReadable4x4FType m0,
    final MatrixReadable4x4FType m1,
    final M out)
  {
    final float r0c0 = m0.getR0C0F() + m1.getR0C0F();
    final float r1c0 = m0.getR1C0F() + m1.getR1C0F();
    final float r2c0 = m0.getR2C0F() + m1.getR2C0F();
    final float r3c0 = m0.getR3C0F() + m1.getR3C0F();

    final float r0c1 = m0.getR0C1F() + m1.getR0C1F();
    final float r1c1 = m0.getR1C1F() + m1.getR1C1F();
    final float r2c1 = m0.getR2C1F() + m1.getR2C1F();
    final float r3c1 = m0.getR3C1F() + m1.getR3C1F();

    final float r0c2 = m0.getR0C2F() + m1.getR0C2F();
    final float r1c2 = m0.getR1C2F() + m1.getR1C2F();
    final float r2c2 = m0.getR2C2F() + m1.getR2C2F();
    final float r3c2 = m0.getR3C2F() + m1.getR3C2F();

    final float r0c3 = m0.getR0C3F() + m1.getR0C3F();
    final float r1c3 = m0.getR1C3F() + m1.getR1C3F();
    final float r2c3 = m0.getR2C3F() + m1.getR2C3F();
    final float r3c3 = m0.getR3C3F() + m1.getR3C3F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);
    out.setR2C0F(r2c0);
    out.setR3C0F(r3c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    out.setR2C1F(r2c1);
    out.setR3C1F(r3c1);

    out.setR0C2F(r0c2);
    out.setR1C2F(r1c2);
    out.setR2C2F(r2c2);
    out.setR3C2F(r3c2);

    out.setR0C3F(r0c3);
    out.setR1C3F(r1c3);
    out.setR2C3F(r2c3);
    out.setR3C3F(r3c3);
    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param <M> The precise type of matrix
   *
   * @return m0
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  addInPlace(
    final M m0,
    final MatrixReadable4x4FType m1)
  {
    return MatrixM4x4F.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row_a   The row on the lefthand side of the addition
   * @param row_b   The row on the righthand side of the addition
   * @param row_c   The destination row
   * @param r       The scaling value
   * @param <M>     The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  addRowScaledInPlace(
    final ContextMM4F context,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM4x4F.addRowScaled(context, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable4x4FType> M addRowScaledUnsafe(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM4F va,
    final VectorM4F vb,
    final M out)
  {
    m.getRow4FUnsafe(row_a, va);
    m.getRow4FUnsafe(row_b, vb);
    VectorM4F.addScaledInPlace(va, vb, r);
    out.setRowWith4FUnsafe(row_c, va);
    return out;
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. The function uses storage preallocated in {@code
   * context} to avoid any new allocations. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row_a   The row on the lefthand side of the addition
   * @param row_b   The row on the righthand side of the addition
   * @param row_c   The destination row
   * @param r       The scaling value
   * @param out     The output matrix
   * @param <M>     The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M addRowScaled(
    final ContextMM4F context,
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final M out)
  {
    return MatrixM4x4F.addRowScaledUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      MatrixM4x4F.rowCheck(row_c),
      r,
      context.v4a,
      context.v4b,
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + MatrixM4x4F.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <M>    The precise type of matrix
   *
   * @return {@code output}
   */

  public static <M extends MatrixWritable4x4FType> M copy(
    final MatrixReadable4x4FType input,
    final M output)
  {
    output.setR0C0F(input.getR0C0F());
    output.setR0C1F(input.getR0C1F());
    output.setR0C2F(input.getR0C2F());
    output.setR0C3F(input.getR0C3F());

    output.setR1C0F(input.getR1C0F());
    output.setR1C1F(input.getR1C1F());
    output.setR1C2F(input.getR1C2F());
    output.setR1C3F(input.getR1C3F());

    output.setR2C0F(input.getR2C0F());
    output.setR2C1F(input.getR2C1F());
    output.setR2C2F(input.getR2C2F());
    output.setR2C3F(input.getR2C3F());

    output.setR3C0F(input.getR3C0F());
    output.setR3C1F(input.getR3C1F());
    output.setR3C2F(input.getR3C2F());
    output.setR3C3F(input.getR3C3F());

    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m The input matrix
   *
   * @return The determinant.
   */

  public static double determinant(
    final MatrixReadable4x4FType m)
  {
    final double r0c0 = (double) m.getR0C0F();
    final double r1c0 = (double) m.getR1C0F();
    final double r2c0 = (double) m.getR2C0F();
    final double r3c0 = (double) m.getR3C0F();

    final double r0c1 = (double) m.getR0C1F();
    final double r1c1 = (double) m.getR1C1F();
    final double r2c1 = (double) m.getR2C1F();
    final double r3c1 = (double) m.getR3C1F();

    final double r0c2 = (double) m.getR0C2F();
    final double r1c2 = (double) m.getR1C2F();
    final double r2c2 = (double) m.getR2C2F();
    final double r3c2 = (double) m.getR3C2F();

    final double r0c3 = (double) m.getR0C3F();
    final double r1c3 = (double) m.getR1C3F();
    final double r2c3 = (double) m.getR2C3F();
    final double r3c3 = (double) m.getR3C3F();

    double sum = 0.0;

    sum += r0c0 * r1c1 * r2c2 * r3c3;
    sum -= r0c0 * r1c1 * r2c3 * r3c2;
    sum += r0c0 * r1c2 * r2c3 * r3c1;
    sum -= r0c0 * r1c2 * r2c1 * r3c3;

    sum += r0c0 * r1c3 * r2c1 * r3c2;
    sum -= r0c0 * r1c3 * r2c2 * r3c1;
    sum -= r0c1 * r1c2 * r2c3 * r3c0;
    sum += r0c1 * r1c2 * r2c0 * r3c3;

    sum -= r0c1 * r1c3 * r2c0 * r3c2;
    sum += r0c1 * r1c3 * r2c2 * r3c0;
    sum -= r0c1 * r1c0 * r2c2 * r3c3;
    sum += r0c1 * r1c0 * r2c3 * r3c2;

    sum += r0c2 * r1c3 * r2c0 * r3c1;
    sum -= r0c2 * r1c3 * r2c1 * r3c0;
    sum += r0c2 * r1c0 * r2c1 * r3c3;
    sum -= r0c2 * r1c0 * r2c3 * r3c1;

    sum += r0c2 * r1c1 * r2c3 * r3c0;
    sum -= r0c2 * r1c1 * r2c0 * r3c3;
    sum -= r0c3 * r1c0 * r2c1 * r3c2;
    sum += r0c3 * r1c0 * r2c2 * r3c1;

    sum -= r0c3 * r1c1 * r2c2 * r3c0;
    sum += r0c3 * r1c1 * r2c0 * r3c2;
    sum -= r0c3 * r1c2 * r2c0 * r3c1;
    sum += r0c3 * r1c2 * r2c1 * r3c0;

    return sum;
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. The function uses
   * storage preallocated in {@code context} to avoid allocating memory. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row_a   The first row
   * @param row_b   The second row
   * @param <M>     The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  exchangeRowsInPlace(
    final ContextMM4F context,
    final M m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4F.exchangeRows(context, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable4x4FType> M exchangeRowsUnsafe(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final VectorM4F va,
    final VectorM4F vb,
    final M out)
  {
    m.getRow4FUnsafe(row_a, va);
    m.getRow4FUnsafe(row_b, vb);
    out.setRowWith4FUnsafe(row_a, vb);
    out.setRowWith4FUnsafe(row_b, va);
    return out;
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out} . </p> <p> The function
   * uses storage preallocated in {@code context} to avoid allocating memory.
   * </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row_a   The first row
   * @param row_b   The second row
   * @param out     The output matrix
   * @param <M>     The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M exchangeRows(
    final ContextMM4F context,
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return MatrixM4x4F.exchangeRowsUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      context.v4a,
      context.v4b,
      out);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4F.indexUnsafe(
      MatrixM4x4F.rowCheck(row), MatrixM4x4F.columnCheck(column));
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
    return (column * MatrixM4x4F.VIEW_COLS) + row;
  }

  private static <M extends MatrixWritable4x4FType> boolean invertActual(
    final MatrixReadable4x4FType m,
    final MatrixM3x3F m3,
    final MatrixM4x4F temp,
    final M out)
  {
    final double d = MatrixM4x4F.determinant(m);

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
    return true;
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
   * @param <M>     The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType>
  boolean invertInPlace(
    final ContextMM4F context,
    final M m)
  {
    return MatrixM4x4F.invert(context, m, m);
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
   * @param <M>     The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   */

  public static <M extends MatrixWritable4x4FType> boolean invert(
    final ContextMM4F context,
    final MatrixReadable4x4FType m,
    final M out)
  {
    return MatrixM4x4F.invertActual(m, context.m3a, context.m4a, out);
  }

  /**
   * <p> Calculate a matrix representing a "camera" looking from the point
   * {@code origin} to the point {@code target}. {@code target} must represent
   * the "up" vector for the camera. Usually, this is simply a unit vector
   * {@code (0, 1, 0)} representing the Y axis. </p> <p> The function uses
   * preallocated storage from {@code context}. </p> <p> The view is expressed
   * as a rotation and translation matrix, written to {@code out_matrix}. </p>
   *
   * @param context    Preallocated storage
   * @param out_matrix The output matrix
   * @param origin     The position of the viewer
   * @param target     The target being viewed
   * @param up         The up vector
   * @param <M>        The precise type of matrix
   */

  public static <M extends MatrixWritable4x4FType> void lookAt(
    final ContextMM4F context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final M out_matrix)
  {
    final VectorM3F forward = context.v3a;
    final VectorM3F new_up = context.v3b;
    final VectorM3F side = context.v3c;
    final VectorM3F move = context.v3d;
    final MatrixM4x4F rotation = context.m4a;
    final MatrixM4x4F translation = context.m4b;

    MatrixM4x4F.setIdentity(rotation);
    MatrixM4x4F.setIdentity(translation);
    MatrixM4x4F.setIdentity(out_matrix);

    /**
     * Calculate "forward" vector
     */

    forward.set3F(
      target.getXF() - origin.getXF(),
      target.getYF() - origin.getYF(),
      target.getZF() - origin.getZF());
    VectorM3F.normalizeInPlace(forward);

    /**
     * Calculate "side" vector
     */

    VectorM3F.crossProduct(forward, up, side);
    VectorM3F.normalizeInPlace(side);

    /**
     * Calculate new "up" vector
     */

    VectorM3F.crossProduct(side, forward, new_up);

    /**
     * Calculate rotation matrix
     */

    rotation.setR0C0F(side.getXF());
    rotation.setR0C1F(side.getYF());
    rotation.setR0C2F(side.getZF());
    rotation.setR1C0F(new_up.getXF());
    rotation.setR1C1F(new_up.getYF());
    rotation.setR1C2F(new_up.getZF());
    rotation.setR2C0F(-forward.getXF());
    rotation.setR2C1F(-forward.getYF());
    rotation.setR2C2F(-forward.getZF());

    /**
     * Calculate camera translation matrix
     */

    move.set3F(-origin.getXF(), -origin.getYF(), -origin.getZF());
    MatrixM4x4F.makeTranslation3F(move, translation);

    /**
     * Produce output matrix
     */

    MatrixM4x4F.multiply(rotation, translation, out_matrix);
  }

  /**
   * <p> Generate a matrix that represents a rotation of {@code angle} radians
   * around the axis {@code axis} and save to {@code out}. </p> <p> The function
   * assumes a right-handed coordinate system and therefore a positive rotation
   * around any axis represents a counter-clockwise rotation around that axis.
   * </p>
   *
   * @param angle The angle in radians
   * @param axis  The axis
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M makeRotation(
    final double angle,
    final VectorReadable3FType axis,
    final M out)
  {
    final double axis_x = (double) axis.getXF();
    final double axis_y = (double) axis.getYF();
    final double axis_z = (double) axis.getZF();

    final double s = Math.sin(angle);
    final double c = Math.cos(angle);
    final double t = 1.0 - c;

    final double tx_sq = t * (axis_x * axis_x);
    final double ty_sq = t * (axis_y * axis_y);
    final double tz_sq = t * (axis_z * axis_z);

    final double txy = t * (axis_x * axis_y);
    final double txz = t * (axis_x * axis_z);
    final double tyz = t * (axis_y * axis_z);

    final double sx = s * axis_x;
    final double sy = s * axis_y;
    final double sz = s * axis_z;

    final double r0c0 = tx_sq + c;
    final double r0c1 = txy - sz;
    final double r0c2 = txz + sy;
    final double r0c3 = 0.0;

    final double r1c0 = txy + sz;
    final double r1c1 = ty_sq + c;
    final double r1c2 = tyz - sx;
    final double r1c3 = 0.0;

    final double r2c0 = txz - sy;
    final double r2c1 = tyz + sx;
    final double r2c2 = tz_sq + c;
    final double r2c3 = 0.0;

    final double r3c0 = 0.0;
    final double r3c1 = 0.0;
    final double r3c2 = 0.0;
    final double r3c3 = 1.0;

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

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y)} from
   * the origin, and save to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M makeTranslation2F(
    final VectorReadable2FType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F(0.0f);
    out.setR0C3F(v.getXF());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F(0.0f);
    out.setR1C3F(v.getYF());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    out.setR2C3F(0.0f);

    out.setR3C0F(0.0f);
    out.setR3C1F(0.0f);
    out.setR3C2F(0.0f);
    out.setR3C3F(1.0f);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y)} from
   * the origin, and save to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M makeTranslation2I(
    final VectorReadable2IType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F(0.0f);
    out.setR0C3F((float) v.getXI());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F(0.0f);
    out.setR1C3F((float) v.getYI());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    out.setR2C3F(0.0f);

    out.setR3C0F(0.0f);
    out.setR3C1F(0.0f);
    out.setR3C2F(0.0f);
    out.setR3C3F(1.0f);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y, v.z)}
   * from the origin, and save to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M makeTranslation3F(
    final VectorReadable3FType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F(0.0f);
    out.setR0C3F(v.getXF());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F(0.0f);
    out.setR1C3F(v.getYF());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    out.setR2C3F(v.getZF());

    out.setR3C0F(0.0f);
    out.setR3C1F(0.0f);
    out.setR3C2F(0.0f);
    out.setR3C3F(1.0f);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y, v.z)}
   * from the origin, and save to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M makeTranslation3I(
    final VectorReadable3IType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F(0.0f);
    out.setR0C3F((float) v.getXI());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F(0.0f);
    out.setR1C3F((float) v.getYI());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    out.setR2C3F((float) v.getZI());

    out.setR3C0F(0.0f);
    out.setR3C1F(0.0f);
    out.setR3C2F(0.0f);
    out.setR3C3F(1.0f);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0  The left input vector
   * @param m1  The right input vector
   * @param out The output vector
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M multiply(
    final MatrixReadable4x4FType m0,
    final MatrixReadable4x4FType m1,
    final M out)
  {
    float r0c0 = 0.0F;
    r0c0 += m0.getR0C0F() * m1.getR0C0F();
    r0c0 += m0.getR0C1F() * m1.getR1C0F();
    r0c0 += m0.getR0C2F() * m1.getR2C0F();
    r0c0 += m0.getR0C3F() * m1.getR3C0F();

    float r1c0 = 0.0F;
    r1c0 += m0.getR1C0F() * m1.getR0C0F();
    r1c0 += m0.getR1C1F() * m1.getR1C0F();
    r1c0 += m0.getR1C2F() * m1.getR2C0F();
    r1c0 += m0.getR1C3F() * m1.getR3C0F();

    float r2c0 = 0.0F;
    r2c0 += m0.getR2C0F() * m1.getR0C0F();
    r2c0 += m0.getR2C1F() * m1.getR1C0F();
    r2c0 += m0.getR2C2F() * m1.getR2C0F();
    r2c0 += m0.getR2C3F() * m1.getR3C0F();

    float r3c0 = 0.0F;
    r3c0 += m0.getR3C0F() * m1.getR0C0F();
    r3c0 += m0.getR3C1F() * m1.getR1C0F();
    r3c0 += m0.getR3C2F() * m1.getR2C0F();
    r3c0 += m0.getR3C3F() * m1.getR3C0F();

    float r0c1 = 0.0F;
    r0c1 += m0.getR0C0F() * m1.getR0C1F();
    r0c1 += m0.getR0C1F() * m1.getR1C1F();
    r0c1 += m0.getR0C2F() * m1.getR2C1F();
    r0c1 += m0.getR0C3F() * m1.getR3C1F();

    float r1c1 = 0.0F;
    r1c1 += m0.getR1C0F() * m1.getR0C1F();
    r1c1 += m0.getR1C1F() * m1.getR1C1F();
    r1c1 += m0.getR1C2F() * m1.getR2C1F();
    r1c1 += m0.getR1C3F() * m1.getR3C1F();

    float r2c1 = 0.0F;
    r2c1 += m0.getR2C0F() * m1.getR0C1F();
    r2c1 += m0.getR2C1F() * m1.getR1C1F();
    r2c1 += m0.getR2C2F() * m1.getR2C1F();
    r2c1 += m0.getR2C3F() * m1.getR3C1F();

    float r3c1 = 0.0F;
    r3c1 += m0.getR3C0F() * m1.getR0C1F();
    r3c1 += m0.getR3C1F() * m1.getR1C1F();
    r3c1 += m0.getR3C2F() * m1.getR2C1F();
    r3c1 += m0.getR3C3F() * m1.getR3C1F();

    float r0c2 = 0.0F;
    r0c2 += m0.getR0C0F() * m1.getR0C2F();
    r0c2 += m0.getR0C1F() * m1.getR1C2F();
    r0c2 += m0.getR0C2F() * m1.getR2C2F();
    r0c2 += m0.getR0C3F() * m1.getR3C2F();

    float r1c2 = 0.0F;
    r1c2 += m0.getR1C0F() * m1.getR0C2F();
    r1c2 += m0.getR1C1F() * m1.getR1C2F();
    r1c2 += m0.getR1C2F() * m1.getR2C2F();
    r1c2 += m0.getR1C3F() * m1.getR3C2F();

    float r2c2 = 0.0F;
    r2c2 += m0.getR2C0F() * m1.getR0C2F();
    r2c2 += m0.getR2C1F() * m1.getR1C2F();
    r2c2 += m0.getR2C2F() * m1.getR2C2F();
    r2c2 += m0.getR2C3F() * m1.getR3C2F();

    float r3c2 = 0.0F;
    r3c2 += m0.getR3C0F() * m1.getR0C2F();
    r3c2 += m0.getR3C1F() * m1.getR1C2F();
    r3c2 += m0.getR3C2F() * m1.getR2C2F();
    r3c2 += m0.getR3C3F() * m1.getR3C2F();

    float r0c3 = 0.0F;
    r0c3 += m0.getR0C0F() * m1.getR0C3F();
    r0c3 += m0.getR0C1F() * m1.getR1C3F();
    r0c3 += m0.getR0C2F() * m1.getR2C3F();
    r0c3 += m0.getR0C3F() * m1.getR3C3F();

    float r1c3 = 0.0F;
    r1c3 += m0.getR1C0F() * m1.getR0C3F();
    r1c3 += m0.getR1C1F() * m1.getR1C3F();
    r1c3 += m0.getR1C2F() * m1.getR2C3F();
    r1c3 += m0.getR1C3F() * m1.getR3C3F();

    float r2c3 = 0.0F;
    r2c3 += m0.getR2C0F() * m1.getR0C3F();
    r2c3 += m0.getR2C1F() * m1.getR1C3F();
    r2c3 += m0.getR2C2F() * m1.getR2C3F();
    r2c3 += m0.getR2C3F() * m1.getR3C3F();

    float r3c3 = 0.0F;
    r3c3 += m0.getR3C0F() * m1.getR0C3F();
    r3c3 += m0.getR3C1F() * m1.getR1C3F();
    r3c3 += m0.getR3C2F() * m1.getR2C3F();
    r3c3 += m0.getR3C3F() * m1.getR3C3F();

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR0C2F(r0c2);
    out.setR0C3F(r0c3);

    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);
    out.setR1C2F(r1c2);
    out.setR1C3F(r1c3);

    out.setR2C0F(r2c0);
    out.setR2C1F(r2c1);
    out.setR2C2F(r2c2);
    out.setR2C3F(r2c3);

    out.setR3C0F(r3c0);
    out.setR3C1F(r3c1);
    out.setR3C2F(r3c2);
    out.setR3C3F(r3c3);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param m0  The left input vector
   * @param m1  The right input vector
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable4x4FType m1)
  {
    return MatrixM4x4F.multiply(m0, m1, m0);
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
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}. The function uses preallocated storage in
   * {@code context} to avoid allocating memory.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param v       The input vector
   * @param out     The output vector
   * @param <V>     The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <V extends VectorWritable4FType> V multiplyVector4F(
    final ContextMM4F context,
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final V out)
  {
    return MatrixM4x4F.multiplyVector4FActual(
      m, v, context.v4a, context.v4b, out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM4x4F.VIEW_COLS);
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param out The output matrix
   * @param m   The input matrix
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M scale(
    final MatrixReadable4x4FType m,
    final double r,
    final M out)
  {
    final float r0c0 = (float) ((double) m.getR0C0F() * r);
    final float r1c0 = (float) ((double) m.getR1C0F() * r);
    final float r2c0 = (float) ((double) m.getR2C0F() * r);
    final float r3c0 = (float) ((double) m.getR3C0F() * r);

    final float r0c1 = (float) ((double) m.getR0C1F() * r);
    final float r1c1 = (float) ((double) m.getR1C1F() * r);
    final float r2c1 = (float) ((double) m.getR2C1F() * r);
    final float r3c1 = (float) ((double) m.getR3C1F() * r);

    final float r0c2 = (float) ((double) m.getR0C2F() * r);
    final float r1c2 = (float) ((double) m.getR1C2F() * r);
    final float r2c2 = (float) ((double) m.getR2C2F() * r);
    final float r3c2 = (float) ((double) m.getR3C2F() * r);

    final float r0c3 = (float) ((double) m.getR0C3F() * r);
    final float r1c3 = (float) ((double) m.getR1C3F() * r);
    final float r2c3 = (float) ((double) m.getR2C3F() * r);
    final float r3c3 = (float) ((double) m.getR3C3F() * r);

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);
    out.setR2C0F(r2c0);
    out.setR3C0F(r3c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    out.setR2C1F(r2c1);
    out.setR3C1F(r3c1);

    out.setR0C2F(r0c2);
    out.setR1C2F(r1c2);
    out.setR2C2F(r2c2);
    out.setR3C2F(r3c2);

    out.setR0C3F(r0c3);
    out.setR1C3F(r1c3);
    out.setR2C3F(r2c3);
    out.setR3C3F(r3c3);

    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param m   The input matrix
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  scaleInPlace(
    final M m,
    final double r)
  {
    return MatrixM4x4F.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code row} of the matrix {@code m} by {@code r} , saving the
   * result to row {@code r} of {@code m}. The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row     The index of the row {@code (0 <= row < 4)}
   * @param r       The scaling value
   * @param <M>     The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  scaleRowInPlace(
    final ContextMM4F context,
    final M m,
    final int row,
    final double r)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m, MatrixM4x4F.rowCheck(row), r, context.v4a, m);
  }

  private static <M extends MatrixWritable4x4FType> M scaleRowUnsafe(
    final MatrixReadable4x4FType m,
    final int row,
    final double r,
    final VectorM4F tmp,
    final M out)
  {
    m.getRow4FUnsafe(row, tmp);
    VectorM4F.scaleInPlace(tmp, r);
    out.setRowWith4FUnsafe(row, tmp);
    return out;
  }

  /**
   * <p> Scale row {@code row} of the matrix {@code m} by {@code r} , saving the
   * result to row {@code r} of {@code out}. The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param row     The index of the row {@code (0 <= row < 4)}
   * @param r       The scaling value
   * @param out     The output matrix
   * @param <M>     The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M scaleRow(
    final ContextMM4F context,
    final MatrixReadable4x4FType m,
    final int row,
    final double r,
    final M out)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m, MatrixM4x4F.rowCheck(row), r, context.v4a, out);
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType> M setIdentity(
    final M m)
  {
    m.setR0C0F(1.0f);
    m.setR1C0F(0.0f);
    m.setR2C0F(0.0f);
    m.setR3C0F(0.0f);

    m.setR0C1F(0.0f);
    m.setR1C1F(1.0f);
    m.setR2C1F(0.0f);
    m.setR3C1F(0.0f);

    m.setR0C2F(0.0f);
    m.setR1C2F(0.0f);
    m.setR2C2F(1.0f);
    m.setR3C2F(0.0f);

    m.setR0C3F(0.0f);
    m.setR1C3F(0.0f);
    m.setR2C3F(0.0f);
    m.setR3C3F(1.0f);

    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m   The matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType> M setZero(
    final M m)
  {
    m.setR0C0F(0.0f);
    m.setR1C0F(0.0f);
    m.setR2C0F(0.0f);
    m.setR3C0F(0.0f);

    m.setR0C1F(0.0f);
    m.setR1C1F(0.0f);
    m.setR2C1F(0.0f);
    m.setR3C1F(0.0f);

    m.setR0C2F(0.0f);
    m.setR1C2F(0.0f);
    m.setR2C2F(0.0f);
    m.setR3C2F(0.0f);

    m.setR0C3F(0.0f);
    m.setR1C3F(0.0f);
    m.setR2C3F(0.0f);
    m.setR3C3F(0.0f);

    return m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as the sum
   * of the diagonal elements of the matrix.
   *
   * @param m The input matrix
   *
   * @return The trace of the matrix
   *
   * @since 5.0.0
   */

  public static double trace(
    final MatrixReadable4x4FType m)
  {
    return (double) (m.getR0C0F() + m.getR1C1F() + m.getR2C2F() + m.getR3C3F());
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param m   The input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable4x4FType> M transpose(
    final MatrixReadable4x4FType m,
    final M out)
  {
    final float r0c0 = m.getR0C0F();
    final float r1c0 = m.getR1C0F();
    final float r2c0 = m.getR2C0F();
    final float r3c0 = m.getR3C0F();

    final float r0c1 = m.getR0C1F();
    final float r1c1 = m.getR1C1F();
    final float r2c1 = m.getR2C1F();
    final float r3c1 = m.getR3C1F();

    final float r0c2 = m.getR0C2F();
    final float r1c2 = m.getR1C2F();
    final float r2c2 = m.getR2C2F();
    final float r3c2 = m.getR3C2F();

    final float r0c3 = m.getR0C3F();
    final float r1c3 = m.getR1C3F();
    final float r2c3 = m.getR2C3F();
    final float r3c3 = m.getR3C3F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r0c1); // swap 0
    out.setR2C0F(r0c2); // swap 1
    out.setR3C0F(r0c3); // swap 2

    out.setR0C1F(r1c0); // swap 0
    out.setR1C1F(r1c1);
    out.setR2C1F(r1c2); // swap 3
    out.setR3C1F(r1c3); // swap 4

    out.setR0C2F(r2c0); // swap 1
    out.setR1C2F(r2c1); // swap 3
    out.setR2C2F(r2c2);
    out.setR3C2F(r2c3); // swap 5

    out.setR0C3F(r3c0); // swap 2
    out.setR1C3F(r3c1); // swap 4
    out.setR2C3F(r3c2); // swap 5
    out.setR3C3F(r3c3);

    return out;
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable4x4FType & MatrixReadable4x4FType> M
  transposeInPlace(
    final M m)
  {
    final float r1c0 = m.getR1C0F();
    final float r2c0 = m.getR2C0F();
    final float r3c0 = m.getR3C0F();

    final float r0c1 = m.getR0C1F();
    final float r2c1 = m.getR2C1F();
    final float r3c1 = m.getR3C1F();

    final float r0c2 = m.getR0C2F();
    final float r1c2 = m.getR1C2F();
    final float r3c2 = m.getR3C2F();

    final float r0c3 = m.getR0C3F();
    final float r1c3 = m.getR1C3F();
    final float r2c3 = m.getR2C3F();

    m.setR1C0F(r0c1); // swap 0
    m.setR2C0F(r0c2); // swap 1
    m.setR3C0F(r0c3); // swap 2

    m.setR0C1F(r1c0); // swap 0
    m.setR2C1F(r1c2); // swap 3
    m.setR3C1F(r1c3); // swap 4

    m.setR0C2F(r2c0); // swap 1
    m.setR1C2F(r2c1); // swap 3
    m.setR3C2F(r2c3); // swap 5

    m.setR0C3F(r3c0); // swap 2
    m.setR1C3F(r3c1); // swap 4
    m.setR2C3F(r3c2); // swap 5

    return m;
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
    final MatrixM4x4F other = (MatrixM4x4F) obj;

    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
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
    MatrixM4x4F.rowCheck(row);
    this.getRow4FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable4FType> void getRow4FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixM4x4F.indexUnsafe(row, 1));
    final float z = this.view.get(MatrixM4x4F.indexUnsafe(row, 2));
    final float w = this.view.get(MatrixM4x4F.indexUnsafe(row, 3));
    out.set4F(x, y, z, w);
  }

  @Override public float getR0C3F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(0, 3));
  }

  @Override public void setR0C3F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(0, 3), x);
  }

  @Override public float getR1C3F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(1, 3));
  }

  @Override public void setR1C3F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(1, 3), x);
  }

  @Override public float getR2C3F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(2, 3));
  }

  @Override public void setR2C3F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(2, 3), x);
  }

  @Override public float getR3C0F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(3, 0));
  }

  @Override public void setR3C0F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(3, 0), x);
  }

  @Override public float getR3C1F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(3, 1));
  }

  @Override public void setR3C1F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(3, 1), x);
  }

  @Override public float getR3C2F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(3, 2));
  }

  @Override public void setR3C2F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(3, 2), x);
  }

  @Override public float getR3C3F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(3, 3));
  }

  @Override public void setR3C3F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(3, 3), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM4x4F.indexChecked(row, column));
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
    this.view.put(MatrixM4x4F.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixM4x4F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(MatrixM4x4F.indexUnsafe(row, 0));
      final float c1 = this.view.get(MatrixM4x4F.indexUnsafe(row, 1));
      final float c2 = this.view.get(MatrixM4x4F.indexUnsafe(row, 2));
      final float c3 = this.view.get(MatrixM4x4F.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.6f %+.6f %+.6f %+.6f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public void setRowWith4F(
    final int row,
    final VectorReadable4FType v)
  {
    MatrixM4x4F.rowCheck(row);
    this.setRowWith4FUnsafe(row, v);
  }

  @Override public void setRowWith4FUnsafe(
    final int row,
    final VectorReadable4FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 2), v.getZF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 3), v.getWF());
  }

  @Override public void setRow0With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(0, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(0, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(0, 2), v.getZF());
    this.view.put(MatrixM4x4F.indexUnsafe(0, 3), v.getWF());
  }

  @Override public void setRow1With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(1, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(1, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(1, 2), v.getZF());
    this.view.put(MatrixM4x4F.indexUnsafe(1, 3), v.getWF());
  }

  @Override public void setRow2With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(2, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(2, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(2, 2), v.getZF());
    this.view.put(MatrixM4x4F.indexUnsafe(2, 3), v.getWF());
  }

  @Override public void setRow3With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(3, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(3, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(3, 2), v.getZF());
    this.view.put(MatrixM4x4F.indexUnsafe(3, 3), v.getWF());
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    MatrixM4x4F.rowCheck(row);
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixM4x4F.indexUnsafe(row, 1));
    final float z = this.view.get(MatrixM4x4F.indexUnsafe(row, 2));
    out.set3F(x, y, z);
  }

  @Override public float getR0C2F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(0, 2));
  }

  @Override public void setR0C2F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    MatrixM4x4F.rowCheck(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 2), v.getZF());
  }

  @Override public float getR1C2F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(1, 2));
  }

  @Override public void setR1C2F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(1, 2), x);
  }

  @Override public float getR2C0F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(2, 0));
  }

  @Override public void setR2C0F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(2, 0), x);
  }

  @Override public float getR2C1F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(2, 1));
  }

  @Override public void setR2C1F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(2, 1), x);
  }

  @Override public float getR2C2F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(2, 2));
  }

  @Override public void setR2C2F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    MatrixM4x4F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixM4x4F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    MatrixM4x4F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixM4x4F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public float getR1C0F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(1, 1), x);
  }

  /**
   * <p>The {@code ContextMM4F} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM4x4F} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM4F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM4F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM4F
  {
    private final MatrixM3x3F m3a = new MatrixM3x3F();
    private final MatrixM4x4F m4a = new MatrixM4x4F();
    private final MatrixM4x4F m4b = new MatrixM4x4F();
    private final VectorM3F   v3a = new VectorM3F();
    private final VectorM3F   v3b = new VectorM3F();
    private final VectorM3F   v3c = new VectorM3F();
    private final VectorM3F   v3d = new VectorM3F();
    private final VectorM4F   v4a = new VectorM4F();
    private final VectorM4F   v4b = new VectorM4F();

    /**
     * Construct a new context.
     */

    public ContextMM4F()
    {

    }
  }
}
