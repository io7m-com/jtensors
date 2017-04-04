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

import com.io7m.jtensors.core.parameterized.vectors.PVector4I;
import com.io7m.jtensors.core.parameterized.vectors.PVector4L;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral4Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PVectorStorageIntegral4Contract
{
  protected abstract PVectorStorageIntegral4Type<Object> create(int offset);

  protected abstract Generator<PVector4L<Object>> createGenerator4L();

  protected abstract Generator<PVector4I<Object>> createGenerator4I();

  protected abstract void checkEquals(
    long x,
    long y);

  @Test
  @PercentagePassing
  public final void testGetSet4()
  {
    final Generator<PVector4L<Object>> gen = this.createGenerator4L();
    final PVector4L<Object> v = gen.next();

    final PVectorStorageIntegral4Type<Object> sv = this.create(0);
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
    final Generator<PVector4L<Object>> gen = this.createGenerator4L();
    final PVector4L<Object> v = gen.next();

    final PVectorStorageIntegral4Type<Object> sv = this.create(0);
    sv.setPVector4L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
    this.checkEquals(v.w(), sv.w());
  }

  @Test
  @PercentagePassing
  public final void testGetSet4I()
  {
    final Generator<PVector4I<Object>> gen = this.createGenerator4I();
    final PVector4I<Object> v = gen.next();

    final PVectorStorageIntegral4Type<Object> sv = this.create(0);
    sv.setPVector4I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
    this.checkEquals(v.w(), sv.w());
  }
}
