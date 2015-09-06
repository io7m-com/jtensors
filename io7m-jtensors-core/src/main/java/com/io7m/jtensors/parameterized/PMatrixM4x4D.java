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
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable4DType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p> A 4x4 mutable matrix type with double precision elements. </p>
 *
 * @param <T0> A phantom type parameter.
 * @param <T1> A phantom type parameter.
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM4x4D<T0, T1>
  implements PMatrixDirectReadable4x4DType<T0, T1>,
  PMatrixWritable4x4DType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = PMatrixM4x4D.VIEW_ROWS * PMatrixM4x4D.VIEW_COLS;
    VIEW_BYTES = PMatrixM4x4D.VIEW_ELEMENTS * PMatrixM4x4D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM4x4D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();
    PMatrixM4x4D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix.
   */

  public PMatrixM4x4D(
    final PMatrixReadable4x4DType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    for (int row = 0; row < PMatrixM4x4D.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM4x4D.VIEW_COLS; ++col) {
        this.setUnsafe(row, col, source.getRowColumnD(row, col));
      }
    }
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param m0   The left input matrix.
   * @param m1   The right input matrix.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   * @param <T4> A phantom type parameter.
   * @param <T5> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3, T4, T5> PMatrixM4x4D<T4, T5> add(
    final PMatrixReadable4x4DType<T0, T1> m0,
    final PMatrixReadable4x4DType<T2, T3> m1,
    final PMatrixM4x4D<T4, T5> out)
  {
    final double r0c0 = m0.getRowColumnD(0, 0) + m1.getRowColumnD(0, 0);
    final double r1c0 = m0.getRowColumnD(1, 0) + m1.getRowColumnD(1, 0);
    final double r2c0 = m0.getRowColumnD(2, 0) + m1.getRowColumnD(2, 0);
    final double r3c0 = m0.getRowColumnD(3, 0) + m1.getRowColumnD(3, 0);

    final double r0c1 = m0.getRowColumnD(0, 1) + m1.getRowColumnD(0, 1);
    final double r1c1 = m0.getRowColumnD(1, 1) + m1.getRowColumnD(1, 1);
    final double r2c1 = m0.getRowColumnD(2, 1) + m1.getRowColumnD(2, 1);
    final double r3c1 = m0.getRowColumnD(3, 1) + m1.getRowColumnD(3, 1);

    final double r0c2 = m0.getRowColumnD(0, 2) + m1.getRowColumnD(0, 2);
    final double r1c2 = m0.getRowColumnD(1, 2) + m1.getRowColumnD(1, 2);
    final double r2c2 = m0.getRowColumnD(2, 2) + m1.getRowColumnD(2, 2);
    final double r3c2 = m0.getRowColumnD(3, 2) + m1.getRowColumnD(3, 2);

    final double r0c3 = m0.getRowColumnD(0, 3) + m1.getRowColumnD(0, 3);
    final double r1c3 = m0.getRowColumnD(1, 3) + m1.getRowColumnD(1, 3);
    final double r2c3 = m0.getRowColumnD(2, 3) + m1.getRowColumnD(2, 3);
    final double r3c3 = m0.getRowColumnD(3, 3) + m1.getRowColumnD(3, 3);

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
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param m0   The left input matrix.
   * @param m1   The right input matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   * @param <T4> A phantom type parameter.
   * @param <T5> A phantom type parameter.
   *
   * @return m0
   */

  public static <T0, T1, T2, T3, T4, T5> PMatrixM4x4D<T4, T5> addInPlace(
    final PMatrixM4x4D<T0, T1> m0,
    final PMatrixReadable4x4DType<T2, T3> m1)
  {
    return (PMatrixM4x4D<T4, T5>) PMatrixM4x4D.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. </p> <p> This is one of the three "elementary"
   * operations defined on matrices. See <a href= "http://en.wikipedia
   * .org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m     The input matrix.
   * @param row_a The row on the lefthand side of the addition.
   * @param row_b The row on the righthand side of the addition.
   * @param row_c The destination row.
   * @param r     The scaling value.
   * @param out   The output matrix.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   * @param <T2>  A phantom type parameter.
   * @param <T3>  A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> addRowScaled(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final PMatrixM4x4D<T2, T3> out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();

    return PMatrixM4x4D.addRowScaledUnsafe(
      m,
      PMatrixM4x4D.rowCheck(row_a),
      PMatrixM4x4D.rowCheck(row_b),
      PMatrixM4x4D.rowCheck(row_c),
      r,
      va,
      vb,
      out);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code m}. </p> <p> This is one of the three "elementary" operations
   * defined on matrices. See <a href= "http://en.wikipedia
   * .org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m     The input matrix.
   * @param row_a The row on the lefthand side of the addition.
   * @param row_b The row on the righthand side of the addition.
   * @param row_c The destination row.
   * @param r     The scaling value.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   * @param <T2>  A phantom type parameter.
   * @param <T3>  A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> addRowScaledInPlace(
    final PMatrixM4x4D<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.addRowScaled(
      m, row_a, row_b, row_c, r, m);
  }

  private static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> addRowScaledUnsafe(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM4D va,
    final VectorM4D vb,
    final PMatrixM4x4D<T2, T3> out)
  {
    PMatrixM4x4D.rowUnsafe(m, row_a, va);
    PMatrixM4x4D.rowUnsafe(m, row_b, vb);
    VectorM4D.addScaledInPlace(va, vb, r);
    PMatrixM4x4D.setRowUnsafe(out, row_c, va);
    return out;
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. The function uses storage preallocated in {@code
   * context} to avoid any new allocations. </p> <p> This is one of the three
   * "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param row_a   The row on the lefthand side of the addition.
   * @param row_b   The row on the righthand side of the addition.
   * @param row_c   The destination row.
   * @param r       The scaling value.
   * @param out     The output matrix.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <T2>    A phantom type parameter.
   * @param <T3>    A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> addRowScaledWithContext(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final PMatrixM4x4D<T2, T3> out)
  {
    return PMatrixM4x4D.addRowScaledUnsafe(
      m,
      PMatrixM4x4D.rowCheck(row_a),
      PMatrixM4x4D.rowCheck(row_b),
      PMatrixM4x4D.rowCheck(row_c),
      r,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + PMatrixM4x4D.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param input  The input vector.
   * @param output The output vector.
   * @param <T0>   A phantom type parameter.
   * @param <T1>   A phantom type parameter.
   *
   * @return {@code output}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> copy(
    final PMatrixReadable4x4DType<T0, T1> input,
    final PMatrixM4x4D<T0, T1> output)
  {
    for (int col = 0; col < PMatrixM4x4D.VIEW_COLS; ++col) {
      for (int row = 0; row < PMatrixM4x4D.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnD(row, col));
      }
    }
    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m    The input matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return The determinant.
   */

  public static <T0, T1> double determinant(
    final PMatrixReadable4x4DType<T0, T1> m)
  {
    final double r0c0 = m.getRowColumnD(0, 0);
    final double r1c0 = m.getRowColumnD(1, 0);
    final double r2c0 = m.getRowColumnD(2, 0);
    final double r3c0 = m.getRowColumnD(3, 0);

    final double r0c1 = m.getRowColumnD(0, 1);
    final double r1c1 = m.getRowColumnD(1, 1);
    final double r2c1 = m.getRowColumnD(2, 1);
    final double r3c1 = m.getRowColumnD(3, 1);

    final double r0c2 = m.getRowColumnD(0, 2);
    final double r1c2 = m.getRowColumnD(1, 2);
    final double r2c2 = m.getRowColumnD(2, 2);
    final double r3c2 = m.getRowColumnD(3, 2);

    final double r0c3 = m.getRowColumnD(0, 3);
    final double r1c3 = m.getRowColumnD(1, 3);
    final double r2c3 = m.getRowColumnD(2, 3);
    final double r3c3 = m.getRowColumnD(3, 3);

    double sum = 0.0;

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
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out}. </p> <p> This is one
   * of the three "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m     The input matrix.
   * @param row_a The first row.
   * @param row_b The second row.
   * @param out   The output matrix.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   * @param <T2>  A phantom type parameter.
   * @param <T3>  A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> exchangeRows(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final PMatrixM4x4D<T2, T3> out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();
    return PMatrixM4x4D.exchangeRowsUnsafe(
      m,
      PMatrixM4x4D.rowCheck(row_a),
      PMatrixM4x4D.rowCheck(row_b),
      va,
      vb,
      out);
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. </p> <p> This is one of
   * the three "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m     The input matrix.
   * @param row_a The first row.
   * @param row_b The second row.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   * @param <T2>  A phantom type parameter.
   * @param <T3>  A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> exchangeRowsInPlace(
    final PMatrixM4x4D<T0, T1> m,
    final int row_a,
    final int row_b)
  {
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.exchangeRows(
      m, row_a, row_b, m);
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. The function uses
   * storage preallocated in {@code context} to avoid allocating memory. </p>
   * <p> This is one of the three "elementary" operations defined on matrices.
   * See <a href= "http://en.wikipedia
   * .org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param row_a   The first row.
   * @param row_b   The second row.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <T2>    A phantom type parameter.
   * @param <T3>    A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3>
  exchangeRowsInPlaceWithContext(
    final ContextPM4D context,
    final PMatrixM4x4D<T0, T1> m,
    final int row_a,
    final int row_b)
  {
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.exchangeRowsWithContext(
      context, m, row_a, row_b, m);
  }

  private static <T0, T1> PMatrixM4x4D<T0, T1> exchangeRowsUnsafe(
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final VectorM4D va,
    final VectorM4D vb,
    final PMatrixM4x4D<T0, T1> out)
  {
    PMatrixM4x4D.rowUnsafe(m, row_a, va);
    PMatrixM4x4D.rowUnsafe(m, row_b, vb);
    PMatrixM4x4D.setRowUnsafe(out, row_a, vb);
    PMatrixM4x4D.setRowUnsafe(out, row_b, va);
    return out;
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out} . </p> <p> The function
   * uses storage preallocated in {@code context} to avoid allocating memory.
   * </p> <p> This is one of the three "elementary" operations defined on
   * matrices. See <a href= "http://en.wikipedia
   * .org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param row_a   The first row.
   * @param row_b   The second row.
   * @param out     The output matrix.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <T2>    A phantom type parameter.
   * @param <T3>    A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> exchangeRowsWithContext(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row_a,
    final int row_b,
    final PMatrixM4x4D<T2, T3> out)
  {
    return PMatrixM4x4D.exchangeRowsUnsafe(
      m,
      PMatrixM4x4D.rowCheck(row_a),
      PMatrixM4x4D.rowCheck(row_b),
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM4x4D.indexUnsafe(
      PMatrixM4x4D.rowCheck(row), PMatrixM4x4D.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * PMatrixM4x4D.VIEW_COLS) + row;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param m    The input matrix.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   *
   * @see PMatrixM4x4D#determinant(PMatrixReadable4x4DType)
   */

  public static <T0, T1> OptionType<PMatrixM4x4D<T1, T0>> invert(
    final PMatrixReadable4x4DType<T0, T1> m,
    final PMatrixM4x4D<T1, T0> out)
  {
    final MatrixM3x3D m3 = new MatrixM3x3D();
    return PMatrixM4x4D.invertActual(m, m3, out);
  }

  private static <T0, T1> OptionType<PMatrixM4x4D<T1, T0>> invertActual(
    final PMatrixReadable4x4DType<T0, T1> m,
    final MatrixM3x3D m3,
    final PMatrixM4x4D<T1, T0> out)
  {
    final double d = PMatrixM4x4D.determinant(m);

    if (d == 0.0) {
      return Option.none();
    }

    final double d_inv = 1.0 / d;

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

      m3.set(0, 0, m.getRowColumnD(1, 1));
      m3.set(0, 1, m.getRowColumnD(1, 2));
      m3.set(0, 2, m.getRowColumnD(1, 3));
      m3.set(1, 0, m.getRowColumnD(2, 1));
      m3.set(1, 1, m.getRowColumnD(2, 2));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 1));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r0c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.set(0, 0, m.getRowColumnD(1, 0));
      m3.set(0, 1, m.getRowColumnD(1, 2));
      m3.set(0, 2, m.getRowColumnD(1, 3));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 2));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r0c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.set(0, 0, m.getRowColumnD(1, 0));
      m3.set(0, 1, m.getRowColumnD(1, 1));
      m3.set(0, 2, m.getRowColumnD(1, 3));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 1));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r0c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.set(0, 0, m.getRowColumnD(1, 0));
      m3.set(0, 1, m.getRowColumnD(1, 1));
      m3.set(0, 2, m.getRowColumnD(1, 2));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 1));
      m3.set(1, 2, m.getRowColumnD(2, 2));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 2));

      r0c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.set(0, 0, m.getRowColumnD(0, 1));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(2, 1));
      m3.set(1, 1, m.getRowColumnD(2, 2));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 1));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r1c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 2));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r1c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 1));
      m3.set(1, 2, m.getRowColumnD(2, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r1c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 2));
      m3.set(1, 0, m.getRowColumnD(2, 0));
      m3.set(1, 1, m.getRowColumnD(2, 1));
      m3.set(1, 2, m.getRowColumnD(2, 2));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 2));

      r1c3 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.set(0, 0, m.getRowColumnD(0, 1));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 1));
      m3.set(1, 1, m.getRowColumnD(1, 2));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(3, 1));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r2c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 2));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 2));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r2c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 1));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 3));

      r2c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 2));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 1));
      m3.set(1, 2, m.getRowColumnD(1, 2));
      m3.set(2, 0, m.getRowColumnD(3, 0));
      m3.set(2, 1, m.getRowColumnD(3, 1));
      m3.set(2, 2, m.getRowColumnD(3, 2));

      r2c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.set(0, 0, m.getRowColumnD(0, 1));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 1));
      m3.set(1, 1, m.getRowColumnD(1, 2));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(2, 1));
      m3.set(2, 1, m.getRowColumnD(2, 2));
      m3.set(2, 2, m.getRowColumnD(2, 3));

      r3c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 2));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 2));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(2, 0));
      m3.set(2, 1, m.getRowColumnD(2, 2));
      m3.set(2, 2, m.getRowColumnD(2, 3));

      r3c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 3));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 1));
      m3.set(1, 2, m.getRowColumnD(1, 3));
      m3.set(2, 0, m.getRowColumnD(2, 0));
      m3.set(2, 1, m.getRowColumnD(2, 1));
      m3.set(2, 2, m.getRowColumnD(2, 3));

      r3c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.set(0, 0, m.getRowColumnD(0, 0));
      m3.set(0, 1, m.getRowColumnD(0, 1));
      m3.set(0, 2, m.getRowColumnD(0, 2));
      m3.set(1, 0, m.getRowColumnD(1, 0));
      m3.set(1, 1, m.getRowColumnD(1, 1));
      m3.set(1, 2, m.getRowColumnD(1, 2));
      m3.set(2, 0, m.getRowColumnD(2, 0));
      m3.set(2, 1, m.getRowColumnD(2, 1));
      m3.set(2, 2, m.getRowColumnD(2, 2));

      r3c3 = MatrixM3x3D.determinant(m3);
    }

    /**
     * Divide sub-matrix determinants by the determinant of the original
     * matrix and transpose.
     */

    out.setUnsafe(0, 0, r0c0 * d_inv);
    out.setUnsafe(0, 1, r0c1 * d_inv);
    out.setUnsafe(0, 2, r0c2 * d_inv);
    out.setUnsafe(0, 3, r0c3 * d_inv);

    out.setUnsafe(1, 0, r1c0 * d_inv);
    out.setUnsafe(1, 1, r1c1 * d_inv);
    out.setUnsafe(1, 2, r1c2 * d_inv);
    out.setUnsafe(1, 3, r1c3 * d_inv);

    out.setUnsafe(2, 0, r2c0 * d_inv);
    out.setUnsafe(2, 1, r2c1 * d_inv);
    out.setUnsafe(2, 2, r2c2 * d_inv);
    out.setUnsafe(2, 3, r2c3 * d_inv);

    out.setUnsafe(3, 0, r3c0 * d_inv);
    out.setUnsafe(3, 1, r3c1 * d_inv);
    out.setUnsafe(3, 2, r3c2 * d_inv);
    out.setUnsafe(3, 3, r3c3 * d_inv);

    PMatrixM4x4D.transposeInPlace(out);
    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param m    The input matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code m}
   *
   * @see PMatrixM4x4D#determinant(PMatrixReadable4x4DType)
   */

  public static <T0, T1> OptionType<PMatrixM4x4D<T1, T0>> invertInPlace(
    final PMatrixM4x4D<T0, T1> m)
  {
    final PMatrixM4x4D<T1, T0> mt = (PMatrixM4x4D<T1, T0>) m;
    return PMatrixM4x4D.invert(m, mt);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(out)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory. If the
   * function returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   *
   * @return {@code m}
   *
   * @see PMatrixM4x4D#determinant(PMatrixReadable4x4DType)
   */

  public static <T0, T1> OptionType<PMatrixM4x4D<T1, T0>>
  invertInPlaceWithContext(
    final ContextPM4D context,
    final PMatrixM4x4D<T0, T1> m)
  {
    final PMatrixM4x4D<T1, T0> mt = (PMatrixM4x4D<T1, T0>) m;
    return PMatrixM4x4D.invertWithContext(context, m, mt);
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory. If the
   * function returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param out     The output matrix.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   *
   * @return {@code out}
   *
   * @see PMatrixM4x4D#determinant(PMatrixReadable4x4DType)
   */

  public static <T0, T1> OptionType<PMatrixM4x4D<T1, T0>> invertWithContext(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final PMatrixM4x4D<T1, T0> out)
  {
    return PMatrixM4x4D.invertActual(m, context.getM3a(), out);
  }

  /**
   * <p> Calculate a matrix representing a "camera" looking from the point
   * {@code origin} to the point {@code target}. {@code target} must represent
   * the "up" vector for the camera. Usually, this is simply a unit vector
   * {@code (0, 1, 0)} representing the Y axis. </p> <p> The function uses
   * preallocated storage from {@code context}. </p> <p> The view is expressed
   * as a rotation and translation matrix, written to {@code out_matrix}. </p>
   *
   * @param context    Preallocated storage
   * @param out_matrix The output matrix
   * @param origin     The position of the viewer
   * @param target     The target being viewed
   * @param up         The up vector
   * @param <T0>       A phantom type parameter.
   * @param <T1>       A phantom type parameter.
   */

  public static <T0, T1> void lookAtWithContext(
    final ContextPM4D context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final PMatrixM4x4D<T0, T1> out_matrix)
  {
    final VectorM3D forward = context.getV3a();
    final VectorM3D new_up = context.getV3b();
    final VectorM3D side = context.getV3c();
    final VectorM3D move = context.getV3d();
    final PMatrixM4x4D<T1, Phantom2Type> rotation =
      (PMatrixM4x4D<T1, Phantom2Type>) context.getM4a();
    final PMatrixM4x4D<T0, T1> translation =
      (PMatrixM4x4D<T0, T1>) context.getM4b();

    PMatrixM4x4D.setIdentity(rotation);
    PMatrixM4x4D.setIdentity(translation);
    PMatrixM4x4D.setIdentity(out_matrix);

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

    rotation.set(0, 0, side.getXD());
    rotation.set(0, 1, side.getYD());
    rotation.set(0, 2, side.getZD());
    rotation.set(1, 0, new_up.getXD());
    rotation.set(1, 1, new_up.getYD());
    rotation.set(1, 2, new_up.getZD());
    rotation.set(2, 0, -forward.getXD());
    rotation.set(2, 1, -forward.getYD());
    rotation.set(2, 2, -forward.getZD());

    /**
     * Calculate camera translation matrix
     */

    move.set3D(-origin.getXD(), -origin.getYD(), -origin.getZD());
    PMatrixM4x4D.makeTranslation3DInto(move, translation);

    /**
     * Produce output matrix
     */

    PMatrixM4x4D.multiply(
      rotation, translation, (PMatrixM4x4D<T0, Phantom2Type>) out_matrix);
  }

  /**
   * <p> Generate and return a matrix that represents a rotation of {@code
   * angle} radians around the axis {@code axis}. </p> <p> The function assumes
   * a right-handed coordinate system and therefore a positive rotation around
   * any axis represents a counter-clockwise rotation around that axis. </p>
   *
   * @param angle The angle in radians.
   * @param axis  The axis.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   *
   * @return A rotation matrix.
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeRotation(
    final double angle,
    final VectorReadable3DType axis)
  {
    final PMatrixM4x4D<T0, T1> out = new PMatrixM4x4D<T0, T1>();
    PMatrixM4x4D.makeRotationInto(angle, axis, out);
    return out;
  }

  /**
   * <p> Generate a matrix that represents a rotation of {@code angle} radians
   * around the axis {@code axis} and save to {@code out}. </p> <p> The function
   * assumes a right-handed coordinate system and therefore a positive rotation
   * around any axis represents a counter-clockwise rotation around that axis.
   * </p>
   *
   * @param angle The angle in radians.
   * @param axis  The axis.
   * @param out   The output matrix.
   * @param <T0>  A phantom type parameter.
   * @param <T1>  A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeRotationInto(
    final double angle,
    final VectorReadable3DType axis,
    final PMatrixM4x4D<T0, T1> out)
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
    final double r0c3 = 0.0;

    final double r1c0 = txy + sz;
    final double r1c1 = ty_sq + c;
    final double r1c2 = tyz - sx;
    final double r1c3 = 0.0;

    final double r2c0 = txz - sy;
    final double r2c1 = tyz + sx;
    final double r2c2 = tz_sq + c;
    final double r2c3 = 0.0;

    final double r3c0 = 0.0;
    final double r3c1 = 0.0;
    final double r3c2 = 0.0;
    final double r3c3 = 1.0;

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
   * Generate and return a matrix that represents a translation of {@code (v.x,
   * v.y)} from the origin.
   *
   * @param v    The translation vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation2D(
    final VectorReadable2DType v)
  {
    final PMatrixM4x4D<T0, T1> out = new PMatrixM4x4D<T0, T1>();
    PMatrixM4x4D.makeTranslation2DInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y)} from
   * the origin, and save to {@code out}.
   *
   * @param v    The translation vector.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation2DInto(
    final VectorReadable2DType v,
    final PMatrixM4x4D<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, v.getXD());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, v.getYD());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, 0.0);

    out.setUnsafe(3, 0, 0.0);
    out.setUnsafe(3, 1, 0.0);
    out.setUnsafe(3, 2, 0.0);
    out.setUnsafe(3, 3, 1.0);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of {@code (v.x,
   * v.y)} from the origin.
   *
   * @param v    The translation vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation2I(
    final VectorReadable2IType v)
  {
    final PMatrixM4x4D<T0, T1> out = new PMatrixM4x4D<T0, T1>();
    PMatrixM4x4D.makeTranslation2IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y)} from
   * the origin, and save to {@code out}.
   *
   * @param v    The translation vector.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation2IInto(
    final VectorReadable2IType v,
    final PMatrixM4x4D<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, (double) v.getXI());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, (double) v.getYI());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, 0.0);

    out.setUnsafe(3, 0, 0.0);
    out.setUnsafe(3, 1, 0.0);
    out.setUnsafe(3, 2, 0.0);
    out.setUnsafe(3, 3, 1.0);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of {@code (v.x,
   * v.y, v.z)} from the origin.
   *
   * @param v    The translation vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation3D(
    final VectorReadable3DType v)
  {
    final PMatrixM4x4D<T0, T1> out = new PMatrixM4x4D<T0, T1>();
    PMatrixM4x4D.makeTranslation3DInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y, v.z)}
   * from the origin, and save to {@code out}.
   *
   * @param v    The translation vector.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation3DInto(
    final VectorReadable3DType v,
    final PMatrixM4x4D<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, v.getXD());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, v.getYD());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, v.getZD());

    out.setUnsafe(3, 0, 0.0);
    out.setUnsafe(3, 1, 0.0);
    out.setUnsafe(3, 2, 0.0);
    out.setUnsafe(3, 3, 1.0);
    return out;
  }

  /**
   * Generate and return a matrix that represents a translation of {@code (v.x,
   * v.y, v.z)} from the origin.
   *
   * @param v    The translation vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation3I(
    final VectorReadable3IType v)
  {
    final PMatrixM4x4D<T0, T1> out = new PMatrixM4x4D<T0, T1>();
    PMatrixM4x4D.makeTranslation3IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of {@code (v.x, v.y, v.z)}
   * from the origin, and save to {@code out}.
   *
   * @param v    The translation vector.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> makeTranslation3IInto(
    final VectorReadable3IType v,
    final PMatrixM4x4D<T0, T1> out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, (double) v.getXI());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, (double) v.getYI());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, (double) v.getZI());

    out.setUnsafe(3, 0, 0.0);
    out.setUnsafe(3, 1, 0.0);
    out.setUnsafe(3, 2, 0.0);
    out.setUnsafe(3, 3, 1.0);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0   The left input vector.
   * @param m1   The right input vector.
   * @param out  The output vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2> PMatrixM4x4D<T0, T2> multiply(
    final PMatrixReadable4x4DType<T1, T2> m0,
    final PMatrixReadable4x4DType<T0, T1> m1,
    final PMatrixM4x4D<T0, T2> out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 0);
    r0c0 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 0);
    r0c0 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 0);
    r0c0 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 0);

    double r1c0 = 0.0;
    r1c0 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 0);
    r1c0 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 0);
    r1c0 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 0);
    r1c0 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 0);

    double r2c0 = 0.0;
    r2c0 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 0);
    r2c0 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 0);
    r2c0 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 0);
    r2c0 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 0);

    double r3c0 = 0.0;
    r3c0 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 0);
    r3c0 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 0);
    r3c0 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 0);
    r3c0 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 0);

    double r0c1 = 0.0;
    r0c1 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 1);
    r0c1 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 1);
    r0c1 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 1);
    r0c1 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 1);

    double r1c1 = 0.0;
    r1c1 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 1);
    r1c1 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 1);
    r1c1 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 1);
    r1c1 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 1);

    double r2c1 = 0.0;
    r2c1 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 1);
    r2c1 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 1);
    r2c1 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 1);
    r2c1 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 1);

    double r3c1 = 0.0;
    r3c1 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 1);
    r3c1 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 1);
    r3c1 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 1);
    r3c1 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 1);

    double r0c2 = 0.0;
    r0c2 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 2);
    r0c2 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 2);
    r0c2 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 2);
    r0c2 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 2);

    double r1c2 = 0.0;
    r1c2 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 2);
    r1c2 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 2);
    r1c2 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 2);
    r1c2 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 2);

    double r2c2 = 0.0;
    r2c2 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 2);
    r2c2 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 2);
    r2c2 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 2);
    r2c2 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 2);

    double r3c2 = 0.0;
    r3c2 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 2);
    r3c2 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 2);
    r3c2 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 2);
    r3c2 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 2);

    double r0c3 = 0.0;
    r0c3 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 3);
    r0c3 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 3);
    r0c3 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 3);
    r0c3 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 3);

    double r1c3 = 0.0;
    r1c3 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 3);
    r1c3 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 3);
    r1c3 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 3);
    r1c3 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 3);

    double r2c3 = 0.0;
    r2c3 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 3);
    r2c3 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 3);
    r2c3 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 3);
    r2c3 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 3);

    double r3c3 = 0.0;
    r3c3 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 3);
    r3c3 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 3);
    r3c3 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 3);
    r3c3 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 3);

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
   * <p> Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}. </p> <p> Formally, this can be considered
   * to be premultiplication of the column vector {@code v} with the matrix
   * {@code m}. </p>
   *
   * @param m    The input matrix.
   * @param v    The input vector.
   * @param out  The output vector.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <V>  The precise type of writable vector.
   *
   * @return {@code out}
   */

  public static <T0, T1, V extends PVectorWritable4DType<T1>> V
  multiplyVector4D(
    final PMatrixReadable4x4DType<T0, T1> m,
    final PVectorReadable4DType<T0> v,
    final V out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();
    return PMatrixM4x4D.multiplyVector4DActual(m, v, va, vb, out);
  }

  private static <T0, T1, V extends PVectorWritable4DType<T1>> V
  multiplyVector4DActual(
    final PMatrixReadable4x4DType<T0, T1> m,
    final PVectorReadable4DType<T0> v,
    final VectorM4D va,
    final VectorM4D vb,
    final V out)
  {
    vb.copyFrom4D(v);

    PMatrixM4x4D.rowUnsafe(m, 0, va);
    out.setXD(VectorM4D.dotProduct(va, vb));
    PMatrixM4x4D.rowUnsafe(m, 1, va);
    out.setYD(VectorM4D.dotProduct(va, vb));
    PMatrixM4x4D.rowUnsafe(m, 2, va);
    out.setZD(VectorM4D.dotProduct(va, vb));
    PMatrixM4x4D.rowUnsafe(m, 3, va);
    out.setWD(VectorM4D.dotProduct(va, vb));

    return out;
  }

  /**
   * <p> Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}. </p> <p> The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p> <p> Formally,
   * this can be considered to be premultiplication of the column vector {@code
   * v} with the matrix {@code m}. </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param v       The input vector.
   * @param out     The output vector.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <V>     The precise type of writable vector.
   *
   * @return {@code out}
   */

  public static <T0, T1, V extends PVectorWritable4DType<T1>> V
  multiplyVector4DWithContext(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final PVectorReadable4DType<T0> v,
    final V out)
  {
    return PMatrixM4x4D.multiplyVector4DActual(
      m, v, context.getV4a(), context.getV4b(), out);
  }

  /**
   * @param m    The matrix
   * @param row  The row
   * @param out  The output vector
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <V>  The precise type of writable vector.
   *
   * @return Row {@code row} of the matrix {@code m} in the vector {@code out}.
   */

  public static <T0, T1, V extends VectorWritable4DType> V row(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row,
    final V out)
  {
    return PMatrixM4x4D.rowUnsafe(m, PMatrixM4x4D.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM4x4D.VIEW_COLS);
    }
    return row;
  }

  private static <V extends VectorWritable4DType> V rowUnsafe(
    final MatrixReadable4x4DType m,
    final int row,
    final V out)
  {
    out.set4D(
      m.getRowColumnD(row, 0),
      m.getRowColumnD(row, 1),
      m.getRowColumnD(row, 2),
      m.getRowColumnD(row, 3));
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param out  The output matrix.
   * @param m    The input matrix.
   * @param r    The scaling value.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scale(
    final PMatrixReadable4x4DType<T0, T1> m,
    final double r,
    final PMatrixM4x4D<T2, T3> out)
  {
    final double r0c0 = m.getRowColumnD(0, 0) * r;
    final double r1c0 = m.getRowColumnD(1, 0) * r;
    final double r2c0 = m.getRowColumnD(2, 0) * r;
    final double r3c0 = m.getRowColumnD(3, 0) * r;

    final double r0c1 = m.getRowColumnD(0, 1) * r;
    final double r1c1 = m.getRowColumnD(1, 1) * r;
    final double r2c1 = m.getRowColumnD(2, 1) * r;
    final double r3c1 = m.getRowColumnD(3, 1) * r;

    final double r0c2 = m.getRowColumnD(0, 2) * r;
    final double r1c2 = m.getRowColumnD(1, 2) * r;
    final double r2c2 = m.getRowColumnD(2, 2) * r;
    final double r3c2 = m.getRowColumnD(3, 2) * r;

    final double r0c3 = m.getRowColumnD(0, 3) * r;
    final double r1c3 = m.getRowColumnD(1, 3) * r;
    final double r2c3 = m.getRowColumnD(2, 3) * r;
    final double r3c3 = m.getRowColumnD(3, 3) * r;

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
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param m    The input matrix.
   * @param r    The scaling value.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scaleInPlace(
    final PMatrixM4x4D<T0, T1> m,
    final double r)
  {
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p> <p> This is one of the three
   * "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m    The input matrix.
   * @param row  The index of the row {@code 0 <= row < 4}.
   * @param r    The scaling value.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scaleRow(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM4x4D<T2, T3> out)
  {
    final VectorM4D tmp = new VectorM4D();
    return PMatrixM4x4D.scaleRowUnsafe(
      m, PMatrixM4x4D.rowCheck(row), r, tmp, out);
  }

  /**
   * <p> Scale row {@code row} of the matrix {@code m} by {@code r} , saving the
   * result to row {@code r} of {@code m}. </p> <p> This is one of the three
   * "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param m    The input matrix.
   * @param row  The index of the row {@code 0 <= row < 4}.
   * @param r    The scaling value.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scaleRowInPlace(
    final PMatrixM4x4D<T0, T1> m,
    final int row,
    final double r)
  {
    final VectorM4D tmp = new VectorM4D();
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.scaleRowUnsafe(
      m, row, r, tmp, m);
  }

  /**
   * <p> Scale row {@code row} of the matrix {@code m} by {@code r} , saving the
   * result to row {@code r} of {@code m}. The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p> <p> This is one
   * of the three "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param row     The index of the row {@code 0 <= row < 4}.
   * @param r       The scaling value.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <T2>    A phantom type parameter.
   * @param <T3>    A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3>
  scaleRowInPlaceWithContext(
    final ContextPM4D context,
    final PMatrixM4x4D<T0, T1> m,
    final int row,
    final double r)
  {
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.scaleRowUnsafe(
      m, PMatrixM4x4D.rowCheck(row), r, context.getV4a(), m);
  }

  private static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scaleRowUnsafe(
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row,
    final double r,
    final VectorM4D tmp,
    final PMatrixM4x4D<T2, T3> out)
  {
    PMatrixM4x4D.rowUnsafe(m, row, tmp);
    VectorM4D.scaleInPlace(tmp, r);
    PMatrixM4x4D.setRowUnsafe(out, row, tmp);
    return out;
  }

  /**
   * <p> Scale row {@code row} of the matrix {@code m} by {@code r} , saving the
   * result to row {@code r} of {@code out}. The function uses preallocated
   * storage in {@code context} to avoid allocating memory. </p> <p> This is one
   * of the three "elementary" operations defined on matrices. See <a href=
   * "http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations"
   * >Elementary operations</a> . </p>
   *
   * @param context Preallocated storage.
   * @param m       The input matrix.
   * @param row     The index of the row {@code 0 <= row < 4}.
   * @param r       The scaling value.
   * @param out     The output matrix.
   * @param <T0>    A phantom type parameter.
   * @param <T1>    A phantom type parameter.
   * @param <T2>    A phantom type parameter.
   * @param <T3>    A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> scaleRowWithContext(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final int row,
    final double r,
    final PMatrixM4x4D<T2, T3> out)
  {
    return PMatrixM4x4D.scaleRowUnsafe(
      m, PMatrixM4x4D.rowCheck(row), r, context.getV4a(), out);
  }

  /**
   * Set the value in the matrix {@code m} at row {@code row}, column {@code
   * column} to {@code value}.
   *
   * @param m      The matrix
   * @param row    The row
   * @param column The column
   * @param value  The value
   * @param <T0>   A phantom type parameter.
   * @param <T1>   A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1> PMatrixM4x4D<T0, T1> set(
    final PMatrixM4x4D<T0, T1> m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(PMatrixM4x4D.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m    The matrix
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> setIdentity(
    final PMatrixM4x4D<T0, T1> m)
  {
    m.view.clear();

    for (int row = 0; row < PMatrixM4x4D.VIEW_ROWS; ++row) {
      for (int col = 0; col < PMatrixM4x4D.VIEW_COLS; ++col) {
        if (row == col) {
          m.setUnsafe(row, col, 1.0);
        } else {
          m.setUnsafe(row, col, 0.0);
        }
      }
    }

    return (PMatrixM4x4D<T2, T3>) m;
  }

  private static <T0, T1> void setRowUnsafe(
    final PMatrixM4x4D<T0, T1> m,
    final int row,
    final VectorReadable4DType v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
    m.setUnsafe(row, 3, v.getWD());
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m    The matrix
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> setZero(
    final PMatrixM4x4D<T0, T1> m)
  {
    m.view.clear();
    for (int index = 0; index < (PMatrixM4x4D.VIEW_ROWS
                                 * PMatrixM4x4D.VIEW_COLS); ++index) {
      m.view.put(index, 0.0);
    }
    return (PMatrixM4x4D<T2, T3>) m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as the sum
   * of the diagonal elements of the matrix.
   *
   * @param m    The input matrix
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   *
   * @return The trace of the matrix
   */

  public static <T0, T1> double trace(
    final PMatrixReadable4x4DType<T0, T1> m)
  {
    return m.getRowColumnD(0, 0)
           + m.getRowColumnD(1, 1)
           + m.getRowColumnD(2, 2)
           + m.getRowColumnD(3, 3);
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param m    The input matrix.
   * @param out  The output matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> transpose(
    final PMatrixReadable4x4DType<T0, T1> m,
    final PMatrixM4x4D<T0, T1> out)
  {
    PMatrixM4x4D.copy(m, out);
    return (PMatrixM4x4D<T2, T3>) PMatrixM4x4D.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param m    The input matrix.
   * @param <T0> A phantom type parameter.
   * @param <T1> A phantom type parameter.
   * @param <T2> A phantom type parameter.
   * @param <T3> A phantom type parameter.
   *
   * @return {@code m}
   */

  public static <T0, T1, T2, T3> PMatrixM4x4D<T2, T3> transposeInPlace(
    final PMatrixM4x4D<T0, T1> m)
  {
    for (int row = 0; row < (PMatrixM4x4D.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < PMatrixM4x4D.VIEW_COLS; ++column) {
        final double x = m.view.get((row * PMatrixM4x4D.VIEW_ROWS) + column);
        m.view.put(
          (row * PMatrixM4x4D.VIEW_ROWS) + column,
          m.view.get(row + (PMatrixM4x4D.VIEW_COLS * column)));
        m.view.put(row + (PMatrixM4x4D.VIEW_COLS * column), x);
      }
    }

    return (PMatrixM4x4D<T2, T3>) m;
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
    final PMatrixM4x4D<?, ?> other = (PMatrixM4x4D<?, ?>) obj;
    for (int index = 0; index < PMatrixM4x4D.VIEW_ELEMENTS; ++index) {
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

  @Override public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    PMatrixM4x4D.rowUnsafe(this, PMatrixM4x4D.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM4x4D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < PMatrixM4x4D.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  /**
   * Set the value at the given row and column.
   *
   * @param row    The row
   * @param column The column
   * @param value  The value
   *
   * @return {@code this}
   */

  public PMatrixM4x4D<T0, T1> set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM4x4D.indexChecked(row, column), value);
    this.view.clear();
    return this;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM4x4D.indexChecked(row, column), value);
  }

  /**
   * <p> Set the value at row {@code row} and {@code column} to {@code value}
   * without bounds checking. </p> <p> This function is only accessible by code
   * in the same package as this. </p>
   */

  PMatrixM4x4D<T0, T1> setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM4x4D.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < PMatrixM4x4D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 0));
      final double c1 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 1));
      final double c2 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 2));
      final double c3 = this.view.get(PMatrixM4x4D.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.15f %+.15f %+.15f %+.15f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  private interface Phantom2Type
  {
    // Type-level only.
  }

  /**
   * <p> The {@code ContextPM4D} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM4x4D} class. </p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p> The user should allocate one {@code ContextPM4D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM4D} value will not generate garbage. </p>
   *
   * @since 7.0.0
   */

  public static class ContextPM4D
  {
    private final MatrixM3x3D        m3a = new MatrixM3x3D();
    private final PMatrixM4x4D<?, ?> m4a = new PMatrixM4x4D<Object, Object>();
    private final PMatrixM4x4D<?, ?> m4b = new PMatrixM4x4D<Object, Object>();
    private final VectorM3D          v3a = new VectorM3D();
    private final VectorM3D          v3b = new VectorM3D();
    private final VectorM3D          v3c = new VectorM3D();
    private final VectorM3D          v3d = new VectorM3D();
    private final VectorM4D          v4a = new VectorM4D();
    private final VectorM4D          v4b = new VectorM4D();

    /**
     * Construct a new context.
     */

    public ContextPM4D()
    {

    }

    final MatrixM3x3D getM3a()
    {
      return this.m3a;
    }

    final PMatrixM4x4D<?, ?> getM4a()
    {
      return this.m4a;
    }

    final PMatrixM4x4D<?, ?> getM4b()
    {
      return this.m4b;
    }

    final VectorM3D getV3a()
    {
      return this.v3a;
    }

    final VectorM3D getV3b()
    {
      return this.v3b;
    }

    final VectorM3D getV3c()
    {
      return this.v3c;
    }

    final VectorM3D getV3d()
    {
      return this.v3d;
    }

    final VectorM4D getV4a()
    {
      return this.v4a;
    }

    final VectorM4D getV4b()
    {
      return this.v4b;
    }
  }
}
