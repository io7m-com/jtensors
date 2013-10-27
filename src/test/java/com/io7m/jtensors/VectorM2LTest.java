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

public class VectorM2LTest extends VectorM2Contract
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
      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      VectorM2L.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.x) == vr.x);
      Assert.assertTrue(Math.abs(v.y) == vr.y);

      {
        final long orig_x = v.x;
        final long orig_y = v.y;

        VectorM2L.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.x);
        Assert.assertTrue(Math.abs(orig_y) == v.y);
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2L out = new VectorM2L();
    final VectorM2L v = new VectorM2L(-1, -1);

    Assert.assertTrue(v.x == -1);
    Assert.assertTrue(v.y == -1);

    final long vx = v.x;
    final long vy = v.y;

    final VectorM2L ov = VectorM2L.absolute(v, out);

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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LTest.randomPositiveSmallNumber();
      final long y0 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v0 = new VectorM2L(x0, y0);

      final long x1 = VectorM2LTest.randomPositiveSmallNumber();
      final long y1 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v1 = new VectorM2L(x1, y1);

      final VectorM2L vr0 = new VectorM2L();
      VectorM2L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        VectorM2L.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2L out = new VectorM2L();
    final VectorM2L v0 = new VectorM2L(1, 1);
    final VectorM2L v1 = new VectorM2L(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);

    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2L ov0 = VectorM2L.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);

    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2L ov1 = VectorM2L.addInPlace(v0, v1);

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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LTest.randomPositiveSmallNumber();
      final long y0 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v0 = new VectorM2L(x0, y0);

      final long x1 = VectorM2LTest.randomPositiveSmallNumber();
      final long y1 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v1 = new VectorM2L(x1, y1);

      final long r = VectorM2LTest.randomPositiveSmallNumber();

      final VectorM2L vr0 = new VectorM2L();
      VectorM2L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        VectorM2L.addScaledInPlace(v0, v1, r);

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
      final VectorM2L v0 = new VectorM2L(1, 0);
      final VectorM2L v1 = new VectorM2L(1, 0);
      final double angle = VectorM2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorM2L v0 = new VectorM2L(x, y);
      final VectorM2L v1 = new VectorM2L(y, -x);
      final double angle = VectorM2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorM2L v0 = new VectorM2L(x, y);
      final VectorM2L v1 = new VectorM2L(-y, x);
      final double angle = VectorM2L.angle(v0, v1);

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
    final VectorM2L v = new VectorM2L(3, 5);

    Assert.assertTrue(v.x == v.getXL());
    Assert.assertTrue(v.y == v.getYL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorM2LTest.randomNegativeNumber();
      final long max_y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L maximum = new VectorM2L(max_x, max_y);

      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      final VectorM2L vo = VectorM2L.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);

      {
        final VectorM2L vr0 =
          VectorM2L.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM2LTest.randomPositiveNumber();
      final long min_y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L minimum = new VectorM2L(min_x, min_y);

      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      final VectorM2L vo = VectorM2L.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2L vr0 =
          VectorM2L.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM2LTest.randomNegativeNumber();
      final long min_y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L minimum = new VectorM2L(min_x, min_y);

      final long max_x = VectorM2LTest.randomPositiveNumber();
      final long max_y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L maximum = new VectorM2L(max_x, max_y);

      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      final VectorM2L vo = VectorM2L.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2L vr0 =
          VectorM2L.clampByVectorInPlace(v, minimum, maximum);
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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorM2LTest.randomNegativeNumber();

      final long x = VectorM2LTest.randomPositiveNumber();
      final long y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      VectorM2L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);

      {
        VectorM2L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM2LTest.randomPositiveNumber();

      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomNegativeNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      VectorM2L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM2LTest.randomNegativeNumber();
      final long maximum = VectorM2LTest.randomPositiveNumber();

      final long x = VectorM2LTest.randomNegativeNumber();
      final long y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();
      VectorM2L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2L vb = new VectorM2L(5, 6);
    final VectorM2L va = new VectorM2L(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2L.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(new VectorM2L().equals(new VectorM2L(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM2L v0 = new VectorM2L(0, 1);
    final VectorM2L v1 = new VectorM2L(0, 0);
    Assert.assertTrue(VectorM2L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LTest.randomPositiveSmallNumber();
      final long y0 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v0 = new VectorM2L(x0, y0);

      final long x1 = VectorM2LTest.randomPositiveSmallNumber();
      final long y1 = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v1 = new VectorM2L(x1, y1);

      Assert.assertTrue(VectorM2L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2L v0 = new VectorM2L(10, 10);
    final VectorM2L v1 = new VectorM2L(10, 10);

    {
      final long p = VectorM2L.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(p == 200);
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
      final VectorM2L q = new VectorM2L(x, y);

      final double ms = VectorM2L.magnitudeSquared(q);
      final double dp = VectorM2L.dotProduct(q, q);

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
      final VectorM2L q = new VectorM2L(x, y);
      final double dp = VectorM2L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2L v0 = new VectorM2L(10, 10);

    {
      final long p = VectorM2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2L m0 = new VectorM2L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2L m0 = new VectorM2L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2L m0 = new VectorM2L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM2L m0 = new VectorM2L();
      final VectorM2L m1 = new VectorM2L();
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
      final VectorM2L m0 = new VectorM2L(x, y);
      final VectorM2L m1 = new VectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = new VectorM2L(x, y);
      final VectorM2L m1 = new VectorM2L(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = new VectorM2L(x, y);
      final VectorM2L m1 = new VectorM2L(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = new VectorM2L(x, y);
      final VectorM2L m1 = new VectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2L m0 = new VectorM2L();
    final VectorM2L m1 = new VectorM2L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2L m0 = new VectorM2L();
      final VectorM2L m1 = new VectorM2L();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2L m0 = new VectorM2L();
      final VectorM2L m1 = new VectorM2L();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2L v0 = new VectorM2L(1, 2);
    final VectorM2L v1 = new VectorM2L(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LTest.randomPositiveNumber();
      final long y0 = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v0 = new VectorM2L(x0, y0);

      final long x1 = VectorM2LTest.randomPositiveNumber();
      final long y1 = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v1 = new VectorM2L(x1, y1);

      final VectorM2L vr0 = new VectorM2L();
      final VectorM2L vr1 = new VectorM2L();
      VectorM2L.interpolateLinear(v0, v1, 0, vr0);
      VectorM2L.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LTest.randomPositiveSmallNumber();
      final long y = VectorM2LTest.randomPositiveSmallNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final long m = VectorM2L.magnitude(v);
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
    final VectorM2L v = new VectorM2L(1, 0);
    final long m = VectorM2L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2L v = new VectorM2L(8, 0);

    {
      final long p = VectorM2L.dotProduct(v, v);
      final long q = VectorM2L.magnitudeSquared(v);
      final long r = VectorM2L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM2L v = new VectorM2L(0, 0);
    final long m = VectorM2L.magnitude(v);
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
      final VectorM2L p = new VectorM2L(1, 0);
      final VectorM2L q = new VectorM2L(0, 1);
      final VectorM2L r = new VectorM2L();
      final VectorM2L u = VectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2L.magnitude(u) == 0);
    }

    {
      final VectorM2L p = new VectorM2L(-1, 0);
      final VectorM2L q = new VectorM2L(0, 1);
      final VectorM2L r = new VectorM2L();
      final VectorM2L u = VectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2L out = new VectorM2L();
    final VectorM2L v0 = new VectorM2L(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    final VectorM2L ov0 = VectorM2L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);

    final VectorM2L ov1 = VectorM2L.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LTest.randomPositiveNumber();
      final long y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();

      VectorM2L.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);

      {
        final long orig_x = v.x;
        final long orig_y = v.y;

        VectorM2L.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LTest.randomPositiveNumber();
      final long y = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v = new VectorM2L(x, y);

      final VectorM2L vr = new VectorM2L();

      VectorM2L.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);

      {
        VectorM2L.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2L v = new VectorM2L(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorM2L 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LTest.randomPositiveNumber();
      final long y0 = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v0 = new VectorM2L(x0, y0);

      final long x1 = VectorM2LTest.randomPositiveNumber();
      final long y1 = VectorM2LTest.randomPositiveNumber();
      final VectorM2L v1 = new VectorM2L(x1, y1);

      final VectorM2L vr0 = new VectorM2L();
      VectorM2L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        VectorM2L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2L out = new VectorM2L();
    final VectorM2L v0 = new VectorM2L(1, 1);
    final VectorM2L v1 = new VectorM2L(1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2L ov0 = VectorM2L.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);

    final VectorM2L ov1 = VectorM2L.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0);
    Assert.assertTrue(ov1.y == 0);
    Assert.assertTrue(v0.x == 0);
    Assert.assertTrue(v0.y == 0);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
  }
}
