/*
 * Copyright © 2012 http://io7m.com
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

import com.io7m.jaux.CheckedMath;

/**
 * A three-dimensional mutable vector type with integer elements.
 */

public final class VectorM3I implements VectorReadable3I
{
  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I absolute(
    final VectorM3I v,
    final VectorM3I out)
  {
    final int x = CheckedMath.absolute(v.x);
    final int y = CheckedMath.absolute(v.y);
    final int z = CheckedMath.absolute(v.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>, saving the result into
   *         <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I absoluteInPlace(
    final VectorM3I v)
  {
    return VectorM3I.absolute(v, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I add(
    final VectorM3I v0,
    final VectorM3I v1,
    final VectorM3I out)
  {
    final int x = CheckedMath.add(v0.x, v1.x);
    final int y = CheckedMath.add(v0.y, v1.y);
    final int z = CheckedMath.add(v0.z, v1.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code> , saving the
   *         result in <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I addInPlace(
    final VectorM3I v0,
    final VectorM3I v1)
  {
    return VectorM3I.add(v0, v1, v0);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I addScaled(
    final VectorM3I v0,
    final VectorM3I v1,
    final double r,
    final VectorM3I out)
  {
    final int mx = CheckedMath.multiply(v1.x, r);
    final int my = CheckedMath.multiply(v1.y, r);
    final int mz = CheckedMath.multiply(v1.z, r);
    final int x = CheckedMath.add(v0.x, mx);
    final int y = CheckedMath.add(v0.y, my);
    final int z = CheckedMath.add(v0.z, mz);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   *         , saving the result to <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I addScaledInPlace(
    final VectorM3I v0,
    final VectorM3I v1,
    final double r)
  {
    return VectorM3I.addScaled(v0, v1, r, v0);
  }

  private static int cast(
    final double x)
  {
    return (int) Math.round(x);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @param maximum
   *          The maximum allowed value.
   * @param out
   *          The output vector.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>.
   */

  public static VectorM3I clamp(
    final VectorM3I v,
    final int minimum,
    final int maximum,
    final VectorM3I out)
  {
    final int x = Math.min(Math.max(v.x, minimum), maximum);
    final int y = Math.min(Math.max(v.y, minimum), maximum);
    final int z = Math.min(Math.max(v.z, minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z)))</code>
   */

  public static VectorM3I clampByVector(
    final VectorM3I v,
    final VectorM3I minimum,
    final VectorM3I maximum,
    final VectorM3I out)
  {
    final int x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final int y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final int z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z)))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3I clampByVectorInPlace(
    final VectorM3I v,
    final VectorM3I minimum,
    final VectorM3I maximum)
  {
    return VectorM3I.clampByVector(v, minimum, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM3I clampInPlace(
    final VectorM3I v,
    final int minimum,
    final int maximum)
  {
    return VectorM3I.clamp(v, minimum, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         .
   */

  public static VectorM3I clampMaximum(
    final VectorM3I v,
    final int maximum,
    final VectorM3I out)
  {
    final int x = Math.min(v.x, maximum);
    final int y = Math.min(v.y, maximum);
    final int z = Math.min(v.z, maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static VectorM3I clampMaximumByVector(
    final VectorM3I v,
    final VectorM3I maximum,
    final VectorM3I out)
  {
    final int x = Math.min(v.x, maximum.x);
    final int y = Math.min(v.y, maximum.y);
    final int z = Math.min(v.z, maximum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3I clampMaximumByVectorInPlace(
    final VectorM3I v,
    final VectorM3I maximum)
  {
    return VectorM3I.clampMaximumByVector(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>.
   */

  public static VectorM3I clampMaximumInPlace(
    final VectorM3I v,
    final int maximum)
  {
    return VectorM3I.clampMaximum(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   */

  public static VectorM3I clampMinimum(
    final VectorM3I v,
    final int minimum,
    final VectorM3I out)
  {
    final int x = Math.max(v.x, minimum);
    final int y = Math.max(v.y, minimum);
    final int z = Math.max(v.z, minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static VectorM3I clampMinimumByVector(
    final VectorM3I v,
    final VectorM3I minimum,
    final VectorM3I out)
  {
    final int x = Math.max(v.x, minimum.x);
    final int y = Math.max(v.y, minimum.y);
    final int z = Math.max(v.z, minimum.z);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   *         , in <code>v</code>.
   */

  public static VectorM3I clampMinimumByVectorInPlace(
    final VectorM3I v,
    final VectorM3I minimum)
  {
    return VectorM3I.clampMinimumByVector(v, minimum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM3I clampMinimumInPlace(
    final VectorM3I v,
    final int minimum)
  {
    return VectorM3I.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param input
   *          The input vector.
   * @param output
   *          The output vector.
   * @return output
   */

  public static VectorM3I copy(
    final VectorReadable3I input,
    final VectorM3I output)
  {
    output.x = input.getXI();
    output.y = input.getYI();
    output.z = input.getZI();
    return output;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The distance between the two vectors.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static int distance(
    final VectorM3I v0,
    final VectorM3I v1)
  {
    final VectorM3I vr = new VectorM3I();
    return VectorM3I.magnitude(VectorM3I.subtract(v0, v1, vr));
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return The scalar product of the two vectors.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static int dotProduct(
    final VectorM3I v0,
    final VectorM3I v1)
  {
    final int mx = CheckedMath.multiply(v0.x, v1.x);
    final int my = CheckedMath.multiply(v0.y, v1.y);
    final int mz = CheckedMath.multiply(v0.z, v1.z);
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I interpolateLinear(
    final VectorM3I v0,
    final VectorM3I v1,
    final double alpha,
    final VectorM3I r)
  {
    final VectorM3I w0 = new VectorM3I();
    final VectorM3I w1 = new VectorM3I();

    VectorM3I.scale(v0, 1.0 - alpha, w0);
    VectorM3I.scale(v1, alpha, w1);

    return VectorM3I.add(w0, w1, r);
  }

  /**
   * @param v
   *          The input vector.
   * @return The magnitude of the input vector.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static int magnitude(
    final VectorM3I v)
  {
    return VectorM3I.cast(Math.sqrt(VectorM3I.magnitudeSquared(v)));
  }

  /**
   * @param v
   *          The input vector.
   * @return The squared magnitude of the input vector.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static int magnitudeSquared(
    final VectorM3I v)
  {
    return VectorM3I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I projection(
    final VectorM3I p,
    final VectorM3I q,
    final VectorM3I r)
  {
    final int dot = VectorM3I.dotProduct(p, q);
    final int qms = VectorM3I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM3I.scale(p, s, r);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I scale(
    final VectorM3I v,
    final double r,
    final VectorM3I out)
  {
    final int mx = CheckedMath.multiply(v.x, r);
    final int my = CheckedMath.multiply(v.y, r);
    final int mz = CheckedMath.multiply(v.z, r);
    out.x = mx;
    out.y = my;
    out.z = mz;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>, saving the result into
   *         <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I scaleInPlace(
    final VectorM3I v,
    final int r)
  {
    return VectorM3I.scale(v, r, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I subtract(
    final VectorM3I v0,
    final VectorM3I v1,
    final VectorM3I out)
  {
    final int mx = CheckedMath.subtract(v0.x, v1.x);
    final int my = CheckedMath.subtract(v0.y, v1.y);
    final int mz = CheckedMath.subtract(v0.z, v1.z);
    out.x = mx;
    out.y = my;
    out.z = mz;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code> , saving the
   *         result into <code>out</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3I subtractInPlace(
    final VectorM3I v0,
    final VectorM3I v1)
  {
    return VectorM3I.subtract(v0, v1, v0);
  }

  public int x = 0;

  public int y = 0;

  public int z = 0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorM3I()
  {

  }

  public VectorM3I(
    final int x,
    final int y,
    final int z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public VectorM3I(
    final VectorReadable3I v)
  {
    this.x = v.getXI();
    this.y = v.getYI();
    this.z = v.getZI();
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int getZI()
  {
    return this.z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM3I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
