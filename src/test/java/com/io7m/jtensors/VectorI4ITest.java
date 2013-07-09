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

public class VectorI4ITest extends VectorI4Contract
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
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final int w = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.absolute(v);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
      Assert.assertEquals(Math.abs(v.z), vr.z);
      Assert.assertEquals(Math.abs(v.w), vr.w);
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI4ITest.randomPositiveSmallNumber();
      final int y0 = VectorI4ITest.randomPositiveSmallNumber();
      final int z0 = VectorI4ITest.randomPositiveSmallNumber();
      final int w0 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);

      final int x1 = VectorI4ITest.randomPositiveSmallNumber();
      final int y1 = VectorI4ITest.randomPositiveSmallNumber();
      final int z1 = VectorI4ITest.randomPositiveSmallNumber();
      final int w1 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);

      final VectorI4I vr0 = VectorI4I.add(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
      Assert.assertTrue(vr0.z == (v0.z + v1.z));
      Assert.assertTrue(vr0.w == (v0.w + v1.w));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI4ITest.randomPositiveSmallNumber();
      final int y0 = VectorI4ITest.randomPositiveSmallNumber();
      final int z0 = VectorI4ITest.randomPositiveSmallNumber();
      final int w0 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);

      final int x1 = VectorI4ITest.randomPositiveSmallNumber();
      final int y1 = VectorI4ITest.randomPositiveSmallNumber();
      final int z1 = VectorI4ITest.randomPositiveSmallNumber();
      final int w1 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);

      final int r = VectorI4ITest.randomPositiveSmallNumber();

      final VectorI4I vr0 = VectorI4I.addScaled(v0, v1, r);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
      Assert.assertTrue(vr0.z == (v0.z + (v1.z * r)));
      Assert.assertTrue(vr0.w == (v0.w + (v1.w * r)));
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
    final VectorI4I v = new VectorI4I(3, 5, 7, 11);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
    Assert.assertTrue(v.z == v.getZI());
    Assert.assertTrue(v.w == v.getWI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI4ITest.randomNegativeNumber();
      final int max_y = VectorI4ITest.randomNegativeNumber();
      final int max_z = VectorI4ITest.randomNegativeNumber();
      final int max_w = VectorI4ITest.randomNegativeNumber();
      final VectorI4I maximum = new VectorI4I(max_x, max_y, max_z, max_w);

      final int x = VectorI4ITest.randomNegativeNumber();
      final int y = VectorI4ITest.randomNegativeNumber();
      final int z = VectorI4ITest.randomNegativeNumber();
      final int w = VectorI4ITest.randomNegativeNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI4ITest.randomPositiveNumber();
      final int min_y = VectorI4ITest.randomPositiveNumber();
      final int min_z = VectorI4ITest.randomPositiveNumber();
      final int min_w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I minimum = new VectorI4I(min_x, min_y, min_z, min_w);

      final int x = VectorI4ITest.randomNegativeNumber();
      final int y = VectorI4ITest.randomNegativeNumber();
      final int z = VectorI4ITest.randomNegativeNumber();
      final int w = VectorI4ITest.randomNegativeNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI4ITest.randomNegativeNumber();
      final int min_y = VectorI4ITest.randomNegativeNumber();
      final int min_z = VectorI4ITest.randomNegativeNumber();
      final int min_w = VectorI4ITest.randomNegativeNumber();
      final VectorI4I minimum = new VectorI4I(min_x, min_y, min_z, min_w);

      final int max_x = VectorI4ITest.randomPositiveNumber();
      final int max_y = VectorI4ITest.randomPositiveNumber();
      final int max_z = VectorI4ITest.randomPositiveNumber();
      final int max_w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I maximum = new VectorI4I(max_x, max_y, max_z, max_w);

      final int x = VectorI4ITest.randomNegativeNumber();
      final int y = VectorI4ITest.randomPositiveNumber();
      final int z = VectorI4ITest.randomPositiveNumber();
      final int w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI4ITest.randomNegativeNumber();

      final int x = VectorI4ITest.randomPositiveNumber();
      final int y = VectorI4ITest.randomPositiveNumber();
      final int z = VectorI4ITest.randomPositiveNumber();
      final int w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.w <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI4ITest.randomPositiveNumber();

      final int x = VectorI4ITest.randomNegativeNumber();
      final int y = VectorI4ITest.randomNegativeNumber();
      final int z = VectorI4ITest.randomNegativeNumber();
      final int w = VectorI4ITest.randomNegativeNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI4ITest.randomNegativeNumber();
      final int maximum = VectorI4ITest.randomPositiveNumber();

      final int x = VectorI4ITest.randomNegativeNumber();
      final int y = VectorI4ITest.randomPositiveNumber();
      final int z = VectorI4ITest.randomPositiveNumber();
      final int w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w <= maximum);
      Assert.assertTrue(vr.w >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorI4I vb = new VectorI4I(5, 6, 7, 8);
    final VectorI4I va = new VectorI4I(1, 2, 3, 4);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);
    Assert.assertFalse(va.w == vb.w);

    final VectorI4I vc = new VectorI4I(va);

    Assert.assertTrue(va.x == vc.x);
    Assert.assertTrue(va.y == vc.y);
    Assert.assertTrue(va.z == vc.z);
    Assert.assertTrue(va.w == vc.w);

    Assert.assertFalse(vc.x == vb.x);
    Assert.assertFalse(vc.y == vb.y);
    Assert.assertFalse(vc.z == vb.z);
    Assert.assertFalse(vc.w == vb.w);
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(new VectorI4I().equals(new VectorI4I(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorI4I v0 = new VectorI4I(0, 1, 0, 0);
    final VectorI4I v1 = new VectorI4I(0, 0, 0, 0);
    Assert.assertTrue(VectorI4I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI4ITest.randomPositiveSmallNumber();
      final int y0 = VectorI4ITest.randomPositiveSmallNumber();
      final int z0 = VectorI4ITest.randomPositiveSmallNumber();
      final int w0 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);

      final int x1 = VectorI4ITest.randomPositiveSmallNumber();
      final int y1 = VectorI4ITest.randomPositiveSmallNumber();
      final int z1 = VectorI4ITest.randomPositiveSmallNumber();
      final int w1 = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorI4I v0 = new VectorI4I(10, 10, 10, 10);
    final VectorI4I v1 = new VectorI4I(10, 10, 10, 10);

    {
      final int p = VectorI4I.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(v1.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorI4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorI4I.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(v1.w == 10);
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
      final VectorI4I q = new VectorI4I(x, y, z, w);

      final double ms = VectorI4I.magnitudeSquared(q);
      final double dp = VectorI4I.dotProduct(q, q);

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
      final int z = (int) (Math.random() * max);
      final int w = (int) (Math.random() * max);
      final VectorI4I q = new VectorI4I(x, y, z, w);
      final double dp = VectorI4I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorI4I v0 = new VectorI4I(10, 10, 10, 10);

    {
      final int p = VectorI4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorI4I.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI4I m0 = new VectorI4I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI4I m0 = new VectorI4I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4I m0 = new VectorI4I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4I m0 = new VectorI4I();
      final VectorI4I m1 = new VectorI4I();
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
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4I m0 = new VectorI4I(x, y, z, w);
      final VectorI4I m1 = new VectorI4I(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI4I m0 = new VectorI4I();
    final VectorI4I m1 = new VectorI4I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI4I m0 = new VectorI4I(23, 0, 0, 0);
      final VectorI4I m1 = new VectorI4I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4I m0 = new VectorI4I(0, 23, 0, 0);
      final VectorI4I m1 = new VectorI4I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4I m0 = new VectorI4I(0, 0, 23, 0);
      final VectorI4I m1 = new VectorI4I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4I m0 = new VectorI4I(0, 0, 0, 23);
      final VectorI4I m1 = new VectorI4I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI4I v0 = new VectorI4I(1, 2, 3, 4);
    final VectorI4I v1 = new VectorI4I(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI4ITest.randomPositiveNumber();
      final int y0 = VectorI4ITest.randomPositiveNumber();
      final int z0 = VectorI4ITest.randomPositiveNumber();
      final int w0 = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);

      final int x1 = VectorI4ITest.randomPositiveNumber();
      final int y1 = VectorI4ITest.randomPositiveNumber();
      final int z1 = VectorI4ITest.randomPositiveNumber();
      final int w1 = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);

      final VectorI4I vr0 = VectorI4I.interpolateLinear(v0, v1, 0);
      final VectorI4I vr1 = VectorI4I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);
      Assert.assertTrue(v0.z == vr0.z);
      Assert.assertTrue(v0.w == vr0.w);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
      Assert.assertTrue(v1.z == vr1.z);
      Assert.assertTrue(v1.w == vr1.w);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI4ITest.randomPositiveSmallNumber();
      final int y = VectorI4ITest.randomPositiveSmallNumber();
      final int z = VectorI4ITest.randomPositiveSmallNumber();
      final int w = VectorI4ITest.randomPositiveSmallNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final int m = VectorI4I.magnitude(v);
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
    final VectorI4I v = new VectorI4I(1, 0, 0, 0);
    final int m = VectorI4I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorI4I v = new VectorI4I(8, 0, 0, 0);

    {
      final int p = VectorI4I.dotProduct(v, v);
      final int q = VectorI4I.magnitudeSquared(v);
      final int r = VectorI4I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorI4I v = new VectorI4I(0, 0, 0, 0);
    final int m = VectorI4I.magnitude(v);
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
      final VectorI4I p = new VectorI4I(1, 0, 0, 1);
      final VectorI4I q = new VectorI4I(0, 1, 0, 1);
      final VectorI4I u = VectorI4I.projection(p, q);

      Assert.assertTrue(VectorI4I.magnitude(u) == 0);
    }

    {
      final VectorI4I p = new VectorI4I(-1, 0, 0, 1);
      final VectorI4I q = new VectorI4I(0, 1, 0, 1);
      final VectorI4I u = VectorI4I.projection(p, q);

      Assert.assertTrue(VectorI4I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI4ITest.randomPositiveNumber();
      final int y = VectorI4ITest.randomPositiveNumber();
      final int z = VectorI4ITest.randomPositiveNumber();
      final int w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.scale(v, 1);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
      Assert.assertTrue(v.z == vr.z);
      Assert.assertTrue(v.w == vr.w);
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI4ITest.randomPositiveNumber();
      final int y = VectorI4ITest.randomPositiveNumber();
      final int z = VectorI4ITest.randomPositiveNumber();
      final int w = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v = new VectorI4I(x, y, z, w);

      final VectorI4I vr = VectorI4I.scale(v, 0);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
      Assert.assertTrue(vr.z == 0);
      Assert.assertTrue(vr.w == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI4I v = new VectorI4I(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[VectorI4I 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI4ITest.randomPositiveNumber();
      final int y0 = VectorI4ITest.randomPositiveNumber();
      final int z0 = VectorI4ITest.randomPositiveNumber();
      final int w0 = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);

      final int x1 = VectorI4ITest.randomPositiveNumber();
      final int y1 = VectorI4ITest.randomPositiveNumber();
      final int z1 = VectorI4ITest.randomPositiveNumber();
      final int w1 = VectorI4ITest.randomPositiveNumber();
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);

      final VectorI4I vr0 = VectorI4I.subtract(v0, v1);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
      Assert.assertTrue(vr0.z == (v0.z - v1.z));
      Assert.assertTrue(vr0.w == (v0.w - v1.w));
    }
  }
}
