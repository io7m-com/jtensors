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

package com.io7m.jtensors.tests;

import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jtensors.OrthonormalizedI3F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.tests.rules.PercentagePassRule;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OrthonormalizedI3FTest
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(OrthonormalizedI3FTest.class);
  }

  @Rule public PercentagePassRule percent =
    new PercentagePassRule(TestUtilities.TEST_RANDOM_ITERATIONS);

  @Test public void testAlreadyOrthonormal0()
  {
    final AlmostEqualFloat.ContextRelative ec =
      TestUtilities.getSingleEqualityContext();

    {
      final VectorI3F v0 = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorI3F v1 = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F v2 = new VectorI3F(0.0f, 0.0f, 1.0f);
      final OrthonormalizedI3F o = new OrthonormalizedI3F(v0, v1, v2);
      Assert.assertTrue(VectorI3F.almostEqual(ec, v0, o.getV0()));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v1, o.getV1()));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v2, o.getV2()));
    }

    {
      final VectorI3F v0 = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorI3F v1 = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorI3F v2 = new VectorI3F(0.0f, 0.0f, -1.0f);
      final OrthonormalizedI3F o = new OrthonormalizedI3F(v0, v1, v2);
      Assert.assertTrue(VectorI3F.almostEqual(ec, v0, o.getV0()));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v1, o.getV1()));
      Assert.assertTrue(VectorI3F.almostEqual(ec, v2, o.getV2()));
    }
  }

  @Test @PercentagePassing public void testAlwaysOrthnormal()
  {
    final float max = 10000.0f;

    final float x0 = (float) (Math.random() * max);
    final float y0 = (float) (Math.random() * max);
    final float z0 = (float) (Math.random() * max);
    final VectorI3F v0 = new VectorI3F(x0, y0, z0);

    final float x1 = (float) (Math.random() * max);
    final float y1 = (float) (Math.random() * max);
    final float z1 = (float) (Math.random() * max);
    final VectorI3F v1 = new VectorI3F(x1, y1, z1);

    final float x2 = (float) (Math.random() * max);
    final float y2 = (float) (Math.random() * max);
    final float z2 = (float) (Math.random() * max);
    final VectorI3F v2 = new VectorI3F(x2, y2, z2);

    final OrthonormalizedI3F o = new OrthonormalizedI3F(v0, v1, v2);

    final float v0m = VectorI3F.magnitude(o.getV0());
    final float v1m = VectorI3F.magnitude(o.getV1());
    final float v2m = VectorI3F.magnitude(o.getV2());

    final float v0_dot_v1 = VectorI3F.dotProduct(o.getV0(), o.getV1());
    final float v0_dot_v2 = VectorI3F.dotProduct(o.getV0(), o.getV2());
    final float v1_dot_v2 = VectorI3F.dotProduct(o.getV1(), o.getV2());

    LOG.debug("v0              : {}", v0);
    LOG.debug("v1              : {}", v1);
    LOG.debug("v2              : {}", v2);
    LOG.debug("o.v0            : {}", o.getV0());
    LOG.debug("o.v1            : {}", o.getV1());
    LOG.debug("o.v2            : {}", o.getV2());
    LOG.debug("magnitude(o.v0) : {}", v0m);
    LOG.debug("magnitude(o.v1) : {}", v1m);
    LOG.debug("magnitude(o.v2) : {}", v2m);
    LOG.debug("o.v0 dot o.v1   : {}", v0_dot_v1);
    LOG.debug("o.v0 dot o.v2   : {}", v0_dot_v2);
    LOG.debug("o.v1 dot o.v2   : {}", v1_dot_v2);
    LOG.debug("--");

    Assert.assertEquals(1.0f, v0m, 0.00001f);
    Assert.assertEquals(1.0f, v1m, 0.00001f);
    Assert.assertEquals(1.0f, v2m, 0.00001f);

    Assert.assertEquals(0.0f, v0_dot_v1, 0.00005f);
    Assert.assertEquals(0.0f, v0_dot_v2, 0.00005f);
    Assert.assertEquals(0.0f, v1_dot_v2, 0.00005f);
  }

  @Test public void testEquals()
  {
    final VectorI3F v0 = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F v2 = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorI3F v3 = new VectorI3F(0.0f, 0.0f, -1.0f);

    final OrthonormalizedI3F o0 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o1 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o2 = new OrthonormalizedI3F(v0, v0, v0);
    final OrthonormalizedI3F o3 = new OrthonormalizedI3F(v1, v1, v1);
    final OrthonormalizedI3F o4 = new OrthonormalizedI3F(v2, v2, v2);
    final OrthonormalizedI3F o5 = new OrthonormalizedI3F(v0, v1, v3);

    Assert.assertTrue(o0.equals(o0));
    Assert.assertFalse(o0.equals(null));
    Assert.assertFalse(o0.equals(Integer.valueOf(23)));
    Assert.assertFalse(o0.equals(o2));
    Assert.assertFalse(o0.equals(o3));
    Assert.assertFalse(o0.equals(o4));
    Assert.assertFalse(o0.equals(o5));
    Assert.assertTrue(o0.equals(o1));
  }

  @Test public void testHashcode()
  {
    final VectorI3F v0 = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F v2 = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorI3F v3 = new VectorI3F(-1.0f, 0.0f, 0.0f);

    final OrthonormalizedI3F o0 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o1 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o2 = new OrthonormalizedI3F(v0, v0, v0);
    final OrthonormalizedI3F o3 = new OrthonormalizedI3F(v1, v1, v1);
    final OrthonormalizedI3F o4 = new OrthonormalizedI3F(v2, v2, v2);
    final OrthonormalizedI3F o5 = new OrthonormalizedI3F(v0, v1, v3);

    Assert.assertTrue(o0.hashCode() == (o0.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o2.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o3.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o4.hashCode()));
    Assert.assertFalse(o0.hashCode() == (o5.hashCode()));
    Assert.assertTrue(o0.hashCode() == (o1.hashCode()));
  }

  @Test public void testToString()
  {
    final VectorI3F v0 = new VectorI3F(1.0f, 0.0f, 0.0f);
    final VectorI3F v1 = new VectorI3F(0.0f, 1.0f, 0.0f);
    final VectorI3F v2 = new VectorI3F(0.0f, 0.0f, 1.0f);
    final VectorI3F v3 = new VectorI3F(-1.0f, 0.0f, 0.0f);

    final OrthonormalizedI3F o0 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o1 = new OrthonormalizedI3F(v0, v1, v2);
    final OrthonormalizedI3F o2 = new OrthonormalizedI3F(v0, v0, v0);
    final OrthonormalizedI3F o3 = new OrthonormalizedI3F(v1, v1, v1);
    final OrthonormalizedI3F o4 = new OrthonormalizedI3F(v2, v2, v2);
    final OrthonormalizedI3F o5 = new OrthonormalizedI3F(v0, v1, v3);

    Assert.assertTrue(o0.toString().equals((o0.toString())));
    Assert.assertFalse(o0.toString().equals((o2.toString())));
    Assert.assertFalse(o0.toString().equals((o3.toString())));
    Assert.assertFalse(o0.toString().equals((o4.toString())));
    Assert.assertFalse(o0.toString().equals((o5.toString())));
    Assert.assertTrue(o0.toString().equals((o1.toString())));
  }
}
