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
import com.io7m.jtensors.Vector4IType;
import com.io7m.jtensors.VectorM4I;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4IContract<T extends Vector4IType>
{
  protected static double getRandom()
  {
    return Math.random();
  }

  public static int randomNegativeNumber()
  {
    return (int) (getRandom() * (double) Integer.MIN_VALUE);
  }

  public static int randomPositiveNumber()
  {
    return (int) (getRandom() * (double) Integer.MAX_VALUE);
  }

  public static int randomPositiveSmallNumber()
  {
    return (int) (getRandom() * (double) (1 << 14));
  }

  protected static int getLargeRandom()
  {
    return (int) (getRandom() * Integer.MAX_VALUE);
  }

  protected abstract T newVectorM4I(
    final int x,
    final int y,
    final int z,
    final int w);

  protected abstract T newVectorM4IFrom(final T v0);

  protected abstract T newVectorM4I();

  @Test
  public final void testAbsolute()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int w =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      VectorM4I.absolute(v, vr);

      Assert.assertEquals((long) Math.abs(v.getXI()), (long) vr.getXI());
      Assert.assertEquals((long) Math.abs(v.getYI()), (long) vr.getYI());
      Assert.assertEquals((long) Math.abs(v.getZI()), (long) vr.getZI());
      Assert.assertEquals((long) Math.abs(v.getWI()), (long) vr.getWI());
    }
  }

  @Test
  public final void testAbsoluteMutation()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int y =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int z =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final int w =
        (int) (getRandom() * (double) Integer.MIN_VALUE);
      final T v = this.newVectorM4I(x, y, z, w);

      VectorM4I.absoluteInPlace(v);

      Assert.assertEquals((long) v.getXI(), (long) Math.abs(x));
      Assert.assertEquals((long) v.getYI(), (long) Math.abs(y));
      Assert.assertEquals((long) v.getZI(), (long) Math.abs(z));
      Assert.assertEquals((long) v.getWI(), (long) Math.abs(w));
    }
  }

  @Test
  public final void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final int w0 = randomPositiveSmallNumber();
      final T v0 = this.newVectorM4I(x0, y0, z0, w0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final int w1 = randomPositiveSmallNumber();
      final T v1 = this.newVectorM4I(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4I();
      VectorM4I.add(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() + v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() + v1.getYI()), (long) vr0.getYI());
      Assert.assertEquals((long) (v0.getZI() + v1.getZI()), (long) vr0.getZI());
      Assert.assertEquals((long) (v0.getWI() + v1.getWI()), (long) vr0.getWI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        final int orig_w = v0.getWI();
        VectorM4I.addInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x + v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y + v1.getYI()), (long) v0.getYI());
        Assert.assertEquals((long) (orig_z + v1.getZI()), (long) v0.getZI());
        Assert.assertEquals((long) (orig_w + v1.getWI()), (long) v0.getWI());
      }
    }
  }

  @Test
  public final void testAddMutation()
  {
    final T out = this.newVectorM4I();
    final T v0 = this.newVectorM4I(1, 1, 1, 1);
    final T v1 = this.newVectorM4I(1, 1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());

    final T ov0 = VectorM4I.add(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());
    Assert.assertEquals(2L, (long) out.getZI());
    Assert.assertEquals(2L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());

    final T ov1 = VectorM4I.addInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());
    Assert.assertEquals(2L, (long) ov1.getZI());
    Assert.assertEquals(2L, (long) ov1.getWI());
    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());
    Assert.assertEquals(2L, (long) v0.getZI());
    Assert.assertEquals(2L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());
  }

  @Test
  public final void testAddScaled()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final int w0 = randomPositiveSmallNumber();
      final T v0 = this.newVectorM4I(x0, y0, z0, w0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final int w1 = randomPositiveSmallNumber();
      final T v1 = this.newVectorM4I(x1, y1, z1, w1);

      final int r = randomPositiveSmallNumber();

      final T vr0 = this.newVectorM4I();
      VectorM4I.addScaled(v0, v1, (double) r, vr0);

      Assert.assertEquals(
        (long) (v0.getXI() + (v1.getXI() * r)), (long) vr0.getXI());
      Assert.assertEquals(
        (long) (v0.getYI() + (v1.getYI() * r)), (long) vr0.getYI());
      Assert.assertEquals(
        (long) (v0.getZI() + (v1.getZI() * r)), (long) vr0.getZI());
      Assert.assertEquals(
        (long) (v0.getWI() + (v1.getWI() * r)), (long) vr0.getWI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        final int orig_w = v0.getWI();
        VectorM4I.addScaledInPlace(v0, v1, (double) r);

        Assert.assertEquals(
          (long) (orig_x + (v1.getXI() * r)), (long) v0.getXI());
        Assert.assertEquals(
          (long) (orig_y + (v1.getYI() * r)), (long) v0.getYI());
        Assert.assertEquals(
          (long) (orig_z + (v1.getZI() * r)), (long) v0.getZI());
        Assert.assertEquals(
          (long) (orig_w + (v1.getWI() * r)), (long) v0.getWI());
      }
    }
  }

  @Test
  public final void testAlmostEqualNot()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testAlmostEqualTransitive()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testCheckInterface()
  {
    final T v = this.newVectorM4I(3, 5, 7, 11);

    Assert.assertEquals((long) v.getXI(), (long) v.getXI());
    Assert.assertEquals((long) v.getYI(), (long) v.getYI());
    Assert.assertEquals((long) v.getZI(), (long) v.getZI());
    Assert.assertEquals((long) v.getWI(), (long) v.getWI());
  }

  @Test
  public final void testClampByVectorMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max_x = randomNegativeNumber();
      final int max_y = randomNegativeNumber();
      final int max_z = randomNegativeNumber();
      final int max_w = randomNegativeNumber();
      final T maximum = this.newVectorM4I(max_x, max_y, max_z, max_w);

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final int w = randomNegativeNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      final T vo = VectorM4I.clampMaximumByVector(v, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getWI() <= maximum.getWI());

      {
        final T vr0 = VectorM4I.clampMaximumByVectorInPlace(v, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
        Assert.assertTrue(v.getWI() <= maximum.getWI());
      }
    }
  }

  @Test
  public final void testClampByVectorMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = randomPositiveNumber();
      final int min_y = randomPositiveNumber();
      final int min_z = randomPositiveNumber();
      final int min_w = randomPositiveNumber();
      final T minimum = this.newVectorM4I(min_x, min_y, min_z, min_w);

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final int w = randomNegativeNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      final T vo = VectorM4I.clampMinimumByVector(v, minimum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
      Assert.assertTrue(vr.getWI() >= minimum.getWI());

      {
        final T vr0 = VectorM4I.clampMinimumByVectorInPlace(v, minimum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
        Assert.assertTrue(v.getWI() >= minimum.getWI());
      }
    }
  }

  @Test
  public final void testClampByVectorOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int min_x = randomNegativeNumber();
      final int min_y = randomNegativeNumber();
      final int min_z = randomNegativeNumber();
      final int min_w = randomNegativeNumber();
      final T minimum = this.newVectorM4I(min_x, min_y, min_z, min_w);

      final int max_x = randomPositiveNumber();
      final int max_y = randomPositiveNumber();
      final int max_z = randomPositiveNumber();
      final int max_w = randomPositiveNumber();
      final T maximum = this.newVectorM4I(max_x, max_y, max_z, max_w);

      final int x = randomNegativeNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final int w = randomPositiveNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      final T vo = VectorM4I.clampByVector(v, minimum, maximum, vr);

      Assert.assertEquals(vr, vo);
      Assert.assertTrue(vr.getXI() <= maximum.getXI());
      Assert.assertTrue(vr.getYI() <= maximum.getYI());
      Assert.assertTrue(vr.getZI() <= maximum.getZI());
      Assert.assertTrue(vr.getWI() <= maximum.getWI());
      Assert.assertTrue(vr.getXI() >= minimum.getXI());
      Assert.assertTrue(vr.getYI() >= minimum.getYI());
      Assert.assertTrue(vr.getZI() >= minimum.getZI());
      Assert.assertTrue(vr.getWI() >= minimum.getWI());

      {
        final T vr0 = VectorM4I.clampByVectorInPlace(v, minimum, maximum);
        Assert.assertEquals(v, vr0);
        Assert.assertTrue(v.getXI() <= maximum.getXI());
        Assert.assertTrue(v.getYI() <= maximum.getYI());
        Assert.assertTrue(v.getZI() <= maximum.getZI());
        Assert.assertTrue(v.getWI() <= maximum.getWI());
        Assert.assertTrue(v.getXI() >= minimum.getXI());
        Assert.assertTrue(v.getYI() >= minimum.getYI());
        Assert.assertTrue(v.getZI() >= minimum.getZI());
        Assert.assertTrue(v.getWI() >= minimum.getWI());
      }
    }
  }

  @Test
  public final void testClampMaximumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int maximum = randomNegativeNumber();

      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final int w = randomPositiveNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      VectorM4I.clampMaximum(v, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getWI() <= maximum);

      {
        VectorM4I.clampMaximumInPlace(v, maximum);
        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getZI() <= maximum);
        Assert.assertTrue(v.getWI() <= maximum);
      }
    }
  }

  @Test
  public final void testClampMinimumOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = randomPositiveNumber();

      final int x = randomNegativeNumber();
      final int y = randomNegativeNumber();
      final int z = randomNegativeNumber();
      final int w = randomNegativeNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      VectorM4I.clampMinimum(v, minimum, vr);

      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() >= minimum);
      Assert.assertTrue(vr.getWI() >= minimum);

      {
        VectorM4I.clampMinimumInPlace(v, minimum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() >= minimum);
        Assert.assertTrue(v.getWI() >= minimum);
      }
    }
  }

  @Test
  public final void testClampOrdering()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int minimum = randomNegativeNumber();
      final int maximum = randomPositiveNumber();

      final int x = randomNegativeNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final int w = randomPositiveNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();
      VectorM4I.clamp(v, minimum, maximum, vr);

      Assert.assertTrue(vr.getXI() <= maximum);
      Assert.assertTrue(vr.getXI() >= minimum);
      Assert.assertTrue(vr.getYI() <= maximum);
      Assert.assertTrue(vr.getYI() >= minimum);
      Assert.assertTrue(vr.getZI() <= maximum);
      Assert.assertTrue(vr.getZI() >= minimum);
      Assert.assertTrue(vr.getWI() <= maximum);
      Assert.assertTrue(vr.getWI() >= minimum);

      {
        VectorM4I.clampInPlace(v, minimum, maximum);

        Assert.assertTrue(v.getXI() <= maximum);
        Assert.assertTrue(v.getXI() >= minimum);
        Assert.assertTrue(v.getYI() <= maximum);
        Assert.assertTrue(v.getYI() >= minimum);
        Assert.assertTrue(v.getZI() <= maximum);
        Assert.assertTrue(v.getZI() >= minimum);
        Assert.assertTrue(v.getWI() <= maximum);
        Assert.assertTrue(v.getWI() >= minimum);
      }
    }
  }

  @Test
  public final void testCopy()
  {
    final T vb = this.newVectorM4I(5, 6, 7, 8);
    final T va = this.newVectorM4I(1, 2, 3, 4);

    Assert.assertNotEquals((long) vb.getXI(), (long) va.getXI());
    Assert.assertNotEquals((long) vb.getYI(), (long) va.getYI());
    Assert.assertNotEquals((long) vb.getZI(), (long) va.getZI());
    Assert.assertNotEquals((long) vb.getWI(), (long) va.getWI());

    VectorM4I.copy(va, vb);

    Assert.assertEquals((long) vb.getXI(), (long) va.getXI());
    Assert.assertEquals((long) vb.getYI(), (long) va.getYI());
    Assert.assertEquals((long) vb.getZI(), (long) va.getZI());
    Assert.assertEquals((long) vb.getWI(), (long) va.getWI());
  }

  @Test
  public final void testCopy2Correct()
  {
    final T v0 = this.newVectorM4I(
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom());
    final T v1 = this.newVectorM4I();

    v1.copyFrom2I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals(0L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());
  }

  @Test
  public final void testCopy3Correct()
  {
    final T v0 = this.newVectorM4I(
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom());
    final T v1 = this.newVectorM4I();

    v1.copyFrom4I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals((long) v0.getZI(), (long) v1.getZI());
    Assert.assertEquals((long) v0.getWI(), (long) v1.getWI());
  }

  @Test
  public final void testCopy4Correct()
  {
    final T v0 = this.newVectorM4I(
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom(),
      getLargeRandom());
    final T v1 = this.newVectorM4I();

    v1.copyFrom3I(v0);

    Assert.assertEquals((long) v0.getXI(), (long) v1.getXI());
    Assert.assertEquals((long) v0.getYI(), (long) v1.getYI());
    Assert.assertEquals((long) v0.getZI(), (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());
  }

  @Test
  public final void testDefault0001()
  {
    Assert.assertTrue(
      this.newVectorM4I().equals(this.newVectorM4I(0, 0, 0, 1)));
  }

  @Test
  public final void testDistance()
  {
    final VectorM4I.ContextVM4I c = new VectorM4I.ContextVM4I();
    final T v0 = this.newVectorM4I(0, 1, 0, 0);
    final T v1 = this.newVectorM4I(0, 0, 0, 0);
    Assert.assertEquals(1L, (long) VectorM4I.distance(c, v0, v1));
  }

  @Test
  public final void testDistanceOrdering()
  {
    final VectorM4I.ContextVM4I c = new VectorM4I.ContextVM4I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveSmallNumber();
      final int y0 = randomPositiveSmallNumber();
      final int z0 = randomPositiveSmallNumber();
      final int w0 = randomPositiveSmallNumber();
      final T v0 = this.newVectorM4I(x0, y0, z0, w0);

      final int x1 = randomPositiveSmallNumber();
      final int y1 = randomPositiveSmallNumber();
      final int z1 = randomPositiveSmallNumber();
      final int w1 = randomPositiveSmallNumber();
      final T v1 = this.newVectorM4I(x1, y1, z1, w1);

      Assert.assertTrue(VectorM4I.distance(c, v0, v1) >= 0);
    }
  }

  @Test
  public final void testDotProduct()
  {
    final T v0 = this.newVectorM4I(10, 10, 10, 10);
    final T v1 = this.newVectorM4I(10, 10, 10, 10);

    {
      final int p = VectorM4I.dotProduct(v0, v1);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(10L, (long) v0.getWI());
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(10L, (long) v1.getZI());
      Assert.assertEquals(10L, (long) v1.getWI());
      Assert.assertEquals(400L, (long) p);
    }

    {
      final int p = VectorM4I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(10L, (long) v0.getWI());
      Assert.assertEquals(400L, (long) p);
    }

    {
      final int p = VectorM4I.dotProduct(v1, v1);
      Assert.assertEquals(10L, (long) v1.getXI());
      Assert.assertEquals(10L, (long) v1.getYI());
      Assert.assertEquals(10L, (long) v1.getZI());
      Assert.assertEquals(10L, (long) v1.getWI());
      Assert.assertEquals(400L, (long) p);
    }
  }

  @Test
  public final void testDotProductPerpendicular()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (getRandom() * (double) max);
      final int y = (int) (getRandom() * (double) max);
      final int z = (int) (getRandom() * (double) max);
      final int w = (int) (getRandom() * (double) max);
      final T q = this.newVectorM4I(x, y, z, w);

      final double ms = (double) VectorM4I.magnitudeSquared(q);
      final double dp = (double) VectorM4I.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, ms, dp);
    }
  }

  @Test
  public final void testDotProductSelf()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int max = 1000;
      final int x = (int) (getRandom() * (double) max);
      final int y = (int) (getRandom() * (double) max);
      final int z = (int) (getRandom() * (double) max);
      final int w = (int) (getRandom() * (double) max);
      final T q = this.newVectorM4I(x, y, z, w);
      final double dp = (double) VectorM4I.dotProduct(q, q);


      AlmostEqualDouble.almostEqual(ec, 1.0, dp);
    }
  }

  @Test
  public final void testDotProductSelfMagnitudeSquared()
  {
    final T v0 = this.newVectorM4I(10, 10, 10, 10);

    {
      final int p = VectorM4I.dotProduct(v0, v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(10L, (long) v0.getWI());
      Assert.assertEquals(400L, (long) p);
    }

    {
      final int p = VectorM4I.magnitudeSquared(v0);
      Assert.assertEquals(10L, (long) v0.getXI());
      Assert.assertEquals(10L, (long) v0.getYI());
      Assert.assertEquals(10L, (long) v0.getZI());
      Assert.assertEquals(10L, (long) v0.getWI());
      Assert.assertEquals(400L, (long) p);
    }
  }

  @Test
  public final void testEqualsCorrect()
  {
    {
      final T m0 = this.newVectorM4I();
      Assert.assertTrue(m0.equals(m0));
    }

    {
      final T m0 = this.newVectorM4I();
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM4I();
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM4I();
      final T m1 = this.newVectorM4I();
      Assert.assertTrue(m0.equals(m1));
    }
  }

  @Test
  public final void testEqualsNotEqualCorrect()
  {
    final int x = (int) (getRandom() * 1000.0);
    final int y = x + 1;
    final int z = y + 1;
    final int w = z + 1;
    final int q = w + 1;

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      Assert.assertFalse(m0.equals(null));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      Assert.assertFalse(m0.equals(Integer.valueOf(23)));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, y, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(x, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(x, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(x, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, q, z, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, y, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, y, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, q, q, w);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, q, z, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(q, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(x, q, q, q);
      Assert.assertFalse(m0.equals(m1));
    }

    {
      final T m0 = this.newVectorM4I(x, y, z, w);
      final T m1 = this.newVectorM4I(x, y, q, q);
      Assert.assertFalse(m0.equals(m1));
    }
  }

  @Test
  public final void testHashCodeEqualsCorrect()
  {
    final T m0 = this.newVectorM4I();
    final T m1 = this.newVectorM4I();
    Assert.assertEquals((long) m0.hashCode(), (long) m1.hashCode());
  }

  @Test
  public final void testHashCodeNotEqualCorrect()
  {
    {
      final T m0 = this.newVectorM4I();
      final T m1 = this.newVectorM4I();
      m1.setXI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4I();
      final T m1 = this.newVectorM4I();
      m1.setYI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4I();
      final T m1 = this.newVectorM4I();
      m1.setZI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }

    {
      final T m0 = this.newVectorM4I();
      final T m1 = this.newVectorM4I();
      m1.setWI(23);
      Assert.assertNotEquals((long) m1.hashCode(), (long) m0.hashCode());
    }
  }

  @Test
  public final void testInitializeReadable()
  {
    final T v0 = this.newVectorM4I(1, 2, 3, 4);
    final T v1 = this.newVectorM4IFrom(v0);

    Assert.assertEquals((long) v1.getXI(), (long) v0.getXI());
    Assert.assertEquals((long) v1.getYI(), (long) v0.getYI());
    Assert.assertEquals((long) v1.getZI(), (long) v0.getZI());
    Assert.assertEquals((long) v1.getWI(), (long) v0.getWI());
  }

  @Test
  public final void testInterpolateLinearLimits()
  {
    final VectorM4I.ContextVM4I c = new VectorM4I.ContextVM4I();
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveNumber();
      final int y0 = randomPositiveNumber();
      final int z0 = randomPositiveNumber();
      final int w0 = randomPositiveNumber();
      final T v0 = this.newVectorM4I(x0, y0, z0, w0);

      final int x1 = randomPositiveNumber();
      final int y1 = randomPositiveNumber();
      final int z1 = randomPositiveNumber();
      final int w1 = randomPositiveNumber();
      final T v1 = this.newVectorM4I(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4I();
      final T vr1 = this.newVectorM4I();
      VectorM4I.interpolateLinear(c, v0, v1, 0.0, vr0);
      VectorM4I.interpolateLinear(c, v0, v1, 1.0, vr1);

      Assert.assertEquals((long) vr0.getXI(), (long) v0.getXI());
      Assert.assertEquals((long) vr0.getYI(), (long) v0.getYI());
      Assert.assertEquals((long) vr0.getZI(), (long) v0.getZI());
      Assert.assertEquals((long) vr0.getWI(), (long) v0.getWI());

      Assert.assertEquals((long) vr1.getXI(), (long) v1.getXI());
      Assert.assertEquals((long) vr1.getYI(), (long) v1.getYI());
      Assert.assertEquals((long) vr1.getZI(), (long) v1.getZI());
      Assert.assertEquals((long) vr1.getWI(), (long) v1.getWI());
    }
  }

  @Test
  public final void testMagnitudeNonzero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveSmallNumber();
      final int y = randomPositiveSmallNumber();
      final int z = randomPositiveSmallNumber();
      final int w = randomPositiveSmallNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final int m = VectorM4I.magnitude(v);
      Assert.assertTrue(m >= 1);
    }
  }

  @Test
  public final void testMagnitudeNormal()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testMagnitudeNormalizeZero()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testMagnitudeOne()
  {
    final T v = this.newVectorM4I(1, 0, 0, 0);
    final int m = VectorM4I.magnitude(v);
    Assert.assertEquals(1L, (long) m);
  }

  @Test
  public final void testMagnitudeSimple()
  {
    final T v = this.newVectorM4I(8, 0, 0, 0);

    {
      final int p = VectorM4I.dotProduct(v, v);
      final int q = VectorM4I.magnitudeSquared(v);
      final int r = VectorM4I.magnitude(v);
      Assert.assertEquals(64L, (long) p);
      Assert.assertEquals(64L, (long) q);
      Assert.assertEquals(8L, (long) r);
    }
  }

  @Test
  public final void testMagnitudeZero()
  {
    final T v = this.newVectorM4I(0, 0, 0, 0);
    final int m = VectorM4I.magnitude(v);
    Assert.assertEquals(0L, (long) m);
  }

  @Test
  public final void testNormalizeSimple()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testNormalizeZero()
  {
    // Not supported by integer vectors
  }

  @Test
  public final void testOrthonormalize()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testOrthonormalizeMutation()
  {
    // Not applicable to integer vectors
  }

  @Test
  public final void testProjectionPerpendicularZero()
  {
    {
      final T p = this.newVectorM4I(1, 0, 0, 0);
      final T q = this.newVectorM4I(0, 1, 0, 0);
      final T r = this.newVectorM4I();
      final T u = VectorM4I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) VectorM4I.magnitude(u));
    }

    {
      final T p = this.newVectorM4I(-1, 0, 0, 0);
      final T q = this.newVectorM4I(0, 1, 0, 0);
      final T r = this.newVectorM4I();
      final T u = VectorM4I.projection(p, q, r);

      Assert.assertSame(r, u);
      Assert.assertEquals(0L, (long) VectorM4I.magnitude(u));
    }
  }

  @Test
  public final void testScaleMutation()
  {
    final T out = this.newVectorM4I();
    final T v0 = this.newVectorM4I(1, 1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());

    final T ov0 = VectorM4I.scale(v0, 2.0, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(2L, (long) out.getXI());
    Assert.assertEquals(2L, (long) out.getYI());
    Assert.assertEquals(2L, (long) out.getZI());
    Assert.assertEquals(2L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());

    final T ov1 = VectorM4I.scaleInPlace(v0, 2);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(2L, (long) ov1.getXI());
    Assert.assertEquals(2L, (long) ov1.getYI());
    Assert.assertEquals(2L, (long) ov1.getZI());
    Assert.assertEquals(2L, (long) ov1.getWI());
    Assert.assertEquals(2L, (long) v0.getXI());
    Assert.assertEquals(2L, (long) v0.getYI());
    Assert.assertEquals(2L, (long) v0.getZI());
    Assert.assertEquals(2L, (long) v0.getWI());
  }

  @Test
  public final void testScaleOne()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final int w = randomPositiveNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();

      VectorM4I.scale(v, 1.0, vr);

      Assert.assertEquals((long) vr.getXI(), (long) v.getXI());
      Assert.assertEquals((long) vr.getYI(), (long) v.getYI());
      Assert.assertEquals((long) vr.getZI(), (long) v.getZI());
      Assert.assertEquals((long) vr.getWI(), (long) v.getWI());

      {
        final int orig_x = v.getXI();
        final int orig_y = v.getYI();
        final int orig_z = v.getZI();
        final int orig_w = v.getWI();

        VectorM4I.scaleInPlace(v, 1);

        Assert.assertEquals((long) orig_x, (long) v.getXI());
        Assert.assertEquals((long) orig_y, (long) v.getYI());
        Assert.assertEquals((long) orig_z, (long) v.getZI());
        Assert.assertEquals((long) orig_w, (long) v.getWI());
      }
    }
  }

  @Test
  public final void testScaleZero()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x = randomPositiveNumber();
      final int y = randomPositiveNumber();
      final int z = randomPositiveNumber();
      final int w = randomPositiveNumber();
      final T v = this.newVectorM4I(x, y, z, w);

      final T vr = this.newVectorM4I();

      VectorM4I.scale(v, 0.0, vr);

      Assert.assertEquals(0L, (long) vr.getXI());
      Assert.assertEquals(0L, (long) vr.getYI());
      Assert.assertEquals(0L, (long) vr.getZI());
      Assert.assertEquals(0L, (long) vr.getWI());

      {
        VectorM4I.scaleInPlace(v, 0);

        Assert.assertEquals(0L, (long) v.getXI());
        Assert.assertEquals(0L, (long) v.getYI());
        Assert.assertEquals(0L, (long) v.getZI());
        Assert.assertEquals(0L, (long) v.getWI());
      }
    }
  }

  @Test
  public final void testString()
  {
    final T v = this.newVectorM4I(1, 2, 3, 4);
    Assert.assertTrue(v.toString().endsWith("1 2 3 4]"));
  }

  @Test
  public final void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final int x0 = randomPositiveNumber();
      final int y0 = randomPositiveNumber();
      final int z0 = randomPositiveNumber();
      final int w0 = randomPositiveNumber();
      final T v0 = this.newVectorM4I(x0, y0, z0, w0);

      final int x1 = randomPositiveNumber();
      final int y1 = randomPositiveNumber();
      final int z1 = randomPositiveNumber();
      final int w1 = randomPositiveNumber();
      final T v1 = this.newVectorM4I(x1, y1, z1, w1);

      final T vr0 = this.newVectorM4I();
      VectorM4I.subtract(v0, v1, vr0);

      Assert.assertEquals((long) (v0.getXI() - v1.getXI()), (long) vr0.getXI());
      Assert.assertEquals((long) (v0.getYI() - v1.getYI()), (long) vr0.getYI());
      Assert.assertEquals((long) (v0.getZI() - v1.getZI()), (long) vr0.getZI());
      Assert.assertEquals((long) (v0.getWI() - v1.getWI()), (long) vr0.getWI());

      {
        final int orig_x = v0.getXI();
        final int orig_y = v0.getYI();
        final int orig_z = v0.getZI();
        final int orig_w = v0.getWI();
        VectorM4I.subtractInPlace(v0, v1);

        Assert.assertEquals((long) (orig_x - v1.getXI()), (long) v0.getXI());
        Assert.assertEquals((long) (orig_y - v1.getYI()), (long) v0.getYI());
        Assert.assertEquals((long) (orig_z - v1.getZI()), (long) v0.getZI());
        Assert.assertEquals((long) (orig_w - v1.getWI()), (long) v0.getWI());
      }
    }
  }

  @Test
  public final void testSubtractMutation()
  {
    final T out = this.newVectorM4I();
    final T v0 = this.newVectorM4I(1, 1, 1, 1);
    final T v1 = this.newVectorM4I(1, 1, 1, 1);

    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(1L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());

    final T ov0 = VectorM4I.subtract(v0, v1, out);

    Assert.assertEquals(ov0, out);
    Assert.assertEquals(0L, (long) out.getXI());
    Assert.assertEquals(0L, (long) out.getYI());
    Assert.assertEquals(0L, (long) out.getZI());
    Assert.assertEquals(0L, (long) out.getWI());
    Assert.assertEquals(1L, (long) v0.getXI());
    Assert.assertEquals(1L, (long) v0.getYI());
    Assert.assertEquals(1L, (long) v0.getZI());
    Assert.assertEquals(1L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());

    final T ov1 = VectorM4I.subtractInPlace(v0, v1);

    Assert.assertEquals(v0, ov1);
    Assert.assertEquals(0L, (long) ov1.getXI());
    Assert.assertEquals(0L, (long) ov1.getYI());
    Assert.assertEquals(0L, (long) ov1.getZI());
    Assert.assertEquals(0L, (long) ov1.getWI());
    Assert.assertEquals(0L, (long) v0.getXI());
    Assert.assertEquals(0L, (long) v0.getYI());
    Assert.assertEquals(0L, (long) v0.getZI());
    Assert.assertEquals(0L, (long) v0.getWI());
    Assert.assertEquals(1L, (long) v1.getXI());
    Assert.assertEquals(1L, (long) v1.getYI());
    Assert.assertEquals(1L, (long) v1.getZI());
    Assert.assertEquals(1L, (long) v1.getWI());
  }
}
