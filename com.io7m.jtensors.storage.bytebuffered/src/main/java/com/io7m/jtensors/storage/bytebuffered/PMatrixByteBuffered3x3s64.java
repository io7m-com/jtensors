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

package com.io7m.jtensors.storage.bytebuffered;

import com.io7m.jnull.NullCheck;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>A storage matrix.</p>
 * <p>Storage component type: {@code double}</p>
 * <p>Storage component count: {@code 3x3}</p>
 *
 * @param <A> A phantom type parameter (possibly representing a source
 *            coordinate system)
 * @param <B> A phantom type parameter (possibly representing a target
 *            coordinate system)
 */

public final class PMatrixByteBuffered3x3s64<A, B>
  implements PMatrixByteBuffered3x3Type<A, B>
{
  private final DoubleBuffer view;
  private final ByteBuffer buffer;

  private PMatrixByteBuffered3x3s64(
    final ByteBuffer bb)
  {
    this.buffer = NullCheck.notNull(bb, "Buffer");
    this.view = this.buffer.asDoubleBuffer();
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A heap-backed matrix in native byte order
   */

  public static <A, B> PMatrixByteBuffered3x3Type<A, B> createHeap()
  {
    return createWith(ByteBuffer.allocate((3 * 3) * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   *
   * @return A direct-memory matrix in native byte order
   */

  public static <A, B> PMatrixByteBuffered3x3Type<A, B> createDirect()
  {
    return createWith(ByteBuffer.allocateDirect((3 * 3) * 8).order(ByteOrder.nativeOrder()));
  }

  /**
   * @param <A> A phantom type parameter (possibly representing a source
   *            coordinate system)
   * @param <B> A phantom type parameter (possibly representing a target
   *            coordinate system)
   * @param b   A byte buffer
   *
   * @return A matrix backed by the given byte buffer
   */

  public static <A, B> PMatrixByteBuffered3x3Type<A, B> createWith(
    final ByteBuffer b)
  {
    return new PMatrixByteBuffered3x3s64<>(b);
  }

  private static int index(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  @Override
  public double r0c0()
  {
    return this.view.get(index(0, 0));
  }

  @Override
  public double r0c1()
  {
    return this.view.get(index(0, 1));
  }

  @Override
  public double r0c2()
  {
    return this.view.get(index(0, 2));
  }

  @Override
  public double r1c0()
  {
    return this.view.get(index(1, 0));
  }

  @Override
  public double r1c1()
  {
    return this.view.get(index(1, 1));
  }

  @Override
  public double r1c2()
  {
    return this.view.get(index(1, 2));
  }

  @Override
  public double r2c0()
  {
    return this.view.get(index(2, 0));
  }

  @Override
  public double r2c1()
  {
    return this.view.get(index(2, 1));
  }

  @Override
  public double r2c2()
  {
    return this.view.get(index(2, 2));
  }

  @Override
  public void setMatrix3x3D(
    final Matrix3x3D m)
  {
    this.view.put(index(0, 0), m.r0c0());
    this.view.put(index(0, 1), m.r0c1());
    this.view.put(index(0, 2), m.r0c2());

    this.view.put(index(1, 0), m.r1c0());
    this.view.put(index(1, 1), m.r1c1());
    this.view.put(index(1, 2), m.r1c2());

    this.view.put(index(2, 0), m.r2c0());
    this.view.put(index(2, 1), m.r2c1());
    this.view.put(index(2, 2), m.r2c2());
  }

  @Override
  public void setMatrix3x3F(
    final Matrix3x3F m)
  {
    this.view.put(index(0, 0), (double) m.r0c0());
    this.view.put(index(0, 1), (double) m.r0c1());
    this.view.put(index(0, 2), (double) m.r0c2());

    this.view.put(index(1, 0), (double) m.r1c0());
    this.view.put(index(1, 1), (double) m.r1c1());
    this.view.put(index(1, 2), (double) m.r1c2());

    this.view.put(index(2, 0), (double) m.r2c0());
    this.view.put(index(2, 1), (double) m.r2c1());
    this.view.put(index(2, 2), (double) m.r2c2());
  }

  @Override
  public void setPMatrix3x3D(
    final PMatrix3x3D<A, B> m)
  {
    this.view.put(index(0, 0), m.r0c0());
    this.view.put(index(0, 1), m.r0c1());
    this.view.put(index(0, 2), m.r0c2());

    this.view.put(index(1, 0), m.r1c0());
    this.view.put(index(1, 1), m.r1c1());
    this.view.put(index(1, 2), m.r1c2());

    this.view.put(index(2, 0), m.r2c0());
    this.view.put(index(2, 1), m.r2c1());
    this.view.put(index(2, 2), m.r2c2());
  }

  @Override
  public void setPMatrix3x3F(
    final PMatrix3x3F<A, B> m)
  {
    this.view.put(index(0, 0), (double) m.r0c0());
    this.view.put(index(0, 1), (double) m.r0c1());
    this.view.put(index(0, 2), (double) m.r0c2());

    this.view.put(index(1, 0), (double) m.r1c0());
    this.view.put(index(1, 1), (double) m.r1c1());
    this.view.put(index(1, 2), (double) m.r1c2());

    this.view.put(index(2, 0), (double) m.r2c0());
    this.view.put(index(2, 1), (double) m.r2c1());
    this.view.put(index(2, 2), (double) m.r2c2());
  }

  @Override
  public ByteBuffer byteBuffer()
  {
    return this.buffer;
  }
}
