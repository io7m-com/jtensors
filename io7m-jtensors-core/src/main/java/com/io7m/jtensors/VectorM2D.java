/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p> A two-dimensional mutable vector type with {@code double} elements.
 * </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorM2D implements Vector2DType
{
  private double x;
  private double y;

  /**
   * Default constructor, initializing the vector with values {@code [0.0,
   * 0.0]}.
   */

  public VectorM2D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public VectorM2D(
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

  public VectorM2D(
    final VectorReadable2DType in_x)
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
   * @param <V> The precise type of writable vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <V extends VectorWritable2DType> V absolute(
    final VectorReadable2DType v,
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  absoluteInPlace(
    final V v)
  {
    return VectorM2D.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <V extends VectorWritable2DType> V add(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  addInPlace(
    final V v0,
    final VectorReadable2DType v1)
  {
    return VectorM2D.add(v0, v1, v0);
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
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <V extends VectorWritable2DType> V addScaled(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  addScaledInPlace(
    final V v0,
    final VectorReadable2DType v1,
    final double r)
  {
    return VectorM2D.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input vector
   * @param qb      The right input vector
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final ContextRelative context,
    final VectorReadable2DType qa,
    final VectorReadable2DType qb)
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
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The angle between the two vectors, in radians.
   */

  public static double angle(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    final double m0 = VectorM2D.magnitude(v0);
    final double m1 = VectorM2D.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, VectorM2D.dotProduct(v0, v1)), 1.0);
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
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <V extends VectorWritable2DType> V clamp(
    final VectorReadable2DType v,
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
   * @param <V>     The precise type of writable vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <V extends VectorWritable2DType> V clampByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType minimum,
    final VectorReadable2DType maximum,
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
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampByVectorInPlace(
    final V v,
    final VectorReadable2DType minimum,
    final VectorReadable2DType maximum)
  {
    return VectorM2D.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampInPlace(
    final V v,
    final double minimum,
    final double maximum)
  {
    return VectorM2D.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <V extends VectorWritable2DType> V clampMaximum(
    final VectorReadable2DType v,
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
   * @param <V>     The precise type of writable vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <V extends VectorWritable2DType> V clampMaximumByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType maximum,
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
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampMaximumByVectorInPlace(
    final V v,
    final VectorReadable2DType maximum)
  {
    return VectorM2D.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampMaximumInPlace(
    final V v,
    final double maximum)
  {
    return VectorM2D.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of writable vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <V extends VectorWritable2DType> V clampMinimum(
    final VectorReadable2DType v,
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
   * @param <V>     The precise type of writable vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static <V extends VectorWritable2DType> V clampMinimumByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType minimum,
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
   * @param <V>     The precise type of readable/writable vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in {@code v}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampMinimumByVectorInPlace(
    final V v,
    final VectorReadable2DType minimum)
  {
    return VectorM2D.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of readable/writable vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  clampMinimumInPlace(
    final V v,
    final double minimum)
  {
    return VectorM2D.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <V>    The precise type of writable vector
   *
   * @return output
   */

  public static <V extends VectorWritable2DType> V copy(
    final VectorReadable2DType input,
    final V output)
  {
    output.set2D(input.getXD(), input.getYD());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param c  Preallocated storage
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The distance between the two vectors.
   *
   * @since 7.0.0
   */

  public static double distance(
    final ContextVM2D c,
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    return VectorM2D.magnitude(VectorM2D.subtract(v0, v1, c.v2a));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The scalar product of the two vectors
   */

  public static double dotProduct(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
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
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li> </ul>
   *
   * @param c     Preallocated storage
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param r     The result vector.
   * @param <V>   The precise type of writable vector
   *
   * @return {@code r}
   *
   * @since 7.0.0
   */

  public static <V extends VectorWritable2DType> V interpolateLinear(
    final ContextVM2D c,
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
    final double alpha,
    final V r)
  {
    VectorM2D.scale(v0, 1.0 - alpha, c.v2a);
    VectorM2D.scale(v1, alpha, c.v2b);
    return VectorM2D.add(c.v2a, c.v2b, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   *
   * @return The magnitude of the input vector
   */

  public static double magnitude(
    final VectorReadable2DType v)
  {
    return Math.sqrt(VectorM2D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable2DType v)
  {
    return VectorM2D.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code out}. The function returns the zero vector
   * iff the input is the zero vector.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return out
   */

  public static <V extends VectorWritable2DType> V normalize(
    final VectorReadable2DType v,
    final V out)
  {
    final double m = VectorM2D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM2D.scale(v, reciprocal, out);
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return v
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  normalizeInPlace(
    final V v)
  {
    return VectorM2D.normalize(v, v);
  }

  /**
   * <p> Orthonormalize and return the vectors {@code v0} and {@code v1} . </p>
   *
   * <p> See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param c      Preallocated storage
   * @param v0     The left vector
   * @param v0_out The orthonormalized form of {@code v0}
   * @param v1     The right vector
   * @param v1_out The orthonormalized form of {@code v1}
   * @param <V>    The precise type of readable vectors
   * @param <U>    The precise type of writable vectors
   *
   * @since 7.0.0
   */

  public static <V extends VectorReadable2DType, U extends
    VectorWritable2DType> void orthoNormalize(
    final ContextVM2D c,
    final VectorReadable2DType v0,
    final U v0_out,
    final VectorReadable2DType v1,
    final U v1_out)
  {
    VectorM2D.normalize(v0, c.v2a);
    VectorM2D.scale(c.v2a, VectorM2D.dotProduct(v1, c.v2a), c.v2b);
    VectorM2D.normalizeInPlace(VectorM2D.subtract(v1, c.v2b, c.v2c));
    v0_out.copyFrom2D(c.v2a);
    v1_out.copyFrom2D(c.v2c);
  }

  /**
   * <p> Orthonormalize and the vectors {@code v0} and {@code v1}. </p> <p> See
   * <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a> </p>
   *
   * @param c   Preallocated storage
   * @param v0  The left vector
   * @param v1  The right vector
   * @param <V> The precise type of readable/writable vector
   * @param <W> The precise type of readable/writable vector
   *
   * @since 7.0.0
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType, W
    extends VectorWritable2DType & VectorReadable2DType> void
  orthoNormalizeInPlace(
    final ContextVM2D c,
    final V v0,
    final W v1)
  {
    VectorM2D.normalizeInPlace(v0);
    VectorM2D.scale(v0, VectorM2D.dotProduct(v1, v0), c.v2a);
    VectorM2D.subtractInPlace(v1, c.v2a);
    VectorM2D.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <V extends VectorWritable2DType> V projection(
    final VectorReadable2DType p,
    final VectorReadable2DType q,
    final V r)
  {
    final double dot = VectorM2D.dotProduct(p, q);
    final double qms = VectorM2D.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM2D.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <V extends VectorWritable2DType> V scale(
    final VectorReadable2DType v,
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  scaleInPlace(
    final V v,
    final double r)
  {
    return VectorM2D.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of writable vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <V extends VectorWritable2DType> V subtract(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
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
   * @param <V> The precise type of readable/writable vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <V extends VectorWritable2DType & VectorReadable2DType> V
  subtractInPlace(
    final V v0,
    final VectorReadable2DType v1)
  {
    return VectorM2D.subtract(v0, v1, v0);
  }

  @Override public void copyFrom2D(
    final VectorReadable2DType in_v)
  {
    VectorM2D.copy(in_v, this);
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
    final VectorM2D other = (VectorM2D) obj;
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
    builder.append("[VectorM2D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

  /**
   * Preallocated storage to allow all vector functions to run without
   * allocating.
   *
   * @since 7.0.0
   */

  public static final class ContextVM2D
  {
    private final VectorM2D v2a;
    private final VectorM2D v2b;
    private final VectorM2D v2c;

    /**
     * Construct preallocated storage.
     */

    public ContextVM2D()
    {
      this.v2a = new VectorM2D();
      this.v2b = new VectorM2D();
      this.v2c = new VectorM2D();
    }
  }
}
