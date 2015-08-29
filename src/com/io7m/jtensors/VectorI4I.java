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
 * A four-dimensional immutable vector type with integer elements.
 */

public final class VectorI4I implements VectorReadable4I, VectorReadable3I
{
  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   */

  public static VectorI4I add(
    final VectorI4I v0,
    final VectorI4I v1)
  {
    return new VectorI4I(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   */

  public static VectorI4I subtract(
    final VectorI4I v0,
    final VectorI4I v1)
  {
    return new VectorI4I(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w);
  }

  public final int x;
  public final int y;

  public final int z;

  public final int w;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0, 1]</code>.
   */

  public VectorI4I()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 1;
  }

  public VectorI4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  @Override public int getW()
  {
    return this.w;
  }

  @Override public int getX()
  {
    return this.x;
  }

  @Override public int getY()
  {
    return this.y;
  }

  @Override public int getZ()
  {
    return this.z;
  }
}
