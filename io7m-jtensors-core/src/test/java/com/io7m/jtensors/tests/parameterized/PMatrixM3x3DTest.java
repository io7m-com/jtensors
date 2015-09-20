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

import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixReadable3x3DType;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public final class PMatrixM3x3DTest<T0, T1, T2>
  extends PMatrixDirect3x3DContract<T0, T1, T2,
  PMatrixM3x3D<T0, T1>,
  PMatrixM3x3D<T0, T1>,
  PMatrixM3x3D<T1, T2>,
  PMatrixM3x3D<T0, T2>,
  PMatrixM3x3D<T1, T0>>
{
  //@formatter:on
  @Override protected PMatrixM3x3D<T0, T1> newMatrix()
  {
    return new PMatrixM3x3D<T0, T1>();
  }

  @Override protected PMatrixM3x3D<T1, T2> newMatrixMultLeft()
  {
    return new PMatrixM3x3D<T1, T2>();
  }

  @Override protected PMatrixM3x3D<T0, T1> newMatrixMultRight()
  {
    return new PMatrixM3x3D<T0, T1>();
  }

  @Override protected PMatrixM3x3D<T0, T2> newMatrixMultResult()
  {
    return new PMatrixM3x3D<T0, T2>();
  }

  @Override protected PMatrixM3x3D<T1, T0> newMatrixInverse()
  {
    return new PMatrixM3x3D<T1, T0>();
  }

  @Override protected PMatrixM3x3D<T0, T1> newMatrixFrom(
    final PMatrixReadable3x3DType<T0, T1> m)
  {
    final PMatrixM3x3D<T0, T1> r = new PMatrixM3x3D<T0, T1>();
    MatrixM3x3D.copy(m, r);
    return r;
  }

  @Override
  protected void checkDirectBufferInvariants(final PMatrixM3x3D<T0, T1> m)
  {
    final PMatrixM3x3D<T0, T1> r = m;
    Assert.assertEquals(0L, (long) r.getDirectDoubleBuffer().position());
  }

  @Override @SuppressWarnings("unchecked")
  protected void checkDirectBufferInvariants(
    final PMatrixReadable3x3DType<?, ?> m)
  {
    final PMatrixM3x3D<T0, T1> r = (PMatrixM3x3D<T0, T1>) m;
    Assert.assertEquals(0L, (long) r.getDirectDoubleBuffer().position());
  }

  @Test public void testConstruct()
  {
    final PMatrixM3x3D<T0, T1> m0 = new PMatrixM3x3D<T0, T1>();
    final PMatrixM3x3D<T0, T1> m1 = new PMatrixM3x3D<T0, T1>(m0);
    Assert.assertEquals(m0, m1);
  }
}
