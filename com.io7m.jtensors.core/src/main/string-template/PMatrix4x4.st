PMatrix4x4(
  className,
  matrixType,
  matrixComponentType,
  scalarRealType,
  opClass) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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

package com.io7m.jtensors.core.parameterized.matrices;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <matrixType>} values.\</p>
 *
 * @param \<T> A phantom type parameter
 * @since 8.0.0
 */

public final class <className>\<T>
{
  private static final <matrixType>\<?> ZERO;
  private static final <matrixType>\<?> IDENTITY;

  static {
    final <matrixComponentType> z = (<matrixComponentType>) <opClass>.zero();
    final <matrixComponentType> s = (<matrixComponentType>) <opClass>.one();

    ZERO =
      <matrixType>.of(
        z, z, z, z,
        z, z, z, z,
        z, z, z, z,
        z, z, z, z);

    IDENTITY =
      <matrixType>.of(
        s, z, z, z,
        z, s, z, z,
        z, z, s, z,
        z, z, z, s);
  }

  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Add the matrices {@code m0} and {@code m1}.
   * @return {@code m0 + m1}
   * @param m0 The left matrix
   * @param m1 The right matrix
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> add(
    final <matrixType>\<T> m0,
    final <matrixType>\<T> m1)
  {
    return <matrixType>
      .\<T>builder()
      .setR0c0(<opClass>.add(m0.r0c0(), m1.r0c0()))
      .setR0c1(<opClass>.add(m0.r0c1(), m1.r0c1()))
      .setR0c2(<opClass>.add(m0.r0c2(), m1.r0c2()))
      .setR0c3(<opClass>.add(m0.r0c3(), m1.r0c3()))
      .setR1c0(<opClass>.add(m0.r1c0(), m1.r1c0()))
      .setR1c1(<opClass>.add(m0.r1c1(), m1.r1c1()))
      .setR1c2(<opClass>.add(m0.r1c2(), m1.r1c2()))
      .setR1c3(<opClass>.add(m0.r1c3(), m1.r1c3()))
      .setR2c0(<opClass>.add(m0.r2c0(), m1.r2c0()))
      .setR2c1(<opClass>.add(m0.r2c1(), m1.r2c1()))
      .setR2c2(<opClass>.add(m0.r2c2(), m1.r2c2()))
      .setR2c3(<opClass>.add(m0.r2c3(), m1.r2c3()))
      .setR3c0(<opClass>.add(m0.r3c0(), m1.r3c0()))
      .setR3c1(<opClass>.add(m0.r3c1(), m1.r3c1()))
      .setR3c2(<opClass>.add(m0.r3c2(), m1.r3c2()))
      .setR3c3(<opClass>.add(m0.r3c3(), m1.r3c3()))
      .build();
  }

  /**
   * Scale the matrix {@code m} by {@code r}.
   * @return {@code m * r}
   * @param m The matrix
   * @param r The scale factor
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> scale(
    final <matrixType>\<T> m,
    final <scalarRealType> r)
  {
    return <matrixType>
      .\<T>builder()
      .setR0c0(<opClass>.multiply(m.r0c0(), r))
      .setR0c1(<opClass>.multiply(m.r0c1(), r))
      .setR0c2(<opClass>.multiply(m.r0c2(), r))
      .setR0c3(<opClass>.multiply(m.r0c3(), r))
      .setR1c0(<opClass>.multiply(m.r1c0(), r))
      .setR1c1(<opClass>.multiply(m.r1c1(), r))
      .setR1c2(<opClass>.multiply(m.r1c2(), r))
      .setR1c3(<opClass>.multiply(m.r1c3(), r))
      .setR2c0(<opClass>.multiply(m.r2c0(), r))
      .setR2c1(<opClass>.multiply(m.r2c1(), r))
      .setR2c2(<opClass>.multiply(m.r2c2(), r))
      .setR2c3(<opClass>.multiply(m.r2c3(), r))
      .setR3c0(<opClass>.multiply(m.r3c0(), r))
      .setR3c1(<opClass>.multiply(m.r3c1(), r))
      .setR3c2(<opClass>.multiply(m.r3c2(), r))
      .setR3c3(<opClass>.multiply(m.r3c3(), r))
      .build();
  }

  /**
   * Subtract the matrices {@code m0} and {@code m1}.
   * @return {@code m0 - m1}
   * @param m0 The left matrix
   * @param m1 The right matrix
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> subtract(
    final <matrixType>\<T> m0,
    final <matrixType>\<T> m1)
  {
    return <matrixType>
      .\<T>builder()
      .setR0c0(<opClass>.subtract(m0.r0c0(), m1.r0c0()))
      .setR0c1(<opClass>.subtract(m0.r0c1(), m1.r0c1()))
      .setR0c2(<opClass>.subtract(m0.r0c2(), m1.r0c2()))
      .setR0c3(<opClass>.subtract(m0.r0c3(), m1.r0c3()))
      .setR1c0(<opClass>.subtract(m0.r1c0(), m1.r1c0()))
      .setR1c1(<opClass>.subtract(m0.r1c1(), m1.r1c1()))
      .setR1c2(<opClass>.subtract(m0.r1c2(), m1.r1c2()))
      .setR1c3(<opClass>.subtract(m0.r1c3(), m1.r1c3()))
      .setR2c0(<opClass>.subtract(m0.r2c0(), m1.r2c0()))
      .setR2c1(<opClass>.subtract(m0.r2c1(), m1.r2c1()))
      .setR2c2(<opClass>.subtract(m0.r2c2(), m1.r2c2()))
      .setR2c3(<opClass>.subtract(m0.r2c3(), m1.r2c3()))
      .setR3c0(<opClass>.subtract(m0.r3c0(), m1.r3c0()))
      .setR3c1(<opClass>.subtract(m0.r3c1(), m1.r3c1()))
      .setR3c2(<opClass>.subtract(m0.r3c2(), m1.r3c2()))
      .setR3c3(<opClass>.subtract(m0.r3c3(), m1.r3c3()))
      .build();
  }

  /**
   * The zero matrix.
   *
   * @return A matrix with all zero components
   * @param \<T> A phantom type parameter
   */

  @SuppressWarnings("unchecked")
  public static \<T> <matrixType>\<T> zero()
  {
    return (<matrixType>\<T>) ZERO;
  }

  /**
   * The identity matrix.
   *
   * @return A matrix with all diagonal components set to 1 and all other components set to 0.
   * @param \<T> A phantom type parameter
   */

  @SuppressWarnings("unchecked")
  public static \<T> <matrixType>\<T> identity()
  {
    return (<matrixType>\<T>) IDENTITY;
  }
}
>>
