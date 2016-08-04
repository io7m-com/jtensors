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

import com.io7m.jtensors.Vector4LType;
import com.io7m.jtensors.VectorI4L;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4LBufferedContract<T extends Vector4LType>
  extends VectorM4LContract<T>
{
  protected abstract Vector4LType newVectorM4LAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetW()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getWL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetW()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setWL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet4L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set4L(23L, 23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3L(23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2L(23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom4L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom4L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.getZL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetW()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.getWL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.setXL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.setYL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.setZL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetW()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.setWL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet4L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.set4L(23L, 23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.set3L(23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.set2L(23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom4L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.copyFrom4L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.copyFrom3L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2L()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 100L);
    v.copyFrom2L(new VectorI4L(23L, 23L, 23L, 23L));
  }

  @Test public final void testCopyFrom4LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.copyFrom4L(new VectorI4L(23L, 24L, 25L, 26L));

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
    Assert.assertEquals(26L, v.getWL());
  }

  @Test public final void testCopyFrom3LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.copyFrom3L(new VectorI4L(23L, 24L, 25L, 26L));

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
  }

  @Test public final void testCopyFrom2LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.copyFrom2L(new VectorI4L(23L, 24L, 25L, 26L));

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
  }

  @Test public final void testSet4LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.set4L(23L, 24L, 25L, 26L);

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
    Assert.assertEquals(26L, v.getWL());
  }

  @Test public final void testSet3LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.set3L(23L, 24L, 25L);

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
  }

  @Test public final void testSet2LIdentity()
  {
    final Vector4LType v = this.newVectorM4LAtIndexFromSize(
      100L, 0L);
    v.set2L(23L, 24L);

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
  }
}
