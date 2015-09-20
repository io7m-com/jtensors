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

import com.io7m.jtensors.Matrix2x2FType;
import com.io7m.jtensors.MatrixHeapArrayM2x2F;
import com.io7m.jtensors.MatrixI2x2F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public class MatrixI2x2FTest
{
  @Test public void testEquals()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final MatrixI2x2F im0 = MatrixI2x2F.newFromReadable(m0);
    final MatrixI2x2F im1 = MatrixI2x2F.newFromReadable(m0);

    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        Assert.assertEquals(
          im0.getRowColumnF(row, col), m0.getRowColumnF(row, col), 0.0);
      }
    }

    Assert.assertEquals(im0, im0);
    Assert.assertEquals(im0.hashCode(), im0.hashCode());
    Assert.assertEquals(im0, im1);
    Assert.assertFalse(im0.equals(null));
    Assert.assertFalse(im0.equals(Integer.valueOf(23)));

    index = 100;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final MatrixI2x2F im2 = MatrixI2x2F.newFromReadable(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public void testFromColumns()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);

    final MatrixI2x2F im0 = MatrixI2x2F.newFromReadable(m0);

    final VectorI3F column_0 = new VectorI3F(0.0f, 0.1f, 0.2f);
    final VectorI3F column_1 = new VectorI3F(10.0f, 10.1f, 10.2f);

    final MatrixI2x2F im1 = MatrixI2x2F.newFromColumns(column_0, column_1);

    Assert.assertEquals(im0, im1);
  }

  @Test public void testFromRows()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);

    final MatrixI2x2F im = MatrixI2x2F.newFromReadable(m0);

    final VectorM3F row = new VectorM3F();

    im.getRow2F(0, row);
    Assert.assertEquals(0.0f, row.getXF(), 0.0);
    Assert.assertEquals(10.0f, row.getYF(), 0.0);

    im.getRow2F(1, row);
    Assert.assertEquals(0.1f, row.getXF(), 0.0);
    Assert.assertEquals(10.1f, row.getYF(), 0.0);
  }

  @Test public void testIdentity()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();
    final MatrixI2x2F im0 = MatrixI2x2F.identity();
    final MatrixI2x2F im1 = MatrixI2x2F.newFromReadable(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public void testMakeMatrix2x2F()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();
    final Matrix2x2FType m1 = MatrixHeapArrayM2x2F.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final MatrixI2x2F im = MatrixI2x2F.newFromReadable(m0);
    im.makeMatrix2x2F(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testToString()
  {
    final Matrix2x2FType m0 = MatrixHeapArrayM2x2F.newMatrix();
    final Matrix2x2FType m1 = MatrixHeapArrayM2x2F.newMatrix();

    int index = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        m0.setRowColumnF(row, col, index);
        m1.setRowColumnF(row, col, index);
        ++index;
      }
    }

    final MatrixI2x2F im0 = MatrixI2x2F.newFromReadable(m0);
    final MatrixI2x2F im1 = MatrixI2x2F.newFromReadable(m1);
    Assert.assertEquals(im1.toString(), im0.toString());
  }
}
