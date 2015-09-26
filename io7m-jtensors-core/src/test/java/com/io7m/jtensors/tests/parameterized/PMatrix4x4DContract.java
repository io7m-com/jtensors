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
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixM4x4D;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4DType;
import com.io7m.jtensors.parameterized.PVectorI4D;
import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrix4x4DContract<T0, T1, T2,
  T           extends PMatrix4x4DType<T0, T1>,
  TMULTRIGHT  extends PMatrix4x4DType<T0, T1>,
  TMULTLEFT   extends PMatrix4x4DType<T1, T2>,
  TMULTRESULT extends PMatrix4x4DType<T0, T2>,
  TINVERSE    extends PMatrix4x4DType<T1, T0>>
  extends PMatrixReadable4x4DContract<T0, T1, T>
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
  protected abstract T newMatrixFrom(PMatrixReadable4x4DType<T0, T1> m);

  protected abstract void checkDirectBufferInvariantsUntyped(
    final PMatrix4x4DType<?, ?> m);

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

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

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

    MatrixM4x4D.copy(m0, m1);

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

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

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newMatrix();
      Assert.assertTrue(m0.equals(m0));
      this.checkDirectBufferInvariantsGeneric(m0);
    }

    {
      final T m0 = this.newMatrix();
      Assert.assertFalse(m0.equals(null));
      this.checkDirectBufferInvariantsGeneric(m0);
    }

    {
      final T m0 = this.newMatrix();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
      this.checkDirectBufferInvariantsGeneric(m0);
    }

    {
      final T m0 = this.newMatrix();
      final T m1 = this.newMatrix();
      Assert.assertTrue(m0.equals(m1));
      this.checkDirectBufferInvariantsGeneric(m0);
    }
  }

  @Test public final void testEqualsNotEqualsCorrect()
  {
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final T m0 = this.newMatrix();
        final T m1 = this.newMatrix();
        this.checkDirectBufferInvariantsGeneric(m0);
        this.checkDirectBufferInvariantsGeneric(m1);
        m1.setRowColumnD(row, col, 256.0);
        this.checkDirectBufferInvariantsGeneric(m0);
        this.checkDirectBufferInvariantsGeneric(m1);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariantsGeneric(m0);
        this.checkDirectBufferInvariantsGeneric(m1);
      }
    }
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

    this.checkDirectBufferInvariantsGeneric(m0);

    final T m1 = this.newMatrixFrom(m0);

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

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

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);
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

    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testInvertIdentity()
  {
    final PMatrixM4x4D.ContextPM4D c = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final boolean r = PMatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertIdentityContextEquivalent()
  {
    final PMatrixM4x4D.ContextPM4D context = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final boolean r = PMatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(1.0, m1.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(1.0, m1.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple()
  {
    final PMatrixM4x4D.ContextPM4D c = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

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
      final boolean r = PMatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(0.5, m1.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple2()
  {
    final PMatrixM4x4D.ContextPM4D c = new PMatrixM4x4D.ContextPM4D();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();
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
      final boolean r = PMatrixM4x4D.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      System.out.println("m0 : ");
      System.out.println(m0);
      System.out.println("m1 : ");
      System.out.println(m1);
      System.out.println("rm : ");
      System.out.println(m1);

      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final PMatrixM4x4D.ContextPM4D context = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();
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
      final boolean r = PMatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq =
        AlmostEqualDouble.almostEqual(context_d, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, m1.getRowColumnD(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimpleContextEquivalent()
  {
    final PMatrixM4x4D.ContextPM4D context = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

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
      final boolean r = PMatrixM4x4D.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(0.5, m1.getRowColumnD(0, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(0, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(1, 0), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(1, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(1, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(2, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 1), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(2, 2), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(2, 3), 0.0);

      Assert.assertEquals(0.0, m1.getRowColumnD(3, 0), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 1), 0.0);
      Assert.assertEquals(0.0, m1.getRowColumnD(3, 2), 0.0);
      Assert.assertEquals(0.5, m1.getRowColumnD(3, 3), 0.0);

      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertZero()
  {
    final PMatrixM4x4D.ContextPM4D c = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM4x4D.setZero(m0);

    {
      final boolean r = PMatrixM4x4D.invert(c, m0, m1);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testInvertZeroContextEquivalent()
  {
    final PMatrixM4x4D.ContextPM4D context = new PMatrixM4x4D.ContextPM4D();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM4x4D.setZero(m0);

    {
      final boolean r = PMatrixM4x4D.invert(context, m0, m1);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariantsGeneric(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testMultiplyIdentity()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();
    final TMULTRESULT r = PMatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m0.getRowColumnD(row, column), 0.0);
        Assert.assertEquals(
          mr.getRowColumnD(row, column), m1.getRowColumnD(row, column), 0.0);
        this.checkDirectBufferInvariantsUntyped(m0);
        this.checkDirectBufferInvariantsUntyped(m1);
        this.checkDirectBufferInvariantsUntyped(mr);
      }
    }
  }

  @Test public final void testMultiplySimple()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();

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

    final TMULTRIGHT m1 = this.newMatrixMultRight();
    MatrixM4x4D.copy(m0, m1);
    final TMULTRESULT mr = this.newMatrixMultResult();

    final TMULTRESULT r = PMatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);

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

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final PMatrixM4x4D.ContextPM4D c = new PMatrixM4x4D.ContextPM4D();
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

    final PVectorI4D<T0> v = new PVectorI4D<T0>(1.0, 2.0, 3.0, 4.0);
    final PVectorM4D<T1> out = new PVectorM4D<T1>();
    final PVectorM4D<T1> r = PMatrixM4x4D.multiplyVector4D(c, m0, v, out);
    Assert.assertSame(out, r);

    this.checkDirectBufferInvariantsGeneric(m0);

    Assert.assertEquals(30.0, out.getXD(), 0.0);
    Assert.assertEquals(70.0, out.getYD(), 0.0);
    Assert.assertEquals(110.0, out.getZD(), 0.0);
    Assert.assertEquals(150.0, out.getWD(), 0.0);
  }

  @Test public final void testMultiplyZero()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();

    MatrixM4x4D.setZero(m1);

    final TMULTRESULT r = PMatrixM4x4D.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(0.0, mr.getRowColumnD(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);
  }

  @Test public final void testRow4()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(1, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(1.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(2, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(1.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(3, v);
    Assert.assertEquals(0.0, v.getXD(), 0.0);
    Assert.assertEquals(0.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(1.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSetGet4()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRowWith4D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith4D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith4D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith4D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(4.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(40.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(400.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(4000.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSetGet4Static()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D();
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow4D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(4.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(40.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(400.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow4D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(4000.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet4Get3()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet4Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet4Get2()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet4Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRow0With4D(new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRow1With4D(new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRow2With4D(new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRow3With4D(new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(00.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
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

  @Test public final void testSetGetIdentity()
  {
    final T m = this.newMatrix();

    this.checkDirectBufferInvariantsGeneric(m);

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

    this.checkDirectBufferInvariantsGeneric(m);
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

    this.checkDirectBufferInvariantsGeneric(m);

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

    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRowWith3D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith3D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith3D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith3D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet3Get3Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRowWith3D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith3D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith3D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith3D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow3DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(3.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(30.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(300.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow3DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(3000.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet2Get2()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRowWith2D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith2D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith2D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith2D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2D(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2D(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testRowSet2Get2Unsafe()
  {
    final T m = this.newMatrix();
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.setRowWith2D(0, new VectorI4D(1.0, 2.0, 3.0, 4.0));
    m.setRowWith2D(1, new VectorI4D(10.0, 20.0, 30.0, 40.0));
    m.setRowWith2D(2, new VectorI4D(100.0, 200.0, 300.0, 400.0));
    m.setRowWith2D(3, new VectorI4D(1000.0, 2000.0, 3000.0, 4000.0));

    m.getRow2DUnsafe(0, v);
    Assert.assertEquals(1.0, v.getXD(), 0.0);
    Assert.assertEquals(2.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(1, v);
    Assert.assertEquals(10.0, v.getXD(), 0.0);
    Assert.assertEquals(20.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(2, v);
    Assert.assertEquals(100.0, v.getXD(), 0.0);
    Assert.assertEquals(200.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);

    m.getRow2DUnsafe(3, v);
    Assert.assertEquals(1000.0, v.getXD(), 0.0);
    Assert.assertEquals(2000.0, v.getYD(), 0.0);
    Assert.assertEquals(0.0, v.getZD(), 0.0);
    Assert.assertEquals(0.0, v.getWD(), 0.0);
    this.checkDirectBufferInvariantsGeneric(m);
  }

  @Test public final void testCopyTyped()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

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

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

    PMatrixM4x4D.copy(m0, m1);

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);

    Assert.assertEquals(1.0, m1.getR0C0D(), 0.0);
    Assert.assertEquals(2.0, m1.getR0C1D(), 0.0);
    Assert.assertEquals(3.0, m1.getR0C2D(), 0.0);
    Assert.assertEquals(4.0, m1.getR0C3D(), 0.0);

    Assert.assertEquals(5.0, m1.getR1C0D(), 0.0);
    Assert.assertEquals(6.0, m1.getR1C1D(), 0.0);
    Assert.assertEquals(7.0, m1.getR1C2D(), 0.0);
    Assert.assertEquals(8.0, m1.getR1C3D(), 0.0);

    Assert.assertEquals(9.0, m1.getR2C0D(), 0.0);
    Assert.assertEquals(10.0, m1.getR2C1D(), 0.0);
    Assert.assertEquals(11.0, m1.getR2C2D(), 0.0);
    Assert.assertEquals(12.0, m1.getR2C3D(), 0.0);

    Assert.assertEquals(13.0, m1.getR3C0D(), 0.0);
    Assert.assertEquals(14.0, m1.getR3C1D(), 0.0);
    Assert.assertEquals(15.0, m1.getR3C2D(), 0.0);
    Assert.assertEquals(16.0, m1.getR3C3D(), 0.0);

    this.checkDirectBufferInvariantsGeneric(m0);
    this.checkDirectBufferInvariantsGeneric(m1);
  }
}
