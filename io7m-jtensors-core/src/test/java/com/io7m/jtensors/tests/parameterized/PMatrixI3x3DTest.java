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

import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.parameterized.PMatrix3x3DType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM3x3D;
import com.io7m.jtensors.parameterized.PMatrixI3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import org.junit.Assert;
import org.junit.Test;

public final class PMatrixI3x3DTest<T0, T1>
  extends PMatrixReadable3x3DContract<T0, T1, PMatrixI3x3D<T0, T1>>
{
  @Test public void testEquals()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI3x3D<T0, T1> im0 = PMatrixI3x3D.newFromReadableUntyped(m0);
    final PMatrixI3x3D<T0, T1> im1 = PMatrixI3x3D.newFromReadableUntyped(m0);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
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
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI3x3D<T0, T1> im2 = PMatrixI3x3D.newFromReadableUntyped(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public void testFromColumns()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);
    m0.setR2C0D(0.2);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);
    m0.setR2C1D(10.2);

    m0.setR0C2D(20.0);
    m0.setR1C2D(20.1);
    m0.setR2C2D(20.2);

    final PMatrixI3x3D<T0, T1> im0 = PMatrixI3x3D.newFromReadableUntyped(m0);

    final VectorI3D column_0 = new VectorI3D(0.0, 0.1, 0.2);
    final VectorI3D column_1 = new VectorI3D(10.0, 10.1, 10.2);
    final VectorI3D column_2 = new VectorI3D(20.0, 20.1, 20.2);

    final PMatrixI3x3D<T0, T1> im1 =
      PMatrixI3x3D.newFromColumns(column_0, column_1, column_2);

    Assert.assertEquals(im0, im1);
  }

  @Test public void testFromRows()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);
    m0.setR2C0D(0.2);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);
    m0.setR2C1D(10.2);

    m0.setR0C2D(20.0);
    m0.setR1C2D(20.1);
    m0.setR2C2D(20.2);

    final PMatrixI3x3D<T0, T1> im = PMatrixI3x3D.newFromReadableUntyped(m0);

    final VectorM3D row = new VectorM3D();

    im.getRow3D(0, row);
    Assert.assertEquals(0.0, row.getXD(), 0.0);
    Assert.assertEquals(10.0, row.getYD(), 0.0);
    Assert.assertEquals(20.0, row.getZD(), 0.0);

    im.getRow3D(1, row);
    Assert.assertEquals(0.1, row.getXD(), 0.0);
    Assert.assertEquals(10.1, row.getYD(), 0.0);
    Assert.assertEquals(20.1, row.getZD(), 0.0);

    im.getRow3D(2, row);
    Assert.assertEquals(0.2, row.getXD(), 0.0);
    Assert.assertEquals(10.2, row.getYD(), 0.0);
    Assert.assertEquals(20.2, row.getZD(), 0.0);
  }

  @Test public void testIdentity()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();
    final PMatrixI3x3D<T0, T1> im0 = PMatrixI3x3D.identity();
    final PMatrixI3x3D<T0, T1> im1 = PMatrixI3x3D.newFromReadableUntyped(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public void testMakeMatrix3x3DUntyped()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType m1 = MatrixHeapArrayM3x3D.newMatrix();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI3x3D<T0, T1> im = PMatrixI3x3D.newFromReadableUntyped(m0);
    im.makeMatrix3x3DUntyped(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testMakeMatrix3x3D()
  {
    final PMatrix3x3DType<T0, T1> m0 = PMatrixHeapArrayM3x3D.newMatrix();
    final PMatrix3x3DType<T0, T1> m1 = PMatrixHeapArrayM3x3D.newMatrix();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI3x3D<T0, T1> im = PMatrixI3x3D.newFromReadableUntyped(m0);
    im.makeMatrix3x3D(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testToString()
  {
    final Matrix3x3DType m0 = MatrixHeapArrayM3x3D.newMatrix();
    final Matrix3x3DType m1 = MatrixHeapArrayM3x3D.newMatrix();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        m1.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final PMatrixI3x3D<T0, T1> im0 = PMatrixI3x3D.newFromReadableUntyped(m0);
    final PMatrixI3x3D<T0, T1> im1 = PMatrixI3x3D.newFromReadableUntyped(m1);
    Assert.assertEquals(im1.toString(), im0.toString());
  }

  @Override protected PMatrixI3x3D<T0, T1> newMatrix()
  {
    return PMatrixI3x3D.identity();
  }

  @Override protected PMatrixI3x3D<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3DType<T0, T1> source)
  {
    return PMatrixI3x3D.newFromReadable(source);
  }

  @Override protected void checkDirectBufferInvariantsWildcard(
    final PMatrixReadable3x3DType<?, ?> m)
  {
    // Nothing required
  }
}
