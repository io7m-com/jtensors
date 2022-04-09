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

package com.io7m.jtensors.orthonormalization;

import com.io7m.jtensors.core.parameterized.vectors.PVector3D;
import com.io7m.jtensors.core.parameterized.vectors.PVector4D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4D;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to orthonormalize sets of vectors.
 */

public final class POrthonormalization
{
  private POrthonormalization()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Orthonormalize the given vectors.
   *
   * @param v0  Vector 0
   * @param v1  Vector 1
   * @param v2  Vector 2
   * @param <T> A phantom type parameter
   *
   * @return The set of vectors, orthonormalized
   */

  public static <T> POrthonormalized3D<T> orthonormalize3D(
    final PVector3D<T> v0,
    final PVector3D<T> v1,
    final PVector3D<T> v2)
  {
    final PVector3D<T> rv0 = PVectors3D.normalize(v0);
    final PVector3D<T> rv1;
    final PVector3D<T> rv2;

    {
      final double v0_dot_v1 = PVectors3D.dotProduct(rv0, v1);
      final PVector3D<T> v0_s = PVectors3D.scale(rv0, v0_dot_v1);
      rv1 = PVectors3D.normalize(PVectors3D.subtract(v1, v0_s));
    }

    {
      final double v1_dot_v2 = PVectors3D.dotProduct(rv1, v2);
      final double v0_dot_v2 = PVectors3D.dotProduct(rv0, v2);

      final PVector3D<T> v0_s = PVectors3D.scale(rv0, v0_dot_v2);
      final PVector3D<T> v2_s = PVectors3D.scale(rv1, v1_dot_v2);
      final PVector3D<T> vs = PVectors3D.add(v0_s, v2_s);
      rv2 = PVectors3D.normalize(PVectors3D.subtract(v2, vs));
    }

    return POrthonormalized3D.of(rv0, rv1, rv2);
  }

  /**
   * Orthonormalize the given vectors.
   *
   * @param v0  Vector 0
   * @param v1  Vector 1
   * @param v2  Vector 2
   * @param <T> A phantom type parameter
   *
   * @return The set of vectors, orthonormalized
   */

  public static <T> POrthonormalized4D<T> orthonormalize4D(
    final PVector4D<T> v0,
    final PVector4D<T> v1,
    final PVector4D<T> v2)
  {
    final PVector4D<T> rv0 = PVectors4D.normalize(v0);
    final PVector4D<T> rv1;
    final PVector4D<T> rv2;

    {
      final double v0_dot_v1 = PVectors4D.dotProduct(rv0, v1);
      final PVector4D<T> v0_s = PVectors4D.scale(rv0, v0_dot_v1);
      rv1 = PVectors4D.normalize(PVectors4D.subtract(v1, v0_s));
    }

    {
      final double v1_dot_v2 = PVectors4D.dotProduct(rv1, v2);
      final double v0_dot_v2 = PVectors4D.dotProduct(rv0, v2);

      final PVector4D<T> v0_s = PVectors4D.scale(rv0, v0_dot_v2);
      final PVector4D<T> v2_s = PVectors4D.scale(rv1, v1_dot_v2);
      final PVector4D<T> vs = PVectors4D.add(v0_s, v2_s);
      rv2 = PVectors4D.normalize(PVectors4D.subtract(v2, vs));
    }

    return POrthonormalized4D.of(rv0, rv1, rv2);
  }
}
