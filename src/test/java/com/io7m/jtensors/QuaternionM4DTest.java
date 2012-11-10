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

public class QuaternionM4DTest
{
  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);

      final QuaternionM4D vr0 = new QuaternionM4D();
      QuaternionM4D.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getZD(),
        v0.getZD() + v1.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getWD(),
        v0.getWD() + v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getXD(),
          orig_x + v1.getXD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getYD(),
          orig_y + v1.getYD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getZD(),
          orig_z + v1.getZD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getWD(),
          orig_w + v1.getWD()));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final QuaternionM4D out = new QuaternionM4D();
    final QuaternionM4D v0 = new QuaternionM4D(1.0, 1.0, 1.0, 1.0);
    final QuaternionM4D v1 = new QuaternionM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final QuaternionM4D ov0 = QuaternionM4D.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(out.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final QuaternionM4D ov1 = QuaternionM4D.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(ov1.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v0.getWD() == 2.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);
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

    final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);
    final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);
    Assert.assertTrue(QuaternionM4D.approximatelyEqual(v0, v1));
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

    final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);
    final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);
    Assert.assertFalse(QuaternionM4D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final QuaternionM4D v = new QuaternionM4D(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
    Assert.assertTrue(v.getWD() == v.getWD());
  }

  @SuppressWarnings("static-method") @Test public void testConjugate()
  {
    final QuaternionM4D e = new QuaternionM4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionM4D q = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionM4D r = new QuaternionM4D();
    final QuaternionM4D u = QuaternionM4D.conjugate(q, r);
    final boolean t = QuaternionM4D.approximatelyEqual(e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @SuppressWarnings("static-method") @Test public void testConjugateInPlace()
  {
    final QuaternionM4D e = new QuaternionM4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionM4D q = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionM4D r = new QuaternionM4D(q);
    final QuaternionM4D u = QuaternionM4D.conjugateInPlace(r);
    final boolean t = QuaternionM4D.approximatelyEqual(e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final QuaternionM4D vb = new QuaternionM4D(5, 6, 7, 8);
    final QuaternionM4D va = new QuaternionM4D(1, 2, 3, 4);

    Assert.assertFalse(va.getXD() == vb.getXD());
    Assert.assertFalse(va.getYD() == vb.getYD());
    Assert.assertFalse(va.getZD() == vb.getZD());
    Assert.assertFalse(va.getWD() == vb.getWD());

    QuaternionM4D.copy(va, vb);

    Assert.assertTrue(va.getXD() == vb.getXD());
    Assert.assertTrue(va.getYD() == vb.getYD());
    Assert.assertTrue(va.getZD() == vb.getZD());
    Assert.assertTrue(va.getWD() == vb.getWD());
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final QuaternionM4D v0 = new QuaternionM4D(10.0, 10.0, 10.0, 10.0);
    final QuaternionM4D v1 = new QuaternionM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = QuaternionM4D.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = QuaternionM4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = QuaternionM4D.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXD() == 10.0);
      Assert.assertTrue(v1.getYD() == 10.0);
      Assert.assertTrue(v1.getZD() == 10.0);
      Assert.assertTrue(v1.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final QuaternionM4D v = new QuaternionM4D(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(QuaternionM4D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductSelfMagnitudeSquared()
  {
    final QuaternionM4D v0 = new QuaternionM4D(10.0, 10.0, 10.0, 10.0);

    {
      final double p = QuaternionM4D.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }

    {
      final double p = QuaternionM4D.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXD() == 10.0);
      Assert.assertTrue(v0.getYD() == 10.0);
      Assert.assertTrue(v0.getZD() == 10.0);
      Assert.assertTrue(v0.getWD() == 10.0);
      Assert.assertTrue(p == 400.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq0()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.x = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq1()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.y = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq2()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.z = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq3()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.w = 23.0;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeEq()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase0()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.x = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase1()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.y = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase2()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.z = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase3()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    m1.w = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final QuaternionM4D v0 = new QuaternionM4D(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4D v1 = new QuaternionM4D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
    Assert.assertTrue(v0.getWD() == v1.getWD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);

      final QuaternionM4D vr0 = new QuaternionM4D();
      final QuaternionM4D vr1 = new QuaternionM4D();
      QuaternionM4D.interpolateLinear(v0, v1, 0.0, vr0);
      QuaternionM4D.interpolateLinear(v0, v1, 1.0, vr1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.getXD(),
        vr0.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.getYD(),
        vr0.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.getZD(),
        vr0.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v0.getWD(),
        vr0.getWD()));

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.getXD(),
        vr1.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.getYD(),
        vr1.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.getZD(),
        vr1.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v1.getWD(),
        vr1.getWD()));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final double m = QuaternionM4D.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double w = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();
      QuaternionM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4D.magnitude(vr);
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMagnitudeNormalizeZero()
  {
    final QuaternionM4D v = new QuaternionM4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionM4D vr = QuaternionM4D.normalizeInPlace(v);
    final double m = QuaternionM4D.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final QuaternionM4D v = new QuaternionM4D(1.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final QuaternionM4D v = new QuaternionM4D(8.0, 0.0, 0.0, 0.0);

    {
      final double p = QuaternionM4D.dotProduct(v, v);
      final double q = QuaternionM4D.magnitudeSquared(v);
      final double r = QuaternionM4D.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final QuaternionM4D v = new QuaternionM4D(0.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_45()
  {
    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleX: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_90()
  {
    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleX: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_45()
  {
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleY: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_90()
  {
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleY: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_45()
  {
    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleZ: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_90()
  {
    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.err.println("testMakeAxisAngleZ: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrixIdentity()
  {
    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionM4D.makeRotationMatrix(q, m);

    Assert.assertTrue(1.0 == m.get(0, 0));
    Assert.assertTrue(0.0 == m.get(0, 1));
    Assert.assertTrue(0.0 == m.get(0, 2));
    Assert.assertTrue(0.0 == m.get(0, 3));

    Assert.assertTrue(0.0 == m.get(1, 0));
    Assert.assertTrue(1.0 == m.get(1, 1));
    Assert.assertTrue(0.0 == m.get(1, 2));
    Assert.assertTrue(0.0 == m.get(1, 3));

    Assert.assertTrue(0.0 == m.get(2, 0));
    Assert.assertTrue(0.0 == m.get(2, 1));
    Assert.assertTrue(1.0 == m.get(2, 2));
    Assert.assertTrue(0.0 == m.get(2, 3));

    Assert.assertTrue(0.0 == m.get(3, 0));
    Assert.assertTrue(0.0 == m.get(3, 1));
    Assert.assertTrue(0.0 == m.get(3, 2));
    Assert.assertTrue(1.0 == m.get(3, 3));
  }

  @SuppressWarnings("static-method") @Test public void testMultiply()
  {
    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionM4D qx = new QuaternionM4D();
    final QuaternionM4D qxr =
      QuaternionM4D.makeFromAxisAngle(axis_x, Math.toRadians(45), qx);
    final QuaternionM4D qy = new QuaternionM4D();
    final QuaternionM4D qyr =
      QuaternionM4D.makeFromAxisAngle(axis_y, Math.toRadians(45), qy);

    Assert.assertSame(qx, qxr);
    Assert.assertSame(qy, qyr);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4D qr = new QuaternionM4D();
    QuaternionM4D.multiply(qy, qx, qr);
    System.err.println("testMultiply: " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getXD(),
      0.3535533905932738));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getYD(),
      0.3535533905932738));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getZD(),
      -0.14644660940672624));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getWD(),
      0.8535533905932737));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final QuaternionM4D v0 = new QuaternionM4D(8.0, 0.0, 0.0, 0.0);
    final QuaternionM4D out = new QuaternionM4D();
    final QuaternionM4D vr = QuaternionM4D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = QuaternionM4D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final QuaternionM4D out = new QuaternionM4D();
    final QuaternionM4D v0 = new QuaternionM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);

    final QuaternionM4D ov0 = QuaternionM4D.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 2.0);
    Assert.assertTrue(out.getYD() == 2.0);
    Assert.assertTrue(out.getZD() == 2.0);
    Assert.assertTrue(out.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);

    final QuaternionM4D ov1 = QuaternionM4D.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 2.0);
    Assert.assertTrue(ov1.getYD() == 2.0);
    Assert.assertTrue(ov1.getZD() == 2.0);
    Assert.assertTrue(ov1.getWD() == 2.0);
    Assert.assertTrue(v0.getXD() == 2.0);
    Assert.assertTrue(v0.getYD() == 2.0);
    Assert.assertTrue(v0.getZD() == 2.0);
    Assert.assertTrue(v0.getWD() == 2.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();

      QuaternionM4D.scale(v, 1.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v.getYD(),
        vr.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v.getZD(),
        vr.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        v.getWD(),
        vr.getWD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();
        final double orig_w = v.getWD();

        QuaternionM4D.scaleInPlace(v, 1.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getXD(),
          orig_x));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getYD(),
          orig_y));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getZD(),
          orig_z));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getWD(),
          orig_w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < 100; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();

      QuaternionM4D.scale(v, 0.0, vr);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getXD(),
        0.0));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getYD(),
        0.0));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getZD(),
        0.0));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getWD(),
        0.0));

      {
        QuaternionM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getXD(),
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getYD(),
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getZD(),
          0.0));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v.getWD(),
          0.0));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final QuaternionM4D v = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().equals("[QuaternionM4D 1.0 2.0 3.0 4.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);

      final QuaternionM4D vr0 = new QuaternionM4D();
      QuaternionM4D.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getZD(),
        v0.getZD() - v1.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr0.getWD(),
        v0.getWD() - v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getXD(),
          orig_x - v1.getXD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getYD(),
          orig_y - v1.getYD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getZD(),
          orig_z - v1.getZD()));
        Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
          v0.getWD(),
          orig_w - v1.getWD()));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final QuaternionM4D out = new QuaternionM4D();
    final QuaternionM4D v0 = new QuaternionM4D(1.0, 1.0, 1.0, 1.0);
    final QuaternionM4D v1 = new QuaternionM4D(1.0, 1.0, 1.0, 1.0);

    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 1.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final QuaternionM4D ov0 = QuaternionM4D.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXD() == 0.0);
    Assert.assertTrue(out.getYD() == 0.0);
    Assert.assertTrue(out.getZD() == 0.0);
    Assert.assertTrue(out.getWD() == 0.0);
    Assert.assertTrue(v0.getXD() == 1.0);
    Assert.assertTrue(v0.getYD() == 1.0);
    Assert.assertTrue(v0.getZD() == 1.0);
    Assert.assertTrue(v0.getWD() == 1.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);

    final QuaternionM4D ov1 = QuaternionM4D.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXD() == 0.0);
    Assert.assertTrue(ov1.getYD() == 0.0);
    Assert.assertTrue(ov1.getZD() == 0.0);
    Assert.assertTrue(ov1.getWD() == 0.0);
    Assert.assertTrue(v0.getXD() == 0.0);
    Assert.assertTrue(v0.getYD() == 0.0);
    Assert.assertTrue(v0.getZD() == 0.0);
    Assert.assertTrue(v0.getWD() == 0.0);
    Assert.assertTrue(v1.getXD() == 1.0);
    Assert.assertTrue(v1.getYD() == 1.0);
    Assert.assertTrue(v1.getZD() == 1.0);
    Assert.assertTrue(v1.getWD() == 1.0);
  }
}
