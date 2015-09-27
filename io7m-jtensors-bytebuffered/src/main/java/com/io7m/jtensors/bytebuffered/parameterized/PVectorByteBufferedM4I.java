/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.bytebuffered.parameterized;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorReadable4IType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;
import com.io7m.jtensors.parameterized.PVectorReadable2IType;
import com.io7m.jtensors.parameterized.PVectorReadable3IType;
import com.io7m.jtensors.parameterized.PVectorReadable4IType;

import java.nio.ByteBuffer;

/**
 * <p>A four-element vector type with {@code int} elements, packed into a {@link
 * ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedM4I<T> implements PVectorByteBuffered4IType<T>
{
  private final ByteBuffer buffer;
  private long offset;

  private PVectorByteBufferedM4I(
    final ByteBuffer in_buffer,
    final long in_offset)
  {
    this.buffer = NullCheck.notNull(in_buffer);
    this.offset = in_offset;
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

  public static <T> PVectorByteBuffered4IType<T> newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PVectorByteBufferedM4I<T>(b, byte_offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 8));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public int getWI()
  {
    return this.getAtOffsetAndIndex(this.offset, 3);
  }

  @Override public void setWI(final int w)
  {
    this.setAtOffsetAndIndex(this.offset, 3, w);
  }

  @Override public int getZI()
  {
    return this.getAtOffsetAndIndex(this.offset, 2);
  }

  @Override public void setZI(final int z)
  {
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public int getXI()
  {
    return this.getAtOffsetAndIndex(this.offset, 0);
  }

  @Override public void setXI(final int x)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final int x)
  {
    this.buffer.putInt(PVectorByteBufferedM4I.getByteOffsetForIndex(o, i), x);
  }

  private int getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getInt(
      PVectorByteBufferedM4I.getByteOffsetForIndex(o, i));
  }

  @Override public int getYI()
  {
    return this.getAtOffsetAndIndex(this.offset, 1);
  }

  @Override public void setYI(final int y)
  {
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public void copyFrom4I(final VectorReadable4IType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZI());
    this.setAtOffsetAndIndex(this.offset, 3, in_v.getWI());
  }

  @Override public void set4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
    this.setAtOffsetAndIndex(this.offset, 2, z);
    this.setAtOffsetAndIndex(this.offset, 3, w);
  }

  @Override public void copyFrom3I(final VectorReadable3IType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZI());
  }

  @Override public void set3I(
    final int x,
    final int y,
    final int z)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
    this.setAtOffsetAndIndex(this.offset, 2, z);
  }

  @Override public void copyFrom2I(final VectorReadable2IType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
  }

  @Override public void set2I(
    final int x,
    final int y)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + this.getWI();
    result = (prime * result) + this.getXI();
    result = (prime * result) + this.getYI();
    result = (prime * result) + this.getZI();
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[PVectorByteBufferedM4I ");
    builder.append(this.getXI());
    builder.append(" ");
    builder.append(this.getYI());
    builder.append(" ");
    builder.append(this.getZI());
    builder.append(" ");
    builder.append(this.getWI());
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
    final PVectorByteBufferedM4I<?> other = (PVectorByteBufferedM4I<?>) obj;
    if (this.getWI() != other.getWI()) {
      return false;
    }
    if (this.getXI() != other.getXI()) {
      return false;
    }
    if (this.getYI() != other.getYI()) {
      return false;
    }
    return this.getZI() == other.getZI();
  }

  @Override public void copyFromTyped4I(final PVectorReadable4IType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZI());
    this.setAtOffsetAndIndex(this.offset, 3, in_v.getWI());
  }

  @Override public void copyFromTyped3I(final PVectorReadable3IType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
    this.setAtOffsetAndIndex(this.offset, 2, in_v.getZI());
  }

  @Override public void copyFromTyped2I(final PVectorReadable2IType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXI());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYI());
  }

  @Override public long getByteOffset()
  {
    return this.offset;
  }

  @Override public void setByteOffset(final long b)
  {
    this.offset = ByteBufferRanges.checkByteOffset(b);
  }
}
