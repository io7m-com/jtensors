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
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jtensors.parameterized.PVector3FType;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3FContract<T, V extends PVector3FType<T>>
{

  protected static double getRandom()
  {
    return Math.random();
  }

  protected static float getRandomSmall()
  {
    return (float) (PVectorM3FContract.getRandom() * (double) Float.MIN_VALUE);
  }

  protected static float getRandomLarge()
  {
    return (float) (PVectorM3FContract.getRandom() * (double) Float.MAX_VALUE);
  }

  protected abstract V newVectorM3F(final V v0);

  protected abstract V newVectorM3F();

  protected abstract V newVectorM3F(
    final float x,
    final float y,
    final float z);

  @Test public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomSmall();
      final float z = PVectorM3FContract.getRandomSmall();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
      PVectorM3F.absolute(v, vr);

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

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomSmall();
      final float z = PVectorM3FContract.getRandomSmall();
      final V v = this.newVectorM3F(x, y, z);

      final float orig_x = v.getXF();
      final float orig_y = v.getYF();
      final float orig_z = v.getZF();

      PVectorM3F.absoluteInPlace(v);

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

  @Test public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float y0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float z0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final V v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float y1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float z1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final V v1 = this.newVectorM3F(x1, y1, z1);

      final V vr0 = this.newVectorM3F();
      PVectorM3F.add(v0, v1, vr0);

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
        PVectorM3F.addInPlace(v0, v1);

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

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM3F();
    final V v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final V v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final V ov0 = PVectorM3F.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final V ov1 = PVectorM3F.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float y0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float z0 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final V v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float y1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final float z1 = (float) (PVectorM3FContract.getRandom() * (double) max);
      final V v1 = this.newVectorM3F(x1, y1, z1);

      final float r = (float) (PVectorM3FContract.getRandom() * (double) max);

      final V vr0 = this.newVectorM3F();
      PVectorM3F.addScaled(v0, v1, (double) r, vr0);

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
        PVectorM3F.addScaledInPlace(v0, v1, (double) r);

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

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) PVectorM3FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(PVectorM3F.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = PVectorM3FContract.getRandomLarge();
      final float y0 = PVectorM3FContract.getRandomLarge();
      final float z0 = PVectorM3FContract.getRandomLarge();
      final V v0 = this.newVectorM3F(x0, y0, z0);
      final V v1 = this.newVectorM3F(x0, y0, z0);
      final V v2 = this.newVectorM3F(x0, y0, z0);

      Assert.assertTrue(PVectorM3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM3F.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM3F(3.0f, 5.0f, 7.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
    Assert.assertEquals((double) v.getZF(), (double) v.getZF(), 0.0);
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = PVectorM3FContract.getRandomSmall();
      final float max_y = PVectorM3FContract.getRandomSmall();
      final float max_z = PVectorM3FContract.getRandomSmall();
      final V maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomSmall();
      final float z = PVectorM3FContract.getRandomSmall();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
      final V vo = PVectorM3F.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      {
        final V vr0 = PVectorM3F.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = PVectorM3FContract.getRandomLarge();
      final float min_y = PVectorM3FContract.getRandomLarge();
      final float min_z = PVectorM3FContract.getRandomLarge();
      final V minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomSmall();
      final float z = PVectorM3FContract.getRandomSmall();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
      final V vo = PVectorM3F.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final V vr0 = PVectorM3F.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = PVectorM3FContract.getRandomSmall();
      final float min_y = PVectorM3FContract.getRandomSmall();
      final float min_z = PVectorM3FContract.getRandomSmall();
      final V minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float max_x = PVectorM3FContract.getRandomLarge();
      final float max_y = PVectorM3FContract.getRandomLarge();
      final float max_z = PVectorM3FContract.getRandomLarge();
      final V maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
      final V vo = PVectorM3F.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final V vr0 = PVectorM3F.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());

        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = PVectorM3FContract.getRandomSmall();

      final float x = PVectorM3FContract.getRandomLarge();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
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

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = PVectorM3FContract.getRandomLarge();

      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomSmall();
      final float z = PVectorM3FContract.getRandomSmall();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
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

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = PVectorM3FContract.getRandomSmall();
      final float maximum = PVectorM3FContract.getRandomLarge();

      final float x = PVectorM3FContract.getRandomSmall();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
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

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM3F(5.0F, 6.0F, 7.0F);
    final V va = this.newVectorM3F(1.0F, 2.0F, 3.0F);

    Assert.assertNotEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertNotEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertNotEquals((double) vb.getZF(), (double) va.getZF(), 0.0);

    PVectorM3F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM3F(
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE,
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE,
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE);
    final V v1 = this.newVectorM3F();
    final V v2 = this.newVectorM3F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);

    v2.copyFromTyped2F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);
  }

  @Test public final void testCopy3Correct()
  {
    final V v0 = this.newVectorM3F(
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE,
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE,
      (float) PVectorM3FContract.getRandom() * Float.MAX_VALUE);
    final V v1 = this.newVectorM3F();
    final V v2 = this.newVectorM3F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);

    v2.copyFromTyped3F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v2.getZF(), 0.0f);
  }

  @Test public final void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) PVectorM3FContract.getRandom();
      final float y0 = (float) PVectorM3FContract.getRandom();
      final float z0 = (float) PVectorM3FContract.getRandom();
      final V v0 = this.newVectorM3F(x0, y0, z0);
      PVectorM3F.normalizeInPlace(v0);

      final float x1 = (float) PVectorM3FContract.getRandom();
      final float y1 = (float) PVectorM3FContract.getRandom();
      final float z1 = (float) PVectorM3FContract.getRandom();
      final V v1 = this.newVectorM3F(x1, y1, z1);
      PVectorM3F.normalizeInPlace(v1);

      final V vr = this.newVectorM3F();
      PVectorM3F.crossProduct(v0, v1, vr);
      PVectorM3F.normalizeInPlace(vr);

      final double dp0 = PVectorM3F.dotProduct(v0, vr);
      final double dp1 = PVectorM3F.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Test public final void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      PVectorM3F.almostEqual(
        ec, this.newVectorM3F(), this.newVectorM3F(0.0F, 0.0F, 0.0F)));
  }

  @Test public final void testDistance()
  {
    final PVectorM3F.ContextPVM3F c = new PVectorM3F.ContextPVM3F();
    final V v0 = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final V v1 = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, PVectorM3F.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM3F.ContextPVM3F c = new PVectorM3F.ContextPVM3F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = PVectorM3FContract.getRandomLarge();
      final float y0 = PVectorM3FContract.getRandomLarge();
      final float z0 = PVectorM3FContract.getRandomLarge();
      final V v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = PVectorM3FContract.getRandomLarge();
      final float y1 = PVectorM3FContract.getRandomLarge();
      final float z1 = PVectorM3FContract.getRandomLarge();
      final V v1 = this.newVectorM3F(x1, y1, z1);

      Assert.assertTrue(PVectorM3F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);
    final V v1 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = PVectorM3F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = PVectorM3F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final V vpx = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final V vmx = this.newVectorM3F(-1.0f, 0.0f, 0.0f);

    final V vpy = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final V vmy = this.newVectorM3F(0.0f, -1.0f, 0.0f);

    final V vpz = this.newVectorM3F(0.0f, 0.0f, 1.0f);
    final V vmz = this.newVectorM3F(0.0f, 0.0f, -1.0f);

    Assert.assertEquals(0.0, PVectorM3F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, PVectorM3F.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, PVectorM3F.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, PVectorM3F.dotProduct(vmy, vmz), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) PVectorM3FContract.getRandom();
      final float y = (float) PVectorM3FContract.getRandom();
      final float z = (float) PVectorM3FContract.getRandom();
      final V q = this.newVectorM3F(x, y, z);
      final double dp = PVectorM3F.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = PVectorM3F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM3F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM3F();
      final V m1 = this.newVectorM3F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) PVectorM3FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final V m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM3F(x, y, z);
      final V m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM3F();
    final V m1 = this.newVectorM3F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM3F();
      final V m1 = this.newVectorM3F();
      m1.setXF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM3F();
      final V m1 = this.newVectorM3F();
      m1.setYF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final V m0 = this.newVectorM3F();
      final V m1 = this.newVectorM3F();
      m1.setZF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    final V v1 = this.newVectorM3F(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
    Assert.assertEquals((double) v1.getZF(), (double) v0.getZF(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final PVectorM3F.ContextPVM3F c = new PVectorM3F.ContextPVM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = PVectorM3FContract.getRandomLarge();
      final float y0 = PVectorM3FContract.getRandomLarge();
      final float z0 = PVectorM3FContract.getRandomLarge();
      final V v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = PVectorM3FContract.getRandomLarge();
      final float y1 = PVectorM3FContract.getRandomLarge();
      final float z1 = PVectorM3FContract.getRandomLarge();
      final V v1 = this.newVectorM3F(x1, y1, z1);

      final V vr0 = this.newVectorM3F();
      final V vr1 = this.newVectorM3F();
      PVectorM3F.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM3F.interpolateLinear(c, v0, v1, 1.0, vr1);

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

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = PVectorM3FContract.getRandomLarge();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final double m = (double) PVectorM3F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (PVectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final float y = (float) (PVectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final float z = (float) (PVectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();
      PVectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = (double) PVectorM3F.magnitude(vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final V v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final V vr = PVectorM3F.normalizeInPlace(v);
    final double m = (double) PVectorM3F.magnitude(vr);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final V v = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final double m = (double) PVectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM3F(8.0f, 0.0f, 0.0f);

    {
      final double p = PVectorM3F.dotProduct(v, v);
      final double q = PVectorM3F.magnitudeSquared(v);
      final double r = (double) PVectorM3F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final V v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final double m = (double) PVectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final V v0 = this.newVectorM3F(8.0f, 0.0f, 0.0f);
    final V out = this.newVectorM3F();
    final V vr = PVectorM3F.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = (double) PVectorM3F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final V qr = this.newVectorM3F();
    final V q = this.newVectorM3F(0.0F, 0.0F, 0.0F);
    PVectorM3F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getZF()));
  }

  @Test public final void testOrthonormalize()
  {
    final PVectorM3F.ContextPVM3F c = new PVectorM3F.ContextPVM3F();
    final V v0 = this.newVectorM3F(0.0F, 1.0F, 0.0F);
    final V v0_out = this.newVectorM3F();
    final V v1 = this.newVectorM3F(0.5f, 0.5f, 0.0F);
    final V v1_out = this.newVectorM3F();

    PVectorM3F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3F(0.0F, 1.0F, 0.0F), v0_out);
    Assert.assertEquals(this.newVectorM3F(1.0F, 0.0F, 0.0F), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final PVectorM3F.ContextPVM3F c = new PVectorM3F.ContextPVM3F();
    final V v0 = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final V v1 = this.newVectorM3F(0.5f, 0.5f, 0.0f);

    PVectorM3F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3F(0.0F, 1.0F, 0.0F), v0);
    Assert.assertEquals(this.newVectorM3F(1.0F, 0.0F, 0.0F), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM3F(1.0f, 0.0f, 0.0f);
      final V q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final V r = this.newVectorM3F();
      final V u = PVectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, (double) PVectorM3F.magnitude(u), 0.0);
    }

    {
      final V p = this.newVectorM3F(-1.0f, 0.0f, 0.0f);
      final V q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final V r = this.newVectorM3F();
      final V u = PVectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, (double) PVectorM3F.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM3F();
    final V v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    final V ov0 = PVectorM3F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    final V ov1 = PVectorM3F.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = PVectorM3FContract.getRandomLarge();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();

      PVectorM3F.scale(v, 1.0, vr);

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

        PVectorM3F.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), orig_z));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = PVectorM3FContract.getRandomLarge();
      final float y = PVectorM3FContract.getRandomLarge();
      final float z = PVectorM3FContract.getRandomLarge();
      final V v = this.newVectorM3F(x, y, z);

      final V vr = this.newVectorM3F();

      PVectorM3F.scale(v, 0.0, vr);

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
        PVectorM3F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = PVectorM3FContract.getRandomLarge();
      final float y0 = PVectorM3FContract.getRandomLarge();
      final float z0 = PVectorM3FContract.getRandomLarge();
      final V v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = PVectorM3FContract.getRandomLarge();
      final float y1 = PVectorM3FContract.getRandomLarge();
      final float z1 = PVectorM3FContract.getRandomLarge();
      final V v1 = this.newVectorM3F(x1, y1, z1);

      final V vr0 = this.newVectorM3F();
      PVectorM3F.subtract(v0, v1, vr0);

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
        PVectorM3F.subtractInPlace(v0, v1);

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

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM3F();
    final V v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final V v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final V ov0 = PVectorM3F.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final V ov1 = PVectorM3F.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getZF(), 0.0);

    Assert.assertEquals(0.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
  }

}
