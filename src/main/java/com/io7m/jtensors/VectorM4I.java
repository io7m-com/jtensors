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

import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.CheckedMath;

/**
 * A four-dimensional mutable vector type with integer elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM4I implements VectorReadable4I
{
  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>(abs v.x, abs v.y, abs v.z, abs.w)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I absolute(
    final VectorM4I v,
    final VectorM4I out)
  {
    final int x = CheckedMath.absolute(v.x);
    final int y = CheckedMath.absolute(v.y);
    final int z = CheckedMath.absolute(v.z);
    final int w = CheckedMath.absolute(v.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>, saving the
   *         result into <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I absoluteInPlace(
    final VectorM4I v)
  {
    return VectorM4I.absolute(v, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I add(
    final VectorM4I v0,
    final VectorM4I v1,
    final VectorM4I out)
  {
    final int x = CheckedMath.add(v0.x, v1.x);
    final int y = CheckedMath.add(v0.y, v1.y);
    final int z = CheckedMath.add(v0.z, v1.z);
    final int w = CheckedMath.add(v0.w, v1.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   *         , saving the result in <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I addInPlace(
    final VectorM4I v0,
    final VectorM4I v1)
  {
    return VectorM4I.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I addScaled(
    final VectorM4I v0,
    final VectorM4I v1,
    final double r,
    final VectorM4I out)
  {
    final int mx = CheckedMath.multiply(v1.x, r);
    final int my = CheckedMath.multiply(v1.y, r);
    final int mz = CheckedMath.multiply(v1.z, r);
    final int mw = CheckedMath.multiply(v1.w, r);
    final int x = CheckedMath.add(v0.x, mx);
    final int y = CheckedMath.add(v0.y, my);
    final int z = CheckedMath.add(v0.z, mz);
    final int w = CheckedMath.add(v0.w, mw);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   *         , saving the result to <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I addScaledInPlace(
    final VectorM4I v0,
    final VectorM4I v1,
    final double r)
  {
    return VectorM4I.addScaled(v0, v1, r, v0);
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

  public static VectorM4I clamp(
    final VectorM4I v,
    final int minimum,
    final int maximum,
    final VectorM4I out)
  {
    final int x = Math.min(Math.max(v.x, minimum), maximum);
    final int y = Math.min(Math.max(v.y, minimum), maximum);
    final int z = Math.min(Math.max(v.z, minimum), maximum);
    final int w = Math.min(Math.max(v.w, minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static VectorM4I clampByVector(
    final VectorM4I v,
    final VectorM4I minimum,
    final VectorM4I maximum,
    final VectorM4I out)
  {
    final int x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final int y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final int z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    final int w = Math.min(Math.max(v.w, minimum.w), maximum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   *         , in <code>v</code>.
   */

  public static VectorM4I clampByVectorInPlace(
    final VectorM4I v,
    final VectorM4I minimum,
    final VectorM4I maximum)
  {
    return VectorM4I.clampByVector(v, minimum, maximum, v);
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

  public static VectorM4I clampInPlace(
    final VectorM4I v,
    final int minimum,
    final int maximum)
  {
    return VectorM4I.clamp(v, minimum, maximum, v);
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

  public static VectorM4I clampMaximum(
    final VectorM4I v,
    final int maximum,
    final VectorM4I out)
  {
    final int x = Math.min(v.x, maximum);
    final int y = Math.min(v.y, maximum);
    final int z = Math.min(v.z, maximum);
    final int w = Math.min(v.w, maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static VectorM4I clampMaximumByVector(
    final VectorM4I v,
    final VectorM4I maximum,
    final VectorM4I out)
  {
    final int x = Math.min(v.x, maximum.x);
    final int y = Math.min(v.y, maximum.y);
    final int z = Math.min(v.z, maximum.z);
    final int w = Math.min(v.w, maximum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   *         , in <code>v</code>.
   */

  public static VectorM4I clampMaximumByVectorInPlace(
    final VectorM4I v,
    final VectorM4I maximum)
  {
    return VectorM4I.clampMaximumByVector(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>.
   */

  public static VectorM4I clampMaximumInPlace(
    final VectorM4I v,
    final int maximum)
  {
    return VectorM4I.clampMaximum(v, maximum, v);
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

  public static VectorM4I clampMinimum(
    final VectorM4I v,
    final int minimum,
    final VectorM4I out)
  {
    final int x = Math.max(v.x, minimum);
    final int y = Math.max(v.y, minimum);
    final int z = Math.max(v.z, minimum);
    final int w = Math.max(v.w, minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static VectorM4I clampMinimumByVector(
    final VectorM4I v,
    final VectorM4I minimum,
    final VectorM4I out)
  {
    final int x = Math.max(v.x, minimum.x);
    final int y = Math.max(v.y, minimum.y);
    final int z = Math.max(v.z, minimum.z);
    final int w = Math.max(v.w, minimum.w);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   *         , in <code>v</code>.
   */

  public static VectorM4I clampMinimumByVectorInPlace(
    final VectorM4I v,
    final VectorM4I minimum)
  {
    return VectorM4I.clampMinimumByVector(v, minimum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM4I clampMinimumInPlace(
    final VectorM4I v,
    final int minimum)
  {
    return VectorM4I.clampMinimum(v, minimum, v);
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

  public static VectorM4I copy(
    final VectorReadable4I input,
    final VectorM4I output)
  {
    output.x = input.getXI();
    output.y = input.getYI();
    output.z = input.getZI();
    output.w = input.getWI();
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
    final VectorM4I v0,
    final VectorM4I v1)
  {
    final VectorM4I vr = new VectorM4I();
    return VectorM4I.magnitude(VectorM4I.subtract(v0, v1, vr));
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
    final VectorM4I v0,
    final VectorM4I v1)
  {
    final int mx = CheckedMath.multiply(v0.x, v1.x);
    final int my = CheckedMath.multiply(v0.y, v1.y);
    final int mz = CheckedMath.multiply(v0.z, v1.z);
    final int mw = CheckedMath.multiply(v0.w, v1.w);
    return CheckedMath.add(CheckedMath.add(CheckedMath.add(mx, my), mz), mw);
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

  public static VectorM4I interpolateLinear(
    final VectorM4I v0,
    final VectorM4I v1,
    final double alpha,
    final VectorM4I r)
  {
    final VectorM4I w0 = new VectorM4I();
    final VectorM4I w1 = new VectorM4I();

    VectorM4I.scale(v0, 1.0 - alpha, w0);
    VectorM4I.scale(v1, alpha, w1);

    return VectorM4I.add(w0, w1, r);
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
    final VectorM4I v)
  {
    return VectorM4I.cast(Math.sqrt(VectorM4I.magnitudeSquared(v)));
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
    final VectorM4I v)
  {
    return VectorM4I.dotProduct(v, v);
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

  public static VectorM4I projection(
    final VectorM4I p,
    final VectorM4I q,
    final VectorM4I r)
  {
    final int dot = VectorM4I.dotProduct(p, q);
    final int qms = VectorM4I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM4I.scale(p, s, r);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I scale(
    final VectorM4I v,
    final double r,
    final VectorM4I out)
  {
    final int mx = CheckedMath.multiply(v.x, r);
    final int my = CheckedMath.multiply(v.y, r);
    final int mz = CheckedMath.multiply(v.z, r);
    final int mw = CheckedMath.multiply(v.w, r);
    out.x = mx;
    out.y = my;
    out.z = mz;
    out.w = mw;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>, saving the
   *         result into <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I scaleInPlace(
    final VectorM4I v,
    final int r)
  {
    return VectorM4I.scale(v, r, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I subtract(
    final VectorM4I v0,
    final VectorM4I v1,
    final VectorM4I out)
  {
    final int mx = CheckedMath.subtract(v0.x, v1.x);
    final int my = CheckedMath.subtract(v0.y, v1.y);
    final int mz = CheckedMath.subtract(v0.z, v1.z);
    final int mw = CheckedMath.subtract(v0.w, v1.w);
    out.x = mx;
    out.y = my;
    out.z = mz;
    out.w = mw;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   *         , saving the result into <code>out</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM4I subtractInPlace(
    final VectorM4I v0,
    final VectorM4I v1)
  {
    return VectorM4I.subtract(v0, v1, v0);
  }

  public int x = 0;

  public int y = 0;

  public int z = 0;

  public int w = 1;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0, 1]</code>.
   */

  public VectorM4I()
  {

  }

  public VectorM4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public VectorM4I(
    final VectorReadable4I v)
  {
    this.x = v.getXI();
    this.y = v.getYI();
    this.z = v.getZI();
    this.w = v.getWI();
  }

  @Override public int getWI()
  {
    return this.w;
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
    builder.append("[VectorM4I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    return builder.toString();
  }
}
