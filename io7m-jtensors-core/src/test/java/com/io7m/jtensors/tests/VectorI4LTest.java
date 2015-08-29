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
import com.io7m.jtensors.VectorI4L;

public class VectorI4LTest extends VectorI4Contract
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
      final long w = (long) (Math.random() * Long.MIN_VALUE);
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.absolute(v);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
      Assert.assertEquals(Math.abs(v.getWL()), vr.getWL());
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI4LTest.randomPositiveSmallNumber();
      final long y0 = VectorI4LTest.randomPositiveSmallNumber();
      final long z0 = VectorI4LTest.randomPositiveSmallNumber();
      final long w0 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v0 = new VectorI4L(x0, y0, z0, w0);

      final long x1 = VectorI4LTest.randomPositiveSmallNumber();
      final long y1 = VectorI4LTest.randomPositiveSmallNumber();
      final long z1 = VectorI4LTest.randomPositiveSmallNumber();
      final long w1 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v1 = new VectorI4L(x1, y1, z1, w1);

      final VectorI4L vr0 = VectorI4L.add(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + v1.getZL()));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() + v1.getWL()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI4LTest.randomPositiveSmallNumber();
      final long y0 = VectorI4LTest.randomPositiveSmallNumber();
      final long z0 = VectorI4LTest.randomPositiveSmallNumber();
      final long w0 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v0 = new VectorI4L(x0, y0, z0, w0);

      final long x1 = VectorI4LTest.randomPositiveSmallNumber();
      final long y1 = VectorI4LTest.randomPositiveSmallNumber();
      final long z1 = VectorI4LTest.randomPositiveSmallNumber();
      final long w1 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v1 = new VectorI4L(x1, y1, z1, w1);

      final long r = VectorI4LTest.randomPositiveSmallNumber();

      final VectorI4L vr0 = VectorI4L.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + (v1.getZL() * r)));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() + (v1.getWL() * r)));
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
    final VectorI4L v = new VectorI4L(3, 5, 7, 11);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
    Assert.assertTrue(v.getZL() == v.getZL());
    Assert.assertTrue(v.getWL() == v.getWL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorI4LTest.randomNegativeNumber();
      final long max_y = VectorI4LTest.randomNegativeNumber();
      final long max_z = VectorI4LTest.randomNegativeNumber();
      final long max_w = VectorI4LTest.randomNegativeNumber();
      final VectorI4L maximum = new VectorI4L(max_x, max_y, max_z, max_w);

      final long x = VectorI4LTest.randomNegativeNumber();
      final long y = VectorI4LTest.randomNegativeNumber();
      final long z = VectorI4LTest.randomNegativeNumber();
      final long w = VectorI4LTest.randomNegativeNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI4LTest.randomPositiveNumber();
      final long min_y = VectorI4LTest.randomPositiveNumber();
      final long min_z = VectorI4LTest.randomPositiveNumber();
      final long min_w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L minimum = new VectorI4L(min_x, min_y, min_z, min_w);

      final long x = VectorI4LTest.randomNegativeNumber();
      final long y = VectorI4LTest.randomNegativeNumber();
      final long z = VectorI4LTest.randomNegativeNumber();
      final long w = VectorI4LTest.randomNegativeNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorI4LTest.randomNegativeNumber();
      final long min_y = VectorI4LTest.randomNegativeNumber();
      final long min_z = VectorI4LTest.randomNegativeNumber();
      final long min_w = VectorI4LTest.randomNegativeNumber();
      final VectorI4L minimum = new VectorI4L(min_x, min_y, min_z, min_w);

      final long max_x = VectorI4LTest.randomPositiveNumber();
      final long max_y = VectorI4LTest.randomPositiveNumber();
      final long max_z = VectorI4LTest.randomPositiveNumber();
      final long max_w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L maximum = new VectorI4L(max_x, max_y, max_z, max_w);

      final long x = VectorI4LTest.randomNegativeNumber();
      final long y = VectorI4LTest.randomPositiveNumber();
      final long z = VectorI4LTest.randomPositiveNumber();
      final long w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorI4LTest.randomNegativeNumber();

      final long x = VectorI4LTest.randomPositiveNumber();
      final long y = VectorI4LTest.randomPositiveNumber();
      final long z = VectorI4LTest.randomPositiveNumber();
      final long w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getWL() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI4LTest.randomPositiveNumber();

      final long x = VectorI4LTest.randomNegativeNumber();
      final long y = VectorI4LTest.randomNegativeNumber();
      final long z = VectorI4LTest.randomNegativeNumber();
      final long w = VectorI4LTest.randomNegativeNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorI4LTest.randomNegativeNumber();
      final long maximum = VectorI4LTest.randomPositiveNumber();

      final long x = VectorI4LTest.randomNegativeNumber();
      final long y = VectorI4LTest.randomPositiveNumber();
      final long z = VectorI4LTest.randomPositiveNumber();
      final long w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() <= maximum);
      Assert.assertTrue(vr.getWL() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI4L vb = new VectorI4L(5, 6, 7, 8);
    final VectorI4L va = new VectorI4L(1, 2, 3, 4);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());
    Assert.assertFalse(va.getWL() == vb.getWL());

    final VectorI4L vc = new VectorI4L(va);

    Assert.assertTrue(va.getXL() == vc.getXL());
    Assert.assertTrue(va.getYL() == vc.getYL());
    Assert.assertTrue(va.getZL() == vc.getZL());
    Assert.assertTrue(va.getWL() == vc.getWL());

    Assert.assertFalse(vc.getXL() == vb.getXL());
    Assert.assertFalse(vc.getYL() == vb.getYL());
    Assert.assertFalse(vc.getZL() == vb.getZL());
    Assert.assertFalse(vc.getWL() == vb.getWL());
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(new VectorI4L().equals(new VectorI4L(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI4L v0 = new VectorI4L(0, 1, 0, 0);
    final VectorI4L v1 = new VectorI4L(0, 0, 0, 0);
    Assert.assertTrue(VectorI4L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI4LTest.randomPositiveSmallNumber();
      final long y0 = VectorI4LTest.randomPositiveSmallNumber();
      final long z0 = VectorI4LTest.randomPositiveSmallNumber();
      final long w0 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v0 = new VectorI4L(x0, y0, z0, w0);

      final long x1 = VectorI4LTest.randomPositiveSmallNumber();
      final long y1 = VectorI4LTest.randomPositiveSmallNumber();
      final long z1 = VectorI4LTest.randomPositiveSmallNumber();
      final long w1 = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v1 = new VectorI4L(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI4L v0 = new VectorI4L(10, 10, 10, 10);
    final VectorI4L v1 = new VectorI4L(10, 10, 10, 10);

    {
      final long p = VectorI4L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(v1.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorI4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorI4L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(v1.getWL() == 10);
      Assert.assertTrue(p == 400);
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
      final long w = (long) (Math.random() * max);
      final VectorI4L q = new VectorI4L(x, y, z, w);

      final double ms = VectorI4L.magnitudeSquared(q);
      final double dp = VectorI4L.dotProduct(q, q);

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
      final long w = (long) (Math.random() * max);
      final VectorI4L q = new VectorI4L(x, y, z, w);
      final double dp = VectorI4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI4L v0 = new VectorI4L(10, 10, 10, 10);

    {
      final long p = VectorI4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorI4L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI4L m0 = new VectorI4L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI4L m0 = new VectorI4L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4L m0 = new VectorI4L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI4L m0 = new VectorI4L();
      final VectorI4L m1 = new VectorI4L();
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
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4L m0 = new VectorI4L(x, y, z, w);
      final VectorI4L m1 = new VectorI4L(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI4L m0 = new VectorI4L();
    final VectorI4L m1 = new VectorI4L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI4L m0 = new VectorI4L(23, 0, 0, 0);
      final VectorI4L m1 = new VectorI4L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4L m0 = new VectorI4L(0, 23, 0, 0);
      final VectorI4L m1 = new VectorI4L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4L m0 = new VectorI4L(0, 0, 23, 0);
      final VectorI4L m1 = new VectorI4L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4L m0 = new VectorI4L(0, 0, 0, 23);
      final VectorI4L m1 = new VectorI4L();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI4L v0 = new VectorI4L(1, 2, 3, 4);
    final VectorI4L v1 = new VectorI4L(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
    Assert.assertTrue(v0.getZL() == v1.getZL());
    Assert.assertTrue(v0.getWL() == v1.getWL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI4LTest.randomPositiveNumber();
      final long y0 = VectorI4LTest.randomPositiveNumber();
      final long z0 = VectorI4LTest.randomPositiveNumber();
      final long w0 = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v0 = new VectorI4L(x0, y0, z0, w0);

      final long x1 = VectorI4LTest.randomPositiveNumber();
      final long y1 = VectorI4LTest.randomPositiveNumber();
      final long z1 = VectorI4LTest.randomPositiveNumber();
      final long w1 = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v1 = new VectorI4L(x1, y1, z1, w1);

      final VectorI4L vr0 = VectorI4L.interpolateLinear(v0, v1, 0);
      final VectorI4L vr1 = VectorI4L.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());
      Assert.assertTrue(v0.getZL() == vr0.getZL());
      Assert.assertTrue(v0.getWL() == vr0.getWL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
      Assert.assertTrue(v1.getZL() == vr1.getZL());
      Assert.assertTrue(v1.getWL() == vr1.getWL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI4LTest.randomPositiveSmallNumber();
      final long y = VectorI4LTest.randomPositiveSmallNumber();
      final long z = VectorI4LTest.randomPositiveSmallNumber();
      final long w = VectorI4LTest.randomPositiveSmallNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final long m = VectorI4L.magnitude(v);
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
    final VectorI4L v = new VectorI4L(1, 0, 0, 0);
    final long m = VectorI4L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI4L v = new VectorI4L(8, 0, 0, 0);

    {
      final long p = VectorI4L.dotProduct(v, v);
      final long q = VectorI4L.magnitudeSquared(v);
      final long r = VectorI4L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI4L v = new VectorI4L(0, 0, 0, 0);
    final long m = VectorI4L.magnitude(v);
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
      final VectorI4L p = new VectorI4L(1, 0, 0, 1);
      final VectorI4L q = new VectorI4L(0, 1, 0, 1);
      final VectorI4L u = VectorI4L.projection(p, q);

      Assert.assertTrue(VectorI4L.magnitude(u) == 0);
    }

    {
      final VectorI4L p = new VectorI4L(-1, 0, 0, 1);
      final VectorI4L q = new VectorI4L(0, 1, 0, 1);
      final VectorI4L u = VectorI4L.projection(p, q);

      Assert.assertTrue(VectorI4L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI4LTest.randomPositiveNumber();
      final long y = VectorI4LTest.randomPositiveNumber();
      final long z = VectorI4LTest.randomPositiveNumber();
      final long w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.scale(v, 1);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
      Assert.assertTrue(v.getZL() == vr.getZL());
      Assert.assertTrue(v.getWL() == vr.getWL());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorI4LTest.randomPositiveNumber();
      final long y = VectorI4LTest.randomPositiveNumber();
      final long z = VectorI4LTest.randomPositiveNumber();
      final long w = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v = new VectorI4L(x, y, z, w);

      final VectorI4L vr = VectorI4L.scale(v, 0);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
      Assert.assertTrue(vr.getZL() == 0);
      Assert.assertTrue(vr.getWL() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI4L v = new VectorI4L(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[VectorI4L 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorI4LTest.randomPositiveNumber();
      final long y0 = VectorI4LTest.randomPositiveNumber();
      final long z0 = VectorI4LTest.randomPositiveNumber();
      final long w0 = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v0 = new VectorI4L(x0, y0, z0, w0);

      final long x1 = VectorI4LTest.randomPositiveNumber();
      final long y1 = VectorI4LTest.randomPositiveNumber();
      final long z1 = VectorI4LTest.randomPositiveNumber();
      final long w1 = VectorI4LTest.randomPositiveNumber();
      final VectorI4L v1 = new VectorI4L(x1, y1, z1, w1);

      final VectorI4L vr0 = VectorI4L.subtract(v0, v1);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() - v1.getZL()));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() - v1.getWL()));
    }
  }
}
