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
 * A three-dimensional mutable vector type with integer elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 *
 * @since 5.3.0
 */

public final class VectorM3L implements VectorReadable3LType, VectorWritable3LType
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
   */

  public static VectorM3L absolute(
    final VectorReadable3LType v,
    final VectorM3L out)
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
   */

  public static VectorM3L absoluteInPlace(
    final VectorM3L v)
    throws ArithmeticException
  {
    return VectorM3L.absolute(v, v);
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
   */

  public static VectorM3L add(
    final VectorReadable3LType v0,
    final VectorReadable3LType v1,
    final VectorM3L out)
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
   */

  public static VectorM3L addInPlace(
    final VectorM3L v0,
    final VectorReadable3LType v1)
    throws ArithmeticException
  {
    return VectorM3L.add(v0, v1, v0);
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
   */

  public static VectorM3L addScaled(
    final VectorReadable3LType v0,
    final VectorReadable3LType v1,
    final double r,
    final VectorM3L out)
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
   */

  public static VectorM3L addScaledInPlace(
    final VectorM3L v0,
    final VectorReadable3LType v1,
    final double r)
    throws ArithmeticException
  {
    return VectorM3L.addScaled(v0, v1, r, v0);
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

  public static VectorM3L clamp(
    final VectorReadable3LType v,
    final long minimum,
    final long maximum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampByVector(
    final VectorReadable3LType v,
    final VectorReadable3LType minimum,
    final VectorReadable3LType maximum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampByVectorInPlace(
    final VectorM3L v,
    final VectorReadable3LType minimum,
    final VectorReadable3LType maximum)
  {
    return VectorM3L.clampByVector(v, minimum, maximum, v);
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

  public static VectorM3L clampInPlace(
    final VectorM3L v,
    final long minimum,
    final long maximum)
  {
    return VectorM3L.clamp(v, minimum, maximum, v);
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

  public static VectorM3L clampMaximum(
    final VectorReadable3LType v,
    final long maximum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampMaximumByVector(
    final VectorReadable3LType v,
    final VectorReadable3LType maximum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampMaximumByVectorInPlace(
    final VectorM3L v,
    final VectorReadable3LType maximum)
  {
    return VectorM3L.clampMaximumByVector(v, maximum, v);
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

  public static VectorM3L clampMaximumInPlace(
    final VectorM3L v,
    final long maximum)
  {
    return VectorM3L.clampMaximum(v, maximum, v);
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

  public static VectorM3L clampMinimum(
    final VectorReadable3LType v,
    final long minimum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampMinimumByVector(
    final VectorReadable3LType v,
    final VectorReadable3LType minimum,
    final VectorM3L out)
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
   */

  public static VectorM3L clampMinimumByVectorInPlace(
    final VectorM3L v,
    final VectorReadable3LType minimum)
  {
    return VectorM3L.clampMinimumByVector(v, minimum, v);
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

  public static VectorM3L clampMinimumInPlace(
    final VectorM3L v,
    final long minimum)
  {
    return VectorM3L.clampMinimum(v, minimum, v);
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

  public static <T extends VectorWritable3LType> T copy(
    final VectorReadable3LType input,
    final T output)
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
   */

  public static long distance(
    final VectorReadable3LType v0,
    final VectorReadable3LType v1)
    throws ArithmeticException
  {
    final VectorM3L vr = new VectorM3L();
    return VectorM3L.magnitude(VectorM3L.subtract(v0, v1, vr));
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
    final VectorReadable3LType v0,
    final VectorReadable3LType v1)
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
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM3L interpolateLinear(
    final VectorReadable3LType v0,
    final VectorReadable3LType v1,
    final double alpha,
    final VectorM3L r)
    throws ArithmeticException
  {
    final VectorM3L w0 = new VectorM3L();
    final VectorM3L w1 = new VectorM3L();

    VectorM3L.scale(v0, 1.0 - alpha, w0);
    VectorM3L.scale(v1, alpha, w1);

    return VectorM3L.add(w0, w1, r);
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
    final VectorReadable3LType v)
    throws ArithmeticException
  {
    return Cast.castToLong(Math.sqrt((double) VectorM3L.magnitudeSquared(v)));
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
    final VectorReadable3LType v)
    throws ArithmeticException
  {
    return VectorM3L.dotProduct(v, v);
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

  public static VectorM3L projection(
    final VectorReadable3LType p,
    final VectorReadable3LType q,
    final VectorM3L r)
    throws ArithmeticException
  {
    final long dot = VectorM3L.dotProduct(p, q);
    final long qms = VectorM3L.magnitudeSquared(q);
    final long s = dot / qms;

    return VectorM3L.scale(p, (double) s, r);
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
   */

  public static VectorM3L scale(
    final VectorReadable3LType v,
    final double r,
    final VectorM3L out)
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
   */

  public static VectorM3L scaleInPlace(
    final VectorM3L v,
    final long r)
    throws ArithmeticException
  {
    return VectorM3L.scale(v, (double) r, v);
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
   */

  public static VectorM3L subtract(
    final VectorReadable3LType v0,
    final VectorReadable3LType v1,
    final VectorM3L out)
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
   */

  public static VectorM3L subtractInPlace(
    final VectorM3L v0,
    final VectorReadable3LType v1)
    throws ArithmeticException
  {
    return VectorM3L.subtract(v0, v1, v0);
  }

  private long x;
  private long y;
  private long z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0]}.
   */

  public VectorM3L()
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

  public VectorM3L(
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

  public VectorM3L(
    final VectorReadable3LType in_v)
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
    final VectorM3L other = (VectorM3L) obj;
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
    final long prime = 31L;
    long result = 1L;
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
    builder.append("[VectorM3L ");
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
