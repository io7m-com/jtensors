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
import com.io7m.jtensors.Vector3IType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorWritable2IType;
import com.io7m.jtensors.VectorWritable3IType;
import com.io7m.jtensors.parameterized.PVector2IType;
import com.io7m.jtensors.parameterized.PVector3IType;
import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.parameterized.PVectorReadable2IType;
import com.io7m.jtensors.parameterized.PVectorReadable3IType;
import com.io7m.jtensors.parameterized.PVectorWritable2IType;
import com.io7m.jtensors.parameterized.PVectorWritable3IType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM3ITest<T>
  extends PVectorM3IContract<T, PVectorM3I<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM3ITest.class);
  }

  @Override protected PVectorM3I<T> newVectorM3I(
    final int x,
    final int y,
    final int z)
  {
    return new PVectorM3I<T>(x, y, z);
  }

  @Override protected PVectorM3I<T> newVectorM3I()
  {
    return new PVectorM3I<T>();
  }

  @Override protected PVectorM3I<T> newVectorM3I(final PVectorM3I<T> v)
  {
    return new PVectorM3I<T>(v);
  }

  @Test public void testHierarchy()
  {
    final PVectorM3I<?> v = new PVectorM3I<Object>();

    Assert.assertTrue(v instanceof Vector3IType);
    Assert.assertTrue(v instanceof Vector2IType);

    Assert.assertTrue(v instanceof VectorReadable3IType);
    Assert.assertTrue(v instanceof VectorReadable2IType);

    Assert.assertTrue(v instanceof VectorWritable3IType);
    Assert.assertTrue(v instanceof VectorWritable2IType);

    Assert.assertTrue(v instanceof PVector3IType);
    Assert.assertTrue(v instanceof PVector2IType);

    Assert.assertTrue(v instanceof PVectorReadable3IType);
    Assert.assertTrue(v instanceof PVectorReadable2IType);

    Assert.assertTrue(v instanceof PVectorWritable3IType);
    Assert.assertTrue(v instanceof PVectorWritable2IType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", PVectorM3I.class, k);
    }

    Assert.assertEquals(12L, (long) interfaces.size());
  }
}
