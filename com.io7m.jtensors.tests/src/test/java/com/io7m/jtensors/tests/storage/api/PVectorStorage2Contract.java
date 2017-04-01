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
import com.io7m.jtensors.generators.PVector2DGenerator;
import com.io7m.jtensors.generators.PVector2FGenerator;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating2Type;
import com.io7m.jtensors.tests.core.TestDOps;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PVectorStorage2Contract extends VectorStorage2Contract
{
  protected abstract PVectorStorageFloating2Type<Object> createWithP2(
    double x,
    double y);

  protected final PVectorStorageFloating2Type<Object> createP2()
  {
    return this.createWithP2(0.0, 0.0);
  }

  private Generator<PVector2D<Object>> createGeneratorP2D()
  {
    return PVector2DGenerator.create();
  }

  private Generator<PVector2F<Object>> createGeneratorP2F()
  {
    return PVector2FGenerator.create();
  }

  @Test
  public final void testGetSetPV2D()
  {
    final Generator<PVector2D<Object>> gen = this.createGeneratorP2D();
    final PVector2D<Object> v = gen.next();

    final PVectorStorageFloating2Type<Object> sv = this.createP2();
    sv.setPVector2D(v);
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
  }

  @Test
  public final void testGetSetPV2F()
  {
    final Generator<PVector2F<Object>> gen = this.createGeneratorP2F();
    final PVector2F<Object> v = gen.next();

    final PVectorStorageFloating2Type<Object> sv = this.createP2();
    sv.setPVector2F(v);
    this.checkAlmostEqual((double) v.x(), sv.x());
    this.checkAlmostEqual((double) v.y(), sv.y());
  }
}
