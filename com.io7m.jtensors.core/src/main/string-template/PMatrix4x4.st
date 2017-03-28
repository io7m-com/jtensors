PMatrix4x4(
  className,
  matrixType,
  matrixComponentType,
  vectorRowType,
  vectorColumnType,
  vectorColumnGenericType,
  scalarRealType,
  dotProductClass,
  hasGeneric,
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

<if(hasGeneric)>
package com.io7m.jtensors.core.parameterized.matrices;
<else>
package com.io7m.jtensors.core.unparameterized.matrices;
<endif>

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;
import com.io7m.junreachable.UnimplementedCodeException;

import java.util.Optional;

/**
 * \<p>Functions over {@link <matrixType>} values.\</p>
 * \<p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * \</p>
 * @since 8.0.0
 */

public final class <className>
{
  private static final <matrixType><if(hasGeneric)>\<?, ?><endif> ZERO;
  private static final <matrixType><if(hasGeneric)>\<?, ?><endif> IDENTITY;

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
   *
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<C> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<D> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<E> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<F> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B, C, D, E, F> <matrixType>\<E, F>
  add(
    final <matrixType>\<A, B> m0,
    final <matrixType>\<C, D> m1)
  <else>
  <matrixType>
  add(
    final <matrixType> m0,
    final <matrixType> m1)
  <endif>
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
   * Calculate the determinant of the matrix {@code m}.
   * @return The determinant of {@code m}
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <scalarRealType>
  determinant(
    final <matrixType>\<A, B> m)
  <else>
  <scalarRealType>
  determinant(
    final <matrixType> m)
  <endif>
  {
    return <opClass>.determinant4x4(
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
      m.r3c0(),
      m.r3c1(),
      m.r3c2(),
      m.r3c3()
    );
  }

  /**
   * Calculate the inverse of the matrix {@code m}.
   * @return The inverse of {@code m}, or nothing if no inverse exists
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> Optional\<<matrixType>\<B, A\>>
  invert(
    final <matrixType>\<A, B> m)
  <else>
  Optional\<<matrixType\>>
  invert(
    final <matrixType> m)
  <endif>
  {
    final <scalarRealType> d = determinant(m);

    if (<opClass>.compare(d, <opClass>.zero()) == 0) {
      return Optional.empty();
    }

    final <scalarRealType> d_inv = <opClass>.divide(<opClass>.one(), d);

    /*
     * This code is based on the Laplace Expansion theorem. Essentially, the
     * inverse of the matrix is calculated by taking the determinants of 3x3
     * sub-matrices of the original matrix. The sub-matrices are created by
     * removing a specific row and column to leave 9 (possibly non-adjacent)
     * cells, which are then placed in a 3x3 matrix.
     *
     * This implementation was derived from the paper "The Laplace Expansion
     * Theorem: Computing the Determinants and Inverses of Matrices" by David
     * Eberly.
     */

    final <scalarRealType> r0c0;
    final <scalarRealType> r0c1;
    final <scalarRealType> r0c2;
    final <scalarRealType> r0c3;

    final <scalarRealType> r1c0;
    final <scalarRealType> r1c1;
    final <scalarRealType> r1c2;
    final <scalarRealType> r1c3;

    final <scalarRealType> r2c0;
    final <scalarRealType> r2c1;
    final <scalarRealType> r2c2;
    final <scalarRealType> r2c3;

    final <scalarRealType> r3c0;
    final <scalarRealType> r3c1;
    final <scalarRealType> r3c2;
    final <scalarRealType> r3c3;

    {
      // Sub-matrix obtained by removing m[0, 0]
      // 1 = (-1) ^ (0 + 0)

      r0c0 = <opClass>.determinant3x3(
        m.r1c1(),
        m.r1c2(),
        m.r1c3(),
        m.r2c1(),
        m.r2c2(),
        m.r2c3(),
        m.r3c1(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[0, 1]
      // -1 = (-1) ^ (0 + 1)

      r0c1 = -<opClass>.determinant3x3(
        m.r1c0(),
        m.r1c2(),
        m.r1c3(),
        m.r2c0(),
        m.r2c2(),
        m.r2c3(),
        m.r3c0(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[0, 2]
      // 1 = (-1) ^ (0 + 2)

      r0c2 = <opClass>.determinant3x3(
        m.r1c0(),
        m.r1c1(),
        m.r1c3(),
        m.r2c0(),
        m.r2c1(),
        m.r2c3(),
        m.r3c0(),
        m.r3c1(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[0, 3]
      // -1 = (-1) ^ (0 + 3)

      r0c3 = -<opClass>.determinant3x3(
        m.r1c0(),
        m.r1c1(),
        m.r1c2(),
        m.r2c0(),
        m.r2c1(),
        m.r2c2(),
        m.r3c0(),
        m.r3c1(),
        m.r3c2()
      );
    }

    {
      // Sub-matrix obtained by removing m[1, 0]
      // -1 = (-1) ^ (1 + 0)

      r1c0 = -<opClass>.determinant3x3(
        m.r0c1(),
        m.r0c2(),
        m.r0c3(),
        m.r2c1(),
        m.r2c2(),
        m.r2c3(),
        m.r3c1(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[1, 1]
      // 1 = (-1) ^ (1 + 1)

      r1c1 = <opClass>.determinant3x3(
        m.r0c0(),
        m.r0c2(),
        m.r0c3(),
        m.r2c0(),
        m.r2c2(),
        m.r2c3(),
        m.r3c0(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[1, 2]
      // -1 = (-1) ^ (1 + 2)

      r1c2 = -<opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c3(),
        m.r2c0(),
        m.r2c1(),
        m.r2c3(),
        m.r3c0(),
        m.r3c1(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[1, 3]
      // 1 = (-1) ^ (1 + 3)

      r1c3 = <opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c2(),
        m.r2c0(),
        m.r2c1(),
        m.r2c2(),
        m.r3c0(),
        m.r3c1(),
        m.r3c2()
      );
    }

    {
      // Sub-matrix obtained by removing m[2, 0]
      // 1 = (-1) ^ (2 + 0)

      r2c0 = <opClass>.determinant3x3(
        m.r0c1(),
        m.r0c2(),
        m.r0c3(),
        m.r1c1(),
        m.r1c2(),
        m.r1c3(),
        m.r3c1(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[2, 1]
      // -1 = (-1) ^ (2 + 1)

      r2c1 = -<opClass>.determinant3x3(
        m.r0c0(),
        m.r0c2(),
        m.r0c3(),
        m.r1c0(),
        m.r1c2(),
        m.r1c3(),
        m.r3c0(),
        m.r3c2(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[2, 2]
      // 1 = (-1) ^ (2 + 2)

      r2c2 = <opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c3(),
        m.r1c0(),
        m.r1c1(),
        m.r1c3(),
        m.r3c0(),
        m.r3c1(),
        m.r3c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[2, 3]
      // -1 = (-1) ^ (2 + 3)

      r2c3 = -<opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c2(),
        m.r1c0(),
        m.r1c1(),
        m.r1c2(),
        m.r3c0(),
        m.r3c1(),
        m.r3c2()
      );
    }

    {
      // Sub-matrix obtained by removing m[3, 0]
      // -1 = (-1) ^ (3 + 0)

      r3c0 = -<opClass>.determinant3x3(
        m.r0c1(),
        m.r0c2(),
        m.r0c3(),
        m.r1c1(),
        m.r1c2(),
        m.r1c3(),
        m.r2c1(),
        m.r2c2(),
        m.r2c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[3, 1]
      // 1 = (-1) ^ (3 + 1)

      r3c1 = <opClass>.determinant3x3(
        m.r0c0(),
        m.r0c2(),
        m.r0c3(),
        m.r1c0(),
        m.r1c2(),
        m.r1c3(),
        m.r2c0(),
        m.r2c2(),
        m.r2c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[3, 2]
      // -1 = (-1) ^ (3 + 2)

      r3c2 = -<opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c3(),
        m.r1c0(),
        m.r1c1(),
        m.r1c3(),
        m.r2c0(),
        m.r2c1(),
        m.r2c3()
      );
    }

    {
      // Sub-matrix obtained by removing m[3, 3]
      // 1 = (-1) ^ (3 + 3)

      r3c3 = <opClass>.determinant3x3(
        m.r0c0(),
        m.r0c1(),
        m.r0c2(),
        m.r1c0(),
        m.r1c1(),
        m.r1c2(),
        m.r2c0(),
        m.r2c1(),
        m.r2c2()
      );
    }

    return Optional.of(transpose(<matrixType>.of(
      r0c0 * d_inv,
      r0c1 * d_inv,
      r0c2 * d_inv,
      r0c3 * d_inv,
      r1c0 * d_inv,
      r1c1 * d_inv,
      r1c2 * d_inv,
      r1c3 * d_inv,
      r2c0 * d_inv,
      r2c1 * d_inv,
      r2c2 * d_inv,
      r2c3 * d_inv,
      r3c0 * d_inv,
      r3c1 * d_inv,
      r3c2 * d_inv,
      r3c3 * d_inv
    )));
  }

  /**
   * Multiply the matrices {@code m0} and {@code m1}.
   * @return {@code m0 * m1}
   * @param m0 The left matrix
   * @param m1 The right matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<C> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B, C> <matrixType>\<A, C>
  multiply(
    final <matrixType>\<B, C> m0,
    final <matrixType>\<A, B> m1)
  <else>
  <matrixType>
  multiply(
    final <matrixType> m0,
    final <matrixType> m1)
  <endif>
  {
    final <scalarRealType> m0_r0c0 = m0.r0c0();
    final <scalarRealType> m0_r0c1 = m0.r0c1();
    final <scalarRealType> m0_r0c2 = m0.r0c2();
    final <scalarRealType> m0_r0c3 = m0.r0c3();
    final <scalarRealType> m0_r1c0 = m0.r1c0();
    final <scalarRealType> m0_r1c1 = m0.r1c1();
    final <scalarRealType> m0_r1c2 = m0.r1c2();
    final <scalarRealType> m0_r1c3 = m0.r1c3();
    final <scalarRealType> m0_r2c0 = m0.r2c0();
    final <scalarRealType> m0_r2c1 = m0.r2c1();
    final <scalarRealType> m0_r2c2 = m0.r2c2();
    final <scalarRealType> m0_r2c3 = m0.r2c3();
    final <scalarRealType> m0_r3c0 = m0.r3c0();
    final <scalarRealType> m0_r3c1 = m0.r3c1();
    final <scalarRealType> m0_r3c2 = m0.r3c2();
    final <scalarRealType> m0_r3c3 = m0.r3c3();

    final <scalarRealType> m1_r0c0 = m1.r0c0();
    final <scalarRealType> m1_r0c1 = m1.r0c1();
    final <scalarRealType> m1_r0c2 = m1.r0c2();
    final <scalarRealType> m1_r0c3 = m1.r0c3();
    final <scalarRealType> m1_r1c0 = m1.r1c0();
    final <scalarRealType> m1_r1c1 = m1.r1c1();
    final <scalarRealType> m1_r1c2 = m1.r1c2();
    final <scalarRealType> m1_r1c3 = m1.r1c3();
    final <scalarRealType> m1_r2c0 = m1.r2c0();
    final <scalarRealType> m1_r2c1 = m1.r2c1();
    final <scalarRealType> m1_r2c2 = m1.r2c2();
    final <scalarRealType> m1_r2c3 = m1.r2c3();
    final <scalarRealType> m1_r3c0 = m1.r3c0();
    final <scalarRealType> m1_r3c1 = m1.r3c1();
    final <scalarRealType> m1_r3c2 = m1.r3c2();
    final <scalarRealType> m1_r3c3 = m1.r3c3();

    final <scalarRealType> r0c0 =
        <opClass>.multiply(m0_r0c0, m1_r0c0)
      + <opClass>.multiply(m0_r0c1, m1_r1c0)
      + <opClass>.multiply(m0_r0c2, m1_r2c0)
      + <opClass>.multiply(m0_r0c3, m1_r3c0);

    final <scalarRealType> r1c0 =
        <opClass>.multiply(m0_r1c0, m1_r0c0)
      + <opClass>.multiply(m0_r1c1, m1_r1c0)
      + <opClass>.multiply(m0_r1c2, m1_r2c0)
      + <opClass>.multiply(m0_r1c3, m1_r3c0);

    final <scalarRealType> r2c0 =
        <opClass>.multiply(m0_r2c0, m1_r0c0)
      + <opClass>.multiply(m0_r2c1, m1_r1c0)
      + <opClass>.multiply(m0_r2c2, m1_r2c0)
      + <opClass>.multiply(m0_r2c3, m1_r3c0);

    final <scalarRealType> r3c0 =
        <opClass>.multiply(m0_r3c0, m1_r0c0)
      + <opClass>.multiply(m0_r3c1, m1_r1c0)
      + <opClass>.multiply(m0_r3c2, m1_r2c0)
      + <opClass>.multiply(m0_r3c3, m1_r3c0);

    final <scalarRealType> r0c1 =
        <opClass>.multiply(m0_r0c0, m1_r0c1)
      + <opClass>.multiply(m0_r0c1, m1_r1c1)
      + <opClass>.multiply(m0_r0c2, m1_r2c1)
      + <opClass>.multiply(m0_r0c3, m1_r3c1);

    final <scalarRealType> r1c1 =
        <opClass>.multiply(m0_r1c0, m1_r0c1)
      + <opClass>.multiply(m0_r1c1, m1_r1c1)
      + <opClass>.multiply(m0_r1c2, m1_r2c1)
      + <opClass>.multiply(m0_r1c3, m1_r3c1);

    final <scalarRealType> r2c1 =
        <opClass>.multiply(m0_r2c0, m1_r0c1)
      + <opClass>.multiply(m0_r2c1, m1_r1c1)
      + <opClass>.multiply(m0_r2c2, m1_r2c1)
      + <opClass>.multiply(m0_r2c3, m1_r3c1);

    final <scalarRealType> r3c1 =
        <opClass>.multiply(m0_r3c0, m1_r0c1)
      + <opClass>.multiply(m0_r3c1, m1_r1c1)
      + <opClass>.multiply(m0_r3c2, m1_r2c1)
      + <opClass>.multiply(m0_r3c3, m1_r3c1);

    final <scalarRealType> r0c2 =
        <opClass>.multiply(m0_r0c0, m1_r0c2)
      + <opClass>.multiply(m0_r0c1, m1_r1c2)
      + <opClass>.multiply(m0_r0c2, m1_r2c2)
      + <opClass>.multiply(m0_r0c3, m1_r3c2);

    final <scalarRealType> r1c2 =
        <opClass>.multiply(m0_r1c0, m1_r0c2)
      + <opClass>.multiply(m0_r1c1, m1_r1c2)
      + <opClass>.multiply(m0_r1c2, m1_r2c2)
      + <opClass>.multiply(m0_r1c3, m1_r3c2);

    final <scalarRealType> r2c2 =
        <opClass>.multiply(m0_r2c0, m1_r0c2)
      + <opClass>.multiply(m0_r2c1, m1_r1c2)
      + <opClass>.multiply(m0_r2c2, m1_r2c2)
      + <opClass>.multiply(m0_r2c3, m1_r3c2);

    final <scalarRealType> r3c2 =
        <opClass>.multiply(m0_r3c0, m1_r0c2)
      + <opClass>.multiply(m0_r3c1, m1_r1c2)
      + <opClass>.multiply(m0_r3c2, m1_r2c2)
      + <opClass>.multiply(m0_r3c3, m1_r3c2);

    final <scalarRealType> r0c3 =
        <opClass>.multiply(m0_r0c0, m1_r0c3)
      + <opClass>.multiply(m0_r0c1, m1_r1c3)
      + <opClass>.multiply(m0_r0c2, m1_r2c3)
      + <opClass>.multiply(m0_r0c3, m1_r3c3);

    final <scalarRealType> r1c3 =
        <opClass>.multiply(m0_r1c0, m1_r0c3)
      + <opClass>.multiply(m0_r1c1, m1_r1c3)
      + <opClass>.multiply(m0_r1c2, m1_r2c3)
      + <opClass>.multiply(m0_r1c3, m1_r3c3);

    final <scalarRealType> r2c3 =
        <opClass>.multiply(m0_r2c0, m1_r0c3)
      + <opClass>.multiply(m0_r2c1, m1_r1c3)
      + <opClass>.multiply(m0_r2c2, m1_r2c3)
      + <opClass>.multiply(m0_r2c3, m1_r3c3);

    final <scalarRealType> r3c3 =
        <opClass>.multiply(m0_r3c0, m1_r0c3)
      + <opClass>.multiply(m0_r3c1, m1_r1c3)
      + <opClass>.multiply(m0_r3c2, m1_r2c3)
      + <opClass>.multiply(m0_r3c3, m1_r3c3);

    return <matrixType>.of(
      r0c0,
      r0c1,
      r0c2,
      r0c3,
      r1c0,
      r1c1,
      r1c2,
      r1c3,
      r2c0,
      r2c1,
      r2c2,
      r2c3,
      r3c0,
      r3c1,
      r3c2,
      r3c3
    );
  }

  <if(hasGeneric)>
  /**
   * \<p>Multiply the vector {@code v} by the matrix {@code m}.\</p>
   * \<p>This is \<i>post multiplication\</i>.\</p>
   * @return {@code m * v}
   * @param m The matrix
   * @param v The vector
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   */

  public static \<A, B> <vectorColumnGenericType>\<B>
  multiplyVectorPost(
    final <matrixType>\<A, B> m,
    final <vectorColumnGenericType>\<A> v)
  {
    final <scalarRealType> vx = v.x();
    final <scalarRealType> vy = v.y();
    final <scalarRealType> vz = v.z();
    final <scalarRealType> vw = v.w();

    final <scalarRealType> x =
      <dotProductClass>.dotProduct4(
        m.r0c0(), m.r0c1(), m.r0c2(), m.r0c3(),
        vx, vy, vz, vw);
    final <scalarRealType> y =
      <dotProductClass>.dotProduct4(
        m.r1c0(), m.r1c1(), m.r1c2(), m.r1c3(),
        vx, vy, vz, vw);
    final <scalarRealType> z =
      <dotProductClass>.dotProduct4(
        m.r2c0(), m.r2c1(), m.r2c2(), m.r2c3(),
        vx, vy, vz, vw);
    final <scalarRealType> w =
      <dotProductClass>.dotProduct4(
        m.r3c0(), m.r3c1(), m.r3c2(), m.r3c3(),
        vx, vy, vz, vw);

    return <vectorColumnGenericType>.of(x, y, z, w);
  }
  <endif>

  /**
   * \<p>Multiply the vector {@code v} by the matrix {@code m}.\</p>
   * \<p>This is \<i>post multiplication\</i>.\</p>
   * @return {@code m * v}
   * @param m The matrix
   * @param v The vector
   */

  public static <vectorColumnType> multiplyVectorPost(
    final <matrixType> m,
    final <vectorColumnType> v)
  {
    final <scalarRealType> vx = v.x();
    final <scalarRealType> vy = v.y();
    final <scalarRealType> vz = v.z();
    final <scalarRealType> vw = v.w();

    final <scalarRealType> x =
      <dotProductClass>.dotProduct4(
        m.r0c0(), m.r0c1(), m.r0c2(), m.r0c3(),
        vx, vy, vz, vw);
    final <scalarRealType> y =
      <dotProductClass>.dotProduct4(
        m.r1c0(), m.r1c1(), m.r1c2(), m.r1c3(),
        vx, vy, vz, vw);
    final <scalarRealType> z =
      <dotProductClass>.dotProduct4(
        m.r2c0(), m.r2c1(), m.r2c2(), m.r2c3(),
        vx, vy, vz, vw);
    final <scalarRealType> w =
      <dotProductClass>.dotProduct4(
        m.r3c0(), m.r3c1(), m.r3c2(), m.r3c3(),
        vx, vy, vz, vw);

    return <vectorColumnType>.of(x, y, z, w);
  }

  /**
   * Construct a matrix from the column vectors {@code (c0, c1, c2, c3)}.
   * @return A constructed matrix
   * @param c0 The column 0
   * @param c1 The column 1
   * @param c2 The column 2
   * @param c3 The column 3
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  <else>
  <matrixType>
  <endif>
  ofColumns(
    final <vectorColumnType> c0,
    final <vectorColumnType> c1,
    final <vectorColumnType> c2,
    final <vectorColumnType> c3)
  {
    return <matrixType>.of(
      c0.x(),
      c1.x(),
      c2.x(),
      c3.x(),
      c0.y(),
      c1.y(),
      c2.y(),
      c3.y(),
      c0.z(),
      c1.z(),
      c2.z(),
      c3.z(),
      c0.w(),
      c1.w(),
      c2.w(),
      c3.w()
    );
  }

  /**
   * Construct a matrix from the row vectors {@code (r0, r1, r2, r3)}.
   * @return A constructed matrix
   * @param r0 The row 0
   * @param r1 The row 1
   * @param r2 The row 2
   * @param r3 The row 3
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  <else>
  <matrixType>
  <endif>
  ofRows(
    final <vectorColumnType> r0,
    final <vectorColumnType> r1,
    final <vectorColumnType> r2,
    final <vectorColumnType> r3)
  {
    return <matrixType>.of(
      r0.x(),
      r0.y(),
      r0.z(),
      r0.w(),
      r1.x(),
      r1.y(),
      r1.z(),
      r1.w(),
      r2.x(),
      r2.y(),
      r2.z(),
      r2.w(),
      r3.x(),
      r3.y(),
      r3.z(),
      r3.w()
    );
  }

  /**
   * Construct a matrix that will scale by {@code (x, y, z)}.
   * @return A constructed matrix
   * @param x The X scaling value
   * @param y The Y scaling value
   * @param z The Z scaling value
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  <else>
  <matrixType>
  <endif>
  ofScale(
    final <scalarRealType> x,
    final <scalarRealType> y,
    final <scalarRealType> z)
  {
    final <scalarRealType> o = <opClass>.zero();
    final <scalarRealType> s = <opClass>.one();

    return <matrixType>.of(
      x, o, o, o,
      o, y, o, o,
      o, o, z, o,
      o, o, o, s
    );
  }

  /**
   * Construct a matrix that will translate by {@code (x, y, z)}.
   * @return A constructed matrix
   * @param x The X scaling value
   * @param y The Y scaling value
   * @param z The Z scaling value
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  <else>
  <matrixType>
  <endif>
  ofTranslation(
    final <scalarRealType> x,
    final <scalarRealType> y,
    final <scalarRealType> z)
  {
    final <scalarRealType> o = <opClass>.zero();
    final <scalarRealType> s = <opClass>.one();

    return <matrixType>.of(
      s, o, o, x,
      o, s, o, y,
      o, o, s, z,
      o, o, o, s
    );
  }

  /**
   * Scale the matrix {@code m} by {@code r}.
   * @return {@code m * r}
   * @param m The matrix
   * @param r The scale factor
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<C> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<D> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B, C, D> <matrixType>\<C, D>
  scale(
    final <matrixType>\<A, B> m,
    final <scalarRealType> r)
  <else>
  <matrixType>
  scale(
    final <matrixType> m,
    final <scalarRealType> r)
  <endif>
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
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<C> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<D> A phantom type parameter (possibly representing a target coordinate system)
   * @param \<E> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<F> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B, C, D, E, F> <matrixType>\<E, F>
  subtract(
    final <matrixType>\<A, B> m0,
    final <matrixType>\<C, D> m1)
  <else>
  <matrixType>
  subtract(
    final <matrixType> m0,
    final <matrixType> m1)
  <endif>
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

  /**
   * Return the trace of the matrix {@code m}. The trace is defined as the sum
   * of the diagonal elements of the matrix.
   *
   * @param m The matrix
   * @return The trace of the matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <scalarRealType>
  trace(
    final <matrixType>\<A, B> m)
  <else>
  <scalarRealType>
  trace(
    final <matrixType> m)
  <endif>
  {
    return <opClass>.add(m.r0c0(), <opClass>.add(m.r1c1(), <opClass>.add(m.r2c2(), m.r3c3())));
  }

  /**
   * Calculate the transpose of the matrix {@code m}.
   * @return The transpose of {@code m}
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  transpose(
    final <matrixType>\<A, B> m)
  <else>
  <matrixType>
  transpose(
    final <matrixType> m)
  <endif>
  {
    return <matrixType>.of(
      m.r0c0(),
      m.r1c0(),
      m.r2c0(),
      m.r3c0(),
      m.r0c1(),
      m.r1c1(),
      m.r2c1(),
      m.r3c1(),
      m.r0c2(),
      m.r1c2(),
      m.r2c2(),
      m.r3c2(),
      m.r0c3(),
      m.r1c3(),
      m.r2c3(),
      m.r3c3()
    );
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn0(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <else>
  <matrixType>
  withColumn0(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn1(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <else>
  <matrixType>
  withColumn1(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn2(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <else>
  <matrixType>
  withColumn2(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn3(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <else>
  <matrixType>
  withColumn3(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <endif>
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
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   * @param r0 The value of row 0 in the column
   * @param r1 The value of row 1 in the column
   * @param r2 The value of row 2 in the column
   * @param r3 The value of row 3 in the column
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn(
    final <matrixType>\<A, B> m,
    final int column,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <else>
  <matrixType>
  withColumn(
    final <matrixType> m,
    final int column,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2,
    final <matrixComponentType> r3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow0(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <else>
  <matrixType>
  withRow0(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow1(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <else>
  <matrixType>
  withRow1(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow2(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <else>
  <matrixType>
  withRow2(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <endif>
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

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow3(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <else>
  <matrixType>
  withRow3(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <endif>
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
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow(
    final <matrixType>\<A, B> m,
    final int row,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <else>
  <matrixType>
  withRow(
    final <matrixType> m,
    final int row,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2,
    final <matrixComponentType> c3)
  <endif>
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
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  <if(hasGeneric)>
  @SuppressWarnings("unchecked")
  public static \<A, B> <matrixType>\<A, B> zero()
  {
    return (<matrixType>\<A, B>) ZERO;
  }
  <else>
  public static <matrixType> zero()
  {
    return ZERO;
  }
  <endif>

  /**
   * The identity matrix.
   *
   * @return A matrix with all diagonal components set to 1 and all other components set to 0.
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   */

  <if(hasGeneric)>
  @SuppressWarnings("unchecked")
  public static \<A, B> <matrixType>\<A, B> identity()
  {
    return (<matrixType>\<A, B>) IDENTITY;
  }
  <else>
  @SuppressWarnings("unchecked")
  public static <matrixType> identity()
  {
    return IDENTITY;
  }
  <endif>

}
>>
