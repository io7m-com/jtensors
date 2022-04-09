Quaternion4(
  className,
  quaternionType,
  quaternionComponentType,
  scalarLargeType,
  scalarRealType,
  matrix4x4Type,
  matrix4x4ComponentType,
  matrix4x4ParameterizedType,
  dotProductClass) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> https://www.io7m.com
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

import java.util.Objects;
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
    final <quaternionComponentType> z = (<quaternionComponentType>) 0.0;
    final <quaternionComponentType> s = (<quaternionComponentType>) 1.0;
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
      (<quaternionComponentType>) q0.x() + q1.x(),
      (<quaternionComponentType>) q0.y() + q1.y(),
      (<quaternionComponentType>) q0.z() + q1.z(),
      (<quaternionComponentType>) q0.w() + q1.w());
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
      (<quaternionComponentType>) -q.x(),
      (<quaternionComponentType>) -q.y(),
      (<quaternionComponentType>) -q.z(),
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
   * \<p>Linearly interpolate between {@code q0} and {@code q1} by the amount
   * {@code alpha}, such that:\</p>
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
    final <quaternionType> q0_r = scale(q0, 1.0 - alpha);
    final <quaternionType> q1_r = scale(q1, alpha);
    return add(q0_r, q1_r);
  }

  /**
   * \<p>Interpolate between {@code q0} and {@code q1}, using \<i>spherical
   * linear interpolation\</i>, by the amount {@code alpha}, such that:\</p>
   *
   * \<ul>
   *   \<li>{@code interpolateSphericalLinear(q0, q1, 0.0) = normalize(q0)}\</li>
   *   \<li>{@code interpolateSphericalLinear(q0, q1, 1.0) = normalize(q1)}\</li>
   * \</ul>
   *
   * \<p>Note that unlike simple linear interpolation, this function is guaranteed
   * to return a normalized quaternion.\</p>
   *
   * @param q0    The left input quaternion
   * @param q1    The right input quaternion
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   *
   * @return A spherical-linearly interpolated quaternion between {@code q0} and
   * {@code q1}
   */

  public static <quaternionType> interpolateSphericalLinear(
    final <quaternionType> q0,
    final <quaternionType> q1,
    final <scalarRealType> alpha)
  {
    final <quaternionType> q0n = normalize(q0);
    <quaternionType> q1n = normalize(q1);

    /*
     * Calculate the dot product to determine if the quaternions are nearly
     * codirectional. If they are, fall back to simple linear interpolation.
     */

    <scalarRealType> dot = dotProduct(q0n, q1n);
    final <scalarRealType> threshold = 0.9995;
    if (dot > threshold) {
      return normalize(interpolateLinear(q0n, q1n, alpha));
    }

    /*
     * If the dot product is negative, the quaternions are separated by more
     * than 180° and a spherical linear interpolation wouldn't result in the
     * shortest path. By negating one quaternion, the shortest path is obtained.
     */

    if (dot \< 0.0) {
      q1n = negate(q1n);
      dot = -dot;
    }

    dot = Math.max(-1.0, Math.min(dot, 1.0));
    final <scalarRealType> theta_0 = StrictMath.acos(dot);
    final <scalarRealType> theta = theta_0 * alpha;

    final <quaternionType> q2 = normalize(subtract(q1n, scale(q0n, dot)));
    final <quaternionType> r0 = scale(q0n, StrictMath.cos(theta));
    final <quaternionType> r1 = scale(q2, StrictMath.sin(theta));
    return add(r0, r1);
  }

  /**
   * The type of comparison functions for values of type {@code <quaternionType>}.
   */

  public interface ComponentComparatorType
  {
    /**
     * @param x The left value
     * @param y The right value
     * @return {@code true} iff {@code x ≅ y}
     */

    boolean equals(
      <scalarRealType> x,
      <scalarRealType> y);
  }

  /**
   * \<p>Return {@code true} iff {@code qa} is the negation of {@code qb}.\</p>
   *
   * @param qa The left quaternion
   * @param qb The right quaternion
   *
   * @return {@code true} iff {@code qa} is the negation of {@code qb}
   */

  public static boolean isNegationOf(
    final ComponentComparatorType c,
    final <quaternionType> qa,
    final <quaternionType> qb)
  {
    final <scalarLargeType> xa = qa.x();
    final <scalarLargeType> ya = qa.y();
    final <scalarLargeType> za = qa.z();
    final <scalarLargeType> wa = qa.w();

    final <scalarLargeType> xb = -qb.x();
    final <scalarLargeType> yb = -qb.y();
    final <scalarLargeType> zb = -qb.z();
    final <scalarLargeType> wb = -qb.w();

    final boolean xs = c.equals(xa, xb);
    final boolean ys = c.equals(ya, yb);
    final boolean zs = c.equals(za, zb);
    final boolean ws = c.equals(wa, wb);

    return xs && ys && zs && ws;
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
    return Math.sqrt(magnitudeSquared(q0));
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
    final <scalarLargeType> q0_w = q0.w();
    final <scalarLargeType> q1_x = q1.x();
    final <scalarLargeType> q0_x = q0.x();
    final <scalarLargeType> q1_w = q1.w();
    final <scalarLargeType> q0_y = q0.y();
    final <scalarLargeType> q0_z = q0.z();
    final <scalarLargeType> q1_y = q1.y();
    final <scalarLargeType> q1_z = q1.z();

    final <scalarLargeType> rx =
      ((q0_w * q1_x) + (q0_x * q1_w) + (q0_y * q1_z)) - (q0_z * q1_y);
    final <scalarLargeType> ry =
      ((q0_w * q1_y) - (q0_x * q1_z)) + (q0_y * q1_w) + (q0_z * q1_x);
    final <scalarLargeType> rz =
      (((q0_w * q1_z) + (q0_x * q1_y)) - (q0_y * q1_x)) + (q0_z * q1_w);
    final <scalarLargeType> rw =
      (q0_w * q1_w) - (q0_x * q1_x) - (q0_y * q1_y) - (q0_z * q1_z);

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
    if (m > 0.0) {
      final <scalarRealType> reciprocal = 1.0 / Math.sqrt(m);
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
      (<quaternionComponentType>) -q.x(),
      (<quaternionComponentType>) -q.y(),
      (<quaternionComponentType>) -q.z(),
      (<quaternionComponentType>) -q.w());
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
    final <scalarRealType> r)
  {
    final <scalarRealType> angle_r = r * 0.5;
    final <scalarRealType> sa = Math.sin(angle_r);
    final <scalarRealType> x = axis_x * sa;
    final <scalarRealType> y = axis_y * sa;
    final <scalarRealType> z = axis_z * sa;
    final <scalarRealType> w = Math.cos(angle_r);
    return <quaternionType>.of(
      (<quaternionComponentType>) x,
      (<quaternionComponentType>) y,
      (<quaternionComponentType>) z,
      (<quaternionComponentType>) w);
  }

  /**
   * Calculate a quaternion that represents the rotation given by the matrix {@code m}.
   *
   * @param m The matrix
   * @return A quaternion
   */

  public static <quaternionType> ofMatrix4x4(
    final <matrix4x4Type> m)
  {
    final <scalarRealType> m_r0c0 = m.r0c0();
    final <scalarRealType> m_r0c1 = m.r0c1();
    final <scalarRealType> m_r0c2 = m.r0c2();
    final <scalarRealType> m_r1c0 = m.r1c0();
    final <scalarRealType> m_r1c1 = m.r1c1();
    final <scalarRealType> m_r1c2 = m.r1c2();
    final <scalarRealType> m_r2c0 = m.r2c0();
    final <scalarRealType> m_r2c1 = m.r2c1();
    final <scalarRealType> m_r2c2 = m.r2c2();

    /*
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final <scalarRealType> trace = m_r0c0 + m_r1c1 + m_r2c2;

    final <scalarRealType> x;
    final <scalarRealType> y;
    final <scalarRealType> z;
    final <scalarRealType> w;

    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m_r2c1 - m_r1c2) / s;
      y = (m_r0c2 - m_r2c0) / s;
      z = (m_r1c0 - m_r0c1) / s;
    } else if ((m_r0c0 > m_r1c1) && (m_r0c0 > m_r2c2)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m_r0c0) - m_r1c1 - m_r2c2) * 2.0;
      w = (m_r2c1 - m_r1c2) / s;
      x = 0.25 * s;
      y = (m_r0c1 + m_r1c0) / s;
      z = (m_r0c2 + m_r2c0) / s;
    } else if (m_r1c1 > m_r2c2) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m_r1c1) - m_r0c0 - m_r2c2) * 2.0;
      w = (m_r0c2 - m_r2c0) / s;
      x = (m_r0c1 + m_r1c0) / s;
      y = 0.25 * s;
      z = (m_r1c2 + m_r2c1) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m_r2c2) - m_r0c0 - m_r1c1) * 2.0;
      w = (m_r1c0 - m_r0c1) / s;
      x = (m_r0c2 + m_r2c0) / s;
      y = (m_r1c2 + m_r2c1) / s;
      z = 0.25 * s;
    }

    return <quaternionType>.of(
      (<quaternionComponentType>) x,
      (<quaternionComponentType>) y,
      (<quaternionComponentType>) z,
      (<quaternionComponentType>) w);
  }

  /**
   * Calculate a quaternion that represents the rotation given by the matrix {@code m}.
   *
   * @param \<A> A phantom type parameter, possibly representing a source coordinate system
   * @param \<B> A phantom type parameter, possibly representing a target coordinate system
   * @param m The matrix
   * @return A quaternion
   */

  public static \<A, B> <quaternionType> ofPMatrix4x4(
    final <matrix4x4ParameterizedType>\<A, B> m)
  {
    final <scalarRealType> m_r0c0 = m.r0c0();
    final <scalarRealType> m_r0c1 = m.r0c1();
    final <scalarRealType> m_r0c2 = m.r0c2();
    final <scalarRealType> m_r1c0 = m.r1c0();
    final <scalarRealType> m_r1c1 = m.r1c1();
    final <scalarRealType> m_r1c2 = m.r1c2();
    final <scalarRealType> m_r2c0 = m.r2c0();
    final <scalarRealType> m_r2c1 = m.r2c1();
    final <scalarRealType> m_r2c2 = m.r2c2();

    /*
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final <scalarRealType> trace = m_r0c0 + m_r1c1 + m_r2c2;

    final <scalarRealType> x;
    final <scalarRealType> y;
    final <scalarRealType> z;
    final <scalarRealType> w;

    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m_r2c1 - m_r1c2) / s;
      y = (m_r0c2 - m_r2c0) / s;
      z = (m_r1c0 - m_r0c1) / s;
    } else if ((m_r0c0 > m_r1c1) && (m_r0c0 > m_r2c2)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m_r0c0) - m_r1c1 - m_r2c2) * 2.0;
      w = (m_r2c1 - m_r1c2) / s;
      x = 0.25 * s;
      y = (m_r0c1 + m_r1c0) / s;
      z = (m_r0c2 + m_r2c0) / s;
    } else if (m_r1c1 > m_r2c2) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m_r1c1) - m_r0c0 - m_r2c2) * 2.0;
      w = (m_r0c2 - m_r2c0) / s;
      x = (m_r0c1 + m_r1c0) / s;
      y = 0.25 * s;
      z = (m_r1c2 + m_r2c1) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m_r2c2) - m_r0c0 - m_r1c1) * 2.0;
      w = (m_r1c0 - m_r0c1) / s;
      x = (m_r0c2 + m_r2c0) / s;
      y = (m_r1c2 + m_r2c1) / s;
      z = 0.25 * s;
    }

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
      (<quaternionComponentType>) (q0.x() * r),
      (<quaternionComponentType>) (q0.y() * r),
      (<quaternionComponentType>) (q0.z() * r),
      (<quaternionComponentType>) (q0.w() * r));
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
      (<quaternionComponentType>) (q0.x() - q1.x()),
      (<quaternionComponentType>) (q0.y() - q1.y()),
      (<quaternionComponentType>) (q0.z() - q1.z()),
      (<quaternionComponentType>) (q0.w() - q1.w()));
  }

  /**
   * Create a 4x4 matrix from the quaternion {@code q}.
   *
   * @return A matrix based on q
   * @param q The quaternion
   */

  public static <matrix4x4Type> toMatrix4x4(
    final <quaternionType> q)
  {
    final <scalarRealType> xx = q.x() * q.x();
    final <scalarRealType> xy = q.x() * q.y();
    final <scalarRealType> xz = q.x() * q.z();
    final <scalarRealType> yy = q.y() * q.y();
    final <scalarRealType> yz = q.y() * q.z();
    final <scalarRealType> zz = q.z() * q.z();
    final <scalarRealType> wx = q.w() * q.x();
    final <scalarRealType> wy = q.w() * q.y();
    final <scalarRealType> wz = q.w() * q.z();

    final <scalarRealType> r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final <scalarRealType> r0c1 = (2.0 * xy) - (2.0 * wz);
    final <scalarRealType> r0c2 = (2.0 * xz) + (2.0 * wy);
    final <scalarRealType> r0c3 = 0.0;

    final <scalarRealType> r1c0 = (2.0 * xy) + (2.0 * wz);
    final <scalarRealType> r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final <scalarRealType> r1c2 = (2.0 * yz) - (2.0 * wx);
    final <scalarRealType> r1c3 = 0.0;

    final <scalarRealType> r2c0 = (2.0 * xz) - (2.0 * wy);
    final <scalarRealType> r2c1 = (2.0 * yz) + (2.0 * wx);
    final <scalarRealType> r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);
    final <scalarRealType> r2c3 = 0.0;

    final <scalarRealType> r3c0 = 0.0;
    final <scalarRealType> r3c1 = 0.0;
    final <scalarRealType> r3c2 = 0.0;
    final <scalarRealType> r3c3 = 1.0;

    return <matrix4x4Type>.of(
      (<matrix4x4ComponentType>) r0c0, (<matrix4x4ComponentType>) r0c1, (<matrix4x4ComponentType>) r0c2, (<matrix4x4ComponentType>) r0c3,
      (<matrix4x4ComponentType>) r1c0, (<matrix4x4ComponentType>) r1c1, (<matrix4x4ComponentType>) r1c2, (<matrix4x4ComponentType>) r1c3,
      (<matrix4x4ComponentType>) r2c0, (<matrix4x4ComponentType>) r2c1, (<matrix4x4ComponentType>) r2c2, (<matrix4x4ComponentType>) r2c3,
      (<matrix4x4ComponentType>) r3c0, (<matrix4x4ComponentType>) r3c1, (<matrix4x4ComponentType>) r3c2, (<matrix4x4ComponentType>) r3c3);
  }

  /**
   * Create a 4x4 matrix from the quaternion {@code q}.
   *
   * @param \<A> A phantom type parameter, possibly representing a source coordinate system
   * @param \<B> A phantom type parameter, possibly representing a target coordinate system
   * @return A matrix based on q
   * @param q The quaternion
   */

  public static \<A, B> <matrix4x4ParameterizedType>\<A, B> toPMatrix4x4(
    final <quaternionType> q)
  {
    final <scalarRealType> xx = q.x() * q.x();
    final <scalarRealType> xy = q.x() * q.y();
    final <scalarRealType> xz = q.x() * q.z();
    final <scalarRealType> yy = q.y() * q.y();
    final <scalarRealType> yz = q.y() * q.z();
    final <scalarRealType> zz = q.z() * q.z();
    final <scalarRealType> wx = q.w() * q.x();
    final <scalarRealType> wy = q.w() * q.y();
    final <scalarRealType> wz = q.w() * q.z();

    final <scalarRealType> r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final <scalarRealType> r0c1 = (2.0 * xy) - (2.0 * wz);
    final <scalarRealType> r0c2 = (2.0 * xz) + (2.0 * wy);
    final <scalarRealType> r0c3 = 0.0;

    final <scalarRealType> r1c0 = (2.0 * xy) + (2.0 * wz);
    final <scalarRealType> r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final <scalarRealType> r1c2 = (2.0 * yz) - (2.0 * wx);
    final <scalarRealType> r1c3 = 0.0;

    final <scalarRealType> r2c0 = (2.0 * xz) - (2.0 * wy);
    final <scalarRealType> r2c1 = (2.0 * yz) + (2.0 * wx);
    final <scalarRealType> r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);
    final <scalarRealType> r2c3 = 0.0;

    final <scalarRealType> r3c0 = 0.0;
    final <scalarRealType> r3c1 = 0.0;
    final <scalarRealType> r3c2 = 0.0;
    final <scalarRealType> r3c3 = 1.0;

    return <matrix4x4ParameterizedType>.of(
      (<matrix4x4ComponentType>) r0c0, (<matrix4x4ComponentType>) r0c1, (<matrix4x4ComponentType>) r0c2, (<matrix4x4ComponentType>) r0c3,
      (<matrix4x4ComponentType>) r1c0, (<matrix4x4ComponentType>) r1c1, (<matrix4x4ComponentType>) r1c2, (<matrix4x4ComponentType>) r1c3,
      (<matrix4x4ComponentType>) r2c0, (<matrix4x4ComponentType>) r2c1, (<matrix4x4ComponentType>) r2c2, (<matrix4x4ComponentType>) r2c3,
      (<matrix4x4ComponentType>) r3c0, (<matrix4x4ComponentType>) r3c1, (<matrix4x4ComponentType>) r3c2, (<matrix4x4ComponentType>) r3c3);
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
