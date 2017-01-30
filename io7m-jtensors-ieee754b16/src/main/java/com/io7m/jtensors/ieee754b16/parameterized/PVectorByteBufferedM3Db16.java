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

package com.io7m.jtensors.ieee754b16.parameterized;

import com.io7m.ieee754b16.Binary16;
import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;
import com.io7m.jtensors.bytebuffered.ByteBuffered;
import com.io7m.jtensors.parameterized.PVectorReadable2DType;
import com.io7m.jtensors.parameterized.PVectorReadable3DType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A three-element vector type with {@code double} elements, packed into a
 * {@link ByteBuffer}. Elements are stored as <b>IEEE 754</b> {@code binary16}
 * values.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedM3Db16<T> extends ByteBuffered
  implements PVectorByteBuffered3Db16Type<T>
{
  private final ByteBuffer buffer;

  private PVectorByteBufferedM3Db16(
    final ByteBuffer in_buffer,
    final AtomicLong in_base,
    final int in_offset)
  {
    super(in_base, in_offset);
    this.buffer = NullCheck.notNull(in_buffer);
  }

  /**
   * <p>Return a new vector that is backed by whatever data is at byte offset
   * {@code byte_offset} in the byte buffer {@code}.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param <T>         A phantom type parameter
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered vector
   */

  public static <T> PVectorByteBuffered3Db16Type<T> newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PVectorByteBufferedM3Db16<T>(b, new AtomicLong(byte_offset), 0);
  }

  /**
   * <p>Return a new vector that is backed by the given byte buffer {@code b}
   * </p>
   *
   * <p>The data for the instance will be taken from the data at the current
   * value of {@code base.get() + offset}, each time a field is requested or
   * set.</p>
   *
   * <p>No initialization of the data is performed.</p>
   *
   * @param <T>    A phantom type parameter
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered vector
   */

  public static <T> PVectorByteBuffered3Db16Type<T>
  newVectorFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new PVectorByteBufferedM3Db16<T>(b, base, offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 2));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override
  public double getZD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 2);
  }

  @Override
  public void setZD(final double z)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 2, z);
  }

  @Override
  public double getXD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 0);
  }

  @Override
  public void setXD(final double x)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final double x)
  {
    final char k = Binary16.packDouble(x);
    this.buffer.putChar(
      getByteOffsetForIndex(o, i), k);
  }

  private double getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    final char k = this.buffer.getChar(
      getByteOffsetForIndex(o, i));
    return Binary16.unpackDouble(k);
  }

  @Override
  public double getYD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 1);
  }

  @Override
  public void setYD(final double y)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 1, y);
  }

  @Override
  public void copyFrom3D(final VectorReadable3DType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
    this.setAtOffsetAndIndex(o, 2, in_v.getZD());
  }

  @Override
  public void set3D(
    final double x,
    final double y,
    final double z)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
  }

  @Override
  public void copyFrom2D(final VectorReadable2DType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
  }

  @Override
  public void set2D(
    final double x,
    final double y)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(this.getXD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.getYD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.getZD());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[PVectorByteBufferedM3Db16 ");
    builder.append(this.getXD());
    builder.append(" ");
    builder.append(this.getYD());
    builder.append(" ");
    builder.append(this.getZD());
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
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
    final PVectorByteBufferedM3Db16<?> other =
      (PVectorByteBufferedM3Db16<?>) obj;
    final double tx = this.getXD();
    final double ty = this.getYD();
    final double tz = this.getZD();
    final double ox = other.getXD();
    final double oy = other.getYD();
    final double oz = other.getZD();
    return Double.doubleToLongBits(tx) == Double.doubleToLongBits(ox)
      && Double.doubleToLongBits(ty) == Double.doubleToLongBits(oy)
      && Double.doubleToLongBits(tz) == Double.doubleToLongBits(oz);
  }

  @Override
  public void copyFromTyped3D(final PVectorReadable3DType<T> in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
    this.setAtOffsetAndIndex(o, 2, in_v.getZD());
  }

  @Override
  public void copyFromTyped2D(final PVectorReadable2DType<T> in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
  }
}
