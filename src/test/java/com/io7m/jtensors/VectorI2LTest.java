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

public class VectorI2LTest extends VectorI2Contract
{
  public static long randomNegativeNumber()
  {
    return (long) (Math.random() * Long.MIN_VALUE);
  }

  public static long randomPositiveNumber()
  {
    return (long) (Math.random() * Long.MAX_VALUE);
  }

  public static long randomPositiveSmallNumber()
  {
    return (long) (Math.random() * (1 << 14));
  }

  @Override @Test public void testAbsolute()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.absolute(v);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI2LTest.randomPositiveSmallNumber();
      final long y0 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v0 = new VectorI2L(x0, y0);

      final long x1 = VectorI2LTest.randomPositiveSmallNumber();
      final long y1 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v1 = new VectorI2L(x1, y1);

      final VectorI2L vr0 = VectorI2L.add(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI2LTest.randomPositiveSmallNumber();
      final long y0 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v0 = new VectorI2L(x0, y0);

      final long x1 = VectorI2LTest.randomPositiveSmallNumber();
      final long y1 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v1 = new VectorI2L(x1, y1);

      final long r = VectorI2LTest.randomPositiveSmallNumber();

      final VectorI2L vr0 = VectorI2L.addScaled(v0, v1, r);

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

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final VectorI2L v0 = new VectorI2L(1, 0);
      final VectorI2L v1 = new VectorI2L(1, 0);
      final double angle = VectorI2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorI2L v0 = new VectorI2L(x, y);
      final VectorI2L v1 = new VectorI2L(y, -x);
      final double angle = VectorI2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorI2L v0 = new VectorI2L(x, y);
      final VectorI2L v1 = new VectorI2L(-y, x);
      final double angle = VectorI2L.angle(v0, v1);

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
    final VectorI2L v = new VectorI2L(3, 5);

    Assert.assertTrue(v.x == v.getXL());
    Assert.assertTrue(v.y == v.getYL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorI2LTest.randomNegativeNumber();
      final long max_y = VectorI2LTest.randomNegativeNumber();
      final VectorI2L maximum = new VectorI2L(max_x, max_y);

      final long x = VectorI2LTest.randomNegativeNumber();
      final long y = VectorI2LTest.randomNegativeNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI2LTest.randomPositiveNumber();
      final long min_y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L minimum = new VectorI2L(min_x, min_y);

      final long x = VectorI2LTest.randomNegativeNumber();
      final long y = VectorI2LTest.randomNegativeNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI2LTest.randomNegativeNumber();
      final long min_y = VectorI2LTest.randomNegativeNumber();
      final VectorI2L minimum = new VectorI2L(min_x, min_y);

      final long max_x = VectorI2LTest.randomPositiveNumber();
      final long max_y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L maximum = new VectorI2L(max_x, max_y);

      final long x = VectorI2LTest.randomNegativeNumber();
      final long y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorI2LTest.randomNegativeNumber();

      final long x = VectorI2LTest.randomPositiveNumber();
      final long y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clampMaximum(v, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI2LTest.randomPositiveNumber();

      final long x = VectorI2LTest.randomNegativeNumber();
      final long y = VectorI2LTest.randomNegativeNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clampMinimum(v, minimum);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI2LTest.randomNegativeNumber();
      final long maximum = VectorI2LTest.randomPositiveNumber();

      final long x = VectorI2LTest.randomNegativeNumber();
      final long y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI2L vb = new VectorI2L(5, 6);
    final VectorI2L va = new VectorI2L(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    final VectorI2L vc = new VectorI2L(va);

    Assert.assertTrue(va.x == vc.x);
    Assert.assertTrue(va.y == vc.y);

    Assert.assertFalse(vc.x == vb.x);
    Assert.assertFalse(vc.y == vb.y);
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new VectorI2L().equals(new VectorI2L(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI2L v0 = new VectorI2L(0, 1);
    final VectorI2L v1 = new VectorI2L(0, 0);
    Assert.assertTrue(VectorI2L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI2LTest.randomPositiveSmallNumber();
      final long y0 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v0 = new VectorI2L(x0, y0);

      final long x1 = VectorI2LTest.randomPositiveSmallNumber();
      final long y1 = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v1 = new VectorI2L(x1, y1);

      Assert.assertTrue(VectorI2L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI2L v0 = new VectorI2L(10, 10);
    final VectorI2L v1 = new VectorI2L(10, 10);

    {
      final long p = VectorI2L.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorI2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorI2L.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final VectorI2L q = new VectorI2L(x, y);

      final double ms = VectorI2L.magnitudeSquared(q);
      final double dp = VectorI2L.dotProduct(q, q);

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

    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final VectorI2L q = new VectorI2L(x, y);
      final double dp = VectorI2L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI2L v0 = new VectorI2L(10, 10);

    {
      final long p = VectorI2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorI2L.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI2L m0 = new VectorI2L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2L m0 = new VectorI2L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2L m0 = new VectorI2L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI2L m0 = new VectorI2L();
      final VectorI2L m1 = new VectorI2L();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final long x = (long) (Math.random() * 1000);
    final long y = x + 1;
    final long z = y + 1;
    final long w = z + 1;
    final long q = w + 1;

    {
      final VectorI2L m0 = new VectorI2L(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2L m0 = new VectorI2L(x, y);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI2L m0 = new VectorI2L(x, y);
      final VectorI2L m1 = new VectorI2L(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2L m0 = new VectorI2L(x, y);
      final VectorI2L m1 = new VectorI2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2L m0 = new VectorI2L(x, y);
      final VectorI2L m1 = new VectorI2L(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI2L m0 = new VectorI2L();
    final VectorI2L m1 = new VectorI2L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2L m0 = new VectorI2L(23, 0);
      final VectorI2L m1 = new VectorI2L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2L m0 = new VectorI2L(0, 23);
      final VectorI2L m1 = new VectorI2L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI2L v0 = new VectorI2L(1, 2);
    final VectorI2L v1 = new VectorI2L(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI2LTest.randomPositiveNumber();
      final long y0 = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v0 = new VectorI2L(x0, y0);

      final long x1 = VectorI2LTest.randomPositiveNumber();
      final long y1 = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v1 = new VectorI2L(x1, y1);

      final VectorI2L vr0 = VectorI2L.interpolateLinear(v0, v1, 0);
      final VectorI2L vr1 = VectorI2L.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI2LTest.randomPositiveSmallNumber();
      final long y = VectorI2LTest.randomPositiveSmallNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final long m = VectorI2L.magnitude(v);
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
    final VectorI2L v = new VectorI2L(1, 0);
    final long m = VectorI2L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI2L v = new VectorI2L(8, 0);

    {
      final long p = VectorI2L.dotProduct(v, v);
      final long q = VectorI2L.magnitudeSquared(v);
      final long r = VectorI2L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI2L v = new VectorI2L(0, 0);
    final long m = VectorI2L.magnitude(v);
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
      final VectorI2L p = new VectorI2L(1, 0);
      final VectorI2L q = new VectorI2L(0, 1);
      final VectorI2L u = VectorI2L.projection(p, q);

      Assert.assertTrue(VectorI2L.magnitude(u) == 0);
    }

    {
      final VectorI2L p = new VectorI2L(-1, 0);
      final VectorI2L q = new VectorI2L(0, 1);
      final VectorI2L u = VectorI2L.projection(p, q);

      Assert.assertTrue(VectorI2L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI2LTest.randomPositiveNumber();
      final long y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.scale(v, 1);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI2LTest.randomPositiveNumber();
      final long y = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v = new VectorI2L(x, y);

      final VectorI2L vr = VectorI2L.scale(v, 0);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI2L v = new VectorI2L(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorI2L 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI2LTest.randomPositiveNumber();
      final long y0 = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v0 = new VectorI2L(x0, y0);

      final long x1 = VectorI2LTest.randomPositiveNumber();
      final long y1 = VectorI2LTest.randomPositiveNumber();
      final VectorI2L v1 = new VectorI2L(x1, y1);

      final VectorI2L vr0 = VectorI2L.subtract(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
    }
  }
}
