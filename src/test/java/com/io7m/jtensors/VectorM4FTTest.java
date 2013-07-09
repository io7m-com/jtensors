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
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.functional.Pair;

public class VectorM4FTTest extends VectorM4TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      VectorM4FT.absolute(v, vr);

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

  @Override @Test public <A> void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();
      final float orig_w = v.getWF();

      VectorM4FT.absoluteInPlace(v);

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

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final float w0 = (float) (Math.random() * max);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final float w1 = (float) (Math.random() * max);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x1, y1, z1, w1);

      final VectorM4FT<A> vr0 = new VectorM4FT<A>();
      VectorM4FT.add(v0, v1, vr0);

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
        VectorM4FT.addInPlace(v0, v1);

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

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM4FT<A> out = new VectorM4FT<A>();
    final VectorM4FT<A> v0 = new VectorM4FT<A>(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(1.0f, 1.0f, 1.0f, 1.0f);

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

    final VectorM4FT<A> ov0 = VectorM4FT.add(v0, v1, out);

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

    final VectorM4FT<A> ov1 = VectorM4FT.addInPlace(v0, v1);

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

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final float w0 = (float) (Math.random() * max);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final float w1 = (float) (Math.random() * max);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x1, y1, z1, w1);

      final float r = (float) (Math.random() * max);

      final VectorM4FT<A> vr0 = new VectorM4FT<A>();
      VectorM4FT.addScaled(v0, v1, r, vr0);

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
        VectorM4FT.addScaledInPlace(v0, v1, r);

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

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, z, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, q, z, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, q, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, z, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, z, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, q, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, z, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, q, w);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, z, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, q, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, q, q, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, q, q);
      Assert.assertFalse(VectorM4FT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x0, y0, z0, w0);
      final VectorM4FT<A> v2 = new VectorM4FT<A>(x0, y0, z0, w0);

      Assert.assertTrue(VectorM4FT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM4FT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM4FT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> maximum =
        new VectorM4FT<A>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      final VectorM4FT<A> vo =
        VectorM4FT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());

      {
        final VectorM4FT<A> vr0 =
          VectorM4FT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
        Assert.assertTrue(v.getWF() <= maximum.getWF());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> minimum =
        new VectorM4FT<A>(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      final VectorM4FT<A> vo =
        VectorM4FT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final VectorM4FT<A> vr0 =
          VectorM4FT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
        Assert.assertTrue(v.getWF() >= minimum.getWF());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final float min_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> minimum =
        new VectorM4FT<A>(min_x, min_y, min_z, min_w);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final float max_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> maximum =
        new VectorM4FT<A>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      final VectorM4FT<A> vo =
        VectorM4FT.clampByVector(v, minimum, maximum, vr);

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
        final VectorM4FT<A> vr0 =
          VectorM4FT.clampByVectorInPlace(v, minimum, maximum);
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

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      VectorM4FT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getWF() <= maximum);

      {
        VectorM4FT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getZF() <= maximum);
        Assert.assertTrue(v.getWF() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      VectorM4FT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        VectorM4FT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() >= minimum);
        Assert.assertTrue(v.getWF() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      VectorM4FT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() <= maximum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        VectorM4FT.clampInPlace(v, minimum, maximum);

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

  @Override @Test public <A> void testCopy()
  {
    final VectorM4FT<A> vb = new VectorM4FT<A>(5, 6, 7, 8);
    final VectorM4FT<A> va = new VectorM4FT<A>(1, 2, 3, 4);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());
    Assert.assertFalse(va.getWF() == vb.getWF());

    VectorM4FT.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
    Assert.assertTrue(va.getWF() == vb.getWF());
  }

  @Override @Test public <A> void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(VectorM4FT.almostEqual(
      ec,
      new VectorM4FT<A>(),
      new VectorM4FT<A>(0, 0, 0, 1)));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      VectorM4FT.distance(v0, v1),
      1.0f));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4FT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(10.0f, 10.0f, 10.0f, 10.0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4FT.dotProduct(v0, v1);
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
      final double p = VectorM4FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4FT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(v1.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorM4FT<A> vpx = new VectorM4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4FT<A> vmx = new VectorM4FT<A>(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorM4FT<A> vpy = new VectorM4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorM4FT<A> vmy = new VectorM4FT<A>(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorM4FT<A> vpz = new VectorM4FT<A>(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorM4FT<A> vmz = new VectorM4FT<A>(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorM4FT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM4FT.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM4FT.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM4FT.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final VectorM4FT<A> q = new VectorM4FT<A>(x, y, z, w);
      final double dp = VectorM4FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = VectorM4FT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v0.getWF() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      final VectorM4FT<A> m1 = new VectorM4FT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>(x, y, z, w);
      final VectorM4FT<A> m1 = new VectorM4FT<A>(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM4FT<A> m0 = new VectorM4FT<A>();
    final VectorM4FT<A> m1 = new VectorM4FT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      final VectorM4FT<A> m1 = new VectorM4FT<A>();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      final VectorM4FT<A> m1 = new VectorM4FT<A>();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      final VectorM4FT<A> m1 = new VectorM4FT<A>();
      m1.setZF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4FT<A> m0 = new VectorM4FT<A>();
      final VectorM4FT<A> m1 = new VectorM4FT<A>();
      m1.setWF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x1, y1, z1, w1);

      final VectorM4FT<A> vr0 = new VectorM4FT<A>();
      final VectorM4FT<A> vr1 = new VectorM4FT<A>();
      VectorM4FT.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM4FT.interpolateLinear(v0, v1, 1.0f, vr1);

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

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final double m = VectorM4FT.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
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
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();
      VectorM4FT.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM4FT.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4FT<A> vr = VectorM4FT.normalizeInPlace(v);
    final double m = VectorM4FT.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4FT.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = VectorM4FT.dotProduct(v, v);
      final double q = VectorM4FT.magnitudeSquared(v);
      final double r = VectorM4FT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4FT.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorM4FT<A> out = new VectorM4FT<A>();
    final VectorM4FT<A> vr = VectorM4FT.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM4FT.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM4FT<A> qr = new VectorM4FT<A>();
    final VectorM4FT<A> q = new VectorM4FT<A>(0, 0, 0, 0);
    VectorM4FT.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getWF()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(0, 1, 0, 0);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(0.5f, 0.5f, 0, 0);

    final Pair<VectorM4FT<A>, VectorM4FT<A>> r =
      VectorM4FT.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM4FT<A>(0, 1, 0, 0), r.first);
    Assert.assertEquals(new VectorM4FT<A>(1, 0, 0, 0), r.second);
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    final VectorM4FT<A> v0 = new VectorM4FT<A>(0f, 1f, 0f, 0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(0.5f, 0.5f, 0f, 0f);

    VectorM4FT.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM4FT<A>(0, 1, 0, 0), v0);
    Assert.assertEquals(new VectorM4FT<A>(1, 0, 0, 0), v1);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM4FT<A> p = new VectorM4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4FT<A> q = new VectorM4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4FT<A> r = new VectorM4FT<A>();
      final VectorM4FT<A> u = VectorM4FT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4FT.magnitude(u) == 0.0);
    }

    {
      final VectorM4FT<A> p = new VectorM4FT<A>(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorM4FT<A> q = new VectorM4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorM4FT<A> r = new VectorM4FT<A>();
      final VectorM4FT<A> u = VectorM4FT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4FT.magnitude(u) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM4FT<A> out = new VectorM4FT<A>();
    final VectorM4FT<A> v0 = new VectorM4FT<A>(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(out.getWF() == 1.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);

    final VectorM4FT<A> ov0 = VectorM4FT.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v0.getWF() == 1.0);

    final VectorM4FT<A> ov1 = VectorM4FT.scaleInPlace(v0, 2.0f);

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

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();

      VectorM4FT.scale(v, 1.0f, vr);

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

        VectorM4FT.scaleInPlace(v, 1.0f);

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

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v = new VectorM4FT<A>(x, y, z, w);

      final VectorM4FT<A> vr = new VectorM4FT<A>();

      VectorM4FT.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));

      {
        VectorM4FT.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), 0.0f));
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM4FT<A> v = new VectorM4FT<A>(1.0f, 2.0f, 3.0f, 4.0f);
    Assert.assertTrue(v.toString().equals("[VectorM4FT 1.0 2.0 3.0 4.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v0 = new VectorM4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM4FT<A> v1 = new VectorM4FT<A>(x1, y1, z1, w1);

      final VectorM4FT<A> vr0 = new VectorM4FT<A>();
      VectorM4FT.subtract(v0, v1, vr0);

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
        VectorM4FT.subtractInPlace(v0, v1);

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

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM4FT<A> out = new VectorM4FT<A>();
    final VectorM4FT<A> v0 = new VectorM4FT<A>(1.0f, 1.0f, 1.0f, 1.0f);
    final VectorM4FT<A> v1 = new VectorM4FT<A>(1.0f, 1.0f, 1.0f, 1.0f);

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

    final VectorM4FT<A> ov0 = VectorM4FT.subtract(v0, v1, out);

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

    final VectorM4FT<A> ov1 = VectorM4FT.subtractInPlace(v0, v1);

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
