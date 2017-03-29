Quaternion4(
  className,
  quaternionType,
  quaternionComponentType,
  scalarLargeType,
  scalarRealType,
  opClass,
  dotProductClass) ::= <<
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

package com.io7m.jtensors.core.quaternions;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <quaternionType>} values.\</p>
 * \<p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * \</p>
 * @since 8.0.0
 */

public final class <className>
{
  private static final <quaternionType> ZERO;
  private static final <quaternionType> IDENTITY;

  static {
    final <quaternionComponentType> z = (<quaternionComponentType>) <opClass>.zero();
    final <quaternionComponentType> s = (<quaternionComponentType>) <opClass>.one();
    ZERO = <quaternionType>.of(z, z, z, z);
    IDENTITY = <quaternionType>.of(z, z, z, s);
  }

  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Add {@code q0} to {@code q1}.
   *
   * @return {@code (q0.x + q1.x, q0.y + q0.y, q0.z + q1.z, q0.w + q1.w) }
   *
   * @param q0 The left quaternion
   * @param q1 The right quaternion
   */

  public static <quaternionType> add(
    final <quaternionType> q0,
    final <quaternionType> q1)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.add(q0.x(), q1.x()),
      (<quaternionComponentType>) <opClass>.add(q0.y(), q1.y()),
      (<quaternionComponentType>) <opClass>.add(q0.z(), q1.z()),
      (<quaternionComponentType>) <opClass>.add(q0.w(), q1.w()));
  }

  /**
   * Calculate the conjugate of the input quaternion {@code q}.
   *
   * @param q The input quaternion
   * @return The conjugate of the input quaternion
   */

  public static <quaternionType> conjugate(
    final <quaternionType> q)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.negate(q.x()),
      (<quaternionComponentType>) <opClass>.negate(q.y()),
      (<quaternionComponentType>) <opClass>.negate(q.z()),
      (<quaternionComponentType>) q.w());
  }

  /**
   * Calculate the scalar product of the quaternions {@code q0} and {@code q1}.
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return The scalar product of the two quaternions
   */

  public static <scalarRealType> dotProduct(
    final <quaternionType> q0,
    final <quaternionType> q1)
  {
    return <dotProductClass>.dotProduct4(
      q0.x(), q0.y(), q0.y(), q0.w(),
      q1.x(), q1.y(), q1.y(), q1.w());
  }

  /**
   * The identity quaternion.
   *
   * @return {@code (0, 0, 0, 1) }
   */

  public static <quaternionType> identity()
  {
    return IDENTITY;
  }

  /**
   * Linearly interpolate between {@code q0} and {@code q1} by the amount {@code
   * alpha}, such that:
   *
   * \<ul>
   *    \<li>{@code interpolateLinear(q0, q1, 0.0) = q0}\</li>
   *    \<li>{@code interpolateLinear(q0, q1, 1.0) = q1}\</li>
   * \</ul>
   *
   * @param q0    The left input quaternion
   * @param q1    The right input quaternion
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   *
   * @return {@code (1 - alpha) * q0 + alpha * q1}
   */

  public static <quaternionType> interpolateLinear(
    final <quaternionType> q0,
    final <quaternionType> q1,
    final <scalarRealType> alpha)
  {
    final <quaternionType> q0_r =
      scale(q0, <opClass>.subtractReal(<opClass>.one(), alpha));
    final <quaternionType> q1_r =
      scale(q1, alpha);
    return add(q0_r, q1_r);
  }

  /**
   * Calculate the squared magnitude of the quaternion {@code q0}.
   *
   * @param q0 The quaternion
   * @return The squared magnitude of the quaternion
   */

  public static <scalarLargeType> magnitudeSquared(
    final <quaternionType> q0)
  {
    return dotProduct(q0, q0);
  }

  /**
   * Calculate the magnitude of the quaternion {@code q0}.
   *
   * @param q0 The quaternion
   * @return The magnitude of the quaternion
   */

  public static <scalarRealType> magnitude(
    final <quaternionType> q0)
  {
    return <opClass>.squareRootLarge(magnitudeSquared(q0));
  }

  /**
   * \<p>Normalize the quaternion {@code q0}.\</p>
   * \<p>If the magnitude of the quaternion is zero, the function returns {@code q0}.\</p>
   * @param q0 The quaternion
   * @return A normalized copy of {@code q0}
   */

  public static <quaternionType> normalize(
    final <quaternionType> q0)
  {
    final <scalarLargeType> m = magnitudeSquared(q0);
    if (<opClass>.compareLarge(m, <opClass>.zero()) > 0) {
      final <scalarRealType> reciprocal =
        <opClass>.divideReal(<opClass>.one(), <opClass>.squareRootLarge(m));
      return scale(q0, reciprocal);
    }

    return q0;
  }

  /**
   * Scale {@code q0} by {@code r}.
   *
   * @return {@code (q0.x * r, q0.y * r, q0.z * r, q0.w * r) }
   *
   * @param q0 The left quaternion
   * @param r The scaling value
   */

  public static <quaternionType> scale(
    final <quaternionType> q0,
    final <scalarRealType> r)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.multiplyReal(q0.x(), r),
      (<quaternionComponentType>) <opClass>.multiplyReal(q0.y(), r),
      (<quaternionComponentType>) <opClass>.multiplyReal(q0.z(), r),
      (<quaternionComponentType>) <opClass>.multiplyReal(q0.w(), r));
  }

  /**
   * Add {@code q0} to {@code q1}.
   *
   * @return {@code (q0.x - q1.x, q0.y - q0.y, q0.z - q1.z, q0.w - q1.w) }
   *
   * @param q0 The left quaternion
   * @param q1 The right quaternion
   */

  public static <quaternionType> subtract(
    final <quaternionType> q0,
    final <quaternionType> q1)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.subtract(q0.x(), q1.x()),
      (<quaternionComponentType>) <opClass>.subtract(q0.y(), q1.y()),
      (<quaternionComponentType>) <opClass>.subtract(q0.z(), q1.z()),
      (<quaternionComponentType>) <opClass>.subtract(q0.w(), q1.w()));
  }

  /**
   * The zero quaternion.
   *
   * @return {@code (0, 0, 0, 0) }
   */

  public static <quaternionType> zero()
  {
    return ZERO;
  }
}
>>
