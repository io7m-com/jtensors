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
import com.io7m.jtensors.parameterized.PVectorI2I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI2ITest<T>
  extends PVectorI2Contract
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

  @Test public void testZero()
  {
    final PVectorI2I<Object> z = PVectorI2I.zero();
    Assert.assertEquals(0, z.getXI());
    Assert.assertEquals(0, z.getYI());
  }

  @Override @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x0, y0);

      final int x1 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v1 = new PVectorI2I<T>(x1, y1);

      final PVectorI2I<T> vr0 = PVectorI2I.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x0, y0);

      final int x1 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v1 = new PVectorI2I<T>(x1, y1);

      final int r = PVectorI2ITest.randomPositiveSmallNumber();

      final PVectorI2I<T> vr0 = PVectorI2I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
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
      final PVectorI2I<T> v0 = new PVectorI2I<T>(1, 0);
      final PVectorI2I<T> v1 = new PVectorI2I<T>(1, 0);
      final double angle = PVectorI2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x, y);
      final PVectorI2I<T> v1 = new PVectorI2I<T>(y, -x);
      final double angle = PVectorI2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x, y);
      final PVectorI2I<T> v1 = new PVectorI2I<T>(-y, x);
      final double angle = PVectorI2I.angle(v0, v1);

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
    final PVectorI2I<T> v = new PVectorI2I<T>(3, 5);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = PVectorI2ITest.randomNegativeNumber();
      final int max_y = PVectorI2ITest.randomNegativeNumber();
      final PVectorI2I<T> maximum = new PVectorI2I<T>(max_x, max_y);

      final int x = PVectorI2ITest.randomNegativeNumber();
      final int y = PVectorI2ITest.randomNegativeNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clampMaximumByPVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI2ITest.randomPositiveNumber();
      final int min_y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> minimum = new PVectorI2I<T>(min_x, min_y);

      final int x = PVectorI2ITest.randomNegativeNumber();
      final int y = PVectorI2ITest.randomNegativeNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clampMinimumByPVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI2ITest.randomNegativeNumber();
      final int min_y = PVectorI2ITest.randomNegativeNumber();
      final PVectorI2I<T> minimum = new PVectorI2I<T>(min_x, min_y);

      final int max_x = PVectorI2ITest.randomPositiveNumber();
      final int max_y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> maximum = new PVectorI2I<T>(max_x, max_y);

      final int x = PVectorI2ITest.randomNegativeNumber();
      final int y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clampByPVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = PVectorI2ITest.randomNegativeNumber();

      final int x = PVectorI2ITest.randomPositiveNumber();
      final int y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI2ITest.randomPositiveNumber();

      final int x = PVectorI2ITest.randomNegativeNumber();
      final int y = PVectorI2ITest.randomNegativeNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI2ITest.randomNegativeNumber();
      final int maximum = PVectorI2ITest.randomPositiveNumber();

      final int x = PVectorI2ITest.randomNegativeNumber();
      final int y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorI2I<T> vb = new PVectorI2I<T>(5, 6);
    final PVectorI2I<T> va = new PVectorI2I<T>(1, 2);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());

    final PVectorI2I<T> vc = new PVectorI2I<T>(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new PVectorI2I<T>().equals(new PVectorI2I<T>(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorI2I<T> v0 = new PVectorI2I<T>(0, 1);
    final PVectorI2I<T> v1 = new PVectorI2I<T>(0, 0);
    Assert.assertTrue(PVectorI2I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x0, y0);

      final int x1 = PVectorI2ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v1 = new PVectorI2I<T>(x1, y1);

      Assert.assertTrue(PVectorI2I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI2I<T> v0 = new PVectorI2I<T>(10, 10);
    final PVectorI2I<T> v1 = new PVectorI2I<T>(10, 10);

    {
      final int p = PVectorI2I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = PVectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = PVectorI2I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
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
      final PVectorI2I<T> q = new PVectorI2I<T>(x, y);

      final double ms = PVectorI2I.magnitudeSquared(q);
      final double dp = PVectorI2I.dotProduct(q, q);

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
      final PVectorI2I<T> q = new PVectorI2I<T>(x, y);
      final double dp = PVectorI2I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorI2I<T> v0 = new PVectorI2I<T>(10, 10);

    {
      final int p = PVectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = PVectorI2I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>();
      final PVectorI2I<T> m1 = new PVectorI2I<T>();
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
      final PVectorI2I<T> m0 = new PVectorI2I<T>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(x, y);
      final PVectorI2I<T> m1 = new PVectorI2I<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(x, y);
      final PVectorI2I<T> m1 = new PVectorI2I<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(x, y);
      final PVectorI2I<T> m1 = new PVectorI2I<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI2I<T> m0 = new PVectorI2I<T>();
    final PVectorI2I<T> m1 = new PVectorI2I<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(23, 0);
      final PVectorI2I<T> m1 = new PVectorI2I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI2I<T> m0 = new PVectorI2I<T>(0, 23);
      final PVectorI2I<T> m1 = new PVectorI2I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI2I<T> v0 = new PVectorI2I<T>(1, 2);
    final PVectorI2I<T> v1 = new PVectorI2I<T>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI2ITest.randomPositiveNumber();
      final int y0 = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x0, y0);

      final int x1 = PVectorI2ITest.randomPositiveNumber();
      final int y1 = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v1 = new PVectorI2I<T>(x1, y1);

      final PVectorI2I<T> vr0 = PVectorI2I.interpolateLinear(v0, v1, 0);
      final PVectorI2I<T> vr1 = PVectorI2I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI2ITest.randomPositiveSmallNumber();
      final int y = PVectorI2ITest.randomPositiveSmallNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final int m = PVectorI2I.magnitude(v);
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
    final PVectorI2I<T> v = new PVectorI2I<T>(1, 0);
    final int m = PVectorI2I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI2I<T> v = new PVectorI2I<T>(8, 0);

    {
      final int p = PVectorI2I.dotProduct(v, v);
      final int q = PVectorI2I.magnitudeSquared(v);
      final int r = PVectorI2I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorI2I<T> v = new PVectorI2I<T>(0, 0);
    final int m = PVectorI2I.magnitude(v);
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
      final PVectorI2I<T> p = new PVectorI2I<T>(1, 0);
      final PVectorI2I<T> q = new PVectorI2I<T>(0, 1);
      final PVectorI2I<T> u = PVectorI2I.projection(p, q);

      Assert.assertTrue(PVectorI2I.magnitude(u) == 0);
    }

    {
      final PVectorI2I<T> p = new PVectorI2I<T>(-1, 0);
      final PVectorI2I<T> q = new PVectorI2I<T>(0, 1);
      final PVectorI2I<T> u = PVectorI2I.projection(p, q);

      Assert.assertTrue(PVectorI2I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI2ITest.randomPositiveNumber();
      final int y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI2ITest.randomPositiveNumber();
      final int y = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v = new PVectorI2I<T>(x, y);

      final PVectorI2I<T> vr = PVectorI2I.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI2I<T> v = new PVectorI2I<T>(1, 2);
    Assert.assertTrue(v.toString().equals("[PVectorI2I 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI2ITest.randomPositiveNumber();
      final int y0 = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v0 = new PVectorI2I<T>(x0, y0);

      final int x1 = PVectorI2ITest.randomPositiveNumber();
      final int y1 = PVectorI2ITest.randomPositiveNumber();
      final PVectorI2I<T> v1 = new PVectorI2I<T>(x1, y1);

      final PVectorI2I<T> vr0 = PVectorI2I.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
    }
  }
}
