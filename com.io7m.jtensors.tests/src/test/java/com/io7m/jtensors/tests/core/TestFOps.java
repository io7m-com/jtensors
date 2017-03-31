/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.core;

import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3F;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4F;
import com.io7m.jtensors.core.parameterized.vectors.PVector3F;
import com.io7m.jtensors.core.parameterized.vectors.PVector4F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3F;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4F;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class TestFOps
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(TestFOps.class);
  }

  private TestFOps()
  {
    throw new UnreachableCodeException();
  }

  public static <T> void checkObjectEquals(
    final T x,
    final T y)
  {
    if (!Objects.equals(x, y)) {
      LOG.error("expected: {}", x);
      LOG.error("received: {}", y);
    }

    Assert.assertEquals(x, y);
  }

  public static <T> void checkObjectNotEquals(
    final T x,
    final T y)
  {
    if (Objects.equals(x, y)) {
      LOG.error("expected: {}", x);
      LOG.error("received: {}", y);
    }

    Assert.assertNotEquals(x, y);
  }

  public static void checkEquals(
    final double x,
    final double y)
  {
    Assert.assertEquals(x, y, 0.0);
  }

  public static void checkAlmostEquals(
    final double x,
    final double y)
  {
    Assert.assertEquals(x, y, 1.0e-6);
  }

  public static void checkAlmostEqualsVague(
    final double x,
    final double y)
  {
    Assert.assertEquals(x, y, 0.1);
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix4x4D m0,
    final Matrix4x4D m1)
  {
    try {
      Assert.assertEquals(m0.r0c0(), m1.r0c0(), 1.0e-6);
      Assert.assertEquals(m0.r0c1(), m1.r0c1(), 1.0e-6);
      Assert.assertEquals(m0.r0c2(), m1.r0c2(), 1.0e-6);
      Assert.assertEquals(m0.r0c3(), m1.r0c3(), 1.0e-6);

      Assert.assertEquals(m0.r1c0(), m1.r1c0(), 1.0e-6);
      Assert.assertEquals(m0.r1c1(), m1.r1c1(), 1.0e-6);
      Assert.assertEquals(m0.r1c2(), m1.r1c2(), 1.0e-6);
      Assert.assertEquals(m0.r1c3(), m1.r1c3(), 1.0e-6);

      Assert.assertEquals(m0.r2c0(), m1.r2c0(), 1.0e-6);
      Assert.assertEquals(m0.r2c1(), m1.r2c1(), 1.0e-6);
      Assert.assertEquals(m0.r2c2(), m1.r2c2(), 1.0e-6);
      Assert.assertEquals(m0.r2c3(), m1.r2c3(), 1.0e-6);

      Assert.assertEquals(m0.r3c0(), m1.r3c0(), 1.0e-6);
      Assert.assertEquals(m0.r3c1(), m1.r3c1(), 1.0e-6);
      Assert.assertEquals(m0.r3c2(), m1.r3c2(), 1.0e-6);
      Assert.assertEquals(m0.r3c3(), m1.r3c3(), 1.0e-6);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", m0);
      LOG.error("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix4x4F m0,
    final Matrix4x4F m1)
  {
    try {
      Assert.assertEquals(m0.r0c0(), m1.r0c0(), 1.0e-3);
      Assert.assertEquals(m0.r0c1(), m1.r0c1(), 1.0e-3);
      Assert.assertEquals(m0.r0c2(), m1.r0c2(), 1.0e-3);
      Assert.assertEquals(m0.r0c3(), m1.r0c3(), 1.0e-3);

      Assert.assertEquals(m0.r1c0(), m1.r1c0(), 1.0e-3);
      Assert.assertEquals(m0.r1c1(), m1.r1c1(), 1.0e-3);
      Assert.assertEquals(m0.r1c2(), m1.r1c2(), 1.0e-3);
      Assert.assertEquals(m0.r1c3(), m1.r1c3(), 1.0e-3);

      Assert.assertEquals(m0.r2c0(), m1.r2c0(), 1.0e-3);
      Assert.assertEquals(m0.r2c1(), m1.r2c1(), 1.0e-3);
      Assert.assertEquals(m0.r2c2(), m1.r2c2(), 1.0e-3);
      Assert.assertEquals(m0.r2c3(), m1.r2c3(), 1.0e-3);

      Assert.assertEquals(m0.r3c0(), m1.r3c0(), 1.0e-3);
      Assert.assertEquals(m0.r3c1(), m1.r3c1(), 1.0e-3);
      Assert.assertEquals(m0.r3c2(), m1.r3c2(), 1.0e-3);
      Assert.assertEquals(m0.r3c3(), m1.r3c3(), 1.0e-3);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", m0);
      LOG.error("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final PMatrix4x4F<?, ?> m0,
    final PMatrix4x4F<?, ?> m1)
  {
    try {
      Assert.assertEquals(m0.r0c0(), m1.r0c0(), 1.0e-3);
      Assert.assertEquals(m0.r0c1(), m1.r0c1(), 1.0e-3);
      Assert.assertEquals(m0.r0c2(), m1.r0c2(), 1.0e-3);
      Assert.assertEquals(m0.r0c3(), m1.r0c3(), 1.0e-3);

      Assert.assertEquals(m0.r1c0(), m1.r1c0(), 1.0e-3);
      Assert.assertEquals(m0.r1c1(), m1.r1c1(), 1.0e-3);
      Assert.assertEquals(m0.r1c2(), m1.r1c2(), 1.0e-3);
      Assert.assertEquals(m0.r1c3(), m1.r1c3(), 1.0e-3);

      Assert.assertEquals(m0.r2c0(), m1.r2c0(), 1.0e-3);
      Assert.assertEquals(m0.r2c1(), m1.r2c1(), 1.0e-3);
      Assert.assertEquals(m0.r2c2(), m1.r2c2(), 1.0e-3);
      Assert.assertEquals(m0.r2c3(), m1.r2c3(), 1.0e-3);

      Assert.assertEquals(m0.r3c0(), m1.r3c0(), 1.0e-3);
      Assert.assertEquals(m0.r3c1(), m1.r3c1(), 1.0e-3);
      Assert.assertEquals(m0.r3c2(), m1.r3c2(), 1.0e-3);
      Assert.assertEquals(m0.r3c3(), m1.r3c3(), 1.0e-3);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", m0);
      LOG.error("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix3x3F m0,
    final Matrix3x3F m1)
  {
    try {
      Assert.assertEquals(m0.r0c0(), m1.r0c0(), 1.0e-3);
      Assert.assertEquals(m0.r0c1(), m1.r0c1(), 1.0e-3);
      Assert.assertEquals(m0.r0c2(), m1.r0c2(), 1.0e-3);

      Assert.assertEquals(m0.r1c0(), m1.r1c0(), 1.0e-3);
      Assert.assertEquals(m0.r1c1(), m1.r1c1(), 1.0e-3);
      Assert.assertEquals(m0.r1c2(), m1.r1c2(), 1.0e-3);

      Assert.assertEquals(m0.r2c0(), m1.r2c0(), 1.0e-3);
      Assert.assertEquals(m0.r2c1(), m1.r2c1(), 1.0e-3);
      Assert.assertEquals(m0.r2c2(), m1.r2c2(), 1.0e-3);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", m0);
      LOG.error("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final PMatrix3x3F<?, ?> m0,
    final PMatrix3x3F<?, ?> m1)
  {
    try {
      Assert.assertEquals(m0.r0c0(), m1.r0c0(), 1.0e-3);
      Assert.assertEquals(m0.r0c1(), m1.r0c1(), 1.0e-3);
      Assert.assertEquals(m0.r0c2(), m1.r0c2(), 1.0e-3);

      Assert.assertEquals(m0.r1c0(), m1.r1c0(), 1.0e-3);
      Assert.assertEquals(m0.r1c1(), m1.r1c1(), 1.0e-3);
      Assert.assertEquals(m0.r1c2(), m1.r1c2(), 1.0e-3);

      Assert.assertEquals(m0.r2c0(), m1.r2c0(), 1.0e-3);
      Assert.assertEquals(m0.r2c1(), m1.r2c1(), 1.0e-3);
      Assert.assertEquals(m0.r2c2(), m1.r2c2(), 1.0e-3);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", m0);
      LOG.error("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final Vector3F v0,
    final Vector3F v1)
  {
    try {
      Assert.assertEquals(v0.x(), v1.x(), 1.0e-6);
      Assert.assertEquals(v0.y(), v1.y(), 1.0e-6);
      Assert.assertEquals(v0.z(), v1.z(), 1.0e-6);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", v0);
      LOG.error("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final PVector3F<?> v0,
    final PVector3F<?> v1)
  {
    try {
      Assert.assertEquals(v0.x(), v1.x(), 1.0e-6);
      Assert.assertEquals(v0.y(), v1.y(), 1.0e-6);
      Assert.assertEquals(v0.z(), v1.z(), 1.0e-6);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", v0);
      LOG.error("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final Vector4F v0,
    final Vector4F v1)
  {
    try {
      Assert.assertEquals(v0.x(), v1.x(), 1.0e-6);
      Assert.assertEquals(v0.y(), v1.y(), 1.0e-6);
      Assert.assertEquals(v0.z(), v1.z(), 1.0e-6);
      Assert.assertEquals(v0.w(), v1.w(), 1.0e-6);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", v0);
      LOG.error("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final PVector4F<?> v0,
    final PVector4F<?> v1)
  {
    try {
      Assert.assertEquals(v0.x(), v1.x(), 1.0e-6);
      Assert.assertEquals(v0.y(), v1.y(), 1.0e-6);
      Assert.assertEquals(v0.z(), v1.z(), 1.0e-6);
      Assert.assertEquals(v0.w(), v1.w(), 1.0e-6);
    } catch (final AssertionError e) {
      LOG.error("expected: {}", v0);
      LOG.error("received: {}", v1);
      throw e;
    }
  }

  private static boolean doubleIsDifferent(
    final double d1,
    final double d2,
    final double delta)
  {
    if (Double.compare(d1, d2) == 0) {
      return false;
    }
    if ((Math.abs(d1 - d2) <= delta)) {
      return false;
    }

    return true;
  }

  public static boolean almostEquals(
    final double x,
    final double y)
  {
    return !doubleIsDifferent(x, y, 1.0e-6);
  }

  public static float radiansOfDegrees(
    final String text)
  {
    return (float) Math.toRadians(Double.parseDouble(text));
  }

  public static float constant(
    final String text)
  {
    return Float.parseFloat(text);
  }

  public static double realConstant(
    final String text)
  {
    return Double.parseDouble(text);
  }

  public static double absolute(
    final double x)
  {
    return Math.abs(x);
  }

  public static double negate(
    final double x)
  {
    return -x;
  }

  public static double add(
    final double x,
    final double y)
  {
    return x + y;
  }

  public static double multiply(
    final double x,
    final double y)
  {
    return x * y;
  }

  public static double multiplyReal(
    final double x,
    final double y)
  {
    return x * y;
  }

  public static double subtract(
    final double x,
    final double y)
  {
    return x - y;
  }

  public static int compareReal(
    final double x,
    final double y)
  {
    return Double.compare(x, y);
  }

  public static double pow(
    final double x,
    final float e)
  {
    return StrictMath.pow(x, e);
  }

  public static double divide(
    final double x,
    final double y)
  {
    return x / y;
  }
}
