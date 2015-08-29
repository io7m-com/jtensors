package com.io7m.jtensors;

/**
 * 'Read' interface to 2x2 matrices with single precision elements.
 */

public interface MatrixReadable2x2F extends MatrixReadableF
{
  /**
   * Retrieve row <code>row</code>, saving the result to <code>out</code>.
   * 
   * @param row
   *          The index of the row, starting at <code>0</code>.
   * @param out
   *          The output vector.
   */

  void getRowF(
    final int row,
    final VectorM2F out);
}
