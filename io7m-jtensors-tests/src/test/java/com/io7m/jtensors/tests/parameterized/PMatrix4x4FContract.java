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
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4FType;
import com.io7m.jtensors.parameterized.PVectorI4F;
import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.parameterized.PVectorReadable4FType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrix4x4FContract<T0, T1, T2,
  T           extends PMatrix4x4FType<T0, T1>,
  TMULTRIGHT  extends PMatrix4x4FType<T0, T1>,
  TMULTLEFT   extends PMatrix4x4FType<T1, T2>,
  TMULTRESULT extends PMatrix4x4FType<T0, T2>,
  TINVERSE    extends PMatrix4x4FType<T1, T0>>
  extends PMatrixReadable4x4FContract<T0, T1, T>
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
  protected abstract T newMatrixFrom(PMatrixReadable4x4FType<T0, T1> m);

  protected abstract void checkDirectBufferInvariantsUntyped(
    final PMatrix4x4FType<?, ?> m);

  @Test public final void testCopy()
  {
    final T m0 = this.newMatrix();
    final T m1 = this.newMatrix();

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    m0.setRowColumnF(0, 0, 1.0f);
    m0.setRowColumnF(0, 1, 2.0f);
    m0.setRowColumnF(0, 2, 3.0f);
    m0.setRowColumnF(0, 3, 4.0f);

    m0.setRowColumnF(1, 0, 5.0f);
    m0.setRowColumnF(1, 1, 6.0f);
    m0.setRowColumnF(1, 2, 7.0f);
    m0.setRowColumnF(1, 3, 8.0f);

    m0.setRowColumnF(2, 0, 9.0f);
    m0.setRowColumnF(2, 1, 10.0f);
    m0.setRowColumnF(2, 2, 11.0f);
    m0.setRowColumnF(2, 3, 12.0f);

    m0.setRowColumnF(3, 0, 13.0f);
    m0.setRowColumnF(3, 1, 14.0f);
    m0.setRowColumnF(3, 2, 15.0f);
    m0.setRowColumnF(3, 3, 16.0f);

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
      Assert.assertTrue(m0.equals(m1));
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
        m1.setRowColumnF(row, col, 256.0f);
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
        Assert.assertFalse(m0.equals(m1));
        this.checkDirectBufferInvariants(m0);
        this.checkDirectBufferInvariants(m1);
      }
    }
  }

  @Test public final void testInitializationFrom()
  {
    final T m0 = this.newMatrix();

    m0.setRowColumnF(0, 0, 3.0f);
    m0.setRowColumnF(0, 1, 5.0f);
    m0.setRowColumnF(0, 2, 7.0f);
    m0.setRowColumnF(0, 3, 11.0f);

    m0.setRowColumnF(1, 0, 13.0f);
    m0.setRowColumnF(1, 1, 17.0f);
    m0.setRowColumnF(1, 2, 19.0f);
    m0.setRowColumnF(1, 3, 23.0f);

    m0.setRowColumnF(2, 0, 29.0f);
    m0.setRowColumnF(2, 1, 31.0f);
    m0.setRowColumnF(2, 2, 37.0f);
    m0.setRowColumnF(2, 3, 41.0f);

    m0.setRowColumnF(3, 0, 43.0f);
    m0.setRowColumnF(3, 1, 47.0f);
    m0.setRowColumnF(3, 2, 53.0f);
    m0.setRowColumnF(3, 3, 59.0f);

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
    final PMatrixM4x4F.ContextPM4F c = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final boolean r = PMatrixM4x4F.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertIdentityContextEquivalent()
  {
    final PMatrixM4x4F.ContextPM4F context = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    {
      final boolean r = PMatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(1.0, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(1.0, (double) m1.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple()
  {
    final PMatrixM4x4F.ContextPM4F c = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    m0.setRowColumnF(0, 0, 2.0f);
    m0.setRowColumnF(0, 1, 0.0f);
    m0.setRowColumnF(0, 2, 0.0f);
    m0.setRowColumnF(0, 3, 0.0f);

    m0.setRowColumnF(1, 0, 0.0f);
    m0.setRowColumnF(1, 1, 2.0f);
    m0.setRowColumnF(1, 2, 0.0f);
    m0.setRowColumnF(1, 3, 0.0f);

    m0.setRowColumnF(2, 0, 0.0f);
    m0.setRowColumnF(2, 1, 0.0f);
    m0.setRowColumnF(2, 2, 2.0f);
    m0.setRowColumnF(2, 3, 0.0f);

    m0.setRowColumnF(3, 0, 0.0f);
    m0.setRowColumnF(3, 1, 0.0f);
    m0.setRowColumnF(3, 2, 0.0f);
    m0.setRowColumnF(3, 3, 2.0f);

    {
      final boolean r = PMatrixM4x4F.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(0.5, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple2()
  {
    final PMatrixM4x4F.ContextPM4F c = new PMatrixM4x4F.ContextPM4F();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();
    boolean eq = false;

    m0.setRowColumnF(0, 0, 1.0f);
    m0.setRowColumnF(0, 1, 0.0f);
    m0.setRowColumnF(0, 2, 5.0f);
    m0.setRowColumnF(0, 3, 0.0f);

    m0.setRowColumnF(1, 0, 0.0f);
    m0.setRowColumnF(1, 1, 2.0f);
    m0.setRowColumnF(1, 2, 0.0f);
    m0.setRowColumnF(1, 3, 11.0f);

    m0.setRowColumnF(2, 0, 7.0f);
    m0.setRowColumnF(2, 1, 0.0f);
    m0.setRowColumnF(2, 2, 3.0f);
    m0.setRowColumnF(2, 3, 0.0f);

    m0.setRowColumnF(3, 0, 0.0f);
    m0.setRowColumnF(3, 1, 13.0f);
    m0.setRowColumnF(3, 2, 0.0f);
    m0.setRowColumnF(3, 3, 4.0f);

    {
      final boolean r = PMatrixM4x4F.invert(c, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimple2ContextEquivalent()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext3dp();

    final PMatrixM4x4F.ContextPM4F context = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();
    boolean eq = false;

    m0.setRowColumnF(0, 0, 1.0f);
    m0.setRowColumnF(0, 1, 0.0f);
    m0.setRowColumnF(0, 2, 5.0f);
    m0.setRowColumnF(0, 3, 0.0f);

    m0.setRowColumnF(1, 0, 0.0f);
    m0.setRowColumnF(1, 1, 2.0f);
    m0.setRowColumnF(1, 2, 0.0f);
    m0.setRowColumnF(1, 3, 11.0f);

    m0.setRowColumnF(2, 0, 7.0f);
    m0.setRowColumnF(2, 1, 0.0f);
    m0.setRowColumnF(2, 2, 3.0f);
    m0.setRowColumnF(2, 3, 0.0f);

    m0.setRowColumnF(3, 0, 0.0f);
    m0.setRowColumnF(3, 1, 13.0f);
    m0.setRowColumnF(3, 2, 0.0f);
    m0.setRowColumnF(3, 3, 4.0f);

    {
      final boolean r = PMatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 0), -0.09375);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 2), 0.15625);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(0, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 1), -0.0296);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(1, 3), 0.0814);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 0), 0.21875);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 2), -0.03125);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(2, 3), 0.0);
      Assert.assertTrue(eq);

      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 1), 0.096);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(
        context_d, (double) m1.getRowColumnF(3, 3), -0.01481);
      Assert.assertTrue(eq);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertSimpleContextEquivalent()
  {
    final PMatrixM4x4F.ContextPM4F context = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    m0.setRowColumnF(0, 0, 2.0f);
    m0.setRowColumnF(0, 1, 0.0f);
    m0.setRowColumnF(0, 2, 0.0f);
    m0.setRowColumnF(0, 3, 0.0f);

    m0.setRowColumnF(1, 0, 0.0f);
    m0.setRowColumnF(1, 1, 2.0f);
    m0.setRowColumnF(1, 2, 0.0f);
    m0.setRowColumnF(1, 3, 0.0f);

    m0.setRowColumnF(2, 0, 0.0f);
    m0.setRowColumnF(2, 1, 0.0f);
    m0.setRowColumnF(2, 2, 2.0f);
    m0.setRowColumnF(2, 3, 0.0f);

    m0.setRowColumnF(3, 0, 0.0f);
    m0.setRowColumnF(3, 1, 0.0f);
    m0.setRowColumnF(3, 2, 0.0f);
    m0.setRowColumnF(3, 3, 2.0f);

    {
      final boolean r = PMatrixM4x4F.invert(context, m0, m1);
      Assert.assertTrue(r);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);

      Assert.assertEquals(0.5, (double) m1.getRowColumnF(0, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(0, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 0), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(1, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(1, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 1), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(2, 2), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(2, 3), 0.0);

      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 0), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 1), 0.0);
      Assert.assertEquals(0.0, (double) m1.getRowColumnF(3, 2), 0.0);
      Assert.assertEquals(0.5, (double) m1.getRowColumnF(3, 3), 0.0);

      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testInvertZero()
  {
    final PMatrixM4x4F.ContextPM4F c = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM4x4F.setZero(m0);

    {
      final boolean r = PMatrixM4x4F.invert(c, m0, m1);
      Assert.assertFalse(r);
    }
  }

  @Test public final void testInvertZeroContextEquivalent()
  {
    final PMatrixM4x4F.ContextPM4F context = new PMatrixM4x4F.ContextPM4F();
    final T m0 = this.newMatrix();
    final TINVERSE m1 = this.newMatrixInverse();

    MatrixM4x4F.setZero(m0);

    {
      final boolean r = PMatrixM4x4F.invert(context, m0, m1);
      Assert.assertFalse(r);
      this.checkDirectBufferInvariants(m0);
      this.checkDirectBufferInvariantsUntyped(m1);
    }
  }

  @Test public final void testMultiplyIdentity()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();
    final TMULTRESULT r = PMatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(
          (double) mr.getRowColumnF(row, column),
          (double) m0.getRowColumnF(row, column),
          0.0);
        Assert.assertEquals(
          (double) mr.getRowColumnF(row, column),
          (double) m1.getRowColumnF(row, column),
          0.0);
        this.checkDirectBufferInvariantsUntyped(m0);
        this.checkDirectBufferInvariantsUntyped(m1);
        this.checkDirectBufferInvariantsUntyped(mr);
      }
    }
  }

  @Test public final void testMultiplySimple()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();

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

    final TMULTRIGHT m1 = this.newMatrixMultRight();
    MatrixM4x4F.copy(m0, m1);
    final TMULTRESULT mr = this.newMatrixMultResult();

    final TMULTRESULT r = PMatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(r, mr);

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);

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

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);
  }

  @Test public final void testMultiplyVectorSimple()
  {
    final PMatrixM4x4F.ContextPM4F c = new PMatrixM4x4F.ContextPM4F();
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

    final PVectorReadable4FType<T0> v = new PVectorI4F<T0>(
      1.0f, 2.0f, 3.0f, 4.0f);
    final PVectorM4F<T1> out = new PVectorM4F<T1>();
    final PVectorM4F<T1> r = PMatrixM4x4F.multiplyVector4F(c, m0, v, out);
    Assert.assertSame(out, r);

    this.checkDirectBufferInvariants(m0);

    Assert.assertEquals(30.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(70.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(110.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(150.0, (double) out.getWF(), 0.0);
  }

  @Test public final void testMultiplyZero()
  {
    final TMULTLEFT m0 = this.newMatrixMultLeft();
    final TMULTRIGHT m1 = this.newMatrixMultRight();
    final TMULTRESULT mr = this.newMatrixMultResult();

    MatrixM4x4F.setZero(m1);

    final TMULTRESULT r = PMatrixM4x4F.multiply(m0, m1, mr);
    Assert.assertSame(mr, r);

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        Assert.assertEquals(0.0, (double) mr.getRowColumnF(row, column), 0.0);
      }
    }

    this.checkDirectBufferInvariantsUntyped(m0);
    this.checkDirectBufferInvariantsUntyped(m1);
    this.checkDirectBufferInvariantsUntyped(mr);
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

  @Test public final void testRowSetGet4()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F();
    this.checkDirectBufferInvariants(m);

    m.setRowWith4F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith4F(
      1, new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith4F(
      2, new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith4F(
      3, new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    m.setRow1With4F(
      new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(
      new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(
      new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(
      new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(
      new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(
      new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(
      new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(
      new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(
      new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(
      new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(
      new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(
      new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRow0With4F(new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRow1With4F(
      new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRow2With4F(
      new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRow3With4F(
      new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    Assert.assertEquals(00.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);

    m.getRow2FUnsafe(3, v);
    Assert.assertEquals(1000.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(2000.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v.getWF(), 0.0);
    this.checkDirectBufferInvariants(m);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow4Overflow()
  {
    final T m = this.newMatrix();
    m.getRow4F(4, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow4Underflow()
  {
    final T m = this.newMatrix();
    m.getRow4F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2Overflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(4, new VectorM3F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3Overflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow3Underflow()
  {
    final T m = this.newMatrix();
    m.getRow3F(-1, new VectorM4F());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testRow2Underflow()
  {
    final T m = this.newMatrix();
    m.getRow2F(-1, new VectorM3F());
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

  @Test public final void testRowSet3Get3()
  {
    final T m = this.newMatrix();
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith3F(
      1, new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith3F(
      2, new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith3F(
      3, new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith3F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith3F(
      1, new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith3F(
      2, new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith3F(
      3, new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith2F(
      1, new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith2F(
      2, new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith2F(
      3, new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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
    final VectorM4F v = new VectorM4F(
      0.0f, 0.0f, 0.0f, 0.0f);
    this.checkDirectBufferInvariants(m);

    m.setRowWith2F(0, new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f));
    m.setRowWith2F(
      1, new VectorI4F(
        10.0f, 20.0f, 30.0f, 40.0f));
    m.setRowWith2F(
      2, new VectorI4F(
        100.0f, 200.0f, 300.0f, 400.0f));
    m.setRowWith2F(
      3, new VectorI4F(
        1000.0f, 2000.0f, 3000.0f, 4000.0f));

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

  @Test public final void testCopyTyped()
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

    PMatrixM4x4F.copy(m0, m1);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);

    Assert.assertEquals(1.0, (double) m1.getR0C0F(), 0.0);
    Assert.assertEquals(2.0, (double) m1.getR0C1F(), 0.0);
    Assert.assertEquals(3.0, (double) m1.getR0C2F(), 0.0);
    Assert.assertEquals(4.0, (double) m1.getR0C3F(), 0.0);

    Assert.assertEquals(5.0, (double) m1.getR1C0F(), 0.0);
    Assert.assertEquals(6.0, (double) m1.getR1C1F(), 0.0);
    Assert.assertEquals(7.0, (double) m1.getR1C2F(), 0.0);
    Assert.assertEquals(8.0, (double) m1.getR1C3F(), 0.0);

    Assert.assertEquals(9.0, (double) m1.getR2C0F(), 0.0);
    Assert.assertEquals(10.0, (double) m1.getR2C1F(), 0.0);
    Assert.assertEquals(11.0, (double) m1.getR2C2F(), 0.0);
    Assert.assertEquals(12.0, (double) m1.getR2C3F(), 0.0);

    Assert.assertEquals(13.0, (double) m1.getR3C0F(), 0.0);
    Assert.assertEquals(14.0, (double) m1.getR3C1F(), 0.0);
    Assert.assertEquals(15.0, (double) m1.getR3C2F(), 0.0);
    Assert.assertEquals(16.0, (double) m1.getR3C3F(), 0.0);

    this.checkDirectBufferInvariants(m0);
    this.checkDirectBufferInvariants(m1);
  }
}
