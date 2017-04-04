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

package com.io7m.jtensors.storage.api.unparameterized.vectors;

import com.io7m.jtensors.core.unparameterized.vectors.Vector2I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import com.io7m.jtensors.core.unparameterized.vectors.VectorReadable2LType;
import com.io7m.jtensors.storage.api.VectorStorageType;

/**
 * The type of mutable 2D integral number vectors.
 */

public interface VectorStorageIntegral2Type
  extends VectorReadable2LType, VectorStorageType
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector2L(
    final Vector2L v)
  {
    this.setXY(v.x(), v.y());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector2I(
    final Vector2I v)
  {
    this.setXY((long) v.x(), (long) v.y());
  }

  /**
   * Set the {@code x} component.
   *
   * @param x The X component
   */

  void setX(
    long x);

  /**
   * Set the {@code Y} component.
   *
   * @param y The Y component
   */

  void setY(
    long y);

  /**
   * Set the {@code x} and {@code y} components.
   *
   * @param x The X component
   * @param y The Y component
   */

  default void setXY(
    final long x,
    final long y)
  {
    this.setX(x);
    this.setY(y);
  }
}
