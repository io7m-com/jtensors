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
