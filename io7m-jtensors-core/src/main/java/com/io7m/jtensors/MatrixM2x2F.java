/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;

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
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 * <p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed for
 * the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * </p>
 */

public final class MatrixM2x2F implements MatrixDirectReadable2x2FType
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
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final MatrixM2x2F out)
  {
    final float r0c0 = m0.getRowColumnF(0, 0) + m1.getRowColumnF(0, 0);
    final float r1c0 = m0.getRowColumnF(1, 0) + m1.getRowColumnF(1, 0);

    final float r0c1 = m0.getRowColumnF(0, 1) + m1.getRowColumnF(0, 1);
    final float r1c1 = m0.getRowColumnF(1, 1) + m1.getRowColumnF(1, 1);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(1, 0, r1c0);

    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 1, r1c1);
    return out;
  }

  /**
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   *
   * @since 5.0.0
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   */

  public static MatrixM2x2F addInPlace(
    final MatrixM2x2F m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.add(m0, m1, m0);
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

  public static MatrixM2x2F addRowScaled(
    final MatrixReadable2x2FType m,
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

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>m</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM2x2F addRowScaledInPlace(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM2x2F.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM2x2F addRowScaledUnsafe(
    final MatrixReadable2x2FType m,
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
    if ((column < 0) || (column >= MatrixM2x2F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM2x2F.VIEW_COLS);
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
    final MatrixReadable2x2FType input,
    final MatrixM2x2F output)
  {
    for (int col = 0; col < MatrixM2x2F.VIEW_COLS; ++col) {
      for (int row = 0; row < MatrixM2x2F.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnF(row, col));
      }
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   *
   * @return The determinant
   * @param m
   *          The input matrix.
   */

  public static float determinant(
    final MatrixReadable2x2FType m)
  {
    final float r0c0 = m.getRowColumnF(0, 0);
    final float r0c1 = m.getRowColumnF(0, 1);
    final float r1c0 = m.getRowColumnF(1, 0);
    final float r1c1 = m.getRowColumnF(1, 1);

    return (r0c0 * r1c1) - (r0c1 * r1c0);
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

  public static MatrixM2x2F exchangeRows(
    final MatrixReadable2x2FType m,
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

  /**
   * <p>
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM2x2F exchangeRowsInPlace(
    final MatrixM2x2F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM2x2F.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM2x2F exchangeRowsUnsafe(
    final MatrixReadable2x2FType m,
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

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM2x2F.indexUnsafe(
      MatrixM2x2F.rowCheck(row),
      MatrixM2x2F.columnCheck(column));
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

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>. If the function returns <code>None</code>,
   * <code>m</code> is untouched.
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   *
   * @return <code>out</code>
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static OptionType<MatrixM2x2F> invert(
    final MatrixReadable2x2FType m,
    final MatrixM2x2F out)
  {
    final float d = MatrixM2x2F.determinant(m);

    if (d == 0) {
      return Option.none();
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

    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>. If the function returns <code>None</code>, <code>m</code>
   * is untouched.
   *
   * @see MatrixM2x2F#determinant(MatrixReadable2x2FType)
   *
   * @return <code>m</code>
   * @param m
   *          The input matrix.
   */

  public static OptionType<MatrixM2x2F> invertInPlace(
    final MatrixM2x2F m)
  {
    return MatrixM2x2F.invert(m, m);
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

  public static MatrixM2x2F multiply(
    final MatrixReadable2x2FType m0,
    final MatrixReadable2x2FType m1,
    final MatrixM2x2F out)
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

  public static MatrixM2x2F multiplyInPlace(
    final MatrixM2x2F m0,
    final MatrixReadable2x2FType m1)
  {
    return MatrixM2x2F.multiply(m0, m1, m0);
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
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <V extends VectorWritable2FType> V multiplyVector2F(
    final MatrixReadable2x2FType m,
    final VectorReadable2FType v,
    final V out)
  {
    final VectorM2F row = new VectorM2F();
    final VectorM2F vi = new VectorM2F(v);

    m.getRow2F(0, row);
    out.setXF(VectorM2F.dotProduct(row, vi));
    m.getRow2F(1, row);
    out.setYF(VectorM2F.dotProduct(row, vi));
    return out;
  }

  /**
   * @return Row <code>row</code> of the matrix <code>m</code> in the vector
   *         <code>out</code>.
   * @param m
   *          The matrix
   * @param row
   *          The row
   * @param out
   *          The output vector
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <V extends VectorWritable2FType> V row(
    final MatrixReadable2x2FType m,
    final int row,
    final V out)
  {
    return MatrixM2x2F.rowUnsafe(m, MatrixM2x2F.rowCheck(row), out);
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

  private static <V extends VectorWritable2FType> V rowUnsafe(
    final MatrixReadable2x2FType m,
    final int row,
    final V out)
  {
    out.set2F(m.getRowColumnF(row, 0), m.getRowColumnF(row, 1));
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
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM2x2F scale(
    final MatrixReadable2x2FType m,
    final float r,
    final MatrixM2x2F out)
  {
    out.setUnsafe(0, 0, m.getRowColumnF(0, 0) * r);
    out.setUnsafe(1, 0, m.getRowColumnF(1, 0) * r);
    out.setUnsafe(0, 1, m.getRowColumnF(0, 1) * r);
    out.setUnsafe(1, 1, m.getRowColumnF(1, 1) * r);
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

  public static MatrixM2x2F scaleInPlace(
    final MatrixM2x2F m,
    final float r)
  {
    return MatrixM2x2F.scale(m, r, m);
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

  public static MatrixM2x2F scaleRow(
    final MatrixReadable2x2FType m,
    final int row,
    final float r,
    final MatrixM2x2F out)
  {
    return MatrixM2x2F.scaleRowUnsafe(m, MatrixM2x2F.rowCheck(row), r, out);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM2x2F scaleRowInPlace(
    final MatrixM2x2F m,
    final int row,
    final float r)
  {
    return MatrixM2x2F.scaleRowUnsafe(m, MatrixM2x2F.rowCheck(row), r, m);
  }

  private static MatrixM2x2F scaleRowUnsafe(
    final MatrixReadable2x2FType m,
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
   *
   * @param m
   *          The matrix
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @return <code>m</code>
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
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM2x2F setIdentity(
    final MatrixM2x2F m)
  {
    m.view.clear();

    for (int row = 0; row < MatrixM2x2F.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM2x2F.VIEW_COLS; ++col) {
        if (row == col) {
          m.setUnsafe(row, col, 1.0f);
        } else {
          m.setUnsafe(row, col, 0.0f);
        }
      }
    }
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM2x2F m,
    final int row,
    final VectorReadable2FType v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   *
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM2x2F setZero(
    final MatrixM2x2F m)
  {
    m.view.clear();
    for (int index = 0; index < (MatrixM2x2F.VIEW_ROWS * MatrixM2x2F.VIEW_COLS); ++index) {
      m.view.put(index, 0.0f);
    }
    return m;
  }

  /**
   * Return the trace of the matrix <code>m</code>. The trace is defined as
   * the sum of the diagonal elements of the matrix.
   *
   * @since 5.0.0
   * @param m
   *          The input matrix
   * @return The trace of the matrix
   */

  public static float trace(
    final MatrixReadable2x2FType m)
  {
    return m.getRowColumnF(0, 0) + m.getRowColumnF(1, 1);
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
    final MatrixReadable2x2FType m,
    final MatrixM2x2F out)
  {
    MatrixM2x2F.copy(m, out);
    return MatrixM2x2F.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static MatrixM2x2F transposeInPlace(
    final MatrixM2x2F m)
  {
    for (int row = 0; row < (2 - 1); ++row) {
      for (int column = row + 1; column < 2; ++column) {
        final float x = m.view.get((row * 2) + column);
        m.view.put((row * 2) + column, m.view.get(row + (2 * column)));
        m.view.put(row + (2 * column), x);
      }
    }

    return m;
  }

  @SuppressWarnings("unused") private final ByteBuffer data;
  private final FloatBuffer                            view;

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
   * @param source
   *          The source matrix
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

    for (int row = 0; row < MatrixM2x2F.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM2x2F.VIEW_COLS; ++col) {
        this.setUnsafe(row, col, source.getRowColumnF(row, col));
      }
    }
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
    MatrixM2x2F.rowUnsafe(this, MatrixM2x2F.rowCheck(row), out);
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
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM2x2F.VIEW_ELEMENTS; ++index) {
      result += Float.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  /**
   * @return Set the value at the given row and column.
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   */

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

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
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
}
