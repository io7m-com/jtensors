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

/**
 * A three-dimensional mutable vector type with double precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM3D implements VectorReadable3D
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
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull VectorM3D absolute(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.abs(v.x);
    final double y = Math.abs(v.y);
    final double z = Math.abs(v.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * modifying the vector in-place.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull VectorM3D absoluteInPlace(
    final @Nonnull VectorM3D v)
  {
    return VectorM3D.absolute(v, v);
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static @Nonnull VectorM3D add(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final @Nonnull VectorM3D out)
  {
    final double x = v0.x + v1.x;
    final double y = v0.y + v1.y;
    final double z = v0.z + v1.z;
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static @Nonnull VectorM3D addInPlace(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1)
  {
    return VectorM3D.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static @Nonnull VectorM3D addScaled(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final double r,
    final @Nonnull VectorM3D out)
  {
    final double x = v0.x + (v1.x * r);
    final double y = v0.y + (v1.y * r);
    final double z = v0.z + (v1.z * r);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static @Nonnull VectorM3D addScaledInPlace(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final double r)
  {
    return VectorM3D.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the elements of the two vectors <code>v0</code>
   * and <code>v1</code> are approximately equal.
   * 
   * @see ApproximatelyEqualDouble
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code>, within an appropriate degree of error for double
   *         precision floating point values
   */

  public static boolean approximatelyEqual(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1)
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

  public static @Nonnull VectorM3D clamp(
    final @Nonnull VectorM3D v,
    final double minimum,
    final double maximum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.min(Math.max(v.x, minimum), maximum);
    final double y = Math.min(Math.max(v.y, minimum), maximum);
    final double z = Math.min(Math.max(v.z, minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull VectorM3D clampByVector(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D minimum,
    final @Nonnull VectorM3D maximum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final double y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final double z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull VectorM3D clampByVectorInPlace(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D minimum,
    final @Nonnull VectorM3D maximum)
  {
    return VectorM3D.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull VectorM3D clampInPlace(
    final @Nonnull VectorM3D v,
    final double minimum,
    final double maximum)
  {
    return VectorM3D.clamp(v, minimum, maximum, v);
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

  public static @Nonnull VectorM3D clampMaximum(
    final @Nonnull VectorM3D v,
    final double maximum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.min(v.x, maximum);
    final double y = Math.min(v.y, maximum);
    final double z = Math.min(v.z, maximum);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull VectorM3D clampMaximumByVector(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D maximum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.min(v.x, maximum.x);
    final double y = Math.min(v.y, maximum.y);
    final double z = Math.min(v.z, maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull VectorM3D clampMaximumByVectorInPlace(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D maximum)
  {
    return VectorM3D.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull VectorM3D clampMaximumInPlace(
    final @Nonnull VectorM3D v,
    final double maximum)
  {
    return VectorM3D.clampMaximum(v, maximum, v);
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

  public static @Nonnull VectorM3D clampMinimum(
    final @Nonnull VectorM3D v,
    final double minimum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.max(v.x, minimum);
    final double y = Math.max(v.y, minimum);
    final double z = Math.max(v.z, minimum);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static @Nonnull VectorM3D clampMinimumByVector(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D minimum,
    final @Nonnull VectorM3D out)
  {
    final double x = Math.max(v.x, minimum.x);
    final double y = Math.max(v.y, minimum.y);
    final double z = Math.max(v.z, minimum.z);
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM3D clampMinimumByVectorInPlace(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D minimum)
  {
    return VectorM3D.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull VectorM3D clampMinimumInPlace(
    final @Nonnull VectorM3D v,
    final double minimum)
  {
    return VectorM3D.clampMinimum(v, minimum, v);
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

  public static @Nonnull VectorM3D copy(
    final @Nonnull VectorReadable3D input,
    final @Nonnull VectorM3D output)
  {
    output.x = input.getXD();
    output.y = input.getYD();
    output.z = input.getZD();
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

  public static @Nonnull VectorM3D crossProduct(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final @Nonnull VectorM3D out)
  {
    final double x = (v0.y * v1.z) - (v0.z * v1.y);
    final double y = (v0.z * v1.x) - (v0.x * v1.z);
    final double z = (v0.x * v1.y) - (v0.y * v1.x);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
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
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1)
  {
    final @Nonnull VectorM3D vr = new VectorM3D();
    return VectorM3D.magnitude(VectorM3D.subtract(v0, v1, vr));
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
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1)
  {
    final double x = v0.x * v1.x;
    final double y = v0.y * v1.y;
    final double z = v0.z * v1.z;
    return x + y + z;
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

  public static @Nonnull VectorM3D interpolateLinear(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final double alpha,
    final @Nonnull VectorM3D r)
  {
    final @Nonnull VectorM3D w0 = new VectorM3D();
    final @Nonnull VectorM3D w1 = new VectorM3D();

    VectorM3D.scale(v0, 1.0 - alpha, w0);
    VectorM3D.scale(v1, alpha, w1);

    return VectorM3D.add(w0, w1, r);
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
    final @Nonnull VectorM3D v)
  {
    return Math.sqrt(VectorM3D.magnitudeSquared(v));
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
    final @Nonnull VectorM3D v)
  {
    return VectorM3D.dotProduct(v, v);
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

  public static @Nonnull VectorM3D normalize(
    final @Nonnull VectorM3D v,
    final @Nonnull VectorM3D out)
  {
    final double m = VectorM3D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM3D.scale(v, reciprocal, out);
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
   *          The input vector
   * 
   * @return v
   */

  public static @Nonnull VectorM3D normalizeInPlace(
    final @Nonnull VectorM3D v)
  {
    return VectorM3D.normalize(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorM3D projection(
    final @Nonnull VectorM3D p,
    final @Nonnull VectorM3D q,
    final @Nonnull VectorM3D r)
  {
    final double dot = VectorM3D.dotProduct(p, q);
    final double qms = VectorM3D.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM3D.scale(p, s, r);
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
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static @Nonnull VectorM3D scale(
    final @Nonnull VectorM3D v,
    final double r,
    final @Nonnull VectorM3D out)
  {
    final double x = v.x * r;
    final double y = v.y * r;
    final double z = v.z * r;
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static @Nonnull VectorM3D scaleInPlace(
    final @Nonnull VectorM3D v,
    final double r)
  {
    return VectorM3D.scale(v, r, v);
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull VectorM3D subtract(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1,
    final @Nonnull VectorM3D out)
  {
    final double x = v0.x - v1.x;
    final double y = v0.y - v1.y;
    final double z = v0.z - v1.z;
    out.x = x;
    out.y = y;
    out.z = z;
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull VectorM3D subtractInPlace(
    final @Nonnull VectorM3D v0,
    final @Nonnull VectorM3D v1)
  {
    return VectorM3D.subtract(v0, v1, v0);
  }

  public double x = 0.0;
  public double y = 0.0;
  public double z = 0.0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorM3D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM3D(
    final double x,
    final double y,
    final double z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM3D(
    final @Nonnull VectorReadable3D v)
  {
    this.x = v.getXD();
    this.y = v.getYD();
    this.z = v.getZD();
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
    final @Nonnull VectorM3D other = (VectorM3D) obj;
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
    builder.append("[VectorM3D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
