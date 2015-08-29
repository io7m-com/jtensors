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
 * A two-dimensional mutable vector type with double precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM2D implements VectorReadable2D
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

  public static @Nonnull VectorM2D absolute(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.abs(v.x);
    final double y = Math.abs(v.y);
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

  public static @Nonnull VectorM2D absoluteInPlace(
    final @Nonnull VectorM2D v)
  {
    return VectorM2D.absolute(v, v);
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

  public static @Nonnull VectorM2D add(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1,
    final @Nonnull VectorM2D out)
  {
    final double x = v0.x + v1.x;
    final double y = v0.y + v1.y;
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

  public static @Nonnull VectorM2D addInPlace(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    return VectorM2D.add(v0, v1, v0);
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

  public static @Nonnull VectorM2D addScaled(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1,
    final double r,
    final @Nonnull VectorM2D out)
  {
    final double x = v0.x + (v1.x * r);
    final double y = v0.y + (v1.y * r);
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

  public static @Nonnull VectorM2D addScaledInPlace(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1,
    final double r)
  {
    return VectorM2D.addScaled(v0, v1, r, v0);
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

  public static double angle(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    final double m0 = VectorM2D.magnitude(v0);
    final double m1 = VectorM2D.magnitude(v1);
    return Math.acos(VectorM2D.dotProduct(v0, v1) / (m0 * m1));
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

  public static double angleDegrees(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    return Math.toDegrees(VectorM2D.angle(v0, v1));
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
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(v0.x, v1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(v0.y, v1.y);
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

  public static @Nonnull VectorM2D clamp(
    final @Nonnull VectorM2D v,
    final double minimum,
    final double maximum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.min(Math.max(v.x, minimum), maximum);
    final double y = Math.min(Math.max(v.y, minimum), maximum);
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

  public static @Nonnull VectorM2D clampByVector(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D minimum,
    final @Nonnull VectorM2D maximum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final double y = Math.min(Math.max(v.y, minimum.y), maximum.y);
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

  public static @Nonnull VectorM2D clampByVectorInPlace(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D minimum,
    final @Nonnull VectorM2D maximum)
  {
    return VectorM2D.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull VectorM2D clampInPlace(
    final @Nonnull VectorM2D v,
    final double minimum,
    final double maximum)
  {
    return VectorM2D.clamp(v, minimum, maximum, v);
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

  public static @Nonnull VectorM2D clampMaximum(
    final @Nonnull VectorM2D v,
    final double maximum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.min(v.x, maximum);
    final double y = Math.min(v.y, maximum);
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

  public static @Nonnull VectorM2D clampMaximumByVector(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D maximum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.min(v.x, maximum.x);
    final double y = Math.min(v.y, maximum.y);
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

  public static @Nonnull VectorM2D clampMaximumByVectorInPlace(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D maximum)
  {
    return VectorM2D.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull VectorM2D clampMaximumInPlace(
    final @Nonnull VectorM2D v,
    final double maximum)
  {
    return VectorM2D.clampMaximum(v, maximum, v);
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

  public static @Nonnull VectorM2D clampMinimum(
    final @Nonnull VectorM2D v,
    final double minimum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.max(v.x, minimum);
    final double y = Math.max(v.y, minimum);
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

  public static @Nonnull VectorM2D clampMinimumByVector(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D minimum,
    final @Nonnull VectorM2D out)
  {
    final double x = Math.max(v.x, minimum.x);
    final double y = Math.max(v.y, minimum.y);
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

  public static @Nonnull VectorM2D clampMinimumByVectorInPlace(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D minimum)
  {
    return VectorM2D.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull VectorM2D clampMinimumInPlace(
    final @Nonnull VectorM2D v,
    final double minimum)
  {
    return VectorM2D.clampMinimum(v, minimum, v);
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

  public static @Nonnull VectorM2D copy(
    final @Nonnull VectorReadable2D input,
    final @Nonnull VectorM2D output)
  {
    output.x = input.getXD();
    output.y = input.getYD();
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

  public static double distance(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    final @Nonnull VectorM2D vr = new VectorM2D();
    return VectorM2D.magnitude(VectorM2D.subtract(v0, v1, vr));
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
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    final double x = v0.x * v1.x;
    final double y = v0.y * v1.y;
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

  public static @Nonnull VectorM2D interpolateLinear(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1,
    final double alpha,
    final @Nonnull VectorM2D r)
  {
    final @Nonnull VectorM2D w0 = new VectorM2D();
    final @Nonnull VectorM2D w1 = new VectorM2D();

    VectorM2D.scale(v0, 1.0 - alpha, w0);
    VectorM2D.scale(v1, alpha, w1);

    return VectorM2D.add(w0, w1, r);
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
    final @Nonnull VectorM2D v)
  {
    return Math.sqrt(VectorM2D.magnitudeSquared(v));
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
    final @Nonnull VectorM2D v)
  {
    return VectorM2D.dotProduct(v, v);
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

  public static @Nonnull VectorM2D normalize(
    final @Nonnull VectorM2D v,
    final @Nonnull VectorM2D out)
  {
    final double m = VectorM2D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM2D.scale(v, reciprocal, out);
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

  public static @Nonnull VectorM2D normalizeInPlace(
    final @Nonnull VectorM2D v)
  {
    return VectorM2D.normalize(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorM2D projection(
    final @Nonnull VectorM2D p,
    final @Nonnull VectorM2D q,
    final @Nonnull VectorM2D r)
  {
    final double dot = VectorM2D.dotProduct(p, q);
    final double qms = VectorM2D.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM2D.scale(p, s, r);
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

  public static @Nonnull VectorM2D scale(
    final @Nonnull VectorM2D v,
    final double r,
    final @Nonnull VectorM2D out)
  {
    final double x = v.x * r;
    final double y = v.y * r;
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

  public static @Nonnull VectorM2D scaleInPlace(
    final @Nonnull VectorM2D v,
    final double r)
  {
    return VectorM2D.scale(v, r, v);
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

  public static @Nonnull VectorM2D subtract(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1,
    final @Nonnull VectorM2D out)
  {
    final double x = v0.x - v1.x;
    final double y = v0.y - v1.y;
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

  public static @Nonnull VectorM2D subtractInPlace(
    final @Nonnull VectorM2D v0,
    final @Nonnull VectorM2D v1)
  {
    return VectorM2D.subtract(v0, v1, v0);
  }

  public double x = 0.0;

  public double y = 0.0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public VectorM2D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM2D(
    final double x,
    final double y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM2D(
    final @Nonnull VectorReadable2D v)
  {
    this.x = v.getXD();
    this.y = v.getYD();
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
    final @Nonnull VectorM2D other = (VectorM2D) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
