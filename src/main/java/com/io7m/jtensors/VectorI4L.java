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
 * A four-dimensional immutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 * 
 * @since 5.3.0
 */

@Immutable public class VectorI4L implements VectorReadable4L
{
  /**
   * The zero vector.
   */

  public static final @Nonnull VectorI4L ZERO = new VectorI4L(0, 0, 0, 0);

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z, abs.w)</code>
   * 
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI4L absolute(
    final @Nonnull VectorReadable4L v)
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    final long z = CheckedMath.absolute(v.getZL());
    final long w = CheckedMath.absolute(v.getWL());
    return new VectorI4L(x, y, z, w);
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI4L add(
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1)
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    final long z = CheckedMath.add(v0.getZL(), v1.getZL());
    final long w = CheckedMath.add(v0.getWL(), v1.getWL());
    return new VectorI4L(x, y, z, w);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI4L addScaled(
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1,
    final double r)
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

  public final static @Nonnull VectorI4L clamp(
    final @Nonnull VectorReadable4L v,
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
   * @return <code>
   *   (min(max(v.x, minimum.x), maximum.x),
   *    min(max(v.y, minimum.y), maximum.y),
   *    min(max(v.z, minimum.z), maximum.z),
   *    min(max(v.w, minimum.w), maximum.w))</code>
   */

  public final static @Nonnull VectorI4L clampByVector(
    final @Nonnull VectorReadable4L v,
    final @Nonnull VectorReadable4L minimum,
    final @Nonnull VectorReadable4L maximum)
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

  public final static @Nonnull VectorI4L clampMaximum(
    final @Nonnull VectorReadable4L v,
    final long maximum)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    final long z = Math.min(v.getZL(), maximum);
    final long w = Math.min(v.getWL(), maximum);
    return new VectorI4L(x, y, z, w);
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public final static @Nonnull VectorI4L clampMaximumByVector(
    final @Nonnull VectorReadable4L v,
    final @Nonnull VectorReadable4L maximum)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    final long z = Math.min(v.getZL(), maximum.getZL());
    final long w = Math.min(v.getWL(), maximum.getWL());
    return new VectorI4L(x, y, z, w);
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

  public final static @Nonnull VectorI4L clampMinimum(
    final @Nonnull VectorReadable4L v,
    final long minimum)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    final long z = Math.max(v.getZL(), minimum);
    final long w = Math.max(v.getWL(), minimum);
    return new VectorI4L(x, y, z, w);
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public final static @Nonnull VectorI4L clampMinimumByVector(
    final @Nonnull VectorReadable4L v,
    final @Nonnull VectorReadable4L minimum)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    final long z = Math.max(v.getZL(), minimum.getZL());
    final long w = Math.max(v.getWL(), minimum.getWL());
    return new VectorI4L(x, y, z, w);
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
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1)
  {
    return VectorI4L.magnitude(VectorI4L.subtract(v0, v1));
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
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1)
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

  public final static @Nonnull VectorI4L interpolateLinear(
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1,
    final double alpha)
  {
    final VectorI4L w0 = VectorI4L.scale(v0, 1.0 - alpha);
    final VectorI4L w1 = VectorI4L.scale(v1, alpha);
    return VectorI4L.add(w0, w1);
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
    final @Nonnull VectorReadable4L v)
  {
    return VectorI4L.cast(Math.sqrt(VectorI4L.magnitudeSquared(v)));
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
    final @Nonnull VectorReadable4L v)
  {
    return VectorI4L.dotProduct(v, v);
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

  public final static @Nonnull VectorI4L projection(
    final @Nonnull VectorReadable4L p,
    final @Nonnull VectorReadable4L q)
  {
    final long dot = VectorI4L.dotProduct(p, q);
    final long qms = VectorI4L.magnitudeSquared(q);
    final long s = dot / qms;
    return VectorI4L.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r, vw * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI4L scale(
    final @Nonnull VectorReadable4L v,
    final double r)
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    final long mz = CheckedMath.multiply(v.getZL(), r);
    final long mw = CheckedMath.multiply(v.getWL(), r);
    return new VectorI4L(mx, my, mz, mw);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public final static @Nonnull VectorI4L subtract(
    final @Nonnull VectorReadable4L v0,
    final @Nonnull VectorReadable4L v1)
  {
    final long x = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long y = CheckedMath.subtract(v0.getYL(), v1.getYL());
    final long z = CheckedMath.subtract(v0.getZL(), v1.getZL());
    final long w = CheckedMath.subtract(v0.getWL(), v1.getWL());
    return new VectorI4L(x, y, z, w);
  }

  public final long x;
  public final long y;
  public final long z;
  public final long w;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0, 1]</code>.
   */

  public VectorI4L()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 1;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI4L(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI4L(
    final @Nonnull VectorReadable4L v)
  {
    this.x = v.getXL();
    this.y = v.getYL();
    this.z = v.getZL();
    this.w = v.getWL();
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
    final @Nonnull VectorI4L other = (VectorI4L) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    if (this.w != other.w) {
      return false;
    }
    return true;
  }

  @Override public final long getWL()
  {
    return this.w;
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
    result = (prime * result) + this.w;
    return (int) result;
  }

  @Override public final String toString()
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
    return builder.toString();
  }

}
