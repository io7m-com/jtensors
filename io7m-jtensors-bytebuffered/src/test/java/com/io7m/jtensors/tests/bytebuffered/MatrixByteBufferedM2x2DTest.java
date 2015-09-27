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

import com.io7m.jtensors.MatrixM2x2D;
import com.io7m.jtensors.MatrixReadable2x2DType;
import com.io7m.jtensors.bytebuffered.MatrixByteBuffered2x2DType;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM2x2D;

import java.nio.ByteBuffer;

public final class MatrixByteBufferedM2x2DTest
  extends MatrixByteBuffered2x2DContract<MatrixByteBuffered2x2DType>
{
  @Override protected MatrixByteBuffered2x2DType newMatrix()
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered2x2DType mr =
      MatrixByteBufferedM2x2D.newMatrixFromByteBuffer(buf, 0L);
    MatrixM2x2D.setIdentity(mr);
    return mr;
  }

  @Override protected MatrixByteBuffered2x2DType newMatrixFrom(
    final MatrixReadable2x2DType m)
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered2x2DType mr =
      MatrixByteBufferedM2x2D.newMatrixFromByteBuffer(buf, 0L);
    MatrixM2x2D.copy(m, mr);
    return mr;
  }

  @Override
  protected void checkDirectBufferInvariants(final MatrixByteBuffered2x2DType m)
  {
    // Nothing required
  }

  @Override protected MatrixByteBuffered2x2DType newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final MatrixByteBuffered2x2DType mr =
      MatrixByteBufferedM2x2D.newMatrixFromByteBuffer(buf, offset);
    return mr;
  }
}
