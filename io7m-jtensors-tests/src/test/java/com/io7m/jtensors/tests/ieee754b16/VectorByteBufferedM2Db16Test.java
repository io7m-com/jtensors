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

package com.io7m.jtensors.tests.ieee754b16;

import com.io7m.jtensors.Vector2DType;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.bytebuffered.ByteBufferedType;
import com.io7m.jtensors.bytebuffered.VectorByteBuffered2DType;
import com.io7m.jtensors.ieee754b16.Vector2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBufferedM2Db16;
import com.io7m.jtensors.ieee754b16.VectorReadable2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable2Db16Type;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.bytebuffered.VectorByteBufferedM2DContract;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM2Db16Test
  extends VectorByteBufferedM2DContract<VectorByteBuffered2DType>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(VectorByteBufferedM2Db16Test.class);
  }

  @Override protected double delta()
  {
    return 0.5;
  }

  @Override protected double randomLargeNegative()
  {
    return Math.random() * -20.0;
  }

  @Override protected double randomLargePositive()
  {
    return Math.random() * 20.0;
  }

  @Override protected Logger logger()
  {
    return VectorByteBufferedM2Db16Test.LOG;
  }

  @Override protected VectorByteBuffered2DType newVectorM2D(
    final double x,
    final double y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered2DType v =
      VectorByteBufferedM2Db16.newVectorFromByteBuffer(buf, 50L);
    v.set2D(x, y);
    return v;
  }

  @Override protected VectorByteBuffered2DType newVectorM2D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered2DType v =
      VectorByteBufferedM2Db16.newVectorFromByteBuffer(buf, 50L);
    v.set2D(0.0, 0.0);
    return v;
  }

  @Override protected VectorByteBuffered2DType newVectorM2D(
    final VectorByteBuffered2DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered2DType vr =
      VectorByteBufferedM2Db16.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2D(v);
    return vr;
  }

  @Override protected VectorByteBuffered2DType newVectorM2DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM2Db16.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered2DType newVectorM2DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM2Db16.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testHierarchy()
  {
    final ByteBuffer buf = ByteBuffer.allocate(6);
    final VectorByteBuffered2Db16Type v =
      VectorByteBufferedM2Db16.newVectorFromByteBuffer(buf, 0L);

    Assert.assertTrue(v instanceof VectorByteBuffered2Db16Type);
    Assert.assertTrue(v instanceof VectorByteBuffered2DType);

    Assert.assertTrue(v instanceof Vector2Db16Type);
    Assert.assertTrue(v instanceof Vector2DType);

    Assert.assertTrue(v instanceof VectorReadable2Db16Type);
    Assert.assertTrue(v instanceof VectorReadable2DType);

    Assert.assertTrue(v instanceof VectorWritable2Db16Type);
    Assert.assertTrue(v instanceof VectorWritable2DType);

    Assert.assertTrue(v instanceof ByteBufferedType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      VectorByteBufferedM2Db16Test.LOG.debug(
        "{} implements {}", VectorByteBufferedM2Db16.class, k);
    }

    Assert.assertEquals(9L, (long) interfaces.size());
  }
}
