PVector2Test(
  className,
  vectorType,
  vectorReadable2Type,
  scalarType,
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

/**
 * \<p>Unit tests for {@link <vectorType>} values.\</p>
 *
 * @since 8.0.0
 */

public final class <className>
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  @Test
  public void testEqualsHashCodeToString()
  {
    final <vectorType>\<Object> v0 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    final <vectorType>\<Object> v1 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"));

    final <vectorType>\<Object> v2 =
      <vectorType>.of(
        <testOpClass>.constant("10"),
        <testOpClass>.constant("1"));

    final <vectorType>\<Object> v3 =
      <vectorType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("11"));

    Assert.assertEquals(v0, v0);
    Assert.assertEquals(v0, v1);
    Assert.assertNotEquals(v0, v2);
    Assert.assertNotEquals(v0, v3);
    Assert.assertNotEquals(v0, null);
    Assert.assertNotEquals(v0, Integer.valueOf(23));

    Assert.assertEquals(v0.toString(), v1.toString());
    Assert.assertNotEquals(v0.toString(), v2.toString());
    Assert.assertNotEquals(v0.toString(), v3.toString());

    Assert.assertEquals(v0.hashCode(), v1.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v2.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v3.hashCode());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
  }

  @Test
  public void testBuilder()
  {
    final <vectorType>\<Object> v0 =
      <vectorType>.builder()
      .setX(<testOpClass>.constant("0"))
      .setY(<testOpClass>.constant("1"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());

    {
      final <vectorType>\<Object> v1 =
        <vectorType>.builder()
        .from(v0)
        .build();
      Assert.assertEquals(v0, v1);
    }

    {
      final <vectorType>\<Object> v1 =
        <vectorType>.builder()
        .from((<vectorReadable2Type>) v0)
        .build();
      Assert.assertEquals(v0, v1);
    }
  }

  @Test
  public void testBuilderFailure()
  {
    this.expected.expect(IllegalStateException.class);

    final <vectorType>\<Object> v0 =
      <vectorType>.builder()
      .build();

    Assert.fail();
  }

  @Test
  public void testAbsolute()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = <opClass>.absolute(v0);
          <testOpClass>.checkAlmostEquals(<testOpClass>.absolute(v0.x()), v1.x());
          <testOpClass>.checkAlmostEquals(<testOpClass>.absolute(v0.y()), v1.y());
        }
    });
  }

  @Test
  public void testAdd()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = gen.next();

          final <vectorType>\<Object> v2 = <opClass>.add(v0, v1);
          <testOpClass>.checkAlmostEquals(<testOpClass>.add(v0.x(), v1.x()), v2.x());
          <testOpClass>.checkAlmostEquals(<testOpClass>.add(v0.y(), v1.y()), v2.y());
        }
    });
  }

  @Test
  public void testAddScaled()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = gen.next();
          final <scalarType> r = gen.next().x();

          final <vectorType>\<Object> v2 = <opClass>.addScaled(v0, v1, r);
          <testOpClass>.checkAlmostEquals(
            <testOpClass>.add(v0.x(), <testOpClass>.multiply(v1.x(), r)), v2.x());
          <testOpClass>.checkAlmostEquals(
            <testOpClass>.add(v0.y(), <testOpClass>.multiply(v1.y(), r)), v2.y());
        }
    });
  }

  @Test
  public void testDotProductSelfMagnitudeSquared()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          <testOpClass>.checkAlmostEquals(
            <opClass>.magnitudeSquared(v0),
            <opClass>.dotProduct(v0, v0));
        }
    });
  }

  @Test
  public void testInterpolateLinearLimits()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = gen.next();

          final <vectorType>\<Object> v2 =
            <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("0"));

          <testOpClass>.checkAlmostEquals(v0.x(), v2.x());
          <testOpClass>.checkAlmostEquals(v0.y(), v2.y());

          final <vectorType>\<Object> v3 =
            <opClass>.interpolateLinear(v0, v1, <testOpClass>.constant("1"));

          <testOpClass>.checkAlmostEquals(v1.x(), v3.x());
          <testOpClass>.checkAlmostEquals(v1.y(), v3.y());
        }
    });
  }

  @Test
  public void testMagnitudeSquaredZero()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          <testOpClass>.checkAlmostEquals(
            <testOpClass>.constant("0"),
            <opClass>.magnitudeSquared(<opClass>.zero()));
        }
    });
  }

  @Test
  public void testMagnitudeZero()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          <testOpClass>.checkAlmostEquals(
            <testOpClass>.constant("0"),
            <opClass>.magnitude(<opClass>.zero()));
        }
    });
  }

  @Test
  public void testNormalizeZero()
  {
    Assert.assertEquals(<opClass>.zero(), <opClass>.normalize(<opClass>.zero()));
  }

  @Test
  public void testMagnitudeNormalizeOne()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> vn = <opClass>.normalize(v0);
          if (<testOpClass>.compare(<opClass>.magnitude(v0), <testOpClass>.constant("0")) > 0) {
            <testOpClass>.checkAlmostEquals(
              <testOpClass>.constant("1"),
              <opClass>.magnitude(vn));
          } else {
            Assert.assertEquals(v0, vn);
          }
        }
    });
  }

  @Test
  public void testScale()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <scalarType> r = gen.next().x();

          final <vectorType>\<Object> v2 = <opClass>.scale(v0, r);
          <testOpClass>.checkAlmostEquals(<testOpClass>.multiply(v0.x(), r), v2.x());
          <testOpClass>.checkAlmostEquals(<testOpClass>.multiply(v0.y(), r), v2.y());
        }
    });
  }

  @Test
  public void testScaleZero()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = <opClass>.scale(v0, <testOpClass>.constant("0"));
          <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.x());
          <testOpClass>.checkAlmostEquals(<testOpClass>.constant("0"), v1.y());
        }
    });
  }

  @Test
  public void testScaleOne()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = <opClass>.scale(v0, <testOpClass>.constant("1"));
          Assert.assertEquals(v0, v1);
        }
    });
  }

  @Test
  public void testSubtract()
  {
    final Generator\<<vectorType>\<Object\>> gen = <generatorType>.create();

    QuickCheck.forAll(
      gen,
      new AbstractCharacteristic\<<vectorType>\<Object\>>()
      {
        @Override
        protected void doSpecify(final <vectorType>\<Object> v0)
        {
          final <vectorType>\<Object> v1 = gen.next();

          final <vectorType>\<Object> v2 = <opClass>.subtract(v0, v1);
          <testOpClass>.checkAlmostEquals(<testOpClass>.subtract(v0.x(), v1.x()), v2.x());
          <testOpClass>.checkAlmostEquals(<testOpClass>.subtract(v0.y(), v1.y()), v2.y());
        }
    });
  }
}
>>