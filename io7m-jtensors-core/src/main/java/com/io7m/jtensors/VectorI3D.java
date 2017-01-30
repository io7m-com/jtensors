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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A three-dimensional immutable vector type with {@code double} elements.
 * </p> <p> Values of this type are immutable and can therefore be safely
 * accessed from multiple threads. </p>
 */

@Immutable
public final class VectorI3D implements VectorReadable3DType
{
  /**
   * The zero vector.
   */

  public static final VectorI3D ZERO = new VectorI3D(0.0, 0.0, 0.0);
  private final double x;
  private final double y;
  private final double z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public VectorI3D()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public VectorI3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The input vector.
   */

  public VectorI3D(
    final VectorReadable3DType in_v)
  {
    this.x = in_v.getXD();
    this.y = in_v.getYD();
    this.z = in_v.getZD();
  }

  /**
   * Calculate the absolute value of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   */

  public static VectorI3D absolute(
    final VectorReadable3DType v)
  {
    return new VectorI3D(
      Math.abs(v.getXD()), Math.abs(v.getYD()), Math.abs(
      v.getZD()));
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   */

  public static VectorI3D add(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    return new VectorI3D(
      v0.getXD() + v1.getXD(),
      v0.getYD() + v1.getYD(),
      v0.getZD() + v1.getZD());
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param r  The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   */

  public static VectorI3D addScaled(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1,
    final double r)
  {
    return add(v0, scale(v1, r));
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input vector
   * @param qb      The right input vector
   *
   * @return {@code true} iff the vectors are almost equal.
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final ContextRelative context,
    final VectorReadable3DType qa,
    final VectorReadable3DType qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, qa.getZD(), qb.getZD());
    return xs && ys && zs;
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}.
   */

  public static VectorI3D clamp(
    final VectorReadable3DType v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    return new VectorI3D(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static VectorI3D clampByVector(
    final VectorReadable3DType v,
    final VectorReadable3DType minimum,
    final VectorReadable3DType maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    return new VectorI3D(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static VectorI3D clampMaximum(
    final VectorReadable3DType v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    return new VectorI3D(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z))}
   */

  public static VectorI3D clampMaximumByVector(
    final VectorReadable3DType v,
    final VectorReadable3DType maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    return new VectorI3D(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static VectorI3D clampMinimum(
    final VectorReadable3DType v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    return new VectorI3D(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z))}
   */

  public static VectorI3D clampMinimumByVector(
    final VectorReadable3DType v,
    final VectorReadable3DType minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    return new VectorI3D(x, y, z);
  }

  /**
   * Calculate the cross product of the vectors {@code v0} and {@code v1}. The
   * result is a vector perpendicular to both vectors.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return A vector perpendicular to both {@code v0} and {@code v1}
   */

  public static VectorI3D crossProduct(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    final double x = (v0.getYD() * v1.getZD()) - (v0.getZD() * v1.getYD());
    final double y = (v0.getZD() * v1.getXD()) - (v0.getXD() * v1.getZD());
    final double z = (v0.getXD() * v1.getYD()) - (v0.getYD() * v1.getXD());
    return new VectorI3D(x, y, z);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The distance between the two vectors
   */

  public static double distance(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    return magnitude(subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The scalar product of the two vectors
   */

  public static double dotProduct(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
    return x + y + z;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0) = v0}</li> <li>{@code
   * interpolateLinear(v0, v1, 1.0) = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   */

  public static VectorI3D interpolateLinear(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1,
    final double alpha)
  {
    final VectorI3D w0 = scale(v0, 1.0 - alpha);
    final VectorI3D w1 = scale(v1, alpha);
    return add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   *
   * @return The magnitude of the input vector
   */

  public static double magnitude(
    final VectorReadable3DType v)
  {
    return Math.sqrt(magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable3DType v)
  {
    return dotProduct(v, v);
  }

  /**
   * Normalize the vector {@code v}, preserving its direction but reducing it to
   * unit length.
   *
   * @param v The input vector
   *
   * @return A vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0}
   */

  public static VectorI3D normalize(
    final VectorReadable3DType v)
  {
    final double m = magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return scale(v, reciprocal);
    }
    return new VectorI3D(v);
  }

  /**
   * <p> Orthonormalize and return the vectors {@code v0} and {@code v1} . </p>
   * <p> See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0 The left vector
   * @param v1 The right vector
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   *
   * @since 5.0.0
   */

  public static Pair<VectorI3D, VectorI3D> orthoNormalize(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    final VectorI3D v0n = normalize(v0);
    final VectorI3D projection =
      scale(v0n, dotProduct(v1, v0n));
    final VectorI3D vr =
      normalize(subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p The left vector
   * @param q The right vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static VectorI3D projection(
    final VectorReadable3DType p,
    final VectorReadable3DType q)
  {
    final double dot = dotProduct(p, q);
    final double qms = magnitudeSquared(q);
    final double s = dot / qms;
    return scale(p, s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v The input vector
   * @param r The scaling value
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   */

  public static VectorI3D scale(
    final VectorReadable3DType v,
    final double r)
  {
    return new VectorI3D(v.getXD() * r, v.getYD() * r, v.getZD() * r);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   */

  public static VectorI3D subtract(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1)
  {
    return new VectorI3D(
      v0.getXD() - v1.getXD(),
      v0.getYD() - v1.getYD(),
      v0.getZD() - v1.getZD());
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
    final VectorI3D other = (VectorI3D) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y) && Double.doubleToLongBits(
      this.z) == Double.doubleToLongBits(other.z);
  }

  @Override
  public double getXD()
  {
    return this.x;
  }

  @Override
  public double getYD()
  {
    return this.y;
  }

  @Override
  public double getZD()
  {
    return this.z;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
