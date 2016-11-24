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

package com.io7m.jtensors.generators;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.Matrix3x3DType;
import com.io7m.jtensors.MatrixHeapArrayM3x3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.support.DoubleGenerator;

/**
 * A matrix generator.
 */

public final class MatrixHeapArrayM3x3DGenerator implements Generator<Matrix3x3DType>
{
  private final Generator<Double> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A scalar generator
   */

  public MatrixHeapArrayM3x3DGenerator(
    final Generator<Double> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "Generator");
  }

  /**
   * Construct a generator.
   */

  public MatrixHeapArrayM3x3DGenerator()
  {
    this.gen = new DoubleGenerator();
  }

  @Override
  public Matrix3x3DType next()
  {
    final Matrix3x3DType m = MatrixHeapArrayM3x3D.newMatrix();
    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        m.setRowColumnD(row, column, this.gen.next().doubleValue());
      }
    }
    return m;
  }
}
