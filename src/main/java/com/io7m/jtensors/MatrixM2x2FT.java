/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.functional.Option;

/**
 * <p>
 * A 2x2 mutable matrix type with single precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM2x2F</code> are backed by direct memory, with
 * the rows and columns of the matrices being stored in column-major format.
 * This allows the matrices to be passed to OpenGL directly, without requiring
 * transposition.
 * </p>
 * <p>
 * The intention of the type parameter <code>A</code> is to be used as a
 * "phantom type" or compile-time tag to statically distinguish between
 * semantically distinct matrices.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 * <p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed for
 * the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * </p>
 * 
 * @since 5.1.0
 */

@NotThreadSafe public final class MatrixM2x2FT<A> implements
  MatrixReadable2x2FT<A>
{
  private static final float[] identity_row_0 = { 1.0f, 0.0f };
  private static final float[] identity_row_1 = { 0.0f, 1.0f };
  private static final float[] zero_row       = { 0.0f, 0.0f };

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> add(
    final @Nonnull MatrixReadable2x2FT<A> m0,
    final @Nonnull MatrixReadable2x2FT<A> m1,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    final FloatBuffer m0_view = m0.getFloatBuffer();
    final FloatBuffer m1_view = m1.getFloatBuffer();

    out.view.put(0, m0_view.get(0) + m1_view.get(0));
    out.view.put(1, m0_view.get(1) + m1_view.get(1));
    out.view.put(2, m0_view.get(2) + m1_view.get(2));
    out.view.put(3, m0_view.get(3) + m1_view.get(3));
    out.view.rewind();

    return out;
  }

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   * 
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> addInPlace(
    final @Nonnull MatrixM2x2FT<A> m0,
    final @Nonnull MatrixReadable2x2FT<A> m1)
  {
    return MatrixM2x2FT.add(m0, m1, m0);
  }

  public static @Nonnull <A> MatrixM2x2FT<A> addRowScaled(
    final @Nonnull MatrixM2x2FT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM2x2FT.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
   * </p>
   * 
   * @param m
   *          The input matrix.
   * @param row_a
   *          The row on the lefthand side of the addition.
   * @param row_b
   *          The row on the righthand side of the addition.
   * @param row_c
   *          The destination row.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> addRowScaled(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    return MatrixM2x2FT.addRowScaledUnsafe(
      m,
      MatrixM2x2FT.rowCheck(row_a),
      MatrixM2x2FT.rowCheck(row_b),
      MatrixM2x2FT.rowCheck(row_c),
      r,
      out);
  }

  private static @Nonnull <A> MatrixM2x2FT<A> addRowScaledUnsafe(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    final @Nonnull VectorM2F va = new VectorM2F();
    final @Nonnull VectorM2F vb = new VectorM2F();
    MatrixM2x2FT.rowUnsafe(m, row_a, va);
    MatrixM2x2FT.rowUnsafe(m, row_b, vb);

    VectorM2F.addScaledInPlace(va, vb, r);
    MatrixM2x2FT.setRowUnsafe(out, row_c, va);

    out.view.rewind();
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM2x2FT.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM2x2FT.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix <code>input</code> to the matrix
   * <code>output</code>, completely replacing all elements.
   * 
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return <code>output</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> copy(
    final @Nonnull MatrixReadable2x2FT<A> input,
    final @Nonnull MatrixM2x2FT<A> output)
  {
    final FloatBuffer source_view = input.getFloatBuffer();

    output.view.put(0, source_view.get(0));
    output.view.put(1, source_view.get(1));
    output.view.put(2, source_view.get(2));
    output.view.put(3, source_view.get(3));
    output.view.rewind();

    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static <A> float determinant(
    final @Nonnull MatrixReadable2x2FT<A> m)
  {
    final float r0c0 = m.getRowColumnF(0, 0);
    final float r0c1 = m.getRowColumnF(0, 1);
    final float r1c0 = m.getRowColumnF(1, 0);
    final float r1c1 = m.getRowColumnF(1, 1);

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  public static @Nonnull <A> MatrixM2x2FT<A> exchangeRows(
    final @Nonnull MatrixM2x2FT<A> m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2FT.exchangeRows(m, row_a, row_b, m);
  }

  /**
   * <p>
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
   * </p>
   * 
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> exchangeRows(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    return MatrixM2x2FT.exchangeRowsUnsafe(
      m,
      MatrixM2x2FT.rowCheck(row_a),
      MatrixM2x2FT.rowCheck(row_b),
      out);
  }

  private static @Nonnull <A> MatrixM2x2FT<A> exchangeRowsUnsafe(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    final @Nonnull VectorM2F va = new VectorM2F();
    final @Nonnull VectorM2F vb = new VectorM2F();

    MatrixM2x2FT.rowUnsafe(m, row_a, va);
    MatrixM2x2FT.rowUnsafe(m, row_b, vb);

    MatrixM2x2FT.setRowUnsafe(out, row_a, vb);
    MatrixM2x2FT.setRowUnsafe(out, row_b, va);

    out.view.rewind();
    return out;
  }

  /**
   * Return a view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static @Nonnull <A> FloatBuffer floatBuffer(
    final @Nonnull MatrixM2x2FT<A> m)
  {
    return m.view;
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static <A> float get(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row,
    final int column)
  {
    final FloatBuffer source_view = m.getFloatBuffer();
    return source_view.get(MatrixM2x2FT.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2FT.indexUnsafe(
      MatrixM2x2FT.rowCheck(row),
      MatrixM2x2FT.columnCheck(column));
  }

  /**
   * <p>
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * </p>
   * <p>
   * (row * 2) + column, corresponds to row-major storage. (column * 2) + row,
   * corresponds to column-major (OpenGL) storage.
   * </p>
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  public static @Nonnull <A> Option<MatrixM2x2FT<A>> invert(
    final @Nonnull MatrixM2x2FT<A> m)
  {
    return MatrixM2x2FT.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see MatrixM2x2FT#determinant(MatrixReadable2x2FT)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static @Nonnull <A> Option<MatrixM2x2FT<A>> invert(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    final float d = MatrixM2x2FT.determinant(m);

    if (d == 0) {
      return new Option.None<MatrixM2x2FT<A>>();
    }

    final float d_inv = 1 / d;

    final float r0c0 = m.getRowColumnF(1, 1) * d_inv;
    final float r0c1 = -m.getRowColumnF(0, 1) * d_inv;
    final float r1c0 = -m.getRowColumnF(1, 0) * d_inv;
    final float r1c1 = m.getRowColumnF(0, 0) * d_inv;

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.view.rewind();

    return new Option.Some<MatrixM2x2FT<A>>(out);
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>m0</code>.
   * 
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @return <code>out</code>
   */

  public static @Nonnull <A, B> MatrixM2x2FT<A> multiply(
    final @Nonnull MatrixM2x2FT<A> m0,
    final @Nonnull MatrixReadable2x2FT<B> m1)
  {
    return MatrixM2x2FT.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>out</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A, B, C> MatrixM2x2FT<C> multiply(
    final @Nonnull MatrixReadable2x2FT<A> m0,
    final @Nonnull MatrixReadable2x2FT<B> m1,
    final @Nonnull MatrixM2x2FT<C> out)
  {
    final float r0c0 =
      (m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 0))
        + (m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 1));
    final float r0c1 =
      (m0.getRowColumnF(0, 1) * m1.getRowColumnF(0, 0))
        + (m0.getRowColumnF(1, 1) * m1.getRowColumnF(0, 1));
    final float r1c0 =
      (m0.getRowColumnF(0, 0) * m1.getRowColumnF(1, 0))
        + (m0.getRowColumnF(1, 0) * m1.getRowColumnF(1, 1));
    final float r1c1 =
      (m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 0))
        + (m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 1));

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.view.rewind();

    return out;
  }

  /**
   * Multiply the matrix <code>m</code> with the vector <code>v</code>,
   * writing the resulting vector to <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>out</code>
   */

  public static @Nonnull <A> VectorM2F multiplyVector2F(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final @Nonnull VectorReadable2F v,
    final @Nonnull VectorM2F out)
  {
    final @Nonnull VectorM2F row = new VectorM2F();
    final @Nonnull VectorM2F vi = new VectorM2F(v);

    m.getRow2F(0, row);
    out.x = VectorM2F.dotProduct(row, vi);
    m.getRow2F(1, row);
    out.y = VectorM2F.dotProduct(row, vi);

    return out;
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static @Nonnull <A> VectorM2F row(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row,
    final @Nonnull VectorM2F out)
  {
    return MatrixM2x2FT.rowUnsafe(m, MatrixM2x2FT.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM2x2FT.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM2x2FT.VIEW_ROWS);
    }
    return row;
  }

  public static @Nonnull <A> VectorM2F rowUnsafe(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row,
    final @Nonnull VectorM2F out)
  {
    out.x = m.getRowColumnF(row, 0);
    out.y = m.getRowColumnF(row, 1);
    return out;
  }

  /**
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> scale(
    final @Nonnull MatrixM2x2FT<A> m,
    final float r)
  {
    return MatrixM2x2FT.scale(m, r, m);
  }

  /**
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> scale(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final float r,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    out.setUnsafe(0, 0, m.getRowColumnF(0, 0) * r);
    out.setUnsafe(1, 0, m.getRowColumnF(1, 0) * r);
    out.setUnsafe(0, 1, m.getRowColumnF(0, 1) * r);
    out.setUnsafe(1, 1, m.getRowColumnF(1, 1) * r);
    out.view.rewind();
    return out;
  }

  public static @Nonnull <A> MatrixM2x2FT<A> scaleRow(
    final @Nonnull MatrixM2x2FT<A> m,
    final int row,
    final float r)
  {
    return MatrixM2x2FT.scaleRowUnsafe(m, MatrixM2x2FT.rowCheck(row), r, m);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
   * </p>
   * 
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 <= row < 4).
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> scaleRow(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row,
    final float r,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    return MatrixM2x2FT.scaleRowUnsafe(m, MatrixM2x2FT.rowCheck(row), r, out);
  }

  private static @Nonnull <A> MatrixM2x2FT<A> scaleRowUnsafe(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final int row,
    final float r,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    final @Nonnull VectorM2F v = new VectorM2F();

    MatrixM2x2FT.rowUnsafe(m, row, v);
    VectorM2F.scaleInPlace(v, r);

    MatrixM2x2FT.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static @Nonnull <A> MatrixM2x2FT<A> set(
    final @Nonnull MatrixM2x2FT<A> m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM2x2FT.indexChecked(row, column), value);
    m.view.rewind();
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> setIdentity(
    final @Nonnull MatrixM2x2FT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2FT.identity_row_0);
    m.view.put(MatrixM2x2FT.identity_row_1);
    m.view.rewind();
    return m;
  }

  private static <A> void setRowUnsafe(
    final @Nonnull MatrixM2x2FT<A> m,
    final int row,
    final @Nonnull VectorReadable2F v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.view.rewind();
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> setZero(
    final @Nonnull MatrixM2x2FT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2FT.zero_row);
    m.view.put(MatrixM2x2FT.zero_row);
    m.view.rewind();
    return m;
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> transpose(
    final @Nonnull MatrixM2x2FT<A> m)
  {
    for (int row = 0; row < (2 - 1); row++) {
      for (int column = row + 1; column < 2; column++) {
        final float x = m.view.get((row * 2) + column);
        m.view.put((row * 2) + column, m.view.get(row + (2 * column)));
        m.view.put(row + (2 * column), x);
      }
    }

    m.view.rewind();
    return m;
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM2x2FT<A> transpose(
    final @Nonnull MatrixReadable2x2FT<A> m,
    final @Nonnull MatrixM2x2FT<A> out)
  {
    MatrixM2x2FT.copy(m, out);
    return MatrixM2x2FT.transpose(out);
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;
  private static final int  VIEW_ELEMENT_SIZE;
  private static final int  VIEW_ELEMENTS;
  private static final int  VIEW_BYTES;
  private static final int  VIEW_COLS;
  private static final int  VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixM2x2FT.VIEW_ROWS * MatrixM2x2FT.VIEW_COLS;
    VIEW_BYTES = MatrixM2x2FT.VIEW_ELEMENTS * MatrixM2x2FT.VIEW_ELEMENT_SIZE;
  }

  /**
   * Return the trace of the matrix <code>m</code>. The trace is defined as
   * the sum of the diagonal elements of the matrix.
   * 
   * 
   * @param m
   *          The input matrix
   * @return The trace of the matrix
   */

  public static <A> float trace(
    final @Nonnull MatrixReadable2x2FT<A> m)
  {
    return m.getRowColumnF(0, 0) + m.getRowColumnF(1, 1);
  }

  public MatrixM2x2FT()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM2x2FT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();
    MatrixM2x2FT.setIdentity(this);
  }

  public MatrixM2x2FT(
    final @Nonnull MatrixReadable2x2FT<A> source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM2x2FT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();

    final FloatBuffer source_view = source.getFloatBuffer();
    for (int index = 0; index < MatrixM2x2FT.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source_view.get(index));
    }

    this.view.rewind();
  }

  @Override public boolean equals(
    final Object obj)
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

    @SuppressWarnings("unchecked") final @Nonnull MatrixM2x2FT<A> other =
      (MatrixM2x2FT<A>) obj;
    for (int index = 0; index < MatrixM2x2FT.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  public float get(
    final int row,
    final int column)
  {
    return MatrixM2x2FT.get(this, row, column);
  }

  @Override public @Nonnull FloatBuffer getFloatBuffer()
  {
    return this.view;
  }

  @Override public void getRow2F(
    final int row,
    final @Nonnull VectorM2F out)
  {
    MatrixM2x2FT.rowUnsafe(this, MatrixM2x2FT.rowCheck(row), out);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return MatrixM2x2FT.get(this, row, column);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM2x2FT.VIEW_ELEMENTS; ++index) {
      result += Float.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  public MatrixM2x2FT<A> set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM2x2FT.indexChecked(row, column), value);
    return this;
  }

  private MatrixM2x2FT<A> setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM2x2FT.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM2x2FT.VIEW_ROWS; ++row) {
      builder.append("[");
      for (int column = 0; column < MatrixM2x2FT.VIEW_COLS; ++column) {
        builder.append(MatrixM2x2FT.get(this, row, column));
        if (column < 1) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }

  /**
   * <p>
   * Cast away the extra type information to give an ordinary
   * {@link MatrixM2x2F}. The resulting matrix instance shares the internal
   * data of this matrix instance (and therefore extreme caution should be
   * taken when mutating either instance).
   * </p>
   */

  public @Nonnull MatrixM2x2F asMatrixM2x2F()
  {
    return new MatrixM2x2F(this.data);
  }
}
