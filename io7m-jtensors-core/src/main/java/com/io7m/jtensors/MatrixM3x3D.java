/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

import com.io7m.jnull.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

//@formatter:off

/**
 * <p>
 * A 3x3 mutable matrix type with double precision elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM3x3D} are backed by direct memory, with the
 * rows and columns of the matrices being stored in column-major format. This
 * allows the matrices to be passed to OpenGL directly, without requiring
 * transposition.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization.
 * </p>
 * <p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * </p>
 * <p>
 * See http://en.wikipedia.org/wiki/Row_equivalence#Elementary_row_operations
 * for the three <i>elementary</i> operations defined on matrices.
 * </p>
 */

//@formatter:on

public final class MatrixM3x3D implements MatrixDirect3x3DType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixM3x3D.VIEW_ROWS * MatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = MatrixM3x3D.VIEW_ELEMENTS * MatrixM3x3D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public MatrixM3x3D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;
    this.view = v;

    MatrixM3x3D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public MatrixM3x3D(
    final MatrixReadable3x3DType source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(MatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM3x3D.copy(source, this);
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M add(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final M out)
  {
    final double r0c0 = m0.getR0C0D() + m1.getR0C0D();
    final double r1c0 = m0.getR1C0D() + m1.getR1C0D();
    final double r2c0 = m0.getR2C0D() + m1.getR2C0D();

    final double r0c1 = m0.getR0C1D() + m1.getR0C1D();
    final double r1c1 = m0.getR1C1D() + m1.getR1C1D();
    final double r2c1 = m0.getR2C1D() + m1.getR2C1D();

    final double r0c2 = m0.getR0C2D() + m1.getR0C2D();
    final double r1c2 = m0.getR1C2D() + m1.getR1C2D();
    final double r2c2 = m0.getR2C2D() + m1.getR2C2D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r1c0);
    out.setR2C0D(r2c0);

    out.setR0C1D(r0c1);
    out.setR1C1D(r1c1);
    out.setR2C1D(r2c1);

    out.setR0C2D(r0c2);
    out.setR1C2D(r1c2);
    out.setR2C2D(r2c2);

    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m0}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  addInPlace(
    final M m0,
    final MatrixReadable3x3DType m1)
  {
    return MatrixM3x3D.add(m0, m1, m0);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M addRowScaled(
    final ContextMM3D c,
    final MatrixReadable3x3DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final M out)
  {
    return MatrixM3x3D.addRowScaledUnsafe(
      m,
      MatrixM3x3D.rowCheck(row_a),
      MatrixM3x3D.rowCheck(row_b),
      MatrixM3x3D.rowCheck(row_c),
      r,
      c.v3a,
      c.v3b,
      out);
  }

  /**
   * <p> Add the values in row {@code row_b} to the values in row {@code row_a}
   * scaled by {@code r}, saving the resulting row in row {@code row_c} of the
   * matrix {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param <M>   The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  addRowScaledInPlace(
    final ContextMM3D c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return MatrixM3x3D.addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable3x3DType> M addRowScaledUnsafe(
    final MatrixReadable3x3DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM3D va,
    final VectorM3D vb,
    final M out)
  {
    m.getRow3DUnsafe(row_a, va);
    m.getRow3DUnsafe(row_b, vb);
    VectorM3D.addScaledInPlace(va, vb, r);
    out.setRowWith3DUnsafe(row_c, va);
    return out;
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixM3x3D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + MatrixM3x3D.VIEW_COLS);
    }
    return column;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <M>    The precise type of matrix
   *
   * @return {@code output}
   */

  public static <M extends MatrixWritable3x3DType> M copy(
    final MatrixReadable3x3DType input,
    final M output)
  {
    output.setR0C0D(input.getR0C0D());
    output.setR0C1D(input.getR0C1D());
    output.setR0C2D(input.getR0C2D());

    output.setR1C0D(input.getR1C0D());
    output.setR1C1D(input.getR1C1D());
    output.setR1C2D(input.getR1C2D());

    output.setR2C0D(input.getR2C0D());
    output.setR2C1D(input.getR2C1D());
    output.setR2C2D(input.getR2C2D());

    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m The input matrix
   *
   * @return The determinant
   */

  public static double determinant(
    final MatrixReadable3x3DType m)
  {
    final double r0c0 = m.getR0C0D();
    final double r0c1 = m.getR0C1D();
    final double r0c2 = m.getR0C2D();

    final double r1c0 = m.getR1C0D();
    final double r1c1 = m.getR1C1D();
    final double r1c2 = m.getR1C2D();

    final double r2c0 = m.getR2C0D();
    final double r2c1 = m.getR2C1D();
    final double r2c2 = m.getR2C2D();

    double sum = 0.0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M exchangeRows(
    final ContextMM3D c,
    final MatrixReadable3x3DType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return MatrixM3x3D.exchangeRowsUnsafe(
      m,
      MatrixM3x3D.rowCheck(row_a),
      MatrixM3x3D.rowCheck(row_b),
      c.v3a,
      c.v3b,
      out);
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param <M>   The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  exchangeRowsInPlace(
    final ContextMM3D c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return MatrixM3x3D.exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable3x3DType> M exchangeRowsUnsafe(
    final MatrixReadable3x3DType m,
    final int row_a,
    final int row_b,
    final VectorM3D va,
    final VectorM3D vb,
    final M out)
  {
    m.getRow3DUnsafe(row_a, va);
    m.getRow3DUnsafe(row_b, vb);
    out.setRowWith3DUnsafe(row_a, vb);
    out.setRowWith3DUnsafe(row_b, va);
    return out;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixM3x3D.indexUnsafe(
      MatrixM3x3D.rowCheck(row), MatrixM3x3D.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   */

  public static <M extends MatrixWritable3x3DType> boolean invert(
    final ContextMM3D c,
    final MatrixReadable3x3DType m,
    final M out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final double orig_r0c0 = m.getR0C0D();
    final double orig_r0c1 = m.getR0C1D();
    final double orig_r0c2 = m.getR0C2D();

    final double orig_r1c0 = m.getR1C0D();
    final double orig_r1c1 = m.getR1C1D();
    final double orig_r1c2 = m.getR1C2D();

    final double orig_r2c0 = m.getR2C0D();
    final double orig_r2c1 = m.getR2C1D();
    final double orig_r2c2 = m.getR2C2D();

    final double r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final double r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final double r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final double r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final double r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final double r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final double r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final double r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final double r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    final MatrixM3x3D temp = c.m3a;

    temp.setR0C0D(r0c0);
    temp.setR0C1D(r0c1);
    temp.setR0C2D(r0c2);

    temp.setR1C0D(r1c0);
    temp.setR1C1D(r1c1);
    temp.setR1C2D(r1c2);

    temp.setR2C0D(r2c0);
    temp.setR2C1D(r2c1);
    temp.setR2C2D(r2c2);

    MatrixM3x3D.scale(temp, d_inv, out);
    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3D#determinant(MatrixReadable3x3DType)
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType>
  boolean invertInPlace(
    final ContextMM3D c,
    final M m)
  {
    return MatrixM3x3D.invert(c, m, m);
  }

  /**
   * <p> Calculate a rotation and translation representing a "camera" looking
   * from the point {@code origin} to the point {@code target}. {@code target}
   * must represent the "up" vector for the camera. Usually, this is simply a
   * unit vector {@code (0, 1, 0)} representing the Y axis. </p> <p> The
   * function uses preallocated storage from {@code context}. </p> <p> The view
   * is expressed as a rotation matrix and a translation vector, written to
   * {@code out_matrix} and {@code out_translation}, respectively. </p>
   *
   * @param context         Preallocated storage
   * @param out_matrix      The output matrix
   * @param out_translation The output translation
   * @param origin          The position of the viewer
   * @param target          The target being viewed
   * @param up              The up vector
   * @param <M>             The precise type of matrix
   * @param <V>             The precise type of vector
   */

  public static <M extends MatrixWritable3x3DType, V extends
    VectorWritable3DType> void lookAt(
    final ContextMM3D context,
    final VectorReadable3DType origin,
    final VectorReadable3DType target,
    final VectorReadable3DType up,
    final M out_matrix,
    final V out_translation)
  {
    final VectorM3D forward = context.v3a;
    final VectorM3D new_up = context.v3b;
    final VectorM3D side = context.v3c;

    MatrixM3x3D.setIdentity(out_matrix);

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

    out_matrix.setR0C0D(side.getXD());
    out_matrix.setR0C1D(side.getYD());
    out_matrix.setR0C2D(side.getZD());
    out_matrix.setR1C0D(new_up.getXD());
    out_matrix.setR1C1D(new_up.getYD());
    out_matrix.setR1C2D(new_up.getZD());
    out_matrix.setR2C0D(-forward.getXD());
    out_matrix.setR2C1D(-forward.getYD());
    out_matrix.setR2C2D(-forward.getZD());

    /**
     * Calculate camera translation matrix
     */

    out_translation.set3D(-origin.getXD(), -origin.getYD(), -origin.getZD());
  }

  /**
   * <p> Generate a matrix that represents a rotation of {@code angle} radians
   * around the axis {@code axis} and save to {@code out}. </p> <p> The function
   * assumes a right-handed coordinate system and therefore a positive rotation
   * around any axis represents a counter-clockwise rotation around that axis.
   * </p>
   *
   * @param angle The angle in radians
   * @param axis  The axis
   * @param out   The output matrix
   * @param <M>   The precise type of matrix
   *
   * @return {@code out}
   *
   * @since 7.0.0
   */

  public static <M extends MatrixWritable3x3DType> M makeRotation(
    final double angle,
    final VectorReadable3DType axis,
    final M out)
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

    final double r1c0 = txy + sz;
    final double r1c1 = ty_sq + c;
    final double r1c2 = tyz - sx;

    final double r2c0 = txz - sy;
    final double r2c1 = tyz + sx;
    final double r2c2 = tz_sq + c;

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR0C2D(r0c2);

    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    out.setR1C2D(r1c2);

    out.setR2C0D(r2c0);
    out.setR2C1D(r2c1);
    out.setR2C2D(r2c2);

    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M makeTranslation2D(
    final VectorReadable2DType v,
    final M out)
  {
    out.setR0C0D(1.0);
    out.setR0C1D(0.0);
    out.setR0C2D(v.getXD());

    out.setR1C0D(0.0);
    out.setR1C1D(1.0);
    out.setR1C2D(v.getYD());

    out.setR2C0D(0.0);
    out.setR2C1D(0.0);
    out.setR2C2D(1.0);
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param v   The translation vector
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M makeTranslation2I(
    final VectorReadable2IType v,
    final M out)
  {
    out.setR0C0D(1.0);
    out.setR0C1D(0.0);
    out.setR0C2D((double) v.getXI());

    out.setR1C0D(0.0);
    out.setR1C1D(1.0);
    out.setR1C2D((double) v.getYI());

    out.setR2C0D(0.0);
    out.setR2C1D(0.0);
    out.setR2C2D(1.0);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0  The left input vector
   * @param m1  The right input vector
   * @param out The output vector
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M multiply(
    final MatrixReadable3x3DType m0,
    final MatrixReadable3x3DType m1,
    final M out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getR0C0D() * m1.getR0C0D();
    r0c0 += m0.getR0C1D() * m1.getR1C0D();
    r0c0 += m0.getR0C2D() * m1.getR2C0D();

    double r1c0 = 0.0;
    r1c0 += m0.getR1C0D() * m1.getR0C0D();
    r1c0 += m0.getR1C1D() * m1.getR1C0D();
    r1c0 += m0.getR1C2D() * m1.getR2C0D();

    double r2c0 = 0.0;
    r2c0 += m0.getR2C0D() * m1.getR0C0D();
    r2c0 += m0.getR2C1D() * m1.getR1C0D();
    r2c0 += m0.getR2C2D() * m1.getR2C0D();

    double r0c1 = 0.0;
    r0c1 += m0.getR0C0D() * m1.getR0C1D();
    r0c1 += m0.getR0C1D() * m1.getR1C1D();
    r0c1 += m0.getR0C2D() * m1.getR2C1D();

    double r1c1 = 0.0;
    r1c1 += m0.getR1C0D() * m1.getR0C1D();
    r1c1 += m0.getR1C1D() * m1.getR1C1D();
    r1c1 += m0.getR1C2D() * m1.getR2C1D();

    double r2c1 = 0.0;
    r2c1 += m0.getR2C0D() * m1.getR0C1D();
    r2c1 += m0.getR2C1D() * m1.getR1C1D();
    r2c1 += m0.getR2C2D() * m1.getR2C1D();

    double r0c2 = 0.0;
    r0c2 += m0.getR0C0D() * m1.getR0C2D();
    r0c2 += m0.getR0C1D() * m1.getR1C2D();
    r0c2 += m0.getR0C2D() * m1.getR2C2D();

    double r1c2 = 0.0;
    r1c2 += m0.getR1C0D() * m1.getR0C2D();
    r1c2 += m0.getR1C1D() * m1.getR1C2D();
    r1c2 += m0.getR1C2D() * m1.getR2C2D();

    double r2c2 = 0.0;
    r2c2 += m0.getR2C0D() * m1.getR0C2D();
    r2c2 += m0.getR2C1D() * m1.getR1C2D();
    r2c2 += m0.getR2C2D() * m1.getR2C2D();

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR0C2D(r0c2);

    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    out.setR1C2D(r1c2);

    out.setR2C0D(r2c0);
    out.setR2C1D(r2c1);
    out.setR2C2D(r2c2);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param m0  The left input vector
   * @param m1  The right input vector
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable3x3DType m1)
  {
    return MatrixM3x3D.multiply(m0, m1, m0);
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}.
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <V extends VectorWritable3DType> V multiplyVector3D(
    final ContextMM3D c,
    final MatrixReadable3x3DType m,
    final VectorReadable3DType v,
    final V out)
  {
    final VectorM3D va = c.v3a;
    final VectorM3D vb = c.v3b;

    vb.copyFrom3D(v);

    m.getRow3DUnsafe(0, va);
    out.setXD(VectorM3D.dotProduct(va, vb));
    m.getRow3DUnsafe(1, va);
    out.setYD(VectorM3D.dotProduct(va, vb));
    m.getRow3DUnsafe(2, va);
    out.setZD(VectorM3D.dotProduct(va, vb));

    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixM3x3D.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixM3x3D.VIEW_ROWS);
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M scale(
    final MatrixReadable3x3DType m,
    final double r,
    final M out)
  {
    final double r0c0 = m.getR0C0D() * r;
    final double r1c0 = m.getR1C0D() * r;
    final double r2c0 = m.getR2C0D() * r;

    final double r0c1 = m.getR0C1D() * r;
    final double r1c1 = m.getR1C1D() * r;
    final double r2c1 = m.getR2C1D() * r;

    final double r0c2 = m.getR0C2D() * r;
    final double r1c2 = m.getR1C2D() * r;
    final double r2c2 = m.getR2C2D() * r;

    out.setR0C0D(r0c0);
    out.setR1C0D(r1c0);
    out.setR2C0D(r2c0);

    out.setR0C1D(r0c1);
    out.setR1C1D(r1c1);
    out.setR2C1D(r2c1);

    out.setR0C2D(r0c2);
    out.setR1C2D(r1c2);
    out.setR2C2D(r2c2);

    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param m   The input matrix
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  scaleInPlace(
    final M m,
    final double r)
  {
    return MatrixM3x3D.scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 3)}
   * @param r   The scaling value
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M scaleRow(
    final ContextMM3D c,
    final MatrixReadable3x3DType m,
    final int row,
    final double r,
    final M out)
  {
    return MatrixM3x3D.scaleRowUnsafe(
      m, MatrixM3x3D.rowCheck(row), r, c.v3a, out);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 3)}
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  scaleRowInPlace(
    final ContextMM3D c,
    final M m,
    final int row,
    final double r)
  {
    return MatrixM3x3D.scaleRowUnsafe(
      m, MatrixM3x3D.rowCheck(row), r, c.v3a, m);
  }

  private static <M extends MatrixWritable3x3DType> M scaleRowUnsafe(
    final MatrixReadable3x3DType m,
    final int row,
    final double r,
    final VectorM3D tmp,
    final M out)
  {
    m.getRow3DUnsafe(row, tmp);
    VectorM3D.scaleInPlace(tmp, r);
    out.setRowWith3DUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType> M setIdentity(
    final M m)
  {
    m.setR0C0D(1.0);
    m.setR1C0D(0.0);
    m.setR2C0D(0.0);

    m.setR0C1D(0.0);
    m.setR1C1D(1.0);
    m.setR2C1D(0.0);

    m.setR0C2D(0.0);
    m.setR1C2D(0.0);
    m.setR2C2D(1.0);

    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType> M setZero(
    final M m)
  {
    m.setR0C0D(0.0);
    m.setR1C0D(0.0);
    m.setR2C0D(0.0);

    m.setR0C1D(0.0);
    m.setR1C1D(0.0);
    m.setR2C1D(0.0);

    m.setR0C2D(0.0);
    m.setR1C2D(0.0);
    m.setR2C2D(0.0);

    return m;
  }

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as the sum
   * of the diagonal elements of the matrix.
   *
   * @param m The input matrix
   *
   * @return The trace of the matrix
   *
   * @since 5.0.0
   */

  public static double trace(
    final MatrixReadable3x3DType m)
  {
    return m.getR0C0D() + m.getR1C1D() + m.getR2C2D();
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param m   The input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3DType> M transpose(
    final MatrixReadable3x3DType m,
    final M out)
  {
    final double r0c0 = m.getR0C0D();
    final double r1c0 = m.getR1C0D();
    final double r2c0 = m.getR2C0D();

    final double r0c1 = m.getR0C1D();
    final double r1c1 = m.getR1C1D();
    final double r2c1 = m.getR2C1D();

    final double r0c2 = m.getR0C2D();
    final double r1c2 = m.getR1C2D();
    final double r2c2 = m.getR2C2D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r0c1); // swap 0
    out.setR2C0D(r0c2); // swap 1

    out.setR0C1D(r1c0); // swap 0
    out.setR1C1D(r1c1);
    out.setR2C1D(r1c2); // swap 2

    out.setR0C2D(r2c0); // swap 1
    out.setR1C2D(r2c1); // swap 2
    out.setR2C2D(r2c2);

    return out;
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3DType & MatrixReadable3x3DType> M
  transposeInPlace(
    final M m)
  {
    final double r1c0 = m.getR1C0D();
    final double r2c0 = m.getR2C0D();

    final double r0c1 = m.getR0C1D();
    final double r2c1 = m.getR2C1D();

    final double r0c2 = m.getR0C2D();
    final double r1c2 = m.getR1C2D();

    m.setR1C0D(r0c1); // swap 0
    m.setR2C0D(r0c2); // swap 1

    m.setR0C1D(r1c0); // swap 0
    m.setR2C1D(r1c2); // swap 2

    m.setR0C2D(r2c0); // swap 1
    m.setR1C2D(r2c1); // swap 2

    return m;
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
    final MatrixM3x3D other = (MatrixM3x3D) obj;

    for (int index = 0; index < MatrixM3x3D.VIEW_ELEMENTS; ++index) {
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
    MatrixM3x3D.rowCheck(row);
    this.getRow3DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(MatrixM3x3D.indexUnsafe(row, 0));
    final double y = this.view.get(MatrixM3x3D.indexUnsafe(row, 1));
    final double z = this.view.get(MatrixM3x3D.indexUnsafe(row, 2));
    out.set3D(x, y, z);
  }

  @Override public double getR0C2D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(0, 2));
  }

  @Override public void setR0C2D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    MatrixM3x3D.rowCheck(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, 0), v.getXD());
    this.view.put(MatrixM3x3D.indexUnsafe(row, 1), v.getYD());
    this.view.put(MatrixM3x3D.indexUnsafe(row, 2), v.getZD());
  }

  @Override public double getR1C2D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(1, 2));
  }

  @Override public void setR1C2D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(1, 2), x);
  }

  @Override public double getR2C0D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(2, 0));
  }

  @Override public void setR2C0D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(2, 0), x);
  }

  @Override public double getR2C1D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(2, 1));
  }

  @Override public void setR2C1D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(2, 1), x);
  }

  @Override public double getR2C2D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(2, 2));
  }

  @Override public void setR2C2D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(2, 2), x);
  }

  public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(MatrixM3x3D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C1D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C2D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(MatrixM3x3D.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixM3x3D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(MatrixM3x3D.indexUnsafe(row, 0));
      final double c1 = this.view.get(MatrixM3x3D.indexUnsafe(row, 1));
      final double c2 = this.view.get(MatrixM3x3D.indexUnsafe(row, 2));
      final String s = String.format("[%+.15f %+.15f %+.15f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    MatrixM3x3D.rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(MatrixM3x3D.indexUnsafe(row, 0));
    final double y = this.view.get(MatrixM3x3D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    MatrixM3x3D.rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(row, 0), v.getXD());
    this.view.put(MatrixM3x3D.indexUnsafe(row, 1), v.getYD());
  }

  @Override public double getR1C0D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(MatrixM3x3D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(MatrixM3x3D.indexUnsafe(1, 1), x);
  }

  /**
   * <p>The {@code ContextMM3D} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM3x3D} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM3D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM3D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM3D
  {
    private final MatrixM3x3D m3a = new MatrixM3x3D();
    private final VectorM3D   v3a = new VectorM3D();
    private final VectorM3D   v3b = new VectorM3D();
    private final VectorM3D   v3c = new VectorM3D();

    /**
     * Construct a new context.
     */

    public ContextMM3D()
    {

    }
  }
}
