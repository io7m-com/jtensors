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
import com.io7m.jaux.AlmostEqualFloat.ContextRelative;
import com.io7m.jaux.functional.Pair;

public class VectorM3FTest extends VectorM3Contract
{
  @Override @Test public void testAbsolute()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.absolute(v, vr);

      Assert
        .assertTrue(AlmostEqualFloat.almostEqual(ec, Math.abs(v.x), vr.x));
      Assert
        .assertTrue(AlmostEqualFloat.almostEqual(ec, Math.abs(v.y), vr.y));
      Assert
        .assertTrue(AlmostEqualFloat.almostEqual(ec, Math.abs(v.z), vr.z));
    }
  }

  @Override @Test public void testAbsoluteMutation()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final float orig_x = v.x;
      final float orig_y = v.y;
      final float orig_z = v.z;

      VectorM3F.absoluteInPlace(v);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_x),
        v.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_y),
        v.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(
        ec,
        Math.abs(orig_z),
        v.z));
    }
  }

  @Override @Test public void testAdd()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.add(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x + v1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y + v1.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.z, v0.z + v1.z));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.addInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          + v1.x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          + v1.y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.z, orig_z
          + v1.z));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov0 = VectorM3F.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov1 = VectorM3F.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
  }

  @Override @Test public void testAddScaled()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max = 1000.0f;

      final float x0 = (float) (Math.random() * max);
      final float y0 = (float) (Math.random() * max);
      final float z0 = (float) (Math.random() * max);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * max);
      final float y1 = (float) (Math.random() * max);
      final float z1 = (float) (Math.random() * max);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final float r = (float) (Math.random() * max);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x
        + (v1.x * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y
        + (v1.y * r)));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.z, v0.z
        + (v1.z * r)));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          + (v1.x * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          + (v1.y * r)));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.z, orig_z
          + (v1.z * r)));
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
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, z);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, q, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, y, q);
      Assert.assertFalse(VectorM3F.almostEqual(ec, m0, m1));
    }
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);
      final VectorM3F v1 = new VectorM3F(x0, y0, z0);
      final VectorM3F v2 = new VectorM3F(x0, y0, z0);

      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v1));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v1, v2));
      Assert.assertTrue(VectorM3F.almostEqual(ec, v0, v2));
    }
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM3F v = new VectorM3F(3.0f, 5.0f, 7.0f);

    Assert.assertTrue(v.x == v.getXF());
    Assert.assertTrue(v.y == v.getYF());
    Assert.assertTrue(v.z == v.getZF());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float max_x = (float) (Math.random() * Float.MIN_VALUE);
      final float max_y = (float) (Math.random() * Float.MIN_VALUE);
      final float max_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F maximum = new VectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MAX_VALUE);
      final float min_y = (float) (Math.random() * Float.MAX_VALUE);
      final float min_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F minimum = new VectorM3F(min_x, min_y, min_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float min_x = (float) (Math.random() * Float.MIN_VALUE);
      final float min_y = (float) (Math.random() * Float.MIN_VALUE);
      final float min_z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F minimum = new VectorM3F(min_x, min_y, min_z);

      final float max_x = (float) (Math.random() * Float.MAX_VALUE);
      final float max_y = (float) (Math.random() * Float.MAX_VALUE);
      final float max_z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F maximum = new VectorM3F(max_x, max_y, max_z);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      final VectorM3F vo = VectorM3F.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.x <= maximum.x);
      Assert.assertTrue(vr.y <= maximum.y);
      Assert.assertTrue(vr.z <= maximum.z);

      Assert.assertTrue(vr.x >= minimum.x);
      Assert.assertTrue(vr.y >= minimum.y);
      Assert.assertTrue(vr.z >= minimum.z);

      {
        final VectorM3F vr0 =
          VectorM3F.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.x <= maximum.x);
        Assert.assertTrue(v.y <= maximum.y);
        Assert.assertTrue(v.z <= maximum.z);

        Assert.assertTrue(v.x >= minimum.x);
        Assert.assertTrue(v.y >= minimum.y);
        Assert.assertTrue(v.z >= minimum.z);
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float maximum = (float) (Math.random() * Float.MIN_VALUE);

      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.z <= maximum);

      {
        VectorM3F.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.z <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float minimum = (float) (Math.random() * Float.MAX_VALUE);

      final float x = (float) (Math.random() * Float.MIN_VALUE);
      final float y = (float) (Math.random() * Float.MIN_VALUE);
      final float z = (float) (Math.random() * Float.MIN_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3F.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z >= minimum);
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
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.x <= maximum);
      Assert.assertTrue(vr.x >= minimum);
      Assert.assertTrue(vr.y <= maximum);
      Assert.assertTrue(vr.y >= minimum);
      Assert.assertTrue(vr.z <= maximum);
      Assert.assertTrue(vr.z >= minimum);

      {
        VectorM3F.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.x <= maximum);
        Assert.assertTrue(v.x >= minimum);
        Assert.assertTrue(v.y <= maximum);
        Assert.assertTrue(v.y >= minimum);
        Assert.assertTrue(v.z <= maximum);
        Assert.assertTrue(v.z >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM3F vb = new VectorM3F(5, 6, 7);
    final VectorM3F va = new VectorM3F(1, 2, 3);

    Assert.assertFalse(va.x == vb.x);
    Assert.assertFalse(va.y == vb.y);
    Assert.assertFalse(va.z == vb.z);

    VectorM3F.copy(va, vb);

    Assert.assertTrue(va.x == vb.x);
    Assert.assertTrue(va.y == vb.y);
    Assert.assertTrue(va.z == vb.z);
  }

  @Override @Test public void testCrossProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext3dp();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) Math.random();
      final float y0 = (float) Math.random();
      final float z0 = (float) Math.random();
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);
      VectorM3F.normalizeInPlace(v0);

      final float x1 = (float) Math.random();
      final float y1 = (float) Math.random();
      final float z1 = (float) Math.random();
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);
      VectorM3F.normalizeInPlace(v1);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.crossProduct(v0, v1, vr);
      VectorM3F.normalizeInPlace(vr);

      final double dp0 = VectorM3F.dotProduct(v0, vr);
      final double dp1 = VectorM3F.dotProduct(v1, vr);

      System.out.println("v0      : " + v0);
      System.out.println("mag(v0) : " + VectorM3F.magnitude(v0));
      System.out.println("v1      : " + v1);
      System.out.println("mag(v1) : " + VectorM3F.magnitude(v1));
      System.out.println("vr      : " + vr);
      System.out.println("mag(vr) : " + VectorM3F.magnitude(vr));
      System.out.println("dp0     : " + dp0);
      System.out.println("dp1     : " + dp1);

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp0, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, dp1, 0.0));
    }
  }

  @Override @Test public void testDefault000()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();
    Assert.assertTrue(VectorM3F.almostEqual(
      ec,
      new VectorM3F(),
      new VectorM3F(0, 0, 0)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM3F v0 = new VectorM3F(0.0f, 1.0f, 0.0f);
    final VectorM3F v1 = new VectorM3F(0.0f, 0.0f, 0.0f);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(
      context,
      VectorM3F.distance(v0, v1),
      1.0f));
  }

  @Override @Test public void testDistanceOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      Assert.assertTrue(VectorM3F.distance(v0, v1) >= 0.0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM3F v0 = new VectorM3F(10.0f, 10.0f, 10.0f);
    final VectorM3F v1 = new VectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v1);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.dotProduct(v1, v1);
      Assert.assertTrue(v1.x == 10.0);
      Assert.assertTrue(v1.y == 10.0);
      Assert.assertTrue(v1.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final VectorM3F vpx = new VectorM3F(1.0f, 0.0f, 0.0f);
    final VectorM3F vmx = new VectorM3F(-1.0f, 0.0f, 0.0f);

    final VectorM3F vpy = new VectorM3F(0.0f, 1.0f, 0.0f);
    final VectorM3F vmy = new VectorM3F(0.0f, -1.0f, 0.0f);

    final VectorM3F vpz = new VectorM3F(0.0f, 0.0f, 1.0f);
    final VectorM3F vmz = new VectorM3F(0.0f, 0.0f, -1.0f);

    Assert.assertTrue(VectorM3F.dotProduct(vpx, vpy) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vpy, vpz) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vmx, vmy) == 0.0);
    Assert.assertTrue(VectorM3F.dotProduct(vmy, vmz) == 0.0);
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) Math.random();
      final float y = (float) Math.random();
      final float z = (float) Math.random();
      final VectorM3F q = new VectorM3F(x, y, z);
      final double dp = VectorM3F.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM3F v0 = new VectorM3F(10.0f, 10.0f, 10.0f);

    {
      final double p = VectorM3F.dotProduct(v0, v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }

    {
      final double p = VectorM3F.magnitudeSquared(v0);
      Assert.assertTrue(v0.x == 10.0);
      Assert.assertTrue(v0.y == 10.0);
      Assert.assertTrue(v0.z == 10.0);
      Assert.assertTrue(p == 300.0);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM3F m0 = new VectorM3F();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM3F m0 = new VectorM3F();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3F m0 = new VectorM3F();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3F m0 = new VectorM3F();
      final VectorM3F m1 = new VectorM3F();
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
      final VectorM3F m0 = new VectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, y, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, z);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM3F m0 = new VectorM3F(x, y, z);
      final VectorM3F m1 = new VectorM3F(x, y, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM3F m0 = new VectorM3F();
    final VectorM3F m1 = new VectorM3F();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM3F m0 = new VectorM3F();
      final VectorM3F m1 = new VectorM3F();
      m1.x = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3F m0 = new VectorM3F();
      final VectorM3F m1 = new VectorM3F();
      m1.y = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM3F m0 = new VectorM3F();
      final VectorM3F m1 = new VectorM3F();
      m1.z = 23;
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM3F v0 = new VectorM3F(1.0f, 2.0f, 3.0f);
    final VectorM3F v1 = new VectorM3F(v0);

    Assert.assertTrue(v0.x == v1.x);
    Assert.assertTrue(v0.y == v1.y);
    Assert.assertTrue(v0.z == v1.z);
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      final VectorM3F vr1 = new VectorM3F();
      VectorM3F.interpolateLinear(v0, v1, 0.0f, vr0);
      VectorM3F.interpolateLinear(v0, v1, 1.0f, vr1);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, vr0.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, vr0.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.z, vr0.z));

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v1.x, vr1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v1.y, vr1.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v1.z, vr1.z));
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final double m = VectorM3F.magnitude(v);
      Assert.assertTrue(m >= 1.0);
    }
  }

  @Override @Test public void testMagnitudeNormal()
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
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();
      VectorM3F.normalize(v, vr);
      Assert.assertNotSame(v, vr);

      final double m = VectorM3F.magnitude(vr);

      System.out.println("m : " + m);
      Assert.assertTrue(AlmostEqualDouble.almostEqual(context_d, m, 1.0));
    }
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    final VectorM3F vr = VectorM3F.normalizeInPlace(v);
    final double m = VectorM3F.magnitude(vr);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorM3F v = new VectorM3F(1.0f, 0.0f, 0.0f);
    final double m = VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 1.0));
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM3F v = new VectorM3F(8.0f, 0.0f, 0.0f);

    {
      final double p = VectorM3F.dotProduct(v, v);
      final double q = VectorM3F.magnitudeSquared(v);
      final double r = VectorM3F.magnitude(v);
      Assert.assertTrue(p == 64.0);
      Assert.assertTrue(q == 64.0);
      Assert.assertTrue(r == 8.0);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM3F v = new VectorM3F(0.0f, 0.0f, 0.0f);
    final double m = VectorM3F.magnitude(v);
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    Assert.assertTrue(AlmostEqualDouble.almostEqual(context, m, 0.0));
  }

  @Override @Test public void testNormalizeSimple()
  {
    final VectorM3F v0 = new VectorM3F(8.0f, 0.0f, 0.0f);
    final VectorM3F out = new VectorM3F();
    final VectorM3F vr = VectorM3F.normalize(v0, out);

    Assert.assertTrue(vr == out);

    final double m = VectorM3F.magnitude(out);
    Assert.assertTrue(m == 1.0);
  }

  @Override @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    final VectorM3F qr = new VectorM3F();
    final VectorM3F q = new VectorM3F(0, 0, 0);
    VectorM3F.normalize(q, qr);

    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.x));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.y));
    Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, 0, qr.z));
  }

  @Override @Test public void testOrthonormalize()
  {
    final VectorM3F v0 = new VectorM3F(0, 1, 0);
    final VectorM3F v1 = new VectorM3F(0.5f, 0.5f, 0);

    final Pair<VectorM3F, VectorM3F> r = VectorM3F.orthoNormalize(v0, v1);

    Assert.assertEquals(new VectorM3F(0, 1, 0), r.first);
    Assert.assertEquals(new VectorM3F(1, 0, 0), r.second);
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    final VectorM3F v0 = new VectorM3F(0f, 1f, 0f);
    final VectorM3F v1 = new VectorM3F(0.5f, 0.5f, 0f);

    VectorM3F.orthoNormalizeInPlace(v0, v1);

    Assert.assertEquals(new VectorM3F(0, 1, 0), v0);
    Assert.assertEquals(new VectorM3F(1, 0, 0), v1);
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM3F p = new VectorM3F(1.0f, 0.0f, 0.0f);
      final VectorM3F q = new VectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = new VectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }

    {
      final VectorM3F p = new VectorM3F(-1.0f, 0.0f, 0.0f);
      final VectorM3F q = new VectorM3F(0.0f, 1.0f, 0.0f);
      final VectorM3F r = new VectorM3F();
      final VectorM3F u = VectorM3F.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM3F.magnitude(u) == 0.0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3F ov0 = VectorM3F.scale(v0, 2.0f, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 2.0);
    Assert.assertTrue(out.y == 2.0);
    Assert.assertTrue(out.z == 2.0);
    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    final VectorM3F ov1 = VectorM3F.scaleInPlace(v0, 2.0f);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 2.0);
    Assert.assertTrue(ov1.y == 2.0);
    Assert.assertTrue(ov1.z == 2.0);
    Assert.assertTrue(v0.x == 2.0);
    Assert.assertTrue(v0.y == 2.0);
    Assert.assertTrue(v0.z == 2.0);
  }

  @Override @Test public void testScaleOne()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();

      VectorM3F.scale(v, 1.0f, vr);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, vr.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, vr.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.z, vr.z));

      {
        final float orig_x = v.x;
        final float orig_y = v.y;
        final float orig_z = v.z;

        VectorM3F.scaleInPlace(v, 1.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, orig_x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, orig_y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.z, orig_z));
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
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v = new VectorM3F(x, y, z);

      final VectorM3F vr = new VectorM3F();

      VectorM3F.scale(v, 0.0f, vr);

      final ContextRelative context = new ContextRelative();
      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, vr.x, 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, vr.y, 0.0f));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(context, vr.z, 0.0f));

      {
        VectorM3F.scaleInPlace(v, 0.0f);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.x, 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.y, 0.0f));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v.z, 0.0f));
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM3F v = new VectorM3F(1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[VectorM3F 1.0 2.0 3.0]"));
  }

  @Override @Test public void testSubtract()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v0 = new VectorM3F(x0, y0, z0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final VectorM3F v1 = new VectorM3F(x1, y1, z1);

      final VectorM3F vr0 = new VectorM3F();
      VectorM3F.subtract(v0, v1, vr0);

      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.x, v0.x - v1.x));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.y, v0.y - v1.y));
      Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, vr0.z, v0.z - v1.z));

      {
        final float orig_x = v0.x;
        final float orig_y = v0.y;
        final float orig_z = v0.z;
        VectorM3F.subtractInPlace(v0, v1);

        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.x, orig_x
          - v1.x));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.y, orig_y
          - v1.y));
        Assert.assertTrue(AlmostEqualFloat.almostEqual(ec, v0.z, orig_z
          - v1.z));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM3F out = new VectorM3F();
    final VectorM3F v0 = new VectorM3F(1.0f, 1.0f, 1.0f);
    final VectorM3F v1 = new VectorM3F(1.0f, 1.0f, 1.0f);

    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);

    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov0 = VectorM3F.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.x == 0.0);
    Assert.assertTrue(out.y == 0.0);
    Assert.assertTrue(out.z == 0.0);

    Assert.assertTrue(v0.x == 1.0);
    Assert.assertTrue(v0.y == 1.0);
    Assert.assertTrue(v0.z == 1.0);

    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);

    final VectorM3F ov1 = VectorM3F.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.x == 0.0);
    Assert.assertTrue(ov1.y == 0.0);
    Assert.assertTrue(ov1.z == 0.0);

    Assert.assertTrue(v0.x == 0.0);
    Assert.assertTrue(v0.y == 0.0);
    Assert.assertTrue(v0.z == 0.0);

    Assert.assertTrue(v1.x == 1.0);
    Assert.assertTrue(v1.y == 1.0);
    Assert.assertTrue(v1.z == 1.0);
  }
}
