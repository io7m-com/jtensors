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

package com.io7m.jtensors.tests.bytebuffered.parameterized;

import com.io7m.jtensors.bytebuffered.parameterized.PMatrixByteBuffered4x4FType;
import com.io7m.jtensors.tests.parameterized.PMatrix4x4FBufferedContract;
import org.junit.Assert;
import org.junit.Test;

//@formatter:off
public abstract class PMatrixByteBuffered4x4FContract<
  T0,
  T1,
  T2,
  T           extends PMatrixByteBuffered4x4FType<T0, T1>,
  TMULTRIGHT  extends PMatrixByteBuffered4x4FType<T0, T1>,
  TMULTLEFT   extends PMatrixByteBuffered4x4FType<T1, T2>,
  TMULTRESULT extends PMatrixByteBuffered4x4FType<T0, T2>,
  TINVERSE    extends PMatrixByteBuffered4x4FType<T1, T0>>
  extends PMatrix4x4FBufferedContract<
    T0,
    T1,
    T2,
    T,
    TMULTRIGHT,
    TMULTLEFT,
    TMULTRESULT,
    TINVERSE>
{
  //@formatter:on

  @Test public final void testByteOffsetSetGetIdentity()
  {
    final T v = this.newMatrix();
    v.setByteOffset(23L);
    Assert.assertEquals(23L, v.getByteOffset());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public final void testByteOffsetSetOutOfRange()
  {
    final T v = this.newMatrix();
    v.setByteOffset((long) Integer.MAX_VALUE);
  }
}
