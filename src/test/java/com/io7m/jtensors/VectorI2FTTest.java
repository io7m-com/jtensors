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
import com.io7m.jaux.functional.Pair;

public class VectorI2FTTest extends VectorI2TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      final VectorI2FT<A> vr = VectorI2FT.absolute(v);

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

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x1, y1);

      final VectorI2FT<A> vr = VectorI2FT.add(v0, v1);

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

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x1, y1);

      final float r = (float) (Math.random() * 100.0f);
      final VectorI2FT<A> vr = VectorI2FT.addScaled(v0, v1, r);

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

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float q = z + 1.0f;

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, y);
      Assert.assertFalse(VectorI2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(x, q);
      Assert.assertFalse(VectorI2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, q);
      Assert.assertFalse(VectorI2FT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x0, y0);
      final VectorI2FT<A> v2 = new VectorI2FT<A>(x0, y0);

      Assert.assertTrue(VectorI2FT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI2FT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI2FT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorI2FT<A> v0 = VectorI2FT.normalize(new VectorI2FT<A>(x, y));
      final VectorI2FT<A> v1 = VectorI2FT.normalize(new VectorI2FT<A>(y, -x));
      final double angle = VectorI2FT.angle(v0, v1);

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
      final VectorI2FT<A> v0 = VectorI2FT.normalize(new VectorI2FT<A>(x, y));
      final VectorI2FT<A> v1 = VectorI2FT.normalize(new VectorI2FT<A>(-y, x));
      final double angle = VectorI2FT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorI2FT<A> v = new VectorI2FT<A>(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> maximum = new VectorI2FT<A>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clampMaximumByVector(v, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI2FT.clampMaximumByVector(v, maximum).getYF() <= maximum
          .getYF());
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> minimum = new VectorI2FT<A>(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clampMinimumByVector(v, minimum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI2FT.clampMinimumByVector(v, minimum).getYF() >= minimum
          .getYF());
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> minimum = new VectorI2FT<A>(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> maximum = new VectorI2FT<A>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clampByVector(v, minimum, maximum).getXF() <= maximum
          .getXF());
      Assert
        .assertTrue(VectorI2FT.clampByVector(v, minimum, maximum).getXF() >= minimum
          .getXF());
      Assert
        .assertTrue(VectorI2FT.clampByVector(v, minimum, maximum).getYF() <= maximum
          .getYF());
      Assert
        .assertTrue(VectorI2FT.clampByVector(v, minimum, maximum).getYF() >= minimum
          .getYF());
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clampMaximum(v, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI2FT.clampMaximum(v, maximum).getYF() <= maximum);
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clampMinimum(v, minimum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI2FT.clampMinimum(v, minimum).getYF() >= minimum);
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      Assert
        .assertTrue(VectorI2FT.clamp(v, minimum, maximum).getXF() <= maximum);
      Assert
        .assertTrue(VectorI2FT.clamp(v, minimum, maximum).getXF() >= minimum);
      Assert
        .assertTrue(VectorI2FT.clamp(v, minimum, maximum).getYF() <= maximum);
      Assert
        .assertTrue(VectorI2FT.clamp(v, minimum, maximum).getYF() >= minimum);
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();

      final VectorI2FT<A> v0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getXF(), x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getYF(), y));
    }
  }

  @Override @Test public <A> void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorI2FT<A> v = new VectorI2FT<A>();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2FT<A> v0 = new VectorI2FT<A>(0.0f, 1.0f);
    final VectorI2FT<A> v1 = new VectorI2FT<A>(0.0f, 0.0f);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorI2FT.distance(v0, v1),
      1.0));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x1, y1);

      Assert.assertTrue(VectorI2FT.distance(v0, v1) >= 0.0f);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorI2FT<A> v0 = new VectorI2FT<A>(10.0f, 10.0f);
    final VectorI2FT<A> v1 = new VectorI2FT<A>(10.0f, 10.0f);

    {
      final double p = VectorI2FT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorI2FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorI2FT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorI2FT<A> vpx = new VectorI2FT<A>(1.0f, 0.0f);
    final VectorI2FT<A> vmx = new VectorI2FT<A>(-1.0f, 0.0f);

    final VectorI2FT<A> vpy = new VectorI2FT<A>(0.0f, 1.0f);
    final VectorI2FT<A> vmy = new VectorI2FT<A>(0.0f, -1.0f);

    Assert.assertTrue(VectorI2FT.dotProduct(vpx, vpy) == 0.0f);
    Assert.assertTrue(VectorI2FT.dotProduct(vmx, vmy) == 0.0f);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorI2FT<A> q = new VectorI2FT<A>(x, y);
      final double dp = VectorI2FT.dotProduct(q, q);

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
      final VectorI2FT<A> q = new VectorI2FT<A>(x, y);

      final double ms = VectorI2FT.magnitudeSquared(q);
      final double dp = VectorI2FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>();
      final VectorI2FT<A> m1 = new VectorI2FT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(x, y);
      final VectorI2FT<A> m1 = new VectorI2FT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorI2FT<A> m0 = new VectorI2FT<A>();
    final VectorI2FT<A> m1 = new VectorI2FT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(23, 0);
      final VectorI2FT<A> m1 = new VectorI2FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2FT<A> m0 = new VectorI2FT<A>(0, 23);
      final VectorI2FT<A> m1 = new VectorI2FT<A>();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorI2FT<A> v0 = new VectorI2FT<A>(1.0f, 2.0f);
    final VectorI2FT<A> v1 = new VectorI2FT<A>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x1, y1);

      Assert.assertTrue(VectorI2FT.almostEqual(
        ec,
        VectorI2FT.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(VectorI2FT.almostEqual(
        ec,
        VectorI2FT.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      final double m = VectorI2FT.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      final VectorI2FT<A> vr = VectorI2FT.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI2FT.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);
      System.out.println("--");

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2FT<A> v = new VectorI2FT<A>(0.0f, 0.0f);
    final VectorI2FT<A> vr = VectorI2FT.normalize(v);
    final double m = VectorI2FT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2FT<A> v = new VectorI2FT<A>(1.0f, 0.0f);
    final double m = VectorI2FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorI2FT<A> v = new VectorI2FT<A>(8.0f, 0.0f);

    {
      final double p = VectorI2FT.dotProduct(v, v);
      final double q = VectorI2FT.magnitudeSquared(v);
      final double r = VectorI2FT.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2FT<A> v = new VectorI2FT<A>(0.0f, 0.0f);
    final double m = VectorI2FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI2FT<A> v0 = new VectorI2FT<A>(8.0f, 0.0f);
    final double dp = VectorI2FT.dotProduct(v0, v0);
    final double ms = VectorI2FT.magnitudeSquared(v0);
    final double mss = Math.sqrt(ms);
    final double m0 = VectorI2FT.magnitude(v0);
    final VectorI2FT<A> vr = VectorI2FT.normalize(v0);
    final double mn = VectorI2FT.magnitude(vr);

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

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorI2FT<A> q = new VectorI2FT<A>(0, 0);
    final VectorI2FT<A> qr = VectorI2FT.normalize(q);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorI2FT<A> v0 = new VectorI2FT<A>(0, 1);
    final VectorI2FT<A> v1 = new VectorI2FT<A>(0.5f, 0.5f);
    final Pair<VectorI2FT<A>, VectorI2FT<A>> on =
      VectorI2FT.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI2FT<A>(1, 0), on.second);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorI2FT<A> p = new VectorI2FT<A>(1.0f, 0.0f);
      final VectorI2FT<A> q = new VectorI2FT<A>(0.0f, 1.0f);
      final VectorI2FT<A> r = VectorI2FT.projection(p, q);
      Assert.assertTrue(VectorI2FT.magnitude(r) == 0.0f);
    }

    {
      final VectorI2FT<A> p = new VectorI2FT<A>(-1.0f, 0.0f);
      final VectorI2FT<A> q = new VectorI2FT<A>(0.0f, 1.0f);
      final VectorI2FT<A> r = VectorI2FT.projection(p, q);
      Assert.assertTrue(VectorI2FT.magnitude(r) == 0.0f);
    }
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      final VectorI2FT<A> vr = VectorI2FT.scale(v, 1.0f);

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

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v = new VectorI2FT<A>(x, y);

      final VectorI2FT<A> vr = VectorI2FT.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorI2FT<A> v = new VectorI2FT<A>(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[VectorI2FT 0.0 1.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v0 = new VectorI2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI2FT<A> v1 = new VectorI2FT<A>(x1, y1);

      final VectorI2FT<A> vr = VectorI2FT.subtract(v0, v1);

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
