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

import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixHeapArrayM2x2D;
import com.io7m.jtensors.MatrixI2x2D;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorM3D;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class MatrixI2x2DTest
{
  @Test public void testEquals()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final MatrixI2x2D im0 = MatrixI2x2D.newFromReadable(m0);
    final MatrixI2x2D im1 = MatrixI2x2D.newFromReadable(m0);

    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
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
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final MatrixI2x2D im2 = MatrixI2x2D.newFromReadable(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public void testFromColumns()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);

    final MatrixI2x2D im0 = MatrixI2x2D.newFromReadable(m0);

    final VectorI3D column_0 = new VectorI3D(0.0, 0.1, 0.2);
    final VectorI3D column_1 = new VectorI3D(10.0, 10.1, 10.2);

    final MatrixI2x2D im1 = MatrixI2x2D.newFromColumns(column_0, column_1);

    Assert.assertEquals(im0, im1);
  }

  @Test public void testFromRows()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();

    m0.setR0C0D(0.0);
    m0.setR1C0D(0.1);

    m0.setR0C1D(10.0);
    m0.setR1C1D(10.1);

    final MatrixI2x2D im = MatrixI2x2D.newFromReadable(m0);

    final VectorM3D row = new VectorM3D();

    im.getRow2D(0, row);
    Assert.assertEquals(0.0, row.getXD(), 0.0);
    Assert.assertEquals(10.0, row.getYD(), 0.0);

    im.getRow2D(1, row);
    Assert.assertEquals(0.1, row.getXD(), 0.0);
    Assert.assertEquals(10.1, row.getYD(), 0.0);
  }

  @Test public void testIdentity()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();
    final MatrixI2x2D im0 = MatrixI2x2D.identity();
    final MatrixI2x2D im1 = MatrixI2x2D.newFromReadable(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public void testMakeMatrix2x2D()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();
    final Matrix2x2DType m1 = MatrixHeapArrayM2x2D.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final MatrixI2x2D im = MatrixI2x2D.newFromReadable(m0);
    im.makeMatrix2x2D(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testToString()
  {
    final Matrix2x2DType m0 = MatrixHeapArrayM2x2D.newMatrix();
    final Matrix2x2DType m1 = MatrixHeapArrayM2x2D.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnD(row, col, (double) index);
        m1.setRowColumnD(row, col, (double) index);
        ++index;
      }
    }

    final MatrixI2x2D im0 = MatrixI2x2D.newFromReadable(m0);
    final MatrixI2x2D im1 = MatrixI2x2D.newFromReadable(m1);
    Assert.assertEquals(im1.toString(), im0.toString());
  }
}
