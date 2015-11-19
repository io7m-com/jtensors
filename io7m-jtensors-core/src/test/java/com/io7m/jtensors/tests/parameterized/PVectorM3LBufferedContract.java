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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.VectorI3L;
import com.io7m.jtensors.parameterized.PVector3LType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM3LBufferedContract<T, V extends PVector3LType<T>>
  extends PVectorM3LContract<T, V>
{
  protected abstract PVector3LType<T> newVectorM3LAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3L(23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2L(23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3L(new VectorI3L(23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2L(new VectorI3L(23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.getZL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.setXL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.setYL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.setZL(23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.set3L(23L, 23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.set2L(23L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.copyFrom3L(new VectorI3L(23L, 23L, 23L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2L()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 100L);
    v.copyFrom2L(new VectorI3L(23L, 23L, 23L));
  }

  @Test public final void testCopyFrom3LIdentity()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 0L);
    v.copyFrom3L(new VectorI3L(23L, 24L, 25L));

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
  }

  @Test public final void testCopyFrom2LIdentity()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 0L);
    v.copyFrom2L(new VectorI3L(23L, 24L, 25L));

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
  }

  @Test public final void testSet3LIdentity()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 0L);
    v.set3L(23L, 24L, 25L);

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
    Assert.assertEquals(25L, v.getZL());
  }

  @Test public final void testSet2LIdentity()
  {
    final PVector3LType<T> v = this.newVectorM3LAtIndexFromSize(
      100L, 0L);
    v.set2L(23L, 24L);

    Assert.assertEquals(23L, v.getXL());
    Assert.assertEquals(24L, v.getYL());
  }
}