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
import com.io7m.jtensors.parameterized.PVectorI3I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI3ITest<T>
  extends PVectorI3Contract
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
    final PVectorI3I<Object> z = PVectorI3I.zero();
    Assert.assertEquals(0, z.getXI());
    Assert.assertEquals(0, z.getYI());
    Assert.assertEquals(0, z.getZI());
  }

  @Override @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v0 = new PVectorI3I<T>(x0, y0, z0);

      final int x1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v1 = new PVectorI3I<T>(x1, y1, z1);

      final PVectorI3I<T> vr0 = PVectorI3I.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v0 = new PVectorI3I<T>(x0, y0, z0);

      final int x1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v1 = new PVectorI3I<T>(x1, y1, z1);

      final int r = PVectorI3ITest.randomPositiveSmallNumber();

      final PVectorI3I<T> vr0 = PVectorI3I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));
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
    final PVectorI3I<T> v = new PVectorI3I<T>(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = PVectorI3ITest.randomNegativeNumber();
      final int max_y = PVectorI3ITest.randomNegativeNumber();
      final int max_z = PVectorI3ITest.randomNegativeNumber();
      final PVectorI3I<T> maximum = new PVectorI3I<T>(max_x, max_y, max_z);

      final int x = PVectorI3ITest.randomNegativeNumber();
      final int y = PVectorI3ITest.randomNegativeNumber();
      final int z = PVectorI3ITest.randomNegativeNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clampMaximumByPVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI3ITest.randomPositiveNumber();
      final int min_y = PVectorI3ITest.randomPositiveNumber();
      final int min_z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> minimum = new PVectorI3I<T>(min_x, min_y, min_z);

      final int x = PVectorI3ITest.randomNegativeNumber();
      final int y = PVectorI3ITest.randomNegativeNumber();
      final int z = PVectorI3ITest.randomNegativeNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clampMinimumByPVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI3ITest.randomNegativeNumber();
      final int min_y = PVectorI3ITest.randomNegativeNumber();
      final int min_z = PVectorI3ITest.randomNegativeNumber();
      final PVectorI3I<T> minimum = new PVectorI3I<T>(min_x, min_y, min_z);

      final int max_x = PVectorI3ITest.randomPositiveNumber();
      final int max_y = PVectorI3ITest.randomPositiveNumber();
      final int max_z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> maximum = new PVectorI3I<T>(max_x, max_y, max_z);

      final int x = PVectorI3ITest.randomNegativeNumber();
      final int y = PVectorI3ITest.randomPositiveNumber();
      final int z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clampByPVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = PVectorI3ITest.randomNegativeNumber();

      final int x = PVectorI3ITest.randomPositiveNumber();
      final int y = PVectorI3ITest.randomPositiveNumber();
      final int z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI3ITest.randomPositiveNumber();

      final int x = PVectorI3ITest.randomNegativeNumber();
      final int y = PVectorI3ITest.randomNegativeNumber();
      final int z = PVectorI3ITest.randomNegativeNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI3ITest.randomNegativeNumber();
      final int maximum = PVectorI3ITest.randomPositiveNumber();

      final int x = PVectorI3ITest.randomNegativeNumber();
      final int y = PVectorI3ITest.randomPositiveNumber();
      final int z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorI3I<T> vb = new PVectorI3I<T>(5, 6, 7);
    final PVectorI3I<T> va = new PVectorI3I<T>(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    final PVectorI3I<T> vc = new PVectorI3I<T>(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());
    Assert.assertTrue(va.getZI() == vc.getZI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
    Assert.assertFalse(vc.getZI() == vb.getZI());
  }

  @Override public void testCrossProductPerpendicular()
  {
    // Not applicable.
  }

  @Override public void testDefault000()
  {
    Assert.assertTrue(new PVectorI3I<T>().equals(new PVectorI3I<T>(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorI3I<T> v0 = new PVectorI3I<T>(0, 1, 0);
    final PVectorI3I<T> v1 = new PVectorI3I<T>(0, 0, 0);
    Assert.assertTrue(PVectorI3I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v0 = new PVectorI3I<T>(x0, y0, z0);

      final int x1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI3ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v1 = new PVectorI3I<T>(x1, y1, z1);

      Assert.assertTrue(PVectorI3I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI3I<T> v0 = new PVectorI3I<T>(10, 10, 10);
    final PVectorI3I<T> v1 = new PVectorI3I<T>(10, 10, 10);

    {
      final int p = PVectorI3I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorI3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorI3I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
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
      final int z = (int) (Math.random() * max);
      final PVectorI3I<T> q = new PVectorI3I<T>(x, y, z);

      final double ms = PVectorI3I.magnitudeSquared(q);
      final double dp = PVectorI3I.dotProduct(q, q);

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
      final int z = (int) (Math.random() * max);
      final PVectorI3I<T> q = new PVectorI3I<T>(x, y, z);
      final double dp = PVectorI3I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorI3I<T> v0 = new PVectorI3I<T>(10, 10, 10);

    {
      final int p = PVectorI3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorI3I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>();
      final PVectorI3I<T> m1 = new PVectorI3I<T>();
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
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(x, y, z);
      final PVectorI3I<T> m1 = new PVectorI3I<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI3I<T> m0 = new PVectorI3I<T>();
    final PVectorI3I<T> m1 = new PVectorI3I<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(23, 0, 0);
      final PVectorI3I<T> m1 = new PVectorI3I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(0, 23, 0);
      final PVectorI3I<T> m1 = new PVectorI3I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3I<T> m0 = new PVectorI3I<T>(0, 0, 23);
      final PVectorI3I<T> m1 = new PVectorI3I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI3I<T> v0 = new PVectorI3I<T>(1, 2, 3);
    final PVectorI3I<T> v1 = new PVectorI3I<T>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI3ITest.randomPositiveNumber();
      final int y0 = PVectorI3ITest.randomPositiveNumber();
      final int z0 = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v0 = new PVectorI3I<T>(x0, y0, z0);

      final int x1 = PVectorI3ITest.randomPositiveNumber();
      final int y1 = PVectorI3ITest.randomPositiveNumber();
      final int z1 = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v1 = new PVectorI3I<T>(x1, y1, z1);

      final PVectorI3I<T> vr0 = PVectorI3I.interpolateLinear(v0, v1, 0);
      final PVectorI3I<T> vr1 = PVectorI3I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI3ITest.randomPositiveSmallNumber();
      final int y = PVectorI3ITest.randomPositiveSmallNumber();
      final int z = PVectorI3ITest.randomPositiveSmallNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final int m = PVectorI3I.magnitude(v);
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
    final PVectorI3I<T> v = new PVectorI3I<T>(1, 0, 0);
    final int m = PVectorI3I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI3I<T> v = new PVectorI3I<T>(8, 0, 0);

    {
      final int p = PVectorI3I.dotProduct(v, v);
      final int q = PVectorI3I.magnitudeSquared(v);
      final int r = PVectorI3I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorI3I<T> v = new PVectorI3I<T>(0, 0, 0);
    final int m = PVectorI3I.magnitude(v);
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
      final PVectorI3I<T> p = new PVectorI3I<T>(1, 0, 0);
      final PVectorI3I<T> q = new PVectorI3I<T>(0, 1, 0);
      final PVectorI3I<T> u = PVectorI3I.projection(p, q);

      Assert.assertTrue(PVectorI3I.magnitude(u) == 0);
    }

    {
      final PVectorI3I<T> p = new PVectorI3I<T>(-1, 0, 0);
      final PVectorI3I<T> q = new PVectorI3I<T>(0, 1, 0);
      final PVectorI3I<T> u = PVectorI3I.projection(p, q);

      Assert.assertTrue(PVectorI3I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI3ITest.randomPositiveNumber();
      final int y = PVectorI3ITest.randomPositiveNumber();
      final int z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI3ITest.randomPositiveNumber();
      final int y = PVectorI3ITest.randomPositiveNumber();
      final int z = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v = new PVectorI3I<T>(x, y, z);

      final PVectorI3I<T> vr = PVectorI3I.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI3I<T> v = new PVectorI3I<T>(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[PVectorI3I 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI3ITest.randomPositiveNumber();
      final int y0 = PVectorI3ITest.randomPositiveNumber();
      final int z0 = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v0 = new PVectorI3I<T>(x0, y0, z0);

      final int x1 = PVectorI3ITest.randomPositiveNumber();
      final int y1 = PVectorI3ITest.randomPositiveNumber();
      final int z1 = PVectorI3ITest.randomPositiveNumber();
      final PVectorI3I<T> v1 = new PVectorI3I<T>(x1, y1, z1);

      final PVectorI3I<T> vr0 = PVectorI3I.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));
    }
  }
}
