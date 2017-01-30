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

package com.io7m.jtensors.parameterized;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;
import com.io7m.jtensors.VectorWritable4FType;

/**
 * <p>The default implementation of the {@link PMatrix4x4FType} interface.</p>
 *
 * <p>This implementation stores values in a plain on-heap array.</p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PMatrixHeapArrayM4x4F<T0, T1>
  implements PMatrix4x4FType<T0, T1>
{
  private final float[][] elements;

  private PMatrixHeapArrayM4x4F(
    final @Nullable PMatrixReadable4x4FType<T0, T1> m)
  {
    this.elements = new float[4][4];

    if (m == null) {
      MatrixM4x4F.setIdentity(this);
    } else {
      MatrixM4x4F.copy(m, this);
    }
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   *
   * @return A new identity matrix
   */

  public static <T0, T1> PMatrix4x4FType<T0, T1> newMatrix()
  {
    return new PMatrixHeapArrayM4x4F<T0, T1>(null);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrix4x4FType<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4FType<T0, T1> m)
  {
    NullCheck.notNull(m);
    return new PMatrixHeapArrayM4x4F<T0, T1>(m);
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

    final PMatrixHeapArrayM4x4F<?, ?> other = (PMatrixHeapArrayM4x4F<?, ?>) obj;
    return MatrixM4x4F.compareElements(this, other);
  }

  @Override
  public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    this.getRow4FUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable4FType> void getRow4FUnsafe(
    final int row,
    final V out)
  {
    out.set4F(
      this.elements[row][0],
      this.elements[row][1],
      this.elements[row][2],
      this.elements[row][3]);
  }

  @Override
  public float getR0C3F()
  {
    return this.elements[0][3];
  }

  @Override
  public void setR0C3F(final float x)
  {
    this.elements[0][3] = x;
  }

  @Override
  public void setRowWith4F(
    final int row,
    final VectorReadable4FType v)
  {
    this.setRowWith4FUnsafe(row, v);
  }

  @Override
  public void setRowWith4FUnsafe(
    final int row,
    final VectorReadable4FType v)
  {
    this.elements[row][0] = v.getXF();
    this.elements[row][1] = v.getYF();
    this.elements[row][2] = v.getZF();
    this.elements[row][3] = v.getWF();
  }

  @Override
  public void setRow0With4F(final VectorReadable4FType v)
  {
    this.elements[0][0] = v.getXF();
    this.elements[0][1] = v.getYF();
    this.elements[0][2] = v.getZF();
    this.elements[0][3] = v.getWF();
  }

  @Override
  public void setRow1With4F(final VectorReadable4FType v)
  {
    this.elements[1][0] = v.getXF();
    this.elements[1][1] = v.getYF();
    this.elements[1][2] = v.getZF();
    this.elements[1][3] = v.getWF();
  }

  @Override
  public void setRow2With4F(final VectorReadable4FType v)
  {
    this.elements[2][0] = v.getXF();
    this.elements[2][1] = v.getYF();
    this.elements[2][2] = v.getZF();
    this.elements[2][3] = v.getWF();
  }

  @Override
  public void setRow3With4F(final VectorReadable4FType v)
  {
    this.elements[3][0] = v.getXF();
    this.elements[3][1] = v.getYF();
    this.elements[3][2] = v.getZF();
    this.elements[3][3] = v.getWF();
  }

  @Override
  public float getR1C3F()
  {
    return this.elements[1][3];
  }

  @Override
  public void setR1C3F(final float x)
  {
    this.elements[1][3] = x;
  }

  @Override
  public float getR2C3F()
  {
    return this.elements[2][3];
  }

  @Override
  public void setR2C3F(final float x)
  {
    this.elements[2][3] = x;
  }

  @Override
  public float getR3C0F()
  {
    return this.elements[3][0];
  }

  @Override
  public void setR3C0F(final float x)
  {
    this.elements[3][0] = x;
  }

  @Override
  public float getR3C1F()
  {
    return this.elements[3][1];
  }

  @Override
  public void setR3C1F(final float x)
  {
    this.elements[3][1] = x;
  }

  @Override
  public float getR3C2F()
  {
    return this.elements[3][2];
  }

  @Override
  public void setR3C2F(final float x)
  {
    this.elements[3][2] = x;
  }

  @Override
  public float getR3C3F()
  {
    return this.elements[3][3];
  }

  @Override
  public void setR3C3F(final float x)
  {
    this.elements[3][3] = x;
  }

  @Override
  public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.elements[row][column];
  }

  @Override
  public int hashCode()
  {
    return MatrixM4x4F.hashElements(this);
  }

  @Override
  public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.elements[row][column] = value;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM4x4F.showElements(this, builder);
    return builder.toString();
  }

  @Override
  public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    this.getRow3FUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.elements[row][0];
    final float y = this.elements[row][1];
    final float z = this.elements[row][2];
    out.set3F(x, y, z);
  }

  @Override
  public float getR0C2F()
  {
    return this.elements[0][2];
  }

  @Override
  public void setR0C2F(final float x)
  {
    this.elements[0][2] = x;
  }

  @Override
  public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    this.setRowWith3FUnsafe(row, v);
  }

  @Override
  public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.elements[row][0] = v.getXF();
    this.elements[row][1] = v.getYF();
    this.elements[row][2] = v.getZF();
  }

  @Override
  public float getR1C2F()
  {
    return this.elements[1][2];
  }

  @Override
  public void setR1C2F(final float x)
  {
    this.elements[1][2] = x;
  }

  @Override
  public float getR2C0F()
  {
    return this.elements[2][0];
  }

  @Override
  public void setR2C0F(final float x)
  {
    this.elements[2][0] = x;
  }

  @Override
  public float getR2C1F()
  {
    return this.elements[2][1];
  }

  @Override
  public void setR2C1F(final float x)
  {
    this.elements[2][1] = x;
  }

  @Override
  public float getR2C2F()
  {
    return this.elements[2][2];
  }

  @Override
  public void setR2C2F(final float x)
  {
    this.elements[2][2] = x;
  }

  @Override
  public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    this.getRow2FUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.elements[row][0];
    final float y = this.elements[row][1];
    out.set2F(x, y);
  }

  @Override
  public float getR0C0F()
  {
    return this.elements[0][0];
  }

  @Override
  public void setR0C0F(final float x)
  {
    this.elements[0][0] = x;
  }

  @Override
  public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    this.setRowWith2FUnsafe(row, v);
  }

  @Override
  public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.elements[row][0] = v.getXF();
    this.elements[row][1] = v.getYF();
  }

  @Override
  public float getR1C0F()
  {
    return this.elements[1][0];
  }

  @Override
  public void setR1C0F(final float x)
  {
    this.elements[1][0] = x;
  }

  @Override
  public float getR0C1F()
  {
    return this.elements[0][1];
  }

  @Override
  public void setR0C1F(final float x)
  {
    this.elements[0][1] = x;
  }

  @Override
  public float getR1C1F()
  {
    return this.elements[1][1];
  }

  @Override
  public void setR1C1F(final float x)
  {
    this.elements[1][1] = x;
  }
}
