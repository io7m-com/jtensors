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
import com.io7m.jtensors.Vector3IType;
import com.io7m.jtensors.VectorM3I;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3IContract<T extends Vector3IType>
{
  public static int randomNegativeNumber()
  {
    return (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (VectorM3IContract.getRandom() * (double) Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (VectorM3IContract.getRandom() * (double) (1 << 14));
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected static int getLarge()
  {
    return (int) (VectorM3IContract.getRandom() * Integer.MAX_VALUE);
  }

  protected abstract T newVectorM3I(T v);

  protected abstract T newVectorM3I();

  protected abstract T newVectorM3I(
    final int x,
    final int y,
    final int z);

  @Test public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      VectorM3I.absolute(v, vr);

      Assert.assertEquals((long) Math.abs(v.getXI()), (long) vr.getXI());
      Assert.assertEquals((long) Math.abs(v.getYI()), (long) vr.getYI());
      Assert.assertEquals((long) Math.abs(v.getZI()), (long) vr.getZI());
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (VectorM3IContract.getRandom() * (double) Integer.MIN_VALUE);
      final T v = this.newVectorM3I(x, y, z);

      VectorM3I.absoluteInPlace(v);

      Assert.assertEquals((long) v.getXI(), (long) Math.abs(x));
      Assert.assertEquals((long) v.getYI(), (long) Math.abs(y));
      Assert.assertEquals((long) v.getZI(), (long) Math.abs(z));
    }
  }

  @Test public final void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3IContract.randomPositiveSmallNumber();
      final int y0 = VectorM3IContract.randomPositiveSmallNumber();
      final int z0 = VectorM3IContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = VectorM3IContract.randomPositiveSmallNumber();
      final int y1 = VectorM3IContract.randomPositiveSmallNumber();
      final int z1 = VectorM3IContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3I(x1, y1, z1);

      final T vr0 = this.newVectorM3I();
      VectorM3I.add(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() + v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() + v1.getYI()), (long) vr0.getYI());
      Assert.assertEquals((long) (v0.getZI() + v1.getZI()), (long) vr0.getZI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3I.addInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x + v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y + v1.getYI()), (long) v0.getYI());
        Assert.assertEquals((long) (orig_z + v1.getZI()), (long) v0.getZI());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM3I();
    final T v0 = this.newVectorM3I(1, 1, 1);
    final T v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());

    final T ov0 = VectorM3I.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());
    Assert.assertEquals(2L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());

    final T ov1 = VectorM3I.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());
    Assert.assertEquals(2L, (long) ov1.getZI());
    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());
    Assert.assertEquals(2L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
  }

  @Test public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3IContract.randomPositiveSmallNumber();
      final int y0 = VectorM3IContract.randomPositiveSmallNumber();
      final int z0 = VectorM3IContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = VectorM3IContract.randomPositiveSmallNumber();
      final int y1 = VectorM3IContract.randomPositiveSmallNumber();
      final int z1 = VectorM3IContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3I(x1, y1, z1);

      final int r = VectorM3IContract.randomPositiveSmallNumber();

      final T vr0 = this.newVectorM3I();
      VectorM3I.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals(
        (long) (v0.getXI() + (v1.getXI() * r)), (long) vr0.getXI());
      Assert.assertEquals(
        (long) (v0.getYI() + (v1.getYI() * r)), (long) vr0.getYI());
      Assert.assertEquals(
        (long) (v0.getZI() + (v1.getZI() * r)), (long) vr0.getZI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3I.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals(
          (long) (orig_x + (v1.getXI() * r)), (long) v0.getXI());
        Assert.assertEquals(
          (long) (orig_y + (v1.getYI() * r)), (long) v0.getYI());
        Assert.assertEquals(
          (long) (orig_z + (v1.getZI() * r)), (long) v0.getZI());
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

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM3I(3, 5, 7);

    Assert.assertEquals((long) v.getXI(), (long) v.getXI());
    Assert.assertEquals((long) v.getYI(), (long) v.getYI());
    Assert.assertEquals((long) v.getZI(), (long) v.getZI());
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM3IContract.randomNegativeNumber();
      final int max_y = VectorM3IContract.randomNegativeNumber();
      final int max_z = VectorM3IContract.randomNegativeNumber();
      final T maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = VectorM3IContract.randomNegativeNumber();
      final int y = VectorM3IContract.randomNegativeNumber();
      final int z = VectorM3IContract.randomNegativeNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      final T vo = VectorM3I.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());

      {
        final T vr0 = VectorM3I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
      }
    }
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM3IContract.randomPositiveNumber();
      final int min_y = VectorM3IContract.randomPositiveNumber();
      final int min_z = VectorM3IContract.randomPositiveNumber();
      final T minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int x = VectorM3IContract.randomNegativeNumber();
      final int y = VectorM3IContract.randomNegativeNumber();
      final int z = VectorM3IContract.randomNegativeNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      final T vo = VectorM3I.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final T vr0 = VectorM3I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM3IContract.randomNegativeNumber();
      final int min_y = VectorM3IContract.randomNegativeNumber();
      final int min_z = VectorM3IContract.randomNegativeNumber();
      final T minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int max_x = VectorM3IContract.randomPositiveNumber();
      final int max_y = VectorM3IContract.randomPositiveNumber();
      final int max_z = VectorM3IContract.randomPositiveNumber();
      final T maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = VectorM3IContract.randomNegativeNumber();
      final int y = VectorM3IContract.randomPositiveNumber();
      final int z = VectorM3IContract.randomPositiveNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      final T vo = VectorM3I.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final T vr0 = VectorM3I.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM3IContract.randomNegativeNumber();

      final int x = VectorM3IContract.randomPositiveNumber();
      final int y = VectorM3IContract.randomPositiveNumber();
      final int z = VectorM3IContract.randomPositiveNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      VectorM3I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);

      {
        VectorM3I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getZI() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM3IContract.randomPositiveNumber();

      final int x = VectorM3IContract.randomNegativeNumber();
      final int y = VectorM3IContract.randomNegativeNumber();
      final int z = VectorM3IContract.randomNegativeNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      VectorM3I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        VectorM3I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM3IContract.randomNegativeNumber();
      final int maximum = VectorM3IContract.randomPositiveNumber();

      final int x = VectorM3IContract.randomNegativeNumber();
      final int y = VectorM3IContract.randomPositiveNumber();
      final int z = VectorM3IContract.randomPositiveNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();
      VectorM3I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        VectorM3I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() <= maximum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM3I(5, 6, 7);
    final T va = this.newVectorM3I(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    VectorM3I.copy(va, vb);

    Assert.assertEquals((long) vb.getXI(), (long) va.getXI());
    Assert.assertEquals((long) vb.getYI(), (long) va.getYI());
    Assert.assertEquals((long) vb.getZI(), (long) va.getZI());
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM3I(
      VectorM3IContract.getLarge(),
      VectorM3IContract.getLarge(),
      VectorM3IContract

        .getLarge());
    final T v1 = this.newVectorM3I();

    v1.copyFrom2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals(0L, (long) v1.getZI());
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM3I(
      VectorM3IContract.getLarge(),
      VectorM3IContract.getLarge(),
      VectorM3IContract.getLarge());
    final T v1 = this.newVectorM3I();

    v1.copyFrom3I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals((long) v0.getZI(), (long) v1.getZI());
  }

  @Test public final void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Test public final void testDefault000()
  {
    Assert.assertTrue(this.newVectorM3I().equals(this.newVectorM3I(0, 0, 0)));
  }

  @Test public final void testDistance()
  {
    final VectorM3I.ContextVM3I c = new VectorM3I.ContextVM3I();
    final T v0 = this.newVectorM3I(0, 1, 0);
    final T v1 = this.newVectorM3I(0, 0, 0);
    Assert.assertEquals(1L, (long) VectorM3I.distance(c, v0, v1));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM3I.ContextVM3I c = new VectorM3I.ContextVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3IContract.randomPositiveSmallNumber();
      final int y0 = VectorM3IContract.randomPositiveSmallNumber();
      final int z0 = VectorM3IContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = VectorM3IContract.randomPositiveSmallNumber();
      final int y1 = VectorM3IContract.randomPositiveSmallNumber();
      final int z1 = VectorM3IContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3I(x1, y1, z1);

      Assert.assertTrue(VectorM3I.distance(c, v0, v1) >= 0);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM3I(10, 10, 10);
    final T v1 = this.newVectorM3I(10, 10, 10);

    {
      final int p = VectorM3I.dotProduct(v0, v1);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(10L, (long) v1.getZI());
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = VectorM3I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = VectorM3I.dotProduct(v1, v1);
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(10L, (long) v1.getZI());
      Assert.assertEquals(300L, (long) p);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (VectorM3IContract.getRandom() * (double) max);
      final int y = (int) (VectorM3IContract.getRandom() * (double) max);
      final int z = (int) (VectorM3IContract.getRandom() * (double) max);
      final T q = this.newVectorM3I(x, y, z);

      final double ms = (double) VectorM3I.magnitudeSquared(q);
      final double dp = (double) VectorM3I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (VectorM3IContract.getRandom() * (double) max);
      final int y = (int) (VectorM3IContract.getRandom() * (double) max);
      final int z = (int) (VectorM3IContract.getRandom() * (double) max);
      final T q = this.newVectorM3I(x, y, z);
      final double dp = (double) VectorM3I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM3I(10, 10, 10);

    {
      final int p = VectorM3I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = VectorM3I.magnitudeSquared(v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(300L, (long) p);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM3I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3I();
      final T m1 = this.newVectorM3I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final int x = (int) (VectorM3IContract.getRandom() * 1000.0);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final T m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3I(x, y, z);
      final T m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM3I();
    final T m1 = this.newVectorM3I();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM3I();
      final T m1 = this.newVectorM3I();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newVectorM3I();
      final T m1 = this.newVectorM3I();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newVectorM3I();
      final T m1 = this.newVectorM3I();
      m1.setZI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM3I(1, 2, 3);
    final T v1 = this.newVectorM3I(v0);

    Assert.assertEquals((long) v1.getXI(), (long) v0.getXI());
    Assert.assertEquals((long) v1.getYI(), (long) v0.getYI());
    Assert.assertEquals((long) v1.getZI(), (long) v0.getZI());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final VectorM3I.ContextVM3I c = new VectorM3I.ContextVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3IContract.randomPositiveNumber();
      final int y0 = VectorM3IContract.randomPositiveNumber();
      final int z0 = VectorM3IContract.randomPositiveNumber();
      final T v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = VectorM3IContract.randomPositiveNumber();
      final int y1 = VectorM3IContract.randomPositiveNumber();
      final int z1 = VectorM3IContract.randomPositiveNumber();
      final T v1 = this.newVectorM3I(x1, y1, z1);

      final T vr0 = this.newVectorM3I();
      final T vr1 = this.newVectorM3I();
      VectorM3I.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM3I.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals((long) vr0.getXI(), (long) v0.getXI());
      Assert.assertEquals((long) vr0.getYI(), (long) v0.getYI());
      Assert.assertEquals((long) vr0.getZI(), (long) v0.getZI());

      Assert.assertEquals((long) vr1.getXI(), (long) v1.getXI());
      Assert.assertEquals((long) vr1.getYI(), (long) v1.getYI());
      Assert.assertEquals((long) vr1.getZI(), (long) v1.getZI());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3IContract.randomPositiveSmallNumber();
      final int y = VectorM3IContract.randomPositiveSmallNumber();
      final int z = VectorM3IContract.randomPositiveSmallNumber();
      final T v = this.newVectorM3I(x, y, z);

      final int m = VectorM3I.magnitude(v);
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
    final T v = this.newVectorM3I(1, 0, 0);
    final int m = VectorM3I.magnitude(v);
    Assert.assertEquals(1L, (long) m);
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM3I(8, 0, 0);

    {
      final int p = VectorM3I.dotProduct(v, v);
      final int q = VectorM3I.magnitudeSquared(v);
      final int r = VectorM3I.magnitude(v);
      Assert.assertEquals(64L, (long) p);
      Assert.assertEquals(64L, (long) q);
      Assert.assertEquals(8L, (long) r);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM3I(0, 0, 0);
    final int m = VectorM3I.magnitude(v);
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
      final T p = this.newVectorM3I(1, 0, 0);
      final T q = this.newVectorM3I(0, 1, 0);
      final T r = this.newVectorM3I();
      final T u = VectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) VectorM3I.magnitude(u));
    }

    {
      final T p = this.newVectorM3I(-1, 0, 0);
      final T q = this.newVectorM3I(0, 1, 0);
      final T r = this.newVectorM3I();
      final T u = VectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) VectorM3I.magnitude(u));
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM3I();
    final T v0 = this.newVectorM3I(1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());

    final T ov0 = VectorM3I.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());
    Assert.assertEquals(2L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());

    final T ov1 = VectorM3I.scaleInPlace(v0, 2);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());
    Assert.assertEquals(2L, (long) ov1.getZI());
    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());
    Assert.assertEquals(2L, (long) v0.getZI());
  }

  @Test public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3IContract.randomPositiveNumber();
      final int y = VectorM3IContract.randomPositiveNumber();
      final int z = VectorM3IContract.randomPositiveNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();

      VectorM3I.scale(v, 1.0, vr);

      Assert.assertEquals((long) vr.getXI(), (long) v.getXI());
      Assert.assertEquals((long) vr.getYI(), (long) v.getYI());
      Assert.assertEquals((long) vr.getZI(), (long) v.getZI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();
        final int orig_z = v.getZI();

        VectorM3I.scaleInPlace(v, 1);

        Assert.assertEquals((long) orig_x, (long) v.getXI());
        Assert.assertEquals((long) orig_y, (long) v.getYI());
        Assert.assertEquals((long) orig_z, (long) v.getZI());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3IContract.randomPositiveNumber();
      final int y = VectorM3IContract.randomPositiveNumber();
      final int z = VectorM3IContract.randomPositiveNumber();
      final T v = this.newVectorM3I(x, y, z);

      final T vr = this.newVectorM3I();

      VectorM3I.scale(v, 0.0, vr);

      Assert.assertEquals(0L, (long) vr.getXI());
      Assert.assertEquals(0L, (long) vr.getYI());
      Assert.assertEquals(0L, (long) vr.getZI());

      {
        VectorM3I.scaleInPlace(v, 0);

        Assert.assertEquals(0L, (long) v.getXI());
        Assert.assertEquals(0L, (long) v.getYI());
        Assert.assertEquals(0L, (long) v.getZI());
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM3I(1, 2, 3);
    Assert.assertTrue(v.toString().endsWith("1 2 3]"));
  }

  @Test public final void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3IContract.randomPositiveNumber();
      final int y0 = VectorM3IContract.randomPositiveNumber();
      final int z0 = VectorM3IContract.randomPositiveNumber();
      final T v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = VectorM3IContract.randomPositiveNumber();
      final int y1 = VectorM3IContract.randomPositiveNumber();
      final int z1 = VectorM3IContract.randomPositiveNumber();
      final T v1 = this.newVectorM3I(x1, y1, z1);

      final T vr0 = this.newVectorM3I();
      VectorM3I.subtract(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() - v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() - v1.getYI()), (long) vr0.getYI());
      Assert.assertEquals((long) (v0.getZI() - v1.getZI()), (long) vr0.getZI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3I.subtractInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x - v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y - v1.getYI()), (long) v0.getYI());
        Assert.assertEquals((long) (orig_z - v1.getZI()), (long) v0.getZI());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM3I();
    final T v0 = this.newVectorM3I(1, 1, 1);
    final T v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());

    final T ov0 = VectorM3I.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());

    final T ov1 = VectorM3I.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0L, (long) ov1.getXI());
    Assert.assertEquals(0L, (long) ov1.getYI());
    Assert.assertEquals(0L, (long) ov1.getZI());
    Assert.assertEquals(0L, (long) v0.getXI());
    Assert.assertEquals(0L, (long) v0.getYI());
    Assert.assertEquals(0L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
  }

}
