/*
 * Copyright © 2012 http://io7m.com
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

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.functional.Pair;

/**
 * A four-dimensional immutable vector type with double precision elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@Immutable public final class VectorI4D implements VectorReadable4D
{
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
   */

  public static @Nonnull VectorI4D addScaled(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1,
    final double r)
  {
    return VectorI4D.add(v0, VectorI4D.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors <code>qa</code> and <code>qb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * 
   * @param context
   *          The equality context
   * @param qa
   *          The left input vector
   * @param qb
   *          The right input vector
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final @Nonnull ContextRelative context,
    final @Nonnull VectorI4D qa,
    final @Nonnull VectorI4D qb)
  {
    final boolean xs = AlmostEqualDouble.almostEqual(context, qa.x, qb.x);
    final boolean ys = AlmostEqualDouble.almostEqual(context, qa.y, qb.y);
    final boolean zs = AlmostEqualDouble.almostEqual(context, qa.z, qb.z);
    final boolean ws = AlmostEqualDouble.almostEqual(context, qa.w, qb.w);
    return xs && ys && zs && ws;
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
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   */

  public static @Nonnull VectorI4D clamp(
    final @Nonnull VectorI4D v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.x, minimum), maximum);
    final double y = Math.min(Math.max(v.y, minimum), maximum);
    final double z = Math.min(Math.max(v.z, minimum), maximum);
    final double w = Math.min(Math.max(v.w, minimum), maximum);
    return new VectorI4D(x, y, z, w);
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
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static @Nonnull VectorI4D clampByVector(
    final @Nonnull VectorI4D v,
    final @Nonnull VectorI4D minimum,
    final @Nonnull VectorI4D maximum)
  {
    final double x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final double y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final double z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    final double w = Math.min(Math.max(v.w, minimum.w), maximum.w);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   */

  public static @Nonnull VectorI4D clampMaximum(
    final @Nonnull VectorI4D v,
    final double maximum)
  {
    final double x = Math.min(v.x, maximum);
    final double y = Math.min(v.y, maximum);
    final double z = Math.min(v.z, maximum);
    final double w = Math.min(v.w, maximum);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static @Nonnull VectorI4D clampMaximumByVector(
    final @Nonnull VectorI4D v,
    final @Nonnull VectorI4D maximum)
  {
    final double x = Math.min(v.x, maximum.x);
    final double y = Math.min(v.y, maximum.y);
    final double z = Math.min(v.z, maximum.z);
    final double w = Math.min(v.w, maximum.w);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static @Nonnull VectorI4D clampMinimum(
    final @Nonnull VectorI4D v,
    final double minimum)
  {
    final double x = Math.max(v.x, minimum);
    final double y = Math.max(v.y, minimum);
    final double z = Math.max(v.z, minimum);
    final double w = Math.max(v.w, minimum);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static @Nonnull VectorI4D clampMinimumByVector(
    final @Nonnull VectorI4D v,
    final @Nonnull VectorI4D minimum)
  {
    final double x = Math.max(v.x, minimum.x);
    final double y = Math.max(v.y, minimum.y);
    final double z = Math.max(v.z, minimum.z);
    final double w = Math.max(v.w, minimum.w);
    return new VectorI4D(x, y, z, w);
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
   */

  public static double distance(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1)
  {
    return VectorI4D.magnitude(VectorI4D.subtract(v0, v1));
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
   */

  public static double dotProduct(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1)
  {
    final double x = v0.x * v1.x;
    final double y = v0.y * v1.y;
    final double z = v0.z * v1.z;
    final double w = v0.w * v1.w;
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>.
   * 
   * The <code>alpha</code> parameter controls the degree of interpolation,
   * such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(v0, v1, 0.0) = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0) = v1</code></li>
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
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   */

  public static @Nonnull VectorI4D interpolateLinear(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1,
    final double alpha)
  {
    final @Nonnull VectorI4D w0 = VectorI4D.scale(v0, 1.0 - alpha);
    final @Nonnull VectorI4D w1 = VectorI4D.scale(v1, alpha);
    return VectorI4D.add(w0, w1);
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
   */

  public static double magnitude(
    final @Nonnull VectorI4D v)
  {
    return Math.sqrt(VectorI4D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final @Nonnull VectorI4D v)
  {
    return VectorI4D.dotProduct(v, v);
  }

  /**
   * Normalize the vector <code>v</code>, preserving its direction but
   * reducing it to unit length.
   * 
   * @param v
   *          The input vector
   * 
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>
   */

  public static @Nonnull VectorI4D normalize(
    final @Nonnull VectorI4D v)
  {
    final double m = VectorI4D.magnitudeSquared(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorI4D.scale(v, reciprocal);
    }
    return v;
  }

  /**
   * Orthonormalize and return the vectors <code>v0</code> and <code>v1</code>
   * . See <a
   * href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">Gram-Schmidt
   * process</a>.
   * 
   * @return A pair <code>(v0, v1)</code>, orthonormalized.
   * 
   * @since 5.0.0
   */

  public static @Nonnull Pair<VectorI4D, VectorI4D> orthoNormalize(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1)
  {
    final VectorI4D v0n = VectorI4D.normalize(v0);
    final VectorI4D projection =
      VectorI4D.scale(v0n, VectorI4D.dotProduct(v1, v0n));
    final VectorI4D vr =
      VectorI4D.normalize(VectorI4D.subtract(v1, projection));
    return new Pair<VectorI4D, VectorI4D>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorI4D projection(
    final @Nonnull VectorI4D p,
    final @Nonnull VectorI4D q)
  {
    final double dot = VectorI4D.dotProduct(p, q);
    final double qms = VectorI4D.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI4D.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   */

  public static @Nonnull VectorI4D scale(
    final @Nonnull VectorI4D v,
    final double r)
  {
    return new VectorI4D(v.x * r, v.y * r, v.z * r, v.w * r);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull VectorI4D subtract(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1)
  {
    return new VectorI4D(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w);
  }

  public final double                    x;
  public final double                    y;
  public final double                    z;
  public final double                    w;

  /**
   * The zero vector.
   */

  public static final @Nonnull VectorI4D ZERO;

  static {
    ZERO = new VectorI4D(0.0, 0.0, 0.0, 0.0);
  }

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>
   */

  public static @Nonnull VectorI4D absolute(
    final @Nonnull VectorI4D v)
  {
    return new VectorI4D(
      Math.abs(v.x),
      Math.abs(v.y),
      Math.abs(v.z),
      Math.abs(v.w));
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
   */

  public static @Nonnull VectorI4D add(
    final @Nonnull VectorI4D v0,
    final @Nonnull VectorI4D v1)
  {
    return new VectorI4D(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w);
  }

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorI4D()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
    this.w = 1.0;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI4D(
    final double x,
    final double y,
    final double z,
    final double w)
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

  public VectorI4D(
    final VectorReadable4D v)
  {
    this.x = v.getXD();
    this.y = v.getYD();
    this.z = v.getZD();
    this.w = v.getWD();
  }

  @Override public boolean equals(
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
    final @Nonnull VectorI4D other = (VectorI4D) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public double getWD()
  {
    return this.w;
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
    temp = Double.doubleToLongBits(this.w);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
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
    builder.append("[VectorI4D ");
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
