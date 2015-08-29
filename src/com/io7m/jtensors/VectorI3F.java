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
 * A three-dimensional immutable vector type with single precision elements.
 */

public final class VectorI3F implements VectorReadable3F
{
  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static VectorI3F addScaled(
    final VectorI3F v0,
    final VectorI3F v1,
    final float r)
  {
    return VectorI3F.add(v0, VectorI3F.scale(v1, r));
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
    final VectorI3F v0,
    final VectorI3F v1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(v0.x, v1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(v0.y, v1.y);
    final boolean ez =
      ApproximatelyEqualDouble.approximatelyEqual(v0.z, v1.z);
    return ex && ey && ez;
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

  public static VectorI3F clamp(
    final VectorI3F v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    final float z = Math.min(Math.max(v.z, minimum), maximum);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          the vector containing the minimum acceptable values
   * @param maximum
   *          the vector containing the maximum acceptable values
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static VectorI3F clampByVector(
    final VectorI3F v,
    final VectorI3F minimum,
    final VectorI3F maximum)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final float z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorI3F clampMaximum(
    final VectorI3F v,
    final float maximum)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    final float z = Math.min(v.z, maximum);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static VectorI3F clampMaximumByVector(
    final VectorI3F v,
    final VectorI3F maximum)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    final float z = Math.min(v.z, maximum.z);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorI3F clampMinimum(
    final VectorI3F v,
    final float minimum)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    final float z = Math.max(v.z, minimum);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static VectorI3F clampMinimumByVector(
    final VectorI3F v,
    final VectorI3F minimum)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    final float z = Math.max(v.z, minimum.z);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return A vector perpendicular to both <code>v0</code> and
   *         <code>v1</code>.
   */

  public static VectorI3F crossProduct(
    final VectorI3F v0,
    final VectorI3F v1)
  {
    final float x = (v0.y * v1.z) - (v0.z * v1.y);
    final float y = (v0.z * v1.x) - (v0.x * v1.z);
    final float z = (v0.x * v1.y) - (v0.y * v1.x);
    return new VectorI3F(x, y, z);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static float distance(
    final VectorI3F v0,
    final VectorI3F v1)
  {
    return VectorI3F.magnitude(VectorI3F.subtract(v0, v1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static float dotProduct(
    final VectorI3F v0,
    final VectorI3F v1)
  {
    final float x = v0.x * v1.x;
    final float y = v0.y * v1.y;
    final float z = v0.z * v1.z;
    return x + y + z;
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

  public static VectorI3F interpolateLinear(
    final VectorI3F v0,
    final VectorI3F v1,
    final float alpha)
  {
    final VectorI3F w0 = VectorI3F.scale(v0, 1.0f - alpha);
    final VectorI3F w1 = VectorI3F.scale(v1, alpha);
    return VectorI3F.add(w0, w1);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector..
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static float magnitude(
    final VectorI3F v)
  {
    return (float) Math.sqrt(VectorI3F.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector..
   */

  public static float magnitudeSquared(
    final VectorI3F v)
  {
    return VectorI3F.dotProduct(v, v);
  }

  /**
   * @param v
   *          The input vector.
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>.
   */

  public static VectorI3F normalize(
    final VectorI3F v)
  {
    final float m = VectorI3F.magnitudeSquared(v);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorI3F.scale(v, reciprocal);
    }
    return v;
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorI3F projection(
    final VectorI3F p,
    final VectorI3F q)
  {
    final float dot = VectorI3F.dotProduct(p, q);
    final float qms = VectorI3F.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI3F.scale(p, s);
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static VectorI3F scale(
    final VectorI3F v,
    final float r)
  {
    return new VectorI3F(v.x * r, v.y * r, v.z * r);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static VectorI3F subtract(
    final VectorI3F v0,
    final VectorI3F v1)
  {
    return new VectorI3F(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z);
  }

  public final float            x;

  public final float            y;

  public final float            z;

  /**
   * The zero vector.
   */

  public static final VectorI3F zero = new VectorI3F(0.0f, 0.0f, 0.0f);

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static VectorI3F absolute(
    final VectorI3F v)
  {
    return new VectorI3F(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static VectorI3F add(
    final VectorI3F v0,
    final VectorI3F v1)
  {
    return new VectorI3F(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z);
  }

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorI3F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
  }

  public VectorI3F(
    final float x,
    final float y,
    final float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public VectorI3F(
    final VectorReadable3F v)
  {
    this.x = v.getX();
    this.y = v.getY();
    this.z = v.getZ();
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
    final VectorI3F other = (VectorI3F) obj;
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

  @Override public float getX()
  {
    return this.x;
  }

  @Override public float getY()
  {
    return this.y;
  }

  @Override public float getZ()
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
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("VectorI3F [");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
