PVector3(
  className,
  vectorType,
  vectorComponentType,
  scalarLargeType,
  scalarRealType,
  opClass,
  dotProductClass,
  hasFloatingPoint) ::= <<
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

package com.io7m.jtensors.core.parameterized.vectors;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <vectorType>} values.\</p>
 * \<p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * \</p>
 * @param \<T> A phantom type parameter
 * @since 8.0.0
 */

public final class <className>\<T>
{
  private static final <vectorType>\<?> ZERO;

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
   * @param \<T> A phantom type parameter
   * @param v0 The vector
   */

  public static \<T> <vectorType>\<T> absolute(
    final <vectorType>\<T> v0)
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
   * @param \<T> A phantom type parameter
   * @param v0 The left vector
   * @param v1 The right vector
   */

  public static \<T> <vectorType>\<T> add(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
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
   * @param \<T> A phantom type parameter
   * @param v0 The left vector
   * @param v1 The right vector
   * @param r The scaling value
   */

  public static \<T> <vectorType>\<T> addScaled(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1,
    final <scalarRealType> r)
  {
    return add(v0, scale(v1, r));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param \<T> A phantom type parameter
   * @param v0 The left vector
   * @param v1 The right vector
   * @return The scalar product of the two vectors
   */

  public static \<T> <scalarLargeType> dotProduct(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
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
   *
   * @return {@code ((1 - alpha) * v0) + (alpha * v1)}
   */

  public static \<T> <vectorType>\<T> interpolateLinear(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1,
    final <scalarRealType> alpha)
  {
    final <vectorType>\<T> v0_r = scale(v0, <opClass>.subtractReal(<opClass>.one(), alpha));
    final <vectorType>\<T> v1_r = scale(v1, alpha);
    return add(v0_r, v1_r);
  }

  /**
   * Calculate the squared magnitude of the vector {@code v0}.
   *
   * @param \<T> A phantom type parameter
   * @param v0 The vector
   * @return The squared magnitude of the vector
   */

  public static \<T> <scalarLargeType> magnitudeSquared(
    final <vectorType>\<T> v0)
  {
    return dotProduct(v0, v0);
  }

  /**
   * Calculate the magnitude of the vector {@code v0}.
   *
   * @param \<T> A phantom type parameter
   * @param v0 The vector
   * @return The magnitude of the vector
   */

  public static \<T> <scalarRealType> magnitude(
    final <vectorType>\<T> v0)
  {
    return <opClass>.squareRootLarge(magnitudeSquared(v0));
  }

  <if(hasFloatingPoint)>
  /**
   * \<p>Normalize the vector {@code v0}.\</p>
   * \<p>If the magnitude of the vector is zero, the function returns {@code v0}.\</p>
   * @param \<T> A phantom type parameter
   * @param v0 The vector
   * @return A normalized copy of {@code v0}
   */

  public static \<T> <vectorType>\<T> normalize(
    final <vectorType>\<T> v0)
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
   * @param \<T> A phantom type parameter
   * @param v0 The left vector
   * @param r The scaling value
   */

  public static \<T> <vectorType>\<T> scale(
    final <vectorType>\<T> v0,
    final <scalarRealType> r)
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
   * @param \<T> A phantom type parameter
   * @param v0 The left vector
   * @param v1 The right vector
   */

  public static \<T> <vectorType>\<T> subtract(
    final <vectorType>\<T> v0,
    final <vectorType>\<T> v1)
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
   * @param \<T> A phantom type parameter
   */

  @SuppressWarnings("unchecked")
  public static \<T> <vectorType>\<T> zero()
  {
    return (<vectorType>\<T>) ZERO;
  }
}
>>
