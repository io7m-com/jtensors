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

package com.io7m.jtensors.bytebuffered;

import com.io7m.jintegers.CheckedMath;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable2LType;
import com.io7m.jtensors.VectorReadable3LType;
import com.io7m.jtensors.VectorReadable4LType;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>A four-element vector type with {@code long} elements, packed into a
 * {@link ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 */

public final class VectorByteBufferedM4L extends ByteBuffered
  implements VectorByteBuffered4LType
{
  private final ByteBuffer buffer;

  private VectorByteBufferedM4L(
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

  public static VectorByteBuffered4LType newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new VectorByteBufferedM4L(b, new AtomicLong(byte_offset), 0);
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

  public static VectorByteBuffered4LType newVectorFromByteBufferAndBase(
    final ByteBuffer b,
    final AtomicLong base,
    final int offset)
  {
    return new VectorByteBufferedM4L(b, base, offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 8));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public long getWL()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 3);
  }

  @Override public void setWL(final long w)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 3, w);
  }

  @Override public long getZL()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 2);
  }

  @Override public void setZL(final long z)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 2, z);
  }

  @Override public long getXL()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 0);
  }

  @Override public void setXL(final long x)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final long x)
  {
    this.buffer.putLong(VectorByteBufferedM4L.getByteOffsetForIndex(o, i), x);
  }

  private long getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getLong(
      VectorByteBufferedM4L.getByteOffsetForIndex(o, i));
  }

  @Override public long getYL()
  {
    return this.getAtOffsetAndIndex(super.getIndex(), 1);
  }

  @Override public void setYL(final long y)
  {
    this.setAtOffsetAndIndex(super.getIndex(), 1, y);
  }

  @Override public void copyFrom4L(final VectorReadable4LType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXL());
    this.setAtOffsetAndIndex(o, 1, in_v.getYL());
    this.setAtOffsetAndIndex(o, 2, in_v.getZL());
    this.setAtOffsetAndIndex(o, 3, in_v.getWL());
  }

  @Override public void set4L(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
    this.setAtOffsetAndIndex(o, 3, w);
  }

  @Override public void copyFrom3L(final VectorReadable3LType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXL());
    this.setAtOffsetAndIndex(o, 1, in_v.getYL());
    this.setAtOffsetAndIndex(o, 2, in_v.getZL());
  }

  @Override public void set3L(
    final long x,
    final long y,
    final long z)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
    this.setAtOffsetAndIndex(o, 2, z);
  }

  @Override public void copyFrom2L(final VectorReadable2LType in_v)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, in_v.getXL());
    this.setAtOffsetAndIndex(o, 1, in_v.getYL());
  }

  @Override public void set2L(
    final long x,
    final long y)
  {
    final long o = super.getIndex();
    this.setAtOffsetAndIndex(o, 0, x);
    this.setAtOffsetAndIndex(o, 1, y);
  }

  @Override public int hashCode()
  {
    final long prime = 31L;
    long result = 1L;
    result = (prime * result) + this.getWL();
    result = (prime * result) + this.getXL();
    result = (prime * result) + this.getYL();
    result = (prime * result) + this.getZL();
    return (int) result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[VectorByteBufferedM4L ");
    builder.append(this.getXL());
    builder.append(" ");
    builder.append(this.getYL());
    builder.append(" ");
    builder.append(this.getZL());
    builder.append(" ");
    builder.append(this.getWL());
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
    final VectorByteBufferedM4L other = (VectorByteBufferedM4L) obj;
    if (this.getWL() != other.getWL()) {
      return false;
    }
    if (this.getXL() != other.getXL()) {
      return false;
    }
    if (this.getYL() != other.getYL()) {
      return false;
    }
    return this.getZL() == other.getZL();
  }
}
