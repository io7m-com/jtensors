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
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4F;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * A matrix generator.
 *
 * @param <A> A phantom type parameter (possibly representing a source
 *            coordinate system)
 * @param <B> A phantom type parameter (possibly representing a target
 *            coordinate system)
 */

public final class PMatrix4x4FGenerator<A, B> implements Generator<PMatrix4x4F<A, B>>
{
  private final Generator<Double> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A component generator
   */

  public PMatrix4x4FGenerator(
    final Generator<Double> in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "Generator");
  }

  /**
   * Create a generator initialized with a default component generator.
   *
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A generator
   */

  public static <A, B> Generator<PMatrix4x4F<A, B>> create()
  {
    return new PMatrix4x4FGenerator<>(PrimitiveGenerators.doubles(
      GeneratorConstants.BOUND_LARGE_FLOAT_LOWER,
      GeneratorConstants.BOUND_LARGE_FLOAT_UPPER
    ));
  }

  /**
   * Create a generator initialized with a default component generator that only
   * produces values in the range {@code [-1.0, 1.0]}.
   *
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A generator
   */

  public static <A, B> Generator<PMatrix4x4F<A, B>> createNormal()
  {
    return new PMatrix4x4FGenerator<>(PrimitiveGenerators.doubles(
      GeneratorConstants.BOUND_NORMAL_FLOAT_LOWER,
      GeneratorConstants.BOUND_NORMAL_FLOAT_UPPER
    ));
  }

  @Override
  public PMatrix4x4F<A, B> next()
  {
    return PMatrix4x4F.of(
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue(),
      this.gen.next().floatValue());
  }
}
