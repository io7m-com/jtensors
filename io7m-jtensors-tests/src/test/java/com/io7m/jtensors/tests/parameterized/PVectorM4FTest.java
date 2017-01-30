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

import com.io7m.jtensors.Vector2FType;
import com.io7m.jtensors.Vector3FType;
import com.io7m.jtensors.Vector4FType;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.VectorReadable4FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.VectorWritable3FType;
import com.io7m.jtensors.VectorWritable4FType;
import com.io7m.jtensors.parameterized.PVector2FType;
import com.io7m.jtensors.parameterized.PVector3FType;
import com.io7m.jtensors.parameterized.PVector4FType;
import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.parameterized.PVectorReadable2FType;
import com.io7m.jtensors.parameterized.PVectorReadable3FType;
import com.io7m.jtensors.parameterized.PVectorReadable4FType;
import com.io7m.jtensors.parameterized.PVectorWritable2FType;
import com.io7m.jtensors.parameterized.PVectorWritable3FType;
import com.io7m.jtensors.parameterized.PVectorWritable4FType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM4FTest<T>
  extends PVectorM4FContract<T, PVectorM4F<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM4FTest.class);
  }

  @Override
  protected PVectorM4F<T> newVectorM4F(
    final float x,
    final float y,
    final float z,
    final float w)
  {
    return new PVectorM4F<T>(x, y, z, w);
  }

  @Override
  protected PVectorM4F<T> newVectorM4FFrom(final PVectorM4F<T> v)
  {
    return new PVectorM4F<T>(v);
  }

  @Override
  protected PVectorM4F<T> newVectorM4F()
  {
    return new PVectorM4F<T>();
  }

  @Test
  public void testHierarchy()
  {
    final PVectorM4F<?> v = new PVectorM4F<Object>();

    Assert.assertTrue(v instanceof Vector4FType);
    Assert.assertTrue(v instanceof Vector3FType);
    Assert.assertTrue(v instanceof Vector2FType);

    Assert.assertTrue(v instanceof VectorReadable4FType);
    Assert.assertTrue(v instanceof VectorReadable3FType);
    Assert.assertTrue(v instanceof VectorReadable2FType);

    Assert.assertTrue(v instanceof VectorWritable4FType);
    Assert.assertTrue(v instanceof VectorWritable3FType);
    Assert.assertTrue(v instanceof VectorWritable2FType);

    Assert.assertTrue(v instanceof PVector4FType);
    Assert.assertTrue(v instanceof PVector3FType);
    Assert.assertTrue(v instanceof PVector2FType);

    Assert.assertTrue(v instanceof PVectorReadable4FType);
    Assert.assertTrue(v instanceof PVectorReadable3FType);
    Assert.assertTrue(v instanceof PVectorReadable2FType);

    Assert.assertTrue(v instanceof PVectorWritable4FType);
    Assert.assertTrue(v instanceof PVectorWritable3FType);
    Assert.assertTrue(v instanceof PVectorWritable2FType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", PVectorM4F.class, k);
    }

    Assert.assertEquals(18L, (long) interfaces.size());
  }
}
