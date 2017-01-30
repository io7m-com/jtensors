/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors;

import com.io7m.jnull.Nullable;
import net.jcip.annotations.Immutable;

/**
 * Three immutable orthonormal vectors.
 *
 * @since 5.2.0
 */

@Immutable
public final class OrthonormalizedI3D
{
  private final VectorI3D rv0;
  private final VectorI3D rv1;
  private final VectorI3D rv2;

  /**
   * Orthonormalize and store the vectors {@code v0}, {@code v1}, and {@code
   * v2}.
   *
   * @param v0 The first vector
   * @param v1 The second vector
   * @param v2 The third vector
   */

  public OrthonormalizedI3D(
    final VectorReadable3DType v0,
    final VectorReadable3DType v1,
    final VectorReadable3DType v2)
  {
    this.rv0 = VectorI3D.normalize(v0);

    {
      final double v0_dot_v1 = VectorI3D.dotProduct(this.rv0, v1);
      final VectorI3D v0_s = VectorI3D.scale(this.rv0, v0_dot_v1);
      this.rv1 = VectorI3D.normalize(VectorI3D.subtract(v1, v0_s));
    }

    {
      final double v1_dot_v2 = VectorI3D.dotProduct(this.rv1, v2);
      final double v0_dot_v2 = VectorI3D.dotProduct(this.rv0, v2);

      final VectorI3D v0_s = VectorI3D.scale(this.rv0, v0_dot_v2);
      final VectorI3D v2_s = VectorI3D.scale(this.rv1, v1_dot_v2);
      final VectorI3D vs = VectorI3D.add(v0_s, v2_s);
      this.rv2 = VectorI3D.normalize(VectorI3D.subtract(v2, vs));
    }
  }

  @Override
  public boolean equals(
    final @Nullable Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final OrthonormalizedI3D other = (OrthonormalizedI3D) obj;
    return this.rv0.equals(other.rv0) && this.rv1.equals(other.rv1) && this.rv2.equals(
      other.rv2);
  }

  /**
   * @return The first vector passed to {@link #OrthonormalizedI3D
   * (VectorReadable3DType, VectorReadable3DType, VectorReadable3DType)} ,
   * orthonormalized with respect to the other two vectors.
   */

  public VectorI3D getV0()
  {
    return this.rv0;
  }

  /**
   * @return The second vector passed to {@link #OrthonormalizedI3D
   * (VectorReadable3DType, VectorReadable3DType, VectorReadable3DType)} ,
   * orthonormalized with respect to the other two vectors.
   */

  public VectorI3D getV1()
  {
    return this.rv1;
  }

  /**
   * @return The third vector passed to {@link #OrthonormalizedI3D
   * (VectorReadable3DType, VectorReadable3DType, VectorReadable3DType)} ,
   * orthonormalized with respect to the other two vectors.
   */

  public VectorI3D getV2()
  {
    return this.rv2;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.rv0.hashCode();
    result = (prime * result) + this.rv1.hashCode();
    result = (prime * result) + this.rv2.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder();
    builder.append("[OrthonormalizedI3D ");
    builder.append(this.rv0);
    builder.append(" ");
    builder.append(this.rv1);
    builder.append(" ");
    builder.append(this.rv2);
    builder.append("]");
    final String r = builder.toString();
    assert r != null;
    return r;
  }
}
