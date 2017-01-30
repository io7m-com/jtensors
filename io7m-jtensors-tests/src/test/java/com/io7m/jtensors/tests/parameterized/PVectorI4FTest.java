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
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.parameterized.PVectorI4F;
import com.io7m.jtensors.parameterized.PVectorReadable4FType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class PVectorI4FTest<T>
  extends PVectorI4Contract
{
  @Test public void testZero()
  {
    final PVectorI4F<Object> z = PVectorI4F.zero();
    Assert.assertEquals(0.0, z.getXF(), 0.0);
    Assert.assertEquals(0.0, z.getYF(), 0.0);
    Assert.assertEquals(0.0, z.getZF(), 0.0);
    Assert.assertEquals(0.0, z.getWF(), 0.0);
  }

  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI4F<T> v = new PVectorI4F<T>(x, y, z, w);

      final PVectorI4F<T> vr = PVectorI4F.absolute(v);

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

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final PVectorI4F<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final PVectorI4F<T> v1 = new PVectorI4F<T>(x1, y1, z1, w1);

      final PVectorI4F<T> vr = PVectorI4F.add(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();

      {
        final float expected = v0.getXF() + v1.getXF();
        final float got = vr.getXF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getYF() + v1.getYF();
        final float got = vr.getYF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getZF() + v1.getZF();
        final float got = vr.getZF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getWF() + v1.getWF();
        final float got = vr.getWF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }
    }
  }

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final PVectorI4F<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final PVectorI4F<T> v1 = new PVectorI4F<T>(x1, y1, z1, w1);

      final float r = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final PVectorI4F<T> vr = PVectorI4F.addScaled(v0, v1, r);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();

      {
        final float expected = v0.getXF() + (v1.getXF() * r);
        final float got = vr.getXF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getYF() + (v1.getYF() * r);
        final float got = vr.getYF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getZF() + (v1.getZF() * r);
        final float got = vr.getZF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getWF() + (v1.getWF() * r);
        final float got = vr.getWF();

        Assert.assertTrue(AlmostEqualFloat.almostEqual(context, expected, got));
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
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, y, z, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(x, q, z, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(x, y, q, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(x, y, z, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, q, z, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, y, q, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, y, z, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, q, q, w);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, q, z, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(q, q, q, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(x, q, q, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorReadable4FType<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> m1 = new PVectorI4F<T>(x, y, q, q);
      Assert.assertFalse(PVectorI4F.almostEqual(ec, m0, m1));
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
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);
      final PVectorReadable4FType<T> v1 = new PVectorI4F<T>(x0, y0, z0, w0);
      final PVectorReadable4FType<T> v2 = new PVectorI4F<T>(x0, y0, z0, w0);

      Assert.assertTrue(PVectorI4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorI4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorI4F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorReadable4FType v = new PVectorI4F<T>(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI4F<T> maximum =
        new PVectorI4F<T>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(
        PVectorI4F.clampMaximumByPVector(v, maximum).getXF()
        <= maximum.getXF());
      Assert.assertTrue(
        PVectorI4F.clampMaximumByPVector(v, maximum).getYF()
        <= maximum.getYF());
      Assert.assertTrue(
        PVectorI4F.clampMaximumByPVector(v, maximum).getZF()
        <= maximum.getZF());
      Assert.assertTrue(
        PVectorI4F.clampMaximumByPVector(v, maximum).getWF()
        <= maximum.getWF());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI4F<T> minimum =
        new PVectorI4F<T>(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(
        PVectorI4F.clampMinimumByPVector(v, minimum).getXF()
        >= minimum.getXF());
      Assert.assertTrue(
        PVectorI4F.clampMinimumByPVector(v, minimum).getYF()
        >= minimum.getYF());
      Assert.assertTrue(
        PVectorI4F.clampMinimumByPVector(v, minimum).getZF()
        >= minimum.getZF());
      Assert.assertTrue(
        PVectorI4F.clampMinimumByPVector(v, minimum).getWF()
        >= minimum.getWF());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final float min_w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI4F<T> minimum =
        new PVectorI4F<T>(min_x, min_y, min_z, min_w);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final float max_w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI4F<T> maximum =
        new PVectorI4F<T>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getXF()
        <= maximum.getXF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getXF()
        >= minimum.getXF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getYF()
        <= maximum.getYF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getYF()
        >= minimum.getYF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getZF()
        <= maximum.getZF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getZF()
        >= minimum.getZF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getWF()
        <= maximum.getWF());
      Assert.assertTrue(
        PVectorI4F.clampByPVector(v, minimum, maximum).getWF()
        >= minimum.getWF());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(PVectorI4F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert.assertTrue(PVectorI4F.clampMaximum(v, maximum).getYF() <= maximum);
      Assert.assertTrue(PVectorI4F.clampMaximum(v, maximum).getZF() <= maximum);
      Assert.assertTrue(PVectorI4F.clampMaximum(v, maximum).getWF() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(PVectorI4F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert.assertTrue(PVectorI4F.clampMinimum(v, minimum).getYF() >= minimum);
      Assert.assertTrue(PVectorI4F.clampMinimum(v, minimum).getZF() >= minimum);
      Assert.assertTrue(PVectorI4F.clampMinimum(v, minimum).getWF() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getYF() >= minimum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getZF() <= maximum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getZF() >= minimum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getWF() <= maximum);
      Assert.assertTrue(
        PVectorI4F.clamp(v, minimum, maximum).getWF() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI4F<T> v = new PVectorI4F<T>(x, y, z, w);
      final PVectorReadable4FType<T> vc = new PVectorI4F<T>(v);

      Assert.assertTrue(PVectorI4F.almostEqual(ec, v, vc));
    }
  }

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final PVectorReadable4FType<T> v = new PVectorI4F<T>();
    PVectorI4F.almostEqual(context, v, new PVectorI4F<T>(0, 0, 0, 1));
  }

  @Override @Test public void testDistance()
  {
    final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(0.0f, 1.0f, 0.0f, 0.0f);
    final PVectorReadable4FType<T> v1 = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, PVectorI4F.distance(v0, v1), 1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v1 = new PVectorI4F<T>(x1, y1, z1, w1);

      Assert.assertTrue(PVectorI4F.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI4F<T> v0 = new PVectorI4F<T>(10.0f, 10.0f, 10.0f, 10.0f);
    final PVectorI4F<T> v1 = new PVectorI4F<T>(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorI4F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = PVectorI4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = PVectorI4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorReadable4FType<T> vpx = new PVectorI4F<T>(1.0f, 0.0f, 0.0f, 0.0f);
    final PVectorReadable4FType<T> vmx = new PVectorI4F<T>(-1.0f, 0.0f, 0.0f, 0.0f);

    final PVectorReadable4FType<T> vpy = new PVectorI4F<T>(0.0f, 1.0f, 0.0f, 0.0f);
    final PVectorReadable4FType<T> vmy = new PVectorI4F<T>(0.0f, -1.0f, 0.0f, 0.0f);

    final PVectorReadable4FType<T> vpz = new PVectorI4F<T>(0.0f, 0.0f, 1.0f, 0.0f);
    final PVectorReadable4FType<T> vmz = new PVectorI4F<T>(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(PVectorI4F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorI4F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(PVectorI4F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(PVectorI4F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final PVectorReadable4FType<T> q = new PVectorI4F<T>(x, y, z, w);
      final double dp = PVectorI4F.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final PVectorReadable4FType<T> q = new PVectorI4F<T>(x, y, z, w);

      final double ms = PVectorI4F.magnitudeSquared(q);
      final double dp = PVectorI4F.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    final PVectorI4F<T> v0 = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);
    final PVectorI4F<T> v1 = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);
    final PVectorI4F<T> vw = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 1.0f);
    final PVectorI4F<T> vz = new PVectorI4F<T>(0.0f, 0.0f, 1.0f, 0.0f);
    final PVectorI4F<T> vy = new PVectorI4F<T>(0.0f, 1.0f, 0.0f, 0.0f);
    final PVectorI4F<T> vx = new PVectorI4F<T>(1.0f, 0.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(x, y, z, w);
      final PVectorI4F<T> m1 = new PVectorI4F<T>(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI4F<T> m0 = new PVectorI4F<T>();
    final PVectorI4F<T> m1 = new PVectorI4F<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(23, 0, 0, 1);
      final PVectorI4F<T> m1 = new PVectorI4F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(0, 23, 0, 1);
      final PVectorI4F<T> m1 = new PVectorI4F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(0, 0, 23, 1);
      final PVectorI4F<T> m1 = new PVectorI4F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI4F<T> m0 = new PVectorI4F<T>(0, 0, 0, 23);
      final PVectorI4F<T> m1 = new PVectorI4F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI4F<T> v0 = new PVectorI4F<T>(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorReadable4FType v1 = new PVectorI4F<T>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v1 = new PVectorI4F<T>(x1, y1, z1, w1);

      Assert.assertTrue(
        PVectorI4F.almostEqual(
          ec, PVectorI4F.interpolateLinear(v0, v1, 0.0f), v0));
      Assert.assertTrue(
        PVectorI4F.almostEqual(
          ec, PVectorI4F.interpolateLinear(v0, v1, 1.0f), v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float z = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float w = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      final double m = PVectorI4F.magnitude(v);
      Assert.assertTrue(m > 0.0f);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x = (float) (1.0f + (Math.random() * max));
      final float y = (float) (1.0f + (Math.random() * max));
      final float z = (float) (1.0f + (Math.random() * max));
      final float w = (float) (1.0f + (Math.random() * max));
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      final PVectorI4F<T> vr = PVectorI4F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = PVectorI4F.magnitude(vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorReadable4FType<T> v = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);
    final PVectorI4F<T> vr = PVectorI4F.normalize(v);
    final double m = PVectorI4F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorReadable4FType<T> v = new PVectorI4F<T>(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = PVectorI4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorReadable4FType<T> v = new PVectorI4F<T>(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = PVectorI4F.dotProduct(v, v);
      final double q = PVectorI4F.magnitudeSquared(v);
      final double r = PVectorI4F.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorReadable4FType<T> v = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = PVectorI4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(8.0f, 0.0f, 0.0f, 0.0f);
    final PVectorI4F<T> vr = PVectorI4F.normalize(v0);
    final float m = PVectorI4F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(0.0f, 0.0f, 0.0f, 0.0f);
    PVectorI4F.almostEqual(ec, PVectorI4F.normalize(v0), v0);
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorReadable4FType<T> v0 = new PVectorI4F<T>(0, 1, 0, 0);
    final PVectorReadable4FType<T> v1 = new PVectorI4F<T>(0.5f, 0.5f, 0, 0);
    final Pair<PVectorI4F<T>, PVectorI4F<T>> on =
      PVectorI4F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new PVectorI4F<T>(1, 0, 0, 0), on.getRight());
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorReadable4FType<T> p = new PVectorI4F<T>(1.0f, 0.0f, 0.0f, 0.0f);
      final PVectorReadable4FType<T> q = new PVectorI4F<T>(0.0f, 1.0f, 0.0f, 0.0f);
      final PVectorI4F<T> r = PVectorI4F.projection(p, q);
      Assert.assertTrue(PVectorI4F.magnitude(r) == 0.0);
    }

    {
      final PVectorReadable4FType<T> p = new PVectorI4F<T>(-1.0f, 0.0f, 0.0f, 0.0f);
      final PVectorReadable4FType<T> q = new PVectorI4F<T>(0.0f, 1.0f, 0.0f, 0.0f);
      final PVectorI4F<T> r = PVectorI4F.projection(p, q);
      Assert.assertTrue(PVectorI4F.magnitude(r) == 0.0);
    }
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI4F<T> v = new PVectorI4F<T>(x, y, z, w);

      final PVectorI4F<T> vr = PVectorI4F.scale(v, 1.0f);

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
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorReadable4FType<T> v = new PVectorI4F<T>(x, y, z, w);

      final PVectorI4F<T> vr = PVectorI4F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI4F<T> v = new PVectorI4F<T>(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue("[PVectorI4F 0.0 1.0 2.0 3.0]".equals(v.toString()));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI4F<T> v0 = new PVectorI4F<T>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI4F<T> v1 = new PVectorI4F<T>(x1, y1, z1, w1);

      final PVectorI4F<T> vr = PVectorI4F.subtract(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), v0.getYF() - v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getZF(), v0.getZF() - v1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getWF(), v0.getWF() - v1.getWF()));
    }
  }

}
