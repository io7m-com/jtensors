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

import com.io7m.jtensors.bytebuffered.VectorByteBuffered2IType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM2I;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM2ITest
  extends VectorByteBufferedM2IContract<VectorByteBuffered2IType>
{
  @Override protected VectorByteBuffered2IType newVectorM2I(
    final int x,
    final int y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 4);
    final VectorByteBuffered2IType v =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 0L);
    v.set2I(x, y);
    return v;
  }

  @Override protected VectorByteBuffered2IType newVectorM2I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 4);
    final VectorByteBuffered2IType v =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 0L);
    v.set2I((int) 0L, (int) 0L);
    return v;
  }

  @Override protected VectorByteBuffered2IType newVectorM2I(
    final VectorByteBuffered2IType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(2 * 4);
    final VectorByteBuffered2IType vr =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 0L);
    vr.copyFrom2I(v);
    return vr;
  }

  @Override protected VectorByteBuffered2IType newVectorM2IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM2I.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered2IType newVectorM2IWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM2I.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(2 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final VectorByteBuffered2IType v =
      VectorByteBufferedM2I.newVectorFromByteBuffer(b, 0L);
    v.set2I(0x11223344, 0x55667788);

    Assert.assertEquals(0x11223344, b.getInt(0));
    Assert.assertEquals(0x55667788, b.getInt(4));
  }
}