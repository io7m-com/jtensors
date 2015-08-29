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

import com.io7m.jaux.ApproximatelyEqualFloat;

public class VectorM4FTest
{
  @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.y),
        vr.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.z),
        vr.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.w),
        vr.w));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;
        final float orig_z = v.z;
        final float orig_w = v.w;

        VectorM4F.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_z),
          v.z));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_w),
          v.w));
      }
    }
  }

  @Test public void testAbsoluteMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v = new VectorM4F(-1.0f, -1.0f, -1.0f, -1.0f);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);
    Assert.assertTrue(v.z == -1.0);
    Assert.assertTrue(v.w == -1.0);

    final float vx = v.x;
    final float vy = v.y;
    final float vz = v.z;
    final float vw = v.w;

    final VectorM4F ov = VectorM4F.absolute(v, out);

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

  @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);
      Assert.assertTrue(vr.z >= 0.0);
      Assert.assertTrue(vr.w >= 0.0);

      {
        VectorM4F.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
        Assert.assertTrue(v.z >= 0.0);
        Assert.assertTrue(v.w >= 0.0);
      }
    }
  }

  @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final VectorM4F vr0 = new VectorM4F();
      VectorM4F.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z + v1.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.w,
        v0.w + v1.w));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        final float orig_w = v0.w;
        VectorM4F.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z + v1.z));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.w,
          orig_w + v1.w));
      }
    }
  }

  @Test public void testAddMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4F v1 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

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

    final VectorM4F ov0 = VectorM4F.add(v0, v1, out);

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

    final VectorM4F ov1 = VectorM4F.addInPlace(v0, v1);

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

  @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);

      final VectorM4F vr0 = new VectorM4F();
      VectorM4F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z + (v1.z * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.w,
        v0.w + (v1.w * r)));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        final float orig_w = v0.w;
        VectorM4F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z + (v1.z * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.w,
          orig_w + (v1.w * r)));
      }
    }
  }

  @Test public void testApproximatelyEqualTransitive0()
  {
    final float x0 = 0.0f;
    final float x1 = 0.0f;
    final float y0 = 0.0f;
    final float y1 = 0.0f;
    final float z0 = 0.0f;
    final float z1 = 0.0f;
    final float w0 = 0.0f;
    final float w1 = 0.0f;
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);
    final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);
    Assert.assertTrue(VectorM4F.approximatelyEqual(v0, v1));
  }

  @Test public void testApproximatelyEqualTransitive1()
  {
    final float x0 = 0.0f;
    final float x1 = 1.0f;
    final float y0 = 0.0f;
    final float y1 = 1.0f;
    final float z0 = 0.0f;
    final float z1 = 1.0f;
    final float w0 = 0.0f;
    final float w1 = 1.0f;
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);
    final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);
    Assert.assertFalse(VectorM4F.approximatelyEqual(v0, v1));
  }

  @Test public void testCheckInterface()
  {
    final VectorM4F v = new VectorM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
    Assert.assertTrue(v.z == v.getZF());
    Assert.assertTrue(v.w == v.getWF());
  }

  @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F maximum = new VectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      final VectorM4F vo = VectorM4F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.w <= maximum.w);

      {
        final VectorM4F vr0 =
          VectorM4F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
        Assert.assertTrue(v.w <= maximum.w);
      }
    }
  }

  @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F minimum = new VectorM4F(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      final VectorM4F vo = VectorM4F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);
      Assert.assertTrue(vr.w >= minimum.w);

      {
        final VectorM4F vr0 =
          VectorM4F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
        Assert.assertTrue(v.w >= minimum.w);
      }
    }
  }

  @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final float min_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F minimum = new VectorM4F(min_x, min_y, min_z, min_w);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final float max_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F maximum = new VectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      final VectorM4F vo = VectorM4F.clampByVector(v, minimum, maximum, vr);

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
        final VectorM4F vr0 =
          VectorM4F.clampByVectorInPlace(v, minimum, maximum);
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

  @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.w <= maximum);

      {
        VectorM4F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.w <= maximum);
      }
    }
  }

  @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
        Assert.assertTrue(v.w >= minimum);
      }
    }
  }

  @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);
      Assert.assertTrue(vr.w <= maximum);
      Assert.assertTrue(vr.w >= minimum);

      {
        VectorM4F.clampInPlace(v, minimum, maximum);

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

  @Test public void testCopy()
  {
    final VectorM4F vb = new VectorM4F(5, 6, 7, 8);
    final VectorM4F va = new VectorM4F(1, 2, 3, 4);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);
    Assert.assertFalse(va.w == vb.w);

    VectorM4F.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
    Assert.assertTrue(va.w == vb.w);
  }

  @Test public void testDistance()
  {
    final VectorM4F v0 = new VectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4F v1 = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      VectorM4F.distance(v0, v1),
      1.0f));
  }

  @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4F.distance(v0, v1) >= 0.0);
    }
  }

  @Test public void testDotProduct()
  {
    final VectorM4F v0 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final VectorM4F v1 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final float p = VectorM4F.dotProduct(v0, v1);
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
      final float p = VectorM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final float p = VectorM4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(v1.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Test public void testDotProductOrthonormal()
  {
    final VectorM4F v = new VectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorM4F.dotProduct(v, v) == 1.0);
  }

  @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4F v0 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final float p = VectorM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final float p = VectorM4F.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v0.w == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Test public void testInitializeReadable()
  {
    final VectorM4F v0 = new VectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F v1 = new VectorM4F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Test public void testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final VectorM4F vr0 = new VectorM4F();
      final VectorM4F vr1 = new VectorM4F();
      VectorM4F.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM4F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.x,
        vr0.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.y,
        vr0.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.z,
        vr0.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.w,
        vr0.w));

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.y,
        vr1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.z,
        vr1.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.w,
        vr1.w));
    }
  }

  @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final float m = VectorM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final float x =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float y =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float z =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float w =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final float m = VectorM4F.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
    }
  }

  @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4F vr = VectorM4F.normalizeInPlace(v);
    final float m = VectorM4F.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @Test public void testMagnitudeOne()
  {
    final VectorM4F v = new VectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final float m = VectorM4F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
  }

  @Test public void testMagnitudeSimple()
  {
    final VectorM4F v = new VectorM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final float p = VectorM4F.dotProduct(v, v);
      final float q = VectorM4F.magnitudeSquared(v);
      final float r = VectorM4F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Test public void testMagnitudeZero()
  {
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final float m = VectorM4F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @Test public void testNormalizeSimple()
  {
    final VectorM4F v0 = new VectorM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4F out = new VectorM4F();
    final VectorM4F vr = VectorM4F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final float m = VectorM4F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM4F p = new VectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4F q = new VectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4F r = new VectorM4F();
      final VectorM4F u = VectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4F.magnitude(u) == 0.0);
    }

    {
      final VectorM4F p = new VectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4F q = new VectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4F r = new VectorM4F();
      final VectorM4F u = VectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4F.magnitude(u) == 0.0);
    }
  }

  @Test public void testScaleMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(out.w == 1.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);

    final VectorM4F ov0 = VectorM4F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(out.w == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v0.w == 1.0);

    final VectorM4F ov1 = VectorM4F.scaleInPlace(v0, 2.0f);

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

  @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();

      VectorM4F.scale(v, 1.0f, vr);

      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.x, vr.x));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.y, vr.y));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.z, vr.z));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.w, vr.w));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;
        final float orig_z = v.z;
        final float orig_w = v.w;

        VectorM4F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          orig_y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.z,
          orig_z));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.w,
          orig_w));
      }
    }
  }

  @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();

      VectorM4F.scale(v, 0.0f, vr);

      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.x, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.y, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.z, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.w, 0.0f));

      {
        VectorM4F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.z,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.w,
          0.0f));
      }
    }
  }

  @Test public void testString()
  {
    final VectorM4F v = new VectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    Assert.assertTrue(v.toString().equals("[VectorM4F 1.0 2.0 3.0 4.0]"));
  }

  @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final VectorM4F vr0 = new VectorM4F();
      VectorM4F.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z - v1.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.w,
        v0.w - v1.w));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        final float orig_w = v0.w;
        VectorM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z - v1.z));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.w,
          orig_w - v1.w));
      }
    }
  }

  @Test public void testSubtractMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4F v1 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

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

    final VectorM4F ov0 = VectorM4F.subtract(v0, v1, out);

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

    final VectorM4F ov1 = VectorM4F.subtractInPlace(v0, v1);

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
