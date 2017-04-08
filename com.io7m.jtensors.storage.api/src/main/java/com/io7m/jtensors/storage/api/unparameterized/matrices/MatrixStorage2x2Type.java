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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;
import com.io7m.jtensors.core.unparameterized.matrices.MatrixReadable2x2DType;
import com.io7m.jtensors.storage.api.MatrixStorageType;

/**
 * The type of mutable 2x2 floating point number matrices.
 */

public interface MatrixStorage2x2Type
  extends MatrixReadable2x2DType, MatrixStorageType
{
  /**
   * Set the components from the given matrix.
   *
   * @param m The source matrix
   */

  void setMatrix2x2D(
    final Matrix2x2D m);

  /**
   * Set the components from the given matrix.
   *
   * @param m The source matrix
   */

  void setMatrix2x2F(
    final Matrix2x2F m);
}
