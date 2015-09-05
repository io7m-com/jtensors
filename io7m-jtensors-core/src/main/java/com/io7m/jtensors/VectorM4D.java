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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jfunctional.Pair;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A four-dimensional mutable vector type with double precision elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

public final class VectorM4D implements VectorReadable4DType, VectorWritable4DType
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
   * @return {@code (abs v.x, abs v.y, abs v.z, abs.w)}
   */

  public static VectorM4D absolute(
    final VectorReadable4DType v,
    final VectorM4D out)
  {
    final double x = Math.abs(v.getXD());
    final double y = Math.abs(v.getYD());
    final double z = Math.abs(v.getZD());
    final double w = Math.abs(v.getWD());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * modifying the vector in-place.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z, abs v.w)}
   */

  public static VectorM4D absoluteInPlace(
    final VectorM4D v)
  {
    return VectorM4D.absolute(v, v);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static VectorM4D add(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1,
    final VectorM4D out)
  {
    final double x = v0.getXD() + v1.getXD();
    final double y = v0.getYD() + v1.getYD();
    final double z = v0.getZD() + v1.getZD();
    final double w = v0.getWD() + v1.getWD();
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)}
   */

  public static VectorM4D addInPlace(
    final VectorM4D v0,
    final VectorReadable4DType v1)
  {
    return VectorM4D.add(v0, v1, v0);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   */

  public static VectorM4D addScaled(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1,
    final double r,
    final VectorM4D out)
  {
    final double x = v0.getXD() + (v1.getXD() * r);
    final double y = v0.getYD() + (v1.getYD() * r);
    final double z = v0.getZD() + (v1.getZD() * r);
    final double w = v0.getWD() + (v1.getWD() * r);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r), v0.w + (v1.w * r))}
   */

  public static VectorM4D addScaledInPlace(
    final VectorM4D v0,
    final VectorReadable4DType v1,
    final double r)
  {
    return VectorM4D.addScaled(v0, v1, r, v0);
  }

  /**
   * Determine whether or not the vectors {@code va} and {@code vb}
   * are equal to within the degree of error given in {@code context}.
   *
   * @see AlmostEqualDouble#almostEqual(AlmostEqualDouble.ContextRelative, double, double)
   *
   * @param context
   *          The equality context
   * @param va
   *          The left input vector
   * @param vb
   *          The right input vector
   * @since 5.0.0
   * @return {@code true} if the vectors are almost equal
   */

  public static boolean almostEqual(
    final AlmostEqualDouble.ContextRelative context,
    final VectorReadable4DType va,
    final VectorReadable4DType vb)
  {
    final boolean xs =
      AlmostEqualDouble.almostEqual(context, va.getXD(), vb.getXD());
    final boolean ys =
      AlmostEqualDouble.almostEqual(context, va.getYD(), vb.getYD());
    final boolean zs =
      AlmostEqualDouble.almostEqual(context, va.getZD(), vb.getZD());
    final boolean ws =
      AlmostEqualDouble.almostEqual(context, va.getWD(), vb.getWD());
    return xs && ys && zs && ws;
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
   */

  public static VectorM4D clamp(
    final VectorReadable4DType v,
    final double minimum,
    final double maximum,
    final VectorM4D out)
  {
    final double x = Math.min(Math.max(v.getXD(), minimum), maximum);
    final double y = Math.min(Math.max(v.getYD(), minimum), maximum);
    final double z = Math.min(Math.max(v.getZD(), minimum), maximum);
    final double w = Math.min(Math.max(v.getWD(), minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4D clampByVector(
    final VectorReadable4DType v,
    final VectorReadable4DType minimum,
    final VectorReadable4DType maximum,
    final VectorM4D out)
  {
    final double x =
      Math.min(Math.max(v.getXD(), minimum.getXD()), maximum.getXD());
    final double y =
      Math.min(Math.max(v.getYD(), minimum.getYD()), maximum.getYD());
    final double z =
      Math.min(Math.max(v.getZD(), minimum.getZD()), maximum.getZD());
    final double w =
      Math.min(Math.max(v.getWD(), minimum.getWD()), maximum.getWD());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z), min(max(v.w, minimum.w), maximum.w))}
   */

  public static VectorM4D clampByVectorInPlace(
    final VectorM4D v,
    final VectorReadable4DType minimum,
    final VectorReadable4DType maximum)
  {
    return VectorM4D.clampByVector(v, minimum, maximum, v);
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
   */

  public static VectorM4D clampInPlace(
    final VectorM4D v,
    final double minimum,
    final double maximum)
  {
    return VectorM4D.clamp(v, minimum, maximum, v);
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
   */

  public static VectorM4D clampMaximum(
    final VectorReadable4DType v,
    final double maximum,
    final VectorM4D out)
  {
    final double x = Math.min(v.getXD(), maximum);
    final double y = Math.min(v.getYD(), maximum);
    final double z = Math.min(v.getZD(), maximum);
    final double w = Math.min(v.getWD(), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4D clampMaximumByVector(
    final VectorReadable4DType v,
    final VectorReadable4DType maximum,
    final VectorM4D out)
  {
    final double x = Math.min(v.getXD(), maximum.getXD());
    final double y = Math.min(v.getYD(), maximum.getYD());
    final double z = Math.min(v.getZD(), maximum.getZD());
    final double w = Math.min(v.getWD(), maximum.getWD());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z), min(v.w, maximum.w))}
   */

  public static VectorM4D clampMaximumByVectorInPlace(
    final VectorM4D v,
    final VectorReadable4DType maximum)
  {
    return VectorM4D.clampMaximumByVector(v, maximum, v);
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
   */

  public static VectorM4D clampMaximumInPlace(
    final VectorM4D v,
    final double maximum)
  {
    return VectorM4D.clampMaximum(v, maximum, v);
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
   */

  public static VectorM4D clampMinimum(
    final VectorReadable4DType v,
    final double minimum,
    final VectorM4D out)
  {
    final double x = Math.max(v.getXD(), minimum);
    final double y = Math.max(v.getYD(), minimum);
    final double z = Math.max(v.getZD(), minimum);
    final double w = Math.max(v.getWD(), minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   */

  public static VectorM4D clampMinimumByVector(
    final VectorReadable4DType v,
    final VectorReadable4DType minimum,
    final VectorM4D out)
  {
    final double x = Math.max(v.getXD(), minimum.getXD());
    final double y = Math.max(v.getYD(), minimum.getYD());
    final double z = Math.max(v.getZD(), minimum.getZD());
    final double w = Math.max(v.getWD(), minimum.getWD());
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z), max(v.w, minimum.w))}
   *         , in {@code v}
   */

  public static VectorM4D clampMinimumByVectorInPlace(
    final VectorM4D v,
    final VectorReadable4DType minimum)
  {
    return VectorM4D.clampMinimumByVector(v, minimum, v);
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
   */

  public static VectorM4D clampMinimumInPlace(
    final VectorM4D v,
    final double minimum)
  {
    return VectorM4D.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   *
   * @return output
   */

  public static VectorWritable4DType copy(
    final VectorReadable4DType input,
    final VectorWritable4DType output)
  {
    output.set4D(input.getXD(), input.getYD(), input.getZD(), input.getWD());
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
   */

  public static double distance(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1)
  {
    final VectorM4D vr = new VectorM4D();
    return VectorM4D.magnitude(VectorM4D.subtract(v0, v1, vr));
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
   */

  public static double dotProduct(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1)
  {
    final double x = v0.getXD() * v1.getXD();
    final double y = v0.getYD() * v1.getYD();
    final double z = v0.getZD() * v1.getZD();
    final double w = v0.getWD() * v1.getWD();
    return x + y + z + w;
  }

  /**
   * Linearly interpolate between {@code v0} and {@code v1} by the
   * amount {@code alpha}, saving the result to {@code r}.
   *
   * The {@code alpha} parameter controls the degree of interpolation,
   * such that:
   *
   * <ul>
   * <li>{@code interpolateLinear(v0, v1, 0.0, r) -> r = v0}</li>
   * <li>{@code interpolateLinear(v0, v1, 1.0, r) -> r = v1}</li>
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
   */

  public static VectorM4D interpolateLinear(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1,
    final double alpha,
    final VectorM4D r)
  {
    final VectorM4D w0 = new VectorM4D();
    final VectorM4D w1 = new VectorM4D();

    VectorM4D.scale(v0, 1.0 - alpha, w0);
    VectorM4D.scale(v1, alpha, w1);

    return VectorM4D.add(w0, w1, r);
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
   */

  public static double magnitude(
    final VectorReadable4DType v)
  {
    return Math.sqrt(VectorM4D.magnitudeSquared(v));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   */

  public static double magnitudeSquared(
    final VectorReadable4DType v)
  {
    return VectorM4D.dotProduct(v, v);
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
   */

  public static VectorM4D normalize(
    final VectorReadable4DType v,
    final VectorM4D out)
  {
    final double m = VectorM4D.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM4D.scale(v, reciprocal, out);
    }
    out.x = v.getXD();
    out.y = v.getYD();
    out.z = v.getZD();
    out.w = v.getWD();
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
   */

  public static VectorM4D normalizeInPlace(
    final VectorM4D v)
  {
    return VectorM4D.normalize(v, v);
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
   * @since 5.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   */

  public static Pair<VectorM4D, VectorM4D> orthoNormalize(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1)
  {
    final VectorM4D v0n = new VectorM4D();
    final VectorM4D vr = new VectorM4D();
    final VectorM4D vp = new VectorM4D();

    VectorM4D.normalize(v0, v0n);
    VectorM4D.scale(v0n, VectorM4D.dotProduct(v1, v0n), vp);
    VectorM4D.normalizeInPlace(VectorM4D.subtract(v1, vp, vr));
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
   * @since 5.0.0
   * @param v0
   *          The left vector
   * @param v1
   *          The right vector
   */

  public static void orthoNormalizeInPlace(
    final VectorM4D v0,
    final VectorM4D v1)
  {
    final VectorM4D projection = new VectorM4D();

    VectorM4D.normalizeInPlace(v0);
    VectorM4D.scale(v0, VectorM4D.dotProduct(v1, v0), projection);
    VectorM4D.subtractInPlace(v1, projection);
    VectorM4D.normalizeInPlace(v1);
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
   */

  public static VectorM4D projection(
    final VectorReadable4DType p,
    final VectorReadable4DType q,
    final VectorM4D r)
  {
    final double dot = VectorM4D.dotProduct(p, q);
    final double qms = VectorM4D.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM4D.scale(p, s, r);
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
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static VectorM4D scale(
    final VectorReadable4DType v,
    final double r,
    final VectorM4D out)
  {
    final double x = v.getXD() * r;
    final double y = v.getYD() * r;
    final double z = v.getZD() * r;
    final double w = v.getWD() * r;
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v.x * r, v.y * r, v.z * r, v.w * r)}
   */

  public static VectorM4D scaleInPlace(
    final VectorM4D v,
    final double r)
  {
    return VectorM4D.scale(v, r, v);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static VectorM4D subtract(
    final VectorReadable4DType v0,
    final VectorReadable4DType v1,
    final VectorM4D out)
  {
    final double x = v0.getXD() - v1.getXD();
    final double y = v0.getYD() - v1.getYD();
    final double z = v0.getZD() - v1.getZD();
    final double w = v0.getWD() - v1.getWD();
    out.x = x;
    out.y = y;
    out.z = z;
    out.w = w;
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)}
   */

  public static VectorM4D subtractInPlace(
    final VectorM4D v0,
    final VectorReadable4DType v1)
  {
    return VectorM4D.subtract(v0, v1, v0);
  }

  private double w = 1.0;
  private double x;
  private double y;
  private double z;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0.0, 0.0, 0.0, 1.0]}.
   */

  public VectorM4D()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   *
   * @param in_x
   *          The {@code x} value
   * @param in_y
   *          The {@code y} value
   * @param in_z
   *          The {@code z} value
   * @param in_w
   *          The {@code w} value
   */

  public VectorM4D(
    final double in_x,
    final double in_y,
    final double in_z,
    final double in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  /**
   * Construct a vector initialized with the values given in the vector
   * {@code v}.
   *
   * @param in_v
   *          The source vector
   */

  public VectorM4D(
    final VectorReadable4DType in_v)
  {
    this.x = in_v.getXD();
    this.y = in_v.getYD();
    this.z = in_v.getZD();
    this.w = in_v.getWD();
  }

  @Override public void copyFrom2D(
    final VectorReadable2DType in_v)
  {
    VectorM2D.copy(in_v, this);
  }

  @Override public void copyFrom3D(
    final VectorReadable3DType in_v)
  {
    VectorM3D.copy(in_v, this);
  }

  @Override public void copyFrom4D(
    final VectorReadable4DType in_v)
  {
    VectorM4D.copy(in_v, this);
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
    final VectorM4D other = (VectorM4D) obj;
    if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
      return false;
    }
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

  @Override public double getWD()
  {
    return this.w;
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.w);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public void set2D(
    final double in_x,
    final double in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void set3D(
    final double in_x,
    final double in_y,
    final double in_z)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
  }

  @Override public void set4D(
    final double in_x,
    final double in_y,
    final double in_z,
    final double in_w)
  {
    this.x = in_x;
    this.y = in_y;
    this.z = in_z;
    this.w = in_w;
  }

  @Override public void setWD(
    final double in_w)
  {
    this.w = in_w;
  }

  @Override public void setXD(
    final double in_x)
  {
    this.x = in_x;
  }

  @Override public void setYD(
    final double in_y)
  {
    this.y = in_y;
  }

  @Override public void setZD(
    final double in_z)
  {
    this.z = in_z;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM4D ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

}
