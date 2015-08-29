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

import com.io7m.jaux.ApproximatelyEqualDouble;

/**
 * A four-dimensional immutable vector type with double precision elements.
 */

public final class VectorI4D implements VectorReadable4D
{
  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   */

  public static VectorI4D addScaled(
    final VectorI4D v0,
    final VectorI4D v1,
    final double r)
  {
    return VectorI4D.add(v0, VectorI4D.scale(v1, r));
  }

  /**
   * @see ApproximatelyEqualDouble
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code> , within an appropriate degree of error for
   *         double precision floating point values.
   */

  public static boolean approximatelyEqual(
    final VectorI4D v0,
    final VectorI4D v1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(v0.x, v1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(v0.y, v1.y);
    final boolean ez =
      ApproximatelyEqualDouble.approximatelyEqual(v0.z, v1.z);
    final boolean ew =
      ApproximatelyEqualDouble.approximatelyEqual(v0.w, v1.w);
    return ex && ey && ez && ew;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>.
   */

  public static VectorI4D clamp(
    final VectorI4D v,
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
   * @param v
   *          The input vector.
   * @param minimum
   *          the vector containing the minimum acceptable values
   * @param maximum
   *          the vector containing the maximum acceptable values
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static VectorI4D clampByVector(
    final VectorI4D v,
    final VectorI4D minimum,
    final VectorI4D maximum)
  {
    final double x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final double y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final double z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    final double w = Math.min(Math.max(v.w, minimum.w), maximum.w);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorI4D clampMaximum(
    final VectorI4D v,
    final double maximum)
  {
    final double x = Math.min(v.x, maximum);
    final double y = Math.min(v.y, maximum);
    final double z = Math.min(v.z, maximum);
    final double w = Math.min(v.w, maximum);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static VectorI4D clampMaximumByVector(
    final VectorI4D v,
    final VectorI4D maximum)
  {
    final double x = Math.min(v.x, maximum.x);
    final double y = Math.min(v.y, maximum.y);
    final double z = Math.min(v.z, maximum.z);
    final double w = Math.min(v.w, maximum.w);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorI4D clampMinimum(
    final VectorI4D v,
    final double minimum)
  {
    final double x = Math.max(v.x, minimum);
    final double y = Math.max(v.y, minimum);
    final double z = Math.max(v.z, minimum);
    final double w = Math.max(v.w, minimum);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static VectorI4D clampMinimumByVector(
    final VectorI4D v,
    final VectorI4D minimum)
  {
    final double x = Math.max(v.x, minimum.x);
    final double y = Math.max(v.y, minimum.y);
    final double z = Math.max(v.z, minimum.z);
    final double w = Math.max(v.w, minimum.w);
    return new VectorI4D(x, y, z, w);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static double distance(
    final VectorI4D v0,
    final VectorI4D v1)
  {
    return VectorI4D.magnitude(VectorI4D.subtract(v0, v1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static double dotProduct(
    final VectorI4D v0,
    final VectorI4D v1)
  {
    final double x = v0.x * v1.x;
    final double y = v0.y * v1.y;
    final double z = v0.z * v1.z;
    final double w = v0.w * v1.w;
    return x + y + z + w;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   */

  public static VectorI4D interpolateLinear(
    final VectorI4D v0,
    final VectorI4D v1,
    final double alpha)
  {
    final VectorI4D w0 = VectorI4D.scale(v0, 1.0 - alpha);
    final VectorI4D w1 = VectorI4D.scale(v1, alpha);
    return VectorI4D.add(w0, w1);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector..
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static double magnitude(
    final VectorI4D v)
  {
    return Math.sqrt(VectorI4D.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector..
   */

  public static double magnitudeSquared(
    final VectorI4D v)
  {
    return VectorI4D.dotProduct(v, v);
  }

  /**
   * @param v
   *          The input vector.
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>.
   */

  public static VectorI4D normalize(
    final VectorI4D v)
  {
    final double m = VectorI4D.magnitudeSquared(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorI4D.scale(v, reciprocal);
    }
    return v;
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorI4D projection(
    final VectorI4D p,
    final VectorI4D q)
  {
    final double dot = VectorI4D.dotProduct(p, q);
    final double qms = VectorI4D.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI4D.scale(p, s);
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   */

  public static VectorI4D scale(
    final VectorI4D v,
    final double r)
  {
    return new VectorI4D(v.x * r, v.y * r, v.z * r, v.w * r);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static VectorI4D subtract(
    final VectorI4D v0,
    final VectorI4D v1)
  {
    return new VectorI4D(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w);
  }

  public final double           x;

  public final double           y;

  public final double           z;

  public final double           w;

  /**
   * The zero vector.
   */

  public static final VectorI4D ZERO = new VectorI4D(0.0, 0.0, 0.0, 0.0);

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>
   */

  public static VectorI4D absolute(
    final VectorI4D v)
  {
    return new VectorI4D(
      Math.abs(v.x),
      Math.abs(v.y),
      Math.abs(v.z),
      Math.abs(v.w));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   */

  public static VectorI4D add(
    final VectorI4D v0,
    final VectorI4D v1)
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
    final VectorI4D other = (VectorI4D) obj;
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
