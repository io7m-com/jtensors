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
  protected abstract double delta();

  protected abstract double randomLargeNegative();

  protected abstract double randomLargePositive();

  @Test public final void testConstants()
  {
    Assert.assertTrue(this.delta() >= 0.0);
    Assert.assertTrue(this.delta() <= 1.0);

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargeNegative() <= 0.0);
    }

    for (int index = 0; index < 1000; ++index) {
      Assert.assertTrue(this.randomLargePositive() >= 0.0);
    }
  }

  protected abstract T newVectorM3D(final T v0);

  protected abstract T newVectorM3D(
    final double x,
    final double y,
    final double z);

  protected abstract T newVectorM3D();

  @Test public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final double z = this.randomLargeNegative();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXD()), vr.getXD(), this.delta());
      Assert.assertEquals(Math.abs(v.getYD()), vr.getYD(), this.delta());
      Assert.assertEquals(Math.abs(v.getZD()), vr.getZD(), this.delta());
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final double z = this.randomLargeNegative();
      final T v = this.newVectorM3D(x, y, z);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();

      VectorM3D.absoluteInPlace(v);

      Assert.assertEquals(Math.abs(orig_x), v.getXD(), this.delta());
      Assert.assertEquals(Math.abs(orig_y), v.getYD(), this.delta());
      Assert.assertEquals(Math.abs(orig_z), v.getZD(), this.delta());
    }
  }

  @Test public final void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      VectorM3D.add(v0, v1, vr0);

      {
        final double vr0_x = vr0.getXD();
        final double vr0_y = vr0.getYD();
        final double vr0_z = vr0.getZD();

        final double v0_x = v0.getXD();
        final double v0_y = v0.getYD();
        final double v0_z = v0.getZD();

        final double v1_x = v1.getXD();
        final double v1_y = v1.getYD();
        final double v1_z = v1.getZD();

        Assert.assertEquals(vr0_x, v0_x + v1_x, this.delta());
        Assert.assertEquals(vr0_y, v0_y + v1_y, this.delta());
        Assert.assertEquals(vr0_z, v0_z + v1_z, this.delta());
      }

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addInPlace(v0, v1);

        final double v0_x = v0.getXD();
        final double v0_y = v0.getYD();
        final double v0_z = v0.getZD();

        final double v1_x = v1.getXD();
        final double v1_y = v1.getYD();
        final double v1_z = v1.getZD();

        Assert.assertEquals(v0_x, orig_x + v1_x, this.delta());
        Assert.assertEquals(v0_y, orig_y + v1_y, this.delta());
        Assert.assertEquals(v0_z, orig_z + v1_z, this.delta());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);
    final T v1 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(0.0, out.getZD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());

    final T ov0 = VectorM3D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), this.delta());
    Assert.assertEquals(2.0, out.getYD(), this.delta());
    Assert.assertEquals(2.0, out.getZD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());

    final T ov1 = VectorM3D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), this.delta());
    Assert.assertEquals(2.0, ov1.getYD(), this.delta());
    Assert.assertEquals(2.0, ov1.getZD(), this.delta());
    Assert.assertEquals(2.0, v0.getXD(), this.delta());
    Assert.assertEquals(2.0, v0.getYD(), this.delta());
    Assert.assertEquals(2.0, v0.getZD(), this.delta());
    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());
  }

  @Test public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final double r = this.randomLargePositive();

      final T vr0 = this.newVectorM3D();
      VectorM3D.addScaled(v0, v1, r, vr0);

      Assert.assertEquals(v0.getXD() + (v1.getXD() * r), vr0.getXD(), this.delta());
      Assert.assertEquals(v0.getYD() + (v1.getYD() * r), vr0.getYD(), this.delta());
      Assert.assertEquals(v0.getZD() + (v1.getZD() * r), vr0.getZD(), this.delta());

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.addScaledInPlace(v0, v1, r);

        Assert.assertEquals(orig_x + (v1.getXD() * r), v0.getXD(), this.delta());
        Assert.assertEquals(orig_y + (v1.getYD() * r), v0.getYD(), this.delta());
        Assert.assertEquals(orig_z + (v1.getZD() * r), v0.getZD(), this.delta());
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = this.randomLargePositive();
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
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
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

    Assert.assertEquals(v.getXD(), v.getXD(), this.delta());
    Assert.assertEquals(v.getYD(), v.getYD(), this.delta());
    Assert.assertEquals(v.getZD(), v.getZD(), this.delta());
  }

  @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = this.randomLargeNegative();
      final double max_y = this.randomLargeNegative();
      final double max_z = this.randomLargeNegative();
      final T maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final double z = this.randomLargeNegative();
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
      final double min_x = this.randomLargePositive();
      final double min_y = this.randomLargePositive();
      final double min_z = this.randomLargePositive();
      final T minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final double z = this.randomLargeNegative();
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
      final double min_x = this.randomLargeNegative();
      final double min_y = this.randomLargeNegative();
      final double min_z = this.randomLargeNegative();
      final T minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double max_x = this.randomLargePositive();
      final double max_y = this.randomLargePositive();
      final double max_z = this.randomLargePositive();
      final T maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = this.randomLargeNegative();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
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
      final double maximum = this.randomLargeNegative();

      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.clampMaximum(v, maximum, vr);

      System.out.printf("f  : %f\n", maximum);
      System.out.printf("v  : %s\n", v);
      System.out.printf("vr : %s\n", v);

      Assert.assertEquals(maximum, vr.getXD(), this.delta());
      Assert.assertEquals(maximum, vr.getYD(), this.delta());
      Assert.assertEquals(maximum, vr.getZD(), this.delta());

      {
        VectorM3D.clampMaximumInPlace(v, maximum);
        Assert.assertEquals(maximum, v.getXD(), this.delta());
        Assert.assertEquals(maximum, v.getYD(), this.delta());
        Assert.assertEquals(maximum, v.getZD(), this.delta());
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.randomLargePositive();

      final double x = this.randomLargeNegative();
      final double y = this.randomLargeNegative();
      final double z = this.randomLargeNegative();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.clampMinimum(v, minimum, vr);

      System.out.printf("f  : %f\n", minimum);
      System.out.printf("v  : %s\n", v);
      System.out.printf("vr : %s\n", v);

      Assert.assertEquals(minimum, vr.getXD(), this.delta());
      Assert.assertEquals(minimum, vr.getYD(), this.delta());
      Assert.assertEquals(minimum, vr.getZD(), this.delta());

      {
        VectorM3D.clampMinimumInPlace(v, minimum);
        Assert.assertEquals(minimum, v.getXD(), this.delta());
        Assert.assertEquals(minimum, v.getYD(), this.delta());
        Assert.assertEquals(minimum, v.getZD(), this.delta());
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.randomLargeNegative();
      final double maximum = this.randomLargePositive();

      final double x = this.randomLargeNegative();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
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

    Assert.assertEquals(vb.getXD(), va.getXD(), this.delta());
    Assert.assertEquals(vb.getYD(), va.getYD(), this.delta());
    Assert.assertEquals(vb.getZD(), va.getZD(), this.delta());
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM3D(
      this.randomLargePositive(),
      this.randomLargePositive(), this.randomLargePositive());
    final T v1 = this.newVectorM3D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), this.delta());
    Assert.assertEquals(v0.getYD(), v1.getYD(), this.delta());
    Assert.assertEquals(0.0, v1.getZD(), this.delta());
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM3D(
      this.randomLargePositive(),
      this.randomLargePositive(), this.randomLargePositive());
    final T v1 = this.newVectorM3D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), this.delta());
    Assert.assertEquals(v0.getYD(), v1.getYD(), this.delta());
    Assert.assertEquals(v0.getZD(), v1.getZD(), this.delta());
  }

  @Test public final void testCrossProductPerpendicular()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);
      VectorM3D.normalizeInPlace(v0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
      final T v1 = this.newVectorM3D(x1, y1, z1);
      VectorM3D.normalizeInPlace(v1);

      final T vr = this.newVectorM3D();
      VectorM3D.crossProduct(v0, v1, vr);
      VectorM3D.normalizeInPlace(vr);

      final double dp0 = VectorM3D.dotProduct(v0, vr);
      final double dp1 = VectorM3D.dotProduct(v1, vr);

      System.out.printf("v0  : %s\n", v0);
      System.out.printf("v1  : %s\n", v1);
      System.out.printf("vr  : %s\n", vr);
      System.out.printf("dp0 : %f\n", dp0);
      System.out.printf("dp1 : %f\n", dp1);

      Assert.assertEquals(0.0, dp0, this.delta());
      Assert.assertEquals(0.0, dp1, this.delta());
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
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
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
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(10.0, v0.getZD(), this.delta());
      Assert.assertEquals(10.0, v1.getXD(), this.delta());
      Assert.assertEquals(10.0, v1.getYD(), this.delta());
      Assert.assertEquals(10.0, v1.getZD(), this.delta());
      Assert.assertEquals(300.0, p, this.delta());
    }

    {
      final double p = VectorM3D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(10.0, v0.getZD(), this.delta());
      Assert.assertEquals(300.0, p, this.delta());
    }

    {
      final double p = VectorM3D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), this.delta());
      Assert.assertEquals(10.0, v1.getYD(), this.delta());
      Assert.assertEquals(10.0, v1.getZD(), this.delta());
      Assert.assertEquals(300.0, p, this.delta());
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

    Assert.assertEquals(0.0, VectorM3D.dotProduct(vpx, vpy), this.delta());
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vpy, vpz), this.delta());
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vmx, vmy), this.delta());
    Assert.assertEquals(0.0, VectorM3D.dotProduct(vmy, vmz), this.delta());
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
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
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(10.0, v0.getZD(), this.delta());
      Assert.assertEquals(300.0, p, this.delta());
    }

    {
      final double p = VectorM3D.magnitudeSquared(v0);
      Assert.assertEquals(10.0, v0.getXD(), this.delta());
      Assert.assertEquals(10.0, v0.getYD(), this.delta());
      Assert.assertEquals(10.0, v0.getZD(), this.delta());
      Assert.assertEquals(300.0, p, this.delta());
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
    final double x = this.randomLargePositive();
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

    Assert.assertEquals(v1.getXD(), v0.getXD(), this.delta());
    Assert.assertEquals(v1.getYD(), v0.getYD(), this.delta());
    Assert.assertEquals(v1.getZD(), v0.getZD(), this.delta());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final VectorM3D.ContextVM3D c = new VectorM3D.ContextVM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      final T vr1 = this.newVectorM3D();
      VectorM3D.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM3D.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals(v0.getXD(), vr0.getXD(), this.delta());
      Assert.assertEquals(v0.getYD(), vr0.getYD(), this.delta());
      Assert.assertEquals(v0.getZD(), vr0.getZD(), this.delta());

      Assert.assertEquals(v1.getXD(), vr1.getXD(), this.delta());
      Assert.assertEquals(v1.getYD(), vr1.getYD(), this.delta());
      Assert.assertEquals(v1.getZD(), vr1.getZD(), this.delta());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
      final T v = this.newVectorM3D(x, y, z);

      final double m = VectorM3D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();
      VectorM3D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3D.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertEquals(1.0, m, this.delta());
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final T v = this.newVectorM3D(0.0, 0.0, 0.0);
    final T vr = VectorM3D.normalizeInPlace(v);
    final double m = VectorM3D.magnitude(vr);
    Assert.assertEquals(0.0, m, this.delta());
  }

  @Test public final void testMagnitudeOne()
  {
    final T v = this.newVectorM3D(1.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertEquals(1.0, m, this.delta());
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM3D(8.0, 0.0, 0.0);

    {
      final double p = VectorM3D.dotProduct(v, v);
      final double q = VectorM3D.magnitudeSquared(v);
      final double r = VectorM3D.magnitude(v);
      Assert.assertEquals(64.0, p, this.delta());
      Assert.assertEquals(64.0, q, this.delta());
      Assert.assertEquals(8.0, r, this.delta());
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final T v = this.newVectorM3D(0.0, 0.0, 0.0);
    final double m = VectorM3D.magnitude(v);
    Assert.assertEquals(0.0, m, this.delta());
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM3D(8.0, 0.0, 0.0);
    final T out = this.newVectorM3D();
    final T vr = VectorM3D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = VectorM3D.magnitude(out);
    Assert.assertEquals(1.0, m, this.delta());
  }

  @Test public final void testNormalizeZero()
  {
    final T qr = this.newVectorM3D();
    final T q = this.newVectorM3D(0.0, 0.0, 0.0);
    VectorM3D.normalize(q, qr);

    Assert.assertEquals(0.0, qr.getXD(), this.delta());
    Assert.assertEquals(0.0, qr.getYD(), this.delta());
    Assert.assertEquals(0.0, qr.getZD(), this.delta());
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
      Assert.assertEquals(0.0, VectorM3D.magnitude(u), this.delta());
    }

    {
      final T p = this.newVectorM3D(-1.0, 0.0, 0.0);
      final T q = this.newVectorM3D(0.0, 1.0, 0.0);
      final T r = this.newVectorM3D();
      final T u = VectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM3D.magnitude(u), this.delta());
    }
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(0.0, out.getZD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());

    final T ov0 = VectorM3D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), this.delta());
    Assert.assertEquals(2.0, out.getYD(), this.delta());
    Assert.assertEquals(2.0, out.getZD(), this.delta());
    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());

    final T ov1 = VectorM3D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), this.delta());
    Assert.assertEquals(2.0, ov1.getYD(), this.delta());
    Assert.assertEquals(2.0, ov1.getZD(), this.delta());
    Assert.assertEquals(2.0, v0.getXD(), this.delta());
    Assert.assertEquals(2.0, v0.getYD(), this.delta());
    Assert.assertEquals(2.0, v0.getZD(), this.delta());
  }

  @Test public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();

      VectorM3D.scale(v, 1.0, vr);

      Assert.assertEquals(v.getXD(), vr.getXD(), this.delta());
      Assert.assertEquals(v.getYD(), vr.getYD(), this.delta());
      Assert.assertEquals(v.getZD(), vr.getZD(), this.delta());

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();

        VectorM3D.scaleInPlace(v, 1.0);

        Assert.assertEquals(orig_x, v.getXD(), this.delta());
        Assert.assertEquals(orig_y, v.getYD(), this.delta());
        Assert.assertEquals(orig_z, v.getZD(), this.delta());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.randomLargePositive();
      final double y = this.randomLargePositive();
      final double z = this.randomLargePositive();
      final T v = this.newVectorM3D(x, y, z);

      final T vr = this.newVectorM3D();

      VectorM3D.scale(v, 0.0, vr);

      Assert.assertEquals(0.0, vr.getXD(), this.delta());
      Assert.assertEquals(0.0, vr.getYD(), this.delta());
      Assert.assertEquals(0.0, vr.getZD(), this.delta());

      {
        VectorM3D.scaleInPlace(v, 0.0);

        Assert.assertEquals(0.0, v.getXD(), this.delta());
        Assert.assertEquals(0.0, v.getYD(), this.delta());
        Assert.assertEquals(0.0, v.getZD(), this.delta());
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
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.randomLargePositive();
      final double y0 = this.randomLargePositive();
      final double z0 = this.randomLargePositive();
      final T v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = this.randomLargePositive();
      final double y1 = this.randomLargePositive();
      final double z1 = this.randomLargePositive();
      final T v1 = this.newVectorM3D(x1, y1, z1);

      final T vr0 = this.newVectorM3D();
      VectorM3D.subtract(v0, v1, vr0);

      {
        final double vr0_x = vr0.getXD();
        final double vr0_y = vr0.getYD();
        final double vr0_z = vr0.getZD();

        final double v0_x = v0.getXD();
        final double v0_y = v0.getYD();
        final double v0_z = v0.getZD();

        final double v1_x = v1.getXD();
        final double v1_y = v1.getYD();
        final double v1_z = v1.getZD();

        Assert.assertEquals(vr0_x, v0_x - v1_x, this.delta());
        Assert.assertEquals(vr0_y, v0_y - v1_y, this.delta());
        Assert.assertEquals(vr0_z, v0_z - v1_z, this.delta());
      }

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        VectorM3D.subtractInPlace(v0, v1);

        final double v0_x = v0.getXD();
        final double v0_y = v0.getYD();
        final double v0_z = v0.getZD();

        final double v1_x = v1.getXD();
        final double v1_y = v1.getYD();
        final double v1_z = v1.getZD();

        Assert.assertEquals(v0_x, orig_x - v1_x, this.delta());
        Assert.assertEquals(v0_y, orig_y - v1_y, this.delta());
        Assert.assertEquals(v0_z, orig_z - v1_z, this.delta());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM3D();
    final T v0 = this.newVectorM3D(1.0, 1.0, 1.0);
    final T v1 = this.newVectorM3D(1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(0.0, out.getZD(), this.delta());

    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());

    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());

    final T ov0 = VectorM3D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), this.delta());
    Assert.assertEquals(0.0, out.getYD(), this.delta());
    Assert.assertEquals(0.0, out.getZD(), this.delta());

    Assert.assertEquals(1.0, v0.getXD(), this.delta());
    Assert.assertEquals(1.0, v0.getYD(), this.delta());
    Assert.assertEquals(1.0, v0.getZD(), this.delta());

    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());

    final T ov1 = VectorM3D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), this.delta());
    Assert.assertEquals(0.0, ov1.getYD(), this.delta());
    Assert.assertEquals(0.0, ov1.getZD(), this.delta());

    Assert.assertEquals(0.0, v0.getXD(), this.delta());
    Assert.assertEquals(0.0, v0.getYD(), this.delta());
    Assert.assertEquals(0.0, v0.getZD(), this.delta());

    Assert.assertEquals(1.0, v1.getXD(), this.delta());
    Assert.assertEquals(1.0, v1.getYD(), this.delta());
    Assert.assertEquals(1.0, v1.getZD(), this.delta());
  }
}
