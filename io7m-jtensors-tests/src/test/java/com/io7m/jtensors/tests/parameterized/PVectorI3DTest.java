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
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.parameterized.PVectorI3D;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI3DTest<T>
  extends PVectorI3Contract
{
  @Test public void testZero()
  {
    final PVectorI3D<Object> z = PVectorI3D.zero();
    Assert.assertEquals(0.0, z.getXD(), 0.0);
    Assert.assertEquals(0.0, z.getYD(), 0.0);
    Assert.assertEquals(0.0, z.getZD(), 0.0);
  }

  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      final PVectorI3D<T> vr = PVectorI3D.absolute(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getZD()), vr.getZD()));
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
      final double z0 = Math.random() * max;
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x1, y1, z1);

      final PVectorI3D<T> vr = PVectorI3D.add(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getZD(), v0.getZD() + v1.getZD()));
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
      final double z0 = Math.random() * max;
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x1, y1, z1);

      final double r = Math.random() * 100.0;
      final PVectorI3D<T> vr = PVectorI3D.addScaled(v0, v1, r);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getZD(), v0.getZD() + (v1.getZD() * r)));
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
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, z);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, q, z);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, y, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, y, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, z);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, z);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, z);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, q, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, y, q);
      Assert.assertFalse(PVectorI3D.almostEqual(ec, m0, m1));
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
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x0, y0, z0);
      final PVectorI3D<T> v2 = new PVectorI3D<T>(x0, y0, z0);

      Assert.assertTrue(PVectorI3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorI3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorI3D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorI3D<T> v = new PVectorI3D<T>(3.0, 5.0, 7.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> maximum = new PVectorI3D<T>(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(
        PVectorI3D.clampMaximumByPVector(v, maximum).getXD()
        <= maximum.getXD());
      Assert.assertTrue(
        PVectorI3D.clampMaximumByPVector(v, maximum).getYD()
        <= maximum.getYD());
      Assert.assertTrue(
        PVectorI3D.clampMaximumByPVector(v, maximum).getZD()
        <= maximum.getZD());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> minimum = new PVectorI3D<T>(min_x, min_y, min_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(
        PVectorI3D.clampMinimumByPVector(v, minimum).getXD()
        >= minimum.getXD());
      Assert.assertTrue(
        PVectorI3D.clampMinimumByPVector(v, minimum).getYD()
        >= minimum.getYD());
      Assert.assertTrue(
        PVectorI3D.clampMinimumByPVector(v, minimum).getZD()
        >= minimum.getZD());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> minimum = new PVectorI3D<T>(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> maximum = new PVectorI3D<T>(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getXD()
        <= maximum.getXD());
      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getXD()
        >= minimum.getXD());
      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getYD()
        <= maximum.getYD());
      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getYD()
        >= minimum.getYD());
      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getZD()
        <= maximum.getZD());
      Assert.assertTrue(
        PVectorI3D.clampByPVector(v, minimum, maximum).getZD()
        >= minimum.getZD());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(PVectorI3D.clampMaximum(v, maximum).getXD() <= maximum);
      Assert.assertTrue(PVectorI3D.clampMaximum(v, maximum).getYD() <= maximum);
      Assert.assertTrue(PVectorI3D.clampMaximum(v, maximum).getZD() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(PVectorI3D.clampMinimum(v, minimum).getXD() >= minimum);
      Assert.assertTrue(PVectorI3D.clampMinimum(v, minimum).getYD() >= minimum);
      Assert.assertTrue(PVectorI3D.clampMinimum(v, minimum).getZD() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getXD() <= maximum);
      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getXD() >= minimum);
      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getYD() <= maximum);
      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getYD() >= minimum);
      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getZD() <= maximum);
      Assert.assertTrue(
        PVectorI3D.clamp(v, minimum, maximum).getZD() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> v1 = new PVectorI3D<T>(v0);
      Assert.assertTrue(PVectorI3D.almostEqual(ec, v0, v1));
    }
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final PVectorI3D<T> v0 =
        PVectorI3D.normalize(new PVectorI3D<T>(x0, y0, z0));

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final PVectorI3D<T> v1 =
        PVectorI3D.normalize(new PVectorI3D<T>(x1, y1, z1));

      final PVectorI3D<T> vr =
        PVectorI3D.normalize(PVectorI3D.crossProduct(v0, v1));

      final double dp0 = PVectorI3D.dotProduct(v0, vr);
      final double dp1 = PVectorI3D.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> v = new PVectorI3D<T>();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> v0 = new PVectorI3D<T>(0.0, 1.0, 0.0);
    final PVectorI3D<T> v1 = new PVectorI3D<T>(0.0, 0.0, 0.0);

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorI3D.distance(v0, v1), 1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x1, y1, z1);

      Assert.assertTrue(PVectorI3D.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI3D<T> v0 = new PVectorI3D<T>(10.0, 10.0, 10.0);
    final PVectorI3D<T> v1 = new PVectorI3D<T>(10.0, 10.0, 10.0);

    {
      final double p = PVectorI3D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorI3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorI3D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorI3D<T> vpx = new PVectorI3D<T>(1.0f, 0.0f, 0.0f);
    final PVectorI3D<T> vmx = new PVectorI3D<T>(-1.0f, 0.0f, 0.0f);

    final PVectorI3D<T> vpy = new PVectorI3D<T>(0.0f, 1.0f, 0.0f);
    final PVectorI3D<T> vmy = new PVectorI3D<T>(0.0f, -1.0f, 0.0f);

    final PVectorI3D<T> vpz = new PVectorI3D<T>(0.0f, 0.0f, 1.0f);
    final PVectorI3D<T> vmz = new PVectorI3D<T>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(PVectorI3D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorI3D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(PVectorI3D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(PVectorI3D.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final PVectorI3D<T> q = new PVectorI3D<T>(x, y, z);
      final double dp = PVectorI3D.dotProduct(q, q);

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
      final PVectorI3D<T> q = new PVectorI3D<T>(x, y, z);

      final double ms = PVectorI3D.magnitudeSquared(q);
      final double dp = PVectorI3D.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>();
      final PVectorI3D<T> m1 = new PVectorI3D<T>();
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
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(x, y, z);
      final PVectorI3D<T> m1 = new PVectorI3D<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI3D<T> m0 = new PVectorI3D<T>();
    final PVectorI3D<T> m1 = new PVectorI3D<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(23, 0, 0);
      final PVectorI3D<T> m1 = new PVectorI3D<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(0, 23, 0);
      final PVectorI3D<T> m1 = new PVectorI3D<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3D<T> m0 = new PVectorI3D<T>(0, 0, 23);
      final PVectorI3D<T> m1 = new PVectorI3D<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI3D<T> v0 = new PVectorI3D<T>(1.0f, 2.0f, 3.0f);
    final PVectorI3D<T> v1 = new PVectorI3D<T>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x1, y1, z1);

      Assert.assertTrue(
        PVectorI3D.almostEqual(
          ec, PVectorI3D.interpolateLinear(v0, v1, 0.0), v0));
      Assert.assertTrue(
        PVectorI3D.almostEqual(
          ec, PVectorI3D.interpolateLinear(v0, v1, 1.0), v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      final double m = PVectorI3D.magnitude(v);
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
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      final PVectorI3D<T> vr = PVectorI3D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = PVectorI3D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> v = new PVectorI3D<T>(0.0, 0.0, 0.0);
    final PVectorI3D<T> vr = PVectorI3D.normalize(v);
    final double m = PVectorI3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> v = new PVectorI3D<T>(1.0, 0.0, 0.0);
    final double m = PVectorI3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI3D<T> v = new PVectorI3D<T>(8.0, 0.0, 0.0);

    {
      final double p = PVectorI3D.dotProduct(v, v);
      final double q = PVectorI3D.magnitudeSquared(v);
      final double r = PVectorI3D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> v = new PVectorI3D<T>(0.0, 0.0, 0.0);
    final double m = PVectorI3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorI3D<T> v0 = new PVectorI3D<T>(8.0, 0.0, 0.0);
    final PVectorI3D<T> vr = PVectorI3D.normalize(v0);
    final double m = PVectorI3D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3D<T> q = new PVectorI3D<T>(0, 0, 0);
    final PVectorI3D<T> qr = PVectorI3D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorI3D<T> v0 = new PVectorI3D<T>(0, 1, 0);
    final PVectorI3D<T> v1 = new PVectorI3D<T>(0.5, 0.5, 0);
    final Pair<PVectorI3D<T>, PVectorI3D<T>> on =
      PVectorI3D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new PVectorI3D<T>(1, 0, 0), on.getRight());
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorI3D<T> p = new PVectorI3D<T>(1.0, 0.0, 0.0);
      final PVectorI3D<T> q = new PVectorI3D<T>(0.0, 1.0, 0.0);
      final PVectorI3D<T> r = PVectorI3D.projection(p, q);
      Assert.assertTrue(PVectorI3D.magnitude(r) == 0.0);
    }

    {
      final PVectorI3D<T> p = new PVectorI3D<T>(-1.0, 0.0, 0.0);
      final PVectorI3D<T> q = new PVectorI3D<T>(0.0, 1.0, 0.0);
      final PVectorI3D<T> r = PVectorI3D.projection(p, q);
      Assert.assertTrue(PVectorI3D.magnitude(r) == 0.0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      final PVectorI3D<T> vr = PVectorI3D.scale(v, 1.0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getYD(), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getZD(), vr.getZD()));
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
      final PVectorI3D<T> v = new PVectorI3D<T>(x, y, z);

      final PVectorI3D<T> vr = PVectorI3D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI3D<T> v = new PVectorI3D<T>(0.0, 1.0, 2.0);
    Assert.assertTrue(v.toString().equals("[PVectorI3D 0.0 1.0 2.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v0 = new PVectorI3D<T>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorI3D<T> v1 = new PVectorI3D<T>(x1, y1, z1);

      final PVectorI3D<T> vr = PVectorI3D.subtract(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getZD(), v0.getZD() - v1.getZD()));
    }
  }
}
