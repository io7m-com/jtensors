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

import com.io7m.jtensors.VectorI2L;
import com.io7m.jtensors.parameterized.PVector2LType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2LBufferedContract<T, V extends PVector2LType<T>>
  extends PVectorM2LContract<T, V>
{
  protected abstract PVector2LType<T> newVectorM2LAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXL(22L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYL(22L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2L()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2L(22L, 22L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2L()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2L(new VectorI2L(22L, 22L));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.getXL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.getYL();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.setXL(22L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.setYL(22L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2L()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.set2L(22L, 23L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2L()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 100L);
    v.copyFrom2L(new VectorI2L(22L, 23L));
  }

  @Test public final void testCopyFrom2LIdentity()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 0L);
    v.copyFrom2L(new VectorI2L(22L, 23L));

    Assert.assertEquals(22L, v.getXL());
    Assert.assertEquals(23L, v.getYL());
  }

  @Test public final void testSet2LIdentity()
  {
    final PVector2LType<T> v = this.newVectorM2LAtIndexFromSize(
      100L, 0L);
    v.set2L(22L, 23L);

    Assert.assertEquals(22L, v.getXL());
    Assert.assertEquals(23L, v.getYL());
  }
}
