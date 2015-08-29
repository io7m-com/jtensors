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

package com.io7m.jtensors;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.annotation.Nonnull;

import org.junit.Test;

/**
 * <p>
 * Classes in the package are non-final in order to allow user code to add
 * phantom type parameters by subclassing. However, subclassing for the
 * purposes of overriding anything is strictly prohibited!
 * </p>
 * <p>
 * This test uses reflection to catch any accidentally non-final methods.
 * </p>
 */

public final class FinalityChecker
{
  private static class NotFinal extends Exception
  {
    private static final long serialVersionUID = -3278041028041610303L;

    NotFinal(
      final @Nonnull Method m)
    {
      super(m + " not final");
    }
  }

  private static class UnexpectedlyFinal extends Exception
  {
    private static final long serialVersionUID = -3278041028041610303L;

    UnexpectedlyFinal(
      final @Nonnull Class<?> c)
    {
      super(c + " is final");
    }
  }

  static void checkAllMethodsFinal(
    final @Nonnull Class<?> c)
    throws NotFinal,
      UnexpectedlyFinal,
      UnexpectedlyFinal
  {
    if ((c.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
      System.out.println("Class "
        + c.getCanonicalName()
        + " is final, but should not be!");
      throw new UnexpectedlyFinal(c);
    }

    final Method[] methods = c.getDeclaredMethods();
    for (final Method m : methods) {
      final int mods = m.getModifiers();
      if ((mods & Modifier.FINAL) == Modifier.FINAL) {
        System.out.println(c.getCanonicalName()
          + "."
          + m.getName()
          + " is final");
      } else {
        System.out.println(c.getCanonicalName()
          + "."
          + m.getName()
          + " is NOT final");
        throw new NotFinal(m);
      }
    }
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM2x2D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM2x2D.class);
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM2x2F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM2x2F.class);
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM3x3D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM3x3D.class);
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM3x3F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM3x3F.class);
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM4x4D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM4x4D.class);
  }

  @SuppressWarnings("static-method") @Test public void testMatrixM4x4F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(MatrixM4x4F.class);
  }

  @SuppressWarnings("static-method") @Test public void testQuaternionM4D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(QuaternionM4D.class);
  }

  @SuppressWarnings("static-method") @Test public void testQuaternionM4F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(QuaternionM4F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI2D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI2D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI2F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI2F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI2I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI2I.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI3D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI3D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI3F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI3F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI3I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI3I.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI4D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI4D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI4F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI4F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorI4I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorI4I.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM2D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM2D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM2F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM2F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM2I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM2I.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM3D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM3D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM3F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM3F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM3I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM3I.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM4D()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM4D.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM4F()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM4F.class);
  }

  @SuppressWarnings("static-method") @Test public void testVectorM4I()
    throws NotFinal,
      UnexpectedlyFinal
  {
    FinalityChecker.checkAllMethodsFinal(VectorM4I.class);
  }
}
