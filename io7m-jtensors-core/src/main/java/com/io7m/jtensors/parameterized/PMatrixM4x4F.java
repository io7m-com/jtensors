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

import com.io7m.jfunctional.Option;
import com.io7m.jfunctional.OptionType;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable4FType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <p>
 * A 4x4 mutable matrix type with single precision elements.
 * </p>
 *
 * @since 7.0.0
 *
 * @param <T0>
 *          A phantom type parameter.
 * @param <T1>
 *          A phantom type parameter.
 */

@SuppressWarnings("unchecked") public final class PMatrixM4x4F<T0, T1> implements
  PMatrixDirectReadable4x4FType<T0, T1>,
  PMatrixWritable4x4FType<T0, T1>
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the {@code MatrixM4x4F} class.
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
   */

  public static class Context
  {
    private final MatrixM3x3F        m3a = new MatrixM3x3F();
    private final PMatrixM4x4F<?, ?> m4a = new PMatrixM4x4F<Object, Object>();
    private final PMatrixM4x4F<?, ?> m4b = new PMatrixM4x4F<Object, Object>();
    private final VectorM3F          v3a = new VectorM3F();
    private final VectorM3F          v3b = new VectorM3F();
    private final VectorM3F          v3c = new VectorM3F();
    private final VectorM3F          v3d = new VectorM3F();
    private final VectorM4F          v4a = new VectorM4F();
    private final VectorM4F          v4b = new VectorM4F();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final MatrixM3x3F getM3a()
    {
      return this.m3a;
    }

    final PMatrixM4x4F<?, ?> getM4a()
    {
      return this.m4a;
    }

    final PMatrixM4x4F<?, ?> getM4b()
    {
      return this.m4b;
    }

    final VectorM3F getV3a()
    {
      return this.v3a;
    }

    final VectorM3F getV3b()
    {
      return this.v3b;
    }

    final VectorM3F getV3c()
    {
      return this.v3c;
    }

    final VectorM3F getV3d()
    {
      return this.v3d;
    }

    final VectorM4F getV4a()
    {
      return this.v4a;
    }

    final VectorM4F getV4b()
    {
      return this.v4b;
    }
  }

  private interface Phantom2Type
  {
    // Type-level only.
  }

  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;

  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = PMatrixM4x4F.VIEW_ROWS * PMatrixM4x4F.VIEW_COLS;
    VIEW_BYTES = PMatrixM4x4F.VIEW_ELEMENTS * PMatrixM4x4F.VIEW_ELEMENT_SIZE;
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

  public static <T0, T1, T2, T3, T4, T5> PMatrixM4x4F<T4, T5> add(
    final PMatrixReadable4x4FType<T0, T1> m0,
    final PMatrixReadable4x4FType<T2, T3> m1,
    final PMatrixM4x4F<T4, T5> out)
  {
    final float r0c0 = m0.getRowColumnF(0, 0) + m1.getRowColumnF(0, 0);
    final float r1c0 = m0.getRowColumnF(1, 0) + m1.getRowColumnF(1, 0);
    final float r2c0 = m0.getRowColumnF(2, 0) + m1.getRowColumnF(2, 0);
    final float r3c0 = m0.getRowColumnF(3, 0) + m1.getRowColumnF(3, 0);

    final float r0c1 = m0.getRowColumnF(0, 1) + m1.getRowColumnF(0, 1);
    final float r1c1 = m0.getRowColumnF(1, 1) + m1.getRowColumnF(1, 1);
    final float r2c1 = m0.getRowColumnF(2, 1) + m1.getRowColumnF(2, 1);
    final float r3c1 = m0.getRowColumnF(3, 1) + m1.getRowColumnF(3, 1);

    final float r0c2 = m0.getRowColumnF(0, 2) + m1.getRowColumnF(0, 2);
    final float r1c2 = m0.getRowColumnF(1, 2) + m1.getRowColumnF(1, 2);
    final float r2c2 = m0.getRowColumnF(2, 2) + m1.getRowColumnF(2, 2);
    final float r3c2 = m0.getRowColumnF(3, 2) + m1.getRowColumnF(3, 2);

    final float r0c3 = m0.getRowColumnF(0, 3) + m1.getRowColumnF(0, 3);
    final float r1c3 = m0.getRowColumnF(1, 3) + m1.getRowColumnF(1, 3);
    final float r2c3 = m0.getRowColumnF(2, 3) + m1.getRowColumnF(2, 3);
    final float r3c3 = m0.getRowColumnF(3, 3) + m1.getRowColumnF(3, 3);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(3, 0, r3c0);

    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(3, 1, r3c1);

    out.setUnsafe(0, 2, r0c2);
    out.setUnsafe(1, 2, r1c2);
    out.setUnsafe(2, 2, r2c2);
    out.setUnsafe(3, 2, r3c2);

    out.setUnsafe(0, 3, r0c3);
    out.setUnsafe(1, 3, r1c3);
    out.setUnsafe(2, 3, r2c3);
    out.setUnsafe(3, 3, r3c3);

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
   * @return m0
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

  public static <T0, T1, T2, T3, T4, T5> PMatrixM4x4F<T4, T5> addInPlace(
    final PMatrixM4x4F<T0, T1> m0,
    final PMatrixReadable4x4FType<T2, T3> m1)
  {
    return (PMatrixM4x4F<T4, T5>) PMatrixM4x4F.add(m0, m1, m0);
  }

  /**
   * <p>
   * Add the values in row {@code row_b} to the values in row
   * {@code row_a} scaled by {@code r}, saving the resulting row in
   * row {@code row_c} of the matrix {@code out}.
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
   * @return {@code out}
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> addRowScaled(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final PMatrixM4x4F<T2, T3> out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();

    return PMatrixM4x4F.addRowScaledUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row_a),
      PMatrixM4x4F.rowCheck(row_b),
      PMatrixM4x4F.rowCheck(row_c),
      r,
      va,
      vb,
      out);
  }

  /**
   * <p>
   * Add the values in row {@code row_b} to the values in row
   * {@code row_a} scaled by {@code r}, saving the resulting row in
   * row {@code row_c} of the matrix {@code m}.
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
   * @return {@code m}
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> addRowScaledInPlace(
    final PMatrixM4x4F<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.addRowScaled(
      m,
      row_a,
      row_b,
      row_c,
      r,
      m);
  }

  private static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> addRowScaledUnsafe(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM4F va,
    final VectorM4F vb,
    final PMatrixM4x4F<T2, T3> out)
  {
    PMatrixM4x4F.rowUnsafe(m, row_a, va);
    PMatrixM4x4F.rowUnsafe(m, row_b, vb);
    VectorM4F.addScaledInPlace(va, vb, r);
    PMatrixM4x4F.setRowUnsafe(out, row_c, va);
    return out;
  }

  /**
   * <p>
   * Add the values in row {@code row_b} to the values in row
   * {@code row_a} scaled by {@code r}, saving the resulting row in
   * row {@code row_c} of the matrix {@code out}. The function uses
   * storage preallocated in {@code context} to avoid any new
   * allocations.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param context
   *          Preallocated storage.
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
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static
    <T0, T1, T2, T3>
    PMatrixM4x4F<T2, T3>
    addRowScaledWithContext(
      final Context context,
      final PMatrixReadable4x4FType<T0, T1> m,
      final int row_a,
      final int row_b,
      final int row_c,
      final double r,
      final PMatrixM4x4F<T2, T3> out)
  {
    return PMatrixM4x4F.addRowScaledUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row_a),
      PMatrixM4x4F.rowCheck(row_b),
      PMatrixM4x4F.rowCheck(row_c),
      r,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + PMatrixM4x4F.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix
   * {@code output}, completely replacing all elements.
   *
   * @param input
   *          The input matrix.
   * @param output
   *          The output matrix.
   * @return {@code output}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> copy(
    final PMatrixReadable4x4FType<T0, T1> input,
    final PMatrixM4x4F<T0, T1> output)
  {
    for (int col = 0; col < PMatrixM4x4F.VIEW_COLS; ++col) {
      for (int row = 0; row < PMatrixM4x4F.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnF(row, col));
      }
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @return The determinant.
   * @param m
   *          The input matrix.
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> double determinant(
    final PMatrixReadable4x4FType<T0, T1> m)
  {
    final double r0c0 = m.getRowColumnF(0, 0);
    final double r1c0 = m.getRowColumnF(1, 0);
    final double r2c0 = m.getRowColumnF(2, 0);
    final double r3c0 = m.getRowColumnF(3, 0);

    final double r0c1 = m.getRowColumnF(0, 1);
    final double r1c1 = m.getRowColumnF(1, 1);
    final double r2c1 = m.getRowColumnF(2, 1);
    final double r3c1 = m.getRowColumnF(3, 1);

    final double r0c2 = m.getRowColumnF(0, 2);
    final double r1c2 = m.getRowColumnF(1, 2);
    final double r2c2 = m.getRowColumnF(2, 2);
    final double r3c2 = m.getRowColumnF(3, 2);

    final double r0c3 = m.getRowColumnF(0, 3);
    final double r1c3 = m.getRowColumnF(1, 3);
    final double r2c3 = m.getRowColumnF(2, 3);
    final double r3c3 = m.getRowColumnF(3, 3);

    double sum = 0;

    sum += r0c0 * r1c1 * r2c2 * r3c3;
    sum -= r0c0 * r1c1 * r2c3 * r3c2;
    sum += r0c0 * r1c2 * r2c3 * r3c1;
    sum -= r0c0 * r1c2 * r2c1 * r3c3;

    sum += r0c0 * r1c3 * r2c1 * r3c2;
    sum -= r0c0 * r1c3 * r2c2 * r3c1;
    sum -= r0c1 * r1c2 * r2c3 * r3c0;
    sum += r0c1 * r1c2 * r2c0 * r3c3;

    sum -= r0c1 * r1c3 * r2c0 * r3c2;
    sum += r0c1 * r1c3 * r2c2 * r3c0;
    sum -= r0c1 * r1c0 * r2c2 * r3c3;
    sum += r0c1 * r1c0 * r2c3 * r3c2;

    sum += r0c2 * r1c3 * r2c0 * r3c1;
    sum -= r0c2 * r1c3 * r2c1 * r3c0;
    sum += r0c2 * r1c0 * r2c1 * r3c3;
    sum -= r0c2 * r1c0 * r2c3 * r3c1;

    sum += r0c2 * r1c1 * r2c3 * r3c0;
    sum -= r0c2 * r1c1 * r2c0 * r3c3;
    sum -= r0c3 * r1c0 * r2c1 * r3c2;
    sum += r0c3 * r1c0 * r2c2 * r3c1;

    sum -= r0c3 * r1c1 * r2c2 * r3c0;
    sum += r0c3 * r1c1 * r2c0 * r3c2;
    sum -= r0c3 * r1c2 * r2c0 * r3c1;
    sum += r0c3 * r1c2 * r2c1 * r3c0;

    return sum;
  }

  /**
   * <p>
   * Exchange the row {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code out}.
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
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> exchangeRows(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row_a,
    final int row_b,
    final PMatrixM4x4F<T2, T3> out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();
    return PMatrixM4x4F.exchangeRowsUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row_a),
      PMatrixM4x4F.rowCheck(row_b),
      va,
      vb,
      out);
  }

  /**
   * <p>
   * Exchange the row {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code m}.
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
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> exchangeRowsInPlace(
    final PMatrixM4x4F<T0, T1> m,
    final int row_a,
    final int row_b)
  {
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.exchangeRows(
      m,
      row_a,
      row_b,
      m);
  }

  /**
   * <p>
   * Exchange the row {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code m}. The
   * function uses storage preallocated in {@code context} to avoid
   * allocating memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static
    <T0, T1, T2, T3>
    PMatrixM4x4F<T2, T3>
    exchangeRowsInPlaceWithContext(
      final Context context,
      final PMatrixM4x4F<T0, T1> m,
      final int row_a,
      final int row_b)
  {
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.exchangeRowsWithContext(
      context,
      m,
      row_a,
      row_b,
      m);
  }

  private static <T0, T1> PMatrixM4x4F<T0, T1> exchangeRowsUnsafe(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final VectorM4F va,
    final VectorM4F vb,
    final PMatrixM4x4F<T0, T1> out)
  {
    PMatrixM4x4F.rowUnsafe(m, row_a, va);
    PMatrixM4x4F.rowUnsafe(m, row_b, vb);
    PMatrixM4x4F.setRowUnsafe(out, row_a, vb);
    PMatrixM4x4F.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * <p>
   * Exchange two rows {@code row_a} and row {@code row_b} of the
   * matrix {@code m}, saving the exchanged rows to {@code out} .
   * </p>
   * <p>
   * The function uses storage preallocated in {@code context} to avoid
   * allocating memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row_a
   *          The first row.
   * @param row_b
   *          The second row.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static
    <T0, T1, T2, T3>
    PMatrixM4x4F<T2, T3>
    exchangeRowsWithContext(
      final Context context,
      final PMatrixReadable4x4FType<T0, T1> m,
      final int row_a,
      final int row_b,
      final PMatrixM4x4F<T2, T3> out)
  {
    return PMatrixM4x4F.exchangeRowsUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row_a),
      PMatrixM4x4F.rowCheck(row_b),
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM4x4F.indexUnsafe(
      PMatrixM4x4F.rowCheck(row),
      PMatrixM4x4F.columnCheck(column));
  }

  /**
   * <p>
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * </p>
   * <p>
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage.
   * </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * PMatrixM4x4F.VIEW_COLS) + row;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting
   * matrix to {@code out}. The function returns {@code Some(out)}
   * iff it was possible to invert the matrix, and {@code None}
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of {@code 0}. If the function returns {@code None},
   * {@code m} is untouched.
   *
   * @see PMatrixM4x4F#determinant(PMatrixReadable4x4FType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> OptionType<PMatrixM4x4F<T1, T0>> invert(
    final PMatrixReadable4x4FType<T0, T1> m,
    final PMatrixM4x4F<T1, T0> out)
  {
    final MatrixM3x3F m3 = new MatrixM3x3F();
    return PMatrixM4x4F.invertActual(m, m3, out);
  }

  private static <T0, T1> OptionType<PMatrixM4x4F<T1, T0>> invertActual(
    final PMatrixReadable4x4FType<T0, T1> m,
    final MatrixM3x3F m3,
    final PMatrixM4x4F<T1, T0> out)
  {
    final double d = PMatrixM4x4F.determinant(m);

    if (d == 0.0) {
      return Option.none();
    }

    final double d_inv = 1 / d;

    /**
     * This code is based on the Laplace Expansion theorem. Essentially, the
     * inverse of the matrix is calculated by taking the determinants of 3x3
     * sub-matrices of the original matrix. The sub-matrices are created by
     * removing a specific row and column to leave 9 (possibly non-adjacent)
     * cells, which are then placed in a 3x3 matrix.
     *
     * This implementation was derived from the paper "The Laplace Expansion
     * Theorem: Computing the Determinants and Inverses of Matrices" by David
     * Eberly.
     */

    final double r0c0;
    final double r0c1;
    final double r0c2;
    final double r0c3;

    final double r1c0;
    final double r1c1;
    final double r1c2;
    final double r1c3;

    final double r2c0;
    final double r2c1;
    final double r2c2;
    final double r2c3;

    final double r3c0;
    final double r3c1;
    final double r3c2;
    final double r3c3;

    {
      // Sub-matrix obtained by removing m[0, 0]
      // 1 = (-1) ^ (0 + 0)

      m3.set(0, 0, m.getRowColumnF(1, 1));
      m3.set(0, 1, m.getRowColumnF(1, 2));
      m3.set(0, 2, m.getRowColumnF(1, 3));
      m3.set(1, 0, m.getRowColumnF(2, 1));
      m3.set(1, 1, m.getRowColumnF(2, 2));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 1));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r0c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.set(0, 0, m.getRowColumnF(1, 0));
      m3.set(0, 1, m.getRowColumnF(1, 2));
      m3.set(0, 2, m.getRowColumnF(1, 3));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 2));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r0c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.set(0, 0, m.getRowColumnF(1, 0));
      m3.set(0, 1, m.getRowColumnF(1, 1));
      m3.set(0, 2, m.getRowColumnF(1, 3));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 1));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r0c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.set(0, 0, m.getRowColumnF(1, 0));
      m3.set(0, 1, m.getRowColumnF(1, 1));
      m3.set(0, 2, m.getRowColumnF(1, 2));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 1));
      m3.set(1, 2, m.getRowColumnF(2, 2));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 2));

      r0c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.set(0, 0, m.getRowColumnF(0, 1));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(2, 1));
      m3.set(1, 1, m.getRowColumnF(2, 2));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 1));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r1c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 2));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r1c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 1));
      m3.set(1, 2, m.getRowColumnF(2, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r1c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 2));
      m3.set(1, 0, m.getRowColumnF(2, 0));
      m3.set(1, 1, m.getRowColumnF(2, 1));
      m3.set(1, 2, m.getRowColumnF(2, 2));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 2));

      r1c3 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.set(0, 0, m.getRowColumnF(0, 1));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 1));
      m3.set(1, 1, m.getRowColumnF(1, 2));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(3, 1));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r2c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 2));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 2));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r2c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 1));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 3));

      r2c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 2));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 1));
      m3.set(1, 2, m.getRowColumnF(1, 2));
      m3.set(2, 0, m.getRowColumnF(3, 0));
      m3.set(2, 1, m.getRowColumnF(3, 1));
      m3.set(2, 2, m.getRowColumnF(3, 2));

      r2c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.set(0, 0, m.getRowColumnF(0, 1));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 1));
      m3.set(1, 1, m.getRowColumnF(1, 2));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(2, 1));
      m3.set(2, 1, m.getRowColumnF(2, 2));
      m3.set(2, 2, m.getRowColumnF(2, 3));

      r3c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 2));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 2));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(2, 0));
      m3.set(2, 1, m.getRowColumnF(2, 2));
      m3.set(2, 2, m.getRowColumnF(2, 3));

      r3c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 3));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 1));
      m3.set(1, 2, m.getRowColumnF(1, 3));
      m3.set(2, 0, m.getRowColumnF(2, 0));
      m3.set(2, 1, m.getRowColumnF(2, 1));
      m3.set(2, 2, m.getRowColumnF(2, 3));

      r3c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.set(0, 0, m.getRowColumnF(0, 0));
      m3.set(0, 1, m.getRowColumnF(0, 1));
      m3.set(0, 2, m.getRowColumnF(0, 2));
      m3.set(1, 0, m.getRowColumnF(1, 0));
      m3.set(1, 1, m.getRowColumnF(1, 1));
      m3.set(1, 2, m.getRowColumnF(1, 2));
      m3.set(2, 0, m.getRowColumnF(2, 0));
      m3.set(2, 1, m.getRowColumnF(2, 1));
      m3.set(2, 2, m.getRowColumnF(2, 2));

      r3c3 = MatrixM3x3F.determinant(m3);
    }

    /**
     * Divide sub-matrix determinants by the determinant of the original
     * matrix and transpose.
     */

    out.setUnsafe(0, 0, (float) (r0c0 * d_inv));
    out.setUnsafe(0, 1, (float) (r0c1 * d_inv));
    out.setUnsafe(0, 2, (float) (r0c2 * d_inv));
    out.setUnsafe(0, 3, (float) (r0c3 * d_inv));

    out.setUnsafe(1, 0, (float) (r1c0 * d_inv));
    out.setUnsafe(1, 1, (float) (r1c1 * d_inv));
    out.setUnsafe(1, 2, (float) (r1c2 * d_inv));
    out.setUnsafe(1, 3, (float) (r1c3 * d_inv));

    out.setUnsafe(2, 0, (float) (r2c0 * d_inv));
    out.setUnsafe(2, 1, (float) (r2c1 * d_inv));
    out.setUnsafe(2, 2, (float) (r2c2 * d_inv));
    out.setUnsafe(2, 3, (float) (r2c3 * d_inv));

    out.setUnsafe(3, 0, (float) (r3c0 * d_inv));
    out.setUnsafe(3, 1, (float) (r3c1 * d_inv));
    out.setUnsafe(3, 2, (float) (r3c2 * d_inv));
    out.setUnsafe(3, 3, (float) (r3c3 * d_inv));

    PMatrixM4x4F.transposeInPlace(out);
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
   * @see PMatrixM4x4F#determinant(PMatrixReadable4x4FType)
   *
   * @param m
   *          The input matrix.
   * @return {@code m}.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> OptionType<PMatrixM4x4F<T1, T0>> invertInPlace(
    final PMatrixM4x4F<T0, T1> m)
  {
    final PMatrixM4x4F<T1, T0> mt = (PMatrixM4x4F<T1, T0>) m;
    return PMatrixM4x4F.invert(m, mt);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting
   * matrix to {@code m}. The function returns {@code Some(out)} iff
   * it was possible to invert the matrix, and {@code None} otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * {@code 0}. The function uses preallocated storage in
   * {@code context} to avoid allocating memory. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @see PMatrixM4x4F#determinant(PMatrixReadable4x4FType)
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static
    <T0, T1>
    OptionType<PMatrixM4x4F<T1, T0>>
    invertInPlaceWithContext(
      final Context context,
      final PMatrixM4x4F<T0, T1> m)
  {
    final PMatrixM4x4F<T1, T0> mt = (PMatrixM4x4F<T1, T0>) m;
    return PMatrixM4x4F.invertWithContext(context, m, mt);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting
   * matrix to {@code out}. The function returns {@code Some(out)}
   * iff it was possible to invert the matrix, and {@code None}
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of {@code 0}. The function uses preallocated storage in
   * {@code context} to avoid allocating memory. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @see PMatrixM4x4F#determinant(PMatrixReadable4x4FType)
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> OptionType<PMatrixM4x4F<T1, T0>> invertWithContext(
    final Context context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final PMatrixM4x4F<T1, T0> out)
  {
    return PMatrixM4x4F.invertActual(m, context.getM3a(), out);
  }

  /**
   * <p>
   * Calculate a matrix representing a "camera" looking from the point
   * {@code origin} to the point {@code target}. {@code target}
   * must represent the "up" vector for the camera. Usually, this is simply a
   * unit vector {@code (0, 1, 0)} representing the Y axis.
   * </p>
   * <p>
   * The function uses preallocated storage from {@code context}.
   * </p>
   * <p>
   * The view is expressed as a rotation and translation matrix, written to
   * {@code out_matrix}.
   * </p>
   *
   * @param context
   *          Preallocated storage
   * @param out_matrix
   *          The output matrix
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
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final PMatrixM4x4F<T0, T1> out_matrix)
  {
    final VectorM3F forward = context.getV3a();
    final VectorM3F new_up = context.getV3b();
    final VectorM3F side = context.getV3c();
    final VectorM3F move = context.getV3d();

    final PMatrixM4x4F<T1, Phantom2Type> rotation =
      (PMatrixM4x4F<T1, Phantom2Type>) context.getM4a();
    final PMatrixM4x4F<T0, T1> translation =
      (PMatrixM4x4F<T0, T1>) context.getM4b();

    PMatrixM4x4F.setIdentity(rotation);
    PMatrixM4x4F.setIdentity(translation);
    PMatrixM4x4F.setIdentity(out_matrix);

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

    rotation.set(0, 0, side.getXF());
    rotation.set(0, 1, side.getYF());
    rotation.set(0, 2, side.getZF());
    rotation.set(1, 0, new_up.getXF());
    rotation.set(1, 1, new_up.getYF());
    rotation.set(1, 2, new_up.getZF());
    rotation.set(2, 0, -forward.getXF());
    rotation.set(2, 1, -forward.getYF());
    rotation.set(2, 2, -forward.getZF());

    /**
     * Calculate camera translation matrix
     */

    move.set3F(-origin.getXF(), -origin.getYF(), -origin.getZF());
    PMatrixM4x4F.makeTranslation3FInto(move, translation);

    /**
     * Produce output matrix
     */

    PMatrixM4x4F.multiply(
      rotation,
      translation,
      (PMatrixM4x4F<T0, Phantom2Type>) out_matrix);
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
   * @return A rotation matrix.
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeRotation(
    final double angle,
    final VectorReadable3FType axis)
  {
    final PMatrixM4x4F<T0, T1> out = new PMatrixM4x4F<T0, T1>();
    PMatrixM4x4F.makeRotationInto(angle, axis, out);
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
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeRotationInto(
    final double angle,
    final VectorReadable3FType axis,
    final PMatrixM4x4F<T0, T1> out)
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
    final double r0c3 = 0;

    final double r1c0 = txy + sz;
    final double r1c1 = ty_sq + c;
    final double r1c2 = tyz - sx;
    final double r1c3 = 0;

    final double r2c0 = txz - sy;
    final double r2c1 = tyz + sx;
    final double r2c2 = tz_sq + c;
    final double r2c3 = 0;

    final double r3c0 = 0;
    final double r3c1 = 0;
    final double r3c2 = 0;
    final double r3c3 = 1;

    out.setUnsafe(0, 0, (float) r0c0);
    out.setUnsafe(0, 1, (float) r0c1);
    out.setUnsafe(0, 2, (float) r0c2);
    out.setUnsafe(0, 3, (float) r0c3);

    out.setUnsafe(1, 0, (float) r1c0);
    out.setUnsafe(1, 1, (float) r1c1);
    out.setUnsafe(1, 2, (float) r1c2);
    out.setUnsafe(1, 3, (float) r1c3);

    out.setUnsafe(2, 0, (float) r2c0);
    out.setUnsafe(2, 1, (float) r2c1);
    out.setUnsafe(2, 2, (float) r2c2);
    out.setUnsafe(2, 3, (float) r2c3);

    out.setUnsafe(3, 0, (float) r3c0);
    out.setUnsafe(3, 1, (float) r3c1);
    out.setUnsafe(3, 2, (float) r3c2);
    out.setUnsafe(3, 3, (float) r3c3);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of
   * {@code (v.x, v.y)} from the origin.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param v
   *          The translation vector.
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation2F(
    final VectorReadable2FType v)
  {
    final PMatrixM4x4F<T0, T1> out = new PMatrixM4x4F<T0, T1>();
    PMatrixM4x4F.makeTranslation2FInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * {@code (v.x, v.y)} from the origin, and save to {@code out}.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation2FInto(
    final VectorReadable2FType v,
    final PMatrixM4x4F<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0f);
    out.setUnsafe(0, 1, 0.0f);
    out.setUnsafe(0, 2, 0.0f);
    out.setUnsafe(0, 3, v.getXF());

    out.setUnsafe(1, 0, 0.0f);
    out.setUnsafe(1, 1, 1.0f);
    out.setUnsafe(1, 2, 0.0f);
    out.setUnsafe(1, 3, v.getYF());

    out.setUnsafe(2, 0, 0.0f);
    out.setUnsafe(2, 1, 0.0f);
    out.setUnsafe(2, 2, 1.0f);
    out.setUnsafe(2, 3, 0.0f);

    out.setUnsafe(3, 0, 0.0f);
    out.setUnsafe(3, 1, 0.0f);
    out.setUnsafe(3, 2, 0.0f);
    out.setUnsafe(3, 3, 1.0f);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of
   * {@code (v.x, v.y)} from the origin.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param v
   *          The translation vector.
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation2I(
    final VectorReadable2IType v)
  {
    final PMatrixM4x4F<T0, T1> out = new PMatrixM4x4F<T0, T1>();
    PMatrixM4x4F.makeTranslation2IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * {@code (v.x, v.y)} from the origin, and save to {@code out}.
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation2IInto(
    final VectorReadable2IType v,
    final PMatrixM4x4F<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0f);
    out.setUnsafe(0, 1, 0.0f);
    out.setUnsafe(0, 2, 0.0f);
    out.setUnsafe(0, 3, v.getXI());

    out.setUnsafe(1, 0, 0.0f);
    out.setUnsafe(1, 1, 1.0f);
    out.setUnsafe(1, 2, 0.0f);
    out.setUnsafe(1, 3, v.getYI());

    out.setUnsafe(2, 0, 0.0f);
    out.setUnsafe(2, 1, 0.0f);
    out.setUnsafe(2, 2, 1.0f);
    out.setUnsafe(2, 3, 0.0f);

    out.setUnsafe(3, 0, 0.0f);
    out.setUnsafe(3, 1, 0.0f);
    out.setUnsafe(3, 2, 0.0f);
    out.setUnsafe(3, 3, 1.0f);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of
   * {@code (v.x, v.y, v.z)} from the origin.
   *
   * @param v
   *          The translation vector.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation3F(
    final VectorReadable3FType v)
  {
    final PMatrixM4x4F<T0, T1> out = new PMatrixM4x4F<T0, T1>();
    PMatrixM4x4F.makeTranslation3FInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * {@code (v.x, v.y, v.z)} from the origin, and save to
   * {@code out}.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation3FInto(
    final VectorReadable3FType v,
    final PMatrixM4x4F<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0f);
    out.setUnsafe(0, 1, 0.0f);
    out.setUnsafe(0, 2, 0.0f);
    out.setUnsafe(0, 3, v.getXF());

    out.setUnsafe(1, 0, 0.0f);
    out.setUnsafe(1, 1, 1.0f);
    out.setUnsafe(1, 2, 0.0f);
    out.setUnsafe(1, 3, v.getYF());

    out.setUnsafe(2, 0, 0.0f);
    out.setUnsafe(2, 1, 0.0f);
    out.setUnsafe(2, 2, 1.0f);
    out.setUnsafe(2, 3, v.getZF());

    out.setUnsafe(3, 0, 0.0f);
    out.setUnsafe(3, 1, 0.0f);
    out.setUnsafe(3, 2, 0.0f);
    out.setUnsafe(3, 3, 1.0f);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of
   * {@code (v.x, v.y, v.z)} from the origin.
   *
   * @param v
   *          The translation vector.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation3I(
    final VectorReadable3IType v)
  {
    final PMatrixM4x4F<T0, T1> out = new PMatrixM4x4F<T0, T1>();
    PMatrixM4x4F.makeTranslation3IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * {@code (v.x, v.y, v.z)} from the origin, and save to
   * {@code out}.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> makeTranslation3IInto(
    final VectorReadable3IType v,
    final PMatrixM4x4F<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0f);
    out.setUnsafe(0, 1, 0.0f);
    out.setUnsafe(0, 2, 0.0f);
    out.setUnsafe(0, 3, v.getXI());

    out.setUnsafe(1, 0, 0.0f);
    out.setUnsafe(1, 1, 1.0f);
    out.setUnsafe(1, 2, 0.0f);
    out.setUnsafe(1, 3, v.getYI());

    out.setUnsafe(2, 0, 0.0f);
    out.setUnsafe(2, 1, 0.0f);
    out.setUnsafe(2, 2, 1.0f);
    out.setUnsafe(2, 3, v.getZI());

    out.setUnsafe(3, 0, 0.0f);
    out.setUnsafe(3, 1, 0.0f);
    out.setUnsafe(3, 2, 0.0f);
    out.setUnsafe(3, 3, 1.0f);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1},
   * writing the result to {@code out}.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @param out
   *          The output matrix.
   * @return {@code out}
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2> PMatrixM4x4F<T0, T2> multiply(
    final PMatrixReadable4x4FType<T1, T2> m0,
    final PMatrixReadable4x4FType<T0, T1> m1,
    final PMatrixM4x4F<T0, T2> out)
  {
    float r0c0 = 0;
    r0c0 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 0);
    r0c0 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 0);
    r0c0 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 0);
    r0c0 += m0.getRowColumnF(0, 3) * m1.getRowColumnF(3, 0);

    float r1c0 = 0;
    r1c0 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 0);
    r1c0 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 0);
    r1c0 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 0);
    r1c0 += m0.getRowColumnF(1, 3) * m1.getRowColumnF(3, 0);

    float r2c0 = 0;
    r2c0 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 0);
    r2c0 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 0);
    r2c0 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 0);
    r2c0 += m0.getRowColumnF(2, 3) * m1.getRowColumnF(3, 0);

    float r3c0 = 0;
    r3c0 += m0.getRowColumnF(3, 0) * m1.getRowColumnF(0, 0);
    r3c0 += m0.getRowColumnF(3, 1) * m1.getRowColumnF(1, 0);
    r3c0 += m0.getRowColumnF(3, 2) * m1.getRowColumnF(2, 0);
    r3c0 += m0.getRowColumnF(3, 3) * m1.getRowColumnF(3, 0);

    float r0c1 = 0;
    r0c1 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 1);
    r0c1 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 1);
    r0c1 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 1);
    r0c1 += m0.getRowColumnF(0, 3) * m1.getRowColumnF(3, 1);

    float r1c1 = 0;
    r1c1 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 1);
    r1c1 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 1);
    r1c1 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 1);
    r1c1 += m0.getRowColumnF(1, 3) * m1.getRowColumnF(3, 1);

    float r2c1 = 0;
    r2c1 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 1);
    r2c1 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 1);
    r2c1 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 1);
    r2c1 += m0.getRowColumnF(2, 3) * m1.getRowColumnF(3, 1);

    float r3c1 = 0;
    r3c1 += m0.getRowColumnF(3, 0) * m1.getRowColumnF(0, 1);
    r3c1 += m0.getRowColumnF(3, 1) * m1.getRowColumnF(1, 1);
    r3c1 += m0.getRowColumnF(3, 2) * m1.getRowColumnF(2, 1);
    r3c1 += m0.getRowColumnF(3, 3) * m1.getRowColumnF(3, 1);

    float r0c2 = 0;
    r0c2 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 2);
    r0c2 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 2);
    r0c2 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 2);
    r0c2 += m0.getRowColumnF(0, 3) * m1.getRowColumnF(3, 2);

    float r1c2 = 0;
    r1c2 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 2);
    r1c2 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 2);
    r1c2 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 2);
    r1c2 += m0.getRowColumnF(1, 3) * m1.getRowColumnF(3, 2);

    float r2c2 = 0;
    r2c2 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 2);
    r2c2 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 2);
    r2c2 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 2);
    r2c2 += m0.getRowColumnF(2, 3) * m1.getRowColumnF(3, 2);

    float r3c2 = 0;
    r3c2 += m0.getRowColumnF(3, 0) * m1.getRowColumnF(0, 2);
    r3c2 += m0.getRowColumnF(3, 1) * m1.getRowColumnF(1, 2);
    r3c2 += m0.getRowColumnF(3, 2) * m1.getRowColumnF(2, 2);
    r3c2 += m0.getRowColumnF(3, 3) * m1.getRowColumnF(3, 2);

    float r0c3 = 0;
    r0c3 += m0.getRowColumnF(0, 0) * m1.getRowColumnF(0, 3);
    r0c3 += m0.getRowColumnF(0, 1) * m1.getRowColumnF(1, 3);
    r0c3 += m0.getRowColumnF(0, 2) * m1.getRowColumnF(2, 3);
    r0c3 += m0.getRowColumnF(0, 3) * m1.getRowColumnF(3, 3);

    float r1c3 = 0;
    r1c3 += m0.getRowColumnF(1, 0) * m1.getRowColumnF(0, 3);
    r1c3 += m0.getRowColumnF(1, 1) * m1.getRowColumnF(1, 3);
    r1c3 += m0.getRowColumnF(1, 2) * m1.getRowColumnF(2, 3);
    r1c3 += m0.getRowColumnF(1, 3) * m1.getRowColumnF(3, 3);

    float r2c3 = 0;
    r2c3 += m0.getRowColumnF(2, 0) * m1.getRowColumnF(0, 3);
    r2c3 += m0.getRowColumnF(2, 1) * m1.getRowColumnF(1, 3);
    r2c3 += m0.getRowColumnF(2, 2) * m1.getRowColumnF(2, 3);
    r2c3 += m0.getRowColumnF(2, 3) * m1.getRowColumnF(3, 3);

    float r3c3 = 0;
    r3c3 += m0.getRowColumnF(3, 0) * m1.getRowColumnF(0, 3);
    r3c3 += m0.getRowColumnF(3, 1) * m1.getRowColumnF(1, 3);
    r3c3 += m0.getRowColumnF(3, 2) * m1.getRowColumnF(2, 3);
    r3c3 += m0.getRowColumnF(3, 3) * m1.getRowColumnF(3, 3);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(0, 2, r0c2);
    out.setUnsafe(0, 3, r0c3);

    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(1, 2, r1c2);
    out.setUnsafe(1, 3, r1c3);

    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(2, 2, r2c2);
    out.setUnsafe(2, 3, r2c3);

    out.setUnsafe(3, 0, r3c0);
    out.setUnsafe(3, 1, r3c1);
    out.setUnsafe(3, 2, r3c2);
    out.setUnsafe(3, 3, r3c3);
    return out;
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
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <V>
   *          The precise type of writable vector.
   */

  public static
    <T0, T1, V extends PVectorWritable4FType<T1>>
    V
    multiplyVector4F(
      final PMatrixReadable4x4FType<T0, T1> m,
      final PVectorReadable4FType<T0> v,
      final V out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();
    return PMatrixM4x4F.multiplyVector4FActual(m, v, va, vb, out);
  }

  private static
    <T0, T1, V extends PVectorWritable4FType<T1>>
    V
    multiplyVector4FActual(
      final PMatrixReadable4x4FType<T0, T1> m,
      final PVectorReadable4FType<T0> v,
      final VectorM4F va,
      final VectorM4F vb,
      final V out)
  {
    VectorM4F.copy(v, vb);

    PMatrixM4x4F.rowUnsafe(m, 0, va);
    out.setXF((float) VectorM4F.dotProduct(va, vb));
    PMatrixM4x4F.rowUnsafe(m, 1, va);
    out.setYF((float) VectorM4F.dotProduct(va, vb));
    PMatrixM4x4F.rowUnsafe(m, 2, va);
    out.setZF((float) VectorM4F.dotProduct(va, vb));
    PMatrixM4x4F.rowUnsafe(m, 3, va);
    out.setWF((float) VectorM4F.dotProduct(va, vb));

    return out;
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v},
   * writing the resulting vector to {@code out}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory.
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return {@code out}
   *
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <V>
   *          The precise type of writable vector.
   */

  public static
    <T0, T1, V extends PVectorWritable4FType<T1>>
    V
    multiplyVector4FWithContext(
      final Context context,
      final PMatrixReadable4x4FType<T0, T1> m,
      final PVectorReadable4FType<T0> v,
      final V out)
  {
    return PMatrixM4x4F.multiplyVector4FActual(
      m,
      v,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  /**
   * Return row {@code row} of the matrix {@code m} in the vector
   * {@code out}.
   *
   * @param row
   *          The row
   * @param m
   *          The matrix
   * @param out
   *          The output vector
   * @return out
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <V>
   *          The precise type of writable vector.
   */

  public static <T0, T1, V extends VectorWritable4FType> V row(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row,
    final V out)
  {
    return PMatrixM4x4F.rowUnsafe(m, PMatrixM4x4F.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM4x4F.VIEW_COLS);
    }
    return row;
  }

  private static <V extends VectorWritable4FType> V rowUnsafe(
    final MatrixReadable4x4FType m,
    final int row,
    final V out)
  {
    out.set4F(
      m.getRowColumnF(row, 0),
      m.getRowColumnF(row, 1),
      m.getRowColumnF(row, 2),
      m.getRowColumnF(row, 3));
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value
   * {@code r}, saving the result in {@code out}.
   *
   * @param out
   *          The output matrix.
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scale(
    final PMatrixReadable4x4FType<T0, T1> m,
    final double r,
    final PMatrixM4x4F<T2, T3> out)
  {
    final float r0c0 = (float) (m.getRowColumnF(0, 0) * r);
    final float r1c0 = (float) (m.getRowColumnF(1, 0) * r);
    final float r2c0 = (float) (m.getRowColumnF(2, 0) * r);
    final float r3c0 = (float) (m.getRowColumnF(3, 0) * r);

    final float r0c1 = (float) (m.getRowColumnF(0, 1) * r);
    final float r1c1 = (float) (m.getRowColumnF(1, 1) * r);
    final float r2c1 = (float) (m.getRowColumnF(2, 1) * r);
    final float r3c1 = (float) (m.getRowColumnF(3, 1) * r);

    final float r0c2 = (float) (m.getRowColumnF(0, 2) * r);
    final float r1c2 = (float) (m.getRowColumnF(1, 2) * r);
    final float r2c2 = (float) (m.getRowColumnF(2, 2) * r);
    final float r3c2 = (float) (m.getRowColumnF(3, 2) * r);

    final float r0c3 = (float) (m.getRowColumnF(0, 3) * r);
    final float r1c3 = (float) (m.getRowColumnF(1, 3) * r);
    final float r2c3 = (float) (m.getRowColumnF(2, 3) * r);
    final float r3c3 = (float) (m.getRowColumnF(3, 3) * r);

    out.setUnsafe(0, 0, r0c0);
    out.setUnsafe(1, 0, r1c0);
    out.setUnsafe(2, 0, r2c0);
    out.setUnsafe(3, 0, r3c0);

    out.setUnsafe(0, 1, r0c1);
    out.setUnsafe(1, 1, r1c1);
    out.setUnsafe(2, 1, r2c1);
    out.setUnsafe(3, 1, r3c1);

    out.setUnsafe(0, 2, r0c2);
    out.setUnsafe(1, 2, r1c2);
    out.setUnsafe(2, 2, r2c2);
    out.setUnsafe(3, 2, r3c2);

    out.setUnsafe(0, 3, r0c3);
    out.setUnsafe(1, 3, r1c3);
    out.setUnsafe(2, 3, r2c3);
    out.setUnsafe(3, 3, r3c3);

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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scaleInPlace(
    final PMatrixM4x4F<T0, T1> m,
    final double r)
  {
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.scale(m, r, m);
  }

  /**
   * <p>
   * Scale row {@code r} of the matrix {@code m} by {@code r},
   * saving the result to row {@code r} of {@code out}.
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
   *          The index of the row {@code 0 <= row < 4}.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scaleRow(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM4x4F<T2, T3> out)
  {
    final VectorM4F tmp = new VectorM4F();
    return PMatrixM4x4F.scaleRowUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row),
      r,
      tmp,
      out);
  }

  /**
   * <p>
   * Scale row {@code row} of the matrix {@code m} by {@code r}
   * , saving the result to row {@code r} of {@code m}.
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
   *          The index of the row {@code 0 <= row < 4}.
   * @param r
   *          The scaling value.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scaleRowInPlace(
    final PMatrixM4x4F<T0, T1> m,
    final int row,
    final double r)
  {
    final VectorM4F tmp = new VectorM4F();
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.scaleRowUnsafe(
      m,
      row,
      r,
      tmp,
      m);
  }

  /**
   * <p>
   * Scale row {@code row} of the matrix {@code m} by {@code r}
   * , saving the result to row {@code r} of {@code m}. The function
   * uses preallocated storage in {@code context} to avoid allocating
   * memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code 0 <= row < 4}.
   * @param r
   *          The scaling value.
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static
    <T0, T1, T2, T3>
    PMatrixM4x4F<T2, T3>
    scaleRowInPlaceWithContext(
      final Context context,
      final PMatrixM4x4F<T0, T1> m,
      final int row,
      final double r)
  {
    return (PMatrixM4x4F<T2, T3>) PMatrixM4x4F.scaleRowUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row),
      r,
      context.getV4a(),
      m);
  }

  private static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scaleRowUnsafe(
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row,
    final double r,
    final VectorM4F tmp,
    final PMatrixM4x4F<T2, T3> out)
  {
    PMatrixM4x4F.rowUnsafe(m, row, tmp);
    VectorM4F.scaleInPlace(tmp, r);
    PMatrixM4x4F.setRowUnsafe(out, row, tmp);
    return out;
  }

  /**
   * <p>
   * Scale row {@code row} of the matrix {@code m} by {@code r}
   * , saving the result to row {@code r} of {@code out}. The
   * function uses preallocated storage in {@code context} to avoid
   * allocating memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> .
   * </p>
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code 0 <= row < 4}.
   * @param r
   *          The scaling value.
   * @param out
   *          The output matrix.
   * @return {@code out}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> scaleRowWithContext(
    final Context context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM4x4F<T2, T3> out)
  {
    return PMatrixM4x4F.scaleRowUnsafe(
      m,
      PMatrixM4x4F.rowCheck(row),
      r,
      context.getV4a(),
      out);
  }

  /**
   * Set the value in the matrix {@code m} at row {@code row},
   * column {@code column} to {@code value}.
   *
   * @return {@code m}
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   */

  public static <T0, T1> PMatrixM4x4F<T0, T1> set(
    final PMatrixM4x4F<T0, T1> m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(PMatrixM4x4F.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m
   *          The input matrix
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> setIdentity(
    final PMatrixM4x4F<T0, T1> m)
  {
    m.view.clear();

    for (int row = 0; row < PMatrixM4x4F.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM4x4F.VIEW_COLS; ++col) {
        if (row == col) {
          m.setUnsafe(row, col, 1.0f);
        } else {
          m.setUnsafe(row, col, 0.0f);
        }
      }
    }

    return (PMatrixM4x4F<T2, T3>) m;
  }

  private static <T0, T1> void setRowUnsafe(
    final PMatrixM4x4F<T0, T1> m,
    final int row,
    final VectorReadable4FType v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.setUnsafe(row, 2, v.getZF());
    m.setUnsafe(row, 3, v.getWF());
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m
   *          The matrix
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> setZero(
    final PMatrixM4x4F<T0, T1> m)
  {
    m.view.clear();
    for (int index = 0; index < (PMatrixM4x4F.VIEW_ROWS * PMatrixM4x4F.VIEW_COLS); ++index) {
      m.view.put(index, 0.0f);
    }
    return (PMatrixM4x4F<T2, T3>) m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as
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
    final PMatrixReadable4x4FType<T0, T1> m)
  {
    return m.getRowColumnF(0, 0)
      + m.getRowColumnF(1, 1)
      + m.getRowColumnF(2, 2)
      + m.getRowColumnF(3, 3);
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
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> transpose(
    final PMatrixReadable4x4FType<T0, T1> m,
    final PMatrixM4x4F<T2, T3> out)
  {
    PMatrixM4x4F.copy(m, (PMatrixM4x4F<T0, T1>) out);
    return PMatrixM4x4F.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix
   * to {@code m}.
   *
   * @param m
   *          The input matrix.
   * @return {@code m}
   * @param <T0>
   *          A phantom type parameter.
   * @param <T1>
   *          A phantom type parameter.
   * @param <T2>
   *          A phantom type parameter.
   * @param <T3>
   *          A phantom type parameter.
   */

  public static <T0, T1, T2, T3> PMatrixM4x4F<T2, T3> transposeInPlace(
    final PMatrixM4x4F<T0, T1> m)
  {
    for (int row = 0; row < (PMatrixM4x4F.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < PMatrixM4x4F.VIEW_COLS; ++column) {
        final float x = m.view.get((row * PMatrixM4x4F.VIEW_ROWS) + column);
        m.view.put(
          (row * PMatrixM4x4F.VIEW_ROWS) + column,
          m.view.get(row + (PMatrixM4x4F.VIEW_COLS * column)));
        m.view.put(row + (PMatrixM4x4F.VIEW_COLS * column), x);
      }
    }

    return (PMatrixM4x4F<T2, T3>) m;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM4x4F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    PMatrixM4x4F.setIdentity(this);
    this.view.rewind();
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source
   *          The source matrix.
   */

  public PMatrixM4x4F(
    final PMatrixReadable4x4FType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.rewind();

    for (int row = 0; row < PMatrixM4x4F.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM4x4F.VIEW_COLS; ++col) {
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

    final PMatrixM4x4F<?, ?> other = (PMatrixM4x4F<?, ?>) obj;
    for (int index = 0; index < PMatrixM4x4F.VIEW_ELEMENTS; ++index) {
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

  @Override public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    PMatrixM4x4F.rowUnsafe(this, PMatrixM4x4F.rowCheck(row), out);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM4x4F.indexChecked(row, column));
  }

  private float getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM4x4F.indexUnsafe(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < PMatrixM4x4F.VIEW_ELEMENTS; ++index) {
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
   * @return {@code this}
   */

  public PMatrixM4x4F<T0, T1> set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixM4x4F.indexChecked(row, column), value);
    return this;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixM4x4F.indexChecked(row, column), value);
  }

  /**
   * Set the value at row {@code row} and {@code column} to
   * {@code value} without bounds checking.
   *
   * This function is only accessible by code in the same package as this.
   */

  PMatrixM4x4F<T0, T1> setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixM4x4F.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < PMatrixM4x4F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(PMatrixM4x4F.indexUnsafe(row, 0));
      final float c1 = this.view.get(PMatrixM4x4F.indexUnsafe(row, 1));
      final float c2 = this.view.get(PMatrixM4x4F.indexUnsafe(row, 2));
      final float c3 = this.view.get(PMatrixM4x4F.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.6f %+.6f %+.6f %+.6f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
