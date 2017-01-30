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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A four-dimensional immutable vector type with integer elements. </p> <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads. </p>
 *
 * @since 5.3.0
 */

@Immutable
public final class VectorI4L implements VectorReadable4LType
{
  /**
   * The zero vector.
   */

  public static final VectorI4L ZERO = new VectorI4L(0L, 0L, 0L, 0L);
  private final long w;
  private final long x;
  private final long y;
  private final long z;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0, 0,
   * 1]}.
   */

  public VectorI4L()
  {
    this.x = 0L;
    this.y = 0L;
    this.z = 0L;
    this.w = 1L;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public VectorI4L(
    final long in_x,
    final long in_y,
    final long in_z,
    final long in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public VectorI4L(
    final VectorReadable4LType in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
    this.z = in_v.getZL();
    this.w = in_v.getWL();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static VectorI4L absolute(
    final VectorReadable4LType v)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    final long z = CheckedMath.absolute(v.getZL());
    final long w = CheckedMath.absolute(v.getWL());
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static VectorI4L add(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    final long z = CheckedMath.add(v0.getZL(), v1.getZL());
    final long w = CheckedMath.add(v0.getWL(), v1.getWL());
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param r  The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static VectorI4L addScaled(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long mz = CheckedMath.multiply(v1.getZL(), r);
    final long mw = CheckedMath.multiply(v1.getWL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    final long z = CheckedMath.add(v0.getZL(), mz);
    final long w = CheckedMath.add(v0.getWL(), mw);
    return new VectorI4L(x, y, z, w);
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
   * least {@code minimum}
   *
   * @since 5.0.0
   */

  public static VectorI4L clamp(
    final VectorReadable4LType v,
    final long minimum,
    final long maximum)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    final long z = Math.min(Math.max(v.getZL(), minimum), maximum);
    final long w = Math.min(Math.max(v.getWL(), minimum), maximum);
    return new VectorI4L(x, y, z, w);
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
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   *
   * @since 5.0.0
   */

  public static VectorI4L clampByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType minimum,
    final VectorReadable4LType maximum)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    final long z =
      Math.min(Math.max(v.getZL(), minimum.getZL()), maximum.getZL());
    final long w =
      Math.min(Math.max(v.getWL(), minimum.getWL()), maximum.getWL());
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *
   * @since 5.0.0
   */

  public static VectorI4L clampMaximum(
    final VectorReadable4LType v,
    final long maximum)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    final long z = Math.min(v.getZL(), maximum);
    final long w = Math.min(v.getWL(), maximum);
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z), min(v.w, maximum.w))}
   *
   * @since 5.0.0
   */

  public static VectorI4L clampMaximumByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType maximum)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    final long z = Math.min(v.getZL(), maximum.getZL());
    final long w = Math.min(v.getWL(), maximum.getWL());
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   *
   * @return A vector with both elements equal to at least {@code minimum}
   *
   * @since 5.0.0
   */

  public static VectorI4L clampMinimum(
    final VectorReadable4LType v,
    final long minimum)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    final long z = Math.max(v.getZL(), minimum);
    final long w = Math.max(v.getWL(), minimum);
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z), max(v.w, minimum.w))}
   *
   * @since 5.0.0
   */

  public static VectorI4L clampMinimumByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType minimum)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    final long z = Math.max(v.getZL(), minimum.getZL());
    final long w = Math.max(v.getWL(), minimum.getWL());
    return new VectorI4L(x, y, z, w);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The distance between the two vectors.
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static long distance(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static long dotProduct(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.multiply(v0.getZL(), v1.getZL());
    final long mw = CheckedMath.multiply(v0.getWL(), v1.getWL());

    long k;
    k = CheckedMath.add(mx, my);
    k = CheckedMath.add(k, mz);
    k = CheckedMath.add(k, mw);
    return k;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow.
   * @since 5.0.0
   */

  public static VectorI4L interpolateLinear(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final double alpha)
    throws ArithmeticException
  {
    final VectorI4L w0 = scale(v0, 1.0 - alpha);
    final VectorI4L w1 = scale(v1, alpha);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static long magnitude(
    final VectorReadable4LType v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt((double) magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static long magnitudeSquared(
    final VectorReadable4LType v)
    throws ArithmeticException
  {
    return dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p The left vector
   * @param q The right vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 5.0.0
   */

  public static VectorI4L projection(
    final VectorReadable4LType p,
    final VectorReadable4LType q)
    throws ArithmeticException
  {
    final long dot = dotProduct(p, q);
    final long qms = magnitudeSquared(q);
    final long s = dot / qms;
    return scale(p, (double) s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v The input vector
   * @param r The scaling value
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, vw * r)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static VectorI4L scale(
    final VectorReadable4LType v,
    final double r)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    final long mz = CheckedMath.multiply(v.getZL(), r);
    final long mw = CheckedMath.multiply(v.getWL(), r);
    return new VectorI4L(mx, my, mz, mw);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static VectorI4L subtract(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    final long x = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long y = CheckedMath.subtract(v0.getYL(), v1.getYL());
    final long z = CheckedMath.subtract(v0.getZL(), v1.getZL());
    final long w = CheckedMath.subtract(v0.getWL(), v1.getWL());
    return new VectorI4L(x, y, z, w);
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
    final VectorI4L other = (VectorI4L) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    return this.w == other.w;
  }

  @Override
  public long getWL()
  {
    return this.w;
  }

  @Override
  public long getXL()
  {
    return this.x;
  }

  @Override
  public long getYL()
  {
    return this.y;
  }

  @Override
  public long getZL()
  {
    return this.z;
  }

  @Override
  public int hashCode()
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    result = (prime * result) + this.w;
    return (int) result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI4L ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

}
