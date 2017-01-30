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
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorReadable2FType;
import org.junit.Assert;
import org.junit.Test;

public class VectorI2FTest extends VectorI2Contract
{
  @Override
  @Test
  public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      final VectorI2F vr = VectorI2F.absolute(v);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));
    }
  }

  @Override
  @Test
  public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorReadable2FType v1 = new VectorI2F(x1, y1);

      final VectorI2F vr = VectorI2F.add(v0, v1);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getYF(), v0.getYF() + v1.getYF()));
    }
  }

  @Override
  @Test
  public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorReadable2FType v1 = new VectorI2F(x1, y1);

      final float r = (float) (Math.random() * 100.0f);
      final VectorI2F vr = VectorI2F.addScaled(v0, v1, r);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getYF(), v0.getYF() + (v1.getYF() * r)));
    }
  }

  @Override
  @Test
  public void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float q = z + 1.0f;

    {
      final VectorReadable2FType m0 = new VectorI2F(x, y);
      final VectorReadable2FType m1 = new VectorI2F(q, y);
      Assert.assertFalse(VectorI2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2FType m0 = new VectorI2F(x, y);
      final VectorReadable2FType m1 = new VectorI2F(x, q);
      Assert.assertFalse(VectorI2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable2FType m0 = new VectorI2F(x, y);
      final VectorReadable2FType m1 = new VectorI2F(q, q);
      Assert.assertFalse(VectorI2F.almostEqual(ec, m0, m1));
    }
  }

  @Override
  @Test
  public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);
      final VectorReadable2FType v1 = new VectorI2F(x0, y0);
      final VectorReadable2FType v2 = new VectorI2F(x0, y0);

      Assert.assertTrue(VectorI2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI2F.almostEqual(ec, v0, v2));
    }
  }

  @Override
  @Test
  public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorI2F v0 = VectorI2F.normalize(new VectorI2F(x, y));
      final VectorI2F v1 = VectorI2F.normalize(new VectorI2F(y, -x));
      final double angle = VectorI2F.angle(v0, v1);


      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorI2F v0 = VectorI2F.normalize(new VectorI2F(x, y));
      final VectorI2F v1 = VectorI2F.normalize(new VectorI2F(-y, x));
      final double angle = VectorI2F.angle(v0, v1);


      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override
  @Test
  public void testCheckInterface()
  {
    final VectorReadable2FType v = new VectorI2F(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override
  @Test
  public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType maximum = new VectorI2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(
        VectorI2F.clampMaximumByVector(v, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI2F.clampMaximumByVector(v, maximum).getYF()
          <= maximum.getYF());
    }
  }

  @Override
  @Test
  public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType minimum = new VectorI2F(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(
        VectorI2F.clampMinimumByVector(v, minimum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI2F.clampMinimumByVector(v, minimum).getYF()
          >= minimum.getYF());
    }
  }

  @Override
  @Test
  public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType minimum = new VectorI2F(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType maximum = new VectorI2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(
        VectorI2F.clampByVector(v, minimum, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI2F.clampByVector(v, minimum, maximum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI2F.clampByVector(v, minimum, maximum).getYF()
          <= maximum.getYF());
      Assert.assertTrue(
        VectorI2F.clampByVector(v, minimum, maximum).getYF()
          >= minimum.getYF());
    }
  }

  @Override
  @Test
  public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(VectorI2F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert.assertTrue(VectorI2F.clampMaximum(v, maximum).getYF() <= maximum);
    }
  }

  @Override
  @Test
  public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(VectorI2F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert.assertTrue(VectorI2F.clampMinimum(v, minimum).getYF() >= minimum);
    }
  }

  @Override
  @Test
  public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      Assert.assertTrue(
        VectorI2F.clamp(v, minimum, maximum).getXF()
          <= maximum);
      Assert.assertTrue(
        VectorI2F.clamp(v, minimum, maximum).getXF()
          >= minimum);
      Assert.assertTrue(
        VectorI2F.clamp(v, minimum, maximum).getYF()
          <= maximum);
      Assert.assertTrue(
        VectorI2F.clamp(v, minimum, maximum).getYF()
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
      final float x = (float) Math.random();
      final float y = (float) Math.random();

      final VectorI2F v0 = new VectorI2F(x, y);
      final VectorReadable2FType v1 = new VectorI2F(v0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getXF(), x));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1.getYF(), y));
    }
  }

  @Override
  @Test
  public void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable2FType v = new VectorI2F();
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
  }

  @Override
  @Test
  public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2FType v0 = new VectorI2F(0.0f, 1.0f);
    final VectorReadable2FType v1 = new VectorI2F(0.0f, 0.0f);

    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorI2F.distance(v0, v1), 1.0));
  }

  @Override
  @Test
  public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v1 = new VectorI2F(x1, y1);

      Assert.assertTrue(VectorI2F.distance(v0, v1) >= 0.0f);
    }
  }

  @Override
  @Test
  public void testDotProduct()
  {
    final VectorReadable2FType v0 = new VectorI2F(10.0f, 10.0f);
    final VectorReadable2FType v1 = new VectorI2F(10.0f, 10.0f);

    {
      final double p = VectorI2F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorI2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorI2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override
  @Test
  public void testDotProductPerpendicular()
  {
    final VectorReadable2FType vpx = new VectorI2F(1.0f, 0.0f);
    final VectorReadable2FType vmx = new VectorI2F(-1.0f, 0.0f);

    final VectorReadable2FType vpy = new VectorI2F(0.0f, 1.0f);
    final VectorReadable2FType vmy = new VectorI2F(0.0f, -1.0f);

    Assert.assertTrue(VectorI2F.dotProduct(vpx, vpy) == 0.0f);
    Assert.assertTrue(VectorI2F.dotProduct(vmx, vmy) == 0.0f);
  }

  @Override
  @Test
  public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorReadable2FType q = new VectorI2F(x, y);
      final double dp = VectorI2F.dotProduct(q, q);


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
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorReadable2FType q = new VectorI2F(x, y);

      final double ms = VectorI2F.magnitudeSquared(q);
      final double dp = VectorI2F.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override
  @Test
  public void testEqualsCorrect()
  {
    {
      final VectorI2F m0 = new VectorI2F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorI2F m0 = new VectorI2F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2F m0 = new VectorI2F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2F m0 = new VectorI2F();
      final VectorI2F m1 = new VectorI2F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI2F m0 = new VectorI2F(x, y);
      final VectorI2F m1 = new VectorI2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testHashCodeEqualsCorrect()
  {
    final VectorI2F m0 = new VectorI2F();
    final VectorI2F m1 = new VectorI2F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override
  @Test
  public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI2F m0 = new VectorI2F(23, 0);
      final VectorI2F m1 = new VectorI2F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI2F m0 = new VectorI2F(0, 23);
      final VectorI2F m1 = new VectorI2F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override
  @Test
  public void testInitializeReadable()
  {
    final VectorI2F v0 = new VectorI2F(1.0f, 2.0f);
    final VectorReadable2FType v1 = new VectorI2F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
  }

  @Override
  @Test
  public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v1 = new VectorI2F(x1, y1);

      Assert.assertTrue(
        VectorI2F.almostEqual(
          ec, VectorI2F.interpolateLinear(v0, v1, 0.0f), v0));
      Assert.assertTrue(
        VectorI2F.almostEqual(
          ec, VectorI2F.interpolateLinear(v0, v1, 1.0f), v1));
    }
  }

  @Override
  @Test
  public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0 + (Math.random() * Float.MAX_VALUE));
      final VectorReadable2FType v = new VectorI2F(x, y);

      final double m = VectorI2F.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override
  @Test
  public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorReadable2FType v = new VectorI2F(x, y);

      final VectorI2F vr = VectorI2F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI2F.magnitude(vr);


      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override
  @Test
  public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2FType v = new VectorI2F(0.0f, 0.0f);
    final VectorI2F vr = VectorI2F.normalize(v);
    final double m = VectorI2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2FType v = new VectorI2F(1.0f, 0.0f);
    final double m = VectorI2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override
  @Test
  public void testMagnitudeSimple()
  {
    final VectorReadable2FType v = new VectorI2F(8.0f, 0.0f);

    {
      final double p = VectorI2F.dotProduct(v, v);
      final double q = VectorI2F.magnitudeSquared(v);
      final double r = VectorI2F.magnitude(v);
      Assert.assertTrue(p == 64.0f);
      Assert.assertTrue(q == 64.0f);
      Assert.assertTrue(r == 8.0f);
    }
  }

  @Override
  @Test
  public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2FType v = new VectorI2F(0.0f, 0.0f);
    final double m = VectorI2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testNormalizeSimple()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable2FType v0 = new VectorI2F(8.0f, 0.0f);
    final double dp = VectorI2F.dotProduct(v0, v0);
    final double ms = VectorI2F.magnitudeSquared(v0);
    final double mss = Math.sqrt(ms);
    final double m0 = VectorI2F.magnitude(v0);
    final VectorI2F vr = VectorI2F.normalize(v0);
    final double mn = VectorI2F.magnitude(vr);


    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp, 64.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, mn, 1.0));
  }

  @Override
  @Test
  public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable2FType q = new VectorI2F(0, 0);
    final VectorI2F qr = VectorI2F.normalize(q);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override
  @Test
  public void testOrthonormalize()
  {
    final VectorReadable2FType v0 = new VectorI2F(0, 1);
    final VectorReadable2FType v1 = new VectorI2F(0.5f, 0.5f);
    final Pair<VectorI2F, VectorI2F> on = VectorI2F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new VectorI2F(1, 0), on.getRight());
  }

  @Override
  @Test
  public void testProjectionPerpendicularZero()
  {
    {
      final VectorReadable2FType p = new VectorI2F(1.0f, 0.0f);
      final VectorReadable2FType q = new VectorI2F(0.0f, 1.0f);
      final VectorI2F r = VectorI2F.projection(p, q);
      Assert.assertTrue(VectorI2F.magnitude(r) == 0.0f);
    }

    {
      final VectorReadable2FType p = new VectorI2F(-1.0f, 0.0f);
      final VectorReadable2FType q = new VectorI2F(0.0f, 1.0f);
      final VectorI2F r = VectorI2F.projection(p, q);
      Assert.assertTrue(VectorI2F.magnitude(r) == 0.0f);
    }
  }

  @Override
  @Test
  public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      final VectorI2F vr = VectorI2F.scale(v, 1.0f);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));
    }
  }

  @Override
  @Test
  public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v = new VectorI2F(x, y);

      final VectorI2F vr = VectorI2F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
    }
  }

  @Override
  @Test
  public void testString()
  {
    final VectorI2F v = new VectorI2F(0.0f, 1.0f);
    Assert.assertTrue("[VectorI2F 0.0 1.0]".equals(v.toString()));
  }

  @Override
  @Test
  public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v0 = new VectorI2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable2FType v1 = new VectorI2F(x1, y1);

      final VectorI2F vr = VectorI2F.subtract(v0, v1);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr.getYF(), v0.getYF() - v1.getYF()));
    }
  }
}
