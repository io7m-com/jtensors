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
 * Properties of 4x4 mutable matrices.
 */

public abstract class MatrixM4x4TContract
{
  public abstract <A> void testAdd();

  public abstract <A> void testAddMutate();

  public abstract <A> void testAddRowScaled();

  public abstract <A> void testAddRowScaledContextEquivalent();

  public abstract <A> void testAddRowScaledOverflowA();

  public abstract <A> void testAddRowScaledOverflowB();

  public abstract <A> void testAddRowScaledOverflowC();

  public abstract <A> void testAddRowScaledUnderflowA();

  public abstract <A> void testAddRowScaledUnderflowB();

  public abstract <A> void testAddRowScaledUnderflowC();

  public abstract <A> void testBufferEndianness();

  public abstract <A> void testCopy();

  public abstract <A> void testDeterminantIdentity();

  public abstract <A> void testDeterminantOther();

  public abstract <A> void testDeterminantScale();

  public abstract <A> void testDeterminantScaleNegative();

  public abstract <A> void testDeterminantZero();

  public abstract <A> void testEqualsCorrect();

  public abstract <A> void testEqualsNotEqualsCorrect();

  public abstract <A> void testExchangeRows();

  public abstract <A> void testExchangeRowsAOverflow();

  public abstract <A> void testExchangeRowsAUnderflow();

  public abstract <A> void testExchangeRowsBOverflow();

  public abstract <A> void testExchangeRowsBUnderflow();

  public abstract <A> void testExchangeRowsContextEquivalent();

  public abstract <A> void testHashcodeNeqExhaustive();

  public abstract <A> void testInitializationFrom();

  public abstract <A> void testInitializationIdentity();

  public abstract <A> void testInvertIdentity();

  public abstract <A> void testInvertIdentityContextEquivalent();

  public abstract <A> void testInvertSimple();

  public abstract <A> void testInvertSimple2();

  public abstract <A> void testInvertSimple2ContextEquivalent();

  public abstract <A> void testInvertSimpleContextEquivalent();

  public abstract <A> void testInvertZero();

  public abstract <A> void testInvertZeroContextEquivalent();

  public abstract <A> void testLookAt_NoTranslation_NegativeX_AroundY();

  public abstract <A> void testLookAt_NoTranslation_NegativeZ_AroundY();

  public abstract <A> void testLookAt_NoTranslation_PositiveX_AroundY();

  public abstract <A> void testLookAt_NoTranslation_PositiveZ_AroundY();

  public abstract <A> void testLookAt_Translation102030_NegativeZ_AroundY();

  public abstract <A> void testMultiplyIdentity();

  public abstract <A> void testMultiplyMutateIdentity();

  public abstract <A> void testMultiplyMutateSimple();

  public abstract <A> void testMultiplySimple();

  public abstract <A> void testMultiplyVectorSimple();

  public abstract <A> void testMultiplyVectorSimpleContextEquivalent();

  public abstract <A> void testMultiplyZero();

  public abstract <A> void testOutOfRangeNegativeColumn();

  public abstract <A> void testOutOfRangeNegativeRow();

  public abstract <A> void testOutOfRangeOverflowColumn();

  public abstract <A> void testOutOfRangeOverflowRow();

  public abstract <A> void testRotateDeterminantOrthogonal();

  public abstract <A> void testRotateVector0X();

  public abstract <A> void testRotateVector0Y();

  public abstract <A> void testRotateVector0Z();

  public abstract <A> void testRotateVector90X();

  public abstract <A> void testRotateVector90Y();

  public abstract <A> void testRotateVector90Z();

  public abstract <A> void testRotateVectorMinus90X();

  public abstract <A> void testRotateVectorMinus90Y();

  public abstract <A> void testRotateVectorMinus90Z();

  public abstract <A> void testRotateX();

  public abstract <A> void testRotateXContextEquivalentInPlace();

  public abstract <A> void testRotateXMakeEquivalent();

  public abstract <A> void testRotateY();

  public abstract <A> void testRotateYContextEquivalentInPlace();

  public abstract <A> void testRotateYMakeEquivalent();

  public abstract <A> void testRotateZ();

  public abstract <A> void testRotateZContextEquivalentInPlace();

  public abstract <A> void testRotateZMakeEquivalent();

  public abstract <A> void testRow();

  public abstract <A> void testRow4();

  public abstract <A> void testRow4Overflow();

  public abstract <A> void testRow4Underflow();

  public abstract <A> void testRowOverflow();

  public abstract <A> void testRowUnderflow();

  public abstract <A> void testScale();

  public abstract <A> void testScaleMutate();

  public abstract <A> void testScaleRow();

  public abstract <A> void testScaleRowContextEquivalent();

  public abstract <A> void testScaleRowMutateOverflow();

  public abstract <A> void testScaleRowMutateUnderflow();

  public abstract <A> void testScaleRowOverflow();

  public abstract <A> void testScaleRowUnderflow();

  public abstract <A> void testSetGetIdentity();

  public abstract <A> void testSetGetInterfaceIdentity();

  public abstract <A> void testStorage();

  public abstract <A> void testString();

  public abstract <A> void testTrace();

  public abstract <A> void testTranslate3_4_Equivalent();

  public abstract <A> void testTranslateSimple2Integer();

  public abstract <A> void testTranslateSimple2IntegerAlt();

  public abstract <A> void testTranslateSimple2Real();

  public abstract <A> void testTranslateSimple2RealAlt();

  public abstract <A> void testTranslateSimple3Integer();

  public abstract <A> void testTranslateSimple3IntegerAlt();

  public abstract <A> void testTranslateSimple3Real();

  public abstract <A> void testTranslateSimple3RealAlt();

  public abstract <A> void testTranslationEquivalent3Integer();

  public abstract <A> void testTranslationEquivalent3Real();

  public abstract <A> void testTranslationMakeEquivalent3Integer();

  public abstract <A> void testTranslationMakeEquivalent3Real();

  public abstract <A> void testTranslationStorage();

  public abstract <A> void testTranspose();

  public abstract <A> void testTransposeMutate();

  public abstract <A> void testZero();
}
