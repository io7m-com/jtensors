PMatrix4x4(
  className,
  matrixType,
  matrixComponentType,
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
