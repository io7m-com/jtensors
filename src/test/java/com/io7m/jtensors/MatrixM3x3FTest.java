package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

import junit.framework.Assert;

import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM3x3FTest
{
  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM3x3F mk = MatrixM3x3F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutate()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM3x3F mr = MatrixM3x3F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddRowScaled()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);

    MatrixM3x3F.addRowScaled(m0, 0, 1, 2, 2.0f, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);
    Assert.assertTrue(m1.get(0, 2) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 13.0);
    Assert.assertTrue(m1.get(2, 1) == 13.0);
    Assert.assertTrue(m1.get(2, 2) == 13.0);

    MatrixM3x3F.addRowScaledInPlace(m0, 0, 1, 2, 2.0f);

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);
    Assert.assertTrue(m0.get(0, 2) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 5.0);
    Assert.assertTrue(m0.get(1, 1) == 5.0);
    Assert.assertTrue(m0.get(1, 2) == 5.0);

    Assert.assertTrue(m0.get(2, 0) == 13.0);
    Assert.assertTrue(m0.get(2, 1) == 13.0);
    Assert.assertTrue(m0.get(2, 2) == 13.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, 3, 0, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, 0, 3, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, 0, 0, 3, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, -1, 0, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, 0, -1, 0, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.addRowScaledInPlace(m, 0, 0, -1, 1.0f);
  }

  @SuppressWarnings("static-method") @Test public void testBufferEndianness()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final FloatBuffer b = MatrixM3x3F.floatBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @SuppressWarnings("static-method") @Test(
    expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnly()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final FloatBuffer b = MatrixM3x3F.floatBuffer(m);
    b.put(0, 0.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnlyInterface()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final FloatBuffer b = m.getFloatBuffer();
    b.put(0, 0.0f);
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 4.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 6.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 8.0f);
    m0.set(2, 2, 9.0f);

    MatrixM3x3F.copy(m0, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);
    Assert.assertTrue(m1.get(0, 2) == 3.0);

    Assert.assertTrue(m1.get(1, 0) == 4.0);
    Assert.assertTrue(m1.get(1, 1) == 5.0);
    Assert.assertTrue(m1.get(1, 2) == 6.0);

    Assert.assertTrue(m1.get(2, 0) == 7.0);
    Assert.assertTrue(m1.get(2, 1) == 8.0);
    Assert.assertTrue(m1.get(2, 2) == 9.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDeterminantIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    Assert.assertTrue(MatrixM3x3F.determinant(m) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantOther()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);

    Assert.assertTrue(MatrixM3x3F.determinant(m) == 8.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantScale()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM3x3F.determinant(m) == 2.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDeterminantScaleNegative()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM3x3F.determinant(m) == -2.0);
  }

  @SuppressWarnings("static-method") @Test public void testDeterminantZero()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.setZero(m);
    Assert.assertTrue(MatrixM3x3F.determinant(m) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public void testExchangeRows()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);

    MatrixM3x3F.exchangeRows(m0, 0, 2, m1);

    Assert.assertTrue(m1.get(0, 0) == 9.0);
    Assert.assertTrue(m1.get(0, 1) == 10.0);
    Assert.assertTrue(m1.get(0, 2) == 11.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 1.0);
    Assert.assertTrue(m1.get(2, 1) == 2.0);
    Assert.assertTrue(m1.get(2, 2) == 3.0);

    MatrixM3x3F.exchangeRowsInPlace(m1, 0, 2);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);
    Assert.assertTrue(m1.get(0, 2) == 3.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 9.0);
    Assert.assertTrue(m1.get(2, 1) == 10.0);
    Assert.assertTrue(m1.get(2, 2) == 11.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.exchangeRowsInPlace(m, 3, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.exchangeRowsInPlace(m, -1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.exchangeRowsInPlace(m, 0, 3);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.exchangeRowsInPlace(m, 0, -1);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializationFrom()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);
    m0.set(0, 2, 7.0f);

    m0.set(1, 0, 11.0f);
    m0.set(1, 1, 13.0f);
    m0.set(1, 2, 17.0f);

    m0.set(2, 0, 19.0f);
    m0.set(2, 1, 23.0f);
    m0.set(2, 2, 29.0f);

    final MatrixM3x3F m1 = new MatrixM3x3F(m0);

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);
    Assert.assertTrue(m1.get(0, 2) == 7.0);

    Assert.assertTrue(m1.get(1, 0) == 11.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);
    Assert.assertTrue(m1.get(1, 2) == 17.0);

    Assert.assertTrue(m1.get(2, 0) == 19.0);
    Assert.assertTrue(m1.get(2, 1) == 23.0);
    Assert.assertTrue(m1.get(2, 2) == 29.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializationIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);
    Assert.assertTrue(m.get(0, 2) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
    Assert.assertTrue(m.get(1, 2) == 0.0);

    Assert.assertTrue(m.get(2, 0) == 0.0);
    Assert.assertTrue(m.get(2, 1) == 0.0);
    Assert.assertTrue(m.get(2, 2) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testInvertIdentity()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3F> s = (Some<MatrixM3x3F>) r;
      final MatrixM3x3F rm = s.value;

      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 2) == 1.0);
    }

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3F> s = (Some<MatrixM3x3F>) r;
      final MatrixM3x3F rm = s.value;

      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 0, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 1, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3F.get(rm, 2, 2) == 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testInvertSimple()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);

    m0.set(2, 0, 0.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 2.0f);

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3F> s = (Some<MatrixM3x3F>) r;
      final MatrixM3x3F rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
      Assert.assertTrue(rm.get(1, 2) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 0.5);
    }

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3F> s = (Some<MatrixM3x3F>) r;
      final MatrixM3x3F rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
      Assert.assertTrue(rm.get(1, 2) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 2);
    }
  }

  @SuppressWarnings("static-method") @Test public void testInvertZero()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    MatrixM3x3F.setZero(m0);

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM3x3F> r = MatrixM3x3F.invertInPlace(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyIdentity()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    final MatrixM3x3F r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyMutateIdentity()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM3x3F r = MatrixM3x3F.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyMutateSimple()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    MatrixM3x3F.set(m0, 0, 0, 1.0f);
    MatrixM3x3F.set(m0, 0, 1, 2.0f);
    MatrixM3x3F.set(m0, 0, 2, 3.0f);

    MatrixM3x3F.set(m0, 1, 0, 4.0f);
    MatrixM3x3F.set(m0, 1, 1, 5.0f);
    MatrixM3x3F.set(m0, 1, 2, 6.0f);

    MatrixM3x3F.set(m0, 2, 0, 7.0f);
    MatrixM3x3F.set(m0, 2, 1, 8.0f);
    MatrixM3x3F.set(m0, 2, 2, 9.0f);

    final MatrixM3x3F m1 = new MatrixM3x3F(m0);
    final MatrixM3x3F r = MatrixM3x3F.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertTrue(MatrixM3x3F.get(r, 0, 0) == 30.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 0, 1) == 36.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 0, 2) == 42.0);

    Assert.assertTrue(MatrixM3x3F.get(r, 1, 0) == 66.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 1, 1) == 81.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 1, 2) == 96.0);

    Assert.assertTrue(MatrixM3x3F.get(r, 2, 0) == 102.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 2, 1) == 126.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 2, 2) == 150.0);
  }

  @SuppressWarnings("static-method") @Test public void testMultiplySimple()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    MatrixM3x3F.set(m0, 0, 0, 1.0f);
    MatrixM3x3F.set(m0, 0, 1, 2.0f);
    MatrixM3x3F.set(m0, 0, 2, 3.0f);

    MatrixM3x3F.set(m0, 1, 0, 4.0f);
    MatrixM3x3F.set(m0, 1, 1, 5.0f);
    MatrixM3x3F.set(m0, 1, 2, 6.0f);

    MatrixM3x3F.set(m0, 2, 0, 7.0f);
    MatrixM3x3F.set(m0, 2, 1, 8.0f);
    MatrixM3x3F.set(m0, 2, 2, 9.0f);

    final MatrixM3x3F m1 = new MatrixM3x3F(m0);
    final MatrixM3x3F mr = new MatrixM3x3F();

    final MatrixM3x3F r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertTrue(MatrixM3x3F.get(r, 0, 0) == 30.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 0, 1) == 36.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 0, 2) == 42.0);

    Assert.assertTrue(MatrixM3x3F.get(r, 1, 0) == 66.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 1, 1) == 81.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 1, 2) == 96.0);

    Assert.assertTrue(MatrixM3x3F.get(r, 2, 0) == 102.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 2, 1) == 126.0);
    Assert.assertTrue(MatrixM3x3F.get(r, 2, 2) == 150.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyVectorSimple()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    MatrixM3x3F.set(m0, 0, 0, 1.0f);
    MatrixM3x3F.set(m0, 0, 1, 2.0f);
    MatrixM3x3F.set(m0, 0, 2, 3.0f);

    MatrixM3x3F.set(m0, 1, 0, 4.0f);
    MatrixM3x3F.set(m0, 1, 1, 5.0f);
    MatrixM3x3F.set(m0, 1, 2, 6.0f);

    MatrixM3x3F.set(m0, 2, 0, 7.0f);
    MatrixM3x3F.set(m0, 2, 1, 8.0f);
    MatrixM3x3F.set(m0, 2, 2, 9.0f);

    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);
    final VectorM3F out = new VectorM3F();

    final VectorM3F r = MatrixM3x3F.multiplyVector3F(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 14.0);
    Assert.assertTrue(out.y == 32.0);
    Assert.assertTrue(out.z == 50.0);
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyZero()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();

    MatrixM3x3F.setZero(m1);

    final MatrixM3x3F r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    m.get(0, -1);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    m.get(-1, 0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    m.get(0, 3);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    m.get(3, 0);
  }

  @SuppressWarnings("static-method") @Test public void testRow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorM3F v = new VectorM3F();

    MatrixM3x3F.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);

    MatrixM3x3F.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);

    MatrixM3x3F.row(m, 2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public void testRowOverflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.row(m, 3, new VectorM3F());
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.row(m, -1, new VectorM3F());
  }

  @SuppressWarnings("static-method") @Test public void testScale()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    final MatrixM3x3F mk = MatrixM3x3F.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutate()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    final MatrixM3x3F mr = MatrixM3x3F.scaleInPlace(m, 5.0f);
    Assert.assertSame(mr, m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleRow()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);

    MatrixM3x3F.scaleRow(m0, 0, 2.0f, m1);
    MatrixM3x3F.scaleRow(m0, 1, 4.0f, m1);
    MatrixM3x3F.scaleRow(m0, 2, 8.0f, m1);

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);
    Assert.assertTrue(m1.get(0, 2) == 6.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);
    Assert.assertTrue(m1.get(1, 2) == 28.0);

    Assert.assertTrue(m1.get(2, 0) == 72.0);
    Assert.assertTrue(m1.get(2, 1) == 80.0);
    Assert.assertTrue(m1.get(2, 2) == 88.0);

    MatrixM3x3F.scaleRowInPlace(m0, 0, 2.0f);
    MatrixM3x3F.scaleRowInPlace(m0, 1, 4.0f);
    MatrixM3x3F.scaleRowInPlace(m0, 2, 8.0f);

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);
    Assert.assertTrue(m0.get(0, 2) == 6.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
    Assert.assertTrue(m0.get(1, 2) == 28.0);

    Assert.assertTrue(m0.get(2, 0) == 72.0);
    Assert.assertTrue(m0.get(2, 1) == 80.0);
    Assert.assertTrue(m0.get(2, 2) == 88.0);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.scaleRowInPlace(m, 3, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.scaleRowInPlace(m, -1, 1.0f);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F r = new MatrixM3x3F();
    MatrixM3x3F.scaleRow(m, 3, 1.0f, r);
  }

  @SuppressWarnings("static-method") @Test(
    expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F r = new MatrixM3x3F();
    MatrixM3x3F.scaleRow(m, -1, 1.0f, r);
  }

  @SuppressWarnings("static-method") @Test public void testSetGetIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    Assert.assertTrue(m.set(0, 0, 3.0f).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).get(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).get(0, 2) == 7.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).get(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).get(1, 2) == 19.0);

    Assert.assertTrue(m.set(2, 0, 29.0f).get(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).get(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).get(2, 2) == 37.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testSetGetInterfaceIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    Assert.assertTrue(m.set(0, 0, 3.0f).getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).getRowColumnF(0, 2) == 7.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).getRowColumnF(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).getRowColumnF(1, 2) == 19.0);

    Assert.assertTrue(m.set(2, 0, 29.0f).getRowColumnF(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).getRowColumnF(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).getRowColumnF(2, 2) == 37.0);
  }

  @SuppressWarnings("static-method") @Test public void testStorage()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    m.set(0, 0, 0);
    m.set(0, 1, 1);
    m.set(0, 2, 2);

    m.set(1, 0, 100);
    m.set(1, 1, 101);
    m.set(1, 2, 102);

    m.set(2, 0, 200);
    m.set(2, 1, 201);
    m.set(2, 2, 202);

    {
      final FloatBuffer b = MatrixM3x3F.floatBuffer(m);

      Assert.assertTrue(b.isReadOnly());
      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);
      Assert.assertTrue(b.get(2) == 200.0);

      Assert.assertTrue(b.get(3) == 1.0);
      Assert.assertTrue(b.get(4) == 101.0);
      Assert.assertTrue(b.get(5) == 201.0);

      Assert.assertTrue(b.get(6) == 2.0);
      Assert.assertTrue(b.get(7) == 102.0);
      Assert.assertTrue(b.get(8) == 202.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();
    final MatrixM3x3F m2 = new MatrixM3x3F();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslate2FMakeIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorM2F v = new VectorM2F(0, 0);

    MatrixM3x3F.makeTranslation2F(v, m);

    Assert.assertEquals(1.0f, m.get(0, 0));
    Assert.assertEquals(0.0f, m.get(0, 1));
    Assert.assertEquals(0.0f, m.get(0, 2));

    Assert.assertEquals(0.0f, m.get(1, 0));
    Assert.assertEquals(1.0f, m.get(1, 1));
    Assert.assertEquals(0.0f, m.get(1, 2));

    Assert.assertEquals(0.0f, m.get(2, 0));
    Assert.assertEquals(0.0f, m.get(2, 1));
    Assert.assertEquals(1.0f, m.get(2, 2));
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslate2FMakeSimple()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorM2F v = new VectorM2F(3, 7);

    MatrixM3x3F.makeTranslation2F(v, m);

    Assert.assertEquals(1.0f, m.get(0, 0));
    Assert.assertEquals(0.0f, m.get(0, 1));
    Assert.assertEquals(3.0f, m.get(0, 2));

    Assert.assertEquals(0.0f, m.get(1, 0));
    Assert.assertEquals(1.0f, m.get(1, 1));
    Assert.assertEquals(7.0f, m.get(1, 2));

    Assert.assertEquals(0.0f, m.get(2, 0));
    Assert.assertEquals(0.0f, m.get(2, 1));
    Assert.assertEquals(1.0f, m.get(2, 2));
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslate2IMakeIdentity()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorM2I v = new VectorM2I(0, 0);

    MatrixM3x3F.makeTranslation2I(v, m);

    Assert.assertEquals(1.0f, m.get(0, 0));
    Assert.assertEquals(0.0f, m.get(0, 1));
    Assert.assertEquals(0.0f, m.get(0, 2));

    Assert.assertEquals(0.0f, m.get(1, 0));
    Assert.assertEquals(1.0f, m.get(1, 1));
    Assert.assertEquals(0.0f, m.get(1, 2));

    Assert.assertEquals(0.0f, m.get(2, 0));
    Assert.assertEquals(0.0f, m.get(2, 1));
    Assert.assertEquals(1.0f, m.get(2, 2));
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslate2IMakeSimple()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorM2I v = new VectorM2I(3, 7);

    MatrixM3x3F.makeTranslation2I(v, m);

    Assert.assertEquals(1.0f, m.get(0, 0));
    Assert.assertEquals(0.0f, m.get(0, 1));
    Assert.assertEquals(3.0f, m.get(0, 2));

    Assert.assertEquals(0.0f, m.get(1, 0));
    Assert.assertEquals(1.0f, m.get(1, 1));
    Assert.assertEquals(7.0f, m.get(1, 2));

    Assert.assertEquals(0.0f, m.get(2, 0));
    Assert.assertEquals(0.0f, m.get(2, 1));
    Assert.assertEquals(1.0f, m.get(2, 2));
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslateSimple2F()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F out = new MatrixM3x3F();
    final VectorI2F v = new VectorI2F(1.0, 2.0);

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(1.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(2.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(2.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(4.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslateSimple2FAlt()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorI2F v = new VectorI2F(1.0, 2.0);

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(1.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(2.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(2.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(4.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslateSimple2I()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F out = new MatrixM3x3F();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(1.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(2.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(1.0f, r.get(0, 0));
      Assert.assertEquals(0.0f, r.get(0, 1));
      Assert.assertEquals(2.0f, r.get(0, 2));

      Assert.assertEquals(0.0f, r.get(1, 0));
      Assert.assertEquals(1.0f, r.get(1, 1));
      Assert.assertEquals(4.0f, r.get(1, 2));

      Assert.assertEquals(0.0f, r.get(2, 0));
      Assert.assertEquals(0.0f, r.get(2, 1));
      Assert.assertEquals(1.0f, r.get(2, 2));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testTranslateSimple2IAlt()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
    }

    {
      final MatrixM3x3F r = MatrixM3x3F.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
    }
  }

  @SuppressWarnings({ "boxing", "static-method" }) @Test public
    void
    testTranslationStorage()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F out = new MatrixM3x3F();

    MatrixM3x3F.translateByVector2F(m, new VectorI2F(1.0, 2.0), out);

    {
      final FloatBuffer b = MatrixM3x3F.floatBuffer(out);

      Assert.assertTrue(b.isReadOnly());
      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());

      Assert.assertEquals(1.0f, b.get(0));
      Assert.assertEquals(0.0f, b.get(1));
      Assert.assertEquals(0.0f, b.get(2));

      Assert.assertEquals(0.0f, b.get(3));
      Assert.assertEquals(1.0f, b.get(4));
      Assert.assertEquals(0.0f, b.get(5));

      Assert.assertEquals(1.0f, b.get(6));
      Assert.assertEquals(2.0f, b.get(7));
      Assert.assertEquals(1.0f, b.get(8));
    }
  }

  @SuppressWarnings("static-method") @Test public void testTranspose()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    final MatrixM3x3F r = new MatrixM3x3F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);

    final MatrixM3x3F k = MatrixM3x3F.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertTrue(m.get(0, 0) == 0.0);
    Assert.assertTrue(m.get(0, 1) == 1.0);
    Assert.assertTrue(m.get(0, 2) == 2.0);

    Assert.assertTrue(m.get(1, 0) == 4.0);
    Assert.assertTrue(m.get(1, 1) == 5.0);
    Assert.assertTrue(m.get(1, 2) == 6.0);

    Assert.assertTrue(m.get(2, 0) == 8.0);
    Assert.assertTrue(m.get(2, 1) == 9.0);
    Assert.assertTrue(m.get(2, 2) == 10.0);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);
    Assert.assertTrue(r.get(0, 2) == 8.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertTrue(r.get(1, 2) == 9.0);

    Assert.assertTrue(r.get(2, 0) == 2.0);
    Assert.assertTrue(r.get(2, 1) == 6.0);
    Assert.assertTrue(r.get(2, 2) == 10.0);
  }

  @SuppressWarnings("static-method") @Test public void testTransposeMutate()
  {
    final MatrixM3x3F m = new MatrixM3x3F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);

    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);

    final MatrixM3x3F r = MatrixM3x3F.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);
    Assert.assertTrue(r.get(0, 2) == 8.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertTrue(r.get(1, 2) == 9.0);

    Assert.assertTrue(r.get(2, 0) == 2.0);
    Assert.assertTrue(r.get(2, 1) == 6.0);
    Assert.assertTrue(r.get(2, 2) == 10.0);
  }

  @SuppressWarnings("static-method") @Test public void testZero()
  {
    final MatrixM3x3F m = new MatrixM3x3F();
    MatrixM3x3F.setZero(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }
  }
}
