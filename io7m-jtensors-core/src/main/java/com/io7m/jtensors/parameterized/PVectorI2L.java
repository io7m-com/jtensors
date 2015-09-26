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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A two-dimensional immutable vector type with integer elements. </p> <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads. </p>
 *
 * @param <T> A phantom type parameter.
 *
 * @since 7.0.0
 */

@Immutable public final class PVectorI2L<T> implements PVectorReadable2LType<T>
{
  private static final PVectorI2L<?> ZERO = new PVectorI2L<Integer>(0L, 0L);
  private final long x;
  private final long y;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0,
   * 0]}.
   */

  public PVectorI2L()
  {
    this.x = 0L;
    this.y = 0L;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public PVectorI2L(
    final long in_x,
    final long in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The input vector
   */

  public PVectorI2L(
    final PVectorReadable2LType<T> in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> absolute(
    final PVectorReadable2LType<T> v)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI2L<T> add(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI2L<T> addScaled(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Calculate the angle between the vectors {@code v0} and {@code v1} in
   * radians.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The angle between the two vectors, in radians.
   */

  public static <T> double angle(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1)
  {
    final double m0 = (double) PVectorI2L.magnitude(v0);
    final double m1 = (double) PVectorI2L.magnitude(v1);
    return Math.acos((double) PVectorI2L.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clamp(
    final PVectorReadable2LType<T> v,
    final long minimum,
    final long maximum)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clampByPVector(
    final PVectorReadable2LType<T> v,
    final PVectorReadable2LType<T> minimum,
    final PVectorReadable2LType<T> maximum)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clampMaximum(
    final PVectorReadable2LType<T> v,
    final long maximum)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clampMaximumByPVector(
    final PVectorReadable2LType<T> v,
    final PVectorReadable2LType<T> maximum)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at least {@code minimum}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clampMinimum(
    final PVectorReadable2LType<T> v,
    final long minimum)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   *
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> clampMinimumByPVector(
    final PVectorReadable2LType<T> v,
    final PVectorReadable2LType<T> minimum)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The distance between the two vectors.
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> long distance(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1)
    throws ArithmeticException
  {
    return PVectorI2L.magnitude(PVectorI2L.subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The scalar product of the two vectors
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> long dotProduct(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    return CheckedMath.add(mx, my);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) → r = v0}</li> <li>{@code
   * interpolateLinear(v0, v1, 1.0, r) → r = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param <T>   A phantom type parameter.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow.
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> interpolateLinear(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1,
    final double alpha)
    throws ArithmeticException
  {
    final PVectorI2L<T> w0 = PVectorI2L.scale(v0, 1.0 - alpha);
    final PVectorI2L<T> w1 = PVectorI2L.scale(v1, alpha);
    return PVectorI2L.add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> long magnitude(
    final PVectorReadable2LType<T> v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt((double) PVectorI2L.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> long magnitudeSquared(
    final PVectorReadable2LType<T> v)
    throws ArithmeticException
  {
    return PVectorI2L.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static <T> PVectorI2L<T> projection(
    final PVectorReadable2LType<T> p,
    final PVectorReadable2LType<T> q)
    throws ArithmeticException
  {
    final long dot = PVectorI2L.dotProduct(p, q);
    final long qms = PVectorI2L.magnitudeSquared(q);
    final long s = dot / qms;
    return PVectorI2L.scale(p, (double) s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v.x * r, v.y * r)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI2L<T> scale(
    final PVectorReadable2LType<T> v,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    return new PVectorI2L<T>(mx, my);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <T> PVectorI2L<T> subtract(
    final PVectorReadable2LType<T> v0,
    final PVectorReadable2LType<T> v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long y = CheckedMath.subtract(v0.getYL(), v1.getYL());
    return new PVectorI2L<T>(x, y);
  }

  /**
   * @param <T> A phantom type parameter.
   *
   * @return The zero vector.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI2L<T> zero()
  {
    return (PVectorI2L<T>) PVectorI2L.ZERO;
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
    final PVectorI2L<?> other = (PVectorI2L<?>) obj;
    if (this.x != other.x) {
      return false;
    }
    return this.y == other.y;
  }

  @Override public long getXL()
  {
    return this.x;
  }

  @Override public long getYL()
  {
    return this.y;
  }

  @Override public int hashCode()
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    return (int) result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorI2L ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

}
