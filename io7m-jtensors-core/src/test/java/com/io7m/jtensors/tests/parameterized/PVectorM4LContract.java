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
import com.io7m.jtensors.parameterized.PVector4LType;
import com.io7m.jtensors.parameterized.PVectorM4L;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM4LContract<T, V extends PVector4LType<T>>
{
  protected abstract V newVectorM4L(
    final long x,
    final long y,
    final long z,
    final long w);

  protected abstract V newVectorM4L();

  protected abstract V newVectorM4LFrom(final V v0);

  public static long randomNegativeNumber()
  {
    return (long) (Math.random() * (double) Long.MIN_VALUE);
  }

  public static long randomPositiveNumber()
  {
    return (long) (Math.random() * (double) Long.MAX_VALUE);
  }

  public static long randomPositiveSmallNumber()
  {
    return (long) (Math.random() * (double) (1 << 14));
  }

  @Test public final void testAbsolute()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long y = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long z = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long w = (long) (Math.random() * (double) Long.MIN_VALUE);
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      PVectorM4L.absolute(v, vr);

      Assert.assertEquals(Math.abs(v.getXL()), vr.getXL());
      Assert.assertEquals(Math.abs(v.getYL()), vr.getYL());
      Assert.assertEquals(Math.abs(v.getZL()), vr.getZL());
      Assert.assertEquals(Math.abs(v.getWL()), vr.getWL());
    }
  }

  @Test public final void testAbsoluteMutation()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long y = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long z = (long) (Math.random() * (double) Long.MIN_VALUE);
      final long w = (long) (Math.random() * (double) Long.MIN_VALUE);
      final V v = this.newVectorM4L(x, y, z, w);

      PVectorM4L.absoluteInPlace(v);

      Assert.assertEquals(v.getXL(), Math.abs(x));
      Assert.assertEquals(v.getYL(), Math.abs(y));
      Assert.assertEquals(v.getZL(), Math.abs(z));
      Assert.assertEquals(v.getWL(), Math.abs(w));
    }
  }

  @Test public final void testAdd()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w0 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w1 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM4L(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4L();
      PVectorM4L.add(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() + v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() + v1.getYL()), vr0.getYL());
      Assert.assertEquals((v0.getZL() + v1.getZL()), vr0.getZL());
      Assert.assertEquals((v0.getWL() + v1.getWL()), vr0.getWL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        PVectorM4L.addInPlace(v0, v1);

        Assert.assertEquals((orig_x + v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y + v1.getYL()), v0.getYL());
        Assert.assertEquals((orig_z + v1.getZL()), v0.getZL());
        Assert.assertEquals((orig_w + v1.getWL()), v0.getWL());
      }
    }
  }

  @Test public final void testAddMutation()
  {
    final V out = this.newVectorM4L();
    final V v0 = this.newVectorM4L(1L, 1L, 1L, 1L);
    final V v1 = this.newVectorM4L(1L, 1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    final V ov0 = PVectorM4L.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());
    Assert.assertEquals(2L, out.getZL());
    Assert.assertEquals(2L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    final V ov1 = PVectorM4L.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());
    Assert.assertEquals(2L, ov1.getZL());
    Assert.assertEquals(2L, ov1.getWL());
    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());
    Assert.assertEquals(2L, v0.getZL());
    Assert.assertEquals(2L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());
  }

  @Test public final void testAddScaled()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w0 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w1 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM4L(x1, y1, z1, w1);

      final long r = PVectorM4LContract.randomPositiveSmallNumber();

      final V vr0 = this.newVectorM4L();
      PVectorM4L.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals((v0.getXL() + (v1.getXL() * r)), vr0.getXL());
      Assert.assertEquals((v0.getYL() + (v1.getYL() * r)), vr0.getYL());
      Assert.assertEquals((v0.getZL() + (v1.getZL() * r)), vr0.getZL());
      Assert.assertEquals((v0.getWL() + (v1.getWL() * r)), vr0.getWL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        PVectorM4L.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals((orig_x + (v1.getXL() * r)), v0.getXL());
        Assert.assertEquals((orig_y + (v1.getYL() * r)), v0.getYL());
        Assert.assertEquals((orig_z + (v1.getZL() * r)), v0.getZL());
        Assert.assertEquals((orig_w + (v1.getWL() * r)), v0.getWL());
      }
    }
  }

  @Test public final void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Test public final void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Test public final void testCheckInterface()
  {
    final V v = this.newVectorM4L(3L, 5L, 7L, 11L);

    Assert.assertEquals(v.getXL(), v.getXL());
    Assert.assertEquals(v.getYL(), v.getYL());
    Assert.assertEquals(v.getZL(), v.getZL());
    Assert.assertEquals(v.getWL(), v.getWL());
  }

  @Test public final void testClampByPVectorMaximumOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max_x = PVectorM4LContract.randomNegativeNumber();
      final long max_y = PVectorM4LContract.randomNegativeNumber();
      final long max_z = PVectorM4LContract.randomNegativeNumber();
      final long max_w = PVectorM4LContract.randomNegativeNumber();
      final V maximum =
        this.newVectorM4L(max_x, max_y, max_z, max_w);

      final long x = PVectorM4LContract.randomNegativeNumber();
      final long y = PVectorM4LContract.randomNegativeNumber();
      final long z = PVectorM4LContract.randomNegativeNumber();
      final long w = PVectorM4LContract.randomNegativeNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      final V vo = PVectorM4L.clampMaximumByPVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());

      {
        final V vr0 = PVectorM4L.clampMaximumByPVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXL() <= maximum.getXL());
        Assert.assertTrue(v.getYL() <= maximum.getYL());
        Assert.assertTrue(v.getZL() <= maximum.getZL());
        Assert.assertTrue(v.getWL() <= maximum.getWL());
      }
    }
  }

  @Test public final void testClampByPVectorMinimumOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorM4LContract.randomPositiveNumber();
      final long min_y = PVectorM4LContract.randomPositiveNumber();
      final long min_z = PVectorM4LContract.randomPositiveNumber();
      final long min_w = PVectorM4LContract.randomPositiveNumber();
      final V minimum =
        this.newVectorM4L(min_x, min_y, min_z, min_w);

      final long x = PVectorM4LContract.randomNegativeNumber();
      final long y = PVectorM4LContract.randomNegativeNumber();
      final long z = PVectorM4LContract.randomNegativeNumber();
      final long w = PVectorM4LContract.randomNegativeNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      final V vo = PVectorM4L.clampMinimumByPVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());

      {
        final V vr0 = PVectorM4L.clampMinimumByPVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXL() >= minimum.getXL());
        Assert.assertTrue(v.getYL() >= minimum.getYL());
        Assert.assertTrue(v.getZL() >= minimum.getZL());
        Assert.assertTrue(v.getWL() >= minimum.getWL());
      }
    }
  }

  @Test public final void testClampByPVectorOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long min_x = PVectorM4LContract.randomNegativeNumber();
      final long min_y = PVectorM4LContract.randomNegativeNumber();
      final long min_z = PVectorM4LContract.randomNegativeNumber();
      final long min_w = PVectorM4LContract.randomNegativeNumber();
      final V minimum =
        this.newVectorM4L(min_x, min_y, min_z, min_w);

      final long max_x = PVectorM4LContract.randomPositiveNumber();
      final long max_y = PVectorM4LContract.randomPositiveNumber();
      final long max_z = PVectorM4LContract.randomPositiveNumber();
      final long max_w = PVectorM4LContract.randomPositiveNumber();
      final V maximum =
        this.newVectorM4L(max_x, max_y, max_z, max_w);

      final long x = PVectorM4LContract.randomNegativeNumber();
      final long y = PVectorM4LContract.randomPositiveNumber();
      final long z = PVectorM4LContract.randomPositiveNumber();
      final long w = PVectorM4LContract.randomPositiveNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      final V vo =
        PVectorM4L.clampByPVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXL() <= maximum.getXL());
      Assert.assertTrue(vr.getYL() <= maximum.getYL());
      Assert.assertTrue(vr.getZL() <= maximum.getZL());
      Assert.assertTrue(vr.getWL() <= maximum.getWL());
      Assert.assertTrue(vr.getXL() >= minimum.getXL());
      Assert.assertTrue(vr.getYL() >= minimum.getYL());
      Assert.assertTrue(vr.getZL() >= minimum.getZL());
      Assert.assertTrue(vr.getWL() >= minimum.getWL());

      {
        final V vr0 =
          PVectorM4L.clampByPVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
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

  @Test public final void testClampMaximumOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long maximum = PVectorM4LContract.randomNegativeNumber();

      final long x = PVectorM4LContract.randomPositiveNumber();
      final long y = PVectorM4LContract.randomPositiveNumber();
      final long z = PVectorM4LContract.randomPositiveNumber();
      final long w = PVectorM4LContract.randomPositiveNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      PVectorM4L.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getWL() <= maximum);

      {
        PVectorM4L.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXL() <= maximum);
        Assert.assertTrue(v.getYL() <= maximum);
        Assert.assertTrue(v.getZL() <= maximum);
        Assert.assertTrue(v.getWL() <= maximum);
      }
    }
  }

  @Test public final void testClampMinimumOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorM4LContract.randomPositiveNumber();

      final long x = PVectorM4LContract.randomNegativeNumber();
      final long y = PVectorM4LContract.randomNegativeNumber();
      final long z = PVectorM4LContract.randomNegativeNumber();
      final long w = PVectorM4LContract.randomNegativeNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      PVectorM4L.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() >= minimum);

      {
        PVectorM4L.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXL() >= minimum);
        Assert.assertTrue(v.getYL() >= minimum);
        Assert.assertTrue(v.getZL() >= minimum);
        Assert.assertTrue(v.getWL() >= minimum);
      }
    }
  }

  @Test public final void testClampOrdering()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long minimum = PVectorM4LContract.randomNegativeNumber();
      final long maximum = PVectorM4LContract.randomPositiveNumber();

      final long x = PVectorM4LContract.randomNegativeNumber();
      final long y = PVectorM4LContract.randomPositiveNumber();
      final long z = PVectorM4LContract.randomPositiveNumber();
      final long w = PVectorM4LContract.randomPositiveNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();
      PVectorM4L.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXL() <= maximum);
      Assert.assertTrue(vr.getXL() >= minimum);
      Assert.assertTrue(vr.getYL() <= maximum);
      Assert.assertTrue(vr.getYL() >= minimum);
      Assert.assertTrue(vr.getZL() <= maximum);
      Assert.assertTrue(vr.getZL() >= minimum);
      Assert.assertTrue(vr.getWL() <= maximum);
      Assert.assertTrue(vr.getWL() >= minimum);

      {
        PVectorM4L.clampInPlace(v, minimum, maximum);

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

  @Test public final void testCopy()
  {
    final V vb = this.newVectorM4L(5L, 6L, 7L, 8L);
    final V va = this.newVectorM4L(1L, 2L, 3L, 4L);

    Assert.assertFalse(va.getXL() == vb.getXL());
    Assert.assertFalse(va.getYL() == vb.getYL());
    Assert.assertFalse(va.getZL() == vb.getZL());
    Assert.assertFalse(va.getWL() == vb.getWL());

    PVectorM4L.copy(va, vb);

    Assert.assertEquals(vb.getXL(), va.getXL());
    Assert.assertEquals(vb.getYL(), va.getYL());
    Assert.assertEquals(vb.getZL(), va.getZL());
    Assert.assertEquals(vb.getWL(), va.getWL());
  }

  @Test public final void testCopy2Correct()
  {
    final V v0 = this.newVectorM4L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final V v1 = this.newVectorM4L();
    final V v2 = this.newVectorM4L();

    v1.copyFrom2L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(0L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    v2.copyFromTyped2L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
    Assert.assertEquals(0L, v2.getZL());
    Assert.assertEquals(1L, v2.getWL());
  }

  @Test public final void testCopy3Correct()
  {
    final V v0 = this.newVectorM4L(
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE,
      (long) Math.random() * Long.MAX_VALUE);
    final V v1 = this.newVectorM4L();
    final V v2 = this.newVectorM4L();

    v1.copyFrom3L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    v2.copyFromTyped3L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
    Assert.assertEquals(v0.getZL(), v2.getZL());
    Assert.assertEquals(1L, v2.getWL());
  }

  @Test public final void testCopy4Correct()
  {
    final V v0 = this.newVectorM4L(
      (long) (Math.random() * Long.MAX_VALUE),
      (long) (Math.random() * Long.MAX_VALUE),
      (long) (Math.random() * Long.MAX_VALUE),
      (long) (Math.random() * Long.MAX_VALUE));
    final V v1 = this.newVectorM4L();
    final V v2 = this.newVectorM4L();

    v1.copyFrom4L(v0);

    Assert.assertEquals(v0.getXL(), v1.getXL());
    Assert.assertEquals(v0.getYL(), v1.getYL());
    Assert.assertEquals(v0.getZL(), v1.getZL());
    Assert.assertEquals(v0.getWL(), v1.getWL());

    v2.copyFromTyped4L(v0);

    Assert.assertEquals(v0.getXL(), v2.getXL());
    Assert.assertEquals(v0.getYL(), v2.getYL());
    Assert.assertEquals(v0.getZL(), v2.getZL());
    Assert.assertEquals(v0.getWL(), v2.getWL());
  }

  @Test public final void testDefault0001()
  {
    Assert.assertTrue(
      this.newVectorM4L().equals(this.newVectorM4L(0L, 0L, 0L, 1L)));
  }

  @Test public final void testDistance()
  {
    final PVectorM4L.ContextPVM4L c = new PVectorM4L.ContextPVM4L();
    final V v0 = this.newVectorM4L(0L, 1L, 0L, 0L);
    final V v1 = this.newVectorM4L(0L, 0L, 0L, 0L);
    Assert.assertEquals(1L, PVectorM4L.distance(c, v0, v1));
  }

  @Test public final void testDistanceOrdering()
  {
    final PVectorM4L.ContextPVM4L c = new PVectorM4L.ContextPVM4L();
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z0 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w0 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long y1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long z1 = PVectorM4LContract.randomPositiveSmallNumber();
      final long w1 = PVectorM4LContract.randomPositiveSmallNumber();
      final V v1 = this.newVectorM4L(x1, y1, z1, w1);

      Assert.assertTrue(PVectorM4L.distance(c, v0, v1) >= 0L);
    }
  }

  @Test public final void testDotProduct()
  {
    final V v0 = this.newVectorM4L(10L, 10L, 10L, 10L);
    final V v1 = this.newVectorM4L(10L, 10L, 10L, 10L);

    {
      final long p = PVectorM4L.dotProduct(v0, v1);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(10L, v0.getWL());
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(10L, v1.getZL());
      Assert.assertEquals(10L, v1.getWL());
      Assert.assertEquals(400L, p);
    }

    {
      final long p = PVectorM4L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(10L, v0.getWL());
      Assert.assertEquals(400L, p);
    }

    {
      final long p = PVectorM4L.dotProduct(v1, v1);
      Assert.assertEquals(10L, v1.getXL());
      Assert.assertEquals(10L, v1.getYL());
      Assert.assertEquals(10L, v1.getZL());
      Assert.assertEquals(10L, v1.getWL());
      Assert.assertEquals(400L, p);
    }
  }

  @Test public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000L;
      final long x = (long) (Math.random() * (double) max);
      final long y = (long) (Math.random() * (double) max);
      final long z = (long) (Math.random() * (double) max);
      final long w = (long) (Math.random() * (double) max);
      final V q = this.newVectorM4L(x, y, z, w);

      final double ms = (double) PVectorM4L.magnitudeSquared(q);
      final double dp = (double) PVectorM4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("ms : " + ms);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long max = 1000L;
      final long x = (long) (Math.random() * (double) max);
      final long y = (long) (Math.random() * (double) max);
      final long z = (long) (Math.random() * (double) max);
      final long w = (long) (Math.random() * (double) max);
      final V q = this.newVectorM4L(x, y, z, w);
      final double dp = (double) PVectorM4L.dotProduct(q, q);

      System.out.println("q  : " + q);
      System.out.println("dp : " + dp);

      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test public final void testDotProductSelfMagnitudeSquared()
  {
    final V v0 = this.newVectorM4L(10L, 10L, 10L, 10L);

    {
      final long p = PVectorM4L.dotProduct(v0, v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(10L, v0.getWL());
      Assert.assertEquals(400L, p);
    }

    {
      final long p = PVectorM4L.magnitudeSquared(v0);
      Assert.assertEquals(10L, v0.getXL());
      Assert.assertEquals(10L, v0.getYL());
      Assert.assertEquals(10L, v0.getZL());
      Assert.assertEquals(10L, v0.getWL());
      Assert.assertEquals(400L, p);
    }
  }

  @Test public final void testEqualsCorrect()
  {
    {
      final V m0 = this.newVectorM4L();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final V m0 = this.newVectorM4L();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4L();
      Assert.assertFalse(m0.equals(Long.valueOf(23L)));
    }

    {
      final V m0 = this.newVectorM4L();
      final V m1 = this.newVectorM4L();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test public final void testEqualsNotEqualCorrect()
  {
    final long x = (long) (Math.random() * 1000.0);
    final long y = x + 1L;
    final long z = y + 1L;
    final long w = z + 1L;
    final long q = w + 1L;

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      Assert.assertFalse(m0.equals(Long.valueOf(23L)));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final V m0 = this.newVectorM4L(x, y, z, w);
      final V m1 = this.newVectorM4L(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test public final void testHashCodeEqualsCorrect()
  {
    final V m0 = this.newVectorM4L();
    final V m1 = this.newVectorM4L();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test public final void testHashCodeNotEqualCorrect()
  {
    {
      final V m0 = this.newVectorM4L();
      final V m1 = this.newVectorM4L();
      m1.setXL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final V m0 = this.newVectorM4L();
      final V m1 = this.newVectorM4L();
      m1.setYL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final V m0 = this.newVectorM4L();
      final V m1 = this.newVectorM4L();
      m1.setZL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }

    {
      final V m0 = this.newVectorM4L();
      final V m1 = this.newVectorM4L();
      m1.setWL(23L);
      Assert.assertFalse(m0.hashCode() == m1.hashCode());
    }
  }

  @Test public final void testInitializeReadable()
  {
    final V v0 = this.newVectorM4L(1L, 2L, 3L, 4L);
    final V v1 = this.newVectorM4LFrom(v0);

    Assert.assertEquals(v1.getXL(), v0.getXL());
    Assert.assertEquals(v1.getYL(), v0.getYL());
    Assert.assertEquals(v1.getZL(), v0.getZL());
    Assert.assertEquals(v1.getWL(), v0.getWL());
  }

  @Test public final void testInterpolateLinearLimits()
  {
    final PVectorM4L.ContextPVM4L c = new PVectorM4L.ContextPVM4L();
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM4LContract.randomPositiveNumber();
      final long y0 = PVectorM4LContract.randomPositiveNumber();
      final long z0 = PVectorM4LContract.randomPositiveNumber();
      final long w0 = PVectorM4LContract.randomPositiveNumber();
      final V v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = PVectorM4LContract.randomPositiveNumber();
      final long y1 = PVectorM4LContract.randomPositiveNumber();
      final long z1 = PVectorM4LContract.randomPositiveNumber();
      final long w1 = PVectorM4LContract.randomPositiveNumber();
      final V v1 = this.newVectorM4L(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4L();
      final V vr1 = this.newVectorM4L();
      PVectorM4L.interpolateLinear(c, v0, v1, 0.0, vr0);
      PVectorM4L.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals(vr0.getXL(), v0.getXL());
      Assert.assertEquals(vr0.getYL(), v0.getYL());
      Assert.assertEquals(vr0.getZL(), v0.getZL());
      Assert.assertEquals(vr0.getWL(), v0.getWL());

      Assert.assertEquals(vr1.getXL(), v1.getXL());
      Assert.assertEquals(vr1.getYL(), v1.getYL());
      Assert.assertEquals(vr1.getZL(), v1.getZL());
      Assert.assertEquals(vr1.getWL(), v1.getWL());
    }
  }

  @Test public final void testMagnitudeNonzero()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM4LContract.randomPositiveSmallNumber();
      final long y = PVectorM4LContract.randomPositiveSmallNumber();
      final long z = PVectorM4LContract.randomPositiveSmallNumber();
      final long w = PVectorM4LContract.randomPositiveSmallNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final long m = PVectorM4L.magnitude(v);
      Assert.assertTrue(m >= 1L);
    }
  }

  @Test public final void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testMagnitudeOne()
  {
    final V v = this.newVectorM4L(1L, 0L, 0L, 0L);
    final long m = PVectorM4L.magnitude(v);
    Assert.assertEquals(1L, m);
  }

  @Test public final void testMagnitudeSimple()
  {
    final V v = this.newVectorM4L(8L, 0L, 0L, 0L);

    {
      final long p = PVectorM4L.dotProduct(v, v);
      final long q = PVectorM4L.magnitudeSquared(v);
      final long r = PVectorM4L.magnitude(v);
      Assert.assertEquals(64L, p);
      Assert.assertEquals(64L, q);
      Assert.assertEquals(8L, r);
    }
  }

  @Test public final void testMagnitudeZero()
  {
    final V v = this.newVectorM4L(0L, 0L, 0L, 0L);
    final long m = PVectorM4L.magnitude(v);
    Assert.assertEquals(0L, m);
  }

  @Test public final void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Test public final void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Test public final void testProjectionPerpendicularZero()
  {
    {
      final V p = this.newVectorM4L(1L, 0L, 0L, 0L);
      final V q = this.newVectorM4L(0L, 1L, 0L, 0L);
      final V r = this.newVectorM4L();
      final V u = PVectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, PVectorM4L.magnitude(u));
    }

    {
      final V p = this.newVectorM4L(-1L, 0L, 0L, 0L);
      final V q = this.newVectorM4L(0L, 1L, 0L, 0L);
      final V r = this.newVectorM4L();
      final V u = PVectorM4L.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, PVectorM4L.magnitude(u));
    }
  }

  @Test public final void testScaleMutation()
  {
    final V out = this.newVectorM4L();
    final V v0 = this.newVectorM4L(1L, 1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());

    final V ov0 = PVectorM4L.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2L, out.getXL());
    Assert.assertEquals(2L, out.getYL());
    Assert.assertEquals(2L, out.getZL());
    Assert.assertEquals(2L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());

    final V ov1 = PVectorM4L.scaleInPlace(v0, 2L);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2L, ov1.getXL());
    Assert.assertEquals(2L, ov1.getYL());
    Assert.assertEquals(2L, ov1.getZL());
    Assert.assertEquals(2L, ov1.getWL());
    Assert.assertEquals(2L, v0.getXL());
    Assert.assertEquals(2L, v0.getYL());
    Assert.assertEquals(2L, v0.getZL());
    Assert.assertEquals(2L, v0.getWL());
  }

  @Test public final void testScaleOne()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM4LContract.randomPositiveNumber();
      final long y = PVectorM4LContract.randomPositiveNumber();
      final long z = PVectorM4LContract.randomPositiveNumber();
      final long w = PVectorM4LContract.randomPositiveNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();

      PVectorM4L.scale(v, 1.0, vr);

      Assert.assertEquals(vr.getXL(), v.getXL());
      Assert.assertEquals(vr.getYL(), v.getYL());
      Assert.assertEquals(vr.getZL(), v.getZL());
      Assert.assertEquals(vr.getWL(), v.getWL());

      {
        final long orig_x = v.getXL();
        final long orig_y = v.getYL();
        final long orig_z = v.getZL();
        final long orig_w = v.getWL();

        PVectorM4L.scaleInPlace(v, 1L);

        Assert.assertEquals(orig_x, v.getXL());
        Assert.assertEquals(orig_y, v.getYL());
        Assert.assertEquals(orig_z, v.getZL());
        Assert.assertEquals(orig_w, v.getWL());
      }
    }
  }

  @Test public final void testScaleZero()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x = PVectorM4LContract.randomPositiveNumber();
      final long y = PVectorM4LContract.randomPositiveNumber();
      final long z = PVectorM4LContract.randomPositiveNumber();
      final long w = PVectorM4LContract.randomPositiveNumber();
      final V v = this.newVectorM4L(x, y, z, w);

      final V vr = this.newVectorM4L();

      PVectorM4L.scale(v, 0.0, vr);

      Assert.assertEquals(0L, vr.getXL());
      Assert.assertEquals(0L, vr.getYL());
      Assert.assertEquals(0L, vr.getZL());
      Assert.assertEquals(0L, vr.getWL());

      {
        PVectorM4L.scaleInPlace(v, 0L);

        Assert.assertEquals(0L, v.getXL());
        Assert.assertEquals(0L, v.getYL());
        Assert.assertEquals(0L, v.getZL());
        Assert.assertEquals(0L, v.getWL());
      }
    }
  }

  @Test public final void testString()
  {
    final V v = this.newVectorM4L(1L, 2L, 3L, 4L);
    Assert.assertTrue(v.toString().endsWith(" 1 2 3 4]"));
  }

  @Test public final void testSubtract()
  {
    for (long index = 0L; index
                         < (long) TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final long x0 = PVectorM4LContract.randomPositiveNumber();
      final long y0 = PVectorM4LContract.randomPositiveNumber();
      final long z0 = PVectorM4LContract.randomPositiveNumber();
      final long w0 = PVectorM4LContract.randomPositiveNumber();
      final V v0 = this.newVectorM4L(x0, y0, z0, w0);

      final long x1 = PVectorM4LContract.randomPositiveNumber();
      final long y1 = PVectorM4LContract.randomPositiveNumber();
      final long z1 = PVectorM4LContract.randomPositiveNumber();
      final long w1 = PVectorM4LContract.randomPositiveNumber();
      final V v1 = this.newVectorM4L(x1, y1, z1, w1);

      final V vr0 = this.newVectorM4L();
      PVectorM4L.subtract(v0, v1, vr0);

      Assert.assertEquals((v0.getXL() - v1.getXL()), vr0.getXL());
      Assert.assertEquals((v0.getYL() - v1.getYL()), vr0.getYL());
      Assert.assertEquals((v0.getZL() - v1.getZL()), vr0.getZL());
      Assert.assertEquals((v0.getWL() - v1.getWL()), vr0.getWL());

      {
        final long orig_x = v0.getXL();
        final long orig_y = v0.getYL();
        final long orig_z = v0.getZL();
        final long orig_w = v0.getWL();
        PVectorM4L.subtractInPlace(v0, v1);

        Assert.assertEquals((orig_x - v1.getXL()), v0.getXL());
        Assert.assertEquals((orig_y - v1.getYL()), v0.getYL());
        Assert.assertEquals((orig_z - v1.getZL()), v0.getZL());
        Assert.assertEquals((orig_w - v1.getWL()), v0.getWL());
      }
    }
  }

  @Test public final void testSubtractMutation()
  {
    final V out = this.newVectorM4L();
    final V v0 = this.newVectorM4L(1L, 1L, 1L, 1L);
    final V v1 = this.newVectorM4L(1L, 1L, 1L, 1L);

    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(1L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    final V ov0 = PVectorM4L.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(0L, out.getXL());
    Assert.assertEquals(0L, out.getYL());
    Assert.assertEquals(0L, out.getZL());
    Assert.assertEquals(0L, out.getWL());
    Assert.assertEquals(1L, v0.getXL());
    Assert.assertEquals(1L, v0.getYL());
    Assert.assertEquals(1L, v0.getZL());
    Assert.assertEquals(1L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());

    final V ov1 = PVectorM4L.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(0L, ov1.getXL());
    Assert.assertEquals(0L, ov1.getYL());
    Assert.assertEquals(0L, ov1.getZL());
    Assert.assertEquals(0L, ov1.getWL());
    Assert.assertEquals(0L, v0.getXL());
    Assert.assertEquals(0L, v0.getYL());
    Assert.assertEquals(0L, v0.getZL());
    Assert.assertEquals(0L, v0.getWL());
    Assert.assertEquals(1L, v1.getXL());
    Assert.assertEquals(1L, v1.getYL());
    Assert.assertEquals(1L, v1.getZL());
    Assert.assertEquals(1L, v1.getWL());
  }
}
