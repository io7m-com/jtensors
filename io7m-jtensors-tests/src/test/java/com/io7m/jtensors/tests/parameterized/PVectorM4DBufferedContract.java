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

import com.io7m.jtensors.parameterized.PVector4DType;
import com.io7m.jtensors.parameterized.PVectorI4D;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM4DBufferedContract<T, V extends PVector4DType<T>>
  extends PVectorM4DContract<T, V>
{
  protected abstract PVector4DType<T> newPVectorM4DAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetW()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getWD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetW()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setWD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet4D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set4D(23.0, 23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3D(23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom4D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom4D(new PVectorI4D(23.0, 23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3D(new PVectorI4D(23.0, 23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2D(new PVectorI4D(23.0, 23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.getZD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetW()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.getWD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.setZD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetW()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.setWD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet4D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.set4D(23.0, 23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.set3D(23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom4D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.copyFrom4D(new PVectorI4D<T>(23.0, 23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.copyFrom3D(new PVectorI4D<T>(23.0, 23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2D()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 100L);
    v.copyFrom2D(new PVectorI4D<T>(23.0, 23.0, 23.0, 23.0));
  }

  @Test
  public final void testCopyFrom4DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.copyFrom4D(new PVectorI4D<T>(23.0, 24.0, 25.0, 26.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
    Assert.assertEquals(26.0, v.getWD(), 0.0);
  }

  @Test
  public final void testCopyFrom3DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.copyFrom3D(new PVectorI4D<T>(23.0, 24.0, 25.0, 26.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
  }

  @Test
  public final void testCopyFrom2DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.copyFrom2D(new PVectorI4D<T>(23.0, 24.0, 25.0, 26.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }

  @Test
  public final void testSet4DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.set4D(23.0, 24.0, 25.0, 26.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
    Assert.assertEquals(26.0, v.getWD(), 0.0);
  }

  @Test
  public final void testSet3DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.set3D(23.0, 24.0, 25.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
  }

  @Test
  public final void testSet2DIdentity()
  {
    final PVector4DType<T> v = this.newPVectorM4DAtIndexFromSize(
      100L, 0L);
    v.set2D(23.0, 24.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }
}
