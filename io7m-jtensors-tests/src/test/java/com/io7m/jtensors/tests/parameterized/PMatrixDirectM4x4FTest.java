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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.MatrixDirectFType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixDirect4x4FType;
import com.io7m.jtensors.parameterized.PMatrixDirectM4x4F;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4FType;
import org.junit.Assert;

//@formatter:off
public final class PMatrixDirectM4x4FTest<T0, T1, T2>
  extends PMatrixDirect4x4FContract<T0, T1, T2,
  PMatrixDirect4x4FType<T0, T1>,
  PMatrixDirect4x4FType<T0, T1>,
  PMatrixDirect4x4FType<T1, T2>,
  PMatrixDirect4x4FType<T0, T2>,
  PMatrixDirect4x4FType<T1, T0>>
{
  //@formatter:on

  @Override
  protected PMatrixDirect4x4FType<T0, T1> newMatrix()
  {
    return PMatrixDirectM4x4F.newMatrix();
  }

  @Override
  protected PMatrixDirect4x4FType<T1, T2> newMatrixMultLeft()
  {
    return PMatrixDirectM4x4F.newMatrix();
  }

  @Override
  protected PMatrixDirect4x4FType<T0, T1> newMatrixMultRight()
  {
    return PMatrixDirectM4x4F.newMatrix();
  }

  @Override
  protected PMatrixDirect4x4FType<T0, T2> newMatrixMultResult()
  {
    return PMatrixDirectM4x4F.newMatrix();
  }

  @Override
  protected PMatrixDirect4x4FType<T1, T0> newMatrixInverse()
  {
    return PMatrixDirectM4x4F.newMatrix();
  }

  @Override
  protected PMatrixDirect4x4FType<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4FType<T0, T1> m)
  {
    return PMatrixDirectM4x4F.newMatrixFrom(m);
  }

  @Override
  protected void checkDirectBufferInvariants(
    final PMatrixDirect4x4FType<T0, T1> m)
  {
    Assert.assertEquals(0L, (long) m.getDirectFloatBuffer().position());
  }

  @Override
  protected void checkDirectBufferInvariantsUntyped(
    final PMatrix4x4FType<?, ?> m)
  {
    final MatrixDirectFType mx = (PMatrixDirect4x4FType<?, ?>) m;
    Assert.assertEquals(0L, (long) mx.getDirectFloatBuffer().position());
  }
}
