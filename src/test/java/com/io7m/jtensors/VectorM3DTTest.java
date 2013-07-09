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

public class VectorM3DTTest extends VectorM3TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.absolute(v, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getXD()),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getYD()),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getZD()),
        vr.getZD()));
    }
  }

  @Override @Test public <A> void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();

      VectorM3DT.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_x),
        v.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_y),
        v.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_z),
        v.getZD()));
    }
  }

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);

      final VectorM3DT<A> vr0 = new VectorM3DT<A>();
      VectorM3DT.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getZD(),
        v0.getZD() + v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3DT.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x + v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y + v1.getYD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getZD(),
          orig_z + v1.getZD()));
      }
    }
  }

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM3DT<A> out = new VectorM3DT<A>();
    final VectorM3DT<A> v0 = new VectorM3DT<A>(1.0f, 1.0f, 1.0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3DT<A> ov0 = VectorM3DT.add(v0, v1, out);

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

    final VectorM3DT<A> ov1 = VectorM3DT.addInPlace(v0, v1);

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

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);

      final double r = Math.random() * max;

      final VectorM3DT<A> vr0 = new VectorM3DT<A>();
      VectorM3DT.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getZD(),
        v0.getZD() + (v1.getZD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3DT.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x + (v1.getXD() * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y + (v1.getYD() * r)));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getZD(),
          orig_z + (v1.getZD() * r)));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, z);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, q, z);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, y, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, z);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, z);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, z);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, q, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, y, q);
      Assert.assertFalse(VectorM3DT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x0, y0, z0);
      final VectorM3DT<A> v2 = new VectorM3DT<A>(x0, y0, z0);

      Assert.assertTrue(VectorM3DT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3DT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3DT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorM3DT<A> v = new VectorM3DT<A>(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Float.MIN_VALUE;
      final double max_y = Math.random() * Float.MIN_VALUE;
      final double max_z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> maximum = new VectorM3DT<A>(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      final VectorM3DT<A> vo =
        VectorM3DT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      {
        final VectorM3DT<A> vr0 =
          VectorM3DT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> minimum = new VectorM3DT<A>(min_x, min_y, min_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      final VectorM3DT<A> vo =
        VectorM3DT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final VectorM3DT<A> vr0 =
          VectorM3DT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Float.MIN_VALUE;
      final double min_y = Math.random() * Float.MIN_VALUE;
      final double min_z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> minimum = new VectorM3DT<A>(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> maximum = new VectorM3DT<A>(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      final VectorM3DT<A> vo =
        VectorM3DT.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final VectorM3DT<A> vr0 =
          VectorM3DT.clampByVectorInPlace(v, minimum, maximum);
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

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Float.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);

      {
        VectorM3DT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3DT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Float.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3DT.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorM3DT<A> vb = new VectorM3DT<A>(5, 6, 7);
    final VectorM3DT<A> va = new VectorM3DT<A>(1, 2, 3);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());
    Assert.assertFalse(va.getZD() == vb.getZD());

    VectorM3DT.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
    Assert.assertTrue(va.getZD() == vb.getZD());
  }

  @Override @Test public <A> void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);
      VectorM3DT.normalizeInPlace(v0);

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);
      VectorM3DT.normalizeInPlace(v1);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.crossProduct(v0, v1, vr);
      VectorM3DT.normalizeInPlace(vr);

      final double dp0 = VectorM3DT.dotProduct(v0, vr);
      final double dp1 = VectorM3DT.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public <A> void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(VectorM3DT.almostEqual(
      ec,
      new VectorM3DT<A>(),
      new VectorM3DT<A>(0, 0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3DT<A> v0 = new VectorM3DT<A>(0.0f, 1.0f, 0.0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(0.0f, 0.0f, 0.0f);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM3DT.distance(v0, v1),
      1.0f));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);

      Assert.assertTrue(VectorM3DT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(10.0f, 10.0f, 10.0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3DT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3DT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorM3DT<A> vpx = new VectorM3DT<A>(1.0f, 0.0f, 0.0f);
    final VectorM3DT<A> vmx = new VectorM3DT<A>(-1.0f, 0.0f, 0.0f);

    final VectorM3DT<A> vpy = new VectorM3DT<A>(0.0f, 1.0f, 0.0f);
    final VectorM3DT<A> vmy = new VectorM3DT<A>(0.0f, -1.0f, 0.0f);

    final VectorM3DT<A> vpz = new VectorM3DT<A>(0.0f, 0.0f, 1.0f);
    final VectorM3DT<A> vmz = new VectorM3DT<A>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorM3DT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM3DT.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM3DT.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM3DT.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final VectorM3DT<A> q = new VectorM3DT<A>(x, y, z);
      final double dp = VectorM3DT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3DT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3DT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      final VectorM3DT<A> m1 = new VectorM3DT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>(x, y, z);
      final VectorM3DT<A> m1 = new VectorM3DT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM3DT<A> m0 = new VectorM3DT<A>();
    final VectorM3DT<A> m1 = new VectorM3DT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      final VectorM3DT<A> m1 = new VectorM3DT<A>();
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      final VectorM3DT<A> m1 = new VectorM3DT<A>();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3DT<A> m0 = new VectorM3DT<A>();
      final VectorM3DT<A> m1 = new VectorM3DT<A>();
      m1.setZD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(1.0f, 2.0f, 3.0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);

      final VectorM3DT<A> vr0 = new VectorM3DT<A>();
      final VectorM3DT<A> vr1 = new VectorM3DT<A>();
      VectorM3DT.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM3DT.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getXD(),
        vr0.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getYD(),
        vr0.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getZD(),
        vr0.getZD()));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getXD(),
        vr1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getYD(),
        vr1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getZD(),
        vr1.getZD()));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final double m = VectorM3DT.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();
      VectorM3DT.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3DT.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3DT<A> v = new VectorM3DT<A>(0.0f, 0.0f, 0.0f);
    final VectorM3DT<A> vr = VectorM3DT.normalizeInPlace(v);
    final double m = VectorM3DT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3DT<A> v = new VectorM3DT<A>(1.0f, 0.0f, 0.0f);
    final double m = VectorM3DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM3DT<A> v = new VectorM3DT<A>(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorM3DT.dotProduct(v, v);
      final double q = VectorM3DT.magnitudeSquared(v);
      final double r = VectorM3DT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3DT<A> v = new VectorM3DT<A>(0.0f, 0.0f, 0.0f);
    final double m = VectorM3DT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(8.0f, 0.0f, 0.0f);
    final VectorM3DT<A> out = new VectorM3DT<A>();
    final VectorM3DT<A> vr = VectorM3DT.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3DT.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM3DT<A> qr = new VectorM3DT<A>();
    final VectorM3DT<A> q = new VectorM3DT<A>(0, 0, 0);
    VectorM3DT.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(0, 1, 0);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(0.5f, 0.5f, 0);

    final Pair<VectorM3DT<A>, VectorM3DT<A>> r =
      VectorM3DT.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM3DT<A>(0, 1, 0), r.first);
    Assert.assertEquals(new VectorM3DT<A>(1, 0, 0), r.second);
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    final VectorM3DT<A> v0 = new VectorM3DT<A>(0f, 1f, 0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(0.5f, 0.5f, 0f);

    VectorM3DT.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM3DT<A>(0, 1, 0), v0);
    Assert.assertEquals(new VectorM3DT<A>(1, 0, 0), v1);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM3DT<A> p = new VectorM3DT<A>(1.0f, 0.0f, 0.0f);
      final VectorM3DT<A> q = new VectorM3DT<A>(0.0f, 1.0f, 0.0f);
      final VectorM3DT<A> r = new VectorM3DT<A>();
      final VectorM3DT<A> u = VectorM3DT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3DT.magnitude(u) == 0.0);
    }

    {
      final VectorM3DT<A> p = new VectorM3DT<A>(-1.0f, 0.0f, 0.0f);
      final VectorM3DT<A> q = new VectorM3DT<A>(0.0f, 1.0f, 0.0f);
      final VectorM3DT<A> r = new VectorM3DT<A>();
      final VectorM3DT<A> u = VectorM3DT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3DT.magnitude(u) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM3DT<A> out = new VectorM3DT<A>();
    final VectorM3DT<A> v0 = new VectorM3DT<A>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final VectorM3DT<A> ov0 = VectorM3DT.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final VectorM3DT<A> ov1 = VectorM3DT.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();

      VectorM3DT.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getYD(),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getZD(),
        vr.getZD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();

        VectorM3DT.scaleInPlace(v, 1.0f);

        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
      }
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v = new VectorM3DT<A>(x, y, z);

      final VectorM3DT<A> vr = new VectorM3DT<A>();

      VectorM3DT.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0f));

      {
        VectorM3DT.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0f));
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM3DT<A> v = new VectorM3DT<A>(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorM3DT 1.0 2.0 3.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v0 = new VectorM3DT<A>(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorM3DT<A> v1 = new VectorM3DT<A>(x1, y1, z1);

      final VectorM3DT<A> vr0 = new VectorM3DT<A>();
      VectorM3DT.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getZD(),
        v0.getZD() - v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3DT.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x - v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y - v1.getYD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getZD(),
          orig_z - v1.getZD()));
      }
    }
  }

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM3DT<A> out = new VectorM3DT<A>();
    final VectorM3DT<A> v0 = new VectorM3DT<A>(1.0f, 1.0f, 1.0f);
    final VectorM3DT<A> v1 = new VectorM3DT<A>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final VectorM3DT<A> ov0 = VectorM3DT.subtract(v0, v1, out);

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

    final VectorM3DT<A> ov1 = VectorM3DT.subtractInPlace(v0, v1);

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
