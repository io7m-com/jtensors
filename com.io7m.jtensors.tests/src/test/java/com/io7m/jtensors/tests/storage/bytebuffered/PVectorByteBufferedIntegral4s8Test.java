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


import com.io7m.jtensors.core.parameterized.vectors.PVector4I;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import com.io7m.jtensors.generators.PVector4IGenerator;
import com.io7m.jtensors.generators.PVector4LGenerator;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral4Type;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral4Type;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral4s8;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.mutable.numbers.core.MutableLong;
import net.java.quickcheck.Generator;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedIntegral4s8Test
  extends PVectorByteBufferedIntegral4Contract
{

  @Override
  protected PVectorStorageIntegral4Type<Object> create(
    final int offset)
  {
    return this.create(MutableLong.create(), offset);
  }

  @Override
  protected PVectorByteBufferedIntegral4Type<Object> create(
    final MutableLong base,
    final int offset)
  {
    return PVectorByteBufferedIntegral4s8.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      base,
      offset);
  }

  @Override
  protected Generator<PVector4L<Object>> createGenerator4L()
  {
    return PVector4LGenerator.create8();
  }

  @Override
  protected Generator<PVector4I<Object>> createGenerator4I()
  {
    return PVector4IGenerator.create8();
  }

  @Override
  protected void checkEquals(
    final long x,
    final long y)
  {
    TestLOps.checkEquals(x, y);
  }
}
