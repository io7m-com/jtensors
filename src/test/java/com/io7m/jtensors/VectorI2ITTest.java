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

public class VectorI2ITTest extends VectorI2TContract
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
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.absolute(v);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
    }
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x0, y0);

      final int x1 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v1 = new VectorI2IT<A>(x1, y1);

      final VectorI2IT<A> vr0 = VectorI2IT.add(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
    }
  }

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x0, y0);

      final int x1 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v1 = new VectorI2IT<A>(x1, y1);

      final int r = VectorI2ITTest.randomPositiveSmallNumber();

      final VectorI2IT<A> vr0 = VectorI2IT.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
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
      final VectorI2IT<A> v0 = new VectorI2IT<A>(1, 0);
      final VectorI2IT<A> v1 = new VectorI2IT<A>(1, 0);
      final double angle = VectorI2IT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x, y);
      final VectorI2IT<A> v1 = new VectorI2IT<A>(y, -x);
      final double angle = VectorI2IT.angle(v0, v1);

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
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x, y);
      final VectorI2IT<A> v1 = new VectorI2IT<A>(-y, x);
      final double angle = VectorI2IT.angle(v0, v1);

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
    final VectorI2IT<A> v = new VectorI2IT<A>(3, 5);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI2ITTest.randomNegativeNumber();
      final int max_y = VectorI2ITTest.randomNegativeNumber();
      final VectorI2IT<A> maximum = new VectorI2IT<A>(max_x, max_y);

      final int x = VectorI2ITTest.randomNegativeNumber();
      final int y = VectorI2ITTest.randomNegativeNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITTest.randomPositiveNumber();
      final int min_y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> minimum = new VectorI2IT<A>(min_x, min_y);

      final int x = VectorI2ITTest.randomNegativeNumber();
      final int y = VectorI2ITTest.randomNegativeNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITTest.randomNegativeNumber();
      final int min_y = VectorI2ITTest.randomNegativeNumber();
      final VectorI2IT<A> minimum = new VectorI2IT<A>(min_x, min_y);

      final int max_x = VectorI2ITTest.randomPositiveNumber();
      final int max_y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> maximum = new VectorI2IT<A>(max_x, max_y);

      final int x = VectorI2ITTest.randomNegativeNumber();
      final int y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI2ITTest.randomNegativeNumber();

      final int x = VectorI2ITTest.randomPositiveNumber();
      final int y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clampMaximum(v, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITTest.randomPositiveNumber();

      final int x = VectorI2ITTest.randomNegativeNumber();
      final int y = VectorI2ITTest.randomNegativeNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clampMinimum(v, minimum);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITTest.randomNegativeNumber();
      final int maximum = VectorI2ITTest.randomPositiveNumber();

      final int x = VectorI2ITTest.randomNegativeNumber();
      final int y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorI2IT<A> vb = new VectorI2IT<A>(5, 6);
    final VectorI2IT<A> va = new VectorI2IT<A>(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    final VectorI2IT<A> vc = new VectorI2IT<A>(va);

    Assert.assertTrue(va.x == vc.x);
    Assert.assertTrue(va.y == vc.y);

    Assert.assertFalse(vc.x == vb.x);
    Assert.assertFalse(vc.y == vb.y);
  }

  @Override @Test public <A> void testDefault00()
  {
    Assert.assertTrue(new VectorI2IT<A>().equals(new VectorI2IT<A>(0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorI2IT<A> v0 = new VectorI2IT<A>(0, 1);
    final VectorI2IT<A> v1 = new VectorI2IT<A>(0, 0);
    Assert.assertTrue(VectorI2IT.distance(v0, v1) == 1);
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x0, y0);

      final int x1 = VectorI2ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v1 = new VectorI2IT<A>(x1, y1);

      Assert.assertTrue(VectorI2IT.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI2IT<A> v0 = new VectorI2IT<A>(10, 10);
    final VectorI2IT<A> v1 = new VectorI2IT<A>(10, 10);

    {
      final int p = VectorI2IT.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2IT.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
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
      final VectorI2IT<A> q = new VectorI2IT<A>(x, y);

      final double ms = VectorI2IT.magnitudeSquared(q);
      final double dp = VectorI2IT.dotProduct(q, q);

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
      final VectorI2IT<A> q = new VectorI2IT<A>(x, y);
      final double dp = VectorI2IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorI2IT<A> v0 = new VectorI2IT<A>(10, 10);

    {
      final int p = VectorI2IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2IT.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>();
      final VectorI2IT<A> m1 = new VectorI2IT<A>();
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
      final VectorI2IT<A> m0 = new VectorI2IT<A>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(x, y);
      final VectorI2IT<A> m1 = new VectorI2IT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(x, y);
      final VectorI2IT<A> m1 = new VectorI2IT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(x, y);
      final VectorI2IT<A> m1 = new VectorI2IT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI2IT<A> m0 = new VectorI2IT<A>();
    final VectorI2IT<A> m1 = new VectorI2IT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(23, 0);
      final VectorI2IT<A> m1 = new VectorI2IT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2IT<A> m0 = new VectorI2IT<A>(0, 23);
      final VectorI2IT<A> m1 = new VectorI2IT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI2IT<A> v0 = new VectorI2IT<A>(1, 2);
    final VectorI2IT<A> v1 = new VectorI2IT<A>(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITTest.randomPositiveNumber();
      final int y0 = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x0, y0);

      final int x1 = VectorI2ITTest.randomPositiveNumber();
      final int y1 = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v1 = new VectorI2IT<A>(x1, y1);

      final VectorI2IT<A> vr0 = VectorI2IT.interpolateLinear(v0, v1, 0);
      final VectorI2IT<A> vr1 = VectorI2IT.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITTest.randomPositiveSmallNumber();
      final int y = VectorI2ITTest.randomPositiveSmallNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final int m = VectorI2IT.magnitude(v);
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
    final VectorI2IT<A> v = new VectorI2IT<A>(1, 0);
    final int m = VectorI2IT.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI2IT<A> v = new VectorI2IT<A>(8, 0);

    {
      final int p = VectorI2IT.dotProduct(v, v);
      final int q = VectorI2IT.magnitudeSquared(v);
      final int r = VectorI2IT.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final VectorI2IT<A> v = new VectorI2IT<A>(0, 0);
    final int m = VectorI2IT.magnitude(v);
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

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI2IT<A> p = new VectorI2IT<A>(1, 0);
      final VectorI2IT<A> q = new VectorI2IT<A>(0, 1);
      final VectorI2IT<A> u = VectorI2IT.projection(p, q);

      Assert.assertTrue(VectorI2IT.magnitude(u) == 0);
    }

    {
      final VectorI2IT<A> p = new VectorI2IT<A>(-1, 0);
      final VectorI2IT<A> q = new VectorI2IT<A>(0, 1);
      final VectorI2IT<A> u = VectorI2IT.projection(p, q);

      Assert.assertTrue(VectorI2IT.magnitude(u) == 0);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITTest.randomPositiveNumber();
      final int y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.scale(v, 1);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITTest.randomPositiveNumber();
      final int y = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v = new VectorI2IT<A>(x, y);

      final VectorI2IT<A> vr = VectorI2IT.scale(v, 0);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI2IT<A> v = new VectorI2IT<A>(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorI2IT 1 2]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITTest.randomPositiveNumber();
      final int y0 = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v0 = new VectorI2IT<A>(x0, y0);

      final int x1 = VectorI2ITTest.randomPositiveNumber();
      final int y1 = VectorI2ITTest.randomPositiveNumber();
      final VectorI2IT<A> v1 = new VectorI2IT<A>(x1, y1);

      final VectorI2IT<A> vr0 = VectorI2IT.subtract(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
    }
  }
}
