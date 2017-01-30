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

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix3x3FContract<T extends Matrix3x3FType>
  extends MatrixReadable3x3FContract<T>
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(
    1.0f, 0.0f, 0.0f);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(
    0.0f, 1.0f, 0.0f);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(
    0.0f, 0.0f, 1.0f);

  private void isRotationMatrixX(
    final AlmostEqualFloat.ContextRelative context,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualFloat.almostEqual(
      context, 1.0f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, -0.707106781187f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixY(
    final AlmostEqualFloat.ContextRelative context,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context, -0.707106781187f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixZ(
    final AlmostEqualFloat.ContextRelative context_d,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualFloat.almostEqual(
      context_d, 0.707106781187f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context_d, -0.707106781187f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context_d, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context_d, 0.707106781187f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context_d, 0.707106781187f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context_d, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context_d, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context_d, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context_d, 1.0f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  @Test public final void testAdd()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    final T mk = MatrixM3x3F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(1.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(3.0, (double) m1.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(4.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }
  }

  @Test public final void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T mr = MatrixM3x3F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(4.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(4.0, (double) mr.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(3.0, (double) m1.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testAddRowScaled()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(3.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(5.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.addRowScaled(s, m0, 0, 1, 2, 2.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.addRowScaledInPlace(s, m0, 0, 1, 2, 2.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowA()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, 3, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowB()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, 0, 3, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowC()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, 0, 0, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowA()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowB()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowC()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.addRowScaledInPlace(s, m, 0, 0, -1, 1.0);
  }

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(6.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(8.0f);
    m0.setR2C2F(9.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.copy(m0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(4.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(6.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(7.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(8.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(9.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, MatrixM3x3F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);
    m.setR1C1F(2.0f);
    m.setR2C2F(2.0f);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(8.0, MatrixM3x3F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(2.0, MatrixM3x3F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setR0C0F(-2.0f);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(-2.0, MatrixM3x3F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantZero()
  {
    final T m = this.newMatrix();
    MatrixM3x3F.setZero(m);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(0.0, MatrixM3x3F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testEqualsCase0()
  {
    final T m0 = this.newMatrix();
    Assert.assertTrue(m0.equals(m0));
  }

  @Test public final void testEqualsCase1()
  {
    final T m0 = this.newMatrix();
    this.checkDirectBufferInvariants(m0);
    Assert.assertFalse(m0.equals(null));
    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testEqualsCase2()
  {
    final T m0 = this.newMatrix();
    this.checkDirectBufferInvariants(m0);
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testEqualsCase3()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    Assert.assertEquals(m0, m1);
    Assert.assertNotSame(m0, m1);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testEqualsNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        m1.setRowColumnF(row, col, 256.0f);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testExchangeRows()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.exchangeRows(s, m0, 0, 2, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(9.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(10.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(11.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.exchangeRowsInPlace(s, m1, 0, 2);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(9.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(10.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(11.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAOverflow()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.exchangeRowsInPlace(s, m, 3, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAUnderflow()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.exchangeRowsInPlace(s, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBOverflow()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.exchangeRowsInPlace(s, m, 0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBUnderflow()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.exchangeRowsInPlace(s, m, 0, -1);
  }

  @Test public final void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        Assert.assertEquals(
          (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        m1.setRowColumnF(row, col, 256.0f);
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(5.0f);
    m0.setR0C2F(7.0f);

    m0.setR1C0F(11.0f);
    m0.setR1C1F(13.0f);
    m0.setR1C2F(17.0f);

    m0.setR2C0F(19.0f);
    m0.setR2C1F(23.0f);
    m0.setR2C2F(29.0f);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(5.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(7.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(11.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(17.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(19.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(23.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(29.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testInvertInPlaceIdentity()
  {
    final MatrixM3x3F.ContextMM3F s = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM3x3F.invert(s, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM3x3F.invertInPlace(s, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimpleNF()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(2.0f);
    m0.setR0C1F(0.0f);
    m0.setR0C2F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);
    m0.setR1C2F(0.0f);

    m0.setR2C0F(0.0f);
    m0.setR2C1F(0.0f);
    m0.setR2C2F(2.0f);

    {
      final boolean r = MatrixM3x3F.invert(c, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM3x3F.invertInPlace(c, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertZeroNF()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM3x3F.setZero(m0);

    {
      final boolean r = MatrixM3x3F.invert(c, m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = MatrixM3x3F.invertInPlace(c, m0);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F.ContextMM3F mc = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final VectorM3F t = new VectorM3F();
    final VectorReadable3FType origin = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorReadable3FType target = new VectorI3F(-1.0f, 0.0f, 0.0f);
    final VectorReadable3FType axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    MatrixM3x3F.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F.ContextMM3F mc = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final VectorM3F t = new VectorM3F();
    final VectorReadable3FType origin = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorReadable3FType target = new VectorI3F(0.0f, 0.0f, -1.0f);
    final VectorReadable3FType axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    MatrixM3x3F.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F.ContextMM3F mc = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final VectorM3F t = new VectorM3F();
    final VectorReadable3FType origin = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorReadable3FType target = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorReadable3FType axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    MatrixM3x3F.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F.ContextMM3F mc = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final VectorM3F t = new VectorM3F();
    final VectorReadable3FType origin = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorReadable3FType target = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorReadable3FType axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    MatrixM3x3F.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F.ContextMM3F mc = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final VectorM3F t = new VectorM3F();
    final VectorReadable3FType origin = new VectorI3F(
      (20 + 0), (30 + 0), (40 + 0));
    final VectorReadable3FType target = new VectorI3F(
      (20 + 0), (30 + 0), (40 + -1));
    final VectorReadable3FType axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    MatrixM3x3F.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
        Assert.assertTrue(
          m1.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplyMutateIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T r = MatrixM3x3F.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testMultiplyMutateSimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(6.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(8.0f);
    m0.setR2C2F(9.0f);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM3x3F.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(30.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(36.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(42.0, (double) r.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(66.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(81.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(96.0, (double) r.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(102.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(126.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(150.0, (double) r.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testMultiplySimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(6.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(8.0f);
    m0.setR2C2F(9.0f);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    Assert.assertEquals(30.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(36.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(42.0, (double) r.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(66.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(81.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(96.0, (double) r.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(102.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(126.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(150.0, (double) r.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(6.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(8.0f);
    m0.setR2C2F(9.0f);

    this.checkDirectBufferInvariants(m0);

    final VectorReadable3FType v = new VectorI3F(1.0f, 2.0f, 3.0f);
    final VectorM3F out = new VectorM3F();

    final VectorM3F r = MatrixM3x3F.multiplyVector3F(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(14.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(32.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(50.0, (double) out.getZF(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM3x3F.setZero(m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T r = MatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(0.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeNegativeColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeNegativeRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(3, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public final void testRotateDeterminantOrthogonal()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final T mt = this.newMatrix();
    final T mi = this.newMatrix();
    final VectorM3F axis = new VectorM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      double angle = Math.random() * (2.0 * Math.PI);
      axis.set3F(
        (float) Math.random(), (float) Math.random(), (float) Math.random());

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

      
      

      MatrixM3x3F.makeRotation(angle, axis, m);

      final double det = MatrixM3x3F.determinant(m);
      

      AlmostEqualFloat.almostEqual(context, (float) det, 1.0f);

      MatrixM3x3F.invert(c, m, mi);
      MatrixM3x3F.transpose(m, mt);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final double mx = (double) mi.getRowColumnF(row, col);
          final double my = (double) mt.getRowColumnF(row, col);
          final boolean eq =
            AlmostEqualFloat.almostEqual(context, (float) mx, (float) my);

          
          
          

          Assert.assertTrue(eq);
        }
      }

      
    }
  }

  /**
   * A rotation of 0 degrees around the X axis has no effect.
   */

  @Test public final void testRotateVector0X()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 0.0f, -1.0f);
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_exp = new VectorM3F(0.0f, 0.0f, -1.0f);

    MatrixM3x3F.makeRotation(0.0, Matrix3x3FContract.AXIS_X, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public final void testRotateVector0Y()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 0.0f, -1.0f);
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_exp = new VectorM3F(0.0f, 0.0f, -1.0f);

    MatrixM3x3F.makeRotation(0.0, Matrix3x3FContract.AXIS_Y, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public final void testRotateVector0Z()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 0.0f, -1.0f);
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_exp = new VectorM3F(0.0f, 0.0f, -1.0f);

    MatrixM3x3F.makeRotation(0.0, Matrix3x3FContract.AXIS_Z, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public final void testRotateVector90X()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 1.0f, 0.0f);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp = new VectorM3F(
      0.0f, 6.1232339957367E-17f, 1.0f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(90.0), Matrix3x3FContract.AXIS_X, m);
    
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVector90Y()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 0.0f, -1.0f);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp = new VectorM3F(
      -1.0f, 0.0f, -6.1232339957367E-17f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(90.0), Matrix3x3FContract.AXIS_Y, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVector90Z()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 1.0f, 0.0f);
    final VectorReadable3FType v_exp = new VectorM3F(
      -1.0f, 6.123233995736766E-17f, 0.0f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(90.0), Matrix3x3FContract.AXIS_Z, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90X()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 1.0f, 0.0f);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp = new VectorM3F(
      0.0f, 6.1232339957367E-17f, -1.0f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(-90.0), Matrix3x3FContract.AXIS_X, m);
    
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90Y()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 0.0f, -1.0f);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp = new VectorM3F(
      1.0f, 0.0f, -6.1232339957367E-17f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(-90.0), Matrix3x3FContract.AXIS_Y, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90Z()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3F v_got = new VectorM3F();
    final VectorReadable3FType v_in = new VectorM3F(0.0f, 1.0f, 0.0f);
    final VectorReadable3FType v_exp = new VectorM3F(
      1.0f, 6.123233995736766E-17f, 0.0f);

    MatrixM3x3F.makeRotation(
      Math.toRadians(-90.0), Matrix3x3FContract.AXIS_Z, m);
    MatrixM3x3F.multiplyVector3F(c, m, v_in, v_got);

    
    
    
    

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  @Test public final void testRotateXMakeEquivalent()
  {

    final AlmostEqualFloat.ContextRelative context_d =
      TestUtilities.getSingleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3F.makeRotation(
        Math.toRadians(45.0), Matrix3x3FContract.AXIS_X, r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3F.determinant(r), 0.000001);

      

      this.isRotationMatrixX(context_d, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateYMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3F.makeRotation(
        Math.toRadians(45.0), Matrix3x3FContract.AXIS_Y, r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3F.determinant(r), 0.000001);

      

      this.isRotationMatrixY(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateZMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context_d =
      TestUtilities.getSingleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3F.makeRotation(
        Math.toRadians(45.0), Matrix3x3FContract.AXIS_Z, r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3F.determinant(r), 0.000001);

      

      this.isRotationMatrixZ(context_d, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRow3F()
  {
    final T m = this.newMatrix();
    final VectorM3F v = new VectorM3F();
    this.checkDirectBufferInvariants(m);

    m.getRow3F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(1, v);
    Assert.assertEquals(0.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(2, v);
    Assert.assertEquals(0.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3FOverflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(4, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3FUnderflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(-1, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2FOverflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(4, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2FUnderflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(-1, new VectorM3F());
  }

  @Test public final void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM3x3F.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(3.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(15.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testScaleMutate()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m);

    final T mr = MatrixM3x3F.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(15.0, (double) m.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(15.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testScaleRow()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);

    this.checkDirectBufferInvariants(m0);

    MatrixM3x3F.scaleRow(c, m0, 0, 2.0, m1);
    MatrixM3x3F.scaleRow(c, m0, 1, 4.0, m1);
    MatrixM3x3F.scaleRow(c, m0, 2, 8.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m1.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(20.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m1.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(72.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m1.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3F.scaleRowInPlace(c, m0, 0, 2.0);
    MatrixM3x3F.scaleRowInPlace(c, m0, 1, 4.0);
    MatrixM3x3F.scaleRowInPlace(c, m0, 2, 8.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m0.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m0.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(20.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m0.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m0.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(72.0, (double) m0.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m0.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m0.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateOverflow()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.scaleRowInPlace(c, m, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateUnderflow()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    MatrixM3x3F.scaleRowInPlace(c, m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowOverflow()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM3x3F.scaleRow(c, m, 3, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowUnderflow()
  {
    final MatrixM3x3F.ContextMM3F c = new MatrixM3x3F.ContextMM3F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM3x3F.scaleRow(c, m, -1, 1.0, r);
  }

  private void checkZeroExcept(
    final T m,
    final int row,
    final int col)
  {
    for (int r = 0; r < 3; ++r) {
      for (int c = 0; c < 3; ++c) {
        if (row == r && c == col) {
          continue;
        }

        final float x = m.getRowColumnF(r, c);
        if (x != 0.0) {
          System.err.printf(
            "row %d col %d, %f != %f\n",
            Integer.valueOf(r),
            Integer.valueOf(c),
            Float.valueOf(x),
            Float.valueOf(0.0f));
          System.err.println(m.toString());
          Assert.assertEquals(0.0, x, 0.0);
        }
      }
    }
  }

  @Test public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    {
      MatrixM3x3F.setZero(m);
      m.setR0C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 0), 0.0);
      this.checkZeroExcept(m, 0, 0);

      MatrixM3x3F.setZero(m);
      m.setR1C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
      this.checkZeroExcept(m, 1, 0);

      MatrixM3x3F.setZero(m);
      m.setR2C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 0), 0.0);
      this.checkZeroExcept(m, 2, 0);
    }

    {
      MatrixM3x3F.setZero(m);
      m.setR0C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 1), 0.0);
      this.checkZeroExcept(m, 0, 1);

      MatrixM3x3F.setZero(m);
      m.setR1C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 1), 0.0);
      this.checkZeroExcept(m, 1, 1);

      MatrixM3x3F.setZero(m);
      m.setR2C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 1), 0.0);
      this.checkZeroExcept(m, 2, 1);
    }

    {
      MatrixM3x3F.setZero(m);
      m.setR0C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 2), 0.0);
      this.checkZeroExcept(m, 0, 2);

      MatrixM3x3F.setZero(m);
      m.setR1C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 2), 0.0);
      this.checkZeroExcept(m, 1, 2);

      MatrixM3x3F.setZero(m);
      m.setR2C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 2), 0.0);
      this.checkZeroExcept(m, 2, 2);
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testSetGetInterfaceIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    m.setRowColumnF(0, 0, 3.0f);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 0), 0.0);
    m.setRowColumnF(0, 1, 5.0f);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(0, 1), 0.0);
    m.setRowColumnF(0, 2, 7.0f);
    Assert.assertEquals(7.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    m.setRowColumnF(1, 0, 13.0f);
    Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
    m.setRowColumnF(1, 1, 17.0f);
    Assert.assertEquals(17.0, (double) m.getRowColumnF(1, 1), 0.0);
    m.setRowColumnF(1, 2, 19.0f);
    Assert.assertEquals(19.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    m.setRowColumnF(2, 0, 29.0f);
    Assert.assertEquals(29.0, (double) m.getRowColumnF(2, 0), 0.0);
    m.setRowColumnF(2, 1, 31.0f);
    Assert.assertEquals(31.0, (double) m.getRowColumnF(2, 1), 0.0);
    m.setRowColumnF(2, 2, 37.0f);
    Assert.assertEquals(37.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTraceIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(3.0, MatrixM3x3F.trace(m), 0.0);
  }

  @Test public final void testTranslate2FMakeIdentity()
  {
    final T m = this.newMatrix();

    final VectorReadable2FType v = new VectorM2F(0.0f, 0.0f);

    MatrixM3x3F.makeTranslation2F(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2FMakeSimple()
  {
    final T m = this.newMatrix();

    final VectorReadable2FType v = new VectorM2F(3.0f, 7.0f);

    MatrixM3x3F.makeTranslation2F(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(7.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2IMakeIdentity()
  {
    final T m = this.newMatrix();

    final VectorReadable2IType v = new VectorM2I(0, 0);

    MatrixM3x3F.makeTranslation2I(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2IMakeSimple()
  {
    final T m = this.newMatrix();

    final VectorReadable2IType v = new VectorM2I(3, 7);

    MatrixM3x3F.makeTranslation2I(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(7.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);
    m.setR0C2F(2.0f);
    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);
    m.setR1C2F(6.0f);
    m.setR2C0F(8.0f);
    m.setR2C1F(9.0f);
    m.setR2C2F(10.0f);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    final T k = MatrixM3x3F.transpose(m, r);
    Assert.assertSame(k, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(2.0, (double) m.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(4.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(6.0, (double) m.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(8.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(9.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) m.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(8.0, (double) r.getRowColumnF(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(9.0, (double) r.getRowColumnF(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(2.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(6.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);
    m.setR0C2F(2.0f);

    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);
    m.setR1C2F(6.0f);

    m.setR2C0F(8.0f);
    m.setR2C1F(9.0f);
    m.setR2C2F(10.0f);

    this.checkDirectBufferInvariants(m);

    final T r = MatrixM3x3F.transposeInPlace(m);
    Assert.assertSame(m, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(8.0, (double) r.getRowColumnF(0, 2), 0.0);

    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(9.0, (double) r.getRowColumnF(1, 2), 0.0);

    Assert.assertEquals(2.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(6.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(2, 2), 0.0);
  }

  @Test public final void testZero()
  {
    final T m = this.newMatrix();
    MatrixM3x3F.setZero(m);

    this.checkDirectBufferInvariants(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(0.0, (double) m.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI3F(1.0f, 2.0f, 3.0f));
    m.setRowWith3F(1, new VectorI3F(10.0f, 20.0f, 30.0f));
    m.setRowWith3F(2, new VectorI3F(100.0f, 200.0f, 300.0f));

    m.getRow3F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  protected abstract void checkDirectBufferInvariants(final T m);

  @Test public final void testRowSet3Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI3F(1.0f, 2.0f, 3.0f));
    m.setRowWith3F(1, new VectorI3F(10.0f, 20.0f, 30.0f));
    m.setRowWith3F(2, new VectorI3F(100.0f, 200.0f, 300.0f));

    m.getRow3FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI2F(1.0f, 2.0f));
    m.setRowWith2F(1, new VectorI2F(10.0f, 20.0f));
    m.setRowWith2F(2, new VectorI2F(100.0f, 200.0f));

    m.getRow2F(0, v);
    Assert.assertEquals(1.0, v.getXF(), 0.0);
    Assert.assertEquals(2.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(1, v);
    Assert.assertEquals(10.0, v.getXF(), 0.0);
    Assert.assertEquals(20.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(2, v);
    Assert.assertEquals(100.0, v.getXF(), 0.0);
    Assert.assertEquals(200.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI2F(1.0f, 2.0f));
    m.setRowWith2F(1, new VectorI2F(10.0f, 20.0f));
    m.setRowWith2F(2, new VectorI2F(100.0f, 200.0f));

    m.getRow2FUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXF(), 0.0);
    Assert.assertEquals(2.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXF(), 0.0);
    Assert.assertEquals(20.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXF(), 0.0);
    Assert.assertEquals(200.0, v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
