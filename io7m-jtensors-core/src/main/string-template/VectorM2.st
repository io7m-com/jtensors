VectorM2(
  scalarType,
  scalarTypeSuffix,
  scalarLargeType,
  scalarClampBoundType,
  scalarDotType,
  scalarDistanceType,
  scalarScaleType,
  className,
  classBaseType,
  classReadableName2,
  classWritableName,
  opClass,
  since,
  hasPartialArithmetic,
  hasDeprecatedScale,
  scalarDeprecatedScaleType,
  hasDeprecatedClamp,
  scalarDeprecatedClampType,
  hasDeprecatedDot,
  scalarDeprecatedDotType,
  hasApproximateEquality,
  approximateEqualityType,
  hasFloatingPoint) ::= <<
/*
 * Copyright © 2016 \<code@io7m.com\> http://io7m.com
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

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * \<p\>A two-dimensional mutable vector type with {@code <scalarType>} elements.\</p\>
 *
 * \<p\>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * \</p\>
 *
 * @since <since>
 */

public final class <className> implements <classBaseType>
{
  private <scalarType> x;
  private <scalarType> y;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0]}.
   */

  public <className>()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   */

  public <className>(
    final <scalarType> in_x,
    final <scalarType> in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code in_v}.
   *
   * @param in_v The source vector
   */

  public <className>(
    final <classReadableName2> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V absolute(
    final <classReadableName2> v,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.absolute(v.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.absolute(v.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code v}.
   *
   * @param v   The input vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (abs v.x, abs v.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  absoluteInPlace(
    final V v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return absolute(v, v);
  }

  <if(hasApproximateEquality)>
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
   * @see <approximateEqualityType>
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final <approximateEqualityType>.ContextRelative context,
    final <classReadableName2> va,
    final <classReadableName2> vb)
  {
    final boolean xs =
      <approximateEqualityType>.almostEqual(context, va.getX<scalarTypeSuffix>(), vb.getX<scalarTypeSuffix>());
    final boolean ys =
      <approximateEqualityType>.almostEqual(context, va.getY<scalarTypeSuffix>(), vb.getY<scalarTypeSuffix>());
    return xs && ys;
  }
  <endif>

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V add(
    final <classReadableName2> v0,
    final <classReadableName2> v1,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  addInPlace(
    final V v0,
    final <classReadableName2> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
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
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V addScaled(
    final <classReadableName2> v0,
    final <classReadableName2> v1,
    final double r,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v1.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v1.getY<scalarTypeSuffix>(), r);
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), mx);
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), my);
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  /**
   * Calculate the angle between vectors {@code v0} and {@code v1}, in radians.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The angle between the two vectors, in radians.
   */

  public static double angle(
    final <classReadableName2> v0,
    final <classReadableName2> v1)
  {
    final double m0 = magnitude(v0);
    final double m1 = magnitude(v1);
    final double raw_dp = dotProduct(v0, v1);
    final double dp = Math.min(Math.max(-1.0, raw_dp), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the result to
   * {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  addScaledInPlace(
    final V v0,
    final <classReadableName2> v1,
    final double r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
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
   * @param \<V\>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static \<V extends <classWritableName>\> V clamp(
    final <classReadableName2> v,
    final <scalarClampBoundType> minimum,
    final <scalarClampBoundType> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> y = <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum), maximum);
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  <if(hasDeprecatedClamp)>
  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param out     The output vector
   * @param \<V\>     The precise type of vector
   * @deprecated Use the {@code clamp} method with the {@code <scalarClampBoundType>}-typed bounds.
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  @Deprecated
  public static \<V extends <classWritableName>\> V clamp(
    final <classReadableName2> v,
    final <scalarDeprecatedClampType> minimum,
    final <scalarDeprecatedClampType> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> y = <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum), maximum);
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }
  <endif>

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}, saving
   * the result to {@code out}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param out     The output vector
   * @param \<V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))}
   */

  public static \<V extends <classWritableName>\> V clampByVector(
    final <classReadableName2> v,
    final <classReadableName2> minimum,
    final <classReadableName2> maximum,
    final V out)
  {
    final <scalarType> x =
      <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>()), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y =
      <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>()), maximum.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(x, y);
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
   * @param \<V>     The precise type of vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))}
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampByVectorInPlace(
    final V v,
    final <classReadableName2> minimum,
    final <classReadableName2> maximum)
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
   * @param \<V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampInPlace(
    final V v,
    final <scalarType> minimum,
    final <scalarType> maximum)
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
   * @param \<V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static \<V extends <classWritableName>\> V clampMaximum(
    final <classReadableName2> v,
    final <scalarType> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum);
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum);
    out.set2<scalarTypeSuffix>(x, y);
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
   * @param \<V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static \<V extends <classWritableName>\> V clampMaximumByVector(
    final <classReadableName2> v,
    final <classReadableName2> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param \<V>     The precise type of vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampMaximumByVectorInPlace(
    final V v,
    final <classReadableName2> maximum)
  {
    return clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param \<V>     The precise type of vector
   *
   * @return A vector with both elements equal to at most {@code maximum}, in {@code v}
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampMaximumInPlace(
    final V v,
    final <scalarType> maximum)
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
   * @param \<V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static \<V extends <classWritableName>\> V clampMinimum(
    final <classReadableName2> v,
    final <scalarType> minimum,
    final V out)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum);
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum);
    out.set2<scalarTypeSuffix>(x, y);
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
   * @param \<V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   */

  public static \<V extends <classWritableName>\> V clampMinimumByVector(
    final <classReadableName2> v,
    final <classReadableName2> minimum,
    final V out)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(x, y);
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}, saving the result to {@code
   * v}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param \<V>     The precise type of vector
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in {@code v}
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampMinimumByVectorInPlace(
    final V v,
    final <classReadableName2> minimum)
  {
    return clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param \<V>     The precise type of vector
   *
   * @return A vector with both elements equal to at least {@code minimum}, in
   * {@code v}.
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  clampMinimumInPlace(
    final V v,
    final <scalarType> minimum)
  {
    return clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector {@code
   * output}.
   *
   * @param \<T>    The specific vector type
   * @param input  The input vector
   * @param output The output vector
   *
   * @return output
   */

  public static \<T extends <classWritableName>\> T copy(
    final <classReadableName2> input,
    final T output)
  {
    output.set2<scalarTypeSuffix>(
      input.getX<scalarTypeSuffix>(),
      input.getY<scalarTypeSuffix>());
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
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static <scalarDistanceType> distance(
    final ContextVM2<scalarTypeSuffix> c,
    final <classReadableName2> v0,
    final <classReadableName2> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
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
   * <if(hasDeprecatedDot)>@deprecated Inconsistent API: to be replaced!<endif>
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  <if(hasDeprecatedDot)>@Deprecated<endif>
  public static
  <if(hasDeprecatedDot)>
    <scalarDeprecatedDotType>
  <else>
    <scalarDotType>
  <endif>
  dotProduct(
    final <classReadableName2> v0,
    final <classReadableName2> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarLargeType> mx = <opClass>.multiply(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarLargeType> my = <opClass>.multiply(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    return <if(hasDeprecatedDot)>(<scalarDeprecatedDotType>)<endif> <opClass>.castLargeToDot(<opClass>.addLarge(mx, my));
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * \<ul>
   *  \<li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}\</li>
   *  \<li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}\</li>
   * \</ul>
   *
   * @param c     Preallocated storage
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param r     The result vector.
   * @param \<V\>   The precise type of vector
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   *
   * @return {@code r}
   *
   * @since 7.0.0
   */

  public static \<V extends <classWritableName>\> V interpolateLinear(
    final ContextVM2<scalarTypeSuffix> c,
    final <classReadableName2> v0,
    final <classReadableName2> v1,
    final double alpha,
    final V r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
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
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static <scalarDistanceType> magnitude(
    final <classReadableName2> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return <opClass>.squareRoot(magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static <scalarDistanceType> magnitudeSquared(
    final <classReadableName2> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return <opClass>.castLargeToDistance(dotProduct(v, v));
  }

  <if(hasFloatingPoint)>
  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code out}. The function returns the zero vector
   * iff the input is the zero vector.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param \<V\> The precise type of vector
   *
   * @return out
   */

  public static \<V extends <classWritableName>\> V normalize(
    final <classReadableName2> v,
    final V out)
  {
    final <scalarLargeType> m = magnitudeSquared(v);
    if (m > 0.0) {
      final <scalarLargeType> reciprocal = 1.0 / Math.sqrt(m);
      return scale(v, reciprocal, out);
    }
    out.set2<scalarTypeSuffix>(
      v.getX<scalarTypeSuffix>(),
      v.getY<scalarTypeSuffix>());
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code v}. The function returns the zero vector iff
   * the input is the zero vector.
   *
   * @param v   The input vector
   * @param \<V\> The precise type of vector
   *
   * @return v
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  normalizeInPlace(
    final V v)
  {
    return normalize(v, v);
  }

  /**
   * \<p>Orthonormalize and return the vectors {@code v0} and {@code v1}.\</p>
   * \<p>See \<a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP\</a>\</p>
   *
   * @param c      Preallocated storage
   * @param v0     The left vector
   * @param v0_out The orthonormalized form of {@code v0}
   * @param v1     The right vector
   * @param v1_out The orthonormalized form of {@code v1}
   * @param \<V\>    The precise type of readable vectors
   * @param \<U\>    The precise type of writable vectors
   *
   * @since 7.0.0
   */

  public static \<V extends <classReadableName2>, U extends <classWritableName>\>
  void orthoNormalize(
    final ContextVM2<scalarTypeSuffix> c,
    final <classReadableName2> v0,
    final U v0_out,
    final <classReadableName2> v1,
    final U v1_out)
  {
    normalize(v0, c.v2a);
    scale(c.v2a, dotProduct(v1, c.v2a), c.v2b);
    normalizeInPlace(subtract(v1, c.v2b, c.v2c));
    v0_out.copyFrom2<scalarTypeSuffix>(c.v2a);
    v1_out.copyFrom2<scalarTypeSuffix>(c.v2c);
  }

  /**
   * \<p>Orthonormalize and the vectors {@code v0} and {@code v1}.\</p>
   * \<p>See \<a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP\</a>\</p>
   *
   * @param c   Preallocated storage
   * @param v0  The left vector
   * @param v1  The right vector
   * @param \<V\> The precise type of readable/writable vector
   * @param \<W\> The precise type of readable/writable vector
   *
   * @since 7.0.0
   */

  public static
    \<V extends <classWritableName> & <classReadableName2>,
      W extends <classWritableName> & <classReadableName2>\>
  void orthoNormalizeInPlace(
    final ContextVM2<scalarTypeSuffix> c,
    final V v0,
    final W v1)
  {
    normalizeInPlace(v0);
    scale(v0, dotProduct(v1, v0), c.v2a);
    subtractInPlace(v1, c.v2a);
    normalizeInPlace(v1);
  }
  <endif>

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q},
   * saving the result in {@code r}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param r   The output vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V projection(
    final <classReadableName2> p,
    final <classReadableName2> q,
    final V r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarLargeType> dot = dotProduct(p, q);
    final <scalarLargeType> qms = magnitudeSquared(q);
    final <scalarLargeType> s = dot / qms;

    return scale(p, (double) s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code out}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param out The output vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V scale(
    final <classReadableName2> v,
    final <scalarScaleType> r,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v.getY<scalarTypeSuffix>(), r);
    out.set2<scalarTypeSuffix>(mx, my);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v.x * r, v.y * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  scaleInPlace(
    final V v,
    final <scalarScaleType> r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return scale(v, r, v);
  }

  <if(hasDeprecatedScale)>
  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param \<V\> The precise type of vector
   *
   * @deprecated Use the {@code scaleInPlace} method with the {@code <scalarScaleType>}-type parameter
   * @return {@code (v.x * r, v.y * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  @Deprecated
  public static \<V extends <classWritableName> & <classReadableName2>\> V
  scaleInPlace(
    final V v,
    final <scalarDeprecatedScaleType> r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return scale(v, (<scalarScaleType>) r, v);
  }
  <endif>

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code out}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param out The output vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName>\> V subtract(
    final <classReadableName2> v0,
    final <classReadableName2> v1,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.subtract(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> my = <opClass>.subtract(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    out.set2<scalarTypeSuffix>(mx, my);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param \<V\> The precise type of vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<V extends <classWritableName> & <classReadableName2>\> V
  subtractInPlace(
    final V v0,
    final <classReadableName2> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return subtract(v0, v1, v0);
  }

  @Override
  public void copyFrom2<scalarTypeSuffix>(
    final <classReadableName2> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
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
    final <className> other = (<className>) obj;
    return <opClass>.scalarEquals(this.x, other.x)
        && <opClass>.scalarEquals(this.y, other.y);
  }

  @Override
  public <scalarType> getX<scalarTypeSuffix>()
  {
    return this.x;
  }

  @Override
  public void setX<scalarTypeSuffix>(
    final <scalarType> in_x)
  {
    this.x = in_x;
  }

  @Override
  public <scalarType> getY<scalarTypeSuffix>()
  {
    return this.y;
  }

  @Override
  public void setY<scalarTypeSuffix>(
    final <scalarType> in_y)
  {
    this.y = in_y;
  }

  @Override
  public int hashCode()
  {
    return <opClass>.hash2(this.x, this.y);
  }

  @Override
  public void set2<scalarTypeSuffix>(
    final <scalarType> in_x,
    final <scalarType> in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[<className> ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
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

  public static final class ContextVM2<scalarTypeSuffix>
  {
    private final <className> v2a;
    private final <className> v2b;
    private final <className> v2c;

    /**
     * Construct preallocated storage.
     */

    public ContextVM2<scalarTypeSuffix>()
    {
      this.v2a = new <className>();
      this.v2b = new <className>();
      this.v2c = new <className>();
    }
  }
}

>>