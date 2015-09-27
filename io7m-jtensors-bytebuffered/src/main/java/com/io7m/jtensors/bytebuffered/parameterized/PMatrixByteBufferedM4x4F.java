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

package com.io7m.jtensors.bytebuffered.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;
import com.io7m.jtensors.VectorWritable4FType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;

import java.nio.ByteBuffer;

/**
 * <p>A 4x4 matrix type with {@code float} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 */

public final class PMatrixByteBufferedM4x4F<T0, T1>
  implements PMatrixByteBuffered4x4FType<T0, T1>
{
  private final ByteBuffer buffer;
  private long offset;

  private PMatrixByteBufferedM4x4F(
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
   * @param <T0>        A phantom type parameter
   * @param <T1>        A phantom type parameter
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered matrix
   */

  public static <T0, T1> PMatrixByteBuffered4x4FType<T0, T1>
  newMatrixFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PMatrixByteBufferedM4x4F<T0, T1>(b, byte_offset);
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
    final long b = CheckedMath.add(base, (long) (index * 4));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    MatrixM4x4F.showElements(this, builder);
    return builder.toString();
  }

  private void setAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col,
    final float x)
  {
    final int i = PMatrixByteBufferedM4x4F.getByteOffsetForIndex(
      o, PMatrixByteBufferedM4x4F.indexUnsafe(row, col));
    this.buffer.putFloat(i, x);
  }

  private float getAtOffsetAndRowColumn(
    final long o,
    final int row,
    final int col)
  {
    return this.buffer.getFloat(
      PMatrixByteBufferedM4x4F.getByteOffsetForIndex(
        o, PMatrixByteBufferedM4x4F.indexUnsafe(row, col)));
  }

  @Override public <V extends VectorWritable4FType> void getRow4F(
    final int row,
    final V out)
  {
    this.getRow4FUnsafe(PMatrixByteBufferedM4x4F.checkRow(row), out);
  }

  @Override public <V extends VectorWritable4FType> void getRow4FUnsafe(
    final int row,
    final V out)
  {
    out.set4F(
      this.getAtOffsetAndRowColumn(this.offset, row, 0),
      this.getAtOffsetAndRowColumn(this.offset, row, 1),
      this.getAtOffsetAndRowColumn(this.offset, row, 2),
      this.getAtOffsetAndRowColumn(this.offset, row, 3));
  }

  @Override public int hashCode()
  {
    return MatrixM4x4F.hashElements(this);
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

    final PMatrixByteBufferedM4x4F<?, ?> other =
      (PMatrixByteBufferedM4x4F<?, ?>) obj;
    return MatrixM4x4F.compareElements(this, other);
  }

  @Override public float getR0C3F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 3);
  }

  @Override public void setR0C3F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 3, x);
  }

  @Override public float getR1C3F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 3);
  }

  @Override public void setR1C3F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 3, x);
  }

  @Override public float getR2C3F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 2, 3);
  }

  @Override public void setR2C3F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 2, 3, x);
  }

  @Override public float getR3C0F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 3, 0);
  }

  @Override public void setR3C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 3, 0, x);
  }

  @Override public float getR3C1F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 3, 1);
  }

  @Override public void setR3C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 3, 1, x);
  }

  @Override public float getR3C2F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 3, 2);
  }

  @Override public void setR3C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 3, 2, x);
  }

  @Override public float getR3C3F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 3, 3);
  }

  @Override public void setR3C3F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 3, 3, x);
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    this.getRow3FUnsafe(PMatrixByteBufferedM4x4F.checkRow(row), out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    out.set3F(
      this.getAtOffsetAndRowColumn(this.offset, row, 0),
      this.getAtOffsetAndRowColumn(this.offset, row, 1),
      this.getAtOffsetAndRowColumn(this.offset, row, 2));
  }

  @Override public float getR0C2F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 2);
  }

  @Override public void setR0C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 2, x);
  }

  @Override public float getR1C2F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 2);
  }

  @Override public void setR1C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 2, x);
  }

  @Override public float getR2C0F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 2, 0);
  }

  @Override public void setR2C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 2, 0, x);
  }

  @Override public float getR2C1F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 2, 1);
  }

  @Override public void setR2C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 2, 1, x);
  }

  @Override public float getR2C2F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 2, 2);
  }

  @Override public void setR2C2F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 2, 2, x);
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    this.getRow2FUnsafe(PMatrixByteBufferedM4x4F.checkRow(row), out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    out.set2F(
      this.getAtOffsetAndRowColumn(this.offset, row, 0),
      this.getAtOffsetAndRowColumn(this.offset, row, 1));
  }

  @Override public float getR0C0F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 0);
  }

  @Override public void setR0C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 0, x);
  }

  @Override public float getR1C0F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 0);
  }

  @Override public void setR1C0F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 0, x);
  }

  @Override public float getR0C1F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 0, 1);
  }

  @Override public void setR0C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 1, x);
  }

  @Override public float getR1C1F()
  {
    return this.getAtOffsetAndRowColumn(this.offset, 1, 1);
  }

  @Override public void setR1C1F(final float x)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 1, x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    PMatrixByteBufferedM4x4F.checkRow(row);
    PMatrixByteBufferedM4x4F.checkColumn(column);
    return this.getAtOffsetAndRowColumn(this.offset, row, column);
  }

  @Override public void setRowWith4F(
    final int row,
    final VectorReadable4FType v)
  {
    PMatrixByteBufferedM4x4F.checkRow(row);
    this.setRowWith4FUnsafe(row, v);
  }

  @Override public void setRowWith4FUnsafe(
    final int row,
    final VectorReadable4FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, row, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, row, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, row, 2, v.getZF());
    this.setAtOffsetAndRowColumn(this.offset, row, 3, v.getWF());
  }

  @Override public void setRow0With4F(final VectorReadable4FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, 0, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, 0, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, 0, 2, v.getZF());
    this.setAtOffsetAndRowColumn(this.offset, 0, 3, v.getWF());
  }

  @Override public void setRow1With4F(final VectorReadable4FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, 1, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, 1, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, 1, 2, v.getZF());
    this.setAtOffsetAndRowColumn(this.offset, 1, 3, v.getWF());
  }

  @Override public void setRow2With4F(final VectorReadable4FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, 2, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, 2, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, 2, 2, v.getZF());
    this.setAtOffsetAndRowColumn(this.offset, 2, 3, v.getWF());
  }

  @Override public void setRow3With4F(final VectorReadable4FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, 3, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, 3, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, 3, 2, v.getZF());
    this.setAtOffsetAndRowColumn(this.offset, 3, 3, v.getWF());
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    PMatrixByteBufferedM4x4F.checkRow(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, row, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, row, 1, v.getYF());
    this.setAtOffsetAndRowColumn(this.offset, row, 2, v.getZF());
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    PMatrixByteBufferedM4x4F.checkRow(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.setAtOffsetAndRowColumn(this.offset, row, 0, v.getXF());
    this.setAtOffsetAndRowColumn(this.offset, row, 1, v.getYF());
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    PMatrixByteBufferedM4x4F.checkRow(row);
    PMatrixByteBufferedM4x4F.checkColumn(column);
    this.setAtOffsetAndRowColumn(this.offset, row, column, value);
  }

  @Override public long getByteOffset()
  {
    return this.offset;
  }

  @Override public void setByteOffset(final long b)
  {
    this.offset = ByteBufferRanges.checkByteOffset(b);
  }
}
