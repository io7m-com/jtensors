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
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2IContract extends VectorM2Contract
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
      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      VectorM2I.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.getXI()) == vr.getXI());
      Assert.assertTrue(Math.abs(v.getYI()) == vr.getYI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        VectorM2I.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.getXI());
        Assert.assertTrue(Math.abs(orig_y) == v.getYI());
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2I out = this.newVectorM2I();
    final VectorM2I v = this.newVectorM2I(-1, -1);

    Assert.assertTrue(v.getXI() == -1);
    Assert.assertTrue(v.getYI() == -1);

    final int vx = v.getXI();
    final int vy = v.getYI();

    final VectorM2I ov = VectorM2I.absolute(v, out);

    Assert.assertTrue(vx == v.getXI());
    Assert.assertTrue(vy == v.getYI());
    Assert.assertTrue(vx == -1);
    Assert.assertTrue(vy == -1);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXI() == 1);
    Assert.assertTrue(out.getYI() == 1);
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2IContract.randomPositiveSmallNumber();
      final int y0 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v0 = this.newVectorM2I(x0, y0);

      final int x1 = VectorM2IContract.randomPositiveSmallNumber();
      final int y1 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v1 = this.newVectorM2I(x1, y1);

      final VectorM2I vr0 = this.newVectorM2I();
      VectorM2I.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2I.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x + v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y + v1.getYI()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2I out = this.newVectorM2I();
    final VectorM2I v0 = this.newVectorM2I(1, 1);
    final VectorM2I v1 = this.newVectorM2I(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);

    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2I ov0 = VectorM2I.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);

    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2I ov1 = VectorM2I.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);

    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);

    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2IContract.randomPositiveSmallNumber();
      final int y0 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v0 = this.newVectorM2I(x0, y0);

      final int x1 = VectorM2IContract.randomPositiveSmallNumber();
      final int y1 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v1 = this.newVectorM2I(x1, y1);

      final int r = VectorM2IContract.randomPositiveSmallNumber();

      final VectorM2I vr0 = this.newVectorM2I();
      VectorM2I.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2I.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXI() == (orig_x + (v1.getXI() * r)));
        Assert.assertTrue(v0.getYI() == (orig_y + (v1.getYI() * r)));
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
      final VectorM2I v0 = this.newVectorM2I(1, 0);
      final VectorM2I v1 = this.newVectorM2I(1, 0);
      final double angle = VectorM2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2I v0 = this.newVectorM2I(x, y);
      final VectorM2I v1 = this.newVectorM2I(y, -x);
      final double angle = VectorM2I.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final int x = (int) (Math.random() * 200);
      final int y = (int) (Math.random() * 200);
      final VectorM2I v0 = this.newVectorM2I(x, y);
      final VectorM2I v1 = this.newVectorM2I(-y, x);
      final double angle = VectorM2I.angle(v0, v1);

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
    final VectorM2I v = this.newVectorM2I(3, 5);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM2IContract.randomNegativeNumber();
      final int max_y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I maximum = this.newVectorM2I(max_x, max_y);

      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      final VectorM2I vo = VectorM2I.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());

      {
        final VectorM2I vr0 = VectorM2I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2IContract.randomPositiveNumber();
      final int min_y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I minimum = this.newVectorM2I(min_x, min_y);

      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      final VectorM2I vo = VectorM2I.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final VectorM2I vr0 = VectorM2I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM2IContract.randomNegativeNumber();
      final int min_y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I minimum = this.newVectorM2I(min_x, min_y);

      final int max_x = VectorM2IContract.randomPositiveNumber();
      final int max_y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I maximum = this.newVectorM2I(max_x, max_y);

      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      final VectorM2I vo = VectorM2I.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final VectorM2I vr0 =
          VectorM2I.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM2IContract.randomNegativeNumber();

      final int x = VectorM2IContract.randomPositiveNumber();
      final int y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      VectorM2I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);

      {
        VectorM2I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2IContract.randomPositiveNumber();

      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomNegativeNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      VectorM2I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        VectorM2I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM2IContract.randomNegativeNumber();
      final int maximum = VectorM2IContract.randomPositiveNumber();

      final int x = VectorM2IContract.randomNegativeNumber();
      final int y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();
      VectorM2I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        VectorM2I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2I vb = this.newVectorM2I(5, 6);
    final VectorM2I va = this.newVectorM2I(1, 2);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());

    VectorM2I.copy(va, vb);

    Assert.assertTrue(va.getXI() == vb.getXI());
    Assert.assertTrue(va.getYI() == vb.getYI());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM2I v0 = this.newVectorM2I(
      (int) Math.random() * Integer.MAX_VALUE,
      (int) Math.random() * Integer.MAX_VALUE);
    final VectorM2I v1 = this.newVectorM2I();

    v1.copyFrom2I(v0);

    Assert.assertEquals(v0.getXI(), v1.getXI());
    Assert.assertEquals(v0.getYI(), v1.getYI());
  }

  @Override @Test public void testDefault00()
  {
    Assert.assertTrue(this.newVectorM2I().equals(this.newVectorM2I(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM2I v0 = this.newVectorM2I(0, 1);
    final VectorM2I v1 = this.newVectorM2I(0, 0);
    Assert.assertTrue(VectorM2I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2IContract.randomPositiveSmallNumber();
      final int y0 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v0 = this.newVectorM2I(x0, y0);

      final int x1 = VectorM2IContract.randomPositiveSmallNumber();
      final int y1 = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v1 = this.newVectorM2I(x1, y1);

      Assert.assertTrue(VectorM2I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2I v0 = this.newVectorM2I(10, 10);
    final VectorM2I v1 = this.newVectorM2I(10, 10);

    {
      final int p = VectorM2I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.dotProduct(v1, v1);
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
      final VectorM2I q = this.newVectorM2I(x, y);

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
      final VectorM2I q = this.newVectorM2I(x, y);
      final double dp = VectorM2I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2I v0 = this.newVectorM2I(10, 10);

    {
      final int p = VectorM2I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }

    {
      final int p = VectorM2I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(p == 200);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2I m0 = this.newVectorM2I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2I m0 = this.newVectorM2I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2I m0 = this.newVectorM2I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2I m0 = this.newVectorM2I();
      final VectorM2I m1 = this.newVectorM2I();
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
      final VectorM2I m0 = this.newVectorM2I(x, y);
      final VectorM2I m1 = this.newVectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = this.newVectorM2I(x, y);
      final VectorM2I m1 = this.newVectorM2I(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = this.newVectorM2I(x, y);
      final VectorM2I m1 = this.newVectorM2I(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2I m0 = this.newVectorM2I(x, y);
      final VectorM2I m1 = this.newVectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2I m0 = this.newVectorM2I();
    final VectorM2I m1 = this.newVectorM2I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2I m0 = this.newVectorM2I();
      final VectorM2I m1 = this.newVectorM2I();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2I m0 = this.newVectorM2I();
      final VectorM2I m1 = this.newVectorM2I();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2I v0 = this.newVectorM2I(1, 2);
    final VectorM2I v1 = new VectorM2I(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2IContract.randomPositiveNumber();
      final int y0 = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v0 = this.newVectorM2I(x0, y0);

      final int x1 = VectorM2IContract.randomPositiveNumber();
      final int y1 = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v1 = this.newVectorM2I(x1, y1);

      final VectorM2I vr0 = this.newVectorM2I();
      final VectorM2I vr1 = this.newVectorM2I();
      VectorM2I.interpolateLinear(v0, v1, 0, vr0);
      VectorM2I.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2IContract.randomPositiveSmallNumber();
      final int y = VectorM2IContract.randomPositiveSmallNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

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
    final VectorM2I v = this.newVectorM2I(1, 0);
    final int m = VectorM2I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2I v = this.newVectorM2I(8, 0);

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
    final VectorM2I v = this.newVectorM2I(0, 0);
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
      final VectorM2I p = this.newVectorM2I(1, 0);
      final VectorM2I q = this.newVectorM2I(0, 1);
      final VectorM2I r = this.newVectorM2I();
      final VectorM2I u = VectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2I.magnitude(u) == 0);
    }

    {
      final VectorM2I p = this.newVectorM2I(-1, 0);
      final VectorM2I q = this.newVectorM2I(0, 1);
      final VectorM2I r = this.newVectorM2I();
      final VectorM2I u = VectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2I out = this.newVectorM2I();
    final VectorM2I v0 = this.newVectorM2I(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    final VectorM2I ov0 = VectorM2I.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);

    final VectorM2I ov1 = VectorM2I.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2IContract.randomPositiveNumber();
      final int y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();

      VectorM2I.scale(v, 1, vr);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        VectorM2I.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXI() == orig_x);
        Assert.assertTrue(v.getYI() == orig_y);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM2IContract.randomPositiveNumber();
      final int y = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v = this.newVectorM2I(x, y);

      final VectorM2I vr = this.newVectorM2I();

      VectorM2I.scale(v, 0, vr);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);

      {
        VectorM2I.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXI() == 0);
        Assert.assertTrue(v.getYI() == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2I v = this.newVectorM2I(1, 2);
    Assert.assertTrue(v.toString().equals("[VectorM2I 1 2]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM2IContract.randomPositiveNumber();
      final int y0 = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v0 = this.newVectorM2I(x0, y0);

      final int x1 = VectorM2IContract.randomPositiveNumber();
      final int y1 = VectorM2IContract.randomPositiveNumber();
      final VectorM2I v1 = this.newVectorM2I(x1, y1);

      final VectorM2I vr0 = this.newVectorM2I();
      VectorM2I.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        VectorM2I.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x - v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y - v1.getYI()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2I out = this.newVectorM2I();
    final VectorM2I v0 = this.newVectorM2I(1, 1);
    final VectorM2I v1 = this.newVectorM2I(1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2I ov0 = VectorM2I.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);

    final VectorM2I ov1 = VectorM2I.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 0);
    Assert.assertTrue(ov1.getYI() == 0);
    Assert.assertTrue(v0.getXI() == 0);
    Assert.assertTrue(v0.getYI() == 0);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
  }

  protected abstract VectorM2I newVectorM2I(
    final int x,
    final int y);

  protected abstract VectorM2I newVectorM2I();
}
