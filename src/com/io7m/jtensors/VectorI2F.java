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

/**
 * A two-dimensional immutable vector type with single precision elements.
 */

public final class VectorI2F implements VectorReadable2F
{
  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static VectorI2F add(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    return new VectorI2F(v0.x + v1.x, v0.y + v1.y);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   */

  public static VectorI2F addScaled(
    final VectorI2F v0,
    final VectorI2F v1,
    final float r)
  {
    return VectorI2F.add(v0, VectorI2F.scale(v1, r));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The angle between the two vectors, in radians.
   */

  public static float angle(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    final float m0 = VectorI2F.magnitude(v0);
    final float m1 = VectorI2F.magnitude(v1);
    return (float) Math.acos(VectorI2F.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The angle between the two vectors, in degrees.
   */

  public static float angleDegrees(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    return (float) (VectorI2F.angle(v0, v1) * (180.0 / Math.PI));
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
    final VectorI2F v0,
    final VectorI2F v1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(v0.x, v1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(v0.y, v1.y);
    return ex && ey;
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

  public static VectorI2F clamp(
    final VectorI2F v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    return new VectorI2F(x, y);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   */

  public static VectorI2F clampByVector(
    final VectorI2F v,
    final VectorI2F minimum,
    final VectorI2F maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    return new VectorI2F(x, y);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorI2F clampMaximum(
    final VectorI2F v,
    final float maximum)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    return new VectorI2F(x, y);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static VectorI2F clampMaximumByVector(
    final VectorI2F v,
    final VectorI2F maximum)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    return new VectorI2F(x, y);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorI2F clampMinimum(
    final VectorI2F v,
    final float minimum)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    return new VectorI2F(x, y);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>
   */

  public static VectorI2F clampMinimumByVector(
    final VectorI2F v,
    final VectorI2F minimum)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    return new VectorI2F(x, y);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static float distance(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    return VectorI2F.magnitude(VectorI2F.subtract(v0, v1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static float dotProduct(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    final float x = v0.x * v1.x;
    final float y = v0.y * v1.y;
    return x + y;
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

  public static VectorI2F interpolateLinear(
    final VectorI2F v0,
    final VectorI2F v1,
    final float alpha)
  {
    final VectorI2F w0 = VectorI2F.scale(v0, (float) (1.0 - alpha));
    final VectorI2F w1 = VectorI2F.scale(v1, alpha);
    return VectorI2F.add(w0, w1);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of The input vector..
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static float magnitude(
    final VectorI2F v)
  {
    return (float) Math.sqrt(VectorI2F.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of The input vector..
   */

  public static float magnitudeSquared(
    final VectorI2F v)
  {
    return VectorI2F.dotProduct(v, v);
  }

  /**
   * @param v
   *          The input vector.
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>.
   */

  public static VectorI2F normalize(
    final VectorI2F v)
  {
    final float m = VectorI2F.magnitude(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorI2F.scale(v, (float) reciprocal);
    }
    return v;
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorI2F projection(
    final VectorI2F p,
    final VectorI2F q)
  {
    final float dot = VectorI2F.dotProduct(p, q);
    final float qms = VectorI2F.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI2F.scale(p, s);
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r)</code>
   */

  public static VectorI2F scale(
    final VectorI2F v,
    final float r)
  {
    return new VectorI2F(v.x * r, v.y * r);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static VectorI2F subtract(
    final VectorI2F v0,
    final VectorI2F v1)
  {
    return new VectorI2F(v0.x - v1.x, v0.y - v1.y);
  }

  public final float            x;

  public final float            y;

  /**
   * The zero vector.
   */

  public static final VectorI2F zero = new VectorI2F(0.0f, 0.0f);

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static VectorI2F absolute(
    final VectorI2F v)
  {
    return new VectorI2F(Math.abs(v.x), Math.abs(v.y));
  }

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public VectorI2F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
  }

  public VectorI2F(
    final double x,
    final double y)
  {
    this.x = (float) x;
    this.y = (float) y;
  }

  public VectorI2F(
    final float x,
    final float y)
  {
    this.x = x;
    this.y = y;
  }

  public VectorI2F(
    final VectorReadable2F v)
  {
    this.x = v.getXf();
    this.y = v.getYf();
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
    final VectorI2F other = (VectorI2F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    return true;
  }

  @Override public float getXf()
  {
    return this.x;
  }

  @Override public float getYf()
  {
    return this.y;
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
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("VectorI2F [");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
