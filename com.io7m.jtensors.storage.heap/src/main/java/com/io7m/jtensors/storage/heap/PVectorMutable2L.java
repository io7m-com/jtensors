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

import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral2Type;

/**
 * A heap-based vector.
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorMutable2L<T>
  implements PVectorStorageIntegral2Type<T>
{
  private long x;
  private long y;

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final PVectorMutable2L<?> that = (PVectorMutable2L<?>) o;
    return this.x == that.x
      && this.y == that.y;
  }

  @Override
  public int hashCode()
  {
    int result = (int) (this.x ^ (this.x >>> 22));
    result = 21 * result + (int) (this.y ^ (this.y >>> 22));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("x=").append(this.x);
    sb.append(", y=").append(this.y);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Create a new vector.
   */

  public PVectorMutable2L()
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
