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

package com.io7m.jtensors.parameterized;

import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.MatrixHeapArrayM4x4D;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable4DType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p> A 4x4 mutable matrix type with double precision elements. </p>
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM4x4D
{
  private PMatrixM4x4D()
  {
    throw new UnreachableCodeException();
  }

  private static <T0, T1, M extends PMatrixWritable4x4DType<T1, T0>> boolean
  invertActual(
    final PMatrixReadable4x4DType<T0, T1> m,
    final Matrix3x3DType m3,
    final Matrix4x4DType temp,
    final M out)
  {
    final double d = MatrixM4x4D.determinant(m);

    if (d == 0.0) {
      return false;
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

      m3.setR0C0D(m.getR1C1D());
      m3.setR0C1D(m.getR1C2D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C1D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r0c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C2D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r0c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C1D());
      m3.setR0C2D(m.getR1C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r0c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.setR0C0D(m.getR1C0D());
      m3.setR0C1D(m.getR1C1D());
      m3.setR0C2D(m.getR1C2D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r0c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C1D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r1c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C2D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r1c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r1c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR2C0D());
      m3.setR1C1D(m.getR2C1D());
      m3.setR1C2D(m.getR2C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r1c3 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C1D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C1D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r2c0 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C2D());
      m3.setR2C2D(m.getR3C3D());

      r2c1 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C3D());

      r2c2 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C2D());
      m3.setR2C0D(m.getR3C0D());
      m3.setR2C1D(m.getR3C1D());
      m3.setR2C2D(m.getR3C2D());

      r2c3 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.setR0C0D(m.getR0C1D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C1D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C1D());
      m3.setR2C1D(m.getR2C2D());
      m3.setR2C2D(m.getR2C3D());

      r3c0 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C2D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C2D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C2D());
      m3.setR2C2D(m.getR2C3D());

      r3c1 = MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C3D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C3D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C1D());
      m3.setR2C2D(m.getR2C3D());

      r3c2 = -MatrixM3x3D.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.setR0C0D(m.getR0C0D());
      m3.setR0C1D(m.getR0C1D());
      m3.setR0C2D(m.getR0C2D());
      m3.setR1C0D(m.getR1C0D());
      m3.setR1C1D(m.getR1C1D());
      m3.setR1C2D(m.getR1C2D());
      m3.setR2C0D(m.getR2C0D());
      m3.setR2C1D(m.getR2C1D());
      m3.setR2C2D(m.getR2C2D());

      r3c3 = MatrixM3x3D.determinant(m3);
    }

    /**
     * Divide sub-matrix determinants by the determinant of the original
     * matrix and transpose.
     */

    temp.setR0C0D(r0c0 * d_inv);
    temp.setR0C1D(r0c1 * d_inv);
    temp.setR0C2D(r0c2 * d_inv);
    temp.setR0C3D(r0c3 * d_inv);

    temp.setR1C0D(r1c0 * d_inv);
    temp.setR1C1D(r1c1 * d_inv);
    temp.setR1C2D(r1c2 * d_inv);
    temp.setR1C3D(r1c3 * d_inv);

    temp.setR2C0D(r2c0 * d_inv);
    temp.setR2C1D(r2c1 * d_inv);
    temp.setR2C2D(r2c2 * d_inv);
    temp.setR2C3D(r2c3 * d_inv);

    temp.setR3C0D(r3c0 * d_inv);
    temp.setR3C1D(r3c1 * d_inv);
    temp.setR3C2D(r3c2 * d_inv);
    temp.setR3C3D(r3c3 * d_inv);

    MatrixM4x4D.transpose(temp, out);
    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. The function uses
   * preallocated storage in {@code context} to avoid allocating memory. If the
   * function returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param out     The output matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code true} iff the matrix was invertible
   */

  public static <T0, T1, MOUT extends PMatrixWritable4x4DType<T1, T0>>
  boolean invert(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final MOUT out)
  {
    return PMatrixM4x4D.invertActual(m, context.m3a, context.m4a, out);
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0     The left input vector
   * @param m1     The right input vector
   * @param out    The output vector
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   * @param <T2>   A phantom type parameter
   * @param <MOUT> The precise type of output matrix
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, MOUT extends PMatrixWritable4x4DType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable4x4DType<T1, T2> m0,
    final PMatrixReadable4x4DType<T0, T1> m1,
    final MOUT out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getR0C0D() * m1.getR0C0D();
    r0c0 += m0.getR0C1D() * m1.getR1C0D();
    r0c0 += m0.getR0C2D() * m1.getR2C0D();
    r0c0 += m0.getR0C3D() * m1.getR3C0D();

    double r1c0 = 0.0;
    r1c0 += m0.getR1C0D() * m1.getR0C0D();
    r1c0 += m0.getR1C1D() * m1.getR1C0D();
    r1c0 += m0.getR1C2D() * m1.getR2C0D();
    r1c0 += m0.getR1C3D() * m1.getR3C0D();

    double r2c0 = 0.0;
    r2c0 += m0.getR2C0D() * m1.getR0C0D();
    r2c0 += m0.getR2C1D() * m1.getR1C0D();
    r2c0 += m0.getR2C2D() * m1.getR2C0D();
    r2c0 += m0.getR2C3D() * m1.getR3C0D();

    double r3c0 = 0.0;
    r3c0 += m0.getR3C0D() * m1.getR0C0D();
    r3c0 += m0.getR3C1D() * m1.getR1C0D();
    r3c0 += m0.getR3C2D() * m1.getR2C0D();
    r3c0 += m0.getR3C3D() * m1.getR3C0D();

    double r0c1 = 0.0;
    r0c1 += m0.getR0C0D() * m1.getR0C1D();
    r0c1 += m0.getR0C1D() * m1.getR1C1D();
    r0c1 += m0.getR0C2D() * m1.getR2C1D();
    r0c1 += m0.getR0C3D() * m1.getR3C1D();

    double r1c1 = 0.0;
    r1c1 += m0.getR1C0D() * m1.getR0C1D();
    r1c1 += m0.getR1C1D() * m1.getR1C1D();
    r1c1 += m0.getR1C2D() * m1.getR2C1D();
    r1c1 += m0.getR1C3D() * m1.getR3C1D();

    double r2c1 = 0.0;
    r2c1 += m0.getR2C0D() * m1.getR0C1D();
    r2c1 += m0.getR2C1D() * m1.getR1C1D();
    r2c1 += m0.getR2C2D() * m1.getR2C1D();
    r2c1 += m0.getR2C3D() * m1.getR3C1D();

    double r3c1 = 0.0;
    r3c1 += m0.getR3C0D() * m1.getR0C1D();
    r3c1 += m0.getR3C1D() * m1.getR1C1D();
    r3c1 += m0.getR3C2D() * m1.getR2C1D();
    r3c1 += m0.getR3C3D() * m1.getR3C1D();

    double r0c2 = 0.0;
    r0c2 += m0.getR0C0D() * m1.getR0C2D();
    r0c2 += m0.getR0C1D() * m1.getR1C2D();
    r0c2 += m0.getR0C2D() * m1.getR2C2D();
    r0c2 += m0.getR0C3D() * m1.getR3C2D();

    double r1c2 = 0.0;
    r1c2 += m0.getR1C0D() * m1.getR0C2D();
    r1c2 += m0.getR1C1D() * m1.getR1C2D();
    r1c2 += m0.getR1C2D() * m1.getR2C2D();
    r1c2 += m0.getR1C3D() * m1.getR3C2D();

    double r2c2 = 0.0;
    r2c2 += m0.getR2C0D() * m1.getR0C2D();
    r2c2 += m0.getR2C1D() * m1.getR1C2D();
    r2c2 += m0.getR2C2D() * m1.getR2C2D();
    r2c2 += m0.getR2C3D() * m1.getR3C2D();

    double r3c2 = 0.0;
    r3c2 += m0.getR3C0D() * m1.getR0C2D();
    r3c2 += m0.getR3C1D() * m1.getR1C2D();
    r3c2 += m0.getR3C2D() * m1.getR2C2D();
    r3c2 += m0.getR3C3D() * m1.getR3C2D();

    double r0c3 = 0.0;
    r0c3 += m0.getR0C0D() * m1.getR0C3D();
    r0c3 += m0.getR0C1D() * m1.getR1C3D();
    r0c3 += m0.getR0C2D() * m1.getR2C3D();
    r0c3 += m0.getR0C3D() * m1.getR3C3D();

    double r1c3 = 0.0;
    r1c3 += m0.getR1C0D() * m1.getR0C3D();
    r1c3 += m0.getR1C1D() * m1.getR1C3D();
    r1c3 += m0.getR1C2D() * m1.getR2C3D();
    r1c3 += m0.getR1C3D() * m1.getR3C3D();

    double r2c3 = 0.0;
    r2c3 += m0.getR2C0D() * m1.getR0C3D();
    r2c3 += m0.getR2C1D() * m1.getR1C3D();
    r2c3 += m0.getR2C2D() * m1.getR2C3D();
    r2c3 += m0.getR2C3D() * m1.getR3C3D();

    double r3c3 = 0.0;
    r3c3 += m0.getR3C0D() * m1.getR0C3D();
    r3c3 += m0.getR3C1D() * m1.getR1C3D();
    r3c3 += m0.getR3C2D() * m1.getR2C3D();
    r3c3 += m0.getR3C3D() * m1.getR3C3D();

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR0C2D(r0c2);
    out.setR0C3D(r0c3);

    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    out.setR1C2D(r1c2);
    out.setR1C3D(r1c3);

    out.setR2C0D(r2c0);
    out.setR2C1D(r2c1);
    out.setR2C2D(r2c2);
    out.setR2C3D(r2c3);

    out.setR3C0D(r3c0);
    out.setR3C1D(r3c1);
    out.setR3C2D(r3c2);
    out.setR3C3D(r3c3);
    return out;
  }

  private static <V extends VectorWritable4DType> V multiplyVector4DActual(
    final MatrixReadable4x4DType m,
    final VectorReadable4DType v,
    final VectorM4D va,
    final VectorM4D vb,
    final V out)
  {
    vb.copyFrom4D(v);

    m.getRow4DUnsafe(0, va);
    out.setXD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(1, va);
    out.setYD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(2, va);
    out.setZD(VectorM4D.dotProduct(va, vb));
    m.getRow4DUnsafe(3, va);
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
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param v       The input vector
   * @param out     The output vector
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <V>     The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <T0, T1, V extends PVectorWritable4DType<T1>> V
  multiplyVector4D(
    final ContextPM4D context,
    final PMatrixReadable4x4DType<T0, T1> m,
    final PVectorReadable4DType<T0> v,
    final V out)
  {
    return PMatrixM4x4D.multiplyVector4DActual(
      m, v, context.v4a, context.v4b, out);
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <M>    The precise type of matrix
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   *
   * @return {@code output}
   */

  public static <T0, T1, M extends PMatrixWritable4x4DType<T0, T1>> M copy(
    final PMatrixReadable4x4DType<T0, T1> input,
    final M output)
  {
    MatrixM4x4D.copy(input, output);
    return output;
  }

  /**
   * <p>The {@code ContextPM4D} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM4x4D} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM4D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM4D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM4D
  {
    private final Matrix3x3DType m3a = MatrixHeapArrayM3x3D.newMatrix();
    private final Matrix4x4DType m4a = MatrixHeapArrayM4x4D.newMatrix();
    private final Matrix4x4DType m4b = MatrixHeapArrayM4x4D.newMatrix();
    private final VectorM3D      v3a = new VectorM3D();
    private final VectorM3D      v3b = new VectorM3D();
    private final VectorM3D      v3c = new VectorM3D();
    private final VectorM3D      v3d = new VectorM3D();
    private final VectorM4D      v4a = new VectorM4D();
    private final VectorM4D      v4b = new VectorM4D();

    /**
     * Construct a new context.
     */

    public ContextPM4D()
    {

    }
  }
}
