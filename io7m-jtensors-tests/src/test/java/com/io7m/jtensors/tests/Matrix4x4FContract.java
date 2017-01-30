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
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorReadable4FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix4x4FContract<T extends Matrix4x4FType>
  extends MatrixReadable4x4FContract<T>
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(
    1.0F, 0.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(
    0.0F, 1.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(
    0.0F, 0.0F, 1.0F);

  @Override
  protected abstract T newMatrix();

  @Override
  protected abstract T newMatrixFrom(MatrixReadable4x4FType m);

  private void isRotationMatrixX(
    final AlmostEqualFloat.ContextRelative context,
    final T r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, -0.707106781187f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
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
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context, -0.707106781187f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixZ(
    final AlmostEqualFloat.ContextRelative context,
    final T r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, -0.707106781187f, r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(
      context, 0.707106781187f, r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testTranslationMakeEquivalent2Integer()
  {
    final T m = this.newMatrix();
    final VectorReadable2IType v = new VectorI2I(1, 2);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4F.makeTranslation2I(v, t);
      MatrixM4x4F.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(2.0, (double) r.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith3F(1, new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith3F(2, new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith3F(3, new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow3F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet3Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith3F(1, new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith3F(2, new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith3F(3, new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow3FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith2F(1, new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith2F(2, new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith2F(3, new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow2F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith2F(1, new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith2F(2, new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith2F(3, new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow2FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTranslationMakeEquivalent2Real()
  {
    final T m = this.newMatrix();
    final VectorReadable2FType v = new VectorI2F(1.0f, 2.0f);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4F.makeTranslation2F(v, t);
      MatrixM4x4F.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);

      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(2.0, (double) r.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
    }
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
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM4x4F.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(1.0, (double) m0.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(3.0, (double) m1.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(4.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Override
  protected abstract void checkDirectBufferInvariants(final T m0);

  @Test public final void testAddMutate()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.setRowColumnF(row, column, 1.0f);
        m1.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final T mr = MatrixM4x4F.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(3.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(5.0f);
    m0.setR1C3F(5.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.addRowScaled(s, m0, 0, 1, 2, (double) (int) 2.0f, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.addRowScaledInPlace(s, m0, 0, 1, 2, (double) (int) 2.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(3.0, (double) m0.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(5.0, (double) m0.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(13.0, (double) m0.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(0.0, (double) m0.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(0.0, (double) m0.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(0.0, (double) m0.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(1.0, (double) m0.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testAddRowScaledContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(3.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(3.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(5.0f);
    m0.setR1C3F(5.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.addRowScaled(context, m0, 0, 1, 2, 2.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(13.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowA()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, 4, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowB()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, 0, 4, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledOverflowC()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, 0, 0, 4, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowA()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, -1, 0, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowB()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, 0, -1, 0, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddRowScaledUnderflowC()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.addRowScaledInPlace(s, m, 0, 0, -1, 1.0);
  }

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);

    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.copy(m0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(5.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(6.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(7.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(8.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(9.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(10.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(11.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(12.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(14.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(15.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(16.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testDeterminantIdentity()
  {
    final T m = this.newMatrix();
    Assert.assertEquals(1.0, MatrixM4x4F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantOther()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);
    m.setR1C1F(2.0f);
    m.setR2C2F(2.0f);
    m.setR3C3F(2.0f);

    Assert.assertEquals(16.0, MatrixM4x4F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScale()
  {
    final T m = this.newMatrix();

    m.setR0C0F(2.0f);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(2.0, MatrixM4x4F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantScaleNegative()
  {
    final T m = this.newMatrix();

    m.setR0C0F(-2.0f);

    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(-2.0, MatrixM4x4F.determinant(m), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testDeterminantZero()
  {
    final T m = this.newMatrix();
    this.checkDirectBufferInvariants(m);
    MatrixM4x4F.setZero(m);
    this.checkDirectBufferInvariants(m);
    Assert.assertEquals(0.0, MatrixM4x4F.determinant(m), 0.0);
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
        m1.setRowColumnF(row, col, 256.0F);
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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);

    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.exchangeRows(s, m0, 0, 3, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(14.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(15.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(16.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.exchangeRowsInPlace(s, m1, 0, 3);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(14.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(15.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(16.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsAOverflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.exchangeRowsInPlace(s, m, 4, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsAUnderflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.exchangeRowsInPlace(s, m, -1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsBOverflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.exchangeRowsInPlace(s, m, 0, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testExchangeRowsBUnderflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.exchangeRowsInPlace(s, m, 0, -1);
  }

  @Test public final void testExchangeRowsContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);

    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.exchangeRows(context, m0, 0, 3, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(14.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(15.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(16.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.exchangeRowsInPlace(context, m1, 0, 3);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(14.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(15.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(16.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(3.0f);
    m0.setR0C1F(5.0f);
    m0.setR0C2F(7.0f);
    m0.setR0C3F(11.0f);

    m0.setR1C0F(13.0f);
    m0.setR1C1F(17.0f);
    m0.setR1C2F(19.0f);
    m0.setR1C3F(23.0f);

    m0.setR2C0F(29.0f);
    m0.setR2C1F(31.0f);
    m0.setR2C2F(37.0f);
    m0.setR2C3F(41.0f);

    m0.setR3C0F(43.0f);
    m0.setR3C1F(47.0f);
    m0.setR3C2F(53.0f);
    m0.setR3C3F(59.0f);

    this.checkDirectBufferInvariants(m0);

    final T m1 = this.newMatrixFrom(m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(3.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(5.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(7.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(11.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(13.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(17.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(19.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(23.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(29.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(31.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(37.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(41.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(43.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(47.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(53.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(59.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testInitializationIdentity()
  {
    final T m = this.newMatrix();

    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(0.0, (double) m.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testInvertIdentity()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM4x4F.invert(s, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(s, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertIdentityContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    {
      final boolean r = MatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(1.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(2.0f);
    m0.setR0C1F(0.0f);
    m0.setR0C2F(0.0f);
    m0.setR0C3F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);
    m0.setR1C2F(0.0f);
    m0.setR1C3F(0.0f);

    m0.setR2C0F(0.0f);
    m0.setR2C1F(0.0f);
    m0.setR2C2F(2.0f);
    m0.setR2C3F(0.0f);

    m0.setR3C0F(0.0f);
    m0.setR3C1F(0.0f);
    m0.setR3C2F(0.0f);
    m0.setR3C3F(2.0f);

    {
      final boolean r = MatrixM4x4F.invert(s, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(s, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple2()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    boolean eq = false;

    m0.setR0C0F(1.0f);
    m0.setR0C1F(0.0f);
    m0.setR0C2F(5.0f);
    m0.setR0C3F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);
    m0.setR1C2F(0.0f);
    m0.setR1C3F(11.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(0.0f);
    m0.setR2C2F(3.0f);
    m0.setR2C3F(0.0f);

    m0.setR3C0F(0.0f);
    m0.setR3C1F(13.0f);
    m0.setR3C2F(0.0f);
    m0.setR3C3F(4.0f);

    {
      final boolean r = MatrixM4x4F.invert(s, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      
      
      
      
      
      

      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(0, 0), -0.09375f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(0, 2), 0.15625f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(1, 1), -0.0296f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(1, 3), 0.0814f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(2, 0), 0.21875f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(2, 2), -0.03125f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(3, 1), 0.096f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(3, 3), -0.01481f);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(s, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      
      
      
      
      
      

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 3), 4.0f);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    boolean eq = false;

    m0.setR0C0F(1.0f);
    m0.setR0C1F(0.0f);
    m0.setR0C2F(5.0f);
    m0.setR0C3F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);
    m0.setR1C2F(0.0f);
    m0.setR1C3F(11.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(0.0f);
    m0.setR2C2F(3.0f);
    m0.setR2C3F(0.0f);

    m0.setR3C0F(0.0f);
    m0.setR3C1F(13.0f);
    m0.setR3C2F(0.0f);
    m0.setR3C3F(4.0f);

    {
      final boolean r = MatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      
      
      
      
      
      

      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(0, 0), -0.09375f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(0, 2), 0.15625f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(1, 1), -0.0296f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(1, 3), 0.0814f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(2, 0), 0.21875f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(2, 2), -0.03125f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(3, 1), 0.096f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(
        context_f, rm.getRowColumnF(3, 3), -0.01481f);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      
      
      
      
      
      

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualFloat.almostEqual(context_f, rm.getRowColumnF(3, 3), 4.0f);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertSimpleContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    m0.setR0C0F(2.0f);
    m0.setR0C1F(0.0f);
    m0.setR0C2F(0.0f);
    m0.setR0C3F(0.0f);

    m0.setR1C0F(0.0f);
    m0.setR1C1F(2.0f);
    m0.setR1C2F(0.0f);
    m0.setR1C3F(0.0f);

    m0.setR2C0F(0.0f);
    m0.setR2C1F(0.0f);
    m0.setR2C2F(2.0f);
    m0.setR2C3F(0.0f);

    m0.setR3C0F(0.0f);
    m0.setR3C1F(0.0f);
    m0.setR3C2F(0.0f);
    m0.setR3C3F(2.0f);

    {
      final boolean r = MatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(0.5, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(0.5, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(context, m1);
      Assert.assertTrue(r);
      final T rm = m1;

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);

      Assert.assertEquals(2.0, (double) rm.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) rm.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(2.0, (double) rm.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(rm);
    }
  }

  @Test public final void testInvertZero()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM4x4F.setZero(m0);

    {
      final boolean r = MatrixM4x4F.invert(s, m0, m1);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(s, m0);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }
  }

  @Test public final void testInvertZeroContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    MatrixM4x4F.setZero(m0);

    {
      final boolean r = MatrixM4x4F.invert(context, m0, m1);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }

    {
      final boolean r = MatrixM4x4F.invertInPlace(context, m0);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
    }
  }

  @Test public final void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(-1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = new VectorI3F(0.0F, 1.0F, 0.0F);
    MatrixM4x4F.lookAt(mc, origin, target, axis, m);

    
    

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(0.0F, 0.0F, -1.0F);
    final VectorReadable3FType axis = new VectorI3F(0.0F, 1.0F, 0.0F);
    MatrixM4x4F.lookAt(mc, origin, target, axis, m);

    
    

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = new VectorI3F(0.0F, 1.0F, 0.0F);
    MatrixM4x4F.lookAt(mc, origin, target, axis, m);

    
    

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(0.0F, 0.0F, 1.0F);
    final VectorReadable3FType axis = new VectorI3F(0.0F, 1.0F, 0.0F);
    MatrixM4x4F.lookAt(mc, origin, target, axis, m);

    
    

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final VectorReadable3FType origin = new VectorI3F(
      (float) (20 + 0), (float) (30 + 0), (float) (40 + 0));
    final VectorReadable3FType target = new VectorI3F(
      (float) (20 + 0), (float) (30 + 0), (float) (40 + -1));
    final VectorReadable3FType axis = new VectorI3F(0.0F, 1.0F, 0.0F);
    MatrixM4x4F.lookAt(mc, origin, target, axis, m);

    
    

    boolean eq = false;

    /**
     * Rotation components
     */

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

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, -20.0f, m.getRowColumnF(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -30.0f, m.getRowColumnF(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -40.0f, m.getRowColumnF(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.getRowColumnF(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.getRowColumnF(3, 3));
    Assert.assertTrue(eq);
  }

  @Test public final void testMultiplyIdentity()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();
    final T r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
        Assert.assertTrue(
          m1.getRowColumnF(row, column) == mr.getRowColumnF(
            row, column));
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
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }

    final T r = MatrixM4x4F.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(
          m0.getRowColumnF(row, column) == m1.getRowColumnF(
            row, column));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testMultiplyMutateSimple()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);
    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);
    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);
    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    final T m1 = this.newMatrixFrom(m0);
    final T r = MatrixM4x4F.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(90.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(100.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(110.0, (double) r.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(120.0, (double) r.getRowColumnF(0, 3), 0.0);
    Assert.assertEquals(202.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(228.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(254.0, (double) r.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(280.0, (double) r.getRowColumnF(1, 3), 0.0);
    Assert.assertEquals(314.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(356.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(398.0, (double) r.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(440.0, (double) r.getRowColumnF(2, 3), 0.0);
    Assert.assertEquals(426.0, (double) r.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(484.0, (double) r.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(542.0, (double) r.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(600.0, (double) r.getRowColumnF(3, 3), 0.0);

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
    m0.setR0C3F(4.0f);
    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);
    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);
    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    final T m1 = this.newMatrixFrom(m0);
    final T mr = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    Assert.assertEquals(90.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(100.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(110.0, (double) r.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(120.0, (double) r.getRowColumnF(0, 3), 0.0);
    Assert.assertEquals(202.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(228.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(254.0, (double) r.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(280.0, (double) r.getRowColumnF(1, 3), 0.0);
    Assert.assertEquals(314.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(356.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(398.0, (double) r.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(440.0, (double) r.getRowColumnF(2, 3), 0.0);
    Assert.assertEquals(426.0, (double) r.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(484.0, (double) r.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(542.0, (double) r.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(600.0, (double) r.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);
    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);
    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);
    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    final VectorReadable4FType v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();

    this.checkDirectBufferInvariants(m0);

    final VectorM4F r = MatrixM4x4F.multiplyVector4F(s, m0, v, out);
    Assert.assertSame(out, r);

    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(30.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(70.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(110.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(150.0, (double) out.getWF(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public void testMultiplyVectorSimpleContextEquivalent()
  {
    final T m0 = this.newMatrix();

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);
    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);
    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);
    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);

    final VectorReadable4FType v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();

    this.checkDirectBufferInvariants(m0);

    final VectorM4F r = MatrixM4x4F.multiplyVector4F(context, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(30.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(70.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(110.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(150.0, (double) out.getWF(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testMultiplyZero()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();
    final T mr = this.newMatrix();

    MatrixM4x4F.setZero(m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final T r = MatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(0.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeNegativeColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeNegativeRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeOverflowColumn()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(0, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testOutOfRangeOverflowRow()
  {
    final T m = this.newMatrix();
    m.getRowColumnF(4, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Test public final void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();

    final T m = this.newMatrix();
    final T mt = this.newMatrix();
    final T mi = this.newMatrix();
    final VectorM3F axis = new VectorM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      float angle = (float) (Math.random() * (2.0 * Math.PI));
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

      
      

      MatrixM4x4F.makeRotation((double) angle, axis, m);

      final double det = MatrixM4x4F.determinant(m);
      

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM4x4F.invert(s, m, mi);
      MatrixM4x4F.transpose(m, mt);

      for (int row = 0; row < 4; ++row) {
        for (int col = 0; col < 4; ++col) {
          final float mx = mi.getRowColumnF(row, col);
          final float my = mt.getRowColumnF(row, col);
          final boolean eq = AlmostEqualFloat.almostEqual(context_f, mx, my);

          
          
          

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_exp = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);

    MatrixM4x4F.makeRotation(0.0, AXIS_X, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Test public final void testRotateVector0Y()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_exp = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);

    MatrixM4x4F.makeRotation(0.0, AXIS_Y, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Test public final void testRotateVector0Z()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T m = this.newMatrix();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_exp = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);

    MatrixM4x4F.makeRotation(0.0, AXIS_Z, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Test public final void testRotateVector90X()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 1.0F, 0.0F, 1.0F);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp =
      new VectorM4F(0.0F, 6.1232339957367E-17f, 1.0F, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(90.0), AXIS_X, m);
    
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp =
      new VectorM4F(-1.0F, 0.0F, -6.1232339957367E-17f, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(90.0), AXIS_Y, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 1.0F, 0.0F, 1.0F);
    final VectorReadable3FType v_exp =
      new VectorM4F(-1.0F, 6.123233995736766E-17f, 0.0F, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(90.0), AXIS_Z, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 1.0F, 0.0F, 1.0F);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp =
      new VectorM4F(0.0F, 6.1232339957367E-17f, -1.0F, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(-90.0), AXIS_X, m);
    
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 0.0F, -1.0F, 1.0F);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorReadable3FType v_exp =
      new VectorM4F(1.0F, 0.0F, -6.1232339957367E-17f, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(-90.0), AXIS_Y, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

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
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final T m = this.newMatrix();
    final VectorM4F v_got = new VectorM4F();
    final VectorReadable4FType v_in = new VectorM4F(0.0F, 1.0F, 0.0F, 1.0F);
    final VectorReadable3FType v_exp =
      new VectorM4F(1.0F, 6.123233995736766E-17f, 0.0F, 1.0F);

    MatrixM4x4F.makeRotation(
      Math.toRadians(-90.0), AXIS_Z, m);
    MatrixM4x4F.multiplyVector4F(s, m, v_in, v_got);

    
    
    
    

    eq = AlmostEqualFloat.almostEqual(context, v_exp.getXF(), v_got.getXF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getYF(), v_got.getYF());
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.getZF(), v_got.getZF());
    Assert.assertTrue(eq);
  }

  @Test public final void testRotateYMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM4x4F.makeRotation(
        Math.toRadians(45.0), AXIS_Y, r);

      this.checkDirectBufferInvariants(r);

      

      this.isRotationMatrixY(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRotateZMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final T r = this.newMatrix();
      MatrixM4x4F.makeRotation(
        Math.toRadians(45.0), AXIS_Z, r);

      this.checkDirectBufferInvariants(r);

      

      this.isRotationMatrixZ(context, r);

      this.checkDirectBufferInvariants(r);
    }
  }

  @Test public final void testRow4()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F();
    this.checkDirectBufferInvariants(m);

    m.getRow4F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(1, v);
    Assert.assertEquals(0.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(2, v);
    Assert.assertEquals(0.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(3, v);
    Assert.assertEquals(0.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow4Overflow()
  {
    final T m = this.newMatrix();
    m.getRow4F(4, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow4Underflow()
  {
    final T m = this.newMatrix();
    m.getRow4F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow2Overflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow2Underflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow3Overflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRow3Underflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(-1, new VectorM4F());
  }

  @Test public final void testScale()
  {
    final T m0 = this.newMatrix();
    final T mr = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    final T mk = MatrixM4x4F.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
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

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m.setRowColumnF(row, column, 3.0f);
      }
    }

    this.checkDirectBufferInvariants(m);

    final T mr = MatrixM4x4F.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(15.0, (double) m.getRowColumnF(row, column), 0.0);
        Assert.assertEquals(15.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testScaleRow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);

    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.scaleRow(s, m0, 0, 2.0, m1);
    MatrixM4x4F.scaleRow(s, m0, 1, 4.0, m1);
    MatrixM4x4F.scaleRow(s, m0, 2, 8.0, m1);
    MatrixM4x4F.scaleRow(s, m0, 3, 16.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(8.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(20.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(32.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(72.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(96.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(208.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(224.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(240.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(256.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.scaleRowInPlace(s, m0, 0, 2.0);
    MatrixM4x4F.scaleRowInPlace(s, m0, 1, 4.0);
    MatrixM4x4F.scaleRowInPlace(s, m0, 2, 8.0);
    MatrixM4x4F.scaleRowInPlace(s, m0, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m0.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m0.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(8.0, (double) m0.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(20.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m0.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m0.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(32.0, (double) m0.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(72.0, (double) m0.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m0.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m0.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(96.0, (double) m0.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(208.0, (double) m0.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(224.0, (double) m0.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(240.0, (double) m0.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(256.0, (double) m0.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test public final void testScaleRowContextEquivalent()
  {
    final MatrixM4x4F.ContextMM4F context = new MatrixM4x4F.ContextMM4F();
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);
    m0.setR0C3F(4.0f);

    m0.setR1C0F(5.0f);
    m0.setR1C1F(6.0f);
    m0.setR1C2F(7.0f);
    m0.setR1C3F(8.0f);

    m0.setR2C0F(9.0f);
    m0.setR2C1F(10.0f);
    m0.setR2C2F(11.0f);
    m0.setR2C3F(12.0f);

    m0.setR3C0F(13.0f);
    m0.setR3C1F(14.0f);
    m0.setR3C2F(15.0f);
    m0.setR3C3F(16.0f);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.scaleRow(context, m0, 0, 2.0, m1);
    MatrixM4x4F.scaleRow(context, m0, 1, 4.0, m1);
    MatrixM4x4F.scaleRow(context, m0, 2, 8.0, m1);
    MatrixM4x4F.scaleRow(context, m0, 3, 16.0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m1.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m1.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(8.0, (double) m1.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(20.0, (double) m1.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m1.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m1.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(32.0, (double) m1.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(72.0, (double) m1.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m1.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m1.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(96.0, (double) m1.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(208.0, (double) m1.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(224.0, (double) m1.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(240.0, (double) m1.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(256.0, (double) m1.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    MatrixM4x4F.scaleRowInPlace(context, m0, 0, 2.0);
    MatrixM4x4F.scaleRowInPlace(context, m0, 1, 4.0);
    MatrixM4x4F.scaleRowInPlace(context, m0, 2, 8.0);
    MatrixM4x4F.scaleRowInPlace(context, m0, 3, 16.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(2.0, (double) m0.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) m0.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(6.0, (double) m0.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(8.0, (double) m0.getRowColumnF(0, 3), 0.0);

    Assert.assertEquals(20.0, (double) m0.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(24.0, (double) m0.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(28.0, (double) m0.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(32.0, (double) m0.getRowColumnF(1, 3), 0.0);

    Assert.assertEquals(72.0, (double) m0.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(80.0, (double) m0.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(88.0, (double) m0.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(96.0, (double) m0.getRowColumnF(2, 3), 0.0);

    Assert.assertEquals(208.0, (double) m0.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(224.0, (double) m0.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(240.0, (double) m0.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(256.0, (double) m0.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowMutateOverflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.scaleRowInPlace(s, m, 4, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowMutateUnderflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    MatrixM4x4F.scaleRowInPlace(s, m, -1, 1.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowOverflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM4x4F.scaleRow(s, m, 4, 1.0, r);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testScaleRowUnderflow()
  {
    final MatrixM4x4F.ContextMM4F s = new MatrixM4x4F.ContextMM4F();
    final T m = this.newMatrix();
    final T r = this.newMatrix();
    MatrixM4x4F.scaleRow(s, m, -1, 1.0, r);
  }

  @Test public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariants(m);

    {
      MatrixM4x4F.setZero(m);
      m.setR0C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 0), 0.0);
      this.checkZeroExcept(m, 0, 0);

      MatrixM4x4F.setZero(m);
      m.setR1C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
      this.checkZeroExcept(m, 1, 0);

      MatrixM4x4F.setZero(m);
      m.setR2C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 0), 0.0);
      this.checkZeroExcept(m, 2, 0);

      MatrixM4x4F.setZero(m);
      m.setR3C0F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR3C0F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(3, 0), 0.0);
      this.checkZeroExcept(m, 3, 0);
    }

    {
      MatrixM4x4F.setZero(m);
      m.setR0C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 1), 0.0);
      this.checkZeroExcept(m, 0, 1);

      MatrixM4x4F.setZero(m);
      m.setR1C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 1), 0.0);
      this.checkZeroExcept(m, 1, 1);

      MatrixM4x4F.setZero(m);
      m.setR2C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 1), 0.0);
      this.checkZeroExcept(m, 2, 1);

      MatrixM4x4F.setZero(m);
      m.setR3C1F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR3C1F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(3, 1), 0.0);
      this.checkZeroExcept(m, 3, 1);
    }

    {
      MatrixM4x4F.setZero(m);
      m.setR0C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 2), 0.0);
      this.checkZeroExcept(m, 0, 2);

      MatrixM4x4F.setZero(m);
      m.setR1C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 2), 0.0);
      this.checkZeroExcept(m, 1, 2);

      MatrixM4x4F.setZero(m);
      m.setR2C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 2), 0.0);
      this.checkZeroExcept(m, 2, 2);

      MatrixM4x4F.setZero(m);
      m.setR3C2F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR3C2F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(3, 2), 0.0);
      this.checkZeroExcept(m, 3, 2);
    }

    {
      MatrixM4x4F.setZero(m);
      m.setR0C3F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR0C3F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(0, 3), 0.0);
      this.checkZeroExcept(m, 0, 3);

      MatrixM4x4F.setZero(m);
      m.setR1C3F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR1C3F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 3), 0.0);
      this.checkZeroExcept(m, 1, 3);

      MatrixM4x4F.setZero(m);
      m.setR2C3F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR2C3F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(2, 3), 0.0);
      this.checkZeroExcept(m, 2, 3);

      MatrixM4x4F.setZero(m);
      m.setR3C3F(13.0f);
      Assert.assertEquals(13.0, (double) m.getR3C3F(), 0.0);
      Assert.assertEquals(13.0, (double) m.getRowColumnF(3, 3), 0.0);
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

        final double x = (double) m.getRowColumnF(r, c);
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

    m.setRowColumnF(0, 0, 3.0f);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 0), 0.0);
    m.setRowColumnF(0, 1, 5.0f);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(0, 1), 0.0);
    m.setRowColumnF(0, 2, 7.0f);
    Assert.assertEquals(7.0, (double) m.getRowColumnF(0, 2), 0.0);
    m.setRowColumnF(0, 3, 11.0f);
    Assert.assertEquals(11.0, (double) m.getRowColumnF(0, 3), 0.0);

    m.setRowColumnF(1, 0, 13.0f);
    Assert.assertEquals(13.0, (double) m.getRowColumnF(1, 0), 0.0);
    m.setRowColumnF(1, 1, 17.0f);
    Assert.assertEquals(17.0, (double) m.getRowColumnF(1, 1), 0.0);
    m.setRowColumnF(1, 2, 19.0f);
    Assert.assertEquals(19.0, (double) m.getRowColumnF(1, 2), 0.0);
    m.setRowColumnF(1, 3, 23.0f);
    Assert.assertEquals(23.0, (double) m.getRowColumnF(1, 3), 0.0);

    m.setRowColumnF(2, 0, 29.0f);
    Assert.assertEquals(29.0, (double) m.getRowColumnF(2, 0), 0.0);
    m.setRowColumnF(2, 1, 31.0f);
    Assert.assertEquals(31.0, (double) m.getRowColumnF(2, 1), 0.0);
    m.setRowColumnF(2, 2, 37.0f);
    Assert.assertEquals(37.0, (double) m.getRowColumnF(2, 2), 0.0);
    m.setRowColumnF(2, 3, 41.0f);
    Assert.assertEquals(41.0, (double) m.getRowColumnF(2, 3), 0.0);

    m.setRowColumnF(3, 0, 43.0f);
    Assert.assertEquals(43.0, (double) m.getRowColumnF(3, 0), 0.0);
    m.setRowColumnF(3, 1, 47.0f);
    Assert.assertEquals(47.0, (double) m.getRowColumnF(3, 1), 0.0);
    m.setRowColumnF(3, 2, 53.0f);
    Assert.assertEquals(53.0, (double) m.getRowColumnF(3, 2), 0.0);
    m.setRowColumnF(3, 3, 59.0f);
    Assert.assertEquals(59.0, (double) m.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testSetIdentity()
  {
    final T m = this.newMatrix();

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m.setRowColumnF(row, col, (float) Math.random());
      }
    }

    this.checkDirectBufferInvariants(m);
    MatrixM4x4F.setIdentity(m);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testTrace()
  {
    final T m = this.newMatrix();
    final double t = MatrixM4x4F.trace(m);
    Assert.assertEquals(t, 4.0, 0.0);
  }

  @Test public final void testTranslate3_4_Equivalent()
  {
    final MatrixM3x3F.ContextMM3F s3 = new MatrixM3x3F.ContextMM3F();
    final MatrixM4x4F.ContextMM4F s4 = new MatrixM4x4F.ContextMM4F();
    final Matrix3x3FType m3 = MatrixHeapArrayM3x3F.newMatrix();
    final T m4 = this.newMatrix();
    final VectorReadable3FType v = new VectorI3F(3.0f, 7.0f, 0.0f);
    final VectorReadable3FType v3i = new VectorM3F(1.0F, 1.0F, 1.0F);
    final VectorM3F v3o = new VectorM3F();
    final VectorReadable4FType w3i = new VectorM4F(1.0F, 1.0F, 1.0F, 1.0F);
    final VectorM4F w3o = new VectorM4F();

    MatrixM3x3F.makeTranslation2F(v, m3);
    MatrixM4x4F.makeTranslation3F(v, m4);

    MatrixM3x3F.multiplyVector3F(s3, m3, v3i, v3o);
    MatrixM4x4F.multiplyVector4F(s4, m4, w3i, w3o);

    Assert.assertEquals((double) w3o.getXF(), (double) v3o.getXF(), 0.0);
    Assert.assertEquals((double) w3o.getYF(), (double) v3o.getYF(), 0.0);
  }

  @Test public final void testTranslationMakeEquivalent3Integer()
  {
    final T m = this.newMatrix();
    final VectorReadable3IType v = new VectorI3I(1, 2, 3);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4F.makeTranslation3I(v, t);
      MatrixM4x4F.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
      this.checkDirectBufferInvariants(t);

      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(2.0, (double) r.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(3.0, (double) r.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
      this.checkDirectBufferInvariants(t);
    }
  }

  @Test public final void testTranslationMakeEquivalent3Real()
  {
    final T m = this.newMatrix();
    final VectorReadable3FType v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final T r = this.newMatrix();
      final T t = this.newMatrix();
      MatrixM4x4F.makeTranslation3F(v, t);
      MatrixM4x4F.multiply(m, t, r);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
      this.checkDirectBufferInvariants(t);

      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(2.0, (double) r.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(3.0, (double) r.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) r.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) r.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m);
      this.checkDirectBufferInvariants(r);
      this.checkDirectBufferInvariants(t);
    }
  }

  @Test public final void testTranspose()
  {
    final T m = this.newMatrix();
    final T r = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);
    m.setR0C2F(2.0f);
    m.setR0C3F(3.0f);
    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);
    m.setR1C2F(6.0f);
    m.setR1C3F(7.0f);
    m.setR2C0F(8.0f);
    m.setR2C1F(9.0f);
    m.setR2C2F(10.0f);
    m.setR2C3F(11.0f);
    m.setR3C0F(12.0f);
    m.setR3C1F(13.0f);
    m.setR3C2F(14.0f);
    m.setR3C3F(15.0f);

    final T k = MatrixM4x4F.transpose(m, r);
    Assert.assertSame(k, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) m.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(1.0, (double) m.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(2.0, (double) m.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(3.0, (double) m.getRowColumnF(0, 3), 0.0);
    Assert.assertEquals(4.0, (double) m.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) m.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(6.0, (double) m.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(7.0, (double) m.getRowColumnF(1, 3), 0.0);
    Assert.assertEquals(8.0, (double) m.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(9.0, (double) m.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) m.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(11.0, (double) m.getRowColumnF(2, 3), 0.0);
    Assert.assertEquals(12.0, (double) m.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(13.0, (double) m.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(14.0, (double) m.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(15.0, (double) m.getRowColumnF(3, 3), 0.0);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(8.0, (double) r.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(12.0, (double) r.getRowColumnF(0, 3), 0.0);
    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(9.0, (double) r.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(13.0, (double) r.getRowColumnF(1, 3), 0.0);
    Assert.assertEquals(2.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(6.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(14.0, (double) r.getRowColumnF(2, 3), 0.0);
    Assert.assertEquals(3.0, (double) r.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(7.0, (double) r.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(11.0, (double) r.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(15.0, (double) r.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testTransposeMutate()
  {
    final T m = this.newMatrix();

    m.setR0C0F(0.0f);
    m.setR0C1F(1.0f);
    m.setR0C2F(2.0f);
    m.setR0C3F(3.0f);
    m.setR1C0F(4.0f);
    m.setR1C1F(5.0f);
    m.setR1C2F(6.0f);
    m.setR1C3F(7.0f);
    m.setR2C0F(8.0f);
    m.setR2C1F(9.0f);
    m.setR2C2F(10.0f);
    m.setR2C3F(11.0f);
    m.setR3C0F(12.0f);
    m.setR3C1F(13.0f);
    m.setR3C2F(14.0f);
    m.setR3C3F(15.0f);

    final T r = MatrixM4x4F.transposeInPlace(m);
    Assert.assertSame(m, r);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);

    Assert.assertEquals(0.0, (double) r.getRowColumnF(0, 0), 0.0);
    Assert.assertEquals(4.0, (double) r.getRowColumnF(0, 1), 0.0);
    Assert.assertEquals(8.0, (double) r.getRowColumnF(0, 2), 0.0);
    Assert.assertEquals(12.0, (double) r.getRowColumnF(0, 3), 0.0);
    Assert.assertEquals(1.0, (double) r.getRowColumnF(1, 0), 0.0);
    Assert.assertEquals(5.0, (double) r.getRowColumnF(1, 1), 0.0);
    Assert.assertEquals(9.0, (double) r.getRowColumnF(1, 2), 0.0);
    Assert.assertEquals(13.0, (double) r.getRowColumnF(1, 3), 0.0);
    Assert.assertEquals(2.0, (double) r.getRowColumnF(2, 0), 0.0);
    Assert.assertEquals(6.0, (double) r.getRowColumnF(2, 1), 0.0);
    Assert.assertEquals(10.0, (double) r.getRowColumnF(2, 2), 0.0);
    Assert.assertEquals(14.0, (double) r.getRowColumnF(2, 3), 0.0);
    Assert.assertEquals(3.0, (double) r.getRowColumnF(3, 0), 0.0);
    Assert.assertEquals(7.0, (double) r.getRowColumnF(3, 1), 0.0);
    Assert.assertEquals(11.0, (double) r.getRowColumnF(3, 2), 0.0);
    Assert.assertEquals(15.0, (double) r.getRowColumnF(3, 3), 0.0);

    this.checkDirectBufferInvariants(m);
    this.checkDirectBufferInvariants(r);
  }

  @Test public final void testZero()
  {
    final T m = this.newMatrix();
    MatrixM4x4F.setZero(m);

    this.checkDirectBufferInvariants(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(0.0, (double) m.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSetGet4()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F();
    this.checkDirectBufferInvariants(m);

    m.setRowWith4F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith4F(1, new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith4F(2, new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith4F(3, new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow4F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(4.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(40.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(400.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(4000.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSetGet4Static()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F();
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow4F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(4.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(40.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(400.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow4F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(4000.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get3()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow3F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow3FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(30.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(300.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow3FUnsafe(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(3000.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get2()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow2F(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test public final void testRowSet4Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(new VectorI4F(10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(new VectorI4F(100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(new VectorI4F(1000.0f, 2000.0f, 3000.0f, 4000.0f));

    m.getRow2FUnsafe(0, v);
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

}
