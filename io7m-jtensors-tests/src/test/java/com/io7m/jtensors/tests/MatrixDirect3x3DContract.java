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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.MatrixDirect3x3DType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

public abstract class MatrixDirect3x3DContract<T extends MatrixDirect3x3DType>
  extends Matrix3x3DContract<T>
{
  @Override
  protected abstract T newMatrix();

  @Test
  public final void testBufferEndianness()
  {
    final T m = this.newMatrix();
    final DoubleBuffer b = m.getDirectDoubleBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test
  public final void testStorage()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, 0.0);
    m.setRowColumnD(0, 1, 1.0);
    m.setRowColumnD(0, 2, 2.0);

    m.setRowColumnD(1, 0, 100.0);
    m.setRowColumnD(1, 1, 101.0);
    m.setRowColumnD(1, 2, 102.0);

    m.setRowColumnD(2, 0, 200.0);
    m.setRowColumnD(2, 1, 201.0);
    m.setRowColumnD(2, 2, 202.0);

    {
      final DoubleBuffer b = m.getDirectDoubleBuffer();

      Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
      Assert.assertEquals(0L, (long) b.position());

      Assert.assertEquals(0.0, b.get(0), 0.0);
      Assert.assertEquals(100.0, b.get(1), 0.0);
      Assert.assertEquals(200.0, b.get(2), 0.0);

      Assert.assertEquals(1.0, b.get(3), 0.0);
      Assert.assertEquals(101.0, b.get(4), 0.0);
      Assert.assertEquals(201.0, b.get(5), 0.0);

      Assert.assertEquals(2.0, b.get(6), 0.0);
      Assert.assertEquals(102.0, b.get(7), 0.0);
      Assert.assertEquals(202.0, b.get(8), 0.0);
    }
  }
}
