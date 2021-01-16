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

import com.io7m.jtensors.core.parameterized.vectors.PVector2D;
import com.io7m.jtensors.core.parameterized.vectors.PVector3D;
import com.io7m.jtensors.core.parameterized.vectors.PVector4D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PVector4DBuilderTest
{
  @Test
  public void testBuilder2D()
  {
    final PVector4D<Object> v =
      PVector4D.builder()
        .from(PVector2D.of(0.0, 1.0))
        .setZ(2.0)
        .setW(3.0)
        .build();
    Assertions.assertEquals(0.0, v.x(), 0.0);
    Assertions.assertEquals(1.0, v.y(), 0.0);
    Assertions.assertEquals(2.0, v.z(), 0.0);
    Assertions.assertEquals(3.0, v.w(), 0.0);
  }

  @Test
  public void testBuilder2DMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector4D.builder()
        .from(PVector2D.of(0.0, 1.0))
        .build();
    });
  }

  @Test
  public void testBuilder3D()
  {
    final PVector4D<Object> v =
      PVector4D.builder()
        .from(PVector3D.of(0.0, 1.0, 2.0))
        .setW(3.0)
        .build();
    Assertions.assertEquals(0.0, v.x(), 0.0);
    Assertions.assertEquals(1.0, v.y(), 0.0);
    Assertions.assertEquals(2.0, v.z(), 0.0);
    Assertions.assertEquals(3.0, v.w(), 0.0);
  }

  @Test
  public void testBuilder3DMissing()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      PVector4D.builder()
        .from(PVector3D.of(0.0, 1.0, 2.0))
        .build();
      Assertions.fail();
    });
  }

  @Test
  public void testBuilder4D()
  {
    final PVector4D<Object> v =
      PVector4D.builder()
        .from(PVector4D.of(0.0, 1.0, 2.0, 3.0))
        .build();
    Assertions.assertEquals(0.0, v.x(), 0.0);
    Assertions.assertEquals(1.0, v.y(), 0.0);
    Assertions.assertEquals(2.0, v.z(), 0.0);
    Assertions.assertEquals(3.0, v.w(), 0.0);
  }
}
