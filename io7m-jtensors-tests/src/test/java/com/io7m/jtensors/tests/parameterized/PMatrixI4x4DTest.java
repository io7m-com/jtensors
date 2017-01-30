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

import com.io7m.jtensors.Matrix4x4DType;
import com.io7m.jtensors.MatrixHeapArrayM4x4D;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4D;
import com.io7m.jtensors.parameterized.PMatrixI4x4D;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4DType;
import org.junit.Assert;
import org.junit.Test;

public final class PMatrixI4x4DTest<T0, T1>
  extends PMatrixReadable4x4DContract<T0, T1, PMatrixI4x4D<T0, T1>>
{

  @Test
  public void testEquals()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI4x4D<T0, T1> im0 = PMatrixI4x4D.newFromReadableUntyped(m0);
    final PMatrixI4x4D<T0, T1> im1 = PMatrixI4x4D.newFromReadableUntyped(m0);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        Assert.assertEquals(
          im0.getRowColumnD(row, col), m0.getRowColumnD(row, col), 0.0);
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
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI4x4D<T0, T1> im2 = PMatrixI4x4D.newFromReadableUntyped(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test
  public void testFromColumns()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);
    m0.setR2C0D(0.2);
    m0.setR3C0D(0.3);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);
    m0.setR2C1D(10.2);
    m0.setR3C1D(10.3);

    m0.setR0C2D(20.0);
    m0.setR1C2D(20.1);
    m0.setR2C2D(20.2);
    m0.setR3C2D(20.3);

    m0.setR0C3D(30.0);
    m0.setR1C3D(30.1);
    m0.setR2C3D(30.2);
    m0.setR3C3D(30.3);

    final PMatrixI4x4D<T0, T1> im0 = PMatrixI4x4D.newFromReadableUntyped(m0);

    final VectorReadable4DType column_0 = new VectorI4D(0.0, 0.1, 0.2, 0.3);
    final VectorReadable4DType column_1 = new VectorI4D(10.0, 10.1, 10.2, 10.3);
    final VectorReadable4DType column_2 = new VectorI4D(20.0, 20.1, 20.2, 20.3);
    final VectorReadable4DType column_3 = new VectorI4D(30.0, 30.1, 30.2, 30.3);

    final PMatrixI4x4D<T0, T1> im1 =
      PMatrixI4x4D.newFromColumns(column_0, column_1, column_2, column_3);

    Assert.assertEquals(im0, im1);
  }

  @Test
  public void testFromRows()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);
    m0.setR2C0D(0.2);
    m0.setR3C0D(0.3);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);
    m0.setR2C1D(10.2);
    m0.setR3C1D(10.3);

    m0.setR0C2D(20.0);
    m0.setR1C2D(20.1);
    m0.setR2C2D(20.2);
    m0.setR3C2D(20.3);

    m0.setR0C3D(30.0);
    m0.setR1C3D(30.1);
    m0.setR2C3D(30.2);
    m0.setR3C3D(30.3);

    final PMatrixI4x4D<T0, T1> im = PMatrixI4x4D.newFromReadableUntyped(m0);

    final VectorM4D row = new VectorM4D();

    im.getRow4D(0, row);
    Assert.assertEquals(0.0, row.getXD(), 0.0);
    Assert.assertEquals(10.0, row.getYD(), 0.0);
    Assert.assertEquals(20.0, row.getZD(), 0.0);
    Assert.assertEquals(30.0, row.getWD(), 0.0);

    im.getRow4D(1, row);
    Assert.assertEquals(0.1, row.getXD(), 0.0);
    Assert.assertEquals(10.1, row.getYD(), 0.0);
    Assert.assertEquals(20.1, row.getZD(), 0.0);
    Assert.assertEquals(30.1, row.getWD(), 0.0);

    im.getRow4D(2, row);
    Assert.assertEquals(0.2, row.getXD(), 0.0);
    Assert.assertEquals(10.2, row.getYD(), 0.0);
    Assert.assertEquals(20.2, row.getZD(), 0.0);
    Assert.assertEquals(30.2, row.getWD(), 0.0);

    im.getRow4D(3, row);
    Assert.assertEquals(0.3, row.getXD(), 0.0);
    Assert.assertEquals(10.3, row.getYD(), 0.0);
    Assert.assertEquals(20.3, row.getZD(), 0.0);
    Assert.assertEquals(30.3, row.getWD(), 0.0);
  }

  @Test
  public void testIdentity()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();
    final PMatrixI4x4D<T0, T1> im0 = PMatrixI4x4D.identity();
    final PMatrixI4x4D<T0, T1> im1 = PMatrixI4x4D.newFromReadableUntyped(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test
  public void testMakeMatrix4x4DUntyped()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType m1 = MatrixHeapArrayM4x4D.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI4x4D<T0, T1> im = PMatrixI4x4D.newFromReadableUntyped(m0);
    im.makeMatrix4x4DUntyped(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test
  public void testMakeMatrix4x4D()
  {
    final PMatrix4x4DType<T0, T1> m0 = PMatrixHeapArrayM4x4D.newMatrix();
    final PMatrix4x4DType<T0, T1> m1 = PMatrixHeapArrayM4x4D.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI4x4D<T0, T1> im = PMatrixI4x4D.newFromReadableUntyped(m0);
    im.makeMatrix4x4D(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test
  public void testToString()
  {
    final Matrix4x4DType m0 = MatrixHeapArrayM4x4D.newMatrix();
    final Matrix4x4DType m1 = MatrixHeapArrayM4x4D.newMatrix();

    int index = 0;
    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        m1.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI4x4D<T0, T1> im0 = PMatrixI4x4D.newFromReadableUntyped(m0);
    final PMatrixI4x4D<T0, T1> im1 = PMatrixI4x4D.newFromReadableUntyped(m1);
    Assert.assertEquals(im0.toString(), im1.toString());
  }

  @Override
  protected PMatrixI4x4D<T0, T1> newMatrix()
  {
    return PMatrixI4x4D.identity();
  }

  @Override
  protected PMatrixI4x4D<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4DType<T0, T1> source)
  {
    return PMatrixI4x4D.newFromReadable(source);
  }

  @Override
  protected void checkDirectBufferInvariantsGeneric(
    final PMatrixI4x4D<T0, T1> m)
  {
    // Nothing required
  }
}
