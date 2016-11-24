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

import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.tests.VectorM2FContract;

public final class PVectorM2FUntypedTest<T>
  extends VectorM2FContract<PVectorM2F<T>>
{
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
}
