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

package com.io7m.jtensors.tests.core;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestLOps
{
  private static final Logger LOG;
  private static final AlmostEqualDouble.ContextRelative ALMOST_EQUAL_LARGE;
  private static final AlmostEqualDouble.ContextRelative ALMOST_EQUAL_VAGUE;

  static {
    LOG = LoggerFactory.getLogger(TestLOps.class);

    ALMOST_EQUAL_LARGE = new AlmostEqualDouble.ContextRelative();
    ALMOST_EQUAL_LARGE.setMaxAbsoluteDifference(0.000001);
    ALMOST_EQUAL_LARGE.setMaxRelativeDifference(0.000001);

    ALMOST_EQUAL_VAGUE = new AlmostEqualDouble.ContextRelative();
    ALMOST_EQUAL_VAGUE.setMaxAbsoluteDifference(0.000001);
    ALMOST_EQUAL_VAGUE.setMaxRelativeDifference(0.000001);
  }

  private TestLOps()
  {
    throw new UnreachableCodeException();
  }

  public static void checkEquals(
    final long x,
    final long y)
  {
    Assert.assertEquals(x, y);
  }

  public static void checkAlmostEquals(
    final long x,
    final long y)
  {
    final double dx = (double) x;
    final double dy = (double) y;
    final double diff = Math.abs(dx - dy);
    final double thresh = Math.abs(dx + dy) * 0.0029;

    if (diff > thresh) {
      throw new AssertionError(
        String.format(
          "Expected: <%f> Received: <%f>",
          Double.valueOf((double) x),
          Double.valueOf((double) y)));
    }
  }

  public static void checkAlmostEquals(
    final double x,
    final double y)
  {
    if (!AlmostEqualDouble.almostEqual(ALMOST_EQUAL_LARGE, x, y)) {
      throw new AssertionError(
        String.format(
          "Expected: <%f> Received: <%f>",
          Double.valueOf(x),
          Double.valueOf(y)));
    }
  }

  public static void checkAlmostEqualsVague(
    final double x,
    final double y)
  {
    if (!AlmostEqualDouble.almostEqual(ALMOST_EQUAL_VAGUE, x, y)) {
      throw new AssertionError(
        String.format(
          "Expected: <%f> Received: <%f>",
          Double.valueOf(x),
          Double.valueOf(y)));
    }
  }

  public static long constant(
    final String text)
  {
    return Long.parseLong(text);
  }

  public static double realConstant(
    final String text)
  {
    return Double.parseDouble(text);
  }

  public static long absolute(
    final long x)
  {
    return Math.abs(x);
  }

  public static long add(
    final long x,
    final long y)
  {
    return Math.addExact(x, y);
  }

  public static long multiply(
    final long x,
    final long y)
  {
    return Math.multiplyExact(x, y);
  }

  public static long multiplyReal(
    final long x,
    final double y)
  {
    return (long) ((double) x * y);
  }

  public static long subtract(
    final long x,
    final long y)
  {
    return Math.subtractExact(x, y);
  }

  public static int compareReal(
    final double x,
    final double y)
  {
    return Double.compare(x, y);
  }
}
