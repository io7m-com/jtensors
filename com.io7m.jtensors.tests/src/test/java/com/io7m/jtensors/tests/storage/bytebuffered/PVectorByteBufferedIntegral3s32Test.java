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


import com.io7m.jtensors.core.parameterized.vectors.PVector3I;
import com.io7m.jtensors.core.parameterized.vectors.PVector3L;
import com.io7m.jtensors.generators.PVector3IGenerator;
import com.io7m.jtensors.generators.PVector3LGenerator;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral3Type;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral3Type;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral3s32;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.mutable.numbers.core.MutableLong;
import net.java.quickcheck.Generator;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedIntegral3s32Test
  extends PVectorByteBufferedIntegral3Contract
{

  @Override
  protected PVectorByteBufferedIntegral3Type<Object> create(
    final MutableLong base,
    final int offset)
  {
    return PVectorByteBufferedIntegral3s32.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      base,
      offset);
  }

  @Override
  protected PVectorStorageIntegral3Type<Object> create(
    final int offset)
  {
    return this.create(MutableLong.create(), offset);
  }

  @Override
  protected Generator<PVector3L<Object>> createGenerator3L()
  {
    return PVector3LGenerator.create32();
  }

  @Override
  protected Generator<PVector3I<Object>> createGenerator3I()
  {
    return PVector3IGenerator.create32();
  }

  @Override
  protected void checkEquals(
    final long x,
    final long y)
  {
    TestLOps.checkEquals(x, y);
  }
}
