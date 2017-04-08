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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4F;
import com.io7m.jtensors.core.unparameterized.vectors.VectorReadable4DType;

/**
 * The type of mutable 4D floating point number vectors.
 */

public interface VectorStorageFloating4Type
  extends VectorReadable4DType, VectorStorageFloating3Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector4D(
    final Vector4D v)
  {
    this.setXYZW(v.x(), v.y(), v.z(), v.w());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector4F(
    final Vector4F v)
  {
    this.setXYZW((double) v.x(), (double) v.y(), (double) v.z(), (double) v.w());
  }

  /**
   * Set the {@code w} component.
   *
   * @param w The W component
   */

  void setW(
    double w);

  /**
   * Set the {@code x}, {@code y}, {@code z}, and {@code w} components.
   *
   * @param x The X component
   * @param y The Y component
   * @param z The Z component
   * @param w The W component
   */

  default void setXYZW(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    this.setX(x);
    this.setY(y);
    this.setZ(z);
    this.setW(w);
  }
}
