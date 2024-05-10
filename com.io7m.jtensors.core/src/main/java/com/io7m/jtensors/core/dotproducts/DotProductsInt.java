/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jtensors.core.dotproducts;

import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions for calculating dot products.
 */

public final class DotProductsInt
{
  private DotProductsInt()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Calculate the dot product of
   * {@code (x0, y0, z0, w0)} and {@code (x1, y1, z1, w1)}.
   *
   * @param x0 The X component of the first vector
   * @param y0 The Y component of the first vector
   * @param z0 The Z component of the first vector
   * @param w0 The W component of the first vector
   * @param x1 The X component of the second vector
   * @param y1 The Y component of the second vector
   * @param z1 The Z component of the second vector
   * @param w1 The W component of the second vector
   *
   * @return The dot product
   */

  public static int dotProduct4(
    final int x0,
    final int y0,
    final int z0,
    final int w0,
    final int x1,
    final int y1,
    final int z1,
    final int w1)
  {
    final int x = x0 * x1;
    final int y = y0 * y1;
    final int z = z0 * z1;
    final int w = w0 * w1;
    return x + y + z + w;
  }

  /**
   * Calculate the dot product of
   * {@code (x0, y0, z0)} and {@code (x1, y1, z1)}.
   *
   * @param x0 The X component of the first vector
   * @param y0 The Y component of the first vector
   * @param z0 The Z component of the first vector
   * @param x1 The X component of the second vector
   * @param y1 The Y component of the second vector
   * @param z1 The Z component of the second vector
   *
   * @return The dot product
   */

  public static int dotProduct3(
    final int x0,
    final int y0,
    final int z0,
    final int x1,
    final int y1,
    final int z1)
  {
    final int x = x0 * x1;
    final int y = y0 * y1;
    final int z = z0 * z1;
    return x + y + z;
  }

  /**
   * Calculate the dot product of {@code (x0, y0)} and {@code (x1, y1)}.
   *
   * @param x0 The X component of the first vector
   * @param y0 The Y component of the first vector
   * @param x1 The X component of the second vector
   * @param y1 The Y component of the second vector
   *
   * @return The dot product
   */

  public static int dotProduct2(
    final int x0,
    final int y0,
    final int x1,
    final int y1)
  {
    final int x = x0 * x1;
    final int y = y0 * y1;
    return x + y;
  }
}
