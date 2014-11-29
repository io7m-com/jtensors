/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;

/**
 * <p>
 * A four-dimensional mutable vector type with single precision elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

public final class PVectorM4F<T> implements
  PVectorReadable4FType<T>,
  PVectorWritable4FType<T>
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> absolute(
    final PVectorReadable4FType<T> v,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> absoluteInPlace(
    final PVectorM4F<T> v)
  {
    return PVectorM4F.absolute(v, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> add(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> addInPlace(
    final PVectorM4F<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    return PVectorM4F.add(v0, v1, v0);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> addScaled(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final double r,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> addScaledInPlace(
    final PVectorM4F<T> v0,
    final PVectorReadable4FType<T> v1,
    final double r)
  {
    return PVectorM4F.addScaled(v0, v1, r, v0);
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
   * @since 7.0.0
   * @return <code>true</code> if the vectors are almost equal
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final PVectorReadable4FType<T> va,
    final PVectorReadable4FType<T> vb)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clamp(
    final PVectorReadable4FType<T> v,
    final double minimum,
    final double maximum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum,
    final PVectorReadable4FType<T> maximum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampByPVectorInPlace(
    final PVectorM4F<T> v,
    final PVectorReadable4FType<T> minimum,
    final PVectorReadable4FType<T> maximum)
  {
    return PVectorM4F.clampByPVector(v, minimum, maximum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampInPlace(
    final PVectorM4F<T> v,
    final float minimum,
    final float maximum)
  {
    return PVectorM4F.clamp(v, minimum, maximum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMaximum(
    final PVectorReadable4FType<T> v,
    final float maximum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMaximumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> maximum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMaximumByPVectorInPlace(
    final PVectorM4F<T> v,
    final PVectorReadable4FType<T> maximum)
  {
    return PVectorM4F.clampMaximumByPVector(v, maximum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMaximumInPlace(
    final PVectorM4F<T> v,
    final float maximum)
  {
    return PVectorM4F.clampMaximum(v, maximum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMinimum(
    final PVectorReadable4FType<T> v,
    final float minimum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMinimumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMinimumByPVectorInPlace(
    final PVectorM4F<T> v,
    final PVectorReadable4FType<T> minimum)
  {
    return PVectorM4F.clampMinimumByPVector(v, minimum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> clampMinimumInPlace(
    final PVectorM4F<T> v,
    final float minimum)
  {
    return PVectorM4F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   *
   * @param <U>
   *          The specific type of vector
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   *
   * @return output
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T, U extends PVectorWritable4FType<T>> U copy(
    final PVectorReadable4FType<T> input,
    final U output)
  {
    output.set4F(input.getXF(), input.getYF(), input.getZF(), input.getWF());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double distance(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final PVectorM4F<T> vr = new PVectorM4F<T>();
    return PVectorM4F.magnitude(PVectorM4F.subtract(v0, v1, vr));
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double dotProduct(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> interpolateLinear(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final double alpha,
    final PVectorM4F<T> r)
  {
    final PVectorM4F<T> w0 = new PVectorM4F<T>();
    final PVectorM4F<T> w1 = new PVectorM4F<T>();

    PVectorM4F.scale(v0, 1.0f - alpha, w0);
    PVectorM4F.scale(v1, alpha, w1);

    return PVectorM4F.add(w0, w1, r);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitude(
    final PVectorReadable4FType<T> v)
  {
    return Math.sqrt(PVectorM4F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable4FType<T> v)
  {
    return PVectorM4F.dotProduct(v, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> normalize(
    final PVectorReadable4FType<T> v,
    final PVectorM4F<T> out)
  {
    final double m = PVectorM4F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM4F.scale(v, reciprocal, out);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> normalizeInPlace(
    final PVectorM4F<T> v)
  {
    return PVectorM4F.normalize(v, v);
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
   * @since 7.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorM4F<T>, PVectorM4F<T>> orthoNormalize(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final PVectorM4F<T> v0n = new PVectorM4F<T>();
    final PVectorM4F<T> vr = new PVectorM4F<T>();
    final PVectorM4F<T> vp = new PVectorM4F<T>();

    PVectorM4F.normalize(v0, v0n);
    PVectorM4F.scale(v0n, PVectorM4F.dotProduct(v1, v0n), vp);
    PVectorM4F.normalizeInPlace(PVectorM4F.subtract(v1, vp, vr));
    return Pair.pair(v0n, vr);
  }

  /**
   * <p>
   * Orthonormalize and the vectors <code>v0</code> and <code>v1</code>.
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @since 7.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> void orthoNormalizeInPlace(
    final PVectorM4F<T> v0,
    final PVectorM4F<T> v1)
  {
    final PVectorM4F<T> projection = new PVectorM4F<T>();

    PVectorM4F.normalizeInPlace(v0);
    PVectorM4F.scale(v0, PVectorM4F.dotProduct(v1, v0), projection);
    PVectorM4F.subtractInPlace(v1, projection);
    PVectorM4F.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   *
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> projection(
    final PVectorReadable4FType<T> p,
    final PVectorReadable4FType<T> q,
    final PVectorM4F<T> r)
  {
    final double dot = PVectorM4F.dotProduct(p, q);
    final double qms = PVectorM4F.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM4F.scale(p, s, r);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> scale(
    final PVectorReadable4FType<T> v,
    final double r,
    final PVectorM4F<T> out)
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> scaleInPlace(
    final PVectorM4F<T> v,
    final double r)
  {
    return PVectorM4F.scale(v, r, v);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>,
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> subtract(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final PVectorM4F<T> out)
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
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>,
   * saving the result to <code>v0</code>.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM4F<T> subtractInPlace(
    final PVectorM4F<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    return PVectorM4F.subtract(v0, v1, v0);
  }

  private float w = 1.0f;
  private float x;
  private float y;
  private float z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public PVectorM4F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   * @param in_z
   *          The <code>z</code> value
   * @param in_w
   *          The <code>w</code> value
   */

  public PVectorM4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>in_v</code>.
   *
   * @param in_v
   *          The source vector
   */

  public PVectorM4F(
    final PVectorReadable4FType<T> in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
    this.w = in_v.getWF();
  }

  @Override public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    VectorM2F.copy(in_v, this);
  }

  @Override public void copyFrom3F(
    final VectorReadable3FType in_v)
  {
    VectorM3F.copy(in_v, this);
  }

  @Override public void copyFrom4F(
    final VectorReadable4FType in_v)
  {
    VectorM4F.copy(in_v, this);
  }

  @Override public void copyFromTyped2F(
    final PVectorReadable2FType<T> in_v)
  {
    PVectorM2F.copy(in_v, this);
  }

  @Override public void copyFromTyped3F(
    final PVectorReadable3FType<T> in_v)
  {
    PVectorM3F.copy(in_v, this);
  }

  @Override public void copyFromTyped4F(
    final PVectorReadable4FType<T> in_v)
  {
    VectorM4F.copy(in_v, this);
  }

  @Override public boolean equals(
    final @Nullable Object obj)
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
    final PVectorM4F<?> other = (PVectorM4F<?>) obj;
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

  @Override public void set2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3F(
    final float in_x,
    final float in_y,
    final float in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void set4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override public void setWF(
    final float in_w)
  {
    this.w = in_w;
  }

  @Override public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override public void setZF(
    final float in_z)
  {
    this.z = in_z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorM4F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
