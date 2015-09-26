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

import com.io7m.jtensors.Vector2IType;
import com.io7m.jtensors.VectorI2I;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM2IBufferedContract<T extends Vector2IType>
  extends VectorM2IContract<T>
{
  protected abstract Vector2IType newVectorM2IAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2I()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2I()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2I(new VectorI2I(23, 23));
  }


  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2I()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2I()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 100L);
    v.copyFrom2I(new VectorI2I(23, 23));
  }

  @Test public final void testCopyFrom2IIdentity()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 0L);
    v.copyFrom2I(new VectorI2I(23, 24));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }

  @Test public final void testSet2IIdentity()
  {
    final Vector2IType v = this.newVectorM2IAtIndexFromSize(
      100L, 0L);
    v.set2I(23, 24);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }
}
