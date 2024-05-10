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

import com.io7m.jtensors.core.unparameterized.vectors.Vector2I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2L;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral2Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class VectorStorageIntegral2Contract
{
  protected abstract VectorStorageIntegral2Type create(int offset);

  protected abstract Generator<Vector2L> createGenerator2L();

  protected abstract Generator<Vector2I> createGenerator2I();

  protected abstract void checkEquals(
    long x,
    long y);


  @PercentPassing
  public final void testGetSet2()
  {
    final Generator<Vector2L> gen = this.createGenerator2L();
    final Vector2L v = gen.next();

    final VectorStorageIntegral2Type sv = this.create(0);
    sv.setXY(v.x(), v.y());

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }


  @PercentPassing
  public final void testGetSet2L()
  {
    final Generator<Vector2L> gen = this.createGenerator2L();
    final Vector2L v = gen.next();

    final VectorStorageIntegral2Type sv = this.create(0);
    sv.setVector2L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }


  @PercentPassing
  public final void testGetSet2I()
  {
    final Generator<Vector2I> gen = this.createGenerator2I();
    final Vector2I v = gen.next();

    final VectorStorageIntegral2Type sv = this.create(0);
    sv.setVector2I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
  }
}
