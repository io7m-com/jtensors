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

package com.io7m.jtensors.generators.parameterized;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.parameterized.PMatrix3x3FType;
import com.io7m.jtensors.parameterized.PMatrixI3x3F;
import net.java.quickcheck.Generator;

/**
 * A matrix generator.
 *
 * @param <T> A phantom type parameter
 * @param <U> A phantom type parameter
 */

public final class PMatrixI3x3FGenerator<T, U> implements Generator<PMatrixI3x3F<T, U>>
{
  private final Generator<PMatrix3x3FType<T, U>> mgen;

  /**
   * Construct a generator.
   *
   * @param in_mgen A matrix generator
   */

  public PMatrixI3x3FGenerator(
    final Generator<PMatrix3x3FType<T, U>> in_mgen)
  {
    this.mgen = NullCheck.notNull(in_mgen, "Generator");
  }

  /**
   * Construct a generator.
   */

  public PMatrixI3x3FGenerator()
  {
    this.mgen = new PMatrixHeapArrayM3x3FGenerator<T, U>();
  }

  @Override
  public PMatrixI3x3F<T, U> next()
  {
    return PMatrixI3x3F.newFromReadable(this.mgen.next());
  }
}
