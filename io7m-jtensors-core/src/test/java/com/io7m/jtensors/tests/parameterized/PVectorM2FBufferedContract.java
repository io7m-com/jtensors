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

import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.parameterized.PVector2FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM2FBufferedContract<T, V extends PVector2FType<T>>
  extends PVectorM2FContract<T, V>
{
  protected abstract PVector2FType<T> newVectorM2FAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2F()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2F()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2F(new VectorI2F(23.0f, 23.0f));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.getXF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.getYF();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.setXF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.setYF(23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2F()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.set2F(23.0f, 23.0f);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2F()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 100L);
    v.copyFrom2F(new VectorI2F(23.0f, 23.0f));
  }

  @Test public final void testCopyFrom2FIdentity()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 0L);
    v.copyFrom2F(new VectorI2F(23.0f, 24.0f));

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }

  @Test public final void testSet2FIdentity()
  {
    final PVector2FType<T> v = this.newVectorM2FAtIndexFromSize(
      100L, 0L);
    v.set2F(23.0f, 24.0f);

    Assert.assertEquals(23.0, (double) v.getXF(), 0.0);
    Assert.assertEquals(24.0, (double) v.getYF(), 0.0);
  }
}
