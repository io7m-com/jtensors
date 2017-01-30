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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * <p> A four-dimensional immutable vector type with {@code float} elements.
 * </p> <p> Values of this type are immutable and can therefore be safely
 * accessed from multiple threads. </p>
 *
 * @param <T> A phantom type parameter.
 */

@Immutable
public final class PVectorI4F<T> implements PVectorReadable4FType<T>
{
  /**
   * The zero vector.
   */

  private static final PVectorI4F<?> ZERO = new PVectorI4F<Float>(
    0.0f, 0.0f, 0.0f, 0.0f);
  private final float w;
  private final float x;
  private final float y;
  private final float z;

  /**
   * Default constructor, initializing the vector with values {@code [0.0, 0.0,
   * 0.0, 1.0]}.
   */

  public PVectorI4F()
  {
    this.x = 0.0f;
    this.y = 0.0f;
    this.z = 0.0f;
    this.w = 1.0f;
  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x The {@code x} value
   * @param in_y The {@code y} value
   * @param in_z The {@code z} value
   * @param in_w The {@code w} value
   */

  public PVectorI4F(
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
   * Construct a vector initialized with the values given in the vector {@code
   * in_v}.
   *
   * @param in_v The source vector
   */

  public PVectorI4F(
    final PVectorReadable4FType<T> in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
    this.z = in_v.getZF();
    this.w = in_v.getWF();
  }

  /**
   * Calculate the absolute value of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (abs v.getXF(), abs v.getYF(), abs v.getZF(), abs
   * v.getWF())}
   */

  public static <T> PVectorI4F<T> absolute(
    final PVectorReadable4FType<T> v)
  {
    return new PVectorI4F<T>(
      Math.abs(v.getXF()),
      Math.abs(v.getYF()),
      Math.abs(v.getZF()),
      Math.abs(v.getWF()));
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.getXF() + v1.getXF(), v0.getYF() + v1.getYF(),
   * v0.getZF() + v1.getZF(), v0.getWF() + v1.getWF())}
   */

  public static <T> PVectorI4F<T> add(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    return new PVectorI4F<T>(
      v0.getXF() + v1.getXF(),
      v0.getYF() + v1.getYF(),
      v0.getZF() + v1.getZF(),
      v0.getWF() + v1.getWF());
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.getXF() + (v1.getXF() * r), v0.getYF() + (v1.getYF() *
   * r), v0.getZF() + (v1.getZF() * r), v0.getWF() + (v1.getWF() * r))}
   */

  public static <T> PVectorI4F<T> addScaled(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
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
   * @param <T>     A phantom type parameter.
   *
   * @return {@code true} iff the vectors are almost equal.
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float,
   * float)
   * @since 7.0.0
   */

  public static <T> boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final PVectorReadable4FType<T> va,
    final PVectorReadable4FType<T> vb)
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
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * maximum]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum} and at
   * least {@code minimum}
   */

  public static <T> PVectorI4F<T> clamp(
    final PVectorReadable4FType<T> v,
    final float minimum,
    final float maximum)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    final float w = Math.min(Math.max(v.getWF(), minimum), maximum);
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum} and {@code maximum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code ( min(max(v.getXF(), minimum.getXF()), maximum.getXF()),
   * min(max(v.getYF(), minimum.getYF()), maximum.getYF()), min(max(v.getZF(),
   * minimum.getZF()), maximum.getZF()), min(max(v.getWF(), minimum.getWF()),
   * maximum.getWF()) )}
   */

  public static <T> PVectorI4F<T> clampByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum,
    final PVectorReadable4FType<T> maximum)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    final float w =
      Math.min(Math.max(v.getWF(), minimum.getWF()), maximum.getWF());
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [-Infinity
   * .. maximum]} inclusive.
   *
   * @param v       The input vector
   * @param maximum The maximum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at most {@code maximum}
   */

  public static <T> PVectorI4F<T> clampMaximum(
    final PVectorReadable4FType<T> v,
    final float maximum)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    final float w = Math.min(v.getWF(), maximum);
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code maximum}.
   *
   * @param v       The input vector
   * @param maximum The vector containing the maximum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (min(v.getXF(), maximum.getXF()), min(v.getYF(),
   * maximum.getYF()), min(v.getZF(), maximum.getZF()), min(v.getWF(),
   * maximum.getWF()))}
   */

  public static <T> PVectorI4F<T> clampMaximumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> maximum)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    final float w = Math.min(v.getWF(), maximum.getWF());
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range {@code [minimum ..
   * Infinity]} inclusive.
   *
   * @param v       The input vector
   * @param minimum The minimum allowed value
   * @param <T>     A phantom type parameter.
   *
   * @return A vector with both elements equal to at least {@code minimum}.
   */

  public static <T> PVectorI4F<T> clampMinimum(
    final PVectorReadable4FType<T> v,
    final float minimum)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    final float w = Math.max(v.getWF(), minimum);
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range given by
   * the corresponding elements in {@code minimum}.
   *
   * @param v       The input vector
   * @param minimum The vector containing the minimum acceptable values
   * @param <T>     A phantom type parameter.
   *
   * @return {@code (max(v.getXF(), minimum.getXF()), max(v.getYF(),
   * minimum.getYF()), max(v.getZF(), minimum.getZF()), max(v.getWF(),
   * minimum.getWF()))}
   */

  public static <T> PVectorI4F<T> clampMinimumByPVector(
    final PVectorReadable4FType<T> v,
    final PVectorReadable4FType<T> minimum)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    final float w = Math.max(v.getWF(), minimum.getWF());
    return new PVectorI4F<T>(x, y, z, w);
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The distance between the two vectors
   */

  public static <T> float distance(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    return magnitude(subtract(v0, v1));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and {@code v1}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return The scalar product of the two vectors
   */

  public static <T> float dotProduct(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
    final float z = v0.getZF() * v1.getZF();
    final float w = v0.getWF() * v1.getWF();
    return x + y + z + w;
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
   * @param <T>   A phantom type parameter.
   *
   * @return {@code (1 - alpha) * v0 + alpha * v1}
   */

  public static <T> PVectorI4F<T> interpolateLinear(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1,
    final float alpha)
  {
    final PVectorReadable4FType<T> w0 = scale(v0, 1.0f - alpha);
    final PVectorReadable4FType<T> w1 = scale(v1, alpha);
    return add(w0, w1);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The magnitude of the input vector
   */

  public static <T> float magnitude(
    final PVectorReadable4FType<T> v)
  {
    return (float) Math.sqrt((double) magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return The squared magnitude of the input vector
   */

  public static <T> float magnitudeSquared(
    final PVectorReadable4FType<T> v)
  {
    return dotProduct(v, v);
  }

  /**
   * Normalize the vector {@code v}, preserving its direction but reducing it to
   * unit length.
   *
   * @param v   The input vector
   * @param <T> A phantom type parameter.
   *
   * @return A vector with the same orientation as {@code v} but with magnitude
   * equal to {@code 1.0}
   */

  public static <T> PVectorI4F<T> normalize(
    final PVectorReadable4FType<T> v)
  {
    final float m = magnitudeSquared(v);
    if (m > 0.0F) {
      final float reciprocal = (float) (1.0 / Math.sqrt((double) m));
      return scale(v, reciprocal);
    }
    return new PVectorI4F<T>(v);
  }

  /**
   * <p> Orthonormalize and return the vectors {@code v0} and {@code v1} . </p>
   * <p> See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @param v0  The left vector
   * @param v1  The right vector
   * @param <T> A phantom type parameter.
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   *
   * @since 7.0.0
   */

  public static <T> Pair<PVectorI4F<T>, PVectorI4F<T>> orthoNormalize(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    final PVectorI4F<T> v0n = normalize(v0);
    final PVectorI4F<T> projection =
      scale(v0n, dotProduct(v1, v0n));
    final PVectorI4F<T> vr =
      normalize(subtract(v1, projection));
    return Pair.pair(v0n, vr);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector {@code
   * q}.
   *
   * @param p   The left vector
   * @param q   The right vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   */

  public static <T> PVectorI4F<T> projection(
    final PVectorReadable4FType<T> p,
    final PVectorReadable4FType<T> q)
  {
    final float dot = dotProduct(p, q);
    final float qms = magnitudeSquared(q);
    final float s = dot / qms;
    return scale(p, s);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}.
   *
   * @param v   The input vector
   * @param r   The scaling value
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v.getXF() * r, v.getYF() * r, v.getZF() * r, v.getWF() *
   * r)}
   */

  public static <T> PVectorI4F<T> scale(
    final PVectorReadable4FType<T> v,
    final float r)
  {
    return new PVectorI4F<T>(
      v.getXF() * r, v.getYF() * r, v.getZF() * r, v.getWF() * r);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0}.
   *
   * @param v0  The left input vector
   * @param v1  The right input vector
   * @param <T> A phantom type parameter.
   *
   * @return {@code (v0.getXF() - v1.getXF(), v0.getYF() - v1.getYF(),
   * v0.getZF() - v1.getZF())}
   */

  public static <T> PVectorI4F<T> subtract(
    final PVectorReadable4FType<T> v0,
    final PVectorReadable4FType<T> v1)
  {
    return new PVectorI4F<T>(
      v0.getXF() - v1.getXF(),
      v0.getYF() - v1.getYF(),
      v0.getZF() - v1.getZF(),
      v0.getWF() - v1.getWF());
  }

  /**
   * @param <T> A phantom type parameter.
   *
   * @return The zero vector.
   */

  @SuppressWarnings("unchecked")
  public static <T> PVectorI4F<T> zero()
  {
    return (PVectorI4F<T>) ZERO;
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
    final PVectorI4F<?> other = (PVectorI4F<?>) obj;
    if (Float.floatToIntBits(this.getWF()) != Float.floatToIntBits(
      other.getWF())) {
      return false;
    }
    if (Float.floatToIntBits(this.getXF()) != Float.floatToIntBits(
      other.getXF())) {
      return false;
    }
    return Float.floatToIntBits(this.getYF()) == Float.floatToIntBits(
      other.getYF()) && Float.floatToIntBits(this.getZF()) == Float.floatToIntBits(
      other.getZF());
  }

  @Override
  public float getWF()
  {
    return this.w;
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
    result = (prime * result) + Float.floatToIntBits(this.getWF());
    result = (prime * result) + Float.floatToIntBits(this.getXF());
    result = (prime * result) + Float.floatToIntBits(this.getYF());
    result = (prime * result) + Float.floatToIntBits(this.getZF());
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorI4F ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
    builder.append(" ");
    builder.append(this.getZF());
    builder.append(" ");
    builder.append(this.getWF());
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
