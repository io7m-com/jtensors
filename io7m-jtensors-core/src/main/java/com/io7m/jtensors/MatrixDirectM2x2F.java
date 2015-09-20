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
import java.nio.FloatBuffer;

/**
 * The default implementation of the {@link MatrixDirect2x2FType} interface.
 *
 * @since 7.0.0
 */

public final class MatrixDirectM2x2F implements MatrixDirect2x2FType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 2;
    VIEW_COLS = 2;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixDirectM2x2F.VIEW_ROWS * MatrixDirectM2x2F.VIEW_COLS;
    VIEW_BYTES =
      MatrixDirectM2x2F.VIEW_ELEMENTS * MatrixDirectM2x2F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  private MatrixDirectM2x2F(final @Nullable MatrixReadable2x2FType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(MatrixDirectM2x2F.VIEW_BYTES);
    b.order(order);

    final FloatBuffer v = b.asFloatBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM2x2F.setIdentity(this);
    } else {
      MatrixM2x2F.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static MatrixDirect2x2FType newMatrix()
  {
    return new MatrixDirectM2x2F(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static MatrixDirect2x2FType newMatrixFrom(
    final MatrixReadable2x2FType m)
  {
    NullCheck.notNull(m);
    return new MatrixDirectM2x2F(m);
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixDirectM2x2F.indexUnsafe(
      MatrixDirectM2x2F.checkRow(row), MatrixDirectM2x2F.checkColumn(column));
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

  @Override public FloatBuffer getDirectFloatBuffer()
  {
    return this.view;
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    MatrixDirectM2x2F.checkRow(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(MatrixDirectM2x2F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixDirectM2x2F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    MatrixDirectM2x2F.checkRow(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixDirectM2x2F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixDirectM2x2F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(MatrixDirectM2x2F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(MatrixDirectM2x2F.indexUnsafe(0, 0), x);
  }

  @Override public float getR1C0F()
  {
    return this.view.get(MatrixDirectM2x2F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(MatrixDirectM2x2F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(MatrixDirectM2x2F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(MatrixDirectM2x2F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(MatrixDirectM2x2F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(MatrixDirectM2x2F.indexUnsafe(1, 1), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(MatrixDirectM2x2F.indexChecked(row, column));
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

    final MatrixDirectM2x2F other = (MatrixDirectM2x2F) obj;
    for (int index = 0; index < MatrixDirectM2x2F.VIEW_ELEMENTS; ++index) {
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

    r = HashUtility.accumulateDoubleHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0F(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1F(), prime, r);

    return r;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float x)
  {
    this.view.put(MatrixDirectM2x2F.indexChecked(row, column), x);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixDirectM2x2F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(MatrixDirectM2x2F.indexUnsafe(row, 0));
      final float c1 = this.view.get(MatrixDirectM2x2F.indexUnsafe(row, 1));
      final String s = String.format("[%+.6f %+.6f]\n", c0, c1);
      builder.append(s);
    }
    return builder.toString();
  }
}
