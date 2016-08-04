/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.Vector3DType;
import com.io7m.jtensors.VectorI3D;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM3DBufferedContract<T extends Vector3DType>
  extends VectorM3DContract<T>
{
  protected abstract Vector3DType newVectorM3DAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3D(23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3D(new VectorI3D(23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2D(new VectorI3D(23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.getXD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.getYD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.getZD();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.setXD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.setYD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.setZD(23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.set3D(23.0, 23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.set2D(23.0, 23.0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.copyFrom3D(new VectorI3D(23.0, 23.0, 23.0));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2D()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 100L);
    v.copyFrom2D(new VectorI3D(23.0, 23.0, 23.0));
  }

  @Test public final void testCopyFrom3DIdentity()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 0L);
    v.copyFrom3D(new VectorI3D(23.0, 24.0, 25.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
  }

  @Test public final void testCopyFrom2DIdentity()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 0L);
    v.copyFrom2D(new VectorI3D(23.0, 24.0, 25.0));

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }

  @Test public final void testSet3DIdentity()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 0L);
    v.set3D(23.0, 24.0, 25.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
    Assert.assertEquals(25.0, v.getZD(), 0.0);
  }

  @Test public final void testSet2DIdentity()
  {
    final Vector3DType v = this.newVectorM3DAtIndexFromSize(
      100L, 0L);
    v.set2D(23.0, 24.0);

    Assert.assertEquals(23.0, v.getXD(), 0.0);
    Assert.assertEquals(24.0, v.getYD(), 0.0);
  }
}
