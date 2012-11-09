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
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.ApproximatelyEqualFloat;

/**
 * A two-dimensional mutable vector type with single precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM2F implements VectorReadable2F
{
  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static @Nonnull VectorM2F absolute(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.abs(v.x);
    final float y = Math.abs(v.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * modifying the vector in-place.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static @Nonnull VectorM2F absoluteInPlace(
    final @Nonnull VectorM2F v)
  {
    return VectorM2F.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static @Nonnull VectorM2F add(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1,
    final @Nonnull VectorM2F out)
  {
    final float x = v0.x + v1.x;
    final float y = v0.y + v1.y;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static @Nonnull VectorM2F addInPlace(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    return VectorM2F.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   */

  public static @Nonnull VectorM2F addScaled(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1,
    final float r,
    final @Nonnull VectorM2F out)
  {
    final float x = v0.x + (v1.x * r);
    final float y = v0.y + (v1.y * r);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   */

  public static @Nonnull VectorM2F addScaledInPlace(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1,
    final float r)
  {
    return VectorM2F.addScaled(v0, v1, r, v0);
  }

  /**
   * Calculate the angle between vectors <code>v0</code> and <code>v1</code>,
   * in radians.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return The angle between the two vectors, in radians.
   */

  public static float angle(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    final float m0 = VectorM2F.magnitude(v0);
    final float m1 = VectorM2F.magnitude(v1);
    return (float) Math.acos(VectorM2F.dotProduct(v0, v1) / (m0 * m1));
  }

  /**
   * Calculate the angle between vectors <code>v0</code> and <code>v1</code>,
   * in degrees.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return The angle between the two vectors, in degrees.
   */

  public static float angleDegrees(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    return (float) (VectorM2F.angle(v0, v1) * (180.0f / Math.PI));
  }

  /**
   * Determine whether or not the elements of the two vectors <code>v0</code>
   * and <code>v1</code> are approximately equal.
   * 
   * @see ApproximatelyEqualFloat
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code>, within an appropriate degree of error for single
   *         precision floating point values
   */

  public static boolean approximatelyEqual(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    final boolean ex = ApproximatelyEqualFloat.approximatelyEqual(v0.x, v1.x);
    final boolean ey = ApproximatelyEqualFloat.approximatelyEqual(v0.y, v1.y);
    return ex && ey;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @param out
   *          The output vector
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   */

  public static @Nonnull VectorM2F clamp(
    final @Nonnull VectorM2F v,
    final float minimum,
    final float maximum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   */

  public static @Nonnull VectorM2F clampByVector(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F minimum,
    final @Nonnull VectorM2F maximum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   */

  public static @Nonnull VectorM2F clampByVectorInPlace(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F minimum,
    final @Nonnull VectorM2F maximum)
  {
    return VectorM2F.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>, in <code>v</code>
   */

  public static @Nonnull VectorM2F clampInPlace(
    final @Nonnull VectorM2F v,
    final float minimum,
    final float maximum)
  {
    return VectorM2F.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   */

  public static @Nonnull VectorM2F clampMaximum(
    final @Nonnull VectorM2F v,
    final float maximum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static @Nonnull VectorM2F clampMaximumByVector(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F maximum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static @Nonnull VectorM2F clampMaximumByVectorInPlace(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F maximum)
  {
    return VectorM2F.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM2F clampMaximumInPlace(
    final @Nonnull VectorM2F v,
    final float maximum)
  {
    return VectorM2F.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   */

  public static @Nonnull VectorM2F clampMinimum(
    final @Nonnull VectorM2F v,
    final float minimum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>
   */

  public static @Nonnull VectorM2F clampMinimumByVector(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F minimum,
    final @Nonnull VectorM2F out)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code> , in
   *         <code>v</code>
   */

  public static @Nonnull VectorM2F clampMinimumByVectorInPlace(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F minimum)
  {
    return VectorM2F.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static @Nonnull VectorM2F clampMinimumInPlace(
    final @Nonnull VectorM2F v,
    final float minimum)
  {
    return VectorM2F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * 
   * @return output
   */

  public static @Nonnull VectorM2F copy(
    final @Nonnull VectorReadable2F input,
    final @Nonnull VectorM2F output)
  {
    output.x = input.getXF();
    output.y = input.getYF();
    return output;
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

  public static float distance(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    final @Nonnull VectorM2F vr = new VectorM2F();
    return VectorM2F.magnitude(VectorM2F.subtract(v0, v1, vr));
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

  public static float dotProduct(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
  {
    final float x = v0.x * v1.x;
    final float y = v0.y * v1.y;
    return x + y;
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>, saving the result to <code>r</code>.
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
   * @param r
   *          The result vector.
   * 
   * @return <code>r</code>
   */

  public static @Nonnull VectorM2F interpolateLinear(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1,
    final float alpha,
    final @Nonnull VectorM2F r)
  {
    final @Nonnull VectorM2F w0 = new VectorM2F();
    final @Nonnull VectorM2F w1 = new VectorM2F();

    VectorM2F.scale(v0, 1.0f - alpha, w0);
    VectorM2F.scale(v1, alpha, w1);

    return VectorM2F.add(w0, w1, r);
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

  public static float magnitude(
    final @Nonnull VectorM2F v)
  {
    return (float) Math.sqrt(VectorM2F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static float magnitudeSquared(
    final @Nonnull VectorM2F v)
  {
    return VectorM2F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as <code>v</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero vector iff the input is the zero vector.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * 
   * @return out
   */

  public static @Nonnull VectorM2F normalize(
    final @Nonnull VectorM2F v,
    final @Nonnull VectorM2F out)
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
   *          The input vector
   * 
   * @return v
   */

  public static @Nonnull VectorM2F normalizeInPlace(
    final @Nonnull VectorM2F v)
  {
    return VectorM2F.normalize(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorM2F projection(
    final @Nonnull VectorM2F p,
    final @Nonnull VectorM2F q,
    final @Nonnull VectorM2F r)
  {
    final float dot = VectorM2F.dotProduct(p, q);
    final float qms = VectorM2F.magnitudeSquared(q);
    final float s = dot / qms;

    return VectorM2F.scale(p, s, r);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   * 
   * @return <code>(v.x * r, v.y * r)</code>
   */

  public static @Nonnull VectorM2F scale(
    final @Nonnull VectorM2F v,
    final float r,
    final @Nonnull VectorM2F out)
  {
    final float x = v.x * r;
    final float y = v.y * r;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r)</code>
   */

  public static @Nonnull VectorM2F scaleInPlace(
    final @Nonnull VectorM2F v,
    final float r)
  {
    return VectorM2F.scale(v, r, v);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static @Nonnull VectorM2F subtract(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1,
    final @Nonnull VectorM2F out)
  {
    final float x = v0.x - v1.x;
    final float y = v0.y - v1.y;
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static @Nonnull VectorM2F subtractInPlace(
    final @Nonnull VectorM2F v0,
    final @Nonnull VectorM2F v1)
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

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM2F(
    final float x,
    final float y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM2F(
    final @Nonnull VectorReadable2F v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
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
    final @Nonnull VectorM2F other = (VectorM2F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
