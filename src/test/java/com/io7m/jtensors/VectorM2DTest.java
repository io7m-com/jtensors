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

public class VectorM2DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.y),
        vr.y));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;

        VectorM2D.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteMutation()
  {
    final VectorM2D out = new VectorM2D();
    final VectorM2D v = new VectorM2D(-1.0, -1.0);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);

    final double vx = v.x;
    final double vy = v.y;

    final VectorM2D ov = VectorM2D.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1.0);
    Assert.assertTrue(out.y == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);

      {
        VectorM2D.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
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

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final double r = Math.random() * Double.MAX_VALUE;

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAngleDegrees0()
  {
    final VectorM2D v0 = new VectorM2D(0.0, 1.0);
    final VectorM2D v1 = new VectorM2D(1.0, 0.0);
    final double d = VectorM2D.angleDegrees(v0, v1);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(d, 90.0));
  }

  @SuppressWarnings("static-method") @Test public void testApproximatelyEqualTransitive0()
  {
    final double x0 = 0.0;
    final double x1 = 0.0;
    final double y0 = 0.0;
    final double y1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));

    final VectorM2D v0 = new VectorM2D(x0, y0);
    final VectorM2D v1 = new VectorM2D(x1, y1);
    Assert.assertTrue(VectorM2D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testApproximatelyEqualTransitive1()
  {
    final double x0 = 0.0;
    final double x1 = 1.0;
    final double y0 = 0.0;
    final double y1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));

    final VectorM2D v0 = new VectorM2D(x0, y0);
    final VectorM2D v1 = new VectorM2D(x1, y1);
    Assert.assertFalse(VectorM2D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorM2D v = new VectorM2D(3.0f, 5.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
  }

  @SuppressWarnings("static-method") @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final VectorM2D vb = new VectorM2D(5, 6);
    final VectorM2D va = new VectorM2D(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2D.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorM2D v0 = new VectorM2D(0.0, 1.0);
    final VectorM2D v1 = new VectorM2D(0.0, 0.0);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorM2D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      Assert.assertTrue(VectorM2D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final VectorM2D v0 = new VectorM2D(10.0, 10.0);
    final VectorM2D v1 = new VectorM2D(10.0, 10.0);

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

  @SuppressWarnings("static-method") @Test public void testDotProductOrthonormal()
  {
    final VectorM2D v = new VectorM2D(1.0f, 0.0f);
    Assert.assertTrue(VectorM2D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSelfMagnitudeSquared()
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

  @SuppressWarnings("static-method") @Test public void testInitializeReadable()
  {
    final VectorM2D v0 = new VectorM2D(1.0f, 2.0f);
    final VectorM2D v1 = new VectorM2D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @SuppressWarnings("static-method") @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.x,
        vr0.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.y,
        vr0.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.y,
        vr1.y));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2D v = new VectorM2D(x, y);

      final double m = VectorM2D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM2D v = new VectorM2D(x, y);

      final VectorM2D vr = new VectorM2D();
      VectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2D.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    final VectorM2D vr = VectorM2D.normalizeInPlace(v);
    final double m = VectorM2D.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final VectorM2D v = new VectorM2D(1.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
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

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final VectorM2D v = new VectorM2D(0.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorM2D v0 = new VectorM2D(8.0, 0.0);
    final VectorM2D out = new VectorM2D();
    final VectorM2D vr = VectorM2D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testProjectionPerpendicularZero()
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

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
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

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
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

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          orig_y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
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

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          0.0));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorM2D v = new VectorM2D(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorM2D 0.0 1.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v0 = new VectorM2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2D v1 = new VectorM2D(x1, y1);

      final VectorM2D vr0 = new VectorM2D();
      VectorM2D.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        VectorM2D.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
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
