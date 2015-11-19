/*
 * Copyright © 2015 <code@io7m.com> http://io7m.com
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
import com.io7m.jtensors.VectorReadable2LType;
import com.io7m.jtensors.VectorReadable3LType;
import com.io7m.jtensors.VectorWritable2LType;
import com.io7m.jtensors.VectorWritable3LType;
import com.io7m.jtensors.parameterized.PVector2LType;
import com.io7m.jtensors.parameterized.PVector3LType;
import com.io7m.jtensors.parameterized.PVectorM3L;
import com.io7m.jtensors.parameterized.PVectorReadable2LType;
import com.io7m.jtensors.parameterized.PVectorReadable3LType;
import com.io7m.jtensors.parameterized.PVectorWritable2LType;
import com.io7m.jtensors.parameterized.PVectorWritable3LType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM3LTest<T>
  extends PVectorM3LContract<T, PVectorM3L<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM3LTest.class);
  }

  @Override protected PVectorM3L<T> newVectorM3L(
    final long x,
    final long y,
    final long z)
  {
    return new PVectorM3L<T>(x, y, z);
  }

  @Override protected PVectorM3L<T> newVectorM3L()
  {
    return new PVectorM3L<T>();
  }

  @Override protected PVectorM3L<T> newVectorM3LFrom(final PVectorM3L<T> v)
  {
    return new PVectorM3L<T>(v);
  }

  @Test public void testHierarchy()
  {
    final PVectorM3L<?> v = new PVectorM3L<Object>();

    Assert.assertTrue(v instanceof Vector3LType);
    Assert.assertTrue(v instanceof Vector2LType);

    Assert.assertTrue(v instanceof VectorReadable3LType);
    Assert.assertTrue(v instanceof VectorReadable2LType);

    Assert.assertTrue(v instanceof VectorWritable3LType);
    Assert.assertTrue(v instanceof VectorWritable2LType);

    Assert.assertTrue(v instanceof PVector3LType);
    Assert.assertTrue(v instanceof PVector2LType);

    Assert.assertTrue(v instanceof PVectorReadable3LType);
    Assert.assertTrue(v instanceof PVectorReadable2LType);

    Assert.assertTrue(v instanceof PVectorWritable3LType);
    Assert.assertTrue(v instanceof PVectorWritable2LType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorM3LTest.LOG.debug(
        "{} implements {}", PVectorM3L.class, k);
    }

    Assert.assertEquals(12L, (long) interfaces.size());
  }
}
