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
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorM4F;

public class VectorM4FTest extends VectorM4Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.absolute(v, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getXF()),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getYF()),
        vr.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getZF()),
        vr.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getWF()),
        vr.getWF()));
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();
      final float orig_w = v.getWF();

      VectorM4F.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_x),
        v.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_y),
        v.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_z),
        v.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_w),
        v.getWF()));
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final float w0 = (float) (Math.random() * max);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final float w1 = (float) (Math.random() * max);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final VectorM4F vr0 = new VectorM4F();
      VectorM4F.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getZF(),
        v0.getZF() + v1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getWF(),
        v0.getWF() + v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          + v1.getZF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getWF(), orig_w
          + v1.getWF()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4F v1 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(out.getWF() == 1.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);

    final VectorM4F ov0 = VectorM4F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);

    final VectorM4F ov1 = VectorM4F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final float w0 = (float) (Math.random() * max);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final float w1 = (float) (Math.random() * max);
      final VectorM4F v1 = new VectorM4F(x1, y1, z1, w1);

      final float r = (float) (Math.random() * max);

      final VectorM4F vr0 = new VectorM4F();
      VectorM4F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + (v1.getYF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getZF(),
        v0.getZF() + (v1.getZF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getWF(),
        v0.getWF() + (v1.getWF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + (v1.getXF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + (v1.getYF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          + (v1.getZF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getWF(), orig_w
          + (v1.getWF() * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, q, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, q, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v0 = new VectorM4F(x0, y0, z0, w0);
      final VectorM4F v1 = new VectorM4F(x0, y0, z0, w0);
      final VectorM4F v2 = new VectorM4F(x0, y0, z0, w0);

      Assert.assertTrue(VectorM4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM4F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM4F v = new VectorM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());

      {
        final VectorM4F vr0 =
          VectorM4F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
        Assert.assertTrue(v.getWF() <= maximum.getWF());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final VectorM4F vr0 =
          VectorM4F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
        Assert.assertTrue(v.getWF() >= minimum.getWF());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final VectorM4F vr0 =
          VectorM4F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
        Assert.assertTrue(v.getWF() <= maximum.getWF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
        Assert.assertTrue(v.getWF() >= minimum.getWF());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getWF() <= maximum);

      {
        VectorM4F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getZF() <= maximum);
        Assert.assertTrue(v.getWF() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        VectorM4F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() >= minimum);
        Assert.assertTrue(v.getWF() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();
      VectorM4F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() <= maximum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        VectorM4F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() <= maximum);
        Assert.assertTrue(v.getZF() >= minimum);
        Assert.assertTrue(v.getWF() <= maximum);
        Assert.assertTrue(v.getWF() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM4F vb = new VectorM4F(5, 6, 7, 8);
    final VectorM4F va = new VectorM4F(1, 2, 3, 4);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());
    Assert.assertFalse(va.getWF() == vb.getWF());

    VectorM4F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
    Assert.assertTrue(va.getWF() == vb.getWF());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM4F v0 =
      new VectorM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final VectorM4F v1 = new VectorM4F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0, v1.getZF(), 0.0f);
    Assert.assertEquals(1, v1.getWF(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final VectorM4F v0 =
      new VectorM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final VectorM4F v1 = new VectorM4F();

    v1.copyFrom4F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v1.getWF(), 0.0f);
  }

  @Override @Test public void testCopy4Correct()
  {
    final VectorM4F v0 =
      new VectorM4F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final VectorM4F v1 = new VectorM4F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v1.getWF(), 0.0f);
  }

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(VectorM4F.almostEqual(
      ec,
      new VectorM4F(),
      new VectorM4F(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM4F v0 = new VectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4F v1 = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      VectorM4F.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

  @Override @Test public void testDotProduct()
  {
    final VectorM4F v0 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final VectorM4F v1 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(v1.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(v1.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM4F vpx = new VectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4F vmx = new VectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorM4F vpy = new VectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4F vmy = new VectorM4F(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorM4F vpz = new VectorM4F(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorM4F vmz = new VectorM4F(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorM4F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM4F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM4F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM4F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final VectorM4F q = new VectorM4F(x, y, z, w);
      final double dp = VectorM4F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4F v0 = new VectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM4F m0 = new VectorM4F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4F m0 = new VectorM4F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4F m0 = new VectorM4F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4F m0 = new VectorM4F();
      final VectorM4F m1 = new VectorM4F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4F m0 = new VectorM4F(x, y, z, w);
      final VectorM4F m1 = new VectorM4F(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM4F m0 = new VectorM4F();
    final VectorM4F m1 = new VectorM4F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4F m0 = new VectorM4F();
      final VectorM4F m1 = new VectorM4F();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4F m0 = new VectorM4F();
      final VectorM4F m1 = new VectorM4F();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4F m0 = new VectorM4F();
      final VectorM4F m1 = new VectorM4F();
      m1.setZF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4F m0 = new VectorM4F();
      final VectorM4F m1 = new VectorM4F();
      m1.setWF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4F v0 = new VectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4F v1 = new VectorM4F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getXF(),
        vr0.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getYF(),
        vr0.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getZF(),
        vr0.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getWF(),
        vr0.getWF()));

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getXF(),
        vr1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getYF(),
        vr1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getZF(),
        vr1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getWF(),
        vr1.getWF()));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final double m = VectorM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      final double m = VectorM4F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4F vr = VectorM4F.normalizeInPlace(v);
    final double m = VectorM4F.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorM4F v = new VectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM4F v = new VectorM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = VectorM4F.dotProduct(v, v);
      final double q = VectorM4F.magnitudeSquared(v);
      final double r = VectorM4F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM4F v = new VectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM4F v0 = new VectorM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4F out = new VectorM4F();
    final VectorM4F vr = VectorM4F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM4F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM4F qr = new VectorM4F();
    final VectorM4F q = new VectorM4F(0, 0, 0, 0);
    VectorM4F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getWF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM4F v0 = new VectorM4F(0, 1, 0, 0);
    final VectorM4F v1 = new VectorM4F(0.5f, 0.5f, 0, 0);

    final Pair<VectorM4F, VectorM4F> r = VectorM4F.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM4F(0, 1, 0, 0), r.getLeft());
    Assert.assertEquals(new VectorM4F(1, 0, 0, 0), r.getRight());
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM4F v0 = new VectorM4F(0f, 1f, 0f, 0f);
    final VectorM4F v1 = new VectorM4F(0.5f, 0.5f, 0f, 0f);

    VectorM4F.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM4F(0, 1, 0, 0), v0);
    Assert.assertEquals(new VectorM4F(1, 0, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
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

  @Override @Test public void testScaleMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(out.getWF() == 1.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);

    final VectorM4F ov0 = VectorM4F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);

    final VectorM4F ov1 = VectorM4F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();

      VectorM4F.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getYF(),
        vr.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getZF(),
        vr.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getWF(),
        vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        VectorM4F.scaleInPlace(v, 1.0f);

        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), orig_w));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4F v = new VectorM4F(x, y, z, w);

      final VectorM4F vr = new VectorM4F();

      VectorM4F.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));

      {
        VectorM4F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM4F v = new VectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    Assert.assertTrue(v.toString().equals("[VectorM4F 1.0 2.0 3.0 4.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() - v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getZF(),
        v0.getZF() - v1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getWF(),
        v0.getWF() - v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          - v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          - v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          - v1.getZF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getWF(), orig_w
          - v1.getWF()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM4F out = new VectorM4F();
    final VectorM4F v0 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4F v1 = new VectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(out.getWF() == 1.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);

    final VectorM4F ov0 = VectorM4F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(out.getWF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);

    final VectorM4F ov1 = VectorM4F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0);
    Assert.assertTrue(ov1.getYF() == 0.0);
    Assert.assertTrue(ov1.getZF() == 0.0);
    Assert.assertTrue(ov1.getWF() == 0.0);
    Assert.assertTrue(v0.getXF() == 0.0);
    Assert.assertTrue(v0.getYF() == 0.0);
    Assert.assertTrue(v0.getZF() == 0.0);
    Assert.assertTrue(v0.getWF() == 0.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
    Assert.assertTrue(v1.getWF() == 1.0);
  }
}
