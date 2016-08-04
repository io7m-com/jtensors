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
import com.io7m.jtensors.parameterized.PVector2LType;
import com.io7m.jtensors.parameterized.PVectorM2L;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2LContract<T, V extends PVector2LType<T>>
{
  public static long randomNegativeNumber()
  {
    return (long) (PVectorM2LContract.getRandom() * (double) Long.MIN_VALUE);
  }

  public static long randomPositiveNumber()
  {
    return (long) (PVectorM2LContract.getRandom() * (double) Long.MAX_VALUE);
  }

  public static long randomPositiveSmallNumber()
  {
    return (long) (PVectorM2LContract.getRandom() * (double) (1 << 14));
  }

  protected static long getRandomLargePositive()
  {
    return (long) (PVectorM2LContract.getRandom() * (double) Long.MAX_VALUE);
  }

  private static double getRandom()
  {
    return Math.random();
  }

  protected abstract V newVectorM2L(
    final long x,
    final long y);

  protected abstract V newVectorM2L(V v);

  protected abstract V newVectorM2L();

  @Test public final void testAbsolute()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomNegativeNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      PVectorM2L.absolute(v, vr);

      Assert.assertEquals(vr.getXL(), Math.abs(v.getXL()));
      Assert.assertEquals(vr.getYL(), Math.abs(v.getYL()));

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();

        PVectorM2L.absoluteInPlace(v);

        Assert.assertEquals(v.getXL(), Math.abs(orig_x));
        Assert.assertEquals(v.getYL(), Math.abs(orig_y));
      }
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final V out = this.newVectorM2L();
    final V v = this.newVectorM2L(-1L, -1L);

    Assert.assertEquals(-1L, v.getXL());
    Assert.assertEquals(-1L, v.getYL());

    final long vx = v.getXL();
    final long vy = v.getYL();

    final V ov = PVectorM2L.absolute(v, out);

    Assert.assertEquals(v.getXL(), vx);
    Assert.assertEquals(v.getYL(), vy);
    Assert.assertEquals(-1L, vx);
    Assert.assertEquals(-1L, vy);

    Assert.assertEquals(ov, out);
    Assert.assertSame(ov, out);
    Assert.assertEquals(1L, out.getXL());
    Assert.assertEquals(1L, out.getYL());
  }

  @Test public final void testAdd()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2L(x0, y0);

      final long x1 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2L(x1, y1);

      final V vr0 = this.newVectorM2L();
      PVectorM2L.add(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() + v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() + v1.getYL()), vr0.getYL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        PVectorM2L.addInPlace(v0, v1);

        Assert.assertEquals((orig_x + v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y + v1.getYL()), v0.getYL());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM2L();
    final V v0 = this.newVectorM2L(1L, 1L);
    final V v1 = this.newVectorM2L(1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());

    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());

    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());

    final V ov0 = PVectorM2L.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());

    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());

    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());

    final V ov1 = PVectorM2L.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());

    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());

    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
  }

  @Test public final void testAddScaled()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2L(x0, y0);

      final long x1 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2L(x1, y1);

      final long r = PVectorM2LContract.randomPositiveSmallNumber();

      final V vr0 = this.newVectorM2L();
      PVectorM2L.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals((v0.getXL() + (v1.getXL() * r)), vr0.getXL());
      Assert.assertEquals((v0.getYL() + (v1.getYL() * r)), vr0.getYL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        PVectorM2L.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals((orig_x + (v1.getXL() * r)), v0.getXL());
        Assert.assertEquals((orig_y + (v1.getYL() * r)), v0.getYL());
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
      final V v0 = this.newVectorM2L(1L, 0L);
      final V v1 = this.newVectorM2L(1L, 0L);
      final double angle = PVectorM2L.angle(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final long x = (long) (PVectorM2LContract.getRandom() * 200.0);
      final long y = (long) (PVectorM2LContract.getRandom() * 200.0);
      final V v0 = this.newVectorM2L(x, y);
      final V v1 = this.newVectorM2L(y, -x);
      final double angle = PVectorM2L.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final long x = (long) (PVectorM2LContract.getRandom() * 200.0);
      final long y = (long) (PVectorM2LContract.getRandom() * 200.0);
      final V v0 = this.newVectorM2L(x, y);
      final V v1 = this.newVectorM2L(-y, x);
      final double angle = PVectorM2L.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM2L(3L, 5L);

    Assert.assertEquals(v.getXL(), v.getXL());
    Assert.assertEquals(v.getYL(), v.getYL());
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long max_x = PVectorM2LContract.randomNegativeNumber();
      final long max_y = PVectorM2LContract.randomNegativeNumber();
      final V maximum = this.newVectorM2L(max_x, max_y);

      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomNegativeNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      final V vo = PVectorM2L.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());

      {
        final V vr0 = PVectorM2L.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long min_x = PVectorM2LContract.randomPositiveNumber();
      final long min_y = PVectorM2LContract.randomPositiveNumber();
      final V minimum = this.newVectorM2L(min_x, min_y);

      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomNegativeNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      final V vo = PVectorM2L.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());

      {
        final V vr0 = PVectorM2L.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long min_x = PVectorM2LContract.randomNegativeNumber();
      final long min_y = PVectorM2LContract.randomNegativeNumber();
      final V minimum = this.newVectorM2L(min_x, min_y);

      final long max_x = PVectorM2LContract.randomPositiveNumber();
      final long max_y = PVectorM2LContract.randomPositiveNumber();
      final V maximum = this.newVectorM2L(max_x, max_y);

      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomPositiveNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      final V vo = PVectorM2L.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());

      {
        final V vr0 = PVectorM2L.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long maximum = PVectorM2LContract.randomNegativeNumber();

      final long x = PVectorM2LContract.randomPositiveNumber();
      final long y = PVectorM2LContract.randomPositiveNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      PVectorM2L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);

      {
        PVectorM2L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long minimum = PVectorM2LContract.randomPositiveNumber();

      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomNegativeNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      PVectorM2L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);

      {
        PVectorM2L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long minimum = PVectorM2LContract.randomNegativeNumber();
      final long maximum = PVectorM2LContract.randomPositiveNumber();

      final long x = PVectorM2LContract.randomNegativeNumber();
      final long y = PVectorM2LContract.randomPositiveNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();
      PVectorM2L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);

      {
        PVectorM2L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getYL() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM2L(5L, 6L);
    final V va = this.newVectorM2L(1L, 2L);

    Assert.assertNotEquals(vb.getXL(), va.getXL());
    Assert.assertNotEquals(vb.getYL(), va.getYL());

    PVectorM2L.copy(va, vb);

    Assert.assertEquals(vb.getXL(), va.getXL());
    Assert.assertEquals(vb.getYL(), va.getYL());
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM2L(
      PVectorM2LContract.getRandomLargePositive(),
      PVectorM2LContract.getRandomLargePositive());
    final V v1 = this.newVectorM2L();
    final V v2 = this.newVectorM2L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());

    v2.copyFromTyped2L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
  }

  @Test public final void testDefault00()
  {
    Assert.assertTrue(this.newVectorM2L().equals(this.newVectorM2L(0L, 0L)));
  }

  @Test public final void testDistance()
  {
    final PVectorM2L.ContextPVM2L c = new PVectorM2L.ContextPVM2L();
    final V v0 = this.newVectorM2L(0L, 1L);
    final V v1 = this.newVectorM2L(0L, 0L);
    Assert.assertEquals(1L, PVectorM2L.distance(c, v0, v1));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM2L.ContextPVM2L c = new PVectorM2L.ContextPVM2L();
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM2L(x0, y0);

      final long x1 = PVectorM2LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM2LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM2L(x1, y1);

      Assert.assertTrue(PVectorM2L.distance(c, v0, v1) >= 0L);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM2L(10L, 10L);
    final V v1 = this.newVectorM2L(10L, 10L);

    {
      final long p = PVectorM2L.dotProduct(v0, v1);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(200L, p);
    }

    {
      final long p = PVectorM2L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(200L, p);
    }

    {
      final long p = PVectorM2L.dotProduct(v1, v1);
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(200L, p);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long max = 1000L;
      final long x = (long) (PVectorM2LContract.getRandom() * (double) max);
      final long y = (long) (PVectorM2LContract.getRandom() * (double) max);
      final V q = this.newVectorM2L(x, y);

      final double ms = (double) PVectorM2L.magnitudeSquared(q);
      final double dp = (double) PVectorM2L.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long max = 1000L;
      final long x = (long) (PVectorM2LContract.getRandom() * (double) max);
      final long y = (long) (PVectorM2LContract.getRandom() * (double) max);
      final V q = this.newVectorM2L(x, y);
      final double dp = (double) PVectorM2L.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM2L(10L, 10L);

    {
      final long p = PVectorM2L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(200L, p);
    }

    {
      final long p = PVectorM2L.magnitudeSquared(v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(200L, p);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM2L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM2L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2L();
      Assert.assertFalse(m0.equals(Long.valueOf(23L)));
    }

    {
      final V m0 = this.newVectorM2L();
      final V m1 = this.newVectorM2L();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final long x = (long) (PVectorM2LContract.getRandom() * 1000.0);
    final long y = x + 1L;
    final long z = y + 1L;
    final long w = z + 1L;
    final long q = w + 1L;

    {
      final V m0 = this.newVectorM2L(x, y);
      final V m1 = this.newVectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2L(x, y);
      final V m1 = this.newVectorM2L(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2L(x, y);
      final V m1 = this.newVectorM2L(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2L(x, y);
      final V m1 = this.newVectorM2L(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM2L();
    final V m1 = this.newVectorM2L();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM2L();
      final V m1 = this.newVectorM2L();
      m1.setXL(23L);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM2L();
      final V m1 = this.newVectorM2L();
      m1.setYL(23L);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM2L(1L, 2L);
    final V v1 = this.newVectorM2L(v0);

    Assert.assertEquals(v1.getXL(), v0.getXL());
    Assert.assertEquals(v1.getYL(), v0.getYL());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final PVectorM2L.ContextPVM2L c = new PVectorM2L.ContextPVM2L();
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = PVectorM2LContract.randomPositiveNumber();
      final long y0 = PVectorM2LContract.randomPositiveNumber();
      final V v0 = this.newVectorM2L(x0, y0);

      final long x1 = PVectorM2LContract.randomPositiveNumber();
      final long y1 = PVectorM2LContract.randomPositiveNumber();
      final V v1 = this.newVectorM2L(x1, y1);

      final V vr0 = this.newVectorM2L();
      final V vr1 = this.newVectorM2L();
      PVectorM2L.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM2L.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals(vr0.getXL(), v0.getXL());
      Assert.assertEquals(vr0.getYL(), v0.getYL());

      Assert.assertEquals(vr1.getXL(), v1.getXL());
      Assert.assertEquals(vr1.getYL(), v1.getYL());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = PVectorM2LContract.randomPositiveSmallNumber();
      final long y = PVectorM2LContract.randomPositiveSmallNumber();
      final V v = this.newVectorM2L(x, y);

      final long m = PVectorM2L.magnitude(v);
      Assert.assertTrue(m >= 1L);
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
    final V v = this.newVectorM2L(1L, 0L);
    final long m = PVectorM2L.magnitude(v);
    Assert.assertEquals(1L, m);
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM2L(8L, 0L);

    {
      final long p = PVectorM2L.dotProduct(v, v);
      final long q = PVectorM2L.magnitudeSquared(v);
      final long r = PVectorM2L.magnitude(v);
      Assert.assertEquals(64L, p);
      Assert.assertEquals(64L, q);
      Assert.assertEquals(8L, r);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final V v = this.newVectorM2L(0L, 0L);
    final long m = PVectorM2L.magnitude(v);
    Assert.assertEquals(0L, m);
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
      final V p = this.newVectorM2L(1L, 0L);
      final V q = this.newVectorM2L(0L, 1L);
      final V r = this.newVectorM2L();
      final V u = PVectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, PVectorM2L.magnitude(u));
    }

    {
      final V p = this.newVectorM2L(-1L, 0L);
      final V q = this.newVectorM2L(0L, 1L);
      final V r = this.newVectorM2L();
      final V u = PVectorM2L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, PVectorM2L.magnitude(u));
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM2L();
    final V v0 = this.newVectorM2L(1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());

    final V ov0 = PVectorM2L.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());

    final V ov1 = PVectorM2L.scaleInPlace(v0, 2L);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());
    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());
  }

  @Test public final void testScaleOne()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = PVectorM2LContract.randomPositiveNumber();
      final long y = PVectorM2LContract.randomPositiveNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();

      PVectorM2L.scale(v, 1.0, vr);

      Assert.assertEquals(vr.getXL(), v.getXL());
      Assert.assertEquals(vr.getYL(), v.getYL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();

        PVectorM2L.scaleInPlace(v, 1L);

        Assert.assertEquals(orig_x, v.getXL());
        Assert.assertEquals(orig_y, v.getYL());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = PVectorM2LContract.randomPositiveNumber();
      final long y = PVectorM2LContract.randomPositiveNumber();
      final V v = this.newVectorM2L(x, y);

      final V vr = this.newVectorM2L();

      PVectorM2L.scale(v, 0.0, vr);

      Assert.assertEquals(0L, vr.getXL());
      Assert.assertEquals(0L, vr.getYL());

      {
        PVectorM2L.scaleInPlace(v, 0L);

        Assert.assertEquals(0L, v.getXL());
        Assert.assertEquals(0L, v.getYL());
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM2L(1L, 2L);
    Assert.assertTrue(v.toString().endsWith("1 2]"));
  }

  @Test public final void testSubtract()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = PVectorM2LContract.randomPositiveNumber();
      final long y0 = PVectorM2LContract.randomPositiveNumber();
      final V v0 = this.newVectorM2L(x0, y0);

      final long x1 = PVectorM2LContract.randomPositiveNumber();
      final long y1 = PVectorM2LContract.randomPositiveNumber();
      final V v1 = this.newVectorM2L(x1, y1);

      final V vr0 = this.newVectorM2L();
      PVectorM2L.subtract(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() - v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() - v1.getYL()), vr0.getYL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        PVectorM2L.subtractInPlace(v0, v1);

        Assert.assertEquals((orig_x - v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y - v1.getYL()), v0.getYL());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM2L();
    final V v0 = this.newVectorM2L(1L, 1L);
    final V v1 = this.newVectorM2L(1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());

    final V ov0 = PVectorM2L.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());

    final V ov1 = PVectorM2L.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0L, ov1.getXL());
    Assert.assertEquals(0L, ov1.getYL());
    Assert.assertEquals(0L, v0.getXL());
    Assert.assertEquals(0L, v0.getYL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
  }

}
