/*
 * Copyright © 2012 http://io7m.com
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

public class VectorM4ITest extends VectorM4Contract
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
      final int w = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      VectorM4I.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
      Assert.assertEquals(Math.abs(v.z), vr.z);
      Assert.assertEquals(Math.abs(v.w), vr.w);
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = (int) (Math.random() * Integer.MIN_VALUE);
      final int y = (int) (Math.random() * Integer.MIN_VALUE);
      final int z = (int) (Math.random() * Integer.MIN_VALUE);
      final int w = (int) (Math.random() * Integer.MIN_VALUE);
      final VectorM4I v = new VectorM4I(x, y, z, w);

      VectorM4I.absoluteInPlace(v);

      Assert.assertEquals(v.x, Math.abs(x));
      Assert.assertEquals(v.y, Math.abs(y));
      Assert.assertEquals(v.z, Math.abs(z));
      Assert.assertEquals(v.w, Math.abs(w));
    }
  }

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM4ITest.randomPositiveSmallNumber();
      final int y0 = VectorM4ITest.randomPositiveSmallNumber();
      final int z0 = VectorM4ITest.randomPositiveSmallNumber();
      final int w0 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v0 = new VectorM4I(x0, y0, z0, w0);

      final int x1 = VectorM4ITest.randomPositiveSmallNumber();
      final int y1 = VectorM4ITest.randomPositiveSmallNumber();
      final int z1 = VectorM4ITest.randomPositiveSmallNumber();
      final int w1 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v1 = new VectorM4I(x1, y1, z1, w1);

      final VectorM4I vr0 = new VectorM4I();
      VectorM4I.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
      Assert.assertTrue(vr0.z == (v0.z + v1.z));
      Assert.assertTrue(vr0.w == (v0.w + v1.w));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        final int orig_w = v0.w;
        VectorM4I.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
        Assert.assertTrue(v0.z == (orig_z + v1.z));
        Assert.assertTrue(v0.w == (orig_w + v1.w));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM4I out = new VectorM4I();
    final VectorM4I v0 = new VectorM4I(1, 1, 1, 1);
    final VectorM4I v1 = new VectorM4I(1, 1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(out.w == 1);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);

    final VectorM4I ov0 = VectorM4I.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(out.w == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);

    final VectorM4I ov1 = VectorM4I.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(ov1.z == 2);
    Assert.assertTrue(ov1.w == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
    Assert.assertTrue(v0.z == 2);
    Assert.assertTrue(v0.w == 2);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM4ITest.randomPositiveSmallNumber();
      final int y0 = VectorM4ITest.randomPositiveSmallNumber();
      final int z0 = VectorM4ITest.randomPositiveSmallNumber();
      final int w0 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v0 = new VectorM4I(x0, y0, z0, w0);

      final int x1 = VectorM4ITest.randomPositiveSmallNumber();
      final int y1 = VectorM4ITest.randomPositiveSmallNumber();
      final int z1 = VectorM4ITest.randomPositiveSmallNumber();
      final int w1 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v1 = new VectorM4I(x1, y1, z1, w1);

      final int r = VectorM4ITest.randomPositiveSmallNumber();

      final VectorM4I vr0 = new VectorM4I();
      VectorM4I.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
      Assert.assertTrue(vr0.z == (v0.z + (v1.z * r)));
      Assert.assertTrue(vr0.w == (v0.w + (v1.w * r)));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        final int orig_w = v0.w;
        VectorM4I.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.x == (orig_x + (v1.x * r)));
        Assert.assertTrue(v0.y == (orig_y + (v1.y * r)));
        Assert.assertTrue(v0.z == (orig_z + (v1.z * r)));
        Assert.assertTrue(v0.w == (orig_w + (v1.w * r)));
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
    final VectorM4I v = new VectorM4I(3, 5, 7, 11);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
    Assert.assertTrue(v.z == v.getZI());
    Assert.assertTrue(v.w == v.getWI());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = VectorM4ITest.randomNegativeNumber();
      final int max_y = VectorM4ITest.randomNegativeNumber();
      final int max_z = VectorM4ITest.randomNegativeNumber();
      final int max_w = VectorM4ITest.randomNegativeNumber();
      final VectorM4I maximum = new VectorM4I(max_x, max_y, max_z, max_w);

      final int x = VectorM4ITest.randomNegativeNumber();
      final int y = VectorM4ITest.randomNegativeNumber();
      final int z = VectorM4ITest.randomNegativeNumber();
      final int w = VectorM4ITest.randomNegativeNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      final VectorM4I vo = VectorM4I.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);

      {
        final VectorM4I vr0 =
          VectorM4I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
        Assert.assertTrue(v.w <= maximum.w);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM4ITest.randomPositiveNumber();
      final int min_y = VectorM4ITest.randomPositiveNumber();
      final int min_z = VectorM4ITest.randomPositiveNumber();
      final int min_w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I minimum = new VectorM4I(min_x, min_y, min_z, min_w);

      final int x = VectorM4ITest.randomNegativeNumber();
      final int y = VectorM4ITest.randomNegativeNumber();
      final int z = VectorM4ITest.randomNegativeNumber();
      final int w = VectorM4ITest.randomNegativeNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      final VectorM4I vo = VectorM4I.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);

      {
        final VectorM4I vr0 =
          VectorM4I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
        Assert.assertTrue(v.w >= minimum.w);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = VectorM4ITest.randomNegativeNumber();
      final int min_y = VectorM4ITest.randomNegativeNumber();
      final int min_z = VectorM4ITest.randomNegativeNumber();
      final int min_w = VectorM4ITest.randomNegativeNumber();
      final VectorM4I minimum = new VectorM4I(min_x, min_y, min_z, min_w);

      final int max_x = VectorM4ITest.randomPositiveNumber();
      final int max_y = VectorM4ITest.randomPositiveNumber();
      final int max_z = VectorM4ITest.randomPositiveNumber();
      final int max_w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I maximum = new VectorM4I(max_x, max_y, max_z, max_w);

      final int x = VectorM4ITest.randomNegativeNumber();
      final int y = VectorM4ITest.randomPositiveNumber();
      final int z = VectorM4ITest.randomPositiveNumber();
      final int w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      final VectorM4I vo = VectorM4I.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);

      {
        final VectorM4I vr0 =
          VectorM4I.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
        Assert.assertTrue(v.w <= maximum.w);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
        Assert.assertTrue(v.w >= minimum.w);
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = VectorM4ITest.randomNegativeNumber();

      final int x = VectorM4ITest.randomPositiveNumber();
      final int y = VectorM4ITest.randomPositiveNumber();
      final int z = VectorM4ITest.randomPositiveNumber();
      final int w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      VectorM4I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.w <= maximum);

      {
        VectorM4I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.w <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM4ITest.randomPositiveNumber();

      final int x = VectorM4ITest.randomNegativeNumber();
      final int y = VectorM4ITest.randomNegativeNumber();
      final int z = VectorM4ITest.randomNegativeNumber();
      final int w = VectorM4ITest.randomNegativeNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      VectorM4I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
        Assert.assertTrue(v.w >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = VectorM4ITest.randomNegativeNumber();
      final int maximum = VectorM4ITest.randomPositiveNumber();

      final int x = VectorM4ITest.randomNegativeNumber();
      final int y = VectorM4ITest.randomPositiveNumber();
      final int z = VectorM4ITest.randomPositiveNumber();
      final int w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();
      VectorM4I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w <= maximum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.z >= minimum);
        Assert.assertTrue(v.w <= maximum);
        Assert.assertTrue(v.w >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM4I vb = new VectorM4I(5, 6, 7, 8);
    final VectorM4I va = new VectorM4I(1, 2, 3, 4);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);
    Assert.assertFalse(va.w == vb.w);

    VectorM4I.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
    Assert.assertTrue(va.w == vb.w);
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(new VectorM4I().equals(new VectorM4I(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM4I v0 = new VectorM4I(0, 1, 0, 0);
    final VectorM4I v1 = new VectorM4I(0, 0, 0, 0);
    Assert.assertTrue(VectorM4I.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM4ITest.randomPositiveSmallNumber();
      final int y0 = VectorM4ITest.randomPositiveSmallNumber();
      final int z0 = VectorM4ITest.randomPositiveSmallNumber();
      final int w0 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v0 = new VectorM4I(x0, y0, z0, w0);

      final int x1 = VectorM4ITest.randomPositiveSmallNumber();
      final int y1 = VectorM4ITest.randomPositiveSmallNumber();
      final int z1 = VectorM4ITest.randomPositiveSmallNumber();
      final int w1 = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v1 = new VectorM4I(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4I.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM4I v0 = new VectorM4I(10, 10, 10, 10);
    final VectorM4I v1 = new VectorM4I(10, 10, 10, 10);

    {
      final int p = VectorM4I.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(v1.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorM4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorM4I.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(v1.w == 10);
      Assert.assertTrue(p == 400);
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
      final int w = (int) (Math.random() * max);
      final VectorM4I q = new VectorM4I(x, y, z, w);

      final double ms = VectorM4I.magnitudeSquared(q);
      final double dp = VectorM4I.dotProduct(q, q);

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
      final int w = (int) (Math.random() * max);
      final VectorM4I q = new VectorM4I(x, y, z, w);
      final double dp = VectorM4I.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4I v0 = new VectorM4I(10, 10, 10, 10);

    {
      final int p = VectorM4I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final int p = VectorM4I.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM4I m0 = new VectorM4I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4I m0 = new VectorM4I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4I m0 = new VectorM4I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4I m0 = new VectorM4I();
      final VectorM4I m1 = new VectorM4I();
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
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4I m0 = new VectorM4I(x, y, z, w);
      final VectorM4I m1 = new VectorM4I(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM4I m0 = new VectorM4I();
    final VectorM4I m1 = new VectorM4I();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4I m0 = new VectorM4I();
      final VectorM4I m1 = new VectorM4I();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4I m0 = new VectorM4I();
      final VectorM4I m1 = new VectorM4I();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4I m0 = new VectorM4I();
      final VectorM4I m1 = new VectorM4I();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4I m0 = new VectorM4I();
      final VectorM4I m1 = new VectorM4I();
      m1.w = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4I v0 = new VectorM4I(1, 2, 3, 4);
    final VectorM4I v1 = new VectorM4I(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM4ITest.randomPositiveNumber();
      final int y0 = VectorM4ITest.randomPositiveNumber();
      final int z0 = VectorM4ITest.randomPositiveNumber();
      final int w0 = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v0 = new VectorM4I(x0, y0, z0, w0);

      final int x1 = VectorM4ITest.randomPositiveNumber();
      final int y1 = VectorM4ITest.randomPositiveNumber();
      final int z1 = VectorM4ITest.randomPositiveNumber();
      final int w1 = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v1 = new VectorM4I(x1, y1, z1, w1);

      final VectorM4I vr0 = new VectorM4I();
      final VectorM4I vr1 = new VectorM4I();
      VectorM4I.interpolateLinear(v0, v1, 0, vr0);
      VectorM4I.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);
      Assert.assertTrue(v0.z == vr0.z);
      Assert.assertTrue(v0.w == vr0.w);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
      Assert.assertTrue(v1.z == vr1.z);
      Assert.assertTrue(v1.w == vr1.w);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM4ITest.randomPositiveSmallNumber();
      final int y = VectorM4ITest.randomPositiveSmallNumber();
      final int z = VectorM4ITest.randomPositiveSmallNumber();
      final int w = VectorM4ITest.randomPositiveSmallNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final int m = VectorM4I.magnitude(v);
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
    final VectorM4I v = new VectorM4I(1, 0, 0, 0);
    final int m = VectorM4I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM4I v = new VectorM4I(8, 0, 0, 0);

    {
      final int p = VectorM4I.dotProduct(v, v);
      final int q = VectorM4I.magnitudeSquared(v);
      final int r = VectorM4I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM4I v = new VectorM4I(0, 0, 0, 0);
    final int m = VectorM4I.magnitude(v);
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

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM4I p = new VectorM4I(1, 0, 0, 0);
      final VectorM4I q = new VectorM4I(0, 1, 0, 0);
      final VectorM4I r = new VectorM4I();
      final VectorM4I u = VectorM4I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4I.magnitude(u) == 0);
    }

    {
      final VectorM4I p = new VectorM4I(-1, 0, 0, 0);
      final VectorM4I q = new VectorM4I(0, 1, 0, 0);
      final VectorM4I r = new VectorM4I();
      final VectorM4I u = VectorM4I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4I.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM4I out = new VectorM4I();
    final VectorM4I v0 = new VectorM4I(1, 1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(out.w == 1);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);

    final VectorM4I ov0 = VectorM4I.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(out.w == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);

    final VectorM4I ov1 = VectorM4I.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(ov1.z == 2);
    Assert.assertTrue(ov1.w == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
    Assert.assertTrue(v0.z == 2);
    Assert.assertTrue(v0.w == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM4ITest.randomPositiveNumber();
      final int y = VectorM4ITest.randomPositiveNumber();
      final int z = VectorM4ITest.randomPositiveNumber();
      final int w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();

      VectorM4I.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
      Assert.assertTrue(v.z == vr.z);
      Assert.assertTrue(v.w == vr.w);

      {
        final int orig_x = v.x;
        final int orig_y = v.y;
        final int orig_z = v.z;
        final int orig_w = v.w;

        VectorM4I.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
        Assert.assertTrue(v.z == orig_z);
        Assert.assertTrue(v.w == orig_w);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = VectorM4ITest.randomPositiveNumber();
      final int y = VectorM4ITest.randomPositiveNumber();
      final int z = VectorM4ITest.randomPositiveNumber();
      final int w = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v = new VectorM4I(x, y, z, w);

      final VectorM4I vr = new VectorM4I();

      VectorM4I.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
      Assert.assertTrue(vr.z == 0);
      Assert.assertTrue(vr.w == 0);

      {
        VectorM4I.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
        Assert.assertTrue(v.z == 0);
        Assert.assertTrue(v.w == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM4I v = new VectorM4I(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[VectorM4I 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = VectorM4ITest.randomPositiveNumber();
      final int y0 = VectorM4ITest.randomPositiveNumber();
      final int z0 = VectorM4ITest.randomPositiveNumber();
      final int w0 = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v0 = new VectorM4I(x0, y0, z0, w0);

      final int x1 = VectorM4ITest.randomPositiveNumber();
      final int y1 = VectorM4ITest.randomPositiveNumber();
      final int z1 = VectorM4ITest.randomPositiveNumber();
      final int w1 = VectorM4ITest.randomPositiveNumber();
      final VectorM4I v1 = new VectorM4I(x1, y1, z1, w1);

      final VectorM4I vr0 = new VectorM4I();
      VectorM4I.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
      Assert.assertTrue(vr0.z == (v0.z - v1.z));
      Assert.assertTrue(vr0.w == (v0.w - v1.w));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        final int orig_w = v0.w;
        VectorM4I.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
        Assert.assertTrue(v0.z == (orig_z - v1.z));
        Assert.assertTrue(v0.w == (orig_w - v1.w));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM4I out = new VectorM4I();
    final VectorM4I v0 = new VectorM4I(1, 1, 1, 1);
    final VectorM4I v1 = new VectorM4I(1, 1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(out.w == 1);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);

    final VectorM4I ov0 = VectorM4I.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(out.w == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);

    final VectorM4I ov1 = VectorM4I.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0);
    Assert.assertTrue(ov1.y == 0);
    Assert.assertTrue(ov1.z == 0);
    Assert.assertTrue(ov1.w == 0);
    Assert.assertTrue(v0.x == 0);
    Assert.assertTrue(v0.y == 0);
    Assert.assertTrue(v0.z == 0);
    Assert.assertTrue(v0.w == 0);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
    Assert.assertTrue(v1.w == 1);
  }

}
