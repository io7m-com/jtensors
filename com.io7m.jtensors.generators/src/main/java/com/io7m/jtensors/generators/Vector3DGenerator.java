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
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

/**
 * A vector generator.
 */

public final class Vector3DGenerator implements Generator<Vector3D>
{
  private final Generator<Double> gen;

  /**
   * Construct a generator.
   *
   * @param in_gen A component generator
   */

  public Vector3DGenerator(
    final Generator<Double> in_gen)
  {
    this.gen = Objects.requireNonNull(in_gen, "Generator");
  }

  /**
   * Create a generator initialized with a default component generator.
   *
   * @return A generator
   */

  public static Generator<Vector3D> create()
  {
    return new Vector3DGenerator(PrimitiveGenerators.doubles(
      GeneratorConstants.BOUND_LARGE_DOUBLE_LOWER,
      GeneratorConstants.BOUND_LARGE_DOUBLE_UPPER
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-65536.0, 65536.0]}.
   *
   * @return A generator
   */

  public static Generator<Vector3D> createSmall()
  {
    return new Vector3DGenerator(PrimitiveGenerators.doubles(
      GeneratorConstants.BOUND_SMALL_DOUBLE_LOWER,
      GeneratorConstants.BOUND_SMALL_DOUBLE_UPPER
    ));
  }

  /**
   * Create a generator initialized with a default component generator that
   * produces values in the range {@code [-1.0, 1.0]}.
   *
   * @return A generator
   */

  public static Generator<Vector3D> createNormal()
  {
    return new Vector3DGenerator(PrimitiveGenerators.doubles(
      GeneratorConstants.BOUND_NORMAL_DOUBLE_LOWER,
      GeneratorConstants.BOUND_NORMAL_DOUBLE_UPPER
    ));
  }

  @Override
  public Vector3D next()
  {
    return Vector3D.of(
      this.gen.next().doubleValue(),
      this.gen.next().doubleValue(),
      this.gen.next().doubleValue());
  }
}
