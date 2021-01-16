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

import com.io7m.jtensors.core.parameterized.vectors.PVector2F;
import com.io7m.jtensors.core.parameterized.vectors.PVector3F;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PVector3FBuilderTest
{
  @Test
  public void testBuilder2F()
  {
    final PVector3F<Object> v =
      PVector3F.builder()
        .from(PVector2F.of(0.0f, 1.0f))
        .setZ(2.0f)
        .build();
    Assertions.assertEquals(0.0, v.x(), 0.0);
    Assertions.assertEquals(1.0, v.y(), 0.0);
    Assertions.assertEquals(2.0, v.z(), 0.0);
  }

  @Test
  public void testBuilder2FMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector3F.builder()
        .from(PVector2F.of(0.0f, 1.0f))
        .build();
    });
  }

  @Test
  public void testBuilder3F()
  {
    final PVector3F<Object> v =
      PVector3F.builder()
        .from(PVector3F.of(0.0f, 1.0f, 2.0f))
        .build();
    Assertions.assertEquals(0.0, v.x(), 0.0);
    Assertions.assertEquals(1.0, v.y(), 0.0);
    Assertions.assertEquals(2.0, v.z(), 0.0);
  }
}
