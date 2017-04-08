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

import com.io7m.jtensors.core.parameterized.vectors.PVector4D;
import com.io7m.jtensors.core.parameterized.vectors.PVector4F;
import com.io7m.jtensors.core.parameterized.vectors.PVectorReadable4DType;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating4Type;

/**
 * The type of mutable 4D floating point number vectors.
 *
 * @param <T> A phantom type parameter
 */

public interface PVectorStorageFloating4Type<T>
  extends PVectorReadable4DType<T>, PVectorStorageFloating3Type<T>, VectorStorageFloating4Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector4D(
    final PVector4D<T> v)
  {
    this.setXYZW(v.x(), v.y(), v.z(), v.w());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector4F(
    final PVector4F<T> v)
  {
    this.setXYZW(
      (double) v.x(),
      (double) v.y(),
      (double) v.z(),
      (double) v.w());
  }
}
