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

import com.io7m.jtensors.Vector2FType;
import com.io7m.jtensors.VectorReadable2FType;
import com.io7m.jtensors.VectorWritable2FType;
import com.io7m.jtensors.parameterized.PVector2FType;
import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.parameterized.PVectorReadable2FType;
import com.io7m.jtensors.parameterized.PVectorWritable2FType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM2FTest<T>
  extends PVectorM2FContract<T, PVectorM2F<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM2FTest.class);
  }

  @Override protected PVectorM2F<T> newVectorM2F(final PVectorM2F<T> v0)
  {
    return new PVectorM2F<T>(v0);
  }

  @Override protected PVectorM2F<T> newVectorM2F(
    final float x1,
    final float y1)
  {
    return new PVectorM2F<T>(x1, y1);
  }

  @Override protected PVectorM2F<T> newVectorM2F()
  {
    return new PVectorM2F<T>();
  }

  @Test public void testHierarchy()
  {
    final PVectorM2F<?> v = new PVectorM2F<Object>();

    Assert.assertTrue(v instanceof Vector2FType);

    Assert.assertTrue(v instanceof VectorReadable2FType);

    Assert.assertTrue(v instanceof VectorWritable2FType);

    Assert.assertTrue(v instanceof PVector2FType);

    Assert.assertTrue(v instanceof PVectorReadable2FType);

    Assert.assertTrue(v instanceof PVectorWritable2FType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorM2FTest.LOG.debug(
        "{} implements {}", PVectorM2F.class, k);
    }

    Assert.assertEquals(6L, (long) interfaces.size());
  }
}
