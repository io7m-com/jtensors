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
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix3x3F;
import com.io7m.jtensors.storage.api.parameterized.matrices.PMatrixStorage3x3Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class PMatrixStorage3x3Contract
{
  protected abstract PMatrixStorage3x3Type<Object, Object> create(int offset);

  protected abstract Generator<PMatrix3x3D<Object, Object>> createGeneratorP3x3D();

  protected abstract Generator<PMatrix3x3F<Object, Object>> createGeneratorP3x3F();

  protected abstract Generator<Matrix3x3D> createGenerator3x3D();

  protected abstract Generator<Matrix3x3F> createGenerator3x3F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @PercentPassing
  public final void testGetSetP3x3D()
  {
    final Generator<PMatrix3x3D<Object, Object>> gen = this.createGeneratorP3x3D();
    final PMatrix3x3D<Object, Object> v = gen.next();

    final PMatrixStorage3x3Type<Object, Object> sv = this.create(0);
    sv.setPMatrix3x3D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());
    this.checkAlmostEquals(v.r0c2(), sv.r0c2());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
    this.checkAlmostEquals(v.r1c2(), sv.r1c2());

    this.checkAlmostEquals(v.r2c0(), sv.r2c0());
    this.checkAlmostEquals(v.r2c1(), sv.r2c1());
    this.checkAlmostEquals(v.r2c2(), sv.r2c2());
  }

  @PercentPassing
  public final void testGetSetP3x3F()
  {
    final Generator<PMatrix3x3F<Object, Object>> gen = this.createGeneratorP3x3F();
    final PMatrix3x3F<Object, Object> v = gen.next();

    final PMatrixStorage3x3Type<Object, Object> sv = this.create(0);
    sv.setPMatrix3x3F(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());
    this.checkAlmostEquals(v.r0c2(), sv.r0c2());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
    this.checkAlmostEquals(v.r1c2(), sv.r1c2());

    this.checkAlmostEquals(v.r2c0(), sv.r2c0());
    this.checkAlmostEquals(v.r2c1(), sv.r2c1());
    this.checkAlmostEquals(v.r2c2(), sv.r2c2());
  }

  @PercentPassing
  public final void testGetSet3x3D()
  {
    final Generator<Matrix3x3D> gen = this.createGenerator3x3D();
    final Matrix3x3D v = gen.next();

    final PMatrixStorage3x3Type<Object, Object> sv = this.create(0);
    sv.setMatrix3x3D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());
    this.checkAlmostEquals(v.r0c2(), sv.r0c2());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
    this.checkAlmostEquals(v.r1c2(), sv.r1c2());

    this.checkAlmostEquals(v.r2c0(), sv.r2c0());
    this.checkAlmostEquals(v.r2c1(), sv.r2c1());
    this.checkAlmostEquals(v.r2c2(), sv.r2c2());
  }

  @PercentPassing
  public final void testGetSet3x3F()
  {
    final Generator<Matrix3x3F> gen = this.createGenerator3x3F();
    final Matrix3x3F v = gen.next();

    final PMatrixStorage3x3Type<Object, Object> sv = this.create(0);
    sv.setMatrix3x3F(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());
    this.checkAlmostEquals(v.r0c2(), sv.r0c2());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
    this.checkAlmostEquals(v.r1c2(), sv.r1c2());

    this.checkAlmostEquals(v.r2c0(), sv.r2c0());
    this.checkAlmostEquals(v.r2c1(), sv.r2c1());
    this.checkAlmostEquals(v.r2c2(), sv.r2c2());
  }
}
