package com.io7m.jtensors;

import junit.framework.Assert;

import org.junit.Test;

public class VectorI4ITest
{
  public static int randomNumber()
  {
    return (int) (Integer.MIN_VALUE + (Math.random() * (Integer.MAX_VALUE - Integer.MIN_VALUE)));
  }

  @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI4ITest.randomNumber();
      final int y0 = VectorI4ITest.randomNumber();
      final int z0 = VectorI4ITest.randomNumber();
      final int w0 = VectorI4ITest.randomNumber();

      final int x1 = VectorI4ITest.randomNumber();
      final int y1 = VectorI4ITest.randomNumber();
      final int z1 = VectorI4ITest.randomNumber();
      final int w1 = VectorI4ITest.randomNumber();

      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);
      final VectorI4I vr = VectorI4I.add(v0, v1);

      Assert.assertEquals(x0 + x1, vr.x);
      Assert.assertEquals(y0 + y1, vr.y);
      Assert.assertEquals(z0 + z1, vr.z);
      Assert.assertEquals(w0 + w1, vr.w);
    }
  }

  @Test public void testCheckInterface()
  {
    final VectorI4I v = new VectorI4I(1, 2, 3, 4);
    Assert.assertEquals(1, v.getX());
    Assert.assertEquals(2, v.getY());
    Assert.assertEquals(3, v.getZ());
    Assert.assertEquals(4, v.getW());
  }

  @Test public void testInit()
  {
    final VectorI4I v = new VectorI4I(1, 2, 3, 4);
    Assert.assertEquals(1, v.x);
    Assert.assertEquals(2, v.y);
    Assert.assertEquals(3, v.z);
    Assert.assertEquals(4, v.w);
  }

  @Test public void testInitDefaultZero()
  {
    final VectorI4I v = new VectorI4I();
    Assert.assertEquals(0, v.x);
    Assert.assertEquals(0, v.y);
    Assert.assertEquals(0, v.z);
    Assert.assertEquals(1, v.w);
  }

  @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI4ITest.randomNumber();
      final int y0 = VectorI4ITest.randomNumber();
      final int z0 = VectorI4ITest.randomNumber();
      final int w0 = VectorI4ITest.randomNumber();

      final int x1 = VectorI4ITest.randomNumber();
      final int y1 = VectorI4ITest.randomNumber();
      final int z1 = VectorI4ITest.randomNumber();
      final int w1 = VectorI4ITest.randomNumber();

      final VectorI4I v0 = new VectorI4I(x0, y0, z0, w0);
      final VectorI4I v1 = new VectorI4I(x1, y1, z1, w1);
      final VectorI4I vr = VectorI4I.subtract(v0, v1);

      Assert.assertEquals(x0 - x1, vr.x);
      Assert.assertEquals(y0 - y1, vr.y);
      Assert.assertEquals(z0 - z1, vr.z);
      Assert.assertEquals(w0 - w1, vr.w);
    }
  }
}
