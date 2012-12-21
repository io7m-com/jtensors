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

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;

/**
 * A four-dimensional mutable quaternion type with double precision elements.
 * 
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

@NotThreadSafe public final class QuaternionM4D implements
  QuaternionReadable4D
{
  /**
   * The Context type contains the minimum storage required for all of the
   * functions of the <code>QuaternionM4D</code> class.
   * 
   * <p>
   * The purpose of the class is to allow applications to allocate all storage
   * ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   * 
   * <p>
   * The user should allocate one <code>Context</code> value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes
   * a <code>Context</code> value will not generate garbage.
   * </p>
   * 
   * @since 5.0.0
   */

  @NotThreadSafe public static final class Context
  {
    final @Nonnull MatrixM3x3D         m3a       = new MatrixM3x3D();
    final @Nonnull VectorM3D           v3a       = new VectorM3D();
    final @Nonnull MatrixM3x3D.Context m_context = new MatrixM3x3D.Context();

    public Context()
    {

    }
  }

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

  public static @Nonnull QuaternionM4D add(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q0.x + q1.x;
    final double y = q0.y + q1.y;
    final double z = q0.z + q1.z;
    final double w = q0.w + q1.w;
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

  public static @Nonnull QuaternionM4D addInPlace(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    return QuaternionM4D.add(q0, q1, q0);
  }

  /**
   * Determine whether or not the quaternions <code>qa</code> and
   * <code>qb</code> are equal to within the degree of error given in
   * <code>context</code>.
   * 
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
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
    final @Nonnull QuaternionM4D qa,
    final @Nonnull QuaternionM4D qb)
  {
    final boolean xs = AlmostEqualDouble.almostEqual(context, qa.x, qb.x);
    final boolean ys = AlmostEqualDouble.almostEqual(context, qa.y, qb.y);
    final boolean zs = AlmostEqualDouble.almostEqual(context, qa.z, qb.z);
    final boolean ws = AlmostEqualDouble.almostEqual(context, qa.w, qb.w);
    return xs && ys && zs && ws;
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

  public static @Nonnull QuaternionM4D conjugate(
    final @Nonnull QuaternionM4D q,
    final @Nonnull QuaternionM4D out)
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

  public static @Nonnull QuaternionM4D conjugateInPlace(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.conjugate(q, q);
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

  public static @Nonnull QuaternionM4D copy(
    final @Nonnull QuaternionReadable4D q,
    final @Nonnull QuaternionM4D out)
  {
    out.x = q.getXD();
    out.y = q.getYD();
    out.z = q.getZD();
    out.w = q.getWD();
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

  public static double dotProduct(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
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

  public static @Nonnull QuaternionM4D interpolateLinear(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final double alpha,
    final @Nonnull QuaternionM4D r)
  {
    final @Nonnull QuaternionM4D w0 = new QuaternionM4D();
    final @Nonnull QuaternionM4D w1 = new QuaternionM4D();

    QuaternionM4D.scale(q0, 1.0 - alpha, w0);
    QuaternionM4D.scale(q1, alpha, w1);

    return QuaternionM4D.add(w0, w1, r);
  }

  /**
   * Return <code>true</code> iff <code>qa</code> is the negation of
   * <code>qb</code>.
   * 
   * <p>
   * Each element is compared with
   * {@link AlmostEqualDouble#almostEqual(com.io7m.jaux.AlmostEqualDouble.ContextRelative, double, double)}
   * .
   * </p>
   * 
   * @since 5.0.0
   */

  public static boolean isNegationOf(
    final @Nonnull AlmostEqualDouble.ContextRelative context,
    final @Nonnull QuaternionReadable4D qa,
    final @Nonnull QuaternionReadable4D qb)
  {
    final double xa = qa.getXD();
    final double ya = qa.getYD();
    final double za = qa.getZD();
    final double wa = qa.getWD();

    final double xb = -qb.getXD();
    final double yb = -qb.getYD();
    final double zb = -qb.getZD();
    final double wb = -qb.getWD();

    final boolean xs = AlmostEqualDouble.almostEqual(context, xa, xb);
    final boolean ys = AlmostEqualDouble.almostEqual(context, ya, yb);
    final boolean zs = AlmostEqualDouble.almostEqual(context, za, zb);
    final boolean ws = AlmostEqualDouble.almostEqual(context, wa, wb);

    return xs && ys && zs && ws;
  }

  /**
   * Produce a quaternion that represents a rotation that "looks at" the point
   * at <code>target</code> assuming the viewer is at <code>origin</code>,
   * using <code>up</code> as the "up" vector, saving the result to
   * <code>q</code>.
   * 
   * <p>
   * The function uses storage preallocated in <code>context</code> to avoid
   * any new allocations.
   * </p>
   * 
   * @param context
   *          Preallocated storage
   * @param q
   *          The output quaternion
   * @param origin
   *          The origin point
   * @param target
   *          The target point
   * @param up
   *          The up vector
   * @return <code>q</code>
   * 
   * @since 5.0.0
   */

  public static @Nonnull QuaternionM4D lookAtWithContext(
    final @Nonnull Context context,
    final @Nonnull VectorReadable3D origin,
    final @Nonnull VectorReadable3D target,
    final @Nonnull VectorReadable3D up,
    final @Nonnull QuaternionM4D q)
  {
    final MatrixM3x3D m = context.m3a;
    final VectorM3D t = context.v3a;
    final MatrixM3x3D.Context mc = context.m_context;

    MatrixM3x3D.lookAtWithContext(mc, origin, target, up, m, t);
    QuaternionM4D.makeFromRotationMatrix3x3(m, q);
    return q;
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
    final @Nonnull QuaternionM4D q)
  {
    return Math.sqrt(QuaternionM4D.magnitudeSquared(q));
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
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.dotProduct(q, q);
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
   *          The normalized quaternion representing the axis
   * @param angle
   *          The angle to rotate, in radians
   * @param out
   *          The output quaternion
   * 
   * @return A quaternion representing the rotation
   */

  public static @Nonnull QuaternionM4D makeFromAxisAngle(
    final @Nonnull VectorReadable3D axis,
    final double angle,
    final @Nonnull QuaternionM4D out)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);

    out.x = axis.getXD() * sa;
    out.y = axis.getYD() * sa;
    out.z = axis.getZD() * sa;
    out.w = Math.cos(angle_r);

    QuaternionM4D.normalizeInPlace(out);
    return out;
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix <code>m</code>,
   * writing the result to <code>out</code>.
   * 
   * @since 5.0.0
   * @param m
   *          The rotation matrix
   * @param out
   *          The output quaternion
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4D makeFromRotationMatrix3x3(
    final @Nonnull MatrixReadable3x3D m,
    final @Nonnull QuaternionM4D out)
  {
    final double m00 = MatrixM3x3D.get(m, 0, 0);
    final double m01 = MatrixM3x3D.get(m, 0, 1);
    final double m02 = MatrixM3x3D.get(m, 0, 2);
    final double m10 = MatrixM3x3D.get(m, 1, 0);
    final double m11 = MatrixM3x3D.get(m, 1, 1);
    final double m12 = MatrixM3x3D.get(m, 1, 2);
    final double m20 = MatrixM3x3D.get(m, 2, 0);
    final double m21 = MatrixM3x3D.get(m, 2, 1);
    final double m22 = MatrixM3x3D.get(m, 2, 2);

    final double trace = MatrixM3x3D.trace(m);

    if (trace > 0) {
      final double S = Math.sqrt(trace + 1.0) * 2; // S = 4 * qw
      out.w = 0.25 * S;
      out.x = (m21 - m12) / S;
      out.y = (m02 - m20) / S;
      out.z = (m10 - m01) / S;
    } else if ((m00 > m11) && (m00 > m22)) {
      final double S = Math.sqrt((1.0 + m00) - m11 - m22) * 2; // S = 4 * qx
      out.w = (m21 - m12) / S;
      out.x = 0.25 * S;
      out.y = (m01 + m10) / S;
      out.z = (m02 + m20) / S;
    } else if (m11 > m22) {
      final double S = Math.sqrt((1.0 + m11) - m00 - m22) * 2; // S = 4 * qy
      out.w = (m02 - m20) / S;
      out.x = (m01 + m10) / S;
      out.y = 0.25 * S;
      out.z = (m12 + m21) / S;
    } else {
      final double S = Math.sqrt((1.0 + m22) - m00 - m11) * 2; // S = 4 * qz
      out.w = (m10 - m01) / S;
      out.x = (m02 + m20) / S;
      out.y = (m12 + m21) / S;
      out.z = 0.25 * S;
    }

    return out;
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix <code>m</code>,
   * writing the result to <code>out</code>.
   * 
   * @since 5.0.0
   * @param m
   *          The rotation matrix
   * @param out
   *          The output quaternion
   * @return <code>out</code>
   */

  public static @Nonnull QuaternionM4D makeFromRotationMatrix4x4(
    final @Nonnull MatrixReadable4x4D m,
    final @Nonnull QuaternionM4D out)
  {
    final double m00 = MatrixM4x4D.get(m, 0, 0);
    final double m01 = MatrixM4x4D.get(m, 0, 1);
    final double m02 = MatrixM4x4D.get(m, 0, 2);
    final double m10 = MatrixM4x4D.get(m, 1, 0);
    final double m11 = MatrixM4x4D.get(m, 1, 1);
    final double m12 = MatrixM4x4D.get(m, 1, 2);
    final double m20 = MatrixM4x4D.get(m, 2, 0);
    final double m21 = MatrixM4x4D.get(m, 2, 1);
    final double m22 = MatrixM4x4D.get(m, 2, 2);

    /**
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final double trace = m00 + m11 + m22;

    if (trace > 0) {
      final double S = Math.sqrt(trace + 1.0) * 2; // S = 4 * qw
      out.w = 0.25 * S;
      out.x = (m21 - m12) / S;
      out.y = (m02 - m20) / S;
      out.z = (m10 - m01) / S;
    } else if ((m00 > m11) && (m00 > m22)) {
      final double S = Math.sqrt((1.0 + m00) - m11 - m22) * 2; // S = 4 * qx
      out.w = (m21 - m12) / S;
      out.x = 0.25 * S;
      out.y = (m01 + m10) / S;
      out.z = (m02 + m20) / S;
    } else if (m11 > m22) {
      final double S = Math.sqrt((1.0 + m11) - m00 - m22) * 2; // S = 4 * qy
      out.w = (m02 - m20) / S;
      out.x = (m01 + m10) / S;
      out.y = 0.25 * S;
      out.z = (m12 + m21) / S;
    } else {
      final double S = Math.sqrt((1.0 + m22) - m00 - m11) * 2; // S = 4 * qz
      out.w = (m10 - m01) / S;
      out.x = (m02 + m20) / S;
      out.y = (m12 + m21) / S;
      out.z = 0.25 * S;
    }

    return out;
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

  public static @Nonnull MatrixM3x3D makeRotationMatrix3x3(
    final @Nonnull QuaternionM4D q,
    final @Nonnull MatrixM3x3D m)
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

    m.setUnsafe(0, 0, r0c0);
    m.setUnsafe(0, 1, r0c1);
    m.setUnsafe(0, 2, r0c2);

    m.setUnsafe(1, 0, r1c0);
    m.setUnsafe(1, 1, r1c1);
    m.setUnsafe(1, 2, r1c2);

    m.setUnsafe(2, 0, r2c0);
    m.setUnsafe(2, 1, r2c1);
    m.setUnsafe(2, 2, r2c2);

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

  public static @Nonnull MatrixM4x4D makeRotationMatrix4x4(
    final @Nonnull QuaternionM4D q,
    final @Nonnull MatrixM4x4D m)
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
   * The following code produces a quaternion <code>qr</code> that represents
   * a rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis:
   * </p>
   * 
   * <pre>
   * QuaternionM4D qr = new QuaternionM4D();
   * QuaternionM4D.multiply(qy, qx, qr);
   * QuaternionM4D.multiply(qz, qr, qr);
   * </pre>
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

  public static @Nonnull QuaternionM4D multiply(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D qr)
  {
    final double rx =
      ((q0.w * q1.x) + (q0.x * q1.w) + (q0.y * q1.z)) - (q0.z * q1.y);
    final double ry =
      ((q0.w * q1.y) - (q0.x * q1.z)) + (q0.y * q1.w) + (q0.z * q1.x);
    final double rz =
      (((q0.w * q1.z) + (q0.x * q1.y)) - (q0.y * q1.x)) + (q0.z * q1.w);
    final double rw =
      (q0.w * q1.w) - (q0.x * q1.x) - (q0.y * q1.y) - (q0.z * q1.z);

    qr.x = rx;
    qr.y = ry;
    qr.z = rz;
    qr.w = rw;
    return qr;
  }

  /**
   * Multiply the quaternion <code>q0</code> by the quaternion <code>q1</code>
   * , saving the result to <code>q0</code>.
   * 
   * @see QuaternionM4D#multiply(QuaternionM4D, QuaternionM4D, QuaternionM4D)
   */

  public static @Nonnull QuaternionM4D multiplyInPlace(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    return QuaternionM4D.multiply(q0, q1, q0);
  }

  /**
   * Negate the elements of <code>qa</code>, writing the resulting quaternion
   * to <code>out</code>.
   * 
   * @return out
   * 
   * @since 5.0.0
   */

  public static @Nonnull QuaternionM4D negate(
    final @Nonnull QuaternionM4D qa,
    final @Nonnull QuaternionM4D out)
  {
    final double x = -qa.x;
    final double y = -qa.y;
    final double z = -qa.z;
    final double w = -qa.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Negate the elements of <code>q</code>, modifying the quaternion in-place.
   * 
   * @return q
   * 
   * @since 5.0.0
   */

  public static @Nonnull QuaternionM4D negateInPlace(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.negate(q, q);
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

  public static @Nonnull QuaternionM4D normalize(
    final @Nonnull QuaternionM4D q,
    final @Nonnull QuaternionM4D out)
  {
    final double m = QuaternionM4D.magnitudeSquared(q);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return QuaternionM4D.scale(q, reciprocal, out);
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

  public static @Nonnull QuaternionM4D normalizeInPlace(
    final @Nonnull QuaternionM4D q)
  {
    return QuaternionM4D.normalize(q, q);
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

  public static @Nonnull QuaternionM4D scale(
    final @Nonnull QuaternionM4D q,
    final double r,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q.x * r;
    final double y = q.y * r;
    final double z = q.z * r;
    final double w = q.w * r;
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

  public static @Nonnull QuaternionM4D scaleInPlace(
    final @Nonnull QuaternionM4D q,
    final double r)
  {
    return QuaternionM4D.scale(q, r, q);
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

  public static @Nonnull QuaternionM4D subtract(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1,
    final @Nonnull QuaternionM4D out)
  {
    final double x = q0.x - q1.x;
    final double y = q0.y - q1.y;
    final double z = q0.z - q1.z;
    final double w = q0.w - q1.w;
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

  public static @Nonnull QuaternionM4D subtractInPlace(
    final @Nonnull QuaternionM4D q0,
    final @Nonnull QuaternionM4D q1)
  {
    return QuaternionM4D.subtract(q0, q1, q0);
  }

  public double x;
  public double y;
  public double z;
  public double w;

  /**
   * Default constructor, initializing the quaternion with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>
   */

  public QuaternionM4D()
  {
    this(0.0, 0.0, 0.0, 1.0);
  }

  /**
   * Construct a quaternion initialized with the given values.
   */

  public QuaternionM4D(
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
   * Construct a quaternion initialized with the values given in
   * <code>q</code>.
   */

  public QuaternionM4D(
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
    final @Nonnull QuaternionM4D other = (QuaternionM4D) obj;
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
    builder.append("[QuaternionM4D ");
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
