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
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A four-dimensional mutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 *
 * @since 5.3.0
 */

public final class VectorM4L implements VectorReadable4LType, VectorWritable4LType
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
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L absolute(
    final VectorReadable4LType v,
    final VectorM4L out)
    throws ArithmeticException
  {
    final long x = CheckedMath.absolute(v.getXL());
    final long y = CheckedMath.absolute(v.getYL());
    final long z = CheckedMath.absolute(v.getZL());
    final long w = CheckedMath.absolute(v.getWL());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L absoluteInPlace(
    final VectorM4L v)
    throws ArithmeticException
  {
    return VectorM4L.absolute(v, v);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L add(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final VectorM4L out)
    throws ArithmeticException
  {
    final long x = CheckedMath.add(v0.getXL(), v1.getXL());
    final long y = CheckedMath.add(v0.getYL(), v1.getYL());
    final long z = CheckedMath.add(v0.getZL(), v1.getZL());
    final long w = CheckedMath.add(v0.getWL(), v1.getWL());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L addInPlace(
    final VectorM4L v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    return VectorM4L.add(v0, v1, v0);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L addScaled(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final double r,
    final VectorM4L out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v1.getXL(), r);
    final long my = CheckedMath.multiply(v1.getYL(), r);
    final long mz = CheckedMath.multiply(v1.getZL(), r);
    final long mw = CheckedMath.multiply(v1.getWL(), r);
    final long x = CheckedMath.add(v0.getXL(), mx);
    final long y = CheckedMath.add(v0.getYL(), my);
    final long z = CheckedMath.add(v0.getZL(), mz);
    final long w = CheckedMath.add(v0.getWL(), mw);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L addScaledInPlace(
    final VectorM4L v0,
    final VectorReadable4LType v1,
    final double r)
    throws ArithmeticException
  {
    return VectorM4L.addScaled(v0, v1, r, v0);
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
   */

  public static VectorM4L clamp(
    final VectorReadable4LType v,
    final long minimum,
    final long maximum,
    final VectorM4L out)
  {
    final long x = Math.min(Math.max(v.getXL(), minimum), maximum);
    final long y = Math.min(Math.max(v.getYL(), minimum), maximum);
    final long z = Math.min(Math.max(v.getZL(), minimum), maximum);
    final long w = Math.min(Math.max(v.getWL(), minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4L clampByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType minimum,
    final VectorReadable4LType maximum,
    final VectorM4L out)
  {
    final long x =
      Math.min(Math.max(v.getXL(), minimum.getXL()), maximum.getXL());
    final long y =
      Math.min(Math.max(v.getYL(), minimum.getYL()), maximum.getYL());
    final long z =
      Math.min(Math.max(v.getZL(), minimum.getZL()), maximum.getZL());
    final long w =
      Math.min(Math.max(v.getWL(), minimum.getWL()), maximum.getWL());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4L clampByVectorInPlace(
    final VectorM4L v,
    final VectorReadable4LType minimum,
    final VectorReadable4LType maximum)
  {
    return VectorM4L.clampByVector(v, minimum, maximum, v);
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
   */

  public static VectorM4L clampInPlace(
    final VectorM4L v,
    final long minimum,
    final long maximum)
  {
    return VectorM4L.clamp(v, minimum, maximum, v);
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
   */

  public static VectorM4L clampMaximum(
    final VectorReadable4LType v,
    final long maximum,
    final VectorM4L out)
  {
    final long x = Math.min(v.getXL(), maximum);
    final long y = Math.min(v.getYL(), maximum);
    final long z = Math.min(v.getZL(), maximum);
    final long w = Math.min(v.getWL(), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4L clampMaximumByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType maximum,
    final VectorM4L out)
  {
    final long x = Math.min(v.getXL(), maximum.getXL());
    final long y = Math.min(v.getYL(), maximum.getYL());
    final long z = Math.min(v.getZL(), maximum.getZL());
    final long w = Math.min(v.getWL(), maximum.getWL());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4L clampMaximumByVectorInPlace(
    final VectorM4L v,
    final VectorReadable4LType maximum)
  {
    return VectorM4L.clampMaximumByVector(v, maximum, v);
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
   */

  public static VectorM4L clampMaximumInPlace(
    final VectorM4L v,
    final long maximum)
  {
    return VectorM4L.clampMaximum(v, maximum, v);
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
   */

  public static VectorM4L clampMinimum(
    final VectorReadable4LType v,
    final long minimum,
    final VectorM4L out)
  {
    final long x = Math.max(v.getXL(), minimum);
    final long y = Math.max(v.getYL(), minimum);
    final long z = Math.max(v.getZL(), minimum);
    final long w = Math.max(v.getWL(), minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   */

  public static VectorM4L clampMinimumByVector(
    final VectorReadable4LType v,
    final VectorReadable4LType minimum,
    final VectorM4L out)
  {
    final long x = Math.max(v.getXL(), minimum.getXL());
    final long y = Math.max(v.getYL(), minimum.getYL());
    final long z = Math.max(v.getZL(), minimum.getZL());
    final long w = Math.max(v.getWL(), minimum.getWL());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   *         , in {@code v}
   */

  public static VectorM4L clampMinimumByVectorInPlace(
    final VectorM4L v,
    final VectorReadable4LType minimum)
  {
    return VectorM4L.clampMinimumByVector(v, minimum, v);
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
   */

  public static VectorM4L clampMinimumInPlace(
    final VectorM4L v,
    final long minimum)
  {
    return VectorM4L.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param <T>
   *          The specific vector type
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * @return output
   */

  public static <T extends VectorWritable4LType> T copy(
    final VectorReadable4LType input,
    final T output)
  {
    output.set4L(input.getXL(), input.getYL(), input.getZL(), input.getWL());
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
   */

  public static long distance(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    final VectorM4L vr = new VectorM4L();
    return VectorM4L.magnitude(VectorM4L.subtract(v0, v1, vr));
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
   */

  public static long dotProduct(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v0.getXL(), v1.getXL());
    final long my = CheckedMath.multiply(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.multiply(v0.getZL(), v1.getZL());
    final long mw = CheckedMath.multiply(v0.getWL(), v1.getWL());
    return CheckedMath.add(CheckedMath.add(CheckedMath.add(mx, my), mz), mw);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li>
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
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L interpolateLinear(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final double alpha,
    final VectorM4L r)
    throws ArithmeticException
  {
    final VectorM4L w0 = new VectorM4L();
    final VectorM4L w1 = new VectorM4L();

    VectorM4L.scale(v0, 1.0 - alpha, w0);
    VectorM4L.scale(v1, alpha, w1);

    return VectorM4L.add(w0, w1, r);
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
   */

  public static long magnitude(
    final VectorReadable4LType v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt(VectorM4L.magnitudeSquared(v)));
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
   */

  public static long magnitudeSquared(
    final VectorReadable4LType v)
    throws ArithmeticException
  {
    return VectorM4L.dotProduct(v, v);
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
   */

  public static VectorM4L projection(
    final VectorReadable4LType p,
    final VectorReadable4LType q,
    final VectorM4L r)
    throws ArithmeticException
  {
    final long dot = VectorM4L.dotProduct(p, q);
    final long qms = VectorM4L.magnitudeSquared(q);
    final long s = dot / qms;

    return VectorM4L.scale(p, s, r);
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
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L scale(
    final VectorReadable4LType v,
    final double r,
    final VectorM4L out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.multiply(v.getXL(), r);
    final long my = CheckedMath.multiply(v.getYL(), r);
    final long mz = CheckedMath.multiply(v.getZL(), r);
    final long mw = CheckedMath.multiply(v.getWL(), r);
    out.x = mx;
    out.y = my;
    out.z = mz;
    out.w = mw;
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
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L scaleInPlace(
    final VectorM4L v,
    final long r)
    throws ArithmeticException
  {
    return VectorM4L.scale(v, r, v);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L subtract(
    final VectorReadable4LType v0,
    final VectorReadable4LType v1,
    final VectorM4L out)
    throws ArithmeticException
  {
    final long mx = CheckedMath.subtract(v0.getXL(), v1.getXL());
    final long my = CheckedMath.subtract(v0.getYL(), v1.getYL());
    final long mz = CheckedMath.subtract(v0.getZL(), v1.getZL());
    final long mw = CheckedMath.subtract(v0.getWL(), v1.getWL());
    out.x = mx;
    out.y = my;
    out.z = mz;
    out.w = mw;
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM4L subtractInPlace(
    final VectorM4L v0,
    final VectorReadable4LType v1)
    throws ArithmeticException
  {
    return VectorM4L.subtract(v0, v1, v0);
  }

  private long w = 1;
  private long x;
  private long y;
  private long z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0, 1]}.
   */

  public VectorM4L()
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
   * @param in_w
   *          The {@code w} value
   */

  public VectorM4L(
    final long in_x,
    final long in_y,
    final long in_z,
    final long in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The source vector
   */

  public VectorM4L(
    final VectorReadable4LType in_v)
  {
    this.x = in_v.getXL();
    this.y = in_v.getYL();
    this.z = in_v.getZL();
    this.w = in_v.getWL();
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

  @Override public void copyFrom4L(
    final VectorReadable4LType in_v)
  {
    VectorM4L.copy(in_v, this);
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
    final VectorM4L other = (VectorM4L) obj;
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

  @Override public long getWL()
  {
    return this.w;
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
    result = (prime * result) + this.w;
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

  @Override public void set4L(
    final long in_x,
    final long in_y,
    final long in_z,
    final long in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override public void setWL(
    final long in_w)
  {
    this.w = in_w;
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
    builder.append("[VectorM4L ");
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
}
