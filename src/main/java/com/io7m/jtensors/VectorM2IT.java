/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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
 * <p>
 * A two-dimensional mutable vector type with integer elements.
 * </p>
 * <p>
 * The intention of the type parameter <code>A</code> is to be used as a
 * "phantom type" or compile-time tag to statically distinguish between
 * semantically distinct vectors.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

@NotThreadSafe public final class VectorM2IT<A> implements
  VectorReadable2IT<A>
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
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> absolute(
    final @Nonnull VectorReadable2IT<A> v,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * saving the result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> absoluteInPlace(
    final @Nonnull VectorM2IT<A> v)
  {
    return VectorM2IT.absolute(v, v);
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> add(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
    out.x = x;
    out.y = y;
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> addInPlace(
    final @Nonnull VectorM2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1)
  {
    return VectorM2IT.add(v0, v1, v0);
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> addScaled(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1,
    final double r,
    final @Nonnull VectorM2IT<A> out)
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    out.x = x;
    out.y = y;
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> addScaledInPlace(
    final @Nonnull VectorM2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1,
    final double r)
  {
    return VectorM2IT.addScaled(v0, v1, r, v0);
  }

  /**
   * Calculate the angle between vectors <code>v0</code> and <code>v1</code>,
   * in radians.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return The angle between the two vectors, in radians.
   */

  public static <A> double angle(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1)
  {
    final double m0 = VectorM2IT.magnitude(v0);
    final double m1 = VectorM2IT.magnitude(v1);
    return Math.acos(VectorM2IT.dotProduct(v0, v1) / (m0 * m1));
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

  public static @Nonnull <A> VectorM2IT<A> clamp(
    final @Nonnull VectorReadable2IT<A> v,
    final int minimum,
    final int maximum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
    out.x = x;
    out.y = y;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampByVector(
    final @Nonnull VectorReadable2IT<A> v,
    final @Nonnull VectorReadable2IT<A> minimum,
    final @Nonnull VectorReadable2IT<A> maximum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
    out.x = x;
    out.y = y;
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampByVectorInPlace(
    final @Nonnull VectorM2IT<A> v,
    final @Nonnull VectorReadable2IT<A> minimum,
    final @Nonnull VectorReadable2IT<A> maximum)
  {
    return VectorM2IT.clampByVector(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM2IT<A> clampInPlace(
    final @Nonnull VectorM2IT<A> v,
    final int minimum,
    final int maximum)
  {
    return VectorM2IT.clamp(v, minimum, maximum, v);
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

  public static @Nonnull <A> VectorM2IT<A> clampMaximum(
    final @Nonnull VectorReadable2IT<A> v,
    final int maximum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
    out.x = x;
    out.y = y;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampMaximumByVector(
    final @Nonnull VectorReadable2IT<A> v,
    final @Nonnull VectorReadable2IT<A> maximum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
    out.x = x;
    out.y = y;
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampMaximumByVectorInPlace(
    final @Nonnull VectorM2IT<A> v,
    final @Nonnull VectorReadable2IT<A> maximum)
  {
    return VectorM2IT.clampMaximumByVector(v, maximum, v);
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

  public static @Nonnull <A> VectorM2IT<A> clampMaximumInPlace(
    final @Nonnull VectorM2IT<A> v,
    final int maximum)
  {
    return VectorM2IT.clampMaximum(v, maximum, v);
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

  public static @Nonnull <A> VectorM2IT<A> clampMinimum(
    final @Nonnull VectorReadable2IT<A> v,
    final int minimum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
    out.x = x;
    out.y = y;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampMinimumByVector(
    final @Nonnull VectorReadable2IT<A> v,
    final @Nonnull VectorReadable2IT<A> minimum,
    final @Nonnull VectorM2IT<A> out)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
    out.x = x;
    out.y = y;
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   *         , in <code>v</code>
   */

  public static @Nonnull <A> VectorM2IT<A> clampMinimumByVectorInPlace(
    final @Nonnull VectorM2IT<A> v,
    final @Nonnull VectorReadable2IT<A> minimum)
  {
    return VectorM2IT.clampMinimumByVector(v, minimum, v);
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

  public static @Nonnull <A> VectorM2IT<A> clampMinimumInPlace(
    final @Nonnull VectorM2IT<A> v,
    final int minimum)
  {
    return VectorM2IT.clampMinimum(v, minimum, v);
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

  public static @Nonnull <A> VectorM2IT<A> copy(
    final @Nonnull VectorReadable2IT<A> input,
    final @Nonnull VectorM2IT<A> output)
  {
    output.x = input.getXI();
    output.y = input.getYI();
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

  public static <A> int distance(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1)
  {
    final @Nonnull VectorM2IT<A> vr = new VectorM2IT<A>();
    return VectorM2IT.magnitude(VectorM2IT.subtract(v0, v1, vr));
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

  public static <A> int dotProduct(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1)
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    return CheckedMath.add(mx, my);
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
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static @Nonnull <A> VectorM2IT<A> interpolateLinear(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1,
    final double alpha,
    final @Nonnull VectorM2IT<A> r)
  {
    final @Nonnull VectorM2IT<A> w0 = new VectorM2IT<A>();
    final @Nonnull VectorM2IT<A> w1 = new VectorM2IT<A>();

    VectorM2IT.scale(v0, 1.0 - alpha, w0);
    VectorM2IT.scale(v1, alpha, w1);

    return VectorM2IT.add(w0, w1, r);
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

  public static <A> int magnitude(
    final @Nonnull VectorReadable2IT<A> v)
  {
    return VectorM2IT.cast(Math.sqrt(VectorM2IT.magnitudeSquared(v)));
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

  public static <A> int magnitudeSquared(
    final @Nonnull VectorReadable2IT<A> v)
  {
    return VectorM2IT.dotProduct(v, v);
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
  public static @Nonnull <A> VectorM2IT<A> projection(
    final @Nonnull VectorReadable2IT<A> p,
    final @Nonnull VectorReadable2IT<A> q,
    final @Nonnull VectorM2IT<A> r)
  {
    final int dot = VectorM2IT.dotProduct(p, q);
    final int qms = VectorM2IT.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM2IT.scale(p, s, r);
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
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> scale(
    final @Nonnull VectorReadable2IT<A> v,
    final double r,
    final @Nonnull VectorM2IT<A> out)
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    out.x = mx;
    out.y = my;
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
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> scaleInPlace(
    final @Nonnull VectorM2IT<A> v,
    final int r)
  {
    return VectorM2IT.scale(v, r, v);
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> subtract(
    final @Nonnull VectorReadable2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1,
    final @Nonnull VectorM2IT<A> out)
  {
    final int mx = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int my = CheckedMath.subtract(v0.getYI(), v1.getYI());
    out.x = mx;
    out.y = my;
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
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static @Nonnull <A> VectorM2IT<A> subtractInPlace(
    final @Nonnull VectorM2IT<A> v0,
    final @Nonnull VectorReadable2IT<A> v1)
  {
    return VectorM2IT.subtract(v0, v1, v0);
  }

  private int x = 0;
  private int y = 0;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorM2IT()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM2IT(
    final int x,
    final int y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorM2IT(
    final @Nonnull VectorReadable2IT<A> v)
  {
    this.x = v.getXI();
    this.y = v.getYI();
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

    @SuppressWarnings("unchecked") final @Nonnull VectorM2IT<A> other =
      (VectorM2IT<A>) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    return result;
  }

  /**
   * Set the value of the <code>x</code> component to the given value.
   */

  public void setXI(
    final int x)
  {
    this.x = x;
  }

  /**
   * Set the value of the <code>y</code> component to the given value.
   */

  public void setYI(
    final int y)
  {
    this.y = y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2IT ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
