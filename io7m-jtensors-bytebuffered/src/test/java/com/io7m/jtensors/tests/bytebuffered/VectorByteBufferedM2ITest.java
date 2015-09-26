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

import com.io7m.jtensors.Vector2IType;
import com.io7m.jtensors.bytebuffered.VectorByteBufferedM2I;
import com.io7m.jtensors.tests.VectorM2IBufferedContract;

import java.nio.ByteBuffer;

public final class VectorByteBufferedM2ITest
  extends VectorM2IBufferedContract<Vector2IType>
{
  @Override protected Vector2IType newVectorM2I(
    final int x,
    final int y)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2IType v =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    v.set2I(x, y);
    return v;
  }

  @Override protected Vector2IType newVectorM2I()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2IType v =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    v.set2I((int) 0L, (int) 0L);
    return v;
  }

  @Override protected Vector2IType newVectorM2I(final Vector2IType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Vector2IType vr =
      VectorByteBufferedM2I.newVectorFromByteBuffer(buf, 50L);
    vr.copyFrom2I(v);
    return vr;
  }

  @Override protected Vector2IType newVectorM2IAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    return VectorByteBufferedM2I.newVectorFromByteBuffer(buf, offset);
  }
}
