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
 * A 4x4 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM4x4D</code> are backed by direct memory, with
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

public final class MatrixM4x4D implements
  MatrixDirectReadable4x4DType,
  MatrixWritable4x4DType
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM4x4D</code> class.
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
   */

  public static class Context
  {
    private final MatrixM3x3D m3a = new MatrixM3x3D();
    private final MatrixM4x4D m4a = new MatrixM4x4D();
    private final MatrixM4x4D m4b = new MatrixM4x4D();
    private final VectorM3D   v3a = new VectorM3D();
    private final VectorM3D   v3b = new VectorM3D();
    private final VectorM3D   v3c = new VectorM3D();
    private final VectorM3D   v3d = new VectorM3D();
    private final VectorM4D   v4a = new VectorM4D();
    private final VectorM4D   v4b = new VectorM4D();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final MatrixM3x3D getM3a()
    {
      return this.m3a;
    }

    final MatrixM4x4D getM4a()
    {
      return this.m4a;
    }

    final MatrixM4x4D getM4b()
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

  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM4x4D.VIEW_ROWS * MatrixM4x4D.VIEW_COLS;
    VIEW_BYTES = MatrixM4x4D.VIEW_ELEMENTS * MatrixM4x4D.VIEW_ELEMENT_SIZE;
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

  public static MatrixM4x4D add(
    final MatrixReadable4x4DType m0,
    final MatrixReadable4x4DType m1,
    final MatrixM4x4D out)
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
   * Elementwise add of matrices <code>m0</code> and <code>m1</code>,
   * returning the result in <code>m0</code>.
   *
   * @param m0
   *          The left input matrix.
   * @param m1
   *          The right input matrix.
   * @return m0
   */

  public static MatrixM4x4D addInPlace(
    final MatrixM4x4D m0,
    final MatrixReadable4x4DType m1)
  {
    return MatrixM4x4D.add(m0, m1, m0);
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

  public static MatrixM4x4D addRowScaled(
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM4x4D out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();

    return MatrixM4x4D.addRowScaledUnsafe(
      m,
      MatrixM4x4D.rowCheck(row_a),
      MatrixM4x4D.rowCheck(row_b),
      MatrixM4x4D.rowCheck(row_c),
      r,
      va,
      vb,
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

  public static MatrixM4x4D addRowScaledInPlace(
    final MatrixM4x4D m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM4x4D.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM4x4D addRowScaledUnsafe(
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM4D va,
    final VectorM4D vb,
    final MatrixM4x4D out)
  {
    MatrixM4x4D.rowUnsafe(m, row_a, va);
    MatrixM4x4D.rowUnsafe(m, row_b, vb);
    VectorM4D.addScaledInPlace(va, vb, r);
    MatrixM4x4D.setRowUnsafe(out, row_c, va);

    return out;
  }

  /**
   * <p>
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>. The function uses
   * storage preallocated in <code>context</code> to avoid any new
   * allocations.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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
   * @return <code>out</code>
   */

  public static MatrixM4x4D addRowScaledWithContext(
    final Context context,
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.addRowScaledUnsafe(
      m,
      MatrixM4x4D.rowCheck(row_a),
      MatrixM4x4D.rowCheck(row_b),
      MatrixM4x4D.rowCheck(row_c),
      r,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + MatrixM4x4D.VIEW_COLS);
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

  public static MatrixM4x4D copy(
    final MatrixReadable4x4DType input,
    final MatrixM4x4D output)
  {
    for (int col = 0; col < MatrixM4x4D.VIEW_COLS; ++col) {
      for (int row = 0; row < MatrixM4x4D.VIEW_ROWS; ++row) {
        output.setUnsafe(row, col, input.getRowColumnD(row, col));
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
    final MatrixReadable4x4DType m)
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

  public static MatrixM4x4D exchangeRows(
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final MatrixM4x4D out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();
    return MatrixM4x4D.exchangeRowsUnsafe(
      m,
      MatrixM4x4D.rowCheck(row_a),
      MatrixM4x4D.rowCheck(row_b),
      va,
      vb,
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

  public static MatrixM4x4D exchangeRowsInPlace(
    final MatrixM4x4D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4D.exchangeRows(m, row_a, row_b, m);
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>. The
   * function uses storage preallocated in <code>context</code> to avoid
   * allocating memory.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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
   * @return <code>m</code>
   */

  public static MatrixM4x4D exchangeRowsInPlaceWithContext(
    final Context context,
    final MatrixM4x4D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4D.exchangeRowsWithContext(context, m, row_a, row_b, m);
  }

  private static MatrixM4x4D exchangeRowsUnsafe(
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final VectorM4D va,
    final VectorM4D vb,
    final MatrixM4x4D out)
  {
    MatrixM4x4D.rowUnsafe(m, row_a, va);
    MatrixM4x4D.rowUnsafe(m, row_b, vb);
    MatrixM4x4D.setRowUnsafe(out, row_a, vb);
    MatrixM4x4D.setRowUnsafe(out, row_b, va);

    return out;
  }

  /**
   * <p>
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
   * </p>
   * <p>
   * The function uses storage preallocated in <code>context</code> to avoid
   * allocating memory.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
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
   * @return <code>out</code>
   */

  public static MatrixM4x4D exchangeRowsWithContext(
    final Context context,
    final MatrixReadable4x4DType m,
    final int row_a,
    final int row_b,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.exchangeRowsUnsafe(
      m,
      MatrixM4x4D.rowCheck(row_a),
      MatrixM4x4D.rowCheck(row_b),
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4D.indexUnsafe(
      MatrixM4x4D.rowCheck(row),
      MatrixM4x4D.columnCheck(column));
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
    return (column * MatrixM4x4D.VIEW_COLS) + row;
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>. If the function returns <code>None</code>,
   * <code>m</code> is untouched.
   *
   * @see MatrixM4x4D#determinant(MatrixReadable4x4DType)
   *
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static OptionType<MatrixM4x4D> invert(
    final MatrixReadable4x4DType m,
    final MatrixM4x4D out)
  {
    final MatrixM3x3D m3 = new MatrixM3x3D();
    return MatrixM4x4D.invertActual(m, m3, out);
  }

  private static OptionType<MatrixM4x4D> invertActual(
    final MatrixReadable4x4DType m,
    final MatrixM3x3D m3,
    final MatrixM4x4D out)
  {
    final double d = MatrixM4x4D.determinant(m);

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

    MatrixM4x4D.transposeInPlace(out);

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
   * @see MatrixM4x4D#determinant(MatrixReadable4x4DType)
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static OptionType<MatrixM4x4D> invertInPlace(
    final MatrixM4x4D m)
  {
    return MatrixM4x4D.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(out)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>. The function uses preallocated storage in
   * <code>context</code> to avoid allocating memory. If the function returns
   * <code>None</code>, <code>m</code> is untouched.
   *
   * @see MatrixM4x4D#determinant(MatrixReadable4x4DType)
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static OptionType<MatrixM4x4D> invertInPlaceWithContext(
    final Context context,
    final MatrixM4x4D m)
  {
    return MatrixM4x4D.invertWithContext(context, m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>. The function uses preallocated storage in
   * <code>context</code> to avoid allocating memory. If the function returns
   * <code>None</code>, <code>m</code> is untouched.
   *
   * @see MatrixM4x4D#determinant(MatrixReadable4x4DType)
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static OptionType<MatrixM4x4D> invertWithContext(
    final Context context,
    final MatrixReadable4x4DType m,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.invertActual(m, context.getM3a(), out);
  }

  /**
   * <p>
   * Calculate a matrix representing a "camera" looking from the point
   * <code>origin</code> to the point <code>target</code>. <code>target</code>
   * must represent the "up" vector for the camera. Usually, this is simply a
   * unit vector <code>(0, 1, 0)</code> representing the Y axis.
   * </p>
   * <p>
   * The function uses preallocated storage from <code>context</code>.
   * </p>
   * <p>
   * The view is expressed as a rotation and translation matrix, written to
   * <code>out_matrix</code>.
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
   */

  public static void lookAtWithContext(
    final Context context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final MatrixM4x4D out_matrix)
  {
    final VectorM3D forward = context.getV3a();
    final VectorM3D new_up = context.getV3b();
    final VectorM3D side = context.getV3c();
    final VectorM3D move = context.getV3d();
    final MatrixM4x4D rotation = context.getM4a();
    final MatrixM4x4D translation = context.getM4b();

    MatrixM4x4D.setIdentity(rotation);
    MatrixM4x4D.setIdentity(translation);
    MatrixM4x4D.setIdentity(out_matrix);

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
    MatrixM4x4D.makeTranslation3DInto(move, translation);

    /**
     * Produce output matrix
     */

    MatrixM4x4D.multiply(rotation, translation, out_matrix);
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
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @return A rotation matrix.
   */

  public static MatrixM4x4D makeRotation(
    final double angle,
    final VectorReadable3DType axis)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeRotationInto(angle, axis, out);

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
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeRotationInto(
    final double angle,
    final VectorReadable3DType axis,
    final MatrixM4x4D out)
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
   * Generate and return a matrix that represents a translation of
   * <code>(v.x, v.y)</code> from the origin.
   *
   * @param v
   *          The translation vector.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation2D(
    final VectorReadable2DType v)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeTranslation2DInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * <code>(v.x, v.y)</code> from the origin, and save to <code>out</code>.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation2DInto(
    final VectorReadable2DType v,
    final MatrixM4x4D out)
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
   * Generate and return a matrix that represents a translation of
   * <code>(v.x, v.y)</code> from the origin.
   *
   * @param v
   *          The translation vector.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation2I(
    final VectorReadable2IType v)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeTranslation2IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * <code>(v.x, v.y)</code> from the origin, and save to <code>out</code>.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation2IInto(
    final VectorReadable2IType v,
    final MatrixM4x4D out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, v.getXI());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, v.getYI());

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
   * Generate and return a matrix that represents a translation of
   * <code>(v.x, v.y, v.z)</code> from the origin.
   *
   * @param v
   *          The translation vector.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation3D(
    final VectorReadable3DType v)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeTranslation3DInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * <code>(v.x, v.y, v.z)</code> from the origin, and save to
   * <code>out</code>.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation3DInto(
    final VectorReadable3DType v,
    final MatrixM4x4D out)
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
   * Generate and return a matrix that represents a translation of
   * <code>(v.x, v.y, v.z)</code> from the origin.
   *
   * @param v
   *          The translation vector.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation3I(
    final VectorReadable3IType v)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeTranslation3IInto(v, out);
    return out;
  }

  /**
   * Generate a matrix that represents a translation of
   * <code>(v.x, v.y, v.z)</code> from the origin, and save to
   * <code>out</code>.
   *
   * @param v
   *          The translation vector.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4D makeTranslation3IInto(
    final VectorReadable3IType v,
    final MatrixM4x4D out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, v.getXI());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, v.getYI());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, v.getZI());

    out.setUnsafe(3, 0, 0.0);
    out.setUnsafe(3, 1, 0.0);
    out.setUnsafe(3, 2, 0.0);
    out.setUnsafe(3, 3, 1.0);

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

  public static MatrixM4x4D multiply(
    final MatrixReadable4x4DType m0,
    final MatrixReadable4x4DType m1,
    final MatrixM4x4D out)
  {
    double r0c0 = 0;
    r0c0 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 0);
    r0c0 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 0);
    r0c0 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 0);
    r0c0 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 0);

    double r1c0 = 0;
    r1c0 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 0);
    r1c0 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 0);
    r1c0 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 0);
    r1c0 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 0);

    double r2c0 = 0;
    r2c0 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 0);
    r2c0 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 0);
    r2c0 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 0);
    r2c0 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 0);

    double r3c0 = 0;
    r3c0 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 0);
    r3c0 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 0);
    r3c0 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 0);
    r3c0 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 0);

    double r0c1 = 0;
    r0c1 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 1);
    r0c1 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 1);
    r0c1 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 1);
    r0c1 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 1);

    double r1c1 = 0;
    r1c1 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 1);
    r1c1 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 1);
    r1c1 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 1);
    r1c1 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 1);

    double r2c1 = 0;
    r2c1 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 1);
    r2c1 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 1);
    r2c1 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 1);
    r2c1 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 1);

    double r3c1 = 0;
    r3c1 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 1);
    r3c1 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 1);
    r3c1 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 1);
    r3c1 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 1);

    double r0c2 = 0;
    r0c2 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 2);
    r0c2 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 2);
    r0c2 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 2);
    r0c2 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 2);

    double r1c2 = 0;
    r1c2 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 2);
    r1c2 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 2);
    r1c2 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 2);
    r1c2 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 2);

    double r2c2 = 0;
    r2c2 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 2);
    r2c2 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 2);
    r2c2 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 2);
    r2c2 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 2);

    double r3c2 = 0;
    r3c2 += m0.getRowColumnD(3, 0) * m1.getRowColumnD(0, 2);
    r3c2 += m0.getRowColumnD(3, 1) * m1.getRowColumnD(1, 2);
    r3c2 += m0.getRowColumnD(3, 2) * m1.getRowColumnD(2, 2);
    r3c2 += m0.getRowColumnD(3, 3) * m1.getRowColumnD(3, 2);

    double r0c3 = 0;
    r0c3 += m0.getRowColumnD(0, 0) * m1.getRowColumnD(0, 3);
    r0c3 += m0.getRowColumnD(0, 1) * m1.getRowColumnD(1, 3);
    r0c3 += m0.getRowColumnD(0, 2) * m1.getRowColumnD(2, 3);
    r0c3 += m0.getRowColumnD(0, 3) * m1.getRowColumnD(3, 3);

    double r1c3 = 0;
    r1c3 += m0.getRowColumnD(1, 0) * m1.getRowColumnD(0, 3);
    r1c3 += m0.getRowColumnD(1, 1) * m1.getRowColumnD(1, 3);
    r1c3 += m0.getRowColumnD(1, 2) * m1.getRowColumnD(2, 3);
    r1c3 += m0.getRowColumnD(1, 3) * m1.getRowColumnD(3, 3);

    double r2c3 = 0;
    r2c3 += m0.getRowColumnD(2, 0) * m1.getRowColumnD(0, 3);
    r2c3 += m0.getRowColumnD(2, 1) * m1.getRowColumnD(1, 3);
    r2c3 += m0.getRowColumnD(2, 2) * m1.getRowColumnD(2, 3);
    r2c3 += m0.getRowColumnD(2, 3) * m1.getRowColumnD(3, 3);

    double r3c3 = 0;
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
   * Multiply the matrix <code>m0</code> with the matrix <code>m1</code>,
   * writing the result to <code>m0</code>.
   *
   * @param m0
   *          The left input vector.
   * @param m1
   *          The right input vector.
   * @return <code>out</code>
   */

  public static MatrixM4x4D multiplyInPlace(
    final MatrixM4x4D m0,
    final MatrixReadable4x4DType m1)
  {
    return MatrixM4x4D.multiply(m0, m1, m0);
  }

  /**
   * <p>
   * Multiply the matrix <code>m</code> with the vector <code>v</code>,
   * writing the resulting vector to <code>out</code>.
   * </p>
   * <p>
   * Formally, this can be considered to be premultiplication of the column
   * vector <code>v</code> with the matrix <code>m</code>.
   * </p>
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

  public static <V extends VectorWritable4DType> V multiplyVector4D(
    final MatrixReadable4x4DType m,
    final VectorReadable4DType v,
    final V out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();
    return MatrixM4x4D.multiplyVector4DActual(m, v, va, vb, out);
  }

  private static <V extends VectorWritable4DType> V multiplyVector4DActual(
    final MatrixReadable4x4DType m,
    final VectorReadable4DType v,
    final VectorM4D va,
    final VectorM4D vb,
    final V out)
  {
    vb.copyFrom4D(v);

    MatrixM4x4D.rowUnsafe(m, 0, va);
    out.setXD(VectorM4D.dotProduct(va, vb));
    MatrixM4x4D.rowUnsafe(m, 1, va);
    out.setYD(VectorM4D.dotProduct(va, vb));
    MatrixM4x4D.rowUnsafe(m, 2, va);
    out.setZD(VectorM4D.dotProduct(va, vb));
    MatrixM4x4D.rowUnsafe(m, 3, va);
    out.setWD(VectorM4D.dotProduct(va, vb));

    return out;
  }

  /**
   * <p>
   * Multiply the matrix <code>m</code> with the vector <code>v</code>,
   * writing the resulting vector to <code>out</code>.
   * </p>
   * <p>
   * The function uses preallocated storage in <code>context</code> to avoid
   * allocating memory.
   * </p>
   * <p>
   * Formally, this can be considered to be premultiplication of the column
   * vector <code>v</code> with the matrix <code>m</code>.
   * </p>
   *
   * @param context
   *          Preallocated storage.
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

  public static
    <V extends VectorWritable4DType>
    V
    multiplyVector4DWithContext(
      final Context context,
      final MatrixReadable4x4DType m,
      final VectorReadable4DType v,
      final V out)
  {
    return MatrixM4x4D.multiplyVector4DActual(
      m,
      v,
      context.getV4a(),
      context.getV4b(),
      out);
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

  public static <V extends VectorWritable4DType> V row(
    final MatrixReadable4x4DType m,
    final int row,
    final V out)
  {
    return MatrixM4x4D.rowUnsafe(m, MatrixM4x4D.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM4x4D.VIEW_COLS);
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
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>out</code>.
   *
   * @param out
   *          The output matrix.
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM4x4D scale(
    final MatrixReadable4x4DType m,
    final double r,
    final MatrixM4x4D out)
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
   * Scale all elements of the matrix <code>m</code> by the scaling value
   * <code>r</code>, saving the result in <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM4x4D scaleInPlace(
    final MatrixM4x4D m,
    final double r)
  {
    return MatrixM4x4D.scale(m, r, m);
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

  public static MatrixM4x4D scaleRow(
    final MatrixReadable4x4DType m,
    final int row,
    final double r,
    final MatrixM4x4D out)
  {
    final VectorM4D tmp = new VectorM4D();
    return MatrixM4x4D.scaleRowUnsafe(
      m,
      MatrixM4x4D.rowCheck(row),
      r,
      tmp,
      out);
  }

  /**
   * <p>
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>.
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
   * @return <code>out</code>
   */

  public static MatrixM4x4D scaleRowInPlace(
    final MatrixM4x4D m,
    final int row,
    final double r)
  {
    final VectorM4D tmp = new VectorM4D();
    return MatrixM4x4D.scaleRowUnsafe(m, row, r, tmp, m);
  }

  /**
   * <p>
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>. The function
   * uses preallocated storage in <code>context</code> to avoid allocating
   * memory.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row {@code (0 <= row < 4)}.
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM4x4D scaleRowInPlaceWithContext(
    final Context context,
    final MatrixM4x4D m,
    final int row,
    final double r)
  {
    return MatrixM4x4D.scaleRowUnsafe(
      m,
      MatrixM4x4D.rowCheck(row),
      r,
      context.getV4a(),
      m);
  }

  private static MatrixM4x4D scaleRowUnsafe(
    final MatrixReadable4x4DType m,
    final int row,
    final double r,
    final VectorM4D tmp,
    final MatrixM4x4D out)
  {
    MatrixM4x4D.rowUnsafe(m, row, tmp);
    VectorM4D.scaleInPlace(tmp, r);
    MatrixM4x4D.setRowUnsafe(out, row, tmp);

    return out;
  }

  /**
   * <p>
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory.
   * </p>
   *
   * <p>
   * This is one of the three <i>elementary</i> operations defined on matrices.
   * </p>
   *
   * @param context
   *          Preallocated storage.
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

  public static MatrixM4x4D scaleRowWithContext(
    final Context context,
    final MatrixReadable4x4DType m,
    final int row,
    final double r,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.scaleRowUnsafe(
      m,
      MatrixM4x4D.rowCheck(row),
      r,
      context.getV4a(),
      out);
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

  public static MatrixM4x4D set(
    final MatrixM4x4D m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM4x4D.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   *
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM4x4D setIdentity(
    final MatrixM4x4D m)
  {
    m.view.clear();

    for (int row = 0; row < MatrixM4x4D.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM4x4D.VIEW_COLS; ++col) {
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
    final MatrixM4x4D m,
    final int row,
    final VectorReadable4DType v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
    m.setUnsafe(row, 3, v.getWD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   *
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public static MatrixM4x4D setZero(
    final MatrixM4x4D m)
  {
    m.view.clear();
    for (int index = 0; index < (MatrixM4x4D.VIEW_ROWS * MatrixM4x4D.VIEW_COLS); ++index) {
      m.view.put(index, 0.0);
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
    final MatrixReadable4x4DType m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2)
      + m.getRowColumnD(3, 3);
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

  public static MatrixM4x4D transpose(
    final MatrixReadable4x4DType m,
    final MatrixM4x4D out)
  {
    MatrixM4x4D.copy(m, out);
    return MatrixM4x4D.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   *
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static MatrixM4x4D transposeInPlace(
    final MatrixM4x4D m)
  {
    for (int row = 0; row < (MatrixM4x4D.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < MatrixM4x4D.VIEW_COLS; ++column) {
        final double x = m.view.get((row * MatrixM4x4D.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM4x4D.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM4x4D.VIEW_COLS * column)));
        m.view.put(row + (MatrixM4x4D.VIEW_COLS * column), x);
      }
    }

    return m;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM4x4D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();
    MatrixM4x4D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source
   *          The source matrix.
   */

  public MatrixM4x4D(
    final MatrixReadable4x4DType source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    for (int row = 0; row < MatrixM4x4D.VIEW_ROWS; ++row) {
      for (int col = 0; col < MatrixM4x4D.VIEW_COLS; ++col) {
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
    final MatrixM4x4D other = (MatrixM4x4D) obj;

    for (int index = 0; index < MatrixM4x4D.VIEW_ELEMENTS; ++index) {
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
    MatrixM4x4D.rowUnsafe(this, MatrixM4x4D.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM4x4D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM4x4D.VIEW_ELEMENTS; ++index) {
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

  public MatrixM4x4D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4D.indexChecked(row, column), value);
    return this;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4D.indexChecked(row, column), value);
  }

  /**
   * <p>
   * Set the value at row <code>row</code> and <code>column</code> to
   * <code>value</code> without bounds checking.
   * </p>
   * <p>
   * This function is only accessible by code in the same package as this.
   * </p>
   */

  MatrixM4x4D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4D.indexUnsafe(row, column), value);
    return this;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM4x4D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(MatrixM4x4D.indexUnsafe(row, 0));
      final double c1 = this.view.get(MatrixM4x4D.indexUnsafe(row, 1));
      final double c2 = this.view.get(MatrixM4x4D.indexUnsafe(row, 2));
      final double c3 = this.view.get(MatrixM4x4D.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.15f %+.15f %+.15f %+.15f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
