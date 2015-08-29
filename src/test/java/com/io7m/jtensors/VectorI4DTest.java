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

public class VectorI4DTest extends VectorI4Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.absolute(v);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.z), vr.z));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.w), vr.w));
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
      final double w0 = Math.random() * max;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final VectorI4D vr = VectorI4D.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x + v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y + v1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z + v1.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.w, v0.w + v1.w));
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
      final double w0 = Math.random() * max;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final double r = Math.random() * 100.0;
      final VectorI4D vr = VectorI4D.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z
        + (v1.z * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.w, v0.w
        + (v1.w * r)));
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
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, z, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, q, z, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, q, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, z, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, z, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, q, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, z, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, q, w);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, z, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, q, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, q, q, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, q, q);
      Assert.assertFalse(VectorI4D.almostEqual(ec, m0, m1));
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
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);
      final VectorI4D v1 = new VectorI4D(x0, y0, z0, w0);
      final VectorI4D v2 = new VectorI4D(x0, y0, z0, w0);

      Assert.assertTrue(VectorI4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI4D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorI4D v = new VectorI4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
    Assert.assertTrue(v.w == v.getWD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final double max_w = Math.random() * Double.MIN_VALUE;
      final VectorI4D maximum = new VectorI4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).w <= maximum.w);
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final double min_w = Math.random() * Double.MAX_VALUE;
      final VectorI4D minimum = new VectorI4D(min_x, min_y, min_z, min_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).w >= minimum.w);
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final double min_w = Math.random() * Double.MIN_VALUE;
      final VectorI4D minimum = new VectorI4D(min_x, min_y, min_z, min_w);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final double max_w = Math.random() * Double.MAX_VALUE;
      final VectorI4D maximum = new VectorI4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).w <= maximum.w);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).w >= minimum.w);
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).z <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).w <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).z >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).w >= minimum);
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
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).z >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).w <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).w >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);
      final VectorI4D vc = new VectorI4D(v);

      Assert.assertTrue(VectorI4D.almostEqual(ec, v, vc));
    }
  }

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v = new VectorI4D();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.w, 1.0));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D v0 = new VectorI4D(0.0, 1.0, 0.0, 0.0);
    final VectorI4D v1 = new VectorI4D(0.0, 0.0, 0.0, 0.0);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorI4D.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4D.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI4D v0 = new VectorI4D(10.0, 10.0, 10.0, 10.0);
    final VectorI4D v1 = new VectorI4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = VectorI4D.dotProduct(v0, v1);
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
      final double p = VectorI4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorI4D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorI4D vpx = new VectorI4D(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4D vmx = new VectorI4D(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorI4D vpy = new VectorI4D(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4D vmy = new VectorI4D(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorI4D vpz = new VectorI4D(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorI4D vmz = new VectorI4D(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorI4D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI4D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI4D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI4D.dotProduct(vmy, vmz) == 0.0);
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
      final VectorI4D q = new VectorI4D(x, y, z, w);
      final double dp = VectorI4D.dotProduct(q, q);

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
      final VectorI4D q = new VectorI4D(x, y, z, w);

      final double ms = VectorI4D.magnitudeSquared(q);
      final double dp = VectorI4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI4D m0 = new VectorI4D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI4D m0 = new VectorI4D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4D m0 = new VectorI4D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4D m0 = new VectorI4D();
      final VectorI4D m1 = new VectorI4D();
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
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4D m0 = new VectorI4D(x, y, z, w);
      final VectorI4D m1 = new VectorI4D(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI4D m0 = new VectorI4D();
    final VectorI4D m1 = new VectorI4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI4D m0 = new VectorI4D(23, 0, 0, 1);
      final VectorI4D m1 = new VectorI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4D m0 = new VectorI4D(0, 23, 0, 1);
      final VectorI4D m1 = new VectorI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4D m0 = new VectorI4D(0, 0, 23, 1);
      final VectorI4D m1 = new VectorI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4D m0 = new VectorI4D(0, 0, 0, 23);
      final VectorI4D m1 = new VectorI4D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI4D v0 = new VectorI4D(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorI4D v1 = new VectorI4D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4D.almostEqual(
        ec,
        VectorI4D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI4D.almostEqual(
        ec,
        VectorI4D.interpolateLinear(v0, v1, 1.0),
        v1));
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

  @Override @Test public void testNormalizeSimple()
  {
    final VectorI4D v0 = new VectorI4D(8.0, 0.0, 0.0, 0.0);
    final VectorI4D vr = VectorI4D.normalize(v0);
    final double m = VectorI4D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4D q = new VectorI4D(0, 0, 0, 0);
    final VectorI4D qr = VectorI4D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.z));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.w));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorI4D v0 = new VectorI4D(0, 1, 0, 0);
    final VectorI4D v1 = new VectorI4D(0.5, 0.5, 0, 0);
    final Pair<VectorI4D, VectorI4D> on = VectorI4D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI4D(1, 0, 0, 0), on.second);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorI4D p = new VectorI4D(1.0, 0.0, 0.0, 0.0);
      final VectorI4D q = new VectorI4D(0.0, 1.0, 0.0, 0.0);
      final VectorI4D r = VectorI4D.projection(p, q);
      Assert.assertTrue(VectorI4D.magnitude(r) == 0.0);
    }

    {
      final VectorI4D p = new VectorI4D(-1.0, 0.0, 0.0, 0.0);
      final VectorI4D q = new VectorI4D(0.0, 1.0, 0.0, 0.0);
      final VectorI4D r = VectorI4D.projection(p, q);
      Assert.assertTrue(VectorI4D.magnitude(r) == 0.0);
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
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      final VectorI4D vr = VectorI4D.scale(v, 1.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, vr.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, vr.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.w, vr.w));
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

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.w, 0.0));
    }
  }

  @Override @Test public void testString()
  {
    final VectorI4D v = new VectorI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().equals("[VectorI4D 0.0 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final VectorI4D vr = VectorI4D.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x - v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y - v1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z - v1.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.w, v0.w - v1.w));
    }
  }
}
