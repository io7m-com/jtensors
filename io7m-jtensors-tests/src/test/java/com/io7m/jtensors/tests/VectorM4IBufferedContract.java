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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.Vector4IType;
import com.io7m.jtensors.VectorI4I;
import org.junit.Assert;
import org.junit.Test;

public abstract class VectorM4IBufferedContract<T extends Vector4IType>
  extends VectorM4IContract<T>
{
  protected abstract Vector4IType newVectorM4IAtIndexFromSize(
    long size,
    long offset);

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetX()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetY()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetZ()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexGetW()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.getWI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetX()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetY()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetZ()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSetW()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.setWI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet4I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set4I(23, 23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet3I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexSet2I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom4I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom4I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom3I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom3I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testHugeIndexCopyFrom2I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, (long) Integer.MAX_VALUE);
    v.copyFrom2I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetX()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getXI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetY()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getYI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetZ()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getZI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsGetW()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.getWI();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetX()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setXI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetY()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setYI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetZ()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setZI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSetW()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.setWI(23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet4I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set4I(23, 23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet3I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set3I(23, 23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsSet2I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.set2I(23, 23);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom4I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom4I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom3I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom3I(new VectorI4I(23, 23, 23, 23));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testOutOfBoundsCopyFrom2I()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 100L);
    v.copyFrom2I(new VectorI4I(23, 23, 23, 23));
  }

  @Test
  public final void testCopyFrom4IIdentity()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
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
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.copyFrom3I(new VectorI4I(23, 24, 25, 26));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test
  public final void testCopyFrom2LIdentity()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.copyFrom2I(new VectorI4I(23, 24, 25, 26));

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }

  @Test
  public final void testSet4IIdentity()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
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
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.set3I(23, 24, 25);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
    Assert.assertEquals(25L, (long) v.getZI());
  }

  @Test
  public final void testSet2LIdentity()
  {
    final Vector4IType v = this.newVectorM4IAtIndexFromSize(
      100L, 0L);
    v.set2I(23, 24);

    Assert.assertEquals(23L, (long) v.getXI());
    Assert.assertEquals(24L, (long) v.getYI());
  }
}
