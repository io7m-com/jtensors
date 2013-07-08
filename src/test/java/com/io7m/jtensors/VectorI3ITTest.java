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

public class VectorI3ITTest extends VectorI3TContract
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
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
    }
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v0 = new VectorI3IT<A>(x0, y0, z0);

      final int x1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v1 = new VectorI3IT<A>(x1, y1, z1);

      final VectorI3IT<A> vr0 = VectorI3IT.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));
    }
  }

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v0 = new VectorI3IT<A>(x0, y0, z0);

      final int x1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v1 = new VectorI3IT<A>(x1, y1, z1);

      final int r = VectorI3ITTest.randomPositiveSmallNumber();

      final VectorI3IT<A> vr0 = VectorI3IT.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));
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

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI3IT<A> v = new VectorI3IT<A>(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI3ITTest.randomNegativeNumber();
      final int max_y = VectorI3ITTest.randomNegativeNumber();
      final int max_z = VectorI3ITTest.randomNegativeNumber();
      final VectorI3IT<A> maximum = new VectorI3IT<A>(max_x, max_y, max_z);

      final int x = VectorI3ITTest.randomNegativeNumber();
      final int y = VectorI3ITTest.randomNegativeNumber();
      final int z = VectorI3ITTest.randomNegativeNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI3ITTest.randomPositiveNumber();
      final int min_y = VectorI3ITTest.randomPositiveNumber();
      final int min_z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> minimum = new VectorI3IT<A>(min_x, min_y, min_z);

      final int x = VectorI3ITTest.randomNegativeNumber();
      final int y = VectorI3ITTest.randomNegativeNumber();
      final int z = VectorI3ITTest.randomNegativeNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI3ITTest.randomNegativeNumber();
      final int min_y = VectorI3ITTest.randomNegativeNumber();
      final int min_z = VectorI3ITTest.randomNegativeNumber();
      final VectorI3IT<A> minimum = new VectorI3IT<A>(min_x, min_y, min_z);

      final int max_x = VectorI3ITTest.randomPositiveNumber();
      final int max_y = VectorI3ITTest.randomPositiveNumber();
      final int max_z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> maximum = new VectorI3IT<A>(max_x, max_y, max_z);

      final int x = VectorI3ITTest.randomNegativeNumber();
      final int y = VectorI3ITTest.randomPositiveNumber();
      final int z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI3ITTest.randomNegativeNumber();

      final int x = VectorI3ITTest.randomPositiveNumber();
      final int y = VectorI3ITTest.randomPositiveNumber();
      final int z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI3ITTest.randomPositiveNumber();

      final int x = VectorI3ITTest.randomNegativeNumber();
      final int y = VectorI3ITTest.randomNegativeNumber();
      final int z = VectorI3ITTest.randomNegativeNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI3ITTest.randomNegativeNumber();
      final int maximum = VectorI3ITTest.randomPositiveNumber();

      final int x = VectorI3ITTest.randomNegativeNumber();
      final int y = VectorI3ITTest.randomPositiveNumber();
      final int z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorI3IT<A> vb = new VectorI3IT<A>(5, 6, 7);
    final VectorI3IT<A> va = new VectorI3IT<A>(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    final VectorI3IT<A> vc = new VectorI3IT<A>(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());
    Assert.assertTrue(va.getZI() == vc.getZI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
    Assert.assertFalse(vc.getZI() == vb.getZI());
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorI3IT<A> v0 = new VectorI3IT<A>(0, 1, 0);
    final VectorI3IT<A> v1 = new VectorI3IT<A>(0, 0, 0);
    Assert.assertTrue(VectorI3IT.distance(v0, v1) == 1);
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v0 = new VectorI3IT<A>(x0, y0, z0);

      final int x1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorI3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v1 = new VectorI3IT<A>(x1, y1, z1);

      Assert.assertTrue(VectorI3IT.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI3IT<A> v0 = new VectorI3IT<A>(10, 10, 10);
    final VectorI3IT<A> v1 = new VectorI3IT<A>(10, 10, 10);

    {
      final int p = VectorI3IT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3IT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
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
      final int z = (int) (Math.random() * max);
      final VectorI3IT<A> q = new VectorI3IT<A>(x, y, z);

      final double ms = VectorI3IT.magnitudeSquared(q);
      final double dp = VectorI3IT.dotProduct(q, q);

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
      final int z = (int) (Math.random() * max);
      final VectorI3IT<A> q = new VectorI3IT<A>(x, y, z);
      final double dp = VectorI3IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorI3IT<A> v0 = new VectorI3IT<A>(10, 10, 10);

    {
      final int p = VectorI3IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorI3IT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>();
      final VectorI3IT<A> m1 = new VectorI3IT<A>();
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
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(x, y, z);
      final VectorI3IT<A> m1 = new VectorI3IT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI3IT<A> m0 = new VectorI3IT<A>();
    final VectorI3IT<A> m1 = new VectorI3IT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(23, 0, 0);
      final VectorI3IT<A> m1 = new VectorI3IT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(0, 23, 0);
      final VectorI3IT<A> m1 = new VectorI3IT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3IT<A> m0 = new VectorI3IT<A>(0, 0, 23);
      final VectorI3IT<A> m1 = new VectorI3IT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI3IT<A> v0 = new VectorI3IT<A>(1, 2, 3);
    final VectorI3IT<A> v1 = new VectorI3IT<A>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITTest.randomPositiveNumber();
      final int y0 = VectorI3ITTest.randomPositiveNumber();
      final int z0 = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v0 = new VectorI3IT<A>(x0, y0, z0);

      final int x1 = VectorI3ITTest.randomPositiveNumber();
      final int y1 = VectorI3ITTest.randomPositiveNumber();
      final int z1 = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v1 = new VectorI3IT<A>(x1, y1, z1);

      final VectorI3IT<A> vr0 = VectorI3IT.interpolateLinear(v0, v1, 0);
      final VectorI3IT<A> vr1 = VectorI3IT.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITTest.randomPositiveSmallNumber();
      final int y = VectorI3ITTest.randomPositiveSmallNumber();
      final int z = VectorI3ITTest.randomPositiveSmallNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final int m = VectorI3IT.magnitude(v);
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
    final VectorI3IT<A> v = new VectorI3IT<A>(1, 0, 0);
    final int m = VectorI3IT.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI3IT<A> v = new VectorI3IT<A>(8, 0, 0);

    {
      final int p = VectorI3IT.dotProduct(v, v);
      final int q = VectorI3IT.magnitudeSquared(v);
      final int r = VectorI3IT.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final VectorI3IT<A> v = new VectorI3IT<A>(0, 0, 0);
    final int m = VectorI3IT.magnitude(v);
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
      final VectorI3IT<A> p = new VectorI3IT<A>(1, 0, 0);
      final VectorI3IT<A> q = new VectorI3IT<A>(0, 1, 0);
      final VectorI3IT<A> u = VectorI3IT.projection(p, q);

      Assert.assertTrue(VectorI3IT.magnitude(u) == 0);
    }

    {
      final VectorI3IT<A> p = new VectorI3IT<A>(-1, 0, 0);
      final VectorI3IT<A> q = new VectorI3IT<A>(0, 1, 0);
      final VectorI3IT<A> u = VectorI3IT.projection(p, q);

      Assert.assertTrue(VectorI3IT.magnitude(u) == 0);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITTest.randomPositiveNumber();
      final int y = VectorI3ITTest.randomPositiveNumber();
      final int z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI3ITTest.randomPositiveNumber();
      final int y = VectorI3ITTest.randomPositiveNumber();
      final int z = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v = new VectorI3IT<A>(x, y, z);

      final VectorI3IT<A> vr = VectorI3IT.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI3IT<A> v = new VectorI3IT<A>(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorI3IT 1 2 3]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI3ITTest.randomPositiveNumber();
      final int y0 = VectorI3ITTest.randomPositiveNumber();
      final int z0 = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v0 = new VectorI3IT<A>(x0, y0, z0);

      final int x1 = VectorI3ITTest.randomPositiveNumber();
      final int y1 = VectorI3ITTest.randomPositiveNumber();
      final int z1 = VectorI3ITTest.randomPositiveNumber();
      final VectorI3IT<A> v1 = new VectorI3IT<A>(x1, y1, z1);

      final VectorI3IT<A> vr0 = VectorI3IT.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));
    }
  }

  @Override public <A> void testCrossProductPerpendicular()
  {
    // Not applicable.
  }

  @Override public <A> void testDefault000()
  {
    Assert.assertTrue(new VectorI3IT<A>().equals(new VectorI3IT<A>(0, 0, 0)));
  }
}
