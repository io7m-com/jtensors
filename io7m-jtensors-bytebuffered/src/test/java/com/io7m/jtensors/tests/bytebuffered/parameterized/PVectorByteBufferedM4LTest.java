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

package com.io7m.jtensors.tests.bytebuffered.parameterized;

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered4LType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM4L;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM4LTest<T>
  extends PVectorByteBufferedM4LContract<T, PVectorByteBuffered4LType<T>>
{
  @Override protected PVectorByteBuffered4LType<T> newVectorM4L(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4LType<T> v =
      PVectorByteBufferedM4L.newVectorFromByteBuffer(buf, 50L);
    v.set4L(x, y, z, w);
    return v;
  }

  @Override protected PVectorByteBuffered4LType<T> newVectorM4L()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4LType<T> v =
      PVectorByteBufferedM4L.newVectorFromByteBuffer(buf, 50L);
    v.set4L(0L, 0L, 0L, 1L);
    return v;
  }

  @Override protected PVectorByteBuffered4LType<T> newVectorM4LFrom(
    final PVectorByteBuffered4LType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered4LType<T> vr =
      PVectorByteBufferedM4L.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4L(v);
    return vr;
  }

  @Override protected PVectorByteBuffered4LType<T> newVectorM4LAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4L.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected PVectorByteBuffered4LType<T> newVectorM4LWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM4L.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }
}
