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
import com.io7m.jaux.functional.Pair;

public class VectorM3DTest extends VectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.absolute(v, vr);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.y), vr.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, Math.abs(v.z), vr.z));
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final double orig_x = v.x;
      final double orig_y = v.y;
      final double orig_z = v.z;

      VectorM3D.absoluteInPlace(v);

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
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final VectorM3D vr0 = new VectorM3D();
      VectorM3D.add(v0, v1, vr0);

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x + v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y + v1.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z + v1.z));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + v1.y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          + v1.z));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0f, 1.0f, 1.0f);
    final VectorM3D v1 = new VectorM3D(1.0f, 1.0f, 1.0f);

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

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);

      final double r = Math.random() * max;

      final VectorM3D vr0 = new VectorM3D();
      VectorM3D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z
        + (v1.z * r)));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          + (v1.x * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          + (v1.y * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          + (v1.z * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
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
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);
      final VectorM3D v1 = new VectorM3D(x0, y0, z0);
      final VectorM3D v2 = new VectorM3D(x0, y0, z0);

      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM3D v = new VectorM3D(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Float.MIN_VALUE;
      final double max_y = Math.random() * Float.MIN_VALUE;
      final double max_z = Math.random() * Float.MIN_VALUE;
      final VectorM3D maximum = new VectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
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

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D minimum = new VectorM3D(min_x, min_y, min_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
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

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Float.MIN_VALUE;
      final double min_y = Math.random() * Float.MIN_VALUE;
      final double min_z = Math.random() * Float.MIN_VALUE;
      final VectorM3D minimum = new VectorM3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D maximum = new VectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
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

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Float.MIN_VALUE;

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

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
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

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Float.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
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

  @Override @Test public void testCopy()
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

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final VectorM3D v0 = new VectorM3D(x0, y0, z0);
      VectorM3D.normalizeInPlace(v0);

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final VectorM3D v1 = new VectorM3D(x1, y1, z1);
      VectorM3D.normalizeInPlace(v1);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.crossProduct(v0, v1, vr);
      VectorM3D.normalizeInPlace(vr);

      final double dp0 = VectorM3D.dotProduct(v0, vr);
      final double dp1 = VectorM3D.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(VectorM3D.almostEqual(
      ec,
      new VectorM3D(),
      new VectorM3D(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D v0 = new VectorM3D(0.0f, 1.0f, 0.0f);
    final VectorM3D v1 = new VectorM3D(0.0f, 0.0f, 0.0f);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM3D.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
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

  @Override @Test public void testDotProduct()
  {
    final VectorM3D v0 = new VectorM3D(10.0f, 10.0f, 10.0f);
    final VectorM3D v1 = new VectorM3D(10.0f, 10.0f, 10.0f);

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

  @Override @Test public void testDotProductPerpendicular()
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

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final VectorM3D q = new VectorM3D(x, y, z);
      final double dp = VectorM3D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3D v0 = new VectorM3D(10.0f, 10.0f, 10.0f);

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

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM3D m0 = new VectorM3D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3D m0 = new VectorM3D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3D m0 = new VectorM3D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3D m0 = new VectorM3D();
      final VectorM3D m1 = new VectorM3D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = new VectorM3D(x, y, z);
      final VectorM3D m1 = new VectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM3D m0 = new VectorM3D();
    final VectorM3D m1 = new VectorM3D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3D m0 = new VectorM3D();
      final VectorM3D m1 = new VectorM3D();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3D m0 = new VectorM3D();
      final VectorM3D m1 = new VectorM3D();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3D m0 = new VectorM3D();
      final VectorM3D m1 = new VectorM3D();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM3D v0 = new VectorM3D(1.0f, 2.0f, 3.0f);
    final VectorM3D v1 = new VectorM3D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

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
      VectorM3D.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM3D.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, vr0.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, vr0.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, vr0.z));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.x, vr1.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.y, vr1.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.z, vr1.z));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
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

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();
      VectorM3D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3D.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D v = new VectorM3D(0.0f, 0.0f, 0.0f);
    final VectorM3D vr = VectorM3D.normalizeInPlace(v);
    final double m = VectorM3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D v = new VectorM3D(1.0f, 0.0f, 0.0f);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM3D v = new VectorM3D(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorM3D.dotProduct(v, v);
      final double q = VectorM3D.magnitudeSquared(v);
      final double r = VectorM3D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D v = new VectorM3D(0.0f, 0.0f, 0.0f);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM3D v0 = new VectorM3D(8.0f, 0.0f, 0.0f);
    final VectorM3D out = new VectorM3D();
    final VectorM3D vr = VectorM3D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D qr = new VectorM3D();
    final VectorM3D q = new VectorM3D(0, 0, 0);
    VectorM3D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.z));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM3D v0 = new VectorM3D(0, 1, 0);
    final VectorM3D v1 = new VectorM3D(0.5f, 0.5f, 0);

    final Pair<VectorM3D, VectorM3D> r = VectorM3D.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM3D(0, 1, 0), r.first);
    Assert.assertEquals(new VectorM3D(1, 0, 0), r.second);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM3D v0 = new VectorM3D(0f, 1f, 0f);
    final VectorM3D v1 = new VectorM3D(0.5f, 0.5f, 0f);

    VectorM3D.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM3D(0, 1, 0), v0);
    Assert.assertEquals(new VectorM3D(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM3D p = new VectorM3D(1.0f, 0.0f, 0.0f);
      final VectorM3D q = new VectorM3D(0.0f, 1.0f, 0.0f);
      final VectorM3D r = new VectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }

    {
      final VectorM3D p = new VectorM3D(-1.0f, 0.0f, 0.0f);
      final VectorM3D q = new VectorM3D(0.0f, 1.0f, 0.0f);
      final VectorM3D r = new VectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3D ov0 = VectorM3D.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3D ov1 = VectorM3D.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();

      VectorM3D.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, vr.y));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, vr.z));

      {
        final double orig_x = v.x;
        final double orig_y = v.y;
        final double orig_z = v.z;

        VectorM3D.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, orig_z));
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
      final VectorM3D v = new VectorM3D(x, y, z);

      final VectorM3D vr = new VectorM3D();

      VectorM3D.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.x, 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.y, 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.z, 0.0f));

      {
        VectorM3D.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.x, 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.y, 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.z, 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM3D v = new VectorM3D(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorM3D 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

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

      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.x, v0.x - v1.x));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.y, v0.y - v1.y));
      Assert
        .assertTrue(AlmostEqualDouble.almostEqual(ec, vr0.z, v0.z - v1.z));

      {
        final double orig_x = v0.x;
        final double orig_y = v0.y;
        final double orig_z = v0.z;
        VectorM3D.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.x, orig_x
          - v1.x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.y, orig_y
          - v1.y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0.z, orig_z
          - v1.z));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM3D out = new VectorM3D();
    final VectorM3D v0 = new VectorM3D(1.0f, 1.0f, 1.0f);
    final VectorM3D v1 = new VectorM3D(1.0f, 1.0f, 1.0f);

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
