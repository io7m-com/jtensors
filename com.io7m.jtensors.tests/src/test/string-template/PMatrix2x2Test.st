PMatrix2x2Test(
  className,
  matrixType,
  matrixComponentType,
  vectorRowType,
  vectorColumnType,
  vectorColumnGenericType,
  vectorGeneratorType,
  vectorGenericGeneratorType,
  generatorType,
  scalarRealType,
  testOpClass,
  opClass,
  vectorOpClass,
  hasGeneric) ::= <<
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
package com.io7m.jtensors.tests.core.parameterized.matrices;
<else>
package com.io7m.jtensors.tests.core.unparameterized.matrices;
<endif>

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
import org.junit.Ignore;
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

  private static Generator\<<matrixType>\> createGenerator()
  {
    final Object o_gen = <generatorType>.create();
    return (Generator\<<matrixType>\>) o_gen;
  }

  private static Generator\<<matrixType>\> createGeneratorNormal()
  {
    final Object o_gen = <generatorType>.createNormal();
    return (Generator\<<matrixType>\>) o_gen;
  }

  private static Generator\<<vectorColumnGenericType>\> createGeneratorVectorGenericNormal()
  {
    final Object o_gen = <vectorGenericGeneratorType>.createNormal();
    return (Generator\<<vectorColumnGenericType>\>) o_gen;
  }

  private static Generator\<<vectorColumnType>\> createGeneratorVectorNormal()
  {
    final Object o_gen = <vectorGeneratorType>.createNormal();
    return (Generator\<<vectorColumnType>\>) o_gen;
  }

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
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = <matrixType>.builder().from(a).build();

    <testOpClass>.checkObjectEquals(a, a);
    <testOpClass>.checkObjectEquals(a, c);
    <testOpClass>.checkObjectEquals(a.hashCode(), a.hashCode());
    <testOpClass>.checkObjectEquals(a.hashCode(), c.hashCode());
    <testOpClass>.checkObjectEquals(a.toString(), a.toString());
    <testOpClass>.checkObjectEquals(a.toString(), c.toString());
    <testOpClass>.checkObjectNotEquals(a, null);
    <testOpClass>.checkObjectNotEquals(a, Integer.valueOf(23));

    <testOpClass>.checkObjectNotEquals(a, b);
    <testOpClass>.checkObjectNotEquals(a.toString(), b.toString());
  }

  /**
   * determinant(identity()) == 1.0
   */

  @Test
  public void testDeterminantIdentity()
  {
    final <matrixType> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), <opClass>.determinant(a));
  }

  /**
   * determinant(zero()) == 0.0
   */

  @Test
  public void testDeterminantZero()
  {
    final <matrixType> a = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), <opClass>.determinant(a));
  }

  @Test
  public void testDeterminantScale()
  {
    final <matrixType> a = <opClass>.identity();

    final <matrixType> mr =
      <matrixType>.builder()
      .from(a).setR0c0(<testOpClass>.constant("2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), <opClass>.determinant(mr));
  }

  @Test
  public void testDeterminantScaleNegative()
  {
    final <matrixType> a = <opClass>.identity();

    final <matrixType> mr =
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
    final Generator\<<matrixType>\> gen = createGenerator();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

    final <scalarRealType> exp_det =
      <opClass>.determinant(<opClass>.multiply(a, b));
    final <scalarRealType> rec_det =
      <testOpClass>.multiply(<opClass>.determinant(a), <opClass>.determinant(b));

    <testOpClass>.checkAlmostEqualsVague(exp_det, rec_det);
  }

  /**
   * ∀ a. determinant(invert(a)) == 1 / determinant(a)
   */

  @Test
  @PercentagePassing
  public void testDeterminantInverse()
  {
    final Generator\<<matrixType>\> gen = createGenerator();
    final <matrixType> a = gen.next();

    final Optional\<<matrixType>\> ia =
      <opClass>.invert(a);
    final double mr0 =
      <opClass>.determinant(ia.get());
    final double mr1 =
      <testOpClass>.divide(<testOpClass>.constant("1"), <opClass>.determinant(a));

    <testOpClass>.checkAlmostEquals(mr0, mr1);
  }

  /**
   * ∀ a s. s² * determinant(a) == determinant(scale(a, s))
   */

  @Test
  @PercentagePassing
  public void testDeterminantScaleExponent()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    final <scalarRealType> exp_s =
      <testOpClass>.pow(s, <testOpClass>.constant("2"));
    final <scalarRealType> exp_det =
      <testOpClass>.multiply(exp_s, <opClass>.determinant(a));
    final <scalarRealType> rec_det =
      <opClass>.determinant(<opClass>.scale(a, s));

    <testOpClass>.checkAlmostEqualsVague(exp_det, rec_det);
  }

  /**
   * ∀ a. determinant(a) == determinant(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testDeterminantTranspose()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> mt = <opClass>.transpose(a);

    <testOpClass>.checkEquals(<opClass>.determinant(a), <opClass>.determinant(mt));
  }

  @Test
  @PercentagePassing
  public void testOfColumns()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b =
      <opClass>.ofColumns(<opClass>.column0(a), <opClass>.column1(a));

    <testOpClass>.checkAlmostEqualsMatrix(a, b);
  }

  @Test
  @PercentagePassing
  public void testOfRows()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b =
      <opClass>.ofRows(<opClass>.row0(a), <opClass>.row1(a));

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
   * ∀ a b. invert(multiply(a, b)) == multiply(invert(b), invert(a))
   */

  @Test
  @PercentagePassing
  public void testInvertProduct()
  {
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

    final <matrixType> ab =
      <opClass>.multiply(a, b);
    final Optional\<<matrixType>\> mr0_opt =
      <opClass>.invert(ab);

    final Optional\<<matrixType>\> ia_opt = <opClass>.invert(a);
    final Optional\<<matrixType>\> ib_opt = <opClass>.invert(b);
    final <matrixType> ia = ia_opt.get();
    final <matrixType> ib = ib_opt.get();

    final <matrixType> mr1 = <opClass>.multiply(ib, ia);
    final <matrixType> mr0 = mr0_opt.get();

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a. multiply(a, invert(a)) == identity()
   */

  @Test
  @PercentagePassing
  public void testInvertMultiplyIdentity()
  {
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final Optional\<<matrixType>\> ia_opt = <opClass>.invert(a);
    final <matrixType> ia = ia_opt.get();
    final <matrixType> mr0 = <opClass>.multiply(a, ia);
    <testOpClass>.checkAlmostEqualsMatrix(<opClass>.identity(), mr0);
  }

  /**
   * ∀ a. transpose(invert(a)) == invert(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testInvertTranspose()
  {
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();

    final Optional\<<matrixType>\> ia_opt = <opClass>.invert(a);
    final <matrixType> ia = ia_opt.get();
    final <matrixType> mr0 = <opClass>.transpose(ia);

    final <matrixType> a_tr = <opClass>.transpose(a);
    final Optional\<<matrixType>\> mr1_opt = <opClass>.invert(a_tr);
    final <matrixType> mr1 = mr1_opt.get();

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ a. multiply(a, identity()) == a
   */

  @Test
  @PercentagePassing
  public void testMultiplyIdentity0()
  {
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();

    final <matrixType> mr0 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();

    final <matrixType> mr0 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();

    final <matrixType> mr0 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();

    final <matrixType> mr0 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = gen.next();

    final <matrixType> mr0 =
      <opClass>.multiply(a, <opClass>.multiply(b, c));
    final <matrixType> mr1 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = gen.next();

    final <matrixType> mr0 =
      <opClass>.multiply(a, <opClass>.add(b, c));
    final <matrixType> mr1 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = gen.next();

    final <matrixType> mr0 =
      <opClass>.multiply(<opClass>.add(b, c), a);
    final <matrixType> mr1 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    final <matrixType> mr0 =
      <opClass>.scale(<opClass>.multiply(a, b), s);
    final <matrixType> mr1 =
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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    final <matrixType> mr0 =
      <opClass>.multiply(<opClass>.scale(a, s), b);
    final <matrixType> mr1 =
      <opClass>.multiply(a, <opClass>.scale(b, s));

    <testOpClass>.checkAlmostEqualsMatrix(mr0, mr1);
  }

  /**
   * ∀ v. multiplyVectorPost(identity(), v) == v
   */

  @Test
  @PercentagePassing
  public void testMultiplyVectorPostIdentity()
  {
    {
      final Generator\<<vectorColumnType>\> gen = createGeneratorVectorNormal();
      final <vectorColumnType> v = gen.next();

      final <vectorColumnType> vr0 =
        <opClass>.multiplyVectorPost(<opClass>.identity(), v);

      <testOpClass>.checkAlmostEqualsVector(v, vr0);
    }

    <if(hasGeneric)>
    {
      final Generator\<<vectorColumnGenericType>\> gen = createGeneratorVectorGenericNormal();
      final <vectorColumnGenericType> v = gen.next();

      final <vectorColumnGenericType> vr0 =
        <opClass>.multiplyVectorPost(<opClass>.identity(), v);

      <testOpClass>.checkAlmostEqualsVector(v, vr0);
    }
    <endif>
  }

  /**
   * ∀ a v. multiplyVectorPost(invert(a), multiplyVectorPost(a, v)) == v
   */

  @Test
  @PercentagePassing
  public void testMultiplyVectorPostIdentityInverse()
  {
    final Generator\<<matrixType>\> m_gen = createGeneratorNormal();
    final <matrixType> a = m_gen.next();
    final Optional\<<matrixType>\> ia_opt = <opClass>.invert(a);
    final <matrixType> ia = ia_opt.get();

    final Generator\<<vectorColumnType>\> v_gen = createGeneratorVectorNormal();
    final <vectorColumnType> v = v_gen.next();

    {
      final <vectorColumnType> vr0 =
        <opClass>.multiplyVectorPost(ia, <opClass>.multiplyVectorPost(a, v));
      <testOpClass>.checkAlmostEqualsVector(v, vr0);
    }

    <if(hasGeneric)>
    {
      final <vectorColumnGenericType> vg =
        <vectorColumnGenericType>.of(v.x(), v.y());
      final <vectorColumnGenericType> vr0 =
        <opClass>.multiplyVectorPost(ia, <opClass>.multiplyVectorPost(a, vg));
      <testOpClass>.checkAlmostEqualsVector(vg, vr0);
    }
    <endif>
  }

  /**
   * ∀ x y v. multiplyVectorPost(ofScale(x, y), v) == (x, y) * v
   */

  @Test
  @PercentagePassing
  public void testMultiplyVectorPostScale()
  {
    final Generator\<<vectorColumnType>\> gen = createGeneratorVectorNormal();
    final <vectorColumnType> vs = gen.next();
    final <vectorColumnType> v = gen.next();

    final <matrixType> m =
      <opClass>.ofScale(vs.x(), vs.y());

    {
      final <vectorColumnType> vr = <opClass>.multiplyVectorPost(m, v);
      final <scalarRealType> rx = <testOpClass>.multiply(vs.x(), v.x());
      final <scalarRealType> ry = <testOpClass>.multiply(vs.y(), v.y());
      <testOpClass>.checkAlmostEquals(rx, vr.x());
      <testOpClass>.checkAlmostEquals(ry, vr.y());
    }

    <if(hasGeneric)>
    {
      final <vectorColumnGenericType> vg =
        <vectorColumnGenericType>.of(v.x(), v.y());

      final <vectorColumnGenericType> vr = <opClass>.multiplyVectorPost(m, vg);
      final <scalarRealType> rx = <testOpClass>.multiply(vs.x(), v.x());
      final <scalarRealType> ry = <testOpClass>.multiply(vs.y(), v.y());
      <testOpClass>.checkAlmostEquals(rx, vr.x());
      <testOpClass>.checkAlmostEquals(ry, vr.y());
    }
    <endif>
  }

  /**
   * ∀ v. multiplyVectorPost(ofScale(1, 1), v) == v
   */

  @Test
  @PercentagePassing
  public void testMultiplyVectorPostScaleOne()
  {
    final Generator\<<vectorColumnType>\> gen = createGeneratorVectorNormal();
    final <vectorColumnType> v = gen.next();

    final <matrixType> m = <opClass>.ofScale(
      <testOpClass>.constant("1"),
      <testOpClass>.constant("1"));

    {
      final <vectorColumnType> vr = <opClass>.multiplyVectorPost(m, v);
      <testOpClass>.checkAlmostEqualsVector(v, vr);
    }

    <if(hasGeneric)>
    {
      final <vectorColumnGenericType> vg =
        <vectorColumnGenericType>.of(v.x(), v.y());
      final <vectorColumnGenericType> vr =
        <opClass>.multiplyVectorPost(m, vg);
      <testOpClass>.checkAlmostEqualsVector(vg, vr);
    }
    <endif>
  }

  /**
   * ∀ a. a == transpose(transpose(a))
   */

  @Test
  @PercentagePassing
  public void testTransposeTranspose()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b = <opClass>.transpose(<opClass>.transpose(a));

    <testOpClass>.checkObjectEquals(a, b);
  }

  /**
   * ∀ a b. transpose(add(a, b)) == add(transpose(a), transpose(b))
   */

  @Test
  @PercentagePassing
  public void testTransposeAdd()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

    final <matrixType> mr0 =
      <opClass>.transpose(<opClass>.add(a, b));
    final <matrixType> mr1 =
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
    final Generator\<<matrixType>\> gen = createGenerator();
    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

    final <matrixType> mr0 =
      <opClass>.transpose(<opClass>.multiply(a, b));
    final <matrixType> mr1 =
      <opClass>.multiply(<opClass>.transpose(b), <opClass>.transpose(a));

    <testOpClass>.checkObjectEquals(mr0, mr1);
  }

  /**
   * trace(identity()) == 2.0
   */

  @Test
  public void testTraceIdentity()
  {
    final <matrixType> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("2"), <opClass>.trace(a));
  }

  /**
   * ∀ a b. trace(add(a, b)) == trace(a) + trace(b)
   */

  @Test
  @PercentagePassing
  public void testTraceAdd()
  {
    final Generator\<<matrixType>\> gen = createGeneratorNormal();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();

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
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

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
    final Generator\<<matrixType>\> gen = createGeneratorNormal();

    final <matrixType> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();

    <testOpClass>.checkAlmostEquals(
      <testOpClass>.multiply(s, <opClass>.trace(a)),
      <opClass>.trace(<opClass>.scale(a, s)));
  }

  @Test
  public void testZero()
  {
    final <matrixType> a = <opClass>.zero();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c1());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c1());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 1));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 1));
  }

  @Test
  public void testIdentity()
  {
    final <matrixType> a = <opClass>.identity();

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r0c1());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.r1c1());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(0, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(0, 1));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), a.rowColumn(1, 0));
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), a.rowColumn(1, 1));
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = <opClass>.add(a, b);

    for (int row = 0; row \< 2; ++row) {
      for (int column = 0; column \< 2; ++column) {
        <matrixComponentType> m0_v = a.rowColumn(row, column);
        <matrixComponentType> m1_v = b.rowColumn(row, column);
        <matrixComponentType> e = (<matrixComponentType>) <testOpClass>.add(m0_v, m1_v);
        <matrixComponentType> r = c.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testSubtract()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <matrixType> b = gen.next();
    final <matrixType> c = <opClass>.subtract(a, b);

    for (int row = 0; row \< 2; ++row) {
      for (int column = 0; column \< 2; ++column) {
        <matrixComponentType> m0_v = a.rowColumn(row, column);
        <matrixComponentType> m1_v = b.rowColumn(row, column);
        <matrixComponentType> e = (<matrixComponentType>) <testOpClass>.subtract(m0_v, m1_v);
        <matrixComponentType> r = c.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testScale()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();
    final <scalarRealType> s = gen.next().r0c0();
    final <matrixType> mr = <opClass>.scale(a, s);

    for (int row = 0; row \< 2; ++row) {
      for (int column = 0; column \< 2; ++column) {
        <matrixComponentType> m_v = a.rowColumn(row, column);
        <matrixComponentType> e = (<matrixComponentType>) <testOpClass>.multiplyReal(m_v, s);
        <matrixComponentType> r = mr.rowColumn(row, column);
        <testOpClass>.checkAlmostEquals(e, r);
      }
    }
  }

  @Test
  @PercentagePassing
  public void testRowColumnAccess()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }

    // Perform the retrievals again to exercise the code paths of
    // lazy attributes.

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn0()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }

    final <matrixType> mr =
      <opClass>.withColumn(
        a,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkAlmostEquals(a.r0c1(), mr.r0c1());

    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());

    {
      final <vectorRowType> row0 = <opClass>.row0(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(mr);
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumn1()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }

    final <matrixType> mr =
      <opClass>.withColumn(
        a,
        1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    <testOpClass>.checkAlmostEquals(a.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c1());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r1c1());

    {
      final <vectorRowType> row0 = <opClass>.row0(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(mr.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(mr);
      <testOpClass>.checkAlmostEquals(mr.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(mr.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(mr);
      <testOpClass>.checkAlmostEquals(mr.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(mr.r1c1(), column1.y());
    }
  }

  @Test
  @PercentagePassing
  public void testWithColumnM1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      a,
      -1,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"));
  }

  @Test
  @PercentagePassing
  public void testWithColumn3()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withColumn(
      a,
      3,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"));
  }

  @Test
  @PercentagePassing
  public void testWithRow0()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }

    final <matrixType> mr =
      <opClass>.withRow(
        a,
        0,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), mr.r0c0());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), mr.r0c1());

    <testOpClass>.checkAlmostEquals(a.r1c0(), mr.r1c0());
    <testOpClass>.checkAlmostEquals(a.r1c1(), mr.r1c1());
  }

  @Test
  @PercentagePassing
  public void testWithRow1()
  {
    final Generator\<<matrixType>\> gen = createGenerator();

    final <matrixType> a = gen.next();

    {
      final <vectorRowType> row0 = <opClass>.row0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), row0.x());
      <testOpClass>.checkAlmostEquals(a.r0c1(), row0.y());

      final <vectorRowType> row1 = <opClass>.row1(a);
      <testOpClass>.checkAlmostEquals(a.r1c0(), row1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), row1.y());

      final <vectorColumnType> column0 = <opClass>.column0(a);
      <testOpClass>.checkAlmostEquals(a.r0c0(), column0.x());
      <testOpClass>.checkAlmostEquals(a.r1c0(), column0.y());

      final <vectorColumnType> column1 = <opClass>.column1(a);
      <testOpClass>.checkAlmostEquals(a.r0c1(), column1.x());
      <testOpClass>.checkAlmostEquals(a.r1c1(), column1.y());
    }

    final <matrixType> mr =
      <opClass>.withRow(
        a,
        1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    <testOpClass>.checkEquals(mr.r0c0(), mr.r0c0());
    <testOpClass>.checkEquals(mr.r0c1(), mr.r0c1());

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), mr.r1c0());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), mr.r1c1());
  }

  @Test
  @PercentagePassing
  public void testWithRowM1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    <opClass>.withRow(
      a,
      -1,
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"));
  }

  @Test
  public void testRowOutOfRangeM1_0()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 0);
  }

  @Test
  public void testRowOutOfRangeM1_1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 1);
  }

  @Test
  public void testRowOutOfRangeM1_2()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 2);
  }

  @Test
  public void testRowOutOfRangeM1_3()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(-1, 3);
  }

  @Test
  public void testRowOutOfRange3_0()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(3, 0);
  }

  @Test
  public void testRowOutOfRange3_1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(3, 1);
  }

  @Test
  public void testRowOutOfRange3_2()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(3, 2);
  }

  @Test
  public void testColumnOutOfRange0_M1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(0, -1);
  }

  @Test
  public void testColumnOutOfRange1_M1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(1, -1);
  }

  @Test
  public void testColumnOutOfRange2_M1()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(2, -1);
  }

  @Test
  public void testColumnOutOfRange0_3()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(0, 3);
  }

  @Test
  public void testColumnOutOfRange1_3()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(1, 3);
  }

  @Test
  public void testColumnOutOfRange2_3()
  {
    final <matrixType> a = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    a.rowColumn(2, 3);
  }
}
>>
