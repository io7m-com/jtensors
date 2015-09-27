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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.parameterized.PVectorI2L;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI2LTest<T>
  extends PVectorI2Contract
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

  @Test public void testZero()
  {
    final PVectorI2L<Object> z = PVectorI2L.zero();
    Assert.assertEquals(0, z.getXL());
    Assert.assertEquals(0, z.getYL());
  }

  @Override @Test public void testAbsolute()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.absolute(v);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y0 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x0, y0);

      final long x1 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y1 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v1 = new PVectorI2L<T>(x1, y1);

      final PVectorI2L<T> vr0 = PVectorI2L.add(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y0 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x0, y0);

      final long x1 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y1 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v1 = new PVectorI2L<T>(x1, y1);

      final long r = PVectorI2LTest.randomPositiveSmallNumber();

      final PVectorI2L<T> vr0 = PVectorI2L.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));
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
      final PVectorI2L<T> v0 = new PVectorI2L<T>(1, 0);
      final PVectorI2L<T> v1 = new PVectorI2L<T>(1, 0);
      final double angle = PVectorI2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x, y);
      final PVectorI2L<T> v1 = new PVectorI2L<T>(y, -x);
      final double angle = PVectorI2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x, y);
      final PVectorI2L<T> v1 = new PVectorI2L<T>(-y, x);
      final double angle = PVectorI2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorI2L<T> v = new PVectorI2L<T>(3, 5);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = PVectorI2LTest.randomNegativeNumber();
      final long max_y = PVectorI2LTest.randomNegativeNumber();
      final PVectorI2L<T> maximum = new PVectorI2L<T>(max_x, max_y);

      final long x = PVectorI2LTest.randomNegativeNumber();
      final long y = PVectorI2LTest.randomNegativeNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clampMaximumByPVector(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorI2LTest.randomPositiveNumber();
      final long min_y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> minimum = new PVectorI2L<T>(min_x, min_y);

      final long x = PVectorI2LTest.randomNegativeNumber();
      final long y = PVectorI2LTest.randomNegativeNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clampMinimumByPVector(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorI2LTest.randomNegativeNumber();
      final long min_y = PVectorI2LTest.randomNegativeNumber();
      final PVectorI2L<T> minimum = new PVectorI2L<T>(min_x, min_y);

      final long max_x = PVectorI2LTest.randomPositiveNumber();
      final long max_y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> maximum = new PVectorI2L<T>(max_x, max_y);

      final long x = PVectorI2LTest.randomNegativeNumber();
      final long y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clampByPVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = PVectorI2LTest.randomNegativeNumber();

      final long x = PVectorI2LTest.randomPositiveNumber();
      final long y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorI2LTest.randomPositiveNumber();

      final long x = PVectorI2LTest.randomNegativeNumber();
      final long y = PVectorI2LTest.randomNegativeNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorI2LTest.randomNegativeNumber();
      final long maximum = PVectorI2LTest.randomPositiveNumber();

      final long x = PVectorI2LTest.randomNegativeNumber();
      final long y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorI2L<T> vb = new PVectorI2L<T>(5, 6);
    final PVectorI2L<T> va = new PVectorI2L<T>(1, 2);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());

    final PVectorI2L<T> vc = new PVectorI2L<T>(va);

    Assert.assertTrue(va.getXL() == vc.getXL());
    Assert.assertTrue(va.getYL() == vc.getYL());

    Assert.assertFalse(vc.getXL() == vb.getXL());
    Assert.assertFalse(vc.getYL() == vb.getYL());
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new PVectorI2L<T>().equals(new PVectorI2L<T>(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorI2L<T> v0 = new PVectorI2L<T>(0, 1);
    final PVectorI2L<T> v1 = new PVectorI2L<T>(0, 0);
    Assert.assertTrue(PVectorI2L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y0 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x0, y0);

      final long x1 = PVectorI2LTest.randomPositiveSmallNumber();
      final long y1 = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v1 = new PVectorI2L<T>(x1, y1);

      Assert.assertTrue(PVectorI2L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI2L<T> v0 = new PVectorI2L<T>(10, 10);
    final PVectorI2L<T> v1 = new PVectorI2L<T>(10, 10);

    {
      final long p = PVectorI2L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = PVectorI2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = PVectorI2L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final PVectorI2L<T> q = new PVectorI2L<T>(x, y);

      final double ms = PVectorI2L.magnitudeSquared(q);
      final double dp = PVectorI2L.dotProduct(q, q);

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

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final PVectorI2L<T> q = new PVectorI2L<T>(x, y);
      final double dp = PVectorI2L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorI2L<T> v0 = new PVectorI2L<T>(10, 10);

    {
      final long p = PVectorI2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = PVectorI2L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>();
      final PVectorI2L<T> m1 = new PVectorI2L<T>();
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
      final PVectorI2L<T> m0 = new PVectorI2L<T>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(x, y);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(x, y);
      final PVectorI2L<T> m1 = new PVectorI2L<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(x, y);
      final PVectorI2L<T> m1 = new PVectorI2L<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(x, y);
      final PVectorI2L<T> m1 = new PVectorI2L<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI2L<T> m0 = new PVectorI2L<T>();
    final PVectorI2L<T> m1 = new PVectorI2L<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(23, 0);
      final PVectorI2L<T> m1 = new PVectorI2L<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI2L<T> m0 = new PVectorI2L<T>(0, 23);
      final PVectorI2L<T> m1 = new PVectorI2L<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI2L<T> v0 = new PVectorI2L<T>(1, 2);
    final PVectorI2L<T> v1 = new PVectorI2L<T>(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorI2LTest.randomPositiveNumber();
      final long y0 = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x0, y0);

      final long x1 = PVectorI2LTest.randomPositiveNumber();
      final long y1 = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v1 = new PVectorI2L<T>(x1, y1);

      final PVectorI2L<T> vr0 = PVectorI2L.interpolateLinear(v0, v1, 0);
      final PVectorI2L<T> vr1 = PVectorI2L.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorI2LTest.randomPositiveSmallNumber();
      final long y = PVectorI2LTest.randomPositiveSmallNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final long m = PVectorI2L.magnitude(v);
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
    final PVectorI2L<T> v = new PVectorI2L<T>(1, 0);
    final long m = PVectorI2L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI2L<T> v = new PVectorI2L<T>(8, 0);

    {
      final long p = PVectorI2L.dotProduct(v, v);
      final long q = PVectorI2L.magnitudeSquared(v);
      final long r = PVectorI2L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorI2L<T> v = new PVectorI2L<T>(0, 0);
    final long m = PVectorI2L.magnitude(v);
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
      final PVectorI2L<T> p = new PVectorI2L<T>(1, 0);
      final PVectorI2L<T> q = new PVectorI2L<T>(0, 1);
      final PVectorI2L<T> u = PVectorI2L.projection(p, q);

      Assert.assertTrue(PVectorI2L.magnitude(u) == 0);
    }

    {
      final PVectorI2L<T> p = new PVectorI2L<T>(-1, 0);
      final PVectorI2L<T> q = new PVectorI2L<T>(0, 1);
      final PVectorI2L<T> u = PVectorI2L.projection(p, q);

      Assert.assertTrue(PVectorI2L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorI2LTest.randomPositiveNumber();
      final long y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.scale(v, 1);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorI2LTest.randomPositiveNumber();
      final long y = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v = new PVectorI2L<T>(x, y);

      final PVectorI2L<T> vr = PVectorI2L.scale(v, 0);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI2L<T> v = new PVectorI2L<T>(1, 2);
    Assert.assertTrue(v.toString().equals("[PVectorI2L 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorI2LTest.randomPositiveNumber();
      final long y0 = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v0 = new PVectorI2L<T>(x0, y0);

      final long x1 = PVectorI2LTest.randomPositiveNumber();
      final long y1 = PVectorI2LTest.randomPositiveNumber();
      final PVectorI2L<T> v1 = new PVectorI2L<T>(x1, y1);

      final PVectorI2L<T> vr0 = PVectorI2L.subtract(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
    }
  }
}
