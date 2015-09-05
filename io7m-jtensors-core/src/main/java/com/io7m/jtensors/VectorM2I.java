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

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>
 * A two-dimensional mutable vector type with integer elements.
 * </p>
 *
 * <p>
 * Values of this type cannot be accessed safely from multiple threads without
 * explicit synchronization.
 * </p>
 */

public final class VectorM2I implements VectorReadable2IType, VectorWritable2IType
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
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I absolute(
    final VectorReadable2IType v,
    final VectorM2I out)
    throws ArithmeticException
  {
    final int x = CheckedMath.absolute(v.getXI());
    final int y = CheckedMath.absolute(v.getYI());
    out.x = x;
    out.y = y;
    return out;
  }

  /**
   * Calculate the absolute values of the elements in vector {@code v},
   * saving the result to {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return {@code (abs v.x, abs v.y, abs v.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I absoluteInPlace(
    final VectorM2I v)
    throws ArithmeticException
  {
    return VectorM2I.absolute(v, v);
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I add(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1,
    final VectorM2I out)
    throws ArithmeticException
  {
    final int x = CheckedMath.add(v0.getXI(), v1.getXI());
    final int y = CheckedMath.add(v0.getYI(), v1.getYI());
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
   * @return {@code (v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I addInPlace(
    final VectorM2I v0,
    final VectorReadable2IType v1)
    throws ArithmeticException
  {
    return VectorM2I.add(v0, v1, v0);
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I addScaled(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1,
    final double r,
    final VectorM2I out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v1.getXI(), r);
    final int my = CheckedMath.multiply(v1.getYI(), r);
    final int x = CheckedMath.add(v0.getXI(), mx);
    final int y = CheckedMath.add(v0.getYI(), my);
    out.x = x;
    out.y = y;
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
   * @return {@code (v0.x + (v1.x * r), v0.y + (v1.y * r), v0.z + (v1.z * r))}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I addScaledInPlace(
    final VectorM2I v0,
    final VectorReadable2IType v1,
    final double r)
    throws ArithmeticException
  {
    return VectorM2I.addScaled(v0, v1, r, v0);
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
   */

  public static double angle(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1)
  {
    final double m0 = VectorM2I.magnitude(v0);
    final double m1 = VectorM2I.magnitude(v1);
    return Math.acos(VectorM2I.dotProduct(v0, v1) / (m0 * m1));
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

  public static VectorM2I clamp(
    final VectorReadable2IType v,
    final int minimum,
    final int maximum,
    final VectorM2I out)
  {
    final int x = Math.min(Math.max(v.getXI(), minimum), maximum);
    final int y = Math.min(Math.max(v.getYI(), minimum), maximum);
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static VectorM2I clampByVector(
    final VectorReadable2IType v,
    final VectorReadable2IType minimum,
    final VectorReadable2IType maximum,
    final VectorM2I out)
  {
    final int x =
      Math.min(Math.max(v.getXI(), minimum.getXI()), maximum.getXI());
    final int y =
      Math.min(Math.max(v.getYI(), minimum.getYI()), maximum.getYI());
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
   * @return {@code (min(max(v.x, minimum.x), maximum.x), min(max(v.y, minimum.y), maximum.y), min(max(v.z, minimum.z), maximum.z))}
   */

  public static VectorM2I clampByVectorInPlace(
    final VectorM2I v,
    final VectorReadable2IType minimum,
    final VectorReadable2IType maximum)
  {
    return VectorM2I.clampByVector(v, minimum, maximum, v);
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

  public static VectorM2I clampInPlace(
    final VectorM2I v,
    final int minimum,
    final int maximum)
  {
    return VectorM2I.clamp(v, minimum, maximum, v);
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

  public static VectorM2I clampMaximum(
    final VectorReadable2IType v,
    final int maximum,
    final VectorM2I out)
  {
    final int x = Math.min(v.getXI(), maximum);
    final int y = Math.min(v.getYI(), maximum);
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))}
   */

  public static VectorM2I clampMaximumByVector(
    final VectorReadable2IType v,
    final VectorReadable2IType maximum,
    final VectorM2I out)
  {
    final int x = Math.min(v.getXI(), maximum.getXI());
    final int y = Math.min(v.getYI(), maximum.getYI());
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
   * @return {@code (min(v.x, maximum.x), min(v.y, maximum.y), min(v.z, maximum.z))}
   */

  public static VectorM2I clampMaximumByVectorInPlace(
    final VectorM2I v,
    final VectorReadable2IType maximum)
  {
    return VectorM2I.clampMaximumByVector(v, maximum, v);
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

  public static VectorM2I clampMaximumInPlace(
    final VectorM2I v,
    final int maximum)
  {
    return VectorM2I.clampMaximum(v, maximum, v);
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

  public static VectorM2I clampMinimum(
    final VectorReadable2IType v,
    final int minimum,
    final VectorM2I out)
  {
    final int x = Math.max(v.getXI(), minimum);
    final int y = Math.max(v.getYI(), minimum);
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   */

  public static VectorM2I clampMinimumByVector(
    final VectorReadable2IType v,
    final VectorReadable2IType minimum,
    final VectorM2I out)
  {
    final int x = Math.max(v.getXI(), minimum.getXI());
    final int y = Math.max(v.getYI(), minimum.getYI());
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
   * @return {@code (max(v.x, minimum.x), max(v.y, minimum.y), max(v.z, minimum.z))}
   *         , in {@code v}
   */

  public static VectorM2I clampMinimumByVectorInPlace(
    final VectorM2I v,
    final VectorReadable2IType minimum)
  {
    return VectorM2I.clampMinimumByVector(v, minimum, v);
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

  public static VectorM2I clampMinimumInPlace(
    final VectorM2I v,
    final int minimum)
  {
    return VectorM2I.clampMinimum(v, minimum, v);
  }

  /**
   * Copy all elements of the vector {@code input} to the vector
   * {@code output}.
   *
   * @param <T>
   *          The specific type of vector
   * @param input
   *          The input vector
   * @param output
   *          The output vector
   *
   * @return output
   */

  public static <T extends VectorWritable2IType> T copy(
    final VectorReadable2IType input,
    final T output)
  {
    output.set2I(input.getXI(), input.getYI());
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int distance(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1)
    throws ArithmeticException
  {
    final VectorM2I vr = new VectorM2I();
    return VectorM2I.magnitude(VectorM2I.subtract(v0, v1, vr));
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int dotProduct(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v0.getXI(), v1.getXI());
    final int my = CheckedMath.multiply(v0.getYI(), v1.getYI());
    return CheckedMath.add(mx, my);
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer
   *           overflow.
   */

  public static VectorM2I interpolateLinear(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1,
    final double alpha,
    final VectorM2I r)
    throws ArithmeticException
  {
    final VectorM2I w0 = new VectorM2I();
    final VectorM2I w1 = new VectorM2I();

    VectorM2I.scale(v0, 1.0 - alpha, w0);
    VectorM2I.scale(v1, alpha, w1);

    return VectorM2I.add(w0, w1, r);
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
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitude(
    final VectorReadable2IType v)
    throws ArithmeticException
  {
    return Cast.castToInt(Math.sqrt(VectorM2I.magnitudeSquared(v)));
  }

  /**
   * Calculate the squared magnitude of the vector {@code v}.
   *
   * @param v
   *          The input vector
   *
   * @return The squared magnitude of the input vector
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static int magnitudeSquared(
    final VectorReadable2IType v)
    throws ArithmeticException
  {
    return VectorM2I.dotProduct(v, v);
  }

  /**
   * Calculate the projection of the vector {@code p} onto the vector
   * {@code q}, saving the result in {@code r}.
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   *
   * @return {@code ((dotProduct p q) / magnitudeSquared q) * q}
   * @param p
   *          The left vector
   * @param q
   *          The right vector
   * @param r
   *          The output vector
   */

  public static VectorM2I projection(
    final VectorReadable2IType p,
    final VectorReadable2IType q,
    final VectorM2I r)
    throws ArithmeticException
  {
    final int dot = VectorM2I.dotProduct(p, q);
    final int qms = VectorM2I.magnitudeSquared(q);
    final int s = dot / qms;

    return VectorM2I.scale(p, s, r);
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I scale(
    final VectorReadable2IType v,
    final double r,
    final VectorM2I out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.multiply(v.getXI(), r);
    final int my = CheckedMath.multiply(v.getYI(), r);
    out.x = mx;
    out.y = my;
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
   * @return {@code (v.x * r, v.y * r, v.z * r)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I scaleInPlace(
    final VectorM2I v,
    final int r)
    throws ArithmeticException
  {
    return VectorM2I.scale(v, r, v);
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I subtract(
    final VectorReadable2IType v0,
    final VectorReadable2IType v1,
    final VectorM2I out)
    throws ArithmeticException
  {
    final int mx = CheckedMath.subtract(v0.getXI(), v1.getXI());
    final int my = CheckedMath.subtract(v0.getYI(), v1.getYI());
    out.x = mx;
    out.y = my;
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
   * @return {@code (v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)}
   *
   * @throws ArithmeticException
   *           Iff an internal arithmetic operation causes an integer overflow
   */

  public static VectorM2I subtractInPlace(
    final VectorM2I v0,
    final VectorReadable2IType v1)
    throws ArithmeticException
  {
    return VectorM2I.subtract(v0, v1, v0);
  }

  private int x;
  private int y;

  /**
   * Default constructor, initializing the vector with values
   * {@code [0, 0, 0]}.
   */

  public VectorM2I()
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

  public VectorM2I(
    final int in_x,
    final int in_y)
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

  public VectorM2I(
    final VectorReadable2IType in_v)
  {
    this.x = in_v.getXI();
    this.y = in_v.getYI();
  }

  @Override public void copyFrom2I(
    final VectorReadable2IType in_v)
  {
    VectorM2I.copy(in_v, this);
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
    final VectorM2I other = (VectorM2I) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    return result;
  }

  @Override public void set2I(
    final int in_x,
    final int in_y)
  {
    this.x = in_x;
    this.y = in_y;
  }

  @Override public void setXI(
    final int in_x)
  {
    this.x = in_x;
  }

  @Override public void setYI(
    final int in_y)
  {
    this.y = in_y;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorM2I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }
}
