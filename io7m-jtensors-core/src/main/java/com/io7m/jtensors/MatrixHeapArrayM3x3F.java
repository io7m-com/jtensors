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
 * <p>The default implementation of the {@link Matrix3x3FType} interface.</p>
 *
 * <p>This implementation stores values in a plain on-heap array.</p>
 *
 * @since 7.0.0
 */

public final class MatrixHeapArrayM3x3F implements Matrix3x3FType
{
  private final float[][] elements;

  private MatrixHeapArrayM3x3F(final @Nullable MatrixReadable3x3FType m)
  {
    this.elements = new float[3][3];

    if (m == null) {
      MatrixM3x3F.setIdentity(this);
    } else {
      MatrixM3x3F.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static Matrix3x3FType newMatrix()
  {
    return new MatrixHeapArrayM3x3F(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static Matrix3x3FType newMatrixFrom(
    final MatrixReadable3x3FType m)
  {
    NullCheck.notNull(m);
    return new MatrixHeapArrayM3x3F(m);
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

    final MatrixHeapArrayM3x3F other = (MatrixHeapArrayM3x3F) obj;
    return Arrays.deepEquals(this.elements, other.elements);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.elements[row][column];
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C1F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C2F(), prime, r);

    return r;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.elements[row][column] = value;
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < 3; ++row) {
      final float c0 = this.elements[row][0];
      final float c1 = this.elements[row][1];
      final float c2 = this.elements[row][2];
      final String s =
        String.format("[%+.6f %+.6f %+.6f]\n", c0, c1, c2);
      builder.append(s);
    }
    return builder.toString();
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.elements[row][0];
    final float y = this.elements[row][1];
    final float z = this.elements[row][2];
    out.set3F(x, y, z);
  }

  @Override public float getR0C2F()
  {
    return this.elements[0][2];
  }

  @Override public void setR0C2F(final float x)
  {
    this.elements[0][2] = x;
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.elements[row][0] = v.getXF();
    this.elements[row][1] = v.getYF();
    this.elements[row][2] = v.getZF();
  }

  @Override public float getR1C2F()
  {
    return this.elements[1][2];
  }

  @Override public void setR1C2F(final float x)
  {
    this.elements[1][2] = x;
  }

  @Override public float getR2C0F()
  {
    return this.elements[2][0];
  }

  @Override public void setR2C0F(final float x)
  {
    this.elements[2][0] = x;
  }

  @Override public float getR2C1F()
  {
    return this.elements[2][1];
  }

  @Override public void setR2C1F(final float x)
  {
    this.elements[2][1] = x;
  }

  @Override public float getR2C2F()
  {
    return this.elements[2][2];
  }

  @Override public void setR2C2F(final float x)
  {
    this.elements[2][2] = x;
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.elements[row][0];
    final float y = this.elements[row][1];
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.elements[0][0];
  }

  @Override public void setR0C0F(final float x)
  {
    this.elements[0][0] = x;
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.elements[row][0] = v.getXF();
    this.elements[row][1] = v.getYF();
  }

  @Override public float getR1C0F()
  {
    return this.elements[1][0];
  }

  @Override public void setR1C0F(final float x)
  {
    this.elements[1][0] = x;
  }

  @Override public float getR0C1F()
  {
    return this.elements[0][1];
  }

  @Override public void setR0C1F(final float x)
  {
    this.elements[0][1] = x;
  }

  @Override public float getR1C1F()
  {
    return this.elements[1][1];
  }

  @Override public void setR1C1F(final float x)
  {
    this.elements[1][1] = x;
  }
}
