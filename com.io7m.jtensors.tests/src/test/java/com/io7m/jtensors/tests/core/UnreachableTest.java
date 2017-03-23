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

package com.io7m.jtensors.tests.core;

import com.io7m.jtensors.core.parameterized.vectors.PVectors2D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors2F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4F;
import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class UnreachableTest
{
  @Rule public final ExpectedException expected = ExpectedException.none();

  private static void callPrivate(
    final Class<?> c)
    throws Throwable
  {
    try {
      final Constructor<?> constructor = c.getDeclaredConstructors()[0];
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (final InvocationTargetException e) {
      throw e.getCause();
    }
  }

  @Test
  public void testPVectorDOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.vectors.PVectorDOps");
    Assert.fail();
  }

  @Test
  public void testPVectorFOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.vectors.PVectorFOps");
    Assert.fail();
  }

  @Test
  public void testPVectors4D()
    throws Throwable
  {
    this.checkUnreachable(PVectors4D.class);
    Assert.fail();
  }

  @Test
  public void testPVectors3D()
    throws Throwable
  {
    this.checkUnreachable(PVectors3D.class);
    Assert.fail();
  }

  @Test
  public void testPVectors2D()
    throws Throwable
  {
    this.checkUnreachable(PVectors2D.class);
    Assert.fail();
  }

  @Test
  public void testPVectors4F()
    throws Throwable
  {
    this.checkUnreachable(PVectors4F.class);
    Assert.fail();
  }

  @Test
  public void testPVectors3F()
    throws Throwable
  {
    this.checkUnreachable(PVectors3F.class);
    Assert.fail();
  }

  @Test
  public void testPVectors2F()
    throws Throwable
  {
    this.checkUnreachable(PVectors2F.class);
    Assert.fail();
  }

  private void checkUnreachableByName(
    final String c)
    throws Throwable
  {
    final Class<?> cc = Class.forName(c);
    this.checkUnreachable(cc);
  }

  private void checkUnreachable(
    final Class<?> c)
    throws Throwable
  {
    this.expected.expect(UnreachableCodeException.class);
    callPrivate(c);
  }
}
