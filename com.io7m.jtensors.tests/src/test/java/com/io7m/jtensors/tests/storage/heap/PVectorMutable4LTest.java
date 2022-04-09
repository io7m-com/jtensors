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

package com.io7m.jtensors.tests.storage.heap;

import com.io7m.jtensors.core.parameterized.vectors.PVector4I;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import com.io7m.jtensors.generators.PVector4IGenerator;
import com.io7m.jtensors.generators.PVector4LGenerator;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral4Type;
import com.io7m.jtensors.storage.heap.PVectorMutable4L;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.jtensors.tests.storage.api.PVectorStorageIntegral4Contract;
import net.java.quickcheck.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PVectorMutable4LTest
  extends PVectorStorageIntegral4Contract
{
  @Override
  protected PVectorStorageIntegral4Type<Object> create(
    final int offset)
  {
    return new PVectorMutable4L<>();
  }

  @Override
  protected Generator<PVector4L<Object>> createGenerator4L()
  {
    return PVector4LGenerator.create64();
  }

  @Override
  protected Generator<PVector4I<Object>> createGenerator4I()
  {
    return PVector4IGenerator.create32();
  }

  @Override
  protected void checkEquals(
    final long x,
    final long y)
  {
    TestLOps.checkEquals(x, y);
  }

  @Test
  public void testEqualsHashToString()
  {
    final PVectorMutable4L<Object> v0 = new PVectorMutable4L<>();
    final PVectorMutable4L<Object> v1 = new PVectorMutable4L<>();
    final PVectorMutable4L<Object> v0_x = new PVectorMutable4L<>();
    final PVectorMutable4L<Object> v0_y = new PVectorMutable4L<>();
    final PVectorMutable4L<Object> v0_z = new PVectorMutable4L<>();
    final PVectorMutable4L<Object> v0_w = new PVectorMutable4L<>();

    Assertions.assertEquals(v0, v0);
    Assertions.assertEquals(v0, v1);
    Assertions.assertEquals(v0.toString(), v1.toString());
    Assertions.assertEquals(v0.hashCode(), v1.hashCode());

    v0_x.setX(2L);
    v0_y.setY(3L);
    v0_z.setZ(4L);
    v0_w.setW(5L);

    Assertions.assertNotEquals(v0, v0_x);
    Assertions.assertNotEquals(v0, v0_y);
    Assertions.assertNotEquals(v0, v0_z);
    Assertions.assertNotEquals(v0, v0_w);

    Assertions.assertNotEquals(v0.toString(), v0_x.toString());
    Assertions.assertNotEquals(v0.toString(), v0_y.toString());
    Assertions.assertNotEquals(v0.toString(), v0_z.toString());
    Assertions.assertNotEquals(v0.toString(), v0_w.toString());
    Assertions.assertNotEquals(v0, Integer.valueOf(23));
    Assertions.assertNotEquals(v0, null);
  }
}
