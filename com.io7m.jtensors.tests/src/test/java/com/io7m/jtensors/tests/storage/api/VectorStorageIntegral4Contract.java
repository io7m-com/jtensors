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

import com.io7m.jtensors.core.unparameterized.vectors.Vector4I;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4L;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageIntegral4Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class VectorStorageIntegral4Contract
{
  protected abstract VectorStorageIntegral4Type create(int offset);

  protected abstract Generator<Vector4L> createGenerator4L();

  protected abstract Generator<Vector4I> createGenerator4I();

  protected abstract void checkEquals(
    long x,
    long y);

  @Test
  @PercentagePassing
  public final void testGetSet4()
  {
    final Generator<Vector4L> gen = this.createGenerator4L();
    final Vector4L v = gen.next();

    final VectorStorageIntegral4Type sv = this.create(0);
    sv.setXYZW(v.x(), v.y(), v.z(), v.w());

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
    this.checkEquals(v.w(), sv.w());
  }

  @Test
  @PercentagePassing
  public final void testGetSet4L()
  {
    final Generator<Vector4L> gen = this.createGenerator4L();
    final Vector4L v = gen.next();

    final VectorStorageIntegral4Type sv = this.create(0);
    sv.setVector4L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
    this.checkEquals(v.w(), sv.w());
  }

  @Test
  @PercentagePassing
  public final void testGetSet4I()
  {
    final Generator<Vector4I> gen = this.createGenerator4I();
    final Vector4I v = gen.next();

    final VectorStorageIntegral4Type sv = this.create(0);
    sv.setVector4I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
    this.checkEquals(v.w(), sv.w());
  }
}
