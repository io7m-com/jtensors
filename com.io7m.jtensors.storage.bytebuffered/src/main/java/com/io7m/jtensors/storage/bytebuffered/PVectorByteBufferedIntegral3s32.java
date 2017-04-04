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

import java.nio.ByteBuffer;

/**
 * <p>A storage vector.</p>
 * <p>Storage component type: {@code int}</p>
 * <p>Storage component count: {@code 3}</p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedIntegral3s32<T>
  extends TensorByteBufferedIntegral32
  implements PVectorByteBufferedIntegral3Type<T>
{
  private PVectorByteBufferedIntegral3s32(
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
   * @param <T>    A phantom type parameter
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered vector
   */

  public static <T> PVectorByteBufferedIntegral3Type<T> createWithBase(
    final ByteBuffer b,
    final ByteBufferOffsetMutable base,
    final int offset)
  {
    return new PVectorByteBufferedIntegral3s32<>(b, base, offset);
  }

  @Override
  public long x()
  {
    return this.getValue(0);
  }

  @Override
  public long y()
  {
    return this.getValue(1);
  }

  @Override
  public long z()
  {
    return this.getValue(2);
  }

  @Override
  public void setX(final long x)
  {
    this.putValue(0, x);
  }

  @Override
  public void setY(final long y)
  {
    this.putValue(1, y);
  }

  @Override
  public void setZ(final long z)
  {
    this.putValue(2, z);
  }
}
