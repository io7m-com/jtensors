/*
 * Copyright © 2017 <code@io7m.com> http://io7m.com
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


import com.io7m.jtensors.core.parameterized.vectors.PVector2D;
import com.io7m.jtensors.core.parameterized.vectors.PVector2F;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating2Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PVectorStorageFloating2Contract
{
  protected abstract PVectorStorageFloating2Type<Object> create(int offset);

  protected abstract Generator<PVector2D<Object>> createGenerator2D();

  protected abstract Generator<PVector2F<Object>> createGenerator2F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @Test
  @PercentagePassing
  public final void testGetSet2()
  {
    final Generator<PVector2D<Object>> gen = this.createGenerator2D();
    final PVector2D<Object> v = gen.next();

    final PVectorStorageFloating2Type<Object> sv = this.create(0);
    sv.setXY(v.x(), v.y());

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }

  @Test
  @PercentagePassing
  public final void testGetSet2D()
  {
    final Generator<PVector2D<Object>> gen = this.createGenerator2D();
    final PVector2D<Object> v = gen.next();

    final PVectorStorageFloating2Type<Object> sv = this.create(0);
    sv.setPVector2D(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }

  @Test
  @PercentagePassing
  public final void testGetSet2F()
  {
    final Generator<PVector2F<Object>> gen = this.createGenerator2F();
    final PVector2F<Object> v = gen.next();

    final PVectorStorageFloating2Type<Object> sv = this.create(0);
    sv.setPVector2F(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }
}
