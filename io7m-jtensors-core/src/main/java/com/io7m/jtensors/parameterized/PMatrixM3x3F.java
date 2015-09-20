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

package com.io7m.jtensors.parameterized;

import com.io7m.jnull.Nullable;
import com.io7m.jtensors.HashUtility;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <p>A 3x3 mutable matrix type with single precision elements.</p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM3x3F<T0, T1>
  implements PMatrixDirect3x3FType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 4;
    VIEW_ELEMENTS = PMatrixM3x3F.VIEW_ROWS * PMatrixM3x3F.VIEW_COLS;
    VIEW_BYTES = PMatrixM3x3F.VIEW_ELEMENTS * PMatrixM3x3F.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer  data;
  private final FloatBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM3x3F()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;
    this.view = v;
    this.view.clear();

    MatrixM3x3F.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public PMatrixM3x3F(
    final PMatrixReadable3x3FType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3F.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final FloatBuffer v = this.data.asFloatBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM3x3F.copy(source, this);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM3x3F.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + PMatrixM3x3F.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM3x3F.indexUnsafe(
      PMatrixM3x3F.rowCheck(row), PMatrixM3x3F.columnCheck(column));
  }

  /**
   * <p> The main function that indexes into the buffer that backs the array.
   * The body of this function decides on how elements are stored. This
   * implementation chooses to store values in column-major format as this
   * allows matrices to be sent directly to OpenGL without conversion. </p> <p>
   * (row * 3) + column, corresponds to row-major storage. (column * 3) + row,
   * corresponds to column-major (OpenGL) storage. </p>
   */

  private static int indexUnsafe(
    final int row,
    final int column)
  {
    return (column * 3) + row;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code out}. The function returns {@code Some(out)} iff it was possible
   * to invert the matrix, and {@code None} otherwise. It is not possible to
   * invert a matrix that has a determinant of {@code 0}. If the function
   * returns {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param out     The output matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3F#determinant(com.io7m.jtensors.MatrixReadable3x3FType)
   */

  public static <T0, T1, MOUT extends PMatrixWritable3x3FType<T1, T0>>
  boolean invert(
    final ContextPM3F context,
    final PMatrixReadable3x3FType<T0, T1> m,
    final MOUT out)
  {
    final double d = MatrixM3x3F.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final float orig_r0c0 = m.getR0C0F();
    final float orig_r0c1 = m.getR0C1F();
    final float orig_r0c2 = m.getR0C2F();

    final float orig_r1c0 = m.getR1C0F();
    final float orig_r1c1 = m.getR1C1F();
    final float orig_r1c2 = m.getR1C2F();

    final float orig_r2c0 = m.getR2C0F();
    final float orig_r2c1 = m.getR2C1F();
    final float orig_r2c2 = m.getR2C2F();

    final float r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final float r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final float r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final float r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final float r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final float r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final float r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final float r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final float r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    final PMatrixM3x3F<?, ?> temp = context.m3a;

    temp.setR0C0F(r0c0);
    temp.setR0C1F(r0c1);
    temp.setR0C2F(r0c2);

    temp.setR1C0F(r1c0);
    temp.setR1C1F(r1c1);
    temp.setR1C2F(r1c2);

    temp.setR2C0F(r2c0);
    temp.setR2C1F(r2c1);
    temp.setR2C2F(r2c2);

    MatrixM3x3F.scale(temp, d_inv, out);
    return true;
  }

  /**
   * Multiply the matrix {@code m0} with the matrix {@code m1}, writing the
   * result to {@code out}.
   *
   * @param m0     The left input vector
   * @param m1     The right input vector
   * @param out    The output vector
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   * @param <T2>   A phantom type parameter
   * @param <MOUT> The precise type of output matrix
   *
   * @return {@code out}
   */

  public static <T0, T1, T2, MOUT extends PMatrixWritable3x3FType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable3x3FType<T1, T2> m0,
    final PMatrixReadable3x3FType<T0, T1> m1,
    final MOUT out)
  {
    float r0c0 = 0.0f;
    r0c0 += m0.getR0C0F() * m1.getR0C0F();
    r0c0 += m0.getR0C1F() * m1.getR1C0F();
    r0c0 += m0.getR0C2F() * m1.getR2C0F();

    float r1c0 = 0.0f;
    r1c0 += m0.getR1C0F() * m1.getR0C0F();
    r1c0 += m0.getR1C1F() * m1.getR1C0F();
    r1c0 += m0.getR1C2F() * m1.getR2C0F();

    float r2c0 = 0.0f;
    r2c0 += m0.getR2C0F() * m1.getR0C0F();
    r2c0 += m0.getR2C1F() * m1.getR1C0F();
    r2c0 += m0.getR2C2F() * m1.getR2C0F();

    float r0c1 = 0.0f;
    r0c1 += m0.getR0C0F() * m1.getR0C1F();
    r0c1 += m0.getR0C1F() * m1.getR1C1F();
    r0c1 += m0.getR0C2F() * m1.getR2C1F();

    float r1c1 = 0.0f;
    r1c1 += m0.getR1C0F() * m1.getR0C1F();
    r1c1 += m0.getR1C1F() * m1.getR1C1F();
    r1c1 += m0.getR1C2F() * m1.getR2C1F();

    float r2c1 = 0.0f;
    r2c1 += m0.getR2C0F() * m1.getR0C1F();
    r2c1 += m0.getR2C1F() * m1.getR1C1F();
    r2c1 += m0.getR2C2F() * m1.getR2C1F();

    float r0c2 = 0.0f;
    r0c2 += m0.getR0C0F() * m1.getR0C2F();
    r0c2 += m0.getR0C1F() * m1.getR1C2F();
    r0c2 += m0.getR0C2F() * m1.getR2C2F();

    float r1c2 = 0.0f;
    r1c2 += m0.getR1C0F() * m1.getR0C2F();
    r1c2 += m0.getR1C1F() * m1.getR1C2F();
    r1c2 += m0.getR1C2F() * m1.getR2C2F();

    float r2c2 = 0.0f;
    r2c2 += m0.getR2C0F() * m1.getR0C2F();
    r2c2 += m0.getR2C1F() * m1.getR1C2F();
    r2c2 += m0.getR2C2F() * m1.getR2C2F();

    out.setR0C0F(r0c0);
    out.setR0C1F(r0c1);
    out.setR0C2F(r0c2);

    out.setR1C0F(r1c0);
    out.setR1C1F(r1c1);
    out.setR1C2F(r1c2);

    out.setR2C0F(r2c0);
    out.setR2C1F(r2c1);
    out.setR2C2F(r2c2);

    return out;
  }

  /**
   * Multiply the matrix {@code m} with the vector {@code v}, writing the
   * resulting vector to {@code out}.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param v       The input vector
   * @param out     The output vector
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <V>     The precise type of writable vector
   *
   * @return {@code out}
   */

  public static <T0, T1, V extends PVectorWritable3FType<T1>> V
  multiplyVector3F(
    final ContextPM3F context,
    final PMatrixReadable3x3FType<T0, T1> m,
    final PVectorReadable3FType<T0> v,
    final V out)
  {
    final VectorM3F va = context.v3a;
    final VectorM3F vb = context.v3b;

    vb.copyFrom3F(v);

    m.getRow3FUnsafe(0, va);
    out.setXF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(1, va);
    out.setYF((float) VectorM3F.dotProduct(va, vb));
    m.getRow3FUnsafe(2, va);
    out.setZF((float) VectorM3F.dotProduct(va, vb));

    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM3x3F.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM3x3F.VIEW_ROWS);
    }
    return row;
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

    final PMatrixM3x3F<?, ?> other = (PMatrixM3x3F<?, ?>) obj;
    for (int index = 0; index < PMatrixM3x3F.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public FloatBuffer getDirectFloatBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable3FType> void getRow3F(
    final int row,
    final V out)
  {
    PMatrixM3x3F.rowCheck(row);
    this.getRow3FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3FType> void getRow3FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(PMatrixM3x3F.indexUnsafe(row, 0));
    final float y = this.view.get(PMatrixM3x3F.indexUnsafe(row, 1));
    final float z = this.view.get(PMatrixM3x3F.indexUnsafe(row, 2));
    out.set3F(x, y, z);
  }

  @Override public float getR0C2F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(0, 2));
  }

  @Override public void setR0C2F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(0, 2), x);
  }

  @Override public float getR1C2F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(1, 2));
  }

  @Override public void setR1C2F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(1, 2), x);
  }

  @Override public float getR2C0F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(2, 0));
  }

  @Override public void setR2C0F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(2, 0), x);
  }

  @Override public float getR2C1F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(2, 1));
  }

  @Override public void setR2C1F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(2, 1), x);
  }

  @Override public float getR2C2F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(2, 2));
  }

  @Override public void setR2C2F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(2, 2), x);
  }

  @Override public float getRowColumnF(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM3x3F.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateFloatHash(this.getR0C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C0F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C0F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C1F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C1F(), prime, r);

    r = HashUtility.accumulateFloatHash(this.getR0C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR1C2F(), prime, r);
    r = HashUtility.accumulateFloatHash(this.getR2C2F(), prime, r);

    return r;
  }

  @Override public void setRowColumnF(
    final int row,
    final int column,
    final float value)
  {
    this.view.put(PMatrixM3x3F.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < PMatrixM3x3F.VIEW_ROWS; ++row) {
      final float c0 = this.view.get(PMatrixM3x3F.indexUnsafe(row, 0));
      final float c1 = this.view.get(PMatrixM3x3F.indexUnsafe(row, 1));
      final float c2 = this.view.get(PMatrixM3x3F.indexUnsafe(row, 2));
      final String s = String.format("[%+.6f %+.6f %+.6f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public <V extends VectorWritable2FType> void getRow2F(
    final int row,
    final V out)
  {
    PMatrixM3x3F.rowCheck(row);
    this.getRow2FUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2FType> void getRow2FUnsafe(
    final int row,
    final V out)
  {
    final float x = this.view.get(PMatrixM3x3F.indexUnsafe(row, 0));
    final float y = this.view.get(PMatrixM3x3F.indexUnsafe(row, 1));
    out.set2F(x, y);
  }

  @Override public float getR0C0F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(0, 0));
  }

  @Override public void setR0C0F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(0, 0), x);
  }

  @Override public float getR1C0F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(1, 0));
  }

  @Override public void setR1C0F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(1, 0), x);
  }

  @Override public float getR0C1F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(0, 1));
  }

  @Override public void setR0C1F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(0, 1), x);
  }

  @Override public float getR1C1F()
  {
    return this.view.get(PMatrixM3x3F.indexUnsafe(1, 1));
  }

  @Override public void setR1C1F(final float x)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(1, 1), x);
  }

  @Override public void setRowWith3F(
    final int row,
    final VectorReadable3FType v)
  {
    PMatrixM3x3F.rowCheck(row);
    this.setRowWith3FUnsafe(row, v);
  }

  @Override public void setRowWith3FUnsafe(
    final int row,
    final VectorReadable3FType v)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixM3x3F.indexUnsafe(row, 1), v.getYF());
    this.view.put(PMatrixM3x3F.indexUnsafe(row, 2), v.getZF());
  }

  @Override public void setRowWith2F(
    final int row,
    final VectorReadable2FType v)
  {
    PMatrixM3x3F.rowCheck(row);
    this.setRowWith2FUnsafe(row, v);
  }

  @Override public void setRowWith2FUnsafe(
    final int row,
    final VectorReadable2FType v)
  {
    this.view.put(PMatrixM3x3F.indexUnsafe(row, 0), v.getXF());
    this.view.put(PMatrixM3x3F.indexUnsafe(row, 1), v.getYF());
  }

  /**
   * <p>The {@code ContextPM3F} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM3x3F} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM3F} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM3F} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM3F
  {
    private final PMatrixM3x3F<?, ?> m3a = new PMatrixM3x3F<Object, Object>();
    private final VectorM3F          v3a = new VectorM3F();
    private final VectorM3F          v3b = new VectorM3F();

    /**
     * Construct a new context.
     */

    public ContextPM3F()
    {

    }
  }

  /**
   * Copy the contents of the matrix {@code input} to the matrix {@code output},
   * completely replacing all elements.
   *
   * @param input  The input vector
   * @param output The output vector
   * @param <M>    The precise type of matrix
   * @param <T0>   A phantom type parameter
   * @param <T1>   A phantom type parameter
   *
   * @return {@code output}
   */

  public static <T0, T1, M extends PMatrixWritable3x3FType<T0, T1>> M copy(
    final PMatrixReadable3x3FType<T0, T1> input,
    final M output)
  {
    MatrixM3x3F.copy(input, output);
    return output;
  }
}
