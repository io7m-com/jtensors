/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4F;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating4Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class VectorStorageFloating4Contract
{
  protected abstract VectorStorageFloating4Type create(int offset);

  protected abstract Generator<Vector4D> createGenerator4D();

  protected abstract Generator<Vector4F> createGenerator4F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);


  @PercentPassing
  public final void testGetSet4()
  {
    final Generator<Vector4D> gen = this.createGenerator4D();
    final Vector4D v = gen.next();

    final VectorStorageFloating4Type sv = this.create(0);
    sv.setXYZW(v.x(), v.y(), v.z(), v.w());

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
    this.checkAlmostEquals(v.w(), sv.w());
  }


  @PercentPassing
  public final void testGetSet4D()
  {
    final Generator<Vector4D> gen = this.createGenerator4D();
    final Vector4D v = gen.next();

    final VectorStorageFloating4Type sv = this.create(0);
    sv.setVector4D(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
    this.checkAlmostEquals(v.w(), sv.w());
  }


  @PercentPassing
  public final void testGetSet4F()
  {
    final Generator<Vector4F> gen = this.createGenerator4F();
    final Vector4F v = gen.next();

    final VectorStorageFloating4Type sv = this.create(0);
    sv.setVector4F(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
    this.checkAlmostEquals(v.w(), sv.w());
  }
}
