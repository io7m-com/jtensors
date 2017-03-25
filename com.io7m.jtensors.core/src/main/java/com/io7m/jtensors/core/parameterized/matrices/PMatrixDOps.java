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

package com.io7m.jtensors.core.parameterized.matrices;

import com.io7m.junreachable.UnreachableCodeException;

final class PMatrixDOps
{
  private PMatrixDOps()
  {
    throw new UnreachableCodeException();
  }

  static double add(
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

  static double divide(
    final double value0,
    final double value1)
  {
    return value0 / value1;
  }

  static int compare(
    final double value0,
    final double value1)
  {
    return Double.compare(value0, value1);
  }

  static double zero()
  {
    return 0.0;
  }

  static double one()
  {
    return 1.0;
  }

  static double determinant3x3(
    final double r0c0,
    final double r0c1,
    final double r0c2,
    final double r1c0,
    final double r1c1,
    final double r1c2,
    final double r2c0,
    final double r2c1,
    final double r2c2)
  {
    double sum = 0.0;
    sum += r0c0 * ((r1c1 * r2c2) - (r1c2 * r2c1));
    sum -= r0c1 * ((r1c0 * r2c2) - (r1c2 * r2c0));
    sum += r0c2 * ((r1c0 * r2c1) - (r1c1 * r2c0));
    return sum;
  }

  static double determinant4x4(
    final double r0c0,
    final double r0c1,
    final double r0c2,
    final double r0c3,
    final double r1c0,
    final double r1c1,
    final double r1c2,
    final double r1c3,
    final double r2c0,
    final double r2c1,
    final double r2c2,
    final double r2c3,
    final double r3c0,
    final double r3c1,
    final double r3c2,
    final double r3c3)
  {
    double sum = 0.0;

    sum += r0c0 * r1c1 * r2c2 * r3c3;
    sum -= r0c0 * r1c1 * r2c3 * r3c2;
    sum += r0c0 * r1c2 * r2c3 * r3c1;
    sum -= r0c0 * r1c2 * r2c1 * r3c3;

    sum += r0c0 * r1c3 * r2c1 * r3c2;
    sum -= r0c0 * r1c3 * r2c2 * r3c1;
    sum -= r0c1 * r1c2 * r2c3 * r3c0;
    sum += r0c1 * r1c2 * r2c0 * r3c3;

    sum -= r0c1 * r1c3 * r2c0 * r3c2;
    sum += r0c1 * r1c3 * r2c2 * r3c0;
    sum -= r0c1 * r1c0 * r2c2 * r3c3;
    sum += r0c1 * r1c0 * r2c3 * r3c2;

    sum += r0c2 * r1c3 * r2c0 * r3c1;
    sum -= r0c2 * r1c3 * r2c1 * r3c0;
    sum += r0c2 * r1c0 * r2c1 * r3c3;
    sum -= r0c2 * r1c0 * r2c3 * r3c1;

    sum += r0c2 * r1c1 * r2c3 * r3c0;
    sum -= r0c2 * r1c1 * r2c0 * r3c3;
    sum -= r0c3 * r1c0 * r2c1 * r3c2;
    sum += r0c3 * r1c0 * r2c2 * r3c1;

    sum -= r0c3 * r1c1 * r2c2 * r3c0;
    sum += r0c3 * r1c1 * r2c0 * r3c2;
    sum -= r0c3 * r1c2 * r2c0 * r3c1;
    sum += r0c3 * r1c2 * r2c1 * r3c0;

    return sum;
  }
}
