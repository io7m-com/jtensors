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
 * <p> A two-dimensional mutable vector type with double precision elements.
 * </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter.
 */

public final class PVectorM2D<T>
  implements PVectorReadable2DType<T>, PVectorWritable2DType<T>
{
  private double x;
  private double y;

  /**
   * Default constructor, initializing the vector with values {@code [0.0,
   * 0.0]}.
   */

  public PVectorM2D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public PVectorM2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_x}.
   *
   * @param in_x The source vector
   */

  public PVectorM2D(
    final PVectorReadable2DType<T> in_x)
  {
    this.x = in_x.getXD();
    this.y = in_x.getYD();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <T, V extends PVectorWritable2DType<T>> V absolute(
    final PVectorReadable2DType<T> v,
    final V out)
  {
    final double x = Math.abs(v.getXD());
    final double y = Math.abs(v.getYD());
    out.set2D(x, y);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V absoluteInPlace(
    final V v)
  {
    return PVectorM2D.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <T, V extends PVectorWritable2DType<T>> V add(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final V out)
  {
    final double x = v0.getXD() + v1.getXD();
    final double y = v0.getYD() + v1.getYD();
    out.set2D(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V addInPlace(
    final V v0,
    final PVectorReadable2DType<T> v1)
  {
    return PVectorM2D.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the result to
   * {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <T, V extends PVectorWritable2DType<T>> V addScaled(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double r,
    final V out)
  {
    final double x = v0.getXD() + (v1.getXD() * r);
    final double y = v0.getYD() + (v1.getYD() * r);
    out.set2D(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the result to
   * {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V addScaledInPlace(
    final V v0,
    final PVectorReadable2DType<T> v1,
    final double r)
  {
    return PVectorM2D.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input vector
   * @param qb      The right input vector
   * @param <T>     A phantom type parameter.
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * @since 7.0.0
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
   * Calculate the angle between vectors {@code v0} and {@code v1}, in radians.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The angle between the two vectors, in radians.
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
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param out     The output vector
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clamp(
    final PVectorReadable2DType<T> v,
    final double minimum,
    final double maximum,
    final V out)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}, saving
   * the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param out     The output vector
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of writable vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clampByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum,
    final PVectorReadable2DType<T> maximum,
    final V out)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}, saving
   * the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampByPVectorInPlace(
    final V v,
    final PVectorReadable2DType<T> minimum,
    final PVectorReadable2DType<T> maximum)
  {
    return PVectorM2D.clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampInPlace(
    final V v,
    final double minimum,
    final double maximum)
  {
    return PVectorM2D.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clampMaximum(
    final PVectorReadable2DType<T> v,
    final double maximum,
    final V out)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * out}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param out     The output vector
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clampMaximumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> maximum,
    final V out)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampMaximumByPVectorInPlace(
    final V v,
    final PVectorReadable2DType<T> maximum)
  {
    return PVectorM2D.clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampMaximumInPlace(
    final V v,
    final double maximum)
  {
    return PVectorM2D.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clampMinimum(
    final PVectorReadable2DType<T> v,
    final double minimum,
    final V out)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of writable vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static <T, V extends PVectorWritable2DType<T>> V clampMinimumByPVector(
    final PVectorReadable2DType<T> v,
    final PVectorReadable2DType<T> minimum,
    final V out)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    out.set2D(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in {@code v}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampMinimumByPVectorInPlace(
    final V v,
    final PVectorReadable2DType<T> minimum)
  {
    return PVectorM2D.clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V clampMinimumInPlace(
    final V v,
    final double minimum)
  {
    return PVectorM2D.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <T>    A phantom type parameter.
   * @param <V>    The precise type of readable vector
   * @param <U>    The precise type of writable vector
   *
   * @return output
   */

  public static <T, U extends PVectorReadable2DType<T>, V extends
    PVectorWritable2DType<T>> V copy(
    final U input,
    final V output)
  {
    output.set2D(input.getXD(), input.getYD());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The distance between the two vectors.
   */

  public static <T> double distance(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1)
  {
    final PVectorM2D<T> vr = new PVectorM2D<T>();
    return PVectorM2D.magnitude(PVectorM2D.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The scalar product of the two vectors
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
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) → r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) → r = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param r     The result vector.
   * @param <T>   A phantom type parameter.
   * @param <V>   The precise type of writable vector
   *
   * @return {@code r}
   */

  public static <T, V extends PVectorWritable2DType<T>> V interpolateLinear(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final double alpha,
    final V r)
  {
    final PVectorM2D<T> w0 = new PVectorM2D<T>();
    final PVectorM2D<T> w1 = new PVectorM2D<T>();

    PVectorM2D.scale(v0, 1.0 - alpha, w0);
    PVectorM2D.scale(v1, alpha, w1);

    return PVectorM2D.add(w0, w1, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The magnitude of the input vector
   */

  public static <T> double magnitude(
    final PVectorReadable2DType<T> v)
  {
    return Math.sqrt(PVectorM2D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The squared magnitude of the input vector
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable2DType<T> v)
  {
    return PVectorM2D.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code out}. The function returns the zero vector
   * iff the input is the zero vector.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return out
   */

  public static <T, V extends PVectorWritable2DType<T>> V normalize(
    final PVectorReadable2DType<T> v,
    final V out)
  {
    final double m = PVectorM2D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM2D.scale(v, reciprocal, out);
    }
    out.set2D(v.getXD(), v.getYD());
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code v}. The function returns the zero vector iff
   * the input is the zero vector.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @return v
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V normalizeInPlace(
    final V v)
  {
    return PVectorM2D.normalize(v, v);
  }

  /**
   * <p> Orthonormalize and return the vectors {@code v0} and {@code v1} . </p>
   * <p> See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0  The left vector
   * @param v1  The right vector
   * @param <T> A phantom type parameter.
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   *
   * @since 7.0.0
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
   * <p> Orthonormalize and the vectors {@code v0} and {@code v1}. </p> <p> See
   * <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a> </p>
   *
   * @param v0  The left vector
   * @param v1  The right vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @since 7.0.0
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> void orthoNormalizeInPlace(
    final V v0,
    final V v1)
  {
    final PVectorM2D<T> projection = new PVectorM2D<T>();

    PVectorM2D.normalizeInPlace(v0);
    PVectorM2D.scale(v0, PVectorM2D.dotProduct(v1, v0), projection);
    PVectorM2D.subtractInPlace(v1, projection);
    PVectorM2D.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <T, V extends PVectorWritable2DType<T>> V projection(
    final PVectorReadable2DType<T> p,
    final PVectorReadable2DType<T> q,
    final V r)
  {
    final double dot = PVectorM2D.dotProduct(p, q);
    final double qms = PVectorM2D.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM2D.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <T, V extends PVectorWritable2DType<T>> V scale(
    final PVectorReadable2DType<T> v,
    final double r,
    final V out)
  {
    final double x = v.getXD() * r;
    final double y = v.getYD() * r;
    out.set2D(x, y);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V scaleInPlace(
    final V v,
    final double r)
  {
    return PVectorM2D.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <T, V extends PVectorWritable2DType<T>> V subtract(
    final PVectorReadable2DType<T> v0,
    final PVectorReadable2DType<T> v1,
    final V out)
  {
    final double x = v0.getXD() - v1.getXD();
    final double y = v0.getYD() - v1.getYD();
    out.set2D(x, y);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <T, V extends PVectorWritable2DType<T> &
    PVectorReadable2DType<T>> V subtractInPlace(
    final V v0,
    final PVectorReadable2DType<T> v1)
  {
    return PVectorM2D.subtract(v0, v1, v0);
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
    return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public void setXD(
    final double in_x)
  {
    this.x = in_x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public void setYD(
    final double in_y)
  {
    this.y = in_y;
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
