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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jfunctional.OptionType;
import com.io7m.jfunctional.Some;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.parameterized.PMatrix3x3DType;
import com.io7m.jtensors.parameterized.PMatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import com.io7m.jtensors.parameterized.PVectorI3D;
import com.io7m.jtensors.parameterized.PVectorM3D;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrix3x3DContract<T0, T1, T2,
  T           extends PMatrix3x3DType<T0, T1>,
  TMULTRIGHT  extends PMatrix3x3DType<T0, T1>,
  TMULTLEFT   extends PMatrix3x3DType<T1, T2>,
  TMULTRESULT extends PMatrix3x3DType<T0, T2>,
  TINVERSE    extends PMatrix3x3DType<T1, T0>>
  extends PMatrixReadable3x3DContract<T0, T1, T>
{
  //@formatter:on

  private static final VectorReadable3DType AXIS_X = new VectorI3D(
    1.0, 0.0, 0.0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(
    0.0, 1.0, 0.0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(
    0.0, 0.0, 1.0);

  @Override protected abstract T newMatrix();

  protected abstract TMULTLEFT newMatrixMultLeft();

  protected abstract TMULTRIGHT newMatrixMultRight();

  protected abstract TMULTRESULT newMatrixMultResult();

  protected abstract TINVERSE newMatrixInverse();

  @Override
  protected abstract T newMatrixFrom(PMatrixReadable3x3DType<T0, T1> m);

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
    Assert.assertTrue(m0.equals(m1));
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

  @Test public final void testInvertIdentity()
  {
    final PMatrixM3x3D.ContextPM3D s = new PMatrixM3x3D.ContextPM3D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final OptionType<TINVERSE> r = PMatrixM3x3D.invert(s, m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<TINVERSE> some = (Some<TINVERSE>) r;
      final TINVERSE rm = some.get();

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
      final OptionType<T> r = PMatrixM3x3D.invertInPlace(s, m1);
      Assert.assertTrue(r.isSome());
      final Some<T> some = (Some<T>) r;
      final T rm = some.get();

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
    final PMatrixM3x3D.ContextPM3D c = new PMatrixM3x3D.ContextPM3D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

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
      final OptionType<TINVERSE> r = PMatrixM3x3D.invert(c, m0, m1);
      Assert.assertTrue(r.isSome());
      final Some<TINVERSE> some = (Some<TINVERSE>) r;
      final TINVERSE rm = some.get();

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
      final OptionType<T> r = PMatrixM3x3D.invertInPlace(c, m1);
      Assert.assertTrue(r.isSome());
      final Some<T> some = (Some<T>) r;
      final T rm = some.get();

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
    final PMatrixM3x3D.ContextPM3D c = new PMatrixM3x3D.ContextPM3D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM3x3D.setZero(m0);

    {
      final OptionType<TINVERSE> r = PMatrixM3x3D.invert(c, m0, m1);
      Assert.assertTrue(r.isNone());
    }

    {
      final OptionType<TINVERSE> r = PMatrixM3x3D.invertInPlace(c, m0);
      Assert.assertTrue(r.isNone());
    }
  }

  @Test public final void testMultiplyIdentity()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();
    final TMULTRESULT r = PMatrixM3x3D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(
          mr.getRowColumnD(row, column),
          m0.getRowColumnD(row, column),
          0.0);
        Assert.assertEquals(
          mr.getRowColumnD(row, column),
          m1.getRowColumnD(row, column),
          0.0);
      }
    }

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);
  }

  @Test public final void testMultiplySimple()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();

    m0.setR0C0D(1.0);
    m0.setR0C1D(2.0);
    m0.setR0C2D(3.0);

    m0.setR1C0D(4.0);
    m0.setR1C1D(5.0);
    m0.setR1C2D(6.0);

    m0.setR2C0D(7.0);
    m0.setR2C1D(8.0);
    m0.setR2C2D(9.0);

    final TMULTRIGHT m1 = this.newMatrixMultRight();
    MatrixM3x3D.copy(m0, m1);
    final TMULTRESULT mr = this.newMatrixMultResult();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final TMULTRESULT r = PMatrixM3x3D.multiply(m0, m1, mr);
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
    final PMatrixM3x3D.ContextPM3D c = new PMatrixM3x3D.ContextPM3D();
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

    final PVectorI3D<T0> v = new PVectorI3D<T0>(1.0, 2.0, 3.0);
    final PVectorM3D<T1> out = new PVectorM3D<T1>();

    final PVectorM3D<T1> r = PMatrixM3x3D.multiplyVector3D(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(14.0, out.getXD(), 0.0);
    Assert.assertEquals(32.0, out.getYD(), 0.0);
    Assert.assertEquals(50.0, out.getZD(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testMultiplyZero()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();

    MatrixM3x3D.setZero(m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final TMULTRESULT r = PMatrixM3x3D.multiply(m0, m1, mr);
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
