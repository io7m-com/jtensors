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
import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2FContract<T> extends PVectorM2Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
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

  protected abstract <T> PVectorM2F<T> newVectorM2F();

  @Override @Test public void testAbsoluteMutation()
  {
    final PVectorM2F<T> out = this.newVectorM2F();
    final PVectorM2F<T> v = this.newVectorM2F(-1.0f, -1.0f);

    Assert.assertTrue(v.getXF() == -1.0);
    Assert.assertTrue(v.getYF() == -1.0);

    final float vx = v.getXF();
    final float vy = v.getYF();

    final PVectorM2F<T> ov = PVectorM2F.absolute(v, out);

    Assert.assertTrue(vx == v.getXF());
    Assert.assertTrue(vy == v.getYF());
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXF() == 1.0);
    Assert.assertTrue(out.getYF() == 1.0);
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final PVectorM2F<T> v1 = this.newVectorM2F(x1, y1);

      final PVectorM2F<T> vr0 = this.newVectorM2F();
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

  @Override @Test public void testAddMutation()
  {
    final PVectorM2F<T> out = this.newVectorM2F();
    final PVectorM2F<T> v0 = this.newVectorM2F(1.0f, 1.0f);
    final PVectorM2F<T> v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final PVectorM2F<T> ov0 = PVectorM2F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final PVectorM2F<T> ov1 = PVectorM2F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final PVectorM2F<T> v1 = this.newVectorM2F(x1, y1);

      final float r = (float) (Math.random() * max);

      final PVectorM2F<T> vr0 = this.newVectorM2F();
      PVectorM2F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getXF(), v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, vr0.getYF(), v0.getYF() + (v1.getYF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        PVectorM2F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getXF(), orig_x + (v1.getXF() * r)));
        Assert.assertTrue(
          AlmostEqualFloat.almostEqual(
            ec, v0.getYF(), orig_y + (v1.getYF() * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(PVectorM2F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);
      final PVectorM2F<T> v1 = this.newVectorM2F(x0, y0);
      final PVectorM2F<T> v2 = this.newVectorM2F(x0, y0);

      Assert.assertTrue(PVectorM2F.almostEqual(ec, v0, v1));
      Assert.assertTrue(PVectorM2F.almostEqual(ec, v1, v2));
      Assert.assertTrue(PVectorM2F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorM2F<T> v0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> v1 = this.newVectorM2F(x, y);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorM2F<T> v0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> v1 = this.newVectorM2F(y, -x);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorM2F<T> v0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> v1 = this.newVectorM2F(-y, x);
      PVectorM2F.normalizeInPlace(v0);
      PVectorM2F.normalizeInPlace(v1);
      final double angle = PVectorM2F.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(
        AlmostEqualDouble.almostEqual(
          ec, angle, Math.toRadians(90)));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final PVectorM2F<T> v = this.newVectorM2F(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override @Test public void testClampByPVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> maximum = this.newVectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
      final PVectorM2F<T> vo = PVectorM2F.clampMaximumByPVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());

      {
        final PVectorM2F<T> vr0 =
          PVectorM2F.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
      }
    }
  }

  @Override @Test public void testClampByPVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> minimum = this.newVectorM2F(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
      final PVectorM2F<T> vo = PVectorM2F.clampMinimumByPVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final PVectorM2F<T> vr0 =
          PVectorM2F.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public void testClampByPVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> minimum = this.newVectorM2F(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> maximum = this.newVectorM2F(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
      final PVectorM2F<T> vo =
        PVectorM2F.clampByPVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final PVectorM2F<T> vr0 =
          PVectorM2F.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
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

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
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

  @Override @Test public void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
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

  @Override @Test public void testCopy()
  {
    final PVectorM2F<T> vb = this.newVectorM2F(5, 6);
    final PVectorM2F<T> va = this.newVectorM2F(1, 2);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());

    PVectorM2F.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
  }

  @Override @Test public void testCopy2Correct()
  {
    final PVectorM2F<T> v0 = this.newVectorM2F(
      (float) Math.random() * Float.MAX_VALUE,
      (float) Math.random() * Float.MAX_VALUE);
    final PVectorM2F<T> v1 = this.newVectorM2F();
    final PVectorM2F<T> v2 = this.newVectorM2F();

    v1.copyFrom2F(v0);

    Assert.assertEquals(v0.getXF(), v1.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v1.getYF(), 0.0f);

    v2.copyFromTyped2F(v0);

    Assert.assertEquals(v0.getXF(), v2.getXF(), 0.0f);
    Assert.assertEquals(v0.getYF(), v2.getYF(), 0.0f);
  }

  @Override @Test public void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(
      PVectorM2F.almostEqual(
        ec, this.newVectorM2F(), this.newVectorM2F(0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();

    final PVectorM2F<T> v0 = this.newVectorM2F(0.0f, 1.0f);
    final PVectorM2F<T> v1 = this.newVectorM2F(0.0f, 0.0f);
    Assert.assertTrue(
      AlmostEqualDouble.almostEqual(
        ec, PVectorM2F.distance(c, v0, v1), 1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v1 = this.newVectorM2F(x1, y1);

      Assert.assertTrue(PVectorM2F.distance(c, v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final PVectorM2F<T> v0 = this.newVectorM2F(10.0f, 10.0f);
    final PVectorM2F<T> v1 = this.newVectorM2F(10.0f, 10.0f);

    {
      final float p = PVectorM2F.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = PVectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = PVectorM2F.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final PVectorM2F<T> vpx = this.newVectorM2F(1.0f, 0.0f);
    final PVectorM2F<T> vmx = this.newVectorM2F(-1.0f, 0.0f);

    final PVectorM2F<T> vpy = this.newVectorM2F(0.0f, 1.0f);
    final PVectorM2F<T> vmy = this.newVectorM2F(0.0f, -1.0f);

    Assert.assertTrue(PVectorM2F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(PVectorM2F.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final PVectorM2F<T> q = this.newVectorM2F(x, y);
      final float dp = PVectorM2F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final PVectorM2F<T> v0 = this.newVectorM2F(10.0f, 10.0f);

    {
      final double p = PVectorM2F.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = PVectorM2F.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      final PVectorM2F<T> m1 = this.newVectorM2F();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F(x, y);
      final PVectorM2F<T> m1 = this.newVectorM2F(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final PVectorM2F<T> m0 = this.newVectorM2F();
    final PVectorM2F<T> m1 = this.newVectorM2F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      final PVectorM2F<T> m1 = this.newVectorM2F();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final PVectorM2F<T> m0 = this.newVectorM2F();
      final PVectorM2F<T> m1 = this.newVectorM2F();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final PVectorM2F<T> v0 = this.newVectorM2F(1.0f, 2.0f);
    final PVectorM2F<T> v1 = new PVectorM2F<T>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v1 = this.newVectorM2F(x1, y1);

      final PVectorM2F<T> vr0 = this.newVectorM2F();
      final PVectorM2F<T> vr1 = this.newVectorM2F();
      PVectorM2F.interpolateLinear(c, v0, v1, 0.0f, vr0);
      PVectorM2F.interpolateLinear(c, v0, v1, 1.0f, vr1);

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

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final double m = PVectorM2F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x = (float) (Math.random() * max);
      final float y = (float) (Math.random() * max);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();
      PVectorM2F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = PVectorM2F.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM2F<T> v = this.newVectorM2F(0.0f, 0.0f);
    final PVectorM2F<T> vr = PVectorM2F.normalizeInPlace(v);
    final double m = PVectorM2F.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0f));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM2F<T> v = this.newVectorM2F(1.0f, 0.0f);
    final double m = PVectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0f));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final PVectorM2F<T> v = this.newVectorM2F(8.0f, 0.0f);

    {
      final double p = PVectorM2F.dotProduct(v, v);
      final double q = PVectorM2F.magnitudeSquared(v);
      final double r = PVectorM2F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final PVectorM2F<T> v = this.newVectorM2F(0.0f, 0.0f);
    final double m = PVectorM2F.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final PVectorM2F<T> v0 = this.newVectorM2F(8.0f, 0.0f);
    final PVectorM2F<T> out = this.newVectorM2F();
    final PVectorM2F<T> vr = PVectorM2F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = PVectorM2F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final PVectorM2F<T> qr = this.newVectorM2F();
    final PVectorM2F<T> q = this.newVectorM2F(0, 0);
    PVectorM2F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override @Test public void testOrthonormalize()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    final PVectorM2F<T> v0 = this.newVectorM2F(0, 1);
    final PVectorM2F<T> v0_out = this.newVectorM2F();
    final PVectorM2F<T> v1 = this.newVectorM2F(0.5f, 0.5f);
    final PVectorM2F<T> v1_out = this.newVectorM2F();

    PVectorM2F.orthoNormalize(c, v0, v0_out, v1, v1_out);

    Assert.assertEquals(this.newVectorM2F(0, 1), v0_out);
    Assert.assertEquals(this.newVectorM2F(1, 0), v1_out);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final PVectorM2F.ContextPVM2F c = new PVectorM2F.ContextPVM2F();
    final PVectorM2F<T> v0 = this.newVectorM2F(0f, 1f);
    final PVectorM2F<T> v1 = this.newVectorM2F(0.5f, 0.5f);

    PVectorM2F.orthoNormalizeInPlace(c, v0, v1);

    Assert.assertEquals(this.newVectorM2F(0, 1), v0);
    Assert.assertEquals(this.newVectorM2F(1, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final PVectorM2F<T> p = this.newVectorM2F(1.0f, 0.0f);
      final PVectorM2F<T> q = this.newVectorM2F(0.0f, 1.0f);
      final PVectorM2F<T> r = this.newVectorM2F();
      final PVectorM2F<T> u = PVectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM2F.magnitude(u) == 0.0);
    }

    {
      final PVectorM2F<T> p = this.newVectorM2F(-1.0f, 0.0f);
      final PVectorM2F<T> q = this.newVectorM2F(0.0f, 1.0f);
      final PVectorM2F<T> r = this.newVectorM2F();
      final PVectorM2F<T> u = PVectorM2F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(PVectorM2F.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final PVectorM2F<T> out = this.newVectorM2F();
    final PVectorM2F<T> v0 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final PVectorM2F<T> ov0 = PVectorM2F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0f);
    Assert.assertTrue(out.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final PVectorM2F<T> ov1 = PVectorM2F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0f);
    Assert.assertTrue(ov1.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 2.0f);
    Assert.assertTrue(v0.getYF() == 2.0f);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();

      PVectorM2F.scale(v, 1.0f, vr);

      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getXF(), vr.getXF()));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          ec, v.getYF(), vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        PVectorM2F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v = this.newVectorM2F(x, y);

      final PVectorM2F<T> vr = this.newVectorM2F();

      PVectorM2F.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getXF(), 0.0f));
      Assert.assertTrue(
        AlmostEqualFloat.almostEqual(
          context, vr.getYF(), 0.0f));

      {
        PVectorM2F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final PVectorM2F<T> v = this.newVectorM2F(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[PVectorM2F 0.0 1.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v0 = this.newVectorM2F(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final PVectorM2F<T> v1 = this.newVectorM2F(x1, y1);

      final PVectorM2F<T> vr0 = this.newVectorM2F();
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

  protected abstract <T> PVectorM2F<T> newVectorM2F(
    final float x1,
    final float y1);

  @Override @Test public void testSubtractMutation()
  {
    final PVectorM2F<T> out = this.newVectorM2F();
    final PVectorM2F<T> v0 = this.newVectorM2F(1.0f, 1.0f);
    final PVectorM2F<T> v1 = this.newVectorM2F(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final PVectorM2F<T> ov0 = PVectorM2F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final PVectorM2F<T> ov1 = PVectorM2F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0f);
    Assert.assertTrue(ov1.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 0.0f);
    Assert.assertTrue(v0.getYF() == 0.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
  }
}
