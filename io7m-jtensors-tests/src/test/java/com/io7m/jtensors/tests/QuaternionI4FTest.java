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
import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.QuaternionI4F;
import com.io7m.jtensors.QuaternionReadable4FType;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuaternionI4FTest extends QuaternionI4Contract
{
  private static final Logger LOG;
  private static final VectorReadable3FType AXIS_X = new VectorI3F(
    1.0F, 0.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(
    0.0F, 1.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(
    0.0F, 0.0F, 1.0F);

  static {
    LOG = LoggerFactory.getLogger(QuaternionI4FTest.class);
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  @Override
  @Test
  public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

      final QuaternionI4F vr = QuaternionI4F.add(v0, v1);

      Assert.assertEquals(
        v0.getXF() + v1.getXF(),
        vr.getXF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getYF() + v1.getYF(),
        vr.getYF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getZF() + v1.getZF(),
        vr.getZF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getWF() + v1.getWF(),
        vr.getWF(),
        Eq.DELTA_F_SMALL);
    }
  }

  @Override
  @Test
  public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, y, z, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(x, q, z, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(x, y, q, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(x, y, z, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, q, z, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, y, q, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, y, z, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, q, q, w);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, q, z, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(q, q, q, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(x, q, q, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }

    {
      final QuaternionReadable4FType m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionReadable4FType m1 = new QuaternionI4F(x, y, q, q);
      Assert.assertFalse(QuaternionI4F.almostEqual(ec, m0, m1));
    }
  }

  @Override
  @Test
  public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionReadable4FType v0 = new QuaternionI4F(x0, y0, z0, w0);
      final QuaternionReadable4FType v1 = new QuaternionI4F(x0, y0, z0, w0);
      final QuaternionReadable4FType v2 = new QuaternionI4F(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionI4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionI4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionI4F.almostEqual(ec, v0, v2));
    }
  }

  @Override
  @Test
  public final void testCheckInterface()
  {
    final VectorReadable4FType v = new QuaternionI4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertEquals(v.getXF(), v.getXF(), 0.0);
    Assert.assertEquals(v.getYF(), v.getYF(), 0.0);
    Assert.assertEquals(v.getZF(), v.getZF(), 0.0);
    Assert.assertEquals(v.getWF(), v.getWF(), 0.0);
  }

  @Override
  @Test
  public final void testConjugate()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionReadable4FType e = new QuaternionI4F(
      -1.0f,
      -2.0f,
      -3.0f,
      4.0f);
    final QuaternionReadable4FType q = new QuaternionI4F(
      1.0f,
      2.0f,
      3.0f,
      4.0f);
    final QuaternionI4F r = QuaternionI4F.conjugate(q);
    final boolean t = QuaternionI4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
  }

  @Override
  @Test
  public final void testConjugateInvertible()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((getRandom() * 200.0) - 100.0);
      final float y = (float) ((getRandom() * 200.0) - 100.0);
      final float z = (float) ((getRandom() * 200.0) - 100.0);
      final float w = (float) ((getRandom() * 200.0) - 100.0);

      final QuaternionI4F q = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F qc0 = QuaternionI4F.conjugate(q);
      final QuaternionI4F qc1 = QuaternionI4F.conjugate(qc0);

      Assert.assertEquals(qc1.getXF(), q.getXF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(qc1.getYF(), q.getYF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(qc1.getZF(), q.getZF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(qc1.getWF(), q.getWF(), Eq.DELTA_F_SMALL);
    }
  }

  @Override
  @Test
  public final void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final QuaternionReadable4FType v = new QuaternionI4F();
    QuaternionI4F.almostEqual(context, v, QuaternionI4F.IDENTITY);
  }

  @Override
  @Test
  public final void testDotProduct()
  {
    final QuaternionI4F v0 = new QuaternionI4F(10.0f, 10.0f, 10.0f, 10.0f);
    final QuaternionI4F v1 = new QuaternionI4F(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = (double) QuaternionI4F.dotProduct(v0, v1);
      Assert.assertEquals(10.0f, v0.getXF(), 0.0);
      Assert.assertEquals(10.0f, v0.getYF(), 0.0);
      Assert.assertEquals(10.0f, v0.getZF(), 0.0);
      Assert.assertEquals(10.0f, v0.getWF(), 0.0);
      Assert.assertEquals(10.0f, v1.getXF(), 0.0);
      Assert.assertEquals(10.0f, v1.getYF(), 0.0);
      Assert.assertEquals(10.0f, v1.getZF(), 0.0);
      Assert.assertEquals(10.0f, v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = (double) QuaternionI4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0f, v0.getXF(), 0.0);
      Assert.assertEquals(10.0f, v0.getYF(), 0.0);
      Assert.assertEquals(10.0f, v0.getZF(), 0.0);
      Assert.assertEquals(10.0f, v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = (double) QuaternionI4F.dotProduct(v1, v1);
      Assert.assertEquals(10.0f, v1.getXF(), 0.0);
      Assert.assertEquals(10.0f, v1.getYF(), 0.0);
      Assert.assertEquals(10.0f, v1.getZF(), 0.0);
      Assert.assertEquals(10.0f, v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Override
  @Test
  public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final float z = (float) getRandom();
      final float w = (float) getRandom();
      final QuaternionI4F q =
        QuaternionI4F.normalize(new QuaternionI4F(x, y, z, w));
      final double dp = (double) QuaternionI4F.dotProduct(q, q);

      Assert.assertEquals(dp, 1.0, 0.00001);
    }
  }

  @Override
  @Test
  public final void testDotProductSelfMagnitudeSquared()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final float z = (float) getRandom();
      final float w = (float) getRandom();
      final QuaternionReadable4FType q = new QuaternionI4F(x, y, z, w);

      final double ms = (double) QuaternionI4F.magnitudeSquared(q);
      final double dp = (double) QuaternionI4F.dotProduct(q, q);

      Assert.assertEquals(dp, ms, Eq.DELTA_D_SMALL);
    }
  }

  @Override
  @Test
  public final void testEqualsCorrect()
  {
    final QuaternionI4F v0 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F v1 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F vw = new QuaternionI4F(0.0f, 0.0f, 0.0f, 1.0f);
    final QuaternionI4F vz = new QuaternionI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final QuaternionI4F vy = new QuaternionI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final QuaternionI4F vx = new QuaternionI4F(1.0f, 0.0f, 0.0f, 0.0f);

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
  public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F m1 = new QuaternionI4F(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override
  @Test
  public final void testHashCodeEqualsCorrect()
  {
    final QuaternionI4F m0 = new QuaternionI4F();
    final QuaternionI4F m1 = new QuaternionI4F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Override
  @Test
  public final void testHashCodeNotEqualCorrect()
  {
    {
      final QuaternionI4F m0 = new QuaternionI4F(23.0F, 0.0F, 0.0F, 1.0F);
      final QuaternionI4F m1 = new QuaternionI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(0.0F, 23.0F, 0.0F, 1.0F);
      final QuaternionI4F m1 = new QuaternionI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(0.0F, 0.0F, 23.0F, 1.0F);
      final QuaternionI4F m1 = new QuaternionI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final QuaternionI4F m0 = new QuaternionI4F(0.0F, 0.0F, 0.0F, 23.0F);
      final QuaternionI4F m1 = new QuaternionI4F();
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override
  @Test
  public final void testInitializeReadable()
  {
    final QuaternionI4F v0 = new QuaternionI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final VectorReadable4FType v1 = new QuaternionI4F(v0);

    Assert.assertEquals(v1.getXF(), v0.getXF(), 0.0);
    Assert.assertEquals(v1.getYF(), v0.getYF(), 0.0);
    Assert.assertEquals(v1.getZF(), v0.getZF(), 0.0);
    Assert.assertEquals(v1.getWF(), v0.getWF(), 0.0);
  }

  @Override
  @Test
  public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionReadable4FType v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionReadable4FType v1 = new QuaternionI4F(x1, y1, z1, w1);

      Assert.assertTrue(
        QuaternionI4F.almostEqual(
          context, QuaternionI4F.interpolateLinear(v0, v1, 0.0f), v0));
      Assert.assertTrue(
        QuaternionI4F.almostEqual(
          context, QuaternionI4F.interpolateLinear(v0, v1, 1.0f), v1));
    }
  }

  @Override
  @Test
  public final void testInterpolateSphericalLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext4dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * 10000.0);
      final float y0 = (float) (getRandom() * 10000.0);
      final float z0 = (float) (getRandom() * 10000.0);
      final float w0 = (float) (getRandom() * 10000.0);
      final QuaternionReadable4FType v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * 10000.0);
      final float y1 = (float) (getRandom() * 10000.0);
      final float z1 = (float) (getRandom() * 10000.0);
      final float w1 = (float) (getRandom() * 10000.0);
      final QuaternionReadable4FType v1 = new QuaternionI4F(x1, y1, z1, w1);

      final QuaternionI4F r0 =
        QuaternionI4F.interpolateSphericalLinear(v0, v1, 0.0f);
      final QuaternionI4F r1 =
        QuaternionI4F.interpolateSphericalLinear(v0, v1, 1.0f);

      final QuaternionI4F v0n = QuaternionI4F.normalize(v0);
      final QuaternionI4F v1n = QuaternionI4F.normalize(v1);

      LOG.debug("v0:  {}", v0);
      LOG.debug("v1:  {}", v1);
      LOG.debug("v0n: {}", v0n);
      LOG.debug("v1n: {}", v1n);
      LOG.debug("r0:  {}", r0);
      LOG.debug("r1:  {}", r1);
      LOG.debug("--");

      Assert.assertTrue(QuaternionI4F.almostEqual(context, r0, v0n));
      Assert.assertTrue(QuaternionI4F.almostEqual(context, r1, v1n));
    }
  }

  @Override
  @Test
  public final void testInterpolateSphericalLinearNegated()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis0 =
      VectorI3F.normalize(new VectorI3F(0.0f, 1.0f, 0.0f));

    final QuaternionReadable4FType v0 =
      QuaternionI4F.makeFromAxisAngle(axis0, 0.0);
    final QuaternionReadable4FType v1 =
      QuaternionI4F.makeFromAxisAngle(axis0, Math.toRadians(181.0));

    Assert.assertTrue(QuaternionI4F.dotProduct(v0, v1) < 0.0);

    final QuaternionI4F r0 =
      QuaternionI4F.interpolateSphericalLinear(v0, v1, 0.0f);
    final QuaternionI4F r1 =
      QuaternionI4F.interpolateSphericalLinear(v0, v1, 1.0f);

    LOG.debug("spherical {} {} {} -> {}", v0, v1, Double.valueOf(0.0), r0);
    LOG.debug("spherical {} {} {} -> {}", v0, v1, Double.valueOf(1.0), r1);

    Assert.assertTrue(
      QuaternionI4F.almostEqual(context, r0, v0));
    Assert.assertTrue(
      QuaternionI4F.almostEqual(context, r1, QuaternionI4F.negate(v1)));
  }

  @Override
  @Test
  public final void testInterpolateSphericalLinearCodirectional()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * 10000.0);
      final float y0 = (float) (getRandom() * 10000.0);
      final float z0 = (float) (getRandom() * 10000.0);
      final float w0 = (float) (getRandom() * 10000.0);
      final QuaternionReadable4FType v0 =
        QuaternionI4F.normalize(new QuaternionI4F(x0, y0, z0, w0));
      final QuaternionI4F v1 =
        QuaternionI4F.normalize(new QuaternionI4F(v0));

      final float alpha = (float) getRandom();

      final QuaternionI4F r0 =
        QuaternionI4F.interpolateSphericalLinear(v0, v1, alpha);
      final QuaternionI4F r1 =
        QuaternionI4F.interpolateLinear(v0, v1, alpha);

      LOG.debug("spherical {} {} {} -> {}", v0, v0, Float.valueOf(alpha), r0);
      LOG.debug("linear    {} {} {} -> {}", v0, v0, Float.valueOf(alpha), r1);

      Assert.assertTrue(
        QuaternionI4F.almostEqual(context, r0, v0));
      Assert.assertTrue(
        QuaternionI4F.almostEqual(context, r1, r1));
    }
  }

  @Override
  @Test
  public final void testLookAtConsistent_Origin_NegativeX()
  {
    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final QuaternionI4F.ContextQI4F qc = new QuaternionI4F.ContextQI4F();

    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mqr = MatrixHeapArrayM4x4F.newMatrix();

    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(-1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM4x4F.lookAt(mc, origin, target, axis, mr);
    final QuaternionI4F q =
      QuaternionI4F.lookAtWithContext(qc, origin, target, axis);
    QuaternionI4F.makeRotationMatrix4x4(q, mqr);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testLookAtConsistent_Origin_PositiveX()
  {
    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final QuaternionI4F.ContextQI4F qc = new QuaternionI4F.ContextQI4F();

    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mqr = MatrixHeapArrayM4x4F.newMatrix();

    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM4x4F.lookAt(mc, origin, target, axis, mr);
    final QuaternionI4F q =
      QuaternionI4F.lookAtWithContext(qc, origin, target, axis);
    QuaternionI4F.makeRotationMatrix4x4(q, mqr);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testLookAtMatrixEquivalentAxisY()
  {
    final QuaternionI4F.ContextQI4F qc = new QuaternionI4F.ContextQI4F();
    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();

    final Matrix4x4FType ml = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float origin_x =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));
      final float origin_y =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));
      final float origin_z =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));

      final float target_x =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));
      final float target_y =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));
      final float target_z =
        (float) ((getRandom() * 100.0) - (getRandom() * 100.0));

      final VectorReadable3FType origin = new VectorI3F(
        origin_x,
        origin_y,
        origin_z);
      final VectorReadable3FType target = new VectorI3F(
        target_x,
        target_y,
        target_z);

      MatrixM4x4F.lookAt(
        mc, origin, target, AXIS_Y, ml);
      final QuaternionI4F lq = QuaternionI4F.lookAtWithContext(
        qc, origin, target, AXIS_Y);
      QuaternionI4F.makeRotationMatrix4x4(lq, mq);

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final float x = ml.getRowColumnF(row, col);
          final float y = mq.getRowColumnF(row, col);
          Assert.assertEquals(y, x, 0.000001);
        }
      }
    }
  }

  @Override
  @Test
  public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (1.0 + (getRandom() * (double) Float.MAX_VALUE));
      final float y =
        (float) (1.0 + (getRandom() * (double) Float.MAX_VALUE));
      final float z =
        (float) (1.0 + (getRandom() * (double) Float.MAX_VALUE));
      final float w =
        (float) (1.0 + (getRandom() * (double) Float.MAX_VALUE));
      final QuaternionReadable4FType v = new QuaternionI4F(x, y, z, w);

      final double m = (double) QuaternionI4F.magnitude(v);
      Assert.assertTrue(m > 0.0);
    }
  }

  @Override
  @Test
  public final void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x = (float) (1.0 + (getRandom() * (double) max));
      final float y = (float) (1.0 + (getRandom() * (double) max));
      final float z = (float) (1.0 + (getRandom() * (double) max));
      final float w = (float) (1.0 + (getRandom() * (double) max));
      final QuaternionReadable4FType v = new QuaternionI4F(x, y, z, w);

      final QuaternionI4F vr = QuaternionI4F.normalize(v);
      Assert.assertNotSame(v, vr);

      final double m = (double) QuaternionI4F.magnitude(vr);
      Assert.assertEquals(1.0, m, 0.00001);
    }
  }

  @Override
  @Test
  public final void testMagnitudeNormalizeZero()
  {
    final QuaternionReadable4FType v = new QuaternionI4F(
      0.0f,
      0.0f,
      0.0f,
      0.0f);
    final QuaternionI4F vr = QuaternionI4F.normalize(v);
    final double m = (double) QuaternionI4F.magnitude(vr);
    Assert.assertEquals(0.0, m, Eq.DELTA_D_SMALL);
  }

  @Override
  @Test
  public final void testMagnitudeOne()
  {
    final QuaternionReadable4FType v = new QuaternionI4F(
      1.0f,
      0.0f,
      0.0f,
      0.0f);
    final double m = (double) QuaternionI4F.magnitude(v);
    Assert.assertEquals(1.0, m, Eq.DELTA_D_SMALL);
  }

  @Override
  @Test
  public final void testMagnitudeSimple()
  {
    final QuaternionReadable4FType v = new QuaternionI4F(
      8.0f,
      0.0f,
      0.0f,
      0.0f);

    {
      final double p = (double) QuaternionI4F.dotProduct(v, v);
      final double q = (double) QuaternionI4F.magnitudeSquared(v);
      final double r = (double) QuaternionI4F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Override
  @Test
  public final void testMagnitudeZero()
  {
    final QuaternionReadable4FType v = new QuaternionI4F(
      0.0f,
      0.0f,
      0.0f,
      0.0f);
    final double m = (double) QuaternionI4F.magnitude(v);
    Assert.assertEquals(0.0, m, Eq.DELTA_D_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorReadable3FType axis_r = new VectorI3F(
        (float) getRandom(),
        (float) getRandom(),
        (float) getRandom());
      final VectorI3F axis_n = VectorI3F.normalize(axis_r);

      final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
        axis_n, (double) (float) Math.toRadians(getRandom() * 360.0));

      Assert.assertEquals(1.0f, QuaternionI4F.magnitude(q), 0.00001);
    }
  }

  @Override
  @Test
  public final void testMakeAxisAngleX_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable3FType axis = AXIS_X;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(45.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.3826834323650898f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.9238795325112867f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleX_90()
  {
    final VectorReadable3FType axis = AXIS_X;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(90.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.7071067811865475f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.7071067811865475f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleY_45()
  {
    final VectorReadable3FType axis = AXIS_Y;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(45.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.0f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.3826834323650898f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.9238795325112867f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleY_90()
  {
    final VectorReadable3FType axis = AXIS_Y;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(90.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.0f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.7071067811865475f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.7071067811865475f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleZ_45()
  {
    final VectorReadable3FType axis = AXIS_Z;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(45.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.0f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.3826834323650898f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.9238795325112867f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeAxisAngleZ_90()
  {
    final VectorReadable3FType axis = AXIS_Z;
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(
      axis, (double) (float) Math.toRadians(90.0));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.0f, q.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.0f, q.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.7071067811865475f, q.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.7071067811865475f, q.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testMakeFromMatrix3x3Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final Matrix3x3FType m = MatrixHeapArrayM3x3F.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2.0 * getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final float axis_x = (float) getRandom();
      final float axis_y = (float) getRandom();
      final float axis_z = (float) getRandom();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /*
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4F qaa = QuaternionI4F.makeFromAxisAngle(axis, angle);

      /*
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3F.makeRotation(angle, axis, m);
      final QuaternionI4F qfm = QuaternionI4F.makeFromRotationMatrix3x3(m);

      final double mag_qfm = (double) QuaternionI4F.magnitude(qfm);
      final double mag_qaa = (double) QuaternionI4F.magnitude(qaa);

      /*
       * The resulting quaternions are unit quaternions.
       */

      Assert.assertEquals(1.0, mag_qfm, 0.00001);
      Assert.assertEquals(1.0, mag_qaa, 0.00001);

      /*
       * The resulting quaternions match.
       */

      if (QuaternionI4F.almostEqual(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /*
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4F.isNegationOf(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override
  @Test
  public final void testMakeFromMatrix4x4Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final Matrix4x4FType m = MatrixHeapArrayM4x4F.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2.0 * getRandom() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final float axis_x = (float) getRandom();
      final float axis_y = (float) getRandom();
      final float axis_z = (float) getRandom();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /*
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4F qaa = QuaternionI4F.makeFromAxisAngle(axis, angle);

      /*
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4F.makeRotation(angle, axis, m);
      final QuaternionI4F qfm = QuaternionI4F.makeFromRotationMatrix4x4(m);

      final double mag_qfm = (double) QuaternionI4F.magnitude(qfm);
      final double mag_qaa = (double) QuaternionI4F.magnitude(qaa);

      /*
       * The resulting quaternions are unit quaternions.
       */

      Assert.assertEquals(1.0, mag_qfm, 0.00001);
      Assert.assertEquals(1.0, mag_qaa, 0.00001);

      /*
       * The resulting quaternions match.
       */

      if (QuaternionI4F.almostEqual(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /*
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4F.isNegationOf(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_45X()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_45Y()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_45Z()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_Identity()
  {
    final QuaternionReadable4FType q = new QuaternionI4F();
    final Matrix3x3FType m = MatrixHeapArrayM3x3F.newMatrix();

    QuaternionI4F.makeRotationMatrix3x3(q, m);

    Assert.assertEquals((double) m.getRowColumnF(0, 0), 1.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(0, 1), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(0, 2), 0.0, 0.0);

    Assert.assertEquals((double) m.getRowColumnF(1, 0), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(1, 1), 1.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(1, 2), 0.0, 0.0);

    Assert.assertEquals((double) m.getRowColumnF(2, 0), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(2, 1), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(2, 2), 1.0, 0.0);
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_Minus45X()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_Minus45Y()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix3x3_Minus45Z()
  {
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_45X()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_45Y()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_45Z()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_Identity()
  {
    final QuaternionReadable4FType q = new QuaternionI4F();
    final Matrix4x4FType m = MatrixHeapArrayM4x4F.newMatrix();

    QuaternionI4F.makeRotationMatrix4x4(q, m);

    Assert.assertEquals((double) m.getRowColumnF(0, 0), 1.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(0, 1), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(0, 2), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(0, 3), 0.0, 0.0);

    Assert.assertEquals((double) m.getRowColumnF(1, 0), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(1, 1), 1.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(1, 2), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(1, 3), 0.0, 0.0);

    Assert.assertEquals((double) m.getRowColumnF(2, 0), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(2, 1), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(2, 2), 1.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(2, 3), 0.0, 0.0);

    Assert.assertEquals((double) m.getRowColumnF(3, 0), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(3, 1), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(3, 2), 0.0, 0.0);
    Assert.assertEquals((double) m.getRowColumnF(3, 3), 1.0, 0.0);
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_Minus45X()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_Minus45Y()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMakeMatrix4x4_Minus45Z()
  {
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        Assert.assertEquals(y, x, Eq.DELTA_F_SMALL);
      }
    }
  }

  @Override
  @Test
  public final void testMultiply()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorReadable3FType axis_x = AXIS_X;
    final VectorReadable3FType axis_y = AXIS_Y;
    final QuaternionI4F qx = QuaternionI4F.makeFromAxisAngle(
      axis_x, (double) (float) Math.toRadians(45.0));
    final QuaternionI4F qy = QuaternionI4F.makeFromAxisAngle(
      axis_y, (double) (float) Math.toRadians(45.0));

    /*
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionI4F qr = QuaternionI4F.multiply(qy, qx);

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertEquals(0.3535533905932738f, qr.getXF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.3535533905932738f, qr.getYF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(-0.14644660940672624f, qr.getZF(), Eq.DELTA_F_SMALL);
    Assert.assertEquals(0.8535533905932737f, qr.getWF(), Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testNegation()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (getRandom() * 2.0) - getRandom();
      final double y = (getRandom() * 2.0) - getRandom();
      final double z = (getRandom() * 2.0) - getRandom();
      final double w = (getRandom() * 2.0) - getRandom();
      final QuaternionReadable4FType qi =
        new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
      final QuaternionReadable4FType qn =
        new QuaternionI4F((float) -x, (float) -y, (float) -z, (float) -w);
      final QuaternionI4F qr = QuaternionI4F.negate(qi);

      Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionI4F.almostEqual(context, qn, qr));
    }
  }

  @Override
  @Test
  public final void testNegationCases()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionReadable4FType qi = new QuaternionI4F(
      1.0F,
      2.0F,
      3.0F,
      4.0F);
    final QuaternionReadable4FType qnx = new QuaternionI4F(
      -1.0F,
      2.0F,
      3.0F,
      4.0F);
    final QuaternionReadable4FType qny = new QuaternionI4F(
      -1.0F,
      -2.0F,
      3.0F,
      4.0F);
    final QuaternionReadable4FType qnz = new QuaternionI4F(
      -1.0F,
      -2.0F,
      -3.0F,
      4.0F);
    final QuaternionReadable4FType qnw = new QuaternionI4F(
      -1.0F,
      -2.0F,
      -3.0F,
      -4.0F);

    Assert.assertFalse(QuaternionI4F.isNegationOf(context, qi, qi));
    Assert.assertFalse(QuaternionI4F.isNegationOf(context, qi, qnx));
    Assert.assertFalse(QuaternionI4F.isNegationOf(context, qi, qny));
    Assert.assertFalse(QuaternionI4F.isNegationOf(context, qi, qnz));
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qnw));

    final QuaternionI4F qnwn = QuaternionI4F.negate(qnw);
    Assert.assertFalse(QuaternionI4F.isNegationOf(context, qi, qnwn));
    Assert.assertTrue(QuaternionI4F.almostEqual(context, qi, qnwn));
  }

  @Override
  @Test
  public final void testNormalizeSimple()
  {
    final QuaternionReadable4FType v0 = new QuaternionI4F(
      8.0f,
      0.0f,
      0.0f,
      0.0f);
    final QuaternionI4F vr = QuaternionI4F.normalize(v0);
    final float m = QuaternionI4F.magnitude(vr);
    Assert.assertEquals(1.0f, m, 0.0);
  }

  @Override
  @Test
  public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final QuaternionReadable4FType q = new QuaternionI4F(
      0.0F,
      0.0F,
      0.0F,
      0.0F);
    final QuaternionI4F qr = QuaternionI4F.normalize(q);

    Assert.assertEquals(qr.getXF(), 0.0F, Eq.DELTA_F_SMALL);
    Assert.assertEquals(qr.getYF(), 0.0F, Eq.DELTA_F_SMALL);
    Assert.assertEquals(qr.getZF(), 0.0F, Eq.DELTA_F_SMALL);
    Assert.assertEquals(qr.getWF(), 0.0F, Eq.DELTA_F_SMALL);
  }

  @Override
  @Test
  public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionI4F v = new QuaternionI4F(x, y, z, w);

      final QuaternionI4F vr = QuaternionI4F.scale(v, 1.0f);

      Assert.assertEquals(vr.getXF(), v.getXF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(vr.getYF(), v.getYF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(vr.getZF(), v.getZF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(vr.getWF(), v.getWF(), Eq.DELTA_F_SMALL);
    }
  }

  @Override
  @Test
  public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionReadable4FType v = new QuaternionI4F(x, y, z, w);

      final QuaternionI4F vr = QuaternionI4F.scale(v, 0.0f);

      Assert.assertEquals(0.0f, vr.getXF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(0.0f, vr.getYF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(0.0f, vr.getZF(), Eq.DELTA_F_SMALL);
      Assert.assertEquals(0.0f, vr.getWF(), Eq.DELTA_F_SMALL);
    }
  }

  @Override
  @Test
  public final void testString()
  {
    final QuaternionI4F v = new QuaternionI4F(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue("[QuaternionI4F 0.0 1.0 2.0 3.0]".equals(v.toString()));
  }

  @Override
  @Test
  public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (getRandom() * (double) Float.MAX_VALUE);
      final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

      final QuaternionI4F vr = QuaternionI4F.subtract(v0, v1);

      Assert.assertEquals(
        v0.getXF() - v1.getXF(),
        vr.getXF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getYF() - v1.getYF(),
        vr.getYF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getZF() - v1.getZF(),
        vr.getZF(),
        Eq.DELTA_F_SMALL);
      Assert.assertEquals(
        v0.getWF() - v1.getWF(),
        vr.getWF(),
        Eq.DELTA_F_SMALL);
    }
  }
}
