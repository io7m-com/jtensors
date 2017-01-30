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
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixReadable3x3DType;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * The default implementation of the {@link PMatrixDirect3x3DType} interface.
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public final class PMatrixDirectM3x3D<T0, T1>
  implements PMatrixDirect3x3DType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = VIEW_ROWS * VIEW_COLS;
    VIEW_BYTES =
      VIEW_ELEMENTS * VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer data;
  private final DoubleBuffer view;

  private PMatrixDirectM3x3D(final @Nullable MatrixReadable3x3DType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(VIEW_BYTES);
    b.order(order);

    final DoubleBuffer v = b.asDoubleBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM3x3D.setIdentity(this);
    } else {
      MatrixM3x3D.copy(m, this);
    }
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   *
   * @return A new identity matrix
   */

  public static <T0, T1> PMatrixDirect3x3DType<T0, T1> newMatrix()
  {
    return new PMatrixDirectM3x3D<T0, T1>(null);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect3x3DType<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM3x3D<T0, T1>(m);
  }

  /**
   * @param <T0> A phantom type parameter
   * @param <T1> A phantom type parameter
   * @param m    The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static <T0, T1> PMatrixDirect3x3DType<T0, T1> newMatrixFromUntyped(
    final MatrixReadable3x3DType m)
  {
    NullCheck.notNull(m);
    return new PMatrixDirectM3x3D<T0, T1>(m);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < "
          + VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return indexUnsafe(
      rowCheck(row), columnCheck(column));
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
    return (column * VIEW_COLS) + row;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + VIEW_COLS);
    }
    return row;
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

    final PMatrixDirectM3x3D<?, ?> other = (PMatrixDirectM3x3D<?, ?>) obj;
    return MatrixM3x3D.compareElements(this, other);
  }

  @Override
  public DoubleBuffer getDirectDoubleBuffer()
  {
    return this.view;
  }

  @Override
  public int hashCode()
  {
    return MatrixM3x3D.hashElements(this);
  }

  @Override
  public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(indexChecked(row, column), value);
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM3x3D.showElements(this, builder);
    return builder.toString();
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    rowCheck(row);
    this.getRow3DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(indexUnsafe(row, 0));
    final double y = this.view.get(indexUnsafe(row, 1));
    final double z = this.view.get(indexUnsafe(row, 2));
    out.set3D(x, y, z);
  }

  @Override
  public double getR0C2D()
  {
    return this.view.get(indexUnsafe(0, 2));
  }

  @Override
  public void setR0C2D(final double x)
  {
    this.view.put(indexUnsafe(0, 2), x);
  }

  @Override
  public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    rowCheck(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override
  public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.view.put(indexUnsafe(row, 0), v.getXD());
    this.view.put(indexUnsafe(row, 1), v.getYD());
    this.view.put(indexUnsafe(row, 2), v.getZD());
  }

  @Override
  public double getR1C2D()
  {
    return this.view.get(indexUnsafe(1, 2));
  }

  @Override
  public void setR1C2D(final double x)
  {
    this.view.put(indexUnsafe(1, 2), x);
  }

  @Override
  public double getR2C0D()
  {
    return this.view.get(indexUnsafe(2, 0));
  }

  @Override
  public void setR2C0D(final double x)
  {
    this.view.put(indexUnsafe(2, 0), x);
  }

  @Override
  public double getR2C1D()
  {
    return this.view.get(indexUnsafe(2, 1));
  }

  @Override
  public void setR2C1D(final double x)
  {
    this.view.put(indexUnsafe(2, 1), x);
  }

  @Override
  public double getR2C2D()
  {
    return this.view.get(indexUnsafe(2, 2));
  }

  @Override
  public void setR2C2D(final double x)
  {
    this.view.put(indexUnsafe(2, 2), x);
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override
  public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(indexUnsafe(row, 0));
    final double y = this.view.get(indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override
  public double getR0C0D()
  {
    return this.view.get(indexUnsafe(0, 0));
  }

  @Override
  public void setR0C0D(final double x)
  {
    this.view.put(indexUnsafe(0, 0), x);
  }

  @Override
  public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override
  public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(indexUnsafe(row, 0), v.getXD());
    this.view.put(indexUnsafe(row, 1), v.getYD());
  }

  @Override
  public double getR1C0D()
  {
    return this.view.get(indexUnsafe(1, 0));
  }

  @Override
  public void setR1C0D(final double x)
  {
    this.view.put(indexUnsafe(1, 0), x);
  }

  @Override
  public double getR0C1D()
  {
    return this.view.get(indexUnsafe(0, 1));
  }

  @Override
  public void setR0C1D(final double x)
  {
    this.view.put(indexUnsafe(0, 1), x);
  }

  @Override
  public double getR1C1D()
  {
    return this.view.get(indexUnsafe(1, 1));
  }

  @Override
  public void setR1C1D(final double x)
  {
    this.view.put(indexUnsafe(1, 1), x);
  }

  @Override
  public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(indexChecked(row, column));
  }
}
