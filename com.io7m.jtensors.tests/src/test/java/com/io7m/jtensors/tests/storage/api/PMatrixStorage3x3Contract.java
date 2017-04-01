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

import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix3x3F;
import com.io7m.jtensors.generators.PMatrix3x3DGenerator;
import com.io7m.jtensors.generators.PMatrix3x3FGenerator;
import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage3x3Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PMatrixStorage3x3Contract extends MatrixStorage3x3Contract
{
  protected abstract PMatrixStorage3x3Type<Object, Object> createIdentity();

  private static Generator<PMatrix3x3D<Object, Object>> createGeneratorP3x3D()
  {
    return PMatrix3x3DGenerator.create();
  }

  private static Generator<PMatrix3x3F<Object, Object>> createGeneratorP3x3F()
  {
    return PMatrix3x3FGenerator.create();
  }

  @Test
  public final void testGetSetPV3D()
  {
    final Generator<PMatrix3x3D<Object, Object>> gen = createGeneratorP3x3D();
    final PMatrix3x3D<Object, Object> m = gen.next();
    final PMatrixStorage3x3Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix3x3D(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        this.checkAlmostEqual(
          m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual(m.r0c0(), sv.r0c0());
    this.checkAlmostEqual(m.r0c1(), sv.r0c1());
    this.checkAlmostEqual(m.r0c2(), sv.r0c2());

    this.checkAlmostEqual(m.r1c0(), sv.r1c0());
    this.checkAlmostEqual(m.r1c1(), sv.r1c1());
    this.checkAlmostEqual(m.r1c2(), sv.r1c2());

    this.checkAlmostEqual(m.r2c0(), sv.r2c0());
    this.checkAlmostEqual(m.r2c1(), sv.r2c1());
    this.checkAlmostEqual(m.r2c2(), sv.r2c2());
  }

  @Test
  public final void testGetSetPV3F()
  {
    final Generator<PMatrix3x3F<Object, Object>> gen = createGeneratorP3x3F();
    final PMatrix3x3F<Object, Object> m = gen.next();
    final PMatrixStorage3x3Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix3x3F(m);

    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 3; ++column) {
        this.checkAlmostEqual(
          (double) m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual((double) m.r0c0(), sv.r0c0());
    this.checkAlmostEqual((double) m.r0c1(), sv.r0c1());
    this.checkAlmostEqual((double) m.r0c2(), sv.r0c2());

    this.checkAlmostEqual((double) m.r1c0(), sv.r1c0());
    this.checkAlmostEqual((double) m.r1c1(), sv.r1c1());
    this.checkAlmostEqual((double) m.r1c2(), sv.r1c2());

    this.checkAlmostEqual((double) m.r2c0(), sv.r2c0());
    this.checkAlmostEqual((double) m.r2c1(), sv.r2c1());
    this.checkAlmostEqual((double) m.r2c2(), sv.r2c2());
  }
}
