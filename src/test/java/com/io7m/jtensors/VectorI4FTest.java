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

import com.io7m.jaux.ApproximatelyEqualFloat;
import com.io7m.jaux.functional.Pair;

public class VectorI4FTest
{
  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.absolute(v).x >= 0.0);
      Assert.assertTrue(VectorI4F.absolute(v).y >= 0.0);
      Assert.assertTrue(VectorI4F.absolute(v).z >= 0.0);
      Assert.assertTrue(VectorI4F.absolute(v).w >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
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

      final VectorI4F vr = VectorI4F.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        + v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        + v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        + v1.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.w, v0.w
        + v1.w));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
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

      final float r = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F vr = VectorI4F.addScaled(v0, v1, r);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        + (v1.z * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.w, v0.w
        + (v1.w * r)));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final float x0 = 0.0f;
    final float x1 = 0.0f;
    final float y0 = 0.0f;
    final float y1 = 0.0f;
    final float z0 = 0.0f;
    final float z1 = 0.0f;
    final float w0 = 0.0f;
    final float w1 = 0.0f;
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);
    final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);
    Assert.assertTrue(VectorI4F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final float x0 = 0.0f;
    final float x1 = 1.0f;
    final float y0 = 0.0f;
    final float y1 = 1.0f;
    final float z0 = 0.0f;
    final float z1 = 1.0f;
    final float w0 = 0.0f;
    final float w1 = 1.0f;
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);
    final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);
    Assert.assertFalse(VectorI4F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI4F v = new VectorI4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
    Assert.assertTrue(v.z == v.getZF());
    Assert.assertTrue(v.w == v.getWF());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final float max_w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F maximum = new VectorI4F(max_x, max_y, max_z, max_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert
        .assertTrue(VectorI4F.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4F.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4F.clampMaximumByVector(v, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4F.clampMaximumByVector(v, maximum).w <= maximum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final float min_w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F minimum = new VectorI4F(min_x, min_y, min_z, min_w);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert
        .assertTrue(VectorI4F.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4F.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4F.clampMinimumByVector(v, minimum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4F.clampMinimumByVector(v, minimum).w >= minimum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).w <= maximum.w);
      Assert
        .assertTrue(VectorI4F.clampByVector(v, minimum, maximum).w >= minimum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).z <= maximum);
      Assert.assertTrue(VectorI4F.clampMaximum(v, maximum).w <= maximum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).z >= minimum);
      Assert.assertTrue(VectorI4F.clampMinimum(v, minimum).w >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final float w = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).z >= minimum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).w <= maximum);
      Assert.assertTrue(VectorI4F.clamp(v, minimum, maximum).w >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorI4F v0 = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4F v1 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      VectorI4F.distance(v0, v1),
      1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(VectorI4F.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorI4F v = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorI4F.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final float x0 = 2.0f;
    final float y0 = 2.0f;
    final float z0 = 2.0f;
    final float w0 = 2.0f;
    final VectorI4F v0 = new VectorI4F(x0, y0, z0, w0);

    final float x1 = 2.0f;
    final float y1 = 2.0f;
    final float z1 = 2.0f;
    final float w1 = 2.0f;
    final VectorI4F v1 = new VectorI4F(x1, y1, z1, w1);

    final float p = VectorI4F.dotProduct(v0, v1);
    Assert.assertTrue(p == 16.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorI4F m0 = new VectorI4F();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorI4F m0 = new VectorI4F();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorI4F m0 = new VectorI4F();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorI4F m0 = new VectorI4F();
    final VectorI4F m1 = new VectorI4F();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
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

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final VectorI4F v0 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F v1 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F vw = new VectorI4F(0.0f, 0.0f, 0.0f, 1.0f);
    final VectorI4F vz = new VectorI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final VectorI4F vy = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final VectorI4F vx = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
    Assert.assertTrue(v0.hashCode() != vz.hashCode());
    Assert.assertTrue(v0.hashCode() != vw.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorI4F v0 = new VectorI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorI4F v1 = new VectorI4F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(VectorI4F.approximatelyEqual(
        VectorI4F.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(VectorI4F.approximatelyEqual(
        VectorI4F.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI4F v = new VectorI4F(x, y, z, w);

      ApproximatelyEqualFloat.approximatelyEqual(
        VectorI4F.magnitude(VectorI4F.normalize(v)),
        1.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testNoargZero()
  {
    final VectorI4F v = new VectorI4F();
    VectorI4F.approximatelyEqual(v, VectorI4F.ZERO);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorI4F v0 = new VectorI4F(8.0f, 0.0f, 0.0f, 0.0f);
    final VectorI4F vr = VectorI4F.normalize(v0);
    final float m = VectorI4F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final VectorI4F v0 = new VectorI4F(0.0f, 0.0f, 0.0f, 0.0f);
    VectorI4F.approximatelyEqual(VectorI4F.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public void testOrthonormalize()
  {
    final VectorI4F v0 = new VectorI4F(0, 1, 0, 0);
    final VectorI4F v1 = new VectorI4F(0.5f, 0.5f, 0, 0);
    final Pair<VectorI4F, VectorI4F> on = VectorI4F.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI4F(1, 0, 0, 0), on.second);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorI4F p = new VectorI4F(1.0f, 0.0f, 0.0f, 0.0f);
      final VectorI4F q = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4F r = VectorI4F.projection(p, q);
      Assert.assertTrue(VectorI4F.magnitude(r) == 0.0);
    }

    {
      final VectorI4F p = new VectorI4F(-1.0f, 0.0f, 0.0f, 0.0f);
      final VectorI4F q = new VectorI4F(0.0f, 1.0f, 0.0f, 0.0f);
      final VectorI4F r = VectorI4F.projection(p, q);
      Assert.assertTrue(VectorI4F.magnitude(r) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI4F v = new VectorI4F(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorI4F 0.0 1.0 2.0 3.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        - v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        - v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        - v1.z));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.w, v0.w
        - v1.w));
    }
  }
}
