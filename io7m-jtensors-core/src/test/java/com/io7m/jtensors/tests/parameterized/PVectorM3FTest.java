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

package com.io7m.jtensors.tests.parameterized;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.jtensors.tests.TestUtilities;

public class PVectorM3FTest<T> extends PVectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.absolute(v, vr);

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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();

      PVectorM3F.absoluteInPlace(v);

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
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);

      final PVectorM3F<T> vr0 = new PVectorM3F<T>();
      PVectorM3F.add(v0, v1, vr0);

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

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        PVectorM3F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          + v1.getZF()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final PVectorM3F<T> out = new PVectorM3F<T>();
    final PVectorM3F<T> v0 = new PVectorM3F<T>(1.0f, 1.0f, 1.0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final PVectorM3F<T> ov0 = PVectorM3F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final PVectorM3F<T> ov1 = PVectorM3F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
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
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);

      final float r = (float) (Math.random() * max);

      final PVectorM3F<T> vr0 = new PVectorM3F<T>();
      PVectorM3F.addScaled(v0, v1, r, vr0);

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

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        PVectorM3F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + (v1.getXF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + (v1.getYF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          + (v1.getZF() * r)));
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
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
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
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x0, y0, z0);
      final PVectorM3F<T> v2 = new PVectorM3F<T>(x0, y0, z0);

      Assert.assertTrue(PVectorM3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM3F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM3F<T> maximum = new PVectorM3F<T>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      final PVectorM3F<T> vo =
        PVectorM3F.clampMaximumByPVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      {
        final PVectorM3F<T> vr0 =
          PVectorM3F.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
      }
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> minimum = new PVectorM3F<T>(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      final PVectorM3F<T> vo =
        PVectorM3F.clampMinimumByPVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final PVectorM3F<T> vr0 =
          PVectorM3F.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
      }
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM3F<T> minimum = new PVectorM3F<T>(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> maximum = new PVectorM3F<T>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      final PVectorM3F<T> vo =
        PVectorM3F.clampByPVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final PVectorM3F<T> vr0 =
          PVectorM3F.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());

        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getZF() <= maximum);

      {
        PVectorM3F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getZF() <= maximum);
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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() >= minimum);

      {
        PVectorM3F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() >= minimum);
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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getZF() >= minimum);

      {
        PVectorM3F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() <= maximum);
        Assert.assertTrue(v.getZF() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorM3F<T> vb = new PVectorM3F<T>(5, 6, 7);
    final PVectorM3F<T> va = new PVectorM3F<T>(1, 2, 3);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());

    PVectorM3F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
  }

  @Override @Test public void testCopy2Correct()
  {
    final PVectorM3F<T> v0 =
      new PVectorM3F<T>(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final PVectorM3F<T> v1 = new PVectorM3F<T>();
    final PVectorM3F<T> v2 = new PVectorM3F<T>();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0, v1.getZF(), 0.0f);

    v2.copyFromTyped2F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(0, v1.getZF(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final PVectorM3F<T> v0 =
      new PVectorM3F<T>(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final PVectorM3F<T> v1 = new PVectorM3F<T>();
    final PVectorM3F<T> v2 = new PVectorM3F<T>();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);

    v2.copyFromTyped3F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v2.getZF(), 0.0f);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);
      PVectorM3F.normalizeInPlace(v0);

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);
      PVectorM3F.normalizeInPlace(v1);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.crossProduct(v0, v1, vr);
      PVectorM3F.normalizeInPlace(vr);

      final double dp0 = PVectorM3F.dotProduct(v0, vr);
      final double dp1 = PVectorM3F.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + PVectorM3F.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + PVectorM3F.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + PVectorM3F.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(PVectorM3F.almostEqual(
      ec,
      new PVectorM3F<T>(),
      new PVectorM3F<T>(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(0.0f, 1.0f, 0.0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(0.0f, 0.0f, 0.0f);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      PVectorM3F.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);

      Assert.assertTrue(PVectorM3F.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(10.0f, 10.0f, 10.0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorM3F<T> vpx = new PVectorM3F<T>(1.0f, 0.0f, 0.0f);
    final PVectorM3F<T> vmx = new PVectorM3F<T>(-1.0f, 0.0f, 0.0f);

    final PVectorM3F<T> vpy = new PVectorM3F<T>(0.0f, 1.0f, 0.0f);
    final PVectorM3F<T> vmy = new PVectorM3F<T>(0.0f, -1.0f, 0.0f);

    final PVectorM3F<T> vpz = new PVectorM3F<T>(0.0f, 0.0f, 1.0f);
    final PVectorM3F<T> vmz = new PVectorM3F<T>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(PVectorM3F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorM3F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(PVectorM3F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(PVectorM3F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final PVectorM3F<T> q = new PVectorM3F<T>(x, y, z);
      final double dp = PVectorM3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      final PVectorM3F<T> m1 = new PVectorM3F<T>();
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
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>(x, y, z);
      final PVectorM3F<T> m1 = new PVectorM3F<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorM3F<T> m0 = new PVectorM3F<T>();
    final PVectorM3F<T> m1 = new PVectorM3F<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      final PVectorM3F<T> m1 = new PVectorM3F<T>();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      final PVectorM3F<T> m1 = new PVectorM3F<T>();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3F<T> m0 = new PVectorM3F<T>();
      final PVectorM3F<T> m1 = new PVectorM3F<T>();
      m1.setZF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(1.0f, 2.0f, 3.0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);

      final PVectorM3F<T> vr0 = new PVectorM3F<T>();
      final PVectorM3F<T> vr1 = new PVectorM3F<T>();
      PVectorM3F.interpolateLinear(v0, v1, 0.0f, vr0);
      PVectorM3F.interpolateLinear(v0, v1, 1.0f, vr1);

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
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final double m = PVectorM3F.magnitude(v);
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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();
      PVectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM3F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(0.0f, 0.0f, 0.0f);
    final PVectorM3F<T> vr = PVectorM3F.normalizeInPlace(v);
    final double m = PVectorM3F.magnitude(vr);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(1.0f, 0.0f, 0.0f);
    final double m = PVectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(8.0f, 0.0f, 0.0f);

    {
      final double p = PVectorM3F.dotProduct(v, v);
      final double q = PVectorM3F.magnitudeSquared(v);
      final double r = PVectorM3F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(0.0f, 0.0f, 0.0f);
    final double m = PVectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(8.0f, 0.0f, 0.0f);
    final PVectorM3F<T> out = new PVectorM3F<T>();
    final PVectorM3F<T> vr = PVectorM3F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = PVectorM3F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorM3F<T> qr = new PVectorM3F<T>();
    final PVectorM3F<T> q = new PVectorM3F<T>(0, 0, 0);
    PVectorM3F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getZF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(0, 1, 0);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(0.5f, 0.5f, 0);

    final Pair<PVectorM3F<T>, PVectorM3F<T>> r =
      PVectorM3F.orthoNormalize(v0, v1);

    Assert.assertEquals(new PVectorM3F<T>(0, 1, 0), r.getLeft());
    Assert.assertEquals(new PVectorM3F<T>(1, 0, 0), r.getRight());
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final PVectorM3F<T> v0 = new PVectorM3F<T>(0f, 1f, 0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(0.5f, 0.5f, 0f);

    PVectorM3F.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new PVectorM3F<T>(0, 1, 0), v0);
    Assert.assertEquals(new PVectorM3F<T>(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorM3F<T> p = new PVectorM3F<T>(1.0f, 0.0f, 0.0f);
      final PVectorM3F<T> q = new PVectorM3F<T>(0.0f, 1.0f, 0.0f);
      final PVectorM3F<T> r = new PVectorM3F<T>();
      final PVectorM3F<T> u = PVectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3F.magnitude(u) == 0.0);
    }

    {
      final PVectorM3F<T> p = new PVectorM3F<T>(-1.0f, 0.0f, 0.0f);
      final PVectorM3F<T> q = new PVectorM3F<T>(0.0f, 1.0f, 0.0f);
      final PVectorM3F<T> r = new PVectorM3F<T>();
      final PVectorM3F<T> u = PVectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3F.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final PVectorM3F<T> out = new PVectorM3F<T>();
    final PVectorM3F<T> v0 = new PVectorM3F<T>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    final PVectorM3F<T> ov0 = PVectorM3F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    final PVectorM3F<T> ov1 = PVectorM3F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();

      PVectorM3F.scale(v, 1.0f, vr);

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

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();

        PVectorM3F.scaleInPlace(v, 1.0f);

        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
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
      final PVectorM3F<T> v = new PVectorM3F<T>(x, y, z);

      final PVectorM3F<T> vr = new PVectorM3F<T>();

      PVectorM3F.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getZF(),
        0.0f));

      {
        PVectorM3F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final PVectorM3F<T> v = new PVectorM3F<T>(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[PVectorM3F 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v0 = new PVectorM3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM3F<T> v1 = new PVectorM3F<T>(x1, y1, z1);

      final PVectorM3F<T> vr0 = new PVectorM3F<T>();
      PVectorM3F.subtract(v0, v1, vr0);

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

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        PVectorM3F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          - v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          - v1.getYF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getZF(), orig_z
          - v1.getZF()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final PVectorM3F<T> out = new PVectorM3F<T>();
    final PVectorM3F<T> v0 = new PVectorM3F<T>(1.0f, 1.0f, 1.0f);
    final PVectorM3F<T> v1 = new PVectorM3F<T>(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);

    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final PVectorM3F<T> ov0 = PVectorM3F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);

    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final PVectorM3F<T> ov1 = PVectorM3F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0);
    Assert.assertTrue(ov1.getYF() == 0.0);
    Assert.assertTrue(ov1.getZF() == 0.0);

    Assert.assertTrue(v0.getXF() == 0.0);
    Assert.assertTrue(v0.getYF() == 0.0);
    Assert.assertTrue(v0.getZF() == 0.0);

    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);
  }
}
