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

package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM2x2DTTest
{
  @SuppressWarnings("static-method") @Test public <A> void testAdd()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> mr = new MatrixM2x2DT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM2x2DT<A> mk = MatrixM2x2DT.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mk).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testAddMutate()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM2x2DT<A> mr = MatrixM2x2DT.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testAddRowScaled()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2DT.addRowScaled(m0, 0, 1, 1, 2.0, m1);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 13.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);

    MatrixM2x2DT.addRowScaled(m0, 0, 1, 1, 2.0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 13.0);
    Assert.assertTrue(m0.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, 2, 0, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, 0, 2, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, 0, 0, 2, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, -1, 0, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, 0, -1, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.addRowScaled(m, 0, 0, -1, 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testBufferEndianness()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    final DoubleBuffer b = MatrixM2x2DT.doubleBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @SuppressWarnings("static-method") @Test public <A> void testCopy()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 4.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2DT.copy(m0, m1);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 4.0);
    Assert.assertTrue(m1.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantIdentity()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    Assert.assertTrue(MatrixM2x2DT.determinant(m) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantOther()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);

    Assert.assertTrue(MatrixM2x2DT.determinant(m) == 4.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantScale()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM2x2DT.determinant(m) == 2.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantScaleNegative()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM2x2DT.determinant(m) == -2.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testDeterminantZero()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.setZero(m);
    Assert.assertTrue(MatrixM2x2DT.determinant(m) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase0()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase1()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase2()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public <A> void testEqualsCase3()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
        final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testExchangeRows()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(1, 0, 3.0);
    m0.set(1, 1, 4.0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());

    MatrixM2x2DT.exchangeRows(m0, 0, 1, m1);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 1.0);
    Assert.assertTrue(m1.get(1, 1) == 2.0);

    MatrixM2x2DT.exchangeRows(m1, 0, 1);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 3.0);
    Assert.assertTrue(m1.get(1, 1) == 4.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.exchangeRows(m, 2, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.exchangeRows(m, -1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.exchangeRows(m, 0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.exchangeRows(m, 0, -1);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
        final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        m1.set(row, col, 256);
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInitializationFrom()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 5.0);

    m0.set(1, 0, 11.0);
    m0.set(1, 1, 13.0);

    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>(m0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);

    Assert.assertTrue(m1.get(1, 0) == 11.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInitializationIdentity()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testInvertIdentity()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2DT<A>> s = (Some<MatrixM2x2DT<A>>) r;
      final MatrixM2x2DT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM2x2DT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2DT.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2DT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2DT.get(rm, 1, 1) == 1.0);
    }

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2DT<A>> s = (Some<MatrixM2x2DT<A>>) r;
      final MatrixM2x2DT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM2x2DT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2DT.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2DT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2DT.get(rm, 1, 1) == 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testInvertSimple()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2DT<A>> s = (Some<MatrixM2x2DT<A>>) r;
      final MatrixM2x2DT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
    }

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2DT<A>> s = (Some<MatrixM2x2DT<A>>) r;
      final MatrixM2x2DT<A> rm = s.value;
      Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testInvertZero()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    MatrixM2x2DT.setZero(m0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM2x2DT<A>> r = MatrixM2x2DT.invert(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyIdentity()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> mr = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> r = MatrixM2x2DT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyMutateIdentity()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM2x2DT<A> r = MatrixM2x2DT.multiply(m0, m1);
    Assert.assertSame(m0, r);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyMutateSimple()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();

    MatrixM2x2DT.set(m0, 0, 0, 1.0);
    MatrixM2x2DT.set(m0, 0, 1, 2.0);
    MatrixM2x2DT.set(m0, 1, 0, 3.0);
    MatrixM2x2DT.set(m0, 1, 1, 4.0);

    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>(m0);
    final MatrixM2x2DT<A> r = MatrixM2x2DT.multiply(m0, m1);
    Assert.assertSame(r, m0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    Assert.assertTrue(MatrixM2x2DT.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 1, 1) == 22.0);

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplySimple()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();

    MatrixM2x2DT.set(m0, 0, 0, 1.0);
    MatrixM2x2DT.set(m0, 0, 1, 2.0);
    MatrixM2x2DT.set(m0, 1, 0, 3.0);
    MatrixM2x2DT.set(m0, 1, 1, 4.0);

    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>(m0);
    final MatrixM2x2DT<A> mr = new MatrixM2x2DT<A>();

    final MatrixM2x2DT<A> r = MatrixM2x2DT.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    Assert.assertTrue(MatrixM2x2DT.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2DT.get(r, 1, 1) == 22.0);

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testMultiplyVectorSimpleND()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();

    MatrixM2x2DT.set(m0, 0, 0, 1.0);
    MatrixM2x2DT.set(m0, 0, 1, 2.0);
    MatrixM2x2DT.set(m0, 1, 0, 3.0);
    MatrixM2x2DT.set(m0, 1, 1, 4.0);

    final VectorI2D v = new VectorI2D(1.0, 2.0);
    final VectorM2D out = new VectorM2D();

    final VectorM2D r = MatrixM2x2DT.multiplyVector2D(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 5.0);
    Assert.assertTrue(out.y == 11.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testMultiplyZero()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> mr = new MatrixM2x2DT<A>();

    MatrixM2x2DT.setZero(m1);

    final MatrixM2x2DT<A> r = MatrixM2x2DT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    m.get(0, -1);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    m.get(-1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    m.get(0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    m.get(2, 0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testRow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    final VectorM2D v = new VectorM2D();

    MatrixM2x2DT.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);

    MatrixM2x2DT.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowOverflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.row(m, 3, new VectorM2D());
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowUnderflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.row(m, -1, new VectorM2D());
  }

  @SuppressWarnings("static-method") @Test public <A> void testScale()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> mr = new MatrixM2x2DT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 3.0);
      }
    }

    final MatrixM2x2DT<A> mk = MatrixM2x2DT.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testScaleMutate()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.set(row, column, 3.0);
      }
    }

    final MatrixM2x2DT<A> mr = MatrixM2x2DT.scale(m, 5.0);
    Assert.assertSame(mr, m);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mr).position());

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(mr).position());
  }

  @SuppressWarnings("static-method") @Test public <A> void testScaleRow()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);

    MatrixM2x2DT.scaleRow(m0, 0, 2.0, m1);
    MatrixM2x2DT.scaleRow(m0, 1, 4.0, m1);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);

    MatrixM2x2DT.scaleRow(m0, 0, 2.0);
    MatrixM2x2DT.scaleRow(m0, 1, 4.0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m0).position());

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.scaleRow(m, 2, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.scaleRow(m, -1, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowOverflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> r = new MatrixM2x2DT<A>();
    MatrixM2x2DT.scaleRow(m, 2, 1.0, r);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowUnderflow()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> r = new MatrixM2x2DT<A>();
    MatrixM2x2DT.scaleRow(m, -1, 1.0, r);
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testSetGetIdentity()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    Assert.assertTrue(m.set(0, 0, 3.0).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).get(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).get(1, 1) == 17.0);

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testSetGetInterfaceIdentity()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m).position());
  }

  @SuppressWarnings("static-method") @Test public <A> void testStorage()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    m.set(0, 0, 0);
    m.set(0, 1, 1);

    m.set(1, 0, 100);
    m.set(1, 1, 101);

    {
      final DoubleBuffer b = MatrixM2x2DT.doubleBuffer(m);

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);

      Assert.assertTrue(b.get(2) == 1.0);
      Assert.assertTrue(b.get(3) == 101.0);
    }
  }

  @SuppressWarnings("static-method") @Test public <A> void testString()
  {
    final MatrixM2x2DT<A> m0 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m1 = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> m2 = new MatrixM2x2DT<A>();
    m2.set(0, 0, 2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @SuppressWarnings({ "static-method" }) @Test public <A> void testTrace()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    Assert.assertTrue(2.0 == MatrixM2x2DT.trace(m));
  }

  @SuppressWarnings("static-method") @Test public <A> void testTranspose()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    final MatrixM2x2DT<A> r = new MatrixM2x2DT<A>();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2DT<A> k = MatrixM2x2DT.transpose(m, r);
    Assert.assertSame(k, r);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    Assert.assertTrue(m.get(0, 0) == 0.0);
    Assert.assertTrue(m.get(0, 1) == 1.0);

    Assert.assertTrue(m.get(1, 0) == 4.0);
    Assert.assertTrue(m.get(1, 1) == 5.0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m).position());

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());
  }

  @SuppressWarnings("static-method") @Test public
    <A>
    void
    testTransposeMutate()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2DT<A> r = MatrixM2x2DT.transpose(m);
    Assert.assertSame(m, r);
    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(r).position());

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public <A> void testZero()
  {
    final MatrixM2x2DT<A> m = new MatrixM2x2DT<A>();
    MatrixM2x2DT.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, MatrixM2x2DT.doubleBuffer(m).position());
  }
}
