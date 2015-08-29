package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

import junit.framework.Assert;

import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM3x3DTest
{
  @Test public void determinantIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    Assert.assertTrue(MatrixM3x3D.determinant(m) == 1.0);
  }

  @Test public void determinantOther()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);

    Assert.assertTrue(MatrixM3x3D.determinant(m) == 8.0);
  }

  @Test public void determinantScale()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM3x3D.determinant(m) == 2.0);
  }

  @Test public void determinantScaleNegative()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM3x3D.determinant(m) == -2.0);
  }

  @Test public void determinantZero()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.setZero(m);
    Assert.assertTrue(MatrixM3x3D.determinant(m) == 0.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    outOfRangeNegativeColumn()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.get(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    outOfRangeNegativeRow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.get(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    outOfRangeOverflowColumn()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.get(0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    outOfRangeOverflowRow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.get(3, 0);
  }

  @Test public void row()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v = new VectorM3D();

    MatrixM3x3D.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);

    MatrixM3x3D.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);

    MatrixM3x3D.row(m, 2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public void rowOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.row(m, 3, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    rowUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.row(m, -1, new VectorM3D());
  }

  @Test public void scale()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 3.0);
      }
    }

    final MatrixM3x3D mk = MatrixM3x3D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @Test public void scaleMutate()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.set(row, column, 3.0);
      }
    }

    final MatrixM3x3D mr = MatrixM3x3D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @Test public void setGetIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    Assert.assertTrue(m.set(0, 0, 3.0).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).get(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0).get(0, 2) == 7.0);

    Assert.assertTrue(m.set(1, 0, 13.0).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).get(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0).get(1, 2) == 19.0);

    Assert.assertTrue(m.set(2, 0, 29.0).get(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0).get(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0).get(2, 2) == 37.0);
  }

  @Test public void testAdd()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM3x3D mk = MatrixM3x3D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @Test public void testAddMutate()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    final MatrixM3x3D mr = MatrixM3x3D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }
  }

  @Test public void testAddRowScaled()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);
    m0.set(0, 2, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);
    m0.set(1, 2, 5.0);

    MatrixM3x3D.addRowScaled(m0, 0, 1, 2, 2.0, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);
    Assert.assertTrue(m1.get(0, 2) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 13.0);
    Assert.assertTrue(m1.get(2, 1) == 13.0);
    Assert.assertTrue(m1.get(2, 2) == 13.0);

    MatrixM3x3D.addRowScaledInPlace(m0, 0, 1, 2, 2.0);

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

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, 3, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, 0, 3, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, 0, 0, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.addRowScaledInPlace(m, 0, 0, -1, 1.0);
  }

  @Test public void testBufferEndianness()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final DoubleBuffer b = MatrixM3x3D.doubleBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test(expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnly()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final DoubleBuffer b = MatrixM3x3D.doubleBuffer(m);
    b.put(0, 0.0f);
  }

  @Test public void testCopy()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);

    m0.set(1, 0, 4.0);
    m0.set(1, 1, 5.0);
    m0.set(1, 2, 6.0);

    m0.set(2, 0, 7.0);
    m0.set(2, 1, 8.0);
    m0.set(2, 2, 9.0);

    MatrixM3x3D.copy(m0, m1);

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

  @Test public void testExchangeRows()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);

    MatrixM3x3D.exchangeRows(m0, 0, 2, m1);

    Assert.assertTrue(m1.get(0, 0) == 9.0);
    Assert.assertTrue(m1.get(0, 1) == 10.0);
    Assert.assertTrue(m1.get(0, 2) == 11.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 1.0);
    Assert.assertTrue(m1.get(2, 1) == 2.0);
    Assert.assertTrue(m1.get(2, 2) == 3.0);

    MatrixM3x3D.exchangeRowsInPlace(m1, 0, 2);

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

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.exchangeRowsInPlace(m, 3, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.exchangeRowsInPlace(m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.exchangeRowsInPlace(m, 0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.exchangeRowsInPlace(m, 0, -1);
  }

  @Test public void testInitializationFrom()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 5.0);
    m0.set(0, 2, 7.0);

    m0.set(1, 0, 11.0);
    m0.set(1, 1, 13.0);
    m0.set(1, 2, 17.0);

    m0.set(2, 0, 19.0);
    m0.set(2, 1, 23.0);
    m0.set(2, 2, 29.0);

    final MatrixM3x3D m1 = new MatrixM3x3D(m0);

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

  @Test public void testInitializationIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

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

  @Test public void testInvertIdentity()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    {
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.value;

      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 2) == 1.0);
    }

    {
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.value;

      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 0, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 1, 2) == 0.0);

      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM3x3D.get(rm, 2, 2) == 1.0);
    }
  }

  @Test public void testInvertSimple()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);
    m0.set(0, 2, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);
    m0.set(1, 2, 0.0);

    m0.set(2, 0, 0.0);
    m0.set(2, 1, 0.0);
    m0.set(2, 2, 2.0);

    {
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.value;

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
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.value;

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

  @Test public void testInvertZero()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    MatrixM3x3D.setZero(m0);

    {
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM3x3D> r = MatrixM3x3D.invert(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @Test public void testMultiplyIdentity()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    final MatrixM3x3D r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM3x3D r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @Test public void testMultiplyMutateSimple()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();

    MatrixM3x3D.set(m0, 0, 0, 1.0);
    MatrixM3x3D.set(m0, 0, 1, 2.0);
    MatrixM3x3D.set(m0, 0, 2, 3.0);

    MatrixM3x3D.set(m0, 1, 0, 4.0);
    MatrixM3x3D.set(m0, 1, 1, 5.0);
    MatrixM3x3D.set(m0, 1, 2, 6.0);

    MatrixM3x3D.set(m0, 2, 0, 7.0);
    MatrixM3x3D.set(m0, 2, 1, 8.0);
    MatrixM3x3D.set(m0, 2, 2, 9.0);

    final MatrixM3x3D m1 = new MatrixM3x3D(m0);
    final MatrixM3x3D r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertTrue(MatrixM3x3D.get(r, 0, 0) == 30.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 0, 1) == 36.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 0, 2) == 42.0);

    Assert.assertTrue(MatrixM3x3D.get(r, 1, 0) == 66.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 1, 1) == 81.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 1, 2) == 96.0);

    Assert.assertTrue(MatrixM3x3D.get(r, 2, 0) == 102.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 2, 1) == 126.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 2, 2) == 150.0);
  }

  @Test public void testMultiplySimple()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();

    MatrixM3x3D.set(m0, 0, 0, 1.0);
    MatrixM3x3D.set(m0, 0, 1, 2.0);
    MatrixM3x3D.set(m0, 0, 2, 3.0);

    MatrixM3x3D.set(m0, 1, 0, 4.0);
    MatrixM3x3D.set(m0, 1, 1, 5.0);
    MatrixM3x3D.set(m0, 1, 2, 6.0);

    MatrixM3x3D.set(m0, 2, 0, 7.0);
    MatrixM3x3D.set(m0, 2, 1, 8.0);
    MatrixM3x3D.set(m0, 2, 2, 9.0);

    final MatrixM3x3D m1 = new MatrixM3x3D(m0);
    final MatrixM3x3D mr = new MatrixM3x3D();

    final MatrixM3x3D r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertTrue(MatrixM3x3D.get(r, 0, 0) == 30.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 0, 1) == 36.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 0, 2) == 42.0);

    Assert.assertTrue(MatrixM3x3D.get(r, 1, 0) == 66.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 1, 1) == 81.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 1, 2) == 96.0);

    Assert.assertTrue(MatrixM3x3D.get(r, 2, 0) == 102.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 2, 1) == 126.0);
    Assert.assertTrue(MatrixM3x3D.get(r, 2, 2) == 150.0);
  }

  @Test public void testMultiplyVectorSimple()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();

    MatrixM3x3D.set(m0, 0, 0, 1.0);
    MatrixM3x3D.set(m0, 0, 1, 2.0);
    MatrixM3x3D.set(m0, 0, 2, 3.0);

    MatrixM3x3D.set(m0, 1, 0, 4.0);
    MatrixM3x3D.set(m0, 1, 1, 5.0);
    MatrixM3x3D.set(m0, 1, 2, 6.0);

    MatrixM3x3D.set(m0, 2, 0, 7.0);
    MatrixM3x3D.set(m0, 2, 1, 8.0);
    MatrixM3x3D.set(m0, 2, 2, 9.0);

    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);
    final VectorM3D out = new VectorM3D();

    final VectorM3D r = MatrixM3x3D.multiplyVector3(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 14.0);
    Assert.assertTrue(out.y == 32.0);
    Assert.assertTrue(out.z == 50.0);
  }

  @Test public void testMultiplyZero()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();

    MatrixM3x3D.setZero(m1);

    final MatrixM3x3D r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @Test public void testScaleRow()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);

    MatrixM3x3D.scaleRow(m0, 0, 2.0, m1);
    MatrixM3x3D.scaleRow(m0, 1, 4.0, m1);
    MatrixM3x3D.scaleRow(m0, 2, 8.0, m1);

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);
    Assert.assertTrue(m1.get(0, 2) == 6.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);
    Assert.assertTrue(m1.get(1, 2) == 28.0);

    Assert.assertTrue(m1.get(2, 0) == 72.0);
    Assert.assertTrue(m1.get(2, 1) == 80.0);
    Assert.assertTrue(m1.get(2, 2) == 88.0);

    MatrixM3x3D.scaleRow(m0, 0, 2.0);
    MatrixM3x3D.scaleRow(m0, 1, 4.0);
    MatrixM3x3D.scaleRow(m0, 2, 8.0);

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

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.scaleRow(m, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.scaleRow(m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D r = new MatrixM3x3D();
    MatrixM3x3D.scaleRow(m, 3, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D r = new MatrixM3x3D();
    MatrixM3x3D.scaleRow(m, -1, 1.0, r);
  }

  @Test public void testStorage()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

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
      final DoubleBuffer b = MatrixM3x3D.doubleBuffer(m);

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

  @Test public void testString()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    final MatrixM3x3D m2 = new MatrixM3x3D();
    m2.set(0, 0, 2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @Test public void transpose()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D r = new MatrixM3x3D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);
    m.set(0, 2, 2.0);
    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);
    m.set(1, 2, 6.0);
    m.set(2, 0, 8.0);
    m.set(2, 1, 9.0);
    m.set(2, 2, 10.0);

    final MatrixM3x3D k = MatrixM3x3D.transpose(m, r);
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

  @Test public void transposeMutate()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);
    m.set(0, 2, 2.0);

    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);
    m.set(1, 2, 6.0);

    m.set(2, 0, 8.0);
    m.set(2, 1, 9.0);
    m.set(2, 2, 10.0);

    final MatrixM3x3D r = MatrixM3x3D.transposeInPlace(m);
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

  @Test public void zero()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.setZero(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }
  }
}
