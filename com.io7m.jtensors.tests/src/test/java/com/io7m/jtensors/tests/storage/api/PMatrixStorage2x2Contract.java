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

import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix2x2F;
import com.io7m.jtensors.generators.PMatrix2x2DGenerator;
import com.io7m.jtensors.generators.PMatrix2x2FGenerator;
import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage2x2Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PMatrixStorage2x2Contract extends MatrixStorage2x2Contract
{
  protected abstract PMatrixStorage2x2Type<Object, Object> createIdentity();

  private static Generator<PMatrix2x2D<Object, Object>> createGeneratorP2x2D()
  {
    return PMatrix2x2DGenerator.create();
  }

  private static Generator<PMatrix2x2F<Object, Object>> createGeneratorP2x2F()
  {
    return PMatrix2x2FGenerator.create();
  }

  @Test
  public final void testGetSetPV2D()
  {
    final Generator<PMatrix2x2D<Object, Object>> gen = createGeneratorP2x2D();
    final PMatrix2x2D<Object, Object> m = gen.next();
    final PMatrixStorage2x2Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix2x2D(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        this.checkAlmostEqual(
          m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual(m.r0c0(), sv.r0c0());
    this.checkAlmostEqual(m.r0c1(), sv.r0c1());

    this.checkAlmostEqual(m.r1c0(), sv.r1c0());
    this.checkAlmostEqual(m.r1c1(), sv.r1c1());
  }

  @Test
  public final void testGetSetPV2F()
  {
    final Generator<PMatrix2x2F<Object, Object>> gen = createGeneratorP2x2F();
    final PMatrix2x2F<Object, Object> m = gen.next();
    final PMatrixStorage2x2Type<Object, Object> sv = this.createIdentity();
    sv.setPMatrix2x2F(m);

    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        this.checkAlmostEqual(
          (double) m.rowColumn(row, column),
          sv.rowColumn(row, column));
      }
    }

    this.checkAlmostEqual((double) m.r0c0(), sv.r0c0());
    this.checkAlmostEqual((double) m.r0c1(), sv.r0c1());

    this.checkAlmostEqual((double) m.r1c0(), sv.r1c0());
    this.checkAlmostEqual((double) m.r1c1(), sv.r1c1());
  }
}
