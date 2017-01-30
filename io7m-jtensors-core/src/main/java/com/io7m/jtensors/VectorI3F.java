/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A three-dimensional immutable vector type with {@code float} elements.
 * </p> <p> Values of this type are immutable and can therefore be safely
 * accessed from multiple threads. </p>
 */

@Immutable
public final class VectorI3F implements VectorReadable3FType
{
  /**
   * The zero vector.
   */

  public static final VectorI3F ZERO = new VectorI3F(0.0f, 0.0f, 0.0f);
  private final float x;
  private final float y;
  private final float z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0]}.
   */

  public VectorI3F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   */

  public VectorI3F(
    final float in_x,
    final float in_y,
    final float in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  /**
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The input vector.
   */

  public VectorI3F(
    final VectorReadable3FType in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
  }

  /**
   * Calculate the absolute value of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return {@code (abs v.getXF(), abs v.getYF(), abs v.getZF())}
   */

  public static VectorI3F absolute(
    final VectorReadable3FType v)
  {
    return new VectorI3F(
      Math.abs(v.getXF()), Math.abs(v.getYF()), Math.abs(
      v.getZF()));
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.getXF() + v1.getXF(), v0.getYF() + v1.getYF(),
   * v0.getZF() + v1.getZF())}
   */

  public static VectorI3F add(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    return new VectorI3F(
      v0.getXF() + v1.getXF(),
      v0.getYF() + v1.getYF(),
      v0.getZF() + v1.getZF());
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   * @param r  The scaling value
   *
   * @return {@code (v0.getXF() + (v1.getXF() * r), v0.getYF() + (v1.getYF() *
   * r), v0.getZF() + (v1.getZF() * r))}
   */

  public static VectorI3F addScaled(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final float r)
  {
    return add(v0, scale(v1, r));
  }

  /**
   * Determine whether or not the vectors {@code va} and {@code vb} are equal to
   * within the degree of error given in {@code context}.
   *
   * @param context The equality context
   * @param va      The left input vector
   * @param vb      The right input vector
   *
   * @return {@code true} iff the vectors are almost equal.
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 5.0.0
   */

  public static boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final VectorReadable3FType va,
    final VectorReadable3FType vb)
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
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}.
   */

  public static VectorI3F clamp(
    final VectorReadable3FType v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    return new VectorI3F(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   *
   * @return {@code ( min(max(v.getXF(), minimum.getXF()), maximum.getXF()),
   * min(max(v.getYF(), minimum.getYF()), maximum.getYF()), min(max(v.getZF(),
   * minimum.getZF()), maximum.getZF()) )}
   */

  public static VectorI3F clampByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType minimum,
    final VectorReadable3FType maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    return new VectorI3F(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static VectorI3F clampMaximum(
    final VectorReadable3FType v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    return new VectorI3F(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.getXF(), maximum.getXF()), min(v.getYF(),
   * maximum.getYF()), min(v.getZF(), maximum.getZF()))}
   */

  public static VectorI3F clampMaximumByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    return new VectorI3F(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   *
   * @return A vector with both elements equal to at least {@code minimum}
   */

  public static VectorI3F clampMinimum(
    final VectorReadable3FType v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    return new VectorI3F(x, y, z);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.getXF(), minimum.getXF()), max(v.getYF(),
   * minimum.getYF()), max(v.getZF(), minimum.getZF()))}
   */

  public static VectorI3F clampMinimumByVector(
    final VectorReadable3FType v,
    final VectorReadable3FType minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    return new VectorI3F(x, y, z);
  }

  /**
   * Calculate the cross product of the vectors {@code v0} and {@code v1}. The
   * result is a vector perpendicular to both vectors.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return A vector perpendicular to both {@code v0} and {@code v1}
   */

  public static VectorI3F crossProduct(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    final float x = (v0.getYF() * v1.getZF()) - (v0.getZF() * v1.getYF());
    final float y = (v0.getZF() * v1.getXF()) - (v0.getXF() * v1.getZF());
    final float z = (v0.getXF() * v1.getYF()) - (v0.getYF() * v1.getXF());
    return new VectorI3F(x, y, z);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The distance between the two vectors
   */

  public static float distance(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    return magnitude(subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return The scalar product of the two vectors
   */

  public static float dotProduct(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
    final float z = v0.getZF() * v1.getZF();
    return x + y + z;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the amount {@code
   * alpha}.
   *
   * The {@code alpha} parameter controls the degree of interpolation, such
   * that:
   *
   * <ul> <li>{@code interpolateLinear(v0, v1, 0.0) = v0}</li> <li>{@code
   * interpolateLinear(v0, v1, 1.0) = v1}</li> </ul>
   *
   * @param v0    The left input vector.
   * @param v1    The right input vector.
   * @param alpha The interpolation value, between {@code 0.0} and {@code 1.0}.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   */

  public static VectorI3F interpolateLinear(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1,
    final float alpha)
  {
    final VectorReadable3FType w0 = scale(v0, 1.0f - alpha);
    final VectorReadable3FType w1 = scale(v1, alpha);
    return add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v The input vector
   *
   * @return The magnitude of the input vector
   */

  public static float magnitude(
    final VectorReadable3FType v)
  {
    return (float) Math.sqrt((double) magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static float magnitudeSquared(
    final VectorReadable3FType v)
  {
    return dotProduct(v, v);
  }

  /**
   * Normalize the vector {@code v}, preserving its direction but reducing it to
   * unit length.
   *
   * @param v The input vector
   *
   * @return A vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0}
   */

  public static VectorI3F normalize(
    final VectorReadable3FType v)
  {
    final float m = magnitudeSquared(v);
    if (m > 0.0F) {
      final float reciprocal = (float) (1.0 / Math.sqrt((double) m));
      return scale(v, reciprocal);
    }
    return new VectorI3F(v);
  }

  /**
   * <p> Orthonormalize and return the vectors {@code v0} and {@code v1} . </p>
   * <p> See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0 The left vector
   * @param v1 The right vector
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   *
   * @since 5.0.0
   */

  public static Pair<VectorI3F, VectorI3F> orthoNormalize(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    final VectorI3F v0n = normalize(v0);
    final VectorI3F projection =
      scale(v0n, dotProduct(v1, v0n));
    final VectorI3F vr =
      normalize(subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p The left vector
   * @param q The right vector
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static VectorI3F projection(
    final VectorReadable3FType p,
    final VectorReadable3FType q)
  {
    final float dot = dotProduct(p, q);
    final float qms = magnitudeSquared(q);
    final float s = dot / qms;
    return scale(p, s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v The input vector
   * @param r The scaling value
   *
   * @return {@code (v.getXF() * r, v.getYF() * r, v.getZF() * r)}
   */

  public static VectorI3F scale(
    final VectorReadable3FType v,
    final float r)
  {
    return new VectorI3F(v.getXF() * r, v.getYF() * r, v.getZF() * r);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0 The left input vector
   * @param v1 The right input vector
   *
   * @return {@code (v0.getXF() - v1.getXF(), v0.getYF() - v1.getYF(),
   * v0.getZF() - v1.getZF())}
   */

  public static VectorI3F subtract(
    final VectorReadable3FType v0,
    final VectorReadable3FType v1)
  {
    return new VectorI3F(
      v0.getXF() - v1.getXF(),
      v0.getYF() - v1.getYF(),
      v0.getZF() - v1.getZF());
  }

  @Override
  public boolean equals(
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
    final VectorI3F other = (VectorI3F) obj;
    return Float.floatToIntBits(this.getXF()) == Float.floatToIntBits(
      other.getXF()) && Float.floatToIntBits(this.getYF()) == Float.floatToIntBits(
      other.getYF()) && Float.floatToIntBits(this.getZF()) == Float.floatToIntBits(
      other.getZF());
  }

  @Override
  public float getXF()
  {
    return this.x;
  }

  @Override
  public float getYF()
  {
    return this.y;
  }

  @Override
  public float getZF()
  {
    return this.z;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.getXF());
    result = (prime * result) + Float.floatToIntBits(this.getYF());
    result = (prime * result) + Float.floatToIntBits(this.getZF());
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3F ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
