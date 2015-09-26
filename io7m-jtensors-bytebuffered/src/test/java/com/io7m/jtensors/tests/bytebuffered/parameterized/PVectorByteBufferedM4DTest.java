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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM4D;
import com.io7m.jtensors.parameterized.PVector4DType;
import com.io7m.jtensors.tests.parameterized.PVectorM4DBufferedContract;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedM4DTest<T>
  extends PVectorM4DBufferedContract<T, PVector4DType<T>>
{
  @Override protected PVector4DType<T> newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4DType<T> v =
      PVectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    v.set4D(x, y, z, w);
    return v;
  }

  @Override protected PVector4DType<T> newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4DType<T> v =
      PVectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    v.set4D(0.0, 0.0, 0.0, 1.0);
    return v;
  }

  @Override protected PVector4DType<T> newVectorM4DFrom(
    final PVector4DType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4DType<T> vr =
      PVectorByteBufferedM4D.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }

  @Override protected PVector4DType<T> newPVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4D.newVectorFromByteBuffer(buf, offset);
  }
}
