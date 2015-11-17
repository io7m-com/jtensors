/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.bytebuffered;

import com.io7m.jtensors.bytebuffered.VectorByteBuffered4DType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM4D;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM4DTest
  extends VectorByteBufferedM4DContract<VectorByteBuffered4DType>
{
  @Override protected VectorByteBuffered4DType newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType v =
      VectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    v.set4D(x, y, z, w);
    return v;
  }

  @Override protected VectorByteBuffered4DType newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType v =
      VectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    v.set4D(0.0, 0.0, 0.0, 1.0);
    return v;
  }

  @Override protected VectorByteBuffered4DType newVectorM4DFrom(
    final VectorByteBuffered4DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType vr =
      VectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }

  @Override protected VectorByteBuffered4DType newVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM4D.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered4DType newVectorM4DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM4D.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final VectorByteBuffered4DType v =
      VectorByteBufferedM4D.newVectorFromByteBuffer(b, 0L);
    v.set4D(
      Double.longBitsToDouble(0x1020304011223344L),
      Double.longBitsToDouble(0x5060708051525354L),
      Double.longBitsToDouble(0x90a0b0c091a1b1c1L),
      Double.longBitsToDouble(0xd0e0f000d1e1f101L));

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
    Assert.assertEquals(0x90a0b0c091a1b1c1L, b.getLong(16));
    Assert.assertEquals(0xd0e0f000d1e1f101L, b.getLong(24));
  }
}
