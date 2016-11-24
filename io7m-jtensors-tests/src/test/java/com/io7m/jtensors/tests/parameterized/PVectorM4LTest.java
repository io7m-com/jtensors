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

import com.io7m.jtensors.Vector2LType;
import com.io7m.jtensors.Vector3LType;
import com.io7m.jtensors.Vector4LType;
import com.io7m.jtensors.VectorReadable2LType;
import com.io7m.jtensors.VectorReadable3LType;
import com.io7m.jtensors.VectorReadable4LType;
import com.io7m.jtensors.VectorWritable2LType;
import com.io7m.jtensors.VectorWritable3LType;
import com.io7m.jtensors.VectorWritable4LType;
import com.io7m.jtensors.parameterized.PVector2LType;
import com.io7m.jtensors.parameterized.PVector3LType;
import com.io7m.jtensors.parameterized.PVector4LType;
import com.io7m.jtensors.parameterized.PVectorM4L;
import com.io7m.jtensors.parameterized.PVectorReadable2LType;
import com.io7m.jtensors.parameterized.PVectorReadable3LType;
import com.io7m.jtensors.parameterized.PVectorReadable4LType;
import com.io7m.jtensors.parameterized.PVectorWritable2LType;
import com.io7m.jtensors.parameterized.PVectorWritable3LType;
import com.io7m.jtensors.parameterized.PVectorWritable4LType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM4LTest<T>
  extends PVectorM4LContract<T, PVectorM4L<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM4LTest.class);
  }

  @Override protected PVectorM4L<T> newVectorM4L(
    final long x,
    final long y,
    final long z,
    final long w)
  {
    return new PVectorM4L<T>(x, y, z, w);
  }

  @Override protected PVectorM4L<T> newVectorM4LFrom(final PVectorM4L<T> v0)
  {
    return new PVectorM4L<T>(v0);
  }

  @Override protected PVectorM4L<T> newVectorM4L()
  {
    return new PVectorM4L<T>();
  }

  @Test public void testHierarchy()
  {
    final PVectorM4L<?> v = new PVectorM4L<Object>();

    Assert.assertTrue(v instanceof Vector4LType);
    Assert.assertTrue(v instanceof Vector3LType);
    Assert.assertTrue(v instanceof Vector2LType);

    Assert.assertTrue(v instanceof VectorReadable4LType);
    Assert.assertTrue(v instanceof VectorReadable3LType);
    Assert.assertTrue(v instanceof VectorReadable2LType);

    Assert.assertTrue(v instanceof VectorWritable4LType);
    Assert.assertTrue(v instanceof VectorWritable3LType);
    Assert.assertTrue(v instanceof VectorWritable2LType);

    Assert.assertTrue(v instanceof PVector4LType);
    Assert.assertTrue(v instanceof PVector3LType);
    Assert.assertTrue(v instanceof PVector2LType);

    Assert.assertTrue(v instanceof PVectorReadable4LType);
    Assert.assertTrue(v instanceof PVectorReadable3LType);
    Assert.assertTrue(v instanceof PVectorReadable2LType);

    Assert.assertTrue(v instanceof PVectorWritable4LType);
    Assert.assertTrue(v instanceof PVectorWritable3LType);
    Assert.assertTrue(v instanceof PVectorWritable2LType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorM4LTest.LOG.debug(
        "{} implements {}", PVectorM4L.class, k);
    }

    Assert.assertEquals(18L, (long) interfaces.size());
  }
}
