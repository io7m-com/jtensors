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

package com.io7m.jtensors.tests.parameterized;

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

      throw new NotFinal(c);
    }
  }

  @Test public void testPVectorI2D()
    throws NotFinal
  {
    checkClassFinal(PVectorI2D.class);
  }

  @Test public void testPVectorI2F()
    throws NotFinal
  {
    checkClassFinal(PVectorI2F.class);
  }

  @Test public void testPVectorI2I()
    throws NotFinal
  {
    checkClassFinal(PVectorI2I.class);
  }

  @Test public void testPVectorI3D()
    throws NotFinal
  {
    checkClassFinal(PVectorI3D.class);
  }

  @Test public void testPVectorI3F()
    throws NotFinal
  {
    checkClassFinal(PVectorI3F.class);
  }

  @Test public void testPVectorI3I()
    throws NotFinal
  {
    checkClassFinal(PVectorI3I.class);
  }

  @Test public void testPVectorI4D()
    throws NotFinal
  {
    checkClassFinal(PVectorI4D.class);
  }

  @Test public void testPVectorI4F()
    throws NotFinal
  {
    checkClassFinal(PVectorI4F.class);
  }

  @Test public void testPVectorI4I()
    throws NotFinal
  {
    checkClassFinal(PVectorI4I.class);
  }

  @Test public void testPVectorM2D()
    throws NotFinal
  {
    checkClassFinal(PVectorM2D.class);
  }

  @Test public void testPVectorM2F()
    throws NotFinal
  {
    checkClassFinal(PVectorM2F.class);
  }

  @Test public void testPVectorM2I()
    throws NotFinal
  {
    checkClassFinal(PVectorM2I.class);
  }

  @Test public void testPVectorM3D()
    throws NotFinal
  {
    checkClassFinal(PVectorM3D.class);
  }

  @Test public void testPVectorM3F()
    throws NotFinal
  {
    checkClassFinal(PVectorM3F.class);
  }

  @Test public void testPVectorM3I()
    throws NotFinal
  {
    checkClassFinal(PVectorM3I.class);
  }

  @Test public void testPVectorM4D()
    throws NotFinal
  {
    checkClassFinal(PVectorM4D.class);
  }

  @Test public void testPVectorM4F()
    throws NotFinal
  {
    checkClassFinal(PVectorM4F.class);
  }

  @Test public void testPVectorM4I()
    throws NotFinal
  {
    checkClassFinal(PVectorM4I.class);
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
