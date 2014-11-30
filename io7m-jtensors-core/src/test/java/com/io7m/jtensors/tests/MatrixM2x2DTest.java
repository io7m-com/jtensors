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

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jtensors.MatrixM2x2D;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorM2D;

@SuppressWarnings("static-method") public class MatrixM2x2DTest
{
  @Test public void testAdd()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM2x2D mk = MatrixM2x2D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, mk.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 1.0);
        Assert.assertTrue(m1.getRowColumnD(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 4.0);
      }
    }
  }

  @Test public void testAddMutate()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM2x2D mr = MatrixM2x2D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 4.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 4.0);
        Assert.assertTrue(m1.getRowColumnD(row, column) == 3.0);
      }
    }
  }

  @Test public void testAddRowScaled()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2D.addRowScaled(m0, 0, 1, 1, 2.0, m1);
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 0.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 13.0);

    MatrixM2x2D.addRowScaledInPlace(m0, 0, 1, 1, 2.0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    Assert.assertTrue(m0.getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m0.getRowColumnD(0, 1) == 3.0);

    Assert.assertTrue(m0.getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m0.getRowColumnD(1, 1) == 13.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, 2, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, 0, 2, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, 0, 0, 2, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaledInPlace(m, 0, 0, -1, 1.0);
  }

  @Test public void testBufferEndianness()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final DoubleBuffer b = m.getDirectDoubleBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test public void testCopy()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 4.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2D.copy(m0, m1);
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 2.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 4.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 5.0);
  }

  @Test public void testDeterminantIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    Assert.assertTrue(MatrixM2x2D.determinant(m) == 1.0);
  }

  @Test public void testDeterminantOther()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == 4.0);
  }

  @Test public void testDeterminantScale()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == 2.0);
  }

  @Test public void testDeterminantScaleNegative()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == -2.0);
  }

  @Test public void testDeterminantZero()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.setZero(m);
    Assert.assertTrue(MatrixM2x2D.determinant(m) == 0.0);
  }

  @Test public void testEqualsCase0()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test public void testEqualsCase1()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertFalse(m0.equals(null));
  }

  @Test public void testEqualsCase2()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @Test public void testEqualsCase3()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    Assert.assertTrue(m0.equals(m1));
  }

  @Test public void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2D m0 = new MatrixM2x2D();
        final MatrixM2x2D m1 = new MatrixM2x2D();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      }
    }
  }

  @Test public void testExchangeRows()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(1, 0, 3.0);
    m0.set(1, 1, 4.0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    MatrixM2x2D.exchangeRows(m0, 0, 1, m1);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 4.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 2.0);

    MatrixM2x2D.exchangeRowsInPlace(m1, 0, 1);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 2.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 4.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRowsInPlace(m, 2, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRowsInPlace(m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRowsInPlace(m, 0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRowsInPlace(m, 0, -1);
  }

  @Test public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2D m0 = new MatrixM2x2D();
        final MatrixM2x2D m1 = new MatrixM2x2D();
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        m1.set(row, col, 256);
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
      }
    }
  }

  @Test public void testInitializationFrom()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 5.0);

    m0.set(1, 0, 11.0);
    m0.set(1, 1, 13.0);

    final MatrixM2x2D m1 = new MatrixM2x2D(m0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 5.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 11.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 13.0);
  }

  @Test public void testInitializationIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m.getRowColumnD(0, 1) == 0.0);

    Assert.assertTrue(m.getRowColumnD(1, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnD(1, 1) == 1.0);
  }

  @Test public void testInvertIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.get();
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 1.0);
    }

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.get();
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 1.0);
    }
  }

  @Test public void testInvertSimple()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.get();
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 0.5);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 0.5);
    }

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.get();
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 2);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 2);
    }
  }

  @Test public void testInvertZero()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    MatrixM2x2D.setZero(m0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertTrue(r.isNone());
    }

    {
      final OptionType<MatrixM2x2D> r = MatrixM2x2D.invertInPlace(m0);
      Assert.assertTrue(r.isNone());
    }
  }

  @Test public void testMultiplyIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();
    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == mr.getRowColumnD(
          row,
          column));
        Assert.assertTrue(m1.getRowColumnD(row, column) == mr.getRowColumnD(
          row,
          column));
      }
    }
  }

  @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == m1.getRowColumnD(
          row,
          column));
      }
    }

    final MatrixM2x2D r = MatrixM2x2D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == m1.getRowColumnD(
          row,
          column));
      }
    }
  }

  @Test public void testMultiplyMutateSimple()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    MatrixM2x2D.set(m0, 0, 0, 1.0);
    MatrixM2x2D.set(m0, 0, 1, 2.0);
    MatrixM2x2D.set(m0, 1, 0, 3.0);
    MatrixM2x2D.set(m0, 1, 1, 4.0);

    final MatrixM2x2D m1 = new MatrixM2x2D(m0);
    final MatrixM2x2D r = MatrixM2x2D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 7.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 10.0);
    Assert.assertTrue(r.getRowColumnD(1, 0) == 15.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 22.0);

    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
  }

  @Test public void testMultiplySimple()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    MatrixM2x2D.set(m0, 0, 0, 1.0);
    MatrixM2x2D.set(m0, 0, 1, 2.0);
    MatrixM2x2D.set(m0, 1, 0, 3.0);
    MatrixM2x2D.set(m0, 1, 1, 4.0);

    final MatrixM2x2D m1 = new MatrixM2x2D(m0);
    final MatrixM2x2D mr = new MatrixM2x2D();

    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 7.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 10.0);
    Assert.assertTrue(r.getRowColumnD(1, 0) == 15.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 22.0);

    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
  }

  @Test public void testMultiplyVectorSimpleND()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    MatrixM2x2D.set(m0, 0, 0, 1.0);
    MatrixM2x2D.set(m0, 0, 1, 2.0);
    MatrixM2x2D.set(m0, 1, 0, 3.0);
    MatrixM2x2D.set(m0, 1, 1, 4.0);

    final VectorI2D v = new VectorI2D(1.0, 2.0);
    final VectorM2D out = new VectorM2D();

    final VectorM2D r = MatrixM2x2D.multiplyVector2D(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.getXD() == 5.0);
    Assert.assertTrue(out.getYD() == 11.0);
  }

  @Test public void testMultiplyZero()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();

    MatrixM2x2D.setZero(m1);

    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(mr.getRowColumnD(row, column) == 0.0);
      }
    }
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.getRowColumnD(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.getRowColumnD(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.getRowColumnD(0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.getRowColumnD(2, 0);
  }

  @Test public void testRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final VectorM2D v = new VectorM2D();

    MatrixM2x2D.row(m, 0, v);
    Assert.assertTrue(v.getXD() == 1.0);
    Assert.assertTrue(v.getYD() == 0.0);

    MatrixM2x2D.row(m, 1, v);
    Assert.assertTrue(v.getXD() == 0.0);
    Assert.assertTrue(v.getYD() == 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.row(m, 3, new VectorM2D());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.row(m, -1, new VectorM2D());
  }

  @Test public void testScale()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 3.0);
      }
    }

    final MatrixM2x2D mk = MatrixM2x2D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 15.0);
      }
    }
  }

  @Test public void testScaleMutate()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.set(row, column, 3.0);
      }
    }

    final MatrixM2x2D mr = MatrixM2x2D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.getRowColumnD(row, column) == 15.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
  }

  @Test public void testScaleRow()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);

    MatrixM2x2D.scaleRow(m0, 0, 2.0, m1);
    MatrixM2x2D.scaleRow(m0, 1, 4.0, m1);
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 2.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 4.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 20.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 24.0);

    MatrixM2x2D.scaleRowInPlace(m0, 0, 2.0);
    MatrixM2x2D.scaleRowInPlace(m0, 1, 4.0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    Assert.assertTrue(m0.getRowColumnD(0, 0) == 2.0);
    Assert.assertTrue(m0.getRowColumnD(0, 1) == 4.0);

    Assert.assertTrue(m0.getRowColumnD(1, 0) == 20.0);
    Assert.assertTrue(m0.getRowColumnD(1, 1) == 24.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.scaleRowInPlace(m, 2, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.scaleRowInPlace(m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, 2, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, -1, 1.0, r);
  }

  @Test public void testSetGetIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testSetGetInterfaceIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testStorage()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 0);
    m.set(0, 1, 1);

    m.set(1, 0, 100);
    m.set(1, 1, 101);

    {
      final DoubleBuffer b = m.getDirectDoubleBuffer();

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);

      Assert.assertTrue(b.get(2) == 1.0);
      Assert.assertTrue(b.get(3) == 101.0);
    }
  }

  @Test public void testString()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D m2 = new MatrixM2x2D();
    m2.set(0, 0, 2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));

    System.out.println(m0.toString());
  }

  @Test public void testTrace()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    Assert.assertTrue(2.0 == MatrixM2x2D.trace(m));
  }

  @Test public void testTranspose()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2D k = MatrixM2x2D.transpose(m, r);
    Assert.assertSame(k, r);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnD(0, 1) == 1.0);

    Assert.assertTrue(m.getRowColumnD(1, 0) == 4.0);
    Assert.assertTrue(m.getRowColumnD(1, 1) == 5.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 4.0);

    Assert.assertTrue(r.getRowColumnD(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 5.0);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
  }

  @Test public void testTransposeMutate()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2D r = MatrixM2x2D.transposeInPlace(m);
    Assert.assertSame(m, r);
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 4.0);

    Assert.assertTrue(r.getRowColumnD(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 5.0);
  }

  @Test public void testZero()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.getRowColumnD(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }
}
