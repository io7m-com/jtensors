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
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import org.junit.Assert;
import org.junit.Test;

public class VectorI3FTest extends VectorI3Contract
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
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      final VectorI3F vr = VectorI3F.absolute(v);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getZF()), vr.getZF()));
    }
  }

  @Override
  @Test
  public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      final VectorI3F vr = VectorI3F.add(v0, v1);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), v0.getYF() + v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getZF(), v0.getZF() + v1.getZF()));
    }
  }

  @Override
  @Test
  public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      final float r = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F vr = VectorI3F.addScaled(v0, v1, r);

      final ContextRelative context = new AlmostEqualFloat.ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), v0.getYF() + (v1.getYF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getZF(), v0.getZF() + (v1.getZF() * r)));
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
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, y, z);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(x, q, z);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(x, y, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, q, z);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, y, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, y, z);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, q, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, q, z);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(q, q, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(x, q, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable3FType m0 = new VectorI3F(x, y, z);
      final VectorReadable3FType m1 = new VectorI3F(x, y, q);
      Assert.assertFalse(VectorI3F.almostEqual(ec, m0, m1));
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
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v0 = new VectorI3F(x0, y0, z0);
      final VectorReadable3FType v1 = new VectorI3F(x0, y0, z0);
      final VectorReadable3FType v2 = new VectorI3F(x0, y0, z0);

      Assert.assertTrue(VectorI3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v0, v2));
    }
  }

  @Override
  @Test
  public void testCheckInterface()
  {
    final VectorReadable3FType v = new VectorI3F(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
  }

  @Override
  @Test
  public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F maximum = new VectorI3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(
        VectorI3F.clampMaximumByVector(v, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI3F.clampMaximumByVector(v, maximum).getYF()
          <= maximum.getYF());
      Assert.assertTrue(
        VectorI3F.clampMaximumByVector(v, maximum).getZF()
          <= maximum.getZF());
    }
  }

  @Override
  @Test
  public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F minimum = new VectorI3F(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(
        VectorI3F.clampMinimumByVector(v, minimum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI3F.clampMinimumByVector(v, minimum).getYF()
          >= minimum.getYF());
      Assert.assertTrue(
        VectorI3F.clampMinimumByVector(v, minimum).getZF()
          >= minimum.getZF());
    }
  }

  @Override
  @Test
  public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F minimum = new VectorI3F(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F maximum = new VectorI3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getYF()
          <= maximum.getYF());
      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getYF()
          >= minimum.getYF());
      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getZF()
          <= maximum.getZF());
      Assert.assertTrue(
        VectorI3F.clampByVector(v, minimum, maximum).getZF()
          >= minimum.getZF());
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
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).getYF() <= maximum);
      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).getZF() <= maximum);
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
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).getYF() >= minimum);
      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).getZF() >= minimum);
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
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getXF()
          <= maximum);
      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getXF()
          >= minimum);
      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getYF()
          <= maximum);
      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getYF()
          >= minimum);
      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getZF()
          <= maximum);
      Assert.assertTrue(
        VectorI3F.clamp(v, minimum, maximum).getZF()
          >= minimum);
    }
  }

  @Override
  @Test
  public void testCopy()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);
      final VectorReadable3FType vc = new VectorI3F(v);

      Assert.assertTrue(VectorI3F.almostEqual(ec, v, vc));
    }
  }

  @Override
  @Test
  public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final VectorI3F v0 = VectorI3F.normalize(new VectorI3F(x0, y0, z0));

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final VectorI3F v1 = VectorI3F.normalize(new VectorI3F(x1, y1, z1));

      final VectorI3F vr = VectorI3F.normalize(VectorI3F.crossProduct(v0, v1));

      final double dp0 = VectorI3F.dotProduct(v0, vr);
      final double dp1 = VectorI3F.dotProduct(v1, vr);


      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override
  @Test
  public void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final VectorReadable3FType v = new VectorI3F();
    VectorI3F.almostEqual(context, v, new VectorI3F(0, 0, 0));
  }

  @Override
  @Test
  public void testDistance()
  {
    final VectorReadable3FType v0 = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorReadable3FType v1 = new VectorI3F(0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, VectorI3F.distance(v0, v1), 1.0f));
  }

  @Override
  @Test
  public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v1 = new VectorI3F(x1, y1, z1);

      Assert.assertTrue(VectorI3F.distance(v0, v1) >= 0.0);
    }
  }

  @Override
  @Test
  public void testDotProduct()
  {
    final VectorI3F v0 = new VectorI3F(10.0f, 10.0f, 10.0f);
    final VectorI3F v1 = new VectorI3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorI3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = VectorI3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }

    {
      final double p = VectorI3F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(p == 300.0f);
    }
  }

  @Override
  @Test
  public void testDotProductPerpendicular()
  {
    final VectorReadable3FType vpx = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorReadable3FType vmx = new VectorI3F(-1.0f, 0.0f, 0.0f);

    final VectorReadable3FType vpy = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorReadable3FType vmy = new VectorI3F(0.0f, -1.0f, 0.0f);

    final VectorReadable3FType vpz = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorReadable3FType vmz = new VectorI3F(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorI3F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vmy, vmz) == 0.0);
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
      final float z = (float) Math.random();
      final VectorReadable3FType q = new VectorI3F(x, y, z);
      final double dp = VectorI3F.dotProduct(q, q);


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
      final float z = (float) Math.random();
      final VectorReadable3FType q = new VectorI3F(x, y, z);

      final double ms = VectorI3F.magnitudeSquared(q);
      final double dp = VectorI3F.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override
  @Test
  public void testEqualsCorrect()
  {
    final VectorI3F v0 = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorI3F vz = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorI3F vy = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F vx = new VectorI3F(1.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
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
      final VectorI3F m0 = new VectorI3F(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI3F m0 = new VectorI3F(x, y, z);
      final VectorI3F m1 = new VectorI3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testHashCodeEqualsCorrect()
  {
    final VectorI3F m0 = new VectorI3F();
    final VectorI3F m1 = new VectorI3F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override
  @Test
  public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI3F m0 = new VectorI3F(23, 0, 0);
      final VectorI3F m1 = new VectorI3F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3F m0 = new VectorI3F(0, 23, 0);
      final VectorI3F m1 = new VectorI3F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI3F m0 = new VectorI3F(0, 0, 23);
      final VectorI3F m1 = new VectorI3F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override
  @Test
  public void testInitializeReadable()
  {
    final VectorI3F v0 = new VectorI3F(1.0f, 2.0f, 3.0f);
    final VectorReadable3FType v1 = new VectorI3F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
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
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v1 = new VectorI3F(x1, y1, z1);

      Assert.assertTrue(
        VectorI3F.almostEqual(
          ec, VectorI3F.interpolateLinear(v0, v1, 0.0f), v0));
      Assert.assertTrue(
        VectorI3F.almostEqual(
          ec, VectorI3F.interpolateLinear(v0, v1, 1.0f), v1));
    }
  }

  @Override
  @Test
  public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float y = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final float z = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      final double m = VectorI3F.magnitude(v);
      Assert.assertTrue(m > 0.0f);
    }
  }

  @Override
  @Test
  public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x = (float) (1.0f + (Math.random() * max));
      final float y = (float) (1.0f + (Math.random() * max));
      final float z = (float) (1.0f + (Math.random() * max));
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      final VectorI3F vr = VectorI3F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI3F.magnitude(vr);


      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override
  @Test
  public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable3FType v = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorI3F vr = VectorI3F.normalize(v);
    final double m = VectorI3F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable3FType v = new VectorI3F(1.0f, 0.0f, 0.0f);
    final double m = VectorI3F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override
  @Test
  public void testMagnitudeSimple()
  {
    final VectorReadable3FType v = new VectorI3F(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorI3F.dotProduct(v, v);
      final double q = VectorI3F.magnitudeSquared(v);
      final double r = VectorI3F.magnitude(v);
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

    final VectorReadable3FType v = new VectorI3F(0.0f, 0.0f, 0.0f);
    final double m = VectorI3F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testNormalizeSimple()
  {
    final VectorReadable3FType v0 = new VectorI3F(8.0f, 0.0f, 0.0f);
    final VectorI3F vr = VectorI3F.normalize(v0);
    final float m = VectorI3F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override
  @Test
  public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable3FType v0 = new VectorI3F(0.0f, 0.0f, 0.0f);
    VectorI3F.almostEqual(ec, VectorI3F.normalize(v0), v0);
  }

  @Override
  @Test
  public void testOrthonormalize()
  {
    final VectorReadable3FType v0 = new VectorI3F(0, 1, 0);
    final VectorReadable3FType v1 = new VectorI3F(0.5f, 0.5f, 0);
    final Pair<VectorI3F, VectorI3F> on = VectorI3F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new VectorI3F(1, 0, 0), on.getRight());
  }

  @Override
  @Test
  public void testProjectionPerpendicularZero()
  {
    {
      final VectorReadable3FType p = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType q = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F r = VectorI3F.projection(p, q);
      Assert.assertTrue(VectorI3F.magnitude(r) == 0.0);
    }

    {
      final VectorReadable3FType p = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType q = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F r = VectorI3F.projection(p, q);
      Assert.assertTrue(VectorI3F.magnitude(r) == 0.0);
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
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      final VectorI3F vr = VectorI3F.scale(v, 1.0f);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getZF(), vr.getZF()));
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
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable3FType v = new VectorI3F(x, y, z);

      final VectorI3F vr = VectorI3F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
    }
  }

  @Override
  @Test
  public void testString()
  {
    final VectorI3F v = new VectorI3F(0.0f, 1.0f, 2.0f);
    Assert.assertTrue("[VectorI3F 0.0 1.0 2.0]".equals(v.toString()));
  }

  @Override
  @Test
  public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      final VectorI3F vr = VectorI3F.subtract(v0, v1);

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
    }
  }
}
