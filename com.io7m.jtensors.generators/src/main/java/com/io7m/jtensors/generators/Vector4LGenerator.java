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
import com.io7m.jtensors.core.unparameterized.vectors.Vector4L;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * A vector generator.
 */

public final class Vector4LGenerator implements Generator<Vector4L>
{
  private final Generator<Long> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A component generator
   */

  public Vector4LGenerator(
    final Generator<Long> in_gen)
  {
    this.gen = NullCheck.notNull(in_gen, "Generator");
  }

  /**
   * Create a generator initialized with a default component generator.
   *
   * @return A generator
   */

  public static Generator<Vector4L> create()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -2147483647L,
      2147483648L
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-65536, 65536]}.
   *
   * @return A generator
   */

  public static Generator<Vector4L> createSmall()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -65536L,
      65536L
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-9223372036854775808,
   * 9223372036854775807]}.
   *
   * @return A generator
   */

  public static Generator<Vector4L> create64()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -9223372036854775808L,
      -9223372036854775807L
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-2147483648, 2147483647]}.
   *
   * @return A generator
   */

  public static Generator<Vector4L> create32()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -2147483648L,
      -2147483647L
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-32768, 32767]}.
   *
   * @return A generator
   */

  public static Generator<Vector4L> create16()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -32768L,
      32767L
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-128, 127]}.
   *
   * @return A generator
   */

  public static Generator<Vector4L> create8()
  {
    return new Vector4LGenerator(PrimitiveGenerators.longs(
      -128L,
      127L
    ));
  }

  @Override
  public Vector4L next()
  {
    return Vector4L.of(
      this.gen.next().longValue(),
      this.gen.next().longValue(),
      this.gen.next().longValue(),
      this.gen.next().longValue());
  }
}
