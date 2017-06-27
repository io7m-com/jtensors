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

package com.io7m.jtensors.tests.storage.heap;

import com.io7m.jtensors.core.unparameterized.vectors.Vector3I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import com.io7m.jtensors.generators.Vector3IGenerator;
import com.io7m.jtensors.generators.Vector3LGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral3Type;
import com.io7m.jtensors.storage.heap.VectorMutable3L;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.storage.api.VectorStorageIntegral3Contract;
import net.java.quickcheck.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public final class VectorMutable3LTest
  extends VectorStorageIntegral3Contract
{
  @Rule public final PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Override
  protected VectorStorageIntegral3Type create(
    final int offset)
  {
    return new VectorMutable3L();
  }

  @Override
  protected Generator<Vector3L> createGenerator3L()
  {
    return Vector3LGenerator.create64();
  }

  @Override
  protected Generator<Vector3I> createGenerator3I()
  {
    return Vector3IGenerator.create32();
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
    final VectorMutable3L v0 = new VectorMutable3L();
    final VectorMutable3L v1 = new VectorMutable3L();
    final VectorMutable3L v0_x = new VectorMutable3L();
    final VectorMutable3L v0_y = new VectorMutable3L();
    final VectorMutable3L v0_z = new VectorMutable3L();

    Assert.assertEquals(v0, v0);
    Assert.assertEquals(v0, v1);
    Assert.assertEquals(v0.toString(), v1.toString());
    Assert.assertEquals(v0.hashCode(), v1.hashCode());

    v0_x.setX(2L);
    v0_y.setY(3L);
    v0_z.setZ(3L);

    Assert.assertNotEquals(v0, v0_x);
    Assert.assertNotEquals(v0, v0_y);
    Assert.assertNotEquals(v0, v0_z);

    Assert.assertNotEquals(v0.toString(), v0_x.toString());
    Assert.assertNotEquals(v0.toString(), v0_y.toString());
    Assert.assertNotEquals(v0.toString(), v0_z.toString());
    Assert.assertNotEquals(v0, Integer.valueOf(23));
    Assert.assertNotEquals(v0, null);
  }
}
