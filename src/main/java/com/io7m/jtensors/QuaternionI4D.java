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

import com.io7m.jaux.ApproximatelyEqualDouble;

/**
 * A four-dimensional immutable quaternion type with double precision
 * elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@Immutable public final class QuaternionI4D implements QuaternionReadable4D
{
  /**
   * The "identity" quaternion, [0.0 0.0 0.0 1.0]
   */

  public final static @Nonnull QuaternionI4D IDENTITY;

  static {
    IDENTITY = new QuaternionI4D(0.0, 0.0, 0.0, 1.0);
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

  public static @Nonnull QuaternionI4D add(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1)
  {
    final double x = q0.x + q1.x;
    final double y = q0.y + q1.y;
    final double z = q0.z + q1.z;
    final double w = q0.w + q1.w;
    return new QuaternionI4D(x, y, z, w);
  }

  /**
   * Determine whether or not the elements of the two quaternions
   * <code>q0</code> and <code>q1</code> are approximately equal.
   * 
   * @see ApproximatelyEqualDouble
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return true, iff <code>q0</code> is approximately equal to
   *         <code>q1</code> , within an appropriate degree of error for
   *         double precision floating point values
   */

  public static boolean approximatelyEqual(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(q0.x, q1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(q0.y, q1.y);
    final boolean ez =
      ApproximatelyEqualDouble.approximatelyEqual(q0.z, q1.z);
    final boolean ew =
      ApproximatelyEqualDouble.approximatelyEqual(q0.w, q1.w);
    return ex && ey && ez && ew;
  }

  /**
   * Calculate the conjugate of the input quaternion <code>q</code>.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return The conjugate of the input quaternion
   */

  public static @Nonnull QuaternionI4D conjugate(
    final @Nonnull QuaternionI4D q)
  {
    final double x = -q.x;
    final double y = -q.y;
    final double z = -q.z;
    final double w = q.w;
    return new QuaternionI4D(x, y, z, w);
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

  public static double dotProduct(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1)
  {
    final double x = q0.x * q1.x;
    final double y = q0.y * q1.y;
    final double z = q0.z * q1.z;
    final double w = q0.w * q1.w;
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

  public static @Nonnull QuaternionI4D interpolateLinear(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1,
    final double alpha)
  {
    final @Nonnull QuaternionI4D w0 = QuaternionI4D.scale(q0, 1.0 - alpha);
    final @Nonnull QuaternionI4D w1 = QuaternionI4D.scale(q1, alpha);
    return QuaternionI4D.add(w0, w1);
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

  public static double magnitude(
    final @Nonnull QuaternionI4D q)
  {
    return Math.sqrt(QuaternionI4D.magnitudeSquared(q));
  }

  /**
   * Calculate the squared magnitude of the quaternion <code>q</code>.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return The squared magnitude of the input quaternion
   */

  public static double magnitudeSquared(
    final @Nonnull QuaternionI4D q)
  {
    return QuaternionI4D.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of <code>angle</code>
   * degrees around the axis specified by <code>axis</code>. <code>axis</code>
   * is assumed to be of unit length.
   * 
   * @see VectorI3D#normalize(VectorI3D)
   * @see VectorI4D#normalize(VectorI4D)
   * @see VectorM3D#normalize(VectorM3D, VectorM3D)
   * @see VectorM4D#normalize(VectorM4D, VectorM4D)
   * 
   * @param axis
   *          The normalized vector representing the axis
   * @param angle
   *          The angle to rotate, in radians
   * 
   * @return A quaternion representing the rotation
   */

  public static @Nonnull QuaternionI4D makeFromAxisAngle(
    final @Nonnull VectorReadable3D axis,
    final double angle)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);
    final double x = axis.getXD() * sa;
    final double y = axis.getYD() * sa;
    final double z = axis.getZD() * sa;
    final double w = Math.cos(angle_r);
    return new QuaternionI4D(x, y, z, w);
  }

  /**
   * Produce a rotation matrix from the quaternion <code>q</code>, saving the
   * result to <code>m</code>.
   * 
   * @param q
   *          The input quaternion
   * @param m
   *          The output matrix
   * 
   * @return <code>m</code>
   */

  public static @Nonnull MatrixM4x4D makeRotationMatrix(
    final @Nonnull QuaternionI4D q,
    final @Nonnull MatrixM4x4D m)
  {
    final double xx = q.x * q.x;
    final double xy = q.x * q.y;
    final double xz = q.x * q.z;
    final double xw = q.x * q.w;
    final double yy = q.y * q.y;
    final double yz = q.y * q.z;
    final double yw = q.y * q.w;
    final double zz = q.z * q.z;
    final double zw = q.z * q.w;

    final double r0c0 = 1 - (2 * (yy + zz));
    final double r1c0 = 2 * (xy - zw);
    final double r2c0 = 2 * (xz + yw);
    final double r3c0 = 0.0;

    final double r0c1 = 2 * (xy + zw);
    final double r1c1 = 1 - (2 * (xx + zz));
    final double r2c1 = 2 * (yz - xw);
    final double r3c1 = 0.0;

    final double r0c2 = 2 * (xz - yw);
    final double r1c2 = 2 * (yz + xw);
    final double r2c2 = 1 - (2 * (xx + yy));
    final double r3c2 = 0.0;

    final double r0c3 = 0.0;
    final double r1c3 = 0.0;
    final double r2c3 = 0.0;
    final double r3c3 = 1.0;

    m.setUnsafe(0, 0, r0c0);
    m.setUnsafe(0, 1, r0c1);
    m.setUnsafe(0, 2, r0c2);
    m.setUnsafe(0, 3, r0c3);

    m.setUnsafe(1, 0, r1c0);
    m.setUnsafe(1, 1, r1c1);
    m.setUnsafe(1, 2, r1c2);
    m.setUnsafe(1, 3, r1c3);

    m.setUnsafe(2, 0, r2c0);
    m.setUnsafe(2, 1, r2c1);
    m.setUnsafe(2, 2, r2c2);
    m.setUnsafe(2, 3, r2c3);

    m.setUnsafe(3, 0, r3c0);
    m.setUnsafe(3, 1, r3c1);
    m.setUnsafe(3, 2, r3c2);
    m.setUnsafe(3, 3, r3c3);

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
   * qr0 = QuaternionI4D.multiply(qy, qx);
   * qr1 = QuaternionI4D.multiply(qz, qy);
   * </code>
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   */

  public static @Nonnull QuaternionI4D multiply(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1)
  {
    final double rx =
      ((q0.w * q1.x) + (q0.x * q1.w) + (q0.y * q1.z)) - (q0.z * q1.y);
    final double ry =
      ((q0.w * q1.y) - (q0.x * q1.z)) + (q0.y * q1.w) + (q0.z * q1.x);
    final double rz =
      (((q0.w * q1.z) + (q0.x * q1.y)) - (q0.y * q1.x)) + (q0.z * q1.w);
    final double rw =
      (q0.w * q1.w) - (q0.x * q1.x) - (q0.y * q1.y) - (q0.z * q1.z);

    return new QuaternionI4D(rx, ry, rz, rw);
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

  public static @Nonnull QuaternionI4D normalize(
    final @Nonnull QuaternionI4D q)
  {
    final double m = QuaternionI4D.magnitudeSquared(q);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return QuaternionI4D.scale(q, reciprocal);
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

  public static @Nonnull QuaternionI4D scale(
    final @Nonnull QuaternionI4D q,
    final double r)
  {
    return new QuaternionI4D(q.x * r, q.y * r, q.z * r, q.w * r);
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

  public static @Nonnull QuaternionI4D subtract(
    final @Nonnull QuaternionI4D q0,
    final @Nonnull QuaternionI4D q1)
  {
    final double x = q0.x - q1.x;
    final double y = q0.y - q1.y;
    final double z = q0.z - q1.z;
    final double w = q0.w - q1.w;
    return new QuaternionI4D(x, y, z, w);
  }

  private final double x;
  private final double y;
  private final double z;
  private final double w;

  /**
   * Default constructor, initializing the quaternion with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>
   */

  public QuaternionI4D()
  {
    this(0.0, 0.0, 0.0, 1.0);
  }

  /**
   * Construct a quaternion initialized with the given values.
   */

  public QuaternionI4D(
    final double x,
    final double y,
    final double z,
    final double w)
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

  public QuaternionI4D(
    final @Nonnull QuaternionReadable4D q)
  {
    this(q.getXD(), q.getYD(), q.getZD(), q.getWD());
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
    final @Nonnull QuaternionI4D other = (QuaternionI4D) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public double getWD()
  {
    return this.w;
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public double getZD()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.w);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[QuaternionI4D ");
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
