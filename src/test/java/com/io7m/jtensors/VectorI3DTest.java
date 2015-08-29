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

import com.io7m.jaux.ApproximatelyEqualDouble;

public class VectorI3DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.absolute(v).x >= 0.0);
      Assert.assertTrue(VectorI3D.absolute(v).y >= 0.0);
      Assert.assertTrue(VectorI3D.absolute(v).z >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final VectorI3D vr = VectorI3D.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z + v1.z));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final double r = Math.random() * Double.MAX_VALUE;
      final VectorI3D vr = VectorI3D.addScaled(v0, v1, r);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z + (v1.z * r)));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final double x0 = 0.0;
    final double x1 = 0.0;
    final double y0 = 0.0;
    final double y1 = 0.0;
    final double z0 = 0.0;
    final double z1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));

    final VectorI3D v0 = new VectorI3D(x0, y0, z0);
    final VectorI3D v1 = new VectorI3D(x1, y1, z1);
    Assert.assertTrue(VectorI3D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final double x0 = 0.0;
    final double x1 = 1.0;
    final double y0 = 0.0;
    final double y1 = 1.0;
    final double z0 = 0.0;
    final double z1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));

    final VectorI3D v0 = new VectorI3D(x0, y0, z0);
    final VectorI3D v1 = new VectorI3D(x1, y1, z1);
    Assert.assertFalse(VectorI3D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI3D v = new VectorI3D(3.0, 5.0, 7.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final VectorI3D maximum = new VectorI3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3D.clampMaximumByVector(v, maximum).z <= maximum.z);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final VectorI3D minimum = new VectorI3D(min_x, min_y, min_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3D.clampMinimumByVector(v, minimum).z >= minimum.z);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final VectorI3D minimum = new VectorI3D(min_x, min_y, min_z);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final VectorI3D maximum = new VectorI3D(max_x, max_y, max_z);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI3D.clampByVector(v, minimum, maximum).z >= minimum.z);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI3D.clampMaximum(v, maximum).z <= maximum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI3D.clampMinimum(v, minimum).z >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI3D.clamp(v, minimum, maximum).z >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductPerpendicular()
  {
    final VectorI3D vy = new VectorI3D(0, 1, 0);
    final VectorI3D vx = new VectorI3D(1, 0, 0);
    final VectorI3D vz = new VectorI3D(0, 0, 1);

    final VectorI3D vxy = VectorI3D.crossProduct(vx, vy);
    final VectorI3D vxz = VectorI3D.crossProduct(vx, vz);
    final VectorI3D vyz = VectorI3D.crossProduct(vy, vz);

    Assert.assertTrue(VectorI3D.dotProduct(vxy, vx) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vxy, vy) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vxz, vx) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vxz, vz) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vyz, vy) == 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(vyz, vz) == 0.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testCrossProductSimple()
  {
    final VectorI3D v0 = new VectorI3D(0, 1, 0);
    final VectorI3D v1 = new VectorI3D(1, 0, 0);
    final VectorI3D vr = VectorI3D.crossProduct(v0, v1);

    Assert.assertTrue(vr.x == 0.0);
    Assert.assertTrue(vr.y == 0.0);
    Assert.assertTrue(vr.z == -1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorI3D v0 = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 0.0, 0.0);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorI3D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      Assert.assertTrue(VectorI3D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorI3D v = new VectorI3D(1.0, 0.0, 0.0);
    Assert.assertTrue(VectorI3D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final double x0 = 2.0;
    final double y0 = 2.0;
    final double z0 = 2.0;
    final VectorI3D v0 = new VectorI3D(x0, y0, z0);

    final double x1 = 2.0;
    final double y1 = 2.0;
    final double z1 = 2.0;
    final VectorI3D v1 = new VectorI3D(x1, y1, z1);

    final double p = VectorI3D.dotProduct(v0, v1);
    Assert.assertTrue(p == 12.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorI3D m0 = new VectorI3D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorI3D m0 = new VectorI3D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorI3D m0 = new VectorI3D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorI3D m0 = new VectorI3D();
    final VectorI3D m1 = new VectorI3D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final VectorI3D v0 = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D vz = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D vy = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D vx = new VectorI3D(1.0, 0.0, 0.0);

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
    final VectorI3D v0 = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 0.0, 0.0);
    final VectorI3D vz = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D vy = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D vx = new VectorI3D(1.0, 0.0, 0.0);

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
    final VectorI3D v0 = new VectorI3D(1.0f, 2.0f, 3.0f);
    final VectorI3D v1 = new VectorI3D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      Assert.assertTrue(VectorI3D.approximatelyEqual(
        VectorI3D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI3D.approximatelyEqual(
        VectorI3D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final VectorI3D v = new VectorI3D(x, y, z);

      ApproximatelyEqualDouble.approximatelyEqual(
        VectorI3D.magnitude(VectorI3D.normalize(v)),
        1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testNoargZero()
  {
    final VectorI3D v = new VectorI3D();
    VectorI3D.approximatelyEqual(v, VectorI3D.ZERO);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorI3D v0 = new VectorI3D(8.0, 0.0, 0.0);
    final VectorI3D vr = VectorI3D.normalize(v0);
    final double m = VectorI3D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final VectorI3D v0 = new VectorI3D(0.0, 0.0, 0.0);
    VectorI3D.approximatelyEqual(VectorI3D.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorI3D p = new VectorI3D(1.0, 0.0, 0.0);
      final VectorI3D q = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D r = VectorI3D.projection(p, q);
      Assert.assertTrue(VectorI3D.magnitude(r) == 0.0);
    }

    {
      final VectorI3D p = new VectorI3D(-1.0, 0.0, 0.0);
      final VectorI3D q = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D r = VectorI3D.projection(p, q);
      Assert.assertTrue(VectorI3D.magnitude(r) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI3D v = new VectorI3D(0.0, 1.0, 2.0);
    Assert.assertTrue(v.toString().equals("[VectorI3D 0.0 1.0 2.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final VectorI3D vr = VectorI3D.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z - v1.z));
    }
  }
}
