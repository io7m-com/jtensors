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
import com.io7m.jtensors.VectorReadable2LType;
import com.io7m.jtensors.VectorWritable2LType;
import com.io7m.jtensors.parameterized.PVector2LType;
import com.io7m.jtensors.parameterized.PVectorM2L;
import com.io7m.jtensors.parameterized.PVectorReadable2LType;
import com.io7m.jtensors.parameterized.PVectorWritable2LType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM2LTest<T>
  extends PVectorM2LContract<T, PVectorM2L<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM2LTest.class);
  }

  @Override protected PVectorM2L<T> newVectorM2L(
    final long x,
    final long y)
  {
    return new PVectorM2L<T>(x, y);
  }

  @Override protected PVectorM2L<T> newVectorM2L(final PVectorM2L<T> v)
  {
    return new PVectorM2L<T>(v);
  }

  @Override protected PVectorM2L<T> newVectorM2L()
  {
    return new PVectorM2L<T>();
  }

  @Test public void testHierarchy()
  {
    final PVectorM2L<?> v = new PVectorM2L<Object>();

    Assert.assertTrue(v instanceof Vector2LType);

    Assert.assertTrue(v instanceof VectorReadable2LType);

    Assert.assertTrue(v instanceof VectorWritable2LType);

    Assert.assertTrue(v instanceof PVector2LType);

    Assert.assertTrue(v instanceof PVectorReadable2LType);

    Assert.assertTrue(v instanceof PVectorWritable2LType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorM2LTest.LOG.debug(
        "{} implements {}", PVectorM2L.class, k);
    }

    Assert.assertEquals(6L, (long) interfaces.size());
  }
}
