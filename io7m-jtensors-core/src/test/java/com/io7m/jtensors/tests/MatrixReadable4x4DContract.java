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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM4x4D;
import com.io7m.jtensors.MatrixI4x4D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import org.junit.Assert;
import org.junit.Test;

public abstract class MatrixReadable4x4DContract<T extends
  MatrixReadable4x4DType>
{
  protected abstract T newMatrix();

  protected abstract T newMatrixFrom(
    MatrixReadable4x4DType source);

  protected abstract void checkDirectBufferInvariants(T m);

  @Test public final void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final T m0 = this.newMatrix();
        final Matrix4x4DType m1 = MatrixHeapArrayM4x4D.newMatrix();
        this.checkDirectBufferInvariants(m0);
        Assert.assertEquals((long) m1.hashCode(), (long) m0.hashCode());
        this.checkDirectBufferInvariants(m0);
        m1.setRowColumnD(row, col, 256.0);
        this.checkDirectBufferInvariants(m0);
        Assert.assertNotEquals((long) m0.hashCode(), (long) m1.hashCode());
        this.checkDirectBufferInvariants(m0);
      }
    }
  }

  /**
   * Test that single-element retrievals are correct.
   */

  @Test public final void testGetCorrect()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final VectorI4D c3 = new VectorI4D(4.0, 40.0, 400.0, 4000.0);
    final MatrixI4x4D m0 = MatrixI4x4D.newFromColumns(c0, c1, c2, c3);

    final T mr = this.newMatrixFrom(m0);
    Assert.assertEquals(1.0, mr.getR0C0D(), 0.0);
    Assert.assertEquals(10.0, mr.getR1C0D(), 0.0);
    Assert.assertEquals(100.0, mr.getR2C0D(), 0.0);
    Assert.assertEquals(1000.0, mr.getR3C0D(), 0.0);

    Assert.assertEquals(2.0, mr.getR0C1D(), 0.0);
    Assert.assertEquals(20.0, mr.getR1C1D(), 0.0);
    Assert.assertEquals(200.0, mr.getR2C1D(), 0.0);
    Assert.assertEquals(2000.0, mr.getR3C1D(), 0.0);

    Assert.assertEquals(3.0, mr.getR0C2D(), 0.0);
    Assert.assertEquals(30.0, mr.getR1C2D(), 0.0);
    Assert.assertEquals(300.0, mr.getR2C2D(), 0.0);
    Assert.assertEquals(3000.0, mr.getR3C2D(), 0.0);

    Assert.assertEquals(4.0, mr.getR0C3D(), 0.0);
    Assert.assertEquals(40.0, mr.getR1C3D(), 0.0);
    Assert.assertEquals(400.0, mr.getR2C3D(), 0.0);
    Assert.assertEquals(4000.0, mr.getR3C3D(), 0.0);

    Assert.assertEquals(1.0, mr.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(10.0, mr.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(100.0, mr.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(1000.0, mr.getRowColumnD(3, 0), 0.0);

    Assert.assertEquals(2.0, mr.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(20.0, mr.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(200.0, mr.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(2000.0, mr.getRowColumnD(3, 1), 0.0);

    Assert.assertEquals(3.0, mr.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(30.0, mr.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(300.0, mr.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(3000.0, mr.getRowColumnD(3, 2), 0.0);

    Assert.assertEquals(4.0, mr.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(40.0, mr.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(400.0, mr.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(4000.0, mr.getRowColumnD(3, 3), 0.0);
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow4Correct()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final VectorI4D c3 = new VectorI4D(4.0, 40.0, 400.0, 4000.0);
    final MatrixI4x4D m0 = MatrixI4x4D.newFromColumns(c0, c1, c2, c3);

    final T mr = this.newMatrixFrom(m0);

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4D(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
      Assert.assertEquals(3.0, out.getZD(), 0.0);
      Assert.assertEquals(4.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4D(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
      Assert.assertEquals(30.0, out.getZD(), 0.0);
      Assert.assertEquals(40.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4D(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
      Assert.assertEquals(300.0, out.getZD(), 0.0);
      Assert.assertEquals(400.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4D(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
      Assert.assertEquals(3000.0, out.getZD(), 0.0);
      Assert.assertEquals(4000.0, out.getWD(), 0.0);
    }

    /*
     * Unsafe
     */

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4DUnsafe(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
      Assert.assertEquals(3.0, out.getZD(), 0.0);
      Assert.assertEquals(4.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4DUnsafe(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
      Assert.assertEquals(30.0, out.getZD(), 0.0);
      Assert.assertEquals(40.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4DUnsafe(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
      Assert.assertEquals(300.0, out.getZD(), 0.0);
      Assert.assertEquals(400.0, out.getWD(), 0.0);
    }

    {
      final VectorM4D out = new VectorM4D();
      mr.getRow4DUnsafe(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
      Assert.assertEquals(3000.0, out.getZD(), 0.0);
      Assert.assertEquals(4000.0, out.getWD(), 0.0);
    }
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow3Correct()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final VectorI4D c3 = new VectorI4D(4.0, 40.0, 400.0, 4000.0);
    final MatrixI4x4D m0 = MatrixI4x4D.newFromColumns(c0, c1, c2, c3);

    final T mr = this.newMatrixFrom(m0);

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3D(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
      Assert.assertEquals(3.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3D(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
      Assert.assertEquals(30.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3D(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
      Assert.assertEquals(300.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3D(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
      Assert.assertEquals(3000.0, out.getZD(), 0.0);
    }

    /*
     * Unsafe
     */

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3DUnsafe(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
      Assert.assertEquals(3.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3DUnsafe(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
      Assert.assertEquals(30.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3DUnsafe(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
      Assert.assertEquals(300.0, out.getZD(), 0.0);
    }

    {
      final VectorM3D out = new VectorM3D();
      mr.getRow3DUnsafe(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
      Assert.assertEquals(3000.0, out.getZD(), 0.0);
    }
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow2Correct()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final VectorI4D c3 = new VectorI4D(4.0, 40.0, 400.0, 4000.0);
    final MatrixI4x4D m0 = MatrixI4x4D.newFromColumns(c0, c1, c2, c3);

    final T mr = this.newMatrixFrom(m0);

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2D(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2D(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2D(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2D(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
    }

    /*
     * Unsafe
     */

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2DUnsafe(0, out);
      Assert.assertEquals(1.0, out.getXD(), 0.0);
      Assert.assertEquals(2.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2DUnsafe(1, out);
      Assert.assertEquals(10.0, out.getXD(), 0.0);
      Assert.assertEquals(20.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2DUnsafe(2, out);
      Assert.assertEquals(100.0, out.getXD(), 0.0);
      Assert.assertEquals(200.0, out.getYD(), 0.0);
    }

    {
      final VectorM2D out = new VectorM2D();
      mr.getRow2DUnsafe(3, out);
      Assert.assertEquals(1000.0, out.getXD(), 0.0);
      Assert.assertEquals(2000.0, out.getYD(), 0.0);
    }
  }

  @Test public final void testString()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    final Matrix4x4DType m2 = MatrixHeapArrayM4x4D.newMatrix();
    m2.setR0C0D(2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }
}
