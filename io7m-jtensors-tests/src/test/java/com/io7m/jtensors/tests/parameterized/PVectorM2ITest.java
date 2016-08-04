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

import com.io7m.jtensors.Vector2IType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorWritable2IType;
import com.io7m.jtensors.parameterized.PVector2IType;
import com.io7m.jtensors.parameterized.PVectorM2I;
import com.io7m.jtensors.parameterized.PVectorReadable2IType;
import com.io7m.jtensors.parameterized.PVectorWritable2IType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM2ITest<T>
  extends PVectorM2IContract<T, PVectorM2I<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM2ITest.class);
  }

  @Override protected PVectorM2I<T> newVectorM2I(
    final int x,
    final int y)
  {
    return new PVectorM2I<T>(x, y);
  }

  @Override protected PVectorM2I<T> newVectorM2I()
  {
    return new PVectorM2I<T>();
  }

  @Override protected PVectorM2I<T> newVectorM2I(final PVectorM2I<T> v)
  {
    return new PVectorM2I<T>(v);
  }

  @Test public void testHierarchy()
  {
    final PVectorM2I<?> v = new PVectorM2I<Object>();

    Assert.assertTrue(v instanceof Vector2IType);

    Assert.assertTrue(v instanceof VectorReadable2IType);

    Assert.assertTrue(v instanceof VectorWritable2IType);

    Assert.assertTrue(v instanceof PVector2IType);

    Assert.assertTrue(v instanceof PVectorReadable2IType);

    Assert.assertTrue(v instanceof PVectorWritable2IType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      PVectorM2ITest.LOG.debug(
        "{} implements {}", PVectorM2I.class, k);
    }

    Assert.assertEquals(6L, (long) interfaces.size());
  }
}
