/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixHeapArrayM2x2D;
import com.io7m.jtensors.MatrixReadable2x2DType;

public final class MatrixHeapArrayM2x2DTest
  extends Matrix2x2DContract<Matrix2x2DType>
{
  @Override protected Matrix2x2DType newMatrix()
  {
    return MatrixHeapArrayM2x2D.newMatrix();
  }

  @Override protected Matrix2x2DType newMatrixFrom(
    final MatrixReadable2x2DType source)
  {
    return MatrixHeapArrayM2x2D.newMatrixFrom(source);
  }

  @Override protected void checkDirectBufferInvariants(final Matrix2x2DType m)
  {
    // Nothing required
  }
}
