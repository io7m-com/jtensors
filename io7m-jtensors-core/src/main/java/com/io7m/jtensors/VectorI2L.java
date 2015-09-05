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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A two-dimensional immutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 *
 * @since 5.3.0
 */

public final class VectorI2L implements VectorReadable2LType
{
  /**
   * The zero vector.
   */

  public static final VectorI2L ZERO = new VectorI2L(0L, 0L);

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI2L absolute(
    final VectorReadable2LType v)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    return new VectorI2L(x, y);
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
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI2L add(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    return new VectorI2L(x, y);
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI2L addScaled(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    return new VectorI2L(x, y);
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
   */

  public static double angle(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
  {
    final double m0 = (double) VectorI2L.magnitude(v0);
    final double m1 = (double) VectorI2L.magnitude(v1);
    return Math.acos((double) VectorI2L.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}
   */

  public static VectorI2L clamp(
    final VectorReadable2LType v,
    final long minimum,
    final long maximum)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    return new VectorI2L(x, y);
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
   * @since 5.0.0
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))}
   */

  public static VectorI2L clampByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType minimum,
    final VectorReadable2LType maximum)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    return new VectorI2L(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static VectorI2L clampMaximum(
    final VectorReadable2LType v,
    final long maximum)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    return new VectorI2L(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 5.0.0
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static VectorI2L clampMaximumByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType maximum)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    return new VectorI2L(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   */

  public static VectorI2L clampMinimum(
    final VectorReadable2LType v,
    final long minimum)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    return new VectorI2L(x, y);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @since 5.0.0
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static VectorI2L clampMinimumByVector(
    final VectorReadable2LType v,
    final VectorReadable2LType minimum)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    return new VectorI2L(x, y);
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
   * @return The distance between the two vectors.
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static long distance(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    return VectorI2L.magnitude(VectorI2L.subtract(v0, v1));
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
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static long dotProduct(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    return CheckedMath.add(mx, my);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li>
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
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   */

  public static VectorI2L interpolateLinear(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1,
    final double alpha)
    throws ArithmeticException
  {
    final VectorI2L w0 = VectorI2L.scale(v0, 1.0 - alpha);
    final VectorI2L w1 = VectorI2L.scale(v1, alpha);
    return VectorI2L.add(w0, w1);
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
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static long magnitude(
    final VectorReadable2LType v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt((double) VectorI2L.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static long magnitudeSquared(
    final VectorReadable2LType v)
    throws ArithmeticException
  {
    return VectorI2L.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}.
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   *
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static VectorI2L projection(
    final VectorReadable2LType p,
    final VectorReadable2LType q)
    throws ArithmeticException
  {
    final long dot = VectorI2L.dotProduct(p, q);
    final long qms = VectorI2L.magnitudeSquared(q);
    final long s = dot / qms;
    return VectorI2L.scale(p, (double) s);
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI2L scale(
    final VectorReadable2LType v,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    return new VectorI2L(mx, my);
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI2L subtract(
    final VectorReadable2LType v0,
    final VectorReadable2LType v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long y = CheckedMath.subtract(v0.getYL(), v1.getYL());
    return new VectorI2L(x, y);
  }

  private final long x;
  private final long y;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0]}.
   */

  public VectorI2L()
  {
    this.x = 0L;
    this.y = 0L;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   */

  public VectorI2L(
    final long in_x,
    final long in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The input vector
   */

  public VectorI2L(
    final VectorReadable2LType in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
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
    final VectorI2L other = (VectorI2L) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
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
    builder.append("[VectorI2L ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

}
