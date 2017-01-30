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
 * Functions over 2x2 mutable matrix types with {@code double} elements.
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

public final class MatrixM2x2D
{
  private MatrixM2x2D()
  {
    throw new UnreachableCodeException();
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row >= 2)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 2");
    }
    return row;
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

  public static <M extends MatrixWritable2x2DType> M add(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1,
    final M out)
  {
    final double r0c0 = m0.getR0C0D() + m1.getR0C0D();
    final double r1c0 = m0.getR1C0D() + m1.getR1C0D();
    final double r0c1 = m0.getR0C1D() + m1.getR0C1D();
    final double r1c1 = m0.getR1C1D() + m1.getR1C1D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r1c0);
    out.setR0C1D(r0c1);
    out.setR1C1D(r1c1);
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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  addInPlace(
    final M m0,
    final MatrixReadable2x2DType m1)
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

  public static <M extends MatrixWritable2x2DType> M addRowScaled(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final M out)
  {
    return addRowScaledUnsafe(
      m,
      checkRow(row_a),
      checkRow(row_b),
      checkRow(row_c),
      r,
      c.v2a,
      c.v2b,
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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  addRowScaledInPlace(
    final ContextMM2D c,
    final M m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r)
  {
    return addRowScaled(c, m, row_a, row_b, row_c, r, m);
  }

  private static <M extends MatrixWritable2x2DType> M addRowScaledUnsafe(
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final int row_c,
    final double r,
    final VectorM2D va,
    final VectorM2D vb,
    final M out)
  {
    m.getRow2DUnsafe(row_a, va);
    m.getRow2DUnsafe(row_b, vb);
    VectorM2D.addScaledInPlace(va, vb, r);
    out.setRowWith2DUnsafe(row_c, va);
    return out;
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

  public static <M extends MatrixWritable2x2DType> M copy(
    final MatrixReadable2x2DType input,
    final M output)
  {
    output.setR0C0D(input.getR0C0D());
    output.setR1C0D(input.getR1C0D());
    output.setR0C1D(input.getR0C1D());
    output.setR1C1D(input.getR1C1D());
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
    final MatrixReadable2x2DType m)
  {
    final double r0c0 = m.getR0C0D();
    final double r0c1 = m.getR0C1D();
    final double r1c0 = m.getR1C0D();
    final double r1c1 = m.getR1C1D();

    return (r0c0 * r1c1) - (r0c1 * r1c0);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code out} . </p>
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

  public static <M extends MatrixWritable2x2DType> M exchangeRows(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final M out)
  {
    return exchangeRowsUnsafe(
      m,
      checkRow(row_a),
      checkRow(row_b),
      c.v2a,
      c.v2b,
      out);
  }

  /**
   * <p> Exchange two rows {@code row_a} and row {@code row_b} of the matrix
   * {@code m}, saving the exchanged rows to {@code m} . </p>
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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  exchangeRowsInPlace(
    final ContextMM2D c,
    final M m,
    final int row_a,
    final int row_b)
  {
    return exchangeRows(c, m, row_a, row_b, m);
  }

  private static <M extends MatrixWritable2x2DType> M exchangeRowsUnsafe(
    final MatrixReadable2x2DType m,
    final int row_a,
    final int row_b,
    final VectorM2D va,
    final VectorM2D vb,
    final M out)
  {
    m.getRow2DUnsafe(row_a, va);
    m.getRow2DUnsafe(row_b, vb);
    out.setRowWith2DUnsafe(row_a, vb);
    out.setRowWith2DUnsafe(row_b, va);
    return out;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param m   The input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2D#determinant(MatrixReadable2x2DType)
   */

  public static <M extends MatrixWritable2x2DType> boolean invert(
    final MatrixReadable2x2DType m,
    final M out)
  {
    final double d = determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final double r0c0 = m.getR1C1D() * d_inv;
    final double r0c1 = -m.getR0C1D() * d_inv;
    final double r1c0 = -m.getR1C0D() * d_inv;
    final double r1c1 = m.getR0C0D() * d_inv;

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);

    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param m   The input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM2x2D#determinant(MatrixReadable2x2DType)
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType>
  boolean invertInPlace(
    final M m)
  {
    return invert(m, m);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M multiply(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1,
    final M out)
  {
    final double orig_r0c0_l = m0.getR0C0D();
    final double orig_r0c0_r = m1.getR0C0D();
    final double orig_r1c0_l = m0.getR1C0D();
    final double orig_r1c0_r = m1.getR1C0D();
    final double orig_r0c1_r = m1.getR0C1D();
    final double orig_r0c1_l = m0.getR0C1D();
    final double orig_r1c1_l = m0.getR1C1D();
    final double orig_r1c1_r = m1.getR1C1D();

    final double r0c0 =
      (orig_r0c0_l * orig_r0c0_r) + (orig_r1c0_l * orig_r0c1_r);
    final double r0c1 =
      (orig_r0c1_l * orig_r0c0_r) + (orig_r1c1_l * orig_r0c1_r);
    final double r1c0 =
      (orig_r0c0_l * orig_r1c0_r) + (orig_r1c0_l * orig_r1c1_r);
    final double r1c1 =
      (orig_r0c1_l * orig_r1c0_r) + (orig_r1c1_l * orig_r1c1_r);

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    return out;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code m0}.
   *
   * @param m0  The left input matrix
   * @param m1  The right input matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  multiplyInPlace(
    final M m0,
    final MatrixReadable2x2DType m1)
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

  public static <V extends VectorWritable2DType> V multiplyVector2D(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final VectorReadable2DType v,
    final V out)
  {
    final VectorM2D row = c.v2a;

    m.getRow2DUnsafe(0, row);
    out.setXD(VectorM2D.dotProduct(row, v));
    m.getRow2DUnsafe(1, row);
    out.setYD(VectorM2D.dotProduct(row, v));
    return out;
  }

  /**
   * Scale all elements of the matrix {@code m} by the scaling value {@code r},
   * saving the result in {@code out}.
   *
   * @param m   The input matrix
   * @param r   The scaling value
   * @param out The output row
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType> M scale(
    final MatrixReadable2x2DType m,
    final double r,
    final M out)
  {
    out.setR0C0D(m.getR0C0D() * r);
    out.setR1C0D(m.getR1C0D() * r);
    out.setR0C1D(m.getR0C1D() * r);
    out.setR1C1D(m.getR1C1D() * r);
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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
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
   * @param m   The input matrix
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param out The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType> M scaleRow(
    final ContextMM2D c,
    final MatrixReadable2x2DType m,
    final int row,
    final double r,
    final M out)
  {
    return scaleRowUnsafe(
      m, checkRow(row), r, c.v2a, out);
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
   * @param row The index of the row {@code (0 <= row < 2)}
   * @param r   The scaling value
   * @param <M> The precise type of matrix
   *
   * @return {@code out}
   */

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  scaleRowInPlace(
    final ContextMM2D c,
    final M m,
    final int row,
    final double r)
  {
    return scaleRowUnsafe(
      m, checkRow(row), r, c.v2a, m);
  }

  private static <M extends MatrixWritable2x2DType> M scaleRowUnsafe(
    final MatrixReadable2x2DType m,
    final int row,
    final double r,
    final VectorM2D tmp,
    final M out)
  {
    m.getRow2DUnsafe(row, tmp);
    VectorM2D.scaleInPlace(tmp, r);
    out.setRowWith2DUnsafe(row, tmp);
    return out;
  }

  /**
   * Set the given matrix {@code m} to the identity matrix.
   *
   * @param m   The matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType> M setIdentity(
    final M m)
  {
    m.setR0C0D(1.0);
    m.setR0C1D(0.0);
    m.setR1C0D(0.0);
    m.setR1C1D(1.0);
    return m;
  }

  /**
   * Set the given matrix {@code m} to the zero matrix.
   *
   * @param m   The matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   */

  public static <M extends MatrixWritable2x2DType> M setZero(
    final M m)
  {
    m.setR0C0D(0.0);
    m.setR0C1D(0.0);
    m.setR1C0D(0.0);
    m.setR1C1D(0.0);
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
    final MatrixReadable2x2DType m)
  {
    return m.getR0C0D() + m.getR1C1D();
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

  public static <M extends MatrixWritable2x2DType> M transpose(
    final MatrixReadable2x2DType m,
    final M out)
  {
    final double r0c0 = m.getR0C0D();
    final double r1c0 = m.getR1C0D();

    final double r0c1 = m.getR0C1D();
    final double r1c1 = m.getR1C1D();

    out.setR0C0D(r0c0);
    out.setR1C0D(r0c1); // swap 0

    out.setR0C1D(r1c0); // swap 0
    out.setR1C1D(r1c1);

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

  public static <M extends MatrixWritable2x2DType & MatrixReadable2x2DType> M
  transposeInPlace(
    final M m)
  {
    final double r1c0 = m.getR1C0D();
    final double r0c1 = m.getR0C1D();

    m.setR1C0D(r0c1); // swap 0
    m.setR0C1D(r1c0); // swap 0
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
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1)
  {
    return compareRow0(m0, m1) && compareRow1(m0, m1);
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

  public static int hashElements(final MatrixReadable2x2DType m)
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(m.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(m.getR1C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(m.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(m.getR1C1D(), prime, r);

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
    final MatrixReadable2x2DType m,
    final StringBuilder sb)
  {
    final String row0 = String.format(
      "[%+.15f %+.15f]\n", m.getR0C0D(), m.getR0C1D());
    final String row1 = String.format(
      "[%+.15f %+.15f]\n", m.getR1C0D(), m.getR1C1D());
    sb.append(row0);
    sb.append(row1);
  }

  private static boolean compareRow0(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1)
  {
    return !(m0.getR0C0D() != m1.getR0C0D()) && m0.getR0C1D() == m1.getR0C1D();
  }

  private static boolean compareRow1(
    final MatrixReadable2x2DType m0,
    final MatrixReadable2x2DType m1)
  {
    return !(m0.getR1C0D() != m1.getR1C0D()) && m0.getR1C1D() == m1.getR1C1D();
  }

  /**
   * <p>The {@code ContextMM2D} type contains the minimum storage required for
   * all of the functions of the {@code MatrixM2x2D} class.</p>
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical
   * code.</p>
   *
   * <p> The user should allocate one {@code ContextMM2D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextMM2D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static final class ContextMM2D
  {
    private final VectorM2D v2a = new VectorM2D();
    private final VectorM2D v2b = new VectorM2D();

    /**
     * Construct a new context.
     */

    public ContextMM2D()
    {

    }
  }
}
