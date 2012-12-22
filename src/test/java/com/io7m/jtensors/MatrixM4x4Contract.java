package com.io7m.jtensors;

/**
 * Properties of 4x4 mutable matrices.
 */

public abstract class MatrixM4x4Contract
{
  public abstract void testAdd();

  public abstract void testAddMutate();

  public abstract void testAddRowScaled();

  public abstract void testAddRowScaledContextEquivalent();

  public abstract void testAddRowScaledOverflowA();

  public abstract void testAddRowScaledOverflowB();

  public abstract void testAddRowScaledOverflowC();

  public abstract void testAddRowScaledUnderflowA();

  public abstract void testAddRowScaledUnderflowB();

  public abstract void testAddRowScaledUnderflowC();

  public abstract void testBufferEndianness();

  public abstract void testCopy();

  public abstract void testDeterminantIdentity();

  public abstract void testDeterminantOther();

  public abstract void testDeterminantScale();

  public abstract void testDeterminantScaleNegative();

  public abstract void testDeterminantZero();

  public abstract void testEqualsCorrect();

  public abstract void testEqualsNotEqualsCorrect();

  public abstract void testExchangeRows();

  public abstract void testExchangeRowsAOverflow();

  public abstract void testExchangeRowsAUnderflow();

  public abstract void testExchangeRowsBOverflow();

  public abstract void testExchangeRowsBUnderflow();

  public abstract void testExchangeRowsContextEquivalent();

  public abstract void testHashcodeNeqExhaustive();

  public abstract void testInitializationFrom();

  public abstract void testInitializationIdentity();

  public abstract void testInvertIdentity();

  public abstract void testInvertIdentityContextEquivalent();

  public abstract void testInvertSimple();

  public abstract void testInvertSimple2();

  public abstract void testInvertSimple2ContextEquivalent();

  public abstract void testInvertSimpleContextEquivalent();

  public abstract void testInvertZero();

  public abstract void testInvertZeroContextEquivalent();

  public abstract void testLookAt_NoTranslation_NegativeX_AroundY();

  public abstract void testLookAt_NoTranslation_NegativeZ_AroundY();

  public abstract void testLookAt_NoTranslation_PositiveX_AroundY();

  public abstract void testLookAt_NoTranslation_PositiveZ_AroundY();

  public abstract void testLookAt_Translation102030_NegativeZ_AroundY();

  public abstract void testMultiplyIdentity();

  public abstract void testMultiplyMutateIdentity();

  public abstract void testMultiplyMutateSimple();

  public abstract void testMultiplySimple();

  public abstract void testMultiplyVectorSimple();

  public abstract void testMultiplyVectorSimpleContextEquivalent();

  public abstract void testMultiplyZero();

  public abstract void testOutOfRangeNegativeColumn();

  public abstract void testOutOfRangeNegativeRow();

  public abstract void testOutOfRangeOverflowColumn();

  public abstract void testOutOfRangeOverflowRow();

  public abstract void testRotateDeterminantOrthogonal();

  public abstract void testRotateVector0X();

  public abstract void testRotateVector0Y();

  public abstract void testRotateVector0Z();

  public abstract void testRotateVector90X();

  public abstract void testRotateVector90Y();

  public abstract void testRotateVector90Z();

  public abstract void testRotateVectorMinus90X();

  public abstract void testRotateVectorMinus90Y();

  public abstract void testRotateVectorMinus90Z();

  public abstract void testRotateX();

  public abstract void testRotateXContextEquivalentInPlace();

  public abstract void testRotateXMakeEquivalent();

  public abstract void testRotateY();

  public abstract void testRotateYContextEquivalentInPlace();

  public abstract void testRotateYMakeEquivalent();

  public abstract void testRotateZ();

  public abstract void testRotateZContextEquivalentInPlace();

  public abstract void testRotateZMakeEquivalent();

  public abstract void testRow();

  public abstract void testRow4();

  public abstract void testRow4Overflow();

  public abstract void testRow4Underflow();

  public abstract void testRowOverflow();

  public abstract void testRowUnderflow();

  public abstract void testScale();

  public abstract void testScaleMutate();

  public abstract void testScaleRow();

  public abstract void testScaleRowContextEquivalent();

  public abstract void testScaleRowMutateOverflow();

  public abstract void testScaleRowMutateUnderflow();

  public abstract void testScaleRowOverflow();

  public abstract void testScaleRowUnderflow();

  public abstract void testSetGetIdentity();

  public abstract void testSetGetInterfaceIdentity();

  public abstract void testStorage();

  public abstract void testString();

  public abstract void testTrace();

  public abstract void testTranslate3_4_Equivalent();

  public abstract void testTranslateSimple2Integer();

  public abstract void testTranslateSimple2IntegerAlt();

  public abstract void testTranslateSimple2Real();

  public abstract void testTranslateSimple2RealAlt();

  public abstract void testTranslateSimple3Integer();

  public abstract void testTranslateSimple3IntegerAlt();

  public abstract void testTranslateSimple3Real();

  public abstract void testTranslateSimple3RealAlt();

  public abstract void testTranslationEquivalent3Integer();

  public abstract void testTranslationEquivalent3Real();

  public abstract void testTranslationMakeEquivalent3Integer();

  public abstract void testTranslationMakeEquivalent3Real();

  public abstract void testTranslationStorage();

  public abstract void testTranspose();

  public abstract void testTransposeMutate();

  public abstract void testZero();
}
