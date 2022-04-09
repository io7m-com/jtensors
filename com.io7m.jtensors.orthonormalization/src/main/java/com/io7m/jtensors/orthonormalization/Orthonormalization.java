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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4D;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to orthonormalize sets of vectors.
 */

public final class Orthonormalization
{
  private Orthonormalization()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Orthonormalize the given vectors.
   *
   * @param v0 Vector 0
   * @param v1 Vector 1
   * @param v2 Vector 2
   *
   * @return The set of vectors, orthonormalized
   */

  public static Orthonormalized3D orthonormalize3D(
    final Vector3D v0,
    final Vector3D v1,
    final Vector3D v2)
  {
    final Vector3D rv0 = Vectors3D.normalize(v0);
    final Vector3D rv1;
    final Vector3D rv2;

    {
      final double v0_dot_v1 = Vectors3D.dotProduct(rv0, v1);
      final Vector3D v0_s = Vectors3D.scale(rv0, v0_dot_v1);
      rv1 = Vectors3D.normalize(Vectors3D.subtract(v1, v0_s));
    }

    {
      final double v1_dot_v2 = Vectors3D.dotProduct(rv1, v2);
      final double v0_dot_v2 = Vectors3D.dotProduct(rv0, v2);

      final Vector3D v0_s = Vectors3D.scale(rv0, v0_dot_v2);
      final Vector3D v2_s = Vectors3D.scale(rv1, v1_dot_v2);
      final Vector3D vs = Vectors3D.add(v0_s, v2_s);
      rv2 = Vectors3D.normalize(Vectors3D.subtract(v2, vs));
    }

    return Orthonormalized3D.of(rv0, rv1, rv2);
  }

  /**
   * Orthonormalize the given vectors.
   *
   * @param v0 Vector 0
   * @param v1 Vector 1
   * @param v2 Vector 2
   *
   * @return The set of vectors, orthonormalized
   */

  public static Orthonormalized4D orthonormalize4D(
    final Vector4D v0,
    final Vector4D v1,
    final Vector4D v2)
  {
    final Vector4D rv0 = Vectors4D.normalize(v0);
    final Vector4D rv1;
    final Vector4D rv2;

    {
      final double v0_dot_v1 = Vectors4D.dotProduct(rv0, v1);
      final Vector4D v0_s = Vectors4D.scale(rv0, v0_dot_v1);
      rv1 = Vectors4D.normalize(Vectors4D.subtract(v1, v0_s));
    }

    {
      final double v1_dot_v2 = Vectors4D.dotProduct(rv1, v2);
      final double v0_dot_v2 = Vectors4D.dotProduct(rv0, v2);

      final Vector4D v0_s = Vectors4D.scale(rv0, v0_dot_v2);
      final Vector4D v2_s = Vectors4D.scale(rv1, v1_dot_v2);
      final Vector4D vs = Vectors4D.add(v0_s, v2_s);
      rv2 = Vectors4D.normalize(Vectors4D.subtract(v2, vs));
    }

    return Orthonormalized4D.of(rv0, rv1, rv2);
  }
}
