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

import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;

public final class MatrixHeapArrayM4x4FTest
  extends Matrix4x4FContract<Matrix4x4FType>
{
  @Override protected Matrix4x4FType newMatrix()
  {
    return MatrixHeapArrayM4x4F.newMatrix();
  }

  @Override protected Matrix4x4FType newMatrixFrom(
    final MatrixReadable4x4FType source)
  {
    return MatrixHeapArrayM4x4F.newMatrixFrom(source);
  }

  @Override protected void checkDirectBufferInvariants(final Matrix4x4FType m)
  {
    // Nothing required
  }
}
