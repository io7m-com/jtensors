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

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 2x2}</p>
 */

public final class MatrixByteBuffered2x2s64 implements MatrixByteBuffered2x2Type
{
  private final DoubleBuffer view;
  private final ByteBuffer buffer;

  private MatrixByteBuffered2x2s64(
    final ByteBuffer bb)
  {
    this.buffer = NullCheck.notNull(bb, "Buffer");
    this.view = this.buffer.asDoubleBuffer();
  }

  /**
   * @return A heap-backed matrix in native byte order
   */

  public static MatrixByteBuffered2x2Type createHeap()
  {
    return createWith(ByteBuffer.allocate((2 * 2) * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @return A direct-memory matrix in native byte order
   */

  public static MatrixByteBuffered2x2Type createDirect()
  {
    return createWith(ByteBuffer.allocateDirect((2 * 2) * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param b A byte buffer
   *
   * @return A matrix backed by the given byte buffer
   */

  public static MatrixByteBuffered2x2Type createWith(
    final ByteBuffer b)
  {
    return new MatrixByteBuffered2x2s64(b);
  }

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 2) + row;
  }

  @Override
  public double r0c0()
  {
    return this.view.get(indexUnsafe(0, 0));
  }

  @Override
  public double r0c1()
  {
    return this.view.get(indexUnsafe(0, 1));
  }

  @Override
  public double r1c0()
  {
    return this.view.get(indexUnsafe(1, 0));
  }

  @Override
  public double r1c1()
  {
    return this.view.get(indexUnsafe(1, 1));
  }

  @Override
  public void setMatrix2x2D(
    final Matrix2x2D m)
  {
    this.view.put(indexUnsafe(0, 0), m.r0c0());
    this.view.put(indexUnsafe(0, 1), m.r0c1());

    this.view.put(indexUnsafe(1, 0), m.r1c0());
    this.view.put(indexUnsafe(1, 1), m.r1c1());
  }

  @Override
  public void setMatrix2x2F(
    final Matrix2x2F m)
  {
    this.view.put(indexUnsafe(0, 0), (double) m.r0c0());
    this.view.put(indexUnsafe(0, 1), (double) m.r0c1());

    this.view.put(indexUnsafe(1, 0), (double) m.r1c0());
    this.view.put(indexUnsafe(1, 1), (double) m.r1c1());
  }

  @Override
  public ByteBuffer byteBuffer()
  {
    return this.buffer;
  }
}
