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
 * A three-dimensional immutable vector type with integer elements.
 */

public final class VectorI3I implements VectorReadable3I
{
  /**
   * The zero vector.
   */

  public static final VectorI3I ZERO = new VectorI3I(0, 0, 0);

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z)</code>
   */

  public static VectorI3I add(
    final VectorI3I v0,
    final VectorI3I v1)
  {
    return new VectorI3I(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z);
  }

  /**
   * @param v0
   *          The left input vector.
   * @param v1
   *          The right input vector.
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z)</code>
   */

  public static VectorI3I subtract(
    final VectorI3I v0,
    final VectorI3I v1)
  {
    return new VectorI3I(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z);
  }

  public final int x;
  public final int y;

  public final int z;

  /**
   * Default constructor, initializing the vector with values
   * <code>[0, 0, 0]</code>.
   */

  public VectorI3I()
  {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }

  public VectorI3I(
    final int x,
    final int y,
    final int z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override public boolean equals(
    final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final VectorI3I other = (VectorI3I) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    return true;
  }

  @Override public int getXI()
  {
    return this.x;
  }

  @Override public int getYI()
  {
    return this.y;
  }

  @Override public int getZI()
  {
    return this.z;
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI3I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append("]");
    return builder.toString();
  }
}
