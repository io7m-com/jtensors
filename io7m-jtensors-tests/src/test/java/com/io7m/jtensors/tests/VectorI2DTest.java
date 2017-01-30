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
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorReadable2DType;
import org.junit.Assert;
import org.junit.Test;

public class VectorI2DTest extends VectorI2Contract
{
  @Override
  @Test
  public void testAbsolute()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      final VectorI2D vr = VectorI2D.absolute(v);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getXD()), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, Math.abs(v.getYD()), vr.getYD()));
    }
  }

  @Override
  @Test
  public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorReadable2DType v1 = new VectorI2D(x1, y1);

      final VectorI2D vr = VectorI2D.add(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() + v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() + v1.getYD()));
    }
  }

  @Override
  @Test
  public void testAddScaled()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final VectorReadable2DType v1 = new VectorI2D(x1, y1);

      final double r = Math.random() * 100.0;
      final VectorI2D vr = VectorI2D.addScaled(v0, v1, r);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() + (v1.getXD() * r)));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() + (v1.getYD() * r)));
    }
  }

  @Override
  @Test
  public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double q = z + 1.0;

    {
      final VectorReadable2DType m0 = new VectorI2D(x, y);
      final VectorReadable2DType m1 = new VectorI2D(q, y);
      Assert.assertFalse(VectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2DType m0 = new VectorI2D(x, y);
      final VectorReadable2DType m1 = new VectorI2D(x, q);
      Assert.assertFalse(VectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2DType m0 = new VectorI2D(x, y);
      final VectorReadable2DType m1 = new VectorI2D(q, q);
      Assert.assertFalse(VectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2DType m0 = new VectorI2D(x, y);
      final VectorReadable2DType m1 = new VectorI2D(q, y);
      Assert.assertFalse(VectorI2D.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2DType m0 = new VectorI2D(x, y);
      final VectorReadable2DType m1 = new VectorI2D(q, q);
      Assert.assertFalse(VectorI2D.almostEqual(ec, m0, m1));
    }
  }

  @Override
  @Test
  public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);
      final VectorReadable2DType v1 = new VectorI2D(x0, y0);
      final VectorReadable2DType v2 = new VectorI2D(x0, y0);

      Assert.assertTrue(VectorI2D.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI2D.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI2D.almostEqual(ec, v0, v2));
    }
  }

  @Override
  @Test
  public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final double x = 1.0 + Math.random();
      final double y = 1.0 + Math.random();
      final VectorI2D v0 = VectorI2D.normalize(new VectorI2D(x, y));
      final VectorI2D v1 = VectorI2D.normalize(new VectorI2D(y, -x));
      final double angle = VectorI2D.angle(v0, v1);


      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final double x = 1.0 + Math.random();
      final double y = 1.0 + Math.random();
      final VectorI2D v0 = VectorI2D.normalize(new VectorI2D(x, y));
      final VectorI2D v1 = VectorI2D.normalize(new VectorI2D(-y, x));
      final double angle = VectorI2D.angle(v0, v1);


      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override
  @Test
  public void testCheckInterface()
  {
    final VectorReadable2DType v = new VectorI2D(3.0, 5.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
  }

  @Override
  @Test
  public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType maximum = new VectorI2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(
        VectorI2D.clampMaximumByVector(v, maximum).getXD()
          <= maximum.getXD());
      Assert.assertTrue(
        VectorI2D.clampMaximumByVector(v, maximum).getYD()
          <= maximum.getYD());
    }
  }

  @Override
  @Test
  public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType minimum = new VectorI2D(min_x, min_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(
        VectorI2D.clampMinimumByVector(v, minimum).getXD()
          >= minimum.getXD());
      Assert.assertTrue(
        VectorI2D.clampMinimumByVector(v, minimum).getYD()
          >= minimum.getYD());
    }
  }

  @Override
  @Test
  public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType minimum = new VectorI2D(min_x, min_y);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType maximum = new VectorI2D(max_x, max_y);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(
        VectorI2D.clampByVector(v, minimum, maximum).getXD()
          <= maximum.getXD());
      Assert.assertTrue(
        VectorI2D.clampByVector(v, minimum, maximum).getXD()
          >= minimum.getXD());
      Assert.assertTrue(
        VectorI2D.clampByVector(v, minimum, maximum).getYD()
          <= maximum.getYD());
      Assert.assertTrue(
        VectorI2D.clampByVector(v, minimum, maximum).getYD()
          >= minimum.getYD());
    }
  }

  @Override
  @Test
  public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(VectorI2D.clampMaximum(v, maximum).getXD() <= maximum);
      Assert.assertTrue(VectorI2D.clampMaximum(v, maximum).getYD() <= maximum);
    }
  }

  @Override
  @Test
  public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(VectorI2D.clampMinimum(v, minimum).getXD() >= minimum);
      Assert.assertTrue(VectorI2D.clampMinimum(v, minimum).getYD() >= minimum);
    }
  }

  @Override
  @Test
  public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      Assert.assertTrue(
        VectorI2D.clamp(v, minimum, maximum).getXD()
          <= maximum);
      Assert.assertTrue(
        VectorI2D.clamp(v, minimum, maximum).getXD()
          >= minimum);
      Assert.assertTrue(
        VectorI2D.clamp(v, minimum, maximum).getYD()
          <= maximum);
      Assert.assertTrue(
        VectorI2D.clamp(v, minimum, maximum).getYD()
          >= minimum);
    }
  }

  @Override
  @Test
  public void testCopy()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();

      final VectorI2D v0 = new VectorI2D(x, y);
      final VectorReadable2DType v1 = new VectorI2D(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getXD(), x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getYD(), y));
    }
  }

  @Override
  @Test
  public void testDefault00()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType v = new VectorI2D();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
  }

  @Override
  @Test
  public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType v0 = new VectorI2D(0.0, 1.0);
    final VectorReadable2DType v1 = new VectorI2D(0.0, 0.0);

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorI2D.distance(v0, v1), 1.0));
  }

  @Override
  @Test
  public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v1 = new VectorI2D(x1, y1);

      Assert.assertTrue(VectorI2D.distance(v0, v1) >= 0.0);
    }
  }

  @Override
  @Test
  public void testDotProduct()
  {
    final VectorReadable2DType v0 = new VectorI2D(10.0, 10.0);
    final VectorReadable2DType v1 = new VectorI2D(10.0, 10.0);

    {
      final double p = VectorI2D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorI2D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }

    {
      final double p = VectorI2D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(p == 200.0);
    }
  }

  @Override
  @Test
  public void testDotProductPerpendicular()
  {
    final VectorReadable2DType vpx = new VectorI2D(1.0f, 0.0f);
    final VectorReadable2DType vmx = new VectorI2D(-1.0f, 0.0f);

    final VectorReadable2DType vpy = new VectorI2D(0.0f, 1.0f);
    final VectorReadable2DType vmy = new VectorI2D(0.0f, -1.0f);

    Assert.assertTrue(VectorI2D.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI2D.dotProduct(vmx, vmy) == 0.0);
  }

  @Override
  @Test
  public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorReadable2DType q = new VectorI2D(x, y);
      final double dp = VectorI2D.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override
  @Test
  public void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final VectorReadable2DType q = new VectorI2D(x, y);

      final double ms = VectorI2D.magnitudeSquared(q);
      final double dp = VectorI2D.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override
  @Test
  public void testEqualsCorrect()
  {
    {
      final VectorI2D m0 = new VectorI2D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2D m0 = new VectorI2D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2D m0 = new VectorI2D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2D m0 = new VectorI2D();
      final VectorI2D m1 = new VectorI2D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2D m0 = new VectorI2D(x, y);
      final VectorI2D m1 = new VectorI2D(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testHashCodeEqualsCorrect()
  {
    final VectorI2D m0 = new VectorI2D();
    final VectorI2D m1 = new VectorI2D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override
  @Test
  public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2D m0 = new VectorI2D(23, 0);
      final VectorI2D m1 = new VectorI2D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2D m0 = new VectorI2D(0, 23);
      final VectorI2D m1 = new VectorI2D();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override
  @Test
  public void testInitializeReadable()
  {
    final VectorI2D v0 = new VectorI2D(1.0f, 2.0f);
    final VectorReadable2DType v1 = new VectorI2D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
  }

  @Override
  @Test
  public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v1 = new VectorI2D(x1, y1);

      Assert.assertTrue(
        VectorI2D.almostEqual(
          ec, VectorI2D.interpolateLinear(v0, v1, 0.0), v0));
      Assert.assertTrue(
        VectorI2D.almostEqual(
          ec, VectorI2D.interpolateLinear(v0, v1, 1.0), v1));
    }
  }

  @Override
  @Test
  public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final VectorReadable2DType v = new VectorI2D(x, y);

      final double m = VectorI2D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override
  @Test
  public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final VectorReadable2DType v = new VectorI2D(x, y);

      final VectorI2D vr = VectorI2D.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI2D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override
  @Test
  public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType v = new VectorI2D(0.0, 0.0);
    final VectorI2D vr = VectorI2D.normalize(v);
    final double m = VectorI2D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType v = new VectorI2D(1.0, 0.0);
    final double m = VectorI2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override
  @Test
  public void testMagnitudeSimple()
  {
    final VectorReadable2DType v = new VectorI2D(8.0, 0.0);

    {
      final double p = VectorI2D.dotProduct(v, v);
      final double q = VectorI2D.magnitudeSquared(v);
      final double r = VectorI2D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override
  @Test
  public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType v = new VectorI2D(0.0, 0.0);
    final double m = VectorI2D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testNormalizeSimple()
  {
    final VectorReadable2DType v0 = new VectorI2D(8.0, 0.0);
    final VectorI2D vr = VectorI2D.normalize(v0);
    final double m = VectorI2D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override
  @Test
  public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2DType q = new VectorI2D(0, 0);
    final VectorI2D qr = VectorI2D.normalize(q);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getXD()));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.getYD()));
  }

  @Override
  @Test
  public void testOrthonormalize()
  {
    final VectorReadable2DType v0 = new VectorI2D(0, 1);
    final VectorReadable2DType v1 = new VectorI2D(0.5, 0.5);
    final Pair<VectorI2D, VectorI2D> on = VectorI2D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new VectorI2D(1, 0), on.getRight());
  }

  @Override
  @Test
  public void testProjectionPerpendicularZero()
  {
    {
      final VectorReadable2DType p = new VectorI2D(1.0, 0.0);
      final VectorReadable2DType q = new VectorI2D(0.0, 1.0);
      final VectorI2D r = VectorI2D.projection(p, q);
      Assert.assertTrue(VectorI2D.magnitude(r) == 0.0);
    }

    {
      final VectorReadable2DType p = new VectorI2D(-1.0, 0.0);
      final VectorReadable2DType q = new VectorI2D(0.0, 1.0);
      final VectorI2D r = VectorI2D.projection(p, q);
      Assert.assertTrue(VectorI2D.magnitude(r) == 0.0);
    }
  }

  @Override
  @Test
  public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      final VectorI2D vr = VectorI2D.scale(v, 1.0);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getXD(), vr.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, v.getYD(), vr.getYD()));
    }
  }

  @Override
  @Test
  public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v = new VectorI2D(x, y);

      final VectorI2D vr = VectorI2D.scale(v, 0.0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
    }
  }

  @Override
  @Test
  public void testString()
  {
    final VectorI2D v = new VectorI2D(0.0, 1.0);
    Assert.assertTrue("[VectorI2D 0.0 1.0]".equals(v.toString()));
  }

  @Override
  @Test
  public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v0 = new VectorI2D(x0, y0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final VectorReadable2DType v1 = new VectorI2D(x1, y1);

      final VectorI2D vr = VectorI2D.subtract(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getXD(), v0.getXD() - v1.getXD()));
      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, vr.getYD(), v0.getYD() - v1.getYD()));
    }
  }
}
