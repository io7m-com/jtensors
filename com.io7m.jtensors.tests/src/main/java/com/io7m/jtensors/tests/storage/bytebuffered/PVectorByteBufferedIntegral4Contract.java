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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.core.unparameterized.vectors.Vector4L;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedIntegral4Type;
import com.io7m.jtensors.tests.storage.api.PVectorStorageIntegral4Contract;
import com.io7m.mutable.numbers.core.MutableLong;
import org.junit.jupiter.api.Test;

public abstract class PVectorByteBufferedIntegral4Contract
  extends PVectorStorageIntegral4Contract
{
  protected abstract PVectorByteBufferedIntegral4Type<Object> create(
    MutableLong base,
    int offset);

  @Test
  public final void testGetSetRange()
  {
    final MutableLong base = MutableLong.create();
    final PVectorByteBufferedIntegral4Type<Object> m = this.create(base, 0);

    m.setVector4L(Vector4L.of(0L, 1L, 2L, 3L));

    {
      this.checkEquals(0L, m.x());
      this.checkEquals(1L, m.y());
      this.checkEquals(2L, m.z());
      this.checkEquals(3L, m.w());
    }

    base.setValue(m.sizeBytes());

    {
      this.checkEquals(0L, m.x());
      this.checkEquals(0L, m.y());
      this.checkEquals(0L, m.z());
      this.checkEquals(0L, m.w());
    }

    m.setVector4L(Vector4L.of(0L, 1L, 2L, 3L));

    {
      this.checkEquals(0L, m.x());
      this.checkEquals(1L, m.y());
      this.checkEquals(2L, m.z());
      this.checkEquals(3L, m.w());
    }

    base.setValue(0L);

    {
      this.checkEquals(0L, m.x());
      this.checkEquals(1L, m.y());
      this.checkEquals(2L, m.z());
      this.checkEquals(3L, m.w());
    }
  }
}
