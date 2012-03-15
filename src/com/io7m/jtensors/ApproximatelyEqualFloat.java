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

public final class ApproximatelyEqualFloat
{
  /**
   * An error epsilon value appropriate to single precision floating point
   * numbers.
   */

  public static final float FLOAT_ERROR = 0.000001f;

  /**
   * @return <code>approximatelyEqualExplicit(x, y, FLOAT_ERROR)</code>
   */

  public static boolean approximatelyEqual(
    final float x,
    final float y)
  {
    return ApproximatelyEqualFloat.approximatelyEqualExplicit(
      x,
      y,
      ApproximatelyEqualFloat.FLOAT_ERROR);
  }

  /**
   * @param e
   *          the given error epsilon value
   * @return true, iff <code>x</code> is approximately equal to <code>y</code>
   *         , within the specified degree of error <code>e</code>.
   */

  public static boolean approximatelyEqualExplicit(
    final float x,
    final float y,
    final float e)
  {
    if (x == y) {
      return true;
    }

    final float abs_x = Math.abs(x);
    final float abs_y = Math.abs(y);
    final float delta = x - y;

    if (abs_y > abs_x) {
      final float r = Math.abs(delta / y);
      return r <= e;
    }

    final float r = Math.abs(delta / x);
    return r <= e;
  }
}
