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

public class VectorI2ITest extends VectorI4Contract
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

  @Override @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.absolute(v);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.add(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v1 = new VectorI2I(x1, y1);

      final int r = VectorI2ITest.randomPositiveSmallNumber();

      final VectorI2I vr0 = VectorI2I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorI2I v = new VectorI2I(3, 5);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI2ITest.randomNegativeNumber();
      final int max_y = VectorI2ITest.randomNegativeNumber();
      final VectorI2I maximum = new VectorI2I(max_x, max_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITest.randomPositiveNumber();
      final int min_y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I minimum = new VectorI2I(min_x, min_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITest.randomNegativeNumber();
      final int min_y = VectorI2ITest.randomNegativeNumber();
      final VectorI2I minimum = new VectorI2I(min_x, min_y);

      final int max_x = VectorI2ITest.randomPositiveNumber();
      final int max_y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I maximum = new VectorI2I(max_x, max_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI2ITest.randomNegativeNumber();

      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITest.randomPositiveNumber();

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITest.randomNegativeNumber();
      final int maximum = VectorI2ITest.randomPositiveNumber();

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI2I vb = new VectorI2I(5, 6);
    final VectorI2I va = new VectorI2I(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    final VectorI2I vc = new VectorI2I(va);

    Assert.assertTrue(va.x == vc.x);
    Assert.assertTrue(va.y == vc.y);

    Assert.assertFalse(vc.x == vb.x);
    Assert.assertFalse(vc.y == vb.y);
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(new VectorI2I().equals(new VectorI2I(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI2I v0 = new VectorI2I(0, 1);
    final VectorI2I v1 = new VectorI2I(0, 0);
    Assert.assertTrue(VectorI2I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v1 = new VectorI2I(x1, y1);

      Assert.assertTrue(VectorI2I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI2I v0 = new VectorI2I(10, 10);
    final VectorI2I v1 = new VectorI2I(10, 10);

    {
      final int p = VectorI2I.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final VectorI2I q = new VectorI2I(x, y);

      final double ms = VectorI2I.magnitudeSquared(q);
      final double dp = VectorI2I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final VectorI2I q = new VectorI2I(x, y);
      final double dp = VectorI2I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI2I v0 = new VectorI2I(10, 10);

    {
      final int p = VectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      final VectorI2I m1 = new VectorI2I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final int x = (int) (Math.random() * 1000);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI2I m0 = new VectorI2I();
    final VectorI2I m1 = new VectorI2I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2I m0 = new VectorI2I(23, 0);
      final VectorI2I m1 = new VectorI2I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2I m0 = new VectorI2I(0, 23);
      final VectorI2I m1 = new VectorI2I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI2I v0 = new VectorI2I(1, 2);
    final VectorI2I v1 = new VectorI2I(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveNumber();
      final int y0 = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveNumber();
      final int y1 = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.interpolateLinear(v0, v1, 0);
      final VectorI2I vr1 = VectorI2I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveSmallNumber();
      final int y = VectorI2ITest.randomPositiveSmallNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final int m = VectorI2I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorI2I v = new VectorI2I(1, 0);
    final int m = VectorI2I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI2I v = new VectorI2I(8, 0);

    {
      final int p = VectorI2I.dotProduct(v, v);
      final int q = VectorI2I.magnitudeSquared(v);
      final int r = VectorI2I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI2I v = new VectorI2I(0, 0);
    final int m = VectorI2I.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Override @Test public void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorI2I p = new VectorI2I(1, 0);
      final VectorI2I q = new VectorI2I(0, 1);
      final VectorI2I u = VectorI2I.projection(p, q);

      Assert.assertTrue(VectorI2I.magnitude(u) == 0);
    }

    {
      final VectorI2I p = new VectorI2I(-1, 0);
      final VectorI2I q = new VectorI2I(0, 1);
      final VectorI2I u = VectorI2I.projection(p, q);

      Assert.assertTrue(VectorI2I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.scale(v, 1);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.scale(v, 0);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorI2I 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveNumber();
      final int y0 = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveNumber();
      final int y1 = VectorI2ITest.randomPositiveNumber();
      final VectorI2I v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.subtract(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
    }
  }
}
