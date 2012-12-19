package com.io7m.jtensors;

import com.io7m.jaux.AlmostEqualDouble;
import com.io7m.jaux.AlmostEqualFloat;

final class TestUtilities
{
  public static final int TEST_RANDOM_ITERATIONS = 5000;

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

  static AlmostEqualFloat.ContextRelative getSingleEqualityContext3dp()
  {
    final AlmostEqualFloat.ContextRelative context =
      new AlmostEqualFloat.ContextRelative();
    context.setMaxAbsoluteDifference(0.001f);
    context.setMaxRelativeDifference(0.001f);
    return context;
  }
}
