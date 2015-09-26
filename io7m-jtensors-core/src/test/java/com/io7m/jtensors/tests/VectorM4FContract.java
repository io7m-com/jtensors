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
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.Vector4FType;
import com.io7m.jtensors.VectorM4F;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4FContract<T extends Vector4FType>
{
  protected abstract T newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w);

  protected abstract T newVectorM4F();

  protected abstract T newVectorM4FFrom(final T v0);

  @Test public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      VectorM4F.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getZF()), vr.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getWF()), vr.getWF()));
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();
      final float orig_w = v.getWF();

      VectorM4F.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_x), v.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_y), v.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_z), v.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(orig_w), v.getWF()));
    }
  }

  @Test public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (this.getRandom() * (double) max);
      final float y0 = (float) (this.getRandom() * (double) max);
      final float z0 = (float) (this.getRandom() * (double) max);
      final float w0 = (float) (this.getRandom() * (double) max);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) max);
      final float y1 = (float) (this.getRandom() * (double) max);
      final float z1 = (float) (this.getRandom() * (double) max);
      final float w1 = (float) (this.getRandom() * (double) max);
      final T v1 = this.newVectorM4F(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4F();
      VectorM4F.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() + v1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getWF(), v0.getWF() + v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z + v1.getZF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getWF(), orig_w + v1.getWF()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM4F();
    final T v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final T v1 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov0 = VectorM4F.add(v0, v1, out);

    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov1 = VectorM4F.addInPlace(v0, v1);

    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (this.getRandom() * (double) max);
      final float y0 = (float) (this.getRandom() * (double) max);
      final float z0 = (float) (this.getRandom() * (double) max);
      final float w0 = (float) (this.getRandom() * (double) max);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) max);
      final float y1 = (float) (this.getRandom() * (double) max);
      final float z1 = (float) (this.getRandom() * (double) max);
      final float w1 = (float) (this.getRandom() * (double) max);
      final T v1 = this.newVectorM4F(x1, y1, z1, w1);

      final float r = (float) (this.getRandom() * (double) max);

      final T vr0 = this.newVectorM4F();
      VectorM4F.addScaled(v0, v1, (double) r, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + (v1.getYF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() + (v1.getZF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getWF(), v0.getWF() + (v1.getWF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.addScaledInPlace(v0, v1, (double) r);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + (v1.getXF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + (v1.getYF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z + (v1.getZF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getWF(), orig_w + (v1.getWF() * r)));
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) this.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, q, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, z, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, q, w);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, z, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, q, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, q, q);
      Assert.assertFalse(VectorM4F.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);
      final T v1 = this.newVectorM4F(x0, y0, z0, w0);
      final T v2 = this.newVectorM4F(x0, y0, z0, w0);

      Assert.assertTrue(VectorM4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM4F.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
    Assert.assertEquals((double) v.getZF(), (double) v.getZF(), 0.0);
    Assert.assertEquals((double) v.getWF(), (double) v.getWF(), 0.0);
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float max_y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float max_z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float max_w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T maximum = this.newVectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      final T vo = VectorM4F.clampMaximumByVector(v, maximum, vr);

      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());

      {
        final T vr0 = VectorM4F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
        Assert.assertTrue(v.getWF() <= maximum.getWF());
      }
    }
  }

  protected double getRandom()
  {
    return Math.random();
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float min_y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float min_z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float min_w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T minimum = this.newVectorM4F(min_x, min_y, min_z, min_w);

      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      final T vo = VectorM4F.clampMinimumByVector(v, minimum, vr);

      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final T vr0 = VectorM4F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
        Assert.assertTrue(v.getWF() >= minimum.getWF());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float min_y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float min_z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float min_w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T minimum = this.newVectorM4F(min_x, min_y, min_z, min_w);

      final float max_x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float max_y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float max_z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float max_w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T maximum = this.newVectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      final T vo = VectorM4F.clampByVector(v, minimum, maximum, vr);

      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final T vr0 = VectorM4F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertSame(v, vr0);
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

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum =
        (float) (this.getRandom() * (double) Float.MIN_VALUE);

      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
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

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum =
        (float) (this.getRandom() * (double) Float.MAX_VALUE);

      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
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

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum =
        (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float maximum =
        (float) (this.getRandom() * (double) Float.MAX_VALUE);

      final float x = (float) (this.getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      VectorM4F.clamp(v, (double) minimum, (double) maximum, vr);

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

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM4F(5.0F, 6.0F, 7.0F, 8.0F);
    final T va = this.newVectorM4F(1.0F, 2.0F, 3.0F, 4.0F);

    Assert.assertNotEquals(vb.getXF(), va.getXF());
    Assert.assertNotEquals(vb.getYF(), va.getYF());
    Assert.assertNotEquals(vb.getZF(), va.getZF());
    Assert.assertNotEquals(vb.getWF(), va.getWF());

    VectorM4F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
    Assert.assertEquals((double) vb.getWF(), (double) va.getWF(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM4F(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newVectorM4F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);
    Assert.assertEquals(1.0F, v1.getWF(), 0.0f);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM4F(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newVectorM4F();

    v1.copyFrom4F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v1.getWF(), 0.0f);
  }

  @Test public final void testCopy4Correct()
  {
    final T v0 = this.newVectorM4F(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newVectorM4F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v1.getWF(), 0.0f);
  }

  @Test public final void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      VectorM4F.almostEqual(
        ec, this.newVectorM4F(), this.newVectorM4F(0.0F, 0.0F, 0.0F, 1.0F)));
  }

  @Test public final void testDistance()
  {
    final VectorM4F.ContextVM4F c = new VectorM4F.ContextVM4F();
    final T v0 = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final T v1 = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, VectorM4F.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM4F.ContextVM4F c = new VectorM4F.ContextVM4F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newVectorM4F(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final T v1 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = VectorM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = VectorM4F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final T vmx = this.newVectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);

    final T vpy = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final T vmy = this.newVectorM4F(0.0f, -1.0f, 0.0f, 0.0f);

    final T vpz = this.newVectorM4F(0.0f, 0.0f, 1.0f, 0.0f);
    final T vmz = this.newVectorM4F(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertEquals(0.0, VectorM4F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, VectorM4F.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, VectorM4F.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, VectorM4F.dotProduct(vmy, vmz), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) this.getRandom();
      final float y = (float) this.getRandom();
      final float z = (float) this.getRandom();
      final float w = (float) this.getRandom();
      final T q = this.newVectorM4F(x, y, z, w);
      final double dp = VectorM4F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = VectorM4F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM4F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM4F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM4F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM4F();
      final T m1 = this.newVectorM4F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) this.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4F(x, y, z, w);
      final T m1 = this.newVectorM4F(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM4F();
    final T m1 = this.newVectorM4F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM4F();
      final T m1 = this.newVectorM4F();
      m1.setXF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4F();
      final T m1 = this.newVectorM4F();
      m1.setYF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4F();
      final T m1 = this.newVectorM4F();
      m1.setZF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4F();
      final T m1 = this.newVectorM4F();
      m1.setWF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final T v1 = this.newVectorM4FFrom(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
    Assert.assertEquals((double) v1.getZF(), (double) v0.getZF(), 0.0);
    Assert.assertEquals((double) v1.getWF(), (double) v0.getWF(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final VectorM4F.ContextVM4F c = new VectorM4F.ContextVM4F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newVectorM4F(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4F();
      final T vr1 = this.newVectorM4F();
      VectorM4F.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM4F.interpolateLinear(c, v0, v1, 1.0, vr1);

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
          ec, v0.getWF(), vr0.getWF()));

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getXF(), vr1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getYF(), vr1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getZF(), vr1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getWF(), vr1.getWF()));
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final double m = VectorM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float y =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float z =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float w =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();
      VectorM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM4F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final T v = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final T vr = VectorM4F.normalizeInPlace(v);
    final double m = VectorM4F.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final T v = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = VectorM4F.dotProduct(v, v);
      final double q = VectorM4F.magnitudeSquared(v);
      final double r = VectorM4F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final T out = this.newVectorM4F();
    final T vr = VectorM4F.normalize(v0, out);

    Assert.assertSame(out, vr);

    final double m = VectorM4F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T qr = this.newVectorM4F();
    final T q = this.newVectorM4F(0.0F, 0.0F, 0.0F, 0.0F);
    VectorM4F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getWF()));
  }

  @Test public final void testOrthonormalize()
  {
    final VectorM4F.ContextVM4F c = new VectorM4F.ContextVM4F();
    final T v0 = this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F);
    final T v1 = this.newVectorM4F(0.5f, 0.5f, 0.0F, 0.0F);
    final T v0_out = this.newVectorM4F();
    final T v1_out = this.newVectorM4F();

    VectorM4F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F), v0_out);
    Assert.assertEquals(this.newVectorM4F(1.0F, 0.0F, 0.0F, 0.0F), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final VectorM4F.ContextVM4F c = new VectorM4F.ContextVM4F();
    final T v0 = this.newVectorM4F(0f, 1f, 0f, 0f);
    final T v1 = this.newVectorM4F(0.5f, 0.5f, 0f, 0f);

    VectorM4F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F), v0);
    Assert.assertEquals(this.newVectorM4F(1.0F, 0.0F, 0.0F, 0.0F), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
      final T q = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final T r = this.newVectorM4F();
      final T u = VectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM4F.magnitude(u), 0.0);
    }

    {
      final T p = this.newVectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);
      final T q = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final T r = this.newVectorM4F();
      final T u = VectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM4F.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM4F();
    final T v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final T ov0 = VectorM4F.scale(v0, 2.0, out);

    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final T ov1 = VectorM4F.scaleInPlace(v0, 2.0);

    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getWF(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();

      VectorM4F.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getZF(), vr.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getWF(), vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        VectorM4F.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), orig_w));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newVectorM4F(x, y, z, w);

      final T vr = this.newVectorM4F();

      VectorM4F.scale(v, 0.0, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));

      {
        VectorM4F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), 0.0f));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0 4.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newVectorM4F(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4F();
      VectorM4F.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() - v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getZF(), v0.getZF() - v1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getWF(), v0.getWF() - v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        VectorM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x - v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y - v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getZF(), orig_z - v1.getZF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getWF(), orig_w - v1.getWF()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM4F();
    final T v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final T v1 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov0 = VectorM4F.subtract(v0, v1, out);

    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov1 = VectorM4F.subtractInPlace(v0, v1);

    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);
  }
}
