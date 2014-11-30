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
 * A 3x3 mutable matrix type with single precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM3x3F</code> are backed by direct memory, with
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

public final class MatrixM3x3F implements MatrixDirectReadable3x3FType
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM3x3F</code> class.
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
    private final MatrixM3x3F m4a = new MatrixM3x3F();
    private final VectorM3F   v3a = new VectorM3F();
    private final VectorM3F   v3b = new VectorM3F();
    private final VectorM3F   v3c = new VectorM3F();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final MatrixM3x3F getM4A()
    {
      return this.m4a;
    }

    final VectorM3F getV3A()
    {
      return this.v3a;
    }

    final VectorM3F getV3B()
    {
      return this.v3b;
    }

    final VectorM3F getV3C()
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
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixM3x3F.VIEW_ROWS * MatrixM3x3F.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3F.VIEW_ELEMENTS * MatrixM3x3F.VIEW_ELEMENT_SIZE;
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

  public static MatrixM3x3F add(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1,
    final MatrixM3x3F out)
  {
    final float r0c0 = m0.getRowColumnF(0, 0) + m1.getRowColumnF(0, 0);
    final float r1c0 = m0.getRowColumnF(1, 0) + m1.getRowColumnF(1, 0);
    final float r2c0 = m0.getRowColumnF(2, 0) + m1.getRowColumnF(2, 0);

    final float r0c1 = m0.getRowColumnF(0, 1) + m1.getRowColumnF(0, 1);
    final float r1c1 = m0.getRowColumnF(1, 1) + m1.getRowColumnF(1, 1);
    final float r2c1 = m0.getRowColumnF(2, 1) + m1.getRowColumnF(2, 1);

    final float r0c2 = m0.getRowColumnF(0, 2) + m1.getRowColumnF(0, 2);
    final float r1c2 = m0.getRowColumnF(1, 2) + m1.getRowColumnF(1, 2);
    final float r2c2 = m0.getRowColumnF(2, 2) + m1.getRowColumnF(2, 2);

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
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   */

  public static MatrixM3x3F addInPlace(
    final MatrixM3x3F m0,
    final MatrixM3x3F m1)
  {
    return MatrixM3x3F.add(m0, m1, m0);
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

  public static MatrixM3x3F addRowScaled(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.addRowScaledUnsafe(
      m,
      MatrixM3x3F.rowCheck(row_a),
      MatrixM3x3F.rowCheck(row_b),
      MatrixM3x3F.rowCheck(row_c),
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

  public static MatrixM3x3F addRowScaledInPlace(
    final MatrixM3x3F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3F.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM3x3F addRowScaledUnsafe(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM3x3F out)
  {
    final VectorM3F va = new VectorM3F();
    final VectorM3F vb = new VectorM3F();
    MatrixM3x3F.rowUnsafe(m, row_a, va);
    MatrixM3x3F.rowUnsafe(m, row_b, vb);

    VectorM3F.addScaledInPlace(va, vb, r);
    MatrixM3x3F.setRowUnsafe(out, row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM3x3F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM3x3F.VIEW_COLS);
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

  public static MatrixM3x3F copy(
    final MatrixReadable3x3FType input,
    final MatrixM3x3F output)
  {
    for (int col = 0; col < MatrixM3x3F.VIEW_COLS; ++col) {
      for (int row = 0; row < MatrixM3x3F.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnF(row, col));
      }
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   *
   * @return The determinant.
   * @param m
   *          The input matrix.
   */

  public static double determinant(
    final MatrixReadable3x3FType m)
  {
    final double r0c0 = m.getRowColumnF(0, 0);
    final double r0c1 = m.getRowColumnF(0, 1);
    final double r0c2 = m.getRowColumnF(0, 2);

    final double r1c0 = m.getRowColumnF(1, 0);
    final double r1c1 = m.getRowColumnF(1, 1);
    final double r1c2 = m.getRowColumnF(1, 2);

    final double r2c0 = m.getRowColumnF(2, 0);
    final double r2c1 = m.getRowColumnF(2, 1);
    final double r2c2 = m.getRowColumnF(2, 2);

    double sum = 0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
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

  public static MatrixM3x3F exchangeRows(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.exchangeRowsUnsafe(
      m,
      MatrixM3x3F.rowCheck(row_a),
      MatrixM3x3F.rowCheck(row_b),
      out);
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code> .
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

  public static MatrixM3x3F exchangeRowsInPlace(
    final MatrixM3x3F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3F.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM3x3F exchangeRowsUnsafe(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final MatrixM3x3F out)
  {
    final VectorM3F va = new VectorM3F();
    final VectorM3F vb = new VectorM3F();

    MatrixM3x3F.rowUnsafe(m, row_a, va);
    MatrixM3x3F.rowUnsafe(m, row_b, vb);

    MatrixM3x3F.setRowUnsafe(out, row_a, vb);
    MatrixM3x3F.setRowUnsafe(out, row_b, va);
    return out;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3F.indexUnsafe(
      MatrixM3x3F.rowCheck(row),
      MatrixM3x3F.columnCheck(column));
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
   * of <code>0</code>.
   *
   * @see MatrixM3x3F#determinant(MatrixReadable3x3FType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static OptionType<MatrixM3x3F> invert(
    final MatrixReadable3x3FType m,
    final MatrixM3x3F out)
  {
    final double d = MatrixM3x3F.determinant(m);

    if (d == 0.0) {
      return Option.none();
    }

    final double d_inv = 1 / d;

    final double orig_r0c0 = m.getRowColumnF(0, 0);
    final double orig_r0c1 = m.getRowColumnF(0, 1);
    final double orig_r0c2 = m.getRowColumnF(0, 2);

    final double orig_r1c0 = m.getRowColumnF(1, 0);
    final double orig_r1c1 = m.getRowColumnF(1, 1);
    final double orig_r1c2 = m.getRowColumnF(1, 2);

    final double orig_r2c0 = m.getRowColumnF(2, 0);
    final double orig_r2c1 = m.getRowColumnF(2, 1);
    final double orig_r2c2 = m.getRowColumnF(2, 2);

    final double r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final double r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final double r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final double r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final double r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final double r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final double r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final double r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final double r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    MatrixM3x3F.set(out, 0, 0, (float) r0c0);
    MatrixM3x3F.set(out, 0, 1, (float) r0c1);
    MatrixM3x3F.set(out, 0, 2, (float) r0c2);

    MatrixM3x3F.set(out, 1, 0, (float) r1c0);
    MatrixM3x3F.set(out, 1, 1, (float) r1c1);
    MatrixM3x3F.set(out, 1, 2, (float) r1c2);

    MatrixM3x3F.set(out, 2, 0, (float) r2c0);
    MatrixM3x3F.set(out, 2, 1, (float) r2c1);
    MatrixM3x3F.set(out, 2, 2, (float) r2c2);

    MatrixM3x3F.scaleInPlace(out, d_inv);
    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   *
   * @see MatrixM3x3F#determinant(MatrixReadable3x3FType)
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static OptionType<MatrixM3x3F> invertInPlace(
    final MatrixM3x3F m)
  {
    return MatrixM3x3F.invert(m, m);
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

  public static void lookAtWithContext(
    final Context context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final MatrixM3x3F out_matrix,
    final VectorM3F out_translation)
  {
    final VectorM3F forward = context.getV3A();
    final VectorM3F new_up = context.getV3B();
    final VectorM3F side = context.getV3C();

    MatrixM3x3F.setIdentity(out_matrix);

    /**
     * Calculate "forward" vector
     */

    forward.set3F(
      target.getXF() - origin.getXF(),
      target.getYF() - origin.getYF(),
      target.getZF() - origin.getZF());
    VectorM3F.normalizeInPlace(forward);

    /**
     * Calculate "side" vector
     */

    VectorM3F.crossProduct(forward, up, side);
    VectorM3F.normalizeInPlace(side);

    /**
     * Calculate new "up" vector
     */

    VectorM3F.crossProduct(side, forward, new_up);

    /**
     * Calculate rotation matrix
     */

    out_matrix.set(0, 0, side.getXF());
    out_matrix.set(0, 1, side.getYF());
    out_matrix.set(0, 2, side.getZF());
    out_matrix.set(1, 0, new_up.getXF());
    out_matrix.set(1, 1, new_up.getYF());
    out_matrix.set(1, 2, new_up.getZF());
    out_matrix.set(2, 0, -forward.getXF());
    out_matrix.set(2, 1, -forward.getYF());
    out_matrix.set(2, 2, -forward.getZF());

    /**
     * Calculate camera translation matrix
     */

    out_translation.set3F(-origin.getXF(), -origin.getYF(), -origin.getZF());
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
   * @return <code>out</code>
   */

  public static MatrixM3x3F makeRotation(
    final double angle,
    final VectorReadable3FType axis)
  {
    final MatrixM3x3F out = new MatrixM3x3F();
    MatrixM3x3F.makeRotation(angle, axis, out);
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

  public static MatrixM3x3F makeRotation(
    final double angle,
    final VectorReadable3FType axis,
    final MatrixM3x3F out)
  {
    final double axis_x = axis.getXF();
    final double axis_y = axis.getYF();
    final double axis_z = axis.getZF();

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

    out.setUnsafe(0, 0, (float) r0c0);
    out.setUnsafe(0, 1, (float) r0c1);
    out.setUnsafe(0, 2, (float) r0c2);

    out.setUnsafe(1, 0, (float) r1c0);
    out.setUnsafe(1, 1, (float) r1c1);
    out.setUnsafe(1, 2, (float) r1c2);

    out.setUnsafe(2, 0, (float) r2c0);
    out.setUnsafe(2, 1, (float) r2c1);
    out.setUnsafe(2, 2, (float) r2c2);
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

  public static MatrixM3x3F makeTranslation2F(
    final VectorReadable2FType v,
    final MatrixM3x3F out)
  {
    out.setUnsafe(0, 2, v.getXF());
    out.setUnsafe(1, 2, v.getYF());
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

  public static MatrixM3x3F makeTranslation2I(
    final VectorReadable2IType v,
    final MatrixM3x3F out)
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

  public static MatrixM3x3F multiply(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1,
    final MatrixM3x3F out)
  {
    double r0c0 = 0;
    r0c0 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 0);
    r0c0 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 0);
    r0c0 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 0);

    double r1c0 = 0;
    r1c0 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 0);
    r1c0 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 0);
    r1c0 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 0);

    double r2c0 = 0;
    r2c0 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 0);
    r2c0 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 0);
    r2c0 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 0);

    double r0c1 = 0;
    r0c1 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 1);
    r0c1 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 1);
    r0c1 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 1);

    double r1c1 = 0;
    r1c1 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 1);
    r1c1 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 1);
    r1c1 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 1);

    double r2c1 = 0;
    r2c1 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 1);
    r2c1 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 1);
    r2c1 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 1);

    double r0c2 = 0;
    r0c2 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 2);
    r0c2 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 2);
    r0c2 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 2);

    double r1c2 = 0;
    r1c2 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 2);
    r1c2 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 2);
    r1c2 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 2);

    double r2c2 = 0;
    r2c2 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 2);
    r2c2 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 2);
    r2c2 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 2);

    out.setUnsafe(0, 0, (float) r0c0);
    out.setUnsafe(0, 1, (float) r0c1);
    out.setUnsafe(0, 2, (float) r0c2);

    out.setUnsafe(1, 0, (float) r1c0);
    out.setUnsafe(1, 1, (float) r1c1);
    out.setUnsafe(1, 2, (float) r1c2);

    out.setUnsafe(2, 0, (float) r2c0);
    out.setUnsafe(2, 1, (float) r2c1);
    out.setUnsafe(2, 2, (float) r2c2);
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

  public static MatrixM3x3F multiplyInPlace(
    final MatrixM3x3F m0,
    final MatrixReadable3x3FType m1)
  {
    return MatrixM3x3F.multiply(m0, m1, m0);
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

  public static VectorM3F multiplyVector3F(
    final MatrixReadable3x3FType m,
    final VectorReadable3FType v,
    final VectorM3F out)
  {
    final VectorM3F row = new VectorM3F();
    final VectorM3F vi = new VectorM3F(v);

    MatrixM3x3F.rowUnsafe(m, 0, row);
    out.setXF((float) VectorM3F.dotProduct(row, vi));
    MatrixM3x3F.rowUnsafe(m, 1, row);
    out.setYF((float) VectorM3F.dotProduct(row, vi));
    MatrixM3x3F.rowUnsafe(m, 2, row);
    out.setZF((float) VectorM3F.dotProduct(row, vi));

    return out;
  }

  private static MatrixM3x3F rotate(
    final double angle,
    final MatrixReadable3x3FType m,
    final MatrixM3x3F tmp,
    final VectorReadable3FType axis,
    final MatrixM3x3F out)
  {
    MatrixM3x3F.makeRotation(angle, axis, tmp);
    MatrixM3x3F.multiply(m, tmp, out);
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

  public static MatrixM3x3F rotate(
    final double angle,
    final MatrixReadable3x3FType m,
    final VectorReadable3FType axis,
    final MatrixM3x3F out)
  {
    final MatrixM3x3F tmp = new MatrixM3x3F();
    return MatrixM3x3F.rotate(angle, m, tmp, axis, out);
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

  public static MatrixM3x3F rotateInPlace(
    final double angle,
    final MatrixM3x3F m,
    final VectorReadable3FType axis)
  {
    final MatrixM3x3F tmp = new MatrixM3x3F();
    return MatrixM3x3F.rotate(angle, m, tmp, axis, m);
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

  public static MatrixM3x3F rotateInPlaceWithContext(
    final Context context,
    final double angle,
    final MatrixM3x3F m,
    final VectorReadable3FType axis)
  {
    return MatrixM3x3F.rotate(angle, m, context.getM4A(), axis, m);
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

  public static MatrixM3x3F rotateWithContext(
    final Context context,
    final double angle,
    final MatrixReadable3x3FType m,
    final VectorReadable3FType axis,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.rotate(angle, m, context.getM4A(), axis, out);
  }

  /**
   * @return Row <code>row</code> of the matrix <code>m</code> in the vector
   *         <code>out</code>.
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param out
   *          The output vector
   */

  public static VectorM3F row(
    final MatrixReadable3x3FType m,
    final int row,
    final VectorM3F out)
  {
    return MatrixM3x3F.rowUnsafe(m, MatrixM3x3F.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM3x3F.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM3x3F.VIEW_ROWS);
    }
    return row;
  }

  private static VectorM3F rowUnsafe(
    final MatrixReadable3x3FType m,
    final int row,
    final VectorM3F out)
  {
    out.set3F(
      m.getRowColumnF(row, 0),
      m.getRowColumnF(row, 1),
      m.getRowColumnF(row, 2));
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
   *          The output matrix
   * @return <code>out</code>
   */

  public static MatrixM3x3F scale(
    final MatrixReadable3x3FType m,
    final double r,
    final MatrixM3x3F out)
  {
    final float r0c0 = (float) (m.getRowColumnF(0, 0) * r);
    final float r1c0 = (float) (m.getRowColumnF(1, 0) * r);
    final float r2c0 = (float) (m.getRowColumnF(2, 0) * r);

    final float r0c1 = (float) (m.getRowColumnF(0, 1) * r);
    final float r1c1 = (float) (m.getRowColumnF(1, 1) * r);
    final float r2c1 = (float) (m.getRowColumnF(2, 1) * r);

    final float r0c2 = (float) (m.getRowColumnF(0, 2) * r);
    final float r1c2 = (float) (m.getRowColumnF(1, 2) * r);
    final float r2c2 = (float) (m.getRowColumnF(2, 2) * r);

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
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM3x3F scaleInPlace(
    final MatrixM3x3F m,
    final double r)
  {
    return MatrixM3x3F.scale(m, r, m);
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

  public static MatrixM3x3F scaleRow(
    final MatrixReadable3x3FType m,
    final int row,
    final double r,
    final MatrixM3x3F out)
  {
    return MatrixM3x3F.scaleRowUnsafe(m, MatrixM3x3F.rowCheck(row), r, out);
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

  public static MatrixM3x3F scaleRowInPlace(
    final MatrixM3x3F m,
    final int row,
    final double r)
  {
    return MatrixM3x3F.scaleRowUnsafe(m, MatrixM3x3F.rowCheck(row), r, m);
  }

  private static MatrixM3x3F scaleRowUnsafe(
    final MatrixReadable3x3FType m,
    final int row,
    final double r,
    final MatrixM3x3F out)
  {
    final VectorM3F v = new VectorM3F();

    MatrixM3x3F.rowUnsafe(m, row, v);
    VectorM3F.scaleInPlace(v, r);

    MatrixM3x3F.setRowUnsafe(out, row, v);
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   *
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @return <code>m</code>
   */

  public static MatrixM3x3F set(
    final MatrixM3x3F m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM3x3F.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   *
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM3x3F setIdentity(
    final MatrixM3x3F m)
  {
    m.view.clear();

    for (int row = 0; row < MatrixM3x3F.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM3x3F.VIEW_COLS; ++col) {
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
    final MatrixM3x3F m,
    final int row,
    final VectorReadable3FType v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.setUnsafe(row, 2, v.getZF());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   *
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM3x3F setZero(
    final MatrixM3x3F m)
  {
    m.view.clear();
    for (int index = 0; index < (MatrixM3x3F.VIEW_ROWS * MatrixM3x3F.VIEW_COLS); ++index) {
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

  public static double trace(
    final MatrixReadable3x3FType m)
  {
    return m.getRowColumnF(0, 0)
      + m.getRowColumnF(1, 1)
      + m.getRowColumnF(2, 2);
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

  public static MatrixM3x3F translateByVector2F(
    final MatrixReadable3x3FType m,
    final VectorReadable2FType v,
    final MatrixM3x3F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();

    final float c2r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c2r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c2r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);

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

  public static MatrixM3x3F translateByVector2FInPlace(
    final MatrixM3x3F m,
    final VectorReadable2FType v)
  {
    return MatrixM3x3F.translateByVector2F(m, v, m);
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

  public static MatrixM3x3F translateByVector2I(
    final MatrixReadable3x3FType m,
    final VectorReadable2IType v,
    final MatrixM3x3F out)
  {
    final float vx = v.getXI();
    final float vy = v.getYI();

    final float c2r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c2r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c2r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);

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

  public static MatrixM3x3F translateByVector2IInPlace(
    final MatrixM3x3F m,
    final VectorReadable2IType v)
  {
    return MatrixM3x3F.translateByVector2I(m, v, m);
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

  public static MatrixM3x3F transpose(
    final MatrixReadable3x3FType m,
    final MatrixM3x3F out)
  {
    MatrixM3x3F.copy(m, out);
    return MatrixM3x3F.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static MatrixM3x3F transposeInPlace(
    final MatrixM3x3F m)
  {
    for (int row = 0; row < (MatrixM3x3F.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < MatrixM3x3F.VIEW_COLS; ++column) {
        final float x = m.view.get((row * MatrixM3x3F.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM3x3F.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM3x3F.VIEW_COLS * column)));
        m.view.put(row + (MatrixM3x3F.VIEW_COLS * column), x);
      }
    }

    return m;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM3x3F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    final FloatBuffer v = b.asFloatBuffer();
    assert v != null;

    this.data = b;
    this.view = v;
    MatrixM3x3F.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source
   *          The source matrix.
   */

  public MatrixM3x3F(
    final MatrixReadable3x3FType source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.rewind();

    for (int row = 0; row < MatrixM3x3F.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM3x3F.VIEW_COLS; ++col) {
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
    final MatrixM3x3F other = (MatrixM3x3F) obj;

    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
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

  @Override public void getRow3F(
    final int row,
    final VectorM3F out)
  {
    MatrixM3x3F.rowUnsafe(this, MatrixM3x3F.rowCheck(row), out);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3F.indexChecked(row, column));
  }

  private float getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3F.indexUnsafe(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM3x3F.VIEW_ELEMENTS; ++index) {
      result += Float.valueOf(this.view.get(index)).hashCode();
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

  public MatrixM3x3F set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM3x3F.indexChecked(row, column), value);
    return this;
  }

  MatrixM3x3F setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM3x3F.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM3x3F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(MatrixM3x3F.indexUnsafe(row, 0));
      final float c1 = this.view.get(MatrixM3x3F.indexUnsafe(row, 1));
      final float c2 = this.view.get(MatrixM3x3F.indexUnsafe(row, 2));
      final String s = String.format("[%+.6f %+.6f %+.6f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
