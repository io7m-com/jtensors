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

package com.io7m.jtensors.storage.api.unparameterized.vectors;

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2F;
import com.io7m.jtensors.core.unparameterized.vectors.VectorReadable2DType;
import com.io7m.jtensors.storage.api.VectorStorageType;

/**
 * The type of mutable 2D floating point number vectors.
 */

public interface VectorStorageFloating2Type
  extends VectorReadable2DType, VectorStorageType
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector2D(
    final Vector2D v)
  {
    this.setXY(v.x(), v.y());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector2F(
    final Vector2F v)
  {
    this.setXY((double) v.x(), (double) v.y());
  }

  /**
   * Set the {@code x} component.
   *
   * @param x The X component
   */

  void setX(
    double x);

  /**
   * Set the {@code Y} component.
   *
   * @param y The Y component
   */

  void setY(
    double y);

  /**
   * Set the {@code x} and {@code y} components.
   *
   * @param x The X component
   * @param y The Y component
   */

  default void setXY(
    final double x,
    final double y)
  {
    this.setX(x);
    this.setY(y);
  }
}
