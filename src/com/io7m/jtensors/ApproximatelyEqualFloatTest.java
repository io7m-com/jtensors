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

public class ApproximatelyEqualFloatTest
{
  @Test public void min_max()
  {
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(
      Float.MIN_VALUE,
      Float.MAX_VALUE));
  }

  @Test public void min_min()
  {
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      Float.MIN_VALUE,
      Float.MIN_VALUE));
  }

  @Test public void one()
  {
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(1.0f, 1.0f));
  }

  @Test public void one_zero()
  {
    Assert
      .assertFalse(ApproximatelyEqualFloat.approximatelyEqual(1.0f, 0.0f));
  }

  @Test public void testMaxMax()
  {
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      Float.MAX_VALUE,
      Float.MAX_VALUE));
  }

  @Test public void testMaxMin()
  {
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(
      Float.MAX_VALUE,
      Float.MIN_VALUE));
  }

  @Test public void zero()
  {
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(0.0f, 0.0f));
  }

  @Test public void zero_epsilon()
  {
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(
      0.0f,
      ApproximatelyEqualFloat.FLOAT_ERROR));
  }

  @Test public void zero_one()
  {
    Assert
      .assertFalse(ApproximatelyEqualFloat.approximatelyEqual(0.0f, 1.0f));
  }
}
