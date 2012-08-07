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
 * A 3x3 mutable matrix type with double precision elements.
 */

public final class MatrixM3x3D implements MatrixReadableD
{
  private static final double[] identity_row_1 = { 0.0, 1.0, 0.0 };

  private static final double[] identity_row_2 = { 0.0, 0.0, 1.0 };

  private static final double[] zero_row       = { 0.0, 0.0, 0.0 };

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

  public static MatrixM3x3D add(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1,
    final MatrixM3x3D out)
  {
    for (int index = 0; index < 9; ++index) {
      out.view.put(index, m0.view.get(index) + m1.view.get(index));
    }
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

  public static MatrixM3x3D addInPlace(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1)
  {
    return MatrixM3x3D.add(m0, m1, m0);
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

  public static MatrixM3x3D addRowScaled(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.addRowScaledUnsafe(
      m,
      MatrixM3x3D.rowCheck(row_a),
      MatrixM3x3D.rowCheck(row_b),
      MatrixM3x3D.rowCheck(row_c),
      r,
      out);
  }

  public static MatrixM3x3D addRowScaledInPlace(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3D.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM3x3D addRowScaledUnsafe(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM3x3D out)
  {
    final VectorM3D va = new VectorM3D();
    final VectorM3D vb = new VectorM3D();
    MatrixM3x3D.rowUnsafe(m, row_a, va);
    MatrixM3x3D.rowUnsafe(m, row_b, vb);

    VectorM3D.addScaledInPlace(va, vb, r);
    MatrixM3x3D.setRowUnsafe(out, row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column > 2)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 3");
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

  public static MatrixM3x3D copy(
    final MatrixM3x3D input,
    final MatrixM3x3D output)
  {
    for (int index = 0; index < 9; ++index) {
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
    final MatrixM3x3D m)
  {
    final double r0c0 = m.getUnsafe(0, 0);
    final double r0c1 = m.getUnsafe(0, 1);
    final double r0c2 = m.getUnsafe(0, 2);

    final double r1c0 = m.getUnsafe(1, 0);
    final double r1c1 = m.getUnsafe(1, 1);
    final double r1c2 = m.getUnsafe(1, 2);

    final double r2c0 = m.getUnsafe(2, 0);
    final double r2c1 = m.getUnsafe(2, 1);
    final double r2c2 = m.getUnsafe(2, 2);

    double sum = 0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static DoubleBuffer doubleBuffer(
    final MatrixM3x3D m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asDoubleBuffer();
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
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

  public static MatrixM3x3D exchangeRows(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.exchangeRowsUnsafe(
      m,
      MatrixM3x3D.rowCheck(row_a),
      MatrixM3x3D.rowCheck(row_b),
      out);
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code> . This
   * is one of the three "elementary" operations defined on matrices.
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
   * @return <code>m</code>
   */

  public static MatrixM3x3D exchangeRowsInPlace(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3D.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM3x3D exchangeRowsUnsafe(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final MatrixM3x3D out)
  {
    final VectorM3D va = new VectorM3D();
    final VectorM3D vb = new VectorM3D();

    MatrixM3x3D.rowUnsafe(m, row_a, va);
    MatrixM3x3D.rowUnsafe(m, row_b, vb);

    MatrixM3x3D.setRowUnsafe(out, row_a, vb);
    MatrixM3x3D.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static double get(
    final MatrixM3x3D m,
    final int row,
    final int column)
  {
    return m.view.get(MatrixM3x3D.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3D.indexUnsafe(
      MatrixM3x3D.rowCheck(row),
      MatrixM3x3D.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage.
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM3x3D#determinant(MatrixM3x3D)
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM3x3D> invert(
    final MatrixM3x3D m)
  {
    return MatrixM3x3D.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM3x3D#determinant(MatrixM3x3D)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM3x3D> invert(
    final MatrixM3x3D m,
    final MatrixM3x3D out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM3x3D>();
    }

    final double d_inv = 1 / d;

    final double r0c0 = m.getUnsafe(0, 0);
    final double r0c1 = m.getUnsafe(0, 1);
    final double r0c2 = m.getUnsafe(0, 2);

    final double r1c0 = m.getUnsafe(1, 0);
    final double r1c1 = m.getUnsafe(1, 1);
    final double r1c2 = m.getUnsafe(1, 2);

    final double r2c0 = m.getUnsafe(2, 0);
    final double r2c1 = m.getUnsafe(2, 1);
    final double r2c2 = m.getUnsafe(2, 2);

    MatrixM3x3D.set(out, 0, 0, (r1c1 * r2c2) - (r1c2 * r2c1));
    MatrixM3x3D.set(out, 0, 1, (r0c2 * r2c1) - (r0c1 * r2c2));
    MatrixM3x3D.set(out, 0, 2, (r0c1 * r1c2) - (r0c2 * r1c1));

    MatrixM3x3D.set(out, 1, 0, (r1c2 * r2c0) - (r1c0 * r2c2));
    MatrixM3x3D.set(out, 1, 1, (r0c0 * r2c2) - (r0c2 * r2c0));
    MatrixM3x3D.set(out, 1, 2, (r0c2 * r1c0) - (r0c0 * r1c2));

    MatrixM3x3D.set(out, 2, 0, (r1c0 * r2c1) - (r1c1 * r2c0));
    MatrixM3x3D.set(out, 2, 1, (r0c1 * r2c0) - (r0c0 * r2c1));
    MatrixM3x3D.set(out, 2, 2, (r0c0 * r1c1) - (r0c1 * r1c0));

    MatrixM3x3D.scaleInPlace(out, d_inv);
    return new Option.Some<MatrixM3x3D>(out);
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * <code>v</code>, writing the resulting matrix to <code>out</code>.
   * 
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D makeTranslation2D(
    final VectorReadable2D v,
    final MatrixM3x3D out)
  {
    out.setUnsafe(0, 2, v.getXD());
    out.setUnsafe(1, 2, v.getYD());
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * <code>v</code>, writing the resulting matrix to <code>out</code>.
   * 
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D makeTranslation2I(
    final VectorReadable2I v,
    final MatrixM3x3D out)
  {
    out.setUnsafe(0, 2, v.getXI());
    out.setUnsafe(1, 2, v.getYI());
    return out;
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

  public static MatrixM3x3D multiply(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1,
    final MatrixM3x3D out)
  {
    double r0c0 = 0;
    r0c0 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 0);
    r0c0 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 0);
    r0c0 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 0);

    double r1c0 = 0;
    r1c0 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 0);
    r1c0 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 0);
    r1c0 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 0);

    double r2c0 = 0;
    r2c0 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 0);
    r2c0 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 0);
    r2c0 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 0);

    double r0c1 = 0;
    r0c1 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 1);
    r0c1 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 1);
    r0c1 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 1);

    double r1c1 = 0;
    r1c1 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 1);
    r1c1 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 1);
    r1c1 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 1);

    double r2c1 = 0;
    r2c1 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 1);
    r2c1 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 1);
    r2c1 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 1);

    double r0c2 = 0;
    r0c2 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 2);
    r0c2 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 2);
    r0c2 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 2);

    double r1c2 = 0;
    r1c2 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 2);
    r1c2 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 2);
    r1c2 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 2);

    double r2c2 = 0;
    r2c2 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 2);
    r2c2 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 2);
    r2c2 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 2);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(0, 2, r0c2);

    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(1, 2, r1c2);

    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(2, 2, r2c2);

    return out;
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

  public static MatrixM3x3D multiplyInPlace(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1)
  {
    return MatrixM3x3D.multiply(m0, m1, m0);
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

  public static VectorM3D multiplyVector3D(
    final MatrixM3x3D m,
    final VectorReadable3D v,
    final VectorM3D out)
  {
    final VectorM3D row = new VectorM3D();
    final VectorM3D vi = new VectorM3D(v);

    MatrixM3x3D.rowUnsafe(m, 0, row);
    out.x = VectorM3D.dotProduct(row, vi);
    MatrixM3x3D.rowUnsafe(m, 1, row);
    out.y = VectorM3D.dotProduct(row, vi);
    MatrixM3x3D.rowUnsafe(m, 2, row);
    out.z = VectorM3D.dotProduct(row, vi);

    return out;
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM3D row(
    final MatrixM3x3D m,
    final int row,
    final VectorM3D out)
  {
    return MatrixM3x3D.rowUnsafe(m, MatrixM3x3D.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row > 2)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 3");
    }
    return row;
  }

  public static VectorM3D rowUnsafe(
    final MatrixM3x3D m,
    final int row,
    final VectorM3D out)
  {
    out.x = m.getUnsafe(row, 0);
    out.y = m.getUnsafe(row, 1);
    out.z = m.getUnsafe(row, 2);
    return out;
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

  public static MatrixM3x3D scale(
    final MatrixM3x3D m,
    final double r,
    final MatrixM3x3D out)
  {
    for (int index = 0; index < 9; ++index) {
      out.view.put(index, m.view.get(index) * r);
    }
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

  public static MatrixM3x3D scaleInPlace(
    final MatrixM3x3D m,
    final double r)
  {
    return MatrixM3x3D.scale(m, r, m);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM3x3D scaleRow(
    final MatrixM3x3D m,
    final int row,
    final double r)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, m);
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

  public static MatrixM3x3D scaleRow(
    final MatrixM3x3D m,
    final int row,
    final double r,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, out);
  }

  private static MatrixM3x3D scaleRowUnsafe(
    final MatrixM3x3D m,
    final int row,
    final double r,
    final MatrixM3x3D out)
  {
    final VectorM3D v = new VectorM3D();

    MatrixM3x3D.rowUnsafe(m, row, v);
    VectorM3D.scaleInPlace(v, r);

    MatrixM3x3D.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM3x3D set(
    final MatrixM3x3D m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM3x3D.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM3x3D setIdentity(
    final MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.identity_row_0);
    m.view.put(MatrixM3x3D.identity_row_1);
    m.view.put(MatrixM3x3D.identity_row_2);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM3x3D m,
    final int row,
    final VectorReadable3D v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM3x3D setZero(
    final MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.zero_row);
    m.view.put(MatrixM3x3D.zero_row);
    m.view.put(MatrixM3x3D.zero_row);
    return m;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D translateByVector2D(
    final MatrixM3x3D m,
    final VectorReadable2D v,
    final MatrixM3x3D out)
  {
    final double vx = v.getXD();
    final double vy = v.getYD();

    final double c2r0 = (m.getUnsafe(0, 0) * vx) + (m.getUnsafe(0, 1) * vy);
    final double c2r1 = (m.getUnsafe(1, 0) * vx) + (m.getUnsafe(1, 1) * vy);
    final double c2r2 = (m.getUnsafe(2, 0) * vx) + (m.getUnsafe(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    return out;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @return <code>m</code>
   */

  public static MatrixM3x3D translateByVector2DInPlace(
    final MatrixM3x3D m,
    final VectorReadable2D v)
  {
    return MatrixM3x3D.translateByVector2D(m, v, m);
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>out</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D translateByVector2I(
    final MatrixM3x3D m,
    final VectorReadable2I v,
    final MatrixM3x3D out)
  {
    final double vx = v.getXI();
    final double vy = v.getYI();

    final double c2r0 = (m.getUnsafe(0, 0) * vx) + (m.getUnsafe(0, 1) * vy);
    final double c2r1 = (m.getUnsafe(1, 0) * vx) + (m.getUnsafe(1, 1) * vy);
    final double c2r2 = (m.getUnsafe(2, 0) * vx) + (m.getUnsafe(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    return out;
  }

  /**
   * Translate the matrix <code>m</code> by the vector <code>v</code>, storing
   * the resulting matrix in <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @param v
   *          The translation vector.
   * @return <code>m</code>
   */

  public static MatrixM3x3D translateByVector2IInPlace(
    final MatrixM3x3D m,
    final VectorReadable2I v)
  {
    return MatrixM3x3D.translateByVector2I(m, v, m);
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

  public static MatrixM3x3D transpose(
    final MatrixM3x3D m,
    final MatrixM3x3D out)
  {
    for (int index = 0; index < 9; ++index) {
      out.view.put(index, m.view.get(index));
    }
    return MatrixM3x3D.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static MatrixM3x3D transposeInPlace(
    final MatrixM3x3D m)
  {
    for (int row = 0; row < (3 - 1); row++) {
      for (int column = row + 1; column < 3; column++) {
        final double x = m.view.get((row * 3) + column);
        m.view.put((row * 3) + column, m.view.get(row + (3 * column)));
        m.view.put(row + (3 * column), x);
      }
    }
    return m;
  }

  private final ByteBuffer      data;

  private final DoubleBuffer    view;

  private static final double[] identity_row_0 = { 1.0, 0.0, 0.0 };

  public MatrixM3x3D()
  {
    this.data =
      ByteBuffer.allocateDirect(3 * 3 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM3x3D.setIdentity(this);
  }

  public MatrixM3x3D(
    final MatrixM3x3D source)
  {
    this.data =
      ByteBuffer.allocateDirect(3 * 3 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    for (int index = 0; index < 9; ++index) {
      this.view.put(index, source.view.get(index));
    }
  }

  public double get(
    final int row,
    final int column)
  {
    return MatrixM3x3D.get(this, row, column);
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(row, column));
  }

  public MatrixM3x3D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
    return this;
  }

  private MatrixM3x3D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 3; ++row) {
      builder.append("[");
      for (int column = 0; column < 3; ++column) {
        builder.append(MatrixM3x3D.get(this, row, column));
        if (column < 2) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return MatrixM3x3D.get(this, row, column);
  }

  @Override public DoubleBuffer getDoubleBuffer()
  {
    final ByteBuffer b =
      this.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asDoubleBuffer();
  }
}
