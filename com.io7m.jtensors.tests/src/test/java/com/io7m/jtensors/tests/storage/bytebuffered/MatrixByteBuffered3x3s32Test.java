/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;
import com.io7m.jtensors.generators.Matrix3x3DGenerator;
import com.io7m.jtensors.generators.Matrix3x3FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage3x3Type;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered3x3Type;
import com.io7m.jtensors.storage.bytebuffered.MatrixByteBuffered3x3s32;
import com.io7m.jtensors.tests.core.TestFOps;
import com.io7m.mutable.numbers.core.MutableLong;
import net.java.quickcheck.Generator;

import java.nio.ByteBuffer;

public final class MatrixByteBuffered3x3s32Test
  extends MatrixByteBuffered3x3Contract
{

  @Override
  protected MatrixStorage3x3Type create(
    final int offset)
  {
    return this.create(MutableLong.create(), offset);
  }

  @Override
  protected MatrixByteBuffered3x3Type create(
    final MutableLong base,
    final int offset)
  {
    return MatrixByteBuffered3x3s32.createWithBase(
      ByteBuffer.allocate(BufferSizes.BUFFER_SIZE_DEFAULT),
      base,
      offset);
  }

  @Override
  protected Generator<Matrix3x3D> createGenerator3x3D()
  {
    return Matrix3x3DGenerator.createNormal();
  }

  @Override
  protected Generator<Matrix3x3F> createGenerator3x3F()
  {
    return Matrix3x3FGenerator.createNormal();
  }

  @Override
  protected void checkAlmostEquals(
    final double x,
    final double y)
  {
    TestFOps.checkAlmostEquals(x, y);
  }
}
