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
import com.io7m.jaux.ApproximatelyEqualDouble;
import com.io7m.jaux.ApproximatelyEqualFloat;

public class QuaternionM4FTest
{
  private static final VectorReadable3F AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3F AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3F AXIS_Z = new VectorI3F(0, 0, 1);

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      QuaternionM4F.add(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getYF(),
        v0.getYF() + v1.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getZF(),
        v0.getZF() + v1.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getWF(),
        v0.getWF() + v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.addInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getXF(),
          orig_x + v1.getXF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getYF(),
          orig_y + v1.getYF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getZF(),
          orig_z + v1.getZF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getWF(),
          orig_w + v1.getWF()));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testAddMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final QuaternionM4F v1 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

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

    final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);
    final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);
    Assert.assertTrue(QuaternionM4F.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

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

    final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);
    final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);
    Assert.assertFalse(QuaternionM4F.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final QuaternionM4F v = new QuaternionM4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @SuppressWarnings("static-method") @Test public void testConjugate()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F e = new QuaternionM4F(-1.0f, -2.0f, -3.0f, 4.0f);
    final QuaternionM4F q = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F r = new QuaternionM4F();
    final QuaternionM4F u = QuaternionM4F.conjugate(q, r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @SuppressWarnings("static-method") @Test public void testConjugateInPlace()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F e = new QuaternionM4F(-1.0f, -2.0f, -3.0f, 4.0f);
    final QuaternionM4F q = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F r = new QuaternionM4F(q);
    final QuaternionM4F u = QuaternionM4F.conjugateInPlace(r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testConjugateInvertible()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((Math.random() * 200) - 100);
      final float y = (float) ((Math.random() * 200) - 100);
      final float z = (float) ((Math.random() * 200) - 100);
      final float w = (float) ((Math.random() * 200) - 100);

      final QuaternionM4F q = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F qc0 = new QuaternionM4F();
      final QuaternionM4F qc1 = new QuaternionM4F();
      QuaternionM4F.conjugate(q, qc0);
      QuaternionM4F.conjugate(qc0, qc1);

      eq = AlmostEqualFloat.almostEqual(context, q.getXF(), qc1.getXF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getYF(), qc1.getYF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getZF(), qc1.getZF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getWF(), qc1.getWF());
      Assert.assertTrue(eq);
    }
  }

  @SuppressWarnings("static-method") @Test public void testCopy()
  {
    final QuaternionM4F vb = new QuaternionM4F(5, 6, 7, 8);
    final QuaternionM4F va = new QuaternionM4F(1, 2, 3, 4);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());
    Assert.assertFalse(va.getWF() == vb.getWF());

    QuaternionM4F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
    Assert.assertTrue(va.getZF() == vb.getZF());
    Assert.assertTrue(va.getWF() == vb.getWF());
  }

  @SuppressWarnings("static-method") @Test public void testDotProduct()
  {
    final QuaternionM4F v0 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);
    final QuaternionM4F v1 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v1);
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
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = QuaternionM4F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(v1.getZF() == 10.0f);
      Assert.assertTrue(v1.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final QuaternionM4F v = new QuaternionM4F(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(QuaternionM4F.dotProduct(v, v) == 1.0f);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductSelfMagnitudeSquared()
  {
    final QuaternionM4F v0 = new QuaternionM4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }

    {
      final double p = QuaternionM4F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v0.getZF() == 10.0f);
      Assert.assertTrue(v0.getWF() == 10.0f);
      Assert.assertTrue(p == 400.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase0()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    Assert.assertTrue(m0.equals(m0));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase1()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    Assert.assertFalse(m0.equals(null));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase2()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    Assert.assertFalse(m0.equals(Integer.valueOf(23)));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCase3()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    Assert.assertTrue(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq0()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.x = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq1()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.y = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq2()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.z = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCaseNeq3()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.w = 23.0f;
    Assert.assertFalse(m0.equals(m1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeEq()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase0()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.x = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase1()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.y = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase2()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.z = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeNeqCase3()
  {
    final QuaternionM4F m0 = new QuaternionM4F();
    final QuaternionM4F m1 = new QuaternionM4F();
    m1.w = 23;
    Assert.assertFalse(m0.hashCode() == m1.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4F v1 = new QuaternionM4F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      final QuaternionM4F vr1 = new QuaternionM4F();
      QuaternionM4F.interpolateLinear(v0, v1, 0.0f, vr0);
      QuaternionM4F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.getXF(),
        vr0.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.getYF(),
        vr0.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.getZF(),
        vr0.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v0.getWF(),
        vr0.getWF()));

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.getXF(),
        vr1.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.getYF(),
        vr1.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.getZF(),
        vr1.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v1.getWF(),
        vr1.getWF()));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final double m = QuaternionM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float y =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float z =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final float w =
        (float) (Math.random() * (Math.sqrt(Float.MAX_VALUE) / 2));
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();
      QuaternionM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMagnitudeNormalizeZero()
  {
    final QuaternionM4F v = new QuaternionM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionM4F vr = QuaternionM4F.normalizeInPlace(v);
    final double m = QuaternionM4F.magnitude(vr);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeOne()
  {
    final QuaternionM4F v = new QuaternionM4F(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 1.0));
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeSimple()
  {
    final QuaternionM4F v = new QuaternionM4F(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = QuaternionM4F.dotProduct(v, v);
      final double q = QuaternionM4F.magnitudeSquared(v);
      final double r = QuaternionM4F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeZero()
  {
    final QuaternionM4F v = new QuaternionM4F(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(m, 0.0));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3F axis_r =
        new VectorI3F(
          (float) Math.random(),
          (float) Math.random(),
          (float) Math.random());
      final VectorI3F axis_n = VectorI3F.normalize(axis_r);

      final QuaternionM4F q = new QuaternionM4F();
      QuaternionM4F.makeFromAxisAngle(
        axis_n,
        Math.toRadians(Math.random() * 360),
        q);

      final double m = QuaternionM4F.magnitude(q);

      System.out.println("axis_r : " + axis_r);
      System.out.println("axis_n : " + axis_n);
      System.out.println("m      : " + m);
      System.out.println("q      : " + q);
      System.out.println("--");

      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_45()
  {
    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_90()
  {
    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_45()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_90()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_45()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_90()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionM4F q = new QuaternionM4F();
    final QuaternionM4F r =
      QuaternionM4F.makeFromAxisAngle(axis, (float) Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeFromMatrix3x3Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final QuaternionM4F qfm = new QuaternionM4F();
    final QuaternionM4F qaa = new QuaternionM4F();
    final MatrixM3x3F m = new MatrixM3x3F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2 * Math.random() * 360.0f) - 360.0f);
      final float angle = (float) Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3F.makeRotation(angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix3x3(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4F.almostEqual(context_f, qfm, qaa)) {
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4F.isNegationOf(context_f, qfm, qaa)) {
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeFromMatrix4x4Exhaustive()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F qfm = new QuaternionM4F();
    final QuaternionM4F qaa = new QuaternionM4F();
    final MatrixM4x4F m = new MatrixM4x4F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2 * Math.random() * 360.0f) - 360.0f);
      final float angle = (float) Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4F.makeRotation(angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix4x4(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionM4F.almostEqual(context, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4F.isNegationOf(context, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Identity()
  {
    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F m = new MatrixM3x3F();

    QuaternionM4F.makeRotationMatrix3x3(q, m);

    Assert.assertTrue(1.0f == m.get(0, 0));
    Assert.assertTrue(0.0f == m.get(0, 1));
    Assert.assertTrue(0.0f == m.get(0, 2));

    Assert.assertTrue(0.0f == m.get(1, 0));
    Assert.assertTrue(1.0f == m.get(1, 1));
    Assert.assertTrue(0.0f == m.get(1, 2));

    Assert.assertTrue(0.0f == m.get(2, 0));
    Assert.assertTrue(0.0f == m.get(2, 1));
    Assert.assertTrue(1.0f == m.get(2, 2));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Identity()
  {
    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F m = new MatrixM4x4F();

    QuaternionM4F.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0f == m.get(0, 0));
    Assert.assertTrue(0.0f == m.get(0, 1));
    Assert.assertTrue(0.0f == m.get(0, 2));
    Assert.assertTrue(0.0f == m.get(0, 3));

    Assert.assertTrue(0.0f == m.get(1, 0));
    Assert.assertTrue(1.0f == m.get(1, 1));
    Assert.assertTrue(0.0f == m.get(1, 2));
    Assert.assertTrue(0.0f == m.get(1, 3));

    Assert.assertTrue(0.0f == m.get(2, 0));
    Assert.assertTrue(0.0f == m.get(2, 1));
    Assert.assertTrue(1.0f == m.get(2, 2));
    Assert.assertTrue(0.0f == m.get(2, 3));

    Assert.assertTrue(0.0f == m.get(3, 0));
    Assert.assertTrue(0.0f == m.get(3, 1));
    Assert.assertTrue(0.0f == m.get(3, 2));
    Assert.assertTrue(1.0f == m.get(3, 3));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F q = new QuaternionM4F();
    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionM4FTest.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testMultiply()
  {
    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionM4F qx = new QuaternionM4F();
    final QuaternionM4F qxr =
      QuaternionM4F.makeFromAxisAngle(axis_x, Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    final QuaternionM4F qyr =
      QuaternionM4F.makeFromAxisAngle(axis_y, Math.toRadians(45), qy);

    Assert.assertSame(qx, qxr);
    Assert.assertSame(qy, qyr);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiply(qy, qx, qr);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getXF(),
      0.3535533905932738f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getYF(),
      0.3535533905932738f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getZF(),
      -0.14644660940672624f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getWF(),
      0.8535533905932737f));
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyInPlace()
  {
    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getXF(),
      0.3535533983204287f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getYF(),
      0.3535533983204287f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getZF(),
      -0.14644661713388138f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getWF(),
      0.8535533828661185f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMultiplyInPlaceOther()
  {
    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4F qz = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiplyInPlace(qr, qz);
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getXF(),
      0.1913417153164435f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getYF(),
      0.4619397784426109f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getZF(),
      0.1913417153164436f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getWF(),
      0.8446231923478736f));
  }

  @SuppressWarnings("static-method") @Test public void testMultiplyOther()
  {
    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final QuaternionM4F qx = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4F qy = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4F qz = new QuaternionM4F();
    QuaternionM4F.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4F qr = new QuaternionM4F();
    QuaternionM4F.multiply(qy, qx, qr);
    QuaternionM4F.multiply(qz, qr, qr);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getXF(),
      0.1913417153164435f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getYF(),
      0.4619397784426109f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getZF(),
      0.1913417153164436f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getWF(),
      0.8446231923478736f));
  }

  @SuppressWarnings("static-method") @Test public void testNegation()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((Math.random() * 2) - Math.random());
      final float y = (float) ((Math.random() * 2) - Math.random());
      final float z = (float) ((Math.random() * 2) - Math.random());
      final float w = (float) ((Math.random() * 2) - Math.random());
      final QuaternionM4F qi = new QuaternionM4F(x, y, z, w);
      final QuaternionM4F qn = new QuaternionM4F(-x, -y, -z, -w);
      final QuaternionM4F qr = new QuaternionM4F();

      QuaternionM4F.negate(qi, qr);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionM4F.almostEqual(context, qn, qr));
    }
  }

  @SuppressWarnings("static-method") @Test public void testNegationCases()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F qi = new QuaternionM4F(1, 2, 3, 4);
    final QuaternionM4F qnx = new QuaternionM4F(-1, 2, 3, 4);
    final QuaternionM4F qny = new QuaternionM4F(-1, -2, 3, 4);
    final QuaternionM4F qnz = new QuaternionM4F(-1, -2, -3, 4);
    final QuaternionM4F qnw = new QuaternionM4F(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnw) == true);

    QuaternionM4F.negateInPlace(qnw);
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnw) == false);
    Assert.assertTrue(QuaternionM4F.almostEqual(context, qi, qnw));
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final QuaternionM4F v0 = new QuaternionM4F(8.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F vr = QuaternionM4F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = QuaternionM4F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.scale(v0, 2.0, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(out.getZF() == 2.0);
    Assert.assertTrue(out.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.scaleInPlace(v0, 2.0);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(ov1.getZF() == 2.0);
    Assert.assertTrue(ov1.getWF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v0.getZF() == 2.0);
    Assert.assertTrue(v0.getWF() == 2.0);
  }

  @SuppressWarnings("static-method") @Test public void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();

      QuaternionM4F.scale(v, 1.0f, vr);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v.getYF(),
        vr.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v.getZF(),
        vr.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        v.getWF(),
        vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        QuaternionM4F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getXF(),
          orig_x));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getYF(),
          orig_y));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getZF(),
          orig_z));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getWF(),
          orig_w));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v = new QuaternionM4F(x, y, z, w);

      final QuaternionM4F vr = new QuaternionM4F();

      QuaternionM4F.scale(v, 0.0f, vr);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getXF(),
        0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getYF(),
        0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getZF(),
        0.0f));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getWF(),
        0.0f));

      {
        QuaternionM4F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getXF(),
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getYF(),
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getZF(),
          0.0f));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v.getWF(),
          0.0f));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final QuaternionM4F v = new QuaternionM4F(1.0f, 2.0f, 3.0f, 4.0f);
    System.out.println(v);
    Assert.assertTrue(v.toString().equals("[QuaternionM4F 1.0 2.0 3.0 4.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v0 = new QuaternionM4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionM4F v1 = new QuaternionM4F(x1, y1, z1, w1);

      final QuaternionM4F vr0 = new QuaternionM4F();
      QuaternionM4F.subtract(v0, v1, vr0);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getYF(),
        v0.getYF() - v1.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getZF(),
        v0.getZF() - v1.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr0.getWF(),
        v0.getWF() - v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getXF(),
          orig_x - v1.getXF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getYF(),
          orig_y - v1.getYF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getZF(),
          orig_z - v1.getZF()));
        Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
          v0.getWF(),
          orig_w - v1.getWF()));
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testSubtractMutation()
  {
    final QuaternionM4F out = new QuaternionM4F();
    final QuaternionM4F v0 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);
    final QuaternionM4F v1 = new QuaternionM4F(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 1.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov0 = QuaternionM4F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(out.getZF() == 0.0f);
    Assert.assertTrue(out.getWF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v0.getZF() == 1.0f);
    Assert.assertTrue(v0.getWF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);

    final QuaternionM4F ov1 = QuaternionM4F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0f);
    Assert.assertTrue(ov1.getYF() == 0.0f);
    Assert.assertTrue(ov1.getZF() == 0.0f);
    Assert.assertTrue(ov1.getWF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 0.0f);
    Assert.assertTrue(v0.getYF() == 0.0f);
    Assert.assertTrue(v0.getZF() == 0.0f);
    Assert.assertTrue(v0.getWF() == 0.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
    Assert.assertTrue(v1.getZF() == 1.0f);
    Assert.assertTrue(v1.getWF() == 1.0f);
  }
}
