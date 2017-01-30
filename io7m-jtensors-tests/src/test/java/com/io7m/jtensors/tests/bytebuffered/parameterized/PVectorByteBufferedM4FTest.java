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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered4FType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM4F;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM4FTest<T>
  extends PVectorByteBufferedM4FContract<T, PVectorByteBuffered4FType<T>>
{
  @Override
  protected PVectorByteBuffered4FType<T> newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4FType<T> v =
      PVectorByteBufferedM4F.newVectorFromByteBuffer(buf, 50L);
    v.set4F(x, y, z, w);
    return v;
  }

  @Override
  protected PVectorByteBuffered4FType<T> newVectorM4F()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4FType<T> v =
      PVectorByteBufferedM4F.newVectorFromByteBuffer(buf, 50L);
    v.set4F(0.0f, 0.0f, 0.0f, 1.0f);
    return v;
  }

  @Override
  protected PVectorByteBuffered4FType<T> newVectorM4FFrom(
    final PVectorByteBuffered4FType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4FType<T> vr =
      PVectorByteBufferedM4F.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4F(v);
    return vr;
  }

  @Override
  protected PVectorByteBuffered4FType<T> newVectorM4FAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4F.newVectorFromByteBuffer(buf, offset);
  }

  @Override
  protected PVectorByteBuffered4FType<T> newVectorM4FWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM4F.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test
  public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final PVectorByteBuffered4FType<?> v =
      PVectorByteBufferedM4F.newVectorFromByteBuffer(b, 0L);
    v.set4F(
      Float.intBitsToFloat(0x10203040),
      Float.intBitsToFloat(0x50607080),
      Float.intBitsToFloat(0x90a0b0c0),
      Float.intBitsToFloat(0xd0e0f000));

    Assert.assertEquals(0x10203040, b.getInt(0));
    Assert.assertEquals(0x50607080, b.getInt(4));
    Assert.assertEquals(0x90a0b0c0, b.getInt(8));
    Assert.assertEquals(0xd0e0f000, b.getInt(12));
  }
}
