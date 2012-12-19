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
import com.io7m.jaux.functional.Pair;

public class VectorI4DTest
{
  @SuppressWarnings("static-method") @Test public void testAbsoluteOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.absolute(v).x >= 0.0);
      Assert.assertTrue(VectorI4D.absolute(v).y >= 0.0);
      Assert.assertTrue(VectorI4D.absolute(v).z >= 0.0);
      Assert.assertTrue(VectorI4D.absolute(v).w >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final VectorI4D vr = VectorI4D.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z + v1.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.w,
        v0.w + v1.w));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final double r = Math.random() * Double.MAX_VALUE;
      final VectorI4D vr = VectorI4D.addScaled(v0, v1, r);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x + (v1.x * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y + (v1.y * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z + (v1.z * r)));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.w,
        v0.w + (v1.w * r)));
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
    final double w0 = 0.0;
    final double w1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);
    final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);
    Assert.assertTrue(VectorI4D.approximatelyEqual(v0, v1));
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
    final double w0 = 0.0;
    final double w1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);
    final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);
    Assert.assertFalse(VectorI4D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI4D v = new VectorI4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertTrue(v.x == v.getXD());
    Assert.assertTrue(v.y == v.getYD());
    Assert.assertTrue(v.z == v.getZD());
    Assert.assertTrue(v.w == v.getWD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max_x = Math.random() * Double.MIN_VALUE;
      final double max_y = Math.random() * Double.MIN_VALUE;
      final double max_z = Math.random() * Double.MIN_VALUE;
      final double max_w = Math.random() * Double.MIN_VALUE;
      final VectorI4D maximum = new VectorI4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4D.clampMaximumByVector(v, maximum).w <= maximum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MAX_VALUE;
      final double min_y = Math.random() * Double.MAX_VALUE;
      final double min_z = Math.random() * Double.MAX_VALUE;
      final double min_w = Math.random() * Double.MAX_VALUE;
      final VectorI4D minimum = new VectorI4D(min_x, min_y, min_z, min_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4D.clampMinimumByVector(v, minimum).w >= minimum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double min_x = Math.random() * Double.MIN_VALUE;
      final double min_y = Math.random() * Double.MIN_VALUE;
      final double min_z = Math.random() * Double.MIN_VALUE;
      final double min_w = Math.random() * Double.MIN_VALUE;
      final VectorI4D minimum = new VectorI4D(min_x, min_y, min_z, min_w);

      final double max_x = Math.random() * Double.MAX_VALUE;
      final double max_y = Math.random() * Double.MAX_VALUE;
      final double max_z = Math.random() * Double.MAX_VALUE;
      final double max_w = Math.random() * Double.MAX_VALUE;
      final VectorI4D maximum = new VectorI4D(max_x, max_y, max_z, max_w);

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).x <= maximum.x);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).x >= minimum.x);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).y <= maximum.y);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).y >= minimum.y);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).z <= maximum.z);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).z >= minimum.z);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).w <= maximum.w);
      Assert
        .assertTrue(VectorI4D.clampByVector(v, minimum, maximum).w >= minimum.w);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double maximum = Math.random() * Double.MIN_VALUE;

      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).x <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).y <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).z <= maximum);
      Assert.assertTrue(VectorI4D.clampMaximum(v, maximum).w <= maximum);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MIN_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).x >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).y >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).z >= minimum);
      Assert.assertTrue(VectorI4D.clampMinimum(v, minimum).w >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double minimum = Math.random() * Double.MIN_VALUE;
      final double maximum = Math.random() * Double.MAX_VALUE;

      final double x = Math.random() * Double.MIN_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MIN_VALUE;
      final double w = Math.random() * Double.MIN_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).x <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).x >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).y <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).y >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).z <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).z >= minimum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).w <= maximum);
      Assert.assertTrue(VectorI4D.clamp(v, minimum, maximum).w >= minimum);
    }
  }

  @SuppressWarnings("static-method") @Test public void testDistance()
  {
    final VectorI4D v0 = new VectorI4D(0.0, 1.0, 0.0, 0.0);
    final VectorI4D v1 = new VectorI4D(0.0, 0.0, 0.0, 0.0);

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      VectorI4D.distance(v0, v1),
      1.0));
  }

  @SuppressWarnings("static-method") @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4D.distance(v0, v1) >= 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final VectorI4D v = new VectorI4D(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(VectorI4D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final double x0 = 2.0;
    final double y0 = 2.0;
    final double z0 = 2.0;
    final double w0 = 2.0;
    final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

    final double x1 = 2.0;
    final double y1 = 2.0;
    final double z1 = 2.0;
    final double w1 = 2.0;
    final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

    final double p = VectorI4D.dotProduct(v0, v1);
    Assert.assertTrue(p == 16.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final VectorI4D m0 = new VectorI4D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final VectorI4D m0 = new VectorI4D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final VectorI4D m0 = new VectorI4D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final VectorI4D m0 = new VectorI4D();
    final VectorI4D m1 = new VectorI4D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final VectorI4D v0 = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D v1 = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D vw = new VectorI4D(0.0, 0.0, 0.0, 1.0);
    final VectorI4D vz = new VectorI4D(0.0, 0.0, 1.0, 0.0);
    final VectorI4D vy = new VectorI4D(0.0, 1.0, 0.0, 0.0);
    final VectorI4D vx = new VectorI4D(1.0, 0.0, 0.0, 0.0);

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
    final VectorI4D v0 = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D v1 = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    final VectorI4D vw = new VectorI4D(0.0, 0.0, 0.0, 1.0);
    final VectorI4D vz = new VectorI4D(0.0, 0.0, 1.0, 0.0);
    final VectorI4D vy = new VectorI4D(0.0, 1.0, 0.0, 0.0);
    final VectorI4D vx = new VectorI4D(1.0, 0.0, 0.0, 0.0);

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
    final VectorI4D v0 = new VectorI4D(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorI4D v1 = new VectorI4D(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
    Assert.assertTrue(v0.w == v1.w);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      Assert.assertTrue(VectorI4D.approximatelyEqual(
        VectorI4D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(VectorI4D.approximatelyEqual(
        VectorI4D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final VectorI4D v = new VectorI4D(x, y, z, w);

      ApproximatelyEqualDouble.approximatelyEqual(
        VectorI4D.magnitude(VectorI4D.normalize(v)),
        1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testNoargZero()
  {
    final VectorI4D v = new VectorI4D();
    VectorI4D.approximatelyEqual(v, VectorI4D.ZERO);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final VectorI4D v0 = new VectorI4D(8.0, 0.0, 0.0, 0.0);
    final VectorI4D vr = VectorI4D.normalize(v0);
    final double m = VectorI4D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final VectorI4D v0 = new VectorI4D(0.0, 0.0, 0.0, 0.0);
    VectorI4D.approximatelyEqual(VectorI4D.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public void testOrthonormalize()
  {
    final VectorI4D v0 = new VectorI4D(0, 1, 0, 0);
    final VectorI4D v1 = new VectorI4D(0.5, 0.5, 0, 0);
    final Pair<VectorI4D, VectorI4D> on = VectorI4D.orthoNormalize(v0, v1);

    Assert.assertEquals(v0, on.first);
    Assert.assertEquals(new VectorI4D(1, 0, 0, 0), on.second);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testProjectionPerpendicularZero()
  {
    {
      final VectorI4D p = new VectorI4D(1.0, 0.0, 0.0, 0.0);
      final VectorI4D q = new VectorI4D(0.0, 1.0, 0.0, 0.0);
      final VectorI4D r = VectorI4D.projection(p, q);
      Assert.assertTrue(VectorI4D.magnitude(r) == 0.0);
    }

    {
      final VectorI4D p = new VectorI4D(-1.0, 0.0, 0.0, 0.0);
      final VectorI4D q = new VectorI4D(0.0, 1.0, 0.0, 0.0);
      final VectorI4D r = VectorI4D.projection(p, q);
      Assert.assertTrue(VectorI4D.magnitude(r) == 0.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI4D v = new VectorI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().equals("[VectorI4D 0.0 1.0 2.0 3.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v0 = new VectorI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final VectorI4D v1 = new VectorI4D(x1, y1, z1, w1);

      final VectorI4D vr = VectorI4D.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.x,
        v0.x - v1.x));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.y,
        v0.y - v1.y));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.z,
        v0.z - v1.z));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.w,
        v0.w - v1.w));
    }
  }
}
