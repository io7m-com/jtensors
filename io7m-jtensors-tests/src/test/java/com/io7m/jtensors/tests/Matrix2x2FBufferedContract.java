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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.Matrix2x2FType;
import org.junit.Assert;
import org.junit.Test;

public abstract class Matrix2x2FBufferedContract<T extends Matrix2x2FType>
  extends Matrix2x2FContract<T>
{
  protected abstract T newMatrixAtIndexFromSize(
    long size,
    long offset);

  @Test
  public final void testHugeIndexGet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, (long) Integer.MAX_VALUE);

    int errors = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        try {
          v.getRowColumnF(row, col);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(4L, (long) errors);
  }

  @Test
  public final void testHugeIndexSet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, (long) Integer.MAX_VALUE);

    int errors = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        try {
          v.setRowColumnF(row, col, errors);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(4L, (long) errors);
  }

  @Test
  public final void testOutOfBoundsIndexGet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, 200L);

    int errors = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        try {
          v.getRowColumnF(row, col);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(4L, (long) errors);
  }

  @Test
  public final void testOutOfBoundsIndexSet()
  {
    final T v = this.newMatrixAtIndexFromSize(
      200L, 200L);

    int errors = 0;
    for (int row = 0; row < 2; ++row) {
      for (int col = 0; col < 2; ++col) {
        try {
          v.setRowColumnF(row, col, errors);
        } catch (final IndexOutOfBoundsException e) {
          ++errors;
        }
      }
    }

    Assert.assertEquals(4L, (long) errors);
  }
}
