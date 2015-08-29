/*
 * Copyright © 2012 http://io7m.com
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
import java.nio.DoubleBuffer;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.functional.Option;

/**
 * A 2x2 mutable matrix type with double precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class MatrixM2x2D implements MatrixReadable2x2D
{
  private static final double[] identity_row_0 = { 1.0, 0.0 };
  private static final double[] identity_row_1 = { 0.0, 1.0 };
  private static final double[] zero_row       = { 0.0, 0.0 };

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

  public static MatrixM2x2D add(
    final @Nonnull MatrixReadable2x2D m0,
    final @Nonnull MatrixReadable2x2D m1,
    final @Nonnull MatrixM2x2D out)
  {
    final DoubleBuffer m0_view = m0.getDoubleBuffer();
    final DoubleBuffer m1_view = m1.getDoubleBuffer();

    out.view.put(0, m0_view.get(0) + m1_view.get(0));
    out.view.put(1, m0_view.get(1) + m1_view.get(1));
    out.view.put(2, m0_view.get(2) + m1_view.get(2));
    out.view.put(3, m0_view.get(3) + m1_view.get(3));

    return out;
  }

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   */

  public static MatrixM2x2D addInPlace(
    final @Nonnull MatrixM2x2D m0,
    final @Nonnull MatrixReadable2x2D m1)
  {
    return MatrixM2x2D.add(m0, m1, m0);
  }

  public static MatrixM2x2D addRowScaled(
    final @Nonnull MatrixM2x2D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM2x2D.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  /**
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM2x2D addRowScaled(
    final @Nonnull MatrixReadable2x2D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM2x2D out)
  {
    return MatrixM2x2D.addRowScaledUnsafe(
      m,
      MatrixM2x2D.rowCheck(row_a),
      MatrixM2x2D.rowCheck(row_b),
      MatrixM2x2D.rowCheck(row_c),
      r,
      out);
  }

  private static MatrixM2x2D addRowScaledUnsafe(
    final @Nonnull MatrixReadable2x2D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM2x2D out)
  {
    final @Nonnull VectorM2D va = new VectorM2D();
    final @Nonnull VectorM2D vb = new VectorM2D();
    MatrixM2x2D.rowUnsafe(m, row_a, va);
    MatrixM2x2D.rowUnsafe(m, row_b, vb);

    VectorM2D.addScaledInPlace(va, vb, r);
    MatrixM2x2D.setRowUnsafe(out, row_c, va);
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
   * Copy the contents of the matrix <code>input</code> to the matrix
   * <code>output</code>, completely replacing all elements.
   * 
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return <code>output</code>
   */

  public static MatrixM2x2D copy(
    final @Nonnull MatrixReadable2x2D input,
    final @Nonnull MatrixM2x2D output)
  {
    final DoubleBuffer source_view = input.getDoubleBuffer();

    output.view.put(0, source_view.get(0));
    output.view.put(1, source_view.get(1));
    output.view.put(2, source_view.get(2));
    output.view.put(3, source_view.get(3));

    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static double determinant(
    final @Nonnull MatrixReadable2x2D m)
  {
    final double r0c0 = m.getRowColumnD(0, 0);
    final double r0c1 = m.getRowColumnD(0, 1);
    final double r1c0 = m.getRowColumnD(1, 0);
    final double r1c1 = m.getRowColumnD(1, 1);

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * Return a view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static DoubleBuffer doubleBuffer(
    final @Nonnull MatrixM2x2D m)
  {
    return m.view;
  }

  public static MatrixM2x2D exchangeRows(
    final @Nonnull MatrixM2x2D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2D.exchangeRows(m, row_a, row_b, m);
  }

  /**
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @formatter:off
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * @formatter:on
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

  public static MatrixM2x2D exchangeRows(
    final @Nonnull MatrixReadable2x2D m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM2x2D out)
  {
    return MatrixM2x2D.exchangeRowsUnsafe(
      m,
      MatrixM2x2D.rowCheck(row_a),
      MatrixM2x2D.rowCheck(row_b),
      out);
  }

  private static MatrixM2x2D exchangeRowsUnsafe(
    final @Nonnull MatrixReadable2x2D m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM2x2D out)
  {
    final @Nonnull VectorM2D va = new VectorM2D();
    final @Nonnull VectorM2D vb = new VectorM2D();

    MatrixM2x2D.rowUnsafe(m, row_a, va);
    MatrixM2x2D.rowUnsafe(m, row_b, vb);

    MatrixM2x2D.setRowUnsafe(out, row_a, vb);
    MatrixM2x2D.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static double get(
    final @Nonnull MatrixReadable2x2D m,
    final int row,
    final int column)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    return source_view.get(MatrixM2x2D.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2D.indexUnsafe(
      MatrixM2x2D.rowCheck(row),
      MatrixM2x2D.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 2) + column, corresponds to row-major storage. (column * 2) + row,
   * corresponds to column-major (OpenGL) storage.
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  public static Option<MatrixM2x2D> invert(
    final @Nonnull MatrixM2x2D m)
  {
    return MatrixM2x2D.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see MatrixM2x2D#determinant(MatrixReadable2x2D)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM2x2D> invert(
    final @Nonnull MatrixReadable2x2D m,
    final @Nonnull MatrixM2x2D out)
  {
    final double d = MatrixM2x2D.determinant(m);

    if (d == 0) {
      return new Option.None<MatrixM2x2D>();
    }

    final double d_inv = 1 / d;

    final double r0c0 = m.getRowColumnD(1, 1) * d_inv;
    final double r0c1 = -m.getRowColumnD(0, 1) * d_inv;
    final double r1c0 = -m.getRowColumnD(1, 0) * d_inv;
    final double r1c1 = m.getRowColumnD(0, 0) * d_inv;

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);

    return new Option.Some<MatrixM2x2D>(out);
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>m0</code>.
   * 
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>out</code>
   */

  public static MatrixM2x2D multiply(
    final @Nonnull MatrixM2x2D m0,
    final @Nonnull MatrixReadable2x2D m1)
  {
    return MatrixM2x2D.multiply(m0, m1, m0);
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

  public static MatrixM2x2D multiply(
    final @Nonnull MatrixReadable2x2D m0,
    final @Nonnull MatrixReadable2x2D m1,
    final @Nonnull MatrixM2x2D out)
  {
    final double r0c0 =
      (m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 0))
        + (m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 1));
    final double r0c1 =
      (m0.getRowColumnD(0, 1) * m1.getRowColumnD(0, 0))
        + (m0.getRowColumnD(1, 1) * m1.getRowColumnD(0, 1));
    final double r1c0 =
      (m0.getRowColumnD(0, 0) * m1.getRowColumnD(1, 0))
        + (m0.getRowColumnD(1, 0) * m1.getRowColumnD(1, 1));
    final double r1c1 =
      (m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 0))
        + (m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 1));

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);

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

  public static VectorM2D multiplyVector2D(
    final @Nonnull MatrixReadable2x2D m,
    final @Nonnull VectorReadable2D v,
    final @Nonnull VectorM2D out)
  {
    final @Nonnull VectorM2D row = new VectorM2D();
    final @Nonnull VectorM2D vi = new VectorM2D(v);

    m.getRowD(0, row);
    out.x = VectorM2D.dotProduct(row, vi);
    m.getRowD(1, row);
    out.y = VectorM2D.dotProduct(row, vi);

    return out;
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM2D row(
    final @Nonnull MatrixReadable2x2D m,
    final int row,
    final @Nonnull VectorM2D out)
  {
    return MatrixM2x2D.rowUnsafe(m, MatrixM2x2D.rowCheck(row), out);
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

  public static VectorM2D rowUnsafe(
    final @Nonnull MatrixReadable2x2D m,
    final int row,
    final @Nonnull VectorM2D out)
  {
    out.x = m.getRowColumnD(row, 0);
    out.y = m.getRowColumnD(row, 1);
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

  public static MatrixM2x2D scale(
    final @Nonnull MatrixM2x2D m,
    final double r)
  {
    return MatrixM2x2D.scale(m, r, m);
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

  public static MatrixM2x2D scale(
    final @Nonnull MatrixReadable2x2D m,
    final double r,
    final @Nonnull MatrixM2x2D out)
  {
    out.setUnsafe(0, 0, m.getRowColumnD(0, 0) * r);
    out.setUnsafe(1, 0, m.getRowColumnD(1, 0) * r);
    out.setUnsafe(0, 1, m.getRowColumnD(0, 1) * r);
    out.setUnsafe(1, 1, m.getRowColumnD(1, 1) * r);
    return out;
  }

  public static MatrixM2x2D scaleRow(
    final @Nonnull MatrixM2x2D m,
    final int row,
    final double r)
  {
    return MatrixM2x2D.scaleRowUnsafe(m, MatrixM2x2D.rowCheck(row), r, m);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM2x2D scaleRow(
    final @Nonnull MatrixReadable2x2D m,
    final int row,
    final double r,
    final @Nonnull MatrixM2x2D out)
  {
    return MatrixM2x2D.scaleRowUnsafe(m, MatrixM2x2D.rowCheck(row), r, out);
  }

  private static MatrixM2x2D scaleRowUnsafe(
    final @Nonnull MatrixReadable2x2D m,
    final int row,
    final double r,
    final @Nonnull MatrixM2x2D out)
  {
    final @Nonnull VectorM2D v = new VectorM2D();

    MatrixM2x2D.rowUnsafe(m, row, v);
    VectorM2D.scaleInPlace(v, r);

    MatrixM2x2D.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM2x2D set(
    final @Nonnull MatrixM2x2D m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM2x2D.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM2x2D setIdentity(
    final @Nonnull MatrixM2x2D m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2D.identity_row_0);
    m.view.put(MatrixM2x2D.identity_row_1);
    return m;
  }

  private static void setRowUnsafe(
    final @Nonnull MatrixM2x2D m,
    final int row,
    final @Nonnull VectorReadable2D v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM2x2D setZero(
    final @Nonnull MatrixM2x2D m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2D.zero_row);
    m.view.put(MatrixM2x2D.zero_row);
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

  public static MatrixM2x2D transpose(
    final @Nonnull MatrixM2x2D m)
  {
    for (int row = 0; row < (2 - 1); row++) {
      for (int column = row + 1; column < 2; column++) {
        final double x = m.view.get((row * 2) + column);
        m.view.put((row * 2) + column, m.view.get(row + (2 * column)));
        m.view.put(row + (2 * column), x);
      }
    }
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

  public static MatrixM2x2D transpose(
    final @Nonnull MatrixReadable2x2D m,
    final @Nonnull MatrixM2x2D out)
  {
    MatrixM2x2D.copy(m, out);
    return MatrixM2x2D.transpose(out);
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;
  private static final int   VIEW_ELEMENT_SIZE;
  private static final int   VIEW_ELEMENTS;
  private static final int   VIEW_BYTES;
  private static final int   VIEW_COLS;
  private static final int   VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM2x2D.VIEW_ROWS * MatrixM2x2D.VIEW_COLS;
    VIEW_BYTES = MatrixM2x2D.VIEW_ELEMENTS * MatrixM2x2D.VIEW_ELEMENT_SIZE;
  }

  public MatrixM2x2D()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM2x2D.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM2x2D.setIdentity(this);
  }

  public MatrixM2x2D(
    final @Nonnull MatrixReadable2x2D source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM2x2D.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    final DoubleBuffer source_view = source.getDoubleBuffer();
    for (int index = 0; index < MatrixM2x2D.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source_view.get(index));
    }
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
    final @Nonnull MatrixM2x2D other = (MatrixM2x2D) obj;

    for (int index = 0; index < MatrixM2x2D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  public double get(
    final int row,
    final int column)
  {
    return MatrixM2x2D.get(this, row, column);
  }

  @Override public @Nonnull DoubleBuffer getDoubleBuffer()
  {
    return this.view;
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return MatrixM2x2D.get(this, row, column);
  }

  @Override public void getRowD(
    final int row,
    final @Nonnull VectorM2D out)
  {
    MatrixM2x2D.rowUnsafe(this, MatrixM2x2D.rowCheck(row), out);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM2x2D.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  public MatrixM2x2D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM2x2D.indexChecked(row, column), value);
    return this;
  }

  private MatrixM2x2D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM2x2D.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 2; ++row) {
      builder.append("[");
      for (int column = 0; column < 2; ++column) {
        builder.append(MatrixM2x2D.get(this, row, column));
        if (column < 1) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
