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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;

public class VectorM3ITTest extends VectorM3TContract
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

  @Override @Test public <A> void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      VectorM3IT.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXI()), vr.getXI());
      Assert.assertEquals(Math.abs(v.getYI()), vr.getYI());
      Assert.assertEquals(Math.abs(v.getZI()), vr.getZI());
    }
  }

  @Override @Test public <A> void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      VectorM3IT.absoluteInPlace(v);

      Assert.assertEquals(v.getXI(), Math.abs(x));
      Assert.assertEquals(v.getYI(), Math.abs(y));
      Assert.assertEquals(v.getZI(), Math.abs(z));
    }
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v0 = new VectorM3IT<A>(x0, y0, z0);

      final int x1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v1 = new VectorM3IT<A>(x1, y1, z1);

      final VectorM3IT<A> vr0 = new VectorM3IT<A>();
      VectorM3IT.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + v1.getZI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3IT.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x + v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y + v1.getYI()));
        Assert.assertTrue(v0.getZI() == (orig_z + v1.getZI()));
      }
    }
  }

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM3IT<A> out = new VectorM3IT<A>();
    final VectorM3IT<A> v0 = new VectorM3IT<A>(1, 1, 1);
    final VectorM3IT<A> v1 = new VectorM3IT<A>(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final VectorM3IT<A> ov0 = VectorM3IT.add(v0, v1, out);

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

    final VectorM3IT<A> ov1 = VectorM3IT.addInPlace(v0, v1);

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

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v0 = new VectorM3IT<A>(x0, y0, z0);

      final int x1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v1 = new VectorM3IT<A>(x1, y1, z1);

      final int r = VectorM3ITTest.randomPositiveSmallNumber();

      final VectorM3IT<A> vr0 = new VectorM3IT<A>();
      VectorM3IT.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() + (v1.getXI() * r)));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() + (v1.getYI() * r)));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() + (v1.getZI() * r)));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3IT.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXI() == (orig_x + (v1.getXI() * r)));
        Assert.assertTrue(v0.getYI() == (orig_y + (v1.getYI() * r)));
        Assert.assertTrue(v0.getZI() == (orig_z + (v1.getZI() * r)));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorM3IT<A> v = new VectorM3IT<A>(3, 5, 7);

    Assert.assertTrue(v.getXI() == v.getXI());
    Assert.assertTrue(v.getYI() == v.getYI());
    Assert.assertTrue(v.getZI() == v.getZI());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM3ITTest.randomNegativeNumber();
      final int max_y = VectorM3ITTest.randomNegativeNumber();
      final int max_z = VectorM3ITTest.randomNegativeNumber();
      final VectorM3IT<A> maximum = new VectorM3IT<A>(max_x, max_y, max_z);

      final int x = VectorM3ITTest.randomNegativeNumber();
      final int y = VectorM3ITTest.randomNegativeNumber();
      final int z = VectorM3ITTest.randomNegativeNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      final VectorM3IT<A> vo =
        VectorM3IT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());

      {
        final VectorM3IT<A> vr0 =
          VectorM3IT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM3ITTest.randomPositiveNumber();
      final int min_y = VectorM3ITTest.randomPositiveNumber();
      final int min_z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> minimum = new VectorM3IT<A>(min_x, min_y, min_z);

      final int x = VectorM3ITTest.randomNegativeNumber();
      final int y = VectorM3ITTest.randomNegativeNumber();
      final int z = VectorM3ITTest.randomNegativeNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      final VectorM3IT<A> vo =
        VectorM3IT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final VectorM3IT<A> vr0 =
          VectorM3IT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM3ITTest.randomNegativeNumber();
      final int min_y = VectorM3ITTest.randomNegativeNumber();
      final int min_z = VectorM3ITTest.randomNegativeNumber();
      final VectorM3IT<A> minimum = new VectorM3IT<A>(min_x, min_y, min_z);

      final int max_x = VectorM3ITTest.randomPositiveNumber();
      final int max_y = VectorM3ITTest.randomPositiveNumber();
      final int max_z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> maximum = new VectorM3IT<A>(max_x, max_y, max_z);

      final int x = VectorM3ITTest.randomNegativeNumber();
      final int y = VectorM3ITTest.randomPositiveNumber();
      final int z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      final VectorM3IT<A> vo =
        VectorM3IT.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());

      {
        final VectorM3IT<A> vr0 =
          VectorM3IT.clampByVectorInPlace(v, minimum, maximum);
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

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM3ITTest.randomNegativeNumber();

      final int x = VectorM3ITTest.randomPositiveNumber();
      final int y = VectorM3ITTest.randomPositiveNumber();
      final int z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      VectorM3IT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);

      {
        VectorM3IT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getZI() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM3ITTest.randomPositiveNumber();

      final int x = VectorM3ITTest.randomNegativeNumber();
      final int y = VectorM3ITTest.randomNegativeNumber();
      final int z = VectorM3ITTest.randomNegativeNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      VectorM3IT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        VectorM3IT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM3ITTest.randomNegativeNumber();
      final int maximum = VectorM3ITTest.randomPositiveNumber();

      final int x = VectorM3ITTest.randomNegativeNumber();
      final int y = VectorM3ITTest.randomPositiveNumber();
      final int z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();
      VectorM3IT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);

      {
        VectorM3IT.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() <= maximum);
        Assert.assertTrue(v.getZI() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorM3IT<A> vb = new VectorM3IT<A>(5, 6, 7);
    final VectorM3IT<A> va = new VectorM3IT<A>(1, 2, 3);

    Assert.assertFalse(va.getXI() == vb.getXI());
    Assert.assertFalse(va.getYI() == vb.getYI());
    Assert.assertFalse(va.getZI() == vb.getZI());

    VectorM3IT.copy(va, vb);

    Assert.assertTrue(va.getXI() == vb.getXI());
    Assert.assertTrue(va.getYI() == vb.getYI());
    Assert.assertTrue(va.getZI() == vb.getZI());
  }

  @Override @Test public <A> void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Override @Test public <A> void testDefault000()
  {
    Assert.assertTrue(new VectorM3IT<A>().equals(new VectorM3IT<A>(0, 0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorM3IT<A> v0 = new VectorM3IT<A>(0, 1, 0);
    final VectorM3IT<A> v1 = new VectorM3IT<A>(0, 0, 0);
    Assert.assertTrue(VectorM3IT.distance(v0, v1) == 1);
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v0 = new VectorM3IT<A>(x0, y0, z0);

      final int x1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITTest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v1 = new VectorM3IT<A>(x1, y1, z1);

      Assert.assertTrue(VectorM3IT.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM3IT<A> v0 = new VectorM3IT<A>(10, 10, 10);
    final VectorM3IT<A> v1 = new VectorM3IT<A>(10, 10, 10);

    {
      final int p = VectorM3IT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3IT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXI() == 10);
      Assert.assertTrue(v1.getYI() == 10);
      Assert.assertTrue(v1.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final int z = (int) (Math.random() * max);
      final VectorM3IT<A> q = new VectorM3IT<A>(x, y, z);

      final double ms = VectorM3IT.magnitudeSquared(q);
      final double dp = VectorM3IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (Math.random() * max);
      final int y = (int) (Math.random() * max);
      final int z = (int) (Math.random() * max);
      final VectorM3IT<A> q = new VectorM3IT<A>(x, y, z);
      final double dp = VectorM3IT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3IT<A> v0 = new VectorM3IT<A>(10, 10, 10);

    {
      final int p = VectorM3IT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3IT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXI() == 10);
      Assert.assertTrue(v0.getYI() == 10);
      Assert.assertTrue(v0.getZI() == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      final VectorM3IT<A> m1 = new VectorM3IT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final int x = (int) (Math.random() * 1000);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>(x, y, z);
      final VectorM3IT<A> m1 = new VectorM3IT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM3IT<A> m0 = new VectorM3IT<A>();
    final VectorM3IT<A> m1 = new VectorM3IT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      final VectorM3IT<A> m1 = new VectorM3IT<A>();
      m1.setXI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      final VectorM3IT<A> m1 = new VectorM3IT<A>();
      m1.setYI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3IT<A> m0 = new VectorM3IT<A>();
      final VectorM3IT<A> m1 = new VectorM3IT<A>();
      m1.setZI(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM3IT<A> v0 = new VectorM3IT<A>(1, 2, 3);
    final VectorM3IT<A> v1 = new VectorM3IT<A>(v0);

    Assert.assertTrue(v0.getXI() == v1.getXI());
    Assert.assertTrue(v0.getYI() == v1.getYI());
    Assert.assertTrue(v0.getZI() == v1.getZI());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3ITTest.randomPositiveNumber();
      final int y0 = VectorM3ITTest.randomPositiveNumber();
      final int z0 = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v0 = new VectorM3IT<A>(x0, y0, z0);

      final int x1 = VectorM3ITTest.randomPositiveNumber();
      final int y1 = VectorM3ITTest.randomPositiveNumber();
      final int z1 = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v1 = new VectorM3IT<A>(x1, y1, z1);

      final VectorM3IT<A> vr0 = new VectorM3IT<A>();
      final VectorM3IT<A> vr1 = new VectorM3IT<A>();
      VectorM3IT.interpolateLinear(v0, v1, 0, vr0);
      VectorM3IT.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXI() == vr0.getXI());
      Assert.assertTrue(v0.getYI() == vr0.getYI());
      Assert.assertTrue(v0.getZI() == vr0.getZI());

      Assert.assertTrue(v1.getXI() == vr1.getXI());
      Assert.assertTrue(v1.getYI() == vr1.getYI());
      Assert.assertTrue(v1.getZI() == vr1.getZI());
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3ITTest.randomPositiveSmallNumber();
      final int y = VectorM3ITTest.randomPositiveSmallNumber();
      final int z = VectorM3ITTest.randomPositiveSmallNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final int m = VectorM3IT.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final VectorM3IT<A> v = new VectorM3IT<A>(1, 0, 0);
    final int m = VectorM3IT.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM3IT<A> v = new VectorM3IT<A>(8, 0, 0);

    {
      final int p = VectorM3IT.dotProduct(v, v);
      final int q = VectorM3IT.magnitudeSquared(v);
      final int r = VectorM3IT.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final VectorM3IT<A> v = new VectorM3IT<A>(0, 0, 0);
    final int m = VectorM3IT.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM3IT<A> p = new VectorM3IT<A>(1, 0, 0);
      final VectorM3IT<A> q = new VectorM3IT<A>(0, 1, 0);
      final VectorM3IT<A> r = new VectorM3IT<A>();
      final VectorM3IT<A> u = VectorM3IT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3IT.magnitude(u) == 0);
    }

    {
      final VectorM3IT<A> p = new VectorM3IT<A>(-1, 0, 0);
      final VectorM3IT<A> q = new VectorM3IT<A>(0, 1, 0);
      final VectorM3IT<A> r = new VectorM3IT<A>();
      final VectorM3IT<A> u = VectorM3IT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3IT.magnitude(u) == 0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM3IT<A> out = new VectorM3IT<A>();
    final VectorM3IT<A> v0 = new VectorM3IT<A>(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final VectorM3IT<A> ov0 = VectorM3IT.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXI() == 2);
    Assert.assertTrue(out.getYI() == 2);
    Assert.assertTrue(out.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);

    final VectorM3IT<A> ov1 = VectorM3IT.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXI() == 2);
    Assert.assertTrue(ov1.getYI() == 2);
    Assert.assertTrue(ov1.getZI() == 2);
    Assert.assertTrue(v0.getXI() == 2);
    Assert.assertTrue(v0.getYI() == 2);
    Assert.assertTrue(v0.getZI() == 2);
  }

  @Override @Test public <A> void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3ITTest.randomPositiveNumber();
      final int y = VectorM3ITTest.randomPositiveNumber();
      final int z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();

      VectorM3IT.scale(v, 1, vr);

      Assert.assertTrue(v.getXI() == vr.getXI());
      Assert.assertTrue(v.getYI() == vr.getYI());
      Assert.assertTrue(v.getZI() == vr.getZI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();
        final int orig_z = v.getZI();

        VectorM3IT.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXI() == orig_x);
        Assert.assertTrue(v.getYI() == orig_y);
        Assert.assertTrue(v.getZI() == orig_z);
      }
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM3ITTest.randomPositiveNumber();
      final int y = VectorM3ITTest.randomPositiveNumber();
      final int z = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v = new VectorM3IT<A>(x, y, z);

      final VectorM3IT<A> vr = new VectorM3IT<A>();

      VectorM3IT.scale(v, 0, vr);

      Assert.assertTrue(vr.getXI() == 0);
      Assert.assertTrue(vr.getYI() == 0);
      Assert.assertTrue(vr.getZI() == 0);

      {
        VectorM3IT.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXI() == 0);
        Assert.assertTrue(v.getYI() == 0);
        Assert.assertTrue(v.getZI() == 0);
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM3IT<A> v = new VectorM3IT<A>(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorM3IT 1 2 3]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM3ITTest.randomPositiveNumber();
      final int y0 = VectorM3ITTest.randomPositiveNumber();
      final int z0 = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v0 = new VectorM3IT<A>(x0, y0, z0);

      final int x1 = VectorM3ITTest.randomPositiveNumber();
      final int y1 = VectorM3ITTest.randomPositiveNumber();
      final int z1 = VectorM3ITTest.randomPositiveNumber();
      final VectorM3IT<A> v1 = new VectorM3IT<A>(x1, y1, z1);

      final VectorM3IT<A> vr0 = new VectorM3IT<A>();
      VectorM3IT.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXI() == (v0.getXI() - v1.getXI()));
      Assert.assertTrue(vr0.getYI() == (v0.getYI() - v1.getYI()));
      Assert.assertTrue(vr0.getZI() == (v0.getZI() - v1.getZI()));

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        VectorM3IT.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXI() == (orig_x - v1.getXI()));
        Assert.assertTrue(v0.getYI() == (orig_y - v1.getYI()));
        Assert.assertTrue(v0.getZI() == (orig_z - v1.getZI()));
      }
    }
  }

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM3IT<A> out = new VectorM3IT<A>();
    final VectorM3IT<A> v0 = new VectorM3IT<A>(1, 1, 1);
    final VectorM3IT<A> v1 = new VectorM3IT<A>(1, 1, 1);

    Assert.assertTrue(out.getXI() == 0);
    Assert.assertTrue(out.getYI() == 0);
    Assert.assertTrue(out.getZI() == 0);
    Assert.assertTrue(v0.getXI() == 1);
    Assert.assertTrue(v0.getYI() == 1);
    Assert.assertTrue(v0.getZI() == 1);
    Assert.assertTrue(v1.getXI() == 1);
    Assert.assertTrue(v1.getYI() == 1);
    Assert.assertTrue(v1.getZI() == 1);

    final VectorM3IT<A> ov0 = VectorM3IT.subtract(v0, v1, out);

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

    final VectorM3IT<A> ov1 = VectorM3IT.subtractInPlace(v0, v1);

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
}
