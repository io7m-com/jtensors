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

import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.tests.VectorM3IContract;

public final class PVectorM3IUntypedTest<T>
  extends VectorM3IContract<PVectorM3I<T>>
{
  @Override
  protected PVectorM3I<T> newVectorM3I(
    final int x,
    final int y,
    final int z)
  {
    return new PVectorM3I<T>(x, y, z);
  }

  @Override
  protected PVectorM3I<T> newVectorM3I()
  {
    return new PVectorM3I<T>();
  }

  @Override
  protected PVectorM3I<T> newVectorM3I(final PVectorM3I<T> v)
  {
    return new PVectorM3I<T>(v);
  }
}
