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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <p>A storage vector.</p>
 * <p>Storage component type: {@code float}</p>
 * <p>Storage component count: {@code 3}</p>
 */

public final class VectorByteBufferedFloating3s32
  implements VectorByteBufferedFloating3Type
{
  private final FloatBuffer view;
  private final ByteBuffer buffer;

  private VectorByteBufferedFloating3s32(
    final ByteBuffer in_buffer)
  {
    this.buffer = NullCheck.notNull(in_buffer, "Buffer");
    this.view = this.buffer.asFloatBuffer();
  }

  /**
   * @return A heap-backed vector in native byte order
   */

  public static VectorByteBufferedFloating3Type createHeap()
  {
    return createWith(ByteBuffer.allocate(3 * 4).order(ByteOrder.nativeOrder()));
  }

  /**
   * @return A direct-memory-backed vector in native byte order
   */

  public static VectorByteBufferedFloating3Type createDirect()
  {
    return createWith(ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param b A byte buffer
   *
   * @return A vector backed by the given byte buffer
   */

  public static VectorByteBufferedFloating3Type createWith(
    final ByteBuffer b)
  {
    return new VectorByteBufferedFloating3s32(b);
  }

  @Override
  public double x()
  {
    return (double) this.view.get(0);
  }

  @Override
  public double y()
  {
    return (double) this.view.get(1);
  }

  @Override
  public double z()
  {
    return (double) this.view.get(2);
  }

  @Override
  public void setX(final double x)
  {
    this.view.put(0, (float) x);
  }

  @Override
  public void setY(final double y)
  {
    this.view.put(1, (float) y);
  }

  @Override
  public void setZ(final double z)
  {
    this.view.put(2, (float) z);
  }

  @Override
  public ByteBuffer byteBuffer()
  {
    return this.buffer;
  }
}
