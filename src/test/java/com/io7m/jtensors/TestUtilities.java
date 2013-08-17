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

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;

final class TestUtilities
{
  public static final int TEST_RANDOM_ITERATIONS = 1000;

  static AlmostEqualDouble.ContextRelative getDoubleEqualityContext()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.000000001);
    context.setMaxRelativeDifference(0.000000001);
    return context;
  }

  static AlmostEqualDouble.ContextRelative getDoubleEqualityContext3dp()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.001);
    context.setMaxRelativeDifference(0.001);
    return context;
  }

  static AlmostEqualDouble.ContextRelative getDoubleEqualityContext6dp()
  {
    final AlmostEqualDouble.ContextRelative context =
      new AlmostEqualDouble.ContextRelative();
    context.setMaxAbsoluteDifference(0.000001);
    context.setMaxRelativeDifference(0.000001);
    return context;
  }

  static AlmostEqualFloat.ContextRelative getSingleEqualityContext()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.000001f);
    context.setMaxRelativeDifference(0.000001f);
    return context;
  }

  static AlmostEqualFloat.ContextRelative getSingleEqualityContext5dp()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.00001f);
    context.setMaxRelativeDifference(0.00001f);
    return context;
  }

  static AlmostEqualFloat.ContextRelative getSingleEqualityContext3dp()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.001f);
    context.setMaxRelativeDifference(0.001f);
    return context;
  }
}
