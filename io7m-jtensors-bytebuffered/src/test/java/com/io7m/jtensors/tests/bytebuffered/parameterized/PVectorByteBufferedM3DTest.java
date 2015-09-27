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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3DType;
import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM3D;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedM3DTest<T>
  extends PVectorByteBufferedM3DContract<T, PVectorByteBuffered3DType<T>>
{
  @Override protected PVectorByteBuffered3DType<T> newVectorM3D(
    final double x,
    final double y,
    final double z)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> v =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    v.set3D(x, y, z);
    return v;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> v =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    v.set3D(0.0, 0.0, 0.0);
    return v;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3D(
    final PVectorByteBuffered3DType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVectorByteBuffered3DType<T> vr =
      PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom3D(v);
    return vr;
  }

  @Override protected PVectorByteBuffered3DType<T> newVectorM3DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM3D.newVectorFromByteBuffer(buf, offset);
  }
}
