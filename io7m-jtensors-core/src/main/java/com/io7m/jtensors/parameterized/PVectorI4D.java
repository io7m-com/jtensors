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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A four-dimensional immutable vector type with double precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

public final class PVectorI4D<T> implements PVectorReadable4DType<T>
{
  /**
   * The zero vector.
   */

  private static final PVectorI4D<?> ZERO;

  static {
    ZERO = new PVectorI4D<Float>(0.0, 0.0, 0.0, 0.0);
  }

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return <code>(abs v.x, abs v.y, abs v.z, abs v.w)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> absolute(
    final PVectorReadable4DType<T> v)
  {
    return new PVectorI4D<T>(
      Math.abs(v.getXD()),
      Math.abs(v.getYD()),
      Math.abs(v.getZD()),
      Math.abs(v.getWD()));
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> add(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1)
  {
    return new PVectorI4D<T>(
      v0.getXD() + v1.getXD(),
      v0.getYD() + v1.getYD(),
      v0.getZD() + v1.getZD(),
      v0.getWD() + v1.getWD());
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> addScaled(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1,
    final double r)
  {
    return PVectorI4D.add(v0, PVectorI4D.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors <code>va</code> and <code>vb</code>
   * are equal to within the degree of error given in <code>context</code>.
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   *
   * @param context
   *          The equality context
   * @param va
   *          The left input vector
   * @param vb
   *          The right input vector
   * @since 7.0.0
   * @return <code>true</code> iff the vectors are almost equal.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> boolean almostEqual(
    final AlmostEqualDouble.ContextRelative context,
    final PVectorReadable4DType<T> va,
    final PVectorReadable4DType<T> vb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, va.getXD(), vb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, va.getYD(), vb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, va.getZD(), vb.getZD());
    final boolean ws =
      AlmostEqualDouble.almostEqual(context, va.getWD(), vb.getWD());
    return xs && ys && zs && ws;
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
   *
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clamp(
    final PVectorReadable4DType<T> v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    final double w = Math.min(Math.max(v.getWD(), minimum), maximum);
    return new PVectorI4D<T>(x, y, z, w);
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
   *
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clampByPVector(
    final PVectorReadable4DType<T> v,
    final PVectorReadable4DType<T> minimum,
    final PVectorReadable4DType<T> maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    final double w =
      Math.min(Math.max(v.getWD(), minimum.getWD()), maximum.getWD());
    return new PVectorI4D<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most <code>maximum</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clampMaximum(
    final PVectorReadable4DType<T> v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    final double w = Math.min(v.getWD(), maximum);
    return new PVectorI4D<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clampMaximumByPVector(
    final PVectorReadable4DType<T> v,
    final PVectorReadable4DType<T> maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    final double w = Math.min(v.getWD(), maximum.getWD());
    return new PVectorI4D<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clampMinimum(
    final PVectorReadable4DType<T> v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    final double w = Math.max(v.getWD(), minimum);
    return new PVectorI4D<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> clampMinimumByPVector(
    final PVectorReadable4DType<T> v,
    final PVectorReadable4DType<T> minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    final double w = Math.max(v.getWD(), minimum.getWD());
    return new PVectorI4D<T>(x, y, z, w);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double distance(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1)
  {
    return PVectorI4D.magnitude(PVectorI4D.subtract(v0, v1));
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double dotProduct(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
    final double w = v0.getWD() * v1.getWD();
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>.
   *
   * The <code>alpha</code> parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li><code>interpolateLinear(v0, v1, 0.0) = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0) = v1</code></li>
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
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> interpolateLinear(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1,
    final double alpha)
  {
    final PVectorReadable4DType<T> w0 = PVectorI4D.scale(v0, 1.0 - alpha);
    final PVectorReadable4DType<T> w1 = PVectorI4D.scale(v1, alpha);
    return PVectorI4D.add(w0, w1);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitude(
    final PVectorReadable4DType<T> v)
  {
    return Math.sqrt(PVectorI4D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable4DType<T> v)
  {
    return PVectorI4D.dotProduct(v, v);
  }

  /**
   * Normalize the vector <code>v</code>, preserving its direction but
   * reducing it to unit length.
   *
   * @param v
   *          The input vector
   *
   * @return A vector with the same orientation as <code>v</code> but with
   *         magnitude equal to <code>1.0</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> normalize(
    final PVectorReadable4DType<T> v)
  {
    final double m = PVectorI4D.magnitudeSquared(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorI4D.scale(v, reciprocal);
    }
    return new PVectorI4D<T>(v);
  }

  /**
   * <p>
   * Orthonormalize and return the vectors <code>v0</code> and <code>v1</code>
   * .
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @return A pair <code>(v0, v1)</code>, orthonormalized.
   * @since 7.0.0
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorI4D<T>, PVectorI4D<T>> orthoNormalize(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1)
  {
    final PVectorI4D<T> v0n = PVectorI4D.normalize(v0);
    final PVectorI4D<T> projection =
      PVectorI4D.scale(v0n, PVectorI4D.dotProduct(v1, v0n));
    final PVectorI4D<T> vr =
      PVectorI4D.normalize(PVectorI4D.subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   *
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> projection(
    final PVectorReadable4DType<T> p,
    final PVectorReadable4DType<T> q)
  {
    final double dot = PVectorI4D.dotProduct(p, q);
    final double qms = PVectorI4D.magnitudeSquared(q);
    final double s = dot / qms;
    return PVectorI4D.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return <code>(v.x * r, v.y * r, v.z * r, v.w * r)</code>
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> scale(
    final PVectorReadable4DType<T> v,
    final double r)
  {
    return new PVectorI4D<T>(
      v.getXD() * r,
      v.getYD() * r,
      v.getZD() * r,
      v.getWD() * r);
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
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorI4D<T> subtract(
    final PVectorReadable4DType<T> v0,
    final PVectorReadable4DType<T> v1)
  {
    return new PVectorI4D<T>(
      v0.getXD() - v1.getXD(),
      v0.getYD() - v1.getYD(),
      v0.getZD() - v1.getZD(),
      v0.getWD() - v1.getWD());
  }

  /**
   * @return The zero vector.
   *
   * @param <T>
   *          A phantom type parameter.
   */

  @SuppressWarnings("unchecked") public static <T> PVectorI4D<T> zero()
  {
    return (PVectorI4D<T>) PVectorI4D.ZERO;
  }

  private final double w;
  private final double x;
  private final double y;
  private final double z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public PVectorI4D()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
    this.w = 1.0;
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
   * @param in_w
   *          The <code>w</code> value
   */

  public PVectorI4D(
    final double in_x,
    final double in_y,
    final double in_z,
    final double in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>in_v</code>.
   *
   * @param in_v
   *          The input vector.
   */

  public PVectorI4D(
    final PVectorReadable4DType<T> in_v)
  {
    this.x = in_v.getXD();
    this.y = in_v.getYD();
    this.z = in_v.getZD();
    this.w = in_v.getWD();
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
    final PVectorI4D<?> other = (PVectorI4D<?>) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    return true;
  }

  @Override public double getWD()
  {
    return this.w;
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public double getZD()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.w);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorI4D ");
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
