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
import com.io7m.jtensors.parameterized.PVector2IType;
import com.io7m.jtensors.parameterized.PVectorM2I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2IContract<T, V extends PVector2IType<T>>
{

  public static int randomNegativeNumber()
  {
    return (int) (PVectorM2IContract.getRandom() * (double) Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (PVectorM2IContract.getRandom() * (double) Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (PVectorM2IContract.getRandom() * (double) (1 << 14));
  }

  protected static int getRandomLargePositive()
  {
    return (int) (PVectorM2IContract.getRandom() * (double) Integer.MAX_VALUE);
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract V newVectorM2I(
    final int x,
    final int y);

  protected abstract V newVectorM2I();

  protected abstract V newVectorM2I(V v);

  @Test public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomNegativeNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      PVectorM2I.absolute(v, vr);

      Assert.assertEquals((long) vr.getXI(), (long) Math.abs(v.getXI()));
      Assert.assertEquals((long) vr.getYI(), (long) Math.abs(v.getYI()));

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        PVectorM2I.absoluteInPlace(v);

        Assert.assertEquals((long) v.getXI(), (long) Math.abs(orig_x));
        Assert.assertEquals((long) v.getYI(), (long) Math.abs(orig_y));
      }
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final V out = this.newVectorM2I();
    final V v = this.newVectorM2I(-1, -1);

    Assert.assertEquals(-1L, (long) v.getXI());
    Assert.assertEquals(-1L, (long) v.getYI());

    final int vx = v.getXI();
    final int vy = v.getYI();

    final V ov = PVectorM2I.absolute(v, out);

    Assert.assertEquals((long) v.getXI(), (long) vx);
    Assert.assertEquals((long) v.getYI(), (long) vy);
    Assert.assertEquals(-1L, (long) vx);
    Assert.assertEquals(-1L, (long) vy);

    Assert.assertEquals(ov, out);
    Assert.assertSame(ov, out);
    Assert.assertEquals(1L, (long) out.getXI());
    Assert.assertEquals(1L, (long) out.getYI());
  }

  @Test public final void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2I(x0, y0);

      final int x1 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2I(x1, y1);

      final V vr0 = this.newVectorM2I();
      PVectorM2I.add(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() + v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() + v1.getYI()), (long) vr0.getYI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        PVectorM2I.addInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x + v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y + v1.getYI()), (long) v0.getYI());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM2I();
    final V v0 = this.newVectorM2I(1, 1);
    final V v1 = this.newVectorM2I(1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());

    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());

    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());

    final V ov0 = PVectorM2I.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());

    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());

    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());

    final V ov1 = PVectorM2I.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());

    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());

    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
  }

  @Test public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2I(x0, y0);

      final int x1 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2I(x1, y1);

      final int r = PVectorM2IContract.randomPositiveSmallNumber();

      final V vr0 = this.newVectorM2I();
      PVectorM2I.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals(
        (long) (v0.getXI() + (v1.getXI() * r)), (long) vr0.getXI());
      Assert.assertEquals(
        (long) (v0.getYI() + (v1.getYI() * r)), (long) vr0.getYI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        PVectorM2I.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals(
          (long) (orig_x + (v1.getXI() * r)), (long) v0.getXI());
        Assert.assertEquals(
          (long) (orig_y + (v1.getYI() * r)), (long) v0.getYI());
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Test public final void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Test public final void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final V v0 = this.newVectorM2I(1, 0);
      final V v1 = this.newVectorM2I(1, 0);
      final double angle = PVectorM2I.angle(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final int x = (int) (PVectorM2IContract.getRandom() * 200.0);
      final int y = (int) (PVectorM2IContract.getRandom() * 200.0);
      final V v0 = this.newVectorM2I(x, y);
      final V v1 = this.newVectorM2I(y, -x);
      final double angle = PVectorM2I.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final int x = (int) (PVectorM2IContract.getRandom() * 200.0);
      final int y = (int) (PVectorM2IContract.getRandom() * 200.0);
      final V v0 = this.newVectorM2I(x, y);
      final V v1 = this.newVectorM2I(-y, x);
      final double angle = PVectorM2I.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM2I(3, 5);

    Assert.assertEquals((long) v.getXI(), (long) v.getXI());
    Assert.assertEquals((long) v.getYI(), (long) v.getYI());
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = PVectorM2IContract.randomNegativeNumber();
      final int max_y = PVectorM2IContract.randomNegativeNumber();
      final V maximum = this.newVectorM2I(max_x, max_y);

      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomNegativeNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      final V vo = PVectorM2I.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());

      {
        final V vr0 = PVectorM2I.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorM2IContract.randomPositiveNumber();
      final int min_y = PVectorM2IContract.randomPositiveNumber();
      final V minimum = this.newVectorM2I(min_x, min_y);

      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomNegativeNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      final V vo = PVectorM2I.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final V vr0 = PVectorM2I.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorM2IContract.randomNegativeNumber();
      final int min_y = PVectorM2IContract.randomNegativeNumber();
      final V minimum = this.newVectorM2I(min_x, min_y);

      final int max_x = PVectorM2IContract.randomPositiveNumber();
      final int max_y = PVectorM2IContract.randomPositiveNumber();
      final V maximum = this.newVectorM2I(max_x, max_y);

      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomPositiveNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      final V vo = PVectorM2I.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());

      {
        final V vr0 = PVectorM2I.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = PVectorM2IContract.randomNegativeNumber();

      final int x = PVectorM2IContract.randomPositiveNumber();
      final int y = PVectorM2IContract.randomPositiveNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      PVectorM2I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);

      {
        PVectorM2I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorM2IContract.randomPositiveNumber();

      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomNegativeNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      PVectorM2I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        PVectorM2I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorM2IContract.randomNegativeNumber();
      final int maximum = PVectorM2IContract.randomPositiveNumber();

      final int x = PVectorM2IContract.randomNegativeNumber();
      final int y = PVectorM2IContract.randomPositiveNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();
      PVectorM2I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);

      {
        PVectorM2I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM2I(5, 6);
    final V va = this.newVectorM2I(1, 2);

    Assert.assertNotEquals((long) vb.getXI(), (long) va.getXI());
    Assert.assertNotEquals((long) vb.getYI(), (long) va.getYI());

    PVectorM2I.copy(va, vb);

    Assert.assertEquals((long) vb.getXI(), (long) va.getXI());
    Assert.assertEquals((long) vb.getYI(), (long) va.getYI());
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM2I(
      PVectorM2IContract.getRandomLargePositive(),
      PVectorM2IContract.getRandomLargePositive());
    final V v1 = this.newVectorM2I();
    final V v2 = this.newVectorM2I();

    v1.copyFrom2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());

    v2.copyFromTyped2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v2.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v2.getYI());
  }

  @Test public final void testDefault00()
  {
    Assert.assertTrue(this.newVectorM2I().equals(this.newVectorM2I(0, 0)));
  }

  @Test public final void testDistance()
  {
    final PVectorM2I.ContextPVM2I c = new PVectorM2I.ContextPVM2I();
    final V v0 = this.newVectorM2I(0, 1);
    final V v1 = this.newVectorM2I(0, 0);
    Assert.assertEquals(1L, PVectorM2I.distance(c, v0, v1));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM2I.ContextPVM2I c = new PVectorM2I.ContextPVM2I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2I(x0, y0);

      final int x1 = PVectorM2IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM2IContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2I(x1, y1);

      Assert.assertTrue(PVectorM2I.distance(c, v0, v1) >= 0L);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM2I(10, 10);
    final V v1 = this.newVectorM2I(10, 10);

    {
      final int p = PVectorM2I.dotProduct(v0, v1);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(200L, (long) p);
    }

    {
      final int p = PVectorM2I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(200L, (long) p);
    }

    {
      final int p = PVectorM2I.dotProduct(v1, v1);
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(200L, (long) p);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (PVectorM2IContract.getRandom() * (double) max);
      final int y = (int) (PVectorM2IContract.getRandom() * (double) max);
      final V q = this.newVectorM2I(x, y);

      final double ms = (double) PVectorM2I.magnitudeSquared(q);
      final double dp = (double) PVectorM2I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (PVectorM2IContract.getRandom() * (double) max);
      final int y = (int) (PVectorM2IContract.getRandom() * (double) max);
      final V q = this.newVectorM2I(x, y);
      final double dp = (double) PVectorM2I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM2I(10, 10);

    {
      final int p = PVectorM2I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(200L, (long) p);
    }

    {
      final int p = PVectorM2I.magnitudeSquared(v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(200L, (long) p);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM2I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM2I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM2I();
      final V m1 = this.newVectorM2I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final int x = (int) (PVectorM2IContract.getRandom() * 1000.0);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final V m0 = this.newVectorM2I(x, y);
      final V m1 = this.newVectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2I(x, y);
      final V m1 = this.newVectorM2I(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2I(x, y);
      final V m1 = this.newVectorM2I(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2I(x, y);
      final V m1 = this.newVectorM2I(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM2I();
    final V m1 = this.newVectorM2I();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM2I();
      final V m1 = this.newVectorM2I();
      m1.setXI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM2I();
      final V m1 = this.newVectorM2I();
      m1.setYI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM2I(1, 2);
    final V v1 = this.newVectorM2I(v0);

    Assert.assertEquals((long) v1.getXI(), (long) v0.getXI());
    Assert.assertEquals((long) v1.getYI(), (long) v0.getYI());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final PVectorM2I.ContextPVM2I c = new PVectorM2I.ContextPVM2I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM2IContract.randomPositiveNumber();
      final int y0 = PVectorM2IContract.randomPositiveNumber();
      final V v0 = this.newVectorM2I(x0, y0);

      final int x1 = PVectorM2IContract.randomPositiveNumber();
      final int y1 = PVectorM2IContract.randomPositiveNumber();
      final V v1 = this.newVectorM2I(x1, y1);

      final V vr0 = this.newVectorM2I();
      final V vr1 = this.newVectorM2I();
      PVectorM2I.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM2I.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals((long) vr0.getXI(), (long) v0.getXI());
      Assert.assertEquals((long) vr0.getYI(), (long) v0.getYI());

      Assert.assertEquals((long) vr1.getXI(), (long) v1.getXI());
      Assert.assertEquals((long) vr1.getYI(), (long) v1.getYI());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM2IContract.randomPositiveSmallNumber();
      final int y = PVectorM2IContract.randomPositiveSmallNumber();
      final V v = this.newVectorM2I(x, y);

      final int m = PVectorM2I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testMagnitudeOne()
  {
    final V v = this.newVectorM2I(1, 0);
    final int m = PVectorM2I.magnitude(v);
    Assert.assertEquals(1L, (long) m);
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM2I(8, 0);

    {
      final int p = PVectorM2I.dotProduct(v, v);
      final int q = PVectorM2I.magnitudeSquared(v);
      final int r = PVectorM2I.magnitude(v);
      Assert.assertEquals(64L, (long) p);
      Assert.assertEquals(64L, (long) q);
      Assert.assertEquals(8L, (long) r);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final V v = this.newVectorM2I(0, 0);
    final int m = PVectorM2I.magnitude(v);
    Assert.assertEquals(0L, (long) m);
  }

  @Test public final void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Test public final void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM2I(1, 0);
      final V q = this.newVectorM2I(0, 1);
      final V r = this.newVectorM2I();
      final V u = PVectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) PVectorM2I.magnitude(u));
    }

    {
      final V p = this.newVectorM2I(-1, 0);
      final V q = this.newVectorM2I(0, 1);
      final V r = this.newVectorM2I();
      final V u = PVectorM2I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) PVectorM2I.magnitude(u));
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM2I();
    final V v0 = this.newVectorM2I(1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());

    final V ov0 = PVectorM2I.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());

    final V ov1 = PVectorM2I.scaleInPlace(v0, 2);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());
    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());
  }

  @Test public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM2IContract.randomPositiveNumber();
      final int y = PVectorM2IContract.randomPositiveNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();

      PVectorM2I.scale(v, 1.0, vr);

      Assert.assertEquals((long) vr.getXI(), (long) v.getXI());
      Assert.assertEquals((long) vr.getYI(), (long) v.getYI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();

        PVectorM2I.scaleInPlace(v, 1);

        Assert.assertEquals((long) orig_x, (long) v.getXI());
        Assert.assertEquals((long) orig_y, (long) v.getYI());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM2IContract.randomPositiveNumber();
      final int y = PVectorM2IContract.randomPositiveNumber();
      final V v = this.newVectorM2I(x, y);

      final V vr = this.newVectorM2I();

      PVectorM2I.scale(v, 0.0, vr);

      Assert.assertEquals(0L, (long) vr.getXI());
      Assert.assertEquals(0L, (long) vr.getYI());

      {
        PVectorM2I.scaleInPlace(v, 0);

        Assert.assertEquals(0L, (long) v.getXI());
        Assert.assertEquals(0L, (long) v.getYI());
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM2I(1, 2);
    Assert.assertTrue(v.toString().endsWith("1 2]"));
  }

  @Test public final void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM2IContract.randomPositiveNumber();
      final int y0 = PVectorM2IContract.randomPositiveNumber();
      final V v0 = this.newVectorM2I(x0, y0);

      final int x1 = PVectorM2IContract.randomPositiveNumber();
      final int y1 = PVectorM2IContract.randomPositiveNumber();
      final V v1 = this.newVectorM2I(x1, y1);

      final V vr0 = this.newVectorM2I();
      PVectorM2I.subtract(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() - v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() - v1.getYI()), (long) vr0.getYI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        PVectorM2I.subtractInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x - v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y - v1.getYI()), (long) v0.getYI());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM2I();
    final V v0 = this.newVectorM2I(1, 1);
    final V v1 = this.newVectorM2I(1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());

    final V ov0 = PVectorM2I.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());

    final V ov1 = PVectorM2I.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0L, (long) ov1.getXI());
    Assert.assertEquals(0L, (long) ov1.getYI());
    Assert.assertEquals(0L, (long) v0.getXI());
    Assert.assertEquals(0L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
  }

}