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

/**
 * An abstract class for implementing byte buffered tensors.
 */

abstract class TensorByteBuffered implements TensorByteBufferedType
{
  private final ByteBuffer buffer;
  private final ByteBufferOffsetMutable base;
  private final int offset;

  protected TensorByteBuffered(
    final ByteBuffer in_buffer,
    final ByteBufferOffsetMutable in_base,
    final int in_offset)
  {
    this.buffer = NullCheck.notNull(in_buffer, "Buffer");
    this.base = NullCheck.notNull(in_base);
    this.offset = in_offset;
  }

  @Override
  public final ByteBuffer byteBuffer()
  {
    return this.buffer;
  }

  @Override
  public final long byteOffset()
  {
    return Math.addExact(
      this.base.offset(),
      Integer.toUnsignedLong(this.offset()));
  }

  @Override
  public final int offset()
  {
    return this.offset;
  }

  protected abstract int componentBytes();

  protected final int byteOffsetForIndex(
    final int component_index)
  {
    return Math.toIntExact(
      Math.addExact(
        this.byteOffset(),
        (long) Math.multiplyExact(component_index, this.componentBytes())));
  }
}
