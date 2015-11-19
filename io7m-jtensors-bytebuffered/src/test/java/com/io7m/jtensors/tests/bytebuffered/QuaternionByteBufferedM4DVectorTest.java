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

import com.io7m.jtensors.Vector4DType;
import com.io7m.jtensors.bytebuffered.QuaternionByteBuffered4DType;
import com.io7m.jtensors.bytebuffered.QuaternionByteBufferedM4D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class QuaternionByteBufferedM4DVectorTest
  extends VectorByteBufferedM4DContract<QuaternionByteBuffered4DType>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(QuaternionByteBufferedM4DVectorTest.class);
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

  @Override protected Vector4DType newVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final QuaternionByteBuffered4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, offset);
    return vr;
  }

  @Override protected QuaternionByteBuffered4DType newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4D(x, y, z, w);
    return vr;
  }

  @Override protected QuaternionByteBuffered4DType newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4D(0.0, 0.0, 0.0, 1.0);
    return vr;
  }

  @Override protected QuaternionByteBuffered4DType newVectorM4DFrom(
    final QuaternionByteBuffered4DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }

  @Override protected QuaternionByteBuffered4DType newVectorM4DWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return QuaternionByteBufferedM4D.newQuaternionFromByteBufferAndBase(
      buf, base, offset);
  }
}
