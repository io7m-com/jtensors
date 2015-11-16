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

import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM4x4D;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBuffered4x4DType;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBufferedM4x4D;
import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixM4x4D;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4DType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

//@formatter:off
public final class PMatrixByteBufferedM4x4DTest<T0, T1, T2>
  extends PMatrixByteBuffered4x4DContract<T0, T1, T2,
  PMatrixByteBuffered4x4DType<T0, T1>,
  PMatrixByteBuffered4x4DType<T0, T1>,
  PMatrixByteBuffered4x4DType<T1, T2>,
  PMatrixByteBuffered4x4DType<T0, T2>,
  PMatrixByteBuffered4x4DType<T1, T0>>
{
    //@formatter:on

  @Override
  protected PMatrixByteBuffered4x4DType<T0, T1> newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer b = ByteBuffer.allocate((int) size);
    final PMatrixByteBuffered4x4DType<T0, T1> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, offset);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T0, T1> newMatrix()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T0, T1> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T1, T2> newMatrixMultLeft()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T1, T2> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T0, T1> newMatrixMultRight()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T0, T1> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T0, T2> newMatrixMultResult()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T0, T2> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T1, T0> newMatrixInverse()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T1, T0> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered4x4DType<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4DType<T0, T1> m)
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered4x4DType<T0, T1> mr =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);
    PMatrixM4x4D.copy(m, mr);
    return mr;
  }

  @Override protected void checkDirectBufferInvariantsUntyped(
    final PMatrix4x4DType<?, ?> m)
  {
    // Nothing
  }

  @Override protected void checkDirectBufferInvariantsGeneric(
    final PMatrixByteBuffered4x4DType<T0, T1> m)
  {
    // Nothing
  }

  @Override
  protected PMatrixByteBuffered4x4DType<T0, T1> newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PMatrixByteBufferedM4x4D.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }
}
