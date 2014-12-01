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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM3x3D.Context;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable3DType;

@SuppressWarnings("static-method") public class MatrixM3x3DTest
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(1, 0, 0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(0, 1, 0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(0, 0, 1);

  private static void isRotationMatrixX(
    final AlmostEqualDouble.ContextRelative context,
    final MatrixM3x3D r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        -0.707106781187,
        r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixY(
    final AlmostEqualDouble.ContextRelative context,
    final MatrixM3x3D r)
  {
    boolean eq;
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualDouble.almostEqual(
        context,
        -0.707106781187,
        r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context,
        0.707106781187,
        r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixZ(
    final AlmostEqualDouble.ContextRelative context_d,
    final MatrixM3x3D r)
  {
    boolean eq;
    eq =
      AlmostEqualDouble.almostEqual(
        context_d,
        0.707106781187,
        r.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context_d,
        -0.707106781187,
        r.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualDouble.almostEqual(
        context_d,
        0.707106781187,
        r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(
        context_d,
        0.707106781187,
        r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
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
    Assert.assertEquals(0, mk.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 1.0);
        Assert.assertTrue(m1.getRowColumnD(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 4.0);
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    final MatrixM3x3D mr = MatrixM3x3D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 4.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 4.0);
        Assert.assertTrue(m1.getRowColumnD(row, column) == 3.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.addRowScaled(m0, 0, 1, 2, 2.0, m1);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 13.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.addRowScaledInPlace(m0, 0, 1, 2, 2.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m0.getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m0.getRowColumnD(0, 1) == 3.0);
    Assert.assertTrue(m0.getRowColumnD(0, 2) == 3.0);

    Assert.assertTrue(m0.getRowColumnD(1, 0) == 5.0);
    Assert.assertTrue(m0.getRowColumnD(1, 1) == 5.0);
    Assert.assertTrue(m0.getRowColumnD(1, 2) == 5.0);

    Assert.assertTrue(m0.getRowColumnD(2, 0) == 13.0);
    Assert.assertTrue(m0.getRowColumnD(2, 1) == 13.0);
    Assert.assertTrue(m0.getRowColumnD(2, 2) == 13.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
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
    final DoubleBuffer b = m.getDirectDoubleBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.copy(m0, m1);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 3.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 4.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 5.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 6.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 7.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 8.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 9.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
  }

  @Test public void testDeterminantIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    Assert.assertTrue(MatrixM3x3D.determinant(m) == 1.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testDeterminantOther()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertTrue(MatrixM3x3D.determinant(m) == 8.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testDeterminantScale()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, 2.0f);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertTrue(MatrixM3x3D.determinant(m) == 2.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testDeterminantScaleNegative()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    m.set(0, 0, -2.0f);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertTrue(MatrixM3x3D.determinant(m) == -2.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testDeterminantZero()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.setZero(m);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertTrue(MatrixM3x3D.determinant(m) == 0.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testEqualsCase0()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test public void testEqualsCase1()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertFalse(m0.equals(null));
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
  }

  @Test public void testEqualsCase2()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
  }

  @Test public void testEqualsCase3()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    Assert.assertTrue(m0.equals(m1));
    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
  }

  @Test public void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final MatrixM3x3D m0 = new MatrixM3x3D();
        final MatrixM3x3D m1 = new MatrixM3x3D();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
        Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      }
    }
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.exchangeRows(m0, 0, 2, m1);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 9.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 10.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 11.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 3.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.exchangeRowsInPlace(m1, 0, 2);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 3.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 9.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 10.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 11.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
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

  @Test public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final MatrixM3x3D m0 = new MatrixM3x3D();
        final MatrixM3x3D m1 = new MatrixM3x3D();
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
        Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
        Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
        Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      }
    }
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    final MatrixM3x3D m1 = new MatrixM3x3D(m0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 5.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 7.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 11.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 17.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 19.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 23.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 29.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
  }

  @Test public void testInitializationIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    Assert.assertTrue(m.getRowColumnD(0, 0) == 1.0);
    Assert.assertTrue(m.getRowColumnD(0, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnD(0, 2) == 0.0);

    Assert.assertTrue(m.getRowColumnD(1, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnD(1, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnD(1, 2) == 0.0);

    Assert.assertTrue(m.getRowColumnD(2, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnD(2, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnD(2, 2) == 1.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testInvertInPlaceIdentity()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    {
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.get();

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(0, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(1, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(2, 2) == 1.0);

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());
    }

    {
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.get();

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(0, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnD(1, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnD(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnD(2, 2) == 1.0);

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testInvertSimpleND()
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
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.get();

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 0.5);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnD(0, 2) == 0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 0.5);
      Assert.assertTrue(rm.getRowColumnD(1, 2) == 0);

      Assert.assertTrue(rm.getRowColumnD(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnD(2, 2) == 0.5);

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());
    }

    {
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<MatrixM3x3D> s = (Some<MatrixM3x3D>) r;
      final MatrixM3x3D rm = s.get();

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());

      Assert.assertTrue(rm.getRowColumnD(0, 0) == 2);
      Assert.assertTrue(rm.getRowColumnD(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnD(0, 2) == 0);

      Assert.assertTrue(rm.getRowColumnD(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(1, 1) == 2);
      Assert.assertTrue(rm.getRowColumnD(1, 2) == 0);

      Assert.assertTrue(rm.getRowColumnD(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnD(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnD(2, 2) == 2);

      Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, rm.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testInvertZeroND()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    MatrixM3x3D.setZero(m0);

    {
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invert(m0, m1);
      Assert.assertTrue(r.isNone());
    }

    {
      final OptionType<MatrixM3x3D> r = MatrixM3x3D.invertInPlace(m0);
      Assert.assertTrue(r.isNone());
    }
  }

  @Test public void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.Context mc = new MatrixM3x3D.Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D t = new VectorM3D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(-1, 0, 0);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM3x3D.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getZD());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.Context mc = new MatrixM3x3D.Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D t = new VectorM3D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(0, 0, -1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM3x3D.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getZD());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.Context mc = new MatrixM3x3D.Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D t = new VectorM3D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(1, 0, 0);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM3x3D.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getZD());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.Context mc = new MatrixM3x3D.Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D t = new VectorM3D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(0, 0, 1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM3x3D.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.getRowColumnD(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, t.getZD());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.Context mc = new MatrixM3x3D.Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D t = new VectorM3D();
    final VectorI3D origin = new VectorI3D(20 + 0, 30 + 0, 40 + 0);
    final VectorI3D target = new VectorI3D(20 + 0, 30 + 0, 40 + -1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM3x3D.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, -20.0, t.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -30.0, t.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -40.0, t.getZD());
    Assert.assertTrue(eq);
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
        Assert.assertTrue(m0.getRowColumnD(row, column) == mr.getRowColumnD(
          row,
          column));
        Assert.assertTrue(m1.getRowColumnD(row, column) == mr.getRowColumnD(
          row,
          column));
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
  }

  @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == m1.getRowColumnD(
          row,
          column));
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    final MatrixM3x3D r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == m1.getRowColumnD(
          row,
          column));
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    final MatrixM3x3D m1 = new MatrixM3x3D(m0);
    final MatrixM3x3D r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 30.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 36.0);
    Assert.assertTrue(r.getRowColumnD(0, 2) == 42.0);

    Assert.assertTrue(r.getRowColumnD(1, 0) == 66.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 81.0);
    Assert.assertTrue(r.getRowColumnD(1, 2) == 96.0);

    Assert.assertTrue(r.getRowColumnD(2, 0) == 102.0);
    Assert.assertTrue(r.getRowColumnD(2, 1) == 126.0);
    Assert.assertTrue(r.getRowColumnD(2, 2) == 150.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    final MatrixM3x3D r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 30.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 36.0);
    Assert.assertTrue(r.getRowColumnD(0, 2) == 42.0);

    Assert.assertTrue(r.getRowColumnD(1, 0) == 66.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 81.0);
    Assert.assertTrue(r.getRowColumnD(1, 2) == 96.0);

    Assert.assertTrue(r.getRowColumnD(2, 0) == 102.0);
    Assert.assertTrue(r.getRowColumnD(2, 1) == 126.0);
    Assert.assertTrue(r.getRowColumnD(2, 2) == 150.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);
    final VectorM3D out = new VectorM3D();

    final VectorM3D r = MatrixM3x3D.multiplyVector3D(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.getXD() == 14.0);
    Assert.assertTrue(out.getYD() == 32.0);
    Assert.assertTrue(out.getZD() == 50.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
  }

  @Test public void testMultiplyZero()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D m1 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();

    MatrixM3x3D.setZero(m1);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    final MatrixM3x3D r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(mr.getRowColumnD(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRowColumnD(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRowColumnD(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRowColumnD(0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRowColumnD(3, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D mt = new MatrixM3x3D();
    final MatrixM3x3D mi = new MatrixM3x3D();
    final VectorM3D axis = new VectorM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      double angle = Math.random() * (2 * Math.PI);
      axis.set3D(Math.random(), Math.random(), Math.random());

      if (Math.random() > 0.5) {
        angle = -angle;
      }
      if (Math.random() > 0.5) {
        axis.setXD(-axis.getXD());
      }
      if (Math.random() > 0.5) {
        axis.setYD(-axis.getYD());
      }
      if (Math.random() > 0.5) {
        axis.setZD(-axis.getZD());
      }
      VectorM3D.normalizeInPlace(axis);

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      MatrixM3x3D.makeRotationInto(angle, axis, m);

      final double det = MatrixM3x3D.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM3x3D.invert(m, mi);
      MatrixM3x3D.transpose(m, mt);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final double mx = mi.getRowColumnD(row, col);
          final double my = mt.getRowColumnD(row, col);
          final boolean eq = AlmostEqualDouble.almostEqual(context, mx, my);

          System.out.println("mi(" + row + ", " + col + ") == " + mx);
          System.out.println("mt(" + row + ", " + col + ") == " + my);
          System.out.println(eq);

          Assert.assertTrue(eq);
        }
      }

      System.out.println("--");
    }
  }

  /**
   * A rotation of 0 degrees around the X axis has no effect.
   */

  @Test public void testRotateVector0X()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_in = new VectorM3D(0, 0, -1);
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_exp = new VectorM3D(0, 0, -1);

    MatrixM3x3D.makeRotationInto(0, MatrixM3x3DTest.AXIS_X, m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public void testRotateVector0Y()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_in = new VectorM3D(0, 0, -1);
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_exp = new VectorM3D(0, 0, -1);

    MatrixM3x3D.makeRotationInto(0, MatrixM3x3DTest.AXIS_Y, m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public void testRotateVector0Z()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_in = new VectorM3D(0, 0, -1);
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_exp = new VectorM3D(0, 0, -1);

    MatrixM3x3D.makeRotationInto(0, MatrixM3x3DTest.AXIS_Z, m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90X()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 1, 0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM3D v_exp = new VectorM3D(0, 6.1232339957367E-17, 1);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(90),
      MatrixM3x3DTest.AXIS_X,
      m);
    System.out.println(m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Y axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90Y()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 0, -1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM3D v_exp = new VectorM3D(-1, 0, -6.1232339957367E-17);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(90),
      MatrixM3x3DTest.AXIS_Y,
      m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Z axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90Z()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 1, 0);
    final VectorM3D v_exp = new VectorM3D(-1, 6.123233995736766E-17, 0);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(90),
      MatrixM3x3DTest.AXIS_Z,
      m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the X axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90X()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 1, 0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM3D v_exp = new VectorM3D(0, 6.1232339957367E-17, -1);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(-90),
      MatrixM3x3DTest.AXIS_X,
      m);
    System.out.println(m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Y axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90Y()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 0, -1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM3D v_exp = new VectorM3D(1, 0, -6.1232339957367E-17);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(-90),
      MatrixM3x3DTest.AXIS_Y,
      m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Z axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90Z()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v_got = new VectorM3D();
    final VectorM3D v_in = new VectorM3D(0, 1, 0);
    final VectorM3D v_exp = new VectorM3D(1, 6.123233995736766E-17, 0);

    MatrixM3x3D.makeRotationInto(
      Math.toRadians(-90),
      MatrixM3x3DTest.AXIS_Z,
      m);
    MatrixM3x3D.multiplyVector3D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  @Test public void testRotateX()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    {
      final MatrixM3x3D out = new MatrixM3x3D();
      final MatrixM3x3D r =
        MatrixM3x3D
          .rotate(Math.toRadians(45), m, MatrixM3x3DTest.AXIS_X, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, out.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_X);
      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateXContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    final Context context = new Context();
    {
      final MatrixM3x3D out = new MatrixM3x3D();

      final MatrixM3x3D r =
        MatrixM3x3D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_X,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, out.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixX(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_X);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      MatrixM3x3DTest.isRotationMatrixX(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateXMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM3x3D r =
        MatrixM3x3D.makeRotation(Math.toRadians(45), MatrixM3x3DTest.AXIS_X);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixX(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateY()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    {
      final MatrixM3x3D out = new MatrixM3x3D();
      final MatrixM3x3D r =
        MatrixM3x3D
          .rotate(Math.toRadians(45), m, MatrixM3x3DTest.AXIS_Y, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Y);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateYContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final Context context = new Context();
    final MatrixM3x3D m = new MatrixM3x3D();
    {
      final MatrixM3x3D out = new MatrixM3x3D();
      final MatrixM3x3D r =
        MatrixM3x3D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Y,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixY(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Y);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixY(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateYMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM3x3D r =
        MatrixM3x3D.makeRotation(Math.toRadians(45), MatrixM3x3DTest.AXIS_Y);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateZ()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    {
      final MatrixM3x3D out = new MatrixM3x3D();
      final MatrixM3x3D r =
        MatrixM3x3D
          .rotate(Math.toRadians(45), m, MatrixM3x3DTest.AXIS_Z, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Z);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateZContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final Context context = new Context();
    final MatrixM3x3D m = new MatrixM3x3D();

    {
      final MatrixM3x3D out = new MatrixM3x3D();
      final MatrixM3x3D r =
        MatrixM3x3D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Z,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r =
        MatrixM3x3D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM3x3DTest.AXIS_Z);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRotateZMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM3x3D r =
        MatrixM3x3D.makeRotation(Math.toRadians(45), MatrixM3x3DTest.AXIS_Z);
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      System.out.println(r);

      MatrixM3x3DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testRow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v = new VectorM3D();

    MatrixM3x3D.row(m, 0, v);
    Assert.assertTrue(v.getXD() == 1.0);
    Assert.assertTrue(v.getYD() == 0.0);
    Assert.assertTrue(v.getZD() == 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    MatrixM3x3D.row(m, 1, v);
    Assert.assertTrue(v.getXD() == 0.0);
    Assert.assertTrue(v.getYD() == 1.0);
    Assert.assertTrue(v.getZD() == 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    MatrixM3x3D.row(m, 2, v);
    Assert.assertTrue(v.getXD() == 0.0);
    Assert.assertTrue(v.getYD() == 0.0);
    Assert.assertTrue(v.getZD() == 1.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testRow3D()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM3D v = new VectorM3D();
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.getRow3D(0, v);
    Assert.assertTrue(v.getXD() == 1.0);
    Assert.assertTrue(v.getYD() == 0.0);
    Assert.assertTrue(v.getZD() == 0.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.getRow3D(1, v);
    Assert.assertTrue(v.getXD() == 0.0);
    Assert.assertTrue(v.getYD() == 1.0);
    Assert.assertTrue(v.getZD() == 0.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.getRow3D(2, v);
    Assert.assertTrue(v.getXD() == 0.0);
    Assert.assertTrue(v.getYD() == 0.0);
    Assert.assertTrue(v.getZD() == 1.0);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow3DOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRow3D(4, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow3DUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    m.getRow3D(-1, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.row(m, 3, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.row(m, -1, new VectorM3D());
  }

  @Test public void testScale()
  {
    final MatrixM3x3D m0 = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 3.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    final MatrixM3x3D mk = MatrixM3x3D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnD(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
  }

  @Test public void testScaleMutate()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.set(row, column, 3.0);
      }
    }

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    final MatrixM3x3D mr = MatrixM3x3D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.getRowColumnD(row, column) == 15.0);
        Assert.assertTrue(mr.getRowColumnD(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, mr.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());

    MatrixM3x3D.scaleRow(m0, 0, 2.0, m1);
    MatrixM3x3D.scaleRow(m0, 1, 4.0, m1);
    MatrixM3x3D.scaleRow(m0, 2, 8.0, m1);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m1.getRowColumnD(0, 0) == 2.0);
    Assert.assertTrue(m1.getRowColumnD(0, 1) == 4.0);
    Assert.assertTrue(m1.getRowColumnD(0, 2) == 6.0);

    Assert.assertTrue(m1.getRowColumnD(1, 0) == 20.0);
    Assert.assertTrue(m1.getRowColumnD(1, 1) == 24.0);
    Assert.assertTrue(m1.getRowColumnD(1, 2) == 28.0);

    Assert.assertTrue(m1.getRowColumnD(2, 0) == 72.0);
    Assert.assertTrue(m1.getRowColumnD(2, 1) == 80.0);
    Assert.assertTrue(m1.getRowColumnD(2, 2) == 88.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    MatrixM3x3D.scaleRowInPlace(m0, 0, 2.0);
    MatrixM3x3D.scaleRowInPlace(m0, 1, 4.0);
    MatrixM3x3D.scaleRowInPlace(m0, 2, 8.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());

    Assert.assertTrue(m0.getRowColumnD(0, 0) == 2.0);
    Assert.assertTrue(m0.getRowColumnD(0, 1) == 4.0);
    Assert.assertTrue(m0.getRowColumnD(0, 2) == 6.0);

    Assert.assertTrue(m0.getRowColumnD(1, 0) == 20.0);
    Assert.assertTrue(m0.getRowColumnD(1, 1) == 24.0);
    Assert.assertTrue(m0.getRowColumnD(1, 2) == 28.0);

    Assert.assertTrue(m0.getRowColumnD(2, 0) == 72.0);
    Assert.assertTrue(m0.getRowColumnD(2, 1) == 80.0);
    Assert.assertTrue(m0.getRowColumnD(2, 2) == 88.0);

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.scaleRowInPlace(m, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.scaleRowInPlace(m, -1, 1.0);
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

  @Test public void testSetGetIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0).getRowColumnD(0, 2) == 7.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0).getRowColumnD(1, 2) == 19.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.set(2, 0, 29.0).getRowColumnD(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0).getRowColumnD(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0).getRowColumnD(2, 2) == 37.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testSetGetInterfaceIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.setRowColumnD(0, 0, 3.0);
    Assert.assertTrue(m.getRowColumnD(0, 0) == 3.0);
    m.setRowColumnD(0, 1, 5.0);
    Assert.assertTrue(m.getRowColumnD(0, 1) == 5.0);
    m.setRowColumnD(0, 2, 7.0);
    Assert.assertTrue(m.getRowColumnD(0, 2) == 7.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.setRowColumnD(1, 0, 13.0);
    Assert.assertTrue(m.getRowColumnD(1, 0) == 13.0);
    m.setRowColumnD(1, 1, 17.0);
    Assert.assertTrue(m.getRowColumnD(1, 1) == 17.0);
    m.setRowColumnD(1, 2, 19.0);
    Assert.assertTrue(m.getRowColumnD(1, 2) == 19.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    m.setRowColumnD(2, 0, 29.0);
    Assert.assertTrue(m.getRowColumnD(2, 0) == 29.0);
    m.setRowColumnD(2, 1, 31.0);
    Assert.assertTrue(m.getRowColumnD(2, 1) == 31.0);
    m.setRowColumnD(2, 2, 37.0);
    Assert.assertTrue(m.getRowColumnD(2, 2) == 37.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
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

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    {
      final DoubleBuffer b = m.getDirectDoubleBuffer();

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

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

    Assert.assertEquals(0, m0.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, m1.getDirectDoubleBuffer().position());
  }

  @Test public void testTrace()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    Assert.assertEquals(3.0, MatrixM3x3D.trace(m), 0.0);
  }

  @Test public void testTranslate2DMakeIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM2D v = new VectorM2D(0, 0);

    MatrixM3x3D.makeTranslation2D(v, m);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testTranslate2DMakeSimple()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM2D v = new VectorM2D(3, 7);

    MatrixM3x3D.makeTranslation2D(v, m);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(7.0, m.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testTranslate2IMakeIdentity()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM2I v = new VectorM2I(0, 0);

    MatrixM3x3D.makeTranslation2I(v, m);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testTranslate2IMakeSimple()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorM2I v = new VectorM2I(3, 7);

    MatrixM3x3D.makeTranslation2I(v, m);
    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(7.0, m.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }

  @Test public void testTranslateSimple2D()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D out = new MatrixM3x3D();
    final VectorI2D v = new VectorI2D(1.0, 2.0);

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(4.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testTranslateSimple2DAlt()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorI2D v = new VectorI2D(1.0, 2.0);

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(4.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testTranslateSimple2I()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D out = new MatrixM3x3D();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(4.0, r.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testTranslateSimple2IAlt()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertTrue(r.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(r.getRowColumnD(0, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnD(0, 2) == 1.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertTrue(r.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnD(1, 1) == 1.0);
      Assert.assertTrue(r.getRowColumnD(1, 2) == 2.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertTrue(r.getRowColumnD(2, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnD(2, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnD(2, 2) == 1.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }

    {
      final MatrixM3x3D r = MatrixM3x3D.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertTrue(r.getRowColumnD(0, 0) == 1.0);
      Assert.assertTrue(r.getRowColumnD(0, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnD(0, 2) == 2.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertTrue(r.getRowColumnD(1, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnD(1, 1) == 1.0);
      Assert.assertTrue(r.getRowColumnD(1, 2) == 4.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

      Assert.assertTrue(r.getRowColumnD(2, 0) == 0.0);
      Assert.assertTrue(r.getRowColumnD(2, 1) == 0.0);
      Assert.assertTrue(r.getRowColumnD(2, 2) == 1.0);

      Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
      Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
    }
  }

  @Test public void testTranslationStorage()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    final MatrixM3x3D out = new MatrixM3x3D();

    MatrixM3x3D.translateByVector2D(m, new VectorI2D(1.0, 2.0), out);

    {
      final DoubleBuffer b = out.getDirectDoubleBuffer();

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertEquals(1.0, b.get(0), 0.0);
      Assert.assertEquals(0.0, b.get(1), 0.0);
      Assert.assertEquals(0.0, b.get(2), 0.0);

      Assert.assertEquals(0.0, b.get(3), 0.0);
      Assert.assertEquals(1.0, b.get(4), 0.0);
      Assert.assertEquals(0.0, b.get(5), 0.0);

      Assert.assertEquals(1.0, b.get(6), 0.0);
      Assert.assertEquals(2.0, b.get(7), 0.0);
      Assert.assertEquals(1.0, b.get(8), 0.0);
    }
  }

  @Test public void testTranspose()
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

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    final MatrixM3x3D k = MatrixM3x3D.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnD(0, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnD(0, 2) == 2.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.getRowColumnD(1, 0) == 4.0);
    Assert.assertTrue(m.getRowColumnD(1, 1) == 5.0);
    Assert.assertTrue(m.getRowColumnD(1, 2) == 6.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(m.getRowColumnD(2, 0) == 8.0);
    Assert.assertTrue(m.getRowColumnD(2, 1) == 9.0);
    Assert.assertTrue(m.getRowColumnD(2, 2) == 10.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnD(0, 2) == 8.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnD(1, 2) == 9.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnD(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnD(2, 2) == 10.0);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());
  }

  @Test public void testTransposeMutate()
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

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    final MatrixM3x3D r = MatrixM3x3D.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
    Assert.assertEquals(0, r.getDirectDoubleBuffer().position());

    Assert.assertTrue(r.getRowColumnD(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnD(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnD(0, 2) == 8.0);

    Assert.assertTrue(r.getRowColumnD(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnD(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnD(1, 2) == 9.0);

    Assert.assertTrue(r.getRowColumnD(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnD(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnD(2, 2) == 10.0);
  }

  @Test public void testZero()
  {
    final MatrixM3x3D m = new MatrixM3x3D();
    MatrixM3x3D.setZero(m);

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.getRowColumnD(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m.getDirectDoubleBuffer().position());
  }
}
