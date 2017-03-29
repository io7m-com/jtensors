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

import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;

public final class TestIOps
{
  private TestIOps()
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
    Assert.assertEquals(x, y);
  }

  public static void checkAlmostEquals(
    final double x,
    final double y)
  {
    Assert.assertEquals(x, y, 1.0e-10);
  }

  public static void checkAlmostEqualsVague(
    final double x,
    final double y)
  {
    Assert.assertEquals(x, y, 1.0e-10);
  }

  public static int constant(
    final String text)
  {
    return Integer.parseInt(text);
  }

  public static double realConstant(
    final String text)
  {
    return Double.parseDouble(text);
  }

  public static int absolute(
    final int x)
  {
    return Math.abs(x);
  }

  public static long add(
    final long x,
    final long y)
  {
    return Math.addExact(x, y);
  }

  public static int multiply(
    final int x,
    final int y)
  {
    return Math.multiplyExact(x, y);
  }

  public static int multiplyReal(
    final int x,
    final double y)
  {
    return (int) ((double) x * y);
  }

  public static int subtract(
    final int x,
    final int y)
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
