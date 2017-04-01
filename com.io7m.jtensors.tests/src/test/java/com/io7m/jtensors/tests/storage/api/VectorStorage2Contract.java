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
import com.io7m.jtensors.generators.Vector2DGenerator;
import com.io7m.jtensors.generators.Vector2FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class VectorStorage2Contract
{
  protected abstract void checkAlmostEqual(
    double a,
    double b);

  protected abstract VectorStorageFloating2Type createWith2(
    double x,
    double y);

  protected final VectorStorageFloating2Type create2()
  {
    return this.createWith2(0.0, 0.0);
  }

  private Generator<Vector2D> createGenerator2D()
  {
    return Vector2DGenerator.createNormal();
  }

  private Generator<Vector2F> createGenerator2F()
  {
    return Vector2FGenerator.createNormal();
  }

  @Test
  public final void testGetSet2()
  {
    final Generator<Vector2D> gen = this.createGenerator2D();
    final Vector2D v = gen.next();

    final VectorStorageFloating2Type sv = this.create2();
    sv.setXY(v.x(), v.y());
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
  }

  @Test
  public final void testGetSetV2D()
  {
    final Generator<Vector2D> gen = this.createGenerator2D();
    final Vector2D v = gen.next();

    final VectorStorageFloating2Type sv = this.create2();
    sv.setVector2D(v);
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
  }

  @Test
  public final void testGetSetV2F()
  {
    final Generator<Vector2F> gen = this.createGenerator2F();
    final Vector2F v = gen.next();

    final VectorStorageFloating2Type sv = this.create2();
    sv.setVector2F(v);
    this.checkAlmostEqual((double) v.x(), sv.x());
    this.checkAlmostEqual((double) v.y(), sv.y());
  }
}
