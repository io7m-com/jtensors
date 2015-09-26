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
import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable3DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix4x4DContract<T extends Matrix4x4DType>
  extends MatrixReadable4x4DContract<T>
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(
    1.0, 0.0, 0.0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(
    0.0, 1.0, 0.0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(
    0.0, 0.0, 1.0);

  protected abstract T newMatrix();

  protected abstract T newMatrixFrom(MatrixReadable4x4DType m);

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
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(3, 3));
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
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.getRowColumnD(3, 3));
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
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, r.getRowColumnD(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, r.getRowColumnD(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0, r.getRowColumnD(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0, r.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testAdd()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM4x4D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(1.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(3.0, m1.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(4.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.setRowColumnD(row, column, 1.0);
        m1.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T mr = MatrixM4x4D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
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
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 3.0);
    m0.setRowColumnD(0, 1, 3.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 3.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 5.0);
    m0.setRowColumnD(1, 2, 5.0);
    m0.setRowColumnD(1, 3, 5.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.addRowScaled(c, m0, 0, 1, 2, 2.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.addRowScaledInPlace(c, m0, 0, 1, 2, 2.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m0.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(3.0, m0.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m0.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(3.0, m0.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(5.0, m0.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, m0.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(5.0, m0.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(5.0, m0.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(13.0, m0.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(13.0, m0.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(13.0, m0.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(13.0, m0.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(0.0, m0.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(0.0, m0.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(0.0, m0.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(1.0, m0.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testAddRowScaledContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 3.0);
    m0.setRowColumnD(0, 1, 3.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 3.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 5.0);
    m0.setRowColumnD(1, 2, 5.0);
    m0.setRowColumnD(1, 3, 5.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.addRowScaled(context, m0, 0, 1, 2, 2.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(13.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 2.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 4.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 6.0);
    m0.setRowColumnD(1, 2, 7.0);
    m0.setRowColumnD(1, 3, 8.0);

    m0.setRowColumnD(2, 0, 9.0);
    m0.setRowColumnD(2, 1, 10.0);
    m0.setRowColumnD(2, 2, 11.0);
    m0.setRowColumnD(2, 3, 12.0);

    m0.setRowColumnD(3, 0, 13.0);
    m0.setRowColumnD(3, 1, 14.0);
    m0.setRowColumnD(3, 2, 15.0);
    m0.setRowColumnD(3, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.copy(m0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(5.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(6.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(7.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(8.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(9.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(10.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(11.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(12.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(14.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(15.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(16.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, MatrixM4x4D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, 2.0);
    m.setRowColumnD(1, 1, 2.0);
    m.setRowColumnD(2, 2, 2.0);
    m.setRowColumnD(3, 3, 2.0);

    Assert.assertEquals(16.0, MatrixM4x4D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, 2.0);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(2.0, MatrixM4x4D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, -2.0);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(-2.0, MatrixM4x4D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantZero()
  {
    final T m = this.newMatrix();
    MatrixM4x4D.setZero(m);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(0.0, MatrixM4x4D.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newMatrix();
      Assert.assertTrue(m0.equals(m0));
      this.checkDirectBufferInvariants(m0);
    }

    {
      final T m0 = this.newMatrix();
      Assert.assertFalse(m0.equals(null));
      this.checkDirectBufferInvariants(m0);
    }

    {
      final T m0 = this.newMatrix();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
      this.checkDirectBufferInvariants(m0);
    }

    {
      final T m0 = this.newMatrix();
      final T m1 = this.newMatrix();
      Assert.assertEquals(m0, m1);
      Assert.assertNotSame(m0, m1);
      this.checkDirectBufferInvariants(m0);
    }
  }

  @Test public final void testEqualsNotEqualsCorrect()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        m1.setRowColumnD(row, col, 256.0);
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testExchangeRows()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 2.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 4.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 6.0);
    m0.setRowColumnD(1, 2, 7.0);
    m0.setRowColumnD(1, 3, 8.0);

    m0.setRowColumnD(2, 0, 9.0);
    m0.setRowColumnD(2, 1, 10.0);
    m0.setRowColumnD(2, 2, 11.0);
    m0.setRowColumnD(2, 3, 12.0);

    m0.setRowColumnD(3, 0, 13.0);
    m0.setRowColumnD(3, 1, 14.0);
    m0.setRowColumnD(3, 2, 15.0);
    m0.setRowColumnD(3, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.exchangeRows(c, m0, 0, 3, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(13.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(14.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(15.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(16.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(1.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.exchangeRowsInPlace(c, m1, 0, 3);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(14.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(15.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(16.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAOverflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.exchangeRowsInPlace(c, m, 4, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsAUnderflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.exchangeRowsInPlace(c, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBOverflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.exchangeRowsInPlace(c, m, 0, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testExchangeRowsBUnderflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.exchangeRowsInPlace(c, m, 0, -1);
  }

  @Test public final void testExchangeRowsContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 2.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 4.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 6.0);
    m0.setRowColumnD(1, 2, 7.0);
    m0.setRowColumnD(1, 3, 8.0);

    m0.setRowColumnD(2, 0, 9.0);
    m0.setRowColumnD(2, 1, 10.0);
    m0.setRowColumnD(2, 2, 11.0);
    m0.setRowColumnD(2, 3, 12.0);

    m0.setRowColumnD(3, 0, 13.0);
    m0.setRowColumnD(3, 1, 14.0);
    m0.setRowColumnD(3, 2, 15.0);
    m0.setRowColumnD(3, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.exchangeRows(context, m0, 0, 3, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(13.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(14.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(15.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(16.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(1.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.exchangeRowsInPlace(context, m1, 0, 3);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(2.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(3.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(14.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(15.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(16.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setRowColumnD(0, 0, 3.0);
    m0.setRowColumnD(0, 1, 5.0);
    m0.setRowColumnD(0, 2, 7.0);
    m0.setRowColumnD(0, 3, 11.0);

    m0.setRowColumnD(1, 0, 13.0);
    m0.setRowColumnD(1, 1, 17.0);
    m0.setRowColumnD(1, 2, 19.0);
    m0.setRowColumnD(1, 3, 23.0);

    m0.setRowColumnD(2, 0, 29.0);
    m0.setRowColumnD(2, 1, 31.0);
    m0.setRowColumnD(2, 2, 37.0);
    m0.setRowColumnD(2, 3, 41.0);

    m0.setRowColumnD(3, 0, 43.0);
    m0.setRowColumnD(3, 1, 47.0);
    m0.setRowColumnD(3, 2, 53.0);
    m0.setRowColumnD(3, 3, 59.0);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(5.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(7.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(11.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(13.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(17.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(19.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(23.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(29.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(31.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(37.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(41.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(43.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(47.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(53.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(59.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(0.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(0.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(0.0, m.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(0.0, m.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testInvertIdentity()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(c, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertIdentityContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 2.0);
    m0.setRowColumnD(0, 1, 0.0);
    m0.setRowColumnD(0, 2, 0.0);
    m0.setRowColumnD(0, 3, 0.0);

    m0.setRowColumnD(1, 0, 0.0);
    m0.setRowColumnD(1, 1, 2.0);
    m0.setRowColumnD(1, 2, 0.0);
    m0.setRowColumnD(1, 3, 0.0);

    m0.setRowColumnD(2, 0, 0.0);
    m0.setRowColumnD(2, 1, 0.0);
    m0.setRowColumnD(2, 2, 2.0);
    m0.setRowColumnD(2, 3, 0.0);

    m0.setRowColumnD(3, 0, 0.0);
    m0.setRowColumnD(3, 1, 0.0);
    m0.setRowColumnD(3, 2, 0.0);
    m0.setRowColumnD(3, 3, 2.0);

    {
      final boolean r = MatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(c, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple2()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    boolean eq = false;

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 0.0);
    m0.setRowColumnD(0, 2, 5.0);
    m0.setRowColumnD(0, 3, 0.0);

    m0.setRowColumnD(1, 0, 0.0);
    m0.setRowColumnD(1, 1, 2.0);
    m0.setRowColumnD(1, 2, 0.0);
    m0.setRowColumnD(1, 3, 11.0);

    m0.setRowColumnD(2, 0, 7.0);
    m0.setRowColumnD(2, 1, 0.0);
    m0.setRowColumnD(2, 2, 3.0);
    m0.setRowColumnD(2, 3, 0.0);

    m0.setRowColumnD(3, 0, 0.0);
    m0.setRowColumnD(3, 1, 13.0);
    m0.setRowColumnD(3, 2, 0.0);
    m0.setRowColumnD(3, 3, 4.0);

    {
      final boolean r = MatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(c, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 0), 1.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 2), 5.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 1), 2.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 3), 11.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 0), 7.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 2), 3.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 1), 13.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 3), 4.0);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    boolean eq = false;

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 0.0);
    m0.setRowColumnD(0, 2, 5.0);
    m0.setRowColumnD(0, 3, 0.0);

    m0.setRowColumnD(1, 0, 0.0);
    m0.setRowColumnD(1, 1, 2.0);
    m0.setRowColumnD(1, 2, 0.0);
    m0.setRowColumnD(1, 3, 11.0);

    m0.setRowColumnD(2, 0, 7.0);
    m0.setRowColumnD(2, 1, 0.0);
    m0.setRowColumnD(2, 2, 3.0);
    m0.setRowColumnD(2, 3, 0.0);

    m0.setRowColumnD(3, 0, 0.0);
    m0.setRowColumnD(3, 1, 13.0);
    m0.setRowColumnD(3, 2, 0.0);
    m0.setRowColumnD(3, 3, 4.0);

    {
      final boolean r = MatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, rm.getRowColumnD(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 0), 1.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 2), 5.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 1), 2.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(1, 3), 11.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 0), 7.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 2), 3.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 1), 13.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, rm.getRowColumnD(3, 3), 4.0);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimpleContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setRowColumnD(0, 0, 2.0);
    m0.setRowColumnD(0, 1, 0.0);
    m0.setRowColumnD(0, 2, 0.0);
    m0.setRowColumnD(0, 3, 0.0);

    m0.setRowColumnD(1, 0, 0.0);
    m0.setRowColumnD(1, 1, 2.0);
    m0.setRowColumnD(1, 2, 0.0);
    m0.setRowColumnD(1, 3, 0.0);

    m0.setRowColumnD(2, 0, 0.0);
    m0.setRowColumnD(2, 1, 0.0);
    m0.setRowColumnD(2, 2, 2.0);
    m0.setRowColumnD(2, 3, 0.0);

    m0.setRowColumnD(3, 0, 0.0);
    m0.setRowColumnD(3, 1, 0.0);
    m0.setRowColumnD(3, 2, 0.0);
    m0.setRowColumnD(3, 3, 2.0);

    {
      final boolean r = MatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(0.5, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, rm.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, rm.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, rm.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(2.0, rm.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertZero()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM4x4D.setZero(m0);

    {
      final boolean r = MatrixM4x4D.invert(c, m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(c, m0);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testInvertZeroContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM4x4D.setZero(m0);

    {
      final boolean r = MatrixM4x4D.invert(context, m0, m1);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }

    {
      final boolean r = MatrixM4x4D.invertInPlace(context, m0);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }
  }

  @Test public final void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final VectorI3D origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D target = new VectorI3D(-1.0, 0.0, 0.0);
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM4x4D.lookAt(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final VectorI3D origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D target = new VectorI3D(0.0, 0.0, -1.0);
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM4x4D.lookAt(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final VectorI3D origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D target = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM4x4D.lookAt(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final VectorI3D origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D target = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM4x4D.lookAt(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final VectorI3D origin = new VectorI3D(
      (double) (20 + 0), (double) (30 + 0), (double) (40 + 0));
    final VectorI3D target = new VectorI3D(
      (double) (20 + 0), (double) (30 + 0), (double) (40 + -1));
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    MatrixM4x4D.lookAt(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, -20.0, m.getRowColumnD(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -30.0, m.getRowColumnD(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -40.0, m.getRowColumnD(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.getRowColumnD(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.getRowColumnD(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m1.getRowColumnD(row, column), 0.0);
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        this.checkDirectBufferInvariants(mr);
      }
    }
  }

  @Test public final void testMultiplyMutateIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(
          m1.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T r = MatrixM4x4D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(
          m1.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testMultiplyMutateSimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);
    m0.setR0C3D(4.0);
    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);
    m0.setR1C3D(8.0);
    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);
    m0.setR2C3D(12.0);
    m0.setR3C0D(13.0);
    m0.setR3C1D(14.0);
    m0.setR3C2D(15.0);
    m0.setR3C3D(16.0);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM4x4D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(90.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(100.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(110.0, r.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(120.0, r.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(202.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(228.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(254.0, r.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(280.0, r.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(314.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(356.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(398.0, r.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(440.0, r.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(426.0, r.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(484.0, r.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(542.0, r.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(600.0, r.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testMultiplySimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);
    m0.setR0C3D(4.0);
    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);
    m0.setR1C3D(8.0);
    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);
    m0.setR2C3D(12.0);
    m0.setR3C0D(13.0);
    m0.setR3C1D(14.0);
    m0.setR3C2D(15.0);
    m0.setR3C3D(16.0);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    final T r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    Assert.assertEquals(90.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(100.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(110.0, r.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(120.0, r.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(202.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(228.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(254.0, r.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(280.0, r.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(314.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(356.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(398.0, r.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(440.0, r.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(426.0, r.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(484.0, r.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(542.0, r.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(600.0, r.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);
    m0.setR0C3D(4.0);
    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);
    m0.setR1C3D(8.0);
    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);
    m0.setR2C3D(12.0);
    m0.setR3C0D(13.0);
    m0.setR3C1D(14.0);
    m0.setR3C2D(15.0);
    m0.setR3C3D(16.0);

    final VectorI4D v = new VectorI4D(1.0, 2.0, 3.0, 4.0);
    final VectorM4D out = new VectorM4D();

    final VectorM4D r = MatrixM4x4D.multiplyVector4D(c, m0, v, out);
    Assert.assertSame(out, r);

    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(30.0, out.getXD(), 0.0);
    Assert.assertEquals(70.0, out.getYD(), 0.0);
    Assert.assertEquals(110.0, out.getZD(), 0.0);
    Assert.assertEquals(150.0, out.getWD(), 0.0);
  }

  @Test public final void testMultiplyVectorSimpleContextEquivalent()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);
    m0.setR0C3D(4.0);
    m0.setR1C0D(5.0);
    m0.setR1C1D(6.0);
    m0.setR1C2D(7.0);
    m0.setR1C3D(8.0);
    m0.setR2C0D(9.0);
    m0.setR2C1D(10.0);
    m0.setR2C2D(11.0);
    m0.setR2C3D(12.0);
    m0.setR3C0D(13.0);
    m0.setR3C1D(14.0);
    m0.setR3C2D(15.0);
    m0.setR3C3D(16.0);

    final VectorI4D v = new VectorI4D(1.0, 2.0, 3.0, 4.0);
    final VectorM4D out = new VectorM4D();
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();

    final VectorM4D r = MatrixM4x4D.multiplyVector4D(context, m0, v, out);
    Assert.assertSame(out, r);

    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(30.0, out.getXD(), 0.0);
    Assert.assertEquals(70.0, out.getYD(), 0.0);
    Assert.assertEquals(110.0, out.getZD(), 0.0);
    Assert.assertEquals(150.0, out.getWD(), 0.0);
  }

  @Test public final void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM4x4D.setZero(m1);

    final T r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
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
    m.getRowColumnD(0, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnD(4, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public final void testRotateDeterminantOrthogonal()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
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

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      MatrixM4x4D.makeRotation(angle, axis, m);

      final double det = MatrixM4x4D.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM4x4D.invert(c, m, mi);
      MatrixM4x4D.transpose(m, mt);

      for (int row = 0; row < 4; ++row) {
        for (int col = 0; col < 4; ++col) {
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

  @Test public final void testRotateVector0X()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorM4D v_in = new VectorM4D(0.0, 0.0, -1.0, 1.0);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0.0, 0.0, -1.0, 1.0);

    MatrixM4x4D.makeRotation(0.0, Matrix4x4DContract.AXIS_X, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public final void testRotateVector0Y()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorM4D v_in = new VectorM4D(0.0, 0.0, -1.0, 1.0);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0.0, 0.0, -1.0, 1.0);

    MatrixM4x4D.makeRotation(0.0, Matrix4x4DContract.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public final void testRotateVector0Z()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final VectorM4D v_in = new VectorM4D(0.0, 0.0, -1.0, 1.0);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0.0, 0.0, -1.0, 1.0);

    MatrixM4x4D.makeRotation(0.0, Matrix4x4DContract.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public final void testRotateVector90X()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 1.0, 0.0, 1.0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(0.0, 6.1232339957367E-17, 1.0, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(90.0), Matrix4x4DContract.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateVector90Y()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 0.0, -1.0, 1.0);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(-1.0, 0.0, -6.1232339957367E-17, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(90.0), Matrix4x4DContract.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateVector90Z()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 1.0, 0.0, 1.0);
    final VectorM4D v_exp =
      new VectorM4D(-1.0, 6.123233995736766E-17, 0.0, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(90.0), Matrix4x4DContract.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateVectorMinus90X()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 1.0, 0.0, 1.0);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(0.0, 6.1232339957367E-17, -1.0, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(-90.0), Matrix4x4DContract.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateVectorMinus90Y()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 0.0, -1.0, 1.0);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(1.0, 0.0, -6.1232339957367E-17, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(-90.0), Matrix4x4DContract.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateVectorMinus90Z()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0.0, 1.0, 0.0, 1.0);
    final VectorM4D v_exp = new VectorM4D(1.0, 6.123233995736766E-17, 0.0, 1.0);

    MatrixM4x4D.makeRotation(
      Math.toRadians(-90.0), Matrix4x4DContract.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(c, m, v_in, v_got);

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

  @Test public final void testRotateXMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM4x4D.makeRotation(
        Math.toRadians(45.0), Matrix4x4DContract.AXIS_X, r);
      this.checkDirectBufferInvariants(r);

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      this.isRotationMatrixX(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateYMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM4x4D.makeRotation(
        Math.toRadians(45.0), Matrix4x4DContract.AXIS_Y, r);
      this.checkDirectBufferInvariants(r);

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      this.isRotationMatrixY(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateZMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM4x4D.makeRotation(
        Math.toRadians(45.0), Matrix4x4DContract.AXIS_Z, r);
      this.checkDirectBufferInvariants(r);

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      this.isRotationMatrixZ(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRow4()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariants(m);

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(1, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(1.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(2, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(1.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(3, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(1.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSetGet4()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariants(m);

    m.setRowWith4D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith4D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith4D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith4D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(4.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(40.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(400.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(4000.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSetGet4Static()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariants(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(4.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(40.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(400.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(4000.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get3()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get2()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(00.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow4Overflow()
  {
    final T m = this.newMatrix();
    m.getRow4D(4, new VectorM4D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow4Underflow()
  {
    final T m = this.newMatrix();
    m.getRow4D(-1, new VectorM4D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2Overflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(4, new VectorM3D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3Overflow()
  {
    final T m = this.newMatrix();
    m.getRow3D(-1, new VectorM4D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3Underflow()
  {
    final T m = this.newMatrix();
    m.getRow3D(-1, new VectorM4D());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2Underflow()
  {
    final T m = this.newMatrix();
    m.getRow2D(-1, new VectorM3D());
  }

  @Test public final void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.setRowColumnD(row, column, 3.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM4x4D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);
    this.checkDirectBufferInvariants(mk);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(3.0, m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);
    this.checkDirectBufferInvariants(mk);
  }

  @Test public final void testScaleMutate()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m.setRowColumnD(row, column, 3.0);
      }
    }

    final T mr = MatrixM4x4D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(15.0, m.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(15.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testScaleRow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 2.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 4.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 6.0);
    m0.setRowColumnD(1, 2, 7.0);
    m0.setRowColumnD(1, 3, 8.0);

    m0.setRowColumnD(2, 0, 9.0);
    m0.setRowColumnD(2, 1, 10.0);
    m0.setRowColumnD(2, 2, 11.0);
    m0.setRowColumnD(2, 3, 12.0);

    m0.setRowColumnD(3, 0, 13.0);
    m0.setRowColumnD(3, 1, 14.0);
    m0.setRowColumnD(3, 2, 15.0);
    m0.setRowColumnD(3, 3, 16.0);

    System.out.println(m0.toString());

    MatrixM4x4D.scaleRow(c, m0, 0, 2.0, m1);
    MatrixM4x4D.scaleRow(c, m0, 1, 4.0, m1);
    MatrixM4x4D.scaleRow(c, m0, 2, 8.0, m1);
    MatrixM4x4D.scaleRow(c, m0, 3, 16.0, m1);

    System.out.println(m1.toString());

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(8.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(20.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(32.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(72.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(96.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(208.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(224.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(240.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(256.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.scaleRowInPlace(c, m0, 0, 2.0);
    MatrixM4x4D.scaleRowInPlace(c, m0, 1, 4.0);
    MatrixM4x4D.scaleRowInPlace(c, m0, 2, 8.0);
    MatrixM4x4D.scaleRowInPlace(c, m0, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m0.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m0.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m0.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(8.0, m0.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(20.0, m0.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m0.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m0.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(32.0, m0.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(72.0, m0.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m0.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m0.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(96.0, m0.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(208.0, m0.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(224.0, m0.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(240.0, m0.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(256.0, m0.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testScaleRowContextEquivalent()
  {
    final MatrixM4x4D.ContextMM4D context = new MatrixM4x4D.ContextMM4D();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setRowColumnD(0, 0, 1.0);
    m0.setRowColumnD(0, 1, 2.0);
    m0.setRowColumnD(0, 2, 3.0);
    m0.setRowColumnD(0, 3, 4.0);

    m0.setRowColumnD(1, 0, 5.0);
    m0.setRowColumnD(1, 1, 6.0);
    m0.setRowColumnD(1, 2, 7.0);
    m0.setRowColumnD(1, 3, 8.0);

    m0.setRowColumnD(2, 0, 9.0);
    m0.setRowColumnD(2, 1, 10.0);
    m0.setRowColumnD(2, 2, 11.0);
    m0.setRowColumnD(2, 3, 12.0);

    m0.setRowColumnD(3, 0, 13.0);
    m0.setRowColumnD(3, 1, 14.0);
    m0.setRowColumnD(3, 2, 15.0);
    m0.setRowColumnD(3, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.scaleRow(context, m0, 0, 2.0, m1);
    MatrixM4x4D.scaleRow(context, m0, 1, 4.0, m1);
    MatrixM4x4D.scaleRow(context, m0, 2, 8.0, m1);
    MatrixM4x4D.scaleRow(context, m0, 3, 16.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m1.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m1.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m1.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(8.0, m1.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(20.0, m1.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m1.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m1.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(32.0, m1.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(72.0, m1.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m1.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m1.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(96.0, m1.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(208.0, m1.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(224.0, m1.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(240.0, m1.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(256.0, m1.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4D.scaleRowInPlace(context, m0, 0, 2.0);
    MatrixM4x4D.scaleRowInPlace(context, m0, 1, 4.0);
    MatrixM4x4D.scaleRowInPlace(context, m0, 2, 8.0);
    MatrixM4x4D.scaleRowInPlace(context, m0, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, m0.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, m0.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(6.0, m0.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(8.0, m0.getRowColumnD(0, 3), 0.0);

    Assert.assertEquals(20.0, m0.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(24.0, m0.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(28.0, m0.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(32.0, m0.getRowColumnD(1, 3), 0.0);

    Assert.assertEquals(72.0, m0.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(80.0, m0.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(88.0, m0.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(96.0, m0.getRowColumnD(2, 3), 0.0);

    Assert.assertEquals(208.0, m0.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(224.0, m0.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(240.0, m0.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(256.0, m0.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateOverflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.scaleRowInPlace(c, m, 4, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowMutateUnderflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.scaleRowInPlace(c, m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowOverflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM4x4D.scaleRow(c, m, 4, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testScaleRowUnderflow()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM4x4D.scaleRow(c, m, -1, 1.0, r);
  }

  @Test public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    {
      MatrixM4x4D.setZero(m);
      m.setR0C0D(13.0);
      Assert.assertEquals(13.0, m.getR0C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 0), 0.0);
      this.checkZeroExcept(m, 0, 0);

      MatrixM4x4D.setZero(m);
      m.setR1C0D(13.0);
      Assert.assertEquals(13.0, m.getR1C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 0), 0.0);
      this.checkZeroExcept(m, 1, 0);

      MatrixM4x4D.setZero(m);
      m.setR2C0D(13.0);
      Assert.assertEquals(13.0, m.getR2C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 0), 0.0);
      this.checkZeroExcept(m, 2, 0);

      MatrixM4x4D.setZero(m);
      m.setR3C0D(13.0);
      Assert.assertEquals(13.0, m.getR3C0D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(3, 0), 0.0);
      this.checkZeroExcept(m, 3, 0);
    }

    {
      MatrixM4x4D.setZero(m);
      m.setR0C1D(13.0);
      Assert.assertEquals(13.0, m.getR0C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 1), 0.0);
      this.checkZeroExcept(m, 0, 1);

      MatrixM4x4D.setZero(m);
      m.setR1C1D(13.0);
      Assert.assertEquals(13.0, m.getR1C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 1), 0.0);
      this.checkZeroExcept(m, 1, 1);

      MatrixM4x4D.setZero(m);
      m.setR2C1D(13.0);
      Assert.assertEquals(13.0, m.getR2C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 1), 0.0);
      this.checkZeroExcept(m, 2, 1);

      MatrixM4x4D.setZero(m);
      m.setR3C1D(13.0);
      Assert.assertEquals(13.0, m.getR3C1D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(3, 1), 0.0);
      this.checkZeroExcept(m, 3, 1);
    }

    {
      MatrixM4x4D.setZero(m);
      m.setR0C2D(13.0);
      Assert.assertEquals(13.0, m.getR0C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 2), 0.0);
      this.checkZeroExcept(m, 0, 2);

      MatrixM4x4D.setZero(m);
      m.setR1C2D(13.0);
      Assert.assertEquals(13.0, m.getR1C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 2), 0.0);
      this.checkZeroExcept(m, 1, 2);

      MatrixM4x4D.setZero(m);
      m.setR2C2D(13.0);
      Assert.assertEquals(13.0, m.getR2C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 2), 0.0);
      this.checkZeroExcept(m, 2, 2);

      MatrixM4x4D.setZero(m);
      m.setR3C2D(13.0);
      Assert.assertEquals(13.0, m.getR3C2D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(3, 2), 0.0);
      this.checkZeroExcept(m, 3, 2);
    }

    {
      MatrixM4x4D.setZero(m);
      m.setR0C3D(13.0);
      Assert.assertEquals(13.0, m.getR0C3D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(0, 3), 0.0);
      this.checkZeroExcept(m, 0, 3);

      MatrixM4x4D.setZero(m);
      m.setR1C3D(13.0);
      Assert.assertEquals(13.0, m.getR1C3D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(1, 3), 0.0);
      this.checkZeroExcept(m, 1, 3);

      MatrixM4x4D.setZero(m);
      m.setR2C3D(13.0);
      Assert.assertEquals(13.0, m.getR2C3D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(2, 3), 0.0);
      this.checkZeroExcept(m, 2, 3);

      MatrixM4x4D.setZero(m);
      m.setR3C3D(13.0);
      Assert.assertEquals(13.0, m.getR3C3D(), 0.0);
      Assert.assertEquals(13.0, m.getRowColumnD(3, 3), 0.0);
      this.checkZeroExcept(m, 3, 3);
    }

    this.checkDirectBufferInvariants(m);
  }

  private void checkZeroExcept(
    final T m,
    final int row,
    final int col)
  {
    for (int r = 0; r < 4; ++r) {
      for (int c = 0; c < 4; ++c) {
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
    m.setRowColumnD(0, 3, 11.0);
    Assert.assertEquals(11.0, m.getRowColumnD(0, 3), 0.0);

    m.setRowColumnD(1, 0, 13.0);
    Assert.assertEquals(13.0, m.getRowColumnD(1, 0), 0.0);
    m.setRowColumnD(1, 1, 17.0);
    Assert.assertEquals(17.0, m.getRowColumnD(1, 1), 0.0);
    m.setRowColumnD(1, 2, 19.0);
    Assert.assertEquals(19.0, m.getRowColumnD(1, 2), 0.0);
    m.setRowColumnD(1, 3, 23.0);
    Assert.assertEquals(23.0, m.getRowColumnD(1, 3), 0.0);

    m.setRowColumnD(2, 0, 29.0);
    Assert.assertEquals(29.0, m.getRowColumnD(2, 0), 0.0);
    m.setRowColumnD(2, 1, 31.0);
    Assert.assertEquals(31.0, m.getRowColumnD(2, 1), 0.0);
    m.setRowColumnD(2, 2, 37.0);
    Assert.assertEquals(37.0, m.getRowColumnD(2, 2), 0.0);
    m.setRowColumnD(2, 3, 41.0);
    Assert.assertEquals(41.0, m.getRowColumnD(2, 3), 0.0);

    m.setRowColumnD(3, 0, 43.0);
    Assert.assertEquals(43.0, m.getRowColumnD(3, 0), 0.0);
    m.setRowColumnD(3, 1, 47.0);
    Assert.assertEquals(47.0, m.getRowColumnD(3, 1), 0.0);
    m.setRowColumnD(3, 2, 53.0);
    Assert.assertEquals(53.0, m.getRowColumnD(3, 2), 0.0);
    m.setRowColumnD(3, 3, 59.0);
    Assert.assertEquals(59.0, m.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testSetIdentity()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m.setRowColumnD(row, col, (double) (float) Math.random());
      }
    }

    this.checkDirectBufferInvariants(m);
    MatrixM4x4D.setIdentity(m);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTrace()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final T m = this.newMatrix();
    final double t = MatrixM4x4D.trace(m);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 4.0, t));
  }

  @Test public final void testTranslate3_4_Equivalent()
  {
    final MatrixM3x3D.ContextMM3D c3 = new MatrixM3x3D.ContextMM3D();
    final MatrixM4x4D.ContextMM4D c4 = new MatrixM4x4D.ContextMM4D();
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType m3 = MatrixHeapArrayM3x3D.newMatrix();
    final T m4 = this.newMatrix();
    final VectorI3D v = new VectorI3D(3.0, 7.0, 0.0);
    final VectorM3D v3i = new VectorM3D(1.0, 1.0, 1.0);
    final VectorM3D v3o = new VectorM3D();
    final VectorM4D w3i = new VectorM4D(1.0, 1.0, 1.0, 1.0);
    final VectorM4D w3o = new VectorM4D();

    MatrixM3x3D.makeTranslation2D(v, m3);
    MatrixM4x4D.makeTranslation3D(v, m4);

    MatrixM3x3D.multiplyVector3D(c3, m3, v3i, v3o);
    MatrixM4x4D.multiplyVector4D(c4, m4, w3i, w3o);

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, v3o.getXD(), w3o.getXD()));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, v3o.getYD(), w3o.getYD()));
  }

  @Test public final void testTranslationMakeEquivalent2Integer()
  {
    final T m = this.newMatrix();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4D.makeTranslation2I(v, t);
      MatrixM4x4D.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
  }

  protected abstract void checkDirectBufferInvariants(final T m);

  @Test public final void testTranslationMakeEquivalent3Integer()
  {
    final T m = this.newMatrix();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4D.makeTranslation3I(v, t);
      MatrixM4x4D.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(3.0, r.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testTranslationMakeEquivalent2Real()
  {
    final T m = this.newMatrix();
    final VectorI2D v = new VectorI2D(1.0, 2.0);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4D.makeTranslation2D(v, t);
      MatrixM4x4D.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testTranslationMakeEquivalent3Real()
  {
    final T m = this.newMatrix();
    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4D.makeTranslation3D(v, t);
      MatrixM4x4D.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, r.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(2.0, r.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(3.0, r.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, r.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, r.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, r.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setRowColumnD(0, 0, 0.0);
    m.setRowColumnD(0, 1, 1.0);
    m.setRowColumnD(0, 2, 2.0);
    m.setRowColumnD(0, 3, 3.0);
    m.setRowColumnD(1, 0, 4.0);
    m.setRowColumnD(1, 1, 5.0);
    m.setRowColumnD(1, 2, 6.0);
    m.setRowColumnD(1, 3, 7.0);
    m.setRowColumnD(2, 0, 8.0);
    m.setRowColumnD(2, 1, 9.0);
    m.setRowColumnD(2, 2, 10.0);
    m.setRowColumnD(2, 3, 11.0);
    m.setRowColumnD(3, 0, 12.0);
    m.setRowColumnD(3, 1, 13.0);
    m.setRowColumnD(3, 2, 14.0);
    m.setRowColumnD(3, 3, 15.0);

    final T k = MatrixM4x4D.transpose(m, r);
    Assert.assertSame(k, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, m.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(1.0, m.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(2.0, m.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(3.0, m.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(4.0, m.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, m.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(6.0, m.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(7.0, m.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(8.0, m.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(9.0, m.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, m.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(11.0, m.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(12.0, m.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(13.0, m.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(14.0, m.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(15.0, m.getRowColumnD(3, 3), 0.0);

    Assert.assertEquals(0.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(8.0, r.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(12.0, r.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(1.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(9.0, r.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(13.0, r.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(2.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(6.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, r.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(14.0, r.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(3.0, r.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(7.0, r.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(11.0, r.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(15.0, r.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setRowColumnD(0, 0, 0.0);
    m.setRowColumnD(0, 1, 1.0);
    m.setRowColumnD(0, 2, 2.0);
    m.setRowColumnD(0, 3, 3.0);
    m.setRowColumnD(1, 0, 4.0);
    m.setRowColumnD(1, 1, 5.0);
    m.setRowColumnD(1, 2, 6.0);
    m.setRowColumnD(1, 3, 7.0);
    m.setRowColumnD(2, 0, 8.0);
    m.setRowColumnD(2, 1, 9.0);
    m.setRowColumnD(2, 2, 10.0);
    m.setRowColumnD(2, 3, 11.0);
    m.setRowColumnD(3, 0, 12.0);
    m.setRowColumnD(3, 1, 13.0);
    m.setRowColumnD(3, 2, 14.0);
    m.setRowColumnD(3, 3, 15.0);

    final T r = MatrixM4x4D.transposeInPlace(m);
    Assert.assertSame(m, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, r.getRowColumnD(0, 0), 0.0);
    Assert.assertEquals(4.0, r.getRowColumnD(0, 1), 0.0);
    Assert.assertEquals(8.0, r.getRowColumnD(0, 2), 0.0);
    Assert.assertEquals(12.0, r.getRowColumnD(0, 3), 0.0);
    Assert.assertEquals(1.0, r.getRowColumnD(1, 0), 0.0);
    Assert.assertEquals(5.0, r.getRowColumnD(1, 1), 0.0);
    Assert.assertEquals(9.0, r.getRowColumnD(1, 2), 0.0);
    Assert.assertEquals(13.0, r.getRowColumnD(1, 3), 0.0);
    Assert.assertEquals(2.0, r.getRowColumnD(2, 0), 0.0);
    Assert.assertEquals(6.0, r.getRowColumnD(2, 1), 0.0);
    Assert.assertEquals(10.0, r.getRowColumnD(2, 2), 0.0);
    Assert.assertEquals(14.0, r.getRowColumnD(2, 3), 0.0);
    Assert.assertEquals(3.0, r.getRowColumnD(3, 0), 0.0);
    Assert.assertEquals(7.0, r.getRowColumnD(3, 1), 0.0);
    Assert.assertEquals(11.0, r.getRowColumnD(3, 2), 0.0);
    Assert.assertEquals(15.0, r.getRowColumnD(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testZero()
  {
    final T m = this.newMatrix();
    MatrixM4x4D.setZero(m);
    this.checkDirectBufferInvariants(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(0.0, m.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowA()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, 4, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowB()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, 0, 4, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledOverflowC()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, 0, 0, 4, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowA()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowB()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testAddRowScaledUnderflowC()
  {
    final MatrixM4x4D.ContextMM4D c = new MatrixM4x4D.ContextMM4D();
    final T m = this.newMatrix();
    MatrixM4x4D.addRowScaledInPlace(c, m, 0, 0, -1, 1.0);
  }

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith3D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith3D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith3D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet3Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith3D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith3D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith3D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith2D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith2D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith2D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith2D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith2D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith2D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
