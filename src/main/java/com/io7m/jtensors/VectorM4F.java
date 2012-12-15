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

import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jaux.ApproximatelyEqualFloat;

/**
 * A four-dimensional mutable vector type with single precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM4F implements VectorReadable4F
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
   * @return <code>(abs v.x, abs v.y, abs v.z, abs.w)</code>
   */

  public static @Nonnull VectorM4F absolute(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.abs(v.x);
    final float y = Math.abs(v.y);
    final float z = Math.abs(v.z);
    final float w = Math.abs(v.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * modifying the vector in-place.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>
   */

  public static @Nonnull VectorM4F absoluteInPlace(
    final @Nonnull VectorM4F v)
  {
    return VectorM4F.absolute(v, v);
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   */

  public static @Nonnull VectorM4F add(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1,
    final @Nonnull VectorM4F out)
  {
    final float x = v0.x + v1.x;
    final float y = v0.y + v1.y;
    final float z = v0.z + v1.z;
    final float w = v0.w + v1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   */

  public static @Nonnull VectorM4F addInPlace(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
  {
    return VectorM4F.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   */

  public static @Nonnull VectorM4F addScaled(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1,
    final float r,
    final @Nonnull VectorM4F out)
  {
    final float x = v0.x + (v1.x * r);
    final float y = v0.y + (v1.y * r);
    final float z = v0.z + (v1.z * r);
    final float w = v0.w + (v1.w * r);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   */

  public static @Nonnull VectorM4F addScaledInPlace(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1,
    final float r)
  {
    return VectorM4F.addScaled(v0, v1, r, v0);
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
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
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

  public static @Nonnull VectorM4F clamp(
    final @Nonnull VectorM4F v,
    final float minimum,
    final float maximum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.min(Math.max(v.x, minimum), maximum);
    final float y = Math.min(Math.max(v.y, minimum), maximum);
    final float z = Math.min(Math.max(v.z, minimum), maximum);
    final float w = Math.min(Math.max(v.w, minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static @Nonnull VectorM4F clampByVector(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F minimum,
    final @Nonnull VectorM4F maximum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final float y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final float z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    final float w = Math.min(Math.max(v.w, minimum.w), maximum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static @Nonnull VectorM4F clampByVectorInPlace(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F minimum,
    final @Nonnull VectorM4F maximum)
  {
    return VectorM4F.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull VectorM4F clampInPlace(
    final @Nonnull VectorM4F v,
    final float minimum,
    final float maximum)
  {
    return VectorM4F.clamp(v, minimum, maximum, v);
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

  public static @Nonnull VectorM4F clampMaximum(
    final @Nonnull VectorM4F v,
    final float maximum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.min(v.x, maximum);
    final float y = Math.min(v.y, maximum);
    final float z = Math.min(v.z, maximum);
    final float w = Math.min(v.w, maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static @Nonnull VectorM4F clampMaximumByVector(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F maximum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.min(v.x, maximum.x);
    final float y = Math.min(v.y, maximum.y);
    final float z = Math.min(v.z, maximum.z);
    final float w = Math.min(v.w, maximum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static @Nonnull VectorM4F clampMaximumByVectorInPlace(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F maximum)
  {
    return VectorM4F.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull VectorM4F clampMaximumInPlace(
    final @Nonnull VectorM4F v,
    final float maximum)
  {
    return VectorM4F.clampMaximum(v, maximum, v);
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

  public static @Nonnull VectorM4F clampMinimum(
    final @Nonnull VectorM4F v,
    final float minimum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.max(v.x, minimum);
    final float y = Math.max(v.y, minimum);
    final float z = Math.max(v.z, minimum);
    final float w = Math.max(v.w, minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static @Nonnull VectorM4F clampMinimumByVector(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F minimum,
    final @Nonnull VectorM4F out)
  {
    final float x = Math.max(v.x, minimum.x);
    final float y = Math.max(v.y, minimum.y);
    final float z = Math.max(v.z, minimum.z);
    final float w = Math.max(v.w, minimum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM4F clampMinimumByVectorInPlace(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F minimum)
  {
    return VectorM4F.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull VectorM4F clampMinimumInPlace(
    final @Nonnull VectorM4F v,
    final float minimum)
  {
    return VectorM4F.clampMinimum(v, minimum, v);
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

  public static @Nonnull VectorM4F copy(
    final @Nonnull VectorReadable4F input,
    final @Nonnull VectorM4F output)
  {
    output.x = input.getXF();
    output.y = input.getYF();
    output.z = input.getZF();
    output.w = input.getWF();
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
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
  {
    final @Nonnull VectorM4F vr = new VectorM4F();
    return VectorM4F.magnitude(VectorM4F.subtract(v0, v1, vr));
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
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
  {
    final float x = v0.x * v1.x;
    final float y = v0.y * v1.y;
    final float z = v0.z * v1.z;
    final float w = v0.w * v1.w;
    return x + y + z + w;
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

  public static @Nonnull VectorM4F interpolateLinear(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1,
    final float alpha,
    final @Nonnull VectorM4F r)
  {
    final @Nonnull VectorM4F w0 = new VectorM4F();
    final @Nonnull VectorM4F w1 = new VectorM4F();

    VectorM4F.scale(v0, 1.0f - alpha, w0);
    VectorM4F.scale(v1, alpha, w1);

    return VectorM4F.add(w0, w1, r);
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
    final @Nonnull VectorM4F v)
  {
    return (float) Math.sqrt(VectorM4F.magnitudeSquared(v));
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
    final @Nonnull VectorM4F v)
  {
    return VectorM4F.dotProduct(v, v);
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

  public static @Nonnull VectorM4F normalize(
    final @Nonnull VectorM4F v,
    final @Nonnull VectorM4F out)
  {
    final float m = VectorM4F.magnitudeSquared(v);
    if (m > 0.0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorM4F.scale(v, reciprocal, out);
    }
    out.x = v.x;
    out.y = v.y;
    out.z = v.z;
    out.w = v.w;
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

  public static @Nonnull VectorM4F normalizeInPlace(
    final @Nonnull VectorM4F v)
  {
    return VectorM4F.normalize(v, v);
  }

  /**
   * Orthonormalize and the vectors <code>v0</code> and <code>v1</code>,
   * modifying them in-place.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">Gram-Schmidt
   *      process</a>
   * 
   * @since 4.2.0
   */

  public static void orthoNormalizeInPlace(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
  {
    final VectorM4F projection = new VectorM4F();

    VectorM4F.normalizeInPlace(v0);
    VectorM4F.scale(v0, VectorM4F.dotProduct(v1, v0), projection);
    VectorM4F.subtractInPlace(v1, projection);
    VectorM4F.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorM4F projection(
    final @Nonnull VectorM4F p,
    final @Nonnull VectorM4F q,
    final @Nonnull VectorM4F r)
  {
    final float dot = VectorM4F.dotProduct(p, q);
    final float qms = VectorM4F.magnitudeSquared(q);
    final float s = dot / qms;

    return VectorM4F.scale(p, s, r);
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
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   */

  public static @Nonnull VectorM4F scale(
    final @Nonnull VectorM4F v,
    final float r,
    final @Nonnull VectorM4F out)
  {
    final float x = v.x * r;
    final float y = v.y * r;
    final float z = v.z * r;
    final float w = v.w * r;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   */

  public static @Nonnull VectorM4F scaleInPlace(
    final @Nonnull VectorM4F v,
    final float r)
  {
    return VectorM4F.scale(v, r, v);
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   */

  public static @Nonnull VectorM4F subtract(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1,
    final @Nonnull VectorM4F out)
  {
    final float x = v0.x - v1.x;
    final float y = v0.y - v1.y;
    final float z = v0.z - v1.z;
    final float w = v0.w - v1.w;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   */

  public static @Nonnull VectorM4F subtractInPlace(
    final @Nonnull VectorM4F v0,
    final @Nonnull VectorM4F v1)
  {
    return VectorM4F.subtract(v0, v1, v0);
  }

  public float x = 0.0f;
  public float y = 0.0f;
  public float z = 0.0f;

  public float w = 1.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorM4F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM4F(
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

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM4F(
    final @Nonnull VectorReadable4F v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
    this.w = v.getWF();
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
    final @Nonnull VectorM4F other = (VectorM4F) obj;
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
    builder.append("[VectorM4F ");
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
