package com.io7m.jtensors;

import java.nio.FloatBuffer;

/**
 * 'Read' interface to matrices with single precision elements.
 */

public interface MatrixReadableF
{
  /**
   * Return a read-only view of the buffer that backs this matrix.
   */

  public FloatBuffer getFloatBuffer();

  /**
   * Retrieve the value from the matrix at row <code>row</code> , column
   * <code>column</code>.
   */

  public float getRowColumnF(
    int row,
    int column);
}
