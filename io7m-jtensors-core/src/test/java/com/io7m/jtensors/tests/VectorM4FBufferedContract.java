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

import com.io7m.jtensors.Vector4FType;
import com.io7m.jtensors.VectorI4F;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4FBufferedContract<T extends Vector4FType>
  extends VectorM4FContract<T>
{
  protected abstract Vector4FType newVectorM4FAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetW()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getWF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetW()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setWF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet4F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set4F(23.0f, 23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3F(23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom4F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom4F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.getZF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetW()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.getWF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.setZF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetW()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.setWF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet4F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.set4F(23.0f, 23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.set3F(23.0f, 23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom4F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.copyFrom4F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.copyFrom3F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2F()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 100L);
    v.copyFrom2F(new VectorI4F(23.0f, 23.0f, 23.0f, 23.0f));
  }

  @Test public final void testCopyFrom4FIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.copyFrom4F(new VectorI4F(23.0f, 24.0f, 25.0f, 26.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(26.0, (double) v.getWF(), 0.0);
  }

  @Test public final void testCopyFrom3DIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.copyFrom3F(new VectorI4F(23.0f, 24.0f, 25.0f, 26.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
  }

  @Test public final void testCopyFrom2DIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.copyFrom2F(new VectorI4F(23.0f, 24.0f, 25.0f, 26.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }

  @Test public final void testSet4FIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.set4F(23.0f, 24.0f, 25.0f, 26.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
    Assert.assertEquals(26.0, (double) v.getWF(), 0.0);
  }

  @Test public final void testSet3DIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.set3F(23.0f, 24.0f, 25.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
    Assert.assertEquals(25.0, (double) v.getZF(), 0.0);
  }

  @Test public final void testSet2DIdentity()
  {
    final Vector4FType v = this.newVectorM4FAtIndexFromSize(
      100L, 0L);
    v.set2F(23.0f, 24.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }
}
