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

import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage2x2Type;
import com.io7m.jtensors.storage.bytebuffered.PMatrixByteBuffered2x2s16;
import com.io7m.jtensors.tests.core.TestB16Ops;
import com.io7m.jtensors.tests.storage.api.PMatrixStorage2x2Contract;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public final class PMatrixByteBuffered2x2s16DirectTest
  extends PMatrixStorage2x2Contract
{
  @Override
  protected void checkAlmostEqual(
    final double a,
    final double b)
  {
    TestB16Ops.checkAlmostEquals(a, b);
  }

  @Override
  protected PMatrixStorage2x2Type<Object, Object> createIdentity()
  {
    return PMatrixByteBuffered2x2s16.createDirect();
  }

  @Test
  public void testByteBufferIdentity()
  {
    final ByteBuffer b = ByteBuffer.allocate(1024);
    Assert.assertEquals(
      b,
      PMatrixByteBuffered2x2s16.createWith(b).byteBuffer());
  }
}
