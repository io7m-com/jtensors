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

import com.io7m.jtensors.MatrixDirectDType;
import com.io7m.jtensors.parameterized.PMatrixDirect3x3DType;
import com.io7m.jtensors.parameterized.PMatrixDirectM3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import org.junit.Assert;

//@formatter:off
public final class PMatrixDirectM3x3DTest<T0, T1, T2>
  extends PMatrixDirect3x3DContract<T0, T1, T2,
  PMatrixDirect3x3DType<T0, T1>,
  PMatrixDirect3x3DType<T0, T1>,
  PMatrixDirect3x3DType<T1, T2>,
  PMatrixDirect3x3DType<T0, T2>,
  PMatrixDirect3x3DType<T1, T0>>
{
  //@formatter:on

  @Override
  protected PMatrixDirect3x3DType<T0, T1> newMatrix()
  {
    return PMatrixDirectM3x3D.newMatrix();
  }

  @Override
  protected PMatrixDirect3x3DType<T1, T2> newMatrixMultLeft()
  {
    return PMatrixDirectM3x3D.newMatrix();
  }

  @Override
  protected PMatrixDirect3x3DType<T0, T1> newMatrixMultRight()
  {
    return PMatrixDirectM3x3D.newMatrix();
  }

  @Override
  protected PMatrixDirect3x3DType<T0, T2> newMatrixMultResult()
  {
    return PMatrixDirectM3x3D.newMatrix();
  }

  @Override
  protected PMatrixDirect3x3DType<T1, T0> newMatrixInverse()
  {
    return PMatrixDirectM3x3D.newMatrix();
  }

  @Override
  protected PMatrixDirect3x3DType<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    return PMatrixDirectM3x3D.newMatrixFrom(m);
  }

  @Override
  protected void checkDirectBufferInvariantsWildcard(
    final PMatrixReadable3x3DType<?, ?> m)
  {
    final MatrixDirectDType mx = (PMatrixDirect3x3DType<?, ?>) m;
    Assert.assertEquals(0L, (long) mx.getDirectDoubleBuffer().position());
  }

  @Override
  protected void checkDirectBufferInvariantsGeneric(
    final PMatrixDirect3x3DType<T0, T1> m)
  {
    Assert.assertEquals(0L, (long) m.getDirectDoubleBuffer().position());
  }
}
