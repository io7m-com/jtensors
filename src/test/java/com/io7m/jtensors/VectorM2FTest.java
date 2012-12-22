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
import com.io7m.jaux.ApproximatelyEqualFloat;
import com.io7m.jaux.functional.Pair;

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

      Assert
        .assertTrue(AlmostEqualFloat.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualFloat.almostEqual(ec, Math.abs(v.y), vr.y));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;

        VectorM2F.absoluteInPlace(v);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_x),
          v.x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_y),
          v.y));
      }
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v = new VectorM2F(-1.0f, -1.0f);

    Assert.assertTrue(v.x == -1.0);
    Assert.assertTrue(v.y == -1.0);

    final float vx = v.x;
    final float vy = v.y;

    final VectorM2F ov = VectorM2F.absolute(v, out);

    Assert.assertTrue(vx == v.x);
    Assert.assertTrue(vy == v.y);
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.x == 1.0);
    Assert.assertTrue(out.y == 1.0);
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x + v1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y + v1.y));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          + v1.x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          + v1.y));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2F ov0 = VectorM2F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);

    final VectorM2F ov1 = VectorM2F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y
        + (v1.y * r)));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          + (v1.x * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          + (v1.y * r)));
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
      TestUtilities.getDoubleEqualityContext();

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

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
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
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
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
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
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
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);

      {
        final VectorM2F vr0 =
          VectorM2F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
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

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);

      {
        VectorM2F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
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

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
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

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);

      {
        VectorM2F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM2F vb = new VectorM2F(5, 6);
    final VectorM2F va = new VectorM2F(1, 2);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);

    VectorM2F.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
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
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(v1.x == 10.0f);
      Assert.assertTrue(v1.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0f);
      Assert.assertTrue(v1.y == 10.0f);
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
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorM2F.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0f);
      Assert.assertTrue(v0.y == 10.0f);
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
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2F m0 = new VectorM2F();
      final VectorM2F m1 = new VectorM2F();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM2F v0 = new VectorM2F(1.0f, 2.0f);
    final VectorM2F v1 = new VectorM2F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, vr0.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, vr0.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v1.x, vr1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v1.y, vr1.y));
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

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.y));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM2F v0 = new VectorM2F(0, 1);
    final VectorM2F v1 = new VectorM2F(0.5f, 0.5f);

    final Pair<VectorM2F, VectorM2F> r = VectorM2F.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM2F(0, 1), r.first);
    Assert.assertEquals(new VectorM2F(1, 0), r.second);
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

    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);

    final VectorM2F ov0 = VectorM2F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0f);
    Assert.assertTrue(out.y == 2.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);

    final VectorM2F ov1 = VectorM2F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0f);
    Assert.assertTrue(ov1.y == 2.0f);
    Assert.assertTrue(v0.x == 2.0f);
    Assert.assertTrue(v0.y == 2.0f);
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, vr.y));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;

        VectorM2F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, orig_y));
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

      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.x, 0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat
        .approximatelyEqual(vr.y, 0.0f));

      {
        VectorM2F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, 0.0f));
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

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x - v1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y - v1.y));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        VectorM2F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          - v1.x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          - v1.y));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM2F out = new VectorM2F();
    final VectorM2F v0 = new VectorM2F(1.0f, 1.0f);
    final VectorM2F v1 = new VectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);

    final VectorM2F ov0 = VectorM2F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0f);
    Assert.assertTrue(out.y == 0.0f);
    Assert.assertTrue(v0.x == 1.0f);
    Assert.assertTrue(v0.y == 1.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);

    final VectorM2F ov1 = VectorM2F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0f);
    Assert.assertTrue(ov1.y == 0.0f);
    Assert.assertTrue(v0.x == 0.0f);
    Assert.assertTrue(v0.y == 0.0f);
    Assert.assertTrue(v1.x == 1.0f);
    Assert.assertTrue(v1.y == 1.0f);
  }
}
