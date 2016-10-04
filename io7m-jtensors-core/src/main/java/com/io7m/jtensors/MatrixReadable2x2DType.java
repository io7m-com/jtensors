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

package com.io7m.jtensors;

/**
 * 'Read' interface to 2x2 matrices with {@code double} elements.
 */

public interface MatrixReadable2x2DType extends MatrixReadableDType
{
  /**
   * Retrieve row {@code row}, saving the result to {@code out}.
   *
   * @param row The index of the row, starting at {@code 0}.
   * @param out The output vector.
   * @param <V> The precise type of writable vector.
   */

  <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out);

  /**
   * Retrieve row {@code row}, saving the result to {@code out}. No bounds
   * checking is performed for {@code row}, and out-of-bounds values result in
   * undefined behaviour.
   *
   * @param row The index of the row, starting at {@code 0}.
   * @param out The output vector.
   * @param <V> The precise type of writable vector.
   */

  <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out);

  /**
   * @return The value at row 0, column 0
   */

  double getR0C0D();

  /**
   * @return The value at row 1, column 0
   */

  double getR1C0D();

  /**
   * @return The value at row 0, column 1
   */

  double getR0C1D();

  /**
   * @return The value at row 1, column 1
   */

  double getR1C1D();
}
