/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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
 * 'Write' interface to 2x2 matrices with {@code double} elements.
 *
 * @since 7.0.0
 */

public interface MatrixWritable2x2DType extends MatrixWritableDType
{
  /**
   * Set row {@code row} of the matrix to {@code v}.
   *
   * @param row The row index
   * @param v   The row vector
   */

  void setRowWith2D(
    int row,
    VectorReadable2DType v);

  /**
   * Set row {@code row} of the matrix to {@code v}. No bounds checking is
   * performed for {@code row}, and out-of-bounds values result in undefined
   * behaviour.
   *
   * @param row The row index
   * @param v   The row vector
   */

  void setRowWith2DUnsafe(
    int row,
    VectorReadable2DType v);

  /**
   * Set row 0, column 0 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR0C0D(double x);

  /**
   * Set row 0, column 1 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR0C1D(double x);

  /**
   * Set row 1, column 0 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR1C0D(double x);

  /**
   * Set row 1, column 1 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR1C1D(double x);
}
