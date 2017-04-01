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
import com.io7m.jtensors.generators.Vector3DGenerator;
import com.io7m.jtensors.generators.Vector3FGenerator;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;
import com.io7m.jtensors.tests.core.TestDOps;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class VectorStorage3Contract extends VectorStorage2Contract
{
  protected abstract VectorStorageFloating3Type createWith3(
    double x,
    double y,
    double z);

  protected final VectorStorageFloating3Type create3()
  {
    return this.createWith3(0.0, 0.0, 0.0);
  }

  private Generator<Vector3D> createGenerator3D()
  {
    return Vector3DGenerator.create();
  }

  private Generator<Vector3F> createGenerator3F()
  {
    return Vector3FGenerator.create();
  }

  @Test
  public final void testGetSet3()
  {
    final Generator<Vector3D> gen = this.createGenerator3D();
    final Vector3D v = gen.next();

    final VectorStorageFloating3Type sv = this.create3();
    sv.setXYZ(v.x(), v.y(), v.z());
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
    this.checkAlmostEqual(v.z(), sv.z());
  }

  @Test
  public final void testGetSetV3D()
  {
    final Generator<Vector3D> gen = this.createGenerator3D();
    final Vector3D v = gen.next();

    final VectorStorageFloating3Type sv = this.create3();
    sv.setVector3D(v);
    this.checkAlmostEqual(v.x(), sv.x());
    this.checkAlmostEqual(v.y(), sv.y());
    this.checkAlmostEqual(v.z(), sv.z());
  }

  @Test
  public final void testGetSetV3F()
  {
    final Generator<Vector3F> gen = this.createGenerator3F();
    final Vector3F v = gen.next();

    final VectorStorageFloating3Type sv = this.create3();
    sv.setVector3F(v);
    this.checkAlmostEqual((double) v.x(), sv.x());
    this.checkAlmostEqual((double) v.y(), sv.y());
    this.checkAlmostEqual((double) v.z(), sv.z());
  }

}
