/*
 * Copyright © 2012 http://io7m.com
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

public class VectorI3DTTest extends VectorI3TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      final VectorI3DT<A> vr = VectorI3DT.absolute(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getXD()),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getYD()),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getZD()),
        vr.getZD()));
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
      final double z0 = Math.random() * max;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x1, y1, z1);

      final VectorI3DT<A> vr = VectorI3DT.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getZD(),
        v0.getZD() + v1.getZD()));
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
      final double z0 = Math.random() * max;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x1, y1, z1);

      final double r = Math.random() * 100.0;
      final VectorI3DT<A> vr = VectorI3DT.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getZD(),
        v0.getZD() + (v1.getZD() * r)));
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
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, z);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, q, z);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, y, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, y, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, z);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, z);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, z);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, q, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, y, q);
      Assert.assertFalse(VectorI3DT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x0, y0, z0);
      final VectorI3DT<A> v2 = new VectorI3DT<A>(x0, y0, z0);

      Assert.assertTrue(VectorI3DT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI3DT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI3DT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI3DT<A> v = new VectorI3DT<A>(3.0, 5.0, 7.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> maximum = new VectorI3DT<A>(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clampMaximumByVector(v, maximum).getXD() <= maximum
          .getXD());
      Assert
        .assertTrue(VectorI3DT.clampMaximumByVector(v, maximum).getYD() <= maximum
          .getYD());
      Assert
        .assertTrue(VectorI3DT.clampMaximumByVector(v, maximum).getZD() <= maximum
          .getZD());
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> minimum = new VectorI3DT<A>(min_x, min_y, min_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clampMinimumByVector(v, minimum).getXD() >= minimum
          .getXD());
      Assert
        .assertTrue(VectorI3DT.clampMinimumByVector(v, minimum).getYD() >= minimum
          .getYD());
      Assert
        .assertTrue(VectorI3DT.clampMinimumByVector(v, minimum).getZD() >= minimum
          .getZD());
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> minimum = new VectorI3DT<A>(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> maximum = new VectorI3DT<A>(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getXD() <= maximum
          .getXD());
      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getXD() >= minimum
          .getXD());
      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getYD() <= maximum
          .getYD());
      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getYD() >= minimum
          .getYD());
      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getZD() <= maximum
          .getZD());
      Assert
        .assertTrue(VectorI3DT.clampByVector(v, minimum, maximum).getZD() >= minimum
          .getZD());
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clampMaximum(v, maximum).getXD() <= maximum);
      Assert
        .assertTrue(VectorI3DT.clampMaximum(v, maximum).getYD() <= maximum);
      Assert
        .assertTrue(VectorI3DT.clampMaximum(v, maximum).getZD() <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clampMinimum(v, minimum).getXD() >= minimum);
      Assert
        .assertTrue(VectorI3DT.clampMinimum(v, minimum).getYD() >= minimum);
      Assert
        .assertTrue(VectorI3DT.clampMinimum(v, minimum).getZD() >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getXD() <= maximum);
      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getXD() >= minimum);
      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getYD() <= maximum);
      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getYD() >= minimum);
      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getZD() <= maximum);
      Assert
        .assertTrue(VectorI3DT.clamp(v, minimum, maximum).getZD() >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> v1 = new VectorI3DT<A>(v0);
      Assert.assertTrue(VectorI3DT.almostEqual(ec, v0, v1));
    }
  }

  @Override @Test public <A> void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final VectorI3DT<A> v0 =
        VectorI3DT.normalize(new VectorI3DT<A>(x0, y0, z0));

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final VectorI3DT<A> v1 =
        VectorI3DT.normalize(new VectorI3DT<A>(x1, y1, z1));

      final VectorI3DT<A> vr =
        VectorI3DT.normalize(VectorI3DT.crossProduct(v0, v1));

      final double dp0 = VectorI3DT.dotProduct(v0, vr);
      final double dp1 = VectorI3DT.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + VectorI3DT.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + VectorI3DT.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + VectorI3DT.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public <A> void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> v = new VectorI3DT<A>();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> v0 = new VectorI3DT<A>(0.0, 1.0, 0.0);
    final VectorI3DT<A> v1 = new VectorI3DT<A>(0.0, 0.0, 0.0);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorI3DT.distance(v0, v1),
      1.0));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x1, y1, z1);

      Assert.assertTrue(VectorI3DT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI3DT<A> v0 = new VectorI3DT<A>(10.0, 10.0, 10.0);
    final VectorI3DT<A> v1 = new VectorI3DT<A>(10.0, 10.0, 10.0);

    {
      final double p = VectorI3DT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorI3DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorI3DT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorI3DT<A> vpx = new VectorI3DT<A>(1.0f, 0.0f, 0.0f);
    final VectorI3DT<A> vmx = new VectorI3DT<A>(-1.0f, 0.0f, 0.0f);

    final VectorI3DT<A> vpy = new VectorI3DT<A>(0.0f, 1.0f, 0.0f);
    final VectorI3DT<A> vmy = new VectorI3DT<A>(0.0f, -1.0f, 0.0f);

    final VectorI3DT<A> vpz = new VectorI3DT<A>(0.0f, 0.0f, 1.0f);
    final VectorI3DT<A> vmz = new VectorI3DT<A>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorI3DT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI3DT.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI3DT.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI3DT.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final VectorI3DT<A> q = new VectorI3DT<A>(x, y, z);
      final double dp = VectorI3DT.dotProduct(q, q);

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
      final double z = Math.random();
      final VectorI3DT<A> q = new VectorI3DT<A>(x, y, z);

      final double ms = VectorI3DT.magnitudeSquared(q);
      final double dp = VectorI3DT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>();
      final VectorI3DT<A> m1 = new VectorI3DT<A>();
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
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(x, y, z);
      final VectorI3DT<A> m1 = new VectorI3DT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI3DT<A> m0 = new VectorI3DT<A>();
    final VectorI3DT<A> m1 = new VectorI3DT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(23, 0, 0);
      final VectorI3DT<A> m1 = new VectorI3DT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(0, 23, 0);
      final VectorI3DT<A> m1 = new VectorI3DT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3DT<A> m0 = new VectorI3DT<A>(0, 0, 23);
      final VectorI3DT<A> m1 = new VectorI3DT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI3DT<A> v0 = new VectorI3DT<A>(1.0f, 2.0f, 3.0f);
    final VectorI3DT<A> v1 = new VectorI3DT<A>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x1, y1, z1);

      Assert.assertTrue(VectorI3DT.almostEqual(
        ec,
        VectorI3DT.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI3DT.almostEqual(
        ec,
        VectorI3DT.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      final double m = VectorI3DT.magnitude(v);
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
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      final VectorI3DT<A> vr = VectorI3DT.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI3DT.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> v = new VectorI3DT<A>(0.0, 0.0, 0.0);
    final VectorI3DT<A> vr = VectorI3DT.normalize(v);
    final double m = VectorI3DT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> v = new VectorI3DT<A>(1.0, 0.0, 0.0);
    final double m = VectorI3DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI3DT<A> v = new VectorI3DT<A>(8.0, 0.0, 0.0);

    {
      final double p = VectorI3DT.dotProduct(v, v);
      final double q = VectorI3DT.magnitudeSquared(v);
      final double r = VectorI3DT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> v = new VectorI3DT<A>(0.0, 0.0, 0.0);
    final double m = VectorI3DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorI3DT<A> v0 = new VectorI3DT<A>(8.0, 0.0, 0.0);
    final VectorI3DT<A> vr = VectorI3DT.normalize(v0);
    final double m = VectorI3DT.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3DT<A> q = new VectorI3DT<A>(0, 0, 0);
    final VectorI3DT<A> qr = VectorI3DT.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorI3DT<A> v0 = new VectorI3DT<A>(0, 1, 0);
    final VectorI3DT<A> v1 = new VectorI3DT<A>(0.5, 0.5, 0);
    final Pair<VectorI3DT<A>, VectorI3DT<A>> on =
      VectorI3DT.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI3DT<A>(1, 0, 0), on.second);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI3DT<A> p = new VectorI3DT<A>(1.0, 0.0, 0.0);
      final VectorI3DT<A> q = new VectorI3DT<A>(0.0, 1.0, 0.0);
      final VectorI3DT<A> r = VectorI3DT.projection(p, q);
      Assert.assertTrue(VectorI3DT.magnitude(r) == 0.0);
    }

    {
      final VectorI3DT<A> p = new VectorI3DT<A>(-1.0, 0.0, 0.0);
      final VectorI3DT<A> q = new VectorI3DT<A>(0.0, 1.0, 0.0);
      final VectorI3DT<A> r = VectorI3DT.projection(p, q);
      Assert.assertTrue(VectorI3DT.magnitude(r) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      final VectorI3DT<A> vr = VectorI3DT.scale(v, 1.0);

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
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v = new VectorI3DT<A>(x, y, z);

      final VectorI3DT<A> vr = VectorI3DT.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI3DT<A> v = new VectorI3DT<A>(0.0, 1.0, 2.0);
    Assert.assertTrue(v.toString().equals("[VectorI3DT 0.0 1.0 2.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v0 = new VectorI3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3DT<A> v1 = new VectorI3DT<A>(x1, y1, z1);

      final VectorI3DT<A> vr = VectorI3DT.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr.getZD(),
        v0.getZD() - v1.getZD()));
    }
  }
}
