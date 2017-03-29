Quaternion4(
  className,
  quaternionType,
  quaternionComponentType,
  scalarLargeType,
  scalarRealType,
  opClass,
  dotProductClass,
  hasFloatingPoint,
  hasGeneric) ::= <<
/*
 * Copyright © 2017 \<code@io7m.com\> http://io7m.com
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

package com.io7m.jtensors.core.quaternions;

import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * \<p>Functions over {@link <quaternionType>} values.\</p>
 * \<p>
 * See "Mathematics for 3D Game Programming and Computer Graphics" 2nd Ed
 * for the derivations of most of the code in this class (ISBN: 1-58450-277-0).
 * \</p>
 * @since 8.0.0
 */

public final class <className>
{
  private <className>()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Add {@code q0} to {@code q1}.
   *
   * @return {@code (q0.x + q1.x, q0.y + q0.y, q0.z + q1.z, q0.w + q1.w) }
   *
   * @param q0 The left vector
   * @param q1 The right vector
   */

  public static <quaternionType> add(
    final <quaternionType> q0,
    final <quaternionType> q1)
  {
    return <quaternionType>.of(
      (<quaternionComponentType>) <opClass>.add(q0.x(), q1.x()),
      (<quaternionComponentType>) <opClass>.add(q0.y(), q1.y()),
      (<quaternionComponentType>) <opClass>.add(q0.z(), q1.z()),
      (<quaternionComponentType>) <opClass>.add(q0.w(), q1.w()));
  }
}
>>
