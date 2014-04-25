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

package com.io7m.jtensors.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.VectorI3L;

public class VectorI3LTest extends VectorI3Contract
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
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.absolute(v);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI3LTest.randomPositiveSmallNumber();
      final long y0 = VectorI3LTest.randomPositiveSmallNumber();
      final long z0 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v0 = new VectorI3L(x0, y0, z0);

      final long x1 = VectorI3LTest.randomPositiveSmallNumber();
      final long y1 = VectorI3LTest.randomPositiveSmallNumber();
      final long z1 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v1 = new VectorI3L(x1, y1, z1);

      final VectorI3L vr0 = VectorI3L.add(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + v1.getZL()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI3LTest.randomPositiveSmallNumber();
      final long y0 = VectorI3LTest.randomPositiveSmallNumber();
      final long z0 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v0 = new VectorI3L(x0, y0, z0);

      final long x1 = VectorI3LTest.randomPositiveSmallNumber();
      final long y1 = VectorI3LTest.randomPositiveSmallNumber();
      final long z1 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v1 = new VectorI3L(x1, y1, z1);

      final long r = VectorI3LTest.randomPositiveSmallNumber();

      final VectorI3L vr0 = VectorI3L.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + (v1.getZL() * r)));
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
    final VectorI3L v = new VectorI3L(3, 5, 7);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
    Assert.assertTrue(v.getZL() == v.getZL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorI3LTest.randomNegativeNumber();
      final long max_y = VectorI3LTest.randomNegativeNumber();
      final long max_z = VectorI3LTest.randomNegativeNumber();
      final VectorI3L maximum = new VectorI3L(max_x, max_y, max_z);

      final long x = VectorI3LTest.randomNegativeNumber();
      final long y = VectorI3LTest.randomNegativeNumber();
      final long z = VectorI3LTest.randomNegativeNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI3LTest.randomPositiveNumber();
      final long min_y = VectorI3LTest.randomPositiveNumber();
      final long min_z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L minimum = new VectorI3L(min_x, min_y, min_z);

      final long x = VectorI3LTest.randomNegativeNumber();
      final long y = VectorI3LTest.randomNegativeNumber();
      final long z = VectorI3LTest.randomNegativeNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI3LTest.randomNegativeNumber();
      final long min_y = VectorI3LTest.randomNegativeNumber();
      final long min_z = VectorI3LTest.randomNegativeNumber();
      final VectorI3L minimum = new VectorI3L(min_x, min_y, min_z);

      final long max_x = VectorI3LTest.randomPositiveNumber();
      final long max_y = VectorI3LTest.randomPositiveNumber();
      final long max_z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L maximum = new VectorI3L(max_x, max_y, max_z);

      final long x = VectorI3LTest.randomNegativeNumber();
      final long y = VectorI3LTest.randomPositiveNumber();
      final long z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorI3LTest.randomNegativeNumber();

      final long x = VectorI3LTest.randomPositiveNumber();
      final long y = VectorI3LTest.randomPositiveNumber();
      final long z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI3LTest.randomPositiveNumber();

      final long x = VectorI3LTest.randomNegativeNumber();
      final long y = VectorI3LTest.randomNegativeNumber();
      final long z = VectorI3LTest.randomNegativeNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI3LTest.randomNegativeNumber();
      final long maximum = VectorI3LTest.randomPositiveNumber();

      final long x = VectorI3LTest.randomNegativeNumber();
      final long y = VectorI3LTest.randomPositiveNumber();
      final long z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI3L vb = new VectorI3L(5, 6, 7);
    final VectorI3L va = new VectorI3L(1, 2, 3);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());

    final VectorI3L vc = new VectorI3L(va);

    Assert.assertTrue(va.getXL() == vc.getXL());
    Assert.assertTrue(va.getYL() == vc.getYL());
    Assert.assertTrue(va.getZL() == vc.getZL());

    Assert.assertFalse(vc.getXL() == vb.getXL());
    Assert.assertFalse(vc.getYL() == vb.getYL());
    Assert.assertFalse(vc.getZL() == vb.getZL());
  }

  @Override public void testCrossProductPerpendicular()
  {
    // Not applicable.
  }

  @Override public void testDefault000()
  {
    Assert.assertTrue(new VectorI3L().equals(new VectorI3L(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI3L v0 = new VectorI3L(0, 1, 0);
    final VectorI3L v1 = new VectorI3L(0, 0, 0);
    Assert.assertTrue(VectorI3L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI3LTest.randomPositiveSmallNumber();
      final long y0 = VectorI3LTest.randomPositiveSmallNumber();
      final long z0 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v0 = new VectorI3L(x0, y0, z0);

      final long x1 = VectorI3LTest.randomPositiveSmallNumber();
      final long y1 = VectorI3LTest.randomPositiveSmallNumber();
      final long z1 = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v1 = new VectorI3L(x1, y1, z1);

      Assert.assertTrue(VectorI3L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI3L v0 = new VectorI3L(10, 10, 10);
    final VectorI3L v1 = new VectorI3L(10, 10, 10);

    {
      final long p = VectorI3L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorI3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorI3L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(p == 300);
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
      final long z = (long) (Math.random() * max);
      final VectorI3L q = new VectorI3L(x, y, z);

      final double ms = VectorI3L.magnitudeSquared(q);
      final double dp = VectorI3L.dotProduct(q, q);

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
      final long z = (long) (Math.random() * max);
      final VectorI3L q = new VectorI3L(x, y, z);
      final double dp = VectorI3L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI3L v0 = new VectorI3L(10, 10, 10);

    {
      final long p = VectorI3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorI3L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI3L m0 = new VectorI3L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI3L m0 = new VectorI3L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3L m0 = new VectorI3L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI3L m0 = new VectorI3L();
      final VectorI3L m1 = new VectorI3L();
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
      final VectorI3L m0 = new VectorI3L(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3L m0 = new VectorI3L(x, y, z);
      final VectorI3L m1 = new VectorI3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI3L m0 = new VectorI3L();
    final VectorI3L m1 = new VectorI3L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3L m0 = new VectorI3L(23, 0, 0);
      final VectorI3L m1 = new VectorI3L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3L m0 = new VectorI3L(0, 23, 0);
      final VectorI3L m1 = new VectorI3L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3L m0 = new VectorI3L(0, 0, 23);
      final VectorI3L m1 = new VectorI3L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI3L v0 = new VectorI3L(1, 2, 3);
    final VectorI3L v1 = new VectorI3L(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
    Assert.assertTrue(v0.getZL() == v1.getZL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI3LTest.randomPositiveNumber();
      final long y0 = VectorI3LTest.randomPositiveNumber();
      final long z0 = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v0 = new VectorI3L(x0, y0, z0);

      final long x1 = VectorI3LTest.randomPositiveNumber();
      final long y1 = VectorI3LTest.randomPositiveNumber();
      final long z1 = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v1 = new VectorI3L(x1, y1, z1);

      final VectorI3L vr0 = VectorI3L.interpolateLinear(v0, v1, 0);
      final VectorI3L vr1 = VectorI3L.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());
      Assert.assertTrue(v0.getZL() == vr0.getZL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
      Assert.assertTrue(v1.getZL() == vr1.getZL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI3LTest.randomPositiveSmallNumber();
      final long y = VectorI3LTest.randomPositiveSmallNumber();
      final long z = VectorI3LTest.randomPositiveSmallNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final long m = VectorI3L.magnitude(v);
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
    final VectorI3L v = new VectorI3L(1, 0, 0);
    final long m = VectorI3L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI3L v = new VectorI3L(8, 0, 0);

    {
      final long p = VectorI3L.dotProduct(v, v);
      final long q = VectorI3L.magnitudeSquared(v);
      final long r = VectorI3L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI3L v = new VectorI3L(0, 0, 0);
    final long m = VectorI3L.magnitude(v);
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
      final VectorI3L p = new VectorI3L(1, 0, 0);
      final VectorI3L q = new VectorI3L(0, 1, 0);
      final VectorI3L u = VectorI3L.projection(p, q);

      Assert.assertTrue(VectorI3L.magnitude(u) == 0);
    }

    {
      final VectorI3L p = new VectorI3L(-1, 0, 0);
      final VectorI3L q = new VectorI3L(0, 1, 0);
      final VectorI3L u = VectorI3L.projection(p, q);

      Assert.assertTrue(VectorI3L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI3LTest.randomPositiveNumber();
      final long y = VectorI3LTest.randomPositiveNumber();
      final long z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.scale(v, 1);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
      Assert.assertTrue(v.getZL() == vr.getZL());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI3LTest.randomPositiveNumber();
      final long y = VectorI3LTest.randomPositiveNumber();
      final long z = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v = new VectorI3L(x, y, z);

      final VectorI3L vr = VectorI3L.scale(v, 0);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
      Assert.assertTrue(vr.getZL() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI3L v = new VectorI3L(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorI3L 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI3LTest.randomPositiveNumber();
      final long y0 = VectorI3LTest.randomPositiveNumber();
      final long z0 = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v0 = new VectorI3L(x0, y0, z0);

      final long x1 = VectorI3LTest.randomPositiveNumber();
      final long y1 = VectorI3LTest.randomPositiveNumber();
      final long z1 = VectorI3LTest.randomPositiveNumber();
      final VectorI3L v1 = new VectorI3L(x1, y1, z1);

      final VectorI3L vr0 = VectorI3L.subtract(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() - v1.getZL()));
    }
  }
}
