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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4L;
import com.io7m.jtensors.core.unparameterized.vectors.VectorReadable4LType;

/**
 * The type of mutable 4D integral number vectors.
 */

public interface VectorStorageIntegral4Type
  extends VectorReadable4LType, VectorStorageIntegral3Type
{
  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector4L(
    final Vector4L v)
  {
    this.setXYZW(v.x(), v.y(), v.z(), v.w());
  }

  /**
   * Set the components from the given vector.
   *
   * @param v The source vector
   */

  default void setVector4I(
    final Vector4I v)
  {
    this.setXYZW((long) v.x(), (long) v.y(), (long) v.z(), (long) v.w());
  }

  /**
   * Set the {@code w} component.
   *
   * @param w The W component
   */

  void setW(
    long w);

  /**
   * Set the {@code x}, {@code y}, {@code z}, and {@code w} components.
   *
   * @param x The X component
   * @param y The Y component
   * @param z The Z component
   * @param w The W component
   */

  default void setXYZW(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    this.setX(x);
    this.setY(y);
    this.setZ(z);
    this.setW(w);
  }
}
