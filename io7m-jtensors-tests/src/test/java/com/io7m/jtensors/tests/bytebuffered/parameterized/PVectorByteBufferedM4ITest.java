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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered4IType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM4I;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM4ITest<T>
  extends PVectorByteBufferedM4IContract<T, PVectorByteBuffered4IType<T>>
{
  @Override
  protected PVectorByteBuffered4IType<T> newVectorM4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4IType<T> v =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    v.set4I(x, y, z, w);
    return v;
  }

  @Override
  protected PVectorByteBuffered4IType<T> newVectorM4I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4IType<T> v =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    v.set4I((int) 0L, (int) 0L, (int) 0L, (int) 1L);
    return v;
  }

  @Override
  protected PVectorByteBuffered4IType<T> newVectorM4IFrom(
    final PVectorByteBuffered4IType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4IType<T> vr =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4I(v);
    return vr;
  }

  @Override
  protected PVectorByteBuffered4IType<T> newVectorM4IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, offset);
  }

  @Override
  protected PVectorByteBuffered4IType<T> newVectorM4IWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM4I.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test
  public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final PVectorByteBuffered4IType<?> v =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(b, 0L);
    v.set4I(0x11223344, 0x55667788, 0x99aabbcc, 0xddeeff00);

    Assert.assertEquals(0x11223344, b.getInt(0));
    Assert.assertEquals(0x55667788, b.getInt(4));
    Assert.assertEquals(0x99aabbcc, b.getInt(8));
    Assert.assertEquals(0xddeeff00, b.getInt(12));
  }
}
