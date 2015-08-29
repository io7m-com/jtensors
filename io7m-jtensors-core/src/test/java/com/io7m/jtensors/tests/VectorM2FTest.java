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

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jfunctional.Pair;
import com.io7m.jtensors.VectorM2F;

public class VectorM2FTest extends VectorM2Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.absolute(v, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getXF()),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getYF()),
        vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2F.absoluteInPlace(v);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_x),
          v.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_y),
          v.getYF()));
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v = new VectorM2F(-1.0f, -1.0f);

    Assert.assertTrue(v.getXF() == -1.0);
    Assert.assertTrue(v.getYF() == -1.0);

    final float vx = v.getXF();
    final float vy = v.getYF();

    final VectorM2F ov = VectorM2F.absolute(v, out);

    Assert.assertTrue(vx == v.getXF());
    Assert.assertTrue(vy == v.getYF());
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXF() == 1.0);
    Assert.assertTrue(out.getYF() == 1.0);
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + v1.getYF()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final VectorM2F ov0 = VectorM2F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final VectorM2F ov1 = VectorM2F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final float r = (float) (Math.random() * max);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + (v1.getYF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + (v1.getXF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + (v1.getYF() * r)));
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
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(x, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(x, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);
      final VectorM2F v1 = new VectorM2F(x0, y0);
      final VectorM2F v2 = new VectorM2F(x0, y0);

      Assert.assertTrue(VectorM2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2F v0 = new VectorM2F(x, y);
      final VectorM2F v1 = new VectorM2F(x, y);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2F v0 = new VectorM2F(x, y);
      final VectorM2F v1 = new VectorM2F(y, -x);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

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
      final VectorM2F v0 = new VectorM2F(x, y);
      final VectorM2F v1 = new VectorM2F(-y, x);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

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
    final VectorM2F v = new VectorM2F(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F maximum = new VectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());

      {
        final VectorM2F vr0 =
          VectorM2F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F minimum = new VectorM2F(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final VectorM2F vr0 =
          VectorM2F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F minimum = new VectorM2F(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F maximum = new VectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      final VectorM2F vo = VectorM2F.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final VectorM2F vr0 =
          VectorM2F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);

      {
        VectorM2F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2F vb = new VectorM2F(5, 6);
    final VectorM2F va = new VectorM2F(1, 2);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());

    VectorM2F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM2F v0 =
      new VectorM2F(
        (float) Math.random() * Float.MAX_VALUE,
        (float) Math.random() * Float.MAX_VALUE);
    final VectorM2F v1 = new VectorM2F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(VectorM2F.almostEqual(
      ec,
      new VectorM2F(),
      new VectorM2F(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2F v0 = new VectorM2F(0.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(0.0f, 0.0f);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM2F.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      Assert.assertTrue(VectorM2F.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM2F v0 = new VectorM2F(10.0f, 10.0f);
    final VectorM2F v1 = new VectorM2F(10.0f, 10.0f);

    {
      final float p = VectorM2F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM2F vpx = new VectorM2F(1.0f, 0.0f);
    final VectorM2F vmx = new VectorM2F(-1.0f, 0.0f);

    final VectorM2F vpy = new VectorM2F(0.0f, 1.0f);
    final VectorM2F vmy = new VectorM2F(0.0f, -1.0f);

    Assert.assertTrue(VectorM2F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM2F.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2F q = new VectorM2F(x, y);
      final float dp = VectorM2F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2F v0 = new VectorM2F(10.0f, 10.0f);

    {
      final double p = VectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorM2F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM2F m0 = new VectorM2F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2F m0 = new VectorM2F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2F m0 = new VectorM2F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2F m0 = new VectorM2F();
      final VectorM2F m1 = new VectorM2F();
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
      final VectorM2F m0 = new VectorM2F(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2F m0 = new VectorM2F(x, y);
      final VectorM2F m1 = new VectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM2F m0 = new VectorM2F();
    final VectorM2F m1 = new VectorM2F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2F m0 = new VectorM2F();
      final VectorM2F m1 = new VectorM2F();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2F m0 = new VectorM2F();
      final VectorM2F m1 = new VectorM2F();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2F v0 = new VectorM2F(1.0f, 2.0f);
    final VectorM2F v1 = new VectorM2F(v0);

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
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      final VectorM2F vr1 = new VectorM2F();
      VectorM2F.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM2F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getXF(),
        vr0.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getYF(),
        vr0.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getXF(),
        vr1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getYF(),
        vr1.getYF()));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final double m = VectorM2F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x = (float) (Math.random() * max);
      final float y = (float) (Math.random() * max);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();
      VectorM2F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2F.magnitude(vr);

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

    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    final VectorM2F vr = VectorM2F.normalizeInPlace(v);
    final double m = VectorM2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0f));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2F v = new VectorM2F(1.0f, 0.0f);
    final double m = VectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0f));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM2F v = new VectorM2F(8.0f, 0.0f);

    {
      final double p = VectorM2F.dotProduct(v, v);
      final double q = VectorM2F.magnitudeSquared(v);
      final double r = VectorM2F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2F v = new VectorM2F(0.0f, 0.0f);
    final double m = VectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM2F v0 = new VectorM2F(8.0f, 0.0f);
    final VectorM2F out = new VectorM2F();
    final VectorM2F vr = VectorM2F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM2F qr = new VectorM2F();
    final VectorM2F q = new VectorM2F(0, 0);
    VectorM2F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM2F v0 = new VectorM2F(0, 1);
    final VectorM2F v1 = new VectorM2F(0.5f, 0.5f);

    final Pair<VectorM2F, VectorM2F> r = VectorM2F.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM2F(0, 1), r.getLeft());
    Assert.assertEquals(new VectorM2F(1, 0), r.getRight());
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM2F v0 = new VectorM2F(0f, 1f);
    final VectorM2F v1 = new VectorM2F(0.5f, 0.5f);

    VectorM2F.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM2F(0, 1), v0);
    Assert.assertEquals(new VectorM2F(1, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM2F p = new VectorM2F(1.0f, 0.0f);
      final VectorM2F q = new VectorM2F(0.0f, 1.0f);
      final VectorM2F r = new VectorM2F();
      final VectorM2F u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2F.magnitude(u) == 0.0);
    }

    {
      final VectorM2F p = new VectorM2F(-1.0f, 0.0f);
      final VectorM2F q = new VectorM2F(0.0f, 1.0f);
      final VectorM2F r = new VectorM2F();
      final VectorM2F u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2F.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final VectorM2F ov0 = VectorM2F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0f);
    Assert.assertTrue(out.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final VectorM2F ov1 = VectorM2F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0f);
    Assert.assertTrue(ov1.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 2.0f);
    Assert.assertTrue(v0.getYF() == 2.0f);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();

      VectorM2F.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getYF(),
        vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2F.scaleInPlace(v, 1.0f);

        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v = new VectorM2F(x, y);

      final VectorM2F vr = new VectorM2F();

      VectorM2F.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        0.0f));

      {
        VectorM2F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM2F v = new VectorM2F(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[VectorM2F 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v0 = new VectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2F v1 = new VectorM2F(x1, y1);

      final VectorM2F vr0 = new VectorM2F();
      VectorM2F.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() - v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          - v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          - v1.getYF()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final VectorM2F ov0 = VectorM2F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final VectorM2F ov1 = VectorM2F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0f);
    Assert.assertTrue(ov1.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 0.0f);
    Assert.assertTrue(v0.getYF() == 0.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
  }
}
