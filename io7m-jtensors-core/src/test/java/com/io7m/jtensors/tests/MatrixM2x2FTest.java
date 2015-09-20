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

import com.io7m.jtensors.MatrixM2x2F;
import com.io7m.jtensors.MatrixReadable2x2FType;
import org.junit.Assert;

public final class MatrixM2x2FTest extends MatrixDirect2x2FContract<MatrixM2x2F>
{
  @Override protected MatrixM2x2F newMatrix()
  {
    return new MatrixM2x2F();
  }

  @Override protected MatrixM2x2F newMatrixFrom(
    final MatrixReadable2x2FType source)
  {
    return new MatrixM2x2F(source);
  }

  @Override protected void checkDirectBufferInvariants(final MatrixM2x2F m)
  {
    Assert.assertEquals(0L, (long) m.getDirectFloatBuffer().position());
  }
}
