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

import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A four-dimensional mutable vector type with single precision elements.
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

@NotThreadSafe public final class VectorM4FT<A> implements
  VectorReadable4FT<A>
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

  public static @Nonnull <A> VectorM4FT<A> absolute(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    final float z = Math.abs(v.getZF());
    final float w = Math.abs(v.getWF());
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

  public static @Nonnull <A> VectorM4FT<A> absoluteInPlace(
    final @Nonnull VectorM4FT<A> v)
  {
    return VectorM4FT.absolute(v, v);
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

  public static @Nonnull <A> VectorM4FT<A> add(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    final float z = v0.getZF() + v1.getZF();
    final float w = v0.getWF() + v1.getWF();
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

  public static @Nonnull <A> VectorM4FT<A> addInPlace(
    final @Nonnull VectorM4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    return VectorM4FT.add(v0, v1, v0);
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

  public static @Nonnull <A> VectorM4FT<A> addScaled(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final double r,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = (float) (v0.getXF() + (v1.getXF() * r));
    final float y = (float) (v0.getYF() + (v1.getYF() * r));
    final float z = (float) (v0.getZF() + (v1.getZF() * r));
    final float w = (float) (v0.getWF() + (v1.getWF() * r));
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

  public static @Nonnull <A> VectorM4FT<A> addScaledInPlace(
    final @Nonnull VectorM4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final double r)
  {
    return VectorM4FT.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors <code>va</code> and <code>vb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
   * 
   * @param context
   *          The equality context
   * @param va
   *          The left input vector
   * @param vb
   *          The right input vector
   * 
   */

  public static <A> boolean almostEqual(
    final @Nonnull AlmostEqualFloat.ContextRelative context,
    final @Nonnull VectorReadable4FT<A> va,
    final @Nonnull VectorReadable4FT<A> vb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, va.getXF(), vb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, va.getYF(), vb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, va.getZF(), vb.getZF());
    final boolean ws =
      AlmostEqualFloat.almostEqual(context, va.getWF(), vb.getWF());
    return xs && ys && zs && ws;
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

  public static @Nonnull <A> VectorM4FT<A> clamp(
    final @Nonnull VectorReadable4FT<A> v,
    final double minimum,
    final double maximum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = (float) Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = (float) Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = (float) Math.min(Math.max(v.getZF(), minimum), maximum);
    final float w = (float) Math.min(Math.max(v.getWF(), minimum), maximum);
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

  public static @Nonnull <A> VectorM4FT<A> clampByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum,
    final @Nonnull VectorReadable4FT<A> maximum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
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

  public static @Nonnull <A> VectorM4FT<A> clampByVectorInPlace(
    final @Nonnull VectorM4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum,
    final @Nonnull VectorReadable4FT<A> maximum)
  {
    return VectorM4FT.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM4FT<A> clampInPlace(
    final @Nonnull VectorM4FT<A> v,
    final float minimum,
    final float maximum)
  {
    return VectorM4FT.clamp(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM4FT<A> clampMaximum(
    final @Nonnull VectorReadable4FT<A> v,
    final float maximum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
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

  public static @Nonnull <A> VectorM4FT<A> clampMaximumByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> maximum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
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

  public static @Nonnull <A> VectorM4FT<A> clampMaximumByVectorInPlace(
    final @Nonnull VectorM4FT<A> v,
    final @Nonnull VectorReadable4FT<A> maximum)
  {
    return VectorM4FT.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull <A> VectorM4FT<A> clampMaximumInPlace(
    final @Nonnull VectorM4FT<A> v,
    final float maximum)
  {
    return VectorM4FT.clampMaximum(v, maximum, v);
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

  public static @Nonnull <A> VectorM4FT<A> clampMinimum(
    final @Nonnull VectorReadable4FT<A> v,
    final float minimum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
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

  public static @Nonnull <A> VectorM4FT<A> clampMinimumByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
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

  public static @Nonnull <A> VectorM4FT<A> clampMinimumByVectorInPlace(
    final @Nonnull VectorM4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum)
  {
    return VectorM4FT.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull <A> VectorM4FT<A> clampMinimumInPlace(
    final @Nonnull VectorM4FT<A> v,
    final float minimum)
  {
    return VectorM4FT.clampMinimum(v, minimum, v);
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

  public static @Nonnull <A> VectorM4FT<A> copy(
    final @Nonnull VectorReadable4FT<A> input,
    final @Nonnull VectorM4FT<A> output)
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

  public static <A> double distance(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    final @Nonnull VectorM4FT<A> vr = new VectorM4FT<A>();
    return VectorM4FT.magnitude(VectorM4FT.subtract(v0, v1, vr));
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
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    final double x = v0.getXF() * v1.getXF();
    final double y = v0.getYF() * v1.getYF();
    final double z = v0.getZF() * v1.getZF();
    final double w = v0.getWF() * v1.getWF();
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

  public static @Nonnull <A> VectorM4FT<A> interpolateLinear(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final double alpha,
    final @Nonnull VectorM4FT<A> r)
  {
    final @Nonnull VectorM4FT<A> w0 = new VectorM4FT<A>();
    final @Nonnull VectorM4FT<A> w1 = new VectorM4FT<A>();

    VectorM4FT.scale(v0, 1.0f - alpha, w0);
    VectorM4FT.scale(v1, alpha, w1);

    return VectorM4FT.add(w0, w1, r);
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
    final @Nonnull VectorReadable4FT<A> v)
  {
    return Math.sqrt(VectorM4FT.magnitudeSquared(v));
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
    final @Nonnull VectorReadable4FT<A> v)
  {
    return VectorM4FT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorM4FT<A> normalize(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorM4FT<A> out)
  {
    final double m = VectorM4FT.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM4FT.scale(v, reciprocal, out);
    }
    out.x = v.getXF();
    out.y = v.getYF();
    out.z = v.getZF();
    out.w = v.getWF();
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

  public static @Nonnull <A> VectorM4FT<A> normalizeInPlace(
    final @Nonnull VectorM4FT<A> v)
  {
    return VectorM4FT.normalize(v, v);
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
    Pair<VectorM4FT<A>, VectorM4FT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable4FT<A> v0,
      final @Nonnull VectorReadable4FT<A> v1)
  {
    final VectorM4FT<A> v0n = new VectorM4FT<A>();
    final VectorM4FT<A> vr = new VectorM4FT<A>();
    final VectorM4FT<A> vp = new VectorM4FT<A>();

    VectorM4FT.normalize(v0, v0n);
    VectorM4FT.scale(v0n, VectorM4FT.dotProduct(v1, v0n), vp);
    VectorM4FT.normalizeInPlace(VectorM4FT.subtract(v1, vp, vr));
    return new Pair<VectorM4FT<A>, VectorM4FT<A>>(v0n, vr);
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
    final @Nonnull VectorM4FT<A> v0,
    final @Nonnull VectorM4FT<A> v1)
  {
    final VectorM4FT<A> projection = new VectorM4FT<A>();

    VectorM4FT.normalizeInPlace(v0);
    VectorM4FT.scale(v0, VectorM4FT.dotProduct(v1, v0), projection);
    VectorM4FT.subtractInPlace(v1, projection);
    VectorM4FT.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorM4FT<A> projection(
    final @Nonnull VectorReadable4FT<A> p,
    final @Nonnull VectorReadable4FT<A> q,
    final @Nonnull VectorM4FT<A> r)
  {
    final double dot = VectorM4FT.dotProduct(p, q);
    final double qms = VectorM4FT.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM4FT.scale(p, s, r);
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

  public static @Nonnull <A> VectorM4FT<A> scale(
    final @Nonnull VectorReadable4FT<A> v,
    final double r,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = (float) (v.getXF() * r);
    final float y = (float) (v.getYF() * r);
    final float z = (float) (v.getZF() * r);
    final float w = (float) (v.getWF() * r);
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

  public static @Nonnull <A> VectorM4FT<A> scaleInPlace(
    final @Nonnull VectorM4FT<A> v,
    final double r)
  {
    return VectorM4FT.scale(v, r, v);
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

  public static @Nonnull <A> VectorM4FT<A> subtract(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final @Nonnull VectorM4FT<A> out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    final float z = v0.getZF() - v1.getZF();
    final float w = v0.getWF() - v1.getWF();
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

  public static @Nonnull <A> VectorM4FT<A> subtractInPlace(
    final @Nonnull VectorM4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    return VectorM4FT.subtract(v0, v1, v0);
  }

  private float x = 0.0f;
  private float y = 0.0f;
  private float z = 0.0f;
  private float w = 1.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorM4FT()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM4FT(
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

  public VectorM4FT(
    final @Nonnull VectorReadable4FT<A> v)
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

    @SuppressWarnings("unchecked") final @Nonnull VectorM4FT<A> other =
      (VectorM4FT<A>) obj;
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

  /**
   * Set the value of the <code>w</code> component to the given value.
   */

  public void setWF(
    final float w)
  {
    this.w = w;
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

  /**
   * Set the value of the <code>z</code> component to the given value.
   */

  public void setZF(
    final float z)
  {
    this.z = z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM4FT ");
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
