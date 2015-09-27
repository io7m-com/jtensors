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
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.bytebuffered.ByteBufferRanges;
import com.io7m.jtensors.parameterized.PVector2FType;
import com.io7m.jtensors.parameterized.PVectorReadable2FType;

import java.nio.ByteBuffer;

/**
 * <p>A two-element vector type with {@code float} elements, packed into a
 * {@link ByteBuffer}.</p>
 *
 * <p> Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization. </p>
 *
 * @param <T> A phantom type parameter
 */

public final class PVectorByteBufferedM2F<T> implements PVector2FType<T>
{
  private final ByteBuffer buffer;
  private final long       offset;

  private PVectorByteBufferedM2F(
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

  public static <T> PVector2FType<T> newVectorFromByteBuffer(
    final ByteBuffer b,
    final long byte_offset)
  {
    return new PVectorByteBufferedM2F<T>(b, byte_offset);
  }

  private static int getByteOffsetForIndex(
    final long base,
    final int index)
  {
    final long b = CheckedMath.add(base, (long) (index * 4));
    return (int) ByteBufferRanges.checkByteOffset(b);
  }

  @Override public float getXF()
  {
    return this.getAtOffsetAndIndex(this.offset, 0);
  }

  @Override public void setXF(final float x)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
  }

  private void setAtOffsetAndIndex(
    final long o,
    final int i,
    final float x)
  {
    this.buffer.putFloat(PVectorByteBufferedM2F.getByteOffsetForIndex(o, i), x);
  }

  private float getAtOffsetAndIndex(
    final long o,
    final int i)
  {
    return this.buffer.getFloat(
      PVectorByteBufferedM2F.getByteOffsetForIndex(o, i));
  }

  @Override public float getYF()
  {
    return this.getAtOffsetAndIndex(this.offset, 1);
  }

  @Override public void setYF(final float y)
  {
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public void copyFrom2F(final VectorReadable2FType in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXF());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYF());
  }

  @Override public void set2F(
    final float x,
    final float y)
  {
    this.setAtOffsetAndIndex(this.offset, 0, x);
    this.setAtOffsetAndIndex(this.offset, 1, y);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Float.floatToIntBits(this.getXF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    temp = Float.floatToIntBits(this.getYF());
    result = (prime * result) + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(128);
    builder.append("[PVectorByteBufferedM2F ");
    builder.append(this.getXF());
    builder.append(" ");
    builder.append(this.getYF());
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
    final PVectorByteBufferedM2F<?> other = (PVectorByteBufferedM2F<?>) obj;
    if (Float.floatToIntBits(this.getXF())
        != Float.floatToIntBits(other.getXF())) {
      return false;
    }
    return Float.floatToIntBits(this.getYF()) == Float.floatToIntBits(
      other.getYF());
  }

  @Override public void copyFromTyped2F(final PVectorReadable2FType<T> in_v)
  {
    this.setAtOffsetAndIndex(this.offset, 0, in_v.getXF());
    this.setAtOffsetAndIndex(this.offset, 1, in_v.getYF());
  }
}
