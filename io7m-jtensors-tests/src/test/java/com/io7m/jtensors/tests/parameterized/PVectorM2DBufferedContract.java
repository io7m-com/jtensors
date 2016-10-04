/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.parameterized.PVector2DType;
import com.io7m.jtensors.parameterized.PVectorI2D;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2DBufferedContract<T, V extends PVector2DType<T>>
  extends PVectorM2DContract<T, V>
{
  protected abstract PVector2DType<T> newVectorM2DAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2D()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2D()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2D(new PVectorI2D<T>(23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2D()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2D()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 100L);
    v.copyFrom2D(new PVectorI2D<T>(23.0, 23.0));
  }

  @Test public final void testCopyFrom2DIdentity()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 0L);
    v.copyFrom2D(new PVectorI2D<T>(23.0, 24.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }

  @Test public final void testSet2DIdentity()
  {
    final PVector2DType<T> v = this.newVectorM2DAtIndexFromSize(
      100L, 0L);
    v.set2D(23.0, 24.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }
}
