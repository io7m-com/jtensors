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
import com.io7m.jtensors.Vector3LType;
import com.io7m.jtensors.VectorM3L;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3LContract<T extends Vector3LType>
{
  public static long randomNegativeNumber()
  {
    return (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
  }

  public static long randomPositiveNumber()
  {
    return (long) (VectorM3LContract.getRandom() * (double) Long.MAX_VALUE);
  }

  public static long randomPositiveSmallNumber()
  {
    return (long) (VectorM3LContract.getRandom() * (double) (1 << 14));
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract T newVectorM3L(
    final long x,
    final long y,
    final long z);

  protected abstract T newVectorM3L();

  protected abstract T newVectorM3L(T v);

  @Test public final void testAbsolute()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final long y =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final long z =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      VectorM3L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final long y =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final long z =
        (long) (VectorM3LContract.getRandom() * (double) Long.MIN_VALUE);
      final T v = this.newVectorM3L(x, y, z);

      VectorM3L.absoluteInPlace(v);

      Assert.assertEquals(v.getXL(), Math.abs(x));
      Assert.assertEquals(v.getYL(), Math.abs(y));
      Assert.assertEquals(v.getZL(), Math.abs(z));
    }
  }

  @Test public final void testAdd()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = VectorM3LContract.randomPositiveSmallNumber();
      final long y0 = VectorM3LContract.randomPositiveSmallNumber();
      final long z0 = VectorM3LContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = VectorM3LContract.randomPositiveSmallNumber();
      final long y1 = VectorM3LContract.randomPositiveSmallNumber();
      final long z1 = VectorM3LContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3L(x1, y1, z1);

      final T vr0 = this.newVectorM3L();
      VectorM3L.add(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() + v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() + v1.getYL()), vr0.getYL());
      Assert.assertEquals((v0.getZL() + v1.getZL()), vr0.getZL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        VectorM3L.addInPlace(v0, v1);

        Assert.assertEquals((orig_x + v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y + v1.getYL()), v0.getYL());
        Assert.assertEquals((orig_z + v1.getZL()), v0.getZL());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM3L();
    final T v0 = this.newVectorM3L(1L, 1L, 1L);
    final T v1 = this.newVectorM3L(1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());

    final T ov0 = VectorM3L.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());
    Assert.assertEquals(2L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());

    final T ov1 = VectorM3L.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());
    Assert.assertEquals(2L, ov1.getZL());
    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());
    Assert.assertEquals(2L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
  }

  @Test public final void testAddScaled()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = VectorM3LContract.randomPositiveSmallNumber();
      final long y0 = VectorM3LContract.randomPositiveSmallNumber();
      final long z0 = VectorM3LContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = VectorM3LContract.randomPositiveSmallNumber();
      final long y1 = VectorM3LContract.randomPositiveSmallNumber();
      final long z1 = VectorM3LContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3L(x1, y1, z1);

      final long r = VectorM3LContract.randomPositiveSmallNumber();

      final T vr0 = this.newVectorM3L();
      VectorM3L.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals((v0.getXL() + (v1.getXL() * r)), vr0.getXL());
      Assert.assertEquals((v0.getYL() + (v1.getYL() * r)), vr0.getYL());
      Assert.assertEquals((v0.getZL() + (v1.getZL() * r)), vr0.getZL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        VectorM3L.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals((orig_x + (v1.getXL() * r)), v0.getXL());
        Assert.assertEquals((orig_y + (v1.getYL() * r)), v0.getYL());
        Assert.assertEquals((orig_z + (v1.getZL() * r)), v0.getZL());
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
    final T v = this.newVectorM3L(3L, 5L, 7L);

    Assert.assertEquals(v.getXL(), v.getXL());
    Assert.assertEquals(v.getYL(), v.getYL());
    Assert.assertEquals(v.getZL(), v.getZL());
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long max_x = VectorM3LContract.randomNegativeNumber();
      final long max_y = VectorM3LContract.randomNegativeNumber();
      final long max_z = VectorM3LContract.randomNegativeNumber();
      final T maximum = this.newVectorM3L(max_x, max_y, max_z);

      final long x = VectorM3LContract.randomNegativeNumber();
      final long y = VectorM3LContract.randomNegativeNumber();
      final long z = VectorM3LContract.randomNegativeNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      final T vo = VectorM3L.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());

      {
        final T vr0 = VectorM3L.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
      }
    }
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long min_x = VectorM3LContract.randomPositiveNumber();
      final long min_y = VectorM3LContract.randomPositiveNumber();
      final long min_z = VectorM3LContract.randomPositiveNumber();
      final T minimum = this.newVectorM3L(min_x, min_y, min_z);

      final long x = VectorM3LContract.randomNegativeNumber();
      final long y = VectorM3LContract.randomNegativeNumber();
      final long z = VectorM3LContract.randomNegativeNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      final T vo = VectorM3L.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());

      {
        final T vr0 = VectorM3L.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long min_x = VectorM3LContract.randomNegativeNumber();
      final long min_y = VectorM3LContract.randomNegativeNumber();
      final long min_z = VectorM3LContract.randomNegativeNumber();
      final T minimum = this.newVectorM3L(min_x, min_y, min_z);

      final long max_x = VectorM3LContract.randomPositiveNumber();
      final long max_y = VectorM3LContract.randomPositiveNumber();
      final long max_z = VectorM3LContract.randomPositiveNumber();
      final T maximum = this.newVectorM3L(max_x, max_y, max_z);

      final long x = VectorM3LContract.randomNegativeNumber();
      final long y = VectorM3LContract.randomPositiveNumber();
      final long z = VectorM3LContract.randomPositiveNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      final T vo = VectorM3L.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());

      {
        final T vr0 = VectorM3L.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long maximum = VectorM3LContract.randomNegativeNumber();

      final long x = VectorM3LContract.randomPositiveNumber();
      final long y = VectorM3LContract.randomPositiveNumber();
      final long z = VectorM3LContract.randomPositiveNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      VectorM3L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);

      {
        VectorM3L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getZL() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long minimum = VectorM3LContract.randomPositiveNumber();

      final long x = VectorM3LContract.randomNegativeNumber();
      final long y = VectorM3LContract.randomNegativeNumber();
      final long z = VectorM3LContract.randomNegativeNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      VectorM3L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);

      {
        VectorM3L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long minimum = VectorM3LContract.randomNegativeNumber();
      final long maximum = VectorM3LContract.randomPositiveNumber();

      final long x = VectorM3LContract.randomNegativeNumber();
      final long y = VectorM3LContract.randomPositiveNumber();
      final long z = VectorM3LContract.randomPositiveNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();
      VectorM3L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);

      {
        VectorM3L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() <= maximum);
        Assert.assertTrue(v.getZL() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM3L(5L, 6L, 7L);
    final T va = this.newVectorM3L(1L, 2L, 3L);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());

    VectorM3L.copy(va, vb);

    Assert.assertEquals(vb.getXL(), va.getXL());
    Assert.assertEquals(vb.getYL(), va.getYL());
    Assert.assertEquals(vb.getZL(), va.getZL());
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM3L(
      this.getLarge(), this.getLarge(), this.getLarge());
    final T v1 = this.newVectorM3L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(0L, v1.getZL());
  }

  protected long getLarge()
  {
    return (long) (VectorM3LContract.getRandom() * Long.MAX_VALUE);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM3L(
      this.getLarge(), this.getLarge(), this.getLarge());
    final T v1 = this.newVectorM3L();

    v1.copyFrom3L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());
  }

  @Test public final void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Test public final void testDefault000()
  {
    Assert.assertTrue(
      this.newVectorM3L().equals(this.newVectorM3L(0L, 0L, 0L)));
  }

  @Test public final void testDistance()
  {
    final VectorM3L.ContextVM3L c = new VectorM3L.ContextVM3L();
    final T v0 = this.newVectorM3L(0L, 1L, 0L);
    final T v1 = this.newVectorM3L(0L, 0L, 0L);
    Assert.assertEquals(1L, VectorM3L.distance(c, v0, v1));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM3L.ContextVM3L c = new VectorM3L.ContextVM3L();
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = VectorM3LContract.randomPositiveSmallNumber();
      final long y0 = VectorM3LContract.randomPositiveSmallNumber();
      final long z0 = VectorM3LContract.randomPositiveSmallNumber();
      final T v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = VectorM3LContract.randomPositiveSmallNumber();
      final long y1 = VectorM3LContract.randomPositiveSmallNumber();
      final long z1 = VectorM3LContract.randomPositiveSmallNumber();
      final T v1 = this.newVectorM3L(x1, y1, z1);

      Assert.assertTrue(VectorM3L.distance(c, v0, v1) >= 0L);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM3L(10L, 10L, 10L);
    final T v1 = this.newVectorM3L(10L, 10L, 10L);

    {
      final long p = VectorM3L.dotProduct(v0, v1);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(10L, v1.getZL());
      Assert.assertEquals(300L, p);

    }

    {
      final long p = VectorM3L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(300L, p);
    }

    {
      final long p = VectorM3L.dotProduct(v1, v1);
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(10L, v1.getZL());
      Assert.assertEquals(300L, p);
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
      final long x = (long) (VectorM3LContract.getRandom() * (double) max);
      final long y = (long) (VectorM3LContract.getRandom() * (double) max);
      final long z = (long) (VectorM3LContract.getRandom() * (double) max);
      final T q = this.newVectorM3L(x, y, z);

      final double ms = (double) VectorM3L.magnitudeSquared(q);
      final double dp = (double) VectorM3L.dotProduct(q, q);

      
      
      

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
      final long x = (long) (VectorM3LContract.getRandom() * (double) max);
      final long y = (long) (VectorM3LContract.getRandom() * (double) max);
      final long z = (long) (VectorM3LContract.getRandom() * (double) max);
      final T q = this.newVectorM3L(x, y, z);
      final double dp = (double) VectorM3L.dotProduct(q, q);

      
      

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM3L(10L, 10L, 10L);

    {
      final long p = VectorM3L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(300L, p);

    }

    {
      final long p = VectorM3L.magnitudeSquared(v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(300L, p);

    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM3L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM3L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3L();
      Assert.assertFalse(m0.equals(Long.valueOf(23L)));
    }

    {
      final T m0 = this.newVectorM3L();
      final T m1 = this.newVectorM3L();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final long x = (long) (VectorM3LContract.getRandom() * 1000.0);
    final long y = x + 1L;
    final long z = y + 1L;
    final long w = z + 1L;
    final long q = w + 1L;

    {
      final T m0 = this.newVectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(Long.valueOf(23L)));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3L(x, y, z);
      final T m1 = this.newVectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM3L();
    final T m1 = this.newVectorM3L();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM3L();
      final T m1 = this.newVectorM3L();
      m1.setXL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newVectorM3L();
      final T m1 = this.newVectorM3L();
      m1.setYL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newVectorM3L();
      final T m1 = this.newVectorM3L();
      m1.setZL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM3L(1L, 2L, 3L);
    final T v1 = this.newVectorM3L(v0);

    Assert.assertEquals(v1.getXL(), v0.getXL());
    Assert.assertEquals(v1.getYL(), v0.getYL());
    Assert.assertEquals(v1.getZL(), v0.getZL());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final VectorM3L.ContextVM3L c = new VectorM3L.ContextVM3L();
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = VectorM3LContract.randomPositiveNumber();
      final long y0 = VectorM3LContract.randomPositiveNumber();
      final long z0 = VectorM3LContract.randomPositiveNumber();
      final T v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = VectorM3LContract.randomPositiveNumber();
      final long y1 = VectorM3LContract.randomPositiveNumber();
      final long z1 = VectorM3LContract.randomPositiveNumber();
      final T v1 = this.newVectorM3L(x1, y1, z1);

      final T vr0 = this.newVectorM3L();
      final T vr1 = this.newVectorM3L();
      VectorM3L.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM3L.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals(vr0.getXL(), v0.getXL());
      Assert.assertEquals(vr0.getYL(), v0.getYL());
      Assert.assertEquals(vr0.getZL(), v0.getZL());

      Assert.assertEquals(vr1.getXL(), v1.getXL());
      Assert.assertEquals(vr1.getYL(), v1.getYL());
      Assert.assertEquals(vr1.getZL(), v1.getZL());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = VectorM3LContract.randomPositiveSmallNumber();
      final long y = VectorM3LContract.randomPositiveSmallNumber();
      final long z = VectorM3LContract.randomPositiveSmallNumber();
      final T v = this.newVectorM3L(x, y, z);

      final long m = VectorM3L.magnitude(v);
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
    final T v = this.newVectorM3L(1L, 0L, 0L);
    final long m = VectorM3L.magnitude(v);
    Assert.assertEquals(1L, m);

  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM3L(8L, 0L, 0L);

    {
      final long p = VectorM3L.dotProduct(v, v);
      final long q = VectorM3L.magnitudeSquared(v);
      final long r = VectorM3L.magnitude(v);
      Assert.assertEquals(64L, p);

      Assert.assertEquals(64L, q);

      Assert.assertEquals(8L, r);

    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM3L(0L, 0L, 0L);
    final long m = VectorM3L.magnitude(v);
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
      final T p = this.newVectorM3L(1L, 0L, 0L);
      final T q = this.newVectorM3L(0L, 1L, 0L);
      final T r = this.newVectorM3L();
      final T u = VectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, VectorM3L.magnitude(u));
    }

    {
      final T p = this.newVectorM3L(-1L, 0L, 0L);
      final T q = this.newVectorM3L(0L, 1L, 0L);
      final T r = this.newVectorM3L();
      final T u = VectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, VectorM3L.magnitude(u));
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM3L();
    final T v0 = this.newVectorM3L(1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());

    final T ov0 = VectorM3L.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());
    Assert.assertEquals(2L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());

    final T ov1 = VectorM3L.scaleInPlace(v0, 2L);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());
    Assert.assertEquals(2L, ov1.getZL());
    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());
    Assert.assertEquals(2L, v0.getZL());
  }

  @Test public final void testScaleOne()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = VectorM3LContract.randomPositiveNumber();
      final long y = VectorM3LContract.randomPositiveNumber();
      final long z = VectorM3LContract.randomPositiveNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();

      VectorM3L.scale(v, 1.0, vr);

      Assert.assertEquals(vr.getXL(), v.getXL());
      Assert.assertEquals(vr.getYL(), v.getYL());
      Assert.assertEquals(vr.getZL(), v.getZL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();
        final long orig_z = v.getZL();

        VectorM3L.scaleInPlace(v, 1L);

        Assert.assertEquals(orig_x, v.getXL());
        Assert.assertEquals(orig_y, v.getYL());
        Assert.assertEquals(orig_z, v.getZL());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x = VectorM3LContract.randomPositiveNumber();
      final long y = VectorM3LContract.randomPositiveNumber();
      final long z = VectorM3LContract.randomPositiveNumber();
      final T v = this.newVectorM3L(x, y, z);

      final T vr = this.newVectorM3L();

      VectorM3L.scale(v, 0.0, vr);

      Assert.assertEquals(0L, vr.getXL());
      Assert.assertEquals(0L, vr.getYL());
      Assert.assertEquals(0L, vr.getZL());

      {
        VectorM3L.scaleInPlace(v, 0L);

        Assert.assertEquals(0L, v.getXL());
        Assert.assertEquals(0L, v.getYL());
        Assert.assertEquals(0L, v.getZL());
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM3L(1L, 2L, 3L);
    Assert.assertTrue(v.toString().endsWith("1 2 3]"));
  }

  @Test public final void testSubtract()
  {
    for (long index = 0L; index
                          < (long) TestUtilities.TEST_RANDOM_ITERATIONS;
         ++index) {
      final long x0 = VectorM3LContract.randomPositiveNumber();
      final long y0 = VectorM3LContract.randomPositiveNumber();
      final long z0 = VectorM3LContract.randomPositiveNumber();
      final T v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = VectorM3LContract.randomPositiveNumber();
      final long y1 = VectorM3LContract.randomPositiveNumber();
      final long z1 = VectorM3LContract.randomPositiveNumber();
      final T v1 = this.newVectorM3L(x1, y1, z1);

      final T vr0 = this.newVectorM3L();
      VectorM3L.subtract(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() - v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() - v1.getYL()), vr0.getYL());
      Assert.assertEquals((v0.getZL() - v1.getZL()), vr0.getZL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        VectorM3L.subtractInPlace(v0, v1);

        Assert.assertEquals((orig_x - v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y - v1.getYL()), v0.getYL());
        Assert.assertEquals((orig_z - v1.getZL()), v0.getZL());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM3L();
    final T v0 = this.newVectorM3L(1L, 1L, 1L);
    final T v1 = this.newVectorM3L(1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());

    final T ov0 = VectorM3L.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());

    final T ov1 = VectorM3L.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0L, ov1.getXL());
    Assert.assertEquals(0L, ov1.getYL());
    Assert.assertEquals(0L, ov1.getZL());
    Assert.assertEquals(0L, v0.getXL());
    Assert.assertEquals(0L, v0.getYL());
    Assert.assertEquals(0L, v0.getZL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
  }

}
