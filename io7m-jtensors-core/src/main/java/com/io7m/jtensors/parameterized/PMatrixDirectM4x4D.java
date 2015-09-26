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

package com.io7m.jtensors.parameterized;

import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.HashUtility;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixReadable4x4DType;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.VectorWritable4DType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * The default implementation of the {@link PMatrixDirect4x4DType} interface.
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PMatrixDirectM4x4D<T0, T1>
  implements PMatrixDirect4x4DType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = PMatrixDirectM4x4D.VIEW_ROWS * PMatrixDirectM4x4D.VIEW_COLS;
    VIEW_BYTES =
      PMatrixDirectM4x4D.VIEW_ELEMENTS * PMatrixDirectM4x4D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  private PMatrixDirectM4x4D(final @Nullable MatrixReadable4x4DType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(PMatrixDirectM4x4D.VIEW_BYTES);
    b.order(order);

    final DoubleBuffer v = b.asDoubleBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM4x4D.setIdentity(this);
    } else {
      MatrixM4x4D.copy(m, this);
    }
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   *
   * @return A new identity matrix
   */

  public static <T0, T1> PMatrixDirect4x4DType<T0, T1> newMatrix()
  {
    return new PMatrixDirectM4x4D<T0, T1>(null);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect4x4DType<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4DType<T0, T1> m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM4x4D<T0, T1>(m);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect4x4DType<T0, T1> newMatrixFromUntyped(
    final MatrixReadable4x4DType m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM4x4D<T0, T1>(m);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixDirectM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < "
        + PMatrixDirectM4x4D.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixDirectM4x4D.indexUnsafe(
      PMatrixDirectM4x4D.rowCheck(row), PMatrixDirectM4x4D.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 4) + column, corresponds to row-major storage. (column * 4) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * PMatrixDirectM4x4D.VIEW_COLS) + row;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixDirectM4x4D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixDirectM4x4D.VIEW_COLS);
    }
    return row;
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
    final PMatrixDirectM4x4D<?, ?> other = (PMatrixDirectM4x4D<?, ?>) obj;
    for (int index = 0; index < PMatrixDirectM4x4D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public DoubleBuffer getDirectDoubleBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.getRow4DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable4DType> void getRow4DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 1));
    final double z = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 2));
    final double w = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 3));
    out.set4D(x, y, z, w);
  }

  @Override public double getR0C3D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(0, 3));
  }

  @Override public void setR0C3D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 3), x);
  }

  @Override public void setRowWith4D(
    final int row,
    final VectorReadable4DType v)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.setRowWith4DUnsafe(row, v);
  }

  @Override public void setRowWith4DUnsafe(
    final int row,
    final VectorReadable4DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 2), v.getZD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 3), v.getWD());
  }

  @Override public void setRow0With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 2), v.getZD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 3), v.getWD());
  }

  @Override public void setRow1With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 2), v.getZD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 3), v.getWD());
  }

  @Override public void setRow2With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 2), v.getZD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 3), v.getWD());
  }

  @Override public void setRow3With4D(final VectorReadable4DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 2), v.getZD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 3), v.getWD());
  }

  @Override public double getR1C3D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(1, 3));
  }

  @Override public void setR1C3D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 3), x);
  }

  @Override public double getR2C3D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(2, 3));
  }

  @Override public void setR2C3D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 3), x);
  }

  @Override public double getR3C0D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(3, 0));
  }

  @Override public void setR3C0D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 0), x);
  }

  @Override public double getR3C1D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(3, 1));
  }

  @Override public void setR3C1D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 1), x);
  }

  @Override public double getR3C2D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(3, 2));
  }

  @Override public void setR3C2D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 2), x);
  }

  @Override public double getR3C3D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(3, 3));
  }

  @Override public void setR3C3D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(3, 3), x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixDirectM4x4D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C1D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C2D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C3D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR3C3D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixDirectM4x4D.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < PMatrixDirectM4x4D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 0));
      final double c1 = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 1));
      final double c2 = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 2));
      final double c3 = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.15f %+.15f %+.15f %+.15f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    return builder.toString();
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.getRow3DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 1));
    final double z = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 2));
    out.set3D(x, y, z);
  }

  @Override public double getR0C2D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(0, 2));
  }

  @Override public void setR0C2D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 1), v.getYD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 2), v.getZD());
  }

  @Override public double getR1C2D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(1, 2));
  }

  @Override public void setR1C2D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 2), x);
  }

  @Override public double getR2C0D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(2, 0));
  }

  @Override public void setR2C0D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 0), x);
  }

  @Override public double getR2C1D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(2, 1));
  }

  @Override public void setR2C1D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 1), x);
  }

  @Override public double getR2C2D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(2, 2));
  }

  @Override public void setR2C2D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixDirectM4x4D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    PMatrixDirectM4x4D.rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(row, 1), v.getYD());
  }

  @Override public double getR1C0D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(PMatrixDirectM4x4D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(PMatrixDirectM4x4D.indexUnsafe(1, 1), x);
  }

}
