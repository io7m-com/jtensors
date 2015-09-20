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
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.Vector2DType;
import com.io7m.jtensors.VectorM2D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2DContract<T extends Vector2DType>
{
  protected abstract T newVectorM2D(T v);

  protected abstract T newVectorM2D();

  protected abstract T newVectorM2D(
    final double x,
    final double y);

  @Test public final void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.absoluteInPlace(v);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_x), v.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, Math.abs(orig_y), v.getYD()));
      }
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T v = this.newVectorM2D(x, y);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();

      VectorM2D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));

    }
  }

  @Test public final void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = VectorM2DContract.getRandom() * max;
      final double y0 = VectorM2DContract.getRandom() * max;
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = VectorM2DContract.getRandom() * max;
      final double y1 = VectorM2DContract.getRandom() * max;
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      VectorM2D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
      }
    }
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);
    final T v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final T ov0 = VectorM2D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final T ov1 = VectorM2D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 20000.0;
      final double x0 = VectorM2DContract.getRandom() * max;
      final double y0 = VectorM2DContract.getRandom() * max;
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = VectorM2DContract.getRandom() * max;
      final double y1 = VectorM2DContract.getRandom() * max;
      final T v1 = this.newVectorM2D(x1, y1);

      final double r = VectorM2DContract.getRandom() * max;

      final T vr0 = this.newVectorM2D();
      VectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = VectorM2DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(VectorM2D.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM2D(x0, y0);
      final T v1 = this.newVectorM2D(x0, y0);
      final T v2 = this.newVectorM2D(x0, y0);

      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2D.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = VectorM2DContract.getRandom();
      final double y = VectorM2DContract.getRandom();
      final T v0 = this.newVectorM2D(x, y);
      final T v1 = this.newVectorM2D(y, -x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final double x = VectorM2DContract.getRandom();
      final double y = VectorM2DContract.getRandom();
      final T v0 = this.newVectorM2D(x, y);
      final T v1 = this.newVectorM2D(-y, x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM2D(3.0, 5.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double max_y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T maximum = this.newVectorM2D(max_x, max_y);

      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      final T vo = VectorM2D.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());

      {
        final T vr0 = VectorM2D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
      }
    }
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double min_y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T minimum = this.newVectorM2D(min_x, min_y);

      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      final T vo = VectorM2D.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final T vr0 = VectorM2D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double min_y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T minimum = this.newVectorM2D(min_x, min_y);

      final double max_x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double max_y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T maximum = this.newVectorM2D(max_x, max_y);

      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      final T vo = VectorM2D.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());

      {
        final T vr0 = VectorM2D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = VectorM2DContract.getRandom() * Double.MIN_VALUE;

      final double x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);

      {
        VectorM2D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = VectorM2DContract.getRandom() * Double.MAX_VALUE;

      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double maximum = VectorM2DContract.getRandom() * Double.MAX_VALUE;

      final double x = VectorM2DContract.getRandom() * Double.MIN_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);

      {
        VectorM2D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM2D(5.0, 6.0);
    final T va = this.newVectorM2D(1.0, 2.0);

    Assert.assertNotEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertNotEquals(vb.getYD(), va.getYD(), 0.0);

    VectorM2D.copy(va, vb);

    Assert.assertEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertEquals(vb.getYD(), va.getYD(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM2D(
      VectorM2DContract.getRandom() * Double.MAX_VALUE,
      VectorM2DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newVectorM2D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
  }

  @Test public final void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      VectorM2D.almostEqual(
        ec, this.newVectorM2D(), this.newVectorM2D(0.0, 0.0)));
  }

  @Test public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    final T v0 = this.newVectorM2D(0.0, 1.0);
    final T v1 = this.newVectorM2D(0.0, 0.0);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorM2D.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM2D(x1, y1);

      Assert.assertTrue(VectorM2D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM2D(10.0, 10.0);
    final T v1 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = VectorM2D.dotProduct(v0, v1);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = VectorM2D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM2D(1.0, 0.0);
    final T vmx = this.newVectorM2D(-1.0, 0.0);

    final T vpy = this.newVectorM2D(0.0, 1.0);
    final T vmy = this.newVectorM2D(0.0, -1.0);

    Assert.assertEquals(0.0, VectorM2D.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, VectorM2D.dotProduct(vmx, vmy), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom();
      final double y = VectorM2DContract.getRandom();
      final T q = this.newVectorM2D(x, y);
      final double dp = VectorM2D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = VectorM2D.magnitudeSquared(v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM2D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM2D();
      final T m1 = this.newVectorM2D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = VectorM2DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2D(x, y);
      final T m1 = this.newVectorM2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM2D();
    final T m1 = this.newVectorM2D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM2D();
      final T m1 = this.newVectorM2D();
      m1.setXD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM2D();
      final T m1 = this.newVectorM2D();
      m1.setYD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM2D(1.0, 2.0);
    final T v1 = this.newVectorM2D(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      final T vr1 = this.newVectorM2D();
      VectorM2D.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM2D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getXD(), vr0.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getYD(), vr0.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final double m = VectorM2D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x =
        VectorM2DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y =
        VectorM2DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2D(0.0, 0.0);
    final T vr = VectorM2D.normalizeInPlace(v);
    final double m = VectorM2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2D(1.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM2D(8.0, 0.0);

    {
      final double p = VectorM2D.dotProduct(v, v);
      final double q = VectorM2D.magnitudeSquared(v);
      final double r = VectorM2D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2D(0.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM2D(8.0, 0.0);
    final T out = this.newVectorM2D();
    final T vr = VectorM2D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = VectorM2D.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T qr = this.newVectorM2D();
    final T q = this.newVectorM2D(0.0, 0.0);
    VectorM2D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
  }

  @Test public final void testOrthonormalize()
  {
    final T v0 = this.newVectorM2D(0.0, 1.0);
    final T v1 = this.newVectorM2D(0.5, 0.5);
    final T v0_out = this.newVectorM2D();
    final T v1_out = this.newVectorM2D();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    VectorM2D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2D(0.0, 1.0), v0_out);
    Assert.assertEquals(this.newVectorM2D(1.0, 0.0), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final T v0 = this.newVectorM2D(0, 1);
    final T v1 = this.newVectorM2D(0.5, 0.5);
    final T v0_out = this.newVectorM2D();
    final T v1_out = this.newVectorM2D();

    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();
    VectorM2D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2D(0.0, 1.0), v0);
    Assert.assertEquals(this.newVectorM2D(1.0, 0.0), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM2D(1.0, 0.0);
      final T q = this.newVectorM2D(0.0, 1.0);
      final T r = this.newVectorM2D();
      final T u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM2D.magnitude(u), 0.0);
    }

    {
      final T p = this.newVectorM2D(-1.0, 0.0);
      final T q = this.newVectorM2D(0.0, 1.0);
      final T r = this.newVectorM2D();
      final T u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM2D.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);

    final T ov0 = VectorM2D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);

    final T ov1 = VectorM2D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();

      VectorM2D.scale(v, 1.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getYD(), vr.getYD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();

      VectorM2D.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), 0.0));

      {
        VectorM2D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM2D(0.0, 1.0);
    Assert.assertTrue(v.toString().endsWith("0.0 1.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM2DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      VectorM2D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);
    final T v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final T ov0 = VectorM2D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);

    final T ov1 = VectorM2D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), 0.0);
    Assert.assertEquals(0.0, ov1.getYD(), 0.0);
    Assert.assertEquals(0.0, v0.getXD(), 0.0);
    Assert.assertEquals(0.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
  }
}
