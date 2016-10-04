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

package com.io7m.jtensors.tests.ieee754b16.parameterized;

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
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered2DType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3DType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered4DType;
import com.io7m.jtensors.ieee754b16.Vector2Db16Type;
import com.io7m.jtensors.ieee754b16.Vector3Db16Type;
import com.io7m.jtensors.ieee754b16.Vector4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorReadable2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorReadable3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorReadable4Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable2Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable3Db16Type;
import com.io7m.jtensors.ieee754b16.VectorWritable4Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVector2Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVector3Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVector4Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorByteBuffered2Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorByteBuffered3Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorByteBuffered4Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorByteBufferedM4Db16;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorReadable2Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorReadable3Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorReadable4Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorWritable2Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorWritable3Db16Type;
import com.io7m.jtensors.ieee754b16.parameterized.PVectorWritable4Db16Type;
import com.io7m.jtensors.parameterized.PVector2DType;
import com.io7m.jtensors.parameterized.PVector3DType;
import com.io7m.jtensors.parameterized.PVector4DType;
import com.io7m.jtensors.parameterized.PVectorReadable2DType;
import com.io7m.jtensors.parameterized.PVectorReadable3DType;
import com.io7m.jtensors.parameterized.PVectorReadable4DType;
import com.io7m.jtensors.parameterized.PVectorWritable2DType;
import com.io7m.jtensors.parameterized.PVectorWritable3DType;
import com.io7m.jtensors.parameterized.PVectorWritable4DType;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.bytebuffered.parameterized.PVectorByteBufferedM4DContract;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM4Db16Test<T>
  extends PVectorByteBufferedM4DContract<T, PVectorByteBuffered4DType<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorByteBufferedM4Db16Test.class);
  }

  @Override protected PVectorByteBuffered4DType<T> newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4DType<T> v =
      PVectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    v.set4D(x, y, z, w);
    return v;
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
    return PVectorByteBufferedM4Db16Test.LOG;
  }

  @Override protected PVectorByteBuffered4DType<T> newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4DType<T> v =
      PVectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    v.set4D(0.0, 0.0, 0.0, 1.0);
    return v;
  }

  @Override protected PVectorByteBuffered4DType<T> newVectorM4DFrom(
    final PVectorByteBuffered4DType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4DType<T> vr =
      PVectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }

  @Override protected PVectorByteBuffered4DType<T> newPVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected PVectorByteBuffered4DType<T> newVectorM4DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM4Db16.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testHierarchy()
  {
    final ByteBuffer buf = ByteBuffer.allocate(6);
    final PVectorByteBuffered4Db16Type<?> v =
      PVectorByteBufferedM4Db16.newVectorFromByteBuffer(buf, 0L);

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

    Assert.assertTrue(v instanceof PVectorByteBuffered4Db16Type);
    Assert.assertTrue(v instanceof PVectorByteBuffered4DType);
    Assert.assertTrue(v instanceof PVectorByteBuffered3Db16Type);
    Assert.assertTrue(v instanceof PVectorByteBuffered3DType);
    Assert.assertTrue(v instanceof PVectorByteBuffered2Db16Type);
    Assert.assertTrue(v instanceof PVectorByteBuffered2DType);

    Assert.assertTrue(v instanceof PVector4Db16Type);
    Assert.assertTrue(v instanceof PVector4DType);
    Assert.assertTrue(v instanceof PVector3Db16Type);
    Assert.assertTrue(v instanceof PVector3DType);
    Assert.assertTrue(v instanceof PVector2Db16Type);
    Assert.assertTrue(v instanceof PVector2DType);

    Assert.assertTrue(v instanceof PVectorReadable4Db16Type);
    Assert.assertTrue(v instanceof PVectorReadable4DType);
    Assert.assertTrue(v instanceof PVectorReadable3Db16Type);
    Assert.assertTrue(v instanceof PVectorReadable3DType);
    Assert.assertTrue(v instanceof PVectorReadable2Db16Type);
    Assert.assertTrue(v instanceof PVectorReadable2DType);

    Assert.assertTrue(v instanceof PVectorWritable4Db16Type);
    Assert.assertTrue(v instanceof PVectorWritable4DType);
    Assert.assertTrue(v instanceof PVectorWritable3Db16Type);
    Assert.assertTrue(v instanceof PVectorWritable3DType);
    Assert.assertTrue(v instanceof PVectorWritable2Db16Type);
    Assert.assertTrue(v instanceof PVectorWritable2DType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorByteBufferedM4Db16Test.LOG.debug(
        "{} implements {}", PVectorByteBufferedM4Db16.class, k);
    }

    Assert.assertEquals(49L, (long) interfaces.size());
  }
}
