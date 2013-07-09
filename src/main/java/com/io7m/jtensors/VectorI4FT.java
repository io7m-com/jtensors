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

import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A four-dimensional immutable vector type with single precision elements.
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

@Immutable public final class VectorI4FT<A> implements VectorReadable4FT<A>
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
   * @return <code>(v0.getXF() + (v1.getXF() * r), v0.getYF() + (v1.getYF() * r), v0.getZF() + (v1.getZF() * r), v0.getWF() + (v1.getWF() * r))</code>
   */

  public static @Nonnull <A> VectorI4FT<A> addScaled(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final float r)
  {
    return VectorI4FT.add(v0, VectorI4FT.scale(v1, r));
  }

  /**
   * Determine whether or not the vectors <code>va</code> and <code>vb</code>
   * are equal to within the degree of error given in <code>context</code>.
   * 
   * @see AlmostEqualFloat#almostEqual(ContextRelative, float, float)
   * 
   * @param context
   *          The equality context
   * @param va
   *          The left input vector
   * @param vb
   *          The right input vector
   * 
   */

  public static <A> boolean almostEqual(
    final @Nonnull AlmostEqualFloat.ContextRelative context,
    final @Nonnull VectorReadable4FT<A> va,
    final @Nonnull VectorReadable4FT<A> vb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, va.getXF(), vb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, va.getYF(), vb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, va.getZF(), vb.getZF());
    final boolean ws =
      AlmostEqualFloat.almostEqual(context, va.getWF(), vb.getWF());
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
   */

  public static @Nonnull <A> VectorI4FT<A> clamp(
    final @Nonnull VectorReadable4FT<A> v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    final float w = Math.min(Math.max(v.getWF(), minimum), maximum);
    return new VectorI4FT<A>(x, y, z, w);
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
   * @return <code>(min(max(v.getXF(), minimum.getXF()), maximum.getXF()), min(max(v.getYF(), minimum.getYF()), maximum.getYF()), min(max(v.getZF(), minimum.getZF()), maximum.getZF()), min(max(v.getWF(), minimum.getWF()), maximum.getWF()))</code>
   */

  public static @Nonnull <A> VectorI4FT<A> clampByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum,
    final @Nonnull VectorReadable4FT<A> maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
    return new VectorI4FT<A>(x, y, z, w);
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

  public static @Nonnull <A> VectorI4FT<A> clampMaximum(
    final @Nonnull VectorReadable4FT<A> v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
    return new VectorI4FT<A>(x, y, z, w);
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
   * @return <code>(min(v.getXF(), maximum.getXF()), min(v.getYF(), maximum.getYF()), min(v.getZF(), maximum.getZF()), min(v.getWF(), maximum.getWF()))</code>
   */

  public static @Nonnull <A> VectorI4FT<A> clampMaximumByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
    return new VectorI4FT<A>(x, y, z, w);
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
   */

  public static @Nonnull <A> VectorI4FT<A> clampMinimum(
    final @Nonnull VectorReadable4FT<A> v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
    return new VectorI4FT<A>(x, y, z, w);
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
   * @return <code>(max(v.getXF(), minimum.getXF()), max(v.getYF(), minimum.getYF()), max(v.getZF(), minimum.getZF()), max(v.getWF(), minimum.getWF()))</code>
   */

  public static @Nonnull <A> VectorI4FT<A> clampMinimumByVector(
    final @Nonnull VectorReadable4FT<A> v,
    final @Nonnull VectorReadable4FT<A> minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
    return new VectorI4FT<A>(x, y, z, w);
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

  public static <A> float distance(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    return VectorI4FT.magnitude(VectorI4FT.subtract(v0, v1));
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

  public static <A> float dotProduct(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
    final float z = v0.getZF() * v1.getZF();
    final float w = v0.getWF() * v1.getWF();
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
   */

  public static @Nonnull <A> VectorI4FT<A> interpolateLinear(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1,
    final float alpha)
  {
    final @Nonnull VectorReadable4FT<A> w0 =
      VectorI4FT.scale(v0, 1.0f - alpha);
    final @Nonnull VectorReadable4FT<A> w1 = VectorI4FT.scale(v1, alpha);
    return VectorI4FT.add(w0, w1);
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

  public static <A> float magnitude(
    final @Nonnull VectorReadable4FT<A> v)
  {
    return (float) Math.sqrt(VectorI4FT.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public static <A> float magnitudeSquared(
    final @Nonnull VectorReadable4FT<A> v)
  {
    return VectorI4FT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorI4FT<A> normalize(
    final @Nonnull VectorReadable4FT<A> v)
  {
    final float m = VectorI4FT.magnitudeSquared(v);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorI4FT.scale(v, reciprocal);
    }
    return new VectorI4FT<A>(v);
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
    Pair<VectorI4FT<A>, VectorI4FT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable4FT<A> v0,
      final @Nonnull VectorReadable4FT<A> v1)
  {
    final VectorI4FT<A> v0n = VectorI4FT.normalize(v0);
    final VectorI4FT<A> projection =
      VectorI4FT.scale(v0n, VectorI4FT.dotProduct(v1, v0n));
    final VectorI4FT<A> vr =
      VectorI4FT.normalize(VectorI4FT.subtract(v1, projection));
    return new Pair<VectorI4FT<A>, VectorI4FT<A>>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorI4FT<A> projection(
    final @Nonnull VectorReadable4FT<A> p,
    final @Nonnull VectorReadable4FT<A> q)
  {
    final float dot = VectorI4FT.dotProduct(p, q);
    final float qms = VectorI4FT.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI4FT.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.getXF() * r, v.getYF() * r, v.getZF() * r, v.getWF() * r)</code>
   */

  public static @Nonnull <A> VectorI4FT<A> scale(
    final @Nonnull VectorReadable4FT<A> v,
    final float r)
  {
    return new VectorI4FT<A>(
      v.getXF() * r,
      v.getYF() * r,
      v.getZF() * r,
      v.getWF() * r);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.getXF() - v1.getXF(), v0.getYF() - v1.getYF(), v0.getZF() - v1.getZF())</code>
   */

  public static @Nonnull <A> VectorI4FT<A> subtract(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    return new VectorI4FT<A>(
      v0.getXF() - v1.getXF(),
      v0.getYF() - v1.getYF(),
      v0.getZF() - v1.getZF(),
      v0.getWF() - v1.getWF());
  }

  private final float x;
  private final float y;
  private final float z;
  private final float w;

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.getXF(), abs v.getYF(), abs v.getZF(), abs v.getWF())</code>
   */

  public static @Nonnull <A> VectorI4FT<A> absolute(
    final @Nonnull VectorReadable4FT<A> v)
  {
    return new VectorI4FT<A>(
      Math.abs(v.getXF()),
      Math.abs(v.getYF()),
      Math.abs(v.getZF()),
      Math.abs(v.getWF()));
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
   * @return <code>(v0.getXF() + v1.getXF(), v0.getYF() + v1.getYF(), v0.getZF() + v1.getZF(), v0.getWF() + v1.getWF())</code>
   */

  public static @Nonnull <A> VectorI4FT<A> add(
    final @Nonnull VectorReadable4FT<A> v0,
    final @Nonnull VectorReadable4FT<A> v1)
  {
    return new VectorI4FT<A>(
      v0.getXF() + v1.getXF(),
      v0.getYF() + v1.getYF(),
      v0.getZF() + v1.getZF(),
      v0.getWF() + v1.getWF());
  }

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorI4FT()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
    this.w = 1.0f;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI4FT(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI4FT(
    final VectorReadable4FT<A> v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
    this.w = v.getWF();
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
    @SuppressWarnings("unchecked") final @Nonnull VectorReadable4FT<A> other =
      (VectorI4FT<A>) obj;
    if (Float.floatToIntBits(this.getWF()) != Float.floatToIntBits(other
      .getWF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getXF()) != Float.floatToIntBits(other
      .getXF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getYF()) != Float.floatToIntBits(other
      .getYF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getZF()) != Float.floatToIntBits(other
      .getZF())) {
      return false;
    }
    return true;
  }

  @Override public float getWF()
  {
    return this.w;
  }

  @Override public float getXF()
  {
    return this.x;
  }

  @Override public float getYF()
  {
    return this.y;
  }

  @Override public float getZF()
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
    result = (prime * result) + Float.floatToIntBits(this.getWF());
    result = (prime * result) + Float.floatToIntBits(this.getXF());
    result = (prime * result) + Float.floatToIntBits(this.getYF());
    result = (prime * result) + Float.floatToIntBits(this.getZF());
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI4FT ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append(" ");
    builder.append(this.getWF());
    builder.append("]");
    return builder.toString();
  }
}
