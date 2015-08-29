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

import com.io7m.jaux.ApproximatelyEqualFloat;

public class VectorM2FTest
{
  @SuppressWarnings("static-method") @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.y),
        vr.y));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;

        VectorM2F.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v = new VectorM2F(-1.0f, -1.0f);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);

    final float vx = v.x;
    final float vy = v.y;

    final VectorM2F ov = VectorM2F.absolute(v, out);

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
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);

      {
        VectorM2F.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2F ov0 = VectorM2F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2F ov1 = VectorM2F.addInPlace(v0, v1);

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
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAngleDegrees0()
  {
    final VectorM2F v0 = new VectorM2F(0.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 0.0f);
    final float d = VectorM2F.angleDegrees(v0, v1);

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(d, 90.0f));
  }

  @SuppressWarnings("static-method") @Test public void testApproximatelyEqualTransitive0()
  {
    final float x0 = 0.0f;
    final float x1 = 0.0f;
    final float y0 = 0.0f;
    final float y1 = 0.0f;
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));

    final VectorM2F v0 = new VectorM2F(x0, y0);
    final VectorM2F v1 = new VectorM2F(x1, y1);
    Assert.assertTrue(VectorM2F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testApproximatelyEqualTransitive1()
  {
    final float x0 = 0.0f;
    final float x1 = 1.0f;
    final float y0 = 0.0f;
    final float y1 = 1.0f;
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));

    final VectorM2F v0 = new VectorM2F(x0, y0);
    final VectorM2F v1 = new VectorM2F(x1, y1);
    Assert.assertFalse(VectorM2F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorM2F v = new VectorM2F(3.0f, 5.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
  }

  @SuppressWarnings("static-method") @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F maximum = new VectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F minimum = new VectorM2F(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F minimum = new VectorM2F(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F maximum = new VectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampByVectorInPlace(v, minimum, maximum);
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
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);

      {
        VectorM2F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final VectorM2F vb = new VectorM2F(5, 6);
    final VectorM2F va = new VectorM2F(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2F.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorM2F v0 = new VectorM2F(0.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(0.0f, 0.0f);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      VectorM2F.distance(v0, v1),
      1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      Assert.assertTrue(VectorM2F.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final VectorM2F v0 = new VectorM2F(10.0f, 10.0f);
    final VectorM2F v1 = new VectorM2F(10.0f, 10.0f);

    {
      final float p = VectorM2F.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(v1.x == 10.0f);
      Assert.assertTrue(v1.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0f);
      Assert.assertTrue(v1.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProductOrthonormal()
  {
    final VectorM2F v = new VectorM2F(1.0f, 0.0f);
    Assert.assertTrue(VectorM2F.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2F v0 = new VectorM2F(10.0f, 10.0f);

    {
      final float p = VectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testInitializeReadable()
  {
    final VectorM2F v0 = new VectorM2F(1.0f, 2.0f);
    final VectorM2F v1 = new VectorM2F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @SuppressWarnings("static-method") @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      final VectorM2F vr1 = new VectorM2F();
      VectorM2F.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM2F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.x,
        vr0.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.y,
        vr0.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.y,
        vr1.y));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final float m = VectorM2F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final float x =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float y =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final float m = VectorM2F.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    final VectorM2F vr = VectorM2F.normalizeInPlace(v);
    final float m = VectorM2F.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final VectorM2F v = new VectorM2F(1.0f, 0.0f);
    final float m = VectorM2F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final VectorM2F v = new VectorM2F(8.0f, 0.0f);

    {
      final float p = VectorM2F.dotProduct(v, v);
      final float q = VectorM2F.magnitudeSquared(v);
      final float r = VectorM2F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    final float m = VectorM2F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorM2F v0 = new VectorM2F(8.0f, 0.0f);
    final VectorM2F out = new VectorM2F();
    final VectorM2F vr = VectorM2F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final float m = VectorM2F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM2F p = new VectorM2F(1.0f, 0.0f);
      final VectorM2F q = new VectorM2F(0.0f, 1.0f);
      final VectorM2F r = new VectorM2F();
      final VectorM2F u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2F.magnitude(u) == 0.0);
    }

    {
      final VectorM2F p = new VectorM2F(-1.0f, 0.0f);
      final VectorM2F q = new VectorM2F(0.0f, 1.0f);
      final VectorM2F r = new VectorM2F();
      final VectorM2F u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2F.magnitude(u) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);

    final VectorM2F ov0 = VectorM2F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0f);
    Assert.assertTrue(out.y == 2.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);

    final VectorM2F ov1 = VectorM2F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0f);
    Assert.assertTrue(ov1.y == 2.0f);
    Assert.assertTrue(v0.x == 2.0f);
    Assert.assertTrue(v0.y == 2.0f);
  }

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();

      VectorM2F.scale(v, 1.0f, vr);

      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.x, vr.x));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.y, vr.y));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;

        VectorM2F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          orig_y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();

      VectorM2F.scale(v, 0.0f, vr);

      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.x, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.y, 0.0f));

      {
        VectorM2F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          0.0f));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorM2F v = new VectorM2F(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[VectorM2F 0.0 1.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);

    final VectorM2F ov0 = VectorM2F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);

    final VectorM2F ov1 = VectorM2F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0f);
    Assert.assertTrue(ov1.y == 0.0f);
    Assert.assertTrue(v0.x == 0.0f);
    Assert.assertTrue(v0.y == 0.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);
  }
}
