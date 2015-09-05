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
 * A three-dimensional immutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 */

public final class VectorI3I implements VectorReadable3IType
{
  /**
   * The zero vector.
   */

  public static final VectorI3I ZERO = new VectorI3I(0, 0, 0);

  /**
   * Calculate the absolute values of the elements in vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorI3I absolute(
    final VectorReadable3IType v)
    throws ArithmeticException
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    final int z = CheckedMath.absolute(v.getZI());
    return new VectorI3I(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}.
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

  public static VectorI3I add(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
    final int z = CheckedMath.add(v0.getZI(), v1.getZI());
    return new VectorI3I(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
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

  public static VectorI3I addScaled(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int mz = CheckedMath.multiply(v1.getZI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    final int z = CheckedMath.add(v0.getZI(), mz);
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}
   */

  public static VectorI3I clamp(
    final VectorReadable3IType v,
    final int minimum,
    final int maximum)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
    final int z = Math.min(Math.max(v.getZI(), minimum), maximum);
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 5.0.0
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static VectorI3I clampByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType minimum,
    final VectorReadable3IType maximum)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
    final int z =
      Math.min(Math.max(v.getZI(), minimum.getZI()), maximum.getZI());
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static VectorI3I clampMaximum(
    final VectorReadable3IType v,
    final int maximum)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
    final int z = Math.min(v.getZI(), maximum);
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 5.0.0
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))}
   */

  public static VectorI3I clampMaximumByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType maximum)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
    final int z = Math.min(v.getZI(), maximum.getZI());
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @since 5.0.0
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   */

  public static VectorI3I clampMinimum(
    final VectorReadable3IType v,
    final int minimum)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
    final int z = Math.max(v.getZI(), minimum);
    return new VectorI3I(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @since 5.0.0
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   */

  public static VectorI3I clampMinimumByVector(
    final VectorReadable3IType v,
    final VectorReadable3IType minimum)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
    final int z = Math.max(v.getZI(), minimum.getZI());
    return new VectorI3I(x, y, z);
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
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int distance(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    return VectorI3I.magnitude(VectorI3I.subtract(v0, v1));
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
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int dotProduct(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    final int mz = CheckedMath.multiply(v0.getZI(), v1.getZI());
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}.
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
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   */

  public static VectorI3I interpolateLinear(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1,
    final double alpha)
    throws ArithmeticException
  {
    final VectorI3I w0 = VectorI3I.scale(v0, 1.0 - alpha);
    final VectorI3I w1 = VectorI3I.scale(v1, alpha);
    return VectorI3I.add(w0, w1);
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
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitude(
    final VectorReadable3IType v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt((double) VectorI3I.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitudeSquared(
    final VectorReadable3IType v)
    throws ArithmeticException
  {
    return VectorI3I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @since 5.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param q
   *          The right vector
   * @param p
   *          The left vector
   */

  public static VectorI3I projection(
    final VectorReadable3IType p,
    final VectorReadable3IType q)
    throws ArithmeticException
  {
    final int dot = VectorI3I.dotProduct(p, q);
    final int qms = VectorI3I.magnitudeSquared(q);
    final int s = dot / qms;
    return VectorI3I.scale(p, (double) s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code out}.
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

  public static VectorI3I scale(
    final VectorReadable3IType v,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    final int mz = CheckedMath.multiply(v.getZI(), r);
    return new VectorI3I(mx, my, mz);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
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

  public static VectorI3I subtract(
    final VectorReadable3IType v0,
    final VectorReadable3IType v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int y = CheckedMath.subtract(v0.getYI(), v1.getYI());
    final int z = CheckedMath.subtract(v0.getZI(), v1.getZI());
    return new VectorI3I(x, y, z);
  }

  private final int x;
  private final int y;
  private final int z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0]}.
   */

  public VectorI3I()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
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

  public VectorI3I(
    final int in_x,
    final int in_y,
    final int in_z)
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

  public VectorI3I(
    final VectorReadable3IType in_v)
  {
    this.x = in_v.getXI();
    this.y = in_v.getYI();
    this.z = in_v.getZI();
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
    final VectorI3I other = (VectorI3I) obj;
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

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int getZI()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3I ");
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
