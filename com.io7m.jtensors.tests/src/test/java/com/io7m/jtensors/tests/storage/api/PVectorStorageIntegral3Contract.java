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

import com.io7m.jtensors.core.parameterized.vectors.PVector3I;
import com.io7m.jtensors.core.parameterized.vectors.PVector3L;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageIntegral3Type;
import com.io7m.jtensors.tests.rules.PercentagePassing;
import net.java.quickcheck.Generator;
import org.junit.Test;

public abstract class PVectorStorageIntegral3Contract
{
  protected abstract PVectorStorageIntegral3Type<Object> create(int offset);

  protected abstract Generator<PVector3L<Object>> createGenerator3L();

  protected abstract Generator<PVector3I<Object>> createGenerator3I();

  protected abstract void checkEquals(
    long x,
    long y);

  @Test
  @PercentagePassing
  public final void testGetSet3()
  {
    final Generator<PVector3L<Object>> gen = this.createGenerator3L();
    final PVector3L<Object> v = gen.next();

    final PVectorStorageIntegral3Type<Object> sv = this.create(0);
    sv.setXYZ(v.x(), v.y(), v.z());

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }

  @Test
  @PercentagePassing
  public final void testGetSet3L()
  {
    final Generator<PVector3L<Object>> gen = this.createGenerator3L();
    final PVector3L<Object> v = gen.next();

    final PVectorStorageIntegral3Type<Object> sv = this.create(0);
    sv.setPVector3L(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }

  @Test
  @PercentagePassing
  public final void testGetSet3I()
  {
    final Generator<PVector3I<Object>> gen = this.createGenerator3I();
    final PVector3I<Object> v = gen.next();

    final PVectorStorageIntegral3Type<Object> sv = this.create(0);
    sv.setPVector3I(v);

    this.checkEquals(v.x(), sv.x());
    this.checkEquals(v.y(), sv.y());
    this.checkEquals(v.z(), sv.z());
  }
}
