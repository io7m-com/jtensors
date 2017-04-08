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

import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4F;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage4x4Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class MatrixStorage4x4Contract
{
  protected abstract MatrixStorage4x4Type create(int offset);

  protected abstract Generator<Matrix4x4D> createGenerator4x4D();

  protected abstract Generator<Matrix4x4F> createGenerator4x4F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @Test
  @PercentagePassing
  public final void testGetSet4x4D()
  {
    final Generator<Matrix4x4D> gen = this.createGenerator4x4D();
    final Matrix4x4D v = gen.next();

    final MatrixStorage4x4Type sv = this.create(0);
    sv.setMatrix4x4D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());
    this.checkAlmostEquals(v.r0c2(), sv.r0c2());
    this.checkAlmostEquals(v.r0c3(), sv.r0c3());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
    this.checkAlmostEquals(v.r1c2(), sv.r1c2());
    this.checkAlmostEquals(v.r1c3(), sv.r1c3());

    this.checkAlmostEquals(v.r2c0(), sv.r2c0());
    this.checkAlmostEquals(v.r2c1(), sv.r2c1());
    this.checkAlmostEquals(v.r2c2(), sv.r2c2());
    this.checkAlmostEquals(v.r2c3(), sv.r2c3());

    this.checkAlmostEquals(v.r3c0(), sv.r3c0());
    this.checkAlmostEquals(v.r3c1(), sv.r3c1());
    this.checkAlmostEquals(v.r3c2(), sv.r3c2());
    this.checkAlmostEquals(v.r3c3(), sv.r3c3());
  }

  @Test
  @PercentagePassing
  public final void testGetSet4x4F()
  {
    final Generator<Matrix4x4F> gen = this.createGenerator4x4F();
    final Matrix4x4F v = gen.next();

    final MatrixStorage4x4Type sv = this.create(0);
    sv.setMatrix4x4F(v);

    this.checkAlmostEquals((double) v.r0c0(), sv.r0c0());
    this.checkAlmostEquals((double) v.r0c1(), sv.r0c1());
    this.checkAlmostEquals((double) v.r0c2(), sv.r0c2());
    this.checkAlmostEquals((double) v.r0c3(), sv.r0c3());

    this.checkAlmostEquals((double) v.r1c0(), sv.r1c0());
    this.checkAlmostEquals((double) v.r1c1(), sv.r1c1());
    this.checkAlmostEquals((double) v.r1c2(), sv.r1c2());
    this.checkAlmostEquals((double) v.r1c3(), sv.r1c3());

    this.checkAlmostEquals((double) v.r2c0(), sv.r2c0());
    this.checkAlmostEquals((double) v.r2c1(), sv.r2c1());
    this.checkAlmostEquals((double) v.r2c2(), sv.r2c2());
    this.checkAlmostEquals((double) v.r2c3(), sv.r2c3());

    this.checkAlmostEquals((double) v.r3c0(), sv.r3c0());
    this.checkAlmostEquals((double) v.r3c1(), sv.r3c1());
    this.checkAlmostEquals((double) v.r3c2(), sv.r3c2());
    this.checkAlmostEquals((double) v.r3c3(), sv.r3c3());
  }
}
