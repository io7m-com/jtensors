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
 * A two-dimensional mutable vector type with integer elements.
 */

public final class VectorM2I implements VectorReadable2I
{
  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @return <code>(abs v.x, abs v.y)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I absolute(
    final VectorM2I v,
    final VectorM2I out)
  {
    final int x = CheckedMath.absolute(v.x);
    final int y = CheckedMath.absolute(v.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @return <code>(abs v.x, abs v.y)</code>, saving the result into
   *         <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I absoluteInPlace(
    final VectorM2I v)
  {
    return VectorM2I.absolute(v, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I add(
    final VectorM2I v0,
    final VectorM2I v1,
    final VectorM2I out)
  {
    final int x = CheckedMath.add(v0.x, v1.x);
    final int y = CheckedMath.add(v0.y, v1.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code> , saving the result in
   *         <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I addInPlace(
    final VectorM2I v0,
    final VectorM2I v1)
  {
    return VectorM2I.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I addScaled(
    final VectorM2I v0,
    final VectorM2I v1,
    final double r,
    final VectorM2I out)
  {
    final int mx = CheckedMath.multiply(v1.x, r);
    final int my = CheckedMath.multiply(v1.y, r);
    final int x = CheckedMath.add(v0.x, mx);
    final int y = CheckedMath.add(v0.y, my);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code> , saving the
   *         result to <code>v0</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I addScaledInPlace(
    final VectorM2I v0,
    final VectorM2I v1,
    final double r)
  {
    return VectorM2I.addScaled(v0, v1, r, v0);
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

  public static VectorM2I clamp(
    final VectorM2I v,
    final int minimum,
    final int maximum,
    final VectorM2I out)
  {
    final int x = Math.min(Math.max(v.x, minimum), maximum);
    final int y = Math.min(Math.max(v.y, minimum), maximum);
    out.x = x;
    out.y = y;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y)))</code>
   */

  public static VectorM2I clampByVector(
    final VectorM2I v,
    final VectorM2I minimum,
    final VectorM2I maximum,
    final VectorM2I out)
  {
    final int x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final int y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))))</code>
   *         , in <code>v</code>.
   */

  public static VectorM2I clampByVectorInPlace(
    final VectorM2I v,
    final VectorM2I minimum,
    final VectorM2I maximum)
  {
    return VectorM2I.clampByVector(v, minimum, maximum, v);
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

  public static VectorM2I clampInPlace(
    final VectorM2I v,
    final int minimum,
    final int maximum)
  {
    return VectorM2I.clamp(v, minimum, maximum, v);
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

  public static VectorM2I clampMaximum(
    final VectorM2I v,
    final int maximum,
    final VectorM2I out)
  {
    final int x = Math.min(v.x, maximum);
    final int y = Math.min(v.y, maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @param out
   *          The output vector.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static VectorM2I clampMaximumByVector(
    final VectorM2I v,
    final VectorM2I maximum,
    final VectorM2I out)
  {
    final int x = Math.min(v.x, maximum.x);
    final int y = Math.min(v.y, maximum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The vector containing the maximum acceptable values.
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code> , in
   *         <code>v</code>.
   */

  public static VectorM2I clampMaximumByVectorInPlace(
    final VectorM2I v,
    final VectorM2I maximum)
  {
    return VectorM2I.clampMaximumByVector(v, maximum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param maximum
   *          The maximum allowed value.
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>.
   */

  public static VectorM2I clampMaximumInPlace(
    final VectorM2I v,
    final int maximum)
  {
    return VectorM2I.clampMaximum(v, maximum, v);
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

  public static VectorM2I clampMinimum(
    final VectorM2I v,
    final int minimum,
    final VectorM2I out)
  {
    final int x = Math.max(v.x, minimum);
    final int y = Math.max(v.y, minimum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>
   */

  public static VectorM2I clampMinimumByVector(
    final VectorM2I v,
    final VectorM2I minimum,
    final VectorM2I out)
  {
    final int x = Math.max(v.x, minimum.x);
    final int y = Math.max(v.y, minimum.y);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The vector containing the minimum acceptable values.
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code> , in
   *         <code>v</code>.
   */

  public static VectorM2I clampMinimumByVectorInPlace(
    final VectorM2I v,
    final VectorM2I minimum)
  {
    return VectorM2I.clampMinimumByVector(v, minimum, v);
  }

  /**
   * @param v
   *          The input vector.
   * @param minimum
   *          The minimum allowed value.
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static VectorM2I clampMinimumInPlace(
    final VectorM2I v,
    final int minimum)
  {
    return VectorM2I.clampMinimum(v, minimum, v);
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

  public static VectorM2I copy(
    final VectorReadable2I input,
    final VectorM2I output)
  {
    output.x = input.getXi();
    output.y = input.getYi();
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
    final VectorM2I v0,
    final VectorM2I v1)
  {
    final VectorM2I vr = new VectorM2I();
    return VectorM2I.magnitude(VectorM2I.subtract(v0, v1, vr));
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
    final VectorM2I v0,
    final VectorM2I v1)
  {
    final int mx = CheckedMath.multiply(v0.x, v1.x);
    final int my = CheckedMath.multiply(v0.y, v1.y);
    return CheckedMath.add(mx, my);
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

  public static VectorM2I interpolateLinear(
    final VectorM2I v0,
    final VectorM2I v1,
    final double alpha,
    final VectorM2I r)
  {
    final VectorM2I w0 = new VectorM2I();
    final VectorM2I w1 = new VectorM2I();

    VectorM2I.scale(v0, 1.0 - alpha, w0);
    VectorM2I.scale(v1, alpha, w1);

    return VectorM2I.add(w0, w1, r);
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
    final VectorM2I v)
  {
    return VectorM2I.cast(Math.sqrt(VectorM2I.magnitudeSquared(v)));
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
    final VectorM2I v)
  {
    return VectorM2I.dotProduct(v, v);
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

  public static VectorM2I projection(
    final VectorM2I p,
    final VectorM2I q,
    final VectorM2I r)
  {
    final int dot = VectorM2I.dotProduct(p, q);
    final int qms = VectorM2I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM2I.scale(p, s, r);
  }

  /**
   * @param v
   *          The input vector.
   * @param out
   *          The output vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I scale(
    final VectorM2I v,
    final double r,
    final VectorM2I out)
  {
    final int mx = CheckedMath.multiply(v.x, r);
    final int my = CheckedMath.multiply(v.y, r);
    out.x = mx;
    out.y = my;
    return out;
  }

  /**
   * @param v
   *          The input vector.
   * @param r
   *          The scaling value.
   * @return <code>(v.x * r, v.y * r)</code>, saving the result into
   *         <code>v</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I scaleInPlace(
    final VectorM2I v,
    final int r)
  {
    return VectorM2I.scale(v, r, v);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I subtract(
    final VectorM2I v0,
    final VectorM2I v1,
    final VectorM2I out)
  {
    final int mx = CheckedMath.subtract(v0.x, v1.x);
    final int my = CheckedMath.subtract(v0.y, v1.y);
    out.x = mx;
    out.y = my;
    return out;
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code> , saving the result into
   *         <code>out</code>.
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I subtractInPlace(
    final VectorM2I v0,
    final VectorM2I v1)
  {
    return VectorM2I.subtract(v0, v1, v0);
  }

  public int x = 0;

  public int y = 0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorM2I()
  {

  }

  public VectorM2I(
    final int x,
    final int y)
  {
    this.x = x;
    this.y = y;
  }

  public VectorM2I(
    final VectorReadable2I v)
  {
    this.x = v.getXi();
    this.y = v.getYi();
  }

  @Override public int getXi()
  {
    return this.x;
  }

  @Override public int getYi()
  {
    return this.y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("VectorM2I [");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
