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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.mutable.numbers.core.MutableLong;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedFloating4Type;
import com.io7m.jtensors.tests.storage.api.PVectorStorageFloating4Contract;
import org.junit.Test;

public abstract class PVectorByteBufferedFloating4Contract
  extends PVectorStorageFloating4Contract
{
  protected abstract PVectorByteBufferedFloating4Type<Object> create(
    final MutableLong base,
    final int offset);

  @Test
  public final void testGetSetRange()
  {
    final MutableLong base = MutableLong.create();
    final PVectorByteBufferedFloating4Type<Object> m = this.create(base, 0);

    m.setVector4D(Vector4D.of(0.0, 1.0, 2.0, 3.0));

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
      this.checkAlmostEquals(2.0, m.z());
      this.checkAlmostEquals(3.0, m.w());
    }

    base.setValue((long) m.sizeBytes());

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(0.0, m.y());
      this.checkAlmostEquals(0.0, m.z());
      this.checkAlmostEquals(0.0, m.w());
    }

    m.setVector4D(Vector4D.of(0.0, 1.0, 2.0, 3.0));

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
      this.checkAlmostEquals(2.0, m.z());
      this.checkAlmostEquals(3.0, m.w());
    }

    base.setValue(0L);

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
      this.checkAlmostEquals(2.0, m.z());
      this.checkAlmostEquals(3.0, m.w());
    }
  }
}
