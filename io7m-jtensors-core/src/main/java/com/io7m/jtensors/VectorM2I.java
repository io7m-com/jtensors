/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A two-dimensional mutable vector type with integer elements.
 * </p>
 * 
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

 public class VectorM2I implements
  VectorReadable2IType,
  VectorWritable2IType
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

  public final static  VectorM2I absolute(
    final  VectorReadable2IType v,
    final  VectorM2I out)
    throws ArithmeticException
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

  public final static  VectorM2I absoluteInPlace(
    final  VectorM2I v)
    throws ArithmeticException
  {
    return VectorM2I.absolute(v, v);
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

  public final static  VectorM2I add(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1,
    final  VectorM2I out)
    throws ArithmeticException
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

  public final static  VectorM2I addInPlace(
    final  VectorM2I v0,
    final  VectorReadable2IType v1)
    throws ArithmeticException
  {
    return VectorM2I.add(v0, v1, v0);
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

  public final static  VectorM2I addScaled(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1,
    final double r,
    final  VectorM2I out)
    throws ArithmeticException
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

  public final static  VectorM2I addScaledInPlace(
    final  VectorM2I v0,
    final  VectorReadable2IType v1,
    final double r)
    throws ArithmeticException
  {
    return VectorM2I.addScaled(v0, v1, r, v0);
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

  public final static double angle(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1)
  {
    final double m0 = VectorM2I.magnitude(v0);
    final double m1 = VectorM2I.magnitude(v1);
    return Math.acos(VectorM2I.dotProduct(v0, v1) / (m0 * m1));
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

  public final static  VectorM2I clamp(
    final  VectorReadable2IType v,
    final int minimum,
    final int maximum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampByVector(
    final  VectorReadable2IType v,
    final  VectorReadable2IType minimum,
    final  VectorReadable2IType maximum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampByVectorInPlace(
    final  VectorM2I v,
    final  VectorReadable2IType minimum,
    final  VectorReadable2IType maximum)
  {
    return VectorM2I.clampByVector(v, minimum, maximum, v);
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

  public final static  VectorM2I clampInPlace(
    final  VectorM2I v,
    final int minimum,
    final int maximum)
  {
    return VectorM2I.clamp(v, minimum, maximum, v);
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

  public final static  VectorM2I clampMaximum(
    final  VectorReadable2IType v,
    final int maximum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampMaximumByVector(
    final  VectorReadable2IType v,
    final  VectorReadable2IType maximum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampMaximumByVectorInPlace(
    final  VectorM2I v,
    final  VectorReadable2IType maximum)
  {
    return VectorM2I.clampMaximumByVector(v, maximum, v);
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

  public final static  VectorM2I clampMaximumInPlace(
    final  VectorM2I v,
    final int maximum)
  {
    return VectorM2I.clampMaximum(v, maximum, v);
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

  public final static  VectorM2I clampMinimum(
    final  VectorReadable2IType v,
    final int minimum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampMinimumByVector(
    final  VectorReadable2IType v,
    final  VectorReadable2IType minimum,
    final  VectorM2I out)
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

  public final static  VectorM2I clampMinimumByVectorInPlace(
    final  VectorM2I v,
    final  VectorReadable2IType minimum)
  {
    return VectorM2I.clampMinimumByVector(v, minimum, v);
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

  public final static  VectorM2I clampMinimumInPlace(
    final  VectorM2I v,
    final int minimum)
  {
    return VectorM2I.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param <T>
   *          The specific type of vector
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * 
   * @return output
   */

  public final static <T extends VectorWritable2IType> T copy(
    final VectorReadable2IType input,
    final T output)
  {
    output.set2I(input.getXI(), input.getYI());
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

  public final static int distance(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1)
    throws ArithmeticException
  {
    final  VectorM2I vr = new VectorM2I();
    return VectorM2I.magnitude(VectorM2I.subtract(v0, v1, vr));
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

  public final static int dotProduct(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1)
    throws ArithmeticException
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

  public final static  VectorM2I interpolateLinear(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1,
    final double alpha,
    final  VectorM2I r)
    throws ArithmeticException
  {
    final  VectorM2I w0 = new VectorM2I();
    final  VectorM2I w1 = new VectorM2I();

    VectorM2I.scale(v0, 1.0 - alpha, w0);
    VectorM2I.scale(v1, alpha, w1);

    return VectorM2I.add(w0, w1, r);
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

  public final static int magnitude(
    final  VectorReadable2IType v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt(VectorM2I.magnitudeSquared(v)));
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

  public final static int magnitudeSquared(
    final  VectorReadable2IType v)
    throws ArithmeticException
  {
    return VectorM2I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   */

  public final static  VectorM2I projection(
    final  VectorReadable2IType p,
    final  VectorReadable2IType q,
    final  VectorM2I r)
    throws ArithmeticException
  {
    final int dot = VectorM2I.dotProduct(p, q);
    final int qms = VectorM2I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM2I.scale(p, s, r);
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

  public final static  VectorM2I scale(
    final  VectorReadable2IType v,
    final double r,
    final  VectorM2I out)
    throws ArithmeticException
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

  public final static  VectorM2I scaleInPlace(
    final  VectorM2I v,
    final int r)
    throws ArithmeticException
  {
    return VectorM2I.scale(v, r, v);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>,
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

  public final static  VectorM2I subtract(
    final  VectorReadable2IType v0,
    final  VectorReadable2IType v1,
    final  VectorM2I out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int my = CheckedMath.subtract(v0.getYI(), v1.getYI());
    out.x = mx;
    out.y = my;
    return out;
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>,
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

  public final static  VectorM2I subtractInPlace(
    final  VectorM2I v0,
    final  VectorReadable2IType v1)
    throws ArithmeticException
  {
    return VectorM2I.subtract(v0, v1, v0);
  }

  private int x;
  private int y;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorM2I()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   * 
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   */

  public VectorM2I(
    final int in_x,
    final int in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>in_v</code>.
   * 
   * @param in_v
   *          The source vector
   */

  public VectorM2I(
    final  VectorReadable2IType in_v)
  {
    this.x = in_v.getXI();
    this.y = in_v.getYI();
  }

  @Override public final void copyFrom2I(
    final VectorReadable2IType in_v)
  {
    VectorM2I.copy(in_v, this);
  }

  @Override public final boolean equals(
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
    final  VectorM2I other = (VectorM2I) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
  }

  @Override public final int getXI()
  {
    return this.x;
  }

  @Override public final int getYI()
  {
    return this.y;
  }

  @Override public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    return result;
  }

  @Override public final void set2I(
    final int in_x,
    final int in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public final void setXI(
    final int in_x)
  {
    this.x = in_x;
  }

  @Override public final void setYI(
    final int in_y)
  {
    this.y = in_y;
  }

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
