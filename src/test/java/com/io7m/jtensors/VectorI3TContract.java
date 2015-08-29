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

/**
 * Properties of 3D vectors.
 */

public abstract class VectorI3TContract
{
  /**
   * <code>∀v. w = absolute(v) → w.x = abs(v.x) ∧ w.y = abs(v.y) ∧ w.z = abs(v.z)</code>
   */

  public abstract <A> void testAbsolute();

  /**
   * Vector addition is correct.
   */

  public abstract <A> void testAdd();

  /**
   * Addition of scaled vectors is correct.
   */

  public abstract <A> void testAddScaled();

  /**
   * Differing vectors are not almost equal.
   */

  public abstract <A> void testAlmostEqualNot();

  /**
   * <code>∀v r s. v ≃ r ∧ r ≃ s → v ≃ s</code>
   */

  public abstract <A> void testAlmostEqualTransitive();

  /**
   * Interface functions work as expected.
   */

  public abstract <A> void testCheckInterface();

  public abstract <A> void testClampByVectorMaximumOrdering();

  public abstract <A> void testClampByVectorMinimumOrdering();

  public abstract <A> void testClampByVectorOrdering();

  public abstract <A> void testClampMaximumOrdering();

  public abstract <A> void testClampMinimumOrdering();

  public abstract <A> void testClampOrdering();

  /**
   * Copying vectors works correctly.
   */

  public abstract <A> void testCopy();

  /**
   * <p>
   * <code>∀v u. dotProduct(crossProduct(v, u), v) = 0 ∧ dotProduct(crossProduct(v, u), u) = 0</code>
   * </p>
   */

  public abstract <A> void testCrossProductPerpendicular();

  /**
   * The default value for D vectors is (0,0,0)
   */

  public abstract <A> void testDefault000();

  public abstract <A> void testDistance();

  public abstract <A> void testDistanceOrdering();

  /**
   * The <code>dotProduct</code> function gives expected results.
   */

  public abstract <A> void testDotProduct();

  public abstract <A> void testDotProductPerpendicular();

  /**
   * <code>∀v. dotProduct(v, v) = 1</code>
   */

  public abstract <A> void testDotProductSelf();

  /**
   * <code>∀v. dotProduct(v, v) = magnitude(v)²</code>
   */

  public abstract <A> void testDotProductSelfMagnitudeSquared();

  /**
   * Equality is correct.
   */

  public abstract <A> void testEqualsCorrect();

  /**
   * Inequality is correct.
   */

  public abstract <A> void testEqualsNotEqualCorrect();

  /**
   * Two equal vectors have the same hashcode.
   */

  public abstract <A> void testHashCodeEqualsCorrect();

  /**
   * All parts of a vector affect the hashcode.
   */

  public abstract <A> void testHashCodeNotEqualCorrect();

  public abstract <A> void testInitializeReadable();

  /**
   * <p>
   * <code>∀v r s. interpolateLinear(v, r, 1.0) = r</code>
   * </p>
   * <p>
   * <code>∀v r s. interpolateLinear(v, r, 0.0) = v</code>
   * </p>
   */

  public abstract <A> void testInterpolateLinearLimits();

  /**
   * The magnitude of any large random vector is greater than zero.
   */

  public abstract <A> void testMagnitudeNonzero();

  /**
   * <p>
   * <code>∀v. magnitude(normalize(v)) = 1</code>
   * </p>
   */

  public abstract <A> void testMagnitudeNormal();

  /**
   * <p>
   * <code>magnitude(normalize((0,0,0)) = 0</code>
   * </p>
   */

  public abstract <A> void testMagnitudeNormalizeZero();

  /**
   * <p>
   * <code>magnitude((1,0,0)) = 1</code>
   * </p>
   */

  public abstract <A> void testMagnitudeOne();

  /**
   * Simple magnitude tests.
   */

  public abstract <A> void testMagnitudeSimple();

  /**
   * <p>
   * <code>magnitude((0,0,0)) = 0</code>
   * </p>
   */

  public abstract <A> void testMagnitudeZero();

  public abstract <A> void testNormalizeSimple();

  /**
   * Normalizing a zero vector results in a zero vector.
   */

  public abstract <A> void testNormalizeZero();

  /**
   * Orthonormalization results in a pair of orthonormal vectors.
   */

  public abstract <A> void testOrthonormalize();

  /**
   * Projecting a vector onto a perpendicular vector results in a vector with
   * zero magnitude.
   */

  public abstract <A> void testProjectionPerpendicularZero();

  /**
   * <p>
   * <code>∀v. scale(v, 1) = v</code>
   * </p>
   */

  public abstract <A> void testScaleOne();

  /**
   * <p>
   * <code>∀v. scale(v, 0) = (0,0,0)</code>
   * </p>
   */

  public abstract <A> void testScaleZero();

  /**
   * toString() gives useful results.
   */

  public abstract <A> void testString();

  /**
   * Vector subtraction is correct.
   */

  public abstract <A> void testSubtract();
}
