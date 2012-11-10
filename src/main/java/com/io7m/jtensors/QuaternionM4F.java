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
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jaux.ApproximatelyEqualFloat;

/**
 * A four-dimensional mutable quaternion type with single precision elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@NotThreadSafe public final class QuaternionM4F implements
  QuaternionReadable4F
{
  /**
   * Calculate the element-wise sum of the quaternions <code>q0</code> and
   * <code>q1</code>, saving the result to <code>qr</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>(q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)</code>
   */

  public static @Nonnull QuaternionM4F add(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1,
    final @Nonnull QuaternionM4F out)
  {
    final float x = q0.x + q1.x;
    final float y = q0.y + q1.y;
    final float z = q0.z + q1.z;
    final float w = q0.w + q1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the element-wise sum of the quaternions <code>q0</code> and
   * <code>q1</code>, saving the result to <code>q0</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)</code>
   */

  public static @Nonnull QuaternionM4F addInPlace(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1)
  {
    return QuaternionM4F.add(q0, q1, q0);
  }

  /**
   * Determine whether or not the elements of the two quaternions
   * <code>q0</code> and <code>q1</code> are approximately equal.
   * 
   * @see ApproximatelyEqualFloat
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return true, iff <code>q0</code> is approximately equal to
   *         <code>q1</code>, within an appropriate degree of error for single
   *         precision floating point values
   */

  public static boolean approximatelyEqual(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1)
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
   * Calculate the conjugate of the input quaternion <code>q</code>, saving
   * the result to <code>out</code>.
   * 
   * @param q
   *          The input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4F conjugate(
    final @Nonnull QuaternionM4F q,
    final @Nonnull QuaternionM4F out)
  {
    out.x = -q.x;
    out.y = -q.y;
    out.z = -q.z;
    out.w = q.w;
    return out;
  }

  /**
   * Calculate the conjugate of the input quaternion <code>q</code>, modifying
   * <code>q</code> in place.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return <code>q</code>
   */

  public static @Nonnull QuaternionM4F conjugateInPlace(
    final @Nonnull QuaternionM4F q)
  {
    return QuaternionM4F.conjugate(q, q);
  }

  /**
   * Copy the contents of the quaternion <code>q</code> to <code>out</code>.
   * 
   * @param q
   *          The input quaternion
   * @param out
   *          The output quaternion
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4F copy(
    final @Nonnull QuaternionM4F q,
    final @Nonnull QuaternionM4F out)
  {
    out.x = q.x;
    out.y = q.y;
    out.z = q.z;
    out.w = q.w;
    return out;
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
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1)
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
   * <li><code>interpolateLinear(q0, q1, 0.0, r) -> r = q0</code></li>
   * <li><code>interpolateLinear(q0, q1, 1.0, r) -> r = q1</code></li>
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

  public static @Nonnull QuaternionM4F interpolateLinear(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1,
    final float alpha,
    final @Nonnull QuaternionM4F r)
  {
    final @Nonnull QuaternionM4F w0 = new QuaternionM4F();
    final @Nonnull QuaternionM4F w1 = new QuaternionM4F();

    QuaternionM4F.scale(q0, 1.0f - alpha, w0);
    QuaternionM4F.scale(q1, alpha, w1);

    return QuaternionM4F.add(w0, w1, r);
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
    final @Nonnull QuaternionM4F q)
  {
    return (float) Math.sqrt(QuaternionM4F.magnitudeSquared(q));
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
    final @Nonnull QuaternionM4F q)
  {
    return QuaternionM4F.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of <code>angle</code>
   * degrees around the axis specified by <code>axis</code>, saving the result
   * to <code>out</code>. <code>axis</code> is assumed to be of unit length.
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
   * @param out
   *          The output quaternion
   * 
   * @return A quaternion representing the rotation
   */

  public static @Nonnull QuaternionM4F makeFromAxisAngle(
    final @Nonnull VectorReadable3F axis,
    final float angle,
    final @Nonnull QuaternionM4F out)
  {
    final float angle_r = angle * 0.5f;
    final float sa = (float) Math.sin(angle_r);

    out.x = axis.getXF() * sa;
    out.y = axis.getYF() * sa;
    out.z = axis.getZF() * sa;
    out.w = (float) Math.cos(angle_r);
    return out;
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

  public static @Nonnull MatrixM4x4F makeRotationMatrix(
    final @Nonnull QuaternionM4F q,
    final @Nonnull MatrixM4x4F m)
  {
    final float xx = q.x * q.x;
    final float xy = q.x * q.y;
    final float xz = q.x * q.z;
    final float xw = q.x * q.w;
    final float yy = q.y * q.y;
    final float yz = q.y * q.z;
    final float yw = q.y * q.w;
    final float zz = q.z * q.z;
    final float zw = q.z * q.w;

    final float r0c0 = 1 - (2 * (yy + zz));
    final float r1c0 = 2 * (xy - zw);
    final float r2c0 = 2 * (xz + yw);
    final float r3c0 = 0.0f;

    final float r0c1 = 2 * (xy + zw);
    final float r1c1 = 1 - (2 * (xx + zz));
    final float r2c1 = 2 * (yz - xw);
    final float r3c1 = 0.0f;

    final float r0c2 = 2 * (xz - yw);
    final float r1c2 = 2 * (yz + xw);
    final float r2c2 = 1 - (2 * (xx + yy));
    final float r3c2 = 0.0f;

    final float r0c3 = 0.0f;
    final float r1c3 = 0.0f;
    final float r2c3 = 0.0f;
    final float r3c3 = 1.0f;

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
   * , saving the result to <code>qr</code>.
   * 
   * Note that this operation is not commutative.
   * 
   * The function is most often used to concatenate quaternions to combine
   * rotations. As an example, assuming that:
   * 
   * <ul>
   * <li><code>qx</code> represents some rotation around the X axis</li>
   * <li><code>qy</code> represents some rotation around the Y axis</li>
   * <li><code>qz</code> represents some rotation around the Z axis</li>
   * </ul>
   * 
   * The following code produces a quaternion <code>qr</code> that represents
   * a rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis:
   * 
   * <code>
   * QuaternionM4F qr = new QuaternionM4D();
   * QuaternionM4F.multiply(qy, qx, qr);
   * QuaternionM4F.multiply(qz, qr, qr);
   * </code>
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param qr
   *          The output quaternion
   * 
   * @return <code>qr</code>
   */

  public static @Nonnull QuaternionM4F multiply(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1,
    final @Nonnull QuaternionM4F qr)
  {
    final float rx =
      ((q0.w * q1.x) + (q0.x * q1.w) + (q0.y * q1.z)) - (q0.z * q1.y);
    final float ry =
      ((q0.w * q1.y) - (q0.x * q1.z)) + (q0.y * q1.w) + (q0.z * q1.x);
    final float rz =
      (((q0.w * q1.z) + (q0.x * q1.y)) - (q0.y * q1.x)) + (q0.z * q1.w);
    final float rw =
      (q0.w * q1.w) - (q0.x * q1.x) - (q0.y * q1.y) - (q0.z * q1.z);

    qr.x = rx;
    qr.y = ry;
    qr.z = rz;
    qr.w = rw;
    return qr;
  }

  /**
   * Returns a quaternion with the same orientation as <code>q</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero quaternion iff the input is the zero quaternion.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return out
   */

  public static @Nonnull QuaternionM4F normalize(
    final @Nonnull QuaternionM4F q,
    final @Nonnull QuaternionM4F out)
  {
    final float m = QuaternionM4F.magnitudeSquared(q);
    if (m > 0.0) {
      final float reciprocal = (float) (1.0 / Math.sqrt(m));
      return QuaternionM4F.scale(q, reciprocal, out);
    }
    out.x = q.x;
    out.y = q.y;
    out.z = q.z;
    out.w = q.w;
    return out;
  }

  /**
   * Returns a quaternion with the same orientation as <code>q</code> but with
   * magnitude equal to <code>1.0</code> in <code>q</code>. The function
   * returns the zero quaternion iff the input is the zero quaternion.
   * 
   * @param q
   *          The input quaternion
   * 
   * @return q
   */

  public static @Nonnull QuaternionM4F normalizeInPlace(
    final @Nonnull QuaternionM4F q)
  {
    return QuaternionM4F.normalize(q, q);
  }

  /**
   * Scale the quaternion <code>q</code> by the scalar <code>r</code>, saving
   * the result to <code>out</code>.
   * 
   * @param q
   *          The input quaternion
   * @param r
   *          The scaling value
   * @param out
   *          The output quaternion
   * 
   * @return <code>(q.x * r, q.y * r, q.z * r, q.w * r)</code>
   */

  public static @Nonnull QuaternionM4F scale(
    final @Nonnull QuaternionM4F q,
    final float r,
    final @Nonnull QuaternionM4F out)
  {
    final float x = q.x * r;
    final float y = q.y * r;
    final float z = q.z * r;
    final float w = q.w * r;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Scale the quaternion <code>q</code> by the scalar <code>r</code>, saving
   * the result to <code>q</code>.
   * 
   * @param q
   *          The input quaternion
   * @param r
   *          The scaling value
   * 
   * @return <code>(q.x * r, q.y * r, q.z * r, q.w * r)</code>
   */

  public static @Nonnull QuaternionM4F scaleInPlace(
    final @Nonnull QuaternionM4F q,
    final float r)
  {
    return QuaternionM4F.scale(q, r, q);
  }

  /**
   * Subtract the quaternion <code>q0</code> from the quaternion
   * <code>q1</code>, saving the result to <code>out</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param out
   *          The output quaternion
   * 
   * @return <code>(q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)</code>
   */

  public static @Nonnull QuaternionM4F subtract(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1,
    final @Nonnull QuaternionM4F out)
  {
    final float x = q0.x - q1.x;
    final float y = q0.y - q1.y;
    final float z = q0.z - q1.z;
    final float w = q0.w - q1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Subtract the quaternion <code>q0</code> from the quaternion
   * <code>q1</code>, saving the result to <code>q0</code>.
   * 
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * 
   * @return <code>(q0.x - q1.x, q0.y - q1.y, q0.z - q1.z, q0.w - q1.w)</code>
   */

  public static @Nonnull QuaternionM4F subtractInPlace(
    final @Nonnull QuaternionM4F q0,
    final @Nonnull QuaternionM4F q1)
  {
    return QuaternionM4F.subtract(q0, q1, q0);
  }

  public float x;
  public float y;
  public float z;
  public float w;

  /**
   * Default constructor, initializing the quaternion with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>
   */

  public QuaternionM4F()
  {
    this(0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Construct a quaternion initialized with the given values.
   */

  public QuaternionM4F(
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
   * Construct a quaternion initialized with the values given in
   * <code>q</code>.
   */

  public QuaternionM4F(
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
    final QuaternionM4F other = (QuaternionM4F) obj;
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
    builder.append("[QuaternionM4F ");
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
