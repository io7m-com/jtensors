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

import com.io7m.jtensors.core.determinants.Determinants;
import com.io7m.jtensors.core.dotproducts.DotProductsDouble;
import com.io7m.jtensors.core.dotproducts.DotProductsInt;
import com.io7m.jtensors.core.dotproducts.DotProductsLong;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices2x2D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices2x2F;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices3x3D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices3x3F;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices4x4D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrices4x4F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors2D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors2F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors2I;
import com.io7m.jtensors.core.parameterized.vectors.PVectors2L;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3I;
import com.io7m.jtensors.core.parameterized.vectors.PVectors3L;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4D;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4F;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4I;
import com.io7m.jtensors.core.parameterized.vectors.PVectors4L;
import com.io7m.jtensors.core.quaternions.Quaternions4D;
import com.io7m.jtensors.core.quaternions.Quaternions4F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices2x2D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices2x2F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices3x3D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices3x3F;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices4x4F;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2F;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2I;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors2L;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3F;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3I;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3L;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4F;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4I;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4L;
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
  public void testPVectorIOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.vectors.PVectorIOps");
    Assert.fail();
  }

  @Test
  public void testPVectorLOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.vectors.PVectorLOps");
    Assert.fail();
  }

  @Test
  public void testPMatrixDOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.matrices.PMatrixDOps");
    Assert.fail();
  }

  @Test
  public void testPMatrixFOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.parameterized.matrices.PMatrixFOps");
    Assert.fail();
  }

  @Test
  public void testMatrixDOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.matrices.MatrixDOps");
    Assert.fail();
  }

  @Test
  public void testMatrixFOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.matrices.MatrixFOps");
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

  @Test
  public void testPVectors4L()
    throws Throwable
  {
    this.checkUnreachable(PVectors4L.class);
    Assert.fail();
  }

  @Test
  public void testPVectors3L()
    throws Throwable
  {
    this.checkUnreachable(PVectors3L.class);
    Assert.fail();
  }

  @Test
  public void testPVectors2L()
    throws Throwable
  {
    this.checkUnreachable(PVectors2L.class);
    Assert.fail();
  }

  @Test
  public void testPVectors4I()
    throws Throwable
  {
    this.checkUnreachable(PVectors4I.class);
    Assert.fail();
  }

  @Test
  public void testPVectors3I()
    throws Throwable
  {
    this.checkUnreachable(PVectors3I.class);
    Assert.fail();
  }

  @Test
  public void testPVectors2I()
    throws Throwable
  {
    this.checkUnreachable(PVectors2I.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices4x4D()
    throws Throwable
  {
    this.checkUnreachable(PMatrices4x4D.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices3x3D()
    throws Throwable
  {
    this.checkUnreachable(PMatrices3x3D.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices2x2D()
    throws Throwable
  {
    this.checkUnreachable(PMatrices2x2D.class);
    Assert.fail();
  }

  @Test
  public void testMatrices4x4D()
    throws Throwable
  {
    this.checkUnreachable(Matrices4x4D.class);
    Assert.fail();
  }

  @Test
  public void testMatrices3x3D()
    throws Throwable
  {
    this.checkUnreachable(Matrices3x3D.class);
    Assert.fail();
  }

  @Test
  public void testMatrices2x2D()
    throws Throwable
  {
    this.checkUnreachable(Matrices2x2D.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices4x4F()
    throws Throwable
  {
    this.checkUnreachable(PMatrices4x4F.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices3x3F()
    throws Throwable
  {
    this.checkUnreachable(PMatrices3x3F.class);
    Assert.fail();
  }

  @Test
  public void testPMatrices2x2F()
    throws Throwable
  {
    this.checkUnreachable(PMatrices2x2F.class);
    Assert.fail();
  }

  @Test
  public void testMatrices4x4F()
    throws Throwable
  {
    this.checkUnreachable(Matrices4x4F.class);
    Assert.fail();
  }

  @Test
  public void testMatrices3x3F()
    throws Throwable
  {
    this.checkUnreachable(Matrices3x3F.class);
    Assert.fail();
  }

  @Test
  public void testMatrices2x2F()
    throws Throwable
  {
    this.checkUnreachable(Matrices2x2F.class);
    Assert.fail();
  }

  @Test
  public void testDeterminants()
    throws Throwable
  {
    this.checkUnreachable(Determinants.class);
    Assert.fail();
  }

  @Test
  public void testDotProductsDouble()
    throws Throwable
  {
    this.checkUnreachable(DotProductsDouble.class);
    Assert.fail();
  }

  @Test
  public void testDotProductsInt()
    throws Throwable
  {
    this.checkUnreachable(DotProductsInt.class);
    Assert.fail();
  }

  @Test
  public void testDotProductsLong()
    throws Throwable
  {
    this.checkUnreachable(DotProductsLong.class);
    Assert.fail();
  }

  @Test
  public void testVectors4D()
    throws Throwable
  {
    this.checkUnreachable(Vectors4D.class);
    Assert.fail();
  }

  @Test
  public void testVectors3D()
    throws Throwable
  {
    this.checkUnreachable(Vectors3D.class);
    Assert.fail();
  }

  @Test
  public void testVectors2D()
    throws Throwable
  {
    this.checkUnreachable(Vectors2D.class);
    Assert.fail();
  }

  @Test
  public void testVectors4F()
    throws Throwable
  {
    this.checkUnreachable(Vectors4F.class);
    Assert.fail();
  }

  @Test
  public void testVectors3F()
    throws Throwable
  {
    this.checkUnreachable(Vectors3F.class);
    Assert.fail();
  }

  @Test
  public void testVectors2F()
    throws Throwable
  {
    this.checkUnreachable(Vectors2F.class);
    Assert.fail();
  }

  @Test
  public void testVectors4L()
    throws Throwable
  {
    this.checkUnreachable(Vectors4L.class);
    Assert.fail();
  }

  @Test
  public void testVectors3L()
    throws Throwable
  {
    this.checkUnreachable(Vectors3L.class);
    Assert.fail();
  }

  @Test
  public void testVectors2L()
    throws Throwable
  {
    this.checkUnreachable(Vectors2L.class);
    Assert.fail();
  }

  @Test
  public void testVectors4I()
    throws Throwable
  {
    this.checkUnreachable(Vectors4I.class);
    Assert.fail();
  }

  @Test
  public void testVectors3I()
    throws Throwable
  {
    this.checkUnreachable(Vectors3I.class);
    Assert.fail();
  }

  @Test
  public void testVectors2I()
    throws Throwable
  {
    this.checkUnreachable(Vectors2I.class);
    Assert.fail();
  }

  @Test
  public void testVectorDOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.vectors.VectorDOps");
    Assert.fail();
  }

  @Test
  public void testVectorFOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.vectors.VectorFOps");
    Assert.fail();
  }

  @Test
  public void testVectorIOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.vectors.VectorIOps");
    Assert.fail();
  }

  @Test
  public void testVectorLOps()
    throws Throwable
  {
    this.checkUnreachableByName(
      "com.io7m.jtensors.core.unparameterized.vectors.VectorLOps");
    Assert.fail();
  }

  @Test
  public void testQuaternions4D()
    throws Throwable
  {
    this.checkUnreachable(Quaternions4D.class);
    Assert.fail();
  }

  @Test
  public void testQuaternions4F()
    throws Throwable
  {
    this.checkUnreachable(Quaternions4F.class);
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
