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
import java.nio.DoubleBuffer;

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;

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
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>
 * for the three <i>elementary</i> operations defined on matrices.
 * </p>
 */

public class MatrixM3x3D implements MatrixReadable3x3DType
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM3x3D</code> class.
   * </p>
   * <p>
   * The purpose of the class is to allow applications to allocate all storage
   * ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   * <p>
   * The user should allocate one <code>Context</code> value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes
   * a <code>Context</code> value will not generate garbage.
   * </p>
   *
   * @since 5.0.0
   */

  public static class Context
  {
    private final MatrixM3x3D m3a = new MatrixM3x3D();
    private final VectorM3D   v3a = new VectorM3D();
    private final VectorM3D   v3b = new VectorM3D();
    private final VectorM3D   v3c = new VectorM3D();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final MatrixM3x3D getM3A()
    {
      return this.m3a;
    }

    final VectorM3D getV3A()
    {
      return this.v3a;
    }

    final VectorM3D getV3B()
    {
      return this.v3b;
    }

    final VectorM3D getV3C()
    {
      return this.v3c;
    }
  }

  private static final double[] IDENTITY_ROW_0;
  private static final double[] IDENTITY_ROW_1;
  private static final double[] IDENTITY_ROW_2;
  private static final int      VIEW_BYTES;
  private static final int      VIEW_COLS;
  private static final int      VIEW_ELEMENT_SIZE;
  private static final int      VIEW_ELEMENTS;
  private static final int      VIEW_ROWS;
  private static final double[] ZERO_ROW;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM3x3D.VIEW_ROWS * MatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3D.VIEW_ELEMENTS * MatrixM3x3D.VIEW_ELEMENT_SIZE;

    IDENTITY_ROW_0 = new double[3];
    MatrixM3x3D.IDENTITY_ROW_0[0] = 1.0;
    MatrixM3x3D.IDENTITY_ROW_0[1] = 0.0;
    MatrixM3x3D.IDENTITY_ROW_0[2] = 0.0;

    IDENTITY_ROW_1 = new double[3];
    MatrixM3x3D.IDENTITY_ROW_1[0] = 0.0;
    MatrixM3x3D.IDENTITY_ROW_1[1] = 1.0;
    MatrixM3x3D.IDENTITY_ROW_1[2] = 0.0;

    IDENTITY_ROW_2 = new double[3];
    MatrixM3x3D.IDENTITY_ROW_2[0] = 0.0;
    MatrixM3x3D.IDENTITY_ROW_2[1] = 0.0;
    MatrixM3x3D.IDENTITY_ROW_2[2] = 1.0;

    ZERO_ROW = new double[3];
    MatrixM3x3D.ZERO_ROW[0] = 0.0;
    MatrixM3x3D.ZERO_ROW[1] = 0.0;
    MatrixM3x3D.ZERO_ROW[2] = 0.0;
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

  public final static MatrixM3x3D add(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D addInPlace(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1)
  {
    return MatrixM3x3D.add(m0, m1, m0);
  }

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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

  public final static MatrixM3x3D addRowScaled(
    final MatrixReadable3x3DType m,
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

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>m</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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

  public final static MatrixM3x3D addRowScaledInPlace(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3D.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM3x3D addRowScaledUnsafe(
    final MatrixReadable3x3DType m,
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

  public final static MatrixM3x3D copy(
    final MatrixReadable3x3DType input,
    final MatrixM3x3D output)
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
   * @return The determinant
   * @param m
   *          The input matrix.
   */

  public final static double determinant(
    final MatrixReadable3x3DType m)
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
   * @return A view of the buffer that backs this matrix.
   * @param m
   *          The input matrix.
   */

  public final static DoubleBuffer doubleBuffer(
    final MatrixM3x3D m)
  {
    return m.view;
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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

  public final static MatrixM3x3D exchangeRows(
    final MatrixReadable3x3DType m,
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
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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

  public final static MatrixM3x3D exchangeRowsInPlace(
    final MatrixM3x3D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3D.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM3x3D exchangeRowsUnsafe(
    final MatrixReadable3x3DType m,
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

    out.view.rewind();
    return out;
  }

  /**
   * @return The value from the matrix <code>m</code> at row <code>row</code>,
   *         column <code>column</code>.
   * @param m
   *          The matrix
   * @param row
   *          The row
   * @param column
   *          The column
   */

  public final static double get(
    final MatrixReadable3x3DType m,
    final int row,
    final int column)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    return source_view.get(MatrixM3x3D.indexChecked(row, column));
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3D.indexUnsafe(
      MatrixM3x3D.rowCheck(row),
      MatrixM3x3D.columnCheck(column));
  }

  /**
   * <p>
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * </p>
   * <p>
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage.
   * </p>
   */

  private static int indexUnsafe(
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
   * of <code>0</code>. If the function returns <code>None</code>,
   * <code>m</code> is untouched.
   *
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public final static OptionType<MatrixM3x3D> invert(
    final MatrixReadable3x3DType m,
    final MatrixM3x3D out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return Option.none();
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
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public final static OptionType<MatrixM3x3D> invertInPlace(
    final MatrixM3x3D m)
  {
    return MatrixM3x3D.invert(m, m);
  }

  /**
   * <p>
   * Calculate a rotation and translation representing a "camera" looking from
   * the point <code>origin</code> to the point <code>target</code>.
   * <code>target</code> must represent the "up" vector for the camera.
   * Usually, this is simply a unit vector <code>(0, 1, 0)</code> representing
   * the Y axis.
   * </p>
   * <p>
   * The function uses preallocated storage from <code>context</code>.
   * </p>
   * <p>
   * The view is expressed as a rotation matrix and a translation vector,
   * written to <code>out_matrix</code> and <code>out_translation</code>,
   * respectively.
   * </p>
   *
   * @param context
   *          Preallocated storage
   * @param out_matrix
   *          The output matrix
   * @param out_translation
   *          The output translation
   * @param origin
   *          The position of the viewer
   * @param target
   *          The target being viewed
   * @param up
   *          The up vector
   */

  public final static void lookAtWithContext(
    final Context context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final MatrixM3x3D out_matrix,
    final VectorM3D out_translation)
  {
    final VectorM3D forward = context.getV3A();
    final VectorM3D new_up = context.getV3B();
    final VectorM3D side = context.getV3C();

    MatrixM3x3D.setIdentity(out_matrix);

    /**
     * Calculate "forward" vector
     */

    forward.set3D(
      target.getXD() - origin.getXD(),
      target.getYD() - origin.getYD(),
      target.getZD() - origin.getZD());
    VectorM3D.normalizeInPlace(forward);

    /**
     * Calculate "side" vector
     */

    VectorM3D.crossProduct(forward, up, side);
    VectorM3D.normalizeInPlace(side);

    /**
     * Calculate new "up" vector
     */

    VectorM3D.crossProduct(side, forward, new_up);

    /**
     * Calculate rotation matrix
     */

    out_matrix.set(0, 0, side.getXD());
    out_matrix.set(0, 1, side.getYD());
    out_matrix.set(0, 2, side.getZD());
    out_matrix.set(1, 0, new_up.getXD());
    out_matrix.set(1, 1, new_up.getYD());
    out_matrix.set(1, 2, new_up.getZD());
    out_matrix.set(2, 0, -forward.getXD());
    out_matrix.set(2, 1, -forward.getYD());
    out_matrix.set(2, 2, -forward.getZD());

    /**
     * Calculate camera translation matrix
     */

    out_translation.set3D(-origin.getXD(), -origin.getYD(), -origin.getZD());
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
   * @return A rotation matrix.
   */

  public final static MatrixM3x3D makeRotation(
    final double angle,
    final VectorReadable3DType axis)
  {
    final MatrixM3x3D out = new MatrixM3x3D();
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

  public final static MatrixM3x3D makeRotation(
    final double angle,
    final VectorReadable3DType axis,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D makeTranslation2D(
    final VectorReadable2DType v,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D makeTranslation2I(
    final VectorReadable2IType v,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D multiply(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D multiplyInPlace(
    final MatrixM3x3D m0,
    final MatrixReadable3x3DType m1)
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

  public final static VectorM3D multiplyVector3D(
    final MatrixReadable3x3DType m,
    final VectorReadable3DType v,
    final VectorM3D out)
  {
    final VectorM3D row = new VectorM3D();
    final VectorM3D vi = new VectorM3D(v);

    MatrixM3x3D.rowUnsafe(m, 0, row);
    out.setXD(VectorM3D.dotProduct(row, vi));
    MatrixM3x3D.rowUnsafe(m, 1, row);
    out.setYD(VectorM3D.dotProduct(row, vi));
    MatrixM3x3D.rowUnsafe(m, 2, row);
    out.setZD(VectorM3D.dotProduct(row, vi));

    return out;
  }

  private static MatrixM3x3D rotate(
    final double angle,
    final MatrixReadable3x3DType m,
    final MatrixM3x3D tmp,
    final VectorReadable3DType axis,
    final MatrixM3x3D out)
  {
    MatrixM3x3D.makeRotation(angle, axis, tmp);
    MatrixM3x3D.multiply(m, tmp, out);
    out.view.rewind();
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

  public final static MatrixM3x3D rotate(
    final double angle,
    final MatrixReadable3x3DType m,
    final VectorReadable3DType axis,
    final MatrixM3x3D out)
  {
    final MatrixM3x3D tmp = new MatrixM3x3D();
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

  public final static MatrixM3x3D rotateInPlace(
    final double angle,
    final MatrixM3x3D m,
    final VectorReadable3DType axis)
  {
    final MatrixM3x3D tmp = new MatrixM3x3D();
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

  public final static MatrixM3x3D rotateInPlaceWithContext(
    final Context context,
    final double angle,
    final MatrixM3x3D m,
    final VectorReadable3DType axis)
  {
    return MatrixM3x3D.rotate(angle, m, context.getM3A(), axis, m);
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

  public final static MatrixM3x3D rotateWithContext(
    final Context context,
    final double angle,
    final MatrixReadable3x3DType m,
    final VectorReadable3DType axis,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.rotate(angle, m, context.getM3A(), axis, out);
  }

  /**
   * @return Row <code>row</code> of the matrix <code>m</code> in the vector
   *         <code>out</code>.
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param out
   *          The output matrix
   */

  public final static VectorM3D row(
    final MatrixReadable3x3DType m,
    final int row,
    final VectorM3D out)
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

  private static VectorM3D rowUnsafe(
    final MatrixReadable3x3DType m,
    final int row,
    final VectorM3D out)
  {
    out.set3D(
      m.getRowColumnD(row, 0),
      m.getRowColumnD(row, 1),
      m.getRowColumnD(row, 2));
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

  public final static MatrixM3x3D scale(
    final MatrixReadable3x3DType m,
    final double r,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D scaleInPlace(
    final MatrixM3x3D m,
    final double r)
  {
    return MatrixM3x3D.scale(m, r, m);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code (0 <= row < 4)}.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public final static MatrixM3x3D scaleRow(
    final MatrixReadable3x3DType m,
    final int row,
    final double r,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, out);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code (0 <= row < 4)}.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public final static MatrixM3x3D scaleRowInPlace(
    final MatrixM3x3D m,
    final int row,
    final double r)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, m);
  }

  private static MatrixM3x3D scaleRowUnsafe(
    final MatrixReadable3x3DType m,
    final int row,
    final double r,
    final MatrixM3x3D out)
  {
    final VectorM3D v = new VectorM3D();

    MatrixM3x3D.rowUnsafe(m, row, v);
    VectorM3D.scaleInPlace(v, r);

    MatrixM3x3D.setRowUnsafe(out, row, v);

    out.view.rewind();
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

  public final static MatrixM3x3D set(
    final MatrixM3x3D m,
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
   * @param m
   *          The input matrix
   * @return <code>m</code>
   */

  public final static MatrixM3x3D setIdentity(
    final MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.IDENTITY_ROW_0);
    m.view.put(MatrixM3x3D.IDENTITY_ROW_1);
    m.view.put(MatrixM3x3D.IDENTITY_ROW_2);
    m.view.rewind();
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM3x3D m,
    final int row,
    final VectorReadable3DType v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   *
   * @param m
   *          The input matrix
   * @return <code>m</code>
   */

  public final static MatrixM3x3D setZero(
    final MatrixM3x3D m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3D.ZERO_ROW);
    m.view.put(MatrixM3x3D.ZERO_ROW);
    m.view.put(MatrixM3x3D.ZERO_ROW);
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

  public final static double trace(
    final MatrixReadable3x3DType m)
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

  public final static MatrixM3x3D translateByVector2D(
    final MatrixReadable3x3DType m,
    final VectorReadable2DType v,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D translateByVector2DInPlace(
    final MatrixM3x3D m,
    final VectorReadable2DType v)
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

  public final static MatrixM3x3D translateByVector2I(
    final MatrixReadable3x3DType m,
    final VectorReadable2IType v,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D translateByVector2IInPlace(
    final MatrixM3x3D m,
    final VectorReadable2IType v)
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

  public final static MatrixM3x3D transpose(
    final MatrixReadable3x3DType m,
    final MatrixM3x3D out)
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

  public final static MatrixM3x3D transposeInPlace(
    final MatrixM3x3D m)
  {
    for (int row = 0; row < (MatrixM3x3D.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < MatrixM3x3D.VIEW_COLS; ++column) {
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

  /**
   * Construct a new identity matrix.
   */

  public MatrixM3x3D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;
    this.view = v;

    MatrixM3x3D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source
   *          The source matrix.
   */

  public MatrixM3x3D(
    final MatrixReadable3x3DType source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;
    this.view = v;

    final DoubleBuffer source_view = source.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source_view.get(index));
    }

    this.view.rewind();
  }

  @Override public final boolean equals(
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
    final MatrixM3x3D other = (MatrixM3x3D) obj;

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  /**
   * @return The value at the given row and column
   * @param row
   *          The row
   * @param column
   *          The column
   */

  public final double get(
    final int row,
    final int column)
  {
    return MatrixM3x3D.get(this, row, column);
  }

  @Override public final DoubleBuffer getDoubleBuffer()
  {
    return this.view;
  }

  @Override public final void getRow3D(
    final int row,
    final VectorM3D out)
  {
    MatrixM3x3D.rowUnsafe(this, MatrixM3x3D.rowCheck(row), out);
  }

  @Override public final double getRowColumnD(
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

  @Override public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  /**
   * Set the value at the given row and column.
   *
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @return <code>this</code>
   */

  public final MatrixM3x3D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
    this.view.rewind();
    return this;
  }

  final MatrixM3x3D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public final String toString()
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
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
