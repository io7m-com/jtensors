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


import com.io7m.jtensors.core.parameterized.vectors.PVector2I;
import com.io7m.jtensors.core.parameterized.vectors.PVector2L;
import com.io7m.jtensors.generators.PVector2IGenerator;
import com.io7m.jtensors.generators.PVector2LGenerator;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral2Type;
import com.io7m.jtensors.storage.bytebuffered.ByteBufferOffsetMutable;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral2s64;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.storage.api.PVectorStorageIntegral2Contract;
import net.java.quickcheck.Generator;
import org.junit.Rule;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedIntegral2s64Test
  extends PVectorStorageIntegral2Contract
{
  @Rule public final PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Override
  protected PVectorStorageIntegral2Type<Object> create(
    final int offset)
  {
    return PVectorByteBufferedIntegral2s64.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      ByteBufferOffsetMutable.create(),
      offset);
  }

  @Override
  protected Generator<PVector2L<Object>> createGenerator2L()
  {
    return PVector2LGenerator.create64();
  }

  @Override
  protected Generator<PVector2I<Object>> createGenerator2I()
  {
    return PVector2IGenerator.create32();
  }

  @Override
  protected void checkEquals(
    final long x,
    final long y)
  {
    TestLOps.checkEquals(x, y);
  }
}
