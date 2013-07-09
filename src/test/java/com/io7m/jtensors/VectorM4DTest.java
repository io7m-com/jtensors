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
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorM4DTest extends VectorM4Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.absolute(v, vr);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.z), vr.z));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.w), vr.w));
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final double orig_x = v.x;
      final double orig_y = v.y;
      final double orig_z = v.z;
      final double orig_w = v.w;

      VectorM4D.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_x),
        v.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_y),
        v.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_z),
        v.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_w),
        v.w));
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final double w0 = Math.random() * max;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final VectorM4D vr0 = new VectorM4D();
      VectorM4D.add(v0, v1, vr0);

      System.out.println("v0  : " + v0);
      System.out.println("v1  : " + v1);
      System.out.println("vr0 : " + vr0);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x + v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y + v1.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z + v1.z));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.w, v0.w + v1.w));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + v1.y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          + v1.z));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.w, orig_w
          + v1.w));
      }
    }
  }

  @Override @Test public void testAddMutation()
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

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final double w0 = Math.random() * max;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final VectorM4D v1 = new VectorM4D(x1, y1, z1, w1);

      final double r = Math.random() * max;

      final VectorM4D vr0 = new VectorM4D();
      VectorM4D.addScaled(v0, v1, r, vr0);

      System.out.println("v0  : " + v0);
      System.out.println("v1  : " + v1);
      System.out.println("vr0 : " + vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z
        + (v1.z * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.w, v0.w
        + (v1.w * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + (v1.x * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + (v1.y * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          + (v1.z * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.w, orig_w
          + (v1.w * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, z, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, q, z, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, q, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, z, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, z, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, q, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, z, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, q, w);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, z, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, q, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, q, q, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, q, q);
      Assert.assertFalse(VectorM4D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorM4D v0 = new VectorM4D(x0, y0, z0, w0);
      final VectorM4D v1 = new VectorM4D(x0, y0, z0, w0);
      final VectorM4D v2 = new VectorM4D(x0, y0, z0, w0);

      Assert.assertTrue(VectorM4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM4D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM4D v = new VectorM4D(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
    Assert.assertTrue(v.w == v.getWD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testCopy()
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

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(VectorM4D.almostEqual(
      ec,
      new VectorM4D(),
      new VectorM4D(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM4D v0 = new VectorM4D(0.0, 1.0, 0.0, 0.0);
    final VectorM4D v1 = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM4D.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testDotProduct()
  {
    final VectorM4D v0 = new VectorM4D(10.0, 10.0, 10.0, 10.0);
    final VectorM4D v1 = new VectorM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = VectorM4D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM4D vpx = new VectorM4D(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4D vmx = new VectorM4D(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorM4D vpy = new VectorM4D(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4D vmy = new VectorM4D(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorM4D vpz = new VectorM4D(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorM4D vmz = new VectorM4D(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorM4D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM4D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM4D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM4D.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final VectorM4D q = new VectorM4D(x, y, z, w);
      final double dp = VectorM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final VectorM4D q = new VectorM4D(x, y, z, w);

      final double ms = VectorM4D.magnitudeSquared(q);
      final double dp = VectorM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM4D m0 = new VectorM4D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4D m0 = new VectorM4D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4D m0 = new VectorM4D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4D m0 = new VectorM4D(x, y, z, w);
      final VectorM4D m1 = new VectorM4D(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM4D m0 = new VectorM4D();
    final VectorM4D m1 = new VectorM4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.w = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4D v0 = new VectorM4D(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4D v1 = new VectorM4D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, vr0.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, vr0.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, vr0.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.w, vr0.w));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.x, vr1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.y, vr1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.z, vr1.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.w, vr1.w));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double w = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final double m = VectorM4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double w = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();
      VectorM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM4D.magnitude(vr);
      System.out.println(m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    final VectorM4D vr = VectorM4D.normalizeInPlace(v);
    final double m = VectorM4D.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorM4D v = new VectorM4D(1.0, 0.0, 0.0, 0.0);
    final double m = VectorM4D.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
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

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM4D v = new VectorM4D(0.0, 0.0, 0.0, 0.0);
    final double m = VectorM4D.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM4D v0 = new VectorM4D(8.0, 0.0, 0.0, 0.0);
    final VectorM4D out = new VectorM4D();
    final VectorM4D vr = VectorM4D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM4D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM4D qr = new VectorM4D();
    final VectorM4D q = new VectorM4D(0, 0, 0, 0);
    VectorM4D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.z));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.w));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM4D v0 = new VectorM4D(0, 1, 0, 0);
    final VectorM4D v1 = new VectorM4D(0.5, 0.5, 0, 0);

    final Pair<VectorM4D, VectorM4D> r = VectorM4D.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM4D(0, 1, 0, 0), r.first);
    Assert.assertEquals(new VectorM4D(1, 0, 0, 0), r.second);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM4D v0 = new VectorM4D(0, 1, 0, 0);
    final VectorM4D v1 = new VectorM4D(0.5, 0.5, 0, 0);

    VectorM4D.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM4D(0, 1, 0, 0), v0);
    Assert.assertEquals(new VectorM4D(1, 0, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
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

  @Override @Test public void testScaleMutation()
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

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();

      VectorM4D.scale(v, 1.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v.x, vr.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v.y, vr.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v.z, vr.z));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, v.w, vr.w));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;
        final double orig_w = v.w;

        VectorM4D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, orig_z));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.w, orig_w));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorM4D v = new VectorM4D(x, y, z, w);

      final VectorM4D vr = new VectorM4D();

      VectorM4D.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, vr.x, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, vr.y, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, vr.z, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, vr.w, 0.0));

      {
        VectorM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.w, 0.0));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM4D v = new VectorM4D(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().equals("[VectorM4D 1.0 2.0 3.0 4.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x - v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y - v1.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z - v1.z));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.w, v0.w - v1.w));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        final double orig_w = v0.w;
        VectorM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          - v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          - v1.y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          - v1.z));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.w, orig_w
          - v1.w));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
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
