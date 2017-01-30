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

import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixHeapArrayM2x2D;
import com.io7m.jtensors.MatrixI2x2D;
import com.io7m.jtensors.MatrixReadable2x2DType;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorReadable2DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class MatrixReadable2x2DContract<T extends
  MatrixReadable2x2DType>
{
  protected abstract T newMatrix();

  protected abstract T newMatrixFrom(
    MatrixReadable2x2DType source);

  protected abstract void checkDirectBufferInvariants(T m);

  /**
   * Test that single-element retrievals are correct.
   */

  @Test
  public final void testGetCorrect()
  {
    final VectorReadable2DType c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorReadable2DType c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final MatrixI2x2D m0 = MatrixI2x2D.newFromColumns(c0, c1);

    final T mr = this.newMatrixFrom(m0);
    Assert.assertEquals(1.0, mr.getR0C0D(), 0.0);
    Assert.assertEquals(10.0, mr.getR1C0D(), 0.0);

    Assert.assertEquals(2.0, mr.getR0C1D(), 0.0);
    Assert.assertEquals(20.0, mr.getR1C1D(), 0.0);

    Assert.assertEquals(1.0, mr.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(10.0, mr.getRowColumnD(1, 0), 0.0);

    Assert.assertEquals(2.0, mr.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(20.0, mr.getRowColumnD(1, 1), 0.0);
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test
  public final void testGetRow2Correct()
  {
    final VectorReadable2DType c0 = new VectorI4D(1.0, 10.0, 100.0, 1000.0);
    final VectorReadable2DType c1 = new VectorI4D(2.0, 20.0, 200.0, 2000.0);
    final VectorI4D c2 = new VectorI4D(3.0, 30.0, 300.0, 3000.0);
    final MatrixI2x2D m0 = MatrixI2x2D.newFromColumns(c0, c1);

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
  }

  @Test
  public final void testString()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    final Matrix2x2DType m2 = MatrixHeapArrayM2x2D.newMatrix();
    m2.setR0C0D(2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testGetOutOfBoundsColumn0()
  {
    final T m0 = this.newMatrix();
    m0.getRowColumnD(0, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testGetOutOfBoundsColumn1()
  {
    final T m0 = this.newMatrix();
    m0.getRowColumnD(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testGetOutOfBoundsRow0()
  {
    final T m0 = this.newMatrix();
    m0.getRowColumnD(4, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testGetOutOfBoundsRow1()
  {
    final T m0 = this.newMatrix();
    m0.getRowColumnD(-1, 0);
  }
}
