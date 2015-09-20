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

import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PMatrixReadable4x4FType;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public final class PMatrixM4x4FTest<T0, T1, T2>
  extends PMatrixDirect4x4FContract<T0, T1, T2,
  PMatrixM4x4F<T0, T1>,
  PMatrixM4x4F<T0, T1>,
  PMatrixM4x4F<T1, T2>,
  PMatrixM4x4F<T0, T2>,
  PMatrixM4x4F<T1, T0>>
{
  //@formatter:on
  @Override protected PMatrixM4x4F<T0, T1> newMatrix()
  {
    return new PMatrixM4x4F<T0, T1>();
  }

  @Override protected PMatrixM4x4F<T1, T2> newMatrixMultLeft()
  {
    return new PMatrixM4x4F<T1, T2>();
  }

  @Override protected PMatrixM4x4F<T0, T1> newMatrixMultRight()
  {
    return new PMatrixM4x4F<T0, T1>();
  }

  @Override protected PMatrixM4x4F<T0, T2> newMatrixMultResult()
  {
    return new PMatrixM4x4F<T0, T2>();
  }

  @Override protected PMatrixM4x4F<T1, T0> newMatrixInverse()
  {
    return new PMatrixM4x4F<T1, T0>();
  }

  @Override
  protected PMatrixM4x4F<T0, T1> newMatrixFrom(final
  PMatrixReadable4x4FType<T0, T1> m)
  {
    final PMatrixM4x4F<T0, T1> r = new PMatrixM4x4F<T0, T1>();
    MatrixM4x4F.copy(m, r);
    return r;
  }

  @Override @SuppressWarnings("unchecked")
  protected void checkDirectBufferInvariants(final PMatrix4x4FType<?, ?> m)
  {
    final PMatrixM4x4F<T0, T1> r = (PMatrixM4x4F<T0, T1>) m;
    Assert.assertEquals(0L, (long) r.getDirectFloatBuffer().position());
  }

  @Override
  protected void checkDirectBufferInvariants(final PMatrixM4x4F<T0, T1> m)
  {
    Assert.assertEquals(0L, (long) m.getDirectFloatBuffer().position());
  }

  @Test public void testConstruct()
  {
    final PMatrixM4x4F<T0, T1> m0 = new PMatrixM4x4F<T0, T1>();
    final PMatrixM4x4F<T0, T1> m1 = new PMatrixM4x4F<T0, T1>(m0);
    Assert.assertEquals(m0, m1);
  }
}
