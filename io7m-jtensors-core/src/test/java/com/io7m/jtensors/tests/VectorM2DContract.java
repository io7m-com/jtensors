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
import com.io7m.jtensors.Vector2DType;
import com.io7m.jtensors.VectorM2D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2DContract<T extends Vector2DType>
{
  protected abstract double delta();

  protected abstract double randomLargeNegative();

  protected abstract double randomLargePositive();

  @Test public final void testConstants()
  {
    Assert.assertTrue(this.delta() >= 0.0);
    Assert.assertTrue(this.delta() <= 1.0);

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargeNegative() <= 0.0);
    }

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargePositive() >= 0.0);
    }
  }

  protected abstract T newVectorM2D(T v);

  protected abstract T newVectorM2D();

  protected abstract T newVectorM2D(
    final double x,
    final double y);

  @Test public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXD()), vr.getXD(), this.delta());
      Assert.assertEquals(Math.abs(v.getYD()), vr.getYD(), this.delta());

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.absoluteInPlace(v);

        Assert.assertEquals(Math.abs(orig_x), v.getXD(), this.delta());
        Assert.assertEquals(Math.abs(orig_y), v.getYD(), this.delta());
      }
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final T v = this.newVectorM2D(x, y);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();

      VectorM2D.absoluteInPlace(v);

      Assert.assertEquals(Math.abs(orig_x), v.getXD(), this.delta());
      Assert.assertEquals(Math.abs(orig_y), v.getYD(), this.delta());
    }
  }

  @Test public final void testAdd()
  {

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      VectorM2D.add(v0, v1, vr0);

      Assert.assertEquals(vr0.getXD(), v0.getXD() + v1.getXD(), this.delta());
      Assert.assertEquals(vr0.getYD(), v0.getYD() + v1.getYD(), this.delta());

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addInPlace(v0, v1);

        Assert.assertEquals(v0.getXD(), orig_x + v1.getXD(), this.delta());
        Assert.assertEquals(v0.getYD(), orig_y + v1.getYD(), this.delta());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);
    final T v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());

    final T ov0 = VectorM2D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), this.delta());
    Assert.assertEquals(2.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());

    final T ov1 = VectorM2D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), this.delta());
    Assert.assertEquals(2.0, ov1.getYD(), this.delta());
    Assert.assertEquals(2.0, v0.getXD(), this.delta());
    Assert.assertEquals(2.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
  }

  @Test public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final T v1 = this.newVectorM2D(x1, y1);

      final double r = this.randomLargePositive();

      final T vr0 = this.newVectorM2D();
      VectorM2D.addScaled(v0, v1, r, vr0);

      Assert.assertEquals(vr0.getXD(), v0.getXD() + (v1.getXD() * r), this.delta());
      Assert.assertEquals(vr0.getYD(), v0.getYD() + (v1.getYD() * r), this.delta());

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.addScaledInPlace(v0, v1, r);

        Assert.assertEquals(v0.getXD(), orig_x + (v1.getXD() * r), this.delta());
        Assert.assertEquals(v0.getYD(), orig_y + (v1.getYD() * r), this.delta());
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = this.randomLargePositive();
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
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
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
    {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x, y);
      final T v1 = this.newVectorM2D(y, -x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertEquals(angle, Math.toRadians(90.0), this.delta());
    }

    {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x, y);
      final T v1 = this.newVectorM2D(-y, x);
      VectorM2D.normalizeInPlace(v0);
      VectorM2D.normalizeInPlace(v1);
      final double angle = VectorM2D.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertEquals(angle, Math.toRadians(90.0), this.delta());
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM2D(3.0, 5.0);

    Assert.assertEquals(v.getXD(), v.getXD(), this.delta());
    Assert.assertEquals(v.getYD(), v.getYD(), this.delta());
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = this.randomLargeNegative();
      final double max_y = this.randomLargeNegative();
      final T maximum = this.newVectorM2D(max_x, max_y);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
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
      final double min_x = this.randomLargePositive();
      final double min_y = this.randomLargePositive();
      final T minimum = this.newVectorM2D(min_x, min_y);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
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
      final double min_x = this.randomLargeNegative();
      final double min_y = this.randomLargeNegative();
      final T minimum = this.newVectorM2D(min_x, min_y);

      final double max_x = this.randomLargePositive();
      final double max_y = this.randomLargePositive();
      final T maximum = this.newVectorM2D(max_x, max_y);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargePositive();
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
      final double maximum = this.randomLargeNegative();

      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.clampMaximum(v, maximum, vr);

      System.out.printf("f  : %f\n", maximum);
      System.out.printf("v  : %s\n", v);
      System.out.printf("vr : %s\n", v);

      Assert.assertEquals(maximum, vr.getXD(), this.delta());
      Assert.assertEquals(maximum, vr.getYD(), this.delta());

      {
        VectorM2D.clampMaximumInPlace(v, maximum);
        Assert.assertEquals(maximum, v.getXD(), this.delta());
        Assert.assertEquals(maximum, v.getYD(), this.delta());
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.randomLargePositive();

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.clampMinimum(v, minimum, vr);

      System.out.printf("f  : %f\n", minimum);
      System.out.printf("v  : %s\n", v);
      System.out.printf("vr : %s\n", v);

      Assert.assertEquals(minimum, vr.getXD(), this.delta());
      Assert.assertEquals(minimum, vr.getYD(), this.delta());

      {
        VectorM2D.clampMinimumInPlace(v, minimum);
        Assert.assertEquals(minimum, v.getXD(), this.delta());
        Assert.assertEquals(minimum, v.getYD(), this.delta());
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.randomLargeNegative();
      final double maximum = this.randomLargePositive();

      final double x = this.randomLargeNegative();
      final double y = this.randomLargePositive();
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

    Assert.assertEquals(vb.getXD(), va.getXD(), this.delta());
    Assert.assertEquals(vb.getYD(), va.getYD(), this.delta());
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM2D(
      this.randomLargePositive(), this.randomLargePositive());
    final T v1 = this.newVectorM2D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), this.delta());
    Assert.assertEquals(v0.getYD(), v1.getYD(), this.delta());
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
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
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
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(10.0, v1.getXD(), this.delta());
      Assert.assertEquals(10.0, v1.getYD(), this.delta());
      Assert.assertEquals(200.0, p, this.delta());
    }

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(200.0, p, this.delta());
    }

    {
      final double p = VectorM2D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), this.delta());
      Assert.assertEquals(10.0, v1.getYD(), this.delta());
      Assert.assertEquals(200.0, p, this.delta());
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM2D(1.0, 0.0);
    final T vmx = this.newVectorM2D(-1.0, 0.0);

    final T vpy = this.newVectorM2D(0.0, 1.0);
    final T vmy = this.newVectorM2D(0.0, -1.0);

    Assert.assertEquals(0.0, VectorM2D.dotProduct(vpx, vpy), this.delta());
    Assert.assertEquals(0.0, VectorM2D.dotProduct(vmx, vmy), this.delta());
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM2D(10.0, 10.0);

    {
      final double p = VectorM2D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(200.0, p, this.delta());
    }

    {
      final double p = VectorM2D.magnitudeSquared(v0);
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(200.0, p, this.delta());
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
    final double x = this.randomLargePositive();
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

    Assert.assertEquals(v1.getXD(), v0.getXD(), this.delta());
    Assert.assertEquals(v1.getYD(), v0.getYD(), this.delta());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final VectorM2D.ContextVM2D c = new VectorM2D.ContextVM2D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      final T vr1 = this.newVectorM2D();
      VectorM2D.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM2D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals(v0.getXD(), vr0.getXD(), this.delta());
      Assert.assertEquals(v0.getYD(), vr0.getYD(), this.delta());
      Assert.assertEquals(v1.getXD(), vr1.getXD(), this.delta());
      Assert.assertEquals(v1.getYD(), vr1.getYD(), this.delta());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v = this.newVectorM2D(x, y);

      final double m = VectorM2D.magnitude(v);
      System.out.printf("%s → %f\n", v, m);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();
      VectorM2D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2D.magnitude(vr);
      Assert.assertEquals(m, 1.0, this.delta());
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final T v = this.newVectorM2D(0.0, 0.0);
    final T vr = VectorM2D.normalizeInPlace(v);
    final double m = VectorM2D.magnitude(vr);
    Assert.assertEquals(m, 0.0, this.delta());
  }

  @Test public final void testMagnitudeOne()
  {
    final T v = this.newVectorM2D(1.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertEquals(m, 1.0, this.delta());
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM2D(8.0, 0.0);

    {
      final double p = VectorM2D.dotProduct(v, v);
      final double q = VectorM2D.magnitudeSquared(v);
      final double r = VectorM2D.magnitude(v);
      Assert.assertEquals(64.0, p, this.delta());
      Assert.assertEquals(64.0, q, this.delta());
      Assert.assertEquals(8.0, r, this.delta());
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM2D(0.0, 0.0);
    final double m = VectorM2D.magnitude(v);
    Assert.assertEquals(m, 0.0, this.delta());
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM2D(8.0, 0.0);
    final T out = this.newVectorM2D();
    final T vr = VectorM2D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = VectorM2D.magnitude(out);
    Assert.assertEquals(1.0, m, this.delta());
  }

  @Test public final void testNormalizeZero()
  {
    final T qr = this.newVectorM2D();
    final T q = this.newVectorM2D(0.0, 0.0);
    VectorM2D.normalize(q, qr);

    Assert.assertEquals(0.0, qr.getXD(), this.delta());
    Assert.assertEquals(0.0, qr.getYD(), this.delta());
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
      Assert.assertEquals(0.0, VectorM2D.magnitude(u), this.delta());
    }

    {
      final T p = this.newVectorM2D(-1.0, 0.0);
      final T q = this.newVectorM2D(0.0, 1.0);
      final T r = this.newVectorM2D();
      final T u = VectorM2D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM2D.magnitude(u), this.delta());
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());

    final T ov0 = VectorM2D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), this.delta());
    Assert.assertEquals(2.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());

    final T ov1 = VectorM2D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), this.delta());
    Assert.assertEquals(2.0, ov1.getYD(), this.delta());
    Assert.assertEquals(2.0, v0.getXD(), this.delta());
    Assert.assertEquals(2.0, v0.getYD(), this.delta());
  }

  @Test public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();

      VectorM2D.scale(v, 1.0, vr);

      Assert.assertEquals(v.getXD(), vr.getXD(), this.delta());
      Assert.assertEquals(v.getYD(), vr.getYD(), this.delta());

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();

        VectorM2D.scaleInPlace(v, 1.0);

        Assert.assertEquals(v.getXD(), orig_x, this.delta());
        Assert.assertEquals(v.getYD(), orig_y, this.delta());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final T v = this.newVectorM2D(x, y);

      final T vr = this.newVectorM2D();

      VectorM2D.scale(v, 0.0, vr);

      Assert.assertEquals(vr.getXD(), 0.0, this.delta());
      Assert.assertEquals(vr.getYD(), 0.0, this.delta());

      {
        VectorM2D.scaleInPlace(v, 0.0);

        Assert.assertEquals(v.getXD(), 0.0, this.delta());
        Assert.assertEquals(v.getYD(), 0.0, this.delta());
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
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final T v0 = this.newVectorM2D(x0, y0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final T v1 = this.newVectorM2D(x1, y1);

      final T vr0 = this.newVectorM2D();
      VectorM2D.subtract(v0, v1, vr0);

      Assert.assertEquals(vr0.getXD(), v0.getXD() - v1.getXD(), this.delta());
      Assert.assertEquals(vr0.getYD(), v0.getYD() - v1.getYD(), this.delta());

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        VectorM2D.subtractInPlace(v0, v1);

        Assert.assertEquals(v0.getXD(), orig_x - v1.getXD(), this.delta());
        Assert.assertEquals(v0.getYD(), orig_y - v1.getYD(), this.delta());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM2D();
    final T v0 = this.newVectorM2D(1.0, 1.0);
    final T v1 = this.newVectorM2D(1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());

    final T ov0 = VectorM2D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());

    final T ov1 = VectorM2D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), this.delta());
    Assert.assertEquals(0.0, ov1.getYD(), this.delta());
    Assert.assertEquals(0.0, v0.getXD(), this.delta());
    Assert.assertEquals(0.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
  }
}
