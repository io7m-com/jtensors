/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.storage.bytebuffered;

import com.io7m.mutable.numbers.core.MutableLongType;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;

import java.nio.ByteBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code binary16}</p>
 * <p>Storage component count: {@code 2x2}</p>
 */

public final class MatrixByteBuffered2x2s16
  extends TensorByteBufferedFloating16
  implements MatrixByteBuffered2x2Type
{
  private MatrixByteBuffered2x2s16(
    final ByteBuffer in_buffer,
    final MutableLongType in_base,
    final int in_offset)
  {
    super(in_buffer, in_base, in_offset);
  }

  /**
   * <p>Return a new vector that is backed by the given byte buffer {@code
   * b}</p>
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
   * @return A new buffered vector
   */

  public static MatrixByteBuffered2x2s16 createWithBase(
    final ByteBuffer b,
    final MutableLongType base,
    final int offset)
  {
    return new MatrixByteBuffered2x2s16(b, base, offset);
  }

  @Override
  protected int componentCount()
  {
    return 2 * 2;
  }

  @Override
  public double r0c0()
  {
    return this.getValue(ColumnMajor.index2x2(0, 0));
  }

  @Override
  public double r0c1()
  {
    return this.getValue(ColumnMajor.index2x2(0, 1));
  }

  @Override
  public double r1c0()
  {
    return this.getValue(ColumnMajor.index2x2(1, 0));
  }

  @Override
  public double r1c1()
  {
    return this.getValue(ColumnMajor.index2x2(1, 1));
  }

  @Override
  public void setMatrix2x2D(final Matrix2x2D m)
  {
    this.putValue(ColumnMajor.index2x2(0, 0), m.r0c0());
    this.putValue(ColumnMajor.index2x2(0, 1), m.r0c1());

    this.putValue(ColumnMajor.index2x2(1, 0), m.r1c0());
    this.putValue(ColumnMajor.index2x2(1, 1), m.r1c1());
  }

  @Override
  public void setMatrix2x2F(final Matrix2x2F m)
  {
    this.putValue(ColumnMajor.index2x2(0, 0), (double) m.r0c0());
    this.putValue(ColumnMajor.index2x2(0, 1), (double) m.r0c1());

    this.putValue(ColumnMajor.index2x2(1, 0), (double) m.r1c0());
    this.putValue(ColumnMajor.index2x2(1, 1), (double) m.r1c1());
  }
}
