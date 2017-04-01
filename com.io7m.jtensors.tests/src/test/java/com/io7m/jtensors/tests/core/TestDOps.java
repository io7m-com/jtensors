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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2F;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.parameterized.vectors.PVector2D;
import com.io7m.jtensors.core.parameterized.vectors.PVector3D;
import com.io7m.jtensors.core.parameterized.vectors.PVector4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class TestDOps
{
  private static final Logger LOG;
  private static final AlmostEqualDouble.ContextRelative ALMOST_EQUAL_LARGE;
  private static final AlmostEqualDouble.ContextRelative ALMOST_EQUAL_VAGUE;

  static {
    LOG = LoggerFactory.getLogger(TestDOps.class);

    ALMOST_EQUAL_LARGE = new AlmostEqualDouble.ContextRelative();
    ALMOST_EQUAL_LARGE.setMaxAbsoluteDifference(0.000001);
    ALMOST_EQUAL_LARGE.setMaxRelativeDifference(0.000001);

    ALMOST_EQUAL_VAGUE = new AlmostEqualDouble.ContextRelative();
    ALMOST_EQUAL_VAGUE.setMaxAbsoluteDifference(0.000001);
    ALMOST_EQUAL_VAGUE.setMaxRelativeDifference(0.000001);
  }

  private TestDOps()
  {
    throw new UnreachableCodeException();
  }

  public static <T> void checkObjectEquals(
    final T x,
    final T y)
  {
    if (!Objects.equals(x, y)) {
      LOG.warn("expected: {}", x);
      LOG.warn("received: {}", y);
    }

    Assert.assertEquals(x, y);
  }

  public static <T> void checkObjectNotEquals(
    final T x,
    final T y)
  {
    if (Objects.equals(x, y)) {
      LOG.warn("expected: {}", x);
      LOG.warn("received: {}", y);
    }

    Assert.assertNotEquals(x, y);
  }

  public static void checkAlmostEqualsMatrix(
    final PMatrix4x4D<?, ?> m0,
    final PMatrix4x4D<?, ?> m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());
      checkAlmostEquals(m0.r0c2(), m1.r0c2());
      checkAlmostEquals(m0.r0c3(), m1.r0c3());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
      checkAlmostEquals(m0.r1c2(), m1.r1c2());
      checkAlmostEquals(m0.r1c3(), m1.r1c3());

      checkAlmostEquals(m0.r2c0(), m1.r2c0());
      checkAlmostEquals(m0.r2c1(), m1.r2c1());
      checkAlmostEquals(m0.r2c2(), m1.r2c2());
      checkAlmostEquals(m0.r2c3(), m1.r2c3());

      checkAlmostEquals(m0.r3c0(), m1.r3c0());
      checkAlmostEquals(m0.r3c1(), m1.r3c1());
      checkAlmostEquals(m0.r3c2(), m1.r3c2());
      checkAlmostEquals(m0.r3c3(), m1.r3c3());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix4x4D m0,
    final Matrix4x4D m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());
      checkAlmostEquals(m0.r0c2(), m1.r0c2());
      checkAlmostEquals(m0.r0c3(), m1.r0c3());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
      checkAlmostEquals(m0.r1c2(), m1.r1c2());
      checkAlmostEquals(m0.r1c3(), m1.r1c3());

      checkAlmostEquals(m0.r2c0(), m1.r2c0());
      checkAlmostEquals(m0.r2c1(), m1.r2c1());
      checkAlmostEquals(m0.r2c2(), m1.r2c2());
      checkAlmostEquals(m0.r2c3(), m1.r2c3());

      checkAlmostEquals(m0.r3c0(), m1.r3c0());
      checkAlmostEquals(m0.r3c1(), m1.r3c1());
      checkAlmostEquals(m0.r3c2(), m1.r3c2());
      checkAlmostEquals(m0.r3c3(), m1.r3c3());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final PMatrix3x3D<?, ?> m0,
    final PMatrix3x3D<?, ?> m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());
      checkAlmostEquals(m0.r0c2(), m1.r0c2());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
      checkAlmostEquals(m0.r1c2(), m1.r1c2());

      checkAlmostEquals(m0.r2c0(), m1.r2c0());
      checkAlmostEquals(m0.r2c1(), m1.r2c1());
      checkAlmostEquals(m0.r2c2(), m1.r2c2());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix3x3D m0,
    final Matrix3x3D m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());
      checkAlmostEquals(m0.r0c2(), m1.r0c2());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
      checkAlmostEquals(m0.r1c2(), m1.r1c2());

      checkAlmostEquals(m0.r2c0(), m1.r2c0());
      checkAlmostEquals(m0.r2c1(), m1.r2c1());
      checkAlmostEquals(m0.r2c2(), m1.r2c2());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final Matrix2x2D m0,
    final Matrix2x2D m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsMatrix(
    final PMatrix2x2D<?, ?> m0,
    final PMatrix2x2D<?, ?> m1)
  {
    try {
      checkAlmostEquals(m0.r0c0(), m1.r0c0());
      checkAlmostEquals(m0.r0c1(), m1.r0c1());

      checkAlmostEquals(m0.r1c0(), m1.r1c0());
      checkAlmostEquals(m0.r1c1(), m1.r1c1());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", m0);
      LOG.warn("received: {}", m1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final PVector4D<?> v0,
    final PVector4D<?> v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
      checkAlmostEquals(v0.z(), v1.z());
      checkAlmostEquals(v0.w(), v1.w());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final Vector4D v0,
    final Vector4D v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
      checkAlmostEquals(v0.z(), v1.z());
      checkAlmostEquals(v0.w(), v1.w());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final PVector3D<?> v0,
    final PVector3D<?> v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
      checkAlmostEquals(v0.z(), v1.z());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final Vector3D v0,
    final Vector3D v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
      checkAlmostEquals(v0.z(), v1.z());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final Vector2D v0,
    final Vector2D v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static void checkAlmostEqualsVector(
    final PVector2D<?> v0,
    final PVector2D<?> v1)
  {
    try {
      checkAlmostEquals(v0.x(), v1.x());
      checkAlmostEquals(v0.y(), v1.y());
    } catch (final AssertionError e) {
      LOG.warn("expected: {}", v0);
      LOG.warn("received: {}", v1);
      throw e;
    }
  }

  public static boolean almostEquals(
    final double x,
    final double y)
  {
    return AlmostEqualDouble.almostEqual(ALMOST_EQUAL_LARGE, x, y);
  }

  public static double radiansOfDegrees(
    final String text)
  {
    return Math.toRadians(Double.parseDouble(text));
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
    if (!AlmostEqualDouble.almostEqual(ALMOST_EQUAL_LARGE, x, y)) {
      throw new AssertionError(
        String.format(
          "Expected: <%f> Received: <%f>",
          Double.valueOf(x),
          Double.valueOf(y)));
    }
  }

  public static void checkAlmostEqualsVague(
    final double x,
    final double y)
  {
    if (!AlmostEqualDouble.almostEqual(ALMOST_EQUAL_VAGUE, x, y)) {
      throw new AssertionError(
        String.format(
          "Expected: <%f> Received: <%f>",
          Double.valueOf(x),
          Double.valueOf(y)));
    }
  }

  public static double constant(
    final String text)
  {
    return Double.parseDouble(text);
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

  public static double divide(
    final double x,
    final double y)
  {
    return x / y;
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
    final double s,
    final double e)
  {
    return StrictMath.pow(s, e);
  }
}
