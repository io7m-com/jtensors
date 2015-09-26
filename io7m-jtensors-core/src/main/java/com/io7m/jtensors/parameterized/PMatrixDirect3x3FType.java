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

package com.io7m.jtensors.parameterized;

import com.io7m.jtensors.MatrixDirect3x3FType;

/**
 * <p>The type of 3x3 matrices with {@code float} elements that are backed by
 * direct byte buffers.</p>
 *
 * <p>Values of type {@code PMatrixDirect3x3FType} are backed by direct memory,
 * with the rows and columns of the matrices being stored in column-major
 * format. This allows the matrices to be passed to OpenGL directly, without
 * requiring transposition.</p>
 *
 * <p>Values of this type cannot be accessed safely from multiple threads
 * without explicit synchronization.</p>
 *
 * @param <T0> A phantom type parameter
 * @param <T1> A phantom type parameter
 *
 * @since 7.0.0
 */

public interface PMatrixDirect3x3FType<T0, T1> extends PMatrix3x3FType<T0, T1>,
  PMatrixDirectReadable3x3FType<T0, T1>,
  MatrixDirect3x3FType
{
  // No extra functions
}
