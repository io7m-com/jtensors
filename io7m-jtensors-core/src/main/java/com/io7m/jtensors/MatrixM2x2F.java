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

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//@formatter:off

/**
 * <p>
 * A 2x2 mutable matrix type with single precision elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM2x2F} are backed by direct memory, with the
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

public final class MatrixM2x2F implements MatrixDirect2x2FType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixM2x2F.VIEW_ROWS * MatrixM2x2F.VIEW_COLS;
    VIEW_BYTES = MatrixM2x2F.VIEW_ELEMENTS * MatrixM2x2F.VIEW_ELEMENT_SIZE;
  }

  @SuppressWarnings("unused") private final ByteBuffer  data;
  private final                             FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM2x2F()
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM2x2F.VIEW_BYTES);
    assert b != null;

    b.order(order);

    this.data = b;

    final FloatBuffer v = b.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM2x2F.setIdentity(this);
  }

  /**
   * Construct a new matrix from the given source matrix.
   *
   * @param source The source matrix
   */

  public MatrixM2x2F(
    final MatrixReadable2x2FType source)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM2x2F.VIEW_BYTES);
    assert b != null;

    b.order(order);

    this.data = b;

    final FloatBuffer v = b.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM2x2F.copy(source, this);
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M add(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final M out)
  {
    final float r0c0 = m0.getR0C0F() + m1.getR0C0F();
    final float r1c0 = m0.getR1C0F() + m1.getR1C0F();

    final float r0c1 = m0.getR0C1F() + m1.getR0C1F();
    final float r1c1 = m0.getR1C1F() + m1.getR1C1F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   *
   * @return {@code m0}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  addInPlace(
    final M m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M addRowScaled(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final M out)
  {
    return MatrixM2x2F.addRowScaledUnsafe(
      m,
      MatrixM2x2F.rowCheck(row_a),
      MatrixM2x2F.rowCheck(row_b),
      MatrixM2x2F.rowCheck(row_c), (double) r,
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
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  addRowScaledInPlace(
    final ContextMM2F c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM2x2F.addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable2x2FType> M addRowScaledUnsafe(
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM2F va,
    final VectorM2F vb,
    final M out)
  {
    m.getRow2FUnsafe(row_a, va);
    m.getRow2FUnsafe(row_b, vb);
    VectorM2F.addScaledInPlace(va, vb, r);
    out.setRowWith2FUnsafe(row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM2x2F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM2x2F.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param <M>    The precise type of matrix
   * @param input  The input vector
   * @param output The output vector
   *
   * @return {@code output}
   */

  public static <M extends MatrixWritable2x2FType> M copy(
    final MatrixReadable2x2FType input,
    final M output)
  {
    output.setR0C0F(input.getR0C0F());
    output.setR1C0F(input.getR1C0F());
    output.setR0C1F(input.getR0C1F());
    output.setR1C1F(input.getR1C1F());
    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m The input matrix
   *
   * @return The determinant
   */

  public static float determinant(
    final MatrixReadable2x2FType m)
  {
    final float r0c0 = m.getR0C0F();
    final float r0c1 = m.getR0C1F();
    final float r1c0 = m.getR1C0F();
    final float r1c1 = m.getR1C1F();

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M exchangeRows(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return MatrixM2x2F.exchangeRowsUnsafe(
      m,
      MatrixM2x2F.rowCheck(row_a),
      MatrixM2x2F.rowCheck(row_b),
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M>   The precise type of matrix
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  exchangeRowsInPlace(
    final ContextMM2F c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2F.exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable2x2FType> M exchangeRowsUnsafe(
    final MatrixReadable2x2FType m,
    final int row_a,
    final int row_b,
    final VectorM2F va,
    final VectorM2F vb,
    final M out)
  {
    m.getRow2FUnsafe(row_a, va);
    m.getRow2FUnsafe(row_b, vb);
    out.setRowWith2FUnsafe(row_a, vb);
    out.setRowWith2FUnsafe(row_b, va);
    return out;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2F.indexUnsafe(
      MatrixM2x2F.rowCheck(row), MatrixM2x2F.columnCheck(column));
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
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   */

  public static <M extends MatrixWritable2x2FType> OptionType<M> invert(
    final MatrixReadable2x2FType m,
    final M out)
  {
    final float d = MatrixM2x2F.determinant(m);

    if (d == 0.0F) {
      return Option.none();
    }

    final float d_inv = 1.0F / d;

    final float r0c0 = m.getR1C1F() * d_inv;
    final float r0c1 = -m.getR0C1F() * d_inv;
    final float r1c0 = -m.getR1C0F() * d_inv;
    final float r1c1 = m.getR0C0F() * d_inv;

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);

    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code m}
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType>
  OptionType<M> invertInPlace(
    final M m)
  {
    return MatrixM2x2F.invert(m, m);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M multiply(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final M out)
  {
    final float r0c0_l = m0.getR0C0F();
    final float r1c0_l = m0.getR1C0F();
    final float r0c1_l = m0.getR0C1F();
    final float r1c1_l = m0.getR1C1F();

    final float r0c0_r = m1.getR0C0F();
    final float r0c1_r = m1.getR0C1F();
    final float r1c0_r = m1.getR1C0F();
    final float r1c1_r = m1.getR1C1F();

    final float r0c0 = (r0c0_l * r0c0_r) + (r1c0_l * r0c1_r);
    final float r0c1 = (r0c1_l * r0c0_r) + (r1c1_l * r0c1_r);
    final float r1c0 = (r0c0_l * r1c0_r) + (r1c0_l * r1c1_r);
    final float r1c1 = (r0c1_l * r1c0_r) + (r1c1_l * r1c1_r);

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);

    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input vector
   * @param m1  The right input vector
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.multiply(m0, m1, m0);
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

  public static <V extends VectorWritable2FType> V multiplyVector2F(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final VectorReadable2FType v,
    final V out)
  {
    final VectorM2F row = c.v2a;

    m.getRow2FUnsafe(0, row);
    out.setXF(VectorM2F.dotProduct(row, v));
    m.getRow2FUnsafe(1, row);
    out.setYF(VectorM2F.dotProduct(row, v));
    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM2x2F.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM2x2F.VIEW_ROWS);
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M scale(
    final MatrixReadable2x2FType m,
    final float r,
    final M out)
  {
    out.setR0C0F(m.getR0C0F() * r);
    out.setR1C0F(m.getR1C0F() * r);
    out.setR0C1F(m.getR0C1F() * r);
    out.setR1C1F(m.getR1C1F() * r);
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  scaleInPlace(
    final M m,
    final float r)
  {
    return MatrixM2x2F.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M> The precise type of matrix
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M scaleRow(
    final ContextMM2F c,
    final MatrixReadable2x2FType m,
    final int row,
    final float r,
    final M out)
  {
    return MatrixM2x2F.scaleRowUnsafe(
      m, MatrixM2x2F.rowCheck(row), (double) r, c.v2a, out);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param <M> The precise type of matrix
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  scaleRowInPlace(
    final ContextMM2F c,
    final M m,
    final int row,
    final float r)
  {
    return MatrixM2x2F.scaleRowUnsafe(
      m, MatrixM2x2F.rowCheck(row), (double) r, c.v2a, m);
  }

  private static <M extends MatrixWritable2x2FType> M scaleRowUnsafe(
    final MatrixReadable2x2FType m,
    final int row,
    final double r,
    final VectorM2F tmp,
    final M out)
  {
    m.getRow2FUnsafe(row, tmp);
    VectorM2F.scaleInPlace(tmp, r);
    out.setRowWith2FUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType> M setIdentity(
    final M m)
  {
    m.setR0C0F(1.0f);
    m.setR0C1F(0.0f);
    m.setR1C0F(0.0f);
    m.setR1C1F(1.0f);
    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType> M setZero(
    final M m)
  {
    m.setR0C0F(0.0f);
    m.setR0C1F(0.0f);
    m.setR1C0F(0.0f);
    m.setR1C1F(0.0f);
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

  public static float trace(
    final MatrixReadable2x2FType m)
  {
    return m.getR0C0F() + m.getR1C1F();
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2FType> M transpose(
    final MatrixReadable2x2FType m,
    final M out)
  {
    final float r0c0 = m.getR0C0F();
    final float r1c0 = m.getR1C0F();

    final float r0c1 = m.getR0C1F();
    final float r1c1 = m.getR1C1F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r0c1); // swap 0

    out.setR0C1F(r1c0); // swap 0
    out.setR1C1F(r1c1);

    return out;
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2FType & MatrixReadable2x2FType> M
  transposeInPlace(
    final M m)
  {
    final float r1c0 = m.getR1C0F();
    final float r0c1 = m.getR0C1F();

    m.setR1C0F(r0c1); // swap 0
    m.setR0C1F(r1c0); // swap 0
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
    final MatrixM2x2F other = (MatrixM2x2F) obj;

    for (int index = 0; index < MatrixM2x2F.VIEW_ELEMENTS; ++index) {
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

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    MatrixM2x2F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixM2x2F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixM2x2F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(MatrixM2x2F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(0, 0), x);
  }

  @Override public float getR1C0F()
  {
    return this.view.get(MatrixM2x2F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(MatrixM2x2F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(MatrixM2x2F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(1, 1), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM2x2F.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C1F(), prime, r);

    return r;
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    MatrixM2x2F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixM2x2F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM2x2F.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixM2x2F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(MatrixM2x2F.indexUnsafe(row, 0));
      final float c1 = this.view.get(MatrixM2x2F.indexUnsafe(row, 1));
      final String s = String.format("[%+.6f %+.6f]\n", c0, c1);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  /**
   * <p>The {@code ContextMM2F} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM2x2F} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM2F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM2F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM2F
  {
    private final VectorM2F   v2a = new VectorM2F();
    private final VectorM2F   v2b = new VectorM2F();

    /**
     * Construct a new context.
     */

    public ContextMM2F()
    {

    }
  }
}
