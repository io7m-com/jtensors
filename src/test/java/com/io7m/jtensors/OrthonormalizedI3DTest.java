/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

public class OrthonormalizedI3DTest
{
  @SuppressWarnings("static-method") @Test public
    void
    testAlreadyOrthonormal0()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    {
      final VectorI3D v0 = new VectorI3D(1.0, 0.0, 0.0);
      final VectorI3D v1 = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D v2 = new VectorI3D(0.0, 0.0, 1.0);
      final OrthonormalizedI3D o = new OrthonormalizedI3D(v0, v1, v2);
      Assert.assertTrue(VectorI3D.almostEqual(ec, v0, o.getV0()));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v1, o.getV1()));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v2, o.getV2()));
    }

    {
      final VectorI3D v0 = new VectorI3D(1.0, 0.0, 0.0);
      final VectorI3D v1 = new VectorI3D(0.0, 1.0, 0.0);
      final VectorI3D v2 = new VectorI3D(0.0, 0.0, -1.0);
      final OrthonormalizedI3D o = new OrthonormalizedI3D(v0, v1, v2);
      Assert.assertTrue(VectorI3D.almostEqual(ec, v0, o.getV0()));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v1, o.getV1()));
      Assert.assertTrue(VectorI3D.almostEqual(ec, v2, o.getV2()));
    }
  }

  @SuppressWarnings("static-method") @Test public void testAlwaysOrthnormal()
  {
    final AlmostEqualDouble.ContextRelative ec =
      TestUtilities.getDoubleEqualityContext();

    for (int index = 0; index < TestUtilities.TEST_RANDOM_ITERATIONS; ++index) {
      final double max = 10000.0;

      final double x0 = Math.random() * max;
      final double y0 = Math.random() * max;
      final double z0 = Math.random() * max;
      final VectorI3D v0 = new VectorI3D(x0, y0, z0);

      final double x1 = Math.random() * max;
      final double y1 = Math.random() * max;
      final double z1 = Math.random() * max;
      final VectorI3D v1 = new VectorI3D(x1, y1, z1);

      final double x2 = Math.random() * max;
      final double y2 = Math.random() * max;
      final double z2 = Math.random() * max;
      final VectorI3D v2 = new VectorI3D(x2, y2, z2);

      final OrthonormalizedI3D o = new OrthonormalizedI3D(v0, v1, v2);

      final double v0m = VectorI3D.magnitude(o.getV0());
      final double v1m = VectorI3D.magnitude(o.getV1());
      final double v2m = VectorI3D.magnitude(o.getV2());

      final double v0_dot_v1 = VectorI3D.dotProduct(o.getV0(), o.getV1());
      final double v0_dot_v2 = VectorI3D.dotProduct(o.getV0(), o.getV2());
      final double v1_dot_v2 = VectorI3D.dotProduct(o.getV1(), o.getV2());

      System.err.println("v0              : " + v0);
      System.err.println("v1              : " + v1);
      System.err.println("v2              : " + v2);
      System.err.println("o.v0            : " + o.getV0());
      System.err.println("o.v1            : " + o.getV1());
      System.err.println("o.v2            : " + o.getV2());
      System.err.println("magniture(o.v0) : " + v0m);
      System.err.println("magniture(o.v1) : " + v1m);
      System.err.println("magniture(o.v2) : " + v2m);
      System.err.println("o.v0 dot o.v1   : " + v0_dot_v1);
      System.err.println("o.v0 dot o.v2   : " + v0_dot_v2);
      System.err.println("o.v1 dot o.v2   : " + v1_dot_v2);
      System.err.println("--");

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0m, 1.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1m, 1.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v2m, 1.0));

      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0_dot_v1, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v0_dot_v2, 0.0));
      Assert.assertTrue(AlmostEqualDouble.almostEqual(ec, v1_dot_v2, 0.0));
    }
  }

  @SuppressWarnings("static-method") @Test public void testEquals()
  {
    final VectorI3D v0 = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D v2 = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D v3 = new VectorI3D(-1.0, 0.0, 0.0);

    final OrthonormalizedI3D o0 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o1 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o2 = new OrthonormalizedI3D(v0, v0, v0);
    final OrthonormalizedI3D o3 = new OrthonormalizedI3D(v1, v1, v1);
    final OrthonormalizedI3D o4 = new OrthonormalizedI3D(v2, v2, v2);
    final OrthonormalizedI3D o5 = new OrthonormalizedI3D(v0, v1, v3);

    Assert.assertTrue(o0.equals(o0));
    Assert.assertFalse(o0.equals(null));
    Assert.assertFalse(o0.equals(Integer.valueOf(23)));
    Assert.assertFalse(o0.equals(o2));
    Assert.assertFalse(o0.equals(o3));
    Assert.assertFalse(o0.equals(o4));
    Assert.assertFalse(o0.equals(o5));
    Assert.assertTrue(o0.equals(o1));
  }

  @SuppressWarnings("static-method") @Test public void testHashcode()
  {
    final VectorI3D v0 = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D v2 = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D v3 = new VectorI3D(-1.0, 0.0, 0.0);

    final OrthonormalizedI3D o0 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o1 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o2 = new OrthonormalizedI3D(v0, v0, v0);
    final OrthonormalizedI3D o3 = new OrthonormalizedI3D(v1, v1, v1);
    final OrthonormalizedI3D o4 = new OrthonormalizedI3D(v2, v2, v2);
    final OrthonormalizedI3D o5 = new OrthonormalizedI3D(v0, v1, v3);

    Assert.assertTrue(o0.hashCode() == (o0.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o2.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o3.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o4.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o5.hashCode()));
    Assert.assertTrue(o0.hashCode() == (o1.hashCode()));
  }

  @SuppressWarnings("static-method") @Test public void testToString()
  {
    final VectorI3D v0 = new VectorI3D(1.0, 0.0, 0.0);
    final VectorI3D v1 = new VectorI3D(0.0, 1.0, 0.0);
    final VectorI3D v2 = new VectorI3D(0.0, 0.0, 1.0);
    final VectorI3D v3 = new VectorI3D(-1.0, 0.0, 0.0);

    final OrthonormalizedI3D o0 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o1 = new OrthonormalizedI3D(v0, v1, v2);
    final OrthonormalizedI3D o2 = new OrthonormalizedI3D(v0, v0, v0);
    final OrthonormalizedI3D o3 = new OrthonormalizedI3D(v1, v1, v1);
    final OrthonormalizedI3D o4 = new OrthonormalizedI3D(v2, v2, v2);
    final OrthonormalizedI3D o5 = new OrthonormalizedI3D(v0, v1, v3);

    Assert.assertTrue(o0.toString().equals((o0.toString())));
    Assert.assertFalse(o0.toString().equals((o2.toString())));
    Assert.assertFalse(o0.toString().equals((o3.toString())));
    Assert.assertFalse(o0.toString().equals((o4.toString())));
    Assert.assertFalse(o0.toString().equals((o5.toString())));
    Assert.assertTrue(o0.toString().equals((o1.toString())));
  }
}
