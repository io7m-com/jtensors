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
 * A 3x3 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type <code>MatrixM3x3D</code> are backed by direct memory, with
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
 * 
 * @since 5.1.0
 */

@NotThreadSafe public final class MatrixM3x3DT<A> implements
  MatrixReadable3x3DT<A>
{
  /**
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>MatrixM3x3D</code> class.
   * 
   * <p>
   * The purpose of the class is to allow applications to allocate all storage
   * ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   * 
   * <p>
   * The user should allocate one <code>Context</code> value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes
   * a <code>Context</code> value will not generate garbage.
   * </p>
   */

  @NotThreadSafe public static final class Context<A>
  {
    final @Nonnull MatrixM3x3DT<A> m3a = new MatrixM3x3DT<A>();
    final @Nonnull VectorM3D       v3a = new VectorM3D();
    final @Nonnull VectorM3D       v3b = new VectorM3D();
    final @Nonnull VectorM3D       v3c = new VectorM3D();

    public Context()
    {

    }
  }

  private static final double[] identity_row_0 = { 1.0, 0.0, 0.0 };
  private static final double[] identity_row_1 = { 0.0, 1.0, 0.0 };
  private static final double[] identity_row_2 = { 0.0, 0.0, 1.0 };
  private static final double[] zero_row       = { 0.0, 0.0, 0.0 };

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

  public static @Nonnull <A> MatrixM3x3DT<A> add(
    final @Nonnull MatrixReadable3x3DT<A> m0,
    final @Nonnull MatrixReadable3x3DT<A> m1,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final DoubleBuffer m0_view = m0.getDoubleBuffer();
    final DoubleBuffer m1_view = m1.getDoubleBuffer();

    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
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
   * @return <code>m0</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> addInPlace(
    final @Nonnull MatrixM3x3DT<A> m0,
    final @Nonnull MatrixM3x3DT<A> m1)
  {
    return MatrixM3x3DT.add(m0, m1, m0);
  }

  /**
   * Add the values in row <code>row_b</code> to the values in row
   * <code>row_a</code> scaled by <code>r</code>, saving the resulting row in
   * row <code>row_c</code> of the matrix <code>out</code>.
   * 
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

  public static @Nonnull <A> MatrixM3x3DT<A> addRowScaled(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    return MatrixM3x3DT.addRowScaledUnsafe(
      m,
      MatrixM3x3DT.rowCheck(row_a),
      MatrixM3x3DT.rowCheck(row_b),
      MatrixM3x3DT.rowCheck(row_c),
      r,
      out);
  }

  public static @Nonnull <A> MatrixM3x3DT<A> addRowScaledInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3DT.addRowScaled(m, row_a, row_b, row_c, r, m);
  }

  private static @Nonnull <A> MatrixM3x3DT<A> addRowScaledUnsafe(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final @Nonnull VectorM3D va = new VectorM3D();
    final @Nonnull VectorM3D vb = new VectorM3D();
    MatrixM3x3DT.rowUnsafe(m, row_a, va);
    MatrixM3x3DT.rowUnsafe(m, row_b, vb);

    VectorM3D.addScaledInPlace(va, vb, r);
    MatrixM3x3DT.setRowUnsafe(out, row_c, va);

    out.view.rewind();
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM3x3DT.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM3x3DT.VIEW_COLS);
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

  public static @Nonnull <A> MatrixM3x3DT<A> copy(
    final @Nonnull MatrixReadable3x3DT<A> input,
    final @Nonnull MatrixM3x3DT<A> output)
  {
    final DoubleBuffer source_view = input.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
      output.view.put(index, source_view.get(index));
    }

    output.view.rewind();
    return output;
  }

  /**
   * Calculate the determinant of the matrix <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   */

  public static <A> double determinant(
    final @Nonnull MatrixReadable3x3DT<A> m)
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
   * Return a view of the buffer that backs this matrix.
   * 
   * @param m
   *          The input matrix.
   */

  public static @Nonnull <A> DoubleBuffer doubleBuffer(
    final @Nonnull MatrixM3x3DT<A> m)
  {
    return m.view;
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>out</code> .
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

  public static @Nonnull <A> MatrixM3x3DT<A> exchangeRows(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    return MatrixM3x3DT.exchangeRowsUnsafe(
      m,
      MatrixM3x3DT.rowCheck(row_a),
      MatrixM3x3DT.rowCheck(row_b),
      out);
  }

  /**
   * Exchange the row <code>row_a</code> and row <code>row_b</code> of the
   * matrix <code>m</code>, saving the exchanged rows to <code>m</code> .
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

  public static @Nonnull <A> MatrixM3x3DT<A> exchangeRowsInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3DT.exchangeRows(m, row_a, row_b, m);
  }

  private static @Nonnull <A> MatrixM3x3DT<A> exchangeRowsUnsafe(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row_a,
    final int row_b,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final @Nonnull VectorM3D va = new VectorM3D();
    final @Nonnull VectorM3D vb = new VectorM3D();

    MatrixM3x3DT.rowUnsafe(m, row_a, va);
    MatrixM3x3DT.rowUnsafe(m, row_b, vb);

    MatrixM3x3DT.setRowUnsafe(out, row_a, vb);
    MatrixM3x3DT.setRowUnsafe(out, row_b, va);

    out.view.rewind();
    return out;
  }

  /**
   * Retrieve the value from the matrix <code>m</code> at row <code>row</code>
   * , column <code>column</code>.
   */

  public static <A> double get(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row,
    final int column)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    return source_view.get(MatrixM3x3DT.indexChecked(row, column));
  }

  private final static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3DT.indexUnsafe(
      MatrixM3x3DT.rowCheck(row),
      MatrixM3x3DT.columnCheck(column));
  }

  /**
   * The main function that indexes into the buffer that backs the array. The
   * body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion.
   * 
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage.
   */

  private final static int indexUnsafe(
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
   * @see MatrixM3x3DT<A>#determinant(MatrixReadable3x3DT<A>)
   * 
   * @param m
   *          The input matrix.
   * @param out
   *          The output matrix.
   */

  public static @Nonnull <A> Option<MatrixM3x3DT<A>> invert(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final double d = MatrixM3x3DT.determinant(m);

    if (d == 0.0) {
      return new Option.None<MatrixM3x3DT<A>>();
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

    MatrixM3x3DT.set(out, 0, 0, r0c0);
    MatrixM3x3DT.set(out, 0, 1, r0c1);
    MatrixM3x3DT.set(out, 0, 2, r0c2);

    MatrixM3x3DT.set(out, 1, 0, r1c0);
    MatrixM3x3DT.set(out, 1, 1, r1c1);
    MatrixM3x3DT.set(out, 1, 2, r1c2);

    MatrixM3x3DT.set(out, 2, 0, r2c0);
    MatrixM3x3DT.set(out, 2, 1, r2c1);
    MatrixM3x3DT.set(out, 2, 2, r2c2);

    MatrixM3x3DT.scaleInPlace(out, d_inv);

    out.view.rewind();
    return new Option.Some<MatrixM3x3DT<A>>(out);
  }

  /**
   * Calculate the inverse of the matrix <code>m</code>, saving the resulting
   * matrix to <code>m</code>. The function returns <code>Some(m)</code> iff
   * it was possible to invert the matrix, and <code>None</code> otherwise. It
   * is not possible to invert a matrix that has a determinant of
   * <code>0</code>.
   * 
   * @see MatrixM3x3DT<A>#determinant(MatrixReadable3x3DT<A>)
   * 
   * @param m
   *          The input matrix.
   */

  public static @Nonnull <A> Option<MatrixM3x3DT<A>> invertInPlace(
    final @Nonnull MatrixM3x3DT<A> m)
  {
    return MatrixM3x3DT.invert(m, m);
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
   */

  public static @Nonnull <A> MatrixM3x3DT<A> makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM3x3DT<A> out = new MatrixM3x3DT<A>();
    MatrixM3x3DT.makeRotation(angle, axis, out);
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
   * 
   * @param angle
   *          The angle in radians.
   * @param axis
   *          The axis.
   * @param out
   *          The output matrix.
   * @return <code>out</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> makeRotation(
    final double angle,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3DT<A> out)
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

    out.view.rewind();
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

  public static @Nonnull <A> MatrixM3x3DT<A> makeTranslation2D(
    final @Nonnull VectorReadable2D v,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    out.setUnsafe(0, 2, v.getXD());
    out.setUnsafe(1, 2, v.getYD());
    out.view.rewind();
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

  public static @Nonnull <A> MatrixM3x3DT<A> makeTranslation2I(
    final @Nonnull VectorReadable2I v,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    out.setUnsafe(0, 2, v.getXI());
    out.setUnsafe(1, 2, v.getYI());
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

  public static @Nonnull <A> MatrixM3x3DT<A> multiply(
    final @Nonnull MatrixReadable3x3DT<A> m0,
    final @Nonnull MatrixReadable3x3DT<A> m1,
    final @Nonnull MatrixM3x3DT<A> out)
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

  public static @Nonnull <A> MatrixM3x3DT<A> multiplyInPlace(
    final @Nonnull MatrixM3x3DT<A> m0,
    final @Nonnull MatrixReadable3x3DT<A> m1)
  {
    return MatrixM3x3DT.multiply(m0, m1, m0);
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

  public static @Nonnull <A> VectorM3D multiplyVector3D(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull VectorReadable3D v,
    final @Nonnull VectorM3D out)
  {
    final @Nonnull VectorM3D row = new VectorM3D();
    final @Nonnull VectorM3D vi = new VectorM3D(v);

    MatrixM3x3DT.rowUnsafe(m, 0, row);
    out.x = VectorM3D.dotProduct(row, vi);
    MatrixM3x3DT.rowUnsafe(m, 1, row);
    out.y = VectorM3D.dotProduct(row, vi);
    MatrixM3x3DT.rowUnsafe(m, 2, row);
    out.z = VectorM3D.dotProduct(row, vi);

    return out;
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>.
   * 
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

  public static @Nonnull <A> MatrixM3x3DT<A> rotate(
    final double angle,
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final @Nonnull MatrixM3x3DT<A> tmp = new MatrixM3x3DT<A>();
    return MatrixM3x3DT.rotate(angle, m, tmp, axis, out);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>.
   * 
   * 
   * @param angle
   *          The angle in radians.
   * @param m
   *          The input matrix.
   * @param axis
   *          A vector representing an axis.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> rotateInPlace(
    final double angle,
    final @Nonnull MatrixM3x3DT<A> m,
    final @Nonnull VectorReadable3D axis)
  {
    final @Nonnull MatrixM3x3DT<A> tmp = new MatrixM3x3DT<A>();
    return MatrixM3x3DT.rotate(angle, m, tmp, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>m</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
   * 
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

  public static @Nonnull <A> MatrixM3x3DT<A> rotateInPlaceWithContext(
    final @Nonnull Context<A> context,
    final double angle,
    final @Nonnull MatrixM3x3DT<A> m,
    final @Nonnull VectorReadable3D axis)
  {
    return MatrixM3x3DT.rotate(angle, m, context.m3a, axis, m);
  }

  /**
   * Rotate the matrix <code>m</code> by <code>angle</code> radians around the
   * axis <code>axis</code>, saving the result into <code>out</code>. The
   * function uses preallocated storage in <code>context</code> to avoid
   * allocating memory. The function assumes a right-handed coordinate system.
   * 
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

  public static @Nonnull <A> MatrixM3x3DT<A> rotateWithContext(
    final @Nonnull Context<A> context,
    final double angle,
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    return MatrixM3x3DT.rotate(angle, m, context.m3a, axis, out);
  }

  /**
   * Return row <code>row</code> of the matrix <code>m</code> in the vector
   * <code>out</code>.
   */

  public static @Nonnull <A> VectorM3D row(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row,
    final @Nonnull VectorM3D out)
  {
    return MatrixM3x3DT.rowUnsafe(m, MatrixM3x3DT.rowCheck(row), out);
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM3x3DT.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM3x3DT.VIEW_ROWS);
    }
    return row;
  }

  public static @Nonnull <A> VectorM3D rowUnsafe(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row,
    final @Nonnull VectorM3D out)
  {
    out.x = m.getRowColumnD(row, 0);
    out.y = m.getRowColumnD(row, 1);
    out.z = m.getRowColumnD(row, 2);
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

  public static @Nonnull <A> MatrixM3x3DT<A> scale(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final double r,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index) * r);
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

  public static @Nonnull <A> MatrixM3x3DT<A> scaleInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final double r)
  {
    return MatrixM3x3DT.scale(m, r, m);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>out</code>.
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

  public static @Nonnull <A> MatrixM3x3DT<A> scaleRow(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row,
    final double r,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    return MatrixM3x3DT.scaleRowUnsafe(m, MatrixM3x3DT.rowCheck(row), r, out);
  }

  /**
   * Scale row <code>r</code> of the matrix <code>m</code> by <code>r</code>,
   * saving the result to row <code>r</code> of <code>m</code>.
   * 
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

  public static @Nonnull <A> MatrixM3x3DT<A> scaleRowInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final int row,
    final double r)
  {
    return MatrixM3x3DT.scaleRowUnsafe(m, MatrixM3x3DT.rowCheck(row), r, m);
  }

  private static @Nonnull <A> MatrixM3x3DT<A> scaleRowUnsafe(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final int row,
    final double r,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final @Nonnull VectorM3D v = new VectorM3D();

    MatrixM3x3DT.rowUnsafe(m, row, v);
    VectorM3D.scaleInPlace(v, r);

    MatrixM3x3DT.setRowUnsafe(out, row, v);

    out.view.rewind();
    return out;
  }

  /**
   * Set the value in the matrix <code>m</code> at row <code>row</code>,
   * column <code>column</code> to <code>value</code>.
   */

  public static @Nonnull <A> MatrixM3x3DT<A> set(
    final @Nonnull MatrixM3x3DT<A> m,
    final int row,
    final int column,
    final double value)
  {
    m.view.put(MatrixM3x3DT.indexChecked(row, column), value);
    m.view.rewind();
    return m;
  }

  /**
   * Set the given matrix <code>m</code> to the identity matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> setIdentity(
    final @Nonnull MatrixM3x3DT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3DT.identity_row_0);
    m.view.put(MatrixM3x3DT.identity_row_1);
    m.view.put(MatrixM3x3DT.identity_row_2);
    m.view.rewind();
    return m;
  }

  private static <A> void setRowUnsafe(
    final @Nonnull MatrixM3x3DT<A> m,
    final int row,
    final @Nonnull VectorReadable3D v)
  {
    m.setUnsafe(row, 0, v.getXD());
    m.setUnsafe(row, 1, v.getYD());
    m.setUnsafe(row, 2, v.getZD());
  }

  /**
   * Set the given matrix <code>m</code> to the zero matrix.
   * 
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> setZero(
    final @Nonnull MatrixM3x3DT<A> m)
  {
    m.view.clear();
    m.view.put(MatrixM3x3DT.zero_row);
    m.view.put(MatrixM3x3DT.zero_row);
    m.view.put(MatrixM3x3DT.zero_row);
    m.view.rewind();
    return m;
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
    final @Nonnull MatrixReadable3x3DT<A> m)
  {
    return m.getRowColumnD(0, 0)
      + m.getRowColumnD(1, 1)
      + m.getRowColumnD(2, 2);
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

  public static @Nonnull <A> MatrixM3x3DT<A> translateByVector2D(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull VectorReadable2D v,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final double vx = v.getXD();
    final double vy = v.getYD();

    final double c2r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c2r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c2r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

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

  public static @Nonnull <A> MatrixM3x3DT<A> translateByVector2DInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final @Nonnull VectorReadable2D v)
  {
    return MatrixM3x3DT.translateByVector2D(m, v, m);
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

  public static @Nonnull <A> MatrixM3x3DT<A> translateByVector2I(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull VectorReadable2I v,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final double vx = v.getXI();
    final double vy = v.getYI();

    final double c2r0 =
      (m.getRowColumnD(0, 0) * vx) + (m.getRowColumnD(0, 1) * vy);
    final double c2r1 =
      (m.getRowColumnD(1, 0) * vx) + (m.getRowColumnD(1, 1) * vy);
    final double c2r2 =
      (m.getRowColumnD(2, 0) * vx) + (m.getRowColumnD(2, 1) * vy);

    out.setUnsafe(0, 2, out.getUnsafe(0, 2) + c2r0);
    out.setUnsafe(1, 2, out.getUnsafe(1, 2) + c2r1);
    out.setUnsafe(2, 2, out.getUnsafe(2, 2) + c2r2);

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

  public static @Nonnull <A> MatrixM3x3DT<A> translateByVector2IInPlace(
    final @Nonnull MatrixM3x3DT<A> m,
    final @Nonnull VectorReadable2I v)
  {
    return MatrixM3x3DT.translateByVector2I(m, v, m);
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

  public static @Nonnull <A> MatrixM3x3DT<A> transpose(
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    final DoubleBuffer source_view = m.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
      out.view.put(index, source_view.get(index));
    }

    out.view.rewind();
    return MatrixM3x3DT.transposeInPlace(out);
  }

  /**
   * Transpose the given matrix <code>m</code>, writing the resulting matrix
   * to <code>m</code>.
   * 
   * @param m
   *          The input matrix.
   * @return <code>m</code>
   */

  public static @Nonnull <A> MatrixM3x3DT<A> transposeInPlace(
    final @Nonnull MatrixM3x3DT<A> m)
  {
    for (int row = 0; row < (MatrixM3x3DT.VIEW_ROWS - 1); row++) {
      for (int column = row + 1; column < MatrixM3x3DT.VIEW_COLS; column++) {
        final double x = m.view.get((row * MatrixM3x3DT.VIEW_ROWS) + column);
        m.view.put(
          (row * MatrixM3x3DT.VIEW_ROWS) + column,
          m.view.get(row + (MatrixM3x3DT.VIEW_COLS * column)));
        m.view.put(row + (MatrixM3x3DT.VIEW_COLS * column), x);
      }
    }

    m.view.rewind();
    return m;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;
  private static final int   VIEW_ELEMENT_SIZE;
  private static final int   VIEW_ELEMENTS;
  private static final int   VIEW_BYTES;
  private static final int   VIEW_COLS;
  private static final int   VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM3x3DT.VIEW_ROWS * MatrixM3x3DT.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3DT.VIEW_ELEMENTS * MatrixM3x3DT.VIEW_ELEMENT_SIZE;
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

  public static <A> void lookAtWithContext(
    final @Nonnull Context<A> context,
    final @Nonnull VectorReadable3D origin,
    final @Nonnull VectorReadable3D target,
    final @Nonnull VectorReadable3D up,
    final @Nonnull MatrixM3x3DT<A> out_matrix,
    final @Nonnull VectorM3D out_translation)
  {
    final VectorM3D forward = context.v3a;
    final VectorM3D new_up = context.v3b;
    final VectorM3D side = context.v3c;

    MatrixM3x3DT.setIdentity(out_matrix);

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

    out_matrix.set(0, 0, side.x);
    out_matrix.set(0, 1, side.y);
    out_matrix.set(0, 2, side.z);
    out_matrix.set(1, 0, new_up.x);
    out_matrix.set(1, 1, new_up.y);
    out_matrix.set(1, 2, new_up.z);
    out_matrix.set(2, 0, -forward.x);
    out_matrix.set(2, 1, -forward.y);
    out_matrix.set(2, 2, -forward.z);

    /**
     * Calculate camera translation matrix
     */

    out_translation.x = -origin.getXD();
    out_translation.y = -origin.getYD();
    out_translation.z = -origin.getZD();
  }

  private static @Nonnull <A> MatrixM3x3DT<A> rotate(
    final double angle,
    final @Nonnull MatrixReadable3x3DT<A> m,
    final @Nonnull MatrixM3x3DT<A> tmp,
    final @Nonnull VectorReadable3D axis,
    final @Nonnull MatrixM3x3DT<A> out)
  {
    MatrixM3x3DT.makeRotation(angle, axis, tmp);
    MatrixM3x3DT.multiply(m, tmp, out);
    out.view.rewind();
    return out;
  }

  public MatrixM3x3DT()
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3DT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();
    MatrixM3x3DT.setIdentity(this);
  }

  public MatrixM3x3DT(
    final @Nonnull MatrixReadable3x3DT<A> source)
  {
    this.data =
      ByteBuffer.allocateDirect(MatrixM3x3DT.VIEW_BYTES).order(
        ByteOrder.nativeOrder());
    this.view = this.data.asDoubleBuffer();

    final DoubleBuffer source_view = source.getDoubleBuffer();
    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
      this.view.put(index, source_view.get(index));
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

    @SuppressWarnings("unchecked") final @Nonnull MatrixM3x3DT<A> other =
      (MatrixM3x3DT<A>) obj;
    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
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
    return MatrixM3x3DT.get(this, row, column);
  }

  @Override public @Nonnull DoubleBuffer getDoubleBuffer()
  {
    return this.view;
  }

  @Override public void getRow3D(
    final int row,
    final @Nonnull VectorM3D out)
  {
    MatrixM3x3DT.rowUnsafe(this, MatrixM3x3DT.rowCheck(row), out);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return MatrixM3x3DT.get(this, row, column);
  }

  private double getUnsafe(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3DT.indexUnsafe(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result);

    for (int index = 0; index < MatrixM3x3DT.VIEW_ELEMENTS; ++index) {
      result += Double.valueOf(this.view.get(index)).hashCode();
    }
    return result;
  }

  public @Nonnull MatrixM3x3DT<A> set(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3DT.indexChecked(row, column), value);
    this.view.rewind();
    return this;
  }

  @Nonnull MatrixM3x3DT<A> setUnsafe(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3DT.indexUnsafe(row, column), value);
    this.view.rewind();
    return this;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < MatrixM3x3DT.VIEW_ROWS; ++row) {
      builder.append("[");
      for (int column = 0; column < MatrixM3x3DT.VIEW_COLS; ++column) {
        builder.append(MatrixM3x3DT.get(this, row, column));
        if (column < (MatrixM3x3DT.VIEW_COLS - 1)) {
          builder.append(" ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }
}
