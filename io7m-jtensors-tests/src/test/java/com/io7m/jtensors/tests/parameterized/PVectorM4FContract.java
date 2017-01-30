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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.parameterized.PVector4FType;
import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM4FContract<T, V extends PVector4FType<T>>
{
  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract V newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w);

  protected abstract V newVectorM4FFrom(
    final V v);

  protected abstract V newVectorM4F();

  @Test
  public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      PVectorM4F.absolute(v, vr);

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

  @Test
  public final void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();
      final float orig_w = v.getWF();

      PVectorM4F.absoluteInPlace(v);

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

  @Test
  public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (getRandom() * (double) max);
      final float y0 = (float) (getRandom() * (double) max);
      final float z0 = (float) (getRandom() * (double) max);
      final float w0 = (float) (getRandom() * (double) max);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) max);
      final float y1 = (float) (getRandom() * (double) max);
      final float z1 = (float) (getRandom() * (double) max);
      final float w1 = (float) (getRandom() * (double) max);
      final V v1 = this.newVectorM4F(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4F();
      PVectorM4F.add(v0, v1, vr0);

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
        PVectorM4F.addInPlace(v0, v1);

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

  @Test
  public final void testAddMutation()
  {
    final V out = this.newVectorM4F();
    final V v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final V v1 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

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

    final V ov0 = PVectorM4F.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
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

    final V ov1 = PVectorM4F.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
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

  @Test
  public final void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (getRandom() * (double) max);
      final float y0 = (float) (getRandom() * (double) max);
      final float z0 = (float) (getRandom() * (double) max);
      final float w0 = (float) (getRandom() * (double) max);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) max);
      final float y1 = (float) (getRandom() * (double) max);
      final float z1 = (float) (getRandom() * (double) max);
      final float w1 = (float) (getRandom() * (double) max);
      final V v1 = this.newVectorM4F(x1, y1, z1, w1);

      final float r = (float) (getRandom() * (double) max);

      final V vr0 = this.newVectorM4F();
      PVectorM4F.addScaled(v0, v1, (double) r, vr0);

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
        PVectorM4F.addScaledInPlace(v0, v1, (double) r);

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

  @Test
  public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, z, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, q, z, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, q, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, z, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, z, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, q, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, z, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, q, w);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, z, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, q, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, q, q, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, q, q);
      Assert.assertFalse(PVectorM4F.almostEqual(ec, m0, m1));
    }
  }

  @Test
  public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);
      final V v1 = this.newVectorM4F(x0, y0, z0, w0);
      final V v2 = this.newVectorM4F(x0, y0, z0, w0);

      Assert.assertTrue(PVectorM4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM4F.almostEqual(ec, v0, v2));
    }
  }

  @Test
  public final void testCheckInterface()
  {
    final V v = this.newVectorM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
    Assert.assertEquals((double) v.getZF(), (double) v.getZF(), 0.0);
    Assert.assertEquals((double) v.getWF(), (double) v.getWF(), 0.0);
  }

  @Test
  public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float max_y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float max_z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float max_w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V maximum = this.newVectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      final V vo = PVectorM4F.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());
      Assert.assertTrue(vr.getWF() <= maximum.getWF());

      {
        final V vr0 = PVectorM4F.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
        Assert.assertTrue(v.getWF() <= maximum.getWF());
      }
    }
  }

  @Test
  public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float min_y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float min_z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float min_w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V minimum = this.newVectorM4F(min_x, min_y, min_z, min_w);

      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      final V vo = PVectorM4F.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());
      Assert.assertTrue(vr.getWF() >= minimum.getWF());

      {
        final V vr0 = PVectorM4F.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
        Assert.assertTrue(v.getWF() >= minimum.getWF());
      }
    }
  }

  @Test
  public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float min_y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float min_z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float min_w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V minimum = this.newVectorM4F(min_x, min_y, min_z, min_w);

      final float max_x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float max_y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float max_z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float max_w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V maximum = this.newVectorM4F(max_x, max_y, max_z, max_w);

      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      final V vo = PVectorM4F.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
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
        final V vr0 = PVectorM4F.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
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

  @Test
  public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum =
        (float) (getRandom() * (double) Float.MIN_VALUE);

      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      PVectorM4F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getWF() <= maximum);

      {
        PVectorM4F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getZF() <= maximum);
        Assert.assertTrue(v.getWF() <= maximum);
      }
    }
  }

  @Test
  public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum =
        (float) (getRandom() * (double) Float.MAX_VALUE);

      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float z = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float w = (float) (getRandom() * (double) Float.MIN_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      PVectorM4F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        PVectorM4F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
        Assert.assertTrue(v.getZF() >= minimum);
        Assert.assertTrue(v.getWF() >= minimum);
      }
    }
  }

  @Test
  public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum =
        (float) (getRandom() * (double) Float.MIN_VALUE);
      final float maximum =
        (float) (getRandom() * (double) Float.MAX_VALUE);

      final float x = (float) (getRandom() * (double) Float.MIN_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      PVectorM4F.clamp(v, (double) minimum, (double) maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);
      Assert.assertTrue(vr.getZF() <= maximum);
      Assert.assertTrue(vr.getZF() >= minimum);
      Assert.assertTrue(vr.getWF() <= maximum);
      Assert.assertTrue(vr.getWF() >= minimum);

      {
        PVectorM4F.clampInPlace(v, minimum, maximum);

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

  @Test
  public final void testCopy()
  {
    final V vb = this.newVectorM4F(5.0F, 6.0F, 7.0F, 8.0F);
    final V va = this.newVectorM4F(1.0F, 2.0F, 3.0F, 4.0F);

    Assert.assertNotEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertNotEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertNotEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
    Assert.assertNotEquals((double) vb.getWF(), (double) va.getWF(), 0.0);

    PVectorM4F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
    Assert.assertEquals((double) vb.getWF(), (double) va.getWF(), 0.0);
  }

  @Test
  public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM4F(
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE);
    final V v1 = this.newVectorM4F();
    final V v2 = this.newVectorM4F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);
    Assert.assertEquals(1.0F, v1.getWF(), 0.0f);

    v2.copyFromTyped2F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v2.getZF(), 0.0f);
    Assert.assertEquals(1.0F, v2.getWF(), 0.0f);
  }

  @Test
  public final void testCopy3Correct()
  {
    final V v0 = this.newVectorM4F(
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE);
    final V v1 = this.newVectorM4F();
    final V v2 = this.newVectorM4F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v1.getWF(), 0.0f);

    v2.copyFromTyped3F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v2.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v2.getWF(), 0.0f);
  }

  @Test
  public final void testCopy4Correct()
  {
    final V v0 = this.newVectorM4F(
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE,
      (float) getRandom() * Float.MAX_VALUE);
    final V v1 = this.newVectorM4F();
    final V v2 = this.newVectorM4F();

    v1.copyFrom4F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v1.getWF(), 0.0f);

    v2.copyFromTyped4F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v2.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v2.getWF(), 0.0f);
  }

  @Test
  public final void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      PVectorM4F.almostEqual(
        ec, this.newVectorM4F(), this.newVectorM4F(0.0F, 0.0F, 0.0F, 1.0F)));
  }

  @Test
  public final void testDistance()
  {
    final PVectorM4F.ContextPVM4F c = new PVectorM4F.ContextPVM4F();
    final V v0 = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final V v1 = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, PVectorM4F.distance(c, v0, v1), 1.0));
  }

  @Test
  public final void testDistanceOrdering()
  {
    final PVectorM4F.ContextPVM4F c = new PVectorM4F.ContextPVM4F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v1 = this.newVectorM4F(x1, y1, z1, w1);

      Assert.assertTrue(PVectorM4F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test
  public final void testDotProduct()
  {
    final V v0 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final V v1 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM4F.dotProduct(v0, v1);
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
      final double p = PVectorM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = PVectorM4F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test
  public final void testDotProductPerpendicular()
  {
    final V vpx = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final V vmx = this.newVectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);

    final V vpy = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final V vmy = this.newVectorM4F(0.0f, -1.0f, 0.0f, 0.0f);

    final V vpz = this.newVectorM4F(0.0f, 0.0f, 1.0f, 0.0f);
    final V vmz = this.newVectorM4F(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertEquals(0.0, PVectorM4F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, PVectorM4F.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, PVectorM4F.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, PVectorM4F.dotProduct(vmy, vmz), 0.0);
  }

  @Test
  public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final float z = (float) getRandom();
      final float w = (float) getRandom();
      final V q = this.newVectorM4F(x, y, z, w);
      final double dp = PVectorM4F.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test
  public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = PVectorM4F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test
  public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM4F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM4F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM4F();
      final V m1 = this.newVectorM4F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test
  public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4F(x, y, z, w);
      final V m1 = this.newVectorM4F(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test
  public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM4F();
    final V m1 = this.newVectorM4F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test
  public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM4F();
      final V m1 = this.newVectorM4F();
      m1.setXF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM4F();
      final V m1 = this.newVectorM4F();
      m1.setYF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM4F();
      final V m1 = this.newVectorM4F();
      m1.setZF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM4F();
      final V m1 = this.newVectorM4F();
      m1.setWF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }
  }

  @Test
  public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final V v1 = this.newVectorM4FFrom(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
    Assert.assertEquals((double) v1.getZF(), (double) v0.getZF(), 0.0);
    Assert.assertEquals((double) v1.getWF(), (double) v0.getWF(), 0.0);
  }

  @Test
  public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final PVectorM4F.ContextPVM4F c = new PVectorM4F.ContextPVM4F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v1 = this.newVectorM4F(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4F();
      final V vr1 = this.newVectorM4F();
      PVectorM4F.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM4F.interpolateLinear(c, v0, v1, 1.0, vr1);

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

  @Test
  public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final double m = PVectorM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test
  public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
          / 2.0));
      final float y =
        (float) (getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
          / 2.0));
      final float z =
        (float) (getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
          / 2.0));
      final float w =
        (float) (getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
          / 2.0));
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();
      PVectorM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM4F.magnitude(vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test
  public final void testMagnitudeNormalizeZero()
  {
    final V v = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final V vr = PVectorM4F.normalizeInPlace(v);
    final double m = PVectorM4F.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test
  public final void testMagnitudeOne()
  {
    final V v = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = PVectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test
  public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = PVectorM4F.dotProduct(v, v);
      final double q = PVectorM4F.magnitudeSquared(v);
      final double r = PVectorM4F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test
  public final void testMagnitudeZero()
  {
    final V v = this.newVectorM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = PVectorM4F.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test
  public final void testNormalizeSimple()
  {
    final V v0 = this.newVectorM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final V out = this.newVectorM4F();
    final V vr = PVectorM4F.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = PVectorM4F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test
  public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final V qr = this.newVectorM4F();
    final V q = this.newVectorM4F(0.0F, 0.0F, 0.0F, 0.0F);
    PVectorM4F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getWF()));
  }

  @Test
  public final void testOrthonormalize()
  {
    final PVectorM4F.ContextPVM4F c = new PVectorM4F.ContextPVM4F();
    final V v0 = this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F);
    final V v0_out = this.newVectorM4F();
    final V v1 = this.newVectorM4F(0.5f, 0.5f, 0.0F, 0.0F);
    final V v1_out = this.newVectorM4F();

    PVectorM4F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F), v0_out);
    Assert.assertEquals(this.newVectorM4F(1.0F, 0.0F, 0.0F, 0.0F), v1_out);
  }

  @Test
  public final void testOrthonormalizeMutation()
  {
    final PVectorM4F.ContextPVM4F c = new PVectorM4F.ContextPVM4F();
    final V v0 = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
    final V v1 = this.newVectorM4F(0.5f, 0.5f, 0.0f, 0.0f);

    PVectorM4F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM4F(0.0F, 1.0F, 0.0F, 0.0F), v0);
    Assert.assertEquals(this.newVectorM4F(1.0F, 0.0F, 0.0F, 0.0F), v1);
  }

  @Test
  public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM4F(1.0f, 0.0f, 0.0f, 0.0f);
      final V q = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final V r = this.newVectorM4F();
      final V u = PVectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM4F.magnitude(u), 0.0);
    }

    {
      final V p = this.newVectorM4F(-1.0f, 0.0f, 0.0f, 0.0f);
      final V q = this.newVectorM4F(0.0f, 1.0f, 0.0f, 0.0f);
      final V r = this.newVectorM4F();
      final V u = PVectorM4F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM4F.magnitude(u), 0.0);
    }
  }

  @Test
  public final void testScaleMutation()
  {
    final V out = this.newVectorM4F();
    final V v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final V ov0 = PVectorM4F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final V ov1 = PVectorM4F.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
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

  @Test
  public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();

      PVectorM4F.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(ec, v.getYF(), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(ec, v.getZF(), vr.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(ec, v.getWF(), vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        PVectorM4F.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), orig_w));
      }
    }
  }

  @Test
  public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v = this.newVectorM4F(x, y, z, w);

      final V vr = this.newVectorM4F();

      PVectorM4F.scale(v, 0.0, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));

      {
        PVectorM4F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getWF(), 0.0f));
      }
    }
  }

  @Test
  public final void testString()
  {
    final V v = this.newVectorM4F(1.0f, 2.0f, 3.0f, 4.0f);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0 4.0]"));
  }

  @Test
  public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v0 = this.newVectorM4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final V v1 = this.newVectorM4F(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4F();
      PVectorM4F.subtract(v0, v1, vr0);

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
        PVectorM4F.subtractInPlace(v0, v1);

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

  @Test
  public final void testSubtractMutation()
  {
    final V out = this.newVectorM4F();
    final V v0 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final V v1 = this.newVectorM4F(1.0f, 1.0f, 1.0f, 1.0f);

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

    final V ov0 = PVectorM4F.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
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

    final V ov1 = PVectorM4F.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
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
