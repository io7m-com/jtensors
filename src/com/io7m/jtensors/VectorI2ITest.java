package com.io7m.jtensors;

import junit.framework.Assert;

import org.junit.Test;

public class VectorI2ITest
{
  public static int randomNumber()
  {
    return (int) (Integer.MIN_VALUE + (Math.random() * (Integer.MAX_VALUE - Integer.MIN_VALUE)));
  }

  @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI2ITest.randomNumber();
      final int y0 = VectorI2ITest.randomNumber();

      final int x1 = VectorI2ITest.randomNumber();
      final int y1 = VectorI2ITest.randomNumber();

      final VectorI2I v0 = new VectorI2I(x0, y0);
      final VectorI2I v1 = new VectorI2I(x1, y1);
      final VectorI2I vr = VectorI2I.add(v0, v1);

      Assert.assertEquals(x0 + x1, vr.x);
      Assert.assertEquals(y0 + y1, vr.y);
    }
  }

  @Test public void testCheckInterface()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertEquals(1, v.getXi());
    Assert.assertEquals(2, v.getYi());
  }

  @Test public void testInit()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertEquals(1, v.x);
    Assert.assertEquals(2, v.y);
  }

  @Test public void testInitDefaultZero()
  {
    final VectorI2I v = new VectorI2I();
    Assert.assertEquals(0, v.x);
    Assert.assertEquals(0, v.y);
  }

  @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI2ITest.randomNumber();
      final int y0 = VectorI2ITest.randomNumber();

      final int x1 = VectorI2ITest.randomNumber();
      final int y1 = VectorI2ITest.randomNumber();

      final VectorI2I v0 = new VectorI2I(x0, y0);
      final VectorI2I v1 = new VectorI2I(x1, y1);
      final VectorI2I vr = VectorI2I.subtract(v0, v1);

      Assert.assertEquals(x0 - x1, vr.x);
      Assert.assertEquals(y0 - y1, vr.y);
    }
  }
}
