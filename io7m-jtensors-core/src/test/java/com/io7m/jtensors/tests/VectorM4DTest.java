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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorM4D;

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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(v.getWD()),
        vr.getWD()));
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

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();
      final double orig_w = v.getWD();

      VectorM4D.absoluteInPlace(v);

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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        Math.abs(orig_w),
        v.getWD()));
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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getWD(),
        v0.getWD() + v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        VectorM4D.addInPlace(v0, v1);

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
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getWD(),
          orig_w + v1.getWD()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v0 = new VectorM4D(1.0, 1.0, 1.0, 1.0);
    final VectorM4D v1 = new VectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final VectorM4D ov0 = VectorM4D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(out.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final VectorM4D ov1 = VectorM4D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(ov1.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v0.getWD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);
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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getWD(),
        v0.getWD() + (v1.getWD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        VectorM4D.addScaledInPlace(v0, v1, r);

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
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getWD(),
          orig_w + (v1.getWD() * r)));
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

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
    Assert.assertTrue(v.getWD() == v.getWD());
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
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());
      Assert.assertTrue(vr.getWD() <= maximum.getWD());

      {
        final VectorM4D vr0 =
          VectorM4D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
        Assert.assertTrue(v.getWD() <= maximum.getWD());
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
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());
      Assert.assertTrue(vr.getWD() >= minimum.getWD());

      {
        final VectorM4D vr0 =
          VectorM4D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
        Assert.assertTrue(v.getWD() >= minimum.getWD());
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
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());
      Assert.assertTrue(vr.getWD() <= maximum.getWD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());
      Assert.assertTrue(vr.getWD() >= minimum.getWD());

      {
        final VectorM4D vr0 =
          VectorM4D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
        Assert.assertTrue(v.getWD() <= maximum.getWD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
        Assert.assertTrue(v.getWD() >= minimum.getWD());
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

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getWD() <= maximum);

      {
        VectorM4D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getWD() <= maximum);
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

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);
      Assert.assertTrue(vr.getWD() >= minimum);

      {
        VectorM4D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
        Assert.assertTrue(v.getWD() >= minimum);
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

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);
      Assert.assertTrue(vr.getWD() <= maximum);
      Assert.assertTrue(vr.getWD() >= minimum);

      {
        VectorM4D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
        Assert.assertTrue(v.getWD() <= maximum);
        Assert.assertTrue(v.getWD() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM4D vb = new VectorM4D(5, 6, 7, 8);
    final VectorM4D va = new VectorM4D(1, 2, 3, 4);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());
    Assert.assertFalse(va.getZD() == vb.getZD());
    Assert.assertFalse(va.getWD() == vb.getWD());

    VectorM4D.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
    Assert.assertTrue(va.getZD() == vb.getZD());
    Assert.assertTrue(va.getWD() == vb.getWD());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM4D v0 =
      new VectorM4D(Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE, Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE);
    final VectorM4D v1 = new VectorM4D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(0, v1.getZD(), 0.0f);
    Assert.assertEquals(1, v1.getWD(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final VectorM4D v0 =
      new VectorM4D(Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE, Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE);
    final VectorM4D v1 = new VectorM4D();

    v1.copyFrom4D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0f);
    Assert.assertEquals(v0.getWD(), v1.getWD(), 0.0f);
  }

  @Override @Test public void testCopy4Correct()
  {
    final VectorM4D v0 =
      new VectorM4D(Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE, Math.random() * Double.MAX_VALUE, Math.random()
        * Double.MAX_VALUE);
    final VectorM4D v1 = new VectorM4D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0f);
    Assert.assertEquals(1.0, v1.getWD(), 0.0f);
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
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.setZD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4D m0 = new VectorM4D();
      final VectorM4D m1 = new VectorM4D();
      m1.setWD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4D v0 = new VectorM4D(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4D v1 = new VectorM4D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
    Assert.assertTrue(v0.getWD() == v1.getWD());
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
        v0.getWD(),
        vr0.getWD()));

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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getWD(),
        vr1.getWD()));
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

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getWD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM4D v0 = new VectorM4D(0, 1, 0, 0);
    final VectorM4D v1 = new VectorM4D(0.5, 0.5, 0, 0);

    final Pair<VectorM4D, VectorM4D> r = VectorM4D.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM4D(0, 1, 0, 0), r.getLeft());
    Assert.assertEquals(new VectorM4D(1, 0, 0, 0), r.getRight());
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

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);

    final VectorM4D ov0 = VectorM4D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(out.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);

    final VectorM4D ov1 = VectorM4D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(ov1.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v0.getWD() == 2.0);
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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getYD(),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getZD(),
        vr.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        v.getWD(),
        vr.getWD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();
        final double orig_w = v.getWD();

        VectorM4D.scaleInPlace(v, 1.0);

        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), orig_w));
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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getXD(),
        0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getYD(),
        0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getZD(),
        0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        context,
        vr.getWD(),
        0.0));

      {
        VectorM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), 0.0));
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
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getWD(),
        v0.getWD() - v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        VectorM4D.subtractInPlace(v0, v1);

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
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getWD(),
          orig_w - v1.getWD()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM4D out = new VectorM4D();
    final VectorM4D v0 = new VectorM4D(1.0, 1.0, 1.0, 1.0);
    final VectorM4D v1 = new VectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final VectorM4D ov0 = VectorM4D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final VectorM4D ov1 = VectorM4D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(ov1.getZD() == 0.0);
    Assert.assertTrue(ov1.getWD() == 0.0);
    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v0.getZD() == 0.0);
    Assert.assertTrue(v0.getWD() == 0.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);
  }
}
