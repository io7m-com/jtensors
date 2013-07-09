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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorI3FTTest extends VectorI3TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      final VectorI3FT<A> vr = VectorI3FT.absolute(v);

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
    }
  }

  @Override @Test public <A> void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x1, y1, z1);

      final VectorI3FT<A> vr = VectorI3FT.add(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        v0.getYF() + v1.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getZF(),
        v0.getZF() + v1.getZF()));
    }
  }

  @Override @Test public <A> void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x1, y1, z1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> vr = VectorI3FT.addScaled(v0, v1, r);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        v0.getYF() + (v1.getYF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getZF(),
        v0.getZF() + (v1.getZF() * r)));
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
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, z);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, q, z);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, y, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, z);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, z);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, z);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, q, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, y, q);
      Assert.assertFalse(VectorI3FT.almostEqual(ec, m0, m1));
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
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x0, y0, z0);
      final VectorI3FT<A> v2 = new VectorI3FT<A>(x0, y0, z0);

      Assert.assertTrue(VectorI3FT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI3FT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI3FT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI3FT<A> v = new VectorI3FT<A>(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> maximum = new VectorI3FT<A>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clampMaximumByVector(v, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI3FT.clampMaximumByVector(v, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(VectorI3FT.clampMaximumByVector(v, maximum).getZF() <= maximum
          .getZF());
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> minimum = new VectorI3FT<A>(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clampMinimumByVector(v, minimum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI3FT.clampMinimumByVector(v, minimum).getYF() >= minimum
          .getYF());
      Assert
        .assertTrue(VectorI3FT.clampMinimumByVector(v, minimum).getZF() >= minimum
          .getZF());
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> minimum = new VectorI3FT<A>(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> maximum = new VectorI3FT<A>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getYF() >= minimum
          .getYF());
      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getZF() <= maximum
          .getZF());
      Assert
        .assertTrue(VectorI3FT.clampByVector(v, minimum, maximum).getZF() >= minimum
          .getZF());
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clampMaximum(v, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI3FT.clampMaximum(v, maximum).getYF() <= maximum);
      Assert
        .assertTrue(VectorI3FT.clampMaximum(v, maximum).getZF() <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clampMinimum(v, minimum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI3FT.clampMinimum(v, minimum).getYF() >= minimum);
      Assert
        .assertTrue(VectorI3FT.clampMinimum(v, minimum).getZF() >= minimum);
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
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getYF() >= minimum);
      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getZF() <= maximum);
      Assert
        .assertTrue(VectorI3FT.clamp(v, minimum, maximum).getZF() >= minimum);
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
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> vc = new VectorI3FT<A>(v);

      Assert.assertTrue(VectorI3FT.almostEqual(ec, v, vc));
    }
  }

  @Override @Test public <A> void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final VectorI3FT<A> v0 =
        VectorI3FT.normalize(new VectorI3FT<A>(x0, y0, z0));

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final VectorI3FT<A> v1 =
        VectorI3FT.normalize(new VectorI3FT<A>(x1, y1, z1));

      final VectorI3FT<A> vr =
        VectorI3FT.normalize(VectorI3FT.crossProduct(v0, v1));

      final double dp0 = VectorI3FT.dotProduct(v0, vr);
      final double dp1 = VectorI3FT.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + VectorI3FT.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + VectorI3FT.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + VectorI3FT.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public <A> void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final VectorI3FT<A> v = new VectorI3FT<A>();
    VectorI3FT.almostEqual(context, v, new VectorI3FT<A>(0, 0, 0));
  }

  @Override @Test public <A> void testDistance()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(0.0f, 1.0f, 0.0f);
    final VectorI3FT<A> v1 = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      VectorI3FT.distance(v0, v1),
      1.0f));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x1, y1, z1);

      Assert.assertTrue(VectorI3FT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(10.0f, 10.0f, 10.0f);
    final VectorI3FT<A> v1 = new VectorI3FT<A>(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorI3FT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = VectorI3FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = VectorI3FT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorI3FT<A> vpx = new VectorI3FT<A>(1.0f, 0.0f, 0.0f);
    final VectorI3FT<A> vmx = new VectorI3FT<A>(-1.0f, 0.0f, 0.0f);

    final VectorI3FT<A> vpy = new VectorI3FT<A>(0.0f, 1.0f, 0.0f);
    final VectorI3FT<A> vmy = new VectorI3FT<A>(0.0f, -1.0f, 0.0f);

    final VectorI3FT<A> vpz = new VectorI3FT<A>(0.0f, 0.0f, 1.0f);
    final VectorI3FT<A> vmz = new VectorI3FT<A>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorI3FT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI3FT.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI3FT.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI3FT.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final VectorI3FT<A> q = new VectorI3FT<A>(x, y, z);
      final double dp = VectorI3FT.dotProduct(q, q);

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
      final VectorI3FT<A> q = new VectorI3FT<A>(x, y, z);

      final double ms = VectorI3FT.magnitudeSquared(q);
      final double dp = VectorI3FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);
    final VectorI3FT<A> v1 = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);
    final VectorI3FT<A> vz = new VectorI3FT<A>(0.0f, 0.0f, 1.0f);
    final VectorI3FT<A> vy = new VectorI3FT<A>(0.0f, 1.0f, 0.0f);
    final VectorI3FT<A> vx = new VectorI3FT<A>(1.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
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
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(x, y, z);
      final VectorI3FT<A> m1 = new VectorI3FT<A>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI3FT<A> m0 = new VectorI3FT<A>();
    final VectorI3FT<A> m1 = new VectorI3FT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(23, 0, 0);
      final VectorI3FT<A> m1 = new VectorI3FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(0, 23, 0);
      final VectorI3FT<A> m1 = new VectorI3FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3FT<A> m0 = new VectorI3FT<A>(0, 0, 23);
      final VectorI3FT<A> m1 = new VectorI3FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(1.0f, 2.0f, 3.0f);
    final VectorI3FT<A> v1 = new VectorI3FT<A>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x1, y1, z1);

      Assert.assertTrue(VectorI3FT.almostEqual(
        ec,
        VectorI3FT.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(VectorI3FT.almostEqual(
        ec,
        VectorI3FT.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float z = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      final double m = VectorI3FT.magnitude(v);
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
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      final VectorI3FT<A> vr = VectorI3FT.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI3FT.magnitude(vr);

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

    final VectorI3FT<A> v = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);
    final VectorI3FT<A> vr = VectorI3FT.normalize(v);
    final double m = VectorI3FT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3FT<A> v = new VectorI3FT<A>(1.0f, 0.0f, 0.0f);
    final double m = VectorI3FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI3FT<A> v = new VectorI3FT<A>(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorI3FT.dotProduct(v, v);
      final double q = VectorI3FT.magnitudeSquared(v);
      final double r = VectorI3FT.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3FT<A> v = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);
    final double m = VectorI3FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(8.0f, 0.0f, 0.0f);
    final VectorI3FT<A> vr = VectorI3FT.normalize(v0);
    final float m = VectorI3FT.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorI3FT<A> v0 = new VectorI3FT<A>(0.0f, 0.0f, 0.0f);
    VectorI3FT.almostEqual(ec, VectorI3FT.normalize(v0), v0);
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorI3FT<A> v0 = new VectorI3FT<A>(0, 1, 0);
    final VectorI3FT<A> v1 = new VectorI3FT<A>(0.5f, 0.5f, 0);
    final Pair<VectorI3FT<A>, VectorI3FT<A>> on =
      VectorI3FT.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI3FT<A>(1, 0, 0), on.second);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI3FT<A> p = new VectorI3FT<A>(1.0f, 0.0f, 0.0f);
      final VectorI3FT<A> q = new VectorI3FT<A>(0.0f, 1.0f, 0.0f);
      final VectorI3FT<A> r = VectorI3FT.projection(p, q);
      Assert.assertTrue(VectorI3FT.magnitude(r) == 0.0);
    }

    {
      final VectorI3FT<A> p = new VectorI3FT<A>(-1.0f, 0.0f, 0.0f);
      final VectorI3FT<A> q = new VectorI3FT<A>(0.0f, 1.0f, 0.0f);
      final VectorI3FT<A> r = VectorI3FT.projection(p, q);
      Assert.assertTrue(VectorI3FT.magnitude(r) == 0.0);
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
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      final VectorI3FT<A> vr = VectorI3FT.scale(v, 1.0f);

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
      final VectorI3FT<A> v = new VectorI3FT<A>(x, y, z);

      final VectorI3FT<A> vr = VectorI3FT.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI3FT<A> v = new VectorI3FT<A>(0.0f, 1.0f, 2.0f);
    Assert.assertTrue(v.toString().equals("[VectorI3FT 0.0 1.0 2.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v0 = new VectorI3FT<A>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3FT<A> v1 = new VectorI3FT<A>(x1, y1, z1);

      final VectorI3FT<A> vr = VectorI3FT.subtract(v0, v1);

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
    }
  }
}
