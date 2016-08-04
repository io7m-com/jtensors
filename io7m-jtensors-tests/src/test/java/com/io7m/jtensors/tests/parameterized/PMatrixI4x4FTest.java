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

import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4F;
import com.io7m.jtensors.parameterized.PMatrixI4x4F;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4FType;
import org.junit.Assert;
import org.junit.Test;

public final class PMatrixI4x4FTest<T0, T1>
  extends PMatrixReadable4x4FContract<T0, T1, PMatrixI4x4F<T0, T1>>
{

  @Test public final void testEquals()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4F<T0, T1> im0 = PMatrixI4x4F.newFromReadableUntyped(m0);
    final PMatrixI4x4F<T0, T1> im1 = PMatrixI4x4F.newFromReadableUntyped(m0);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        Assert.assertEquals(
          (double) im0.getRowColumnF(row, col),
          (double) m0.getRowColumnF(row, col),
          0.0);
      }
    }

    Assert.assertEquals(im0, im0);
    Assert.assertEquals((long) im0.hashCode(), (long) im0.hashCode());
    Assert.assertEquals(im0, im1);
    Assert.assertFalse(im0.equals(null));
    Assert.assertFalse(im0.equals(Integer.valueOf(23)));

    index = 100;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4F<T0, T1> im2 = PMatrixI4x4F.newFromReadableUntyped(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public final void testFromColumns()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);
    m0.setR2C0F(0.2f);
    m0.setR3C0F(0.3f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);
    m0.setR2C1F(10.2f);
    m0.setR3C1F(10.3f);

    m0.setR0C2F(20.0f);
    m0.setR1C2F(20.1f);
    m0.setR2C2F(20.2f);
    m0.setR3C2F(20.3f);

    m0.setR0C3F(30.0f);
    m0.setR1C3F(30.1f);
    m0.setR2C3F(30.2f);
    m0.setR3C3F(30.3f);

    final PMatrixI4x4F<T0, T1> im0 = PMatrixI4x4F.newFromReadableUntyped(m0);

    final VectorI4F column_0 = new VectorI4F(
      0.0f, 0.1f, 0.2f, 0.3f);
    final VectorI4F column_1 = new VectorI4F(
      10.0f, 10.1f, 10.2f, 10.3f);
    final VectorI4F column_2 = new VectorI4F(
      20.0f, 20.1f, 20.2f, 20.3f);
    final VectorI4F column_3 = new VectorI4F(
      30.0f, 30.1f, 30.2f, 30.3f);

    final PMatrixI4x4F<T0, T1> im1 =
      PMatrixI4x4F.newFromColumns(column_0, column_1, column_2, column_3);

    Assert.assertEquals(im0, im1);
  }

  @Test public final void testFromRows()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);
    m0.setR2C0F(0.2f);
    m0.setR3C0F(0.3f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);
    m0.setR2C1F(10.2f);
    m0.setR3C1F(10.3f);

    m0.setR0C2F(20.0f);
    m0.setR1C2F(20.1f);
    m0.setR2C2F(20.2f);
    m0.setR3C2F(20.3f);

    m0.setR0C3F(30.0f);
    m0.setR1C3F(30.1f);
    m0.setR2C3F(30.2f);
    m0.setR3C3F(30.3f);

    final PMatrixI4x4F<T0, T1> im = PMatrixI4x4F.newFromReadableUntyped(m0);

    final VectorM4F row = new VectorM4F();

    im.getRow4F(0, row);
    Assert.assertEquals(0.0, (double) row.getXF(), 0.0);
    Assert.assertEquals(10.0, (double) row.getYF(), 0.0);
    Assert.assertEquals(20.0, (double) row.getZF(), 0.0);
    Assert.assertEquals(30.0, (double) row.getWF(), 0.0);

    im.getRow4F(1, row);
    Assert.assertEquals(0.1, (double) row.getXF(), 0.000001);
    Assert.assertEquals(10.1, (double) row.getYF(), 0.000001);
    Assert.assertEquals(20.1, (double) row.getZF(), 0.000001);
    Assert.assertEquals(30.1, (double) row.getWF(), 0.000001);

    im.getRow4F(2, row);
    Assert.assertEquals(0.2, (double) row.getXF(), 0.000001);
    Assert.assertEquals(10.2, (double) row.getYF(), 0.000001);
    Assert.assertEquals(20.2, (double) row.getZF(), 0.000001);
    Assert.assertEquals(30.2, (double) row.getWF(), 0.000001);

    im.getRow4F(3, row);
    Assert.assertEquals(0.3, (double) row.getXF(), 0.000001);
    Assert.assertEquals(10.3, (double) row.getYF(), 0.000001);
    Assert.assertEquals(20.3, (double) row.getZF(), 0.000001);
    Assert.assertEquals(30.3, (double) row.getWF(), 0.000001);
  }

  @Test public final void testIdentity()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();
    final PMatrixI4x4F<T0, T1> im0 = PMatrixI4x4F.identity();
    final PMatrixI4x4F<T0, T1> im1 = PMatrixI4x4F.newFromReadableUntyped(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public final void testMakeMatrix4x4FUntyped()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType m1 = MatrixHeapArrayM4x4F.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4F<T0, T1> im = PMatrixI4x4F.newFromReadableUntyped(m0);
    im.makeMatrix4x4FUntyped(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public final void testMakeMatrix4x4F()
  {
    final PMatrix4x4FType<T0, T1> m0 = PMatrixHeapArrayM4x4F.newMatrix();
    final PMatrix4x4FType<T0, T1> m1 = PMatrixHeapArrayM4x4F.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4F<T0, T1> im = PMatrixI4x4F.newFromReadableUntyped(m0);
    im.makeMatrix4x4F(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public final void testToString()
  {
    final Matrix4x4FType m0 = MatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType m1 = MatrixHeapArrayM4x4F.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnF(row, col, index);
        m1.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final PMatrixI4x4F<T0, T1> im0 = PMatrixI4x4F.newFromReadableUntyped(m0);
    final PMatrixI4x4F<T0, T1> im1 = PMatrixI4x4F.newFromReadableUntyped(m1);
    Assert.assertEquals(im0.toString(), im1.toString());
  }

  @Override protected PMatrixI4x4F<T0, T1> newMatrix()
  {
    return PMatrixI4x4F.identity();
  }

  @Override protected PMatrixI4x4F<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4FType<T0, T1> source)
  {
    return PMatrixI4x4F.newFromReadable(source);
  }

  @Override protected void checkDirectBufferInvariants(
    final PMatrixI4x4F<T0, T1> m)
  {
    // Nothing required
  }
}