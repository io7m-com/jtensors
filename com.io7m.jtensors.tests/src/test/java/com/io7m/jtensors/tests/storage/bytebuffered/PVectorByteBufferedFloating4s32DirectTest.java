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

package com.io7m.jtensors.tests.storage.bytebuffered;

import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating2Type;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating3Type;
import com.io7m.jtensors.storage.api.parameterized.vectors.PVectorStorageFloating4Type;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating2Type;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating3Type;
import com.io7m.jtensors.storage.api.unparameterized.vectors.VectorStorageFloating4Type;
import com.io7m.jtensors.storage.bytebuffered.PVectorByteBufferedFloating4s32;
import com.io7m.jtensors.tests.core.TestFOps;
import com.io7m.jtensors.tests.storage.api.PVectorStorage4Contract;

public final class PVectorByteBufferedFloating4s32DirectTest extends
  PVectorStorage4Contract
{
  @Override
  protected void checkAlmostEqual(
    final double a,
    final double b)
  {
    TestFOps.checkAlmostEquals(a, b);
  }

  @Override
  protected VectorStorageFloating4Type createWith4(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }

  @Override
  protected PVectorStorageFloating4Type<Object> createWithP4(
    final double x,
    final double y,
    final double z,
    final double w)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }

  @Override
  protected PVectorStorageFloating3Type<Object> createWithP3(
    final double x,
    final double y,
    final double z)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }

  @Override
  protected PVectorStorageFloating2Type<Object> createWithP2(
    final double x,
    final double y)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }

  @Override
  protected VectorStorageFloating2Type createWith2(
    final double x,
    final double y)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }

  @Override
  protected VectorStorageFloating3Type createWith3(
    final double x,
    final double y,
    final double z)
  {
    return PVectorByteBufferedFloating4s32.createDirect();
  }
}
