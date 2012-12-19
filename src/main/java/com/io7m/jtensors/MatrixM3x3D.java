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
 * <p>
 * A 3x3 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM3x3D</code> are backed by direct memory, with
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

@NotThreadSafe public final class MatrixM3x3D implements MatrixReadable3x3D
{
  /**
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM3x3D</code> class.
   * 
   * <p>
   * The purpose of the class is to allow applications to allocate all storage
   * ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   * 
   * <p>
   * The user should allocate one <code>Context</code> value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes
   * a <code>Context</code> value will not generate garbage.
   * </p>
   * 
   * @since 5.0.0
   */

  @NotThreadSafe public static final class Context
  {
    final @Nonnull MatrixM3x3D m4a = new MatrixM3x3D();

    public Context()
    {

    }
  }

  private static final double[] identity_row_0 = { 1.0, 0.0, 0.0 };
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
    final @Nonnull MatrixReadable3x3D m0,
    final @Nonnull MatrixReadable3x3D m1,
    final @Nonnull MatrixM3x3D out)
  {
    final DoubleBuffer m0_view = m0.getDoubleBuffer();
    final DoubleBuffer m1_view = m1.getDoubleBuffer();

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      out.view.put(index, m0_view.get(index) + m1_view.get(index));
    }

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m0,
    final @Nonnull MatrixM3x3D m1)
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
    final @Nonnull MatrixReadable3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM3x3D out)
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
    final @Nonnull MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3D.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM3x3D addRowScaledUnsafe(
    final @Nonnull MatrixReadable3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM3x3D out)
  {
    final @Nonnull VectorM3D va = new VectorM3D();
    final @Nonnull VectorM3D vb = new VectorM3D();
    MatrixM3x3D.rowUnsafe(m, row_a, va);
    MatrixM3x3D.rowUnsafe(m, row_b, vb);

    VectorM3D.addScaledInPlace(va, vb, r);
    MatrixM3x3D.setRowUnsafe(out, row_c, va);

    out.view.rewind();
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM3x3D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM3x3D.VIEW_COLS);
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
    final @Nonnull MatrixReadable3x3D input,
    final @Nonnull MatrixM3x3D output)
  {
    final DoubleBuffer source_view = input.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      output.view.put(index, source_view.get(index));
    }

    output.view.rewind();
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static double determinant(
    final @Nonnull MatrixReadable3x3D m)
  {
    final double r0c0 = m.getRowColumnD(0, 0);
    final double r0c1 = m.getRowColumnD(0, 1);
    final double r0c2 = m.getRowColumnD(0, 2);

    final double r1c0 = m.getRowColumnD(1, 0);
    final double r1c1 = m.getRowColumnD(1, 1);
    final double r1c2 = m.getRowColumnD(1, 2);

    final double r2c0 = m.getRowColumnD(2, 0);
    final double r2c1 = m.getRowColumnD(2, 1);
    final double r2c2 = m.getRowColumnD(2, 2);

    double sum = 0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * Return a view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static DoubleBuffer doubleBuffer(
    final @Nonnull MatrixM3x3D m)
  {
    return m.view;
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
    final @Nonnull MatrixReadable3x3D m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM3x3D out)
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
    final @Nonnull MatrixM3x3D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3D.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM3x3D exchangeRowsUnsafe(
    final @Nonnull MatrixReadable3x3D m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM3x3D out)
  {
    final @Nonnull VectorM3D va = new VectorM3D();
    final @Nonnull VectorM3D vb = new VectorM3D();

    MatrixM3x3D.rowUnsafe(m, row_a, va);
    MatrixM3x3D.rowUnsafe(m, row_b, vb);

    MatrixM3x3D.setRowUnsafe(out, row_a, vb);
    MatrixM3x3D.setRowUnsafe(out, row_b, va);

    out.view.rewind();
    return out;
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static double get(
    final @Nonnull MatrixReadable3x3D m,
    final int row,
    final int column)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    return source_view.get(MatrixM3x3D.indexChecked(row, column));
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
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see MatrixM3x3D#determinant(MatrixReadable3x3D)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM3x3D> invert(
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull MatrixM3x3D out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM3x3D>();
    }

    final double d_inv = 1 / d;

    final double orig_r0c0 = m.getRowColumnD(0, 0);
    final double orig_r0c1 = m.getRowColumnD(0, 1);
    final double orig_r0c2 = m.getRowColumnD(0, 2);

    final double orig_r1c0 = m.getRowColumnD(1, 0);
    final double orig_r1c1 = m.getRowColumnD(1, 1);
    final double orig_r1c2 = m.getRowColumnD(1, 2);

    final double orig_r2c0 = m.getRowColumnD(2, 0);
    final double orig_r2c1 = m.getRowColumnD(2, 1);
    final double orig_r2c2 = m.getRowColumnD(2, 2);

    final double r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final double r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final double r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final double r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final double r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final double r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final double r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final double r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final double r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    MatrixM3x3D.set(out, 0, 0, r0c0);
    MatrixM3x3D.set(out, 0, 1, r0c1);
    MatrixM3x3D.set(out, 0, 2, r0c2);

    MatrixM3x3D.set(out, 1, 0, r1c0);
    MatrixM3x3D.set(out, 1, 1, r1c1);
    MatrixM3x3D.set(out, 1, 2, r1c2);

    MatrixM3x3D.set(out, 2, 0, r2c0);
    MatrixM3x3D.set(out, 2, 1, r2c1);
    MatrixM3x3D.set(out, 2, 2, r2c2);

    MatrixM3x3D.scaleInPlace(out, d_inv);

    out.view.rewind();
    return new Option.Some<MatrixM3x3D>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see MatrixM3x3D#determinant(MatrixReadable3x3D)
   * 
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM3x3D> invertInPlace(
    final @Nonnull MatrixM3x3D m)
  {
    return MatrixM3x3D.invert(m, m);
  }

  /**
   * <p>
   * Generate and return a matrix that represents a rotation of
   * <code>angle</code> radians around the axis <code>axis</code>.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
   * @since 5.0.0
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   */

  public static MatrixM3x3D makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM3x3D out = new MatrixM3x3D();
    MatrixM3x3D.makeRotation(angle, axis, out);
    out.view.rewind();
    return out;
  }

  /**
   * <p>
   * Generate a matrix that represents a rotation of <code>angle</code>
   * radians around the axis <code>axis</code> and save to <code>out</code>.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
   * @since 5.0.0
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3D out)
  {
    final double axis_x = axis.getXD();
    final double axis_y = axis.getYD();
    final double axis_z = axis.getZD();

    final double s = Math.sin(angle);
    final double c = Math.cos(angle);
    final double t = 1 - c;

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

    final double r1c0 = txy + sz;
    final double r1c1 = ty_sq + c;
    final double r1c2 = tyz - sx;

    final double r2c0 = txz - sy;
    final double r2c1 = tyz + sx;
    final double r2c2 = tz_sq + c;

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(0, 2, r0c2);

    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(1, 2, r1c2);

    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(2, 2, r2c2);

    out.view.rewind();
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

  public static MatrixM3x3D makeTranslation2D(
    final @Nonnull VectorReadable2D v,
    final @Nonnull MatrixM3x3D out)
  {
    out.setUnsafe(0, 2, v.getXD());
    out.setUnsafe(1, 2, v.getYD());
    out.view.rewind();
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
    final @Nonnull VectorReadable2I v,
    final @Nonnull MatrixM3x3D out)
  {
    out.setUnsafe(0, 2, v.getXI());
    out.setUnsafe(1, 2, v.getYI());
    out.view.rewind();
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
    final @Nonnull MatrixReadable3x3D m0,
    final @Nonnull MatrixReadable3x3D m1,
    final @Nonnull MatrixM3x3D out)
  {
    double r0c0 = 0;
    r0c0 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 0);
    r0c0 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 0);
    r0c0 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 0);

    double r1c0 = 0;
    r1c0 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 0);
    r1c0 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 0);
    r1c0 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 0);

    double r2c0 = 0;
    r2c0 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 0);
    r2c0 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 0);
    r2c0 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 0);

    double r0c1 = 0;
    r0c1 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 1);
    r0c1 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 1);
    r0c1 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 1);

    double r1c1 = 0;
    r1c1 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 1);
    r1c1 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 1);
    r1c1 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 1);

    double r2c1 = 0;
    r2c1 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 1);
    r2c1 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 1);
    r2c1 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 1);

    double r0c2 = 0;
    r0c2 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 2);
    r0c2 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 2);
    r0c2 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 2);

    double r1c2 = 0;
    r1c2 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 2);
    r1c2 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 2);
    r1c2 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 2);

    double r2c2 = 0;
    r2c2 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 2);
    r2c2 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 2);
    r2c2 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 2);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(0, 2, r0c2);

    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(1, 2, r1c2);

    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(2, 2, r2c2);

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m0,
    final @Nonnull MatrixReadable3x3D m1)
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
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull VectorReadable3D v,
    final @Nonnull VectorM3D out)
  {
    final @Nonnull VectorM3D row = new VectorM3D();
    final @Nonnull VectorM3D vi = new VectorM3D(v);

    MatrixM3x3D.rowUnsafe(m, 0, row);
    out.x = VectorM3D.dotProduct(row, vi);
    MatrixM3x3D.rowUnsafe(m, 1, row);
    out.y = VectorM3D.dotProduct(row, vi);
    MatrixM3x3D.rowUnsafe(m, 2, row);
    out.z = VectorM3D.dotProduct(row, vi);

    return out;
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>.
   * 
   * @since 5.0.0
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D rotate(
    final double angle,
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3D out)
  {
    final @Nonnull MatrixM3x3D tmp = new MatrixM3x3D();
    return MatrixM3x3D.rotate(angle, m, tmp, axis, out);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>.
   * 
   * @since 5.0.0
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @return <code>m</code>
   */

  public static MatrixM3x3D rotateInPlace(
    final double angle,
    final @Nonnull MatrixM3x3D m,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM3x3D tmp = new MatrixM3x3D();
    return MatrixM3x3D.rotate(angle, m, tmp, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
   * 
   * @since 5.0.0
   * @param context
   *          Preallocated storage.
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @return <code>m</code>
   */

  public static MatrixM3x3D rotateInPlaceWithContext(
    final @Nonnull Context context,
    final double angle,
    final @Nonnull MatrixM3x3D m,
    final @Nonnull VectorReadable3D axis)
  {
    return MatrixM3x3D.rotate(angle, m, context.m4a, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
   * 
   * @since 5.0.0
   * @param context
   *          Preallocated storage.
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM3x3D rotateWithContext(
    final @Nonnull Context context,
    final double angle,
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3D out)
  {
    return MatrixM3x3D.rotate(angle, m, context.m4a, axis, out);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM3D row(
    final @Nonnull MatrixReadable3x3D m,
    final int row,
    final @Nonnull VectorM3D out)
  {
    return MatrixM3x3D.rowUnsafe(m, MatrixM3x3D.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM3x3D.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM3x3D.VIEW_ROWS);
    }
    return row;
  }

  public static VectorM3D rowUnsafe(
    final @Nonnull MatrixReadable3x3D m,
    final int row,
    final @Nonnull VectorM3D out)
  {
    out.x = m.getRowColumnD(row, 0);
    out.y = m.getRowColumnD(row, 1);
    out.z = m.getRowColumnD(row, 2);
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
    final @Nonnull MatrixReadable3x3D m,
    final double r,
    final @Nonnull MatrixM3x3D out)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index) * r);
    }

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m,
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

  public static @Nonnull MatrixM3x3D scaleRowInPlace(
    final @Nonnull MatrixM3x3D m,
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
    final @Nonnull MatrixReadable3x3D m,
    final int row,
    final double r,
    final @Nonnull MatrixM3x3D out)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, out);
  }

  private static MatrixM3x3D scaleRowUnsafe(
    final @Nonnull MatrixReadable3x3D m,
    final int row,
    final double r,
    final @Nonnull MatrixM3x3D out)
  {
    final @Nonnull VectorM3D v = new VectorM3D();

    MatrixM3x3D.rowUnsafe(m, row, v);
    VectorM3D.scaleInPlace(v, r);

    MatrixM3x3D.setRowUnsafe(out, row, v);

    out.view.rewind();
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM3x3D set(
    final @Nonnull MatrixM3x3D m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM3x3D.indexChecked(row, column), value);
    m.view.rewind();
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM3x3D setIdentity(
    final @Nonnull MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.identity_row_0);
    m.view.put(MatrixM3x3D.identity_row_1);
    m.view.put(MatrixM3x3D.identity_row_2);
    m.view.rewind();
    return m;
  }

  private static void setRowUnsafe(
    final @Nonnull MatrixM3x3D m,
    final int row,
    final @Nonnull VectorReadable3D v)
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
    final @Nonnull MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.zero_row);
    m.view.put(MatrixM3x3D.zero_row);
    m.view.put(MatrixM3x3D.zero_row);
    m.view.rewind();
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

  public static double trace(
    final @Nonnull MatrixReadable3x3D m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2);
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
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull VectorReadable2D v,
    final @Nonnull MatrixM3x3D out)
  {
    final double vx = v.getXD();
    final double vy = v.getYD();

    final double c2r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c2r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c2r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m,
    final @Nonnull VectorReadable2D v)
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
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull VectorReadable2I v,
    final @Nonnull MatrixM3x3D out)
  {
    final double vx = v.getXI();
    final double vy = v.getYI();

    final double c2r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c2r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c2r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m,
    final @Nonnull VectorReadable2I v)
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
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull MatrixM3x3D out)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index));
    }

    out.view.rewind();
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
    final @Nonnull MatrixM3x3D m)
  {
    for (int row = 0; row < (MatrixM3x3D.VIEW_ROWS - 1); row++) {
      for (int column = row + 1; column < MatrixM3x3D.VIEW_COLS; column++) {
        final double x = m.view.get((row * MatrixM3x3D.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM3x3D.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM3x3D.VIEW_COLS * column)));
        m.view.put(row + (MatrixM3x3D.VIEW_COLS * column), x);
      }
    }

    m.view.rewind();
    return m;
  }

  private final ByteBuffer   data;

  private final DoubleBuffer view;

  private static final int   VIEW_ELEMENT_SIZE;

  private static final int   VIEW_ELEMENTS;

  private static final int   VIEW_BYTES;

  private static final int   VIEW_COLS;

  private static final int   VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM3x3D.VIEW_ROWS * MatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3D.VIEW_ELEMENTS * MatrixM3x3D.VIEW_ELEMENT_SIZE;
  }

  private static @Nonnull MatrixM3x3D rotate(
    final double angle,
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull MatrixM3x3D tmp,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3D out)
  {
    MatrixM3x3D.makeRotation(angle, axis, tmp);
    MatrixM3x3D.multiply(m, tmp, out);
    out.view.rewind();
    return out;
  }

  public MatrixM3x3D()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM3x3D.setIdentity(this);
  }

  public MatrixM3x3D(
    final @Nonnull MatrixReadable3x3D source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    final DoubleBuffer source_view = source.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
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
    final @Nonnull MatrixM3x3D other = (MatrixM3x3D) obj;

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
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
    return MatrixM3x3D.get(this, row, column);
  }

  @Override public DoubleBuffer getDoubleBuffer()
  {
    return this.view;
  }

  @Override public void getRow3D(
    final int row,
    final @Nonnull VectorM3D out)
  {
    MatrixM3x3D.rowUnsafe(this, MatrixM3x3D.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  public MatrixM3x3D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
    this.view.rewind();
    return this;
  }

  MatrixM3x3D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
      builder.append("[");
      for (int column = 0; column < MatrixM3x3D.VIEW_COLS; ++column) {
        builder.append(MatrixM3x3D.get(this, row, column));
        if (column < (MatrixM3x3D.VIEW_COLS - 1)) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
