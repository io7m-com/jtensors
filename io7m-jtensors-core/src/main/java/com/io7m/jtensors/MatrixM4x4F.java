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
 * A 4x4 mutable matrix type with single precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM4x4F</code> are backed by direct memory, with
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

public class MatrixM4x4F implements MatrixReadable4x4FType
{
  /**
   * <p>
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM4x4F</code> class.
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
    private final MatrixM3x3F m3a = new MatrixM3x3F();
    private final MatrixM4x4F m4a = new MatrixM4x4F();
    private final MatrixM4x4F m4b = new MatrixM4x4F();
    private final VectorM3F   v3a = new VectorM3F();
    private final VectorM3F   v3b = new VectorM3F();
    private final VectorM3F   v3c = new VectorM3F();
    private final VectorM3F   v3d = new VectorM3F();
    private final VectorM4F   v4a = new VectorM4F();
    private final VectorM4F   v4b = new VectorM4F();

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

    final MatrixM4x4F getM4a()
    {
      return this.m4a;
    }

    final MatrixM4x4F getM4b()
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

  private static final float[] IDENTITY_ROW_0;
  private static final float[] IDENTITY_ROW_1;
  private static final float[] IDENTITY_ROW_2;
  private static final float[] IDENTITY_ROW_3;
  private static final int     VIEW_BYTES;
  private static final int     VIEW_COLS;
  private static final int     VIEW_ELEMENT_SIZE;
  private static final int     VIEW_ELEMENTS;
  private static final int     VIEW_ROWS;
  private static final float[] ZERO_ROW;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixM4x4F.VIEW_ROWS * MatrixM4x4F.VIEW_COLS;
    VIEW_BYTES = MatrixM4x4F.VIEW_ELEMENTS * MatrixM4x4F.VIEW_ELEMENT_SIZE;

    IDENTITY_ROW_0 = new float[4];
    MatrixM4x4F.IDENTITY_ROW_0[0] = 1.0f;
    MatrixM4x4F.IDENTITY_ROW_0[1] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_0[2] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_0[3] = 0.0f;

    IDENTITY_ROW_1 = new float[4];
    MatrixM4x4F.IDENTITY_ROW_1[0] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_1[1] = 1.0f;
    MatrixM4x4F.IDENTITY_ROW_1[2] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_1[3] = 0.0f;

    IDENTITY_ROW_2 = new float[4];
    MatrixM4x4F.IDENTITY_ROW_2[0] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_2[1] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_2[2] = 1.0f;
    MatrixM4x4F.IDENTITY_ROW_2[3] = 0.0f;

    IDENTITY_ROW_3 = new float[4];
    MatrixM4x4F.IDENTITY_ROW_3[0] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_3[1] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_3[2] = 0.0f;
    MatrixM4x4F.IDENTITY_ROW_3[3] = 1.0f;

    ZERO_ROW = new float[4];
    MatrixM4x4F.ZERO_ROW[0] = 0.0f;
    MatrixM4x4F.ZERO_ROW[1] = 0.0f;
    MatrixM4x4F.ZERO_ROW[2] = 0.0f;
    MatrixM4x4F.ZERO_ROW[3] = 0.0f;
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

  public final static MatrixM4x4F add(
    final MatrixReadable4x4FType m0,
    final MatrixReadable4x4FType m1,
    final MatrixM4x4F out)
  {
    final FloatBuffer m0_view = m0.getFloatBuffer();
    final FloatBuffer m1_view = m1.getFloatBuffer();

    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
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
   * @return m0
   */

  public final static MatrixM4x4F addInPlace(
    final MatrixM4x4F m0,
    final MatrixReadable4x4FType m1)
  {
    return MatrixM4x4F.add(m0, m1, m0);
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

  public final static MatrixM4x4F addRowScaled(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM4x4F out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();

    return MatrixM4x4F.addRowScaledUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      MatrixM4x4F.rowCheck(row_c),
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

  public final static MatrixM4x4F addRowScaledInPlace(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM4x4F.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM4x4F addRowScaledUnsafe(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM4F va,
    final VectorM4F vb,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.rowUnsafe(m, row_a, va);
    MatrixM4x4F.rowUnsafe(m, row_b, vb);
    VectorM4F.addScaledInPlace(va, vb, r);
    MatrixM4x4F.setRowUnsafe(out, row_c, va);

    out.view.rewind();
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

  public final static MatrixM4x4F addRowScaledWithContext(
    final Context context,
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.addRowScaledUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      MatrixM4x4F.rowCheck(row_c),
      r,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + MatrixM4x4F.VIEW_COLS);
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

  public final static MatrixM4x4F copy(
    final MatrixReadable4x4FType input,
    final MatrixM4x4F output)
  {
    final FloatBuffer input_view = input.getFloatBuffer();
    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
      output.view.put(index, input_view.get(index));
    }

    output.view.rewind();
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @return The determinant.
   * @param m
   *          The input matrix.
   */

  public final static double determinant(
    final MatrixReadable4x4FType m)
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

  public final static MatrixM4x4F exchangeRows(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final MatrixM4x4F out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();
    return MatrixM4x4F.exchangeRowsUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
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

  public final static MatrixM4x4F exchangeRowsInPlace(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4F.exchangeRows(m, row_a, row_b, m);
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

  public final static MatrixM4x4F exchangeRowsInPlaceWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4F.exchangeRowsWithContext(context, m, row_a, row_b, m);
  }

  private static MatrixM4x4F exchangeRowsUnsafe(
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final VectorM4F va,
    final VectorM4F vb,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.rowUnsafe(m, row_a, va);
    MatrixM4x4F.rowUnsafe(m, row_b, vb);
    MatrixM4x4F.setRowUnsafe(out, row_a, vb);
    MatrixM4x4F.setRowUnsafe(out, row_b, va);

    out.view.rewind();
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

  public final static MatrixM4x4F exchangeRowsWithContext(
    final Context context,
    final MatrixReadable4x4FType m,
    final int row_a,
    final int row_b,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.exchangeRowsUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      context.getV4a(),
      context.getV4b(),
      out);
  }

  /**
   * @return A view of the buffer that backs this matrix.
   * @param m
   *          The input matrix.
   */

  public final static FloatBuffer floatBuffer(
    final MatrixM4x4F m)
  {
    return m.view;
  }

  /**
   * @return The value from the matrix <code>m</code> at row <code>row</code>,
   *         column <code>column</code>.
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param column
   *          The column
   */

  public final static float get(
    final MatrixReadable4x4FType m,
    final int row,
    final int column)
  {
    final FloatBuffer m_view = m.getFloatBuffer();
    return m_view.get(MatrixM4x4F.indexChecked(row, column));
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4F.indexUnsafe(
      MatrixM4x4F.rowCheck(row),
      MatrixM4x4F.columnCheck(column));
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
    return (column * MatrixM4x4F.VIEW_COLS) + row;
  }

  private static OptionType<MatrixM4x4F> invert(
    final MatrixReadable4x4FType m,
    final MatrixM3x3F m3,
    final MatrixM4x4F out)
  {
    final double d = MatrixM4x4F.determinant(m);

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

    double r0c0;
    double r0c1;
    double r0c2;
    double r0c3;

    double r1c0;
    double r1c1;
    double r1c2;
    double r1c3;

    double r2c0;
    double r2c1;
    double r2c2;
    double r2c3;

    double r3c0;
    double r3c1;
    double r3c2;
    double r3c3;

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

    MatrixM4x4F.transposeInPlace(out);

    out.view.rewind();
    return Option.some(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>. If the function returns <code>None</code>,
   * <code>m</code> is untouched.
   * 
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>.
   */

  public final static OptionType<MatrixM4x4F> invert(
    final MatrixReadable4x4FType m,
    final MatrixM4x4F out)
  {
    final MatrixM3x3F m3 = new MatrixM3x3F();
    return MatrixM4x4F.invert(m, m3, out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>. If the function returns <code>None</code>, <code>m</code>
   * is untouched.
   * 
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>.
   */

  public final static OptionType<MatrixM4x4F> invertInPlace(
    final MatrixM4x4F m)
  {
    return MatrixM4x4F.invert(m, m);
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
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   * 
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public final static OptionType<MatrixM4x4F> invertInPlaceWithContext(
    final Context context,
    final MatrixM4x4F m)
  {
    return MatrixM4x4F.invertWithContext(context, m, m);
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
   * @see MatrixM4x4F#determinant(MatrixReadable4x4FType)
   * 
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public final static OptionType<MatrixM4x4F> invertWithContext(
    final Context context,
    final MatrixReadable4x4FType m,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.invert(m, context.getM3a(), out);
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

  public final static void lookAtWithContext(
    final Context context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final MatrixM4x4F out_matrix)
  {
    final VectorM3F forward = context.getV3a();
    final VectorM3F new_up = context.getV3b();
    final VectorM3F side = context.getV3c();
    final VectorM3F move = context.getV3d();
    final MatrixM4x4F rotation = context.getM4a();
    final MatrixM4x4F translation = context.getM4b();

    MatrixM4x4F.setIdentity(rotation);
    MatrixM4x4F.setIdentity(translation);
    MatrixM4x4F.setIdentity(out_matrix);

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
    MatrixM4x4F.translateByVector3FInPlace(translation, move);

    /**
     * Produce output matrix
     */

    MatrixM4x4F.multiply(rotation, translation, out_matrix);
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
   * @return A rotation matrix.
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   */

  public final static MatrixM4x4F makeRotation(
    final double angle,
    final VectorReadable3FType axis)
  {
    final MatrixM4x4F out = new MatrixM4x4F();
    MatrixM4x4F.makeRotation(angle, axis, out);

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
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public final static MatrixM4x4F makeRotation(
    final double angle,
    final VectorReadable3FType axis,
    final MatrixM4x4F out)
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

    out.view.rewind();
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

  public final static MatrixM4x4F makeTranslation3F(
    final VectorReadable3FType v)
  {
    final MatrixM4x4F out = new MatrixM4x4F();
    MatrixM4x4F.makeTranslation3F(v, out);

    out.view.rewind();
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

  public final static MatrixM4x4F makeTranslation3F(
    final VectorReadable3FType v,
    final MatrixM4x4F out)
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

    out.view.rewind();
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

  public final static MatrixM4x4F makeTranslation3I(
    final VectorReadable3IType v)
  {
    final MatrixM4x4F out = new MatrixM4x4F();
    MatrixM4x4F.makeTranslation3I(v, out);

    out.view.rewind();
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

  public final static MatrixM4x4F makeTranslation3I(
    final VectorReadable3IType v,
    final MatrixM4x4F out)
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

  public final static MatrixM4x4F multiply(
    final MatrixReadable4x4FType m0,
    final MatrixReadable4x4FType m1,
    final MatrixM4x4F out)
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

  public final static MatrixM4x4F multiplyInPlace(
    final MatrixM4x4F m0,
    final MatrixReadable4x4FType m1)
  {
    return MatrixM4x4F.multiply(m0, m1, m0);
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

  public final static VectorM4F multiplyVector4F(
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final VectorM4F out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();
    return MatrixM4x4F.multiplyVector4F(m, v, va, vb, out);
  }

  private static VectorM4F multiplyVector4F(
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final VectorM4F va,
    final VectorM4F vb,
    final VectorM4F out)
  {
    VectorM4F.copy(v, vb);

    MatrixM4x4F.rowUnsafe(m, 0, va);
    out.setXF((float) VectorM4F.dotProduct(va, vb));
    MatrixM4x4F.rowUnsafe(m, 1, va);
    out.setYF((float) VectorM4F.dotProduct(va, vb));
    MatrixM4x4F.rowUnsafe(m, 2, va);
    out.setZF((float) VectorM4F.dotProduct(va, vb));
    MatrixM4x4F.rowUnsafe(m, 3, va);
    out.setWF((float) VectorM4F.dotProduct(va, vb));

    return out;
  }

  /**
   * Multiply the matrix <code>m</code> with the vector <code>v</code>,
   * writing the resulting vector to <code>out</code>. The function uses
   * preallocated storage in <code>context</code> to avoid allocating memory.
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
   */

  public final static VectorM4F multiplyVector4FWithContext(
    final Context context,
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final VectorM4F out)
  {
    return MatrixM4x4F.multiplyVector4F(
      m,
      v,
      context.getV4a(),
      context.getV4b(),
      out);
  }

  private static MatrixM4x4F rotate(
    final double angle,
    final MatrixReadable4x4FType m,
    final MatrixM4x4F tmp,
    final VectorReadable3FType axis,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.makeRotation(angle, axis, tmp);
    MatrixM4x4F.multiply(m, tmp, out);

    out.view.rewind();
    return out;
  }

  /**
   * <p>
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
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

  public final static MatrixM4x4F rotate(
    final double angle,
    final MatrixReadable4x4FType m,
    final VectorReadable3FType axis,
    final MatrixM4x4F out)
  {
    final MatrixM4x4F tmp = new MatrixM4x4F();
    return MatrixM4x4F.rotate(angle, m, tmp, axis, out);
  }

  /**
   * <p>
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @return <code>m</code>
   */

  public final static MatrixM4x4F rotateInPlace(
    final double angle,
    final MatrixM4x4F m,
    final VectorReadable3FType axis)
  {
    final MatrixM4x4F tmp = new MatrixM4x4F();
    return MatrixM4x4F.rotate(angle, m, tmp, axis, m);
  }

  /**
   * <p>
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
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

  public final static MatrixM4x4F rotateInPlaceWithContext(
    final Context context,
    final double angle,
    final MatrixM4x4F m,
    final VectorReadable3FType axis)
  {
    return MatrixM4x4F.rotate(angle, m, context.getM4a(), axis, m);
  }

  /**
   * <p>
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory.
   * </p>
   * <p>
   * The function assumes a right-handed coordinate system and therefore a
   * positive rotation around any axis represents a counter-clockwise rotation
   * around that axis.
   * </p>
   * 
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

  public final static MatrixM4x4F rotateWithContext(
    final Context context,
    final double angle,
    final MatrixReadable4x4FType m,
    final VectorReadable3FType axis,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.rotate(angle, m, context.getM4a(), axis, out);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   * 
   * @param row
   *          The row
   * @param m
   *          The matrix
   * @param out
   *          The output vector
   * @return out
   */

  public final static VectorM4F row(
    final MatrixReadable4x4FType m,
    final int row,
    final VectorM4F out)
  {
    return MatrixM4x4F.rowUnsafe(m, MatrixM4x4F.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM4x4F.VIEW_COLS);
    }
    return row;
  }

  private static VectorM4F rowUnsafe(
    final MatrixReadable4x4FType m,
    final int row,
    final VectorM4F out)
  {
    out.set4F(
      m.getRowColumnF(row, 0),
      m.getRowColumnF(row, 1),
      m.getRowColumnF(row, 2),
      m.getRowColumnF(row, 3));
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
   * @return <code>out</code>
   */

  public final static MatrixM4x4F scale(
    final MatrixReadable4x4FType m,
    final double r,
    final MatrixM4x4F out)
  {
    final FloatBuffer m_view = m.getFloatBuffer();
    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
      out.view.put(index, (float) (m_view.get(index) * r));
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

  public final static MatrixM4x4F scaleInPlace(
    final MatrixM4x4F m,
    final double r)
  {
    return MatrixM4x4F.scale(m, r, m);
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

  public final static MatrixM4x4F scaleRow(
    final MatrixReadable4x4FType m,
    final int row,
    final double r,
    final MatrixM4x4F out)
  {
    final VectorM4F tmp = new VectorM4F();
    return MatrixM4x4F.scaleRowUnsafe(
      m,
      MatrixM4x4F.rowCheck(row),
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

  public final static MatrixM4x4F scaleRowInPlace(
    final MatrixM4x4F m,
    final int row,
    final double r)
  {
    final VectorM4F tmp = new VectorM4F();
    return MatrixM4x4F.scaleRowUnsafe(m, row, r, tmp, m);
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

  public final static MatrixM4x4F scaleRowInPlaceWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row,
    final double r)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m,
      MatrixM4x4F.rowCheck(row),
      r,
      context.getV4a(),
      m);
  }

  private static MatrixM4x4F scaleRowUnsafe(
    final MatrixReadable4x4FType m,
    final int row,
    final double r,
    final VectorM4F tmp,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.rowUnsafe(m, row, tmp);
    VectorM4F.scaleInPlace(tmp, r);
    MatrixM4x4F.setRowUnsafe(out, row, tmp);

    out.view.rewind();
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

  public final static MatrixM4x4F scaleRowWithContext(
    final Context context,
    final MatrixReadable4x4FType m,
    final int row,
    final double r,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m,
      MatrixM4x4F.rowCheck(row),
      r,
      context.getV4a(),
      out);
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   * 
   * @return <code>m</code>
   * @param m
   *          The input matrix
   * @param row
   *          The row
   * @param column
   *          The column
   * @param value
   *          The value
   */

  public final static MatrixM4x4F set(
    final MatrixM4x4F m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM4x4F.indexChecked(row, column), value);
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

  public final static MatrixM4x4F setIdentity(
    final MatrixM4x4F m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4F.IDENTITY_ROW_0);
    m.view.put(MatrixM4x4F.IDENTITY_ROW_1);
    m.view.put(MatrixM4x4F.IDENTITY_ROW_2);
    m.view.put(MatrixM4x4F.IDENTITY_ROW_3);
    m.view.rewind();
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM4x4F m,
    final int row,
    final VectorReadable4FType v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.setUnsafe(row, 2, v.getZF());
    m.setUnsafe(row, 3, v.getWF());
    m.view.rewind();
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @param m
   *          The matrix
   * @return <code>m</code>
   */

  public final static MatrixM4x4F setZero(
    final MatrixM4x4F m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4F.ZERO_ROW);
    m.view.put(MatrixM4x4F.ZERO_ROW);
    m.view.put(MatrixM4x4F.ZERO_ROW);
    m.view.put(MatrixM4x4F.ZERO_ROW);
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
    final MatrixReadable4x4FType m)
  {
    return m.getRowColumnF(0, 0)
      + m.getRowColumnF(1, 1)
      + m.getRowColumnF(2, 2)
      + m.getRowColumnF(3, 3);
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

  public final static MatrixM4x4F translateByVector2F(
    final MatrixReadable4x4FType m,
    final VectorReadable2FType v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();

    final float c3r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c3r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c3r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);
    final float c3r3 =
      (m.getRowColumnF(3, 0) * vx) + (m.getRowColumnF(3, 1) * vy);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public final static MatrixM4x4F translateByVector2FInPlace(
    final MatrixM4x4F m,
    final VectorReadable2FType v)
  {
    return MatrixM4x4F.translateByVector2F(m, v, m);
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

  public final static MatrixM4x4F translateByVector2I(
    final MatrixReadable4x4FType m,
    final VectorReadable2IType v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXI();
    final float vy = v.getYI();

    final float c3r0 =
      (m.getRowColumnF(0, 0) * vx) + (m.getRowColumnF(0, 1) * vy);
    final float c3r1 =
      (m.getRowColumnF(1, 0) * vx) + (m.getRowColumnF(1, 1) * vy);
    final float c3r2 =
      (m.getRowColumnF(2, 0) * vx) + (m.getRowColumnF(2, 1) * vy);
    final float c3r3 =
      (m.getRowColumnF(3, 0) * vx) + (m.getRowColumnF(3, 1) * vy);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public final static MatrixM4x4F translateByVector2IInPlace(
    final MatrixM4x4F m,
    final VectorReadable2IType v)
  {
    return MatrixM4x4F.translateByVector2I(m, v, m);
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

  public final static MatrixM4x4F translateByVector3F(
    final MatrixReadable4x4FType m,
    final VectorReadable3FType v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();
    final float vz = v.getZF();

    final float c3r0 =
      (m.getRowColumnF(0, 0) * vx)
        + (m.getRowColumnF(0, 1) * vy)
        + (m.getRowColumnF(0, 2) * vz);
    final float c3r1 =
      (m.getRowColumnF(1, 0) * vx)
        + (m.getRowColumnF(1, 1) * vy)
        + (m.getRowColumnF(1, 2) * vz);
    final float c3r2 =
      (m.getRowColumnF(2, 0) * vx)
        + (m.getRowColumnF(2, 1) * vy)
        + (m.getRowColumnF(2, 2) * vz);
    final float c3r3 =
      (m.getRowColumnF(3, 0) * vx)
        + (m.getRowColumnF(3, 1) * vy)
        + (m.getRowColumnF(3, 2) * vz);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public final static MatrixM4x4F translateByVector3FInPlace(
    final MatrixM4x4F m,
    final VectorReadable3FType v)
  {
    return MatrixM4x4F.translateByVector3F(m, v, m);
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

  public final static MatrixM4x4F translateByVector3I(
    final MatrixReadable4x4FType m,
    final VectorReadable3IType v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXI();
    final float vy = v.getYI();
    final float vz = v.getZI();

    final float c3r0 =
      (m.getRowColumnF(0, 0) * vx)
        + (m.getRowColumnF(0, 1) * vy)
        + (m.getRowColumnF(0, 2) * vz);
    final float c3r1 =
      (m.getRowColumnF(1, 0) * vx)
        + (m.getRowColumnF(1, 1) * vy)
        + (m.getRowColumnF(1, 2) * vz);
    final float c3r2 =
      (m.getRowColumnF(2, 0) * vx)
        + (m.getRowColumnF(2, 1) * vy)
        + (m.getRowColumnF(2, 2) * vz);
    final float c3r3 =
      (m.getRowColumnF(3, 0) * vx)
        + (m.getRowColumnF(3, 1) * vy)
        + (m.getRowColumnF(3, 2) * vz);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public final static MatrixM4x4F translateByVector3IInPlace(
    final MatrixM4x4F m,
    final VectorReadable3IType v)
  {
    return MatrixM4x4F.translateByVector3I(m, v, m);
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

  public final static MatrixM4x4F transpose(
    final MatrixReadable4x4FType m,
    final MatrixM4x4F out)
  {
    final FloatBuffer m_view = m.getFloatBuffer();
    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
      out.view.put(index, m_view.get(index));
    }
    return MatrixM4x4F.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public final static MatrixM4x4F transposeInPlace(
    final MatrixM4x4F m)
  {
    for (int row = 0; row < (MatrixM4x4F.VIEW_ROWS - 1); ++row) {
      for (int column = row + 1; column < MatrixM4x4F.VIEW_COLS; ++column) {
        final float x = m.view.get((row * MatrixM4x4F.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM4x4F.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM4x4F.VIEW_COLS * column)));
        m.view.put(row + (MatrixM4x4F.VIEW_COLS * column), x);
      }
    }

    m.view.rewind();
    return m;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM4x4F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;

    MatrixM4x4F.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   * 
   * @param source
   *          The source matrix.
   */

  public MatrixM4x4F(
    final MatrixM4x4F source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM4x4F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;
    b.order(order);

    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;

    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source.view.get(index));
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
    final MatrixM4x4F other = (MatrixM4x4F) obj;

    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
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

  public final float get(
    final int row,
    final int column)
  {
    return MatrixM4x4F.get(this, row, column);
  }

  @Override public final FloatBuffer getFloatBuffer()
  {
    return this.view;
  }

  @Override public final void getRow4F(
    final int row,
    final VectorM4F out)
  {
    MatrixM4x4F.rowUnsafe(this, MatrixM4x4F.rowCheck(row), out);
  }

  @Override public final float getRowColumnF(
    final int row,
    final int column)
  {
    return MatrixM4x4F.get(this, row, column);
  }

  private float getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM4x4F.indexUnsafe(row, column));
  }

  @Override public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM4x4F.VIEW_ELEMENTS; ++index) {
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

  public final MatrixM4x4F set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM4x4F.indexChecked(row, column), value);
    this.view.rewind();
    return this;
  }

  /**
   * Set the value at row <code>row</code> and <code>column</code> to
   * <code>value</code> without bounds checking.
   * 
   * This function is only accessible by code in the same package as this.
   */

  final MatrixM4x4F setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM4x4F.VIEW_ROWS; ++row) {
      final String text =
        String.format(
          "[%.15f\t%.15f\t%.15f\t%.15f]\n",
          Double.valueOf(MatrixM4x4F.get(this, row, 0)),
          Double.valueOf(MatrixM4x4F.get(this, row, 1)),
          Double.valueOf(MatrixM4x4F.get(this, row, 2)),
          Double.valueOf(MatrixM4x4F.get(this, row, 3)));
      builder.append(text);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
