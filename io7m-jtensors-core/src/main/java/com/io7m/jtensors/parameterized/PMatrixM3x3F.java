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

import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * <p>A 3x3 mutable matrix type with single precision elements.</p>
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM3x3F
{
  private PMatrixM3x3F()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param out     The output matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3F#determinant(com.io7m.jtensors.MatrixReadable3x3FType)
   */

  public static <T0, T1, MOUT extends PMatrixWritable3x3FType<T1, T0>>
  boolean invert(
    final ContextPM3F context,
    final PMatrixReadable3x3FType<T0, T1> m,
    final MOUT out)
  {
    final double d = MatrixM3x3F.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final float orig_r0c0 = m.getR0C0F();
    final float orig_r0c1 = m.getR0C1F();
    final float orig_r0c2 = m.getR0C2F();

    final float orig_r1c0 = m.getR1C0F();
    final float orig_r1c1 = m.getR1C1F();
    final float orig_r1c2 = m.getR1C2F();

    final float orig_r2c0 = m.getR2C0F();
    final float orig_r2c1 = m.getR2C1F();
    final float orig_r2c2 = m.getR2C2F();

    final float r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final float r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final float r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final float r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final float r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final float r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final float r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final float r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final float r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    final Matrix3x3FType temp = context.m3a;

    temp.setR0C0F(r0c0);
    temp.setR0C1F(r0c1);
    temp.setR0C2F(r0c2);

    temp.setR1C0F(r1c0);
    temp.setR1C1F(r1c1);
    temp.setR1C2F(r1c2);

    temp.setR2C0F(r2c0);
    temp.setR2C1F(r2c1);
    temp.setR2C2F(r2c2);

    MatrixM3x3F.scale(temp, d_inv, out);
    return true;
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

  public static <T0, T1, T2, MOUT extends PMatrixWritable3x3FType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable3x3FType<T1, T2> m0,
    final PMatrixReadable3x3FType<T0, T1> m1,
    final MOUT out)
  {
    float r0c0 = 0.0f;
    r0c0 += m0.getR0C0F() * m1.getR0C0F();
    r0c0 += m0.getR0C1F() * m1.getR1C0F();
    r0c0 += m0.getR0C2F() * m1.getR2C0F();

    float r1c0 = 0.0f;
    r1c0 += m0.getR1C0F() * m1.getR0C0F();
    r1c0 += m0.getR1C1F() * m1.getR1C0F();
    r1c0 += m0.getR1C2F() * m1.getR2C0F();

    float r2c0 = 0.0f;
    r2c0 += m0.getR2C0F() * m1.getR0C0F();
    r2c0 += m0.getR2C1F() * m1.getR1C0F();
    r2c0 += m0.getR2C2F() * m1.getR2C0F();

    float r0c1 = 0.0f;
    r0c1 += m0.getR0C0F() * m1.getR0C1F();
    r0c1 += m0.getR0C1F() * m1.getR1C1F();
    r0c1 += m0.getR0C2F() * m1.getR2C1F();

    float r1c1 = 0.0f;
    r1c1 += m0.getR1C0F() * m1.getR0C1F();
    r1c1 += m0.getR1C1F() * m1.getR1C1F();
    r1c1 += m0.getR1C2F() * m1.getR2C1F();

    float r2c1 = 0.0f;
    r2c1 += m0.getR2C0F() * m1.getR0C1F();
    r2c1 += m0.getR2C1F() * m1.getR1C1F();
    r2c1 += m0.getR2C2F() * m1.getR2C1F();

    float r0c2 = 0.0f;
    r0c2 += m0.getR0C0F() * m1.getR0C2F();
    r0c2 += m0.getR0C1F() * m1.getR1C2F();
    r0c2 += m0.getR0C2F() * m1.getR2C2F();

    float r1c2 = 0.0f;
    r1c2 += m0.getR1C0F() * m1.getR0C2F();
    r1c2 += m0.getR1C1F() * m1.getR1C2F();
    r1c2 += m0.getR1C2F() * m1.getR2C2F();

    float r2c2 = 0.0f;
    r2c2 += m0.getR2C0F() * m1.getR0C2F();
    r2c2 += m0.getR2C1F() * m1.getR1C2F();
    r2c2 += m0.getR2C2F() * m1.getR2C2F();

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR0C2F(r0c2);

    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);
    out.setR1C2F(r1c2);

    out.setR2C0F(r2c0);
    out.setR2C1F(r2c1);
    out.setR2C2F(r2c2);

    return out;
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}.
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

  public static <T0, T1, V extends PVectorWritable3FType<T1>> V
  multiplyVector3F(
    final ContextPM3F context,
    final PMatrixReadable3x3FType<T0, T1> m,
    final PVectorReadable3FType<T0> v,
    final V out)
  {
    final VectorM3F va = context.v3a;
    final VectorM3F vb = context.v3b;

    vb.copyFrom3F(v);

    m.getRow3FUnsafe(0, va);
    out.setXF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(1, va);
    out.setYF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(2, va);
    out.setZF((float) VectorM3F.dotProduct(va, vb));

    return out;
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

  public static <T0, T1, M extends PMatrixWritable3x3FType<T0, T1>> M copy(
    final PMatrixReadable3x3FType<T0, T1> input,
    final M output)
  {
    MatrixM3x3F.copy(input, output);
    return output;
  }

  /**
   * <p>The {@code ContextPM3F} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM3x3F} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM3F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM3F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM3F
  {
    private final Matrix3x3FType m3a = MatrixHeapArrayM3x3F.newMatrix();
    private final VectorM3F      v3a = new VectorM3F();
    private final VectorM3F      v3b = new VectorM3F();

    /**
     * Construct a new context.
     */

    public ContextPM3F()
    {

    }
  }
}
