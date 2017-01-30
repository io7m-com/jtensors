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

import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixM2x2D;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorReadable2DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix2x2DContract<T extends Matrix2x2DType>
  extends MatrixReadable2x2DContract<T>
{
  @Test
  public final void testAdd()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    final T mk = MatrixM2x2D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mk);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(1.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(3.0, m1.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(4.0, mr.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Override
  protected abstract void checkDirectBufferInvariants(final T mk);

  @Test
  public final void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    final T mr = MatrixM2x2D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(4.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(4.0, mr.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(3.0, m1.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test
  public final void testAddRowScaled()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(3.0);
    m0.setR0C1D(3.0);

    m0.setR1C0D(5.0);
    m0.setR1C1D(5.0);

    MatrixM2x2D.addRowScaled(c, m0, 0, 1, 1, 2.0, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(0.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(13.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(13.0, m1.getR1C1D(), 0.0);

    MatrixM2x2D.addRowScaledInPlace(c, m0, 0, 1, 1, 2.0);
    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(3.0, m0.getR0C0D(), 0.0);
    Assert.assertEquals(3.0, m0.getR0C1D(), 0.0);

    Assert.assertEquals(13.0, m0.getR1C0D(), 0.0);
    Assert.assertEquals(13.0, m0.getR1C1D(), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowA()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, 2, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowB()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, 0, 2, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowC()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, 0, 0, 2, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowA()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowB()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowC()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.addRowScaledInPlace(c, m, 0, 0, -1, 1.0);
  }

  @Test
  public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);

    MatrixM2x2D.copy(m0, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(2.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(4.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(5.0, m1.getR1C1D(), 0.0);
  }

  @Test
  public final void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, MatrixM2x2D.determinant(m), 0.0);
  }

  @Test
  public final void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setR0C0D(2.0);
    m.setR1C1D(2.0);

    Assert.assertEquals(4.0, MatrixM2x2D.determinant(m), 0.0);
  }

  @Test
  public final void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setR0C0D(2.0);

    Assert.assertEquals(2.0, MatrixM2x2D.determinant(m), 0.0);
  }

  @Test
  public final void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setR0C0D(-2.0);

    Assert.assertEquals(-2.0, MatrixM2x2D.determinant(m), 0.0);
  }

  @Test
  public final void testDeterminantZero()
  {
    final T m = this.newMatrix();
    MatrixM2x2D.setZero(m);
    Assert.assertEquals(0.0, MatrixM2x2D.determinant(m), 0.0);
  }

  @Test
  public final void testEqualsCase0()
  {
    final T m0 = this.newMatrix();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test
  public final void testEqualsCase1()
  {
    final T m0 = this.newMatrix();
    Assert.assertFalse(m0.equals(null));
  }

  @Test
  public final void testEqualsCase2()
  {
    final T m0 = this.newMatrix();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @Test
  public final void testEqualsCase3()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    Assert.assertEquals(m0, m1);
    Assert.assertNotSame(m0, m1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test
  public final void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        m1.setRowColumnD(row, col, 256.0);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test
  public final void testExchangeRows()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR1C0D(3.0);
    m0.setR1C1D(4.0);
    this.checkDirectBufferInvariants(m0);

    MatrixM2x2D.exchangeRows(c, m0, 0, 1, m1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(4.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(1.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(2.0, m1.getR1C1D(), 0.0);

    MatrixM2x2D.exchangeRowsInPlace(c, m1, 0, 1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(2.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(3.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(4.0, m1.getR1C1D(), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAOverflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.exchangeRowsInPlace(c, m, 2, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAUnderflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.exchangeRowsInPlace(c, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBOverflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.exchangeRowsInPlace(c, m, 0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBUnderflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.exchangeRowsInPlace(c, m, 0, -1);
  }

  @Test
  public final void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        Assert.assertEquals(
          (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
        m1.setRowColumnD(row, col, 256.0);
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
      }
    }
  }

  @Test
  public final void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(3.0);
    m0.setR0C1D(5.0);

    m0.setR1C0D(11.0);
    m0.setR1C1D(13.0);

    final T m1 = this.newMatrixFrom(m0);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(5.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(11.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(13.0, m1.getR1C1D(), 0.0);
  }

  @Test
  public final void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, m.getR0C0D(), 0.0);
    Assert.assertEquals(0.0, m.getR0C1D(), 0.0);

    Assert.assertEquals(0.0, m.getR1C0D(), 0.0);
    Assert.assertEquals(1.0, m.getR1C1D(), 0.0);
  }

  @Test
  public final void testInvertIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM2x2D.invert(m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getR0C0D(), 0.0);
      Assert.assertEquals(0.0, rm.getR0C1D(), 0.0);

      Assert.assertEquals(0.0, rm.getR1C0D(), 0.0);
      Assert.assertEquals(1.0, rm.getR1C1D(), 0.0);
    }

    {
      final boolean r = MatrixM2x2D.invertInPlace(m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getR0C0D(), 0.0);
      Assert.assertEquals(0.0, rm.getR0C1D(), 0.0);

      Assert.assertEquals(0.0, rm.getR1C0D(), 0.0);
      Assert.assertEquals(1.0, rm.getR1C1D(), 0.0);
    }
  }

  @Test
  public final void testInvertSimple()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(2.0);
    m0.setR0C1D(0.0);

    m0.setR1C0D(0.0);
    m0.setR1C1D(2.0);

    {
      final boolean r = MatrixM2x2D.invert(m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, rm.getR0C0D(), 0.0);
      Assert.assertEquals(0.0, rm.getR0C1D(), 0.0);

      Assert.assertEquals(0.0, rm.getR1C0D(), 0.0);
      Assert.assertEquals(0.5, rm.getR1C1D(), 0.0);
    }

    {
      final boolean r = MatrixM2x2D.invertInPlace(m1);
      Assert.assertTrue(r);
      final T rm = m1;
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, rm.getR0C0D(), 0.0);
      Assert.assertEquals(0.0, rm.getR0C1D(), 0.0);

      Assert.assertEquals(0.0, rm.getR1C0D(), 0.0);
      Assert.assertEquals(2.0, rm.getR1C1D(), 0.0);
    }
  }

  @Test
  public final void testInvertZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM2x2D.setZero(m0);
    this.checkDirectBufferInvariants(m0);

    {
      final boolean r = MatrixM2x2D.invert(m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = MatrixM2x2D.invertInPlace(m0);
      Assert.assertFalse(r);
    }
  }

  @Test
  public final void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m1.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test
  public final void testMultiplyMutateIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(
          m1.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
      }
    }

    final T r = MatrixM2x2D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(
          m1.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test
  public final void testMultiplyMutateSimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR1C0D(3.0);
    m0.setR1C1D(4.0);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM2x2D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(7.0, r.getR0C0D(), 0.0);
    Assert.assertEquals(10.0, r.getR0C1D(), 0.0);
    Assert.assertEquals(15.0, r.getR1C0D(), 0.0);
    Assert.assertEquals(22.0, r.getR1C1D(), 0.0);

    this.checkDirectBufferInvariants(r);
  }

  @Test
  public final void testMultiplySimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR1C0D(3.0);
    m0.setR1C1D(4.0);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    final T r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(7.0, r.getR0C0D(), 0.0);
    Assert.assertEquals(10.0, r.getR0C1D(), 0.0);
    Assert.assertEquals(15.0, r.getR1C0D(), 0.0);
    Assert.assertEquals(22.0, r.getR1C1D(), 0.0);

    this.checkDirectBufferInvariants(r);
  }

  @Test
  public final void testMultiplyVectorSimpleND()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR1C0D(3.0);
    m0.setR1C1D(4.0);

    final VectorReadable2DType v = new VectorI2D(1.0, 2.0);
    final VectorM2D out = new VectorM2D();

    final VectorM2D r = MatrixM2x2D.multiplyVector2D(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(5.0, out.getXD(), 0.0);
    Assert.assertEquals(11.0, out.getYD(), 0.0);
  }

  @Test
  public final void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM2x2D.setZero(m1);

    final T r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(0.0, mr.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeNegativeColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeNegativeRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(2, 0);
  }

  @Test
  public final void testRow()
  {
    final T m = this.newMatrix();
    final VectorM2D v = new VectorM2D();

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);

    m.getRow2D(1, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(1.0, v.getYD(), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRowOverflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(3, new VectorM2D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRowUnderflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(-1, new VectorM2D());
  }

  @Test
  public final void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.setRowColumnD(row, column, 3.0);
      }
    }

    final T mk = MatrixM2x2D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(3.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test
  public final void testScaleMutate()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.setRowColumnD(row, column, 3.0);
      }
    }

    final T mr = MatrixM2x2D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(15.0, m.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(mr);
  }

  @Test
  public final void testScaleRow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);

    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);

    MatrixM2x2D.scaleRow(c, m0, 0, 2.0, m1);
    MatrixM2x2D.scaleRow(c, m0, 1, 4.0, m1);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(4.0, m1.getR0C1D(), 0.0);

    Assert.assertEquals(20.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(24.0, m1.getR1C1D(), 0.0);

    MatrixM2x2D.scaleRowInPlace(c, m0, 0, 2.0);
    MatrixM2x2D.scaleRowInPlace(c, m0, 1, 4.0);
    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(2.0, m0.getR0C0D(), 0.0);
    Assert.assertEquals(4.0, m0.getR0C1D(), 0.0);

    Assert.assertEquals(20.0, m0.getR1C0D(), 0.0);
    Assert.assertEquals(24.0, m0.getR1C1D(), 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateOverflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.scaleRowInPlace(c, m, 2, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateUnderflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    MatrixM2x2D.scaleRowInPlace(c, m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowOverflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM2x2D.scaleRow(c, m, 2, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowUnderflow()
  {
    final MatrixM2x2D.ContextMM2D c = new MatrixM2x2D.ContextMM2D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM2x2D.scaleRow(c, m, -1, 1.0, r);
  }

  private void checkZeroExcept(
    final T m,
    final int row,
    final int col)
  {
    for (int r = 0; r < 2; ++r) {
      for (int c = 0; c < 2; ++c) {
        if (row == r && c == col) {
          continue;
        }

        final double x = m.getRowColumnD(r, c);
        if (x != 0.0) {
          System.err.printf(
            "row %d col %d, %f != %f\n",
            Integer.valueOf(r),
            Integer.valueOf(c),
            Double.valueOf(x),
            Double.valueOf(0.0));
          System.err.println(m.toString());
          Assert.assertEquals(0.0, x, 0.0);
        }
      }
    }
  }

  @Test
  public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    {
      MatrixM2x2D.setZero(m);
      m.setR0C0D(13.0);
      Assert.assertEquals(13.0, m.getR0C0D(), 0.0);
      Assert.assertEquals(13.0, m.getR0C0D(), 0.0);
      this.checkZeroExcept(m, 0, 0);

      MatrixM2x2D.setZero(m);
      m.setR1C0D(13.0);
      Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
      Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
      this.checkZeroExcept(m, 1, 0);
    }

    {
      MatrixM2x2D.setZero(m);
      m.setR0C1D(13.0);
      Assert.assertEquals(13.0, m.getR0C1D(), 0.0);
      Assert.assertEquals(13.0, m.getR0C1D(), 0.0);
      this.checkZeroExcept(m, 0, 1);

      MatrixM2x2D.setZero(m);
      m.setR1C1D(13.0);
      Assert.assertEquals(13.0, m.getR1C1D(), 0.0);
      Assert.assertEquals(13.0, m.getR1C1D(), 0.0);
      this.checkZeroExcept(m, 1, 1);
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test
  public final void testSetInterfaceGetIdentity()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, 3.0);
    Assert.assertEquals(3.0, m.getR0C0D(), 0.0);
    m.setRowColumnD(0, 1, 5.0);
    Assert.assertEquals(5.0, m.getR0C1D(), 0.0);

    m.setRowColumnD(1, 0, 13.0);
    Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
    m.setRowColumnD(1, 1, 17.0);
    Assert.assertEquals(17.0, m.getR1C1D(), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test
  public final void testSetGetInterfaceIdentity()
  {
    final T m = this.newMatrix();

    m.setR0C0D(3.0);
    Assert.assertEquals(3.0, m.getR0C0D(), 0.0);
    m.setR0C1D(5.0);
    Assert.assertEquals(5.0, m.getR0C1D(), 0.0);

    m.setR1C0D(13.0);
    Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
    m.setR1C1D(17.0);
    Assert.assertEquals(17.0, m.getR1C1D(), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test
  public final void testTrace()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(MatrixM2x2D.trace(m), 2.0, 0.0);
  }

  @Test
  public final void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setR0C0D(0.0);
    m.setR0C1D(1.0);

    m.setR1C0D(4.0);
    m.setR1C1D(5.0);

    final T k = MatrixM2x2D.transpose(m, r);
    Assert.assertSame(k, r);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, m.getR0C0D(), 0.0);
    Assert.assertEquals(1.0, m.getR0C1D(), 0.0);

    Assert.assertEquals(4.0, m.getR1C0D(), 0.0);
    Assert.assertEquals(5.0, m.getR1C1D(), 0.0);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, r.getR0C0D(), 0.0);
    Assert.assertEquals(4.0, r.getR0C1D(), 0.0);

    Assert.assertEquals(1.0, r.getR1C0D(), 0.0);
    Assert.assertEquals(5.0, r.getR1C1D(), 0.0);
    this.checkDirectBufferInvariants(r);
  }

  @Test
  public final void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setR0C0D(0.0);
    m.setR0C1D(1.0);

    m.setR1C0D(4.0);
    m.setR1C1D(5.0);

    final T r = MatrixM2x2D.transposeInPlace(m);
    Assert.assertSame(m, r);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, r.getR0C0D(), 0.0);
    Assert.assertEquals(4.0, r.getR0C1D(), 0.0);

    Assert.assertEquals(1.0, r.getR1C0D(), 0.0);
    Assert.assertEquals(5.0, r.getR1C1D(), 0.0);
  }

  @Test
  public final void testZero()
  {
    final T m = this.newMatrix();
    MatrixM2x2D.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertEquals(0.0, m.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test
  public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI2D(1.0, 2.0));
    m.setRowWith2D(1, new VectorI2D(10.0, 20.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test
  public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI2D(1.0, 2.0));
    m.setRowWith2D(1, new VectorI2D(10.0, 20.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
