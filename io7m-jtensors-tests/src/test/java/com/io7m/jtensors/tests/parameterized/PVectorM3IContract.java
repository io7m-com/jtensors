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
import com.io7m.jtensors.parameterized.PVector3IType;
import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3IContract<T, V extends PVector3IType<T>>
{
  public static int randomNegativeNumber()
  {
    return (int) (getRandom() * (double) Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (getRandom() * (double) Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (getRandom() * (double) (1 << 14));
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected static int getLarge()
  {
    return (int) (getRandom() * Integer.MAX_VALUE);
  }

  protected abstract V newVectorM3I(
    final int x,
    final int y,
    final int z);

  protected abstract V newVectorM3I();

  protected abstract V newVectorM3I(V v);

  @Test
  public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      PVectorM3I.absolute(v, vr);

      Assert.assertEquals((long) Math.abs(v.getXI()), (long) vr.getXI());
      Assert.assertEquals((long) Math.abs(v.getYI()), (long) vr.getYI());
      Assert.assertEquals((long) Math.abs(v.getZI()), (long) vr.getZI());
    }
  }

  @Test
  public final void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final V v = this.newVectorM3I(x, y, z);

      PVectorM3I.absoluteInPlace(v);

      Assert.assertEquals((long) v.getXI(), (long) Math.abs(x));
      Assert.assertEquals((long) v.getYI(), (long) Math.abs(y));
      Assert.assertEquals((long) v.getZI(), (long) Math.abs(z));
    }
  }

  @Test
  public final void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final V v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final V v1 = this.newVectorM3I(x1, y1, z1);

      final V vr0 = this.newVectorM3I();
      PVectorM3I.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        PVectorM3I.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x + v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y + v1.getYI()));
        Assert.assertTrue(v0.getZI() == (orig_z + v1.getZI()));
      }
    }
  }

  @Test
  public final void testAddMutation()
  {
    final V out = this.newVectorM3I();
    final V v0 = this.newVectorM3I(1, 1, 1);
    final V v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final V ov0 = PVectorM3I.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(out.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final V ov1 = PVectorM3I.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(ov1.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
    Assert.assertTrue(v0.getZI() == 2);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);
  }

  @Test
  public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final V v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final V v1 = this.newVectorM3I(x1, y1, z1);

      final int r = randomPositiveSmallNumber();

      final V vr0 = this.newVectorM3I();
      PVectorM3I.addScaled(v0, v1, (double) r, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        PVectorM3I.addScaledInPlace(v0, v1, (double) r);

        Assert.assertTrue(v0.getXI() == (orig_x + (v1.getXI() * r)));
        Assert.assertTrue(v0.getYI() == (orig_y + (v1.getYI() * r)));
        Assert.assertTrue(v0.getZI() == (orig_z + (v1.getZI() * r)));
      }
    }
  }

  @Test
  public final void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testCheckInterface()
  {
    final V v = this.newVectorM3I(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Test
  public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = randomNegativeNumber();
      final int max_y = randomNegativeNumber();
      final int max_z = randomNegativeNumber();
      final V maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      final V vo = PVectorM3I.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());

      {
        final V vr0 = PVectorM3I.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
      }
    }
  }

  @Test
  public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = randomPositiveNumber();
      final int min_y = randomPositiveNumber();
      final int min_z = randomPositiveNumber();
      final V minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      final V vo = PVectorM3I.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final V vr0 = PVectorM3I.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Test
  public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = randomNegativeNumber();
      final int min_y = randomNegativeNumber();
      final int min_z = randomNegativeNumber();
      final V minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int max_x = randomPositiveNumber();
      final int max_y = randomPositiveNumber();
      final int max_z = randomPositiveNumber();
      final V maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = randomNegativeNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      final V vo = PVectorM3I.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final V vr0 = PVectorM3I.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Test
  public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = randomNegativeNumber();

      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      PVectorM3I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);

      {
        PVectorM3I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getZI() <= maximum);
      }
    }
  }

  @Test
  public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = randomPositiveNumber();

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      PVectorM3I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        PVectorM3I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Test
  public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = randomNegativeNumber();
      final int maximum = randomPositiveNumber();

      final int x = randomNegativeNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();
      PVectorM3I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        PVectorM3I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() <= maximum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Test
  public final void testCopy()
  {
    final V vb = this.newVectorM3I(5, 6, 7);
    final V va = this.newVectorM3I(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    PVectorM3I.copy(va, vb);

    Assert.assertTrue(va.getXI() == vb.getXI());
    Assert.assertTrue(va.getYI() == vb.getYI());
    Assert.assertTrue(va.getZI() == vb.getZI());
  }

  @Test
  public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM3I(
      getLarge(),
      getLarge(),

      getLarge());
    final V v1 = this.newVectorM3I();
    final V v2 = this.newVectorM3I();

    v1.copyFrom2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals(0L, (long) v1.getZI());

    v2.copyFromTyped2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v2.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v2.getYI());
    Assert.assertEquals(0L, (long) v2.getZI());
  }

  @Test
  public final void testCopy3Correct()
  {
    final V v0 = this.newVectorM3I(
      getLarge(),
      getLarge(),
      getLarge());
    final V v1 = this.newVectorM3I();
    final V v2 = this.newVectorM3I();

    v1.copyFrom3I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals((long) v0.getZI(), (long) v1.getZI());

    v2.copyFromTyped3I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v2.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v2.getYI());
    Assert.assertEquals((long) v0.getZI(), (long) v2.getZI());
  }

  @Test
  public final void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Test
  public final void testDefault000()
  {
    Assert.assertTrue(this.newVectorM3I().equals(this.newVectorM3I(0, 0, 0)));
  }

  @Test
  public final void testDistance()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    final V v0 = this.newVectorM3I(0, 1, 0);
    final V v1 = this.newVectorM3I(0, 0, 0);
    Assert.assertTrue(PVectorM3I.distance(c, v0, v1) == 1);
  }

  @Test
  public final void testDistanceOrdering()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final V v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final V v1 = this.newVectorM3I(x1, y1, z1);

      Assert.assertTrue(PVectorM3I.distance(c, v0, v1) >= 0);
    }
  }

  @Test
  public final void testDotProduct()
  {
    final V v0 = this.newVectorM3I(10, 10, 10);
    final V v1 = this.newVectorM3I(10, 10, 10);

    {
      final int p = PVectorM3I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = PVectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = PVectorM3I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertEquals(300L, (long) p);
    }
  }

  @Test
  public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (getRandom() * (double) max);
      final int y = (int) (getRandom() * (double) max);
      final int z = (int) (getRandom() * (double) max);
      final V q = this.newVectorM3I(x, y, z);

      final double ms = (double) PVectorM3I.magnitudeSquared(q);
      final double dp = (double) PVectorM3I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test
  public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (getRandom() * (double) max);
      final int y = (int) (getRandom() * (double) max);
      final int z = (int) (getRandom() * (double) max);
      final V q = this.newVectorM3I(x, y, z);
      final double dp = (double) PVectorM3I.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test
  public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM3I(10, 10, 10);

    {
      final int p = PVectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertEquals(300L, (long) p);
    }

    {
      final int p = PVectorM3I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertEquals(300L, (long) p);
    }
  }

  @Test
  public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM3I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM3I();
      final V m1 = this.newVectorM3I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test
  public final void testEqualsNotEqualCorrect()
  {
    final int x = (int) (getRandom() * 1000.0);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final V m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3I(x, y, z);
      final V m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test
  public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM3I();
    final V m1 = this.newVectorM3I();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test
  public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM3I();
      final V m1 = this.newVectorM3I();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final V m0 = this.newVectorM3I();
      final V m1 = this.newVectorM3I();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final V m0 = this.newVectorM3I();
      final V m1 = this.newVectorM3I();
      m1.setZI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Test
  public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM3I(1, 2, 3);
    final V v1 = this.newVectorM3I(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Test
  public final void testInterpolateLinearLimits()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveNumber();
      final int y0 = randomPositiveNumber();
      final int z0 = randomPositiveNumber();
      final V v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = randomPositiveNumber();
      final int y1 = randomPositiveNumber();
      final int z1 = randomPositiveNumber();
      final V v1 = this.newVectorM3I(x1, y1, z1);

      final V vr0 = this.newVectorM3I();
      final V vr1 = this.newVectorM3I();
      PVectorM3I.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM3I.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Test
  public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveSmallNumber();
      final int y = randomPositiveSmallNumber();
      final int z = randomPositiveSmallNumber();
      final V v = this.newVectorM3I(x, y, z);

      final int m = PVectorM3I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Test
  public final void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testMagnitudeOne()
  {
    final V v = this.newVectorM3I(1, 0, 0);
    final int m = PVectorM3I.magnitude(v);
    Assert.assertEquals(1L, (long) m);
  }

  @Test
  public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM3I(8, 0, 0);

    {
      final int p = PVectorM3I.dotProduct(v, v);
      final int q = PVectorM3I.magnitudeSquared(v);
      final int r = PVectorM3I.magnitude(v);
      Assert.assertEquals(64L, (long) p);
      Assert.assertEquals(64L, (long) q);
      Assert.assertEquals(8L, (long) r);
    }
  }

  @Test
  public final void testMagnitudeZero()
  {
    final V v = this.newVectorM3I(0, 0, 0);
    final int m = PVectorM3I.magnitude(v);
    Assert.assertEquals(0L, (long) m);
  }

  @Test
  public final void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM3I(1, 0, 0);
      final V q = this.newVectorM3I(0, 1, 0);
      final V r = this.newVectorM3I();
      final V u = PVectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3I.magnitude(u) == 0);
    }

    {
      final V p = this.newVectorM3I(-1, 0, 0);
      final V q = this.newVectorM3I(0, 1, 0);
      final V r = this.newVectorM3I();
      final V u = PVectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3I.magnitude(u) == 0);
    }
  }

  @Test
  public final void testScaleMutation()
  {
    final V out = this.newVectorM3I();
    final V v0 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final V ov0 = PVectorM3I.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(out.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final V ov1 = PVectorM3I.scaleInPlace(v0, 2);

    Assert.assertEquals(v0, ov1);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(ov1.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
    Assert.assertTrue(v0.getZI() == 2);
  }

  @Test
  public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();

      PVectorM3I.scale(v, 1.0, vr);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();
        final int orig_z = v.getZI();

        PVectorM3I.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXI() == orig_x);
        Assert.assertTrue(v.getYI() == orig_y);
        Assert.assertTrue(v.getZI() == orig_z);
      }
    }
  }

  @Test
  public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final V v = this.newVectorM3I(x, y, z);

      final V vr = this.newVectorM3I();

      PVectorM3I.scale(v, 0.0, vr);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);

      {
        PVectorM3I.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXI() == 0);
        Assert.assertTrue(v.getYI() == 0);
        Assert.assertTrue(v.getZI() == 0);
      }
    }
  }

  @Test
  public final void testString()
  {
    final V v = this.newVectorM3I(1, 2, 3);
    Assert.assertTrue(v.toString().endsWith("1 2 3]"));
  }

  @Test
  public final void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveNumber();
      final int y0 = randomPositiveNumber();
      final int z0 = randomPositiveNumber();
      final V v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = randomPositiveNumber();
      final int y1 = randomPositiveNumber();
      final int z1 = randomPositiveNumber();
      final V v1 = this.newVectorM3I(x1, y1, z1);

      final V vr0 = this.newVectorM3I();
      PVectorM3I.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        PVectorM3I.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x - v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y - v1.getYI()));
        Assert.assertTrue(v0.getZI() == (orig_z - v1.getZI()));
      }
    }
  }

  @Test
  public final void testSubtractMutation()
  {
    final V out = this.newVectorM3I();
    final V v0 = this.newVectorM3I(1, 1, 1);
    final V v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final V ov0 = PVectorM3I.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final V ov1 = PVectorM3I.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertTrue(ov1.getXI() == 0);
    Assert.assertTrue(ov1.getYI() == 0);
    Assert.assertTrue(ov1.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 0);
    Assert.assertTrue(v0.getYI() == 0);
    Assert.assertTrue(v0.getZI() == 0);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);
  }

}
