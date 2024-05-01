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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.core.unparameterized.vectors.Vector3I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import com.io7m.jtensors.generators.Vector3IGenerator;
import com.io7m.jtensors.generators.Vector3LGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral3Type;
import com.io7m.jtensors.storage.bytebuffered.VectorByteBufferedIntegral3Type;
import com.io7m.jtensors.storage.bytebuffered.VectorByteBufferedIntegral3s32;
import com.io7m.jtensors.tests.core.TestLOps;
import com.io7m.mutable.numbers.core.MutableLong;
import net.java.quickcheck.Generator;

import java.nio.ByteBuffer;

public final class VectorByteBufferedIntegral3s32Test
  extends VectorByteBufferedIntegral3Contract
{

  @Override
  protected VectorStorageIntegral3Type create(
    final int offset)
  {
    return this.create(MutableLong.create(), offset);
  }

  @Override
  protected VectorByteBufferedIntegral3Type create(
    final MutableLong base,
    final int offset)
  {
    return VectorByteBufferedIntegral3s32.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      base,
      offset);
  }

  @Override
  protected Generator<Vector3L> createGenerator3L()
  {
    return Vector3LGenerator.create32();
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
}
