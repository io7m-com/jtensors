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
import com.io7m.jtensors.VectorReadable3LType;
import com.io7m.jtensors.parameterized.PVectorI3L;
import com.io7m.jtensors.parameterized.PVectorReadable3LType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI3LTest<T>
  extends PVectorI3Contract
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
    final PVectorI3L<Object> z = PVectorI3L.zero();
    Assert.assertEquals(0, z.getXL());
    Assert.assertEquals(0, z.getYL());
    Assert.assertEquals(0, z.getZL());
  }

  @Override @Test public void testAbsolute()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final PVectorI3L<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.absolute(v);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = randomPositiveSmallNumber();
      final long y0 = randomPositiveSmallNumber();
      final long z0 = randomPositiveSmallNumber();
      final PVectorI3L<T> v0 = new PVectorI3L<T>(x0, y0, z0);

      final long x1 = randomPositiveSmallNumber();
      final long y1 = randomPositiveSmallNumber();
      final long z1 = randomPositiveSmallNumber();
      final PVectorI3L<T> v1 = new PVectorI3L<T>(x1, y1, z1);

      final PVectorI3L<T> vr0 = PVectorI3L.add(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + v1.getZL()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = randomPositiveSmallNumber();
      final long y0 = randomPositiveSmallNumber();
      final long z0 = randomPositiveSmallNumber();
      final PVectorI3L<T> v0 = new PVectorI3L<T>(x0, y0, z0);

      final long x1 = randomPositiveSmallNumber();
      final long y1 = randomPositiveSmallNumber();
      final long z1 = randomPositiveSmallNumber();
      final PVectorI3L<T> v1 = new PVectorI3L<T>(x1, y1, z1);

      final long r = randomPositiveSmallNumber();

      final PVectorI3L<T> vr0 = PVectorI3L.addScaled(v0, v1, r);

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
    final VectorReadable3LType v = new PVectorI3L<T>(3, 5, 7);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
    Assert.assertTrue(v.getZL() == v.getZL());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = randomNegativeNumber();
      final long max_y = randomNegativeNumber();
      final long max_z = randomNegativeNumber();
      final PVectorI3L<T> maximum = new PVectorI3L<T>(max_x, max_y, max_z);

      final long x = randomNegativeNumber();
      final long y = randomNegativeNumber();
      final long z = randomNegativeNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clampMaximumByPVector(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = randomPositiveNumber();
      final long min_y = randomPositiveNumber();
      final long min_z = randomPositiveNumber();
      final PVectorI3L<T> minimum = new PVectorI3L<T>(min_x, min_y, min_z);

      final long x = randomNegativeNumber();
      final long y = randomNegativeNumber();
      final long z = randomNegativeNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clampMinimumByPVector(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = randomNegativeNumber();
      final long min_y = randomNegativeNumber();
      final long min_z = randomNegativeNumber();
      final PVectorI3L<T> minimum = new PVectorI3L<T>(min_x, min_y, min_z);

      final long max_x = randomPositiveNumber();
      final long max_y = randomPositiveNumber();
      final long max_z = randomPositiveNumber();
      final PVectorI3L<T> maximum = new PVectorI3L<T>(max_x, max_y, max_z);

      final long x = randomNegativeNumber();
      final long y = randomPositiveNumber();
      final long z = randomPositiveNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clampByPVector(v, minimum, maximum);

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
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = randomNegativeNumber();

      final long x = randomPositiveNumber();
      final long y = randomPositiveNumber();
      final long z = randomPositiveNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = randomPositiveNumber();

      final long x = randomNegativeNumber();
      final long y = randomNegativeNumber();
      final long z = randomNegativeNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = randomNegativeNumber();
      final long maximum = randomPositiveNumber();

      final long x = randomNegativeNumber();
      final long y = randomPositiveNumber();
      final long z = randomPositiveNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.clamp(v, minimum, maximum);

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
    final PVectorI3L<T> vb = new PVectorI3L<T>(5, 6, 7);
    final PVectorI3L<T> va = new PVectorI3L<T>(1, 2, 3);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());

    final PVectorI3L<T> vc = new PVectorI3L<T>(va);

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
    Assert.assertTrue(new PVectorI3L<T>().equals(new PVectorI3L<T>(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorReadable3LType<T> v0 = new PVectorI3L<T>(0, 1, 0);
    final PVectorReadable3LType<T> v1 = new PVectorI3L<T>(0, 0, 0);
    Assert.assertTrue(PVectorI3L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = randomPositiveSmallNumber();
      final long y0 = randomPositiveSmallNumber();
      final long z0 = randomPositiveSmallNumber();
      final PVectorReadable3LType<T> v0 = new PVectorI3L<T>(x0, y0, z0);

      final long x1 = randomPositiveSmallNumber();
      final long y1 = randomPositiveSmallNumber();
      final long z1 = randomPositiveSmallNumber();
      final PVectorReadable3LType<T> v1 = new PVectorI3L<T>(x1, y1, z1);

      Assert.assertTrue(PVectorI3L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI3L<T> v0 = new PVectorI3L<T>(10, 10, 10);
    final PVectorI3L<T> v1 = new PVectorI3L<T>(10, 10, 10);

    {
      final long p = PVectorI3L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorI3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorI3L.dotProduct(v1, v1);
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

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final long z = (long) (Math.random() * max);
      final PVectorReadable3LType<T> q = new PVectorI3L<T>(x, y, z);

      final double ms = PVectorI3L.magnitudeSquared(q);
      final double dp = PVectorI3L.dotProduct(q, q);

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
      final long z = (long) (Math.random() * max);
      final PVectorReadable3LType<T> q = new PVectorI3L<T>(x, y, z);
      final double dp = PVectorI3L.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorI3L<T> v0 = new PVectorI3L<T>(10, 10, 10);

    {
      final long p = PVectorI3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorI3L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>();
      final PVectorI3L<T> m1 = new PVectorI3L<T>();
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
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(x, y, z);
      final PVectorI3L<T> m1 = new PVectorI3L<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI3L<T> m0 = new PVectorI3L<T>();
    final PVectorI3L<T> m1 = new PVectorI3L<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(23, 0, 0);
      final PVectorI3L<T> m1 = new PVectorI3L<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(0, 23, 0);
      final PVectorI3L<T> m1 = new PVectorI3L<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3L<T> m0 = new PVectorI3L<T>(0, 0, 23);
      final PVectorI3L<T> m1 = new PVectorI3L<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI3L<T> v0 = new PVectorI3L<T>(1, 2, 3);
    final VectorReadable3LType v1 = new PVectorI3L<T>(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
    Assert.assertTrue(v0.getZL() == v1.getZL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = randomPositiveNumber();
      final long y0 = randomPositiveNumber();
      final long z0 = randomPositiveNumber();
      final PVectorI3L<T> v0 = new PVectorI3L<T>(x0, y0, z0);

      final long x1 = randomPositiveNumber();
      final long y1 = randomPositiveNumber();
      final long z1 = randomPositiveNumber();
      final PVectorI3L<T> v1 = new PVectorI3L<T>(x1, y1, z1);

      final PVectorI3L<T> vr0 = PVectorI3L.interpolateLinear(v0, v1, 0);
      final PVectorI3L<T> vr1 = PVectorI3L.interpolateLinear(v0, v1, 1);

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
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = randomPositiveSmallNumber();
      final long y = randomPositiveSmallNumber();
      final long z = randomPositiveSmallNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final long m = PVectorI3L.magnitude(v);
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
    final PVectorReadable3LType<T> v = new PVectorI3L<T>(1, 0, 0);
    final long m = PVectorI3L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorReadable3LType<T> v = new PVectorI3L<T>(8, 0, 0);

    {
      final long p = PVectorI3L.dotProduct(v, v);
      final long q = PVectorI3L.magnitudeSquared(v);
      final long r = PVectorI3L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorReadable3LType<T> v = new PVectorI3L<T>(0, 0, 0);
    final long m = PVectorI3L.magnitude(v);
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
      final PVectorReadable3LType<T> p = new PVectorI3L<T>(1, 0, 0);
      final PVectorReadable3LType<T> q = new PVectorI3L<T>(0, 1, 0);
      final PVectorI3L<T> u = PVectorI3L.projection(p, q);

      Assert.assertTrue(PVectorI3L.magnitude(u) == 0);
    }

    {
      final PVectorReadable3LType<T> p = new PVectorI3L<T>(-1, 0, 0);
      final PVectorReadable3LType<T> q = new PVectorI3L<T>(0, 1, 0);
      final PVectorI3L<T> u = PVectorI3L.projection(p, q);

      Assert.assertTrue(PVectorI3L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = randomPositiveNumber();
      final long y = randomPositiveNumber();
      final long z = randomPositiveNumber();
      final PVectorI3L<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.scale(v, 1);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
      Assert.assertTrue(v.getZL() == vr.getZL());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = randomPositiveNumber();
      final long y = randomPositiveNumber();
      final long z = randomPositiveNumber();
      final PVectorReadable3LType<T> v = new PVectorI3L<T>(x, y, z);

      final PVectorI3L<T> vr = PVectorI3L.scale(v, 0);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
      Assert.assertTrue(vr.getZL() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI3L<T> v = new PVectorI3L<T>(1, 2, 3);
    Assert.assertTrue("[PVectorI3L 1 2 3]".equals(v.toString()));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = randomPositiveNumber();
      final long y0 = randomPositiveNumber();
      final long z0 = randomPositiveNumber();
      final PVectorI3L<T> v0 = new PVectorI3L<T>(x0, y0, z0);

      final long x1 = randomPositiveNumber();
      final long y1 = randomPositiveNumber();
      final long z1 = randomPositiveNumber();
      final PVectorI3L<T> v1 = new PVectorI3L<T>(x1, y1, z1);

      final PVectorI3L<T> vr0 = PVectorI3L.subtract(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() - v1.getZL()));
    }
  }
}
