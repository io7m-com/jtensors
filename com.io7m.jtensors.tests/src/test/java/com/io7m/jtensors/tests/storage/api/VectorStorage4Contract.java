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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4F;
import com.io7m.jtensors.generators.Vector4DGenerator;
import com.io7m.jtensors.generators.Vector4FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating4Type;
import com.io7m.jtensors.tests.core.TestDOps;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class VectorStorage4Contract extends VectorStorage3Contract
{
  protected abstract VectorStorageFloating4Type createWith4(
    double x,
    double y,
    double z,
    double w);

  protected final VectorStorageFloating4Type create4()
  {
    return this.createWith4(0.0, 0.0, 0.0, 1.0);
  }

  private Generator<Vector4D> createGenerator4D()
  {
    return Vector4DGenerator.create();
  }

  private Generator<Vector4F> createGenerator4F()
  {
    return Vector4FGenerator.create();
  }

  @Test
  public final void testGetSet4()
  {
    final Generator<Vector4D> gen = this.createGenerator4D();
    final Vector4D v = gen.next();

    final VectorStorageFloating4Type sv = this.create4();
    sv.setXYZW(v.x(), v.y(), v.z(), v.w());
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
    this.checkAlmostEqual(v.z(), sv.z());
    this.checkAlmostEqual(v.w(), sv.w());
  }

  @Test
  public final void testGetSetV4D()
  {
    final Generator<Vector4D> gen = this.createGenerator4D();
    final Vector4D v = gen.next();

    final VectorStorageFloating4Type sv = this.create4();
    sv.setVector4D(v);
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
    this.checkAlmostEqual(v.z(), sv.z());
    this.checkAlmostEqual(v.w(), sv.w());
  }

  @Test
  public final void testGetSetV4F()
  {
    final Generator<Vector4F> gen = this.createGenerator4F();
    final Vector4F v = gen.next();

    final VectorStorageFloating4Type sv = this.create4();
    sv.setVector4F(v);
    this.checkAlmostEqual((double) v.x(), sv.x());
    this.checkAlmostEqual((double) v.y(), sv.y());
    this.checkAlmostEqual((double) v.z(), sv.z());
    this.checkAlmostEqual((double) v.w(), sv.w());
  }

}
