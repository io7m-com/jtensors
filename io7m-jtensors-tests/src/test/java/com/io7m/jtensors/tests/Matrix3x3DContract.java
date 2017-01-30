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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix3x3DContract<T extends Matrix3x3DType>
  extends MatrixReadable3x3DContract<T>
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(
    1.0, 0.0, 0.0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(
    0.0, 1.0, 0.0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(
    0.0, 0.0, 1.0);

  private void isRotationMatrixX(
    final AlmostEqualDouble.ContextRelative context,
    final T r)
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
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixY(
    final AlmostEqualDouble.ContextRelative context,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixZ(
    final AlmostEqualDouble.ContextRelative context_d,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, r.getRowColumnD(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context_d, -0.707106781187, r.getRowColumnD(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, r.getRowColumnD(1, 1));
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

  @Test public final void testAdd()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    final T mk = MatrixM3x3D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);
    this.checkDirectBufferInvariants(mk);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(1.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(3.0, m1.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(4.0, mr.getRowColumnD(row, column), 0.0);
      }
    }
  }

  @Test public final void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T mr = MatrixM3x3D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);
    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(4.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(4.0, mr.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(3.0, m1.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testAddRowScaled()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(3.0);
    m0.setR0C1D(3.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(5.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(5.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.addRowScaled(s, m0, 0, 1, 2, 2.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.addRowScaledInPlace(s, m0, 0, 1, 2, 2.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m0.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(3.0, m0.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m0.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(5.0, m0.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, m0.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(5.0, m0.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(13.0, m0.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(13.0, m0.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(13.0, m0.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowA()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, 3, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowB()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, 0, 3, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowC()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, 0, 0, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowA()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowB()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowC()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.addRowScaledInPlace(s, m, 0, 0, -1, 1.0);
  }

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(6.0);

    m0.setR2C0D(7.0);
    m0.setR2C1D(8.0);
    m0.setR2C2D(9.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.copy(m0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(4.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(6.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(7.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(8.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(9.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, MatrixM3x3D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setR0C0D(2.0);
    m.setR1C1D(2.0);
    m.setR2C2D(2.0);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(8.0, MatrixM3x3D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setR0C0D(2.0);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(2.0, MatrixM3x3D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setR0C0D(-2.0);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(-2.0, MatrixM3x3D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantZero()
  {
    final T m = this.newMatrix();
    MatrixM3x3D.setZero(m);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(0.0, MatrixM3x3D.determinant(m), 0.0);
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
        m1.setRowColumnD(row, col, 256.0);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testExchangeRows()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);

    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.exchangeRows(s, m0, 0, 2, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(9.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(10.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(11.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(1.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.exchangeRowsInPlace(s, m1, 0, 2);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(9.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(10.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(11.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAOverflow()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.exchangeRowsInPlace(s, m, 3, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAUnderflow()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.exchangeRowsInPlace(s, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBOverflow()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.exchangeRowsInPlace(s, m, 0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBUnderflow()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.exchangeRowsInPlace(s, m, 0, -1);
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
        m1.setRowColumnD(row, col, 256.0);
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

    m0.setR0C0D(3.0);
    m0.setR0C1D(5.0);
    m0.setR0C2D(7.0);

    m0.setR1C0D(11.0);
    m0.setR1C1D(13.0);
    m0.setR1C2D(17.0);

    m0.setR2C0D(19.0);
    m0.setR2C1D(23.0);
    m0.setR2C2D(29.0);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(5.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(7.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(11.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(17.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(19.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(23.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(29.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testInvertInPlaceIdentity()
  {
    final MatrixM3x3D.ContextMM3D s = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM3x3D.invert(s, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM3x3D.invertInPlace(s, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimpleND()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(2.0);
    m0.setR0C1D(0.0);
    m0.setR0C2D(0.0);

    m0.setR1C0D(0.0);
    m0.setR1C1D(2.0);
    m0.setR1C2D(0.0);

    m0.setR2C0D(0.0);
    m0.setR2C1D(0.0);
    m0.setR2C2D(2.0);

    {
      final boolean r = MatrixM3x3D.invert(c, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM3x3D.invertInPlace(c, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertZeroND()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM3x3D.setZero(m0);

    {
      final boolean r = MatrixM3x3D.invert(c, m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = MatrixM3x3D.invertInPlace(c, m0);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.ContextMM3D mc = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final VectorM3D t = new VectorM3D();
    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(-1.0, 0.0, 0.0);
    final VectorReadable3DType axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM3x3D.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.ContextMM3D mc = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final VectorM3D t = new VectorM3D();
    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(0.0, 0.0, -1.0);
    final VectorReadable3DType axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM3x3D.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.ContextMM3D mc = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final VectorM3D t = new VectorM3D();
    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(1.0, 0.0, 0.0);
    final VectorReadable3DType axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM3x3D.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.ContextMM3D mc = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final VectorM3D t = new VectorM3D();
    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(0.0, 0.0, 1.0);
    final VectorReadable3DType axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM3x3D.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D.ContextMM3D mc = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final VectorM3D t = new VectorM3D();
    final VectorReadable3DType origin = new VectorI3D(
      (double) (20 + 0), (double) (30 + 0), (double) (40 + 0));
    final VectorReadable3DType target = new VectorI3D(
      (double) (20 + 0), (double) (30 + 0), (double) (40 + -1));
    final VectorReadable3DType axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM3x3D.lookAt(mc, origin, target, axis, m, t);

    
    
    
    

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

  @Test public final void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(
          m0.getRowColumnD(row, column) == mr.getRowColumnD(
            row, column));
        Assert.assertTrue(
          m1.getRowColumnD(row, column) == mr.getRowColumnD(
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
          m0.getRowColumnD(row, column) == m1.getRowColumnD(
            row, column));
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertTrue(
          m0.getRowColumnD(row, column) == m1.getRowColumnD(
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

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(6.0);

    m0.setR2C0D(7.0);
    m0.setR2C1D(8.0);
    m0.setR2C2D(9.0);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM3x3D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(30.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(36.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(42.0, r.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(66.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(81.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(96.0, r.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(102.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(126.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(150.0, r.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testMultiplySimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(6.0);

    m0.setR2C0D(7.0);
    m0.setR2C1D(8.0);
    m0.setR2C2D(9.0);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    Assert.assertEquals(30.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(36.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(42.0, r.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(66.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(81.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(96.0, r.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(102.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(126.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(150.0, r.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(6.0);

    m0.setR2C0D(7.0);
    m0.setR2C1D(8.0);
    m0.setR2C2D(9.0);

    this.checkDirectBufferInvariants(m0);

    final VectorReadable3DType v = new VectorI3D(1.0, 2.0, 3.0);
    final VectorM3D out = new VectorM3D();

    final VectorM3D r = MatrixM3x3D.multiplyVector3D(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(14.0, out.getXD(), 0.0);
    Assert.assertEquals(32.0, out.getYD(), 0.0);
    Assert.assertEquals(50.0, out.getZD(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM3x3D.setZero(m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T r = MatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(0.0, mr.getRowColumnD(row, column), 0.0);
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
    m.getRowColumnD(0, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(3, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public final void testRotateDeterminantOrthogonal()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final T mt = this.newMatrix();
    final T mi = this.newMatrix();
    final VectorM3D axis = new VectorM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      double angle = Math.random() * (2.0 * Math.PI);
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

      
      

      MatrixM3x3D.makeRotation(angle, axis, m);

      final double det = MatrixM3x3D.determinant(m);
      

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM3x3D.invert(c, m, mi);
      MatrixM3x3D.transpose(m, mt);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final double mx = mi.getRowColumnD(row, col);
          final double my = mt.getRowColumnD(row, col);
          final boolean eq = AlmostEqualDouble.almostEqual(context, mx, my);

          
          
          

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
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 0.0, -1.0);
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_exp = new VectorM3D(0.0, 0.0, -1.0);

    MatrixM3x3D.makeRotation(0.0, AXIS_X, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public final void testRotateVector0Y()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 0.0, -1.0);
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_exp = new VectorM3D(0.0, 0.0, -1.0);

    MatrixM3x3D.makeRotation(0.0, AXIS_Y, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public final void testRotateVector0Z()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 0.0, -1.0);
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_exp = new VectorM3D(0.0, 0.0, -1.0);

    MatrixM3x3D.makeRotation(0.0, AXIS_Z, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM3D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public final void testRotateVector90X()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 1.0, 0.0);

    /*
      XXX: Strange Y value due to floating point imprecision, with no good
      way to compare it to 0 with an epsilon. The value of Z is the only
      significant element, anyway.
     */

    final VectorReadable3DType v_exp = new VectorM3D(0.0, 6.1232339957367E-17, 1.0);

    MatrixM3x3D.makeRotation(
      Math.toRadians(90.0), AXIS_X, m);
    
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVector90Y()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 0.0, -1.0);

    /*
      XXX: Strange Z value due to floating point imprecision, with no good
      way to compare it to 0 with an epsilon. The value of X is the only
      significant element, anyway.
     */

    final VectorReadable3DType v_exp = new VectorM3D(-1.0, 0.0, -6.1232339957367E-17);

    MatrixM3x3D.makeRotation(
      Math.toRadians(90.0), AXIS_Y, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVector90Z()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 1.0, 0.0);
    final VectorReadable3DType v_exp = new VectorM3D(-1.0, 6.123233995736766E-17, 0.0);

    MatrixM3x3D.makeRotation(
      Math.toRadians(90.0), AXIS_Z, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90X()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 1.0, 0.0);

    /*
      XXX: Strange Y value due to floating point imprecision, with no good
      way to compare it to 0 with an epsilon. The value of Z is the only
      significant element, anyway.
     */

    final VectorReadable3DType v_exp = new VectorM3D(0.0, 6.1232339957367E-17, -1.0);

    MatrixM3x3D.makeRotation(
      Math.toRadians(-90.0), AXIS_X, m);
    
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90Y()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 0.0, -1.0);

    /*
      XXX: Strange Z value due to floating point imprecision, with no good
      way to compare it to 0 with an epsilon. The value of X is the only
      significant element, anyway.
     */

    final VectorReadable3DType v_exp = new VectorM3D(1.0, 0.0, -6.1232339957367E-17);

    MatrixM3x3D.makeRotation(
      Math.toRadians(-90.0), AXIS_Y, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

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

  @Test public final void testRotateVectorMinus90Z()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM3D v_got = new VectorM3D();
    final VectorReadable3DType v_in = new VectorM3D(0.0, 1.0, 0.0);
    final VectorReadable3DType v_exp = new VectorM3D(1.0, 6.123233995736766E-17, 0.0);

    MatrixM3x3D.makeRotation(
      Math.toRadians(-90.0), AXIS_Z, m);
    MatrixM3x3D.multiplyVector3D(c, m, v_in, v_got);

    
    
    
    

    eq = AlmostEqualDouble.almostEqual(context, v_exp.getXD(), v_got.getXD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getYD(), v_got.getYD());
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.getZD(), v_got.getZD());
    Assert.assertTrue(eq);
  }

  @Test public final void testRotateXMakeEquivalent()
  {

    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3D.makeRotation(
        Math.toRadians(45.0),
        AXIS_X,
        r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      

      this.isRotationMatrixX(context_d, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateYMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3D.makeRotation(
        Math.toRadians(45.0),
        AXIS_Y,
        r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      

      this.isRotationMatrixY(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateZMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM3x3D.makeRotation(
        Math.toRadians(45.0),
        AXIS_Z,
        r);
      this.checkDirectBufferInvariants(r);
      Assert.assertEquals(1.0, MatrixM3x3D.determinant(r), 0.0);

      

      this.isRotationMatrixZ(context_d, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRow3D()
  {
    final T m = this.newMatrix();
    final VectorM3D v = new VectorM3D();
    this.checkDirectBufferInvariants(m);

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(1, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(1.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(2, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(1.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3DOverflow()
  {
    final T m = this.newMatrix();
    m.getRow3D(4, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3DUnderflow()
  {
    final T m = this.newMatrix();
    m.getRow3D(-1, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2DOverflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(4, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2DUnderflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(-1, new VectorM3D());
  }

  @Test public final void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m0.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM3x3D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(3.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
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
        m.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m);

    final T mr = MatrixM3x3D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(15.0, m.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testScaleRow()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);

    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);

    this.checkDirectBufferInvariants(m0);

    MatrixM3x3D.scaleRow(c, m0, 0, 2.0, m1);
    MatrixM3x3D.scaleRow(c, m0, 1, 4.0, m1);
    MatrixM3x3D.scaleRow(c, m0, 2, 8.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m1.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(20.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m1.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(72.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m1.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM3x3D.scaleRowInPlace(c, m0, 0, 2.0);
    MatrixM3x3D.scaleRowInPlace(c, m0, 1, 4.0);
    MatrixM3x3D.scaleRowInPlace(c, m0, 2, 8.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m0.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m0.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m0.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(20.0, m0.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m0.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m0.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(72.0, m0.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m0.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m0.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateOverflow()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.scaleRowInPlace(c, m, 3, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateUnderflow()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    MatrixM3x3D.scaleRowInPlace(c, m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowOverflow()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM3x3D.scaleRow(c, m, 3, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowUnderflow()
  {
    final MatrixM3x3D.ContextMM3D c = new MatrixM3x3D.ContextMM3D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM3x3D.scaleRow(c, m, -1, 1.0, r);
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

  @Test public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    {
      MatrixM3x3D.setZero(m);
      m.setR0C0D(13.0);
      Assert.assertEquals(13.0, m.getR0C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 0), 0.0);
      this.checkZeroExcept(m, 0, 0);

      MatrixM3x3D.setZero(m);
      m.setR1C0D(13.0);
      Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 0), 0.0);
      this.checkZeroExcept(m, 1, 0);

      MatrixM3x3D.setZero(m);
      m.setR2C0D(13.0);
      Assert.assertEquals(13.0, m.getR2C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 0), 0.0);
      this.checkZeroExcept(m, 2, 0);
    }

    {
      MatrixM3x3D.setZero(m);
      m.setR0C1D(13.0);
      Assert.assertEquals(13.0, m.getR0C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 1), 0.0);
      this.checkZeroExcept(m, 0, 1);

      MatrixM3x3D.setZero(m);
      m.setR1C1D(13.0);
      Assert.assertEquals(13.0, m.getR1C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 1), 0.0);
      this.checkZeroExcept(m, 1, 1);

      MatrixM3x3D.setZero(m);
      m.setR2C1D(13.0);
      Assert.assertEquals(13.0, m.getR2C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 1), 0.0);
      this.checkZeroExcept(m, 2, 1);
    }

    {
      MatrixM3x3D.setZero(m);
      m.setR0C2D(13.0);
      Assert.assertEquals(13.0, m.getR0C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 2), 0.0);
      this.checkZeroExcept(m, 0, 2);

      MatrixM3x3D.setZero(m);
      m.setR1C2D(13.0);
      Assert.assertEquals(13.0, m.getR1C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 2), 0.0);
      this.checkZeroExcept(m, 1, 2);

      MatrixM3x3D.setZero(m);
      m.setR2C2D(13.0);
      Assert.assertEquals(13.0, m.getR2C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 2), 0.0);
      this.checkZeroExcept(m, 2, 2);
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testSetGetInterfaceIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    m.setRowColumnD(0, 0, 3.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 0), 0.0);
    m.setRowColumnD(0, 1, 5.0);
    Assert.assertEquals(5.0, m.getRowColumnD(0, 1), 0.0);
    m.setRowColumnD(0, 2, 7.0);
    Assert.assertEquals(7.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    m.setRowColumnD(1, 0, 13.0);
    Assert.assertEquals(13.0, m.getRowColumnD(1, 0), 0.0);
    m.setRowColumnD(1, 1, 17.0);
    Assert.assertEquals(17.0, m.getRowColumnD(1, 1), 0.0);
    m.setRowColumnD(1, 2, 19.0);
    Assert.assertEquals(19.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    m.setRowColumnD(2, 0, 29.0);
    Assert.assertEquals(29.0, m.getRowColumnD(2, 0), 0.0);
    m.setRowColumnD(2, 1, 31.0);
    Assert.assertEquals(31.0, m.getRowColumnD(2, 1), 0.0);
    m.setRowColumnD(2, 2, 37.0);
    Assert.assertEquals(37.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTraceIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(3.0, MatrixM3x3D.trace(m), 0.0);
  }

  @Test public final void testTranslate2DMakeIdentity()
  {
    final T m = this.newMatrix();

    final VectorReadable2DType v = new VectorM2D(0.0, 0.0);

    MatrixM3x3D.makeTranslation2D(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2DMakeSimple()
  {
    final T m = this.newMatrix();

    final VectorReadable2DType v = new VectorM2D(3.0, 7.0);

    MatrixM3x3D.makeTranslation2D(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(7.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2IMakeIdentity()
  {
    final T m = this.newMatrix();

    final VectorReadable2IType v = new VectorM2I(0, 0);

    MatrixM3x3D.makeTranslation2I(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslate2IMakeSimple()
  {
    final T m = this.newMatrix();

    final VectorReadable2IType v = new VectorM2I(3, 7);

    MatrixM3x3D.makeTranslation2I(v, m);
    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(7.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setR0C0D(0.0);
    m.setR0C1D(1.0);
    m.setR0C2D(2.0);
    m.setR1C0D(4.0);
    m.setR1C1D(5.0);
    m.setR1C2D(6.0);
    m.setR2C0D(8.0);
    m.setR2C1D(9.0);
    m.setR2C2D(10.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    final T k = MatrixM3x3D.transpose(m, r);
    Assert.assertSame(k, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(2.0, m.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(4.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(6.0, m.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(8.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(9.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, m.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(8.0, r.getRowColumnD(0, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(1.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(9.0, r.getRowColumnD(1, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(2.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(6.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, r.getRowColumnD(2, 2), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setR0C0D(0.0);
    m.setR0C1D(1.0);
    m.setR0C2D(2.0);

    m.setR1C0D(4.0);
    m.setR1C1D(5.0);
    m.setR1C2D(6.0);

    m.setR2C0D(8.0);
    m.setR2C1D(9.0);
    m.setR2C2D(10.0);

    this.checkDirectBufferInvariants(m);

    final T r = MatrixM3x3D.transposeInPlace(m);
    Assert.assertSame(m, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(8.0, r.getRowColumnD(0, 2), 0.0);

    Assert.assertEquals(1.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(9.0, r.getRowColumnD(1, 2), 0.0);

    Assert.assertEquals(2.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(6.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, r.getRowColumnD(2, 2), 0.0);
  }

  @Test public final void testZero()
  {
    final T m = this.newMatrix();
    MatrixM3x3D.setZero(m);

    this.checkDirectBufferInvariants(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(0.0, m.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM3D v = new VectorM3D(0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3D(0, new VectorI3D(1.0, 2.0, 3.0));
    m.setRowWith3D(1, new VectorI3D(10.0, 20.0, 30.0));
    m.setRowWith3D(2, new VectorI3D(100.0, 200.0, 300.0));

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Override
  protected abstract void checkDirectBufferInvariants(final T m);

  @Test public final void testRowSet3Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM3D v = new VectorM3D(0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3D(0, new VectorI3D(1.0, 2.0, 3.0));
    m.setRowWith3D(1, new VectorI3D(10.0, 20.0, 30.0));
    m.setRowWith3D(2, new VectorI3D(100.0, 200.0, 300.0));

    m.getRow3DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI2D(1.0, 2.0));
    m.setRowWith2D(1, new VectorI2D(10.0, 20.0));
    m.setRowWith2D(2, new VectorI2D(100.0, 200.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI2D(1.0, 2.0));
    m.setRowWith2D(1, new VectorI2D(10.0, 20.0));
    m.setRowWith2D(2, new VectorI2D(100.0, 200.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
