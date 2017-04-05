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

package com.io7m.jtensors.storage.heap;

import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral3Type;

/**
 * A heap-based vector.
 */

public final class VectorMutable3L
  implements VectorStorageIntegral3Type
{
  private long x;
  private long y;
  private long z;

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final VectorMutable3L that = (VectorMutable3L) o;
    return this.x == that.x
      && this.y == that.y
      && this.z == that.z;
  }

  @Override
  public int hashCode()
  {
    int result = (int) (this.x ^ (this.x >>> 32));
    result = 31 * result + (int) (this.y ^ (this.y >>> 32));
    result = 31 * result + (int) (this.z ^ (this.z >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("x=").append(this.x);
    sb.append(", y=").append(this.y);
    sb.append(", z=").append(this.z);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Create a new vector.
   */

  public VectorMutable3L()
  {

  }

  @Override
  public long x()
  {
    return this.x;
  }

  @Override
  public long y()
  {
    return this.y;
  }

  @Override
  public long z()
  {
    return this.z;
  }

  @Override
  public void setZ(final long in_z)
  {
    this.z = in_z;
  }

  @Override
  public void setX(final long in_x)
  {
    this.x = in_x;
  }

  @Override
  public void setY(final long in_y)
  {
    this.y = in_y;
  }
}
