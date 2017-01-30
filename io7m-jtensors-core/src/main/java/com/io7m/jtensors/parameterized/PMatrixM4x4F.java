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

package com.io7m.jtensors.parameterized;

import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable4FType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p> A 4x4 mutable matrix type with {@code float} elements. </p>
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked")
public final class PMatrixM4x4F
{
  private PMatrixM4x4F()
  {
    throw new UnreachableCodeException();
  }

  private static <T0, T1, M extends PMatrixWritable4x4FType<T1, T0>> boolean
  invertActual(
    final PMatrixReadable4x4FType<T0, T1> m,
    final Matrix3x3FType m3,
    final Matrix4x4FType temp,
    final M out)
  {
    final double d = MatrixM4x4F.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    /*
      This code is based on the Laplace Expansion theorem. Essentially, the
      inverse of the matrix is calculated by taking the determinants of 3x3
      sub-matrices of the original matrix. The sub-matrices are created by
      removing a specific row and column to leave 9 (possibly non-adjacent)
      cells, which are then placed in a 3x3 matrix.

      This implementation was derived from the paper "The Laplace Expansion
      Theorem: Computing the Determinants and Inverses of Matrices" by David
      Eberly.
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

      m3.setR0C0F(m.getR1C1F());
      m3.setR0C1F(m.getR1C2F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C1F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r0c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C2F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r0c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C1F());
      m3.setR0C2F(m.getR1C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r0c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      m3.setR0C0F(m.getR1C0F());
      m3.setR0C1F(m.getR1C1F());
      m3.setR0C2F(m.getR1C2F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r0c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C1F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r1c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C2F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r1c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r1c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR2C0F());
      m3.setR1C1F(m.getR2C1F());
      m3.setR1C2F(m.getR2C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r1c3 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C1F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C1F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r2c0 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C2F());
      m3.setR2C2F(m.getR3C3F());

      r2c1 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C3F());

      r2c2 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C2F());
      m3.setR2C0F(m.getR3C0F());
      m3.setR2C1F(m.getR3C1F());
      m3.setR2C2F(m.getR3C2F());

      r2c3 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      m3.setR0C0F(m.getR0C1F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C1F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C1F());
      m3.setR2C1F(m.getR2C2F());
      m3.setR2C2F(m.getR2C3F());

      r3c0 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C2F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C2F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C2F());
      m3.setR2C2F(m.getR2C3F());

      r3c1 = MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C3F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C3F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C1F());
      m3.setR2C2F(m.getR2C3F());

      r3c2 = -MatrixM3x3F.determinant(m3);
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      m3.setR0C0F(m.getR0C0F());
      m3.setR0C1F(m.getR0C1F());
      m3.setR0C2F(m.getR0C2F());
      m3.setR1C0F(m.getR1C0F());
      m3.setR1C1F(m.getR1C1F());
      m3.setR1C2F(m.getR1C2F());
      m3.setR2C0F(m.getR2C0F());
      m3.setR2C1F(m.getR2C1F());
      m3.setR2C2F(m.getR2C2F());

      r3c3 = MatrixM3x3F.determinant(m3);
    }

    /*
      Divide sub-matrix determinants by the determinant of the original
      matrix and transpose.
     */

    temp.setR0C0F((float) (r0c0 * d_inv));
    temp.setR0C1F((float) (r0c1 * d_inv));
    temp.setR0C2F((float) (r0c2 * d_inv));
    temp.setR0C3F((float) (r0c3 * d_inv));

    temp.setR1C0F((float) (r1c0 * d_inv));
    temp.setR1C1F((float) (r1c1 * d_inv));
    temp.setR1C2F((float) (r1c2 * d_inv));
    temp.setR1C3F((float) (r1c3 * d_inv));

    temp.setR2C0F((float) (r2c0 * d_inv));
    temp.setR2C1F((float) (r2c1 * d_inv));
    temp.setR2C2F((float) (r2c2 * d_inv));
    temp.setR2C3F((float) (r2c3 * d_inv));

    temp.setR3C0F((float) (r3c0 * d_inv));
    temp.setR3C1F((float) (r3c1 * d_inv));
    temp.setR3C2F((float) (r3c2 * d_inv));
    temp.setR3C3F((float) (r3c3 * d_inv));

    MatrixM4x4F.transpose(temp, out);
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

  public static <T0, T1, MOUT extends PMatrixWritable4x4FType<T1, T0>>
  boolean invert(
    final ContextPM4F context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final MOUT out)
  {
    return invertActual(m, context.m3a, context.m4a, out);
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

  public static <T0, T1, T2, MOUT extends PMatrixWritable4x4FType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable4x4FType<T1, T2> m0,
    final PMatrixReadable4x4FType<T0, T1> m1,
    final MOUT out)
  {
    double r0c0 = 0.0;
    r0c0 += (double) (m0.getR0C0F() * m1.getR0C0F());
    r0c0 += (double) (m0.getR0C1F() * m1.getR1C0F());
    r0c0 += (double) (m0.getR0C2F() * m1.getR2C0F());
    r0c0 += (double) (m0.getR0C3F() * m1.getR3C0F());

    double r1c0 = 0.0;
    r1c0 += (double) (m0.getR1C0F() * m1.getR0C0F());
    r1c0 += (double) (m0.getR1C1F() * m1.getR1C0F());
    r1c0 += (double) (m0.getR1C2F() * m1.getR2C0F());
    r1c0 += (double) (m0.getR1C3F() * m1.getR3C0F());

    double r2c0 = 0.0;
    r2c0 += (double) (m0.getR2C0F() * m1.getR0C0F());
    r2c0 += (double) (m0.getR2C1F() * m1.getR1C0F());
    r2c0 += (double) (m0.getR2C2F() * m1.getR2C0F());
    r2c0 += (double) (m0.getR2C3F() * m1.getR3C0F());

    double r3c0 = 0.0;
    r3c0 += (double) (m0.getR3C0F() * m1.getR0C0F());
    r3c0 += (double) (m0.getR3C1F() * m1.getR1C0F());
    r3c0 += (double) (m0.getR3C2F() * m1.getR2C0F());
    r3c0 += (double) (m0.getR3C3F() * m1.getR3C0F());

    double r0c1 = 0.0;
    r0c1 += (double) (m0.getR0C0F() * m1.getR0C1F());
    r0c1 += (double) (m0.getR0C1F() * m1.getR1C1F());
    r0c1 += (double) (m0.getR0C2F() * m1.getR2C1F());
    r0c1 += (double) (m0.getR0C3F() * m1.getR3C1F());

    double r1c1 = 0.0;
    r1c1 += (double) (m0.getR1C0F() * m1.getR0C1F());
    r1c1 += (double) (m0.getR1C1F() * m1.getR1C1F());
    r1c1 += (double) (m0.getR1C2F() * m1.getR2C1F());
    r1c1 += (double) (m0.getR1C3F() * m1.getR3C1F());

    double r2c1 = 0.0;
    r2c1 += (double) (m0.getR2C0F() * m1.getR0C1F());
    r2c1 += (double) (m0.getR2C1F() * m1.getR1C1F());
    r2c1 += (double) (m0.getR2C2F() * m1.getR2C1F());
    r2c1 += (double) (m0.getR2C3F() * m1.getR3C1F());

    double r3c1 = 0.0;
    r3c1 += (double) (m0.getR3C0F() * m1.getR0C1F());
    r3c1 += (double) (m0.getR3C1F() * m1.getR1C1F());
    r3c1 += (double) (m0.getR3C2F() * m1.getR2C1F());
    r3c1 += (double) (m0.getR3C3F() * m1.getR3C1F());

    double r0c2 = 0.0;
    r0c2 += (double) (m0.getR0C0F() * m1.getR0C2F());
    r0c2 += (double) (m0.getR0C1F() * m1.getR1C2F());
    r0c2 += (double) (m0.getR0C2F() * m1.getR2C2F());
    r0c2 += (double) (m0.getR0C3F() * m1.getR3C2F());

    double r1c2 = 0.0;
    r1c2 += (double) (m0.getR1C0F() * m1.getR0C2F());
    r1c2 += (double) (m0.getR1C1F() * m1.getR1C2F());
    r1c2 += (double) (m0.getR1C2F() * m1.getR2C2F());
    r1c2 += (double) (m0.getR1C3F() * m1.getR3C2F());

    double r2c2 = 0.0;
    r2c2 += (double) (m0.getR2C0F() * m1.getR0C2F());
    r2c2 += (double) (m0.getR2C1F() * m1.getR1C2F());
    r2c2 += (double) (m0.getR2C2F() * m1.getR2C2F());
    r2c2 += (double) (m0.getR2C3F() * m1.getR3C2F());

    double r3c2 = 0.0;
    r3c2 += (double) (m0.getR3C0F() * m1.getR0C2F());
    r3c2 += (double) (m0.getR3C1F() * m1.getR1C2F());
    r3c2 += (double) (m0.getR3C2F() * m1.getR2C2F());
    r3c2 += (double) (m0.getR3C3F() * m1.getR3C2F());

    double r0c3 = 0.0;
    r0c3 += (double) (m0.getR0C0F() * m1.getR0C3F());
    r0c3 += (double) (m0.getR0C1F() * m1.getR1C3F());
    r0c3 += (double) (m0.getR0C2F() * m1.getR2C3F());
    r0c3 += (double) (m0.getR0C3F() * m1.getR3C3F());

    double r1c3 = 0.0;
    r1c3 += (double) (m0.getR1C0F() * m1.getR0C3F());
    r1c3 += (double) (m0.getR1C1F() * m1.getR1C3F());
    r1c3 += (double) (m0.getR1C2F() * m1.getR2C3F());
    r1c3 += (double) (m0.getR1C3F() * m1.getR3C3F());

    double r2c3 = 0.0;
    r2c3 += (double) (m0.getR2C0F() * m1.getR0C3F());
    r2c3 += (double) (m0.getR2C1F() * m1.getR1C3F());
    r2c3 += (double) (m0.getR2C2F() * m1.getR2C3F());
    r2c3 += (double) (m0.getR2C3F() * m1.getR3C3F());

    double r3c3 = 0.0;
    r3c3 += (double) (m0.getR3C0F() * m1.getR0C3F());
    r3c3 += (double) (m0.getR3C1F() * m1.getR1C3F());
    r3c3 += (double) (m0.getR3C2F() * m1.getR2C3F());
    r3c3 += (double) (m0.getR3C3F() * m1.getR3C3F());

    out.setR0C0F((float) r0c0);
    out.setR0C1F((float) r0c1);
    out.setR0C2F((float) r0c2);
    out.setR0C3F((float) r0c3);

    out.setR1C0F((float) r1c0);
    out.setR1C1F((float) r1c1);
    out.setR1C2F((float) r1c2);
    out.setR1C3F((float) r1c3);

    out.setR2C0F((float) r2c0);
    out.setR2C1F((float) r2c1);
    out.setR2C2F((float) r2c2);
    out.setR2C3F((float) r2c3);

    out.setR3C0F((float) r3c0);
    out.setR3C1F((float) r3c1);
    out.setR3C2F((float) r3c2);
    out.setR3C3F((float) r3c3);
    return out;
  }

  private static <V extends VectorWritable4FType> V multiplyVector4FActual(
    final MatrixReadable4x4FType m,
    final VectorReadable4FType v,
    final VectorM4F va,
    final VectorM4F vb,
    final V out)
  {
    vb.copyFrom4F(v);

    m.getRow4FUnsafe(0, va);
    out.setXF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(1, va);
    out.setYF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(2, va);
    out.setZF((float) VectorM4F.dotProduct(va, vb));
    m.getRow4FUnsafe(3, va);
    out.setWF((float) VectorM4F.dotProduct(va, vb));

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

  public static <T0, T1, V extends PVectorWritable4FType<T1>> V
  multiplyVector4F(
    final ContextPM4F context,
    final PMatrixReadable4x4FType<T0, T1> m,
    final PVectorReadable4FType<T0> v,
    final V out)
  {
    return multiplyVector4FActual(
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

  public static <T0, T1, M extends PMatrixWritable4x4FType<T0, T1>> M copy(
    final PMatrixReadable4x4FType<T0, T1> input,
    final M output)
  {
    MatrixM4x4F.copy(input, output);
    return output;
  }

  /**
   * <p>The {@code ContextPM4F} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM4x4F} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM4F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM4F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM4F
  {
    private final Matrix3x3FType m3a = MatrixHeapArrayM3x3F.newMatrix();
    private final Matrix4x4FType m4a = MatrixHeapArrayM4x4F.newMatrix();
    private final Matrix4x4FType m4b = MatrixHeapArrayM4x4F.newMatrix();
    private final VectorM3F v3a = new VectorM3F();
    private final VectorM3F v3b = new VectorM3F();
    private final VectorM3F v3c = new VectorM3F();
    private final VectorM3F v3d = new VectorM3F();
    private final VectorM4F v4a = new VectorM4F();
    private final VectorM4F v4b = new VectorM4F();

    /**
     * Construct a new context.
     */

    public ContextPM4F()
    {

    }
  }
}
