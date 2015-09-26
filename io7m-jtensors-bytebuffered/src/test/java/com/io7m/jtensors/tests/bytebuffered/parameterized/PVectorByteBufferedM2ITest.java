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

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBufferedM2I;
import com.io7m.jtensors.parameterized.PVector2IType;
import com.io7m.jtensors.tests.parameterized.PVectorM2IBufferedContract;

import java.nio.ByteBuffer;

public final class PVectorByteBufferedM2ITest<T>
  extends PVectorM2IBufferedContract<T, PVector2IType<T>>
{
  @Override protected PVector2IType<T> newVectorM2I(
    final int x,
    final int y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector2IType<T> v =
      PVectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    v.set2I(x, y);
    return v;
  }

  @Override protected PVector2IType<T> newVectorM2I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector2IType<T> v =
      PVectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    v.set2I((int) 0L, (int) 0L);
    return v;
  }

  @Override protected PVector2IType<T> newVectorM2I(final PVector2IType<T> v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final PVector2IType<T> vr =
      PVectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2I(v);
    return vr;
  }

  @Override protected PVector2IType<T> newVectorM2IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return PVectorByteBufferedM2I.newVectorFromByteBuffer(buf, offset);
  }
}
