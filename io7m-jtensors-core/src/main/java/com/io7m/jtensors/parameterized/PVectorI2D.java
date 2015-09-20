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
 * A two-dimensional immutable vector type with double precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

@Immutable public final class PVectorI2D<T> implements PVectorReadable2DType<T>
{
  private final static PVectorI2D<?> ZERO = new PVectorI2D<Float>(0.0, 0.0);

  /**
   * Calculate the absolute value of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> absolute(
    final PVectorReadable2DType<T> v)
  {
    return new PVectorI2D<T>(Math.abs(v.getXD()), Math.abs(v.getYD()));
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> add(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    return new PVectorI2D<T>(v0.getXD() + v1.getXD(), v0.getYD() + v1.getYD());
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> addScaled(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double r)
  {
    return PVectorI2D.add(v0, PVectorI2D.scale(v1, r));
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
    final PVectorReadable2DType<T> qa,
    final PVectorReadable2DType<T> qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    return xs && ys;
  }

  /**
   * Calculate the angle between the vectors {@code v0} and
   * {@code v1} in radians.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The angle between the two vectors, in radians.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double angle(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final double m0 = PVectorI2D.magnitude(v0);
    final double m1 = PVectorI2D.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, PVectorI2D.dotProduct(v0, v1)), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
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

  public static <T> PVectorI2D<T> clamp(
    final PVectorReadable2DType<T> v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    return new PVectorI2D<T>(x, y);
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
   *         minimum.y), maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> clampByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum,
    final PVectorReadable2DType<T> maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    return new PVectorI2D<T>(x, y);
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

  public static <T> PVectorI2D<T> clampMaximum(
    final PVectorReadable2DType<T> v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    return new PVectorI2D<T>(x, y);
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> clampMaximumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    return new PVectorI2D<T>(x, y);
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

  public static <T> PVectorI2D<T> clampMinimum(
    final PVectorReadable2DType<T> v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    return new PVectorI2D<T>(x, y);
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

  public static <T> PVectorI2D<T> clampMinimumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    return new PVectorI2D<T>(x, y);
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
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    return PVectorI2D.magnitude(PVectorI2D.subtract(v0, v1));
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
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    return x + y;
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
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param alpha
   *          The interpolation value, between {@code 0.0} and
   *          {@code 1.0}
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> interpolateLinear(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double alpha)
  {
    final PVectorI2D<T> w0 = PVectorI2D.scale(v0, 1.0 - alpha);
    final PVectorI2D<T> w1 = PVectorI2D.scale(v1, alpha);
    return PVectorI2D.add(w0, w1);
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
    final PVectorReadable2DType<T> v)
  {
    return Math.sqrt(PVectorI2D.magnitudeSquared(v));
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
    final PVectorReadable2DType<T> v)
  {
    return PVectorI2D.dotProduct(v, v);
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

  public static <T> PVectorI2D<T> normalize(
    final PVectorReadable2DType<T> v)
  {
    final double m = PVectorI2D.magnitudeSquared(v);
    if (m > 0.0) {
      final double sq = Math.sqrt(m);
      final double r = 1.0 / sq;
      return PVectorI2D.scale(v, r);
    }
    return new PVectorI2D<T>(v);
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
   *
   * @since 7.0.0
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorI2D<T>, PVectorI2D<T>> orthoNormalize(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final PVectorI2D<T> v0n = PVectorI2D.normalize(v0);
    final PVectorI2D<T> projection =
      PVectorI2D.scale(v0n, PVectorI2D.dotProduct(v1, v0n));
    final PVectorI2D<T> vr =
      PVectorI2D.normalize(PVectorI2D.subtract(v1, projection));
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

  public static <T> PVectorI2D<T> projection(
    final PVectorReadable2DType<T> p,
    final PVectorReadable2DType<T> q)
  {
    final double dot = PVectorI2D.dotProduct(p, q);
    final double qms = PVectorI2D.magnitudeSquared(q);
    final double s = dot / qms;
    return PVectorI2D.scale(p, s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v.x * r, v.y * r)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> scale(
    final PVectorReadable2DType<T> v,
    final double r)
  {
    return new PVectorI2D<T>(v.getXD() * r, v.getYD() * r);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI2D<T> subtract(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    return new PVectorI2D<T>(v0.getXD() - v1.getXD(), v0.getYD() - v1.getYD());
  }

  /**
   * @return The zero vector.
   *
   * @param <T>
   *          A phantom type parameter.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI2D<T> zero()
  {
    return (PVectorI2D<T>) PVectorI2D.ZERO;
  }

  /**
   * The {@code x} value.
   */

  private final double x;

  /**
   * The {@code y} value.
   */

  private final double y;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public PVectorI2D()
  {
    this.x = 0.0;
    this.y = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   */

  public PVectorI2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code v}.
   *
   * @param in_v
   *          The input vector
   */

  public PVectorI2D(
    final PVectorReadable2DType<T> in_v)
  {
    NullCheck.notNull(in_v, "Input vector");
    this.x = in_v.getXD();
    this.y = in_v.getYD();
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
    @SuppressWarnings("unchecked") final PVectorI2D<T> other =
      (PVectorI2D<T>) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public double getYD()
  {
    return this.y;
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
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorI2D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
