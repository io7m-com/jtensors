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
import com.io7m.jtensors.parameterized.PVectorI4I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI4ITest<T>
  extends PVectorI4Contract
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
    final PVectorI4I<Object> z = PVectorI4I.zero();
    Assert.assertEquals(0, z.getXI());
    Assert.assertEquals(0, z.getYI());
    Assert.assertEquals(0, z.getZI());
    Assert.assertEquals(0, z.getWI());
  }

  @Override @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final int w = (int) (Math.random() * Integer.MIN_VALUE);
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
      Assert.assertEquals(Math.abs(v.getWI()), vr.getWI());
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w0 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v0 = new PVectorI4I<T>(x0, y0, z0, w0);

      final int x1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w1 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v1 = new PVectorI4I<T>(x1, y1, z1, w1);

      final PVectorI4I<T> vr0 = PVectorI4I.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));
      Assert.assertTrue(vr0.getWI() == (v0.getWI() + v1.getWI()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w0 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v0 = new PVectorI4I<T>(x0, y0, z0, w0);

      final int x1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w1 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v1 = new PVectorI4I<T>(x1, y1, z1, w1);

      final int r = PVectorI4ITest.randomPositiveSmallNumber();

      final PVectorI4I<T> vr0 = PVectorI4I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));
      Assert.assertTrue(vr0.getWI() == (v0.getWI() + (v1.getWI() * r)));
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
    final PVectorI4I<T> v = new PVectorI4I<T>(3, 5, 7, 11);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
    Assert.assertTrue(v.getWI() == v.getWI());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = PVectorI4ITest.randomNegativeNumber();
      final int max_y = PVectorI4ITest.randomNegativeNumber();
      final int max_z = PVectorI4ITest.randomNegativeNumber();
      final int max_w = PVectorI4ITest.randomNegativeNumber();
      final PVectorI4I<T> maximum =
        new PVectorI4I<T>(max_x, max_y, max_z, max_w);

      final int x = PVectorI4ITest.randomNegativeNumber();
      final int y = PVectorI4ITest.randomNegativeNumber();
      final int z = PVectorI4ITest.randomNegativeNumber();
      final int w = PVectorI4ITest.randomNegativeNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clampMaximumByPVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getWI() <= maximum.getWI());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI4ITest.randomPositiveNumber();
      final int min_y = PVectorI4ITest.randomPositiveNumber();
      final int min_z = PVectorI4ITest.randomPositiveNumber();
      final int min_w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> minimum =
        new PVectorI4I<T>(min_x, min_y, min_z, min_w);

      final int x = PVectorI4ITest.randomNegativeNumber();
      final int y = PVectorI4ITest.randomNegativeNumber();
      final int z = PVectorI4ITest.randomNegativeNumber();
      final int w = PVectorI4ITest.randomNegativeNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clampMinimumByPVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
      Assert.assertTrue(vr.getWI() >= minimum.getWI());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorI4ITest.randomNegativeNumber();
      final int min_y = PVectorI4ITest.randomNegativeNumber();
      final int min_z = PVectorI4ITest.randomNegativeNumber();
      final int min_w = PVectorI4ITest.randomNegativeNumber();
      final PVectorI4I<T> minimum =
        new PVectorI4I<T>(min_x, min_y, min_z, min_w);

      final int max_x = PVectorI4ITest.randomPositiveNumber();
      final int max_y = PVectorI4ITest.randomPositiveNumber();
      final int max_z = PVectorI4ITest.randomPositiveNumber();
      final int max_w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> maximum =
        new PVectorI4I<T>(max_x, max_y, max_z, max_w);

      final int x = PVectorI4ITest.randomNegativeNumber();
      final int y = PVectorI4ITest.randomPositiveNumber();
      final int z = PVectorI4ITest.randomPositiveNumber();
      final int w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clampByPVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getWI() <= maximum.getWI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
      Assert.assertTrue(vr.getWI() >= minimum.getWI());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = PVectorI4ITest.randomNegativeNumber();

      final int x = PVectorI4ITest.randomPositiveNumber();
      final int y = PVectorI4ITest.randomPositiveNumber();
      final int z = PVectorI4ITest.randomPositiveNumber();
      final int w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getWI() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI4ITest.randomPositiveNumber();

      final int x = PVectorI4ITest.randomNegativeNumber();
      final int y = PVectorI4ITest.randomNegativeNumber();
      final int z = PVectorI4ITest.randomNegativeNumber();
      final int w = PVectorI4ITest.randomNegativeNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);
      Assert.assertTrue(vr.getWI() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorI4ITest.randomNegativeNumber();
      final int maximum = PVectorI4ITest.randomPositiveNumber();

      final int x = PVectorI4ITest.randomNegativeNumber();
      final int y = PVectorI4ITest.randomPositiveNumber();
      final int z = PVectorI4ITest.randomPositiveNumber();
      final int w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);
      Assert.assertTrue(vr.getWI() <= maximum);
      Assert.assertTrue(vr.getWI() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorI4I<T> vb = new PVectorI4I<T>(5, 6, 7, 8);
    final PVectorI4I<T> va = new PVectorI4I<T>(1, 2, 3, 4);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());
    Assert.assertFalse(va.getWI() == vb.getWI());

    final PVectorI4I<T> vc = new PVectorI4I<T>(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());
    Assert.assertTrue(va.getZI() == vc.getZI());
    Assert.assertTrue(va.getWI() == vc.getWI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
    Assert.assertFalse(vc.getZI() == vb.getZI());
    Assert.assertFalse(vc.getWI() == vb.getWI());
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(
      new PVectorI4I<T>().equals(new PVectorI4I<T>(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorI4I<T> v0 = new PVectorI4I<T>(0, 1, 0, 0);
    final PVectorI4I<T> v1 = new PVectorI4I<T>(0, 0, 0, 0);
    Assert.assertTrue(PVectorI4I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z0 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w0 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v0 = new PVectorI4I<T>(x0, y0, z0, w0);

      final int x1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int y1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int z1 = PVectorI4ITest.randomPositiveSmallNumber();
      final int w1 = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v1 = new PVectorI4I<T>(x1, y1, z1, w1);

      Assert.assertTrue(PVectorI4I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI4I<T> v0 = new PVectorI4I<T>(10, 10, 10, 10);
    final PVectorI4I<T> v1 = new PVectorI4I<T>(10, 10, 10, 10);

    {
      final int p = PVectorI4I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v0.getWI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(v1.getWI() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = PVectorI4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v0.getWI() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = PVectorI4I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(v1.getWI() == 10);
      Assert.assertTrue(p == 400);
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
      final int w = (int) (Math.random() * max);
      final PVectorI4I<T> q = new PVectorI4I<T>(x, y, z, w);

      final double ms = PVectorI4I.magnitudeSquared(q);
      final double dp = PVectorI4I.dotProduct(q, q);

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
      final int w = (int) (Math.random() * max);
      final PVectorI4I<T> q = new PVectorI4I<T>(x, y, z, w);
      final double dp = PVectorI4I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorI4I<T> v0 = new PVectorI4I<T>(10, 10, 10, 10);

    {
      final int p = PVectorI4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v0.getWI() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = PVectorI4I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v0.getWI() == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>();
      final PVectorI4I<T> m1 = new PVectorI4I<T>();
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
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(x, y, z, w);
      final PVectorI4I<T> m1 = new PVectorI4I<T>(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI4I<T> m0 = new PVectorI4I<T>();
    final PVectorI4I<T> m1 = new PVectorI4I<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(23, 0, 0, 0);
      final PVectorI4I<T> m1 = new PVectorI4I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(0, 23, 0, 0);
      final PVectorI4I<T> m1 = new PVectorI4I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(0, 0, 23, 0);
      final PVectorI4I<T> m1 = new PVectorI4I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4I<T> m0 = new PVectorI4I<T>(0, 0, 0, 23);
      final PVectorI4I<T> m1 = new PVectorI4I<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI4I<T> v0 = new PVectorI4I<T>(1, 2, 3, 4);
    final PVectorI4I<T> v1 = new PVectorI4I<T>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
    Assert.assertTrue(v0.getWI() == v1.getWI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI4ITest.randomPositiveNumber();
      final int y0 = PVectorI4ITest.randomPositiveNumber();
      final int z0 = PVectorI4ITest.randomPositiveNumber();
      final int w0 = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v0 = new PVectorI4I<T>(x0, y0, z0, w0);

      final int x1 = PVectorI4ITest.randomPositiveNumber();
      final int y1 = PVectorI4ITest.randomPositiveNumber();
      final int z1 = PVectorI4ITest.randomPositiveNumber();
      final int w1 = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v1 = new PVectorI4I<T>(x1, y1, z1, w1);

      final PVectorI4I<T> vr0 = PVectorI4I.interpolateLinear(v0, v1, 0);
      final PVectorI4I<T> vr1 = PVectorI4I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());
      Assert.assertTrue(v0.getWI() == vr0.getWI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
      Assert.assertTrue(v1.getWI() == vr1.getWI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI4ITest.randomPositiveSmallNumber();
      final int y = PVectorI4ITest.randomPositiveSmallNumber();
      final int z = PVectorI4ITest.randomPositiveSmallNumber();
      final int w = PVectorI4ITest.randomPositiveSmallNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final int m = PVectorI4I.magnitude(v);
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
    final PVectorI4I<T> v = new PVectorI4I<T>(1, 0, 0, 0);
    final int m = PVectorI4I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI4I<T> v = new PVectorI4I<T>(8, 0, 0, 0);

    {
      final int p = PVectorI4I.dotProduct(v, v);
      final int q = PVectorI4I.magnitudeSquared(v);
      final int r = PVectorI4I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorI4I<T> v = new PVectorI4I<T>(0, 0, 0, 0);
    final int m = PVectorI4I.magnitude(v);
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
      final PVectorI4I<T> p = new PVectorI4I<T>(1, 0, 0, 1);
      final PVectorI4I<T> q = new PVectorI4I<T>(0, 1, 0, 1);
      final PVectorI4I<T> u = PVectorI4I.projection(p, q);

      Assert.assertTrue(PVectorI4I.magnitude(u) == 0);
    }

    {
      final PVectorI4I<T> p = new PVectorI4I<T>(-1, 0, 0, 1);
      final PVectorI4I<T> q = new PVectorI4I<T>(0, 1, 0, 1);
      final PVectorI4I<T> u = PVectorI4I.projection(p, q);

      Assert.assertTrue(PVectorI4I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI4ITest.randomPositiveNumber();
      final int y = PVectorI4ITest.randomPositiveNumber();
      final int z = PVectorI4ITest.randomPositiveNumber();
      final int w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());
      Assert.assertTrue(v.getWI() == vr.getWI());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorI4ITest.randomPositiveNumber();
      final int y = PVectorI4ITest.randomPositiveNumber();
      final int z = PVectorI4ITest.randomPositiveNumber();
      final int w = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v = new PVectorI4I<T>(x, y, z, w);

      final PVectorI4I<T> vr = PVectorI4I.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);
      Assert.assertTrue(vr.getWI() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI4I<T> v = new PVectorI4I<T>(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[PVectorI4I 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorI4ITest.randomPositiveNumber();
      final int y0 = PVectorI4ITest.randomPositiveNumber();
      final int z0 = PVectorI4ITest.randomPositiveNumber();
      final int w0 = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v0 = new PVectorI4I<T>(x0, y0, z0, w0);

      final int x1 = PVectorI4ITest.randomPositiveNumber();
      final int y1 = PVectorI4ITest.randomPositiveNumber();
      final int z1 = PVectorI4ITest.randomPositiveNumber();
      final int w1 = PVectorI4ITest.randomPositiveNumber();
      final PVectorI4I<T> v1 = new PVectorI4I<T>(x1, y1, z1, w1);

      final PVectorI4I<T> vr0 = PVectorI4I.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));
      Assert.assertTrue(vr0.getWI() == (v0.getWI() - v1.getWI()));
    }
  }
}
