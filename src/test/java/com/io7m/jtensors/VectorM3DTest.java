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

import com.io7m.jaux.ApproximatelyEqualDouble;

public class VectorM3DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.y),
        vr.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.z),
        vr.z));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;

        VectorM3D.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_z),
          v.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v = new VectorM3D(-1.0, -1.0, -1.0);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);
    Assert.assertTrue(v.z == -1.0);

    final double vx = v.x;
    final double vy = v.y;
    final double vz = v.z;

    final VectorM3D ov = VectorM3D.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vz == v.z);
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);
    Assert.assertTrue(vz == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1.0);
    Assert.assertTrue(out.y == 1.0);
    Assert.assertTrue(out.z == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);
      Assert.assertTrue(vr.z >= 0.0);

      {
        VectorM3D.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
        Assert.assertTrue(v.z >= 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final VectorM3D vr0 = new VectorM3D();
      VectorM3D.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z + v1.z));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z + v1.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0, 1.0, 1.0);
    final VectorM3D v1 = new VectorM3D(1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3D ov0 = VectorM3D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3D ov1 = VectorM3D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final double r = Math.random() * Double.MAX_VALUE;

      final VectorM3D vr0 = new VectorM3D();
      VectorM3D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z + (v1.z * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z + (v1.z * r)));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final double x0 = 0.0;
    final double x1 = 0.0;
    final double y0 = 0.0;
    final double y1 = 0.0;
    final double z0 = 0.0;
    final double z1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));

    final VectorM3D v0 = new VectorM3D(x0, y0, z0);
    final VectorM3D v1 = new VectorM3D(x1, y1, z1);
    Assert.assertTrue(VectorM3D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final double x0 = 0.0;
    final double x1 = 1.0;
    final double y0 = 0.0;
    final double y1 = 1.0;
    final double z0 = 0.0;
    final double z1 = 0.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));

    final VectorM3D v0 = new VectorM3D(x0, y0, z0);
    final VectorM3D v1 = new VectorM3D(x1, y1, z1);
    Assert.assertFalse(VectorM3D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorM3D v = new VectorM3D(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final VectorM3D maximum = new VectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      final VectorM3D vo = VectorM3D.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      {
        final VectorM3D vr0 =
          VectorM3D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D minimum = new VectorM3D(min_x, min_y, min_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      final VectorM3D vo = VectorM3D.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3D vr0 =
          VectorM3D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final VectorM3D minimum = new VectorM3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D maximum = new VectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      final VectorM3D vo = VectorM3D.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3D vr0 =
          VectorM3D.clampByVectorInPlace(v, minimum, maximum);
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

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);

      {
        VectorM3D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final VectorM3D vb = new VectorM3D(5, 6, 7);
    final VectorM3D va = new VectorM3D(1, 2, 3);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);

    VectorM3D.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductPerpendicular()
  {
    final VectorM3D vy = new VectorM3D(0, 1, 0);
    final VectorM3D vx = new VectorM3D(1, 0, 0);
    final VectorM3D vz = new VectorM3D(0, 0, 1);
    final VectorM3D out = new VectorM3D();

    final VectorM3D vxy = VectorM3D.crossProduct(vx, vy, out);
    Assert.assertSame(vxy, out);
    Assert.assertTrue(VectorM3D.dotProduct(vxy, vx) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vxy, vy) == 0.0);

    final VectorM3D vxz = VectorM3D.crossProduct(vx, vz, out);
    Assert.assertSame(vxy, out);
    Assert.assertTrue(VectorM3D.dotProduct(vxz, vx) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vxz, vz) == 0.0);

    final VectorM3D vyz = VectorM3D.crossProduct(vy, vz, out);
    Assert.assertSame(vyz, out);
    Assert.assertTrue(VectorM3D.dotProduct(vyz, vy) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vyz, vz) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductSimple()
  {
    final VectorM3D v0 = new VectorM3D(0, 1, 0);
    final VectorM3D v1 = new VectorM3D(1, 0, 0);
    final VectorM3D out = new VectorM3D();
    final VectorM3D vr = VectorM3D.crossProduct(v0, v1, out);

    Assert.assertSame(vr, out);
    Assert.assertTrue(vr.x == 0.0);
    Assert.assertTrue(vr.y == 0.0);
    Assert.assertTrue(vr.z == -1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorM3D v0 = new VectorM3D(0.0, 1.0, 0.0);
    final VectorM3D v1 = new VectorM3D(0.0, 0.0, 0.0);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorM3D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      Assert.assertTrue(VectorM3D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final VectorM3D v0 = new VectorM3D(10.0, 10.0, 10.0);
    final VectorM3D v1 = new VectorM3D(10.0, 10.0, 10.0);

    {
      final double p = VectorM3D.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorM3D v = new VectorM3D(1.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorM3D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductPerpendicular()
  {
    final VectorM3D vpx = new VectorM3D(1.0f, 0.0f, 0.0f);
    final VectorM3D vmx = new VectorM3D(-1.0f, 0.0f, 0.0f);

    final VectorM3D vpy = new VectorM3D(0.0f, 1.0f, 0.0f);
    final VectorM3D vmy = new VectorM3D(0.0f, -1.0f, 0.0f);

    final VectorM3D vpz = new VectorM3D(0.0f, 0.0f, 1.0f);
    final VectorM3D vmz = new VectorM3D(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorM3D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM3D.dotProduct(vmy, vmz) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductSelfMagnitudeSquared()
  {
    final VectorM3D v0 = new VectorM3D(10.0, 10.0, 10.0);

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorM3D m0 = new VectorM3D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorM3D m0 = new VectorM3D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorM3D m0 = new VectorM3D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq0()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.x = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq1()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.y = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq2()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.z = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeEq()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase0()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.x = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase1()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.y = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase2()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    m1.z = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorM3D v0 = new VectorM3D(1.0f, 2.0f, 3.0f);
    final VectorM3D v1 = new VectorM3D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final VectorM3D vr0 = new VectorM3D();
      final VectorM3D vr1 = new VectorM3D();
      VectorM3D.interpolateLinear(v0, v1, 0.0, vr0);
      VectorM3D.interpolateLinear(v0, v1, 1.0, vr1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.x,
        vr0.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.y,
        vr0.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.z,
        vr0.z));

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.y,
        vr1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.z,
        vr1.z));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final double m = VectorM3D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3D.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMagnitudeNormalizeZero()
  {
    final VectorM3D v = new VectorM3D(0.0, 0.0, 0.0);
    final VectorM3D vr = VectorM3D.normalizeInPlace(v);
    final double m = VectorM3D.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final VectorM3D v = new VectorM3D(1.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final VectorM3D v = new VectorM3D(8.0, 0.0, 0.0);

    {
      final double p = VectorM3D.dotProduct(v, v);
      final double q = VectorM3D.magnitudeSquared(v);
      final double r = VectorM3D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final VectorM3D v = new VectorM3D(0.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorM3D v0 = new VectorM3D(8.0, 0.0, 0.0);
    final VectorM3D out = new VectorM3D();
    final VectorM3D vr = VectorM3D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testOrthonormalize()
  {
    final VectorM3D v0 = new VectorM3D(0, 1, 0);
    final VectorM3D v1 = new VectorM3D(0.5, 0.5, 0);

    VectorM3D.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM3D(0, 1, 0), v0);
    Assert.assertEquals(new VectorM3D(1, 0, 0), v1);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorM3D p = new VectorM3D(1.0, 0.0, 0.0);
      final VectorM3D q = new VectorM3D(0.0, 1.0, 0.0);
      final VectorM3D r = new VectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }

    {
      final VectorM3D p = new VectorM3D(-1.0, 0.0, 0.0);
      final VectorM3D q = new VectorM3D(0.0, 1.0, 0.0);
      final VectorM3D r = new VectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3D ov0 = VectorM3D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3D ov1 = VectorM3D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();

      VectorM3D.scale(v, 1.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.x, vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.y, vr.y));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.z, vr.z));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;

        VectorM3D.scaleInPlace(v, 1.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          orig_y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.z,
          orig_z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();

      VectorM3D.scale(v, 0.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.x, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.y, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.z, 0.0));

      {
        VectorM3D.scaleInPlace(v, 0.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.z,
          0.0));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorM3D v = new VectorM3D(0.0, 1.0, 2.0);
    Assert.assertTrue(v.toString().equals("[VectorM3D 0.0 1.0 2.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final VectorM3D vr0 = new VectorM3D();
      VectorM3D.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z - v1.z));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z - v1.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0, 1.0, 1.0);
    final VectorM3D v1 = new VectorM3D(1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3D ov0 = VectorM3D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3D ov1 = VectorM3D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0);
    Assert.assertTrue(ov1.y == 0.0);
    Assert.assertTrue(ov1.z == 0.0);
    Assert.assertTrue(v0.x == 0.0);
    Assert.assertTrue(v0.y == 0.0);
    Assert.assertTrue(v0.z == 0.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
  }
}
