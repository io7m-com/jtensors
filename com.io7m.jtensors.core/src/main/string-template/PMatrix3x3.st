PMatrix3x3(
  className,
  matrixType,
  matrixComponentType,
  vectorRowType,
  vectorColumnType,
  vectorColumnGenericType,
  scalarRealType,
  dotProductClass,
  hasGeneric,
  opClass,
  matrixConversionType) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> https://www.io7m.com
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

import java.util.Objects;
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
        z, z, z,
        z, z, z,
        z, z, z);

    IDENTITY =
      <matrixType>.of(
        s, z, z,
        z, s, z,
        z, z, s);
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
      (<matrixComponentType>) (m0.r0c0() + m1.r0c0()),
      (<matrixComponentType>) (m0.r0c1() + m1.r0c1()),
      (<matrixComponentType>) (m0.r0c2() + m1.r0c2()),
      (<matrixComponentType>) (m0.r1c0() + m1.r1c0()),
      (<matrixComponentType>) (m0.r1c1() + m1.r1c1()),
      (<matrixComponentType>) (m0.r1c2() + m1.r1c2()),
      (<matrixComponentType>) (m0.r2c0() + m1.r2c0()),
      (<matrixComponentType>) (m0.r2c1() + m1.r2c1()),
      (<matrixComponentType>) (m0.r2c2() + m1.r2c2())
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
    return <opClass>.determinant3x3(
      m.r0c0(), m.r0c1(), m.r0c2(),
      m.r1c0(), m.r1c1(), m.r1c2(),
      m.r2c0(), m.r2c1(), m.r2c2());
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

    if (d == <opClass>.zero()) {
      return Optional.empty();
    }

    final <scalarRealType> d_inv = <opClass>.one() / d;

    final <scalarRealType> orig_r0c0 = m.r0c0();
    final <scalarRealType> orig_r0c1 = m.r0c1();
    final <scalarRealType> orig_r0c2 = m.r0c2();

    final <scalarRealType> orig_r1c0 = m.r1c0();
    final <scalarRealType> orig_r1c1 = m.r1c1();
    final <scalarRealType> orig_r1c2 = m.r1c2();

    final <scalarRealType> orig_r2c0 = m.r2c0();
    final <scalarRealType> orig_r2c1 = m.r2c1();
    final <scalarRealType> orig_r2c2 = m.r2c2();

    final <scalarRealType> r0c0 =
      (orig_r1c1 * orig_r2c2) - (orig_r1c2 * orig_r2c1);
    final <scalarRealType> r0c1 =
      (orig_r0c2 * orig_r2c1) - (orig_r0c1 * orig_r2c2);
    final <scalarRealType> r0c2 =
      (orig_r0c1 * orig_r1c2) - (orig_r0c2 * orig_r1c1);

    final <scalarRealType> r1c0 =
      (orig_r1c2 * orig_r2c0) - (orig_r1c0 * orig_r2c2);
    final <scalarRealType> r1c1 =
      (orig_r0c0 * orig_r2c2) - (orig_r0c2 * orig_r2c0);
    final <scalarRealType> r1c2 =
      (orig_r0c2 * orig_r1c0) - (orig_r0c0 * orig_r1c2);

    final <scalarRealType> r2c0 =
      (orig_r1c0 * orig_r2c1) - (orig_r1c1 * orig_r2c0);
    final <scalarRealType> r2c1 =
      (orig_r0c1 * orig_r2c0) - (orig_r0c0 * orig_r2c1);
    final <scalarRealType> r2c2 =
      (orig_r0c0 * orig_r1c1) - (orig_r0c1 * orig_r1c0);

    return Optional.of(scale(<matrixType>.of(
      (<matrixComponentType>) r0c0, (<matrixComponentType>) r0c1, (<matrixComponentType>) r0c2,
      (<matrixComponentType>) r1c0, (<matrixComponentType>) r1c1, (<matrixComponentType>) r1c2,
      (<matrixComponentType>) r2c0, (<matrixComponentType>) r2c1, (<matrixComponentType>) r2c2), d_inv));
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
    final <scalarRealType> m0_r1c0 = m0.r1c0();
    final <scalarRealType> m0_r1c1 = m0.r1c1();
    final <scalarRealType> m0_r1c2 = m0.r1c2();
    final <scalarRealType> m0_r2c0 = m0.r2c0();
    final <scalarRealType> m0_r2c1 = m0.r2c1();
    final <scalarRealType> m0_r2c2 = m0.r2c2();

    final <scalarRealType> m1_r0c0 = m1.r0c0();
    final <scalarRealType> m1_r0c1 = m1.r0c1();
    final <scalarRealType> m1_r0c2 = m1.r0c2();
    final <scalarRealType> m1_r1c0 = m1.r1c0();
    final <scalarRealType> m1_r1c1 = m1.r1c1();
    final <scalarRealType> m1_r1c2 = m1.r1c2();
    final <scalarRealType> m1_r2c0 = m1.r2c0();
    final <scalarRealType> m1_r2c1 = m1.r2c1();
    final <scalarRealType> m1_r2c2 = m1.r2c2();

    final <scalarRealType> r0c0 =
        (m0_r0c0 * m1_r0c0)
      + (m0_r0c1 * m1_r1c0)
      + (m0_r0c2 * m1_r2c0);

    final <scalarRealType> r1c0 =
        (m0_r1c0 * m1_r0c0)
      + (m0_r1c1 * m1_r1c0)
      + (m0_r1c2 * m1_r2c0);

    final <scalarRealType> r2c0 =
        (m0_r2c0 * m1_r0c0)
      + (m0_r2c1 * m1_r1c0)
      + (m0_r2c2 * m1_r2c0);

    final <scalarRealType> r0c1 =
        (m0_r0c0 * m1_r0c1)
      + (m0_r0c1 * m1_r1c1)
      + (m0_r0c2 * m1_r2c1);

    final <scalarRealType> r1c1 =
        (m0_r1c0 * m1_r0c1)
      + (m0_r1c1 * m1_r1c1)
      + (m0_r1c2 * m1_r2c1);

    final <scalarRealType> r2c1 =
        (m0_r2c0 * m1_r0c1)
      + (m0_r2c1 * m1_r1c1)
      + (m0_r2c2 * m1_r2c1);

    final <scalarRealType> r0c2 =
        (m0_r0c0 * m1_r0c2)
      + (m0_r0c1 * m1_r1c2)
      + (m0_r0c2 * m1_r2c2);

    final <scalarRealType> r1c2 =
        (m0_r1c0 * m1_r0c2)
      + (m0_r1c1 * m1_r1c2)
      + (m0_r1c2 * m1_r2c2);

    final <scalarRealType> r2c2 =
        (m0_r2c0 * m1_r0c2)
      + (m0_r2c1 * m1_r1c2)
      + (m0_r2c2 * m1_r2c2);

    return <matrixType>.of(
      (<matrixComponentType>) r0c0, (<matrixComponentType>) r0c1, (<matrixComponentType>) r0c2,
      (<matrixComponentType>) r1c0, (<matrixComponentType>) r1c1, (<matrixComponentType>) r1c2,
      (<matrixComponentType>) r2c0, (<matrixComponentType>) r2c1, (<matrixComponentType>) r2c2);
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

    final <scalarRealType> x =
      <dotProductClass>.dotProduct3(
        m.r0c0(), m.r0c1(), m.r0c2(),
        vx, vy, vz);
    final <scalarRealType> y =
      <dotProductClass>.dotProduct3(
        m.r1c0(), m.r1c1(), m.r1c2(),
        vx, vy, vz);
    final <scalarRealType> z =
      <dotProductClass>.dotProduct3(
        m.r2c0(), m.r2c1(), m.r2c2(),
        vx, vy, vz);

    return <vectorColumnGenericType>.of(
      (<matrixComponentType>) x,
      (<matrixComponentType>) y,
      (<matrixComponentType>) z);
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

    final <scalarRealType> x =
      <dotProductClass>.dotProduct3(
        m.r0c0(), m.r0c1(), m.r0c2(),
        vx, vy, vz);
    final <scalarRealType> y =
      <dotProductClass>.dotProduct3(
        m.r1c0(), m.r1c1(), m.r1c2(),
        vx, vy, vz);
    final <scalarRealType> z =
      <dotProductClass>.dotProduct3(
        m.r2c0(), m.r2c1(), m.r2c2(),
        vx, vy, vz);

    return <vectorColumnType>.of(
      (<matrixComponentType>) x,
      (<matrixComponentType>) y,
      (<matrixComponentType>) z);
  }

  /**
   * Construct a matrix from the column vectors {@code (c0, c1, c2)}.
   * @return A constructed matrix
   * @param c0 The column 0
   * @param c1 The column 1
   * @param c2 The column 2
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
    final <vectorColumnType> c2)
  {
    return <matrixType>.of(
      c0.x(), c1.x(), c2.x(),
      c0.y(), c1.y(), c2.y(),
      c0.z(), c1.z(), c2.z());
  }

  /**
   * Construct a matrix from the row vectors {@code (r0, r1, r2)}.
   * @return A constructed matrix
   * @param r0 The row 0
   * @param r1 The row 1
   * @param r2 The row 2
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
    final <vectorColumnType> r2)
  {
    return <matrixType>.of(
      r0.x(), r0.y(), r0.z(),
      r1.x(), r1.y(), r1.z(),
      r2.x(), r2.y(), r2.z());
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
    final <matrixComponentType> o = (<matrixComponentType>) <opClass>.zero();
    final <matrixComponentType> s = (<matrixComponentType>) <opClass>.one();

    return <matrixType>.of(
      (<matrixComponentType>) x, o, o,
      o, (<matrixComponentType>) y, o,
      o, o, (<matrixComponentType>) z);
  }

  /**
   * Construct a matrix that will rotate by {@code angle} radians around the axis {@code (x, y, z)}.
   * @return A constructed matrix
   * @param angle The angle in radians
   * @param axis_x The X axis value
   * @param axis_y The Y axis value
   * @param axis_z The Z axis value
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
  ofAxisAngle(
    final <scalarRealType> axis_x,
    final <scalarRealType> axis_y,
    final <scalarRealType> axis_z,
    final <scalarRealType> angle)
  {
    final <scalarRealType> s = <opClass>.sine(angle);
    final <scalarRealType> c = <opClass>.cosine(angle);
    final <scalarRealType> t = <opClass>.one() - c;

    final <scalarRealType> tx_sq = t * (axis_x * axis_x);
    final <scalarRealType> ty_sq = t * (axis_y * axis_y);
    final <scalarRealType> tz_sq = t * (axis_z * axis_z);

    final <scalarRealType> txy = t * (axis_x * axis_y);
    final <scalarRealType> txz = t * (axis_x * axis_z);
    final <scalarRealType> tyz = t * (axis_y * axis_z);

    final <scalarRealType> sx = s * axis_x;
    final <scalarRealType> sy = s * axis_y;
    final <scalarRealType> sz = s * axis_z;

    final <scalarRealType> r0c0 = tx_sq + c;
    final <scalarRealType> r0c1 = txy - sz;
    final <scalarRealType> r0c2 = txz + sy;

    final <scalarRealType> r1c0 = txy + sz;
    final <scalarRealType> r1c1 = ty_sq + c;
    final <scalarRealType> r1c2 = tyz - sx;

    final <scalarRealType> r2c0 = txz - sy;
    final <scalarRealType> r2c1 = tyz + sx;
    final <scalarRealType> r2c2 = tz_sq + c;

    return <matrixType>.of(
      (<matrixComponentType>) r0c0, (<matrixComponentType>) r0c1, (<matrixComponentType>) r0c2,
      (<matrixComponentType>) r1c0, (<matrixComponentType>) r1c1, (<matrixComponentType>) r1c2,
      (<matrixComponentType>) r2c0, (<matrixComponentType>) r2c1, (<matrixComponentType>) r2c2);
  }

  /**
   * @return Row 0 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorRowType> row0(final <matrixType>\<A, B> m)
  <else>                  <vectorRowType> row0(final <matrixType> m)
  <endif>
  {
    return <vectorRowType>.of(m.r0c0(), m.r0c1(), m.r0c2());
  }

  /**
   * @return Row 1 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorRowType> row1(final <matrixType>\<A, B> m)
  <else>                  <vectorRowType> row1(final <matrixType> m)
  <endif>
  {
    return <vectorRowType>.of(m.r1c0(), m.r1c1(), m.r1c2());
  }

  /**
   * @return Row 2 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorRowType> row2(final <matrixType>\<A, B> m)
  <else>                  <vectorRowType> row2(final <matrixType> m)
  <endif>
  {
    return <vectorRowType>.of(m.r2c0(), m.r2c1(), m.r2c2());
  }

  /**
   * @return Column 0 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorColumnType> column0(final <matrixType>\<A, B> m)
  <else>                  <vectorColumnType> column0(final <matrixType> m)
  <endif>
  {
    return <vectorColumnType>.of(m.r0c0(), m.r1c0(), m.r2c0());
  }

  /**
   * @return Column 1 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorColumnType> column1(final <matrixType>\<A, B> m)
  <else>                  <vectorColumnType> column1(final <matrixType> m)
  <endif>
  {
    return <vectorColumnType>.of(m.r0c1(), m.r1c1(), m.r2c1());
  }

  /**
   * @return Column 2 of the matrix
   * @param m The matrix
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a source coordinate system)
   * <endif>
   */

  public static
  <if(hasGeneric)>\<A, B> <vectorColumnType> column2(final <matrixType>\<A, B> m)
  <else>                  <vectorColumnType> column2(final <matrixType> m)
  <endif>
  {
    return <vectorColumnType>.of(m.r0c2(), m.r1c2(), m.r2c2());
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
      (<matrixComponentType>) (m.r0c0() * r),
      (<matrixComponentType>) (m.r0c1() * r),
      (<matrixComponentType>) (m.r0c2() * r),
      (<matrixComponentType>) (m.r1c0() * r),
      (<matrixComponentType>) (m.r1c1() * r),
      (<matrixComponentType>) (m.r1c2() * r),
      (<matrixComponentType>) (m.r2c0() * r),
      (<matrixComponentType>) (m.r2c1() * r),
      (<matrixComponentType>) (m.r2c2() * r)
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
      (<matrixComponentType>) (m0.r0c0() - m1.r0c0()),
      (<matrixComponentType>) (m0.r0c1() - m1.r0c1()),
      (<matrixComponentType>) (m0.r0c2() - m1.r0c2()),
      (<matrixComponentType>) (m0.r1c0() - m1.r1c0()),
      (<matrixComponentType>) (m0.r1c1() - m1.r1c1()),
      (<matrixComponentType>) (m0.r1c2() - m1.r1c2()),
      (<matrixComponentType>) (m0.r2c0() - m1.r2c0()),
      (<matrixComponentType>) (m0.r2c1() - m1.r2c1()),
      (<matrixComponentType>) (m0.r2c2() - m1.r2c2())
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
    return m.r0c0() + (m.r1c1() + m.r2c2());
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
      m.r0c0(), m.r1c0(), m.r2c0(),
      m.r0c1(), m.r1c1(), m.r2c1(),
      m.r0c2(), m.r1c2(), m.r2c2());
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn0(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <else>
  <matrixType>
  withColumn0(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <endif>
  {
    return <matrixType>.of(
      r0, m.r0c1(), m.r0c2(),
      r1, m.r1c1(), m.r1c2(),
      r2, m.r2c1(), m.r2c2());
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn1(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <else>
  <matrixType>
  withColumn1(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <endif>
  {
    return <matrixType>.of(
      m.r0c0(), r0, m.r0c2(),
      m.r1c0(), r1, m.r1c2(),
      m.r2c0(), r2, m.r2c2());
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn2(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <else>
  <matrixType>
  withColumn2(
    final <matrixType> m,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <endif>
  {
    return <matrixType>.of(
      m.r0c0(), m.r0c1(), r0,
      m.r1c0(), m.r1c1(), r1,
      m.r2c0(), m.r2c1(), r2);
  }

  /**
   * Set the column {@code column} of {@code m} to {@code (r0, r1, r2)}.
   * @return A matrix with the given row
   * @param m The matrix
   * @param column The column index in the range {@code [0, 2]}
   * <if(hasGeneric)>
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * <endif>
   * @param r0 The value of row 0 in the column
   * @param r1 The value of row 1 in the column
   * @param r2 The value of row 2 in the column
   */

  public static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withColumn(
    final <matrixType>\<A, B> m,
    final int column,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <else>
  <matrixType>
  withColumn(
    final <matrixType> m,
    final int column,
    final <matrixComponentType> r0,
    final <matrixComponentType> r1,
    final <matrixComponentType> r2)
  <endif>
  {
    switch (column) {
      case 0:
      {
        return withColumn0(m, r0, r1, r2);
      }
      case 1:
      {
        return withColumn1(m, r0, r1, r2);
      }
      case 2:
      {
        return withColumn2(m, r0, r1, r2);
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Column %d must be in the range [0, 2]", Integer.valueOf(column)));
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
    final <matrixComponentType> c2)
  <else>
  <matrixType>
  withRow0(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <endif>
  {
    return <matrixType>.of(
      c0,       c1,       c2,
      m.r1c0(), m.r1c1(), m.r1c2(),
      m.r2c0(), m.r2c1(), m.r2c2());
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow1(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <else>
  <matrixType>
  withRow1(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <endif>
  {
    return <matrixType>.of(
      m.r0c0(), m.r0c1(), m.r0c2(),
      c0,       c1,       c2,
      m.r2c0(), m.r2c1(), m.r2c2());
  }

  private static
  <if(hasGeneric)>
  \<A, B> <matrixType>\<A, B>
  withRow2(
    final <matrixType>\<A, B> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <else>
  <matrixType>
  withRow2(
    final <matrixType> m,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <endif>
  {
    return <matrixType>.of(
      m.r0c0(), m.r0c1(), m.r0c2(),
      m.r1c0(), m.r1c1(), m.r1c2(),
      c0,       c1,       c2);
  }

  /**
   * Set the row {@code row} of {@code m} to {@code (c0, c1, c2)}.
   * @return A matrix with the given row
   * @param m The matrix
   * @param row The row index in the range {@code [0, 2]}
   * @param c0 The value of column 0 in the row
   * @param c1 The value of column 1 in the row
   * @param c2 The value of column 2 in the row
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
    final <matrixComponentType> c2)
  <else>
  <matrixType>
  withRow(
    final <matrixType> m,
    final int row,
    final <matrixComponentType> c0,
    final <matrixComponentType> c1,
    final <matrixComponentType> c2)
  <endif>
  {
    switch (row) {
      case 0:
      {
        return withRow0(m, c0, c1, c2);
      }
      case 1:
      {
        return withRow1(m, c0, c1, c2);
      }
      case 2:
      {
        return withRow2(m, c0, c1, c2);
      }
      default: {
        throw new IndexOutOfBoundsException(String.format(
          "Row %d must be in the range [0, 2]", Integer.valueOf(row)));
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

  <if(hasGeneric)>
  /**
   * @param m The input matrix
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @return A matrix equal to {@code m} but without type parameters
   */

  public static \<A, B> <matrixConversionType> toUnparameterized(
    final <matrixType>\<A, B> m)
  {
    return <matrixConversionType>.of(
      m.r0c0(), m.r0c1(), m.r0c2(),
      m.r1c0(), m.r1c1(), m.r1c2(),
      m.r2c0(), m.r2c1(), m.r2c2());
  }

  /**
   * @param m The input matrix
   * @param \<A> A phantom type parameter (possibly representing a source coordinate system)
   * @param \<B> A phantom type parameter (possibly representing a target coordinate system)
   * @return A matrix equal to {@code m} with type parameters
   */

  public static \<A, B> <matrixType>\<A, B> toParameterized(
    final <matrixConversionType> m)
  {
    return <matrixType>.of(
      m.r0c0(), m.r0c1(), m.r0c2(),
      m.r1c0(), m.r1c1(), m.r1c2(),
      m.r2c0(), m.r2c1(), m.r2c2());
  }
  <endif>

}
>>
