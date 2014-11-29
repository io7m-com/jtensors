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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.parameterized.PVectorI3F;
import com.io7m.jtensors.tests.TestUtilities;

@SuppressWarnings("static-method") public class PVectorI3FTest<T> extends
  PVectorI3Contract
{
  @Test public void testZero()
  {
    final PVectorI3F<Object> z = PVectorI3F.zero();
    Assert.assertEquals(0.0, z.getXF(), 0.0);
    Assert.assertEquals(0.0, z.getYF(), 0.0);
    Assert.assertEquals(0.0, z.getZF(), 0.0);
  }

  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      final PVectorI3F<T> vr = PVectorI3F.absolute(v);

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

  @Override @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x1, y1, z1);

      final PVectorI3F<T> vr = PVectorI3F.add(v0, v1);

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

  @Override @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x1, y1, z1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> vr = PVectorI3F.addScaled(v0, v1, r);

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
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, z);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, q, z);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, y, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, z);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, z);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, z);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, q, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, y, q);
      Assert.assertFalse(PVectorI3F.almostEqual(ec, m0, m1));
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
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x0, y0, z0);
      final PVectorI3F<T> v2 = new PVectorI3F<T>(x0, y0, z0);

      Assert.assertTrue(PVectorI3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorI3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorI3F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorI3F<T> v = new PVectorI3F<T>(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> maximum = new PVectorI3F<T>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert
        .assertTrue(PVectorI3F.clampMaximumByPVector(v, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(PVectorI3F.clampMaximumByPVector(v, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(PVectorI3F.clampMaximumByPVector(v, maximum).getZF() <= maximum
          .getZF());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> minimum = new PVectorI3F<T>(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert
        .assertTrue(PVectorI3F.clampMinimumByPVector(v, minimum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(PVectorI3F.clampMinimumByPVector(v, minimum).getYF() >= minimum
          .getYF());
      Assert
        .assertTrue(PVectorI3F.clampMinimumByPVector(v, minimum).getZF() >= minimum
          .getZF());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> minimum = new PVectorI3F<T>(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> maximum = new PVectorI3F<T>(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getXF() <= maximum.getXF());
      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getXF() >= minimum.getXF());
      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getYF() <= maximum.getYF());
      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getYF() >= minimum.getYF());
      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getZF() <= maximum.getZF());
      Assert.assertTrue(PVectorI3F
        .clampByPVector(v, minimum, maximum)
        .getZF() >= minimum.getZF());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert
        .assertTrue(PVectorI3F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert
        .assertTrue(PVectorI3F.clampMaximum(v, maximum).getYF() <= maximum);
      Assert
        .assertTrue(PVectorI3F.clampMaximum(v, maximum).getZF() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert
        .assertTrue(PVectorI3F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert
        .assertTrue(PVectorI3F.clampMinimum(v, minimum).getYF() >= minimum);
      Assert
        .assertTrue(PVectorI3F.clampMinimum(v, minimum).getZF() >= minimum);
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
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getYF() >= minimum);
      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getZF() <= maximum);
      Assert
        .assertTrue(PVectorI3F.clamp(v, minimum, maximum).getZF() >= minimum);
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
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> vc = new PVectorI3F<T>(v);

      Assert.assertTrue(PVectorI3F.almostEqual(ec, v, vc));
    }
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final PVectorI3F<T> v0 =
        PVectorI3F.normalize(new PVectorI3F<T>(x0, y0, z0));

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final PVectorI3F<T> v1 =
        PVectorI3F.normalize(new PVectorI3F<T>(x1, y1, z1));

      final PVectorI3F<T> vr =
        PVectorI3F.normalize(PVectorI3F.crossProduct(v0, v1));

      final double dp0 = PVectorI3F.dotProduct(v0, vr);
      final double dp1 = PVectorI3F.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + PVectorI3F.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + PVectorI3F.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + PVectorI3F.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final PVectorI3F<T> v = new PVectorI3F<T>();
    PVectorI3F.almostEqual(context, v, new PVectorI3F<T>(0, 0, 0));
  }

  @Override @Test public void testDistance()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(0.0f, 1.0f, 0.0f);
    final PVectorI3F<T> v1 = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(
      context,
      PVectorI3F.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x1, y1, z1);

      Assert.assertTrue(PVectorI3F.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(10.0f, 10.0f, 10.0f);
    final PVectorI3F<T> v1 = new PVectorI3F<T>(10.0f, 10.0f, 10.0f);

    {
      final double p = PVectorI3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = PVectorI3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = PVectorI3F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorI3F<T> vpx = new PVectorI3F<T>(1.0f, 0.0f, 0.0f);
    final PVectorI3F<T> vmx = new PVectorI3F<T>(-1.0f, 0.0f, 0.0f);

    final PVectorI3F<T> vpy = new PVectorI3F<T>(0.0f, 1.0f, 0.0f);
    final PVectorI3F<T> vmy = new PVectorI3F<T>(0.0f, -1.0f, 0.0f);

    final PVectorI3F<T> vpz = new PVectorI3F<T>(0.0f, 0.0f, 1.0f);
    final PVectorI3F<T> vmz = new PVectorI3F<T>(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(PVectorI3F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorI3F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(PVectorI3F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(PVectorI3F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final PVectorI3F<T> q = new PVectorI3F<T>(x, y, z);
      final double dp = PVectorI3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

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
      final PVectorI3F<T> q = new PVectorI3F<T>(x, y, z);

      final double ms = PVectorI3F.magnitudeSquared(q);
      final double dp = PVectorI3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);
    final PVectorI3F<T> v1 = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);
    final PVectorI3F<T> vz = new PVectorI3F<T>(0.0f, 0.0f, 1.0f);
    final PVectorI3F<T> vy = new PVectorI3F<T>(0.0f, 1.0f, 0.0f);
    final PVectorI3F<T> vx = new PVectorI3F<T>(1.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
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
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(x, y, z);
      final PVectorI3F<T> m1 = new PVectorI3F<T>(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI3F<T> m0 = new PVectorI3F<T>();
    final PVectorI3F<T> m1 = new PVectorI3F<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(23, 0, 0);
      final PVectorI3F<T> m1 = new PVectorI3F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(0, 23, 0);
      final PVectorI3F<T> m1 = new PVectorI3F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI3F<T> m0 = new PVectorI3F<T>(0, 0, 23);
      final PVectorI3F<T> m1 = new PVectorI3F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(1.0f, 2.0f, 3.0f);
    final PVectorI3F<T> v1 = new PVectorI3F<T>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x1, y1, z1);

      Assert.assertTrue(PVectorI3F.almostEqual(
        ec,
        PVectorI3F.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(PVectorI3F.almostEqual(
        ec,
        PVectorI3F.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float z = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      final double m = PVectorI3F.magnitude(v);
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
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      final PVectorI3F<T> vr = PVectorI3F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = PVectorI3F.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3F<T> v = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);
    final PVectorI3F<T> vr = PVectorI3F.normalize(v);
    final double m = PVectorI3F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3F<T> v = new PVectorI3F<T>(1.0f, 0.0f, 0.0f);
    final double m = PVectorI3F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI3F<T> v = new PVectorI3F<T>(8.0f, 0.0f, 0.0f);

    {
      final double p = PVectorI3F.dotProduct(v, v);
      final double q = PVectorI3F.magnitudeSquared(v);
      final double r = PVectorI3F.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI3F<T> v = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);
    final double m = PVectorI3F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(8.0f, 0.0f, 0.0f);
    final PVectorI3F<T> vr = PVectorI3F.normalize(v0);
    final float m = PVectorI3F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorI3F<T> v0 = new PVectorI3F<T>(0.0f, 0.0f, 0.0f);
    PVectorI3F.almostEqual(ec, PVectorI3F.normalize(v0), v0);
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorI3F<T> v0 = new PVectorI3F<T>(0, 1, 0);
    final PVectorI3F<T> v1 = new PVectorI3F<T>(0.5f, 0.5f, 0);
    final Pair<PVectorI3F<T>, PVectorI3F<T>> on =
      PVectorI3F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new PVectorI3F<T>(1, 0, 0), on.getRight());
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorI3F<T> p = new PVectorI3F<T>(1.0f, 0.0f, 0.0f);
      final PVectorI3F<T> q = new PVectorI3F<T>(0.0f, 1.0f, 0.0f);
      final PVectorI3F<T> r = PVectorI3F.projection(p, q);
      Assert.assertTrue(PVectorI3F.magnitude(r) == 0.0);
    }

    {
      final PVectorI3F<T> p = new PVectorI3F<T>(-1.0f, 0.0f, 0.0f);
      final PVectorI3F<T> q = new PVectorI3F<T>(0.0f, 1.0f, 0.0f);
      final PVectorI3F<T> r = PVectorI3F.projection(p, q);
      Assert.assertTrue(PVectorI3F.magnitude(r) == 0.0);
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
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      final PVectorI3F<T> vr = PVectorI3F.scale(v, 1.0f);

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

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v = new PVectorI3F<T>(x, y, z);

      final PVectorI3F<T> vr = PVectorI3F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI3F<T> v = new PVectorI3F<T>(0.0f, 1.0f, 2.0f);
    Assert.assertTrue(v.toString().equals("[PVectorI3F 0.0 1.0 2.0]"));
  }

  @Override @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v0 = new PVectorI3F<T>(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI3F<T> v1 = new PVectorI3F<T>(x1, y1, z1);

      final PVectorI3F<T> vr = PVectorI3F.subtract(v0, v1);

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
