/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.functional.Option;

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
 * The intention of the type parameter <code>A</code> is to be used as a
 * "phantom type" or compile-time tag to statically distinguish between
 * semantically distinct matrices.
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

@NotThreadSafe public final class MatrixM4x4DT<A> implements
  MatrixReadable4x4DT<A>
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

  @NotThreadSafe public static final class Context<A>
  {
    final @Nonnull MatrixM4x4DT<A> m4a = new MatrixM4x4DT<A>();
    final @Nonnull MatrixM4x4DT<A> m4b = new MatrixM4x4DT<A>();
    final @Nonnull VectorM4D       v4a = new VectorM4D();
    final @Nonnull VectorM4D       v4b = new VectorM4D();
    final @Nonnull MatrixM3x3D     m3a = new MatrixM3x3D();
    final @Nonnull VectorM3D       v3a = new VectorM3D();
    final @Nonnull VectorM3D       v3b = new VectorM3D();
    final @Nonnull VectorM3D       v3c = new VectorM3D();
    final @Nonnull VectorM3D       v3d = new VectorM3D();

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

  public static @Nonnull <A> MatrixM4x4DT<A> add(
    final @Nonnull MatrixReadable4x4DT<A> m0,
    final @Nonnull MatrixReadable4x4DT<A> m1,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final DoubleBuffer m0_view = m0.getDoubleBuffer();
    final DoubleBuffer m1_view = m1.getDoubleBuffer();

    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
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

  public static @Nonnull <A> MatrixM4x4DT<A> addInPlace(
    final @Nonnull MatrixM4x4DT<A> m0,
    final @Nonnull MatrixReadable4x4DT<A> m1)
  {
    return MatrixM4x4DT.add(m0, m1, m0);
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

  public static @Nonnull <A> MatrixM4x4DT<A> addRowScaled(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final @Nonnull VectorM4D va = new VectorM4D();
    final @Nonnull VectorM4D vb = new VectorM4D();

    return MatrixM4x4DT.addRowScaledUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row_a),
      MatrixM4x4DT.rowCheck(row_b),
      MatrixM4x4DT.rowCheck(row_c),
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

  public static @Nonnull <A> MatrixM4x4DT<A> addRowScaledInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM4x4DT.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static @Nonnull <A> MatrixM4x4DT<A> addRowScaledUnsafe(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull VectorM4D va,
    final @Nonnull VectorM4D vb,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    MatrixM4x4DT.rowUnsafe(m, row_a, va);
    MatrixM4x4DT.rowUnsafe(m, row_b, vb);
    VectorM4D.addScaledInPlace(va, vb, r);
    MatrixM4x4DT.setRowUnsafe(out, row_c, va);
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
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
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

  public static @Nonnull <A> MatrixM4x4DT<A> addRowScaledWithContext(
    final Context<A> context,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    return MatrixM4x4DT.addRowScaledUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row_a),
      MatrixM4x4DT.rowCheck(row_b),
      MatrixM4x4DT.rowCheck(row_c),
      r,
      context.v4a,
      context.v4b,
      out);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM4x4DT.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < " + MatrixM4x4DT.VIEW_COLS);
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

  public static @Nonnull <A> MatrixM4x4DT<A> copy(
    final @Nonnull MatrixReadable4x4DT<A> input,
    final @Nonnull MatrixM4x4DT<A> output)
  {
    final DoubleBuffer input_view = input.getDoubleBuffer();
    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      output.view.put(index, input_view.get(index));
    }

    output.view.rewind();
    return output;
  }

  /**
   * Return a view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static @Nonnull <A> DoubleBuffer doubleBuffer(
    final @Nonnull MatrixM4x4DT<A> m)
  {
    return m.view;
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code>.
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

  public static @Nonnull <A> MatrixM4x4DT<A> exchangeRows(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final @Nonnull VectorM4D va = new VectorM4D();
    final @Nonnull VectorM4D vb = new VectorM4D();
    return MatrixM4x4DT.exchangeRowsUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row_a),
      MatrixM4x4DT.rowCheck(row_b),
      va,
      vb,
      out);
  }

  /**
   * <p>
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>.
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

  public static @Nonnull <A> MatrixM4x4DT<A> exchangeRowsInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4DT.exchangeRows(m, row_a, row_b, m);
  }

  private static @Nonnull <A> MatrixM4x4DT<A> exchangeRowsUnsafe(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull VectorM4D va,
    final @Nonnull VectorM4D vb,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    MatrixM4x4DT.rowUnsafe(m, row_a, va);
    MatrixM4x4DT.rowUnsafe(m, row_b, vb);
    MatrixM4x4DT.setRowUnsafe(out, row_a, vb);
    MatrixM4x4DT.setRowUnsafe(out, row_b, va);

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
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
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

  public static @Nonnull <A> MatrixM4x4DT<A> exchangeRowsWithContext(
    final Context<A> context,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    return MatrixM4x4DT.exchangeRowsUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row_a),
      MatrixM4x4DT.rowCheck(row_b),
      context.v4a,
      context.v4b,
      out);
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static <A> double get(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final int column)
  {
    final DoubleBuffer m_view = m.getDoubleBuffer();
    return m_view.get(MatrixM4x4DT.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM4x4DT.indexUnsafe(
      MatrixM4x4DT.rowCheck(row),
      MatrixM4x4DT.columnCheck(column));
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

  private final static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * MatrixM4x4DT.VIEW_COLS) + row;
  }

  private static @Nonnull <A> Option<MatrixM4x4DT<A>> invert(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull MatrixM3x3D m3,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final double d = MatrixM4x4DT.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM4x4DT<A>>();
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

    MatrixM4x4DT.transposeInPlace(out);

    out.view.rewind();
    return new Option.Some<MatrixM4x4DT<A>>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>.
   * 
   * @see MatrixM4x4DT#determinant(MatrixReadable4x4DT)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static @Nonnull <A> Option<MatrixM4x4DT<A>> invert(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final @Nonnull MatrixM3x3D m3 = new MatrixM3x3D();
    return MatrixM4x4DT.invert(m, m3, out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see MatrixM4x4DT#determinant(MatrixReadable4x4DT)
   * 
   * @param m
   *          The input matrix.
   */

  public static @Nonnull <A> Option<MatrixM4x4DT<A>> invertInPlace(
    final @Nonnull MatrixM4x4DT<A> m)
  {
    return MatrixM4x4DT.invert(m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(out)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>. The function uses preallocated storage in
   * <code>context</code> to avoid allocating memory.
   * 
   * @see MatrixM4x4DT#determinant(MatrixReadable4x4DT)
   * 
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   */

  public static @Nonnull
    <A>
    Option<MatrixM4x4DT<A>>
    invertInPlaceWithContext(
      final Context<A> context,
      final @Nonnull MatrixM4x4DT<A> m)
  {
    return MatrixM4x4DT.invertWithContext(context, m, m);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>out</code>. The function returns <code>Some(out)</code>
   * iff it was possible to invert the matrix, and <code>None</code>
   * otherwise. It is not possible to invert a matrix that has a determinant
   * of <code>0</code>. The function uses preallocated storage in
   * <code>context</code> to avoid allocating memory.
   * 
   * @see MatrixM4x4DT#determinant(MatrixReadable4x4DT)
   * 
   * @param context
   *          Preallocated storage.
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static @Nonnull <A> Option<MatrixM4x4DT<A>> invertWithContext(
    final Context<A> context,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    return MatrixM4x4DT.invert(m, context.m3a, out);
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
   */

  public static @Nonnull <A> MatrixM4x4DT<A> makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM4x4DT<A> out = new MatrixM4x4DT<A>();
    MatrixM4x4DT.makeRotation(angle, axis, out);
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

  public static @Nonnull <A> MatrixM4x4DT<A> makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM4x4DT<A> out)
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

  public static @Nonnull <A> MatrixM4x4DT<A> makeTranslation3D(
    final @Nonnull VectorReadable3D v)
  {
    final @Nonnull MatrixM4x4DT<A> out = new MatrixM4x4DT<A>();
    MatrixM4x4DT.makeTranslation3D(v, out);
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

  public static @Nonnull <A> MatrixM4x4DT<A> makeTranslation3D(
    final @Nonnull VectorReadable3D v,
    final @Nonnull MatrixM4x4DT<A> out)
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

  public static @Nonnull <A> MatrixM4x4DT<A> makeTranslation3I(
    final @Nonnull VectorReadable3I v)
  {
    final @Nonnull MatrixM4x4DT<A> out = new MatrixM4x4DT<A>();
    MatrixM4x4DT.makeTranslation3I(v, out);
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

  public static @Nonnull <A> MatrixM4x4DT<A> makeTranslation3I(
    final @Nonnull VectorReadable3I v,
    final @Nonnull MatrixM4x4DT<A> out)
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

  public static @Nonnull <A, B, C> MatrixM4x4DT<C> multiply(
    final @Nonnull MatrixReadable4x4DT<A> m0,
    final @Nonnull MatrixReadable4x4DT<B> m1,
    final @Nonnull MatrixM4x4DT<C> out)
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

  public static @Nonnull <A, B> MatrixM4x4DT<A> multiplyInPlace(
    final @Nonnull MatrixM4x4DT<A> m0,
    final @Nonnull MatrixReadable4x4DT<B> m1)
  {
    return MatrixM4x4DT.multiply(m0, m1, m0);
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
   */

  public static @Nonnull <A> VectorM4D multiplyVector4D(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable4D v,
    final @Nonnull VectorM4D out)
  {
    final @Nonnull VectorM4D va = new VectorM4D();
    final @Nonnull VectorM4D vb = new VectorM4D();
    return MatrixM4x4DT.multiplyVector4D(m, v, va, vb, out);
  }

  private static @Nonnull <A> VectorM4D multiplyVector4D(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable4D v,
    final @Nonnull VectorM4D va,
    final @Nonnull VectorM4D vb,
    final @Nonnull VectorM4D out)
  {
    vb.x = v.getXD();
    vb.y = v.getYD();
    vb.z = v.getZD();
    vb.w = v.getWD();

    MatrixM4x4DT.rowUnsafe(m, 0, va);
    out.x = VectorM4D.dotProduct(va, vb);
    MatrixM4x4DT.rowUnsafe(m, 1, va);
    out.y = VectorM4D.dotProduct(va, vb);
    MatrixM4x4DT.rowUnsafe(m, 2, va);
    out.z = VectorM4D.dotProduct(va, vb);
    MatrixM4x4DT.rowUnsafe(m, 3, va);
    out.w = VectorM4D.dotProduct(va, vb);

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
   */

  public static @Nonnull <A> VectorM4D multiplyVector4DWithContext(
    final Context<A> context,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable4D v,
    final @Nonnull VectorM4D out)
  {
    return MatrixM4x4DT.multiplyVector4D(m, v, context.v4a, context.v4b, out);
  }

  private static @Nonnull <A> MatrixM4x4DT<A> rotate(
    final double angle,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull MatrixM4x4DT<A> tmp,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    MatrixM4x4DT.makeRotation(angle, axis, tmp);
    MatrixM4x4DT.multiply(m, tmp, out);
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

  public static @Nonnull <A> MatrixM4x4DT<A> rotate(
    final double angle,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final @Nonnull MatrixM4x4DT<A> tmp = new MatrixM4x4DT<A>();
    return MatrixM4x4DT.rotate(angle, m, tmp, axis, out);
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

  public static @Nonnull <A> MatrixM4x4DT<A> rotateInPlace(
    final double angle,
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM4x4DT<A> tmp = new MatrixM4x4DT<A>();
    return MatrixM4x4DT.rotate(angle, m, tmp, axis, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> rotateInPlaceWithContext(
    final Context<A> context,
    final double angle,
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable3D axis)
  {
    return MatrixM4x4DT.rotate(angle, m, context.m4a, axis, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> rotateWithContext(
    final Context<A> context,
    final double angle,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    return MatrixM4x4DT.rotate(angle, m, context.m4a, axis, out);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static @Nonnull <A> VectorM4D row(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final @Nonnull VectorM4D out)
  {
    return MatrixM4x4DT.rowUnsafe(m, MatrixM4x4DT.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM4x4DT.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM4x4DT.VIEW_COLS);
    }
    return row;
  }

  public static @Nonnull <A> VectorM4D rowUnsafe(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final @Nonnull VectorM4D out)
  {
    out.x = m.getRowColumnD(row, 0);
    out.y = m.getRowColumnD(row, 1);
    out.z = m.getRowColumnD(row, 2);
    out.w = m.getRowColumnD(row, 3);
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

  public static @Nonnull <A> MatrixM4x4DT<A> scale(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final double r,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final DoubleBuffer m_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      out.view.put(index, m_view.get(index) * r);
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

  public static @Nonnull <A> MatrixM4x4DT<A> scaleInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final double r)
  {
    return MatrixM4x4DT.scale(m, r, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> scaleRow(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final double r,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final @Nonnull VectorM4D tmp = new VectorM4D();
    return MatrixM4x4DT.scaleRowUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row),
      r,
      tmp,
      out);
  }

  /**
   * <p>
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>.
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
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM4x4DT<A> scaleRowInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final int row,
    final double r)
  {
    final @Nonnull VectorM4D tmp = new VectorM4D();
    return MatrixM4x4DT.scaleRowUnsafe(m, row, r, tmp, m);
  }

  /**
   * <p>
   * Scale row <code>row</code> of the matrix <code>m</code> by <code>r</code>
   * , saving the result to row <code>r</code> of <code>m</code>. The function
   * uses preallocated storage in <code>context</code> to avoid allocating
   * memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
   * </p>
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

  public static @Nonnull <A> MatrixM4x4DT<A> scaleRowInPlaceWithContext(
    final Context<A> context,
    final @Nonnull MatrixM4x4DT<A> m,
    final int row,
    final double r)
  {
    return MatrixM4x4DT.scaleRowUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row),
      r,
      context.v4a,
      m);
  }

  private static @Nonnull <A> MatrixM4x4DT<A> scaleRowUnsafe(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final double r,
    final @Nonnull VectorM4D tmp,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    MatrixM4x4DT.rowUnsafe(m, row, tmp);
    VectorM4D.scaleInPlace(tmp, r);
    MatrixM4x4DT.setRowUnsafe(out, row, tmp);

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
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
   * </p>
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

  public static @Nonnull <A> MatrixM4x4DT<A> scaleRowWithContext(
    final Context<A> context,
    final @Nonnull MatrixReadable4x4DT<A> m,
    final int row,
    final double r,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    return MatrixM4x4DT.scaleRowUnsafe(
      m,
      MatrixM4x4DT.rowCheck(row),
      r,
      context.v4a,
      out);
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static @Nonnull <A> MatrixM4x4DT<A> set(
    final @Nonnull MatrixM4x4DT<A> m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM4x4DT.indexChecked(row, column), value);
    m.view.rewind();
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM4x4DT<A> setIdentity(
    final @Nonnull MatrixM4x4DT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4DT.identity_row_0);
    m.view.put(MatrixM4x4DT.identity_row_1);
    m.view.put(MatrixM4x4DT.identity_row_2);
    m.view.put(MatrixM4x4DT.identity_row_3);
    m.view.rewind();
    return m;
  }

  private static <A> void setRowUnsafe(
    final @Nonnull MatrixM4x4DT<A> m,
    final int row,
    final @Nonnull VectorReadable4D v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
    m.setUnsafe(row, 3, v.getWD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM4x4DT<A> setZero(
    final @Nonnull MatrixM4x4DT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM4x4DT.zero_row);
    m.view.put(MatrixM4x4DT.zero_row);
    m.view.put(MatrixM4x4DT.zero_row);
    m.view.put(MatrixM4x4DT.zero_row);
    m.view.rewind();
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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector2D(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable2D v,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final double vx = v.getXD();
    final double vy = v.getYD();

    final double c3r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c3r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c3r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);
    final double c3r3 =
      (m.getRowColumnD(3, 0) * vx) + (m.getRowColumnD(3, 1) * vy);

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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector2DInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable2D v)
  {
    return MatrixM4x4DT.translateByVector2D(m, v, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector2I(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable2I v,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final double vx = v.getXI();
    final double vy = v.getYI();

    final double c3r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c3r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c3r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);
    final double c3r3 =
      (m.getRowColumnD(3, 0) * vx) + (m.getRowColumnD(3, 1) * vy);

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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector2IInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable2I v)
  {
    return MatrixM4x4DT.translateByVector2I(m, v, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector3D(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable3D v,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final double vx = v.getXD();
    final double vy = v.getYD();
    final double vz = v.getZD();

    final double c3r0 =
      (m.getRowColumnD(0, 0) * vx)
        + (m.getRowColumnD(0, 1) * vy)
        + (m.getRowColumnD(0, 2) * vz);
    final double c3r1 =
      (m.getRowColumnD(1, 0) * vx)
        + (m.getRowColumnD(1, 1) * vy)
        + (m.getRowColumnD(1, 2) * vz);
    final double c3r2 =
      (m.getRowColumnD(2, 0) * vx)
        + (m.getRowColumnD(2, 1) * vy)
        + (m.getRowColumnD(2, 2) * vz);
    final double c3r3 =
      (m.getRowColumnD(3, 0) * vx)
        + (m.getRowColumnD(3, 1) * vy)
        + (m.getRowColumnD(3, 2) * vz);

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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector3DInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable3D v)
  {
    return MatrixM4x4DT.translateByVector3D(m, v, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector3I(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull VectorReadable3I v,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final double vx = v.getXI();
    final double vy = v.getYI();
    final double vz = v.getZI();

    final double c3r0 =
      (m.getRowColumnD(0, 0) * vx)
        + (m.getRowColumnD(0, 1) * vy)
        + (m.getRowColumnD(0, 2) * vz);
    final double c3r1 =
      (m.getRowColumnD(1, 0) * vx)
        + (m.getRowColumnD(1, 1) * vy)
        + (m.getRowColumnD(1, 2) * vz);
    final double c3r2 =
      (m.getRowColumnD(2, 0) * vx)
        + (m.getRowColumnD(2, 1) * vy)
        + (m.getRowColumnD(2, 2) * vz);
    final double c3r3 =
      (m.getRowColumnD(3, 0) * vx)
        + (m.getRowColumnD(3, 1) * vy)
        + (m.getRowColumnD(3, 2) * vz);

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

  public static @Nonnull <A> MatrixM4x4DT<A> translateByVector3IInPlace(
    final @Nonnull MatrixM4x4DT<A> m,
    final @Nonnull VectorReadable3I v)
  {
    return MatrixM4x4DT.translateByVector3I(m, v, m);
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

  public static @Nonnull <A> MatrixM4x4DT<A> transpose(
    final @Nonnull MatrixReadable4x4DT<A> m,
    final @Nonnull MatrixM4x4DT<A> out)
  {
    final DoubleBuffer m_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      out.view.put(index, m_view.get(index));
    }
    return MatrixM4x4DT.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM4x4DT<A> transposeInPlace(
    final @Nonnull MatrixM4x4DT<A> m)
  {
    for (int row = 0; row < (MatrixM4x4DT.VIEW_ROWS - 1); row++) {
      for (int column = row + 1; column < MatrixM4x4DT.VIEW_COLS; column++) {
        final double x = m.view.get((row * MatrixM4x4DT.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM4x4DT.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM4x4DT.VIEW_COLS * column)));
        m.view.put(row + (MatrixM4x4DT.VIEW_COLS * column), x);
      }
    }

    m.view.rewind();
    return m;
  }

  private final ByteBuffer      data;
  private final DoubleBuffer    view;
  private static final double[] identity_row_0 = { 1.0, 0.0, 0.0, 0.0 };
  private static final double[] identity_row_1 = { 0.0, 1.0, 0.0, 0.0 };
  private static final double[] identity_row_2 = { 0.0, 0.0, 1.0, 0.0 };
  private static final double[] identity_row_3 = { 0.0, 0.0, 0.0, 1.0 };
  private static final double[] zero_row       = { 0.0, 0.0, 0.0, 0.0 };

  private static final int      VIEW_ELEMENT_SIZE;
  private static final int      VIEW_ELEMENTS;
  private static final int      VIEW_BYTES;
  private static final int      VIEW_COLS;
  private static final int      VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM4x4DT.VIEW_ROWS * MatrixM4x4DT.VIEW_COLS;
    VIEW_BYTES = MatrixM4x4DT.VIEW_ELEMENTS * MatrixM4x4DT.VIEW_ELEMENT_SIZE;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static <A> double determinant(
    final @Nonnull MatrixReadable4x4DT<A> m)
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
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code>. The
   * function uses storage preallocated in <code>context</code> to avoid
   * allocating memory.
   * </p>
   * <p>
   * This is one of the three "elementary" operations defined on matrices. See
   * {@link <a href="http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations">Elementary operations</a>}
   * .
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

  public static @Nonnull <A> MatrixM4x4DT<A> exchangeRowsInPlaceWithContext(
    final Context<A> context,
    final @Nonnull MatrixM4x4DT<A> m,
    final int row_a,
    final int row_b)
  {
    return MatrixM4x4DT.exchangeRowsWithContext(context, m, row_a, row_b, m);
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

  public static <A, B> void lookAtWithContext(
    final @Nonnull Context<A> context,
    final @Nonnull VectorReadable3DT<B> origin,
    final @Nonnull VectorReadable3DT<B> target,
    final @Nonnull VectorReadable3DT<B> up,
    final @Nonnull MatrixM4x4DT<A> out_matrix)
  {
    final VectorM3D forward = context.v3a;
    final VectorM3D new_up = context.v3b;
    final VectorM3D side = context.v3c;
    final VectorM3D move = context.v3d;
    final MatrixM4x4DT<A> rotation = context.m4a;
    final MatrixM4x4DT<A> translation = context.m4b;

    MatrixM4x4DT.setIdentity(rotation);
    MatrixM4x4DT.setIdentity(translation);
    MatrixM4x4DT.setIdentity(out_matrix);

    /**
     * Calculate "forward" vector
     */

    forward.x = target.getXD() - origin.getXD();
    forward.y = target.getYD() - origin.getYD();
    forward.z = target.getZD() - origin.getZD();
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

    rotation.set(0, 0, side.x);
    rotation.set(0, 1, side.y);
    rotation.set(0, 2, side.z);
    rotation.set(1, 0, new_up.x);
    rotation.set(1, 1, new_up.y);
    rotation.set(1, 2, new_up.z);
    rotation.set(2, 0, -forward.x);
    rotation.set(2, 1, -forward.y);
    rotation.set(2, 2, -forward.z);

    /**
     * Calculate camera translation matrix
     */

    move.x = -origin.getXD();
    move.y = -origin.getYD();
    move.z = -origin.getZD();
    MatrixM4x4DT.translateByVector3DInPlace(translation, move);

    /**
     * Produce output matrix
     */

    MatrixM4x4DT.multiply(rotation, translation, out_matrix);
  }

  /**
   * Return the trace of the matrix <code>m</code>. The trace is defined as
   * the sum of the diagonal elements of the matrix.
   * 
   * 
   * @param m
   *          The input matrix
   * @return The trace of the matrix
   */

  public static <A> double trace(
    final @Nonnull MatrixReadable4x4DT<A> m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2)
      + m.getRowColumnD(3, 3);
  }

  public MatrixM4x4DT()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM4x4DT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM4x4DT.setIdentity(this);
  }

  public MatrixM4x4DT(
    final @Nonnull MatrixM4x4DT<A> source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM4x4DT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source.view.get(index));
    }

    this.view.rewind();
  }

  @Override public boolean equals(
    final Object obj)
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

    @SuppressWarnings("unchecked") final @Nonnull MatrixM4x4DT<A> other =
      (MatrixM4x4DT<A>) obj;
    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  public double get(
    final int row,
    final int column)
  {
    return MatrixM4x4DT.get(this, row, column);
  }

  @Override public @Nonnull DoubleBuffer getDoubleBuffer()
  {
    return this.view;
  }

  @Override public void getRow4D(
    final int row,
    final @Nonnull VectorM4D out)
  {
    MatrixM4x4DT.rowUnsafe(this, MatrixM4x4DT.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return MatrixM4x4DT.get(this, row, column);
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM4x4DT.indexUnsafe(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM4x4DT.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  public MatrixM4x4DT<A> set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4DT.indexChecked(row, column), value);
    this.view.rewind();
    return this;
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

  MatrixM4x4DT<A> setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM4x4DT.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM4x4DT.VIEW_ROWS; ++row) {
      final String text =
        String.format(
          "[%.15f\t%.15f\t%.15f\t%.15f]\n",
          Double.valueOf(MatrixM4x4DT.get(this, row, 0)),
          Double.valueOf(MatrixM4x4DT.get(this, row, 1)),
          Double.valueOf(MatrixM4x4DT.get(this, row, 2)),
          Double.valueOf(MatrixM4x4DT.get(this, row, 3)));
      builder.append(text);
    }
    return builder.toString();
  }

  /**
   * <p>
   * Cast away the extra type information to give an ordinary
   * {@link MatrixM4x4D}. The resulting matrix instance shares the internal
   * data of this matrix instance (and therefore extreme caution should be
   * taken when mutating either instance).
   * </p>
   */

  public @Nonnull MatrixM4x4D asMatrixM4x4D()
  {
    return new MatrixM4x4D(this.data);
  }
}
