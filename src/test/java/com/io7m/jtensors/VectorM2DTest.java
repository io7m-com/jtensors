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
import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jaux.functional.Pair;

public class VectorM2DTest extends VectorM2Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.absolute(v, vr);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;

        VectorM2D.absoluteInPlace(v);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          Math.abs(orig_y),
          v.y));
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final double orig_x = v.x;
      final double orig_y = v.y;

      VectorM2D.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_x),
        v.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_y),
        v.y));

    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.add(v0, v1, vr0);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x + v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y + v1.y));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + v1.y));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2D out = new VectorM2D();
    final VectorM2D v0 = new VectorM2D(1.0, 1.0);
    final VectorM2D v1 = new VectorM2D(1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2D ov0 = VectorM2D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2D ov1 = VectorM2D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final double r = Math.random() * max;

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y
        + (v1.y * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + (v1.x * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + (v1.y * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);
      final VectorM2D v1 = new VectorM2D(x0, y0);
      final VectorM2D v2 = new VectorM2D(x0, y0);

      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D v0 = new VectorM2D(x, y);
      final VectorM2D v1 = new VectorM2D(y, -x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D v0 = new VectorM2D(x, y);
      final VectorM2D v1 = new VectorM2D(-y, x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

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
    final VectorM2D v = new VectorM2D(3.0f, 5.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorM2D maximum = new VectorM2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      final VectorM2D vo = VectorM2D.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);

      {
        final VectorM2D vr0 =
          VectorM2D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorM2D minimum = new VectorM2D(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      final VectorM2D vo = VectorM2D.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2D vr0 =
          VectorM2D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final VectorM2D minimum = new VectorM2D(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final VectorM2D maximum = new VectorM2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      final VectorM2D vo = VectorM2D.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2D vr0 =
          VectorM2D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);

      {
        VectorM2D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2D vb = new VectorM2D(5, 6);
    final VectorM2D va = new VectorM2D(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2D.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(VectorM2D.almostEqual(
      ec,
      new VectorM2D(),
      new VectorM2D(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v0 = new VectorM2D(0.0, 1.0);
    final VectorM2D v1 = new VectorM2D(0.0, 0.0);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM2D.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      Assert.assertTrue(VectorM2D.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2D v0 = new VectorM2D(10.0f, 10.0f);
    final VectorM2D v1 = new VectorM2D(10.0f, 10.0f);

    {
      final double p = VectorM2D.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM2D vpx = new VectorM2D(1.0f, 0.0f);
    final VectorM2D vmx = new VectorM2D(-1.0f, 0.0f);

    final VectorM2D vpy = new VectorM2D(0.0f, 1.0f);
    final VectorM2D vmy = new VectorM2D(0.0f, -1.0f);

    Assert.assertTrue(VectorM2D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM2D.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2D q = new VectorM2D(x, y);
      final double dp = VectorM2D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2D v0 = new VectorM2D(10.0, 10.0);

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2D.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2D m0 = new VectorM2D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2D m0 = new VectorM2D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2D m0 = new VectorM2D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2D m0 = new VectorM2D();
      final VectorM2D m1 = new VectorM2D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2D m0 = new VectorM2D(x, y);
      final VectorM2D m1 = new VectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2D m0 = new VectorM2D();
    final VectorM2D m1 = new VectorM2D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2D m0 = new VectorM2D();
      final VectorM2D m1 = new VectorM2D();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2D m0 = new VectorM2D();
      final VectorM2D m1 = new VectorM2D();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2D v0 = new VectorM2D(1.0f, 2.0f);
    final VectorM2D v1 = new VectorM2D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final VectorM2D vr0 = new VectorM2D();
      final VectorM2D vr1 = new VectorM2D();
      VectorM2D.interpolateLinear(v0, v1, 0.0, vr0);
      VectorM2D.interpolateLinear(v0, v1, 1.0, vr1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, vr0.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, vr0.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.x, vr1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.y, vr1.y));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final double m = VectorM2D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = new VectorM2D(0.0, 0.0);
    final VectorM2D vr = VectorM2D.normalizeInPlace(v);
    final double m = VectorM2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = new VectorM2D(1.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2D v = new VectorM2D(8.0, 0.0);

    {
      final double p = VectorM2D.dotProduct(v, v);
      final double q = VectorM2D.magnitudeSquared(v);
      final double r = VectorM2D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D v = new VectorM2D(0.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM2D v0 = new VectorM2D(8.0, 0.0);
    final VectorM2D out = new VectorM2D();
    final VectorM2D vr = VectorM2D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D qr = new VectorM2D();
    final VectorM2D q = new VectorM2D(0, 0);
    VectorM2D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM2D v0 = new VectorM2D(0, 1);
    final VectorM2D v1 = new VectorM2D(0.5f, 0.5f);

    final Pair<VectorM2D, VectorM2D> r = VectorM2D.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM2D(0, 1), r.first);
    Assert.assertEquals(new VectorM2D(1, 0), r.second);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM2D v0 = new VectorM2D(0f, 1f);
    final VectorM2D v1 = new VectorM2D(0.5f, 0.5f);

    VectorM2D.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM2D(0, 1), v0);
    Assert.assertEquals(new VectorM2D(1, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM2D p = new VectorM2D(1.0, 0.0);
      final VectorM2D q = new VectorM2D(0.0, 1.0);
      final VectorM2D r = new VectorM2D();
      final VectorM2D u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2D.magnitude(u) == 0.0);
    }

    {
      final VectorM2D p = new VectorM2D(-1.0, 0.0);
      final VectorM2D q = new VectorM2D(0.0, 1.0);
      final VectorM2D r = new VectorM2D();
      final VectorM2D u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2D.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2D out = new VectorM2D();
    final VectorM2D v0 = new VectorM2D(1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);

    final VectorM2D ov0 = VectorM2D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);

    final VectorM2D ov1 = VectorM2D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();

      VectorM2D.scale(v, 1.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.x, vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.y, vr.y));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;

        VectorM2D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, orig_y));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();

      VectorM2D.scale(v, 0.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.x, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.y, 0.0));

      {
        VectorM2D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2D v = new VectorM2D(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorM2D 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.subtract(v0, v1, vr0);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x - v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y - v1.y));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          - v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          - v1.y));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2D out = new VectorM2D();
    final VectorM2D v0 = new VectorM2D(1.0, 1.0);
    final VectorM2D v1 = new VectorM2D(1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2D ov0 = VectorM2D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2D ov1 = VectorM2D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0);
    Assert.assertTrue(ov1.y == 0.0);
    Assert.assertTrue(v0.x == 0.0);
    Assert.assertTrue(v0.y == 0.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
  }
}
