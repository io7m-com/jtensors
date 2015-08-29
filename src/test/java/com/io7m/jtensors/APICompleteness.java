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
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

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

  private static void checkMatrix2()
  {
    final Class<?> classes[] = { MatrixM2x2F.class };
    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(MatrixM2x2D.class, c);
    }
  }

  private static void checkMatrix3()
  {
    final Class<?> classes[] = { MatrixM3x3F.class };
    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(MatrixM3x3D.class, c);
    }
  }

  private static void checkMatrix4()
  {
    final Class<?> classes[] = { MatrixM4x4F.class };
    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(MatrixM4x4D.class, c);
    }
  }

  private static void checkQuaternionM4()
  {
    final Class<?> classes[] =
      { QuaternionM4F.class, QuaternionI4D.class, QuaternionI4F.class };
    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(QuaternionM4D.class, c);
    }
  }

  private static void checkVector2()
  {
    final Class<?> classes[] =
      {
        VectorM2F.class,
        VectorM2I.class,
        VectorI2D.class,
        VectorI2F.class,
        VectorI2I.class };
    for (final Class<?> c : classes) {
      APICompleteness.checkAgainst(VectorM2D.class, c);
    }
  }

  private static void checkVector3()
  {
    {
      final Class<?> classes[] =
        {
          VectorM3F.class,
          VectorM3I.class,
          VectorI3D.class,
          VectorI3F.class,
          VectorI3I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(VectorM3D.class, c);
      }
    }

    {
      final Class<?> classes[] = { VectorI3F.class, VectorI3I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(VectorI3D.class, c);
      }
    }
  }

  private static void checkVector4()
  {
    {
      final Class<?> classes[] =
        {
          VectorM4F.class,
          VectorM4I.class,
          VectorI4D.class,
          VectorI4F.class,
          VectorI4I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(VectorM4D.class, c);
      }
    }

    {
      final Class<?> classes[] = { VectorI4F.class, VectorI4I.class };
      for (final Class<?> c : classes) {
        APICompleteness.checkAgainst(VectorI4D.class, c);
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
    APICompleteness.checkQuaternionM4();
    APICompleteness.checkMatrix4();
    APICompleteness.checkMatrix3();
    APICompleteness.checkMatrix2();
    APICompleteness.checkVector4();
    APICompleteness.checkVector3();
    APICompleteness.checkVector2();
  }
}
