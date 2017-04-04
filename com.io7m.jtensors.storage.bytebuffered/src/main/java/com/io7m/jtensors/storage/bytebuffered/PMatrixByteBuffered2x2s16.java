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


import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;

import java.nio.ByteBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 2x2}</p>
 *
 * @param <A> A phantom type parameter (possibly representing a source
 *            coordinate system)
 * @param <B> A phantom type parameter (possibly representing a target
 *            coordinate system)
 */

public final class PMatrixByteBuffered2x2s16<A, B>
  extends TensorByteBufferedFloating16
  implements PMatrixByteBuffered2x2Type<A, B>
{
  private PMatrixByteBuffered2x2s16(
    final ByteBuffer in_buffer,
    final ByteBufferOffsetMutable in_base,
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
   * @param <A>    A phantom type parameter (possibly representing a source
   *               coordinate system)
   * @param <B>    A phantom type parameter (possibly representing a target
   *               coordinate system)
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered vector
   */

  public static <A, B> PMatrixByteBuffered2x2Type<A, B> createWithBase(
    final ByteBuffer b,
    final ByteBufferOffsetMutable base,
    final int offset)
  {
    return new PMatrixByteBuffered2x2s16<>(b, base, offset);
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
  public void setPMatrix2x2D(final PMatrix2x2D<A, B> m)
  {
    this.putValue(ColumnMajor.index2x2(0, 0), m.r0c0());
    this.putValue(ColumnMajor.index2x2(0, 1), m.r0c1());

    this.putValue(ColumnMajor.index2x2(1, 0), m.r1c0());
    this.putValue(ColumnMajor.index2x2(1, 1), m.r1c1());
  }

  @Override
  public void setPMatrix2x2F(final PMatrix2x2F<A, B> m)
  {
    this.putValue(ColumnMajor.index2x2(0, 0), (double) m.r0c0());
    this.putValue(ColumnMajor.index2x2(0, 1), (double) m.r0c1());

    this.putValue(ColumnMajor.index2x2(1, 0), (double) m.r1c0());
    this.putValue(ColumnMajor.index2x2(1, 1), (double) m.r1c1());
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
