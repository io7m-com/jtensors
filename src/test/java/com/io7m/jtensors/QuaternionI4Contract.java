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

public abstract class QuaternionI4Contract
{
  /**
   * Quaternion addition is correct.
   */

  public abstract void testAdd();

  /**
   * Differing quaternions are not almost equal.
   */

  public abstract void testAlmostEqualNot();

  /**
   * <code>∀q r s. q ≃ r ∧ r ≃ s → q ≃ s</code>
   */

  public abstract void testAlmostEqualTransitive();

  /**
   * Interface functions work as expected.
   */

  public abstract void testCheckInterface();

  /**
   * The conjugate of a quaternion <code>q = (v, s)</code> is given by
   * <code>(-v, s)</code>.
   */

  public abstract void testConjugate();

  /**
   * Conjugation is invertible; <code>∀q. conjugate(conjugate(q)) = q</code>
   */

  public abstract void testConjugateInvertible();

  /**
   * The default value for quaternions is (0,0,0,1)
   */

  public abstract void testDefault0001();

  /**
   * The <code>dotProduct</code> function gives expected results.
   */

  public abstract void testDotProduct();

  /**
   * <code>∀q. dotProduct(q, q) = 1</code>
   */

  public abstract void testDotProductSelf();

  /**
   * <code>∀q. dotProduct(q, q) = magnitude(q)²</code>
   */

  public abstract void testDotProductSelfMagnitudeSquared();

  /**
   * Equality is correct.
   */

  public abstract void testEqualsCorrect();

  /**
   * Inequality is correct.
   */

  public abstract void testEqualsNotEqualCorrect();

  /**
   * Two equal quaternions have the same hashcode.
   */

  public abstract void testHashCodeEqualsCorrect();

  /**
   * All parts of a quaternion affect the hashcode.
   */

  public abstract void testHashCodeNotEqualCorrect();

  public abstract void testInitializeReadable();

  /**
   * <p>
   * <code>∀q r s. interpolateLinear(q, r, 1.0) = r</code>
   * </p>
   * <p>
   * <code>∀q r s. interpolateLinear(q, r, 0.0) = q</code>
   * </p>
   */

  public abstract void testInterpolateLinearLimits();

  /**
   * Looking at (-1,0,0) from the origin gives consistent results with the 4x4
   * matrix implementation.
   */

  public abstract void testLookAtConsistent_Origin_NegativeX();

  /**
   * Looking at (1,0,0) from the origin gives consistent results with the 4x4
   * matrix implementation.
   */

  public abstract void testLookAtConsistent_Origin_PositiveX();

  /**
   * Looking at a random target from a random origin, using the Y axis as the
   * "up" vector gives the same results as the 4x4 matrix equivalent.
   */

  public abstract void testLookAtMatrixEquivalentAxisY();

  /**
   * The magnitude of any large random vector is greater than zero.
   */

  public abstract void testMagnitudeNonzero();

  /**
   * <p>
   * <code>∀q. magnitude(normalize(q)) = 1</code>
   * </p>
   */

  public abstract void testMagnitudeNormal();

  /**
   * <p>
   * <code>magnitude(normalize((0,0,0,0)) = 0</code>
   * </p>
   */

  public abstract void testMagnitudeNormalizeZero();

  /**
   * <p>
   * <code>magnitude((1,0,0,0)) = 1</code>
   * </p>
   */

  public abstract void testMagnitudeOne();

  /**
   * Simple magnitude tests.
   */

  public abstract void testMagnitudeSimple();

  /**
   * <p>
   * <code>magnitude((0,0,0,0)) = 0</code>
   * </p>
   */

  public abstract void testMagnitudeZero();

  /**
   * <p>
   * <code>∀a r. magnitude(makeFromAxisAngle(a, r)) = 1</code>
   * </p>
   */

  public abstract void testMakeAxisAngleNormal();

  public abstract void testMakeAxisAngleX_45();

  public abstract void testMakeAxisAngleX_90();

  public abstract void testMakeAxisAngleY_45();

  public abstract void testMakeAxisAngleY_90();

  public abstract void testMakeAxisAngleZ_45();

  public abstract void testMakeAxisAngleZ_90();

  /**
   * A quaternion <code>q</code> produced from an axis/angle, and a quaternion
   * <code>r</code> produced from a 3x3 matrix produced from an axis/angle
   * implies <code>q = r</code>.
   */

  public abstract void testMakeFromMatrix3x3Exhaustive();

  /**
   * A quaternion <code>q</code> produced from an axis/angle, and a quaternion
   * <code>r</code> produced from a 4x4 matrix produced from an axis/angle
   * implies <code>q = r</code>.
   */

  public abstract void testMakeFromMatrix4x4Exhaustive();

  public abstract void testMakeMatrix3x3_45X();

  public abstract void testMakeMatrix3x3_45Y();

  public abstract void testMakeMatrix3x3_45Z();

  /**
   * The identity quaternion results in the 3x3 identity matrix.
   */

  public abstract void testMakeMatrix3x3_Identity();

  public abstract void testMakeMatrix3x3_Minus45X();

  public abstract void testMakeMatrix3x3_Minus45Y();

  public abstract void testMakeMatrix3x3_Minus45Z();

  public abstract void testMakeMatrix4x4_45X();

  public abstract void testMakeMatrix4x4_45Y();

  public abstract void testMakeMatrix4x4_45Z();

  /**
   * The identity quaternion results in the 4x4 identity matrix.
   */

  public abstract void testMakeMatrix4x4_Identity();

  public abstract void testMakeMatrix4x4_Minus45X();

  public abstract void testMakeMatrix4x4_Minus45Y();

  public abstract void testMakeMatrix4x4_Minus45Z();

  /**
   * Quaternion multiplication is correct.
   */

  public abstract void testMultiply();

  /**
   * <p>
   * <code>∀q. isNegationOf(q, negate(q))</code>
   * </p>
   */

  public abstract void testNegation();

  public abstract void testNegationCases();

  public abstract void testNormalizeSimple();

  /**
   * Normalizing a zero quaternion results in a zero quaternion.
   */

  public abstract void testNormalizeZero();

  /**
   * <p>
   * <code>∀q. scale(q, 1) = q</code>
   * </p>
   */

  public abstract void testScaleOne();

  /**
   * <p>
   * <code>∀q. scale(q, 0) = (0,0,0,0)</code>
   * </p>
   */

  public abstract void testScaleZero();

  /**
   * toString() gives useful results.
   */

  public abstract void testString();

  /**
   * Quaternion subtraction is correct.
   */

  public abstract void testSubtract();
}
