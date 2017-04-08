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

import com.io7m.jtensors.core.parameterized.vectors.PVector3I;
import com.io7m.jtensors.core.parameterized.vectors.PVector3L;
import com.io7m.jtensors.core.parameterized.vectors.PVectorReadable3LType;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral3Type;

/**
 * The type of mutable 3D integral number vectors.
 *
 * @param <T> A phantom type parameter
 */

public interface PVectorStorageIntegral3Type<T>
  extends PVectorReadable3LType<T>,
  PVectorStorageIntegral2Type<T>,
  VectorStorageIntegral3Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector3L(
    final PVector3L<T> v)
  {
    this.setXYZ(v.x(), v.y(), v.z());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector3I(
    final PVector3I<T> v)
  {
    this.setXYZ((long) v.x(), (long) v.y(), (long) v.z());
  }
}
