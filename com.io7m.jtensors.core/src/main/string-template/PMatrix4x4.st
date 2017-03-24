PMatrix4x4(
  className,
  matrixType,
  matrixComponentType,
  scalarRealType,
  opClass) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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

package com.io7m.jtensors.core.parameterized.matrices;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <matrixType>} values.\</p>
 *
 * @param \<T> A phantom type parameter
 * @since 8.0.0
 */

public final class <className>\<T>
{
  private static final <matrixType>\<?> ZERO;
  private static final <matrixType>\<?> IDENTITY;

  static {
    final <matrixComponentType> z = (<matrixComponentType>) <opClass>.zero();
    final <matrixComponentType> s = (<matrixComponentType>) <opClass>.one();

    ZERO =
      <matrixType>.of(
        z, z, z, z,
        z, z, z, z,
        z, z, z, z,
        z, z, z, z);

    IDENTITY =
      <matrixType>.of(
        s, z, z, z,
        z, s, z, z,
        z, z, s, z,
        z, z, z, s);
  }

  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Add the matrices {@code m0} and {@code m1}.
   * @return {@code m0 + m1}
   * @param m0 The left matrix
   * @param m1 The right matrix
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> add(
    final <matrixType>\<T> m0,
    final <matrixType>\<T> m1)
  {
    return <matrixType>.of(
      <opClass>.add(m0.r0c0(), m1.r0c0()),
      <opClass>.add(m0.r0c1(), m1.r0c1()),
      <opClass>.add(m0.r0c2(), m1.r0c2()),
      <opClass>.add(m0.r0c3(), m1.r0c3()),
      <opClass>.add(m0.r1c0(), m1.r1c0()),
      <opClass>.add(m0.r1c1(), m1.r1c1()),
      <opClass>.add(m0.r1c2(), m1.r1c2()),
      <opClass>.add(m0.r1c3(), m1.r1c3()),
      <opClass>.add(m0.r2c0(), m1.r2c0()),
      <opClass>.add(m0.r2c1(), m1.r2c1()),
      <opClass>.add(m0.r2c2(), m1.r2c2()),
      <opClass>.add(m0.r2c3(), m1.r2c3()),
      <opClass>.add(m0.r3c0(), m1.r3c0()),
      <opClass>.add(m0.r3c1(), m1.r3c1()),
      <opClass>.add(m0.r3c2(), m1.r3c2()),
      <opClass>.add(m0.r3c3(), m1.r3c3())
    );
  }

  /**
   * Scale the matrix {@code m} by {@code r}.
   * @return {@code m * r}
   * @param m The matrix
   * @param r The scale factor
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> scale(
    final <matrixType>\<T> m,
    final <scalarRealType> r)
  {
    return <matrixType>.of(
      <opClass>.multiply(m.r0c0(), r),
      <opClass>.multiply(m.r0c1(), r),
      <opClass>.multiply(m.r0c2(), r),
      <opClass>.multiply(m.r0c3(), r),
      <opClass>.multiply(m.r1c0(), r),
      <opClass>.multiply(m.r1c1(), r),
      <opClass>.multiply(m.r1c2(), r),
      <opClass>.multiply(m.r1c3(), r),
      <opClass>.multiply(m.r2c0(), r),
      <opClass>.multiply(m.r2c1(), r),
      <opClass>.multiply(m.r2c2(), r),
      <opClass>.multiply(m.r2c3(), r),
      <opClass>.multiply(m.r3c0(), r),
      <opClass>.multiply(m.r3c1(), r),
      <opClass>.multiply(m.r3c2(), r),
      <opClass>.multiply(m.r3c3(), r)
    );
  }

  /**
   * Subtract the matrices {@code m0} and {@code m1}.
   * @return {@code m0 - m1}
   * @param m0 The left matrix
   * @param m1 The right matrix
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> subtract(
    final <matrixType>\<T> m0,
    final <matrixType>\<T> m1)
  {
    return <matrixType>.of(
      <opClass>.subtract(m0.r0c0(), m1.r0c0()),
      <opClass>.subtract(m0.r0c1(), m1.r0c1()),
      <opClass>.subtract(m0.r0c2(), m1.r0c2()),
      <opClass>.subtract(m0.r0c3(), m1.r0c3()),
      <opClass>.subtract(m0.r1c0(), m1.r1c0()),
      <opClass>.subtract(m0.r1c1(), m1.r1c1()),
      <opClass>.subtract(m0.r1c2(), m1.r1c2()),
      <opClass>.subtract(m0.r1c3(), m1.r1c3()),
      <opClass>.subtract(m0.r2c0(), m1.r2c0()),
      <opClass>.subtract(m0.r2c1(), m1.r2c1()),
      <opClass>.subtract(m0.r2c2(), m1.r2c2()),
      <opClass>.subtract(m0.r2c3(), m1.r2c3()),
      <opClass>.subtract(m0.r3c0(), m1.r3c0()),
      <opClass>.subtract(m0.r3c1(), m1.r3c1()),
      <opClass>.subtract(m0.r3c2(), m1.r3c2()),
      <opClass>.subtract(m0.r3c3(), m1.r3c3())
    );
  }

  private static \<T> <matrixType>\<T> withColumn0(
    final <matrixType>\<T> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  {
    return <matrixType>.of(
      r0,
      m.r0c1(),
      m.r0c2(),
      m.r0c3(),

      r1,
      m.r1c1(),
      m.r1c2(),
      m.r1c3(),

      r2,
      m.r2c1(),
      m.r2c2(),
      m.r2c3(),

      r3,
      m.r3c1(),
      m.r3c2(),
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withColumn1(
    final <matrixType>\<T> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  {
    return <matrixType>.of(
      m.r0c0(),
      r0,
      m.r0c2(),
      m.r0c3(),

      m.r1c0(),
      r1,
      m.r1c2(),
      m.r1c3(),

      m.r2c0(),
      r2,
      m.r2c2(),
      m.r2c3(),

      m.r3c0(),
      r3,
      m.r3c2(),
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withColumn2(
    final <matrixType>\<T> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r0c1(),
      r0,
      m.r0c3(),

      m.r1c0(),
      m.r1c1(),
      r1,
      m.r1c3(),

      m.r2c0(),
      m.r2c1(),
      r2,
      m.r2c3(),

      m.r3c0(),
      m.r3c1(),
      r3,
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withColumn3(
    final <matrixType>\<T> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r0c1(),
      m.r0c2(),
      r0,

      m.r1c0(),
      m.r1c1(),
      m.r1c2(),
      r1,

      m.r2c0(),
      m.r2c1(),
      m.r2c2(),
      r2,

      m.r3c0(),
      m.r3c1(),
      m.r3c2(),
      r3
    );
  }

  /**
   * Set the column {@code column} of {@code m} to {@code (r0, r1, r2, r3)}.
   * @return A matrix with the given row
   * @param m The matrix
   * @param column The column index in the range {@code [0, 3]}
   * @param \<T> A phantom type parameter
   * @param r0 The value of row 0 in the column
   * @param r1 The value of row 1 in the column
   * @param r2 The value of row 2 in the column
   * @param r3 The value of row 3 in the column
   */

  public static \<T> <matrixType>\<T> withColumn(
    final <matrixType>\<T> m,
    final int column,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  {
    switch (column) {
      case 0:
      {
        return withColumn0(m, r0, r1, r2, r3);
      }
      case 1:
      {
        return withColumn1(m, r0, r1, r2, r3);
      }
      case 2:
      {
        return withColumn2(m, r0, r1, r2, r3);
      }
      case 3:
      {
        return withColumn3(m, r0, r1, r2, r3);
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Column %d must be in the range [0, 3]", Integer.valueOf(column)));
      }
    }
  }

  private static \<T> <matrixType>\<T> withRow0(
    final <matrixType>\<T> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  {
    return <matrixType>.of(
      c0,
      c1,
      c2,
      c3,
      m.r1c0(),
      m.r1c1(),
      m.r1c2(),
      m.r1c3(),
      m.r2c0(),
      m.r2c1(),
      m.r2c2(),
      m.r2c3(),
      m.r3c0(),
      m.r3c1(),
      m.r3c2(),
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withRow1(
    final <matrixType>\<T> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r0c1(),
      m.r0c2(),
      m.r0c3(),
      c0,
      c1,
      c2,
      c3,
      m.r2c0(),
      m.r2c1(),
      m.r2c2(),
      m.r2c3(),
      m.r3c0(),
      m.r3c1(),
      m.r3c2(),
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withRow2(
    final <matrixType>\<T> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r0c1(),
      m.r0c2(),
      m.r0c3(),
      m.r1c0(),
      m.r1c1(),
      m.r1c2(),
      m.r1c3(),
      c0,
      c1,
      c2,
      c3,
      m.r3c0(),
      m.r3c1(),
      m.r3c2(),
      m.r3c3()
    );
  }

  private static \<T> <matrixType>\<T> withRow3(
    final <matrixType>\<T> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r0c1(),
      m.r0c2(),
      m.r0c3(),
      m.r1c0(),
      m.r1c1(),
      m.r1c2(),
      m.r1c3(),
      m.r2c0(),
      m.r2c1(),
      m.r2c2(),
      m.r2c3(),
      c0,
      c1,
      c2,
      c3
    );
  }

  /**
   * Set the row {@code row} of {@code m} to {@code (c0, c1, c2, c3)}.
   * @return A matrix with the given row
   * @param m The matrix
   * @param row The row index in the range {@code [0, 3]}
   * @param c0 The value of column 0 in the row
   * @param c1 The value of column 1 in the row
   * @param c2 The value of column 2 in the row
   * @param c3 The value of column 3 in the row
   * @param \<T> A phantom type parameter
   */

  public static \<T> <matrixType>\<T> withRow(
    final <matrixType>\<T> m,
    final int row,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  {
    switch (row) {
      case 0:
      {
        return withRow0(m, c0, c1, c2, c3);
      }
      case 1:
      {
        return withRow1(m, c0, c1, c2, c3);
      }
      case 2:
      {
        return withRow2(m, c0, c1, c2, c3);
      }
      case 3:
      {
        return withRow3(m, c0, c1, c2, c3);
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Row %d must be in the range [0, 3]", Integer.valueOf(row)));
      }
    }
  }

  /**
   * The zero matrix.
   *
   * @return A matrix with all zero components
   * @param \<T> A phantom type parameter
   */

  @SuppressWarnings("unchecked")
  public static \<T> <matrixType>\<T> zero()
  {
    return (<matrixType>\<T>) ZERO;
  }

  /**
   * The identity matrix.
   *
   * @return A matrix with all diagonal components set to 1 and all other components set to 0.
   * @param \<T> A phantom type parameter
   */

  @SuppressWarnings("unchecked")
  public static \<T> <matrixType>\<T> identity()
  {
    return (<matrixType>\<T>) IDENTITY;
  }
}
>>
