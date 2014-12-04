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

package com.io7m.jtensors.tests.parameterized;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrixM3x3F;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.jtensors.tests.TestUtilities;

@SuppressWarnings("static-method") public class PMatrixM3x3FTest<T>
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(0, 0, 1);

  private static <T> void isRotationMatrixX(
    final AlmostEqualFloat.ContextRelative context,
    final PMatrixM3x3F<T, T> r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private static <T> void isRotationMatrixY(
    final AlmostEqualFloat.ContextRelative context,
    final PMatrixM3x3F<T, T> r)
  {
    boolean eq;

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private static <T> void isRotationMatrixZ(
    final AlmostEqualFloat.ContextRelative context,
    final PMatrixM3x3F<T, T> r)
  {
    boolean eq;

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        -0.707106781187f,
        r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualFloat.almostEqual(
        context,
        0.707106781187f,
        r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  @Test public void testAdd()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mr = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    final PMatrixM3x3F<T, T> mk = PMatrixM3x3F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    Assert.assertEquals(0, mk.getDirectFloatBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 1.0);
        Assert.assertTrue(m1.getRowColumnF(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 4.0);
      }
    }
  }

  @Test public void testAddMutate()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> mr = PMatrixM3x3F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 4.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 4.0);
        Assert.assertTrue(m1.getRowColumnF(row, column) == 3.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Test public void testAddRowScaled()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.addRowScaled(m0, 0, 1, 2, 2.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 13.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.addRowScaledInPlace(m0, 0, 1, 2, 2.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m0.getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m0.getRowColumnF(0, 1) == 3.0);
    Assert.assertTrue(m0.getRowColumnF(0, 2) == 3.0);

    Assert.assertTrue(m0.getRowColumnF(1, 0) == 5.0);
    Assert.assertTrue(m0.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(m0.getRowColumnF(1, 2) == 5.0);

    Assert.assertTrue(m0.getRowColumnF(2, 0) == 13.0);
    Assert.assertTrue(m0.getRowColumnF(2, 1) == 13.0);
    Assert.assertTrue(m0.getRowColumnF(2, 2) == 13.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, 3, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, 0, 3, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, 0, 0, 3, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, -1, 0, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, 0, -1, 0, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.addRowScaledInPlace(m, 0, 0, -1, 1.0f);
  }

  @Test public void testBufferEndianness()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final FloatBuffer b = m.getDirectFloatBuffer();

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Test public void testCopy()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 4.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 6.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 8.0f);
    m0.set(2, 2, 9.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.copy(m0, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 3.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 4.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 6.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 7.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 8.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 9.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test public void testDeterminantIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    Assert.assertTrue(PMatrixM3x3F.determinant(m) == 1.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testDeterminantOther()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(PMatrixM3x3F.determinant(m) == 8.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testDeterminantScale()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    m.set(0, 0, 2.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(PMatrixM3x3F.determinant(m) == 2.0);
  }

  @Test public void testDeterminantScaleNegative()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    m.set(0, 0, -2.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertTrue(PMatrixM3x3F.determinant(m) == -2.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testDeterminantZero()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.setZero(m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertTrue(PMatrixM3x3F.determinant(m) == 0.0);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testEqualsCase0()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test public void testEqualsCase1()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertFalse(m0.equals(null));
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
  }

  @Test public void testEqualsCase2()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
  }

  @Test public void testEqualsCase3()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
    Assert.assertTrue(m0.equals(m1));
    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test public void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
        final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
        m1.set(row, col, 256);
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }
  }

  @Test public void testExchangeRows()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.exchangeRows(m0, 0, 2, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 9.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 10.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 11.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 3.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.exchangeRowsInPlace(m1, 0, 2);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 3.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 0.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 9.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 10.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 11.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.exchangeRowsInPlace(m, 3, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.exchangeRowsInPlace(m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.exchangeRowsInPlace(m, 0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.exchangeRowsInPlace(m, 0, -1);
  }

  @Test public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
        final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
        Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      }
    }
  }

  @Test public void testInitializationFrom()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);
    m0.set(0, 2, 7.0f);

    m0.set(1, 0, 11.0f);
    m0.set(1, 1, 13.0f);
    m0.set(1, 2, 17.0f);

    m0.set(2, 0, 19.0f);
    m0.set(2, 1, 23.0f);
    m0.set(2, 2, 29.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>(m0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 7.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 11.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 13.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 17.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 19.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 23.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 29.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test public void testInitializationIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    Assert.assertTrue(m.getRowColumnF(0, 0) == 1.0);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 0.0);

    Assert.assertTrue(m.getRowColumnF(1, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 0.0);

    Assert.assertTrue(m.getRowColumnF(2, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 0.0);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 1.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testInvertIdentity()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    {
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<PMatrixM3x3F<T, T>> s = (Some<PMatrixM3x3F<T, T>>) r;
      final PMatrixM3x3F<T, T> rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<PMatrixM3x3F<T, T>> s = (Some<PMatrixM3x3F<T, T>>) r;
      final PMatrixM3x3F<T, T> rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 1.0);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0.0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0.0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 1.0);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Test public void testInvertSimple()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

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
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invert(m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<PMatrixM3x3F<T, T>> s = (Some<PMatrixM3x3F<T, T>>) r;
      final PMatrixM3x3F<T, T> rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 0.5);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 0.5);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }

    {
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invertInPlace(m1);
      Assert.assertTrue(r.isSome());
      final Some<PMatrixM3x3F<T, T>> s = (Some<PMatrixM3x3F<T, T>>) r;
      final PMatrixM3x3F<T, T> rm = s.get();

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());

      Assert.assertTrue(rm.getRowColumnF(0, 0) == 2);
      Assert.assertTrue(rm.getRowColumnF(0, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(0, 2) == 0);

      Assert.assertTrue(rm.getRowColumnF(1, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(1, 1) == 2);
      Assert.assertTrue(rm.getRowColumnF(1, 2) == 0);

      Assert.assertTrue(rm.getRowColumnF(2, 0) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 1) == 0);
      Assert.assertTrue(rm.getRowColumnF(2, 2) == 2);

      Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
      Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
      Assert.assertEquals(0, rm.getDirectFloatBuffer().position());
    }
  }

  @Test public void testInvertZero()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    PMatrixM3x3F.setZero(m0);

    {
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invert(m0, m1);
      Assert.assertTrue(r.isNone());
    }

    {
      final OptionType<PMatrixM3x3F<T, T>> r = PMatrixM3x3F.invertInPlace(m0);
      Assert.assertTrue(r.isNone());
    }
  }

  @Test public void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F.Context mc = new PMatrixM3x3F.Context();
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F t = new VectorM3F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(-1, 0, 0);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    PMatrixM3x3F.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F.Context mc = new PMatrixM3x3F.Context();
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F t = new VectorM3F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(0, 0, -1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    PMatrixM3x3F.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F.Context mc = new PMatrixM3x3F.Context();
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F t = new VectorM3F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(1, 0, 0);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    PMatrixM3x3F.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F.Context mc = new PMatrixM3x3F.Context();
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F t = new VectorM3F();
    final VectorI3F origin = new VectorI3F(0, 0, 0);
    final VectorI3F target = new VectorI3F(0, 0, 1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    PMatrixM3x3F.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, t.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F.Context mc = new PMatrixM3x3F.Context();
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F t = new VectorM3F();
    final VectorI3F origin = new VectorI3F(20 + 0, 30 + 0, 40 + 0);
    final VectorI3F target = new VectorI3F(20 + 0, 30 + 0, 40 + -1);
    final VectorI3F axis = new VectorI3F(0, 1, 0);
    PMatrixM3x3F.lookAtWithContext(mc, origin, target, axis, m, t);

    System.out.println("m : ");
    System.out.println(m);
    System.out.println("t : ");
    System.out.println(t);

    boolean eq = false;

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(2, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, -20.0f, t.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -30.0f, t.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -40.0f, t.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testMultiplyIdentity()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mr = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == mr.getRowColumnF(
          row,
          column));
        Assert.assertTrue(m1.getRowColumnF(row, column) == mr.getRowColumnF(
          row,
          column));
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Test public void testMultiplyMutateIdentity()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == m1.getRowColumnF(
          row,
          column));
      }
    }

    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.multiply(m0, m1, m0);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == m1.getRowColumnF(
          row,
          column));
      }
    }
  }

  @Test public void testMultiplyMutateSimple()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();

    PMatrixM3x3F.set(m0, 0, 0, 1.0f);
    PMatrixM3x3F.set(m0, 0, 1, 2.0f);
    PMatrixM3x3F.set(m0, 0, 2, 3.0f);

    PMatrixM3x3F.set(m0, 1, 0, 4.0f);
    PMatrixM3x3F.set(m0, 1, 1, 5.0f);
    PMatrixM3x3F.set(m0, 1, 2, 6.0f);

    PMatrixM3x3F.set(m0, 2, 0, 7.0f);
    PMatrixM3x3F.set(m0, 2, 1, 8.0f);
    PMatrixM3x3F.set(m0, 2, 2, 9.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>(m0);
    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.multiply(m0, m1, m0);
    Assert.assertSame(r, m0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 30.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 36.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 42.0);

    Assert.assertTrue(r.getRowColumnF(1, 0) == 66.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 81.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 96.0);

    Assert.assertTrue(r.getRowColumnF(2, 0) == 102.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 126.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 150.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Test public void testMultiplySimple()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();

    PMatrixM3x3F.set(m0, 0, 0, 1.0f);
    PMatrixM3x3F.set(m0, 0, 1, 2.0f);
    PMatrixM3x3F.set(m0, 0, 2, 3.0f);

    PMatrixM3x3F.set(m0, 1, 0, 4.0f);
    PMatrixM3x3F.set(m0, 1, 1, 5.0f);
    PMatrixM3x3F.set(m0, 1, 2, 6.0f);

    PMatrixM3x3F.set(m0, 2, 0, 7.0f);
    PMatrixM3x3F.set(m0, 2, 1, 8.0f);
    PMatrixM3x3F.set(m0, 2, 2, 9.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>(m0);
    final PMatrixM3x3F<T, T> mr = new PMatrixM3x3F<T, T>();

    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 30.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 36.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 42.0);

    Assert.assertTrue(r.getRowColumnF(1, 0) == 66.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 81.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 96.0);

    Assert.assertTrue(r.getRowColumnF(2, 0) == 102.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 126.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 150.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Test public void testMultiplyVectorSimple()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();

    PMatrixM3x3F.set(m0, 0, 0, 1.0f);
    PMatrixM3x3F.set(m0, 0, 1, 2.0f);
    PMatrixM3x3F.set(m0, 0, 2, 3.0f);

    PMatrixM3x3F.set(m0, 1, 0, 4.0f);
    PMatrixM3x3F.set(m0, 1, 1, 5.0f);
    PMatrixM3x3F.set(m0, 1, 2, 6.0f);

    PMatrixM3x3F.set(m0, 2, 0, 7.0f);
    PMatrixM3x3F.set(m0, 2, 1, 8.0f);
    PMatrixM3x3F.set(m0, 2, 2, 9.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final PVectorM3F<T> v = new PVectorM3F<T>(1.0f, 2.0f, 3.0f);
    final PVectorM3F<T> out = new PVectorM3F<T>();

    final PVectorM3F<T> r = PMatrixM3x3F.multiplyVector3F(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.getXF() == 14.0);
    Assert.assertTrue(out.getYF() == 32.0);
    Assert.assertTrue(out.getZF() == 50.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
  }

  @Test public void testMultiplyZero()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mr = new PMatrixM3x3F<T, T>();

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    PMatrixM3x3F.setZero(m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(mr.getRowColumnF(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRowColumnF(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRowColumnF(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRowColumnF(0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRowColumnF(3, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mt = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mi = new PMatrixM3x3F<T, T>();
    final VectorM3F axis = new VectorM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      double angle = Math.random() * (2 * Math.PI);
      axis.set3F(
        (float) Math.random(),
        (float) Math.random(),
        (float) Math.random());

      if (Math.random() > 0.5) {
        angle = -angle;
      }
      if (Math.random() > 0.5) {
        axis.setXF(-axis.getXF());
      }
      if (Math.random() > 0.5) {
        axis.setYF(-axis.getYF());
      }
      if (Math.random() > 0.5) {
        axis.setZF(-axis.getZF());
      }
      VectorM3F.normalizeInPlace(axis);

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      PMatrixM3x3F.makeRotationInto(angle, axis, m);

      final double det = PMatrixM3x3F.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      PMatrixM3x3F.invert(m, mi);
      PMatrixM3x3F.transpose(m, mt);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final float mx = mi.getRowColumnF(row, col);
          final float my = mt.getRowColumnF(row, col);
          final boolean eq = AlmostEqualFloat.almostEqual(context_f, mx, my);

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
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 0, -1);
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_exp = new PVectorM3F<T>(0, 0, -1);

    PMatrixM3x3F.makeRotationInto(0, PMatrixM3x3FTest.AXIS_X, m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public void testRotateVector0Y()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 0, -1);
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_exp = new PVectorM3F<T>(0, 0, -1);

    PMatrixM3x3F.makeRotationInto(0, PMatrixM3x3FTest.AXIS_Y, m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public void testRotateVector0Z()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 0, -1);
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_exp = new PVectorM3F<T>(0, 0, -1);

    PMatrixM3x3F.makeRotationInto(0, PMatrixM3x3FTest.AXIS_Z, m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 1, 0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final PVectorM3F<T> v_exp = new PVectorM3F<T>(0, 6.1232339957367E-17f, 1);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(90),
      PMatrixM3x3FTest.AXIS_X,
      m);
    System.out.println(m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Y axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 0, -1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final PVectorM3F<T> v_exp =
      new PVectorM3F<T>(-1, 0, -6.1232339957367E-17f);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(90),
      PMatrixM3x3FTest.AXIS_Y,
      m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Z axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public void testRotateVector90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 1, 0);
    final PVectorM3F<T> v_exp =
      new PVectorM3F<T>(-1, 6.123233995736766E-17f, 0);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(90),
      PMatrixM3x3FTest.AXIS_Z,
      m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the X axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 1, 0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final PVectorM3F<T> v_exp =
      new PVectorM3F<T>(0, 6.1232339957367E-17f, -1);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(-90),
      PMatrixM3x3FTest.AXIS_X,
      m);
    System.out.println(m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Y axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 0, -1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final PVectorM3F<T> v_exp =
      new PVectorM3F<T>(1, 0, -6.1232339957367E-17f);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(-90),
      PMatrixM3x3FTest.AXIS_Y,
      m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Z axis gives the correct clockwise
   * rotation of the vector.
   */

  @Test public void testRotateVectorMinus90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PVectorM3F<T> v_got = new PVectorM3F<T>();
    final PVectorM3F<T> v_in = new PVectorM3F<T>(0, 1, 0);
    final PVectorM3F<T> v_exp =
      new PVectorM3F<T>(1, 6.123233995736766E-17f, 0);

    PMatrixM3x3F.makeRotationInto(
      Math.toRadians(-90),
      PMatrixM3x3FTest.AXIS_Z,
      m);
    PMatrixM3x3F.multiplyVector3F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  @Test public void testRotateXMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final PMatrixM3x3F<T, T> r =
        PMatrixM3x3F
          .makeRotation(Math.toRadians(45), PMatrixM3x3FTest.AXIS_X);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      PMatrixM3x3FTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Test public void testRotateYMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final PMatrixM3x3F<T, T> r =
        PMatrixM3x3F
          .makeRotation(Math.toRadians(45), PMatrixM3x3FTest.AXIS_Y);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      PMatrixM3x3FTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Test public void testRotateZMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final PMatrixM3x3F<T, T> r =
        PMatrixM3x3F
          .makeRotation(Math.toRadians(45), PMatrixM3x3FTest.AXIS_Z);
      Assert.assertEquals(0, r.getDirectFloatBuffer().position());

      System.out.println(r);

      PMatrixM3x3FTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, r.getDirectFloatBuffer().position());
    }
  }

  @Test public void testRow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F v = new VectorM3F();

    PMatrixM3x3F.row(m, 0, v);
    Assert.assertTrue(v.getXF() == 1.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    PMatrixM3x3F.row(m, 1, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 1.0);
    Assert.assertTrue(v.getZF() == 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    PMatrixM3x3F.row(m, 2, v);
    Assert.assertTrue(v.getXF() == 0.0);
    Assert.assertTrue(v.getYF() == 0.0);
    Assert.assertTrue(v.getZF() == 1.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testRow3F()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM3F v = new VectorM3F();
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow3F(0, v);
    Assert.assertTrue(v.getXF() == 1.0f);
    Assert.assertTrue(v.getYF() == 0.0f);
    Assert.assertTrue(v.getZF() == 0.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow3F(1, v);
    Assert.assertTrue(v.getXF() == 0.0f);
    Assert.assertTrue(v.getYF() == 1.0f);
    Assert.assertTrue(v.getZF() == 0.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.getRow3F(2, v);
    Assert.assertTrue(v.getXF() == 0.0f);
    Assert.assertTrue(v.getYF() == 0.0f);
    Assert.assertTrue(v.getZF() == 1.0f);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow3FOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRow3F(4, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow3FUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    m.getRow3F(-1, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.row(m, 3, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.row(m, -1, new VectorM3F());
  }

  @Test public void testScale()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> mr = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> mk = PMatrixM3x3F.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m0.getRowColumnF(row, column) == 3.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Test public void testScaleMutate()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> mr = PMatrixM3x3F.scaleInPlace(m, 5.0f);
    Assert.assertSame(mr, m);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.getRowColumnF(row, column) == 15.0);
        Assert.assertTrue(mr.getRowColumnF(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, mr.getDirectFloatBuffer().position());
  }

  @Test public void testScaleRow()
  {
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());

    PMatrixM3x3F.scaleRow(m0, 0, 2.0f, m1);
    PMatrixM3x3F.scaleRow(m0, 1, 4.0f, m1);
    PMatrixM3x3F.scaleRow(m0, 2, 8.0f, m1);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m1.getRowColumnF(0, 0) == 2.0);
    Assert.assertTrue(m1.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(m1.getRowColumnF(0, 2) == 6.0);

    Assert.assertTrue(m1.getRowColumnF(1, 0) == 20.0);
    Assert.assertTrue(m1.getRowColumnF(1, 1) == 24.0);
    Assert.assertTrue(m1.getRowColumnF(1, 2) == 28.0);

    Assert.assertTrue(m1.getRowColumnF(2, 0) == 72.0);
    Assert.assertTrue(m1.getRowColumnF(2, 1) == 80.0);
    Assert.assertTrue(m1.getRowColumnF(2, 2) == 88.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    PMatrixM3x3F.scaleRowInPlace(m0, 0, 2.0f);
    PMatrixM3x3F.scaleRowInPlace(m0, 1, 4.0f);
    PMatrixM3x3F.scaleRowInPlace(m0, 2, 8.0f);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());

    Assert.assertTrue(m0.getRowColumnF(0, 0) == 2.0);
    Assert.assertTrue(m0.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(m0.getRowColumnF(0, 2) == 6.0);

    Assert.assertTrue(m0.getRowColumnF(1, 0) == 20.0);
    Assert.assertTrue(m0.getRowColumnF(1, 1) == 24.0);
    Assert.assertTrue(m0.getRowColumnF(1, 2) == 28.0);

    Assert.assertTrue(m0.getRowColumnF(2, 0) == 72.0);
    Assert.assertTrue(m0.getRowColumnF(2, 1) == 80.0);
    Assert.assertTrue(m0.getRowColumnF(2, 2) == 88.0);

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.scaleRowInPlace(m, 3, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.scaleRowInPlace(m, -1, 1.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> r = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.scaleRow(m, 3, 1.0f, r);
  }

  @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> r = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.scaleRow(m, -1, 1.0f, r);
  }

  @Test public void testSetIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m.set(row, col, (float) Math.random());
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    PMatrixM3x3F.setIdentity(m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testSetGetIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(m.set(0, 0, 3.0f).getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).getRowColumnF(0, 2) == 7.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(m.set(1, 0, 13.0f).getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).getRowColumnF(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).getRowColumnF(1, 2) == 19.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertTrue(m.set(2, 0, 29.0f).getRowColumnF(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).getRowColumnF(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).getRowColumnF(2, 2) == 37.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testSetGetInterfaceIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.setRowColumnF(0, 0, 3.0f);
    Assert.assertTrue(m.getRowColumnF(0, 0) == 3.0f);
    m.setRowColumnF(0, 1, 5.0f);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 5.0f);
    m.setRowColumnF(0, 2, 7.0f);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 7.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.setRowColumnF(1, 0, 13.0f);
    Assert.assertTrue(m.getRowColumnF(1, 0) == 13.0f);
    m.setRowColumnF(1, 1, 17.0f);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 17.0f);
    m.setRowColumnF(1, 2, 19.0f);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 19.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    m.setRowColumnF(2, 0, 29.0f);
    Assert.assertTrue(m.getRowColumnF(2, 0) == 29.0f);
    m.setRowColumnF(2, 1, 31.0f);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 31.0f);
    m.setRowColumnF(2, 2, 37.0f);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 37.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testStorage()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    m.set(0, 0, 0);
    m.set(0, 1, 1);
    m.set(0, 2, 2);

    m.set(1, 0, 100);
    m.set(1, 1, 101);
    m.set(1, 2, 102);

    m.set(2, 0, 200);
    m.set(2, 1, 201);
    m.set(2, 2, 202);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    {
      final FloatBuffer b = m.getDirectFloatBuffer();

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
    final PMatrixM3x3F<T, T> m0 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m1 = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> m2 = new PMatrixM3x3F<T, T>();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));

    Assert.assertEquals(0, m0.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m1.getDirectFloatBuffer().position());
    Assert.assertEquals(0, m2.getDirectFloatBuffer().position());
  }

  @Test public void testTrace()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final double t = PMatrixM3x3F.trace(m);

    Assert.assertEquals(3.0, t, 0.0);
  }

  @Test public void testTranslate2FMakeIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM2F v = new VectorM2F(0, 0);

    PMatrixM3x3F.makeTranslation2F(v, m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(1.0f, m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testTranslate2FMakeSimple()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM2F v = new VectorM2F(3, 7);

    PMatrixM3x3F.makeTranslation2F(v, m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(1.0f, m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0f, m.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(7.0f, m.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testTranslate2IMakeIdentity()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM2I v = new VectorM2I(0, 0);

    PMatrixM3x3F.makeTranslation2I(v, m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(1.0f, m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testTranslate2IMakeSimple()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final VectorM2I v = new VectorM2I(3, 7);

    PMatrixM3x3F.makeTranslation2I(v, m);
    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(1.0f, m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0f, m.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(7.0f, m.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    Assert.assertEquals(0.0f, m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0f, m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0f, m.getRowColumnF(2, 2), 0.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

  @Test public void testTranspose()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    final PMatrixM3x3F<T, T> r = new PMatrixM3x3F<T, T>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> k = PMatrixM3x3F.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(m.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(m.getRowColumnF(0, 1) == 1.0);
    Assert.assertTrue(m.getRowColumnF(0, 2) == 2.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(m.getRowColumnF(1, 0) == 4.0);
    Assert.assertTrue(m.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(m.getRowColumnF(1, 2) == 6.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(m.getRowColumnF(2, 0) == 8.0);
    Assert.assertTrue(m.getRowColumnF(2, 1) == 9.0);
    Assert.assertTrue(m.getRowColumnF(2, 2) == 10.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 8.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 9.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 10.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Test public void testTransposeMutate()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);

    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);

    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    final PMatrixM3x3F<T, T> r = PMatrixM3x3F.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(0, 0) == 0.0);
    Assert.assertTrue(r.getRowColumnF(0, 1) == 4.0);
    Assert.assertTrue(r.getRowColumnF(0, 2) == 8.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(1, 0) == 1.0);
    Assert.assertTrue(r.getRowColumnF(1, 1) == 5.0);
    Assert.assertTrue(r.getRowColumnF(1, 2) == 9.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());

    Assert.assertTrue(r.getRowColumnF(2, 0) == 2.0);
    Assert.assertTrue(r.getRowColumnF(2, 1) == 6.0);
    Assert.assertTrue(r.getRowColumnF(2, 2) == 10.0);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
    Assert.assertEquals(0, r.getDirectFloatBuffer().position());
  }

  @Test public void testZero()
  {
    final PMatrixM3x3F<T, T> m = new PMatrixM3x3F<T, T>();
    PMatrixM3x3F.setZero(m);

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(m.getRowColumnF(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, m.getDirectFloatBuffer().position());
  }

}
