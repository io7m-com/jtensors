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
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

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

  @Override protected MatrixByteBuffered2x2DType newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return MatrixByteBufferedM2x2D.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final MatrixByteBuffered2x2DType m =
      MatrixByteBufferedM2x2D.newMatrixFromByteBuffer(b, 0L);

    m.setRowColumnD(0, 0, Double.longBitsToDouble(0x0102030405060708L));
    m.setRowColumnD(1, 0, Double.longBitsToDouble(0x1112131415161718L));

    m.setRowColumnD(0, 1, Double.longBitsToDouble(0x4142434445464748L));
    m.setRowColumnD(1, 1, Double.longBitsToDouble(0x5152535455565758L));

    Assert.assertEquals(0x0102030405060708L, b.getLong(0));
    Assert.assertEquals(0x1112131415161718L, b.getLong(8));

    Assert.assertEquals(0x4142434445464748L, b.getLong(16));
    Assert.assertEquals(0x5152535455565758L, b.getLong(24));
  }
}
