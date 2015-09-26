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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.parameterized.PMatrixI3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PMatrixReadable3x3DContract<T0, T1, T extends
  PMatrixReadable3x3DType<T0, T1>>
{
  protected abstract T newMatrix();

  protected abstract T newMatrixFrom(
    PMatrixReadable3x3DType<T0, T1> source);

  protected abstract void checkDirectBufferInvariantsWildcard(
    PMatrixReadable3x3DType<?, ?> m);

  /**
   * Test that single-element retrievals are correct.
   */

  @Test public final void testGetCorrect()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final PMatrixI3x3D<T0, T1> m0 = PMatrixI3x3D.newFromColumns(c0, c1, c2);

    final T mr = this.newMatrixFrom(m0);
    Assert.assertEquals(1.0, mr.getR0C0D(), 0.0);
    Assert.assertEquals(10.0, mr.getR1C0D(), 0.0);
    Assert.assertEquals(100.0, mr.getR2C0D(), 0.0);

    Assert.assertEquals(2.0, mr.getR0C1D(), 0.0);
    Assert.assertEquals(20.0, mr.getR1C1D(), 0.0);
    Assert.assertEquals(200.0, mr.getR2C1D(), 0.0);

    Assert.assertEquals(3.0, mr.getR0C2D(), 0.0);
    Assert.assertEquals(30.0, mr.getR1C2D(), 0.0);
    Assert.assertEquals(300.0, mr.getR2C2D(), 0.0);

    Assert.assertEquals(1.0, mr.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(10.0, mr.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(100.0, mr.getRowColumnD(2, 0), 0.0);

    Assert.assertEquals(2.0, mr.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(20.0, mr.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(200.0, mr.getRowColumnD(2, 1), 0.0);

    Assert.assertEquals(3.0, mr.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(30.0, mr.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(300.0, mr.getRowColumnD(2, 2), 0.0);
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow3Correct()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final PMatrixI3x3D<T0, T1> m0 = PMatrixI3x3D.newFromColumns(c0, c1, c2);

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
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow2Correct()
  {
    final VectorI4D c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorI4D c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final PMatrixI3x3D<T0, T1> m0 = PMatrixI3x3D.newFromColumns(c0, c1, c2);

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
  }

  @Test public final void testString()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    final Matrix3x3DType m2 = MatrixHeapArrayM3x3D.newMatrix();
    m2.setR0C0D(2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }
}
