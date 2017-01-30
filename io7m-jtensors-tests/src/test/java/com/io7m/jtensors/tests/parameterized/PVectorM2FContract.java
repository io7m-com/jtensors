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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jtensors.parameterized.PVector2FType;
import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2FContract<T, V extends PVector2FType<T>>
{
  protected static float getRandomLargeNegative()
  {
    return (float) (getRandom() * (double) -Float.MAX_VALUE);
  }

  protected static double getRandom()
  {
    return Math.random();
  }

  protected static float getRandomLargePositive()
  {
    return (float) (getRandom() * (double) Float.MAX_VALUE);
  }

  protected abstract V newVectorM2F(final V v0);

  protected abstract V newVectorM2F(
    final float x1,
    final float y1);

  protected abstract V newVectorM2F();

  @Test
  public final void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = getRandomLargeNegative();
      final float y = getRandomLargeNegative();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      PVectorM2F.absolute(v, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getXF()), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, Math.abs(v.getYF()), vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        PVectorM2F.absoluteInPlace(v);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, Math.abs(orig_x), v.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, Math.abs(orig_y), v.getYF()));
      }
    }
  }

  @Test
  public final void testAbsoluteMutation()
  {
    final V out = this.newVectorM2F();
    final V v = this.newVectorM2F(-1.0f, -1.0f);

    Assert.assertEquals(-1.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(-1.0, (double) v.getYF(), 0.0);

    final float vx = v.getXF();
    final float vy = v.getYF();

    final V ov = PVectorM2F.absolute(v, out);

    Assert.assertEquals((double) v.getXF(), (double) vx, 0.0);
    Assert.assertEquals((double) v.getYF(), (double) vy, 0.0);
    Assert.assertEquals(-1.0, (double) vx, 0.0);
    Assert.assertEquals(-1.0, (double) vy, 0.0);

    Assert.assertEquals(ov, out);
    Assert.assertSame(ov, out);
    Assert.assertEquals(1.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) out.getYF(), 0.0);
  }

  @Test
  public final void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (getRandom() * (double) max);
      final float y0 = (float) (getRandom() * (double) max);
      final V v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (getRandom() * (double) max);
      final float y1 = (float) (getRandom() * (double) max);
      final V v1 = this.newVectorM2F(x1, y1);

      final V vr0 = this.newVectorM2F();
      PVectorM2F.add(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        PVectorM2F.addInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + v1.getYF()));
      }
    }
  }

  @Test
  public final void testAddMutation()
  {
    final V out = this.newVectorM2F();
    final V v0 = this.newVectorM2F(1.0f, 1.0f);
    final V v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final V ov0 = PVectorM2F.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final V ov1 = PVectorM2F.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
  }

  @Test
  public final void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x0 = (float) (getRandom() * (double) max);
      final float y0 = (float) (getRandom() * (double) max);
      final V v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (getRandom() * (double) max);
      final float y1 = (float) (getRandom() * (double) max);
      final V v1 = this.newVectorM2F(x1, y1);

      final float r = (float) (getRandom() * (double) max);

      final V vr0 = this.newVectorM2F();
      PVectorM2F.addScaled(v0, v1, (double) r, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + (v1.getYF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        PVectorM2F.addScaledInPlace(v0, v1, (double) r);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + (v1.getXF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + (v1.getYF() * r)));
      }
    }
  }

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
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }
  }

  @Test
  public final void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = getRandomLargePositive();
      final float y0 = getRandomLargePositive();
      final V v0 = this.newVectorM2F(x0, y0);
      final V v1 = this.newVectorM2F(x0, y0);
      final V v2 = this.newVectorM2F(x0, y0);

      Assert.assertTrue(PVectorM2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM2F.almostEqual(ec, v0, v2));
    }
  }

  @Test
  public final void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final V v0 = this.newVectorM2F(x, y);
      final V v1 = this.newVectorM2F(x, y);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final V v0 = this.newVectorM2F(x, y);
      final V v1 = this.newVectorM2F(y, -x);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }

    {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final V v0 = this.newVectorM2F(x, y);
      final V v1 = this.newVectorM2F(-y, x);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90.0)));
    }
  }

  @Test
  public final void testCheckInterface()
  {
    final V v = this.newVectorM2F(3.0f, 5.0f);

    Assert.assertEquals((double) v.getXF(), (double) v.getXF(), 0.0);
    Assert.assertEquals((double) v.getYF(), (double) v.getYF(), 0.0);
  }

  @Test
  public final void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = getRandomLargeNegative();
      final float max_y = getRandomLargeNegative();
      final V maximum = this.newVectorM2F(max_x, max_y);

      final float x = getRandomLargeNegative();
      final float y = getRandomLargeNegative();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      final V vo = PVectorM2F.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());

      {
        final V vr0 = PVectorM2F.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
      }
    }
  }

  @Test
  public final void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = getRandomLargePositive();
      final float min_y = getRandomLargePositive();
      final V minimum = this.newVectorM2F(min_x, min_y);

      final float x = getRandomLargeNegative();
      final float y = getRandomLargeNegative();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      final V vo = PVectorM2F.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final V vr0 = PVectorM2F.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Test
  public final void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = getRandomLargeNegative();
      final float min_y = getRandomLargeNegative();
      final V minimum = this.newVectorM2F(min_x, min_y);

      final float max_x = getRandomLargePositive();
      final float max_y = getRandomLargePositive();
      final V maximum = this.newVectorM2F(max_x, max_y);

      final float x = getRandomLargeNegative();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      final V vo = PVectorM2F.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertSame(vr, vo);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final V vr0 = PVectorM2F.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertSame(v, vr0);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Test
  public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = getRandomLargeNegative();

      final float x = getRandomLargePositive();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      PVectorM2F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);

      {
        PVectorM2F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
      }
    }
  }

  @Test
  public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = getRandomLargePositive();

      final float x = getRandomLargeNegative();
      final float y = getRandomLargeNegative();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      PVectorM2F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        PVectorM2F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Test
  public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = getRandomLargeNegative();
      final float maximum = getRandomLargePositive();

      final float x = getRandomLargeNegative();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      PVectorM2F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        PVectorM2F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Test
  public final void testCopy()
  {
    final V vb = this.newVectorM2F(5.0F, 6.0F);
    final V va = this.newVectorM2F(1.0F, 2.0F);

    Assert.assertNotEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertNotEquals((double) vb.getYF(), (double) va.getYF(), 0.0);

    PVectorM2F.copy(va, vb);

    Assert.assertEquals((double) vb.getXF(), (double) va.getXF(), 0.0);
    Assert.assertEquals((double) vb.getYF(), (double) va.getYF(), 0.0);
  }

  @Test
  public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM2F(
      getRandomLargePositive(),
      getRandomLargePositive());
    final V v1 = this.newVectorM2F();
    final V v2 = this.newVectorM2F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);

    v2.copyFromTyped2F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
  }

  @Test
  public final void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      PVectorM2F.almostEqual(
        ec, this.newVectorM2F(), this.newVectorM2F(0.0F, 0.0F)));
  }

  @Test
  public final void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();

    final V v0 = this.newVectorM2F(0.0f, 1.0f);
    final V v1 = this.newVectorM2F(0.0f, 0.0f);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorM2F.distance(c, v0, v1), 1.0));
  }

  @Test
  public final void testDistanceOrdering()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = getRandomLargePositive();
      final float y0 = getRandomLargePositive();
      final V v0 = this.newVectorM2F(x0, y0);

      final float x1 = getRandomLargePositive();
      final float y1 = getRandomLargePositive();
      final V v1 = this.newVectorM2F(x1, y1);

      Assert.assertTrue(PVectorM2F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Test
  public final void testDotProduct()
  {
    final V v0 = this.newVectorM2F(10.0f, 10.0f);
    final V v1 = this.newVectorM2F(10.0f, 10.0f);

    {
      final float p = PVectorM2F.dotProduct(v0, v1);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }

    {
      final float p = PVectorM2F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }

    {
      final float p = PVectorM2F.dotProduct(v1, v1);
      Assert.assertEquals(10.0, (double) v1.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v1.getYF(), 0.0);
      Assert.assertEquals(200.0, (double) p, 0.0);
    }
  }

  @Test
  public final void testDotProductPerpendicular()
  {
    final V vpx = this.newVectorM2F(1.0f, 0.0f);
    final V vmx = this.newVectorM2F(-1.0f, 0.0f);

    final V vpy = this.newVectorM2F(0.0f, 1.0f);
    final V vmy = this.newVectorM2F(0.0f, -1.0f);

    Assert.assertEquals(0.0, (double) PVectorM2F.dotProduct(vpx, vpy), 0.0);
    Assert.assertEquals(0.0, (double) PVectorM2F.dotProduct(vmx, vmy), 0.0);
  }

  @Test
  public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) getRandom();
      final float y = (float) getRandom();
      final V q = this.newVectorM2F(x, y);
      final float dp = PVectorM2F.dotProduct(q, q);

      AlmostEqualDouble.almostEqual(ec, 1.0, (double) dp);
    }
  }

  @Test
  public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM2F(10.0f, 10.0f);

    {
      final double p = (double) PVectorM2F.dotProduct(v0, v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }

    {
      final double p = PVectorM2F.magnitudeSquared(v0);
      Assert.assertEquals(10.0, (double) v0.getXF(), 0.0);
      Assert.assertEquals(10.0, (double) v0.getYF(), 0.0);
      Assert.assertEquals(200.0, p, 0.0);
    }
  }

  @Test
  public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM2F();
      final V m1 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test
  public final void testEqualsNotEqualCorrect()
  {
    final float x = (float) getRandom();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final V m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM2F(x, y);
      final V m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test
  public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM2F();
    final V m1 = this.newVectorM2F();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test
  public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM2F();
      final V m1 = this.newVectorM2F();
      m1.setXF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final V m0 = this.newVectorM2F();
      final V m1 = this.newVectorM2F();
      m1.setYF(23.0F);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test
  public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM2F(1.0f, 2.0f);
    final V v1 = this.newVectorM2F(v0);

    Assert.assertEquals((double) v1.getXF(), (double) v0.getXF(), 0.0);
    Assert.assertEquals((double) v1.getYF(), (double) v0.getYF(), 0.0);
  }

  @Test
  public final void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = getRandomLargePositive();
      final float y0 = getRandomLargePositive();
      final V v0 = this.newVectorM2F(x0, y0);

      final float x1 = getRandomLargePositive();
      final float y1 = getRandomLargePositive();
      final V v1 = this.newVectorM2F(x1, y1);

      final V vr0 = this.newVectorM2F();
      final V vr1 = this.newVectorM2F();
      PVectorM2F.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM2F.interpolateLinear(c, v0, v1, 1.0, vr1);

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

  @Test
  public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = getRandomLargePositive();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final double m = PVectorM2F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Test
  public final void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x = (float) (getRandom() * (double) max);
      final float y = (float) (getRandom() * (double) max);
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();
      PVectorM2F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM2F.magnitude(vr);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Test
  public final void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2F(0.0f, 0.0f);
    final V vr = PVectorM2F.normalizeInPlace(v);
    final double m = PVectorM2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test
  public final void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2F(1.0f, 0.0f);
    final double m = PVectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
  }

  @Test
  public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM2F(8.0f, 0.0f);

    {
      final double p = (double) PVectorM2F.dotProduct(v, v);
      final double q = PVectorM2F.magnitudeSquared(v);
      final double r = PVectorM2F.magnitude(v);
      Assert.assertEquals(64.0, p, 0.0);
      Assert.assertEquals(64.0, q, 0.0);
      Assert.assertEquals(8.0, r, 0.0);
    }
  }

  @Test
  public final void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final V v = this.newVectorM2F(0.0f, 0.0f);
    final double m = PVectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Test
  public final void testNormalizeSimple()
  {
    final V v0 = this.newVectorM2F(8.0f, 0.0f);
    final V out = this.newVectorM2F();
    final V vr = PVectorM2F.normalize(v0, out);

    Assert.assertEquals(out, vr);
    Assert.assertSame(out, vr);

    final double m = PVectorM2F.magnitude(out);
    Assert.assertEquals(1.0, m, 0.0);
  }

  @Test
  public final void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final V qr = this.newVectorM2F();
    final V q = this.newVectorM2F(0.0F, 0.0F);
    PVectorM2F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0.0F, qr.getYF()));
  }

  @Test
  public final void testOrthonormalize()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    final V v0 = this.newVectorM2F(0.0F, 1.0F);
    final V v0_out = this.newVectorM2F();
    final V v1 = this.newVectorM2F(0.5f, 0.5f);
    final V v1_out = this.newVectorM2F();

    PVectorM2F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2F(0.0F, 1.0F), v0_out);
    Assert.assertEquals(this.newVectorM2F(1.0F, 0.0F), v1_out);
  }

  @Test
  public final void testOrthonormalizeMutation()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    final V v0 = this.newVectorM2F(0.0f, 1.0f);
    final V v1 = this.newVectorM2F(0.5f, 0.5f);

    PVectorM2F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2F(0.0F, 1.0F), v0);
    Assert.assertEquals(this.newVectorM2F(1.0F, 0.0F), v1);
  }

  @Test
  public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM2F(1.0f, 0.0f);
      final V q = this.newVectorM2F(0.0f, 1.0f);
      final V r = this.newVectorM2F();
      final V u = PVectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM2F.magnitude(u), 0.0);
    }

    {
      final V p = this.newVectorM2F(-1.0f, 0.0f);
      final V q = this.newVectorM2F(0.0f, 1.0f);
      final V r = this.newVectorM2F();
      final V u = PVectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0.0, PVectorM2F.magnitude(u), 0.0);
    }
  }

  @Test
  public final void testScaleMutation()
  {
    final V out = this.newVectorM2F();
    final V v0 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);

    final V ov0 = PVectorM2F.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(2.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);

    final V ov1 = PVectorM2F.scaleInPlace(v0, 2.0);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(2.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(2.0, (double) v0.getYF(), 0.0);
  }

  @Test
  public final void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = getRandomLargePositive();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();

      PVectorM2F.scale(v, 1.0, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        PVectorM2F.scaleInPlace(v, 1.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
      }
    }
  }

  @Test
  public final void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = getRandomLargePositive();
      final float y = getRandomLargePositive();
      final V v = this.newVectorM2F(x, y);

      final V vr = this.newVectorM2F();

      PVectorM2F.scale(v, 0.0, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), 0.0f));

      {
        PVectorM2F.scaleInPlace(v, 0.0);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
      }
    }
  }

  @Test
  public final void testString()
  {
    final V v = this.newVectorM2F(0.0f, 1.0f);
    Assert.assertTrue(v.toString().endsWith("0.0 1.0]"));
  }

  @Test
  public final void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = getRandomLargePositive();
      final float y0 = getRandomLargePositive();
      final V v0 = this.newVectorM2F(x0, y0);

      final float x1 = getRandomLargePositive();
      final float y1 = getRandomLargePositive();
      final V v1 = this.newVectorM2F(x1, y1);

      final V vr0 = this.newVectorM2F();
      PVectorM2F.subtract(v0, v1, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() - v1.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() - v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        PVectorM2F.subtractInPlace(v0, v1);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x - v1.getXF()));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y - v1.getYF()));
      }
    }
  }

  @Test
  public final void testSubtractMutation()
  {
    final V out = this.newVectorM2F();
    final V v0 = this.newVectorM2F(1.0f, 1.0f);
    final V v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final V ov0 = PVectorM2F.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertSame(ov0, out);
    Assert.assertEquals(0.0, (double) out.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) out.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);

    final V ov1 = PVectorM2F.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertSame(v0, ov1);
    Assert.assertEquals(0.0, (double) ov1.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) ov1.getYF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getXF(), 0.0);
    Assert.assertEquals(0.0, (double) v0.getYF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getXF(), 0.0);
    Assert.assertEquals(1.0, (double) v1.getYF(), 0.0);
  }
}
