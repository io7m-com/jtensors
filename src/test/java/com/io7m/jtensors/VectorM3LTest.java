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

public class VectorM3LTest extends VectorM3Contract
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
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      VectorM3L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.x), vr.x);
      Assert.assertEquals(Math.abs(v.y), vr.y);
      Assert.assertEquals(Math.abs(v.z), vr.z);
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final VectorM3L v = new VectorM3L(x, y, z);

      VectorM3L.absoluteInPlace(v);

      Assert.assertEquals(v.x, Math.abs(x));
      Assert.assertEquals(v.y, Math.abs(y));
      Assert.assertEquals(v.z, Math.abs(z));
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM3LTest.randomPositiveSmallNumber();
      final long y0 = VectorM3LTest.randomPositiveSmallNumber();
      final long z0 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v0 = new VectorM3L(x0, y0, z0);

      final long x1 = VectorM3LTest.randomPositiveSmallNumber();
      final long y1 = VectorM3LTest.randomPositiveSmallNumber();
      final long z1 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v1 = new VectorM3L(x1, y1, z1);

      final VectorM3L vr0 = new VectorM3L();
      VectorM3L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
      Assert.assertTrue(vr0.z == (v0.z + v1.z));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        VectorM3L.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
        Assert.assertTrue(v0.z == (orig_z + v1.z));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM3L out = new VectorM3L();
    final VectorM3L v0 = new VectorM3L(1, 1, 1);
    final VectorM3L v1 = new VectorM3L(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3L ov0 = VectorM3L.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3L ov1 = VectorM3L.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(ov1.z == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
    Assert.assertTrue(v0.z == 2);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM3LTest.randomPositiveSmallNumber();
      final long y0 = VectorM3LTest.randomPositiveSmallNumber();
      final long z0 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v0 = new VectorM3L(x0, y0, z0);

      final long x1 = VectorM3LTest.randomPositiveSmallNumber();
      final long y1 = VectorM3LTest.randomPositiveSmallNumber();
      final long z1 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v1 = new VectorM3L(x1, y1, z1);

      final long r = VectorM3LTest.randomPositiveSmallNumber();

      final VectorM3L vr0 = new VectorM3L();
      VectorM3L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
      Assert.assertTrue(vr0.z == (v0.z + (v1.z * r)));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        VectorM3L.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.x == (orig_x + (v1.x * r)));
        Assert.assertTrue(v0.y == (orig_y + (v1.y * r)));
        Assert.assertTrue(v0.z == (orig_z + (v1.z * r)));
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
    final VectorM3L v = new VectorM3L(3, 5, 7);

    Assert.assertTrue(v.x == v.getXL());
    Assert.assertTrue(v.y == v.getYL());
    Assert.assertTrue(v.z == v.getZL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorM3LTest.randomNegativeNumber();
      final long max_y = VectorM3LTest.randomNegativeNumber();
      final long max_z = VectorM3LTest.randomNegativeNumber();
      final VectorM3L maximum = new VectorM3L(max_x, max_y, max_z);

      final long x = VectorM3LTest.randomNegativeNumber();
      final long y = VectorM3LTest.randomNegativeNumber();
      final long z = VectorM3LTest.randomNegativeNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      final VectorM3L vo = VectorM3L.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      {
        final VectorM3L vr0 =
          VectorM3L.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM3LTest.randomPositiveNumber();
      final long min_y = VectorM3LTest.randomPositiveNumber();
      final long min_z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L minimum = new VectorM3L(min_x, min_y, min_z);

      final long x = VectorM3LTest.randomNegativeNumber();
      final long y = VectorM3LTest.randomNegativeNumber();
      final long z = VectorM3LTest.randomNegativeNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      final VectorM3L vo = VectorM3L.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3L vr0 =
          VectorM3L.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM3LTest.randomNegativeNumber();
      final long min_y = VectorM3LTest.randomNegativeNumber();
      final long min_z = VectorM3LTest.randomNegativeNumber();
      final VectorM3L minimum = new VectorM3L(min_x, min_y, min_z);

      final long max_x = VectorM3LTest.randomPositiveNumber();
      final long max_y = VectorM3LTest.randomPositiveNumber();
      final long max_z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L maximum = new VectorM3L(max_x, max_y, max_z);

      final long x = VectorM3LTest.randomNegativeNumber();
      final long y = VectorM3LTest.randomPositiveNumber();
      final long z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      final VectorM3L vo = VectorM3L.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3L vr0 =
          VectorM3L.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorM3LTest.randomNegativeNumber();

      final long x = VectorM3LTest.randomPositiveNumber();
      final long y = VectorM3LTest.randomPositiveNumber();
      final long z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      VectorM3L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);

      {
        VectorM3L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM3LTest.randomPositiveNumber();

      final long x = VectorM3LTest.randomNegativeNumber();
      final long y = VectorM3LTest.randomNegativeNumber();
      final long z = VectorM3LTest.randomNegativeNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      VectorM3L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM3LTest.randomNegativeNumber();
      final long maximum = VectorM3LTest.randomPositiveNumber();

      final long x = VectorM3LTest.randomNegativeNumber();
      final long y = VectorM3LTest.randomPositiveNumber();
      final long z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();
      VectorM3L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM3L vb = new VectorM3L(5, 6, 7);
    final VectorM3L va = new VectorM3L(1, 2, 3);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);

    VectorM3L.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    // Not applicable for integer vectors
  }

  @Override @Test public void testDefault000()
  {
    Assert.assertTrue(new VectorM3L().equals(new VectorM3L(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM3L v0 = new VectorM3L(0, 1, 0);
    final VectorM3L v1 = new VectorM3L(0, 0, 0);
    Assert.assertTrue(VectorM3L.distance(v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM3LTest.randomPositiveSmallNumber();
      final long y0 = VectorM3LTest.randomPositiveSmallNumber();
      final long z0 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v0 = new VectorM3L(x0, y0, z0);

      final long x1 = VectorM3LTest.randomPositiveSmallNumber();
      final long y1 = VectorM3LTest.randomPositiveSmallNumber();
      final long z1 = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v1 = new VectorM3L(x1, y1, z1);

      Assert.assertTrue(VectorM3L.distance(v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM3L v0 = new VectorM3L(10, 10, 10);
    final VectorM3L v1 = new VectorM3L(10, 10, 10);

    {
      final long p = VectorM3L.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorM3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorM3L.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(p == 300);
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
      final VectorM3L q = new VectorM3L(x, y, z);

      final double ms = VectorM3L.magnitudeSquared(q);
      final double dp = VectorM3L.dotProduct(q, q);

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
      final VectorM3L q = new VectorM3L(x, y, z);
      final double dp = VectorM3L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3L v0 = new VectorM3L(10, 10, 10);

    {
      final long p = VectorM3L.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final long p = VectorM3L.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM3L m0 = new VectorM3L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3L m0 = new VectorM3L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3L m0 = new VectorM3L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM3L m0 = new VectorM3L();
      final VectorM3L m1 = new VectorM3L();
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
      final VectorM3L m0 = new VectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3L m0 = new VectorM3L(x, y, z);
      final VectorM3L m1 = new VectorM3L(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM3L m0 = new VectorM3L();
    final VectorM3L m1 = new VectorM3L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3L m0 = new VectorM3L();
      final VectorM3L m1 = new VectorM3L();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3L m0 = new VectorM3L();
      final VectorM3L m1 = new VectorM3L();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3L m0 = new VectorM3L();
      final VectorM3L m1 = new VectorM3L();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM3L v0 = new VectorM3L(1, 2, 3);
    final VectorM3L v1 = new VectorM3L(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM3LTest.randomPositiveNumber();
      final long y0 = VectorM3LTest.randomPositiveNumber();
      final long z0 = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v0 = new VectorM3L(x0, y0, z0);

      final long x1 = VectorM3LTest.randomPositiveNumber();
      final long y1 = VectorM3LTest.randomPositiveNumber();
      final long z1 = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v1 = new VectorM3L(x1, y1, z1);

      final VectorM3L vr0 = new VectorM3L();
      final VectorM3L vr1 = new VectorM3L();
      VectorM3L.interpolateLinear(v0, v1, 0, vr0);
      VectorM3L.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);
      Assert.assertTrue(v0.z == vr0.z);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
      Assert.assertTrue(v1.z == vr1.z);
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM3LTest.randomPositiveSmallNumber();
      final long y = VectorM3LTest.randomPositiveSmallNumber();
      final long z = VectorM3LTest.randomPositiveSmallNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final long m = VectorM3L.magnitude(v);
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
    final VectorM3L v = new VectorM3L(1, 0, 0);
    final long m = VectorM3L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM3L v = new VectorM3L(8, 0, 0);

    {
      final long p = VectorM3L.dotProduct(v, v);
      final long q = VectorM3L.magnitudeSquared(v);
      final long r = VectorM3L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM3L v = new VectorM3L(0, 0, 0);
    final long m = VectorM3L.magnitude(v);
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
      final VectorM3L p = new VectorM3L(1, 0, 0);
      final VectorM3L q = new VectorM3L(0, 1, 0);
      final VectorM3L r = new VectorM3L();
      final VectorM3L u = VectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3L.magnitude(u) == 0);
    }

    {
      final VectorM3L p = new VectorM3L(-1, 0, 0);
      final VectorM3L q = new VectorM3L(0, 1, 0);
      final VectorM3L r = new VectorM3L();
      final VectorM3L u = VectorM3L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM3L out = new VectorM3L();
    final VectorM3L v0 = new VectorM3L(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);

    final VectorM3L ov0 = VectorM3L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);

    final VectorM3L ov1 = VectorM3L.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(ov1.z == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
    Assert.assertTrue(v0.z == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM3LTest.randomPositiveNumber();
      final long y = VectorM3LTest.randomPositiveNumber();
      final long z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();

      VectorM3L.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
      Assert.assertTrue(v.z == vr.z);

      {
        final long orig_x = v.x;
        final long orig_y = v.y;
        final long orig_z = v.z;

        VectorM3L.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
        Assert.assertTrue(v.z == orig_z);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM3LTest.randomPositiveNumber();
      final long y = VectorM3LTest.randomPositiveNumber();
      final long z = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v = new VectorM3L(x, y, z);

      final VectorM3L vr = new VectorM3L();

      VectorM3L.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
      Assert.assertTrue(vr.z == 0);

      {
        VectorM3L.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
        Assert.assertTrue(v.z == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM3L v = new VectorM3L(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorM3L 1 2 3]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM3LTest.randomPositiveNumber();
      final long y0 = VectorM3LTest.randomPositiveNumber();
      final long z0 = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v0 = new VectorM3L(x0, y0, z0);

      final long x1 = VectorM3LTest.randomPositiveNumber();
      final long y1 = VectorM3LTest.randomPositiveNumber();
      final long z1 = VectorM3LTest.randomPositiveNumber();
      final VectorM3L v1 = new VectorM3L(x1, y1, z1);

      final VectorM3L vr0 = new VectorM3L();
      VectorM3L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
      Assert.assertTrue(vr0.z == (v0.z - v1.z));

      {
        final long orig_x = v0.x;
        final long orig_y = v0.y;
        final long orig_z = v0.z;
        VectorM3L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
        Assert.assertTrue(v0.z == (orig_z - v1.z));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM3L out = new VectorM3L();
    final VectorM3L v0 = new VectorM3L(1, 1, 1);
    final VectorM3L v1 = new VectorM3L(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3L ov0 = VectorM3L.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3L ov1 = VectorM3L.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0);
    Assert.assertTrue(ov1.y == 0);
    Assert.assertTrue(ov1.z == 0);
    Assert.assertTrue(v0.x == 0);
    Assert.assertTrue(v0.y == 0);
    Assert.assertTrue(v0.z == 0);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);
  }
}
