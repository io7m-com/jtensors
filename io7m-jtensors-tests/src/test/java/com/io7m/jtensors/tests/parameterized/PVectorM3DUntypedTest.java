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

import com.io7m.jtensors.parameterized.PVectorM3D;
import com.io7m.jtensors.tests.VectorM3DContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PVectorM3DUntypedTest<T>
  extends VectorM3DContract<PVectorM3D<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM3DUntypedTest.class);
  }

  @Override protected double delta()
  {
    return 0.0000000000001;
  }

  @Override protected double randomLargeNegative()
  {
    return Math.random() * -100000000.0;
  }

  @Override protected double randomLargePositive()
  {
    return Math.random() * 100000000.0;
  }

  @Override protected Logger logger()
  {
    return PVectorM3DUntypedTest.LOG;
  }

  @Override protected PVectorM3D<T> newVectorM3D(final PVectorM3D<T> v)
  {
    return new PVectorM3D<T>(v);
  }

  @Override protected PVectorM3D<T> newVectorM3D()
  {
    return new PVectorM3D<T>();
  }

  @Override protected PVectorM3D<T> newVectorM3D(
    final double x,
    final double y,
    final double z)
  {
    return new PVectorM3D<T>(x, y, z);
  }
}
