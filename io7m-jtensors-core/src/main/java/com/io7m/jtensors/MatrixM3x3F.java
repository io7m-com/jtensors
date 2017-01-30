/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.junreachable.UnreachableCodeException;

//@formatter:off

/**
 * <p>
 * A 3x3 mutable matrix type with {@code float} elements.
 * </p>
 * <p>
 * Values of type {@code MatrixM3x3F} are backed by direct memory, with the
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

public final class MatrixM3x3F
{
  private MatrixM3x3F()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M add(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1,
    final M out)
  {
    final float r0c0 = m0.getR0C0F() + m1.getR0C0F();
    final float r1c0 = m0.getR1C0F() + m1.getR1C0F();
    final float r2c0 = m0.getR2C0F() + m1.getR2C0F();

    final float r0c1 = m0.getR0C1F() + m1.getR0C1F();
    final float r1c1 = m0.getR1C1F() + m1.getR1C1F();
    final float r2c1 = m0.getR2C1F() + m1.getR2C1F();

    final float r0c2 = m0.getR0C2F() + m1.getR0C2F();
    final float r1c2 = m0.getR1C2F() + m1.getR1C2F();
    final float r2c2 = m0.getR2C2F() + m1.getR2C2F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);
    out.setR2C0F(r2c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    out.setR2C1F(r2c1);

    out.setR0C2F(r0c2);
    out.setR1C2F(r1c2);
    out.setR2C2F(r2c2);
    return out;
  }

  /**
   * Elementwise add of matrices {@code m0} and {@code m1}, returning the result
   * in {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   *
   * @return {@code m0}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  addInPlace(
    final M m0,
    final MatrixReadable3x3FType m1)
  {
    return add(m0, m1, m0);
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
   * @param <M>   The precise type of matrix
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M addRowScaled(
    final ContextMM3F c,
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final M out)
  {
    return addRowScaledUnsafe(
      m,
      rowCheck(row_a),
      rowCheck(row_b),
      rowCheck(row_c),
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
   * @param <M>   The precise type of matrix
   * @param m     The input matrix
   * @param row_a The row on the lefthand side of the addition
   * @param row_b The row on the righthand side of the addition
   * @param row_c The destination row
   * @param r     The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  addRowScaledInPlace(
    final ContextMM3F c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable3x3FType> M addRowScaledUnsafe(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM3F va,
    final VectorM3F vb,
    final M out)
  {
    m.getRow3FUnsafe(row_a, va);
    m.getRow3FUnsafe(row_b, vb);
    VectorM3F.addScaledInPlace(va, vb, r);
    out.setRowWith3FUnsafe(row_c, va);
    return out;
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param <M>    The precise type of matrix
   * @param input  The input vector
   * @param output The output vector
   *
   * @return {@code output}
   */

  public static <M extends MatrixWritable3x3FType> M copy(
    final MatrixReadable3x3FType input,
    final M output)
  {
    output.setR0C0F(input.getR0C0F());
    output.setR0C1F(input.getR0C1F());
    output.setR0C2F(input.getR0C2F());

    output.setR1C0F(input.getR1C0F());
    output.setR1C1F(input.getR1C1F());
    output.setR1C2F(input.getR1C2F());

    output.setR2C0F(input.getR2C0F());
    output.setR2C1F(input.getR2C1F());
    output.setR2C2F(input.getR2C2F());

    return output;
  }

  /**
   * Calculate the determinant of the matrix {@code m}.
   *
   * @param m The input matrix
   *
   * @return The determinant.
   */

  public static double determinant(
    final MatrixReadable3x3FType m)
  {
    final double r0c0 = (double) m.getR0C0F();
    final double r0c1 = (double) m.getR0C1F();
    final double r0c2 = (double) m.getR0C2F();

    final double r1c0 = (double) m.getR1C0F();
    final double r1c1 = (double) m.getR1C1F();
    final double r1c2 = (double) m.getR1C2F();

    final double r2c0 = (double) m.getR2C0F();
    final double r2c1 = (double) m.getR2C1F();
    final double r2c2 = (double) m.getR2C2F();

    double sum = 0.0;

    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));

    return sum;
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out} . </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param <M>   The precise type of matrix
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M exchangeRows(
    final ContextMM3F c,
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return exchangeRowsUnsafe(
      m,
      rowCheck(row_a),
      rowCheck(row_b),
      c.v3a,
      c.v3b,
      out);
  }

  /**
   * <p> Exchange the row {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m} . </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c     Preallocated storage
   * @param <M>   The precise type of matrix
   * @param m     The input matrix
   * @param row_a The first row
   * @param row_b The second row
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  exchangeRowsInPlace(
    final ContextMM3F c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable3x3FType> M exchangeRowsUnsafe(
    final MatrixReadable3x3FType m,
    final int row_a,
    final int row_b,
    final VectorM3F va,
    final VectorM3F vb,
    final M out)
  {
    m.getRow3FUnsafe(row_a, va);
    m.getRow3FUnsafe(row_b, vb);
    out.setRowWith3FUnsafe(row_a, vb);
    out.setRowWith3FUnsafe(row_b, va);
    return out;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}.
   *
   * @param c   Preallocated storage
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3F#determinant(MatrixReadable3x3FType)
   */

  public static <M extends MatrixWritable3x3FType> boolean invert(
    final ContextMM3F c,
    final MatrixReadable3x3FType m,
    final M out)
  {
    final double d = determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final double orig_r0c0 = (double) m.getR0C0F();
    final double orig_r0c1 = (double) m.getR0C1F();
    final double orig_r0c2 = (double) m.getR0C2F();

    final double orig_r1c0 = (double) m.getR1C0F();
    final double orig_r1c1 = (double) m.getR1C1F();
    final double orig_r1c2 = (double) m.getR1C2F();

    final double orig_r2c0 = (double) m.getR2C0F();
    final double orig_r2c1 = (double) m.getR2C1F();
    final double orig_r2c2 = (double) m.getR2C2F();

    final double r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final double r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final double r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final double r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final double r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final double r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final double r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final double r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final double r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    final Matrix3x3FType temp = c.m3a;

    temp.setR0C0F((float) r0c0);
    temp.setR0C1F((float) r0c1);
    temp.setR0C2F((float) r0c2);

    temp.setR1C0F((float) r1c0);
    temp.setR1C1F((float) r1c1);
    temp.setR1C2F((float) r1c2);

    temp.setR2C0F((float) r2c0);
    temp.setR2C1F((float) r2c1);
    temp.setR2C2F((float) r2c2);

    scale(temp, d_inv, out);
    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}.
   *
   * @param c   Preallocated storage
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3F#determinant(MatrixReadable3x3FType)
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType>
  boolean invertInPlace(
    final ContextMM3F c,
    final M m)
  {
    return invert(c, m, m);
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
   * @param <M>             The precise type of matrix
   * @param context         Preallocated storage
   * @param out_matrix      The output matrix
   * @param out_translation The output translation
   * @param origin          The position of the viewer
   * @param target          The target being viewed
   * @param up              The up vector
   * @param <V>             The precise type of writable vector
   */

  public static <V extends VectorWritable3FType, M extends
    MatrixWritable3x3FType> void lookAt(
    final ContextMM3F context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final M out_matrix,
    final V out_translation)
  {
    final VectorM3F forward = context.v3a;
    final VectorM3F new_up = context.v3b;
    final VectorM3F side = context.v3c;

    setIdentity(out_matrix);

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

    out_matrix.setR0C0F(side.getXF());
    out_matrix.setR0C1F(side.getYF());
    out_matrix.setR0C2F(side.getZF());
    out_matrix.setR1C0F(new_up.getXF());
    out_matrix.setR1C1F(new_up.getYF());
    out_matrix.setR1C2F(new_up.getZF());
    out_matrix.setR2C0F(-forward.getXF());
    out_matrix.setR2C1F(-forward.getYF());
    out_matrix.setR2C2F(-forward.getZF());

    /**
     * Calculate camera translation matrix
     */

    out_translation.set3F(-origin.getXF(), -origin.getYF(), -origin.getZF());
  }

  /**
   * <p> Generate a matrix that represents a rotation of {@code angle} radians
   * around the axis {@code axis} and save to {@code out}. </p> <p> The function
   * assumes a right-handed coordinate system and therefore a positive rotation
   * around any axis represents a counter-clockwise rotation around that axis.
   * </p>
   *
   * @param <M>   The precise type of matrix
   * @param angle The angle in radians
   * @param axis  The axis
   * @param out   The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M makeRotation(
    final double angle,
    final VectorReadable3FType axis,
    final M out)
  {
    final double axis_x = (double) axis.getXF();
    final double axis_y = (double) axis.getYF();
    final double axis_z = (double) axis.getZF();

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

    out.setR0C0F((float) r0c0);
    out.setR0C1F((float) r0c1);
    out.setR0C2F((float) r0c2);

    out.setR1C0F((float) r1c0);
    out.setR1C1F((float) r1c1);
    out.setR1C2F((float) r1c2);

    out.setR2C0F((float) r2c0);
    out.setR2C1F((float) r2c1);
    out.setR2C2F((float) r2c2);
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param v   The translation vector
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M makeTranslation2F(
    final VectorReadable2FType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F(v.getXF());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F(v.getYF());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    return out;
  }

  /**
   * Create a translation matrix that represents a translation by the vector
   * {@code v}, writing the resulting matrix to {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param v   The translation vector
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M makeTranslation2I(
    final VectorReadable2IType v,
    final M out)
  {
    out.setR0C0F(1.0f);
    out.setR0C1F(0.0f);
    out.setR0C2F((float) v.getXI());

    out.setR1C0F(0.0f);
    out.setR1C1F(1.0f);
    out.setR1C2F((float) v.getYI());

    out.setR2C0F(0.0f);
    out.setR2C1F(0.0f);
    out.setR2C2F(1.0f);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input vector
   * @param m1  The right input vector
   * @param out The output vector
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M multiply(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1,
    final M out)
  {
    double r0c0 = 0.0;
    r0c0 += (double) (m0.getR0C0F() * m1.getR0C0F());
    r0c0 += (double) (m0.getR0C1F() * m1.getR1C0F());
    r0c0 += (double) (m0.getR0C2F() * m1.getR2C0F());

    double r1c0 = 0.0;
    r1c0 += (double) (m0.getR1C0F() * m1.getR0C0F());
    r1c0 += (double) (m0.getR1C1F() * m1.getR1C0F());
    r1c0 += (double) (m0.getR1C2F() * m1.getR2C0F());

    double r2c0 = 0.0;
    r2c0 += (double) (m0.getR2C0F() * m1.getR0C0F());
    r2c0 += (double) (m0.getR2C1F() * m1.getR1C0F());
    r2c0 += (double) (m0.getR2C2F() * m1.getR2C0F());

    double r0c1 = 0.0;
    r0c1 += (double) (m0.getR0C0F() * m1.getR0C1F());
    r0c1 += (double) (m0.getR0C1F() * m1.getR1C1F());
    r0c1 += (double) (m0.getR0C2F() * m1.getR2C1F());

    double r1c1 = 0.0;
    r1c1 += (double) (m0.getR1C0F() * m1.getR0C1F());
    r1c1 += (double) (m0.getR1C1F() * m1.getR1C1F());
    r1c1 += (double) (m0.getR1C2F() * m1.getR2C1F());

    double r2c1 = 0.0;
    r2c1 += (double) (m0.getR2C0F() * m1.getR0C1F());
    r2c1 += (double) (m0.getR2C1F() * m1.getR1C1F());
    r2c1 += (double) (m0.getR2C2F() * m1.getR2C1F());

    double r0c2 = 0.0;
    r0c2 += (double) (m0.getR0C0F() * m1.getR0C2F());
    r0c2 += (double) (m0.getR0C1F() * m1.getR1C2F());
    r0c2 += (double) (m0.getR0C2F() * m1.getR2C2F());

    double r1c2 = 0.0;
    r1c2 += (double) (m0.getR1C0F() * m1.getR0C2F());
    r1c2 += (double) (m0.getR1C1F() * m1.getR1C2F());
    r1c2 += (double) (m0.getR1C2F() * m1.getR2C2F());

    double r2c2 = 0.0;
    r2c2 += (double) (m0.getR2C0F() * m1.getR0C2F());
    r2c2 += (double) (m0.getR2C1F() * m1.getR1C2F());
    r2c2 += (double) (m0.getR2C2F() * m1.getR2C2F());

    out.setR0C0F((float) r0c0);
    out.setR0C1F((float) r0c1);
    out.setR0C2F((float) r0c2);

    out.setR1C0F((float) r1c0);
    out.setR1C1F((float) r1c1);
    out.setR1C2F((float) r1c2);

    out.setR2C0F((float) r2c0);
    out.setR2C1F((float) r2c1);
    out.setR2C2F((float) r2c2);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param <M> The precise type of matrix
   * @param m0  The left input vector
   * @param m1  The right input vector
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable3x3FType m1)
  {
    return multiply(m0, m1, m0);
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

  public static <V extends VectorWritable3FType> V multiplyVector3F(
    final ContextMM3F c,
    final MatrixReadable3x3FType m,
    final VectorReadable3FType v,
    final V out)
  {
    final VectorM3F va = c.v3a;
    final VectorM3F vb = c.v3b;

    vb.copyFrom3F(v);

    m.getRow3FUnsafe(0, va);
    out.setXF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(1, va);
    out.setYF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(2, va);
    out.setZF((float) VectorM3F.dotProduct(va, vb));

    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= 3)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 3");
    }
    return row;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M scale(
    final MatrixReadable3x3FType m,
    final double r,
    final M out)
  {
    final float r0c0 = (float) ((double) m.getR0C0F() * r);
    final float r1c0 = (float) ((double) m.getR1C0F() * r);
    final float r2c0 = (float) ((double) m.getR2C0F() * r);

    final float r0c1 = (float) ((double) m.getR0C1F() * r);
    final float r1c1 = (float) ((double) m.getR1C1F() * r);
    final float r2c1 = (float) ((double) m.getR2C1F() * r);

    final float r0c2 = (float) ((double) m.getR0C2F() * r);
    final float r1c2 = (float) ((double) m.getR1C2F() * r);
    final float r2c2 = (float) ((double) m.getR2C2F() * r);

    out.setR0C0F(r0c0);
    out.setR1C0F(r1c0);
    out.setR2C0F(r2c0);

    out.setR0C1F(r0c1);
    out.setR1C1F(r1c1);
    out.setR2C1F(r2c1);

    out.setR0C2F(r0c2);
    out.setR1C2F(r1c2);
    out.setR2C2F(r2c2);

    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  scaleInPlace(
    final M m,
    final double r)
  {
    return scale(m, r, m);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code out}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 3)}
   * @param r   The scaling value
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M scaleRow(
    final ContextMM3F c,
    final MatrixReadable3x3FType m,
    final int row,
    final double r,
    final M out)
  {
    return scaleRowUnsafe(
      m, rowCheck(row), r, c.v3a, out);
  }

  /**
   * <p> Scale row {@code r} of the matrix {@code m} by {@code r}, saving the
   * result to row {@code r} of {@code m}. </p>
   *
   * <p> This is one of the three <i>elementary</i> operations defined on
   * matrices. </p>
   *
   * @param c   Preallocated storage
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 3)}
   * @param r   The scaling value
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  scaleRowInPlace(
    final ContextMM3F c,
    final M m,
    final int row,
    final double r)
  {
    return scaleRowUnsafe(
      m, rowCheck(row), r, c.v3a, m);
  }

  private static <M extends MatrixWritable3x3FType> M scaleRowUnsafe(
    final MatrixReadable3x3FType m,
    final int row,
    final double r,
    final VectorM3F tmp,
    final M out)
  {
    m.getRow3FUnsafe(row, tmp);
    VectorM3F.scaleInPlace(tmp, r);
    out.setRowWith3FUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType> M setIdentity(
    final M m)
  {
    m.setR0C0F(1.0f);
    m.setR1C0F(0.0f);
    m.setR2C0F(0.0f);

    m.setR0C1F(0.0f);
    m.setR1C1F(1.0f);
    m.setR2C1F(0.0f);

    m.setR0C2F(0.0f);
    m.setR1C2F(0.0f);
    m.setR2C2F(1.0f);

    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param <M> The precise type of matrix
   * @param m   The matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType> M setZero(
    final M m)
  {
    m.setR0C0F(0.0f);
    m.setR1C0F(0.0f);
    m.setR2C0F(0.0f);

    m.setR0C1F(0.0f);
    m.setR1C1F(0.0f);
    m.setR2C1F(0.0f);

    m.setR0C2F(0.0f);
    m.setR1C2F(0.0f);
    m.setR2C2F(0.0f);

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
    final MatrixReadable3x3FType m)
  {
    return (double) (m.getR0C0F() + m.getR1C1F() + m.getR2C2F());
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code out}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   * @param out The output matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable3x3FType> M transpose(
    final MatrixReadable3x3FType m,
    final M out)
  {
    final float r0c0 = m.getR0C0F();
    final float r1c0 = m.getR1C0F();
    final float r2c0 = m.getR2C0F();

    final float r0c1 = m.getR0C1F();
    final float r1c1 = m.getR1C1F();
    final float r2c1 = m.getR2C1F();

    final float r0c2 = m.getR0C2F();
    final float r1c2 = m.getR1C2F();
    final float r2c2 = m.getR2C2F();

    out.setR0C0F(r0c0);
    out.setR1C0F(r0c1); // swap 0
    out.setR2C0F(r0c2); // swap 1

    out.setR0C1F(r1c0); // swap 0
    out.setR1C1F(r1c1);
    out.setR2C1F(r1c2); // swap 2

    out.setR0C2F(r2c0); // swap 1
    out.setR1C2F(r2c1); // swap 2
    out.setR2C2F(r2c2);

    return out;
  }

  /**
   * Transpose the given matrix {@code m}, writing the resulting matrix to
   * {@code m}.
   *
   * @param <M> The precise type of matrix
   * @param m   The input matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable3x3FType & MatrixReadable3x3FType> M
  transposeInPlace(
    final M m)
  {
    final float r1c0 = m.getR1C0F();
    final float r2c0 = m.getR2C0F();

    final float r0c1 = m.getR0C1F();
    final float r2c1 = m.getR2C1F();

    final float r0c2 = m.getR0C2F();
    final float r1c2 = m.getR1C2F();

    m.setR1C0F(r0c1); // swap 0
    m.setR2C0F(r0c2); // swap 1

    m.setR0C1F(r1c0); // swap 0
    m.setR2C1F(r1c2); // swap 2

    m.setR0C2F(r2c0); // swap 1
    m.setR1C2F(r2c1); // swap 2

    return m;
  }

  /**
   * Compare matrices.
   *
   * @param m0 The left matrix
   * @param m1 The right matrix
   *
   * @return {@code true} if all elements of {@code m0} are equal to {@code m1}.
   *
   * @since 7.0.0
   */

  public static boolean compareElements(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1)
  {
    if (!compareRow0(m0, m1)) {
      return false;
    }
    return compareRow1(m0, m1) && compareRow2(m0, m1);
  }

  /**
   * Hash matrices.
   *
   * @param m The input matrix
   *
   * @return The hash of all the elements of {@code m}
   *
   * @since 7.0.0
   */

  public static int hashElements(final MatrixReadable3x3FType m)
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(m.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR1C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR2C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(m.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR1C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR2C1F(), prime, r);

    r = HashUtility.accumulateFloatHash(m.getR0C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR1C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(m.getR2C2F(), prime, r);

    return r;
  }

  /**
   * Show matrices. Print all of the elements of {@code m} in square-bracketed
   * matrix form.
   *
   * @param m  The input matrix
   * @param sb The string builder
   *
   * @since 7.0.0
   */

  public static void showElements(
    final MatrixReadable3x3FType m,
    final StringBuilder sb)
  {
    final String row0 = String.format(
      "[%+.6f %+.6f %+.6f]\n",
      Float.valueOf(m.getR0C0F()), Float.valueOf(m.getR0C1F()),
      Float.valueOf(m.getR0C2F()));
    final String row1 = String.format(
      "[%+.6f %+.6f %+.6f]\n",
      Float.valueOf(m.getR1C0F()), Float.valueOf(m.getR1C1F()),
      Float.valueOf(m.getR1C2F()));
    final String row2 = String.format(
      "[%+.6f %+.6f %+.6f]\n",
      Float.valueOf(m.getR2C0F()), Float.valueOf(m.getR2C1F()),
      Float.valueOf(m.getR2C2F()));
    sb.append(row0);
    sb.append(row1);
    sb.append(row2);
  }

  private static boolean compareRow0(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1)
  {
    if (m0.getR0C0F() != m1.getR0C0F()) {
      return false;
    }
    return !(m0.getR0C1F() != m1.getR0C1F()) && m0.getR0C2F() == m1.getR0C2F();
  }

  private static boolean compareRow1(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1)
  {
    if (m0.getR1C0F() != m1.getR1C0F()) {
      return false;
    }
    return !(m0.getR1C1F() != m1.getR1C1F()) && m0.getR1C2F() == m1.getR1C2F();
  }

  private static boolean compareRow2(
    final MatrixReadable3x3FType m0,
    final MatrixReadable3x3FType m1)
  {
    if (m0.getR2C0F() != m1.getR2C0F()) {
      return false;
    }
    return !(m0.getR2C1F() != m1.getR2C1F()) && m0.getR2C2F() == m1.getR2C2F();
  }

  /**
   * <p>The {@code ContextMM3F} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM3x3F} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p>The user should allocate one {@code ContextMM3F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM3F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM3F
  {
    private final Matrix3x3FType m3a = MatrixHeapArrayM3x3F.newMatrix();
    private final VectorM3F v3a = new VectorM3F();
    private final VectorM3F v3b = new VectorM3F();
    private final VectorM3F v3c = new VectorM3F();

    /**
     * Construct a new context.
     */

    public ContextMM3F()
    {

    }
  }
}
