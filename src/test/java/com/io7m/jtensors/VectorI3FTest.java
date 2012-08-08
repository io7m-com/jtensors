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

public class VectorI3FTest
{
  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.absolute(v).x >= 0.0);
      Assert.assertTrue(VectorI3F.absolute(v).y >= 0.0);
      Assert.assertTrue(VectorI3F.absolute(v).z >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      final VectorI3F vr = VectorI3F.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        + v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        + v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        + v1.z));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        + (v1.z * r)));
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
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));

    final VectorI3F v0 = new VectorI3F(x0, y0, z0);
    final VectorI3F v1 = new VectorI3F(x1, y1, z1);
    Assert.assertTrue(VectorI3F.approximatelyEqual(v0, v1));
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
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));

    final VectorI3F v0 = new VectorI3F(x0, y0, z0);
    final VectorI3F v1 = new VectorI3F(x1, y1, z1);
    Assert.assertFalse(VectorI3F.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI3F v = new VectorI3F(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
    Assert.assertTrue(v.z == v.getZF());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F maximum = new VectorI3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert
        .assertTrue(VectorI3F.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3F.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3F.clampMaximumByVector(v, maximum).z <= maximum.z);
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
      final VectorI3F minimum = new VectorI3F(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert
        .assertTrue(VectorI3F.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3F.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3F.clampMinimumByVector(v, minimum).z >= minimum.z);
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
      final VectorI3F minimum = new VectorI3F(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F maximum = new VectorI3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI3F.clampByVector(v, minimum, maximum).z >= minimum.z);
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
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI3F.clampMaximum(v, maximum).z <= maximum);
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
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI3F.clampMinimum(v, minimum).z >= minimum);
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
      final VectorI3F v = new VectorI3F(x, y, z);

      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI3F.clamp(v, minimum, maximum).z >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductPerpendicular()
  {
    final VectorI3F vy = new VectorI3F(0, 1, 0);
    final VectorI3F vx = new VectorI3F(1, 0, 0);
    final VectorI3F vz = new VectorI3F(0, 0, 1);

    final VectorI3F vxy = VectorI3F.crossProduct(vx, vy);
    final VectorI3F vxz = VectorI3F.crossProduct(vx, vz);
    final VectorI3F vyz = VectorI3F.crossProduct(vy, vz);

    Assert.assertTrue(VectorI3F.dotProduct(vxy, vx) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vxy, vy) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vxz, vx) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vxz, vz) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vyz, vy) == 0.0);
    Assert.assertTrue(VectorI3F.dotProduct(vyz, vz) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductSimple()
  {
    final VectorI3F v0 = new VectorI3F(0, 1, 0);
    final VectorI3F v1 = new VectorI3F(1, 0, 0);
    final VectorI3F vr = VectorI3F.crossProduct(v0, v1);

    Assert.assertTrue(vr.x == 0.0);
    Assert.assertTrue(vr.y == 0.0);
    Assert.assertTrue(vr.z == -1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorI3F v0 = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 0.0f, 0.0f);

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      VectorI3F.distance(v0, v1),
      1.0f));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      Assert.assertTrue(VectorI3F.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorI3F v = new VectorI3F(1.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorI3F.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final float x0 = 2.0f;
    final float y0 = 2.0f;
    final float z0 = 2.0f;
    final VectorI3F v0 = new VectorI3F(x0, y0, z0);

    final float x1 = 2.0f;
    final float y1 = 2.0f;
    final float z1 = 2.0f;
    final VectorI3F v1 = new VectorI3F(x1, y1, z1);

    final float p = VectorI3F.dotProduct(v0, v1);
    Assert.assertTrue(p == 12.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
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

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final VectorI3F v0 = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 0.0f, 0.0f);
    final VectorI3F vz = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorI3F vy = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F vx = new VectorI3F(1.0f, 0.0f, 0.0f);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
    Assert.assertTrue(v0.hashCode() != vz.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final VectorI3F v0 = new VectorI3F(1.0f, 2.0f, 3.0f);
    final VectorI3F v1 = new VectorI3F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      Assert.assertTrue(VectorI3F.approximatelyEqual(
        VectorI3F.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(VectorI3F.approximatelyEqual(
        VectorI3F.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v = new VectorI3F(x, y, z);

      ApproximatelyEqualFloat.approximatelyEqual(
        VectorI3F.magnitude(VectorI3F.normalize(v)),
        1.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testNoargZero()
  {
    final VectorI3F v = new VectorI3F();
    VectorI3F.approximatelyEqual(v, VectorI3F.ZERO);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorI3F v0 = new VectorI3F(8.0f, 0.0f, 0.0f);
    final VectorI3F vr = VectorI3F.normalize(v0);
    final float m = VectorI3F.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final VectorI3F v0 = new VectorI3F(0.0f, 0.0f, 0.0f);
    VectorI3F.approximatelyEqual(VectorI3F.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorI3F p = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorI3F q = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F r = VectorI3F.projection(p, q);
      Assert.assertTrue(VectorI3F.magnitude(r) == 0.0f);
    }

    {
      final VectorI3F p = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorI3F q = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F r = VectorI3F.projection(p, q);
      Assert.assertTrue(VectorI3F.magnitude(r) == 0.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI3F v = new VectorI3F(0.0f, 1.0f, 2.0f);
    Assert.assertTrue(v.toString().equals("[VectorI3F 0.0 1.0 2.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v0 = new VectorI3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorI3F v1 = new VectorI3F(x1, y1, z1);

      final VectorI3F vr = VectorI3F.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.x, v0.x
        - v1.x));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.y, v0.y
        - v1.y));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(vr.z, v0.z
        - v1.z));
    }
  }
}
