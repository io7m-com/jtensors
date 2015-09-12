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
import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3IContract<T> extends PVectorM3Contract
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
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
      PVectorM3I.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
    }
  }

  protected abstract <T> PVectorM3I<T> newVectorM3I(
    final int x,
    final int y,
    final int z);

  @Override @Test public void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      PVectorM3I.absoluteInPlace(v);

      Assert.assertEquals(v.getXI(), Math.abs(x));
      Assert.assertEquals(v.getYI(), Math.abs(y));
      Assert.assertEquals(v.getZI(), Math.abs(z));
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z0 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z1 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v1 = this.newVectorM3I(x1, y1, z1);

      final PVectorM3I<T> vr0 = this.newVectorM3I();
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

  @Override @Test public void testAddMutation()
  {
    final PVectorM3I<T> out = this.newVectorM3I();
    final PVectorM3I<T> v0 = this.newVectorM3I(1, 1, 1);
    final PVectorM3I<T> v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final PVectorM3I<T> ov0 = PVectorM3I.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(out.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final PVectorM3I<T> ov1 = PVectorM3I.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
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

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z0 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z1 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v1 = this.newVectorM3I(x1, y1, z1);

      final int r = PVectorM3IContract.randomPositiveSmallNumber();

      final PVectorM3I<T> vr0 = this.newVectorM3I();
      PVectorM3I.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        PVectorM3I.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXI() == (orig_x + (v1.getXI() * r)));
        Assert.assertTrue(v0.getYI() == (orig_y + (v1.getYI() * r)));
        Assert.assertTrue(v0.getZI() == (orig_z + (v1.getZI() * r)));
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
    final PVectorM3I<T> v = this.newVectorM3I(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = PVectorM3IContract.randomNegativeNumber();
      final int max_y = PVectorM3IContract.randomNegativeNumber();
      final int max_z = PVectorM3IContract.randomNegativeNumber();
      final PVectorM3I<T> maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = PVectorM3IContract.randomNegativeNumber();
      final int y = PVectorM3IContract.randomNegativeNumber();
      final int z = PVectorM3IContract.randomNegativeNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
      final PVectorM3I<T> vo = PVectorM3I.clampMaximumByPVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());

      {
        final PVectorM3I<T> vr0 =
          PVectorM3I.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
      }
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorM3IContract.randomPositiveNumber();
      final int min_y = PVectorM3IContract.randomPositiveNumber();
      final int min_z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int x = PVectorM3IContract.randomNegativeNumber();
      final int y = PVectorM3IContract.randomNegativeNumber();
      final int z = PVectorM3IContract.randomNegativeNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
      final PVectorM3I<T> vo = PVectorM3I.clampMinimumByPVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final PVectorM3I<T> vr0 =
          PVectorM3I.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = PVectorM3IContract.randomNegativeNumber();
      final int min_y = PVectorM3IContract.randomNegativeNumber();
      final int min_z = PVectorM3IContract.randomNegativeNumber();
      final PVectorM3I<T> minimum = this.newVectorM3I(min_x, min_y, min_z);

      final int max_x = PVectorM3IContract.randomPositiveNumber();
      final int max_y = PVectorM3IContract.randomPositiveNumber();
      final int max_z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> maximum = this.newVectorM3I(max_x, max_y, max_z);

      final int x = PVectorM3IContract.randomNegativeNumber();
      final int y = PVectorM3IContract.randomPositiveNumber();
      final int z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
      final PVectorM3I<T> vo =
        PVectorM3I.clampByPVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final PVectorM3I<T> vr0 =
          PVectorM3I.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = PVectorM3IContract.randomNegativeNumber();

      final int x = PVectorM3IContract.randomPositiveNumber();
      final int y = PVectorM3IContract.randomPositiveNumber();
      final int z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
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

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorM3IContract.randomPositiveNumber();

      final int x = PVectorM3IContract.randomNegativeNumber();
      final int y = PVectorM3IContract.randomNegativeNumber();
      final int z = PVectorM3IContract.randomNegativeNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
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

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = PVectorM3IContract.randomNegativeNumber();
      final int maximum = PVectorM3IContract.randomPositiveNumber();

      final int x = PVectorM3IContract.randomNegativeNumber();
      final int y = PVectorM3IContract.randomPositiveNumber();
      final int z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();
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

  @Override @Test public void testCopy()
  {
    final PVectorM3I<T> vb = this.newVectorM3I(5, 6, 7);
    final PVectorM3I<T> va = this.newVectorM3I(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    PVectorM3I.copy(va, vb);

    Assert.assertTrue(va.getXI() == vb.getXI());
    Assert.assertTrue(va.getYI() == vb.getYI());
    Assert.assertTrue(va.getZI() == vb.getZI());
  }

  @Override @Test public void testCopy2Correct()
  {
    final PVectorM3I<T> v0 = this.newVectorM3I(
      (int) Math.random() * Integer.MAX_VALUE,
      (int) Math.random() * Integer.MAX_VALUE,
      (int) Math.random() * Integer.MAX_VALUE);
    final PVectorM3I<T> v1 = this.newVectorM3I();
    final PVectorM3I<T> v2 = this.newVectorM3I();

    v1.copyFrom2I(v0);

    Assert.assertEquals(v0.getXI(), v1.getXI());
    Assert.assertEquals(v0.getYI(), v1.getYI());
    Assert.assertEquals(0, v1.getZI());

    v2.copyFromTyped2I(v0);

    Assert.assertEquals(v0.getXI(), v2.getXI());
    Assert.assertEquals(v0.getYI(), v2.getYI());
    Assert.assertEquals(0, v2.getZI());
  }

  @Override @Test public void testCopy3Correct()
  {
    final PVectorM3I<T> v0 = this.newVectorM3I(
      (int) Math.random() * Integer.MAX_VALUE,
      (int) Math.random() * Integer.MAX_VALUE,
      (int) Math.random() * Integer.MAX_VALUE);
    final PVectorM3I<T> v1 = this.newVectorM3I();
    final PVectorM3I<T> v2 = this.newVectorM3I();

    v1.copyFrom3I(v0);

    Assert.assertEquals(v0.getXI(), v1.getXI());
    Assert.assertEquals(v0.getYI(), v1.getYI());
    Assert.assertEquals(v0.getZI(), v1.getZI());

    v2.copyFromTyped3I(v0);

    Assert.assertEquals(v0.getXI(), v2.getXI());
    Assert.assertEquals(v0.getYI(), v2.getYI());
    Assert.assertEquals(v0.getZI(), v2.getZI());
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Override @Test public void testDefault000()
  {
    Assert.assertTrue(this.newVectorM3I().equals(this.newVectorM3I(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    final PVectorM3I<T> v0 = this.newVectorM3I(0, 1, 0);
    final PVectorM3I<T> v1 = this.newVectorM3I(0, 0, 0);
    Assert.assertTrue(PVectorM3I.distance(c, v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y0 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z0 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int y1 = PVectorM3IContract.randomPositiveSmallNumber();
      final int z1 = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v1 = this.newVectorM3I(x1, y1, z1);

      Assert.assertTrue(PVectorM3I.distance(c, v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorM3I<T> v0 = this.newVectorM3I(10, 10, 10);
    final PVectorM3I<T> v1 = this.newVectorM3I(10, 10, 10);

    {
      final int p = PVectorM3I.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorM3I.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
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
      final int z = (int) (Math.random() * max);
      final PVectorM3I<T> q = this.newVectorM3I(x, y, z);

      final double ms = PVectorM3I.magnitudeSquared(q);
      final double dp = PVectorM3I.dotProduct(q, q);

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
      final int z = (int) (Math.random() * max);
      final PVectorM3I<T> q = this.newVectorM3I(x, y, z);
      final double dp = PVectorM3I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorM3I<T> v0 = this.newVectorM3I(10, 10, 10);

    {
      final int p = PVectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = PVectorM3I.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      final PVectorM3I<T> m1 = this.newVectorM3I();
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
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I(x, y, z);
      final PVectorM3I<T> m1 = this.newVectorM3I(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorM3I<T> m0 = this.newVectorM3I();
    final PVectorM3I<T> m1 = this.newVectorM3I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      final PVectorM3I<T> m1 = this.newVectorM3I();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      final PVectorM3I<T> m1 = this.newVectorM3I();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3I<T> m0 = this.newVectorM3I();
      final PVectorM3I<T> m1 = this.newVectorM3I();
      m1.setZI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorM3I<T> v0 = this.newVectorM3I(1, 2, 3);
    final PVectorM3I<T> v1 = new PVectorM3I<T>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final PVectorM3I.ContextPVM3I c = new PVectorM3I.ContextPVM3I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM3IContract.randomPositiveNumber();
      final int y0 = PVectorM3IContract.randomPositiveNumber();
      final int z0 = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = PVectorM3IContract.randomPositiveNumber();
      final int y1 = PVectorM3IContract.randomPositiveNumber();
      final int z1 = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v1 = this.newVectorM3I(x1, y1, z1);

      final PVectorM3I<T> vr0 = this.newVectorM3I();
      final PVectorM3I<T> vr1 = this.newVectorM3I();
      PVectorM3I.interpolateLinear(c, v0, v1, 0, vr0);
      PVectorM3I.interpolateLinear(c, v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM3IContract.randomPositiveSmallNumber();
      final int y = PVectorM3IContract.randomPositiveSmallNumber();
      final int z = PVectorM3IContract.randomPositiveSmallNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final int m = PVectorM3I.magnitude(v);
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
    final PVectorM3I<T> v = this.newVectorM3I(1, 0, 0);
    final int m = PVectorM3I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorM3I<T> v = this.newVectorM3I(8, 0, 0);

    {
      final int p = PVectorM3I.dotProduct(v, v);
      final int q = PVectorM3I.magnitudeSquared(v);
      final int r = PVectorM3I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorM3I<T> v = this.newVectorM3I(0, 0, 0);
    final int m = PVectorM3I.magnitude(v);
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
      final PVectorM3I<T> p = this.newVectorM3I(1, 0, 0);
      final PVectorM3I<T> q = this.newVectorM3I(0, 1, 0);
      final PVectorM3I<T> r = this.newVectorM3I();
      final PVectorM3I<T> u = PVectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3I.magnitude(u) == 0);
    }

    {
      final PVectorM3I<T> p = this.newVectorM3I(-1, 0, 0);
      final PVectorM3I<T> q = this.newVectorM3I(0, 1, 0);
      final PVectorM3I<T> r = this.newVectorM3I();
      final PVectorM3I<T> u = PVectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final PVectorM3I<T> out = this.newVectorM3I();
    final PVectorM3I<T> v0 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final PVectorM3I<T> ov0 = PVectorM3I.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(out.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final PVectorM3I<T> ov1 = PVectorM3I.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(ov1.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
    Assert.assertTrue(v0.getZI() == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM3IContract.randomPositiveNumber();
      final int y = PVectorM3IContract.randomPositiveNumber();
      final int z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();

      PVectorM3I.scale(v, 1, vr);

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

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = PVectorM3IContract.randomPositiveNumber();
      final int y = PVectorM3IContract.randomPositiveNumber();
      final int z = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v = this.newVectorM3I(x, y, z);

      final PVectorM3I<T> vr = this.newVectorM3I();

      PVectorM3I.scale(v, 0, vr);

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

  @Override @Test public void testString()
  {
    final PVectorM3I<T> v = this.newVectorM3I(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[PVectorM3I 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = PVectorM3IContract.randomPositiveNumber();
      final int y0 = PVectorM3IContract.randomPositiveNumber();
      final int z0 = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v0 = this.newVectorM3I(x0, y0, z0);

      final int x1 = PVectorM3IContract.randomPositiveNumber();
      final int y1 = PVectorM3IContract.randomPositiveNumber();
      final int z1 = PVectorM3IContract.randomPositiveNumber();
      final PVectorM3I<T> v1 = this.newVectorM3I(x1, y1, z1);

      final PVectorM3I<T> vr0 = this.newVectorM3I();
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

  @Override @Test public void testSubtractMutation()
  {
    final PVectorM3I<T> out = this.newVectorM3I();
    final PVectorM3I<T> v0 = this.newVectorM3I(1, 1, 1);
    final PVectorM3I<T> v1 = this.newVectorM3I(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final PVectorM3I<T> ov0 = PVectorM3I.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final PVectorM3I<T> ov1 = PVectorM3I.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
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

  protected abstract <T> PVectorM3I<T> newVectorM3I();
}
