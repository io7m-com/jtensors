PMatrix4x4Test(
  className,
  matrixType,
  matrixComponentType,
  vectorRowType,
  vectorColumnType,
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
import com.io7m.jtensors.tests.TestUtilities;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import com.io7m.junreachable.UnreachableCodeException;
import java.util.Optional;
import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
  public void testBuilderUnspecified()
  {
    this.expected.expect(IllegalStateException.class);
    <matrixType>.builder().build();
  }

  @Test
  @PercentagePassing
  public void testEqualsHashCodeToString()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = <matrixType>.builder().from(a).build();

    <testOpClass>.checkObjectEquals(a, a);
    <testOpClass>.checkObjectEquals(a, c);
    <testOpClass>.checkObjectEquals(a.hashCode(), a.hashCode());
    <testOpClass>.checkObjectEquals(a.hashCode(), c.hashCode());
    <testOpClass>.checkObjectEquals(a.toString(), a.toString());
    <testOpClass>.checkObjectEquals(a.toString(), c.toString());
    <testOpClass>.checkObjectNotEquals(a, null);
    <testOpClass>.checkObjectNotEquals(a, Integer.valueOf(23));

    <testOpClass>.checkObjectNotEquals(a, b);
    <testOpClass>.checkObjectNotEquals(a.hashCode(), b.hashCode());
    <testOpClass>.checkObjectNotEquals(a.toString(), b.toString());
  }

  /**
   * determinant(identity()) == 1.0
   */

  @Test
  public void testDeterminantIdentity()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), <opClass>.determinant(a));
  }

  /**
   * determinant(zero()) == 0.0
   */

  @Test
  public void testDeterminantZero()
  {
    final <matrixType>\<Object, Object> a = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), <opClass>.determinant(a));
  }

  @Test
  public void testDeterminantScale()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    final <matrixType>\<Object, Object> mr =
      <matrixType>.builder()
      .from(a).setR0c0(<testOpClass>.constant("2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), <opClass>.determinant(mr));
  }

  @Test
  public void testDeterminantScaleNegative()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    final <matrixType>\<Object, Object> mr =
      <matrixType>.builder()
      .from(a).setR0c0(<testOpClass>.constant("-2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("-2"), <opClass>.determinant(mr));
  }

  /**
   * ∀ a b. determinant(multiply(a, b)) == determinant(a) * determinant(b)
   */

  @Test
  @PercentagePassing
  public void testDeterminantProduct()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    <testOpClass>.checkAlmostEquals(
      <opClass>.determinant(<opClass>.multiply(a, b)),
      <testOpClass>.multiply(<opClass>.determinant(a), <opClass>.determinant(b)));
  }

  /**
   * ∀ a. determinant(invert(a)) == 1 / determinant(a)
   */

  @Test
  @PercentagePassing
  public void testDeterminantInverse()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();
    final <matrixType>\<Object, Object> a = gen.next();

    final double mr0 =
      <opClass>.determinant(<opClass>.invert(a).get());
    final double mr1 =
      <testOpClass>.divide(<testOpClass>.constant("1"), <opClass>.determinant(a));

    <testOpClass>.checkAlmostEquals(mr0, mr1);
  }

  /**
   * ∀ a s. s⁴ * determinant(a) == determinant(scale(a, s))
   */

  @Test
  @PercentagePassing
  public void testDeterminantScaleExponent()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    <testOpClass>.checkEquals(
      <testOpClass>.multiply(<testOpClass>.pow(s, <testOpClass>.constant("4")), <opClass>.determinant(a)),
      <opClass>.determinant(<opClass>.scale(a, s)));
  }

  /**
   * ∀ a. determinant(a) == determinant(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testDeterminantTranspose()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> mt = <opClass>.transpose(a);

    <testOpClass>.checkEquals(<opClass>.determinant(a), <opClass>.determinant(mt));
  }

  @Test
  @PercentagePassing
  public void testOfColumns()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b =
      <opClass>.ofColumns(a.column0(), a.column1(), a.column2(), a.column3());

    <testOpClass>.checkAlmostEqualsMatrix(a, b);
  }

  @Test
  @PercentagePassing
  public void testOfRows()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b =
      <opClass>.ofRows(a.row0(), a.row1(), a.row2(), a.row3());

    <testOpClass>.checkAlmostEqualsMatrix(a, b);
  }

  /**
   * invert(zero()) == empty()
   */

  @Test
  public void testInvertZero()
  {
    Assert.assertFalse(<opClass>.invert(<opClass>.zero()).isPresent());
  }

  /**
   * invert(identity()) == Optional.of(identity())
   */

  @Test
  public void testInvertIdentity()
  {
    <testOpClass>.checkAlmostEqualsMatrix(
      <opClass>.identity(),
      <opClass>.invert(<opClass>.identity()).get()
    );
  }

  /**
   * ∀ a b. invert(multiply(a, b)) == multiply(invert(a), invert(b))
   */

  @Test
  @PercentagePassing
  public void testInvertProduct()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    final Optional\<<matrixType>\<Object, Object>\> mr0_opt =
      <opClass>.invert(<opClass>.multiply(a, b));
    final Optional\<<matrixType>\<Object, Object>\> mr1_opt =
      <opClass>.invert(a).flatMap(ia -> {
        return <opClass>.invert(b).map(ib -> {
          return <opClass>.multiply(ib, ia);
        });
      });

    Assert.assertTrue(mr0_opt.isPresent());
    Assert.assertTrue(mr1_opt.isPresent());

    final <matrixType>\<Object, Object> mr0 = mr0_opt.get();
    final <matrixType>\<Object, Object> mr1 = mr1_opt.get();

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a. multiply(a, invert(a)) == identity()
   */

  @Test
  @PercentagePassing
  public void testInvertMultiplyIdentity()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final Optional\<<matrixType>\<Object, Object>\> mr0_opt =
      <opClass>.invert(a).map(aa -> {
        return <opClass>.multiply(a, aa);
      });

    Assert.assertTrue(mr0_opt.isPresent());

    final <matrixType>\<Object, Object> mr0 = mr0_opt.get();
    <testOpClass>.checkAlmostEqualsMatrix(<opClass>.identity(), mr0);
  }

  /**
   * ∀ a. transpose(invert(a)) == invert(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testInvertTranspose()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final Optional\<<matrixType>\<Object, Object>\> mr0_opt =
      <opClass>.invert(a).map(aa -> {
        return <opClass>.transpose(aa);
      });
    final Optional\<<matrixType>\<Object, Object>\> mr1_opt =
      <opClass>.invert(<opClass>.transpose(a));

    Assert.assertTrue(mr0_opt.isPresent());
    Assert.assertTrue(mr1_opt.isPresent());

    final <matrixType>\<Object, Object> mr0 = mr0_opt.get();
    final <matrixType>\<Object, Object> mr1 = mr1_opt.get();
    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a. multiply(a, identity()) == a
   */

  @Test
  @PercentagePassing
  public void testMultiplyIdentity0()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(a, <opClass>.identity());

    <testOpClass>.checkAlmostEqualsMatrix(a, mr0);
  }

  /**
   * ∀ a. multiply(identity(), a) == a
   */

  @Test
  @PercentagePassing
  public void testMultiplyIdentity1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(<opClass>.identity(), a);

    <testOpClass>.checkAlmostEqualsMatrix(a, mr0);
  }

  /**
   * ∀ a. multiply(a, zero()) == zero()
   */

  @Test
  @PercentagePassing
  public void testMultiplyZero0()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(a, <opClass>.zero());

    <testOpClass>.checkAlmostEqualsMatrix(<opClass>.zero(), mr0);
  }

  /**
   * ∀ a. multiply(zero(), a) == zero()
   */

  @Test
  @PercentagePassing
  public void testMultiplyZero1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(<opClass>.zero(), a);

    <testOpClass>.checkAlmostEqualsMatrix(<opClass>.zero(), mr0);
  }

  /**
   * ∀ a b c. multiply(a, multiply(b, c)) == multiply(multiply(a, b), c)
   */

  @Test
  @PercentagePassing
  public void testMultiplyAssociative()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(a, <opClass>.multiply(b, c));
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.multiply(<opClass>.multiply(a, b), c);

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a b c. multiply(a, add(b, c)) == add(multiply(a, b), multiply(a, c))
   */

  @Test
  @PercentagePassing
  public void testMultiplyAddDistributive0()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(a, <opClass>.add(b, c));
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.add(<opClass>.multiply(a, b), <opClass>.multiply(a, c));

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a b c. multiply(add(b, c), a) == add(multiply(b, a), multiply(c, a))
   */

  @Test
  @PercentagePassing
  public void testMultiplyAddDistributive1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(<opClass>.add(b, c), a);
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.add(<opClass>.multiply(b, a), <opClass>.multiply(c, a));

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a b s. scale(multiply(a, b), s) == multiply(scale(a, s), b)
   */

  @Test
  @PercentagePassing
  public void testMultiplyScale0()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.scale(<opClass>.multiply(a, b), s);
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.multiply(<opClass>.scale(a, s), b);

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a b s. multiply(scale(a, s), b) == multiply(a, scale(b, s))
   */

  @Test
  @PercentagePassing
  public void testMultiplyScale1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.multiply(<opClass>.scale(a, s), b);
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.multiply(a, <opClass>.scale(b, s));

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a. a == transpose(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testTransposeTranspose()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = <opClass>.transpose(<opClass>.transpose(a));

    <testOpClass>.checkObjectEquals(a, b);
  }

  /**
   * ∀ a b. transpose(add(a, b)) == add(transpose(a), transpose(b))
   */

  @Test
  @PercentagePassing
  public void testTransposeAdd()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.transpose(<opClass>.add(a, b));
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.add(<opClass>.transpose(a), <opClass>.transpose(b));

    <testOpClass>.checkObjectEquals(mr0, mr1);
  }

  /**
   * ∀ a b. transpose(multiply(a, b)) == multiply(transpose(b), transpose(a))
   */

  @Test
  @PercentagePassing
  public void testTransposeProduct()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();
    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    final <matrixType>\<Object, Object> mr0 =
      <opClass>.transpose(<opClass>.multiply(a, b));
    final <matrixType>\<Object, Object> mr1 =
      <opClass>.multiply(<opClass>.transpose(b), <opClass>.transpose(a));

    <testOpClass>.checkObjectEquals(mr0, mr1);
  }

  /**
   * trace(identity()) == 4.0
   */

  @Test
  public void testTraceIdentity()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("4"), <opClass>.trace(a));
  }

  /**
   * ∀ a b. trace(add(a, b)) == trace(a) + trace(b)
   */

  @Test
  @PercentagePassing
  public void testTraceAdd()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    final <scalarRealType> mr0 =
      <opClass>.trace(<opClass>.add(a, b));
    final <scalarRealType> mr1 =
      <testOpClass>.add(<opClass>.trace(a), <opClass>.trace(b));

    <testOpClass>.checkAlmostEquals(mr0, mr1);
  }

  /**
   * ∀ a b. trace(multiply(a, b)) == trace(multiply(b, a))
   */

  @Test
  @PercentagePassing
  public void testTraceProduct()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();

    final <scalarRealType> mr0 =
      <opClass>.trace(<opClass>.multiply(a, b));
    final <scalarRealType> mr1 =
      <opClass>.trace(<opClass>.multiply(b, a));

    <testOpClass>.checkAlmostEquals(mr0, mr1);
  }

  /**
   * ∀ a. trace(a) == trace(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testTraceTranspose()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    <testOpClass>.checkEquals(
      <opClass>.trace(a),
      <opClass>.trace(<opClass>.transpose(a)));
  }

  /**
   * ∀ a s. s * trace(a) == trace(scale(a, s))
   */

  @Test
  @PercentagePassing
  public void testTraceScale()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.createNormal();

    final <matrixType>\<Object, Object> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    <testOpClass>.checkAlmostEquals(
      <testOpClass>.multiply(s, <opClass>.trace(a)),
      <opClass>.trace(<opClass>.scale(a, s)));
  }

  @Test
  public void testZero()
  {
    final <matrixType>\<Object, Object> a = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 3));
  }

  @Test
  public void testIdentity()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r3c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(1, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(2, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(2, 3));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 1));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(3, 2));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(3, 3));
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = <opClass>.add(a, b);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m0_v = a.rowColumn(row, column);
        <matrixComponentType> m1_v = b.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.add(m0_v, m1_v);
        <matrixComponentType> r = c.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testSubtract()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <matrixType>\<Object, Object> b = gen.next();
    final <matrixType>\<Object, Object> c = <opClass>.subtract(a, b);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m0_v = a.rowColumn(row, column);
        <matrixComponentType> m1_v = b.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.subtract(m0_v, m1_v);
        <matrixComponentType> r = c.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testScale()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();
    final <matrixType>\<Object, Object> mr = <opClass>.scale(a, s);

    for (int row = 0; row \< 4; ++row) {
      for (int column = 0; column \< 4; ++column) {
        <matrixComponentType> m_v = a.rowColumn(row, column);
        <matrixComponentType> e = <testOpClass>.multiplyReal(m_v, s);
        <matrixComponentType> r = mr.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testRowColumnAccess()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    // Perform the retrievals again to exercise the code paths of
    // lazy attributes.

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn0()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withColumn(
        a,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkAlmostEquals(a.r0c1(), mr.r0c1());
    <testOpClass>.checkAlmostEquals(a.r0c2(), mr.r0c2());
    <testOpClass>.checkAlmostEquals(a.r0c3(), mr.r0c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());

    {
      final <vectorRowType> row0 = mr.row0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(mr.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(mr.r0c3(), row0.w());

      final <vectorRowType> row1 = mr.row1();
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), row1.w());

      final <vectorRowType> row2 = mr.row2();
      <testOpClass>.checkAlmostEquals(mr.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), row2.w());

      final <vectorRowType> row3 = mr.row3();
      <testOpClass>.checkAlmostEquals(mr.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), row3.w());

      final <vectorColumnType> column0 = mr.column0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(mr.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(mr.r3c0(), column0.w());

      final <vectorColumnType> column1 = mr.column1();
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), column1.w());

      final <vectorColumnType> column2 = mr.column2();
      <testOpClass>.checkAlmostEquals(mr.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), column2.w());

      final <vectorColumnType> column3 = mr.column3();
      <testOpClass>.checkAlmostEquals(mr.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), column3.w());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withColumn(
        a,
        1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(a.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c1());
    <testOpClass>.checkAlmostEquals(a.r0c2(), mr.r0c2());
    <testOpClass>.checkAlmostEquals(a.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());

    {
      final <vectorRowType> row0 = mr.row0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(mr.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(mr.r0c3(), row0.w());

      final <vectorRowType> row1 = mr.row1();
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), row1.w());

      final <vectorRowType> row2 = mr.row2();
      <testOpClass>.checkAlmostEquals(mr.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), row2.w());

      final <vectorRowType> row3 = mr.row3();
      <testOpClass>.checkAlmostEquals(mr.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), row3.w());

      final <vectorColumnType> column0 = mr.column0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(mr.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(mr.r3c0(), column0.w());

      final <vectorColumnType> column1 = mr.column1();
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), column1.w());

      final <vectorColumnType> column2 = mr.column2();
      <testOpClass>.checkAlmostEquals(mr.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), column2.w());

      final <vectorColumnType> column3 = mr.column3();
      <testOpClass>.checkAlmostEquals(mr.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), column3.w());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn2()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withColumn(
        a,
        2,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(a.r0c0(), mr.r0c0());
    <testOpClass>.checkAlmostEquals(a.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c2());
    <testOpClass>.checkAlmostEquals(a.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());

    {
      final <vectorRowType> row0 = mr.row0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(mr.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(mr.r0c3(), row0.w());

      final <vectorRowType> row1 = mr.row1();
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), row1.w());

      final <vectorRowType> row2 = mr.row2();
      <testOpClass>.checkAlmostEquals(mr.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), row2.w());

      final <vectorRowType> row3 = mr.row3();
      <testOpClass>.checkAlmostEquals(mr.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), row3.w());

      final <vectorColumnType> column0 = mr.column0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(mr.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(mr.r3c0(), column0.w());

      final <vectorColumnType> column1 = mr.column1();
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), column1.w());

      final <vectorColumnType> column2 = mr.column2();
      <testOpClass>.checkAlmostEquals(mr.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), column2.w());

      final <vectorColumnType> column3 = mr.column3();
      <testOpClass>.checkAlmostEquals(mr.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), column3.w());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn3()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withColumn(
        a,
        3,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkAlmostEquals(a.r0c0(), mr.r0c0());
    <testOpClass>.checkAlmostEquals(a.r0c1(), mr.r0c1());
    <testOpClass>.checkAlmostEquals(a.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c3());

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r3c3());

    {
      final <vectorRowType> row0 = mr.row0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(mr.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(mr.r0c3(), row0.w());

      final <vectorRowType> row1 = mr.row1();
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), row1.w());

      final <vectorRowType> row2 = mr.row2();
      <testOpClass>.checkAlmostEquals(mr.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), row2.w());

      final <vectorRowType> row3 = mr.row3();
      <testOpClass>.checkAlmostEquals(mr.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), row3.w());

      final <vectorColumnType> column0 = mr.column0();
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(mr.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(mr.r3c0(), column0.w());

      final <vectorColumnType> column1 = mr.column1();
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(mr.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(mr.r3c1(), column1.w());

      final <vectorColumnType> column2 = mr.column2();
      <testOpClass>.checkAlmostEquals(mr.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(mr.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(mr.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(mr.r3c2(), column2.w());

      final <vectorColumnType> column3 = mr.column3();
      <testOpClass>.checkAlmostEquals(mr.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(mr.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(mr.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(mr.r3c3(), column3.w());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumnM1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      a,
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
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      a,
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
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withRow(
        a,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r0c1());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), mr.r0c2());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow1()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withRow(
        a,
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

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow2()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withRow(
        a,
        2,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(mr.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(mr.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r2c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r2c1());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("2"), mr.r2c2());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("3"), mr.r2c3());

    <testOpClass>.checkAlmostEquals(a.r3c0(), mr.r3c0());
    <testOpClass>.checkAlmostEquals(a.r3c1(), mr.r3c1());
    <testOpClass>.checkAlmostEquals(a.r3c2(), mr.r3c2());
    <testOpClass>.checkAlmostEquals(a.r3c3(), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRow3()
  {
    final Generator\<<matrixType>\<Object, Object\>> gen = <generatorType>.create();

    final <matrixType>\<Object, Object> a = gen.next();

    {
      final <vectorRowType> row0 = a.row0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());
      <testOpClass>.checkAlmostEquals(a.r0c2(), row0.z());
      <testOpClass>.checkAlmostEquals(a.r0c3(), row0.w());

      final <vectorRowType> row1 = a.row1();
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());
      <testOpClass>.checkAlmostEquals(a.r1c2(), row1.z());
      <testOpClass>.checkAlmostEquals(a.r1c3(), row1.w());

      final <vectorRowType> row2 = a.row2();
      <testOpClass>.checkAlmostEquals(a.r2c0(), row2.x());
      <testOpClass>.checkAlmostEquals(a.r2c1(), row2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), row2.z());
      <testOpClass>.checkAlmostEquals(a.r2c3(), row2.w());

      final <vectorRowType> row3 = a.row3();
      <testOpClass>.checkAlmostEquals(a.r3c0(), row3.x());
      <testOpClass>.checkAlmostEquals(a.r3c1(), row3.y());
      <testOpClass>.checkAlmostEquals(a.r3c2(), row3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), row3.w());

      final <vectorColumnType> column0 = a.column0();
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());
      <testOpClass>.checkAlmostEquals(a.r2c0(), column0.z());
      <testOpClass>.checkAlmostEquals(a.r3c0(), column0.w());

      final <vectorColumnType> column1 = a.column1();
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
      <testOpClass>.checkAlmostEquals(a.r2c1(), column1.z());
      <testOpClass>.checkAlmostEquals(a.r3c1(), column1.w());

      final <vectorColumnType> column2 = a.column2();
      <testOpClass>.checkAlmostEquals(a.r0c2(), column2.x());
      <testOpClass>.checkAlmostEquals(a.r1c2(), column2.y());
      <testOpClass>.checkAlmostEquals(a.r2c2(), column2.z());
      <testOpClass>.checkAlmostEquals(a.r3c2(), column2.w());

      final <vectorColumnType> column3 = a.column3();
      <testOpClass>.checkAlmostEquals(a.r0c3(), column3.x());
      <testOpClass>.checkAlmostEquals(a.r1c3(), column3.y());
      <testOpClass>.checkAlmostEquals(a.r2c3(), column3.z());
      <testOpClass>.checkAlmostEquals(a.r3c3(), column3.w());
    }

    final <matrixType>\<Object, Object> mr =
      <opClass>.withRow(
        a,
        3,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());
    <testOpClass>.checkEquals(mr.r0c2(), mr.r0c2());
    <testOpClass>.checkEquals(mr.r0c3(), mr.r0c3());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
    <testOpClass>.checkAlmostEquals(a.r1c2(), mr.r1c2());
    <testOpClass>.checkAlmostEquals(a.r1c3(), mr.r1c3());

    <testOpClass>.checkAlmostEquals(a.r2c0(), mr.r2c0());
    <testOpClass>.checkAlmostEquals(a.r2c1(), mr.r2c1());
    <testOpClass>.checkAlmostEquals(a.r2c2(), mr.r2c2());
    <testOpClass>.checkAlmostEquals(a.r2c3(), mr.r2c3());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r3c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r3c1());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("2"), mr.r3c2());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("3"), mr.r3c3());
  }

  @Test
  @PercentagePassing
  public void testWithRowM1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withRow(
      a,
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
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withRow(
      a,
      4,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"),
      <testOpClass>.constant("3"));
  }

  @Test
  public void testRowOutOfRangeM1_0()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 0);
  }

  @Test
  public void testRowOutOfRangeM1_1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 1);
  }

  @Test
  public void testRowOutOfRangeM1_2()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 2);
  }

  @Test
  public void testRowOutOfRangeM1_3()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 3);
  }

  @Test
  public void testRowOutOfRange4_0()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(4, 0);
  }

  @Test
  public void testRowOutOfRange4_1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(4, 1);
  }

  @Test
  public void testRowOutOfRange4_2()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(4, 2);
  }

  @Test
  public void testRowOutOfRange4_3()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(4, 3);
  }

  @Test
  public void testColumnOutOfRange0_M1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(0, -1);
  }

  @Test
  public void testColumnOutOfRange1_M1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(1, -1);
  }

  @Test
  public void testColumnOutOfRange2_M1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(2, -1);
  }

  @Test
  public void testColumnOutOfRange3_M1()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(3, -1);
  }

  @Test
  public void testColumnOutOfRange0_4()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(0, 4);
  }

  @Test
  public void testColumnOutOfRange1_4()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(1, 4);
  }

  @Test
  public void testColumnOutOfRange2_4()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(2, 4);
  }

  @Test
  public void testColumnOutOfRange3_4()
  {
    final <matrixType>\<Object, Object> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(3, 4);
  }
}
>>
