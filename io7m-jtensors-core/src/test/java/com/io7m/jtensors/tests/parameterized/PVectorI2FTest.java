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
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.parameterized.PVectorI2F;
import com.io7m.jtensors.tests.TestUtilities;

@SuppressWarnings("static-method") public class PVectorI2FTest<T> extends
  PVectorI2Contract
{
  @Test public void testZero()
  {
    final PVectorI2F<Object> z = PVectorI2F.zero();
    Assert.assertEquals(0.0, z.getXF(), 0.0);
    Assert.assertEquals(0.0, z.getYF(), 0.0);
  }

  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      final PVectorI2F<T> vr = PVectorI2F.absolute(v);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getXF()),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getYF()),
        vr.getYF()));
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x1, y1);

      final PVectorI2F<T> vr = PVectorI2F.add(v0, v1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getYF(),
        v0.getYF() + v1.getYF()));
    }
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x1, y1);

      final float r = (float) (Math.random() * 100.0f);
      final PVectorI2F<T> vr = PVectorI2F.addScaled(v0, v1, r);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getXF(),
        v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getYF(),
        v0.getYF() + (v1.getYF() * r)));
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float q = z + 1.0f;

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, y);
      Assert.assertFalse(PVectorI2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(x, q);
      Assert.assertFalse(PVectorI2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, q);
      Assert.assertFalse(PVectorI2F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x0, y0);
      final PVectorI2F<T> v2 = new PVectorI2F<T>(x0, y0);

      Assert.assertTrue(PVectorI2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorI2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorI2F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorI2F<T> v0 = PVectorI2F.normalize(new PVectorI2F<T>(x, y));
      final PVectorI2F<T> v1 = PVectorI2F.normalize(new PVectorI2F<T>(y, -x));
      final double angle = PVectorI2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorI2F<T> v0 = PVectorI2F.normalize(new PVectorI2F<T>(x, y));
      final PVectorI2F<T> v1 = PVectorI2F.normalize(new PVectorI2F<T>(-y, x));
      final double angle = PVectorI2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorI2F<T> v = new PVectorI2F<T>(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> maximum = new PVectorI2F<T>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert
        .assertTrue(PVectorI2F.clampMaximumByPVector(v, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(PVectorI2F.clampMaximumByPVector(v, maximum).getYF() <= maximum
          .getYF());
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> minimum = new PVectorI2F<T>(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert
        .assertTrue(PVectorI2F.clampMinimumByPVector(v, minimum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(PVectorI2F.clampMinimumByPVector(v, minimum).getYF() >= minimum
          .getYF());
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> minimum = new PVectorI2F<T>(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> maximum = new PVectorI2F<T>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert.assertTrue(PVectorI2F
        .clampByPVector(v, minimum, maximum)
        .getXF() <= maximum.getXF());
      Assert.assertTrue(PVectorI2F
        .clampByPVector(v, minimum, maximum)
        .getXF() >= minimum.getXF());
      Assert.assertTrue(PVectorI2F
        .clampByPVector(v, minimum, maximum)
        .getYF() <= maximum.getYF());
      Assert.assertTrue(PVectorI2F
        .clampByPVector(v, minimum, maximum)
        .getYF() >= minimum.getYF());
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert
        .assertTrue(PVectorI2F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert
        .assertTrue(PVectorI2F.clampMaximum(v, maximum).getYF() <= maximum);
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert
        .assertTrue(PVectorI2F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert
        .assertTrue(PVectorI2F.clampMinimum(v, minimum).getYF() >= minimum);
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      Assert
        .assertTrue(PVectorI2F.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert
        .assertTrue(PVectorI2F.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert
        .assertTrue(PVectorI2F.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert
        .assertTrue(PVectorI2F.clamp(v, minimum, maximum).getYF() >= minimum);
    }
  }

  @Override @Test public void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();

      final PVectorI2F<T> v0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getXF(), x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getYF(), y));
    }
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorI2F<T> v = new PVectorI2F<T>();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2F<T> v0 = new PVectorI2F<T>(0.0f, 1.0f);
    final PVectorI2F<T> v1 = new PVectorI2F<T>(0.0f, 0.0f);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      PVectorI2F.distance(v0, v1),
      1.0));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x1, y1);

      Assert.assertTrue(PVectorI2F.distance(v0, v1) >= 0.0f);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorI2F<T> v0 = new PVectorI2F<T>(10.0f, 10.0f);
    final PVectorI2F<T> v1 = new PVectorI2F<T>(10.0f, 10.0f);

    {
      final double p = PVectorI2F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = PVectorI2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = PVectorI2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorI2F<T> vpx = new PVectorI2F<T>(1.0f, 0.0f);
    final PVectorI2F<T> vmx = new PVectorI2F<T>(-1.0f, 0.0f);

    final PVectorI2F<T> vpy = new PVectorI2F<T>(0.0f, 1.0f);
    final PVectorI2F<T> vmy = new PVectorI2F<T>(0.0f, -1.0f);

    Assert.assertTrue(PVectorI2F.dotProduct(vpx, vpy) == 0.0f);
    Assert.assertTrue(PVectorI2F.dotProduct(vmx, vmy) == 0.0f);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorI2F<T> q = new PVectorI2F<T>(x, y);
      final double dp = PVectorI2F.dotProduct(q, q);

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
      final PVectorI2F<T> q = new PVectorI2F<T>(x, y);

      final double ms = PVectorI2F.magnitudeSquared(q);
      final double dp = PVectorI2F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>();
      final PVectorI2F<T> m1 = new PVectorI2F<T>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(x, y);
      final PVectorI2F<T> m1 = new PVectorI2F<T>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorI2F<T> m0 = new PVectorI2F<T>();
    final PVectorI2F<T> m1 = new PVectorI2F<T>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(23, 0);
      final PVectorI2F<T> m1 = new PVectorI2F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorI2F<T> m0 = new PVectorI2F<T>(0, 23);
      final PVectorI2F<T> m1 = new PVectorI2F<T>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorI2F<T> v0 = new PVectorI2F<T>(1.0f, 2.0f);
    final PVectorI2F<T> v1 = new PVectorI2F<T>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x1, y1);

      Assert.assertTrue(PVectorI2F.almostEqual(
        ec,
        PVectorI2F.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(PVectorI2F.almostEqual(
        ec,
        PVectorI2F.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      final double m = PVectorI2F.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      final PVectorI2F<T> vr = PVectorI2F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = PVectorI2F.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);
      System.out.println("--");

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2F<T> v = new PVectorI2F<T>(0.0f, 0.0f);
    final PVectorI2F<T> vr = PVectorI2F.normalize(v);
    final double m = PVectorI2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2F<T> v = new PVectorI2F<T>(1.0f, 0.0f);
    final double m = PVectorI2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorI2F<T> v = new PVectorI2F<T>(8.0f, 0.0f);

    {
      final double p = PVectorI2F.dotProduct(v, v);
      final double q = PVectorI2F.magnitudeSquared(v);
      final double r = PVectorI2F.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2F<T> v = new PVectorI2F<T>(0.0f, 0.0f);
    final double m = PVectorI2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorI2F<T> v0 = new PVectorI2F<T>(8.0f, 0.0f);
    final double dp = PVectorI2F.dotProduct(v0, v0);
    final double ms = PVectorI2F.magnitudeSquared(v0);
    final double mss = Math.sqrt(ms);
    final double m0 = PVectorI2F.magnitude(v0);
    final PVectorI2F<T> vr = PVectorI2F.normalize(v0);
    final double mn = PVectorI2F.magnitude(vr);

    System.out.println("v0  : " + v0);
    System.out.println("dp  : " + dp);
    System.out.println("m0  : " + m0);
    System.out.println("ms  : " + ms);
    System.out.println("mss : " + mss);
    System.out.println("vr  : " + vr);
    System.out.println("mn  : " + mn);
    System.out.println("--");

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp, 64.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, mn, 1.0));
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorI2F<T> q = new PVectorI2F<T>(0, 0);
    final PVectorI2F<T> qr = PVectorI2F.normalize(q);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorI2F<T> v0 = new PVectorI2F<T>(0, 1);
    final PVectorI2F<T> v1 = new PVectorI2F<T>(0.5f, 0.5f);
    final Pair<PVectorI2F<T>, PVectorI2F<T>> on =
      PVectorI2F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new PVectorI2F<T>(1, 0), on.getRight());
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorI2F<T> p = new PVectorI2F<T>(1.0f, 0.0f);
      final PVectorI2F<T> q = new PVectorI2F<T>(0.0f, 1.0f);
      final PVectorI2F<T> r = PVectorI2F.projection(p, q);
      Assert.assertTrue(PVectorI2F.magnitude(r) == 0.0f);
    }

    {
      final PVectorI2F<T> p = new PVectorI2F<T>(-1.0f, 0.0f);
      final PVectorI2F<T> q = new PVectorI2F<T>(0.0f, 1.0f);
      final PVectorI2F<T> r = PVectorI2F.projection(p, q);
      Assert.assertTrue(PVectorI2F.magnitude(r) == 0.0f);
    }
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      final PVectorI2F<T> vr = PVectorI2F.scale(v, 1.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getYF(),
        vr.getYF()));
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v = new PVectorI2F<T>(x, y);

      final PVectorI2F<T> vr = PVectorI2F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
    }
  }

  @Override @Test public void testString()
  {
    final PVectorI2F<T> v = new PVectorI2F<T>(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[PVectorI2F 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v0 = new PVectorI2F<T>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorI2F<T> v1 = new PVectorI2F<T>(x1, y1);

      final PVectorI2F<T> vr = PVectorI2F.subtract(v0, v1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr.getYF(),
        v0.getYF() - v1.getYF()));
    }
  }
}
