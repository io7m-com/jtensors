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
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.MatrixHeapArrayM4x4D;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.Quaternion4DType;
import com.io7m.jtensors.QuaternionM4D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable3DType;
import org.junit.Assert;
import org.junit.Test;

public abstract class QuaternionM4DContract<T extends Quaternion4DType>
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(
    1.0, 0.0, 0.0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(
    0.0, 1.0, 0.0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(
    0.0, 0.0, 1.0);

  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract T newQuaternion();

  protected abstract T newQuaternion(
    final double x,
    final double y,
    final double z,
    final double w);

  protected abstract T newQuaternion(final T v);

  @Test public final void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = QuaternionM4DContract.getRandom() * max;
      final double y0 = QuaternionM4DContract.getRandom() * max;
      final double z0 = QuaternionM4DContract.getRandom() * max;
      final double w0 = QuaternionM4DContract.getRandom() * max;
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final double x1 = QuaternionM4DContract.getRandom() * max;
      final double y1 = QuaternionM4DContract.getRandom() * max;
      final double z1 = QuaternionM4DContract.getRandom() * max;
      final double w1 = QuaternionM4DContract.getRandom() * max;
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      QuaternionM4D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getWD(), v0.getWD() + v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + v1.getZD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getWD(), orig_w + v1.getWD()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0, 1.0, 1.0, 1.0);
    final T v1 = this.newQuaternion(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final T ov0 = QuaternionM4D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(2.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final T ov1 = QuaternionM4D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, ov1.getWD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = QuaternionM4DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newQuaternion(x0, y0, z0, w0);
      final T v1 = this.newQuaternion(x0, y0, z0, w0);
      final T v2 = this.newQuaternion(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newQuaternion(3.0, 5.0, 7.0, 11.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
    Assert.assertEquals(v.getZD(), v.getZD(), 0.0);
    Assert.assertEquals(v.getWD(), v.getWD(), 0.0);
  }

  @Test public final void testConjugate()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final T e = this.newQuaternion(-1.0, -2.0, -3.0, 4.0);
    final T q = this.newQuaternion(1.0, 2.0, 3.0, 4.0);
    final T r = this.newQuaternion();
    final T u = QuaternionM4D.conjugate(q, r);
    final boolean t = QuaternionM4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Test public final void testConjugateInPlace()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final T e = this.newQuaternion(-1.0, -2.0, -3.0, 4.0);
    final T q = this.newQuaternion(1.0, 2.0, 3.0, 4.0);
    final T r = this.newQuaternion(q);
    final T u = QuaternionM4D.conjugateInPlace(r);
    final boolean t = QuaternionM4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Test public final void testConjugateInvertible()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (QuaternionM4DContract.getRandom() * 200.0) - 100.0;
      final double y = (QuaternionM4DContract.getRandom() * 200.0) - 100.0;
      final double z = (QuaternionM4DContract.getRandom() * 200.0) - 100.0;
      final double w = (QuaternionM4DContract.getRandom() * 200.0) - 100.0;

      final T q = this.newQuaternion(x, y, z, w);
      final T qc0 = this.newQuaternion();
      final T qc1 = this.newQuaternion();
      QuaternionM4D.conjugate(q, qc0);
      QuaternionM4D.conjugate(qc0, qc1);

      eq = AlmostEqualDouble.almostEqual(context_d, q.getXD(), qc1.getXD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getYD(), qc1.getYD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getZD(), qc1.getZD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getWD(), qc1.getWD());
      Assert.assertTrue(eq);
    }
  }

  @Test public final void testCopy()
  {
    final T vb = this.newQuaternion(5.0, 6.0, 7.0, 8.0);
    final T va = this.newQuaternion(1.0, 2.0, 3.0, 4.0);

    Assert.assertNotEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertNotEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertNotEquals(vb.getZD(), va.getZD(), 0.0);
    Assert.assertNotEquals(vb.getWD(), va.getWD(), 0.0);

    QuaternionM4D.copy(va, vb);

    Assert.assertEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertEquals(vb.getZD(), va.getZD(), 0.0);
    Assert.assertEquals(vb.getWD(), va.getWD(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newQuaternion(
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(0.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newQuaternion(
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom4D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0);
    Assert.assertEquals(v0.getWD(), v1.getWD(), 0.0);
  }

  @Test public final void testCopy4Correct()
  {
    final T v0 = this.newQuaternion(
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE,
      QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }

  @Test public final void testDefault0001()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T ql = this.newQuaternion();
    final T qr = this.newQuaternion(0.0, 0.0, 0.0, 1.0);

    
    
    Assert.assertTrue(QuaternionM4D.almostEqual(ec, ql, qr));
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newQuaternion(10.0, 10.0, 10.0, 10.0);
    final T v1 = this.newQuaternion(10.0, 10.0, 10.0, 10.0);

    {
      final double p = QuaternionM4D.dotProduct(v0, v1);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v0.getWD(), 0.0);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionM4D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v0.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionM4D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = QuaternionM4DContract.getRandom();
      final double y = QuaternionM4DContract.getRandom();
      final double z = QuaternionM4DContract.getRandom();
      final double w = QuaternionM4DContract.getRandom();
      final T q = this.newQuaternion(x, y, z, w);
      final double dp = QuaternionM4D.dotProduct(q, q);

      
      

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = QuaternionM4DContract.getRandom();
      final double y = QuaternionM4DContract.getRandom();
      final double z = QuaternionM4DContract.getRandom();
      final double w = QuaternionM4DContract.getRandom();
      final T q = this.newQuaternion(x, y, z, w);

      final double ms = QuaternionM4D.magnitudeSquared(q);
      final double dp = QuaternionM4D.dotProduct(q, q);

      
      
      

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newQuaternion();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newQuaternion();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newQuaternion();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = QuaternionM4DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newQuaternion();
    final T m1 = this.newQuaternion();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setXD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setYD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setZD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setWD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newQuaternion(1.0, 2.0, 3.0, 4.0);
    final T v1 = this.newQuaternion(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
    Assert.assertEquals(v1.getZD(), v0.getZD(), 0.0);
    Assert.assertEquals(v1.getWD(), v0.getWD(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final double x1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      final T vr1 = this.newQuaternion();
      QuaternionM4D.interpolateLinear(c, v0, v1, 0.0, vr0);
      QuaternionM4D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getXD(), vr0.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getYD(), vr0.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getZD(), vr0.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getWD(), vr0.getWD()));

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getZD(), vr1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getWD(), vr1.getWD()));
    }
  }

  @Test public final void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final QuaternionM4D.ContextQM4D qc = new QuaternionM4D.ContextQM4D();

    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mqr = MatrixHeapArrayM4x4D.newMatrix();
    final T qr = this.newQuaternion();

    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(-1.0, 0.0, 0.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM4x4D.lookAt(mc, origin, target, axis, mr);
    QuaternionM4D.lookAt(qc, origin, target, axis, qr);
    QuaternionM4D.makeRotationMatrix4x4(qr, mqr);

    
    
    
    

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mqr.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final QuaternionM4D.ContextQM4D qc = new QuaternionM4D.ContextQM4D();

    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mqr = MatrixHeapArrayM4x4D.newMatrix();
    final T qr = this.newQuaternion();

    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(1.0, 0.0, 0.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM4x4D.lookAt(mc, origin, target, axis, mr);
    QuaternionM4D.lookAt(qc, origin, target, axis, qr);
    QuaternionM4D.makeRotationMatrix4x4(qr, mqr);

    
    
    
    

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mqr.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D.ContextQM4D qc = new QuaternionM4D.ContextQM4D();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();

    final Matrix4x4DType ml = MatrixHeapArrayM4x4D.newMatrix();
    final T lq = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double origin_x = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);
      final double origin_y = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);
      final double origin_z = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);

      final double target_x = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);
      final double target_y = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);
      final double target_z = (QuaternionM4DContract.getRandom() * 100.0)
                              - (QuaternionM4DContract.getRandom() * 100.0);

      final VectorI3D origin = new VectorI3D(origin_x, origin_y, origin_z);
      final VectorI3D target = new VectorI3D(target_x, target_y, target_z);

      MatrixM4x4D.lookAt(
        mc, origin, target, QuaternionM4DContract.AXIS_Y, ml);
      QuaternionM4D.lookAt(
        qc, origin, target, QuaternionM4DContract.AXIS_Y, lq);
      QuaternionM4D.makeRotationMatrix4x4(lq, mq);

      
      
      
      

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final double x = ml.getRowColumnD(row, col);
          final double y = mq.getRowColumnD(row, col);

          final boolean eq = AlmostEqualDouble.almostEqual(ec, x, y);
          Assert.assertTrue(eq);
        }
      }
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x =
        1.0 + (QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
      final double y =
        1.0 + (QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
      final double z =
        1.0 + (QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
      final double w =
        1.0 + (QuaternionM4DContract.getRandom() * Double.MAX_VALUE);
      final T v = this.newQuaternion(x, y, z, w);

      final double m = QuaternionM4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x =
        QuaternionM4DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y =
        QuaternionM4DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double z =
        QuaternionM4DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double w =
        QuaternionM4DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();
      QuaternionM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newQuaternion(0.0, 0.0, 0.0, 0.0);
    final T vr = QuaternionM4D.normalizeInPlace(v);
    final double m = QuaternionM4D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newQuaternion(1.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newQuaternion(8.0, 0.0, 0.0, 0.0);

    {
      final double p = QuaternionM4D.dotProduct(v, v);
      final double q = QuaternionM4D.magnitudeSquared(v);
      final double r = QuaternionM4D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newQuaternion(0.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testMakeAxisAngleNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3D axis_r = new VectorI3D(
        QuaternionM4DContract.getRandom(),
        QuaternionM4DContract.getRandom(),
        QuaternionM4DContract

          .getRandom());
      final VectorI3D axis_n = VectorI3D.normalize(axis_r);

      final T q = this.newQuaternion();
      QuaternionM4D.makeFromAxisAngle(
        c,
        axis_n,
        Math.toRadians(QuaternionM4DContract.getRandom() * 360.0),
        q);

      final double m = QuaternionM4D.magnitude(q);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));

      
      
      
      
      
    }
  }

  @Test public final void testMakeAxisAngleX_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getXD(), 0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.9238795325112867));
  }

  @Test public final void testMakeAxisAngleX_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getXD(), 0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.7071067811865475));
  }

  @Test public final void testMakeAxisAngleY_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getYD(), 0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.9238795325112867));
  }

  @Test public final void testMakeAxisAngleY_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getYD(), 0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.7071067811865475));
  }

  @Test public final void testMakeAxisAngleZ_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getZD(), 0.3826834323650898));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.9238795325112867));
  }

  @Test public final void testMakeAxisAngleZ_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final T q = this.newQuaternion();
    final T r =
      QuaternionM4D.makeFromAxisAngle(c, axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getZD(), 0.7071067811865475));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, q.getWD(), 0.7071067811865475));
  }

  @Test public final void testMakeFromMatrix3x3Exhaustive()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();
    final T qfm = this.newQuaternion();
    final T qaa = this.newQuaternion();
    final Matrix3x3DType m = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees =
        (2.0 * QuaternionM4DContract.getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = QuaternionM4DContract.getRandom();
      final double axis_y = QuaternionM4DContract.getRandom();
      final double axis_z = QuaternionM4DContract.getRandom();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4D.makeFromAxisAngle(c, axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3D.makeRotation(angle, axis, m);
      QuaternionM4D.makeFromRotationMatrix3x3(m, qfm);

      final double mag_qfm = QuaternionM4D.magnitude(qfm);
      final double mag_qaa = QuaternionM4D.magnitude(qaa);

      
      
      
      
      
      
      
      
      

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Test public final void testMakeFromMatrix4x4Exhaustive()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T qfm = this.newQuaternion();
    final T qaa = this.newQuaternion();
    final Matrix4x4DType m = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees =
        (2.0 * QuaternionM4DContract.getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = QuaternionM4DContract.getRandom();
      final double axis_y = QuaternionM4DContract.getRandom();
      final double axis_z = QuaternionM4DContract.getRandom();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4D.makeFromAxisAngle(c, axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4D.makeRotation(angle, axis, m);
      QuaternionM4D.makeFromRotationMatrix4x4(m, qfm);

      final double mag_qfm = QuaternionM4D.magnitude(qfm);
      final double mag_qaa = QuaternionM4D.magnitude(qaa);

      
      
      
      
      
      
      
      
      

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Test public final void testMakeMatrix3x3_45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Identity()
  {
    final T q = this.newQuaternion();
    final Matrix3x3DType m = MatrixHeapArrayM3x3D.newMatrix();

    QuaternionM4D.makeRotationMatrix3x3(q, m);

    Assert.assertEquals(m.getRowColumnD(0, 0), 1.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(0, 1), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(0, 2), 0.0, 0.0);

    Assert.assertEquals(m.getRowColumnD(1, 0), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(1, 1), 1.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(1, 2), 0.0, 0.0);

    Assert.assertEquals(m.getRowColumnD(2, 0), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(2, 1), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(2, 2), 1.0, 0.0);
  }

  @Test public final void testMakeMatrix3x3_Minus45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Minus45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Minus45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Identity()
  {
    final T q = this.newQuaternion();
    final Matrix4x4DType m = MatrixHeapArrayM4x4D.newMatrix();

    QuaternionM4D.makeRotationMatrix4x4(q, m);

    Assert.assertEquals(m.getRowColumnD(0, 0), 1.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(0, 1), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(0, 2), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(0, 3), 0.0, 0.0);

    Assert.assertEquals(m.getRowColumnD(1, 0), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(1, 1), 1.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(1, 2), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(1, 3), 0.0, 0.0);

    Assert.assertEquals(m.getRowColumnD(2, 0), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(2, 1), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(2, 2), 1.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(2, 3), 0.0, 0.0);

    Assert.assertEquals(m.getRowColumnD(3, 0), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(3, 1), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(3, 2), 0.0, 0.0);
    Assert.assertEquals(m.getRowColumnD(3, 3), 1.0, 0.0);
  }

  @Test public final void testMakeMatrix4x4_Minus45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Minus45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Minus45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final T q = this.newQuaternion();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionM4DContract.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(c, axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMultiply()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();
    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final T qx = this.newQuaternion();
    final T qxr =
      QuaternionM4D.makeFromAxisAngle(c, axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    final T qyr =
      QuaternionM4D.makeFromAxisAngle(c, axis_y, Math.toRadians(45.0), qy);

    Assert.assertSame(qx, qxr);
    Assert.assertSame(qy, qyr);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4D.multiply(qy, qx, qr);

    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, qr.getXD(), 0.3535533905932738));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, qr.getYD(), 0.3535533905932738));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, qr.getZD(), -0.14644660940672624));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, qr.getWD(), 0.8535533905932737));
  }

  @Test public final void testMultiplyInPlace()
  {
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);

    final T qx = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_y, Math.toRadians(45.0), qy);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4D.multiplyInPlace(qr, qy);
    QuaternionM4D.multiplyInPlace(qr, qx);

    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.3535533983204287, qr.getXD(), 0.0000001);
    Assert.assertEquals(0.3535533983204287, qr.getYD(), 0.0000001);
    Assert.assertEquals(-0.14644661713388138, qr.getZD(), 0.0000001);
    Assert.assertEquals(0.8535533828661185, qr.getWD(), 0.0000001);
  }

  @Test public final void testMultiplyInPlaceOther()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D axis_z = new VectorI3D(0.0, 0.0, 1.0);

    final T qx = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_y, Math.toRadians(45.0), qy);
    final T qz = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_z, Math.toRadians(45.0), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4D.multiplyInPlace(qr, qz);
    QuaternionM4D.multiplyInPlace(qr, qy);
    QuaternionM4D.multiplyInPlace(qr, qx);

    
    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.1913417153164435, qr.getXD(), 0.0000001);
    Assert.assertEquals(0.4619397784426109, qr.getYD(), 0.0000001);
    Assert.assertEquals(0.1913417153164436, qr.getZD(), 0.0000001);
    Assert.assertEquals(0.8446231923478736, qr.getWD(), 0.0000001);
  }

  @Test public final void testMultiplyOther()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final QuaternionM4D.ContextQM4D c = new QuaternionM4D.ContextQM4D();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D axis_z = new VectorI3D(0.0, 0.0, 1.0);

    final T qx = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_y, Math.toRadians(45.0), qy);
    final T qz = this.newQuaternion();
    QuaternionM4D.makeFromAxisAngle(
      c, axis_z, Math.toRadians(45.0), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4D.multiply(qy, qx, qr);
    QuaternionM4D.multiply(qz, qr, qr);

    
    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.1913417153164435, qr.getXD(), 0.0000001);
    Assert.assertEquals(0.4619397784426109, qr.getYD(), 0.0000001);
    Assert.assertEquals(0.1913417153164436, qr.getZD(), 0.0000001);
    Assert.assertEquals(0.8446231923478736, qr.getWD(), 0.0000001);
  }

  @Test public final void testNegation()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (QuaternionM4DContract.getRandom() * 2.0)
                       - QuaternionM4DContract.getRandom();
      final double y = (QuaternionM4DContract.getRandom() * 2.0)
                       - QuaternionM4DContract.getRandom();
      final double z = (QuaternionM4DContract.getRandom() * 2.0)
                       - QuaternionM4DContract.getRandom();
      final double w = (QuaternionM4DContract.getRandom() * 2.0)
                       - QuaternionM4DContract.getRandom();
      final T qi = this.newQuaternion(x, y, z, w);
      final T qn = this.newQuaternion(-x, -y, -z, -w);
      final T qr = this.newQuaternion();

      QuaternionM4D.negate(qi, qr);

      
      
      
      

      Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionM4D.almostEqual(context, qn, qr));
    }
  }

  @Test public final void testNegationCases()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final T qi = this.newQuaternion(1.0, 2.0, 3.0, 4.0);
    final T qnx = this.newQuaternion(-1.0, 2.0, 3.0, 4.0);
    final T qny = this.newQuaternion(-1.0, -2.0, 3.0, 4.0);
    final T qnz = this.newQuaternion(-1.0, -2.0, -3.0, 4.0);
    final T qnw = this.newQuaternion(-1.0, -2.0, -3.0, -4.0);

    Assert.assertFalse(QuaternionM4D.isNegationOf(context, qi, qi));
    Assert.assertFalse(QuaternionM4D.isNegationOf(context, qi, qnx));
    Assert.assertFalse(QuaternionM4D.isNegationOf(context, qi, qny));
    Assert.assertFalse(QuaternionM4D.isNegationOf(context, qi, qnz));
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qnw));

    QuaternionM4D.negateInPlace(qnw);
    Assert.assertFalse(QuaternionM4D.isNegationOf(context, qi, qnw));
    Assert.assertTrue(QuaternionM4D.almostEqual(context, qi, qnw));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newQuaternion(8.0, 0.0, 0.0, 0.0);
    final T out = this.newQuaternion();
    final T vr = QuaternionM4D.normalize(v0, out);

    Assert.assertEquals(out, vr);

    final double m = QuaternionM4D.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T qr = this.newQuaternion();
    final T q = this.newQuaternion(0.0, 0.0, 0.0, 0.0);
    QuaternionM4D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getWD()));
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);

    final T ov0 = QuaternionM4D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(2.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);

    final T ov1 = QuaternionM4D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, ov1.getWD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getWD(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();

      QuaternionM4D.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getYD(), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getZD(), vr.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getWD(), vr.getWD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();
        final double orig_w = v.getWD();

        QuaternionM4D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), orig_w));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();

      QuaternionM4D.scale(v, 0.0, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getWD(), 0.0));

      {
        QuaternionM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), 0.0));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newQuaternion(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().endsWith(" 1.0 2.0 3.0 4.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w0 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final double x1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double z1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final double w1 = QuaternionM4DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      QuaternionM4D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() - v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getWD(), v0.getWD() - v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z - v1.getZD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getWD(), orig_w - v1.getWD()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0, 1.0, 1.0, 1.0);
    final T v1 = this.newQuaternion(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final T ov0 = QuaternionM4D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(0.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final T ov1 = QuaternionM4D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), 0.0);
    Assert.assertEquals(0.0, ov1.getYD(), 0.0);
    Assert.assertEquals(0.0, ov1.getZD(), 0.0);
    Assert.assertEquals(0.0, ov1.getWD(), 0.0);
    Assert.assertEquals(0.0, v0.getXD(), 0.0);
    Assert.assertEquals(0.0, v0.getYD(), 0.0);
    Assert.assertEquals(0.0, v0.getZD(), 0.0);
    Assert.assertEquals(0.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }
}
