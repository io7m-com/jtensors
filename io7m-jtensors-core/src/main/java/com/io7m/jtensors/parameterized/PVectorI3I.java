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

/**
 * <p>
 * A three-dimensional immutable vector type with integer elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

public final class PVectorI3I<T> implements PVectorReadable3IType<T>
{
  /**
   * The zero vector.
   */

  public static final PVectorI3I<?> ZERO = new PVectorI3I<Integer>(0, 0, 0);

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   *
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> absolute(
    final PVectorReadable3IType<T> v)
    throws ArithmeticException
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    final int z = CheckedMath.absolute(v.getZI());
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> add(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
    final int z = CheckedMath.add(v0.getZI(), v1.getZI());
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> addScaled(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int mz = CheckedMath.multiply(v1.getZI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    final int z = CheckedMath.add(v0.getZI(), mz);
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @since 7.0.0
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clamp(
    final PVectorReadable3IType<T> v,
    final int minimum,
    final int maximum)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
    final int z = Math.min(Math.max(v.getZI(), minimum), maximum);
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 7.0.0
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clampByPVector(
    final PVectorReadable3IType<T> v,
    final PVectorReadable3IType<T> minimum,
    final PVectorReadable3IType<T> maximum)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
    final int z =
      Math.min(Math.max(v.getZI(), minimum.getZI()), maximum.getZI());
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * @since 7.0.0
   * @return A vector with both elements equal to at most <code>maximum</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clampMaximum(
    final PVectorReadable3IType<T> v,
    final int maximum)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
    final int z = Math.min(v.getZI(), maximum);
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @since 7.0.0
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clampMaximumByPVector(
    final PVectorReadable3IType<T> v,
    final PVectorReadable3IType<T> maximum)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
    final int z = Math.min(v.getZI(), maximum.getZI());
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @since 7.0.0
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clampMinimum(
    final PVectorReadable3IType<T> v,
    final int minimum)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
    final int z = Math.max(v.getZI(), minimum);
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @since 7.0.0
   *
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> clampMinimumByPVector(
    final PVectorReadable3IType<T> v,
    final PVectorReadable3IType<T> minimum)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
    final int z = Math.max(v.getZI(), minimum.getZI());
    return new PVectorI3I<T>(x, y, z);
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
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> int distance(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1)
    throws ArithmeticException
  {
    return PVectorI3I.magnitude(PVectorI3I.subtract(v0, v1));
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
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> int dotProduct(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    final int mz = CheckedMath.multiply(v0.getZI(), v1.getZI());
    return CheckedMath.add(CheckedMath.add(mx, my), mz);
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>.
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
   *
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> interpolateLinear(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1,
    final double alpha)
    throws ArithmeticException
  {
    final PVectorI3I<T> w0 = PVectorI3I.scale(v0, 1.0 - alpha);
    final PVectorI3I<T> w1 = PVectorI3I.scale(v1, alpha);
    return PVectorI3I.add(w0, w1);
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
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> int magnitude(
    final PVectorReadable3IType<T> v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt(PVectorI3I.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> int magnitudeSquared(
    final PVectorReadable3IType<T> v)
    throws ArithmeticException
  {
    return PVectorI3I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   *
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @since 7.0.0
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   * @param q
   *          The right vector
   * @param p
   *          The left vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> projection(
    final PVectorReadable3IType<T> p,
    final PVectorReadable3IType<T> q)
    throws ArithmeticException
  {
    final int dot = PVectorI3I.dotProduct(p, q);
    final int qms = PVectorI3I.magnitudeSquared(q);
    final int s = dot / qms;
    return PVectorI3I.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>out</code>.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> scale(
    final PVectorReadable3IType<T> v,
    final double r)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    final int mz = CheckedMath.multiply(v.getZI(), r);
    return new PVectorI3I<T>(mx, my, mz);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>.
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI3I<T> subtract(
    final PVectorReadable3IType<T> v0,
    final PVectorReadable3IType<T> v1)
    throws ArithmeticException
  {
    final int x = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int y = CheckedMath.subtract(v0.getYI(), v1.getYI());
    final int z = CheckedMath.subtract(v0.getZI(), v1.getZI());
    return new PVectorI3I<T>(x, y, z);
  }

  /**
   * @return The zero vector.
   *
   * @param <T>
   *          A phantom type parameter.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI3I<T> zero()
  {
    return (PVectorI3I<T>) PVectorI3I.ZERO;
  }

  private final int x;
  private final int y;
  private final int z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public PVectorI3I()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   * @param in_z
   *          The <code>z</code> value
   */

  public PVectorI3I(
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
   * <code>in_v</code>.
   *
   * @param in_v
   *          The source vector
   */

  public PVectorI3I(
    final PVectorReadable3IType<T> in_v)
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
    final PVectorI3I<?> other = (PVectorI3I<?>) obj;
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
    builder.append("[PVectorI3I ");
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
