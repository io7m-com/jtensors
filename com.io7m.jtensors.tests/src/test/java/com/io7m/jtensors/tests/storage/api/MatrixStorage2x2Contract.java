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


import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix2x2F;
import com.io7m.jtensors.storage.api.unparameterized.matrices.MatrixStorage2x2Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class MatrixStorage2x2Contract
{
  protected abstract MatrixStorage2x2Type create(int offset);

  protected abstract Generator<Matrix2x2D> createGenerator2x2D();

  protected abstract Generator<Matrix2x2F> createGenerator2x2F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);

  @Test
  @PercentagePassing
  public final void testGetSet2x2D()
  {
    final Generator<Matrix2x2D> gen = this.createGenerator2x2D();
    final Matrix2x2D v = gen.next();

    final MatrixStorage2x2Type sv = this.create(0);
    sv.setMatrix2x2D(v);

    this.checkAlmostEquals(v.r0c0(), sv.r0c0());
    this.checkAlmostEquals(v.r0c1(), sv.r0c1());

    this.checkAlmostEquals(v.r1c0(), sv.r1c0());
    this.checkAlmostEquals(v.r1c1(), sv.r1c1());
  }

  @Test
  @PercentagePassing
  public final void testGetSet2x2F()
  {
    final Generator<Matrix2x2F> gen = this.createGenerator2x2F();
    final Matrix2x2F v = gen.next();

    final MatrixStorage2x2Type sv = this.create(0);
    sv.setMatrix2x2F(v);

    this.checkAlmostEquals((double) v.r0c0(), sv.r0c0());
    this.checkAlmostEquals((double) v.r0c1(), sv.r0c1());

    this.checkAlmostEquals((double) v.r1c0(), sv.r1c0());
    this.checkAlmostEquals((double) v.r1c1(), sv.r1c1());
  }
}
