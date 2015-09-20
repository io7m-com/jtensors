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
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix3x3FType;
import com.io7m.jtensors.parameterized.PMatrixM3x3F;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3FType;
import com.io7m.jtensors.parameterized.PVectorI3F;
import com.io7m.jtensors.parameterized.PVectorM3F;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrix3x3FContract<T0, T1, T2,
  T           extends PMatrix3x3FType<T0, T1>,
  TMULTRIGHT  extends PMatrix3x3FType<T0, T1>,
  TMULTLEFT   extends PMatrix3x3FType<T1, T2>,
  TMULTRESULT extends PMatrix3x3FType<T0, T2>,
  TINVERSE    extends PMatrix3x3FType<T1, T0>>
  extends PMatrixReadable3x3FContract<T0, T1, T>
{
  //@formatter:on

  private static final VectorReadable3FType AXIS_X = new VectorI3F(
    1.0f, 0.0f, 0.0f);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(
    0.0f, 1.0f, 0.0f);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(
    0.0f, 0.0f, 1.0f);

  @Override protected abstract T newMatrix();

  protected abstract TMULTLEFT newMatrixMultLeft();

  protected abstract TMULTRIGHT newMatrixMultRight();

  protected abstract TMULTRESULT newMatrixMultResult();

  protected abstract TINVERSE newMatrixInverse();

  @Override
  protected abstract T newMatrixFrom(PMatrixReadable3x3FType<T0, T1> m);

  private void isRotationMatrixX(
    final AlmostEqualDouble.ContextRelative context,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(
      context, 1.0, (double) r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, (double) r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixY(
    final AlmostEqualDouble.ContextRelative context,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, 0.0, (double) r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 1.0,
                                       (double) r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0,
                                       (double) r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context, -0.707106781187, (double) r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context, 0.0,
                                       (double) r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context, 0.707106781187, (double) r.getRowColumnF(2, 2));
    Assert.assertTrue(eq);
  }

  private void isRotationMatrixZ(
    final AlmostEqualDouble.ContextRelative context_d,
    final T r)
  {
    boolean eq;
    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, (double) r.getRowColumnF(0, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context_d, -0.707106781187, (double) r.getRowColumnF(0, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0,
                                       (double) r.getRowColumnF(0, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, (double) r.getRowColumnF(1, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(
      context_d, 0.707106781187, (double) r.getRowColumnF(1, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0,
                                       (double) r.getRowColumnF(1, 2));
    Assert.assertTrue(eq);

    eq = AlmostEqualDouble.almostEqual(context_d, 0.0,
                                       (double) r.getRowColumnF(2, 0));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 0.0,
                                       (double) r.getRowColumnF(2, 1));
    Assert.assertTrue(eq);
    eq = AlmostEqualDouble.almostEqual(context_d, 1.0,
                                       (double) r.getRowColumnF(2, 2));
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
        m1.setRowColumnF(row, col, 256.0f);
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

  @Test public final void testInvertIdentity()
  {
    final PMatrixM3x3F.ContextPM3F s = new PMatrixM3x3F.ContextPM3F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final boolean r = PMatrixM3x3F.invert(s, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);

      Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);
    }

    {
      final boolean r = PMatrixM3x3F.invertInPlace(s, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);

      Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);
    }
  }

  @Test public final void testInvertSimpleNF()
  {
    final PMatrixM3x3F.ContextPM3F c = new PMatrixM3x3F.ContextPM3F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

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
      final boolean r = PMatrixM3x3F.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);

      Assert.assertEquals(0.5, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);
    }

    {
      final boolean r = PMatrixM3x3F.invertInPlace(c, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);

      Assert.assertEquals(2.0, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(2.0, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(2.0, (double) m1.getRowColumnF(2, 2), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariants(m1);
      this.checkDirectBufferInvariants(m1);
    }
  }

  @Test public final void testInvertZeroNF()
  {
    final PMatrixM3x3F.ContextPM3F c = new PMatrixM3x3F.ContextPM3F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM3x3F.setZero(m0);

    {
      final boolean r = PMatrixM3x3F.invert(c, m0, m1);
      Assert.assertFalse(r);
    }

    {
      final boolean r = PMatrixM3x3F.invertInPlace(c, m0);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testMultiplyIdentity()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();
    final TMULTRESULT r = PMatrixM3x3F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        Assert.assertEquals(
          (double) mr.getRowColumnF(row, column), (double) m0.getRowColumnF(row, column),
          0.0);
        Assert.assertEquals(
          (double) mr.getRowColumnF(row, column), (double) m1.getRowColumnF(row, column),
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

    m0.setR0C0F(1.0f);
    m0.setR0C1F(2.0f);
    m0.setR0C2F(3.0f);

    m0.setR1C0F(4.0f);
    m0.setR1C1F(5.0f);
    m0.setR1C2F(6.0f);

    m0.setR2C0F(7.0f);
    m0.setR2C1F(8.0f);
    m0.setR2C2F(9.0f);

    final TMULTRIGHT m1 = this.newMatrixMultRight();
    MatrixM3x3F.copy(m0, m1);
    final TMULTRESULT mr = this.newMatrixMultResult();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    final TMULTRESULT r = PMatrixM3x3F.multiply(m0, m1, mr);
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
    final PMatrixM3x3F.ContextPM3F c = new PMatrixM3x3F.ContextPM3F();
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

    final PVectorI3F<T0> v = new PVectorI3F<T0>(1.0f, 2.0f, 3.0f);
    final PVectorM3F<T1> out = new PVectorM3F<T1>();

    final PVectorM3F<T1> r = PMatrixM3x3F.multiplyVector3F(c, m0, v, out);
    Assert.assertSame(out, r);

    Assert.assertEquals(14.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(32.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(50.0, (double) out.getZF(), 0.0);

    this.checkDirectBufferInvariants(m0);
  }

  @Test public final void testMultiplyZero()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();

    MatrixM3x3F.setZero(m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
    this.checkDirectBufferInvariants(mr);

    final TMULTRESULT r = PMatrixM3x3F.multiply(m0, m1, mr);
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
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2F(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
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
    Assert.assertEquals(1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(1, v);
    Assert.assertEquals(10.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(20.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(2, v);
    Assert.assertEquals(100.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(200.0, (double) v.getYF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }
}
