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

package com.io7m.jtensors.core.unparameterized.matrices;

import org.immutables.value.Value;

import static java.lang.Integer.valueOf;

/**
 * The type of 3x3 {@code double}-typed matrices.
 */

public interface MatrixReadable3x3DType extends MatrixReadable2x2DType
{
  @Override
  @Value.Parameter(order = 0)
  double r0c0();

  @Override
  @Value.Parameter(order = 1)
  double r0c1();

  /**
   * @return The value at row 0 column 2
   */

  @Value.Parameter(order = 2)
  double r0c2();

  @Override
  @Value.Parameter(order = 3)
  double r1c0();

  @Override
  @Value.Parameter(order = 4)
  double r1c1();

  /**
   * @return The value at row 1 column 2
   */

  @Value.Parameter(order = 5)
  double r1c2();

  /**
   * @return The value at row 2 column 0
   */

  @Value.Parameter(order = 6)
  double r2c0();

  /**
   * @return The value at row 2 column 1
   */

  @Value.Parameter(order = 7)
  double r2c1();

  /**
   * @return The value at row 2 column 2
   */

  @Value.Parameter(order = 8)
  double r2c2();

  @Override
  default double rowColumn(
    final int row,
    final int column)
  {
    switch (row) {
      case 0: {
        switch (column) {
          case 0: {
            return this.r0c0();
          }
          case 1: {
            return this.r0c1();
          }
          case 2: {
            return this.r0c2();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 2]", valueOf(column)));
          }
        }
      }
      case 1: {
        switch (column) {
          case 0: {
            return this.r1c0();
          }
          case 1: {
            return this.r1c1();
          }
          case 2: {
            return this.r1c2();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 2]", valueOf(column)));
          }
        }
      }
      case 2: {
        switch (column) {
          case 0: {
            return this.r2c0();
          }
          case 1: {
            return this.r2c1();
          }
          case 2: {
            return this.r2c2();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 2]", valueOf(column)));
          }
        }
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Row %d must be in the range [0, 2]", valueOf(row)));
      }
    }
  }
}
