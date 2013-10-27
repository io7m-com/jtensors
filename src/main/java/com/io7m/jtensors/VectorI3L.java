/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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
import javax.annotation.concurrent.Immutable;

import com.io7m.jaux.CheckedMath;

/**
 * <p>
 * A three-dimensional immutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 * 
 * @since 5.3.0
 */

@Immutable public class VectorI3L implements VectorReadable3L
{
  /**
   * The zero vector.
   */

  public static final @Nonnull VectorI3L ZERO = new VectorI3L(0, 0, 0);

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   * 
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L absolute(
    final @Nonnull VectorReadable3L v)
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    final long z = CheckedMath.absolute(v.getZL());
    return new VectorI3L(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L add(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1)
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    final long z = CheckedMath.add(v0.getZL(), v1.getZL());
    return new VectorI3L(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L addScaled(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1,
    final double r)
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long mz = CheckedMath.multiply(v1.getZL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    final long z = CheckedMath.add(v0.getZL(), mz);
    return new VectorI3L(x, y, z);
  }

  private final static long cast(
    final double x)
  {
    return Math.round(x);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   */

  public final static @Nonnull VectorI3L clamp(
    final @Nonnull VectorReadable3L v,
    final long minimum,
    final long maximum)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    final long z = Math.min(Math.max(v.getZL(), minimum), maximum);
    return new VectorI3L(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 5.0.0
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public final static @Nonnull VectorI3L clampByVector(
    final @Nonnull VectorReadable3L v,
    final @Nonnull VectorReadable3L minimum,
    final @Nonnull VectorReadable3L maximum)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    final long z =
      Math.min(Math.max(v.getZL(), minimum.getZL()), maximum.getZL());
    return new VectorI3L(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most <code>maximum</code>
   */

  public final static @Nonnull VectorI3L clampMaximum(
    final @Nonnull VectorReadable3L v,
    final long maximum)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    final long z = Math.min(v.getZL(), maximum);
    return new VectorI3L(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 5.0.0
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public final static @Nonnull VectorI3L clampMaximumByVector(
    final @Nonnull VectorReadable3L v,
    final @Nonnull VectorReadable3L maximum)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    final long z = Math.min(v.getZL(), maximum.getZL());
    return new VectorI3L(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   */

  public final static @Nonnull VectorI3L clampMinimum(
    final @Nonnull VectorReadable3L v,
    final long minimum)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    final long z = Math.max(v.getZL(), minimum);
    return new VectorI3L(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @since 5.0.0
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public final static @Nonnull VectorI3L clampMinimumByVector(
    final @Nonnull VectorReadable3L v,
    final @Nonnull VectorReadable3L minimum)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    final long z = Math.max(v.getZL(), minimum.getZL());
    return new VectorI3L(x, y, z);
  }

  /**
   * Calculate the distance between the two vectors <code>v0</code> and
   * <code>v1</code>.
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

  public final static long distance(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1)
  {
    return VectorI3L.magnitude(VectorI3L.subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors <code>v0</code> and
   * <code>v1</code>.
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

  public final static long dotProduct(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1)
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.multiply(v0.getZL(), v1.getZL());
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>.
   * 
   * The <code>alpha</code> parameter controls the degree of interpolation,
   * such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(v0, v1, 0.0, r) -> r = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0, r) -> r = v1</code></li>
   * </ul>
   * 
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * 
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public final static @Nonnull VectorI3L interpolateLinear(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1,
    final double alpha)
  {
    final VectorI3L w0 = VectorI3L.scale(v0, 1.0 - alpha);
    final VectorI3L w1 = VectorI3L.scale(v1, alpha);
    return VectorI3L.add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector <code>v</code>.
   * 
   * Correspondingly, <code>magnitude(normalize(v)) == 1.0</code>.
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

  public final static long magnitude(
    final @Nonnull VectorReadable3L v)
  {
    return VectorI3L.cast(Math.sqrt(VectorI3L.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
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

  public final static long magnitudeSquared(
    final @Nonnull VectorReadable3L v)
  {
    return VectorI3L.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L projection(
    final @Nonnull VectorReadable3L p,
    final @Nonnull VectorReadable3L q)
  {
    final long dot = VectorI3L.dotProduct(p, q);
    final long qms = VectorI3L.magnitudeSquared(q);
    final long s = dot / qms;
    return VectorI3L.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L scale(
    final @Nonnull VectorReadable3L v,
    final double r)
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    final long mz = CheckedMath.multiply(v.getZL(), r);
    return new VectorI3L(mx, my, mz);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI3L subtract(
    final @Nonnull VectorReadable3L v0,
    final @Nonnull VectorReadable3L v1)
  {
    final long x = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long y = CheckedMath.subtract(v0.getYL(), v1.getYL());
    final long z = CheckedMath.subtract(v0.getZL(), v1.getZL());
    return new VectorI3L(x, y, z);
  }

  public final long x;
  public final long y;
  public final long z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorI3L()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI3L(
    final long x,
    final long y,
    final long z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI3L(
    final @Nonnull VectorReadable3L v)
  {
    this.x = v.getXL();
    this.y = v.getYL();
    this.z = v.getZL();
  }

  @Override public final boolean equals(
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
    final @Nonnull VectorI3L other = (VectorI3L) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    return true;
  }

  @Override public final long getXL()
  {
    return this.x;
  }

  @Override public final long getYL()
  {
    return this.y;
  }

  @Override public final long getZL()
  {
    return this.z;
  }

  @Override public final int hashCode()
  {
    final long prime = 31;
    long result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return (int) result;
  }

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3L ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }

}
