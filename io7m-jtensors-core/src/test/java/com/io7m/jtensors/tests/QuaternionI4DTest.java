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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.QuaternionI4D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable3DType;

public class QuaternionI4DTest extends QuaternionI4Contract
{
  private static final VectorReadable3DType AXIS_X = new VectorI3D(1, 0, 0);
  private static final VectorReadable3DType AXIS_Y = new VectorI3D(0, 1, 0);
  private static final VectorReadable3DType AXIS_Z = new VectorI3D(0, 0, 1);

  @Override @Test public void testAdd()
  {
    final ContextRelative context = new ContextRelative();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final double w0 = Math.random() * max;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getZD(),
        v0.getZD() + v1.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getWD(),
        v0.getWD() + v1.getWD()));
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
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

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);
      final QuaternionI4D v1 = new QuaternionI4D(x0, y0, z0, w0);
      final QuaternionI4D v2 = new QuaternionI4D(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionI4D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final QuaternionI4D v = new QuaternionI4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
    Assert.assertTrue(v.getWD() == v.getWD());
  }

  @Override @Test public void testConjugate()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D e = new QuaternionI4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionI4D q = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D r = QuaternionI4D.conjugate(q);
    final boolean t = QuaternionI4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
  }

  @Override @Test public void testConjugateInvertible()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 200) - 100;
      final double y = (Math.random() * 200) - 100;
      final double z = (Math.random() * 200) - 100;
      final double w = (Math.random() * 200) - 100;

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

  @Override @Test public void testDefault0001()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionI4D v = new QuaternionI4D();
    QuaternionI4D.almostEqual(context, v, QuaternionI4D.IDENTITY);
  }

  @Override @Test public void testDotProduct()
  {
    final QuaternionI4D v0 = new QuaternionI4D(10.0, 10.0, 10.0, 10.0);
    final QuaternionI4D v1 = new QuaternionI4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = QuaternionI4D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = QuaternionI4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = QuaternionI4D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);
      final double dp = QuaternionI4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);

      final double ms = QuaternionI4D.magnitudeSquared(q);
      final double dp = QuaternionI4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
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

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
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

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final QuaternionI4D m0 = new QuaternionI4D();
    final QuaternionI4D m1 = new QuaternionI4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final QuaternionI4D m0 = new QuaternionI4D(23, 0, 0, 1);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0, 23, 0, 1);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0, 0, 23, 1);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4D m0 = new QuaternionI4D(0, 0, 0, 23);
      final QuaternionI4D m1 = new QuaternionI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final QuaternionI4D v0 = new QuaternionI4D(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionI4D v1 = new QuaternionI4D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
    Assert.assertTrue(v0.getWD() == v1.getWD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      Assert.assertTrue(QuaternionI4D.almostEqual(
        context,
        QuaternionI4D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(QuaternionI4D.almostEqual(
        context,
        QuaternionI4D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @Override @Test public void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextM4D mc = new MatrixM4x4D.ContextM4D();
    final QuaternionI4D.Context qc = new QuaternionI4D.Context();

    final MatrixM4x4D mr = new MatrixM4x4D();
    final MatrixM4x4D mqr = new MatrixM4x4D();

    final VectorReadable3DType origin = new VectorI3D(0, 0, 0);
    final VectorReadable3DType target = new VectorI3D(-1, 0, 0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, mr);
    final QuaternionI4D q =
      QuaternionI4D.lookAtWithContext(qc, origin, target, axis);
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

  @Override @Test public void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.ContextM4D mc = new MatrixM4x4D.ContextM4D();
    final QuaternionI4D.Context qc = new QuaternionI4D.Context();

    final MatrixM4x4D mr = new MatrixM4x4D();
    final MatrixM4x4D mqr = new MatrixM4x4D();

    final VectorReadable3DType origin = new VectorI3D(0, 0, 0);
    final VectorReadable3DType target = new VectorI3D(1, 0, 0);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, mr);
    final QuaternionI4D q =
      QuaternionI4D.lookAtWithContext(qc, origin, target, axis);
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

  @Override @Test public void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D.Context qc = new QuaternionI4D.Context();
    final MatrixM4x4D.ContextM4D mc = new MatrixM4x4D.ContextM4D();

    final MatrixM4x4D ml = new MatrixM4x4D();
    final MatrixM4x4D mq = new MatrixM4x4D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double origin_x = (Math.random() * 100) - (Math.random() * 100);
      final double origin_y = (Math.random() * 100) - (Math.random() * 100);
      final double origin_z = (Math.random() * 100) - (Math.random() * 100);

      final double target_x = (Math.random() * 100) - (Math.random() * 100);
      final double target_y = (Math.random() * 100) - (Math.random() * 100);
      final double target_z = (Math.random() * 100) - (Math.random() * 100);

      final VectorI3D origin = new VectorI3D(origin_x, origin_y, origin_z);
      final VectorI3D target = new VectorI3D(target_x, target_y, target_z);

      MatrixM4x4D.lookAtWithContext(
        mc,
        origin,
        target,
        QuaternionI4DTest.AXIS_Y,
        ml);
      final QuaternionI4D lq =
        QuaternionI4D.lookAtWithContext(
          qc,
          origin,
          target,
          QuaternionI4DTest.AXIS_Y);
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

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double w = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final double m = VectorI4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double w = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI4D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D vr = VectorI4D.normalize(v);
    final double m = VectorI4D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(1.0, 0.0, 0.0, 0.0);
    final double m = VectorI4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI4D v = new VectorI4D(8.0, 0.0, 0.0, 0.0);

    {
      final double p = VectorI4D.dotProduct(v, v);
      final double q = VectorI4D.magnitudeSquared(v);
      final double r = VectorI4D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final double m = VectorI4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMakeAxisAngleNormal()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3D axis_r =
        new VectorI3D(Math.random(), Math.random(), Math.random());
      final VectorI3D axis_n = VectorI3D.normalize(axis_r);

      final QuaternionI4D q =
        QuaternionI4D.makeFromAxisAngle(
          axis_n,
          Math.toRadians(Math.random() * 360));

      System.out.println("q            : " + q);
      System.out.println("magnitude(q) : " + QuaternionI4D.magnitude(q));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        QuaternionI4D.magnitude(q),
        1.0));
    }
  }

  @Override @Test public void testMakeAxisAngleX_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getXD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleX_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getXD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeAxisAngleY_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getYD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleY_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getYD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeAxisAngleZ_45()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getZD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleZ_90()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getZD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeFromMatrix3x3Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
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

      MatrixM3x3D.makeRotationInto(angle, axis, m);
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

  @Override @Test public void testMakeFromMatrix4x4Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
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

      MatrixM4x4D.makeRotationInto(angle, axis, m);
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

  @Override @Test public void testMakeMatrix3x3_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix3x3_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix3x3_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix3x3_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM3x3D m = new MatrixM3x3D();

    QuaternionI4D.makeRotationMatrix3x3(q, m);

    Assert.assertTrue(1.0 == m.getRowColumnD(0, 0));
    Assert.assertTrue(0.0 == m.getRowColumnD(0, 1));
    Assert.assertTrue(0.0 == m.getRowColumnD(0, 2));

    Assert.assertTrue(0.0 == m.getRowColumnD(1, 0));
    Assert.assertTrue(1.0 == m.getRowColumnD(1, 1));
    Assert.assertTrue(0.0 == m.getRowColumnD(1, 2));

    Assert.assertTrue(0.0 == m.getRowColumnD(2, 0));
    Assert.assertTrue(0.0 == m.getRowColumnD(2, 1));
    Assert.assertTrue(1.0 == m.getRowColumnD(2, 2));
  }

  @Override @Test public void testMakeMatrix3x3_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix3x3_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix3x3_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0 == m.getRowColumnD(0, 0));
    Assert.assertTrue(0.0 == m.getRowColumnD(0, 1));
    Assert.assertTrue(0.0 == m.getRowColumnD(0, 2));
    Assert.assertTrue(0.0 == m.getRowColumnD(0, 3));

    Assert.assertTrue(0.0 == m.getRowColumnD(1, 0));
    Assert.assertTrue(1.0 == m.getRowColumnD(1, 1));
    Assert.assertTrue(0.0 == m.getRowColumnD(1, 2));
    Assert.assertTrue(0.0 == m.getRowColumnD(1, 3));

    Assert.assertTrue(0.0 == m.getRowColumnD(2, 0));
    Assert.assertTrue(0.0 == m.getRowColumnD(2, 1));
    Assert.assertTrue(1.0 == m.getRowColumnD(2, 2));
    Assert.assertTrue(0.0 == m.getRowColumnD(2, 3));

    Assert.assertTrue(0.0 == m.getRowColumnD(3, 0));
    Assert.assertTrue(0.0 == m.getRowColumnD(3, 1));
    Assert.assertTrue(0.0 == m.getRowColumnD(3, 2));
    Assert.assertTrue(1.0 == m.getRowColumnD(3, 3));
  }

  @Override @Test public void testMakeMatrix4x4_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMakeMatrix4x4_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3DType axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotationInto(radians, axis, mr);
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

  @Override @Test public void testMultiply()
  {
    final ContextRelative context = new ContextRelative();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D qx =
      QuaternionI4D.makeFromAxisAngle(axis_x, Math.toRadians(45));
    final QuaternionI4D qy =
      QuaternionI4D.makeFromAxisAngle(axis_y, Math.toRadians(45));

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

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      qr.getXD(),
      0.3535533905932738));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      qr.getYD(),
      0.3535533905932738));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      qr.getZD(),
      -0.14644660940672624));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      qr.getWD(),
      0.8535533905932737));
  }

  @Override @Test public void testNegation()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 2) - Math.random();
      final double y = (Math.random() * 2) - Math.random();
      final double z = (Math.random() * 2) - Math.random();
      final double w = (Math.random() * 2) - Math.random();
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

  @Override @Test public void testNegationCases()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D qi = new QuaternionI4D(1, 2, 3, 4);
    final QuaternionI4D qnx = new QuaternionI4D(-1, 2, 3, 4);
    final QuaternionI4D qny = new QuaternionI4D(-1, -2, 3, 4);
    final QuaternionI4D qnz = new QuaternionI4D(-1, -2, -3, 4);
    final QuaternionI4D qnw = new QuaternionI4D(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnw) == true);

    final QuaternionI4D qnwn = QuaternionI4D.negate(qnw);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnwn) == false);
    Assert.assertTrue(QuaternionI4D.almostEqual(context, qi, qnwn));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final QuaternionI4D v0 = new QuaternionI4D(8.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vr = QuaternionI4D.normalize(v0);
    final double m = QuaternionI4D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D q = new QuaternionI4D(0, 0, 0, 0);
    final QuaternionI4D qr = QuaternionI4D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getWD()));
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.scale(v, 1.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getYD(),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getZD(),
        vr.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getWD(),
        vr.getWD()));
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getWD(), 0.0));
    }
  }

  @Override @Test public void testString()
  {
    final QuaternionI4D v = new QuaternionI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().equals("[QuaternionI4D 0.0 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final ContextRelative context = new ContextRelative();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getZD(),
        v0.getZD() - v1.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getWD(),
        v0.getWD() - v1.getWD()));
    }
  }
}
