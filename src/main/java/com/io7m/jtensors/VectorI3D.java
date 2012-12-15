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

import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jaux.functional.Pair;

/**
 * A three-dimensional immutable vector type with double precision elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@Immutable public final class VectorI3D implements VectorReadable3D
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

  public static @Nonnull VectorI3D addScaled(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1,
    final double r)
  {
    return VectorI3D.add(v0, VectorI3D.scale(v1, r));
  }

  /**
   * Determine whether or not the elements of the two vectors <code>v0</code>
   * and <code>v1</code> are approximately equal.
   * 
   * @see ApproximatelyEqualDouble
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return true, iff <code>v0</code> is approximately equal to
   *         <code>v1</code> , within an appropriate degree of error for
   *         double precision floating point values
   */

  public static boolean approximatelyEqual(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    final boolean ex =
      ApproximatelyEqualDouble.approximatelyEqual(v0.x, v1.x);
    final boolean ey =
      ApproximatelyEqualDouble.approximatelyEqual(v0.y, v1.y);
    final boolean ez =
      ApproximatelyEqualDouble.approximatelyEqual(v0.z, v1.z);
    return ex && ey && ez;
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

  public static @Nonnull VectorI3D clamp(
    final @Nonnull VectorI3D v,
    final double minimum,
    final double maximum)
  {
    final double x = Math.min(Math.max(v.x, minimum), maximum);
    final double y = Math.min(Math.max(v.y, minimum), maximum);
    final double z = Math.min(Math.max(v.z, minimum), maximum);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D clampByVector(
    final @Nonnull VectorI3D v,
    final @Nonnull VectorI3D minimum,
    final @Nonnull VectorI3D maximum)
  {
    final double x = Math.min(Math.max(v.x, minimum.x), maximum.x);
    final double y = Math.min(Math.max(v.y, minimum.y), maximum.y);
    final double z = Math.min(Math.max(v.z, minimum.z), maximum.z);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D clampMaximum(
    final @Nonnull VectorI3D v,
    final double maximum)
  {
    final double x = Math.min(v.x, maximum);
    final double y = Math.min(v.y, maximum);
    final double z = Math.min(v.z, maximum);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D clampMaximumByVector(
    final @Nonnull VectorI3D v,
    final @Nonnull VectorI3D maximum)
  {
    final double x = Math.min(v.x, maximum.x);
    final double y = Math.min(v.y, maximum.y);
    final double z = Math.min(v.z, maximum.z);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D clampMinimum(
    final @Nonnull VectorI3D v,
    final double minimum)
  {
    final double x = Math.max(v.x, minimum);
    final double y = Math.max(v.y, minimum);
    final double z = Math.max(v.z, minimum);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D clampMinimumByVector(
    final @Nonnull VectorI3D v,
    final @Nonnull VectorI3D minimum)
  {
    final double x = Math.max(v.x, minimum.x);
    final double y = Math.max(v.y, minimum.y);
    final double z = Math.max(v.z, minimum.z);
    return new VectorI3D(x, y, z);
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

  public static @Nonnull VectorI3D crossProduct(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    final double x = (v0.y * v1.z) - (v0.z * v1.y);
    final double y = (v0.z * v1.x) - (v0.x * v1.z);
    final double z = (v0.x * v1.y) - (v0.y * v1.x);
    return new VectorI3D(x, y, z);
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
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    return VectorI3D.magnitude(VectorI3D.subtract(v0, v1));
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
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    final double x = v0.x * v1.x;
    final double y = v0.y * v1.y;
    final double z = v0.z * v1.z;
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

  public static @Nonnull VectorI3D interpolateLinear(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1,
    final double alpha)
  {
    final @Nonnull VectorI3D w0 = VectorI3D.scale(v0, 1.0 - alpha);
    final @Nonnull VectorI3D w1 = VectorI3D.scale(v1, alpha);
    return VectorI3D.add(w0, w1);
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
    final @Nonnull VectorI3D v)
  {
    return Math.sqrt(VectorI3D.magnitudeSquared(v));
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
    final @Nonnull VectorI3D v)
  {
    return VectorI3D.dotProduct(v, v);
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

  public static @Nonnull VectorI3D normalize(
    final @Nonnull VectorI3D v)
  {
    final double m = VectorI3D.magnitudeSquared(v);
    if (m > 0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorI3D.scale(v, reciprocal);
    }
    return v;
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
   * @since 4.2.0
   */

  public static @Nonnull Pair<VectorI3D, VectorI3D> orthoNormalize(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    final VectorI3D v0n = VectorI3D.normalize(v0);
    final VectorI3D projection =
      VectorI3D.scale(v0n, VectorI3D.dotProduct(v1, v0n));
    final VectorI3D vr =
      VectorI3D.normalize(VectorI3D.subtract(v1, projection));
    return new Pair<VectorI3D, VectorI3D>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorI3D projection(
    final @Nonnull VectorI3D p,
    final @Nonnull VectorI3D q)
  {
    final double dot = VectorI3D.dotProduct(p, q);
    final double qms = VectorI3D.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI3D.scale(p, s);
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

  public static @Nonnull VectorI3D scale(
    final @Nonnull VectorI3D v,
    final double r)
  {
    return new VectorI3D(v.x * r, v.y * r, v.z * r);
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

  public static @Nonnull VectorI3D subtract(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    return new VectorI3D(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z);
  }

  public final double                    x;
  public final double                    y;
  public final double                    z;

  /**
   * The zero vector.
   */

  public static final @Nonnull VectorI3D ZERO = new VectorI3D(0.0, 0.0, 0.0);

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull VectorI3D absolute(
    final @Nonnull VectorI3D v)
  {
    return new VectorI3D(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
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

  public static @Nonnull VectorI3D add(
    final @Nonnull VectorI3D v0,
    final @Nonnull VectorI3D v1)
  {
    return new VectorI3D(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z);
  }

  /**
   * Default constructor, initializing the vector with values <code>[0.0, 0.0,
   * 0.0]</code>.
   */

  public VectorI3D()
  {
    this.x = 0.0;
    this.y = 0.0;
    this.z = 0.0;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI3D(
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

  public VectorI3D(
    final VectorReadable3D v)
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
    final @Nonnull VectorI3D other = (VectorI3D) obj;
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
    builder.append("[VectorI3D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
