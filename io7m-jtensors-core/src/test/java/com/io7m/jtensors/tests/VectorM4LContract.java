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
import com.io7m.jtensors.VectorM4L;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4LContract extends VectorM4Contract
{
  public static long randomNegativeNumber()
  {
    return (long) (Math.random() * Long.MIN_VALUE);
  }

  public static long randomPositiveNumber()
  {
    return (long) (Math.random() * Long.MAX_VALUE);
  }

  public static long randomPositiveSmallNumber()
  {
    return (long) (Math.random() * (1 << 14));
  }

  @Override @Test public void testAbsolute()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final long w = (long) (Math.random() * Long.MIN_VALUE);
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      VectorM4L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
      Assert.assertEquals(Math.abs(v.getWL()), vr.getWL());
    }
  }

  protected abstract VectorM4L newVectorM4L(
    final long x,
    final long y,
    final long z,
    final long w);

  @Override @Test public void testAbsoluteMutation()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * Long.MIN_VALUE);
      final long y = (long) (Math.random() * Long.MIN_VALUE);
      final long z = (long) (Math.random() * Long.MIN_VALUE);
      final long w = (long) (Math.random() * Long.MIN_VALUE);
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      VectorM4L.absoluteInPlace(v);

      Assert.assertEquals(v.getXL(), Math.abs(x));
      Assert.assertEquals(v.getYL(), Math.abs(y));
      Assert.assertEquals(v.getZL(), Math.abs(z));
      Assert.assertEquals(v.getWL(), Math.abs(w));
    }
  }

  @Override @Test public void testAdd()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LContract.randomPositiveSmallNumber();
      final long y0 = VectorM4LContract.randomPositiveSmallNumber();
      final long z0 = VectorM4LContract.randomPositiveSmallNumber();
      final long w0 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LContract.randomPositiveSmallNumber();
      final long y1 = VectorM4LContract.randomPositiveSmallNumber();
      final long z1 = VectorM4LContract.randomPositiveSmallNumber();
      final long w1 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v1 = this.newVectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = this.newVectorM4L();
      VectorM4L.add(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + v1.getZL()));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() + v1.getWL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        VectorM4L.addInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x + v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y + v1.getYL()));
        Assert.assertTrue(v0.getZL() == (orig_z + v1.getZL()));
        Assert.assertTrue(v0.getWL() == (orig_w + v1.getWL()));
      }
    }
  }

  @Override @Test public void testAddMutation()
  {
    final VectorM4L out = this.newVectorM4L();
    final VectorM4L v0 = this.newVectorM4L(1, 1, 1, 1);
    final VectorM4L v1 = this.newVectorM4L(1, 1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(out.getWL() == 1);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);

    final VectorM4L ov0 = VectorM4L.add(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);
    Assert.assertTrue(out.getZL() == 2);
    Assert.assertTrue(out.getWL() == 2);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);

    final VectorM4L ov1 = VectorM4L.addInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);
    Assert.assertTrue(ov1.getZL() == 2);
    Assert.assertTrue(ov1.getWL() == 2);
    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);
    Assert.assertTrue(v0.getZL() == 2);
    Assert.assertTrue(v0.getWL() == 2);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);
  }

  @Override @Test public void testAddScaled()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LContract.randomPositiveSmallNumber();
      final long y0 = VectorM4LContract.randomPositiveSmallNumber();
      final long z0 = VectorM4LContract.randomPositiveSmallNumber();
      final long w0 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LContract.randomPositiveSmallNumber();
      final long y1 = VectorM4LContract.randomPositiveSmallNumber();
      final long z1 = VectorM4LContract.randomPositiveSmallNumber();
      final long w1 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v1 = this.newVectorM4L(x1, y1, z1, w1);

      final long r = VectorM4LContract.randomPositiveSmallNumber();

      final VectorM4L vr0 = this.newVectorM4L();
      VectorM4L.addScaled(v0, v1, r, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() + (v1.getXL() * r)));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() + (v1.getYL() * r)));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() + (v1.getZL() * r)));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() + (v1.getWL() * r)));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        VectorM4L.addScaledInPlace(v0, v1, r);

        Assert.assertTrue(v0.getXL() == (orig_x + (v1.getXL() * r)));
        Assert.assertTrue(v0.getYL() == (orig_y + (v1.getYL() * r)));
        Assert.assertTrue(v0.getZL() == (orig_z + (v1.getZL() * r)));
        Assert.assertTrue(v0.getWL() == (orig_w + (v1.getWL() * r)));
      }
    }
  }

  @Override @Test public void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testCheckInterface()
  {
    final VectorM4L v = this.newVectorM4L(3, 5, 7, 11);

    Assert.assertTrue(v.getXL() == v.getXL());
    Assert.assertTrue(v.getYL() == v.getYL());
    Assert.assertTrue(v.getZL() == v.getZL());
    Assert.assertTrue(v.getWL() == v.getWL());
  }

  @Override @Test public void testClampByVectorMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = VectorM4LContract.randomNegativeNumber();
      final long max_y = VectorM4LContract.randomNegativeNumber();
      final long max_z = VectorM4LContract.randomNegativeNumber();
      final long max_w = VectorM4LContract.randomNegativeNumber();
      final VectorM4L maximum = this.newVectorM4L(max_x, max_y, max_z, max_w);

      final long x = VectorM4LContract.randomNegativeNumber();
      final long y = VectorM4LContract.randomNegativeNumber();
      final long z = VectorM4LContract.randomNegativeNumber();
      final long w = VectorM4LContract.randomNegativeNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      final VectorM4L vo = VectorM4L.clampMaximumByVector(v, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());

      {
        final VectorM4L vr0 = VectorM4L.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
        Assert.assertTrue(v.getWL() <= maximum.getWL());
      }
    }
  }

  @Override @Test public void testClampByVectorMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM4LContract.randomPositiveNumber();
      final long min_y = VectorM4LContract.randomPositiveNumber();
      final long min_z = VectorM4LContract.randomPositiveNumber();
      final long min_w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L minimum = this.newVectorM4L(min_x, min_y, min_z, min_w);

      final long x = VectorM4LContract.randomNegativeNumber();
      final long y = VectorM4LContract.randomNegativeNumber();
      final long z = VectorM4LContract.randomNegativeNumber();
      final long w = VectorM4LContract.randomNegativeNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      final VectorM4L vo = VectorM4L.clampMinimumByVector(v, minimum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());

      {
        final VectorM4L vr0 = VectorM4L.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
        Assert.assertTrue(v.getWL() >= minimum.getWL());
      }
    }
  }

  @Override @Test public void testClampByVectorOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = VectorM4LContract.randomNegativeNumber();
      final long min_y = VectorM4LContract.randomNegativeNumber();
      final long min_z = VectorM4LContract.randomNegativeNumber();
      final long min_w = VectorM4LContract.randomNegativeNumber();
      final VectorM4L minimum = this.newVectorM4L(min_x, min_y, min_z, min_w);

      final long max_x = VectorM4LContract.randomPositiveNumber();
      final long max_y = VectorM4LContract.randomPositiveNumber();
      final long max_z = VectorM4LContract.randomPositiveNumber();
      final long max_w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L maximum = this.newVectorM4L(max_x, max_y, max_z, max_w);

      final long x = VectorM4LContract.randomNegativeNumber();
      final long y = VectorM4LContract.randomPositiveNumber();
      final long z = VectorM4LContract.randomPositiveNumber();
      final long w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      final VectorM4L vo = VectorM4L.clampByVector(v, minimum, maximum, vr);

      Assert.assertTrue(vo == vr);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());

      {
        final VectorM4L vr0 =
          VectorM4L.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertTrue(vr0 == v);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
        Assert.assertTrue(v.getWL() <= maximum.getWL());
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
        Assert.assertTrue(v.getWL() >= minimum.getWL());
      }
    }
  }

  @Override @Test public void testClampMaximumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = VectorM4LContract.randomNegativeNumber();

      final long x = VectorM4LContract.randomPositiveNumber();
      final long y = VectorM4LContract.randomPositiveNumber();
      final long z = VectorM4LContract.randomPositiveNumber();
      final long w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      VectorM4L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getWL() <= maximum);

      {
        VectorM4L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getZL() <= maximum);
        Assert.assertTrue(v.getWL() <= maximum);
      }
    }
  }

  @Override @Test public void testClampMinimumOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM4LContract.randomPositiveNumber();

      final long x = VectorM4LContract.randomNegativeNumber();
      final long y = VectorM4LContract.randomNegativeNumber();
      final long z = VectorM4LContract.randomNegativeNumber();
      final long w = VectorM4LContract.randomNegativeNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      VectorM4L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() >= minimum);

      {
        VectorM4L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() >= minimum);
        Assert.assertTrue(v.getWL() >= minimum);
      }
    }
  }

  @Override @Test public void testClampOrdering()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = VectorM4LContract.randomNegativeNumber();
      final long maximum = VectorM4LContract.randomPositiveNumber();

      final long x = VectorM4LContract.randomNegativeNumber();
      final long y = VectorM4LContract.randomPositiveNumber();
      final long z = VectorM4LContract.randomPositiveNumber();
      final long w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();
      VectorM4L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() <= maximum);
      Assert.assertTrue(vr.getWL() >= minimum);

      {
        VectorM4L.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() <= maximum);
        Assert.assertTrue(v.getZL() >= minimum);
        Assert.assertTrue(v.getWL() <= maximum);
        Assert.assertTrue(v.getWL() >= minimum);
      }
    }
  }

  @Override @Test public void testCopy()
  {
    final VectorM4L vb = this.newVectorM4L(5, 6, 7, 8);
    final VectorM4L va = this.newVectorM4L(1, 2, 3, 4);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());
    Assert.assertFalse(va.getWL() == vb.getWL());

    VectorM4L.copy(va, vb);

    Assert.assertTrue(va.getXL() == vb.getXL());
    Assert.assertTrue(va.getYL() == vb.getYL());
    Assert.assertTrue(va.getZL() == vb.getZL());
    Assert.assertTrue(va.getWL() == vb.getWL());
  }

  @Override @Test public void testCopy2Correct()
  {
    final VectorM4L v0 = this.newVectorM4L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final VectorM4L v1 = this.newVectorM4L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(0, v1.getZL());
    Assert.assertEquals(1, v1.getWL());
  }

  @Override @Test public void testCopy3Correct()
  {
    final VectorM4L v0 = this.newVectorM4L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final VectorM4L v1 = this.newVectorM4L();

    v1.copyFrom4L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());
    Assert.assertEquals(v0.getWL(), v1.getWL());
  }

  @Override @Test public void testCopy4Correct()
  {
    final VectorM4L v0 = this.newVectorM4L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final VectorM4L v1 = this.newVectorM4L();

    v1.copyFrom3L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());
    Assert.assertEquals(1, v1.getWL());
  }

  @Override @Test public void testDefault0001()
  {
    Assert.assertTrue(
      this.newVectorM4L()
        .equals(this.newVectorM4L(0, 0, 0, 1)));
  }

  @Override @Test public void testDistance()
  {
    final VectorM4L.ContextVM4L c = new VectorM4L.ContextVM4L();
    final VectorM4L v0 = this.newVectorM4L(0, 1, 0, 0);
    final VectorM4L v1 = this.newVectorM4L(0, 0, 0, 0);
    Assert.assertTrue(VectorM4L.distance(c, v0, v1) == 1);
  }

  @Override @Test public void testDistanceOrdering()
  {
    final VectorM4L.ContextVM4L c = new VectorM4L.ContextVM4L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LContract.randomPositiveSmallNumber();
      final long y0 = VectorM4LContract.randomPositiveSmallNumber();
      final long z0 = VectorM4LContract.randomPositiveSmallNumber();
      final long w0 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LContract.randomPositiveSmallNumber();
      final long y1 = VectorM4LContract.randomPositiveSmallNumber();
      final long z1 = VectorM4LContract.randomPositiveSmallNumber();
      final long w1 = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v1 = this.newVectorM4L(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4L.distance(c, v0, v1) >= 0);
    }
  }

  @Override @Test public void testDotProduct()
  {
    final VectorM4L v0 = this.newVectorM4L(10, 10, 10, 10);
    final VectorM4L v1 = this.newVectorM4L(10, 10, 10, 10);

    {
      final long p = VectorM4L.dotProduct(v0, v1);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(v1.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorM4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorM4L.dotProduct(v1, v1);
      Assert.assertTrue(v1.getXL() == 10);
      Assert.assertTrue(v1.getYL() == 10);
      Assert.assertTrue(v1.getZL() == 10);
      Assert.assertTrue(v1.getWL() == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final long z = (long) (Math.random() * max);
      final long w = (long) (Math.random() * max);
      final VectorM4L q = this.newVectorM4L(x, y, z, w);

      final double ms = VectorM4L.magnitudeSquared(q);
      final double dp = VectorM4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Override @Test public void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000;
      final long x = (long) (Math.random() * max);
      final long y = (long) (Math.random() * max);
      final long z = (long) (Math.random() * max);
      final long w = (long) (Math.random() * max);
      final VectorM4L q = this.newVectorM4L(x, y, z, w);
      final double dp = VectorM4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Override @Test public void testDotProductSelfMagnitudeSquared()
  {
    final VectorM4L v0 = this.newVectorM4L(10, 10, 10, 10);

    {
      final long p = VectorM4L.dotProduct(v0, v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }

    {
      final long p = VectorM4L.magnitudeSquared(v0);
      Assert.assertTrue(v0.getXL() == 10);
      Assert.assertTrue(v0.getYL() == 10);
      Assert.assertTrue(v0.getZL() == 10);
      Assert.assertTrue(v0.getWL() == 10);
      Assert.assertTrue(p == 400);
    }
  }

  @Override @Test public void testEqualsCorrect()
  {
    {
      final VectorM4L m0 = this.newVectorM4L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      final VectorM4L m1 = this.newVectorM4L();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Override @Test public void testEqualsNotEqualCorrect()
  {
    final long x = (long) (Math.random() * 1000);
    final long y = x + 1;
    final long z = y + 1;
    final long w = z + 1;
    final long q = w + 1;

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(Long.valueOf(23)));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final VectorM4L m0 = this.newVectorM4L(x, y, z, w);
      final VectorM4L m1 = this.newVectorM4L(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Override @Test public void testHashCodeEqualsCorrect()
  {
    final VectorM4L m0 = this.newVectorM4L();
    final VectorM4L m1 = this.newVectorM4L();
    Assert.assertEquals(m0.hashCode(), m1.hashCode());
  }

  @Override @Test public void testHashCodeNotEqualCorrect()
  {
    {
      final VectorM4L m0 = this.newVectorM4L();
      final VectorM4L m1 = this.newVectorM4L();
      m1.setXL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      final VectorM4L m1 = this.newVectorM4L();
      m1.setYL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      final VectorM4L m1 = this.newVectorM4L();
      m1.setZL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final VectorM4L m0 = this.newVectorM4L();
      final VectorM4L m1 = this.newVectorM4L();
      m1.setWL(23);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Override @Test public void testInitializeReadable()
  {
    final VectorM4L v0 = this.newVectorM4L(1, 2, 3, 4);
    final VectorM4L v1 = new VectorM4L(v0);

    Assert.assertTrue(v0.getXL() == v1.getXL());
    Assert.assertTrue(v0.getYL() == v1.getYL());
    Assert.assertTrue(v0.getZL() == v1.getZL());
    Assert.assertTrue(v0.getWL() == v1.getWL());
  }

  @Override @Test public void testInterpolateLinearLimits()
  {
    final VectorM4L.ContextVM4L c = new VectorM4L.ContextVM4L();
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LContract.randomPositiveNumber();
      final long y0 = VectorM4LContract.randomPositiveNumber();
      final long z0 = VectorM4LContract.randomPositiveNumber();
      final long w0 = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LContract.randomPositiveNumber();
      final long y1 = VectorM4LContract.randomPositiveNumber();
      final long z1 = VectorM4LContract.randomPositiveNumber();
      final long w1 = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v1 = this.newVectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = this.newVectorM4L();
      final VectorM4L vr1 = this.newVectorM4L();
      VectorM4L.interpolateLinear(c, v0, v1, 0, vr0);
      VectorM4L.interpolateLinear(c, v0, v1, 1, vr1);

      Assert.assertTrue(v0.getXL() == vr0.getXL());
      Assert.assertTrue(v0.getYL() == vr0.getYL());
      Assert.assertTrue(v0.getZL() == vr0.getZL());
      Assert.assertTrue(v0.getWL() == vr0.getWL());

      Assert.assertTrue(v1.getXL() == vr1.getXL());
      Assert.assertTrue(v1.getYL() == vr1.getYL());
      Assert.assertTrue(v1.getZL() == vr1.getZL());
      Assert.assertTrue(v1.getWL() == vr1.getWL());
    }
  }

  @Override @Test public void testMagnitudeNonzero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LContract.randomPositiveSmallNumber();
      final long y = VectorM4LContract.randomPositiveSmallNumber();
      final long z = VectorM4LContract.randomPositiveSmallNumber();
      final long w = VectorM4LContract.randomPositiveSmallNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final long m = VectorM4L.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Override @Test public void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testMagnitudeOne()
  {
    final VectorM4L v = this.newVectorM4L(1, 0, 0, 0);
    final long m = VectorM4L.magnitude(v);
    Assert.assertTrue(m == 1);
  }

  @Override @Test public void testMagnitudeSimple()
  {
    final VectorM4L v = this.newVectorM4L(8, 0, 0, 0);

    {
      final long p = VectorM4L.dotProduct(v, v);
      final long q = VectorM4L.magnitudeSquared(v);
      final long r = VectorM4L.magnitude(v);
      Assert.assertTrue(p == 64);
      Assert.assertTrue(q == 64);
      Assert.assertTrue(r == 8);
    }
  }

  @Override @Test public void testMagnitudeZero()
  {
    final VectorM4L v = this.newVectorM4L(0, 0, 0, 0);
    final long m = VectorM4L.magnitude(v);
    Assert.assertTrue(m == 0);
  }

  @Override @Test public void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Override @Test public void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Override @Test public void testProjectionPerpendicularZero()
  {
    {
      final VectorM4L p = this.newVectorM4L(1, 0, 0, 0);
      final VectorM4L q = this.newVectorM4L(0, 1, 0, 0);
      final VectorM4L r = this.newVectorM4L();
      final VectorM4L u = VectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4L.magnitude(u) == 0);
    }

    {
      final VectorM4L p = this.newVectorM4L(-1, 0, 0, 0);
      final VectorM4L q = this.newVectorM4L(0, 1, 0, 0);
      final VectorM4L r = this.newVectorM4L();
      final VectorM4L u = VectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertTrue(VectorM4L.magnitude(u) == 0);
    }
  }

  @Override @Test public void testScaleMutation()
  {
    final VectorM4L out = this.newVectorM4L();
    final VectorM4L v0 = this.newVectorM4L(1, 1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(out.getWL() == 1);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);

    final VectorM4L ov0 = VectorM4L.scale(v0, 2, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 2);
    Assert.assertTrue(out.getYL() == 2);
    Assert.assertTrue(out.getZL() == 2);
    Assert.assertTrue(out.getWL() == 2);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);

    final VectorM4L ov1 = VectorM4L.scaleInPlace(v0, 2);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 2);
    Assert.assertTrue(ov1.getYL() == 2);
    Assert.assertTrue(ov1.getZL() == 2);
    Assert.assertTrue(ov1.getWL() == 2);
    Assert.assertTrue(v0.getXL() == 2);
    Assert.assertTrue(v0.getYL() == 2);
    Assert.assertTrue(v0.getZL() == 2);
    Assert.assertTrue(v0.getWL() == 2);
  }

  @Override @Test public void testScaleOne()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LContract.randomPositiveNumber();
      final long y = VectorM4LContract.randomPositiveNumber();
      final long z = VectorM4LContract.randomPositiveNumber();
      final long w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();

      VectorM4L.scale(v, 1, vr);

      Assert.assertTrue(v.getXL() == vr.getXL());
      Assert.assertTrue(v.getYL() == vr.getYL());
      Assert.assertTrue(v.getZL() == vr.getZL());
      Assert.assertTrue(v.getWL() == vr.getWL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();
        final long orig_z = v.getZL();
        final long orig_w = v.getWL();

        VectorM4L.scaleInPlace(v, 1);

        Assert.assertTrue(v.getXL() == orig_x);
        Assert.assertTrue(v.getYL() == orig_y);
        Assert.assertTrue(v.getZL() == orig_z);
        Assert.assertTrue(v.getWL() == orig_w);
      }
    }
  }

  @Override @Test public void testScaleZero()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = VectorM4LContract.randomPositiveNumber();
      final long y = VectorM4LContract.randomPositiveNumber();
      final long z = VectorM4LContract.randomPositiveNumber();
      final long w = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v = this.newVectorM4L(x, y, z, w);

      final VectorM4L vr = this.newVectorM4L();

      VectorM4L.scale(v, 0, vr);

      Assert.assertTrue(vr.getXL() == 0);
      Assert.assertTrue(vr.getYL() == 0);
      Assert.assertTrue(vr.getZL() == 0);
      Assert.assertTrue(vr.getWL() == 0);

      {
        VectorM4L.scaleInPlace(v, 0);

        Assert.assertTrue(v.getXL() == 0);
        Assert.assertTrue(v.getYL() == 0);
        Assert.assertTrue(v.getZL() == 0);
        Assert.assertTrue(v.getWL() == 0);
      }
    }
  }

  @Override @Test public void testString()
  {
    final VectorM4L v = this.newVectorM4L(1, 2, 3, 4);
    Assert.assertTrue(v.toString().equals("[VectorM4L 1 2 3 4]"));
  }

  @Override @Test public void testSubtract()
  {
    for (long index = 0; index
                         < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = VectorM4LContract.randomPositiveNumber();
      final long y0 = VectorM4LContract.randomPositiveNumber();
      final long z0 = VectorM4LContract.randomPositiveNumber();
      final long w0 = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = VectorM4LContract.randomPositiveNumber();
      final long y1 = VectorM4LContract.randomPositiveNumber();
      final long z1 = VectorM4LContract.randomPositiveNumber();
      final long w1 = VectorM4LContract.randomPositiveNumber();
      final VectorM4L v1 = this.newVectorM4L(x1, y1, z1, w1);

      final VectorM4L vr0 = this.newVectorM4L();
      VectorM4L.subtract(v0, v1, vr0);

      Assert.assertTrue(vr0.getXL() == (v0.getXL() - v1.getXL()));
      Assert.assertTrue(vr0.getYL() == (v0.getYL() - v1.getYL()));
      Assert.assertTrue(vr0.getZL() == (v0.getZL() - v1.getZL()));
      Assert.assertTrue(vr0.getWL() == (v0.getWL() - v1.getWL()));

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        VectorM4L.subtractInPlace(v0, v1);

        Assert.assertTrue(v0.getXL() == (orig_x - v1.getXL()));
        Assert.assertTrue(v0.getYL() == (orig_y - v1.getYL()));
        Assert.assertTrue(v0.getZL() == (orig_z - v1.getZL()));
        Assert.assertTrue(v0.getWL() == (orig_w - v1.getWL()));
      }
    }
  }

  @Override @Test public void testSubtractMutation()
  {
    final VectorM4L out = this.newVectorM4L();
    final VectorM4L v0 = this.newVectorM4L(1, 1, 1, 1);
    final VectorM4L v1 = this.newVectorM4L(1, 1, 1, 1);

    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(out.getWL() == 1);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);

    final VectorM4L ov0 = VectorM4L.subtract(v0, v1, out);

    Assert.assertTrue(out == ov0);
    Assert.assertTrue(out.getXL() == 0);
    Assert.assertTrue(out.getYL() == 0);
    Assert.assertTrue(out.getZL() == 0);
    Assert.assertTrue(out.getWL() == 0);
    Assert.assertTrue(v0.getXL() == 1);
    Assert.assertTrue(v0.getYL() == 1);
    Assert.assertTrue(v0.getZL() == 1);
    Assert.assertTrue(v0.getWL() == 1);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);

    final VectorM4L ov1 = VectorM4L.subtractInPlace(v0, v1);

    Assert.assertTrue(ov1 == v0);
    Assert.assertTrue(ov1.getXL() == 0);
    Assert.assertTrue(ov1.getYL() == 0);
    Assert.assertTrue(ov1.getZL() == 0);
    Assert.assertTrue(ov1.getWL() == 0);
    Assert.assertTrue(v0.getXL() == 0);
    Assert.assertTrue(v0.getYL() == 0);
    Assert.assertTrue(v0.getZL() == 0);
    Assert.assertTrue(v0.getWL() == 0);
    Assert.assertTrue(v1.getXL() == 1);
    Assert.assertTrue(v1.getYL() == 1);
    Assert.assertTrue(v1.getZL() == 1);
    Assert.assertTrue(v1.getWL() == 1);
  }

  protected abstract VectorM4L newVectorM4L();
}
