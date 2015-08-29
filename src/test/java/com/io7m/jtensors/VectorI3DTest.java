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

public class VectorI3DTest extends VectorI3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      final VectorI3D vr = VectorI3D.absolute(v);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.z), vr.z));
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
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final VectorI3D vr = VectorI3D.add(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x + v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y + v1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z + v1.z));
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
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final double r = Math.random() * 100.0;
      final VectorI3D vr = VectorI3D.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z
        + (v1.z * r)));
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
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, z);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, q, z);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, y, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, y, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, z);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, z);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, z);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, q, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, y, q);
      Assert.assertFalse(VectorI3D.almostEqual(ec, m0, m1));
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
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);
      final VectorI3D v1 = new VectorI3D(x0, y0, z0);
      final VectorI3D v2 = new VectorI3D(x0, y0, z0);

      Assert.assertTrue(VectorI3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorI3D v = new VectorI3D(3.0, 5.0, 7.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final VectorI3D maximum = new VectorI3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).z <= maximum.z);
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorI3D minimum = new VectorI3D(min_x, min_y, min_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).z >= minimum.z);
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final VectorI3D minimum = new VectorI3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorI3D maximum = new VectorI3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).z >= minimum.z);
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).z <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).z >= minimum);
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
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).z >= minimum);
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
      final VectorI3D v0 = new VectorI3D(x, y, z);
      final VectorI3D v1 = new VectorI3D(v0);
      Assert.assertTrue(VectorI3D.almostEqual(ec, v0, v1));
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
      final VectorI3D v0 = VectorI3D.normalize(new VectorI3D(x0, y0, z0));

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final VectorI3D v1 = VectorI3D.normalize(new VectorI3D(x1, y1, z1));

      final VectorI3D vr =
        VectorI3D.normalize(VectorI3D.crossProduct(v0, v1));

      final double dp0 = VectorI3D.dotProduct(v0, vr);
      final double dp1 = VectorI3D.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + VectorI3D.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + VectorI3D.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + VectorI3D.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D v = new VectorI3D();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, 0.0));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D v0 = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 0.0, 0.0);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorI3D.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      Assert.assertTrue(VectorI3D.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI3D v0 = new VectorI3D(10.0, 10.0, 10.0);
    final VectorI3D v1 = new VectorI3D(10.0, 10.0, 10.0);

    {
      final double p = VectorI3D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorI3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorI3D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorI3D vpx = new VectorI3D(1.0f, 0.0f, 0.0f);
    final VectorI3D vmx = new VectorI3D(-1.0f, 0.0f, 0.0f);

    final VectorI3D vpy = new VectorI3D(0.0f, 1.0f, 0.0f);
    final VectorI3D vmy = new VectorI3D(0.0f, -1.0f, 0.0f);

    final VectorI3D vpz = new VectorI3D(0.0f, 0.0f, 1.0f);
    final VectorI3D vmz = new VectorI3D(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorI3D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final VectorI3D q = new VectorI3D(x, y, z);
      final double dp = VectorI3D.dotProduct(q, q);

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
      final VectorI3D q = new VectorI3D(x, y, z);

      final double ms = VectorI3D.magnitudeSquared(q);
      final double dp = VectorI3D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI3D m0 = new VectorI3D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI3D m0 = new VectorI3D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3D m0 = new VectorI3D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3D m0 = new VectorI3D();
      final VectorI3D m1 = new VectorI3D();
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
      final VectorI3D m0 = new VectorI3D(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3D m0 = new VectorI3D(x, y, z);
      final VectorI3D m1 = new VectorI3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI3D m0 = new VectorI3D();
    final VectorI3D m1 = new VectorI3D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3D m0 = new VectorI3D(23, 0, 0);
      final VectorI3D m1 = new VectorI3D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3D m0 = new VectorI3D(0, 23, 0);
      final VectorI3D m1 = new VectorI3D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3D m0 = new VectorI3D(0, 0, 23);
      final VectorI3D m1 = new VectorI3D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI3D v0 = new VectorI3D(1.0f, 2.0f, 3.0f);
    final VectorI3D v1 = new VectorI3D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      Assert.assertTrue(VectorI3D.almostEqual(
        ec,
        VectorI3D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI3D.almostEqual(
        ec,
        VectorI3D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorI3D v = new VectorI3D(x, y, z);

      final double m = VectorI3D.magnitude(v);
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
      final VectorI3D v = new VectorI3D(x, y, z);

      final VectorI3D vr = VectorI3D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI3D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D v = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D vr = VectorI3D.normalize(v);
    final double m = VectorI3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D v = new VectorI3D(1.0, 0.0, 0.0);
    final double m = VectorI3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI3D v = new VectorI3D(8.0, 0.0, 0.0);

    {
      final double p = VectorI3D.dotProduct(v, v);
      final double q = VectorI3D.magnitudeSquared(v);
      final double r = VectorI3D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D v = new VectorI3D(0.0, 0.0, 0.0);
    final double m = VectorI3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorI3D v0 = new VectorI3D(8.0, 0.0, 0.0);
    final VectorI3D vr = VectorI3D.normalize(v0);
    final double m = VectorI3D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D q = new VectorI3D(0, 0, 0);
    final VectorI3D qr = VectorI3D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.z));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorI3D v0 = new VectorI3D(0, 1, 0);
    final VectorI3D v1 = new VectorI3D(0.5, 0.5, 0);
    final Pair<VectorI3D, VectorI3D> on = VectorI3D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI3D(1, 0, 0), on.second);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorI3D p = new VectorI3D(1.0, 0.0, 0.0);
      final VectorI3D q = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D r = VectorI3D.projection(p, q);
      Assert.assertTrue(VectorI3D.magnitude(r) == 0.0);
    }

    {
      final VectorI3D p = new VectorI3D(-1.0, 0.0, 0.0);
      final VectorI3D q = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D r = VectorI3D.projection(p, q);
      Assert.assertTrue(VectorI3D.magnitude(r) == 0.0);
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
      final VectorI3D v = new VectorI3D(x, y, z);

      final VectorI3D vr = VectorI3D.scale(v, 1.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, vr.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, vr.z));
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
      final VectorI3D v = new VectorI3D(x, y, z);

      final VectorI3D vr = VectorI3D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, 0.0));
    }
  }

  @Override @Test public void testString()
  {
    final VectorI3D v = new VectorI3D(0.0, 1.0, 2.0);
    Assert.assertTrue(v.toString().equals("[VectorI3D 0.0 1.0 2.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final VectorI3D vr = VectorI3D.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, v0.x - v1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, v0.y - v1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, v0.z - v1.z));
    }
  }
}
