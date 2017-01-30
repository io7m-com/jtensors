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

package com.io7m.jtensors.tests.ieee754b16;

import com.io7m.jtensors.Vector2DType;
import com.io7m.jtensors.Vector3DType;
import com.io7m.jtensors.Vector4DType;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.VectorWritable4DType;
import com.io7m.jtensors.bytebuffered.ByteBufferedType;
import com.io7m.jtensors.bytebuffered.VectorByteBuffered2DType;
import com.io7m.jtensors.bytebuffered.VectorByteBuffered3DType;
import com.io7m.jtensors.bytebuffered.VectorByteBuffered4DType;
import com.io7m.jtensors.ieee754b16.Vector2Db16Type;
import com.io7m.jtensors.ieee754b16.Vector3Db16Type;
import com.io7m.jtensors.ieee754b16.Vector4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBufferedM4Db16;
import com.io7m.jtensors.ieee754b16.VectorReadable2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorReadable3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorReadable4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable4Db16Type;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.bytebuffered.VectorByteBufferedM4DContract;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM4Db16Test
  extends VectorByteBufferedM4DContract<VectorByteBuffered4DType>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(VectorByteBufferedM4Db16Test.class);
  }

  @Override
  protected double delta()
  {
    return 0.5;
  }

  @Override
  protected double randomLargeNegative()
  {
    return Math.random() * -20.0;
  }

  @Override
  protected double randomLargePositive()
  {
    return Math.random() * 20.0;
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected VectorByteBuffered4DType newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType v =
      VectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    v.set4D(x, y, z, w);
    return v;
  }

  @Override
  protected VectorByteBuffered4DType newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType v =
      VectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    v.set4D(0.0, 0.0, 0.0, 1.0);
    return v;
  }

  @Override
  protected VectorByteBuffered4DType newVectorM4DFrom(
    final VectorByteBuffered4DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered4DType vr =
      VectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }

  @Override
  protected VectorByteBuffered4DType newVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, offset);
  }

  @Override
  protected VectorByteBuffered4DType newVectorM4DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM4Db16.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test
  public void testHierarchy()
  {
    final ByteBuffer buf = ByteBuffer.allocate(6);
    final VectorByteBuffered4Db16Type v =
      VectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 0L);

    Assert.assertTrue(v instanceof VectorByteBuffered4Db16Type);
    Assert.assertTrue(v instanceof VectorByteBuffered4DType);
    Assert.assertTrue(v instanceof VectorByteBuffered3Db16Type);
    Assert.assertTrue(v instanceof VectorByteBuffered3DType);
    Assert.assertTrue(v instanceof VectorByteBuffered2Db16Type);
    Assert.assertTrue(v instanceof VectorByteBuffered2DType);

    Assert.assertTrue(v instanceof Vector4Db16Type);
    Assert.assertTrue(v instanceof Vector4DType);
    Assert.assertTrue(v instanceof Vector3Db16Type);
    Assert.assertTrue(v instanceof Vector3DType);
    Assert.assertTrue(v instanceof Vector2Db16Type);
    Assert.assertTrue(v instanceof Vector2DType);

    Assert.assertTrue(v instanceof VectorReadable4Db16Type);
    Assert.assertTrue(v instanceof VectorReadable4DType);
    Assert.assertTrue(v instanceof VectorReadable3Db16Type);
    Assert.assertTrue(v instanceof VectorReadable3DType);
    Assert.assertTrue(v instanceof VectorReadable2Db16Type);
    Assert.assertTrue(v instanceof VectorReadable2DType);

    Assert.assertTrue(v instanceof VectorWritable4Db16Type);
    Assert.assertTrue(v instanceof VectorWritable4DType);
    Assert.assertTrue(v instanceof VectorWritable3Db16Type);
    Assert.assertTrue(v instanceof VectorWritable3DType);
    Assert.assertTrue(v instanceof VectorWritable2Db16Type);
    Assert.assertTrue(v instanceof VectorWritable2DType);

    Assert.assertTrue(v instanceof ByteBufferedType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", VectorByteBufferedM4Db16.class, k);
    }

    Assert.assertEquals(25L, (long) interfaces.size());
  }
}
