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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixReadable3x3FType;
import com.io7m.jtensors.parameterized.PMatrixM3x3F;
import com.io7m.jtensors.tests.MatrixDirect3x3FContract;
import org.junit.Assert;

public final class PMatrixM3x3FUntypedTest<T0, T1>
  extends MatrixDirect3x3FContract<PMatrixM3x3F<T0, T1>>
{
  @Override protected PMatrixM3x3F<T0, T1> newMatrix()
  {
    return new PMatrixM3x3F<T0, T1>();
  }

  @Override protected PMatrixM3x3F<T0, T1> newMatrixFrom(
    final MatrixReadable3x3FType source)
  {
    final PMatrixM3x3F<T0, T1> m = new PMatrixM3x3F<T0, T1>();
    MatrixM3x3F.copy(source, m);
    return m;
  }

  @Override
  protected void checkDirectBufferInvariants(final PMatrixM3x3F<T0, T1> m)
  {
    Assert.assertEquals(0L, (long) m.getDirectFloatBuffer().position());
  }
}
