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

import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating3Type;

/**
 * A heap-based vector.
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorMutable3D<T>
  implements PVectorStorageFloating3Type<T>
{
  private double x;
  private double y;
  private double z;

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final PVectorMutable3D<?> that = (PVectorMutable3D<?>) o;
    return Double.compare(that.x, this.x) == 0
      && Double.compare(that.y, this.y) == 0
      && Double.compare(that.z, this.z) == 0;
  }

  @Override
  public int hashCode()
  {
    int result;
    long temp;
    temp = Double.doubleToLongBits(this.x);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.y);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.z);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
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

  public PVectorMutable3D()
  {

  }

  @Override
  public double x()
  {
    return this.x;
  }

  @Override
  public double y()
  {
    return this.y;
  }

  @Override
  public double z()
  {
    return this.z;
  }

  @Override
  public void setZ(final double in_z)
  {
    this.z = in_z;
  }

  @Override
  public void setX(final double in_x)
  {
    this.x = in_x;
  }

  @Override
  public void setY(final double in_y)
  {
    this.y = in_y;
  }
}
