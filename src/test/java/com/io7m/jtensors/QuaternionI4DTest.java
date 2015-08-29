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

import com.io7m.jaux.ApproximatelyEqualDouble;

public class QuaternionI4DTest
{
  @SuppressWarnings("static-method") @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
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
    Assert.assertTrue(QuaternionI4D.approximatelyEqual(v0, v1));
  }

  @SuppressWarnings("static-method") @Test public
    void
    testApproximatelyEqualTransitive1()
  {
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
    Assert.assertFalse(QuaternionI4D.approximatelyEqual(v0, v1));
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
    final QuaternionI4D e = new QuaternionI4D(-1.0, -2.0, -3.0, 4.0);
    final QuaternionI4D q = new QuaternionI4D(1.0, 2.0, 3.0, 4.0);
    final QuaternionI4D r = QuaternionI4D.conjugate(q);
    final boolean t = QuaternionI4D.approximatelyEqual(e, r);

    Assert.assertTrue(t);
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
    for (int index = 0; index < 100; ++index) {
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

      Assert.assertTrue(QuaternionI4D.approximatelyEqual(
        QuaternionI4D.interpolateLinear(v0, v1, 0.0),
        v0));
      Assert.assertTrue(QuaternionI4D.approximatelyEqual(
        QuaternionI4D.interpolateLinear(v0, v1, 1.0),
        v1));
    }
  }

  @SuppressWarnings("static-method") @Test public void testMagnitudeNormal()
  {
    for (int index = 0; index < 100; ++index) {
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
    for (int index = 0; index < 100; ++index) {
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

      System.err.println("testMakeAxisAngleNormal: " + q);
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeAxisAngleX_45()
  {
    final VectorI3D axis = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D q =
      QuaternionI4D.makeFromAxisAngle(axis, Math.toRadians(45));

    System.err.println("testMakeAxisAngleX: " + q);

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

    System.err.println("testMakeAxisAngleX: " + q);

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

    System.err.println("testMakeAxisAngleY: " + q);

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

    System.err.println("testMakeAxisAngleY: " + q);

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

    System.err.println("testMakeAxisAngleZ: " + q);

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

    System.err.println("testMakeAxisAngleZ: " + q);

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
    testMakeMatrixIdentity()
  {
    final QuaternionI4D q = new QuaternionI4D();
    final MatrixM4x4D m = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix(q, m);

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
    testMakeMatrixRotateX_90()
  {
    final VectorI3D axis_x = new VectorI3D(1.0, 0.0, 0.0);
    final QuaternionI4D qx =
      QuaternionI4D.makeFromAxisAngle(axis_x, Math.toRadians(90));

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix(qx, mq);
    MatrixM4x4D.rotateInPlace(Math.toRadians(90), mr, axis_x);

    System.err.println("testMakeMatrixRotateX_90: mq: ");
    System.err.println(mq);
    System.err.println("testMakeMatrixRotateX_90: mr: ");
    System.err.println(mr);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        final Double dx = Double.valueOf(x);
        final Double dy = Double.valueOf(y);
        System.err.println(String.format("%.16f\t%.16f", dx, dy));
        ApproximatelyEqualDouble.approximatelyEqual(
          dx.doubleValue(),
          dy.doubleValue());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrixRotateY_90()
  {
    final VectorI3D axis_y = new VectorI3D(0.0, 1.0, 0.0);
    final QuaternionI4D qy =
      QuaternionI4D.makeFromAxisAngle(axis_y, Math.toRadians(90));

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix(qy, mq);
    MatrixM4x4D.rotateInPlace(Math.toRadians(90), mr, axis_y);

    System.err.println("testMakeMatrixRotateY_90: mq: ");
    System.err.println(mq);
    System.err.println("testMakeMatrixRotateY_90: mr: ");
    System.err.println(mr);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        final Double dx = Double.valueOf(x);
        final Double dy = Double.valueOf(y);
        System.err.println(String.format("%.16f\t%.16f", dx, dy));
        ApproximatelyEqualDouble.approximatelyEqual(
          dx.doubleValue(),
          dy.doubleValue());
      }
    }
  }

  @SuppressWarnings("static-method") @Test public
    void
    testMakeMatrixRotateZ_90()
  {
    final VectorI3D axis_z = new VectorI3D(0.0, 0.0, 1.0);
    final QuaternionI4D qy =
      QuaternionI4D.makeFromAxisAngle(axis_z, Math.toRadians(90));

    final MatrixM4x4D mq = new MatrixM4x4D();
    final MatrixM4x4D mr = new MatrixM4x4D();

    QuaternionI4D.makeRotationMatrix(qy, mq);
    MatrixM4x4D.rotateInPlace(Math.toRadians(90), mr, axis_z);

    System.err.println("testMakeMatrixRotateZ_90: mq: ");
    System.err.println(mq);
    System.err.println("testMakeMatrixRotateZ_90: mr: ");
    System.err.println(mr);

    for (int row = 0; row < 4; ++row) {
      for (int col = 0; col < 4; ++col) {
        final double x = mr.get(row, col);
        final double y = mq.get(row, col);
        final Double dx = Double.valueOf(x);
        final Double dy = Double.valueOf(y);
        System.err.println(String.format("%.16f\t%.16f", dx, dy));
        ApproximatelyEqualDouble.approximatelyEqual(
          dx.doubleValue(),
          dy.doubleValue());
      }
    }
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
    System.err.println("testMultiply: " + qr);

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

  @SuppressWarnings("static-method") @Test public void testNoargIdentity()
  {
    final QuaternionI4D v = new QuaternionI4D();
    QuaternionI4D.approximatelyEqual(v, QuaternionI4D.IDENTITY);
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
    final QuaternionI4D v0 = new QuaternionI4D(0.0, 0.0, 0.0, 0.0);
    QuaternionI4D.approximatelyEqual(QuaternionI4D.normalize(v0), v0);
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final QuaternionI4D v = new QuaternionI4D(0.0, 1.0, 2.0, 3.0);
    Assert.assertTrue(v.toString().equals("[QuaternionI4D 0.0 1.0 2.0 3.0]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
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
