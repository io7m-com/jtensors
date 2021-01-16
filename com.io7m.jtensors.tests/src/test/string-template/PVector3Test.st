PVector3Test(
  className,
  vectorType,
  vectorConversionType,
  vectorComponentType,
  vectorReadable3Type,
  vectorReadable2Type,
  scalarRealType,
  generatorType,
  testOpClass,
  opClass,
  hasGeneric,
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

import java.util.Objects;
import com.io7m.junreachable.UnreachableCodeException;

import net.java.quickcheck.Generator;
import net.java.quickcheck.QuickCheck;
import net.java.quickcheck.characteristic.AbstractCharacteristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.io7m.percentpass.extension.PercentPassing;

/**
 * \<p>Unit tests for {@link <vectorType>} values.\</p>
 *
 * @since 8.0.0
 */

public final class <className>
{
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
    final var v0 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final var v1 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final var v2 =
      <vectorType>.of(
        <testOpClass>.constant("10"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"));

    final var v3 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("11"),
        <testOpClass>.constant("2"));

    final var v4 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("12"));

    Assertions.assertEquals(v0, v0);
    Assertions.assertEquals(v0, v1);
    Assertions.assertNotEquals(v0, v2);
    Assertions.assertNotEquals(v0, v3);
    Assertions.assertNotEquals(v0, v4);
    Assertions.assertNotEquals(v0, null);
    Assertions.assertNotEquals(v0, Integer.valueOf(23));

    Assertions.assertEquals(v0.toString(), v1.toString());
    Assertions.assertNotEquals(v0.toString(), v2.toString());
    Assertions.assertNotEquals(v0.toString(), v3.toString());
    Assertions.assertNotEquals(v0.toString(), v4.toString());

    Assertions.assertEquals(v0.hashCode(), v1.hashCode());
    Assertions.assertNotEquals(v0.hashCode(), v2.hashCode());
    Assertions.assertNotEquals(v0.hashCode(), v3.hashCode());
    Assertions.assertNotEquals(v0.hashCode(), v4.hashCode());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());
  }

  @Test
  public void testBuilder()
  {
    final var v0 =
      <vectorType>.builder()
      .setX(<testOpClass>.constant("0"))
      .setY(<testOpClass>.constant("1"))
      .setZ(<testOpClass>.constant("2"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());

    {
      final var v1 =
        <vectorType>.builder()
        .from(v0)
        .build();
      Assertions.assertEquals(v0, v1);
    }

    {
      final var v1 =
        <vectorType>.builder()
        .from((<vectorReadable3Type>) v0)
        .build();
      Assertions.assertEquals(v0, v1);
    }

    {
      final var v1 =
        <vectorType>.builder()
        .setZ(<testOpClass>.constant("2"))
        .from((<vectorReadable2Type>) v0)
        .build();
      Assertions.assertEquals(v0, v1);
    }
  }

  @Test
  public void testBuilderFailure()
  {
    Assertions.assertThrows(IllegalStateException.class, () -> {
        final var v0 =
          <vectorType>.builder()
          .build();
    });
  }

  @PercentPassing
  public void testAbsolute()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = <opClass>.absolute(v0);

    var ex = (<vectorComponentType>) <testOpClass>.absolute(v0.x());
    var ey = (<vectorComponentType>) <testOpClass>.absolute(v0.y());
    var ez = (<vectorComponentType>) <testOpClass>.absolute(v0.z());

    <testOpClass>.checkAlmostEquals(ex, v1.x());
    <testOpClass>.checkAlmostEquals(ey, v1.y());
    <testOpClass>.checkAlmostEquals(ez, v1.z());
  }

  @PercentPassing
  public void testAdd()
  {
    final Generator\<<vectorType>\> gen = createGenerator();
    final var v0 = gen.next();
    final var v1 = gen.next();

    var ex = (<vectorComponentType>) <testOpClass>.add(v0.x(), v1.x());
    var ey = (<vectorComponentType>) <testOpClass>.add(v0.y(), v1.y());
    var ez = (<vectorComponentType>) <testOpClass>.add(v0.z(), v1.z());

    final var v2 = <opClass>.add(v0, v1);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  /**
   * ∀ v0 v1 v2. add(add(v0, v1), v2) == add(v0, add(v1, v2))
   */

  @PercentPassing
  public void testAddAssociative()
  {
    final Generator\<<vectorType>\> gen = createGeneratorSmall();

    final var v0 = gen.next();
    final var v1 = gen.next();
    final var v2 = gen.next();

    final var vr0 = <opClass>.add(<opClass>.add(v0, v1), v2);
    final var vr1 = <opClass>.add(v0, <opClass>.add(v1, v2));

    <testOpClass>.checkAlmostEqualsVague(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEqualsVague(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEqualsVague(vr0.z(), vr1.z());
  }

  /**
   * ∀ v0 v1. add(v0, v1) == add(v1, v0)
   */

  @PercentPassing
  public void testAddCommutative()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = gen.next();

    final var vr0 = <opClass>.add(v0, v1);
    final var vr1 = <opClass>.add(v1, v0);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ v0. add(v0, negate(v0)) == (0, 0, 0)
   */

  @PercentPassing
  public void testAddNegationZero()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var vr0 = <opClass>.add(v0, <opClass>.negate(v0));

    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), vr0.z());
  }

  @PercentPassing
  public void testAddScaled()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = gen.next();
    final var r = gen.next().x();

    var mx = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.x(), r);
    var my = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.y(), r);
    var mz = (<vectorComponentType>) <testOpClass>.multiplyReal(v1.z(), r);

    var ex = (<vectorComponentType>) <testOpClass>.add(v0.x(), mx);
    var ey = (<vectorComponentType>) <testOpClass>.add(v0.y(), my);
    var ez = (<vectorComponentType>) <testOpClass>.add(v0.z(), mz);

    final var v2 = <opClass>.addScaled(v0, v1, r);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  @Test
  public void testClampMinimum()
  {
    final var v = <vectorType>.of(
      <testOpClass>.constant("0"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("2"));

    final var v_min = <vectorType>.of(
      <testOpClass>.constant("10"),
      <testOpClass>.constant("11"),
      <testOpClass>.constant("12"));

    final var v_max = <vectorType>.of(
      <testOpClass>.constant("110"),
      <testOpClass>.constant("111"),
      <testOpClass>.constant("112"));

    final var vr =
      <opClass>.clamp(v, v_min, v_max);

    <testOpClass>.checkEquals(v_min.x(), vr.x());
    <testOpClass>.checkEquals(v_min.y(), vr.y());
    <testOpClass>.checkEquals(v_min.z(), vr.z());
  }

  @Test
  public void testClampMaximum()
  {
    final var v = <vectorType>.of(
      <testOpClass>.constant("1110"),
      <testOpClass>.constant("1111"),
      <testOpClass>.constant("1112"));

    final var v_min = <vectorType>.of(
      <testOpClass>.constant("10"),
      <testOpClass>.constant("11"),
      <testOpClass>.constant("12"));

    final var v_max = <vectorType>.of(
      <testOpClass>.constant("110"),
      <testOpClass>.constant("111"),
      <testOpClass>.constant("112"));

    final var vr =
      <opClass>.clamp(v, v_min, v_max);

    <testOpClass>.checkEquals(v_max.x(), vr.x());
    <testOpClass>.checkEquals(v_max.y(), vr.y());
    <testOpClass>.checkEquals(v_max.z(), vr.z());
  }

  @Test
  public void testClampMixed()
  {
    final var v = <vectorType>.of(
      <testOpClass>.constant("1110"),
      <testOpClass>.constant("1"),
      <testOpClass>.constant("1112"));

    final var v_min = <vectorType>.of(
      <testOpClass>.constant("10"),
      <testOpClass>.constant("11"),
      <testOpClass>.constant("12"));

    final var v_max = <vectorType>.of(
      <testOpClass>.constant("110"),
      <testOpClass>.constant("111"),
      <testOpClass>.constant("112"));

    final var vr =
      <opClass>.clamp(v, v_min, v_max);

    <testOpClass>.checkEquals(v_max.x(), vr.x());
    <testOpClass>.checkEquals(v_min.y(), vr.y());
    <testOpClass>.checkEquals(v_max.z(), vr.z());
  }

  /**
   * ∀ a b. crossProduct(a, b) == crossProduct(negate(b), a)
   */

  @PercentPassing
  public void testCrossProductAnticommutative()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var a = gen.next();
    final var b = gen.next();

    final var vr0 = <opClass>.crossProduct(a, b);
    final var vr1 = <opClass>.crossProduct(<opClass>.negate(b), a);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. crossProduct(a, add(b, c)) == add(crossProduct(a, b), crossProduct(a, c))
   */

  @PercentPassing(passPercent = 97)
  public void testCrossProductDistributive()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var a = gen.next();
    final var b = gen.next();
    final var c = gen.next();

    final var vr0 =
      <opClass>.crossProduct(a, <opClass>.add(b, c));
    final var vr1 =
      <opClass>.add(<opClass>.crossProduct(a, b), <opClass>.crossProduct(a, c));

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. crossProduct(scale(a, c), b) == scale(crossProduct(a, b), c)
   */

  @PercentPassing
  public void testCrossProductScale0()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var a = gen.next();
    final var b = gen.next();
    final var c = Math.random();

    final var vr0 =
      <opClass>.crossProduct(<opClass>.scale(a, c), b);
    final var vr1 =
      <opClass>.scale(<opClass>.crossProduct(a, b), c);

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  /**
   * ∀ a b c. scale(crossProduct(a, b), c) == crossProduct(a, scale(b, c))
   */

  @PercentPassing
  public void testCrossProductScale1()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var a = gen.next();
    final var b = gen.next();
    final var c = Math.random();

    final var vr0 =
      <opClass>.scale(<opClass>.crossProduct(a, b), c);
    final var vr1 =
      <opClass>.crossProduct(a, <opClass>.scale(b, c));

    <testOpClass>.checkAlmostEquals(vr0.x(), vr1.x());
    <testOpClass>.checkAlmostEquals(vr0.y(), vr1.y());
    <testOpClass>.checkAlmostEquals(vr0.z(), vr1.z());
  }

  @PercentPassing
  public void testDistanceZero()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();

    <testOpClass>.checkAlmostEquals(
      <testOpClass>.constant("0"),
      <opClass>.distance(v0, v0));
  }

  @PercentPassing
  public void testDotProductSelfMagnitudeSquared()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();

    <testOpClass>.checkAlmostEquals(
      <opClass>.magnitudeSquared(v0),
      <opClass>.dotProduct(v0, v0));
  }

  @PercentPassing
  public void testInterpolateLinearLimits()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = gen.next();

    final var v2 =
      <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("0"));

    <testOpClass>.checkAlmostEquals(v0.x(), v2.x());
    <testOpClass>.checkAlmostEquals(v0.y(), v2.y());
    <testOpClass>.checkAlmostEquals(v0.z(), v2.z());

    final var v3 =
      <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("1"));

    <testOpClass>.checkAlmostEquals(v1.x(), v3.x());
    <testOpClass>.checkAlmostEquals(v1.y(), v3.y());
    <testOpClass>.checkAlmostEquals(v1.z(), v3.z());
  }

  @PercentPassing
  public void testInterpolateBilinearLimitsX0Y0()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var x0y0 = gen.next();
    final var x1y0 = gen.next();
    final var x0y1 = gen.next();
    final var x1y1 = gen.next();

    final var r =
      <opClass>.interpolateBilinear(
        x0y0,
        x1y0,
        x0y1,
        x1y1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("0"));

    System.out.println("--");
    System.out.println("x0y0:     " + x0y0);
    System.out.println("x0y1:     " + x0y1);
    System.out.println("x1y0:     " + x1y0);
    System.out.println("x1y1:     " + x1y1);
    System.out.println("r (x0y0): " + r);

    <testOpClass>.checkAlmostEquals(x0y0.x(), r.x());
    <testOpClass>.checkAlmostEquals(x0y0.y(), r.y());
    <testOpClass>.checkAlmostEquals(x0y0.z(), r.z());
  }

  @PercentPassing
  public void testInterpolateBilinearLimitsX1Y0()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var x0y0 = gen.next();
    final var x1y0 = gen.next();
    final var x0y1 = gen.next();
    final var x1y1 = gen.next();

    final var r =
      <opClass>.interpolateBilinear(
        x0y0,
        x1y0,
        x0y1,
        x1y1,
        <testOpClass>.constant("1"),
        <testOpClass>.constant("0"));

    System.out.println("--");
    System.out.println("x0y0:     " + x0y0);
    System.out.println("x0y1:     " + x0y1);
    System.out.println("x1y0:     " + x1y0);
    System.out.println("x1y1:     " + x1y1);
    System.out.println("r (x1y0): " + r);

    <testOpClass>.checkAlmostEquals(x1y0.x(), r.x());
    <testOpClass>.checkAlmostEquals(x1y0.y(), r.y());
    <testOpClass>.checkAlmostEquals(x1y0.z(), r.z());
  }

  @PercentPassing
  public void testInterpolateBilinearLimitsX0Y1()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var x0y0 = gen.next();
    final var x1y0 = gen.next();
    final var x0y1 = gen.next();
    final var x1y1 = gen.next();

    final var r =
      <opClass>.interpolateBilinear(
        x0y0,
        x1y0,
        x0y1,
        x1y1,
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    System.out.println("--");
    System.out.println("x0y0:     " + x0y0);
    System.out.println("x0y1:     " + x0y1);
    System.out.println("x1y0:     " + x1y0);
    System.out.println("x1y1:     " + x1y1);
    System.out.println("r (x0y1): " + r);

    <testOpClass>.checkAlmostEquals(x0y1.x(), r.x());
    <testOpClass>.checkAlmostEquals(x0y1.y(), r.y());
    <testOpClass>.checkAlmostEquals(x0y1.z(), r.z());
  }

  @PercentPassing
  public void testInterpolateBilinearLimitsX1Y1()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var x0y0 = gen.next();
    final var x1y0 = gen.next();
    final var x0y1 = gen.next();
    final var x1y1 = gen.next();

    final var r =
      <opClass>.interpolateBilinear(
        x0y0,
        x1y0,
        x0y1,
        x1y1,
        <testOpClass>.constant("1"),
        <testOpClass>.constant("1"));

    System.out.println("--");
    System.out.println("x0y0:     " + x0y0);
    System.out.println("x0y1:     " + x0y1);
    System.out.println("x1y0:     " + x1y0);
    System.out.println("x1y1:     " + x1y1);
    System.out.println("r (x1y1): " + r);

    <testOpClass>.checkAlmostEquals(x1y1.x(), r.x());
    <testOpClass>.checkAlmostEquals(x1y1.y(), r.y());
    <testOpClass>.checkAlmostEquals(x1y1.z(), r.z());
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
    Assertions.assertEquals(<opClass>.zero(), <opClass>.normalize(<opClass>.zero()));
  }

  @PercentPassing
  public void testMagnitudeNormalizeOne()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var vn = <opClass>.normalize(v0);
    final var m = <opClass>.magnitude(vn);

    if (<testOpClass>.compareReal(m, <testOpClass>.constant("0")) > 0) {
      <testOpClass>.checkAlmostEquals(<testOpClass>.constant("1"), m);
    } else {
      Assertions.assertEquals(v0, vn);
    }
  }
  <endif>

  @PercentPassing
  public void testScale()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var r = gen.next().x();

    var ex = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.x(), r);
    var ey = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.y(), r);
    var ez = (<vectorComponentType>) <testOpClass>.multiplyReal(v0.z(), r);

    final var v2 = <opClass>.scale(v0, r);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  @PercentPassing
  public void testScaleZero()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = <opClass>.scale(v0, <testOpClass>.constant("0"));
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.x());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.y());
    <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.z());
  }

  @PercentPassing
  public void testScaleOne()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = <opClass>.scale(v0, <testOpClass>.constant("1"));
    Assertions.assertEquals(v0, v1);
  }

  @PercentPassing
  public void testSubtract()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = gen.next();

    var ex = (<vectorComponentType>) <testOpClass>.subtract(v0.x(), v1.x());
    var ey = (<vectorComponentType>) <testOpClass>.subtract(v0.y(), v1.y());
    var ez = (<vectorComponentType>) <testOpClass>.subtract(v0.z(), v1.z());

    final var v2 = <opClass>.subtract(v0, v1);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
  }

  @PercentPassing
  public void testWithXYZ()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = gen.next();
    final var v2 = v0.withX(v1.x()).withY(v1.y()).withZ(v1.z());
    Assertions.assertEquals(v1, v2);
  }

  @PercentPassing
  public void testCopyOf()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var v0 = gen.next();
    final var v1 = <vectorType>.copyOf(v0);
    Assertions.assertEquals(v0, v1);
  }

  <if(hasGeneric)>
  @PercentPassing
  public void testConversionsParameterized()
  {
    final Generator\<<vectorType>\> gen = createGenerator();

    final var a = gen.next();
    final <vectorConversionType> b = <opClass>.toUnparameterized(a);
    final var c = <opClass>.toParameterized(b);

    Assertions.assertEquals(a.x(), b.x());
    Assertions.assertEquals(a.y(), b.y());
    Assertions.assertEquals(a.z(), b.z());
    Assertions.assertEquals(a, c);
  }
  <endif>
}
>>