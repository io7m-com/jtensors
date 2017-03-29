Quaternion4Test(
  className,
  quaternionType,
  quaternionComponentType,
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
    final <quaternionType> v0 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> v1 =
      <quaternionType>.of(
        <testOpClass>.constant("0"),
        <testOpClass>.constant("1"),
        <testOpClass>.constant("2"),
        <testOpClass>.constant("3"));

    final <quaternionType> v2 =
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

    Assert.assertEquals(v0, v0);
    Assert.assertEquals(v0, v1);
    Assert.assertNotEquals(v0, v2);
    Assert.assertNotEquals(v0, v3);
    Assert.assertNotEquals(v0, v4);
    Assert.assertNotEquals(v0, v5);
    Assert.assertNotEquals(v0, null);
    Assert.assertNotEquals(v0, Integer.valueOf(23));

    Assert.assertEquals(v0.toString(), v1.toString());
    Assert.assertNotEquals(v0.toString(), v2.toString());
    Assert.assertNotEquals(v0.toString(), v3.toString());
    Assert.assertNotEquals(v0.toString(), v4.toString());
    Assert.assertNotEquals(v0.toString(), v5.toString());

    Assert.assertEquals(v0.hashCode(), v1.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v2.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v3.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v4.hashCode());
    Assert.assertNotEquals(v0.hashCode(), v5.hashCode());

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), v0.w());
  }

  @Test
  public void testBuilder()
  {
    final <quaternionType> v0 =
      <quaternionType>.builder()
      .setX(<testOpClass>.constant("0"))
      .setY(<testOpClass>.constant("1"))
      .setZ(<testOpClass>.constant("2"))
      .setW(<testOpClass>.constant("3"))
      .build();

    <testOpClass>.checkEquals(<testOpClass>.constant("0"), v0.x());
    <testOpClass>.checkEquals(<testOpClass>.constant("1"), v0.y());
    <testOpClass>.checkEquals(<testOpClass>.constant("2"), v0.z());
    <testOpClass>.checkEquals(<testOpClass>.constant("3"), v0.w());

    {
      final <quaternionType> v1 =
        <quaternionType>.builder()
        .from(v0)
        .build();
      Assert.assertEquals(v0, v1);
    }
  }

  @Test
  public void testBuilderFailure()
  {
    this.expected.expect(IllegalStateException.class);

    final <quaternionType> v0 =
      <quaternionType>.builder()
      .build();

    Assert.fail();
  }

  @Test
  @PercentagePassing
  public void testAdd()
  {
    final Generator\<<quaternionType>\> gen = createGenerator();
    final <quaternionType> v0 = gen.next();
    final <quaternionType> v1 = gen.next();

    <quaternionComponentType> ex = (<quaternionComponentType>) <testOpClass>.add(v0.x(), v1.x());
    <quaternionComponentType> ey = (<quaternionComponentType>) <testOpClass>.add(v0.y(), v1.y());
    <quaternionComponentType> ez = (<quaternionComponentType>) <testOpClass>.add(v0.z(), v1.z());
    <quaternionComponentType> ew = (<quaternionComponentType>) <testOpClass>.add(v0.w(), v1.w());

    final <quaternionType> v2 = <opClass>.add(v0, v1);
    <testOpClass>.checkAlmostEquals(ex, v2.x());
    <testOpClass>.checkAlmostEquals(ey, v2.y());
    <testOpClass>.checkAlmostEquals(ez, v2.z());
    <testOpClass>.checkAlmostEquals(ew, v2.w());
  }
}
>>