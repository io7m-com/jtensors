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

import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4F;
import com.io7m.jtensors.generators.PMatrix4x4DGenerator;
import com.io7m.jtensors.generators.PMatrix4x4FGenerator;
import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage4x4Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PMatrixStorage4x4Contract extends MatrixStorage4x4Contract
{
  private static Generator<PMatrix4x4D<Object, Object>> createGeneratorP4x4D()
  {
    return PMatrix4x4DGenerator.createNormal();
  }

  private static Generator<PMatrix4x4F<Object, Object>> createGeneratorP4x4F()
  {
    return PMatrix4x4FGenerator.createNormal();
  }

  protected abstract PMatrixStorage4x4Type<Object, Object> createIdentity();

  @Test
  public final void testGetSetPV4D()
  {
    final Generator<PMatrix4x4D<Object, Object>> gen = createGeneratorP4x4D();
    final PMatrix4x4D<Object, Object> m = gen.next();
    final PMatrixStorage4x4Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix4x4D(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        this.checkAlmostEqual(
          m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual(m.r0c0(), sv.r0c0());
    this.checkAlmostEqual(m.r0c1(), sv.r0c1());
    this.checkAlmostEqual(m.r0c2(), sv.r0c2());
    this.checkAlmostEqual(m.r0c3(), sv.r0c3());

    this.checkAlmostEqual(m.r1c0(), sv.r1c0());
    this.checkAlmostEqual(m.r1c1(), sv.r1c1());
    this.checkAlmostEqual(m.r1c2(), sv.r1c2());
    this.checkAlmostEqual(m.r1c3(), sv.r1c3());

    this.checkAlmostEqual(m.r2c0(), sv.r2c0());
    this.checkAlmostEqual(m.r2c1(), sv.r2c1());
    this.checkAlmostEqual(m.r2c2(), sv.r2c2());
    this.checkAlmostEqual(m.r2c3(), sv.r2c3());

    this.checkAlmostEqual(m.r3c0(), sv.r3c0());
    this.checkAlmostEqual(m.r3c1(), sv.r3c1());
    this.checkAlmostEqual(m.r3c2(), sv.r3c2());
    this.checkAlmostEqual(m.r3c3(), sv.r3c3());
  }

  @Test
  public final void testGetSetPV4F()
  {
    final Generator<PMatrix4x4F<Object, Object>> gen = createGeneratorP4x4F();
    final PMatrix4x4F<Object, Object> m = gen.next();
    final PMatrixStorage4x4Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix4x4F(m);

    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        this.checkAlmostEqual(
          (double) m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual((double) m.r0c0(), sv.r0c0());
    this.checkAlmostEqual((double) m.r0c1(), sv.r0c1());
    this.checkAlmostEqual((double) m.r0c2(), sv.r0c2());
    this.checkAlmostEqual((double) m.r0c3(), sv.r0c3());

    this.checkAlmostEqual((double) m.r1c0(), sv.r1c0());
    this.checkAlmostEqual((double) m.r1c1(), sv.r1c1());
    this.checkAlmostEqual((double) m.r1c2(), sv.r1c2());
    this.checkAlmostEqual((double) m.r1c3(), sv.r1c3());

    this.checkAlmostEqual((double) m.r2c0(), sv.r2c0());
    this.checkAlmostEqual((double) m.r2c1(), sv.r2c1());
    this.checkAlmostEqual((double) m.r2c2(), sv.r2c2());
    this.checkAlmostEqual((double) m.r2c3(), sv.r2c3());

    this.checkAlmostEqual((double) m.r3c0(), sv.r3c0());
    this.checkAlmostEqual((double) m.r3c1(), sv.r3c1());
    this.checkAlmostEqual((double) m.r3c2(), sv.r3c2());
    this.checkAlmostEqual((double) m.r3c3(), sv.r3c3());
  }
}
