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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM4I;
import com.io7m.jtensors.parameterized.PVector4IType;
import com.io7m.jtensors.tests.parameterized.PVectorM4IBufferedContract;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedM4ITest<T>
  extends PVectorM4IBufferedContract<T, PVector4IType<T>>
{
  @Override protected PVector4IType<T> newVectorM4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4IType<T> v =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    v.set4I(x, y, z, w);
    return v;
  }

  @Override protected PVector4IType<T> newVectorM4I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4IType<T> v =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    v.set4I((int) 0L, (int) 0L, (int) 0L, (int) 1L);
    return v;
  }

  @Override protected PVector4IType<T> newVectorM4IFrom(
    final PVector4IType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector4IType<T> vr =
      PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom4I(v);
    return vr;
  }

  @Override protected PVector4IType<T> newVectorM4IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM4I.newVectorFromByteBuffer(buf, offset);
  }
}
