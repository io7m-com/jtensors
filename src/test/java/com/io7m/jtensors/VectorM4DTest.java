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

public class VectorM4DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.y),
        vr.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.z),
        vr.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        Math.abs(v.w),
        vr.w));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;
        final double orig_w = v.w;

        VectorM4D.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_z),
          v.z));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          Math.abs(orig_w),
          v.w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v = new VectorM4D(-1.0, -1.0, -1.0, -1.0);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);
    Assert.assertTrue(v.z == -1.0);
    Assert.assertTrue(v.w == -1.0);

    final double vx = v.x;
    final double vy = v.y;
    final double vz = v.z;
    final double vw = v.w;

    final VectorM4D ov = VectorM4D.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vz == v.z);
    Assert.assertTrue(vw == v.w);
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);
    Assert.assertTrue(vz == -1.0);
    Assert.assertTrue(vw == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1.0);
    Assert.assertTrue(out.y == 1.0);
    Assert.assertTrue(out.z == 1.0);
    Assert.assertTrue(out.w == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);
      Assert.assertTrue(vr.z >= 0.0);
      Assert.assertTrue(vr.w >= 0.0);

      {
        VectorM4D.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
        Assert.assertTrue(v.z >= 0.0);
        Assert.assertTrue(v.w >= 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final VectorM4D vr0 = new VectorM4D();
      VectorM4D.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z + v1.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.w,
        v0.w + v1.w));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z + v1.z));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.w,
          orig_w + v1.w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v0 = new VectorM4D(1.0, 1.0, 1.0, 1.0);
    final VectorM4D v1 = new VectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(out.w == 1.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);

    final VectorM4D ov0 = VectorM4D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(out.w == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);

    final VectorM4D ov1 = VectorM4D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(ov1.w == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
    Assert.assertTrue(v0.w == 2.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final double r = Math.random() * Double.MAX_VALUE;

      final VectorM4D vr0 = new VectorM4D();
      VectorM4D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z + (v1.z * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.w,
        v0.w + (v1.w * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z + (v1.z * r)));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.w,
          orig_w + (v1.w * r)));
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
    final double w0 = 0.0;
    final double w1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);
    final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);
    Assert.assertTrue(VectorM4D.approximatelyEqual(v0, v1));
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
    final double z1 = 1.0;
    final double w0 = 0.0;
    final double w1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);
    final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);
    Assert.assertFalse(VectorM4D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorM4D v = new VectorM4D(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
    Assert.assertTrue(v.w == v.getWD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final double max_w = Math.random() * Double.MIN_VALUE;
      final VectorM4D maximum = new VectorM4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      final VectorM4D vo = VectorM4D.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);

      {
        final VectorM4D vr0 =
          VectorM4D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
        Assert.assertTrue(v.w <= maximum.w);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final double min_w = Math.random() * Double.MAX_VALUE;
      final VectorM4D minimum = new VectorM4D(min_x, min_y, min_z, min_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      final VectorM4D vo = VectorM4D.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);

      {
        final VectorM4D vr0 =
          VectorM4D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
        Assert.assertTrue(v.w >= minimum.w);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final double min_w = Math.random() * Double.MIN_VALUE;
      final VectorM4D minimum = new VectorM4D(min_x, min_y, min_z, min_w);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final double max_w = Math.random() * Double.MAX_VALUE;
      final VectorM4D maximum = new VectorM4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      final VectorM4D vo = VectorM4D.clampByVector(v, minimum, maximum, vr);

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
        final VectorM4D vr0 =
          VectorM4D.clampByVectorInPlace(v, minimum, maximum);
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

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.w <= maximum);

      {
        VectorM4D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.w <= maximum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
        Assert.assertTrue(v.w >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w <= maximum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4D.clampInPlace(v, minimum, maximum);

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

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final VectorM4D vb = new VectorM4D(5, 6, 7, 8);
    final VectorM4D va = new VectorM4D(1, 2, 3, 4);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);
    Assert.assertFalse(va.w == vb.w);

    VectorM4D.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
    Assert.assertTrue(va.w == vb.w);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorM4D v0 = new VectorM4D(0.0, 1.0, 0.0, 0.0);
    final VectorM4D v1 = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorM4D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final VectorM4D v0 = new VectorM4D(10.0, 10.0, 10.0, 10.0);
    final VectorM4D v1 = new VectorM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = VectorM4D.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(v1.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4D.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(v1.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorM4D v = new VectorM4D(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorM4D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductSelfMagnitudeSquared()
  {
    final VectorM4D v0 = new VectorM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = VectorM4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4D.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorM4D m0 = new VectorM4D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorM4D m0 = new VectorM4D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorM4D m0 = new VectorM4D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq0()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.x = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq1()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.y = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq2()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.z = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq3()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.w = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeEq()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase0()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.x = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase1()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.y = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase2()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.z = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase3()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    m1.w = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorM4D v0 = new VectorM4D(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4D v1 = new VectorM4D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final VectorM4D vr0 = new VectorM4D();
      final VectorM4D vr1 = new VectorM4D();
      VectorM4D.interpolateLinear(v0, v1, 0.0, vr0);
      VectorM4D.interpolateLinear(v0, v1, 1.0, vr1);

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
        v0.w,
        vr0.w));

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.y,
        vr1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.z,
        vr1.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.w,
        vr1.w));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final double m = VectorM4D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double w = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM4D.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMagnitudeNormalizeZero()
  {
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    final VectorM4D vr = VectorM4D.normalizeInPlace(v);
    final double m = VectorM4D.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final VectorM4D v = new VectorM4D(1.0, 0.0, 0.0, 0.0);
    final double m = VectorM4D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final VectorM4D v = new VectorM4D(8.0, 0.0, 0.0, 0.0);

    {
      final double p = VectorM4D.dotProduct(v, v);
      final double q = VectorM4D.magnitudeSquared(v);
      final double r = VectorM4D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    final double m = VectorM4D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorM4D v0 = new VectorM4D(8.0, 0.0, 0.0, 0.0);
    final VectorM4D out = new VectorM4D();
    final VectorM4D vr = VectorM4D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM4D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorM4D p = new VectorM4D(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4D q = new VectorM4D(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4D r = new VectorM4D();
      final VectorM4D u = VectorM4D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4D.magnitude(u) == 0.0);
    }

    {
      final VectorM4D p = new VectorM4D(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4D q = new VectorM4D(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4D r = new VectorM4D();
      final VectorM4D u = VectorM4D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4D.magnitude(u) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v0 = new VectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(out.w == 1.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);

    final VectorM4D ov0 = VectorM4D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(out.w == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);

    final VectorM4D ov1 = VectorM4D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(ov1.w == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
    Assert.assertTrue(v0.w == 2.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();

      VectorM4D.scale(v, 1.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.x, vr.x));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.y, vr.y));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.z, vr.z));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(v.w, vr.w));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;
        final double orig_w = v.w;

        VectorM4D.scaleInPlace(v, 1.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          orig_y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.z,
          orig_z));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.w,
          orig_w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();

      VectorM4D.scale(v, 0.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.x, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.y, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.z, 0.0));
      Assert.assertTrue(ApproximatelyEqualDouble
        .approximatelyEqual(vr.w, 0.0));

      {
        VectorM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.x,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.y,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.z,
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.w,
          0.0));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorM4D v = new VectorM4D(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().equals("[VectorM4D 1.0 2.0 3.0 4.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final VectorM4D vr0 = new VectorM4D();
      VectorM4D.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.z,
        v0.z - v1.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.w,
        v0.w - v1.w));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.z,
          orig_z - v1.z));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.w,
          orig_w - v1.w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v0 = new VectorM4D(1.0, 1.0, 1.0, 1.0);
    final VectorM4D v1 = new VectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(out.w == 1.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);

    final VectorM4D ov0 = VectorM4D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(out.w == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);

    final VectorM4D ov1 = VectorM4D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0);
    Assert.assertTrue(ov1.y == 0.0);
    Assert.assertTrue(ov1.z == 0.0);
    Assert.assertTrue(ov1.w == 0.0);
    Assert.assertTrue(v0.x == 0.0);
    Assert.assertTrue(v0.y == 0.0);
    Assert.assertTrue(v0.z == 0.0);
    Assert.assertTrue(v0.w == 0.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
    Assert.assertTrue(v1.w == 1.0);
  }
}
