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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorM2FTTest extends VectorM2TContract
{
  @Override @Test public <A> void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      VectorM2FT.absolute(v, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getXF()),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(v.getYF()),
        vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2FT.absoluteInPlace(v);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_x),
          v.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(
          ec,
          Math.abs(orig_y),
          v.getYF()));
      }
    }
  }

  @Override @Test public <A> void testAbsoluteMutation()
  {
    final VectorM2FT<A> out = new VectorM2FT<A>();
    final VectorM2FT<A> v = new VectorM2FT<A>(-1.0f, -1.0f);

    Assert.assertTrue(v.getXF() == -1.0);
    Assert.assertTrue(v.getYF() == -1.0);

    final float vx = v.getXF();
    final float vy = v.getYF();

    final VectorM2FT<A> ov = VectorM2FT.absolute(v, out);

    Assert.assertTrue(vx == v.getXF());
    Assert.assertTrue(vy == v.getYF());
    Assert.assertTrue(vx == -1.0);
    Assert.assertTrue(vy == -1.0);

    Assert.assertTrue(out == ov);
    Assert.assertTrue(out.getXF() == 1.0);
    Assert.assertTrue(out.getYF() == 1.0);
  }

  @Override @Test public <A> void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 10000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x1, y1);

      final VectorM2FT<A> vr0 = new VectorM2FT<A>();
      VectorM2FT.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2FT.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + v1.getYF()));
      }
    }
  }

  @Override @Test public <A> void testAddMutation()
  {
    final VectorM2FT<A> out = new VectorM2FT<A>();
    final VectorM2FT<A> v0 = new VectorM2FT<A>(1.0f, 1.0f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0);
    Assert.assertTrue(out.getYF() == 0.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final VectorM2FT<A> ov0 = VectorM2FT.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0);
    Assert.assertTrue(out.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 1.0);
    Assert.assertTrue(v0.getYF() == 1.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);

    final VectorM2FT<A> ov1 = VectorM2FT.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0);
    Assert.assertTrue(ov1.getYF() == 2.0);
    Assert.assertTrue(v0.getXF() == 2.0);
    Assert.assertTrue(v0.getYF() == 2.0);
    Assert.assertTrue(v1.getXF() == 1.0);
    Assert.assertTrue(v1.getYF() == 1.0);
  }

  @Override @Test public <A> void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x1, y1);

      final float r = (float) (Math.random() * max);

      final VectorM2FT<A> vr0 = new VectorM2FT<A>();
      VectorM2FT.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() + (v1.getXF() * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() + (v1.getYF() * r)));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2FT.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          + (v1.getXF() * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          + (v1.getYF() * r)));
      }
    }
  }

  @Override @Test public <A> void testAlmostEqualNot()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(x, q);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, q);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(x, q);
      Assert.assertFalse(VectorM2FT.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public <A> void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x0, y0);
      final VectorM2FT<A> v2 = new VectorM2FT<A>(x0, y0);

      Assert.assertTrue(VectorM2FT.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM2FT.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM2FT.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public <A> void testAngle()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x, y);
      VectorM2FT.normalizeInPlace(v0);
      VectorM2FT.normalizeInPlace(v1);
      final double angle = VectorM2FT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, angle, 0.0));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(y, -x);
      VectorM2FT.normalizeInPlace(v0);
      VectorM2FT.normalizeInPlace(v1);
      final double angle = VectorM2FT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }

    {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(-y, x);
      VectorM2FT.normalizeInPlace(v0);
      VectorM2FT.normalizeInPlace(v1);
      final double angle = VectorM2FT.angle(v0, v1);

      System.out.println("v0    : " + v0);
      System.out.println("v1    : " + v1);
      System.out.println("angle : " + angle);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(
        ec,
        angle,
        Math.toRadians(90)));
    }
  }

  @Override @Test public <A> void testCheckInterface()
  {
    final VectorM2FT<A> v = new VectorM2FT<A>(3.0f, 5.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
  }

  @Override @Test public <A> void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> maximum = new VectorM2FT<A>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      final VectorM2FT<A> vo =
        VectorM2FT.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());

      {
        final VectorM2FT<A> vr0 =
          VectorM2FT.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> minimum = new VectorM2FT<A>(min_x, min_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      final VectorM2FT<A> vo =
        VectorM2FT.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final VectorM2FT<A> vr0 =
          VectorM2FT.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public <A> void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> minimum = new VectorM2FT<A>(min_x, min_y);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> maximum = new VectorM2FT<A>(max_x, max_y);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      final VectorM2FT<A> vo =
        VectorM2FT.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXF() <= maximum.getXF());
      Assert.assertTrue(vr.getYF() <= maximum.getYF());
      Assert.assertTrue(vr.getXF() >= minimum.getXF());
      Assert.assertTrue(vr.getYF() >= minimum.getYF());

      {
        final VectorM2FT<A> vr0 =
          VectorM2FT.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXF() <= maximum.getXF());
        Assert.assertTrue(v.getYF() <= maximum.getYF());
        Assert.assertTrue(v.getXF() >= minimum.getXF());
        Assert.assertTrue(v.getYF() >= minimum.getYF());
      }
    }
  }

  @Override @Test public <A> void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      VectorM2FT.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getYF() <= maximum);

      {
        VectorM2FT.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getYF() <= maximum);
      }
    }
  }

  @Override @Test public <A> void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      VectorM2FT.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2FT.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MIN_VALUE);
      final float maximum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      VectorM2FT.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXF() <= maximum);
      Assert.assertTrue(vr.getXF() >= minimum);
      Assert.assertTrue(vr.getYF() <= maximum);
      Assert.assertTrue(vr.getYF() >= minimum);

      {
        VectorM2FT.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXF() <= maximum);
        Assert.assertTrue(v.getXF() >= minimum);
        Assert.assertTrue(v.getYF() <= maximum);
        Assert.assertTrue(v.getYF() >= minimum);
      }
    }
  }

  @Override @Test public <A> void testCopy()
  {
    final VectorM2FT<A> vb = new VectorM2FT<A>(5, 6);
    final VectorM2FT<A> va = new VectorM2FT<A>(1, 2);

    Assert.assertFalse(va.getXF() == vb.getXF());
    Assert.assertFalse(va.getYF() == vb.getYF());

    VectorM2FT.copy(va, vb);

    Assert.assertTrue(va.getXF() == vb.getXF());
    Assert.assertTrue(va.getYF() == vb.getYF());
  }

  @Override @Test public <A> void testDefault00()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(VectorM2FT.almostEqual(
      ec,
      new VectorM2FT<A>(),
      new VectorM2FT<A>(0, 0)));
  }

  @Override @Test public <A> void testDistance()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2FT<A> v0 = new VectorM2FT<A>(0.0f, 1.0f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(0.0f, 0.0f);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      ec,
      VectorM2FT.distance(v0, v1),
      1.0f));
  }

  @Override @Test public <A> void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x1, y1);

      Assert.assertTrue(VectorM2FT.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public <A> void testDotProduct()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(10.0f, 10.0f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(10.0f, 10.0f);

    {
      final float p = VectorM2FT.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final float p = VectorM2FT.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXF() == 10.0f);
      Assert.assertTrue(v1.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public <A> void testDotProductPerpendicular()
  {
    final VectorM2FT<A> vpx = new VectorM2FT<A>(1.0f, 0.0f);
    final VectorM2FT<A> vmx = new VectorM2FT<A>(-1.0f, 0.0f);

    final VectorM2FT<A> vpy = new VectorM2FT<A>(0.0f, 1.0f);
    final VectorM2FT<A> vmy = new VectorM2FT<A>(0.0f, -1.0f);

    Assert.assertTrue(VectorM2FT.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM2FT.dotProduct(vmx, vmy) == 0.0);
  }

  @Override @Test public <A> void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final VectorM2FT<A> q = new VectorM2FT<A>(x, y);
      final float dp = VectorM2FT.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public <A> void testDotProductSelfMagnitudeSquared()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(10.0f, 10.0f);

    {
      final double p = VectorM2FT.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }

    {
      final double p = VectorM2FT.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXF() == 10.0f);
      Assert.assertTrue(v0.getYF() == 10.0f);
      Assert.assertTrue(p == 200.0f);
    }
  }

  @Override @Test public <A> void testEqualsCorrect()
  {
    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      final VectorM2FT<A> m1 = new VectorM2FT<A>();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testEqualsNotEqualCorrect()
  {
    final float x = (float) Math.random();
    final float y = x + 1.0f;
    final float z = y + 1.0f;
    final float w = z + 1.0f;
    final float q = w + 1.0f;

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, y);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>(x, y);
      final VectorM2FT<A> m1 = new VectorM2FT<A>(x, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public <A> void testHashCodeEqualsCorrect()
  {
    final VectorM2FT<A> m0 = new VectorM2FT<A>();
    final VectorM2FT<A> m1 = new VectorM2FT<A>();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public <A> void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      final VectorM2FT<A> m1 = new VectorM2FT<A>();
      m1.setXF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM2FT<A> m0 = new VectorM2FT<A>();
      final VectorM2FT<A> m1 = new VectorM2FT<A>();
      m1.setYF(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public <A> void testInitializeReadable()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(1.0f, 2.0f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
  }

  @Override @Test public <A> void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x1, y1);

      final VectorM2FT<A> vr0 = new VectorM2FT<A>();
      final VectorM2FT<A> vr1 = new VectorM2FT<A>();
      VectorM2FT.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM2FT.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getXF(),
        vr0.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v0.getYF(),
        vr0.getYF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getXF(),
        vr1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v1.getYF(),
        vr1.getYF()));
    }
  }

  @Override @Test public <A> void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final double m = VectorM2FT.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public <A> void testMagnitudeNormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext6dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 20000.0f;
      final float x = (float) (Math.random() * max);
      final float y = (float) (Math.random() * max);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();
      VectorM2FT.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM2FT.magnitude(vr);

      System.out.println("v  : " + v);
      System.out.println("vr : " + vr);
      System.out.println("m  : " + m);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0));
    }
  }

  @Override @Test public <A> void testMagnitudeNormalizeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2FT<A> v = new VectorM2FT<A>(0.0f, 0.0f);
    final VectorM2FT<A> vr = VectorM2FT.normalizeInPlace(v);
    final double m = VectorM2FT.magnitude(vr);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0f));
  }

  @Override @Test public <A> void testMagnitudeOne()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2FT<A> v = new VectorM2FT<A>(1.0f, 0.0f);
    final double m = VectorM2FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 1.0f));
  }

  @Override @Test public <A> void testMagnitudeSimple()
  {
    final VectorM2FT<A> v = new VectorM2FT<A>(8.0f, 0.0f);

    {
      final double p = VectorM2FT.dotProduct(v, v);
      final double q = VectorM2FT.magnitudeSquared(v);
      final double r = VectorM2FT.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public <A> void testMagnitudeZero()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    final VectorM2FT<A> v = new VectorM2FT<A>(0.0f, 0.0f);
    final double m = VectorM2FT.magnitude(v);
    Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, m, 0.0));
  }

  @Override @Test public <A> void testNormalizeSimple()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(8.0f, 0.0f);
    final VectorM2FT<A> out = new VectorM2FT<A>();
    final VectorM2FT<A> vr = VectorM2FT.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM2FT.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public <A> void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM2FT<A> qr = new VectorM2FT<A>();
    final VectorM2FT<A> q = new VectorM2FT<A>(0, 0);
    VectorM2FT.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getXF()));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.getYF()));
  }

  @Override @Test public <A> void testOrthonormalize()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(0, 1);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(0.5f, 0.5f);

    final Pair<VectorM2FT<A>, VectorM2FT<A>> r =
      VectorM2FT.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM2FT<A>(0, 1), r.first);
    Assert.assertEquals(new VectorM2FT<A>(1, 0), r.second);
  }

  @Override @Test public <A> void testOrthonormalizeMutation()
  {
    final VectorM2FT<A> v0 = new VectorM2FT<A>(0f, 1f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(0.5f, 0.5f);

    VectorM2FT.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM2FT<A>(0, 1), v0);
    Assert.assertEquals(new VectorM2FT<A>(1, 0), v1);
  }

  @Override @Test public <A> void testProjectionPerpendicularZero()
  {
    {
      final VectorM2FT<A> p = new VectorM2FT<A>(1.0f, 0.0f);
      final VectorM2FT<A> q = new VectorM2FT<A>(0.0f, 1.0f);
      final VectorM2FT<A> r = new VectorM2FT<A>();
      final VectorM2FT<A> u = VectorM2FT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2FT.magnitude(u) == 0.0);
    }

    {
      final VectorM2FT<A> p = new VectorM2FT<A>(-1.0f, 0.0f);
      final VectorM2FT<A> q = new VectorM2FT<A>(0.0f, 1.0f);
      final VectorM2FT<A> r = new VectorM2FT<A>();
      final VectorM2FT<A> u = VectorM2FT.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM2FT.magnitude(u) == 0.0);
    }
  }

  @Override @Test public <A> void testScaleMutation()
  {
    final VectorM2FT<A> out = new VectorM2FT<A>();
    final VectorM2FT<A> v0 = new VectorM2FT<A>(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final VectorM2FT<A> ov0 = VectorM2FT.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 2.0f);
    Assert.assertTrue(out.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);

    final VectorM2FT<A> ov1 = VectorM2FT.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 2.0f);
    Assert.assertTrue(ov1.getYF() == 2.0f);
    Assert.assertTrue(v0.getXF() == 2.0f);
    Assert.assertTrue(v0.getYF() == 2.0f);
  }

  @Override @Test public <A> void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();

      VectorM2FT.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getXF(),
        vr.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        v.getYF(),
        vr.getYF()));

      {
        final float orig_x = v.getXF();
        final float orig_y = v.getYF();

        VectorM2FT.scaleInPlace(v, 1.0f);

        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), orig_x));
        Assert
          .assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), orig_y));
      }
    }
  }

  @Override @Test public <A> void testScaleZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v = new VectorM2FT<A>(x, y);

      final VectorM2FT<A> vr = new VectorM2FT<A>();

      VectorM2FT.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getXF(),
        0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        context,
        vr.getYF(),
        0.0f));

      {
        VectorM2FT.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getXF(), 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.getYF(), 0.0f));
      }
    }
  }

  @Override @Test public <A> void testString()
  {
    final VectorM2FT<A> v = new VectorM2FT<A>(0.0f, 1.0f);
    Assert.assertTrue(v.toString().equals("[VectorM2FT 0.0 1.0]"));
  }

  @Override @Test public <A> void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v0 = new VectorM2FT<A>(x0, y0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM2FT<A> v1 = new VectorM2FT<A>(x1, y1);

      final VectorM2FT<A> vr0 = new VectorM2FT<A>();
      VectorM2FT.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        vr0.getYF(),
        v0.getYF() - v1.getYF()));

      {
        final float orig_x = v0.getXF();
        final float orig_y = v0.getYF();
        VectorM2FT.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getXF(), orig_x
          - v1.getXF()));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.getYF(), orig_y
          - v1.getYF()));
      }
    }
  }

  @Override @Test public <A> void testSubtractMutation()
  {
    final VectorM2FT<A> out = new VectorM2FT<A>();
    final VectorM2FT<A> v0 = new VectorM2FT<A>(1.0f, 1.0f);
    final VectorM2FT<A> v1 = new VectorM2FT<A>(1.0f, 1.0f);

    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final VectorM2FT<A> ov0 = VectorM2FT.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXF() == 0.0f);
    Assert.assertTrue(out.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 1.0f);
    Assert.assertTrue(v0.getYF() == 1.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);

    final VectorM2FT<A> ov1 = VectorM2FT.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXF() == 0.0f);
    Assert.assertTrue(ov1.getYF() == 0.0f);
    Assert.assertTrue(v0.getXF() == 0.0f);
    Assert.assertTrue(v0.getYF() == 0.0f);
    Assert.assertTrue(v1.getXF() == 1.0f);
    Assert.assertTrue(v1.getYF() == 1.0f);
  }
}
