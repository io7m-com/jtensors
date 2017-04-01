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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3F;
import com.io7m.jtensors.core.unparameterized.vectors.VectorReadable3DType;

/**
 * The type of mutable 3D floating point number vectors.
 */

public interface VectorStorageFloating3Type
  extends VectorReadable3DType, VectorStorageFloating2Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector3D(
    final Vector3D v)
  {
    this.setXYZ(v.x(), v.y(), v.z());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector3F(
    final Vector3F v)
  {
    this.setXYZ((double) v.x(), (double) v.y(), (double) v.z());
  }

  /**
   * Set the {@code z} component.
   *
   * @param z The Z component
   */

  void setZ(
    double z);

  /**
   * Set the {@code x}, {@code y}, {@code z} components.
   *
   * @param x The X component
   * @param y The Y component
   * @param z The Z component
   */

  default void setXYZ(
    final double x,
    final double y,
    final double z)
  {
    this.setX(x);
    this.setY(y);
    this.setZ(z);
  }
}
