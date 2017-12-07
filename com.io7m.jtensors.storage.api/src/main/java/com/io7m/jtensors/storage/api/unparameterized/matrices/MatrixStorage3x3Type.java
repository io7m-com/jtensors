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

package com.io7m.jtensors.storage.api.unparameterized.matrices;

import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;
import com.io7m.jtensors.core.unparameterized.matrices.MatrixReadable3x3DType;
import com.io7m.jtensors.storage.api.MatrixStorageType;

/**
 * The type of mutable 3x3 floating point number matrices.
 */

public interface MatrixStorage3x3Type
  extends MatrixReadable3x3DType, MatrixStorageType
{
  /**
   * Set the components from the given matrix.
   *
   * @param m The source matrix
   */

  void setMatrix3x3D(
    Matrix3x3D m);

  /**
   * Set the components from the given matrix.
   *
   * @param m The source matrix
   */

  void setMatrix3x3F(
    Matrix3x3F m);
}
