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

import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBuffered3x3DType;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBufferedM3x3D;
import com.io7m.jtensors.parameterized.PMatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;

import java.nio.ByteBuffer;

//@formatter:off
public final class PMatrixByteBufferedM3x3DTest<T0, T1, T2>
  extends PMatrixByteBuffered3x3DContract<T0, T1, T2,
  PMatrixByteBuffered3x3DType<T0, T1>,
  PMatrixByteBuffered3x3DType<T0, T1>,
  PMatrixByteBuffered3x3DType<T1, T2>,
  PMatrixByteBuffered3x3DType<T0, T2>,
  PMatrixByteBuffered3x3DType<T1, T0>>
{
    //@formatter:on

  @Override
  protected PMatrixByteBuffered3x3DType<T0, T1> newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer b = ByteBuffer.allocate((int) size);
    final PMatrixByteBuffered3x3DType<T0, T1> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, offset);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T0, T1> newMatrix()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T0, T1> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    MatrixM3x3D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T1, T2> newMatrixMultLeft()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T1, T2> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    MatrixM3x3D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T0, T1> newMatrixMultRight()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T0, T1> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    MatrixM3x3D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T0, T2> newMatrixMultResult()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T0, T2> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    MatrixM3x3D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T1, T0> newMatrixInverse()
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T1, T0> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    MatrixM3x3D.setIdentity(mr);
    return mr;
  }

  @Override protected PMatrixByteBuffered3x3DType<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    final ByteBuffer b = ByteBuffer.allocate(300);
    final PMatrixByteBuffered3x3DType<T0, T1> mr =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);
    PMatrixM3x3D.copy(m, mr);
    return mr;
  }

  @Override protected void checkDirectBufferInvariantsWildcard(
    final PMatrixReadable3x3DType<?, ?> m)
  {
    // Nothing
  }

  @Override protected void checkDirectBufferInvariantsGeneric(
    final PMatrixByteBuffered3x3DType<T0, T1> m)
  {
    // Nothing
  }
}
