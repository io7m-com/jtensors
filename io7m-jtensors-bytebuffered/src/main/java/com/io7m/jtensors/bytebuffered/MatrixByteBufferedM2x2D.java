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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixM2x2D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorWritable2DType;

import java.nio.ByteBuffer;

/**
 * <p>A 2x2 matrix type with {@code double} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class MatrixByteBufferedM2x2D implements Matrix2x2DType
{
  private final ByteBuffer buffer;
  private final long       offset;

  private MatrixByteBufferedM2x2D(
    final ByteBuffer in_buffer,
    final long in_offset)
  {
    this.buffer = NullCheck.notNull(in_buffer);
    this.offset = in_offset;
  }

  /**
   * <p>Return a new matrix that is backed by whatever data is at byte offset
   * {@code byte_offset} in the byte buffer {@code}.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered matrix
   */

  public static Matrix2x2DType newMatrixFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new MatrixByteBufferedM2x2D(b, byte_offset);
  }

  private static int checkColumn(
    final int column)
  {
    if ((column < 0) || (column >= 2)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 2");
    }
    return column;
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p>
   *
   * <p> (row * 2) + column, corresponds to row-major storage. (column * 2) +
   * row, corresponds to column-major (OpenGL) storage. </p>
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
    if ((row < 0) || (row >= 2)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 2");
    }
    return row;
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 8));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM2x2D.showElements(this, builder);
    return builder.toString();
  }

  private void setAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col,
    final double x)
  {
    final int i = MatrixByteBufferedM2x2D.getByteOffsetForIndex(
      o, MatrixByteBufferedM2x2D.indexUnsafe(row, col));
    this.buffer.putDouble(i, x);
  }

  private double getAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col)
  {
    return this.buffer.getDouble(
      MatrixByteBufferedM2x2D.getByteOffsetForIndex(
        o, MatrixByteBufferedM2x2D.indexUnsafe(row, col)));
  }

  @Override public int hashCode()
  {
    return MatrixM2x2D.hashElements(this);
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

    final MatrixByteBufferedM2x2D other = (MatrixByteBufferedM2x2D) obj;
    return MatrixM2x2D.compareElements(this, other);
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(MatrixByteBufferedM2x2D.checkRow(row), out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    out.set2D(
      this.getAtOffsetAndRowColumn(this.offset, row, 0),
      this.getAtOffsetAndRowColumn(this.offset, row, 1));
  }

  @Override public double getR0C0D()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 0);
  }

  @Override public void setR0C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 0, x);
  }

  @Override public double getR1C0D()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 0);
  }

  @Override public void setR1C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 0, x);
  }

  @Override public double getR0C1D()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 1);
  }

  @Override public void setR0C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 1, x);
  }

  @Override public double getR1C1D()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 1);
  }

  @Override public void setR1C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 1, x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    MatrixByteBufferedM2x2D.checkRow(row);
    MatrixByteBufferedM2x2D.checkColumn(column);
    return this.getAtOffsetAndRowColumn(this.offset, row, column);
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    MatrixByteBufferedM2x2D.checkRow(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, row, 0, v.getXD());
    this.setAtOffsetAndRowColumn(this.offset, row, 1, v.getYD());
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    MatrixByteBufferedM2x2D.checkRow(row);
    MatrixByteBufferedM2x2D.checkColumn(column);
    this.setAtOffsetAndRowColumn(this.offset, row, column, value);
  }
}
