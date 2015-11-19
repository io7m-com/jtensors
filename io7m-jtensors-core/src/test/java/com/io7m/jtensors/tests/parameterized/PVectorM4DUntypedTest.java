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

import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.tests.VectorM4DContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PVectorM4DUntypedTest<T>
  extends VectorM4DContract<PVectorM4D<T>>
{
  private static final Logger LOG;

  static {
    LOG = LoggerFactory.getLogger(PVectorM4DUntypedTest.class);
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
    return PVectorM4DUntypedTest.LOG;
  }

  @Override protected PVectorM4D<T> newVectorM4D()
  {
    return new PVectorM4D<T>();
  }

  @Override protected PVectorM4D<T> newVectorM4DFrom(final PVectorM4D<T> v)
  {
    return new PVectorM4D<T>(v);
  }

  @Override protected PVectorM4D<T> newVectorM4D(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    return new PVectorM4D<T>(x, y, z, w);
  }
}
