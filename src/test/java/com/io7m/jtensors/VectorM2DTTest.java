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
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorM2DTTest extends VectorM2TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      VectorM2DT.absolute(v, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getXD()),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getYD()),
        vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2DT.absoluteInPlace(v);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          Math.abs(orig_x),
          v.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          Math.abs(orig_y),
          v.getYD()));
      }
    }
  }

  @Override @Test public <A> void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();

      VectorM2DT.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_x),
        v.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_y),
        v.getYD()));

    }
  }

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x1, y1);

      final VectorM2DT<A> vr0 = new VectorM2DT<A>();
      VectorM2DT.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() + v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2DT.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x + v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y + v1.getYD()));
      }
    }
  }

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM2DT<A> out = new VectorM2DT<A>();
    final VectorM2DT<A> v0 = new VectorM2DT<A>(1.0, 1.0);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2DT<A> ov0 = VectorM2DT.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2DT<A> ov1 = VectorM2DT.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
  }

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x1, y1);

      final double r = Math.random() * max;

      final VectorM2DT<A> vr0 = new VectorM2DT<A>();
      VectorM2DT.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() + (v1.getYD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2DT.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x + (v1.getXD() * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y + (v1.getYD() * r)));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(x, q);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, q);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(x, q);
      Assert.assertFalse(VectorM2DT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x0, y0);
      final VectorM2DT<A> v2 = new VectorM2DT<A>(x0, y0);

      Assert.assertTrue(VectorM2DT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2DT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2DT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> v1 = new VectorM2DT<A>(y, -x);
      VectorM2DT.normalizeInPlace(v0);
      VectorM2DT.normalizeInPlace(v1);
      final double angle = VectorM2DT.angle(v0, v1);

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
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> v1 = new VectorM2DT<A>(-y, x);
      VectorM2DT.normalizeInPlace(v0);
      VectorM2DT.normalizeInPlace(v1);
      final double angle = VectorM2DT.angle(v0, v1);

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
    final VectorM2DT<A> v = new VectorM2DT<A>(3.0f, 5.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> maximum = new VectorM2DT<A>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      final VectorM2DT<A> vo =
        VectorM2DT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());

      {
        final VectorM2DT<A> vr0 =
          VectorM2DT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> minimum = new VectorM2DT<A>(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      final VectorM2DT<A> vo =
        VectorM2DT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final VectorM2DT<A> vr0 =
          VectorM2DT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> minimum = new VectorM2DT<A>(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> maximum = new VectorM2DT<A>(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      final VectorM2DT<A> vo =
        VectorM2DT.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final VectorM2DT<A> vr0 =
          VectorM2DT.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      VectorM2DT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);

      {
        VectorM2DT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      VectorM2DT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2DT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      VectorM2DT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2DT.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorM2DT<A> vb = new VectorM2DT<A>(5, 6);
    final VectorM2DT<A> va = new VectorM2DT<A>(1, 2);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());

    VectorM2DT.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
  }

  @Override @Test public <A> void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(VectorM2DT.almostEqual(
      ec,
      new VectorM2DT<A>(),
      new VectorM2DT<A>(0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2DT<A> v0 = new VectorM2DT<A>(0.0, 1.0);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(0.0, 0.0);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM2DT.distance(v0, v1),
      1.0));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x1, y1);

      Assert.assertTrue(VectorM2DT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(10.0f, 10.0f);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(10.0f, 10.0f);

    {
      final double p = VectorM2DT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2DT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorM2DT<A> vpx = new VectorM2DT<A>(1.0f, 0.0f);
    final VectorM2DT<A> vmx = new VectorM2DT<A>(-1.0f, 0.0f);

    final VectorM2DT<A> vpy = new VectorM2DT<A>(0.0f, 1.0f);
    final VectorM2DT<A> vmy = new VectorM2DT<A>(0.0f, -1.0f);

    Assert.assertTrue(VectorM2DT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM2DT.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorM2DT<A> q = new VectorM2DT<A>(x, y);
      final double dp = VectorM2DT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(10.0, 10.0);

    {
      final double p = VectorM2DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorM2DT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      final VectorM2DT<A> m1 = new VectorM2DT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>(x, y);
      final VectorM2DT<A> m1 = new VectorM2DT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM2DT<A> m0 = new VectorM2DT<A>();
    final VectorM2DT<A> m1 = new VectorM2DT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      final VectorM2DT<A> m1 = new VectorM2DT<A>();
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2DT<A> m0 = new VectorM2DT<A>();
      final VectorM2DT<A> m1 = new VectorM2DT<A>();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(1.0f, 2.0f);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x1, y1);

      final VectorM2DT<A> vr0 = new VectorM2DT<A>();
      final VectorM2DT<A> vr1 = new VectorM2DT<A>();
      VectorM2DT.interpolateLinear(v0, v1, 0.0, vr0);
      VectorM2DT.interpolateLinear(v0, v1, 1.0, vr1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getXD(),
        vr0.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getYD(),
        vr0.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getXD(),
        vr1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getYD(),
        vr1.getYD()));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final double m = VectorM2DT.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();
      VectorM2DT.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2DT.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2DT<A> v = new VectorM2DT<A>(0.0, 0.0);
    final VectorM2DT<A> vr = VectorM2DT.normalizeInPlace(v);
    final double m = VectorM2DT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2DT<A> v = new VectorM2DT<A>(1.0, 0.0);
    final double m = VectorM2DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM2DT<A> v = new VectorM2DT<A>(8.0, 0.0);

    {
      final double p = VectorM2DT.dotProduct(v, v);
      final double q = VectorM2DT.magnitudeSquared(v);
      final double r = VectorM2DT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2DT<A> v = new VectorM2DT<A>(0.0, 0.0);
    final double m = VectorM2DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(8.0, 0.0);
    final VectorM2DT<A> out = new VectorM2DT<A>();
    final VectorM2DT<A> vr = VectorM2DT.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2DT.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2DT<A> qr = new VectorM2DT<A>();
    final VectorM2DT<A> q = new VectorM2DT<A>(0, 0);
    VectorM2DT.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(0, 1);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(0.5f, 0.5f);

    final Pair<VectorM2DT<A>, VectorM2DT<A>> r =
      VectorM2DT.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM2DT<A>(0, 1), r.first);
    Assert.assertEquals(new VectorM2DT<A>(1, 0), r.second);
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    final VectorM2DT<A> v0 = new VectorM2DT<A>(0f, 1f);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(0.5f, 0.5f);

    VectorM2DT.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM2DT<A>(0, 1), v0);
    Assert.assertEquals(new VectorM2DT<A>(1, 0), v1);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM2DT<A> p = new VectorM2DT<A>(1.0, 0.0);
      final VectorM2DT<A> q = new VectorM2DT<A>(0.0, 1.0);
      final VectorM2DT<A> r = new VectorM2DT<A>();
      final VectorM2DT<A> u = VectorM2DT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2DT.magnitude(u) == 0.0);
    }

    {
      final VectorM2DT<A> p = new VectorM2DT<A>(-1.0, 0.0);
      final VectorM2DT<A> q = new VectorM2DT<A>(0.0, 1.0);
      final VectorM2DT<A> r = new VectorM2DT<A>();
      final VectorM2DT<A> u = VectorM2DT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2DT.magnitude(u) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM2DT<A> out = new VectorM2DT<A>();
    final VectorM2DT<A> v0 = new VectorM2DT<A>(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);

    final VectorM2DT<A> ov0 = VectorM2DT.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);

    final VectorM2DT<A> ov1 = VectorM2DT.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();

      VectorM2DT.scale(v, 1.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getYD(),
        vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2DT.scaleInPlace(v, 1.0);

        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
      }
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v = new VectorM2DT<A>(x, y);

      final VectorM2DT<A> vr = new VectorM2DT<A>();

      VectorM2DT.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getXD(),
        0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getYD(),
        0.0));

      {
        VectorM2DT.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM2DT<A> v = new VectorM2DT<A>(0.0, 1.0);
    Assert.assertTrue(v.toString().equals("[VectorM2DT 0.0 1.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v0 = new VectorM2DT<A>(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorM2DT<A> v1 = new VectorM2DT<A>(x1, y1);

      final VectorM2DT<A> vr0 = new VectorM2DT<A>();
      VectorM2DT.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() - v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2DT.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x - v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y - v1.getYD()));
      }
    }
  }

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM2DT<A> out = new VectorM2DT<A>();
    final VectorM2DT<A> v0 = new VectorM2DT<A>(1.0, 1.0);
    final VectorM2DT<A> v1 = new VectorM2DT<A>(1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2DT<A> ov0 = VectorM2DT.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);

    final VectorM2DT<A> ov1 = VectorM2DT.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
  }
}
