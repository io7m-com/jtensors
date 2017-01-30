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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.parameterized.PMatrix3x3DType;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrix3x3DBufferedContract<
  T0,
  T1,
  T2,
  T extends PMatrix3x3DType<T0, T1>,
  TMULTRIGHT extends PMatrix3x3DType<T0, T1>,
  TMULTLEFT extends PMatrix3x3DType<T1, T2>,
  TMULTRESULT extends PMatrix3x3DType<T0, T2>,
  TINVERSE extends PMatrix3x3DType<T1, T0>>
  extends PMatrix3x3DContract<
  T0,
  T1,
  T2,
  T,
  TMULTRIGHT,
  TMULTLEFT,
  TMULTRESULT,
  TINVERSE>
{
  //@formatter:on

  protected abstract T newMatrixAtIndexFromSize(
    long size,
    long offset);

  @Test
  public final void testHugeIndexGet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, (long) Integer.MAX_VALUE);

    int errors = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        try {
          v.getRowColumnD(row, col);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(9L, (long) errors);
  }

  @Test
  public final void testHugeIndexSet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, (long) Integer.MAX_VALUE);

    int errors = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        try {
          v.setRowColumnD(row, col, errors);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(9L, (long) errors);
  }

  @Test
  public final void testOutOfBoundsIndexGet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, 200L);

    int errors = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        try {
          v.getRowColumnD(row, col);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(9L, (long) errors);
  }

  @Test
  public final void testOutOfBoundsIndexSet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, 200L);

    int errors = 0;
    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        try {
          v.setRowColumnD(row, col, errors);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(9L, (long) errors);
  }
}
