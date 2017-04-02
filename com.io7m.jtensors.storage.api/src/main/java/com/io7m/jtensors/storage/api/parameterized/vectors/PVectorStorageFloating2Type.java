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

package com.io7m.jtensors.storage.api.parameterized.vectors;

import com.io7m.jtensors.core.parameterized.vectors.PVector2D;
import com.io7m.jtensors.core.parameterized.vectors.PVector2F;
import com.io7m.jtensors.core.parameterized.vectors.PVectorReadable2DType;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;

/**
 * The type of mutable 2D floating point number vectors.
 *
 * @param <T> A phantom type parameter
 */

public interface PVectorStorageFloating2Type<T>
  extends PVectorReadable2DType<T>, VectorStorageFloating2Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector2D(
    final PVector2D<T> v)
  {
    this.setXY(v.x(), v.y());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector2F(
    final PVector2F<T> v)
  {
    this.setXY((double) v.x(), (double) v.y());
  }
}
