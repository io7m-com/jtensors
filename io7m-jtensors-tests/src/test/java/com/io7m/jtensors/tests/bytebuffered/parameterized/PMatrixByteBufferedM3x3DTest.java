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

import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBuffered3x3DType;
import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBufferedM3x3D;
import com.io7m.jtensors.parameterized.PMatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

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

  @Override
  protected PMatrixByteBuffered3x3DType<T0, T1> newMatrixWithBaseOffset(
    final int size,
    final AtomicLong base,
    final int offset)
  {
    final ByteBuffer buf = ByteBuffer.allocate(size);
    return PMatrixByteBufferedM3x3D.newMatrixFromByteBufferAndBase(
      buf, base, offset);
  }

  @Test public void testImplementationSpecificMemoryLayout0()
  {
    final ByteBuffer b = ByteBuffer.allocate(9 * 8);
    b.order(ByteOrder.BIG_ENDIAN);

    final PMatrixByteBuffered3x3DType<?, ?> m =
      PMatrixByteBufferedM3x3D.newMatrixFromByteBuffer(b, 0L);

    m.setRowColumnD(0, 0, Double.longBitsToDouble(0x0102030405060708L));
    m.setRowColumnD(1, 0, Double.longBitsToDouble(0x1112131415161718L));
    m.setRowColumnD(2, 0, Double.longBitsToDouble(0x2122232425262728L));

    m.setRowColumnD(0, 1, Double.longBitsToDouble(0x4142434445464748L));
    m.setRowColumnD(1, 1, Double.longBitsToDouble(0x5152535455565758L));
    m.setRowColumnD(2, 1, Double.longBitsToDouble(0x6162636465666768L));

    m.setRowColumnD(0, 2, Double.longBitsToDouble(0x8182838485868788L));
    m.setRowColumnD(1, 2, Double.longBitsToDouble(0x9192939495969798L));
    m.setRowColumnD(2, 2, Double.longBitsToDouble(0xa1a2a3a4a5a6a7a8L));

    Assert.assertEquals(0x0102030405060708L, b.getLong(0));
    Assert.assertEquals(0x1112131415161718L, b.getLong(8));
    Assert.assertEquals(0x2122232425262728L, b.getLong(16));

    Assert.assertEquals(0x4142434445464748L, b.getLong(24));
    Assert.assertEquals(0x5152535455565758L, b.getLong(32));
    Assert.assertEquals(0x6162636465666768L, b.getLong(40));

    Assert.assertEquals(0x8182838485868788L, b.getLong(48));
    Assert.assertEquals(0x9192939495969798L, b.getLong(56));
    Assert.assertEquals(0xa1a2a3a4a5a6a7a8L, b.getLong(64));
  }
}
