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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;

/**
 * <p> A four-dimensional mutable vector type with {@code float} elements.
 * </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PVectorM4F<T> implements PVector4FType<T>
{
  private float w = 1.0f;
  private float x;
  private float y;
  private float z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0, 1.0]}.
   */

  public PVectorM4F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
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
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public PVectorM4F(
    final PVectorReadable4FType<T> in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
    this.w = in_v.getWF();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   */

  public static <T, V extends PVectorWritable4FType<T>> V absolute(
    final PVectorReadable4FType<T> v,
    final V out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    final float z = Math.abs(v.getZF());
    final float w = Math.abs(v.getWF());
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs v.w)}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V absoluteInPlace(
    final V v)
  {
    return PVectorM4F.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static <T, V extends PVectorWritable4FType<T>> V add(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final V out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    final float z = v0.getZF() + v1.getZF();
    final float w = v0.getWF() + v1.getWF();
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V addInPlace(
    final V v0,
    final PVectorReadable4FType<T> v1)
  {
    return PVectorM4F.add(v0, v1, v0);
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
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   */

  public static <T, V extends PVectorWritable4FType<T>> V addScaled(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final double r,
    final V out)
  {
    final float x = (float) ((double) v0.getXF() + ((double) v1.getXF() * r));
    final float y = (float) ((double) v0.getYF() + ((double) v1.getYF() * r));
    final float z = (float) ((double) v0.getZF() + ((double) v1.getZF() * r));
    final float w = (float) ((double) v0.getWF() + ((double) v1.getWF() * r));
    out.set4F(x, y, z, w);
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
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V addScaledInPlace(
    final V v0,
    final PVectorReadable4FType<T> v1,
    final double r)
  {
    return PVectorM4F.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code va} and {@code vb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param va      The left input vector
   * @param vb      The right input vector
   * @param <T>     A phantom type parameter
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 7.0.0
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
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param out     The output vector
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clamp(
    final PVectorReadable4FType<T> v,
    final double minimum,
    final double maximum,
    final V out)
  {
    final float x =
      (float) Math.min(Math.max((double) v.getXF(), minimum), maximum);
    final float y =
      (float) Math.min(Math.max((double) v.getYF(), minimum), maximum);
    final float z =
      (float) Math.min(Math.max((double) v.getZF(), minimum), maximum);
    final float w =
      (float) Math.min(Math.max((double) v.getWF(), minimum), maximum);
    out.set4F(x, y, z, w);
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
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clampByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum,
    final PVectorReadable4FType<T> maximum,
    final V out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
    out.set4F(x, y, z, w);
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
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampByPVectorInPlace(
    final V v,
    final PVectorReadable4FType<T> minimum,
    final PVectorReadable4FType<T> maximum)
  {
    return PVectorM4F.clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampInPlace(
    final V v,
    final float minimum,
    final float maximum)
  {
    return PVectorM4F.clamp(v, (double) minimum, (double) maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clampMaximum(
    final PVectorReadable4FType<T> v,
    final float maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
    out.set4F(x, y, z, w);
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
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z), min(v.w, maximum.w))}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clampMaximumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z), min(v.w, maximum.w))}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampMaximumByPVectorInPlace(
    final V v,
    final PVectorReadable4FType<T> maximum)
  {
    return PVectorM4F.clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} , in
   * {@code v}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampMaximumInPlace(
    final V v,
    final float maximum)
  {
    return PVectorM4F.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param out     The output vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clampMinimum(
    final PVectorReadable4FType<T> v,
    final float minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
    out.set4F(x, y, z, w);
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
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z), max(v.w, minimum.w))}
   */

  public static <T, V extends PVectorWritable4FType<T>> V clampMinimumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z), max(v.w, minimum.w))} , in {@code v}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampMinimumByPVectorInPlace(
    final V v,
    final PVectorReadable4FType<T> minimum)
  {
    return PVectorM4F.clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V clampMinimumInPlace(
    final V v,
    final float minimum)
  {
    return PVectorM4F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param <U>    The specific type of vector
   * @param input  The input vector
   * @param output The output vector
   * @param <T>    A phantom type parameter
   *
   * @return output
   */

  public static <T, U extends PVectorWritable4FType<T>> U copy(
    final PVectorReadable4FType<T> input,
    final U output)
  {
    output.set4F(input.getXF(), input.getYF(), input.getZF(), input.getWF());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param c   Preallocated storage
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter
   *
   * @return The distance between the two vectors.
   */

  public static <T> double distance(
    final ContextPVM4F c,
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final PVectorM4F<T> vr = (PVectorM4F<T>) c.va;
    return PVectorM4F.magnitude(PVectorM4F.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter
   *
   * @return The scalar product of the two vectors
   */

  public static <T> double dotProduct(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final double x = (double) (v0.getXF() * v1.getXF());
    final double y = (double) (v0.getYF() * v1.getYF());
    final double z = (double) (v0.getZF() * v1.getZF());
    final double w = (double) (v0.getWF() * v1.getWF());
    return x + y + z + w;
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
   * @param <T>   A phantom type parameter
   * @param <V>   The precise type of vector
   *
   * @return {@code r}
   *
   * @since 7.0.0
   */

  public static <T, V extends PVectorWritable4FType<T>> V interpolateLinear(
    final ContextPVM4F c,
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final double alpha,
    final V r)
  {
    final PVectorM4F<T> va = (PVectorM4F<T>) c.va;
    final PVectorM4F<T> vb = (PVectorM4F<T>) c.vb;
    PVectorM4F.scale(v0, 1.0 - alpha, va);
    PVectorM4F.scale(v1, alpha, vb);
    return PVectorM4F.add(va, vb, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter
   *
   * @return The magnitude of the input vector
   */

  public static <T> double magnitude(
    final PVectorReadable4FType<T> v)
  {
    return Math.sqrt(PVectorM4F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter
   *
   * @return The squared magnitude of the input vector
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable4FType<T> v)
  {
    return PVectorM4F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code out}. The function returns the zero vector
   * iff the input is the zero vector.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return out
   */

  public static <T, V extends PVectorWritable4FType<T>> V normalize(
    final PVectorReadable4FType<T> v,
    final V out)
  {
    final double m = PVectorM4F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM4F.scale(v, reciprocal, out);
    }

    out.set4F(v.getXF(), v.getYF(), v.getZF(), v.getWF());
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code v}. The function returns the zero vector iff
   * the input is the zero vector.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return v
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V normalizeInPlace(
    final V v)
  {
    return PVectorM4F.normalize(v, v);
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

  public static <T, V extends PVectorWritable4FType<T>> void orthoNormalize(
    final ContextPVM4F c,
    final PVectorReadable4FType<T> v0,
    final V v0_out,
    final PVectorReadable4FType<T> v1,
    final V v1_out)
  {
    final PVectorM4F<T> va = (PVectorM4F<T>) c.va;
    final PVectorM4F<T> vb = (PVectorM4F<T>) c.vb;
    final PVectorM4F<T> vc = (PVectorM4F<T>) c.vc;
    PVectorM4F.normalize(v0, va);
    PVectorM4F.scale(va, PVectorM4F.dotProduct(v1, va), vb);
    PVectorM4F.normalizeInPlace(PVectorM4F.subtract(v1, vb, vc));
    PVectorM4F.copy(va, v0_out);
    PVectorM4F.copy(vc, v1_out);
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

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> void orthoNormalizeInPlace(
    final ContextPVM4F c,
    final V v0,
    final V v1)
  {
    final PVectorM4F<T> va = (PVectorM4F<T>) c.va;
    final PVectorM4F<T> vb = (PVectorM4F<T>) c.vb;
    final PVectorM4F<T> vc = (PVectorM4F<T>) c.vc;
    PVectorM4F.normalize(v0, va);
    PVectorM4F.scale(va, PVectorM4F.dotProduct(v1, va), vb);
    PVectorM4F.normalizeInPlace(PVectorM4F.subtract(v1, vb, vc));
    PVectorM4F.copy(va, v0);
    PVectorM4F.copy(vc, v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <T, V extends PVectorWritable4FType<T>> V projection(
    final PVectorReadable4FType<T> p,
    final PVectorReadable4FType<T> q,
    final V r)
  {
    final double dot = PVectorM4F.dotProduct(p, q);
    final double qms = PVectorM4F.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM4F.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static <T, V extends PVectorWritable4FType<T>> V scale(
    final PVectorReadable4FType<T> v,
    final double r,
    final V out)
  {
    final float x = (float) ((double) v.getXF() * r);
    final float y = (float) ((double) v.getYF() * r);
    final float z = (float) ((double) v.getZF() * r);
    final float w = (float) ((double) v.getWF() * r);
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V scaleInPlace(
    final V v,
    final double r)
  {
    return PVectorM4F.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static <T, V extends PVectorWritable4FType<T>> V subtract(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final V out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    final float z = v0.getZF() - v1.getZF();
    final float w = v0.getWF() - v1.getWF();
    out.set4F(x, y, z, w);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static <T, V extends PVectorWritable4FType<T> &
    PVectorReadable4FType<T>> V subtractInPlace(
    final V v0,
    final PVectorReadable4FType<T> v1)
  {
    return PVectorM4F.subtract(v0, v1, v0);
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
    return Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
  }

  @Override public float getWF()
  {
    return this.w;
  }

  @Override public void setWF(
    final float in_w)
  {
    this.w = in_w;
  }

  @Override public float getXF()
  {
    return this.x;
  }

  @Override public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override public float getYF()
  {
    return this.y;
  }

  @Override public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override public float getZF()
  {
    return this.z;
  }

  @Override public void setZF(
    final float in_z)
  {
    this.z = in_z;
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

  /**
   * Preallocated storage to allow all vector functions to run without
   * allocating.
   *
   * @since 7.0.0
   */

  public static final class ContextPVM4F
  {
    private final PVectorM4F<?> va;
    private final PVectorM4F<?> vb;
    private final PVectorM4F<?> vc;

    /**
     * Construct preallocated storage.
     */

    public ContextPVM4F()
    {
      this.va = new PVectorM4F<Object>();
      this.vb = new PVectorM4F<Object>();
      this.vc = new PVectorM4F<Object>();
    }
  }
}
