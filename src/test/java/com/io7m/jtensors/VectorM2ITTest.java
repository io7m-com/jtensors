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

public class VectorM2ITTest extends VectorM2TContract
{
  public static int randomNegativeNumber()
  {
    return (int) (Math.random() * Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (Math.random() * Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (Math.random() * (1 << 14));
  }

  @Override @Test public <A> void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      VectorM2IT.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.getXI()) == vr.getXI());
      Assert.assertTrue(Math.abs(v.getYI()) == vr.getYI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        VectorM2IT.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.getXI());
        Assert.assertTrue(Math.abs(orig_y) == v.getYI());
      }
    }
  }

  @Override @Test public <A> void testAbsoluteMutation()
  {
    final VectorM2IT<A> out = new VectorM2IT<A>();
    final VectorM2IT<A> v = new VectorM2IT<A>(-1, -1);

    Assert.assertTrue(v.getXI() == -1);
    Assert.assertTrue(v.getYI() == -1);

    final int vx = v.getXI();
    final int vy = v.getYI();

    final VectorM2IT<A> ov = VectorM2IT.absolute(v, out);

    Assert.assertTrue(vx == v.getXI());
    Assert.assertTrue(vy == v.getYI());
    Assert.assertTrue(vx == -1);
    Assert.assertTrue(vy == -1);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXI() == 1);
    Assert.assertTrue(out.getYI() == 1);
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x0, y0);

      final int x1 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v1 = new VectorM2IT<A>(x1, y1);

      final VectorM2IT<A> vr0 = new VectorM2IT<A>();
      VectorM2IT.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2IT.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x + v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y + v1.getYI()));
      }
    }
  }

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM2IT<A> out = new VectorM2IT<A>();
    final VectorM2IT<A> v0 = new VectorM2IT<A>(1, 1);
    final VectorM2IT<A> v1 = new VectorM2IT<A>(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);

    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2IT<A> ov0 = VectorM2IT.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);

    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2IT<A> ov1 = VectorM2IT.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);

    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
  }

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x0, y0);

      final int x1 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v1 = new VectorM2IT<A>(x1, y1);

      final int r = VectorM2ITTest.randomPositiveSmallNumber();

      final VectorM2IT<A> vr0 = new VectorM2IT<A>();
      VectorM2IT.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2IT.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXI() == (orig_x + (v1.getXI() * r)));
        Assert.assertTrue(v0.getYI() == (orig_y + (v1.getYI() * r)));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final VectorM2IT<A> v0 = new VectorM2IT<A>(1, 0);
      final VectorM2IT<A> v1 = new VectorM2IT<A>(1, 0);
      final double angle = VectorM2IT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> v1 = new VectorM2IT<A>(y, -x);
      final double angle = VectorM2IT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> v1 = new VectorM2IT<A>(-y, x);
      final double angle = VectorM2IT.angle(v0, v1);

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
    final VectorM2IT<A> v = new VectorM2IT<A>(3, 5);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM2ITTest.randomNegativeNumber();
      final int max_y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> maximum = new VectorM2IT<A>(max_x, max_y);

      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      final VectorM2IT<A> vo =
        VectorM2IT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());

      {
        final VectorM2IT<A> vr0 =
          VectorM2IT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2ITTest.randomPositiveNumber();
      final int min_y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> minimum = new VectorM2IT<A>(min_x, min_y);

      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      final VectorM2IT<A> vo =
        VectorM2IT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final VectorM2IT<A> vr0 =
          VectorM2IT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2ITTest.randomNegativeNumber();
      final int min_y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> minimum = new VectorM2IT<A>(min_x, min_y);

      final int max_x = VectorM2ITTest.randomPositiveNumber();
      final int max_y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> maximum = new VectorM2IT<A>(max_x, max_y);

      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      final VectorM2IT<A> vo =
        VectorM2IT.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final VectorM2IT<A> vr0 =
          VectorM2IT.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM2ITTest.randomNegativeNumber();

      final int x = VectorM2ITTest.randomPositiveNumber();
      final int y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      VectorM2IT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);

      {
        VectorM2IT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2ITTest.randomPositiveNumber();

      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomNegativeNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      VectorM2IT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        VectorM2IT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2ITTest.randomNegativeNumber();
      final int maximum = VectorM2ITTest.randomPositiveNumber();

      final int x = VectorM2ITTest.randomNegativeNumber();
      final int y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();
      VectorM2IT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        VectorM2IT.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorM2IT<A> vb = new VectorM2IT<A>(5, 6);
    final VectorM2IT<A> va = new VectorM2IT<A>(1, 2);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());

    VectorM2IT.copy(va, vb);

    Assert.assertTrue(va.getXI() == vb.getXI());
    Assert.assertTrue(va.getYI() == vb.getYI());
  }

  @Override @Test public <A> void testDefault00()
  {
    Assert.assertTrue(new VectorM2IT<A>().equals(new VectorM2IT<A>(0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorM2IT<A> v0 = new VectorM2IT<A>(0, 1);
    final VectorM2IT<A> v1 = new VectorM2IT<A>(0, 0);
    Assert.assertTrue(VectorM2IT.distance(v0, v1) == 1);
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x0, y0);

      final int x1 = VectorM2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v1 = new VectorM2IT<A>(x1, y1);

      Assert.assertTrue(VectorM2IT.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM2IT<A> v0 = new VectorM2IT<A>(10, 10);
    final VectorM2IT<A> v1 = new VectorM2IT<A>(10, 10);

    {
      final int p = VectorM2IT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2IT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final VectorM2IT<A> q = new VectorM2IT<A>(x, y);

      final double ms = VectorM2IT.magnitudeSquared(q);
      final double dp = VectorM2IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final VectorM2IT<A> q = new VectorM2IT<A>(x, y);
      final double dp = VectorM2IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2IT<A> v0 = new VectorM2IT<A>(10, 10);

    {
      final int p = VectorM2IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2IT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      final VectorM2IT<A> m1 = new VectorM2IT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final int x = (int) (Math.random() * 1000);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> m1 = new VectorM2IT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> m1 = new VectorM2IT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> m1 = new VectorM2IT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>(x, y);
      final VectorM2IT<A> m1 = new VectorM2IT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM2IT<A> m0 = new VectorM2IT<A>();
    final VectorM2IT<A> m1 = new VectorM2IT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      final VectorM2IT<A> m1 = new VectorM2IT<A>();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2IT<A> m0 = new VectorM2IT<A>();
      final VectorM2IT<A> m1 = new VectorM2IT<A>();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM2IT<A> v0 = new VectorM2IT<A>(1, 2);
    final VectorM2IT<A> v1 = new VectorM2IT<A>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITTest.randomPositiveNumber();
      final int y0 = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x0, y0);

      final int x1 = VectorM2ITTest.randomPositiveNumber();
      final int y1 = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v1 = new VectorM2IT<A>(x1, y1);

      final VectorM2IT<A> vr0 = new VectorM2IT<A>();
      final VectorM2IT<A> vr1 = new VectorM2IT<A>();
      VectorM2IT.interpolateLinear(v0, v1, 0, vr0);
      VectorM2IT.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITTest.randomPositiveSmallNumber();
      final int y = VectorM2ITTest.randomPositiveSmallNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final int m = VectorM2IT.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final VectorM2IT<A> v = new VectorM2IT<A>(1, 0);
    final int m = VectorM2IT.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM2IT<A> v = new VectorM2IT<A>(8, 0);

    {
      final int p = VectorM2IT.dotProduct(v, v);
      final int q = VectorM2IT.magnitudeSquared(v);
      final int r = VectorM2IT.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final VectorM2IT<A> v = new VectorM2IT<A>(0, 0);
    final int m = VectorM2IT.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM2IT<A> p = new VectorM2IT<A>(1, 0);
      final VectorM2IT<A> q = new VectorM2IT<A>(0, 1);
      final VectorM2IT<A> r = new VectorM2IT<A>();
      final VectorM2IT<A> u = VectorM2IT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2IT.magnitude(u) == 0);
    }

    {
      final VectorM2IT<A> p = new VectorM2IT<A>(-1, 0);
      final VectorM2IT<A> q = new VectorM2IT<A>(0, 1);
      final VectorM2IT<A> r = new VectorM2IT<A>();
      final VectorM2IT<A> u = VectorM2IT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2IT.magnitude(u) == 0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM2IT<A> out = new VectorM2IT<A>();
    final VectorM2IT<A> v0 = new VectorM2IT<A>(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    final VectorM2IT<A> ov0 = VectorM2IT.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    final VectorM2IT<A> ov1 = VectorM2IT.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
  }

  @Override @Test public <A> void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITTest.randomPositiveNumber();
      final int y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();

      VectorM2IT.scale(v, 1, vr);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        VectorM2IT.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXI() == orig_x);
        Assert.assertTrue(v.getYI() == orig_y);
      }
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITTest.randomPositiveNumber();
      final int y = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v = new VectorM2IT<A>(x, y);

      final VectorM2IT<A> vr = new VectorM2IT<A>();

      VectorM2IT.scale(v, 0, vr);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);

      {
        VectorM2IT.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXI() == 0);
        Assert.assertTrue(v.getYI() == 0);
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM2IT<A> v = new VectorM2IT<A>(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorM2IT 1 2]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITTest.randomPositiveNumber();
      final int y0 = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v0 = new VectorM2IT<A>(x0, y0);

      final int x1 = VectorM2ITTest.randomPositiveNumber();
      final int y1 = VectorM2ITTest.randomPositiveNumber();
      final VectorM2IT<A> v1 = new VectorM2IT<A>(x1, y1);

      final VectorM2IT<A> vr0 = new VectorM2IT<A>();
      VectorM2IT.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2IT.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x - v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y - v1.getYI()));
      }
    }
  }

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM2IT<A> out = new VectorM2IT<A>();
    final VectorM2IT<A> v0 = new VectorM2IT<A>(1, 1);
    final VectorM2IT<A> v1 = new VectorM2IT<A>(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2IT<A> ov0 = VectorM2IT.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2IT<A> ov1 = VectorM2IT.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 0);
    Assert.assertTrue(ov1.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 0);
    Assert.assertTrue(v0.getYI() == 0);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
  }
}
