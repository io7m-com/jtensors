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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import org.immutables.value.Value;

import static java.lang.Integer.valueOf;

/**
 * The type of 4x4 {@code double}-typed matrices.
 */

public interface MatrixReadable4x4DType extends MatrixReadableDType
{
  /**
   * @return The value at row 0 column 0
   */

  @Value.Parameter(order = 0)
  double r0c0();

  /**
   * @return The value at row 0 column 1
   */

  @Value.Parameter(order = 1)
  double r0c1();

  /**
   * @return The value at row 0 column 2
   */

  @Value.Parameter(order = 2)
  double r0c2();

  /**
   * @return The value at row 0 column 3
   */

  @Value.Parameter(order = 3)
  double r0c3();

  /**
   * @return The value at row 1 column 0
   */

  @Value.Parameter(order = 4)
  double r1c0();

  /**
   * @return The value at row 1 column 1
   */

  @Value.Parameter(order = 5)
  double r1c1();

  /**
   * @return The value at row 1 column 2
   */

  @Value.Parameter(order = 6)
  double r1c2();

  /**
   * @return The value at row 1 column 3
   */

  @Value.Parameter(order = 7)
  double r1c3();

  /**
   * @return The value at row 2 column 0
   */

  @Value.Parameter(order = 8)
  double r2c0();

  /**
   * @return The value at row 2 column 1
   */

  @Value.Parameter(order = 9)
  double r2c1();

  /**
   * @return The value at row 2 column 2
   */

  @Value.Parameter(order = 10)
  double r2c2();

  /**
   * @return The value at row 2 column 3
   */

  @Value.Parameter(order = 11)
  double r2c3();

  /**
   * @return The value at row 3 column 0
   */

  @Value.Parameter(order = 12)
  double r3c0();

  /**
   * @return The value at row 3 column 1
   */

  @Value.Parameter(order = 13)
  double r3c1();

  /**
   * @return The value at row 3 column 2
   */

  @Value.Parameter(order = 14)
  double r3c2();

  /**
   * @return The value at row 3 column 3
   */

  @Value.Parameter(order = 15)
  double r3c3();

  /**
   * @return Row 0 of the matrix
   */

  @Value.Lazy
  default Vector4D row0()
  {
    return Vector4D.of(this.r0c0(), this.r0c1(), this.r0c2(), this.r0c3());
  }

  /**
   * @return Row 1 of the matrix
   */

  @Value.Lazy
  default Vector4D row1()
  {
    return Vector4D.of(this.r1c0(), this.r1c1(), this.r1c2(), this.r1c3());
  }

  /**
   * @return Row 2 of the matrix
   */

  @Value.Lazy
  default Vector4D row2()
  {
    return Vector4D.of(this.r2c0(), this.r2c1(), this.r2c2(), this.r2c3());
  }

  /**
   * @return Row 3 of the matrix
   */

  @Value.Lazy
  default Vector4D row3()
  {
    return Vector4D.of(this.r3c0(), this.r3c1(), this.r3c2(), this.r3c3());
  }

  /**
   * @return Column 0 of the matrix
   */

  @Value.Lazy
  default Vector4D column0()
  {
    return Vector4D.of(this.r0c0(), this.r1c0(), this.r2c0(), this.r3c0());
  }

  /**
   * @return Column 1 of the matrix
   */

  @Value.Lazy
  default Vector4D column1()
  {
    return Vector4D.of(this.r0c1(), this.r1c1(), this.r2c1(), this.r3c1());
  }

  /**
   * @return Column 2 of the matrix
   */

  @Value.Lazy
  default Vector4D column2()
  {
    return Vector4D.of(this.r0c2(), this.r1c2(), this.r2c2(), this.r3c2());
  }

  /**
   * @return Column 3 of the matrix
   */

  @Value.Lazy
  default Vector4D column3()
  {
    return Vector4D.of(this.r0c3(), this.r1c3(), this.r2c3(), this.r3c3());
  }

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
          case 3: {
            return this.r0c3();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 3]", valueOf(column)));
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
          case 3: {
            return this.r1c3();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 3]", valueOf(column)));
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
          case 3: {
            return this.r2c3();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 3]", valueOf(column)));
          }
        }
      }
      case 3: {
        switch (column) {
          case 0: {
            return this.r3c0();
          }
          case 1: {
            return this.r3c1();
          }
          case 2: {
            return this.r3c2();
          }
          case 3: {
            return this.r3c3();
          }
          default: {
            throw new IndexOutOfBoundsException(String.format(
              "Column %d must be in the range [0, 3]", valueOf(column)));
          }
        }
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Row %d must be in the range [0, 3]", valueOf(row)));
      }
    }
  }
}
