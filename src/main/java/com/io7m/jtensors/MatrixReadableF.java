package com.io7m.jtensors;

import java.nio.FloatBuffer;

/**
 * 'Read' interface to matrices with single precision elements.
 */

public interface MatrixReadableF
{
  /**
   * Retrieve the value from the matrix at row <code>row</code> , column
   * <code>column</code>.
   */

  public double getRowColumnF(
    int row,
    int column);

  /**
   * Return a read-only view of the buffer that backs this matrix.
   */

  public FloatBuffer getFloatBuffer();
}
