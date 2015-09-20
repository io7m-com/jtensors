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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>The default implementation of the {@link MatrixDirect2x2DType}
 * interface.</p>
 *
 * @since 7.0.0
 */

public final class MatrixDirectM2x2D implements MatrixDirect2x2DType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = MatrixDirectM2x2D.VIEW_ROWS * MatrixDirectM2x2D.VIEW_COLS;
    VIEW_BYTES =
      MatrixDirectM2x2D.VIEW_ELEMENTS * MatrixDirectM2x2D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  private MatrixDirectM2x2D(final @Nullable MatrixReadable2x2DType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(MatrixDirectM2x2D.VIEW_BYTES);
    b.order(order);

    final DoubleBuffer v = b.asDoubleBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM2x2D.setIdentity(this);
    } else {
      MatrixM2x2D.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static MatrixDirect2x2DType newMatrix()
  {
    return new MatrixDirectM2x2D(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static MatrixDirect2x2DType newMatrixFrom(
    final MatrixReadable2x2DType m)
  {
    NullCheck.notNull(m);
    return new MatrixDirectM2x2D(m);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixDirectM2x2D.indexUnsafe(
      MatrixDirectM2x2D.checkRow(row), MatrixDirectM2x2D.checkColumn(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 2) + column, corresponds to row-major storage. (column * 2) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row > 1)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 2");
    }
    return row;
  }

  private static int checkColumn(
    final int column)
  {
    if ((column < 0) || (column > 1)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 2");
    }
    return column;
  }

  @Override public DoubleBuffer getDirectDoubleBuffer()
  {
    return this.view;
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    MatrixDirectM2x2D.checkRow(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(MatrixDirectM2x2D.indexUnsafe(row, 0), v.getXD());
    this.view.put(MatrixDirectM2x2D.indexUnsafe(row, 1), v.getYD());
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    MatrixDirectM2x2D.checkRow(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(MatrixDirectM2x2D.indexUnsafe(row, 0));
    final double y = this.view.get(MatrixDirectM2x2D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(MatrixDirectM2x2D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(MatrixDirectM2x2D.indexUnsafe(0, 0), x);
  }

  @Override public double getR1C0D()
  {
    return this.view.get(MatrixDirectM2x2D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(MatrixDirectM2x2D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(MatrixDirectM2x2D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(MatrixDirectM2x2D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(MatrixDirectM2x2D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(MatrixDirectM2x2D.indexUnsafe(1, 1), x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(MatrixDirectM2x2D.indexChecked(row, column));
  }

  @Override public boolean equals(final Object obj)
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

    final MatrixDirectM2x2D other = (MatrixDirectM2x2D) obj;
    for (int index = 0; index < MatrixDirectM2x2D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
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
    final double x)
  {
    this.view.put(MatrixDirectM2x2D.indexChecked(row, column), x);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixDirectM2x2D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(MatrixDirectM2x2D.indexUnsafe(row, 0));
      final double c1 = this.view.get(MatrixDirectM2x2D.indexUnsafe(row, 1));
      final String s = String.format("[%+.15f %+.15f]\n", c0, c1);
      builder.append(s);
    }
    return builder.toString();
  }
}
