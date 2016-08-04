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

import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualFloat;

import java.util.SortedMap;
import java.util.TreeMap;

public final class TestUtilities
{
  public static final int TEST_RANDOM_ITERATIONS = 1000;

  public static AlmostEqualDouble.ContextRelative getDoubleEqualityContext()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.000000001);
    context.setMaxRelativeDifference(0.000000001);
    return context;
  }

  public static AlmostEqualDouble.ContextRelative getDoubleEqualityContext3dp()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.001);
    context.setMaxRelativeDifference(0.001);
    return context;
  }

  public static AlmostEqualDouble.ContextRelative getDoubleEqualityContext6dp()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.000001);
    context.setMaxRelativeDifference(0.000001);
    return context;
  }

  public static AlmostEqualFloat.ContextRelative getSingleEqualityContext()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.000001f);
    context.setMaxRelativeDifference(0.000001f);
    return context;
  }

  public static AlmostEqualFloat.ContextRelative getSingleEqualityContext2dp()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.01f);
    context.setMaxRelativeDifference(0.01f);
    return context;
  }

  public static AlmostEqualFloat.ContextRelative getSingleEqualityContext3dp()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.001f);
    context.setMaxRelativeDifference(0.001f);
    return context;
  }

  public static SortedMap<String, Class<?>> getInterfaces(
    final Class<?> c)
  {
    final SortedMap<String, Class<?>> rs = new TreeMap<String, Class<?>>();
    TestUtilities.getInterfacesActual(c, rs);
    return rs;
  }

  private static void getInterfacesActual(
    final Class<?> c,
    final SortedMap<String, Class<?>> rs)
  {
    for (final Class<?> i : c.getInterfaces()) {
      rs.put(i.getCanonicalName(), i);
      TestUtilities.getInterfacesActual(i, rs);
    }
  }
}
