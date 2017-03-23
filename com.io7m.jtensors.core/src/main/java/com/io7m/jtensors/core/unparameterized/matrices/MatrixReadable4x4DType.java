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

/**
 * The type of 4x4 {@code double}-typed matrices.
 */

public interface MatrixReadable4x4DType extends MatrixReadable3x3DType
{
  @Override
  @Value.Parameter(order = 0)
  double r0c0();

  @Override
  @Value.Parameter(order = 1)
  double r0c1();

  @Override
  @Value.Parameter(order = 2)
  double r0c2();

  /**
   * @return The value at row 0 column 3
   */

  @Value.Parameter(order = 3)
  double r0c3();

  @Override
  @Value.Parameter(order = 4)
  double r1c0();

  @Override
  @Value.Parameter(order = 5)
  double r1c1();

  @Override
  @Value.Parameter(order = 6)
  double r1c2();

  /**
   * @return The value at row 1 column 3
   */

  @Value.Parameter(order = 7)
  double r1c3();

  @Override
  @Value.Parameter(order = 8)
  double r2c0();

  @Override
  @Value.Parameter(order = 9)
  double r2c1();

  @Override
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
}
