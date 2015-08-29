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
import javax.annotation.concurrent.NotThreadSafe;

import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

/**
 * A three-dimensional mutable vector type with single precision elements.
 * 
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 */

@NotThreadSafe public final class VectorM3F implements VectorReadable3F
{
  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull VectorM3F absolute(
    final @Nonnull VectorReadable3F v,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.abs(v.getXF());
    final float y = Math.abs(v.getYF());
    final float z = Math.abs(v.getZF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector <code>v</code>,
   * modifying the vector in-place.
   * 
   * @param v
   *          The input vector
   * 
   * @return <code>(abs v.x, abs v.y, abs v.z)</code>
   */

  public static @Nonnull VectorM3F absoluteInPlace(
    final @Nonnull VectorM3F v)
  {
    return VectorM3F.absolute(v, v);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static @Nonnull VectorM3F add(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1,
    final @Nonnull VectorM3F out)
  {
    final float x = v0.getXF() + v1.getXF();
    final float y = v0.getYF() + v1.getYF();
    final float z = v0.getZF() + v1.getZF();
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>, saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static @Nonnull VectorM3F addInPlace(
    final @Nonnull VectorM3F v0,
    final @Nonnull VectorReadable3F v1)
  {
    return VectorM3F.add(v0, v1, v0);
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>out</code>.
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
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static @Nonnull VectorM3F addScaled(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1,
    final double r,
    final @Nonnull VectorM3F out)
  {
    final float x = (float) (v0.getXF() + (v1.getXF() * r));
    final float y = (float) (v0.getYF() + (v1.getYF() * r));
    final float z = (float) (v0.getZF() + (v1.getZF() * r));
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and the
   * element-wise product of <code>v1</code> and <code>r</code>, saving the
   * result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))</code>
   */

  public static @Nonnull VectorM3F addScaledInPlace(
    final @Nonnull VectorM3F v0,
    final @Nonnull VectorReadable3F v1,
    final double r)
  {
    return VectorM3F.addScaled(v0, v1, r, v0);
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
   */

  public static boolean almostEqual(
    final @Nonnull AlmostEqualFloat.ContextRelative context,
    final @Nonnull VectorReadable3F va,
    final @Nonnull VectorReadable3F vb)
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
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
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
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>
   */

  public static @Nonnull VectorM3F clamp(
    final @Nonnull VectorReadable3F v,
    final float minimum,
    final float maximum,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.min(Math.max(v.getXF(), minimum), maximum);
    final float y = Math.min(Math.max(v.getYF(), minimum), maximum);
    final float z = Math.min(Math.max(v.getZF(), minimum), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>out</code>.
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
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull VectorM3F clampByVector(
    final @Nonnull VectorReadable3F v,
    final @Nonnull VectorReadable3F minimum,
    final @Nonnull VectorReadable3F maximum,
    final @Nonnull VectorM3F out)
  {
    final float x =
      Math.min(Math.max(v.getXF(), minimum.getXF()), maximum.getXF());
    final float y =
      Math.min(Math.max(v.getYF(), minimum.getYF()), maximum.getYF());
    final float z =
      Math.min(Math.max(v.getZF(), minimum.getZF()), maximum.getZF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code> and
   * <code>maximum</code>, saving the result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))</code>
   */

  public static @Nonnull VectorM3F clampByVectorInPlace(
    final @Nonnull VectorM3F v,
    final @Nonnull VectorReadable3F minimum,
    final @Nonnull VectorReadable3F maximum)
  {
    return VectorM3F.clampByVector(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         and at least <code>minimum</code>, in <code>v</code>
   */

  public static @Nonnull VectorM3F clampInPlace(
    final @Nonnull VectorM3F v,
    final float minimum,
    final float maximum)
  {
    return VectorM3F.clamp(v, minimum, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   */

  public static @Nonnull VectorM3F clampMaximum(
    final @Nonnull VectorReadable3F v,
    final float maximum,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.min(v.getXF(), maximum);
    final float y = Math.min(v.getYF(), maximum);
    final float z = Math.min(v.getZF(), maximum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * @param out
   *          The output vector
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull VectorM3F clampMaximumByVector(
    final @Nonnull VectorReadable3F v,
    final @Nonnull VectorReadable3F maximum,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.min(v.getXF(), maximum.getXF());
    final float y = Math.min(v.getYF(), maximum.getYF());
    final float z = Math.min(v.getZF(), maximum.getZF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>maximum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The vector containing the maximum acceptable values
   * 
   * @return <code>(min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))</code>
   */

  public static @Nonnull VectorM3F clampMaximumByVectorInPlace(
    final @Nonnull VectorM3F v,
    final @Nonnull VectorReadable3F maximum)
  {
    return VectorM3F.clampMaximumByVector(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[-Infinity .. maximum]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param maximum
   *          The maximum allowed value
   * 
   * @return A vector with both elements equal to at most <code>maximum</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM3F clampMaximumInPlace(
    final @Nonnull VectorM3F v,
    final float maximum)
  {
    return VectorM3F.clampMaximum(v, maximum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>
   */

  public static @Nonnull VectorM3F clampMinimum(
    final @Nonnull VectorReadable3F v,
    final float minimum,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.max(v.getXF(), minimum);
    final float y = Math.max(v.getYF(), minimum);
    final float z = Math.max(v.getZF(), minimum);
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   */

  public static @Nonnull VectorM3F clampMinimumByVector(
    final @Nonnull VectorReadable3F v,
    final @Nonnull VectorReadable3F minimum,
    final @Nonnull VectorM3F out)
  {
    final float x = Math.max(v.getXF(), minimum.getXF());
    final float y = Math.max(v.getYF(), minimum.getYF());
    final float z = Math.max(v.getZF(), minimum.getZF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the inclusive range
   * given by the corresponding elements in <code>minimum</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The vector containing the minimum acceptable values
   * 
   * @return <code>(max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))</code>
   *         , in <code>v</code>
   */

  public static @Nonnull VectorM3F clampMinimumByVectorInPlace(
    final @Nonnull VectorM3F v,
    final @Nonnull VectorReadable3F minimum)
  {
    return VectorM3F.clampMinimumByVector(v, minimum, v);
  }

  /**
   * Clamp the elements of the vector <code>v</code> to the range
   * <code>[minimum .. Infinity]</code> inclusive, saving the result to
   * <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param minimum
   *          The minimum allowed value
   * 
   * @return A vector with both elements equal to at least
   *         <code>minimum</code>, in <code>v</code>.
   */

  public static @Nonnull VectorM3F clampMinimumInPlace(
    final @Nonnull VectorM3F v,
    final float minimum)
  {
    return VectorM3F.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector <code>input</code> to the vector
   * <code>output</code>.
   * 
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   * 
   * @return output
   */

  public static @Nonnull VectorM3F copy(
    final @Nonnull VectorReadable3F input,
    final @Nonnull VectorM3F output)
  {
    output.x = input.getXF();
    output.y = input.getYF();
    output.z = input.getZF();
    return output;
  }

  /**
   * Return a vector perpendicular to both <code>v0</code> and <code>v1</code>
   * , saving the result in <code>out</code>.
   * 
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param out
   *          The output vector.
   * @return out
   */

  public static @Nonnull VectorM3F crossProduct(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1,
    final @Nonnull VectorM3F out)
  {
    final float x = (v0.getYF() * v1.getZF()) - (v0.getZF() * v1.getYF());
    final float y = (v0.getZF() * v1.getXF()) - (v0.getXF() * v1.getZF());
    final float z = (v0.getXF() * v1.getYF()) - (v0.getYF() * v1.getXF());
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
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
   * @return The distance between the two vectors.
   */

  public static double distance(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1)
  {
    final @Nonnull VectorM3F vr = new VectorM3F();
    return VectorM3F.magnitude(VectorM3F.subtract(v0, v1, vr));
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
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1)
  {
    final double x = v0.getXF() * v1.getXF();
    final double y = v0.getYF() * v1.getYF();
    final double z = v0.getZF() * v1.getZF();
    return x + y + z;
  }

  /**
   * Linearly interpolate between <code>v0</code> and <code>v1</code> by the
   * amount <code>alpha</code>, saving the result to <code>r</code>.
   * 
   * The <code>alpha</code> parameter controls the degree of interpolation,
   * such that:
   * 
   * <ul>
   * <li><code>interpolateLinear(v0, v1, 0.0, r) -> r = v0</code></li>
   * <li><code>interpolateLinear(v0, v1, 1.0, r) -> r = v1</code></li>
   * </ul>
   * 
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @param alpha
   *          The interpolation value, between <code>0.0</code> and
   *          <code>1.0</code>.
   * @param r
   *          The result vector.
   * 
   * @return <code>r</code>
   */

  public static @Nonnull VectorM3F interpolateLinear(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1,
    final double alpha,
    final @Nonnull VectorM3F r)
  {
    final @Nonnull VectorM3F w0 = new VectorM3F();
    final @Nonnull VectorM3F w1 = new VectorM3F();

    VectorM3F.scale(v0, 1.0f - alpha, w0);
    VectorM3F.scale(v1, alpha, w1);

    return VectorM3F.add(w0, w1, r);
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

  public static float magnitude(
    final @Nonnull VectorReadable3F v)
  {
    return (float) Math.sqrt(VectorM3F.magnitudeSquared(v));
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
    final @Nonnull VectorReadable3F v)
  {
    return VectorM3F.dotProduct(v, v);
  }

  /**
   * Returns a vector with the same orientation as <code>v</code> but with
   * magnitude equal to <code>1.0</code> in <code>out</code>. The function
   * returns the zero vector iff the input is the zero vector.
   * 
   * @param v
   *          The input vector
   * @param out
   *          The output vector
   * 
   * @return out
   */

  public static @Nonnull VectorM3F normalize(
    final @Nonnull VectorReadable3F v,
    final @Nonnull VectorM3F out)
  {
    final double m = VectorM3F.magnitudeSquared(v);
    if (m > 0.0) {
      final double reciprocal = 1.0 / Math.sqrt(m);
      return VectorM3F.scale(v, reciprocal, out);
    }
    out.x = v.getXF();
    out.y = v.getYF();
    out.z = v.getZF();
    return out;
  }

  /**
   * Returns a vector with the same orientation as <code>v</code> but with
   * magnitude equal to <code>1.0</code> in <code>v</code>. The function
   * returns the zero vector iff the input is the zero vector.
   * 
   * @param v
   *          The input vector
   * 
   * @return v
   */

  public static @Nonnull VectorM3F normalizeInPlace(
    final @Nonnull VectorM3F v)
  {
    return VectorM3F.normalize(v, v);
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
   * @since 5.0.0
   */

  public static @Nonnull Pair<VectorM3F, VectorM3F> orthoNormalize(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1)
  {
    final VectorM3F v0n = new VectorM3F();
    final VectorM3F vr = new VectorM3F();
    final VectorM3F vp = new VectorM3F();

    VectorM3F.normalize(v0, v0n);
    VectorM3F.scale(v0n, VectorM3F.dotProduct(v1, v0n), vp);
    VectorM3F.normalizeInPlace(VectorM3F.subtract(v1, vp, vr));
    return new Pair<VectorM3F, VectorM3F>(v0n, vr);
  }

  /**
   * Orthonormalize and the vectors <code>v0</code> and <code>v1</code>,
   * modifying them in-place.
   * 
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Gram-Schmidt_process">Gram-Schmidt
   *      process</a>
   * 
   * @since 5.0.0
   */

  public static void orthoNormalizeInPlace(
    final @Nonnull VectorM3F v0,
    final @Nonnull VectorM3F v1)
  {
    final VectorM3F projection = new VectorM3F();

    VectorM3F.normalizeInPlace(v0);
    VectorM3F.scale(v0, VectorM3F.dotProduct(v1, v0), projection);
    VectorM3F.subtractInPlace(v1, projection);
    VectorM3F.normalizeInPlace(v1);
  }

  /**
   * Calculate the projection of the vector <code>p</code> onto the vector
   * <code>q</code>, saving the result in <code>r</code>.
   * 
   * @return <code>((dotProduct p q) / magnitudeSquared q) * q</code>
   */

  public static @Nonnull VectorM3F projection(
    final @Nonnull VectorReadable3F p,
    final @Nonnull VectorReadable3F q,
    final @Nonnull VectorM3F r)
  {
    final double dot = VectorM3F.dotProduct(p, q);
    final double qms = VectorM3F.magnitudeSquared(q);
    final double s = dot / qms;

    return VectorM3F.scale(p, s, r);
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>out</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * @param out
   *          The output vector
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static @Nonnull VectorM3F scale(
    final @Nonnull VectorReadable3F v,
    final double r,
    final @Nonnull VectorM3F out)
  {
    final double x = v.getXF() * r;
    final double y = v.getYF() * r;
    final double z = v.getZF() * r;
    out.x = (float) x;
    out.y = (float) y;
    out.z = (float) z;
    return out;
  }

  /**
   * Scale the vector <code>v</code> by the scalar <code>r</code>, saving the
   * result to <code>v</code>.
   * 
   * @param v
   *          The input vector
   * @param r
   *          The scaling value
   * 
   * @return <code>(v.x * r, v.y * r, v.z * r)</code>
   */

  public static @Nonnull VectorM3F scaleInPlace(
    final @Nonnull VectorM3F v,
    final double r)
  {
    return VectorM3F.scale(v, r, v);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>out</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * @param out
   *          The output vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull VectorM3F subtract(
    final @Nonnull VectorReadable3F v0,
    final @Nonnull VectorReadable3F v1,
    final @Nonnull VectorM3F out)
  {
    final float x = v0.getXF() - v1.getXF();
    final float y = v0.getYF() - v1.getYF();
    final float z = v0.getZF() - v1.getZF();
    out.x = x;
    out.y = y;
    out.z = z;
    return out;
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>,
   * saving the result to <code>v0</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static @Nonnull VectorM3F subtractInPlace(
    final @Nonnull VectorM3F v0,
    final @Nonnull VectorReadable3F v1)
  {
    return VectorM3F.subtract(v0, v1, v0);
  }

  public float x = 0.0f;
  public float y = 0.0f;

  public float z = 0.0f;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0.0, 0.0, 0.0]</code>.
   */

  public VectorM3F()
  {

  }

  /**
   * Construct a vector initialized with the given values.
   */

  public VectorM3F(
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

  public VectorM3F(
    final @Nonnull VectorReadable3F v)
  {
    this.x = v.getXF();
    this.y = v.getYF();
    this.z = v.getZF();
  }

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
    final @Nonnull VectorM3F other = (VectorM3F) obj;
    if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
      return false;
    }
    if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
      return false;
    }
    if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
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

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.x);
    result = (prime * result) + Float.floatToIntBits(this.y);
    result = (prime * result) + Float.floatToIntBits(this.z);
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM3F ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
