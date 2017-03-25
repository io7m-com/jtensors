PMatrix4x4Test(
  className,
  matrixType,
  matrixComponentType,
  generatorType,
  scalarRealType,
  testOpClass,
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

package com.io7m.jtensors.tests.core.parameterized.matrices;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;
import org.junit.Rule;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import com.io7m.jtensors.tests.TestUtilities;

/**
 * \<p>Unit tests for {@link <matrixType>} values.\</p>
 *
 * @since 8.0.0
 */

public final class <className>
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  @Test
  @PercentagePassing
  public void testEqualsHashCodeToString()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = gen.next();
    final <matrixType>\<Object> m2 = <matrixType>.builder().from(m0).build();

    Assert.assertEquals(m0, m0);
    Assert.assertEquals(m0, m2);
    Assert.assertEquals(m0.hashCode(), m0.hashCode());
    Assert.assertEquals(m0.hashCode(), m2.hashCode());
    Assert.assertEquals(m0.toString(), m0.toString());
    Assert.assertEquals(m0.toString(), m2.toString());
    Assert.assertNotEquals(m0, null);
    Assert.assertNotEquals(m0, Integer.valueOf(23));

    Assert.assertNotEquals(m0, m1);
    Assert.assertNotEquals(m0.hashCode(), m1.hashCode());
    Assert.assertNotEquals(m0.toString(), m1.toString());
  }

  /**
   * determinant(identity()) == 1.0
   */

  @Test
  public void testDeterminantIdentity()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), <opClass>.determinant(m));
  }

  /**
   * determinant(zero()) == 0.0
   */

  @Test
  public void testDeterminantZero()
  {
    final <matrixType>\<Object> m = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), <opClass>.determinant(m));
  }

  @Test
  public void testDeterminantScale()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    final <matrixType>\<Object> mr =
      <matrixType>.builder()
      .from(m).setR0c0(<testOpClass>.constant("2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), <opClass>.determinant(mr));
  }

  @Test
  public void testDeterminantScaleNegative()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    final <matrixType>\<Object> mr =
      <matrixType>.builder()
      .from(m).setR0c0(<testOpClass>.constant("-2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("-2"), <opClass>.determinant(mr));
  }

  /**
   * ∀ m s. s⁴ * determinant(m) == determinant(scale(m, s))
   */

  @Test
  @PercentagePassing
  public void testDeterminantScaleExponent()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    <testOpClass>.checkEquals(
      <testOpClass>.multiply(<testOpClass>.pow(s, <testOpClass>.constant("4")), <opClass>.determinant(m)),
      <opClass>.determinant(<opClass>.scale(m, s)));
  }

  /**
   * ∀ m. determinant(m) == determinant(transpose(m))
   */

  @Test
  @PercentagePassing
  public void testDeterminantTranspose()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mt = <opClass>.transpose(m);

    <testOpClass>.checkEquals(<opClass>.determinant(m), <opClass>.determinant(mt));
  }

  /**
   * ∀ m. m == transpose(transpose(m))
   */

  @Test
  @PercentagePassing
  public void testTransposeTranspose()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = <opClass>.transpose(<opClass>.transpose(m0));

    Assert.assertEquals(m0, m1);
  }

  /**
   * ∀ m n. transpose(add(m, n)) == add(transpose(m), transpose(n))
   */

  @Test
  @PercentagePassing
  public void testTransposeAdd()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = gen.next();

    final <matrixType>\<Object> mr0 =
      <opClass>.transpose(<opClass>.add(m0, m1));
    final <matrixType>\<Object> mr1 =
      <opClass>.add(<opClass>.transpose(m0), <opClass>.transpose(m1));

    Assert.assertEquals(mr0, mr1);
  }

  /**
   * trace(identity()) == 4.0
   */

  @Test
  public void testTraceIdentity()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("4"), <opClass>.trace(m));
  }

  /**
   * ∀ m n. trace(add(m, n)) == trace(m) + trace(n)
   */

  @Test
  @PercentagePassing
  public void testTraceAdd()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.createNormal();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = gen.next();

    final <scalarRealType> mr0 =
      <opClass>.trace(<opClass>.add(m0, m1));
    final <scalarRealType> mr1 =
      <testOpClass>.add(<opClass>.trace(m0), <opClass>.trace(m1));

    <testOpClass>.checkAlmostEquals(mr0, mr1);
  }

  /**
   * ∀ m. trace(m) == trace(transpose(m))
   */

  @Test
  @PercentagePassing
  public void testTraceTranspose()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();

    <testOpClass>.checkEquals(
      <opClass>.trace(m),
      <opClass>.trace(<opClass>.transpose(m)));
  }

  /**
   * ∀ m s. s * trace(m) == trace(scale(m, s))
   */

  @Test
  @PercentagePassing
  public void testTraceScale()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.createNormal();

    final <matrixType>\<Object> m = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    <testOpClass>.checkAlmostEquals(
      <testOpClass>.multiply(s, <opClass>.trace(m)),
      <opClass>.trace(<opClass>.scale(m, s)));
  }

  @Test
  public void testZero()
  {
    final <matrixType>\<Object> m = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 3));
  }

  @Test
  public void testIdentity()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.r3c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(0, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.rowColumn(1, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(1, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.rowColumn(2, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(2, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), m.rowColumn(3, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), m.rowColumn(3, 3));
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = gen.next();
    final <matrixType>\<Object> m2 = <opClass>.add(m0, m1);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m0_v = m0.rowColumn(row, column);
        <matrixComponentType> m1_v = m1.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.add(m0_v, m1_v);
        <matrixComponentType> r = m2.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testSubtract()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m0 = gen.next();
    final <matrixType>\<Object> m1 = gen.next();
    final <matrixType>\<Object> m2 = <opClass>.subtract(m0, m1);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m0_v = m0.rowColumn(row, column);
        <matrixComponentType> m1_v = m1.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.subtract(m0_v, m1_v);
        <matrixComponentType> r = m2.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testScale()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <scalarRealType> s = gen.next().r0c0();
    final <matrixType>\<Object> mr = <opClass>.scale(m, s);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m_v = m.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.multiplyReal(m_v, s);
        <matrixComponentType> r = mr.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn0()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withColumn(
        m,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkAlmostEquals(m.r0c1(), mr.r0c1());
    <testOpClass>.checkAlmostEquals(m.r0c2(), mr.r0c2());
    <testOpClass>.checkAlmostEquals(m.r0c3(), mr.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithColumn1()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withColumn(
        m,
        1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(m.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c1());
    <testOpClass>.checkAlmostEquals(m.r0c2(), mr.r0c2());
    <testOpClass>.checkAlmostEquals(m.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithColumn2()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withColumn(
        m,
        2,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(m.r0c0(), mr.r0c0());
    <testOpClass>.checkAlmostEquals(m.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c2());
    <testOpClass>.checkAlmostEquals(m.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithColumn3()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withColumn(
        m,
        3,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(m.r0c0(), mr.r0c0());
    <testOpClass>.checkAlmostEquals(m.r0c1(), mr.r0c1());
    <testOpClass>.checkAlmostEquals(m.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithColumnM1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      m,
      -1,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"),
      <testOpClass>.constant("3"));
  }

  @Test
  @PercentagePassing
  public void testWithColumn4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      m,
      4,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"),
      <testOpClass>.constant("3"));
  }

  @Test
  @PercentagePassing
  public void testWithRow0()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withRow(
        m,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow1()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withRow(
        m,
        1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(mr.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(mr.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r1c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r1c1());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("2"), mr.r1c2());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("3"), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow2()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withRow(
        m,
        2,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(mr.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(mr.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r2c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r2c1());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("2"), mr.r2c2());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("3"), mr.r2c3());

    <testOpClass>.checkAlmostEquals(m.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(m.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(m.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(m.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow3()
  {
    final Generator\<<matrixType>\<Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object> m = gen.next();
    final <matrixType>\<Object> mr =
      <opClass>.withRow(
        m,
        3,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(mr.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(mr.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(m.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(m.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(m.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(m.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(m.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(m.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(m.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(m.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r3c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r3c1());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("2"), mr.r3c2());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("3"), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRowM1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withRow(
      m,
      -1,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"),
      <testOpClass>.constant("3"));
  }

  @Test
  @PercentagePassing
  public void testWithRow4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withRow(
      m,
      4,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"),
      <testOpClass>.constant("3"));
  }

  @Test
  public void testRowOutOfRangeM1_0()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(-1, 0);
  }

  @Test
  public void testRowOutOfRangeM1_1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(-1, 1);
  }

  @Test
  public void testRowOutOfRangeM1_2()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(-1, 2);
  }

  @Test
  public void testRowOutOfRangeM1_3()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(-1, 3);
  }

  @Test
  public void testRowOutOfRange4_0()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(4, 0);
  }

  @Test
  public void testRowOutOfRange4_1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(4, 1);
  }

  @Test
  public void testRowOutOfRange4_2()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(4, 2);
  }

  @Test
  public void testRowOutOfRange4_3()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(4, 3);
  }

  @Test
  public void testColumnOutOfRange0_M1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(0, -1);
  }

  @Test
  public void testColumnOutOfRange1_M1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(1, -1);
  }

  @Test
  public void testColumnOutOfRange2_M1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(2, -1);
  }

  @Test
  public void testColumnOutOfRange3_M1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(3, -1);
  }

  @Test
  public void testColumnOutOfRange0_4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(0, 4);
  }

  @Test
  public void testColumnOutOfRange1_4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(1, 4);
  }

  @Test
  public void testColumnOutOfRange2_4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(2, 4);
  }

  @Test
  public void testColumnOutOfRange3_4()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(3, 4);
  }
}
>>