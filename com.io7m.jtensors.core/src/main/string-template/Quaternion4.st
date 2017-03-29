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
   * \<p>Multiply the quaternion {@code q0} by the quaternion {@code q1}.\</p>
   *
   * \<p>Note that this operation is not commutative.\</p>
   *
   * \<p>The function is most often used to concatenate quaternions to combine
   * rotations. As an example, assuming that:\</p>
   *
   * \<ul>
   *    \<li>{@code qx} represents some rotation around the X axis\</li>
   *    \<li>{@code qy} represents some rotation around the Y axis\</li>
   *    \<li>{@code qz} represents some rotation around the Z axis\</li>
   * \</ul>
   *
   * \<p>The following code produces a quaternion {@code qr1} that represents a
   * rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis:\</p>
   *
   * {@code qr0 = multiply(qy, qx); qr1 = multiply(qz, qy); }
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   * @return The multiplication of the input quaternions
   */

  public static <quaternionType> multiply(
    final <quaternionType> q0,
    final <quaternionType> q1)
  {
    final <scalarRealType> q0_x = q0.x();
    final <scalarRealType> q0_y = q0.y();
    final <scalarRealType> q0_z = q0.z();
    final <scalarRealType> q0_w = q0.w();

    final <scalarRealType> q1_x = q1.x();
    final <scalarRealType> q1_y = q1.y();
    final <scalarRealType> q1_z = q1.z();
    final <scalarRealType> q1_w = q1.w();

    final <scalarRealType> q0_w_q1_x =
      <opClass>.multiplyReal(q0_w, q1_x);
    final <scalarRealType> q0_x_q1_w =
      <opClass>.multiplyReal(q0_x, q1_w);
    final <scalarRealType> q0_y_q1_z =
      <opClass>.multiplyReal(q0_y, q1_z);
    final <scalarRealType> q0_z_q1_y =
      <opClass>.multiplyReal(q0_z, q1_y);
    final <scalarRealType> rx =
      <opClass>.subtract(
        <opClass>.add(
          <opClass>.add(q0_w_q1_x, q0_x_q1_w),
          q0_y_q1_z),
        q0_z_q1_y);

    final <scalarRealType> q0_w_q1_y =
      <opClass>.multiplyReal(q0_w, q1_y);
    final <scalarRealType> q0_x_q1_z =
      <opClass>.multiplyReal(q0_x, q1_z);
    final <scalarRealType> q0_y_q1_w =
      <opClass>.multiplyReal(q0_y, q1_w);
    final <scalarRealType> q0_z_q1_x =
      <opClass>.multiplyReal(q0_z, q1_x);
    final <scalarRealType> ry =
      <opClass>.add(
        <opClass>.subtract(q0_w_q1_y, q0_x_q1_z),
        <opClass>.add(q0_y_q1_w, q0_z_q1_x));

    final <scalarRealType> q0_w_q1_z =
      <opClass>.multiplyReal(q0_w, q1_z);
    final <scalarRealType> q0_x_q1_y =
      <opClass>.multiplyReal(q0_x, q1_y);
    final <scalarRealType> q0_y_q1_x =
      <opClass>.multiplyReal(q0_y, q1_x);
    final <scalarRealType> q0_z_q1_w =
      <opClass>.multiplyReal(q0_z, q1_w);
    final <scalarRealType> rz =
      <opClass>.add(
        <opClass>.subtract(
          <opClass>.add(q0_w_q1_z, q0_x_q1_y),
          q0_y_q1_x),
        q0_z_q1_w);

    final <scalarRealType> q0_w_q1_w =
      <opClass>.multiplyReal(q0_w, q1_w);
    final <scalarRealType> q0_x_q1_x =
      <opClass>.multiplyReal(q0_x, q1_x);
    final <scalarRealType> q0_y_q1_y =
      <opClass>.multiplyReal(q0_y, q1_y);
    final <scalarRealType> q0_z_q1_z =
      <opClass>.multiplyReal(q0_z, q1_z);
    final <scalarRealType> rw =
      <opClass>.subtract(
        <opClass>.subtract(
          <opClass>.subtract(
            q0_w_q1_w,
            q0_x_q1_x),
          q0_y_q1_y),
        q0_z_q1_z);

    return <quaternionType>.of(
      (<quaternionComponentType>) rx,
      (<quaternionComponentType>) ry,
      (<quaternionComponentType>) rz,
      (<quaternionComponentType>) rw);
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
   * Calculate the negation of the input quaternion {@code q}.
   *
   * @param q The input quaternion
   * @return The negation of the input quaternion
   */

  public static <quaternionType> negate(
    final <quaternionType> q)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.negate(q.x()),
      (<quaternionComponentType>) <opClass>.negate(q.y()),
      (<quaternionComponentType>) <opClass>.negate(q.z()),
      (<quaternionComponentType>) <opClass>.negate(q.w()));
  }

  /**
   * Calculate a quaternion that represents a rotation of {@code r} radians
   * around the axis {@code (axis_x, axis_y, axis_z)}.
   *
   * @param r The rotation in radians
   * @param axis_x The X component of the axis
   * @param axis_y The Y component of the axis
   * @param axis_z The Z component of the axis
   * @return A quaternion
   */

  public static <quaternionType> ofAxisAngle(
    final <scalarRealType> axis_x,
    final <scalarRealType> axis_y,
    final <scalarRealType> axis_z,
    final <scalarRealType> angle)
  {
    final <scalarRealType> angle_r = <opClass>.multiplyReal(angle, <opClass>.zeroPointFive());
    final <scalarRealType> sa = <opClass>.sine(angle_r);
    final <scalarRealType> x = <opClass>.multiplyReal(axis_x, sa);
    final <scalarRealType> y = <opClass>.multiplyReal(axis_y, sa);
    final <scalarRealType> z = <opClass>.multiplyReal(axis_z, sa);
    final <scalarRealType> w = <opClass>.cosine(angle_r);
    return <quaternionType>.of(
      (<quaternionComponentType>) x,
      (<quaternionComponentType>) y,
      (<quaternionComponentType>) z,
      (<quaternionComponentType>) w);
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
