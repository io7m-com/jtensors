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

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>
 * A 3x3 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM3x3D} are backed by direct memory, with
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

public final class MatrixM3x3D implements
  MatrixDirectReadable3x3DType,
  MatrixWritable3x3DType
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the {@code MatrixM3x3D} class.
   * </p>
   * <p>
   * The purpose of the class is to allow applications to allocate all storage
   * ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   * <p>
   * The user should allocate one {@code Context} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes
   * a {@code Context} value will not generate garbage.
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

  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM3x3D.VIEW_ROWS * MatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3D.VIEW_ELEMENTS * MatrixM3x3D.VIEW_ELEMENT_SIZE;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D add(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final MatrixM3x3D out)
  {
    final double r0c0 = m0.getRowColumnD(0, 0) + m1.getRowColumnD(0, 0);
    final double r1c0 = m0.getRowColumnD(1, 0) + m1.getRowColumnD(1, 0);
    final double r2c0 = m0.getRowColumnD(2, 0) + m1.getRowColumnD(2, 0);

    final double r0c1 = m0.getRowColumnD(0, 1) + m1.getRowColumnD(0, 1);
    final double r1c1 = m0.getRowColumnD(1, 1) + m1.getRowColumnD(1, 1);
    final double r2c1 = m0.getRowColumnD(2, 1) + m1.getRowColumnD(2, 1);

    final double r0c2 = m0.getRowColumnD(0, 2) + m1.getRowColumnD(0, 2);
    final double r1c2 = m0.getRowColumnD(1, 2) + m1.getRowColumnD(1, 2);
    final double r2c2 = m0.getRowColumnD(2, 2) + m1.getRowColumnD(2, 2);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(2, 0, r2c0);

    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(2, 1, r2c1);

    out.setUnsafe(0, 2, r0c2);
    out.setUnsafe(1, 2, r1c2);
    out.setUnsafe(2, 2, r2c2);

    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1},
   * returning the result in {@code m0}.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return {@code m0}
   */

  public static MatrixM3x3D addInPlace(
    final MatrixM3x3D m0,
    final MatrixM3x3D m1)
  {
    return MatrixM3x3D.add(m0, m1, m0);
  }

  /**
   * <p>
   * Add the values in row {@code row_b} to the values in row
   * {@code row_a} scaled by {@code r}, saving the resulting row in
   * row {@code row_c} of the matrix {@code out}.
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
   * @return {@code out}
   */

  public static MatrixM3x3D addRowScaled(
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
   * Add the values in row {@code row_b} to the values in row
   * {@code row_a} scaled by {@code r}, saving the resulting row in
   * row {@code row_c} of the matrix {@code m}.
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
   * @return {@code m}
   */

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
   * Copy the contents of the matrix {@code input} to the matrix
   * {@code output}, completely replacing all elements.
   *
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return {@code output}
   */

  public static MatrixM3x3D copy(
    final MatrixReadable3x3DType input,
    final MatrixM3x3D output)
  {
    for (int col = 0; col < MatrixM3x3D.VIEW_COLS; ++col) {
      for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnD(row, col));
      }
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @return The determinant
   * @param m
   *          The input matrix.
   */

  public static double determinant(
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

    double sum = 0.0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * <p>
   * Exchange the row {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code out}.
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
   * @return {@code out}
   */

  public static MatrixM3x3D exchangeRows(
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
   * Exchange the row {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code m}.
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
   * @return {@code m}
   */

  public static MatrixM3x3D exchangeRowsInPlace(
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
    return out;
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
   * Calculate the inverse of the matrix {@code m}, saving the resulting
   * matrix to {@code out}. The function returns {@code Some(out)}
   * iff it was possible to invert the matrix, and {@code None}
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of {@code 0}. If the function returns {@code None},
   * {@code m} is untouched.
   *
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static OptionType<MatrixM3x3D> invert(
    final MatrixReadable3x3DType m,
    final MatrixM3x3D out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return Option.none();
    }

    final double d_inv = 1.0 / d;

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
    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting
   * matrix to {@code m}. The function returns {@code Some(m)} iff
   * it was possible to invert the matrix, and {@code None} otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * {@code 0}. If the function returns {@code None}, {@code m}
   * is untouched.
   *
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @return {@code m}
   */

  public static OptionType<MatrixM3x3D> invertInPlace(
    final MatrixM3x3D m)
  {
    return MatrixM3x3D.invert(m, m);
  }

  /**
   * <p>
   * Calculate a rotation and translation representing a "camera" looking from
   * the point {@code origin} to the point {@code target}.
   * {@code target} must represent the "up" vector for the camera.
   * Usually, this is simply a unit vector {@code (0, 1, 0)} representing
   * the Y axis.
   * </p>
   * <p>
   * The function uses preallocated storage from {@code context}.
   * </p>
   * <p>
   * The view is expressed as a rotation matrix and a translation vector,
   * written to {@code out_matrix} and {@code out_translation},
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

  public static void lookAtWithContext(
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
   * {@code angle} radians around the axis {@code axis}.
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

  public static MatrixM3x3D makeRotation(
    final double angle,
    final VectorReadable3DType axis)
  {
    final MatrixM3x3D out = new MatrixM3x3D();
    MatrixM3x3D.makeRotationInto(angle, axis, out);
    return out;
  }

  /**
   * <p>
   * Generate a matrix that represents a rotation of {@code angle}
   * radians around the axis {@code axis} and save to {@code out}.
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
   * @return {@code out}
   */

  public static MatrixM3x3D makeRotationInto(
    final double angle,
    final VectorReadable3DType axis,
    final MatrixM3x3D out)
  {
    final double axis_x = axis.getXD();
    final double axis_y = axis.getYD();
    final double axis_z = axis.getZD();

    final double s = Math.sin(angle);
    final double c = Math.cos(angle);
    final double t = 1.0 - c;

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

    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D makeTranslation2D(
    final VectorReadable2DType v,
    final MatrixM3x3D out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, v.getXD());
    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, v.getYD());
    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D makeTranslation2I(
    final VectorReadable2IType v,
    final MatrixM3x3D out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, (double) v.getXI());
    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, (double) v.getYI());
    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1},
   * writing the result to {@code out}.
   *
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return {@code out}
   */

  public static MatrixM3x3D multiply(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final MatrixM3x3D out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 0);
    r0c0 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 0);
    r0c0 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 0);

    double r1c0 = 0.0;
    r1c0 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 0);
    r1c0 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 0);
    r1c0 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 0);

    double r2c0 = 0.0;
    r2c0 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 0);
    r2c0 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 0);
    r2c0 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 0);

    double r0c1 = 0.0;
    r0c1 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 1);
    r0c1 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 1);
    r0c1 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 1);

    double r1c1 = 0.0;
    r1c1 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 1);
    r1c1 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 1);
    r1c1 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 1);

    double r2c1 = 0.0;
    r2c1 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 1);
    r2c1 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 1);
    r2c1 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 1);

    double r0c2 = 0.0;
    r0c2 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 2);
    r0c2 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 2);
    r0c2 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 2);

    double r1c2 = 0.0;
    r1c2 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 2);
    r1c2 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 2);
    r1c2 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 2);

    double r2c2 = 0.0;
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
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1},
   * writing the result to {@code m0}.
   *
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @return {@code out}
   */

  public static MatrixM3x3D multiplyInPlace(
    final MatrixM3x3D m0,
    final MatrixReadable3x3DType m1)
  {
    return MatrixM3x3D.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v},
   * writing the resulting vector to {@code out}.
   *
   * @param m
   *          The input matrix.
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return {@code out}
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <V extends VectorWritable3DType> V multiplyVector3D(
    final MatrixReadable3x3DType m,
    final VectorReadable3DType v,
    final V out)
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

  /**
   * @return Row {@code row} of the matrix {@code m} in the vector
   *         {@code out}.
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param out
   *          The output matrix
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <V extends VectorWritable3DType> V row(
    final MatrixReadable3x3DType m,
    final int row,
    final V out)
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

  private static <V extends VectorWritable3DType> V rowUnsafe(
    final MatrixReadable3x3DType m,
    final int row,
    final V out)
  {
    out.set3D(
      m.getRowColumnD(row, 0),
      m.getRowColumnD(row, 1),
      m.getRowColumnD(row, 2));
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value
   * {@code r}, saving the result in {@code out}.
   *
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D scale(
    final MatrixReadable3x3DType m,
    final double r,
    final MatrixM3x3D out)
  {
    final double r0c0 = m.getRowColumnD(0, 0) * r;
    final double r1c0 = m.getRowColumnD(1, 0) * r;
    final double r2c0 = m.getRowColumnD(2, 0) * r;

    final double r0c1 = m.getRowColumnD(0, 1) * r;
    final double r1c1 = m.getRowColumnD(1, 1) * r;
    final double r2c1 = m.getRowColumnD(2, 1) * r;

    final double r0c2 = m.getRowColumnD(0, 2) * r;
    final double r1c2 = m.getRowColumnD(1, 2) * r;
    final double r2c2 = m.getRowColumnD(2, 2) * r;

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(2, 0, r2c0);

    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(2, 1, r2c1);

    out.setUnsafe(0, 2, r0c2);
    out.setUnsafe(1, 2, r1c2);
    out.setUnsafe(2, 2, r2c2);

    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value
   * {@code r}, saving the result in {@code m}.
   *
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return {@code m}
   */

  public static MatrixM3x3D scaleInPlace(
    final MatrixM3x3D m,
    final double r)
  {
    return MatrixM3x3D.scale(m, r, m);
  }

  /**
   * <p>
   * Scale row {@code r} of the matrix {@code m} by {@code r},
   * saving the result to row {@code r} of {@code out}.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code (0 <= row < 3)}.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D scaleRow(
    final MatrixReadable3x3DType m,
    final int row,
    final double r,
    final MatrixM3x3D out)
  {
    return MatrixM3x3D.scaleRowUnsafe(m, MatrixM3x3D.rowCheck(row), r, out);
  }

  /**
   * <p>
   * Scale row {@code r} of the matrix {@code m} by {@code r},
   * saving the result to row {@code r} of {@code m}.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code (0 <= row < 3)}.
   * @param r
   *          The scaling value.
   * @return {@code m}
   */

  public static MatrixM3x3D scaleRowInPlace(
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
    return out;
  }

  /**
   * Set the value in the matrix {@code m} at row {@code row},
   * column {@code column} to {@code value}.
   *
   * @param m
   *          The matrix
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @return {@code m}
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
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m
   *          The input matrix
   * @return {@code m}
   */

  public static MatrixM3x3D setIdentity(
    final MatrixM3x3D m)
  {
    m.view.clear();

    for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM3x3D.VIEW_COLS; ++col) {
        if (row == col) {
          m.setUnsafe(row, col, (double) 1.0);
        } else {
          m.setUnsafe(row, col, (double) 0.0);
        }
      }
    }

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
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m
   *          The input matrix
   * @return {@code m}
   */

  public static MatrixM3x3D setZero(
    final MatrixM3x3D m)
  {
    m.view.clear();

    for (int index = 0; index < (MatrixM3x3D.VIEW_ROWS * MatrixM3x3D.VIEW_COLS); ++index) {
      m.view.put(index, 0.0);
    }
    return m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as
   * the sum of the diagonal elements of the matrix.
   *
   * @since 5.0.0
   * @param m
   *          The input matrix
   * @return The trace of the matrix
   */

  public static double trace(
    final MatrixReadable3x3DType m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2);
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix
   * to {@code out}.
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static MatrixM3x3D transpose(
    final MatrixReadable3x3DType m,
    final MatrixM3x3D out)
  {
    MatrixM3x3D.copy(m, out);
    return MatrixM3x3D.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix
   * to {@code m}.
   *
   * @param m
   *          The input matrix.
   * @return {@code m}
   */

  public static MatrixM3x3D transposeInPlace(
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
    this.view.clear();

    for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM3x3D.VIEW_COLS; ++col) {
        this.setUnsafe(row, col, source.getRowColumnD(row, col));
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
    final MatrixM3x3D other = (MatrixM3x3D) obj;

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
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

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    MatrixM3x3D.rowUnsafe(this, MatrixM3x3D.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3D.indexChecked(row, column));
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

  /**
   * Set the value at the given row and column.
   *
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @return {@code this}
   */

  public MatrixM3x3D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
    return this;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
  }

  MatrixM3x3D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(MatrixM3x3D.indexUnsafe(row, 0));
      final double c1 = this.view.get(MatrixM3x3D.indexUnsafe(row, 1));
      final double c2 = this.view.get(MatrixM3x3D.indexUnsafe(row, 2));
      final String s = String.format("[%+.15f %+.15f %+.15f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
