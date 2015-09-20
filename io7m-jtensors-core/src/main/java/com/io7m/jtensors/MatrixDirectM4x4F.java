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
 * <p>The default implementation of the {@link MatrixDirect4x4FType}
 * interface.</p>
 *
 * @since 7.0.0
 */

public final class MatrixDirectM4x4F implements MatrixDirect4x4FType
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 4;
    VIEW_COLS = 4;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = MatrixDirectM4x4F.VIEW_ROWS * MatrixDirectM4x4F.VIEW_COLS;
    VIEW_BYTES =
      MatrixDirectM4x4F.VIEW_ELEMENTS * MatrixDirectM4x4F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  private MatrixDirectM4x4F(final @Nullable MatrixReadable4x4FType m)
  {
    final ByteOrder order = ByteOrder.nativeOrder();
    assert order != null;

    final ByteBuffer b =
      ByteBuffer.allocateDirect(MatrixDirectM4x4F.VIEW_BYTES);
    b.order(order);

    final FloatBuffer v = b.asFloatBuffer();

    this.data = b;
    this.view = v;
    this.view.clear();

    if (m == null) {
      MatrixM4x4F.setIdentity(this);
    } else {
      MatrixM4x4F.copy(m, this);
    }
  }

  /**
   * @return A new identity matrix
   */

  public static MatrixDirect4x4FType newMatrix()
  {
    return new MatrixDirectM4x4F(null);
  }

  /**
   * @param m The source matrix
   *
   * @return A new matrix based on the given matrix
   */

  public static MatrixDirect4x4FType newMatrixFrom(
    final MatrixReadable4x4FType m)
  {
    NullCheck.notNull(m);
    return new MatrixDirectM4x4F(m);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= MatrixDirectM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < "
        + MatrixDirectM4x4F.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return MatrixDirectM4x4F.indexUnsafe(
      MatrixDirectM4x4F.rowCheck(row), MatrixDirectM4x4F.columnCheck(column));
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
    return (column * MatrixDirectM4x4F.VIEW_COLS) + row;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= MatrixDirectM4x4F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + MatrixDirectM4x4F.VIEW_COLS);
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

    final MatrixDirectM4x4F other = (MatrixDirectM4x4F) obj;
    for (int index = 0; index < MatrixDirectM4x4F.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public FloatBuffer getDirectFloatBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.getRow4FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable4FType> void getRow4FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 1));
    final float z = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 2));
    final float w = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 3));
    out.set4F(x, y, z, w);
  }

  @Override public float getR0C3F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(0, 3));
  }

  @Override public void setR0C3F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 3), x);
  }

  @Override public void setRowWith4F(
    final int row,
    final VectorReadable4FType v)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.setRowWith4FUnsafe(row, v);
  }

  @Override public void setRowWith4FUnsafe(
    final int row,
    final VectorReadable4FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 2), v.getZF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 3), v.getWF());
  }

  @Override public void setRow0With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 2), v.getZF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 3), v.getWF());
  }

  @Override public void setRow1With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 2), v.getZF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 3), v.getWF());
  }

  @Override public void setRow2With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 2), v.getZF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 3), v.getWF());
  }

  @Override public void setRow3With4F(final VectorReadable4FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 2), v.getZF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 3), v.getWF());
  }

  @Override public float getR1C3F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(1, 3));
  }

  @Override public void setR1C3F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 3), x);
  }

  @Override public float getR2C3F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(2, 3));
  }

  @Override public void setR2C3F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 3), x);
  }

  @Override public float getR3C0F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(3, 0));
  }

  @Override public void setR3C0F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 0), x);
  }

  @Override public float getR3C1F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(3, 1));
  }

  @Override public void setR3C1F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 1), x);
  }

  @Override public float getR3C2F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(3, 2));
  }

  @Override public void setR3C2F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 2), x);
  }

  @Override public float getR3C3F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(3, 3));
  }

  @Override public void setR3C3F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(3, 3), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(MatrixDirectM4x4F.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C1F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C2F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C3F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR3C3F(), prime, r);

    return r;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(MatrixDirectM4x4F.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < MatrixDirectM4x4F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 0));
      final float c1 = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 1));
      final float c2 = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 2));
      final float c3 = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 3));
      final String s =
        String.format("[%+.6f %+.6f %+.6f %+.6f]\n", c0, c1, c2, c3);
      builder.append(s);
    }
    return builder.toString();
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 1));
    final float z = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 2));
    out.set3F(x, y, z);
  }

  @Override public float getR0C2F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(0, 2));
  }

  @Override public void setR0C2F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 2), x);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 1), v.getYF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 2), v.getZF());
  }

  @Override public float getR1C2F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(1, 2));
  }

  @Override public void setR1C2F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 2), x);
  }

  @Override public float getR2C0F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(2, 0));
  }

  @Override public void setR2C0F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 0), x);
  }

  @Override public float getR2C1F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(2, 1));
  }

  @Override public void setR2C1F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 1), x);
  }

  @Override public float getR2C2F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(2, 2));
  }

  @Override public void setR2C2F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(2, 2), x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 0));
    final float y = this.view.get(MatrixDirectM4x4F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 0), x);
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    MatrixDirectM4x4F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 0), v.getXF());
    this.view.put(MatrixDirectM4x4F.indexUnsafe(row, 1), v.getYF());
  }

  @Override public float getR1C0F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(MatrixDirectM4x4F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(MatrixDirectM4x4F.indexUnsafe(1, 1), x);
  }

}
