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

import com.io7m.jtensors.parameterized.PMatrix4x4DType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4D;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4DType;

//@formatter:off
public final class PMatrixHeapArrayM4x4DTest<T0, T1, T2>
  extends PMatrix4x4DContract<T0, T1, T2,
  PMatrix4x4DType<T0, T1>,
  PMatrix4x4DType<T0, T1>,
  PMatrix4x4DType<T1, T2>,
  PMatrix4x4DType<T0, T2>,
  PMatrix4x4DType<T1, T0>>
{
  //@formatter:on

  @Override
  protected PMatrix4x4DType<T0, T1> newMatrix()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override
  protected PMatrix4x4DType<T1, T2> newMatrixMultLeft()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override
  protected PMatrix4x4DType<T0, T1> newMatrixMultRight()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override
  protected PMatrix4x4DType<T0, T2> newMatrixMultResult()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override
  protected PMatrix4x4DType<T1, T0> newMatrixInverse()
  {
    return PMatrixHeapArrayM4x4D.newMatrix();
  }

  @Override
  protected PMatrix4x4DType<T0, T1> newMatrixFrom(
    final PMatrixReadable4x4DType<T0, T1> m)
  {
    return PMatrixHeapArrayM4x4D.newMatrixFrom(m);
  }

  @Override
  protected void checkDirectBufferInvariantsGeneric(
    final PMatrix4x4DType<T0, T1> m)
  {
    // Nothing
  }

  @Override
  protected void checkDirectBufferInvariantsUntyped(
    final PMatrix4x4DType<?, ?> m)
  {
    // Nothing
  }
}
