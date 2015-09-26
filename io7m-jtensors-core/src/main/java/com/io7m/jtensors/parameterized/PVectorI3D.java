/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p>
 * A three-dimensional immutable vector type with double precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

@Immutable public final class PVectorI3D<T> implements PVectorReadable3DType<T>
{
  /**
   * The zero vector.
   */

  public static final PVectorI3D<?> ZERO = new PVectorI3D<Double>(
                                           0.0,
                                           0.0,
                                           0.0);

  /**
   * Calculate the absolute value of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> absolute(
    final PVectorReadable3DType<T> v)
  {
    return new PVectorI3D<T>(
      Math.abs(v.getXD()),
      Math.abs(v.getYD()),
      Math.abs(v.getZD()));
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> add(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    return new PVectorI3D<T>(
      v0.getXD() + v1.getXD(),
      v0.getYD() + v1.getYD(),
      v0.getZD() + v1.getZD());
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z *
   *         r))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> addScaled(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final double r)
  {
    return PVectorI3D.add(v0, PVectorI3D.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb}
   * are equal to within the degree of error given in {@code context}.
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   *
   * @param context
   *          The equality context
   * @param qa
   *          The left input vector
   * @param qb
   *          The right input vector
   * @since 7.0.0
   * @return {@code true} iff the vectors are almost equal.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> boolean almostEqual(
    final ContextRelative context,
    final PVectorReadable3DType<T> qa,
    final PVectorReadable3DType<T> qb)
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
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum
   * .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clamp(
    final PVectorReadable3DType<T> v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   *         minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clampByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> minimum,
    final PVectorReadable3DType<T> maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clampMaximum(
    final PVectorReadable3DType<T> v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   *         maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clampMaximumByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum
   * .. Infinity]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clampMinimum(
    final PVectorReadable3DType<T> v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   *         minimum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> clampMinimumByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Calculate the cross product of the vectors {@code v0} and
   * {@code v1}. The result is a vector perpendicular to both vectors.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return A vector perpendicular to both {@code v0} and
   *         {@code v1}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> crossProduct(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    final double x = (v0.getYD() * v1.getZD()) - (v0.getZD() * v1.getYD());
    final double y = (v0.getZD() * v1.getXD()) - (v0.getXD() * v1.getZD());
    final double z = (v0.getXD() * v1.getYD()) - (v0.getYD() * v1.getXD());
    return new PVectorI3D<T>(x, y, z);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The distance between the two vectors
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double distance(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    return PVectorI3D.magnitude(PVectorI3D.subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The scalar product of the two vectors
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double dotProduct(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
    return x + y + z;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0) = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0) = v1}</li>
   * </ul>
   *
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between {@code 0.0} and
   *          {@code 1.0}.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> interpolateLinear(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final double alpha)
  {
    final PVectorI3D<T> w0 = PVectorI3D.scale(v0, 1.0 - alpha);
    final PVectorI3D<T> w1 = PVectorI3D.scale(v1, alpha);
    return PVectorI3D.add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v
   *          The input vector
   *
   * @return The magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitude(
    final PVectorReadable3DType<T> v)
  {
    return Math.sqrt(PVectorI3D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable3DType<T> v)
  {
    return PVectorI3D.dotProduct(v, v);
  }

  /**
   * Normalize the vector {@code v}, preserving its direction but
   * reducing it to unit length.
   *
   * @param v
   *          The input vector
   *
   * @return A vector with the same orientation as {@code v} but with
   *         magnitude equal to {@code 1.0}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> normalize(
    final PVectorReadable3DType<T> v)
  {
    final double m = PVectorI3D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorI3D.scale(v, reciprocal);
    }
    return new PVectorI3D<T>(v);
  }

  /**
   * <p>
   * Orthonormalize and return the vectors {@code v0} and {@code v1}
   * .
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @return A pair {@code (v0, v1)}, orthonormalized.
   * @since 7.0.0
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorI3D<T>, PVectorI3D<T>> orthoNormalize(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    final PVectorI3D<T> v0n = PVectorI3D.normalize(v0);
    final PVectorI3D<T> projection =
      PVectorI3D.scale(v0n, PVectorI3D.dotProduct(v1, v0n));
    final PVectorI3D<T> vr =
      PVectorI3D.normalize(PVectorI3D.subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}.
   *
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> projection(
    final PVectorReadable3DType<T> p,
    final PVectorReadable3DType<T> q)
  {
    final double dot = PVectorI3D.dotProduct(p, q);
    final double qms = PVectorI3D.magnitudeSquared(q);
    final double s = dot / qms;
    return PVectorI3D.scale(p, s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> scale(
    final PVectorReadable3DType<T> v,
    final double r)
  {
    return new PVectorI3D<T>(v.getXD() * r, v.getYD() * r, v.getZD() * r);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3D<T> subtract(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    return new PVectorI3D<T>(
      v0.getXD() - v1.getXD(),
      v0.getYD() - v1.getYD(),
      v0.getZD() - v1.getZD());
  }

  /**
   * @return The zero vector.
   *
   * @param <T>
   *          A phantom type parameter.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI3D<T> zero()
  {
    return (PVectorI3D<T>) PVectorI3D.ZERO;
  }

  private final double x;
  private final double y;
  private final double z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public PVectorI3D()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   * @param in_z
   *          The {@code z} value
   */

  public PVectorI3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The input vector.
   */

  public PVectorI3D(
    final PVectorReadable3DType<T> in_v)
  {
    this.x = in_v.getXD();
    this.y = in_v.getYD();
    this.z = in_v.getZD();
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
    final PVectorI3D<?> other = (PVectorI3D<?>) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    return Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z);
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
    builder.append("[PVectorI3D ");
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
