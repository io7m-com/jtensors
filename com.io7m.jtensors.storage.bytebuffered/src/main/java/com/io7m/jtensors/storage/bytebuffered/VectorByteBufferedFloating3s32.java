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
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;

import java.nio.ByteBuffer;

/**
 * <p>A storage vector.</p>
 * <p>Storage component type: {@code float}</p>
 * <p>Storage component count: {@code 3}</p>
 */

public final class VectorByteBufferedFloating3s32
  implements VectorStorageFloating3Type
{
  private final ByteBuffer buffer;

  private VectorByteBufferedFloating3s32(
    final ByteBuffer in_buffer)
  {
    this.buffer = NullCheck.notNull(in_buffer, "Buffer");
  }

  public static VectorStorageFloating3Type createHeap()
  {
    return createWith(ByteBuffer.allocate(3 * 4));
  }

  public static VectorStorageFloating3Type createDirect()
  {
    return createWith(ByteBuffer.allocateDirect(3 * 4));
  }

  public static VectorStorageFloating3Type createWith(
    final ByteBuffer b)
  {
    return new VectorByteBufferedFloating3s32(b);
  }

  @Override
  public double x()
  {
    return (double) this.buffer.getFloat(0);
  }

  @Override
  public double y()
  {
    return (double) this.buffer.getFloat(4);
  }

  @Override
  public double z()
  {
    return (double) this.buffer.getFloat(8);
  }

  @Override
  public void setX(final double x)
  {
    this.buffer.putFloat(0, (float) x);
  }

  @Override
  public void setY(final double y)
  {
    this.buffer.putFloat(4, (float) y);
  }

  @Override
  public void setZ(final double z)
  {
    this.buffer.putFloat(8, (float) z);
  }
}
