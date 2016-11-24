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

import com.io7m.jtensors.Vector2IType;
import com.io7m.jtensors.Vector3IType;
import com.io7m.jtensors.Vector4IType;
import com.io7m.jtensors.VectorReadable2IType;
import com.io7m.jtensors.VectorReadable3IType;
import com.io7m.jtensors.VectorReadable4IType;
import com.io7m.jtensors.VectorWritable2IType;
import com.io7m.jtensors.VectorWritable3IType;
import com.io7m.jtensors.VectorWritable4IType;
import com.io7m.jtensors.parameterized.PVector2IType;
import com.io7m.jtensors.parameterized.PVector3IType;
import com.io7m.jtensors.parameterized.PVector4IType;
import com.io7m.jtensors.parameterized.PVectorM4I;
import com.io7m.jtensors.parameterized.PVectorReadable2IType;
import com.io7m.jtensors.parameterized.PVectorReadable3IType;
import com.io7m.jtensors.parameterized.PVectorReadable4IType;
import com.io7m.jtensors.parameterized.PVectorWritable2IType;
import com.io7m.jtensors.parameterized.PVectorWritable3IType;
import com.io7m.jtensors.parameterized.PVectorWritable4IType;
import com.io7m.jtensors.tests.TestUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public final class PVectorM4ITest<T>
  extends PVectorM4IContract<T, PVectorM4I<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM4ITest.class);
  }

  @Override protected PVectorM4I<T> newVectorM4I(
    final int x,
    final int y,
    final int z,
    final int w)
  {
    return new PVectorM4I<T>(x, y, z, w);
  }

  @Override protected PVectorM4I<T> newVectorM4I()
  {
    return new PVectorM4I<T>();
  }

  @Override protected PVectorM4I<T> newVectorM4IFrom(final PVectorM4I<T> v)
  {
    return new PVectorM4I<T>(v);
  }

  @Test public void testHierarchy()
  {
    final PVectorM4I<?> v = new PVectorM4I<Object>();

    Assert.assertTrue(v instanceof Vector4IType);
    Assert.assertTrue(v instanceof Vector3IType);
    Assert.assertTrue(v instanceof Vector2IType);

    Assert.assertTrue(v instanceof VectorReadable4IType);
    Assert.assertTrue(v instanceof VectorReadable3IType);
    Assert.assertTrue(v instanceof VectorReadable2IType);

    Assert.assertTrue(v instanceof VectorWritable4IType);
    Assert.assertTrue(v instanceof VectorWritable3IType);
    Assert.assertTrue(v instanceof VectorWritable2IType);

    Assert.assertTrue(v instanceof PVector4IType);
    Assert.assertTrue(v instanceof PVector3IType);
    Assert.assertTrue(v instanceof PVector2IType);

    Assert.assertTrue(v instanceof PVectorReadable4IType);
    Assert.assertTrue(v instanceof PVectorReadable3IType);
    Assert.assertTrue(v instanceof PVectorReadable2IType);

    Assert.assertTrue(v instanceof PVectorWritable4IType);
    Assert.assertTrue(v instanceof PVectorWritable3IType);
    Assert.assertTrue(v instanceof PVectorWritable2IType);

    final SortedMap<String, Class<?>> interfaces =
      TestUtilities.getInterfaces(v.getClass());
    for (final String k : interfaces.keySet()) {
      LOG.debug(
        "{} implements {}", PVectorM4I.class, k);
    }

    Assert.assertEquals(18L, (long) interfaces.size());
  }
}
