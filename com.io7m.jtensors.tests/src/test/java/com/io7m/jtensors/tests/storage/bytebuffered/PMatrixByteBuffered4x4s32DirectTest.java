/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage4x4Type;
import com.io7m.jtensors.storage.bytebuffered.PMatrixByteBuffered4x4s32;
import com.io7m.jtensors.tests.core.TestDOps;
import com.io7m.jtensors.tests.storage.api.PMatrixStorage4x4Contract;

public final class PMatrixByteBuffered4x4s32DirectTest
  extends PMatrixStorage4x4Contract
{
  @Override
  protected void checkAlmostEqual(
    final double a,
    final double b)
  {
    TestDOps.checkAlmostEquals(a, b);
  }

  @Override
  protected PMatrixStorage4x4Type<Object, Object> createIdentity()
  {
    return PMatrixByteBuffered4x4s32.createDirect();
  }
}
