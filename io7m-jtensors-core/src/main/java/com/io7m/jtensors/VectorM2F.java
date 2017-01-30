/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p> A two-dimensional mutable vector type with {@code float} elements. </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorM2F implements Vector2FType
{
  private float x;
  private float y;

  /**
   * Default constructor, initializing the vector with values {@code [0.0,
   * 0.0]}.
   */

  public VectorM2F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public VectorM2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public VectorM2F(
    final VectorReadable2FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <V extends VectorWritable2FType> V absolute(
    final VectorReadable2FType v,
    final V out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    out.set2F(x, y);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v   The input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  absoluteInPlace(
    final V v)
  {
    return absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <V extends VectorWritable2FType> V add(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final V out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    out.set2F(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  addInPlace(
    final V v0,
    final VectorReadable2FType v1)
  {
    return add(v0, v1, v0);
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
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <V extends VectorWritable2FType> V addScaled(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final double r,
    final V out)
  {
    final double x = (double) v0.getXF() + ((double) v1.getXF() * r);
    final double y = (double) v0.getYF() + ((double) v1.getYF() * r);
    out.set2F((float) x, (float) y);
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
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  addScaledInPlace(
    final V v0,
    final VectorReadable2FType v1,
    final double r)
  {
    return addScaled(v0, v1, r, v0);
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
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final VectorReadable2FType qa,
    final VectorReadable2FType qb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, qa.getXF(), qb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, qa.getYF(), qb.getYF());
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
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    final double m0 = magnitude(v0);
    final double m1 = magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, (double) dotProduct(v0, v1)), 1.0);
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
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <V extends VectorWritable2FType> V clamp(
    final VectorReadable2FType v,
    final float minimum,
    final float maximum,
    final V out)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    out.set2F(x, y);
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
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <V extends VectorWritable2FType> V clampByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType minimum,
    final VectorReadable2FType maximum,
    final V out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    out.set2F(x, y);
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
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampByVectorInPlace(
    final V v,
    final VectorReadable2FType minimum,
    final VectorReadable2FType maximum)
  {
    return clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampInPlace(
    final V v,
    final float minimum,
    final float maximum)
  {
    return clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <V extends VectorWritable2FType> V clampMaximum(
    final VectorReadable2FType v,
    final float maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    out.set2F(x, y);
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
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <V extends VectorWritable2FType> V clampMaximumByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    out.set2F(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampMaximumByVectorInPlace(
    final V v,
    final VectorReadable2FType maximum)
  {
    return clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampMaximumInPlace(
    final V v,
    final float maximum)
  {
    return clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <V extends VectorWritable2FType> V clampMinimum(
    final VectorReadable2FType v,
    final float minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    out.set2F(x, y);
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
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static <V extends VectorWritable2FType> V clampMinimumByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    out.set2F(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in {@code v}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampMinimumByVectorInPlace(
    final V v,
    final VectorReadable2FType minimum)
  {
    return clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  clampMinimumInPlace(
    final V v,
    final float minimum)
  {
    return clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <V>    The precise type of vector
   *
   * @return output
   */

  public static <V extends VectorWritable2FType> V copy(
    final VectorReadable2FType input,
    final V output)
  {
    output.set2F(input.getXF(), input.getYF());
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
    final ContextVM2F c,
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    return magnitude(subtract(v0, v1, c.v2a));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The scalar product of the two vectors
   */

  public static float dotProduct(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
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
   * @param <V>   The precise type of vector
   *
   * @return {@code r}
   *
   * @since 7.0.0
   */

  public static <V extends VectorWritable2FType> V interpolateLinear(
    final ContextVM2F c,
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final double alpha,
    final V r)
  {
    scale(v0, 1.0 - alpha, c.v2a);
    scale(v1, alpha, c.v2b);
    return add(c.v2a, c.v2b, r);
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
    final VectorReadable2FType v)
  {
    return Math.sqrt(magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable2FType v)
  {
    return (double) dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code out}. The function returns the zero vector
   * iff the input is the zero vector.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return out
   */

  public static <V extends VectorWritable2FType> V normalize(
    final VectorReadable2FType v,
    final V out)
  {
    final double m = magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return scale(v, reciprocal, out);
    }

    out.set2F(v.getXF(), v.getYF());
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code v}. The function returns the zero vector iff
   * the input is the zero vector.
   *
   * @param v   The input vector
   * @param <V> The precise type of vector
   *
   * @return v
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  normalizeInPlace(
    final V v)
  {
    return normalize(v, v);
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

  public static <V extends VectorReadable2FType, U extends
    VectorWritable2FType> void orthoNormalize(
    final ContextVM2F c,
    final VectorReadable2FType v0,
    final U v0_out,
    final VectorReadable2FType v1,
    final U v1_out)
  {
    normalize(v0, c.v2a);
    scale(c.v2a, (double) dotProduct(v1, c.v2a), c.v2b);
    normalizeInPlace(subtract(v1, c.v2b, c.v2c));
    v0_out.copyFrom2F(c.v2a);
    v1_out.copyFrom2F(c.v2c);
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

  public static <V extends VectorWritable2FType & VectorReadable2FType, W
    extends VectorWritable2FType & VectorReadable2FType> void
  orthoNormalizeInPlace(
    final ContextVM2F c,
    final V v0,
    final W v1)
  {
    normalizeInPlace(v0);
    scale(v0, (double) dotProduct(v1, v0), c.v2a);
    subtractInPlace(v1, c.v2a);
    normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <V extends VectorWritable2FType> V projection(
    final VectorReadable2FType p,
    final VectorReadable2FType q,
    final V r)
  {
    final double dot = (double) dotProduct(p, q);
    final double qms = magnitudeSquared(q);
    final double s = dot / qms;

    return scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <V extends VectorWritable2FType> V scale(
    final VectorReadable2FType v,
    final double r,
    final V out)
  {
    final double x = (double) v.getXF() * r;
    final double y = (double) v.getYF() * r;
    out.set2F((float) x, (float) y);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  scaleInPlace(
    final V v,
    final double r)
  {
    return scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <V extends VectorWritable2FType> V subtract(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final V out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    out.set2F(x, y);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <V extends VectorWritable2FType & VectorReadable2FType> V
  subtractInPlace(
    final V v0,
    final VectorReadable2FType v1)
  {
    return subtract(v0, v1, v0);
  }

  @Override
  public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    copy(in_v, this);
  }

  @Override
  public boolean equals(
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
    final VectorM2F other = (VectorM2F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    return Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y);
  }

  @Override
  public float getXF()
  {
    return this.x;
  }

  @Override
  public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override
  public float getYF()
  {
    return this.y;
  }

  @Override
  public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    return result;
  }

  @Override
  public void set2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2F ");
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

  public static final class ContextVM2F
  {
    private final VectorM2F v2a;
    private final VectorM2F v2b;
    private final VectorM2F v2c;

    /**
     * Construct preallocated storage.
     */

    public ContextVM2F()
    {
      this.v2a = new VectorM2F();
      this.v2b = new VectorM2F();
      this.v2c = new VectorM2F();
    }
  }
}
