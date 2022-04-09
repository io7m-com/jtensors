/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jtensors.core.parameterized.vectors.PVector4I;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import com.io7m.jtensors.core.parameterized.vectors.PVectorReadable4LType;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral4Type;

/**
 * The type of mutable 4D integral number vectors.
 *
 * @param <T> A phantom type parameter
 */

public interface PVectorStorageIntegral4Type<T>
  extends PVectorReadable4LType<T>,
  PVectorStorageIntegral3Type<T>,
  VectorStorageIntegral4Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector4L(
    final PVector4L<T> v)
  {
    this.setXYZW(v.x(), v.y(), v.z(), v.w());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setPVector4I(
    final PVector4I<T> v)
  {
    this.setXYZW(
      (long) v.x(),
      (long) v.y(),
      (long) v.z(),
      (long) v.w());
  }
}
