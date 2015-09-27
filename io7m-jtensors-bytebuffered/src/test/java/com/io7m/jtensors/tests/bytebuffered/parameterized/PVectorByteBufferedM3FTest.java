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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3FType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM3F;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedM3FTest<T>
  extends PVectorByteBufferedM3FContract<T, PVectorByteBuffered3FType<T>>
{
  @Override protected PVectorByteBuffered3FType<T> newVectorM3F(
    final float x,
    final float y,
    final float z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3FType<T> v =
      PVectorByteBufferedM3F.newVectorFromByteBuffer(buf, 50L);
    v.set3F(x, y, z);
    return v;
  }

  @Override protected PVectorByteBuffered3FType<T> newVectorM3F()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3FType<T> v =
      PVectorByteBufferedM3F.newVectorFromByteBuffer(buf, 50L);
    v.set3F(0.0f, 0.0f, 0.0f);
    return v;
  }

  @Override protected PVectorByteBuffered3FType<T> newVectorM3F(
    final PVectorByteBuffered3FType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3FType<T> vr =
      PVectorByteBufferedM3F.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3F(v);
    return vr;
  }

  @Override protected PVectorByteBuffered3FType<T> newVectorM3FAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM3F.newVectorFromByteBuffer(buf, offset);
  }
}
