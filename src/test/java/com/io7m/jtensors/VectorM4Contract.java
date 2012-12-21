package com.io7m.jtensors;

public abstract class VectorM4Contract extends VectorI4Contract
{
  public abstract void testAbsoluteMutation();

  /**
   * Vector addition via mutation is correct.
   */

  public abstract void testAddMutation();

  /**
   * Vector scaling via mutation is correct.
   */

  public abstract void testScaleMutation();

  /**
   * Vector subtraction via mutation is correct.
   */

  public abstract void testSubtractMutation();
}
