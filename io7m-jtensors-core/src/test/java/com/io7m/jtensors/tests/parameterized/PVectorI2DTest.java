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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.parameterized.PVectorI2D;
import com.io7m.jtensors.tests.TestUtilities;

@SuppressWarnings("static-method") public class PVectorI2DTest<T> extends
  PVectorI2Contract
{
  @Test public void testZero()
  {
    final PVectorI2D<Object> z = PVectorI2D.zero();
    Assert.assertEquals(0.0, z.getXD(), 0.0);
    Assert.assertEquals(0.0, z.getYD(), 0.0);
  }

  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      final PVectorI2D<T> vr = PVectorI2D.absolute(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getXD()),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getYD()),
        vr.getYD()));
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x1, y1);

      final PVectorI2D<T> vr = PVectorI2D.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() + v1.getYD()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x1, y1);

      final double r = Math.random() * 100.0;
      final PVectorI2D<T> vr = PVectorI2D.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() + (v1.getYD() * r)));
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double q = z + 1.0;

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, y);
      Assert.assertFalse(PVectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(x, q);
      Assert.assertFalse(PVectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(PVectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, y);
      Assert.assertFalse(PVectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(PVectorI2D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x0, y0);
      final PVectorI2D<T> v2 = new PVectorI2D<T>(x0, y0);

      Assert.assertTrue(PVectorI2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorI2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorI2D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = 1.0 + Math.random();
      final double y = 1.0 + Math.random();
      final PVectorI2D<T> v0 = PVectorI2D.normalize(new PVectorI2D<T>(x, y));
      final PVectorI2D<T> v1 = PVectorI2D.normalize(new PVectorI2D<T>(y, -x));
      final double angle = PVectorI2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final double x = 1.0 + Math.random();
      final double y = 1.0 + Math.random();
      final PVectorI2D<T> v0 = PVectorI2D.normalize(new PVectorI2D<T>(x, y));
      final PVectorI2D<T> v1 = PVectorI2D.normalize(new PVectorI2D<T>(-y, x));
      final double angle = PVectorI2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorI2D<T> v = new PVectorI2D<T>(3.0, 5.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> maximum = new PVectorI2D<T>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert
        .assertTrue(PVectorI2D.clampMaximumByPVector(v, maximum).getXD() <= maximum
          .getXD());
      Assert
        .assertTrue(PVectorI2D.clampMaximumByPVector(v, maximum).getYD() <= maximum
          .getYD());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> minimum = new PVectorI2D<T>(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert
        .assertTrue(PVectorI2D.clampMinimumByPVector(v, minimum).getXD() >= minimum
          .getXD());
      Assert
        .assertTrue(PVectorI2D.clampMinimumByPVector(v, minimum).getYD() >= minimum
          .getYD());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> minimum = new PVectorI2D<T>(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> maximum = new PVectorI2D<T>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert.assertTrue(PVectorI2D
        .clampByPVector(v, minimum, maximum)
        .getXD() <= maximum.getXD());
      Assert.assertTrue(PVectorI2D
        .clampByPVector(v, minimum, maximum)
        .getXD() >= minimum.getXD());
      Assert.assertTrue(PVectorI2D
        .clampByPVector(v, minimum, maximum)
        .getYD() <= maximum.getYD());
      Assert.assertTrue(PVectorI2D
        .clampByPVector(v, minimum, maximum)
        .getYD() >= minimum.getYD());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert
        .assertTrue(PVectorI2D.clampMaximum(v, maximum).getXD() <= maximum);
      Assert
        .assertTrue(PVectorI2D.clampMaximum(v, maximum).getYD() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert
        .assertTrue(PVectorI2D.clampMinimum(v, minimum).getXD() >= minimum);
      Assert
        .assertTrue(PVectorI2D.clampMinimum(v, minimum).getYD() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      Assert
        .assertTrue(PVectorI2D.clamp(v, minimum, maximum).getXD() <= maximum);
      Assert
        .assertTrue(PVectorI2D.clamp(v, minimum, maximum).getXD() >= minimum);
      Assert
        .assertTrue(PVectorI2D.clamp(v, minimum, maximum).getYD() <= maximum);
      Assert
        .assertTrue(PVectorI2D.clamp(v, minimum, maximum).getYD() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();

      final PVectorI2D<T> v0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> v1 = new PVectorI2D<T>(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getXD(), x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getYD(), y));
    }
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> v = new PVectorI2D<T>();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> v0 = new PVectorI2D<T>(0.0, 1.0);
    final PVectorI2D<T> v1 = new PVectorI2D<T>(0.0, 0.0);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      PVectorI2D.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x1, y1);

      Assert.assertTrue(PVectorI2D.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI2D<T> v0 = new PVectorI2D<T>(10.0, 10.0);
    final PVectorI2D<T> v1 = new PVectorI2D<T>(10.0, 10.0);

    {
      final double p = PVectorI2D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = PVectorI2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = PVectorI2D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorI2D<T> vpx = new PVectorI2D<T>(1.0f, 0.0f);
    final PVectorI2D<T> vmx = new PVectorI2D<T>(-1.0f, 0.0f);

    final PVectorI2D<T> vpy = new PVectorI2D<T>(0.0f, 1.0f);
    final PVectorI2D<T> vmy = new PVectorI2D<T>(0.0f, -1.0f);

    Assert.assertTrue(PVectorI2D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorI2D.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final PVectorI2D<T> q = new PVectorI2D<T>(x, y);
      final double dp = PVectorI2D.dotProduct(q, q);

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
      final PVectorI2D<T> q = new PVectorI2D<T>(x, y);

      final double ms = PVectorI2D.magnitudeSquared(q);
      final double dp = PVectorI2D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>();
      final PVectorI2D<T> m1 = new PVectorI2D<T>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(x, y);
      final PVectorI2D<T> m1 = new PVectorI2D<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI2D<T> m0 = new PVectorI2D<T>();
    final PVectorI2D<T> m1 = new PVectorI2D<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(23, 0);
      final PVectorI2D<T> m1 = new PVectorI2D<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI2D<T> m0 = new PVectorI2D<T>(0, 23);
      final PVectorI2D<T> m1 = new PVectorI2D<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI2D<T> v0 = new PVectorI2D<T>(1.0f, 2.0f);
    final PVectorI2D<T> v1 = new PVectorI2D<T>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x1, y1);

      Assert.assertTrue(PVectorI2D.almostEqual(
        ec,
        PVectorI2D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(PVectorI2D.almostEqual(
        ec,
        PVectorI2D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      final double m = PVectorI2D.magnitude(v);
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
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      final PVectorI2D<T> vr = PVectorI2D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = PVectorI2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> v = new PVectorI2D<T>(0.0, 0.0);
    final PVectorI2D<T> vr = PVectorI2D.normalize(v);
    final double m = PVectorI2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> v = new PVectorI2D<T>(1.0, 0.0);
    final double m = PVectorI2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI2D<T> v = new PVectorI2D<T>(8.0, 0.0);

    {
      final double p = PVectorI2D.dotProduct(v, v);
      final double q = PVectorI2D.magnitudeSquared(v);
      final double r = PVectorI2D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> v = new PVectorI2D<T>(0.0, 0.0);
    final double m = PVectorI2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorI2D<T> v0 = new PVectorI2D<T>(8.0, 0.0);
    final PVectorI2D<T> vr = PVectorI2D.normalize(v0);
    final double m = PVectorI2D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2D<T> q = new PVectorI2D<T>(0, 0);
    final PVectorI2D<T> qr = PVectorI2D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorI2D<T> v0 = new PVectorI2D<T>(0, 1);
    final PVectorI2D<T> v1 = new PVectorI2D<T>(0.5, 0.5);
    final Pair<PVectorI2D<T>, PVectorI2D<T>> on =
      PVectorI2D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new PVectorI2D<T>(1, 0), on.getRight());
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorI2D<T> p = new PVectorI2D<T>(1.0, 0.0);
      final PVectorI2D<T> q = new PVectorI2D<T>(0.0, 1.0);
      final PVectorI2D<T> r = PVectorI2D.projection(p, q);
      Assert.assertTrue(PVectorI2D.magnitude(r) == 0.0);
    }

    {
      final PVectorI2D<T> p = new PVectorI2D<T>(-1.0, 0.0);
      final PVectorI2D<T> q = new PVectorI2D<T>(0.0, 1.0);
      final PVectorI2D<T> r = PVectorI2D.projection(p, q);
      Assert.assertTrue(PVectorI2D.magnitude(r) == 0.0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      final PVectorI2D<T> vr = PVectorI2D.scale(v, 1.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getYD(),
        vr.getYD()));
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v = new PVectorI2D<T>(x, y);

      final PVectorI2D<T> vr = PVectorI2D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI2D<T> v = new PVectorI2D<T>(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[PVectorI2D 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v0 = new PVectorI2D<T>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final PVectorI2D<T> v1 = new PVectorI2D<T>(x1, y1);

      final PVectorI2D<T> vr = PVectorI2D.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() - v1.getYD()));
    }
  }
}
