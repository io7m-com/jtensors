package com.io7m.jtensors;

/**
 * 'Read' interface to 2x2 matrices with double precision elements.
 */

public interface MatrixReadable2x2D extends MatrixReadableD
{
  /**
   * Retrieve row <code>row</code>, saving the result to <code>out</code>.
   * 
   * @param row
   *          The index of the row, starting at <code>0</code>.
   * @param out
   *          The output vector.
   */

  void getRowD(
    final int row,
    final VectorM2D out);
}
