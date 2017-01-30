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

package com.io7m.jtensors.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

public abstract class VectorDContract
{
  protected abstract double delta();

  protected abstract double randomLargeNegative();

  protected abstract double randomLargePositive();

  protected abstract Logger logger();

  @Test
  public final void testConstants()
  {
    Assert.assertTrue(this.delta() >= 0.0);
    Assert.assertTrue(this.delta() <= 1.0);

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargeNegative() <= 0.0);
    }

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargePositive() >= 0.0);
    }
  }
}
