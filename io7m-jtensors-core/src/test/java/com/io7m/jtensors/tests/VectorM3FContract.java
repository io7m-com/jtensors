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
import com.io7m.jtensors.Vector3FType;
import com.io7m.jtensors.VectorM3F;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3FContract<T extends Vector3FType>
{

  protected static float getSmallRandom()
  {
    return (float) (VectorM3FContract.getRandom() * (double) Float.MIN_VALUE);
  }

  protected static float getLargeRandom()
  {
    return (float) (VectorM3FContract.getRandom() * (double) Float.MAX_VALUE);
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract T newVectorM3F(final T v0);

  protected abstract T newVectorM3F();

  protected abstract T newVectorM3F(
    final float x,
    final float y,
    final float z);

  @Test public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getSmallRandom();
      final float z = VectorM3FContract.getSmallRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
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

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getSmallRandom();
      final float z = VectorM3FContract.getSmallRandom();
      final T v = this.newVectorM3F(x, y, z);

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

  @Test public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float y0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float z0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final T v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float y1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float z1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final T v1 = this.newVectorM3F(x1, y1, z1);

      final T vr0 = this.newVectorM3F();
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

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM3F();
    final T v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final T v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final T ov0 = VectorM3F.add(v0, v1, out);

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

    final T ov1 = VectorM3F.addInPlace(v0, v1);

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

      final float x0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float y0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float z0 = (float) (VectorM3FContract.getRandom() * (double) max);
      final T v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float y1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final float z1 = (float) (VectorM3FContract.getRandom() * (double) max);
      final T v1 = this.newVectorM3F(x1, y1, z1);

      final float r = (float) (VectorM3FContract.getRandom() * (double) max);

      final T vr0 = this.newVectorM3F();
      VectorM3F.addScaled(v0, v1, (double) r, vr0);

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
        VectorM3F.addScaledInPlace(v0, v1, (double) r);

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

    final float x = (float) VectorM3FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM3FContract.getLargeRandom();
      final float y0 = VectorM3FContract.getLargeRandom();
      final float z0 = VectorM3FContract.getLargeRandom();
      final T v0 = this.newVectorM3F(x0, y0, z0);
      final T v1 = this.newVectorM3F(x0, y0, z0);
      final T v2 = this.newVectorM3F(x0, y0, z0);

      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM3F(3.0f, 5.0f, 7.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
    Assert.assertEquals((double) v.getZF(), (double) v.getZF(), 0.0);
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = VectorM3FContract.getSmallRandom();
      final float max_y = VectorM3FContract.getSmallRandom();
      final float max_z = VectorM3FContract.getSmallRandom();
      final T maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getSmallRandom();
      final float z = VectorM3FContract.getSmallRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
      final T vo = VectorM3F.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      {
        final T vr0 = VectorM3F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getZF() <= maximum.getZF());
      }
    }
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = VectorM3FContract.getLargeRandom();
      final float min_y = VectorM3FContract.getLargeRandom();
      final float min_z = VectorM3FContract.getLargeRandom();
      final T minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getSmallRandom();
      final float z = VectorM3FContract.getSmallRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
      final T vo = VectorM3F.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final T vr0 = VectorM3F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
        Assert.assertTrue(v.getZF() >= minimum.getZF());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = VectorM3FContract.getSmallRandom();
      final float min_y = VectorM3FContract.getSmallRandom();
      final float min_z = VectorM3FContract.getSmallRandom();
      final T minimum = this.newVectorM3F(min_x, min_y, min_z);

      final float max_x = VectorM3FContract.getLargeRandom();
      final float max_y = VectorM3FContract.getLargeRandom();
      final float max_z = VectorM3FContract.getLargeRandom();
      final T maximum = this.newVectorM3F(max_x, max_y, max_z);

      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
      final T vo = VectorM3F.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getZF() <= maximum.getZF());

      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());
      Assert.assertTrue(vr.getZF() >= minimum.getZF());

      {
        final T vr0 = VectorM3F.clampByVectorInPlace(v, minimum, maximum);
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
      final float maximum = VectorM3FContract.getSmallRandom();

      final float x = VectorM3FContract.getLargeRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
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

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = VectorM3FContract.getLargeRandom();

      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getSmallRandom();
      final float z = VectorM3FContract.getSmallRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
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

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = VectorM3FContract.getSmallRandom();
      final float maximum = VectorM3FContract.getLargeRandom();

      final float x = VectorM3FContract.getSmallRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
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

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM3F(5.0F, 6.0F, 7.0F);
    final T va = this.newVectorM3F(1.0F, 2.0F, 3.0F);

    Assert.assertNotEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertNotEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertNotEquals((double) vb.getZF(), (double) va.getZF(), 0.0);

    VectorM3F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM3F(
      VectorM3FContract.getLargeRandom(),
      VectorM3FContract.getLargeRandom(),
      VectorM3FContract.getLargeRandom());
    final T v1 = this.newVectorM3F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM3F(
      VectorM3FContract.getLargeRandom(),
      VectorM3FContract.getLargeRandom(),
      VectorM3FContract.getLargeRandom());
    final T v1 = this.newVectorM3F();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
  }

  @Test public final void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) VectorM3FContract.getRandom();
      final float y0 = (float) VectorM3FContract.getRandom();
      final float z0 = (float) VectorM3FContract.getRandom();
      final T v0 = this.newVectorM3F(x0, y0, z0);
      VectorM3F.normalizeInPlace(v0);

      final float x1 = (float) VectorM3FContract.getRandom();
      final float y1 = (float) VectorM3FContract.getRandom();
      final float z1 = (float) VectorM3FContract.getRandom();
      final T v1 = this.newVectorM3F(x1, y1, z1);
      VectorM3F.normalizeInPlace(v1);

      final T vr = this.newVectorM3F();
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

  @Test public final void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      VectorM3F.almostEqual(
        ec, this.newVectorM3F(), this.newVectorM3F(0.0F, 0.0F, 0.0F)));
  }

  @Test public final void testDistance()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final T v0 = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final T v1 = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        context, VectorM3F.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM3FContract.getLargeRandom();
      final float y0 = VectorM3FContract.getLargeRandom();
      final float z0 = VectorM3FContract.getLargeRandom();
      final T v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = VectorM3FContract.getLargeRandom();
      final float y1 = VectorM3FContract.getLargeRandom();
      final float z1 = VectorM3FContract.getLargeRandom();
      final T v1 = this.newVectorM3F(x1, y1, z1);

      Assert.assertTrue(VectorM3F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);
    final T v1 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final T vmx = this.newVectorM3F(-1.0f, 0.0f, 0.0f);

    final T vpy = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final T vmy = this.newVectorM3F(0.0f, -1.0f, 0.0f);

    final T vpz = this.newVectorM3F(0.0f, 0.0f, 1.0f);
    final T vmz = this.newVectorM3F(0.0f, 0.0f, -1.0f);

    Assert.assertEquals(0.0, VectorM3F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, VectorM3F.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, VectorM3F.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, VectorM3F.dotProduct(vmy, vmz), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) VectorM3FContract.getRandom();
      final float y = (float) VectorM3FContract.getRandom();
      final float z = (float) VectorM3FContract.getRandom();
      final T q = this.newVectorM3F(x, y, z);
      final double dp = VectorM3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM3F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3F();
      final T m1 = this.newVectorM3F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) VectorM3FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3F(x, y, z);
      final T m1 = this.newVectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM3F();
    final T m1 = this.newVectorM3F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM3F();
      final T m1 = this.newVectorM3F();
      m1.setXF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final T m0 = this.newVectorM3F();
      final T m1 = this.newVectorM3F();
      m1.setYF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final T m0 = this.newVectorM3F();
      final T m1 = this.newVectorM3F();
      m1.setZF(23.0F);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    final T v1 = this.newVectorM3F(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
    Assert.assertEquals((double) v1.getZF(), (double) v0.getZF(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM3FContract.getLargeRandom();
      final float y0 = VectorM3FContract.getLargeRandom();
      final float z0 = VectorM3FContract.getLargeRandom();
      final T v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = VectorM3FContract.getLargeRandom();
      final float y1 = VectorM3FContract.getLargeRandom();
      final float z1 = VectorM3FContract.getLargeRandom();
      final T v1 = this.newVectorM3F(x1, y1, z1);

      final T vr0 = this.newVectorM3F();
      final T vr1 = this.newVectorM3F();
      VectorM3F.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM3F.interpolateLinear(c, v0, v1, 1.0, vr1);

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
      final float x = VectorM3FContract.getLargeRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final double m = (double) VectorM3F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (VectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final float y = (float) (VectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final float z = (float) (VectorM3FContract.getRandom()
                               * (Math.sqrt((double) Float.MAX_VALUE) / 2.0));
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();
      VectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = (double) VectorM3F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final T v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final T vr = VectorM3F.normalizeInPlace(v);
    final double m = (double) VectorM3F.magnitude(vr);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final T v = this.newVectorM3F(1.0f, 0.0f, 0.0f);
    final double m = (double) VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM3F(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorM3F.dotProduct(v, v);
      final double q = VectorM3F.magnitudeSquared(v);
      final double r = (double) VectorM3F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM3F(0.0f, 0.0f, 0.0f);
    final double m = (double) VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM3F(8.0f, 0.0f, 0.0f);
    final T out = this.newVectorM3F();
    final T vr = VectorM3F.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = (double) VectorM3F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T qr = this.newVectorM3F();
    final T q = this.newVectorM3F(0.0F, 0.0F, 0.0F);
    VectorM3F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getZF()));
  }

  @Test public final void testOrthonormalize()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final T v0 = this.newVectorM3F(0.0F, 1.0F, 0.0F);
    final T v1 = this.newVectorM3F(0.5f, 0.5f, 0.0F);
    final T v0_out = this.newVectorM3F();
    final T v1_out = this.newVectorM3F();

    VectorM3F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3F(0.0F, 1.0F, 0.0F), v0_out);
    Assert.assertEquals(this.newVectorM3F(1.0F, 0.0F, 0.0F), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final VectorM3F.ContextVM3F c = new VectorM3F.ContextVM3F();
    final T v0 = this.newVectorM3F(0.0f, 1.0f, 0.0f);
    final T v1 = this.newVectorM3F(0.5f, 0.5f, 0.0f);

    VectorM3F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3F(0.0F, 1.0F, 0.0F), v0);
    Assert.assertEquals(this.newVectorM3F(1.0F, 0.0F, 0.0F), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM3F(1.0f, 0.0f, 0.0f);
      final T q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final T r = this.newVectorM3F();
      final T u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, (double) VectorM3F.magnitude(u), 0.0);
    }

    {
      final T p = this.newVectorM3F(-1.0f, 0.0f, 0.0f);
      final T q = this.newVectorM3F(0.0f, 1.0f, 0.0f);
      final T r = this.newVectorM3F();
      final T u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, (double) VectorM3F.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM3F();
    final T v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    final T ov0 = VectorM3F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    final T ov1 = VectorM3F.scaleInPlace(v0, 2.0);

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
      final float x = VectorM3FContract.getLargeRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();

      VectorM3F.scale(v, 1.0, vr);

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

        VectorM3F.scaleInPlace(v, 1.0);

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
      final float x = VectorM3FContract.getLargeRandom();
      final float y = VectorM3FContract.getLargeRandom();
      final float z = VectorM3FContract.getLargeRandom();
      final T v = this.newVectorM3F(x, y, z);

      final T vr = this.newVectorM3F();

      VectorM3F.scale(v, 0.0, vr);

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
        VectorM3F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getZF(), 0.0f));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM3F(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM3FContract.getLargeRandom();
      final float y0 = VectorM3FContract.getLargeRandom();
      final float z0 = VectorM3FContract.getLargeRandom();
      final T v0 = this.newVectorM3F(x0, y0, z0);

      final float x1 = VectorM3FContract.getLargeRandom();
      final float y1 = VectorM3FContract.getLargeRandom();
      final float z1 = VectorM3FContract.getLargeRandom();
      final T v1 = this.newVectorM3F(x1, y1, z1);

      final T vr0 = this.newVectorM3F();
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

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM3F();
    final T v0 = this.newVectorM3F(1.0f, 1.0f, 1.0f);
    final T v1 = this.newVectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);

    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);

    final T ov0 = VectorM3F.subtract(v0, v1, out);

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

    final T ov1 = VectorM3F.subtractInPlace(v0, v1);

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
