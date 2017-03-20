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

import com.io7m.jintegers.CheckedMath;
import com.io7m.junreachable.UnreachableCodeException;

final class PVectorLOps
{
  private PVectorLOps()
  {
    throw new UnreachableCodeException();
  }

  static long one()
  {
    return 1L;
  }

  static long absolute(
    final long value)
  {
    return CheckedMath.absolute(value);
  }

  static long add(
    final long value0,
    final long value1)
  {
    return CheckedMath.add(value0, value1);
  }

  static long addLarge(
    final long value0,
    final long value1)
  {
    return CheckedMath.add(value0, value1);
  }

  static long subtract(
    final long value0,
    final long value1)
  {
    return CheckedMath.subtract(value0, value1);
  }

  static long multiply(
    final long value0,
    final long value1)
  {
    return CheckedMath.multiply(value0, value1);
  }

  static long multiplyReal(
    final long value0,
    final double value1)
  {
    return CheckedMath.multiply(value0, value1);
  }

  static long minimum(
    final long value0,
    final long value1)
  {
    return Math.min(value0, value1);
  }

  static long maximum(
    final long value0,
    final long value1)
  {
    return Math.max(value0, value1);
  }

  static boolean scalarEquals(
    final long value0,
    final long value1)
  {
    return value0 == value1;
  }

  static int hash4(
    final long w,
    final long x,
    final long y,
    final long z)
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + w;
    result = (prime * result) + x;
    result = (prime * result) + y;
    result = (prime * result) + z;
    return (int) result;
  }

  static int hash3(
    final long x,
    final long y,
    final long z)
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + x;
    result = (prime * result) + y;
    result = (prime * result) + z;
    return (int) result;
  }

  static int hash2(
    final long x,
    final long y)
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + x;
    result = (prime * result) + y;
    return (int) result;
  }

  static long squareRoot(
    final long value)
  {
    return Cast.castToLong(Math.sqrt((double) value));
  }

  static long castLargeToDistance(
    final long value)
  {
    return value;
  }

  static long castLargeToDot(
    final long value)
  {
    return value;
  }
}
