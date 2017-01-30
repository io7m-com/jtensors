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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorReadable2IType;
import org.junit.Assert;
import org.junit.Test;

public class VectorI2ITest extends VectorI2Contract
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
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.absolute(v);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.add(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v1 = new VectorI2I(x1, y1);

      final int r = VectorI2ITest.randomPositiveSmallNumber();

      final VectorI2I vr0 = VectorI2I.addScaled(v0, v1, r);

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
      final VectorReadable2IType v0 = new VectorI2I(1, 0);
      final VectorReadable2IType v1 = new VectorI2I(1, 0);
      final double angle = VectorI2I.angle(v0, v1);

      
      
      

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorReadable2IType v0 = new VectorI2I(x, y);
      final VectorReadable2IType v1 = new VectorI2I(y, -x);
      final double angle = VectorI2I.angle(v0, v1);

      
      
      

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorReadable2IType v0 = new VectorI2I(x, y);
      final VectorReadable2IType v1 = new VectorI2I(-y, x);
      final double angle = VectorI2I.angle(v0, v1);

      
      
      

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorReadable2IType v = new VectorI2I(3, 5);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorI2ITest.randomNegativeNumber();
      final int max_y = VectorI2ITest.randomNegativeNumber();
      final VectorReadable2IType maximum = new VectorI2I(max_x, max_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMaximumByVector(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITest.randomPositiveNumber();
      final int min_y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType minimum = new VectorI2I(min_x, min_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMinimumByVector(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorI2ITest.randomNegativeNumber();
      final int min_y = VectorI2ITest.randomNegativeNumber();
      final VectorReadable2IType minimum = new VectorI2I(min_x, min_y);

      final int max_x = VectorI2ITest.randomPositiveNumber();
      final int max_y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType maximum = new VectorI2I(max_x, max_y);

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampByVector(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorI2ITest.randomNegativeNumber();

      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMaximum(v, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITest.randomPositiveNumber();

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomNegativeNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clampMinimum(v, minimum);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorI2ITest.randomNegativeNumber();
      final int maximum = VectorI2ITest.randomPositiveNumber();

      final int x = VectorI2ITest.randomNegativeNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.clamp(v, minimum, maximum);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorReadable2IType vb = new VectorI2I(5, 6);
    final VectorI2I va = new VectorI2I(1, 2);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());

    final VectorReadable2IType vc = new VectorI2I(va);

    Assert.assertTrue(va.getXI() == vc.getXI());
    Assert.assertTrue(va.getYI() == vc.getYI());

    Assert.assertFalse(vc.getXI() == vb.getXI());
    Assert.assertFalse(vc.getYI() == vb.getYI());
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new VectorI2I().equals(new VectorI2I(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorReadable2IType v0 = new VectorI2I(0, 1);
    final VectorReadable2IType v1 = new VectorI2I(0, 0);
    Assert.assertTrue(VectorI2I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveSmallNumber();
      final int y0 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveSmallNumber();
      final int y1 = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v1 = new VectorI2I(x1, y1);

      Assert.assertTrue(VectorI2I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorReadable2IType v0 = new VectorI2I(10, 10);
    final VectorReadable2IType v1 = new VectorI2I(10, 10);

    {
      final int p = VectorI2I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.dotProduct(v1, v1);
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
      final VectorReadable2IType q = new VectorI2I(x, y);

      final double ms = VectorI2I.magnitudeSquared(q);
      final double dp = VectorI2I.dotProduct(q, q);

      
      
      

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
      final VectorReadable2IType q = new VectorI2I(x, y);
      final double dp = VectorI2I.dotProduct(q, q);

      
      

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorReadable2IType v0 = new VectorI2I(10, 10);

    {
      final int p = VectorI2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorI2I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2I m0 = new VectorI2I();
      final VectorI2I m1 = new VectorI2I();
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
      final VectorI2I m0 = new VectorI2I(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2I m0 = new VectorI2I(x, y);
      final VectorI2I m1 = new VectorI2I(q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorI2I m0 = new VectorI2I();
    final VectorI2I m1 = new VectorI2I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2I m0 = new VectorI2I(23, 0);
      final VectorI2I m1 = new VectorI2I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2I m0 = new VectorI2I(0, 23);
      final VectorI2I m1 = new VectorI2I();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorI2I v0 = new VectorI2I(1, 2);
    final VectorReadable2IType v1 = new VectorI2I(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveNumber();
      final int y0 = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveNumber();
      final int y1 = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.interpolateLinear(v0, v1, 0);
      final VectorI2I vr1 = VectorI2I.interpolateLinear(v0, v1, 1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveSmallNumber();
      final int y = VectorI2ITest.randomPositiveSmallNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final int m = VectorI2I.magnitude(v);
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
    final VectorReadable2IType v = new VectorI2I(1, 0);
    final int m = VectorI2I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorReadable2IType v = new VectorI2I(8, 0);

    {
      final int p = VectorI2I.dotProduct(v, v);
      final int q = VectorI2I.magnitudeSquared(v);
      final int r = VectorI2I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorReadable2IType v = new VectorI2I(0, 0);
    final int m = VectorI2I.magnitude(v);
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
      final VectorReadable2IType p = new VectorI2I(1, 0);
      final VectorReadable2IType q = new VectorI2I(0, 1);
      final VectorI2I u = VectorI2I.projection(p, q);

      Assert.assertTrue(VectorI2I.magnitude(u) == 0);
    }

    {
      final VectorReadable2IType p = new VectorI2I(-1, 0);
      final VectorReadable2IType q = new VectorI2I(0, 1);
      final VectorI2I u = VectorI2I.projection(p, q);

      Assert.assertTrue(VectorI2I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.scale(v, 1);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorI2ITest.randomPositiveNumber();
      final int y = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v = new VectorI2I(x, y);

      final VectorI2I vr = VectorI2I.scale(v, 0);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
    }
  }

  @Override @Test public void testString()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertTrue("[VectorI2I 1 2]".equals(v.toString()));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorI2ITest.randomPositiveNumber();
      final int y0 = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v0 = new VectorI2I(x0, y0);

      final int x1 = VectorI2ITest.randomPositiveNumber();
      final int y1 = VectorI2ITest.randomPositiveNumber();
      final VectorReadable2IType v1 = new VectorI2I(x1, y1);

      final VectorI2I vr0 = VectorI2I.subtract(v0, v1);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
    }
  }
}
