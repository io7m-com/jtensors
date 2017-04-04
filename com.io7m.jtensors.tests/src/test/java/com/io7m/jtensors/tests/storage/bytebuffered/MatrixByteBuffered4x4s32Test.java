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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;
import com.io7m.jtensors.generators.Matrix4x4DGenerator;
import com.io7m.jtensors.generators.Matrix4x4FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage4x4Type;
import com.io7m.jtensors.storage.bytebuffered.ByteBufferOffsetMutable;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered4x4s32;
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.core.TestFOps;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.storage.api.MatrixStorage4x4Contract;
import net.java.quickcheck.Generator;
import org.junit.Rule;

import java.nio.ByteBuffer;

public final class MatrixByteBuffered4x4s32Test
  extends MatrixStorage4x4Contract
{
  @Rule public final PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Override
  protected MatrixStorage4x4Type create(
    final int offset)
  {
    return MatrixByteBuffered4x4s32.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      ByteBufferOffsetMutable.create(),
      offset);
  }

  @Override
  protected Generator<Matrix4x4D> createGenerator4x4D()
  {
    return Matrix4x4DGenerator.createNormal();
  }

  @Override
  protected Generator<Matrix4x4F> createGenerator4x4F()
  {
    return Matrix4x4FGenerator.createNormal();
  }

  @Override
  protected void checkAlmostEquals(
    final double x,
    final double y)
  {
    TestFOps.checkAlmostEquals(x, y);
  }
}
