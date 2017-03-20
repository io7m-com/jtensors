PVectorM4(
  scalarType,
  scalarTypeSuffix,
  scalarLargeType,
  scalarClampBoundType,
  scalarDotType,
  scalarDistanceType,
  scalarScaleType,
  className,
  classBaseType,
  classReadableName4,
  classReadableUnparameterizedName4,
  classReadableName3,
  classReadableUnparameterizedName3,
  classReadableName2,
  classReadableUnparameterizedName2,
  classWritableName,
  opClass,
  since,
  hasPartialArithmetic,
  hasDeprecatedScale,
  scalarDeprecatedScaleType,
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

package com.io7m.jtensors.parameterized;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * \<p\>A four-dimensional mutable vector type with {@code <scalarType>} elements.\</p\>
 *
 * \<p\>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * \</p\>
 *
 * @param \<T\> A phantom type parameter
 * @since <since>
 */

public final class <className>\<T\> implements <classBaseType>\<T\>
{
  private <scalarType> w = <opClass>.one();
  private <scalarType> x;
  private <scalarType> y;
  private <scalarType> z;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0, 0, 1]}.
   */

  public <className>()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public <className>(
    final <scalarType> in_x,
    final <scalarType> in_y,
    final <scalarType> in_z,
    final <scalarType> in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code in_v}.
   *
   * @param in_v The source vector
   */

  public <className>(
    final <classReadableName4> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
    this.w = in_v.getW<scalarTypeSuffix>();
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code out}.
   *
   * @param v   The input vector
   * @param out The output vector
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V absolute(
    final <classReadableName4> v,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.absolute(v.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.absolute(v.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.absolute(v.getZ<scalarTypeSuffix>());
    final <scalarType> w = <opClass>.absolute(v.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(x, y, z, w);
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}, saving
   * the result to {@code v}.
   *
   * @param v   The input vector
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\> A phantom type parameter
   * @return {@code true} if the vectors are almost equal
   *
   * @see <approximateEqualityType>
   * @since 5.0.0
   */

  public static \<T\> boolean almostEqual(
    final <approximateEqualityType>.ContextRelative context,
    final <classReadableName4>\<T\> va,
    final <classReadableName4>\<T\> vb)
  {
    final boolean xs =
      <approximateEqualityType>.almostEqual(context, va.getX<scalarTypeSuffix>(), vb.getX<scalarTypeSuffix>());
    final boolean ys =
      <approximateEqualityType>.almostEqual(context, va.getY<scalarTypeSuffix>(), vb.getY<scalarTypeSuffix>());
    final boolean zs =
      <approximateEqualityType>.almostEqual(context, va.getZ<scalarTypeSuffix>(), vb.getZ<scalarTypeSuffix>());
    final boolean ws =
      <approximateEqualityType>.almostEqual(context, va.getW<scalarTypeSuffix>(), vb.getW<scalarTypeSuffix>());
    return xs && ys && zs && ws;
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
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V add(
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.add(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());
    final <scalarType> w = <opClass>.add(v0.getW<scalarTypeSuffix>(), v1.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(x, y, z, w);
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1},
   * saving the result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  addInPlace(
    final V v0,
    final <classReadableName4>\<T\> v1)
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
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V addScaled(
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1,
    final double r,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v1.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v1.getY<scalarTypeSuffix>(), r);
    final <scalarType> mz = <opClass>.multiplyReal(v1.getZ<scalarTypeSuffix>(), r);
    final <scalarType> mw = <opClass>.multiplyReal(v1.getW<scalarTypeSuffix>(), r);
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), mx);
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), my);
    final <scalarType> z = <opClass>.add(v0.getZ<scalarTypeSuffix>(), mz);
    final <scalarType> w = <opClass>.add(v0.getW<scalarTypeSuffix>(), mw);
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r),
   * v0.w + (v1.w * r))}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  addScaledInPlace(
    final V v0,
    final <classReadableName4>\<T\> v1,
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
   * @param \<T\> A phantom type parameter
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clamp(
    final <classReadableName4>\<T\> v,
    final <scalarClampBoundType> minimum,
    final <scalarClampBoundType> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> y = <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> z = <opClass>.minimum(<opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> w = <opClass>.minimum(<opClass>.maximum(v.getW<scalarTypeSuffix>(), minimum), maximum);
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<V>     The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clampByPVector(
    final <classReadableName4>\<T\> v,
    final <classReadableName4>\<T\> minimum,
    final <classReadableName4>\<T\> maximum,
    final V out)
  {
    final <scalarType> x =
      <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>()), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y =
      <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>()), maximum.getY<scalarTypeSuffix>());
    final <scalarType> z =
      <opClass>.minimum(<opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum.getZ<scalarTypeSuffix>()), maximum.getZ<scalarTypeSuffix>());
    final <scalarType> w =
      <opClass>.minimum(<opClass>.maximum(v.getW<scalarTypeSuffix>(), minimum.getW<scalarTypeSuffix>()), maximum.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<T\> A phantom type parameter
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   * minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w,
   * minimum.w), maximum.w))}
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  clampByPVectorInPlace(
    final V v,
    final <classReadableName4>\<T\> minimum,
    final <classReadableName4>\<T\> maximum)
  {
    return clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param \<V>     The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}, in {@code v}
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\> A phantom type parameter
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clampMaximum(
    final <classReadableName4>\<T\> v,
    final <scalarType> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum);
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum);
    final <scalarType> z = <opClass>.minimum(v.getZ<scalarTypeSuffix>(), maximum);
    final <scalarType> w = <opClass>.minimum(v.getW<scalarTypeSuffix>(), maximum);
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<T\> A phantom type parameter
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clampMaximumByPVector(
    final <classReadableName4>\<T\> v,
    final <classReadableName4>\<T\> maximum,
    final V out)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.minimum(v.getZ<scalarTypeSuffix>(), maximum.getZ<scalarTypeSuffix>());
    final <scalarType> w = <opClass>.minimum(v.getW<scalarTypeSuffix>(), maximum.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<T\>     A phantom type parameter
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  clampMaximumByPVectorInPlace(
    final V v,
    final <classReadableName4>\<T\> maximum)
  {
    return clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param \<V>     The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return A vector with both elements equal to at most {@code maximum}, in {@code v}
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\>    A phantom type parameter
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clampMinimum(
    final <classReadableName4>\<T\> v,
    final <scalarType> minimum,
    final V out)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum);
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum);
    final <scalarType> z = <opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum);
    final <scalarType> w = <opClass>.maximum(v.getW<scalarTypeSuffix>(), minimum);
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<T\>    A phantom type parameter
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   */

  public static \<T, V extends <classWritableName>\<T\>\> V clampMinimumByPVector(
    final <classReadableName4>\<T\> v,
    final <classReadableName4>\<T\> minimum,
    final V out)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum.getZ<scalarTypeSuffix>());
    final <scalarType> w = <opClass>.maximum(v.getW<scalarTypeSuffix>(), minimum.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(x, y, z, w);
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
   * @param \<T\> A phantom type parameter
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}, in {@code v}
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  clampMinimumByPVectorInPlace(
    final V v,
    final <classReadableName4>\<T\> minimum)
  {
    return clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive, saving the result to {@code v}.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param \<V>     The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return A vector with both elements equal to at least {@code minimum}, in {@code v}.
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<V\>    The specific vector type
   * @param input  The input vector
   * @param output The output vector
   * @param \<T\> A phantom type parameter
   * @return output
   */

  public static \<T, V extends <classWritableName>\<T\>\> V copy(
    final <classReadableName4> input,
    final V output)
  {
    output.set4<scalarTypeSuffix>(
      input.getX<scalarTypeSuffix>(),
      input.getY<scalarTypeSuffix>(),
      input.getZ<scalarTypeSuffix>(),
      input.getW<scalarTypeSuffix>());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param c  Preallocated storage
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter
   * @return The distance between the two vectors.
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarDistanceType> distance(
    final ContextPVM4<scalarTypeSuffix> c,
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return magnitude(subtract(v0, v1, c.v2a));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter
   * @return The scalar product of the two vectors
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarDotType> dotProduct(
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarLargeType> mx = <opClass>.multiply(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarLargeType> my = <opClass>.multiply(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarLargeType> mz = <opClass>.multiply(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());
    final <scalarLargeType> mw = <opClass>.multiply(v0.getW<scalarTypeSuffix>(), v1.getW<scalarTypeSuffix>());
    return <opClass>.castLargeToDot(<opClass>.addLarge(<opClass>.addLarge(<opClass>.addLarge(mx, my), mz), mw));
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
   * @param \<T\> A phantom type parameter
   * @return {@code r}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   * @since 7.0.0
   */

  public static \<T, V extends <classWritableName>\<T\>\> V interpolateLinear(
    final ContextPVM4<scalarTypeSuffix> c,
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1,
    final double alpha,
    final V r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <className>\<T\> va = (<className>\<T\>) c.v2a;
    final <className>\<T\> vb = (<className>\<T\>) c.v2b;
    scale(v0, 1.0 - alpha, va);
    scale(v1, alpha, vb);
    return add(va, vb, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   * @param \<T\> A phantom type parameter
   * @return The magnitude of the input vector
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarDistanceType> magnitude(
    final <classReadableName4>\<T\> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return <opClass>.squareRoot(magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   * @param \<T\> A phantom type parameter
   * @return The squared magnitude of the input vector
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarDistanceType> magnitudeSquared(
    final <classReadableName4>\<T\> v)
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
   * @param \<T\> A phantom type parameter
   * @return out
   */

  public static \<T, V extends <classWritableName>\<T\>\> V normalize(
    final <classReadableName4>\<T\> v,
    final V out)
  {
    final <scalarLargeType> m = magnitudeSquared(v);
    if (m > 0.0) {
      final <scalarLargeType> reciprocal = 1.0 / Math.sqrt(m);
      return scale(v, reciprocal, out);
    }
    out.set4<scalarTypeSuffix>(
      v.getX<scalarTypeSuffix>(),
      v.getY<scalarTypeSuffix>(),
      v.getZ<scalarTypeSuffix>(),
      v.getW<scalarTypeSuffix>());
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0} in {@code v}. The function returns the zero vector iff
   * the input is the zero vector.
   *
   * @param v   The input vector
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return v
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\> A phantom type parameter
   * @since 7.0.0
   */

  public static \<T, V extends <classReadableName4>\<T\>, U extends <classWritableName>\<T\>\> void orthoNormalize(
    final ContextPVM4<scalarTypeSuffix> c,
    final <classReadableName4>\<T\> v0,
    final U v0_out,
    final <classReadableName4>\<T\> v1,
    final U v1_out)
  {
    normalize(v0, c.v2a);
    scale(c.v2a, dotProduct(v1, c.v2a), c.v2b);
    normalizeInPlace(subtract(v1, c.v2b, c.v2c));
    v0_out.copyFrom4<scalarTypeSuffix>(c.v2a);
    v1_out.copyFrom4<scalarTypeSuffix>(c.v2c);
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
   * @param \<T\> A phantom type parameter
   * @since 7.0.0
   */

  public static
    \<T,
      V extends <classWritableName>\<T\> & <classReadableName4>\<T\>,
      W extends <classWritableName>\<T\> & <classReadableName4>\<T\>\>
  void orthoNormalizeInPlace(
    final ContextPVM4<scalarTypeSuffix> c,
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
   * @param \<T\> A phantom type parameter
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V projection(
    final <classReadableName4>\<T\> p,
    final <classReadableName4>\<T\> q,
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
   * @param \<T\> A phantom type parameter
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V scale(
    final <classReadableName4>\<T\> v,
    final <scalarScaleType> r,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v.getY<scalarTypeSuffix>(), r);
    final <scalarType> mz = <opClass>.multiplyReal(v.getZ<scalarTypeSuffix>(), r);
    final <scalarType> mw = <opClass>.multiplyReal(v.getW<scalarTypeSuffix>(), r);
    out.set4<scalarTypeSuffix>(mx, my, mz, mw);
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the result to
   * {@code v}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\> A phantom type parameter
   * @deprecated Use the {@code scaleInPlace} method with the {@code <scalarScaleType>}-type parameter
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  @Deprecated
  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
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
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\>\> V subtract(
    final <classReadableName4>\<T\> v0,
    final <classReadableName4>\<T\> v1,
    final V out)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.subtract(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> my = <opClass>.subtract(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarType> mz = <opClass>.subtract(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());
    final <scalarType> mw = <opClass>.subtract(v0.getW<scalarTypeSuffix>(), v1.getW<scalarTypeSuffix>());
    out.set4<scalarTypeSuffix>(mx, my, mz, mw);
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}, saving the
   * result to {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param \<V\> The precise type of vector
   * @param \<T\> A phantom type parameter
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an
   *                             overflow or underflow
   * <endif>
   */

  public static \<T, V extends <classWritableName>\<T\> & <classReadableName4>\<T\>\> V
  subtractInPlace(
    final V v0,
    final <classReadableName4>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return subtract(v0, v1, v0);
  }

  @Override
  public void copyFrom2<scalarTypeSuffix>(
    final <classReadableUnparameterizedName2> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
  }

  @Override
  public void copyFrom3<scalarTypeSuffix>(
    final <classReadableUnparameterizedName3> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
  }

  @Override
  public void copyFrom4<scalarTypeSuffix>(
    final <classReadableUnparameterizedName4> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
    this.w = in_v.getW<scalarTypeSuffix>();
  }

  @Override
  public void copyFromTyped2<scalarTypeSuffix>(
    final <classReadableName2>\<T> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
  }

  @Override
  public void copyFromTyped4<scalarTypeSuffix>(
    final <classReadableName4>\<T> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
    this.w = in_v.getW<scalarTypeSuffix>();
  }

  @Override
  public void copyFromTyped3<scalarTypeSuffix>(
    final <classReadableName3>\<T> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
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
    return <opClass>.scalarEquals(this.w, other.w)
        && <opClass>.scalarEquals(this.x, other.x)
        && <opClass>.scalarEquals(this.y, other.y)
        && <opClass>.scalarEquals(this.z, other.z);
  }

  @Override
  public <scalarType> getW<scalarTypeSuffix>()
  {
    return this.w;
  }

  @Override
  public void setW<scalarTypeSuffix>(
    final <scalarType> in_w)
  {
    this.w = in_w;
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
  public <scalarType> getZ<scalarTypeSuffix>()
  {
    return this.z;
  }

  @Override
  public void setZ<scalarTypeSuffix>(
    final <scalarType> in_z)
  {
    this.z = in_z;
  }

  @Override
  public int hashCode()
  {
    return <opClass>.hash4(this.w, this.x, this.y, this.z);
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
  public void set3<scalarTypeSuffix>(
    final <scalarType> in_x,
    final <scalarType> in_y,
    final <scalarType> in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override
  public void set4<scalarTypeSuffix>(
    final <scalarType> in_x,
    final <scalarType> in_y,
    final <scalarType> in_z,
    final <scalarType> in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[<className> ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
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

  public static final class ContextPVM4<scalarTypeSuffix>
  {
    private final <className> v2a;
    private final <className> v2b;
    private final <className> v2c;

    /**
     * Construct preallocated storage.
     */

    public ContextPVM4<scalarTypeSuffix>()
    {
      this.v2a = new <className>();
      this.v2b = new <className>();
      this.v2c = new <className>();
    }
  }
}

>>