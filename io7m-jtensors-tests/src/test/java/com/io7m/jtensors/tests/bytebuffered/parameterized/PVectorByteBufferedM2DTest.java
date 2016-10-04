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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered2DType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM2D;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM2DTest<T>
  extends PVectorByteBufferedM2DContract<T, PVectorByteBuffered2DType<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorByteBufferedM2DTest.class);
  }

  @Override protected double delta()
  {
    return 0.0000000000001;
  }

  @Override protected double randomLargeNegative()
  {
    return Math.random() * -100000000.0;
  }

  @Override protected double randomLargePositive()
  {
    return Math.random() * 100000000.0;
  }

  @Override protected Logger logger()
  {
    return LOG;
  }

  @Override protected PVectorByteBuffered2DType<T> newVectorM2D(
    final double x,
    final double y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2DType<T> v =
      PVectorByteBufferedM2D.newVectorFromByteBuffer(buf, 50L);
    v.set2D(x, y);
    return v;
  }

  @Override protected PVectorByteBuffered2DType<T> newVectorM2D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2DType<T> v =
      PVectorByteBufferedM2D.newVectorFromByteBuffer(buf, 50L);
    v.set2D(0.0, 0.0);
    return v;
  }

  @Override protected PVectorByteBuffered2DType<T> newVectorM2D(
    final PVectorByteBuffered2DType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered2DType<T> vr =
      PVectorByteBufferedM2D.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2D(v);
    return vr;
  }

  @Override protected PVectorByteBuffered2DType<T> newVectorM2DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM2D.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected PVectorByteBuffered2DType<T> newVectorM2DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM2D.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(2 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final PVectorByteBuffered2DType<?> v =
      PVectorByteBufferedM2D.newVectorFromByteBuffer(b, 0L);
    v.set2D(
      Double.longBitsToDouble(0x1020304011223344L),
      Double.longBitsToDouble(0x5060708051525354L));

    Assert.assertEquals(0x1020304011223344L, b.getLong(0));
    Assert.assertEquals(0x5060708051525354L, b.getLong(8));
  }
}
