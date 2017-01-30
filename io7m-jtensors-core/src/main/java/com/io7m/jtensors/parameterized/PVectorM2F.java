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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorReadable2FType;

/**
 * <p> A two-dimensional mutable vector type with {@code float} elements. </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter.
 *
 * @since 7.0.0
 */

public final class PVectorM2F<T> implements PVector2FType<T>
{
  private float x;
  private float y;

  /**
   * Default constructor, initializing the vector with values {@code [0.0,
   * 0.0]}.
   */

  public PVectorM2F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public PVectorM2F(
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

  public PVectorM2F(
    final PVectorReadable2FType<T> in_v)
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <T, V extends PVectorWritable2FType<T>> V absolute(
    final PVectorReadable2FType<T> v,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V absoluteInPlace(
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <T, V extends PVectorWritable2FType<T>> V add(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V addInPlace(
    final V v0,
    final PVectorReadable2FType<T> v1)
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <T, V extends PVectorWritable2FType<T>> V addScaled(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V addScaledInPlace(
    final V v0,
    final PVectorReadable2FType<T> v1,
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
   * @param <T>     A phantom type parameter.
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 7.0.0
   */

  public static <T> boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final PVectorReadable2FType<T> qa,
    final PVectorReadable2FType<T> qb)
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
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The angle between the two vectors, in radians.
   */

  public static <T> double angle(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clamp(
    final PVectorReadable2FType<T> v,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clampByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> minimum,
    final PVectorReadable2FType<T> maximum,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y))}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampByPVectorInPlace(
    final V v,
    final PVectorReadable2FType<T> minimum,
    final PVectorReadable2FType<T> maximum)
  {
    return clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampInPlace(
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clampMaximum(
    final PVectorReadable2FType<T> v,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clampMaximumByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> maximum,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampMaximumByPVectorInPlace(
    final V v,
    final PVectorReadable2FType<T> maximum)
  {
    return clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampMaximumInPlace(
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clampMinimum(
    final PVectorReadable2FType<T> v,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static <T, V extends PVectorWritable2FType<T>> V clampMinimumByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> minimum,
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
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in {@code v}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampMinimumByPVectorInPlace(
    final V v,
    final PVectorReadable2FType<T> minimum)
  {
    return clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V clampMinimumInPlace(
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
   * @param <T>    A phantom type parameter.
   * @param <V>    The precise type of vector
   *
   * @return output
   */

  public static <T, V extends PVectorWritable2FType<T>> V copy(
    final PVectorReadable2FType<T> input,
    final V output)
  {
    output.set2F(input.getXF(), input.getYF());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param c   Preallocated storage
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The distance between the two vectors.
   */

  public static <T> double distance(
    final ContextPVM2F c,
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    final PVectorM2F<T> vr = (PVectorM2F<T>) c.va;
    return magnitude(subtract(v0, v1, vr));
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

  public static <T> float dotProduct(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
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
   * @param v0    The left input vector
   * @param v1    The right input vector
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}
   * @param r     The result vector
   * @param <V>   The precise type of vector
   * @param <T>   A phantom type parameter
   *
   * @return {@code r}
   *
   * @since 7.0.0
   */

  public static <T, V extends PVectorWritable2FType<T>> V interpolateLinear(
    final ContextPVM2F c,
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
    final double alpha,
    final V r)
  {
    final PVectorM2F<T> va = (PVectorM2F<T>) c.va;
    final PVectorM2F<T> vb = (PVectorM2F<T>) c.vb;
    scale(v0, 1.0 - alpha, va);
    scale(v1, alpha, vb);
    return add(va, vb, r);
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
    final PVectorReadable2FType<T> v)
  {
    return Math.sqrt(magnitudeSquared(v));
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
    final PVectorReadable2FType<T> v)
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return out
   */

  public static <T, V extends PVectorWritable2FType<T>> V normalize(
    final PVectorReadable2FType<T> v,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return v
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V normalizeInPlace(
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
   * @param <T>    A phantom type parameter
   * @param <V>    The precise type of vector
   *
   * @since 7.0.0
   */

  public static <T, V extends PVectorWritable2FType<T>> void orthoNormalize(
    final ContextPVM2F c,
    final PVectorReadable2FType<T> v0,
    final V v0_out,
    final PVectorReadable2FType<T> v1,
    final V v1_out)
  {
    final PVectorM2F<T> va = (PVectorM2F<T>) c.va;
    final PVectorM2F<T> vb = (PVectorM2F<T>) c.vb;
    final PVectorM2F<T> vc = (PVectorM2F<T>) c.vc;
    normalize(v0, va);
    scale(va, (double) dotProduct(v1, va), vb);
    normalizeInPlace(subtract(v1, vb, vc));
    copy(va, v0_out);
    copy(vc, v1_out);
  }

  /**
   * <p> Orthonormalize and the vectors {@code v0} and {@code v1}. </p> <p> See
   * <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a> </p>
   *
   * @param c   Preallocated storage
   * @param v0  The left vector
   * @param v1  The right vector
   * @param <V> The precise type of vector
   * @param <T> A phantom type parameter
   *
   * @since 7.0.0
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> void orthoNormalizeInPlace(
    final ContextPVM2F c,
    final V v0,
    final V v1)
  {
    final PVectorM2F<T> va = (PVectorM2F<T>) c.va;
    final PVectorM2F<T> vb = (PVectorM2F<T>) c.vb;
    final PVectorM2F<T> vc = (PVectorM2F<T>) c.vc;
    normalize(v0, va);
    scale(va, (double) dotProduct(v1, va), vb);
    normalizeInPlace(subtract(v1, vb, vc));
    copy(va, v0);
    copy(vc, v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <T, V extends PVectorWritable2FType<T>> V projection(
    final PVectorReadable2FType<T> p,
    final PVectorReadable2FType<T> q,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <T, V extends PVectorWritable2FType<T>> V scale(
    final PVectorReadable2FType<T> v,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V scaleInPlace(
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <T, V extends PVectorWritable2FType<T>> V subtract(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
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
   * @param <T> A phantom type parameter.
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   */

  public static <T, V extends PVectorWritable2FType<T> &
    PVectorReadable2FType<T>> V subtractInPlace(
    final V v0,
    final PVectorReadable2FType<T> v1)
  {
    return subtract(v0, v1, v0);
  }

  @Override
  public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    VectorM2F.copy(in_v, this);
  }

  @Override
  public void copyFromTyped2F(
    final PVectorReadable2FType<T> in_v)
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
    final PVectorM2F<?> other = (PVectorM2F<?>) obj;
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
    builder.append("[PVectorM2F ");
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

  public static final class ContextPVM2F
  {
    private final PVectorM2F<?> va;
    private final PVectorM2F<?> vb;
    private final PVectorM2F<?> vc;

    /**
     * Construct preallocated storage.
     */

    public ContextPVM2F()
    {
      this.va = new PVectorM2F<Object>();
      this.vb = new PVectorM2F<Object>();
      this.vc = new PVectorM2F<Object>();
    }
  }
}
