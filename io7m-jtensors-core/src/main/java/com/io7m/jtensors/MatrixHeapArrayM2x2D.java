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

package com.io7m.jtensors;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

import java.util.Arrays;

/**
 * <p>The default implementation of the {@link Matrix2x2DType} interface.</p>
 *
 * <p>This implementation stores values in a plain on-heap array.</p>
 *
 * @since 7.0.0
 */

public final class MatrixHeapArrayM2x2D implements Matrix2x2DType
{
  private final double[][] elements;

  private MatrixHeapArrayM2x2D(final @Nullable MatrixReadable2x2DType m)
  {
    this.elements = new double[2][2];

    if (m == null) {
      MatrixM2x2D.setIdentity(this);
    } else {
      MatrixM2x2D.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static Matrix2x2DType newMatrix()
  {
    return new MatrixHeapArrayM2x2D(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static Matrix2x2DType newMatrixFrom(
    final MatrixReadable2x2DType m)
  {
    NullCheck.notNull(m);
    return new MatrixHeapArrayM2x2D(m);
  }

  @Override public boolean equals(
    final @Nullable Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }

    final MatrixHeapArrayM2x2D other = (MatrixHeapArrayM2x2D) obj;
    return Arrays.deepEquals(this.elements, other.elements);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.elements[row][column];
  }

  @Override public int hashCode()
  {
    final int prime = 21;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.elements[row][column] = value;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < 2; ++row) {
      final double c0 = this.elements[row][0];
      final double c1 = this.elements[row][1];
      final String s =
        String.format("[%+.15f %+.15f]\n", c0, c1);
      builder.append(s);
    }
    return builder.toString();
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.elements[row][0];
    final double y = this.elements[row][1];
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.elements[0][0];
  }

  @Override public void setR0C0D(final double x)
  {
    this.elements[0][0] = x;
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.elements[row][0] = v.getXD();
    this.elements[row][1] = v.getYD();
  }

  @Override public double getR1C0D()
  {
    return this.elements[1][0];
  }

  @Override public void setR1C0D(final double x)
  {
    this.elements[1][0] = x;
  }

  @Override public double getR0C1D()
  {
    return this.elements[0][1];
  }

  @Override public void setR0C1D(final double x)
  {
    this.elements[0][1] = x;
  }

  @Override public double getR1C1D()
  {
    return this.elements[1][1];
  }

  @Override public void setR1C1D(final double x)
  {
    this.elements[1][1] = x;
  }
}
