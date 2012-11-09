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

public class VectorM3FTest
{
  @SuppressWarnings("static-method") @Test public void testAbsolute()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.absolute(v, vr);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.x),
        vr.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.y),
        vr.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        Math.abs(v.z),
        vr.z));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;
        final float orig_z = v.z;

        VectorM3F.absoluteInPlace(v);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_y),
          v.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          Math.abs(orig_z),
          v.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAbsoluteMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v = new VectorM3F(-1.0f, -1.0f, -1.0f);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);
    Assert.assertTrue(v.z == -1.0);

    final float vx = v.x;
    final float vy = v.y;
    final float vz = v.z;

    final VectorM3F ov = VectorM3F.absolute(v, out);

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
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.absolute(v, vr);

      Assert.assertTrue(vr.x >= 0.0);
      Assert.assertTrue(vr.y >= 0.0);
      Assert.assertTrue(vr.z >= 0.0);

      {
        VectorM3F.absoluteInPlace(v);
        Assert.assertTrue(v.x >= 0.0);
        Assert.assertTrue(v.y >= 0.0);
        Assert.assertTrue(v.z >= 0.0);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z + v1.z));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + v1.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z + v1.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov0 = VectorM3F.add(v0, v1, out);

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

    final VectorM3F ov1 = VectorM3F.addInPlace(v0, v1);

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
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z + (v1.z * r)));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x + (v1.x * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y + (v1.y * r)));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z + (v1.z * r)));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final float x0 = 0.0f;
    final float x1 = 0.0f;
    final float y0 = 0.0f;
    final float y1 = 0.0f;
    final float z0 = 0.0f;
    final float z1 = 0.0f;
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));

    final VectorM3F v0 = new VectorM3F(x0, y0, z0);
    final VectorM3F v1 = new VectorM3F(x1, y1, z1);
    Assert.assertTrue(VectorM3F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final float x0 = 0.0f;
    final float x1 = 1.0f;
    final float y0 = 0.0f;
    final float y1 = 1.0f;
    final float z0 = 0.0f;
    final float z1 = 0.0f;
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));

    final VectorM3F v0 = new VectorM3F(x0, y0, z0);
    final VectorM3F v1 = new VectorM3F(x1, y1, z1);
    Assert.assertFalse(VectorM3F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorM3F v = new VectorM3F(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
    Assert.assertTrue(v.z == v.getZF());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F maximum = new VectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampMaximumByVectorInPlace(v, maximum);
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
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F minimum = new VectorM3F(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampMinimumByVectorInPlace(v, minimum);
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
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F minimum = new VectorM3F(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F maximum = new VectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampByVectorInPlace(v, minimum, maximum);
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
    for (int index = 0; index < 100; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);

      {
        VectorM3F.clampMaximumInPlace(v, maximum);
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
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3F.clampInPlace(v, minimum, maximum);

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
    final VectorM3F vb = new VectorM3F(5, 6, 7);
    final VectorM3F va = new VectorM3F(1, 2, 3);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);

    VectorM3F.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductPerpendicular()
  {
    final VectorM3F vy = new VectorM3F(0, 1, 0);
    final VectorM3F vx = new VectorM3F(1, 0, 0);
    final VectorM3F vz = new VectorM3F(0, 0, 1);
    final VectorM3F out = new VectorM3F();

    final VectorM3F vxy = VectorM3F.crossProduct(vx, vy, out);
    Assert.assertSame(vxy, out);
    Assert.assertTrue(VectorM3F.dotProduct(vxy, vx) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vxy, vy) == 0.0);

    final VectorM3F vxz = VectorM3F.crossProduct(vx, vz, out);
    Assert.assertSame(vxy, out);
    Assert.assertTrue(VectorM3F.dotProduct(vxz, vx) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vxz, vz) == 0.0);

    final VectorM3F vyz = VectorM3F.crossProduct(vy, vz, out);
    Assert.assertSame(vyz, out);
    Assert.assertTrue(VectorM3F.dotProduct(vyz, vy) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vyz, vz) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductSimple()
  {
    final VectorM3F v0 = new VectorM3F(0, 1, 0);
    final VectorM3F v1 = new VectorM3F(1, 0, 0);
    final VectorM3F out = new VectorM3F();
    final VectorM3F vr = VectorM3F.crossProduct(v0, v1, out);

    Assert.assertSame(vr, out);
    Assert.assertTrue(vr.x == 0.0);
    Assert.assertTrue(vr.y == 0.0);
    Assert.assertTrue(vr.z == -1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorM3F v0 = new VectorM3F(0.0f, 1.0f, 0.0f);
    final VectorM3F v1 = new VectorM3F(0.0f, 0.0f, 0.0f);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      VectorM3F.distance(v0, v1),
      1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      Assert.assertTrue(VectorM3F.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final VectorM3F v0 = new VectorM3F(10.0f, 10.0f, 10.0f);
    final VectorM3F v1 = new VectorM3F(10.0f, 10.0f, 10.0f);

    {
      final float p = VectorM3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final float p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final float p = VectorM3F.dotProduct(v1, v1);
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
    final VectorM3F v = new VectorM3F(1.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorM3F.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductSelfMagnitudeSquared()
  {
    final VectorM3F v0 = new VectorM3F(10.0f, 10.0f, 10.0f);

    {
      final float p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final float p = VectorM3F.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorM3F m0 = new VectorM3F();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorM3F m0 = new VectorM3F();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorM3F m0 = new VectorM3F();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq0()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.x = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq1()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.y = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq2()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.z = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeEq()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase0()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.x = 23f;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase1()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.y = 23f;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase2()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    m1.z = 23f;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorM3F v0 = new VectorM3F(1.0f, 2.0f, 3.0f);
    final VectorM3F v1 = new VectorM3F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      final VectorM3F vr1 = new VectorM3F();
      VectorM3F.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM3F.interpolateLinear(v0, v1, 1.0f, vr1);

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
        v1.x,
        vr1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.y,
        vr1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.z,
        vr1.z));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final float m = VectorM3F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final float x =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float y =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float z =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final float m = VectorM3F.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMagnitudeNormalizeZero()
  {
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    final VectorM3F vr = VectorM3F.normalizeInPlace(v);
    final float m = VectorM3F.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final VectorM3F v = new VectorM3F(1.0f, 0.0f, 0.0f);
    final float m = VectorM3F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final VectorM3F v = new VectorM3F(8.0f, 0.0f, 0.0f);

    {
      final float p = VectorM3F.dotProduct(v, v);
      final float q = VectorM3F.magnitudeSquared(v);
      final float r = VectorM3F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    final float m = VectorM3F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(m, 0.0f));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorM3F v0 = new VectorM3F(8.0f, 0.0f, 0.0f);
    final VectorM3F out = new VectorM3F();
    final VectorM3F vr = VectorM3F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final float m = VectorM3F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorM3F p = new VectorM3F(1.0f, 0.0f, 0.0f);
      final VectorM3F q = new VectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = new VectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }

    {
      final VectorM3F p = new VectorM3F(-1.0f, 0.0f, 0.0f);
      final VectorM3F q = new VectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = new VectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3F ov0 = VectorM3F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3F ov1 = VectorM3F.scaleInPlace(v0, 2.0f);

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
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();

      VectorM3F.scale(v, 1.0f, vr);

      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.x, vr.x));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.y, vr.y));
      Assert
        .assertTrue(ApproximatelyEqualFloat.approximatelyEqual(v.z, vr.z));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;
        final float orig_z = v.z;

        VectorM3F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          orig_x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          orig_y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.z,
          orig_z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();

      VectorM3F.scale(v, 0.0f, vr);

      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.x, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.y, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.z, 0.0f));

      {
        VectorM3F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.x,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.y,
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.z,
          0.0f));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorM3F v = new VectorM3F(0.0f, 1.0f, 2.0f);
    Assert.assertTrue(v.toString().equals("[VectorM3F 0.0 1.0 2.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.z,
        v0.z - v1.z));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.x,
          orig_x - v1.x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.y,
          orig_y - v1.y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.z,
          orig_z - v1.z));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov0 = VectorM3F.subtract(v0, v1, out);

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

    final VectorM3F ov1 = VectorM3F.subtractInPlace(v0, v1);

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
