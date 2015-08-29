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

public class VectorM2ITest extends VectorM2Contract
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
      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      VectorM2I.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.x) == vr.x);
      Assert.assertTrue(Math.abs(v.y) == vr.y);

      {
        final int orig_x = v.x;
        final int orig_y = v.y;

        VectorM2I.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.x);
        Assert.assertTrue(Math.abs(orig_y) == v.y);
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2I out = new VectorM2I();
    final VectorM2I v = new VectorM2I(-1, -1);

    Assert.assertTrue(v.x == -1);
    Assert.assertTrue(v.y == -1);

    final int vx = v.x;
    final int vy = v.y;

    final VectorM2I ov = VectorM2I.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vx == -1);
    Assert.assertTrue(vy == -1);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1);
    Assert.assertTrue(out.y == 1);
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v0 = new VectorM2I(x0, y0);

      final int x1 = VectorM2ITest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v1 = new VectorM2I(x1, y1);

      final VectorM2I vr0 = new VectorM2I();
      VectorM2I.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        VectorM2I.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2I out = new VectorM2I();
    final VectorM2I v0 = new VectorM2I(1, 1);
    final VectorM2I v1 = new VectorM2I(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);

    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2I ov0 = VectorM2I.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);

    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2I ov1 = VectorM2I.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);

    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v0 = new VectorM2I(x0, y0);

      final int x1 = VectorM2ITest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v1 = new VectorM2I(x1, y1);

      final int r = VectorM2ITest.randomPositiveSmallNumber();

      final VectorM2I vr0 = new VectorM2I();
      VectorM2I.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        VectorM2I.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.x == (orig_x + (v1.x * r)));
        Assert.assertTrue(v0.y == (orig_y + (v1.y * r)));
      }
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
      final VectorM2I v0 = new VectorM2I(1, 0);
      final VectorM2I v1 = new VectorM2I(1, 0);
      final double angle = VectorM2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2I v0 = new VectorM2I(x, y);
      final VectorM2I v1 = new VectorM2I(y, -x);
      final double angle = VectorM2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2I v0 = new VectorM2I(x, y);
      final VectorM2I v1 = new VectorM2I(-y, x);
      final double angle = VectorM2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM2I v = new VectorM2I(3, 5);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM2ITest.randomNegativeNumber();
      final int max_y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I maximum = new VectorM2I(max_x, max_y);

      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      final VectorM2I vo = VectorM2I.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);

      {
        final VectorM2I vr0 =
          VectorM2I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2ITest.randomPositiveNumber();
      final int min_y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I minimum = new VectorM2I(min_x, min_y);

      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      final VectorM2I vo = VectorM2I.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2I vr0 =
          VectorM2I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2ITest.randomNegativeNumber();
      final int min_y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I minimum = new VectorM2I(min_x, min_y);

      final int max_x = VectorM2ITest.randomPositiveNumber();
      final int max_y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I maximum = new VectorM2I(max_x, max_y);

      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      final VectorM2I vo = VectorM2I.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2I vr0 =
          VectorM2I.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM2ITest.randomNegativeNumber();

      final int x = VectorM2ITest.randomPositiveNumber();
      final int y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      VectorM2I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);

      {
        VectorM2I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2ITest.randomPositiveNumber();

      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomNegativeNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      VectorM2I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2ITest.randomNegativeNumber();
      final int maximum = VectorM2ITest.randomPositiveNumber();

      final int x = VectorM2ITest.randomNegativeNumber();
      final int y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();
      VectorM2I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2I vb = new VectorM2I(5, 6);
    final VectorM2I va = new VectorM2I(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2I.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new VectorM2I().equals(new VectorM2I(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM2I v0 = new VectorM2I(0, 1);
    final VectorM2I v1 = new VectorM2I(0, 0);
    Assert.assertTrue(VectorM2I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITest.randomPositiveSmallNumber();
      final int y0 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v0 = new VectorM2I(x0, y0);

      final int x1 = VectorM2ITest.randomPositiveSmallNumber();
      final int y1 = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v1 = new VectorM2I(x1, y1);

      Assert.assertTrue(VectorM2I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2I v0 = new VectorM2I(10, 10);
    final VectorM2I v1 = new VectorM2I(10, 10);

    {
      final int p = VectorM2I.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
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
      final VectorM2I q = new VectorM2I(x, y);

      final double ms = VectorM2I.magnitudeSquared(q);
      final double dp = VectorM2I.dotProduct(q, q);

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
      final VectorM2I q = new VectorM2I(x, y);
      final double dp = VectorM2I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2I v0 = new VectorM2I(10, 10);

    {
      final int p = VectorM2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2I m0 = new VectorM2I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2I m0 = new VectorM2I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2I m0 = new VectorM2I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2I m0 = new VectorM2I();
      final VectorM2I m1 = new VectorM2I();
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
      final VectorM2I m0 = new VectorM2I(x, y);
      final VectorM2I m1 = new VectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = new VectorM2I(x, y);
      final VectorM2I m1 = new VectorM2I(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = new VectorM2I(x, y);
      final VectorM2I m1 = new VectorM2I(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = new VectorM2I(x, y);
      final VectorM2I m1 = new VectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2I m0 = new VectorM2I();
    final VectorM2I m1 = new VectorM2I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2I m0 = new VectorM2I();
      final VectorM2I m1 = new VectorM2I();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2I m0 = new VectorM2I();
      final VectorM2I m1 = new VectorM2I();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2I v0 = new VectorM2I(1, 2);
    final VectorM2I v1 = new VectorM2I(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITest.randomPositiveNumber();
      final int y0 = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v0 = new VectorM2I(x0, y0);

      final int x1 = VectorM2ITest.randomPositiveNumber();
      final int y1 = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v1 = new VectorM2I(x1, y1);

      final VectorM2I vr0 = new VectorM2I();
      final VectorM2I vr1 = new VectorM2I();
      VectorM2I.interpolateLinear(v0, v1, 0, vr0);
      VectorM2I.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITest.randomPositiveSmallNumber();
      final int y = VectorM2ITest.randomPositiveSmallNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final int m = VectorM2I.magnitude(v);
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
    final VectorM2I v = new VectorM2I(1, 0);
    final int m = VectorM2I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2I v = new VectorM2I(8, 0);

    {
      final int p = VectorM2I.dotProduct(v, v);
      final int q = VectorM2I.magnitudeSquared(v);
      final int r = VectorM2I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM2I v = new VectorM2I(0, 0);
    final int m = VectorM2I.magnitude(v);
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

  @Override @Test public void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM2I p = new VectorM2I(1, 0);
      final VectorM2I q = new VectorM2I(0, 1);
      final VectorM2I r = new VectorM2I();
      final VectorM2I u = VectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2I.magnitude(u) == 0);
    }

    {
      final VectorM2I p = new VectorM2I(-1, 0);
      final VectorM2I q = new VectorM2I(0, 1);
      final VectorM2I r = new VectorM2I();
      final VectorM2I u = VectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2I out = new VectorM2I();
    final VectorM2I v0 = new VectorM2I(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    final VectorM2I ov0 = VectorM2I.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    final VectorM2I ov1 = VectorM2I.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITest.randomPositiveNumber();
      final int y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();

      VectorM2I.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);

      {
        final int orig_x = v.x;
        final int orig_y = v.y;

        VectorM2I.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2ITest.randomPositiveNumber();
      final int y = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v = new VectorM2I(x, y);

      final VectorM2I vr = new VectorM2I();

      VectorM2I.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);

      {
        VectorM2I.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2I v = new VectorM2I(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorM2I 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2ITest.randomPositiveNumber();
      final int y0 = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v0 = new VectorM2I(x0, y0);

      final int x1 = VectorM2ITest.randomPositiveNumber();
      final int y1 = VectorM2ITest.randomPositiveNumber();
      final VectorM2I v1 = new VectorM2I(x1, y1);

      final VectorM2I vr0 = new VectorM2I();
      VectorM2I.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        VectorM2I.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2I out = new VectorM2I();
    final VectorM2I v0 = new VectorM2I(1, 1);
    final VectorM2I v1 = new VectorM2I(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2I ov0 = VectorM2I.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2I ov1 = VectorM2I.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0);
    Assert.assertTrue(ov1.y == 0);
    Assert.assertTrue(v0.x == 0);
    Assert.assertTrue(v0.y == 0);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
  }
}
