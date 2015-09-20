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
import com.io7m.jtensors.parameterized.PVector4DType;
import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM4DContract<T, V extends PVector4DType<T>>
{
  protected abstract V newVectorM4D();

  protected abstract V newVectorM4DFrom(V v);

  protected abstract V newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w);

  @Test public final void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MIN_VALUE;
      final double z = this.getRandom() * Double.MIN_VALUE;
      final double w = this.getRandom() * Double.MIN_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      PVectorM4D.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getZD()), vr.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getWD()), vr.getWD()));
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MIN_VALUE;
      final double z = this.getRandom() * Double.MIN_VALUE;
      final double w = this.getRandom() * Double.MIN_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();
      final double orig_w = v.getWD();

      PVectorM4D.absoluteInPlace(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_x), v.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_y), v.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_z), v.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(orig_w), v.getWD()));
    }
  }

  @Test public final void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0;

      final double x0 = this.getRandom() * max;
      final double y0 = this.getRandom() * max;
      final double z0 = this.getRandom() * max;
      final double w0 = this.getRandom() * max;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * max;
      final double y1 = this.getRandom() * max;
      final double z1 = this.getRandom() * max;
      final double w1 = this.getRandom() * max;
      final V v1 = this.newVectorM4D(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4D();
      PVectorM4D.add(v0, v1, vr0);

      System.out.println("v0  : " + v0);
      System.out.println("v1  : " + v1);
      System.out.println("vr0 : " + vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getWD(), v0.getWD() + v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        PVectorM4D.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + v1.getZD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getWD(), orig_w + v1.getWD()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM4D();
    final V v0 = this.newVectorM4D(1.0, 1.0, 1.0, 1.0);
    final V v1 = this.newVectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final V ov0 = PVectorM4D.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(2.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final V ov1 = PVectorM4D.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, ov1.getWD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }

  @Test public final void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0;

      final double x0 = this.getRandom() * max;
      final double y0 = this.getRandom() * max;
      final double z0 = this.getRandom() * max;
      final double w0 = this.getRandom() * max;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * max;
      final double y1 = this.getRandom() * max;
      final double z1 = this.getRandom() * max;
      final double w1 = this.getRandom() * max;
      final V v1 = this.newVectorM4D(x1, y1, z1, w1);

      final double r = this.getRandom() * max;

      final V vr0 = this.newVectorM4D();
      PVectorM4D.addScaled(v0, v1, r, vr0);

      System.out.println("v0  : " + v0);
      System.out.println("v1  : " + v1);
      System.out.println("vr0 : " + vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() + (v1.getYD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() + (v1.getZD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getWD(), v0.getWD() + (v1.getWD() * r)));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        PVectorM4D.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x + (v1.getXD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y + (v1.getYD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z + (v1.getZD() * r)));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getWD(), orig_w + (v1.getWD() * r)));
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = this.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, z, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, q, z, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, q, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, z, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, z, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, q, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, z, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, q, w);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, z, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, q, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, q, q, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, q, q);
      Assert.assertFalse(PVectorM4D.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);
      final V v1 = this.newVectorM4D(x0, y0, z0, w0);
      final V v2 = this.newVectorM4D(x0, y0, z0, w0);

      Assert.assertTrue(PVectorM4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM4D.almostEqual(ec, v0, v2));
    }
  }

  protected double getRandom()
  {
    return Math.random();
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertEquals(v.getXD(), v.getXD(), 0.0);
    Assert.assertEquals(v.getYD(), v.getYD(), 0.0);
    Assert.assertEquals(v.getZD(), v.getZD(), 0.0);
    Assert.assertEquals(v.getWD(), v.getWD(), 0.0);
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = this.getRandom() * Double.MIN_VALUE;
      final double max_y = this.getRandom() * Double.MIN_VALUE;
      final double max_z = this.getRandom() * Double.MIN_VALUE;
      final double max_w = this.getRandom() * Double.MIN_VALUE;
      final V maximum = this.newVectorM4D(max_x, max_y, max_z, max_w);

      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MIN_VALUE;
      final double z = this.getRandom() * Double.MIN_VALUE;
      final double w = this.getRandom() * Double.MIN_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      final V vo = PVectorM4D.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());
      Assert.assertTrue(vr.getWD() <= maximum.getWD());

      {
        final V vr0 = PVectorM4D.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
        Assert.assertTrue(v.getWD() <= maximum.getWD());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = this.getRandom() * Double.MAX_VALUE;
      final double min_y = this.getRandom() * Double.MAX_VALUE;
      final double min_z = this.getRandom() * Double.MAX_VALUE;
      final double min_w = this.getRandom() * Double.MAX_VALUE;
      final V minimum = this.newVectorM4D(min_x, min_y, min_z, min_w);

      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MIN_VALUE;
      final double z = this.getRandom() * Double.MIN_VALUE;
      final double w = this.getRandom() * Double.MIN_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      final V vo = PVectorM4D.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());
      Assert.assertTrue(vr.getWD() >= minimum.getWD());

      {
        final V vr0 = PVectorM4D.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
        Assert.assertTrue(v.getWD() >= minimum.getWD());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = this.getRandom() * Double.MIN_VALUE;
      final double min_y = this.getRandom() * Double.MIN_VALUE;
      final double min_z = this.getRandom() * Double.MIN_VALUE;
      final double min_w = this.getRandom() * Double.MIN_VALUE;
      final V minimum = this.newVectorM4D(min_x, min_y, min_z, min_w);

      final double max_x = this.getRandom() * Double.MAX_VALUE;
      final double max_y = this.getRandom() * Double.MAX_VALUE;
      final double max_z = this.getRandom() * Double.MAX_VALUE;
      final double max_w = this.getRandom() * Double.MAX_VALUE;
      final V maximum = this.newVectorM4D(max_x, max_y, max_z, max_w);

      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      final V vo = PVectorM4D.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());
      Assert.assertTrue(vr.getWD() <= maximum.getWD());
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());
      Assert.assertTrue(vr.getWD() >= minimum.getWD());

      {
        final V vr0 = PVectorM4D.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
        Assert.assertTrue(v.getWD() <= maximum.getWD());
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
        Assert.assertTrue(v.getWD() >= minimum.getWD());
      }
    }
  }

  @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = this.getRandom() * Double.MIN_VALUE;

      final double x = this.getRandom() * Double.MAX_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      PVectorM4D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getWD() <= maximum);

      {
        PVectorM4D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getWD() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.getRandom() * Double.MAX_VALUE;

      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MIN_VALUE;
      final double z = this.getRandom() * Double.MIN_VALUE;
      final double w = this.getRandom() * Double.MIN_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      PVectorM4D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);
      Assert.assertTrue(vr.getWD() >= minimum);

      {
        PVectorM4D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
        Assert.assertTrue(v.getWD() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = this.getRandom() * Double.MIN_VALUE;
      final double maximum = this.getRandom() * Double.MAX_VALUE;

      final double x = this.getRandom() * Double.MIN_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      PVectorM4D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);
      Assert.assertTrue(vr.getWD() <= maximum);
      Assert.assertTrue(vr.getWD() >= minimum);

      {
        PVectorM4D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
        Assert.assertTrue(v.getWD() <= maximum);
        Assert.assertTrue(v.getWD() >= minimum);
      }
    }
  }

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM4D(5.0, 6.0, 7.0, 8.0);
    final V va = this.newVectorM4D(1.0, 2.0, 3.0, 4.0);

    Assert.assertNotEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertNotEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertNotEquals(vb.getZD(), va.getZD(), 0.0);
    Assert.assertNotEquals(vb.getWD(), va.getWD(), 0.0);

    PVectorM4D.copy(va, vb);

    Assert.assertEquals(vb.getXD(), va.getXD(), 0.0);
    Assert.assertEquals(vb.getYD(), va.getYD(), 0.0);
    Assert.assertEquals(vb.getZD(), va.getZD(), 0.0);
    Assert.assertEquals(vb.getWD(), va.getWD(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM4D(
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE);
    final V v1 = this.newVectorM4D();
    final V v2 = this.newVectorM4D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(0.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    v2.copyFromTyped2D(v0);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0);
    Assert.assertEquals(0.0, v2.getZD(), 0.0);
    Assert.assertEquals(1.0, v2.getWD(), 0.0);
  }

  @Test public final void testCopy3Correct()
  {
    final V v0 = this.newVectorM4D(
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE);
    final V v1 = this.newVectorM4D();
    final V v2 = this.newVectorM4D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    v2.copyFromTyped3D(v0);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v2.getZD(), 0.0);
    Assert.assertEquals(1.0, v2.getWD(), 0.0);
  }

  @Test public final void testCopy4Correct()
  {
    final V v0 = this.newVectorM4D(
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE,
      this.getRandom() * Double.MAX_VALUE);
    final V v1 = this.newVectorM4D();
    final V v2 = this.newVectorM4D();

    v1.copyFrom4D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0);
    Assert.assertEquals(v0.getWD(), v1.getWD(), 0.0);

    v2.copyFromTyped4D(v0);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0);
    Assert.assertEquals(v0.getZD(), v2.getZD(), 0.0);
    Assert.assertEquals(v0.getWD(), v2.getWD(), 0.0);
  }

  @Test public final void testDefault0001()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      PVectorM4D.almostEqual(
        ec, this.newVectorM4D(), this.newVectorM4D(0.0, 0.0, 0.0, 1.0)));
  }

  @Test public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM4D.ContextPVM4D c = new PVectorM4D.ContextPVM4D();

    final V v0 = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
    final V v1 = this.newVectorM4D(0.0, 0.0, 0.0, 0.0);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorM4D.distance(c, v0, v1), 1.0));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM4D.ContextPVM4D c = new PVectorM4D.ContextPVM4D();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * Double.MAX_VALUE;
      final double y1 = this.getRandom() * Double.MAX_VALUE;
      final double z1 = this.getRandom() * Double.MAX_VALUE;
      final double w1 = this.getRandom() * Double.MAX_VALUE;
      final V v1 = this.newVectorM4D(x1, y1, z1, w1);

      Assert.assertTrue(PVectorM4D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM4D(10.0, 10.0, 10.0, 10.0);
    final V v1 = this.newVectorM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = PVectorM4D.dotProduct(v0, v1);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v0.getWD(), 0.0);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = PVectorM4D.dotProduct(v0, v0);
      Assert.assertEquals(10.0, v0.getXD(), 0.0);
      Assert.assertEquals(10.0, v0.getYD(), 0.0);
      Assert.assertEquals(10.0, v0.getZD(), 0.0);
      Assert.assertEquals(10.0, v0.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = PVectorM4D.dotProduct(v1, v1);
      Assert.assertEquals(10.0, v1.getXD(), 0.0);
      Assert.assertEquals(10.0, v1.getYD(), 0.0);
      Assert.assertEquals(10.0, v1.getZD(), 0.0);
      Assert.assertEquals(10.0, v1.getWD(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final V vpx = this.newVectorM4D(1.0, 0.0, 0.0, 0.0);
    final V vmx = this.newVectorM4D(-1.0, 0.0, 0.0, 0.0);

    final V vpy = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
    final V vmy = this.newVectorM4D(0.0, -1.0, 0.0, 0.0);

    final V vpz = this.newVectorM4D(0.0, 0.0, 1.0, 0.0);
    final V vmz = this.newVectorM4D(0.0, 0.0, -1.0, 0.0);

    Assert.assertEquals(0.0, PVectorM4D.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, PVectorM4D.dotProduct(vpy, vpz), 0.0);
    Assert.assertEquals(0.0, PVectorM4D.dotProduct(vmx, vmy), 0.0);
    Assert.assertEquals(0.0, PVectorM4D.dotProduct(vmy, vmz), 0.0);
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom();
      final double y = this.getRandom();
      final double z = this.getRandom();
      final double w = this.getRandom();
      final V q = this.newVectorM4D(x, y, z, w);
      final double dp = PVectorM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom();
      final double y = this.getRandom();
      final double z = this.getRandom();
      final double w = this.getRandom();
      final V q = this.newVectorM4D(x, y, z, w);

      final double ms = PVectorM4D.magnitudeSquared(q);
      final double dp = PVectorM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM4D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM4D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM4D();
      final V m1 = this.newVectorM4D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final double x = this.getRandom();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4D(x, y, z, w);
      final V m1 = this.newVectorM4D(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM4D();
    final V m1 = this.newVectorM4D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM4D();
      final V m1 = this.newVectorM4D();
      m1.setXD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM4D();
      final V m1 = this.newVectorM4D();
      m1.setYD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM4D();
      final V m1 = this.newVectorM4D();
      m1.setZD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM4D();
      final V m1 = this.newVectorM4D();
      m1.setWD(23.0);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM4D(1.0, 2.0, 3.0, 4.0);
    final V v1 = this.newVectorM4DFrom(v0);

    Assert.assertEquals(v1.getXD(), v0.getXD(), 0.0);
    Assert.assertEquals(v1.getYD(), v0.getYD(), 0.0);
    Assert.assertEquals(v1.getZD(), v0.getZD(), 0.0);
    Assert.assertEquals(v1.getWD(), v0.getWD(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM4D.ContextPVM4D c = new PVectorM4D.ContextPVM4D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * Double.MAX_VALUE;
      final double y1 = this.getRandom() * Double.MAX_VALUE;
      final double z1 = this.getRandom() * Double.MAX_VALUE;
      final double w1 = this.getRandom() * Double.MAX_VALUE;
      final V v1 = this.newVectorM4D(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4D();
      final V vr1 = this.newVectorM4D();
      PVectorM4D.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM4D.interpolateLinear(c, v0, v1, 1.0, vr1);

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
          ec, v0.getWD(), vr0.getWD()));

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getXD(), vr1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getYD(), vr1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getZD(), vr1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v1.getWD(), vr1.getWD()));
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double y = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double z = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final double w = 1.0 + (this.getRandom() * Double.MAX_VALUE);
      final V v = this.newVectorM4D(x, y, z, w);

      final double m = PVectorM4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final ContextRelative context = new ContextRelative();
    context.setMaxAbsoluteDifference(0.000000000000001);

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double y = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double z = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final double w = this.getRandom() * (Math.sqrt(Double.MAX_VALUE) / 2.0);
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();
      PVectorM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM4D.magnitude(vr);
      System.out.println(m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final V v = this.newVectorM4D(0.0, 0.0, 0.0, 0.0);
    final V vr = PVectorM4D.normalizeInPlace(v);
    final double m = PVectorM4D.magnitude(vr);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final V v = this.newVectorM4D(1.0, 0.0, 0.0, 0.0);
    final double m = PVectorM4D.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM4D(8.0, 0.0, 0.0, 0.0);

    {
      final double p = PVectorM4D.dotProduct(v, v);
      final double q = PVectorM4D.magnitudeSquared(v);
      final double r = PVectorM4D.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final V v = this.newVectorM4D(0.0, 0.0, 0.0, 0.0);
    final double m = PVectorM4D.magnitude(v);
    final ContextRelative context = new ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testNormalizeSimple()
  {
    final V v0 = this.newVectorM4D(8.0, 0.0, 0.0, 0.0);
    final V out = this.newVectorM4D();
    final V vr = PVectorM4D.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = PVectorM4D.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V qr = this.newVectorM4D();
    final V q = this.newVectorM4D(0.0, 0.0, 0.0, 0.0);
    PVectorM4D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getZD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0.0, qr.getWD()));
  }

  @Test public final void testOrthonormalize()
  {
    final PVectorM4D.ContextPVM4D c = new PVectorM4D.ContextPVM4D();
    final V v0 = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
    final V v1 = this.newVectorM4D(0.5, 0.5, 0.0, 0.0);

    final V v0_out = this.newVectorM4D();
    final V v1_out = this.newVectorM4D();
    PVectorM4D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM4D(0.0, 1.0, 0.0, 0.0), v0_out);
    Assert.assertEquals(this.newVectorM4D(1.0, 0.0, 0.0, 0.0), v1_out);
  }

  @Test public final void testOrthonormalizeMutation()
  {
    final PVectorM4D.ContextPVM4D c = new PVectorM4D.ContextPVM4D();
    final V v0 = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
    final V v1 = this.newVectorM4D(0.5, 0.5, 0.0, 0.0);

    PVectorM4D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM4D(0.0, 1.0, 0.0, 0.0), v0);
    Assert.assertEquals(this.newVectorM4D(1.0, 0.0, 0.0, 0.0), v1);
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM4D(1.0, 0.0, 0.0, 0.0);
      final V q = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
      final V r = this.newVectorM4D();
      final V u = PVectorM4D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM4D.magnitude(u), 0.0);
    }

    {
      final V p = this.newVectorM4D(-1.0, 0.0, 0.0, 0.0);
      final V q = this.newVectorM4D(0.0, 1.0, 0.0, 0.0);
      final V r = this.newVectorM4D();
      final V u = PVectorM4D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM4D.magnitude(u), 0.0);
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM4D();
    final V v0 = this.newVectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);

    final V ov0 = PVectorM4D.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, out.getXD(), 0.0);
    Assert.assertEquals(2.0, out.getYD(), 0.0);
    Assert.assertEquals(2.0, out.getZD(), 0.0);
    Assert.assertEquals(2.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);

    final V ov1 = PVectorM4D.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, ov1.getXD(), 0.0);
    Assert.assertEquals(2.0, ov1.getYD(), 0.0);
    Assert.assertEquals(2.0, ov1.getZD(), 0.0);
    Assert.assertEquals(2.0, ov1.getWD(), 0.0);
    Assert.assertEquals(2.0, v0.getXD(), 0.0);
    Assert.assertEquals(2.0, v0.getYD(), 0.0);
    Assert.assertEquals(2.0, v0.getZD(), 0.0);
    Assert.assertEquals(2.0, v0.getWD(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MAX_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();

      PVectorM4D.scale(v, 1.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getYD(), vr.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getZD(), vr.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, v.getWD(), vr.getWD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();
        final double orig_w = v.getWD();

        PVectorM4D.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), orig_w));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = this.getRandom() * Double.MAX_VALUE;
      final double y = this.getRandom() * Double.MAX_VALUE;
      final double z = this.getRandom() * Double.MAX_VALUE;
      final double w = this.getRandom() * Double.MAX_VALUE;
      final V v = this.newVectorM4D(x, y, z, w);

      final V vr = this.newVectorM4D();

      PVectorM4D.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getXD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getYD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getZD(), 0.0));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          context, vr.getWD(), 0.0));

      {
        PVectorM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), 0.0));
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM4D(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().endsWith("1.0 2.0 3.0 4.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = this.getRandom() * Double.MAX_VALUE;
      final double y0 = this.getRandom() * Double.MAX_VALUE;
      final double z0 = this.getRandom() * Double.MAX_VALUE;
      final double w0 = this.getRandom() * Double.MAX_VALUE;
      final V v0 = this.newVectorM4D(x0, y0, z0, w0);

      final double x1 = this.getRandom() * Double.MAX_VALUE;
      final double y1 = this.getRandom() * Double.MAX_VALUE;
      final double z1 = this.getRandom() * Double.MAX_VALUE;
      final double w1 = this.getRandom() * Double.MAX_VALUE;
      final V v1 = this.newVectorM4D(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4D();
      PVectorM4D.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getYD(), v0.getYD() - v1.getYD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getZD(), v0.getZD() - v1.getZD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr0.getWD(), v0.getWD() - v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        PVectorM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getXD(), orig_x - v1.getXD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getYD(), orig_y - v1.getYD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getZD(), orig_z - v1.getZD()));
        Assert.assertTrue(
          AlmostEqualDouble.almostEqual(
            ec, v0.getWD(), orig_w - v1.getWD()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM4D();
    final V v0 = this.newVectorM4D(1.0, 1.0, 1.0, 1.0);
    final V v1 = this.newVectorM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(1.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final V ov0 = PVectorM4D.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, out.getXD(), 0.0);
    Assert.assertEquals(0.0, out.getYD(), 0.0);
    Assert.assertEquals(0.0, out.getZD(), 0.0);
    Assert.assertEquals(0.0, out.getWD(), 0.0);
    Assert.assertEquals(1.0, v0.getXD(), 0.0);
    Assert.assertEquals(1.0, v0.getYD(), 0.0);
    Assert.assertEquals(1.0, v0.getZD(), 0.0);
    Assert.assertEquals(1.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);

    final V ov1 = PVectorM4D.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, ov1.getXD(), 0.0);
    Assert.assertEquals(0.0, ov1.getYD(), 0.0);
    Assert.assertEquals(0.0, ov1.getZD(), 0.0);
    Assert.assertEquals(0.0, ov1.getWD(), 0.0);
    Assert.assertEquals(0.0, v0.getXD(), 0.0);
    Assert.assertEquals(0.0, v0.getYD(), 0.0);
    Assert.assertEquals(0.0, v0.getZD(), 0.0);
    Assert.assertEquals(0.0, v0.getWD(), 0.0);
    Assert.assertEquals(1.0, v1.getXD(), 0.0);
    Assert.assertEquals(1.0, v1.getYD(), 0.0);
    Assert.assertEquals(1.0, v1.getZD(), 0.0);
    Assert.assertEquals(1.0, v1.getWD(), 0.0);
  }

}
