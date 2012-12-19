/*
 * Copyright © 2012 http://io7m.com
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;

/**
 * A four-dimensional immutable quaternion type with single precision
 * elements.
 * 
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 */

@Immutable public final class QuaternionI4F implements QuaternionReadable4F
{
  /**
   * The "identity" quaternion, [0.0 0.0 0.0 1.0]
   */

  public final static @Nonnull QuaternionI4F IDENTITY;

  static {
    IDENTITY = new QuaternionI4F(0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Calculate the element-wise sum of the quaternions <code>q0</code> and
   * <code>q1</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)</code>
   */

  public static @Nonnull QuaternionI4F add(
    final @Nonnull QuaternionI4F q0,
    final @Nonnull QuaternionI4F q1)
  {
    final float x = q0.x + q1.x;
    final float y = q0.y + q1.y;
    final float z = q0.z + q1.z;
    final float w = q0.w + q1.w;
    return new QuaternionI4F(x, y, z, w);
  }

  /**
   * Determine whether or not the quaternions <code>qa</code> and
   * <code>qb</code> are equal to within the degree of error given in
   * <code>context</code>.
   * 
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
   * 
   * @param context
   *          The equality context
   * @param qa
   *          The left input quaternion
   * @param qb
   *          The right input quaternion
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final @Nonnull ContextRelative context,
    final @Nonnull QuaternionI4F qa,
    final @Nonnull QuaternionI4F qb)
  {
    final boolean xs = AlmostEqualFloat.almostEqual(context, qa.x, qb.x);
    final boolean ys = AlmostEqualFloat.almostEqual(context, qa.y, qb.y);
    final boolean zs = AlmostEqualFloat.almostEqual(context, qa.z, qb.z);
    final boolean ws = AlmostEqualFloat.almostEqual(context, qa.w, qb.w);
    return xs && ys && zs && ws;
  }

  /**
   * Calculate the conjugate of the input quaternion <code>q</code>.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return The conjugate of the input quaternion
   */

  public static @Nonnull QuaternionI4F conjugate(
    final @Nonnull QuaternionI4F q)
  {
    final float x = -q.x;
    final float y = -q.y;
    final float z = -q.z;
    final float w = q.w;
    return new QuaternionI4F(x, y, z, w);
  }

  /**
   * Calculate the scalar product of the quaternions <code>q0</code> and
   * <code>q1</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return The scalar product of the two quaternions
   */

  public static float dotProduct(
    final @Nonnull QuaternionI4F q0,
    final @Nonnull QuaternionI4F q1)
  {
    final float x = q0.x * q1.x;
    final float y = q0.y * q1.y;
    final float z = q0.z * q1.z;
    final float w = q0.w * q1.w;
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between <code>q0</code> and <code>q1</code> by the
   * amount <code>alpha</code>, such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(q0, q1, 0.0) = q0</code></li>
   * <li><code>interpolateLinear(q0, q1, 1.0) = q1</code></li>
   * </ul>
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>
   * 
   * @return <code>(1 - alpha) * q0 + alpha * q1</code>
   */

  public static @Nonnull QuaternionI4F interpolateLinear(
    final @Nonnull QuaternionI4F q0,
    final @Nonnull QuaternionI4F q1,
    final float alpha)
  {
    final @Nonnull QuaternionI4F w0 = QuaternionI4F.scale(q0, 1.0f - alpha);
    final @Nonnull QuaternionI4F w1 = QuaternionI4F.scale(q1, alpha);
    return QuaternionI4F.add(w0, w1);
  }

  /**
   * Return <code>true</code> iff <code>qa</code> is the negation of
   * <code>qb</code>.
   * 
   * <p>
   * Each element is compared with
   * {@link AlmostEqualFloat#almostEqual(com.io7m.jaux.AlmostEqualFloat.ContextRelative, float, float)}
   * .
   * </p>
   * 
   * @since 5.0.0
   */

  public static boolean isNegationOf(
    final @Nonnull AlmostEqualFloat.ContextRelative context,
    final @Nonnull QuaternionReadable4F qa,
    final @Nonnull QuaternionReadable4F qb)
  {
    final float xa = qa.getXF();
    final float ya = qa.getYF();
    final float za = qa.getZF();
    final float wa = qa.getWF();

    final float xb = -qb.getXF();
    final float yb = -qb.getYF();
    final float zb = -qb.getZF();
    final float wb = -qb.getWF();

    final boolean xs = AlmostEqualFloat.almostEqual(context, xa, xb);
    final boolean ys = AlmostEqualFloat.almostEqual(context, ya, yb);
    final boolean zs = AlmostEqualFloat.almostEqual(context, za, zb);
    final boolean ws = AlmostEqualFloat.almostEqual(context, wa, wb);

    return xs && ys && zs && ws;
  }

  /**
   * Calculate the magnitude of the quaternion <code>q</code>.
   * 
   * Correspondingly, <code>magnitude(normalize(q)) == 1.0</code>.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return The magnitude of the input quaternion
   */

  public static float magnitude(
    final @Nonnull QuaternionI4F q)
  {
    return (float) Math.sqrt(QuaternionI4F.magnitudeSquared(q));
  }

  /**
   * Calculate the squared magnitude of the quaternion <code>q</code>.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return The squared magnitude of the input quaternion
   */

  public static float magnitudeSquared(
    final @Nonnull QuaternionI4F q)
  {
    return QuaternionI4F.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of <code>angle</code>
   * degrees around the axis specified by <code>axis</code>. <code>axis</code>
   * is assumed to be of unit length.
   * 
   * @see VectorI3F#normalize(VectorI3F)
   * @see VectorI4F#normalize(VectorI4F)
   * @see VectorM3F#normalize(VectorM3F, VectorM3F)
   * @see VectorM4F#normalize(VectorM4F, VectorM4F)
   * 
   * @param axis
   *          The normalized vector representing the axis
   * @param angle
   *          The angle to rotate, in radians
   * 
   * @return A quaternion representing the rotation
   */

  public static @Nonnull QuaternionI4F makeFromAxisAngle(
    final @Nonnull VectorReadable3F axis,
    final double angle)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);
    final double x = axis.getXF() * sa;
    final double y = axis.getYF() * sa;
    final double z = axis.getZF() * sa;
    final double w = Math.cos(angle_r);
    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix <code>m</code>.
   * 
   * @since 5.0.0
   * @param m
   *          The rotation matrix
   */

  public static @Nonnull QuaternionI4F makeFromRotationMatrix3x3(
    final @Nonnull MatrixReadable3x3F m)
  {
    final double m00 = MatrixM3x3F.get(m, 0, 0);
    final double m01 = MatrixM3x3F.get(m, 0, 1);
    final double m02 = MatrixM3x3F.get(m, 0, 2);
    final double m10 = MatrixM3x3F.get(m, 1, 0);
    final double m11 = MatrixM3x3F.get(m, 1, 1);
    final double m12 = MatrixM3x3F.get(m, 1, 2);
    final double m20 = MatrixM3x3F.get(m, 2, 0);
    final double m21 = MatrixM3x3F.get(m, 2, 1);
    final double m22 = MatrixM3x3F.get(m, 2, 2);

    final double trace = MatrixM3x3F.trace(m);

    double x;
    double y;
    double z;
    double w;

    if (trace > 0) {
      final double S = Math.sqrt(trace + 1.0) * 2; // S = 4 * qw
      w = 0.25 * S;
      x = (m21 - m12) / S;
      y = (m02 - m20) / S;
      z = (m10 - m01) / S;
    } else if ((m00 > m11) && (m00 > m22)) {
      final double S = Math.sqrt((1.0 + m00) - m11 - m22) * 2; // S = 4 * qx
      w = (m21 - m12) / S;
      x = 0.25 * S;
      y = (m01 + m10) / S;
      z = (m02 + m20) / S;
    } else if (m11 > m22) {
      final double S = Math.sqrt((1.0 + m11) - m00 - m22) * 2; // S = 4 * qy
      w = (m02 - m20) / S;
      x = (m01 + m10) / S;
      y = 0.25 * S;
      z = (m12 + m21) / S;
    } else {
      final double S = Math.sqrt((1.0 + m22) - m00 - m11) * 2; // S = 4 * qz
      w = (m10 - m01) / S;
      x = (m02 + m20) / S;
      y = (m12 + m21) / S;
      z = 0.25 * S;
    }

    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix <code>m</code>,
   * writing the result to <code>out</code>.
   * 
   * @since 5.0.0
   * @param m
   *          The rotation matrix
   */

  public static @Nonnull QuaternionI4F makeFromRotationMatrix4x4(
    final @Nonnull MatrixReadable4x4F m)
  {
    final double m00 = MatrixM4x4F.get(m, 0, 0);
    final double m01 = MatrixM4x4F.get(m, 0, 1);
    final double m02 = MatrixM4x4F.get(m, 0, 2);
    final double m10 = MatrixM4x4F.get(m, 1, 0);
    final double m11 = MatrixM4x4F.get(m, 1, 1);
    final double m12 = MatrixM4x4F.get(m, 1, 2);
    final double m20 = MatrixM4x4F.get(m, 2, 0);
    final double m21 = MatrixM4x4F.get(m, 2, 1);
    final double m22 = MatrixM4x4F.get(m, 2, 2);

    /**
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final double trace = m00 + m11 + m22;

    double x;
    double y;
    double z;
    double w;

    if (trace > 0) {
      final double S = Math.sqrt(trace + 1.0) * 2; // S = 4 * qw
      w = 0.25 * S;
      x = (m21 - m12) / S;
      y = (m02 - m20) / S;
      z = (m10 - m01) / S;
    } else if ((m00 > m11) && (m00 > m22)) {
      final double S = Math.sqrt((1.0 + m00) - m11 - m22) * 2; // S = 4 * qx
      w = (m21 - m12) / S;
      x = 0.25 * S;
      y = (m01 + m10) / S;
      z = (m02 + m20) / S;
    } else if (m11 > m22) {
      final double S = Math.sqrt((1.0 + m11) - m00 - m22) * 2; // S = 4 * qy
      w = (m02 - m20) / S;
      x = (m01 + m10) / S;
      y = 0.25 * S;
      z = (m12 + m21) / S;
    } else {
      final double S = Math.sqrt((1.0 + m22) - m00 - m11) * 2; // S = 4 * qz
      w = (m10 - m01) / S;
      x = (m02 + m20) / S;
      y = (m12 + m21) / S;
      z = 0.25 * S;
    }

    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a rotation matrix from the quaternion <code>q</code>, saving the
   * result to <code>m</code>.
   * 
   * @since 5.0.0
   * @param q
   *          The input quaternion
   * @param m
   *          The output matrix
   * 
   * @return <code>m</code>
   */

  public static @Nonnull MatrixM3x3F makeRotationMatrix3x3(
    final @Nonnull QuaternionI4F q,
    final @Nonnull MatrixM3x3F m)
  {
    final double xx = q.x * q.x;
    final double xy = q.x * q.y;
    final double xz = q.x * q.z;
    final double yy = q.y * q.y;
    final double yz = q.y * q.z;
    final double zz = q.z * q.z;
    final double wx = q.w * q.x;
    final double wy = q.w * q.y;
    final double wz = q.w * q.z;

    final double r0c0 = 1.0 - (2 * yy) - (2 * zz);
    final double r0c1 = (2 * xy) - (2 * wz);
    final double r0c2 = (2 * xz) + (2 * wy);

    final double r1c0 = (2 * xy) + (2 * wz);
    final double r1c1 = 1.0 - (2 * xx) - (2 * zz);
    final double r1c2 = (2 * yz) - (2 * wx);

    final double r2c0 = (2 * xz) - (2 * wy);
    final double r2c1 = (2 * yz) + (2 * wx);
    final double r2c2 = 1.0 - (2 * xx) - (2 * yy);

    m.setUnsafe(0, 0, (float) r0c0);
    m.setUnsafe(0, 1, (float) r0c1);
    m.setUnsafe(0, 2, (float) r0c2);

    m.setUnsafe(1, 0, (float) r1c0);
    m.setUnsafe(1, 1, (float) r1c1);
    m.setUnsafe(1, 2, (float) r1c2);

    m.setUnsafe(2, 0, (float) r2c0);
    m.setUnsafe(2, 1, (float) r2c1);
    m.setUnsafe(2, 2, (float) r2c2);

    return m;
  }

  /**
   * Produce a rotation matrix from the quaternion <code>q</code>, saving the
   * result to <code>m</code>.
   * 
   * @since 5.0.0
   * @param q
   *          The input quaternion
   * @param m
   *          The output matrix
   * 
   * @return <code>m</code>
   */

  public static @Nonnull MatrixM4x4F makeRotationMatrix4x4(
    final @Nonnull QuaternionI4F q,
    final @Nonnull MatrixM4x4F m)
  {
    final double xx = q.x * q.x;
    final double xy = q.x * q.y;
    final double xz = q.x * q.z;
    final double yy = q.y * q.y;
    final double yz = q.y * q.z;
    final double zz = q.z * q.z;
    final double wx = q.w * q.x;
    final double wy = q.w * q.y;
    final double wz = q.w * q.z;

    final double r0c0 = 1.0 - (2 * yy) - (2 * zz);
    final double r0c1 = (2 * xy) - (2 * wz);
    final double r0c2 = (2 * xz) + (2 * wy);
    final double r0c3 = 0.0;

    final double r1c0 = (2 * xy) + (2 * wz);
    final double r1c1 = 1.0 - (2 * xx) - (2 * zz);
    final double r1c2 = (2 * yz) - (2 * wx);
    final double r1c3 = 0.0;

    final double r2c0 = (2 * xz) - (2 * wy);
    final double r2c1 = (2 * yz) + (2 * wx);
    final double r2c2 = 1.0 - (2 * xx) - (2 * yy);
    final double r2c3 = 0.0;

    final double r3c0 = 0.0;
    final double r3c1 = 0.0;
    final double r3c2 = 0.0;
    final double r3c3 = 1.0;

    m.setUnsafe(0, 0, (float) r0c0);
    m.setUnsafe(0, 1, (float) r0c1);
    m.setUnsafe(0, 2, (float) r0c2);
    m.setUnsafe(0, 3, (float) r0c3);

    m.setUnsafe(1, 0, (float) r1c0);
    m.setUnsafe(1, 1, (float) r1c1);
    m.setUnsafe(1, 2, (float) r1c2);
    m.setUnsafe(1, 3, (float) r1c3);

    m.setUnsafe(2, 0, (float) r2c0);
    m.setUnsafe(2, 1, (float) r2c1);
    m.setUnsafe(2, 2, (float) r2c2);
    m.setUnsafe(2, 3, (float) r2c3);

    m.setUnsafe(3, 0, (float) r3c0);
    m.setUnsafe(3, 1, (float) r3c1);
    m.setUnsafe(3, 2, (float) r3c2);
    m.setUnsafe(3, 3, (float) r3c3);

    return m;
  }

  /**
   * Multiply the quaternion <code>q0</code> by the quaternion <code>q1</code>
   * .
   * 
   * <p>
   * Note that this operation is not commutative.
   * </p>
   * 
   * <p>
   * The function is most often used to concatenate quaternions to combine
   * rotations. As an example, assuming that:
   * </p>
   * 
   * <ul>
   * <li><code>qx</code> represents some rotation around the X axis</li>
   * <li><code>qy</code> represents some rotation around the Y axis</li>
   * <li><code>qz</code> represents some rotation around the Z axis</li>
   * </ul>
   * 
   * <p>
   * The following code produces a quaternion <code>qr1</code> that represents
   * a rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis:
   * </p>
   * 
   * <code>
   * qr0 = QuaternionI4F.multiply(qy, qx);
   * qr1 = QuaternionI4F.multiply(qz, qy);
   * </code>
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   */

  public static @Nonnull QuaternionI4F multiply(
    final @Nonnull QuaternionI4F q0,
    final @Nonnull QuaternionI4F q1)
  {
    final float rx =
      ((q0.w * q1.x) + (q0.x * q1.w) + (q0.y * q1.z)) - (q0.z * q1.y);
    final float ry =
      ((q0.w * q1.y) - (q0.x * q1.z)) + (q0.y * q1.w) + (q0.z * q1.x);
    final float rz =
      (((q0.w * q1.z) + (q0.x * q1.y)) - (q0.y * q1.x)) + (q0.z * q1.w);
    final float rw =
      (q0.w * q1.w) - (q0.x * q1.x) - (q0.y * q1.y) - (q0.z * q1.z);

    return new QuaternionI4F(rx, ry, rz, rw);
  }

  /**
   * Negate the elements of <code>q</code>.
   * 
   * @since 5.0.0
   */

  public static @Nonnull QuaternionI4F negate(
    final @Nonnull QuaternionI4F q)
  {
    return new QuaternionI4F(-q.x, -q.y, -q.z, -q.w);
  }

  /**
   * Normalize the quaternion <code>q</code>, preserqing its direction but
   * reducing it to unit length.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return A quaternion with the same orientation as <code>q</code> but with
   *         magnitude equal to <code>1.0</code>
   */

  public static @Nonnull QuaternionI4F normalize(
    final @Nonnull QuaternionI4F q)
  {
    final float m = QuaternionI4F.magnitudeSquared(q);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return QuaternionI4F.scale(q, reciprocal);
    }
    return q;
  }

  /**
   * Scale the quaternion <code>q</code> by the scalar <code>r</code>.
   * 
   * @param q
   *          The input quaternion
   * @param r
   *          The scaling value
   * 
   * @return <code>(q.x * r, q.y * r, q.z * r, q.w * r)</code>
   */

  public static @Nonnull QuaternionI4F scale(
    final @Nonnull QuaternionI4F q,
    final float r)
  {
    return new QuaternionI4F(q.x * r, q.y * r, q.z * r, q.w * r);
  }

  /**
   * Subtract the quaternion <code>q0</code> from the quaternion
   * <code>q1</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x - q1.x, q0.y - q1.y, q0.z - q1.z)</code>
   */

  public static @Nonnull QuaternionI4F subtract(
    final @Nonnull QuaternionI4F q0,
    final @Nonnull QuaternionI4F q1)
  {
    final float x = q0.x - q1.x;
    final float y = q0.y - q1.y;
    final float z = q0.z - q1.z;
    final float w = q0.w - q1.w;
    return new QuaternionI4F(x, y, z, w);
  }

  final float x;
  final float y;
  final float z;
  final float w;

  /**
   * Default constructor, initializing the quaternion with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>
   */

  public QuaternionI4F()
  {
    this(0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Construct a quaternion initialized with the given values.
   */

  public QuaternionI4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  /**
   * Construct a quaternion initialized with the values contained in
   * <code>q</code>.
   */

  public QuaternionI4F(
    final @Nonnull QuaternionReadable4F q)
  {
    this(q.getXF(), q.getYF(), q.getZF(), q.getWF());
  }

  @Override public boolean equals(
    final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final QuaternionI4F other = (QuaternionI4F) obj;
    if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
      return false;
    }
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public float getWF()
  {
    return this.w;
  }

  @Override public float getXF()
  {
    return this.x;
  }

  @Override public float getYF()
  {
    return this.y;
  }

  @Override public float getZF()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.w);
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuaternionI4F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    return builder.toString();
  }

}
