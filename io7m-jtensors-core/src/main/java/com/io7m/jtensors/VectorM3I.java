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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p> A three-dimensional mutable vector type with integer elements. </p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorM3I implements Vector3IType
{
  private int x;
  private int y;
  private int z;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0,
   * 0]}.
   */

  public VectorM3I()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public VectorM3I(
    final int in_x,
    final int in_y,
    final int in_z)
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

  public VectorM3I(
    final VectorReadable3IType in_v)
  {
    this.x = in_v.getXI();
    this.y = in_v.getYI();
    this.z = in_v.getZI();
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V absolute(
    final VectorReadable3IType v,
    final V out)
    throws ArithmeticException
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    final int z = CheckedMath.absolute(v.getZI());
    out.set3I(x, y, z);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code v}.
   *
   * @param v   The input vector
   * @param <V> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  absoluteInPlace(
    final V v)
    throws ArithmeticException
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V add(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final V out)
    throws ArithmeticException
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
    final int z = CheckedMath.add(v0.getZI(), v1.getZI());
    out.set3I(x, y, z);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  addInPlace(
    final V v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V addScaled(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final double r,
    final V out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int mz = CheckedMath.multiply(v1.getZI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    final int z = CheckedMath.add(v0.getZI(), mz);
    out.set3I(x, y, z);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  addScaledInPlace(
    final V v0,
    final VectorReadable3IType v1,
    final double r)
    throws ArithmeticException
  {
    return addScaled(v0, v1, r, v0);
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

  public static <V extends VectorWritable3IType> V clamp(
    final VectorReadable3IType v,
    final int minimum,
    final int maximum,
    final V out)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
    final int z = Math.min(Math.max(v.getZI(), minimum), maximum);
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType> V clampByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType minimum,
    final VectorReadable3IType maximum,
    final V out)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
    final int z =
      Math.min(Math.max(v.getZI(), minimum.getZI()), maximum.getZI());
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampByVectorInPlace(
    final V v,
    final VectorReadable3IType minimum,
    final VectorReadable3IType maximum)
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampInPlace(
    final V v,
    final int minimum,
    final int maximum)
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

  public static <V extends VectorWritable3IType> V clampMaximum(
    final VectorReadable3IType v,
    final int maximum,
    final V out)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
    final int z = Math.min(v.getZI(), maximum);
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType> V clampMaximumByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType maximum,
    final V out)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
    final int z = Math.min(v.getZI(), maximum.getZI());
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampMaximumByVectorInPlace(
    final V v,
    final VectorReadable3IType maximum)
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampMaximumInPlace(
    final V v,
    final int maximum)
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

  public static <V extends VectorWritable3IType> V clampMinimum(
    final VectorReadable3IType v,
    final int minimum,
    final V out)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
    final int z = Math.max(v.getZI(), minimum);
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType> V clampMinimumByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType minimum,
    final V out)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
    final int z = Math.max(v.getZI(), minimum.getZI());
    out.set3I(x, y, z);
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampMinimumByVectorInPlace(
    final V v,
    final VectorReadable3IType minimum)
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

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  clampMinimumInPlace(
    final V v,
    final int minimum)
  {
    return clampMinimum(v, minimum, v);
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

  public static <V extends VectorWritable3IType> V copy(
    final VectorReadable3IType input,
    final V output)
  {
    output.set3I(input.getXI(), input.getYI(), input.getZI());
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
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   * @since 7.0.0
   */

  public static int distance(
    final ContextVM3I c,
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static int dotProduct(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    final int mz = CheckedMath.multiply(v0.getZI(), v1.getZI());
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
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
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow.
   * @since 7.0.0
   */

  public static <V extends VectorWritable3IType> V interpolateLinear(
    final ContextVM3I c,
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final double alpha,
    final V r)
    throws ArithmeticException
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static int magnitude(
    final VectorReadable3IType v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt((double) magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static int magnitudeSquared(
    final VectorReadable3IType v)
    throws ArithmeticException
  {
    return dotProduct(v, v);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V projection(
    final VectorReadable3IType p,
    final VectorReadable3IType q,
    final V r)
    throws ArithmeticException
  {
    final int dot = dotProduct(p, q);
    final int qms = magnitudeSquared(q);
    final int s = dot / qms;

    return scale(p, (double) s, r);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V scale(
    final VectorReadable3IType v,
    final double r,
    final V out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    final int mz = CheckedMath.multiply(v.getZI(), r);
    out.set3I(mx, my, mz);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  scaleInPlace(
    final V v,
    final int r)
    throws ArithmeticException
  {
    return scale(v, (double) r, v);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType> V subtract(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final V out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int my = CheckedMath.subtract(v0.getYI(), v1.getYI());
    final int mz = CheckedMath.subtract(v0.getZI(), v1.getZI());
    out.set3I(mx, my, mz);
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
   *
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             integer overflow
   */

  public static <V extends VectorWritable3IType & VectorReadable3IType> V
  subtractInPlace(
    final V v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    return subtract(v0, v1, v0);
  }

  @Override
  public void copyFrom2I(
    final VectorReadable2IType in_v)
  {
    VectorM2I.copy(in_v, this);
  }

  @Override
  public void copyFrom3I(
    final VectorReadable3IType in_v)
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
    final VectorM3I other = (VectorM3I) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return this.z == other.z;
  }

  @Override
  public int getXI()
  {
    return this.x;
  }

  @Override
  public void setXI(
    final int in_x)
  {
    this.x = in_x;
  }

  @Override
  public int getYI()
  {
    return this.y;
  }

  @Override
  public void setYI(
    final int in_y)
  {
    this.y = in_y;
  }

  @Override
  public int getZI()
  {
    return this.z;
  }

  @Override
  public void setZI(
    final int in_z)
  {
    this.z = in_z;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return result;
  }

  @Override
  public void set2I(
    final int in_x,
    final int in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override
  public void set3I(
    final int in_x,
    final int in_y,
    final int in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM3I ");
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

  public static final class ContextVM3I
  {
    private final VectorM3I v2a;
    private final VectorM3I v2b;

    /**
     * Construct preallocated storage.
     */

    public ContextVM3I()
    {
      this.v2a = new VectorM3I();
      this.v2b = new VectorM3I();
    }
  }
}
