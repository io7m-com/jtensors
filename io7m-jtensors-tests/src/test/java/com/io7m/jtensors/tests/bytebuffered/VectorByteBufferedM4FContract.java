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

package com.io7m.jtensors.tests.bytebuffered;

import com.io7m.jtensors.bytebuffered.VectorByteBuffered4FType;
import com.io7m.jtensors.tests.VectorM4FBufferedContract;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

public abstract class VectorByteBufferedM4FContract<T extends
  VectorByteBuffered4FType>
  extends VectorM4FBufferedContract<T>
{
  protected abstract T newVectorM4FWithBaseOffset(
    int size,
    AtomicLong base,
    int offset);

  @Test public final void testByteOffsetSetGetIdentity()
  {
    final T v = this.newVectorM4F();
    v.setByteOffset(23L);
    Assert.assertEquals(23L, v.getByteOffset());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testByteOffsetSetOutOfRange()
  {
    final T v = this.newVectorM4F();
    v.setByteOffset((long) Integer.MAX_VALUE);
  }

  @Test public final void testByteBaseOffsetSetGetIdentity()
  {
    final AtomicLong base = new AtomicLong(0L);
    final T v = this.newVectorM4FWithBaseOffset(1000, base, 0);
    v.setByteOffset(23L);
    Assert.assertEquals(23L, v.getByteOffset());
  }

  @Test public final void testByteBaseOffsetSetGetIdentity100()
  {
    final AtomicLong base = new AtomicLong(0L);
    final T v = this.newVectorM4FWithBaseOffset(1000, base, 100);
    v.setByteOffset(23L);
    Assert.assertEquals(123L, v.getByteOffset());
  }
}
