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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;
import com.io7m.jtensors.generators.Matrix3x3DGenerator;
import com.io7m.jtensors.generators.Matrix3x3FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage3x3Type;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class MatrixStorage3x3Contract
{
  private static Generator<Matrix3x3D> createGeneratorP3x3D()
  {
    return Matrix3x3DGenerator.createNormal();
  }

  private static Generator<Matrix3x3F> createGeneratorP3x3F()
  {
    return Matrix3x3FGenerator.createNormal();
  }

  protected abstract void checkAlmostEqual(
    double a,
    double b);

  protected abstract MatrixStorage3x3Type createIdentity();

  @Test
  public final void testGetSetV3D()
  {
    final Generator<Matrix3x3D> gen = createGeneratorP3x3D();
    final Matrix3x3D m = gen.next();
    final MatrixStorage3x3Type sv = this.createIdentity();
    sv.setMatrix3x3D(m);

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
  public final void testGetSetV3F()
  {
    final Generator<Matrix3x3F> gen = createGeneratorP3x3F();
    final Matrix3x3F m = gen.next();
    final MatrixStorage3x3Type sv = this.createIdentity();
    sv.setMatrix3x3F(m);

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
