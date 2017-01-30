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
import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI2L;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI3L;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorI4I;
import com.io7m.jtensors.VectorI4L;
import com.io7m.jtensors.VectorM2D;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM2L;
import com.io7m.jtensors.VectorM3D;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM3I;
import com.io7m.jtensors.VectorM3L;
import com.io7m.jtensors.VectorM4D;
import com.io7m.jtensors.VectorM4F;
import com.io7m.jtensors.VectorM4I;
import com.io7m.jtensors.VectorM4L;
import com.io7m.jtensors.parameterized.PMatrixM3x3D;
import com.io7m.jtensors.parameterized.PMatrixM3x3F;
import com.io7m.jtensors.parameterized.PMatrixM4x4D;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PVectorI2D;
import com.io7m.jtensors.parameterized.PVectorI2F;
import com.io7m.jtensors.parameterized.PVectorI2I;
import com.io7m.jtensors.parameterized.PVectorI2L;
import com.io7m.jtensors.parameterized.PVectorI3D;
import com.io7m.jtensors.parameterized.PVectorI3F;
import com.io7m.jtensors.parameterized.PVectorI3I;
import com.io7m.jtensors.parameterized.PVectorI3L;
import com.io7m.jtensors.parameterized.PVectorI4D;
import com.io7m.jtensors.parameterized.PVectorI4F;
import com.io7m.jtensors.parameterized.PVectorI4I;
import com.io7m.jtensors.parameterized.PVectorI4L;
import com.io7m.jtensors.parameterized.PVectorM2D;
import com.io7m.jtensors.parameterized.PVectorM2F;
import com.io7m.jtensors.parameterized.PVectorM2I;
import com.io7m.jtensors.parameterized.PVectorM2L;
import com.io7m.jtensors.parameterized.PVectorM3D;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.jtensors.parameterized.PVectorM3I;
import com.io7m.jtensors.parameterized.PVectorM3L;
import com.io7m.jtensors.parameterized.PVectorM4D;
import com.io7m.jtensors.parameterized.PVectorM4F;
import com.io7m.jtensors.parameterized.PVectorM4I;
import com.io7m.jtensors.parameterized.PVectorM4L;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("static-method")
public final class OverloadChecker
{
  public static void checkOverloading(
    final Class<?> c)
    throws Exception
  {
    final Collection<String> names = new HashSet<String>();
    final HashSet<String> collisions = new HashSet<String>();

    for (final Field n : c.getDeclaredFields()) {
      assert n != null;
      final String name = n.getName();
      assert name != null;
      if (names.contains(name)) {
        collisions.add(name);
      }
      names.add(name);
    }

    if (collisions.size() > 0) {
      throw new Exception(
        String.format(
          "Overloaded fields for class %s: %s", c, collisions));
    }
  }

  @Test
  public void testMatrixM2x2D()
    throws Exception
  {
    checkOverloading(MatrixM2x2D.class);
  }

  @Test
  public void testMatrixM2x2F()
    throws Exception
  {
    checkOverloading(MatrixM2x2F.class);
  }

  @Test
  public void testMatrixM3x3D()
    throws Exception
  {
    checkOverloading(MatrixM3x3D.class);
  }

  @Test
  public void testMatrixM3x3F()
    throws Exception
  {
    checkOverloading(MatrixM3x3F.class);
  }

  @Test
  public void testMatrixM4x4D()
    throws Exception
  {
    checkOverloading(MatrixM4x4D.class);
  }

  @Test
  public void testMatrixM4x4F()
    throws Exception
  {
    checkOverloading(MatrixM4x4F.class);
  }

  @Test
  public void testPMatrixM3x3D()
    throws Exception
  {
    checkOverloading(PMatrixM3x3D.class);
  }

  @Test
  public void testPMatrixM3x3F()
    throws Exception
  {
    checkOverloading(PMatrixM3x3F.class);
  }

  @Test
  public void testPMatrixM4x4D()
    throws Exception
  {
    checkOverloading(PMatrixM4x4D.class);
  }

  @Test
  public void testPMatrixM4x4F()
    throws Exception
  {
    checkOverloading(PMatrixM4x4F.class);
  }

  @Test
  public void testPVectorI2D()
    throws Exception
  {
    checkOverloading(PVectorI2D.class);
  }

  @Test
  public void testPVectorI2F()
    throws Exception
  {
    checkOverloading(PVectorI2F.class);
  }

  @Test
  public void testPVectorI2I()
    throws Exception
  {
    checkOverloading(PVectorI2I.class);
  }

  @Test
  public void testPVectorI2L()
    throws Exception
  {
    checkOverloading(PVectorI2L.class);
  }

  @Test
  public void testPVectorI3D()
    throws Exception
  {
    checkOverloading(PVectorI3D.class);
  }

  @Test
  public void testPVectorI3F()
    throws Exception
  {
    checkOverloading(PVectorI3F.class);
  }

  @Test
  public void testPVectorI3I()
    throws Exception
  {
    checkOverloading(PVectorI3I.class);
  }

  @Test
  public void testPVectorI3L()
    throws Exception
  {
    checkOverloading(PVectorI3L.class);
  }

  @Test
  public void testPVectorI4D()
    throws Exception
  {
    checkOverloading(PVectorI4D.class);
  }

  @Test
  public void testPVectorI4F()
    throws Exception
  {
    checkOverloading(PVectorI4F.class);
  }

  @Test
  public void testPVectorI4I()
    throws Exception
  {
    checkOverloading(PVectorI4I.class);
  }

  @Test
  public void testPVectorI4L()
    throws Exception
  {
    checkOverloading(PVectorI4L.class);
  }

  @Test
  public void testPVectorM2D()
    throws Exception
  {
    checkOverloading(PVectorM2D.class);
  }

  @Test
  public void testPVectorM2F()
    throws Exception
  {
    checkOverloading(PVectorM2F.class);
  }

  @Test
  public void testPVectorM2I()
    throws Exception
  {
    checkOverloading(PVectorM2I.class);
  }

  @Test
  public void testPVectorM2L()
    throws Exception
  {
    checkOverloading(PVectorM2L.class);
  }

  @Test
  public void testPVectorM3D()
    throws Exception
  {
    checkOverloading(PVectorM3D.class);
  }

  @Test
  public void testPVectorM3F()
    throws Exception
  {
    checkOverloading(PVectorM3F.class);
  }

  @Test
  public void testPVectorM3I()
    throws Exception
  {
    checkOverloading(PVectorM3I.class);
  }

  @Test
  public void testPVectorM3L()
    throws Exception
  {
    checkOverloading(PVectorM3L.class);
  }

  @Test
  public void testPVectorM4D()
    throws Exception
  {
    checkOverloading(PVectorM4D.class);
  }

  @Test
  public void testPVectorM4F()
    throws Exception
  {
    checkOverloading(PVectorM4F.class);
  }

  @Test
  public void testPVectorM4I()
    throws Exception
  {
    checkOverloading(PVectorM4I.class);
  }

  @Test
  public void testPVectorM4L()
    throws Exception
  {
    checkOverloading(PVectorM4L.class);
  }

  @Test
  public void testVectorI2D()
    throws Exception
  {
    checkOverloading(VectorI2D.class);
  }

  @Test
  public void testVectorI2F()
    throws Exception
  {
    checkOverloading(VectorI2F.class);
  }

  @Test
  public void testVectorI2I()
    throws Exception
  {
    checkOverloading(VectorI2I.class);
  }

  @Test
  public void testVectorI2L()
    throws Exception
  {
    checkOverloading(VectorI2L.class);
  }

  @Test
  public void testVectorI3D()
    throws Exception
  {
    checkOverloading(VectorI3D.class);
  }

  @Test
  public void testVectorI3F()
    throws Exception
  {
    checkOverloading(VectorI3F.class);
  }

  @Test
  public void testVectorI3I()
    throws Exception
  {
    checkOverloading(VectorI3I.class);
  }

  @Test
  public void testVectorI3L()
    throws Exception
  {
    checkOverloading(VectorI3L.class);
  }

  @Test
  public void testVectorI4D()
    throws Exception
  {
    checkOverloading(VectorI4D.class);
  }

  @Test
  public void testVectorI4F()
    throws Exception
  {
    checkOverloading(VectorI4F.class);
  }

  @Test
  public void testVectorI4I()
    throws Exception
  {
    checkOverloading(VectorI4I.class);
  }

  @Test
  public void testVectorI4L()
    throws Exception
  {
    checkOverloading(VectorI4L.class);
  }

  @Test
  public void testVectorM2D()
    throws Exception
  {
    checkOverloading(VectorM2D.class);
  }

  @Test
  public void testVectorM2F()
    throws Exception
  {
    checkOverloading(VectorM2F.class);
  }

  @Test
  public void testVectorM2I()
    throws Exception
  {
    checkOverloading(VectorM2I.class);
  }

  @Test
  public void testVectorM2L()
    throws Exception
  {
    checkOverloading(VectorM2L.class);
  }

  @Test
  public void testVectorM3D()
    throws Exception
  {
    checkOverloading(VectorM3D.class);
  }

  @Test
  public void testVectorM3F()
    throws Exception
  {
    checkOverloading(VectorM3F.class);
  }

  @Test
  public void testVectorM3I()
    throws Exception
  {
    checkOverloading(VectorM3I.class);
  }

  @Test
  public void testVectorM3L()
    throws Exception
  {
    checkOverloading(VectorM3L.class);
  }

  @Test
  public void testVectorM4D()
    throws Exception
  {
    checkOverloading(VectorM4D.class);
  }

  @Test
  public void testVectorM4F()
    throws Exception
  {
    checkOverloading(VectorM4F.class);
  }

  @Test
  public void testVectorM4I()
    throws Exception
  {
    checkOverloading(VectorM4I.class);
  }

  @Test
  public void testVectorM4L()
    throws Exception
  {
    checkOverloading(VectorM4L.class);
  }
}
