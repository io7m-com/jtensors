/*
 * Copyright © 2012 http://io7m.com
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
 * A two-dimensional immutable vector type with integer elements.
 */

public final class VectorI2I implements VectorReadable2I
{
  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y)</code>
   */

  public static VectorI2I add(
    final VectorI2I v0,
    final VectorI2I v1)
  {
    return new VectorI2I(v0.x + v1.x, v0.y + v1.y);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y)</code>
   */

  public static VectorI2I subtract(
    final VectorI2I v0,
    final VectorI2I v1)
  {
    return new VectorI2I(v0.x - v1.x, v0.y - v1.y);
  }

  public final int x;
  public final int y;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0]</code>.
   */

  public VectorI2I()
  {
    this.x = 0;
    this.y = 0;
  }

  public VectorI2I(
    final int x,
    final int y)
  {
    this.x = x;
    this.y = y;
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

}
