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
import java.nio.FloatBuffer;

import com.io7m.jaux.functional.Option;

/**
 * A 4x4 mutable matrix type with single precision elements.
 */

public final class MatrixM4x4F
{
  /**
   * The Context type contains the minimum storage required for all of the
   * functions of the MatrixM4x4F class. The purpose of the class is to allow
   * applications to allocate all storage ahead of time in order to allow
   * functions in the class to avoid allocating memory (not including stack
   * space) for intermediate calculations. This can reduce garbage collection
   * in speed critical code.
   */

  public static final class Context
  {
    final MatrixM4x4F m4a = new MatrixM4x4F();
    final VectorM4F   va  = new VectorM4F();
    final VectorM4F   vb  = new VectorM4F();
    final MatrixM3x3F m3a = new MatrixM3x3F();

    public Context()
    {

    }
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

  public static MatrixM4x4F add(
    final MatrixM4x4F m0,
    final MatrixM4x4F m1,
    final MatrixM4x4F out)
  {
    for (int index = 0; index < 16; ++index) {
      out.view.put(index, m0.view.get(index) + m1.view.get(index));
    }
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

  public static MatrixM4x4F addInPlace(
    final MatrixM4x4F m0,
    final MatrixM4x4F m1)
  {
    return MatrixM4x4F.add(m0, m1, m0);
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

  public static MatrixM4x4F addRowScaled(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
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
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>m</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM4x4F addRowScaledInPlace(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r)
  {
    return MatrixM4x4F.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static MatrixM4x4F addRowScaledUnsafe(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final VectorM4F va,
    final VectorM4F vb,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.rowUnsafe(m, row_a, va);
    MatrixM4x4F.rowUnsafe(m, row_b, vb);
    VectorM4F.addScaledInPlace(va, vb, r);
    MatrixM4x4F.setRowUnsafe(out, row_c, va);
    return out;
  }

  /**
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>. The function uses
   * storage preallocated in <code>context</code> to avoid any new
   * allocations.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM4x4F addRowScaledWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final int row_c,
    final float r,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.addRowScaledUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      MatrixM4x4F.rowCheck(row_c),
      r,
      context.va,
      context.vb,
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column > 3)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < 4");
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

  public static MatrixM4x4F copy(
    final MatrixM4x4F input,
    final MatrixM4x4F output)
  {
    for (int index = 0; index < 16; ++index) {
      output.view.put(index, input.view.get(index));
    }
    return output;
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

  public static MatrixM4x4F exchangeRows(
    final MatrixM4x4F m,
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
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>. This
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

  public static MatrixM4x4F exchangeRowsInPlace(
    final MatrixM4x4F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4F.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM4x4F exchangeRowsUnsafe(
    final MatrixM4x4F m,
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
    return out;
  }

  /**
   * Exchange two rows <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
   * This is one of the three "elementary" operations defined on matrices. The
   * function uses storage preallocated in <code>context</code> to avoid
   * allocating memory.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM4x4F exchangeRowsWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row_a,
    final int row_b,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.exchangeRowsUnsafe(
      m,
      MatrixM4x4F.rowCheck(row_a),
      MatrixM4x4F.rowCheck(row_b),
      context.va,
      context.vb,
      out);
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static FloatBuffer floatBuffer(
    final MatrixM4x4F m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asFloatBuffer();
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static float get(
    final MatrixM4x4F m,
    final int row,
    final int column)
  {
    return m.view.get(MatrixM4x4F.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4F.indexUnsafe(
      MatrixM4x4F.rowCheck(row),
      MatrixM4x4F.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage.
   * 
   * @param row
   * @param column
   * @return
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 4) + row;
  }

  private static Option<MatrixM4x4F> invert(
    final MatrixM4x4F m,
    final MatrixM3x3F m3,
    final MatrixM4x4F out)
  {
    final float d = MatrixM4x4F.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM4x4F>();
    }

    final float d_inv = 1 / d;

    /*
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

    float r0c0;
    float r0c1;
    float r0c2;
    float r0c3;

    float r1c0;
    float r1c1;
    float r1c2;
    float r1c3;

    float r2c0;
    float r2c1;
    float r2c2;
    float r2c3;

    float r3c0;
    float r3c1;
    float r3c2;
    float r3c3;

    {
      // Sub-matrix obtained by removing m[0, 0]
      // 1 = (-1) ^ (0 + 0)

      m3.set(0, 0, m.getUnsafe(1, 1));
      m3.set(0, 1, m.getUnsafe(1, 2));
      m3.set(0, 2, m.getUnsafe(1, 3));
      m3.set(1, 0, m.getUnsafe(2, 1));
      m3.set(1, 1, m.getUnsafe(2, 2));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 1));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r0c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.set(0, 0, m.getUnsafe(1, 0));
      m3.set(0, 1, m.getUnsafe(1, 2));
      m3.set(0, 2, m.getUnsafe(1, 3));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 2));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r0c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.set(0, 0, m.getUnsafe(1, 0));
      m3.set(0, 1, m.getUnsafe(1, 1));
      m3.set(0, 2, m.getUnsafe(1, 3));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 1));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r0c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.set(0, 0, m.getUnsafe(1, 0));
      m3.set(0, 1, m.getUnsafe(1, 1));
      m3.set(0, 2, m.getUnsafe(1, 2));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 1));
      m3.set(1, 2, m.getUnsafe(2, 2));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 2));

      r0c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.set(0, 0, m.getUnsafe(0, 1));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(2, 1));
      m3.set(1, 1, m.getUnsafe(2, 2));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 1));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r1c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 2));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r1c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 1));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r1c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 2));
      m3.set(1, 0, m.getUnsafe(2, 0));
      m3.set(1, 1, m.getUnsafe(2, 1));
      m3.set(1, 2, m.getUnsafe(2, 2));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 2));

      r1c3 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.set(0, 0, m.getUnsafe(0, 1));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 1));
      m3.set(1, 1, m.getUnsafe(1, 2));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(3, 1));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r2c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 2));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r2c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 1));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r2c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 2));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 1));
      m3.set(1, 2, m.getUnsafe(1, 2));
      m3.set(2, 0, m.getUnsafe(3, 0));
      m3.set(2, 1, m.getUnsafe(3, 1));
      m3.set(2, 2, m.getUnsafe(3, 2));

      r2c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.set(0, 0, m.getUnsafe(0, 1));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 1));
      m3.set(1, 1, m.getUnsafe(1, 2));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(2, 1));
      m3.set(2, 1, m.getUnsafe(2, 2));
      m3.set(2, 2, m.getUnsafe(2, 3));

      r3c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 2));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 2));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(2, 0));
      m3.set(2, 1, m.getUnsafe(2, 2));
      m3.set(2, 2, m.getUnsafe(2, 3));

      r3c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 3));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 1));
      m3.set(1, 2, m.getUnsafe(1, 3));
      m3.set(2, 0, m.getUnsafe(2, 0));
      m3.set(2, 1, m.getUnsafe(2, 1));
      m3.set(2, 2, m.getUnsafe(2, 3));

      r3c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.set(0, 0, m.getUnsafe(0, 0));
      m3.set(0, 1, m.getUnsafe(0, 1));
      m3.set(0, 2, m.getUnsafe(0, 2));
      m3.set(1, 0, m.getUnsafe(1, 0));
      m3.set(1, 1, m.getUnsafe(1, 1));
      m3.set(1, 2, m.getUnsafe(1, 2));
      m3.set(2, 0, m.getUnsafe(2, 0));
      m3.set(2, 1, m.getUnsafe(2, 1));
      m3.set(2, 2, m.getUnsafe(2, 2));

      r3c3 = MatrixM3x3F.determinant(m3);
    }

    /*
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

    MatrixM4x4F.transposeInPlace(out);
    return new Option.Some<MatrixM4x4F>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM4x4F#determinant(MatrixM4x4F)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM4x4F> invert(
    final MatrixM4x4F m,
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
   * <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM4x4F#determinant(MatrixM4x4F)
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM4x4F> invertInPlace(
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
   * <code>context</code> to avoid allocating memory.
   * 
   * @see com.io7m.jtensors.MatrixM4x4F#determinant(MatrixM4x4F)
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM4x4F> invertInPlaceWithContext(
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
   * <code>context</code> to avoid allocating memory.
   * 
   * @see com.io7m.jtensors.MatrixM4x4F#determinant(MatrixM4x4F)
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM4x4F> invertWithContext(
    final Context context,
    final MatrixM4x4F m,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.invert(m, context.m3a, out);
  }

  /**
   * Generate and return a matrix that represents a rotation of
   * <code>angle</code> radians around the axis <code>axis</code>. The
   * function assumes a right-handed coordinate system.
   * 
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   */

  public static MatrixM4x4F makeRotation(
    final float angle,
    final VectorReadable3F axis)
  {
    final MatrixM4x4F out = new MatrixM4x4F();
    MatrixM4x4F.makeRotation(angle, axis, out);
    return out;
  }

  /**
   * Generate a matrix that represents a rotation of <code>angle</code>
   * radians around the axis <code>axis</code> and save to <code>out</code>.
   * The function assumes a right-handed coordinate system.
   * 
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static MatrixM4x4F makeRotation(
    final float angle,
    final VectorReadable3F axis,
    final MatrixM4x4F out)
  {
    final float ax = axis.getXF();
    final float ay = axis.getYF();
    final float az = axis.getZF();

    final float c = (float) Math.cos(angle);
    final float s = (float) Math.sin(angle);
    final float omc = 1.0f - c;

    final float sx = s * ax;
    final float sy = s * ay;
    final float sz = s * az;

    final float axis_xy = ax * ay;
    final float axis_xz = ax * az;
    final float axis_yz = ay * az;

    /*
     * Right-handed coordinate system. Flip signs where indicated for a
     * left-handed system.
     */

    out.setUnsafe(0, 0, c + (omc * (ax * ax)));
    out.setUnsafe(0, 1, (omc * axis_xy) + sz); // - sz for left-handed.
    out.setUnsafe(0, 2, (omc * axis_xz) - sy); // + sy for left-handed.
    out.setUnsafe(0, 3, 0.0f);

    out.setUnsafe(1, 0, (omc * axis_xy) - sz); // + sz for left-handed.
    out.setUnsafe(1, 1, c + (omc * (ay * ay)));
    out.setUnsafe(1, 2, (omc * axis_yz) + sx); // - sz for left-handed.
    out.setUnsafe(1, 3, 0.0f);

    out.setUnsafe(2, 0, (omc * axis_xz) + sy); // - sy for left-handed.
    out.setUnsafe(2, 1, (omc * axis_yz) - sx); // + sx for left-handed.
    out.setUnsafe(2, 2, c + (omc * (az * az)));
    out.setUnsafe(2, 3, 0.0f);

    out.setUnsafe(3, 0, 0.0f);
    out.setUnsafe(3, 1, 0.0f);
    out.setUnsafe(3, 2, 0.0f);
    out.setUnsafe(3, 3, 1.0f);

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

  public static MatrixM4x4F makeTranslation(
    final VectorReadable3F v)
  {
    final MatrixM4x4F out = new MatrixM4x4F();
    MatrixM4x4F.makeTranslation(v, out);
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

  public static MatrixM4x4F makeTranslation(
    final VectorReadable3F v,
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

  public static MatrixM4x4F multiply(
    final MatrixM4x4F m0,
    final MatrixM4x4F m1,
    final MatrixM4x4F out)
  {
    float r0c0 = 0;
    r0c0 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 0);
    r0c0 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 0);
    r0c0 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 0);
    r0c0 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 0);

    float r1c0 = 0;
    r1c0 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 0);
    r1c0 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 0);
    r1c0 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 0);
    r1c0 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 0);

    float r2c0 = 0;
    r2c0 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 0);
    r2c0 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 0);
    r2c0 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 0);
    r2c0 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 0);

    float r3c0 = 0;
    r3c0 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 0);
    r3c0 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 0);
    r3c0 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 0);
    r3c0 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 0);

    float r0c1 = 0;
    r0c1 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 1);
    r0c1 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 1);
    r0c1 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 1);
    r0c1 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 1);

    float r1c1 = 0;
    r1c1 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 1);
    r1c1 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 1);
    r1c1 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 1);
    r1c1 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 1);

    float r2c1 = 0;
    r2c1 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 1);
    r2c1 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 1);
    r2c1 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 1);
    r2c1 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 1);

    float r3c1 = 0;
    r3c1 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 1);
    r3c1 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 1);
    r3c1 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 1);
    r3c1 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 1);

    float r0c2 = 0;
    r0c2 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 2);
    r0c2 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 2);
    r0c2 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 2);
    r0c2 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 2);

    float r1c2 = 0;
    r1c2 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 2);
    r1c2 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 2);
    r1c2 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 2);
    r1c2 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 2);

    float r2c2 = 0;
    r2c2 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 2);
    r2c2 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 2);
    r2c2 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 2);
    r2c2 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 2);

    float r3c2 = 0;
    r3c2 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 2);
    r3c2 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 2);
    r3c2 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 2);
    r3c2 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 2);

    float r0c3 = 0;
    r0c3 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 3);
    r0c3 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 3);
    r0c3 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 3);
    r0c3 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 3);

    float r1c3 = 0;
    r1c3 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 3);
    r1c3 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 3);
    r1c3 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 3);
    r1c3 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 3);

    float r2c3 = 0;
    r2c3 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 3);
    r2c3 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 3);
    r2c3 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 3);
    r2c3 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 3);

    float r3c3 = 0;
    r3c3 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 3);
    r3c3 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 3);
    r3c3 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 3);
    r3c3 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 3);

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

  private static VectorM4F multiply(
    final MatrixM4x4F m,
    final VectorReadable4F v,
    final VectorM4F va,
    final VectorM4F vb,
    final VectorM4F out)
  {
    vb.x = v.getXF();
    vb.y = v.getYF();
    vb.z = v.getZF();
    vb.w = v.getWF();

    MatrixM4x4F.rowUnsafe(m, 0, va);
    out.x = VectorM4F.dotProduct(va, vb);
    MatrixM4x4F.rowUnsafe(m, 1, va);
    out.y = VectorM4F.dotProduct(va, vb);
    MatrixM4x4F.rowUnsafe(m, 2, va);
    out.z = VectorM4F.dotProduct(va, vb);
    MatrixM4x4F.rowUnsafe(m, 3, va);
    out.w = VectorM4F.dotProduct(va, vb);

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
   * @return <code>m0</code>
   */

  public static MatrixM4x4F multiplyInPlace(
    final MatrixM4x4F m0,
    final MatrixM4x4F m1)
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

  public static VectorM4F multiplyVector4F(
    final MatrixM4x4F m,
    final VectorReadable4F v,
    final VectorM4F out)
  {
    final VectorM4F va = new VectorM4F();
    final VectorM4F vb = new VectorM4F();
    return MatrixM4x4F.multiply(m, v, va, vb, out);
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

  public static VectorM4F multiplyVector4FWithContext(
    final Context context,
    final MatrixM4x4F m,
    final VectorReadable4F v,
    final VectorM4F out)
  {
    return MatrixM4x4F.multiply(m, v, context.va, context.vb, out);
  }

  private static MatrixM4x4F rotate(
    final float angle,
    final MatrixM4x4F m,
    final MatrixM4x4F tmp,
    final VectorReadable3F axis,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.makeRotation(angle, axis, tmp);
    MatrixM4x4F.multiply(m, tmp, out);
    return out;
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>.
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

  public static MatrixM4x4F rotate(
    final float angle,
    final MatrixM4x4F m,
    final VectorReadable3F axis,
    final MatrixM4x4F out)
  {
    final MatrixM4x4F tmp = new MatrixM4x4F();
    return MatrixM4x4F.rotate(angle, m, tmp, axis, out);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>.
   * 
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @return <code>m</code>
   */

  public static MatrixM4x4F rotateInPlace(
    final float angle,
    final MatrixM4x4F m,
    final VectorReadable3F axis)
  {
    final MatrixM4x4F tmp = new MatrixM4x4F();
    return MatrixM4x4F.rotate(angle, m, tmp, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
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

  public static MatrixM4x4F rotateInPlaceWithContext(
    final Context context,
    final float angle,
    final MatrixM4x4F m,
    final VectorReadable3F axis)
  {
    return MatrixM4x4F.rotate(angle, m, context.m4a, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
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

  public static MatrixM4x4F rotateWithContext(
    final Context context,
    final float angle,
    final MatrixM4x4F m,
    final VectorReadable3F axis,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.rotate(angle, m, context.m4a, axis, out);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   * 
   * @param m
   * @param row
   * @param out
   * @return <code>out</code>
   */

  public static VectorM4F row(
    final MatrixM4x4F m,
    final int row,
    final VectorM4F out)
  {
    return MatrixM4x4F.rowUnsafe(m, MatrixM4x4F.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row > 3)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 4");
    }
    return row;
  }

  public static VectorM4F rowUnsafe(
    final MatrixM4x4F m,
    final int row,
    final VectorM4F out)
  {
    out.x = m.getUnsafe(row, 0);
    out.y = m.getUnsafe(row, 1);
    out.z = m.getUnsafe(row, 2);
    out.w = m.getUnsafe(row, 3);
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

  public static MatrixM4x4F scale(
    final MatrixM4x4F m,
    final float r,
    final MatrixM4x4F out)
  {
    for (int index = 0; index < 16; ++index) {
      out.view.put(index, m.view.get(index) * r);
    }
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

  public static MatrixM4x4F scaleInPlace(
    final MatrixM4x4F m,
    final float r)
  {
    return MatrixM4x4F.scale(m, r, m);
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

  public static MatrixM4x4F scaleRow(
    final MatrixM4x4F m,
    final int row,
    final float r,
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

  public static MatrixM4x4F scaleRowInPlace(
    final MatrixM4x4F m,
    final int row,
    final float r)
  {
    final VectorM4F tmp = new VectorM4F();
    return MatrixM4x4F.scaleRowUnsafe(m, row, r, tmp, m);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>. The function
   * uses preallocated storage in <code>context</code> to avoid allocating
   * memory.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param row
   *          The index of the row (0 <= row < 4).
   * @param r
   *          The scaling value.
   * @return <code>m</code>
   */

  public static MatrixM4x4F scaleRowInPlaceWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row,
    final float r)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m,
      MatrixM4x4F.rowCheck(row),
      r,
      context.va,
      m);
  }

  private static MatrixM4x4F scaleRowUnsafe(
    final MatrixM4x4F m,
    final int row,
    final float r,
    final VectorM4F tmp,
    final MatrixM4x4F out)
  {
    MatrixM4x4F.rowUnsafe(m, row, tmp);
    VectorM4F.scaleInPlace(tmp, r);
    MatrixM4x4F.setRowUnsafe(out, row, tmp);
    return out;
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>. The function
   * uses preallocated storage in <code>context</code> to avoid allocating
   * memory.
   * 
   * This is one of the three "elementary" operations defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
   * 
   * @param context
   *          Preallocated storage.
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

  public static MatrixM4x4F scaleRowWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row,
    final float r,
    final MatrixM4x4F out)
  {
    return MatrixM4x4F.scaleRowUnsafe(
      m,
      MatrixM4x4F.rowCheck(row),
      r,
      context.va,
      out);
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static MatrixM4x4F set(
    final MatrixM4x4F m,
    final int row,
    final int column,
    final float value)
  {
    m.view.put(MatrixM4x4F.indexChecked(row, column), value);
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM4x4F setIdentity(
    final MatrixM4x4F m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4F.identity_row_0);
    m.view.put(MatrixM4x4F.identity_row_1);
    m.view.put(MatrixM4x4F.identity_row_2);
    m.view.put(MatrixM4x4F.identity_row_3);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM4x4F m,
    final int row,
    final VectorReadable4F v)
  {
    m.setUnsafe(row, 0, v.getXF());
    m.setUnsafe(row, 1, v.getYF());
    m.setUnsafe(row, 2, v.getZF());
    m.setUnsafe(row, 3, v.getWF());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM4x4F setZero(
    final MatrixM4x4F m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4F.zero_row);
    m.view.put(MatrixM4x4F.zero_row);
    m.view.put(MatrixM4x4F.zero_row);
    m.view.put(MatrixM4x4F.zero_row);
    return m;
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

  public static MatrixM4x4F translateByVector2F(
    final MatrixM4x4F m,
    final VectorReadable2F v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();

    final float c3r0 = (m.getUnsafe(0, 0) * vx) + (m.getUnsafe(0, 1) * vy);
    final float c3r1 = (m.getUnsafe(1, 0) * vx) + (m.getUnsafe(1, 1) * vy);
    final float c3r2 = (m.getUnsafe(2, 0) * vx) + (m.getUnsafe(2, 1) * vy);
    final float c3r3 = (m.getUnsafe(3, 0) * vx) + (m.getUnsafe(3, 1) * vy);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public static MatrixM4x4F translateByVector2FInPlace(
    final MatrixM4x4F m,
    final VectorReadable2F v)
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

  public static MatrixM4x4F translateByVector3F(
    final MatrixM4x4F m,
    final VectorReadable3F v,
    final MatrixM4x4F out)
  {
    final float vx = v.getXF();
    final float vy = v.getYF();
    final float vz = v.getZF();

    final float c3r0 =
      (m.getUnsafe(0, 0) * vx)
        + (m.getUnsafe(0, 1) * vy)
        + (m.getUnsafe(0, 2) * vz);
    final float c3r1 =
      (m.getUnsafe(1, 0) * vx)
        + (m.getUnsafe(1, 1) * vy)
        + (m.getUnsafe(1, 2) * vz);
    final float c3r2 =
      (m.getUnsafe(2, 0) * vx)
        + (m.getUnsafe(2, 1) * vy)
        + (m.getUnsafe(2, 2) * vz);
    final float c3r3 =
      (m.getUnsafe(3, 0) * vx)
        + (m.getUnsafe(3, 1) * vy)
        + (m.getUnsafe(3, 2) * vz);

    out.setUnsafe(0, 3, out.getUnsafe(0, 3) + c3r0);
    out.setUnsafe(1, 3, out.getUnsafe(1, 3) + c3r1);
    out.setUnsafe(2, 3, out.getUnsafe(2, 3) + c3r2);
    out.setUnsafe(3, 3, out.getUnsafe(3, 3) + c3r3);

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

  public static MatrixM4x4F translateByVector3FInPlace(
    final MatrixM4x4F m,
    final VectorReadable3F v)
  {
    return MatrixM4x4F.translateByVector3F(m, v, m);
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

  public static MatrixM4x4F transpose(
    final MatrixM4x4F m,
    final MatrixM4x4F out)
  {
    for (int index = 0; index < 16; ++index) {
      out.view.put(index, m.view.get(index));
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

  public static MatrixM4x4F transposeInPlace(
    final MatrixM4x4F m)
  {
    for (int row = 0; row < (4 - 1); row++) {
      for (int column = row + 1; column < 4; column++) {
        final float x = m.view.get((row * 4) + column);
        m.view.put((row * 4) + column, m.view.get(row + (4 * column)));
        m.view.put(row + (4 * column), x);
      }
    }
    return m;
  }

  private final ByteBuffer     data;

  private final FloatBuffer    view;

  private static final float[] identity_row_0 = { 1.0f, 0.0f, 0.0f, 0.0f };

  private static final float[] identity_row_1 = { 0.0f, 1.0f, 0.0f, 0.0f };

  private static final float[] identity_row_2 = { 0.0f, 0.0f, 1.0f, 0.0f };

  private static final float[] identity_row_3 = { 0.0f, 0.0f, 0.0f, 1.0f };

  private static final float[] zero_row       = { 0.0f, 0.0f, 0.0f, 0.0f };

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static float determinant(
    final MatrixM4x4F m)
  {
    final float r0c0 = m.getUnsafe(0, 0);
    final float r1c0 = m.getUnsafe(1, 0);
    final float r2c0 = m.getUnsafe(2, 0);
    final float r3c0 = m.getUnsafe(3, 0);

    final float r0c1 = m.getUnsafe(0, 1);
    final float r1c1 = m.getUnsafe(1, 1);
    final float r2c1 = m.getUnsafe(2, 1);
    final float r3c1 = m.getUnsafe(3, 1);

    final float r0c2 = m.getUnsafe(0, 2);
    final float r1c2 = m.getUnsafe(1, 2);
    final float r2c2 = m.getUnsafe(2, 2);
    final float r3c2 = m.getUnsafe(3, 2);

    final float r0c3 = m.getUnsafe(0, 3);
    final float r1c3 = m.getUnsafe(1, 3);
    final float r2c3 = m.getUnsafe(2, 3);
    final float r3c3 = m.getUnsafe(3, 3);

    float sum = 0;

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
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>. The
   * function uses storage preallocated in <code>context</code> to avoid
   * allocating memory. This is one of the three "elementary" operations
   * defined on matrices.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary
   *      operations</a>.
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

  public static MatrixM4x4F exchangeRowsInPlaceWithContext(
    final Context context,
    final MatrixM4x4F m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4F.exchangeRowsWithContext(context, m, row_a, row_b, m);
  }

  public MatrixM4x4F()
  {
    this.data =
      ByteBuffer.allocateDirect(4 * 4 * 4).order(ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();
    MatrixM4x4F.setIdentity(this);
  }

  public MatrixM4x4F(
    final MatrixM4x4F source)
  {
    this.data =
      ByteBuffer.allocateDirect(4 * 4 * 4).order(ByteOrder.nativeOrder());
    this.view = this.data.asFloatBuffer();

    for (int index = 0; index < 16; ++index) {
      this.view.put(index, source.view.get(index));
    }
  }

  public float get(
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

  public MatrixM4x4F set(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM4x4F.indexChecked(row, column), value);
    return this;
  }

  private MatrixM4x4F setUnsafe(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixM4x4F.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 4; ++row) {
      final String text =
        String.format(
          "[%.12f\t%.12f\t%.12f\t%.12f]\n",
          Double.valueOf(MatrixM4x4F.get(this, row, 0)),
          Double.valueOf(MatrixM4x4F.get(this, row, 1)),
          Double.valueOf(MatrixM4x4F.get(this, row, 2)),
          Double.valueOf(MatrixM4x4F.get(this, row, 3)));
      builder.append(text);
    }
    return builder.toString();
  }
}
