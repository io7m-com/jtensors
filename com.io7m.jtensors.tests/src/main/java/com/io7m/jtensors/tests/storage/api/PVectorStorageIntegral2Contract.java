/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jtensors.tests.storage.api;

import com.io7m.jtensors.core.parameterized.vectors.PVector2I;
import com.io7m.jtensors.core.parameterized.vectors.PVector2L;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral2Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class PVectorStorageIntegral2Contract
{
  protected abstract PVectorStorageIntegral2Type<Object> create(int offset);

  protected abstract Generator<PVector2L<Object>> createGenerator2L();

  protected abstract Generator<PVector2I<Object>> createGenerator2I();

  protected abstract void checkEquals(
    long x,
    long y);


  @PercentPassing
  public final void testGetSet2()
  {
    final Generator<PVector2L<Object>> gen = this.createGenerator2L();
    final PVector2L<Object> v = gen.next();

    final PVectorStorageIntegral2Type<Object> sv = this.create(0);
    sv.setXY(v.x(), v.y());

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }


  @PercentPassing
  public final void testGetSet2L()
  {
    final Generator<PVector2L<Object>> gen = this.createGenerator2L();
    final PVector2L<Object> v = gen.next();

    final PVectorStorageIntegral2Type<Object> sv = this.create(0);
    sv.setPVector2L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }


  @PercentPassing
  public final void testGetSet2I()
  {
    final Generator<PVector2I<Object>> gen = this.createGenerator2I();
    final PVector2I<Object> v = gen.next();

    final PVectorStorageIntegral2Type<Object> sv = this.create(0);
    sv.setPVector2I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }
}
