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

package com.io7m.jtensors.tests;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.VectorM3D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3DContract extends VectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      VectorM3D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getZD()), vr.getZD()));
    }
  }

  protected abstract VectorM3D newVectorM3D(
    final double x,
    final double y,
    final double z);

  protected abstract VectorM3D newVectorM3D();

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();

      VectorM3D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_z), v.getZD()));
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
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);

      final VectorM3D vr0 = this.newVectorM3D();
      VectorM3D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + v1.getZD()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM3D out = this.newVectorM3D();
    final VectorM3D v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);
    final VectorM3D v1 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3D ov0 = VectorM3D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3D ov1 = VectorM3D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
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
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);

      final double r = Math.random() * max;

      final VectorM3D vr0 = this.newVectorM3D();
      VectorM3D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + (v1.getZD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + (v1.getZD() * r)));
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
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, y, q);
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
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);
      final VectorM3D v1 = this.newVectorM3D(x0, y0, z0);
      final VectorM3D v2 = this.newVectorM3D(x0, y0, z0);

      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM3D v = this.newVectorM3D(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Float.MIN_VALUE;
      final double max_y = Math.random() * Float.MIN_VALUE;
      final double max_z = Math.random() * Float.MIN_VALUE;
      final VectorM3D maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      final VectorM3D vo = VectorM3D.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      {
        final VectorM3D vr0 = VectorM3D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      final VectorM3D vo = VectorM3D.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final VectorM3D vr0 = VectorM3D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Float.MIN_VALUE;
      final double min_y = Math.random() * Float.MIN_VALUE;
      final double min_z = Math.random() * Float.MIN_VALUE;
      final VectorM3D minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorM3D maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      final VectorM3D vo = VectorM3D.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final VectorM3D vr0 =
          VectorM3D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());

        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
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
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      VectorM3D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);

      {
        VectorM3D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
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
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      VectorM3D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
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
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
      VectorM3D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM3D vb = this.newVectorM3D(5, 6, 7);
    final VectorM3D va = this.newVectorM3D(1, 2, 3);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());
    Assert.assertFalse(va.getZD() == vb.getZD());

    VectorM3D.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
    Assert.assertTrue(va.getZD() == vb.getZD());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM3D v0 = this.newVectorM3D(
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE);
    final VectorM3D v1 = this.newVectorM3D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(0, v1.getZD(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final VectorM3D v0 = this.newVectorM3D(
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE);
    final VectorM3D v1 = this.newVectorM3D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0f);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);
      VectorM3D.normalizeInPlace(v0);

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);
      VectorM3D.normalizeInPlace(v1);

      final VectorM3D vr = this.newVectorM3D();
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
    Assert.assertTrue(
      VectorM3D.almostEqual(
        ec, this.newVectorM3D(), this.newVectorM3D(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM3D.Context3D c = new VectorM3D.Context3D();

    final VectorM3D v0 = this.newVectorM3D(0.0f, 1.0f, 0.0f);
    final VectorM3D v1 = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorM3D.distance(c, v0, v1), 1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    final VectorM3D.Context3D c = new VectorM3D.Context3D();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);

      Assert.assertTrue(VectorM3D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM3D v0 = this.newVectorM3D(10.0f, 10.0f, 10.0f);
    final VectorM3D v1 = this.newVectorM3D(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM3D vpx = this.newVectorM3D(1.0f, 0.0f, 0.0f);
    final VectorM3D vmx = this.newVectorM3D(-1.0f, 0.0f, 0.0f);

    final VectorM3D vpy = this.newVectorM3D(0.0f, 1.0f, 0.0f);
    final VectorM3D vmy = this.newVectorM3D(0.0f, -1.0f, 0.0f);

    final VectorM3D vpz = this.newVectorM3D(0.0f, 0.0f, 1.0f);
    final VectorM3D vmz = this.newVectorM3D(0.0f, 0.0f, -1.0f);

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
      final VectorM3D q = this.newVectorM3D(x, y, z);
      final double dp = VectorM3D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3D v0 = this.newVectorM3D(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3D.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM3D m0 = this.newVectorM3D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3D m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3D m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3D m0 = this.newVectorM3D();
      final VectorM3D m1 = this.newVectorM3D();
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
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3D m0 = this.newVectorM3D(x, y, z);
      final VectorM3D m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM3D m0 = this.newVectorM3D();
    final VectorM3D m1 = this.newVectorM3D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3D m0 = this.newVectorM3D();
      final VectorM3D m1 = this.newVectorM3D();
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3D m0 = this.newVectorM3D();
      final VectorM3D m1 = this.newVectorM3D();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3D m0 = this.newVectorM3D();
      final VectorM3D m1 = this.newVectorM3D();
      m1.setZD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM3D v0 = this.newVectorM3D(1.0f, 2.0f, 3.0f);
    final VectorM3D v1 = new VectorM3D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM3D.Context3D c = new VectorM3D.Context3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);

      final VectorM3D vr0 = this.newVectorM3D();
      final VectorM3D vr1 = this.newVectorM3D();
      VectorM3D.interpolateLinear(c, v0, v1, 0.0f, vr0);
      VectorM3D.interpolateLinear(c, v0, v1, 1.0f, vr1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getXD(), vr0.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getYD(), vr0.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getZD(), vr0.getZD()));

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getZD(), vr1.getZD()));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

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
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();
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

    final VectorM3D v = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    final VectorM3D vr = VectorM3D.normalizeInPlace(v);
    final double m = VectorM3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D v = this.newVectorM3D(1.0f, 0.0f, 0.0f);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM3D v = this.newVectorM3D(8.0f, 0.0f, 0.0f);

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

    final VectorM3D v = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM3D v0 = this.newVectorM3D(8.0f, 0.0f, 0.0f);
    final VectorM3D out = this.newVectorM3D();
    final VectorM3D vr = VectorM3D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3D qr = this.newVectorM3D();
    final VectorM3D q = this.newVectorM3D(0, 0, 0);
    VectorM3D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM3D.Context3D c = new VectorM3D.Context3D();
    final VectorM3D v0 = this.newVectorM3D(0, 1, 0);
    final VectorM3D v1 = this.newVectorM3D(0.5f, 0.5f, 0);
    final VectorM3D v0_out = new VectorM3D();
    final VectorM3D v1_out = new VectorM3D();

    VectorM3D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3D(0, 1, 0), v0_out);
    Assert.assertEquals(this.newVectorM3D(1, 0, 0), v1_out);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM3D.Context3D c = new VectorM3D.Context3D();
    final VectorM3D v0 = this.newVectorM3D(0f, 1f, 0f);
    final VectorM3D v1 = this.newVectorM3D(0.5f, 0.5f, 0f);

    VectorM3D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3D(0, 1, 0), v0);
    Assert.assertEquals(this.newVectorM3D(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM3D p = this.newVectorM3D(1.0f, 0.0f, 0.0f);
      final VectorM3D q = this.newVectorM3D(0.0f, 1.0f, 0.0f);
      final VectorM3D r = this.newVectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }

    {
      final VectorM3D p = this.newVectorM3D(-1.0f, 0.0f, 0.0f);
      final VectorM3D q = this.newVectorM3D(0.0f, 1.0f, 0.0f);
      final VectorM3D r = this.newVectorM3D();
      final VectorM3D u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3D.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM3D out = this.newVectorM3D();
    final VectorM3D v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final VectorM3D ov0 = VectorM3D.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final VectorM3D ov1 = VectorM3D.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();

      VectorM3D.scale(v, 1.0f, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getYD(), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getZD(), vr.getZD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();

        VectorM3D.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
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
      final VectorM3D v = this.newVectorM3D(x, y, z);

      final VectorM3D vr = this.newVectorM3D();

      VectorM3D.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0f));

      {
        VectorM3D.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM3D v = this.newVectorM3D(1.0f, 2.0f, 3.0f);
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
      final VectorM3D v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3D v1 = this.newVectorM3D(x1, y1, z1);

      final VectorM3D vr0 = this.newVectorM3D();
      VectorM3D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() - v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z - v1.getZD()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM3D out = this.newVectorM3D();
    final VectorM3D v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);
    final VectorM3D v1 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3D ov0 = VectorM3D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3D ov1 = VectorM3D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(ov1.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v0.getZD() == 0.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
  }
}
