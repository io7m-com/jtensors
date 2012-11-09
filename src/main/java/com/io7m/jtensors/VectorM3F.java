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

import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.ApproximatelyEqualDouble;

/**
 * A three-dimensional mutable vector type with single precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM3F implements VectorReadable3F
{
  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static VectorM3F absolute(
    final VectorM3F v,
    final VectorM3F out)
  {
    final float x = Math.abs(v.x);
    final float y = Math.abs(v.y);
    final float z = Math.abs(v.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>, saving the result into
   *         <code>v</code>.
   */

  public static VectorM3F absoluteInPlace(
    final VectorM3F v)
  {
    return VectorM3F.absolute(v, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static VectorM3F add(
    final VectorM3F v0,
    final VectorM3F v1,
    final VectorM3F out)
  {
    final float x = v0.x + v1.x;
    final float y = v0.y + v1.y;
    final float z = v0.z + v1.z;
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>, saving the
   *         result in <code>v0</code>.
   */

  public static VectorM3F addInPlace(
    final VectorM3F v0,
    final VectorM3F v1)
  {
    return VectorM3F.add(v0, v1, v0);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static VectorM3F addScaled(
    final VectorM3F v0,
    final VectorM3F v1,
    final float r,
    final VectorM3F out)
  {
    final float x = v0.x + (v1.x * r);
    final float y = v0.y + (v1.y * r);
    final float z = v0.z + (v1.z * r);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   *         , saving the result to <code>v0</code>.
   */

  public static VectorM3F addScaledInPlace(
    final VectorM3F v0,
    final VectorM3F v1,
    final float r)
  {
    return VectorM3F.addScaled(v0, v1, r, v0);
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
    final VectorM3F v0,
    final VectorM3F v1)
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
   * @param out
   *          The output vector.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>.
   */

  public static VectorM3F clamp(
    final VectorM3F v,
    final float minimum,
    final float maximum,
    final VectorM3F out)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    final float z = Math.min(Math.max(v.z, minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static VectorM3F clampByVector(
    final VectorM3F v,
    final VectorM3F minimum,
    final VectorM3F maximum,
    final VectorM3F out)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final float z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3F clampByVectorInPlace(
    final VectorM3F v,
    final VectorM3F minimum,
    final VectorM3F maximum)
  {
    return VectorM3F.clampByVector(v, minimum, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM3F clampInPlace(
    final VectorM3F v,
    final float minimum,
    final float maximum)
  {
    return VectorM3F.clamp(v, minimum, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorM3F clampMaximum(
    final VectorM3F v,
    final float maximum,
    final VectorM3F out)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    final float z = Math.min(v.z, maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static VectorM3F clampMaximumByVector(
    final VectorM3F v,
    final VectorM3F maximum,
    final VectorM3F out)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    final float z = Math.min(v.z, maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3F clampMaximumByVectorInPlace(
    final VectorM3F v,
    final VectorM3F maximum)
  {
    return VectorM3F.clampMaximumByVector(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>.
   */

  public static VectorM3F clampMaximumInPlace(
    final VectorM3F v,
    final float maximum)
  {
    return VectorM3F.clampMaximum(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorM3F clampMinimum(
    final VectorM3F v,
    final float minimum,
    final VectorM3F out)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    final float z = Math.max(v.z, minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static VectorM3F clampMinimumByVector(
    final VectorM3F v,
    final VectorM3F minimum,
    final VectorM3F out)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    final float z = Math.max(v.z, minimum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3F clampMinimumByVectorInPlace(
    final VectorM3F v,
    final VectorM3F minimum)
  {
    return VectorM3F.clampMinimumByVector(v, minimum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM3F clampMinimumInPlace(
    final VectorM3F v,
    final float minimum)
  {
    return VectorM3F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return output
   */

  public static VectorM3F copy(
    final VectorReadable3F input,
    final VectorM3F output)
  {
    output.x = input.getXF();
    output.y = input.getYF();
    output.z = input.getZF();
    return output;
  }

  /**
   * Return a vector perpendicular to both <code>v0</code> and <code>v1</code>
   * , saving the result in <code>out</code>.
   * 
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return out
   */

  public static VectorM3F crossProduct(
    final VectorReadable3F v0,
    final VectorReadable3F v1,
    final VectorM3F out)
  {
    final float x = (v0.getYF() * v1.getZF()) - (v0.getZF() * v1.getYF());
    final float y = (v0.getZF() * v1.getXF()) - (v0.getXF() * v1.getZF());
    final float z = (v0.getXF() * v1.getYF()) - (v0.getYF() * v1.getXF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static float distance(
    final VectorM3F v0,
    final VectorM3F v1)
  {
    final VectorM3F vr = new VectorM3F();
    return VectorM3F.magnitude(VectorM3F.subtract(v0, v1, vr));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static float dotProduct(
    final VectorM3F v0,
    final VectorM3F v1)
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

  public static VectorM3F interpolateLinear(
    final VectorM3F v0,
    final VectorM3F v1,
    final float alpha,
    final VectorM3F r)
  {
    final VectorM3F w0 = new VectorM3F();
    final VectorM3F w1 = new VectorM3F();

    VectorM3F.scale(v0, 1.0f - alpha, w0);
    VectorM3F.scale(v1, alpha, w1);

    return VectorM3F.add(w0, w1, r);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector.
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static float magnitude(
    final VectorM3F v)
  {
    return (float) Math.sqrt(VectorM3F.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector.
   */

  public static float magnitudeSquared(
    final VectorM3F v)
  {
    return VectorM3F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as <code>v</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero vector iff the input is the zero vector.
   * 
   * @param v
   *          The input vector.
   * @return out
   */

  public static VectorM3F normalize(
    final VectorM3F v,
    final VectorM3F out)
  {
    final float m = VectorM3F.magnitudeSquared(v);
    if (m > 0.0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorM3F.scale(v, reciprocal, out);
    }
    out.x = v.x;
    out.y = v.y;
    out.z = v.z;
    return out;
  }

  /**
   * Returns a vector with the same orientation as <code>v</code> but with
   * magnitude equal to <code>1.0</code> in <code>v</code>. The function
   * returns the zero vector iff the input is the zero vector.
   * 
   * @param v
   *          The input vector.
   * @return v
   */

  public static VectorM3F normalizeInPlace(
    final VectorM3F v)
  {
    return VectorM3F.normalize(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorM3F projection(
    final VectorM3F p,
    final VectorM3F q,
    final VectorM3F r)
  {
    final float dot = VectorM3F.dotProduct(p, q);
    final float qms = VectorM3F.magnitudeSquared(q);
    final float s = dot / qms;

    return VectorM3F.scale(p, s, r);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static VectorM3F scale(
    final VectorM3F v,
    final float r,
    final VectorM3F out)
  {
    final float x = v.x * r;
    final float y = v.y * r;
    final float z = v.z * r;
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>, saving the result into
   *         <code>v</code>.
   */

  public static VectorM3F scaleInPlace(
    final VectorM3F v,
    final float r)
  {
    return VectorM3F.scale(v, r, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static VectorM3F subtract(
    final VectorM3F v0,
    final VectorM3F v1,
    final VectorM3F out)
  {
    final float x = v0.x - v1.x;
    final float y = v0.y - v1.y;
    final float z = v0.z - v1.z;
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>, saving the
   *         result into <code>out</code>.
   */

  public static VectorM3F subtractInPlace(
    final VectorM3F v0,
    final VectorM3F v1)
  {
    return VectorM3F.subtract(v0, v1, v0);
  }

  public float x = 0.0f;

  public float y = 0.0f;

  public float z = 0.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorM3F()
  {

  }

  public VectorM3F(
    final float x,
    final float y,
    final float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public VectorM3F(
    final VectorReadable3F v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
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
    final VectorM3F other = (VectorM3F) obj;
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
    builder.append("[VectorM3F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
