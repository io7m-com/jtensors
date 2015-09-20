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
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

/**
 * <p>A 3x3 mutable matrix type with double precision elements.</p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

@SuppressWarnings("unchecked") public final class PMatrixM3x3D<T0, T1>
  implements PMatrixDirect3x3DType<T0, T1>
{
  private static final int VIEW_BYTES;
  private static final int VIEW_COLS;
  private static final int VIEW_ELEMENT_SIZE;
  private static final int VIEW_ELEMENTS;
  private static final int VIEW_ROWS;

  static {
    VIEW_ROWS = 3;
    VIEW_COLS = 3;
    VIEW_ELEMENT_SIZE = 8;
    VIEW_ELEMENTS = PMatrixM3x3D.VIEW_ROWS * PMatrixM3x3D.VIEW_COLS;
    VIEW_BYTES = PMatrixM3x3D.VIEW_ELEMENTS * PMatrixM3x3D.VIEW_ELEMENT_SIZE;
  }

  private final ByteBuffer   data;
  private final DoubleBuffer view;

  /**
   * Construct a new identity matrix.
   */

  public PMatrixM3x3D()
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;
    this.view = v;
    this.view.clear();

    MatrixM3x3D.setIdentity(this);
  }

  /**
   * Construct a new copy of the given matrix.
   *
   * @param source The source matrix
   */

  public PMatrixM3x3D(
    final PMatrixReadable3x3DType<T0, T1> source)
  {
    final ByteBuffer b = ByteBuffer.allocateDirect(PMatrixM3x3D.VIEW_BYTES);
    assert b != null;

    final ByteOrder order = ByteOrder.nativeOrder();
    b.order(order);
    this.data = b;

    final DoubleBuffer v = this.data.asDoubleBuffer();
    assert v != null;

    this.view = v;
    this.view.clear();

    MatrixM3x3D.copy(source, this);
  }

  private static int columnCheck(
    final int column)
  {
    if ((column < 0) || (column >= PMatrixM3x3D.VIEW_COLS)) {
      throw new IndexOutOfBoundsException(
        "column must be in the range 0 <= column < " + PMatrixM3x3D.VIEW_COLS);
    }
    return column;
  }

  private static int indexChecked(
    final int row,
    final int column)
  {
    return PMatrixM3x3D.indexUnsafe(
      PMatrixM3x3D.rowCheck(row), PMatrixM3x3D.columnCheck(column));
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
   * @see MatrixM3x3D#determinant(com.io7m.jtensors.MatrixReadable3x3DType)
   */

  public static <T0, T1, MOUT extends PMatrixWritable3x3DType<T1, T0>>
  boolean invert(
    final ContextPM3D context,
    final PMatrixReadable3x3DType<T0, T1> m,
    final MOUT out)
  {
    final double d = MatrixM3x3D.determinant(m);

    if (d == 0.0) {
      return false;
    }

    final double d_inv = 1.0 / d;

    final double orig_r0c0 = m.getR0C0D();
    final double orig_r0c1 = m.getR0C1D();
    final double orig_r0c2 = m.getR0C2D();

    final double orig_r1c0 = m.getR1C0D();
    final double orig_r1c1 = m.getR1C1D();
    final double orig_r1c2 = m.getR1C2D();

    final double orig_r2c0 = m.getR2C0D();
    final double orig_r2c1 = m.getR2C1D();
    final double orig_r2c2 = m.getR2C2D();

    final double r0c0 = (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final double r0c1 = (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final double r0c2 = (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final double r1c0 = (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final double r1c1 = (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final double r1c2 = (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final double r2c0 = (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final double r2c1 = (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final double r2c2 = (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    final PMatrixM3x3D<?, ?> temp = context.m3a;

    temp.setR0C0D(r0c0);
    temp.setR0C1D(r0c1);
    temp.setR0C2D(r0c2);

    temp.setR1C0D(r1c0);
    temp.setR1C1D(r1c1);
    temp.setR1C2D(r1c2);

    temp.setR2C0D(r2c0);
    temp.setR2C1D(r2c1);
    temp.setR2C2D(r2c2);

    MatrixM3x3D.scale(temp, d_inv, out);
    return true;
  }

  /**
   * Calculate the inverse of the matrix {@code m}, saving the resulting matrix
   * to {@code m}. The function returns {@code Some(m)} iff it was possible to
   * invert the matrix, and {@code None} otherwise. It is not possible to invert
   * a matrix that has a determinant of {@code 0}. If the function returns
   * {@code None}, {@code m} is untouched.
   *
   * @param context Preallocated storage
   * @param m       The input matrix
   * @param <T0>    A phantom type parameter
   * @param <T1>    A phantom type parameter
   * @param <MIN>   The precise type of input matrix
   * @param <MOUT>  The precise type of output matrix
   *
   * @return {@code true} iff the matrix was invertible
   *
   * @see MatrixM3x3D#determinant(com.io7m.jtensors.MatrixReadable3x3DType)
   */

  public static <T0, T1, MIN extends PMatrixWritable3x3DType<T0, T1> &
    PMatrixReadable3x3DType<T0, T1>, MOUT extends PMatrixWritable3x3DType<T1,
    T0> & PMatrixReadable3x3DType<T1, T0>> boolean invertInPlace(
    final ContextPM3D context,
    final MIN m)
  {
    final PMatrixM3x3D<T1, T0> mt = (PMatrixM3x3D<T1, T0>) m;
    return (boolean) PMatrixM3x3D.invert(context, m, mt);
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

  public static <T0, T1, T2, MOUT extends PMatrixWritable3x3DType<T0, T2>>
  MOUT multiply(
    final PMatrixReadable3x3DType<T1, T2> m0,
    final PMatrixReadable3x3DType<T0, T1> m1,
    final MOUT out)
  {
    double r0c0 = 0.0;
    r0c0 += m0.getR0C0D() * m1.getR0C0D();
    r0c0 += m0.getR0C1D() * m1.getR1C0D();
    r0c0 += m0.getR0C2D() * m1.getR2C0D();

    double r1c0 = 0.0;
    r1c0 += m0.getR1C0D() * m1.getR0C0D();
    r1c0 += m0.getR1C1D() * m1.getR1C0D();
    r1c0 += m0.getR1C2D() * m1.getR2C0D();

    double r2c0 = 0.0;
    r2c0 += m0.getR2C0D() * m1.getR0C0D();
    r2c0 += m0.getR2C1D() * m1.getR1C0D();
    r2c0 += m0.getR2C2D() * m1.getR2C0D();

    double r0c1 = 0.0;
    r0c1 += m0.getR0C0D() * m1.getR0C1D();
    r0c1 += m0.getR0C1D() * m1.getR1C1D();
    r0c1 += m0.getR0C2D() * m1.getR2C1D();

    double r1c1 = 0.0;
    r1c1 += m0.getR1C0D() * m1.getR0C1D();
    r1c1 += m0.getR1C1D() * m1.getR1C1D();
    r1c1 += m0.getR1C2D() * m1.getR2C1D();

    double r2c1 = 0.0;
    r2c1 += m0.getR2C0D() * m1.getR0C1D();
    r2c1 += m0.getR2C1D() * m1.getR1C1D();
    r2c1 += m0.getR2C2D() * m1.getR2C1D();

    double r0c2 = 0.0;
    r0c2 += m0.getR0C0D() * m1.getR0C2D();
    r0c2 += m0.getR0C1D() * m1.getR1C2D();
    r0c2 += m0.getR0C2D() * m1.getR2C2D();

    double r1c2 = 0.0;
    r1c2 += m0.getR1C0D() * m1.getR0C2D();
    r1c2 += m0.getR1C1D() * m1.getR1C2D();
    r1c2 += m0.getR1C2D() * m1.getR2C2D();

    double r2c2 = 0.0;
    r2c2 += m0.getR2C0D() * m1.getR0C2D();
    r2c2 += m0.getR2C1D() * m1.getR1C2D();
    r2c2 += m0.getR2C2D() * m1.getR2C2D();

    out.setR0C0D(r0c0);
    out.setR0C1D(r0c1);
    out.setR0C2D(r0c2);

    out.setR1C0D(r1c0);
    out.setR1C1D(r1c1);
    out.setR1C2D(r1c2);

    out.setR2C0D(r2c0);
    out.setR2C1D(r2c1);
    out.setR2C2D(r2c2);

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

  public static <T0, T1, V extends PVectorWritable3DType<T1>> V
  multiplyVector3D(
    final ContextPM3D context,
    final PMatrixReadable3x3DType<T0, T1> m,
    final PVectorReadable3DType<T0> v,
    final V out)
  {
    final VectorM3D va = context.v3a;
    final VectorM3D vb = context.v3b;

    vb.copyFrom3D(v);

    m.getRow3DUnsafe(0, va);
    out.setXD(VectorM3D.dotProduct(va, vb));
    m.getRow3DUnsafe(1, va);
    out.setYD(VectorM3D.dotProduct(va, vb));
    m.getRow3DUnsafe(2, va);
    out.setZD(VectorM3D.dotProduct(va, vb));

    return out;
  }

  private static int rowCheck(
    final int row)
  {
    if ((row < 0) || (row >= PMatrixM3x3D.VIEW_ROWS)) {
      throw new IndexOutOfBoundsException(
        "row must be in the range 0 <= row < " + PMatrixM3x3D.VIEW_ROWS);
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

    final PMatrixM3x3D<?, ?> other = (PMatrixM3x3D<?, ?>) obj;
    for (int index = 0; index < PMatrixM3x3D.VIEW_ELEMENTS; ++index) {
      if (other.view.get(index) != this.view.get(index)) {
        return false;
      }
    }

    return true;
  }

  @Override public DoubleBuffer getDirectDoubleBuffer()
  {
    return this.view;
  }

  @Override public <V extends VectorWritable3DType> void getRow3D(
    final int row,
    final V out)
  {
    PMatrixM3x3D.rowCheck(row);
    this.getRow3DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable3DType> void getRow3DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixM3x3D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixM3x3D.indexUnsafe(row, 1));
    final double z = this.view.get(PMatrixM3x3D.indexUnsafe(row, 2));
    out.set3D(x, y, z);
  }

  @Override public double getR0C2D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(0, 2));
  }

  @Override public void setR0C2D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(0, 2), x);
  }

  @Override public double getR1C2D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(1, 2));
  }

  @Override public void setR1C2D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(1, 2), x);
  }

  @Override public double getR2C0D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(2, 0));
  }

  @Override public void setR2C0D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(2, 0), x);
  }

  @Override public double getR2C1D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(2, 1));
  }

  @Override public void setR2C1D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(2, 1), x);
  }

  @Override public double getR2C2D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(2, 2));
  }

  @Override public void setR2C2D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(2, 2), x);
  }

  @Override public double getRowColumnD(
    final int row,
    final int column)
  {
    return this.view.get(PMatrixM3x3D.indexChecked(row, column));
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int r = prime;

    r = HashUtility.accumulateDoubleHash(this.getR0C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C0D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C0D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C1D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C1D(), prime, r);

    r = HashUtility.accumulateDoubleHash(this.getR0C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR1C2D(), prime, r);
    r = HashUtility.accumulateDoubleHash(this.getR2C2D(), prime, r);

    return r;
  }

  @Override public void setRowColumnD(
    final int row,
    final int column,
    final double value)
  {
    this.view.put(PMatrixM3x3D.indexChecked(row, column), value);
  }

  @SuppressWarnings("boxing") @Override public String toString()
  {
    final StringBuilder builder = new StringBuilder(512);
    for (int row = 0; row < PMatrixM3x3D.VIEW_ROWS; ++row) {
      final double c0 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 0));
      final double c1 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 1));
      final double c2 = this.view.get(PMatrixM3x3D.indexUnsafe(row, 2));
      final String s = String.format("[%+.15f %+.15f %+.15f]\n", c0, c1, c2);
      builder.append(s);
    }
    final String r = builder.toString();
    assert r != null;
    return r;
  }

  @Override public <V extends VectorWritable2DType> void getRow2D(
    final int row,
    final V out)
  {
    PMatrixM3x3D.rowCheck(row);
    this.getRow2DUnsafe(row, out);
  }

  @Override public <V extends VectorWritable2DType> void getRow2DUnsafe(
    final int row,
    final V out)
  {
    final double x = this.view.get(PMatrixM3x3D.indexUnsafe(row, 0));
    final double y = this.view.get(PMatrixM3x3D.indexUnsafe(row, 1));
    out.set2D(x, y);
  }

  @Override public double getR0C0D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(0, 0));
  }

  @Override public void setR0C0D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(0, 0), x);
  }

  @Override public double getR1C0D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(1, 0));
  }

  @Override public void setR1C0D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(1, 0), x);
  }

  @Override public double getR0C1D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(0, 1));
  }

  @Override public void setR0C1D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(0, 1), x);
  }

  @Override public double getR1C1D()
  {
    return this.view.get(PMatrixM3x3D.indexUnsafe(1, 1));
  }

  @Override public void setR1C1D(final double x)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(1, 1), x);
  }

  @Override public void setRowWith3D(
    final int row,
    final VectorReadable3DType v)
  {
    PMatrixM3x3D.rowCheck(row);
    this.setRowWith3DUnsafe(row, v);
  }

  @Override public void setRowWith3DUnsafe(
    final int row,
    final VectorReadable3DType v)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixM3x3D.indexUnsafe(row, 1), v.getYD());
    this.view.put(PMatrixM3x3D.indexUnsafe(row, 2), v.getZD());
  }

  @Override public void setRowWith2D(
    final int row,
    final VectorReadable2DType v)
  {
    PMatrixM3x3D.rowCheck(row);
    this.setRowWith2DUnsafe(row, v);
  }

  @Override public void setRowWith2DUnsafe(
    final int row,
    final VectorReadable2DType v)
  {
    this.view.put(PMatrixM3x3D.indexUnsafe(row, 0), v.getXD());
    this.view.put(PMatrixM3x3D.indexUnsafe(row, 1), v.getYD());
  }

  /**
   * <p>The {@code ContextPM3D} type contains the minimum storage required for
   * all of the functions of the {@code PMatrixM3x3D} class.</p>
   *
   * <p>The purpose of the class is to allow applications to allocate all
   * storage ahead of time in order to allow functions in the class to avoid
   * allocating memory (not including stack space) for intermediate
   * calculations. This can reduce garbage collection in speed critical code.
   * </p>
   *
   * <p>The user should allocate one {@code ContextPM3D} value per thread, and
   * then pass this value to matrix functions. Any matrix function that takes a
   * {@code ContextPM3D} value will not generate garbage.</p>
   *
   * @since 7.0.0
   */

  public static class ContextPM3D
  {
    private final PMatrixM3x3D<?, ?> m3a = new PMatrixM3x3D<Object, Object>();
    private final VectorM3D          v3a = new VectorM3D();
    private final VectorM3D          v3b = new VectorM3D();

    /**
     * Construct a new context.
     */

    public ContextPM3D()
    {

    }
  }
}
