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
import java.nio.FloatBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Option;
import com.io7m.jaux.functional.Option.Some;
import com.io7m.jaux.functional.Option.Type;

public class MatrixM4x4FTTest extends MatrixM4x4TContract
{
  private static final VectorReadable3F AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3F AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3F AXIS_Z = new VectorI3F(0, 0, 1);

  private static <A> void isRotationMatrixX(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4FT<A> r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, -0.707106781187f, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  private static <A> void isRotationMatrixY(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4FT<A> r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, -0.707106781187f, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  private static <A> void isRotationMatrixZ(
    final AlmostEqualFloat.ContextRelative context,
    final MatrixM4x4FT<A> r)
  {
    boolean eq;

    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, -0.707106781187f, r.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(0, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.707106781187f, r.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(1, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(2, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(2, 3));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 0.0f, r.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, 1.0f, r.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public <A> void testAdd()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mr = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    final MatrixM4x4FT<A> mk = MatrixM4x4FT.add(m0, m1, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 1.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test public <A> void testAddMutate()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 1.0f);
        m1.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    final MatrixM4x4FT<A> mr = MatrixM4x4FT.addInPlace(m0, m1);
    Assert.assertSame(mr, m0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 4.0);
        Assert.assertTrue(mr.get(row, column) == 4.0);
        Assert.assertTrue(m1.get(row, column) == 3.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test public <A> void testAddRowScaled()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);
    m0.set(1, 3, 5.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.addRowScaled(m0, 0, 1, 2, 2.0f, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.addRowScaledInPlace(m0, 0, 1, 2, 2.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test public <A> void testAddRowScaledContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 3.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 3.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 5.0f);
    m0.set(1, 2, 5.0f);
    m0.set(1, 3, 5.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.addRowScaledWithContext(context, m0, 0, 1, 2, 2.0f, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowA()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, 4, 0, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowB()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, 0, 4, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledOverflowC()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, 0, 0, 4, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowA()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, -1, 0, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowB()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, 0, -1, 0, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testAddRowScaledUnderflowC()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.addRowScaledInPlace(m, 0, 0, -1, 1.0f);
  }

  @Override @Test public <A> void testBufferEndianness()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final FloatBuffer b = MatrixM4x4FT.floatBuffer(m);

    Assert.assertEquals(ByteOrder.nativeOrder(), b.order());
  }

  @Override @Test public <A> void testCopy()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.copy(m0, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test public <A> void testDeterminantIdentity()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    Assert.assertTrue(MatrixM4x4FT.determinant(m) == 1.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testDeterminantOther()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    m.set(0, 0, 2.0f);
    m.set(1, 1, 2.0f);
    m.set(2, 2, 2.0f);
    m.set(3, 3, 2.0f);

    Assert.assertTrue(MatrixM4x4FT.determinant(m) == 16.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testDeterminantScale()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    m.set(0, 0, 2.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertTrue(MatrixM4x4FT.determinant(m) == 2.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testDeterminantScaleNegative()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    m.set(0, 0, -2.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertTrue(MatrixM4x4FT.determinant(m) == -2.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testDeterminantZero()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    MatrixM4x4FT.setZero(m);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertTrue(MatrixM4x4FT.determinant(m) == 0.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
      Assert.assertTrue(m0.equals(m0));
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    }

    {
      final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
      Assert.assertFalse(m0.equals(null));
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    }

    {
      final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    }

    {
      final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
      Assert.assertTrue(m0.equals(m1));
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    }
  }

  @Override @Test public <A> void testEqualsNotEqualsCorrect()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
        final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        Assert.assertFalse(m0.equals(m1));
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      }
    }
  }

  @Override @Test public <A> void testExchangeRows()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.exchangeRows(m0, 0, 3, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.exchangeRowsInPlace(m1, 0, 3);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAOverflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.exchangeRowsInPlace(m, 4, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsAUnderflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.exchangeRowsInPlace(m, -1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBOverflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.exchangeRowsInPlace(m, 0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testExchangeRowsBUnderflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.exchangeRowsInPlace(m, 0, -1);
  }

  @Override @Test public <A> void testExchangeRowsContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.exchangeRowsWithContext(context, m0, 0, 3, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.exchangeRowsInPlaceWithContext(context, m1, 0, 3);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test public <A> void testHashcodeNeqExhaustive()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
        final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        Assert.assertTrue(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        m1.set(row, col, 256);
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        Assert.assertFalse(m0.hashCode() == m1.hashCode());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      }
    }
  }

  @Override @Test public <A> void testInitializationFrom()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 3.0f);
    m0.set(0, 1, 5.0f);
    m0.set(0, 2, 7.0f);
    m0.set(0, 3, 11.0f);

    m0.set(1, 0, 13.0f);
    m0.set(1, 1, 17.0f);
    m0.set(1, 2, 19.0f);
    m0.set(1, 3, 23.0f);

    m0.set(2, 0, 29.0f);
    m0.set(2, 1, 31.0f);
    m0.set(2, 2, 37.0f);
    m0.set(2, 3, 41.0f);

    m0.set(3, 0, 43.0f);
    m0.set(3, 1, 47.0f);
    m0.set(3, 2, 53.0f);
    m0.set(3, 3, 59.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());

    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>(m0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test public <A> void testInitializationIdentity()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testInvertIdentity()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertIdentityContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 0) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 0, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 1) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 1, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 2) == 1.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 2, 3) == 0.0);

      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 0) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 1) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 2) == 0.0);
      Assert.assertTrue(MatrixM4x4FT.get(rm, 3, 3) == 1.0);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertSimple()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 0.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 0.0f);

    m0.set(2, 0, 0.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 2.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 0.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 2.0f);

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertSimple2()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    boolean eq = false;

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 5.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 11.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 3.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 13.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 4.0f);

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 0), -0.09375f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 2), 0.15625f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 1), -0.0296f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 3), 0.0814f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 0), 0.21875f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 2), -0.03125f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 1), 0.096f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 3), -0.01481f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invertInPlace(m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 3), 4.0f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext3dp();

    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    boolean eq = false;

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 5.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 11.0f);

    m0.set(2, 0, 7.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 3.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 13.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 4.0f);

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 0), -0.09375f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 2), 0.15625f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 1), -0.0296f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 3), 0.0814f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 0), 0.21875f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 2), -0.03125f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 1), 0.096f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 3), -0.01481f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(rm);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 0), 1.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 2), 5.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(0, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 1), 2.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(1, 3), 11.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 0), 7.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 1), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 2), 3.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(2, 3), 0.0f);
      Assert.assertTrue(eq);

      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 0), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 1), 13.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 2), 0.0f);
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context_f, rm.get(3, 3), 4.0f);
      Assert.assertTrue(eq);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertSimpleContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    m0.set(0, 0, 2.0f);
    m0.set(0, 1, 0.0f);
    m0.set(0, 2, 0.0f);
    m0.set(0, 3, 0.0f);

    m0.set(1, 0, 0.0f);
    m0.set(1, 1, 2.0f);
    m0.set(1, 2, 0.0f);
    m0.set(1, 3, 0.0f);

    m0.set(2, 0, 0.0f);
    m0.set(2, 1, 0.0f);
    m0.set(2, 2, 2.0f);
    m0.set(2, 3, 0.0f);

    m0.set(3, 0, 0.0f);
    m0.set(3, 1, 0.0f);
    m0.set(3, 2, 0.0f);
    m0.set(3, 3, 2.0f);

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertInPlaceWithContext(context, m1);
      Assert.assertEquals(Type.OPTION_SOME, r.type);
      final Some<MatrixM4x4FT<A>> s = (Some<MatrixM4x4FT<A>>) r;
      final MatrixM4x4FT<A> rm = s.value;

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(rm).position());
    }
  }

  @Override @Test public <A> void testInvertZero()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.setZero(m0);

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invert(m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r = MatrixM4x4FT.invertInPlace(m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    }
  }

  @Override @Test public <A> void testInvertZeroContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.setZero(m0);

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertWithContext(context, m0, m1);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    }

    {
      final Option<MatrixM4x4FT<A>> r =
        MatrixM4x4FT.invertInPlaceWithContext(context, m0);
      Assert.assertEquals(Type.OPTION_NONE, r.type);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    }
  }

  class World
  {
    // Nothing
  }

  @Override @Test public
    <A>
    void
    testLookAt_NoTranslation_NegativeX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> mc = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3FT<World> origin = new VectorI3FT<World>(0, 0, 0);
    final VectorI3FT<World> target = new VectorI3FT<World>(-1, 0, 0);
    final VectorI3FT<World> axis = new VectorI3FT<World>(0, 1, 0);
    MatrixM4x4FT.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    <A>
    void
    testLookAt_NoTranslation_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> mc = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3FT<World> origin = new VectorI3FT<World>(0, 0, 0);
    final VectorI3FT<World> target = new VectorI3FT<World>(0, 0, -1);
    final VectorI3FT<World> axis = new VectorI3FT<World>(0, 1, 0);
    MatrixM4x4FT.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    <A>
    void
    testLookAt_NoTranslation_PositiveX_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> mc = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3FT<World> origin = new VectorI3FT<World>(0, 0, 0);
    final VectorI3FT<World> target = new VectorI3FT<World>(1, 0, 0);
    final VectorI3FT<World> axis = new VectorI3FT<World>(0, 1, 0);
    MatrixM4x4FT.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    <A>
    void
    testLookAt_NoTranslation_PositiveZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> mc = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3FT<World> origin = new VectorI3FT<World>(0, 0, 0);
    final VectorI3FT<World> target = new VectorI3FT<World>(0, 0, 1);
    final VectorI3FT<World> axis = new VectorI3FT<World>(0, 1, 0);
    MatrixM4x4FT.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -1.0f, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public
    <A>
    void
    testLookAt_Translation102030_NegativeZ_AroundY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> mc = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3FT<World> origin =
      new VectorI3FT<World>(20 + 0, 30 + 0, 40 + 0);
    final VectorI3FT<World> target =
      new VectorI3FT<World>(20 + 0, 30 + 0, 40 + -1);
    final VectorI3FT<World> axis = new VectorI3FT<World>(0, 1, 0);
    MatrixM4x4FT.lookAtWithContext(mc, origin, target, axis, m);

    System.out.println("m : ");
    System.out.println(m);

    boolean eq = false;

    /**
     * Rotation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(2, 2));
    Assert.assertTrue(eq);

    /**
     * Translation components
     */

    eq = AlmostEqualFloat.almostEqual(ec, -20.0f, m.get(0, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -30.0f, m.get(1, 3));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, -40.0f, m.get(2, 3));
    Assert.assertTrue(eq);

    /**
     * Etc
     */

    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 0.0f, m.get(3, 2));
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(ec, 1.0f, m.get(3, 3));
    Assert.assertTrue(eq);
  }

  @Override @Test public <A> void testMultiplyIdentity()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mr = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> r = MatrixM4x4FT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == mr.get(row, column));
        Assert.assertTrue(m1.get(row, column) == mr.get(row, column));
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
      }
    }
  }

  @Override @Test public <A> void testMultiplyMutateIdentity()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      }
    }

    final MatrixM4x4FT<A> r = MatrixM4x4FT.multiplyInPlace(m0, m1);
    Assert.assertSame(m0, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == m1.get(row, column));
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
        Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
      }
    }
  }

  @Override @Test public <A> void testMultiplyMutateSimple()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.set(m0, 0, 0, 1.0f);
    MatrixM4x4FT.set(m0, 0, 1, 2.0f);
    MatrixM4x4FT.set(m0, 0, 2, 3.0f);
    MatrixM4x4FT.set(m0, 0, 3, 4.0f);
    MatrixM4x4FT.set(m0, 1, 0, 5.0f);
    MatrixM4x4FT.set(m0, 1, 1, 6.0f);
    MatrixM4x4FT.set(m0, 1, 2, 7.0f);
    MatrixM4x4FT.set(m0, 1, 3, 8.0f);
    MatrixM4x4FT.set(m0, 2, 0, 9.0f);
    MatrixM4x4FT.set(m0, 2, 1, 10.0f);
    MatrixM4x4FT.set(m0, 2, 2, 11.0f);
    MatrixM4x4FT.set(m0, 2, 3, 12.0f);
    MatrixM4x4FT.set(m0, 3, 0, 13.0f);
    MatrixM4x4FT.set(m0, 3, 1, 14.0f);
    MatrixM4x4FT.set(m0, 3, 2, 15.0f);
    MatrixM4x4FT.set(m0, 3, 3, 16.0f);

    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>(m0);
    final MatrixM4x4FT<A> r = MatrixM4x4FT.multiplyInPlace(m0, m1);
    Assert.assertSame(r, m0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 0) == 90.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 1) == 100.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 2) == 110.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 3) == 120.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 0) == 202.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 1) == 228.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 2) == 254.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 3) == 280.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 0) == 314.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 1) == 356.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 2) == 398.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 3) == 440.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 0) == 426.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 1) == 484.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 2) == 542.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 3) == 600.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
  }

  @Override @Test public <A> void testMultiplySimple()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.set(m0, 0, 0, 1.0f);
    MatrixM4x4FT.set(m0, 0, 1, 2.0f);
    MatrixM4x4FT.set(m0, 0, 2, 3.0f);
    MatrixM4x4FT.set(m0, 0, 3, 4.0f);
    MatrixM4x4FT.set(m0, 1, 0, 5.0f);
    MatrixM4x4FT.set(m0, 1, 1, 6.0f);
    MatrixM4x4FT.set(m0, 1, 2, 7.0f);
    MatrixM4x4FT.set(m0, 1, 3, 8.0f);
    MatrixM4x4FT.set(m0, 2, 0, 9.0f);
    MatrixM4x4FT.set(m0, 2, 1, 10.0f);
    MatrixM4x4FT.set(m0, 2, 2, 11.0f);
    MatrixM4x4FT.set(m0, 2, 3, 12.0f);
    MatrixM4x4FT.set(m0, 3, 0, 13.0f);
    MatrixM4x4FT.set(m0, 3, 1, 14.0f);
    MatrixM4x4FT.set(m0, 3, 2, 15.0f);
    MatrixM4x4FT.set(m0, 3, 3, 16.0f);

    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>(m0);
    final MatrixM4x4FT<A> mr = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    final MatrixM4x4FT<A> r = MatrixM4x4FT.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 0) == 90.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 1) == 100.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 2) == 110.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 0, 3) == 120.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 0) == 202.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 1) == 228.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 2) == 254.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 1, 3) == 280.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 0) == 314.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 1) == 356.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 2) == 398.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 2, 3) == 440.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 0) == 426.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 1) == 484.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 2) == 542.0);
    Assert.assertTrue(MatrixM4x4FT.get(r, 3, 3) == 600.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test public <A> void testMultiplyVectorSimple()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.set(m0, 0, 0, 1.0f);
    MatrixM4x4FT.set(m0, 0, 1, 2.0f);
    MatrixM4x4FT.set(m0, 0, 2, 3.0f);
    MatrixM4x4FT.set(m0, 0, 3, 4.0f);
    MatrixM4x4FT.set(m0, 1, 0, 5.0f);
    MatrixM4x4FT.set(m0, 1, 1, 6.0f);
    MatrixM4x4FT.set(m0, 1, 2, 7.0f);
    MatrixM4x4FT.set(m0, 1, 3, 8.0f);
    MatrixM4x4FT.set(m0, 2, 0, 9.0f);
    MatrixM4x4FT.set(m0, 2, 1, 10.0f);
    MatrixM4x4FT.set(m0, 2, 2, 11.0f);
    MatrixM4x4FT.set(m0, 2, 3, 12.0f);
    MatrixM4x4FT.set(m0, 3, 0, 13.0f);
    MatrixM4x4FT.set(m0, 3, 1, 14.0f);
    MatrixM4x4FT.set(m0, 3, 2, 15.0f);
    MatrixM4x4FT.set(m0, 3, 3, 16.0f);

    final VectorI4F v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());

    final VectorM4F r = MatrixM4x4FT.multiplyVector4F(m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());

    Assert.assertTrue(out.x == 30.0);
    Assert.assertTrue(out.y == 70.0);
    Assert.assertTrue(out.z == 110.0);
    Assert.assertTrue(out.w == 150.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
  }

  @Override @SuppressWarnings({}) @Test public
    <A>
    void
    testMultiplyVectorSimpleContextEquivalent()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();

    MatrixM4x4FT.set(m0, 0, 0, 1.0f);
    MatrixM4x4FT.set(m0, 0, 1, 2.0f);
    MatrixM4x4FT.set(m0, 0, 2, 3.0f);
    MatrixM4x4FT.set(m0, 0, 3, 4.0f);
    MatrixM4x4FT.set(m0, 1, 0, 5.0f);
    MatrixM4x4FT.set(m0, 1, 1, 6.0f);
    MatrixM4x4FT.set(m0, 1, 2, 7.0f);
    MatrixM4x4FT.set(m0, 1, 3, 8.0f);
    MatrixM4x4FT.set(m0, 2, 0, 9.0f);
    MatrixM4x4FT.set(m0, 2, 1, 10.0f);
    MatrixM4x4FT.set(m0, 2, 2, 11.0f);
    MatrixM4x4FT.set(m0, 2, 3, 12.0f);
    MatrixM4x4FT.set(m0, 3, 0, 13.0f);
    MatrixM4x4FT.set(m0, 3, 1, 14.0f);
    MatrixM4x4FT.set(m0, 3, 2, 15.0f);
    MatrixM4x4FT.set(m0, 3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());

    final VectorI4F v = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F out = new VectorM4F();
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());

    final VectorM4F r =
      MatrixM4x4FT.multiplyVector4FWithContext(context, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertTrue(out.x == 30.0);
    Assert.assertTrue(out.y == 70.0);
    Assert.assertTrue(out.z == 110.0);
    Assert.assertTrue(out.w == 150.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
  }

  @Override @Test public <A> void testMultiplyZero()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mr = new MatrixM4x4FT<A>();

    MatrixM4x4FT.setZero(m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    final MatrixM4x4FT<A> r = MatrixM4x4FT.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(mr.get(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeColumn()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.get(0, -1);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeNegativeRow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.get(-1, 0);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowColumn()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.get(0, 4);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testOutOfRangeOverflowRow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.get(4, 0);
  }

  /**
   * All rotation matrices have a determinant of 1.0 and are orthogonal.
   */

  @Override @Test public <A> void testRotateDeterminantOrthogonal()
  {
    final AlmostEqualDouble.ContextRelative context =
      TestUtilities.getDoubleEqualityContext();
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mt = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mi = new MatrixM4x4FT<A>();
    final VectorM3F axis = new VectorM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      float angle = (float) (Math.random() * (2 * Math.PI));
      axis.x = (float) Math.random();
      axis.y = (float) Math.random();
      axis.z = (float) Math.random();

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
      VectorM3F.normalizeInPlace(axis);

      System.out.println("axis  : " + axis);
      System.out.println("angle : " + angle);

      MatrixM4x4FT.makeRotation(angle, axis, m);

      final double det = MatrixM4x4FT.determinant(m);
      System.out.println("det   : " + det);

      AlmostEqualDouble.almostEqual(context, det, 1.0);

      MatrixM4x4FT.invert(m, mi);
      MatrixM4x4FT.transpose(m, mt);

      for (int row = 0; row < 4; ++row) {
        for (int col = 0; col < 4; ++col) {
          final float mx = mi.get(row, col);
          final float my = mt.get(row, col);
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

  @Override @Test public <A> void testRotateVector0X()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4FT.makeRotation(0, MatrixM4x4FTTest.AXIS_X, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Y axis has no effect.
   */

  @Override @Test public <A> void testRotateVector0Y()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4FT.makeRotation(0, MatrixM4x4FTTest.AXIS_Y, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 0 degrees around the Z axis has no effect.
   */

  @Override @Test public <A> void testRotateVector0Z()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_exp = new VectorM4F(0, 0, -1, 1);

    MatrixM4x4FT.makeRotation(0, MatrixM4x4FTTest.AXIS_Z, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    Assert.assertTrue(VectorM4F.almostEqual(ec, v_exp, v_got));
  }

  /**
   * A rotation of 90 degrees around the X axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public <A> void testRotateVector90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(0, 6.1232339957367E-17f, 1, 1);

    MatrixM4x4FT.makeRotation(Math.toRadians(90), MatrixM4x4FTTest.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Y axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public <A> void testRotateVector90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(-1, 0, -6.1232339957367E-17f, 1);

    MatrixM4x4FT.makeRotation(Math.toRadians(90), MatrixM4x4FTTest.AXIS_Y, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of 90 degrees around the Z axis gives the correct
   * counter-clockwise rotation of the vector.
   */

  @Override @Test public <A> void testRotateVector90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);
    final VectorM4F v_exp = new VectorM4F(-1, 6.123233995736766E-17f, 0, 1);

    MatrixM4x4FT.makeRotation(Math.toRadians(90), MatrixM4x4FTTest.AXIS_Z, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the X axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public <A> void testRotateVectorMinus90X()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);

    /**
     * XXX: Strange Y value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of Z is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(0, 6.1232339957367E-17f, -1, 1);

    MatrixM4x4FT
      .makeRotation(Math.toRadians(-90), MatrixM4x4FTTest.AXIS_X, m);
    System.out.println(m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Y axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public <A> void testRotateVectorMinus90Y()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 0, -1, 1);

    /**
     * XXX: Strange Z value due to floating point imprecision, with no good
     * way to compare it to 0 with an epsilon. The value of X is the only
     * significant element, anyway.
     */

    final VectorM4F v_exp = new VectorM4F(1, 0, -6.1232339957367E-17f, 1);

    MatrixM4x4FT
      .makeRotation(Math.toRadians(-90), MatrixM4x4FTTest.AXIS_Y, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  /**
   * A rotation of -90 degrees around the Z axis gives the correct clockwise
   * rotation of the vector.
   */

  @Override @Test public <A> void testRotateVectorMinus90Z()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v_got = new VectorM4F();
    final VectorM4F v_in = new VectorM4F(0, 1, 0, 1);
    final VectorM4F v_exp = new VectorM4F(1, 6.123233995736766E-17f, 0, 1);

    MatrixM4x4FT
      .makeRotation(Math.toRadians(-90), MatrixM4x4FTTest.AXIS_Z, m);
    MatrixM4x4FT.multiplyVector4F(m, v_in, v_got);

    System.out.println("in  : " + v_in);
    System.out.println("exp : " + v_exp);
    System.out.println("got : " + v_got);
    System.out.println("--");

    eq = AlmostEqualFloat.almostEqual(context, v_exp.x, v_got.x);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.y, v_got.y);
    Assert.assertTrue(eq);
    eq = AlmostEqualFloat.almostEqual(context, v_exp.z, v_got.z);
    Assert.assertTrue(eq);
  }

  @Override @Test public <A> void testRotateX()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotate(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_X,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(out).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_X);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateXContextEquivalentInPlace()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();

    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();

      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_X,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(out).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixX(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_X);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      MatrixM4x4FTTest.isRotationMatrixX(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateXMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT
          .makeRotation(Math.toRadians(45), MatrixM4x4FTTest.AXIS_X);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixX(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateY()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotate(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Y,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateYContextEquivalentInPlace()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Y,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixY(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixY(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateYMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT
          .makeRotation(Math.toRadians(45), MatrixM4x4FTTest.AXIS_Y);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixY(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateZ()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotate(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Z,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlace(
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateZContextEquivalentInPlace()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Z,
          out);
      Assert.assertSame(r, out);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixZ(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT.rotateInPlaceWithContext(
          context,
          Math.toRadians(45),
          m,
          MatrixM4x4FTTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixZ(context_f, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRotateZMakeEquivalent()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    {
      final MatrixM4x4FT<A> r =
        MatrixM4x4FT
          .makeRotation(Math.toRadians(45), MatrixM4x4FTTest.AXIS_Z);
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

      System.out.println(r);

      MatrixM4x4FTTest.isRotationMatrixZ(context, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testRow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v = new VectorM4F();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    MatrixM4x4FT.row(m, 0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    MatrixM4x4FT.row(m, 1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    MatrixM4x4FT.row(m, 2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
    Assert.assertTrue(v.w == 0.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    MatrixM4x4FT.row(m, 3, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 1.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testRow4()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorM4F v = new VectorM4F();
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    m.getRow4F(0, v);
    Assert.assertTrue(v.x == 1.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    m.getRow4F(1, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 1.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    m.getRow4F(2, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 1.0);
    Assert.assertTrue(v.w == 0.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    m.getRow4F(3, v);
    Assert.assertTrue(v.x == 0.0);
    Assert.assertTrue(v.y == 0.0);
    Assert.assertTrue(v.z == 0.0);
    Assert.assertTrue(v.w == 1.0);
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRow4Overflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.getRow4F(4, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRow4Underflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    m.getRow4F(-1, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowOverflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.row(m, 4, new VectorM4F());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testRowUnderflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.row(m, -1, new VectorM4F());
  }

  @Override @Test public <A> void testScale()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> mr = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m0.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    final MatrixM4x4FT<A> mk = MatrixM4x4FT.scale(m0, 5.0f, mr);
    Assert.assertSame(mr, mk);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m0.get(row, column) == 3.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test public <A> void testScaleMutate()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        m.set(row, column, 3.0f);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    final MatrixM4x4FT<A> mr = MatrixM4x4FT.scaleInPlace(m, 5.0f);
    Assert.assertSame(mr, m);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.get(row, column) == 15.0);
        Assert.assertTrue(mr.get(row, column) == 15.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(mr).position());
  }

  @Override @Test public <A> void testScaleRow()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.scaleRow(m0, 0, 2.0f, m1);
    MatrixM4x4FT.scaleRow(m0, 1, 4.0f, m1);
    MatrixM4x4FT.scaleRow(m0, 2, 8.0f, m1);
    MatrixM4x4FT.scaleRow(m0, 3, 16.0f, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.scaleRowInPlace(m0, 0, 2.0f);
    MatrixM4x4FT.scaleRowInPlace(m0, 1, 4.0f);
    MatrixM4x4FT.scaleRowInPlace(m0, 2, 8.0f);
    MatrixM4x4FT.scaleRowInPlace(m0, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test public <A> void testScaleRowContextEquivalent()
  {
    final MatrixM4x4FT.Context<A> context = new MatrixM4x4FT.Context<A>();
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    m0.set(0, 0, 1.0f);
    m0.set(0, 1, 2.0f);
    m0.set(0, 2, 3.0f);
    m0.set(0, 3, 4.0f);

    m0.set(1, 0, 5.0f);
    m0.set(1, 1, 6.0f);
    m0.set(1, 2, 7.0f);
    m0.set(1, 3, 8.0f);

    m0.set(2, 0, 9.0f);
    m0.set(2, 1, 10.0f);
    m0.set(2, 2, 11.0f);
    m0.set(2, 3, 12.0f);

    m0.set(3, 0, 13.0f);
    m0.set(3, 1, 14.0f);
    m0.set(3, 2, 15.0f);
    m0.set(3, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.scaleRowWithContext(context, m0, 0, 2.0f, m1);
    MatrixM4x4FT.scaleRowWithContext(context, m0, 1, 4.0f, m1);
    MatrixM4x4FT.scaleRowWithContext(context, m0, 2, 8.0f, m1);
    MatrixM4x4FT.scaleRowWithContext(context, m0, 3, 16.0f, m1);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    Assert.assertTrue(m1.get(0, 0) == 2.0f);
    Assert.assertTrue(m1.get(0, 1) == 4.0f);
    Assert.assertTrue(m1.get(0, 2) == 6.0f);
    Assert.assertTrue(m1.get(0, 3) == 8.0f);

    Assert.assertTrue(m1.get(1, 0) == 20.0f);
    Assert.assertTrue(m1.get(1, 1) == 24.0f);
    Assert.assertTrue(m1.get(1, 2) == 28.0f);
    Assert.assertTrue(m1.get(1, 3) == 32.0f);

    Assert.assertTrue(m1.get(2, 0) == 72.0f);
    Assert.assertTrue(m1.get(2, 1) == 80.0f);
    Assert.assertTrue(m1.get(2, 2) == 88.0f);
    Assert.assertTrue(m1.get(2, 3) == 96.0f);

    Assert.assertTrue(m1.get(3, 0) == 208.0f);
    Assert.assertTrue(m1.get(3, 1) == 224.0f);
    Assert.assertTrue(m1.get(3, 2) == 240.0f);
    Assert.assertTrue(m1.get(3, 3) == 256.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    MatrixM4x4FT.scaleRowInPlaceWithContext(context, m0, 0, 2.0f);
    MatrixM4x4FT.scaleRowInPlaceWithContext(context, m0, 1, 4.0f);
    MatrixM4x4FT.scaleRowInPlaceWithContext(context, m0, 2, 8.0f);
    MatrixM4x4FT.scaleRowInPlaceWithContext(context, m0, 3, 16.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());

    Assert.assertTrue(m0.get(0, 0) == 2.0f);
    Assert.assertTrue(m0.get(0, 1) == 4.0f);
    Assert.assertTrue(m0.get(0, 2) == 6.0f);
    Assert.assertTrue(m0.get(0, 3) == 8.0f);

    Assert.assertTrue(m0.get(1, 0) == 20.0f);
    Assert.assertTrue(m0.get(1, 1) == 24.0f);
    Assert.assertTrue(m0.get(1, 2) == 28.0f);
    Assert.assertTrue(m0.get(1, 3) == 32.0f);

    Assert.assertTrue(m0.get(2, 0) == 72.0f);
    Assert.assertTrue(m0.get(2, 1) == 80.0f);
    Assert.assertTrue(m0.get(2, 2) == 88.0f);
    Assert.assertTrue(m0.get(2, 3) == 96.0f);

    Assert.assertTrue(m0.get(3, 0) == 208.0f);
    Assert.assertTrue(m0.get(3, 1) == 224.0f);
    Assert.assertTrue(m0.get(3, 2) == 240.0f);
    Assert.assertTrue(m0.get(3, 3) == 256.0f);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateOverflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.scaleRowInPlace(m, 4, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowMutateUnderflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.scaleRowInPlace(m, -1, 1.0f);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowOverflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
    MatrixM4x4FT.scaleRow(m, 4, 1.0f, r);
  }

  @Override @Test(expected = IndexOutOfBoundsException.class) public
    <A>
    void
    testScaleRowUnderflow()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
    MatrixM4x4FT.scaleRow(m, -1, 1.0f, r);
  }

  @Override @Test public <A> void testSetGetIdentity()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    Assert.assertTrue(m.set(0, 0, 3.0f).get(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).get(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).get(0, 2) == 7.0);
    Assert.assertTrue(m.set(0, 3, 11.0f).get(0, 3) == 11.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).get(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).get(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).get(1, 2) == 19.0);
    Assert.assertTrue(m.set(1, 3, 23.0f).get(1, 3) == 23.0);

    Assert.assertTrue(m.set(2, 0, 29.0f).get(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).get(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).get(2, 2) == 37.0);
    Assert.assertTrue(m.set(2, 3, 41.0f).get(2, 3) == 41.0);

    Assert.assertTrue(m.set(3, 0, 43.0f).get(3, 0) == 43.0);
    Assert.assertTrue(m.set(3, 1, 47.0f).get(3, 1) == 47.0);
    Assert.assertTrue(m.set(3, 2, 53.0f).get(3, 2) == 53.0);
    Assert.assertTrue(m.set(3, 3, 59.0f).get(3, 3) == 59.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testSetGetInterfaceIdentity()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    Assert.assertTrue(m.set(0, 0, 3.0f).getRowColumnF(0, 0) == 3.0);
    Assert.assertTrue(m.set(0, 1, 5.0f).getRowColumnF(0, 1) == 5.0);
    Assert.assertTrue(m.set(0, 2, 7.0f).getRowColumnF(0, 2) == 7.0);
    Assert.assertTrue(m.set(0, 3, 11.0f).getRowColumnF(0, 3) == 11.0);

    Assert.assertTrue(m.set(1, 0, 13.0f).getRowColumnF(1, 0) == 13.0);
    Assert.assertTrue(m.set(1, 1, 17.0f).getRowColumnF(1, 1) == 17.0);
    Assert.assertTrue(m.set(1, 2, 19.0f).getRowColumnF(1, 2) == 19.0);
    Assert.assertTrue(m.set(1, 3, 23.0f).getRowColumnF(1, 3) == 23.0);

    Assert.assertTrue(m.set(2, 0, 29.0f).getRowColumnF(2, 0) == 29.0);
    Assert.assertTrue(m.set(2, 1, 31.0f).getRowColumnF(2, 1) == 31.0);
    Assert.assertTrue(m.set(2, 2, 37.0f).getRowColumnF(2, 2) == 37.0);
    Assert.assertTrue(m.set(2, 3, 41.0f).getRowColumnF(2, 3) == 41.0);

    Assert.assertTrue(m.set(3, 0, 43.0f).getRowColumnF(3, 0) == 43.0);
    Assert.assertTrue(m.set(3, 1, 47.0f).getRowColumnF(3, 1) == 47.0);
    Assert.assertTrue(m.set(3, 2, 53.0f).getRowColumnF(3, 2) == 53.0);
    Assert.assertTrue(m.set(3, 3, 59.0f).getRowColumnF(3, 3) == 59.0);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }

  @Override @Test public <A> void testStorage()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

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
      final FloatBuffer b = MatrixM4x4FT.floatBuffer(m);

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

  @Override @Test public <A> void testString()
  {
    final MatrixM4x4FT<A> m0 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m1 = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> m2 = new MatrixM4x4FT<A>();
    m2.set(0, 0, 2.0f);

    Assert.assertEquals(m0.toString(), m1.toString());
    Assert.assertFalse(m0.toString().equals(m2.toString()));

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m0).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m1).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m2).position());
  }

  @Override @Test public <A> void testTrace()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final double t = MatrixM4x4FT.trace(m);
    Assert.assertTrue(4.0 == t);
  }

  @Override @Test public <A> void testTranslate3_4_Equivalent()
  {
    final MatrixM3x3F m3 = new MatrixM3x3F();
    final MatrixM4x4FT<A> m4 = new MatrixM4x4FT<A>();
    final VectorI3F v = new VectorI3F(3.0f, 7.0f, 0.0f);
    final VectorM3F v3i = new VectorM3F(1, 1, 1);
    final VectorM3F v3o = new VectorM3F();
    final VectorM4F w3i = new VectorM4F(1, 1, 1, 1);
    final VectorM4F w3o = new VectorM4F();

    MatrixM3x3F.makeTranslation2F(v, m3);
    MatrixM4x4FT.makeTranslation3F(v, m4);

    MatrixM3x3F.multiplyVector3F(m3, v3i, v3o);
    MatrixM4x4FT.multiplyVector4F(m4, w3i, w3o);

    Assert.assertTrue(v3o.x == w3o.x);
    Assert.assertTrue(v3o.y == w3o.y);
  }

  @Override @Test public <A> void testTranslateSimple2Integer()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple2IntegerAlt()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI2I v = new VectorI2I(1, 2);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple2Real()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
    final VectorI2F v = new VectorI2F(1.0f, 2.0f);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple2RealAlt()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI2F v = new VectorI2F(1.0f, 2.0f);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector2FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple3Integer()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple3IntegerAlt()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3IInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple3Real()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3F(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslateSimple3RealAlt()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3FInPlace(m, v);
      Assert.assertSame(m, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslationEquivalent3Integer()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> r = MatrixM4x4FT.translateByVector3I(m, v, out);
      Assert.assertSame(out, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }

    {
      final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> t = new MatrixM4x4FT<A>();

      MatrixM4x4FT.makeTranslation3I(v, t);
      MatrixM4x4FT.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslationEquivalent3Real()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> t = MatrixM4x4FT.makeTranslation3F(v);
      MatrixM4x4FT.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
    }
  }

  @Override @Test public <A> void testTranslationMakeEquivalent3Integer()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3I v = new VectorI3I(1, 2, 3);

    {
      final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> t = MatrixM4x4FT.makeTranslation3I(v);
      MatrixM4x4FT.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(t).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(t).position());
    }
  }

  @Override @Test public <A> void testTranslationMakeEquivalent3Real()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final VectorI3F v = new VectorI3F(1.0f, 2.0f, 3.0f);

    {
      final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();
      final MatrixM4x4FT<A> t = MatrixM4x4FT.makeTranslation3F(v);
      MatrixM4x4FT.multiply(m, t, r);

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(t).position());

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

      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
      Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(t).position());
    }
  }

  @Override @Test public <A> void testTranslationStorage()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> out = new MatrixM4x4FT<A>();

    MatrixM4x4FT.translateByVector3F(m, new VectorI3F(1.0f, 2.0f, 3.0f), out);

    {
      final FloatBuffer b = MatrixM4x4FT.floatBuffer(out);

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

  @Override @Test public <A> void testTranspose()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    final MatrixM4x4FT<A> r = new MatrixM4x4FT<A>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(0, 3, 3.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(1, 3, 7.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);
    m.set(2, 3, 11.0f);
    m.set(3, 0, 12.0f);
    m.set(3, 1, 13.0f);
    m.set(3, 2, 14.0f);
    m.set(3, 3, 15.0f);

    final MatrixM4x4FT<A> k = MatrixM4x4FT.transpose(m, r);
    Assert.assertSame(k, r);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
  }

  @Override @Test public <A> void testTransposeMutate()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();

    m.set(0, 0, 0.0f);
    m.set(0, 1, 1.0f);
    m.set(0, 2, 2.0f);
    m.set(0, 3, 3.0f);
    m.set(1, 0, 4.0f);
    m.set(1, 1, 5.0f);
    m.set(1, 2, 6.0f);
    m.set(1, 3, 7.0f);
    m.set(2, 0, 8.0f);
    m.set(2, 1, 9.0f);
    m.set(2, 2, 10.0f);
    m.set(2, 3, 11.0f);
    m.set(3, 0, 12.0f);
    m.set(3, 1, 13.0f);
    m.set(3, 2, 14.0f);
    m.set(3, 3, 15.0f);

    final MatrixM4x4FT<A> r = MatrixM4x4FT.transposeInPlace(m);
    Assert.assertSame(m, r);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());

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

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(r).position());
  }

  @Override @Test public <A> void testZero()
  {
    final MatrixM4x4FT<A> m = new MatrixM4x4FT<A>();
    MatrixM4x4FT.setZero(m);

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertTrue(m.get(row, column) == 0.0);
      }
    }

    Assert.assertEquals(0, MatrixM4x4FT.floatBuffer(m).position());
  }
}
