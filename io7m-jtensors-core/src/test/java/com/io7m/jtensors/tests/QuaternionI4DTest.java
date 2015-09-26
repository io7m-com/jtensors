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
import com.io7m.jtensors.QuaternionI4D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable3DType;
import org.junit.Assert;
import org.junit.Test;

public class QuaternionI4DTest extends QuaternionI4Contract
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(
    1.0, 0.0, 0.0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(
    0.0, 1.0, 0.0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(
    0.0, 0.0, 1.0);

  @Override @Test public final void testAdd()
  {
    final ContextRelative context = new ContextRelative();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = this.getRandom() * max;
      final double y0 = this.getRandom() * max;
      final double z0 = this.getRandom() * max;
      final double w0 = this.getRandom() * max;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * max;
      final double y1 = this.getRandom() * max;
      final double z1 = this.getRandom() * max;
      final double w1 = this.getRandom() * max;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.add(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getZD(), v0.getZD() + v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getWD(), v0.getWD() + v1.getWD()));
    }
  }

  @Override @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = this.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, z, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, q, z, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, q, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, z, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, z, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, q, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, z, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, q, w);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, z, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, q, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, q, q, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, q, q);
      Assert.assertFalse(QuaternionI4D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);
      final QuaternionI4D v1 = new QuaternionI4D(x0, y0, z0, w0);
      final QuaternionI4D v2 = new QuaternionI4D(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public final void testCheckInterface()
  {
    final QuaternionI4D v = new QuaternionI4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
    Assert.assertEquals(v.getZD(), v.getZD(), 0.0);
    Assert.assertEquals(v.getWD(), v.getWD(), 0.0);
  }

  @Override @Test public final void testConjugate()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D e = new QuaternionI4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionI4D q = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D r = QuaternionI4D.conjugate(q);
    final boolean t = QuaternionI4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
  }

  @Override @Test public final void testConjugateInvertible()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (this.getRandom() * 200.0) - 100.0;
      final double y = (this.getRandom() * 200.0) - 100.0;
      final double z = (this.getRandom() * 200.0) - 100.0;
      final double w = (this.getRandom() * 200.0) - 100.0;

      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D qc0 = QuaternionI4D.conjugate(q);
      final QuaternionI4D qc1 = QuaternionI4D.conjugate(qc0);

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

  @Override @Test public final void testDefault0001()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionI4D v = new QuaternionI4D();
    QuaternionI4D.almostEqual(context, v, QuaternionI4D.IDENTITY);
  }

  @Override @Test public final void testDotProduct()
  {
    final QuaternionI4D v0 = new QuaternionI4D(10.0, 10.0, 10.0, 10.0);
    final QuaternionI4D v1 = new QuaternionI4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = QuaternionI4D.dotProduct(v0, v1);
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
      final double p = QuaternionI4D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v0.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionI4D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Override @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom();
      final double y = this.getRandom();
      final double z = this.getRandom();
      final double w = this.getRandom();
      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);
      final double dp = QuaternionI4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom();
      final double y = this.getRandom();
      final double z = this.getRandom();
      final double w = this.getRandom();
      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);

      final double ms = QuaternionI4D.magnitudeSquared(q);
      final double dp = QuaternionI4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public final void testEqualsCorrect()
  {
    final QuaternionI4D v0 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D v1 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vw = new QuaternionI4D(0.0, 0.0, 0.0, 1.0);
    final QuaternionI4D vz = new QuaternionI4D(0.0, 0.0, 1.0, 0.0);
    final QuaternionI4D vy = new QuaternionI4D(0.0, 1.0, 0.0, 0.0);
    final QuaternionI4D vx = new QuaternionI4D(1.0, 0.0, 0.0, 0.0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @Override @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = this.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D m1 = new QuaternionI4D(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public final void testHashCodeEqualsCorrect()
  {
    final QuaternionI4D m0 = new QuaternionI4D();
    final QuaternionI4D m1 = new QuaternionI4D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Override @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final QuaternionI4D m0 = new QuaternionI4D(23.0, 0.0, 0.0, 1.0);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0.0, 23.0, 0.0, 1.0);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0.0, 0.0, 23.0, 1.0);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0.0, 0.0, 0.0, 23.0);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public final void testInitializeReadable()
  {
    final QuaternionI4D v0 = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D v1 = new QuaternionI4D(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
    Assert.assertEquals(v1.getZD(), v0.getZD(), 0.0);
    Assert.assertEquals(v1.getWD(), v0.getWD(), 0.0);
  }

  @Override @Test public final void testInterpolateLinearLimits()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * Double.MAX_VALUE;
      final double y1 = this.getRandom() * Double.MAX_VALUE;
      final double z1 = this.getRandom() * Double.MAX_VALUE;
      final double w1 = this.getRandom() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      Assert.assertTrue(
        QuaternionI4D.almostEqual(
          context, QuaternionI4D.interpolateLinear(v0, v1, 0.0), v0));
      Assert.assertTrue(
        QuaternionI4D.almostEqual(
          context, QuaternionI4D.interpolateLinear(v0, v1, 1.0), v1));
    }
  }

  @Override @Test public final void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final QuaternionI4D.Context qc = new QuaternionI4D.Context();

    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mqr = MatrixHeapArrayM4x4D.newMatrix();

    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(-1.0, 0.0, 0.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.lookAt(mc, origin, target, axis, mr);
    final QuaternionI4D q =
      QuaternionI4D.lookAt(qc, origin, target, axis);
    QuaternionI4D.makeRotationMatrix4x4(q, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

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

  @Override @Test public final void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();
    final QuaternionI4D.Context qc = new QuaternionI4D.Context();

    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mqr = MatrixHeapArrayM4x4D.newMatrix();

    final VectorReadable3DType origin = new VectorI3D(0.0, 0.0, 0.0);
    final VectorReadable3DType target = new VectorI3D(1.0, 0.0, 0.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.lookAt(mc, origin, target, axis, mr);
    final QuaternionI4D q =
      QuaternionI4D.lookAt(qc, origin, target, axis);
    QuaternionI4D.makeRotationMatrix4x4(q, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

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

  @Override @Test public final void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D.Context qc = new QuaternionI4D.Context();
    final MatrixM4x4D.ContextMM4D mc = new MatrixM4x4D.ContextMM4D();

    final Matrix4x4DType ml = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double origin_x =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);
      final double origin_y =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);
      final double origin_z =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);

      final double target_x =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);
      final double target_y =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);
      final double target_z =
        (this.getRandom() * 100.0) - (this.getRandom() * 100.0);

      final VectorI3D origin = new VectorI3D(origin_x, origin_y, origin_z);
      final VectorI3D target = new VectorI3D(target_x, target_y, target_z);

      MatrixM4x4D.lookAt(
        mc, origin, target, QuaternionI4DTest.AXIS_Y, ml);
      final QuaternionI4D lq = QuaternionI4D.lookAt(
        qc, origin, target, QuaternionI4DTest.AXIS_Y);
      QuaternionI4D.makeRotationMatrix4x4(lq, mq);

      System.out.println("ml : ");
      System.out.println(ml);
      System.out.println("mq : ");
      System.out.println(mq);

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

  protected double getRandom()
  {
    return Math.random();
  }

  @Override @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double y = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double z = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double w = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final double m = VectorI4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double z = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double w = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI4D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D vr = VectorI4D.normalize(v);
    final double m = VectorI4D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(1.0, 0.0, 0.0, 0.0);
    final double m = VectorI4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public final void testMagnitudeSimple()
  {
    final VectorI4D v = new VectorI4D(8.0, 0.0, 0.0, 0.0);

    {
      final double p = VectorI4D.dotProduct(v, v);
      final double q = VectorI4D.magnitudeSquared(v);
      final double r = VectorI4D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Override @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final double m = VectorI4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public final void testMakeAxisAngleNormal()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3D axis_r =
        new VectorI3D(this.getRandom(), this.getRandom(), this.getRandom());
      final VectorI3D axis_n = VectorI3D.normalize(axis_r);

      final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(
        axis_n, Math.toRadians(this.getRandom() * 360.0));

      System.out.println("q            : " + q);
      System.out.println("magnitude(q) : " + QuaternionI4D.magnitude(q));

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, QuaternionI4D.magnitude(q), 1.0));
    }
  }

  @Override @Test public final void testMakeAxisAngleX_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getXD(), 0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.9238795325112867));
  }

  @Override @Test public final void testMakeAxisAngleX_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getXD(), 0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.7071067811865475));
  }

  @Override @Test public final void testMakeAxisAngleY_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getYD(), 0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.9238795325112867));
  }

  @Override @Test public final void testMakeAxisAngleY_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getYD(), 0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.7071067811865475));
  }

  @Override @Test public final void testMakeAxisAngleZ_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getZD(), 0.3826834323650898));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.9238795325112867));
  }

  @Override @Test public final void testMakeAxisAngleZ_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90.0));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getZD(), 0.7071067811865475));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, q.getWD(), 0.7071067811865475));
  }

  @Override @Test public final void testMakeFromMatrix3x3Exhaustive()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType m = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2.0 * this.getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = this.getRandom();
      final double axis_y = this.getRandom();
      final double axis_z = this.getRandom();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4D qaa = QuaternionI4D.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3D.makeRotation(angle, axis, m);
      final QuaternionI4D qfm = QuaternionI4D.makeFromRotationMatrix3x3(m);

      final double mag_qfm = QuaternionI4D.magnitude(qfm);
      final double mag_qaa = QuaternionI4D.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

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

      if (QuaternionI4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public final void testMakeFromMatrix4x4Exhaustive()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType m = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2.0 * this.getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = this.getRandom();
      final double axis_y = this.getRandom();
      final double axis_z = this.getRandom();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4D qaa = QuaternionI4D.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4D.makeRotation(angle, axis, m);
      final QuaternionI4D qfm = QuaternionI4D.makeFromRotationMatrix4x4(m);

      final double mag_qfm = QuaternionI4D.magnitude(qfm);
      final double mag_qaa = QuaternionI4D.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

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

      if (QuaternionI4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public final void testMakeMatrix3x3_45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix3x3_45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix3x3_45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix3x3_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final Matrix3x3DType m = MatrixHeapArrayM3x3D.newMatrix();

    QuaternionI4D.makeRotationMatrix3x3(q, m);

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

  @Override @Test public final void testMakeMatrix3x3_Minus45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix3x3_Minus45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix3x3_Minus45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix3x3DType mq = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType mr = MatrixHeapArrayM3x3D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final Matrix4x4DType m = MatrixHeapArrayM4x4D.newMatrix();

    QuaternionI4D.makeRotationMatrix4x4(q, m);

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

  @Override @Test public final void testMakeMatrix4x4_Minus45X()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_Minus45Y()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMakeMatrix4x4_Minus45Z()
  {
    final ContextRelative context_d = TestUtilities.getDoubleEqualityContext();

    final Matrix4x4DType mq = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType mr = MatrixHeapArrayM4x4D.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.getRowColumnD(row, col);
        final double y = mq.getRowColumnD(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public final void testMultiply()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D qx =
      QuaternionI4D.makeFromAxisAngle(axis_x, Math.toRadians(45.0));
    final QuaternionI4D qy =
      QuaternionI4D.makeFromAxisAngle(axis_y, Math.toRadians(45.0));

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionI4D qr = QuaternionI4D.multiply(qy, qx);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, qr.getXD(), 0.3535533905932738));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, qr.getYD(), 0.3535533905932738));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, qr.getZD(), -0.14644660940672624));
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, qr.getWD(), 0.8535533905932737));
  }

  @Override @Test public final void testNegation()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (this.getRandom() * 2.0) - this.getRandom();
      final double y = (this.getRandom() * 2.0) - this.getRandom();
      final double z = (this.getRandom() * 2.0) - this.getRandom();
      final double w = (this.getRandom() * 2.0) - this.getRandom();
      final QuaternionI4D qi = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D qn = new QuaternionI4D(-x, -y, -z, -w);
      final QuaternionI4D qr = QuaternionI4D.negate(qi);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionI4D.almostEqual(context, qn, qr));
    }
  }

  @Override @Test public final void testNegationCases()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D qi = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D qnx = new QuaternionI4D(-1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D qny = new QuaternionI4D(-1.0, -2.0, 3.0, 4.0);
    final QuaternionI4D qnz = new QuaternionI4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionI4D qnw = new QuaternionI4D(-1.0, -2.0, -3.0, -4.0);

    Assert.assertFalse(QuaternionI4D.isNegationOf(context, qi, qi));
    Assert.assertFalse(QuaternionI4D.isNegationOf(context, qi, qnx));
    Assert.assertFalse(QuaternionI4D.isNegationOf(context, qi, qny));
    Assert.assertFalse(QuaternionI4D.isNegationOf(context, qi, qnz));
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnw));

    final QuaternionI4D qnwn = QuaternionI4D.negate(qnw);
    Assert.assertFalse(QuaternionI4D.isNegationOf(context, qi, qnwn));
    Assert.assertTrue(QuaternionI4D.almostEqual(context, qi, qnwn));
  }

  @Override @Test public final void testNormalizeSimple()
  {
    final QuaternionI4D v0 = new QuaternionI4D(8.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vr = QuaternionI4D.normalize(v0);
    final double m = QuaternionI4D.magnitude(vr);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Override @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D q = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D qr = QuaternionI4D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getWD()));
  }

  @Override @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MAX_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.scale(v, 1.0);

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
    }
  }

  @Override @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MAX_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getWD(), 0.0));
    }
  }

  @Override @Test public final void testString()
  {
    final QuaternionI4D v = new QuaternionI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue("[QuaternionI4D 0.0 1.0 2.0 3.0]".equals(v.toString()));
  }

  @Override @Test public final void testSubtract()
  {
    final ContextRelative context = new ContextRelative();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * Double.MAX_VALUE;
      final double y1 = this.getRandom() * Double.MAX_VALUE;
      final double z1 = this.getRandom() * Double.MAX_VALUE;
      final double w1 = this.getRandom() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.subtract(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getZD(), v0.getZD() - v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getWD(), v0.getWD() - v1.getWD()));
    }
  }
}
