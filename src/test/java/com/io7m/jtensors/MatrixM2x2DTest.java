package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

import junit.framework.Assert;

import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM2x2DTest
{
  @SuppressWarnings("static-method") @Test public void testAdd()
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

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutate()
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

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddRowScaled()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2D.addRowScaled(m0, 0, 1, 1, 2.0, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 13.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);

    MatrixM2x2D.addRowScaled(m0, 0, 1, 1, 2.0);

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 13.0);
    Assert.assertTrue(m0.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, 2, 0, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, 0, 2, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, 0, 0, 2, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, -1, 0, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, 0, -1, 0, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.addRowScaled(m, 0, 0, -1, 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testBufferEndianness()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final DoubleBuffer b = MatrixM2x2D.doubleBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @SuppressWarnings("static-method") @Test(
    expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnly()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final DoubleBuffer b = MatrixM2x2D.doubleBuffer(m);
    b.put(0, 0.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnlyInterface()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final DoubleBuffer b = m.getDoubleBuffer();
    b.put(0, 0.0f);
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 4.0);
    m0.set(1, 1, 5.0);

    MatrixM2x2D.copy(m0, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 4.0);
    Assert.assertTrue(m1.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDeterminantIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    Assert.assertTrue(MatrixM2x2D.determinant(m) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantOther()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == 4.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantScale()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == 2.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDeterminantScaleNegative()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM2x2D.determinant(m) == -2.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantZero()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.setZero(m);
    Assert.assertTrue(MatrixM2x2D.determinant(m) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        final MatrixM2x2D m0 = new MatrixM2x2D();
        final MatrixM2x2D m1 = new MatrixM2x2D();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testExchangeRows()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(1, 0, 3.0);
    m0.set(1, 1, 4.0);

    MatrixM2x2D.exchangeRows(m0, 0, 1, m1);

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 1.0);
    Assert.assertTrue(m1.get(1, 1) == 2.0);

    MatrixM2x2D.exchangeRows(m1, 0, 1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 3.0);
    Assert.assertTrue(m1.get(1, 1) == 4.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRows(m, 2, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRows(m, -1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRows(m, 0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.exchangeRows(m, 0, -1);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testHashcodeNeqExhaustive()
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

  @SuppressWarnings("static-method") @Test public
    void
    testInitializationFrom()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 5.0);

    m0.set(1, 0, 11.0);
    m0.set(1, 1, 13.0);

    final MatrixM2x2D m1 = new MatrixM2x2D(m0);

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);

    Assert.assertTrue(m1.get(1, 0) == 11.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializationIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testInvertIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.value;

      Assert.assertTrue(MatrixM2x2D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2D.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2D.get(rm, 1, 1) == 1.0);
    }

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.value;

      Assert.assertTrue(MatrixM2x2D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2D.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2D.get(rm, 1, 1) == 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testInvertSimple()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
    }

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2D> s = (Some<MatrixM2x2D>) r;
      final MatrixM2x2D rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
    }
  }

  @SuppressWarnings("static-method") @Test public void testInvertZero()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    MatrixM2x2D.setZero(m0);

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM2x2D> r = MatrixM2x2D.invert(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();
    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyMutateIdentity()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyMutateSimple()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();

    MatrixM2x2D.set(m0, 0, 0, 1.0);
    MatrixM2x2D.set(m0, 0, 1, 2.0);
    MatrixM2x2D.set(m0, 1, 0, 3.0);
    MatrixM2x2D.set(m0, 1, 1, 4.0);

    final MatrixM2x2D m1 = new MatrixM2x2D(m0);
    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertTrue(MatrixM2x2D.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 1, 1) == 22.0);
  }

  @SuppressWarnings("static-method") @Test public void testMultiplySimple()
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

    Assert.assertTrue(MatrixM2x2D.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2D.get(r, 1, 1) == 22.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyVectorSimpleND()
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

    Assert.assertTrue(out.x == 5.0);
    Assert.assertTrue(out.y == 11.0);
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyZero()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D mr = new MatrixM2x2D();

    MatrixM2x2D.setZero(m1);

    final MatrixM2x2D r = MatrixM2x2D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.get(0, -1);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.get(-1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.get(0, 2);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    m.get(2, 0);
  }

  @SuppressWarnings("static-method") @Test public void testRow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final VectorM2D v = new VectorM2D();

    MatrixM2x2D.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);

    MatrixM2x2D.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public void testRowOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.row(m, 3, new VectorM2D());
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.row(m, -1, new VectorM2D());
  }

  @SuppressWarnings("static-method") @Test public void testScale()
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

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutate()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.set(row, column, 3.0);
      }
    }

    final MatrixM2x2D mr = MatrixM2x2D.scale(m, 5.0);
    Assert.assertSame(mr, m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleRow()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);

    MatrixM2x2D.scaleRow(m0, 0, 2.0, m1);
    MatrixM2x2D.scaleRow(m0, 1, 4.0, m1);

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);

    MatrixM2x2D.scaleRow(m0, 0, 2.0);
    MatrixM2x2D.scaleRow(m0, 1, 4.0);

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, 2, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, -1, 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, 2, 1.0, r);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();
    MatrixM2x2D.scaleRow(m, -1, 1.0, r);
  }

  @SuppressWarnings("static-method") @Test public void testSetGetIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.set(0, 0, 3.0).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).get(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).get(1, 1) == 17.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testSetGetInterfaceIdentity()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);
  }

  @SuppressWarnings("static-method") @Test public void testStorage()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 0);
    m.set(0, 1, 1);

    m.set(1, 0, 100);
    m.set(1, 1, 101);

    {
      final DoubleBuffer b = MatrixM2x2D.doubleBuffer(m);

      Assert.assertTrue(b.isReadOnly());
      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);

      Assert.assertTrue(b.get(2) == 1.0);
      Assert.assertTrue(b.get(3) == 101.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final MatrixM2x2D m0 = new MatrixM2x2D();
    final MatrixM2x2D m1 = new MatrixM2x2D();
    final MatrixM2x2D m2 = new MatrixM2x2D();
    m2.set(0, 0, 2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @SuppressWarnings("static-method") @Test public void testTranspose()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    final MatrixM2x2D r = new MatrixM2x2D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2D k = MatrixM2x2D.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertTrue(m.get(0, 0) == 0.0);
    Assert.assertTrue(m.get(0, 1) == 1.0);

    Assert.assertTrue(m.get(1, 0) == 4.0);
    Assert.assertTrue(m.get(1, 1) == 5.0);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public void testTransposeMutate()
  {
    final MatrixM2x2D m = new MatrixM2x2D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);

    final MatrixM2x2D r = MatrixM2x2D.transpose(m);
    Assert.assertSame(m, r);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
  }

  @SuppressWarnings("static-method") @Test public void testZero()
  {
    final MatrixM2x2D m = new MatrixM2x2D();
    MatrixM2x2D.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }
  }
}
