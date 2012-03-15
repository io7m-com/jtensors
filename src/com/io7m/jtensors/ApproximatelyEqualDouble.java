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

/**
 * Floating point values cannot be directly compared for equality. This class
 * implements an approximate equality function for comparing values within a
 * given degree of error.
 */

package com.io7m.jtensors;

public final class ApproximatelyEqualDouble
{
  /**
   * An error epsilon value appropriate to double precision floating point
   * numbers.
   */

  public static final double DOUBLE_ERROR = 0.00000000000001;

  /**
   * @return <code>approximatelyEqualExplicit(x, y, DOUBLE_ERROR)</code>
   */

  public static boolean approximatelyEqual(
    final double x,
    final double y)
  {
    return ApproximatelyEqualDouble.approximatelyEqualExplicit(
      x,
      y,
      ApproximatelyEqualDouble.DOUBLE_ERROR);
  }

  /**
   * @param e
   *          the given error epsilon value
   * @return true, iff <code>x</code> is approximately equal to <code>y</code>
   *         , within the specified degree of error <code>e</code>.
   */

  public static boolean approximatelyEqualExplicit(
    final double x,
    final double y,
    final double e)
  {
    if (x == y) {
      return true;
    }

    final double abs_x = Math.abs(x);
    final double abs_y = Math.abs(y);
    final double delta = x - y;

    if (abs_y > abs_x) {
      final double r = Math.abs(delta / y);
      return r <= e;
    }

    final double r = Math.abs(delta / x);
    return r <= e;
  }
}
