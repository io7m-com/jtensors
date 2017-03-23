PMatrix4x4Test(
  className,
  matrixType,
  matrixComponentType,
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

package com.io7m.jtensors.tests.core.parameterized.matrixs;

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
  public void testRowOutOfRange0()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(-1, 0);
  }

  @Test
  public void testRowOutOfRange1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(4, 0);
  }

  @Test
  public void testColumnOutOfRange0()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(0, -1);
  }

  @Test
  public void testColumnOutOfRange1()
  {
    final <matrixType>\<Object> m = <opClass>.identity();

    this.expected.expect(IndexOutOfBoundsException.class);
    m.rowColumn(0, 4);
  }
}
>>