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

package com.io7m.jtensors.bytebuffered.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.VectorWritable4DType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;
import com.io7m.jtensors.bytebuffered.ByteBuffered;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A 4x4 matrix type with {@code double} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 */

public final class PMatrixByteBufferedM4x4D<T0, T1> extends ByteBuffered
  implements PMatrixByteBuffered4x4DType<T0, T1>
{
  private final ByteBuffer buffer;

  private PMatrixByteBufferedM4x4D(
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
   * @param <T0>        A phantom type parameter
   * @param <T1>        A phantom type parameter
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered matrix
   */

  public static <T0, T1> PMatrixByteBuffered4x4DType<T0, T1>
  newMatrixFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PMatrixByteBufferedM4x4D<T0, T1>(
      b, new AtomicLong(byte_offset), 0);
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
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered matrix
   */

  public static <T0, T1> PMatrixByteBuffered4x4DType<T0, T1>
  newMatrixFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new PMatrixByteBufferedM4x4D<T0, T1>(b, base, offset);
  }

  private static int checkColumn(
    final int column)
  {
    if ((column < 0) || (column >= 4)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= row < 4");
    }
    return column;
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p>
   *
   * <p> (row * 4) + column, corresponds to row-major storage. (column * 4) +
   * row, corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 4) + row;
  }

  private static int checkRow(
    final int row)
  {
    if ((row < 0) || (row >= 4)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < 4");
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
    MatrixM4x4D.showElements(this, builder);
    return builder.toString();
  }

  private void setAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col,
    final double x)
  {
    final int i = PMatrixByteBufferedM4x4D.getByteOffsetForIndex(
      o, PMatrixByteBufferedM4x4D.indexUnsafe(row, col));
    this.buffer.putDouble(i, x);
  }

  private double getAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col)
  {
    return this.buffer.getDouble(
      PMatrixByteBufferedM4x4D.getByteOffsetForIndex(
        o, PMatrixByteBufferedM4x4D.indexUnsafe(row, col)));
  }

  @Override public <V extends VectorWritable4DType> void getRow4D(
    final int row,
    final V out)
  {
    this.getRow4DUnsafe(PMatrixByteBufferedM4x4D.checkRow(row), out);
  }

  @Override public <V extends VectorWritable4DType> void getRow4DUnsafe(
    final int row,
    final V out)
  {
    final long o = super.getIndex();
    out.set4D(
      this.getAtOffsetAndRowColumn(o, row, 0),
      this.getAtOffsetAndRowColumn(o, row, 1),
      this.getAtOffsetAndRowColumn(o, row, 2),
      this.getAtOffsetAndRowColumn(o, row, 3));
  }

  @Override public int hashCode()
  {
    return MatrixM4x4D.hashElements(this);
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

    final PMatrixByteBufferedM4x4D<?, ?> other =
      (PMatrixByteBufferedM4x4D<?, ?>) obj;
    return MatrixM4x4D.compareElements(this, other);
  }

  @Override public double getR0C3D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 0, 3);
  }

  @Override public void setR0C3D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 0, 3, x);
  }

  @Override public double getR1C3D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 1, 3);
  }

  @Override public void setR1C3D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 1, 3, x);
  }

  @Override public double getR2C3D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 2, 3);
  }

  @Override public void setR2C3D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 2, 3, x);
  }

  @Override public double getR3C0D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 3, 0);
  }

  @Override public void setR3C0D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 3, 0, x);
  }

  @Override public double getR3C1D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 3, 1);
  }

  @Override public void setR3C1D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 3, 1, x);
  }

  @Override public double getR3C2D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 3, 2);
  }

  @Override public void setR3C2D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 3, 2, x);
  }

  @Override public double getR3C3D()
  {
    return this.getAtOffsetAndRowColumn(super.getIndex(), 3, 3);
  }

  @Override public void setR3C3D(final double x)
  {
    this.setAtOffsetAndRowColumn(super.getIndex(), 3, 3, x);
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    this.getRow3DUnsafe(PMatrixByteBufferedM4x4D.checkRow(row), out);
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
    this.getRow2DUnsafe(PMatrixByteBufferedM4x4D.checkRow(row), out);
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
    PMatrixByteBufferedM4x4D.checkRow(row);
    PMatrixByteBufferedM4x4D.checkColumn(column);
    return this.getAtOffsetAndRowColumn(super.getIndex(), row, column);
  }

  @Override public void setRowWith4D(
    final int row,
    final VectorReadable4DType v)
  {
    PMatrixByteBufferedM4x4D.checkRow(row);
    this.setRowWith4DUnsafe(row, v);
  }

  @Override public void setRowWith4DUnsafe(
    final int row,
    final VectorReadable4DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, row, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, row, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, row, 2, v.getZD());
    this.setAtOffsetAndRowColumn(o, row, 3, v.getWD());
  }

  @Override public void setRow0With4D(final VectorReadable4DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, 0, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, 0, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, 0, 2, v.getZD());
    this.setAtOffsetAndRowColumn(o, 0, 3, v.getWD());
  }

  @Override public void setRow1With4D(final VectorReadable4DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, 1, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, 1, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, 1, 2, v.getZD());
    this.setAtOffsetAndRowColumn(o, 1, 3, v.getWD());
  }

  @Override public void setRow2With4D(final VectorReadable4DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, 2, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, 2, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, 2, 2, v.getZD());
    this.setAtOffsetAndRowColumn(o, 2, 3, v.getWD());
  }

  @Override public void setRow3With4D(final VectorReadable4DType v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndRowColumn(o, 3, 0, v.getXD());
    this.setAtOffsetAndRowColumn(o, 3, 1, v.getYD());
    this.setAtOffsetAndRowColumn(o, 3, 2, v.getZD());
    this.setAtOffsetAndRowColumn(o, 3, 3, v.getWD());
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    PMatrixByteBufferedM4x4D.checkRow(row);
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
    PMatrixByteBufferedM4x4D.checkRow(row);
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
    PMatrixByteBufferedM4x4D.checkRow(row);
    PMatrixByteBufferedM4x4D.checkColumn(column);
    this.setAtOffsetAndRowColumn(super.getIndex(), row, column, value);
  }
}
