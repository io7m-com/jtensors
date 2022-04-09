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

import com.io7m.jtensors.core.parameterized.vectors.PVector2I;
import com.io7m.jtensors.core.parameterized.vectors.PVector3I;
import com.io7m.jtensors.core.parameterized.vectors.PVector4I;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PVector4IBuilderTest
{
  @Test
  public void testBuilder2I()
  {
    final PVector4I<Object> v =
      PVector4I.builder()
        .from(PVector2I.of(0, 1))
        .setZ(2)
        .setW(3)
        .build();
    Assertions.assertEquals(0L, v.x());
    Assertions.assertEquals(1L, v.y());
    Assertions.assertEquals(2L, v.z());
    Assertions.assertEquals(3L, v.w());
  }

  @Test
  public void testBuilder2IMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector4I.builder()
        .from(PVector2I.of(0, 1))
        .build();
    });
  }

  @Test
  public void testBuilder3I()
  {
    final PVector4I<Object> v =
      PVector4I.builder()
        .from(PVector3I.of(0, 1, 2))
        .setW(3)
        .build();
    Assertions.assertEquals(0L, v.x());
    Assertions.assertEquals(1L, v.y());
    Assertions.assertEquals(2L, v.z());
    Assertions.assertEquals(3L, v.w());
  }

  @Test
  public void testBuilder3IMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector4I.builder()
        .from(PVector3I.of(0, 1, 2))
        .build();
    });
  }

  @Test
  public void testBuilder4I()
  {
    final PVector4I<Object> v =
      PVector4I.builder()
        .from(PVector4I.of(0, 1, 2, 3))
        .build();
    Assertions.assertEquals(0L, v.x());
    Assertions.assertEquals(1L, v.y());
    Assertions.assertEquals(2L, v.z());
    Assertions.assertEquals(3L, v.w());
  }
}
