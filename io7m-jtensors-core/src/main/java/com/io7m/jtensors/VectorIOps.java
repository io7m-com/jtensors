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

import com.io7m.jintegers.CheckedMath;
import com.io7m.junreachable.UnreachableCodeException;

final class VectorIOps
{
  private VectorIOps()
  {
    throw new UnreachableCodeException();
  }

  static int one()
  {
    return 1;
  }

  static int absolute(
    final int value)
  {
    return CheckedMath.absolute(value);
  }

  static int add(
    final int value0,
    final int value1)
  {
    return CheckedMath.add(value0, value1);
  }

  static long addLarge(
    final long value0,
    final long value1)
  {
    return CheckedMath.add(value0, value1);
  }

  static int subtract(
    final int value0,
    final int value1)
  {
    return CheckedMath.subtract(value0, value1);
  }

  static int multiply(
    final int value0,
    final int value1)
  {
    return CheckedMath.multiply(value0, value1);
  }

  static int multiplyReal(
    final int value0,
    final double value1)
  {
    return CheckedMath.multiply(value0, value1);
  }

  static int minimum(
    final int value0,
    final int value1)
  {
    return Math.min(value0, value1);
  }

  static int maximum(
    final int value0,
    final int value1)
  {
    return Math.max(value0, value1);
  }

  static boolean scalarEquals(
    final int value0,
    final int value1)
  {
    return value0 == value1;
  }

  static int hash4(
    final int w,
    final int x,
    final int y,
    final int z)
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
    final int x,
    final int y,
    final int z)
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + x;
    result = (prime * result) + y;
    result = (prime * result) + z;
    return (int) result;
  }

  static int hash2(
    final int x,
    final int y)
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + x;
    result = (prime * result) + y;
    return (int) result;
  }

  static int squareRoot(
    final long i)
  {
    return Cast.castToInt(Math.sqrt((double) i));
  }

  static int castLargeToDistance(
    final long value)
  {
    return (int) value;
  }

  static int castLargeToDot(
    final long value)
  {
    return (int) value;
  }
}
