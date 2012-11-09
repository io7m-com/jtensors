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

import com.io7m.jaux.ApproximatelyEqualDouble;

public class VectorI2DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MIN_VALUE);

      Assert.assertTrue(VectorI2D.absolute(v).x >= 0.0);
      Assert.assertTrue(VectorI2D.absolute(v).y >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v0 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v1 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D vr = VectorI2D.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + v1.y));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v0 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v1 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final double r = Math.random() * Double.MAX_VALUE;
      final VectorI2D vr = VectorI2D.addScaled(v0, v1, r);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + (v1.y * r)));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAngleDegrees0()
  {
    final VectorI2D v0 = new VectorI2D(0.0, 1.0);
    final VectorI2D v1 = new VectorI2D(1.0, 0.0);
    final double d = VectorI2D.angleDegrees(v0, v1);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(d, 90.0));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final double x0 = 0.0;
    final double x1 = 0.0;
    final double y0 = 0.0;
    final double y1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));

    final VectorI2D v0 = new VectorI2D(x0, y0);
    final VectorI2D v1 = new VectorI2D(x1, y1);
    Assert.assertTrue(VectorI2D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final double x0 = 0.0;
    final double x1 = 1.0;
    final double y0 = 0.0;
    final double y1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));

    final VectorI2D v0 = new VectorI2D(x0, y0);
    final VectorI2D v1 = new VectorI2D(x1, y1);
    Assert.assertFalse(VectorI2D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI2D v = new VectorI2D(3.0, 5.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorI2D maximum = new VectorI2D(max_x, max_y);
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MIN_VALUE);

      Assert
        .assertTrue(VectorI2D.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI2D.clampMaximumByVector(v, maximum).y <= maximum.y);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorI2D minimum = new VectorI2D(min_x, min_y);
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MIN_VALUE);

      Assert
        .assertTrue(VectorI2D.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI2D.clampMinimumByVector(v, minimum).y >= minimum.y);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D minimum =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MIN_VALUE);
      final VectorI2D maximum =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MAX_VALUE);

      Assert
        .assertTrue(VectorI2D.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI2D.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI2D.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI2D.clampByVector(v, minimum, maximum).y >= minimum.y);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);

      Assert.assertTrue(VectorI2D.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI2D.clampMaximum(v, maximum).y <= maximum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MIN_VALUE);

      Assert.assertTrue(VectorI2D.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI2D.clampMinimum(v, minimum).y >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MIN_VALUE, Math.random()
          * Double.MAX_VALUE);

      Assert.assertTrue(VectorI2D.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI2D.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI2D.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI2D.clamp(v, minimum, maximum).y >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorI2D v0 = new VectorI2D(0.0, 1.0);
    final VectorI2D v1 = new VectorI2D(0.0, 0.0);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorI2D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v0 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v1 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);

      Assert.assertTrue(VectorI2D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorI2D v = new VectorI2D(1.0, 0.0);
    Assert.assertTrue(VectorI2D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorI2D m0 = new VectorI2D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorI2D m0 = new VectorI2D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorI2D m0 = new VectorI2D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorI2D m0 = new VectorI2D();
    final VectorI2D m1 = new VectorI2D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final VectorI2D v0 = new VectorI2D(0.0, 0.0);
    final VectorI2D v1 = new VectorI2D(0.0, 0.0);
    final VectorI2D vy = new VectorI2D(0.0, 1.0);
    final VectorI2D vx = new VectorI2D(1.0, 0.0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final VectorI2D v0 = new VectorI2D(0.0, 0.0);
    final VectorI2D v1 = new VectorI2D(0.0, 0.0);
    final VectorI2D vy = new VectorI2D(0.0, 1.0);
    final VectorI2D vx = new VectorI2D(1.0, 0.0);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorI2D v0 = new VectorI2D(1.0f, 2.0f);
    final VectorI2D v1 = new VectorI2D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v0 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v1 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);

      Assert.assertTrue(VectorI2D.approximatelyEqual(
        VectorI2D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI2D.approximatelyEqual(
        VectorI2D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      ApproximatelyEqualDouble.approximatelyEqual(
        VectorI2D.magnitude(VectorI2D.normalize(v)),
        1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testNoargZero()
  {
    final VectorI2D v = new VectorI2D();
    VectorI2D.approximatelyEqual(v, VectorI2D.ZERO);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorI2D v0 = new VectorI2D(8.0, 0.0);
    final VectorI2D vr = VectorI2D.normalize(v0);
    final double m = VectorI2D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final VectorI2D v0 = new VectorI2D(0.0, 0.0);
    VectorI2D.approximatelyEqual(VectorI2D.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorI2D p = new VectorI2D(1.0, 0.0);
      final VectorI2D q = new VectorI2D(0.0, 1.0);
      final VectorI2D r = VectorI2D.projection(p, q);
      Assert.assertTrue(VectorI2D.magnitude(r) == 0.0);
    }

    {
      final VectorI2D p = new VectorI2D(-1.0, 0.0);
      final VectorI2D q = new VectorI2D(0.0, 1.0);
      final VectorI2D r = VectorI2D.projection(p, q);
      Assert.assertTrue(VectorI2D.magnitude(r) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI2D v = new VectorI2D(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorI2D 0.0 1.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final VectorI2D v0 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D v1 =
        new VectorI2D(Math.random() * Double.MAX_VALUE, Math.random()
          * Double.MAX_VALUE);
      final VectorI2D vr = VectorI2D.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y - v1.y));
    }
  }
}
