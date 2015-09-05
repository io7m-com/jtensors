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

package com.io7m.jtensors.parameterized;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorReadable2FType;

/**
 * <p>
 * A two-dimensional mutable vector type with single precision elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 *
 * @param <T>
 *          A phantom type parameter.
 */

public final class PVectorM2F<T> implements
  PVectorReadable2FType<T>,
  PVectorWritable2FType<T>
{
  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   *
   * @return {@code (abs v.x, abs v.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> absolute(
    final PVectorReadable2FType<T> v,
    final PVectorM2F<T> out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> absoluteInPlace(
    final PVectorM2F<T> v)
  {
    return PVectorM2F.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> add(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
    final PVectorM2F<T> out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and
   * {@code v1}, saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x + v1.x, v0.y + v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> addInPlace(
    final PVectorM2F<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    return PVectorM2F.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> addScaled(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
    final double r,
    final PVectorM2F<T> out)
  {
    final double x = v0.getXF() + (v1.getXF() * r);
    final double y = v0.getYF() + (v1.getYF() * r);
    out.x = (float) x;
    out.y = (float) y;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors {@code v0} and the
   * element-wise product of {@code v1} and {@code r}, saving the
   * result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> addScaledInPlace(
    final PVectorM2F<T> v0,
    final PVectorReadable2FType<T> v1,
    final double r)
  {
    return PVectorM2F.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code qa} and {@code qb}
   * are equal to within the degree of error given in {@code context}.
   *
   * @see AlmostEqualFloat#almostEqual(AlmostEqualFloat.ContextRelative, float, float)
   *
   * @param context
   *          The equality context
   * @param qa
   *          The left input vector
   * @param qb
   *          The right input vector
   * @since 7.0.0
   * @return {@code true} if the vectors are almost equal
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> boolean almostEqual(
    final AlmostEqualFloat.ContextRelative context,
    final PVectorReadable2FType<T> qa,
    final PVectorReadable2FType<T> qb)
  {
    final boolean xs =
      AlmostEqualFloat.almostEqual(context, qa.getXF(), qb.getXF());
    final boolean ys =
      AlmostEqualFloat.almostEqual(context, qa.getYF(), qb.getYF());
    return xs && ys;
  }

  /**
   * Calculate the angle between vectors {@code v0} and {@code v1},
   * in radians.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The angle between the two vectors, in radians.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double angle(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    final double m0 = PVectorM2F.magnitude(v0);
    final double m1 = PVectorM2F.magnitude(v1);
    final double dp =
      Math.min(Math.max(-1.0, PVectorM2F.dotProduct(v0, v1)), 1.0);
    final double f = m0 * m1;
    final double r = dp / f;
    return Math.acos(r);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * @param out
   *          The output vector
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clamp(
    final PVectorReadable2FType<T> v,
    final float minimum,
    final float maximum,
    final PVectorM2F<T> out)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> minimum,
    final PVectorReadable2FType<T> maximum,
    final PVectorM2F<T> out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum} and
   * {@code maximum}, saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampByPVectorInPlace(
    final PVectorM2F<T> v,
    final PVectorReadable2FType<T> minimum,
    final PVectorReadable2FType<T> maximum)
  {
    return PVectorM2F.clampByPVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         and at least {@code minimum}, in {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampInPlace(
    final PVectorM2F<T> v,
    final float minimum,
    final float maximum)
  {
    return PVectorM2F.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMaximum(
    final PVectorReadable2FType<T> v,
    final float maximum,
    final PVectorM2F<T> out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMaximumByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> maximum,
    final PVectorM2F<T> out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code maximum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   *
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMaximumByPVectorInPlace(
    final PVectorM2F<T> v,
    final PVectorReadable2FType<T> maximum)
  {
    return PVectorM2F.clampMaximumByPVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [-Infinity .. maximum]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   *
   * @return A vector with both elements equal to at most {@code maximum}
   *         , in {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMaximumInPlace(
    final PVectorM2F<T> v,
    final float maximum)
  {
    return PVectorM2F.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMinimum(
    final PVectorReadable2FType<T> v,
    final float minimum,
    final PVectorM2F<T> out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMinimumByPVector(
    final PVectorReadable2FType<T> v,
    final PVectorReadable2FType<T> minimum,
    final PVectorM2F<T> out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Clamp the elements of the vector {@code v} to the inclusive range
   * given by the corresponding elements in {@code minimum}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   *
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y))} , in
   *         {@code v}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMinimumByPVectorInPlace(
    final PVectorM2F<T> v,
    final PVectorReadable2FType<T> minimum)
  {
    return PVectorM2F.clampMinimumByPVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector {@code v} to the range
   * {@code [minimum .. Infinity]} inclusive, saving the result to
   * {@code v}.
   *
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   *
   * @return A vector with both elements equal to at least
   *         {@code minimum}, in {@code v}.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> clampMinimumInPlace(
    final PVectorM2F<T> v,
    final float minimum)
  {
    return PVectorM2F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param <U>
   *          The specific vector type
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   *
   * @return output
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T, U extends PVectorWritable2FType<T>> U copy(
    final PVectorReadable2FType<T> input,
    final U output)
  {
    output.set2F(input.getXF(), input.getYF());
    return output;
  }

  /**
   * Calculate the distance between the two vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The distance between the two vectors.
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double distance(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    final PVectorM2F<T> vr = new PVectorM2F<T>();
    return PVectorM2F.magnitude(PVectorM2F.subtract(v0, v1, vr));
  }

  /**
   * Calculate the scalar product of the vectors {@code v0} and
   * {@code v1}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return The scalar product of the two vectors
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> float dotProduct(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    final float x = v0.getXF() * v1.getXF();
    final float y = v0.getYF() * v1.getYF();
    return x + y;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -&gt; r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -&gt; r = v1}</li>
   * </ul>
   *
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between {@code 0.0} and
   *          {@code 1.0}.
   * @param r
   *          The result vector.
   *
   * @return {@code r}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> interpolateLinear(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
    final double alpha,
    final PVectorM2F<T> r)
  {
    final PVectorM2F<T> w0 = new PVectorM2F<T>();
    final PVectorM2F<T> w1 = new PVectorM2F<T>();

    PVectorM2F.scale(v0, 1.0f - alpha, w0);
    PVectorM2F.scale(v1, alpha, w1);

    return PVectorM2F.add(w0, w1, r);
  }

  /**
   * Calculate the magnitude of the vector {@code v}.
   *
   * Correspondingly, {@code magnitude(normalize(v)) == 1.0}.
   *
   * @param v
   *          The input vector
   *
   * @return The magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitude(
    final PVectorReadable2FType<T> v)
  {
    return Math.sqrt(PVectorM2F.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> double magnitudeSquared(
    final PVectorReadable2FType<T> v)
  {
    return PVectorM2F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with
   * magnitude equal to {@code 1.0} in {@code out}. The function
   * returns the zero vector iff the input is the zero vector.
   *
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   *
   * @return out
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> normalize(
    final PVectorReadable2FType<T> v,
    final PVectorM2F<T> out)
  {
    final double m = PVectorM2F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return PVectorM2F.scale(v, reciprocal, out);
    }
    out.x = v.getXF();
    out.y = v.getYF();
    return out;
  }

  /**
   * Returns a vector with the same orientation as {@code v} but with
   * magnitude equal to {@code 1.0} in {@code v}. The function
   * returns the zero vector iff the input is the zero vector.
   *
   * @param v
   *          The input vector
   *
   * @return v
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> normalizeInPlace(
    final PVectorM2F<T> v)
  {
    return PVectorM2F.normalize(v, v);
  }

  /**
   * <p>
   * Orthonormalize and return the vectors {@code v0} and {@code v1}
   * .
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @return A pair {@code (v0, v1)}, orthonormalized.
   * @since 7.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> Pair<PVectorM2F<T>, PVectorM2F<T>> orthoNormalize(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    final PVectorM2F<T> v0n = new PVectorM2F<T>();
    final PVectorM2F<T> vr = new PVectorM2F<T>();
    final PVectorM2F<T> vp = new PVectorM2F<T>();

    PVectorM2F.normalize(v0, v0n);
    PVectorM2F.scale(v0n, PVectorM2F.dotProduct(v1, v0n), vp);
    PVectorM2F.normalizeInPlace(PVectorM2F.subtract(v1, vp, vr));
    return Pair.pair(v0n, vr);
  }

  /**
   * <p>
   * Orthonormalize and the vectors {@code v0} and {@code v1}.
   * </p>
   * <p>
   * See <a href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">GSP</a>
   * </p>
   *
   * @since 7.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> void orthoNormalizeInPlace(
    final PVectorM2F<T> v0,
    final PVectorM2F<T> v1)
  {
    final PVectorM2F<T> projection = new PVectorM2F<T>();

    PVectorM2F.normalizeInPlace(v0);
    PVectorM2F.scale(v0, PVectorM2F.dotProduct(v1, v0), projection);
    PVectorM2F.subtractInPlace(v1, projection);
    PVectorM2F.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}, saving the result in {@code r}.
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> projection(
    final PVectorReadable2FType<T> p,
    final PVectorReadable2FType<T> q,
    final PVectorM2F<T> r)
  {
    final double dot = PVectorM2F.dotProduct(p, q);
    final double qms = PVectorM2F.magnitudeSquared(q);
    final double s = dot / qms;

    return PVectorM2F.scale(p, s, r);
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code out}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   *
   * @return {@code (v.x * r, v.y * r)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> scale(
    final PVectorReadable2FType<T> v,
    final double r,
    final PVectorM2F<T> out)
  {
    final double x = v.getXF() * r;
    final double y = v.getYF() * r;
    out.x = (float) x;
    out.y = (float) y;
    return out;
  }

  /**
   * Scale the vector {@code v} by the scalar {@code r}, saving the
   * result to {@code v}.
   *
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   *
   * @return {@code (v.x * r, v.y * r)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> scaleInPlace(
    final PVectorM2F<T> v,
    final double r)
  {
    return PVectorM2F.scale(v, r, v);
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code out}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> subtract(
    final PVectorReadable2FType<T> v0,
    final PVectorReadable2FType<T> v1,
    final PVectorM2F<T> out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Subtract the vector {@code v1} from the vector {@code v0},
   * saving the result to {@code v0}.
   *
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   *
   * @return {@code (v0.x - v1.x, v0.y - v1.y)}
   * @param <T>
   *          A phantom type parameter.
   */

  public static <T> PVectorM2F<T> subtractInPlace(
    final PVectorM2F<T> v0,
    final PVectorReadable2FType<T> v1)
  {
    return PVectorM2F.subtract(v0, v1, v0);
  }

  private float x;
  private float y;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0.0, 0.0]}.
   */

  public PVectorM2F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   */

  public PVectorM2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code in_v}.
   *
   * @param in_v
   *          The source vector
   */

  public PVectorM2F(
    final PVectorReadable2FType<T> in_v)
  {
    this.x = in_v.getXF();
    this.y = in_v.getYF();
  }

  @Override public void copyFrom2F(
    final VectorReadable2FType in_v)
  {
    VectorM2F.copy(in_v, this);
  }

  @Override public void copyFromTyped2F(
    final PVectorReadable2FType<T> in_v)
  {
    PVectorM2F.copy(in_v, this);
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
    final PVectorM2F<?> other = (PVectorM2F<?>) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    return result;
  }

  @Override public void set2F(
    final float in_x,
    final float in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void setXF(
    final float in_x)
  {
    this.x = in_x;
  }

  @Override public void setYF(
    final float in_y)
  {
    this.y = in_y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[PVectorM2F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
