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

import com.io7m.jtensors.Vector4FType;
import com.io7m.jtensors.bytebuffered.QuaternionByteBuffered4FType;
import com.io7m.jtensors.bytebuffered.QuaternionByteBufferedM4F;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class QuaternionByteBufferedM4FVectorTest
  extends VectorByteBufferedM4FContract<QuaternionByteBuffered4FType>
{
  @Override protected Vector4FType newVectorM4FAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final QuaternionByteBuffered4FType vr =
      QuaternionByteBufferedM4F.newQuaternionFromByteBuffer(buf, offset);
    return vr;
  }

  @Override protected QuaternionByteBuffered4FType newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4FType vr =
      QuaternionByteBufferedM4F.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4F(x, y, z, w);
    return vr;
  }

  @Override protected QuaternionByteBuffered4FType newVectorM4F()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4FType vr =
      QuaternionByteBufferedM4F.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4F(0.0f, 0.0f, 0.0f, 1.0f);
    return vr;
  }

  @Override protected QuaternionByteBuffered4FType newVectorM4FFrom(
    final QuaternionByteBuffered4FType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final QuaternionByteBuffered4FType vr =
      QuaternionByteBufferedM4F.newQuaternionFromByteBuffer(buf, 50L);
    vr.copyFrom4F(v);
    return vr;
  }

  @Override protected QuaternionByteBuffered4FType newVectorM4FWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return QuaternionByteBufferedM4F.newQuaternionFromByteBufferAndBase(
      buf, base, offset);
  }
}
