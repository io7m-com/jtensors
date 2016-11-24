/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

import com.io7m.jranges.RangeInclusiveL;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to check that byte offsets are not out of range for use with a
 * {@link java.nio.ByteBuffer} value.
 */

public final class ByteBufferRanges
{
  private static final RangeInclusiveL NON_NEGATIVE_INT_RANGE;

  static {
    NON_NEGATIVE_INT_RANGE = new RangeInclusiveL(
      0L, (long) (Integer.MAX_VALUE - 1));
  }

  private ByteBufferRanges()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Check that a byte offset is within the range accept by {@link
   * java.nio.ByteBuffer} instances.
   *
   * @param b The byte offset
   *
   * @return {@code b}, iff valid
   *
   * @throws IndexOutOfBoundsException On invalid offsets
   */

  public static long checkByteOffset(final long b)
  {
    if (ByteBufferRanges.NON_NEGATIVE_INT_RANGE.includesValue(b)) {
      return b;
    }

    throw new IndexOutOfBoundsException(
      String.format(
        "Byte offset %d is not in the range [%d, %d]",
        Long.valueOf(b),
        Long.valueOf(ByteBufferRanges.NON_NEGATIVE_INT_RANGE.getLower()),
        Long.valueOf(ByteBufferRanges.NON_NEGATIVE_INT_RANGE.getUpper())));
  }
}
