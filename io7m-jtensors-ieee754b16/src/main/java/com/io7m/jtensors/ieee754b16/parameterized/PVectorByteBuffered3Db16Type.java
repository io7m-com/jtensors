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

package com.io7m.jtensors.ieee754b16.parameterized;

import com.io7m.jtensors.bytebuffered.parameterized.PVectorByteBuffered3DType;
import com.io7m.jtensors.ieee754b16.VectorByteBuffered3Db16Type;

/**
 * The type of {@link PVector3Db16Type} values that are also byte buffered.
 *
 * @param <T> A phantom type parameter
 *
 * @since 7.0.0
 */

public interface PVectorByteBuffered3Db16Type<T> extends PVector3Db16Type<T>,
  PVectorByteBuffered3DType<T>,
  PVectorByteBuffered2Db16Type<T>,
  VectorByteBuffered3Db16Type
{
  // No extra functions
}
