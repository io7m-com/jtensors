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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4F;
import com.io7m.jtensors.generators.Vector4DGenerator;
import com.io7m.jtensors.generators.Vector4FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating4Type;
import com.io7m.jtensors.storage.bytebuffered.ByteBufferOffsetMutable;
import com.io7m.jtensors.storage.bytebuffered.VectorByteBufferedFloating4s16;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.core.TestB16Ops;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.storage.api.VectorStorageFloating4Contract;
import net.java.quickcheck.Generator;
import org.junit.Rule;

import java.nio.ByteBuffer;

public final class VectorByteBufferedFloating4s16Test
  extends VectorStorageFloating4Contract
{
  @Rule public final PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Override
  protected VectorStorageFloating4Type create(
    final int offset)
  {
    return VectorByteBufferedFloating4s16.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      ByteBufferOffsetMutable.create(),
      offset);
  }

  @Override
  protected Generator<Vector4D> createGenerator4D()
  {
    return Vector4DGenerator.createNormal();
  }

  @Override
  protected Generator<Vector4F> createGenerator4F()
  {
    return Vector4FGenerator.createNormal();
  }

  @Override
  protected void checkAlmostEquals(
    final double x,
    final double y)
  {
    TestB16Ops.checkAlmostEquals(x, y);
  }
}
