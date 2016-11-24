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

import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixReadable3x3FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBuffered3x3FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM3x3F;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class MatrixByteBufferedM3x3FTest
  extends MatrixByteBuffered3x3FContract<MatrixByteBuffered3x3FType>
{
  @Override protected MatrixByteBuffered3x3FType newMatrix()
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered3x3FType mr =
      MatrixByteBufferedM3x3F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM3x3F.setIdentity(mr);
    return mr;
  }

  @Override protected MatrixByteBuffered3x3FType newMatrixFrom(
    final MatrixReadable3x3FType m)
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered3x3FType mr =
      MatrixByteBufferedM3x3F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM3x3F.copy(m, mr);
    return mr;
  }

  @Override
  protected void checkDirectBufferInvariants(final MatrixByteBuffered3x3FType m)
  {
    // Nothing required
  }

  @Override protected MatrixByteBuffered3x3FType newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final MatrixByteBuffered3x3FType mr =
      MatrixByteBufferedM3x3F.newMatrixFromByteBuffer(buf, offset);
    return mr;
  }

  @Override protected MatrixByteBuffered3x3FType newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return MatrixByteBufferedM3x3F.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(9 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final MatrixByteBuffered3x3FType m =
      MatrixByteBufferedM3x3F.newMatrixFromByteBuffer(b, 0L);

    m.setRowColumnF(0, 0, Float.intBitsToFloat(0x01020304));
    m.setRowColumnF(1, 0, Float.intBitsToFloat(0x11121314));
    m.setRowColumnF(2, 0, Float.intBitsToFloat(0x21222324));

    m.setRowColumnF(0, 1, Float.intBitsToFloat(0x41424344));
    m.setRowColumnF(1, 1, Float.intBitsToFloat(0x51525354));
    m.setRowColumnF(2, 1, Float.intBitsToFloat(0x61626364));

    m.setRowColumnF(0, 2, Float.intBitsToFloat(0x81828384));
    m.setRowColumnF(1, 2, Float.intBitsToFloat(0x91929394));
    m.setRowColumnF(2, 2, Float.intBitsToFloat(0xa1a2a3a4));

    Assert.assertEquals(0x01020304, b.getInt(0));
    Assert.assertEquals(0x11121314, b.getInt(4));
    Assert.assertEquals(0x21222324, b.getInt(8));

    Assert.assertEquals(0x41424344, b.getInt(12));
    Assert.assertEquals(0x51525354, b.getInt(16));
    Assert.assertEquals(0x61626364, b.getInt(20));

    Assert.assertEquals(0x81828384, b.getInt(24));
    Assert.assertEquals(0x91929394, b.getInt(28));
    Assert.assertEquals(0xa1a2a3a4, b.getInt(32));
  }
}
