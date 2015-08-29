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

import javax.annotation.Nonnull;
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
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z, abs.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I absolute(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I out)
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
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * saving the result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z, abs.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I absoluteInPlace(
    final @Nonnull VectorM4I v)
  {
    return VectorM4I.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I add(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1,
    final @Nonnull VectorM4I out)
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
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I addInPlace(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1)
  {
    return VectorM4I.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I addScaled(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1,
    final double r,
    final @Nonnull VectorM4I out)
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
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I addScaledInPlace(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1,
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
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @param out
   *          The output vector
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   */

  public static @Nonnull VectorM4I clamp(
    final @Nonnull VectorM4I v,
    final int minimum,
    final int maximum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static @Nonnull VectorM4I clampByVector(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I minimum,
    final @Nonnull VectorM4I maximum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   */

  public static @Nonnull VectorM4I clampByVectorInPlace(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I minimum,
    final @Nonnull VectorM4I maximum)
  {
    return VectorM4I.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>, in <code>v</code>
   */

  public static @Nonnull VectorM4I clampInPlace(
    final @Nonnull VectorM4I v,
    final int minimum,
    final int maximum)
  {
    return VectorM4I.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   */

  public static @Nonnull VectorM4I clampMaximum(
    final @Nonnull VectorM4I v,
    final int maximum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static @Nonnull VectorM4I clampMaximumByVector(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I maximum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   */

  public static @Nonnull VectorM4I clampMaximumByVectorInPlace(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I maximum)
  {
    return VectorM4I.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM4I clampMaximumInPlace(
    final @Nonnull VectorM4I v,
    final int maximum)
  {
    return VectorM4I.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   */

  public static @Nonnull VectorM4I clampMinimum(
    final @Nonnull VectorM4I v,
    final int minimum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   */

  public static @Nonnull VectorM4I clampMinimumByVector(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I minimum,
    final @Nonnull VectorM4I out)
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
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM4I clampMinimumByVectorInPlace(
    final @Nonnull VectorM4I v,
    final @Nonnull VectorM4I minimum)
  {
    return VectorM4I.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static @Nonnull VectorM4I clampMinimumInPlace(
    final @Nonnull VectorM4I v,
    final int minimum)
  {
    return VectorM4I.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * 
   * @return output
   */

  public static @Nonnull VectorM4I copy(
    final @Nonnull VectorReadable4I input,
    final @Nonnull VectorM4I output)
  {
    output.x = input.getXI();
    output.y = input.getYI();
    output.z = input.getZI();
    output.w = input.getWI();
    return output;
  }

  /**
   * Calculate the distance between the two vectors <code>v0</code> and
   * <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return The distance between the two vectors.
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int distance(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1)
  {
    final @Nonnull VectorM4I vr = new VectorM4I();
    return VectorM4I.magnitude(VectorM4I.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors <code>v0</code> and
   * <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return The scalar product of the two vectors
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int dotProduct(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1)
  {
    final int mx = CheckedMath.multiply(v0.x, v1.x);
    final int my = CheckedMath.multiply(v0.y, v1.y);
    final int mz = CheckedMath.multiply(v0.z, v1.z);
    final int mw = CheckedMath.multiply(v0.w, v1.w);
    return CheckedMath.add(CheckedMath.add(CheckedMath.add(mx, my), mz), mw);
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>, saving the result to <code>r</code>.
   * 
   * The <code>alpha</code> parameter controls the degree of interpolation,
   * such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(v0, v1, 0.0, r) -> r = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0, r) -> r = v1</code></li>
   * </ul>
   * 
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * @param r
   *          The result vector.
   * 
   * @return <code>r</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I interpolateLinear(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1,
    final double alpha,
    final @Nonnull VectorM4I r)
  {
    final @Nonnull VectorM4I w0 = new VectorM4I();
    final @Nonnull VectorM4I w1 = new VectorM4I();

    VectorM4I.scale(v0, 1.0 - alpha, w0);
    VectorM4I.scale(v1, alpha, w1);

    return VectorM4I.add(w0, w1, r);
  }

  /**
   * Calculate the magnitude of the vector <code>v</code>.
   * 
   * Correspondingly, <code>magnitude(normalize(v)) == 1.0</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The magnitude of the input vector
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitude(
    final @Nonnull VectorM4I v)
  {
    return VectorM4I.cast(Math.sqrt(VectorM4I.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitudeSquared(
    final @Nonnull VectorM4I v)
  {
    return VectorM4I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I projection(
    final @Nonnull VectorM4I p,
    final @Nonnull VectorM4I q,
    final @Nonnull VectorM4I r)
  {
    final int dot = VectorM4I.dotProduct(p, q);
    final int qms = VectorM4I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM4I.scale(p, s, r);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I scale(
    final @Nonnull VectorM4I v,
    final double r,
    final @Nonnull VectorM4I out)
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
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I scaleInPlace(
    final @Nonnull VectorM4I v,
    final int r)
  {
    return VectorM4I.scale(v, r, v);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I subtract(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1,
    final @Nonnull VectorM4I out)
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
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull VectorM4I subtractInPlace(
    final @Nonnull VectorM4I v0,
    final @Nonnull VectorM4I v1)
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

  /**
   * Construct a vector initialized with the given values.
   */

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

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM4I(
    final @Nonnull VectorReadable4I v)
  {
    this.x = v.getXI();
    this.y = v.getYI();
    this.z = v.getZI();
    this.w = v.getWI();
  }

  @Override public boolean equals(
    final Object obj)
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
    final @Nonnull VectorM4I other = (VectorM4I) obj;
    if (this.w != other.w) {
      return false;
    }
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    return true;
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.w;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return result;
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
