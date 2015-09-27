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
import com.io7m.jtensors.Vector3DType;
import com.io7m.jtensors.VectorM3D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3DContract<T extends Vector3DType>
{
  protected static double getRandom()
  {
    return Math.random();
  }

  protected abstract T newVectorM3D(final T v0);

  protected abstract T newVectorM3D(
    final double x,
    final double y,
    final double z);

  protected abstract T newVectorM3D();

  @Test public final void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double z = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getZD()), vr.getZD()));
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double z = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();

      VectorM3D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_z), v.getZD()));
    }
  }

  @Test public final void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0;

      final double x0 = VectorM3DContract.getRandom() * max;
      final double y0 = VectorM3DContract.getRandom() * max;
      final double z0 = VectorM3DContract.getRandom() * max;
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = VectorM3DContract.getRandom() * max;
      final double y1 = VectorM3DContract.getRandom() * max;
      final double z1 = VectorM3DContract.getRandom() * max;
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      VectorM3D.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + v1.getZD()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);
    final T v1 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);

    final T ov0 = VectorM3D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);

    final T ov1 = VectorM3D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0;

      final double x0 = VectorM3DContract.getRandom() * max;
      final double y0 = VectorM3DContract.getRandom() * max;
      final double z0 = VectorM3DContract.getRandom() * max;
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = VectorM3DContract.getRandom() * max;
      final double y1 = VectorM3DContract.getRandom() * max;
      final double z1 = VectorM3DContract.getRandom() * max;
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final double r = VectorM3DContract.getRandom() * max;

      final T vr0 = this.newVectorM3D();
      VectorM3D.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + (v1.getZD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + (v1.getZD() * r)));
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = VectorM3DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(VectorM3D.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM3D(x0, y0, z0);
      final T v1 = this.newVectorM3D(x0, y0, z0);
      final T v2 = this.newVectorM3D(x0, y0, z0);

      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3D.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM3D(3.0, 5.0, 7.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
    Assert.assertEquals(v.getZD(), v.getZD(), 0.0);
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double max_y =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double max_z =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double z = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      final T vo = VectorM3D.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      {
        final T vr0 = VectorM3D.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
      }
    }
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double min_y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double min_z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double z = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      final T vo = VectorM3D.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final T vr0 = VectorM3D.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double min_y =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double min_z =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double max_x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double max_y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double max_z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      final T vo = VectorM3D.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final T vr0 = VectorM3D.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());

        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;

      final double x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);

      {
        VectorM3D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = VectorM3DContract.getRandom() * Double.MAX_VALUE;

      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double z = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum =
        VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double maximum = VectorM3DContract.getRandom() * Double.MAX_VALUE;

      final double x = VectorM3DContract.getRandom() * (double) Float.MIN_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        VectorM3D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final T vb = this.newVectorM3D(5.0, 6.0, 7.0);
    final T va = this.newVectorM3D(1.0, 2.0, 3.0);

    Assert.assertNotEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertNotEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertNotEquals(vb.getZD(), va.getZD(), 0.0);

    VectorM3D.copy(va, vb);

    Assert.assertEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertEquals(vb.getZD(), va.getZD(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM3D(
      VectorM3DContract.getRandom() * Double.MAX_VALUE,
      VectorM3DContract.getRandom() * Double.MAX_VALUE,
      VectorM3DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newVectorM3D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(0.0, v1.getZD(), 0.0);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM3D(
      VectorM3DContract.getRandom() * Double.MAX_VALUE,
      VectorM3DContract.getRandom() * Double.MAX_VALUE,
      VectorM3DContract.getRandom() * Double.MAX_VALUE);
    final T v1 = this.newVectorM3D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0);
  }

  @Test public final void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM3DContract.getRandom();
      final double y0 = VectorM3DContract.getRandom();
      final double z0 = VectorM3DContract.getRandom();
      final T v0 = this.newVectorM3D(x0, y0, z0);
      VectorM3D.normalizeInPlace(v0);

      final double x1 = VectorM3DContract.getRandom();
      final double y1 = VectorM3DContract.getRandom();
      final double z1 = VectorM3DContract.getRandom();
      final T v1 = this.newVectorM3D(x1, y1, z1);
      VectorM3D.normalizeInPlace(v1);

      final T vr = this.newVectorM3D();
      VectorM3D.crossProduct(v0, v1, vr);
      VectorM3D.normalizeInPlace(vr);

      final double dp0 = VectorM3D.dotProduct(v0, vr);
      final double dp1 = VectorM3D.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Test public final void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      VectorM3D.almostEqual(
        ec, this.newVectorM3D(), this.newVectorM3D(0.0, 0.0, 0.0)));
  }

  @Test public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();

    final T v0 = this.newVectorM3D(0.0, 1.0, 0.0);
    final T v1 = this.newVectorM3D(0.0, 0.0, 0.0);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorM3D.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM3D(x1, y1, z1);

      Assert.assertTrue(VectorM3D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM3D(10.0, 10.0, 10.0);
    final T v1 = this.newVectorM3D(10.0, 10.0, 10.0);

    {
      final double p = VectorM3D.dotProduct(v0, v1);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM3D(1.0, 0.0, 0.0);
    final T vmx = this.newVectorM3D(-1.0, 0.0, 0.0);

    final T vpy = this.newVectorM3D(0.0, 1.0, 0.0);
    final T vmy = this.newVectorM3D(0.0, -1.0, 0.0);

    final T vpz = this.newVectorM3D(0.0, 0.0, 1.0);
    final T vmz = this.newVectorM3D(0.0, 0.0, -1.0);

    Assert.assertEquals(0.0, VectorM3D.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vmy, vmz), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom();
      final double y = VectorM3DContract.getRandom();
      final double z = VectorM3DContract.getRandom();
      final T q = this.newVectorM3D(x, y, z);
      final double dp = VectorM3D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM3D(10.0, 10.0, 10.0);

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }

    {
      final double p = VectorM3D.magnitudeSquared(v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(300.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM3D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3D();
      final T m1 = this.newVectorM3D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = VectorM3DContract.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final T m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM3D(x, y, z);
      final T m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM3D();
    final T m1 = this.newVectorM3D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM3D();
      final T m1 = this.newVectorM3D();
      m1.setXD(23.0);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final T m0 = this.newVectorM3D();
      final T m1 = this.newVectorM3D();
      m1.setYD(23.0);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }

    {
      final T m0 = this.newVectorM3D();
      final T m1 = this.newVectorM3D();
      m1.setZD(23.0);
      Assert.assertNotEquals(
        (double) m1.hashCode(), (double) m0.hashCode(), 0.0);
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM3D(1.0, 2.0, 3.0);
    final T v1 = this.newVectorM3D(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
    Assert.assertEquals(v1.getZD(), v0.getZD(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      final T vr1 = this.newVectorM3D();
      VectorM3D.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM3D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getXD(), vr0.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getYD(), vr0.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v0.getZD(), vr0.getZD()));

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getZD(), vr1.getZD()));
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final double m = VectorM3D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x =
        VectorM3DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y =
        VectorM3DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double z =
        VectorM3DContract.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3D.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM3D(0.0, 0.0, 0.0);
    final T vr = VectorM3D.normalizeInPlace(v);
    final double m = VectorM3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM3D(1.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM3D(8.0, 0.0, 0.0);

    {
      final double p = VectorM3D.dotProduct(v, v);
      final double q = VectorM3D.magnitudeSquared(v);
      final double r = VectorM3D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM3D(0.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM3D(8.0, 0.0, 0.0);
    final T out = this.newVectorM3D();
    final T vr = VectorM3D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = VectorM3D.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T qr = this.newVectorM3D();
    final T q = this.newVectorM3D(0.0, 0.0, 0.0);
    VectorM3D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getZD()));
  }

  @Test public final void testOrthonormalize()
  {
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();
    final T v0 = this.newVectorM3D(0.0, 1.0, 0.0);
    final T v1 = this.newVectorM3D(0.5, 0.5, 0.0);
    final T v0_out = this.newVectorM3D();
    final T v1_out = this.newVectorM3D();

    VectorM3D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3D(0.0, 1.0, 0.0), v0_out);
    Assert.assertEquals(this.newVectorM3D(1.0, 0.0, 0.0), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();
    final T v0 = this.newVectorM3D(0.0, 1.0, 0.0);
    final T v1 = this.newVectorM3D(0.5, 0.5, 0.0);

    VectorM3D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3D(0.0, 1.0, 0.0), v0);
    Assert.assertEquals(this.newVectorM3D(1.0, 0.0, 0.0), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM3D(1.0, 0.0, 0.0);
      final T q = this.newVectorM3D(0.0, 1.0, 0.0);
      final T r = this.newVectorM3D();
      final T u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM3D.magnitude(u), 0.0);
    }

    {
      final T p = this.newVectorM3D(-1.0, 0.0, 0.0);
      final T q = this.newVectorM3D(0.0, 1.0, 0.0);
      final T r = this.newVectorM3D();
      final T u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM3D.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);

    final T ov0 = VectorM3D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);

    final T ov1 = VectorM3D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();

      VectorM3D.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getYD(), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getZD(), vr.getZD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();

        VectorM3D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();

      VectorM3D.scale(v, 0.0, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));

      {
        VectorM3D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newVectorM3D(1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z0 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double y1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final double z1 = VectorM3DContract.getRandom() * Double.MAX_VALUE;
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      VectorM3D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() - v1.getZD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z - v1.getZD()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);
    final T v1 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);

    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);

    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);

    final T ov0 = VectorM3D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);

    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);

    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);

    final T ov1 = VectorM3D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), 0.0);
    Assert.assertEquals(0.0, ov1.getYD(), 0.0);
    Assert.assertEquals(0.0, ov1.getZD(), 0.0);

    Assert.assertEquals(0.0, v0.getXD(), 0.0);
    Assert.assertEquals(0.0, v0.getYD(), 0.0);
    Assert.assertEquals(0.0, v0.getZD(), 0.0);

    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
  }
}
