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

package com.io7m.jtensors.tests.bytebuffered.parameterized;

import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBuffered4x4DType;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBufferedM4x4D;
import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixM4x4D;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4DType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(16 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final PMatrixByteBuffered4x4DType<?, ?> m =
      PMatrixByteBufferedM4x4D.newMatrixFromByteBuffer(b, 0L);

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
