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

package com.io7m.jtensors.tests.core.parameterized.vectors;

import com.io7m.jtensors.core.parameterized.vectors.PVector2I;
import com.io7m.jtensors.core.parameterized.vectors.PVector3I;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class PVector3IBuilderTest
{
  @Rule public ExpectedException expected = ExpectedException.none();

  @Test
  public void testBuilder2I()
  {
    final PVector3I<Object> v =
      PVector3I.builder()
        .from(PVector2I.of(0, 1))
        .setZ(2)
        .build();
    Assert.assertEquals(0L, (long) v.x());
    Assert.assertEquals(1L, (long) v.y());
    Assert.assertEquals(2L, (long) v.z());
  }

  @Test
  public void testBuilder2IMissing()
  {
    this.expected.expect(IllegalStateException.class);

    PVector3I.builder()
      .from(PVector2I.of(0, 1))
      .build();
    Assert.fail();
  }

  @Test
  public void testBuilder3I()
  {
    final PVector3I<Object> v =
      PVector3I.builder()
        .from(PVector3I.of(0, 1, 2))
        .build();
    Assert.assertEquals(0L, (long) v.x());
    Assert.assertEquals(1L, (long) v.y());
    Assert.assertEquals(2L, (long) v.z());
  }
}
