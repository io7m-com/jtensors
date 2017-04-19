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
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;

import java.nio.ByteBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code float}</p>
 * <p>Storage component count: {@code 4x4}</p>
 */

public final class MatrixByteBuffered4x4s32
  extends TensorByteBufferedFloating32
  implements MatrixByteBuffered4x4Type
{
  private MatrixByteBuffered4x4s32(
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

  public static MatrixByteBuffered4x4s32 createWithBase(
    final ByteBuffer b,
    final MutableLongType base,
    final int offset)
  {
    return new MatrixByteBuffered4x4s32(b, base, offset);
  }

  @Override
  protected int componentCount()
  {
    return 4 * 4;
  }

  @Override
  public double r0c0()
  {
    return this.getValue(ColumnMajor.index4x4(0, 0));
  }

  @Override
  public double r0c1()
  {
    return this.getValue(ColumnMajor.index4x4(0, 1));
  }

  @Override
  public double r0c2()
  {
    return this.getValue(ColumnMajor.index4x4(0, 2));
  }

  @Override
  public double r0c3()
  {
    return this.getValue(ColumnMajor.index4x4(0, 3));
  }

  @Override
  public double r1c0()
  {
    return this.getValue(ColumnMajor.index4x4(1, 0));
  }

  @Override
  public double r1c1()
  {
    return this.getValue(ColumnMajor.index4x4(1, 1));
  }

  @Override
  public double r1c2()
  {
    return this.getValue(ColumnMajor.index4x4(1, 2));
  }

  @Override
  public double r1c3()
  {
    return this.getValue(ColumnMajor.index4x4(1, 3));
  }

  @Override
  public double r2c0()
  {
    return this.getValue(ColumnMajor.index4x4(2, 0));
  }

  @Override
  public double r2c1()
  {
    return this.getValue(ColumnMajor.index4x4(2, 1));
  }

  @Override
  public double r2c2()
  {
    return this.getValue(ColumnMajor.index4x4(2, 2));
  }

  @Override
  public double r2c3()
  {
    return this.getValue(ColumnMajor.index4x4(2, 3));
  }

  @Override
  public double r3c0()
  {
    return this.getValue(ColumnMajor.index4x4(3, 0));
  }

  @Override
  public double r3c1()
  {
    return this.getValue(ColumnMajor.index4x4(3, 1));
  }

  @Override
  public double r3c2()
  {
    return this.getValue(ColumnMajor.index4x4(3, 2));
  }

  @Override
  public double r3c3()
  {
    return this.getValue(ColumnMajor.index4x4(3, 3));
  }

  @Override
  public void setMatrix4x4D(final Matrix4x4D m)
  {
    this.putValue(ColumnMajor.index4x4(0, 0), m.r0c0());
    this.putValue(ColumnMajor.index4x4(0, 1), m.r0c1());
    this.putValue(ColumnMajor.index4x4(0, 2), m.r0c2());
    this.putValue(ColumnMajor.index4x4(0, 3), m.r0c3());

    this.putValue(ColumnMajor.index4x4(1, 0), m.r1c0());
    this.putValue(ColumnMajor.index4x4(1, 1), m.r1c1());
    this.putValue(ColumnMajor.index4x4(1, 2), m.r1c2());
    this.putValue(ColumnMajor.index4x4(1, 3), m.r1c3());

    this.putValue(ColumnMajor.index4x4(2, 0), m.r2c0());
    this.putValue(ColumnMajor.index4x4(2, 1), m.r2c1());
    this.putValue(ColumnMajor.index4x4(2, 2), m.r2c2());
    this.putValue(ColumnMajor.index4x4(2, 3), m.r2c3());

    this.putValue(ColumnMajor.index4x4(3, 0), m.r3c0());
    this.putValue(ColumnMajor.index4x4(3, 1), m.r3c1());
    this.putValue(ColumnMajor.index4x4(3, 2), m.r3c2());
    this.putValue(ColumnMajor.index4x4(3, 3), m.r3c3());
  }

  @Override
  public void setMatrix4x4F(final Matrix4x4F m)
  {
    this.putValue(ColumnMajor.index4x4(0, 0), (double) m.r0c0());
    this.putValue(ColumnMajor.index4x4(0, 1), (double) m.r0c1());
    this.putValue(ColumnMajor.index4x4(0, 2), (double) m.r0c2());
    this.putValue(ColumnMajor.index4x4(0, 3), (double) m.r0c3());

    this.putValue(ColumnMajor.index4x4(1, 0), (double) m.r1c0());
    this.putValue(ColumnMajor.index4x4(1, 1), (double) m.r1c1());
    this.putValue(ColumnMajor.index4x4(1, 2), (double) m.r1c2());
    this.putValue(ColumnMajor.index4x4(1, 3), (double) m.r1c3());

    this.putValue(ColumnMajor.index4x4(2, 0), (double) m.r2c0());
    this.putValue(ColumnMajor.index4x4(2, 1), (double) m.r2c1());
    this.putValue(ColumnMajor.index4x4(2, 2), (double) m.r2c2());
    this.putValue(ColumnMajor.index4x4(2, 3), (double) m.r2c3());

    this.putValue(ColumnMajor.index4x4(3, 0), (double) m.r3c0());
    this.putValue(ColumnMajor.index4x4(3, 1), (double) m.r3c1());
    this.putValue(ColumnMajor.index4x4(3, 2), (double) m.r3c2());
    this.putValue(ColumnMajor.index4x4(3, 3), (double) m.r3c3());
  }
}
