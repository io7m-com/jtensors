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
import java.nio.FloatBuffer;

import com.io7m.jaux.functional.Option;

/**
 * A 2x2 mutable matrix type with single precision elements.
 */

public final class MatrixM2x2F
{
  private static final float[] identity_row_0 = { 1.0f, 0.0f };

  private static final float[] identity_row_1 = { 0.0f, 1.0f };

  private static final float[] zero_row       = { 0.0f, 0.0f };

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

  public static MatrixM2x2F add(
    final MatrixM2x2F m0,
    final MatrixM2x2F m1)
  {
    return MatrixM2x2F.add(m0, m1, m0);
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

  public static MatrixM2x2F add(
    final MatrixM2x2F m0,
    final MatrixM2x2F m1,
    final MatrixM2x2F out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m0.view.get(index) + m1.view.get(index));
    }
    return out;
  }

  public static MatrixM2x2F addRowScaled(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM2x2F.addRowScaled(m, row_a, row_b, row_c, r, m);
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

  public static MatrixM2x2F addRowScaled(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final MatrixM2x2F out)
  {
    return MatrixM2x2F.addRowScaledUnsafe(
      m,
      MatrixM2x2F.rowCheck(row_a),
      MatrixM2x2F.rowCheck(row_b),
      MatrixM2x2F.rowCheck(row_c),
      r,
      out);
  }

  private static MatrixM2x2F addRowScaledUnsafe(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final MatrixM2x2F out)
  {
    final VectorM2F va = new VectorM2F();
    final VectorM2F vb = new VectorM2F();
    MatrixM2x2F.rowUnsafe(m, row_a, va);
    MatrixM2x2F.rowUnsafe(m, row_b, vb);

    VectorM2F.addScaledInPlace(va, vb, r);
    MatrixM2x2F.setRowUnsafe(out, row_c, va);
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

  public static MatrixM2x2F copy(
    final MatrixM2x2F input,
    final MatrixM2x2F output)
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

  public static float determinant(
    final MatrixM2x2F m)
  {
    final float r0c0 = MatrixM2x2F.get(m, 0, 0);
    final float r0c1 = MatrixM2x2F.get(m, 0, 1);
    final float r1c0 = MatrixM2x2F.get(m, 1, 0);
    final float r1c1 = MatrixM2x2F.get(m, 1, 1);

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  public static MatrixM2x2F exchangeRows(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2F.exchangeRows(m, row_a, row_b, m);
  }

  /**
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM2x2F exchangeRows(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final MatrixM2x2F out)
  {
    return MatrixM2x2F.exchangeRowsUnsafe(
      m,
      MatrixM2x2F.rowCheck(row_a),
      MatrixM2x2F.rowCheck(row_b),
      out);
  }

  private static MatrixM2x2F exchangeRowsUnsafe(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final MatrixM2x2F out)
  {
    final VectorM2F va = new VectorM2F();
    final VectorM2F vb = new VectorM2F();

    MatrixM2x2F.rowUnsafe(m, row_a, va);
    MatrixM2x2F.rowUnsafe(m, row_b, vb);

    MatrixM2x2F.setRowUnsafe(out, row_a, vb);
    MatrixM2x2F.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static FloatBuffer floatBuffer(
    final MatrixM2x2F m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asFloatBuffer();
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static float get(
    final MatrixM2x2F m,
    final int row,
    final int column)
  {
    return m.view.get(MatrixM2x2F.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2F.indexUnsafe(
      MatrixM2x2F.rowCheck(row),
      MatrixM2x2F.columnCheck(column));
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

  public static Option<MatrixM2x2F> invert(
    final MatrixM2x2F m)
  {
    return MatrixM2x2F.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM2x2F#determinant(MatrixM2x2F)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM2x2F> invert(
    final MatrixM2x2F m,
    final MatrixM2x2F out)
  {
    final float d = MatrixM2x2F.determinant(m);

    if (d == 0) {
      return new Option.None<MatrixM2x2F>();
    }

    final float d_inv = 1 / d;

    final float r0c0 = m.getUnsafe(1, 1) * d_inv;
    final float r0c1 = -m.getUnsafe(0, 1) * d_inv;
    final float r1c0 = -m.getUnsafe(1, 0) * d_inv;
    final float r1c1 = m.getUnsafe(0, 0) * d_inv;

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);

    return new Option.Some<MatrixM2x2F>(out);
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

  public static MatrixM2x2F multiply(
    final MatrixM2x2F m0,
    final MatrixM2x2F m1)
  {
    return MatrixM2x2F.multiply(m0, m1, m0);
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

  public static MatrixM2x2F multiply(
    final MatrixM2x2F m0,
    final MatrixM2x2F m1,
    final MatrixM2x2F out)
  {
    final float r0c0 =
      (m0.getUnsafe(0, 0) * m1.getUnsafe(0, 0))
        + (m0.getUnsafe(1, 0) * m1.getUnsafe(0, 1));
    final float r0c1 =
      (m0.getUnsafe(0, 1) * m1.getUnsafe(0, 0))
        + (m0.getUnsafe(1, 1) * m1.getUnsafe(0, 1));
    final float r1c0 =
      (m0.getUnsafe(0, 0) * m1.getUnsafe(1, 0))
        + (m0.getUnsafe(1, 0) * m1.getUnsafe(1, 1));
    final float r1c1 =
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

  public static VectorM2F multiply(
    final MatrixM2x2F m,
    final VectorReadable2F v,
    final VectorM2F out)
  {
    final VectorM2F row = new VectorM2F();
    final VectorM2F vi = new VectorM2F(v);

    MatrixM2x2F.rowUnsafe(m, 0, row);
    out.x = VectorM2F.dotProduct(row, vi);
    MatrixM2x2F.rowUnsafe(m, 1, row);
    out.y = VectorM2F.dotProduct(row, vi);

    return out;
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM2F row(
    final MatrixM2x2F m,
    final int row,
    final VectorM2F out)
  {
    return MatrixM2x2F.rowUnsafe(m, MatrixM2x2F.rowCheck(row), out);
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

  public static VectorM2F rowUnsafe(
    final MatrixM2x2F m,
    final int row,
    final VectorM2F out)
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

  public static MatrixM2x2F scale(
    final MatrixM2x2F m,
    final float r)
  {
    return MatrixM2x2F.scale(m, r, m);
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

  public static MatrixM2x2F scale(
    final MatrixM2x2F m,
    final float r,
    final MatrixM2x2F out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m.view.get(index) * r);
    }
    return out;
  }

  public static MatrixM2x2F scaleRow(
    final MatrixM2x2F m,
    final int row,
    final float r)
  {
    return MatrixM2x2F.scaleRowUnsafe(m, MatrixM2x2F.rowCheck(row), r, m);
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
   * @return <code>m</code>
   */

  public static MatrixM2x2F scaleRow(
    final MatrixM2x2F m,
    final int row,
    final float r,
    final MatrixM2x2F out)
  {
    return MatrixM2x2F.scaleRowUnsafe(m, MatrixM2x2F.rowCheck(row), r, out);
  }

  private static MatrixM2x2F scaleRowUnsafe(
    final MatrixM2x2F m,
    final int row,
    final float r,
    final MatrixM2x2F out)
  {
    final VectorM2F v = new VectorM2F();

    MatrixM2x2F.rowUnsafe(m, row, v);
    VectorM2F.scaleInPlace(v, r);

    MatrixM2x2F.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM2x2F set(
    final MatrixM2x2F m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM2x2F.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM2x2F setIdentity(
    final MatrixM2x2F m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2F.identity_row_0);
    m.view.put(MatrixM2x2F.identity_row_1);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM2x2F m,
    final int row,
    final VectorReadable2F v)
  {
    m.setUnsafe(row, 0, v.getXf());
    m.setUnsafe(row, 1, v.getYf());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM2x2F setZero(
    final MatrixM2x2F m)
  {
    m.view.clear();
    m.view.put(MatrixM2x2F.zero_row);
    m.view.put(MatrixM2x2F.zero_row);
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

  public static MatrixM2x2F transpose(
    final MatrixM2x2F m)
  {
    for (int row = 0; row < (2 - 1); row++) {
      for (int column = row + 1; column < 2; column++) {
        final float x = m.view.get((row * 2) + column);
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

  public static MatrixM2x2F transpose(
    final MatrixM2x2F m,
    final MatrixM2x2F out)
  {
    for (int index = 0; index < 4; ++index) {
      out.view.put(index, m.view.get(index));
    }
    return MatrixM2x2F.transpose(out);
  }

  private final ByteBuffer  data;

  private final FloatBuffer view;

  public MatrixM2x2F()
  {
    this.data =
      ByteBuffer.allocateDirect(2 * 2 * 4).order(ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();
    MatrixM2x2F.setIdentity(this);
  }

  public MatrixM2x2F(
    final MatrixM2x2F source)
  {
    this.data =
      ByteBuffer.allocateDirect(2 * 2 * 4).order(ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();

    for (int index = 0; index < 4; ++index) {
      this.view.put(index, source.view.get(index));
    }
  }

  public float get(
    final int row,
    final int column)
  {
    return MatrixM2x2F.get(this, row, column);
  }

  private float getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM2x2F.indexUnsafe(row, column));
  }

  public MatrixM2x2F set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM2x2F.indexChecked(row, column), value);
    return this;
  }

  private MatrixM2x2F setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM2x2F.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 2; ++row) {
      builder.append("[");
      for (int column = 0; column < 2; ++column) {
        builder.append(MatrixM2x2F.get(this, row, column));
        if (column < 2) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
