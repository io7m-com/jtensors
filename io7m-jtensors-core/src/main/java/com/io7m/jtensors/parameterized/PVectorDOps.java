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

final class PVectorDOps
{
  private PVectorDOps()
  {
    throw new UnreachableCodeException();
  }

  static double one()
  {
    return 1.0;
  }

  static double absolute(
    final double value)
  {
    return Math.abs(value);
  }

  static double add(
    final double value0,
    final double value1)
  {
    return value0 + value1;
  }

  static double addLarge(
    final double value0,
    final double value1)
  {
    return value0 + value1;
  }

  static double subtract(
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

  static double minimum(
    final double value0,
    final double value1)
  {
    return Math.min(value0, value1);
  }

  static double maximum(
    final double value0,
    final double value1)
  {
    return Math.max(value0, value1);
  }

  static boolean scalarEquals(
    final double value0,
    final double value1)
  {
    return Double.doubleToLongBits(value0) == Double.doubleToLongBits(value1);
  }

  static int hash4(
    final double w,
    final double x,
    final double y,
    final double z)
  {
    long temp = Double.doubleToLongBits(w);
    int result = 1;
    final int prime = 31;
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(x);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  static int hash3(
    final double x,
    final double y,
    final double z)
  {
    long temp = Double.doubleToLongBits(x);
    int result = 1;
    final int prime = 31;
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(z);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  static int hash2(
    final double x,
    final double y)
  {
    long temp = Double.doubleToLongBits(x);
    int result = 1;
    final int prime = 31;
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
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
