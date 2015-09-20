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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p> A three-dimensional mutable vector type with single precision elements.
 * </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorM3F implements Vector3FType
{
  private float x;
  private float y;
  private float z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public VectorM3F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public VectorM3F(
    final float in_x,
    final float in_y,
    final float in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public VectorM3F(
    final VectorReadable3FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   */

  public static <V extends VectorWritable3FType> V absolute(
    final VectorReadable3FType v,
    final V out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    final float z = Math.abs(v.getZF());
    out.set3F(x, y, z);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v   The input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  absoluteInPlace(
    final V v)
  {
    return VectorM3F.absolute(v, v);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   */

  public static <V extends VectorWritable3FType> V add(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final V out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    final float z = v0.getZF() + v1.getZF();
    out.set3F(x, y, z);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  addInPlace(
    final V v0,
    final VectorReadable3FType v1)
  {
    return VectorM3F.add(v0, v1, v0);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   */

  public static <V extends VectorWritable3FType> V addScaled(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final double r,
    final V out)
  {
    final float x = (float) ((double) v0.getXF() + ((double) v1.getXF() * r));
    final float y = (float) ((double) v0.getYF() + ((double) v1.getYF() * r));
    final float z = (float) ((double) v0.getZF() + ((double) v1.getZF() * r));
    out.set3F(x, y, z);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  addScaledInPlace(
    final V v0,
    final VectorReadable3FType v1,
    final double r)
  {
    return VectorM3F.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code va} and {@code vb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param va      The left input vector
   * @param vb      The right input vector
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final VectorReadable3FType va,
    final VectorReadable3FType vb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, va.getXF(), vb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, va.getYF(), vb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, va.getZF(), vb.getZF());
    return xs && ys && zs;
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

  public static <V extends VectorWritable3FType> V clamp(
    final VectorReadable3FType v,
    final float minimum,
    final float maximum,
    final V out)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    out.set3F(x, y, z);
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
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <V extends VectorWritable3FType> V clampByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType minimum,
    final VectorReadable3FType maximum,
    final V out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    out.set3F(x, y, z);
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
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampByVectorInPlace(
    final V v,
    final VectorReadable3FType minimum,
    final VectorReadable3FType maximum)
  {
    return VectorM3F.clampByVector(v, minimum, maximum, v);
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

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampInPlace(
    final V v,
    final float minimum,
    final float maximum)
  {
    return VectorM3F.clamp(v, minimum, maximum, v);
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

  public static <V extends VectorWritable3FType> V clampMaximum(
    final VectorReadable3FType v,
    final float maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    out.set3F(x, y, z);
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z))}
   */

  public static <V extends VectorWritable3FType> V clampMaximumByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType maximum,
    final V out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    out.set3F(x, y, z);
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   * maximum.z))}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampMaximumByVectorInPlace(
    final V v,
    final VectorReadable3FType maximum)
  {
    return VectorM3F.clampMaximumByVector(v, maximum, v);
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

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampMaximumInPlace(
    final V v,
    final float maximum)
  {
    return VectorM3F.clampMaximum(v, maximum, v);
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

  public static <V extends VectorWritable3FType> V clampMinimum(
    final VectorReadable3FType v,
    final float minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    out.set3F(x, y, z);
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z))}
   */

  public static <V extends VectorWritable3FType> V clampMinimumByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType minimum,
    final V out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    out.set3F(x, y, z);
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   * minimum.z))} , in {@code v}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampMinimumByVectorInPlace(
    final V v,
    final VectorReadable3FType minimum)
  {
    return VectorM3F.clampMinimumByVector(v, minimum, v);
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

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  clampMinimumInPlace(
    final V v,
    final float minimum)
  {
    return VectorM3F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param <V>    The specific type of vector
   * @param input  The input vector
   * @param output The output vector
   *
   * @return output
   */

  public static <V extends VectorWritable3FType> V copy(
    final VectorReadable3FType input,
    final V output)
  {
    output.set3F(input.getXF(), input.getYF(), input.getZF());
    return output;
  }

  /**
   * Return a vector perpendicular to both {@code v0} and {@code v1} , saving
   * the result in {@code out}.
   *
   * @param v0  The left input vector.
   * @param v1  The right input vector.
   * @param out The output vector.
   * @param <V> The precise type of vector
   *
   * @return out
   */

  public static <V extends VectorWritable3FType> V crossProduct(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final V out)
  {
    final float x = (v0.getYF() * v1.getZF()) - (v0.getZF() * v1.getYF());
    final float y = (v0.getZF() * v1.getXF()) - (v0.getXF() * v1.getZF());
    final float z = (v0.getXF() * v1.getYF()) - (v0.getYF() * v1.getXF());
    out.set3F(x, y, z);
    return out;
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
    final ContextVM3F c,
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    return (double) VectorM3F.magnitude(VectorM3F.subtract(v0, v1, c.v2a));
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
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    final double x = (double) (v0.getXF() * v1.getXF());
    final double y = (double) (v0.getYF() * v1.getYF());
    final double z = (double) (v0.getZF() * v1.getZF());
    return x + y + z;
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

  public static <V extends VectorWritable3FType> V interpolateLinear(
    final ContextVM3F c,
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final double alpha,
    final V r)
  {
    VectorM3F.scale(v0, 1.0 - alpha, c.v2a);
    VectorM3F.scale(v1, alpha, c.v2b);
    return VectorM3F.add(c.v2a, c.v2b, r);
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

  public static float magnitude(
    final VectorReadable3FType v)
  {
    return (float) Math.sqrt(VectorM3F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable3FType v)
  {
    return VectorM3F.dotProduct(v, v);
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

  public static <V extends VectorWritable3FType> V normalize(
    final VectorReadable3FType v,
    final V out)
  {
    final double m = VectorM3F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM3F.scale(v, reciprocal, out);
    }

    out.set3F(v.getXF(), v.getYF(), v.getZF());
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

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  normalizeInPlace(
    final V v)
  {
    return VectorM3F.normalize(v, v);
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

  public static <V extends VectorReadable3FType, U extends
    VectorWritable3FType> void orthoNormalize(
    final ContextVM3F c,
    final VectorReadable3FType v0,
    final U v0_out,
    final VectorReadable3FType v1,
    final U v1_out)
  {
    VectorM3F.normalize(v0, c.v2a);
    VectorM3F.scale(c.v2a, VectorM3F.dotProduct(v1, c.v2a), c.v2b);
    VectorM3F.normalizeInPlace(VectorM3F.subtract(v1, c.v2b, c.v2c));
    v0_out.copyFrom3F(c.v2a);
    v1_out.copyFrom3F(c.v2c);
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

  public static <V extends VectorWritable3FType & VectorReadable3FType, W
    extends VectorWritable3FType & VectorReadable3FType> void
  orthoNormalizeInPlace(
    final ContextVM3F c,
    final V v0,
    final W v1)
  {
    VectorM3F.normalizeInPlace(v0);
    VectorM3F.scale(v0, VectorM3F.dotProduct(v1, v0), c.v2a);
    VectorM3F.subtractInPlace(v1, c.v2a);
    VectorM3F.normalizeInPlace(v1);
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

  public static <V extends VectorWritable3FType> V projection(
    final VectorReadable3FType p,
    final VectorReadable3FType q,
    final V r)
  {
    final double dot = VectorM3F.dotProduct(p, q);
    final double qms = VectorM3F.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM3F.scale(p, s, r);
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   */

  public static <V extends VectorWritable3FType> V scale(
    final VectorReadable3FType v,
    final double r,
    final V out)
  {
    final double x = (double) v.getXF() * r;
    final double y = (double) v.getYF() * r;
    final double z = (double) v.getZF() * r;
    out.set3F((float) x, (float) y, (float) z);
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  scaleInPlace(
    final V v,
    final double r)
  {
    return VectorM3F.scale(v, r, v);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   */

  public static <V extends VectorWritable3FType> V subtract(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final V out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    final float z = v0.getZF() - v1.getZF();
    out.set3F(x, y, z);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   */

  public static <V extends VectorWritable3FType & VectorReadable3FType> V
  subtractInPlace(
    final V v0,
    final VectorReadable3FType v1)
  {
    return VectorM3F.subtract(v0, v1, v0);
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
    final VectorM3F other = (VectorM3F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    return Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
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

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM3F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
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

  public static final class ContextVM3F
  {
    private final VectorM3F v2a;
    private final VectorM3F v2b;
    private final VectorM3F v2c;

    /**
     * Construct preallocated storage.
     */

    public ContextVM3F()
    {
      this.v2a = new VectorM3F();
      this.v2b = new VectorM3F();
      this.v2c = new VectorM3F();
    }
  }
}
