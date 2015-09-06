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
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jtensors.VectorM3F;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3FContract extends VectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getZF()), vr.getZF()));
    }
  }

  protected abstract VectorM3F newVectorM3F(
    final float x,
    final float y,
    final float z);

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();

      VectorM3F.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_x), v.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_y), v.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_z), v.getZF()));
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
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);

      final VectorM3F vr0 = this.newVectorM3F();
      VectorM3F.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() + v1.getZF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        VectorM3F.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z + v1.getZF()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM3F out = this.newVectorM3F();
    final VectorM3F v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final VectorM3F ov0 = VectorM3F.add(v0, v1, out);

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

    final VectorM3F ov1 = VectorM3F.addInPlace(v0, v1);

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
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);

      final float r = (float) (Math.random() * max);

      final VectorM3F vr0 = this.newVectorM3F();
      VectorM3F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + (v1.getYF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() + (v1.getZF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        VectorM3F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + (v1.getXF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + (v1.getYF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z + (v1.getZF() * r)));
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
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
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
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);
      final VectorM3F v1 = this.newVectorM3F(x0, y0, z0);
      final VectorM3F v2 = this.newVectorM3F(x0, y0, z0);

      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM3F v = this.newVectorM3F(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      final VectorM3F vo = VectorM3F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      {
        final VectorM3F vr0 = VectorM3F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      final VectorM3F vo = VectorM3F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final VectorM3F vr0 = VectorM3F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      final VectorM3F vo = VectorM3F.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final VectorM3F vr0 =
          VectorM3F.clampByVectorInPlace(v, minimum, maximum);
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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getZF() <= maximum);

      {
        VectorM3F.clampMaximumInPlace(v, maximum);
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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() >= minimum);

      {
        VectorM3F.clampMinimumInPlace(v, minimum);
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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getZF() >= minimum);

      {
        VectorM3F.clampInPlace(v, minimum, maximum);

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
    final VectorM3F vb = this.newVectorM3F(5, 6, 7);
    final VectorM3F va = this.newVectorM3F(1, 2, 3);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());

    VectorM3F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM3F v0 = this.newVectorM3F(
      (float) Math.random() * Float.MAX_VALUE,
      (float) Math.random() * Float.MAX_VALUE,
      (float) Math.random() * Float.MAX_VALUE);
    final VectorM3F v1 = this.newVectorM3F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0, v1.getZF(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final VectorM3F v0 = this.newVectorM3F(
      (float) Math.random() * Float.MAX_VALUE,
      (float) Math.random() * Float.MAX_VALUE,
      (float) Math.random() * Float.MAX_VALUE);
    final VectorM3F v1 = this.newVectorM3F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);
      VectorM3F.normalizeInPlace(v0);

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);
      VectorM3F.normalizeInPlace(v1);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.crossProduct(v0, v1, vr);
      VectorM3F.normalizeInPlace(vr);

      final double dp0 = VectorM3F.dotProduct(v0, vr);
      final double dp1 = VectorM3F.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + VectorM3F.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + VectorM3F.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + VectorM3F.magnitude(vr));
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
    Assert.assertTrue(
      VectorM3F.almostEqual(
        ec, this.newVectorM3F(), this.newVectorM3F(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final VectorM3F v0 = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final VectorM3F v1 = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, VectorM3F.distance(c, v0, v1), 1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);

      Assert.assertTrue(VectorM3F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM3F v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);
    final VectorM3F v1 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0);
      Assert.assertTrue(v1.getYF() == 10.0);
      Assert.assertTrue(v1.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM3F vpx = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final VectorM3F vmx = this.newVectorM3F(-1.0f, 0.0f, 0.0f);

    final VectorM3F vpy = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final VectorM3F vmy = this.newVectorM3F(0.0f, -1.0f, 0.0f);

    final VectorM3F vpz = this.newVectorM3F(0.0f, 0.0f, 1.0f);
    final VectorM3F vmz = this.newVectorM3F(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorM3F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final VectorM3F q = this.newVectorM3F(x, y, z);
      final double dp = VectorM3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3F v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0);
      Assert.assertTrue(v0.getYF() == 10.0);
      Assert.assertTrue(v0.getZF() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM3F m0 = this.newVectorM3F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3F m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3F m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3F m0 = this.newVectorM3F();
      final VectorM3F m1 = this.newVectorM3F();
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
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = this.newVectorM3F(x, y, z);
      final VectorM3F m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM3F m0 = this.newVectorM3F();
    final VectorM3F m1 = this.newVectorM3F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3F m0 = this.newVectorM3F();
      final VectorM3F m1 = this.newVectorM3F();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3F m0 = this.newVectorM3F();
      final VectorM3F m1 = this.newVectorM3F();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3F m0 = this.newVectorM3F();
      final VectorM3F m1 = this.newVectorM3F();
      m1.setZF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM3F v0 = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    final VectorM3F v1 = new VectorM3F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);

      final VectorM3F vr0 = this.newVectorM3F();
      final VectorM3F vr1 = this.newVectorM3F();
      VectorM3F.interpolateLinear(c, v0, v1, 0.0f, vr0);
      VectorM3F.interpolateLinear(c, v0, v1, 1.0f, vr1);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v0.getXF(), vr0.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v0.getYF(), vr0.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v0.getZF(), vr0.getZF()));

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getXF(), vr1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getYF(), vr1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getZF(), vr1.getZF()));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final double m = VectorM3F.magnitude(v);
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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();
      VectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM3F v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final VectorM3F vr = VectorM3F.normalizeInPlace(v);
    final double m = VectorM3F.magnitude(vr);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorM3F v = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final double m = VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM3F v = this.newVectorM3F(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorM3F.dotProduct(v, v);
      final double q = VectorM3F.magnitudeSquared(v);
      final double r = VectorM3F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM3F v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final double m = VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM3F v0 = this.newVectorM3F(8.0f, 0.0f, 0.0f);
    final VectorM3F out = this.newVectorM3F();
    final VectorM3F vr = VectorM3F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM3F qr = this.newVectorM3F();
    final VectorM3F q = this.newVectorM3F(0, 0, 0);
    VectorM3F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getZF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final VectorM3F v0 = this.newVectorM3F(0, 1, 0);
    final VectorM3F v1 = this.newVectorM3F(0.5f, 0.5f, 0);
    final VectorM3F v0_out = new VectorM3F();
    final VectorM3F v1_out = new VectorM3F();

    VectorM3F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3F(0, 1, 0), v0_out);
    Assert.assertEquals(this.newVectorM3F(1, 0, 0), v1_out);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final VectorM3F v0 = this.newVectorM3F(0f, 1f, 0f);
    final VectorM3F v1 = this.newVectorM3F(0.5f, 0.5f, 0f);

    VectorM3F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3F(0, 1, 0), v0);
    Assert.assertEquals(this.newVectorM3F(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM3F p = this.newVectorM3F(1.0f, 0.0f, 0.0f);
      final VectorM3F q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = this.newVectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }

    {
      final VectorM3F p = this.newVectorM3F(-1.0f, 0.0f, 0.0f);
      final VectorM3F q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = this.newVectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM3F out = this.newVectorM3F();
    final VectorM3F v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    final VectorM3F ov0 = VectorM3F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    final VectorM3F ov1 = VectorM3F.scaleInPlace(v0, 2.0f);

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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();

      VectorM3F.scale(v, 1.0f, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getZF(), vr.getZF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();

        VectorM3F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
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
      final VectorM3F v = this.newVectorM3F(x, y, z);

      final VectorM3F vr = this.newVectorM3F();

      VectorM3F.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getZF(), 0.0f));

      {
        VectorM3F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM3F v = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorM3F 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = this.newVectorM3F(x1, y1, z1);

      final VectorM3F vr0 = this.newVectorM3F();
      VectorM3F.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() - v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() - v1.getZF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        VectorM3F.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x - v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y - v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z - v1.getZF()));
      }
    }
  }

  protected abstract VectorM3F newVectorM3F();

  @Override @Test public void testSubtractMutation()
  {
    final VectorM3F out = this.newVectorM3F();
    final VectorM3F v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(out.getZF() == 0.0);

    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v0.getZF() == 1.0);

    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
    Assert.assertTrue(v1.getZF() == 1.0);

    final VectorM3F ov0 = VectorM3F.subtract(v0, v1, out);

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

    final VectorM3F ov1 = VectorM3F.subtractInPlace(v0, v1);

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
