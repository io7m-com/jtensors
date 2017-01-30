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
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorReadable4FType;
import org.junit.Assert;
import org.junit.Test;

public class VectorI4FTest extends VectorI4Contract
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
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      final VectorI4F vr = VectorI4F.absolute(v);

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

  @Override
  @Test
  public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);

      final VectorI4F vr = VectorI4F.add(v0, v1);

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

  @Override
  @Test
  public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w0 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float y1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float z1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final float w1 = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);

      final float r = (float) (Math.random() * (Float.MAX_VALUE / 2));
      final VectorI4F vr = VectorI4F.addScaled(v0, v1, r);

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
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, y, z, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(x, q, z, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(x, y, q, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(x, y, z, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, q, z, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, y, q, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, y, z, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, q, q, w);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, q, z, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(q, q, q, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(x, q, q, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
    }

    {
      final VectorReadable4FType m0 = new VectorI4F(x, y, z, w);
      final VectorReadable4FType m1 = new VectorI4F(x, y, q, q);
      Assert.assertFalse(VectorI4F.almostEqual(ec, m0, m1));
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
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v0 = new VectorI4F(x0, y0, z0, w0);
      final VectorReadable4FType v1 = new VectorI4F(x0, y0, z0, w0);
      final VectorReadable4FType v2 = new VectorI4F(x0, y0, z0, w0);

      Assert.assertTrue(VectorI4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorI4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorI4F.almostEqual(ec, v0, v2));
    }
  }

  @Override
  @Test
  public void testCheckInterface()
  {
    final VectorReadable4FType v = new VectorI4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @Override
  @Test
  public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F maximum = new VectorI4F(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(
        VectorI4F.clampMaximumByVector(v, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI4F.clampMaximumByVector(v, maximum).getYF()
          <= maximum.getYF());
      Assert.assertTrue(
        VectorI4F.clampMaximumByVector(v, maximum).getZF()
          <= maximum.getZF());
      Assert.assertTrue(
        VectorI4F.clampMaximumByVector(v, maximum).getWF()
          <= maximum.getWF());
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
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F minimum = new VectorI4F(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(
        VectorI4F.clampMinimumByVector(v, minimum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI4F.clampMinimumByVector(v, minimum).getYF()
          >= minimum.getYF());
      Assert.assertTrue(
        VectorI4F.clampMinimumByVector(v, minimum).getZF()
          >= minimum.getZF());
      Assert.assertTrue(
        VectorI4F.clampMinimumByVector(v, minimum).getWF()
          >= minimum.getWF());
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
      final float min_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F minimum = new VectorI4F(min_x, min_y, min_z, min_w);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final float max_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F maximum = new VectorI4F(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getXF()
          <= maximum.getXF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getXF()
          >= minimum.getXF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getYF()
          <= maximum.getYF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getYF()
          >= minimum.getYF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getZF()
          <= maximum.getZF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getZF()
          >= minimum.getZF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getWF()
          <= maximum.getWF());
      Assert.assertTrue(
        VectorI4F.clampByVector(v, minimum, maximum).getWF()
          >= minimum.getWF());
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
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).getXF() <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).getYF() <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).getZF() <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).getWF() <= maximum);
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
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).getXF() >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).getYF() >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).getZF() >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).getWF() >= minimum);
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
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getXF()
          <= maximum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getXF()
          >= minimum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getYF()
          <= maximum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getYF()
          >= minimum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getZF()
          <= maximum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getZF()
          >= minimum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getWF()
          <= maximum);
      Assert.assertTrue(
        VectorI4F.clamp(v, minimum, maximum).getWF()
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
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);
      final VectorReadable4FType vc = new VectorI4F(v);

      Assert.assertTrue(VectorI4F.almostEqual(ec, v, vc));
    }
  }

  @Override
  @Test
  public void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final VectorReadable4FType v = new VectorI4F();
    VectorI4F.almostEqual(context, v, new VectorI4F(0, 0, 0, 1));
  }

  @Override
  @Test
  public void testDistance()
  {
    final VectorReadable4FType v0 = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorReadable4FType v1 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);

    final ContextRelative context = new AlmostEqualFloat.ContextRelative();
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, VectorI4F.distance(v0, v1), 1.0f));
  }

  @Override
  @Test
  public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v0 = new VectorI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v1 = new VectorI4F(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4F.distance(v0, v1) >= 0.0);
    }
  }

  @Override
  @Test
  public void testDotProduct()
  {
    final VectorI4F v0 = new VectorI4F(10.0f, 10.0f, 10.0f, 10.0f);
    final VectorI4F v1 = new VectorI4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = VectorI4F.dotProduct(v0, v1);
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
      final double p = VectorI4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = VectorI4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @Override
  @Test
  public void testDotProductPerpendicular()
  {
    final VectorReadable4FType vpx = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);
    final VectorReadable4FType vmx = new VectorI4F(-1.0f, 0.0f, 0.0f, 0.0f);

    final VectorReadable4FType vpy = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorReadable4FType vmy = new VectorI4F(0.0f, -1.0f, 0.0f, 0.0f);

    final VectorReadable4FType vpz = new VectorI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorReadable4FType vmz = new VectorI4F(0.0f, 0.0f, -1.0f, 0.0f);

    Assert.assertTrue(VectorI4F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorI4F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorI4F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorI4F.dotProduct(vmy, vmz) == 0.0);
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
      final float w = (float) Math.random();
      final VectorReadable4FType q = new VectorI4F(x, y, z, w);
      final double dp = VectorI4F.dotProduct(q, q);


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
      final float w = (float) Math.random();
      final VectorReadable4FType q = new VectorI4F(x, y, z, w);

      final double ms = VectorI4F.magnitudeSquared(q);
      final double dp = VectorI4F.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override
  @Test
  public void testEqualsCorrect()
  {
    final VectorI4F v0 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F v1 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F vw = new VectorI4F(0.0f, 0.0f, 0.0f, 1.0f);
    final VectorI4F vz = new VectorI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorI4F vy = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4F vx = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
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
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorI4F m0 = new VectorI4F(x, y, z, w);
      final VectorI4F m1 = new VectorI4F(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override
  @Test
  public void testHashCodeEqualsCorrect()
  {
    final VectorI4F m0 = new VectorI4F();
    final VectorI4F m1 = new VectorI4F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override
  @Test
  public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorI4F m0 = new VectorI4F(23, 0, 0, 1);
      final VectorI4F m1 = new VectorI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4F m0 = new VectorI4F(0, 23, 0, 1);
      final VectorI4F m1 = new VectorI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4F m0 = new VectorI4F(0, 0, 23, 1);
      final VectorI4F m1 = new VectorI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorI4F m0 = new VectorI4F(0, 0, 0, 23);
      final VectorI4F m1 = new VectorI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override
  @Test
  public void testInitializeReadable()
  {
    final VectorI4F v0 = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorReadable4FType v1 = new VectorI4F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
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
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v0 = new VectorI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v1 = new VectorI4F(x1, y1, z1, w1);

      Assert.assertTrue(
        VectorI4F.almostEqual(
          ec, VectorI4F.interpolateLinear(v0, v1, 0.0f), v0));
      Assert.assertTrue(
        VectorI4F.almostEqual(
          ec, VectorI4F.interpolateLinear(v0, v1, 1.0f), v1));
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
      final float w = (float) (1.0f + (Math.random() * Float.MAX_VALUE));
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      final double m = VectorI4F.magnitude(v);
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
      final float w = (float) (1.0f + (Math.random() * max));
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      final VectorI4F vr = VectorI4F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = VectorI4F.magnitude(vr);


      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override
  @Test
  public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable4FType v = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F vr = VectorI4F.normalize(v);
    final double m = VectorI4F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorReadable4FType v = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorI4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override
  @Test
  public void testMagnitudeSimple()
  {
    final VectorReadable4FType v = new VectorI4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = VectorI4F.dotProduct(v, v);
      final double q = VectorI4F.magnitudeSquared(v);
      final double r = VectorI4F.magnitude(v);
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

    final VectorReadable4FType v = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = VectorI4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override
  @Test
  public void testNormalizeSimple()
  {
    final VectorReadable4FType v0 = new VectorI4F(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F vr = VectorI4F.normalize(v0);
    final float m = VectorI4F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @Override
  @Test
  public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable4FType v0 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    VectorI4F.almostEqual(ec, VectorI4F.normalize(v0), v0);
  }

  @Override
  @Test
  public void testOrthonormalize()
  {
    final VectorReadable4FType v0 = new VectorI4F(0, 1, 0, 0);
    final VectorReadable4FType v1 = new VectorI4F(0.5f, 0.5f, 0, 0);
    final Pair<VectorI4F, VectorI4F> on = VectorI4F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.getLeft());
    Assert.assertEquals(new VectorI4F(1, 0, 0, 0), on.getRight());
  }

  @Override
  @Test
  public void testProjectionPerpendicularZero()
  {
    {
      final VectorReadable4FType p = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorReadable4FType q = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4F r = VectorI4F.projection(p, q);
      Assert.assertTrue(VectorI4F.magnitude(r) == 0.0);
    }

    {
      final VectorReadable4FType p = new VectorI4F(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorReadable4FType q = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4F r = VectorI4F.projection(p, q);
      Assert.assertTrue(VectorI4F.magnitude(r) == 0.0);
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
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      final VectorI4F vr = VectorI4F.scale(v, 1.0f);

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
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorReadable4FType v = new VectorI4F(x, y, z, w);

      final VectorI4F vr = VectorI4F.scale(v, 0.0f);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getXF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getYF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getZF(), 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr.getWF(), 0.0f));
    }
  }

  @Override
  @Test
  public void testString()
  {
    final VectorI4F v = new VectorI4F(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue("[VectorI4F 0.0 1.0 2.0 3.0]".equals(v.toString()));
  }

  @Override
  @Test
  public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);

      final VectorI4F vr = VectorI4F.subtract(v0, v1);

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
