/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A two-dimensional immutable vector type with double precision elements.
 * </p>
 * <p>
 * The intention of the type parameter <code>A</code> is to be used as a
 * "phantom type" or compile-time tag to statically distinguish between
 * semantically distinct vectors.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 * 
 * @since 5.1.0
 */

@Immutable public final class VectorI2DT<A> implements VectorReadable2DT<A>
{
  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public static final @Nonnull <A> VectorI2DT<A> absolute(
    final @Nonnull VectorReadable2DT<A> v)
  {
    return new VectorI2DT<A>(Math.abs(v.getXD()), Math.abs(v.getYD()));
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

  public static final @Nonnull <A> VectorI2DT<A> add(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1)
  {
    return new VectorI2DT<A>(v0.getXD() + v1.getXD(), v0.getYD() + v1.getYD());
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

  public static final @Nonnull <A> VectorI2DT<A> addScaled(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1,
    final double r)
  {
    return VectorI2DT.add(v0, VectorI2DT.scale(v1, r));
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
   */

  public static final <A> boolean almostEqual(
    final @Nonnull ContextRelative context,
    final @Nonnull VectorReadable2DT<A> qa,
    final @Nonnull VectorReadable2DT<A> qb)
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

  public static final <A> double angle(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1)
  {
    final double m0 = VectorI2DT.magnitude(v0);
    final double m1 = VectorI2DT.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, VectorI2DT.dotProduct(v0, v1)), 1.0);
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

  public static final @Nonnull <A> VectorI2DT<A> clamp(
    final @Nonnull VectorReadable2DT<A> v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    return new VectorI2DT<A>(x, y);
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

  public static final @Nonnull <A> VectorI2DT<A> clampByVector(
    final @Nonnull VectorReadable2DT<A> v,
    final @Nonnull VectorReadable2DT<A> minimum,
    final @Nonnull VectorReadable2DT<A> maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    return new VectorI2DT<A>(x, y);
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

  public static final @Nonnull <A> VectorI2DT<A> clampMaximum(
    final @Nonnull VectorReadable2DT<A> v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    return new VectorI2DT<A>(x, y);
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

  public static final @Nonnull <A> VectorI2DT<A> clampMaximumByVector(
    final @Nonnull VectorReadable2DT<A> v,
    final @Nonnull VectorReadable2DT<A> maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    return new VectorI2DT<A>(x, y);
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

  public static final @Nonnull <A> VectorI2DT<A> clampMinimum(
    final @Nonnull VectorReadable2DT<A> v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    return new VectorI2DT<A>(x, y);
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

  public static final @Nonnull <A> VectorI2DT<A> clampMinimumByVector(
    final @Nonnull VectorReadable2DT<A> v,
    final @Nonnull VectorReadable2DT<A> minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    return new VectorI2DT<A>(x, y);
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

  public static final <A> double distance(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1)
  {
    return VectorI2DT.magnitude(VectorI2DT.subtract(v0, v1));
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

  public static final <A> double dotProduct(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1)
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
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * 
   * @return <code>(1 - alpha) * v0 + alpha * v1</code>
   */

  public static final @Nonnull <A> VectorI2DT<A> interpolateLinear(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1,
    final double alpha)
  {
    final @Nonnull VectorI2DT<A> w0 = VectorI2DT.scale(v0, 1.0 - alpha);
    final @Nonnull VectorI2DT<A> w1 = VectorI2DT.scale(v1, alpha);
    return VectorI2DT.add(w0, w1);
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

  public static final <A> double magnitude(
    final @Nonnull VectorReadable2DT<A> v)
  {
    return Math.sqrt(VectorI2DT.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static final <A> double magnitudeSquared(
    final @Nonnull VectorReadable2DT<A> v)
  {
    return VectorI2DT.dotProduct(v, v);
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

  public static final @Nonnull <A> VectorI2DT<A> normalize(
    final @Nonnull VectorReadable2DT<A> v)
  {
    final double m = VectorI2DT.magnitudeSquared(v);
    if (m > 0) {
      final double sq = Math.sqrt(m);
      final double r = 1.0 / sq;
      return VectorI2DT.scale(v, r);
    }
    return new VectorI2DT<A>(v);
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
   * @return A pair <code>(v0, v1)</code>, orthonormalized.
   */

  public static final @Nonnull
    <A>
    Pair<VectorI2DT<A>, VectorI2DT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable2DT<A> v0,
      final @Nonnull VectorReadable2DT<A> v1)
  {
    final VectorI2DT<A> v0n = VectorI2DT.normalize(v0);
    final VectorI2DT<A> projection =
      VectorI2DT.scale(v0n, VectorI2DT.dotProduct(v1, v0n));
    final VectorI2DT<A> vr =
      VectorI2DT.normalize(VectorI2DT.subtract(v1, projection));
    return new Pair<VectorI2DT<A>, VectorI2DT<A>>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static final @Nonnull <A> VectorI2DT<A> projection(
    final @Nonnull VectorReadable2DT<A> p,
    final @Nonnull VectorReadable2DT<A> q)
  {
    final double dot = VectorI2DT.dotProduct(p, q);
    final double qms = VectorI2DT.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI2DT.scale(p, s);
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

  public static final @Nonnull <A> VectorI2DT<A> scale(
    final @Nonnull VectorReadable2DT<A> v,
    final double r)
  {
    return new VectorI2DT<A>(v.getXD() * r, v.getYD() * r);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static final @Nonnull <A> VectorI2DT<A> subtract(
    final @Nonnull VectorReadable2DT<A> v0,
    final @Nonnull VectorReadable2DT<A> v1)
  {
    return new VectorI2DT<A>(v0.getXD() - v1.getXD(), v0.getYD() - v1.getYD());
  }

  private final double x;

  private final double y;

  /**
   * Default constructor, initializing the vector with values <code>[0.0, 0.0,
   * 0.0]</code>.
   */

  public VectorI2DT()
  {
    this.x = 0.0;
    this.y = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI2DT(
    final double x,
    final double y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI2DT(
    final VectorReadable2DT<A> v)
  {
    this.x = v.getXD();
    this.y = v.getYD();
  }

  @Override public final boolean equals(
    final Object obj)
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
    @SuppressWarnings("unchecked") final @Nonnull VectorI2DT<A> other =
      (VectorI2DT<A>) obj;
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    return true;
  }

  @Override public final double getXD()
  {
    return this.x;
  }

  @Override public final double getYD()
  {
    return this.y;
  }

  @Override public final int hashCode()
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

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI2DT ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
  }
}
