PVector3(
  className,
  vectorType,
  vectorConversionType,
  vectorComponentType,
  scalarLargeType,
  scalarRealType,
  opClass,
  dotProductClass,
  hasFloatingPoint,
  hasGeneric) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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

<if(hasGeneric)>
package com.io7m.jtensors.core.parameterized.vectors;
<else>
package com.io7m.jtensors.core.unparameterized.vectors;
<endif>

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <vectorType>} values.\</p>
 * \<p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * \</p>
 * @since 8.0.0
 */

public final class <className>
{
  private static final <vectorType><if(hasGeneric)>\<?><endif> ZERO;

  static {
    final <vectorComponentType> z = (<vectorComponentType>) <opClass>.zero();
    ZERO = <vectorType>.of(z, z, z);
  }

  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Calculate the absolute of {@code v0}.
   *
   * @return {@code (abs v0.x, abs v0.y, abs v0.z) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The vector
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  absolute(
    final <vectorType>\<T> v0)
  <else>
  <vectorType>
  absolute(
    final <vectorType> v0)
  <endif>
  {
    return <vectorType>.of(
      (<vectorComponentType>) <opClass>.absolute(v0.x()),
      (<vectorComponentType>) <opClass>.absolute(v0.y()),
      (<vectorComponentType>) <opClass>.absolute(v0.z()));
  }

  /**
   * Add {@code v0} to {@code v1}.
   *
   * @return {@code (v0.x + v1.x, v0.y + v0.y, v0.z + v1.z) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T> add(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
  <else>
  <vectorType> add(
    final <vectorType> v0,
    final <vectorType> v1)
  <endif>
  {
    return <vectorType>.of(
      (<vectorComponentType>) <opClass>.add(v0.x(), v1.x()),
      (<vectorComponentType>) <opClass>.add(v0.y(), v1.y()),
      (<vectorComponentType>) <opClass>.add(v0.z(), v1.z())
    );
  }

  /**
   * Add {@code v0} to {@code v1 * r}.
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v0.y * r), v0.z + (v1.z * r)) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   * @param r The scaling value
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  addScaled(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1,
    final <scalarRealType> r)
  <else>
  <vectorType>
  addScaled(
    final <vectorType> v0,
    final <vectorType> v1,
    final <scalarRealType> r)
  <endif>
  {
    return add(v0, scale(v1, r));
  }

  /**
   * Clamp the values in {@code v} by {@code v_min} and {@code v_max}.
   *
   * @return {@code (max(min(v.x, v_max.x()), v_min.x()),
   *                 max(min(v.y, v_max.y()), v_min.y()),
   *                 max(min(v.z, v_max.z()), v_min.z())) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v The source vector
   * @param v_min The minimum vector
   * @param v_max The maximum vector
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  clamp(
    final <vectorType>\<T> v,
    final <vectorType>\<T> v_min,
    final <vectorType>\<T> v_max)
  <else>
  <vectorType>
  clamp(
    final <vectorType> v,
    final <vectorType> v_min,
    final <vectorType> v_max)
  <endif>
  {
    final <scalarRealType> x = <opClass>.clamp(v.x(), v_min.x(), v_max.x());
    final <scalarRealType> y = <opClass>.clamp(v.y(), v_min.y(), v_max.y());
    final <scalarRealType> z = <opClass>.clamp(v.z(), v_min.z(), v_max.z());

    return <vectorType>.of(
      (<vectorComponentType>) x,
      (<vectorComponentType>) y,
      (<vectorComponentType>) z);
  }

  /**
   * Calculate the cross product of the vectors {@code v0} and {@code v1}.
   *
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   * @return The cross product of the two vectors
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  crossProduct(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
  <else>
  <vectorType>
  crossProduct(
    final <vectorType> v0,
    final <vectorType> v1)
  <endif>
  {
    final <scalarRealType> x =
      <opClass>.subtractReal(
        <opClass>.multiplyReal(v0.y(), v1.z()),
        <opClass>.multiplyReal(v0.z(), v1.y()));
    final <scalarRealType> y =
      <opClass>.subtractReal(
        <opClass>.multiplyReal(v0.z(), v1.x()),
        <opClass>.multiplyReal(v0.x(), v1.z()));
    final <scalarRealType> z =
      <opClass>.subtractReal(
        <opClass>.multiplyReal(v0.x(), v1.y()),
        <opClass>.multiplyReal(v0.y(), v1.x()));

    return <vectorType>.of(
      (<vectorComponentType>) x,
      (<vectorComponentType>) y,
      (<vectorComponentType>) z);
  }

  /**
   * Calculate the distance between {@code v0} and {@code v1}.
   *
   * @return The distance between {@code v0} and {@code v1}.
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   */

  public static
  <if(hasGeneric)>
  \<T> <scalarLargeType> distance(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
  <else>
  <scalarLargeType> distance(
    final <vectorType> v0,
    final <vectorType> v1)
  <endif>
  {
    return (<scalarLargeType>) magnitude(subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   * @return The scalar product of the two vectors
   */

  public static
  <if(hasGeneric)>
  \<T> <scalarLargeType>
  dotProduct(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
  <else>
  <scalarLargeType>
  dotProduct(
    final <vectorType> v0,
    final <vectorType> v1)
  <endif>
  {
    return <dotProductClass>.dotProduct3(
      v0.x(), v0.y(), v0.z(),
      v1.x(), v1.y(), v1.z());
  }

  /**
   * \<p>Linearly interpolate between {@code v0} and {@code v1} by the amount {@code alpha}.\</p>
   *
   * \<p>The {@code alpha} parameter controls the degree of interpolation, such that:\</p>
   *
   * \<ul>
   *   \<li>{@code interpolateLinear(v0, v1, 0.0) = v0}\</li>
   *   \<li>{@code interpolateLinear(v0, v1, 1.0) = v1}\</li>
   * \</ul>
   *
   * @param v0    The left input vector
   * @param v1    The right input vector
   * @param alpha The interpolation value in the range {@code [0, 1]}
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @return {@code ((1 - alpha) * v0) + (alpha * v1)}
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  interpolateLinear(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1,
    final <scalarRealType> alpha)
  <else>
  <vectorType>
  interpolateLinear(
    final <vectorType> v0,
    final <vectorType> v1,
    final <scalarRealType> alpha)
  <endif>
  {
    final <vectorType><if(hasGeneric)>\<T><endif> v0_r =
      scale(v0, <opClass>.subtractReal(<opClass>.one(), alpha));
    final <vectorType><if(hasGeneric)>\<T><endif> v1_r =
      scale(v1, alpha);
    return add(v0_r, v1_r);
  }

  /**
   * Calculate the squared magnitude of the vector {@code v0}.
   *
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The vector
   * @return The squared magnitude of the vector
   */

  public static
  <if(hasGeneric)>
  \<T> <scalarLargeType>
  magnitudeSquared(
    final <vectorType>\<T> v0)
  <else>
  <scalarLargeType>
  magnitudeSquared(
    final <vectorType> v0)
  <endif>
  {
    return dotProduct(v0, v0);
  }

  /**
   * Calculate the magnitude of the vector {@code v0}.
   *
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The vector
   * @return The magnitude of the vector
   */

  public static
  <if(hasGeneric)>
  \<T> <scalarRealType>
  magnitude(
    final <vectorType>\<T> v0)
  <else>
  <scalarRealType>
  magnitude(
    final <vectorType> v0)
  <endif>
  {
    return <opClass>.squareRootLarge(magnitudeSquared(v0));
  }

  /**
   * Calculate the negation of {@code v}.
   *
   * @return {@code (-v.x, -v.y, -v.z) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v The vector
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T> negate(
    final <vectorType>\<T> v)
  <else>
  <vectorType> negate(
    final <vectorType> v)
  <endif>
  {
    return <vectorType>.of(
      (<vectorComponentType>) <opClass>.negate(v.x()),
      (<vectorComponentType>) <opClass>.negate(v.y()),
      (<vectorComponentType>) <opClass>.negate(v.z()));
  }

  <if(hasFloatingPoint)>
  /**
   * \<p>Normalize the vector {@code v0}.\</p>
   * \<p>If the magnitude of the vector is zero, the function returns {@code v0}.\</p>
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The vector
   * @return A normalized copy of {@code v0}
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  normalize(
    final <vectorType>\<T> v0)
  <else>
  <vectorType>
  normalize(
    final <vectorType> v0)
  <endif>
  {
    final <scalarLargeType> m = magnitudeSquared(v0);
    if (<opClass>.compareLarge(m, <opClass>.zero()) > 0) {
      final <scalarRealType> reciprocal =
        <opClass>.divideReal(<opClass>.one(), <opClass>.squareRootLarge(m));
      return scale(v0, reciprocal);
    }

    return v0;
  }
  <endif>

  /**
   * Scale {@code v0} by {@code r}.
   *
   * @return {@code (v0.x * r, v0.y * r, v0.z * r) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param r The scaling value
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  scale(
    final <vectorType>\<T> v0,
    final <scalarRealType> r)
  <else>
  <vectorType>
  scale(
    final <vectorType> v0,
    final <scalarRealType> r)
  <endif>
  {
    return <vectorType>.of(
      (<vectorComponentType>) <opClass>.multiplyReal(v0.x(), r),
      (<vectorComponentType>) <opClass>.multiplyReal(v0.y(), r),
      (<vectorComponentType>) <opClass>.multiplyReal(v0.z(), r));
  }

  /**
   * Subtract {@code v1} from {@code v0}.
   *
   * @return {@code (v0.x - v1.x, v0.y - v0.y, v0.z - v1.z) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   * @param v0 The left vector
   * @param v1 The right vector
   */

  public static
  <if(hasGeneric)>
  \<T> <vectorType>\<T>
  subtract(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
  <else>
  <vectorType>
  subtract(
    final <vectorType> v0,
    final <vectorType> v1)
  <endif>
  {
    return <vectorType>.of(
      (<vectorComponentType>) <opClass>.subtract(v0.x(), v1.x()),
      (<vectorComponentType>) <opClass>.subtract(v0.y(), v1.y()),
      (<vectorComponentType>) <opClass>.subtract(v0.z(), v1.z()));
  }

  /**
   * The zero vector.
   *
   * @return {@code (0, 0, 0) }
   * <if(hasGeneric)>
   * @param \<T> A phantom type parameter
   * <endif>
   */

  <if(hasGeneric)>
  @SuppressWarnings("unchecked")
  public static \<T> <vectorType>\<T> zero()
  {
    return (<vectorType>\<T>) ZERO;
  }
  <else>
  public static <vectorType> zero()
  {
    return (<vectorType>) ZERO;
  }
  <endif>

  <if(hasGeneric)>
  /**
   * @param v The input vector
   * @param \<A> A phantom type parameter (possibly representing a coordinate system)
   * @return A vector equal to {@code m} but without type parameters
   */

  public static \<A> <vectorConversionType> toUnparameterized(
    final <vectorType>\<A> v)
  {
    return <vectorConversionType>.of(v.x(), v.y(), v.z());
  }

  /**
   * @param v The input vector
   * @param \<A> A phantom type parameter (possibly representing a coordinate system)
   * @return A vector equal to {@code v} with type parameters
   */

  public static \<A> <vectorType>\<A> toParameterized(
    final <vectorConversionType> v)
  {
    return <vectorType>.of(v.x(), v.y(), v.z());
  }
  <endif>
}
>>
