/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.core.parameterized.vectors;

import com.io7m.junreachable.UnreachableCodeException;

final class PVectorFOps
{
  private PVectorFOps()
  {
    throw new UnreachableCodeException();
  }

  static double absolute(
    final double value0)
  {
    return Math.abs(value0);
  }

  static double add(
    final double value0,
    final double value1)
  {
    return (value0 + value1);
  }

  static double negate(
    final double value0)
  {
    return -value0;
  }

  static double subtract(
    final double value0,
    final double value1)
  {
    return value0 - value1;
  }

  static double subtractReal(
    final double value0,
    final double value1)
  {
    return value0 - value1;
  }

  static double multiply(
    final double value0,
    final double value1)
  {
    return value0 * value1;
  }

  static double multiplyReal(
    final double value0,
    final double value1)
  {
    return value0 * value1;
  }

  static double multiplyRealReal(
    final double value0,
    final double value1)
  {
    return value0 * value1;
  }

  static double divideReal(
    final double value0,
    final double value1)
  {
    return value0 / value1;
  }

  static double divideRealReal(
    final double value0,
    final double value1)
  {
    return value0 / value1;
  }

  static double clamp(
    final double x,
    final double min,
    final double max)
  {
    return Math.min(Math.max(x, min), max);
  }

  static double zero()
  {
    return 0.0;
  }

  static double one()
  {
    return 1.0;
  }

  static int compareLarge(
    final double value0,
    final double value1)
  {
    return Double.compare(value0, value1);
  }

  static double squareRootLarge(
    final double v)
  {
    return Math.sqrt(v);
  }

  static double clampReal(
    final double value,
    final double low,
    final double high)
  {
    return Math.min(high, Math.max(value, low));
  }

  static double arcCosine(
    final double r)
  {
    return StrictMath.acos(r);
  }
}
