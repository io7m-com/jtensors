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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A four-dimensional immutable vector type with integer elements.
 * 
 * Values of this type are immutable and can therefore be safely accessed from
 * multiple threads.
 */

@Immutable public final class VectorI4I implements VectorReadable4I
{
  /**
   * The zero vector.
   */

  public static final @Nonnull VectorI4I ZERO = new VectorI4I(0, 0, 0, 0);

  /**
   * Calculate the element-wise sum of the vectors <code>v0</code> and
   * <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w)</code>
   */

  public static @Nonnull VectorI4I add(
    final @Nonnull VectorI4I v0,
    final @Nonnull VectorI4I v1)
  {
    return new VectorI4I(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z, v0.w + v1.w);
  }

  /**
   * Subtract the vector <code>v0</code> from the vector <code>v1</code>.
   * 
   * @param v0
   *          The left input vector
   * @param v1
   *          The right input vector
   * 
   * @return <code>(v0.x - v1.x, v0.y - v1.y, v0.z - v1.z, v0.w - v1.w)</code>
   */

  public static @Nonnull VectorI4I subtract(
    final @Nonnull VectorI4I v0,
    final @Nonnull VectorI4I v1)
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

  /**
   * Construct a vector initialized with the given values.
   */

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

  /**
   * Construct a vector initialized with the values given in the vector
   * <code>v</code>.
   */

  public VectorI4I(
    final @Nonnull VectorReadable4I v)
  {
    this.x = v.getXI();
    this.y = v.getYI();
    this.z = v.getZI();
    this.w = v.getWI();
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
    final @Nonnull VectorI4I other = (VectorI4I) obj;
    if (this.w != other.w) {
      return false;
    }
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

  @Override public int getWI()
  {
    return this.w;
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
    result = (prime * result) + this.w;
    result = (prime * result) + this.x;
    result = (prime * result) + this.y;
    result = (prime * result) + this.z;
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[VectorI4I ");
    builder.append(this.x);
    builder.append(" ");
    builder.append(this.y);
    builder.append(" ");
    builder.append(this.z);
    builder.append(" ");
    builder.append(this.w);
    builder.append("]");
    return builder.toString();
  }
}
