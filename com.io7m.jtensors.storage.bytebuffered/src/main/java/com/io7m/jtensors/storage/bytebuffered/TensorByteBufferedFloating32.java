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

abstract class TensorByteBufferedFloating32 extends TensorByteBuffered
{
  TensorByteBufferedFloating32(
    final ByteBuffer in_buffer,
    final ByteBufferOffsetMutable in_base,
    final int in_offset)
  {
    super(in_buffer, in_base, in_offset);
  }

  @Override
  protected final int componentBytes()
  {
    return 4;
  }

  final void putValue(
    final int component_index,
    final double value)
  {
    final int offset = this.byteOffsetForIndex(component_index);
    this.byteBuffer().putFloat(offset, (float) value);
  }

  final double getValue(
    final int i)
  {
    final int offset = this.byteOffsetForIndex(i);
    return (double) this.byteBuffer().getFloat(offset);
  }
}
