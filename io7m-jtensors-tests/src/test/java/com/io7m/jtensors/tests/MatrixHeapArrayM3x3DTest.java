/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests;

import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import com.io7m.jtensors.MatrixReadable3x3DType;

public final class MatrixHeapArrayM3x3DTest
  extends Matrix3x3DContract<Matrix3x3DType>
{
  @Override
  protected Matrix3x3DType newMatrix()
  {
    return MatrixHeapArrayM3x3D.newMatrix();
  }

  @Override
  protected Matrix3x3DType newMatrixFrom(
    final MatrixReadable3x3DType source)
  {
    return MatrixHeapArrayM3x3D.newMatrixFrom(source);
  }

  @Override
  protected void checkDirectBufferInvariants(final Matrix3x3DType m)
  {
    // Nothing required
  }
}
