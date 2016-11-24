/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.bytebuffered.MatrixByteBuffered4x4DType;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM4x4D;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class MatrixByteBufferedM4x4DTest
  extends MatrixByteBuffered4x4DContract<MatrixByteBuffered4x4DType>
{
  @Override protected MatrixByteBuffered4x4DType newMatrix()
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered4x4DType mr =
      MatrixByteBufferedM4x4D.newMatrixFromByteBuffer(buf, 0L);
    MatrixM4x4D.setIdentity(mr);
    return mr;
  }

  @Override protected MatrixByteBuffered4x4DType newMatrixFrom(
    final MatrixReadable4x4DType m)
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered4x4DType mr =
      MatrixByteBufferedM4x4D.newMatrixFromByteBuffer(buf, 0L);
    MatrixM4x4D.copy(m, mr);
    return mr;
  }

  @Override
  protected void checkDirectBufferInvariants(final MatrixByteBuffered4x4DType m)
  {
    // Nothing required
  }

  @Override protected MatrixByteBuffered4x4DType newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final MatrixByteBuffered4x4DType mr =
      MatrixByteBufferedM4x4D.newMatrixFromByteBuffer(buf, offset);
    return mr;
  }

  @Override protected MatrixByteBuffered4x4DType newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return MatrixByteBufferedM4x4D.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(16 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final MatrixByteBuffered4x4DType m =
      MatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);

    m.setRowColumnD(0, 0, Double.longBitsToDouble(0x0102030405060708L));
    m.setRowColumnD(1, 0, Double.longBitsToDouble(0x1112131415161718L));
    m.setRowColumnD(2, 0, Double.longBitsToDouble(0x2122232425262728L));
    m.setRowColumnD(3, 0, Double.longBitsToDouble(0x3132333435363738L));

    m.setRowColumnD(0, 1, Double.longBitsToDouble(0x4142434445464748L));
    m.setRowColumnD(1, 1, Double.longBitsToDouble(0x5152535455565758L));
    m.setRowColumnD(2, 1, Double.longBitsToDouble(0x6162636465666768L));
    m.setRowColumnD(3, 1, Double.longBitsToDouble(0x7172737475767778L));

    m.setRowColumnD(0, 2, Double.longBitsToDouble(0x8182838485868788L));
    m.setRowColumnD(1, 2, Double.longBitsToDouble(0x9192939495969798L));
    m.setRowColumnD(2, 2, Double.longBitsToDouble(0xa1a2a3a4a5a6a7a8L));
    m.setRowColumnD(3, 2, Double.longBitsToDouble(0xb1b2b3b4b5b6b7b8L));

    m.setRowColumnD(0, 3, Double.longBitsToDouble(0xc1c2c3c4c5c6c7c8L));
    m.setRowColumnD(1, 3, Double.longBitsToDouble(0xd1d2d3d4d5d6d7d8L));
    m.setRowColumnD(2, 3, Double.longBitsToDouble(0xe1e2e3e4e5e6e7e8L));
    m.setRowColumnD(3, 3, Double.longBitsToDouble(0xf1f2f3f4f5f6f7f8L));

    Assert.assertEquals(0x0102030405060708L, b.getLong(0));
    Assert.assertEquals(0x1112131415161718L, b.getLong(8));
    Assert.assertEquals(0x2122232425262728L, b.getLong(16));
    Assert.assertEquals(0x3132333435363738L, b.getLong(24));

    Assert.assertEquals(0x4142434445464748L, b.getLong(32));
    Assert.assertEquals(0x5152535455565758L, b.getLong(40));
    Assert.assertEquals(0x6162636465666768L, b.getLong(48));
    Assert.assertEquals(0x7172737475767778L, b.getLong(56));

    Assert.assertEquals(0x8182838485868788L, b.getLong(64));
    Assert.assertEquals(0x9192939495969798L, b.getLong(72));
    Assert.assertEquals(0xa1a2a3a4a5a6a7a8L, b.getLong(80));
    Assert.assertEquals(0xb1b2b3b4b5b6b7b8L, b.getLong(88));

    Assert.assertEquals(0xc1c2c3c4c5c6c7c8L, b.getLong(96));
    Assert.assertEquals(0xd1d2d3d4d5d6d7d8L, b.getLong(104));
    Assert.assertEquals(0xe1e2e3e4e5e6e7e8L, b.getLong(112));
    Assert.assertEquals(0xf1f2f3f4f5f6f7f8L, b.getLong(120));
  }
}
