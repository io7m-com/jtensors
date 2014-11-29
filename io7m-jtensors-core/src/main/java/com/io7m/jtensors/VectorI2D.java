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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A two-dimensional immutable vector type with double precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 */

public final class VectorI2D implements VectorReadable2DType
{
  /**
   * The zero vector.
   */

  public static final VectorI2D ZERO = new VectorI2D(0.0, 0.0);

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static VectorI2D absolute(
    final VectorReadable2DType v)
  {
    return new VectorI2D(Math.abs(v.getXD()), Math.abs(v.getYD()));
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
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static VectorI2D add(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    return new VectorI2D(v0.getXD() + v1.getXD(), v0.getYD() + v1.getYD());
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r))</code>
   */

  public static VectorI2D addScaled(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
    final double r)
  {
    return VectorI2D.add(v0, VectorI2D.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors <code>qa</code> and <code>qb</code>
   * are equal to within the degree of error given in <code>context</code>.
   *
   * @see AlmostEqualDouble#almostEqual(ContextRelative, double, double)
   *
   * @param context
   *          The equality context
   * @param qa
   *          The left input vector
   * @param qb
   *          The right input vector
   * @since 5.0.0
   * @return <code>true</code> iff the vectors are almost equal.
   */

  public static boolean almostEqual(
    final ContextRelative context,
    final VectorReadable2DType qa,
    final VectorReadable2DType qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    return xs && ys;
  }

  /**
   * Calculate the angle between the vectors <code>v0</code> and
   * <code>v1</code> in radians.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The angle between the two vectors, in radians.
   */

  public static double angle(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    final double m0 = VectorI2D.magnitude(v0);
    final double m1 = VectorI2D.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, VectorI2D.dotProduct(v0, v1)), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum
   * .. maximum]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>.
   */

  public static VectorI2D clamp(
    final VectorReadable2DType v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    return new VectorI2D(x, y);
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y,
   *         minimum.y), maximum.y))</code>
   */

  public static VectorI2D clampByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType minimum,
    final VectorReadable2DType maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    return new VectorI2D(x, y);
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
   */

  public static VectorI2D clampMaximum(
    final VectorReadable2DType v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    return new VectorI2D(x, y);
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y))</code>
   */

  public static VectorI2D clampMaximumByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    return new VectorI2D(x, y);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum
   * .. Infinity]</code> inclusive.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   */

  public static VectorI2D clampMinimum(
    final VectorReadable2DType v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    return new VectorI2D(x, y);
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z,
   *         minimum.z))</code>
   */

  public static VectorI2D clampMinimumByVector(
    final VectorReadable2DType v,
    final VectorReadable2DType minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    return new VectorI2D(x, y);
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
   * @return The distance between the two vectors
   */

  public static double distance(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    return VectorI2D.magnitude(VectorI2D.subtract(v0, v1));
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
   */

  public static double dotProduct(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    return x + y;
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
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>
   *
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   */

  public static VectorI2D interpolateLinear(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1,
    final double alpha)
  {
    final VectorI2D w0 = VectorI2D.scale(v0, 1.0 - alpha);
    final VectorI2D w1 = VectorI2D.scale(v1, alpha);
    return VectorI2D.add(w0, w1);
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
   */

  public static double magnitude(
    final VectorReadable2DType v)
  {
    return Math.sqrt(VectorI2D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable2DType v)
  {
    return VectorI2D.dotProduct(v, v);
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
   */

  public static VectorI2D normalize(
    final VectorReadable2DType v)
  {
    final double m = VectorI2D.magnitudeSquared(v);
    if (m > 0) {
      final double sq = Math.sqrt(m);
      final double r = 1.0 / sq;
      return VectorI2D.scale(v, r);
    }
    return new VectorI2D(v);
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
   *
   * @since 5.0.0
   */

  public static Pair<VectorI2D, VectorI2D> orthoNormalize(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    final VectorI2D v0n = VectorI2D.normalize(v0);
    final VectorI2D projection =
      VectorI2D.scale(v0n, VectorI2D.dotProduct(v1, v0n));
    final VectorI2D vr =
      VectorI2D.normalize(VectorI2D.subtract(v1, projection));
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
   */

  public static VectorI2D projection(
    final VectorReadable2DType p,
    final VectorReadable2DType q)
  {
    final double dot = VectorI2D.dotProduct(p, q);
    final double qms = VectorI2D.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI2D.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return <code>(v.x * r, v.y * r)</code>
   */

  public static VectorI2D scale(
    final VectorReadable2DType v,
    final double r)
  {
    return new VectorI2D(v.getXD() * r, v.getYD() * r);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static VectorI2D subtract(
    final VectorReadable2DType v0,
    final VectorReadable2DType v1)
  {
    return new VectorI2D(v0.getXD() - v1.getXD(), v0.getYD() - v1.getYD());
  }

  /**
   * The <code>x</code> value.
   */

  private final double x;

  /**
   * The <code>y</code> value.
   */

  private final double y;

  /**
   * Default constructor, initializing the vector with values <code>[0.0, 0.0,
   * 0.0]</code>.
   */

  public VectorI2D()
  {
    this.x = 0.0;
    this.y = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   */

  public VectorI2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   *
   * @param in_v
   *          The input vector
   */

  public VectorI2D(
    final VectorReadable2DType in_v)
  {
    NullCheck.notNull(in_v, "Input vector");
    this.x = in_v.getXD();
    this.y = in_v.getYD();
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
    final VectorI2D other = (VectorI2D) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    return true;
  }

  @Override public double getXD()
  {
    return this.x;
  }

  @Override public double getYD()
  {
    return this.y;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI2D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
