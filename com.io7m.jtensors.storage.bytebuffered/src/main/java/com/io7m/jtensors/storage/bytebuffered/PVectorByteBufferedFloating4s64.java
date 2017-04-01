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
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating4Type;

import java.nio.ByteBuffer;

/**
 * <p>A storage vector.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 4}</p>
 */

public final class PVectorByteBufferedFloating4s64<T>
  implements PVectorStorageFloating4Type<T>
{
  private final ByteBuffer buffer;

  private PVectorByteBufferedFloating4s64(
    final ByteBuffer in_buffer)
  {
    this.buffer = NullCheck.notNull(in_buffer, "Buffer");
  }

  public static <T> PVectorStorageFloating4Type<T> createHeap()
  {
    return createWith(ByteBuffer.allocate(4 * 8));
  }

  public static <T> PVectorStorageFloating4Type<T> createDirect()
  {
    return createWith(ByteBuffer.allocateDirect(4 * 8));
  }

  public static <T> PVectorStorageFloating4Type<T> createWith(
    final ByteBuffer b)
  {
    return new PVectorByteBufferedFloating4s64<>(b);
  }

  @Override
  public double x()
  {
    return this.buffer.getDouble(0);
  }

  @Override
  public double y()
  {
    return this.buffer.getDouble(8);
  }

  @Override
  public double z()
  {
    return this.buffer.getDouble(16);
  }

  @Override
  public double w()
  {
    return this.buffer.getDouble(24);
  }

  @Override
  public void setX(final double x)
  {
    this.buffer.putDouble(0, x);
  }

  @Override
  public void setY(final double y)
  {
    this.buffer.putDouble(8, y);
  }

  @Override
  public void setZ(final double z)
  {
    this.buffer.putDouble(16, z);
  }

  @Override
  public void setW(final double w)
  {
    this.buffer.putDouble(24, w);
  }
}
