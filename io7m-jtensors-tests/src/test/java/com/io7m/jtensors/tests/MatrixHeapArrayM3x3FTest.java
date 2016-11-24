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

import com.io7m.jtensors.Matrix3x3FType;
import com.io7m.jtensors.MatrixHeapArrayM3x3F;
import com.io7m.jtensors.MatrixReadable3x3FType;

public final class MatrixHeapArrayM3x3FTest
  extends Matrix3x3FContract<Matrix3x3FType>
{
  @Override protected Matrix3x3FType newMatrix()
  {
    return MatrixHeapArrayM3x3F.newMatrix();
  }

  @Override protected Matrix3x3FType newMatrixFrom(
    final MatrixReadable3x3FType source)
  {
    return MatrixHeapArrayM3x3F.newMatrixFrom(source);
  }

  @Override protected void checkDirectBufferInvariants(final Matrix3x3FType m)
  {
    // Nothing required
  }
}
