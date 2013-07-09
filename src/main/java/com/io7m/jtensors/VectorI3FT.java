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

import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

/**
 * <p>
 * A three-dimensional immutable vector type with single precision elements.
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

@Immutable public final class VectorI3FT<A> implements VectorReadable3FT<A>
{
  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.getXF(), abs v.getYF(), abs v.getZF())</code>
   */

  public static @Nonnull <A> VectorI3FT<A> absolute(
    final @Nonnull VectorReadable3FT<A> v)
  {
    return new VectorI3FT<A>(
      Math.abs(v.getXF()),
      Math.abs(v.getYF()),
      Math.abs(v.getZF()));
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
   * @return <code>(v0.getXF() + v1.getXF(), v0.getYF() + v1.getYF(), v0.getZF() + v1.getZF())</code>
   */

  public static @Nonnull <A> VectorI3FT<A> add(
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1)
  {
    return new VectorI3FT<A>(
      v0.getXF() + v1.getXF(),
      v0.getYF() + v1.getYF(),
      v0.getZF() + v1.getZF());
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
   * @return <code>(v0.getXF() + (v1.getXF() * r), v0.getYF() + (v1.getYF() * r), v0.getZF() + (v1.getZF() * r))</code>
   */

  public static @Nonnull <A> VectorI3FT<A> addScaled(
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1,
    final float r)
  {
    return VectorI3FT.add(v0, VectorI3FT.scale(v1, r));
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
    final @Nonnull VectorReadable3FT<A> va,
    final @Nonnull VectorReadable3FT<A> vb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, va.getXF(), vb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, va.getYF(), vb.getYF());
    final boolean zs =
      AlmostEqualFloat.almostEqual(context, va.getZF(), vb.getZF());
    return xs && ys && zs;
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

  public static @Nonnull <A> VectorI3FT<A> clamp(
    final @Nonnull VectorReadable3FT<A> v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    return new VectorI3FT<A>(x, y, z);
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
   * @return <code>(min(max(v.getXF(), minimum.getXF()), maximum.getXF()), min(max(v.getYF(), minimum.getYF()), maximum.getYF()), min(max(v.getZF(), minimum.getZF()), maximum.getZF()))</code>
   */

  public static @Nonnull <A> VectorI3FT<A> clampByVector(
    final @Nonnull VectorReadable3FT<A> v,
    final @Nonnull VectorReadable3FT<A> minimum,
    final @Nonnull VectorReadable3FT<A> maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    return new VectorI3FT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3FT<A> clampMaximum(
    final @Nonnull VectorReadable3FT<A> v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    return new VectorI3FT<A>(x, y, z);
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
   * @return <code>(min(v.getXF(), maximum.getXF()), min(v.getYF(), maximum.getYF()), min(v.getZF(), maximum.getZF()))</code>
   */

  public static @Nonnull <A> VectorI3FT<A> clampMaximumByVector(
    final @Nonnull VectorReadable3FT<A> v,
    final @Nonnull VectorReadable3FT<A> maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    return new VectorI3FT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3FT<A> clampMinimum(
    final @Nonnull VectorReadable3FT<A> v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    return new VectorI3FT<A>(x, y, z);
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
   * @return <code>(max(v.getXF(), minimum.getXF()), max(v.getYF(), minimum.getYF()), max(v.getZF(), minimum.getZF()))</code>
   */

  public static @Nonnull <A> VectorI3FT<A> clampMinimumByVector(
    final @Nonnull VectorReadable3FT<A> v,
    final @Nonnull VectorReadable3FT<A> minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    return new VectorI3FT<A>(x, y, z);
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

  public static @Nonnull <A> VectorI3FT<A> crossProduct(
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1)
  {
    final float x = (v0.getYF() * v1.getZF()) - (v0.getZF() * v1.getYF());
    final float y = (v0.getZF() * v1.getXF()) - (v0.getXF() * v1.getZF());
    final float z = (v0.getXF() * v1.getYF()) - (v0.getYF() * v1.getXF());
    return new VectorI3FT<A>(x, y, z);
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
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1)
  {
    return VectorI3FT.magnitude(VectorI3FT.subtract(v0, v1));
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
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
    final float z = v0.getZF() * v1.getZF();
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

  public static @Nonnull <A> VectorI3FT<A> interpolateLinear(
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1,
    final float alpha)
  {
    final @Nonnull VectorReadable3FT<A> w0 =
      VectorI3FT.scale(v0, 1.0f - alpha);
    final @Nonnull VectorReadable3FT<A> w1 = VectorI3FT.scale(v1, alpha);
    return VectorI3FT.add(w0, w1);
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
    final @Nonnull VectorReadable3FT<A> v)
  {
    return (float) Math.sqrt(VectorI3FT.magnitudeSquared(v));
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
    final @Nonnull VectorReadable3FT<A> v)
  {
    return VectorI3FT.dotProduct(v, v);
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

  public static @Nonnull <A> VectorI3FT<A> normalize(
    final @Nonnull VectorReadable3FT<A> v)
  {
    final float m = VectorI3FT.magnitudeSquared(v);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorI3FT.scale(v, reciprocal);
    }
    return new VectorI3FT<A>(v);
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
    Pair<VectorI3FT<A>, VectorI3FT<A>>
    orthoNormalize(
      final @Nonnull VectorReadable3FT<A> v0,
      final @Nonnull VectorReadable3FT<A> v1)
  {
    final VectorI3FT<A> v0n = VectorI3FT.normalize(v0);
    final VectorI3FT<A> projection =
      VectorI3FT.scale(v0n, VectorI3FT.dotProduct(v1, v0n));
    final VectorI3FT<A> vr =
      VectorI3FT.normalize(VectorI3FT.subtract(v1, projection));
    return new Pair<VectorI3FT<A>, VectorI3FT<A>>(v0n, vr);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull <A> VectorI3FT<A> projection(
    final @Nonnull VectorReadable3FT<A> p,
    final @Nonnull VectorReadable3FT<A> q)
  {
    final float dot = VectorI3FT.dotProduct(p, q);
    final float qms = VectorI3FT.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI3FT.scale(p, s);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.getXF() * r, v.getYF() * r, v.getZF() * r)</code>
   */

  public static @Nonnull <A> VectorI3FT<A> scale(
    final @Nonnull VectorReadable3FT<A> v,
    final float r)
  {
    return new VectorI3FT<A>(v.getXF() * r, v.getYF() * r, v.getZF() * r);
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

  public static @Nonnull <A> VectorI3FT<A> subtract(
    final @Nonnull VectorReadable3FT<A> v0,
    final @Nonnull VectorReadable3FT<A> v1)
  {
    return new VectorI3FT<A>(
      v0.getXF() - v1.getXF(),
      v0.getYF() - v1.getYF(),
      v0.getZF() - v1.getZF());
  }

  private final float x;

  private final float y;

  private final float z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorI3FT()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorI3FT(
    final float x,
    final float y,
    final float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI3FT(
    final VectorReadable3FT<A> v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
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
    @SuppressWarnings("unchecked") final @Nonnull VectorI3FT<A> other =
      (VectorI3FT<A>) obj;
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
    result = (prime * result) + Float.floatToIntBits(this.getXF());
    result = (prime * result) + Float.floatToIntBits(this.getYF());
    result = (prime * result) + Float.floatToIntBits(this.getZF());
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3FT ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append("]");
    return builder.toString();
  }
}
