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

package com.io7m.jtensors.generators;

import com.io7m.junreachable.UnreachableCodeException;

final class GeneratorConstants
{
  private GeneratorConstants()
  {
    throw new UnreachableCodeException();
  }

  static final double BOUND_LARGE_DOUBLE_UPPER = 1_000_000.0;

  static final double BOUND_LARGE_DOUBLE_LOWER = -1_000_000.0;

  static final double BOUND_SMALL_DOUBLE_UPPER = 1_000.0;

  static final double BOUND_SMALL_DOUBLE_LOWER = -1_000.0;

  static final double BOUND_NORMAL_DOUBLE_UPPER = 1.0;

  static final double BOUND_NORMAL_DOUBLE_LOWER = -1.0;

  static final double BOUND_LARGE_FLOAT_UPPER = 1_000_000.0;

  static final double BOUND_LARGE_FLOAT_LOWER = -1_000_000.0;

  static final double BOUND_SMALL_FLOAT_UPPER = 1_000.0;

  static final double BOUND_SMALL_FLOAT_LOWER = -1_000.0;

  static final double BOUND_NORMAL_FLOAT_UPPER = 1.0;

  static final double BOUND_NORMAL_FLOAT_LOWER = -1.0;
}
