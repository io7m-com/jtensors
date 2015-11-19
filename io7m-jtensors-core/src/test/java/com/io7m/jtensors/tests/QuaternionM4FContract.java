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
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.Quaternion4FType;
import com.io7m.jtensors.QuaternionM4D;
import com.io7m.jtensors.QuaternionM4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class QuaternionM4FContract<T extends Quaternion4FType>
{
  private static final VectorReadable3FType AXIS_X = new VectorI3F(
    1.0F, 0.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Y = new VectorI3F(
    0.0F, 1.0F, 0.0F);
  private static final VectorReadable3FType AXIS_Z = new VectorI3F(
    0.0F, 0.0F, 1.0F);

  protected abstract T newQuaternion();

  protected abstract T newQuaternion(
    final float x,
    final float y,
    final float z,
    final float w);

  protected abstract T newQuaternion(final T v);

  @Test public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      QuaternionM4F.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getYF(), v0.getYF() + v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getZF(), v0.getZF() + v1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getWF(), v0.getWF() + v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getXF(), orig_x + v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getYF(), orig_y + v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getZF(), orig_z + v1.getZF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getWF(), orig_w + v1.getWF()));
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0f, 1.0f, 1.0f, 1.0f);
    final T v1 = this.newQuaternion(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov0 = QuaternionM4F.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov1 = QuaternionM4F.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);
  }

  @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) this.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, y, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, w);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, z, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(q, q, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, q, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newQuaternion(x, y, z, w);
      final T m1 = this.newQuaternion(x, y, q, q);
      Assert.assertFalse(QuaternionM4F.almostEqual(ec, m0, m1));
    }
  }

  @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newQuaternion(x0, y0, z0, w0);
      final T v1 = this.newQuaternion(x0, y0, z0, w0);
      final T v2 = this.newQuaternion(x0, y0, z0, w0);

      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v0, v1));
      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v1, v2));
      Assert.assertTrue(QuaternionM4F.almostEqual(ec, v0, v2));
    }
  }

  @Test public final void testCheckInterface()
  {
    final T v = this.newQuaternion(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
    Assert.assertEquals((double) v.getZF(), (double) v.getZF(), 0.0);
    Assert.assertEquals((double) v.getWF(), (double) v.getWF(), 0.0);
  }

  @Test public final void testConjugate()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final T e = this.newQuaternion(-1.0f, -2.0f, -3.0f, 4.0f);
    final T q = this.newQuaternion(1.0f, 2.0f, 3.0f, 4.0f);
    final T r = this.newQuaternion();
    final T u = QuaternionM4F.conjugate(q, r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Test public final void testConjugateInPlace()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final T e = this.newQuaternion(-1.0f, -2.0f, -3.0f, 4.0f);
    final T q = this.newQuaternion(1.0f, 2.0f, 3.0f, 4.0f);
    final T r = this.newQuaternion(q);
    final T u = QuaternionM4F.conjugateInPlace(r);
    final boolean t = QuaternionM4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
    Assert.assertSame(r, u);
  }

  @Test public final void testConjugateInvertible()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((this.getRandom() * 200.0) - 100.0);
      final float y = (float) ((this.getRandom() * 200.0) - 100.0);
      final float z = (float) ((this.getRandom() * 200.0) - 100.0);
      final float w = (float) ((this.getRandom() * 200.0) - 100.0);

      final T q = this.newQuaternion(x, y, z, w);
      final T qc0 = this.newQuaternion();
      final T qc1 = this.newQuaternion();
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

  @Test public final void testCopy()
  {
    final T vb = this.newQuaternion(5.0F, 6.0F, 7.0F, 8.0F);
    final T va = this.newQuaternion(1.0F, 2.0F, 3.0F, 4.0F);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());
    Assert.assertFalse(va.getZF() == vb.getZF());
    Assert.assertFalse(va.getWF() == vb.getWF());

    QuaternionM4F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
    Assert.assertEquals((double) vb.getZF(), (double) va.getZF(), 0.0);
    Assert.assertEquals((double) vb.getWF(), (double) va.getWF(), 0.0);
  }

  @Test public final void testCopy2Correct()
  {
    final T v0 = this.newQuaternion(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(0.0F, v1.getZF(), 0.0f);
    Assert.assertEquals(1.0F, v1.getWF(), 0.0f);
  }

  @Test public final void testCopy3Correct()
  {
    final T v0 = this.newQuaternion(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom4F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(v0.getWF(), v1.getWF(), 0.0f);
  }

  @Test public final void testCopy4Correct()
  {
    final T v0 = this.newQuaternion(
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE,
      (float) this.getRandom() * Float.MAX_VALUE);
    final T v1 = this.newQuaternion();

    v1.copyFrom3F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
    Assert.assertEquals(v0.getZF(), v1.getZF(), 0.0f);
    Assert.assertEquals(1.0f, v1.getWF(), 0.0f);
  }

  @Test public final void testDefault0001()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    Assert.assertTrue(
      QuaternionM4F.almostEqual(
        ec, this.newQuaternion(), this.newQuaternion(0.0F, 0.0F, 0.0F, 1.0F)));
  }

  @Test public final void testDotProduct()
  {
    final T v0 = this.newQuaternion(10.0f, 10.0f, 10.0f, 10.0f);
    final T v1 = this.newQuaternion(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionM4F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) this.getRandom();
      final float y = (float) this.getRandom();
      final float z = (float) this.getRandom();
      final float w = (float) this.getRandom();
      final QuaternionM4D q = new QuaternionM4D(
        (double) x, (double) y, (double) z, (double) w);
      final double dp = QuaternionM4D.dotProduct(q, q);

      
      

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newQuaternion(10.0f, 10.0f, 10.0f, 10.0f);

    {
      final double p = QuaternionM4F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }

    {
      final double p = QuaternionM4F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getZF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getWF(), 0.0);
      Assert.assertEquals(400.0, p, 0.0);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newQuaternion();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newQuaternion();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newQuaternion();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setXF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setYF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setZF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setWF(23.0f);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final QuaternionM4D m0 = new QuaternionM4D();
    final QuaternionM4D m1 = new QuaternionM4D();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setXF(23.0F);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setYF(23.0F);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setZF(23.0F);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final T m0 = this.newQuaternion();
      final T m1 = this.newQuaternion();
      m1.setWF(23.0F);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final T v0 = this.newQuaternion(1.0f, 2.0f, 3.0f, 4.0f);
    final T v1 = this.newQuaternion(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
    Assert.assertEquals((double) v1.getZF(), (double) v0.getZF(), 0.0);
    Assert.assertEquals((double) v1.getWF(), (double) v0.getWF(), 0.0);
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();
    final QuaternionM4F.ContextQM4F c = new QuaternionM4F.ContextQM4F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      final T vr1 = this.newQuaternion();
      QuaternionM4F.interpolateLinear(c, v0, v1, 0.0, vr0);
      QuaternionM4F.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v0.getXF(), vr0.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v0.getYF(), vr0.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v0.getZF(), vr0.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v0.getWF(), vr0.getWF()));

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v1.getXF(), vr1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v1.getYF(), vr1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v1.getZF(), vr1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v1.getWF(), vr1.getWF()));
    }
  }

  protected double getRandom()
  {
    return Math.random();
  }

  @Test public final void testLookAtConsistent_Origin_NegativeX()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final QuaternionM4F.ContextQM4F qc = new QuaternionM4F.ContextQM4F();

    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mqr = MatrixHeapArrayM4x4F.newMatrix();
    final T qr = this.newQuaternion();

    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(-1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM4x4F.lookAt(mc, origin, target, axis, mr);
    QuaternionM4F.lookAt(qc, origin, target, axis, qr);
    QuaternionM4F.makeRotationMatrix4x4(qr, mqr);

    
    
    
    

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testLookAtConsistent_Origin_PositiveX()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();
    final QuaternionM4F.ContextQM4F qc = new QuaternionM4F.ContextQM4F();

    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mqr = MatrixHeapArrayM4x4F.newMatrix();
    final T qr = this.newQuaternion();

    final VectorReadable3FType origin = new VectorI3F(0.0F, 0.0F, 0.0F);
    final VectorReadable3FType target = new VectorI3F(1.0F, 0.0F, 0.0F);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM4x4F.lookAt(mc, origin, target, axis, mr);
    QuaternionM4F.lookAt(qc, origin, target, axis, qr);
    QuaternionM4F.makeRotationMatrix4x4(qr, mqr);

    
    
    
    

    boolean eq = false;

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mqr.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(ec, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testLookAtMatrixEquivalentAxisY()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final QuaternionM4F.ContextQM4F qc = new QuaternionM4F.ContextQM4F();
    final MatrixM4x4F.ContextMM4F mc = new MatrixM4x4F.ContextMM4F();

    final Matrix4x4FType ml = MatrixHeapArrayM4x4F.newMatrix();
    final T lq = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float origin_x =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));
      final float origin_y =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));
      final float origin_z =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));

      final float target_x =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));
      final float target_y =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));
      final float target_z =
        (float) ((this.getRandom() * 100.0) - (this.getRandom() * 100.0));

      final VectorI3F origin = new VectorI3F(origin_x, origin_y, origin_z);
      final VectorI3F target = new VectorI3F(target_x, target_y, target_z);

      MatrixM4x4F.lookAt(
        mc, origin, target, QuaternionM4FContract.AXIS_Y, ml);
      QuaternionM4F.lookAt(
        qc, origin, target, QuaternionM4FContract.AXIS_Y, lq);
      QuaternionM4F.makeRotationMatrix4x4(lq, mq);

      
      
      
      

      for (int row = 0; row < 3; ++row) {
        for (int col = 0; col < 3; ++col) {
          final float x = ml.getRowColumnF(row, col);
          final float y = mq.getRowColumnF(row, col);

          final boolean eq = AlmostEqualFloat.almostEqual(ec, x, y);
          Assert.assertTrue(eq);
        }
      }
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newQuaternion(x, y, z, w);

      final double m = QuaternionM4F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float y =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float z =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final float w =
        (float) (this.getRandom() * (Math.sqrt((double) Float.MAX_VALUE)
                                     / 2.0));
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();
      QuaternionM4F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = QuaternionM4F.magnitude(vr);

      
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final T v = this.newQuaternion(0.0f, 0.0f, 0.0f, 0.0f);
    final T vr = QuaternionM4F.normalizeInPlace(v);
    final double m = QuaternionM4F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMagnitudeOne()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final T v = this.newQuaternion(1.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Test public final void testMagnitudeSimple()
  {
    final T v = this.newQuaternion(8.0f, 0.0f, 0.0f, 0.0f);

    {
      final double p = QuaternionM4F.dotProduct(v, v);
      final double q = QuaternionM4F.magnitudeSquared(v);
      final double r = QuaternionM4F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final T v = this.newQuaternion(0.0f, 0.0f, 0.0f, 0.0f);
    final double m = QuaternionM4F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Test public final void testMakeAxisAngleNormal()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3F axis_r = new VectorI3F(
        (float) this.getRandom(),
        (float) this.getRandom(),
        (float) this.getRandom());
      final VectorI3F axis_n = VectorI3F.normalize(axis_r);

      final T q = this.newQuaternion();
      QuaternionM4F.makeFromAxisAngle(
        axis_n, Math.toRadians(this.getRandom() * 360.0), q);

      final double m = QuaternionM4F.magnitude(q);

      
      
      
      
      

      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Test public final void testMakeAxisAngleX_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getXF(), 0.3826834323650898f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.9238795325112867f));
  }

  @Test public final void testMakeAxisAngleX_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getXF(), 0.7071067811865475f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.7071067811865475f));
  }

  @Test public final void testMakeAxisAngleY_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getYF(), 0.3826834323650898f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.9238795325112867f));
  }

  @Test public final void testMakeAxisAngleY_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getYF(), 0.7071067811865475f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getZF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.7071067811865475f));
  }

  @Test public final void testMakeAxisAngleZ_45()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(45.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getZF(), 0.3826834323650898f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.9238795325112867f));
  }

  @Test public final void testMakeAxisAngleZ_90()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final T q = this.newQuaternion();
    final T r = QuaternionM4F.makeFromAxisAngle(
      axis, Math.toRadians(90.0), q);
    Assert.assertSame(r, q);

    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getXF(), 0.0f));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(context, q.getYF(), 0.0f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getZF(), 0.7071067811865475f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, q.getWF(), 0.7071067811865475f));
  }

  @Test public final void testMakeFromMatrix3x3Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final T qfm = this.newQuaternion();
    final T qaa = this.newQuaternion();
    final Matrix3x3FType m = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2.0 * this.getRandom() * 360.0) - 360.0);
      final float angle = (float) Math.toRadians((double) degrees);
      final float axis_x = (float) this.getRandom();
      final float axis_y = (float) this.getRandom();
      final float axis_z = (float) this.getRandom();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, (double) angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3F.makeRotation((double) angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix3x3(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      
      
      
      
      
      
      
      
      

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

  @Test public final void testMakeFromMatrix4x4Exhaustive()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final T qfm = this.newQuaternion();
    final T qaa = this.newQuaternion();
    final Matrix4x4FType m = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float degrees = (float) ((2.0 * this.getRandom() * 360.0) - 360.0);
      final float angle = (float) Math.toRadians((double) degrees);
      final float axis_x = (float) this.getRandom();
      final float axis_y = (float) this.getRandom();
      final float axis_z = (float) this.getRandom();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      QuaternionM4F.makeFromAxisAngle(axis, (double) angle, qaa);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4F.makeRotation((double) angle, axis, m);
      QuaternionM4F.makeFromRotationMatrix4x4(m, qfm);

      final double mag_qfm = QuaternionM4F.magnitude(qfm);
      final double mag_qaa = QuaternionM4F.magnitude(qaa);

      
      
      
      
      
      
      
      
      

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

  @Test public final void testMakeMatrix3x3_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Identity()
  {
    final T q = this.newQuaternion();
    final Matrix3x3FType m = MatrixHeapArrayM3x3F.newMatrix();

    QuaternionM4F.makeRotationMatrix3x3(q, m);

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

  @Test public final void testMakeMatrix3x3_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix3x3_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix3x3FType mq = MatrixHeapArrayM3x3F.newMatrix();
    final Matrix3x3FType mr = MatrixHeapArrayM3x3F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix3x3(q, mq);

    
    
    
    

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Identity()
  {
    final T q = this.newQuaternion();
    final Matrix4x4FType m = MatrixHeapArrayM4x4F.newMatrix();

    QuaternionM4F.makeRotationMatrix4x4(q, m);

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

  @Test public final void testMakeMatrix4x4_Minus45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMakeMatrix4x4_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final T q = this.newQuaternion();
    final Matrix4x4FType mq = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType mr = MatrixHeapArrayM4x4F.newMatrix();
    boolean eq = false;

    final double radians = Math.toRadians(-45.0);
    final VectorReadable3FType axis = QuaternionM4FContract.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    QuaternionM4F.makeFromAxisAngle(axis, radians, q);
    QuaternionM4F.makeRotationMatrix4x4(q, mq);

    
    
    
    

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.getRowColumnF(row, col);
        final float y = mq.getRowColumnF(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @Test public final void testMultiply()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final T qx = this.newQuaternion();
    final T qxr =
      QuaternionM4F.makeFromAxisAngle(axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    final T qyr =
      QuaternionM4F.makeFromAxisAngle(axis_y, Math.toRadians(45.0), qy);

    Assert.assertSame(qx, qxr);
    Assert.assertSame(qy, qyr);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4F.multiply(qy, qx, qr);

    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getXF(), 0.3535533905932738f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getYF(), 0.3535533905932738f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getZF(), -0.14644660940672624f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getWF(), 0.8535533905932737f));
  }

  @Test public final void testMultiplyInPlace()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);

    final T qx = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_y, Math.toRadians(45.0), qy);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getXF(), 0.3535533983204287f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getYF(), 0.3535533983204287f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getZF(), -0.14644661713388138f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getWF(), 0.8535533828661185f));
  }

  @Test public final void testMultiplyInPlaceOther()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final T qx = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_y, Math.toRadians(45.0), qy);
    final T qz = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_z, Math.toRadians(45.0), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4F.multiplyInPlace(qr, qz);
    QuaternionM4F.multiplyInPlace(qr, qy);
    QuaternionM4F.multiplyInPlace(qr, qx);

    
    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getXF(), 0.1913417153164435f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getYF(), 0.4619397784426109f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getZF(), 0.1913417153164436f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getWF(), 0.8446231923478736f));
  }

  @Test public final void testMultiplyOther()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F axis_z = new VectorI3F(0.0f, 0.0f, 1.0f);

    final T qx = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_x, Math.toRadians(45.0), qx);
    final T qy = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_y, Math.toRadians(45.0), qy);
    final T qz = this.newQuaternion();
    QuaternionM4F.makeFromAxisAngle(
      axis_z, Math.toRadians(45.0), qz);

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis,
     * followed by a 45 degree rotation around the global Z axis.
     */

    final T qr = this.newQuaternion();
    QuaternionM4F.multiply(qy, qx, qr);
    QuaternionM4F.multiply(qz, qr, qr);

    
    
    
    

    /**
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getXF(), 0.1913417153164435f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getYF(), 0.4619397784426109f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getZF(), 0.1913417153164436f));
    Assert.assertTrue(
      AlmostEqualFloat.almostEqual(
        context, qr.getWF(), 0.8446231923478736f));
  }

  @Test public final void testNegation()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((this.getRandom() * 2.0) - this.getRandom());
      final float y = (float) ((this.getRandom() * 2.0) - this.getRandom());
      final float z = (float) ((this.getRandom() * 2.0) - this.getRandom());
      final float w = (float) ((this.getRandom() * 2.0) - this.getRandom());
      final T qi = this.newQuaternion(x, y, z, w);
      final T qn = this.newQuaternion(-x, -y, -z, -w);
      final T qr = this.newQuaternion();

      QuaternionM4F.negate(qi, qr);

      
      
      
      

      Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionM4F.almostEqual(context, qn, qr));
    }
  }

  @Test public final void testNegationCases()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final T qi = this.newQuaternion(1.0F, 2.0F, 3.0F, 4.0F);
    final T qnx = this.newQuaternion(-1.0F, 2.0F, 3.0F, 4.0F);
    final T qny = this.newQuaternion(-1.0F, -2.0F, 3.0F, 4.0F);
    final T qnz = this.newQuaternion(-1.0F, -2.0F, -3.0F, 4.0F);
    final T qnw = this.newQuaternion(-1.0F, -2.0F, -3.0F, -4.0F);

    Assert.assertFalse(QuaternionM4F.isNegationOf(context, qi, qi));
    Assert.assertFalse(QuaternionM4F.isNegationOf(context, qi, qnx));
    Assert.assertFalse(QuaternionM4F.isNegationOf(context, qi, qny));
    Assert.assertFalse(QuaternionM4F.isNegationOf(context, qi, qnz));
    Assert.assertTrue(QuaternionM4F.isNegationOf(context, qi, qnw));

    QuaternionM4F.negateInPlace(qnw);
    Assert.assertFalse(QuaternionM4F.isNegationOf(context, qi, qnw));
    Assert.assertTrue(QuaternionM4F.almostEqual(context, qi, qnw));
  }

  @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newQuaternion(8.0f, 0.0f, 0.0f, 0.0f);
    final T out = this.newQuaternion();
    final T vr = QuaternionM4F.normalize(v0, out);

    Assert.assertEquals(out, vr);

    final double m = QuaternionM4F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T qr = this.newQuaternion();
    final T q = this.newQuaternion(0.0F, 0.0F, 0.0F, 0.0F);
    QuaternionM4F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getZF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getWF()));
  }

  @Test public final void testScaleMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final T ov0 = QuaternionM4F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);

    final T ov1 = QuaternionM4F.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getWF(), 0.0);
  }

  @Test public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();

      QuaternionM4F.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v.getYF(), vr.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v.getZF(), vr.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, v.getWF(), vr.getWF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();
        final float orig_z = v.getZF();
        final float orig_w = v.getWF();

        QuaternionM4F.scaleInPlace(v, 1.0);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getXF(), orig_x));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getYF(), orig_y));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getZF(), orig_z));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getWF(), orig_w));
      }
    }
  }

  @Test public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v = this.newQuaternion(x, y, z, w);

      final T vr = this.newQuaternion();

      QuaternionM4F.scale(v, 0.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getZF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getWF(), 0.0f));

      {
        QuaternionM4F.scaleInPlace(v, 0.0);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getXF(), 0.0f));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getYF(), 0.0f));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getZF(), 0.0f));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v.getWF(), 0.0f));
      }
    }
  }

  @Test public final void testString()
  {
    final T v = this.newQuaternion(1.0f, 2.0f, 3.0f, 4.0f);
    
    Assert.assertTrue(v.toString().endsWith(" 1.0 2.0 3.0 4.0]"));
  }

  @Test public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w0 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v0 = this.newQuaternion(x0, y0, z0, w0);

      final float x1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float y1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float z1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final float w1 = (float) (this.getRandom() * (double) Float.MAX_VALUE);
      final T v1 = this.newQuaternion(x1, y1, z1, w1);

      final T vr0 = this.newQuaternion();
      QuaternionM4F.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getYF(), v0.getYF() - v1.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getZF(), v0.getZF() - v1.getZF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr0.getWF(), v0.getWF() - v1.getWF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        final float orig_z = v0.getZF();
        final float orig_w = v0.getWF();
        QuaternionM4F.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getXF(), orig_x - v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getYF(), orig_y - v1.getYF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getZF(), orig_z - v1.getZF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            context, v0.getWF(), orig_w - v1.getWF()));
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final T out = this.newQuaternion();
    final T v0 = this.newQuaternion(1.0f, 1.0f, 1.0f, 1.0f);
    final T v1 = this.newQuaternion(1.0f, 1.0f, 1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov0 = QuaternionM4F.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);

    final T ov1 = QuaternionM4F.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(0.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getWF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getZF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getWF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getZF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getWF(), 0.0);
  }
}
