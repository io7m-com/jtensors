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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2F;
import com.io7m.jtensors.generators.Vector2DGenerator;
import com.io7m.jtensors.generators.Vector2FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;
import com.io7m.jtensors.storage.bytebuffered.ByteBufferOffsetMutable;
import com.io7m.jtensors.storage.bytebuffered.VectorByteBufferedFloating2s64;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.core.TestDOps;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.storage.api.VectorStorageFloating2Contract;
import net.java.quickcheck.Generator;
import org.junit.Rule;

import java.nio.ByteBuffer;

public final class VectorByteBufferedFloating2s64Test
  extends VectorStorageFloating2Contract
{
  @Rule public final PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Override
  protected VectorStorageFloating2Type create(
    final int offset)
  {
    return VectorByteBufferedFloating2s64.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      ByteBufferOffsetMutable.create(),
      offset);
  }

  @Override
  protected Generator<Vector2D> createGenerator2D()
  {
    return Vector2DGenerator.createNormal();
  }

  @Override
  protected Generator<Vector2F> createGenerator2F()
  {
    return Vector2FGenerator.createNormal();
  }

  @Override
  protected void checkAlmostEquals(
    final double x,
    final double y)
  {
    TestDOps.checkAlmostEquals(x, y);
  }
}
