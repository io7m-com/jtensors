/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
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

import com.io7m.jtensors.MatrixM2x2D;
import com.io7m.jtensors.MatrixM2x2F;
import com.io7m.jtensors.MatrixM3x3D;
import com.io7m.jtensors.MatrixM3x3F;
import com.io7m.jtensors.MatrixM4x4D;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.QuaternionI4D;
import com.io7m.jtensors.QuaternionI4F;
import com.io7m.jtensors.QuaternionM4D;
import com.io7m.jtensors.QuaternionM4F;
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorI4I;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorM4I;
import com.io7m.jtensors.parameterized.PVectorI2D;
import com.io7m.jtensors.parameterized.PVectorI2F;
import com.io7m.jtensors.parameterized.PVectorI2I;
import com.io7m.jtensors.parameterized.PVectorI3D;
import com.io7m.jtensors.parameterized.PVectorI3F;
import com.io7m.jtensors.parameterized.PVectorI3I;
import com.io7m.jtensors.parameterized.PVectorI4D;
import com.io7m.jtensors.parameterized.PVectorI4F;
import com.io7m.jtensors.parameterized.PVectorI4I;
import com.io7m.jtensors.parameterized.PVectorM2D;
import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.parameterized.PVectorM2I;
import com.io7m.jtensors.parameterized.PVectorM3D;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.parameterized.PVectorM4I;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.lang.reflect.Modifier;

/**
 * <p> All classes must be final. </p>
 */

@SuppressWarnings("static-method") public final class FinalityChecker
{
  static void checkClassFinal(
    final @Nonnull Class<?> c)
    throws NotFinal
  {
    if ((c.getModifiers() & Modifier.FINAL) != Modifier.FINAL) {
      System.out.println("Class " + c.getCanonicalName() + " is not final!");
      throw new NotFinal(c);
    }
  }

  @Test public void testMatrixM2x2D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM2x2D.class);
  }

  @Test public void testMatrixM2x2F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM2x2F.class);
  }

  @Test public void testMatrixM3x3D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM3x3D.class);
  }

  @Test public void testMatrixM3x3F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM3x3F.class);
  }

  @Test public void testMatrixM4x4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM4x4D.class);
  }

  @Test public void testMatrixM4x4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(MatrixM4x4F.class);
  }

  @Test public void testPVectorI2D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI2D.class);
  }

  @Test public void testPVectorI2F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI2F.class);
  }

  @Test public void testPVectorI2I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI2I.class);
  }

  @Test public void testPVectorI3D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI3D.class);
  }

  @Test public void testPVectorI3F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI3F.class);
  }

  @Test public void testPVectorI3I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI3I.class);
  }

  @Test public void testPVectorI4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI4D.class);
  }

  @Test public void testPVectorI4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI4F.class);
  }

  @Test public void testPVectorI4I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorI4I.class);
  }

  @Test public void testPVectorM2D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM2D.class);
  }

  @Test public void testPVectorM2F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM2F.class);
  }

  @Test public void testPVectorM2I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM2I.class);
  }

  @Test public void testPVectorM3D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM3D.class);
  }

  @Test public void testPVectorM3F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM3F.class);
  }

  @Test public void testPVectorM3I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM3I.class);
  }

  @Test public void testPVectorM4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM4D.class);
  }

  @Test public void testPVectorM4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM4F.class);
  }

  @Test public void testPVectorM4I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(PVectorM4I.class);
  }

  @Test public void testQuaternionI4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(QuaternionI4D.class);
  }

  @Test public void testQuaternionI4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(QuaternionI4F.class);
  }

  @Test public void testQuaternionM4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(QuaternionM4D.class);
  }

  @Test public void testQuaternionM4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(QuaternionM4F.class);
  }

  @Test public void testVectorI2D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI2D.class);
  }

  @Test public void testVectorI2F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI2F.class);
  }

  @Test public void testVectorI2I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI2I.class);
  }

  @Test public void testVectorI3D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI3D.class);
  }

  @Test public void testVectorI3F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI3F.class);
  }

  @Test public void testVectorI3I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI3I.class);
  }

  @Test public void testVectorI4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI4D.class);
  }

  @Test public void testVectorI4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI4F.class);
  }

  @Test public void testVectorI4I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorI4I.class);
  }

  @Test public void testVectorM2D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM2D.class);
  }

  @Test public void testVectorM2F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM2F.class);
  }

  @Test public void testVectorM2I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM2I.class);
  }

  @Test public void testVectorM3D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM3D.class);
  }

  @Test public void testVectorM3F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM3F.class);
  }

  @Test public void testVectorM3I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM3I.class);
  }

  @Test public void testVectorM4D()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM4D.class);
  }

  @Test public void testVectorM4F()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM4F.class);
  }

  @Test public void testVectorM4I()
    throws NotFinal
  {
    FinalityChecker.checkClassFinal(VectorM4I.class);
  }

  private static class NotFinal extends Exception
  {
    private static final long serialVersionUID = -3278041028041610303L;

    NotFinal(
      final @Nonnull Class<?> c)
    {
      super(c + " not final");
    }
  }
}
