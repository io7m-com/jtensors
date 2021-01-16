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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3F;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class VectorStorageFloating3Contract
{
  protected abstract VectorStorageFloating3Type create(int offset);

  protected abstract Generator<Vector3D> createGenerator3D();

  protected abstract Generator<Vector3F> createGenerator3F();

  protected abstract void checkAlmostEquals(
    double x,
    double y);


  @PercentPassing
  public final void testGetSet3()
  {
    final Generator<Vector3D> gen = this.createGenerator3D();
    final Vector3D v = gen.next();

    final VectorStorageFloating3Type sv = this.create(0);
    sv.setXYZ(v.x(), v.y(), v.z());

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
  }


  @PercentPassing
  public final void testGetSet3D()
  {
    final Generator<Vector3D> gen = this.createGenerator3D();
    final Vector3D v = gen.next();

    final VectorStorageFloating3Type sv = this.create(0);
    sv.setVector3D(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
  }


  @PercentPassing
  public final void testGetSet3F()
  {
    final Generator<Vector3F> gen = this.createGenerator3F();
    final Vector3F v = gen.next();

    final VectorStorageFloating3Type sv = this.create(0);
    sv.setVector3F(v);

    this.checkAlmostEquals(v.x(), sv.x());
    this.checkAlmostEquals(v.y(), sv.y());
    this.checkAlmostEquals(v.z(), sv.z());
  }
}
