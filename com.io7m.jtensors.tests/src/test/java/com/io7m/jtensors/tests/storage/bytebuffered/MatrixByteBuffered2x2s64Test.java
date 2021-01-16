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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;
import com.io7m.jtensors.generators.Matrix2x2DGenerator;
import com.io7m.jtensors.generators.Matrix2x2FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage2x2Type;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered2x2Type;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered2x2s64;
import com.io7m.jtensors.tests.core.TestDOps;
import com.io7m.mutable.numbers.core.MutableLong;
import net.java.quickcheck.Generator;

import java.nio.ByteBuffer;

public final class MatrixByteBuffered2x2s64Test
  extends MatrixByteBuffered2x2Contract
{

  @Override
  protected MatrixStorage2x2Type create(
    final int offset)
  {
    return this.create(MutableLong.create(), offset);
  }

  @Override
  protected MatrixByteBuffered2x2Type create(
    final MutableLong base,
    final int offset)
  {
    return MatrixByteBuffered2x2s64.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      base,
      offset);
  }

  @Override
  protected Generator<Matrix2x2D> createGenerator2x2D()
  {
    return Matrix2x2DGenerator.createNormal();
  }

  @Override
  protected Generator<Matrix2x2F> createGenerator2x2F()
  {
    return Matrix2x2FGenerator.createNormal();
  }

  @Override
  protected void checkAlmostEquals(
    final double x,
    final double y)
  {
    TestDOps.checkAlmostEquals(x, y);
  }
}
