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
 * 'Write' interface to three-dimensional vectors with double precision
 * elements.
 * </p>
 */

public interface VectorWritable3DType extends VectorWritable2DType
{
  /**
   * Set the current vector to the contents of the input vector.
   * 
   * @param in_v
   *          The input vector
   */

  void copyFrom3D(
    VectorReadable3DType in_v);

  /**
   * Set the current <code>x</code>, <code>y</code> and <code>z</code> values
   * of the current vector.
   * 
   * @param x
   *          The new <code>x</code> value.
   * @param y
   *          The new <code>y</code> value.
   * @param z
   *          The new <code>z</code> value.
   */

  void set3D(
    double x,
    double y,
    double z);

  /**
   * Set the current <code>z</code> value of the current vector.
   * 
   * @param z
   *          The new <code>z</code> value.
   */

  void setZD(
    double z);
}
