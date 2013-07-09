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

import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A two-dimensional mutable vector type with single precision elements.
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

@NotThreadSafe public final class VectorM2FT<A> implements
  VectorReadable2FT<A>
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

  public static @Nonnull <A> VectorM2FT<A> absolute(
    final @Nonnull VectorReadable2FT<A> v,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
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

  public static @Nonnull <A> VectorM2FT<A> absoluteInPlace(
    final @Nonnull VectorM2FT<A> v)
  {
    return VectorM2FT.absolute(v, v);
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

  public static @Nonnull <A> VectorM2FT<A> add(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
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

  public static @Nonnull <A> VectorM2FT<A> addInPlace(
    final @Nonnull VectorM2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1)
  {
    return VectorM2FT.add(v0, v1, v0);
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

  public static @Nonnull <A> VectorM2FT<A> addScaled(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1,
    final double r,
    final @Nonnull VectorM2FT<A> out)
  {
    final double x = v0.getXF() + (v1.getXF() * r);
    final double y = v0.getYF() + (v1.getYF() * r);
    out.x = (float) x;
    out.y = (float) y;
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

  public static @Nonnull <A> VectorM2FT<A> addScaledInPlace(
    final @Nonnull VectorM2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1,
    final double r)
  {
    return VectorM2FT.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors <code>qa</code> and <code>qb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
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
    final @Nonnull AlmostEqualFloat.ContextRelative context,
    final @Nonnull VectorReadable2FT<A> qa,
    final @Nonnull VectorReadable2FT<A> qb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, qa.getXF(), qb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, qa.getYF(), qb.getYF());
    return xs && ys;
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

  public static <A> double angle(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1)
  {
    final double m0 = VectorM2FT.magnitude(v0);
    final double m1 = VectorM2FT.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, VectorM2FT.dotProduct(v0, v1)), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
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

  public static @Nonnull <A> VectorM2FT<A> clamp(
    final @Nonnull VectorReadable2FT<A> v,
    final float minimum,
    final float maximum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
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

  public static @Nonnull <A> VectorM2FT<A> clampByVector(
    final @Nonnull VectorReadable2FT<A> v,
    final @Nonnull VectorReadable2FT<A> minimum,
    final @Nonnull VectorReadable2FT<A> maximum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
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

  public static @Nonnull <A> VectorM2FT<A> clampByVectorInPlace(
    final @Nonnull VectorM2FT<A> v,
    final @Nonnull VectorReadable2FT<A> minimum,
    final @Nonnull VectorReadable2FT<A> maximum)
  {
    return VectorM2FT.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM2FT<A> clampInPlace(
    final @Nonnull VectorM2FT<A> v,
    final float minimum,
    final float maximum)
  {
    return VectorM2FT.clamp(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM2FT<A> clampMaximum(
    final @Nonnull VectorReadable2FT<A> v,
    final float maximum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
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

  public static @Nonnull <A> VectorM2FT<A> clampMaximumByVector(
    final @Nonnull VectorReadable2FT<A> v,
    final @Nonnull VectorReadable2FT<A> maximum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
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

  public static @Nonnull <A> VectorM2FT<A> clampMaximumByVectorInPlace(
    final @Nonnull VectorM2FT<A> v,
    final @Nonnull VectorReadable2FT<A> maximum)
  {
    return VectorM2FT.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull <A> VectorM2FT<A> clampMaximumInPlace(
    final @Nonnull VectorM2FT<A> v,
    final float maximum)
  {
    return VectorM2FT.clampMaximum(v, maximum, v);
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

  public static @Nonnull <A> VectorM2FT<A> clampMinimum(
    final @Nonnull VectorReadable2FT<A> v,
    final float minimum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
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

  public static @Nonnull <A> VectorM2FT<A> clampMinimumByVector(
    final @Nonnull VectorReadable2FT<A> v,
    final @Nonnull VectorReadable2FT<A> minimum,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
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

  public static @Nonnull <A> VectorM2FT<A> clampMinimumByVectorInPlace(
    final @Nonnull VectorM2FT<A> v,
    final @Nonnull VectorReadable2FT<A> minimum)
  {
    return VectorM2FT.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull <A> VectorM2FT<A> clampMinimumInPlace(
    final @Nonnull VectorM2FT<A> v,
    final float minimum)
  {
    return VectorM2FT.clampMinimum(v, minimum, v);
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

  public static @Nonnull <A> VectorM2FT<A> copy(
    final @Nonnull VectorReadable2FT<A> input,
    final @Nonnull VectorM2FT<A> output)
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

  public static <A> double distance(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1)
  {
    final @Nonnull VectorM2FT<A> vr = new VectorM2FT<A>();
    return VectorM2FT.magnitude(VectorM2FT.subtract(v0, v1, vr));
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

  public static <A> float dotProduct(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
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

  public static @Nonnull <A> VectorM2FT<A> interpolateLinear(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1,
    final double alpha,
    final @Nonnull VectorM2FT<A> r)
  {
    final @Nonnull VectorM2FT<A> w0 = new VectorM2FT<A>();
    final @Nonnull VectorM2FT<A> w1 = new VectorM2FT<A>();

    VectorM2FT.scale(v0, 1.0f - alpha, w0);
    VectorM2FT.scale(v1, alpha, w1);

    return VectorM2FT.add(w0, w1, r);
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
    final @Nonnull VectorReadable2FT<A> v)
  {
    return Math.sqrt(VectorM2FT.magnitudeSquared(v));
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
    final @Nonnull VectorReadable2FT<A> v)
  {
    return VectorM2FT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorM2FT<A> normalize(
    final @Nonnull VectorReadable2FT<A> v,
    final @Nonnull VectorM2FT<A> out)
  {
    final double m = VectorM2FT.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM2FT.scale(v, reciprocal, out);
    }
    out.x = v.getXF();
    out.y = v.getYF();
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

  public static @Nonnull <A> VectorM2FT<A> normalizeInPlace(
    final @Nonnull VectorM2FT<A> v)
  {
    return VectorM2FT.normalize(v, v);
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
    Pair<VectorM2FT<A>, VectorM2FT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable2FT<A> v0,
      final @Nonnull VectorReadable2FT<A> v1)
  {
    final VectorM2FT<A> v0n = new VectorM2FT<A>();
    final VectorM2FT<A> vr = new VectorM2FT<A>();
    final VectorM2FT<A> vp = new VectorM2FT<A>();

    VectorM2FT.normalize(v0, v0n);
    VectorM2FT.scale(v0n, VectorM2FT.dotProduct(v1, v0n), vp);
    VectorM2FT.normalizeInPlace(VectorM2FT.subtract(v1, vp, vr));
    return new Pair<VectorM2FT<A>, VectorM2FT<A>>(v0n, vr);
  }

  /**
   * <p>
   * Orthonormalize and the vectors <code>v0</code> and <code>v1</code>.
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   * 
   * 
   */

  public static <A> void orthoNormalizeInPlace(
    final @Nonnull VectorM2FT<A> v0,
    final @Nonnull VectorM2FT<A> v1)
  {
    final VectorM2FT<A> projection = new VectorM2FT<A>();

    VectorM2FT.normalizeInPlace(v0);
    VectorM2FT.scale(v0, VectorM2FT.dotProduct(v1, v0), projection);
    VectorM2FT.subtractInPlace(v1, projection);
    VectorM2FT.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorM2FT<A> projection(
    final @Nonnull VectorReadable2FT<A> p,
    final @Nonnull VectorReadable2FT<A> q,
    final @Nonnull VectorM2FT<A> r)
  {
    final double dot = VectorM2FT.dotProduct(p, q);
    final double qms = VectorM2FT.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM2FT.scale(p, s, r);
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

  public static @Nonnull <A> VectorM2FT<A> scale(
    final @Nonnull VectorReadable2FT<A> v,
    final double r,
    final @Nonnull VectorM2FT<A> out)
  {
    final double x = v.getXF() * r;
    final double y = v.getYF() * r;
    out.x = (float) x;
    out.y = (float) y;
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

  public static @Nonnull <A> VectorM2FT<A> scaleInPlace(
    final @Nonnull VectorM2FT<A> v,
    final double r)
  {
    return VectorM2FT.scale(v, r, v);
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

  public static @Nonnull <A> VectorM2FT<A> subtract(
    final @Nonnull VectorReadable2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1,
    final @Nonnull VectorM2FT<A> out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
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

  public static @Nonnull <A> VectorM2FT<A> subtractInPlace(
    final @Nonnull VectorM2FT<A> v0,
    final @Nonnull VectorReadable2FT<A> v1)
  {
    return VectorM2FT.subtract(v0, v1, v0);
  }

  private float x = 0.0f;
  private float y = 0.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public VectorM2FT()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM2FT(
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

  public VectorM2FT(
    final @Nonnull VectorReadable2FT<A> v)
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

    @SuppressWarnings("unchecked") final @Nonnull VectorM2FT<A> other =
      (VectorM2FT<A>) obj;
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

  /**
   * Set the value of the <code>x</code> component to the given value.
   */

  public void setXF(
    final float x)
  {
    this.x = x;
  }

  /**
   * Set the value of the <code>y</code> component to the given value.
   */

  public void setYF(
    final float y)
  {
    this.y = y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2FT ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
