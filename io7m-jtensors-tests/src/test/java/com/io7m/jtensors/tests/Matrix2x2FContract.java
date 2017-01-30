/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.Matrix2x2FType;
import com.io7m.jtensors.MatrixM2x2F;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorReadable2FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix2x2FContract<T extends Matrix2x2FType>
  extends MatrixReadable2x2FContract<T>
{
  @Test
  public void testAdd()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    final T mk = MatrixM2x2F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mk);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(1.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(3.0, (double) m1.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(4.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Override
  protected abstract void checkDirectBufferInvariants(final T mk);

  @Test
  public void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    final T mr = MatrixM2x2F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(4.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(4.0, (double) mr.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(3.0, (double) m1.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test
  public void testAddRowScaled()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(5.0f);

    MatrixM2x2F.addRowScaled(c, m0, 0, 1, 1, 2.0f, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(1, 1), 0.0);

    MatrixM2x2F.addRowScaledInPlace(c, m0, 0, 1, 1, 2.0f);
    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(13.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(1, 1), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowA()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, 2, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowB()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, 0, 2, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowC()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, 0, 0, 2, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowA()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, -1, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowB()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, 0, -1, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowC()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.addRowScaledInPlace(c, m, 0, 0, -1, 1.0f);
  }

  @Test
  public void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);

    MatrixM2x2F.copy(m0, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(4.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m1.getRowColumnF(1, 1), 0.0);
  }

  @Test
  public void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, (double) MatrixM2x2F.determinant(m), 0.0);
  }

  @Test
  public void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);
    m.setR1C1F(2.0f);

    Assert.assertEquals(4.0, (double) MatrixM2x2F.determinant(m), 0.0);
  }

  @Test
  public void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);

    Assert.assertEquals(2.0, (double) MatrixM2x2F.determinant(m), 0.0);
  }

  @Test
  public void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setR0C0F(-2.0f);

    Assert.assertEquals(-2.0, (double) MatrixM2x2F.determinant(m), 0.0);
  }

  @Test
  public void testDeterminantZero()
  {
    final T m = this.newMatrix();
    MatrixM2x2F.setZero(m);
    Assert.assertEquals(0.0, (double) MatrixM2x2F.determinant(m), 0.0);
  }

  @Test
  public void testEqualsCase0()
  {
    final T m0 = this.newMatrix();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test
  public void testEqualsCase1()
  {
    final T m0 = this.newMatrix();
    Assert.assertFalse(m0.equals(null));
  }

  @Test
  public void testEqualsCase2()
  {
    final T m0 = this.newMatrix();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @Test
  public void testEqualsCase3()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    Assert.assertEquals(m0, m1);
    Assert.assertNotSame(m0, m1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test
  public void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        m1.setRowColumnF(row, col, 256.0F);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test
  public void testExchangeRows()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR1C0F(3.0f);
    m0.setR1C1F(4.0f);

    MatrixM2x2F.exchangeRows(c, m0, 0, 1, m1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(1, 1), 0.0);

    MatrixM2x2F.exchangeRowsInPlace(c, m1, 0, 1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(3.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(1, 1), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsAOverflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.exchangeRowsInPlace(c, m, 2, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsAUnderflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.exchangeRowsInPlace(c, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsBOverflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.exchangeRowsInPlace(c, m, 0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsBUnderflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.exchangeRowsInPlace(c, m, 0, -1);
  }

  @Test
  public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        Assert.assertEquals((long) m1.hashCode(), (long) m0.hashCode());
        m1.setRowColumnF(row, col, 256.0F);
        Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
      }
    }
  }

  @Test
  public void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(5.0f);

    m0.setR1C0F(11.0f);
    m0.setR1C1F(13.0f);

    final T m1 = this.newMatrixFrom(m0);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(5.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(11.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(1, 1), 0.0);
  }

  @Test
  public void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
  }

  @Test
  public void testInvertIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM2x2F.invert(m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
    }

    {
      final boolean r = MatrixM2x2F.invertInPlace(m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
    }
  }

  @Test
  public void testInvertSimple()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(2.0f);
    m0.setR0C1F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);

    {
      final boolean r = MatrixM2x2F.invert(m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(1, 1), 0.0);
    }

    {
      final boolean r = MatrixM2x2F.invertInPlace(m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(1, 1), 0.0);
    }
  }

  @Test
  public void testInvertZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM2x2F.setZero(m0);
    this.checkDirectBufferInvariants(m0);

    {
      final boolean r = MatrixM2x2F.invert(m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = MatrixM2x2F.invertInPlace(m0);
      Assert.assertFalse(r);
    }
  }

  @Test
  public void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
        Assert.assertTrue(
          m1.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
      }
    }
  }

  @Test
  public void testMultiplyMutateIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
      }
    }

    final T r = MatrixM2x2F.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
      }
    }
  }

  @Test
  public void testMultiplyMutateSimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR1C0F(3.0f);
    m0.setR1C1F(4.0f);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM2x2F.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(7.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(15.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(22.0, (double) r.getRowColumnF(1, 1), 0.0);

    this.checkDirectBufferInvariants(r);
  }

  @Test
  public void testMultiplySimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR1C0F(3.0f);
    m0.setR1C1F(4.0f);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    final T r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(7.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(15.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(22.0, (double) r.getRowColumnF(1, 1), 0.0);

    this.checkDirectBufferInvariants(r);
  }

  @Test
  public void testMultiplyVectorSimpleND()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR1C0F(3.0f);
    m0.setR1C1F(4.0f);

    final VectorReadable2FType v = new VectorI2F(1.0f, 2.0f);
    final VectorM2F out = new VectorM2F();

    final VectorM2F r = MatrixM2x2F.multiplyVector2F(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(5.0f, out.getXF(), 0.0f);
    Assert.assertEquals(11.0f, out.getYF(), 0.0f);
  }

  @Test
  public void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM2x2F.setZero(m1);

    final T r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(0.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeNegativeColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeNegativeRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeOverflowColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(2, 0);
  }

  @Test
  public void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnF(row, column, 3.0f);
      }
    }

    final T mk = MatrixM2x2F.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(3.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(15.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test
  public void testScaleMutate()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.setRowColumnF(row, column, 3.0f);
      }
    }

    final T mr = MatrixM2x2F.scaleInPlace(m, 5.0f);
    Assert.assertSame(mr, m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(15.0, (double) m.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(15.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test
  public void testScaleRow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);

    MatrixM2x2F.scaleRow(c, m0, 0, 2.0f, m1);
    MatrixM2x2F.scaleRow(c, m0, 1, 4.0f, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(20.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m1.getRowColumnF(1, 1), 0.0);

    MatrixM2x2F.scaleRowInPlace(c, m0, 0, 2.0f);
    MatrixM2x2F.scaleRowInPlace(c, m0, 1, 4.0f);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m0.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(20.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m0.getRowColumnF(1, 1), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowMutateOverflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.scaleRowInPlace(c, m, 2, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowMutateUnderflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    MatrixM2x2F.scaleRowInPlace(c, m, -1, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowOverflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM2x2F.scaleRow(c, m, 2, 1.0f, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowUnderflow()
  {
    final MatrixM2x2F.ContextMM2F c = new MatrixM2x2F.ContextMM2F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM2x2F.scaleRow(c, m, -1, 1.0f, r);
  }

  @Test
  public void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    m.setR0C0F(3.0f);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 0), 0.0);
    m.setR0C1F(5.0f);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(0, 1), 0.0);

    m.setR1C0F(13.0f);
    Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
    m.setR1C1F(17.0f);
    Assert.assertEquals(17.0, (double) m.getRowColumnF(1, 1), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test
  public void testSetGetInterfaceIdentity()
  {
    final T m = this.newMatrix();

    m.setRowColumnF(0, 0, 3.0f);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 0), 0.0);
    m.setRowColumnF(0, 1, 5.0f);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(0, 1), 0.0);

    m.setRowColumnF(1, 0, 13.0f);
    Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
    m.setRowColumnF(1, 1, 17.0f);
    Assert.assertEquals(17.0, (double) m.getRowColumnF(1, 1), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test
  public void testSetIdentity()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m.setRowColumnF(row, col, (float) Math.random());
      }
    }

    this.checkDirectBufferInvariants(m);
    MatrixM2x2F.setIdentity(m);
    this.checkDirectBufferInvariants(m);
  }

  @Test
  public void testTrace()
  {
    final T m = this.newMatrix();
    final float t = MatrixM2x2F.trace(m);
    Assert.assertEquals((double) t, 2.0, 0.0);
  }

  @Test
  public void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);

    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);

    final T k = MatrixM2x2F.transpose(m, r);
    Assert.assertSame(k, r);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(4.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(1, 1), 0.0);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
    this.checkDirectBufferInvariants(r);
  }

  @Test
  public void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);

    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);

    final T r = MatrixM2x2F.transposeInPlace(m);
    Assert.assertSame(m, r);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);

    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
  }

  @Test
  public void testZero()
  {
    final T m = this.newMatrix();
    MatrixM2x2F.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(0.0, (double) m.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test
  public void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI2F(1.0f, 2.0f));
    m.setRowWith2F(1, new VectorI2F(10.0f, 20.0f));

    m.getRow2F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test
  public void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI2F(1.0f, 2.0f));
    m.setRowWith2F(1, new VectorI2F(10.0f, 20.0f));

    m.getRow2FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
