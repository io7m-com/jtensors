/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jnull.Nullable;

/**
 * A four-dimensional mutable quaternion type with single precision elements.
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

public final class QuaternionM4F implements
  QuaternionReadable4FType,
  QuaternionWritable4FType
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

  public static class Context
  {
    private final MatrixM3x3F.Context m_context = new MatrixM3x3F.Context();
    private final MatrixM3x3F         m3a       = new MatrixM3x3F();
    private final VectorM3F           v3a       = new VectorM3F();

    /**
     * Construct a new context.
     */

    public Context()
    {

    }

    final MatrixM3x3F.Context getContext()
    {
      return this.m_context;
    }

    final MatrixM3x3F getM3A()
    {
      return this.m3a;
    }

    final VectorM3F getV3A()
    {
      return this.v3a;
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

  public static QuaternionM4F add(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final QuaternionM4F out)
  {
    final float x = q0.getXF() + q1.getXF();
    final float y = q0.getYF() + q1.getYF();
    final float z = q0.getZF() + q1.getZF();
    final float w = q0.getWF() + q1.getWF();
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

  public static QuaternionM4F addInPlace(
    final QuaternionM4F q0,
    final QuaternionReadable4FType q1)
  {
    return QuaternionM4F.add(q0, q1, q0);
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
   * @return <code>true</code> if the quaternions are almost equal
   */

  public static boolean almostEqual(
    final ContextRelative context,
    final QuaternionReadable4FType qa,
    final QuaternionReadable4FType qb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, qa.getXF(), qb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, qa.getYF(), qb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, qa.getZF(), qb.getZF());
    final boolean ws =
      AlmostEqualFloat.almostEqual(context, qa.getWF(), qb.getWF());
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

  public static QuaternionM4F conjugate(
    final QuaternionReadable4FType q,
    final QuaternionM4F out)
  {
    out.x = -q.getXF();
    out.y = -q.getYF();
    out.z = -q.getZF();
    out.w = q.getWF();
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

  public static QuaternionM4F conjugateInPlace(
    final QuaternionM4F q)
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

  public static QuaternionM4F copy(
    final QuaternionReadable4FType q,
    final QuaternionM4F out)
  {
    out.x = q.getXF();
    out.y = q.getYF();
    out.z = q.getZF();
    out.w = q.getWF();
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
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1)
  {
    final double x = q0.getXF() * q1.getXF();
    final double y = q0.getYF() * q1.getYF();
    final double z = q0.getZF() * q1.getZF();
    final double w = q0.getWF() * q1.getWF();
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between <code>q0</code> and <code>q1</code> by the
   * amount <code>alpha</code>, such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(q0, q1, 0.0, r) -> r = q0}</li>
   * <li>{@code interpolateLinear(q0, q1, 1.0, r) -> r = q1}</li>
   * </ul>
   *
   * @param q0
   *          The left input quaternion
   * @param q1
   *          The right input quaternion
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>
   * @param r
   *          The output quaternion
   *
   * @return <code>(1 - alpha) * q0 + alpha * q1</code>
   */

  public static QuaternionM4F interpolateLinear(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final double alpha,
    final QuaternionM4F r)
  {
    final QuaternionM4F w0 = new QuaternionM4F();
    final QuaternionM4F w1 = new QuaternionM4F();

    QuaternionM4F.scale(q0, 1.0f - alpha, w0);
    QuaternionM4F.scale(q1, alpha, w1);

    return QuaternionM4F.add(w0, w1, r);
  }

  /**
   * Return <code>true</code> iff <code>qa</code> is the negation of
   * <code>qb</code>.
   *
   * <p>
   * Each element is compared with
   * {@link AlmostEqualFloat#almostEqual(ContextRelative, float, float)}.
   * </p>
   *
   * @param context
   *          The equality context
   * @param qa
   *          The left quaternion
   * @param qb
   *          The right quaternion
   * @since 5.0.0
   * @return <code>true</code> iff <code>qa</code> is the negation of
   *         <code>qb</code>
   */

  public static boolean isNegationOf(
    final AlmostEqualFloat.ContextRelative context,
    final QuaternionReadable4FType qa,
    final QuaternionReadable4FType qb)
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

  public static QuaternionM4F lookAtWithContext(
    final Context context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up,
    final QuaternionM4F q)
  {
    final MatrixM3x3F m = context.getM3A();
    final VectorM3F t = context.getV3A();
    final MatrixM3x3F.Context mc = context.getContext();

    MatrixM3x3F.lookAtWithContext(mc, origin, target, up, m, t);
    QuaternionM4F.makeFromRotationMatrix3x3(m, q);
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
    final QuaternionReadable4FType q)
  {
    return Math.sqrt(QuaternionM4F.magnitudeSquared(q));
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
    final QuaternionReadable4FType q)
  {
    return QuaternionM4F.dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of <code>angle</code>
   * degrees around the axis specified by <code>axis</code>, saving the result
   * to <code>out</code>. <code>axis</code> is assumed to be of unit length.
   *
   * @see VectorI3F#normalize(VectorReadable3FType)
   * @see VectorI4F#normalize(VectorReadable4FType)
   * @see VectorM3F#normalize(VectorReadable3FType, VectorM3F)
   * @see VectorM4F#normalize(VectorReadable4FType, VectorM4F)
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

  public static QuaternionM4F makeFromAxisAngle(
    final VectorReadable3FType axis,
    final double angle,
    final QuaternionM4F out)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);

    out.x = (float) (axis.getXF() * sa);
    out.y = (float) (axis.getYF() * sa);
    out.z = (float) (axis.getZF() * sa);
    out.w = (float) Math.cos(angle_r);
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

  public static QuaternionM4F makeFromRotationMatrix3x3(
    final MatrixReadable3x3FType m,
    final QuaternionM4F out)
  {
    final double m00 = m.getRowColumnF(0, 0);
    final double m01 = m.getRowColumnF(0, 1);
    final double m02 = m.getRowColumnF(0, 2);
    final double m10 = m.getRowColumnF(1, 0);
    final double m11 = m.getRowColumnF(1, 1);
    final double m12 = m.getRowColumnF(1, 2);
    final double m20 = m.getRowColumnF(2, 0);
    final double m21 = m.getRowColumnF(2, 1);
    final double m22 = m.getRowColumnF(2, 2);
    final double trace = MatrixM3x3F.trace(m);

    final double x;
    final double y;
    final double z;
    final double w;

    if (trace > 0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    out.x = (float) x;
    out.y = (float) y;
    out.z = (float) z;
    out.w = (float) w;
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

  public static QuaternionM4F makeFromRotationMatrix4x4(
    final MatrixReadable4x4FType m,
    final QuaternionM4F out)
  {
    final double m00 = m.getRowColumnF(0, 0);
    final double m01 = m.getRowColumnF(0, 1);
    final double m02 = m.getRowColumnF(0, 2);
    final double m10 = m.getRowColumnF(1, 0);
    final double m11 = m.getRowColumnF(1, 1);
    final double m12 = m.getRowColumnF(1, 2);
    final double m20 = m.getRowColumnF(2, 0);
    final double m21 = m.getRowColumnF(2, 1);
    final double m22 = m.getRowColumnF(2, 2);

    /**
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final double trace = m00 + m11 + m22;

    final double x;
    final double y;
    final double z;
    final double w;

    if (trace > 0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    out.x = (float) x;
    out.y = (float) y;
    out.z = (float) z;
    out.w = (float) w;
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

  public static MatrixM3x3F makeRotationMatrix3x3(
    final QuaternionM4F q,
    final MatrixM3x3F m)
  {
    final double xx = q.getXF() * q.getXF();
    final double xy = q.getXF() * q.getYF();
    final double xz = q.getXF() * q.getZF();
    final double yy = q.getYF() * q.getYF();
    final double yz = q.getYF() * q.getZF();
    final double zz = q.getZF() * q.getZF();
    final double wx = q.getWF() * q.getXF();
    final double wy = q.getWF() * q.getYF();
    final double wz = q.getWF() * q.getZF();

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
   * @param <M>
   *          The precise type of matrix.
   */

  public static <M extends MatrixWritable4x4FType> M makeRotationMatrix4x4(
    final QuaternionReadable4FType q,
    final M m)
  {
    final double xx = q.getXF() * q.getXF();
    final double xy = q.getXF() * q.getYF();
    final double xz = q.getXF() * q.getZF();
    final double yy = q.getYF() * q.getYF();
    final double yz = q.getYF() * q.getZF();
    final double zz = q.getZF() * q.getZF();
    final double wx = q.getWF() * q.getXF();
    final double wy = q.getWF() * q.getYF();
    final double wz = q.getWF() * q.getZF();

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

    m.setRowColumnF(0, 0, (float) r0c0);
    m.setRowColumnF(0, 1, (float) r0c1);
    m.setRowColumnF(0, 2, (float) r0c2);
    m.setRowColumnF(0, 3, (float) r0c3);

    m.setRowColumnF(1, 0, (float) r1c0);
    m.setRowColumnF(1, 1, (float) r1c1);
    m.setRowColumnF(1, 2, (float) r1c2);
    m.setRowColumnF(1, 3, (float) r1c3);

    m.setRowColumnF(2, 0, (float) r2c0);
    m.setRowColumnF(2, 1, (float) r2c1);
    m.setRowColumnF(2, 2, (float) r2c2);
    m.setRowColumnF(2, 3, (float) r2c3);

    m.setRowColumnF(3, 0, (float) r3c0);
    m.setRowColumnF(3, 1, (float) r3c1);
    m.setRowColumnF(3, 2, (float) r3c2);
    m.setRowColumnF(3, 3, (float) r3c3);

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
   * <code>
   * QuaternionM4F qr = new QuaternionM4F();
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

  public static QuaternionM4F multiply(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final QuaternionM4F qr)
  {
    final float q0x = q0.getXF();
    final float q0y = q0.getYF();
    final float q0z = q0.getZF();
    final float q0w = q0.getWF();
    final float q1x = q1.getXF();
    final float q1y = q1.getYF();
    final float q1z = q1.getZF();
    final float q1w = q1.getWF();

    final float rx = ((q0w * q1x) + (q0x * q1w) + (q0y * q1z)) - (q0z * q1y);
    final float ry = ((q0w * q1y) - (q0x * q1z)) + (q0y * q1w) + (q0z * q1x);
    final float rz =
      (((q0w * q1z) + (q0x * q1y)) - (q0y * q1x)) + (q0z * q1w);
    final float rw = (q0w * q1w) - (q0x * q1x) - (q0y * q1y) - (q0z * q1z);

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
   * @see QuaternionM4F#multiply(QuaternionReadable4FType,
   *      QuaternionReadable4FType, QuaternionM4F)
   *
   * @param q0
   *          The left quaternion
   * @param q1
   *          The right quaternion
   * @return q0
   */

  public static QuaternionM4F multiplyInPlace(
    final QuaternionM4F q0,
    final QuaternionReadable4FType q1)
  {
    return QuaternionM4F.multiply(q0, q1, q0);
  }

  /**
   * Negate the elements of <code>qa</code>, writing the resulting quaternion
   * to <code>out</code>.
   *
   * @param qa
   *          The input quaternion
   * @param out
   *          The output quaternion
   * @return out
   *
   * @since 5.0.0
   */

  public static QuaternionM4F negate(
    final QuaternionReadable4FType qa,
    final QuaternionM4F out)
  {
    final float x = -qa.getXF();
    final float y = -qa.getYF();
    final float z = -qa.getZF();
    final float w = -qa.getWF();
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Negate the elements of <code>q</code>, modifying the quaternion in-place.
   *
   * @param q
   *          The input quaternion
   * @return q
   *
   * @since 5.0.0
   */

  public static QuaternionM4F negateInPlace(
    final QuaternionM4F q)
  {
    return QuaternionM4F.negate(q, q);
  }

  /**
   * Returns a quaternion with the same orientation as <code>q</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero quaternion iff the input is the zero quaternion.
   *
   * @param out
   *          The output quaternion
   * @param q
   *          The input quaternion
   *
   * @return out
   */

  public static QuaternionM4F normalize(
    final QuaternionReadable4FType q,
    final QuaternionM4F out)
  {
    final double m = QuaternionM4F.magnitudeSquared(q);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return QuaternionM4F.scale(q, reciprocal, out);
    }
    out.x = q.getXF();
    out.y = q.getYF();
    out.z = q.getZF();
    out.w = q.getWF();
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

  public static QuaternionM4F normalizeInPlace(
    final QuaternionM4F q)
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

  public static QuaternionM4F scale(
    final QuaternionReadable4FType q,
    final double r,
    final QuaternionM4F out)
  {
    final double x = q.getXF() * r;
    final double y = q.getYF() * r;
    final double z = q.getZF() * r;
    final double w = q.getWF() * r;
    out.x = (float) x;
    out.y = (float) y;
    out.z = (float) z;
    out.w = (float) w;
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

  public static QuaternionM4F scaleInPlace(
    final QuaternionM4F q,
    final double r)
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

  public static QuaternionM4F subtract(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final QuaternionM4F out)
  {
    final float x = q0.getXF() - q1.getXF();
    final float y = q0.getYF() - q1.getYF();
    final float z = q0.getZF() - q1.getZF();
    final float w = q0.getWF() - q1.getWF();
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

  public static QuaternionM4F subtractInPlace(
    final QuaternionM4F q0,
    final QuaternionReadable4FType q1)
  {
    return QuaternionM4F.subtract(q0, q1, q0);
  }

  private float w;
  private float x;
  private float y;
  private float z;

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
   *
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   * @param in_z
   *          The <code>z</code> value
   * @param in_w
   *          The <code>w</code> value
   */

  public QuaternionM4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a quaternion initialized with the values given in
   * <code>q</code>.
   *
   * @param q
   *          The source quaternion
   */

  public QuaternionM4F(
    final QuaternionReadable4FType q)
  {
    this(q.getXF(), q.getYF(), q.getZF(), q.getWF());
  }

  @Override public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    VectorM2F.copy(in_v, this);
  }

  @Override public void copyFrom3F(
    final VectorReadable3FType in_v)
  {
    VectorM3F.copy(in_v, this);
  }

  @Override public void copyFrom4F(
    final VectorReadable4FType in_v)
  {
    VectorM4F.copy(in_v, this);
  }

  @Override public boolean equals(
    final @Nullable Object obj)
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

  @Override public void set2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3F(
    final float in_x,
    final float in_y,
    final float in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void set4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override public void setWF(
    final float in_w)
  {
    this.w = in_w;
  }

  @Override public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override public void setZF(
    final float in_z)
  {
    this.z = in_z;
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
    final String r = builder.toString();
    assert r != null;
    return r;
  }

}
