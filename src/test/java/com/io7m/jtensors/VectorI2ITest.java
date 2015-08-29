package com.io7m.jtensors;

import junit.framework.Assert;

import org.junit.Test;

public class VectorI2ITest
{
  public static int randomNumber()
  {
    return (int) (Integer.MIN_VALUE + (Math.random() * (Integer.MAX_VALUE - Integer.MIN_VALUE)));
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
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

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertEquals(1, v.getXI());
    Assert.assertEquals(2, v.getYI());
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final VectorI2I v0 = new VectorI2I(0, 0);
    final VectorI2I v1 = new VectorI2I(0, 0);
    final VectorI2I vy = new VectorI2I(0, 1);
    final VectorI2I vx = new VectorI2I(1, 0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final VectorI2I v0 = new VectorI2I(0, 0);
    final VectorI2I v1 = new VectorI2I(0, 0);
    final VectorI2I vy = new VectorI2I(0, 1);
    final VectorI2I vx = new VectorI2I(1, 0);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testInit()
  {
    final VectorI2I v = new VectorI2I(1, 2);
    Assert.assertEquals(1, v.x);
    Assert.assertEquals(2, v.y);
  }

  @SuppressWarnings("static-method") @Test public void testInitDefaultZero()
  {
    final VectorI2I v = new VectorI2I();
    Assert.assertEquals(0, v.x);
    Assert.assertEquals(0, v.y);
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI2I v = new VectorI2I(0, 1);
    Assert.assertTrue(v.toString().equals("[VectorI2I 0 1]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
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
