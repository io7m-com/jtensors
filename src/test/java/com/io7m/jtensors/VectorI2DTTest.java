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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.functional.Pair;

public class VectorI2DTTest extends VectorI2TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      final VectorI2DT<A> vr = VectorI2DT.absolute(v);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));
    }
  }

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x1, y1);

      final VectorI2DT<A> vr = VectorI2DT.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x + v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y + v1.y));
    }
  }

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x1, y1);

      final double r = Math.random() * 100.0;
      final VectorI2DT<A> vr = VectorI2DT.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y
        + (v1.y * r)));
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double q = z + 1.0;

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, y);
      Assert.assertFalse(VectorI2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(x, q);
      Assert.assertFalse(VectorI2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(VectorI2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, y);
      Assert.assertFalse(VectorI2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(VectorI2DT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x0, y0);
      final VectorI2DT<A> v2 = new VectorI2DT<A>(x0, y0);

      Assert.assertTrue(VectorI2DT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI2DT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI2DT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = 1.0 + Math.random();
      final double y = 1.0 + Math.random();
      final VectorI2DT<A> v0 = VectorI2DT.normalize(new VectorI2DT<A>(x, y));
      final VectorI2DT<A> v1 = VectorI2DT.normalize(new VectorI2DT<A>(y, -x));
      final double angle = VectorI2DT.angle(v0, v1);

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
      final VectorI2DT<A> v0 = VectorI2DT.normalize(new VectorI2DT<A>(x, y));
      final VectorI2DT<A> v1 = VectorI2DT.normalize(new VectorI2DT<A>(-y, x));
      final double angle = VectorI2DT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI2DT<A> v = new VectorI2DT<A>(3.0, 5.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> maximum = new VectorI2DT<A>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert
        .assertTrue(VectorI2DT.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI2DT.clampMaximumByVector(v, maximum).y <= maximum.y);
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> minimum = new VectorI2DT<A>(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert
        .assertTrue(VectorI2DT.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI2DT.clampMinimumByVector(v, minimum).y >= minimum.y);
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> minimum = new VectorI2DT<A>(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> maximum = new VectorI2DT<A>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert
        .assertTrue(VectorI2DT.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI2DT.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI2DT.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI2DT.clampByVector(v, minimum, maximum).y >= minimum.y);
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert.assertTrue(VectorI2DT.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI2DT.clampMaximum(v, maximum).y <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert.assertTrue(VectorI2DT.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI2DT.clampMinimum(v, minimum).y >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      Assert.assertTrue(VectorI2DT.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI2DT.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI2DT.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI2DT.clamp(v, minimum, maximum).y >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();

      final VectorI2DT<A> v0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> v1 = new VectorI2DT<A>(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.x, x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.y, y));
    }
  }

  @Override @Test public <A> void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> v = new VectorI2DT<A>();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> v0 = new VectorI2DT<A>(0.0, 1.0);
    final VectorI2DT<A> v1 = new VectorI2DT<A>(0.0, 0.0);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorI2DT.distance(v0, v1),
      1.0));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x1, y1);

      Assert.assertTrue(VectorI2DT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI2DT<A> v0 = new VectorI2DT<A>(10.0, 10.0);
    final VectorI2DT<A> v1 = new VectorI2DT<A>(10.0, 10.0);

    {
      final double p = VectorI2DT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorI2DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorI2DT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorI2DT<A> vpx = new VectorI2DT<A>(1.0f, 0.0f);
    final VectorI2DT<A> vmx = new VectorI2DT<A>(-1.0f, 0.0f);

    final VectorI2DT<A> vpy = new VectorI2DT<A>(0.0f, 1.0f);
    final VectorI2DT<A> vmy = new VectorI2DT<A>(0.0f, -1.0f);

    Assert.assertTrue(VectorI2DT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI2DT.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorI2DT<A> q = new VectorI2DT<A>(x, y);
      final double dp = VectorI2DT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorI2DT<A> q = new VectorI2DT<A>(x, y);

      final double ms = VectorI2DT.magnitudeSquared(q);
      final double dp = VectorI2DT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>();
      final VectorI2DT<A> m1 = new VectorI2DT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(x, y);
      final VectorI2DT<A> m1 = new VectorI2DT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI2DT<A> m0 = new VectorI2DT<A>();
    final VectorI2DT<A> m1 = new VectorI2DT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(23, 0);
      final VectorI2DT<A> m1 = new VectorI2DT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2DT<A> m0 = new VectorI2DT<A>(0, 23);
      final VectorI2DT<A> m1 = new VectorI2DT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI2DT<A> v0 = new VectorI2DT<A>(1.0f, 2.0f);
    final VectorI2DT<A> v1 = new VectorI2DT<A>(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x1, y1);

      Assert.assertTrue(VectorI2DT.almostEqual(
        ec,
        VectorI2DT.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI2DT.almostEqual(
        ec,
        VectorI2DT.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      final double m = VectorI2DT.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      final VectorI2DT<A> vr = VectorI2DT.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI2DT.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> v = new VectorI2DT<A>(0.0, 0.0);
    final VectorI2DT<A> vr = VectorI2DT.normalize(v);
    final double m = VectorI2DT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> v = new VectorI2DT<A>(1.0, 0.0);
    final double m = VectorI2DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI2DT<A> v = new VectorI2DT<A>(8.0, 0.0);

    {
      final double p = VectorI2DT.dotProduct(v, v);
      final double q = VectorI2DT.magnitudeSquared(v);
      final double r = VectorI2DT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> v = new VectorI2DT<A>(0.0, 0.0);
    final double m = VectorI2DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorI2DT<A> v0 = new VectorI2DT<A>(8.0, 0.0);
    final VectorI2DT<A> vr = VectorI2DT.normalize(v0);
    final double m = VectorI2DT.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2DT<A> q = new VectorI2DT<A>(0, 0);
    final VectorI2DT<A> qr = VectorI2DT.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorI2DT<A> v0 = new VectorI2DT<A>(0, 1);
    final VectorI2DT<A> v1 = new VectorI2DT<A>(0.5, 0.5);
    final Pair<VectorI2DT<A>, VectorI2DT<A>> on =
      VectorI2DT.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI2DT<A>(1, 0), on.second);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI2DT<A> p = new VectorI2DT<A>(1.0, 0.0);
      final VectorI2DT<A> q = new VectorI2DT<A>(0.0, 1.0);
      final VectorI2DT<A> r = VectorI2DT.projection(p, q);
      Assert.assertTrue(VectorI2DT.magnitude(r) == 0.0);
    }

    {
      final VectorI2DT<A> p = new VectorI2DT<A>(-1.0, 0.0);
      final VectorI2DT<A> q = new VectorI2DT<A>(0.0, 1.0);
      final VectorI2DT<A> r = VectorI2DT.projection(p, q);
      Assert.assertTrue(VectorI2DT.magnitude(r) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      final VectorI2DT<A> vr = VectorI2DT.scale(v, 1.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, vr.y));
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v = new VectorI2DT<A>(x, y);

      final VectorI2DT<A> vr = VectorI2DT.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, 0.0));
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI2DT<A> v = new VectorI2DT<A>(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorI2DT 0.0 1.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v0 = new VectorI2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorI2DT<A> v1 = new VectorI2DT<A>(x1, y1);

      final VectorI2DT<A> vr = VectorI2DT.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x - v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y - v1.y));
    }
  }
}
