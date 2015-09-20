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
import java.nio.DoubleBuffer;

//@formatter:off

/**
 * <p>
 * A 2x2 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM2x2D} are backed by direct memory, with the
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

public final class MatrixM2x2D implements MatrixDirect2x2DType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM2x2D.VIEW_ROWS * MatrixM2x2D.VIEW_COLS;
    VIEW_BYTES = MatrixM2x2D.VIEW_ELEMENTS * MatrixM2x2D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new matrix, initialized to the identity matrix.
   */

  public MatrixM2x2D()
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM2x2D.VIEW_BYTES);
    assert b != null;
    b.order(order);

    final DoubleBuffer v = b.asDoubleBuffer();
    assert v != null;

    this.data = b;
    this.view = v;
    this.view.clear();

    MatrixM2x2D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public MatrixM2x2D(
    final MatrixReadable2x2DType source)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM2x2D.VIEW_BYTES);
    assert b != null;
    b.order(order);

    final DoubleBuffer d = b.asDoubleBuffer();
    assert d != null;

    this.data = b;
    this.view = d;
    this.view.clear();

    MatrixM2x2D.copy(source, this);
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

  public static <M extends MatrixWritable2x2DType> M add(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1,
    final M out)
  {
    final double r0c0 = m0.getR0C0D() + m1.getR0C0D();
    final double r1c0 = m0.getR1C0D() + m1.getR1C0D();
    final double r0c1 = m0.getR0C1D() + m1.getR0C1D();
    final double r1c1 = m0.getR1C1D() + m1.getR1C1D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r1c0);
    out.setR0C1D(r0c1);
    out.setR1C1D(r1c1);
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
   * @return {@code m0}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  addInPlace(
    final M m0,
    final MatrixReadable2x2DType m1)
  {
    return MatrixM2x2D.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M addRowScaled(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final M out)
  {
    return MatrixM2x2D.addRowScaledUnsafe(
      m,
      MatrixM2x2D.rowCheck(row_a),
      MatrixM2x2D.rowCheck(row_b),
      MatrixM2x2D.rowCheck(row_c),
      r,
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param <M>   The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  addRowScaledInPlace(
    final ContextMM2D c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM2x2D.addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable2x2DType> M addRowScaledUnsafe(
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM2D va,
    final VectorM2D vb,
    final M out)
  {
    m.getRow2DUnsafe(row_a, va);
    m.getRow2DUnsafe(row_b, vb);
    VectorM2D.addScaledInPlace(va, vb, r);
    out.setRowWith2DUnsafe(row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column > 1)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 2");
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

  public static <M extends MatrixWritable2x2DType> M copy(
    final MatrixReadable2x2DType input,
    final M output)
  {
    output.setR0C0D(input.getR0C0D());
    output.setR1C0D(input.getR1C0D());
    output.setR0C1D(input.getR0C1D());
    output.setR1C1D(input.getR1C1D());
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
    final MatrixReadable2x2DType m)
  {
    final double r0c0 = m.getR0C0D();
    final double r0c1 = m.getR0C1D();
    final double r1c0 = m.getR1C0D();
    final double r1c1 = m.getR1C1D();

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out} . </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M exchangeRows(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return MatrixM2x2D.exchangeRowsUnsafe(
      m,
      MatrixM2x2D.rowCheck(row_a),
      MatrixM2x2D.rowCheck(row_b),
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m} . </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param <M>   The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  exchangeRowsInPlace(
    final ContextMM2D c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2D.exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable2x2DType> M exchangeRowsUnsafe(
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final VectorM2D va,
    final VectorM2D vb,
    final M out)
  {
    m.getRow2DUnsafe(row_a, va);
    m.getRow2DUnsafe(row_b, vb);
    out.setRowWith2DUnsafe(row_a, vb);
    out.setRowWith2DUnsafe(row_b, va);
    return out;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2D.indexUnsafe(
      MatrixM2x2D.rowCheck(row), MatrixM2x2D.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 2) + column, corresponds to row-major storage. (column * 2) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param m   The input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2D#determinant(MatrixReadable2x2DType)
   */

  public static <M extends MatrixWritable2x2DType> boolean invert(
    final MatrixReadable2x2DType m,
    final M out)
  {
    final double d = MatrixM2x2D.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final double r0c0 = m.getR1C1D() * d_inv;
    final double r0c1 = -m.getR0C1D() * d_inv;
    final double r1c0 = -m.getR1C0D() * d_inv;
    final double r1c1 = m.getR0C0D() * d_inv;

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);

    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2D#determinant(MatrixReadable2x2DType)
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType>
  boolean invertInPlace(
    final M m)
  {
    return MatrixM2x2D.invert(m, m);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M multiply(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1,
    final M out)
  {
    final double orig_r0c0_l = m0.getR0C0D();
    final double orig_r0c0_r = m1.getR0C0D();
    final double orig_r1c0_l = m0.getR1C0D();
    final double orig_r1c0_r = m1.getR1C0D();
    final double orig_r0c1_r = m1.getR0C1D();
    final double orig_r0c1_l = m0.getR0C1D();
    final double orig_r1c1_l = m0.getR1C1D();
    final double orig_r1c1_r = m1.getR1C1D();

    final double r0c0 =
      (orig_r0c0_l * orig_r0c0_r) + (orig_r1c0_l * orig_r0c1_r);
    final double r0c1 =
      (orig_r0c1_l * orig_r0c0_r) + (orig_r1c1_l * orig_r0c1_r);
    final double r1c0 =
      (orig_r0c0_l * orig_r1c0_r) + (orig_r1c0_l * orig_r1c1_r);
    final double r1c1 =
      (orig_r0c1_l * orig_r1c0_r) + (orig_r1c1_l * orig_r1c1_r);

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable2x2DType m1)
  {
    return MatrixM2x2D.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}.
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <V extends VectorWritable2DType> V multiplyVector2D(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final VectorReadable2DType v,
    final V out)
  {
    final VectorM2D row = c.v2a;

    m.getRow2DUnsafe(0, row);
    out.setXD(VectorM2D.dotProduct(row, v));
    m.getRow2DUnsafe(1, row);
    out.setYD(VectorM2D.dotProduct(row, v));
    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row > 1)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 2");
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output row
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType> M scale(
    final MatrixReadable2x2DType m,
    final double r,
    final M out)
  {
    out.setR0C0D(m.getR0C0D() * r);
    out.setR1C0D(m.getR1C0D() * r);
    out.setR0C1D(m.getR0C1D() * r);
    out.setR1C1D(m.getR1C1D() * r);
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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  scaleInPlace(
    final M m,
    final double r)
  {
    return MatrixM2x2D.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M scaleRow(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row,
    final double r,
    final M out)
  {
    return MatrixM2x2D.scaleRowUnsafe(
      m, MatrixM2x2D.rowCheck(row), r, c.v2a, out);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  scaleRowInPlace(
    final ContextMM2D c,
    final M m,
    final int row,
    final double r)
  {
    return MatrixM2x2D.scaleRowUnsafe(
      m, MatrixM2x2D.rowCheck(row), r, c.v2a, m);
  }

  private static <M extends MatrixWritable2x2DType> M scaleRowUnsafe(
    final MatrixReadable2x2DType m,
    final int row,
    final double r,
    final VectorM2D tmp,
    final M out)
  {
    m.getRow2DUnsafe(row, tmp);
    VectorM2D.scaleInPlace(tmp, r);
    out.setRowWith2DUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m   The matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType> M setIdentity(
    final M m)
  {
    m.setR0C0D(1.0);
    m.setR0C1D(0.0);
    m.setR1C0D(0.0);
    m.setR1C1D(1.0);
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

  public static <M extends MatrixWritable2x2DType> M setZero(
    final M m)
  {
    m.setR0C0D(0.0);
    m.setR0C1D(0.0);
    m.setR1C0D(0.0);
    m.setR1C1D(0.0);
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
    final MatrixReadable2x2DType m)
  {
    return m.getR0C0D() + m.getR1C1D();
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

  public static <M extends MatrixWritable2x2DType> M transpose(
    final MatrixReadable2x2DType m,
    final M out)
  {
    final double r0c0 = m.getR0C0D();
    final double r1c0 = m.getR1C0D();

    final double r0c1 = m.getR0C1D();
    final double r1c1 = m.getR1C1D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r0c1); // swap 0

    out.setR0C1D(r1c0); // swap 0
    out.setR1C1D(r1c1);

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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  transposeInPlace(
    final M m)
  {
    final double r1c0 = m.getR1C0D();
    final double r0c1 = m.getR0C1D();

    m.setR1C0D(r0c1); // swap 0
    m.setR0C1D(r1c0); // swap 0
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

    final MatrixM2x2D other = (MatrixM2x2D) obj;
    for (int index = 0; index < MatrixM2x2D.VIEW_ELEMENTS; ++index) {
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

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    MatrixM2x2D.rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(MatrixM2x2D.indexUnsafe(row, 0));
    final double y = this.view.get(MatrixM2x2D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(MatrixM2x2D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    MatrixM2x2D.rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(row, 0), v.getXD());
    this.view.put(MatrixM2x2D.indexUnsafe(row, 1), v.getYD());
  }

  @Override public double getR1C0D()
  {
    return this.view.get(MatrixM2x2D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(MatrixM2x2D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(MatrixM2x2D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(1, 1), x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM2x2D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM2x2D.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixM2x2D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(MatrixM2x2D.indexUnsafe(row, 0));
      final double c1 = this.view.get(MatrixM2x2D.indexUnsafe(row, 1));
      final String s = String.format("[%+.15f %+.15f]\n", c0, c1);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  /**
   * <p>The {@code ContextMM2D} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM2x2D} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM2D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM2D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM2D
  {
    private final VectorM2D   v2a = new VectorM2D();
    private final VectorM2D   v2b = new VectorM2D();

    /**
     * Construct a new context.
     */

    public ContextMM2D()
    {

    }
  }
}
