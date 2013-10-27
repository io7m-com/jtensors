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

public class VectorM4LTest extends VectorM4Contract
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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final long w = (long) (Math.random() * Long.MIN_VALUE);
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      VectorM4L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
      Assert.assertEquals(Math.abs(v.z), vr.z);
      Assert.assertEquals(Math.abs(v.w), vr.w);
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final long w = (long) (Math.random() * Long.MIN_VALUE);
      final VectorM4L v = new VectorM4L(x, y, z, w);

      VectorM4L.absoluteInPlace(v);

      Assert.assertEquals(v.x, Math.abs(x));
      Assert.assertEquals(v.y, Math.abs(y));
      Assert.assertEquals(v.z, Math.abs(z));
      Assert.assertEquals(v.w, Math.abs(w));
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LTest.randomPositiveSmallNumber();
      final long y0 = VectorM4LTest.randomPositiveSmallNumber();
      final long z0 = VectorM4LTest.randomPositiveSmallNumber();
      final long w0 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v0 = new VectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LTest.randomPositiveSmallNumber();
      final long y1 = VectorM4LTest.randomPositiveSmallNumber();
      final long z1 = VectorM4LTest.randomPositiveSmallNumber();
      final long w1 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v1 = new VectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = new VectorM4L();
      VectorM4L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
      Assert.assertTrue(vr0.z == (v0.z + v1.z));
      Assert.assertTrue(vr0.w == (v0.w + v1.w));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        final long orig_w = v0.w;
        VectorM4L.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
        Assert.assertTrue(v0.z == (orig_z + v1.z));
        Assert.assertTrue(v0.w == (orig_w + v1.w));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM4L out = new VectorM4L();
    final VectorM4L v0 = new VectorM4L(1, 1, 1, 1);
    final VectorM4L v1 = new VectorM4L(1, 1, 1, 1);

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

    final VectorM4L ov0 = VectorM4L.add(v0, v1, out);

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

    final VectorM4L ov1 = VectorM4L.addInPlace(v0, v1);

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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LTest.randomPositiveSmallNumber();
      final long y0 = VectorM4LTest.randomPositiveSmallNumber();
      final long z0 = VectorM4LTest.randomPositiveSmallNumber();
      final long w0 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v0 = new VectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LTest.randomPositiveSmallNumber();
      final long y1 = VectorM4LTest.randomPositiveSmallNumber();
      final long z1 = VectorM4LTest.randomPositiveSmallNumber();
      final long w1 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v1 = new VectorM4L(x1, y1, z1, w1);

      final long r = VectorM4LTest.randomPositiveSmallNumber();

      final VectorM4L vr0 = new VectorM4L();
      VectorM4L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
      Assert.assertTrue(vr0.z == (v0.z + (v1.z * r)));
      Assert.assertTrue(vr0.w == (v0.w + (v1.w * r)));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        final long orig_w = v0.w;
        VectorM4L.addScaledInPlace(v0, v1, r);

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
    final VectorM4L v = new VectorM4L(3, 5, 7, 11);

    Assert.assertTrue(v.x == v.getXL());
    Assert.assertTrue(v.y == v.getYL());
    Assert.assertTrue(v.z == v.getZL());
    Assert.assertTrue(v.w == v.getWL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorM4LTest.randomNegativeNumber();
      final long max_y = VectorM4LTest.randomNegativeNumber();
      final long max_z = VectorM4LTest.randomNegativeNumber();
      final long max_w = VectorM4LTest.randomNegativeNumber();
      final VectorM4L maximum = new VectorM4L(max_x, max_y, max_z, max_w);

      final long x = VectorM4LTest.randomNegativeNumber();
      final long y = VectorM4LTest.randomNegativeNumber();
      final long z = VectorM4LTest.randomNegativeNumber();
      final long w = VectorM4LTest.randomNegativeNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      final VectorM4L vo = VectorM4L.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);

      {
        final VectorM4L vr0 =
          VectorM4L.clampMaximumByVectorInPlace(v, maximum);
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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM4LTest.randomPositiveNumber();
      final long min_y = VectorM4LTest.randomPositiveNumber();
      final long min_z = VectorM4LTest.randomPositiveNumber();
      final long min_w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L minimum = new VectorM4L(min_x, min_y, min_z, min_w);

      final long x = VectorM4LTest.randomNegativeNumber();
      final long y = VectorM4LTest.randomNegativeNumber();
      final long z = VectorM4LTest.randomNegativeNumber();
      final long w = VectorM4LTest.randomNegativeNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      final VectorM4L vo = VectorM4L.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);

      {
        final VectorM4L vr0 =
          VectorM4L.clampMinimumByVectorInPlace(v, minimum);
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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM4LTest.randomNegativeNumber();
      final long min_y = VectorM4LTest.randomNegativeNumber();
      final long min_z = VectorM4LTest.randomNegativeNumber();
      final long min_w = VectorM4LTest.randomNegativeNumber();
      final VectorM4L minimum = new VectorM4L(min_x, min_y, min_z, min_w);

      final long max_x = VectorM4LTest.randomPositiveNumber();
      final long max_y = VectorM4LTest.randomPositiveNumber();
      final long max_z = VectorM4LTest.randomPositiveNumber();
      final long max_w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L maximum = new VectorM4L(max_x, max_y, max_z, max_w);

      final long x = VectorM4LTest.randomNegativeNumber();
      final long y = VectorM4LTest.randomPositiveNumber();
      final long z = VectorM4LTest.randomPositiveNumber();
      final long w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      final VectorM4L vo = VectorM4L.clampByVector(v, minimum, maximum, vr);

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
        final VectorM4L vr0 =
          VectorM4L.clampByVectorInPlace(v, minimum, maximum);
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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorM4LTest.randomNegativeNumber();

      final long x = VectorM4LTest.randomPositiveNumber();
      final long y = VectorM4LTest.randomPositiveNumber();
      final long z = VectorM4LTest.randomPositiveNumber();
      final long w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      VectorM4L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.w <= maximum);

      {
        VectorM4L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.w <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM4LTest.randomPositiveNumber();

      final long x = VectorM4LTest.randomNegativeNumber();
      final long y = VectorM4LTest.randomNegativeNumber();
      final long z = VectorM4LTest.randomNegativeNumber();
      final long w = VectorM4LTest.randomNegativeNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      VectorM4L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
        Assert.assertTrue(v.w >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM4LTest.randomNegativeNumber();
      final long maximum = VectorM4LTest.randomPositiveNumber();

      final long x = VectorM4LTest.randomNegativeNumber();
      final long y = VectorM4LTest.randomPositiveNumber();
      final long z = VectorM4LTest.randomPositiveNumber();
      final long w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();
      VectorM4L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w <= maximum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4L.clampInPlace(v, minimum, maximum);

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
    final VectorM4L vb = new VectorM4L(5, 6, 7, 8);
    final VectorM4L va = new VectorM4L(1, 2, 3, 4);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);
    Assert.assertFalse(va.w == vb.w);

    VectorM4L.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
    Assert.assertTrue(va.w == vb.w);
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(new VectorM4L().equals(new VectorM4L(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM4L v0 = new VectorM4L(0, 1, 0, 0);
    final VectorM4L v1 = new VectorM4L(0, 0, 0, 0);
    Assert.assertTrue(VectorM4L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LTest.randomPositiveSmallNumber();
      final long y0 = VectorM4LTest.randomPositiveSmallNumber();
      final long z0 = VectorM4LTest.randomPositiveSmallNumber();
      final long w0 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v0 = new VectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LTest.randomPositiveSmallNumber();
      final long y1 = VectorM4LTest.randomPositiveSmallNumber();
      final long z1 = VectorM4LTest.randomPositiveSmallNumber();
      final long w1 = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v1 = new VectorM4L(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM4L v0 = new VectorM4L(10, 10, 10, 10);
    final VectorM4L v1 = new VectorM4L(10, 10, 10, 10);

    {
      final long p = VectorM4L.dotProduct(v0, v1);
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
      final long p = VectorM4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorM4L.dotProduct(v1, v1);
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

    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final long z = (long) (Math.random() * max);
      final long w = (long) (Math.random() * max);
      final VectorM4L q = new VectorM4L(x, y, z, w);

      final double ms = VectorM4L.magnitudeSquared(q);
      final double dp = VectorM4L.dotProduct(q, q);

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

    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final long z = (long) (Math.random() * max);
      final long w = (long) (Math.random() * max);
      final VectorM4L q = new VectorM4L(x, y, z, w);
      final double dp = VectorM4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4L v0 = new VectorM4L(10, 10, 10, 10);

    {
      final long p = VectorM4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v0.w == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorM4L.magnitudeSquared(v0);
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
      final VectorM4L m0 = new VectorM4L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4L m0 = new VectorM4L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4L m0 = new VectorM4L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM4L m0 = new VectorM4L();
      final VectorM4L m1 = new VectorM4L();
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
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = new VectorM4L(x, y, z, w);
      final VectorM4L m1 = new VectorM4L(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM4L m0 = new VectorM4L();
    final VectorM4L m1 = new VectorM4L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4L m0 = new VectorM4L();
      final VectorM4L m1 = new VectorM4L();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = new VectorM4L();
      final VectorM4L m1 = new VectorM4L();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = new VectorM4L();
      final VectorM4L m1 = new VectorM4L();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = new VectorM4L();
      final VectorM4L m1 = new VectorM4L();
      m1.w = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4L v0 = new VectorM4L(1, 2, 3, 4);
    final VectorM4L v1 = new VectorM4L(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LTest.randomPositiveNumber();
      final long y0 = VectorM4LTest.randomPositiveNumber();
      final long z0 = VectorM4LTest.randomPositiveNumber();
      final long w0 = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v0 = new VectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LTest.randomPositiveNumber();
      final long y1 = VectorM4LTest.randomPositiveNumber();
      final long z1 = VectorM4LTest.randomPositiveNumber();
      final long w1 = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v1 = new VectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = new VectorM4L();
      final VectorM4L vr1 = new VectorM4L();
      VectorM4L.interpolateLinear(v0, v1, 0, vr0);
      VectorM4L.interpolateLinear(v0, v1, 1, vr1);

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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LTest.randomPositiveSmallNumber();
      final long y = VectorM4LTest.randomPositiveSmallNumber();
      final long z = VectorM4LTest.randomPositiveSmallNumber();
      final long w = VectorM4LTest.randomPositiveSmallNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final long m = VectorM4L.magnitude(v);
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
    final VectorM4L v = new VectorM4L(1, 0, 0, 0);
    final long m = VectorM4L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM4L v = new VectorM4L(8, 0, 0, 0);

    {
      final long p = VectorM4L.dotProduct(v, v);
      final long q = VectorM4L.magnitudeSquared(v);
      final long r = VectorM4L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM4L v = new VectorM4L(0, 0, 0, 0);
    final long m = VectorM4L.magnitude(v);
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
      final VectorM4L p = new VectorM4L(1, 0, 0, 0);
      final VectorM4L q = new VectorM4L(0, 1, 0, 0);
      final VectorM4L r = new VectorM4L();
      final VectorM4L u = VectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4L.magnitude(u) == 0);
    }

    {
      final VectorM4L p = new VectorM4L(-1, 0, 0, 0);
      final VectorM4L q = new VectorM4L(0, 1, 0, 0);
      final VectorM4L r = new VectorM4L();
      final VectorM4L u = VectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM4L out = new VectorM4L();
    final VectorM4L v0 = new VectorM4L(1, 1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(out.w == 1);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);

    final VectorM4L ov0 = VectorM4L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(out.w == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v0.w == 1);

    final VectorM4L ov1 = VectorM4L.scaleInPlace(v0, 2);

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
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LTest.randomPositiveNumber();
      final long y = VectorM4LTest.randomPositiveNumber();
      final long z = VectorM4LTest.randomPositiveNumber();
      final long w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();

      VectorM4L.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
      Assert.assertTrue(v.z == vr.z);
      Assert.assertTrue(v.w == vr.w);

      {
        final long orig_x = v.x;
        final long orig_y = v.y;
        final long orig_z = v.z;
        final long orig_w = v.w;

        VectorM4L.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
        Assert.assertTrue(v.z == orig_z);
        Assert.assertTrue(v.w == orig_w);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LTest.randomPositiveNumber();
      final long y = VectorM4LTest.randomPositiveNumber();
      final long z = VectorM4LTest.randomPositiveNumber();
      final long w = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v = new VectorM4L(x, y, z, w);

      final VectorM4L vr = new VectorM4L();

      VectorM4L.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
      Assert.assertTrue(vr.z == 0);
      Assert.assertTrue(vr.w == 0);

      {
        VectorM4L.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
        Assert.assertTrue(v.z == 0);
        Assert.assertTrue(v.w == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM4L v = new VectorM4L(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[VectorM4L 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LTest.randomPositiveNumber();
      final long y0 = VectorM4LTest.randomPositiveNumber();
      final long z0 = VectorM4LTest.randomPositiveNumber();
      final long w0 = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v0 = new VectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LTest.randomPositiveNumber();
      final long y1 = VectorM4LTest.randomPositiveNumber();
      final long z1 = VectorM4LTest.randomPositiveNumber();
      final long w1 = VectorM4LTest.randomPositiveNumber();
      final VectorM4L v1 = new VectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = new VectorM4L();
      VectorM4L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
      Assert.assertTrue(vr0.z == (v0.z - v1.z));
      Assert.assertTrue(vr0.w == (v0.w - v1.w));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        final long orig_w = v0.w;
        VectorM4L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
        Assert.assertTrue(v0.z == (orig_z - v1.z));
        Assert.assertTrue(v0.w == (orig_w - v1.w));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM4L out = new VectorM4L();
    final VectorM4L v0 = new VectorM4L(1, 1, 1, 1);
    final VectorM4L v1 = new VectorM4L(1, 1, 1, 1);

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

    final VectorM4L ov0 = VectorM4L.subtract(v0, v1, out);

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

    final VectorM4L ov1 = VectorM4L.subtractInPlace(v0, v1);

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
