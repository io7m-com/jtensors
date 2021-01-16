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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.storage.bytebuffered.PMatrixByteBuffered2x2Type;
import com.io7m.jtensors.tests.storage.api.PMatrixStorage2x2Contract;
import com.io7m.mutable.numbers.core.MutableLong;
import org.junit.jupiter.api.Test;

public abstract class PMatrixByteBuffered2x2Contract extends
  PMatrixStorage2x2Contract
{
  protected abstract PMatrixByteBuffered2x2Type<Object, Object> create(
    MutableLong base,
    int offset);

  @Test
  public final void testGetSetRange()
  {
    final MutableLong base = MutableLong.create();
    final PMatrixByteBuffered2x2Type<Object, Object> m = this.create(base, 0);

    m.setMatrix2x2D(Matrix2x2D.of(
      0.0, 1.0,
      2.0, 3.0));

    {
      int index = 0;
      for (int row = 0; row < 2; ++row) {
        for (int column = 0; column < 2; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }

    base.setValue(m.sizeBytes());

    {
      for (int row = 0; row < 2; ++row) {
        for (int column = 0; column < 2; ++column) {
          this.checkAlmostEquals(0.0, m.rowColumn(row, column));
        }
      }
    }

    m.setMatrix2x2D(Matrix2x2D.of(
      0.0, 1.0,
      2.0, 3.0));

    {
      int index = 0;
      for (int row = 0; row < 2; ++row) {
        for (int column = 0; column < 2; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }

    base.setValue(0L);

    {
      int index = 0;
      for (int row = 0; row < 2; ++row) {
        for (int column = 0; column < 2; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }
  }
}
