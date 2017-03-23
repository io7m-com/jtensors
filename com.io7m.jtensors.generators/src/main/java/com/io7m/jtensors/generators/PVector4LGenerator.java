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

package com.io7m.jtensors.generators;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * A vector generator.
 *
 * @param <T>
 */

public final class PVector4LGenerator<T> implements Generator<PVector4L<T>>
{
  private final Generator<Long> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A component generator
   */

  public PVector4LGenerator(
    final Generator<Long> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "Generator");
  }

  /**
   * Create a generator initialized with a default component generator.
   *
   * @param <T> A phantom type parameter
   *
   * @return A generator
   */

  public static <T> Generator<PVector4L<T>> create()
  {
    return new PVector4LGenerator<>(PrimitiveGenerators.longs(
      -2147483647L,
      2147483648L
    ));
  }

  @Override
  public PVector4L<T> next()
  {
    return PVector4L.of(
      this.gen.next().longValue(),
      this.gen.next().longValue(),
      this.gen.next().longValue(),
      this.gen.next().longValue());
  }
}
