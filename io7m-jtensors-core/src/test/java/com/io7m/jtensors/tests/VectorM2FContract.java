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
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jtensors.Vector2FType;
import com.io7m.jtensors.VectorM2F;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2FContract<T extends Vector2FType>
{
  protected abstract T newVectorM2F(T v);

  protected abstract T newVectorM2F();

  protected abstract T newVectorM2F(
    final float x,
    final float y);

   @Test public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargeNegative();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      VectorM2F.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2F.absoluteInPlace(v);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, Math.abs(orig_x), v.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, Math.abs(orig_y), v.getYF()));
      }
    }
  }

   @Test public final void testAbsoluteMutation()
  {
    final T out = this.newVectorM2F();
    final T v = this.newVectorM2F(-1.0f, -1.0f);

    Assert.assertEquals(-1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(-1.0, (double) v.getYF(), 0.0);

    final float vx = v.getXF();
    final float vy = v.getYF();

    final T ov = VectorM2F.absolute(v, out);

    Assert.assertEquals((double) v.getXF(), (double) vx, 0.0);
    Assert.assertEquals((double) v.getYF(), (double) vy, 0.0);
    Assert.assertEquals(-1.0, (double) vx, 0.0);
    Assert.assertEquals(-1.0, (double) vy, 0.0);

    Assert.assertEquals(ov, out); Assert.assertSame(ov, out);
    Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getYF(), 0.0);
  }

   @Test public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (VectorM2FContract.getRandom() * (double) max);
      final float y0 = (float) (VectorM2FContract.getRandom() * (double) max);
      final T v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (VectorM2FContract.getRandom() * (double) max);
      final float y1 = (float) (VectorM2FContract.getRandom() * (double) max);
      final T v1 = this.newVectorM2F(x1, y1);

      final T vr0 = this.newVectorM2F();
      VectorM2F.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + v1.getYF()));
      }
    }
  }

   @Test public final void testAddMutation()
  {
    final T out = this.newVectorM2F();
    final T v0 = this.newVectorM2F(1.0f, 1.0f);
    final T v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final T ov0 = VectorM2F.add(v0, v1, out);

    Assert.assertEquals(ov0, out); Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final T ov1 = VectorM2F.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1); Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
  }

   @Test public final void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x0 = (float) (VectorM2FContract.getRandom() * (double) max);
      final float y0 = (float) (VectorM2FContract.getRandom() * (double) max);
      final T v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (VectorM2FContract.getRandom() * (double) max);
      final float y1 = (float) (VectorM2FContract.getRandom() * (double) max);
      final T v1 = this.newVectorM2F(x1, y1);

      final float r = (float) (VectorM2FContract.getRandom() * (double) max);

      final T vr0 = this.newVectorM2F();
      VectorM2F.addScaled(v0, v1, (double) r, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + (v1.getYF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.addScaledInPlace(v0, v1, (double) r);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + (v1.getXF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + (v1.getYF() * r)));
      }
    }
  }

   @Test public final void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) VectorM2FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(VectorM2F.almostEqual(ec, m0, m1));
    }
  }

   @Test public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM2FContract.getRandomLargePositive();
      final float y0 = VectorM2FContract.getRandomLargePositive();
      final T v0 = this.newVectorM2F(x0, y0);
      final T v1 = this.newVectorM2F(x0, y0);
      final T v2 = this.newVectorM2F(x0, y0);

      Assert.assertTrue(VectorM2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2F.almostEqual(ec, v0, v2));
    }
  }

   @Test public final void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) VectorM2FContract.getRandom();
      final float y = (float) VectorM2FContract.getRandom();
      final T v0 = this.newVectorM2F(x, y);
      final T v1 = this.newVectorM2F(x, y);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final float x = (float) VectorM2FContract.getRandom();
      final float y = (float) VectorM2FContract.getRandom();
      final T v0 = this.newVectorM2F(x, y);
      final T v1 = this.newVectorM2F(y, -x);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final float x = (float) VectorM2FContract.getRandom();
      final float y = (float) VectorM2FContract.getRandom();
      final T v0 = this.newVectorM2F(x, y);
      final T v1 = this.newVectorM2F(-y, x);
      VectorM2F.normalizeInPlace(v0);
      VectorM2F.normalizeInPlace(v1);
      final double angle = VectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

   @Test public final void testCheckInterface()
  {
    final T v = this.newVectorM2F(3.0f, 5.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
  }

   @Test public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = VectorM2FContract.getRandomLargeNegative();
      final float max_y = VectorM2FContract.getRandomLargeNegative();
      final T maximum = this.newVectorM2F(max_x, max_y);

      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargeNegative();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      final T vo = VectorM2F.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo); Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());

      {
        final T vr0 = VectorM2F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0); Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
      }
    }
  }

  protected static float getRandomLargeNegative()
  {
    return (float) (VectorM2FContract.getRandom() * (double) -Float.MAX_VALUE);
  }

  @Test public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = VectorM2FContract.getRandomLargePositive();
      final float min_y = VectorM2FContract.getRandomLargePositive();
      final T minimum = this.newVectorM2F(min_x, min_y);

      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargeNegative();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      final T vo = VectorM2F.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo); Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final T vr0 = VectorM2F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0); Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

   @Test public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = VectorM2FContract.getRandomLargeNegative();
      final float min_y = VectorM2FContract.getRandomLargeNegative();
      final T minimum = this.newVectorM2F(min_x, min_y);

      final float max_x = VectorM2FContract.getRandomLargePositive();
      final float max_y = VectorM2FContract.getRandomLargePositive();
      final T maximum = this.newVectorM2F(max_x, max_y);

      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      final T vo = VectorM2F.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo); Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final T vr0 =
          VectorM2F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0); Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

   @Test public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = VectorM2FContract.getRandomLargeNegative();

      final float x = VectorM2FContract.getRandomLargePositive();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      VectorM2F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);

      {
        VectorM2F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
      }
    }
  }

   @Test public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = VectorM2FContract.getRandomLargePositive();

      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargeNegative();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      VectorM2F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

   @Test public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = VectorM2FContract.getRandomLargeNegative();
      final float maximum = VectorM2FContract.getRandomLargePositive();

      final float x = VectorM2FContract.getRandomLargeNegative();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      VectorM2F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

   @Test public final void testCopy()
  {
    final T vb = this.newVectorM2F(5.0F, 6.0F);
    final T va = this.newVectorM2F(1.0F, 2.0F);

    Assert.assertNotEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertNotEquals((double) vb.getYF(), (double) va.getYF(), 0.0);

    VectorM2F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
  }

   @Test public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM2F(
      VectorM2FContract.getRandomLargePositive(),
      VectorM2FContract.getRandomLargePositive());
    final T v1 = this.newVectorM2F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);
  }

  protected static float getRandomLargePositive()
  {
    return (float) VectorM2FContract.getRandom() * Float.MAX_VALUE;
  }

  @Test public final void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      VectorM2F.almostEqual(
        ec, this.newVectorM2F(), this.newVectorM2F(0.0F, 0.0F)));
  }

   @Test public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final VectorM2F.ContextVM2F c = new VectorM2F.ContextVM2F();

    final T v0 = this.newVectorM2F(0.0f, 1.0f);
    final T v1 = this.newVectorM2F(0.0f, 0.0f);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, VectorM2F.distance(c, v0, v1), 1.0));
  }

   @Test public final void testDistanceOrdering()
  {
    final VectorM2F.ContextVM2F c = new VectorM2F.ContextVM2F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM2FContract.getRandomLargePositive();
      final float y0 = VectorM2FContract.getRandomLargePositive();
      final T v0 = this.newVectorM2F(x0, y0);

      final float x1 = VectorM2FContract.getRandomLargePositive();
      final float y1 = VectorM2FContract.getRandomLargePositive();
      final T v1 = this.newVectorM2F(x1, y1);

      Assert.assertTrue(VectorM2F.distance(c, v0, v1) >= 0.0);
    }
  }

   @Test public final void testDotProduct()
  {
    final T v0 = this.newVectorM2F(10.0f, 10.0f);
    final T v1 = this.newVectorM2F(10.0f, 10.0f);

    {
      final float p = VectorM2F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }

    {
      final float p = VectorM2F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }

    {
      final float p = VectorM2F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }
  }

   @Test public final void testDotProductPerpendicular()
  {
    final T vpx = this.newVectorM2F(1.0f, 0.0f);
    final T vmx = this.newVectorM2F(-1.0f, 0.0f);

    final T vpy = this.newVectorM2F(0.0f, 1.0f);
    final T vmy = this.newVectorM2F(0.0f, -1.0f);

    Assert.assertEquals(0.0, (double) VectorM2F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, (double) VectorM2F.dotProduct(vmx, vmy), 0.0);
  }

   @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) VectorM2FContract.getRandom();
      final float y = (float) VectorM2FContract.getRandom();
      final T q = this.newVectorM2F(x, y);
      final float dp = VectorM2F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, (double) dp);
    }
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM2F(10.0f, 10.0f);

    {
      final double p = (double) VectorM2F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = VectorM2F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

   @Test public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM2F();
      final T m1 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

   @Test public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) VectorM2FContract.getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final T m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM2F(x, y);
      final T m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

   @Test public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM2F();
    final T m1 = this.newVectorM2F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

   @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM2F();
      final T m1 = this.newVectorM2F();
      m1.setXF(23.0F);
      Assert.assertNotEquals((long) m0.hashCode(), (long) m1.hashCode());
    }

    {
      final T m0 = this.newVectorM2F();
      final T m1 = this.newVectorM2F();
      m1.setYF(23.0F);
      Assert.assertNotEquals((long) m0.hashCode(), (long) m1.hashCode());
    }
  }

   @Test public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM2F(1.0f, 2.0f);
    final T v1 = this.newVectorM2F(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
  }

   @Test public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final VectorM2F.ContextVM2F c = new VectorM2F.ContextVM2F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM2FContract.getRandomLargePositive();
      final float y0 = VectorM2FContract.getRandomLargePositive();
      final T v0 = this.newVectorM2F(x0, y0);

      final float x1 = VectorM2FContract.getRandomLargePositive();
      final float y1 = VectorM2FContract.getRandomLargePositive();
      final T v1 = this.newVectorM2F(x1, y1);

      final T vr0 = this.newVectorM2F();
      final T vr1 = this.newVectorM2F();
      VectorM2F.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM2F.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v0.getXF(), vr0.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v0.getYF(), vr0.getYF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getXF(), vr1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v1.getYF(), vr1.getYF()));
    }
  }

   @Test public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM2FContract.getRandomLargePositive();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final double m = VectorM2F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

   @Test public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x = (float) (VectorM2FContract.getRandom() * (double) max);
      final float y = (float) (VectorM2FContract.getRandom() * (double) max);
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();
      VectorM2F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2F.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

   @Test public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2F(0.0f, 0.0f);
    final T vr = VectorM2F.normalizeInPlace(v);
    final double m = VectorM2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

   @Test public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2F(1.0f, 0.0f);
    final double m = VectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

   @Test public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM2F(8.0f, 0.0f);

    {
      final double p = (double) VectorM2F.dotProduct(v, v);
      final double q = VectorM2F.magnitudeSquared(v);
      final double r = VectorM2F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

   @Test public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final T v = this.newVectorM2F(0.0f, 0.0f);
    final double m = VectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

   @Test public final void testNormalizeSimple()
  {
    final T v0 = this.newVectorM2F(8.0f, 0.0f);
    final T out = this.newVectorM2F();
    final T vr = VectorM2F.normalize(v0, out);

    Assert.assertEquals(out, vr); Assert.assertSame(out, vr);

    final double m = VectorM2F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

   @Test public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final T qr = this.newVectorM2F();
    final T q = this.newVectorM2F(0.0F, 0.0F);
    VectorM2F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
  }

   @Test public final void testOrthonormalize()
  {
    final VectorM2F.ContextVM2F c = new VectorM2F.ContextVM2F();
    final T v0 = this.newVectorM2F(0.0F, 1.0F);
    final T v1 = this.newVectorM2F(0.5f, 0.5f);

    final T v0_out = this.newVectorM2F();
    final T v1_out = this.newVectorM2F();

    VectorM2F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2F(0.0F, 1.0F), v0_out);
    Assert.assertEquals(this.newVectorM2F(1.0F, 0.0F), v1_out);
  }

   @Test public final void testOrthonormalizeMutation()
  {
    final VectorM2F.ContextVM2F c = new VectorM2F.ContextVM2F();
    final T v0 = this.newVectorM2F(0f, 1f);
    final T v1 = this.newVectorM2F(0.5f, 0.5f);

    VectorM2F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2F(0.0F, 1.0F), v0);
    Assert.assertEquals(this.newVectorM2F(1.0F, 0.0F), v1);
  }

   @Test public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM2F(1.0f, 0.0f);
      final T q = this.newVectorM2F(0.0f, 1.0f);
      final T r = this.newVectorM2F();
      final T u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM2F.magnitude(u), 0.0);
    }

    {
      final T p = this.newVectorM2F(-1.0f, 0.0f);
      final T q = this.newVectorM2F(0.0f, 1.0f);
      final T r = this.newVectorM2F();
      final T u = VectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, VectorM2F.magnitude(u), 0.0);
    }
  }

   @Test public final void testScaleMutation()
  {
    final T out = this.newVectorM2F();
    final T v0 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);

    final T ov0 = VectorM2F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out); Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);

    final T ov1 = VectorM2F.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1); Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
  }

   @Test public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM2FContract.getRandomLargePositive();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();

      VectorM2F.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2F.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
      }
    }
  }

   @Test public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = VectorM2FContract.getRandomLargePositive();
      final float y = VectorM2FContract.getRandomLargePositive();
      final T v = this.newVectorM2F(x, y);

      final T vr = this.newVectorM2F();

      VectorM2F.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), 0.0f));

      {
        VectorM2F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
      }
    }
  }

   @Test public final void testString()
  {
    final T v = this.newVectorM2F(0.0f, 1.0f);
    Assert.assertTrue(v.toString().endsWith("0.0 1.0]"));
  }

   @Test public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = VectorM2FContract.getRandomLargePositive();
      final float y0 = VectorM2FContract.getRandomLargePositive();
      final T v0 = this.newVectorM2F(x0, y0);

      final float x1 = VectorM2FContract.getRandomLargePositive();
      final float y1 = VectorM2FContract.getRandomLargePositive();
      final T v1 = this.newVectorM2F(x1, y1);

      final T vr0 = this.newVectorM2F();
      VectorM2F.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() - v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2F.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x - v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y - v1.getYF()));
      }
    }
  }

   @Test public final void testSubtractMutation()
  {
    final T out = this.newVectorM2F();
    final T v0 = this.newVectorM2F(1.0f, 1.0f);
    final T v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final T ov0 = VectorM2F.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out); Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final T ov1 = VectorM2F.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1); Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
  }
}
