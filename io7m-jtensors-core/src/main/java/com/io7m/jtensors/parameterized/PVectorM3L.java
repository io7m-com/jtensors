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

package com.io7m.jtensors.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2L;
import com.io7m.jtensors.VectorM3L;
import com.io7m.jtensors.VectorReadable2LType;
import com.io7m.jtensors.VectorReadable3LType;

/**
 * <p>
 * A three-dimensional mutable vector type with integer elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 *
 * @since 7.0.0
 * @param <T>
 *          A phantom type parameter.
 */

public final class PVectorM3L<T> implements
  PVectorReadable3LType<T>,
  PVectorWritable3LType<T>
{
  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> absolute(
    final PVectorReadable3LType<T> v,
    final PVectorM3L<T> out)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    final long z = CheckedMath.absolute(v.getZL());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> absoluteInPlace(
    final PVectorM3L<T> v)
    throws ArithmeticException
  {
    return PVectorM3L.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> add(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1,
    final PVectorM3L<T> out)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    final long z = CheckedMath.add(v0.getZL(), v1.getZL());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> addInPlace(
    final PVectorM3L<T> v0,
    final PVectorReadable3LType<T> v1)
    throws ArithmeticException
  {
    return PVectorM3L.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code out}.
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> addScaled(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1,
    final double r,
    final PVectorM3L<T> out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long mz = CheckedMath.multiply(v1.getZL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    final long z = CheckedMath.add(v0.getZL(), mz);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> addScaledInPlace(
    final PVectorM3L<T> v0,
    final PVectorReadable3LType<T> v1,
    final double r)
    throws ArithmeticException
  {
    return PVectorM3L.addScaled(v0, v1, r, v0);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code out}.
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
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clamp(
    final PVectorReadable3LType<T> v,
    final long minimum,
    final long maximum,
    final PVectorM3L<T> out)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    final long z = Math.min(Math.max(v.getZL(), minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code out}.
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampByPVector(
    final PVectorReadable3LType<T> v,
    final PVectorReadable3LType<T> minimum,
    final PVectorReadable3LType<T> maximum,
    final PVectorM3L<T> out)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    final long z =
      Math.min(Math.max(v.getZL(), minimum.getZL()), maximum.getZL());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampByPVectorInPlace(
    final PVectorM3L<T> v,
    final PVectorReadable3LType<T> minimum,
    final PVectorReadable3LType<T> maximum)
  {
    return PVectorM3L.clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}, in {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampInPlace(
    final PVectorM3L<T> v,
    final long minimum,
    final long maximum)
  {
    return PVectorM3L.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMaximum(
    final PVectorReadable3LType<T> v,
    final long maximum,
    final PVectorM3L<T> out)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    final long z = Math.min(v.getZL(), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMaximumByPVector(
    final PVectorReadable3LType<T> v,
    final PVectorReadable3LType<T> maximum,
    final PVectorM3L<T> out)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    final long z = Math.min(v.getZL(), maximum.getZL());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMaximumByPVectorInPlace(
    final PVectorM3L<T> v,
    final PVectorReadable3LType<T> maximum)
  {
    return PVectorM3L.clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         , in {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMaximumInPlace(
    final PVectorM3L<T> v,
    final long maximum)
  {
    return PVectorM3L.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMinimum(
    final PVectorReadable3LType<T> v,
    final long minimum,
    final PVectorM3L<T> out)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    final long z = Math.max(v.getZL(), minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMinimumByPVector(
    final PVectorReadable3LType<T> v,
    final PVectorReadable3LType<T> minimum,
    final PVectorM3L<T> out)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    final long z = Math.max(v.getZL(), minimum.getZL());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   *         , in {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMinimumByPVectorInPlace(
    final PVectorM3L<T> v,
    final PVectorReadable3LType<T> minimum)
  {
    return PVectorM3L.clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}, in {@code v}.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> clampMinimumInPlace(
    final PVectorM3L<T> v,
    final long minimum)
  {
    return PVectorM3L.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param <U>
   *          The specific vector type
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * @return output
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T, U extends PVectorWritable3LType<T>> U copy(
    final PVectorReadable3LType<T> input,
    final U output)
  {
    output.set3L(input.getXL(), input.getYL(), input.getZL());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and
   * {@code v1}.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> long distance(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1)
    throws ArithmeticException
  {
    final PVectorM3L<T> vr = new PVectorM3L<T>();
    return PVectorM3L.magnitude(PVectorM3L.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and
   * {@code v1}.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> long dotProduct(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.multiply(v0.getZL(), v1.getZL());
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -&gt; r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -&gt; r = v1}</li>
   * </ul>
   *
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between {@code 0.0} and
   *          {@code 1.0}.
   * @param r
   *          The result vector.
   *
   * @return {@code r}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> interpolateLinear(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1,
    final double alpha,
    final PVectorM3L<T> r)
    throws ArithmeticException
  {
    final PVectorM3L<T> w0 = new PVectorM3L<T>();
    final PVectorM3L<T> w1 = new PVectorM3L<T>();

    PVectorM3L.scale(v0, 1.0 - alpha, w0);
    PVectorM3L.scale(v1, alpha, w1);

    return PVectorM3L.add(w0, w1, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v
   *          The input vector
   *
   * @return The magnitude of the input vector
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> long magnitude(
    final PVectorReadable3LType<T> v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt(PVectorM3L.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> long magnitudeSquared(
    final PVectorReadable3LType<T> v)
    throws ArithmeticException
  {
    return PVectorM3L.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}, saving the result in {@code r}.
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> projection(
    final PVectorReadable3LType<T> p,
    final PVectorReadable3LType<T> q,
    final PVectorM3L<T> r)
    throws ArithmeticException
  {
    final long dot = PVectorM3L.dotProduct(p, q);
    final long qms = PVectorM3L.magnitudeSquared(q);
    final long s = dot / qms;

    return PVectorM3L.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> scale(
    final PVectorReadable3LType<T> v,
    final double r,
    final PVectorM3L<T> out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    final long mz = CheckedMath.multiply(v.getZL(), r);
    out.x = mx;
    out.y = my;
    out.z = mz;
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> scaleInPlace(
    final PVectorM3L<T> v,
    final long r)
    throws ArithmeticException
  {
    return PVectorM3L.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> subtract(
    final PVectorReadable3LType<T> v0,
    final PVectorReadable3LType<T> v1,
    final PVectorM3L<T> out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long my = CheckedMath.subtract(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.subtract(v0.getZL(), v1.getZL());
    out.x = mx;
    out.y = my;
    out.z = mz;
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM3L<T> subtractInPlace(
    final PVectorM3L<T> v0,
    final PVectorReadable3LType<T> v1)
    throws ArithmeticException
  {
    return PVectorM3L.subtract(v0, v1, v0);
  }

  private long x;
  private long y;
  private long z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0]}.
   */

  public PVectorM3L()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   * @param in_z
   *          The {@code z} value
   */

  public PVectorM3L(
    final long in_x,
    final long in_y,
    final long in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The source vector
   */

  public PVectorM3L(
    final PVectorReadable3LType<T> in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
    this.z = in_v.getZL();
  }

  @Override public void copyFrom2L(
    final VectorReadable2LType in_v)
  {
    VectorM2L.copy(in_v, this);
  }

  @Override public void copyFrom3L(
    final VectorReadable3LType in_v)
  {
    VectorM3L.copy(in_v, this);
  }

  @Override public void copyFromTyped2L(
    final PVectorReadable2LType<T> in_v)
  {
    PVectorM2L.copy(in_v, this);
  }

  @Override public void copyFromTyped3L(
    final PVectorReadable3LType<T> in_v)
  {
    VectorM3L.copy(in_v, this);
  }

  @Override public boolean equals(
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
    final PVectorM3L<?> other = (PVectorM3L<?>) obj;
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

  @Override public long getXL()
  {
    return this.x;
  }

  @Override public long getYL()
  {
    return this.y;
  }

  @Override public long getZL()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final long prime = 31;
    long result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return (int) result;
  }

  @Override public void set2L(
    final long in_x,
    final long in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3L(
    final long in_x,
    final long in_y,
    final long in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void setXL(
    final long in_x)
  {
    this.x = in_x;
  }

  @Override public void setYL(
    final long in_y)
  {
    this.y = in_y;
  }

  @Override public void setZL(
    final long in_z)
  {
    this.z = in_z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorM3L ");
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
