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

package com.io7m.jtensors.generators;

import java.util.Objects;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3I;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * A vector generator.
 */

public final class Vector3IGenerator implements Generator<Vector3I>
{
  private final Generator<Integer> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A component generator
   */

  public Vector3IGenerator(
    final Generator<Integer> in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "Generator");
  }

  /**
   * Create a generator initialized with a default component generator.
   *
   * @return A generator
   */

  public static Generator<Vector3I> create()
  {
    return new Vector3IGenerator(PrimitiveGenerators.integers(
      -32767,
      32768
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-128, 128]}.
   *
   * @return A generator
   */

  public static Generator<Vector3I> createSmall()
  {
    return new Vector3IGenerator(PrimitiveGenerators.integers(
      -128,
      128
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-2147483648, 2147483647]}.
   *
   * @return A generator
   */

  public static Generator<Vector3I> create32()
  {
    return new Vector3IGenerator(PrimitiveGenerators.integers(
      -2147483648,
      -2147483647
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-32768, 32767]}.
   *
   * @return A generator
   */

  public static Generator<Vector3I> create16()
  {
    return new Vector3IGenerator(PrimitiveGenerators.integers(
      -32768,
      32767
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-128, 127]}.
   *
   * @return A generator
   */

  public static Generator<Vector3I> create8()
  {
    return new Vector3IGenerator(PrimitiveGenerators.integers(
      -128,
      127
    ));
  }

  @Override
  public Vector3I next()
  {
    return Vector3I.of(
      this.gen.next().intValue(),
      this.gen.next().intValue(),
      this.gen.next().intValue());
  }
}
