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

import com.io7m.jtensors.VectorI4I;
import com.io7m.jtensors.parameterized.PVector4IType;
import org.junit.Assert;
import org.junit.Test;

public abstract class PVectorM4IBufferedContract<T, V extends PVector4IType<T>>
  extends PVectorM4IContract<T, V>
{
  protected abstract PVector4IType<T> newVectorM4IAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetW()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getWI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetW()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setWI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet4I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set4I(23, 23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom4I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom4I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetW()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getWI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetW()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setWI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet4I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set4I(23, 23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom4I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom4I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom3I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2I()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom2I(new VectorI4I(23, 23, 23, 23));
  }

  @Test
  public final void testCopyFrom4IIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.copyFrom4I(new VectorI4I(23, 24, 25, 26));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
    Assert.assertEquals(26L, (long) v.getWI());
  }

  @Test
  public final void testCopyFrom3IIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.copyFrom3I(new VectorI4I(23, 24, 25, 26));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test
  public final void testCopyFrom2LIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.copyFrom2I(new VectorI4I(23, 24, 25, 26));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }

  @Test
  public final void testSet4IIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.set4I(23, 24, 25, 26);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
    Assert.assertEquals(26L, (long) v.getWI());
  }

  @Test
  public final void testSet3IIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.set3I(23, 24, 25);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test
  public final void testSet2LIdentity()
  {
    final PVector4IType<T> v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.set2I(23, 24);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }
}
