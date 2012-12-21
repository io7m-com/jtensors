package com.io7m.jtensors;

/**
 * Mutable quaternion contracts.
 */

public abstract class QuaternionM4Contract extends QuaternionI4Contract
{
  /**
   * Quaternion addition via mutation is correct.
   */

  public abstract void testAddMutation();

  /**
   * The conjugate of a quaternion <code>q = (v, s)</code> is given by
   * <code>(-v, s)</code>. The version using mutation works.
   */

  public abstract void testConjugateInPlace();

  /**
   * Copying quaternions works correctly.
   */

  public abstract void testCopy();

  /**
   * Quaternion multiplication via mutation is correct.
   */

  public abstract void testMultiplyInPlace();

  /**
   * Quaternion multiplication via mutation is correct.
   */

  public abstract void testMultiplyInPlaceOther();

  /**
   * Quaternion multiplication is correct.
   */

  public abstract void testMultiplyOther();

  /**
   * Quaternion scaling via mutation is correct.
   */

  public abstract void testScaleMutation();

  /**
   * Quaternion subtraction via mutation is correct.
   */

  public abstract void testSubtractMutation();
}
