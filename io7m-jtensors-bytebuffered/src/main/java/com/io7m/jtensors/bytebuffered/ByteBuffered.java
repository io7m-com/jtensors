/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jnull.NullCheck;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The base of byte-buffered types.
 */

public abstract class ByteBuffered implements ByteBufferedType
{
  private final AtomicLong base;
  private final int        offset;

  protected ByteBuffered(
    final AtomicLong in_base,
    final int in_offset)
  {
    this.base = NullCheck.notNull(in_base);
    this.offset = in_offset;
  }

  @Override public final long getByteOffset()
  {
    return this.base.get() + (long) this.offset;
  }

  @Override public final void setByteOffset(final long b)
  {
    ByteBufferRanges.checkByteOffset(b + (long) this.getOffset());
    this.setBase(b);
  }

  protected final int getOffset()
  {
    return this.offset;
  }

  protected final long getIndex()
  {
    return this.base.get() + (long) this.offset;
  }

  protected final void setBase(final long in_base)
  {
    this.base.set(in_base);
  }
}
