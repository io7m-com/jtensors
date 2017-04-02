PVector3Test(
  className,
  vectorType,
  vectorComponentType,
  vectorReadable3Type,
  vectorReadable2Type,
  scalarRealType,
  generatorType,
  testOpClass,
  opClass,
  hasFloatingPoint) ::= <<
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

package com.io7m.jtensors.tests.core.parameterized.vectors;

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
 * \<p>Unit tests for {@link <vectorType>} values.\</p>
 *
 * @since 8.0.0
 */

public final class <className>
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  private static Generator\<<vectorType>\> createGenerator()
  {
    final Object o_gen = <generatorType>.create();
    return (Generator\<<vectorType>\>) o_gen;
  }

  private static Generator\<<vectorType>\> createGeneratorSmall()
  {
    final Object o_gen = <generatorType>.createSmall();
    return (Generator\<<vectorType>\>) o_gen;
  }

  @Test
  public void testEqualsHashCodeToString()
  {
    final <vectorType> v0 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final <vectorType> v1 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final <vectorType> v2 =
      <vectorType>.of(
        <testOpClass>.constant("10"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final <vectorType> v3 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("11"),
        <testOpClass>.constant("2"));

    final <vectorType> v4 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("12"));

    Assert.assertEquals(v0, v0);
    Assert.assertEquals(v0, v1);
    Assert.assertNotEquals(v0, v2);
    Assert.assertNotEquals(v0, v3);
    Assert.assertNotEquals(v0, v4);
    Assert.assertNotEquals(v0, null);
    Assert.assertNotEquals(v0, Integer.valueOf(23));

    Assert.assertEquals(v0.toString(), v1.toString());
    Assert.assertNotEquals(v0.toString(), v2.toString());
    Assert.assertNotEquals(v0.toString(), v3.toString());
    Assert.assertNotEquals(v0.toString(), v4.toString());

    Assert.assertEquals(v0.hashCode(), v1.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v2.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v3.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v4.hashCode());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());
  }

  @Test
  public void testBuilder()
  {
    final <vectorType> v0 =
      <vectorType>.builder()
      .setX(<testOpClass>.constant("0"))
      .setY(<testOpClass>.constant("1"))
      .setZ(<testOpClass>.constant("2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());

    {
      final <vectorType> v1 =
        <vectorType>.builder()
        .from(v0)
        .build();
      Assert.assertEquals(v0, v1);
    }

    {
      final <vectorType> v1 =
        <vectorType>.builder()
        .from((<vectorReadable3Type>) v0)
        .build();
      Assert.assertEquals(v0, v1);
    }

    {
      final <vectorType> v1 =
        <vectorType>.builder()
        .setZ(<testOpClass>.constant("2"))
        .from((<vectorReadable2Type>) v0)
        .build();
      Assert.assertEquals(v0, v1);
    }
  }

  @Test
  public void testBuilderFailure()
  {
    this.expected.expect(IllegalStateException.class);

    final <vectorType> v0 =
      <vectorType>.builder()
      .build();

    Assert.fail();
  }

  @Test
  @PercentagePassing
  public void testAbsolute()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = <opClass>.absolute(v0);

    <vectorComponentType> ex = (<vectorComponentType>) <testOpClass>.absolute(v0.x());
    <vectorComponentType> ey = (<vectorComponentType>) <testOpClass>.absolute(v0.y());
    <vectorComponentType> ez = (<vectorComponentType>) <testOpClass>.absolute(v0.z());

    <testOpClass>.checkAlmostEquals(ex, v1.x());
    <testOpClass>.checkAlmostEquals(ey, v1.y());
    <testOpClass>.checkAlmostEquals(ez, v1.z());
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<vectorType>\> gen = createGenerator();
    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();

    <vectorComponentType> ex = (<vectorComponentType>) <testOpClass>.add(v0.x(), v1.x());
    <vectorComponentType> ey = (<vectorComponentType>) <testOpClass>.add(v0.y(), v1.y());
    <vectorComponentType> ez = (<vectorComponentType>) <testOpClass>.add(v0.z(), v1.z());

    final <vectorType> v2 = <opClass>.add(v0, v1);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  /**
   * ∀ v0 v1 v2. add(add(v0, v1), v2) == add(v0, add(v1, v2))
   */

  @Test
  @PercentagePassing
  public void testAddAssociative()
  {
    final Generator\<<vectorType>\> gen = createGeneratorSmall();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();
    final <vectorType> v2 = gen.next();

    final <vectorType> vr0 = <opClass>.add(<opClass>.add(v0, v1), v2);
    final <vectorType> vr1 = <opClass>.add(v0, <opClass>.add(v1, v2));

    <testOpClass>.checkAlmostEqualsVague(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEqualsVague(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEqualsVague(vr0.z(), vr1.z());
  }

  /**
   * ∀ v0 v1. add(v0, v1) == add(v1, v0)
   */

  @Test
  @PercentagePassing
  public void testAddCommutative()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();

    final <vectorType> vr0 = <opClass>.add(v0, v1);
    final <vectorType> vr1 = <opClass>.add(v1, v0);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ v0. add(v0, negate(v0)) == (0, 0, 0)
   */

  @Test
  @PercentagePassing
  public void testAddNegationZero()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> vr0 = <opClass>.add(v0, <opClass>.negate(v0));

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.z());
  }

  @Test
  @PercentagePassing
  public void testAddScaled()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();
    final <scalarRealType> r = gen.next().x();

    <vectorComponentType> mx = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.x(), r);
    <vectorComponentType> my = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.y(), r);
    <vectorComponentType> mz = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.z(), r);

    <vectorComponentType> ex = (<vectorComponentType>) <testOpClass>.add(v0.x(), mx);
    <vectorComponentType> ey = (<vectorComponentType>) <testOpClass>.add(v0.y(), my);
    <vectorComponentType> ez = (<vectorComponentType>) <testOpClass>.add(v0.z(), mz);

    final <vectorType> v2 = <opClass>.addScaled(v0, v1, r);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  /**
   * ∀ a b. crossProduct(a, b) == crossProduct(negate(b), a)
   */

  @Test
  @PercentagePassing
  public void testCrossProductAnticommutative()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> a = gen.next();
    final <vectorType> b = gen.next();

    final <vectorType> vr0 = <opClass>.crossProduct(a, b);
    final <vectorType> vr1 = <opClass>.crossProduct(<opClass>.negate(b), a);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. crossProduct(a, add(b, c)) == add(crossProduct(a, b), crossProduct(a, c))
   */

  @Test
  @PercentagePassing
  public void testCrossProductDistributive()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> a = gen.next();
    final <vectorType> b = gen.next();
    final <vectorType> c = gen.next();

    final <vectorType> vr0 =
      <opClass>.crossProduct(a, <opClass>.add(b, c));
    final <vectorType> vr1 =
      <opClass>.add(<opClass>.crossProduct(a, b), <opClass>.crossProduct(a, c));

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. crossProduct(scale(a, c), b) == scale(crossProduct(a, b), c)
   */

  @Test
  @PercentagePassing
  public void testCrossProductScale0()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> a = gen.next();
    final <vectorType> b = gen.next();
    final <scalarRealType> c = Math.random();

    final <vectorType> vr0 =
      <opClass>.crossProduct(<opClass>.scale(a, c), b);
    final <vectorType> vr1 =
      <opClass>.scale(<opClass>.crossProduct(a, b), c);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. scale(crossProduct(a, b), c) == crossProduct(a, scale(b, c))
   */

  @Test
  @PercentagePassing
  public void testCrossProductScale1()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> a = gen.next();
    final <vectorType> b = gen.next();
    final <scalarRealType> c = Math.random();

    final <vectorType> vr0 =
      <opClass>.scale(<opClass>.crossProduct(a, b), c);
    final <vectorType> vr1 =
      <opClass>.crossProduct(a, <opClass>.scale(b, c));

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  @Test
  @PercentagePassing(passPercent = 80.0)
  public void testDotProductSelfMagnitudeSquared()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();

    <testOpClass>.checkAlmostEquals(
      <opClass>.magnitudeSquared(v0),
      <opClass>.dotProduct(v0, v0));
  }

  @Test
  @PercentagePassing
  public void testInterpolateLinearLimits()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();

    final <vectorType> v2 =
      <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("0"));

    <testOpClass>.checkAlmostEquals(v0.x(), v2.x());
    <testOpClass>.checkAlmostEquals(v0.y(), v2.y());
    <testOpClass>.checkAlmostEquals(v0.z(), v2.z());

    final <vectorType> v3 =
      <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("1"));

    <testOpClass>.checkAlmostEquals(v1.x(), v3.x());
    <testOpClass>.checkAlmostEquals(v1.y(), v3.y());
    <testOpClass>.checkAlmostEquals(v1.z(), v3.z());
  }

  @Test
  public void testMagnitudeSquaredZero()
  {
    <testOpClass>.checkAlmostEquals(
      <testOpClass>.constant("0"),
      <opClass>.magnitudeSquared(<opClass>.zero()));
  }

  @Test
  public void testMagnitudeZero()
  {
    <testOpClass>.checkAlmostEquals(
      <testOpClass>.constant("0"),
      <opClass>.magnitude(<opClass>.zero()));
  }

  <if(hasFloatingPoint)>
  @Test
  public void testNormalizeZero()
  {
    Assert.assertEquals(<opClass>.zero(), <opClass>.normalize(<opClass>.zero()));
  }

  @Test
  @PercentagePassing
  public void testMagnitudeNormalizeOne()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> vn = <opClass>.normalize(v0);
    final <scalarRealType> m = <opClass>.magnitude(vn);

    if (<testOpClass>.compareReal(m, <testOpClass>.constant("0")) > 0) {
      <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), m);
    } else {
      Assert.assertEquals(v0, vn);
    }
  }
  <endif>

  @Test
  @PercentagePassing
  public void testScale()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <scalarRealType> r = gen.next().x();

    <vectorComponentType> ex = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.x(), r);
    <vectorComponentType> ey = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.y(), r);
    <vectorComponentType> ez = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.z(), r);

    final <vectorType> v2 = <opClass>.scale(v0, r);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  @Test
  @PercentagePassing
  public void testScaleZero()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = <opClass>.scale(v0, <testOpClass>.constant("0"));
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.z());
  }

  @Test
  @PercentagePassing
  public void testScaleOne()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = <opClass>.scale(v0, <testOpClass>.constant("1"));
    Assert.assertEquals(v0, v1);
  }

  @Test
  @PercentagePassing
  public void testSubtract()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final <vectorType> v0 = gen.next();
    final <vectorType> v1 = gen.next();

    <vectorComponentType> ex = (<vectorComponentType>) <testOpClass>.subtract(v0.x(), v1.x());
    <vectorComponentType> ey = (<vectorComponentType>) <testOpClass>.subtract(v0.y(), v1.y());
    <vectorComponentType> ez = (<vectorComponentType>) <testOpClass>.subtract(v0.z(), v1.z());

    final <vectorType> v2 = <opClass>.subtract(v0, v1);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }
}
>>