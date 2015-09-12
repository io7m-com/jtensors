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
import com.io7m.jtensors.parameterized.PVectorM3D;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3DContract<T> extends PVectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.absolute(v, vr);

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

  protected abstract <T> PVectorM3D<T> newVectorM3D(
    final double x,
    final double y,
    final double z);

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final double orig_x = v.getXD();
      final double orig_y = v.getYD();
      final double orig_z = v.getZD();

      PVectorM3D.absoluteInPlace(v);

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

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);

      final PVectorM3D<T> vr0 = this.newVectorM3D();
      PVectorM3D.add(v0, v1, vr0);

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
        PVectorM3D.addInPlace(v0, v1);

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

  @Override @Test public void testAddMutation()
  {
    final PVectorM3D<T> out = this.newVectorM3D();
    final PVectorM3D<T> v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);
    final PVectorM3D<T> v1 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final PVectorM3D<T> ov0 = PVectorM3D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final PVectorM3D<T> ov1 = PVectorM3D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 1000.0f;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);

      final double r = Math.random() * max;

      final PVectorM3D<T> vr0 = this.newVectorM3D();
      PVectorM3D.addScaled(v0, v1, r, vr0);

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
        PVectorM3D.addScaledInPlace(v0, v1, r);

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

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(PVectorM3D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);
      final PVectorM3D<T> v1 = this.newVectorM3D(x0, y0, z0);
      final PVectorM3D<T> v2 = this.newVectorM3D(x0, y0, z0);

      Assert.assertTrue(PVectorM3D.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM3D.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM3D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorM3D<T> v = this.newVectorM3D(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Float.MIN_VALUE;
      final double max_y = Math.random() * Float.MIN_VALUE;
      final double max_z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      final PVectorM3D<T> vo = PVectorM3D.clampMaximumByPVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      {
        final PVectorM3D<T> vr0 =
          PVectorM3D.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());
      }
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      final PVectorM3D<T> vo = PVectorM3D.clampMinimumByPVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final PVectorM3D<T> vr0 =
          PVectorM3D.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Float.MIN_VALUE;
      final double min_y = Math.random() * Float.MIN_VALUE;
      final double min_z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> minimum = this.newVectorM3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> maximum = this.newVectorM3D(max_x, max_y, max_z);

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      final PVectorM3D<T> vo =
        PVectorM3D.clampByPVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXD() <= maximum.getXD());
      Assert.assertTrue(vr.getYD() <= maximum.getYD());
      Assert.assertTrue(vr.getZD() <= maximum.getZD());

      Assert.assertTrue(vr.getXD() >= minimum.getXD());
      Assert.assertTrue(vr.getYD() >= minimum.getYD());
      Assert.assertTrue(vr.getZD() >= minimum.getZD());

      {
        final PVectorM3D<T> vr0 =
          PVectorM3D.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXD() <= maximum.getXD());
        Assert.assertTrue(v.getYD() <= maximum.getYD());
        Assert.assertTrue(v.getZD() <= maximum.getZD());

        Assert.assertTrue(v.getXD() >= minimum.getXD());
        Assert.assertTrue(v.getYD() >= minimum.getYD());
        Assert.assertTrue(v.getZD() >= minimum.getZD());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Float.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getZD() <= maximum);

      {
        PVectorM3D.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getZD() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Float.MIN_VALUE;
      final double z = Math.random() * Float.MIN_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        PVectorM3D.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Float.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Float.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXD() <= maximum);
      Assert.assertTrue(vr.getXD() >= minimum);
      Assert.assertTrue(vr.getYD() <= maximum);
      Assert.assertTrue(vr.getYD() >= minimum);
      Assert.assertTrue(vr.getZD() <= maximum);
      Assert.assertTrue(vr.getZD() >= minimum);

      {
        PVectorM3D.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXD() <= maximum);
        Assert.assertTrue(v.getXD() >= minimum);
        Assert.assertTrue(v.getYD() <= maximum);
        Assert.assertTrue(v.getYD() >= minimum);
        Assert.assertTrue(v.getZD() <= maximum);
        Assert.assertTrue(v.getZD() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final PVectorM3D<T> vb = this.newVectorM3D(5, 6, 7);
    final PVectorM3D<T> va = this.newVectorM3D(1, 2, 3);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());
    Assert.assertFalse(va.getZD() == vb.getZD());

    PVectorM3D.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
    Assert.assertTrue(va.getZD() == vb.getZD());
  }

  @Override @Test public void testCopy2Correct()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE);
    final PVectorM3D<T> v1 = this.newVectorM3D();
    final PVectorM3D<T> v2 = this.newVectorM3D();

    v1.copyFrom2D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(0, v1.getZD(), 0.0f);

    v2.copyFromTyped2D(v0);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0f);
    Assert.assertEquals(0, v2.getZD(), 0.0f);
  }

  @Override @Test public void testCopy3Correct()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE,
      Math.random() * Double.MAX_VALUE);
    final PVectorM3D<T> v1 = this.newVectorM3D();
    final PVectorM3D<T> v2 = this.newVectorM3D();

    v1.copyFrom3D(v0);

    Assert.assertEquals(v0.getXD(), v1.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v1.getYD(), 0.0f);
    Assert.assertEquals(v0.getZD(), v1.getZD(), 0.0f);

    v2.copyFromTyped3D(v0);

    Assert.assertEquals(v0.getXD(), v2.getXD(), 0.0f);
    Assert.assertEquals(v0.getYD(), v2.getYD(), 0.0f);
    Assert.assertEquals(v0.getZD(), v2.getZD(), 0.0f);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random();
      final double y0 = Math.random();
      final double z0 = Math.random();
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);
      PVectorM3D.normalizeInPlace(v0);

      final double x1 = Math.random();
      final double y1 = Math.random();
      final double z1 = Math.random();
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);
      PVectorM3D.normalizeInPlace(v1);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.crossProduct(v0, v1, vr);
      PVectorM3D.normalizeInPlace(vr);

      final double dp0 = PVectorM3D.dotProduct(v0, vr);
      final double dp1 = PVectorM3D.dotProduct(v1, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    Assert.assertTrue(
      PVectorM3D.almostEqual(
        ec, this.newVectorM3D(), this.newVectorM3D(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM3D.ContextPVM3D c = new PVectorM3D.ContextPVM3D();

    final PVectorM3D<T> v0 = this.newVectorM3D(0.0f, 1.0f, 0.0f);
    final PVectorM3D<T> v1 = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorM3D.distance(c, v0, v1), 1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    final PVectorM3D.ContextPVM3D c = new PVectorM3D.ContextPVM3D();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);

      Assert.assertTrue(PVectorM3D.distance(c, v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(10.0f, 10.0f, 10.0f);
    final PVectorM3D<T> v1 = this.newVectorM3D(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorM3D<T> vpx = this.newVectorM3D(1.0f, 0.0f, 0.0f);
    final PVectorM3D<T> vmx = this.newVectorM3D(-1.0f, 0.0f, 0.0f);

    final PVectorM3D<T> vpy = this.newVectorM3D(0.0f, 1.0f, 0.0f);
    final PVectorM3D<T> vmy = this.newVectorM3D(0.0f, -1.0f, 0.0f);

    final PVectorM3D<T> vpz = this.newVectorM3D(0.0f, 0.0f, 1.0f);
    final PVectorM3D<T> vmz = this.newVectorM3D(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(PVectorM3D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorM3D.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(PVectorM3D.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(PVectorM3D.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final PVectorM3D<T> q = this.newVectorM3D(x, y, z);
      final double dp = PVectorM3D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorM3D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = PVectorM3D.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      final PVectorM3D<T> m1 = this.newVectorM3D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0f;
    final double z = y + 1.0f;
    final double w = z + 1.0f;
    final double q = w + 1.0f;

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D(x, y, z);
      final PVectorM3D<T> m1 = this.newVectorM3D(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorM3D<T> m0 = this.newVectorM3D();
    final PVectorM3D<T> m1 = this.newVectorM3D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      final PVectorM3D<T> m1 = this.newVectorM3D();
      m1.setXD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      final PVectorM3D<T> m1 = this.newVectorM3D();
      m1.setYD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM3D<T> m0 = this.newVectorM3D();
      final PVectorM3D<T> m1 = this.newVectorM3D();
      m1.setZD(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(1.0f, 2.0f, 3.0f);
    final PVectorM3D<T> v1 = new PVectorM3D<T>(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM3D.ContextPVM3D c = new PVectorM3D.ContextPVM3D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);

      final PVectorM3D<T> vr0 = this.newVectorM3D();
      final PVectorM3D<T> vr1 = this.newVectorM3D();
      PVectorM3D.interpolateLinear(c, v0, v1, 0.0f, vr0);
      PVectorM3D.interpolateLinear(c, v0, v1, 1.0f, vr1);

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

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final double m = PVectorM3D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();
      PVectorM3D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM3D.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM3D<T> v = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    final PVectorM3D<T> vr = PVectorM3D.normalizeInPlace(v);
    final double m = PVectorM3D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM3D<T> v = this.newVectorM3D(1.0f, 0.0f, 0.0f);
    final double m = PVectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorM3D<T> v = this.newVectorM3D(8.0f, 0.0f, 0.0f);

    {
      final double p = PVectorM3D.dotProduct(v, v);
      final double q = PVectorM3D.magnitudeSquared(v);
      final double r = PVectorM3D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM3D<T> v = this.newVectorM3D(0.0f, 0.0f, 0.0f);
    final double m = PVectorM3D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorM3D<T> v0 = this.newVectorM3D(8.0f, 0.0f, 0.0f);
    final PVectorM3D<T> out = this.newVectorM3D();
    final PVectorM3D<T> vr = PVectorM3D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = PVectorM3D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM3D<T> qr = this.newVectorM3D();
    final PVectorM3D<T> q = this.newVectorM3D(0, 0, 0);
    PVectorM3D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getZD()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorM3D.ContextPVM3D c = new PVectorM3D.ContextPVM3D();
    final PVectorM3D<T> v0 = this.newVectorM3D(0, 1, 0);
    final PVectorM3D<T> v0_out = this.newVectorM3D();
    final PVectorM3D<T> v1 = this.newVectorM3D(0.5f, 0.5f, 0);
    final PVectorM3D<T> v1_out = this.newVectorM3D();

    PVectorM3D.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM3D(0, 1, 0), v0_out);
    Assert.assertEquals(this.newVectorM3D(1, 0, 0), v1_out);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final PVectorM3D.ContextPVM3D c = new PVectorM3D.ContextPVM3D();
    final PVectorM3D<T> v0 = this.newVectorM3D(0f, 1f, 0f);
    final PVectorM3D<T> v1 = this.newVectorM3D(0.5f, 0.5f, 0f);

    PVectorM3D.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM3D(0, 1, 0), v0);
    Assert.assertEquals(this.newVectorM3D(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorM3D<T> p = this.newVectorM3D(1.0f, 0.0f, 0.0f);
      final PVectorM3D<T> q = this.newVectorM3D(0.0f, 1.0f, 0.0f);
      final PVectorM3D<T> r = this.newVectorM3D();
      final PVectorM3D<T> u = PVectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3D.magnitude(u) == 0.0);
    }

    {
      final PVectorM3D<T> p = this.newVectorM3D(-1.0f, 0.0f, 0.0f);
      final PVectorM3D<T> q = this.newVectorM3D(0.0f, 1.0f, 0.0f);
      final PVectorM3D<T> r = this.newVectorM3D();
      final PVectorM3D<T> u = PVectorM3D.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM3D.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final PVectorM3D<T> out = this.newVectorM3D();
    final PVectorM3D<T> v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final PVectorM3D<T> ov0 = PVectorM3D.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    final PVectorM3D<T> ov1 = PVectorM3D.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();

      PVectorM3D.scale(v, 1.0f, vr);

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

        PVectorM3D.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v = this.newVectorM3D(x, y, z);

      final PVectorM3D<T> vr = this.newVectorM3D();

      PVectorM3D.scale(v, 0.0f, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0f));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0f));

      {
        PVectorM3D.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0f));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final PVectorM3D<T> v = this.newVectorM3D(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[PVectorM3D 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v0 = this.newVectorM3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final PVectorM3D<T> v1 = this.newVectorM3D(x1, y1, z1);

      final PVectorM3D<T> vr0 = this.newVectorM3D();
      PVectorM3D.subtract(v0, v1, vr0);

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
        PVectorM3D.subtractInPlace(v0, v1);

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

  @Override @Test public void testSubtractMutation()
  {
    final PVectorM3D<T> out = this.newVectorM3D();
    final PVectorM3D<T> v0 = this.newVectorM3D(1.0f, 1.0f, 1.0f);
    final PVectorM3D<T> v1 = this.newVectorM3D(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final PVectorM3D<T> ov0 = PVectorM3D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);

    final PVectorM3D<T> ov1 = PVectorM3D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(ov1.getZD() == 0.0);

    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v0.getZD() == 0.0);

    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
  }

  protected abstract <T> PVectorM3D<T> newVectorM3D();
}
