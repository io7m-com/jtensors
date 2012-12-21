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
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;

public class QuaternionM4DTest extends QuaternionM4Contract
{
  private static final VectorReadable3D AXIS_X = new VectorI3D(1, 0, 0);
  private static final VectorReadable3D AXIS_Y = new VectorI3D(0, 1, 0);
  private static final VectorReadable3D AXIS_Z = new VectorI3D(0, 0, 1);

  @Override @Test public void testAdd()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;
      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final double w0 = Math.random() * max;
      final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final double w1 = Math.random() * max;
      final QuaternionM4D v1 = new QuaternionM4D(x1, y1, z1, w1);

      final QuaternionM4D vr0 = new QuaternionM4D();
      QuaternionM4D.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getZD(),
        v0.getZD() + v1.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getWD(),
        v0.getWD() + v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x + v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y + v1.getYD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getZD(),
          orig_z + v1.getZD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getWD(),
          orig_w + v1.getWD()));
      }
    }
  }

  @Override @Test public void testAddMutation()
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

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, q, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, z, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, q, w);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, z, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, q, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, q, q);
      Assert.assertFalse(QuaternionM4D.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v0 = new QuaternionM4D(x0, y0, z0, w0);
      final QuaternionM4D v1 = new QuaternionM4D(x0, y0, z0, w0);
      final QuaternionM4D v2 = new QuaternionM4D(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionM4D.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final QuaternionM4D v = new QuaternionM4D(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
    Assert.assertTrue(v.getWD() == v.getWD());
  }

  @Override @Test public void testConjugate()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D e = new QuaternionM4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionM4D q = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionM4D r = new QuaternionM4D();
    final QuaternionM4D u = QuaternionM4D.conjugate(q, r);
    final boolean t = QuaternionM4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Override @Test public void testConjugateInPlace()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D e = new QuaternionM4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionM4D q = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionM4D r = new QuaternionM4D(q);
    final QuaternionM4D u = QuaternionM4D.conjugateInPlace(r);
    final boolean t = QuaternionM4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Override @Test public void testConjugateInvertible()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 200) - 100;
      final double y = (Math.random() * 200) - 100;
      final double z = (Math.random() * 200) - 100;
      final double w = (Math.random() * 200) - 100;

      final QuaternionM4D q = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D qc0 = new QuaternionM4D();
      final QuaternionM4D qc1 = new QuaternionM4D();
      QuaternionM4D.conjugate(q, qc0);
      QuaternionM4D.conjugate(qc0, qc1);

      eq = AlmostEqualDouble.almostEqual(context_d, q.getXD(), qc1.getXD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getYD(), qc1.getYD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getZD(), qc1.getZD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getWD(), qc1.getWD());
      Assert.assertTrue(eq);
    }
  }

  @Override @Test public void testCopy()
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

  @Override @Test public void testDefault0001()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    Assert.assertTrue(QuaternionM4D.almostEqual(
      ec,
      new QuaternionM4D(),
      new QuaternionM4D(0, 0, 0, 1)));
  }

  @Override @Test public void testDotProduct()
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

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final QuaternionM4D q = new QuaternionM4D(x, y, z, w);
      final double dp = QuaternionM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random();
      final double y = Math.random();
      final double z = Math.random();
      final double w = Math.random();
      final QuaternionM4D q = new QuaternionM4D(x, y, z, w);

      final double ms = QuaternionM4D.magnitudeSquared(q);
      final double dp = QuaternionM4D.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final QuaternionM4D m0 = new QuaternionM4D();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      final QuaternionM4D m1 = new QuaternionM4D();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final double x = Math.random();
    final double y = x + 1.0;
    final double z = y + 1.0;
    final double w = z + 1.0;
    final double q = w + 1.0;

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D m1 = new QuaternionM4D(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final QuaternionM4D m0 = new QuaternionM4D();
      final QuaternionM4D m1 = new QuaternionM4D();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      final QuaternionM4D m1 = new QuaternionM4D();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      final QuaternionM4D m1 = new QuaternionM4D();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionM4D m0 = new QuaternionM4D();
      final QuaternionM4D m1 = new QuaternionM4D();
      m1.w = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final QuaternionM4D v0 = new QuaternionM4D(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionM4D v1 = new QuaternionM4D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
    Assert.assertTrue(v0.getWD() == v1.getWD());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getXD(),
        vr0.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getYD(),
        vr0.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getZD(),
        vr0.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v0.getWD(),
        vr0.getWD()));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getXD(),
        vr1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getYD(),
        vr1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getZD(),
        vr1.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v1.getWD(),
        vr1.getWD()));
    }
  }

  @Override @Test public void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final QuaternionM4D.Context qc = new QuaternionM4D.Context();

    final MatrixM4x4D mr = new MatrixM4x4D();
    final MatrixM4x4D mqr = new MatrixM4x4D();
    final QuaternionM4D qr = new QuaternionM4D();

    final VectorReadable3D origin = new VectorI3D(0, 0, 0);
    final VectorReadable3D target = new VectorI3D(-1, 0, 0);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, mr);
    QuaternionM4D.lookAtWithContext(qc, origin, target, axis, qr);
    QuaternionM4D.makeRotationMatrix4x4(qr, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mqr.get(row, col);
        eq = AlmostEqualDouble.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();
    final QuaternionM4D.Context qc = new QuaternionM4D.Context();

    final MatrixM4x4D mr = new MatrixM4x4D();
    final MatrixM4x4D mqr = new MatrixM4x4D();
    final QuaternionM4D qr = new QuaternionM4D();

    final VectorReadable3D origin = new VectorI3D(0, 0, 0);
    final VectorReadable3D target = new VectorI3D(1, 0, 0);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM4x4D.lookAtWithContext(mc, origin, target, axis, mr);
    QuaternionM4D.lookAtWithContext(qc, origin, target, axis, qr);
    QuaternionM4D.makeRotationMatrix4x4(qr, mqr);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mqr: ");
    System.out.println(mqr);

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mqr.get(row, col);
        eq = AlmostEqualDouble.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D.Context qc = new QuaternionM4D.Context();
    final MatrixM4x4D.Context mc = new MatrixM4x4D.Context();

    final MatrixM4x4D ml = new MatrixM4x4D();
    final QuaternionM4D lq = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double origin_x = (Math.random() * 100) - (Math.random() * 100);
      final double origin_y = (Math.random() * 100) - (Math.random() * 100);
      final double origin_z = (Math.random() * 100) - (Math.random() * 100);

      final double target_x = (Math.random() * 100) - (Math.random() * 100);
      final double target_y = (Math.random() * 100) - (Math.random() * 100);
      final double target_z = (Math.random() * 100) - (Math.random() * 100);

      final VectorI3D origin = new VectorI3D(origin_x, origin_y, origin_z);
      final VectorI3D target = new VectorI3D(target_x, target_y, target_z);

      MatrixM4x4D.lookAtWithContext(
        mc,
        origin,
        target,
        QuaternionM4DTest.AXIS_Y,
        ml);
      QuaternionM4D.lookAtWithContext(
        qc,
        origin,
        target,
        QuaternionM4DTest.AXIS_Y,
        lq);
      QuaternionM4D.makeRotationMatrix4x4(lq, mq);

      System.out.println("ml : ");
      System.out.println(ml);
      System.out.println("mq : ");
      System.out.println(mq);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final double x = ml.get(row, col);
          final double y = mq.get(row, col);

          final boolean eq = AlmostEqualDouble.almostEqual(ec, x, y);
          Assert.assertTrue(eq);
        }
      }
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double y = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double z = 1.0 + (Math.random() * Double.MAX_VALUE);
      final double w = 1.0 + (Math.random() * Double.MAX_VALUE);
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final double m = QuaternionM4D.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double y = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double z = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final double w = Math.random() * (Math.sqrt(Double.MAX_VALUE) / 2);
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();
      QuaternionM4D.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4D.magnitude(vr);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D v = new QuaternionM4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionM4D vr = QuaternionM4D.normalizeInPlace(v);
    final double m = QuaternionM4D.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D v = new QuaternionM4D(1.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
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

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D v = new QuaternionM4D(0.0, 0.0, 0.0, 0.0);
    final double m = QuaternionM4D.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testMakeAxisAngleNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3D axis_r =
        new VectorI3D(
          (float) Math.random(),
          (float) Math.random(),
          (float) Math.random());
      final VectorI3D axis_n = VectorI3D.normalize(axis_r);

      final QuaternionM4D q = new QuaternionM4D();
      QuaternionM4D.makeFromAxisAngle(
        axis_n,
        (float) Math.toRadians(Math.random() * 360),
        q);

      final double m = QuaternionM4D.magnitude(q);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));

      System.out.println("axis_r : " + axis_r);
      System.out.println("axis_n : " + axis_n);
      System.out.println("m      : " + m);
      System.out.println("q      : " + q);
      System.out.println("--");
    }
  }

  @Override @Test public void testMakeAxisAngleX_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getXD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleX_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getXD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeAxisAngleY_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getYD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleY_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getYD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getZD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeAxisAngleZ_45()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(45), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getZD(),
      0.3826834323650898));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.9238795325112867));
  }

  @Override @Test public void testMakeAxisAngleZ_90()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionM4D q = new QuaternionM4D();
    final QuaternionM4D r =
      QuaternionM4D.makeFromAxisAngle(axis, Math.toRadians(90), q);
    Assert.assertSame(r, q);

    System.out.println("r : " + r);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getXD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, q.getYD(), 0.0));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getZD(),
      0.7071067811865475));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      q.getWD(),
      0.7071067811865475));
  }

  @Override @Test public void testMakeFromMatrix3x3Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D qfm = new QuaternionM4D();
    final QuaternionM4D qaa = new QuaternionM4D();
    final MatrixM3x3D m = new MatrixM3x3D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4D.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3D.makeRotation(angle, axis, m);
      QuaternionM4D.makeFromRotationMatrix3x3(m, qfm);

      final double mag_qfm = QuaternionM4D.magnitude(qfm);
      final double mag_qaa = QuaternionM4D.magnitude(qaa);

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

      if (QuaternionM4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public void testMakeFromMatrix4x4Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D qfm = new QuaternionM4D();
    final QuaternionM4D qaa = new QuaternionM4D();
    final MatrixM4x4D m = new MatrixM4x4D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4D.makeFromAxisAngle(axis, angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4D.makeRotation(angle, axis, m);
      QuaternionM4D.makeFromRotationMatrix4x4(m, qfm);

      final double mag_qfm = QuaternionM4D.magnitude(qfm);
      final double mag_qaa = QuaternionM4D.magnitude(qaa);

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

      if (QuaternionM4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionM4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override @Test public void testMakeMatrix3x3_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Identity()
  {
    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D m = new MatrixM3x3D();

    QuaternionM4D.makeRotationMatrix3x3(q, m);

    Assert.assertTrue(1.0 == m.get(0, 0));
    Assert.assertTrue(0.0 == m.get(0, 1));
    Assert.assertTrue(0.0 == m.get(0, 2));

    Assert.assertTrue(0.0 == m.get(1, 0));
    Assert.assertTrue(1.0 == m.get(1, 1));
    Assert.assertTrue(0.0 == m.get(1, 2));

    Assert.assertTrue(0.0 == m.get(2, 0));
    Assert.assertTrue(0.0 == m.get(2, 1));
    Assert.assertTrue(1.0 == m.get(2, 2));
  }

  @Override @Test public void testMakeMatrix3x3_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix3x3_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Identity()
  {
    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionM4D.makeRotationMatrix4x4(q, m);

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

  @Override @Test public void testMakeMatrix4x4_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMakeMatrix4x4_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D q = new QuaternionM4D();
    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionM4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    QuaternionM4D.makeFromAxisAngle(axis, radians, q);
    QuaternionM4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Override @Test public void testMultiply()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

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

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getXD(),
      0.3535533905932738));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getYD(),
      0.3535533905932738));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getZD(),
      -0.14644660940672624));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getWD(),
      0.8535533905932737));
  }

  @Override @Test public void testMultiplyInPlace()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);

    final QuaternionM4D qx = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4D qy = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionM4D qr = new QuaternionM4D();
    QuaternionM4D.multiplyInPlace(qr, qy);
    QuaternionM4D.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getXD(),
      0.3535533983204287));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getYD(),
      0.3535533983204287));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getZD(),
      -0.14644661713388138));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getWD(),
      0.8535533828661185));
  }

  @Override @Test public void testMultiplyInPlaceOther()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D axis_z = new VectorI3D(0.0, 0.0, 1.0);

    final QuaternionM4D qx = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4D qy = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4D qz = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4D qr = new QuaternionM4D();
    QuaternionM4D.multiplyInPlace(qr, qz);
    QuaternionM4D.multiplyInPlace(qr, qy);
    QuaternionM4D.multiplyInPlace(qr, qx);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getXD(),
      0.1913417153164435));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getYD(),
      0.4619397784426109));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getZD(),
      0.1913417153164436));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getWD(),
      0.8446231923478736));
  }

  @Override @Test public void testMultiplyOther()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D axis_z = new VectorI3D(0.0, 0.0, 1.0);

    final QuaternionM4D qx = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_x, (float) Math.toRadians(45), qx);
    final QuaternionM4D qy = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_y, (float) Math.toRadians(45), qy);
    final QuaternionM4D qz = new QuaternionM4D();
    QuaternionM4D.makeFromAxisAngle(axis_z, (float) Math.toRadians(45), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final QuaternionM4D qr = new QuaternionM4D();
    QuaternionM4D.multiply(qy, qx, qr);
    QuaternionM4D.multiply(qz, qr, qr);

    System.out.println("qx : " + qx);
    System.out.println("qy : " + qy);
    System.out.println("qz : " + qz);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getXD(),
      0.1913417153164435));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getYD(),
      0.4619397784426109));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getZD(),
      0.1913417153164436));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      qr.getWD(),
      0.8446231923478736));
  }

  @Override @Test public void testNegation()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 2) - Math.random();
      final double y = (Math.random() * 2) - Math.random();
      final double z = (Math.random() * 2) - Math.random();
      final double w = (Math.random() * 2) - Math.random();
      final QuaternionM4D qi = new QuaternionM4D(x, y, z, w);
      final QuaternionM4D qn = new QuaternionM4D(-x, -y, -z, -w);
      final QuaternionM4D qr = new QuaternionM4D();

      QuaternionM4D.negate(qi, qr);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionM4D.almostEqual(context, qn, qr));
    }
  }

  @Override @Test public void testNegationCases()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D qi = new QuaternionM4D(1, 2, 3, 4);
    final QuaternionM4D qnx = new QuaternionM4D(-1, 2, 3, 4);
    final QuaternionM4D qny = new QuaternionM4D(-1, -2, 3, 4);
    final QuaternionM4D qnz = new QuaternionM4D(-1, -2, -3, 4);
    final QuaternionM4D qnw = new QuaternionM4D(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qnw) == true);

    QuaternionM4D.negateInPlace(qnw);
    Assert.assertTrue(QuaternionM4D.isNegationOf(context, qi, qnw) == false);
    Assert.assertTrue(QuaternionM4D.almostEqual(context, qi, qnw));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final QuaternionM4D v0 = new QuaternionM4D(8.0, 0.0, 0.0, 0.0);
    final QuaternionM4D out = new QuaternionM4D();
    final QuaternionM4D vr = QuaternionM4D.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = QuaternionM4D.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final QuaternionM4D qr = new QuaternionM4D();
    final QuaternionM4D q = new QuaternionM4D(0, 0, 0, 0);
    QuaternionM4D.normalize(q, qr);

    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.z));
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, 0, qr.w));
  }

  @Override @Test public void testScaleMutation()
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

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();

      QuaternionM4D.scale(v, 1.0, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getXD(),
        vr.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getYD(),
        vr.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getZD(),
        vr.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        v.getWD(),
        vr.getWD()));

      {
        final double orig_x = v.getXD();
        final double orig_y = v.getYD();
        final double orig_z = v.getZD();
        final double orig_w = v.getWD();

        QuaternionM4D.scaleInPlace(v, 1.0);

        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), orig_x));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), orig_y));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), orig_z));
        Assert
          .assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), orig_w));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionM4D v = new QuaternionM4D(x, y, z, w);

      final QuaternionM4D vr = new QuaternionM4D();

      QuaternionM4D.scale(v, 0.0, vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getXD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getYD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getZD(), 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, vr.getWD(), 0.0));

      {
        QuaternionM4D.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getXD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getYD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getZD(), 0.0));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v.getWD(), 0.0));
      }
    }
  }

  @Override @Test public void testString()
  {
    final QuaternionM4D v = new QuaternionM4D(1.0, 2.0, 3.0, 4.0);
    Assert.assertTrue(v.toString().equals("[QuaternionM4D 1.0 2.0 3.0 4.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
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

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getZD(),
        v0.getZD() - v1.getZD()));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        vr0.getWD(),
        v0.getWD() - v1.getWD()));

      {
        final double orig_x = v0.getXD();
        final double orig_y = v0.getYD();
        final double orig_z = v0.getZD();
        final double orig_w = v0.getWD();
        QuaternionM4D.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getXD(),
          orig_x - v1.getXD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getYD(),
          orig_y - v1.getYD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getZD(),
          orig_z - v1.getZD()));
        Assert.assertTrue(AlmostEqualDouble.almostEqual(
          ec,
          v0.getWD(),
          orig_w - v1.getWD()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
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
