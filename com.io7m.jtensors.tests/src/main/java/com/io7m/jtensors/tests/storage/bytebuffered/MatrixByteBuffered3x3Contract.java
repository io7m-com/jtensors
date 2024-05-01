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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered3x3Type;
import com.io7m.jtensors.tests.storage.api.MatrixStorage3x3Contract;
import com.io7m.mutable.numbers.core.MutableLong;
import org.junit.jupiter.api.Test;

public abstract class MatrixByteBuffered3x3Contract extends
  MatrixStorage3x3Contract
{
  protected abstract MatrixByteBuffered3x3Type create(
    MutableLong base,
    int offset);

  @Test
  public final void testGetSetRange()
  {
    final MutableLong base = MutableLong.create();
    final MatrixByteBuffered3x3Type m = this.create(base, 0);

    m.setMatrix3x3D(Matrix3x3D.of(
      0.0, 1.0, 2.0,
      3.0, 4.0, 5.0,
      6.0, 7.0, 8.0));

    {
      int index = 0;
      for (int row = 0; row < 3; ++row) {
        for (int column = 0; column < 3; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }

    base.setValue(m.sizeBytes());

    {
      for (int row = 0; row < 3; ++row) {
        for (int column = 0; column < 3; ++column) {
          this.checkAlmostEquals(0.0, m.rowColumn(row, column));
        }
      }
    }

    m.setMatrix3x3D(Matrix3x3D.of(
      0.0, 1.0, 2.0,
      3.0, 4.0, 5.0,
      6.0, 7.0, 8.0));

    {
      int index = 0;
      for (int row = 0; row < 3; ++row) {
        for (int column = 0; column < 3; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }

    base.setValue(0L);

    {
      int index = 0;
      for (int row = 0; row < 3; ++row) {
        for (int column = 0; column < 3; ++column) {
          this.checkAlmostEquals(index, m.rowColumn(row, column));
          ++index;
        }
      }
    }
  }
}
