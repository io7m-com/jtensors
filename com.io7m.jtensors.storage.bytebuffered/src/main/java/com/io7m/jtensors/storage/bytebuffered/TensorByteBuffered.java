/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
import java.util.Objects;

import java.nio.ByteBuffer;

/**
 * An abstract class for implementing byte buffered tensors.
 */

abstract class TensorByteBuffered implements TensorByteBufferedType
{
  private final ByteBuffer buffer;
  private final MutableLongType base;
  private final int offset;

  protected TensorByteBuffered(
    final ByteBuffer in_buffer,
    final MutableLongType in_base,
    final int in_offset)
  {
    this.buffer = Objects.requireNonNull(in_buffer, "Buffer");
    this.base = Objects.requireNonNull(in_base, "Base");
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
      this.base.value(),
      Integer.toUnsignedLong(this.offset()));
  }

  @Override
  public final int offset()
  {
    return this.offset;
  }

  protected abstract int componentBytes();

  protected abstract int componentCount();

  @Override
  public final int sizeBytes()
  {
    return Math.multiplyExact(this.componentCount(), this.componentBytes());
  }

  protected final int byteOffsetForIndex(
    final int component_index)
  {
    final int size_components =
      Math.multiplyExact(component_index, this.componentBytes());
    final long base_offset =
      this.byteOffset();
    return Math.toIntExact(Math.addExact(base_offset, size_components));
  }
}
