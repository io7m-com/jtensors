Quaternion4Test(
  className,
  quaternionType,
  quaternionComponentType,
  scalarRealType,
  generatorType,
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
 * \<p>Unit tests for {@link <quaternionType>} values.\</p>
 *
 * @since 8.0.0
 */

public final class <className>
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_ITERATIONS);

  private static Generator\<<quaternionType>\> createGenerator()
  {
    final Object o_gen = <generatorType>.create();
    return (Generator\<<quaternionType>\>) o_gen;
  }

  @Test
  public void testEqualsHashCodeToString()
  {
    final <quaternionType> q0 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> q1 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> q2 =
      <quaternionType>.of(
        <testOpClass>.constant("10"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> v3 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("11"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> v4 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("12"),
        <testOpClass>.constant("3"));

    final <quaternionType> v5 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("13"));

    Assert.assertEquals(q0, q0);
    Assert.assertEquals(q0, q1);
    Assert.assertNotEquals(q0, q2);
    Assert.assertNotEquals(q0, v3);
    Assert.assertNotEquals(q0, v4);
    Assert.assertNotEquals(q0, v5);
    Assert.assertNotEquals(q0, null);
    Assert.assertNotEquals(q0, Integer.valueOf(23));

    Assert.assertEquals(q0.toString(), q1.toString());
    Assert.assertNotEquals(q0.toString(), q2.toString());
    Assert.assertNotEquals(q0.toString(), v3.toString());
    Assert.assertNotEquals(q0.toString(), v4.toString());
    Assert.assertNotEquals(q0.toString(), v5.toString());

    Assert.assertEquals(q0.hashCode(), q1.hashCode());
    Assert.assertNotEquals(q0.hashCode(), q2.hashCode());
    Assert.assertNotEquals(q0.hashCode(), v3.hashCode());
    Assert.assertNotEquals(q0.hashCode(), v4.hashCode());
    Assert.assertNotEquals(q0.hashCode(), v5.hashCode());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), q0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), q0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), q0.z());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), q0.w());
  }

  @Test
  public void testBuilder()
  {
    final <quaternionType> q0 =
      <quaternionType>.builder()
      .setX(<testOpClass>.constant("0"))
      .setY(<testOpClass>.constant("1"))
      .setZ(<testOpClass>.constant("2"))
      .setW(<testOpClass>.constant("3"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), q0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), q0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), q0.z());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), q0.w());

    {
      final <quaternionType> q1 =
        <quaternionType>.builder()
        .from(q0)
        .build();
      Assert.assertEquals(q0, q1);
    }
  }

  @Test
  public void testBuilderFailure()
  {
    this.expected.expect(IllegalStateException.class);

    final <quaternionType> q0 =
      <quaternionType>.builder()
      .build();

    Assert.fail();
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();
    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();

    <quaternionComponentType> ex = (<quaternionComponentType>) <testOpClass>.add(q0.x(), q1.x());
    <quaternionComponentType> ey = (<quaternionComponentType>) <testOpClass>.add(q0.y(), q1.y());
    <quaternionComponentType> ez = (<quaternionComponentType>) <testOpClass>.add(q0.z(), q1.z());
    <quaternionComponentType> ew = (<quaternionComponentType>) <testOpClass>.add(q0.w(), q1.w());

    final <quaternionType> q2 = <opClass>.add(q0, q1);
    <testOpClass>.checkAlmostEquals(ex, q2.x());
    <testOpClass>.checkAlmostEquals(ey, q2.y());
    <testOpClass>.checkAlmostEquals(ez, q2.z());
    <testOpClass>.checkAlmostEquals(ew, q2.w());
  }

  /**
   * ∀ q0 q1 q2. add(add(q0, q1), q2) == add(q0, add(q1, q2))
   */

  @Test
  @PercentagePassing
  public void testAddAssociative()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();
    final <quaternionType> q2 = gen.next();

    final <quaternionType> qr0 = <opClass>.add(<opClass>.add(q0, q1), q2);
    final <quaternionType> qr1 = <opClass>.add(q0, <opClass>.add(q1, q2));

    <testOpClass>.checkAlmostEquals(qr0.x(), qr1.x());
    <testOpClass>.checkAlmostEquals(qr0.y(), qr1.y());
    <testOpClass>.checkAlmostEquals(qr0.z(), qr1.z());
    <testOpClass>.checkAlmostEquals(qr0.w(), qr1.w());
  }

  /**
   * ∀ q0 q1. add(q0, q1) == add(q1, q0)
   */

  @Test
  @PercentagePassing
  public void testAddCommutative()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();

    final <quaternionType> qr0 = <opClass>.add(q0, q1);
    final <quaternionType> qr1 = <opClass>.add(q1, q0);

    <testOpClass>.checkAlmostEquals(qr0.x(), qr1.x());
    <testOpClass>.checkAlmostEquals(qr0.y(), qr1.y());
    <testOpClass>.checkAlmostEquals(qr0.z(), qr1.z());
    <testOpClass>.checkAlmostEquals(qr0.w(), qr1.w());
  }

  /**
   * ∀ q0. add(q0, negate(q0)) == (0, 0, 0, 0)
   */

  @Test
  @PercentagePassing
  public void testAddNegationZero()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> qr0 = <opClass>.add(q0, <opClass>.negate(q0));

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.z());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.w());
  }

  @Test
  @PercentagePassing
  public void testConjugate()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();
    final <quaternionType> q0 = gen.next();

    final <quaternionType> q1 = <opClass>.conjugate(q0);
    <testOpClass>.checkAlmostEquals(<testOpClass>.negate(q0.x()), q1.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.negate(q0.y()), q1.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.negate(q0.z()), q1.z());
    <testOpClass>.checkAlmostEquals(q0.w(), q1.w());

    final <quaternionType> q2 = <opClass>.conjugate(q1);
    <testOpClass>.checkAlmostEquals(q0.x(), q2.x());
    <testOpClass>.checkAlmostEquals(q0.y(), q2.y());
    <testOpClass>.checkAlmostEquals(q0.z(), q2.z());
    <testOpClass>.checkAlmostEquals(q0.w(), q2.w());
  }

  @Test
  @PercentagePassing(passPercent = 80.0)
  public void testDotProductSelfMagnitudeSquared()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();

    <testOpClass>.checkAlmostEquals(
      <opClass>.magnitudeSquared(q0),
      <opClass>.dotProduct(q0, q0));
  }

  @Test
  @PercentagePassing
  public void testInterpolateLinearLimits()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();

    final <quaternionType> q2 =
      <opClass>.interpolateLinear(q0, q1, <testOpClass>.constant("0"));

    <testOpClass>.checkAlmostEquals(q0.x(), q2.x());
    <testOpClass>.checkAlmostEquals(q0.y(), q2.y());
    <testOpClass>.checkAlmostEquals(q0.z(), q2.z());
    <testOpClass>.checkAlmostEquals(q0.w(), q2.w());

    final <quaternionType> v3 =
      <opClass>.interpolateLinear(q0, q1, <testOpClass>.constant("1"));

    <testOpClass>.checkAlmostEquals(q1.x(), v3.x());
    <testOpClass>.checkAlmostEquals(q1.y(), v3.y());
    <testOpClass>.checkAlmostEquals(q1.z(), v3.z());
    <testOpClass>.checkAlmostEquals(q1.w(), v3.w());
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

  @Test
  public void testMagnitudeSquaredIdentity()
  {
    <testOpClass>.checkAlmostEquals(
      <testOpClass>.constant("1"),
      <opClass>.magnitudeSquared(<opClass>.identity()));
  }

  @Test
  public void testMagnitudeIdentity()
  {
    <testOpClass>.checkAlmostEquals(
      <testOpClass>.constant("1"),
      <opClass>.magnitude(<opClass>.identity()));
  }

  @Test
  @PercentagePassing
  public void testMagnitudeNormalizeOne()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> vn = <opClass>.normalize(q0);
    final <scalarRealType> m = <opClass>.magnitude(vn);

    if (<testOpClass>.compareReal(m, <testOpClass>.constant("0")) > 0) {
      <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), m);
    } else {
      Assert.assertEquals(q0, vn);
    }
  }

  /**
   * ∀ q0 q1 q2. multiply(multiply(q0, q1), q2) == multiply(q0, multiply(q1, q2))
   */

  @Test
  @PercentagePassing
  public void testMultiplyAssociative()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();
    final <quaternionType> q2 = gen.next();

    final <quaternionType> qr0 = <opClass>.multiply(<opClass>.multiply(q0, q1), q2);
    final <quaternionType> qr1 = <opClass>.multiply(q0, <opClass>.multiply(q1, q2));

    <testOpClass>.checkAlmostEquals(qr0.x(), qr1.x());
    <testOpClass>.checkAlmostEquals(qr0.y(), qr1.y());
    <testOpClass>.checkAlmostEquals(qr0.z(), qr1.z());
    <testOpClass>.checkAlmostEquals(qr0.w(), qr1.w());
  }

  /**
   * ∀ q0. multiply(q0, conjugate(q0)) == (0, 0, 0, 1)
   */

  @Test
  @PercentagePassing
  public void testMultiplyConjugateIdentity()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = <opClass>.normalize(gen.next());
    final <quaternionType> qr0 = <opClass>.normalize(<opClass>.multiply(q0, <opClass>.conjugate(q0)));

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), qr0.z());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), qr0.w());
  }

  @Test
  public void testNormalizeZero()
  {
    Assert.assertEquals(<opClass>.zero(), <opClass>.normalize(<opClass>.zero()));
  }

  /**
   * ∀ q0 x y z. normalize(ofAxisAngle(x, y, z, 0)) == (0, 0, 0, 1)
   */

  @Test
  @PercentagePassing
  public void testOfAxisAngleIdentity()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 =
      <opClass>.normalize(gen.next());
    final <quaternionType> q1 =
      <opClass>.normalize(
        <opClass>.ofAxisAngle(
          q0.x(), q0.y(), q0.z(), <testOpClass>.constant("0")));

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.z());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), q1.w());
  }

  @Test
  public void testMakeAxisAngleX_45()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("1"),
        <testOpClass>.constant("0"),
        <testOpClass>.constant("0"),
        <testOpClass>.radiansOfDegrees("45.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.3826834323650898, q.x());
    <testOpClass>.checkAlmostEquals(0.0f,               q.y());
    <testOpClass>.checkAlmostEquals(0.0f,               q.z());
    <testOpClass>.checkAlmostEquals(0.9238795325112867, q.w());
  }

  @Test
  public void testMakeAxisAngleX_90()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("1"),
        <testOpClass>.constant("0"),
        <testOpClass>.constant("0"),
        <testOpClass>.radiansOfDegrees("90.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.x());
    <testOpClass>.checkAlmostEquals(0.0,                q.y());
    <testOpClass>.checkAlmostEquals(0.0,                q.z());
    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.w());
  }

  @Test
  public void testMakeAxisAngleY_45()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("0"),
        <testOpClass>.radiansOfDegrees("45.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.0,                q.x());
    <testOpClass>.checkAlmostEquals(0.3826834323650898, q.y());
    <testOpClass>.checkAlmostEquals(0.0,                q.z());
    <testOpClass>.checkAlmostEquals(0.9238795325112867, q.w());
  }

  @Test
  public void testMakeAxisAngleY_90()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("0"),
        <testOpClass>.radiansOfDegrees("90.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.0,                q.x());
    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.y());
    <testOpClass>.checkAlmostEquals(0.0,                q.z());
    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.w());
  }

  @Test
  public void testMakeAxisAngleZ_45()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.radiansOfDegrees("45.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.0,                q.x());
    <testOpClass>.checkAlmostEquals(0.0,                q.y());
    <testOpClass>.checkAlmostEquals(0.3826834323650898, q.z());
    <testOpClass>.checkAlmostEquals(0.9238795325112867, q.w());
  }

  @Test
  public void testMakeAxisAngleZ_90()
  {
    final <quaternionType> q =
      <opClass>.ofAxisAngle(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.radiansOfDegrees("90.0"));

    /*
     * Values obtained by checking against the results produced by Blender.
     *
     * @see http://blender.org
     */

    <testOpClass>.checkAlmostEquals(0.0,                q.x());
    <testOpClass>.checkAlmostEquals(0.0,                q.y());
    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.z());
    <testOpClass>.checkAlmostEquals(0.7071067811865475, q.w());
  }

  @Test
  @PercentagePassing
  public void testScale()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <scalarRealType> r = gen.next().x();

    <quaternionComponentType> ex = (<quaternionComponentType>) <testOpClass>.multiplyReal(q0.x(), r);
    <quaternionComponentType> ey = (<quaternionComponentType>) <testOpClass>.multiplyReal(q0.y(), r);
    <quaternionComponentType> ez = (<quaternionComponentType>) <testOpClass>.multiplyReal(q0.z(), r);
    <quaternionComponentType> ew = (<quaternionComponentType>) <testOpClass>.multiplyReal(q0.w(), r);

    final <quaternionType> q2 = <opClass>.scale(q0, r);
    <testOpClass>.checkAlmostEquals(ex, q2.x());
    <testOpClass>.checkAlmostEquals(ey, q2.y());
    <testOpClass>.checkAlmostEquals(ez, q2.z());
    <testOpClass>.checkAlmostEquals(ew, q2.w());
  }

  @Test
  @PercentagePassing
  public void testScaleZero()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = <opClass>.scale(q0, <testOpClass>.constant("0"));
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.z());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), q1.w());
  }

  @Test
  @PercentagePassing
  public void testScaleOne()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = <opClass>.scale(q0, <testOpClass>.constant("1"));
    Assert.assertEquals(q0, q1);
  }

  @Test
  @PercentagePassing
  public void testSubtract()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();

    final <quaternionType> q0 = gen.next();
    final <quaternionType> q1 = gen.next();

    <quaternionComponentType> ex = (<quaternionComponentType>) <testOpClass>.subtract(q0.x(), q1.x());
    <quaternionComponentType> ey = (<quaternionComponentType>) <testOpClass>.subtract(q0.y(), q1.y());
    <quaternionComponentType> ez = (<quaternionComponentType>) <testOpClass>.subtract(q0.z(), q1.z());
    <quaternionComponentType> ew = (<quaternionComponentType>) <testOpClass>.subtract(q0.w(), q1.w());

    final <quaternionType> q2 = <opClass>.subtract(q0, q1);
    <testOpClass>.checkAlmostEquals(ex, q2.x());
    <testOpClass>.checkAlmostEquals(ey, q2.y());
    <testOpClass>.checkAlmostEquals(ez, q2.z());
    <testOpClass>.checkAlmostEquals(ew, q2.w());
  }
}
>>