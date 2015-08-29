package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

import junit.framework.Assert;

import org.junit.Test;

import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM2x2FTest
{
  @Test public void testAdd()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();
    final MatrixM2x2F mr = new MatrixM2x2F();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2F mk = MatrixM2x2F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }
  }

  @Test public void testAddMutate()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2F mr = MatrixM2x2F.add(m0, m1);
    Assert.assertSame(mr, m0);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }
  }

  @Test public void testAddRowScaled()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);

    MatrixM2x2F.addRowScaled(m0, 0, 1, 1, 2.0f, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 13.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);

    MatrixM2x2F.addRowScaled(m0, 0, 1, 1, 2.0f);

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 13.0);
    Assert.assertTrue(m0.get(1, 1) == 13.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, 2, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, 0, 2, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, 0, 0, 2, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, -1, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, 0, -1, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.addRowScaled(m, 0, 0, -1, 1.0f);
  }

  @Test public void testBufferEndianness()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final FloatBuffer b = MatrixM2x2F.floatBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test(expected = ReadOnlyBufferException.class) public
    void
    testBufferReadOnly()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final FloatBuffer b = MatrixM2x2F.floatBuffer(m);
    b.put(0, 0.0f);
  }

  @Test public void testCopy()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);

    m0.set(1, 0, 4.0f);
    m0.set(1, 1, 5.0f);

    MatrixM2x2F.copy(m0, m1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 4.0);
    Assert.assertTrue(m1.get(1, 1) == 5.0);
  }

  @Test public void testDeterminantIdentity()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    Assert.assertTrue(MatrixM2x2F.determinant(m) == 1.0);
  }

  @Test public void testDeterminantOther()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);

    Assert.assertTrue(MatrixM2x2F.determinant(m) == 4.0);
  }

  @Test public void testDeterminantScale()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    m.set(0, 0, 2.0f);

    Assert.assertTrue(MatrixM2x2F.determinant(m) == 2.0);
  }

  @Test public void testDeterminantScaleNegative()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    m.set(0, 0, -2.0f);

    Assert.assertTrue(MatrixM2x2F.determinant(m) == -2.0);
  }

  @Test public void testDeterminantZero()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.setZero(m);
    Assert.assertTrue(MatrixM2x2F.determinant(m) == 0.0);
  }

  @Test public void testExchangeRows()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(1, 0, 3.0f);
    m0.set(1, 1, 4.0f);

    MatrixM2x2F.exchangeRows(m0, 0, 1, m1);

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 1.0);
    Assert.assertTrue(m1.get(1, 1) == 2.0);

    MatrixM2x2F.exchangeRows(m1, 0, 1);

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);

    Assert.assertTrue(m1.get(1, 0) == 3.0);
    Assert.assertTrue(m1.get(1, 1) == 4.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.exchangeRows(m, 2, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.exchangeRows(m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.exchangeRows(m, 0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.exchangeRows(m, 0, -1);
  }

  @Test public void testInitializationFrom()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);

    m0.set(1, 0, 11.0f);
    m0.set(1, 1, 13.0f);

    final MatrixM2x2F m1 = new MatrixM2x2F(m0);

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);

    Assert.assertTrue(m1.get(1, 0) == 11.0);
    Assert.assertTrue(m1.get(1, 1) == 13.0);
  }

  @Test public void testInitializationIdentity()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
  }

  @Test public void testInvertIdentity()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2F> s = (Some<MatrixM2x2F>) r;
      final MatrixM2x2F rm = s.value;

      Assert.assertTrue(MatrixM2x2F.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2F.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2F.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2F.get(rm, 1, 1) == 1.0);
    }

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2F> s = (Some<MatrixM2x2F>) r;
      final MatrixM2x2F rm = s.value;

      Assert.assertTrue(MatrixM2x2F.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM2x2F.get(rm, 0, 1) == 0.0);

      Assert.assertTrue(MatrixM2x2F.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM2x2F.get(rm, 1, 1) == 1.0);
    }
  }

  @Test public void testInvertSimple()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2F> s = (Some<MatrixM2x2F>) r;
      final MatrixM2x2F rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
    }

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM2x2F> s = (Some<MatrixM2x2F>) r;
      final MatrixM2x2F rm = s.value;

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
    }
  }

  @Test public void testInvertZero()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    MatrixM2x2F.setZero(m0);

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM2x2F> r = MatrixM2x2F.invert(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @Test public void testMultiplyIdentity()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();
    final MatrixM2x2F mr = new MatrixM2x2F();
    final MatrixM2x2F r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
      }
    }
  }

  @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    final MatrixM2x2F r = MatrixM2x2F.multiply(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }
  }

  @Test public void testMultiplyMutateSimple()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();

    MatrixM2x2F.set(m0, 0, 0, 1.0f);
    MatrixM2x2F.set(m0, 0, 1, 2.0f);
    MatrixM2x2F.set(m0, 1, 0, 3.0f);
    MatrixM2x2F.set(m0, 1, 1, 4.0f);

    final MatrixM2x2F m1 = new MatrixM2x2F(m0);
    final MatrixM2x2F r = MatrixM2x2F.multiply(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertTrue(MatrixM2x2F.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 1, 1) == 22.0);
  }

  @Test public void testMultiplySimple()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();

    MatrixM2x2F.set(m0, 0, 0, 1.0f);
    MatrixM2x2F.set(m0, 0, 1, 2.0f);
    MatrixM2x2F.set(m0, 1, 0, 3.0f);
    MatrixM2x2F.set(m0, 1, 1, 4.0f);

    final MatrixM2x2F m1 = new MatrixM2x2F(m0);
    final MatrixM2x2F mr = new MatrixM2x2F();

    final MatrixM2x2F r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertTrue(MatrixM2x2F.get(r, 0, 0) == 7.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 0, 1) == 10.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 1, 0) == 15.0);
    Assert.assertTrue(MatrixM2x2F.get(r, 1, 1) == 22.0);
  }

  @Test public void testMultiplyVectorSimple()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();

    MatrixM2x2F.set(m0, 0, 0, 1.0f);
    MatrixM2x2F.set(m0, 0, 1, 2.0f);
    MatrixM2x2F.set(m0, 1, 0, 3.0f);
    MatrixM2x2F.set(m0, 1, 1, 4.0f);

    final VectorI2F v = new VectorI2F(1.0, 2.0);
    final VectorM2F out = new VectorM2F();

    final VectorM2F r = MatrixM2x2F.multiply(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 5.0);
    Assert.assertTrue(out.y == 11.0);
  }

  @Test public void testMultiplyZero()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();
    final MatrixM2x2F mr = new MatrixM2x2F();

    MatrixM2x2F.setZero(m1);

    final MatrixM2x2F r = MatrixM2x2F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    m.get(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    m.get(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    m.get(0, 2);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    m.get(2, 0);
  }

  @Test public void testRow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final VectorM2F v = new VectorM2F();

    MatrixM2x2F.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);

    MatrixM2x2F.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.row(m, 3, new VectorM2F());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.row(m, -1, new VectorM2F());
  }

  @Test public void testScale()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F mr = new MatrixM2x2F();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2F mk = MatrixM2x2F.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @Test public void testScaleMutate()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    final MatrixM2x2F mr = MatrixM2x2F.scale(m, 5.0f);
    Assert.assertSame(mr, m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }
  }

  @Test public void testScaleRow()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);

    MatrixM2x2F.scaleRow(m0, 0, 2.0f, m1);
    MatrixM2x2F.scaleRow(m0, 1, 4.0f, m1);

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);

    MatrixM2x2F.scaleRow(m0, 0, 2.0f);
    MatrixM2x2F.scaleRow(m0, 1, 4.0f);

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.scaleRow(m, 2, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.scaleRow(m, -1, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final MatrixM2x2F r = new MatrixM2x2F();
    MatrixM2x2F.scaleRow(m, 2, 1.0f, r);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final MatrixM2x2F r = new MatrixM2x2F();
    MatrixM2x2F.scaleRow(m, -1, 1.0f, r);
  }

  @Test public void testSetGetIdentity()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    Assert.assertTrue(m.set(0, 0, 3.0f).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).get(0, 1) == 5.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).get(1, 1) == 17.0);
  }

  @Test public void testStorage()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    m.set(0, 0, 0);
    m.set(0, 1, 1);

    m.set(1, 0, 100);
    m.set(1, 1, 101);

    {
      final FloatBuffer b = MatrixM2x2F.floatBuffer(m);

      Assert.assertTrue(b.isReadOnly());
      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);

      Assert.assertTrue(b.get(2) == 1.0);
      Assert.assertTrue(b.get(3) == 101.0);
    }
  }

  @Test public void testString()
  {
    final MatrixM2x2F m0 = new MatrixM2x2F();
    final MatrixM2x2F m1 = new MatrixM2x2F();
    final MatrixM2x2F m2 = new MatrixM2x2F();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @Test public void testTranspose()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    final MatrixM2x2F r = new MatrixM2x2F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);

    final MatrixM2x2F k = MatrixM2x2F.transpose(m, r);
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

  @Test public void testTransposeMutate()
  {
    final MatrixM2x2F m = new MatrixM2x2F();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);

    final MatrixM2x2F r = MatrixM2x2F.transpose(m);
    Assert.assertSame(m, r);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);

    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
  }

  @Test public void testZero()
  {
    final MatrixM2x2F m = new MatrixM2x2F();
    MatrixM2x2F.setZero(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }
  }
}
