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
import com.io7m.jtensors.VectorReadable2DType;
import com.io7m.jtensors.VectorReadable3DType;
import com.io7m.jtensors.VectorWritable2DType;
import com.io7m.jtensors.VectorWritable3DType;
import com.io7m.jtensors.parameterized.PVector2DType;
import com.io7m.jtensors.parameterized.PVector3DType;
import com.io7m.jtensors.parameterized.PVectorM3D;
import com.io7m.jtensors.parameterized.PVectorReadable2DType;
import com.io7m.jtensors.parameterized.PVectorReadable3DType;
import com.io7m.jtensors.parameterized.PVectorWritable2DType;
import com.io7m.jtensors.parameterized.PVectorWritable3DType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM3DTest<T>
  extends PVectorM3DContract<T, PVectorM3D<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM3DTest.class);
  }

  @Override
  protected double delta()
  {
    return 0.0000000000001;
  }

  @Override
  protected double randomLargeNegative()
  {
    return Math.random() * -1000000.0;
  }

  @Override
  protected double randomLargePositive()
  {
    return Math.random() * 1000000.0;
  }

  @Override
  protected Logger logger()
  {
    return LOG;
  }

  @Override
  protected PVectorM3D<T> newVectorM3D(final PVectorM3D<T> v)
  {
    return new PVectorM3D<T>(v);
  }

  @Override
  protected PVectorM3D<T> newVectorM3D()
  {
    return new PVectorM3D<T>();
  }

  @Override
  protected PVectorM3D<T> newVectorM3D(
    final double x,
    final double y,
    final double z)
  {
    return new PVectorM3D<T>(x, y, z);
  }

  @Test
  public void testHierarchy()
  {
    final PVectorM3D<?> v = new PVectorM3D<Object>();

    Assert.assertTrue(v instanceof Vector3DType);
    Assert.assertTrue(v instanceof Vector2DType);

    Assert.assertTrue(v instanceof VectorReadable3DType);
    Assert.assertTrue(v instanceof VectorReadable2DType);

    Assert.assertTrue(v instanceof VectorWritable3DType);
    Assert.assertTrue(v instanceof VectorWritable2DType);

    Assert.assertTrue(v instanceof PVector3DType);
    Assert.assertTrue(v instanceof PVector2DType);

    Assert.assertTrue(v instanceof PVectorReadable3DType);
    Assert.assertTrue(v instanceof PVectorReadable2DType);

    Assert.assertTrue(v instanceof PVectorWritable3DType);
    Assert.assertTrue(v instanceof PVectorWritable2DType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", PVectorM3D.class, k);
    }

    Assert.assertEquals(12L, (long) interfaces.size());
  }
}
