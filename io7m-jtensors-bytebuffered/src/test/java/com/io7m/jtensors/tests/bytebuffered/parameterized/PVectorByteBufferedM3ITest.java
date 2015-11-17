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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3IType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM3I;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class PVectorByteBufferedM3ITest<T>
  extends PVectorByteBufferedM3IContract<T, PVectorByteBuffered3IType<T>>
{
  @Override protected PVectorByteBuffered3IType<T> newVectorM3I(
    final int x,
    final int y,
    final int z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3IType<T> v =
      PVectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    v.set3I(x, y, z);
    return v;
  }

  @Override protected PVectorByteBuffered3IType<T> newVectorM3I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3IType<T> v =
      PVectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    v.set3I((int) 0L, (int) 0L, (int) 0L);
    return v;
  }

  @Override protected PVectorByteBuffered3IType<T> newVectorM3I(
    final PVectorByteBuffered3IType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3IType<T> vr =
      PVectorByteBufferedM3I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3I(v);
    return vr;
  }

  @Override protected PVectorByteBuffered3IType<T> newVectorM3IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM3I.newVectorFromByteBuffer(buf, offset);
  }

  @Override protected PVectorByteBuffered3IType<T> newVectorM3IWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PVectorByteBufferedM3I.newVectorFromByteBufferAndBase(
      buf, base, offset);
  }
}
