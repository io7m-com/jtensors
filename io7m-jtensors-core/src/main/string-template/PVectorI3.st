PVectorI3(
  scalarType,
  scalarTypeSuffix,
  scalarScaleType,
  className,
  classBaseType,
  classReadableName3,
  classReadableUnparameterizedName3,
  classReadableName2,
  classReadableUnparameterizedName2,
  opClass,
  since,
  hasPartialArithmetic,
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

import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * \<p\>A three-dimensional immutable vector type with {@code <scalarType>} elements.\</p\>
 *
 * \<p\>Values of this type are immutable and can therefore be safely accessed from multiple threads.\</p\>
 *
 * @param \<T\> A phantom type parameter.
 *
 * @since 7.0.0
 */

@Immutable
public final class <className>\<T\> implements <classBaseType>\<T\>
{
  /**
   * The zero vector.
   */

  public static final <className>\<?\> ZERO = new <className>(<opClass>.zero(), <opClass>.zero(), <opClass>.zero());

  private final <scalarType> x;
  private final <scalarType> y;
  private final <scalarType> z;

  /**
   * Default constructor, initializing the vector with values {@code [0, 0, 0]}.
   */

  public <className>()
  {
    this.x = <opClass>.zero();
    this.y = <opClass>.zero();
    this.z = <opClass>.zero();
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public <className>(
    final <scalarType> in_x,
    final <scalarType> in_y,
    final <scalarType> in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code in_v}.
   *
   * @param in_v The source vector
   */

  public <className>(
    final <classReadableName3>\<T\> in_v)
  {
    this.x = in_v.getX<scalarTypeSuffix>();
    this.y = in_v.getY<scalarTypeSuffix>();
    this.z = in_v.getZ<scalarTypeSuffix>();
  }

  /**
   * @param \<T\> A phantom type parameter.
   *
   * @return The zero vector.
   */

  @SuppressWarnings("unchecked")
  public static \<T\> <className>\<T\> zero()
  {
    return (<className>\<T\>) ZERO;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v The input vector
   * @param \<T\> A phantom type parameter.
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> absolute(
    final <classReadableName3>\<T\> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.absolute(v.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.absolute(v.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.absolute(v.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter.
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> add(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.add(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param r  The scaling value
   * @param \<T\> A phantom type parameter.
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> addScaled(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1,
    final <scalarScaleType> r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v1.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v1.getY<scalarTypeSuffix>(), r);
    final <scalarType> mz = <opClass>.multiplyReal(v1.getZ<scalarTypeSuffix>(), r);
    final <scalarType> x = <opClass>.add(v0.getX<scalarTypeSuffix>(), mx);
    final <scalarType> y = <opClass>.add(v0.getY<scalarTypeSuffix>(), my);
    final <scalarType> z = <opClass>.add(v0.getZ<scalarTypeSuffix>(), mz);
    return new <className>(x, y, z);
  }

  <if(hasApproximateEquality)>
  /**
   * Determine whether or not the vectors {@code va} and {@code vb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param va      The left input vector
   * @param vb      The right input vector
   * @param \<T\>     A phantom type parameter.
   *
   * @return {@code true} iff the vectors are almost equal.
   *
   * @see <approximateEqualityType>
   * @since 7.0.0
   */

  public static \<T\> boolean almostEqual(
    final <approximateEqualityType>.ContextRelative context,
    final <classReadableName3>\<T\> va,
    final <classReadableName3>\<T\> vb)
  {
    final boolean xs =
      <approximateEqualityType>.almostEqual(context, va.getX<scalarTypeSuffix>(), vb.getX<scalarTypeSuffix>());
    final boolean ys =
      <approximateEqualityType>.almostEqual(context, va.getY<scalarTypeSuffix>(), vb.getY<scalarTypeSuffix>());
    final boolean zs =
      <approximateEqualityType>.almostEqual(context, va.getZ<scalarTypeSuffix>(), vb.getZ<scalarTypeSuffix>());
    return xs && ys && zs;
  }
  <endif>

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param \<T\> A phantom type parameter.
   * @return A vector with both elements equal to at most {@code maximum} and at least {@code minimum}
   */

  public static \<T\> <className>\<T\> clamp(
    final <classReadableName3>\<T\> v,
    final <scalarType> minimum,
    final <scalarType> maximum)
  {
    final <scalarType> x = <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> y = <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum), maximum);
    final <scalarType> z = <opClass>.minimum(<opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum), maximum);
    return new <className>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param \<T\> A phantom type parameter.
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static \<T\> <className>\<T\> clampByPVector(
    final <classReadableName3>\<T\> v,
    final <classReadableName3>\<T\> minimum,
    final <classReadableName3>\<T\> maximum)
  {
    final <scalarType> x =
      <opClass>.minimum(<opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>()), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y =
      <opClass>.minimum(<opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>()), maximum.getY<scalarTypeSuffix>());
    final <scalarType> z =
      <opClass>.minimum(<opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum.getZ<scalarTypeSuffix>()), maximum.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param \<T\> A phantom type parameter.
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static \<T\> <className>\<T\> clampMaximum(
    final <classReadableName3>\<T\> v,
    final <scalarType> maximum)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum);
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum);
    final <scalarType> z = <opClass>.minimum(v.getZ<scalarTypeSuffix>(), maximum);
    return new <className>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param \<T\> A phantom type parameter.
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static \<T\> <className>\<T\> clampMaximumByPVector(
    final <classReadableName3>\<T\> v,
    final <classReadableName3>\<T\> maximum)
  {
    final <scalarType> x = <opClass>.minimum(v.getX<scalarTypeSuffix>(), maximum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.minimum(v.getY<scalarTypeSuffix>(), maximum.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.minimum(v.getZ<scalarTypeSuffix>(), maximum.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param \<T\> A phantom type parameter.
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static \<T\> <className>\<T\> clampMinimum(
    final <classReadableName3>\<T\> v,
    final <scalarType> minimum)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum);
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum);
    final <scalarType> z = <opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum);
    return new <className>(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param \<T\> A phantom type parameter.
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   */

  public static \<T\> <className>\<T\> clampMinimumByPVector(
    final <classReadableName3>\<T\> v,
    final <classReadableName3>\<T\> minimum)
  {
    final <scalarType> x = <opClass>.maximum(v.getX<scalarTypeSuffix>(), minimum.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.maximum(v.getY<scalarTypeSuffix>(), minimum.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.maximum(v.getZ<scalarTypeSuffix>(), minimum.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
  }

  /**
   * Calculate the cross product of the vectors {@code v0} and {@code v1}. The
   * result is a vector perpendicular to both vectors.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param \<T\> A phantom type parameter.
   *
   * @return A vector perpendicular to both {@code v0} and {@code v1}
   */

  public static \<T\> <className>\<T\> crossProduct(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
  {
    final <scalarType> x =
      <opClass>.subtract(<opClass>.multiply(v0.getY<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>()), <opClass>.multiply(v0.getZ<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>()));
    final <scalarType> y =
      <opClass>.subtract(<opClass>.multiply(v0.getZ<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>()), <opClass>.multiply(v0.getX<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>()));
    final <scalarType> z =
      <opClass>.subtract(<opClass>.multiply(v0.getX<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>()), <opClass>.multiply(v0.getY<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>()));
    return new <className>\<T\>(x, y, z);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter.
   * @return The distance between the two vectors.
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarType> distance(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return magnitude(subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter.
   * @return The scalar product of the two vectors
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarType> dotProduct(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiply(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> my = <opClass>.multiply(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarType> mz = <opClass>.multiply(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());

    <scalarType> k;
    k = <opClass>.add(mx, my);
    k = <opClass>.add(k, mz);
    return k;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * \<ul>
   *   \<li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}\</li>
   *   \<li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}\</li>
   * \</ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   * @param \<T\> A phantom type parameter.
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> interpolateLinear(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1,
    final <scalarScaleType> alpha)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <className> w0 = scale(v0, <opClass>.subtractScale(<opClass>.one(), alpha));
    final <className> w1 = scale(v1, alpha);
    return add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   * @param \<T\> A phantom type parameter.
   * @return The magnitude of the input vector
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarType> magnitude(
    final <classReadableName3>\<T\> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return (<scalarType>) <opClass>.squareRoot(magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   * @param \<T\> A phantom type parameter.
   * @return The squared magnitude of the input vector
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <scalarType> magnitudeSquared(
    final <classReadableName3>\<T\> v)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    return dotProduct(v, v);
  }

  <if(hasFloatingPoint)>
  /**
   * Normalize the vector {@code v}, preserving its direction but reducing it to
   * unit length.
   *
   * @param v   The input vector
   * @param \<T\> A phantom type parameter.
   *
   * @return A vector with the same orientation as {@code v} but with magnitude equal to {@code 1.0}
   */

  public static \<T\> <className>\<T\> normalize(
    final <classReadableName3>\<T\> v)
  {
    final <scalarType> m = magnitudeSquared(v);
    if (m > 0.0F) {
      final <scalarType> reciprocal = <opClass>.divide(<opClass>.one(), (<scalarType>) <opClass>.squareRoot(m));
      return scale(v, reciprocal);
    }
    return new <className>\<T\>(v);
  }

  /**
   * \<p\>Orthonormalize and return the vectors {@code v0} and {@code v1}.\</p\>
   * \<p\>See \<a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP\</a\>\</p\>
   *
   * @param v0  The left vector
   * @param v1  The right vector
   * @param \<T\> A phantom type parameter.
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   *
   * @since 7.0.0
   */

  public static \<T\> Pair\<<className>\<T>, <className>\<T>\> orthoNormalize(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
  {
    final <className>\<T> v0n = normalize(v0);
    final <className>\<T> projection = scale(v0n, dotProduct(v1, v0n));
    final <className>\<T> vr = normalize(subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }
  <endif>

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code q}.
   *
   * @param p The left vector
   * @param q The right vector
   * @param \<T\> A phantom type parameter.
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> projection(
    final <classReadableName3>\<T\> p,
    final <classReadableName3>\<T\> q)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> dot = dotProduct(p, q);
    final <scalarType> qms = magnitudeSquared(q);
    final <scalarType> s = <opClass>.divide(dot, qms);
    return scale(p, (<scalarScaleType>) s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v The input vector
   * @param r The scaling value
   * @param \<T\> A phantom type parameter.
   * @return {@code (v.x * r, v.y * r, v.z * r, vw * r)}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> scale(
    final <classReadableName3>\<T\> v,
    final <scalarScaleType> r)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> mx = <opClass>.multiplyReal(v.getX<scalarTypeSuffix>(), r);
    final <scalarType> my = <opClass>.multiplyReal(v.getY<scalarTypeSuffix>(), r);
    final <scalarType> mz = <opClass>.multiplyReal(v.getZ<scalarTypeSuffix>(), r);
    return new <className>(mx, my, mz);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param \<T\> A phantom type parameter.
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   *
   * <if(hasPartialArithmetic)>
   * @throws ArithmeticException Iff an internal arithmetic operation causes an overflow or underflow
   * <endif>
   */

  public static \<T\> <className>\<T\> subtract(
    final <classReadableName3>\<T\> v0,
    final <classReadableName3>\<T\> v1)
    <if(hasPartialArithmetic)>throws ArithmeticException<endif>
  {
    final <scalarType> x = <opClass>.subtract(v0.getX<scalarTypeSuffix>(), v1.getX<scalarTypeSuffix>());
    final <scalarType> y = <opClass>.subtract(v0.getY<scalarTypeSuffix>(), v1.getY<scalarTypeSuffix>());
    final <scalarType> z = <opClass>.subtract(v0.getZ<scalarTypeSuffix>(), v1.getZ<scalarTypeSuffix>());
    return new <className>(x, y, z);
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
    final <className>\<?\> other = (<className>\<?\>) obj;
    return <opClass>.scalarEquals(this.x, other.x)
        && <opClass>.scalarEquals(this.y, other.y)
        && <opClass>.scalarEquals(this.z, other.z);
  }

  @Override
  public <scalarType> getX<scalarTypeSuffix>()
  {
    return this.x;
  }

  @Override
  public <scalarType> getY<scalarTypeSuffix>()
  {
    return this.y;
  }

  @Override
  public <scalarType> getZ<scalarTypeSuffix>()
  {
    return this.z;
  }

  @Override
  public int hashCode()
  {
    return <opClass>.hash3(this.x, this.y, this.z);
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
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}

>>