/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jtensors.core.parameterized.vectors.PVector2L;
import com.io7m.jtensors.core.parameterized.vectors.PVector3L;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PVector3LBuilderTest
{
  @Test
  public void testBuilder2L()
  {
    final PVector3L<Object> v =
      PVector3L.builder()
        .from(PVector2L.of(0L, 1L))
        .setZ(2L)
        .build();
    Assertions.assertEquals(0L, v.x());
    Assertions.assertEquals(1L, v.y());
    Assertions.assertEquals(2L, v.z());
  }

  @Test
  public void testBuilder2LMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector3L.builder()
        .from(PVector2L.of(0L, 1L))
        .build();
    });
  }

  @Test
  public void testBuilder3L()
  {
    final PVector3L<Object> v =
      PVector3L.builder()
        .from(PVector3L.of(0L, 1L, 2L))
        .build();
    Assertions.assertEquals(0L, v.x());
    Assertions.assertEquals(1L, v.y());
    Assertions.assertEquals(2L, v.z());
  }
}
