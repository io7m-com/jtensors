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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorReadable2DType;

/**
 * <p>
 * A two-dimensional mutable vector type with double precision elements.
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

public final class PVectorM2D<T> implements
  PVectorReadable2DType<T>,
  PVectorWritable2DType<T>
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> absolute(
    final PVectorReadable2DType<T> v,
    final PVectorM2D<T> out)
  {
    final double x = Math.abs(v.getXD());
    final double y = Math.abs(v.getYD());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> absoluteInPlace(
    final PVectorM2D<T> v)
  {
    return PVectorM2D.absolute(v, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> add(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final PVectorM2D<T> out)
  {
    final double x = v0.getXD() + v1.getXD();
    final double y = v0.getYD() + v1.getYD();
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> addInPlace(
    final PVectorM2D<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    return PVectorM2D.add(v0, v1, v0);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> addScaled(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double r,
    final PVectorM2D<T> out)
  {
    final double x = v0.getXD() + (v1.getXD() * r);
    final double y = v0.getYD() + (v1.getYD() * r);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> addScaledInPlace(
    final PVectorM2D<T> v0,
    final PVectorReadable2DType<T> v1,
    final double r)
  {
    return PVectorM2D.addScaled(v0, v1, r, v0);
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
   * @since 7.0.0
   * @return <code>true</code> if the vectors are almost equal
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> boolean almostEqual(
    final ContextRelative context,
    final PVectorReadable2DType<T> qa,
    final PVectorReadable2DType<T> qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double angle(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final double m0 = PVectorM2D.magnitude(v0);
    final double m1 = PVectorM2D.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, PVectorM2D.dotProduct(v0, v1)), 1.0);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clamp(
    final PVectorReadable2DType<T> v,
    final double minimum,
    final double maximum,
    final PVectorM2D<T> out)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum,
    final PVectorReadable2DType<T> maximum,
    final PVectorM2D<T> out)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampByPVectorInPlace(
    final PVectorM2D<T> v,
    final PVectorReadable2DType<T> minimum,
    final PVectorReadable2DType<T> maximum)
  {
    return PVectorM2D.clampByPVector(v, minimum, maximum, v);
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

  public static <T> PVectorM2D<T> clampInPlace(
    final PVectorM2D<T> v,
    final double minimum,
    final double maximum)
  {
    return PVectorM2D.clamp(v, minimum, maximum, v);
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

  public static <T> PVectorM2D<T> clampMaximum(
    final PVectorReadable2DType<T> v,
    final double maximum,
    final PVectorM2D<T> out)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampMaximumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> maximum,
    final PVectorM2D<T> out)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampMaximumByPVectorInPlace(
    final PVectorM2D<T> v,
    final PVectorReadable2DType<T> maximum)
  {
    return PVectorM2D.clampMaximumByPVector(v, maximum, v);
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

  public static <T> PVectorM2D<T> clampMaximumInPlace(
    final PVectorM2D<T> v,
    final double maximum)
  {
    return PVectorM2D.clampMaximum(v, maximum, v);
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

  public static <T> PVectorM2D<T> clampMinimum(
    final PVectorReadable2DType<T> v,
    final double minimum,
    final PVectorM2D<T> out)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampMinimumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum,
    final PVectorM2D<T> out)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> clampMinimumByPVectorInPlace(
    final PVectorM2D<T> v,
    final PVectorReadable2DType<T> minimum)
  {
    return PVectorM2D.clampMinimumByPVector(v, minimum, v);
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

  public static <T> PVectorM2D<T> clampMinimumInPlace(
    final PVectorM2D<T> v,
    final double minimum)
  {
    return PVectorM2D.clampMinimum(v, minimum, v);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorWritable2DType<T> copy(
    final PVectorReadable2DType<T> input,
    final PVectorWritable2DType<T> output)
  {
    output.set2D(input.getXD(), input.getYD());
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
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final PVectorM2D<T> vr = new PVectorM2D<T>();
    return PVectorM2D.magnitude(PVectorM2D.subtract(v0, v1, vr));
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
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
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
   * <li><code>interpolateLinear(v0, v1, 0.0, r) -&gt; r = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0, r) -&gt; r = v1</code></li>
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

  public static <T> PVectorM2D<T> interpolateLinear(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double alpha,
    final PVectorM2D<T> r)
  {
    final PVectorM2D<T> w0 = new PVectorM2D<T>();
    final PVectorM2D<T> w1 = new PVectorM2D<T>();

    PVectorM2D.scale(v0, 1.0 - alpha, w0);
    PVectorM2D.scale(v1, alpha, w1);

    return PVectorM2D.add(w0, w1, r);
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
    final PVectorReadable2DType<T> v)
  {
    return Math.sqrt(PVectorM2D.magnitudeSquared(v));
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
    final PVectorReadable2DType<T> v)
  {
    return PVectorM2D.dotProduct(v, v);
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

  public static <T> PVectorM2D<T> normalize(
    final PVectorReadable2DType<T> v,
    final PVectorM2D<T> out)
  {
    final double m = PVectorM2D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM2D.scale(v, reciprocal, out);
    }
    out.x = v.getXD();
    out.y = v.getYD();
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

  public static <T> PVectorM2D<T> normalizeInPlace(
    final PVectorM2D<T> v)
  {
    return PVectorM2D.normalize(v, v);
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
   * @since 7.0.0
   *
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorM2D<T>, PVectorM2D<T>> orthoNormalize(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final PVectorM2D<T> v0n = new PVectorM2D<T>();
    final PVectorM2D<T> vr = new PVectorM2D<T>();
    final PVectorM2D<T> vp = new PVectorM2D<T>();

    PVectorM2D.normalize(v0, v0n);
    PVectorM2D.scale(v0n, PVectorM2D.dotProduct(v1, v0n), vp);
    PVectorM2D.normalizeInPlace(PVectorM2D.subtract(v1, vp, vr));
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
   *
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> void orthoNormalizeInPlace(
    final PVectorM2D<T> v0,
    final PVectorM2D<T> v1)
  {
    final PVectorM2D<T> projection = new PVectorM2D<T>();

    PVectorM2D.normalizeInPlace(v0);
    PVectorM2D.scale(v0, PVectorM2D.dotProduct(v1, v0), projection);
    PVectorM2D.subtractInPlace(v1, projection);
    PVectorM2D.normalizeInPlace(v1);
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

  public static <T> PVectorM2D<T> projection(
    final PVectorReadable2DType<T> p,
    final PVectorReadable2DType<T> q,
    final PVectorM2D<T> r)
  {
    final double dot = PVectorM2D.dotProduct(p, q);
    final double qms = PVectorM2D.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM2D.scale(p, s, r);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> scale(
    final PVectorReadable2DType<T> v,
    final double r,
    final PVectorM2D<T> out)
  {
    final double x = v.getXD() * r;
    final double y = v.getYD() * r;
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> scaleInPlace(
    final PVectorM2D<T> v,
    final double r)
  {
    return PVectorM2D.scale(v, r, v);
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> subtract(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final PVectorM2D<T> out)
  {
    final double x = v0.getXD() - v1.getXD();
    final double y = v0.getYD() - v1.getYD();
    out.x = x;
    out.y = y;
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2D<T> subtractInPlace(
    final PVectorM2D<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    return PVectorM2D.subtract(v0, v1, v0);
  }

  private double x;
  private double y;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public PVectorM2D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   */

  public PVectorM2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>in_x</code>.
   *
   * @param in_x
   *          The source vector
   */

  public PVectorM2D(
    final PVectorReadable2DType<T> in_x)
  {
    this.x = in_x.getXD();
    this.y = in_x.getYD();
  }

  @Override public void copyFrom2D(
    final VectorReadable2DType in_v)
  {
    VectorM2D.copy(in_v, this);
  }

  @Override public void copyFromTyped2D(
    final PVectorReadable2DType<T> in_v)
  {
    PVectorM2D.copy(in_v, this);
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
    final PVectorM2D<?> other = (PVectorM2D<?>) obj;
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

  @Override public void set2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void setXD(
    final double in_x)
  {
    this.x = in_x;
  }

  @Override public void setYD(
    final double in_y)
  {
    this.y = in_y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorM2D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
