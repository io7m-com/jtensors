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

import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.parameterized.PMatrixI3x3F;
import com.io7m.jtensors.parameterized.PMatrixM3x3F;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3FType;
import org.junit.Assert;
import org.junit.Test;

public final class PMatrixI3x3FTest<T0, T1>
  extends PMatrixReadable3x3FContract<T0, T1, PMatrixI3x3F<T0, T1>>
{
  @Test public void testEquals()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnF(row, col, (float) index);
        ++index;
      }
    }

    final PMatrixI3x3F<T0, T1> im0 = PMatrixI3x3F.newFromReadableUntyped(m0);
    final PMatrixI3x3F<T0, T1> im1 = PMatrixI3x3F.newFromReadableUntyped(m0);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        Assert.assertEquals(
          (double) im0.getRowColumnF(row, col), (double) m0.getRowColumnF(row, col), 0.0);
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
        m0.setRowColumnF(row, col, (float) index);
        ++index;
      }
    }

    final PMatrixI3x3F<T0, T1> im2 = PMatrixI3x3F.newFromReadableUntyped(m0);
    Assert.assertFalse(im0.equals(im2));
  }

  @Test public void testFromColumns()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);
    m0.setR2C0F(0.2f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);
    m0.setR2C1F(10.2f);

    m0.setR0C2F(20.0f);
    m0.setR1C2F(20.1f);
    m0.setR2C2F(20.2f);

    final PMatrixI3x3F<T0, T1> im0 = PMatrixI3x3F.newFromReadableUntyped(m0);

    final VectorI3F column_0 = new VectorI3F(0.0f, 0.1f, 0.2f);
    final VectorI3F column_1 = new VectorI3F(10.0f, 10.1f, 10.2f);
    final VectorI3F column_2 = new VectorI3F(20.0f, 20.1f, 20.2f);

    final PMatrixI3x3F<T0, T1> im1 =
      PMatrixI3x3F.newFromColumns(column_0, column_1, column_2);

    Assert.assertEquals(im0, im1);
  }

  @Test public void testFromRows()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();

    m0.setR0C0F(0.0f);
    m0.setR1C0F(0.1f);
    m0.setR2C0F(0.2f);

    m0.setR0C1F(10.0f);
    m0.setR1C1F(10.1f);
    m0.setR2C1F(10.2f);

    m0.setR0C2F(20.0f);
    m0.setR1C2F(20.1f);
    m0.setR2C2F(20.2f);

    final PMatrixI3x3F<T0, T1> im = PMatrixI3x3F.newFromReadableUntyped(m0);

    final VectorM3F row = new VectorM3F();

    im.getRow3F(0, row);
    Assert.assertEquals(0.0, (double) row.getXF(), 0.0);
    Assert.assertEquals(10.0, (double) row.getYF(), 0.0);
    Assert.assertEquals(20.0, (double) row.getZF(), 0.0);

    im.getRow3F(1, row);
    Assert.assertEquals(0.1, (double) row.getXF(), 0.000001);
    Assert.assertEquals(10.1, (double) row.getYF(), 0.000001);
    Assert.assertEquals(20.1, (double) row.getZF(), 0.000001);

    im.getRow3F(2, row);
    Assert.assertEquals(0.2, (double) row.getXF(), 0.000001);
    Assert.assertEquals(10.2, (double) row.getYF(), 0.000001);
    Assert.assertEquals(20.2, (double) row.getZF(), 0.000001);
  }

  @Test public void testIdentity()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final PMatrixI3x3F<T0, T1> im0 = PMatrixI3x3F.identity();
    final PMatrixI3x3F<T0, T1> im1 = PMatrixI3x3F.newFromReadableUntyped(m0);
    Assert.assertEquals(im1, im0);
  }

  @Test public void testMakeMatrix3x3FUntyped()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnF(row, col, (float) index);
        ++index;
      }
    }

    final PMatrixI3x3F<T0, T1> im = PMatrixI3x3F.newFromReadableUntyped(m0);
    im.makeMatrix3x3FUntyped(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testMakeMatrix3x3F()
  {
    final PMatrixM3x3F<T0, T1> m0 = new PMatrixM3x3F<T0, T1>();
    final PMatrixM3x3F<T0, T1> m1 = new PMatrixM3x3F<T0, T1>();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnF(row, col, (float) index);
        ++index;
      }
    }

    final PMatrixI3x3F<T0, T1> im = PMatrixI3x3F.newFromReadableUntyped(m0);
    im.makeMatrix3x3F(m1);
    Assert.assertEquals(m0, m1);
  }

  @Test public void testToString()
  {
    final MatrixM3x3F m0 = new MatrixM3x3F();
    final MatrixM3x3F m1 = new MatrixM3x3F();

    int index = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        m0.setRowColumnF(row, col, (float) index);
        m1.setRowColumnF(row, col, (float) index);
        ++index;
      }
    }

    final PMatrixI3x3F<T0, T1> im0 = PMatrixI3x3F.newFromReadableUntyped(m0);
    final PMatrixI3x3F<T0, T1> im1 = PMatrixI3x3F.newFromReadableUntyped(m1);
    Assert.assertEquals(im1.toString(), im0.toString());
  }

  @Override protected PMatrixI3x3F<T0, T1> newMatrix()
  {
    return PMatrixI3x3F.identity();
  }

  @Override protected PMatrixI3x3F<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3FType<T0, T1> source)
  {
    return PMatrixI3x3F.newFromReadable(source);
  }

  @Override protected void checkDirectBufferInvariants(
    final PMatrixReadable3x3FType<?, ?> m)
  {
    // Nothing required
  }
}
