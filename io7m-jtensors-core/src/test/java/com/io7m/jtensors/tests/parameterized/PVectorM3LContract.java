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
import com.io7m.jtensors.parameterized.PVectorM3L;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3LContract<T> extends PVectorM3Contract
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
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      PVectorM3L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
    }
  }

  protected abstract <T> PVectorM3L<T> newVectorM3L(
    final long x,
    final long y,
    final long z);

  @Override @Test public void testAbsoluteMutation()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      PVectorM3L.absoluteInPlace(v);

      Assert.assertEquals(v.getXL(), Math.abs(x));
      Assert.assertEquals(v.getYL(), Math.abs(y));
      Assert.assertEquals(v.getZL(), Math.abs(z));
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v1 = this.newVectorM3L(x1, y1, z1);

      final PVectorM3L<T> vr0 = this.newVectorM3L();
      PVectorM3L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + v1.getZL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        PVectorM3L.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x + v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y + v1.getYL()));
        Assert.assertTrue(v0.getZL() == (orig_z + v1.getZL()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final PVectorM3L<T> out = this.newVectorM3L();
    final PVectorM3L<T> v0 = this.newVectorM3L(1, 1, 1);
    final PVectorM3L<T> v1 = this.newVectorM3L(1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);

    final PVectorM3L<T> ov0 = PVectorM3L.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);
    Assert.assertTrue(out.getZL() == 2);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);

    final PVectorM3L<T> ov1 = PVectorM3L.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);
    Assert.assertTrue(ov1.getZL() == 2);
    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);
    Assert.assertTrue(v0.getZL() == 2);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v1 = this.newVectorM3L(x1, y1, z1);

      final long r = PVectorM3LContract.randomPositiveSmallNumber();

      final PVectorM3L<T> vr0 = this.newVectorM3L();
      PVectorM3L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + (v1.getZL() * r)));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        PVectorM3L.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXL() == (orig_x + (v1.getXL() * r)));
        Assert.assertTrue(v0.getYL() == (orig_y + (v1.getYL() * r)));
        Assert.assertTrue(v0.getZL() == (orig_z + (v1.getZL() * r)));
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

  @Override @Test public void testCheckInterface()
  {
    final PVectorM3L<T> v = this.newVectorM3L(3, 5, 7);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
    Assert.assertTrue(v.getZL() == v.getZL());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = PVectorM3LContract.randomNegativeNumber();
      final long max_y = PVectorM3LContract.randomNegativeNumber();
      final long max_z = PVectorM3LContract.randomNegativeNumber();
      final PVectorM3L<T> maximum = this.newVectorM3L(max_x, max_y, max_z);

      final long x = PVectorM3LContract.randomNegativeNumber();
      final long y = PVectorM3LContract.randomNegativeNumber();
      final long z = PVectorM3LContract.randomNegativeNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      final PVectorM3L<T> vo = PVectorM3L.clampMaximumByPVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());

      {
        final PVectorM3L<T> vr0 =
          PVectorM3L.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
      }
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorM3LContract.randomPositiveNumber();
      final long min_y = PVectorM3LContract.randomPositiveNumber();
      final long min_z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> minimum = this.newVectorM3L(min_x, min_y, min_z);

      final long x = PVectorM3LContract.randomNegativeNumber();
      final long y = PVectorM3LContract.randomNegativeNumber();
      final long z = PVectorM3LContract.randomNegativeNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      final PVectorM3L<T> vo = PVectorM3L.clampMinimumByPVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());

      {
        final PVectorM3L<T> vr0 =
          PVectorM3L.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
      }
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorM3LContract.randomNegativeNumber();
      final long min_y = PVectorM3LContract.randomNegativeNumber();
      final long min_z = PVectorM3LContract.randomNegativeNumber();
      final PVectorM3L<T> minimum = this.newVectorM3L(min_x, min_y, min_z);

      final long max_x = PVectorM3LContract.randomPositiveNumber();
      final long max_y = PVectorM3LContract.randomPositiveNumber();
      final long max_z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> maximum = this.newVectorM3L(max_x, max_y, max_z);

      final long x = PVectorM3LContract.randomNegativeNumber();
      final long y = PVectorM3LContract.randomPositiveNumber();
      final long z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      final PVectorM3L<T> vo =
        PVectorM3L.clampByPVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());

      {
        final PVectorM3L<T> vr0 =
          PVectorM3L.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = PVectorM3LContract.randomNegativeNumber();

      final long x = PVectorM3LContract.randomPositiveNumber();
      final long y = PVectorM3LContract.randomPositiveNumber();
      final long z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      PVectorM3L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);

      {
        PVectorM3L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getZL() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorM3LContract.randomPositiveNumber();

      final long x = PVectorM3LContract.randomNegativeNumber();
      final long y = PVectorM3LContract.randomNegativeNumber();
      final long z = PVectorM3LContract.randomNegativeNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      PVectorM3L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);

      {
        PVectorM3L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorM3LContract.randomNegativeNumber();
      final long maximum = PVectorM3LContract.randomPositiveNumber();

      final long x = PVectorM3LContract.randomNegativeNumber();
      final long y = PVectorM3LContract.randomPositiveNumber();
      final long z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();
      PVectorM3L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);

      {
        PVectorM3L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() <= maximum);
        Assert.assertTrue(v.getZL() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorM3L<T> vb = this.newVectorM3L(5, 6, 7);
    final PVectorM3L<T> va = this.newVectorM3L(1, 2, 3);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());

    PVectorM3L.copy(va, vb);

    Assert.assertTrue(va.getXL() == vb.getXL());
    Assert.assertTrue(va.getYL() == vb.getYL());
    Assert.assertTrue(va.getZL() == vb.getZL());
  }

  @Override @Test public void testCopy2Correct()
  {
    final PVectorM3L<T> v0 = this.newVectorM3L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final PVectorM3L<T> v1 = this.newVectorM3L();
    final PVectorM3L<T> v2 = this.newVectorM3L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(0, v1.getZL());

    v2.copyFromTyped2L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
    Assert.assertEquals(0, v2.getZL());
  }

  @Override @Test public void testCopy3Correct()
  {
    final PVectorM3L<T> v0 = this.newVectorM3L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final PVectorM3L<T> v1 = this.newVectorM3L();
    final PVectorM3L<T> v2 = this.newVectorM3L();

    v1.copyFrom3L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());

    v2.copyFromTyped3L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
    Assert.assertEquals(v0.getZL(), v2.getZL());
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Override @Test public void testDefault000()
  {
    Assert.assertTrue(this.newVectorM3L().equals(this.newVectorM3L(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorM3L.ContextPVM3L c = new PVectorM3L.ContextPVM3L();
    final PVectorM3L<T> v0 = this.newVectorM3L(0, 1, 0);
    final PVectorM3L<T> v1 = this.newVectorM3L(0, 0, 0);
    Assert.assertTrue(PVectorM3L.distance(c, v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    final PVectorM3L.ContextPVM3L c = new PVectorM3L.ContextPVM3L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM3LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v1 = this.newVectorM3L(x1, y1, z1);

      Assert.assertTrue(PVectorM3L.distance(c, v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorM3L<T> v0 = this.newVectorM3L(10, 10, 10);
    final PVectorM3L<T> v1 = this.newVectorM3L(10, 10, 10);

    {
      final long p = PVectorM3L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorM3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorM3L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(p == 300);
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
      final long z = (long) (Math.random() * max);
      final PVectorM3L<T> q = this.newVectorM3L(x, y, z);

      final double ms = PVectorM3L.magnitudeSquared(q);
      final double dp = PVectorM3L.dotProduct(q, q);

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
      final long z = (long) (Math.random() * max);
      final PVectorM3L<T> q = this.newVectorM3L(x, y, z);
      final double dp = PVectorM3L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorM3L<T> v0 = this.newVectorM3L(10, 10, 10);

    {
      final long p = PVectorM3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = PVectorM3L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      final PVectorM3L<T> m1 = this.newVectorM3L();
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
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L(x, y, z);
      final PVectorM3L<T> m1 = this.newVectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorM3L<T> m0 = this.newVectorM3L();
    final PVectorM3L<T> m1 = this.newVectorM3L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      final PVectorM3L<T> m1 = this.newVectorM3L();
      m1.setXL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      final PVectorM3L<T> m1 = this.newVectorM3L();
      m1.setYL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3L<T> m0 = this.newVectorM3L();
      final PVectorM3L<T> m1 = this.newVectorM3L();
      m1.setZL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorM3L<T> v0 = this.newVectorM3L(1, 2, 3);
    final PVectorM3L<T> v1 = new PVectorM3L<T>(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
    Assert.assertTrue(v0.getZL() == v1.getZL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final PVectorM3L.ContextPVM3L c = new PVectorM3L.ContextPVM3L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM3LContract.randomPositiveNumber();
      final long y0 = PVectorM3LContract.randomPositiveNumber();
      final long z0 = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = PVectorM3LContract.randomPositiveNumber();
      final long y1 = PVectorM3LContract.randomPositiveNumber();
      final long z1 = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v1 = this.newVectorM3L(x1, y1, z1);

      final PVectorM3L<T> vr0 = this.newVectorM3L();
      final PVectorM3L<T> vr1 = this.newVectorM3L();
      PVectorM3L.interpolateLinear(c, v0, v1, 0, vr0);
      PVectorM3L.interpolateLinear(c, v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());
      Assert.assertTrue(v0.getZL() == vr0.getZL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
      Assert.assertTrue(v1.getZL() == vr1.getZL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM3LContract.randomPositiveSmallNumber();
      final long y = PVectorM3LContract.randomPositiveSmallNumber();
      final long z = PVectorM3LContract.randomPositiveSmallNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final long m = PVectorM3L.magnitude(v);
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
    final PVectorM3L<T> v = this.newVectorM3L(1, 0, 0);
    final long m = PVectorM3L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorM3L<T> v = this.newVectorM3L(8, 0, 0);

    {
      final long p = PVectorM3L.dotProduct(v, v);
      final long q = PVectorM3L.magnitudeSquared(v);
      final long r = PVectorM3L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorM3L<T> v = this.newVectorM3L(0, 0, 0);
    final long m = PVectorM3L.magnitude(v);
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
      final PVectorM3L<T> p = this.newVectorM3L(1, 0, 0);
      final PVectorM3L<T> q = this.newVectorM3L(0, 1, 0);
      final PVectorM3L<T> r = this.newVectorM3L();
      final PVectorM3L<T> u = PVectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3L.magnitude(u) == 0);
    }

    {
      final PVectorM3L<T> p = this.newVectorM3L(-1, 0, 0);
      final PVectorM3L<T> q = this.newVectorM3L(0, 1, 0);
      final PVectorM3L<T> r = this.newVectorM3L();
      final PVectorM3L<T> u = PVectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final PVectorM3L<T> out = this.newVectorM3L();
    final PVectorM3L<T> v0 = this.newVectorM3L(1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);

    final PVectorM3L<T> ov0 = PVectorM3L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);
    Assert.assertTrue(out.getZL() == 2);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);

    final PVectorM3L<T> ov1 = PVectorM3L.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);
    Assert.assertTrue(ov1.getZL() == 2);
    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);
    Assert.assertTrue(v0.getZL() == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM3LContract.randomPositiveNumber();
      final long y = PVectorM3LContract.randomPositiveNumber();
      final long z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();

      PVectorM3L.scale(v, 1, vr);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
      Assert.assertTrue(v.getZL() == vr.getZL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();
        final long orig_z = v.getZL();

        PVectorM3L.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXL() == orig_x);
        Assert.assertTrue(v.getYL() == orig_y);
        Assert.assertTrue(v.getZL() == orig_z);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM3LContract.randomPositiveNumber();
      final long y = PVectorM3LContract.randomPositiveNumber();
      final long z = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v = this.newVectorM3L(x, y, z);

      final PVectorM3L<T> vr = this.newVectorM3L();

      PVectorM3L.scale(v, 0, vr);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
      Assert.assertTrue(vr.getZL() == 0);

      {
        PVectorM3L.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXL() == 0);
        Assert.assertTrue(v.getYL() == 0);
        Assert.assertTrue(v.getZL() == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final PVectorM3L<T> v = this.newVectorM3L(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[PVectorM3L 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM3LContract.randomPositiveNumber();
      final long y0 = PVectorM3LContract.randomPositiveNumber();
      final long z0 = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v0 = this.newVectorM3L(x0, y0, z0);

      final long x1 = PVectorM3LContract.randomPositiveNumber();
      final long y1 = PVectorM3LContract.randomPositiveNumber();
      final long z1 = PVectorM3LContract.randomPositiveNumber();
      final PVectorM3L<T> v1 = this.newVectorM3L(x1, y1, z1);

      final PVectorM3L<T> vr0 = this.newVectorM3L();
      PVectorM3L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() - v1.getZL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        PVectorM3L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x - v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y - v1.getYL()));
        Assert.assertTrue(v0.getZL() == (orig_z - v1.getZL()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final PVectorM3L<T> out = this.newVectorM3L();
    final PVectorM3L<T> v0 = this.newVectorM3L(1, 1, 1);
    final PVectorM3L<T> v1 = this.newVectorM3L(1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);

    final PVectorM3L<T> ov0 = PVectorM3L.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);

    final PVectorM3L<T> ov1 = PVectorM3L.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 0);
    Assert.assertTrue(ov1.getYL() == 0);
    Assert.assertTrue(ov1.getZL() == 0);
    Assert.assertTrue(v0.getXL() == 0);
    Assert.assertTrue(v0.getYL() == 0);
    Assert.assertTrue(v0.getZL() == 0);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
  }

  protected abstract <T> PVectorM3L<T> newVectorM3L();
}
