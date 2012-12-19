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
import com.io7m.jaux.AlmostEqualFloat;
import com.io7m.jaux.ApproximatelyEqualFloat;

public class QuaternionI4FTest
{
  private static final VectorReadable3F AXIS_X = new VectorI3F(1, 0, 0);
  private static final VectorReadable3F AXIS_Y = new VectorI3F(0, 1, 0);
  private static final VectorReadable3F AXIS_Z = new VectorI3F(0, 0, 1);

  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

      final QuaternionI4F vr = QuaternionI4F.add(v0, v1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getXF(),
        v0.getXF() + v1.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getYF(),
        v0.getYF() + v1.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getZF(),
        v0.getZF() + v1.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getWF(),
        v0.getWF() + v1.getWF()));
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive0()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final float x0 = 0.0f;
    final float x1 = 0.0f;
    final float y0 = 0.0f;
    final float y1 = 0.0f;
    final float z0 = 0.0f;
    final float z1 = 0.0f;
    final float w0 = 0.0f;
    final float w1 = 0.0f;
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);
    final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);
    Assert.assertTrue(QuaternionI4F.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final float x0 = 0.0f;
    final float x1 = 1.0f;
    final float y0 = 0.0f;
    final float y1 = 1.0f;
    final float z0 = 0.0f;
    final float z1 = 1.0f;
    final float w0 = 0.0f;
    final float w1 = 1.0f;
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(x0, x1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(y0, y1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(z0, z1));
    Assert.assertFalse(ApproximatelyEqualFloat.approximatelyEqual(w0, w1));

    final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);
    final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);
    Assert.assertFalse(QuaternionI4F.almostEqual(context, v0, v1));
  }

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final QuaternionI4F v = new QuaternionI4F(3.0f, 5.0f, 7.0f, 11.0f);

    Assert.assertTrue(v.getXF() == v.getXF());
    Assert.assertTrue(v.getYF() == v.getYF());
    Assert.assertTrue(v.getZF() == v.getZF());
    Assert.assertTrue(v.getWF() == v.getWF());
  }

  @SuppressWarnings("static-method") @Test public void testConjugate()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionI4F e = new QuaternionI4F(-1.0f, -2.0f, -3.0f, 4.0f);
    final QuaternionI4F q = new QuaternionI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionI4F r = QuaternionI4F.conjugate(q);
    final boolean t = QuaternionI4F.almostEqual(context, e, r);

    Assert.assertTrue(t);
  }

  @SuppressWarnings("static-method") @Test public
    void
    testConjugateInvertible()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) ((Math.random() * 200) - 100);
      final float y = (float) ((Math.random() * 200) - 100);
      final float z = (float) ((Math.random() * 200) - 100);
      final float w = (float) ((Math.random() * 200) - 100);

      final QuaternionI4F q = new QuaternionI4F(x, y, z, w);
      final QuaternionI4F qc0 = QuaternionI4F.conjugate(q);
      final QuaternionI4F qc1 = QuaternionI4F.conjugate(qc0);

      eq = AlmostEqualFloat.almostEqual(context, q.getXF(), qc1.getXF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getYF(), qc1.getYF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getZF(), qc1.getZF());
      Assert.assertTrue(eq);
      eq = AlmostEqualFloat.almostEqual(context, q.getWF(), qc1.getWF());
      Assert.assertTrue(eq);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testDotProductOrthonormal()
  {
    final QuaternionI4F v = new QuaternionI4F(1.0f, 0.0f, 0.0f, 0.0f);
    Assert.assertTrue(QuaternionI4F.dotProduct(v, v) == 1.0f);
  }

  @SuppressWarnings("static-method") @Test public void testDotProductSimple()
  {
    final float x0 = 2.0f;
    final float y0 = 2.0f;
    final float z0 = 2.0f;
    final float w0 = 2.0f;
    final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

    final float x1 = 2.0f;
    final float y1 = 2.0f;
    final float z1 = 2.0f;
    final float w1 = 2.0f;
    final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

    final float p = QuaternionI4F.dotProduct(v0, v1);
    Assert.assertTrue(p == 16.0);
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final QuaternionI4F v0 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F v1 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F vw = new QuaternionI4F(0.0f, 0.0f, 0.0f, 1.0f);
    final QuaternionI4F vz = new QuaternionI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final QuaternionI4F vy = new QuaternionI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final QuaternionI4F vx = new QuaternionI4F(1.0f, 0.0f, 0.0f, 0.0f);

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
    final QuaternionI4F v0 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F v1 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F vw = new QuaternionI4F(0.0f, 0.0f, 0.0f, 1.0f);
    final QuaternionI4F vz = new QuaternionI4F(0.0f, 0.0f, 1.0f, 0.0f);
    final QuaternionI4F vy = new QuaternionI4F(0.0f, 1.0f, 0.0f, 0.0f);
    final QuaternionI4F vx = new QuaternionI4F(1.0f, 0.0f, 0.0f, 0.0f);

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
    final QuaternionI4F v0 = new QuaternionI4F(1.0f, 2.0f, 3.0f, 4.0f);
    final QuaternionI4F v1 = new QuaternionI4F(v0);

    Assert.assertTrue(v0.getXF() == v1.getXF());
    Assert.assertTrue(v0.getYF() == v1.getYF());
    Assert.assertTrue(v0.getZF() == v1.getZF());
    Assert.assertTrue(v0.getWF() == v1.getWF());
  }

  @SuppressWarnings("static-method") @Test public
    void
    testInterpolateLinearLimits()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

      Assert.assertTrue(QuaternionI4F.almostEqual(
        context,
        QuaternionI4F.interpolateLinear(v0, v1, 0.0f),
        v0));
      Assert.assertTrue(QuaternionI4F.almostEqual(
        context,
        QuaternionI4F.interpolateLinear(v0, v1, 1.0f),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x = (float) (Math.random() * Float.MAX_VALUE);
      final float y = (float) (Math.random() * Float.MAX_VALUE);
      final float z = (float) (Math.random() * Float.MAX_VALUE);
      final float w = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v = new QuaternionI4F(x, y, z, w);

      ApproximatelyEqualFloat.approximatelyEqual(
        QuaternionI4F.magnitude(QuaternionI4F.normalize(v)),
        1.0f);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleNormal()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final VectorI3F axis_r =
        new VectorI3F(
          (float) Math.random(),
          (float) Math.random(),
          (float) Math.random());
      final VectorI3F axis_n = VectorI3F.normalize(axis_r);

      final QuaternionI4F q =
        QuaternionI4F.makeFromAxisAngle(
          axis_n,
          (float) Math.toRadians(Math.random() * 360));

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        QuaternionI4F.magnitude(q),
        1.0f));

      System.err.println("testMakeAxisAngleNormal: " + q);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_45()
  {
    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(45));

    System.err.println("testMakeAxisAngleX: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_90()
  {
    final VectorI3F axis = new VectorI3F(1.0f, 0.0f, 0.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(90));

    System.err.println("testMakeAxisAngleX: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_45()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(45));

    System.err.println("testMakeAxisAngleY: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleY_90()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(90));

    System.err.println("testMakeAxisAngleY: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_45()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(45));

    System.err.println("testMakeAxisAngleZ: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.3826834323650898f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.9238795325112867f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleZ_90()
  {
    final VectorI3F axis = new VectorI3F(0.0f, 0.0f, 1.0f);
    final QuaternionI4F q =
      QuaternionI4F.makeFromAxisAngle(axis, (float) Math.toRadians(90));

    System.err.println("testMakeAxisAngleZ: " + q);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getXF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getYF(),
      0.0f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getZF(),
      0.7071067811865475f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      q.getWF(),
      0.7071067811865475f));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeFromMatrix3x3Exhaustive()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final MatrixM3x3F m = new MatrixM3x3F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4F qaa = QuaternionI4F.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM3x3F.makeRotation(angle, axis, m);
      final QuaternionI4F qfm = QuaternionI4F.makeFromRotationMatrix3x3(m);

      final double mag_qfm = QuaternionI4F.magnitude(qfm);
      final double mag_qaa = QuaternionI4F.magnitude(qaa);

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

      if (QuaternionI4F.almostEqual(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4F.isNegationOf(context_f, qfm, qaa)) {
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
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();
    final AlmostEqualDouble.ContextRelative context_d =
      TestUtilities.getDoubleEqualityContext6dp();

    final MatrixM4x4F m = new MatrixM4x4F();
    boolean eq = false;

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double degrees = (2 * Math.random() * 360.0) - 360.0;
      final double angle = Math.toRadians(degrees);
      final float axis_x = (float) Math.random();
      final float axis_y = (float) Math.random();
      final float axis_z = (float) Math.random();
      final VectorM3F axis = new VectorM3F(axis_x, axis_y, axis_z);
      VectorM3F.normalizeInPlace(axis);

      /**
       * Produce a quaternion from an axis and angle.
       */

      final QuaternionI4F qaa = QuaternionI4F.makeFromAxisAngle(axis, angle);

      /**
       * Produce a rotation matrix from an axis and angle, and then a
       * quaternion from that matrix.
       */

      MatrixM4x4F.makeRotation(angle, axis, m);
      final QuaternionI4F qfm = QuaternionI4F.makeFromRotationMatrix4x4(m);

      final double mag_qfm = QuaternionI4F.magnitude(qfm);
      final double mag_qaa = QuaternionI4F.magnitude(qaa);

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

      if (QuaternionI4F.almostEqual(context_f, qfm, qaa)) {
        Assert.assertTrue(true);
        continue;
      }

      /**
       * The sign of quaternions may flip when created from matrices.
       */

      if (QuaternionI4F.isNegationOf(context_f, qfm, qaa)) {
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
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Identity()
  {
    final QuaternionI4F q = new QuaternionI4F();
    final MatrixM3x3F m = new MatrixM3x3F();

    QuaternionI4F.makeRotationMatrix3x3(q, m);

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
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_X;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Y;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix3x3_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM3x3F mq = new MatrixM3x3F();
    final MatrixM3x3F mr = new MatrixM3x3F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Z;

    MatrixM3x3F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix3x3(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 3; ++row) {
      for (int col = 0; col < 3; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45X()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Identity()
  {
    final QuaternionI4F q = new QuaternionI4F();
    final MatrixM4x4F m = new MatrixM4x4F();

    QuaternionI4F.makeRotationMatrix4x4(q, m);

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
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_X;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Y()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Y;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrix4x4_Minus45Z()
  {
    final AlmostEqualFloat.ContextRelative context_f =
      TestUtilities.getSingleEqualityContext();

    final MatrixM4x4F mq = new MatrixM4x4F();
    final MatrixM4x4F mr = new MatrixM4x4F();
    boolean eq = false;

    final double radians = Math.toRadians(-45);
    final VectorReadable3F axis = QuaternionI4FTest.AXIS_Z;

    MatrixM4x4F.makeRotation(radians, axis, mr);
    final QuaternionI4F q = QuaternionI4F.makeFromAxisAngle(axis, radians);
    QuaternionI4F.makeRotationMatrix4x4(q, mq);

    System.out.println("mr: ");
    System.out.println(mr);
    System.out.println("mq: ");
    System.out.println(mq);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final float x = mr.get(row, col);
        final float y = mq.get(row, col);
        eq = AlmostEqualFloat.almostEqual(context_f, x, y);
        Assert.assertTrue(eq);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrixIdentity()
  {
    final QuaternionI4F q = new QuaternionI4F();
    final MatrixM4x4F m = new MatrixM4x4F();

    QuaternionI4F.makeRotationMatrix4x4(q, m);

    Assert.assertTrue(1.0f == m.get(0, 0));
    Assert.assertTrue(0.0f == m.get(0, 1));
    Assert.assertTrue(0.0f == m.get(0, 2));
    Assert.assertTrue(0.0f == m.get(0, 3));

    Assert.assertTrue(0.0f == m.get(1, 0));
    Assert.assertTrue(1.0f == m.get(1, 1));
    Assert.assertTrue(0.0f == m.get(1, 2));
    Assert.assertTrue(0.0f == m.get(1, 3));

    Assert.assertTrue(0.0f == m.get(2, 0));
    Assert.assertTrue(0.0f == m.get(2, 1));
    Assert.assertTrue(1.0f == m.get(2, 2));
    Assert.assertTrue(0.0f == m.get(2, 3));

    Assert.assertTrue(0.0f == m.get(3, 0));
    Assert.assertTrue(0.0f == m.get(3, 1));
    Assert.assertTrue(0.0f == m.get(3, 2));
    Assert.assertTrue(1.0f == m.get(3, 3));
  }

  @SuppressWarnings("static-method") @Test public void testMultiply()
  {
    final VectorI3F axis_x = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F axis_y = new VectorI3F(0.0f, 1.0f, 0.0f);
    final QuaternionI4F qx =
      QuaternionI4F.makeFromAxisAngle(axis_x, (float) Math.toRadians(45));
    final QuaternionI4F qy =
      QuaternionI4F.makeFromAxisAngle(axis_y, (float) Math.toRadians(45));

    /**
     * The quaternion resulting from a 45 degree rotation around the global X
     * axis, followed by a 45 degree rotation around the global Y axis.
     */

    final QuaternionI4F qr = QuaternionI4F.multiply(qy, qx);
    System.err.println("testMultiply: " + qr);

    /**
     * Values obtained by checking against the results produced by Blender.
     * 
     * @see http://blender.org
     */

    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getXF(),
      0.3535533905932738f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getYF(),
      0.3535533905932738f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getZF(),
      -0.14644660940672624f));
    Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
      qr.getWF(),
      0.8535533905932737f));
  }

  @SuppressWarnings("static-method") @Test public void testNegation()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double x = (Math.random() * 2) - Math.random();
      final double y = (Math.random() * 2) - Math.random();
      final double z = (Math.random() * 2) - Math.random();
      final double w = (Math.random() * 2) - Math.random();
      final QuaternionI4F qi =
        new QuaternionI4F((float) x, (float) y, (float) z, (float) w);
      final QuaternionI4F qn =
        new QuaternionI4F((float) -x, (float) -y, (float) -z, (float) -w);
      final QuaternionI4F qr = QuaternionI4F.negate(qi);

      System.out.println("qi : " + qi);
      System.out.println("qn : " + qn);
      System.out.println("qr : " + qr);
      System.out.println("--");

      Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qr));
      Assert.assertTrue(QuaternionI4F.almostEqual(context, qn, qr));
    }
  }

  @SuppressWarnings("static-method") @Test public void testNegationCases()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionI4F qi = new QuaternionI4F(1, 2, 3, 4);
    final QuaternionI4F qnx = new QuaternionI4F(-1, 2, 3, 4);
    final QuaternionI4F qny = new QuaternionI4F(-1, -2, 3, 4);
    final QuaternionI4F qnz = new QuaternionI4F(-1, -2, -3, 4);
    final QuaternionI4F qnw = new QuaternionI4F(-1, -2, -3, -4);

    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qi) == false);
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qnx) == false);
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qny) == false);
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qnz) == false);
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qnw) == true);

    final QuaternionI4F qnwn = QuaternionI4F.negate(qnw);
    Assert.assertTrue(QuaternionI4F.isNegationOf(context, qi, qnwn) == false);
    Assert.assertTrue(QuaternionI4F.almostEqual(context, qi, qnwn));
  }

  @SuppressWarnings("static-method") @Test public void testNoargIdentity()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionI4F v = new QuaternionI4F();
    QuaternionI4F.almostEqual(context, v, QuaternionI4F.IDENTITY);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeSimple()
  {
    final QuaternionI4F v0 = new QuaternionI4F(8.0f, 0.0f, 0.0f, 0.0f);
    final QuaternionI4F vr = QuaternionI4F.normalize(v0);
    final float m = QuaternionI4F.magnitude(vr);
    Assert.assertTrue(m == 1.0f);
  }

  @SuppressWarnings("static-method") @Test public void testNormalizeZero()
  {
    final AlmostEqualFloat.ContextRelative context =
      TestUtilities.getSingleEqualityContext();

    final QuaternionI4F v0 = new QuaternionI4F(0.0f, 0.0f, 0.0f, 0.0f);
    QuaternionI4F.almostEqual(context, QuaternionI4F.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final QuaternionI4F v = new QuaternionI4F(0.0f, 1.0f, 2.0f, 3.0f);
    Assert.assertTrue(v.toString().equals("[QuaternionI4F 0.0 1.0 2.0 3.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final float x0 = (float) (Math.random() * Float.MAX_VALUE);
      final float y0 = (float) (Math.random() * Float.MAX_VALUE);
      final float z0 = (float) (Math.random() * Float.MAX_VALUE);
      final float w0 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v0 = new QuaternionI4F(x0, y0, z0, w0);

      final float x1 = (float) (Math.random() * Float.MAX_VALUE);
      final float y1 = (float) (Math.random() * Float.MAX_VALUE);
      final float z1 = (float) (Math.random() * Float.MAX_VALUE);
      final float w1 = (float) (Math.random() * Float.MAX_VALUE);
      final QuaternionI4F v1 = new QuaternionI4F(x1, y1, z1, w1);

      final QuaternionI4F vr = QuaternionI4F.subtract(v0, v1);

      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getXF(),
        v0.getXF() - v1.getXF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getYF(),
        v0.getYF() - v1.getYF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getZF(),
        v0.getZF() - v1.getZF()));
      Assert.assertTrue(ApproximatelyEqualFloat.approximatelyEqual(
        vr.getWF(),
        v0.getWF() - v1.getWF()));
    }
  }
}
