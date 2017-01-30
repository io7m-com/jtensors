/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jtensors.tests;

import com.io7m.junreachable.UnreachableCodeException;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class UnreachableTest
{
  private static void execNoArgPrivateConstructor(final String name)
    throws
    ClassNotFoundException,
    InstantiationException,
    IllegalAccessException
  {
    try {
      final Class<?> c = Class.forName(name);
      final Constructor<?>[] cons = c.getDeclaredConstructors();
      cons[0].setAccessible(true);
      cons[0].newInstance();
    } catch (final InvocationTargetException e) {
      throw (UnreachableCodeException) e.getCause();
    }
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableCast()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.Cast");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableParameterizedCast()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.parameterized.Cast");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableHashUtility()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.HashUtility");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM2x2D()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM2x2D");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM2x2F()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM2x2F");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM3x3D()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM3x3D");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM3x3F()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM3x3F");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM4x4D()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM4x4D");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachableMatrixM4x4F()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.MatrixM4x4F");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachablePMatrixM3x3D()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.parameterized.PMatrixM3x3D");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachablePMatrixM3x3F()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.parameterized.PMatrixM3x3F");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachablePMatrixM4x4D()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.parameterized.PMatrixM4x4D");
  }

  @Test(expected = UnreachableCodeException.class)
  public void testUnreachablePMatrixM4x4F()
    throws Exception
  {
    execNoArgPrivateConstructor(
      "com.io7m.jtensors.parameterized.PMatrixM4x4F");
  }
}
