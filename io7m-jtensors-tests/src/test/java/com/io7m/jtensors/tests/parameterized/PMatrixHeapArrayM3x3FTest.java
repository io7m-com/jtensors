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

import com.io7m.jtensors.parameterized.PMatrix3x3FType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM3x3F;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3FType;

//@formatter:off
public final class PMatrixHeapArrayM3x3FTest<T0, T1, T2>
  extends PMatrix3x3FContract<T0, T1, T2,
  PMatrix3x3FType<T0, T1>,
  PMatrix3x3FType<T0, T1>,
  PMatrix3x3FType<T1, T2>,
  PMatrix3x3FType<T0, T2>,
  PMatrix3x3FType<T1, T0>>
{
  //@formatter:on

  @Override
  protected PMatrix3x3FType<T0, T1> newMatrix()
  {
    return PMatrixHeapArrayM3x3F.newMatrix();
  }

  @Override
  protected PMatrix3x3FType<T1, T2> newMatrixMultLeft()
  {
    return PMatrixHeapArrayM3x3F.newMatrix();
  }

  @Override
  protected PMatrix3x3FType<T0, T1> newMatrixMultRight()
  {
    return PMatrixHeapArrayM3x3F.newMatrix();
  }

  @Override
  protected PMatrix3x3FType<T0, T2> newMatrixMultResult()
  {
    return PMatrixHeapArrayM3x3F.newMatrix();
  }

  @Override
  protected PMatrix3x3FType<T1, T0> newMatrixInverse()
  {
    return PMatrixHeapArrayM3x3F.newMatrix();
  }

  @Override
  protected PMatrix3x3FType<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3FType<T0, T1> m)
  {
    return PMatrixHeapArrayM3x3F.newMatrixFrom(m);
  }

  @Override
  protected void checkDirectBufferInvariantsWildcard(
    final PMatrixReadable3x3FType<?, ?> m)
  {
    // Nothing
  }

  @Override
  protected void checkDirectBufferInvariants(
    final PMatrix3x3FType<T0, T1> m)
  {
    // Nothing
  }
}
