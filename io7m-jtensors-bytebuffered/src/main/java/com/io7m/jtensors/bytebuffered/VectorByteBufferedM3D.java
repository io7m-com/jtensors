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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A three-element vector type with {@code double} elements, packed into a
 * {@link ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorByteBufferedM3D extends ByteBuffered implements VectorByteBuffered3DType
{
  private final ByteBuffer buffer;

  private VectorByteBufferedM3D(
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
   * @param b           The byte buffer
   * @param byte_offset A byte offset
   *
   * @return A new buffered vector
   */

  public static VectorByteBuffered3DType newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new VectorByteBufferedM3D(b, new AtomicLong(byte_offset), 0);
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
   * @param b      The byte buffer
   * @param base   The base address
   * @param offset A constant offset
   *
   * @return A new buffered vector
   */

  public static VectorByteBuffered3DType newVectorFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new VectorByteBufferedM3D(b, base, offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 8));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public double getZD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 2);
  }

  @Override public void setZD(final double z)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 2, z);
  }

  @Override public double getXD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 0);
  }

  @Override public void setXD(final double x)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final double x)
  {
    this.buffer.putDouble(getByteOffsetForIndex(o, i), x);
  }

  private double getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getDouble(
      getByteOffsetForIndex(o, i));
  }

  @Override public double getYD()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 1);
  }

  @Override public void setYD(final double y)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 1, y);
  }

  @Override public void copyFrom3D(final VectorReadable3DType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
    this.setAtOffsetAndIndex(o, 2, in_v.getZD());
  }

  @Override public void set3D(
    final double x,
    final double y,
    final double z)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
  }

  @Override public void copyFrom2D(final VectorReadable2DType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXD());
    this.setAtOffsetAndIndex(o, 1, in_v.getYD());
  }

  @Override public void set2D(
    final double x,
    final double y)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
  }

  @Override public int hashCode()
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

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[VectorByteBufferedM3D ");
    builder.append(this.getXD());
    builder.append(" ");
    builder.append(this.getYD());
    builder.append(" ");
    builder.append(this.getZD());
    builder.append("]");
    final String r = builder.toString();
    return NullCheck.notNull(r);
  }

  @Override public boolean equals(
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
    final VectorByteBufferedM3D other = (VectorByteBufferedM3D) obj;
    if (Double.doubleToLongBits(this.getXD())
        != Double.doubleToLongBits(other.getXD())) {
      return false;
    }
    if (Double.doubleToLongBits(this.getYD())
        != Double.doubleToLongBits(other.getYD())) {
      return false;
    }
    return Double.doubleToLongBits(this.getZD()) == Double.doubleToLongBits(
      other.getZD());
  }
}
