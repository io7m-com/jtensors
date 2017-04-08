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

import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2F;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class VectorStorageFloating2Contract
{
  protected abstract VectorStorageFloating2Type create(int offset);

  protected abstract Generator<Vector2D> createGenerator2D();

  protected abstract Generator<Vector2F> createGenerator2F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @Test
  @PercentagePassing
  public final void testGetSet2()
  {
    final Generator<Vector2D> gen = this.createGenerator2D();
    final Vector2D v = gen.next();

    final VectorStorageFloating2Type sv = this.create(0);
    sv.setXY(v.x(), v.y());

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }

  @Test
  @PercentagePassing
  public final void testGetSet2D()
  {
    final Generator<Vector2D> gen = this.createGenerator2D();
    final Vector2D v = gen.next();

    final VectorStorageFloating2Type sv = this.create(0);
    sv.setVector2D(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }

  @Test
  @PercentagePassing
  public final void testGetSet2F()
  {
    final Generator<Vector2F> gen = this.createGenerator2F();
    final Vector2F v = gen.next();

    final VectorStorageFloating2Type sv = this.create(0);
    sv.setVector2F(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
  }
}
