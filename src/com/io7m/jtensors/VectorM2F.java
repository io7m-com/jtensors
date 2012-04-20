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
 * A two-dimensional mutable vector type with single precision elements.
 */

public final class VectorM2F implements VectorReadable2F
{
  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static VectorM2F absolute(
    final VectorM2F v,
    final VectorM2F out)
  {
    final float x = Math.abs(v.x);
    final float y = Math.abs(v.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y)</code>, saving the result into
   *         <code>v</code>.
   */

  public static VectorM2F absoluteInPlace(
    final VectorM2F v)
  {
    return VectorM2F.absolute(v, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static VectorM2F add(
    final VectorM2F v0,
    final VectorM2F v1,
    final VectorM2F out)
  {
    final float x = v0.x + v1.x;
    final float y = v0.y + v1.y;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>, saving the result in
   *         <code>v0</code>.
   */

  public static VectorM2F addInPlace(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    return VectorM2F.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   */

  public static VectorM2F addScaled(
    final VectorM2F v0,
    final VectorM2F v1,
    final float r,
    final VectorM2F out)
  {
    final float x = v0.x + (v1.x * r);
    final float y = v0.y + (v1.y * r);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>, saving the
   *         result to <code>v0</code>.
   */

  public static VectorM2F addScaledInPlace(
    final VectorM2F v0,
    final VectorM2F v1,
    final float r)
  {
    return VectorM2F.addScaled(v0, v1, r, v0);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The angle between the two vectors, in radians.
   */

  public static float angle(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    final float m0 = VectorM2F.magnitude(v0);
    final float m1 = VectorM2F.magnitude(v1);
    return (float) Math.acos(VectorM2F.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The angle between the two vectors, in degrees.
   */

  public static float angleDegrees(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    return (float) (VectorM2F.angle(v0, v1) * (180.0f / Math.PI));
  }

  /**
   * @see ApproximatelyEqualFloat
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code> , within an appropriate degree of error for float
   *         precision floating point values.
   */

  public static boolean approximatelyEqual(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    final boolean ex = ApproximatelyEqualFloat.approximatelyEqual(v0.x, v1.x);
    final boolean ey = ApproximatelyEqualFloat.approximatelyEqual(v0.y, v1.y);
    return ex && ey;
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

  public static VectorM2F clamp(
    final VectorM2F v,
    final float minimum,
    final float maximum,
    final VectorM2F out)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    out.x = x;
    out.y = y;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   */

  public static VectorM2F clampByVector(
    final VectorM2F v,
    final VectorM2F minimum,
    final VectorM2F maximum,
    final VectorM2F out)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   *         , in <code>v</code>.
   */

  public static VectorM2F clampByVectorInPlace(
    final VectorM2F v,
    final VectorM2F minimum,
    final VectorM2F maximum)
  {
    return VectorM2F.clampByVector(v, minimum, maximum, v);
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

  public static VectorM2F clampInPlace(
    final VectorM2F v,
    final float minimum,
    final float maximum)
  {
    return VectorM2F.clamp(v, minimum, maximum, v);
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

  public static VectorM2F clampMaximum(
    final VectorM2F v,
    final float maximum,
    final VectorM2F out)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static VectorM2F clampMaximumByVector(
    final VectorM2F v,
    final VectorM2F maximum,
    final VectorM2F out)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>, in
   *         <code>v</code>.
   */

  public static VectorM2F clampMaximumByVectorInPlace(
    final VectorM2F v,
    final VectorM2F maximum)
  {
    return VectorM2F.clampMaximumByVector(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>.
   */

  public static VectorM2F clampMaximumInPlace(
    final VectorM2F v,
    final float maximum)
  {
    return VectorM2F.clampMaximum(v, maximum, v);
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

  public static VectorM2F clampMinimum(
    final VectorM2F v,
    final float minimum,
    final VectorM2F out)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>
   */

  public static VectorM2F clampMinimumByVector(
    final VectorM2F v,
    final VectorM2F minimum,
    final VectorM2F out)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>, in
   *         <code>v</code>.
   */

  public static VectorM2F clampMinimumByVectorInPlace(
    final VectorM2F v,
    final VectorM2F minimum)
  {
    return VectorM2F.clampMinimumByVector(v, minimum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM2F clampMinimumInPlace(
    final VectorM2F v,
    final float minimum)
  {
    return VectorM2F.clampMinimum(v, minimum, v);
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

  public static VectorM2F copy(
    final VectorReadable2F input,
    final VectorM2F output)
  {
    output.x = input.getXf();
    output.y = input.getYf();
    return output;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   */

  public static float distance(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    final VectorM2F vr = new VectorM2F();
    return VectorM2F.magnitude(VectorM2F.subtract(v0, v1, vr));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   */

  public static float dotProduct(
    final VectorM2F v0,
    final VectorM2F v1)
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

  public static VectorM2F interpolateLinear(
    final VectorM2F v0,
    final VectorM2F v1,
    final float alpha,
    final VectorM2F r)
  {
    final VectorM2F w0 = new VectorM2F();
    final VectorM2F w1 = new VectorM2F();

    VectorM2F.scale(v0, 1.0f - alpha, w0);
    VectorM2F.scale(v1, alpha, w1);

    return VectorM2F.add(w0, w1, r);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector.
   *         <code>magnitude(normalize(v)) == 1.0</code>.
   */

  public static float magnitude(
    final VectorM2F v)
  {
    return (float) Math.sqrt(VectorM2F.magnitudeSquared(v));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector.
   */

  public static float magnitudeSquared(
    final VectorM2F v)
  {
    return VectorM2F.dotProduct(v, v);
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

  public static VectorM2F normalize(
    final VectorM2F v,
    final VectorM2F out)
  {
    final float m = VectorM2F.magnitudeSquared(v);
    if (m > 0.0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorM2F.scale(v, reciprocal, out);
    }
    out.x = v.x;
    out.y = v.y;
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

  public static VectorM2F normalizeInPlace(
    final VectorM2F v)
  {
    return VectorM2F.normalize(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static VectorM2F projection(
    final VectorM2F p,
    final VectorM2F q,
    final VectorM2F r)
  {
    final float dot = VectorM2F.dotProduct(p, q);
    final float qms = VectorM2F.magnitudeSquared(q);
    final float s = dot / qms;

    return VectorM2F.scale(p, s, r);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r)</code>
   */

  public static VectorM2F scale(
    final VectorM2F v,
    final float r,
    final VectorM2F out)
  {
    final float x = v.x * r;
    final float y = v.y * r;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r)</code>, saving the result into
   *         <code>v</code>.
   */

  public static VectorM2F scaleInPlace(
    final VectorM2F v,
    final float r)
  {
    return VectorM2F.scale(v, r, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static VectorM2F subtract(
    final VectorM2F v0,
    final VectorM2F v1,
    final VectorM2F out)
  {
    final float x = v0.x - v1.x;
    final float y = v0.y - v1.y;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>, saving the result into
   *         <code>out</code>.
   */

  public static VectorM2F subtractInPlace(
    final VectorM2F v0,
    final VectorM2F v1)
  {
    return VectorM2F.subtract(v0, v1, v0);
  }

  public float x = 0.0f;

  public float y = 0.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public VectorM2F()
  {

  }

  public VectorM2F(
    final float x,
    final float y)
  {
    this.x = x;
    this.y = y;
  }

  public VectorM2F(
    final VectorReadable2F v)
  {
    this.x = v.getXf();
    this.y = v.getYf();
  }

  @Override public float getXf()
  {
    return this.x;
  }

  @Override public float getYf()
  {
    return this.y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("VectorM2F [");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
