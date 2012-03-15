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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

public class ApproximatelyEqualDoubleTest
{
  @Test public void testMaxMax()
  {
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      Double.MAX_VALUE,
      Double.MAX_VALUE));
  }

  @Test public void testMaxMin()
  {
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(
      Double.MAX_VALUE,
      Double.MIN_VALUE));
  }

  @Test public void testMinMax()
  {
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(
      Double.MIN_VALUE,
      Double.MAX_VALUE));
  }

  @Test public void testMinMin()
  {
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      Double.MIN_VALUE,
      Double.MIN_VALUE));
  }

  @Test public void testOne()
  {
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(1.0, 1.0));
  }

  @Test public void testOneZero()
  {
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(1.0, 0.0));
  }

  @Test public void testZero()
  {
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(0.0, 0.0));
  }

  @Test public void testZeroEpsilon()
  {
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(
      0.0,
      ApproximatelyEqualDouble.DOUBLE_ERROR));
  }

  @Test public void testZeroOne()
  {
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(0.0, 1.0));
  }
}
