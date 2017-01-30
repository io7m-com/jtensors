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

package com.io7m.jtensors;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;

/**
 * <p>The default implementation of the {@link Matrix4x4DType} interface.</p>
 *
 * <p>This implementation stores values in a plain on-heap array.</p>
 *
 * @since 7.0.0
 */

public final class MatrixHeapArrayM4x4D implements Matrix4x4DType
{
  private final double[][] elements;

  private MatrixHeapArrayM4x4D(final @Nullable MatrixReadable4x4DType m)
  {
    this.elements = new double[4][4];

    if (m == null) {
      MatrixM4x4D.setIdentity(this);
    } else {
      MatrixM4x4D.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static Matrix4x4DType newMatrix()
  {
    return new MatrixHeapArrayM4x4D(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static Matrix4x4DType newMatrixFrom(
    final MatrixReadable4x4DType m)
  {
    NullCheck.notNull(m);
    return new MatrixHeapArrayM4x4D(m);
  }

  @Override
  public boolean equals(
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

    final MatrixHeapArrayM4x4D other = (MatrixHeapArrayM4x4D) obj;
    return MatrixM4x4D.compareElements(this, other);
  }

  @Override
  public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    this.getRow4DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable4DType> void getRow4DUnsafe(
    final int row,
    final V out)
  {
    out.set4D(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  @Override
  public double getR0C3D()
  {
    return this.elements[0][3];
  }

  @Override
  public void setR0C3D(final double x)
  {
    this.elements[0][3] = x;
  }

  @Override
  public void setRowWith4D(
    final int row,
    final VectorReadable4DType v)
  {
    this.setRowWith4DUnsafe(row, v);
  }

  @Override
  public void setRowWith4DUnsafe(
    final int row,
    final VectorReadable4DType v)
  {
    this.elements[row][0] = v.getXD();
    this.elements[row][1] = v.getYD();
    this.elements[row][2] = v.getZD();
    this.elements[row][3] = v.getWD();
  }

  @Override
  public void setRow0With4D(final VectorReadable4DType v)
  {
    this.elements[0][0] = v.getXD();
    this.elements[0][1] = v.getYD();
    this.elements[0][2] = v.getZD();
    this.elements[0][3] = v.getWD();
  }

  @Override
  public void setRow1With4D(final VectorReadable4DType v)
  {
    this.elements[1][0] = v.getXD();
    this.elements[1][1] = v.getYD();
    this.elements[1][2] = v.getZD();
    this.elements[1][3] = v.getWD();
  }

  @Override
  public void setRow2With4D(final VectorReadable4DType v)
  {
    this.elements[2][0] = v.getXD();
    this.elements[2][1] = v.getYD();
    this.elements[2][2] = v.getZD();
    this.elements[2][3] = v.getWD();
  }

  @Override
  public void setRow3With4D(final VectorReadable4DType v)
  {
    this.elements[3][0] = v.getXD();
    this.elements[3][1] = v.getYD();
    this.elements[3][2] = v.getZD();
    this.elements[3][3] = v.getWD();
  }

  @Override
  public double getR1C3D()
  {
    return this.elements[1][3];
  }

  @Override
  public void setR1C3D(final double x)
  {
    this.elements[1][3] = x;
  }

  @Override
  public double getR2C3D()
  {
    return this.elements[2][3];
  }

  @Override
  public void setR2C3D(final double x)
  {
    this.elements[2][3] = x;
  }

  @Override
  public double getR3C0D()
  {
    return this.elements[3][0];
  }

  @Override
  public void setR3C0D(final double x)
  {
    this.elements[3][0] = x;
  }

  @Override
  public double getR3C1D()
  {
    return this.elements[3][1];
  }

  @Override
  public void setR3C1D(final double x)
  {
    this.elements[3][1] = x;
  }

  @Override
  public double getR3C2D()
  {
    return this.elements[3][2];
  }

  @Override
  public void setR3C2D(final double x)
  {
    this.elements[3][2] = x;
  }

  @Override
  public double getR3C3D()
  {
    return this.elements[3][3];
  }

  @Override
  public void setR3C3D(final double x)
  {
    this.elements[3][3] = x;
  }

  @Override
  public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.elements[row][column];
  }

  @Override
  public int hashCode()
  {
    return MatrixM4x4D.hashElements(this);
  }

  @Override
  public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.elements[row][column] = value;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM4x4D.showElements(this, builder);
    return builder.toString();
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    this.getRow3DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.elements[row][0];
    final double y = this.elements[row][1];
    final double z = this.elements[row][2];
    out.set3D(x, y, z);
  }

  @Override
  public double getR0C2D()
  {
    return this.elements[0][2];
  }

  @Override
  public void setR0C2D(final double x)
  {
    this.elements[0][2] = x;
  }

  @Override
  public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    this.setRowWith3DUnsafe(row, v);
  }

  @Override
  public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.elements[row][0] = v.getXD();
    this.elements[row][1] = v.getYD();
    this.elements[row][2] = v.getZD();
  }

  @Override
  public double getR1C2D()
  {
    return this.elements[1][2];
  }

  @Override
  public void setR1C2D(final double x)
  {
    this.elements[1][2] = x;
  }

  @Override
  public double getR2C0D()
  {
    return this.elements[2][0];
  }

  @Override
  public void setR2C0D(final double x)
  {
    this.elements[2][0] = x;
  }

  @Override
  public double getR2C1D()
  {
    return this.elements[2][1];
  }

  @Override
  public void setR2C1D(final double x)
  {
    this.elements[2][1] = x;
  }

  @Override
  public double getR2C2D()
  {
    return this.elements[2][2];
  }

  @Override
  public void setR2C2D(final double x)
  {
    this.elements[2][2] = x;
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.elements[row][0];
    final double y = this.elements[row][1];
    out.set2D(x, y);
  }

  @Override
  public double getR0C0D()
  {
    return this.elements[0][0];
  }

  @Override
  public void setR0C0D(final double x)
  {
    this.elements[0][0] = x;
  }

  @Override
  public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    this.setRowWith2DUnsafe(row, v);
  }

  @Override
  public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.elements[row][0] = v.getXD();
    this.elements[row][1] = v.getYD();
  }

  @Override
  public double getR1C0D()
  {
    return this.elements[1][0];
  }

  @Override
  public void setR1C0D(final double x)
  {
    this.elements[1][0] = x;
  }

  @Override
  public double getR0C1D()
  {
    return this.elements[0][1];
  }

  @Override
  public void setR0C1D(final double x)
  {
    this.elements[0][1] = x;
  }

  @Override
  public double getR1C1D()
  {
    return this.elements[1][1];
  }

  @Override
  public void setR1C1D(final double x)
  {
    this.elements[1][1] = x;
  }
}
