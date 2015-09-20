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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;

/**
 * <p> A three-dimensional mutable vector type with double precision elements.
 * </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PVectorM3D<T> implements PVector3DType<T>
{
  private double x;
  private double y;
  private double z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public PVectorM3D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public PVectorM3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * v}.
   *
   * @param in_v The source vector
   */

  public PVectorM3D(
    final PVectorReadable3DType<T> in_v)
  {
    this.x = in_v.getXD();
    this.y = in_v.getYD();
    this.z = in_v.getZD();
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
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   */

  public static <T, V extends PVectorWritable3DType<T>> V absolute(
    final PVectorReadable3DType<T> v,
    final V out)
  {
    final double x = Math.abs(v.getXD());
    final double y = Math.abs(v.getYD());
    final double z = Math.abs(v.getZD());
    out.set3D(x, y, z);
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
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V absoluteInPlace(
    final V v)
  {
    return PVectorM3D.absolute(v, v);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   */

  public static <T, V extends PVectorWritable3DType<T>> V add(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final V out)
  {
    final double x = v0.getXD() + v1.getXD();
    final double y = v0.getYD() + v1.getYD();
    final double z = v0.getZD() + v1.getZD();
    out.set3D(x, y, z);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V addInPlace(
    final V v0,
    final PVectorReadable3DType<T> v1)
  {
    return PVectorM3D.add(v0, v1, v0);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   */

  public static <T, V extends PVectorWritable3DType<T>> V addScaled(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final double r,
    final V out)
  {
    final double x = v0.getXD() + (v1.getXD() * r);
    final double y = v0.getYD() + (v1.getYD() * r);
    final double z = v0.getZD() + (v1.getZD() * r);
    out.set3D(x, y, z);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V addScaledInPlace(
    final V v0,
    final PVectorReadable3DType<T> v1,
    final double r)
  {
    return PVectorM3D.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param qa      The left input vector
   * @param qb      The right input vector
   * @param <T>     A phantom type parameter
   *
   * @return {@code true} if the vectors are almost equal
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   * @since 7.0.0
   */

  public static <T> boolean almostEqual(
    final ContextRelative context,
    final PVectorReadable3DType<T> qa,
    final PVectorReadable3DType<T> qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, qa.getZD(), qb.getZD());
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
   * @param <T>     A phantom type parameter
   * @param <V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <T, V extends PVectorWritable3DType<T>> V clamp(
    final PVectorReadable3DType<T> v,
    final double minimum,
    final double maximum,
    final V out)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    out.set3D(x, y, z);
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
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <T, V extends PVectorWritable3DType<T>> V clampByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> minimum,
    final PVectorReadable3DType<T> maximum,
    final V out)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    out.set3D(x, y, z);
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
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampByPVectorInPlace(
    final V v,
    final PVectorReadable3DType<T> minimum,
    final PVectorReadable3DType<T> maximum)
  {
    return PVectorM3D.clampByPVector(v, minimum, maximum, v);
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

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampInPlace(
    final V v,
    final double minimum,
    final double maximum)
  {
    return PVectorM3D.clamp(v, minimum, maximum, v);
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

  public static <T, V extends PVectorWritable3DType<T>> V clampMaximum(
    final PVectorReadable3DType<T> v,
    final double maximum,
    final V out)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    out.set3D(x, y, z);
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
   * maximum.z))}
   */

  public static <T, V extends PVectorWritable3DType<T>> V clampMaximumByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> maximum,
    final V out)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    out.set3D(x, y, z);
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
   * maximum.z))}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampMaximumByPVectorInPlace(
    final V v,
    final PVectorReadable3DType<T> maximum)
  {
    return PVectorM3D.clampMaximumByPVector(v, maximum, v);
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

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampMaximumInPlace(
    final V v,
    final double maximum)
  {
    return PVectorM3D.clampMaximum(v, maximum, v);
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

  public static <T, V extends PVectorWritable3DType<T>> V clampMinimum(
    final PVectorReadable3DType<T> v,
    final double minimum,
    final V out)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    out.set3D(x, y, z);
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
   * minimum.z))}
   */

  public static <T, V extends PVectorWritable3DType<T>> V clampMinimumByPVector(
    final PVectorReadable3DType<T> v,
    final PVectorReadable3DType<T> minimum,
    final V out)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    out.set3D(x, y, z);
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
   * minimum.z))} , in {@code v}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampMinimumByPVectorInPlace(
    final V v,
    final PVectorReadable3DType<T> minimum)
  {
    return PVectorM3D.clampMinimumByPVector(v, minimum, v);
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

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V clampMinimumInPlace(
    final V v,
    final double minimum)
  {
    return PVectorM3D.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <T>    A phantom type parameter
   *
   * @return output
   */

  public static <T> PVectorWritable3DType<T> copy(
    final PVectorReadable3DType<T> input,
    final PVectorWritable3DType<T> output)
  {
    output.set3D(input.getXD(), input.getYD(), input.getZD());
    return output;
  }

  /**
   * Return a vector perpendicular to both {@code v0} and {@code v1} , saving
   * the result in {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param <T> A phantom type parameter
   * @param <V> The precise type of vector
   *
   * @return out
   */

  public static <T, V extends PVectorWritable3DType<T>> V crossProduct(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final V out)
  {
    final double x = (v0.getYD() * v1.getZD()) - (v0.getZD() * v1.getYD());
    final double y = (v0.getZD() * v1.getXD()) - (v0.getXD() * v1.getZD());
    final double z = (v0.getXD() * v1.getYD()) - (v0.getYD() * v1.getXD());
    out.set3D(x, y, z);
    return out;
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
    final ContextPVM3D c,
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    final PVectorM3D<T> vr = (PVectorM3D<T>) c.va;
    return PVectorM3D.magnitude(PVectorM3D.subtract(v0, v1, vr));
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
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
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

  public static <T, V extends PVectorWritable3DType<T>> V interpolateLinear(
    final ContextPVM3D c,
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final double alpha,
    final V r)
  {
    final PVectorM3D<T> va = (PVectorM3D<T>) c.va;
    final PVectorM3D<T> vb = (PVectorM3D<T>) c.vb;
    PVectorM3D.scale(v0, 1.0 - alpha, va);
    PVectorM3D.scale(v1, alpha, vb);
    return PVectorM3D.add(va, vb, r);
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
    final PVectorReadable3DType<T> v)
  {
    return Math.sqrt(PVectorM3D.magnitudeSquared(v));
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
    final PVectorReadable3DType<T> v)
  {
    return PVectorM3D.dotProduct(v, v);
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

  public static <T, V extends PVectorWritable3DType<T>> V normalize(
    final PVectorReadable3DType<T> v,
    final V out)
  {
    final double m = PVectorM3D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM3D.scale(v, reciprocal, out);
    }
    out.set3D(v.getXD(), v.getYD(), v.getZD());
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

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V normalizeInPlace(
    final V v)
  {
    return PVectorM3D.normalize(v, v);
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

  public static <T, V extends PVectorWritable3DType<T>> void orthoNormalize(
    final ContextPVM3D c,
    final PVectorReadable3DType<T> v0,
    final V v0_out,
    final PVectorReadable3DType<T> v1,
    final V v1_out)
  {
    final PVectorM3D<T> va = (PVectorM3D<T>) c.va;
    final PVectorM3D<T> vb = (PVectorM3D<T>) c.vb;
    final PVectorM3D<T> vc = (PVectorM3D<T>) c.vc;
    PVectorM3D.normalize(v0, va);
    PVectorM3D.scale(va, PVectorM3D.dotProduct(v1, va), vb);
    PVectorM3D.normalizeInPlace(PVectorM3D.subtract(v1, vb, vc));
    PVectorM3D.copy(va, v0_out);
    PVectorM3D.copy(vc, v1_out);
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

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> void orthoNormalizeInPlace(
    final ContextPVM3D c,
    final V v0,
    final V v1)
  {
    final PVectorM3D<T> va = (PVectorM3D<T>) c.va;
    final PVectorM3D<T> vb = (PVectorM3D<T>) c.vb;
    final PVectorM3D<T> vc = (PVectorM3D<T>) c.vc;
    PVectorM3D.normalize(v0, va);
    PVectorM3D.scale(va, PVectorM3D.dotProduct(v1, va), vb);
    PVectorM3D.normalizeInPlace(PVectorM3D.subtract(v1, vb, vc));
    PVectorM3D.copy(va, v0);
    PVectorM3D.copy(vc, v1);
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

  public static <T, V extends PVectorWritable3DType<T>> V projection(
    final PVectorReadable3DType<T> p,
    final PVectorReadable3DType<T> q,
    final V r)
  {
    final double dot = PVectorM3D.dotProduct(p, q);
    final double qms = PVectorM3D.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM3D.scale(p, s, r);
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   */

  public static <T, V extends PVectorWritable3DType<T>> V scale(
    final PVectorReadable3DType<T> v,
    final double r,
    final V out)
  {
    final double x = v.getXD() * r;
    final double y = v.getYD() * r;
    final double z = v.getZD() * r;
    out.set3D(x, y, z);
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V scaleInPlace(
    final V v,
    final double r)
  {
    return PVectorM3D.scale(v, r, v);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   */

  public static <T, V extends PVectorWritable3DType<T>> V subtract(
    final PVectorReadable3DType<T> v0,
    final PVectorReadable3DType<T> v1,
    final V out)
  {
    final double x = v0.getXD() - v1.getXD();
    final double y = v0.getYD() - v1.getYD();
    final double z = v0.getZD() - v1.getZD();
    out.set3D(x, y, z);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   */

  public static <T, V extends PVectorWritable3DType<T> &
    PVectorReadable3DType<T>> V subtractInPlace(
    final V v0,
    final PVectorReadable3DType<T> v1)
  {
    return PVectorM3D.subtract(v0, v1, v0);
  }

  @Override public void copyFrom2D(
    final VectorReadable2DType in_v)
  {
    VectorM2D.copy(in_v, this);
  }

  @Override public void copyFrom3D(
    final VectorReadable3DType in_v)
  {
    VectorM3D.copy(in_v, this);
  }

  @Override public void copyFromTyped2D(
    final PVectorReadable2DType<T> in_v)
  {
    PVectorM2D.copy(in_v, this);
  }

  @Override public void copyFromTyped3D(
    final PVectorReadable3DType<T> in_v)
  {
    PVectorM3D.copy(in_v, this);
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
    final PVectorM3D<?> other = (PVectorM3D<?>) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    return Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z);
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

  @Override public double getZD()
  {
    return this.z;
  }

  @Override public void setZD(
    final double in_z)
  {
    this.z = in_z;
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
    temp = Double.doubleToLongBits(this.z);
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

  @Override public void set3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorM3D ");
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

  public static final class ContextPVM3D
  {
    private final PVectorM3D<?> va;
    private final PVectorM3D<?> vb;
    private final PVectorM3D<?> vc;

    /**
     * Construct preallocated storage.
     */

    public ContextPVM3D()
    {
      this.va = new PVectorM3D<Object>();
      this.vb = new PVectorM3D<Object>();
      this.vc = new PVectorM3D<Object>();
    }
  }
}
