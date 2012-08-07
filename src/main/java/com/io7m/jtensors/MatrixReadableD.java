package com.io7m.jtensors;

import java.nio.DoubleBuffer;

/**
 * 'Read' interface to matrices with double precision elements.
 */

public interface MatrixReadableD
{
  /**
   * Retrieve the value from the matrix at row <code>row</code> , column
   * <code>column</code>.
   */

  public double getRowColumnD(
    int row,
    int column);

  /**
   * Return a read-only view of the buffer that backs this matrix.
   */

  public DoubleBuffer getDoubleBuffer();
}
