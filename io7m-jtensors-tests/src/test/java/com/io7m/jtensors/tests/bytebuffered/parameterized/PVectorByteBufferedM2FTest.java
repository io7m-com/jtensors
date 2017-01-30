/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.bytebuffered.parameterized;

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered2FType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM2F;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM2FTest<T>
  extends PVectorByteBufferedM2FContract<T, PVectorByteBuffered2FType<T>>
{
  @Override
  protected PVectorByteBuffered2FType<T> newVectorM2F(
    final float x,
    final float y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2FType<T> v =
      PVectorByteBufferedM2F.newVectorFromByteBuffer(buf, 50L);
    v.set2F(x, y);
    return v;
  }

  @Override
  protected PVectorByteBuffered2FType<T> newVectorM2F()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2FType<T> v =
      PVectorByteBufferedM2F.newVectorFromByteBuffer(buf, 50L);
    v.set2F(0.0f, 0.0f);
    return v;
  }

  @Override
  protected PVectorByteBuffered2FType<T> newVectorM2F(
    final PVectorByteBuffered2FType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2FType<T> vr =
      PVectorByteBufferedM2F.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2F(v);
    return vr;
  }

  @Override
  protected PVectorByteBuffered2FType<T> newVectorM2FAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM2F.newVectorFromByteBuffer(buf, offset);
  }

  @Override
  protected PVectorByteBuffered2FType<T> newVectorM2FWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM2F.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test
  public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(3 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final PVectorByteBuffered2FType<?> v =
      PVectorByteBufferedM2F.newVectorFromByteBuffer(b, 0L);
    v.set2F(
      Float.intBitsToFloat(0x10203040), Float.intBitsToFloat(0x50607080));

    Assert.assertEquals(0x10203040, b.getInt(0));
    Assert.assertEquals(0x50607080, b.getInt(4));
  }
}
