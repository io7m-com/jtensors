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

import com.io7m.junreachable.UnreachableCodeException;

final class PVectorFOps
{
  private PVectorFOps()
  {
    throw new UnreachableCodeException();
  }

  static float one()
  {
    return 1.0f;
  }

  static float absolute(
    final float value)
  {
    return Math.abs(value);
  }

  static float add(
    final float value0,
    final float value1)
  {
    return value0 + value1;
  }

  static double addLarge(
    final double value0,
    final double value1)
  {
    return value0 + value1;
  }

  static float subtract(
    final float value0,
    final float value1)
  {
    return value0 - value1;
  }

  static float multiply(
    final float value0,
    final float value1)
  {
    return value0 * value1;
  }

  static float multiplyReal(
    final float value0,
    final double value1)
  {
    return (float) (value0 * value1);
  }

  static float minimum(
    final double value0,
    final double value1)
  {
    return (float) Math.min(value0, value1);
  }

  static float maximum(
    final double value0,
    final double value1)
  {
    return (float) Math.max(value0, value1);
  }

  static boolean scalarEquals(
    final float value0,
    final float value1)
  {
    return Float.floatToIntBits(value0) == Float.floatToIntBits(value1);
  }

  static int hash4(
    final float w,
    final float x,
    final float y,
    final float z)
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(w);
    result = (prime * result) + Float.floatToIntBits(x);
    result = (prime * result) + Float.floatToIntBits(y);
    result = (prime * result) + Float.floatToIntBits(z);
    return result;
  }

  static int hash3(
    final float x,
    final float y,
    final float z)
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(x);
    result = (prime * result) + Float.floatToIntBits(y);
    result = (prime * result) + Float.floatToIntBits(z);
    return result;
  }

  static int hash2(
    final float x,
    final float y)
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(x);
    result = (prime * result) + Float.floatToIntBits(y);
    return result;
  }

  static double squareRoot(
    final double value)
  {
    return Math.sqrt(value);
  }

  static double castLargeToDistance(
    final double v)
  {
    return v;
  }

  static double castLargeToDot(
    final double v)
  {
    return v;
  }
}
