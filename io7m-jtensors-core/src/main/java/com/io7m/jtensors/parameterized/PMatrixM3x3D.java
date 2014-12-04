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

package com.io7m.jtensors.parameterized;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixReadable3x3DType;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable3DType;

/**
 * <p>
 * A 3x3 mutable matrix type with double precision elements.
 * </p>
 *
 * @since 7.0.0
 *
 * @param <T0>
 *          A phantom type parameter.
 * @param <T1>
 *          A phantom type parameter.
 */

@SuppressWarnings("unchecked") public final class PMatrixM3x3D<T0, T1> implements
  PMatrixDirectReadable3x3DType<T0, T1>,
  PMatrixWritable3x3DType<T0, T1>
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
   *
   */

  public static class Context
  {
    private final PMatrixM3x3D<?, ?> m3a = new PMatrixM3x3D<Object, Object>();
    private final VectorM3D          v3a = new VectorM3D();
    private final VectorM3D          v3b = new VectorM3D();
    private final VectorM3D          v3c = new VectorM3D();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final PMatrixM3x3D<?, ?> getM3A()
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
    VIEW_ELEMENTS = PMatrixM3x3D.VIEW_ROWS * PMatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = PMatrixM3x3D.VIEW_ELEMENTS * PMatrixM3x3D.VIEW_ELEMENT_SIZE;
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   * @param <T4>
   *          A phantom type parameter.
   * @param <T5>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3, T4, T5> PMatrixM3x3D<T4, T5> add(
    final PMatrixReadable3x3DType<T0, T1> m0,
    final PMatrixReadable3x3DType<T2, T3> m1,
    final PMatrixM3x3D<T4, T5> out)
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
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return <code>m0</code>
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   * @param <T4>
   *          A phantom type parameter.
   * @param <T5>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3, T4, T5> PMatrixM3x3D<T4, T5> addInPlace(
    final PMatrixM3x3D<T0, T1> m0,
    final PMatrixM3x3D<T2, T3> m1)
  {
    return (PMatrixM3x3D<T4, T5>) PMatrixM3x3D.add(m0, m1, m0);
  }

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> addRowScaled(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final PMatrixM3x3D<T2, T3> out)
  {
    return PMatrixM3x3D.addRowScaledUnsafe(
      m,
      PMatrixM3x3D.rowCheck(row_a),
      PMatrixM3x3D.rowCheck(row_b),
      PMatrixM3x3D.rowCheck(row_c),
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
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> addRowScaledInPlace(
    final PMatrixM3x3D<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return (PMatrixM3x3D<T2, T3>) PMatrixM3x3D.addRowScaled(
      m,
      row_a,
      row_b,
      row_c,
      r,
      m);
  }

  private static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> addRowScaledUnsafe(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final PMatrixM3x3D<T2, T3> out)
  {
    final VectorM3D va = new VectorM3D();
    final VectorM3D vb = new VectorM3D();
    PMatrixM3x3D.rowUnsafe(m, row_a, va);
    PMatrixM3x3D.rowUnsafe(m, row_b, vb);

    VectorM3D.addScaledInPlace(va, vb, r);
    PMatrixM3x3D.setRowUnsafe(out, row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM3x3D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + PMatrixM3x3D.VIEW_COLS);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM3x3D<T0, T1> copy(
    final MatrixReadable3x3DType input,
    final PMatrixM3x3D<T0, T1> output)
  {
    for (int col = 0; col < PMatrixM3x3D.VIEW_COLS; ++col) {
      for (int row = 0; row < PMatrixM3x3D.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnD(row, col));
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> double determinant(
    final PMatrixReadable3x3DType<T0, T1> m)
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
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> exchangeRows(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final PMatrixM3x3D<T2, T3> out)
  {
    return PMatrixM3x3D.exchangeRowsUnsafe(
      m,
      PMatrixM3x3D.rowCheck(row_a),
      PMatrixM3x3D.rowCheck(row_b),
      out);
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> exchangeRowsInPlace(
    final PMatrixM3x3D<T0, T1> m,
    final int row_a,
    final int row_b)
  {
    return (PMatrixM3x3D<T2, T3>) PMatrixM3x3D.exchangeRows(
      m,
      row_a,
      row_b,
      m);
  }

  private static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> exchangeRowsUnsafe(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final PMatrixM3x3D<T2, T3> out)
  {
    final VectorM3D va = new VectorM3D();
    final VectorM3D vb = new VectorM3D();

    PMatrixM3x3D.rowUnsafe(m, row_a, va);
    PMatrixM3x3D.rowUnsafe(m, row_b, vb);

    PMatrixM3x3D.setRowUnsafe(out, row_a, vb);
    PMatrixM3x3D.setRowUnsafe(out, row_b, va);
    return out;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM3x3D.indexUnsafe(
      PMatrixM3x3D.rowCheck(row),
      PMatrixM3x3D.columnCheck(column));
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
   * @see PMatrixM3x3D#determinant(PMatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> OptionType<PMatrixM3x3D<T1, T0>> invert(
    final PMatrixReadable3x3DType<T0, T1> m,
    final PMatrixM3x3D<T1, T0> out)
  {
    final double d = PMatrixM3x3D.determinant(m);

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

    PMatrixM3x3D.set(out, 0, 0, r0c0);
    PMatrixM3x3D.set(out, 0, 1, r0c1);
    PMatrixM3x3D.set(out, 0, 2, r0c2);

    PMatrixM3x3D.set(out, 1, 0, r1c0);
    PMatrixM3x3D.set(out, 1, 1, r1c1);
    PMatrixM3x3D.set(out, 1, 2, r1c2);

    PMatrixM3x3D.set(out, 2, 0, r2c0);
    PMatrixM3x3D.set(out, 2, 1, r2c1);
    PMatrixM3x3D.set(out, 2, 2, r2c2);

    PMatrixM3x3D.scaleInPlace(out, d_inv);
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
   * @see PMatrixM3x3D#determinant(PMatrixReadable3x3DType)
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> OptionType<PMatrixM3x3D<T1, T0>> invertInPlace(
    final PMatrixM3x3D<T0, T1> m)
  {
    final PMatrixM3x3D<T0, T1> mf = m;
    final PMatrixM3x3D<T1, T0> mt = (PMatrixM3x3D<T1, T0>) m;
    return PMatrixM3x3D.invert(mf, mt);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> void lookAtWithContext(
    final Context context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final PMatrixM3x3D<T0, T1> out_matrix,
    final VectorM3D out_translation)
  {
    final VectorM3D forward = context.getV3A();
    final VectorM3D new_up = context.getV3B();
    final VectorM3D side = context.getV3C();

    PMatrixM3x3D.setIdentity(out_matrix);

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
   *
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @return A rotation matrix.
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM3x3D<T0, T1> makeRotation(
    final double angle,
    final VectorReadable3DType axis)
  {
    final PMatrixM3x3D<T0, T1> out = new PMatrixM3x3D<T0, T1>();
    PMatrixM3x3D.makeRotationInto(angle, axis, out);
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
   *
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM3x3D<T0, T1> makeRotationInto(
    final double angle,
    final VectorReadable3DType axis,
    final PMatrixM3x3D<T0, T1> out)
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM3x3D<T0, T1> makeTranslation2D(
    final VectorReadable2DType v,
    final PMatrixM3x3D<T0, T1> out)
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM3x3D<T0, T1> makeTranslation2I(
    final VectorReadable2IType v,
    final PMatrixM3x3D<T0, T1> out)
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2> PMatrixM3x3D<T0, T2> multiply(
    final PMatrixReadable3x3DType<T1, T2> m0,
    final PMatrixReadable3x3DType<T0, T1> m1,
    final PMatrixM3x3D<T0, T2> out)
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <V>
   *          The precise type of writable vector.
   */

  public static
    <T0, T1, V extends PVectorWritable3DType<T1>>
    V
    multiplyVector3D(
      final PMatrixReadable3x3DType<T0, T1> m,
      final PVectorReadable3DType<T0> v,
      final V out)
  {
    final VectorM3D row = new VectorM3D();
    final VectorM3D vi = new VectorM3D(v);

    PMatrixM3x3D.rowUnsafe(m, 0, row);
    out.setXD(VectorM3D.dotProduct(row, vi));
    PMatrixM3x3D.rowUnsafe(m, 1, row);
    out.setYD(VectorM3D.dotProduct(row, vi));
    PMatrixM3x3D.rowUnsafe(m, 2, row);
    out.setZD(VectorM3D.dotProduct(row, vi));

    return out;
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <T0, T1, V extends VectorWritable3DType> V row(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row,
    final V out)
  {
    return PMatrixM3x3D.rowUnsafe(m, PMatrixM3x3D.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM3x3D.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM3x3D.VIEW_ROWS);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> scale(
    final PMatrixReadable3x3DType<T0, T1> m,
    final double r,
    final PMatrixM3x3D<T2, T3> out)
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
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> scaleInPlace(
    final PMatrixM3x3D<T0, T1> m,
    final double r)
  {
    return (PMatrixM3x3D<T2, T3>) PMatrixM3x3D.scale(m, r, m);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 &lt;= row &lt; 3).
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> scaleRow(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM3x3D<T2, T3> out)
  {
    return PMatrixM3x3D.scaleRowUnsafe(m, PMatrixM3x3D.rowCheck(row), r, out);
  }

  /**
   * <p>
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 &lt;= row &lt; 3).
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> scaleRowInPlace(
    final PMatrixM3x3D<T0, T1> m,
    final int row,
    final double r)
  {
    return (PMatrixM3x3D<T2, T3>) PMatrixM3x3D.scaleRowUnsafe(
      m,
      PMatrixM3x3D.rowCheck(row),
      r,
      m);
  }

  private static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> scaleRowUnsafe(
    final PMatrixReadable3x3DType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM3x3D<T2, T3> out)
  {
    final VectorM3D v = new VectorM3D();

    PMatrixM3x3D.rowUnsafe(m, row, v);
    VectorM3D.scaleInPlace(v, r);

    PMatrixM3x3D.setRowUnsafe(out, row, v);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> set(
    final PMatrixM3x3D<T0, T1> m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(PMatrixM3x3D.indexChecked(row, column), value);
    return (PMatrixM3x3D<T2, T3>) m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   *
   * @param m
   *          The input matrix
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> setIdentity(
    final PMatrixM3x3D<T0, T1> m)
  {
    m.view.clear();

    for (int row = 0; row < PMatrixM3x3D.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM3x3D.VIEW_COLS; ++col) {
        if (row == col) {
          m.setUnsafe(row, col, 1.0f);
        } else {
          m.setUnsafe(row, col, 0.0f);
        }
      }
    }

    return (PMatrixM3x3D<T2, T3>) m;
  }

  private static <T0, T1> void setRowUnsafe(
    final PMatrixM3x3D<T0, T1> m,
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> setZero(
    final PMatrixM3x3D<T0, T1> m)
  {
    m.view.clear();
    for (int index = 0; index < (PMatrixM3x3D.VIEW_ROWS * PMatrixM3x3D.VIEW_COLS); ++index) {
      m.view.put(index, 0.0);
    }
    return (PMatrixM3x3D<T2, T3>) m;
  }

  /**
   * Return the trace of the matrix <code>m</code>. The trace is defined as
   * the sum of the diagonal elements of the matrix.
   *
   * @param m
   *          The input matrix
   * @return The trace of the matrix
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> double trace(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> transpose(
    final PMatrixReadable3x3DType<T0, T1> m,
    final PMatrixM3x3D<T2, T3> out)
  {
    PMatrixM3x3D.copy(m, out);
    return PMatrixM3x3D.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM3x3D<T2, T3> transposeInPlace(
    final PMatrixM3x3D<T0, T1> m)
  {
    for (int row = 0; row < (PMatrixM3x3D.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < PMatrixM3x3D.VIEW_COLS; ++column) {
        final double x = m.view.get((row * PMatrixM3x3D.VIEW_ROWS) + column);
        m.view.put(
          (row * PMatrixM3x3D.VIEW_ROWS) + column,
          m.view.get(row + (PMatrixM3x3D.VIEW_COLS * column)));
        m.view.put(row + (PMatrixM3x3D.VIEW_COLS * column), x);
      }
    }

    return (PMatrixM3x3D<T2, T3>) m;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM3x3D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;
    this.view = v;
    this.view.clear();

    PMatrixM3x3D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source
   *          The source matrix.
   */

  public PMatrixM3x3D(
    final PMatrixReadable3x3DType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    for (int row = 0; row < PMatrixM3x3D.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM3x3D.VIEW_COLS; ++col) {
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

    final PMatrixM3x3D<?, ?> other = (PMatrixM3x3D<?, ?>) obj;
    for (int index = 0; index < PMatrixM3x3D.VIEW_ELEMENTS; ++index) {
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
    PMatrixM3x3D.rowUnsafe(this, PMatrixM3x3D.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM3x3D.indexChecked(row, column));
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < PMatrixM3x3D.VIEW_ELEMENTS; ++index) {
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

  public PMatrixM3x3D<T0, T1> set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM3x3D.indexChecked(row, column), value);
    return this;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM3x3D.indexChecked(row, column), value);
  }

  PMatrixM3x3D<T0, T1> setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < PMatrixM3x3D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 0));
      final double c1 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 1));
      final double c2 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 2));
      final String s = String.format("[%+.15f %+.15f %+.15f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
