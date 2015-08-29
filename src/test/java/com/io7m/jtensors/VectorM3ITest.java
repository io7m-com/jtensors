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

public class VectorM3ITest
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

  @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomNegativeNumber();
      final int z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      VectorM3I.absolute(v, vr);

      Assert.assertTrue(Math.abs(v.x) == vr.x);
      Assert.assertTrue(Math.abs(v.y) == vr.y);
      Assert.assertTrue(Math.abs(v.z) == vr.z);

      {
        final int orig_x = v.x;
        final int orig_y = v.y;
        final int orig_z = v.z;

        VectorM3I.absoluteInPlace(v);

        Assert.assertTrue(Math.abs(orig_x) == v.x);
        Assert.assertTrue(Math.abs(orig_y) == v.y);
        Assert.assertTrue(Math.abs(orig_z) == v.z);
      }
    }
  }

  @Test public void testAbsoluteMutation()
  {
    final VectorM3I out = new VectorM3I();
    final VectorM3I v = new VectorM3I(-1, -1, -1);

    Assert.assertTrue(v.x == -1);
    Assert.assertTrue(v.y == -1);
    Assert.assertTrue(v.z == -1);

    final int vx = v.x;
    final int vy = v.y;
    final int vz = v.z;

    final VectorM3I ov = VectorM3I.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vz == v.z);
    Assert.assertTrue(vx == -1);
    Assert.assertTrue(vy == -1);
    Assert.assertTrue(vz == -1);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1);
    Assert.assertTrue(out.y == 1);
    Assert.assertTrue(out.z == 1);
  }

  @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomNegativeNumber();
      final int z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      VectorM3I.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0);
      Assert.assertTrue(vr.y >= 0);
      Assert.assertTrue(vr.z >= 0);

      {
        VectorM3I.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0);
        Assert.assertTrue(v.y >= 0);
        Assert.assertTrue(v.z >= 0);
      }
    }
  }

  @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorM3ITest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v0 = new VectorM3I(x0, y0, z0);

      final int x1 = VectorM3ITest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v1 = new VectorM3I(x1, y1, z1);

      final VectorM3I vr0 = new VectorM3I();
      VectorM3I.add(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x + v1.x));
      Assert.assertTrue(vr0.y == (v0.y + v1.y));
      Assert.assertTrue(vr0.z == (v0.z + v1.z));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        VectorM3I.addInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x + v1.x));
        Assert.assertTrue(v0.y == (orig_y + v1.y));
        Assert.assertTrue(v0.z == (orig_z + v1.z));
      }
    }
  }

  @Test public void testAddMutation()
  {
    final VectorM3I out = new VectorM3I();
    final VectorM3I v0 = new VectorM3I(1, 1, 1);
    final VectorM3I v1 = new VectorM3I(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);

    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);

    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3I ov0 = VectorM3I.add(v0, v1, out);

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

    final VectorM3I ov1 = VectorM3I.addInPlace(v0, v1);

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

  @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorM3ITest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v0 = new VectorM3I(x0, y0, z0);

      final int x1 = VectorM3ITest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v1 = new VectorM3I(x1, y1, z1);

      final int r = VectorM3ITest.randomPositiveSmallNumber();

      final VectorM3I vr0 = new VectorM3I();
      VectorM3I.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.x == (v0.x + (v1.x * r)));
      Assert.assertTrue(vr0.y == (v0.y + (v1.y * r)));
      Assert.assertTrue(vr0.z == (v0.z + (v1.z * r)));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        VectorM3I.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.x == (orig_x + (v1.x * r)));
        Assert.assertTrue(v0.y == (orig_y + (v1.y * r)));
        Assert.assertTrue(v0.z == (orig_z + (v1.z * r)));
      }
    }
  }

  @Test public void testCheckInterface()
  {
    final VectorM3I v = new VectorM3I(3, 5, 7);

    Assert.assertTrue(v.x == v.getXI());
    Assert.assertTrue(v.y == v.getYI());
    Assert.assertTrue(v.z == v.getZI());
  }

  @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int max_x = VectorM3ITest.randomNegativeNumber();
      final int max_y = VectorM3ITest.randomNegativeNumber();
      final int max_z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I maximum = new VectorM3I(max_x, max_y, max_z);

      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomNegativeNumber();
      final int z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      final VectorM3I vo = VectorM3I.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      {
        final VectorM3I vr0 =
          VectorM3I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
      }
    }
  }

  @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int min_x = VectorM3ITest.randomPositiveNumber();
      final int min_y = VectorM3ITest.randomPositiveNumber();
      final int min_z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I minimum = new VectorM3I(min_x, min_y, min_z);

      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomNegativeNumber();
      final int z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      final VectorM3I vo = VectorM3I.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3I vr0 =
          VectorM3I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int min_x = VectorM3ITest.randomNegativeNumber();
      final int min_y = VectorM3ITest.randomNegativeNumber();
      final int min_z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I minimum = new VectorM3I(min_x, min_y, min_z);

      final int max_x = VectorM3ITest.randomPositiveNumber();
      final int max_y = VectorM3ITest.randomPositiveNumber();
      final int max_z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I maximum = new VectorM3I(max_x, max_y, max_z);

      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomPositiveNumber();
      final int z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      final VectorM3I vo = VectorM3I.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3I vr0 =
          VectorM3I.clampByVectorInPlace(v, minimum, maximum);
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

  @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int maximum = VectorM3ITest.randomNegativeNumber();

      final int x = VectorM3ITest.randomPositiveNumber();
      final int y = VectorM3ITest.randomPositiveNumber();
      final int z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      VectorM3I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);

      {
        VectorM3I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
      }
    }
  }

  @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int minimum = VectorM3ITest.randomPositiveNumber();

      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomNegativeNumber();
      final int z = VectorM3ITest.randomNegativeNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      VectorM3I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int minimum = VectorM3ITest.randomNegativeNumber();
      final int maximum = VectorM3ITest.randomPositiveNumber();

      final int x = VectorM3ITest.randomNegativeNumber();
      final int y = VectorM3ITest.randomPositiveNumber();
      final int z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();
      VectorM3I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @Test public void testCopy()
  {
    final VectorM3I vb = new VectorM3I(5, 6, 7);
    final VectorM3I va = new VectorM3I(1, 2, 3);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);

    VectorM3I.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
  }

  @Test public void testDistance()
  {
    final VectorM3I v0 = new VectorM3I(0, 1, 0);
    final VectorM3I v1 = new VectorM3I(0, 0, 0);
    Assert.assertTrue(VectorM3I.distance(v0, v1) == 1);
  }

  @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorM3ITest.randomPositiveSmallNumber();
      final int y0 = VectorM3ITest.randomPositiveSmallNumber();
      final int z0 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v0 = new VectorM3I(x0, y0, z0);

      final int x1 = VectorM3ITest.randomPositiveSmallNumber();
      final int y1 = VectorM3ITest.randomPositiveSmallNumber();
      final int z1 = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v1 = new VectorM3I(x1, y1, z1);

      Assert.assertTrue(VectorM3I.distance(v0, v1) >= 0);
    }
  }

  @Test public void testDotProduct()
  {
    final VectorM3I v0 = new VectorM3I(10, 10, 10);
    final VectorM3I v1 = new VectorM3I(10, 10, 10);

    {
      final int p = VectorM3I.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3I.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10);
      Assert.assertTrue(v1.y == 10);
      Assert.assertTrue(v1.z == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Test public void testDotProductOrthonormal()
  {
    final VectorM3I v = new VectorM3I(1, 0, 0);
    Assert.assertTrue(VectorM3I.dotProduct(v, v) == 1);
  }

  @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3I v0 = new VectorM3I(10, 10, 10);

    {
      final int p = VectorM3I.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }

    {
      final int p = VectorM3I.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10);
      Assert.assertTrue(v0.y == 10);
      Assert.assertTrue(v0.z == 10);
      Assert.assertTrue(p == 300);
    }
  }

  @Test public void testInitializeReadable()
  {
    final VectorM3I v0 = new VectorM3I(1, 2, 3);
    final VectorM3I v1 = new VectorM3I(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorM3ITest.randomPositiveNumber();
      final int y0 = VectorM3ITest.randomPositiveNumber();
      final int z0 = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v0 = new VectorM3I(x0, y0, z0);

      final int x1 = VectorM3ITest.randomPositiveNumber();
      final int y1 = VectorM3ITest.randomPositiveNumber();
      final int z1 = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v1 = new VectorM3I(x1, y1, z1);

      final VectorM3I vr0 = new VectorM3I();
      final VectorM3I vr1 = new VectorM3I();
      VectorM3I.interpolateLinear(v0, v1, 0, vr0);
      VectorM3I.interpolateLinear(v0, v1, 1, vr1);

      Assert.assertTrue(v0.x == vr0.x);
      Assert.assertTrue(v0.y == vr0.y);
      Assert.assertTrue(v0.z == vr0.z);

      Assert.assertTrue(v1.x == vr1.x);
      Assert.assertTrue(v1.y == vr1.y);
      Assert.assertTrue(v1.z == vr1.z);
    }
  }

  @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final int x = VectorM3ITest.randomPositiveSmallNumber();
      final int y = VectorM3ITest.randomPositiveSmallNumber();
      final int z = VectorM3ITest.randomPositiveSmallNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final int m = VectorM3I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Test public void testMagnitudeOne()
  {
    final VectorM3I v = new VectorM3I(1, 0, 0);
    final int m = VectorM3I.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Test public void testMagnitudeSimple()
  {
    final VectorM3I v = new VectorM3I(8, 0, 0);

    {
      final int p = VectorM3I.dotProduct(v, v);
      final int q = VectorM3I.magnitudeSquared(v);
      final int r = VectorM3I.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Test public void testMagnitudeZero()
  {
    final VectorM3I v = new VectorM3I(0, 0, 0);
    final int m = VectorM3I.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM3I p = new VectorM3I(1, 0, 0);
      final VectorM3I q = new VectorM3I(0, 1, 0);
      final VectorM3I r = new VectorM3I();
      final VectorM3I u = VectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3I.magnitude(u) == 0);
    }

    {
      final VectorM3I p = new VectorM3I(-1, 0, 0);
      final VectorM3I q = new VectorM3I(0, 1, 0);
      final VectorM3I r = new VectorM3I();
      final VectorM3I u = VectorM3I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3I.magnitude(u) == 0);
    }
  }

  @Test public void testScaleMutation()
  {
    final VectorM3I out = new VectorM3I();
    final VectorM3I v0 = new VectorM3I(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);

    final VectorM3I ov0 = VectorM3I.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2);
    Assert.assertTrue(out.y == 2);
    Assert.assertTrue(out.z == 2);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);

    final VectorM3I ov1 = VectorM3I.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2);
    Assert.assertTrue(ov1.y == 2);
    Assert.assertTrue(ov1.z == 2);
    Assert.assertTrue(v0.x == 2);
    Assert.assertTrue(v0.y == 2);
    Assert.assertTrue(v0.z == 2);
  }

  @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
      final int x = VectorM3ITest.randomPositiveNumber();
      final int y = VectorM3ITest.randomPositiveNumber();
      final int z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();

      VectorM3I.scale(v, 1, vr);

      Assert.assertTrue(v.x == vr.x);
      Assert.assertTrue(v.y == vr.y);
      Assert.assertTrue(v.z == vr.z);

      {
        final int orig_x = v.x;
        final int orig_y = v.y;
        final int orig_z = v.z;

        VectorM3I.scaleInPlace(v, 1);

        Assert.assertTrue(v.x == orig_x);
        Assert.assertTrue(v.y == orig_y);
        Assert.assertTrue(v.z == orig_z);
      }
    }
  }

  @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final int x = VectorM3ITest.randomPositiveNumber();
      final int y = VectorM3ITest.randomPositiveNumber();
      final int z = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v = new VectorM3I(x, y, z);

      final VectorM3I vr = new VectorM3I();

      VectorM3I.scale(v, 0, vr);

      Assert.assertTrue(vr.x == 0);
      Assert.assertTrue(vr.y == 0);
      Assert.assertTrue(vr.z == 0);

      {
        VectorM3I.scaleInPlace(v, 0);

        Assert.assertTrue(v.x == 0);
        Assert.assertTrue(v.y == 0);
        Assert.assertTrue(v.z == 0);
      }
    }
  }

  @Test public void testString()
  {
    final VectorM3I v = new VectorM3I(1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorM3I 1 2 3]"));
  }

  @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorM3ITest.randomPositiveNumber();
      final int y0 = VectorM3ITest.randomPositiveNumber();
      final int z0 = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v0 = new VectorM3I(x0, y0, z0);

      final int x1 = VectorM3ITest.randomPositiveNumber();
      final int y1 = VectorM3ITest.randomPositiveNumber();
      final int z1 = VectorM3ITest.randomPositiveNumber();
      final VectorM3I v1 = new VectorM3I(x1, y1, z1);

      final VectorM3I vr0 = new VectorM3I();
      VectorM3I.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.x == (v0.x - v1.x));
      Assert.assertTrue(vr0.y == (v0.y - v1.y));
      Assert.assertTrue(vr0.z == (v0.z - v1.z));

      {
        final int orig_x = v0.x;
        final int orig_y = v0.y;
        final int orig_z = v0.z;
        VectorM3I.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.x == (orig_x - v1.x));
        Assert.assertTrue(v0.y == (orig_y - v1.y));
        Assert.assertTrue(v0.z == (orig_z - v1.z));
      }
    }
  }

  @Test public void testSubtractMutation()
  {
    final VectorM3I out = new VectorM3I();
    final VectorM3I v0 = new VectorM3I(1, 1, 1);
    final VectorM3I v1 = new VectorM3I(1, 1, 1);

    Assert.assertTrue(out.x == 0);
    Assert.assertTrue(out.y == 0);
    Assert.assertTrue(out.z == 0);
    Assert.assertTrue(v0.x == 1);
    Assert.assertTrue(v0.y == 1);
    Assert.assertTrue(v0.z == 1);
    Assert.assertTrue(v1.x == 1);
    Assert.assertTrue(v1.y == 1);
    Assert.assertTrue(v1.z == 1);

    final VectorM3I ov0 = VectorM3I.subtract(v0, v1, out);

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

    final VectorM3I ov1 = VectorM3I.subtractInPlace(v0, v1);

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
