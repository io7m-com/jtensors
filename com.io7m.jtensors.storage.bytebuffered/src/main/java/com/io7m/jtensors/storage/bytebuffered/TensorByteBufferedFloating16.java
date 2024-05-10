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

import com.io7m.ieee754b16.Binary16;
import com.io7m.mutable.numbers.core.MutableLongType;

import java.nio.ByteBuffer;

abstract class TensorByteBufferedFloating16 extends TensorByteBuffered
{
  TensorByteBufferedFloating16(
    final ByteBuffer in_buffer,
    final MutableLongType in_base,
    final int in_offset)
  {
    super(in_buffer, in_base, in_offset);
  }

  @Override
  protected final int componentBytes()
  {
    return 2;
  }

  final void putValue(
    final int component_index,
    final double value)
  {
    final int offset = this.byteOffsetForIndex(component_index);
    final char target = Binary16.packDouble(value);
    this.byteBuffer().putChar(offset, target);
  }

  final double getValue(
    final int i)
  {
    final int offset = this.byteOffsetForIndex(i);
    final char source = this.byteBuffer().getChar(offset);
    return Binary16.unpackDouble(source);
  }
}
