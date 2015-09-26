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

import com.io7m.jtensors.Quaternion4DType;
import com.io7m.jtensors.Vector4DType;
import com.io7m.jtensors.bytebuffered.QuaternionByteBufferedM4D;
import com.io7m.jtensors.tests.VectorM4DBufferedContract;

import java.nio.ByteBuffer;

public final class QuaternionByteBufferedM4DVectorTest
  extends VectorM4DBufferedContract<Quaternion4DType>
{
  @Override protected Vector4DType newVectorM4DAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final Quaternion4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, offset);
    return vr;
  }

  @Override protected Quaternion4DType newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Quaternion4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4D(x, y, z, w);
    return vr;
  }

  @Override protected Quaternion4DType newVectorM4D()
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Quaternion4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.set4D(0.0, 0.0, 0.0, 1.0);
    return vr;
  }

  @Override
  protected Quaternion4DType newVectorM4DFrom(final Quaternion4DType v)
  {
    final ByteBuffer buf = ByteBuffer.allocate(100);
    final Quaternion4DType vr =
      QuaternionByteBufferedM4D.newQuaternionFromByteBuffer(buf, 50L);
    vr.copyFrom4D(v);
    return vr;
  }
}
