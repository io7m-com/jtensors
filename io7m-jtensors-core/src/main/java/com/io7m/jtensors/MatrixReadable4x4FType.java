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
 * 'Read' interface to 4x4 matrices with single precision elements.
 */

public interface MatrixReadable4x4FType extends MatrixReadable3x3FType
{
  /**
   * Retrieve row {@code row}, saving the result to {@code out}.
   *
   * @param row The index of the row, starting at {@code 0}.
   * @param out The output vector.
   * @param <V> The precise type of writable vector.
   */

  <V extends VectorWritable4FType> void getRow4F(
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

  <V extends VectorWritable4FType> void getRow4FUnsafe(
    int row,
    V out);

  /**
   * @return The value at row 0, column 3
   */

  float getR0C3F();

  /**
   * @return The value at row 1, column 3
   */

  float getR1C3F();

  /**
   * @return The value at row 2, column 3
   */

  float getR2C3F();

  /**
   * @return The value at row 3, column 0
   */

  float getR3C0F();

  /**
   * @return The value at row 3, column 1
   */

  float getR3C1F();

  /**
   * @return The value at row 3, column 2
   */

  float getR3C2F();

  /**
   * @return The value at row 3, column 3
   */

  float getR3C3F();
}
