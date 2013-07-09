/*
 * Copyright © 2012 http://io7m.com
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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorI4FTTest extends VectorI4TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      final VectorI4FT<A> vr = VectorI4FT.absolute(v);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getXF()),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getYF()),
        vr.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getZF()),
        vr.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getWF()),
        vr.getWF()));
    }
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x1, y1, z1, w1);

      final VectorI4FT<A> vr = VectorI4FT.add(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();

      {
        final float expected = v0.getXF() + v1.getXF();
        final float got = vr.getXF();
        System.out.println("x: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getYF() + v1.getYF();
        final float got = vr.getYF();
        System.out.println("y: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getZF() + v1.getZF();
        final float got = vr.getZF();
        System.out.println("z: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getWF() + v1.getWF();
        final float got = vr.getWF();
        System.out.println("w: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }
    }
  }

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x1, y1, z1, w1);

      final float r = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4FT<A> vr = VectorI4FT.addScaled(v0, v1, r);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();

      {
        final float expected = v0.getXF() + (v1.getXF() * r);
        final float got = vr.getXF();
        System.out.println("x: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getYF() + (v1.getYF() * r);
        final float got = vr.getYF();
        System.out.println("y: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getZF() + (v1.getZF() * r);
        final float got = vr.getZF();
        System.out.println("z: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }

      {
        final float expected = v0.getWF() + (v1.getWF() * r);
        final float got = vr.getWF();
        System.out.println("w: expected " + expected + " got " + got);
        Assert.assertTrue(AlmostEqualFloat
          .almostEqual(context, expected, got));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, z, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, q, z, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, q, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, z, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, z, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, q, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, z, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, q, w);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, z, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, q, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, q, q, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, q, q);
      Assert.assertFalse(VectorI4FT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x0, y0, z0, w0);
      final VectorI4FT<A> v2 = new VectorI4FT<A>(x0, y0, z0, w0);

      Assert.assertTrue(VectorI4FT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI4FT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI4FT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI4FT<A> v = new VectorI4FT<A>(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> maximum =
        new VectorI4FT<A>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clampMaximumByVector(v, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI4FT.clampMaximumByVector(v, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(VectorI4FT.clampMaximumByVector(v, maximum).getZF() <= maximum
          .getZF());
      Assert
        .assertTrue(VectorI4FT.clampMaximumByVector(v, maximum).getWF() <= maximum
          .getWF());
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> minimum =
        new VectorI4FT<A>(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clampMinimumByVector(v, minimum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI4FT.clampMinimumByVector(v, minimum).getYF() >= minimum
          .getYF());
      Assert
        .assertTrue(VectorI4FT.clampMinimumByVector(v, minimum).getZF() >= minimum
          .getZF());
      Assert
        .assertTrue(VectorI4FT.clampMinimumByVector(v, minimum).getWF() >= minimum
          .getWF());
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final float min_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> minimum =
        new VectorI4FT<A>(min_x, min_y, min_z, min_w);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final float max_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> maximum =
        new VectorI4FT<A>(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getYF() >= minimum
          .getYF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getZF() <= maximum
          .getZF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getZF() >= minimum
          .getZF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getWF() <= maximum
          .getWF());
      Assert
        .assertTrue(VectorI4FT.clampByVector(v, minimum, maximum).getWF() >= minimum
          .getWF());
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clampMaximum(v, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clampMaximum(v, maximum).getYF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clampMaximum(v, maximum).getZF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clampMaximum(v, maximum).getWF() <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clampMinimum(v, minimum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clampMinimum(v, minimum).getYF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clampMinimum(v, minimum).getZF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clampMinimum(v, minimum).getWF() >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getYF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getZF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getZF() >= minimum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getWF() <= maximum);
      Assert
        .assertTrue(VectorI4FT.clamp(v, minimum, maximum).getWF() >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> vc = new VectorI4FT<A>(v);

      Assert.assertTrue(VectorI4FT.almostEqual(ec, v, vc));
    }
  }

  @Override @Test public <A> void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final VectorI4FT<A> v = new VectorI4FT<A>();
    VectorI4FT.almostEqual(context, v, new VectorI4FT<A>(0, 0, 0, 1));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4FT<A> v1 = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      VectorI4FT.distance(v0, v1),
      1.0f));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4FT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(10.0f, 10.0f, 10.0f, 10.0f);
    final VectorI4FT<A> v1 = new VectorI4FT<A>(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorI4FT.dotProduct(v0, v1);
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
      final double p = VectorI4FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = VectorI4FT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorI4FT<A> vpx = new VectorI4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vmx = new VectorI4FT<A>(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorI4FT<A> vpy = new VectorI4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vmy = new VectorI4FT<A>(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorI4FT<A> vpz = new VectorI4FT<A>(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorI4FT<A> vmz = new VectorI4FT<A>(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorI4FT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI4FT.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI4FT.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI4FT.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final VectorI4FT<A> q = new VectorI4FT<A>(x, y, z, w);
      final double dp = VectorI4FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final float w = (float) Math.random();
      final VectorI4FT<A> q = new VectorI4FT<A>(x, y, z, w);

      final double ms = VectorI4FT.magnitudeSquared(q);
      final double dp = VectorI4FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4FT<A> v1 = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vw = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 1.0f);
    final VectorI4FT<A> vz = new VectorI4FT<A>(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorI4FT<A> vy = new VectorI4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vx = new VectorI4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(x, y, z, w);
      final VectorI4FT<A> m1 = new VectorI4FT<A>(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI4FT<A> m0 = new VectorI4FT<A>();
    final VectorI4FT<A> m1 = new VectorI4FT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(23, 0, 0, 1);
      final VectorI4FT<A> m1 = new VectorI4FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(0, 23, 0, 1);
      final VectorI4FT<A> m1 = new VectorI4FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(0, 0, 23, 1);
      final VectorI4FT<A> m1 = new VectorI4FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4FT<A> m0 = new VectorI4FT<A>(0, 0, 0, 23);
      final VectorI4FT<A> m1 = new VectorI4FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorI4FT<A> v1 = new VectorI4FT<A>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4FT.almostEqual(
        ec,
        VectorI4FT.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(VectorI4FT.almostEqual(
        ec,
        VectorI4FT.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float z = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float w = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      final double m = VectorI4FT.magnitude(v);
      Assert.assertTrue(m > 0.0f);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x = (float) (1.0f + (Math.random() * max));
      final float y = (float) (1.0f + (Math.random() * max));
      final float z = (float) (1.0f + (Math.random() * max));
      final float w = (float) (1.0f + (Math.random() * max));
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      final VectorI4FT<A> vr = VectorI4FT.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI4FT.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4FT<A> v = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vr = VectorI4FT.normalize(v);
    final double m = VectorI4FT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4FT<A> v = new VectorI4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorI4FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI4FT<A> v = new VectorI4FT<A>(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = VectorI4FT.dotProduct(v, v);
      final double q = VectorI4FT.magnitudeSquared(v);
      final double r = VectorI4FT.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI4FT<A> v = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorI4FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4FT<A> vr = VectorI4FT.normalize(v0);
    final float m = VectorI4FT.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorI4FT<A> v0 = new VectorI4FT<A>(0.0f, 0.0f, 0.0f, 0.0f);
    VectorI4FT.almostEqual(ec, VectorI4FT.normalize(v0), v0);
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorI4FT<A> v0 = new VectorI4FT<A>(0, 1, 0, 0);
    final VectorI4FT<A> v1 = new VectorI4FT<A>(0.5f, 0.5f, 0, 0);
    final Pair<VectorI4FT<A>, VectorI4FT<A>> on =
      VectorI4FT.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI4FT<A>(1, 0, 0, 0), on.second);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI4FT<A> p = new VectorI4FT<A>(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorI4FT<A> q = new VectorI4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4FT<A> r = VectorI4FT.projection(p, q);
      Assert.assertTrue(VectorI4FT.magnitude(r) == 0.0);
    }

    {
      final VectorI4FT<A> p = new VectorI4FT<A>(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorI4FT<A> q = new VectorI4FT<A>(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4FT<A> r = VectorI4FT.projection(p, q);
      Assert.assertTrue(VectorI4FT.magnitude(r) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      final VectorI4FT<A> vr = VectorI4FT.scale(v, 1.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getYF(),
        vr.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getZF(),
        vr.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getWF(),
        vr.getWF()));
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v = new VectorI4FT<A>(x, y, z, w);

      final VectorI4FT<A> vr = VectorI4FT.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI4FT<A> v = new VectorI4FT<A>(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorI4FT 0.0 1.0 2.0 3.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v0 = new VectorI4FT<A>(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4FT<A> v1 = new VectorI4FT<A>(x1, y1, z1, w1);

      final VectorI4FT<A> vr = VectorI4FT.subtract(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        v0.getYF() - v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getZF(),
        v0.getZF() - v1.getZF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getWF(),
        v0.getWF() - v1.getWF()));
    }
  }

}
