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

import com.io7m.jtensors.bytebuffered.VectorByteBuffered3DType;
import com.io7m.jtensors.ieee754b16.VectorByteBufferedM3Db16;
import com.io7m.jtensors.tests.bytebuffered.VectorByteBufferedM3DContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class VectorByteBufferedM3Db16Test
  extends VectorByteBufferedM3DContract<VectorByteBuffered3DType>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(VectorByteBufferedM3Db16Test.class);
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
    return VectorByteBufferedM3Db16Test.LOG;
  }

  @Override protected VectorByteBuffered3DType newVectorM3D(
    final double x,
    final double y,
    final double z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered3DType v =
      VectorByteBufferedM3Db16.newVectorFromByteBuffer(buf, 50L);
    v.set3D(x, y, z);
    return v;
  }

  @Override protected VectorByteBuffered3DType newVectorM3D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered3DType v =
      VectorByteBufferedM3Db16.newVectorFromByteBuffer(buf, 50L);
    v.set3D(0.0, 0.0, 0.0);
    return v;
  }

  @Override protected VectorByteBuffered3DType newVectorM3D(
    final VectorByteBuffered3DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final VectorByteBuffered3DType vr =
      VectorByteBufferedM3Db16.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3D(v);
    return vr;
  }

  @Override protected VectorByteBuffered3DType newVectorM3DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM3Db16.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected VectorByteBuffered3DType newVectorM3DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return VectorByteBufferedM3Db16.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }
}
