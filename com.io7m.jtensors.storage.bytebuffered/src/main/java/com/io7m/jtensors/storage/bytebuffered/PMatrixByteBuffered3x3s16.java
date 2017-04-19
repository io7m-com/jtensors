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
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;

import java.nio.ByteBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 3x3}</p>
 *
 * @param <A> A phantom type parameter (possibly representing a source
 *            coordinate system)
 * @param <B> A phantom type parameter (possibly representing a target
 *            coordinate system)
 */

public final class PMatrixByteBuffered3x3s16<A, B>
  extends TensorByteBufferedFloating16
  implements PMatrixByteBuffered3x3Type<A, B>
{
  private PMatrixByteBuffered3x3s16(
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

  public static <A, B> PMatrixByteBuffered3x3s16<A, B> createWithBase(
    final ByteBuffer b,
    final MutableLongType base,
    final int offset)
  {
    return new PMatrixByteBuffered3x3s16<>(b, base, offset);
  }

  @Override
  protected int componentCount()
  {
    return 3 * 3;
  }

  @Override
  public double r0c0()
  {
    return this.getValue(ColumnMajor.index3x3(0, 0));
  }

  @Override
  public double r0c1()
  {
    return this.getValue(ColumnMajor.index3x3(0, 1));
  }

  @Override
  public double r0c2()
  {
    return this.getValue(ColumnMajor.index3x3(0, 2));
  }

  @Override
  public double r1c0()
  {
    return this.getValue(ColumnMajor.index3x3(1, 0));
  }

  @Override
  public double r1c1()
  {
    return this.getValue(ColumnMajor.index3x3(1, 1));
  }

  @Override
  public double r1c2()
  {
    return this.getValue(ColumnMajor.index3x3(1, 2));
  }

  @Override
  public double r2c0()
  {
    return this.getValue(ColumnMajor.index3x3(2, 0));
  }

  @Override
  public double r2c1()
  {
    return this.getValue(ColumnMajor.index3x3(2, 1));
  }

  @Override
  public double r2c2()
  {
    return this.getValue(ColumnMajor.index3x3(2, 2));
  }

  @Override
  public void setPMatrix3x3D(final PMatrix3x3D<A, B> m)
  {
    this.putValue(ColumnMajor.index3x3(0, 0), m.r0c0());
    this.putValue(ColumnMajor.index3x3(0, 1), m.r0c1());
    this.putValue(ColumnMajor.index3x3(0, 2), m.r0c2());

    this.putValue(ColumnMajor.index3x3(1, 0), m.r1c0());
    this.putValue(ColumnMajor.index3x3(1, 1), m.r1c1());
    this.putValue(ColumnMajor.index3x3(1, 2), m.r1c2());

    this.putValue(ColumnMajor.index3x3(2, 0), m.r2c0());
    this.putValue(ColumnMajor.index3x3(2, 1), m.r2c1());
    this.putValue(ColumnMajor.index3x3(2, 2), m.r2c2());
  }

  @Override
  public void setPMatrix3x3F(final PMatrix3x3F<A, B> m)
  {
    this.putValue(ColumnMajor.index3x3(0, 0), (double) m.r0c0());
    this.putValue(ColumnMajor.index3x3(0, 1), (double) m.r0c1());
    this.putValue(ColumnMajor.index3x3(0, 2), (double) m.r0c2());

    this.putValue(ColumnMajor.index3x3(1, 0), (double) m.r1c0());
    this.putValue(ColumnMajor.index3x3(1, 1), (double) m.r1c1());
    this.putValue(ColumnMajor.index3x3(1, 2), (double) m.r1c2());

    this.putValue(ColumnMajor.index3x3(2, 0), (double) m.r2c0());
    this.putValue(ColumnMajor.index3x3(2, 1), (double) m.r2c1());
    this.putValue(ColumnMajor.index3x3(2, 2), (double) m.r2c2());
  }

  @Override
  public void setMatrix3x3D(final Matrix3x3D m)
  {
    this.putValue(ColumnMajor.index3x3(0, 0), m.r0c0());
    this.putValue(ColumnMajor.index3x3(0, 1), m.r0c1());
    this.putValue(ColumnMajor.index3x3(0, 2), m.r0c2());

    this.putValue(ColumnMajor.index3x3(1, 0), m.r1c0());
    this.putValue(ColumnMajor.index3x3(1, 1), m.r1c1());
    this.putValue(ColumnMajor.index3x3(1, 2), m.r1c2());

    this.putValue(ColumnMajor.index3x3(2, 0), m.r2c0());
    this.putValue(ColumnMajor.index3x3(2, 1), m.r2c1());
    this.putValue(ColumnMajor.index3x3(2, 2), m.r2c2());
  }

  @Override
  public void setMatrix3x3F(final Matrix3x3F m)
  {
    this.putValue(ColumnMajor.index3x3(0, 0), (double) m.r0c0());
    this.putValue(ColumnMajor.index3x3(0, 1), (double) m.r0c1());
    this.putValue(ColumnMajor.index3x3(0, 2), (double) m.r0c2());

    this.putValue(ColumnMajor.index3x3(1, 0), (double) m.r1c0());
    this.putValue(ColumnMajor.index3x3(1, 1), (double) m.r1c1());
    this.putValue(ColumnMajor.index3x3(1, 2), (double) m.r1c2());

    this.putValue(ColumnMajor.index3x3(2, 0), (double) m.r2c0());
    this.putValue(ColumnMajor.index3x3(2, 1), (double) m.r2c1());
    this.putValue(ColumnMajor.index3x3(2, 2), (double) m.r2c2());
  }
}
