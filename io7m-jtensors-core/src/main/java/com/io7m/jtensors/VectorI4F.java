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
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A four-dimensional immutable vector type with single precision elements.
 * </p>
 * <p>
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 * </p>
 */

 public class VectorI4F implements VectorReadable4FType
{
  /**
   * The zero vector.
   */

  public static final VectorI4F ZERO = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);

  /**
   * Calculate the absolute value of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.getXF(), abs v.getYF(), abs v.getZF(), abs v.getWF())</code>
   */

  public final static VectorI4F absolute(
    final VectorReadable4FType v)
  {
    return new VectorI4F(Math.abs(v.getXF()), Math.abs(v.getYF()), Math.abs(v
      .getZF()), Math.abs(v.getWF()));
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

  public final static VectorI4F add(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    return new VectorI4F(
      v0.getXF() + v1.getXF(),
      v0.getYF() + v1.getYF(),
      v0.getZF() + v1.getZF(),
      v0.getWF() + v1.getWF());
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
   * @return <code>(v0.getXF() + (v1.getXF() * r), v0.getYF() + (v1.getYF() * r), v0.getZF() + (v1.getZF() * r), v0.getWF() + (v1.getWF() * r))</code>
   */

  public final static VectorI4F addScaled(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final float r)
  {
    return VectorI4F.add(v0, VectorI4F.scale(v1, r));
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
   * @since 5.0.0
   * @return <code>true</code> iff the vectors are almost equal.
   */

  public final static boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final VectorReadable4FType va,
    final VectorReadable4FType vb)
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

  public final static VectorI4F clamp(
    final VectorReadable4FType v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    final float w = Math.min(Math.max(v.getWF(), minimum), maximum);
    return new VectorI4F(x, y, z, w);
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
   * @return <code>(
   *   min(max(v.getXF(), minimum.getXF()), maximum.getXF()),
   *   min(max(v.getYF(), minimum.getYF()), maximum.getYF()), 
   *   min(max(v.getZF(), minimum.getZF()), maximum.getZF()), 
   *   min(max(v.getWF(), minimum.getWF()), maximum.getWF())
   * )</code>
   */

  public final static VectorI4F clampByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType minimum,
    final VectorReadable4FType maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
    return new VectorI4F(x, y, z, w);
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

  public final static VectorI4F clampMaximum(
    final VectorReadable4FType v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
    return new VectorI4F(x, y, z, w);
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

  public final static VectorI4F clampMaximumByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
    return new VectorI4F(x, y, z, w);
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

  public final static VectorI4F clampMinimum(
    final VectorReadable4FType v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
    return new VectorI4F(x, y, z, w);
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

  public final static VectorI4F clampMinimumByVector(
    final VectorReadable4FType v,
    final VectorReadable4FType minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
    return new VectorI4F(x, y, z, w);
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

  public final static float distance(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    return VectorI4F.magnitude(VectorI4F.subtract(v0, v1));
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

  public final static float dotProduct(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
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

  public final static VectorI4F interpolateLinear(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1,
    final float alpha)
  {
    final VectorReadable4FType w0 = VectorI4F.scale(v0, 1.0f - alpha);
    final VectorReadable4FType w1 = VectorI4F.scale(v1, alpha);
    return VectorI4F.add(w0, w1);
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

  public final static float magnitude(
    final VectorReadable4FType v)
  {
    return (float) Math.sqrt(VectorI4F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector <code>v</code>.
   * 
   * @param v
   *          The input vector
   * 
   * @return The squared magnitude of the input vector
   */

  public final static float magnitudeSquared(
    final VectorReadable4FType v)
  {
    return VectorI4F.dotProduct(v, v);
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

  public final static VectorI4F normalize(
    final VectorReadable4FType v)
  {
    final float m = VectorI4F.magnitudeSquared(v);
    if (m > 0) {
      final float reciprocal = (float) (1.0f / Math.sqrt(m));
      return VectorI4F.scale(v, reciprocal);
    }
    return new VectorI4F(v);
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
   * @since 5.0.0
   */

  public final static Pair<VectorI4F, VectorI4F> orthoNormalize(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    final VectorI4F v0n = VectorI4F.normalize(v0);
    final VectorI4F projection =
      VectorI4F.scale(v0n, VectorI4F.dotProduct(v1, v0n));
    final VectorI4F vr =
      VectorI4F.normalize(VectorI4F.subtract(v1, projection));
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

  public final static VectorI4F projection(
    final VectorReadable4FType p,
    final VectorReadable4FType q)
  {
    final float dot = VectorI4F.dotProduct(p, q);
    final float qms = VectorI4F.magnitudeSquared(q);
    final float s = dot / qms;
    return VectorI4F.scale(p, s);
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

  public final static VectorI4F scale(
    final VectorReadable4FType v,
    final float r)
  {
    return new VectorI4F(
      v.getXF() * r,
      v.getYF() * r,
      v.getZF() * r,
      v.getWF() * r);
  }

  /**
   * Subtract the vector <code>v1</code> from the vector <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.getXF() - v1.getXF(), v0.getYF() - v1.getYF(), v0.getZF() - v1.getZF())</code>
   */

  public final static VectorI4F subtract(
    final VectorReadable4FType v0,
    final VectorReadable4FType v1)
  {
    return new VectorI4F(
      v0.getXF() - v1.getXF(),
      v0.getYF() - v1.getYF(),
      v0.getZF() - v1.getZF(),
      v0.getWF() - v1.getWF());
  }

  private final float w;
  private final float x;
  private final float y;
  private final float z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0, 1.0]</code>.
   */

  public VectorI4F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
    this.w = 1.0f;
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

  public VectorI4F(
    final float in_x,
    final float in_y,
    final float in_z,
    final float in_w)
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
   *          The source vector
   */

  public VectorI4F(
    final VectorReadable4FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
    this.w = in_v.getWF();
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
    final VectorReadable4FType other = (VectorI4F) obj;
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

  @Override public final float getWF()
  {
    return this.w;
  }

  @Override public final float getXF()
  {
    return this.x;
  }

  @Override public final float getYF()
  {
    return this.y;
  }

  @Override public final float getZF()
  {
    return this.z;
  }

  @Override public final int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.getWF());
    result = (prime * result) + Float.floatToIntBits(this.getXF());
    result = (prime * result) + Float.floatToIntBits(this.getYF());
    result = (prime * result) + Float.floatToIntBits(this.getZF());
    return result;
  }

  @Override public final String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI4F ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append(" ");
    builder.append(this.getWF());
    builder.append("]");
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
