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
import com.io7m.jtensors.Matrix2x2DType;
import com.io7m.jtensors.MatrixI2x2D;
import net.java.quickcheck.Generator;

/**
 * A matrix generator.
 */

public final class MatrixI2x2DGenerator implements Generator<MatrixI2x2D>
{
  private final Generator<Matrix2x2DType> mgen;

  /**
   * Construct a generator.
   *
   * @param in_mgen A matrix generator
   */

  public MatrixI2x2DGenerator(
    final Generator<Matrix2x2DType> in_mgen)
  {
    this.mgen = NullCheck.notNull(in_mgen, "Generator");
  }

  /**
   * Construct a generator.
   */

  public MatrixI2x2DGenerator()
  {
    this.mgen = new MatrixHeapArrayM2x2DGenerator();
  }

  @Override
  public MatrixI2x2D next()
  {
    return MatrixI2x2D.newFromReadable(this.mgen.next());
  }
}
