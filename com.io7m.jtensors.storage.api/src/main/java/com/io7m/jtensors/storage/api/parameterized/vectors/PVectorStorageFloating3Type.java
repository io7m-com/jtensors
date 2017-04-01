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

import com.io7m.jtensors.core.parameterized.vectors.PVector3D;
import com.io7m.jtensors.core.parameterized.vectors.PVector3F;
import com.io7m.jtensors.core.parameterized.vectors.PVectorReadable3DType;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;

/**
 * The type of mutable 3D floating point number vectors.
 *
 * @param <T> A phantom type parameter
 */

public interface PVectorStorageFloating3Type<T>
  extends PVectorReadable3DType<T>, PVectorStorageFloating2Type<T>, VectorStorageFloating3Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector3D(
    final PVector3D<T> v)
  {
    this.setXYZ(v.x(), v.y(), v.z());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector3F(
    final PVector3F<T> v)
  {
    this.setXYZ((double) v.x(), (double) v.y(), (double) v.z());
  }
}
