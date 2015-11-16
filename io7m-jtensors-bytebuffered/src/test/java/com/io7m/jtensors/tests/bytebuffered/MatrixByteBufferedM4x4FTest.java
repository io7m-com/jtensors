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

import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBuffered4x4FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM4x4F;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public final class MatrixByteBufferedM4x4FTest
  extends MatrixByteBuffered4x4FContract<MatrixByteBuffered4x4FType>
{
  @Override protected MatrixByteBuffered4x4FType newMatrix()
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered4x4FType mr =
      MatrixByteBufferedM4x4F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM4x4F.setIdentity(mr);
    return mr;
  }

  @Override protected MatrixByteBuffered4x4FType newMatrixFrom(
    final MatrixReadable4x4FType m)
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered4x4FType mr =
      MatrixByteBufferedM4x4F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM4x4F.copy(m, mr);
    return mr;
  }

  @Override
  protected void checkDirectBufferInvariants(final MatrixByteBuffered4x4FType m)
  {
    // Nothing required
  }

  @Override protected MatrixByteBuffered4x4FType newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final MatrixByteBuffered4x4FType mr =
      MatrixByteBufferedM4x4F.newMatrixFromByteBuffer(buf, offset);
    return mr;
  }

  @Override protected MatrixByteBuffered4x4FType newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return MatrixByteBufferedM4x4F.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }
}
