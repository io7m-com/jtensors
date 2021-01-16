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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3L;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral3Type;
import com.io7m.percentpass.extension.PercentPassing;
import net.java.quickcheck.Generator;

public abstract class VectorStorageIntegral3Contract
{
  protected abstract VectorStorageIntegral3Type create(int offset);

  protected abstract Generator<Vector3L> createGenerator3L();

  protected abstract Generator<Vector3I> createGenerator3I();

  protected abstract void checkEquals(
    long x,
    long y);


  @PercentPassing
  public final void testGetSet3()
  {
    final Generator<Vector3L> gen = this.createGenerator3L();
    final Vector3L v = gen.next();

    final VectorStorageIntegral3Type sv = this.create(0);
    sv.setXYZ(v.x(), v.y(), v.z());

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }


  @PercentPassing
  public final void testGetSet3L()
  {
    final Generator<Vector3L> gen = this.createGenerator3L();
    final Vector3L v = gen.next();

    final VectorStorageIntegral3Type sv = this.create(0);
    sv.setVector3L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }


  @PercentPassing
  public final void testGetSet3I()
  {
    final Generator<Vector3I> gen = this.createGenerator3I();
    final Vector3I v = gen.next();

    final VectorStorageIntegral3Type sv = this.create(0);
    sv.setVector3I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }
}
