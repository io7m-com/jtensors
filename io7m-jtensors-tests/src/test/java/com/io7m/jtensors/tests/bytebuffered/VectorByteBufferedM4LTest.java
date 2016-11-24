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

package com.io7m.jtensors.tests.bytebuffered;

import com.io7m.jtensors.bytebuffered.VectorByteBuffered4LType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM4L;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM4LTest
  extends VectorByteBufferedM4LContract<VectorByteBuffered4LType>
{
  @Override protected VectorByteBuffered4LType newVectorM4L(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(4 * 8);
    final VectorByteBuffered4LType v =
      VectorByteBufferedM4L.newVectorFromByteBuffer(buf, 0L);
    v.set4L(x, y, z, w);
    return v;
  }

  @Override protected VectorByteBuffered4LType newVectorM4L()
  {
    final ByteBuffer buf = ByteBuffer.allocate(4 * 8);
    final VectorByteBuffered4LType v =
      VectorByteBufferedM4L.newVectorFromByteBuffer(buf, 0L);
    v.set4L(0L, 0L, 0L, 1L);
    return v;
  }

  @Override protected VectorByteBuffered4LType newVectorM4LFrom(
    final VectorByteBuffered4LType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(4 * 8);
    final VectorByteBuffered4LType vr =
      VectorByteBufferedM4L.newVectorFromByteBuffer(buf, 0L);
    vr.copyFrom4L(v);
    return vr;
  }

  @Override protected VectorByteBuffered4LType newVectorM4LAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM4L.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered4LType newVectorM4LWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM4L.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final VectorByteBuffered4LType v =
      VectorByteBufferedM4L.newVectorFromByteBuffer(b, 0L);
    v.set4L(
      0x1020304011223344L,
      0x5060708051525354L,
      0x90a0b0c091a1b1c1L,
      0xd0e0f000d1e1f101L);

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
    Assert.assertEquals(0x90a0b0c091a1b1c1L, b.getLong(16));
    Assert.assertEquals(0xd0e0f000d1e1f101L, b.getLong(24));
  }
}
