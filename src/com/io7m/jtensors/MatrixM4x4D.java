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

import com.io7m.jaux.functional.Option;

/**
 * A 4x4 mutable matrix type with double precision elements.
 */

public final class MatrixM4x4D
{
  /**
   * The Context type contains the minimum storage required for all of the
   * functions of the MatrixM4x4D class. The purpose of the class is to allow
   * applications to allocate all storage ahead of time in order to allow
   * functions in the class to avoid allocating memory (not including stack
   * space) for intermediate calculations. This can reduce garbage collection
   * in speed critical code.
   */

  public static final class Context
  {
    final MatrixM4x4D m4a = new MatrixM4x4D();
    final VectorM4D   va  = new VectorM4D();
    final VectorM4D   vb  = new VectorM4D();
    final MatrixM3x3D m3a = new MatrixM3x3D();

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

  public static MatrixM4x4D add(
    final MatrixM4x4D m0,
    final MatrixM4x4D m1,
    final MatrixM4x4D out)
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
   * @return m0
   */

  public static MatrixM4x4D addInPlace(
    final MatrixM4x4D m0,
    final MatrixM4x4D m1)
  {
    return MatrixM4x4D.add(m0, m1, m0);
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

  public static MatrixM4x4D addRowScaled(
    final Context context,
    final MatrixM4x4D m,
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
      context.va,
      context.vb,
      out);
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

  public static MatrixM4x4D addRowScaled(
    final MatrixM4x4D m,
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
    final MatrixM4x4D m,
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

  public static MatrixM4x4D copy(
    final MatrixM4x4D input,
    final MatrixM4x4D output)
  {
    for (int index = 0; index < 16; ++index) {
      output.view.put(index, input.view.get(index));
    }
    return output;
  }

  /**
   * Return a read-only view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static DoubleBuffer doubleBuffer(
    final MatrixM4x4D m)
  {
    final ByteBuffer b =
      m.data.asReadOnlyBuffer().order(ByteOrder.nativeOrder());
    return b.asDoubleBuffer();
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

  public static MatrixM4x4D exchangeRows(
    final Context context,
    final MatrixM4x4D m,
    final int row_a,
    final int row_b,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.exchangeRowsUnsafe(
      m,
      MatrixM4x4D.rowCheck(row_a),
      MatrixM4x4D.rowCheck(row_b),
      context.va,
      context.vb,
      out);
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

  public static MatrixM4x4D exchangeRows(
    final MatrixM4x4D m,
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

  public static MatrixM4x4D exchangeRowsInPlace(
    final MatrixM4x4D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4D.exchangeRows(m, row_a, row_b, m);
  }

  private static MatrixM4x4D exchangeRowsUnsafe(
    final MatrixM4x4D m,
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
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static double get(
    final MatrixM4x4D m,
    final int row,
    final int column)
  {
    return m.view.get(MatrixM4x4D.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4D.indexUnsafe(
      MatrixM4x4D.rowCheck(row),
      MatrixM4x4D.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage.
   */

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 4) + row;
  }

  private static Option<MatrixM4x4D> invert(
    final MatrixM4x4D m,
    final MatrixM3x3D m3,
    final MatrixM4x4D out)
  {
    final double d = MatrixM4x4D.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM4x4D>();
    }

    final double d_inv = 1 / d;

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

      m3.set(0, 0, m.getUnsafe(1, 1));
      m3.set(0, 1, m.getUnsafe(1, 2));
      m3.set(0, 2, m.getUnsafe(1, 3));
      m3.set(1, 0, m.getUnsafe(2, 1));
      m3.set(1, 1, m.getUnsafe(2, 2));
      m3.set(1, 2, m.getUnsafe(2, 3));
      m3.set(2, 0, m.getUnsafe(3, 1));
      m3.set(2, 1, m.getUnsafe(3, 2));
      m3.set(2, 2, m.getUnsafe(3, 3));

      r0c0 = MatrixM3x3D.determinant(m3);
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

      r0c1 = -MatrixM3x3D.determinant(m3);
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

      r0c2 = MatrixM3x3D.determinant(m3);
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

      r0c3 = -MatrixM3x3D.determinant(m3);
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

      r1c0 = -MatrixM3x3D.determinant(m3);
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

      r1c1 = MatrixM3x3D.determinant(m3);
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

      r1c2 = -MatrixM3x3D.determinant(m3);
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

      r1c3 = MatrixM3x3D.determinant(m3);
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

      r2c0 = MatrixM3x3D.determinant(m3);
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

      r2c1 = -MatrixM3x3D.determinant(m3);
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

      r2c2 = MatrixM3x3D.determinant(m3);
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

      r2c3 = -MatrixM3x3D.determinant(m3);
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

      r3c0 = -MatrixM3x3D.determinant(m3);
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

      r3c1 = MatrixM3x3D.determinant(m3);
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

      r3c2 = -MatrixM3x3D.determinant(m3);
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

      r3c3 = MatrixM3x3D.determinant(m3);
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

    MatrixM4x4D.transposeInPlace(out);
    return new Option.Some<MatrixM4x4D>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM4x4D#determinant(MatrixM4x4D)
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM4x4D> invert(
    final MatrixM4x4D m,
    final MatrixM4x4D out)
  {
    final MatrixM3x3D m3 = new MatrixM3x3D();
    return MatrixM4x4D.invert(m, m3, out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see com.io7m.jtensors.MatrixM4x4D#determinant(MatrixM4x4D)
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM4x4D> invertInPlace(
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
   * <code>context</code> to avoid allocating memory.
   * 
   * @see com.io7m.jtensors.MatrixM4x4D#determinant(MatrixM4x4D)
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   */

  public static Option<MatrixM4x4D> invertInPlaceWithContext(
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
   * <code>context</code> to avoid allocating memory.
   * 
   * @see com.io7m.jtensors.MatrixM4x4D#determinant(MatrixM4x4D)
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static Option<MatrixM4x4D> invertWithContext(
    final Context context,
    final MatrixM4x4D m,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.invert(m, context.m3a, out);
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

  public static MatrixM4x4D makeRotation(
    final double angle,
    final VectorReadable3D axis)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeRotation(angle, axis, out);
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

  public static MatrixM4x4D makeRotation(
    final double angle,
    final VectorReadable3D axis,
    final MatrixM4x4D out)
  {
    final double ax = axis.getXd();
    final double ay = axis.getYd();
    final double az = axis.getZd();

    final double c = Math.cos(angle);
    final double s = Math.sin(angle);
    final double omc = 1.0 - c;

    final double sx = s * ax;
    final double sy = s * ay;
    final double sz = s * az;

    final double axis_xy = ax * ay;
    final double axis_xz = ax * az;
    final double axis_yz = ay * az;

    /*
     * Right-handed coordinate system. Flip signs where indicated for a
     * left-handed system.
     */

    out.setUnsafe(0, 0, c + (omc * (ax * ax)));
    out.setUnsafe(0, 1, (omc * axis_xy) + sz); // - sz for left-handed.
    out.setUnsafe(0, 2, (omc * axis_xz) - sy); // + sy for left-handed.
    out.setUnsafe(0, 3, 0.0);

    out.setUnsafe(1, 0, (omc * axis_xy) - sz); // + sz for left-handed.
    out.setUnsafe(1, 1, c + (omc * (ay * ay)));
    out.setUnsafe(1, 2, (omc * axis_yz) + sx); // - sz for left-handed.
    out.setUnsafe(1, 3, 0.0);

    out.setUnsafe(2, 0, (omc * axis_xz) + sy); // - sy for left-handed.
    out.setUnsafe(2, 1, (omc * axis_yz) - sx); // + sx for left-handed.
    out.setUnsafe(2, 2, c + (omc * (az * az)));
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

  public static MatrixM4x4D makeTranslation(
    final VectorReadable3D v)
  {
    final MatrixM4x4D out = new MatrixM4x4D();
    MatrixM4x4D.makeTranslation(v, out);
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

  public static MatrixM4x4D makeTranslation(
    final VectorReadable3D v,
    final MatrixM4x4D out)
  {
    out.setUnsafe(0, 0, 1.0);
    out.setUnsafe(0, 1, 0.0);
    out.setUnsafe(0, 2, 0.0);
    out.setUnsafe(0, 3, v.getXd());

    out.setUnsafe(1, 0, 0.0);
    out.setUnsafe(1, 1, 1.0);
    out.setUnsafe(1, 2, 0.0);
    out.setUnsafe(1, 3, v.getYd());

    out.setUnsafe(2, 0, 0.0);
    out.setUnsafe(2, 1, 0.0);
    out.setUnsafe(2, 2, 1.0);
    out.setUnsafe(2, 3, v.getZd());

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
    final MatrixM4x4D m0,
    final MatrixM4x4D m1,
    final MatrixM4x4D out)
  {
    double r0c0 = 0;
    r0c0 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 0);
    r0c0 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 0);
    r0c0 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 0);
    r0c0 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 0);

    double r1c0 = 0;
    r1c0 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 0);
    r1c0 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 0);
    r1c0 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 0);
    r1c0 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 0);

    double r2c0 = 0;
    r2c0 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 0);
    r2c0 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 0);
    r2c0 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 0);
    r2c0 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 0);

    double r3c0 = 0;
    r3c0 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 0);
    r3c0 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 0);
    r3c0 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 0);
    r3c0 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 0);

    double r0c1 = 0;
    r0c1 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 1);
    r0c1 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 1);
    r0c1 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 1);
    r0c1 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 1);

    double r1c1 = 0;
    r1c1 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 1);
    r1c1 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 1);
    r1c1 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 1);
    r1c1 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 1);

    double r2c1 = 0;
    r2c1 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 1);
    r2c1 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 1);
    r2c1 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 1);
    r2c1 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 1);

    double r3c1 = 0;
    r3c1 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 1);
    r3c1 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 1);
    r3c1 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 1);
    r3c1 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 1);

    double r0c2 = 0;
    r0c2 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 2);
    r0c2 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 2);
    r0c2 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 2);
    r0c2 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 2);

    double r1c2 = 0;
    r1c2 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 2);
    r1c2 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 2);
    r1c2 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 2);
    r1c2 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 2);

    double r2c2 = 0;
    r2c2 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 2);
    r2c2 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 2);
    r2c2 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 2);
    r2c2 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 2);

    double r3c2 = 0;
    r3c2 += m0.getUnsafe(3, 0) * m1.getUnsafe(0, 2);
    r3c2 += m0.getUnsafe(3, 1) * m1.getUnsafe(1, 2);
    r3c2 += m0.getUnsafe(3, 2) * m1.getUnsafe(2, 2);
    r3c2 += m0.getUnsafe(3, 3) * m1.getUnsafe(3, 2);

    double r0c3 = 0;
    r0c3 += m0.getUnsafe(0, 0) * m1.getUnsafe(0, 3);
    r0c3 += m0.getUnsafe(0, 1) * m1.getUnsafe(1, 3);
    r0c3 += m0.getUnsafe(0, 2) * m1.getUnsafe(2, 3);
    r0c3 += m0.getUnsafe(0, 3) * m1.getUnsafe(3, 3);

    double r1c3 = 0;
    r1c3 += m0.getUnsafe(1, 0) * m1.getUnsafe(0, 3);
    r1c3 += m0.getUnsafe(1, 1) * m1.getUnsafe(1, 3);
    r1c3 += m0.getUnsafe(1, 2) * m1.getUnsafe(2, 3);
    r1c3 += m0.getUnsafe(1, 3) * m1.getUnsafe(3, 3);

    double r2c3 = 0;
    r2c3 += m0.getUnsafe(2, 0) * m1.getUnsafe(0, 3);
    r2c3 += m0.getUnsafe(2, 1) * m1.getUnsafe(1, 3);
    r2c3 += m0.getUnsafe(2, 2) * m1.getUnsafe(2, 3);
    r2c3 += m0.getUnsafe(2, 3) * m1.getUnsafe(3, 3);

    double r3c3 = 0;
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

  public static VectorM4D multiply(
    final MatrixM4x4D m,
    final VectorReadable4D v,
    final VectorM4D out)
  {
    final VectorM4D va = new VectorM4D();
    final VectorM4D vb = new VectorM4D();
    return MatrixM4x4D.multiply(m, v, va, vb, out);
  }

  private static VectorM4D multiply(
    final MatrixM4x4D m,
    final VectorReadable4D v,
    final VectorM4D va,
    final VectorM4D vb,
    final VectorM4D out)
  {
    vb.x = v.getXd();
    vb.y = v.getYd();
    vb.z = v.getZd();
    vb.w = v.getWd();

    MatrixM4x4D.rowUnsafe(m, 0, va);
    out.x = VectorM4D.dotProduct(va, vb);
    MatrixM4x4D.rowUnsafe(m, 1, va);
    out.y = VectorM4D.dotProduct(va, vb);
    MatrixM4x4D.rowUnsafe(m, 2, va);
    out.z = VectorM4D.dotProduct(va, vb);
    MatrixM4x4D.rowUnsafe(m, 3, va);
    out.w = VectorM4D.dotProduct(va, vb);

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
    final MatrixM4x4D m1)
  {
    return MatrixM4x4D.multiply(m0, m1, m0);
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

  public static VectorM4D multiplyWithContext(
    final Context context,
    final MatrixM4x4D m,
    final VectorReadable4D v,
    final VectorM4D out)
  {
    return MatrixM4x4D.multiply(m, v, context.va, context.vb, out);
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

  public static MatrixM4x4D rotate(
    final Context context,
    final double angle,
    final MatrixM4x4D m,
    final VectorReadable3D axis,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.rotate(angle, m, context.m4a, axis, out);
  }

  private static MatrixM4x4D rotate(
    final double angle,
    final MatrixM4x4D m,
    final MatrixM4x4D tmp,
    final VectorReadable3D axis,
    final MatrixM4x4D out)
  {
    MatrixM4x4D.makeRotation(angle, axis, tmp);
    MatrixM4x4D.multiply(m, tmp, out);
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

  public static MatrixM4x4D rotate(
    final double angle,
    final MatrixM4x4D m,
    final VectorReadable3D axis,
    final MatrixM4x4D out)
  {
    final MatrixM4x4D tmp = new MatrixM4x4D();
    return MatrixM4x4D.rotate(angle, m, tmp, axis, out);
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

  public static MatrixM4x4D rotateInPlace(
    final double angle,
    final MatrixM4x4D m,
    final VectorReadable3D axis)
  {
    final MatrixM4x4D tmp = new MatrixM4x4D();
    return MatrixM4x4D.rotate(angle, m, tmp, axis, m);
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

  public static MatrixM4x4D rotateWithContext(
    final Context context,
    final double angle,
    final MatrixM4x4D m,
    final VectorReadable3D axis)
  {
    return MatrixM4x4D.rotate(angle, m, context.m4a, axis, m);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static VectorM4D row(
    final MatrixM4x4D m,
    final int row,
    final VectorM4D out)
  {
    return MatrixM4x4D.rowUnsafe(m, MatrixM4x4D.rowCheck(row), out);
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

  public static VectorM4D rowUnsafe(
    final MatrixM4x4D m,
    final int row,
    final VectorM4D out)
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

  public static MatrixM4x4D scale(
    final MatrixM4x4D m,
    final double r,
    final MatrixM4x4D out)
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

  public static MatrixM4x4D scaleInPlace(
    final MatrixM4x4D m,
    final double r)
  {
    return MatrixM4x4D.scale(m, r, m);
  }

  /**
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory.
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

  public static MatrixM4x4D scaleRow(
    final Context context,
    final MatrixM4x4D m,
    final int row,
    final double r,
    final MatrixM4x4D out)
  {
    return MatrixM4x4D.scaleRowUnsafe(
      m,
      MatrixM4x4D.rowCheck(row),
      r,
      context.va,
      out);
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

  public static MatrixM4x4D scaleRow(
    final MatrixM4x4D m,
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
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>.
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
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>. The function
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
      context.va,
      m);
  }

  private static MatrixM4x4D scaleRowUnsafe(
    final MatrixM4x4D m,
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
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
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
   * @return <code>m</code>
   */

  public static MatrixM4x4D setIdentity(
    final MatrixM4x4D m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4D.identity_row_0);
    m.view.put(MatrixM4x4D.identity_row_1);
    m.view.put(MatrixM4x4D.identity_row_2);
    m.view.put(MatrixM4x4D.identity_row_3);
    return m;
  }

  private static void setRowUnsafe(
    final MatrixM4x4D m,
    final int row,
    final VectorReadable4D v)
  {
    m.setUnsafe(row, 0, v.getXd());
    m.setUnsafe(row, 1, v.getYd());
    m.setUnsafe(row, 2, v.getZd());
    m.setUnsafe(row, 3, v.getWd());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static MatrixM4x4D setZero(
    final MatrixM4x4D m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4D.zero_row);
    m.view.put(MatrixM4x4D.zero_row);
    m.view.put(MatrixM4x4D.zero_row);
    m.view.put(MatrixM4x4D.zero_row);
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

  public static MatrixM4x4D translateByVector2D(
    final MatrixM4x4D m,
    final VectorReadable2D v,
    final MatrixM4x4D out)
  {
    final double vx = v.getXd();
    final double vy = v.getYd();

    final double c3r0 = (m.getUnsafe(0, 0) * vx) + (m.getUnsafe(0, 1) * vy);
    final double c3r1 = (m.getUnsafe(1, 0) * vx) + (m.getUnsafe(1, 1) * vy);
    final double c3r2 = (m.getUnsafe(2, 0) * vx) + (m.getUnsafe(2, 1) * vy);
    final double c3r3 = (m.getUnsafe(3, 0) * vx) + (m.getUnsafe(3, 1) * vy);

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

  public static MatrixM4x4D translateByVector2DInPlace(
    final MatrixM4x4D m,
    final VectorReadable2D v)
  {
    return MatrixM4x4D.translateByVector2D(m, v, m);
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

  public static MatrixM4x4D translateByVector3D(
    final MatrixM4x4D m,
    final VectorReadable3D v,
    final MatrixM4x4D out)
  {
    final double vx = v.getXd();
    final double vy = v.getYd();
    final double vz = v.getZd();

    final double c3r0 =
      (m.getUnsafe(0, 0) * vx)
        + (m.getUnsafe(0, 1) * vy)
        + (m.getUnsafe(0, 2) * vz);
    final double c3r1 =
      (m.getUnsafe(1, 0) * vx)
        + (m.getUnsafe(1, 1) * vy)
        + (m.getUnsafe(1, 2) * vz);
    final double c3r2 =
      (m.getUnsafe(2, 0) * vx)
        + (m.getUnsafe(2, 1) * vy)
        + (m.getUnsafe(2, 2) * vz);
    final double c3r3 =
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

  public static MatrixM4x4D translateByVector3DInPlace(
    final MatrixM4x4D m,
    final VectorReadable3D v)
  {
    return MatrixM4x4D.translateByVector3D(m, v, m);
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
    final MatrixM4x4D m,
    final MatrixM4x4D out)
  {
    for (int index = 0; index < 16; ++index) {
      out.view.put(index, m.view.get(index));
    }
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
    for (int row = 0; row < (4 - 1); row++) {
      for (int column = row + 1; column < 4; column++) {
        final double x = m.view.get((row * 4) + column);
        m.view.put((row * 4) + column, m.view.get(row + (4 * column)));
        m.view.put(row + (4 * column), x);
      }
    }
    return m;
  }

  private final ByteBuffer      data;

  private final DoubleBuffer    view;

  private static final double[] identity_row_0 = { 1.0, 0.0, 0.0, 0.0 };

  private static final double[] identity_row_1 = { 0.0, 1.0, 0.0, 0.0 };

  private static final double[] identity_row_2 = { 0.0, 0.0, 1.0, 0.0 };

  private static final double[] identity_row_3 = { 0.0, 0.0, 0.0, 1.0 };

  private static final double[] zero_row       = { 0.0, 0.0, 0.0, 0.0 };

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static double determinant(
    final MatrixM4x4D m)
  {
    final double r0c0 = m.getUnsafe(0, 0);
    final double r1c0 = m.getUnsafe(1, 0);
    final double r2c0 = m.getUnsafe(2, 0);
    final double r3c0 = m.getUnsafe(3, 0);

    final double r0c1 = m.getUnsafe(0, 1);
    final double r1c1 = m.getUnsafe(1, 1);
    final double r2c1 = m.getUnsafe(2, 1);
    final double r3c1 = m.getUnsafe(3, 1);

    final double r0c2 = m.getUnsafe(0, 2);
    final double r1c2 = m.getUnsafe(1, 2);
    final double r2c2 = m.getUnsafe(2, 2);
    final double r3c2 = m.getUnsafe(3, 2);

    final double r0c3 = m.getUnsafe(0, 3);
    final double r1c3 = m.getUnsafe(1, 3);
    final double r2c3 = m.getUnsafe(2, 3);
    final double r3c3 = m.getUnsafe(3, 3);

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

  public static MatrixM4x4D exchangeRowsInPlaceWithContext(
    final Context context,
    final MatrixM4x4D m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4D.exchangeRows(context, m, row_a, row_b, m);
  }

  public MatrixM4x4D()
  {
    this.data =
      ByteBuffer.allocateDirect(4 * 4 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM4x4D.setIdentity(this);
  }

  public MatrixM4x4D(
    final MatrixM4x4D source)
  {
    this.data =
      ByteBuffer.allocateDirect(4 * 4 * 8).order(ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    for (int index = 0; index < 16; ++index) {
      this.view.put(index, source.view.get(index));
    }
  }

  public double get(
    final int row,
    final int column)
  {
    return MatrixM4x4D.get(this, row, column);
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM4x4D.indexUnsafe(row, column));
  }

  public MatrixM4x4D set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4D.indexChecked(row, column), value);
    return this;
  }

  private MatrixM4x4D setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4D.indexUnsafe(row, column), value);
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 4; ++row) {
      final String text =
        String.format(
          "[%.12f\t%.12f\t%.12f\t%.12f]\n",
          Double.valueOf(MatrixM4x4D.get(this, row, 0)),
          Double.valueOf(MatrixM4x4D.get(this, row, 1)),
          Double.valueOf(MatrixM4x4D.get(this, row, 2)),
          Double.valueOf(MatrixM4x4D.get(this, row, 3)));
      builder.append(text);
    }
    return builder.toString();
  }
}
