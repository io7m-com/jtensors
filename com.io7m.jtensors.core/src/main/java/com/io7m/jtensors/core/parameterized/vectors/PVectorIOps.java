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

final class PVectorIOps
{
  private PVectorIOps()
  {
    throw new UnreachableCodeException();
  }

  static int absolute(
    final int value0)
  {
    return Math.abs(value0);
  }

  static int add(
    final int value0,
    final int value1)
  {
    return Math.addExact(value0, value1);
  }

  static int negate(
    final int value0)
  {
    return -value0;
  }

  static int subtract(
    final int value0,
    final int value1)
  {
    return Math.subtractExact(value0, value1);
  }

  static double subtractReal(
    final double value0,
    final double value1)
  {
    return value0 - value1;
  }

  static int multiply(
    final int value0,
    final int value1)
  {
    return value0 * value1;
  }

  static int multiplyReal(
    final int value0,
    final double value1)
  {
    return (int) ((double) value0 * value1);
  }

  static double multiplyRealReal(
    final double value0,
    final double value1)
  {
    return value0 * value1;
  }

  static int clamp(
    final int x,
    final int min,
    final int max)
  {
    return Math.min(Math.max(x, min), max);
  }

  static int zero()
  {
    return 0;
  }

  static int one()
  {
    return 1;
  }

  static double squareRootLarge(
    final long v)
  {
    return Math.sqrt((double) v);
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

  static double divideRealReal(
    final double value0,
    final double value1)
  {
    return value0 / value1;
  }
}
