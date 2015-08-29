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

package com.io7m.jtensors;

import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;
import com.io7m.jtensors.MatrixM4x4D.Context;

public class MatrixM4x4DTest extends MatrixM4x4Contract
{
  private static final VectorReadable3D AXIS_X = new VectorI3D(1, 0, 0);
  private static final VectorReadable3D AXIS_Y = new VectorI3D(0, 1, 0);
  private static final VectorReadable3D AXIS_Z = new VectorI3D(0, 0, 1);

  private static void isRotationMatrixX(
    final AlmostEqualDouble.ContextRelative context,
    final MatrixM4x4D r)
  {
    boolean eq;

    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, -0.707106781187, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixY(
    final AlmostEqualDouble.ContextRelative context,
    final MatrixM4x4D r)
  {
    boolean eq;

    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, -0.707106781187, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.707106781187, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  private static void isRotationMatrixZ(
    final AlmostEqualDouble.ContextRelative context_d,
    final MatrixM4x4D r)
  {
    boolean eq;

    eq =
      AlmostEqualDouble.almostEqual(context_d, 0.707106781187, r.get(0, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(context_d, -0.707106781187, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(0, 3));
    Assert.assertTrue(eq);

    eq =
      AlmostEqualDouble.almostEqual(context_d, 0.707106781187, r.get(1, 0));
    Assert.assertTrue(eq);
    eq =
      AlmostEqualDouble.almostEqual(context_d, 0.707106781187, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testAdd()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    final MatrixM4x4D mk = MatrixM4x4D.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
  }

  @Override @Test public void testAddMutate()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0);
        m1.set(row, column, 3.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    final MatrixM4x4D mr = MatrixM4x4D.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
  }

  @Override @Test public void testAddRowScaled()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);
    m0.set(1, 2, 5.0);
    m0.set(1, 3, 5.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.addRowScaled(m0, 0, 1, 2, 2.0, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);
    Assert.assertTrue(m1.get(0, 2) == 0.0);
    Assert.assertTrue(m1.get(0, 3) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 13.0);
    Assert.assertTrue(m1.get(2, 1) == 13.0);
    Assert.assertTrue(m1.get(2, 2) == 13.0);
    Assert.assertTrue(m1.get(2, 3) == 13.0);

    Assert.assertTrue(m1.get(3, 0) == 0.0);
    Assert.assertTrue(m1.get(3, 1) == 0.0);
    Assert.assertTrue(m1.get(3, 2) == 0.0);
    Assert.assertTrue(m1.get(3, 3) == 1.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.addRowScaledInPlace(m0, 0, 1, 2, 2.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m0.get(0, 0) == 3.0);
    Assert.assertTrue(m0.get(0, 1) == 3.0);
    Assert.assertTrue(m0.get(0, 2) == 3.0);
    Assert.assertTrue(m0.get(0, 3) == 3.0);

    Assert.assertTrue(m0.get(1, 0) == 5.0);
    Assert.assertTrue(m0.get(1, 1) == 5.0);
    Assert.assertTrue(m0.get(1, 2) == 5.0);
    Assert.assertTrue(m0.get(1, 3) == 5.0);

    Assert.assertTrue(m0.get(2, 0) == 13.0);
    Assert.assertTrue(m0.get(2, 1) == 13.0);
    Assert.assertTrue(m0.get(2, 2) == 13.0);
    Assert.assertTrue(m0.get(2, 3) == 13.0);

    Assert.assertTrue(m0.get(3, 0) == 0.0);
    Assert.assertTrue(m0.get(3, 1) == 0.0);
    Assert.assertTrue(m0.get(3, 2) == 0.0);
    Assert.assertTrue(m0.get(3, 3) == 1.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testAddRowScaledContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 3.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 3.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 5.0);
    m0.set(1, 2, 5.0);
    m0.set(1, 3, 5.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.addRowScaledWithContext(context, m0, 0, 1, 2, 2.0, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 0.0);
    Assert.assertTrue(m1.get(0, 2) == 0.0);
    Assert.assertTrue(m1.get(0, 3) == 0.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 13.0);
    Assert.assertTrue(m1.get(2, 1) == 13.0);
    Assert.assertTrue(m1.get(2, 2) == 13.0);
    Assert.assertTrue(m1.get(2, 3) == 13.0);

    Assert.assertTrue(m1.get(3, 0) == 0.0);
    Assert.assertTrue(m1.get(3, 1) == 0.0);
    Assert.assertTrue(m1.get(3, 2) == 0.0);
    Assert.assertTrue(m1.get(3, 3) == 1.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, 4, 0, 0, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, 0, 4, 0, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, 0, 0, 4, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, -1, 0, 0, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, 0, -1, 0, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.addRowScaledInPlace(m, 0, 0, -1, 1.0);
  }

  @Override @Test public void testBufferEndianness()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final DoubleBuffer b = MatrixM4x4D.doubleBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Override @Test public void testCopy()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 4.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);
    m0.set(1, 3, 8.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);
    m0.set(2, 3, 12.0);

    m0.set(3, 0, 13.0);
    m0.set(3, 1, 14.0);
    m0.set(3, 2, 15.0);
    m0.set(3, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.copy(m0, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);
    Assert.assertTrue(m1.get(0, 2) == 3.0);
    Assert.assertTrue(m1.get(0, 3) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 5.0);
    Assert.assertTrue(m1.get(1, 1) == 6.0);
    Assert.assertTrue(m1.get(1, 2) == 7.0);
    Assert.assertTrue(m1.get(1, 3) == 8.0);

    Assert.assertTrue(m1.get(2, 0) == 9.0);
    Assert.assertTrue(m1.get(2, 1) == 10.0);
    Assert.assertTrue(m1.get(2, 2) == 11.0);
    Assert.assertTrue(m1.get(2, 3) == 12.0);

    Assert.assertTrue(m1.get(3, 0) == 13.0);
    Assert.assertTrue(m1.get(3, 1) == 14.0);
    Assert.assertTrue(m1.get(3, 2) == 15.0);
    Assert.assertTrue(m1.get(3, 3) == 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testDeterminantIdentity()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    Assert.assertTrue(MatrixM4x4D.determinant(m) == 1.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testDeterminantOther()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);
    m.set(3, 3, 2.0f);

    Assert.assertTrue(MatrixM4x4D.determinant(m) == 16.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testDeterminantScale()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    m.set(0, 0, 2.0f);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertTrue(MatrixM4x4D.determinant(m) == 2.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testDeterminantScaleNegative()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    m.set(0, 0, -2.0f);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertTrue(MatrixM4x4D.determinant(m) == -2.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testDeterminantZero()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.setZero(m);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertTrue(MatrixM4x4D.determinant(m) == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final MatrixM4x4D m0 = new MatrixM4x4D();
      Assert.assertTrue(m0.equals(m0));
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    }

    {
      final MatrixM4x4D m0 = new MatrixM4x4D();
      Assert.assertFalse(m0.equals(null));
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    }

    {
      final MatrixM4x4D m0 = new MatrixM4x4D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    }

    {
      final MatrixM4x4D m0 = new MatrixM4x4D();
      final MatrixM4x4D m1 = new MatrixM4x4D();
      Assert.assertTrue(m0.equals(m1));
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    }
  }

  @Override @Test public void testEqualsNotEqualsCorrect()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4D m0 = new MatrixM4x4D();
        final MatrixM4x4D m1 = new MatrixM4x4D();
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      }
    }
  }

  @Override @Test public void testExchangeRows()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 4.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);
    m0.set(1, 3, 8.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);
    m0.set(2, 3, 12.0);

    m0.set(3, 0, 13.0);
    m0.set(3, 1, 14.0);
    m0.set(3, 2, 15.0);
    m0.set(3, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.exchangeRows(m0, 0, 3, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 13.0);
    Assert.assertTrue(m1.get(0, 1) == 14.0);
    Assert.assertTrue(m1.get(0, 2) == 15.0);
    Assert.assertTrue(m1.get(0, 3) == 16.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 0.0);
    Assert.assertTrue(m1.get(2, 1) == 0.0);
    Assert.assertTrue(m1.get(2, 2) == 1.0);
    Assert.assertTrue(m1.get(2, 3) == 0.0);

    Assert.assertTrue(m1.get(3, 0) == 1.0);
    Assert.assertTrue(m1.get(3, 1) == 2.0);
    Assert.assertTrue(m1.get(3, 2) == 3.0);
    Assert.assertTrue(m1.get(3, 3) == 4.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.exchangeRowsInPlace(m1, 0, 3);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);
    Assert.assertTrue(m1.get(0, 2) == 3.0);
    Assert.assertTrue(m1.get(0, 3) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 0.0);
    Assert.assertTrue(m1.get(2, 1) == 0.0);
    Assert.assertTrue(m1.get(2, 2) == 1.0);
    Assert.assertTrue(m1.get(2, 3) == 0.0);

    Assert.assertTrue(m1.get(3, 0) == 13.0);
    Assert.assertTrue(m1.get(3, 1) == 14.0);
    Assert.assertTrue(m1.get(3, 2) == 15.0);
    Assert.assertTrue(m1.get(3, 3) == 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.exchangeRowsInPlace(m, 4, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.exchangeRowsInPlace(m, -1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.exchangeRowsInPlace(m, 0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.exchangeRowsInPlace(m, 0, -1);
  }

  @Override @Test public void testExchangeRowsContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 4.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);
    m0.set(1, 3, 8.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);
    m0.set(2, 3, 12.0);

    m0.set(3, 0, 13.0);
    m0.set(3, 1, 14.0);
    m0.set(3, 2, 15.0);
    m0.set(3, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.exchangeRowsWithContext(context, m0, 0, 3, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 13.0);
    Assert.assertTrue(m1.get(0, 1) == 14.0);
    Assert.assertTrue(m1.get(0, 2) == 15.0);
    Assert.assertTrue(m1.get(0, 3) == 16.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 0.0);
    Assert.assertTrue(m1.get(2, 1) == 0.0);
    Assert.assertTrue(m1.get(2, 2) == 1.0);
    Assert.assertTrue(m1.get(2, 3) == 0.0);

    Assert.assertTrue(m1.get(3, 0) == 1.0);
    Assert.assertTrue(m1.get(3, 1) == 2.0);
    Assert.assertTrue(m1.get(3, 2) == 3.0);
    Assert.assertTrue(m1.get(3, 3) == 4.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.exchangeRowsInPlaceWithContext(context, m1, 0, 3);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 1.0);
    Assert.assertTrue(m1.get(0, 1) == 2.0);
    Assert.assertTrue(m1.get(0, 2) == 3.0);
    Assert.assertTrue(m1.get(0, 3) == 4.0);

    Assert.assertTrue(m1.get(1, 0) == 0.0);
    Assert.assertTrue(m1.get(1, 1) == 1.0);
    Assert.assertTrue(m1.get(1, 2) == 0.0);
    Assert.assertTrue(m1.get(1, 3) == 0.0);

    Assert.assertTrue(m1.get(2, 0) == 0.0);
    Assert.assertTrue(m1.get(2, 1) == 0.0);
    Assert.assertTrue(m1.get(2, 2) == 1.0);
    Assert.assertTrue(m1.get(2, 3) == 0.0);

    Assert.assertTrue(m1.get(3, 0) == 13.0);
    Assert.assertTrue(m1.get(3, 1) == 14.0);
    Assert.assertTrue(m1.get(3, 2) == 15.0);
    Assert.assertTrue(m1.get(3, 3) == 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4D m0 = new MatrixM4x4D();
        final MatrixM4x4D m1 = new MatrixM4x4D();
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      }
    }
  }

  @Override @Test public void testInitializationFrom()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();

    m0.set(0, 0, 3.0);
    m0.set(0, 1, 5.0);
    m0.set(0, 2, 7.0);
    m0.set(0, 3, 11.0);

    m0.set(1, 0, 13.0);
    m0.set(1, 1, 17.0);
    m0.set(1, 2, 19.0);
    m0.set(1, 3, 23.0);

    m0.set(2, 0, 29.0);
    m0.set(2, 1, 31.0);
    m0.set(2, 2, 37.0);
    m0.set(2, 3, 41.0);

    m0.set(3, 0, 43.0);
    m0.set(3, 1, 47.0);
    m0.set(3, 2, 53.0);
    m0.set(3, 3, 59.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());

    final MatrixM4x4D m1 = new MatrixM4x4D(m0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 3.0);
    Assert.assertTrue(m1.get(0, 1) == 5.0);
    Assert.assertTrue(m1.get(0, 2) == 7.0);
    Assert.assertTrue(m1.get(0, 3) == 11.0);

    Assert.assertTrue(m1.get(1, 0) == 13.0);
    Assert.assertTrue(m1.get(1, 1) == 17.0);
    Assert.assertTrue(m1.get(1, 2) == 19.0);
    Assert.assertTrue(m1.get(1, 3) == 23.0);

    Assert.assertTrue(m1.get(2, 0) == 29.0);
    Assert.assertTrue(m1.get(2, 1) == 31.0);
    Assert.assertTrue(m1.get(2, 2) == 37.0);
    Assert.assertTrue(m1.get(2, 3) == 41.0);

    Assert.assertTrue(m1.get(3, 0) == 43.0);
    Assert.assertTrue(m1.get(3, 1) == 47.0);
    Assert.assertTrue(m1.get(3, 2) == 53.0);
    Assert.assertTrue(m1.get(3, 3) == 59.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testInitializationIdentity()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    Assert.assertTrue(m.get(0, 0) == 1.0);
    Assert.assertTrue(m.get(0, 1) == 0.0);
    Assert.assertTrue(m.get(0, 2) == 0.0);
    Assert.assertTrue(m.get(0, 3) == 0.0);

    Assert.assertTrue(m.get(1, 0) == 0.0);
    Assert.assertTrue(m.get(1, 1) == 1.0);
    Assert.assertTrue(m.get(1, 2) == 0.0);
    Assert.assertTrue(m.get(1, 3) == 0.0);

    Assert.assertTrue(m.get(2, 0) == 0.0);
    Assert.assertTrue(m.get(2, 1) == 0.0);
    Assert.assertTrue(m.get(2, 2) == 1.0);
    Assert.assertTrue(m.get(2, 3) == 0.0);

    Assert.assertTrue(m.get(3, 0) == 0.0);
    Assert.assertTrue(m.get(3, 1) == 0.0);
    Assert.assertTrue(m.get(3, 2) == 0.0);
    Assert.assertTrue(m.get(3, 3) == 1.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testInvertIdentity()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertIdentityContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4D.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertSimple()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);
    m0.set(0, 2, 0.0);
    m0.set(0, 3, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);
    m0.set(1, 2, 0.0);
    m0.set(1, 3, 0.0);

    m0.set(2, 0, 0.0);
    m0.set(2, 1, 0.0);
    m0.set(2, 2, 2.0);
    m0.set(2, 3, 0.0);

    m0.set(3, 0, 0.0);
    m0.set(3, 1, 0.0);
    m0.set(3, 2, 0.0);
    m0.set(3, 3, 2.0);

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);
      Assert.assertTrue(rm.get(0, 3) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
      Assert.assertTrue(rm.get(1, 2) == 0);
      Assert.assertTrue(rm.get(1, 3) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 0.5);
      Assert.assertTrue(rm.get(2, 3) == 0);

      Assert.assertTrue(rm.get(3, 0) == 0);
      Assert.assertTrue(rm.get(3, 1) == 0);
      Assert.assertTrue(rm.get(3, 2) == 0);
      Assert.assertTrue(rm.get(3, 3) == 0.5);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);
      Assert.assertTrue(rm.get(0, 3) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
      Assert.assertTrue(rm.get(1, 2) == 0);
      Assert.assertTrue(rm.get(1, 3) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 2);
      Assert.assertTrue(rm.get(2, 3) == 0);

      Assert.assertTrue(rm.get(3, 0) == 0);
      Assert.assertTrue(rm.get(3, 1) == 0);
      Assert.assertTrue(rm.get(3, 2) == 0);
      Assert.assertTrue(rm.get(3, 3) == 2);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertSimple2()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    boolean eq = false;

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 0.0);
    m0.set(0, 2, 5.0);
    m0.set(0, 3, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);
    m0.set(1, 2, 0.0);
    m0.set(1, 3, 11.0);

    m0.set(2, 0, 7.0);
    m0.set(2, 1, 0.0);
    m0.set(2, 2, 3.0);
    m0.set(2, 3, 0.0);

    m0.set(3, 0, 0.0);
    m0.set(3, 1, 13.0);
    m0.set(3, 2, 0.0);
    m0.set(3, 3, 4.0);

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 3), -0.01481);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 0), 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 2), 5.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 1), 2.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 3), 11.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 0), 7.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 2), 3.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 1), 13.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 3), 4.0);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    boolean eq = false;

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 0.0);
    m0.set(0, 2, 5.0);
    m0.set(0, 3, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);
    m0.set(1, 2, 0.0);
    m0.set(1, 3, 11.0);

    m0.set(2, 0, 7.0);
    m0.set(2, 1, 0.0);
    m0.set(2, 2, 3.0);
    m0.set(2, 3, 0.0);

    m0.set(3, 0, 0.0);
    m0.set(3, 1, 13.0);
    m0.set(3, 2, 0.0);
    m0.set(3, 3, 4.0);

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 3), -0.01481);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 0), 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 2), 5.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 1), 2.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(1, 3), 11.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 0), 7.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 2), 3.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 1), 13.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, rm.get(3, 3), 4.0);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertSimpleContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    m0.set(0, 0, 2.0);
    m0.set(0, 1, 0.0);
    m0.set(0, 2, 0.0);
    m0.set(0, 3, 0.0);

    m0.set(1, 0, 0.0);
    m0.set(1, 1, 2.0);
    m0.set(1, 2, 0.0);
    m0.set(1, 3, 0.0);

    m0.set(2, 0, 0.0);
    m0.set(2, 1, 0.0);
    m0.set(2, 2, 2.0);
    m0.set(2, 3, 0.0);

    m0.set(3, 0, 0.0);
    m0.set(3, 1, 0.0);
    m0.set(3, 2, 0.0);
    m0.set(3, 3, 2.0);

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 0.5);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);
      Assert.assertTrue(rm.get(0, 3) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 0.5);
      Assert.assertTrue(rm.get(1, 2) == 0);
      Assert.assertTrue(rm.get(1, 3) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 0.5);
      Assert.assertTrue(rm.get(2, 3) == 0);

      Assert.assertTrue(rm.get(3, 0) == 0);
      Assert.assertTrue(rm.get(3, 1) == 0);
      Assert.assertTrue(rm.get(3, 2) == 0);
      Assert.assertTrue(rm.get(3, 3) == 0.5);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4D> s = (Some<MatrixM4x4D>) r;
      final MatrixM4x4D rm = s.value;

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());

      Assert.assertTrue(rm.get(0, 0) == 2);
      Assert.assertTrue(rm.get(0, 1) == 0);
      Assert.assertTrue(rm.get(0, 2) == 0);
      Assert.assertTrue(rm.get(0, 3) == 0);

      Assert.assertTrue(rm.get(1, 0) == 0);
      Assert.assertTrue(rm.get(1, 1) == 2);
      Assert.assertTrue(rm.get(1, 2) == 0);
      Assert.assertTrue(rm.get(1, 3) == 0);

      Assert.assertTrue(rm.get(2, 0) == 0);
      Assert.assertTrue(rm.get(2, 1) == 0);
      Assert.assertTrue(rm.get(2, 2) == 2);
      Assert.assertTrue(rm.get(2, 3) == 0);

      Assert.assertTrue(rm.get(3, 0) == 0);
      Assert.assertTrue(rm.get(3, 1) == 0);
      Assert.assertTrue(rm.get(3, 2) == 0);
      Assert.assertTrue(rm.get(3, 3) == 2);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(rm).position());
    }
  }

  @Override @Test public void testInvertZero()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    MatrixM4x4D.setZero(m0);

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }

    {
      final Option<MatrixM4x4D> r = MatrixM4x4D.invertInPlace(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
    }
  }

  @Override @Test public void testInvertZeroContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    MatrixM4x4D.setZero(m0);

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    }

    {
      final Option<MatrixM4x4D> r =
        MatrixM4x4D.invertInPlaceWithContext(context, m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    }
  }

  @Override @Test public void testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(-1, 0, 0);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(0, 0, -1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(1, 0, 0);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D origin = new VectorI3D(0, 0, 0);
    final VectorI3D target = new VectorI3D(0, 0, 1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -1.0, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    void
    testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D origin = new VectorI3D(20 + 0, 30 + 0, 40 + 0);
    final VectorI3D target = new VectorI3D(20 + 0, 30 + 0, 40 + -1);
    final VectorI3D axis = new VectorI3D(0, 1, 0);
    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualDouble.almostEqual(ec, -20.0, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -30.0, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, -40.0, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 0.0, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(ec, 1.0, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public void testMultiplyIdentity()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    final MatrixM4x4D r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
        Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
      }
    }
  }

  @Override @Test public void testMultiplyMutateIdentity()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    final MatrixM4x4D r = MatrixM4x4D.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testMultiplyMutateSimple()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();

    MatrixM4x4D.set(m0, 0, 0, 1.0);
    MatrixM4x4D.set(m0, 0, 1, 2.0);
    MatrixM4x4D.set(m0, 0, 2, 3.0);
    MatrixM4x4D.set(m0, 0, 3, 4.0);
    MatrixM4x4D.set(m0, 1, 0, 5.0);
    MatrixM4x4D.set(m0, 1, 1, 6.0);
    MatrixM4x4D.set(m0, 1, 2, 7.0);
    MatrixM4x4D.set(m0, 1, 3, 8.0);
    MatrixM4x4D.set(m0, 2, 0, 9.0);
    MatrixM4x4D.set(m0, 2, 1, 10.0);
    MatrixM4x4D.set(m0, 2, 2, 11.0);
    MatrixM4x4D.set(m0, 2, 3, 12.0);
    MatrixM4x4D.set(m0, 3, 0, 13.0);
    MatrixM4x4D.set(m0, 3, 1, 14.0);
    MatrixM4x4D.set(m0, 3, 2, 15.0);
    MatrixM4x4D.set(m0, 3, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());

    final MatrixM4x4D m1 = new MatrixM4x4D(m0);
    final MatrixM4x4D r = MatrixM4x4D.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(MatrixM4x4D.get(r, 0, 0) == 90.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 1) == 100.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 2) == 110.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 3) == 120.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 0) == 202.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 1) == 228.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 2) == 254.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 3) == 280.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 0) == 314.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 1) == 356.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 2) == 398.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 3) == 440.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 0) == 426.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 1) == 484.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 2) == 542.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 3) == 600.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testMultiplySimple()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();

    MatrixM4x4D.set(m0, 0, 0, 1.0);
    MatrixM4x4D.set(m0, 0, 1, 2.0);
    MatrixM4x4D.set(m0, 0, 2, 3.0);
    MatrixM4x4D.set(m0, 0, 3, 4.0);
    MatrixM4x4D.set(m0, 1, 0, 5.0);
    MatrixM4x4D.set(m0, 1, 1, 6.0);
    MatrixM4x4D.set(m0, 1, 2, 7.0);
    MatrixM4x4D.set(m0, 1, 3, 8.0);
    MatrixM4x4D.set(m0, 2, 0, 9.0);
    MatrixM4x4D.set(m0, 2, 1, 10.0);
    MatrixM4x4D.set(m0, 2, 2, 11.0);
    MatrixM4x4D.set(m0, 2, 3, 12.0);
    MatrixM4x4D.set(m0, 3, 0, 13.0);
    MatrixM4x4D.set(m0, 3, 1, 14.0);
    MatrixM4x4D.set(m0, 3, 2, 15.0);
    MatrixM4x4D.set(m0, 3, 3, 16.0);

    final MatrixM4x4D m1 = new MatrixM4x4D(m0);
    final MatrixM4x4D mr = new MatrixM4x4D();

    final MatrixM4x4D r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    Assert.assertTrue(MatrixM4x4D.get(r, 0, 0) == 90.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 1) == 100.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 2) == 110.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 0, 3) == 120.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 0) == 202.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 1) == 228.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 2) == 254.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 1, 3) == 280.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 0) == 314.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 1) == 356.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 2) == 398.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 2, 3) == 440.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 0) == 426.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 1) == 484.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 2) == 542.0);
    Assert.assertTrue(MatrixM4x4D.get(r, 3, 3) == 600.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
  }

  @Override @Test public void testMultiplyVectorSimple()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();

    MatrixM4x4D.set(m0, 0, 0, 1.0);
    MatrixM4x4D.set(m0, 0, 1, 2.0);
    MatrixM4x4D.set(m0, 0, 2, 3.0);
    MatrixM4x4D.set(m0, 0, 3, 4.0);
    MatrixM4x4D.set(m0, 1, 0, 5.0);
    MatrixM4x4D.set(m0, 1, 1, 6.0);
    MatrixM4x4D.set(m0, 1, 2, 7.0);
    MatrixM4x4D.set(m0, 1, 3, 8.0);
    MatrixM4x4D.set(m0, 2, 0, 9.0);
    MatrixM4x4D.set(m0, 2, 1, 10.0);
    MatrixM4x4D.set(m0, 2, 2, 11.0);
    MatrixM4x4D.set(m0, 2, 3, 12.0);
    MatrixM4x4D.set(m0, 3, 0, 13.0);
    MatrixM4x4D.set(m0, 3, 1, 14.0);
    MatrixM4x4D.set(m0, 3, 2, 15.0);
    MatrixM4x4D.set(m0, 3, 3, 16.0);

    final VectorI4D v = new VectorI4D(1.0, 2.0, 3.0, 4.0);
    final VectorM4D out = new VectorM4D();

    final VectorM4D r = MatrixM4x4D.multiplyVector4D(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());

    Assert.assertTrue(out.x == 30.0);
    Assert.assertTrue(out.y == 70.0);
    Assert.assertTrue(out.z == 110.0);
    Assert.assertTrue(out.w == 150.0);
  }

  @Override @Test public void testMultiplyVectorSimpleContextEquivalent()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();

    MatrixM4x4D.set(m0, 0, 0, 1.0);
    MatrixM4x4D.set(m0, 0, 1, 2.0);
    MatrixM4x4D.set(m0, 0, 2, 3.0);
    MatrixM4x4D.set(m0, 0, 3, 4.0);
    MatrixM4x4D.set(m0, 1, 0, 5.0);
    MatrixM4x4D.set(m0, 1, 1, 6.0);
    MatrixM4x4D.set(m0, 1, 2, 7.0);
    MatrixM4x4D.set(m0, 1, 3, 8.0);
    MatrixM4x4D.set(m0, 2, 0, 9.0);
    MatrixM4x4D.set(m0, 2, 1, 10.0);
    MatrixM4x4D.set(m0, 2, 2, 11.0);
    MatrixM4x4D.set(m0, 2, 3, 12.0);
    MatrixM4x4D.set(m0, 3, 0, 13.0);
    MatrixM4x4D.set(m0, 3, 1, 14.0);
    MatrixM4x4D.set(m0, 3, 2, 15.0);
    MatrixM4x4D.set(m0, 3, 3, 16.0);

    final VectorI4D v = new VectorI4D(1.0, 2.0, 3.0, 4.0);
    final VectorM4D out = new VectorM4D();
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();

    final VectorM4D r =
      MatrixM4x4D.multiplyVector4DWithContext(context, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());

    Assert.assertTrue(out.x == 30.0);
    Assert.assertTrue(out.y == 70.0);
    Assert.assertTrue(out.z == 110.0);
    Assert.assertTrue(out.w == 150.0);
  }

  @Override @Test public void testMultiplyZero()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    MatrixM4x4D.setZero(m1);

    final MatrixM4x4D r = MatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.get(0, -1);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.get(-1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.get(0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.get(4, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Override @Test public void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D mt = new MatrixM4x4D();
    final MatrixM4x4D mi = new MatrixM4x4D();
    final VectorM3D axis = new VectorM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      double angle = Math.random() * (2 * Math.PI);
      axis.x = Math.random();
      axis.y = Math.random();
      axis.z = Math.random();

      if (Math.random() > 0.5) {
        angle = -angle;
      }
      if (Math.random() > 0.5) {
        axis.x = -axis.x;
      }
      if (Math.random() > 0.5) {
        axis.y = -axis.y;
      }
      if (Math.random() > 0.5) {
        axis.z = -axis.z;
      }
      VectorM3D.normalizeInPlace(axis);

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      MatrixM4x4D.makeRotation(angle, axis, m);

      final double det = MatrixM4x4D.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM4x4D.invert(m, mi);
      MatrixM4x4D.transpose(m, mt);

      for (int row = 0; row < 4; ++row) {
        for (int col = 0; col < 4; ++col) {
          final double mx = mi.get(row, col);
          final double my = mt.get(row, col);
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

  @Override @Test public void testRotateVector0X()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_in = new VectorM4D(0, 0, -1, 1);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0, 0, -1, 1);

    MatrixM4x4D.makeRotation(0, MatrixM4x4DTest.AXIS_X, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Override @Test public void testRotateVector0Y()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_in = new VectorM4D(0, 0, -1, 1);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0, 0, -1, 1);

    MatrixM4x4D.makeRotation(0, MatrixM4x4DTest.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4D.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Override @Test public void testRotateVector0Z()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_in = new VectorM4D(0, 0, -1, 1);
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_exp = new VectorM4D(0, 0, -1, 1);

    MatrixM4x4D.makeRotation(0, MatrixM4x4DTest.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

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

  @Override @Test public void testRotateVector90X()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(0, 6.1232339957367E-17, 1, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(90), MatrixM4x4DTest.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Y axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public void testRotateVector90Y()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(-1, 0, -6.1232339957367E-17, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(90), MatrixM4x4DTest.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Z axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public void testRotateVector90Z()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 1, 0, 1);
    final VectorM4D v_exp = new VectorM4D(-1, 6.123233995736766E-17, 0, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(90), MatrixM4x4DTest.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the X axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90X()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(0, 6.1232339957367E-17, -1, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(-90), MatrixM4x4DTest.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Y axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90Y()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4D v_exp = new VectorM4D(1, 0, -6.1232339957367E-17, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(-90), MatrixM4x4DTest.AXIS_Y, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Z axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public void testRotateVectorMinus90Z()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v_got = new VectorM4D();
    final VectorM4D v_in = new VectorM4D(0, 1, 0, 1);
    final VectorM4D v_exp = new VectorM4D(1, 6.123233995736766E-17, 0, 1);

    MatrixM4x4D.makeRotation(Math.toRadians(-90), MatrixM4x4DTest.AXIS_Z, m);
    MatrixM4x4D.multiplyVector4D(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualDouble.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  @Override @Test public void testRotateX()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r =
        MatrixM4x4D
          .rotate(Math.toRadians(45), m, MatrixM4x4DTest.AXIS_X, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(out).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_X);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateXContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final Context context = new Context();
    {
      final MatrixM4x4D out = new MatrixM4x4D();

      final MatrixM4x4D r =
        MatrixM4x4D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_X,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(out).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixX(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_X);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      MatrixM4x4DTest.isRotationMatrixX(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateXMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM4x4D r =
        MatrixM4x4D.makeRotation(Math.toRadians(45), MatrixM4x4DTest.AXIS_X);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateY()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r =
        MatrixM4x4D
          .rotate(Math.toRadians(45), m, MatrixM4x4DTest.AXIS_Y, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateYContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final Context context = new Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r =
        MatrixM4x4D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Y,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixY(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixY(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateYMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM4x4D r =
        MatrixM4x4D.makeRotation(Math.toRadians(45), MatrixM4x4DTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateZ()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r =
        MatrixM4x4D
          .rotate(Math.toRadians(45), m, MatrixM4x4DTest.AXIS_Z, out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateZContextEquivalentInPlace()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final Context context = new Context();
    final MatrixM4x4D m = new MatrixM4x4D();
    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r =
        MatrixM4x4D.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Z,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r =
        MatrixM4x4D.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4DTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixZ(context_d, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRotateZMakeEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    {
      final MatrixM4x4D r =
        MatrixM4x4D.makeRotation(Math.toRadians(45), MatrixM4x4DTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      final double det = MatrixM4x4D.determinant(r);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 1.0, det));

      System.out.println(r);

      MatrixM4x4DTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testRow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v = new VectorM4D();
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    MatrixM4x4D.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    MatrixM4x4D.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    MatrixM4x4D.row(m, 2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    MatrixM4x4D.row(m, 3, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 1.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testRow4()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorM4D v = new VectorM4D();
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    m.getRow4D(0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    m.getRow4D(1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    m.getRow4D(2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    m.getRow4D(3, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 1.0);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow4Overflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.getRow4D(4, new VectorM4D());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRow4Underflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    m.getRow4D(-1, new VectorM4D());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowOverflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.row(m, 4, new VectorM4D());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testRowUnderflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.row(m, -1, new VectorM4D());
  }

  @Override @Test public void testScale()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 3.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    final MatrixM4x4D mk = MatrixM4x4D.scale(m0, 5.0, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mk).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mk).position());
  }

  @Override @Test public void testScaleMutate()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m.set(row, column, 3.0);
      }
    }

    final MatrixM4x4D mr = MatrixM4x4D.scaleInPlace(m, 5.0);
    Assert.assertSame(mr, m);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(mr).position());
  }

  @Override @Test public void testScaleRow()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 4.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);
    m0.set(1, 3, 8.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);
    m0.set(2, 3, 12.0);

    m0.set(3, 0, 13.0);
    m0.set(3, 1, 14.0);
    m0.set(3, 2, 15.0);
    m0.set(3, 3, 16.0);

    MatrixM4x4D.scaleRow(m0, 0, 2.0, m1);
    MatrixM4x4D.scaleRow(m0, 1, 4.0, m1);
    MatrixM4x4D.scaleRow(m0, 2, 8.0, m1);
    MatrixM4x4D.scaleRow(m0, 3, 16.0, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);
    Assert.assertTrue(m1.get(0, 2) == 6.0);
    Assert.assertTrue(m1.get(0, 3) == 8.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);
    Assert.assertTrue(m1.get(1, 2) == 28.0);
    Assert.assertTrue(m1.get(1, 3) == 32.0);

    Assert.assertTrue(m1.get(2, 0) == 72.0);
    Assert.assertTrue(m1.get(2, 1) == 80.0);
    Assert.assertTrue(m1.get(2, 2) == 88.0);
    Assert.assertTrue(m1.get(2, 3) == 96.0);

    Assert.assertTrue(m1.get(3, 0) == 208.0);
    Assert.assertTrue(m1.get(3, 1) == 224.0);
    Assert.assertTrue(m1.get(3, 2) == 240.0);
    Assert.assertTrue(m1.get(3, 3) == 256.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.scaleRowInPlace(m0, 0, 2.0);
    MatrixM4x4D.scaleRowInPlace(m0, 1, 4.0);
    MatrixM4x4D.scaleRowInPlace(m0, 2, 8.0);
    MatrixM4x4D.scaleRowInPlace(m0, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);
    Assert.assertTrue(m0.get(0, 2) == 6.0);
    Assert.assertTrue(m0.get(0, 3) == 8.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
    Assert.assertTrue(m0.get(1, 2) == 28.0);
    Assert.assertTrue(m0.get(1, 3) == 32.0);

    Assert.assertTrue(m0.get(2, 0) == 72.0);
    Assert.assertTrue(m0.get(2, 1) == 80.0);
    Assert.assertTrue(m0.get(2, 2) == 88.0);
    Assert.assertTrue(m0.get(2, 3) == 96.0);

    Assert.assertTrue(m0.get(3, 0) == 208.0);
    Assert.assertTrue(m0.get(3, 1) == 224.0);
    Assert.assertTrue(m0.get(3, 2) == 240.0);
    Assert.assertTrue(m0.get(3, 3) == 256.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test public void testScaleRowContextEquivalent()
  {
    final MatrixM4x4D.Context context = new MatrixM4x4D.Context();
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    m0.set(0, 0, 1.0);
    m0.set(0, 1, 2.0);
    m0.set(0, 2, 3.0);
    m0.set(0, 3, 4.0);

    m0.set(1, 0, 5.0);
    m0.set(1, 1, 6.0);
    m0.set(1, 2, 7.0);
    m0.set(1, 3, 8.0);

    m0.set(2, 0, 9.0);
    m0.set(2, 1, 10.0);
    m0.set(2, 2, 11.0);
    m0.set(2, 3, 12.0);

    m0.set(3, 0, 13.0);
    m0.set(3, 1, 14.0);
    m0.set(3, 2, 15.0);
    m0.set(3, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.scaleRowWithContext(context, m0, 0, 2.0, m1);
    MatrixM4x4D.scaleRowWithContext(context, m0, 1, 4.0, m1);
    MatrixM4x4D.scaleRowWithContext(context, m0, 2, 8.0, m1);
    MatrixM4x4D.scaleRowWithContext(context, m0, 3, 16.0, m1);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 2.0);
    Assert.assertTrue(m1.get(0, 1) == 4.0);
    Assert.assertTrue(m1.get(0, 2) == 6.0);
    Assert.assertTrue(m1.get(0, 3) == 8.0);

    Assert.assertTrue(m1.get(1, 0) == 20.0);
    Assert.assertTrue(m1.get(1, 1) == 24.0);
    Assert.assertTrue(m1.get(1, 2) == 28.0);
    Assert.assertTrue(m1.get(1, 3) == 32.0);

    Assert.assertTrue(m1.get(2, 0) == 72.0);
    Assert.assertTrue(m1.get(2, 1) == 80.0);
    Assert.assertTrue(m1.get(2, 2) == 88.0);
    Assert.assertTrue(m1.get(2, 3) == 96.0);

    Assert.assertTrue(m1.get(3, 0) == 208.0);
    Assert.assertTrue(m1.get(3, 1) == 224.0);
    Assert.assertTrue(m1.get(3, 2) == 240.0);
    Assert.assertTrue(m1.get(3, 3) == 256.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    MatrixM4x4D.scaleRowInPlaceWithContext(context, m0, 0, 2.0);
    MatrixM4x4D.scaleRowInPlaceWithContext(context, m0, 1, 4.0);
    MatrixM4x4D.scaleRowInPlaceWithContext(context, m0, 2, 8.0);
    MatrixM4x4D.scaleRowInPlaceWithContext(context, m0, 3, 16.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());

    Assert.assertTrue(m0.get(0, 0) == 2.0);
    Assert.assertTrue(m0.get(0, 1) == 4.0);
    Assert.assertTrue(m0.get(0, 2) == 6.0);
    Assert.assertTrue(m0.get(0, 3) == 8.0);

    Assert.assertTrue(m0.get(1, 0) == 20.0);
    Assert.assertTrue(m0.get(1, 1) == 24.0);
    Assert.assertTrue(m0.get(1, 2) == 28.0);
    Assert.assertTrue(m0.get(1, 3) == 32.0);

    Assert.assertTrue(m0.get(2, 0) == 72.0);
    Assert.assertTrue(m0.get(2, 1) == 80.0);
    Assert.assertTrue(m0.get(2, 2) == 88.0);
    Assert.assertTrue(m0.get(2, 3) == 96.0);

    Assert.assertTrue(m0.get(3, 0) == 208.0);
    Assert.assertTrue(m0.get(3, 1) == 224.0);
    Assert.assertTrue(m0.get(3, 2) == 240.0);
    Assert.assertTrue(m0.get(3, 3) == 256.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.scaleRowInPlace(m, 4, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.scaleRowInPlace(m, -1, 1.0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowOverflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D r = new MatrixM4x4D();
    MatrixM4x4D.scaleRow(m, 4, 1.0, r);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    void
    testScaleRowUnderflow()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D r = new MatrixM4x4D();
    MatrixM4x4D.scaleRow(m, -1, 1.0, r);
  }

  @Override @Test public void testSetGetIdentity()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    Assert.assertTrue(m.set(0, 0, 3.0).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).get(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0).get(0, 2) == 7.0);
    Assert.assertTrue(m.set(0, 3, 11.0).get(0, 3) == 11.0);

    Assert.assertTrue(m.set(1, 0, 13.0).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).get(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0).get(1, 2) == 19.0);
    Assert.assertTrue(m.set(1, 3, 23.0).get(1, 3) == 23.0);

    Assert.assertTrue(m.set(2, 0, 29.0).get(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0).get(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0).get(2, 2) == 37.0);
    Assert.assertTrue(m.set(2, 3, 41.0).get(2, 3) == 41.0);

    Assert.assertTrue(m.set(3, 0, 43.0).get(3, 0) == 43.0);
    Assert.assertTrue(m.set(3, 1, 47.0).get(3, 1) == 47.0);
    Assert.assertTrue(m.set(3, 2, 53.0).get(3, 2) == 53.0);
    Assert.assertTrue(m.set(3, 3, 59.0).get(3, 3) == 59.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testSetGetInterfaceIdentity()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    Assert.assertTrue(m.set(0, 0, 3.0).getRowColumnD(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0).getRowColumnD(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0).getRowColumnD(0, 2) == 7.0);
    Assert.assertTrue(m.set(0, 3, 11.0).getRowColumnD(0, 3) == 11.0);

    Assert.assertTrue(m.set(1, 0, 13.0).getRowColumnD(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0).getRowColumnD(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0).getRowColumnD(1, 2) == 19.0);
    Assert.assertTrue(m.set(1, 3, 23.0).getRowColumnD(1, 3) == 23.0);

    Assert.assertTrue(m.set(2, 0, 29.0).getRowColumnD(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0).getRowColumnD(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0).getRowColumnD(2, 2) == 37.0);
    Assert.assertTrue(m.set(2, 3, 41.0).getRowColumnD(2, 3) == 41.0);

    Assert.assertTrue(m.set(3, 0, 43.0).getRowColumnD(3, 0) == 43.0);
    Assert.assertTrue(m.set(3, 1, 47.0).getRowColumnD(3, 1) == 47.0);
    Assert.assertTrue(m.set(3, 2, 53.0).getRowColumnD(3, 2) == 53.0);
    Assert.assertTrue(m.set(3, 3, 59.0).getRowColumnD(3, 3) == 59.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

  @Override @Test public void testStorage()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    m.set(0, 0, 0);
    m.set(0, 1, 1);
    m.set(0, 2, 2);
    m.set(0, 3, 3);

    m.set(1, 0, 100);
    m.set(1, 1, 101);
    m.set(1, 2, 102);
    m.set(1, 3, 103);

    m.set(2, 0, 200);
    m.set(2, 1, 201);
    m.set(2, 2, 202);
    m.set(2, 3, 203);

    m.set(3, 0, 300);
    m.set(3, 1, 301);
    m.set(3, 2, 302);
    m.set(3, 3, 303);

    {
      final DoubleBuffer b = MatrixM4x4D.doubleBuffer(m);

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 0.0);
      Assert.assertTrue(b.get(1) == 100.0);
      Assert.assertTrue(b.get(2) == 200.0);
      Assert.assertTrue(b.get(3) == 300.0);

      Assert.assertTrue(b.get(4) == 1.0);
      Assert.assertTrue(b.get(5) == 101.0);
      Assert.assertTrue(b.get(6) == 201.0);
      Assert.assertTrue(b.get(7) == 301.0);

      Assert.assertTrue(b.get(8) == 2.0);
      Assert.assertTrue(b.get(9) == 102.0);
      Assert.assertTrue(b.get(10) == 202.0);
      Assert.assertTrue(b.get(11) == 302.0);

      Assert.assertTrue(b.get(12) == 3.0);
      Assert.assertTrue(b.get(13) == 103.0);
      Assert.assertTrue(b.get(14) == 203.0);
      Assert.assertTrue(b.get(15) == 303.0);
    }
  }

  @Override @Test public void testString()
  {
    final MatrixM4x4D m0 = new MatrixM4x4D();
    final MatrixM4x4D m1 = new MatrixM4x4D();
    final MatrixM4x4D m2 = new MatrixM4x4D();
    m2.set(0, 0, 2.0);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));
  }

  @Override @Test public void testTrace()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    final double t = MatrixM4x4D.trace(m);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, 4.0, t));
  }

  @Override @Test public void testTranslate3_4_Equivalent()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m3 = new MatrixM3x3D();
    final MatrixM4x4D m4 = new MatrixM4x4D();
    final VectorI3D v = new VectorI3D(3.0, 7.0, 0.0);
    final VectorM3D v3i = new VectorM3D(1, 1, 1);
    final VectorM3D v3o = new VectorM3D();
    final VectorM4D w3i = new VectorM4D(1, 1, 1, 1);
    final VectorM4D w3o = new VectorM4D();

    MatrixM3x3D.makeTranslation2D(v, m3);
    MatrixM4x4D.makeTranslation3D(v, m4);

    MatrixM3x3D.multiplyVector3D(m3, v3i, v3o);
    MatrixM4x4D.multiplyVector4D(m4, w3i, w3o);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v3o.x, w3o.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v3o.y, w3o.y));
  }

  @Override @Test public void testTranslateSimple2Integer()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D out = new MatrixM4x4D();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple2IntegerAlt()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple2Real()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D out = new MatrixM4x4D();
    final VectorI2D v = new VectorI2D(1.0, 2.0);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple2RealAlt()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI2D v = new VectorI2D(1.0, 2.0);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector2DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 0.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple3Integer()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D out = new MatrixM4x4D();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 6.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple3IntegerAlt()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 6.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple3Real()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D out = new MatrixM4x4D();
    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 6.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslateSimple3RealAlt()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3DInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 2.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 4.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 6.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslationEquivalent3Integer()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = new MatrixM4x4D();
      final MatrixM4x4D t = new MatrixM4x4D();

      MatrixM4x4D.makeTranslation3I(v, t);
      MatrixM4x4D.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslationEquivalent3Real()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);

    {
      final MatrixM4x4D out = new MatrixM4x4D();
      final MatrixM4x4D r = MatrixM4x4D.translateByVector3D(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }

    {
      final MatrixM4x4D r = new MatrixM4x4D();
      final MatrixM4x4D t = new MatrixM4x4D();

      MatrixM4x4D.makeTranslation3D(v, t);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      MatrixM4x4D.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslationMakeEquivalent3Integer()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4D r = new MatrixM4x4D();
      final MatrixM4x4D t = MatrixM4x4D.makeTranslation3I(v);
      MatrixM4x4D.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslationMakeEquivalent3Real()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final VectorI3D v = new VectorI3D(1.0, 2.0, 3.0);

    {
      final MatrixM4x4D r = new MatrixM4x4D();
      final MatrixM4x4D t = MatrixM4x4D.makeTranslation3D(v);
      MatrixM4x4D.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

      Assert.assertTrue(r.get(0, 0) == 1.0);
      Assert.assertTrue(r.get(0, 1) == 0.0);
      Assert.assertTrue(r.get(0, 2) == 0.0);
      Assert.assertTrue(r.get(0, 3) == 1.0);

      Assert.assertTrue(r.get(1, 0) == 0.0);
      Assert.assertTrue(r.get(1, 1) == 1.0);
      Assert.assertTrue(r.get(1, 2) == 0.0);
      Assert.assertTrue(r.get(1, 3) == 2.0);

      Assert.assertTrue(r.get(2, 0) == 0.0);
      Assert.assertTrue(r.get(2, 1) == 0.0);
      Assert.assertTrue(r.get(2, 2) == 1.0);
      Assert.assertTrue(r.get(2, 3) == 3.0);

      Assert.assertTrue(r.get(3, 0) == 0.0);
      Assert.assertTrue(r.get(3, 1) == 0.0);
      Assert.assertTrue(r.get(3, 2) == 0.0);
      Assert.assertTrue(r.get(3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
    }
  }

  @Override @Test public void testTranslationStorage()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D out = new MatrixM4x4D();

    MatrixM4x4D.translateByVector3D(m, new VectorI3D(1.0, 2.0, 3.0), out);

    {
      final DoubleBuffer b = MatrixM4x4D.doubleBuffer(out);

      Assert.assertTrue(b.order() == ByteOrder.nativeOrder());
      Assert.assertEquals(0, b.position());

      Assert.assertTrue(b.get(0) == 1.0);
      Assert.assertTrue(b.get(1) == 0.0);
      Assert.assertTrue(b.get(2) == 0.0);
      Assert.assertTrue(b.get(3) == 0.0);

      Assert.assertTrue(b.get(4) == 0.0);
      Assert.assertTrue(b.get(5) == 1.0);
      Assert.assertTrue(b.get(6) == 0.0);
      Assert.assertTrue(b.get(7) == 0.0);

      Assert.assertTrue(b.get(8) == 0.0);
      Assert.assertTrue(b.get(9) == 0.0);
      Assert.assertTrue(b.get(10) == 1.0);
      Assert.assertTrue(b.get(11) == 0.0);

      Assert.assertTrue(b.get(12) == 1.0);
      Assert.assertTrue(b.get(13) == 2.0);
      Assert.assertTrue(b.get(14) == 3.0);
      Assert.assertTrue(b.get(15) == 1.0);
    }
  }

  @Override @Test public void testTranspose()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    final MatrixM4x4D r = new MatrixM4x4D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);
    m.set(0, 2, 2.0);
    m.set(0, 3, 3.0);
    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);
    m.set(1, 2, 6.0);
    m.set(1, 3, 7.0);
    m.set(2, 0, 8.0);
    m.set(2, 1, 9.0);
    m.set(2, 2, 10.0);
    m.set(2, 3, 11.0);
    m.set(3, 0, 12.0);
    m.set(3, 1, 13.0);
    m.set(3, 2, 14.0);
    m.set(3, 3, 15.0);

    final MatrixM4x4D k = MatrixM4x4D.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

    Assert.assertTrue(m.get(0, 0) == 0.0);
    Assert.assertTrue(m.get(0, 1) == 1.0);
    Assert.assertTrue(m.get(0, 2) == 2.0);
    Assert.assertTrue(m.get(0, 3) == 3.0);
    Assert.assertTrue(m.get(1, 0) == 4.0);
    Assert.assertTrue(m.get(1, 1) == 5.0);
    Assert.assertTrue(m.get(1, 2) == 6.0);
    Assert.assertTrue(m.get(1, 3) == 7.0);
    Assert.assertTrue(m.get(2, 0) == 8.0);
    Assert.assertTrue(m.get(2, 1) == 9.0);
    Assert.assertTrue(m.get(2, 2) == 10.0);
    Assert.assertTrue(m.get(2, 3) == 11.0);
    Assert.assertTrue(m.get(3, 0) == 12.0);
    Assert.assertTrue(m.get(3, 1) == 13.0);
    Assert.assertTrue(m.get(3, 2) == 14.0);
    Assert.assertTrue(m.get(3, 3) == 15.0);

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);
    Assert.assertTrue(r.get(0, 2) == 8.0);
    Assert.assertTrue(r.get(0, 3) == 12.0);
    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertTrue(r.get(1, 2) == 9.0);
    Assert.assertTrue(r.get(1, 3) == 13.0);
    Assert.assertTrue(r.get(2, 0) == 2.0);
    Assert.assertTrue(r.get(2, 1) == 6.0);
    Assert.assertTrue(r.get(2, 2) == 10.0);
    Assert.assertTrue(r.get(2, 3) == 14.0);
    Assert.assertTrue(r.get(3, 0) == 3.0);
    Assert.assertTrue(r.get(3, 1) == 7.0);
    Assert.assertTrue(r.get(3, 2) == 11.0);
    Assert.assertTrue(r.get(3, 3) == 15.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
  }

  @Override @Test public void testTransposeMutate()
  {
    final MatrixM4x4D m = new MatrixM4x4D();

    m.set(0, 0, 0.0);
    m.set(0, 1, 1.0);
    m.set(0, 2, 2.0);
    m.set(0, 3, 3.0);
    m.set(1, 0, 4.0);
    m.set(1, 1, 5.0);
    m.set(1, 2, 6.0);
    m.set(1, 3, 7.0);
    m.set(2, 0, 8.0);
    m.set(2, 1, 9.0);
    m.set(2, 2, 10.0);
    m.set(2, 3, 11.0);
    m.set(3, 0, 12.0);
    m.set(3, 1, 13.0);
    m.set(3, 2, 14.0);
    m.set(3, 3, 15.0);

    final MatrixM4x4D r = MatrixM4x4D.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());

    Assert.assertTrue(r.get(0, 0) == 0.0);
    Assert.assertTrue(r.get(0, 1) == 4.0);
    Assert.assertTrue(r.get(0, 2) == 8.0);
    Assert.assertTrue(r.get(0, 3) == 12.0);
    Assert.assertTrue(r.get(1, 0) == 1.0);
    Assert.assertTrue(r.get(1, 1) == 5.0);
    Assert.assertTrue(r.get(1, 2) == 9.0);
    Assert.assertTrue(r.get(1, 3) == 13.0);
    Assert.assertTrue(r.get(2, 0) == 2.0);
    Assert.assertTrue(r.get(2, 1) == 6.0);
    Assert.assertTrue(r.get(2, 2) == 10.0);
    Assert.assertTrue(r.get(2, 3) == 14.0);
    Assert.assertTrue(r.get(3, 0) == 3.0);
    Assert.assertTrue(r.get(3, 1) == 7.0);
    Assert.assertTrue(r.get(3, 2) == 11.0);
    Assert.assertTrue(r.get(3, 3) == 15.0);

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(r).position());
  }

  @Override @Test public void testZero()
  {
    final MatrixM4x4D m = new MatrixM4x4D();
    MatrixM4x4D.setZero(m);
    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4D.doubleBuffer(m).position());
  }

}
