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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

import com.io7m.jtensors.VectorI2D;
import com.io7m.jtensors.VectorI2F;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3D;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorI3I;
import com.io7m.jtensors.VectorI4D;
import com.io7m.jtensors.VectorI4F;
import com.io7m.jtensors.VectorI4I;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorM3I;
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

public final class APICompleteness
{
  private static void checkAgainst(
    final Class<?> c_base,
    final Class<?> c)
  {
    System.out.println("Checking " + c + " against " + c_base);
    System.out.println();

    final HashMap<String, Method> methods = APICompleteness.getMethods(c);
    final HashMap<String, Method> baseline_methods =
      APICompleteness.getMethods(c_base);

    for (final Entry<String, Method> e : baseline_methods.entrySet()) {
      final String m_name = e.getKey();
      final String c_name = c.getName();

      if (methods.containsKey(APICompleteness.floatVersion(m_name))) {
        continue;
      }

      if (methods.containsKey(m_name) == false) {
        System.out.println(c_name
          + ": "
          + m_name
          + " not present from "
          + c_base.getName());
      }
    }

    for (final Entry<String, Method> e : methods.entrySet()) {
      final String m_name = e.getKey();
      final String c_name = c.getName();

      if (baseline_methods.containsKey(APICompleteness.doubleVersion(m_name))) {
        continue;
      }
      if (baseline_methods.containsKey(m_name)) {
        continue;
      }

      System.out.println(c_name
        + ": extra method "
        + m_name
        + " not in base "
        + c_base.getName());
    }

    System.out.println("--");
  }

  private static void checkPVector2()
  {
    final Class<?> classes[] =
      {
        PVectorM2F.class,
        PVectorM2I.class,
        PVectorI2D.class,
        PVectorI2F.class,
        PVectorI2I.class,

        VectorM2F.class,
        VectorM2I.class,
        VectorI2D.class,
        VectorI2F.class,
        VectorI2I.class };

    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(PVectorM2D.class, c);
    }
  }

  private static void checkPVector3()
  {
    {
      final Class<?> classes[] =
        {
          PVectorM3F.class,
          PVectorM3I.class,
          PVectorI3D.class,
          PVectorI3F.class,
          PVectorI3I.class,

          VectorM3F.class,
          VectorM3I.class,
          VectorI3D.class,
          VectorI3F.class,
          VectorI3I.class };

      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(PVectorM3D.class, c);
      }
    }

    {
      final Class<?> classes[] = { PVectorI3F.class, PVectorI3I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(PVectorI3D.class, c);
      }
    }
  }

  private static void checkPVector4()
  {
    {
      final Class<?> classes[] =
        {
          PVectorM4F.class,
          PVectorM4I.class,
          PVectorI4D.class,
          PVectorI4F.class,
          PVectorI4I.class,

          VectorM4F.class,
          VectorM4I.class,
          VectorI4D.class,
          VectorI4F.class,
          VectorI4I.class };

      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(PVectorM4D.class, c);
      }
    }

    {
      final Class<?> classes[] = { PVectorI4F.class, PVectorI4I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(PVectorI4D.class, c);
      }
    }
  }

  private static String doubleVersion(
    final String name)
  {
    return name.replace('F', 'D').replaceAll("float", "double");
  }

  private static String floatVersion(
    final String name)
  {
    return name.replace('D', 'F').replaceAll("double", "float");
  }

  private static HashMap<String, Method> getMethods(
    final Class<?> c)
  {
    final HashMap<String, Method> methods = new HashMap<String, Method>();

    for (final Method method : c.getDeclaredMethods()) {
      if ((method.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
        continue;
      }
      if ((method.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
        continue;
      }

      methods.put(method.getName(), method);
    }

    return methods;
  }

  @SuppressWarnings("static-method") @Test public
    void
    testQuaternionCompleteness()
  {
    APICompleteness.checkPVector4();
    APICompleteness.checkPVector3();
    APICompleteness.checkPVector2();
  }
}
