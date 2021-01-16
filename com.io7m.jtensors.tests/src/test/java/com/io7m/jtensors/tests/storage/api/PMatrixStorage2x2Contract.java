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
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;
import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage2x2Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class PMatrixStorage2x2Contract
{
  protected abstract PMatrixStorage2x2Type<Object, Object> create(int offset);

  protected abstract Generator<PMatrix2x2D<Object, Object>> createGeneratorP2x2D();

  protected abstract Generator<PMatrix2x2F<Object, Object>> createGeneratorP2x2F();

  protected abstract Generator<Matrix2x2D> createGenerator2x2D();

  protected abstract Generator<Matrix2x2F> createGenerator2x2F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @PercentPassing
  public final void testGetSetP2x2D()
  {
    final Generator<PMatrix2x2D<Object, Object>> gen = this.createGeneratorP2x2D();
    final PMatrix2x2D<Object, Object> v = gen.next();

    final PMatrixStorage2x2Type<Object, Object> sv = this.create(0);
    sv.setPMatrix2x2D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
  }

  @PercentPassing
  public final void testGetSetP2x2F()
  {
    final Generator<PMatrix2x2F<Object, Object>> gen = this.createGeneratorP2x2F();
    final PMatrix2x2F<Object, Object> v = gen.next();

    final PMatrixStorage2x2Type<Object, Object> sv = this.create(0);
    sv.setPMatrix2x2F(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
  }

  @PercentPassing
  public final void testGetSet2x2D()
  {
    final Generator<Matrix2x2D> gen = this.createGenerator2x2D();
    final Matrix2x2D v = gen.next();

    final PMatrixStorage2x2Type<Object, Object> sv = this.create(0);
    sv.setMatrix2x2D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
  }

  @PercentPassing
  public final void testGetSet2x2F()
  {
    final Generator<Matrix2x2F> gen = this.createGenerator2x2F();
    final Matrix2x2F v = gen.next();

    final PMatrixStorage2x2Type<Object, Object> sv = this.create(0);
    sv.setMatrix2x2F(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
  }
}
