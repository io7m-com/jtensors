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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3DType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM3D;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM3DTest<T>
  extends PVectorByteBufferedM3DContract<T, PVectorByteBuffered3DType<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorByteBufferedM3DTest.class);
  }

  @Override protected double delta()
  {
    return 0.0000000000001;
  }

  @Override protected double randomLargeNegative()
  {
    return Math.random() * -1000000.0;
  }

  @Override protected double randomLargePositive()
  {
    return Math.random() * 1000000.0;
  }

  @Override protected Logger logger()
  {
    return LOG;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3D(
    final double x,
    final double y,
    final double z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> v =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    v.set3D(x, y, z);
    return v;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> v =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    v.set3D(0.0, 0.0, 0.0);
    return v;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3D(
    final PVectorByteBuffered3DType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> vr =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3D(v);
    return vr;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM3D.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(3 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final PVectorByteBuffered3DType<?> v =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(b, 0L);
    v.set3D(
      Double.longBitsToDouble(0x1020304011223344L),
      Double.longBitsToDouble(0x5060708051525354L),
      Double.longBitsToDouble(0x90a0b0c091a1b1c1L));

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
    Assert.assertEquals(0x90a0b0c091a1b1c1L, b.getLong(16));
  }
}
