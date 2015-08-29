package com.io7m.jtensors;

import junit.framework.Assert;

import org.junit.Test;

public class VectorI4ITest
{
  public static int randomNumber()
  {
    return (int) (Integer.MIN_VALUE + (Math.random() * (Integer.MAX_VALUE - Integer.MIN_VALUE)));
  }

  @SuppressWarnings("static-method") @Test public void testAdd()
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

  @SuppressWarnings("static-method") @Test public void testCheckInterface()
  {
    final VectorI4I v = new VectorI4I(1, 2, 3, 4);
    Assert.assertEquals(1, v.getXI());
    Assert.assertEquals(2, v.getYI());
    Assert.assertEquals(3, v.getZI());
    Assert.assertEquals(4, v.getWI());
  }

  @SuppressWarnings("static-method") @Test public void testEqualsCorrect()
  {
    final VectorI4I v0 = new VectorI4I(0, 0, 0, 0);
    final VectorI4I v1 = new VectorI4I(0, 0, 0, 0);
    final VectorI4I vw = new VectorI4I(0, 0, 0, 1);
    final VectorI4I vz = new VectorI4I(0, 0, 1, 0);
    final VectorI4I vy = new VectorI4I(0, 1, 0, 0);
    final VectorI4I vx = new VectorI4I(1, 0, 0, 0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vw));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @SuppressWarnings("static-method") @Test public void testHashCodeCorrect()
  {
    final VectorI4I v0 = new VectorI4I(0, 0, 0, 0);
    final VectorI4I v1 = new VectorI4I(0, 0, 0, 0);
    final VectorI4I vw = new VectorI4I(0, 0, 0, 1);
    final VectorI4I vz = new VectorI4I(0, 0, 1, 0);
    final VectorI4I vy = new VectorI4I(0, 1, 0, 0);
    final VectorI4I vx = new VectorI4I(1, 0, 0, 0);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
    Assert.assertTrue(v0.hashCode() != vz.hashCode());
    Assert.assertTrue(v0.hashCode() != vw.hashCode());
  }

  @SuppressWarnings("static-method") @Test public void testInit()
  {
    final VectorI4I v = new VectorI4I(1, 2, 3, 4);
    Assert.assertEquals(1, v.x);
    Assert.assertEquals(2, v.y);
    Assert.assertEquals(3, v.z);
    Assert.assertEquals(4, v.w);
  }

  @SuppressWarnings("static-method") @Test public void testInitDefaultZero()
  {
    final VectorI4I v = new VectorI4I();
    Assert.assertEquals(0, v.x);
    Assert.assertEquals(0, v.y);
    Assert.assertEquals(0, v.z);
    Assert.assertEquals(1, v.w);
  }

  @SuppressWarnings("static-method") @Test public void testString()
  {
    final VectorI4I v = new VectorI4I(0, 1, 2, 3);
    Assert.assertTrue(v.toString().equals("[VectorI4I 0 1 2 3]"));
  }

  @SuppressWarnings("static-method") @Test public void testSubtract()
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
