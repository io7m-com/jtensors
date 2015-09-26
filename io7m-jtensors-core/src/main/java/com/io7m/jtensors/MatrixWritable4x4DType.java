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
 * 'Write' interface to 4x4 matrices with double precision elements.
 *
 * @since 7.0.0
 */

public interface MatrixWritable4x4DType extends MatrixWritable3x3DType
{
  /**
   * Set row {@code row} of the matrix to {@code v}.
   *
   * @param row The row index
   * @param v   The row vector
   */

  void setRowWith4D(
    int row,
    VectorReadable4DType v);

  /**
   * Set row {@code row} of the matrix to {@code v}. No bounds checking is
   * performed for {@code row}, and out-of-bounds values result in undefined
   * behaviour.
   *
   * @param row The row index
   * @param v   The row vector
   */

  void setRowWith4DUnsafe(
    int row,
    VectorReadable4DType v);

  /**
   * Set row 0  to {@code v}.
   *
   * @param v The row vector
   */

  void setRow0With4D(VectorReadable4DType v);

  /**
   * Set row 1  to {@code v}.
   *
   * @param v The row vector
   */

  void setRow1With4D(VectorReadable4DType v);

  /**
   * Set row 2  to {@code v}.
   *
   * @param v The row vector
   */

  void setRow2With4D(VectorReadable4DType v);

  /**
   * Set row 3  to {@code v}.
   *
   * @param v The row vector
   */

  void setRow3With4D(VectorReadable4DType v);

  /**
   * Set row 0, column 3 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR0C3D(double x);

  /**
   * Set row 1, column 3 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR1C3D(double x);

  /**
   * Set row 2, column 3 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR2C3D(double x);

  /**
   * Set row 3, column 0 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR3C0D(double x);

  /**
   * Set row 3, column 1 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR3C1D(double x);

  /**
   * Set row 3, column 2 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR3C2D(double x);

  /**
   * Set row 3, column 3 of the matrix to {@code x}.
   *
   * @param x The value
   */

  void setR3C3D(double x);
}
