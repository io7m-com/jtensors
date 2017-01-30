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

import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.parameterized.PVector3FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3FBufferedContract<T, V extends PVector3FType<T>>
  extends PVectorM3FContract<T, V>
{
  protected abstract PVector3FType<T> newVectorM3FAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3F(23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3F(new VectorI3F(23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2F(new VectorI3F(23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.getZF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.setZF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.set3F(23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.copyFrom3F(new VectorI3F(23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2F()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 100L);
    v.copyFrom2F(new VectorI3F(23.0f, 23.0f, 23.0f));
  }

  @Test
  public final void testCopyFrom3FIdentity()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 0L);
    v.copyFrom3F(new VectorI3F(23.0f, 24.0f, 25.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
  }

  @Test
  public final void testCopyFrom2DIdentity()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 0L);
    v.copyFrom2F(new VectorI3F(23.0f, 24.0f, 25.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }

  @Test
  public final void testSet3FIdentity()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 0L);
    v.set3F(23.0f, 24.0f, 25.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
  }

  @Test
  public final void testSet2DIdentity()
  {
    final PVector3FType<T> v = this.newVectorM3FAtIndexFromSize(
      100L, 0L);
    v.set2F(23.0f, 24.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }
}
