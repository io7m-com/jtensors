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

import com.io7m.jaux.functional.Option;

/**
 * A 2x2 mutable matrix type with double precision elements.
 */

public final class MatrixM2x2D
{
  private static final double[] identity_row_0 = { 1.0, 0.0 };

  private static final double[] identity_row_1 = { 0.0, 1.0 };

  private static final double[] zero_row       = { 0.0, 0.0 };

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

  public static MatrixM2x2D add(
    final MatrixM2x2D m0,
    final MatrixM2x2D m1)
  {
    return MatrixM2x2D.add(m0, m1, m0);
  }

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
    final MatrixM2x2D m0,
    final MatrixM2x2D m1,
    final MatrixM2x2D out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m0.view.get(index) + m1.view.get(index));
    }
    return out;
  }

  public static MatrixM2x2D addRowScaled(
    final MatrixM2x2D m,
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
    final MatrixM2x2D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM2x2D out)
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
    final MatrixM2x2D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM2x2D out)
  {
    final VectorM2D va = new VectorM2D();
    final VectorM2D vb = new VectorM2D();
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
    final MatrixM2x2D input,
    final MatrixM2x2D output)
  {
    for (int index = 0; index < 4; ++index) {
      output.view.put(index, input.view.get(index));
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static double determinant(
    final MatrixM2x2D m)
  {
    final double r0c0 = MatrixM2x2D.get(m, 0, 0);
    final double r0c1 = MatrixM2x2D.get(m, 0, 1);
    final double r1c0 = MatrixM2x2D.get(m, 1, 0);
    final double r1c1 = MatrixM2x2D.get(m, 1, 1);

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static DoubleBuffer doubleBuffer(
    final MatrixM2x2D m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asDoubleBuffer();
  }

  public static MatrixM2x2D exchangeRows(
    final MatrixM2x2D m,
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
    final MatrixM2x2D m,
    final int row_a,
    final int row_b,
    final MatrixM2x2D out)
  {
    return MatrixM2x2D.exchangeRowsUnsafe(
      m,
      MatrixM2x2D.rowCheck(row_a),
      MatrixM2x2D.rowCheck(row_b),
      out);
  }

  private static MatrixM2x2D exchangeRowsUnsafe(
    final MatrixM2x2D m,
    final int row_a,
    final int row_b,
    final MatrixM2x2D out)
  {
    final VectorM2D va = new VectorM2D();
    final VectorM2D vb = new VectorM2D();

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
    final MatrixM2x2D m,
    final int row,
    final int column)
  {
    return m.view.get(MatrixM2x2D.indexChecked(row, column));
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
    final MatrixM2x2D m)
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
   * @see com.io7m.jtensors.MatrixM2x2D#determinant(MatrixM2x2D)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM2x2D> invert(
    final MatrixM2x2D m,
    final MatrixM2x2D out)
  {
    final double d = MatrixM2x2D.determinant(m);

    if (d == 0) {
      return new Option.None<MatrixM2x2D>();
    }

    final double d_inv = 1 / d;

    final double r0c0 = m.getUnsafe(1, 1) * d_inv;
    final double r0c1 = -m.getUnsafe(0, 1) * d_inv;
    final double r1c0 = -m.getUnsafe(1, 0) * d_inv;
    final double r1c1 = m.getUnsafe(0, 0) * d_inv;

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
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @return <code>out</code>
   */

  public static MatrixM2x2D multiply(
    final MatrixM2x2D m0,
    final MatrixM2x2D m1)
  {
    return MatrixM2x2D.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>out</code>.
   * 
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>out</code>
   */

  public static MatrixM2x2D multiply(
    final MatrixM2x2D m0,
    final MatrixM2x2D m1,
    final MatrixM2x2D out)
  {
    final double r0c0 =
      (m0.getUnsafe(0, 0) * m1.getUnsafe(0, 0))
        + (m0.getUnsafe(1, 0) * m1.getUnsafe(0, 1));
    final double r0c1 =
      (m0.getUnsafe(0, 1) * m1.getUnsafe(0, 0))
        + (m0.getUnsafe(1, 1) * m1.getUnsafe(0, 1));
    final double r1c0 =
      (m0.getUnsafe(0, 0) * m1.getUnsafe(1, 0))
        + (m0.getUnsafe(1, 0) * m1.getUnsafe(1, 1));
    final double r1c1 =
      (m0.getUnsafe(0, 1) * m1.getUnsafe(1, 0))
        + (m0.getUnsafe(1, 1) * m1.getUnsafe(1, 1));

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

  public static VectorM2D multiply(
    final MatrixM2x2D m,
    final VectorReadable2D v,
    final VectorM2D out)
  {
    final VectorM2D row = new VectorM2D();
    final VectorM2D vi = new VectorM2D(v);

    MatrixM2x2D.rowUnsafe(m, 0, row);
    out.x = VectorM2D.dotProduct(row, vi);
    MatrixM2x2D.rowUnsafe(m, 1, row);
    out.y = VectorM2D.dotProduct(row, vi);

    return out;
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM2D row(
    final MatrixM2x2D m,
    final int row,
    final VectorM2D out)
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
    final MatrixM2x2D m,
    final int row,
    final VectorM2D out)
  {
    out.x = m.getUnsafe(row, 0);
    out.y = m.getUnsafe(row, 1);
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
    final MatrixM2x2D m,
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
    final MatrixM2x2D m,
    final double r,
    final MatrixM2x2D out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m.view.get(index) * r);
    }
    return out;
  }

  public static MatrixM2x2D scaleRow(
    final MatrixM2x2D m,
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
    final MatrixM2x2D m,
    final int row,
    final double r,
    final MatrixM2x2D out)
  {
    return MatrixM2x2D.scaleRowUnsafe(m, MatrixM2x2D.rowCheck(row), r, out);
  }

  private static MatrixM2x2D scaleRowUnsafe(
    final MatrixM2x2D m,
    final int row,
    final double r,
    final MatrixM2x2D out)
  {
    final VectorM2D v = new VectorM2D();

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
    final MatrixM2x2D m,
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
    final MatrixM2x2D m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2D.identity_row_0);
    m.view.put(MatrixM2x2D.identity_row_1);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM2x2D m,
    final int row,
    final VectorReadable2D v)
  {
    m.setUnsafe(row, 0, v.getXd());
    m.setUnsafe(row, 1, v.getYd());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM2x2D setZero(
    final MatrixM2x2D m)
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
    final MatrixM2x2D m)
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
    final MatrixM2x2D m,
    final MatrixM2x2D out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m.view.get(index));
    }
    return MatrixM2x2D.transpose(out);
  }

  private final ByteBuffer   data;

  private final DoubleBuffer view;

  public MatrixM2x2D()
  {
    this.data =
      ByteBuffer.allocateDirect(2 * 2 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM2x2D.setIdentity(this);
  }

  public MatrixM2x2D(
    final MatrixM2x2D source)
  {
    this.data =
      ByteBuffer.allocateDirect(2 * 2 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    for (int index = 0; index < 4; ++index) {
      this.view.put(index, source.view.get(index));
    }
  }

  public double get(
    final int row,
    final int column)
  {
    return MatrixM2x2D.get(this, row, column);
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM2x2D.indexUnsafe(row, column));
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
        if (column < 2) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
