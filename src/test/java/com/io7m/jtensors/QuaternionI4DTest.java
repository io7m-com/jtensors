/*
 * Copyright © 2012 http://io7m.com
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

package com.io7m.jtensors;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualDouble.ContextRelative;
import com.io7m.jaux.ApproximatelyEqualDouble;

public class QuaternionI4DTest
{
  private static final VectorReadable3D AXIS_X = new VectorI3D(1, 0, 0);
  private static final VectorReadable3D AXIS_Y = new VectorI3D(0, 1, 0);
  private static final VectorReadable3D AXIS_Z = new VectorI3D(0, 0, 1);

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getXD(),
        v0.getXD() + v1.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getYD(),
        v0.getYD() + v1.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getZD(),
        v0.getZD() + v1.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getWD(),
        v0.getWD() + v1.getWD()));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final double x0 = 0.0;
    final double x1 = 0.0;
    final double y0 = 0.0;
    final double y1 = 0.0;
    final double z0 = 0.0;
    final double z1 = 0.0;
    final double w0 = 0.0;
    final double w1 = 0.0;
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);
    final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);
    Assert.assertTrue(QuaternionI4D.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final double x0 = 0.0;
    final double x1 = 1.0;
    final double y0 = 0.0;
    final double y1 = 1.0;
    final double z0 = 0.0;
    final double z1 = 1.0;
    final double w0 = 0.0;
    final double w1 = 1.0;
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualDouble.approximatelyEqual(w0, w1));

    final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);
    final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);
    Assert.assertFalse(QuaternionI4D.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final QuaternionI4D v = new QuaternionI4D(3.0, 5.0, 7.0, 11.0);

    Assert.assertTrue(v.getXD() == v.getXD());
    Assert.assertTrue(v.getYD() == v.getYD());
    Assert.assertTrue(v.getZD() == v.getZD());
    Assert.assertTrue(v.getWD() == v.getWD());
  }

  @SuppressWarnings("static-method") @Test public void testConjugate()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D e = new QuaternionI4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionI4D q = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D r = QuaternionI4D.conjugate(q);
    final boolean t = QuaternionI4D.almostEqual(context, e, r);

    Assert.assertTrue(t);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testConjugateInvertible()
  {
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 200) - 100;
      final double y = (Math.random() * 200) - 100;
      final double z = (Math.random() * 200) - 100;
      final double w = (Math.random() * 200) - 100;

      final QuaternionI4D q = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D qc0 = QuaternionI4D.conjugate(q);
      final QuaternionI4D qc1 = QuaternionI4D.conjugate(qc0);

      eq = AlmostEqualDouble.almostEqual(context_d, q.getXD(), qc1.getXD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getYD(), qc1.getYD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getZD(), qc1.getZD());
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, q.getWD(), qc1.getWD());
      Assert.assertTrue(eq);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final QuaternionI4D v = new QuaternionI4D(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(QuaternionI4D.dotProduct(v, v) == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final double x0 = 2.0;
    final double y0 = 2.0;
    final double z0 = 2.0;
    final double w0 = 2.0;
    final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

    final double x1 = 2.0;
    final double y1 = 2.0;
    final double z1 = 2.0;
    final double w1 = 2.0;
    final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

    final double p = QuaternionI4D.dotProduct(v0, v1);
    Assert.assertTrue(p == 16.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final QuaternionI4D v0 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D v1 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vw = new QuaternionI4D(0.0, 0.0, 0.0, 1.0);
    final QuaternionI4D vz = new QuaternionI4D(0.0, 0.0, 1.0, 0.0);
    final QuaternionI4D vy = new QuaternionI4D(0.0, 1.0, 0.0, 0.0);
    final QuaternionI4D vx = new QuaternionI4D(1.0, 0.0, 0.0, 0.0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final QuaternionI4D v0 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D v1 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vw = new QuaternionI4D(0.0, 0.0, 0.0, 1.0);
    final QuaternionI4D vz = new QuaternionI4D(0.0, 0.0, 1.0, 0.0);
    final QuaternionI4D vy = new QuaternionI4D(0.0, 1.0, 0.0, 0.0);
    final QuaternionI4D vx = new QuaternionI4D(1.0, 0.0, 0.0, 0.0);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
    Assert.assertTrue(v0.hashCode() != vz.hashCode());
    Assert.assertTrue(v0.hashCode() != vw.hashCode());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInitializeReadable()
  {
    final QuaternionI4D v0 = new QuaternionI4D(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionI4D v1 = new QuaternionI4D(v0);

    Assert.assertTrue(v0.getXD() == v1.getXD());
    Assert.assertTrue(v0.getYD() == v1.getYD());
    Assert.assertTrue(v0.getZD() == v1.getZD());
    Assert.assertTrue(v0.getWD() == v1.getWD());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      Assert.assertTrue(QuaternionI4D.almostEqual(
        context,
        QuaternionI4D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(QuaternionI4D.almostEqual(
        context,
        QuaternionI4D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = Math.random() * Double.MAX_VALUE;
      final double y = Math.random() * Double.MAX_VALUE;
      final double z = Math.random() * Double.MAX_VALUE;
      final double w = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v = new QuaternionI4D(x, y, z, w);

      ApproximatelyEqualDouble.approximatelyEqual(
        QuaternionI4D.magnitude(QuaternionI4D.normalize(v)),
        1.0);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3D axis_r =
        new VectorI3D(Math.random(), Math.random(), Math.random());
      final VectorI3D axis_n = VectorI3D.normalize(axis_r);

      final QuaternionI4D q =
        QuaternionI4D.makeFromAxisAngle(
          axis_n,
          Math.toRadians(Math.random() * 360));

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        QuaternionI4D.magnitude(q),
        1.0));

      System.out.println("q : " + q);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_45()
  {
    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_90()
  {
    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_45()
  {
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_90()
  {
    final VectorI3D axis = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_45()
  {
    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.3826834323650898));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.9238795325112867));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_90()
  {
    final VectorI3D axis = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(90));

    System.out.println("q : " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getXD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getYD(),
      0.0));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getZD(),
      0.7071067811865475));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      q.getWD(),
      0.7071067811865475));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeFromMatrix3x3Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D m = new MatrixM3x3D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4D qaa = QuaternionI4D.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3D.makeRotation(angle, axis, m);
      final QuaternionI4D qfm = QuaternionI4D.makeFromRotationMatrix3x3(m);

      final double mag_qfm = QuaternionI4D.magnitude(qfm);
      final double mag_qaa = QuaternionI4D.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionI4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeFromMatrix4x4Exhaustive()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D m = new MatrixM4x4D();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final double axis_x = Math.random();
      final double axis_y = Math.random();
      final double axis_z = Math.random();
      final VectorM3D axis = new VectorM3D(axis_x, axis_y, axis_z);
      VectorM3D.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4D qaa = QuaternionI4D.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4D.makeRotation(angle, axis, m);
      final QuaternionI4D qfm = QuaternionI4D.makeFromRotationMatrix4x4(m);

      final double mag_qfm = QuaternionI4D.magnitude(qfm);
      final double mag_qaa = QuaternionI4D.magnitude(qaa);

      System.out.println("mag_qfm : " + mag_qfm);
      System.out.println("mag_qaa : " + mag_qaa);
      System.out.println("axis    : " + axis);
      System.out.println("angle   : " + angle);
      System.out.println("m       : ");
      System.out.println(m);
      System.out.println("qfm     : " + qfm);
      System.out.println("qaa     : " + qaa);
      System.out.println("--");

      /**
       * The resulting quaternions are unit quaternions.
       */

      eq = AlmostEqualDouble.almostEqual(context_d, mag_qfm, 1.0);
      Assert.assertTrue(eq);
      eq = AlmostEqualDouble.almostEqual(context_d, mag_qaa, 1.0);
      Assert.assertTrue(eq);

      /**
       * The resulting quaternions match.
       */

      if (QuaternionI4D.almostEqual(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4D.isNegationOf(context_d, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      Assert.fail(qfm + " != " + qaa);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM3x3D m = new MatrixM3x3D();

    QuaternionI4D.makeRotationMatrix3x3(q, m);

    Assert.assertTrue(1.0 == m.get(0, 0));
    Assert.assertTrue(0.0 == m.get(0, 1));
    Assert.assertTrue(0.0 == m.get(0, 2));

    Assert.assertTrue(0.0 == m.get(1, 0));
    Assert.assertTrue(1.0 == m.get(1, 1));
    Assert.assertTrue(0.0 == m.get(1, 2));

    Assert.assertTrue(0.0 == m.get(2, 0));
    Assert.assertTrue(0.0 == m.get(2, 1));
    Assert.assertTrue(1.0 == m.get(2, 2));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_X;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Y;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM3x3D mq = new MatrixM3x3D();
    final MatrixM3x3D mr = new MatrixM3x3D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Z;

    MatrixM3x3D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Identity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0 == m.get(0, 0));
    Assert.assertTrue(0.0 == m.get(0, 1));
    Assert.assertTrue(0.0 == m.get(0, 2));
    Assert.assertTrue(0.0 == m.get(0, 3));

    Assert.assertTrue(0.0 == m.get(1, 0));
    Assert.assertTrue(1.0 == m.get(1, 1));
    Assert.assertTrue(0.0 == m.get(1, 2));
    Assert.assertTrue(0.0 == m.get(1, 3));

    Assert.assertTrue(0.0 == m.get(2, 0));
    Assert.assertTrue(0.0 == m.get(2, 1));
    Assert.assertTrue(1.0 == m.get(2, 2));
    Assert.assertTrue(0.0 == m.get(2, 3));

    Assert.assertTrue(0.0 == m.get(3, 0));
    Assert.assertTrue(0.0 == m.get(3, 1));
    Assert.assertTrue(0.0 == m.get(3, 2));
    Assert.assertTrue(1.0 == m.get(3, 3));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45X()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_X;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Y()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Y;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Z()
  {
    final ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext();

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3D axis = QuaternionI4DTest.AXIS_Z;

    MatrixM4x4D.makeRotation(radians, axis, mr);
    final QuaternionI4D q = QuaternionI4D.makeFromAxisAngle(axis, radians);
    QuaternionI4D.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        eq = AlmostEqualDouble.almostEqual(context_d, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrixIdentity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0 == m.get(0, 0));
    Assert.assertTrue(0.0 == m.get(0, 1));
    Assert.assertTrue(0.0 == m.get(0, 2));
    Assert.assertTrue(0.0 == m.get(0, 3));

    Assert.assertTrue(0.0 == m.get(1, 0));
    Assert.assertTrue(1.0 == m.get(1, 1));
    Assert.assertTrue(0.0 == m.get(1, 2));
    Assert.assertTrue(0.0 == m.get(1, 3));

    Assert.assertTrue(0.0 == m.get(2, 0));
    Assert.assertTrue(0.0 == m.get(2, 1));
    Assert.assertTrue(1.0 == m.get(2, 2));
    Assert.assertTrue(0.0 == m.get(2, 3));

    Assert.assertTrue(0.0 == m.get(3, 0));
    Assert.assertTrue(0.0 == m.get(3, 1));
    Assert.assertTrue(0.0 == m.get(3, 2));
    Assert.assertTrue(1.0 == m.get(3, 3));
  }

  @SuppressWarnings("static-method") @Test public void testMultiply()
  {
    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D qx =
      QuaternionI4D.makeFromAxisAngle(axis_x, Math.toRadians(45));
    final QuaternionI4D qy =
      QuaternionI4D.makeFromAxisAngle(axis_y, Math.toRadians(45));

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionI4D qr = QuaternionI4D.multiply(qy, qx);
    System.out.println("qr : " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getXD(),
      0.3535533905932738));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getYD(),
      0.3535533905932738));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getZD(),
      -0.14644660940672624));
    Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
      qr.getWD(),
      0.8535533905932737));
  }

  @SuppressWarnings("static-method") @Test public void testNegation()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 2) - Math.random();
      final double y = (Math.random() * 2) - Math.random();
      final double z = (Math.random() * 2) - Math.random();
      final double w = (Math.random() * 2) - Math.random();
      final QuaternionI4D qi = new QuaternionI4D(x, y, z, w);
      final QuaternionI4D qn = new QuaternionI4D(-x, -y, -z, -w);
      final QuaternionI4D qr = QuaternionI4D.negate(qi);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionI4D.almostEqual(context, qn, qr));
    }
  }

  @SuppressWarnings("static-method") @Test public void testNegationCases()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();

    final QuaternionI4D qi = new QuaternionI4D(1, 2, 3, 4);
    final QuaternionI4D qnx = new QuaternionI4D(-1, 2, 3, 4);
    final QuaternionI4D qny = new QuaternionI4D(-1, -2, 3, 4);
    final QuaternionI4D qnz = new QuaternionI4D(-1, -2, -3, 4);
    final QuaternionI4D qnw = new QuaternionI4D(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnw) == true);

    final QuaternionI4D qnwn = QuaternionI4D.negate(qnw);
    Assert.assertTrue(QuaternionI4D.isNegationOf(context, qi, qnwn) == false);
    Assert.assertTrue(QuaternionI4D.almostEqual(context, qi, qnwn));
  }

  @SuppressWarnings("static-method") @Test public void testNoargIdentity()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionI4D v = new QuaternionI4D();
    QuaternionI4D.almostEqual(context, v, QuaternionI4D.IDENTITY);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final QuaternionI4D v0 = new QuaternionI4D(8.0, 0.0, 0.0, 0.0);
    final QuaternionI4D vr = QuaternionI4D.normalize(v0);
    final double m = QuaternionI4D.magnitude(vr);
    Assert.assertTrue(m == 1.0);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final ContextRelative context = TestUtilities.getDoubleEqualityContext();
    final QuaternionI4D v0 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    QuaternionI4D.almostEqual(context, QuaternionI4D.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final QuaternionI4D v = new QuaternionI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().equals("[QuaternionI4D 0.0 1.0 2.0 3.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x0 = Math.random() * Double.MAX_VALUE;
      final double y0 = Math.random() * Double.MAX_VALUE;
      final double z0 = Math.random() * Double.MAX_VALUE;
      final double w0 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v0 = new QuaternionI4D(x0, y0, z0, w0);

      final double x1 = Math.random() * Double.MAX_VALUE;
      final double y1 = Math.random() * Double.MAX_VALUE;
      final double z1 = Math.random() * Double.MAX_VALUE;
      final double w1 = Math.random() * Double.MAX_VALUE;
      final QuaternionI4D v1 = new QuaternionI4D(x1, y1, z1, w1);

      final QuaternionI4D vr = QuaternionI4D.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getXD(),
        v0.getXD() - v1.getXD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getYD(),
        v0.getYD() - v1.getYD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getZD(),
        v0.getZD() - v1.getZD()));
      Assert.assertTrue(ApproximatelyEqualDouble.approximatelyEqual(
        vr.getWD(),
        v0.getWD() - v1.getWD()));
    }
  }

}
