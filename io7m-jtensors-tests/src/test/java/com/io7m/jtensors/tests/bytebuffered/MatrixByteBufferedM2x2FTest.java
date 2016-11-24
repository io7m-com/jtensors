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

import com.io7m.jtensors.MatrixM2x2F;
import com.io7m.jtensors.MatrixReadable2x2FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBuffered2x2FType;
import com.io7m.jtensors.bytebuffered.MatrixByteBufferedM2x2F;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

public final class MatrixByteBufferedM2x2FTest
  extends MatrixByteBuffered2x2FContract<MatrixByteBuffered2x2FType>
{
  @Override protected MatrixByteBuffered2x2FType newMatrix()
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered2x2FType mr =
      MatrixByteBufferedM2x2F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM2x2F.setIdentity(mr);
    return mr;
  }

  @Override protected MatrixByteBuffered2x2FType newMatrixFrom(
    final MatrixReadable2x2FType m)
  {
    final ByteBuffer buf = ByteBuffer.allocate(200);
    final MatrixByteBuffered2x2FType mr =
      MatrixByteBufferedM2x2F.newMatrixFromByteBuffer(buf, 0L);
    MatrixM2x2F.copy(m, mr);
    return mr;
  }

  @Override
  protected void checkDirectBufferInvariants(final MatrixByteBuffered2x2FType m)
  {
    // Nothing required
  }

  @Override protected MatrixByteBuffered2x2FType newMatrixAtIndexFromSize(
    final long size,
    final long offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate((int) size);
    final MatrixByteBuffered2x2FType mr =
      MatrixByteBufferedM2x2F.newMatrixFromByteBuffer(buf, offset);
    return mr;
  }

  @Override protected MatrixByteBuffered2x2FType newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return MatrixByteBufferedM2x2F.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(4 * 4);
    b.order(ByteOrder.BIG_ENDIAN);

    final MatrixByteBuffered2x2FType m =
      MatrixByteBufferedM2x2F.newMatrixFromByteBuffer(b, 0L);

    m.setRowColumnF(0, 0, Float.intBitsToFloat(0x01020304));
    m.setRowColumnF(1, 0, Float.intBitsToFloat(0x11121314));

    m.setRowColumnF(0, 1, Float.intBitsToFloat(0x41424344));
    m.setRowColumnF(1, 1, Float.intBitsToFloat(0x51525354));

    Assert.assertEquals(0x01020304, b.getInt(0));
    Assert.assertEquals(0x11121314, b.getInt(4));

    Assert.assertEquals(0x41424344, b.getInt(8));
    Assert.assertEquals(0x51525354, b.getInt(12));
  }
}
