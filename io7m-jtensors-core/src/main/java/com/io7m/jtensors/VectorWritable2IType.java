/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
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
 * <p>
 * 'Write' interface to two-dimensional vectors with integer elements.
 * </p>
 */

public interface VectorWritable2IType
{
  /**
   * Set the current vector to the contents of the input vector.
   *
   * @param in_v
   *          The input vector
   */

  void copyFrom2I(
    VectorReadable2IType in_v);

  /**
   * Set the current {@code x} and {@code y} values of the current
   * vector.
   *
   * @param x
   *          The new {@code x} value.
   * @param y
   *          The new {@code y} value.
   */

  void set2I(
    int x,
    int y);

  /**
   * Set the current {@code x} value of the current vector.
   *
   * @param x
   *          The new {@code x} value.
   */

  void setXI(
    int x);

  /**
   * Set the current {@code y} value of the current vector.
   *
   * @param y
   *          The new {@code y} value.
   */

  void setYI(
    int y);
}
