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

import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixI3x3F;
import com.io7m.jtensors.MatrixReadable3x3FType;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM3F;
import org.junit.Assert;
import org.junit.Test;

public abstract class MatrixReadable3x3FContract<T extends
  MatrixReadable3x3FType>
{
  protected abstract T newMatrix();

  protected abstract T newMatrixFrom(
    MatrixReadable3x3FType source);

  protected abstract void checkDirectBufferInvariants(T m);

  /**
   * Test that single-element retrievals are correct.
   */

  @Test public final void testGetCorrect()
  {
    final VectorI4F c0 = new VectorI4F(1.0f, 10.0f, 100.0f, 1000.0f);
    final VectorI4F c1 = new VectorI4F(2.0f, 20.0f, 200.0f, 2000.0f);
    final VectorI4F c2 = new VectorI4F(3.0f, 30.0f, 300.0f, 3000.0f);
    final MatrixI3x3F m0 = MatrixI3x3F.newFromColumns(c0, c1, c2);

    final T mr = this.newMatrixFrom(m0);
    Assert.assertEquals(1.0, (double) mr.getR0C0F(), 0.0);
    Assert.assertEquals(10.0, (double) mr.getR1C0F(), 0.0);
    Assert.assertEquals(100.0, (double) mr.getR2C0F(), 0.0);

    Assert.assertEquals(2.0, (double) mr.getR0C1F(), 0.0);
    Assert.assertEquals(20.0, (double) mr.getR1C1F(), 0.0);
    Assert.assertEquals(200.0, (double) mr.getR2C1F(), 0.0);

    Assert.assertEquals(3.0, (double) mr.getR0C2F(), 0.0);
    Assert.assertEquals(30.0, (double) mr.getR1C2F(), 0.0);
    Assert.assertEquals(300.0, (double) mr.getR2C2F(), 0.0);

    Assert.assertEquals(1.0, (double) mr.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(10.0, (double) mr.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(100.0, (double) mr.getRowColumnF(2, 0), 0.0);

    Assert.assertEquals(2.0, (double) mr.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(20.0, (double) mr.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(200.0, (double) mr.getRowColumnF(2, 1), 0.0);

    Assert.assertEquals(3.0, (double) mr.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(30.0, (double) mr.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(300.0, (double) mr.getRowColumnF(2, 2), 0.0);
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow3Correct()
  {
    final VectorI4F c0 = new VectorI4F(1.0f, 10.0f, 100.0f, 1000.0f);
    final VectorI4F c1 = new VectorI4F(2.0f, 20.0f, 200.0f, 2000.0f);
    final VectorI4F c2 = new VectorI4F(3.0f, 30.0f, 300.0f, 3000.0f);
    final MatrixI3x3F m0 = MatrixI3x3F.newFromColumns(c0, c1, c2);

    final T mr = this.newMatrixFrom(m0);

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3F(0, out);
      Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(3.0, (double) out.getZF(), 0.0);
    }

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3F(1, out);
      Assert.assertEquals(10.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(20.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(30.0, (double) out.getZF(), 0.0);
    }

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3F(2, out);
      Assert.assertEquals(100.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(200.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(300.0, (double) out.getZF(), 0.0);
    }

    /*
     * Unsafe
     */

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3FUnsafe(0, out);
      Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(3.0, (double) out.getZF(), 0.0);
    }

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3FUnsafe(1, out);
      Assert.assertEquals(10.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(20.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(30.0, (double) out.getZF(), 0.0);
    }

    {
      final VectorM3F out = new VectorM3F();
      mr.getRow3FUnsafe(2, out);
      Assert.assertEquals(100.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(200.0, (double) out.getYF(), 0.0);
      Assert.assertEquals(300.0, (double) out.getZF(), 0.0);
    }
  }

  /**
   * Test that row retrievals are correct.
   */

  @Test public final void testGetRow2Correct()
  {
    final VectorI4F c0 = new VectorI4F(
      1.0f, 10.0f, 100.0f, 1000.0f);
    final VectorI4F c1 = new VectorI4F(
      2.0f, 20.0f, 200.0f, 2000.0f);
    final VectorI4F c2 = new VectorI4F(
      3.0f, 30.0f, 300.0f, 3000.0f);
    final MatrixI3x3F m0 = MatrixI3x3F.newFromColumns(c0, c1, c2);

    final T mr = this.newMatrixFrom(m0);

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2F(0, out);
      Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    }

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2F(1, out);
      Assert.assertEquals(10.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(20.0, (double) out.getYF(), 0.0);
    }

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2F(2, out);
      Assert.assertEquals(100.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(200.0, (double) out.getYF(), 0.0);
    }

    /*
     * Unsafe
     */

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2FUnsafe(0, out);
      Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    }

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2FUnsafe(1, out);
      Assert.assertEquals(10.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(20.0, (double) out.getYF(), 0.0);
    }

    {
      final VectorM2F out = new VectorM2F();
      mr.getRow2FUnsafe(2, out);
      Assert.assertEquals(100.0, (double) out.getXF(), 0.0);
      Assert.assertEquals(200.0, (double) out.getYF(), 0.0);
    }
  }

  @Test public final void testString()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    final Matrix3x3FType m2 = MatrixHeapArrayM3x3F.newMatrix();
    m2.setR0C0F(2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }
}
