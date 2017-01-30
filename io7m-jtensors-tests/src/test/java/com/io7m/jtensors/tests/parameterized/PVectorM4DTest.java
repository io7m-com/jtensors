/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests.parameterized;

import com.io7m.jtensors.Vector2DType;
import com.io7m.jtensors.Vector3DType;
import com.io7m.jtensors.Vector4DType;
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorReadable4DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.VectorWritable4DType;
import com.io7m.jtensors.parameterized.PVector2DType;
import com.io7m.jtensors.parameterized.PVector3DType;
import com.io7m.jtensors.parameterized.PVector4DType;
import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.parameterized.PVectorReadable2DType;
import com.io7m.jtensors.parameterized.PVectorReadable3DType;
import com.io7m.jtensors.parameterized.PVectorReadable4DType;
import com.io7m.jtensors.parameterized.PVectorWritable2DType;
import com.io7m.jtensors.parameterized.PVectorWritable3DType;
import com.io7m.jtensors.parameterized.PVectorWritable4DType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM4DTest<T>
  extends PVectorM4DContract<T, PVectorM4D<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM4DTest.class);
  }

  @Override protected double delta()
  {
    return 0.0000000000001;
  }

  @Override protected double randomLargeNegative()
  {
    return Math.random() * -1000000.0;
  }

  @Override protected double randomLargePositive()
  {
    return Math.random() * 1000000.0;
  }

  @Override protected Logger logger()
  {
    return LOG;
  }

  @Override protected PVectorM4D<T> newVectorM4D()
  {
    return new PVectorM4D<T>();
  }

  @Override protected PVectorM4D<T> newVectorM4DFrom(final PVectorM4D<T> v)
  {
    return new PVectorM4D<T>(v);
  }

  @Override protected PVectorM4D<T> newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    return new PVectorM4D<T>(x, y, z, w);
  }

  @Test public void testHierarchy()
  {
    final PVectorM4D<?> v = new PVectorM4D<Object>();

    Assert.assertTrue(v instanceof Vector4DType);
    Assert.assertTrue(v instanceof Vector3DType);
    Assert.assertTrue(v instanceof Vector2DType);

    Assert.assertTrue(v instanceof VectorReadable4DType);
    Assert.assertTrue(v instanceof VectorReadable3DType);
    Assert.assertTrue(v instanceof VectorReadable2DType);

    Assert.assertTrue(v instanceof VectorWritable4DType);
    Assert.assertTrue(v instanceof VectorWritable3DType);
    Assert.assertTrue(v instanceof VectorWritable2DType);

    Assert.assertTrue(v instanceof PVector4DType);
    Assert.assertTrue(v instanceof PVector3DType);
    Assert.assertTrue(v instanceof PVector2DType);

    Assert.assertTrue(v instanceof PVectorReadable4DType);
    Assert.assertTrue(v instanceof PVectorReadable3DType);
    Assert.assertTrue(v instanceof PVectorReadable2DType);

    Assert.assertTrue(v instanceof PVectorWritable4DType);
    Assert.assertTrue(v instanceof PVectorWritable3DType);
    Assert.assertTrue(v instanceof PVectorWritable2DType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", PVectorM4D.class, k);
    }

    Assert.assertEquals(18L, (long) interfaces.size());
  }
}
