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
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM2L;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2LContract extends VectorM2Contract
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
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      VectorM2L.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.getXL()) == vr.getXL());
      Assert.assertTrue(Math.abs(v.getYL()) == vr.getYL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();

        VectorM2L.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.getXL());
        Assert.assertTrue(Math.abs(orig_y) == v.getYL());
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2L out = this.newVectorM2L();
    final VectorM2L v = this.newVectorM2L(-1, -1);

    Assert.assertTrue(v.getXL() == -1);
    Assert.assertTrue(v.getYL() == -1);

    final long vx = v.getXL();
    final long vy = v.getYL();

    final VectorM2L ov = VectorM2L.absolute(v, out);

    Assert.assertTrue(vx == v.getXL());
    Assert.assertTrue(vy == v.getYL());
    Assert.assertTrue(vx == -1);
    Assert.assertTrue(vy == -1);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXL() == 1);
    Assert.assertTrue(out.getYL() == 1);
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LContract.randomPositiveSmallNumber();
      final long y0 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v0 = this.newVectorM2L(x0, y0);

      final long x1 = VectorM2LContract.randomPositiveSmallNumber();
      final long y1 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v1 = this.newVectorM2L(x1, y1);

      final VectorM2L vr0 = this.newVectorM2L();
      VectorM2L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        VectorM2L.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x + v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y + v1.getYL()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2L out = this.newVectorM2L();
    final VectorM2L v0 = this.newVectorM2L(1, 1);
    final VectorM2L v1 = this.newVectorM2L(1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);

    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);

    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);

    final VectorM2L ov0 = VectorM2L.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);

    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);

    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);

    final VectorM2L ov1 = VectorM2L.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);

    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);

    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LContract.randomPositiveSmallNumber();
      final long y0 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v0 = this.newVectorM2L(x0, y0);

      final long x1 = VectorM2LContract.randomPositiveSmallNumber();
      final long y1 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v1 = this.newVectorM2L(x1, y1);

      final long r = VectorM2LContract.randomPositiveSmallNumber();

      final VectorM2L vr0 = this.newVectorM2L();
      VectorM2L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        VectorM2L.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXL() == (orig_x + (v1.getXL() * r)));
        Assert.assertTrue(v0.getYL() == (orig_y + (v1.getYL() * r)));
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
      final VectorM2L v0 = this.newVectorM2L(1, 0);
      final VectorM2L v1 = this.newVectorM2L(1, 0);
      final double angle = VectorM2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorM2L v0 = this.newVectorM2L(x, y);
      final VectorM2L v1 = this.newVectorM2L(y, -x);
      final double angle = VectorM2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final long x = (long) (Math.random() * 200);
      final long y = (long) (Math.random() * 200);
      final VectorM2L v0 = this.newVectorM2L(x, y);
      final VectorM2L v1 = this.newVectorM2L(-y, x);
      final double angle = VectorM2L.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM2L v = this.newVectorM2L(3, 5);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorM2LContract.randomNegativeNumber();
      final long max_y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L maximum = this.newVectorM2L(max_x, max_y);

      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      final VectorM2L vo = VectorM2L.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());

      {
        final VectorM2L vr0 = VectorM2L.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM2LContract.randomPositiveNumber();
      final long min_y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L minimum = this.newVectorM2L(min_x, min_y);

      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      final VectorM2L vo = VectorM2L.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());

      {
        final VectorM2L vr0 = VectorM2L.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM2LContract.randomNegativeNumber();
      final long min_y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L minimum = this.newVectorM2L(min_x, min_y);

      final long max_x = VectorM2LContract.randomPositiveNumber();
      final long max_y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L maximum = this.newVectorM2L(max_x, max_y);

      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      final VectorM2L vo = VectorM2L.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());

      {
        final VectorM2L vr0 =
          VectorM2L.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorM2LContract.randomNegativeNumber();

      final long x = VectorM2LContract.randomPositiveNumber();
      final long y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      VectorM2L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);

      {
        VectorM2L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM2LContract.randomPositiveNumber();

      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomNegativeNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      VectorM2L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);

      {
        VectorM2L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM2LContract.randomNegativeNumber();
      final long maximum = VectorM2LContract.randomPositiveNumber();

      final long x = VectorM2LContract.randomNegativeNumber();
      final long y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();
      VectorM2L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);

      {
        VectorM2L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getYL() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2L vb = this.newVectorM2L(5, 6);
    final VectorM2L va = this.newVectorM2L(1, 2);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());

    VectorM2L.copy(va, vb);

    Assert.assertTrue(va.getXL() == vb.getXL());
    Assert.assertTrue(va.getYL() == vb.getYL());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM2L v0 = this.newVectorM2L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final VectorM2L v1 = this.newVectorM2L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(this.newVectorM2L().equals(this.newVectorM2L(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM2L.Context2L c = new VectorM2L.Context2L();
    final VectorM2L v0 = this.newVectorM2L(0, 1);
    final VectorM2L v1 = this.newVectorM2L(0, 0);
    Assert.assertTrue(VectorM2L.distance(c, v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    final VectorM2L.Context2L c = new VectorM2L.Context2L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LContract.randomPositiveSmallNumber();
      final long y0 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v0 = this.newVectorM2L(x0, y0);

      final long x1 = VectorM2LContract.randomPositiveSmallNumber();
      final long y1 = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v1 = this.newVectorM2L(x1, y1);

      Assert.assertTrue(VectorM2L.distance(c, v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2L v0 = this.newVectorM2L(10, 10);
    final VectorM2L v1 = this.newVectorM2L(10, 10);

    {
      final long p = VectorM2L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(p == 200);
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
      final VectorM2L q = this.newVectorM2L(x, y);

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

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final VectorM2L q = this.newVectorM2L(x, y);
      final double dp = VectorM2L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2L v0 = this.newVectorM2L(10, 10);

    {
      final long p = VectorM2L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final long p = VectorM2L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2L m0 = this.newVectorM2L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2L m0 = this.newVectorM2L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2L m0 = this.newVectorM2L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM2L m0 = this.newVectorM2L();
      final VectorM2L m1 = this.newVectorM2L();
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
      final VectorM2L m0 = this.newVectorM2L(x, y);
      final VectorM2L m1 = this.newVectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = this.newVectorM2L(x, y);
      final VectorM2L m1 = this.newVectorM2L(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = this.newVectorM2L(x, y);
      final VectorM2L m1 = this.newVectorM2L(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2L m0 = this.newVectorM2L(x, y);
      final VectorM2L m1 = this.newVectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2L m0 = this.newVectorM2L();
    final VectorM2L m1 = this.newVectorM2L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2L m0 = this.newVectorM2L();
      final VectorM2L m1 = this.newVectorM2L();
      m1.setXL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2L m0 = this.newVectorM2L();
      final VectorM2L m1 = this.newVectorM2L();
      m1.setYL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2L v0 = this.newVectorM2L(1, 2);
    final VectorM2L v1 = new VectorM2L(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final VectorM2L.Context2L c = new VectorM2L.Context2L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LContract.randomPositiveNumber();
      final long y0 = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v0 = this.newVectorM2L(x0, y0);

      final long x1 = VectorM2LContract.randomPositiveNumber();
      final long y1 = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v1 = this.newVectorM2L(x1, y1);

      final VectorM2L vr0 = this.newVectorM2L();
      final VectorM2L vr1 = this.newVectorM2L();
      VectorM2L.interpolateLinear(c, v0, v1, 0, vr0);
      VectorM2L.interpolateLinear(c, v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LContract.randomPositiveSmallNumber();
      final long y = VectorM2LContract.randomPositiveSmallNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

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
    final VectorM2L v = this.newVectorM2L(1, 0);
    final long m = VectorM2L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2L v = this.newVectorM2L(8, 0);

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
    final VectorM2L v = this.newVectorM2L(0, 0);
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
      final VectorM2L p = this.newVectorM2L(1, 0);
      final VectorM2L q = this.newVectorM2L(0, 1);
      final VectorM2L r = this.newVectorM2L();
      final VectorM2L u = VectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2L.magnitude(u) == 0);
    }

    {
      final VectorM2L p = this.newVectorM2L(-1, 0);
      final VectorM2L q = this.newVectorM2L(0, 1);
      final VectorM2L r = this.newVectorM2L();
      final VectorM2L u = VectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2L out = this.newVectorM2L();
    final VectorM2L v0 = this.newVectorM2L(1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);

    final VectorM2L ov0 = VectorM2L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);

    final VectorM2L ov1 = VectorM2L.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);
    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LContract.randomPositiveNumber();
      final long y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();

      VectorM2L.scale(v, 1, vr);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();

        VectorM2L.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXL() == orig_x);
        Assert.assertTrue(v.getYL() == orig_y);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM2LContract.randomPositiveNumber();
      final long y = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v = this.newVectorM2L(x, y);

      final VectorM2L vr = this.newVectorM2L();

      VectorM2L.scale(v, 0, vr);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);

      {
        VectorM2L.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXL() == 0);
        Assert.assertTrue(v.getYL() == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2L v = this.newVectorM2L(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorM2L 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM2LContract.randomPositiveNumber();
      final long y0 = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v0 = this.newVectorM2L(x0, y0);

      final long x1 = VectorM2LContract.randomPositiveNumber();
      final long y1 = VectorM2LContract.randomPositiveNumber();
      final VectorM2L v1 = this.newVectorM2L(x1, y1);

      final VectorM2L vr0 = this.newVectorM2L();
      VectorM2L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        VectorM2L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x - v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y - v1.getYL()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2L out = this.newVectorM2L();
    final VectorM2L v0 = this.newVectorM2L(1, 1);
    final VectorM2L v1 = this.newVectorM2L(1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);

    final VectorM2L ov0 = VectorM2L.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);

    final VectorM2L ov1 = VectorM2L.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 0);
    Assert.assertTrue(ov1.getYL() == 0);
    Assert.assertTrue(v0.getXL() == 0);
    Assert.assertTrue(v0.getYL() == 0);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
  }

  protected abstract VectorM2L newVectorM2L();

  protected abstract VectorM2L newVectorM2L(
    final long x,
    final long y);
}
