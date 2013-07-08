/*
 * Copyright © 2012 http://io7m.com
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
 * A three-dimensional immutable vector type with double precision elements.
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

@Immutable public final class VectorI3DT<A> implements VectorReadable3DT<A>
{
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z *
   *         r))</code>
   */

  public static @Nonnull <A> VectorI3DT<A> addScaled(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final double r)
  {
    return VectorI3DT.add(v0, VectorI3DT.scale(v1, r));
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
   * 
   */

  public static <A> boolean almostEqual(
    final @Nonnull ContextRelative context,
    final @Nonnull VectorReadable3DT<A> qa,
    final @Nonnull VectorReadable3DT<A> qb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, qa.getXD(), qb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, qa.getYD(), qb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, qa.getZD(), qb.getZD());
    return xs && ys && zs;
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

  public static @Nonnull <A> VectorI3DT<A> clamp(
    final @Nonnull VectorReadable3DT<A> v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    return new VectorI3DT<A>(x, y, z);
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
   *         minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull <A> VectorI3DT<A> clampByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum,
    final @Nonnull VectorReadable3DT<A> maximum)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    return new VectorI3DT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3DT<A> clampMaximum(
    final @Nonnull VectorReadable3DT<A> v,
    final double maximum)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    return new VectorI3DT<A>(x, y, z);
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
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z,
   *         maximum.z))</code>
   */

  public static @Nonnull <A> VectorI3DT<A> clampMaximumByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> maximum)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    return new VectorI3DT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3DT<A> clampMinimum(
    final @Nonnull VectorReadable3DT<A> v,
    final double minimum)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    return new VectorI3DT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3DT<A> clampMinimumByVector(
    final @Nonnull VectorReadable3DT<A> v,
    final @Nonnull VectorReadable3DT<A> minimum)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    return new VectorI3DT<A>(x, y, z);
  }

  /**
   * Calculate the cross product of the vectors <code>v0</code> and
   * <code>v1</code>. The result is a vector perpendicular to both vectors.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return A vector perpendicular to both <code>v0</code> and
   *         <code>v1</code>
   */

  public static @Nonnull <A> VectorI3DT<A> crossProduct(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    final double x = (v0.getYD() * v1.getZD()) - (v0.getZD() * v1.getYD());
    final double y = (v0.getZD() * v1.getXD()) - (v0.getXD() * v1.getZD());
    final double z = (v0.getXD() * v1.getYD()) - (v0.getYD() * v1.getXD());
    return new VectorI3DT<A>(x, y, z);
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

  public static <A> double distance(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    return VectorI3DT.magnitude(VectorI3DT.subtract(v0, v1));
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

  public static <A> double dotProduct(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
    return x + y + z;
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

  public static @Nonnull <A> VectorI3DT<A> interpolateLinear(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1,
    final double alpha)
  {
    final @Nonnull VectorI3DT<A> w0 = VectorI3DT.scale(v0, 1.0 - alpha);
    final @Nonnull VectorI3DT<A> w1 = VectorI3DT.scale(v1, alpha);
    return VectorI3DT.add(w0, w1);
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

  public static <A> double magnitude(
    final @Nonnull VectorReadable3DT<A> v)
  {
    return Math.sqrt(VectorI3DT.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static <A> double magnitudeSquared(
    final @Nonnull VectorReadable3DT<A> v)
  {
    return VectorI3DT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorI3DT<A> normalize(
    final @Nonnull VectorReadable3DT<A> v)
  {
    final double m = VectorI3DT.magnitudeSquared(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorI3DT.scale(v, reciprocal);
    }
    return new VectorI3DT<A>(v);
  }

  /**
   * Orthonormalize and return the vectors <code>v0</code> and <code>v1</code>
   * .
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">Gram-Schmidt
   *      process</a>
   * 
   * @return A pair <code>(v0, v1)</code>, orthonormalized.
   * 
   * 
   */

  public static @Nonnull
    <A>
    Pair<VectorI3DT<A>, VectorI3DT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable3DT<A> v0,
      final @Nonnull VectorReadable3DT<A> v1)
  {
    final VectorI3DT<A> v0n = VectorI3DT.normalize(v0);
    final VectorI3DT<A> projection =
      VectorI3DT.scale(v0n, VectorI3DT.dotProduct(v1, v0n));
    final VectorI3DT<A> vr =
      VectorI3DT.normalize(VectorI3DT.subtract(v1, projection));
    return new Pair<VectorI3DT<A>, VectorI3DT<A>>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorI3DT<A> projection(
    final @Nonnull VectorReadable3DT<A> p,
    final @Nonnull VectorReadable3DT<A> q)
  {
    final double dot = VectorI3DT.dotProduct(p, q);
    final double qms = VectorI3DT.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI3DT.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static @Nonnull <A> VectorI3DT<A> scale(
    final @Nonnull VectorReadable3DT<A> v,
    final double r)
  {
    return new VectorI3DT<A>(v.getXD() * r, v.getYD() * r, v.getZD() * r);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull <A> VectorI3DT<A> subtract(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    return new VectorI3DT<A>(
      v0.getXD() - v1.getXD(),
      v0.getYD() - v1.getYD(),
      v0.getZD() - v1.getZD());
  }

  private final double x;
  private final double y;
  private final double z;

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull <A> VectorI3DT<A> absolute(
    final @Nonnull VectorReadable3DT<A> v)
  {
    return new VectorI3DT<A>(
      Math.abs(v.getXD()),
      Math.abs(v.getYD()),
      Math.abs(v.getZD()));
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
   */

  public static @Nonnull <A> VectorI3DT<A> add(
    final @Nonnull VectorReadable3DT<A> v0,
    final @Nonnull VectorReadable3DT<A> v1)
  {
    return new VectorI3DT<A>(
      v0.getXD() + v1.getXD(),
      v0.getYD() + v1.getYD(),
      v0.getZD() + v1.getZD());
  }

  /**
   * Default constructor, initializing the vector with values <code>[0.0, 0.0,
   * 0.0]</code>.
   */

  public VectorI3DT()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI3DT(
    final double x,
    final double y,
    final double z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI3DT(
    final VectorReadable3DT<A> v)
  {
    this.x = v.getXD();
    this.y = v.getYD();
    this.z = v.getZD();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(
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
    @SuppressWarnings("unchecked") final @Nonnull VectorI3DT<A> other =
      (VectorI3DT<A>) obj;
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
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
    builder.append("[VectorI3DT ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
