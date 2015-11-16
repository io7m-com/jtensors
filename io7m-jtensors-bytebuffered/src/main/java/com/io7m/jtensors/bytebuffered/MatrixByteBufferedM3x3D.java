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
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A 3x3 matrix type with {@code double} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class MatrixByteBufferedM3x3D extends ByteBuffered implements MatrixByteBuffered3x3DType
{
  private final ByteBuffer buffer;

  private MatrixByteBufferedM3x3D(
    final ByteBuffer in_buffer,
    final AtomicLong in_base,
    final int in_offset)
  {
    super(in_base, in_offset);
    this.buffer = NullCheck.notNull(in_buffer);
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

  public static MatrixByteBuffered3x3DType newMatrixFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new MatrixByteBufferedM3x3D(b, new AtomicLong(byte_offset), 0);
  }

  /**
   * <p>Return a new matrix that is backed by the given byte buffer {@code b}
   * </p>
   *
   * <p>The data for the instance will be taken from the data at the current
   * value of {@code base.get() + offset}, each time a field is requested or
   * set.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered matrix
   */

  public static MatrixByteBuffered3x3DType newMatrixFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new MatrixByteBufferedM3x3D(b, base, offset);
  }

  private static int checkColumn(
    final int column)
  {
    if ((column < 0) || (column >= 3)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < 3");
    }
    return column;
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p>
   *
   * <p> (row * 3) + column, corresponds to row-major storage. (column * 3) +
   * row, corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row >= 3)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 3");
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
    MatrixM3x3D.showElements(this, builder);
    return builder.toString();
  }

  private void setAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col,
    final double x)
  {
    final int i = MatrixByteBufferedM3x3D.getByteOffsetForIndex(
      o, MatrixByteBufferedM3x3D.indexUnsafe(row, col));
    this.buffer.putDouble(i, x);
  }

  private double getAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col)
  {
    return this.buffer.getDouble(
      MatrixByteBufferedM3x3D.getByteOffsetForIndex(
        o, MatrixByteBufferedM3x3D.indexUnsafe(row, col)));
  }

  @Override public int hashCode()
  {
    return MatrixM3x3D.hashElements(this);
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

    final MatrixByteBufferedM3x3D other = (MatrixByteBufferedM3x3D) obj;
    return MatrixM3x3D.compareElements(this, other);
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    this.getRow3DUnsafe(MatrixByteBufferedM3x3D.checkRow(row), out);
  }

  @Override public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final long o = super.getIndex();
    out.set3D(
      this.getAtOffsetAndRowColumn(o, row, 0),
      this.getAtOffsetAndRowColumn(o, row, 1),
      this.getAtOffsetAndRowColumn(o, row, 2));
  }

  @Override public double getR0C2D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 2);
  }

  @Override public void setR0C2D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 2, x);
  }

  @Override public double getR1C2D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 2);
  }

  @Override public void setR1C2D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 2, x);
  }

  @Override public double getR2C0D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 0);
  }

  @Override public void setR2C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 0, x);
  }

  @Override public double getR2C1D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 1);
  }

  @Override public void setR2C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 1, x);
  }

  @Override public double getR2C2D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 2);
  }

  @Override public void setR2C2D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 2, x);
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    this.getRow2DUnsafe(MatrixByteBufferedM3x3D.checkRow(row), out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final long o = super.getIndex();
    out.set2D(
      this.getAtOffsetAndRowColumn(o, row, 0),
      this.getAtOffsetAndRowColumn(o, row, 1));
  }

  @Override public double getR0C0D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 0);
  }

  @Override public void setR0C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 0, x);
  }

  @Override public double getR1C0D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 0);
  }

  @Override public void setR1C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 0, x);
  }

  @Override public double getR0C1D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 1);
  }

  @Override public void setR0C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 1, x);
  }

  @Override public double getR1C1D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 1);
  }

  @Override public void setR1C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 1, x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    MatrixByteBufferedM3x3D.checkRow(row);
    MatrixByteBufferedM3x3D.checkColumn(column);
    return this.getAtOffsetAndRowColumn(super.getIndex(), row, column);
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    MatrixByteBufferedM3x3D.checkRow(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, row, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, row, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, row, 2, v.getZD());
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    MatrixByteBufferedM3x3D.checkRow(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, row, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, row, 1, v.getYD());
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    MatrixByteBufferedM3x3D.checkRow(row);
    MatrixByteBufferedM3x3D.checkColumn(column);
    this.setAtOffsetAndRowColumn(super.getIndex(), row, column, value);
  }
}
