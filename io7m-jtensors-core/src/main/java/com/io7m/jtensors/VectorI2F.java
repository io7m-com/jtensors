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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A two-dimensional immutable vector type with single precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 */

 public class VectorI2F implements VectorReadable2FType
{
  /**
   * The zero vector.
   */

  public static final VectorI2F ZERO = new VectorI2F(0.0f, 0.0f);

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y)</code>
   */

  public final static VectorI2F absolute(
    final VectorReadable2FType v)
  {
    return new VectorI2F(Math.abs(v.getXF()), Math.abs(v.getYF()));
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

  public final static VectorI2F add(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    return new VectorI2F(v0.getXF() + v1.getXF(), v0.getYF() + v1.getYF());
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

  public final static VectorI2F addScaled(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final double r)
  {
    return VectorI2F.add(v0, VectorI2F.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors <code>qa</code> and <code>qb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
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

  public final static boolean almostEqual(
    final ContextRelative context,
    final VectorReadable2FType qa,
    final VectorReadable2FType qb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, qa.getXF(), qb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, qa.getYF(), qb.getYF());
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

  public final static double angle(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    final double m0 = VectorI2F.magnitude(v0);
    final double m1 = VectorI2F.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, VectorI2F.dotProduct(v0, v1)), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
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
   *         and at least <code>minimum</code>.
   */

  public final static VectorI2F clamp(
    final VectorReadable2FType v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    return new VectorI2F(x, y);
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))</code>
   */

  public final static VectorI2F clampByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType minimum,
    final VectorReadable2FType maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    return new VectorI2F(x, y);
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

  public final static VectorI2F clampMaximum(
    final VectorReadable2FType v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    return new VectorI2F(x, y);
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

  public final static VectorI2F clampMaximumByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    return new VectorI2F(x, y);
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
   *         <code>minimum</code>
   */

  public final static VectorI2F clampMinimum(
    final VectorReadable2FType v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    return new VectorI2F(x, y);
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
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y))</code>
   */

  public final static VectorI2F clampMinimumByVector(
    final VectorReadable2FType v,
    final VectorReadable2FType minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    return new VectorI2F(x, y);
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

  public final static double distance(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    return VectorI2F.magnitude(VectorI2F.subtract(v0, v1));
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

  public final static double dotProduct(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    final double x = v0.getXF() * v1.getXF();
    final double y = v0.getYF() * v1.getYF();
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

  public final static VectorI2F interpolateLinear(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1,
    final float alpha)
  {
    final VectorReadable2FType w0 =
      VectorI2F.scale(v0, (float) (1.0 - alpha));
    final VectorReadable2FType w1 = VectorI2F.scale(v1, alpha);
    return VectorI2F.add(w0, w1);
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

  public final static double magnitude(
    final VectorReadable2FType v)
  {
    return Math.sqrt(VectorI2F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @return The squared magnitude of the input vector
   */

  public final static double magnitudeSquared(
    final VectorReadable2FType v)
  {
    return VectorI2F.dotProduct(v, v);
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

  public final static VectorI2F normalize(
    final VectorReadable2FType v)
  {
    final double m = VectorI2F.magnitudeSquared(v);
    if (m > 0) {
      final double sq = Math.sqrt(m);
      final double r = 1.0 / sq;
      return VectorI2F.scale(v, r);
    }
    return new VectorI2F(v);
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

  public final static Pair<VectorI2F, VectorI2F> orthoNormalize(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    final VectorI2F v0n = VectorI2F.normalize(v0);
    final VectorI2F projection =
      VectorI2F.scale(v0n, VectorI2F.dotProduct(v1, v0n));
    final VectorI2F vr =
      VectorI2F.normalize(VectorI2F.subtract(v1, projection));
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

  public final static VectorI2F projection(
    final VectorReadable2FType p,
    final VectorReadable2FType q)
  {
    final double dot = VectorI2F.dotProduct(p, q);
    final double qms = VectorI2F.magnitudeSquared(q);
    final double s = dot / qms;
    return VectorI2F.scale(p, s);
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

  public final static VectorI2F scale(
    final VectorReadable2FType v,
    final double r)
  {
    final double x = v.getXF() * r;
    final double y = v.getYF() * r;
    return new VectorI2F((float) x, (float) y);
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

  public final static VectorI2F subtract(
    final VectorReadable2FType v0,
    final VectorReadable2FType v1)
  {
    return new VectorI2F(v0.getXF() - v1.getXF(), v0.getYF() - v1.getYF());
  }

  private final float           x;

  private final float           y;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0]</code>.
   */

  public VectorI2F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
  }

  /**
   * Construct a vector initialized with the given values.
   * 
   * @param in_x
   *          The <code>x</code> value
   * @param in_y
   *          The <code>y</code> value
   */

  public VectorI2F(
    final float in_x,
    final float in_y)
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

  public VectorI2F(
    final VectorReadable2FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
  }

  @Override public final boolean equals(
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
    final VectorI2F other = (VectorI2F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    return true;
  }

  @Override public final float getXF()
  {
    return this.x;
  }

  @Override public final float getYF()
  {
    return this.y;
  }

  @Override public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    return result;
  }

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI2F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
