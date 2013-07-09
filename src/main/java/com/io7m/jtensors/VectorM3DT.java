/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A three-dimensional mutable vector type with double precision elements.
 * </p>
 * <p>
 * The intention of the type parameter <code>A</code> is to be used as a
 * "phantom type" or compile-time tag to statically distinguish between
 * semantically distinct vectors.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

@NotThreadSafe public final class VectorM3DT<A> implements
  VectorReadable3DT<A>
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

  public static @Nonnull <A> VectorM3DT<A> absolute(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.abs(v.getXD());
    final double y = Math.abs(v.getYD());
    final double z = Math.abs(v.getZD());
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

  public static @Nonnull <A> VectorM3DT<A> absoluteInPlace(
    final @Nonnull VectorM3DT<A> v)
  {
    return VectorM3DT.absolute(v, v);
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

  public static @Nonnull <A> VectorM3DT<A> add(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = v0.getXD() + v1.getXD();
    final double y = v0.getYD() + v1.getYD();
    final double z = v0.getZD() + v1.getZD();
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

  public static @Nonnull <A> VectorM3DT<A> addInPlace(
    final @Nonnull VectorM3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    return VectorM3DT.add(v0, v1, v0);
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

  public static @Nonnull <A> VectorM3DT<A> addScaled(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final double r,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = v0.getXD() + (v1.getXD() * r);
    final double y = v0.getYD() + (v1.getYD() * r);
    final double z = v0.getZD() + (v1.getZD() * r);
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

  public static @Nonnull <A> VectorM3DT<A> addScaledInPlace(
    final @Nonnull VectorM3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final double r)
  {
    return VectorM3DT.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors <code>qa</code> and <code>qb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * 
   * @param context
   *          The equality context
   * @param qa
   *          The left input vector
   * @param qb
   *          The right input vector
   * 
   */

  public static <A> boolean almostEqual(
    final @Nonnull ContextRelative context,
    final @Nonnull VectorReadable3DT<A> qa,
    final @Nonnull VectorReadable3DT<A> qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, qa.getZD(), qb.getZD());
    return xs && ys && zs;
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

  public static @Nonnull <A> VectorM3DT<A> clamp(
    final @Nonnull VectorReadable3DT<A> v,
    final double minimum,
    final double maximum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
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

  public static @Nonnull <A> VectorM3DT<A> clampByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum,
    final @Nonnull VectorReadable3DT<A> maximum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
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

  public static @Nonnull <A> VectorM3DT<A> clampByVectorInPlace(
    final @Nonnull VectorM3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum,
    final @Nonnull VectorReadable3DT<A> maximum)
  {
    return VectorM3DT.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM3DT<A> clampInPlace(
    final @Nonnull VectorM3DT<A> v,
    final double minimum,
    final double maximum)
  {
    return VectorM3DT.clamp(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM3DT<A> clampMaximum(
    final @Nonnull VectorReadable3DT<A> v,
    final double maximum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
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

  public static @Nonnull <A> VectorM3DT<A> clampMaximumByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> maximum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
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

  public static @Nonnull <A> VectorM3DT<A> clampMaximumByVectorInPlace(
    final @Nonnull VectorM3DT<A> v,
    final @Nonnull VectorReadable3DT<A> maximum)
  {
    return VectorM3DT.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull <A> VectorM3DT<A> clampMaximumInPlace(
    final @Nonnull VectorM3DT<A> v,
    final double maximum)
  {
    return VectorM3DT.clampMaximum(v, maximum, v);
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

  public static @Nonnull <A> VectorM3DT<A> clampMinimum(
    final @Nonnull VectorReadable3DT<A> v,
    final double minimum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
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

  public static @Nonnull <A> VectorM3DT<A> clampMinimumByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
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

  public static @Nonnull <A> VectorM3DT<A> clampMinimumByVectorInPlace(
    final @Nonnull VectorM3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum)
  {
    return VectorM3DT.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull <A> VectorM3DT<A> clampMinimumInPlace(
    final @Nonnull VectorM3DT<A> v,
    final double minimum)
  {
    return VectorM3DT.clampMinimum(v, minimum, v);
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

  public static @Nonnull <A> VectorM3DT<A> copy(
    final @Nonnull VectorReadable3DT<A> input,
    final @Nonnull VectorM3DT<A> output)
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

  public static @Nonnull <A> VectorM3DT<A> crossProduct(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = (v0.getYD() * v1.getZD()) - (v0.getZD() * v1.getYD());
    final double y = (v0.getZD() * v1.getXD()) - (v0.getXD() * v1.getZD());
    final double z = (v0.getXD() * v1.getYD()) - (v0.getYD() * v1.getXD());
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

  public static <A> double distance(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    final @Nonnull VectorM3DT<A> vr = new VectorM3DT<A>();
    return VectorM3DT.magnitude(VectorM3DT.subtract(v0, v1, vr));
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

  public static <A> double dotProduct(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
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

  public static @Nonnull <A> VectorM3DT<A> interpolateLinear(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final double alpha,
    final @Nonnull VectorM3DT<A> r)
  {
    final @Nonnull VectorM3DT<A> w0 = new VectorM3DT<A>();
    final @Nonnull VectorM3DT<A> w1 = new VectorM3DT<A>();

    VectorM3DT.scale(v0, 1.0 - alpha, w0);
    VectorM3DT.scale(v1, alpha, w1);

    return VectorM3DT.add(w0, w1, r);
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

  public static <A> double magnitude(
    final @Nonnull VectorReadable3DT<A> v)
  {
    return Math.sqrt(VectorM3DT.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static <A> double magnitudeSquared(
    final @Nonnull VectorReadable3DT<A> v)
  {
    return VectorM3DT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorM3DT<A> normalize(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorM3DT<A> out)
  {
    final double m = VectorM3DT.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM3DT.scale(v, reciprocal, out);
    }
    out.x = v.getXD();
    out.y = v.getYD();
    out.z = v.getZD();
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

  public static @Nonnull <A> VectorM3DT<A> normalizeInPlace(
    final @Nonnull VectorM3DT<A> v)
  {
    return VectorM3DT.normalize(v, v);
  }

  /**
   * <p>
   * Orthonormalize and return the vectors <code>v0</code> and <code>v1</code>
   * .
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   * 
   * @return A pair <code>(v0, v1)</code>, orthonormalized.
   * 
   * 
   */

  public static @Nonnull
    <A>
    Pair<VectorM3DT<A>, VectorM3DT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable3DT<A> v0,
      final @Nonnull VectorReadable3DT<A> v1)
  {
    final VectorM3DT<A> v0n = new VectorM3DT<A>();
    final VectorM3DT<A> vr = new VectorM3DT<A>();
    final VectorM3DT<A> vp = new VectorM3DT<A>();

    VectorM3DT.normalize(v0, v0n);
    VectorM3DT.scale(v0n, VectorM3DT.dotProduct(v1, v0n), vp);
    VectorM3DT.normalizeInPlace(VectorM3DT.subtract(v1, vp, vr));
    return new Pair<VectorM3DT<A>, VectorM3DT<A>>(v0n, vr);
  }

  /**
   * <p>
   * Orthonormalize and the vectors <code>v0</code> and <code>v1</code>.
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   */

  public static <A> void orthoNormalizeInPlace(
    final @Nonnull VectorM3DT<A> v0,
    final @Nonnull VectorM3DT<A> v1)
  {
    final VectorM3DT<A> projection = new VectorM3DT<A>();

    VectorM3DT.normalizeInPlace(v0);
    VectorM3DT.scale(v0, VectorM3DT.dotProduct(v1, v0), projection);
    VectorM3DT.subtractInPlace(v1, projection);
    VectorM3DT.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorM3DT<A> projection(
    final @Nonnull VectorReadable3DT<A> p,
    final @Nonnull VectorReadable3DT<A> q,
    final @Nonnull VectorM3DT<A> r)
  {
    final double dot = VectorM3DT.dotProduct(p, q);
    final double qms = VectorM3DT.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM3DT.scale(p, s, r);
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

  public static @Nonnull <A> VectorM3DT<A> scale(
    final @Nonnull VectorReadable3DT<A> v,
    final double r,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = v.getXD() * r;
    final double y = v.getYD() * r;
    final double z = v.getZD() * r;
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

  public static @Nonnull <A> VectorM3DT<A> scaleInPlace(
    final @Nonnull VectorM3DT<A> v,
    final double r)
  {
    return VectorM3DT.scale(v, r, v);
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

  public static @Nonnull <A> VectorM3DT<A> subtract(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final @Nonnull VectorM3DT<A> out)
  {
    final double x = v0.getXD() - v1.getXD();
    final double y = v0.getYD() - v1.getYD();
    final double z = v0.getZD() - v1.getZD();
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

  public static @Nonnull <A> VectorM3DT<A> subtractInPlace(
    final @Nonnull VectorM3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    return VectorM3DT.subtract(v0, v1, v0);
  }

  private double x = 0.0;
  private double y = 0.0;
  private double z = 0.0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorM3DT()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM3DT(
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

  public VectorM3DT(
    final @Nonnull VectorReadable3DT<A> v)
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

    @SuppressWarnings("unchecked") final @Nonnull VectorM3DT<A> other =
      (VectorM3DT<A>) obj;
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

  /**
   * Set the value of the <code>x</code> component to the given value.
   */

  public void setXD(
    final double x)
  {
    this.x = x;
  }

  /**
   * Set the value of the <code>y</code> component to the given value.
   */

  public void setYD(
    final double y)
  {
    this.y = y;
  }

  /**
   * Set the value of the <code>z</code> component to the given value.
   */

  public void setZD(
    final double z)
  {
    this.z = z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM3DT ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
