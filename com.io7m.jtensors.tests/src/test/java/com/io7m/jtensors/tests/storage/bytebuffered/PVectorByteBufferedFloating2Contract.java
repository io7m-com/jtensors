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

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedFloating2Type;
import com.io7m.jtensors.tests.storage.api.PVectorStorageFloating2Contract;
import com.io7m.mutable.numbers.core.MutableLong;
import org.junit.jupiter.api.Test;

public abstract class PVectorByteBufferedFloating2Contract
  extends PVectorStorageFloating2Contract
{
  protected abstract PVectorByteBufferedFloating2Type<Object> create(
    MutableLong base,
    int offset);

  @Test
  public final void testGetSetRange()
  {
    final MutableLong base = MutableLong.create();
    final PVectorByteBufferedFloating2Type<Object> m = this.create(base, 0);

    m.setVector2D(Vector2D.of(0.0, 1.0));

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
    }

    base.setValue(m.sizeBytes());

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(0.0, m.y());
    }

    m.setVector2D(Vector2D.of(0.0, 1.0));

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
    }

    base.setValue(0L);

    {
      this.checkAlmostEquals(0.0, m.x());
      this.checkAlmostEquals(1.0, m.y());
    }
  }
}
