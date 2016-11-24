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
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixI4x4F;
import net.java.quickcheck.Generator;

/**
 * A matrix generator.
 */

public final class MatrixI4x4FGenerator implements Generator<MatrixI4x4F>
{
  private final Generator<Matrix4x4FType> mgen;

  /**
   * Construct a generator.
   *
   * @param in_mgen A matrix generator
   */

  public MatrixI4x4FGenerator(
    final Generator<Matrix4x4FType> in_mgen)
  {
    this.mgen = NullCheck.notNull(in_mgen, "Generator");
  }

  /**
   * Construct a generator.
   */

  public MatrixI4x4FGenerator()
  {
    this.mgen = new MatrixHeapArrayM4x4FGenerator();
  }

  @Override
  public MatrixI4x4F next()
  {
    return MatrixI4x4F.newFromReadable(this.mgen.next());
  }
}
