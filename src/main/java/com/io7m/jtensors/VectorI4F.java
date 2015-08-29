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
 * A four-dimensional immutable vector type with single precision elements.
 */

public final class VectorI4F implements VectorReadable4F
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

  public static VectorI4F addScaled(
    final VectorI4F v0,
    final VectorI4F v1,
    final float r)
  {
    return VectorI4F.add(v0, VectorI4F.scale(v1, r));
  }

  /**
   * @see ApproximatelyEqualDouble
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code> , within an appropriate degree of error for
   *         single precision floating point values.
   */

  public static boolean approximatelyEqual(
    final VectorI4F v0,
    final VectorI4F v1)
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

  public static VectorI4F clamp(
    final VectorI4F v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    final float z = Math.min(Math.max(v.z, minimum), maximum);
    final float w = Math.min(Math.max(v.w, minimum), maximum);
    return new VectorI4F(x, y, z, w);
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

  public static VectorI4F clampByVector(
    final VectorI4F v,
    final VectorI4F minimum,
    final VectorI4F maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final float z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    final float w = Math.min(Math.max(v.w, minimum.w), maximum.w);
    return new VectorI4F(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorI4F clampMaximum(
    final VectorI4F v,
    final float maximum)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    final float z = Math.min(v.z, maximum);
    final float w = Math.min(v.w, maximum);
    return new VectorI4F(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static VectorI4F clampMaximumByVector(
    final VectorI4F v,
    final VectorI4F maximum)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    final float z = Math.min(v.z, maximum.z);
    final float w = Math.min(v.w, maximum.w);
    return new VectorI4F(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorI4F clampMinimum(
    final VectorI4F v,
    final float minimum)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    final float z = Math.max(v.z, minimum);
    final float w = Math.max(v.w, minimum);
    return new VectorI4F(x, y, z, w);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static VectorI4F clampMinimumByVector(
    final VectorI4F v,
    final VectorI4F minimum)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    final float z = Math.max(v.z, minimum.z);
    final float w = Math.max(v.w, minimum.w);
    return new VectorI4F(x, y, z, w);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static float distance(
    final VectorI4F v0,
    final VectorI4F v1)
  {
    return VectorI4F.magnitude(VectorI4F.subtract(v0, v1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static float dotProduct(
    final VectorI4F v0,
    final VectorI4F v1)
  {
    final float x = v0.x * v1.x;
    final float y = v0.y * v1.y;
    final float z = v0.z * v1.z;
    final float w = v0.w * v1.w;
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

  public static VectorI4F interpolateLinear(
    final VectorI4F v0,
    final VectorI4F v1,
    final float alpha)
  {
    final VectorI4F w0 = VectorI4F.scale(v0, 1.0f - alpha);
    final VectorI4F w1 = VectorI4F.scale(v1, alpha);
    return VectorI4F.add(w0, w1);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector..
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static float magnitude(
    final VectorI4F v)
  {
    return (float) Math.sqrt(VectorI4F.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector..
   */

  public static float magnitudeSquared(
    final VectorI4F v)
  {
    return VectorI4F.dotProduct(v, v);
  }

  /**
   * @param v
   *          The input vector.
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>.
   */

  public static VectorI4F normalize(
    final VectorI4F v)
  {
    final float m = VectorI4F.magnitudeSquared(v);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorI4F.scale(v, reciprocal);
    }
    return v;
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorI4F projection(
    final VectorI4F p,
    final VectorI4F q)
  {
    final float dot = VectorI4F.dotProduct(p, q);
    final float qms = VectorI4F.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI4F.scale(p, s);
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   */

  public static VectorI4F scale(
    final VectorI4F v,
    final float r)
  {
    return new VectorI4F(v.x * r, v.y * r, v.z * r, v.w * r);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static VectorI4F subtract(
    final VectorI4F v0,
    final VectorI4F v1)
  {
    return new VectorI4F(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w);
  }

  public final float            x;

  public final float            y;

  public final float            z;

  public final float            w;

  /**
   * The zero vector.
   */

  public static final VectorI4F ZERO = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>
   */

  public static VectorI4F absolute(
    final VectorI4F v)
  {
    return new VectorI4F(
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

  public static VectorI4F add(
    final VectorI4F v0,
    final VectorI4F v1)
  {
    return new VectorI4F(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w);
  }

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorI4F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
    this.w = 1.0f;
  }

  public VectorI4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public VectorI4F(
    final VectorReadable4F v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
    this.w = v.getWF();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    final VectorI4F other = (VectorI4F) obj;
    if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
      return false;
    }
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public float getWF()
  {
    return this.w;
  }

  @Override public float getXF()
  {
    return this.x;
  }

  @Override public float getYF()
  {
    return this.y;
  }

  @Override public float getZF()
  {
    return this.z;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.w);
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI4F ");
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
