/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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
import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;
import com.io7m.junreachable.UnreachableCodeException;
import net.jcip.annotations.Immutable;

/**
 * A four-dimensional immutable quaternion type with {@code float} elements.
 *
 * <p> Values of this type are immutable and can therefore be safely accessed
 * from multiple threads. </p>
 */

@EqualityStructural
@Immutable
public final class QuaternionI4F implements QuaternionReadable4FType
{
  /**
   * The "identity" quaternion, [0.0 0.0 0.0 1.0]
   */

  public static final QuaternionI4F IDENTITY;

  static {
    IDENTITY = new QuaternionI4F(0.0f, 0.0f, 0.0f, 1.0f);
  }

  private final float w;
  private final float x;
  private final float y;
  private final float z;

  /**
   * Default constructor, initializing the quaternion with values {@code [0.0,
   * 0.0, 0.0, 1.0]}
   */

  public QuaternionI4F()
  {
    this(0.0f, 0.0f, 0.0f, 1.0f);
  }

  /**
   * Construct a quaternion initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public QuaternionI4F(
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
   * Construct a quaternion initialized with the values contained in {@code q}.
   *
   * @param q The source quaternion
   */

  public QuaternionI4F(
    final QuaternionReadable4FType q)
  {
    this(q.getXF(), q.getYF(), q.getZF(), q.getWF());
  }

  /**
   * Calculate the element-wise sum of the quaternions {@code q0} and {@code
   * q1}.
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return {@code (q0.x + q1.x, q0.y + q1.y, q0.z + q1.z, q0.w + q1.w)}
   */

  public static QuaternionI4F add(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1)
  {
    final float x = q0.getXF() + q1.getXF();
    final float y = q0.getYF() + q1.getYF();
    final float z = q0.getZF() + q1.getZF();
    final float w = q0.getWF() + q1.getWF();
    return new QuaternionI4F(x, y, z, w);
  }

  /**
   * Determine whether or not the quaternions {@code qa} and {@code qb} are
   * equal to within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input quaternion
   * @param qb      The right input quaternion
   *
   * @return {@code true} if the quaternions are almost equal
   *
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
   * @since 5.0.0
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
   * Calculate the conjugate of the input quaternion {@code q}.
   *
   * @param q The input quaternion
   *
   * @return The conjugate of the input quaternion
   */

  public static QuaternionI4F conjugate(
    final QuaternionReadable4FType q)
  {
    final float x = -q.getXF();
    final float y = -q.getYF();
    final float z = -q.getZF();
    final float w = q.getWF();
    return new QuaternionI4F(x, y, z, w);
  }

  /**
   * Calculate the scalar product of the quaternions {@code q0} and {@code q1}.
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return The scalar product of the two quaternions
   */

  public static float dotProduct(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1)
  {
    final float x = q0.getXF() * q1.getXF();
    final float y = q0.getYF() * q1.getYF();
    final float z = q0.getZF() * q1.getZF();
    final float w = q0.getWF() * q1.getWF();
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between {@code q0} and {@code q1} by the amount {@code
   * alpha}, such that:
   *
   * <ul> <li>{@code interpolateLinear(q0, q1, 0.0) = q0}</li> <li>{@code
   * interpolateLinear(q0, q1, 1.0) = q1}</li> </ul>
   *
   * @param q0    The left input quaternion
   * @param q1    The right input quaternion
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   *
   * @return {@code (1 - alpha) * q0 + alpha * q1}
   */

  public static QuaternionI4F interpolateLinear(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final float alpha)
  {
    final QuaternionI4F w0 = scale(q0, 1.0f - alpha);
    final QuaternionI4F w1 = scale(q1, alpha);
    return add(w0, w1);
  }

  /**
   * Interpolate between {@code q0} and {@code q1}, using <i>spherical linear
   * interpolation</i>, by the amount {@code alpha}, such that:
   *
   * <ul> <li>{@code interpolateSphericalLinear(q0, q1, 0.0) = normalize(q0)}</li>
   * <li>{@code interpolateSphericalLinear(q0, q1, 1.0) = normalize(q1)}</li> </ul>
   *
   * <p>Note that unlike simple linear interpolation, this function is guaranteed
   * to return a normalized quaternion.</p>
   *
   * @param q0    The left input quaternion
   * @param q1    The right input quaternion
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   *
   * @return A spherical-linearly interpolated quaternion between {@code q0} and
   * {@code q1}
   */

  public static QuaternionI4F interpolateSphericalLinear(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1,
    final float alpha)
  {
    final QuaternionI4F q0n = normalize(q0);
    QuaternionI4F q1n = normalize(q1);

    /*
     * Calculate the dot product to determine if the quaternions are nearly
     * codirectional. If they are, fall back to simple linear interpolation.
     */

    float dot = dotProduct(q0n, q1n);
    final float DOT_THRESHOLD = 0.9995f;
    if (dot > DOT_THRESHOLD) {
      return normalize(interpolateLinear(q0n, q1n, alpha));
    }

    /*
     * If the dot product is negative, the quaternions are separated by more
     * than 180° and a spherical linear interpolation wouldn't result in the
     * shortest path. By negating one quaternion, the shortest path is obtained.
     */

    if (dot < 0.0f) {
      q1n = negate(q1n);
      dot = -dot;
    }

    dot = Math.max(-1.0f, Math.min(dot, 1.0f));
    final double theta_0 = StrictMath.acos((double) dot);
    final double theta = theta_0 * (double) alpha;

    final QuaternionI4F q2 = normalize(subtract(q1n, scale(q0n, dot)));
    final QuaternionI4F r0 = scale(q0n, (float) StrictMath.cos(theta));
    final QuaternionI4F r1 = scale(q2, (float) StrictMath.sin(theta));
    return add(r0, r1);
  }

  /**
   * Return {@code true} iff {@code qa} is the negation of {@code qb}.
   *
   * <p> Each element is compared with {@link AlmostEqualFloat#almostEqual
   * (ContextRelative, float, float)}. </p>
   *
   * @param context The equality context
   * @param qa      The left quaternion
   * @param qb      The right quaternion
   *
   * @return {@code true} iff {@code qa} is the negation of {@code qb}
   *
   * @since 5.0.0
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
   * at {@code target} assuming the viewer is at {@code origin}, using {@code
   * up} as the "up" vector.
   *
   * <p> The function uses storage preallocated in {@code context} to avoid any
   * new allocations. </p>
   *
   * @param context Preallocated storage
   * @param origin  The origin point
   * @param target  The target point
   * @param up      The up vector
   *
   * @return A quaternion looking at the target
   *
   * @since 5.0.0
   */

  public static QuaternionI4F lookAtWithContext(
    final ContextQI4F context,
    final VectorReadable3FType origin,
    final VectorReadable3FType target,
    final VectorReadable3FType up)
  {
    final Matrix3x3FType m = context.m3a;
    final VectorM3F t = context.v3a;
    final MatrixM3x3F.ContextMM3F mc = context.m_context;

    MatrixM3x3F.lookAt(mc, origin, target, up, m, t);
    return makeFromRotationMatrix3x3(m);
  }

  /**
   * Calculate the magnitude of the quaternion {@code q}.
   *
   * Correspondingly, {@code magnitude(normalize(q)) == 1.0}.
   *
   * @param q The input quaternion
   *
   * @return The magnitude of the input quaternion
   */

  public static float magnitude(
    final QuaternionReadable4FType q)
  {
    return (float) Math.sqrt((double) magnitudeSquared(q));
  }

  /**
   * Calculate the squared magnitude of the quaternion {@code q}.
   *
   * @param q The input quaternion
   *
   * @return The squared magnitude of the input quaternion
   */

  public static float magnitudeSquared(
    final QuaternionReadable4FType q)
  {
    return dotProduct(q, q);
  }

  /**
   * Produce a quaternion that represents a rotation of {@code angle} degrees
   * around the axis specified by {@code axis}. {@code axis} is assumed to be of
   * unit length.
   *
   * @param axis  The normalized vector representing the axis
   * @param angle The angle to rotate, in radians
   *
   * @return A quaternion representing the rotation
   *
   * @see VectorI3F#normalize(VectorReadable3FType)
   * @see VectorI4F#normalize(VectorReadable4FType)
   * @see VectorM3F#normalize(VectorReadable3FType, VectorWritable3FType)
   * @see VectorM4F#normalize(VectorReadable4FType, VectorWritable4FType)
   */

  public static QuaternionI4F makeFromAxisAngle(
    final VectorReadable3FType axis,
    final double angle)
  {
    final double angle_r = angle * 0.5;
    final double sa = Math.sin(angle_r);
    final double x = (double) axis.getXF() * sa;
    final double y = (double) axis.getYF() * sa;
    final double z = (double) axis.getZF() * sa;
    final double w = Math.cos(angle_r);
    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix {@code m}.
   *
   * @param m The rotation matrix
   *
   * @return A quaternion representing the rotation matrix
   *
   * @since 5.0.0
   */

  public static QuaternionI4F makeFromRotationMatrix3x3(
    final MatrixReadable3x3FType m)
  {
    final double m00 = (double) m.getR0C0F();
    final double m01 = (double) m.getR0C1F();
    final double m02 = (double) m.getR0C2F();
    final double m10 = (double) m.getR1C0F();
    final double m11 = (double) m.getR1C1F();
    final double m12 = (double) m.getR1C2F();
    final double m20 = (double) m.getR2C0F();
    final double m21 = (double) m.getR2C1F();
    final double m22 = (double) m.getR2C2F();

    final double trace = MatrixM3x3F.trace(m);

    final double x;
    final double y;
    final double z;
    final double w;

    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2.0;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2.0;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2.0;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a quaternion equivalent to the rotation matrix {@code m}, writing
   * the result to {@code out}.
   *
   * @param m The rotation matrix
   *
   * @return A quaternion representing the rotation matrix
   *
   * @since 5.0.0
   */

  public static QuaternionI4F makeFromRotationMatrix4x4(
    final MatrixReadable4x4FType m)
  {
    final double m00 = (double) m.getR0C0F();
    final double m01 = (double) m.getR0C1F();
    final double m02 = (double) m.getR0C2F();
    final double m10 = (double) m.getR1C0F();
    final double m11 = (double) m.getR1C1F();
    final double m12 = (double) m.getR1C2F();
    final double m20 = (double) m.getR2C0F();
    final double m21 = (double) m.getR2C1F();
    final double m22 = (double) m.getR2C2F();

    /*
     * Explicitly ignore the bottom right element of the matrix, as this
     * affects the magnitude of the created quaternion.
     */

    final double trace = m00 + m11 + m22;

    final double x;
    final double y;
    final double z;
    final double w;

    if (trace > 0.0) {
      // S = 4 * qw
      final double s = Math.sqrt(trace + 1.0) * 2.0;
      w = 0.25 * s;
      x = (m21 - m12) / s;
      y = (m02 - m20) / s;
      z = (m10 - m01) / s;
    } else if ((m00 > m11) && (m00 > m22)) {
      // S = 4 * qx
      final double s = Math.sqrt((1.0 + m00) - m11 - m22) * 2.0;
      w = (m21 - m12) / s;
      x = 0.25 * s;
      y = (m01 + m10) / s;
      z = (m02 + m20) / s;
    } else if (m11 > m22) {
      // S = 4 * qy
      final double s = Math.sqrt((1.0 + m11) - m00 - m22) * 2.0;
      w = (m02 - m20) / s;
      x = (m01 + m10) / s;
      y = 0.25 * s;
      z = (m12 + m21) / s;
    } else {
      // S = 4 * qz
      final double s = Math.sqrt((1.0 + m22) - m00 - m11) * 2.0;
      w = (m10 - m01) / s;
      x = (m02 + m20) / s;
      y = (m12 + m21) / s;
      z = 0.25 * s;
    }

    return new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
  }

  /**
   * Produce a rotation matrix from the quaternion {@code q}, saving the result
   * to {@code m}.
   *
   * @param q   The input quaternion
   * @param m   The output matrix
   * @param <M> The precise type of writable matrix.
   *
   * @return {@code m}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable3x3FType> M makeRotationMatrix3x3(
    final QuaternionReadable4FType q,
    final M m)
  {
    final double xx = (double) (q.getXF() * q.getXF());
    final double xy = (double) (q.getXF() * q.getYF());
    final double xz = (double) (q.getXF() * q.getZF());
    final double yy = (double) (q.getYF() * q.getYF());
    final double yz = (double) (q.getYF() * q.getZF());
    final double zz = (double) (q.getZF() * q.getZF());
    final double wx = (double) (q.getWF() * q.getXF());
    final double wy = (double) (q.getWF() * q.getYF());
    final double wz = (double) (q.getWF() * q.getZF());

    final double r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final double r0c1 = (2.0 * xy) - (2.0 * wz);
    final double r0c2 = (2.0 * xz) + (2.0 * wy);

    final double r1c0 = (2.0 * xy) + (2.0 * wz);
    final double r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final double r1c2 = (2.0 * yz) - (2.0 * wx);

    final double r2c0 = (2.0 * xz) - (2.0 * wy);
    final double r2c1 = (2.0 * yz) + (2.0 * wx);
    final double r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);

    m.setR0C0F((float) r0c0);
    m.setR0C1F((float) r0c1);
    m.setR0C2F((float) r0c2);

    m.setR1C0F((float) r1c0);
    m.setR1C1F((float) r1c1);
    m.setR1C2F((float) r1c2);

    m.setR2C0F((float) r2c0);
    m.setR2C1F((float) r2c1);
    m.setR2C2F((float) r2c2);

    return m;
  }

  /**
   * Produce a rotation matrix from the quaternion {@code q}, saving the result
   * to {@code m}.
   *
   * @param q   The input quaternion
   * @param m   The output matrix
   * @param <M> The precise type of matrix
   *
   * @return {@code m}
   *
   * @since 5.0.0
   */

  public static <M extends MatrixWritable4x4FType> M makeRotationMatrix4x4(
    final QuaternionReadable4FType q,
    final M m)
  {
    final double xx = (double) (q.getXF() * q.getXF());
    final double xy = (double) (q.getXF() * q.getYF());
    final double xz = (double) (q.getXF() * q.getZF());
    final double yy = (double) (q.getYF() * q.getYF());
    final double yz = (double) (q.getYF() * q.getZF());
    final double zz = (double) (q.getZF() * q.getZF());
    final double wx = (double) (q.getWF() * q.getXF());
    final double wy = (double) (q.getWF() * q.getYF());
    final double wz = (double) (q.getWF() * q.getZF());

    final double r0c0 = 1.0 - (2.0 * yy) - (2.0 * zz);
    final double r0c1 = (2.0 * xy) - (2.0 * wz);
    final double r0c2 = (2.0 * xz) + (2.0 * wy);
    final double r0c3 = 0.0;

    final double r1c0 = (2.0 * xy) + (2.0 * wz);
    final double r1c1 = 1.0 - (2.0 * xx) - (2.0 * zz);
    final double r1c2 = (2.0 * yz) - (2.0 * wx);
    final double r1c3 = 0.0;

    final double r2c0 = (2.0 * xz) - (2.0 * wy);
    final double r2c1 = (2.0 * yz) + (2.0 * wx);
    final double r2c2 = 1.0 - (2.0 * xx) - (2.0 * yy);
    final double r2c3 = 0.0;

    final double r3c0 = 0.0;
    final double r3c1 = 0.0;
    final double r3c2 = 0.0;
    final double r3c3 = 1.0;

    m.setR0C0F((float) r0c0);
    m.setR0C1F((float) r0c1);
    m.setR0C2F((float) r0c2);
    m.setR0C3F((float) r0c3);

    m.setR1C0F((float) r1c0);
    m.setR1C1F((float) r1c1);
    m.setR1C2F((float) r1c2);
    m.setR1C3F((float) r1c3);

    m.setR2C0F((float) r2c0);
    m.setR2C1F((float) r2c1);
    m.setR2C2F((float) r2c2);
    m.setR2C3F((float) r2c3);

    m.setR3C0F((float) r3c0);
    m.setR3C1F((float) r3c1);
    m.setR3C2F((float) r3c2);
    m.setR3C3F((float) r3c3);

    return m;
  }

  /**
   * Multiply the quaternion {@code q0} by the quaternion {@code q1} .
   *
   * <p> Note that this operation is not commutative. </p>
   *
   * <p> The function is most often used to concatenate quaternions to combine
   * rotations. As an example, assuming that: </p>
   *
   * <ul> <li>{@code qx} represents some rotation around the X axis</li>
   * <li>{@code qy} represents some rotation around the Y axis</li> <li>{@code
   * qz} represents some rotation around the Z axis</li> </ul>
   *
   * <p> The following code produces a quaternion {@code qr1} that represents a
   * rotation around the X axis, followed by a rotation around the Y axis,
   * followed by a rotation around the Z axis: </p>
   *
   * {@code qr0 = QuaternionI4F.multiply(qy, qx); qr1 = QuaternionI4F
   * .multiply(qz, qy); }
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return The multiplication of the input quaternions
   */

  public static QuaternionI4F multiply(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1)
  {
    final float q0_w = q0.getWF();
    final float q1_x = q1.getXF();
    final float q0_x = q0.getXF();
    final float q1_w = q1.getWF();
    final float q1_z = q1.getZF();
    final float q0_z = q0.getZF();
    final float q1_y = q1.getYF();
    final float q0_y = q0.getYF();

    final float rx =
      ((q0_w * q1_x) + (q0_x * q1_w) + (q0_y * q1_z)) - (q0_z * q1_y);
    final float ry =
      ((q0_w * q1_y) - (q0_x * q1_z)) + (q0_y * q1_w) + (q0_z * q1_x);
    final float rz =
      (((q0_w * q1_z) + (q0_x * q1_y)) - (q0_y * q1_x)) + (q0_z * q1_w);
    final float rw =
      (q0_w * q1_w) - (q0_x * q1_x) - (q0_y * q1_y) - (q0_z * q1_z);

    return new QuaternionI4F(rx, ry, rz, rw);
  }

  /**
   * Negate the elements of {@code q}.
   *
   * @param q The source quaternion
   *
   * @return The negation of {@code q}
   *
   * @since 5.0.0
   */

  public static QuaternionI4F negate(
    final QuaternionReadable4FType q)
  {
    return new QuaternionI4F(-q.getXF(), -q.getYF(), -q.getZF(), -q.getWF());
  }

  /**
   * Normalize the quaternion {@code q}, preserqing its direction but reducing
   * it to unit length.
   *
   * @param q The input quaternion
   *
   * @return A quaternion with the same orientation as {@code q} but with
   * magnitude equal to {@code 1.0}
   */

  public static QuaternionI4F normalize(
    final QuaternionReadable4FType q)
  {
    final float m = magnitudeSquared(q);
    if (m > 0.0F) {
      final float reciprocal = (float) (1.0 / Math.sqrt((double) m));
      return scale(q, reciprocal);
    }
    return new QuaternionI4F(q);
  }

  /**
   * Scale the quaternion {@code q} by the scalar {@code r}.
   *
   * @param q The input quaternion
   * @param r The scaling value
   *
   * @return {@code (q.x * r, q.y * r, q.z * r, q.w * r)}
   */

  public static QuaternionI4F scale(
    final QuaternionReadable4FType q,
    final float r)
  {
    return new QuaternionI4F(
      q.getXF() * r, q.getYF() * r, q.getZF() * r, q.getWF() * r);
  }

  /**
   * Subtract the quaternion {@code q0} from the quaternion {@code q1}.
   *
   * @param q0 The left input quaternion
   * @param q1 The right input quaternion
   *
   * @return {@code (q0.x - q1.x, q0.y - q1.y, q0.z - q1.z)}
   */

  public static QuaternionI4F subtract(
    final QuaternionReadable4FType q0,
    final QuaternionReadable4FType q1)
  {
    final float x = q0.getXF() - q1.getXF();
    final float y = q0.getYF() - q1.getYF();
    final float z = q0.getZF() - q1.getZF();
    final float w = q0.getWF() - q1.getWF();
    return new QuaternionI4F(x, y, z, w);
  }

  @Override
  public boolean equals(
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
    final QuaternionI4F other = (QuaternionI4F) obj;
    return Float.floatToIntBits(this.w) == Float.floatToIntBits(other.w)
      && Float.floatToIntBits(this.x) == Float.floatToIntBits(other.x)
      && Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y)
      && Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
  }

  @Override
  public float getWF()
  {
    return this.w;
  }

  @Override
  public float getXF()
  {
    return this.x;
  }

  @Override
  public float getYF()
  {
    return this.y;
  }

  @Override
  public float getZF()
  {
    return this.z;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.w);
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(64);
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

  /**
   * The ContextQM4F type contains the minimum storage required for all of the
   * functions of the {@code QuaternionM4F} class.
   *
   * <p> The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p> The user should allocate one {@code ContextQM4F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextQM4F} value will not generate garbage. </p>
   *
   * @since 5.0.0
   */

  public static class ContextQI4F
  {
    private final MatrixM3x3F.ContextMM3F m_context =
      new MatrixM3x3F.ContextMM3F();
    private final Matrix3x3FType m3a =
      MatrixHeapArrayM3x3F.newMatrix();
    private final VectorM3F v3a =
      new VectorM3F();

    /**
     * Construct a new context.
     */

    public ContextQI4F()
    {

    }
  }

}
